package com.iss.itreasury.custommenu.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.bill.mywork.dao.BillDraftOutActionDao;
import com.iss.itreasury.bill.mywork.dao.BillMyWorkDao;
import com.iss.itreasury.bill.mywork.dao.BillTransDiscountActonDao;
import com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo;
import com.iss.itreasury.craftbrother.mywork.dao.CraMyWorkDao;
import com.iss.itreasury.craftbrother.mywork.dao.CraTransActionDao;
import com.iss.itreasury.craftbrother.mywork.dao.CreditSettingMyWorkDao;
import com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInfo;
import com.iss.itreasury.craftbrother.mywork.dataentity.CreditSettingMyWorkInfo;
import com.iss.itreasury.creditrating.mywork.dao.CreRtMyWorkDao;
import com.iss.itreasury.creditrating.mywork.dao.CreRtTransActionDao;
import com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo;
import com.iss.itreasury.custommenu.dataentity.CustomMenu_PrivilegeInfo;
import com.iss.itreasury.custommenu.dataentity.User_customprivilegeInfo;
import com.iss.itreasury.custommenu.dataentity.User_customprivilegedetailInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.mywork.dao.SettInutWorkDao;
import com.iss.itreasury.evoucher.mywork.dataentity.SettInutWorkInfo;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.mywork.dao.LoanContractPlanChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanContractRateChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanContractRisklevelChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanContractStatusChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanCreditDao;
import com.iss.itreasury.loan.mywork.dao.LoanMyWorkDao;
import com.iss.itreasury.loan.mywork.dao.LoanTransActionDao;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.system.privilege.dataentity.Sys_PrivilegeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

public class CustomMenudao {
	/**当前DAO的使用的数据库连接*/
	protected Connection transConn = null;
	
	/**当前DAO的使用的结果集*/
	protected ResultSet transRS = null;
	/**当前DAO的使用的PreparedStatement*/
	protected PreparedStatement transPS = null;
	/**是否数据库连接是自维护的（即不是容器维护的）*/
	private boolean isSelfManagedConn = false;
	
