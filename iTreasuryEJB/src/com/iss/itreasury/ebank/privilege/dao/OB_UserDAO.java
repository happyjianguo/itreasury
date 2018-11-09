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

package com.iss.itreasury.ebank.privilege.dao;

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

import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.dataentity.AccountOwnedBySAInfo;
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

public class OB_UserDAO extends ITreasuryDAO {

	public OB_UserDAO() {
		super("ob_user");
	}

	public OB_UserDAO(Connection conn) {
		super("ob_user", true, conn);
	}
	
	public OB_UserDAO(boolean isNeedPrefix, Connection conn) {
		super("ob_user", isNeedPrefix, conn);
	}

	/**
	 * 根据用户登录名查询用户信息
	 * 
	 * @param loginName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws IException
	 */
	public Collection findByLoginNo(String LoginNo) throws IException
	{

		Collection c = null;
		OB_UserInfo condition = new OB_UserInfo();
		try {
			this.initDAO();

			condition.setSLoginNo(LoginNo);
			condition.setNStatus(1L);

			c = this.findByCondition(condition);
			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("根据登录名查找用户发生错误", e);
			// throw new IException(null,e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return c;
	}

	/**
	 * 根据用户登录名查询用户信息
	 * 
	 * @param loginName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws IException
	 */
	public Collection findByLoginNo(String LoginNo, long clientId)
			throws IException

	{

		Collection c = null;
		OB_UserInfo condition = new OB_UserInfo();
		try {
			this.initDAO();

			condition.setNClientId(clientId);
			condition.setSLoginNo(LoginNo);
			condition.setNStatus(1L);

			c = this.findByCondition(condition);

			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("根据登录名查找用户发生错误", e);
			// throw new IException(null,e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return c;

	}

	public static void main(String args[]) {
		/*
		 * OB_UserDAO dao = new OB_UserDAO(); OB_UserEntity en = new
		 * OB_UserEntity(); en.setId(1); Vector v = null; try { v =
		 * dao.findUserByCondition(en,1); v =
		 * (Vector)dao.findUserByCondition(en,1,1); OBtem.out.println(v.size()); }
		 * catch(ITreasuryDAOException e) { e.printStackTrace(); }
		 * catch(SQLException e) { e.printStackTrace(); }
		 */
		/*
		 * Collection c = null;
		 * 
		 * OB_UserInfo info = new OB_UserInfo(); OB_UserDAO dao = new
		 * OB_UserDAO(); info.setSLoginNo("01-0002"); info.setNStatus(1); try {
		 * c = dao.findByCondition(info); if (c != null) { Iterator it =
		 * c.iterator(); if (it.hasNext()) { info = (OB_UserInfo) it.next(); } } }
		 * catch (ITreasuryDAOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } System.out.println(info.getId() + "\t" +
		 * info.getSName() + "\t" + info.getSLoginNo() + "\t" +
		 * info.getSPassword());
		 */
		OB_UserInfo info = new OB_UserInfo();
		OB_UserDAO dao = new OB_UserDAO();
		Collection c = null;

		try {
			info = (OB_UserInfo) dao.findByID(42, OB_UserInfo.class);
			System.out.println(info.getNClientId());
			System.out.println(info.getId() + "\t" + info.getNClientId()
					+ info.getSName() + info.getSPassword());
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据用户信息，用户组ID，排序条件查询用户信息
	 * 
	 * @param userInfoCondition
	 * @param groupId
	 * @param orderCondition
	 * @return
	 * @throws IException
	 */
	public Vector findUserByCondition(OB_UserInfo userInfoCondition,
			long groupId, long orderCondition, long lasc) throws IException {
		// TODO Auto-generated method stub
		Vector vectTemp = new Vector();
		String name = userInfoCondition.getSName();
		String login = userInfoCondition.getSLoginNo();
		long officeid = userInfoCondition.getNOfficeID();
		String currencyID = userInfoCondition.getSCurrencyId();
		try {
			initDAO();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append("select a.id,a.sname,a.sloginno,a.nofficeid,a.sCURRENCYId,b.sname,a.dtinput,a.ndepartmentid from userinfo a,userinfo b  where a.nstatusid=1 and a.dtinput=b.id");
			sbSQL
					.append("   and a.id in(select userid from OB_group_of_user where groupid="
							+ groupId + ")");
			if (name.length() > 0) {

				sbSQL.append(" and a.sname like '%" + name + "%'");
			}
			if (login.length() > 0) {
				sbSQL.append(" and a.sloginno like '%" + login + "%'");
			}
			if (officeid != -1L) {
				sbSQL.append(" and a.nofficeid=" + officeid);
			}
			if (currencyID != null) {
				sbSQL
						.append(" and a.scurrencyID like \'%" + currencyID
								+ "%\'");
			}
			if (orderCondition < 0) {
				sbSQL.append(" order by a.id");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.USERNAME) {
				sbSQL.append(" order by a.sname");
				if (lasc == 1)
					sbSQL.append(" asc ");
				else
					sbSQL.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.LOGINNAME) {
				sbSQL.append(" order by a.sloginno");
				if (lasc == 1)
					sbSQL.append(" asc ");
				else
					sbSQL.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.OFFICE) {
				sbSQL.append(" order by a.nofficeid");
				if (lasc == 1)
					sbSQL.append(" asc ");
				else
					sbSQL.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.CURRENCY) {
				sbSQL.append(" order by a.sCURRENCYId");
				if (lasc == 1)
					sbSQL.append(" asc ");
				else
					sbSQL.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTUSER) {
				sbSQL.append(" order by b.sname");
				if (lasc == 1)
					sbSQL.append(" asc ");
				else
					sbSQL.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTDATE) {
				sbSQL.append(" order by a.dtinput");
				if (lasc == 1)
					sbSQL.append(" asc ");
				else
					sbSQL.append(" desc ");
			}

			System.out.println(sbSQL);
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			ResultSetMetaData rsmd = transRS.getMetaData();
			Vector oneGroup;
			for (; transRS.next(); vectTemp.addElement(oneGroup)) {
				oneGroup = new Vector();
				for (int i = 0; i < rsmd.getColumnCount(); i++)
					oneGroup.addElement(transRS.getString(i + 1));

			}

			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return vectTemp.size() > 0 ? vectTemp : null;
	}

	/**
	 * 根据用户信息，排序条件查询用户
	 * 
	 * @param userInfoCondition
	 * @param orderCondition
	 * @return
	 * @throws SQLException
	 * @throws IException
	 */
	public Vector findUserByCondition(OB_UserInfo userInfoCondition,
			long orderCondition, long lasc) throws IException {

		String name = userInfoCondition.getSName();
		String login = userInfoCondition.getSLoginNo();
		long officeid = userInfoCondition.getNOfficeID();
		String currencyID = userInfoCondition.getSCurrencyId();

		Vector vectTemp = new Vector();
		try {
			initDAO();

			StringBuffer sb = new StringBuffer();
			sb
					.append("select a.id,a.sname,a.sloginno,a.nofficeid,a.sCURRENCYId,b.sname,a.dtinput,a.ndepartmentid  from userinfo a,userinfo b  where a.nstatusid=1 and a.id=b.id(+)");
			if (name != null && name.length() > 0)
				sb.append(" and a.sname like '%" + name + "%'");
			if (login != null && login.length() > 0)
				sb.append(" and a.sloginno like '%" + login + "%'");
			if (officeid != -1L)
				sb.append(" and a.nofficeid=" + officeid);
			if (currencyID != null && currencyID != null)
				sb.append(" and a.scurrencyID like \'%" + currencyID + "%\'");
			if (orderCondition < 0) {
				sb.append(" order by a.id");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.USERNAME) {
				sb.append(" order by a.sname");
				if (lasc == 1)
					sb.append(" asc ");
				else
					sb.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.LOGINNAME) {
				sb.append(" order by a.sloginno");
				if (lasc == 1)
					sb.append(" asc ");
				else
					sb.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.OFFICE) {
				sb.append(" order by a.nofficeid");
				if (lasc == 1)
					sb.append(" asc ");
				else
					sb.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.CURRENCY) {
				sb.append(" order by a.sCURRENCYId");
				if (lasc == 1)
					sb.append(" asc ");
				else
					sb.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTUSER) {
				sb.append(" order by b.sname");
				if (lasc == 1)
					sb.append(" asc ");
				else
					sb.append(" desc ");
			} else if (orderCondition == PrivilegeConstant.UserOrderCondition.INPUTDATE) {
				sb.append(" order by a.dtinput");
				if (lasc == 1)
					sb.append(" asc ");
				else
					sb.append(" desc ");
			}
			System.out.println(sb);
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			ResultSetMetaData rsmd = transRS.getMetaData();
			Vector oneGroup;
			for (; transRS.next(); vectTemp.addElement(oneGroup)) {
				oneGroup = new Vector();
				for (int i = 0; i < rsmd.getColumnCount(); i++)
					oneGroup.addElement(transRS.getString(i + 1));

			}

			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return vectTemp.size() > 0 ? vectTemp : null;

	}

	/**
	 * 查找客户可用的账户资源
	 * 
	 * @throws IException
	 */
	public Collection findAccountByClient(long lClientID) throws IException {

		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select distinct nOfficeID,NCLIENTID,SACCOUNTNO from Sett_account where nStatusID=1 and NCHECKSTATUSID=4");
			if(Config.getBoolean(ConfigConstant.EBANK_MANAGE_CHILDCLIENT,false)){
				sbSQL.append(" and nclientid in (select id from client_clientinfo a where a.levelcode like (select a.levelcode from client_clientinfo a where a.id ="+lClientID+")||'%') order by nclientid ");
			}else{
				sbSQL.append(" and nclientid = "+ lClientID);
			}

			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				AccountInfo o = new AccountInfo();
				o.m_lOfficeID = transRS.getLong("nOfficeID");
				o.m_lClientID = transRS.getLong("NCLIENTID");
				o.m_strAccountNo = transRS.getString("SACCOUNTNO");
				v.add(o);
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}
	public List findEbankAccountBySelect(long lClientID,long lCurrencyID) throws IException
	{
		List list = new ArrayList();
		try
		{
			
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select t.s_accountno ");
			sbSQL.append(" from bs_bankaccountinfo t,client_clientinfo cc ");
			sbSQL.append(" where t.n_clientid=cc.id ");
			sbSQL.append(" and t.n_accountstatus=1 ");
			sbSQL.append(" and t.n_rdstatus=1 ");
			sbSQL.append(" and t.n_ischeck=1 ");
			sbSQL.append(" and t.n_clientid="+lClientID);
			if(lCurrencyID>0)
			{
				sbSQL.append(" and t.n_currencytype="+lCurrencyID);
			}
			sbSQL.append(" order by t.s_accountno ");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				
				String accountno = transRS.getString("s_accountno");
				list.add(accountno);
			}
			
		}
		catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		}finally {

			this.finalizeDAO();

		}
		
		return list.size() > 0 ? list:null;

		
		
	}
	public Collection findEbankAccountBySA(long lClientID,long lCurrencyID) throws IException
	{
		Vector v = new Vector();
		try
		{
			
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select cc.id,t.s_accountno ");
			sbSQL.append(" from bs_bankaccountinfo t,client_clientinfo cc ");
			sbSQL.append(" where t.n_clientid=cc.id ");
			sbSQL.append(" and t.n_accountstatus=1 ");
			sbSQL.append(" and t.n_rdstatus=1 ");
			sbSQL.append(" and t.n_ischeck=1 ");
			if(Config.getBoolean(ConfigConstant.EBANK_MANAGE_CHILDCLIENT,false)){
				sbSQL.append(" and t.n_clientid in (select id from client_clientinfo where levelcode like (select levelcode from client_clientinfo where id = "+lClientID+")||'%')");
			}else{
				sbSQL.append(" and t.n_clientid="+lClientID);
			}
			if(lCurrencyID>0)
			{
				sbSQL.append(" and t.n_currencytype="+lCurrencyID);
			}
			sbSQL.append(" order by t.s_accountno ");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				AccountInfo o = new AccountInfo();
				o.m_lClientID = transRS.getLong("id");
				o.m_strAccountNo = transRS.getString("s_accountno");
				v.add(o);
				
			}
			
		}
		catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		}finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v:null;

		
		
	}
	/**
	 * 查找系统管理员可用的账户
	 * 
	 * @throws IException
	 */
	public Collection findAccountBySA(long lClientID, long lCurrencyID,
			long lSAID) throws IException {

		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select distinct a.nClientID,a.saccountno from OB_AccountOwnedBySA a,Sett_Account ai where ai.nStatusID=1 and a.sAccountNo=ai.saccountno");
			sbSQL.append(" and ai.NCHECKSTATUSID="+SETTConstant.AccountCheckStatus.CHECK);
			sbSQL.append(" and a.nSAID=" + lSAID);
			if(Config.getBoolean(ConfigConstant.EBANK_MANAGE_CHILDCLIENT,false)){
				sbSQL.append(" and a.nClientID=" + lClientID);
			}else{
				sbSQL.append(" and ai.nClientID=" + lClientID);
			}
			if (lCurrencyID > 0) {
				sbSQL.append(" and ai.ncurrencyid=" + lCurrencyID);
			}
			sbSQL.append(" order by a.saccountno ");
			System.out.println("sql="+sbSQL.toString());
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				AccountInfo o = new AccountInfo();
				o.m_lClientID = transRS.getLong("nClientID");
				o.m_strAccountNo = transRS.getString("saccountno");
				v.add(o);
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}

	public boolean isDuplicateOBLoginNo(String strLogin) throws IException
	{
		boolean bReturn = false;

		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL
					.append("select  id from ob_user where SLOGINNO=? and nstatus in(?,?)");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("===isDuplicateOBLoginNo=====>"
					+ sbSQL.toString());
			transPS.setString(1, strLogin);
			transPS.setLong(2, SYSConstant.SysCheckStatus.CHECK);
			transPS.setLong(3, SYSConstant.SysCheckStatus.UNCHECK);
			transRS = transPS.executeQuery();
			
			if (transRS.next()) {
				bReturn = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new IException("判断网银用户登录名是否重复时出错，" + e.getMessage(), e);
		} finally {
			this.finalizeDAO();

		}
		
		return bReturn;
	}
	
	public boolean isDuplicateIdentityCard(String identityCard)throws Exception
	{
		boolean bReturn = false;
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select  id from ob_user where spassword=? and nstatus in(?,?)");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("===isDuplicateIdentityCard=====>"+ sbSQL.toString());
			transPS.setString(1, identityCard);
			transPS.setLong(2, SYSConstant.SysCheckStatus.CHECK);
			transPS.setLong(3, SYSConstant.SysCheckStatus.UNCHECK);
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				bReturn = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new IException("判断网银用户登录名是否重复时出错，" + e.getMessage(), e);
		} finally {
			this.finalizeDAO();

		}		
		return bReturn;
	}


	/**
	 * 判断是否已存在该客户的有效网银客户
	 * 
	 * @param clientId
	 * @return
	 * @throws IException
	 */
	public boolean isExistOBInfo(long clientId) throws IException
	{
		boolean bReturn = false;

		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL
					.append("select  id from ob_user where NCLIENTID=? and nstatus=?");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1, clientId);
			transPS.setLong(2, Constant.RecordStatus.VALID);
			transRS = transPS.executeQuery();

			if (transRS.next()) 
			{
				bReturn = true;
			} 
		}catch (Exception e) {
			e.printStackTrace();
			throw new IException("判断客户是否已存在网银客户时出错，" + e.getMessage(), e);
		}finally {
			this.finalizeDAO();

		}
		
