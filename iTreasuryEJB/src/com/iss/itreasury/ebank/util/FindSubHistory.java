package com.iss.itreasury.ebank.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.iss.itreasury.util.Database;
import com.iss.itreasury.settlement.util.NameRef;

public class FindSubHistory {

	private final String[] ybwj = // 0001859
	{"241", "289", "290", "291",
			"293", "297", "349", "390", "394", "395",
			"512", "513", "514", "516", "517", "518", "519",
			"1037", "1684" ,"1690","1694", "1700", "1701",
			"1707", "1714", "1716", "1717", "1718",
			"1722", "1723", "1724", "1729", "1730",
			"1733", "1748", "1751", "1752", "1754",
			"1755", "1756", "1757", "1758", "1759", "1760",
			"1861", "1862", "1868", "1869", "1870", "1874",
			"1876", "1883", "1884", "1889", "1729"};

	private final String[] yqsj = // 0001749
	{"1754","1756","1757","1758","1759"};

	private final String[] wly = // 0000510
	{"513","514","515","516","517","518","519","520","1886"};

	private final String[] yqyy = // 0001711
	{"1715","1716","1717","1718","1719","1720"};

	public Map findabc(String lClientId, long lCurrencyID) throws SQLException
	{
		return this.compareSubAccount(lClientId,lCurrencyID);
	}
	
	private Map compareSubAccount(String lClientId, long lCurrencyID) throws SQLException 
	{
		Map list = new HashMap();
		if (lClientId.equals("0001859")) {
			list = this.findSubAccount(ybwj, lCurrencyID);
		}
		
		if (lClientId.equals("0001749")) {
			list = this.findSubAccount(yqsj, lCurrencyID);
		}

		if (lClientId.equals("0000510")) {
			list = this.findSubAccount(wly, lCurrencyID);
		}

		if (lClientId.equals("0001711")) {
			list = this.findSubAccount(yqyy, lCurrencyID);
		}

		return list;
	}

	private Map findSubAccount(String[] findlist, long lCurrencyID)
			throws SQLException {

		NameRef ref = new NameRef();
		Map map = new HashMap();
		for (int i = 0; i < findlist.length; i++) {
			map.put(findlist[i], findSubAccountParticular(findlist[i], lCurrencyID));
		}
		return map;
	}
	
	public ArrayList findCode(String lClientId, long lCurrencyID)
	{
		ArrayList list = new ArrayList();
		if (lClientId.equals("0001859")) {
			list = this.findCodeList(ybwj, lCurrencyID);
		}
		
		if (lClientId.equals("0001749")) {
			list = this.findCodeList(yqsj, lCurrencyID);
		}

		if (lClientId.equals("0000510")) {
			list = this.findCodeList(wly, lCurrencyID);
		}

		if (lClientId.equals("0001711")) {
			list = this.findCodeList(yqyy, lCurrencyID);
		}
	
		return list;
	}
	
	private ArrayList findCodeList(String[] findlist, long lCurrencyID)
	{
		ArrayList list = new ArrayList();
		for (int i = 0 ; i < findlist.length ; i++)
		{
			list.add(findlist[i]);
		}
		return list;
	}
	
	private Map findSubAccountParticular(String lClientId,long lCurrencyID) throws SQLException 
	{
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		
		Map map = new HashMap();
		
		NameRef ref = new NameRef();
		
		String strSQL = "select " +
				"a.ID as AccountID,a.sAccountNo AccountNo," +
				"c.ID as ClientID,c.sCode as ClientNo," +
				"c.sName as ClientName,  atp.naccountgroupid as Naccountgroupid," +
				"a.naccounttypeid as Naccounttypeid, atp.sAccountType as sAccountType " +
				"from " +
				"sett_Account a," +
				"Client c," +
				"sett_AccountType atp " +
				"where " +
				"a.nclientid=c.id and a.nCheckStatusid =4 and a.naccounttypeid=atp.id " +
				"and a.nCurrencyID=? and c.id=? " +
				"and a.naccounttypeid not in (12,20,21,30,31,32,81,82)" +
				"order by " +
				"atp.naccountgroupid, a.id";
		
		
		String strJFSQl = "select " +
				"a.ID as AccountID,a.sAccountNo AccountNo," +
				"c.ID as ClientID,c.sCode as ClientNo," +
				"c.sName as ClientName,  atp.naccountgroupid as Naccountgroupid," +
				"a.naccounttypeid as Naccounttypeid, atp.sAccountType as sAccountType " +
				"from " +
				"sett_Account a," +
				"Client c," +
				"sett_AccountType atp " +
				"where " +
				"a.nclientid=c.id and a.nCheckStatusid =4 and a.naccounttypeid=atp.id " +
				"and a.nCurrencyID=? and c.id=? " +
				"and a.naccounttypeid not in (20,30,31,32,81,82)" +
				"order by " +
				"atp.naccountgroupid, a.id";
		try{
			con = Database.getConnection();

			if(lClientId.equals("0001711"))
			{
				ps = con.prepareStatement(strJFSQl);
			}
			else
			{
				ps = con.prepareStatement(strSQL);
			}
			
			ps.setLong(1,lCurrencyID);
			
			ps.setString(2, lClientId);
			
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				StringBuffer str = new StringBuffer();
				str.append(rs.getString("accountid"));
				str.append(";");
				str.append(rs.getString("naccountgroupid"));
				str.append("@");
				str.append(rs.getString("naccounttypeid"));
				
				StringBuffer str1 = new StringBuffer();
				str1.append(rs.getString("sAccountType"));
				str1.append(" -- ");
				str1.append(NameRef.getNoLineAccountNo(rs.getString("AccountNo")));

				map.put(str.toString(),str1.toString());
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(rs != null)
			{
				rs.close();
			}
			if(ps != null)
			{
				ps.close();
			}
			if(con != null)
			{
				con.close();
			}
		}
		return map;
	}
}
