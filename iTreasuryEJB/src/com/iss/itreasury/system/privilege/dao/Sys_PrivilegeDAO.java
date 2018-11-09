/**
 * 权限数据库访问层类实体
 * 
 * @author jinchen
 */

package com.iss.itreasury.system.privilege.dao;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.custommenu.bizlogic.CustomMenubiz;
import com.iss.itreasury.custommenu.dataentity.User_customprivilegeInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
//import com.iss.itreasury.ldap.*;
import com.iss.itreasury.system.privilege.dataentity.Sys_PrivilegeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.itreasury.system.privilege.util.PrivilegeConstant;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.Constant;

//import com.iss.itreasury.util.Constant;

public class Sys_PrivilegeDAO extends ITreasuryDAO
{

    public Sys_PrivilegeDAO()
    {
        super("sys_privilege");
    }

    public Sys_PrivilegeDAO(String tableName)
    {
        super(tableName);
    }

    public Sys_PrivilegeDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public Sys_PrivilegeDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public Sys_PrivilegeDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public Sys_PrivilegeDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }

    /**
     * 根据模块ID查询所有权限
     * 
     * @param moduleId
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesbyModuleId(long moduleId,long officeId) throws SQLException, ITreasuryDAOException
    {
        ArrayList co = new ArrayList();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();

            sb.append("select * from sys_privilege where moduleId=");
            sb.append(moduleId);
            //modify by leiyang date 2007/08/09
            sb.append(" and plevel>1 and plevel<>999 and statusId=");
            sb.append(Constant.RecordStatus.VALID);
            if(officeId>-1)
            {
            	sb.append(" and officeid ="+officeId);
            	
            }
            sb.append(" order by privilegeNo ");
            log.info("sql="+sb.toString());
            transPS = transConn.prepareStatement(sb.toString());
            Sys_PrivilegeInfo privilege;
            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                privilege = new Sys_PrivilegeInfo();
                privilege.setId(transRS.getLong("id"));
                privilege.setMenuUrl(transRS.getString("menuUrl"));
                privilege.setName(transRS.getString("name"));
                privilege.setOfficeID(transRS.getLong("officeId"));
                privilege.setPlevel(transRS.getLong("plevel"));
                privilege.setPrivilegeNo(transRS.getString("privilegeNo"));
                co.add(privilege);
            }
            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return co.size() > 0 ? co : null;
    }
    
    /**
     * Create by leiyang date 2007/08/09
     * 
     * @return
     * @throws SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesByMain(ArrayList arg0,long lUserId,long lOfficeID) throws SQLException, ITreasuryDAOException
    {
        ArrayList co = new ArrayList();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();
            String subSql = "";
            Sys_PrivilegeInfo privilege;
            sb.append("select * from sys_privilege where plevel=1");
            sb.append(" and moduleid in (");
            for(int i=0; i<arg0.size(); i++){
            	//add by zcwang 2008-1-10
            	if(arg0.get(i).toString().equals(String.valueOf(Constant.CustomModule.CUSTOMMODULE)))
            	{
            		CustomMenubiz customMenubiz = new CustomMenubiz();
            		User_customprivilegeInfo uInfo=customMenubiz.getUser_customprivilege(lUserId);
            		privilege = new Sys_PrivilegeInfo();
                   // privilege.setId(transRS.getLong("id"));
            		privilege.setModuleID(Constant.CustomModule.CUSTOMMODULE);
                    privilege.setMenuUrl("iTreasuryMain.jsp?moduleID=" + Constant.CustomModule.CUSTOMMODULE);
                    privilege.setName(uInfo.getDModuleName());
                    privilege.setOfficeID(1);
                    privilege.setPlevel(1);
                    privilege.setPrivilegeNo("");
                    co.add(privilege);
            	}
            	else
            	{
            		/*
	            	if(i == 0){
	            		 sb.append(arg0.get(i).toString());
	            	}
	            	else{
	            		sb.append(", " + arg0.get(i).toString());
	            	}
	            	*/
            		subSql += arg0.get(i).toString()+",";
            	}
            }
            if(subSql.lastIndexOf(",")==subSql.length()-1)
            {
            	sb.append(subSql.substring(0,(subSql.length()-1)));
            }
            sb.append(")");
            sb.append(" and statusId=");
            sb.append(Constant.RecordStatus.VALID);
            sb.append(" and officeid="+lOfficeID);
            sb.append(" order by id");
            transPS = transConn.prepareStatement(sb.toString());
            System.out.println(sb.toString()+" ****************************-----------*****************************");
            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                privilege = new Sys_PrivilegeInfo();
                privilege.setId(transRS.getLong("id"));
                privilege.setModuleID(transRS.getLong("moduleId"));
                privilege.setMenuUrl(transRS.getString("menuUrl"));
                privilege.setName(transRS.getString("name"));
                privilege.setOfficeID(transRS.getLong("officeId"));
                privilege.setPlevel(transRS.getLong("plevel"));
                privilege.setPrivilegeNo(transRS.getString("privilegeNo"));
                co.add(privilege);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            finalizeDAO();
        }
        return co.size() > 0 ? co : null;
    }
    
    

    /**
     * 根据模块查询用户具有的所有权限取并集
     * 
     * @param userId
     * @param moduleID
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     */
    public Collection getPrivilegeOfUser(long userId, long moduleID,long lOfficeID) throws ITreasuryDAOException, SQLException
    {

        Vector vPrivilegeList = new Vector();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append(" select distinct sp.* \n");
            sb.append(" from sys_privilege sp,sys_group_of_user sgou, sys_privilege_of_group spou,sys_group sgroup\n");
            sb.append(" where sp.id=spou.privilegeId and spou.groupid=sgou.groupid and  spou.groupid = sgroup.id \n");
            sb.append("   and sgou.userid= ");
            sb.append(userId);
            sb.append("   and sp.moduleId= ");
            sb.append(moduleID);
            sb.append("  and sp.statusid= ");
            sb.append(Constant.RecordStatus.VALID);
            //add by wjliu -2007-5-31 加一个用户组为已复核的条件
            sb.append("  and sgroup.nstatusid = ");
            sb.append(Constant.RecordStatus.VALID);
            //modify by leiyang date 2007/08/09
            sb.append("  and sp.plevel>1");
            sb.append("  and sp.officeid ="+lOfficeID);
            
            sb.append(" order by privilegeNo ");
            System.out.println("查询用户权限的sql为            " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            Sys_PrivilegeInfo sys_privilegeEntity;

            while (transRS.next())
            {
                sys_privilegeEntity = new Sys_PrivilegeInfo();
                sys_privilegeEntity.setId(transRS.getLong("id"));
                sys_privilegeEntity.setMenuUrl(transRS.getString("menuurl"));
                sys_privilegeEntity.setName(transRS.getString("name"));
                sys_privilegeEntity.setPlevel(transRS.getLong("plevel"));
                sys_privilegeEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
                sys_privilegeEntity.setModuleID(moduleID);
                vPrivilegeList.add(sys_privilegeEntity);
            }

            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return vPrivilegeList.size() > 0 ? vPrivilegeList : null;

    }

    public Collection findPrivilegesByGroupId(long groupId) throws SQLException, ITreasuryDAOException
    {
        Vector vPrivilegeList = new Vector();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append(" select * from sys_privilege ");
            sb.append(" where id  in (select  privilegeId from sys_privilege_of_group ");
            sb.append(" where groupId  =   ");
            sb.append(groupId);
            sb.append(" )  ");

            sb.append(" order by privilegeNo ");
            System.out.println("查询用户权限的sql为            " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();

            Sys_PrivilegeInfo sys_privilegeEntity;

            while (transRS.next())
            {
                sys_privilegeEntity = new Sys_PrivilegeInfo();
                sys_privilegeEntity.setId(transRS.getLong("id"));
                sys_privilegeEntity.setMenuUrl(transRS.getString("menuurl"));
                sys_privilegeEntity.setName(transRS.getString("name"));
                sys_privilegeEntity.setPlevel(transRS.getLong("plevel"));
                sys_privilegeEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
                //sys_privilegeEntity.setModuleID(moduleID);
                vPrivilegeList.add(sys_privilegeEntity);
            }

            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return vPrivilegeList.size() > 0 ? vPrivilegeList : null;

    }

    /**
     * 初始化权限信息
     * 
     * @param privilege
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void addPrivilege(Sys_PrivilegeInfo privilege) throws ITreasuryDAOException, SQLException
    {
        try
        {
            initDAO();
            String sqlstr = "INSERT INTO sys_privilege (plevel,privilegeNo,officeID,moduleID,name,Id,menuUrl) VALUES (" + privilege.getPlevel() + ",'" + privilege.getPrivilegeNo() + "'," + privilege.getOfficeID() + "," + privilege.getModuleID() + ",'" + privilege.getName() + "',Seq_Sys_Privilege.Nextval,'" + privilege.getMenuUrl() + "') ";
            Statement stmt = transConn.createStatement();
            System.out.println(sqlstr);
            stmt.executeUpdate(sqlstr);
            //transConn.commit();
            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
    }

    public Collection findModulesByUser(long userID,long lOfficeID) throws ITreasuryDAOException, SQLException
	{
    	ArrayList a = null;
        try
        {
        	a = new ArrayList();
        	//	增加自定义模块信息 2008-1-10
			CustomMenubiz customMenubiz = new CustomMenubiz();
			if(customMenubiz.getUser_customprivilege(userID)!=null)
			{
				a.add(new Long(Constant.CustomModule.CUSTOMMODULE));
			}
			//
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append(" select distinct sp.moduleID \n");
            sb.append(" from sys_privilege sp,sys_group_of_user sgou, sys_privilege_of_group spou,sys_userauthority s \n");
            sb.append(" where sp.id=spou.privilegeId and spou.groupid=sgou.groupid  \n");
            sb.append("  and s.userid = sgou.userid  \n");
            sb.append("   and sgou.userid= ");
            sb.append(userID);
            sb.append("  and sp.statusid = ");
            sb.append(Constant.RecordStatus.VALID);
            sb.append(" and s.nstatusid = "+SYSConstant.SysAuthority.ALREADYAUTHORITY);
            sb.append(" and s.authorizedofficeid = "+lOfficeID);
            sb.append(" and sgou.officeid ="+lOfficeID);
            //modify by leiyang date 2007/08/09
            sb.append("  and sp.plevel>1");

            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            while (transRS.next())
            {
            	a.add(new Long(transRS.getLong("moduleID")));	
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            finalizeDAO();
        }
    	
    	
    	return a;
	}
    
    public Collection findModulesByUser(long userID) throws ITreasuryDAOException, SQLException
	{
    	ArrayList a = null;
        try
        {
        	a = new ArrayList();
        	//	增加自定义模块信息 2008-1-10
			CustomMenubiz customMenubiz = new CustomMenubiz();
			if(customMenubiz.getUser_customprivilege(userID)!=null)
			{
				a.add(new Long(Constant.CustomModule.CUSTOMMODULE));
			}
			//
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append(" select distinct sp.moduleID \n");
            sb.append(" from sys_privilege sp,sys_group_of_user sgou, sys_privilege_of_group spou \n");
            sb.append(" where sp.id=spou.privilegeId and spou.groupid=sgou.groupid  \n");
            sb.append("   and sgou.userid= ");
            sb.append(userID);
            sb.append("  and sp.statusid = ");
            sb.append(Constant.RecordStatus.VALID);
            //modify by leiyang date 2007/08/09
            sb.append("  and sp.plevel>1");
            sb.append("  and sgou.officeid in ");
            sb.append("  (");
            sb.append("  select s.authorizedofficeid from sys_userauthority s ");
            sb.append("  where s.userid ="+userID);
            sb.append("  and s.nstatusid ="+SYSConstant.SysAuthority.ALREADYAUTHORITY);
            sb.append("  )");
            
            log.info("sql="+sb.toString());
            
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            
            
            while (transRS.next())
            {
            	a.add(new Long(transRS.getLong("moduleID")));	
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            finalizeDAO();
        }
    	
    	
    	return a;
	}
    
    public ArrayList findUserAuthority(Sys_UserInfo userInfoCondition,long lOrderCondition, long lasc)throws ITreasuryDAOException, SQLException
    {
    	ArrayList list = new ArrayList();
    	ArrayList singlelist = null;
    	try
    	{
    		StringBuffer sql = new StringBuffer();
    		initDAO();
    		sql.append(" select s.id,u.sname,u.sloginno,u.nofficeid,s.scurrencyid,a.sname,s.dtinput,s.nstatusid,u.id,s.checkuserid,s.dtcheck ");
    		sql.append(" from ( ");
    		sql.append(" select * from sys_userauthority s ");
    		sql.append(" where s.authorizedofficeid="+userInfoCondition.getAuthorizedOfficeID());
    		if(userInfoCondition.getCurrencyID()!=null&&userInfoCondition.getCurrencyID().trim().length()>0)
    		{
    			
    			sql.append(" and (s.scurrencyid like \'%," + userInfoCondition.getCurrencyID() + ",%\'");
    			sql.append(" or s.scurrencyid like \'" + userInfoCondition.getCurrencyID() + ",%\'");
    			sql.append(" or s.scurrencyid like \'%," + userInfoCondition.getCurrencyID() + "\'");
    			sql.append(" or s.scurrencyid =\'" + userInfoCondition.getCurrencyID() + "\')");
    		}
    		sql.append(" ) s ");
    		sql.append(" left join userinfo a ");
    		sql.append(" on s.ninputuserid = a.id ");
    		sql.append(" right join ( ");
    		sql.append(" select * from userinfo u");
    		sql.append(" where u.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
    		if(userInfoCondition.getName()!=null&&userInfoCondition.getName().length()>0)
    		{
    			sql.append(" and u.sname like '%"+userInfoCondition.getName()+"%'");
    		}
    		
    		if(userInfoCondition.getLoginNo()!=null&&userInfoCondition.getLoginNo().length()>0)
    		{
    			sql.append(" and u.sloginno like '%"+userInfoCondition.getLoginNo()+"%'");
    		}
    		
    		if(userInfoCondition.getOfficeID()>-1)
    		{
    			sql.append(" and u.nofficeid ="+userInfoCondition.getOfficeID());
    		}
    		sql.append(" ) u ");
    		sql.append(" on u.id = s.userid ");
    		sql.append(" where 2>1 ");
    		if(userInfoCondition.getStatusID()>-1)
    		{
    			if(userInfoCondition.getStatusID()==SYSConstant.SysAuthority.NOTAUTHORITY)
    			{
    				sql.append(" and s.id is null ");
    			}
    			else
    			{
    				sql.append(" and s.id is not null ");
    				sql.append(" and s.nstatusid="+userInfoCondition.getStatusID());
    			}
    			//复核时复核人不能和录入人相同
    			if(userInfoCondition.getStatusID()==SYSConstant.SysAuthority.INAUTHORIZATION)
    			{
    				if(userInfoCondition.getCheckUserID()>0)
    				{
    					sql.append(" and s.ninputuserid !="+userInfoCondition.getCheckUserID());
    				}
    			}
    		}
    		
            if (lOrderCondition < 0)
            {
            	sql.append(" order by u.id ");
            }
            else if (lOrderCondition == PrivilegeConstant.UserOrderCondition.USERNAME)
            {
            	sql.append(" order by u.sname ");
            }
            else if (lOrderCondition == PrivilegeConstant.UserOrderCondition.LOGINNAME)
            {
            	sql.append(" order by u.sloginno ");
            }
            else if (lOrderCondition == PrivilegeConstant.UserOrderCondition.OFFICE)
            {
            	sql.append(" order by u.nofficeid ");
            }
            else if (lOrderCondition == PrivilegeConstant.UserOrderCondition.CURRENCY)
            {
            	sql.append(" order by s.scurrencyid ");
            }    
            else if (lOrderCondition == PrivilegeConstant.UserOrderCondition.INPUTUSER)
            {
            	sql.append(" order by a.sname ");
            }              
            else if (lOrderCondition == PrivilegeConstant.UserOrderCondition.INPUTDATE)
            {
            	sql.append(" order by s.dtinput ");
            }             
            if (lasc == 1)
            {
            	sql.append(" asc ");
            }
            else
            {
            	sql.append(" desc ");
            }
            log.info("sql="+sql.toString());
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS = transPS.executeQuery();
    		ResultSetMetaData rsmd = transRS.getMetaData();
    		while(transRS.next())
    		{
    			singlelist = new ArrayList();
    			for(int i = 1;i<=rsmd.getColumnCount();i++)
    			{
    				
    				singlelist.add(transRS.getString(i));
    			}
    			list.add(singlelist);
    		}
    		
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		finalizeDAO();
    	}
    	return list.size()>0?list:null;
    }

    public static void main(String args1[])
    {
    	/*try {
			Sys_PrivilegeDAO dao = new Sys_PrivilegeDAO();
			ArrayList a = (ArrayList)dao.findModulesByUser(1);
			
			for (int i=0;i<a.size();i++)
				System.out.println(a.get(i));
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        //      初始化权限数据
        /*
         * Sys_PrivilegeDAO dao = new Sys_PrivilegeDAO();
         * 
         * Vector v = SettlementMenu_dq.getCollection(); int count = v.size();
         * for(int i = 0;i <count; i++) {
         * 
         * Sys_PrivilegeEntity p = new Sys_PrivilegeEntity();
         * p.setPlevel(((com.iss.itreasury.ldap.PrivilegeInfo)v.get(i)).lLevel);
         * p.setMenuUrl(((com.iss.itreasury.ldap.PrivilegeInfo)v.get(i)).strJSP);
         * p.setName(((com.iss.itreasury.ldap.PrivilegeInfo)v.get(i)).strName);
         * p.setPrivilegeNo(((com.iss.itreasury.ldap.PrivilegeInfo)v.get(i)).strNo);
         * try { dao.addPrivilege(p); } catch (ITreasuryDAOException e) { //
         * TODO Auto-generated catch block e.printStackTrace(); } catch
         * (SQLException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } //String strlevel =
         * ((com.iss.itreasury.ldap.PrivilegeInfo)v.get(i)).lLevel; }
         * System.out.println("初始化权限数据完成");
         */
        /*
         * Sys_PrivilegeDAO dao = new Sys_PrivilegeDAO(); Collection c = null;
         * try { c = dao.findPrivilegesByGroupId(50); } catch
         * (ITreasuryDAOException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } catch (SQLException e) { // TODO
         * Auto-generated catch block e.printStackTrace(); } if (c!=null) {
         * System.out.println(c.size()); System.out.println("yse"); } else
         * System.out.println("no...............");
         */

    }
}