		return bReturn;
	}
	/**
	 * 查找所有的客户
	 * 
	 * @throws IException
	 */
	public ClientInfo findClientByCode(String strClientCode) throws IException

	{

		ClientInfo o = null;
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL
					.append(" select id,scode,sname,nOfficeID from Client where scode='"
							+ strClientCode + "' ");

			transPS = transConn.prepareStatement(sbSQL.toString());

			transRS = transPS.executeQuery();
			while (transRS.next()) {
				o = new ClientInfo();
				o.m_lOfficeID = transRS.getLong("nOfficeID");
				o.m_lID = transRS.getLong("id");
				o.m_strCode = transRS.getString("scode");
				o.m_strName = transRS.getString("sName");
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return o;
	}
	
	/**
	 * 查找所有的客户,根据ID
	 * 
	 * @throws IException
	 */
	public ClientInfo findClientById(long clientId) throws IException

	{

		ClientInfo o = null;
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL
					.append(" select id,scode,sname,nOfficeID from Client where ID="
							+ clientId + " ");

			transPS = transConn.prepareStatement(sbSQL.toString());

			transRS = transPS.executeQuery();
			while (transRS.next()) {
				o = new ClientInfo();
				o.m_lOfficeID = transRS.getLong("nOfficeID");
				o.m_lID = transRS.getLong("id");
				o.m_strCode = transRS.getString("scode");
				o.m_strName = transRS.getString("sName");
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return o;
	}

	/**
	 * 查找所有的客户
	 * 
	 * @throws IException
	 */
	public Collection findAllClient() throws IException {
		long lReturn = -1;

		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL
					.append(" select id,scode,sname,nOfficeID from Client where length(scode) > 2 and nstatusid="
							+ 1 + " order by nofficeid, id ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				ClientInfo o = new ClientInfo();
				o.m_lOfficeID = transRS.getLong("nOfficeID");
				o.m_lID = transRS.getLong("id");
				o.m_strCode = transRS.getString("scode");
				o.m_strName = transRS.getString("sName");
				v.add(o);
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}
	public Collection findEbankAccountByUserQuery(long lUserID,long lCurrencyID,long clientID)throws IException
	{
		Vector v = new Vector();
		try
		{
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select e.nclientid,e.saccountno,t.n_id,t.n_accounttype" );
			sbSQL.append(" from OB_EbankAccountByUserQuery e,bs_bankaccountinfo t ");
			sbSQL.append(" where e.saccountno=t.s_accountno ");
			sbSQL.append(" and t.n_accountstatus=1 ");
			sbSQL.append(" and t.n_rdstatus=1 ");
			sbSQL.append(" and t.n_ischeck=1 ");
			
			sbSQL.append(" and e.nuserid="+lUserID);
			if (lCurrencyID > 0) {
				sbSQL.append(" and t.n_currencytype="+lCurrencyID);
			}
			if(clientID>0)
			{
				sbSQL.append(" and e.nclientid="+clientID);
			}
			sbSQL.append(" order by t.s_accountno ");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				AccountInfo o = new AccountInfo();
				o.m_lAccountID = transRS.getLong("n_id");
				o.m_lClientID=transRS.getLong("nclientid");
				o.m_strAccountNo=transRS.getString("saccountno");
				o.m_lAccountType=transRS.getLong("n_accounttype");
				v.add(o);
			}
			
			
		}catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}
	public Collection findEbankAccountByUserOperate(long lUserID,long lCurrencyID,long clientID) throws IException
	{
		Vector v = new Vector();
		try
		{
			
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select e.nclientid,e.saccountno,t.n_id,t.n_accounttype" );
			sbSQL.append(" from OB_EbankAccountByUserOperation e,bs_bankaccountinfo t ");
			sbSQL.append(" where e.saccountno=t.s_accountno ");
			sbSQL.append(" and t.n_accountstatus=1 ");
			sbSQL.append(" and t.n_rdstatus=1 ");
			sbSQL.append(" and t.n_ischeck=1 ");
			
			sbSQL.append(" and e.nuserid="+lUserID);
			if (lCurrencyID > 0) {
				sbSQL.append(" and t.n_currencytype="+lCurrencyID);
			}
			if(clientID>0)
			{
				sbSQL.append(" and e.nclientid ="+clientID);
			}
			sbSQL.append(" order by t.s_accountno ");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				AccountInfo o = new AccountInfo();
				o.m_lAccountID = transRS.getLong("n_id");
				o.m_lClientID=transRS.getLong("nclientid");
				o.m_strAccountNo=transRS.getString("saccountno");
				o.m_lAccountType=transRS.getLong("n_accounttype");
				v.add(o);
			}
			
			
		}catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * 查找用户可用的账户
	 * 
	 * @throws IException
	 */
	public Collection findAccountByUser(long lUserID, long lCurrencyID,long clientID)
			throws IException

	{
		long lReturn = -1;
		
		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" select a.nClientID,a.saccountno,ai.id,ai.sname,ai.naccounttypeid from OB_AccountOwnedByUser a, Sett_Account ai where ai.nStatusID=1 and a.saccountno=ai.saccountno and a.nUserID="
							+ lUserID);
			if (lCurrencyID > 0) {
				sbSQL.append(" and ai.ncurrencyid=" + lCurrencyID);
			}
			if(clientID>0)
			{
				sbSQL.append(" and a.nclientid ="+clientID);
			}
			sbSQL.append("  order by a.saccountno  ");
			System.out.println(sbSQL);
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			
			while (transRS.next()) {
				AccountInfo o = new AccountInfo();
				o.m_lAccountID = transRS.getLong("id");
				o.m_lClientID = transRS.getLong("nClientID");
				o.m_strAccountNo = transRS.getString("saccountno");
				o.m_lAccountType = transRS.getLong("naccounttypeid");
				o.m_strAccountName = transRS.getString("sname");
				v.add(o);
			}

			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}
	public Collection findAccountByUserQuery(long lUserID, long lCurrencyID,long clientID)
	throws IException

{
		long lReturn = -1;
		
		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
				.append(" select a.nClientID,a.saccountno,ai.id,ai.sname,ai.naccounttypeid from OB_AccountOwnedByUserQuery a, Sett_Account ai where ai.nStatusID=1 and a.saccountno=ai.saccountno and a.nUserID="
					+ lUserID);
			if (lCurrencyID > 0) {
				sbSQL.append(" and ai.ncurrencyid=" + lCurrencyID);
			}
			if(clientID>0)
			{
				sbSQL.append(" and a.nclientid="+clientID);
			}
			sbSQL.append("  order by a.saccountno  ");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			
			while (transRS.next()) {
				AccountInfo o = new AccountInfo();
				o.m_lAccountID = transRS.getLong("id");
				o.m_lClientID = transRS.getLong("nClientID");
				o.m_strAccountNo = transRS.getString("saccountno");
				o.m_lAccountType = transRS.getLong("naccounttypeid");
				o.m_strAccountName = transRS.getString("sname");
				v.add(o);
			}

			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * 查找用户可用的账户
	 * 
	 * @throws IException
	 */
	public Collection findOBAccountByClient(long lClientID) throws IException

	{
		long lReturn = -1;

		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" select nClientID,saccountno from OB_AccountOwnedByUser where nClientID="
							+ lClientID);
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				AccountInfo o = new AccountInfo();
				o.m_lClientID = transRS.getLong("nClientID");
				o.m_strAccountNo = transRS.getString("saccountno");
				v.add(o);
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}

	/**
	 * 判断是否是系统管理员
	 * 
	 * @throws IException
	 */
	public boolean isSA(long lUserID) throws IException {
		boolean b = false;

		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select nSAID from OB_AccountOwnedBySA where nSAID="
					+ lUserID);
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				b = true;
			}

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		this.finalizeDAO();
		return b;
	}

	/**
	 * 向 OB_AccountOwnedBySA 增加记录
	 * 
	 * @throws IException
	 */
	public long addAccountOwnedBySA(long lClientID, long lSAID,
			String strAccountNo) throws IException {
		long lReturn = -1;

		long lAccountID = 0;
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id from sett_account where sAccountNo ='"
					+ strAccountNo + "' and nStatusID=1 and NCHECKSTATUSID=4 ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("======add111=====" + sbSQL.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				lAccountID = transRS.getLong("id");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			StringBuffer sb = new StringBuffer();
			sb
					.append(" insert into OB_AccountOwnedBySA (nSAID,nClientID,sAccountNo,nAccountID) values(?,?,?,?) ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lSAID);
			transPS.setLong(2, lClientID);
			transPS.setString(3, strAccountNo);
			transPS.setLong(4, lAccountID);
			lReturn = transPS.executeUpdate();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		this.finalizeDAO();
		return lReturn;
	}
	public void addEbankAccountOwnedUser(long lClientID,long lUserID,String strAccountNo,String strTableName) throws IException
	{
		long lAccountID = 0;
		try
		{
			
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select n_id from bs_bankaccountinfo t ");
			sbSQL.append(" where s_accountno='"+strAccountNo+"'");
			sbSQL.append(" and t.n_accountstatus=1 ");
			sbSQL.append(" and t.n_rdstatus=1 ");
			sbSQL.append(" and t.n_ischeck=1 ");
			
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			
			if (transRS.next()) {
				lAccountID = transRS.getLong("n_id");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			StringBuffer sb = new StringBuffer();
			sb.append(" insert into "+strTableName+" (nUserID,nClientID,sAccountNo,nAccountID) values(?,?,?,?) ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lUserID);
			transPS.setLong(2, lClientID);
			transPS.setString(3, strAccountNo);
			transPS.setLong(4, lAccountID);
			transPS.executeUpdate();
			
		}
		catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		
		
		
	}
	public void addAccountOwnedByUserPublic(long lClientID,long lUserID,String strAccountNo,String strTableName) throws IException
	{
		long lAccountID = 0;
		try{
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id from sett_account where sAccountNo ='"
					+ strAccountNo + "' and nStatusID=1 and NCHECKSTATUSID=4");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=====add222=====" + sbSQL.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				lAccountID = transRS.getLong("id");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			StringBuffer sb = new StringBuffer();
			sb.append(" insert into "+strTableName+" (nUserID,nClientID,sAccountNo,nAccountID) values(?,?,?,?) ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lUserID);
			transPS.setLong(2, lClientID);
			transPS.setString(3, strAccountNo);
			transPS.setLong(4, lAccountID);
			transPS.executeUpdate();
			
		}
		catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		
	}
	/**
	 * 向 OB_AccountOwnedBySA 增加记录
	 */
	public long addAccountOwnedByUser(long lClientID, long lUserID,
			String strAccountNo) throws IException {
		long lReturn = -1;

		long lAccountID = 0;
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id from sett_account where sAccountNo ='"
					+ strAccountNo + "' and nStatusID=1 and NCHECKSTATUSID=4");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=====add222=====" + sbSQL.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				lAccountID = transRS.getLong("id");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			StringBuffer sb = new StringBuffer();
			sb
					.append(" insert into OB_AccountOwnedByUser (nUserID,nClientID,sAccountNo,nAccountID) values(?,?,?,?) ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lUserID);
			transPS.setLong(2, lClientID);
			transPS.setString(3, strAccountNo);
			transPS.setLong(4, lAccountID);
			lReturn = transPS.executeUpdate();

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return lReturn;
	}

	/**
	 * 删除 OB_AccountOwnedByUser 中记录
	 * 
	 * @throws IException
	 */
	public long deleteAccountOwnedByUser(long lUserID) throws IException
	{
		long lReturn = -1;

		try {
			this.initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" delete from OB_AccountOwnedByUser where nUserID=? ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lUserID);
			lReturn = transPS.executeUpdate();

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return lReturn;
	}
	
	/**
	 * 删除 OB_AccountOwnedByUser 中记录
	 * 
	 * @throws IException
	 */
	public long deleteAccountOwnedByUser(long lUserID,long lClientID) throws IException
	{
		long lReturn = -1;

		try {
			this.initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" delete from OB_AccountOwnedByUser where nUserID=? and nclientid=?");
			System.out.println(sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lUserID);
			transPS.setLong(2, lClientID);
			lReturn = transPS.executeUpdate();

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return lReturn;
	}	
	
	public void deleteAccountOwnedByUserPublic(long lUserID,long lClientID,String strTableName)throws IException
	{
		try {
			this.initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append("delete from "+strTableName+" where nUserID=? and nclientid=? ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lUserID);
			transPS.setLong(2, lClientID);
			transPS.executeUpdate();
			
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
	}

	/**
	 * 删除 OB_AccountOwnedByUser 中记录
	 * 
	 * @throws IException
	 */
	public long deleteAccountOwnedByClient(long lClientID, String strAccountNo)
			throws IException

	{
		long lReturn = -1;

		try {
			this.initDAO();
			StringBuffer sb = new StringBuffer();
			sb
					.append(" delete from OB_AccountOwnedByUser where nClientID=? and sAccountNo=?");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lClientID);
			transPS.setString(2, strAccountNo);
			lReturn = transPS.executeUpdate();

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return lReturn;
	}

	/**
	 * 删除 OB_AccountOwnedByUser 中记录
	 * 
	 * @throws IException
	 */
	public long deleteAccountOwnedBySA(long lClientID) throws IException

	{
		long lReturn = -1;

		try {
			this.initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" delete from OB_AccountOwnedBySA where nClientID=? ");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lClientID);
			lReturn = transPS.executeUpdate();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		this.finalizeDAO();
		return lReturn;
	}

	public Collection queryOBClientSA(QueryClientSAInfo qc) throws IException

	{

		Vector v = new Vector();

		try {
			this.initDAO();

			StringBuffer sbDetail = new StringBuffer();
			sbDetail.append(" from ob_user a, office b, client c \n");
			sbDetail.append(" where b.id=c.nofficeid \n");
			sbDetail.append(" and a.NSTATUS=1 \n");

			sbDetail.append(" and a.sloginno=c.scode \n");
			if (qc.getStartClientCode() != null
					&& qc.getStartClientCode().length() > 0)
				sbDetail.append("      and  a.sloginno>='"
						+ qc.getStartClientCode() + "' ");
			if (qc.getEndClientCode() != null
					&& qc.getEndClientCode().length() > 0)
				sbDetail.append("      and  a.sloginno<='"
						+ qc.getEndClientCode() + "' ");
			if (qc.getOfficeID() > 0)
				sbDetail.append("      and  b.id= " + qc.getOfficeID());

			//			
			String strField = " select count(*) \n";
			//
			StringBuffer sb = new StringBuffer();
			sb.append(strField + sbDetail.toString());
			Log.print(sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			long lCount = 0;
			if (transRS.next()) {
				lCount = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			//
			sbDetail.append(" order by  " + qc.getOrder() + " " + qc.getSort()
					+ "\n  ");
			qc.setTotalPages(lCount / qc.getLinesOfPage());
			if (lCount % qc.getLinesOfPage() != 0)
				qc.setTotalPages(qc.getTotalPages() + 1);
			long lRowNumStart = (qc.getCurrentPage() - 1) * qc.getLinesOfPage()
					+ 1;
			long lRowNumEnd = lRowNumStart + qc.getLinesOfPage() - 1;
			//
			strField = " select a.*, b.sname officename, c.sname clientname,c.scode clientcode \n";
			sb.setLength(0);
			sb.append(" select * from (select a.*,rownum r from  ( " + strField
					+ sbDetail.toString() + " ) a ) \n");
			sb
					.append(" where r between " + lRowNumStart + " and "
							+ lRowNumEnd);
			Log.print(sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OB_UserInfo o = new OB_UserInfo();
				o.setClientCode(transRS.getString("clientcode"));
				o.setNClientId(transRS.getLong("NCLIENTID"));
				o.setClientName(transRS.getString("clientname"));
				o.setInput(transRS.getTimestamp("dtInput"));
				o.setPrvgLevel(transRS.getString("SPRVGLEVEL"));
				o.setSLoginNo(transRS.getString("SLOGINNO"));
				o.setOfficeName(transRS.getString("officename"));
				o.setSPassword(transRS.getString("SPASSWORD"));
				o.setNStatus(transRS.getLong("NSTATUS"));
				o.setTotalPages(qc.getTotalPages());
				o.setNSaid(transRS.getLong("ID"));
				v.add(o);
			}
			Log.print(" v.size " + v.size());

			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return v.size() > 0 ? v : null;
	}

	public Collection queryOBClientAll(QueryClientSAInfo qc) throws IException

	{

		Vector v = new Vector();

		try {
			this.initDAO();

			StringBuffer sbDetail = new StringBuffer();
			sbDetail.append(" from( ");
			sbDetail.append(" select c.id clientid,u.id userid,c.scode clientcode,c.sname clientname,o.sname officename,");
			sbDetail.append(" u.sloginno loginno,u.spassword password, 1 isadmin,u.dtchangepassword dtchangepassword, a.isbelongtoclient isbelongtoclient,o.id  officeid,u.nstatus status,u.nsaid nsaid ");
			sbDetail.append(" from ob_user u, ob_admin_of_user a, client c, office o ");
			sbDetail.append(" where u.nsaid = -1 ");
			sbDetail.append(" and u.id = a.initialuserid ");
			sbDetail.append(" and a.clientid = c.id ");
			sbDetail.append(" and c.nofficeid = o.id ");
			sbDetail.append(" and c.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
			if(qc.getStatusID()>-1)
			{
				sbDetail.append(" and u.nstatus ="+qc.getStatusID());
			}
			sbDetail.append(" union all ");
			sbDetail.append(" select c.id clientid,u.id userid,c.scode clientcode,c.sname clientname,o.sname officename,");
			sbDetail.append(" u.sloginno loginno,u.spassword password, 2 isadmin,u.dtchangepassword dtchangepassword,1 isbelongtoclient,o.id officeid,u.nstatus status,u.nsaid nsaid ");
			sbDetail.append(" from ob_user u, client c, office o ");
			sbDetail.append(" where u.nsaid != -1 ");
			sbDetail.append(" and u.nclientid = c.id ");
			sbDetail.append(" and c.nofficeid = o.id ");
			sbDetail.append(" and c.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
			if(qc.getStatusID()>-1)
			{
				sbDetail.append(" and u.nstatus ="+qc.getStatusID());
			}
			sbDetail.append(" )");
			
			sbDetail.append(" where 2>1 and status<>0 ");
			if(qc.getStartClientCode().trim().length()>0&&!qc.getStartClientCode().equals(""))
			{
				sbDetail.append(" and clientcode >='"+qc.getStartClientCode().trim()+"'");
			}
			if(qc.getEndClientCode().trim().length()>0&&!qc.getEndClientCode().equals(""))
			{
				sbDetail.append(" and clientcode <='"+qc.getEndClientCode().trim()+"'");
			}
			if(qc.getOfficeID()>-1)
			{
				sbDetail.append(" and officeid = "+qc.getOfficeID());
				
			}
			if(qc.getStartDate().trim().length()>0&&!qc.getStartDate().equals(""))
			{
				sbDetail.append(" and dtchangepassword >= to_date('"+qc.getStartDate().trim()+"','yyyy-mm-dd')");
				
			}
			if(qc.getEndDate().trim().length()>0&&!qc.getEndDate().equals(""))
			{
				sbDetail.append(" and dtchangepassword <= to_date('"+qc.getEndDate().trim()+"','yyyy-mm-dd')");
			}
			if(qc.getIsAdmin()>-1)
			{
				sbDetail.append(" and isadmin ="+qc.getIsAdmin());
			}
			if(qc.getStatusID()>-1)
			{
				sbDetail.append(" and status ="+qc.getStatusID());
			}


			//			
			String strField = " select count(*) \n";
			//
			StringBuffer sb = new StringBuffer();
			sb.append(strField + sbDetail.toString());
			Log.print("=============>" + sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			long lCount = 0;
			if (transRS.next()) {
				lCount = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			// 
			sbDetail.append(" order by  " + qc.getOrder() + " " + qc.getSort()
					+ "\n  ");
			qc.setTotalPages(lCount / qc.getLinesOfPage());
			if (lCount % qc.getLinesOfPage() != 0)
				qc.setTotalPages(qc.getTotalPages() + 1);
			long lRowNumStart = (qc.getCurrentPage() - 1) * qc.getLinesOfPage()
					+ 1;
			long lRowNumEnd = lRowNumStart + qc.getLinesOfPage() - 1;
			//
			
			strField = " select  clientid, userid,clientcode,clientname,officename,loginno,password,isadmin,dtchangepassword,isbelongtoclient,officeid,status,nsaid  \n";
			sb.setLength(0);
			sb.append(" select * from (select a.*,rownum r from  ( " + strField
					+ sbDetail.toString() + " ) a ) \n");
			sb
					.append(" where r between " + lRowNumStart + " and "
							+ lRowNumEnd);
			Log.print(sb.toString());
			System.out.println("sql="+sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OB_UserInfo o = new OB_UserInfo();
				o.setNClientId(transRS.getLong("clientid"));
				o.setId(transRS.getLong("userid"));
				o.setClientCode(transRS.getString("clientcode"));
				o.setClientName(transRS.getString("clientname"));
				o.setOfficeName(transRS.getString("officename"));
				o.setSLoginNo(transRS.getString("loginno"));
				o.setSPassword(transRS.getString("password"));
				o.setIsAdmin(transRS.getLong("isadmin"));
				o.setDtChangePassword(transRS.getTimestamp("dtchangepassword"));
				o.setIsBelongToClient(transRS.getLong("isbelongtoclient"));
				o.setTotalPages(qc.getTotalPages());
				o.setIsBelongToClient(transRS.getLong("isbelongtoclient"));
				o.setNOfficeID(transRS.getLong("officeid"));
				o.setNSaid(transRS.getLong("nsaid"));
				v.add(o);
			}
			Log.print(" v.size " + v.size());

			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 933) {
				throw new IException("查找所有权限发生数据库操作异常，请检查您的输入是否正确", e);
			} else {
				throw new IException("查找所有权限发生数据库操作异常", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return v.size() > 0 ? v : null;
	}
	/**
	 * 查询网上客户可以复核的信息（航天科工）
	 * @param qc
	 * @return
	 * @throws IException
	 */
	public Collection queryCheckOBClientAll(QueryClientSAInfo qc) throws IException

	{

		Vector v = new Vector();

		try {
			this.initDAO();

			StringBuffer sbDetail = new StringBuffer();
			sbDetail.append(" from ob_user a, office b, client c \n");
			sbDetail.append(" where b.id=c.nofficeid \n");
			sbDetail.append(" and c.id = a.NCLIENTID ");
			sbDetail.append(" and (c.ISINSTITUTIONALCLIENT <=0 or c.ISINSTITUTIONALCLIENT is null ) ");
			sbDetail.append(" and a.nusernumber > 0 ");
			sbDetail.append(" and c.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
			if (qc.getStatus() != null) {
				sbDetail.append(" and  a.NSTATUS='" + qc.getStatus() + "' ");
				if(qc.getStatus().longValue()==SYSConstant.SysCheckStatus.UNCHECK)
				{
					sbDetail.append(" and a.ninputuserid != '" + qc.getInputUserID() + "'");
				}
			}

	
			if (qc.getStartClientCode() != null
					&& qc.getStartClientCode().length() > 0)
				sbDetail.append(" and  c.scode>='"
						+ qc.getStartClientCode() + "' ");
			if (qc.getEndClientCode() != null
					&& qc.getEndClientCode().length() > 0)
				sbDetail.append(" and  c.scode<='" + qc.getEndClientCode()
						+ "' ");
			if (qc.getOfficeID() > 0)
				sbDetail.append(" and  b.id= " + qc.getOfficeID());

			//			
			String strField = " select count(*) \n";
			//
			StringBuffer sb = new StringBuffer();
			sb.append(strField + sbDetail.toString());
			
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			long lCount = 0;
			if (transRS.next()) {
				lCount = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			// 
			sbDetail.append(" order by  " + qc.getOrder() + " " + qc.getSort()
					+ "\n  ");
			qc.setTotalPages(lCount / qc.getLinesOfPage());
			if (lCount % qc.getLinesOfPage() != 0)
				qc.setTotalPages(qc.getTotalPages() + 1);
			long lRowNumStart = (qc.getCurrentPage() - 1) * qc.getLinesOfPage()
					+ 1;
			long lRowNumEnd = lRowNumStart + qc.getLinesOfPage() - 1;
			//
			strField = " select a.*, b.sname officename, c.sname clientname,c.scode clientcode \n";
			sb.setLength(0);
			sb.append(" select * from (select a.*,rownum r from  ( " + strField
					+ sbDetail.toString() + " ) a ) \n");
			sb
					.append(" where r between " + lRowNumStart + " and "
							+ lRowNumEnd);
			Log.print(sb.toString());
			System.out.println("=============>" + sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OB_UserInfo o = new OB_UserInfo();
				o.setClientCode(transRS.getString("clientcode"));
				o.setNClientId(transRS.getLong("NCLIENTID"));
				o.setClientName(transRS.getString("clientname"));
				o.setInput(transRS.getTimestamp("dtInput"));
				o.setPrvgLevel(transRS.getString("SPRVGLEVEL"));
				o.setSLoginNo(transRS.getString("SLOGINNO"));
				o.setOfficeName(transRS.getString("officename"));
				o.setSPassword(transRS.getString("SPASSWORD"));
				o.setNStatus(transRS.getLong("NSTATUS"));
				o.setTotalPages(qc.getTotalPages());
				o.setNSaid(transRS.getLong("NSAID"));
				o.setId(transRS.getLong("ID"));
				o.setSCertNo(transRS.getString("sCertNo"));	
				
				//added by mzh_fu 2008/02/02
				o.setSCertCn(transRS.getString("scertCn"));
				
				v.add(o);
			}
			Log.print(" v.size " + v.size());

			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 933) {
				throw new IException("查找所有权限发生数据库操作异常，请检查您的输入是否正确", e);
			} else {
				throw new IException("查找所有权限发生数据库操作异常", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return v.size() > 0 ? v : null;
	}

	/**
	 * 查询网银复核用户（集成平台）
	 * @param qc
	 * @return
	 * @throws IException
	 */
	public Collection queryCheckClientAll(QueryClientSAInfo qc) throws IException

	{

		Vector v = new Vector();

		try {
			this.initDAO();

			StringBuffer sbDetail = new StringBuffer();
			sbDetail.append(" from ob_user a, office b, client c, ob_admin_of_user d \n");
			sbDetail.append(" where b.id=c.nofficeid \n");
			sbDetail.append(" and a.id = d.initialuserid ");
			sbDetail.append(" and d.clientid = c.id ");
			sbDetail.append(" and c.id = a.NCLIENTID ");
			sbDetail.append(" and (c.ISINSTITUTIONALCLIENT <=0 or c.ISINSTITUTIONALCLIENT is null ) ");
			sbDetail.append(" and c.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
			if (qc.getStatus() != null) {
				sbDetail.append(" and  a.NSTATUS='" + qc.getStatus() + "' ");
				if(qc.getStatus().longValue()==SYSConstant.SysCheckStatus.UNCHECK)
				{
					sbDetail.append(" and a.ninputuserid != '" + qc.getInputUserID() + "'");
				}
			}

	
			if (qc.getStartClientCode() != null
					&& qc.getStartClientCode().length() > 0)
				sbDetail.append(" and  c.scode>='"
						+ qc.getStartClientCode() + "' ");
			if (qc.getEndClientCode() != null
					&& qc.getEndClientCode().length() > 0)
				sbDetail.append(" and  c.scode<='" + qc.getEndClientCode()
						+ "' ");
			if (qc.getOfficeID() > 0)
				sbDetail.append(" and  b.id= " + qc.getOfficeID());

			//			
			String strField = " select count(*) \n";
			//
			StringBuffer sb = new StringBuffer();
			sb.append(strField + sbDetail.toString());
			
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			long lCount = 0;
			if (transRS.next()) {
				lCount = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			// 
			sbDetail.append(" order by  " + qc.getOrder() + " " + qc.getSort()
					+ "\n  ");
			qc.setTotalPages(lCount / qc.getLinesOfPage());
			if (lCount % qc.getLinesOfPage() != 0)
				qc.setTotalPages(qc.getTotalPages() + 1);
			long lRowNumStart = (qc.getCurrentPage() - 1) * qc.getLinesOfPage()
					+ 1;
			long lRowNumEnd = lRowNumStart + qc.getLinesOfPage() - 1;
			//
			strField = " select a.*, b.sname officename, c.sname clientname,c.scode clientcode \n";
			sb.setLength(0);
			sb.append(" select * from (select a.*,rownum r from  ( " + strField
					+ sbDetail.toString() + " ) a ) \n");
			sb
					.append(" where r between " + lRowNumStart + " and "
							+ lRowNumEnd);
			Log.print(sb.toString());
			System.out.println("=============>" + sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OB_UserInfo o = new OB_UserInfo();
				o.setClientCode(transRS.getString("clientcode"));
				o.setNClientId(transRS.getLong("NCLIENTID"));
				o.setClientName(transRS.getString("clientname"));
				o.setInput(transRS.getTimestamp("dtInput"));
				o.setPrvgLevel(transRS.getString("SPRVGLEVEL"));
				o.setSLoginNo(transRS.getString("SLOGINNO"));
				o.setOfficeName(transRS.getString("officename"));
				o.setSPassword(transRS.getString("SPASSWORD"));
				o.setNStatus(transRS.getLong("NSTATUS"));
				o.setTotalPages(qc.getTotalPages());
				o.setNSaid(transRS.getLong("NSAID"));
				o.setId(transRS.getLong("ID"));
				o.setSCertNo(transRS.getString("sCertNo"));	
				
				//added by mzh_fu 2008/02/02
				o.setSCertCn(transRS.getString("scertCn"));
				
				v.add(o);
			}
			Log.print(" v.size " + v.size());

			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 933) {
				throw new IException("查找所有权限发生数据库操作异常，请检查您的输入是否正确", e);
			} else {
				throw new IException("查找所有权限发生数据库操作异常", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return v.size() > 0 ? v : null;
	}

	/**
	 * @param clientID
	 * @return
	 */
	public Collection findUserByClientID(long clientID) throws IException {
		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" select a.id, a.dtInput, a.nofficeid, a.sname, ");
			sbSQL.append(" a.ncurrencyid, a.nInputUserId, ");
			sbSQL
					.append(" a.sLoginNo,a.sPassword,a.nsaid,b.sname as inputusername ");
			sbSQL.append(" from ob_user a,ob_user b  ");
			sbSQL.append("");
			sbSQL.append(" where a.nclientid = " + clientID + " ");
			sbSQL.append(" and a.nstatus = 1  ");
			sbSQL.append(" and a.ninputuserid = b.id(+) ");
			sbSQL.append(" order by a.id ");
			log.print("findUserByClientID 的sQl 语句未 \t" + sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OB_UserInfo o = new OB_UserInfo();
				o.setId(transRS.getLong("id"));
				o.setInput(transRS.getTimestamp("dtInput"));
				o.setNClientId(clientID);
				o.setNOfficeID(transRS.getLong("nofficeid"));
				o.setSName(transRS.getString("sName"));
				o.setNCurrencyId(transRS.getLong("ncurrencyId"));
				// o.setNDepartmentID(transRS.getLong("nDepartmentId"));
				o.setNInputUserID(transRS.getLong("nInputUserId"));
				o.setSLoginNo(transRS.getString("sLoginNo"));
				o.setSPassword(transRS.getString("sPassword"));
				o.setInputUserName(transRS.getString("inputusername"));
				v.add(o);
			}
			this.finalizeDAO();

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return v.size() > 0 ? v : null;
	}

	/**
	 * @param clientID
	 * @return
	 */
	public Collection findGroupsByClient(long clientID) throws IException {
		Vector v = new Vector();
		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select * from ob_group where clientid =  "
					+ clientID + " ");

			log.print("findGroupsByClient 的sQl 语句未 \t" + sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OB_GroupInfo o = new OB_GroupInfo();
				o.setId(transRS.getLong("id"));
				o.setInputDate(transRS.getTimestamp("inputdate"));
				o.setClientId(transRS.getLong("clientid"));
				o.setOfficeID(transRS.getLong("officeid"));
				o.setName(transRS.getString("name"));

				o.setInputUserID(transRS.getLong("InputUserId"));

				v.add(o);
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}

	/**
	 * 保存账户交易类型设置 Create Date: 2003-8-13
	 * 
	 * @param Collection
	 * @return long 大于0表示成功，小于0表示保存失败
	 * @throws IException
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 * @exception Exception
	 */
	public long addAccountPrvg(Collection c) throws IException {
		// 定义变量

		Iterator it = null;
		AccountPrvgInfo info = null;
		long lReturn = -1;
		try {
			this.initDAO();
			StringBuffer sbSql = new StringBuffer();

			if (c != null) {
				it = c.iterator();
				it.hasNext();
				info = (AccountPrvgInfo) it.next();

				if (info != null) {
					sbSql
							.append(" DELETE FROM OB_AccountPrvg  WHERE  nAccountID = "
									+ info.getAccountID());
					//log.print(sbSql.toString());
					System.out.println("=====add444======" + sbSql.toString());
					transPS = transConn.prepareStatement(sbSql.toString());
					lReturn = transPS.executeUpdate();

					if (transPS != null) {
						transPS.close();
						transPS = null;
					}
				}

				it = c.iterator();
				for (int i = 0; it.hasNext(); i++) {
					info = (AccountPrvgInfo) it.next();

					if (info.getTypeID() != -1) {

						sbSql.setLength(0);

						sbSql.append(" INSERT INTO  OB_AccountPrvg ");
						sbSql
								.append(" (nAccountID,nTransType,nSetUserID,dtSet) ");
						sbSql.append(" values (?,?,?,sysdate )");
						//log.print(sbSql.toString());

						transPS = transConn.prepareStatement(sbSql.toString());

						transPS.setLong(1, info.getAccountID());
						transPS.setLong(2, info.getTypeID());
						transPS.setLong(3, info.getInputUserID());
						lReturn = transPS.executeUpdate();

						if (transPS != null) {
							transPS.close();
							transPS = null;
						}
					}
				}
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

		return lReturn;
	}

	/**
	 * 获取所有交易类型 Create Date: 2003-8-13
	 * 
	 * @param lAccountID
	 *            账户ID
	 * @return Collection
	 * @throws IException
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 * @exception Exception
	 */
	public Collection getInitAccountPrvgByAccountNo(String strAccountNo)
			throws IException

	{

		AccountPrvgInfo info = null;
		Vector vResult = new Vector();
		long lAccountID = -1;
		long lAccountTypeID = -1;

		boolean CAPTRANSFER_SELF = false; // 资金划拨-本转
		boolean CAPTRANSFER_BANKPAY = false; // 资金划拨-银行付款
		boolean CAPTRANSFER_INTERNALVIREMENT = false; // 资金划拨-内部转账
		boolean OPENFIXDEPOSIT = false; // 定期开立
		boolean FIXEDTOCURRENTTRANSFER = false; // 定期支取
		boolean DRIVEFIXDEPOSIT = false; //定期续存
		boolean OPENNOTIFYACCOUNT = false; // 通知开立
		boolean NOTIFYDEPOSITDRAW = false; // 通知支取
		boolean TRUSTLOANRECEIVE = false; // 自营贷款清还
		boolean CONSIGNLOANRECEIVE = false; // 委托贷款清还
		boolean INTERESTFEEPAYMENT = false; // 利息费用清还

		try {
			this.initDAO();
			transPS = transConn
					.prepareStatement("select id,NACCOUNTTYPEID from sett_account where sAccountNo ='"
							+ strAccountNo + "' and nStatusID=1 and NCHECKSTATUSID=4 ");
			System.out
					.println("========SQL33333====== select id,NACCOUNTTYPEID from sett_account where sAccountNo ='"
							+ strAccountNo + "'");
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				lAccountID = transRS.getLong("id");
				lAccountTypeID = transRS.getLong("NACCOUNTTYPEID");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;

			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
					|| SETTConstant.AccountType
							.isOtherDepositAccountType(lAccountTypeID)) {
				CAPTRANSFER_SELF = true; // 资金划拨-本转
				CAPTRANSFER_BANKPAY = true; // 资金划拨-银行付款
				CAPTRANSFER_INTERNALVIREMENT = true; // 资金划拨-内部转账
				OPENFIXDEPOSIT = true; // 定期开立
				FIXEDTOCURRENTTRANSFER = true; // 定期支取
				DRIVEFIXDEPOSIT = true; //定期续存
				OPENNOTIFYACCOUNT = true; // 通知开立
				NOTIFYDEPOSITDRAW = true; // 通知支取
				TRUSTLOANRECEIVE = true; // 自营贷款清还
				CONSIGNLOANRECEIVE = true; // 委托贷款清还
				INTERESTFEEPAYMENT = true; // 利息费用清还
			} else if (SETTConstant.AccountType
					.isFixAccountType(lAccountTypeID)) {
				OPENFIXDEPOSIT = true; // 定期开立
				FIXEDTOCURRENTTRANSFER = true; // 定期支取
				DRIVEFIXDEPOSIT = true; //定期续存
			} else if (SETTConstant.AccountType
					.isNotifyAccountType(lAccountTypeID)) {
				OPENNOTIFYACCOUNT = true; // 通知开立
				NOTIFYDEPOSITDRAW = true; // 通知支取
			} else if (SETTConstant.AccountType
					.isTrustAccountType(lAccountTypeID)) {
				// TRUSTLOANRECEIVE = true; //自营贷款清还
				// INTERESTFEEPAYMENT = true; //利息费用清还
			} else if (SETTConstant.AccountType
					.isConsignAccountType(lAccountTypeID)) {
				// CONSIGNLOANRECEIVE = true; //委托贷款清还
				// INTERESTFEEPAYMENT = true; //利息费用清还
			}

			if (CAPTRANSFER_SELF == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); // 资金划拨
				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_SELF);
				info.setValue(CAPTRANSFER_SELF);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 对外付款
			if (CAPTRANSFER_BANKPAY == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); // 资金划拨
				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
				info.setValue(CAPTRANSFER_BANKPAY);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 内部转账
			if (CAPTRANSFER_INTERNALVIREMENT == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); // 资金划拨
				info
						.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
				info.setValue(CAPTRANSFER_INTERNALVIREMENT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 定期开立
			if (OPENFIXDEPOSIT == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); // 定期存款
				info.setTypeID(OBConstant.SettInstrType.OPENFIXDEPOSIT);
				info.setValue(OPENFIXDEPOSIT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 定期支取
			if (FIXEDTOCURRENTTRANSFER == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); // 定期存款
				info.setTypeID(OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER);
				info.setValue(FIXEDTOCURRENTTRANSFER);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}
			
			//定期续存
			if (DRIVEFIXDEPOSIT  == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); // 定期存款
				info.setTypeID(OBConstant.SettInstrType.DRIVEFIXDEPOSIT);
				info.setValue(FIXEDTOCURRENTTRANSFER);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 通知开立
			if (OPENNOTIFYACCOUNT == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.NOTISFY); // 通知存款
				info.setTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);
				info.setValue(OPENNOTIFYACCOUNT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 通知支取
			if (NOTIFYDEPOSITDRAW == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.NOTISFY); // 通知存款
				info.setTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
				info.setValue(NOTIFYDEPOSITDRAW);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 自营贷款清还
			if (TRUSTLOANRECEIVE == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); // 贷款清还
				info.setTypeID(OBConstant.SettInstrType.TRUSTLOANRECEIVE);
				info.setValue(TRUSTLOANRECEIVE);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 委托贷款清还
			if (CONSIGNLOANRECEIVE == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); // 贷款清还
				info.setTypeID(OBConstant.SettInstrType.CONSIGNLOANRECEIVE);
				info.setValue(CONSIGNLOANRECEIVE);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			// 利息费用清还
			if (INTERESTFEEPAYMENT == true) {
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); // 贷款清还
				info.setTypeID(OBConstant.SettInstrType.INTERESTFEEPAYMENT);
				info.setValue(INTERESTFEEPAYMENT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		Log.print("++++++++++++++++++++++++++++++++++v.size=" + vResult.size());
		return vResult.size() > 0 ? vResult : null;
	}

	/**
	 * 查找所有的客户
	 * 
	 * @throws IException
	 */
	public void updateUserSAId(long userId) throws IException

	{

		try {
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" update ob_user set nsaid=id where id=" + userId);

			transPS = transConn.prepareStatement(sbSQL.toString());

			transPS.executeUpdate();

			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}

	}

	public long checkUserCertNo(String strLoginNo, String strDN)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		UserBean u = new UserBean();
		String strDN1 = "";
		String strDN2 = "";
		long lResult = -1;
		long lMaxID = -1;
		try {
			sb.append("select sCertNo from ob_user where sLoginNo ='"
					+ strLoginNo + "'");
			System.out.println(sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				if (strDN != null && (!"".equals(strDN))) {
					strDN1 = transRS.getString("sCertNo").trim();

					if (strDN1 != null && (!"".equals(strDN1))) {
						strDN2 = u.getCN(strDN).trim();
						if (strDN2 != null && (!"".equals(strDN2))) {
							if (strDN1.equalsIgnoreCase(strDN2)) {
								lResult = 1;
							} else {
								System.out.println("不匹配的签名对: (" + strDN1
										+ ") 和 (" + strDN2 + ")");
							}
						}

					}

				}

			}

			sb.setLength(0);
			this.finalizeDAO();

			//
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				this.finalizeDAO();
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return lResult;
		// ID, SNAME, SLOGINNO, SPASSWORD, NOFFICEID, NUSERGROUPID, NCURRENCYID,
		// NINPUTUSERID, DTINPUT, NSTATUSID
	}

	public OB_UserInfo getUserByID(long lUserID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		OB_UserInfo pi = new OB_UserInfo();
		try {
			conn = Database.getConnection();
			sb
					.append(" select userinfo.ID id, userinfo.SNAME name , userinfo.SLOGINNO loginno, userinfo.SPASSWORD password, ");
			sb
					.append("        userinfo.NOFFICEID officeid, userinfo.NUSERGROUPID groupid, userinfo.NCURRENCYID currencyid, ");
			sb
					.append("        userinfo.NINPUTUSERID inputuserid, userinfo.DTINPUT datainput, userinfo.NSTATUS nstatusid, userinfo.SPRVGLEVEL sLevel, ");
			sb
					.append("        office.sname officename, input.sname inputusername \n");
			sb.append(" from userinfo userinfo, office, userinfo input \n");
			sb
					.append(" where input.id(+)=userinfo.ninputuserid and office.id(+)=userinfo.nofficeid and userinfo.id=? \n");
			// Log.print(" Userinfo sql : " + sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lUserID);
			rs = ps.executeQuery();
			// Ldap ldap = new Ldap();
			Sys_Group_Of_UserDAO sys_Group_Of_UserDAO = new Sys_Group_Of_UserDAO();
			if (rs.next()) {
				pi.setId(rs.getLong("id"));
				pi.setSName(rs.getString("name"));
				pi.setSLoginNo(rs.getString("loginno"));
				pi.setSPassword(rs.getString("password"));
				pi.setNOfficeID(rs.getLong("officeid"));
				// pi.vGroupID = ldap.getUserByDN(pi.strLogin).vGroupID;
				// pi.vGroupID =(Vector)
				// sys_Group_Of_UserDAO.findGroupByUserId(pi.getId());
				pi.setNCurrencyId(rs.getLong("currencyid"));
				pi.setNInputUserID(rs.getLong("inputuserid"));
				pi.setInput(rs.getTimestamp("datainput"));
				pi.setNStatus(rs.getLong("nstatusid"));
				pi.setOfficeName(rs.getString("officename"));
				pi.setInputUserName(rs.getString("inputusername"));
				pi.setPrvgLevel(rs.getString("sLevel"));
				// pi.lCloseSystem = rs.getLong("nCloseSystem");
				// pi.lIsAdminUser = rs.getLong("nIsAdminUser");
				// pi.lIsVirtualUser = rs.getLong("nIsVirtualUser");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null){
					conn.close();
				 conn=null;}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return pi;
	}

	/**
	 * 覆盖基类的findByCondition 方法，由于中电子增加了密码的二进制字段，因而不能再用基类的方法
	 * 
	 * @author zntan
	 * @param userInfoCondition
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByCondition(OB_UserInfo userInfoCondition)
			throws ITreasuryDAOException {
		Vector vectTemp = new Vector();
		try {
			// TODO Auto-generated method stub
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ob_User  where ");
			String strs[] = this.getAllFieldNameBuffer(userInfoCondition,
					DAO_OPERATION_FIND);
			sb.append(strs[0]);
			transPS = transConn.prepareStatement(sb.toString());
			System.out.println("========>SQL:" + sb.toString());
			setPrepareStatementByDataEntity(userInfoCondition,
					DAO_OPERATION_FIND, strs[0]);
			transRS = transPS.executeQuery();
			System.out.println("effffffffffffff");
			while (transRS != null && transRS.next()) {
				OB_UserInfo info = new OB_UserInfo();

				info.setId(transRS.getLong("ID"));
				info.setInput(transRS.getTimestamp("dtInput"));
				info.setNClientId(transRS.getLong("nClientID"));
				info.setNCurrencyId(transRS.getLong("nCurrencyID"));
				info.setNInputUserID(transRS.getLong("nInputUserID"));
				info.setNOfficeID(transRS.getLong("nOfficeID"));
				info.setNSaid(transRS.getLong("nSaid"));
				info.setNStatus(transRS.getLong("nStatus"));
				info.setPrvgLevel(transRS.getString("sPrvgLevel"));
				info.setSCertNo(transRS.getString("sCertNo"));
				info.setSCurrencyId(transRS.getString("sCurrencyID"));
				info.setSLoginNo(transRS.getString("sLoginNo"));
				info.setSName(transRS.getString("sName"));
				info.setSPassword(transRS.getString("sPassword"));

				// info.setId(transRS.getLong("ID"));
				// info.setSName(transRS.getString("sName"));
				// info.setNClientId(transRS.getLong("nClientID"));
				// info.setNCurrencyId(transRS.getLong("nCurrencyID"));
				// info.setSLoginNo(transRS.getString("sLoginNo"));
				// info.setSPassword(transRS.getString("sPassword"));
				// info.setNInputUserID(transRS.getLong("nInputUserID"));
				// info.setInput(transRS.getTimestamp("dtInput"));
				// info.setNStatus(transRS.getLong("nStatus"));
				// info.setNSaid(transRS.getLong("nSaid"));
				// info.setPrvgLevel(transRS.getString("sPrvgLevel"));
				// info.setSCurrencyId(transRS.getString("sCurrencyID"));
				// info.setUserGroupId(transRS.getLong("nUserGroupId"));
				// info.setNOfficeID(transRS.getLong("nOfficeID"));
				// info.setSCertNo(transRS.getString("sCertNo"));
				info.setNIsVirtualUser(transRS.getLong("nIsVirtualUser"));

				vectTemp.add(info);
			}
			System.out.println("TTTTTTTTTT" + vectTemp != null + "TTTTTTTTTTT");
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			finalizeDAO();
		}
		return vectTemp.size() > 0 ? vectTemp : null;

	}

	/**
	 * 覆盖基类的findByID 因为中电子增加二进制字段
	 * 
	 * @param id
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public ITreasuryBaseDataEntity findByID(long id, Class className)
			throws ITreasuryDAOException {
		OB_UserInfo info = null;
		initDAO();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE id = " + id);
		String strSQL = buffer.toString();
		log.debug(strSQL);
		try {
			prepareStatement(strSQL);
			executeQuery();
			while (transRS != null && transRS.next()) {
				info = new OB_UserInfo();
				info.setId(transRS.getLong("ID"));
				info.setInput(transRS.getTimestamp("dtInput"));
				info.setNClientId(transRS.getLong("nClientID"));
				info.setNCurrencyId(transRS.getLong("nCurrencyID"));
				info.setNInputUserID(transRS.getLong("nInputUserID"));
				info.setNOfficeID(transRS.getLong("nOfficeID"));
				info.setNSaid(transRS.getLong("nSaid"));
				info.setNStatus(transRS.getLong("nStatus"));
				info.setPrvgLevel(transRS.getString("sPrvgLevel"));
				info.setSCertNo(transRS.getString("sCertNo"));
				
				//added by mzh_fu 2008/02/02
				info.setSCertCn(transRS.getString("sCertCn"));
				
				info.setSCurrencyId(transRS.getString("sCurrencyID"));
				info.setSLoginNo(transRS.getString("sLoginNo"));
				info.setSName(transRS.getString("sName"));
				info.setSPassword(transRS.getString("sPassword"));
				info.setNIsVirtualUser(transRS.getLong("nisvirtualuser"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new ITreasuryDAOException(" findById 出错", e1);
		}
		finalizeDAO();
		return info;
	}

	// 根据指定的网银客户集合查询其相关信息
	public Collection findUserByCondition(long sloginno,long status,long isval) throws Exception {

		Vector v = new Vector();
//		String[] StrLoginno = sloginno.split(",");
//		int len = StrLoginno.length;
//		String[] StrLoginno1 = new String[len];
		try {
			// String strSQL ="";
			this.initDAO();
			StringBuffer buffer = new StringBuffer();

			buffer.append("select  \n");
			buffer.append("c.id id,  \n");
			buffer.append("c.name name  \n");
			buffer.append("from \n");
			buffer.append("Sett_OBQueryPrivilegeSetting s, \n");
			buffer.append("client_clientinfo c\n");
			buffer.append("where  \n");
			buffer.append(" s.childclientid = c.id \n");
			buffer.append("and s.statusid= \n");
			buffer.append("'"+status+"'");
			buffer.append(" and isvalid=\n");
			buffer.append("'"+isval+"'");		
			buffer.append(" and  \n");
			buffer.append(" s.parentclientid=  \n");
			buffer.append("'" + sloginno + "'");
			buffer.append(" and s.childclientid <> \n");
			buffer.append("'" + sloginno + "'");

			// buffer.append(sloginno );

//			if (StrLoginno != null && StrLoginno.length > 0) {
//				buffer.append(" and CODE in ( \n");
//				for (int i = 0; i < len; i++) {
//					// buffer.append(""+sloginno[i]);
//					StrLoginno1[i] = StrLoginno[i];
//					sloginno = StrLoginno1[i];
//					buffer.append("'" + StrLoginno1[i] + "'");
//					if (i < len - 1) {
//						buffer.append(",");
//					}
//					//					
//					//					
//				}
//				buffer.append(" )  \n");
//			}
			System.out.println("sql：" + buffer.toString());
			transPS = transConn.prepareStatement(buffer.toString());

			transRS = transPS.executeQuery();
			while (transRS.next()) {

				OB_UserInfo info = new OB_UserInfo();
				info.setClientName(NameRef.getClientNameByID(transRS
						.getLong("id")));

				info.setNClientId(transRS.getLong("id"));
				System.out.println("客户ID:" + info.getNClientId());
				System.out.println("客户名称:" + info.getClientName());

				v.add(info);
			}
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生错误", e);
			// throw new IException(null,e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查找所有权限发生数据库操作异常", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("其他异常", e);
		} finally {

			this.finalizeDAO();

		}
		return v.size() > 0 ? v : null;
	}
	
	public LoginUserInfo getLoginUserInfo(String strLogin, String strPassword)  throws ITreasuryDAOException {
		LoginUserInfo loginInfo = null;
		StringBuffer strSQL = new StringBuffer();
	     try {
	         /*-----------------init DAO --------------------*/
	         try {
	           initDAO();
	         }
	         catch (ITreasuryDAOException e) {
	            throw new ITreasuryDAOException("创建连接时异常",e);
	         }
	         /*----------------end of init------------------*/
	         try {
	             if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
	             {
		        	 if(EncryptManager.getOBBeanFactory().checkOBPassword(strLogin, strPassword)){
		        		 strSQL.append(" select a.id as id ,a.sName as sUserName,a.ncurrencyID as lCurrencyID ,a.nsaid,a.scertcn as certcn, a.scertno as certsn, ");
		        		 strSQL.append(" b.id as lClientID,a.nOfficeID as lOfficeID,b.sName as sClientName,");
		        		 strSQL.append(" b.sCode as sClientCode,b.sSimpleName, c.sName as sOfficeName,c.sCode as sOfficeNo");
		        		 strSQL.append(",a.scertno as CertSerialNumber ");
		        		 strSQL.append(" ,a.scertcn as subjectCommonName, d.accesstime accesstime");
		        		 strSQL.append(" from ob_user a, client b, office c , "); 
		        		 strSQL.append("     (select b.clientid, max(b.accesstime) accesstime from ob_user a,sys_loginrecord b where a.sloginno =? and a.nclientid = b.clientid and b.usertype="+ Constant.EBANK_USER +" group by b.clientid) d");
			        	 strSQL.append(" where a.nclientid=b.id(+)");
			        	 strSQL.append(" and a.nofficeID=c.id(+)");
			        	 strSQL.append(" and a.nclientid = d.clientid(+)");
			        	 strSQL.append(" and a.sloginno=?");
			        	 strSQL.append(" and a.nStatus = " + Constant.RecordStatus.VALID);
	
		        		 prepareStatement(strSQL.toString());
			        	 transPS.setString(1, strLogin);
			        	 transPS.setString(2, strLogin);
		        	 }
		        	 else
		        	 {
		        		throw new IException("用户名或密码不正确,请重新登录");
		        	 }
	             }
		         else 
		         {
		        	 strSQL.append(" select a.id as id ,a.sName as sUserName,a.ncurrencyID as lCurrencyID ,a.nsaid,a.scertcn as certcn,a.scertno as certsn, ");
		        	 strSQL.append(" a.scertno as CertSerialNumber,"); 
		        	 strSQL.append(" a.scertcn as subjectCommonName,");  
		        	 strSQL.append(" b.id as lClientID,a.nOfficeID as lOfficeID,b.sName as sClientName,");
		        	 strSQL.append(" b.sCode as sClientCode,b.sSimpleName, c.sName as sOfficeName, c.sCode as sOfficeNo, d.accesstime accesstime");
		        	 strSQL.append(" from ob_user a, client b, office c, ");
		        	 strSQL.append("     (select b.clientid, max(b.accesstime) accesstime from ob_user a,sys_loginrecord b where a.sloginno =? and a.nclientid = b.clientid and b.usertype="+ Constant.EBANK_USER +" group by b.clientid) d");
		        	 strSQL.append(" where a.nclientid=b.id(+)");
		        	 strSQL.append(" and a.nofficeID=c.id(+)");
		        	 strSQL.append(" and a.nclientid = d.clientid(+)");
		        	 strSQL.append(" and a.sloginno=?");
		        	 strSQL.append(" and a.spassword=?");
		        	 strSQL.append(" and a.nStatus = " + Constant.RecordStatus.VALID);

		        	 prepareStatement(strSQL.toString());
		        	 transPS.setString(1, strLogin);
		        	 transPS.setString(2, strLogin);
		        	 transPS.setString(3, strPassword);
		         }
	             executeQuery();
	 		     if(this.transRS.next()) {
	 		    	loginInfo = new LoginUserInfo();
	 		    	loginInfo.setId(transRS.getLong("id"));
	 		    	loginInfo.setUsername(transRS.getString("sUserName"));
	 		    	loginInfo.setCurrencyId(transRS.getLong("lCurrencyID"));
	 		    	loginInfo.setSaId(transRS.getLong("nsaid"));
	 		    	loginInfo.setCertSerialNumber(transRS.getString("CertSerialNumber"));
	 		    	loginInfo.setSubjectCommonName(transRS.getString("subjectCommonName"));
	 		    	loginInfo.setClientID(transRS.getLong("lClientID"));
	 		    	loginInfo.setOfficeID(transRS.getLong("lOfficeID"));
	 		    	loginInfo.setClientName(transRS.getString("sClientName"));
	 		    	loginInfo.setClientCode(transRS.getString("sClientCode"));
	 		    	loginInfo.setSimpleName(transRS.getString("sSimpleName"));
	 		    	loginInfo.setOfficeName(transRS.getString("sOfficeName"));
	 		    	loginInfo.setOfficeNo(transRS.getString("sOfficeNo"));
                    loginInfo.setLastLoginTime(transRS.getTimestamp("accesstime"));
                    loginInfo.setCertcn(transRS.getString("certcn"));
                    loginInfo.setCertsn(transRS.getString("certsn"));
               
                    switch ((int) loginInfo.getCurrencyId())
                    {
	                    case (int) Constant.CurrencyType.RMB:
	                    	loginInfo.setCurrencyIdSymbol("￥");
	                        break;
	                    case (int) Constant.CurrencyType.USD:
	                    	loginInfo.setCurrencyIdSymbol("＄");
	                        break;
	                    case (int) Constant.CurrencyType.ED:
	                    	loginInfo.setCurrencyIdSymbol("E");
	                        break;
	                    case (int) Constant.CurrencyType.UKP:
	                    	loginInfo.setCurrencyIdSymbol("￡");
	                        break;
	                    default:
	                    	loginInfo.setCurrencyIdSymbol("￥");
	                    	break;
                    }
	 			 }
	         }
	         catch (Exception e) {
	           throw new ITreasuryDAOException("查询出错", e);
	         }
	         
	         /*----------------finalize Dao-----------------*/
	         try {
	           finalizeDAO();
	         }
	         catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("关闭连接时异常",e);
	         }
	         /*----------------end of finalize---------------*/
	     }
	     catch (Exception e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
	       throw new ITreasuryDAOException("查询异常",e);
	     }
	     finally {
	       finalizeDAO();
	     }
		return loginInfo;
	}
	
	public LoginUserInfo getLoginUserInfo(String strLogin, String strPassword, String integration)  throws ITreasuryDAOException {
		LoginUserInfo loginInfo = null;
		StringBuffer strSQL = new StringBuffer();
	     try {
	         /*-----------------init DAO --------------------*/
	         try {
	           initDAO();
	         }
	         catch (ITreasuryDAOException e) {
	            throw new ITreasuryDAOException("创建连接时异常",e);
	         }
	         /*----------------end of init------------------*/
	         try {
	        	 strSQL.append(" select a.id as id ,a.sName as sUserName,a.ncurrencyID as lCurrencyID ,a.nsaid, ");
	        	 strSQL.append(" a.scertno as CertSerialNumber,"); 
	        	 strSQL.append(" a.scertcn as subjectCommonName,");  
	        	 strSQL.append(" b.id as lClientID,a.nOfficeID as lOfficeID,b.sName as sClientName,");
	        	 strSQL.append(" b.sCode as sClientCode,b.sSimpleName, c.sName as sOfficeName, c.sCode as sOfficeNo, d.accesstime accesstime");
	        	 strSQL.append(" from ob_user a, client b, office c, ");
	        	 strSQL.append("     (select b.clientid, max(b.accesstime) accesstime from ob_user a,sys_loginrecord b where a.sloginno =? and a.nclientid = b.clientid and b.usertype="+ Constant.EBANK_USER +" group by b.clientid) d");
	        	 strSQL.append(" where a.nclientid=b.id(+)");
	        	 strSQL.append(" and a.nofficeID=c.id(+)");
	        	 strSQL.append(" and a.nclientid = d.clientid(+)");
	        	 strSQL.append(" and a.sloginno=?");
	        	 //如果配置了启用ldap查询，则登陆时就不需要验证业务登陆密码，否则，就需要验证密码是否和数据库中一致才可以登陆
	 			 if (!Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_LDAP,false)){
	 				 strSQL.append(" and a.spassword=?");
	 			 }
	        	 strSQL.append(" and a.nStatus = " + Constant.RecordStatus.VALID);

	        	 prepareStatement(strSQL.toString());
	        	 transPS.setString(1, strLogin);
	        	 transPS.setString(2, strLogin);
	        	 //如果配置了启用ldap查询，则登陆时就不需要验证业务登陆密码，否则，就需要验证密码是否和数据库中一致才可以登陆
	 			 if (!Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_LDAP,false)){
	 				 transPS.setString(3, strPassword);
	 			 }
	             executeQuery();
	 		     if(this.transRS.next()) {
	 		    	loginInfo = new LoginUserInfo();
	 		    	loginInfo.setId(transRS.getLong("id"));
	 		    	loginInfo.setUsername(transRS.getString("sUserName"));
	 		    	loginInfo.setCurrencyId(transRS.getLong("lCurrencyID"));
	 		    	loginInfo.setSaId(transRS.getLong("nsaid"));
	 		    	loginInfo.setCertSerialNumber(transRS.getString("CertSerialNumber"));
	 		    	loginInfo.setSubjectCommonName(transRS.getString("subjectCommonName"));
	 		    	loginInfo.setClientID(transRS.getLong("lClientID"));
	 		    	loginInfo.setOfficeID(transRS.getLong("lOfficeID"));
	 		    	loginInfo.setClientName(transRS.getString("sClientName"));
	 		    	loginInfo.setClientCode(transRS.getString("sClientCode"));
	 		    	loginInfo.setSimpleName(transRS.getString("sSimpleName"));
	 		    	loginInfo.setOfficeName(transRS.getString("sOfficeName"));
	 		    	loginInfo.setOfficeNo(transRS.getString("sOfficeNo"));
                    loginInfo.setLastLoginTime(transRS.getTimestamp("accesstime"));
	 		    	
                    switch ((int) loginInfo.getCurrencyId())
                    {
	                    case (int) Constant.CurrencyType.RMB:
	                    	loginInfo.setCurrencyIdSymbol("￥");
	                        break;
	                    case (int) Constant.CurrencyType.USD:
	                    	loginInfo.setCurrencyIdSymbol("＄");
	                        break;
	                    case (int) Constant.CurrencyType.ED:
	                    	loginInfo.setCurrencyIdSymbol("E");
	                        break;
	                    case (int) Constant.CurrencyType.UKP:
	                    	loginInfo.setCurrencyIdSymbol("￡");
	                        break;
	                    default:
	                    	loginInfo.setCurrencyIdSymbol("￥");
	                    	break;
                    }
	 			 }
	         }
	         catch (Exception e) {
	           throw new ITreasuryDAOException("查询出错", e);
	         }
	         
	         /*----------------finalize Dao-----------------*/
	         try {
	           finalizeDAO();
	         }
	         catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("关闭连接时异常",e);
	         }
	         /*----------------end of finalize---------------*/
	     }
	     catch (Exception e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
	       throw new ITreasuryDAOException("查询异常",e);
	     }
	     finally {
	       finalizeDAO();
	     }
		return loginInfo;
	}
	
	/**
	 * 
	 * 更新密码，把加密得到的二进制数据写入数据库，操作数据库表ob_user
	 * @param lUserID 网银用户id
	 * @param newPass 密码
	 */
	public void saveEncryptPassword(long lUserID,byte[] encryptPassword) throws ITreasuryDAOException
	{		
		try
    	{
			initDAO();
			
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(encryptPassword);
			String strSQL = " update ob_user set EncryptPassword=?, spassword = ?, dtchangepassword = ? where id=? ";
			prepareStatement(strSQL);
			this.transPS.setBinaryStream(1,is,is.available());
			this.transPS.setString(2,"");
			this.transPS.setTimestamp(3, Env.getSystemDateTime());
			this.transPS.setLong(4,lUserID);
			
			executeUpdate();
    	}
		catch (Exception e)
		{
			throw new ITreasuryDAOException("保存加密密码时出错，" + e.getMessage(),e);
		}
		finally
		{
			finalizeDAO();
		}
	}	
	
	public boolean validateAccount(long lUserID) throws ITreasuryDAOException
	{
		boolean bReturn = false;
		try
		{
			initDAO();
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from OB_AccountOwnedBySA o ");
			sql.append(" where o.nsaid ="+lUserID);
			prepareStatement(sql.toString());
			executeQuery();
			while(this.transRS.next())
			{
				bReturn = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}		
		return bReturn;
	}
	
	public ArrayList findAccountByClientID(long clientid) throws ITreasuryDAOException
	{
		
		ArrayList list = new ArrayList();
		AccountOwnedBySAInfo info = null;
		try
		{
			initDAO();
			StringBuffer sql = new StringBuffer();
			sql.append(" select distinct o.nsaid from OB_AccountOwnedBySA o ");
			sql.append(" where o.nclientid ="+clientid);
			prepareStatement(sql.toString());
			executeQuery();	
			while(this.transRS.next())
			{
				info = new AccountOwnedBySAInfo();
				info.setNSAID(transRS.getLong("nsaid"));
				list.add(info);
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
	
	public void updateAccountUser(long clientID,long oldUserID,long newUserID) throws Exception
	{
		try
		{
			initDAO();
			StringBuffer sql = new StringBuffer();
			sql.append(" update OB_AccountOwnedBySA o ");
			sql.append(" set o.nsaid ="+newUserID);
			sql.append(" where o.nsaid ="+oldUserID);
			sql.append(" and o.nclientid ="+clientID);
			prepareStatement(sql.toString());
			executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
			//throw new Exception();
		}
		finally
		{
			finalizeDAO();
		}
	}
	
	public void deleteBankUserByClient(OB_UserInfo userinfo) throws ITreasuryDAOException
	{
		try
		{
			initDAO();
			StringBuffer sql = new StringBuffer();
			sql.append(" update ob_user o ");
			sql.append(" set o.nstatus ="+userinfo.getNStatus());
			sql.append(" where o.nclientid ="+userinfo.getNClientId());
			prepareStatement(sql.toString());
			executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}		
	}
	/**
	 * 查询已授权用户
	 * @param clientID
	 * @return
	 * @throws Exception
	 */
	public ArrayList findAuthorizedUser(long clientID) throws Exception
	{
		ArrayList list = new ArrayList();
		OB_UserInfo info = null;
		try{
			initDAO();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT DISTINCT u.ID,u.NAME,u.LOGINNAME,u.SCURRENCYID,t4.NAME CLIENTNAME FROM MG_USER U ");
			sql.append(" INNER JOIN MG_R_USER_DUTY_AGENCY T1 ON U.ID = T1.USERID ");
			sql.append(" INNER JOIN MG_R_DUTY_AGENCY T2 ON T2.ID = T1.AGENCY_DUTY_ID ");
			sql.append(" INNER JOIN MG_AGENCY T3 ON T3.ID = T2.AGENCYID ");
			sql.append(" INNER JOIN MG_AGENCY T4 on T4.ID = u.ORGID ");
			sql.append(" WHERE U.FLAG = '1' ");
			sql.append(" AND U.STATUS = '1' ");
			sql.append(" AND T3.FLAG = '1' ");
			sql.append(" AND T3.STATUS = '1' ");
			sql.append(" and u.AGENCY_TYPE = '1002' ");
			sql.append(" and t4.AGENCY_TYPE = '1002' ");
			if(clientID>0)
			{
				sql.append(" and t3.ID ='"+clientID+"'");
			}
			log.info("sql="+sql.toString());
			prepareStatement(sql.toString());
			executeQuery();
			while(this.transRS.next())
			{
				info = new OB_UserInfo();
				info.setId(transRS.getLong("ID"));
				info.setSName(transRS.getString("NAME"));
				info.setSLoginNo(transRS.getString("LOGINNAME"));
				info.setClientName(transRS.getString("CLIENTNAME"));
				info.setNCurrencyId(transRS.getLong("SCURRENCYID"));
				list.add(info);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询用户出错",e);
		}
		finally
		{
			finalizeDAO();
		}		
		return list.size()>0?list:null;
	}
	
	public long getOfficeByClient(long clientId) throws Exception
	{
		long lOfficeId = -1;
		try
		{
			initDAO();
			StringBuffer sql = new StringBuffer();
			sql.append(" select c.nOfficeID from client c ");
			sql.append(" where c.id ="+clientId);
			log.info("sql="+sql.toString());
			prepareStatement(sql.toString());
			executeQuery();
			while(this.transRS.next())
			{
				lOfficeId = transRS.getLong("nOfficeID");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询用户出错",e);
		}
		finally
		{
			finalizeDAO();
		}	
		return lOfficeId;
	}
	
	public String queryUserSQL(QueryClientSAInfo qInfo){
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select  clientid, userid,clientcode,clientname,officename,loginno,password,isadmin,dtchangepassword,isbelongtoclient,officeid,status,nsaid  \n");
		sb.append(" from( ");
		sb.append(" select c.id clientid,u.id userid,c.scode clientcode,c.sname clientname,o.sname officename,");
		sb.append(" u.sloginno loginno,u.spassword password, 1 isadmin,u.dtchangepassword dtchangepassword, a.isbelongtoclient isbelongtoclient,o.id  officeid,u.nstatus status,u.nsaid nsaid ");
		sb.append(" from ob_user u, ob_admin_of_user a, client c, office o ");
		sb.append(" where u.nsaid = -1 ");
		sb.append(" and u.id = a.initialuserid ");
		sb.append(" and a.clientid = c.id ");
		sb.append(" and c.nofficeid = o.id ");
		sb.append(" and c.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
		if(qInfo.getStatusID()>-1)
		{
			sb.append(" and u.nstatus ="+qInfo.getStatusID());
		}
		sb.append(" union all ");
		sb.append(" select c.id clientid,u.id userid,c.scode clientcode,c.sname clientname,o.sname officename,");
		sb.append(" u.sloginno loginno,u.spassword password, 2 isadmin,u.dtchangepassword dtchangepassword,1 isbelongtoclient,o.id officeid,u.nstatus status,u.nsaid nsaid ");
		sb.append(" from ob_user u, client c, office o ");
		sb.append(" where u.nsaid != -1 ");
		sb.append(" and u.nclientid = c.id ");
		sb.append(" and c.nofficeid = o.id ");
		sb.append(" and c.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
		if(qInfo.getStatusID()>-1)
		{
			sb.append(" and u.nstatus ="+qInfo.getStatusID());
		}
		sb.append(" )");
		sb.append(" where 2>1 and status<>0 ");
		if(qInfo.getStartClientCode().trim().length()>0&&!qInfo.getStartClientCode().equals(""))
		{
			sb.append(" and clientcode >='"+qInfo.getStartClientCode().trim()+"'");
		}
		if(qInfo.getEndClientCode().trim().length()>0&&!qInfo.getEndClientCode().equals(""))
		{
			sb.append(" and clientcode <='"+qInfo.getEndClientCode().trim()+"'");
		}
		if(qInfo.getOfficeID()>-1)
		{
			sb.append(" and officeid = "+qInfo.getOfficeID());
			
		}
		if(qInfo.getStartDate().trim().length()>0&&!qInfo.getStartDate().equals(""))
		{
			sb.append(" and dtchangepassword >= to_date('"+qInfo.getStartDate().trim()+"','yyyy-mm-dd')");
			
		}
		if(qInfo.getEndDate().trim().length()>0&&!qInfo.getEndDate().equals(""))
		{
			sb.append(" and dtchangepassword <= to_date('"+qInfo.getEndDate().trim()+"','yyyy-mm-dd')");
		}
		if(qInfo.getIsAdmin()>-1)
		{
			sb.append(" and isadmin ="+qInfo.getIsAdmin());
		}
		if(qInfo.getStatusID()>-1)
		{
			sb.append(" and status ="+qInfo.getStatusID());
		}
		return sb.toString();
		
	}
	
	
	public String queryUser4CheckSQL(QueryClientSAInfo qInfo){
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*, b.sname officename, c.sname clientname,c.scode clientcode  \n");
		sb.append(" from ob_user a, office b, client c, ob_admin_of_user d \n");
		sb.append(" where b.id=c.nofficeid \n");
		sb.append(" and a.id = d.initialuserid ");
		sb.append(" and d.clientid = c.id ");
		sb.append(" and c.id = a.NCLIENTID ");
		sb.append(" and (c.ISINSTITUTIONALCLIENT <=0 or c.ISINSTITUTIONALCLIENT is null ) ");
		sb.append(" and c.nstatusid ="+SYSConstant.SysCheckStatus.CHECK);
		if (qInfo.getStatus() != null) {
			sb.append(" and  a.NSTATUS='" + qInfo.getStatus() + "' ");
			if(qInfo.getStatus().longValue()==SYSConstant.SysCheckStatus.UNCHECK)
			{
				sb.append(" and a.ninputuserid != '" + qInfo.getInputUserID() + "'");
			}
		}
		if (qInfo.getStartClientCode() != null
				&& qInfo.getStartClientCode().length() > 0)
			sb.append(" and  c.scode>='"
					+ qInfo.getStartClientCode() + "' ");
		if (qInfo.getEndClientCode() != null
				&& qInfo.getEndClientCode().length() > 0)
			sb.append(" and  c.scode<='" + qInfo.getEndClientCode()
					+ "' ");
		if (qInfo.getOfficeID() > 0)
			sb.append(" and  b.id= " + qInfo.getOfficeID());
	
		return sb.toString();
		
	}
	
	public String getQueryUser4MngSQL(long clientID){
		
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select a.id, a.dtInput, a.nofficeid, a.sname, ");
		sbSQL.append(" a.ncurrencyid, a.nInputUserId, ");
		sbSQL
				.append(" a.sLoginNo,a.sPassword,a.nsaid,b.sname as inputusername ");
		sbSQL.append(" from ob_user a,ob_user b  ");
		sbSQL.append("");
		sbSQL.append(" where a.nclientid = " + clientID + " ");
		sbSQL.append(" and a.nstatus = 1  ");
		sbSQL.append(" and a.ninputuserid = b.id(+) ");
	
		return sbSQL.toString();
		
	}
	
	public String getQueryUser4AuthorizedSQL(long clientID){
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT u.ID,u.NAME,u.LOGINNAME,u.SCURRENCYID,t4.NAME CLIENTNAME FROM MG_USER U ");
		sql.append(" INNER JOIN MG_R_USER_DUTY_AGENCY T1 ON U.ID = T1.USERID ");
		sql.append(" INNER JOIN MG_R_DUTY_AGENCY T2 ON T2.ID = T1.AGENCY_DUTY_ID ");
		sql.append(" INNER JOIN MG_AGENCY T3 ON T3.ID = T2.AGENCYID ");
		sql.append(" INNER JOIN MG_AGENCY T4 on T4.ID = u.ORGID ");
		sql.append(" WHERE U.FLAG = '1' ");
		sql.append(" AND U.STATUS = '1' ");
		sql.append(" AND T3.FLAG = '1' ");
		sql.append(" AND T3.STATUS = '1' ");
		sql.append(" and u.AGENCY_TYPE = '1002' ");
		sql.append(" and t4.AGENCY_TYPE = '1002' ");
		if(clientID>0)
		{
			sql.append(" and t3.ID ='"+clientID+"'");
		}
	
		return sql.toString();
		
	}
}