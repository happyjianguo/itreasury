// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name: Sys_UserDAO.java
/**
 * 用户信息数据访问层类实体
 * 
 * @author jicnhen
 *  
 */

package com.iss.itreasury.system.privilege.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.system.privilege.bizlogic.UserBean;
import com.iss.itreasury.system.privilege.dataentity.QueryOfficeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserAuthorityInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.itreasury.system.privilege.util.PrivilegeConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
//import java.io.PrintStream;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.DataFormat;
// Referenced classes of package com.iss.itreasury.system.privilege.dao:
//            Sys_GroupDAO

public class Sys_UserDAO extends ITreasuryDAO
{

    public Sys_UserDAO()
    {
        super("userinfo");
    }

    public Sys_UserDAO(String tableName)
    {
        super(tableName);
    }

    public Sys_UserDAO(String tableName, Connection conn)
    {
        super(tableName, true, conn);
    }

    public Sys_UserDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public Sys_UserDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public Sys_UserDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }

    /**
     * 根据用户登录名查询用户信息
     * 
     * @param loginName
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findByLoginNo(String LoginNo) throws ITreasuryDAOException
    {
        Sys_UserInfo condition = new Sys_UserInfo();
        try
        {

            condition.setLoginNo(LoginNo);
            condition.setStatusID(1L);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return findByCondition(condition);
    }
    
    /**
     * 根据用户信息，用户组ID，排序条件查询用户信息
     * 
     * @param userInfoCondition
     * @param groupId
     * @param orderCondition
     * @return
     */
    public Vector findUserByCondition(Sys_UserInfo userInfoCondition, long groupId, long orderCondition, long lasc) throws ITreasuryDAOException, SQLException
    {
        Vector vectTemp = new Vector();
        // TODO Auto-generated method stub
        try
        {
            String name = userInfoCondition.getName();
            String login = userInfoCondition.getLoginNo();
            long officeid = userInfoCondition.getOfficeID();
            String currencyID = userInfoCondition.getCurrencyID();
            long statusID = userInfoCondition.getStatusID();
            initDAO();
            StringBuffer sbSQL = new StringBuffer();
            //modified by qhzhou 2008-03-25
            //sbSQL.append("select a.id,a.sname,a.sloginno,a.nofficeid,a.sCURRENCYId,b.sname,a.dtinput,a.ndepartmentid,a.nstatusid,a.nisadminuser from userinfo a,userinfo b  where a.nstatusid="+SYSConstant.SysCheckStatus.UNCHECK+" and a.NINPUTUSERID=b.id");
            sbSQL.append("select a.id as id,a.sname,a.sloginno,a.nofficeid,a.sCURRENCYId,b.sname,a.dtinput,a.ndepartmentid, a.CheckUserID,a.dtCheck,a.nstatusid,a.purviewtype,a.ninputuserid,a.fingerPrintType from userinfo a,userinfo b  where  a.NINPUTUSERID=b.id"); //jzw 2010-05-17
            sbSQL.append("   and a.id in(select userid from sys_group_of_user where groupid=" + groupId + ")");
            if (name.length() > 0)
            {

                sbSQL.append(" and a.sname like '%" + name + "%'");
            }
            if (login.length() > 0)
            {
                sbSQL.append(" and a.sloginno like '%" + login + "%'");
            }
            if (officeid != -1L)
            {
                sbSQL.append(" and a.nofficeid=" + officeid);
            }
            if(statusID>0)
            {
            	sbSQL.append(" and a.nstatusid=" + statusID);
            }
            else
            {
            	sbSQL.append(" and a.nstatusid!= 0 ");
            }
            if (currencyID != null)
            {
                sbSQL.append(" and (a.scurrencyID like \'%," + currencyID + ",%\'");
                sbSQL.append(" or a.scurrencyID like \'" + currencyID + ",%\'");
                sbSQL.append(" or a.scurrencyID like \'%," + currencyID + "\'");
                sbSQL.append(" or a.scurrencyID =\'" + currencyID + "\')");
            }
            if (orderCondition < 0)
            {
                sbSQL.append(" order by a.id");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.USERNAME)
            {
                sbSQL.append(" order by a.sname");
                if (lasc == 1)
                    sbSQL.append(" asc ");
                else
                    sbSQL.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.LOGINNAME)
            {
                sbSQL.append(" order by a.sloginno");
                if (lasc == 1)
                    sbSQL.append(" asc ");
                else
                    sbSQL.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.OFFICE)
            {
                sbSQL.append(" order by a.nofficeid");
                if (lasc == 1)
                    sbSQL.append(" asc ");
                else
                    sbSQL.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.CURRENCY)
            {
                sbSQL.append(" order by a.sCURRENCYId");
                if (lasc == 1)
                    sbSQL.append(" asc ");
                else
                    sbSQL.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTUSER)
            {
                sbSQL.append(" order by b.sname");
                if (lasc == 1)
                    sbSQL.append(" asc ");
                else
                    sbSQL.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTDATE)
            {
                sbSQL.append(" order by a.dtinput");
                if (lasc == 1)
                    sbSQL.append(" asc ");
                else
                    sbSQL.append(" desc ");
            }

            System.out.println(sbSQL+"^^^^^&&&&&&&&&&&&&&&&&^^^^^^^^^");
            transPS = transConn.prepareStatement(sbSQL.toString());
            transRS = transPS.executeQuery();
            ResultSetMetaData rsmd = transRS.getMetaData();
            Vector oneGroup;
            for (; transRS.next(); vectTemp.addElement(oneGroup))
            {
                oneGroup = new Vector();
                for (int i = 0; i < rsmd.getColumnCount(); i++)
                    oneGroup.addElement(transRS.getString(i + 1));

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
        return vectTemp.size() > 0 ? vectTemp : null;
    }

    /**
     * 根据用户信息，排序条件查询用户
     * 
     * @param userInfoCondition
     * @param orderCondition
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
    public Vector findUserByCondition(Sys_UserInfo userInfoCondition, long orderCondition, long lasc) throws SQLException, ITreasuryDAOException
    {
        Vector vectTemp = new Vector();
        try
        {
            // TODO Auto-generated method stub
            String name = userInfoCondition.getName();
            String login = userInfoCondition.getLoginNo();
            long officeid = userInfoCondition.getOfficeID();
            String currencyID = userInfoCondition.getCurrencyID();
            long statusID = userInfoCondition.getStatusID();
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append("select a.id as id,a.sname,a.sloginno,a.nofficeid,a.sCURRENCYId,b.sname,a.dtinput,a.ndepartmentid, a.CheckUserID,a.dtCheck,a.nstatusid,a.purviewtype,a.NINPUTUSERID,a.fingerPrintType  from userinfo a,userinfo b  where a.NINPUTUSERID=b.id(+)"); //jzw 2010-05-17
            if (name != null && name.length() > 0)
                sb.append(" and a.sname like '%" + name + "%'");
            if (login != null && login.length() > 0)
                sb.append(" and a.sloginno like '%" + login + "%'");
            if (officeid != -1L)
                sb.append(" and a.nofficeid=" + officeid);
            

            sb.append(" and a.nstatusid=" + statusID);
            if(statusID==SYSConstant.SysCheckStatus.UNCHECK)
            {
            	if (userInfoCondition.getCheckUserID() > 0)
                {
                    sb.append(" and a.nInputUserID != "+userInfoCondition.getCheckUserID()+"");
                }  
            	if (userInfoCondition.getInputUserID() > 0)
                {
                    sb.append(" and a.nInputUserID = "+userInfoCondition.getInputUserID()+"");
                }  
            }
            /*
            else
            {
            	sb.append(" and a.nstatusid!= 0 ");
            }
            */
            if (currencyID != null && currencyID != null)
            {
                sb.append(" and (a.scurrencyID like \'%," + currencyID + ",%\'");
                sb.append(" or a.scurrencyID like \'" + currencyID + ",%\'");
                sb.append(" or a.scurrencyID like \'%," + currencyID + "\'");
                sb.append(" or a.scurrencyID =\'" + currencyID + "\')");
            }
            if (orderCondition < 0)
            {
                sb.append(" order by a.id");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.USERNAME)
            {
                sb.append(" order by a.sname");
                if (lasc == 1)
                    sb.append(" asc ");
                else
                    sb.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.LOGINNAME)
            {
                sb.append(" order by a.sloginno");
                if (lasc == 1)
                    sb.append(" asc ");
                else
                    sb.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.OFFICE)
            {
                sb.append(" order by a.nofficeid");
                if (lasc == 1)
                    sb.append(" asc ");
                else
                    sb.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.CURRENCY)
            {
                sb.append(" order by a.sCURRENCYId");
                if (lasc == 1)
                    sb.append(" asc ");
                else
                    sb.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTUSER)
            {
                sb.append(" order by b.sname");
                if (lasc == 1)
                    sb.append(" asc ");
                else
                    sb.append(" desc ");
            }
            else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTDATE)
            {
                sb.append(" order by a.dtinput");
                if (lasc == 1)
                    sb.append(" asc ");
                else
                    sb.append(" desc ");
            }

            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            ResultSetMetaData rsmd = transRS.getMetaData();
            Vector oneGroup;
            
            for (; transRS.next(); vectTemp.addElement(oneGroup))
            {
                oneGroup = new Vector();
                for (int i = 0; i < rsmd.getColumnCount(); i++)
                    oneGroup.addElement(transRS.getString(i + 1));

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
        return vectTemp.size() > 0 ? vectTemp : null;

    }
    public long checkUserCertNo(String strLoginNo,String strDN) throws Exception
	{
        UserBean u = new UserBean();
		StringBuffer sb = new StringBuffer();
		String strDN1 = "";
		String strDN2 = "";
		long lResult = -1;
		long lMaxID = -1;
		try
		{ 
			sb.append("select sCertNo from userinfo where sLoginNo ='" + strLoginNo + "'");
			System.out.println(sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next())
			{
		    	System.out.println("===========has next trans");
			    if(strDN!=null && (!"".equals(strDN)))
			    {
			    	System.out.println("===========strDN is not null");
			        strDN1 = transRS.getString("sCertNo");
			       
			        if(strDN1!=null && (!"".equals(strDN1)))
			        {
			        	System.out.println("===========strDN::"+strDN+"::");
			        	System.out.println("===========strDN1::"+strDN1+"::");
			            strDN2 = u.getCN(strDN);
			            System.out.println("===========strDN2::"+strDN2+"::");
			            if(strDN2!=null && (!"".equals(strDN2)))
			            {
			            	 System.out.println("===========AAAAAAAAAA++++++++++1");
			                if (strDN1 ==strDN2 || strDN1.equals(strDN2))
			                {
			                    lResult = 1;
			                    System.out.println("===========AAAAAAAAAA++++++++++2");
			                } 
			            }
			            
			        } 
					
			    }
				  
				
			}
			
			sb.setLength(0);
			this.finalizeDAO();
			
			//
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lResult;
		//		ID, SNAME, SLOGINNO, SPASSWORD, NOFFICEID, NUSERGROUPID, NCURRENCYID, NINPUTUSERID, DTINPUT, NSTATUSID
	}

    public static void main(String[] args) throws ITreasuryDAOException
    {
        Connection transConn = null;
        try
        {
            transConn = Database.getConnection();
            transConn.setAutoCommit(false);
        }
        catch (Exception e)
        {
            throw new ITreasuryDAOException("数据库初使化异常发生", e);
        }
        Sys_UserDAO user = new Sys_UserDAO("userinfo", true, transConn);
        Sys_UserInfo info = new Sys_UserInfo();
        info.setId(1);
        info.setPassword("111");
        try
        {
            user.update(info);
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 覆盖基类的findByCondition 方法，由于中电子增加了密码的二进制字段，因而不能再用基类的方法
     * @author zntan
     * @param userInfoCondition
     * @return
     * @throws ITreasuryDAOException
     */
    public Collection findByCondition(Sys_UserInfo userInfoCondition) throws ITreasuryDAOException
    {
        Vector vectTemp = new Vector();
        try
        {
            // TODO Auto-generated method stub
            String name = userInfoCondition.getName();
            String login = userInfoCondition.getLoginNo();
            long officeid = userInfoCondition.getOfficeID();
            String currencyID = userInfoCondition.getCurrencyID();
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append("select * from userinfo  where nstatusid in (1,2) and ");
            String strs[] = this.getAllFieldNameBuffer(userInfoCondition, DAO_OPERATION_FIND);
            sb.append(strs[0]);
            System.out.println(" find by condition sql:"+sb.toString());
            transPS = transConn.prepareStatement(sb.toString());
            setPrepareStatementByDataEntity(userInfoCondition, DAO_OPERATION_FIND, strs[0]);
            transRS = transPS.executeQuery();
            while (transRS!= null && transRS.next())
            {
            	Sys_UserInfo info = new Sys_UserInfo();
            	info.setCertNo(transRS.getString("sCertNo"));
            	
            	//added by mzh_fu 证书CN
            	info.setCertCn(transRS.getString("sCertCn"));
            	
            	info.setCurrencyID(transRS.getString("sCurrencyID"));
            	info.setDepartmentID(transRS.getLong("nDepartmentID"));
            	info.setId(transRS.getLong("ID"));
            	info.setInput(transRS.getTimestamp("dtInput"));
            	info.setInputUserID(transRS.getLong("nInputUserID"));
            	info.setIsAdminUser(transRS.getLong("nIsAdminUser"));
            	info.setIsVirtualUser(transRS.getLong("nIsVirtualUser"));
            	info.setLoginNo(transRS.getString("sLoginNo"));
            	info.setName(transRS.getString("sName"));
            	info.setCode(transRS.getString("sCode"));
            	info.setOfficeID(transRS.getLong("nOfficeID"));
            	info.setPassword(transRS.getString("sPassword"));
            	info.setStatusID(transRS.getLong("nStatusID"));
            	vectTemp.add(info);
            }

            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	finalizeDAO();
        }
        return vectTemp.size() > 0 ? vectTemp : null;

    }
    public int findUserCount(Sys_UserInfo userInfoCondition) throws ITreasuryDAOException
    {
        int count = -1;
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append("select count(*) from userinfo  where nstatusid in (1,2) ");
            sb.append(" and nOfficeID = "+userInfoCondition.getOfficeID());
            sb.append(" and sCode = '"+userInfoCondition.getCode()+"' and id <> "+userInfoCondition.getId());
            System.out.println(" find by condition sql:"+sb.toString());
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            while (transRS!= null && transRS.next())
            {
            	count = transRS.getInt(1);
            }

            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	finalizeDAO();
        }
        return count;

    }
    
    public boolean validateLoginNo(String strLoginNo, long lID) throws ITreasuryDAOException
    {
        boolean bl = false;
        try
        {
            initDAO();
            
            StringBuffer sb = new StringBuffer();
            
            sb.append(" select * from userinfo where nstatusid in (1,2) and sloginno = '"+strLoginNo+"' ");
            if(lID > 0)
            {
            	sb.append(" and id != "+lID+" ");
            }
            
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            while (transRS!= null && transRS.next())
            {
            	bl = true;
            }

            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	finalizeDAO();
        }
        
        return bl;
    }
    
    /**
     * 覆盖基类的findByID 因为中电子增加二进制字段
     * @param id
     * @return
     * @throws ITreasuryDAOException
     */
    public ITreasuryBaseDataEntity findByID(long id,Class className)  throws ITreasuryDAOException
	{
    	Sys_UserInfo info = null; 
    	initDAO();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE id = " + id);
		String strSQL = buffer.toString();
		log.debug(strSQL);
		System.out.println(strSQL);
		
		try
		{
			prepareStatement(strSQL);
			executeQuery();
			while (transRS!= null && transRS.next())
			{
				info = new Sys_UserInfo();
				info.setCertNo(transRS.getString("sCertNo"));
				//info.setCurrencyID(transRS.getString("sCurrencyID"));
				info.setDepartmentID(transRS.getLong("nDepartmentID"));
				info.setId(transRS.getLong("ID"));
				info.setInput(transRS.getTimestamp("dtInput"));
				info.setInputUserID(transRS.getLong("nInputUserID"));
				info.setIsAdminUser(transRS.getLong("nIsAdminUser"));
				info.setIsVirtualUser(transRS.getLong("nIsVirtualUser"));
				info.setLoginNo(transRS.getString("sLoginNo"));
				info.setName(transRS.getString("sName"));
				info.setCode(transRS.getString("sCode"));
				info.setOfficeID(transRS.getLong("nOfficeID"));
				info.setPassword(transRS.getString("sPassword"));
				info.setStatusID(transRS.getLong("nStatusID"));
				//Added by zwsun, 2007-06-13, 权限分离
				info.setPurviewType(transRS.getLong("purviewType"));
				//Added by jiangqi, 2011-01-24, 是否是指纹管理员
				info.setFingerPrintType(transRS.getLong("fingerPrintType"));
				
				//added by mzh_fu 2008/02/14
				info.setCertCn(transRS.getString("sCertCn"));
				
				info.setCurrencyID(getClientCurrencyIDs(info));
			}
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new ITreasuryDAOException(" findById 出错", e1);
		}
		finalizeDAO();
		return info;
    }
    /* 根据用户登录名查询用户信息
     * 
     * @param loginName
     * @return @throws
     *         ITreasuryDAOException
     */
    public long chekXao(Sys_UserInfo info) throws ITreasuryDAOException
    {
 	   long id = -1;        
        try
        {   
 			initDAO();
 			StringBuffer sqlstr = new StringBuffer();
 			sqlstr.append(" update userinfo ");
 			sqlstr.append(" set nStatusID = "+info.getStatusID());
 			sqlstr.append(" , CheckUserID = "+info.getCheckUserID());
 			sqlstr.append(" , dtChangePassword = to_date('"+DataFormat.formatDate(info.getChangePassword(),2));
 			sqlstr.append("','YYYY-MM-DD:HH24:MI:SS')");
 			if(info.getCheck()!=null)
 			{            	
            		sqlstr.append(" , dtCheck = to_date('"+DataFormat.formatDate(DataFormat.getDateTime(String.valueOf(info.getCheck())),1)+"' ,'yyyy-mm-dd' ) "); 
            	} 
 	       sqlstr.append(" where id=" + info.getId());
 	       System.out.println("============zfa========="+sqlstr.toString());
 	       transPS = transConn.prepareStatement(sqlstr.toString());
            transPS.executeQuery();
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
        return id;
    }
   
    public ArrayList findUserAuthorityByCondition(Sys_UserAuthorityInfo conditionInfo)throws ITreasuryDAOException
    {
    	ArrayList list = new ArrayList();
    	Sys_UserAuthorityInfo authorityInfo = null;
    	StringBuffer sql = new StringBuffer();
    	try
    	{
    		initDAO();
    		sql.append(" select * from sys_userauthority ");
    		sql.append(" where 2>1 ");
    		if(conditionInfo.getId()>-1)
    		{
    			sql.append(" and id="+conditionInfo.getId());
    		}
    		if(conditionInfo.getUserId()>-1)
    		{
    			sql.append(" and userid="+conditionInfo.getUserId());
    		}
    		if(conditionInfo.getAuthorizedOfficeId()>-1)
    		{
    			sql.append(" and authorizedofficeid="+conditionInfo.getAuthorizedOfficeId());
    		}
    		if(conditionInfo.getNStatusId()>-1)
    		{
    			sql.append(" and nstatusid ="+conditionInfo.getNStatusId());
    		}
    		
    		sql.append(" order by id ");
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS=transPS.executeQuery();
    		while(transRS.next())
    		{
    			authorityInfo = new Sys_UserAuthorityInfo();
    			authorityInfo.setId(transRS.getLong("id"));
    			authorityInfo.setSCurrencyId(transRS.getString("scurrencyid"));
    			authorityInfo.setUserResponsibility(transRS.getLong("userresponsibility"));
    			authorityInfo.setNStatusId(transRS.getLong("nstatusid"));
    			authorityInfo.setAuthorizedOfficeId(transRS.getLong("authorizedofficeid"));
    			list.add(authorityInfo);
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
    
    public boolean findDuty(long lUserID) throws ITreasuryDAOException
    {
    	StringBuffer sql = new StringBuffer();
    	boolean jobAssion = false;
    	try
    	{
    		initDAO();
    		sql.append(" select * ");
    		sql.append(" from MG_R_USER_DUTY_AGENCY m ");
    		sql.append(" where ");   
    		if(lUserID>0)
    		{
    			sql.append(" m.userid ="+lUserID);
    		}
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS=transPS.executeQuery();
    		if(transRS.next())
    		{
    			jobAssion = true;
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		finalizeDAO();
    	} 
    	return jobAssion;
    }
    
    public long findOfficeByModule(QueryOfficeInfo queryOfficeInfo)throws ITreasuryDAOException
    {
    	long lOfficeID = -1;
    	StringBuffer sql = new StringBuffer();
    	try
    	{
    		initDAO();
    		sql.append(" select distinct s.authorizedofficeid ");
    		sql.append(" from sys_userauthority s, ");
    		sql.append(" sys_group_of_user g, ");
    		sql.append(" sys_privilege_of_group spou, ");
    		sql.append(" sys_privilege sp ");
    		sql.append(" where s.userid = g.userid ");
    		sql.append(" and s.authorizedofficeid = g.officeid ");
    		sql.append(" and sp.officeid = g.officeid ");
    		sql.append(" and g.groupid = spou.groupid ");
    		sql.append(" and spou.privilegeid = sp.id ");
    		sql.append(" and s.nstatusid ="+SYSConstant.SysAuthority.ALREADYAUTHORITY);
    		if(queryOfficeInfo.getLModelID()>0)
    		{
    			sql.append(" and sp.moduleid ="+queryOfficeInfo.getLModelID());
    		}
    		if(queryOfficeInfo.getLUserID()>0)
    		{
    			sql.append(" and s.userid ="+queryOfficeInfo.getLUserID());
    		}
    		if(queryOfficeInfo.getLOfficeID()>0)
    		{
    			sql.append(" and s.authorizedofficeid ="+queryOfficeInfo.getLOfficeID());
    		}
    		sql.append(" order by authorizedofficeid ");
    		log.info("sql="+sql.toString());
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS=transPS.executeQuery();
    		while(transRS.next())
    		{
    			lOfficeID = transRS.getLong("authorizedofficeid");
    			break;
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		finalizeDAO();
    	}     	
    	return lOfficeID;
    }
    
    //add by dwj 20111101 通过用户ID和用户办事处ID获得用户币种
    public String getClientCurrencyIDs(Sys_UserInfo info) throws ITreasuryDAOException, SQLException
    {
    	StringBuffer sb = new StringBuffer();
    	StringBuffer sqlsb = new StringBuffer();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	Connection conn = null;
    	sqlsb.append(" select b.curencyid curencyid from mg_r_user_authorty a,mg_r_user_authorty_currency b \n");
    	sqlsb.append(" where a.scurrencyid = b.id  ");
    	sqlsb.append(" and a.userid = " + info.getId());
    	sqlsb.append(" and a.orgid = " + info.getOfficeID());
    	try {
    		conn =  Database.getConnection();
    		ps = conn.prepareStatement(sqlsb.toString());
    		rs = ps.executeQuery();
    		while(rs.next())
    		{
    			sb.append(rs.getString("curencyid"));
    			sb.append(",");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException(" 获得用户币种 出错", e);
		}finally{
			
			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (ps != null) {
				ps.close();
				ps = null;
			}

			if (conn != null) {
				conn.close();
				conn = null;
			}
			
		}
    	return sb.length()<1?"":sb.substring(0, sb.length()-1);
    	
    }
    //end add by dwj 20111101
    
    public String queryUserSQL(Sys_UserInfo userInfoCondition, long groupId, long orderCondition, long lasc){

        String name = userInfoCondition.getName();
        String login = userInfoCondition.getLoginNo();
        long officeid = userInfoCondition.getOfficeID();
        String currencyID = userInfoCondition.getCurrencyID();
        long statusID = userInfoCondition.getStatusID();

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select a.id as id,a.sname,a.sloginno,a.nofficeid,a.sCURRENCYId,b.sname as sname1,a.dtinput,a.ndepartmentid, a.CheckUserID,a.dtCheck,a.nstatusid,a.purviewtype,a.ninputuserid,a.fingerPrintType from userinfo a,userinfo b  where  a.NINPUTUSERID=b.id"); //jzw 2010-05-17
        sbSQL.append("   and a.id in(select userid from sys_group_of_user where groupid=" + groupId + ")");
        if (name.length() > 0)
        {

            sbSQL.append(" and a.sname like '%" + name + "%'");
        }
        if (login.length() > 0)
        {
            sbSQL.append(" and a.sloginno like '%" + login + "%'");
        }
        if (officeid != -1L)
        {
            sbSQL.append(" and a.nofficeid=" + officeid);
        }
        if(statusID>0)
        {
        	sbSQL.append(" and a.nstatusid=" + statusID);
        }
        else
        {
        	sbSQL.append(" and a.nstatusid!= 0 ");
        }
        if (currencyID != null)
        {
            sbSQL.append(" and (a.scurrencyID like \'%," + currencyID + ",%\'");
            sbSQL.append(" or a.scurrencyID like \'" + currencyID + ",%\'");
            sbSQL.append(" or a.scurrencyID like \'%," + currencyID + "\'");
            sbSQL.append(" or a.scurrencyID =\'" + currencyID + "\')");
        }
        if (orderCondition < 0)
        {
            sbSQL.append(" order by a.id");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.USERNAME)
        {
            sbSQL.append(" order by a.sname");
            if (lasc == 1)
                sbSQL.append(" asc ");
            else
                sbSQL.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.LOGINNAME)
        {
            sbSQL.append(" order by a.sloginno");
            if (lasc == 1)
                sbSQL.append(" asc ");
            else
                sbSQL.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.OFFICE)
        {
            sbSQL.append(" order by a.nofficeid");
            if (lasc == 1)
                sbSQL.append(" asc ");
            else
                sbSQL.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.CURRENCY)
        {
            sbSQL.append(" order by a.sCURRENCYId");
            if (lasc == 1)
                sbSQL.append(" asc ");
            else
                sbSQL.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTUSER)
        {
            sbSQL.append(" order by b.sname");
            if (lasc == 1)
                sbSQL.append(" asc ");
            else
                sbSQL.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTDATE)
        {
            sbSQL.append(" order by a.dtinput");
            if (lasc == 1)
                sbSQL.append(" asc ");
            else
                sbSQL.append(" desc ");
        }

    	return sbSQL.toString();
    }
    
    public String queryUserSQL( Sys_UserInfo userInfoCondition, long orderCondition, long lasc){

    	 // TODO Auto-generated method stub
        String name = userInfoCondition.getName();
        String login = userInfoCondition.getLoginNo();
        long officeid = userInfoCondition.getOfficeID();
        String currencyID = userInfoCondition.getCurrencyID();
        long statusID = userInfoCondition.getStatusID();
       
        StringBuffer sb = new StringBuffer();
        sb.append("select a.id as id,a.sname,a.sloginno,a.nofficeid,a.sCURRENCYId,b.sname as sname1,a.dtinput,a.ndepartmentid, a.CheckUserID,a.dtCheck,a.nstatusid,a.purviewtype,a.NINPUTUSERID,a.fingerPrintType  from userinfo a,userinfo b  where a.NINPUTUSERID=b.id(+)"); //jzw 2010-05-17
        if (name != null && name.length() > 0)
            sb.append(" and a.sname like '%" + name + "%'");
        if (login != null && login.length() > 0)
            sb.append(" and a.sloginno like '%" + login + "%'");
        if (officeid != -1L)
            sb.append(" and a.nofficeid=" + officeid);
        

        sb.append(" and a.nstatusid=" + statusID);
        if(statusID==SYSConstant.SysCheckStatus.UNCHECK)
        {
        	if (userInfoCondition.getCheckUserID() > 0)
            {
                sb.append(" and a.nInputUserID != "+userInfoCondition.getCheckUserID()+"");
            }  
        	if (userInfoCondition.getInputUserID() > 0)
            {
                sb.append(" and a.nInputUserID = "+userInfoCondition.getInputUserID()+"");
            }  
        }
        if (currencyID != null && currencyID != null)
        {
            sb.append(" and (a.scurrencyID like \'%," + currencyID + ",%\'");
            sb.append(" or a.scurrencyID like \'" + currencyID + ",%\'");
            sb.append(" or a.scurrencyID like \'%," + currencyID + "\'");
            sb.append(" or a.scurrencyID =\'" + currencyID + "\')");
        }
        if (orderCondition < 0)
        {
            sb.append(" order by a.id");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.USERNAME)
        {
            sb.append(" order by a.sname");
            if (lasc == 1)
                sb.append(" asc ");
            else
                sb.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.LOGINNAME)
        {
            sb.append(" order by a.sloginno");
            if (lasc == 1)
                sb.append(" asc ");
            else
                sb.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.OFFICE)
        {
            sb.append(" order by a.nofficeid");
            if (lasc == 1)
                sb.append(" asc ");
            else
                sb.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.CURRENCY)
        {
            sb.append(" order by a.sCURRENCYId");
            if (lasc == 1)
                sb.append(" asc ");
            else
                sb.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTUSER)
        {
            sb.append(" order by b.sname");
            if (lasc == 1)
                sb.append(" asc ");
            else
                sb.append(" desc ");
        }
        else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTDATE)
        {
            sb.append(" order by a.dtinput");
            if (lasc == 1)
                sb.append(" asc ");
            else
                sb.append(" desc ");
        }

    	return sb.toString();
    }
   
}