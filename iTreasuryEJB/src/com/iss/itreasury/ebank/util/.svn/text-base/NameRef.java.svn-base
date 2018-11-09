/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.ebank.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.iss.itreasury.ebank.obsystem.dataentity.ClientCapInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
/**
 * @author yiwang
 *
 */
public class NameRef
{
    private static HashMap hmUserName = new HashMap();
    private static HashMap hmApprovalName = new HashMap();
    private static HashMap hmClientName = new HashMap();
    private static HashMap hmClientCode = new HashMap();
    
    private static HashMap hmAccountNo = new HashMap();
	private static HashMap hmAccountName = new HashMap();
	
	private static HashMap hmCurrencyName = new HashMap();
	private static HashMap hmCurrencysymb = new HashMap();
	private static HashMap hmcolsname = new HashMap();

	private static void findUser()
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			//hmUserName = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, sname from ob_user");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				hmUserName.put(String.valueOf(rs.getLong("id")), rs.getString("sname"));
			}
			// 增加机制、机核等用户
			//TODO TOCONTINUE
			long []MachineUser = Constant.MachineUser.getAllCode(1,1);
			for( int i=0; i< MachineUser.length;i++)
			{
			    hmUserName.put(String.valueOf(MachineUser[i]), Constant.MachineUser.getName(MachineUser[i]));
			}
		}
		catch (Exception sqle)
		{
		    sqle.printStackTrace();
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

	}
	private static void findApproval()
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
		    //hmApprovalName = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, sname from ob_approvalSetting");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
			    hmApprovalName.put(String.valueOf(rs.getLong("id")), rs.getString("sname"));
			}
			// 增加机制、机核等用户
			//TODO TOCONTINUE
			long []MachineUser = Constant.MachineUser.getAllCode(1,1);
			for( int i=0; i< MachineUser.length;i++)
			{
			    hmApprovalName.put(String.valueOf(MachineUser[i]), Constant.MachineUser.getName(MachineUser[i]));
			}
		}
		catch (Exception sqle)
		{
		    sqle.printStackTrace();
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

	}
	private static void findClient()
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			//hmClientName = new HashMap();
			//hmClientCode = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, scode,sname from client");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				hmClientName.put(String.valueOf(rs.getLong("id")), rs.getString("sname"));
				hmClientCode.put(String.valueOf(rs.getLong("id")), rs.getString("scode"));
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
		    sqle.printStackTrace();
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
	}

	/**
	 * 方法说明：根据 UserID 取得用户名称 
	 * 注：原来是从map里获取名称，但是存在这样两个问题：
	 *    1.用户名被修改后没有重新reload map，此种方法取出的还是修改前的名称（这个是程序bug）；
	 *    2.即使reload map，在双机同时运行的情况下，还是存在一台机子修改了用户，另一台机子的map没有办法reload的情况。
	 *    基于以上两个原因，将本方法改为直接查询数据库的方式，来保证数据的准确性。
	 *    
	 * 使用场景：由于这种方式有一定的开销，因此，不推荐在列表查询显示时使用。列表显示时可以在原查询里直接关联用户表查出用户名。
	 *    
	 * @param lUserID
	 * @return
	 * @
	 */
	public static String getUserNameByID(long lUserID)
	{
		String strReturn = "";
		Connection conn = null;
		PreparedStatement ps = null;		
		ResultSet rs = null;

		try
		{
			conn = Database.getConnection();
			String strSQL = "select sName  from ob_user  where id = " + lUserID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = rs.getString("sName");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Log.print("获取网银用户名称时出错，" + e.getMessage());
			
		}finally{
			if(rs!=null)
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(ps!=null) 
				try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if(conn!=null) 
				try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return strReturn;
	}
	/**
	 * 通过ID获得审批流名称
	 * @param lID
	 * @return
	 */
	public static String getApprovalSettingNameByID(long lID)
	{
		String strReturn = "";
		try
		{
			Object obj = hmApprovalName.get(String.valueOf(lID));
			if (obj != null)
			{
				strReturn = obj.toString();
			}
			else
			{
			    findApproval();
				obj = hmApprovalName.get(String.valueOf(lID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 功能：通过客户ID查询客户名称
	 * @param lClientID
	 * @return
	 * @
	 */
	public static String getClientNameByID(long lClientID)
	{
		String strReturn = "";
		try
		{
			Object obj = hmClientName.get(String.valueOf(lClientID));
			if (obj != null)
			{
				strReturn = obj.toString();
			}
			else
			{
				findClient();
				obj = hmClientName.get(String.valueOf(lClientID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	/**
	 * 功能：通过客户ID查询客户名称
	 * @param lClientID
	 * @return
	 * @
	 */
	public static String getClientCodeByID(long lClientID)
	{
		String strReturn = "";
		try
		{
			Object obj = hmClientCode.get(String.valueOf(lClientID));
			if (obj != null)
			{
				strReturn = obj.toString();
			}
			else
			{
				findClient();
				obj = hmClientCode.get(String.valueOf(lClientID));
				strReturn = (obj != null ? obj.toString() : "");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	
	public static long getAcctGroupIDByAcctID(long lAcctID)
	{
		long lGroupID = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "select ACCOUNTTYPE.NACCOUNTGROUPID  from ACCOUNTINFO ,ACCOUNTTYPE  where ACCOUNTINFO.id = "+lAcctID + " and  ACCOUNTINFO.naccounttypeid=ACCOUNTTYPE.id ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				lGroupID = rs.getLong(1);
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
		
		return lGroupID;
	}
	public static String getCurrencySymbolbyId(long currencyid)
	{
	    String  currencySym = "";
	    String ColsName = "CHINESENAME";
		try
		{
			Object obj = hmCurrencysymb.get(String.valueOf(currencyid));
			if (obj != null)
			{
			    currencySym = obj.toString();
			}
			else
			{
			     
			    findOBCurrency(ColsName);
				obj = hmCurrencysymb.get(String.valueOf(currencyid));
				currencySym = (obj != null ? obj.toString() : "");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());   
		}
	
	 
		if(currencySym==null)
		{
		    return "";
		}
	    return currencySym;
	}
	
	public static String getCurrencyNamebyId(long currencyid,String langset)
	{
	    String currencyname = "";
	    String ColsName = "CHINESENAME";
	    
	    if(langset.trim().equalsIgnoreCase("en"))
	    { 
	       ColsName = "ENGLISHNAME"; 
	    }  
	    if(langset.trim().equalsIgnoreCase("zh"))
	    { 
	        ColsName = "CHINESENAME"; 
	    } 
	    
		try
		{
			Object obj = hmCurrencyName.get(String.valueOf(currencyid));
			if (obj != null && ColsName.equalsIgnoreCase((String) hmcolsname.get(String.valueOf(currencyid))))
			{
			    currencyname = obj.toString();
			}
			else
			{
			    findOBCurrency(ColsName);
				obj = hmCurrencyName.get(String.valueOf(currencyid));
				currencyname = (obj != null ? obj.toString() : "");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());   
		}
		 
 
		if(currencyname == null)
		{
		    return "";
		}
	    return currencyname;
	}
	
	private static void findOBCurrency(String Colsname)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
		    //hmCurrencyName = new HashMap();
		    //hmCurrencysymb = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select t.ID id,t."+Colsname+" sname,t.CURRSYMBOL symbol from SYS_CURRENCYSET t");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
 			while (rs.next())
			{
			    hmCurrencyName.put(String.valueOf(rs.getLong("id")), rs.getString("sname"));
			    hmCurrencysymb.put(String.valueOf(rs.getLong("id")), rs.getString("symbol"));
				hmcolsname.put(String.valueOf(rs.getLong("id")),Colsname);
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
	public static String getAcctTypeNameByAcctTypeID(long lAcctTypeID)
	{
		String acctTypeName = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "select saccounttype from sett_accounttype where id = "+lAcctTypeID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				acctTypeName = rs.getString("saccounttype");
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
	 * 系统关机时,把内存中的HashMap清空
	 *
	 */
	public static void cleanHashMap()
	{
		if (NameRef.hmUserName != null && !NameRef.hmUserName.isEmpty())
		{
			NameRef.hmUserName.clear();
		}
		if (NameRef.hmApprovalName != null && !NameRef.hmApprovalName.isEmpty())
		{
			NameRef.hmApprovalName.clear();
		}
		if (NameRef.hmClientName != null && !NameRef.hmClientName.isEmpty())
		{
			NameRef.hmClientName.clear();
		}
		
		if (NameRef.hmClientCode != null && !NameRef.hmClientCode.isEmpty())
		{
			NameRef.hmClientCode.clear();
		}
		if (NameRef.hmAccountNo != null && !NameRef.hmAccountNo.isEmpty())
		{
			NameRef.hmAccountNo.clear();
		}
		if (NameRef.hmAccountName != null && !NameRef.hmAccountName.isEmpty())
		{
			NameRef.hmAccountName.clear();
		}
		
		if (NameRef.hmCurrencyName != null && !NameRef.hmCurrencyName.isEmpty()) 
		{
			NameRef.hmCurrencyName.clear();
		}
		if (NameRef.hmCurrencysymb != null && !NameRef.hmCurrencysymb.isEmpty())
		{
			NameRef.hmCurrencysymb.clear();
		}
		if (NameRef.hmcolsname != null && !NameRef.hmcolsname.isEmpty())
		{
			NameRef.hmcolsname.clear();
		}
	}
	
	/**
	 * 根据id查询外部账号
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
			String strSQL = "select t.s_accountno as s_accountno from bs_bankaccountinfo t where t.n_id="+ID;
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
	 * 根据id查询外部账号
	 * @param ID
	 * @return
	 */
	public static String getBankPayAcctNameByAcctID(long ID)
	{
		String acctTypeName = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "select t.s_accountno  from bs_bankaccountinfo t where t.n_id ="+ID;
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
	 * 根据id查询外部账号
	 * @param ID
	 * @return
	 */
	public static String getCurrencyCodeByID(long ID)
	{
		String acctTypeName = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "select t.code from currencyinfo t where t.id="+ID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				acctTypeName = rs.getString("code");
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
	
	//	 从网银的收款方信息表中取收款账户名称
	public String getRecAccountNameByID(long accountID)
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
			 
		try
		{	
			String sql = "select t.* from ob_payeeinfo t where t.id = " + accountID + "";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("spayeename");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
				Log.print(e.toString());
			}    
		}
		return accountNO;
	}
	
//	 从网银的收款方信息表中取收款账号
	public String getRecAccountNOByID(long accountID)
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
			 
		try
		{	
			String sql = "select t.* from ob_payeeinfo t where t.id = " + accountID + "";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("spayeeacctno");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
				Log.print(e.toString());
			}    
		}
		return accountNO;
	}
	
    //从网银的收款方信息表中取收款方信息
	public ClientCapInfo getRecAccountInfoByID(long accountID)
	{

		ClientCapInfo info = new ClientCapInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
			 
		try
		{	
			String sql = "select t.* from ob_payeeinfo t where t.id = " + accountID + " and t.niscpfacct = 2";
			System.out.println("SQL="+sql);
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
			      info.setPayeeName(rs.getString("SPAYEENAME"));
			      info.setPayeeAccoutNO(rs.getString("SPAYEEACCTNO"));
			      info.setPayeeBankName(rs.getString("SPAYEEBANKNAME"));
			      info.setPayeeProv(rs.getString("SPAYEEPROV"));
			      info.setCity(rs.getString("SPAYEECITY"));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
				Log.print(e.toString());
			}    
		}
		return info;
	}
	
	// 从网银的收款方信息中获取结算收款账户ID
	public long getAccountIdByEbankRecId(long ebankAccountID)
	{
		long accountID = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
			 
		try
		{	
			String sql = "select sett.id id from ob_payeeinfo ob,sett_account sett  where ob.id = " + ebankAccountID + " and ob.SPAYEEACCTNO=sett.SACCOUNTNO ";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountID = rs.getLong("id");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
				Log.print(e.toString());
			}    
		}
		return accountID;
	}
	
	//
//	 从结算表中取账号
	public String getAccountNOByIDFromSett(long accountID) throws Exception
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{

			String sql = "select t.* from sett_account t where t.id = " + accountID + "";	
		
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("saccountno");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
		return accountNO;
	}
	
	/**
	 * 根据银行账户id查询账号
	 * @author 马现福
	 * @param ID
	 * @return
	 */
	public static String getAccountNOByAccountID(long accountID) throws Exception
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{

			String sql = "select a.S_ACCOUNTNO from BS_BANKACCOUNTINFO a,BS_BANKSETTING b where a.n_rdstatus=1 and a.n_bankid=b.n_id and a.n_id = " + accountID;	
		
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("S_ACCOUNTNO");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
		return accountNO;
	}
	
	/**
	 * 根据银行账户id查询账号
	 * @author 马现福
	 * @param ID
	 * @return
	 */
	public static String getAccountNameByAccountID(long accountID) throws Exception
	{

		String accountName = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{

			String sql = "select a.S_ACCOUNTNAME from BS_BANKACCOUNTINFO a,BS_BANKSETTING b where a.n_rdstatus=1 and a.n_bankid=b.n_id and a.n_id = " + accountID;	
		
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountName = rs.getString("S_ACCOUNTNAME");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
		return accountName;
	}
	
	/**
	 * 国家代码和名称
	 * @author 马现福
	 * @param lCountryID
	 * @return
	 */
	public static String getCountryNameByID(long lCountryID)throws Exception {
		if (lCountryID <= 0)
			return "";
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{

			String sql = "select t.S_NAME from bs_countrysetting t where t.n_id = " + lCountryID;	
		
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				strReturn = rs.getString("S_NAME");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
	 * 根据银行账户id查询银行名称
	 * @param ID
	 * @return
	 */
	public static String getBankNameByAcctID(long ID)
	{
		String acctTypeName = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "select t.s_branchname from bs_bankaccountinfo t  where t.n_id="+ID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				acctTypeName = rs.getString("s_branchname");
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
	 * @author 马现福
	 * @param bankType
	 * @return
	 */
	public static String getBankIdByRefCode(String bankType) {
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();
			String strSQL = "select t.N_ID from bs_banksetting t  where t.S_REFERENCECODE='"+bankType + "'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				strReturn +=String.valueOf(rs.getLong("N_ID"))+","; 
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			Log.print("根据银行id[" + bankType + "]获取银行名称时出现异常，出错原因："
					+ e.getMessage());
		}
		return strReturn.length()>0?strReturn.substring(0, strReturn.lastIndexOf(",")):"";
	}
	
	/**
	 * @author 马现福
	 * @param lBankID
	 * @return
	 */
	public static String getBankNameByID(long lBankID) {
		if (lBankID <= 0)
			return "";
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();
			String strSQL = "select t.s_name from bs_banksetting t  where t.n_id="+lBankID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = rs.getString("s_name");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			Log.print("根据银行id[" + lBankID + "]获取银行名称时出现异常，出错原因："
					+ e.getMessage());
		}
		return strReturn;
	}
	
	/**
	 * @author 马现福
	 * @param currencyID
	 * @return
	 */
	public static String getCurrencyNameByID(long currencyID) {
		if (currencyID <= 0)
			return "";
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = Database.getConnection();
			String strSQL = "select t.name from currencyinfo t  where t.id="+currencyID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = rs.getString("name");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			Log.print("根据币种id[" + currencyID + "]获取币种名称时出现异常，出错原因："
					+ e.getMessage());
		}
		return strReturn;
	}

	/**
	 * 根据userid查询clientid
	 * @param userid
	 * @return
	 */
	public static long getClientByUserID(long userID)
	{
		long ClientID = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "select t.NCLIENTID from ob_user t  where t.ID="+userID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				ClientID = rs.getLong("NCLIENTID");
			}
			
		}
		catch(Exception e)
		{
			Log.print(e.toString());
		}
		finally
		{
			try {
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return ClientID;
	}
	
//	 从网银的收款方信息表中取收款账户名称
	public  static String getReceveAccountNameByID(long accountID)
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
			 
		try
		{	
			String sql = "select t.* from ob_payeeinfo t where t.id = " + accountID + "";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("spayeename");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
				Log.print(e.toString());
			}    
		}
		return accountNO;
	}
	
//	 从网银的收款方信息表中取收款账号
	public static String getReceveAccountNOByID(long accountID)
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
			 
		try
		{	
			String sql = "select t.* from ob_payeeinfo t where t.id = " + accountID + "";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("spayeeacctno");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
				Log.print(e.toString());
			}    
		}
		return accountNO;
	}
	
	
	//通过id查找摘要名称 add by xlchang 2010-12-11
	public static String getAbstractNameByID(long id) 
	{

		String name = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{

			String sql = "select s.sdesc from ob_standardabstract s where s.id =  " + id + "";	
		
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				name = rs.getString("sdesc");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();			
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
				Log.print(e.toString());
			}    
		}
		return name;
	}
	public static boolean verifyRelationByWorkflowID(long wfid)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean isExist = false;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from ob_approvalrelationnew ");
			sb.append(" where APPROVALID = "+wfid+"");
			ps = con.prepareStatement(sb.toString());
			System.out.print(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				isExist =true;
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
				e.printStackTrace();
			}
	
		}
		return isExist;
	}
	
	/**
	 * 功能：通过账户ID查询账户类型
	 * 
	 * @param lAccountID
	 * @return @
	 */
	public static long getAccountOfficeByID(long lAccountID) throws Exception{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lResult = -1;
		try {
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select nofficeid from sett_account where ID="
					+ lAccountID);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				lResult = rs.getLong("nofficeid");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} 
		finally
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
		return lResult;
	}
	
	
}
