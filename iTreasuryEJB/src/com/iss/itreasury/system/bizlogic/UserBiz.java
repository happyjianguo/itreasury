/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.system.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.iss.itreasury.dataentity.PeopleInfo; 
import com.iss.itreasury.encrypt.EncryptManager;
import com.iss.itreasury.system.privilege.dao.Sys_Group_Of_UserDAO;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Encrypt;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.Log;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UserBiz
{
	public static void main(String[] args)
	{
	}

	public PeopleInfo getUserByID(long lUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		PeopleInfo pi = new PeopleInfo();
		try
		{
			conn = Database.getConnection();
			sb.append(" select userinfo.ID id, userinfo.SNAME name , userinfo.SLOGINNO loginno, userinfo.SPASSWORD password, ");
			sb.append("        userinfo.NOFFICEID officeid, userinfo.NUSERGROUPID groupid, userinfo.NCURRENCYID currencyid, ");
			sb.append("        userinfo.NINPUTUSERID inputuserid, userinfo.DTINPUT datainput, userinfo.NSTATUSID nstatusid, userinfo.SPRIVLEVEL sLevel,userinfo.nCloseSystem nCloseSystem,userinfo.nIsAdminUser nIsAdminUser,userinfo.nIsVirtualUser nIsVirtualUser, ");
			sb.append("        office.sname officename, input.sname inputusername \n");
			sb.append(" from userinfo userinfo, office, userinfo input \n");
			sb.append(" where input.id(+)=userinfo.ninputuserid and office.id(+)=userinfo.nofficeid and userinfo.id=? \n");
			//		Log.print(" Userinfo sql : " + sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lUserID);
			rs = ps.executeQuery();
			//Ldap ldap = new Ldap();
			Sys_Group_Of_UserDAO sys_Group_Of_UserDAO = new Sys_Group_Of_UserDAO();
			if (rs.next())
			{
				pi.lUid = rs.getLong("id");
				pi.strUserName = rs.getString("name");
				pi.strLogin = rs.getString("loginno");
				pi.strPassword = rs.getString("password");
				pi.lOfficeID = rs.getLong("officeid");
				//pi.vGroupID = ldap.getUserByDN(pi.strLogin).vGroupID;
				pi.vGroupID =(Vector) sys_Group_Of_UserDAO.findGroupByUserId(pi.lUid,pi.lOfficeID);
				pi.lCurrencyID = rs.getLong("currencyid");
				pi.lInputUserID = rs.getLong("inputuserid");
				pi.dtInput = rs.getDate("datainput");
				pi.lStatusID = rs.getLong("nstatusid");
				pi.strOfficeName = rs.getString("officename");
				pi.strInputUserName = rs.getString("inputusername");
				pi.strLevel = rs.getString("sLevel");
				pi.lCloseSystem = rs.getLong("nCloseSystem");
				pi.lIsAdminUser = rs.getLong("nIsAdminUser");
				pi.lIsVirtualUser = rs.getLong("nIsVirtualUser");
			}
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return pi;
	}
	public Collection getUser(String strUserName, String strLogin, long lOfficeID, long lGroupID, long lCurrencyID, long lPageNo, long lPageLineCount) throws Exception
	{
		Vector v = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" select * from (		\n");
			sb.append(" select userinfo.ID id, userinfo.SNAME name , userinfo.SLOGINNO loginno, userinfo.SPASSWORD password, \n");
			sb.append("        userinfo.NOFFICEID officeid, userinfo.NUSERGROUPID groupid, userinfo.NCURRENCYID currencyid, \n");
			sb.append("        userinfo.NINPUTUSERID inputuserid, userinfo.DTINPUT datainput, userinfo.NSTATUSID nstatusid, userinfo.SPRIVLEVEL sLevel, userinfo.nCloseSystem nCloseSystem,userinfo.nIsAdminUser nIsAdminUser,userinfo.nIsVirtualUser nIsVirtualUser , \n");
			sb.append("        office.sname officename, input.sname inputusername,rownum num \n");
			sb.append(" from userinfo userinfo, office, userinfo input \n");
			sb.append(" where input.id(+)=userinfo.ninputuserid and office.id(+)=userinfo.nofficeid and userinfo.nstatusid=" + Constant.RecordStatus.VALID + " \n");
			if (strUserName != null && strUserName.length() > 0)
				sb.append(" and userinfo.SNAME=? ");
			if (strLogin != null && strLogin.length() > 0)
				sb.append(" and userinfo.SLOGINNO=? ");
			if (lOfficeID > 0)
				sb.append(" and userinfo.NOFFICEID=? ");
			if (lGroupID > 0)
				sb.append(" and userinfo.NUSERGROUPID=? ");
			if (lCurrencyID > 0 && lCurrencyID != Constant.CODE_RECORD_ALL)
				sb.append(" and userinfo.NCURRENCYID in(?,?) ");
			sb.append(" ) a where a.num between ? and ? ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			int nIndex = 1;
			//
			if (strUserName != null && strUserName.length() > 0)
			{
				ps.setString(nIndex, strUserName);
				nIndex++;
			}
			if (strLogin != null && strLogin.length() > 0)
			{
				ps.setString(nIndex, strLogin);
				nIndex++;
			}
			if (lOfficeID > 0)
			{
				ps.setLong(nIndex, lOfficeID);
				nIndex++;
			}
			if (lGroupID > 0)
			{
				ps.setLong(nIndex, lGroupID);
				nIndex++;
			}
			if (lCurrencyID > 0)
			{
				ps.setLong(nIndex, lCurrencyID);
				nIndex++;
				ps.setLong(nIndex, Constant.CODE_RECORD_ALL);
				nIndex++;
			}
			ps.setLong(nIndex, (lPageNo - 1) * lPageLineCount + 1);
			ps.setLong(nIndex + 1, lPageNo * lPageLineCount);
			//
			rs = ps.executeQuery();
			
			Log.print("1");
			while (rs.next())
			{
				PeopleInfo pi = new PeopleInfo();
				pi.lUid = rs.getLong("id");
				pi.strUserName = rs.getString("name");
				pi.strLogin = rs.getString("loginno");
				pi.strPassword = rs.getString("password");
				pi.lOfficeID = rs.getLong("officeid");
//				pi.vGroupID = ldap.getUserByDN(pi.strLogin).vGroupID;
				pi.lCurrencyID = rs.getLong("currencyid");
				pi.lInputUserID = rs.getLong("inputuserid");
				pi.dtInput = rs.getDate("datainput");
				pi.lStatusID = rs.getLong("nstatusid");
				pi.strOfficeName = rs.getString("officename");
				pi.strInputUserName = rs.getString("inputusername");
				pi.lCloseSystem = rs.getLong("nCloseSystem");
				pi.lIsAdminUser = rs.getLong("nIsAdminUser");
				pi.lIsVirtualUser = rs.getLong("nIsVirtualUser");
				Log.print("2");
				pi.strLevel = rs.getString("sLevel");
				Log.print("3");
				v.addElement(pi);
			}
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return (v.size() > 0 ? v : null);
	}
	public void modifyUser(PeopleInfo pi) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" update userinfo set SNAME=?, NOFFICEID=?, NUSERGROUPID=?, NCURRENCYID=?, NINPUTUSERID=?,SPRIVLEVEL=?,nCloseSystem=?,nIsAdminUser=?,nIsVirtualUser=? where id=? ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, pi.strUserName);
			ps.setLong(2, pi.lOfficeID);
			ps.setLong(3, -1);
			ps.setLong(4, pi.lCurrencyID);
			ps.setLong(5, pi.lInputUserID);
			ps.setString(6, pi.strLevel);
			ps.setLong(7, pi.lCloseSystem);
			ps.setLong(8,pi.lIsAdminUser);
			ps.setLong(9,pi.lIsVirtualUser);
			ps.setLong(10, pi.lUid);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			//
			//Ldap ldap = new Ldap();
			//ldap.modifyUser(pi);
			//
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}
	public void deleteUser(long lUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" update userinfo set NSTATUSID=? where id=? ");
			Log.print("delete Userinfo sql : " + sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.INVALID);
			ps.setLong(2, lUserID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			//
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}
	public void changeUserPassword(long lUserID, String strPassword) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" update userinfo set SPASSWORD=? where id=? ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, strPassword);
			ps.setLong(2, lUserID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			//
			//Ldap ldap = new Ldap();
			//		ldap.changeUserPassword();
			//
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}
	public long getTotalPages(String strUserName, String strLogin, long lOfficeID, long lGroupID, long lCurrencyID, long lPageNo, long lPageLineCount) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" select count(*)   \n");
			sb.append(" from userinfo userinfo, office, userinfo input \n");
			sb.append(" where input.id(+)=userinfo.ninputuserid and office.id(+)=userinfo.nofficeid and userinfo.nstatusid=" + Constant.RecordStatus.VALID + " \n");
			if (strUserName != null && strUserName.length() > 0)
				sb.append(" and userinfo.SNAME=? ");
			if (strLogin != null && strLogin.length() > 0)
				sb.append(" and userinfo.SLOGINNO=? ");
			if (lOfficeID > 0)
				sb.append(" and userinfo.NOFFICEID=? ");
			if (lGroupID > 0)
				sb.append(" and userinfo.NUSERGROUPID=? ");
			if (lCurrencyID > 0 && lCurrencyID != Constant.CODE_RECORD_ALL)
				sb.append(" and userinfo.NCURRENCYID in(?,?) ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			int nIndex = 1;
			//
			if (strUserName != null && strUserName.length() > 0)
			{
				ps.setString(nIndex, strUserName);
				nIndex++;
			}
			if (strLogin != null && strLogin.length() > 0)
			{
				ps.setString(nIndex, strLogin);
				nIndex++;
			}
			if (lOfficeID > 0)
			{
				ps.setLong(nIndex, lOfficeID);
				nIndex++;
			}
			if (lGroupID > 0)
			{
				ps.setLong(nIndex, lGroupID);
				nIndex++;
			}
			if (lCurrencyID > 0)
			{
				ps.setLong(nIndex, lCurrencyID);
				nIndex++;
				ps.setLong(nIndex, Constant.CODE_RECORD_ALL);
				nIndex++;
			}
			//
			rs = ps.executeQuery();
			long lRecordCount = 0;
			if (rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			lResult = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lResult++;
			}
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lResult;
	}
	public long addUser(PeopleInfo pi) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append("select ID from UserInfo where SLOGINNO=? and NSTATUSID=?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, pi.strLogin);
			ps.setLong(2,Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				Log.print("登录名重复，请重新输入");
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				return -1;
			}
			sb.setLength(0);
			sb.append("select nvl(max(ID)+1,1) ID from UserInfo");
			Log.print("********************************************\n");
			Log.print("===========>得到最大ID的 strSQL==" + sb.toString());
			Log.print("********************************************\n");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			rs.next();
			lMaxID = rs.getLong(1);
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//insert新的记录
			sb.setLength(0);
			sb.append(
				"insert into userinfo (ID, SNAME, SLOGINNO, SPASSWORD, NOFFICEID, NUSERGROUPID, NCURRENCYID, NINPUTUSERID, DTINPUT, NSTATUSID,SPRIVLEVEL,NCLOSESYSTEM,NISADMINUSER,NISVIRTUALUSER,dtChangePassword) values (?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,sysdate)");
			Log.print("Insert Userinfo sql : " + sb.toString());
//			Log.print(" UserGroupID : " + pi.lGroupID);
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lMaxID);
			ps.setString(2, pi.strUserName);
			ps.setString(3, pi.strLogin);
			ps.setString(4, pi.strPassword);
			ps.setLong(5, pi.lOfficeID);
			ps.setLong(6, -1);
			ps.setLong(7, pi.lCurrencyID);
			ps.setLong(8, pi.lInputUserID);
			ps.setLong(9, Constant.RecordStatus.VALID);
			ps.setString(10, pi.strLevel);
			ps.setLong(11, pi.lCloseSystem);
			ps.setLong(12,pi.lIsAdminUser);
			ps.setLong(13,pi.lIsVirtualUser);
			lResult = ps.executeUpdate();
			Log.print(" Max User ID : " + lMaxID);
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			//
			Log.print(" Ldap ");
			//Ldap ldap = new Ldap();
			pi.lUid = lMaxID;
			//Log.print(" Befor Ldap ");
			//ldap.addUser(pi);
			//Log.print(" After Ldap ");
			//
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lMaxID;
		//		ID, SNAME, SLOGINNO, SPASSWORD, NOFFICEID, NUSERGROUPID, NCURRENCYID, NINPUTUSERID, DTINPUT, NSTATUSID
	}
	public void changeUserPassword(long lUserID, String strLoginno, String strPassword) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			//加密校验
			if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
			{
				Log.print("中电子修改密码！");
				EncryptManager.getBeanFactory().changeUserPassword(lUserID,strPassword);
			}
			else
			{
			conn = Database.getConnection();
			sb.append(" update userinfo set SPASSWORD=?, dtChangePassword=?  where id=? ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, strPassword);
			ps.setTimestamp(2,Env.getSystemDate());
			ps.setLong(3, lUserID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			//
			//Ldap ldap = new Ldap();
			//ldap.changeUserPassword(strLoginno, strPassword);
			//
			conn.close();
			conn = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}
	public void deleteUser(long lUserID, String strLoginno) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" update userinfo set NSTATUSID=? where id=? ");
			Log.print("delete Userinfo sql : " + sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.INVALID);
			ps.setLong(2, lUserID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			//
			//Ldap ldap = new Ldap();
			//ldap.removeUser(strLoginno);
			//
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-4-4 14:42:11)
	 * @return com.iss.cpf.ldap.PeopleInfo
	 * @param strLogin java.lang.String
	 * @param strPassword java.lang.String
	 * @exception java.lang.Exception 异常说明。
	 */
	public PeopleInfo getUser(String strLogin, String strPassword) throws java.lang.Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		PeopleInfo pi = new PeopleInfo();
		try
		{
			conn = Database.getConnection();
			sb.append(" select userinfo.ID id, userinfo.SNAME name , userinfo.SLOGINNO loginno, userinfo.SPASSWORD password, ");
			sb.append("        userinfo.NOFFICEID officeid, userinfo.NUSERGROUPID groupid, userinfo.NCURRENCYID currencyid, ");
			sb.append("        userinfo.NINPUTUSERID inputuserid, userinfo.DTINPUT datainput, userinfo.NSTATUSID nstatusid, userinfo.SPRIVLEVEL sLevel,userinfo.nCloseSystem nCloseSystem,userinfo.nIsAdminUser nIsAdminUser,userinfo.nIsVirtualUser nIsVirtualUser,  ");
			sb.append("        office.sname officename, input.sname inputusername \n");
			sb.append(" from userinfo userinfo, office, userinfo input \n");
			sb.append(" where input.id(+)=userinfo.ninputuserid and office.id(+)=userinfo.nofficeid and UPPER(userinfo.SPASSWORD)=UPPER(?) and UPPER(userinfo.SLOGINNO)=UPPER(?) \n");
			Log.print(" Userinfo sql : " + sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, strPassword);
			ps.setString(2, strLogin);
			rs = ps.executeQuery();
			//Ldap ldap = new Ldap();
			if (rs.next())
			{
				pi.lUid = rs.getLong("id");
				pi.strUserName = rs.getString("name");
				pi.strLogin = rs.getString("loginno");
				pi.strPassword = rs.getString("password");
				pi.lOfficeID = rs.getLong("officeid");
				//pi.vGroupID = ldap.getUserByDN(pi.strLogin).vGroupID;
				pi.lCurrencyID = rs.getLong("currencyid");
				pi.lInputUserID = rs.getLong("inputuserid");
				pi.dtInput = rs.getDate("datainput");
				pi.lStatusID = rs.getLong("nstatusid");
				pi.strOfficeName = rs.getString("officename");
				pi.strInputUserName = rs.getString("inputusername");
				pi.strLevel = rs.getString("sLevel");
				pi.lCloseSystem = rs.getLong("nCloseSystem");
				pi.lIsAdminUser = rs.getLong("nIsAdminUser");
				pi.lIsVirtualUser = rs.getLong("nIsVirtualUser");
			}
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return pi;
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-21 17:20:15)
	 * @param lUserID long
	 * @param strLevel java.lang.String
	 * @exception java.lang.Exception 异常说明。
	 */
	public void modifyLevel(long lGroupID, String strLevel) throws java.lang.Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" update userinfo set SPRIVLEVEL=? where NUSERGROUPID=? ");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, strLevel);
			ps.setLong(2, lGroupID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	}
	public Collection getUser(String strUserName, String strLogin, long lOfficeID, long lGroupID, long lCurrencyID, long lPageNo, long lPageLineCount, long lOrderParam, long lDesc)
		throws Exception
	{
		Vector v = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sb.append(" select * from ( select a.*,rownum num from (		\n");
			sb.append(" select userinfo.ID id, userinfo.SNAME name , userinfo.SLOGINNO loginno, userinfo.SPASSWORD password, \n");
			sb.append("        userinfo.NOFFICEID officeid, userinfo.NUSERGROUPID groupid, userinfo.NCURRENCYID currencyid, \n");
			sb.append("        userinfo.NINPUTUSERID inputuserid, userinfo.DTINPUT datainput, userinfo.NSTATUSID nstatusid, userinfo.SPRIVLEVEL sLevel, userinfo.nCloseSystem nCloseSystem,userinfo.nIsAdminUser nIsAdminUser,userinfo.nIsVirtualUser nIsVirtualUser, \n");
			sb.append("        office.sname officename, input.sname inputusername \n");
			sb.append(" from userinfo userinfo, office, userinfo input \n");
			sb.append(" where input.id(+)=userinfo.ninputuserid and office.id(+)=userinfo.nofficeid and userinfo.nstatusid=" + Constant.RecordStatus.VALID + " \n");
			if (strUserName != null && strUserName.length() > 0)
				sb.append(" and userinfo.SNAME=? ");
			if (strLogin != null && strLogin.length() > 0)
				sb.append(" and userinfo.SLOGINNO=? ");
			if (lOfficeID > 0)
				sb.append(" and userinfo.NOFFICEID=? ");
			if (lGroupID > 0)
				sb.append(" and userinfo.NUSERGROUPID=? ");
			if (lCurrencyID > 0 && lCurrencyID != Constant.CODE_RECORD_ALL)
				sb.append(" and userinfo.NCURRENCYID in(?,?) ");
			switch ((int) lOrderParam)
			{
				case 1 :
					sb.append(" order by name");
					break;
				case 2 :
					sb.append(" order by loginno");
					break;
				case 3 :
					sb.append(" order by officeid");
					break;
				case 4 :
					sb.append(" order by groupid");
					break;
				case 5 :
					sb.append(" order by sLevel");
					break;
				case 6 :
					sb.append(" order by currencyid");
					break;
				case 7 :
					sb.append(" order by inputusername");
					break;
				case 8 :
					sb.append(" order by datainput");
					break;
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_ASC)
			{
				sb.append(" desc");
			}
			sb.append(" ) a ) b where b.num between ? and ? ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			int nIndex = 1;
			//
			if (strUserName != null && strUserName.length() > 0)
			{
				ps.setString(nIndex, strUserName);
				nIndex++;
			}
			if (strLogin != null && strLogin.length() > 0)
			{
				ps.setString(nIndex, strLogin);
				nIndex++;
			}
			if (lOfficeID > 0)
			{
				ps.setLong(nIndex, lOfficeID);
				nIndex++;
			}
			if (lGroupID > 0)
			{
				ps.setLong(nIndex, lGroupID);
				nIndex++;
			}
			if (lCurrencyID > 0)
			{
				ps.setLong(nIndex, lCurrencyID);
				nIndex++;
				ps.setLong(nIndex, Constant.CODE_RECORD_ALL);
				nIndex++;
			}
			ps.setLong(nIndex, (lPageNo - 1) * lPageLineCount + 1);
			ps.setLong(nIndex + 1, lPageNo * lPageLineCount);
			//
			rs = ps.executeQuery();
			//Ldap ldap = new Ldap();
			Log.print("1");
			while (rs.next())
			{
				PeopleInfo pi = new PeopleInfo();
				pi.lUid = rs.getLong("id");
				pi.strUserName = rs.getString("name");
				pi.strLogin = rs.getString("loginno");
				pi.strPassword = rs.getString("password");
				pi.lOfficeID = rs.getLong("officeid");
//				pi.vGroupID = ldap.getUserByDN(pi.strLogin).vGroupID;
				pi.lCurrencyID = rs.getLong("currencyid");
				pi.lInputUserID = rs.getLong("inputuserid");
				pi.dtInput = rs.getDate("datainput");
				pi.lStatusID = rs.getLong("nstatusid");
				pi.strOfficeName = rs.getString("officename");
				pi.strInputUserName = rs.getString("inputusername");
				pi.strLevel = rs.getString("sLevel");
				pi.lCloseSystem = rs.getLong("nCloseSystem");
				pi.lIsAdminUser = rs.getLong("nIsAdminUser");
				pi.lIsVirtualUser = rs.getLong("nIsVirtualUser");
				v.addElement(pi);
			}
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return (v.size() > 0 ? v : null);
	}
	public Collection getUserByModule(long lOfficeID, long lModuleID) throws Exception
	{
		Vector v = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		long lMaxID = -1;
		/*
		try
		{
			
			Ldap ldap = new Ldap();
			java.util.Hashtable h = new java.util.Hashtable();
			String strModule = "";
			Collection c = ldap.getGroupByOffice(lOfficeID);
			if (c != null)
			{
				Iterator it = c.iterator();
				while (it.hasNext())
				{
					GroupInfo gi = (GroupInfo) it.next();
					boolean b = false;
					for (int i = 0; i < gi.vModule.size(); i++)
					{
						if (lModuleID == Long.parseLong(gi.vModule.elementAt(i).toString()))
						{
							b = true;
							break;
						}
					}
					if (b == true)
						strModule = gi.lGroupID + ",";
				}
			}
			conn = Database.getConnection();
			sb.append(" select userinfo.ID id, userinfo.SNAME name , userinfo.SLOGINNO loginno, userinfo.SPASSWORD password, ");
			sb.append("        userinfo.NOFFICEID officeid, userinfo.NUSERGROUPID groupid, userinfo.NCURRENCYID currencyid, ");
			sb.append("        userinfo.NINPUTUSERID inputuserid, userinfo.DTINPUT datainput, userinfo.NSTATUSID nstatusid, userinfo.SPRIVLEVEL sLevel,userinfo.nIsAdminUser nIsAdminUser,userinfo.nIsVirtualUser nIsVirtualUser, ");
			sb.append("        office.sname officename, input.sname inputusername \n");
			sb.append(" from   userinfo userinfo, office, userinfo input \n");
			sb.append(" where  input.id(+)=userinfo.ninputuserid and office.id(+)=userinfo.nofficeid  and userinfo.nofficeid= ?\n");
			sb.append("        and userinfo.NSTATUSID=? and userinfo.NUSERGROUPID in(" + strModule.substring(0, strModule.length() - 1) + " )");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			//
			//
			rs = ps.executeQuery();
			while (rs.next())
			{
				PeopleInfo pi = new PeopleInfo();
				pi.lUid = rs.getLong("id");
				pi.strUserName = rs.getString("name");
				pi.strLogin = rs.getString("loginno");
				pi.strPassword = rs.getString("password");
				pi.lOfficeID = rs.getLong("officeid");
				pi.vGroupID = ldap.getUserByDN(pi.strLogin).vGroupID;
				pi.lCurrencyID = rs.getLong("currencyid");
				pi.lInputUserID = rs.getLong("inputuserid");
				pi.dtInput = rs.getDate("datainput");
				pi.lStatusID = rs.getLong("nstatusid");
				pi.strOfficeName = rs.getString("officename");
				pi.strInputUserName = rs.getString("inputusername");
				pi.strLevel = rs.getString("sLevel");
				pi.lIsAdminUser = rs.getLong("nIsAdminUser");
				pi.lIsVirtualUser = rs.getLong("nIsVirtualUser");
				v.addElement(pi);
			}
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}*/
		return (v.size() > 0 ? v : null);
	}

	/*
	 * 计算提醒天数，
	 * return -1 不提醒，0 过期 >0 提前提醒
	 */

	public long calIntervalDays(long lOfficeID,long lUserID) throws Exception
	{
		long lIntervalDays = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			conn = Database.getConnection();

			sb.append(" select (to_date('" + Env.getSystemDateString() + "','yyyy-mm-dd')-dtChangePassword) as intervalDays \n");
			sb.append(" from userInfo  \n");
			sb.append(" where id=" + lUserID + " \n");

			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			
			rs = ps.executeQuery();
			
			if (rs.next())
			{
				lIntervalDays = rs.getLong("intervalDays");
			}
			
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
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
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lIntervalDays;
	}
	
	public long getAlertDays(long lOfficeID,long lUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lAlertDays = -1;//过期天数
		long lRemindDays = -1;//提前提醒天数
		long lInervalDays = -1;
		long lReturn = -1;
		
		try
		{
			conn = Database.getConnection();

			sb.append(" select * from sys_changepasswordsetting  \n");
			sb.append(" where nOfficeID=" + lOfficeID + " and nStatusID=1 \n");

			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			
			rs = ps.executeQuery();
			
			if (rs.next())
			{
				lAlertDays = rs.getLong("NCHANGEINTERVAL");
				lRemindDays = rs.getLong("NREMINDDAYS");
			}
			
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			
			lInervalDays = this.calIntervalDays(lOfficeID,lUserID);
			
			if(lAlertDays<0 || (lAlertDays - lInervalDays >lRemindDays))//不警告
			{
				lReturn = -1;
			}
			else if((lAlertDays - lInervalDays>=0) && (lAlertDays - lInervalDays<=lRemindDays))
			{
				lReturn = lAlertDays - lInervalDays;
			}
			else
			{
				lReturn = 0;
			}
			
			
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
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lReturn;
	}
}
