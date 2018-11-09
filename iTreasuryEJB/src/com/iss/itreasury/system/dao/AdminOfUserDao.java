//Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
//Decompiler options: packimports(3) fieldsfirst ansi 
//Source File Name:   OB_UserDAO.java
/**
 * 用户信息数据访问层类实体
 * 
 * @author jicnhen
 *  
 */

package com.iss.itreasury.system.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;

import com.iss.itreasury.ebank.obsystem.dataentity.AccountPrvgInfo;
import com.iss.itreasury.ebank.privilege.bizlogic.UserBean;
import com.iss.itreasury.ebank.privilege.dataentity.AccountInfo;
import com.iss.itreasury.ebank.privilege.dataentity.ClientInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_GroupInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo;
import com.iss.itreasury.ebank.util.NameRef;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.encrypt.EncryptManager;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.dataentity.AdminOfUserInfo;
import com.iss.itreasury.system.dataentity.QueryClientSAInfo;
import com.iss.itreasury.system.privilege.dao.Sys_Group_Of_UserDAO;
import com.iss.itreasury.system.privilege.util.PrivilegeConstant;
import com.iss.itreasury.system.user.dataentity.LoginUserInfo;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Encrypt;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

import com.iss.itreasury.util.Log;
// import java.io.PrintStream;
import java.sql.*;
import java.util.*;

// Referenced classes of package com.iss.itreasury.system.privilege.dao:
// Sys_GroupDAO

public class AdminOfUserDao extends ITreasuryDAO {

	public AdminOfUserDao() {
		super("ob_admin_of_user");
	}

	public AdminOfUserDao(Connection conn) {
		super("ob_admin_of_user", true, conn);
	}
	
	public AdminOfUserDao(boolean isNeedPrefix, Connection conn) {
		super("ob_admin_of_user", isNeedPrefix, conn);
	}
	
