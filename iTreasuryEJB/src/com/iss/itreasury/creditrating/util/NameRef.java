/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.creditrating.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.util.*;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class NameRef {
	private static HashMap hmClientName = new HashMap();

	private static HashMap hmClientCode = new HashMap();
	
	private static HashMap hmUserName = new HashMap();
	
	private static void findClient() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// hmClientName = new HashMap();
			// hmClientCode = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, scode,sname from client");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmClientName.put(String.valueOf(rs.getLong("id")), rs
						.getString("sname"));
				hmClientCode.put(String.valueOf(rs.getLong("id")), rs
						.getString("scode"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception sqle) {

			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				;
			}

		}
	}
	/**
	 * 功能：通过客户ID查询客户编号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getClientCodeByID(long lClientID) {
		String strReturn = "";
		try {
			Object obj = hmClientCode.get(String.valueOf(lClientID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findClient();
				obj = hmClientCode.get(String.valueOf(lClientID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 功能：通过客户ID查询客户名称
	 * 
	 * @param lClientID
	 * @return @
	 */
	public static String getClientNameByID(long lClientID) {
		String strReturn = "";
		try {
			Object obj = hmClientName.get(String.valueOf(lClientID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findClient();
				obj = hmClientName.get(String.valueOf(lClientID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 方法说明：根据 UserID 取得用户名称
	 * 
	 * @param lUserID
	 * @return @
	 */
	public static String getUserNameByID(long lUserID) {
		String strReturn = "";
		try {
			Object obj = hmUserName.get(String.valueOf(lUserID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findUser();
				obj = hmUserName.get(String.valueOf(lUserID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	private static void findUser() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// hmUserName = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, sname from userinfo");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmUserName.put(String.valueOf(rs.getLong("id")), rs
						.getString("sname"));
			}
			// 增加机制、机核等用户
			// TODO TOCONTINUE
			long[] MachineUser = Constant.MachineUser.getAllCode(1, 1);
			for (int i = 0; i < MachineUser.length; i++) {
				hmUserName.put(String.valueOf(MachineUser[i]),
						Constant.MachineUser.getName(MachineUser[i]));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception sqle) {

			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				;
			}

		}

	}
}