	/**
	 * DAO初使化类，在使用具体的DAO操作前，必须先调用此方法!!!!　
	 * @param 
	 * @param 　
	 * @return
	 * @throws SecuritiesDAOException
	 */			
	protected void initDAO() throws ITreasuryDAOException{
		try {
			if(transConn == null)
				transConn = Database.getConnection();
		} catch (Exception e) {
			throw new ITreasuryDAOException("数据库初使化异常发生",e);
		}
	}
    /**
     * @param userID
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findModulesByUser(long userID) throws ITreasuryDAOException, SQLException
	{
    	ArrayList a = null;
        try
        {
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
            
            System.out.println("查询用户权限的sql为            " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            
            a = new ArrayList();
            while (transRS.next())
            {
            	a.add(new Long(transRS.getLong("moduleID")));	
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
    	
    	
    	return a;
	}
    
    /**
     * @param moduleID
     * @return
     * @throws SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesByMain(long moduleID,long userID) throws SQLException, ITreasuryDAOException
    {
    	
    		Collection coll = new ArrayList();
    	  CustomMenu_PrivilegeInfo custommenu_privilegeinfo = null;
    	  HashMap m = new HashMap();
        try
        {
        	User_customprivilegeInfo uInfo = getUser_customprivilegeInfo(userID);
        	if(uInfo!=null)
        	{
        		m = getUser_customprivilegeDetial(uInfo.getId());
        	}
            initDAO();
            StringBuffer sb = new StringBuffer();

            sb.append("select t.* from sys_privilege t where plevel=1");
            sb.append(" and t.moduleid ="+ moduleID);
            sb.append(" and t.statusId=");
            sb.append(Constant.RecordStatus.VALID);
            sb.append(" order by t.id");
            transPS = transConn.prepareStatement(sb.toString());
          
            transRS = transPS.executeQuery();
            if (transRS.next())
            {
            	custommenu_privilegeinfo = new CustomMenu_PrivilegeInfo();
            	custommenu_privilegeinfo.setId(transRS.getLong("id"));
            	custommenu_privilegeinfo.setMenuUrl(transRS.getString("menuUrl"));
            	custommenu_privilegeinfo.setName(transRS.getString("name"));
            	custommenu_privilegeinfo.setOfficeID(transRS.getLong("officeId"));
            	custommenu_privilegeinfo.setPlevel(transRS.getLong("plevel"));
            	custommenu_privilegeinfo.setPrivilegeNo(transRS.getString("privilegeNo"));
            	custommenu_privilegeinfo.setModuleID(moduleID);
            	if(uInfo!=null)
            	{
            		if(m!=null && m.size()>0)
            		{
            			User_customprivilegedetailInfo udInfo =(User_customprivilegedetailInfo) m.get(new Long(custommenu_privilegeinfo.getId()));
            			if(udInfo!=null)
            			{
            				if(udInfo.getUser_definedName()!=null)
            				{
            					custommenu_privilegeinfo.setByname(udInfo.getUser_definedName());
            				}
            				else
            				{
            					custommenu_privilegeinfo.setByname("#");
            				}
	            			
	                    	custommenu_privilegeinfo.setUId(udInfo.getId());
            			}
            			else
            			{
            				custommenu_privilegeinfo.setByname("#");
            				custommenu_privilegeinfo.setUId(-1);
            			}
            		}
            		
            		else
            		{
            			custommenu_privilegeinfo.setByname("#");
        				custommenu_privilegeinfo.setUId(-1);
            		}
            	
            	}
            	else
    			{
    				custommenu_privilegeinfo.setByname("#");
    				custommenu_privilegeinfo.setUId(-1);
    			}
            	coll.add(custommenu_privilegeinfo);
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
        return coll;
    }
    
    /**
     * @param moduleID
     * @return
     * @throws SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesByMainView(long moduleID,long userID) throws SQLException, ITreasuryDAOException
    {
    	
    		Collection coll = new ArrayList();
    		 Sys_PrivilegeInfo sys_privilegeEntity;
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();

            sb.append("select t.*,uscd.USER_DEFINEDNAME,uscd.id uscdid from sys_privilege t,user_customprivilege usc,user_customprivilegedetail uscd where plevel=1");
            sb.append(" and t.moduleid ="+ moduleID);
            sb.append(" and t.statusId=");
            sb.append(Constant.RecordStatus.VALID);
            sb.append(" and uscd.privilegeid=t.id");
            sb.append(" and usc.id=uscd.USER_CUSTOMPRIVILEGEID");
            sb.append(" and usc.userid="+userID);
            sb.append(" order by t.id");
            transPS = transConn.prepareStatement(sb.toString());
          
            transRS = transPS.executeQuery();
            if (transRS.next())
            {
            	 sys_privilegeEntity = new Sys_PrivilegeInfo();
                 sys_privilegeEntity.setId(transRS.getLong("id"));
                 sys_privilegeEntity.setMenuUrl((transRS.getString("menuurl")!=null)?(transRS.getString("menuurl").indexOf("?")>0?transRS.getString("menuurl")+"&UmoduleId=" + moduleID:transRS.getString("menuurl")+"?UmoduleId="+ moduleID):"");
                 sys_privilegeEntity.setName(transRS.getString("USER_DEFINEDNAME")!=null && !transRS.getString("USER_DEFINEDNAME").equals("")?transRS.getString("USER_DEFINEDNAME"):transRS.getString("name"));
                 sys_privilegeEntity.setPlevel(transRS.getLong("plevel"));
                 sys_privilegeEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
                 sys_privilegeEntity.setModuleID(moduleID);
                 coll.add(sys_privilegeEntity);
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
        return coll;
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
    public Collection getPrivilegeOfUser(long userId, long moduleID) throws ITreasuryDAOException, SQLException
    {

        Vector vPrivilegeList = new Vector();
        HashMap m = new HashMap();
        try
        {
        	User_customprivilegeInfo uInfo = getUser_customprivilegeInfo(userId);
        	if(uInfo!=null)
        	{
        		m = getUser_customprivilegeDetial(uInfo.getId());
        	}
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
            sb.append(" order by privilegeNo ");
            //System.out.println("查询用户权限的sql为            " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            CustomMenu_PrivilegeInfo custommenu_privilegeinfo;

            while (transRS.next())
            {
            	custommenu_privilegeinfo = new CustomMenu_PrivilegeInfo();
            	custommenu_privilegeinfo.setId(transRS.getLong("id"));
            	custommenu_privilegeinfo.setMenuUrl(transRS.getString("menuurl"));
            	custommenu_privilegeinfo.setName(transRS.getString("name"));
            	custommenu_privilegeinfo.setPlevel(transRS.getLong("plevel"));
            	custommenu_privilegeinfo.setPrivilegeNo(transRS.getString("privilegeNo"));
            	custommenu_privilegeinfo.setModuleID(moduleID);
            	if(uInfo!=null)
            	{
            		if(m!=null && m.size()>0)
            		{
            			User_customprivilegedetailInfo udInfo =(User_customprivilegedetailInfo) m.get(new Long(custommenu_privilegeinfo.getId()));
            			if(udInfo!=null)
            			{
            				if(udInfo.getUser_definedName()!=null)
            				{
            					custommenu_privilegeinfo.setByname(udInfo.getUser_definedName());
            				}
            				else
            				{
            					custommenu_privilegeinfo.setByname("#");
            				}
	                    	custommenu_privilegeinfo.setUId(udInfo.getId());
            			}
            			else
            			{
            				custommenu_privilegeinfo.setByname("#");
            				custommenu_privilegeinfo.setUId(-1);
            			}
            		}
            		else
            		{
            			custommenu_privilegeinfo.setByname("#");
        				custommenu_privilegeinfo.setUId(-1);
            		}
            	
            	}
            	else
    			{
    				custommenu_privilegeinfo.setByname("#");
    				custommenu_privilegeinfo.setUId(-1);
    			}
                vPrivilegeList.add(custommenu_privilegeinfo);
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
     * @param uInfo
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public long  toSaveUser_customprivilege(User_customprivilegeInfo uInfo)throws ITreasuryDAOException, SQLException
    {
    	long lReturn = -1;
    	long lMaxID = -1;
    	try {
			initDAO();
			lMaxID = geSequenceID("SEQ_USER_CUSTOMPRIVILEGE");
			String sqlstr = "INSERT INTO user_customprivilege (ID,USERID,DMODULENAME,STATUSID) VALUES (" + lMaxID + "," + uInfo.getUserId() + ",'" + uInfo.getDModuleName() + "'," + uInfo.getStatusId() + ")";
            Statement stmt = transConn.createStatement();
            //System.out.println(sqlstr);
            stmt.executeUpdate(sqlstr);
            lReturn = lMaxID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
        {
	            finalizeDAO();
        }
		 return lReturn;
    }
    
    /**
     * @param uInfo
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public long  toUpdateUser_customprivilege(User_customprivilegeInfo uInfo)throws ITreasuryDAOException, SQLException
    {
    	long lReturn = -1;
    	try {
			initDAO();
			String sqlstr = "UPDATE user_customprivilege set USERID=?,DMODULENAME=?,STATUSID=? where ID=?";
			System.out.println(sqlstr);
			transPS = transConn.prepareStatement(sqlstr.toString());
			int index = 1;
			transPS.setLong(index++, uInfo.getUserId());
			transPS.setString(index++, uInfo.getDModuleName());
			transPS.setLong(index++, uInfo.getStatusId());
			transPS.setLong(index++, uInfo.getId());
			int i = transPS.executeUpdate();
			if (i > 0) {
				 lReturn = uInfo.getId();
			}
           
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
        {
	            finalizeDAO();
        }
		 return lReturn;
    }
    
    /**
     * @param uId
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public long deleteUser_customprivilege(long uId) throws ITreasuryDAOException, SQLException
    {
    	long lReturn = -1;
    	
    	try {
			initDAO();
			String sqlstr = "delete user_customprivilege  t where t.id=" + uId;
            Statement stmt = transConn.createStatement();
           // System.out.println(sqlstr);
            stmt.executeUpdate(sqlstr);
            lReturn = uId;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
        {
	            finalizeDAO();
        }
		 return lReturn;
    	
    }
    
    /**
     * @param user_customprivilegeid
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public long deleteUser_customprivilegedetail(long user_customprivilegeid) throws ITreasuryDAOException, SQLException
    {
    	long lReturn = -1;
    	
    	try {
			initDAO();
			String sqlstr = "delete user_customprivilegedetail  t where t.USER_CUSTOMPRIVILEGEID=" + user_customprivilegeid;
            Statement stmt = transConn.createStatement();
            //System.out.println(sqlstr);
            stmt.executeUpdate(sqlstr);
            lReturn = user_customprivilegeid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
        {
	            finalizeDAO();
        }
		 return lReturn;
    	
    }
    /**
     * @param udInfo
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public long toSaveUser_customprivilegedetail(User_customprivilegedetailInfo udInfo) throws ITreasuryDAOException, SQLException
    {
    	long lReturn = -1;
    	long lMaxID = -1;
    	try {
			initDAO();
			lMaxID = geSequenceID("seq_user_customprivilegedetail");
			String sqlstr = "INSERT INTO user_customprivilegedetail (ID,PRIVILEGEID,USER_CUSTOMPRIVILEGEID,USER_DEFINEDNAME,STATUSID) VALUES (" + lMaxID + "," + udInfo.getPrivilegeId()+ "," + udInfo.getUser_customprivilegeId() + ",'" + udInfo.getUser_definedName() + "'," + udInfo.getStatusId() + ")";
            Statement stmt = transConn.createStatement();
            //System.out.println(sqlstr);
            stmt.executeUpdate(sqlstr);
            lReturn = lMaxID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
        {
	            finalizeDAO();
        }
		 return lReturn;
    }
    /**
     * @param userId
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public User_customprivilegeInfo getUser_customprivilegeInfo(long userId) throws ITreasuryDAOException, SQLException
    {
    	User_customprivilegeInfo uInfo = null;
      try
      {
          initDAO();
          StringBuffer sb = new StringBuffer();

          sb.append("select * from user_customprivilege where 1=1");
          sb.append(" and userid ="+ userId);
          sb.append(" and statusId=");
          sb.append(Constant.RecordStatus.VALID);
          transPS = transConn.prepareStatement(sb.toString());
        
          transRS = transPS.executeQuery();
          if (transRS.next())
          {
        	  uInfo = new User_customprivilegeInfo();
        	  uInfo.setId(transRS.getLong("id"));
        	  uInfo.setUserId(transRS.getLong("USERID"));
        	  uInfo.setDModuleName(transRS.getString("DMODULENAME"));
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
      return uInfo;
    	
    }
    
    /**
     * @param userId
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public HashMap getUser_customprivilegeDetial(long user_customprivilegeid) throws ITreasuryDAOException, SQLException
    {
    	HashMap  m = new HashMap();
    	User_customprivilegedetailInfo uInfo = null;
      try
      {
          initDAO();
          StringBuffer sb = new StringBuffer();

          sb.append("select * from user_customprivilegedetail where 1=1");
          sb.append(" and USER_CUSTOMPRIVILEGEID ="+ user_customprivilegeid);
          sb.append(" and statusId=");
          sb.append(Constant.RecordStatus.VALID);
          transPS = transConn.prepareStatement(sb.toString());
        
          transRS = transPS.executeQuery();
          while (transRS.next())
          {
        	  uInfo = new User_customprivilegedetailInfo();
        	  uInfo.setId(transRS.getLong("id"));
        	  uInfo.setPrivilegeId(transRS.getLong("PRIVILEGEID"));
        	  uInfo.setUser_customprivilegeId(transRS.getLong("USER_CUSTOMPRIVILEGEID"));
        	  uInfo.setUser_definedName(transRS.getString("USER_DEFINEDNAME"));
        	  m.put(new Long(uInfo.getPrivilegeId()), uInfo);
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
      return m;
    	
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
    public Collection getPrivilegeOfUserView(long userId, long moduleID) throws ITreasuryDAOException, SQLException
    {

        Vector vPrivilegeList = new Vector();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();
            sb.append(" select distinct sp.*,uscd.USER_DEFINEDNAME \n");
            sb.append(" from sys_privilege sp,sys_group_of_user sgou, sys_privilege_of_group spou,sys_group sgroup, user_customprivilege usc,user_customprivilegedetail uscd\n");
            sb.append(" where sp.id=spou.privilegeId and spou.groupid=sgou.groupid and  spou.groupid = sgroup.id \n");
            sb.append("   and sgou.userid= ");
            sb.append(userId);
            sb.append("   and usc.userid= ");
            sb.append(userId);
            sb.append("  and uscd.user_customprivilegeid=usc.id");
            sb.append("  and uscd.privilegeid=sp.id");
            sb.append("  and sp.moduleId= ");
            sb.append(moduleID);
            sb.append("  and sp.statusid= ");
            sb.append(Constant.RecordStatus.VALID);
            //add by wjliu -2007-5-31 加一个用户组为已复核的条件
            sb.append("  and sgroup.nstatusid = ");
            sb.append(Constant.RecordStatus.VALID);
            //modify by leiyang date 2007/08/09
            sb.append("  and sp.plevel>=1");
            sb.append("  and usc.id=uscd.USER_CUSTOMPRIVILEGEID");
            sb.append("  and usc.userid="+userId);
            sb.append(" order by privilegeNo ");
           // System.out.println("查询用户权限的sql为            " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transRS = transPS.executeQuery();
            Sys_PrivilegeInfo sys_privilegeEntity;

            while (transRS.next())
            {
                sys_privilegeEntity = new Sys_PrivilegeInfo();
                sys_privilegeEntity.setId(transRS.getLong("id"));
                sys_privilegeEntity.setMenuUrl((transRS.getString("menuurl")!=null)?(transRS.getString("menuurl").indexOf("?")>0?transRS.getString("menuurl")+"&UmoduleId=" + moduleID:transRS.getString("menuurl")+"?UmoduleId="+ moduleID):"");
                sys_privilegeEntity.setName(transRS.getString("USER_DEFINEDNAME")!=null && !transRS.getString("USER_DEFINEDNAME").equals("")?transRS.getString("USER_DEFINEDNAME"):transRS.getString("name"));
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

    /**
     * 根据sequence的命名规则获取下一个可用的ID值
     * sequence命名规则: seq_+tablename，例如,SEC_Remark表的sequence为SEQ_SEC_Remark
    * @param
    * @param
    * @return 下一个ID
    * @throws ITreasuryDAOException
     */
    protected long geSequenceID(String strSeqName)  throws ITreasuryDAOException{

	/**
	 * 此方法只能在DAO中被调用，即不重新创建数据库资源，因此也不需要 关闭数据库资源
	 */
	long id = -1;
	String sql = "SELECT " + strSeqName + ".nextval nextid from dual";
	//prepareStatement(sql);
	PreparedStatement localPS = null;
	ResultSet localRS = null;
	try {	//内部维护RS和PS，否则将会产生冲突,但Connection使用同一个
        if( transConn != null && transConn.isClosed() )
        {
            try {
                transConn = Database.getConnection();
            }
            catch ( Exception e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }	
		localPS = transConn.prepareStatement(sql);
		localRS = localPS.executeQuery();

		if (localRS.next())
		{
			id = localRS.getLong("nextid");
		}
		if(localRS != null)
			localRS.close();
		if(localPS != null)
			localPS.close();
	} catch (SQLException e) {
		new ITreasuryDAOException("数据库获取ID产异常",e);
		}		
	
		return id;		
}
    	
    /**
	 * DAO结束操作，在DAO操作的结尾必须调用此方法!!!!!!!!　
	 * @param 
	 * @param 　
	 * @return
	 * @throws ITreasuryDAOException
	 */				
	protected void finalizeDAO() throws ITreasuryDAOException{
		try {
			if (transRS != null) {
				transRS.close();
				transRS = null;
			}

			if (transPS != null) {
				transPS.close();
				transPS = null;
			}

			if (transConn != null && !isSelfManagedConn) {
				transConn.close();
				transConn = null;
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库关闭异常发生",e);			
		}
	}
	
	/**
	 * 查询电子单据柜 待提交的业务条数
	 * @param qInfo
	 * @return
	 */
	public int querySumFirstMenuForMoudle16(SettInutWorkInfo qInfo){
    	Collection c_Return = null;
    	int ss = 0;
        try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	qInfo.setQueryPurpose(dao.QUERYCURRENTWORK);
        	c_Return = dao.queryMyWork(qInfo);		
        	Vector v = (Vector)c_Return;
        	ss = v.size();
    		this.updatePriMenu(ss,qInfo.getModuleID(),false);
        } 
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        return ss;
      
        
    }
	
	/**
	 * 查询电子单据柜 已拒绝的业务条数
	 * @param qInfo
	 * @return
	 */
	public int querySumSecondMenuForMoudle16(SettInutWorkInfo qInfo,long ldesc,long lOrderField){
    	Collection c_Return = null;
    	int ss = 0;
        try
        {	SettInutWorkDao dao = new SettInutWorkDao();
     		PageLoader pageLoader = dao.queryRefuseWork(qInfo,ldesc,lOrderField);
     		ss = pageLoader.getPageLoaderInfo().getRowCount();
        } 
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        return ss;
      
        
    }
    
	/**
	 * 查询信用评级  待审批的业务条数
	 * @param qInfo
	 * @return
	 */
    public int querySumSecondMenuForMoudle23(CreRtMyWorkInfo qInfo){
    	int ss = 0;
    	try {
    		Object objReturn = null;
    		CreRtMyWorkDao dao = new CreRtTransActionDao();
    		objReturn = dao.queryMyWork(qInfo);	
    		System.out.println("creRtMyWorkInfo.getQueryPurpose():"+qInfo.getQueryPurpose());
    		Vector v = (Vector)objReturn;
		ss = v.size();
		this.updatePriMenu(ss,qInfo.getModuleID(),false);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ss;
    }
    
    /**
     * 查询信用评级  待提交的业务条数
     * @param qInfo
     * @return
     */
    public int querySumFirstMenuForMoudle23(CreRtMyWorkInfo qInfo){
    	int ss = 0;
    	try {
    	Object objReturn = null;
		CreRtMyWorkDao dao = new CreRtTransActionDao();
		objReturn = dao.queryMyWork(qInfo);
		Vector v = (Vector)objReturn;
		ss = v.size();
		this.updatePriMenu(ss,qInfo.getModuleID(),true);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ss;
    }
    
    /**
     * 查询信用评级  已拒绝的业务条数
     * @param qInfo
     * @return
     */
    public int querySumThirdMenuForMoudle23(CreRtMyWorkInfo qInfo){
    	int ss = 0;
    	try {
    	Object objReturn = null;
		CreRtMyWorkDao dao = new CreRtTransActionDao();
		objReturn = dao.queryMyWork(qInfo);
		PageLoader pageLoader = (PageLoader)objReturn;
		
		
		ss = pageLoader.getPageLoaderInfo().getRowCount();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ss;
    }
    
    /**
     * 查询贷款管理  待审批的业务条数
     * @param qInfo
     * @return
     */
    public int querySumSecondMenuForMoudle2(LoanMyWorkInfo qInfo){
    	int ss = 0;
    	try {
    		Object objReturn1 = null;
    	
		LoanMyWorkDao dao1 = new LoanContractStatusChangeDao();
		objReturn1 = dao1.queryMyWork(qInfo);
		Vector v1 = (Vector)objReturn1;
		
		Object objReturn2 = null;
		LoanMyWorkDao dao2 = new LoanTransActionDao();
		objReturn2 = dao2.queryMyWork(qInfo);	
		Vector v2 = (Vector)objReturn2;
		
		Object objReturn3 = null;
		LoanMyWorkDao dao3 = new LoanContractRisklevelChangeDao();
		objReturn3 = dao3.queryMyWork(qInfo);
		Vector v3 = (Vector)objReturn3;
		
		Object objReturn4 = null;
		LoanMyWorkDao dao4 = new LoanContractRateChangeDao();
		objReturn4 = dao4.queryMyWork(qInfo);
		Vector v4 = (Vector)objReturn4;
		
		Object objReturn5 = null;
		LoanMyWorkDao dao5 = new LoanCreditDao();
		objReturn5 = dao5.queryMyWork(qInfo);
		Vector v5 = (Vector)objReturn5;
		
		Object objReturn6 = null;
		LoanMyWorkDao dao6 = new LoanContractPlanChangeDao();
		objReturn6 = dao6.queryMyWork(qInfo);
		Vector v6 = (Vector)objReturn6;
		
		ss = v1.size()+v2.size()+v3.size()+v4.size()+v5.size()+v6.size();
		this.updatePriMenu(ss,qInfo.getModuleID(),false);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ss;
    }
    
    /**
     * 查询贷款管理  待提交的业务条数
     * @param qInfo
     * @return
     */
    public int querySumFirstMenuForMoudle2(LoanMyWorkInfo qInfo){
    	int ss = 0;
    	try {
    	Object objReturn1 = null;
		LoanMyWorkDao dao = new LoanContractStatusChangeDao();
		objReturn1 = dao.queryMyWork(qInfo);
		Vector v1 = (Vector)objReturn1;
		v1.size();
		
		Object objReturn2 = null;
		LoanMyWorkDao dao2 = new LoanTransActionDao();
		objReturn2 = dao2.queryMyWork(qInfo);	
		Vector v2 = (Vector)objReturn2;
		v2.size();
		
		Object objReturn3 = null;
		LoanMyWorkDao dao3 = new LoanContractPlanChangeDao();
		objReturn3 = dao3.queryMyWork(qInfo);
		Vector v3 = (Vector)objReturn3;
		v3.size();
		
		Object objReturn4 = null;
		LoanMyWorkDao dao4 = new LoanContractRateChangeDao();
		objReturn4 = dao4.queryMyWork(qInfo);
		Vector v4 = (Vector)objReturn4;
		v4.size();
		
		Object objReturn5 = null;
		LoanMyWorkDao dao5 = new LoanContractRisklevelChangeDao();
		objReturn5 = dao5.queryMyWork(qInfo);
		Vector v5 = (Vector)objReturn5;
		v5.size();
		
		Object objReturn6 = null;
		LoanMyWorkDao dao6 = new LoanCreditDao();
		objReturn6 = dao6.queryMyWork(qInfo);
		Vector v6 = (Vector)objReturn6;
		v6.size();
		
		ss = v1.size()+v2.size()+v3.size()+v4.size()+v5.size()+v6.size();
		this.updatePriMenu(ss,qInfo.getModuleID(),true);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ss;
    }
    
    /**
     * 查询贷款管理  已拒绝的业务条数
     * @param qInfo
     * @return
     */
    public int querySumThirdMenuForMoudle2(LoanMyWorkInfo qInfo){
    	int ss = 0;
    	int s1 = 0;
    	try {
    	Object objReturn1 = null;
		LoanMyWorkDao dao = new LoanContractStatusChangeDao();
		objReturn1 = dao.queryMyWork(qInfo);
		PageLoader v1 = (PageLoader)objReturn1;
		Object[] obj = (Object[])v1.listAll();
		if(obj != null){
			s1 = obj.length;
		}
		
		Object objReturn2 = null;
		LoanMyWorkDao dao2 = new LoanTransActionDao();
		objReturn2 = dao2.queryMyWork(qInfo);	
		PageLoader v2 = (PageLoader)objReturn2;
		int s2 = v2.getPageLoaderInfo().getRowCount();
		
		Object objReturn3 = null;
		LoanMyWorkDao dao3 = new LoanContractPlanChangeDao();
		objReturn3 = dao3.queryMyWork(qInfo);
		PageLoader v3 = (PageLoader)objReturn3;
		int s3 = v3.getPageLoaderInfo().getRowCount();
		
		Object objReturn4 = null;
		LoanMyWorkDao dao4 = new LoanContractRateChangeDao();
		objReturn4 = dao4.queryMyWork(qInfo);
		PageLoader v4 = (PageLoader)objReturn4;
		int s4 = v4.getPageLoaderInfo().getRowCount();
		
		Object objReturn5 = null;
		LoanMyWorkDao dao5 = new LoanContractRisklevelChangeDao();
		objReturn5 = dao5.queryMyWork(qInfo);
		PageLoader v5 = (PageLoader)objReturn5;
		int s5 = v5.getPageLoaderInfo().getRowCount();
		
		Object objReturn6 = null;
		LoanMyWorkDao dao6 = new LoanCreditDao();
		objReturn6 = dao6.queryMyWork(qInfo);
		PageLoader v6 = (PageLoader)objReturn6;
		int s6 = v6.getPageLoaderInfo().getRowCount();
		
		ss = s1+s2+s3+s4+s5+s6;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ss;
    }
    
    /**
     * 查询同业往来  待审批的业务条数
     * @param qInfo1
     * @param qInfo
     * @return
     */
    public int querySumSecendMenuForMoudle15(CraMyWorkInfo qInfo1,CreditSettingMyWorkInfo qInfo){
    	int ss = 0;
    	Object objReturn1 = null;
		CraMyWorkDao dao1 = new CraTransActionDao();
		try {
			objReturn1 = dao1.queryMyWork(qInfo1);
			Vector v1 = (Vector)objReturn1;
			v1.size();
		
		Object objReturn = null;
		CraMyWorkDao dao = new CreditSettingMyWorkDao();
		objReturn = dao.queryMyWork(qInfo);
		Vector v = (Vector)objReturn;
		v.size();	
		
		ss = v1.size()+v.size();
		this.updatePriMenu(ss,qInfo.getModuleID(),false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
    }
    
    /**
     * 查询同业往来  待提交的业务条数
     * @param qInfo1
     * @param qInfo
     * @return
     */
    public int querySumFirstMenuForMoudle15(CraMyWorkInfo qInfo1,CreditSettingMyWorkInfo qInfo){
    	int ss=0;
    	Object objReturn1 = null;
		CraMyWorkDao dao1 = new CraTransActionDao();
		try {
			objReturn1 = dao1.queryMyWork(qInfo1);
			Vector v1 = (Vector)objReturn1;
			v1.size();
		
		Object objReturn = null;
		CraMyWorkDao dao = new CreditSettingMyWorkDao();
		objReturn = dao.queryMyWork(qInfo);
		Vector v = (Vector)objReturn;
		v.size();	
		
		ss = v1.size()+v.size(); 
		this.updatePriMenu(ss,qInfo.getModuleID(),true);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
    }
    
    /**
     * 查询同业往来  已拒绝的业务条数
     * @param qInfo1
     * @param qInfo
     * @return
     */
    public int querySumThirdMenuForMoudle15(CraMyWorkInfo qInfo1,CreditSettingMyWorkInfo qInfo){
    	int ss = 0;
    	Object objReturn1 = null;
		CraMyWorkDao dao1 = new CraTransActionDao();
		try {
			objReturn1 = dao1.queryMyWork(qInfo1);
			PageLoader pageLoader = (PageLoader)objReturn1;
		
		Object objReturn = null;
		CraMyWorkDao dao = new CreditSettingMyWorkDao();
		objReturn = dao.queryMyWork(qInfo);
		PageLoader pageLoader1 = (PageLoader)objReturn;
		
		ss = pageLoader.getPageLoaderInfo().getRowCount()+pageLoader1.getPageLoaderInfo().getRowCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
    }
    
    /**
     * 查询票据管理  待审批的业务条数
     * @param qInfo
     * @return
     */
    public int querySumSecendMenuForMoudle10(BillMyWorkInfo qInfo){
    	int ss = 0;
    	Object objReturn1 = null;
		BillMyWorkDao dao1 = new BillDraftOutActionDao();
		try {
			objReturn1 = dao1.queryMyWork(qInfo);
			Vector v1 = (Vector)objReturn1;
			v1.size();
		

		Object objReturn = null;
				BillMyWorkDao dao = new BillTransDiscountActonDao();
				objReturn = dao.queryMyWork(qInfo);
				Vector v = (Vector)objReturn;
				v.size();	
				
		ss = v1.size()+v.size();
		this.updatePriMenu(ss,qInfo.getModuleID(),false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return ss;
    }
    
    /**
     * 查询票据管理  待提交的业务条数
     * @param qInfo1
     * @param qInfo
     * @return
     */
    public int querySumFirstMenuForMoudle10(BillMyWorkInfo qInfo1,TransDiscountApplyQueryInfo qInfo){
    	int ss = 0;
    	Object objReturn = null;
		BillMyWorkDao dao1 = new BillDraftOutActionDao();
		try {	
				objReturn = dao1.queryMyWork(qInfo1);
		Vector v = (Vector)objReturn;
		v.size();
		
		Collection c = null;
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO("Cra_loanform");
            if(qInfo.getQueryPurpose()==1)
            	c=dao.findByMultiOption2(qInfo);
            else if(qInfo.getQueryPurpose()==2)
            	c=dao.findByMultiOption1(qInfo);
            else if(qInfo.getQueryPurpose()==3)
            	c=dao.findByMultiOption3(qInfo);
            else if(qInfo.getQueryPurpose()==4)
            	c=dao.findByMultiOption4(qInfo);
            ss = (v!=null?v.size():0)+(c!=null?c.size():0);   
			this.updatePriMenu(ss,qInfo1.getModuleID(),true);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            //throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
     }
     return ss;
    }
    
    
    /**
     * 查询票据管理  已拒绝的业务条数
     * @param qInfo
     * @return
     */
    public int querySumThirdMenuForMoudle10(BillMyWorkInfo qInfo){
    	int ss = 0;
    	Object objReturn1 = null;
		BillMyWorkDao dao1 = new BillDraftOutActionDao();
		try {
			objReturn1 = dao1.queryMyWork(qInfo);
			PageLoader pageLoader = (PageLoader)objReturn1;
		

		Object objReturn = null;
				BillMyWorkDao dao = new BillTransDiscountActonDao();
				objReturn = dao.queryMyWork(qInfo);
				PageLoader pageLoader1 = (PageLoader)objReturn;
				
		ss = pageLoader.getPageLoaderInfo().getRowCount()+pageLoader1.getPageLoaderInfo().getRowCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return ss;
    }
    
    /**
     * 更新sys_privilage表中“待提交业务”和“待审批业务”的名称
     * @param ss
     * @param ll
     * @param firstM
     * @throws Exception
     */
    public void updatePriMenu(int ss,long ll,boolean firstM) throws Exception{
		 Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
		
			if(firstM){
				sqlBuffer.append("update sys_privilege set name='待提交业务("+ss+")' where name like '%待提交%' and moduleid="+ll+"");
			}else{
				sqlBuffer.append("update sys_privilege set name='待审批业务("+ss+")' where name like '%待审批%' and moduleid="+ll+"");	
			}
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}
			
		}

}