    public ArrayList findClientNotSelect(OB_UserInfo userInfo)throws Exception
    {
    	ArrayList list = new ArrayList();
    	HashMap map = null;
    	StringBuffer sql = new StringBuffer();
    	long clientID = -1;
    	String clientNo = "";
    	String clientName = "";
    	try
    	{
    		initDAO();
    		sql.append(" select * from client c ");
    		sql.append(" where c.nstatusid>0 ");
    		sql.append(" and (c.ISINSTITUTIONALCLIENT <= 0 or c.ISINSTITUTIONALCLIENT is null) ");
    		sql.append(" and c.id not in (select distinct clientid from ob_admin_of_user) ");
    		if(userInfo.getNClientId()>-1)
    		{
    			sql.append(" and c.id !="+userInfo.getNClientId());
    		}
    		sql.append(" order by c.sCode ");
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS = transPS.executeQuery();

    		while(transRS.next())
    		{
    			map = new HashMap();
    			clientID = transRS.getLong("id");
    			clientNo = transRS.getString("scode");
    			clientName = transRS.getString("sname");
    			map.put("clientID", String.valueOf(clientID));
    			map.put("clientMessage", clientNo+"  "+clientName);
    			list.add(map);
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
    
    public boolean whetherHasAdmin(OB_UserInfo userInfo)throws Exception
    {
    	boolean hasAdmin = false;
    	StringBuffer sql = new StringBuffer();
    	try
    	{
    		initDAO();
    		sql.append(" select * ");
    		sql.append(" from client c, ob_admin_of_user o ");
    		sql.append(" where c.id = o.clientid ");
    		if(userInfo.getNClientId()>-1)
    		{
    			sql.append(" and c.id="+userInfo.getNClientId());
    		}
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS = transPS.executeQuery();
    		while(transRS.next())
    		{
    			hasAdmin = true;
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		finalizeDAO();
    	}
    	return hasAdmin;
    }
    
    /**
     * 查询管理员用户信息（航天科工）
     * @param userInfo
     * @return
     * @throws Exception
     */
    public HashMap findAdminInformation(OB_UserInfo userInfo)throws Exception
    {
    	HashMap map = new HashMap();
    	StringBuffer sql = new StringBuffer();
    	OB_UserInfo info = null;
    	String strUserNumber = "";
    	try
    	{
    		initDAO();
    		sql.append(" select u.id,u.sloginno,u.spassword,u.scertcn,u.scertno,u.nusernumber,u.nstatus ");
    		sql.append(" from ob_user u ");
    		sql.append(" where u.nusernumber is not null ");
    		
    		if(userInfo.getNClientId()>0)
    		{
    			sql.append(" and u.nclientid ="+userInfo.getNClientId());
    		}
    		if(userInfo.getNStatus()>0)
    		{
    			sql.append(" and u.nstatus ="+userInfo.getNStatus());
    		}
    		else if (userInfo.getNStatus() == SYSConstant.SysCheckStatus.DELETED)
    		{
    			sql.append("and u.nstatus !="+SYSConstant.SysCheckStatus.DELETED);
    		}

    		transPS = transConn.prepareStatement(sql.toString());
    		transRS = transPS.executeQuery();
    		while(transRS.next())
    		{
    			info = new OB_UserInfo();
    			info.setId(transRS.getLong("id"));
    			info.setSLoginNo(transRS.getString("sloginno"));
    			info.setSPassword(transRS.getString("spassword"));
    			info.setSCertCn(transRS.getString("scertcn"));
    			info.setSCertNo(transRS.getString("scertno"));
    			info.setNStatus(transRS.getLong("nstatus"));
    			strUserNumber = String.valueOf(transRS.getLong("nusernumber"));
    			map.put(strUserNumber, info);
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		finalizeDAO();
    	}
    	return map.size()>0?map:null;
    }
    
    /**
     * 查询管理员用户信息（集成平台）
     * @param userInfo
     * @return
     * @throws Exception
     */
    public ArrayList findAdminUserInformation(OB_UserInfo userInfo)throws Exception
    {
    	ArrayList list = new ArrayList();
    	StringBuffer sql = new StringBuffer();
    	OB_UserInfo info = null;
    	
    	try
    	{
    		initDAO();
    		sql.append(" select u.id,u.sloginno,u.spassword,u.scertcn,u.scertno,u.nstatus,u.sname ");
    		sql.append(" from ob_user u ");
    		sql.append(" where u.nsaid = -1 ");  //网银管理员用户
    		
    		if(userInfo.getNClientId()>0)
    		{
    			sql.append(" and u.nclientid ="+userInfo.getNClientId());
    		}
    		if(userInfo.getNStatus()>0)
    		{
    			sql.append(" and u.nstatus ="+userInfo.getNStatus());
    		}
    		else if (userInfo.getNStatus() == SYSConstant.SysCheckStatus.DELETED)
    		{
    			sql.append("and u.nstatus !="+SYSConstant.SysCheckStatus.DELETED);
    		}
    		log.info("sql="+sql.toString());
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS = transPS.executeQuery();
    		
    		while(transRS.next())
    		{
    			info = new OB_UserInfo();
    			info.setId(transRS.getLong("id"));
    			info.setSName(transRS.getString("sname"));
    			info.setSLoginNo(transRS.getString("sloginno"));
    			info.setSPassword(transRS.getString("spassword"));
    			info.setSCertCn(transRS.getString("scertcn"));
    			info.setSCertNo(transRS.getString("scertno"));
    			info.setNStatus(transRS.getLong("nstatus"));
    			list.add(info);
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		throw new IException("查询管理员用户出错",e);
    		
    	}
    	finally
    	{
    		finalizeDAO();
    	}
    	return list.size()>0?list:null;
    }    
	
    public ArrayList findClientSelected(AdminOfUserInfo adminOfUserInfo)throws Exception
    {
    	ArrayList list = new ArrayList();
    	HashMap map = null;
    	StringBuffer sql = new StringBuffer();
    	long clientID = -1;
    	String clientNo = "";
    	String clientName = "";
    	try
    	{
    		initDAO();
    		sql.append(" select c.id,c.scode,c.sname ");
    		sql.append(" from client c,ob_admin_of_user o ");
    		sql.append(" where c.nstatusid>0 ");
    		sql.append(" and (c.ISINSTITUTIONALCLIENT <= 0 or c.ISINSTITUTIONALCLIENT is null) ");
    		sql.append(" and c.id = o.clientid ");
    		if(adminOfUserInfo.getInitialUserID()>-1)
    		{
    			sql.append(" and o.initialuserid ="+adminOfUserInfo.getInitialUserID());
    		}
    		if(adminOfUserInfo.getIsBelongToClient()>-1)
    		{
    			sql.append(" and o.isbelongtoclient ="+adminOfUserInfo.getIsBelongToClient());
    		}
    		sql.append(" order by o.isbelongtoclient,c.sCode ");
    		log.info("sql="+sql.toString());
    		transPS = transConn.prepareStatement(sql.toString());
    		transRS = transPS.executeQuery();
    		while(transRS.next())
    		{
    			map = new HashMap();
    			clientID = transRS.getLong("id");
    			clientNo = transRS.getString("scode");
    			clientName = transRS.getString("sname");
    			map.put("clientID", String.valueOf(clientID));
    			map.put("clientMessage", clientNo+"  "+clientName);
    			map.put("clientCode", clientNo);
    			map.put("clientName", clientName);
    			list.add(map);
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
    
    public void deleteAdminOfUser(AdminOfUserInfo adminOfUserInfo,Connection conn) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	PreparedStatement ps =null;
    	try
    	{
    		sql.append(" delete from ob_admin_of_user o ");
    		sql.append(" where 2>1 ");
    		if(adminOfUserInfo.getInitialUserID()>-1)
    		{
    			sql.append(" and o.initialuserid ="+adminOfUserInfo.getInitialUserID());
    		}
    		if(adminOfUserInfo.getIsBelongToClient()>-1)
    		{
    			sql.append(" and o.isbelongtoclient ="+adminOfUserInfo.getIsBelongToClient());
    		}
    		ps = conn.prepareStatement(sql.toString());
    		ps.execute();

    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		if(ps!=null)
    		{
    			ps.close();
    			ps=null;
    		}
    	}
    }
    
    public ArrayList findUserByClient(AdminOfUserInfo queryInfo,Connection conn) throws Exception
    {
    	ArrayList list = new ArrayList();
    	AdminOfUserInfo info = null;
    	PreparedStatement ps =null;
    	ResultSet rs = null;
    	StringBuffer sql = new StringBuffer();
    	try
    	{
    		sql.append(" select distinct o.initialuserid from ob_admin_of_user o ");
    		sql.append(" where 2>1 ");
    		if(queryInfo.getClientID()>0)
    		{
    			sql.append(" and o.clientid ="+queryInfo.getClientID());
    		}
    		if(queryInfo.getIsBelongToClient()>0)
    		{
    			sql.append(" and o.isbelongtoclient ="+queryInfo.getIsBelongToClient());
    		}
    		ps = conn.prepareStatement(sql.toString());
    		rs = ps.executeQuery();
    		while(rs.next())
    		{
    			info = new AdminOfUserInfo();
    			info.setInitialUserID(rs.getLong("initialuserid"));
    			list.add(info);
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		if(rs!=null)
    		{
    			rs.close();
    			rs=null;
    		}    		
    		if(ps!=null)
    		{
    			ps.close();
    			ps=null;
    		}
    	}
    	return list.size()>0?list:null;
    }
    
    
    
    
}