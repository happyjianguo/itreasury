package com.iss.itreasury.securities.notice.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.notice.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.bizlogic.*;
import com.iss.itreasury.system.dataentity.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.securities.deliveryorder.dao.*;
import com.iss.itreasury.securities.deliveryorder.dataentity.*;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SEC_NoticeWithSecuritiesDAO extends SecuritiesDAO {
	
	public static void main(String[] args){
		SEC_NoticeDAO dao = new SEC_NoticeDAO();
		NoticeInfo info = new NoticeInfo();
		info.setCode("yangfan");
		info.setDeliveryOrderId(25);
//		try 
//		{
//			//dao.add(info);
//			dao.updataDeliveyOrderStatus(222,6);
//		}
//		catch (SecuritiesDAOException e1)
//		{
//			e1.printStackTrace();
//		}
	}

	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SEC_NoticeWithSecuritiesDAO(){
		super("sec_noticewithsecurities");
	}
	
	public Collection findByNoticeID(long lNoticeID) throws SecuritiesDAOException
	{
		System.out.println("heredao");
		String strSQL = "";
		Vector v = new Vector ();
        		
		try  
		{
			initDAO();
		} catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
	    		
	    		
	    		
		strSQL = " select * from sec_noticewithsecurities aa "
			+ " where 1=1 "
			+ " and StatusID = " + Constant.RecordStatus.VALID 
			+ " and noticeID = " + lNoticeID
			+ " order by securitiesId ";
      
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
				NoticeWithSecuritiesInfo info = new NoticeWithSecuritiesInfo ();
				info.setId(rs.getLong("id"));
				info.setNoticeId(rs.getLong("noticeId"));
				info.setSecuritiesId(rs.getLong("securitiesId"));
				info.setQuantity(rs.getLong("quantity"));
				info.setPrice(rs.getDouble("price"));
				info.setStatusId(rs.getLong("statusId"));
				info.setRateName(rs.getString("rateName"));
				info.setSettlementTypeId(rs.getLong("settlementTypeId"));
				info.setSumAmount(rs.getDouble("sumAmount"));
				info.setRelatedId(rs.getLong("relatedid"));
				info.setFaceSumAmount(rs.getDouble("faceSumAmount"));
				
				v.add (info);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询业务信息的证券代码产生错误",e);			
		} catch (SQLException e) {
			throw new SecuritiesDAOException("查询业务信息的证券代码产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (v.size () > 0 ? v : null);
		
	}
	
	
	/**
	 * 通过合同id查询合同执行情况(债券的收取和销售情况)
	 * @param lContractID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection findByContractID(long lContractID) throws SecuritiesDAOException
	{
		System.out.println("heredao");
		String strSQL = "";
		Vector v = new Vector ();
        		
		try  
		{
			initDAO();
		} catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
	    		
	    		
	    		
		strSQL = " select a.id,a.securitiesid,a.ratename,a.facesumamount/100 receivedNumber,facesumamount receivedAmount,"
      			+" d.saledNumber,d.saledAmount "
				+" from sec_noticeWithSecurities a,"
				+" sec_noticeform b,"
				+" sec_applycontract c,"
				+" (select sum(faceSumAmount/100) saledNumber,sum(faceSumAmount) saledAmount,relatedId "
				+" from sec_noticewithsecurities aa,sec_noticeform bb"
				+" where aa.noticeid = bb.id"
				+" and aa.statusid = " + Constant.RecordStatus.VALID
				+" and bb.statusid in ( " + SECConstant.NoticeFormStatus.CHECKED + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + ")"
				+" group by relatedId)d "
				+" where " 
				+" a.noticeid = b.id " 
				+" and a.statusid = " + Constant.RecordStatus.VALID
				+" and b.contractid = c.id " 
				+" and c.id = " + lContractID
				+" and b.statusid in ( " + SECConstant.NoticeFormStatus.CHECKED + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + ")"
				+" and b.transactiontypeid = " + SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY
				+" and a.id = d.relatedId(+) ";
      
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
				ContractWithSecuritiesInfo info = new ContractWithSecuritiesInfo ();
				info.setId(rs.getLong("id"));
				info.setSecuritiesId(rs.getLong("securitiesId"));
				info.setRateName(rs.getString("rateName"));
				info.setReceiveQuantity(rs.getDouble("receivedNumber"));
				info.setReceiveAmount(rs.getDouble("receivedAmount"));
				info.setSaleQuantity(rs.getDouble("saledNumber"));
				info.setSaleAmount(rs.getDouble("saledAmount"));
				info.setBalanceQuantity(info.getReceiveQuantity()-info.getSaleQuantity());
				info.setBalanceAmount(info.getReceiveAmount()-info.getSaleAmount());
				v.add (info);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询合同执行情况的证券代码产生错误",e);			
		} catch (SQLException e) {
			throw new SecuritiesDAOException("查询合同执行情况的证券代码产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (v.size () > 0 ? v : null);
		
	}
	
	/**
	 * 通过交易类型获得该通知单下所有证券的交易总额
	 * @param lNoticeID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public double getSecAmountByNoticeId(long lNoticeID)  throws SecuritiesDAOException
	{
		double secAmount = 0;
		String strSQL = "";
		
		try  
		{
			initDAO();
		} catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		String temp = "quantity";
		SEC_NoticeDAO nDao = new SEC_NoticeDAO();
		NoticeInfo nInfo = new NoticeInfo();
		try
		{
			nInfo = (NoticeInfo)nDao.findByID(lNoticeID,nInfo.getClass());
		}
		catch (ITreasuryDAOException e1)
		{
			e1.printStackTrace();
		}
		//如果是债券承销收款,则数量是用票面小计除以面值
		if(nInfo.getTransactionTypeId()==SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY)
		{
			temp = "faceSumAmount/100";
		}
		strSQL = " select sum("+temp+" * price) secAmount "
		       + " from sec_noticewithsecurities where 1=1 "
			   + " and StatusID = " + Constant.RecordStatus.VALID 
			   + " and noticeID = " + lNoticeID;
			   
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
				secAmount = rs.getDouble("secAmount");
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询业务信息的交易总额产生错误",e);			
		} catch (SQLException e) {
			throw new SecuritiesDAOException("查询业务信息的交易总额产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}

		return secAmount;
	}
	
	/**
	 * 通过交易类型获得该通知单下所有证券的交易总额(债券承销)
	 * @param lNoticeID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public double getSecAmountByNoticeIdForBOND_UNDERWRITING(long lNoticeID)  throws SecuritiesDAOException
	{
		double secAmount = 0;
		String strSQL = "";
		
		try  
		{
			initDAO();
		} catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		strSQL = " select sum(faceSumAmount) secAmount "
			   + " from sec_noticewithsecurities where 1=1 "
			   + " and StatusID = " + Constant.RecordStatus.VALID 
			   + " and noticeID = " + lNoticeID;
			   
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
				secAmount = rs.getDouble("secAmount");
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询业务信息的交易总额产生错误",e);			
		} catch (SQLException e) {
			throw new SecuritiesDAOException("查询业务信息的交易总额产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}

		return secAmount;
	}
	
}