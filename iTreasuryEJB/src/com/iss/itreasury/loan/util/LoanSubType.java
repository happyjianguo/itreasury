package com.iss.itreasury.loan.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.util.Database;

public  class LoanSubType {
	
	
	
	/**
	 * 获得贷款当中的自营，银团，委托贷款的子类型
	 * @return
	 * @throws SQLException 
	 * @throws SQLException 
	 */
    public static List getLoanSubTypes(long officeID , long currencyID )  {
		
		List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		
	
	try{
		
		sql="select a.id as subTypeID, a.loantypeid as mainTypeID, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.YT+","+LOANConstant.LoanType.ZY+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{	
			Map map = new HashMap();
			String subTypeID = rs.getString("subTypeID");
			String typeName= rs.getString("typeName");
			String mainTypeID = rs.getString("mainTypeID");
			map.put("subTypeID",subTypeID);
			map.put("typeName", typeName);
			map.put("mainTypeID", mainTypeID);
			list.add(map);
			
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return list;
		
	}
	/**
	 * 获得贷款当中的自营，银团，委托贷款的子类型
	 * @return
	 * @throws SQLException 
	 * @throws SQLException 
	 */
    public static List getLoanSubTypes6(long officeID , long currencyID )  {
		
		List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		
	
	try{
		
		sql="select a.id as subTypeID, a.loantypeid as mainTypeID, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.YT+","+LOANConstant.LoanType.ZY+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID+" order by a.loantypeid,a.id";
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{	
			Map map = new HashMap();
			String subTypeID = rs.getString("subTypeID");
			String typeName= rs.getString("typeName");
			String mainTypeID = rs.getString("mainTypeID");
			map.put("subTypeID",subTypeID);
			map.put("typeName", typeName);
			map.put("mainTypeID", mainTypeID);
			list.add(map);
			
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return list;
		
	}
    
 public static List getLoanSubTypesNEW1(long officeID , long currencyID )  {
		
		List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		
	
	try{
		
		sql="select a.id as subTypeID, a.loantypeid as mainTypeID, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.YT+","+LOANConstant.LoanType.ZY+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{	
			Map map = new HashMap();
			String subTypeID = rs.getString("subTypeID");
			String typeName= rs.getString("typeName");
			String mainTypeID = rs.getString("mainTypeID");
			map.put("subTypeID",subTypeID);
			map.put("typeName", typeName);
			map.put("mainTypeID", mainTypeID);
			list.add(map);
			
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return list;
		
	}
    
  /**
    * 得到贷款类型的所有子类型
    * @param officeID
    * @param currencyID
    * @return
    */ 
 public static List getAllLoanSubTypes(long officeID , long currencyID )  {
		
		List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		
	
	try{
		
		sql="select a.id as subTypeID, a.loantypeid, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
		sql +=" and a.loantypeid not in("+LOANConstant.LoanType.XDZR+","+LOANConstant.LoanType.CREDIT+","+LOANConstant.LoanType.MFXD+")";
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next())
		{	
			Map map = new HashMap();
			String subTypeID = rs.getString("subTypeID");
			String typeName= rs.getString("typeName");
			map.put("subTypeID",subTypeID);
			map.put("typeName", typeName);
			list.add(map);
			
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return list;
		
	}
 
 /**
	 * 获得贷款当中的自营，银团，委托贷款,贴现的子类型
	 * @return
	 * @throws SQLException 
	 * @throws SQLException 
	 */
 public static List getLoanSubTypes1(long officeID , long currencyID )  {
		
		List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		
	
	try{
		
		sql="select a.id as subTypeID, a.loantypeid, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.YT+","+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.TX+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{	
			Map map = new HashMap();
			String subTypeID = rs.getString("subTypeID");
			String typeName= rs.getString("typeName");
			map.put("subTypeID",subTypeID);
			map.put("typeName", typeName);
			list.add(map);
			
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return list;
		
	}
 
 
 /**
	 * 获得贷款当中的自营，银团，委托贷款,融资租赁的子类型
	 * @return
	 * @throws SQLException 
	 * @throws SQLException 
	 */
public static List getLoanSubTypes2(long officeID , long currencyID )  {
		
		List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		
	
	try{
		
		sql="select a.id as subTypeID, a.loantypeid, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.YT+","+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.RZZL+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{	
			Map map = new HashMap();
			String subTypeID = rs.getString("subTypeID");
			String typeName= rs.getString("typeName");
			map.put("subTypeID",subTypeID);
			map.put("typeName", typeName);
			list.add(map);
			
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return list;
		
	}



/**
 * 获得贷款当中的自营，银团，委托贷款,融资租赁的子类型
 * @return
 * @throws SQLException 
 * @throws SQLException 
 */
public static List getLoanSubTypes3(long officeID , long currencyID )  {
	
	List list = new ArrayList();
	
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String sql= null;
	

try{
	
	sql="select a.id as subTypeID, a.loantypeid, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
			" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.YT+","+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.RZZL+","+LOANConstant.LoanType.DB+","+LOANConstant.LoanType.TX+") and a.id=b.subloantypeid " +
			"and b.currencyid="+currencyID+"and b.officeid="+officeID;
	con = Database.getConnection();
	ps = con.prepareStatement(sql);
	rs = ps.executeQuery();
	while (rs.next())
	{	
		Map map = new HashMap();
		String subTypeID = rs.getString("subTypeID");
		String typeName= rs.getString("typeName");
		map.put("subTypeID",subTypeID);
		map.put("typeName", typeName);
		list.add(map);
		
	}
	
}catch (Exception e){
	
	// TODO Auto-generated catch block
	e.printStackTrace();
	try {
		cleanup(ps);
		cleanup(con);
		cleanup(rs);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	

}finally{
	
	try {
		cleanup(ps);
		cleanup(con);
		cleanup(rs);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
	
	return list;
	
}


/**
 * 获得贷款当中的自营,委托贷款,贴现,担保的子类型
 * @return
 * @throws SQLException 
 * @throws SQLException 
 */
public static List getLoanSubTypes4(long officeID , long currencyID )  {
	
	List list = new ArrayList();
	
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String sql= null;
	

try{
	
	sql="select a.id as subTypeID, a.loantypeid, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
			" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.TX+","+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.DB+") and a.id=b.subloantypeid " +
			"and b.currencyid="+currencyID+"and b.officeid="+officeID;
	con = Database.getConnection();
	ps = con.prepareStatement(sql);
	rs = ps.executeQuery();
	while (rs.next())
	{	
		Map map = new HashMap();
		String subTypeID = rs.getString("subTypeID");
		String typeName= rs.getString("typeName");
		map.put("subTypeID",subTypeID);
		map.put("typeName", typeName);
		list.add(map);
		
	}
	
}catch (Exception e){
	
	// TODO Auto-generated catch block
	e.printStackTrace();
	try {
		cleanup(ps);
		cleanup(con);
		cleanup(rs);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	

}finally{
	
	try {
		cleanup(ps);
		cleanup(con);
		cleanup(rs);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
	
	return list;
	
}
    
    

    
    
    
    
    
    
    
    
    
    
    
    private static  void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private static void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private static void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	
	
	/**
	 * 获得贷款当中的自营，银团，委托贷款的子类型
	 * @return
	 * @throws SQLException 
	 * @throws SQLException 
	 */
    public static List getLoanSubTypes5(long officeID , long currencyID )  {
		
		List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		
	
	try{
		
		sql="select a.id as subTypeID, a.loantypeid as mainTypeID, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.ZY+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{	
			Map map = new HashMap();
			String subTypeID = rs.getString("subTypeID");
			String typeName= rs.getString("typeName");
			String mainTypeID = rs.getString("mainTypeID");
			map.put("subTypeID",subTypeID);
			map.put("typeName", typeName);
			map.put("mainTypeID", mainTypeID);
			list.add(map);
			
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return list;
		
	}
    /**
     * 根据子类型id返回子类型名称
     * @param officeID
     * @param currencyID
     * @param lSubLoanType
     * @return
     */
    public static String getLoanSubTypesNew(long officeID , long currencyID,long lSubLoanType)
    {
    	List list = new ArrayList();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		//返回的贷款子类型
		String typeName = "";
		
	
	try{
		/*
		sql="select a.id as subTypeID, a.loantypeid as mainTypeID, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.ZY+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
				*/
		
		sql = "select a.name as typeName "
			+" from loan_loantypesetting a, loan_loantyperelation b "
			+" where a.statusid = 1 "
			+" and a.loantypeid = b.loantypeid "
			+" and a.id = b.subloantypeid "
			+" and b.currencyid="+currencyID+"and b.officeid="+officeID
			+" and a.id="+lSubLoanType;
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{	
			typeName= rs.getString("typeName");
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return typeName;
    }
    
    /**
     * 根据子类型id返回子类型名称
     * @param officeID
     * @param currencyID
     * @param lSubLoanType
     * @return
     */
    public static LoanTypeSettingInfo getLoanSubTypesByTransNo(String transNo)
    {
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		StringBuffer sqlContractform = new StringBuffer();
		sqlContractform.append(" select loan.nsubtypeid from loan_contractform con,loan_loanform loan,sett_vtransaction vtran ");
		sqlContractform.append(" where con.nloanid = loan.id and con.id = vtran.contractid ");
		sqlContractform.append(" and vtran.transno = " + transNo);
		StringBuffer sqlDiscount = new StringBuffer();
		sqlDiscount.append(" select con.nbankacceptpo,con.nbizacceptpo ");
		sqlDiscount.append(" from loan_contractform con,loan_loanform loan,sett_vtransaction vtran ");
		sqlDiscount.append(" where con.nloanid = loan.id and con.id = vtran.contractid ");
		sqlDiscount.append(" and vtran.transno = " + transNo);
		//返回的贷款子类型
		LoanTypeSettingInfo info = new LoanTypeSettingInfo();		
	
	try{
		/*
		sql="select a.id as subTypeID, a.loantypeid as mainTypeID, a.name as typeName from loan_loantypesetting a, loan_loantyperelation b " +
				" where a.statusid=1 and a.loantypeid= b.loantypeid and  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.ZY+") and a.id=b.subloantypeid " +
				"and b.currencyid="+currencyID+"and b.officeid="+officeID;
				*/
		
		sql = "select a.name as typeName, a.loanTypeID, a.code as typeCode, a.id as subTypeID, -1 as nbankacceptpo, -1 as nbizacceptpo "
			+" from loan_loantypesetting a, loan_loantyperelation b "
			+" where a.statusid = 1 "
			+" and a.loantypeid = b.loantypeid "
			+" and a.id = b.subloantypeid "
			+" and a.id in ("+sqlContractform+")"
		    +" and a.loantypeid != "+ LOANConstant.LoanType.TX 
		    +" union all "
			+" select '', -1, '', -1, temp.nbankacceptpo, temp.nbizacceptpo "
			+" from ("+sqlDiscount+" and con.ntypeid = "+LOANConstant.LoanType.TX+") temp";
		    
		System.out.println("***dwj*** : ");
		System.out.println("***dwj*** : " + sql);
		System.out.println("***dwj*** : ");
		con = Database.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{
			info.setName(rs.getString("typeName"));
			info.setLoanTypeID(rs.getLong("loanTypeID"));
			info.setCode(rs.getString("typeCode"));
			info.setId(rs.getLong("subTypeID"));
			info.setBankAcceptPo(rs.getLong("nbankacceptpo"));
			info.setBizAcceptPo(rs.getLong("nbizacceptpo"));
		}
		
	}catch (Exception e){
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}finally{
		
		try {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return info;
    }


}
