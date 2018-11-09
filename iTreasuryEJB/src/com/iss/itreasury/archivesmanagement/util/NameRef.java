/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.archivesmanagement.util;

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

	//
	private static HashMap hmAccountNo = new HashMap();

	private static HashMap hmAccountName = new HashMap();

	private static HashMap hmClientIDByAccountID = new HashMap();

	private static HashMap hmClientCodeByAccountID = new HashMap();

	private static HashMap hmClientNameByAccountID = new HashMap();

	private static HashMap hmBankNoByID = new HashMap();

	private static HashMap hmBankNameByID = new HashMap();

	// private static HashMap hmBankAccountCodeByID = new HashMap();

	// private static HashMap hmExtAccountNoByID = new HashMap();
	// private static HashMap hmExtClientNameByID = new HashMap();
	// private static HashMap hmExtClientProvinceByID = new HashMap();
	// private static HashMap hmExtClientCityByID = new HashMap();
	// private static HashMap hmExtClientBankNameByID = new HashMap();

	private static HashMap hmCashFlowNameByID = new HashMap();

	private static HashMap hmEnterpriceName = new HashMap();

	// private methods write following block.
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
	
	private static void findAccount(long accountId)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			//hmAccountNo = new HashMap();
			//hmAccountName = new HashMap();
			//hmClientIDByAccountID = new HashMap();
			//hmClientCodeByAccountID = new HashMap();
			//hmClientNameByAccountID = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select account.id accountID,account.saccountno accountNo,account.sname accountName,client_clientinfo.id clientID,client_clientinfo.code clientCode,client_clientinfo.name clientName \n");
			sb.append(" from sett_account account,client_clientinfo \n");
			sb.append(" where account.nclientid=client_clientinfo.id \n");
			sb.append(" and account.id="+accountId);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				hmAccountNo.put(String.valueOf(rs.getLong("accountID")), rs.getString("accountNo"));
				hmAccountName.put(String.valueOf(rs.getLong("accountID")), rs.getString("accountName"));

				hmClientIDByAccountID.put(String.valueOf(rs.getLong("accountID")), rs.getString("clientID"));
				hmClientCodeByAccountID.put(String.valueOf(rs.getLong("accountID")), rs.getString("clientCode"));
				hmClientNameByAccountID.put(String.valueOf(rs.getLong("accountID")), rs.getString("clientName"));
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception sqle)
		{

			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}

		}
	}

	private static void findAccount() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// hmAccountNo = new HashMap();
			// hmAccountName = new HashMap();
			// hmClientIDByAccountID = new HashMap();
			// hmClientCodeByAccountID = new HashMap();
			// hmClientNameByAccountID = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb
					.append(" select account.id accountID,account.saccountno accountNo,account.sname accountName,client.id clientID,client.scode clientCode,client.sname clientName \n");
			sb.append(" from sett_account account,client \n");
			sb.append(" where account.nclientid=client.id \n");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmAccountNo.put(String.valueOf(rs.getLong("accountID")), rs
						.getString("accountNo"));
				hmAccountName.put(String.valueOf(rs.getLong("accountID")), rs
						.getString("accountName"));

				hmClientIDByAccountID.put(String.valueOf(rs
						.getLong("accountID")), rs.getString("clientID"));
				hmClientCodeByAccountID.put(String.valueOf(rs
						.getLong("accountID")), rs.getString("clientCode"));
				hmClientNameByAccountID.put(String.valueOf(rs
						.getLong("accountID")), rs.getString("clientName"));
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

	private static void findBank() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// hmBankNoByID = new HashMap();
			// hmBankNameByID = new HashMap();
			//
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

	private static void findCashFlow() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// hmCashFlowNameByID = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, sname, scode, smulticode from sett_cashflow");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmCashFlowNameByID.put(String.valueOf(rs.getLong("id")), rs
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

	private static void findEnterprice() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// hmEnterpriceName = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select id, sname from SETT_ENTERPRICETYPE where nstatusid=1");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmEnterpriceName.put(String.valueOf(rs.getLong("id")), rs
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
	 * 功能：通过账户ID查询账户编号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getAccountNoByID(long lAccountID) {
		String strReturn = "";
		try {
			Object obj = hmAccountNo.get(String.valueOf(lAccountID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findAccount();
				obj = hmAccountNo.get(String.valueOf(lAccountID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 功能：通过账户ID查询账户编号（用于页面显示无'-'账户号）
	 * 特殊处理：一汽要求账户号无'-'
	 * @param lAccountID
	 * @return
	 * @
	 */
	public static String getNoLineAccountNoByID(long lAccountID)
	{
		String strReturn = "";
		
		String strTemp1 = "";
		String strTemp2 = "";
		String strTemp3 = "";
		String strTemp4 = "";
		
		try
		{
			Object obj = hmAccountNo.get(String.valueOf(lAccountID));
			if (obj != null)
			{
				strReturn = obj.toString();
			}
			else
			{
				findAccount(lAccountID);
				obj = hmAccountNo.get(String.valueOf(lAccountID));
				strReturn = (obj != null ? obj.toString() : "");
			}
			if(strReturn !=null && strReturn.length() > 0)
			{		
				String[] accountNo_array = strReturn.split("-");
				if(strReturn.startsWith("-") || strReturn.endsWith("-"))
				{
					System.out.println("----------账户号格式异常（首字符出现'-'或末字符出现'-'）-----------");
					throw new ITreasuryException("账户号格式异常：",strReturn,null);
				}
				int lineNumber = accountNo_array.length -1;

				//取放大镜第一段字符串
				if(accountNo_array[0] != null && accountNo_array[0] != "")
				{
					strTemp1 = accountNo_array[0].toString();
				}
				//取放大镜第二段字符串
				if(accountNo_array[1] != null && accountNo_array[1] != "")
				{
					strTemp2 = accountNo_array[1].toString();
				}
				//取放大镜第三段字符串
				if(accountNo_array[2] != null && accountNo_array[2] != "")
				{
					strTemp3 = accountNo_array[2].toString();
				}
				if(lineNumber > 2)
				{
					//取放大镜第四段字符串
					if(accountNo_array[3] != null && accountNo_array[3] != "")
					{
						strTemp4 = accountNo_array[3].toString();
					}
				}
				strReturn = strTemp1 + strTemp2 + strTemp3 + strTemp4 ;
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 功能：给账户号加'-'（用于页面显示无'-'账户号）
	 * 特殊处理：录入数据时一汽操作员不会加‘-’但是我们需要人为的给他加
	 * modify by wjliu --2007-5-8 去掉一汽中的那些限制
	 * @param lAccountID
	 * @return
	 * @
	 */	
	public static String setAccontLine(String str) throws IException{
//		String strline = "";
//		if(str != null && !str.equals(""))
//		{
//			str = str.trim();
//			if(str.length() == 13){
//				char[] charline = str.toCharArray();
//				for(int i = 0;i<charline.length;i++){
//					strline = strline + charline[i];
//					if(i==1){
//						strline = strline + "-";
//					}
//					if(i==3){
//						strline = strline + "-";
//					}
//					if(i==10){
//						strline = strline + "-";
//					}	
//		
//				}
//			}else{
//				throw new IException("输入账号的位数不够");
//			}
//		}
		
		return str;
	}
	
	/**
	 * 功能：去账户号'-'（用于页面显示无'-'账户号）
	 * 特殊处理：一汽要求账户号无'-'
	 * @param lAccountID
	 * @return
	 * @
	 */
//	public static String getNoLineAccountNo(String strAccountNo)
//	{
//		String strReturn = "";	
//		String strTemp1 = "";
//		String strTemp2 = "";
//		String strTemp3 = "";
//		String strTemp4 = "";
//		try
//		{
//			if(strAccountNo != null && strAccountNo.length() > 0)
//			{
//				String[] accountNo_array = strAccountNo.split("-");		
//				if(accountNo_array.length > 1)
//				{
//					//取放大镜第一段字符串
//					if(accountNo_array[0] != null)
//					{
//						strTemp1 = accountNo_array[0].toString();
//					}
//					//取放大镜第二段字符串
//					if(accountNo_array[1] != null)
//					{
//						strTemp2 = accountNo_array[1].toString();
//					}
//					//取放大镜第三段字符串
//					if(accountNo_array.length > 2)
//					{
//					if(accountNo_array[2] != null)
//					{
//						strTemp3 = accountNo_array[2].toString();
//					}
//					}
//					//取放大镜第四段字符串
//					if(accountNo_array.length > 3)
//					{
//						if(accountNo_array[3] != null)
//						{
//							strTemp4 = accountNo_array[3].toString();
//						}
//					}
//					strReturn = strTemp1 + strTemp2 + strTemp3 + strTemp4 ;
//				}
//
//			}
//			else
//			{
//				strReturn = strAccountNo;
//			}
//		}
//		catch (Exception e)
//		{
//			System.out.println(e.toString());
//		}
//		return strReturn;
//	}
	public static String getNoLineAccountNo(String strAccountNo)
	{
		String strReturn = strAccountNo;	
		String strTemp[];
		try {
			if(strAccountNo != null && strAccountNo.length() > 0) {
				
				//modified by mzh_fu 2007/05/03 增加下面if(Config.getBoolean(ConfigConstant.EBANK_DEPOSITNO_NOLINE,true))的判断			
				if(Config.getBoolean(ConfigConstant.EBANK_DEPOSITNO_NOLINE,true)){
					String[] accountNo_array = strAccountNo.split("-");	
					//2010-12-28 江琪修改 解决 编号[存单号/客户编号/账户编号]去掉"-"后的重复问题
					if(accountNo_array.length>1){
						strReturn="";
						for(int i=0;i<accountNo_array.length;i++){
							if(accountNo_array.length > i) {
								//取放大镜第一段字符串
								if(accountNo_array[i] != null) {
									strReturn=strReturn+accountNo_array[i].toString();
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	
	

	/**
	 * 功能：通过账户ID查询账户名称
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getAccountNameByID(long lAccountID) {
		String strReturn = "";
		try {
			String strSQL = "select sName from sett_Account where id="
					+ lAccountID;
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
	 * 功能：通过汇票ID查询对应汇票号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getBillCodeByID(long lBillID) {
		String strReturn = "";
		try {
			String strSQL = "select sCode from loan_discountcontractbill where id="
					+ lBillID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过科目号查询对应科目名称
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getSubjectNameByCode(String code) {
		String strReturn = "";
		try {
			String strSQL = "select sSubjectName from sett_vglsubjectdefinition where sSubjectCode='"
					+ code + "'";
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sSubjectName");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过办事处编号和科目号查询对应科目名称
	 * 
	 * @param OfficeID
	 * @param SubjectCode
	 * @return SubjectName @
	 */
	public static String getSubjectNameByCode(long lOfficeID,
			String strSubjectCode) {
		String strReturn = "";
		try {
			//sett_glsubjectdefinition替换为sett_vglsubjectdefinition,ssegmentcode2替换为sSubjectCode
//			String strSQL = "select ssegmentname2 from sett_glsubjectdefinition where nofficeid="
//					+ lOfficeID + " and ssegmentcode2='" + strSubjectCode + "'";
			String strSQL = "select sSubjectName from sett_vglsubjectdefinition where nofficeid="
				+ lOfficeID + " and sSubjectCode='" + strSubjectCode + "'";
//			Collection c = SETTHTML
//					.getCommonSelectList(strSQL, "ssegmentname2");
			Collection c = SETTHTML
			.getCommonSelectList(strSQL, "sSubjectName");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过账户ID查询对应科目
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getSubjectByAccountID(long lAccountID) {
		String strReturn = "";
		try {
			String strSQL = "select sSubject from sett_Account where id="
					+ lAccountID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sSubject");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过成员行ID查询对应科目
	 * 
	 * @param lBankID
	 * @return @
	 */
	public static String getSubjectByBankID(long lBankID) {
		String strReturn = "";
		try {
			String strSQL = "select OTHERGLSUBJECTCODE from LOAN_YT_ATTENDBANK where id="
					+ lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"OTHERGLSUBJECTCODE");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过账户ID查询客户ID
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static long getClientIDByAccountID(long lAccountID) {
		long lReturn = -1;
		try {
			Object obj = hmClientIDByAccountID.get(String.valueOf(lAccountID));
			if (obj != null) {
				lReturn = Long.parseLong(obj.toString());
			} else {
				findAccount();
				obj = hmClientIDByAccountID.get(String.valueOf(lAccountID));
				lReturn = (obj != null ? Long.parseLong(obj.toString()) : -1);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}

	/**
	 * 功能：通过账户ID查询客户编号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getClientCodeByAccountID(long lAccountID) {
		String strReturn = "";
		try {
			Object obj = hmClientCodeByAccountID
					.get(String.valueOf(lAccountID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findAccount();
				obj = hmClientCodeByAccountID.get(String.valueOf(lAccountID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过账户ID查询客户名称
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getClientNameByAccountID(long lAccountID) {
		String strReturn = "";
		try {
			Object obj = hmClientNameByAccountID
					.get(String.valueOf(lAccountID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findAccount();
				obj = hmClientNameByAccountID.get(String.valueOf(lAccountID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过银行ID查询银行编号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getBankNoByID(long lID) {
		String strReturn = "";
		try {
			Object obj = hmBankNoByID.get(String.valueOf(lID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findBank();
				obj = hmBankNoByID.get(String.valueOf(lID));
				strReturn = (obj != null ? obj.toString() : "");
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

	/**
	 * 根据id查询对应的收/付款银行账户的账户编号
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getBankAccountCodeByID(long lAccountID, long lBankID) {
		String strReturn = "";
		try {
			String strSQL = " select sBankAccountCode sBankAccountNo from SETT_BRANCH where ID="
					+ lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"sBankAccountNo");
			// modify by wjliu 加一个条件，是否有值
			// if (c != null )
			if (c != null &&  c.size() > 0 ) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn == null ?"":strReturn;
	}
	
	/**
	 * 根据id查询对应的银行编号
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getBankCodeByID(long lBankID) {
		String strReturn = "";
		try {
			String strSQL = " select scode from SETT_BRANCH where ID="
					+ lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"scode");
			// modify by wjliu 加一个条件，是否有值
			// if (c != null )
			if (c != null &&  c.size() > 0 ) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn == null ?"":strReturn;
	}

	/**
	 * 根据id查询对应的收/付款银行账户的账户编号
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getBankAccountCodeByAccountIDandBankID(
			long lAccountID, long lBankID) {
		String strReturn = "";
		String strSQL = "";

		try {
			System.out.println("=====根据账户ID和银行ID联合查询银行账户号: lAccountID:"
					+ lAccountID + "  lBankID:" + lBankID);
			Collection c = null;

			strSQL = "  select sBankAccountNO from SETT_ACCOUNTBANK where nAccountID = "
					+ lAccountID + " and nBankID = " + lBankID;
			System.out.println(" SQL: " + strSQL);
			c = SETTHTML.getCommonSelectList(strSQL, "sBankAccountNO");

			if (c != null) // 如果查到了
			{
				strReturn = (String) c.iterator().next();
			} else {
				strSQL = "  select sBankAccountCode from sett_Branch where ID = "
						+ lBankID;
				System.out.println(" SQL: " + strSQL);
				c = SETTHTML.getCommonSelectList(strSQL, "sBankAccountCode");
				if (c != null) {
					strReturn = (String) c.iterator().next();
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/*
	 * 根据开户行ID查询银行账户名称
	 */
	public static String getBankAccountNameByID(long lAccountID, long lBankID) {
		String strReturn = "";
		try {
			String strSQL = " select sEnterpriseName sBankAccountName from SETT_BRANCH where ID="
					+ lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"sBankAccountName");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过贴现票据ID查询贴现票据编号
	 * 
	 * @param lDiscountBillID
	 * @return @
	 */
	public static String getDiscountBillNoByID(long lDiscountBillID) {
		String strReturn = "";
		try {
			String strSQL = "select sCode from loan_discountcontractbill where ID="
					+ lDiscountBillID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过贴现凭证ID查询贴现凭证编号
	 * 
	 * @param lDiscountCredenceID
	 * @return @
	 */
	public static String getDiscountCredenceNoByID(long lDiscountCredenceID) {
		String strReturn = "";
		try {
			String strSQL = "select sCode from loan_discountcredence where ID="
					+ lDiscountCredenceID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过贴现票据ID查询贴现票据票面金额
	 * 
	 * @param lDiscountBillID
	 * @return @
	 */
	public static String getDiscountBillAmountByDiscountBillID(
			long lDiscountBillID) {
		String strReturn = "";
		try {
			String strSQL = "select to_char(a.mAmount) Amount from loan_discountcontractbill a where a.ID="
					+ lDiscountBillID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "Amount");
			if (c != null) {
				Log.print("通过贴现票据ID查询贴现票据票面金额------取得金额");
				strReturn = (String) c.iterator().next();
				Log.print("通过贴现票据ID查询贴现票据票面金额------取得金额为strReturn");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过贴现票据ID查询实际贴现金额
	 * 
	 * @param lDiscountBillID
	 * @return @
	 */
	public static String getDiscountAmountByDiscountBillID(long lDiscountBillID) {
		String strReturn = "";
		try {
			String strSQL = "select to_char(a.mCheckAmount) CheckAmount from loan_discountcontractbill a where a.ID="
					+ lDiscountBillID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "CheckAmount");
			if (c != null) {
				Log.print("通过贴现票据ID查询实际贴现金额------取得金额");
				strReturn = (String) c.iterator().next();
				Log.print("通过贴现票据ID查询实际贴现金额------取得金额为strReturn");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过贴现票据ID查询贴现票据贴现日
	 * 
	 * @param lDiscountBillID
	 * @return @
	 */
	public static String getDiscountDateByDiscountBillID(long lDiscountBillID) {
		String strReturn = "";
		try {
			String strSQL = "select a.sCode DiscountBillNo,to_char(b.dtDiscountDate,'yyyy-mm-dd') DiscountDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.nDiscountCredenceID DiscountCredenceID from loan_discountcontractbill a,loan_ContractForm b where b.id = a.nContractID and a.ID="
					+ lDiscountBillID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "DiscountDate");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	// 推荐使用此方法，上面一个不推荐 add by weihuang
	public static String getDiscountDateByDiscountBillID1(long lDiscountBillID) {
		String strReturn = "";
		try {
			String strSQL = "select a.*,to_char(dtpublicdate,'yyyy-mm-dd') dtpublicdate,b.MRATE from Loan_DiscountContractBill a,loan_discountcredence b where a.nStatusID=1 "
					+ " and a.ndiscountcredenceid=b.id and a.id="
					+ lDiscountBillID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "dtpublicdate");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过贴现票据ID查询贴现票据到期日期
	 * 
	 * @param lDiscountBillID
	 * @return @
	 */
	public static String getEndDateByDiscountBillID(long lDiscountBillID) {
		String strReturn = "";
		try {
			String strSQL = "select a.sCode DiscountBillNo,to_char(b.dtStartDate,'yyyy-mm-dd') DiscountDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.nDiscountCredenceID DiscountCredenceID from loan_discountcontractbill a,loan_ContractForm b where b.id = a.nContractID and a.ID="
					+ lDiscountBillID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "EndDate");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过办事处ID查询办事处名称
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getOfficeNameByID(long lOfficeID) {
		String strReturn = "";
		try {
			String strSQL = "select sName from Office where ID=" + lOfficeID;
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
	 * 功能：通过合同ID查询合同名称
	 * 
	 * @param lContractID
	 * @return @
	 */
	public static String getContractNoByID(long lContractID) {
		String strReturn = "";
		try {
			String strSQL = "select sContractCode from Loan_ContractForm where ID="
					+ lContractID;
			Collection c = SETTHTML
					.getCommonSelectList(strSQL, "sContractCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过利息税费率ID查询其编号
	 * 
	 * @param lContractID
	 * @return @
	 */
	public static String getInterestTaxPlanCodeById(long lInterestTaxPlanID) {
		String strReturn = "";
		try {
			String strSQL = " select code from Sett_TaxRatePlanSetting where ID = "
					+ lInterestTaxPlanID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "code");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过利息税费率ID查询其名称
	 * 
	 * @param lContractID
	 * @return @
	 */
	public static String getInterestTaxPlanNameById(long lInterestTaxPlanID) {
		String strReturn = "";
		try {
			String strSQL = " select name from Sett_TaxRatePlanSetting where ID = "
					+ lInterestTaxPlanID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "name");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过业务通知单ID查询业务通知单号
	 * 
	 * @param lSecuritiesNoticeFormID
	 * @return @
	 */
	public static String getSecuritiesNoticeFormNoByID(
			long lSecuritiesNoticeFormID) {
		String strReturn = "";
		try {
			String strSQL = "select Code from sec_NoticeForm where ID="
					+ lSecuritiesNoticeFormID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "Code");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过特殊交易类型ID查询特殊交易类型名称
	 * 
	 * @param lSpecialTypeID
	 * @return @
	 */
	public static String getSpecialTypeNameByID(long lSpecialTypeID) {
		String strReturn = "";
		try {
			String strSQL = "SELECT sname FROM sett_specialoperation where id="
					+ lSpecialTypeID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sname");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过放款通知单ID查询放款通知单编号
	 * 
	 * @param lPayFormID
	 * @return @
	 */
	public static String getPayFormNoByID(long lPayFormID) {
		String strReturn = "";
		try {
			String strSQL = "select sCode from loan_payform where ID="
					+ lPayFormID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过担保通知单ID查询担保放款通知单编号
	 * 
	 * @param lPayFormID
	 * @return @
	 */
	public static String getMarginPayFormNoByID(long lMarginPayFormID) {
		String strReturn = "";
		try {
			String strSQL = "select code from loan_assurechargeform where ID="
					+ lMarginPayFormID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "code");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过担保通知单ID查询担保放款通知单余额
	 * 
	 * @param lPayFormID
	 * @return @
	 */
	public static String getMarginPayFormAmountByID(long lMarginPayFormID) {
		String strReturn = "";
		try {
			String strSQL = "select to_char(recognizanceamount) recognizanceamount from loan_assurechargeform where ID="
					+ lMarginPayFormID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"recognizanceamount");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过免还通知单ID查询免还通知单编号
	 * 
	 * @param lPayFormID
	 * @return @
	 */
	public static String getFreeFormNoByID(long lFreeFormID) {
		String strReturn = "";
		try {
			String strSQL = "select sCode from loan_Freeform where ID="
					+ lFreeFormID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过提前还款通知单ID查询提前还款通知单编号
	 * 
	 * @param lPayFormID
	 * @return @
	 */
	public static String getAheadPayFormNoByID(long lAheadPayFormID) {
		String strReturn = "";
		try {
			String strSQL = "select sCode from loan_aheadrepayform where ID="
					+ lAheadPayFormID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过办事处ID查询办事处编号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getOfficeNoByID(long lOfficeID) {
		String strReturn = "";
		try {
			String strSQL = "select sCode from Office where ID=" + lOfficeID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过币种ID查询币种编号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getCurrencyNoByID(long lCurrencyID) {
		String strReturn = "";
		try {
			String strSQL = "select currencyNo from currencyinfo where ID="
					+ lCurrencyID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "currencyNo");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过总账类型ID查询总账借贷关系
	 * 
	 * @param lGLTypeID
	 * @return @
	 */
	public static long getBalanceDirection(long lGLTypeID,long officeid,long currencyId) {
		long strReturn = -1;
		try {
			String strSQL = "select nbalancedirection from sett_generalledger a,sett_glsubjectdefinition b " +
					" where a.ssubjectcode = b.ssegmentcode2  and  a.ID= " + lGLTypeID;
			strSQL += "a.nofficeid = " + officeid;
			strSQL += "a.ncurrencyid = " + currencyId;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "nbalancedirection");
			if (c != null) {
				strReturn = ((Long) c.iterator().next()).longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
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
	 * 功能：通过转贴现类型ID查询转贴现类型描述
	 * 
	 * @param lGLTypeID
	 * @return @
	 */
	public static String getDiscountTypeByID(long lDiscountTypeID) {
		String strReturn = "";
		try {
			String strSQL = "select sSubjectName sName from sett_vglsubjectdefinition where ID="
					+ lDiscountTypeID;
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
	 * 功能：通过总账类型ID查询总账类型编号
	 * 
	 * @param lGLTypeID
	 * @return @
	 */
	public static String getGLTypeNoByID(long lGLTypeID) {
		String strReturn = "";
		try {
			String strSQL = "select sGeneralLedgerCode from sett_generalledger where ID="
					+ lGLTypeID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"sGeneralLedgerCode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 功能：通过总账类型ID查询总账科目代码
	 * 
	 * @param lGLTypeID
	 * @return @
	 */
	public static String getGLKNoByID(long lGLTypeID) {
		String strReturn = "";
		try {
			String strSQL = "select ssubjectcode from sett_generalledger where ID="
					+ lGLTypeID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"ssubjectcode");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过科目编号查询科目Id
	 * 
	 * @param subjectCode
	 * @return lSubjectId @
	 */
	public static long getSubjectIdByCode(String subjectCode) {
		long lSubjectId = -1;
		try {
			//sett_glsubjectdefinition替换为sett_vglsubjectdefinition
		    //  ssegmentcode2替换为sSubjectCode
//			String strSQL = " select id from sett_vglsubjectdefinition where sSegmentCode2 = '"
//					+ subjectCode + "'";
			String strSQL = " select id from sett_vglsubjectdefinition where sSubjectCode = '"
				+ subjectCode + "'";
			strSQL += " and nstatus = " + Constant.RecordStatus.VALID;
			System.out.println("  通过科目编号查询科目Id :   \n" + strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, "id");
			if (c != null) {
				// lSubjectId = ((Long)c.iterator().next()).longValue();
				lSubjectId = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lSubjectId;
	}

	/**
	 * 功能：通过台账交易ID查询外部交易对手编号
	 * 
	 * @param lTransInvestmentID
	 * @return @
	 */
	public static String getCounterPartNoByTransInvestmentID(
			long lTransInvestmentID) {
		String strReturn = "";
		try {
			String strSQL = "select sCounterPartNo from sett_transinvestment where ID="
					+ lTransInvestmentID;
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"sCounterPartNo");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过证实书组合键（发放银行ID,票据类型ID,票据号），查询交易号
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static String getTransNoByBankBillKey(long lBankID,
			long lBillTypeID, String strBillNo) {
		String strReturn = "";
		try {
			StringBuffer sbSQL = new StringBuffer();
			long lTransactionType = -1;
			if (lBillTypeID == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION) {
				lTransactionType = SETTConstant.TransactionType.OPENFIXEDDEPOSIT;
				sbSQL.append("select distinct sTransNo from (");
				sbSQL
						.append("   select sTransNo from SETT_TRANSOPENFIXEDDEPOSIT ");
				sbSQL.append("   where nCertificationBankID=" + lBankID
						+ " and sDepositNo='" + strBillNo
						+ "' and nTransactionTypeID =" + lTransactionType);
				sbSQL.append(" union");
				sbSQL
						.append(" 	select distinct sTransNo from SETT_TRANSFIXEDCONTINUE ");
				sbSQL.append(" 	where nStatusID>0 and nNewCertificationBankID="
						+ lBankID + " and sNewDepositNo='" + strBillNo + "')");
			} else if (lBillTypeID == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION) {
				lTransactionType = SETTConstant.TransactionType.OPENNOTIFYACCOUNT;
				sbSQL
						.append(" select distinct sTransNo from SETT_TRANSOPENFIXEDDEPOSIT ");
				sbSQL.append(" where nStatusID>0 and nCertificationBankID="
						+ lBankID + " and sDepositNo='" + strBillNo
						+ "' and nTransactionTypeID =" + lTransactionType);
			} else if (lBillTypeID == SETTConstant.BankBillType.SEAL_CARD) {
				lTransactionType = SETTConstant.TransactionType.OPENNOTIFYACCOUNT;
				sbSQL.append("select distinct sTransNo from (");
				sbSQL
						.append("   select sTransNo from SETT_TRANSOPENFIXEDDEPOSIT ");
				sbSQL.append("   where nStatusID>0 and nSealBankID=" + lBankID
						+ " and sSealNo='" + strBillNo + "'");
				sbSQL.append(" union");
				sbSQL
						.append(" 	select distinct sTransNo from SETT_TRANSFIXEDCONTINUE ");
				sbSQL.append(" 	where nStatusID>0 and nNewSealBankID="
						+ lBankID + " and sNewSealNo='" + strBillNo + "')");
			} else if (lBillTypeID == SETTConstant.BankBillType.CABLE_TRANSFER
					|| lBillTypeID == SETTConstant.BankBillType.CASH_CHEQUE
					|| lBillTypeID == SETTConstant.BankBillType.DRAFT_TRUST_DEED
					|| lBillTypeID == SETTConstant.BankBillType.TRANSFER_ACCOUNT_CHEQUE) {
				sbSQL.append("select distinct sTransNo from (");
				sbSQL.append("   select distinct sTransNo ");
				sbSQL.append("   from sett_transcurrentdeposit ");
				sbSQL.append("   where nStatusID>0 and sbillno='" + strBillNo
						+ "'");
				sbSQL.append("   and nbillbankid=" + lBankID);
				sbSQL.append("   and nbilltypeid=" + lBillTypeID);
				sbSQL.append(" union ");
				sbSQL.append("   select distinct sTransNo ");
				sbSQL.append("   from SETT_TRANSOPENFIXEDDEPOSIT ");
				sbSQL.append("   where nStatusID>0 and sbillno='" + strBillNo
						+ "'");
				sbSQL.append("   and nbillbankid=" + lBankID);
				sbSQL.append("   and nbilltypeid=" + lBillTypeID);
				sbSQL.append(" ) ");
			}

			Collection c = SETTHTML.getCommonSelectList(sbSQL.toString(),
					"sTransNo");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.print(e.toString());
		}
		return strReturn;
	}

	public static String findClientNoByID(long lID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sResult = "";
		if (lID <= 0) {
			return sResult;
		}
		try {
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select scode from client where id=" + lID);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				sResult = rs.getString("scode");
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
		return sResult;
	}

	public static String findClientNameByID(long lID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sResult = "";
		if (lID <= 0) {
			return sResult;
		}
		try {
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select sname from client where id=" + lID);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				sResult = rs.getString("sname");
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
		return sResult;
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
			String strSQL = "select sDesc from sett_standardabstractdetail where ID="
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
	 * 通过外部账户的id查外部账户的No
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getExtAccountNoByID(long lID) {
		String strReturn = "";
		try {
			String strSQL = "select id,sExtAcctNo,sExtAcctName,sProvince,sCity,sBankName from sett_ExternalAccount where id="
					+ lID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sExtAcctNo");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.print(e.toString());
		}
		return strReturn;
	}

	/**
	 * 通过外部账户的id查外部客户的名称
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getExtClientNameByID(long lID) {
		String strReturn = "";
		try {
			String strSQL = "select id,sExtAcctNo,sExtAcctName,sProvince,sCity,sBankName from sett_ExternalAccount where id="
					+ lID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sExtAcctName");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.print(e.toString());
		}
		return strReturn;
	}

	/**
	 * 通过外部账户的id查外部客户的汇入地（省）
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getExtClientProvinceByID(long lID) {
		String strReturn = "";
		try {
			String strSQL = "select id,sExtAcctNo,sExtAcctName,sProvince,sCity,sBankName from sett_ExternalAccount where id="
					+ lID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sProvince");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.print(e.toString());
		}
		return strReturn;
	}

	/**
	 * 通过外部账户的id查外部客户的汇入地（市）
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getExtClientCityByID(long lID) {
		String strReturn = "";
		try {
			String strSQL = "select id,sExtAcctNo,sExtAcctName,sProvince,sCity,sBankName from sett_ExternalAccount where id="
					+ lID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sCity");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.print(e.toString());
		}
		return strReturn;
	}

	/**
	 * 通过外部账户的id查外部客户的汇入行名称
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getExtClientBankNameByID(long lID) {
		String strReturn = "";
		try {
			String strSQL = "select id,sExtAcctNo,sExtAcctName,sProvince,sCity,sBankName from sett_ExternalAccount where id="
					+ lID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sBankName");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.print(e.toString());
		}
		return strReturn;
	}

	/**
	 * 根据id查询现金流向的名称
	 * 
	 * @param lID
	 * @return String
	 */
	public static String getCashFlowNameByID(long lID) {
		String strReturn = "";
		try {
			Object obj = hmCashFlowNameByID.get(String.valueOf(lID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findCashFlow();
				obj = hmCashFlowNameByID.get(String.valueOf(lID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过客户ID查询客户的账户
	 * 
	 * @param lDetailID
	 * @return @
	 */
	public static String getAccountByClientID(long clientID) {
		String strReturn = "";
		try {
			String strSQL = "select sAccount from Client where ID=" + clientID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sAccount");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过账户编号得到账户的ID
	 * 
	 * @param lDetailID
	 * @return @
	 */
	public static long getAccountIdByNo(String accountNo) {
		long lReturn = -1;
		try {
			String strSQL = " select ID from sett_account where saccountno = '"
					+ accountNo + "'";
			strSQL += " and nstatusid = " + Constant.RecordStatus.VALID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "ID");
			if (c != null) {
				lReturn = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}
	/**
	 * 功能：通过账户编号和帐户状态得到账户的ID
	 * @param lDetailID
	 * @return @
	 */
	public static long getAccountIdByNoAndStatus(String accountNo,long lStatus) {
		long lReturn = -1;
		try {
			String strSQL = " select ID from sett_account where saccountno = '"
					+ accountNo + "'";
			if(lStatus > 0){
				strSQL += " and nstatusid = " + lStatus;
			}
			Collection c = SETTHTML.getCommonSelectList(strSQL, "ID");
			if (c != null) {
				lReturn = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}
	/**
	 * 功能：通过账户ID查询账户类型
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static long getAccountTypeByID(long lAccountID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lResult = -1;
		try {
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select nAccountTypeID from sett_account where ID="
					+ lAccountID);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				lResult = rs.getLong("nAccountTypeID");
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
		return lResult;
	}

	/**
	 * 功能：通过科目号查询科目类型
	 * 
	 * @param lDetailID
	 * @return @
	 */
	public static int getSubjectTypeByCode(String strSubjectNo) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int iResult = 0;

		try {
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select NSUBJECTTYPE from sett_vglsubjectdefinition where sSubjectcode='"
							+ strSubjectNo +"'");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				iResult = rs.getInt("NSUBJECTTYPE");
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
		return iResult;
	}

	/**
	 * 功能：通过利率计划ID得到利率计划名称
	 * 
	 * @param lDetailID
	 * @return @
	 */
	public static String getInterestRatePlanNameByID(long lInterestRatePlanID) {
		String strReturn = "";
		try {
			String strSQL = "select sName from sett_interestrateplan where ID="
					+ lInterestRatePlanID;
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
	 * 功能：通过银行利率ID得到银行利率名称
	 * 
	 * @param lDetailID
	 * @return @
	 */
	public static String getInterestRateNameByID(long lInterestRateID) {
		String strReturn = "";
		try {
			String strSQL = "select distinct sName from sett_InterestRate where ID="
					+ lInterestRateID;
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
	 * 功能：通过账户ID得到利率计划名称
	 * 
	 * @param lDetailID
	 * @return @
	 */
	public static String getInterestRatePlanNameByAccountID(long lAccountID) {
		String strReturn = "";
		try {
			String strSQL = "select a.sName from sett_interestrateplan a,sett_account b,sett_subaccount c where c.naccountid=b.id and c.ac_ninterestrateplanid=a.id and b.id="
					+ lAccountID;
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
	 * 功能：通过子账记ID得到放款通知单ID
	 * 
	 * @param marginSubaccountId
	 * @return loanNoteNo
	 * @author liwang
	 */
	public static long getLoanNoteIdBySubaccountID(long marginSubaccountId) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long loanNoteID = -1;

		try {
			con = Database.getConnection();
			String strSQL = "select AL_nLoanNoteID from sett_SubAccount where id="
					+ marginSubaccountId;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				loanNoteID = rs.getLong("AL_nLoanNoteID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return loanNoteID;

	}

	/**
	 * 功能：通过子账户ID得到利率
	 * 
	 * @param marginSubaccountId
	 * @return InterestRate
	 * @author liwang
	 */
	public static double getMarginRateBySubaccountID(long marginSubaccountId) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		double InterestRate = 0.00;
		try {
			con = Database.getConnection();
			String strSQL = "select af_mrate from sett_SubAccount where id="
					+ marginSubaccountId;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				InterestRate = rs.getDouble("af_mrate");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return InterestRate;
	}

	/**
	 * 功能：通过子账记ID得到定期存单号
	 * 
	 * @param marginSubaccountId
	 * @return depositNo
	 * @author liwang
	 */
	public static String getDepositNoBySubaccountID(long marginSubaccountId) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String depositNo = "";
		try {
			con = Database.getConnection();
			String strSQL = "select AF_sDepositNo from sett_SubAccount where id="
					+ marginSubaccountId;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				depositNo = rs.getString("AF_sDepositNo");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return depositNo;
	}
	
	/**
	 * 功能：通过定期存单号得到定期存单的计提利息
	 * 
	 * @param depositNo
	 * @return interest
	 * @author 马现福
	 */
	public static double getInterestByDepositNo(String depositNo) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		double interest = 0.0;
		try {
			con = Database.getConnection();
			String strSQL = "select AF_MPREDRAWINTEREST from sett_SubAccount where AF_SDEPOSITNO= '"
					+ depositNo+"'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				interest = rs.getDouble("AF_MPREDRAWINTEREST");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return interest;
	}

	/**
	 * 功能：通过担保业务的通知单ID得到获得通知单编号
	 * 
	 * @param loanNoteID
	 * @return loanNoteNO
	 * @author liwang
	 */
	public static String getAssureCodeByID(long loanNoteID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String loanNoteNO = "";
		try {
			con = Database.getConnection();
			String strSQL = "select  code  from LOAN_ASSURECHARGEFORM  where  id="
					+ loanNoteID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				loanNoteNO = rs.getString("code");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return loanNoteNO;
	}

	/**
	 * 功能：通过担保业务的通知单ID得到合同编号
	 * 
	 * @param loanid
	 * @return contractNo
	 * @author liwang
	 */
	public static String getContractIDByLoanID(long loanNoteID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String contractNo = "";
		try {
			con = Database.getConnection();
			String strSQL = "select lc.scontractcode scontractcode from LOAN_ASSURECHARGEFORM la,loan_contractform lc where la.contractid=lc.id  and  la.id="
					+ loanNoteID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				contractNo = rs.getString("scontractcode");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return contractNo;
	}

	/**
	 * 功能：通过利率计划ID得到利率计划类型ID
	 * 
	 * @param lInterestRateID
	 * @return lTypeID @
	 */
	public static long getInterestRatePlanTypeIDByID(long lInterestRatePlanID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lInterestRatePlanTypeID = -1;
		try {
			con = Database.getConnection();
			String strSQL = "select a.NINTERESTPLANTYPEID from sett_interestrateplan a where a.id="
					+ lInterestRatePlanID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				lInterestRatePlanTypeID = rs.getLong("NINTERESTPLANTYPEID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lInterestRatePlanTypeID;
	}

	/**
	 * 功能：通过交易费用设置ID查找交易费用设置的名称
	 * 
	 * @param lContractID
	 * @return @
	 */
	public static String getFeeTypeSettingNameByID(long lFeeTypeSettingID) {
		String strReturn = "";
		try {
			String strSQL = "select SNAME from sett_transactionfeetype where ID="
					+ lFeeTypeSettingID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "SNAME");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过委托付款凭证ID得到委托付款凭证号
	 * 
	 * @param lID
	 * @return @
	 */
	public static String getVoucherNoByID(long lID) {
		String strReturn = "";
		try {
			String strSQL = "select VoucherNo from sett_AccountTrustVoucher where ID = "
					+ lID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "VoucherNo");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过利率ID查询利率
	 * 
	 * @param lID
	 * @return @
	 */
	public static double getBankInterestByID(long lID) {
		double strReturn = 0;
		try {
			String strSQL = "select mRate from loan_InterestRate where ID = "
					+ lID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "mRate");
			if (c != null) {
				strReturn = ((java.math.BigDecimal) c.iterator().next())
						.doubleValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 获得结算上线日期（每日余额表中开始有记录的日期）
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public static Timestamp getSettlementSubmitDate() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Timestamp tsSubmitDate = null;
		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select min(dtDate) submitDate from sett_DailyAccountBalance");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				tsSubmitDate = rs.getTimestamp("submitDate");
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
		return tsSubmitDate;
	}

	// public methods write following block.
	/**
	 * 方法说明：根据 活期账户号 取得该活期账户的19位银行账户(上气网银用)
	 * 
	 * @param lUserID
	 * @return @
	 */
	public static String getBankAccountNOByCurrenctAccountID(String sAccountNo) {
		String strReturn = "";
		try {
			String strSQL = "select a.SBANKACCOUNTNO SBANKACCOUNTNO from SETT_BANKACCOUNTOFFILIALE a,SETT_ACCOUNT b where a.NWITHINACCOUNTID=b.id and b.SACCOUNTNO = '"
					+ sAccountNo.trim() + "'";
			Collection c = SETTHTML.getCommonSelectList(strSQL,
					"SBANKACCOUNTNO");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过客户类型ID查询客户类型名称
	 * 
	 * @param lClientTypeID
	 * @return @
	 */
	public static String getClientTypeNameByID(long lClientTypeID) {
		String strReturn = "";
		try {
			String strSQL = "select sName from sett_ClientType where ID ="
					+ lClientTypeID;
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
	 * 功能：通过股份公司统计项目ID查询项目名称（sefc）
	 * 
	 * @param lProjectID
	 * @return @
	 */
	public static String getStockProjectNameByID(long lProjectID) {
		String strReturn = "";
		try {
			String strSQL = "select projectName from sett_stockProjectNameSetting where ID ="
					+ lProjectID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "projectName");
			if (c != null) {
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过客户ID查询其客户类型ID
	 * 
	 * @param lClientID
	 * @return @
	 */
	public static long getClientTypeIDByID(long lClientID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		try {
			con = Database.getConnection();
			String strSQL = "select nCorpNatureID from client where ID = "
					+ lClientID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				lReturn = rs.getLong("nCorpNatureID");
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
		return lReturn;
	}

	/**
	 * 根据企业类型ID查询企业类型
	 * 
	 * @param lClientID
	 * @return
	 */
	public static String getEnterpriceNameByID(long ID) {
		String strReturn = "";
		try {
			Object obj = hmEnterpriceName.get(String.valueOf(ID));
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findEnterprice();
				obj = hmEnterpriceName.get(String.valueOf(ID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过旧系统账户号查询账户ID（航天科工） kenny (2005-07-22)
	 * 
	 * @param oldAccount
	 * @return long
	 */
	public static long getAccountIdByOldAccountNo(String oldAccount) {
		long lReturn = -1;
		try {
			String strSQL = "select accountId from SETT_ACCOUNTRELATIONSSETTING where originalAccountNo='"
					+ oldAccount + "'";
			// System.out.println("========strSQL="+strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, "accountId");
			if (c != null) {
				lReturn = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}

	/**
	 * 功能：通过旧系统账户号查询对应的客户ID（航天科工） kenny (2005-07-22)
	 * 
	 * @param oldAccount
	 * @return long
	 */
	public static long getClientIdByOldAccountNo(String oldAccount) {
		long lReturn = -1;
		try {
			String strSQL = "select b.nClientId from SETT_ACCOUNTRELATIONSSETTING a, sett_account b where a.accountId=b.id and originalAccountNo='"
					+ oldAccount + "'";
			// System.out.println("========strSQL="+strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, "nClientId");
			if (c != null) {
				lReturn = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}

	/**
	 * 功能：通过银行账号查询对应的银行账号ID（航天科工） kenny (2005-07-22)
	 * 
	 * @param branchCode
	 * @return long
	 */
	public static long getBankAccountIdByBranchCode(String branchCode) {
		long lReturn = -1;
		try {
			String strSQL = "select id from sett_branch where sbankaccountCode='"
					+ branchCode + "'";
			strSQL = strSQL + " and nStatusId=1 ";
			// System.out.println("========strSQL="+strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, "id");
			if (c != null) {
				lReturn = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}

	/**
	 * 功能：通过建行存款查询对应的银行账号ID（航天科工） kenny (2005-08-31)
	 * 
	 * @param enterpriseName
	 * @return long
	 */
	public static long getBankAccountIdByEnterpriseName(String enterpriseName) {
		long lReturn = -1;
		try {
			String strSQL = "select id from sett_branch where senterpriseName='"
					+ enterpriseName + "'";
			strSQL = strSQL + " and nStatusId=1 ";
			// System.out.println("========strSQL="+strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, "id");
			if (c != null) {
				lReturn = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}

	/**
	 * 功能：通过用户名称查询对应用户ID（航天科工） kenny (2005-07-22)
	 * 
	 * @param userName
	 * @return long
	 */
	public static long getUserIdByUserName(String userName) {
		long lReturn = -1;
		try {
			String strSQL = "select id from userinfo where sName='" + userName
					+ "'";
			// System.out.println("========strSQL="+strSQL);
			Collection c = SETTHTML.getCommonSelectList(strSQL, "id");
			if (c != null) {
				lReturn = ((java.math.BigDecimal) c.iterator().next())
						.longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lReturn;
	}

	/**
	 * 功能：通过账户ID查找旧账户号（航天科工） kenny (2005-07-22)
	 * 
	 * @param lAccountID
	 * @return String
	 */
	public static String getOriginalAcctNoByAcctID(long lAccountID) {
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			String strSQL = "select ORIGINALACCOUNTNO from Sett_AccountRelationsSetting where 1=1 "
					+ " and ACCOUNTID = " + lAccountID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				strReturn = rs.getString("ORIGINALACCOUNTNO");
			} else {
				strReturn = getAccountNoByID(lAccountID);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
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
		return strReturn;
	}

	/**
	 * 功能：通过账户ID查找对应银行实体账户号
	 * 
	 * @param lAccountID
	 * @return String
	 */
	public static String getBankFactAccountNO(long lAccountID) {
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			String strSQL = "select S_ACCOUNTNO from bS_BANKACCOUNTINFO where 1=1 "
					+ " and N_SUBJECTID = " + lAccountID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			System.out.println("==========" + strSQL);
			if (rs.next()) {
				strReturn = rs.getString("S_ACCOUNTNO");
				System.out.println("========" + rs.getString("S_ACCOUNTNO"));
			} else {
				strReturn = "";
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
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
		return strReturn;
	}

	/**
	 * 功能：通过账户ID查找对应银行实体账户名称
	 * 
	 * @param lAccountID
	 * @return String
	 */
	public static String getBankFactAccountName(long lAccountID) {
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			String strSQL = "select S_ACCOUNTNAME from bS_BANKACCOUNTINFO where 1=1 "
					+ " and N_SUBJECTID = " + lAccountID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			System.out.println("==========" + strSQL);
			if (rs.next()) {
				strReturn = rs.getString("S_ACCOUNTNAME");
				System.out.println("========" + rs.getString("S_ACCOUNTNAME"));
			} else {
				strReturn = "";
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
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
		return strReturn;
	}

	/**
	 * 功能：通过账户ID查找对应开户银行名
	 * 
	 * @param lAccountID
	 * @return String
	 */
	public static String getOpenBankName(long lAccountID) {
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			String strSQL = "select a.S_Name  openBankName from bs_banksetting a, bs_bankAccountInfo b where a.n_id = b.n_bankid "
					+ " and b.N_SUBJECTID = " + lAccountID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				strReturn = rs.getString("openBankName");
			} else {
				strReturn = "";
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
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
		return strReturn;
	}

	/**
	 * 功能：通过旧账户号查找总账类型（航天科工） kenny (2005-08-31)
	 * 
	 * @param oldAccountNo
	 * @return long
	 */
	public static long getGeneralledgerTypeByOldAccountNo(String oldAccountNo) {
		long lReturn = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			String strSQL = "select id from SETT_GENERALLEDGER where sname like '%"
					+ oldAccountNo + "%' and nstatusId=1";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				lReturn = rs.getLong("id");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
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
		return lReturn;
	}

	/**
	 * 通过账户种类code查询账户种类
	 * 
	 * @throws Exception
	 * @author yinghu
	 * 
	 * TODO To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Style - Code Templates
	 */
	public static String getAccountTypeCodeByAccountType(String accountType)
			throws Exception {
		String result = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();

			StringBuffer strSQL = new StringBuffer();
			strSQL.append("select saccounttypecode from SETT_ACCOUNTTYPE ");
			strSQL.append("where saccounttype='");
			strSQL.append(accountType);
			strSQL.append("'");
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("saccounttypecode");
			}
		} catch (Exception e) {
			throw e;
		} finally {
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

			}
		}
		return result;
	}

	/**
	 * 通过账户种类code查询账户种类
	 * 
	 * @throws Exception
	 * @author yinghu
	 * 
	 * TODO To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Style - Code Templates
	 */
	public static String getAccountTypeNameByAccountTypeID(long accountTypeID)
			throws Exception {
		String result = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();

			StringBuffer strSQL = new StringBuffer();
			strSQL.append(" select saccounttype from SETT_ACCOUNTTYPE " );
			strSQL.append(" where id = "+accountTypeID );
			
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("saccounttype");
			}
		} catch (Exception e) {
			throw e;
		} finally {
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

			}
		}
		return result;
	}
	
	/**
	 * 根据id查询外部账户编号
	 * @param ID
	 * @return
	 */
	public static String getBankAcctNameByAcctID(long ID)
	{
		String acctTypeName = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "select t.s_accountno from bs_bankaccountinfo t where t.n_id="+ID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				acctTypeName = rs.getString("s_accountno");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
		}
		
		return acctTypeName;
	}
    
	/**
	 * 功能：根据id 得到网银用户名称
	 * @param lAccountID
	 * @return
	 * @
	 */
	public static String getOBUserNameByID(long lAccountID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sName from ob_user t where id=" + lAccountID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sName");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}	
	
	/**
	 * 功能：通过账户ID查找账户号/公积金旧账户号（一汽财务） yangbai (2006-12-20)
	 * 打印页面显示账户号时使用（公积金账户打印显示旧账号，其他显示新账户号）
	 * @param  lAccountID
	 * @return String
	 */
	public static String getAccumulationOldAcctNoByAcctID(long lAccountID)
	{
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL = "select ORIGINALACCOUNTNO from Sett_AccountRelationsSetting where 1=1 " 
							+ " and ACCOUNTID = " + lAccountID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = rs.getString("ORIGINALACCOUNTNO");
				if(strReturn.length() != 6)//只有公积金700账户长度为6
					strReturn = getNoLineAccountNoByID(lAccountID);
			}
			else
			{
				strReturn = getNoLineAccountNoByID(lAccountID);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return strReturn;
	}
	/**
	 * 查出通知存款支取日期（一天/七天）
	 * @param args
	 */
	public static String getNoticeDay(long lOfficeID,long lCurrencyID,long noticeDay){
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strNoticeDay = "";
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select distinct a.nFixedDepositMonthID as DepositMonthID,(a.nFixedDepositMonthID || '个月') as DepositMonthDesc");
        sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
        sbSQL.append(" where a.nStatusID=1 and b.nStatusID=1 and a.nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.NOTIFY + " and a.nFixedDepositMonthID < 10000");
        sbSQL.append(" and a.nOfficeID = " + lOfficeID + " and a.nCurrencyID = " + lCurrencyID);
        sbSQL.append(" and b.OfficeID = " + lOfficeID + " and b.CurrencyID = " + lCurrencyID);
        sbSQL.append(" and a.nFixedDepositMonthID = " + noticeDay);
        sbSQL.append(" union");
        sbSQL.append(" select distinct a.nFixedDepositMonthID as DepositMonthID,((a.nFixedDepositMonthID-10000) || '天') as DepositMonthDesc");
        sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
        sbSQL.append(" where a.nStatusID=1 and b.nStatusID=1 and a.nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.NOTIFY + " and a.nFixedDepositMonthID>10000");
        sbSQL.append(" and a.nOfficeID = " + lOfficeID + " and a.nCurrencyID = " + lCurrencyID);
        sbSQL.append(" and b.OfficeID = " + lOfficeID + " and b.CurrencyID = " + lCurrencyID);
        sbSQL.append(" and a.nFixedDepositMonthID = " + noticeDay);
        try 
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			if (rs.next()){
				strNoticeDay = rs.getString("DepositMonthDesc");
			}
		} 
		catch (Exception e) {
			System.out.println(e.toString());
		}
		finally{
			try{
				if (rs != null){
					rs.close();
					rs = null;
				}if (ps != null){
					ps.close();
					ps = null;
				}if (con != null){
					con.close();
					con = null;
				}
			}
			catch (Exception e){
				;
			}
		}
        return strNoticeDay;
	}
	
	// 后台主测试函数
	public static void main(String[] args) {
		System.out.println("=========================开始==============!");
		System.out.println(" 结果为:" + NameRef.getBankFactAccountNO(179));
		System.out.println(" 结果为:" + NameRef.getOpenBankName(179));
		System.out.println("=========================结束==============!");

		// System.out.println(NameRef.getExtAccountNoByID(1));
		// System.out.println(NameRef.getAccountNoByID(1));
		// System.out.println(NameRef.getCashFlowNameByID(1));
		// System.out.println(NameRef.getAccountTypeByID(484));

	}
	
	/**
	 * 功能：通过子账户ID查找合同编号 Boxu Add 2008年5月8日
	 * 自营，委贷，结息和计提打印显示合同使用
	 * @param  lSubAccountID
	 * @return String
	 */
	public static String getContractcodeBySubAccountID(long lSubAccountID)
	{
		String contractCode = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL  = " select lc.scontractcode contractcode from sett_subaccount ss, loan_contractform lc, loan_payform lp ";
				   strSQL += " where ss.al_nloannoteid = lp.id and lp.ncontractid = lc.id ";
				   strSQL += " and ss.id = " + lSubAccountID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				contractCode = rs.getString("contractcode");
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return contractCode;
	}
	
	public static String getBankNameBybankID(long bankId) 
	{
		String strReturn = "";
		String strSQL = "select BANKNAME from SEC_CounterpartBankAccount where id = " + bankId;
		Collection c;
		try {
			c = SETTHTML.getCommonSelectList(strSQL,"BANKNAME");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
		
		return strReturn;
	}
	
}