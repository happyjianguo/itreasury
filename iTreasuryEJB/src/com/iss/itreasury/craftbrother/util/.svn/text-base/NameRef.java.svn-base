/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.craftbrother.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.settlement.util.SETTHTML;
import com.iss.itreasury.util.*;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class NameRef {

	private static HashMap hmUserName = new HashMap();
	private static HashMap craContractCode = new HashMap();
    private static HashMap counterPartBankNO = new HashMap();
    private static HashMap counterPartCode = new HashMap();
    private static HashMap craNoteCode = new HashMap();
	private static HashMap hmBankNameByID = new HashMap();
	private static HashMap hmBankNoByID = new HashMap();
	
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

	private static void findContractCode() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, contractcode from CRA_TRANSFERCONTRACTFORM");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				craContractCode.put(String.valueOf(rs.getLong("id")), rs
						.getString("contractcode"));
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
	
	private static void findCounterPartBankNO() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, counterpartbankno from CRA_COUNTERPARTBANK");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				counterPartBankNO.put(String.valueOf(rs.getLong("id")), rs
						.getString("counterpartbankno"));
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
	
	private static void findCounterPartCode() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, counterpartcode from CRA_COUNTERPART");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				counterPartCode.put(String.valueOf(rs.getLong("id")), rs
						.getString("counterpartcode"));
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


	// public methods write following block.
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
	
	/**
	 * 方法说明：根据 cracontractID 取得转让合同编号
	 * 
	 * @param cracontractID
	 * @return @
	 */
	public static String getContractCodeById(long cracontractID)
	{
		String strReturn = "";
		try
		{
			Object obj = craContractCode.get(String.valueOf(cracontractID));
			if(obj != null){
				strReturn = obj.toString();
			}
			else{
				findContractCode();
				obj = craContractCode.get(String.valueOf(cracontractID));
				strReturn = (obj != null ? obj.toString() : "");
			}				
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 方法说明：根据 counterPartBankID 取得 交易对手银行账号
	 * 
	 * @param counterPartBankID
	 * @return @
	 */
	public static String getCounterPartBankNOById(long counterPartBankID)
	{
		String strReturn = "";
		try
		{
			Object obj = counterPartBankNO.get(String.valueOf(counterPartBankID));
			if(obj != null){
				strReturn = obj.toString();
			}
			else{
				findCounterPartBankNO();
				obj = counterPartBankNO.get(String.valueOf(counterPartBankID));
				strReturn = (obj != null ? obj.toString() : "");
			}				
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 方法说明：根据 id 取得交易对手编号
	 * 
	 * @param lUserID
	 * @return @
	 */
	public static String getCounterpartCodeByID(long CounterpartID) {
		String strReturn = "";
		try {
			Object obj = counterPartCode.get(String.valueOf(CounterpartID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findCounterPartCode();
				obj = counterPartCode.get(String.valueOf(CounterpartID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 方法说明：根据 id 取得通知单编号
	 * 
	 * @param lUserID
	 * @return @
	 */
	public static String getNoticeCodeByID(long id) {
		String strReturn = "";
		try {
			Object obj = craNoteCode.get(String.valueOf(id));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findNoteCode();
				obj = craNoteCode.get(String.valueOf(id));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	private static void findNoteCode() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, noticecode from cra_transfernoticeform");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				craNoteCode.put(String.valueOf(rs.getLong("id")), rs
						.getString("noticecode"));
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
	 * 功能：通过银行ID查询银行名称
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getBankNameByID(long lID) {
		String strReturn = "";
		try {
			Object obj = hmBankNameByID.get(String.valueOf(lID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findBank();
				obj = hmBankNameByID.get(String.valueOf(lID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	private static void findBank() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select branch.id id, branch.COUNTERPARTBANKNO bankno, branch.COUNTERPARTBANKNAME bankname ,branch.COUNTERPARNAME accountname  from cra_counterpartbank branch");

			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmBankNameByID.put(String.valueOf(rs.getLong("id")), rs
						.getString("bankno"));

				hmBankNameByID.put(String.valueOf(rs.getLong("id")), rs
						.getString("bankname"));
				hmBankNameByID.put(String.valueOf(rs.getLong("id")), rs
						.getString("accountname"));
				hmBankNameByID.put(String.valueOf(rs.getLong("id")), rs
						.getString("bankname"));
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
	 * 功能：通过总账类型ID查询总账类型描述
	 * 
	 * @param lGLTypeID
	 * @return @
	 */
	public static String getGLTypeDescByID(long lGLTypeID) {
		String strReturn = "";
		try {
			String strSQL = "select sName from sett_generalledger where ID="
					+ lGLTypeID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sName");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 功能：通过标准摘要明细ID查询标准摘要明细描述（）
	 * 
	 * @param lDetailID
	 * @return @
	 */
	public static String getAbstractDetailDescByID(long lDetailID) {
		String strReturn = "";
		try {
			String strSQL = "select sDesc from sett_StandardAbstract where ID="
					+ lDetailID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sDesc");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 功能：通过银行ID查询银行名称
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getBankNameByBankID(long lID) {
		String strReturn = "";
		try {
			Object obj = hmBankNameByID.get(String.valueOf(lID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findBankInfo();
				obj = hmBankNameByID.get(String.valueOf(lID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	private static void findBankInfo() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select branch.id id, branch.scode scode, branch.sname sname from sett_branch branch");

			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmBankNoByID.put(String.valueOf(rs.getLong("id")), rs
						.getString("sCode"));

				hmBankNameByID.put(String.valueOf(rs.getLong("id")), rs
						.getString("sname"));
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
	 * 功能：通过交易对手ID查询交易对手名称
	 * 
	 * @param Counterpartid
	 * @return @
	 */
	public static String getCounterpartNameByID(long Counterpartid) {
		String strReturn = "";
		try {
			String strSQL = "select COUNTERPARTNAME from cra_counterpart where id="
					+ Counterpartid;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "COUNTERPARTNAME");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

}