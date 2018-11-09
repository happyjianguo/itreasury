/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settpaynotice.dao;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.settpaynotice.dataentity.SettDiscountBillInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettDiscountBillDAO extends SettlementDAO
{

	public SettDiscountBillDAO()
	{
		super("LOAN_DISCOUNTCONTRACTBILL",true);
		super.setUseMaxID();
	}
	public SettDiscountBillDAO(Connection conn)
	{
		super("LOAN_DISCOUNTCONTRACTBILL",true, conn);
		super.setUseMaxID();
	}
	public SettDiscountBillInfo findDiscountBillByCredenceID(long lCredenceID)
	{
		SettDiscountBillInfo info = null;
		String strSQL = "";
		try
		{
			this.initDAO();
			strSQL = "select * from " + this.strTableName + " where nDiscountCredenceID = " + lCredenceID + " and nstatusid=" + Constant.RecordStatus.VALID;
			transPS = prepareStatement(strSQL);
			transRS = executeQuery();
			if (transRS.next())
			{
				info = new SettDiscountBillInfo();
				info.setAcceptPOTypeID(transRS.getLong("nAcceptPOTypeID"));
				info.setAddDays(transRS.getLong("nAddDays"));
				info.setAmount(transRS.getDouble("mAmount"));
				info.setBank(transRS.getString("sBank"));
				info.setBillSourceTypeID(transRS.getLong("nBillSourceTypeID"));
				info.setCheckAmount(transRS.getDouble("mCheckAmount"));
				info.setCode(transRS.getString("sCode"));
				info.setConsignStatusID(transRS.getLong("nConsignStatusID"));
				info.setContractID(transRS.getLong("nContractID"));
				info.setCreate(transRS.getTimestamp("dtCreate"));
				info.setDiscountCredenceID(transRS.getLong("nDiscountCredenceID"));
				info.setEnd(transRS.getTimestamp("dtEnd"));
				info.setFormerConstatusID(transRS.getLong("nFormerConstatusID"));
				info.setFormerOwner(transRS.getString("sFormerOwner"));
				info.setId(transRS.getLong("id"));
				info.setIsLocal(transRS.getLong("nIsLocal"));
				info.setSerialNo(transRS.getLong("nSerialNo"));
				info.setStrAcceptorAccount(transRS.getString("StrAcceptorAccount"));
				info.setStrAcceptorBank(transRS.getString("StrAcceptorBank"));
				info.setStrAcceptorName(transRS.getString("StrAcceptorName"));
				info.setUserName(transRS.getString("sUserName"));
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return info;
	}
	/**
	 * 保存贴现票据信息，操作LOAN_DISCOUNTCONTRACTBILL表
	 * @param DiscountBillInfo info,
	 * @param String tsCreate,
	 * @param String tsEnd,
	 * @param long lCurrencyID,
	 * @param long lOfficeID
	 * @return long
	 * @throws RemoteException,IException
	 */
	public long addBillList(DiscountBillInfo info,
			String tsCreate,
			String tsEnd,
			long lCurrencyID,
			long lOfficeID) throws Exception {
		int flag =0;
		Connection con = null;
		//ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			con = Database.getConnection();
			sql=" Insert into LOAN_DISCOUNTCONTRACTBILL(ID,NCONTRACTID,NSERIALNO,SUSERNAME,SBANK,nIsLocal,dtCreate,dtEnd,sCode,mAmount,nStatusID,nAddDays,nAcceptPOTypeID,sFormerOwner,nCurrencyID,nOfficeID)"
				+ " values ((select nvl(max(ID)+1,1) from LOAN_DISCOUNTCONTRACTBILL),"+info.getDiscountContractID()+","+info.getSerialNo()+",'"+info.getUserName()+"','"+info.getBank()+"',"+info.getIsLocal()+",to_date('"+tsCreate+"','yyyy-mm-dd'),to_date('"+tsEnd+"','yyyy-mm-dd'),'"+info.getCode()+"',"+info.getAmount()+",1,"+info.getAddDays()+","+info.getAcceptPOTypeID()+",'"+info.getFormerOwner()+"',"+lCurrencyID+","+lOfficeID+") ";
			ps = con.prepareStatement(sql);
			//rs=ps.executeQuery();
			flag=ps.executeUpdate();
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		
		return flag;
		
	}
	public long addBillList1(long contractId, long num, String strUser, String strBank, long isLocal, String tsCreate, String tsEnd, String strCode, double amount, long addDay, long acceptPOTypeID, String strFormerOwner, long currencyID, long officeID) throws Exception {
		int flag =0;
		Connection con = null;
		//ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			con = Database.getConnection();
			sql=" Insert into LOAN_DISCOUNTCONTRACTBILL(ID,NCONTRACTID,NSERIALNO,SUSERNAME,SBANK,nIsLocal,dtCreate,dtEnd,sCode,mAmount,nStatusID,nAddDays,nAcceptPOTypeID,sFormerOwner,nCurrencyID,nOfficeID)"
				+ " values ((select nvl(max(ID)+1,1) from LOAN_DISCOUNTCONTRACTBILL),"+contractId+","+num+",'"+strUser+"','"+strBank+"',"+isLocal+",to_date('"+tsCreate+"','yyyy-mm-dd'),to_date('"+tsEnd+"','yyyy-mm-dd'),'"+strCode+"',"+amount+",1,"+addDay+","+acceptPOTypeID+",'"+strFormerOwner+"',"+currencyID+","+officeID+") ";
			ps = con.prepareStatement(sql);
			//rs=ps.executeQuery();
			flag=ps.executeUpdate();
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		
		return flag;
		
	}
	public List getBillList(long contractId) throws Exception {
		int flag =0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		List list = new ArrayList();
		try
		{
			con = Database.getConnection();
			sql="select ID,NCONTRACTID,NSERIALNO,SUSERNAME,SBANK,NISLOCAL,to_char(DTCREATE,'YYYY-MM-DD') DTCREATE,to_char(DTEND,'YYYY-MM-DD') DTEND,SCODE,MAMOUNT,NSTATUSID,NADDDAYS,SFORMEROWNER,NACCEPTPOTYPEID from LOAN_DISCOUNTCONTRACTBILL where NSTATUSID=1 and NCONTRACTID ="+contractId;
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Map map = new HashMap();
				map.put("ID", rs.getString("ID"));
				map.put("NCONTRACTID", rs.getString("NCONTRACTID"));
				map.put("NSERIALNO", rs.getString("NSERIALNO"));
				map.put("SUSERNAME", rs.getString("SUSERNAME"));
				map.put("SBANK", rs.getString("SBANK"));
				map.put("NISLOCAL", rs.getString("NISLOCAL"));
				map.put("DTCREATE", rs.getString("DTCREATE"));
				map.put("DTEND", rs.getString("DTEND"));
				map.put("SCODE", rs.getString("SCODE"));
				map.put("MAMOUNT", rs.getString("MAMOUNT"));
				map.put("NADDDAYS", rs.getString("NADDDAYS"));
				map.put("SFORMEROWNER", rs.getString("SFORMEROWNER"));
				map.put("NACCEPTPOTYPEID", rs.getString("NACCEPTPOTYPEID"));
				list.add(map);
			}
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		
		return list;
		
	}
	
	public List getBillListByPayNoticeID(long payNoticeID) throws Exception {
		int flag =0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		List list = new ArrayList();
		try
		{
			con = Database.getConnection();
			sql="select ID,NCONTRACTID,NSERIALNO,SUSERNAME,SBANK,NISLOCAL,to_char(DTCREATE,'YYYY-MM-DD') DTCREATE,to_char(DTEND,'YYYY-MM-DD') DTEND,SCODE,MAMOUNT,NSTATUSID,NADDDAYS,SFORMEROWNER,NACCEPTPOTYPEID from LOAN_DISCOUNTCONTRACTBILL where NSTATUSID=1 and ndiscountcredenceid ="+payNoticeID;
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Map map = new HashMap();
				map.put("ID", rs.getString("ID"));
				map.put("NCONTRACTID", rs.getString("NCONTRACTID"));
				map.put("NSERIALNO", rs.getString("NSERIALNO"));
				map.put("SUSERNAME", rs.getString("SUSERNAME"));
				map.put("SBANK", rs.getString("SBANK"));
				map.put("NISLOCAL", rs.getString("NISLOCAL"));
				map.put("DTCREATE", rs.getString("DTCREATE"));
				map.put("DTEND", rs.getString("DTEND"));
				map.put("SCODE", rs.getString("SCODE"));
				map.put("MAMOUNT", rs.getString("MAMOUNT"));
				map.put("NADDDAYS", rs.getString("NADDDAYS"));
				map.put("SFORMEROWNER", rs.getString("SFORMEROWNER"));
				map.put("NACCEPTPOTYPEID", rs.getString("NACCEPTPOTYPEID"));
				list.add(map);
			}
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		
		return list;
		
	}
	
	public long deleteBill(String Id) throws Exception {
		int flag =0;
		Connection con = null;
		//ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			con = Database.getConnection();
			sql="update LOAN_DISCOUNTCONTRACTBILL set  NSTATUSID =0 where id ="+Id;
			ps = con.prepareStatement(sql);
			//rs=ps.executeQuery();
			flag=ps.executeUpdate();
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		
		return flag;
		
	}
	
	public PageLoader getContractBillList(SettContractInfo conditionInfo) {
		String m_sbSelect=" ID,NCONTRACTID,NSERIALNO,SUSERNAME,SBANK,NISLOCAL,to_char(DTCREATE,'YYYY-MM-DD') DTCREATE,DTEND," +
				"SCODE,MAMOUNT,NSTATUSID,NADDDAYS,SFORMEROWNER,NACCEPTPOTYPEID ";
		String m_sbFrom=" LOAN_DISCOUNTCONTRACTBILL ";
		String m_sbWhere=" NSTATUSID=1 and NCONTRACTID ="+conditionInfo.getId();
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.transdiscount.dataentity.DiscountBillInfo",
			null);
		return pageLoader;
	}
	
	public long updateDiscountBill(String Id,String dAmount,String NIsLocal,String endday,String addDays,Timestamp publicDate,SettContractInfo conditionInfo) throws Exception {
		int flag =0;
		Connection con = null;
		//ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		int days =0;
		double dAccrual = 0; // 贴现利息
		double dRealAmount = 0; // 实付贴现金额
		double dPurchaserInterest = 0; //买方付息
		
		if(Long.parseLong(NIsLocal)==Constant.YesOrNo.NO){
    		days=DataFormat.getIntervalDays(publicDate,DataFormat.getDateTime(endday))-1+Integer.parseInt(addDays)+3;//非本地要加3天
    	}else{
    		days=DataFormat.getIntervalDays(publicDate,DataFormat.getDateTime(endday))-1+Integer.parseInt(addDays);
    	}
    	
		dAccrual = Double.parseDouble(dAmount) * (conditionInfo.getDiscountRate() / 360) * 0.01 * days;//贴现利息
		dAccrual = DataFormat.formatDouble(dAccrual, 2);//贴现利息
		dPurchaserInterest = dAccrual * conditionInfo.getPurchaserInterestRate() * 0.01;//买方付息
		dPurchaserInterest = DataFormat.formatDouble(dPurchaserInterest, 2);//买方付息
		dRealAmount = Double.parseDouble(dAmount) - dAccrual + dPurchaserInterest;// 实付贴现金额
    	
		try
		{
			con = Database.getConnection();
			sql="update LOAN_DISCOUNTCONTRACTBILL set  MCHECKAMOUNT ="+dRealAmount+" where id ="+Id;
			ps = con.prepareStatement(sql);
			//rs=ps.executeQuery();
			flag=ps.executeUpdate();
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		
		return flag;
		
	}
	
	public long updatebillpayNoticeID(long payNoticeID,String sid) throws Exception {
		int flag =0;
		Connection con = null;
		//ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			con = Database.getConnection();
			sql="update LOAN_DISCOUNTCONTRACTBILL set  ndiscountcredenceid ="+payNoticeID+" where id ="+sid;
			ps = con.prepareStatement(sql);
			//rs=ps.executeQuery();
			flag=ps.executeUpdate();
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		
		return flag;
		
	}
	
	
	public int getBillAmount(String code) throws Exception {
		int flag =0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			con = Database.getConnection();
			sql="select ID from LOAN_DISCOUNTCONTRACTBILL where NSTATUSID=1 and scode ='"+code+"'";
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				flag++;
			}
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		
		return flag;
		
	}
	
	public Map getBillInfo(String ID) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		Map map = new HashMap();
		try
		{
			con = Database.getConnection();
			sql="select ID,NCONTRACTID,NSERIALNO,SUSERNAME,SBANK,NISLOCAL,to_char(DTCREATE,'YYYY-MM-DD') DTCREATE,to_char(DTEND,'YYYY-MM-DD') DTEND,SCODE,MAMOUNT,NSTATUSID,NADDDAYS,SFORMEROWNER,NACCEPTPOTYPEID from LOAN_DISCOUNTCONTRACTBILL where NSTATUSID=1 and id ="+ID;
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				map.put("ID", rs.getString("ID"));
				map.put("NCONTRACTID", rs.getString("NCONTRACTID"));
				map.put("NSERIALNO", rs.getString("NSERIALNO"));
				map.put("SUSERNAME", rs.getString("SUSERNAME"));
				map.put("SBANK", rs.getString("SBANK"));
				map.put("NISLOCAL", rs.getString("NISLOCAL"));
				map.put("DTCREATE", rs.getString("DTCREATE"));
				map.put("DTEND", rs.getString("DTEND"));
				map.put("SCODE", rs.getString("SCODE"));
				map.put("MAMOUNT", rs.getString("MAMOUNT"));
				map.put("NADDDAYS", rs.getString("NADDDAYS"));
				map.put("SFORMEROWNER", rs.getString("SFORMEROWNER"));
				map.put("NACCEPTPOTYPEID", rs.getString("NACCEPTPOTYPEID"));
			}
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		}
		
		return map;
	}
	
	
	public long updateBillInfo(String ID,String userName,String addDays,String bank,String isLocal,String formerOwner,String acceptPOTypeID,String create,String end,String code,String amount) throws Exception {
		int flag =0;
		Connection con = null;
		//ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			con = Database.getConnection();
			sql="update LOAN_DISCOUNTCONTRACTBILL set  suserName ='"+userName+"',NADDDAYS="+addDays+",sbank='"+bank+"',nisLocal="+isLocal+",sformerOwner='"+formerOwner+"',nacceptPOTypeID="+acceptPOTypeID+",dtcreate=to_date('"+create+"','yyyy-mm-dd'),dtend=to_date('"+end+"','yyyy-mm-dd'),scode='"+code+"',mamount="+amount+" where id ="+ID;
			ps = con.prepareStatement(sql);
			flag=ps.executeUpdate();
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(ps!=null){
			ps.close();
			}
			if(con!=null){
			con.close();
			}
		}
		return flag;
	}
	
	
	
	public static void main(String[] args)
	{
		SettDiscountBillDAO dao = new SettDiscountBillDAO();
		dao.findDiscountBillByCredenceID(1778);
	}
	
}
