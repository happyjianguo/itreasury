/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontract.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.securitiesgeneralledger.bizlogic.SecuritiesGeneralLedgerOperation;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.securities.stock.bizlogic.StockOperation;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import java.util.*;

import com.iss.itreasury.loan.attornmentapply.dao.*;

import java.rmi.RemoteException;
import java.sql.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractDao extends SecuritiesDAO{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SecuritiesContractDao(){
		super("SEC_APPLYCONTRACT");
	}
	private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(CallableStatement cs) throws SQLException {
		try {
			if (cs != null) {
				cs.close();
				cs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}

//	public SecuritiesContractDao(){
//		super("SEC_APPLYCONTRACT");
//	}
	
	/**
	 * @param transConn
	 */
	public SecuritiesContractDao(Connection transConn) {
		
		super("SEC_APPLYCONTRACT",transConn);
	}
    
	/**
	 * 合同修改查找和审核查找
	 * cjin 2006-12-20
	 * @param qInfo
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection findByMultiOption1(SecuritiesContractQueryInfo qInfo,long ActionID) throws SecuritiesDAOException 
	{
		String strSQL="";
		System.out.println("================11111===进入==aaa======================");
		/*SUBLOANTYPEID----ACTIONID*/
		long lSubLoanTypeID = -1;
		long lActionID = -1;
		
		Vector v = new Vector();
		long transactionTypeID = qInfo.getTransactionTypeId();
		
		/*证卷操作*/
//		if ( ActionID == 1 )
//		{
//			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //申请
//			lSubLoanTypeID=result[0];
//			lActionID=result[1];
//		}
//		else if ( ActionID == 2 )
//		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //业务
			lSubLoanTypeID=result[0];
			lActionID=result[1];
//		}
		
		long id = -1;
		long startContractId=qInfo.getStartContractId();
		long transactionTypeId= qInfo.getTransactionTypeId();
		long endContractId=qInfo.getEndContractId();
		long counterpartId = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		Timestamp startBeginDate=qInfo.getStartBeginDate();
		Timestamp endBeginDate=qInfo.getEndBeginDate();
		Timestamp startEndDate=qInfo.getStartEndDate();
		Timestamp endEndDate=qInfo.getEndEndDate();
		long statusId=qInfo.getStatusId();
		long intervalNum=qInfo.getIntervalNum();
		long settlementTypeId=qInfo.getSettlementTypeId();
		long interestTypeId=qInfo.getInterestTypeId();
		double commissionChargeRate=qInfo.getCommissionChargeRate();		//手续费率
		double beginBoldScale=qInfo.getBeginBoldScale();			//债券总规模
		double endBoldScale=qInfo.getEndBoldScale();

		Timestamp startInputDate=qInfo.getStartInputDate();
		Timestamp endInputDate=qInfo.getEndInputDate();
		Timestamp startTransactionDate=qInfo.getStartTransactionDate();
		Timestamp endTransactionDate=qInfo.getEndTransactionDate();
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		long userId = qInfo.getUserId();
		long inputUserId = qInfo.getInputUserId();
		long nextCheckUserId = qInfo.getNextCheckUserId();
		long queryPurpose = qInfo.getQueryPurpose();	// 1:for modify 2:for examine

        long currencyId = qInfo.getCurrencyId();
		long clientId=qInfo.getClientId();
		long planStatusId = qInfo.getPlanStatusId();
		
		double startcostAmount = 0;  //回购成本开始
		double endcostAmount = 0;  //回购成本结束
		long startterm = -1;  //开始期限
		long endterm = -1;  //结束期限
	    double startincomeRate = 0;  //开始回购收益率
        double endincomeRate = 0;  //结束回购收益率
		
		String strUser = "";
		long lModuleID = -1;
		long lLoanTypeID = -1;
		long lApprovalID = -1;
		
//		zpli add 2005-09-14
		long lOfficeID=qInfo.getOfficeId();
		long lCurrencyID=qInfo.getCurrencyId();

		startcostAmount = qInfo.getStartcostAmount();  //回购成本开始
		endcostAmount = qInfo.getEndcostAmount();  //回购成本结束
		startincomeRate = qInfo.getStartincomeRate();  //开始回购收益率
		endincomeRate = qInfo.getEndincomeRate();  //结束回购收益率
		startterm = qInfo.getStartterm();  //回购期限开始
		endterm = qInfo.getEndterm();  //回购期限结束
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		
				
		try 
		{			
			strSQL = "SELECT c.*,-1 moneysegment,-1 approvalid from SEC_ApplyContract c"; 
			strSQL+=",(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			strSQL+=" where (a.NNEXTUSERID="+userId+" "; 
			
			//修改
			strSQL+=" and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
			
			strSQL+="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			strSQL+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			strSQL+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.ContractStatus.APPROVALING+"";
			
	
			if ( transactionTypeID > 0 )
			{
				strSQL += " and c.transactiontypeid = " + transactionTypeID ;
			}
			if (clientId != -1) { 
				strSQL += " and c.ClientID = " + clientId ;
			}
			
			if (counterpartId != -1) {
				strSQL += " and c.CounterpartID = " + counterpartId ;                    
			}
        
			if (startAmount != 0) {
				strSQL += " and c.Amount >= " + startAmount ;
			}
        
			if (endAmount != 0) {
				strSQL += " and c.Amount <= " + endAmount ;
			}
			
			if (startContractId !=-1){
				strSQL+=" and c.ID>=" + startContractId;
			}
			
			if (endContractId != -1){
				strSQL+=" and c.ID<=" + endContractId ;
			}
			
			if (intervalNum !=-1 ){
				strSQL+=" and c.term=" + intervalNum;
			}
			
			if (settlementTypeId !=-1 ){
				strSQL+=" and c.settlementTypeID=" + settlementTypeId;
			}
			
			if (interestTypeId !=-1 ){
				strSQL+=" and c.interestTypeID= " + interestTypeId;
			}
			
			if (commissionChargeRate !=0 ){
				strSQL+=" and c.commissionChargeRate=" + commissionChargeRate ;
			}
			
			if (beginBoldScale !=0 ){
				strSQL+=" and c.sumBold>=" + beginBoldScale; 
			}
			
			if (endBoldScale !=0 ){
				strSQL+=" and c.sumBold<=" + endBoldScale;
			}
			
			if (startBeginDate != null) {
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startBeginDate) + "'";
			}
			
			if (endBeginDate != null) {
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endBeginDate) + "'";
			}

			if (startEndDate != null) {
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startEndDate) + "'";
			}
			
			if (endEndDate != null) {
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endEndDate) + "'";
			}
			
			if (startInputDate != null) {
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startInputDate) + "'";
			}
			
			if (endInputDate != null) {
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endInputDate) + "'";
			}
			
			/*
			boxu add 2006-09-19
			double startcostAmount = 0;  //回购成本开始
			double endcostAmount = 0;  //回购成本结束
			long startterm = -1;  //开始期限
			long endterm = -1;  //结束期限
			double startincomeRate = 0;  //开始回购收益率
			double endincomeRate = 0;  //结束回购收益率
			*/
			if (startcostAmount != 0)
			{
				strSQL += " and c.costAmount >= " + startcostAmount;
			}
			if (endcostAmount != 0)
			{
				strSQL += " and c.costAmount <= " + endcostAmount;
			}
			if (startincomeRate > 0)
			{
				strSQL += " and c.incomeRate >= " + startincomeRate;
			}
			if (endincomeRate > 0)
			{
				strSQL += " and c.incomeRate <= " + endincomeRate;
			}
			if (startterm > 0)
			{
				strSQL += " and c.term >= " + startterm;
			}
			if (endterm > 0)
			{
				strSQL += " and c.term <= " + endterm;
			}
			
			strSQL+=" union ( ";
			    
			 strSQL += " select d.* from (";
			 strSQL += " select aaa.* from (";
			 strSQL += " select aa.*,rr.moneysegment,rr.approvalid from SEC_ApplyContract aa,loan_approvalrelation rr";
				//增加关于币种的判断-mhjin-东方电气
			 strSQL += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ContractStatus.SUBMIT;
				
				//lSubLoanTypeID 和 lActionID
			 strSQL += " and rr.actionid = " + lActionID ;
			 strSQL += " and rr.subloantypeid = " + lSubLoanTypeID ;
			
			 if ( transactionTypeID > 0 )
				{
					strSQL += " and aa.transactiontypeid = " + transactionTypeID ;
				}
				if (clientId != -1) { 
					strSQL += " and c.ClientID = " + clientId ;
				}
				
				if (counterpartId != -1) {
					strSQL += " and aa.CounterpartID = " + counterpartId ;                    
				}
	        
				if (startAmount != 0) {
					strSQL += " and aa.Amount >= " + startAmount ;
				}
	        
				if (endAmount != 0) {
					strSQL += " and aa.Amount <= " + endAmount ;
				}
				
				if (startContractId !=-1){
					strSQL+=" and aa.ID>=" + startContractId;
				}
				
				if (endContractId != -1){
					strSQL+=" and aa.ID<=" + endContractId ;
				}
				
				if (intervalNum !=-1 ){
					strSQL+=" and c.term=" + intervalNum;
				}
				
				if (settlementTypeId !=-1 ){
					strSQL+=" and c.settlementTypeID=" + settlementTypeId;
				}
				
				if (interestTypeId !=-1 ){
					strSQL+=" and c.interestTypeID= " + interestTypeId;
				}
				
				if (commissionChargeRate !=0 ){
					strSQL+=" and c.commissionChargeRate=" + commissionChargeRate ;
				}
				
				if (beginBoldScale !=0 ){
					strSQL+=" and c.sumBold>=" + beginBoldScale; 
				}
				
				if (endBoldScale !=0 ){
					strSQL+=" and c.sumBold<=" + endBoldScale;
				}
				
				if (startBeginDate != null) {
					strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startBeginDate) + "'";
				}
				
				if (endBeginDate != null) {
					strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endBeginDate) + "'";
				}

				if (startEndDate != null) {
					strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startEndDate) + "'";
				}
				
				if (endEndDate != null) {
					strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endEndDate) + "'";
				}
				
				if (startInputDate != null) {
					strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startInputDate) + "'";
				}
				
				if (endInputDate != null) {
					strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endInputDate) + "'";
				}
				
				/*
				boxu add 2006-09-19
				double startcostAmount = 0;  //回购成本开始
				double endcostAmount = 0;  //回购成本结束
				long startterm = -1;  //开始期限
				long endterm = -1;  //结束期限
				double startincomeRate = 0;  //开始回购收益率
				double endincomeRate = 0;  //结束回购收益率
				*/
				if (startcostAmount != 0)
				{
					strSQL += " and aa.costAmount >= " + startcostAmount;
				}
				if (endcostAmount != 0)
				{
					strSQL += " and aa.costAmount <= " + endcostAmount;
				}
				if (startincomeRate > 0)
				{
					strSQL += " and aa.incomeRate >= " + startincomeRate;
				}
				if (endincomeRate > 0)
				{
					strSQL += " and aa.incomeRate <= " + endincomeRate;
				}
				if (startterm > 0)
				{
					strSQL += " and aa.term >= " + startterm;
				}
				if (endterm > 0)
				{
					strSQL += " and aa.term <= " + endterm;
				}
			 
				strSQL += " ) aaa,(";
				strSQL += " select aa.id,max(rr.moneysegment) maxamount from SEC_ApplyContract aa,loan_approvalrelation rr";
				//增加关于币种的判断-mhjin-东方电气
				strSQL += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.amount>rr.moneysegment and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ContractStatus.SUBMIT;
				
				//lSubLoanTypeID 和 lActionID
				strSQL += " and rr.actionid = " + lActionID ;
				strSQL += " and rr.subloantypeid = " + lSubLoanTypeID ;
				
				if ( transactionTypeID > 0 )
				{
					strSQL += " and aa.transactiontypeid = " + transactionTypeID ;
				}
				if (clientId != -1) { 
					strSQL += " and c.ClientID = " + clientId ;
				}
				
				if (counterpartId != -1) {
					strSQL += " and aa.CounterpartID = " + counterpartId ;                    
				}
	        
				if (startAmount != 0) {
					strSQL += " and aa.Amount >= " + startAmount ;
				}
	        
				if (endAmount != 0) {
					strSQL += " and aa.Amount <= " + endAmount ;
				}
				
				if (startContractId !=-1){
					strSQL+=" and aa.ID>=" + startContractId;
				}
				
				if (endContractId != -1){
					strSQL+=" and aa.ID<=" + endContractId ;
				}
				
				if (intervalNum !=-1 ){
					strSQL+=" and c.term=" + intervalNum;
				}
				
				if (settlementTypeId !=-1 ){
					strSQL+=" and c.settlementTypeID=" + settlementTypeId;
				}
				
				if (interestTypeId !=-1 ){
					strSQL+=" and c.interestTypeID= " + interestTypeId;
				}
				
				if (commissionChargeRate !=0 ){
					strSQL+=" and c.commissionChargeRate=" + commissionChargeRate ;
				}
				
				if (beginBoldScale !=0 ){
					strSQL+=" and c.sumBold>=" + beginBoldScale; 
				}
				
				if (endBoldScale !=0 ){
					strSQL+=" and c.sumBold<=" + endBoldScale;
				}
				
				if (startBeginDate != null) {
					strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startBeginDate) + "'";
				}
				
				if (endBeginDate != null) {
					strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endBeginDate) + "'";
				}

				if (startEndDate != null) {
					strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startEndDate) + "'";
				}
				
				if (endEndDate != null) {
					strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endEndDate) + "'";
				}
				
				if (startInputDate != null) {
					strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startInputDate) + "'";
				}
				
				if (endInputDate != null) {
					strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endInputDate) + "'";
				}
				/*
				boxu add 2006-09-19
				double startcostAmount = 0;  //回购成本开始
				double endcostAmount = 0;  //回购成本结束
				long startterm = -1;  //开始期限
				long endterm = -1;  //结束期限
				double startincomeRate = 0;  //开始回购收益率
				double endincomeRate = 0;  //结束回购收益率
				*/
				if (startcostAmount != 0)
				{
					strSQL += " and aa.costAmount >= " + startcostAmount;
				}
				if (endcostAmount != 0)
				{
					strSQL += " and aa.costAmount <= " + endcostAmount;
				}
				if (startincomeRate > 0)
				{
					strSQL += " and aa.incomeRate >= " + startincomeRate;
				}
				if (endincomeRate > 0)
				{
					strSQL += " and aa.incomeRate <= " + endincomeRate;
				}
				if (startterm > 0)
				{
					strSQL += " and aa.term >= " + startterm;
				}
				if (endterm > 0)
				{
					strSQL += " and aa.term <= " + endterm;
				}
				
				strSQL += " group by aa.id ) bbb";
				strSQL += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
				strSQL += " loan_approvalsetting e,loan_approvalitem f";
				strSQL += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
				strSQL +=")";
				System.out.println("查询语句SQL^^^^^^^^^^^===="+strSQL);
				
				prepareStatement(strSQL);
				ResultSet rs1 = executeQuery();
				long rep = -1;
				while (rs1 != null && rs1.next())
				{
					System.out.println("1111111111111111111111111111");
					rep = rs1.getLong("id");
					SecuritiesContractInfo sInfo = new SecuritiesContractInfo();
					sInfo.setId(rep);
					sInfo.setClientId(rs1.getLong("clientId"));
					sInfo.setCounterpartId(rs1.getLong("counterpartId"));
					sInfo.setCounterpartBankId(rs1.getLong("counterpartBankId"));
					sInfo.setAccountId(rs1.getLong("accountId"));
					sInfo.setActiveDate(rs1.getTimestamp("activeDate"));
					System.out.println("222222222222222222");
					sInfo.setAmount(rs1.getDouble("amount"));
					sInfo.setApplyId(rs1.getLong("applyId"));					
					sInfo.setCode(rs1.getString("code"));					
					sInfo.setCurrencyId(rs1.getLong("currencyId"));
					
					sInfo.setInputDate(rs1.getTimestamp("inputDate"));
					sInfo.setInputUserId(rs1.getLong("inputUserId"));
					sInfo.setInterestTypeId(rs1.getLong("interestTypeId"));
					sInfo.setMaturitySource(rs1.getString("maturitySource"));
					
					System.out.println("3333333333333333");
					sInfo.setNeedInform(rs1.getLong("needInform"));
					//sInfo.setNextCheckUserId(rs1.getLong("nextCheckUserName"));
					sInfo.setOfficeId(rs1.getLong("officeId"));
					sInfo.setRate(rs1.getDouble("rate"));
					sInfo.setRemark(rs1.getString("remark"));
					sInfo.setSettlementTypeId(rs1.getLong("settlementTypeId"));
					
					sInfo.setStartRate(rs1.getDouble("startRate"));
					sInfo.setStatusId(rs1.getLong("statusId"));
					sInfo.setTerm(rs1.getLong("term"));
					sInfo.setTermTypeId(rs1.getLong("termTypeId"));
					System.out.println("444444444444444444444444");
					
					//sInfo.setTimeStamp(rs1.getTimestamp("timeStamp"));
					sInfo.setTransactionDate(rs1.getTimestamp("TransactionDate"));
					sInfo.setTransactionEndDate(rs1.getTimestamp("TransactionEndDate"));
					sInfo.setTransactionStartDate(rs1.getTimestamp("transactionStartDate"));
					sInfo.setTransactionTypeId(rs1.getLong("TransactionTypeId"));
					sInfo.setUpdateDate(rs1.getTimestamp("updateDate"));
					System.out.println("555555555555555555555");
					sInfo.setUpdateUserId(rs1.getLong("updateUserId"));
					//sInfo.setClientName(NameRef.getClientNameByID(rs1.getLong("clientId"))); 
					sInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId")));
					System.out.println("aaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
					//sInfo.setInputUserName(rs1.getString("inputUserName"));
					//sInfo.setNextCheckUserName(rs1.getString("nextCheckUserName"));
					sInfo.setCommissionChargeRate(rs1.getDouble("commissionChargeRate"));
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
					sInfo.setChangeRate(rs1.getDouble("changeRate"));
					//sInfo.setCounterpartCode(rs1.getString("counterpartCode"));
					//sInfo.setLastCheckUserId(rs1.getLong("lastCheckUserId"));
					//sInfo.setLastCheckUserName(rs1.getString("lastCheckUserName"));
					
					//sInfo.setUserCheckLevel(rs1.getLong("userCheckLevel"));
					//sInfo.setBalance(rs1.getDouble("balance"));
					//sInfo.setBuyBackAmount(rs1.getDouble("buyBackAmount"));
					System.out.println("aaaaaaaccccccccccccccccccccccccccccccccccccccccccc");
					//sInfo.setReceivedAmount(rs1.getDouble("receivedAmount"));
					sInfo.setInterestUpdateDate(rs1.getTimestamp("interestUpdateDate"));
					sInfo.setLastEndInterestDate(rs1.getTimestamp("lastEndInterestDate"));
					sInfo.setInterestBalance(rs1.getDouble("interestBalance"));
					sInfo.setInterestBeginDate(rs1.getTimestamp("interestBeginDate"));
					//sInfo.setBoldScale(rs1.getDouble("boldScale"));
					sInfo.setBondName(rs1.getString("bondName"));
					System.out.println("66666666666666666666");
					
					//sInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel"));
					sInfo.setStockHolderID(rs1.getLong("stockHolderID"));
					//sInfo.setPlanModifyDate(rs1.getTimestamp("planModifyDate"));
					//sInfo.setPlanDetailCount(rs1.getLong("planDetailCount"));
					sInfo.setContractInterest(rs1.getDouble("contractInterest"));
					System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
					sInfo.setContractInterestDate(rs1.getTimestamp("contractInterestDate"));
					sInfo.setAccountIDArr(rs1.getString("AccountIDArr"));
					sInfo.setCounterpartBankIDArr(rs1.getString("counterpartBankIDArr"));
					sInfo.setStockHolderIDArr(rs1.getString("stockHolderIDArr"));
					sInfo.setCostAmount(rs1.getDouble("costAmount"));
					System.out.println("77777777777777777");
					sInfo.setIncomeRate(rs1.getDouble("incomeRate"));
					System.out.println("8888888888888888888888888888888888888888");
					v.add(sInfo);
				}
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同时产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
		return (v.size () > 0 ? v : null);
	}	
	
	
	/**
	 * 修改查找和审核查找
	 * @param qInfo
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection findByMultiOption2(SecuritiesContractQueryInfo qInfo) throws SecuritiesDAOException 
	{
		System.out.println("=================进入修改查找方法==========================");
		Collection c=null;
		String strSQL="";
		ApprovalBiz appBiz = new ApprovalBiz();
		String strUser = "";
		long lModuleID = -1;
		long lLoanTypeID = -1;
		long lActionID = -1;
		long lApprovalID = -1;

		long queryPurpose=qInfo.getQueryPurpose();
		
//		zpli add 2005-09-14
		long lOfficeID=qInfo.getOfficeId();
		long lCurrencyID=qInfo.getCurrencyId();
		////
		
		/*boxu add 2006-09-19*/
		double startcostAmount = 0;  //回购成本开始
		double endcostAmount = 0;  //回购成本结束
		long startterm = -1;  //开始期限
		long endterm = -1;  //结束期限
		double startincomeRate = 0;  //开始回购收益率
		double endincomeRate = 0;  //结束回购收益率
		startcostAmount = qInfo.getStartcostAmount();  //回购成本开始
		endcostAmount = qInfo.getEndcostAmount();  //回购成本结束
		startincomeRate = qInfo.getStartincomeRate();  //开始回购收益率
		endincomeRate = qInfo.getEndincomeRate();  //结束回购收益率
		startterm = qInfo.getStartterm();  //回购期限开始
		endterm = qInfo.getEndterm();  //回购期限结束
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		

		try {
			//获得ApprovalID
			if (lApprovalID <= 0)
			{
				//zpli modify 2005-09-14
				lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(),3,lOfficeID,lCurrencyID);
				//lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(),1);
			}
		} catch (Exception e1) 
		{
			log4j.error("getApprovalID fail");
			e1.printStackTrace();
		}
		try
		{
			long[] param=NameRef.getLoantypidAndActionidByTransactionid(3,qInfo.getTransactionTypeId());
			lLoanTypeID=param[0];
			lActionID=param[1];
			//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
			
			//zpli modify 2005-09-20
			strUser = appBiz.findTheVeryUser(Constant.ModuleType.CRAFTBROTHER,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,qInfo.getUserId());
			//strUser = appBiz.findTheVeryUser(lApprovalID,qInfo.getUserId());
		
		} catch (Exception e2)
		{
			log4j.error("findTheVeryUser fail");
			e2.printStackTrace();
		}
				
		try 
		{
			strSQL = "select aa.*,bb.boldScale from SEC_ApplyContract aa, \n" 
				+" (\n"
				+"  	 select contractFormID,sum(amount) as boldScale \n"
				+"			from SEC_ContractFormBondType \n"
				+"			group by contractFormID \n"				
				+" ) bb \n"
				+" where 1=1 \n"
				+" and bb.contractFormID(+)=aa.ID \n";
				
			if (queryPurpose==1)				//for modify
			{
				strSQL+= " and ( (StatusID = " + SECConstant.ContractStatus.CHECK
					+ " and InputUserID = " + qInfo.getUserId() 
					+ " ) or (StatusID = " + SECConstant.ContractStatus.SAVE
					+ " and InputUserID = " + qInfo.getUserId()
					+ " ) or (statusID="+SECConstant.ContractStatus.SUBMIT+" "
					+ " and InputUserID = " + qInfo.getUserId()
					+ " and NextCheckUserID = " + qInfo.getUserId() +" ) )";
								
				if ( qInfo.getTransactionTypeId() > 0 && qInfo.getTransactionTypeId() < 1000 )
				{
					String tmpSQL = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedApplyForm = 1 and BusinessTypeID = " + qInfo.getTransactionTypeId();
					strSQL += " and transactionTypeID in (" + NameRef.getNamesByID(tmpSQL,"ID") + ") ";				
				}
				else if ( qInfo.getTransactionTypeId() > 1000 )
				{
					strSQL += " and transactionTypeID = " + qInfo.getTransactionTypeId();	
				}
				if ( qInfo.getStatusId() == SECConstant.ContractStatus.SUBMIT )
				{
					strSQL += " and NextCheckUserID = " + qInfo.getUserId();
					strSQL += " and StatusID = " + SECConstant.ContractStatus.SUBMIT;				
				}
			}
			else if (queryPurpose==2)		//for check
			{
				strSQL+= " and NextCheckUserID in " + strUser + " and TransactionTypeID = " + qInfo.getTransactionTypeId() 
					+ " and StatusID = " + SECConstant.ContractStatus.SUBMIT;
			}
			else if (queryPurpose==3)		//for active
			{
				strSQL+=" and statusID="+SECConstant.ContractStatus.CHECK
					+" and inputUserID="+qInfo.getUserId();

				if ( qInfo.getTransactionTypeId() > 0 && qInfo.getTransactionTypeId() < 1000 )
				{
					String tmpSQL = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedApplyForm = 1 and BusinessTypeID = " + qInfo.getTransactionTypeId();
					strSQL += " and transactionTypeID in (" + NameRef.getNamesByID(tmpSQL,"ID") + ") ";				
				}
				else if ( qInfo.getTransactionTypeId() > 1000 )
				{
					strSQL += " and transactionTypeID = " + qInfo.getTransactionTypeId();	
				}
			}
			else if (queryPurpose==4)		//for end contract
			{
				strSQL += " and statusID="+SECConstant.ContractStatus.ACTIVE
					+" and inputUserID=" + qInfo.getUserId() 
					+" and transactionTypeID="+SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING;
					
			}
			else if (queryPurpose==5)		//合同权限转移-合同查找
			{
				strSQL += " and statusID !=" + SECConstant.ContractStatus.CANCEL;
				strSQL += " and statusID !=" + SECConstant.ContractStatus.REFUSE;
				
				if (qInfo.getTransactionTypeId() > 0)
				{
					strSQL += " and transactionTypeID=" + qInfo.getTransactionTypeId();
				}
				if (qInfo.getInputUserId() > 0)
				{
					strSQL += " and inputUserID=" + qInfo.getInputUserId(); 
				}
			}
			
			if (qInfo.getClientId() != -1) {
				strSQL += " and ClientID = " + qInfo.getClientId() ;
			}
        
			if (qInfo.getCounterpartId() != -1) {
				strSQL += " and CounterpartID = " + qInfo.getCounterpartId() ;                    
			}
        
			if (qInfo.getStartAmount() != 0) {
				strSQL += " and Amount >= " + qInfo.getStartAmount() ;
			}
        
			if (qInfo.getEndAmount() != 0) {
				strSQL += " and Amount <= " + qInfo.getEndAmount() ;
			}
			
			if (qInfo.getStartContractId()!=-1){
				strSQL+=" and ID>=" + qInfo.getStartContractId();
			}
			
			if (qInfo.getEndContractId() != -1){
				strSQL+=" and ID<=" + qInfo.getEndContractId() ;
			}
			
			if (qInfo.getIntervalNum() !=-1 ){
				strSQL+=" and term=" + qInfo.getIntervalNum();
			}
			
			if (qInfo.getSettlementTypeId() !=-1 ){
				strSQL+=" and settlementTypeID=" + qInfo.getSettlementTypeId();
			}
			
			if (qInfo.getInterestTypeId()!=-1 ){
				strSQL+=" and interestTypeID= " + qInfo.getInterestTypeId();
			}
			
			if (qInfo.getCommissionChargeRate() !=0 ){
				strSQL+=" and commissionChargeRate=" + qInfo.getCommissionChargeRate() ;
			}
			
			if (qInfo.getBeginBoldScale() !=0 ){
				strSQL+=" and sumBold>=" + qInfo.getBeginBoldScale(); 
			}
			
			if (qInfo.getEndBoldScale()!=0 ){
				strSQL+=" and sumBold<=" + qInfo.getEndBoldScale();
			}
			
			if (qInfo.getStartBeginDate() != null) {
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartBeginDate()) + "'";
			}
			
			if (qInfo.getEndBeginDate() != null) {
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndBeginDate()) + "'";
			}

			if (qInfo.getStartEndDate() != null) {
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartEndDate()) + "'";
			}
			
			if (qInfo.getEndEndDate() != null) {
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndEndDate()) + "'";
			}
			
			if (qInfo.getStartInputDate() != null) {
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartInputDate()) + "'";
			}
			
			if (qInfo.getEndInputDate() != null) {
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndInputDate()) + "'";
			}
			
			if (qInfo.getStatusId()>0)
			{
				strSQL += " and statusID="+qInfo.getStatusId();
			}
			
			/*
			boxu add 2006-09-19
			double startcostAmount = 0;  //回购成本开始
			double endcostAmount = 0;  //回购成本结束
			long startterm = -1;  //开始期限
			long endterm = -1;  //结束期限
			double startincomeRate = 0;  //开始回购收益率
			double endincomeRate = 0;  //结束回购收益率
			*/
			if (startcostAmount != 0)
			{
				strSQL += " and costAmount >= " + startcostAmount;
			}
			if (endcostAmount != 0)
			{
				strSQL += " and costAmount <= " + endcostAmount;
			}
			if (startincomeRate > 0)
			{
				strSQL += " and incomeRate >= " + startincomeRate;
			}
			if (endincomeRate > 0)
			{
				strSQL += " and incomeRate <= " + endincomeRate;
			}
			if (startterm > 0)
			{
				strSQL += " and term >= " + startterm;
			}
			if (endterm > 0)
			{
				strSQL += " and term <= " + endterm;
			}
			
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			int nIndex = 0;
			String orderParamString=qInfo.getOrderParamString() ;
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0)
			{
				if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_applycontract"))
				{
					strSQL += " order by aa." + orderParamString.substring(nIndex+1);
				}
			}
			else
			{
				strSQL += " order by aa.ID";
			}
			
			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
				strSQL += " desc";
			}
			
			System.out.println("xxxxxxxxxbbbbbbbbbbbb"+strSQL);
			
			prepareStatement(strSQL);
			executeQuery();
			c=getDataEntitiesFromResultSet( SecuritiesContractInfo.class );
	
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同时产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
		return (c.size () > 0 ? c : null);
	}
	
	
	/**
	 * 修改查找和审核查找
	 * @param qInfo
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws SecuritiesDAOException 
	{
		System.out.println("=================进入修改查找方法==========================");
		Collection c=null;
		String strSQL="";
		ApprovalBiz appBiz = new ApprovalBiz();
		String strUser = "";
		long lModuleID = -1;
		long lLoanTypeID = -1;
		long lActionID = -1;
		long lApprovalID = -1;

		long queryPurpose=qInfo.getQueryPurpose();
		
//		zpli add 2005-09-14
		long lOfficeID=qInfo.getOfficeId();
		long lCurrencyID=qInfo.getCurrencyId();
		////
		
		/*boxu add 2006-09-19*/
		double startcostAmount = 0;  //回购成本开始
		double endcostAmount = 0;  //回购成本结束
		long startterm = -1;  //开始期限
		long endterm = -1;  //结束期限
		double startincomeRate = 0;  //开始回购收益率
		double endincomeRate = 0;  //结束回购收益率
		startcostAmount = qInfo.getStartcostAmount();  //回购成本开始
		endcostAmount = qInfo.getEndcostAmount();  //回购成本结束
		startincomeRate = qInfo.getStartincomeRate();  //开始回购收益率
		endincomeRate = qInfo.getEndincomeRate();  //结束回购收益率
		startterm = qInfo.getStartterm();  //回购期限开始
		endterm = qInfo.getEndterm();  //回购期限结束
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		

		try {
			//获得ApprovalID
			if (lApprovalID <= 0)
			{
				//zpli modify 2005-09-14
				lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(),1,lOfficeID,lCurrencyID);
				//lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(),1);
			}
		} catch (Exception e1) 
		{
			log4j.error("getApprovalID fail");
			e1.printStackTrace();
		}
		try
		{
			long[] param=NameRef.getLoantypidAndActionidByTransactionid(1,qInfo.getTransactionTypeId());
			lLoanTypeID=param[0];
			lActionID=param[1];
			//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
			
			//zpli modify 2005-09-20
			strUser = appBiz.findTheVeryUser(Constant.ModuleType.SECURITIES,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,qInfo.getUserId());
			//strUser = appBiz.findTheVeryUser(lApprovalID,qInfo.getUserId());
		
		} catch (Exception e2)
		{
			log4j.error("findTheVeryUser fail");
			e2.printStackTrace();
		}
				
		try 
		{
			strSQL = "select aa.*,bb.boldScale from SEC_ApplyContract aa, \n" 
				+" (\n"
				+"  	 select contractFormID,sum(amount) as boldScale \n"
				+"			from SEC_ContractFormBondType \n"
				+"			group by contractFormID \n"				
				+" ) bb \n"
				+" where 1=1 \n"
				+" and bb.contractFormID(+)=aa.ID \n";
				
			if (queryPurpose==1)				//for modify
			{
				strSQL+= " and ( (StatusID = " + SECConstant.ContractStatus.CHECK
					+ " and InputUserID = " + qInfo.getUserId() 
					+ " ) or (StatusID = " + SECConstant.ContractStatus.SAVE
					+ " and InputUserID = " + qInfo.getUserId()
					+ " ) or (statusID="+SECConstant.ContractStatus.SUBMIT+" "
					+ " and InputUserID = " + qInfo.getUserId()
					+ " and NextCheckUserID = " + qInfo.getUserId() +" ) )";
								
				if ( qInfo.getTransactionTypeId() > 0 && qInfo.getTransactionTypeId() < 1000 )
				{
					String tmpSQL = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedApplyForm = 1 and BusinessTypeID = " + qInfo.getTransactionTypeId();
					strSQL += " and transactionTypeID in (" + NameRef.getNamesByID(tmpSQL,"ID") + ") ";				
				}
				else if ( qInfo.getTransactionTypeId() > 1000 )
				{
					strSQL += " and transactionTypeID = " + qInfo.getTransactionTypeId();	
				}
				if ( qInfo.getStatusId() == SECConstant.ContractStatus.SUBMIT )
				{
					strSQL += " and NextCheckUserID = " + qInfo.getUserId();
					strSQL += " and StatusID = " + SECConstant.ContractStatus.SUBMIT;				
				}
			}
			else if (queryPurpose==2)		//for check
			{
				strSQL+= " and NextCheckUserID in " + strUser + " and TransactionTypeID = " + qInfo.getTransactionTypeId() 
					+ " and StatusID = " + SECConstant.ContractStatus.SUBMIT;
			}
			else if (queryPurpose==3)		//for active
			{
				strSQL+=" and statusID="+SECConstant.ContractStatus.CHECK
					+" and inputUserID="+qInfo.getUserId();

				if ( qInfo.getTransactionTypeId() > 0 && qInfo.getTransactionTypeId() < 1000 )
				{
					String tmpSQL = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedApplyForm = 1 and BusinessTypeID = " + qInfo.getTransactionTypeId();
					strSQL += " and transactionTypeID in (" + NameRef.getNamesByID(tmpSQL,"ID") + ") ";				
				}
				else if ( qInfo.getTransactionTypeId() > 1000 )
				{
					strSQL += " and transactionTypeID = " + qInfo.getTransactionTypeId();	
				}
			}
			else if (queryPurpose==4)		//for end contract
			{
				strSQL += " and statusID="+SECConstant.ContractStatus.ACTIVE
					+" and inputUserID=" + qInfo.getUserId() 
					+" and transactionTypeID="+SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING;
					
			}
			else if (queryPurpose==5)		//合同权限转移-合同查找
			{
				strSQL += " and statusID !=" + SECConstant.ContractStatus.CANCEL;
				strSQL += " and statusID !=" + SECConstant.ContractStatus.REFUSE;
				
				if (qInfo.getTransactionTypeId() > 0)
				{
					strSQL += " and transactionTypeID=" + qInfo.getTransactionTypeId();
				}
				if (qInfo.getInputUserId() > 0)
				{
					strSQL += " and inputUserID=" + qInfo.getInputUserId(); 
				}
			}
			
			if (qInfo.getClientId() != -1) {
				strSQL += " and ClientID = " + qInfo.getClientId() ;
			}
        
			if (qInfo.getCounterpartId() != -1) {
				strSQL += " and CounterpartID = " + qInfo.getCounterpartId() ;                    
			}
        
			if (qInfo.getStartAmount() != 0) {
				strSQL += " and Amount >= " + qInfo.getStartAmount() ;
			}
        
			if (qInfo.getEndAmount() != 0) {
				strSQL += " and Amount <= " + qInfo.getEndAmount() ;
			}
			
			if (qInfo.getStartContractId()!=-1){
				strSQL+=" and ID>=" + qInfo.getStartContractId();
			}
			
			if (qInfo.getEndContractId() != -1){
				strSQL+=" and ID<=" + qInfo.getEndContractId() ;
			}
			
			if (qInfo.getIntervalNum() !=-1 ){
				strSQL+=" and term=" + qInfo.getIntervalNum();
			}
			
			if (qInfo.getSettlementTypeId() !=-1 ){
				strSQL+=" and settlementTypeID=" + qInfo.getSettlementTypeId();
			}
			
			if (qInfo.getInterestTypeId()!=-1 ){
				strSQL+=" and interestTypeID= " + qInfo.getInterestTypeId();
			}
			
			if (qInfo.getCommissionChargeRate() !=0 ){
				strSQL+=" and commissionChargeRate=" + qInfo.getCommissionChargeRate() ;
			}
			
			if (qInfo.getBeginBoldScale() !=0 ){
				strSQL+=" and sumBold>=" + qInfo.getBeginBoldScale(); 
			}
			
			if (qInfo.getEndBoldScale()!=0 ){
				strSQL+=" and sumBold<=" + qInfo.getEndBoldScale();
			}
			
			if (qInfo.getStartBeginDate() != null) {
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartBeginDate()) + "'";
			}
			
			if (qInfo.getEndBeginDate() != null) {
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndBeginDate()) + "'";
			}

			if (qInfo.getStartEndDate() != null) {
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartEndDate()) + "'";
			}
			
			if (qInfo.getEndEndDate() != null) {
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndEndDate()) + "'";
			}
			
			if (qInfo.getStartInputDate() != null) {
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartInputDate()) + "'";
			}
			
			if (qInfo.getEndInputDate() != null) {
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndInputDate()) + "'";
			}
			
			if (qInfo.getStatusId()>0)
			{
				strSQL += " and statusID="+qInfo.getStatusId();
			}
			
			/*
			boxu add 2006-09-19
			double startcostAmount = 0;  //回购成本开始
			double endcostAmount = 0;  //回购成本结束
			long startterm = -1;  //开始期限
			long endterm = -1;  //结束期限
			double startincomeRate = 0;  //开始回购收益率
			double endincomeRate = 0;  //结束回购收益率
			*/
			if (startcostAmount != 0)
			{
				strSQL += " and costAmount >= " + startcostAmount;
			}
			if (endcostAmount != 0)
			{
				strSQL += " and costAmount <= " + endcostAmount;
			}
			if (startincomeRate > 0)
			{
				strSQL += " and incomeRate >= " + startincomeRate;
			}
			if (endincomeRate > 0)
			{
				strSQL += " and incomeRate <= " + endincomeRate;
			}
			if (startterm > 0)
			{
				strSQL += " and term >= " + startterm;
			}
			if (endterm > 0)
			{
				strSQL += " and term <= " + endterm;
			}
			
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			int nIndex = 0;
			String orderParamString=qInfo.getOrderParamString() ;
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0)
			{
				if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_applycontract"))
				{
					strSQL += " order by aa." + orderParamString.substring(nIndex+1);
				}
			}
			else
			{
				strSQL += " order by aa.ID";
			}
			
			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
				strSQL += " desc";
			}
			
			System.out.println("xxxxxxxxxbbbbbbbbbbbb"+strSQL);
			
			prepareStatement(strSQL);
			executeQuery();
			c=getDataEntitiesFromResultSet( SecuritiesContractInfo.class );
	
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同时产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
		return (c.size () > 0 ? c : null);
	}	
	/**
	 * 合同审批返回修改时，需要把合同设成待第一级审核状态
	 * @param contractID
	 * @return
	 * @throws Exception
	 */
	public long setCheckUserBack(long contractID) throws Exception
	{
		String strSQL = null;
		long    lResult=-1;
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		
				
		try 
		{
			strSQL = " update loan_LoanForm set NNEXTCHECKUSERID=NINPUTUSERID where ID="+contractID;
			prepareStatement(strSQL);
			lResult=executeUpdate();
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("退回修改时设置下级审核人产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/
		
		return lResult;		
	}
	
	public long check(ApprovalTracingInfo ATInfo) throws SecuritiesDAOException {
		
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";

		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();
		
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();

		SecuritiesContractInfo aInfo = new SecuritiesContractInfo();
		SEC_ApplyDAO applyDao = new SEC_ApplyDAO();
		SecuritiesContractInfo tmpInfo = new SecuritiesContractInfo();
		long applyID=-1;

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
		{			
			try
			{
				tmpInfo=(SecuritiesContractInfo)this.findByID(lApprovalContentID,tmpInfo.getClass());
				applyID=tmpInfo.getAccountId();
			}
			catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}

			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.REFUSE);
			try {
				update(aInfo);
				applyDao.updateStatus(applyID,SECConstant.ApplyFormStatus.REJECTED );
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.SUBMIT);
			aInfo.setNextCheckUserId(lNextUserID);
			aInfo.setNextCheckLevel(getNextCheckLevelByApplyID(lApprovalContentID) + 1);			
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}

		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.CHECK);
			aInfo.setNextCheckUserId(lNextUserID);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}

		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.SUBMIT);
			aInfo.setNextCheckUserId(ATInfo.getInputUserID());
			aInfo.setNextCheckLevel( 1 );
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		log4j.debug("check end");
		
		return lApprovalContentID;
	}	

	/**
	 * 
	 * @param lTransactionTypeID
	 * @return
	 * @throws SecuritiesDAOException
	 */	
	public String getContractCode(long lTransactionTypeID) throws SecuritiesDAOException 
	{
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2,4);
		
		try {
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	    
		strSQL = " select max(nvl(Code,0)) Code from SEC_ApplyContract where Code like 'HT" + strYear + lTransactionTypeID + "%'";
		log4j.debug(strSQL);

		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
		
			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				log4j.debug(strCode);
				if (strCode != null && strCode.length() == 11)
				{
					lCode = Long.parseLong(strCode.substring(8)) + 1;
				}
				else
				{
					lCode = 1;
				}
				strCode = "HT" + strYear + lTransactionTypeID + DataFormat.formatInt(lCode, 3, true);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("生成合同编号产生错误",e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("生成合同编号产生错误",e);
		}
			
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		log4j.debug(":::::::::: ::::strcode::::::" + strCode);

		return strCode;
	}
	
	/**
	 * 获得该申请书的当前审批级别
	 * @param applyId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	private long getNextCheckLevelByApplyID(long applyId) throws SecuritiesDAOException
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nextCheckLevel from SEC_APPLYCONTRACT where 1 = 1 ";
		strSQL += " and id = " + applyId;
		try
		{
			initDAO();
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			try
			{
				while (rs != null && rs.next()) 
				{
					nextCheckLevel = rs.getLong("nextCheckLevel");
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch(ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return nextCheckLevel;
	}
	
	//取得当前合同金额，根据条件判断是否结算利息
	//isInterest = true : 不计算利息
	//isInterest = false : 计算利息
	public ContractBalanceInfo getContractBalanceInfo(long contractID, boolean isInterest) throws ITreasuryDAOException 
	{
		String strSQL = "";
		ContractBalanceInfo info=new ContractBalanceInfo();
		try { 
			initDAO();

			strSQL="select sum(aa.mAmount) amount, sum(bb.NOTICEINTEREST) interest\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in ("+SECConstant.Direction.FINANCE_RECEIVE+","+SECConstant.Direction.PAY_AND_FINANCE_RECEIVE+")\n"
					+" and cc.id="+contractID;
			log.print( strSQL );		
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs!=null && rs.next())
			{
				if(isInterest == false){
					info.setTotalReceivedAmount(rs.getDouble("amount") - rs.getDouble("interest"));
				}
				else {
					info.setTotalReceivedAmount(rs.getDouble("amount"));
				}
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			
			strSQL="select sum(aa.mAmount) amount, sum(bb.NOTICEINTEREST) interest\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in ("+SECConstant.Direction.FINANCE_PAY+","+SECConstant.Direction.RECEIVE_AND_FINANCE_PAY+")\n"
					+" and cc.id="+contractID;
			log.print( strSQL );		
			prepareStatement(strSQL);
			rs = executeQuery();
			if (rs!=null && rs.next())
			{
				if(isInterest == false){
					info.setTotalPaiedAmount(rs.getDouble("amount") - rs.getDouble("interest"));
				}
				else {
					info.setTotalPaiedAmount(rs.getDouble("amount"));
				}
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			info.setBalance(Math.abs(info.getTotalReceivedAmount() - info.getTotalPaiedAmount()));
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("取合同余额时产生错误",e);
		} catch (SQLException e) {
			throw new ITreasuryDAOException("取合同余额时产生错误",e);
		}
		finally {
			finalizeDAO();
		}
		return info;
	}
	
		
	//取得合同当前余额
	public ContractBalanceInfo getContractBalance(long contractID) throws SecuritiesDAOException 
	{
		String strSQL = "";
		ContractBalanceInfo info=new ContractBalanceInfo();
		double totalInterest = 0;
		
		try { 
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		try {
			strSQL="select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in (4,7)\n"
					+" and cc.id="+contractID;
			log.print( strSQL );		
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs!=null && rs.next())
			{
				info.setTotalReceivedAmount(rs.getDouble(1)-rs.getDouble(2));
				System.out.println("sum(mAmount)-----"+rs.getDouble(1));
				System.out.println("sum(mInterest)-----"+rs.getDouble(2));
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			strSQL="select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in (5,6)\n"
					+" and cc.id="+contractID;
			log.print( strSQL );		
			prepareStatement(strSQL);
			rs = executeQuery();
			if (rs!=null && rs.next())
			{
				System.out.println("sum(mAmount)-----"+rs.getDouble(1));
				System.out.println("sum(mInterest)-----"+rs.getDouble(2));
				
				info.setTotalPaiedAmount(rs.getDouble(1)-rs.getDouble(2));
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			info.setBalance(Math.abs( info.getTotalReceivedAmount()-info.getTotalPaiedAmount())+totalInterest );
			System.out.println("Balance-----"+info.getBalance());

			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("取合同余额时产生错误",e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("取合同余额时产生错误",e);
		}
			
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return info;
	}
	
  //取得合同当前余额2
	public ContractBalanceInfo getContractAmount(long contractID) throws SecuritiesDAOException 
	{
		String strSQL = "";
		ContractBalanceInfo info=new ContractBalanceInfo();
		double totalInterest = 0;
		
		try { 
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		try {
			strSQL="select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in (5,6)\n"
					+" and cc.id="+contractID;
			log.print( strSQL );		
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs!=null && rs.next())
			{
				info.setTotalReceivedAmount(rs.getDouble(1)-rs.getDouble(2));
				System.out.println("sum(mAmount)-----"+rs.getDouble(1));
				System.out.println("sum(mInterest)-----"+rs.getDouble(2));
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			strSQL="select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in (4,7)\n"
					+" and cc.id="+contractID;
			log.print( strSQL );		
			prepareStatement(strSQL);
			rs = executeQuery();
			if (rs!=null && rs.next())
			{
				System.out.println("sum(mAmount)-----"+rs.getDouble(1));
				System.out.println("sum(mInterest)-----"+rs.getDouble(2));
				
				info.setTotalPaiedAmount(rs.getDouble(1)-rs.getDouble(2));
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			info.setBalance(Math.abs( info.getTotalReceivedAmount()-info.getTotalPaiedAmount())+totalInterest );
			System.out.println("Balance-----"+info.getBalance());

			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("取合同余额时产生错误",e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("取合同余额时产生错误",e);
		}
			
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return info;
	}
	
	//取得合同历史余额
	public ContractBalanceInfo getContractBalance(long contractID,Timestamp date) throws SecuritiesDAOException 
	{
		String strSQL = "";
		ContractBalanceInfo info=new ContractBalanceInfo();
		double totalInterest = 0;
		
		try { 
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		try {
			strSQL="select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in (4,7)\n"
					+" and cc.id="+contractID
					+" and to_char(aa.dtSettlementDate,'YYYY-MM-DD') <= '" + DataFormat.getDateString(date) + "'";
			log.print( strSQL );		
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs!=null && rs.next())
			{
				info.setTotalReceivedAmount(rs.getDouble(1)-rs.getDouble(2));
				System.out.println("sum(mAmount)-----"+rs.getDouble(1));
				System.out.println("sum(mInterest)-----"+rs.getDouble(2));
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			strSQL="select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n"
					+" from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+" where bb.id(+)=aa.nsecuritiesnoticeid\n"
					+" and cc.id(+)=bb.contractid\n"
					+" and dd.id(+)=bb.transactiontypeid\n"
					+" and aa.nstatusid=3\n"
					+" and dd.capitaldirection in (5,6)\n"
					+" and cc.id="+contractID
					+" and to_char(aa.dtSettlementDate,'YYYY-MM-DD') <= '" + DataFormat.getDateString(date) + "'";
			log.print( strSQL );		
			prepareStatement(strSQL);
			rs = executeQuery();
			if (rs!=null && rs.next())
			{
				System.out.println("sum(mAmount)-----"+rs.getDouble(1));
				System.out.println("sum(mInterest)-----"+rs.getDouble(2));
				
				info.setTotalPaiedAmount(rs.getDouble(1)-rs.getDouble(2));
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			info.setBalance(Math.abs( info.getTotalReceivedAmount()-info.getTotalPaiedAmount())+totalInterest );
			System.out.println("Balance-----"+info.getBalance());

			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("取合同余额时产生错误",e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("取合同余额时产生错误",e);
		}
			
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return info;
	}
	
	/**
	 * 通过合同id转入证券库存
	 * @param lContractID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public void saveStockByContractId(long lContractID) throws java.rmi.RemoteException, SecuritiesException
	{
		System.out.println("heredao");
		String strSQL = "";
		Vector v = new Vector ();
		
		double sumAmount = 0.0;//交易合计,会计分录用
		
		SecuritiesContractDao dao = new SecuritiesContractDao();
		SecuritiesContractInfo cInfo = new SecuritiesContractInfo();
		//获得合同的帐户和clientid
		try
		{
			cInfo = (SecuritiesContractInfo)dao.findByID(lContractID,cInfo.getClass());
		}
		catch (ITreasuryDAOException e1)
		{
			e1.printStackTrace();
		}
		
		//只有债券承销需要进行这些操作
		if(cInfo.getTransactionTypeId() != SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
		{
			log4j.debug("transactionTypeId="+cInfo.getTransactionTypeId());
			return;
		}
		
		StockOperation operation = new StockOperation();
		//库存操作的entity
		SecuritiesStockParam param = new SecuritiesStockParam();
		
		param.setAccountID(cInfo.getAccountId());
		param.setClient(cInfo.getClientId());
		param.setDeliveryDate(Env.getSystemDate());
				
        		
		try  
		{
			initDAO();
		} catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
	    		
	    		
	    		
		strSQL = "select securitiesid,nvl(nvl(sum(receivedNumber),0)-nvl(sum(saledNumber),0),0) balanceNumber,nvl(nvl(sum(receivedAmount),0)-nvl(sum(saledAmount),0),0) balanceAmount " 
				+" from("	
				+" select a.id,a.securitiesid,a.ratename,a.facesumamount/100 receivedNumber,facesumamount receivedAmount,"
				+" d.saledNumber,d.saledAmount "
				+" from sec_noticeWithSecurities a,"
				+" sec_noticeform b,"
				+" sec_applycontract c,"
				+" (select sum(faceSumAmount/100) saledNumber,sum(faceSumAmount) saledAmount,relatedId "
				+" from sec_noticewithsecurities aa,sec_noticeform bb"
				+" where aa.noticeid = bb.id"
				+" and bb.statusid = " + SECConstant.NoticeFormStatus.CHECKED
				+" group by relatedId)d "
				+" where " 
				+" a.noticeid = b.id " 
				+" and a.statusid = " + Constant.RecordStatus.VALID
				+" and b.contractid = c.id " 
				+" and c.id = " + lContractID
				+" and b.statusid = " + SECConstant.NoticeFormStatus.CHECKED
				+" and b.transactiontypeid = " + SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY
				+" and a.id = d.relatedId(+) "
				+" ) group by securitiesid ";
      
		log4j.debug(strSQL);
		
		try 
		{
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
				param.setSecuritiesID(rs.getLong("securitiesid"));
				param.setQuantity(rs.getDouble("balanceNumber"));
				param.setAmount(rs.getDouble("balanceAmount"));
				
				if(param.getAmount() > 0)
				{
					sumAmount += param.getAmount();
					operation.enterStock(param);
				}
			}
			//生成会计分录
			if(sumAmount > 0)
			{
				generateGLEntry(cInfo,sumAmount,this.transConn);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("通过合同id转入证券库存产生错误",e);			
		} catch (SQLException e) {
			throw new SecuritiesDAOException("通过合同id转入证券库存产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		
	}
	
	/**生成会计分录
	 * @param info
	 * @param sumAmount
	 * @throws Exception
	 */
	private void generateGLEntry(SecuritiesContractInfo info,double sumAmount,Connection conn) throws java.rmi.RemoteException, SecuritiesException
	{
		try
		{
			log4j.info("生成合同会计分录");
			GenerateGLEntryParam generateGLEntryParam;
			SecuritiesGeneralLedgerOperation securitiesGeneralLedgerOperation = new SecuritiesGeneralLedgerOperation(conn);
			generateGLEntryParam = new GenerateGLEntryParam();
			generateGLEntryParam.setNetIncome(sumAmount);
			generateGLEntryParam.setAccountType(SECConstant.EntryAccountType.AccountType_13);
			generateGLEntryParam.setOfficeID(info.getOfficeId());
			generateGLEntryParam.setCurrencyID(info.getCurrencyId());
			generateGLEntryParam.setInputUserID(info.getInputUserId());
			generateGLEntryParam.setCheckUserID(info.getInputUserId());
			generateGLEntryParam.setTransactionType(SECConstant.BusinessType.BOND_UNDERWRITING.CONTRACT_END);
			generateGLEntryParam.setSubTransactionType(info.getInterestTypeId());
			generateGLEntryParam.setDeliverOrderCode(info.getCode());
			//generateGLEntryParam.setSuspenseInterest(dContractInterest);
			generateGLEntryParam.setExecuteDate(Env.getSystemDate());
			generateGLEntryParam.setDeliveryDate(Env.getSystemDate());
			generateGLEntryParam.setTransactionDate(Env.getSystemDate());
						
			securitiesGeneralLedgerOperation.generateGLEntry(generateGLEntryParam);
			log4j.debug("生成合同会计分录成功");
		}
		catch(SecuritiesException e)
		{
			log4j.debug("生成合同会计分录失败");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 通过虚拟交割单ID取得合同信息（债券承销）
	 * */
	public SecuritiesContractInfo findContractInfoByDeliveryOrderID(long lDeliveryOrderID) throws SecuritiesDAOException{
		String strSQL = "";
		SecuritiesContractInfo info = new SecuritiesContractInfo();
		SecuritiesContractDao dao = new SecuritiesContractDao();
		long lContractID = -1;
		
		try { 
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		try {
			strSQL = "select ContractID from Sec_Noticeform "
				+ " where StatusID not in ( " + SECConstant.NoticeFormStatus.CANCELED + "," + SECConstant.NoticeFormStatus.REJECTED + " )"
				+ " and DeliveryOrderID = " + lDeliveryOrderID;
			log.print( strSQL );
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next())
			{
			    lContractID = rs.getLong("ContractID");
			}
			if (lContractID > 0)
			{
			    info = (SecuritiesContractInfo) dao.findByID(lContractID,info.getClass());
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("通过虚拟交割单ID取得合同信息时产生错误",e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("通过虚拟交割单ID取得合同信息时产生错误",e);
		}
			
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return info;
	}
	
	/**
	 * 结束合同得方法
	 * @param lID
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws  SecuritiesException
	{
		AttornmentApplyDao attDao = new AttornmentApplyDao();
		SecuritiesContractInfo info = null;

		for (int i = 0; i < lID.length; i++)
		{
			try
			{
				if (lID[i]<=0) continue;
				info=(SecuritiesContractInfo)findByID(lID[i],SecuritiesContractInfo.class );
				//System.out.println("---------------"+info.getTransactionTypeId() );
			
				if (info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE)
				{
					attDao.endForRepurchase( info.getApplyId() );
				}
				updateStatus(lID[i], SECConstant.ContractStatus.FINISH);
				//更新库存
				saveStockByContractId(lID[i]);
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new SecuritiesException("", e);
			}
			catch (Exception e)
			{
				e.printStackTrace() ;
			}
		}	
	}
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查交割单是否已被使用
	 * @param contractId 申请单id
	 * @return
	 */
	public boolean checkStatuID(long contractId) throws SecuritiesDAOException{
		boolean flag=false;
		long[] checkStatusId={
				//SECConstant.NoticeFormStatus.SUBMITED,
				SECConstant.NoticeFormStatus.APPROVALING,
				SECConstant.NoticeFormStatus.CHECKED,
				SECConstant.NoticeFormStatus.USED,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "select count(*) from sec_noticeform s");
		sqlBuf.append(" where s.statusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.contractid = " + contractId);
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
			ResultSet rs = executeQuery();
			try
			{
				if(rs != null && rs.next())
				{
					long temp=rs.getLong(1);
					if(temp>0){
						flag=true;
					}
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return flag;		
	}
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查交割单是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean cancelNotice(long contractId) throws SecuritiesDAOException{
		boolean flag=false;
		long[] checkStatusId={
				SECConstant.NoticeFormStatus.SUBMITED,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "update  sec_noticeform s");
		sqlBuf.append(" set s.statusid = "+SECConstant.NoticeFormStatus.CANCELED);
		sqlBuf.append(" where s.statusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.contractid = " + contractId);
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
			ResultSet rs = executeQuery();
			try
			{
				if(rs != null && rs.next())
				{
					long temp=rs.getLong(1);
					if(temp>0){
						flag=true;
					}
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return flag;		
	}
	/**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long update(long loanID, long userID, long newStatusID)
			throws Exception {
	   PreparedStatement ps = null;
	   Connection conn = null;
       String strSQL = null;
       long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update SEC_APPLYCONTRACT set StatusID=? where ID=?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, newStatusID);
			ps.setLong(2, loanID);

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("update SEC_APPLYCONTRACT property info error:"
						+ lResult);
				return -1;
			} else {
				return loanID;
			}
		} catch (Exception e) {

			cleanup(ps);
			cleanup(conn);
			throw e;
		} finally {

			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 更新资产转让合同转让款项可用余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updateAvailableTransferBalance(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set availableTransferBalance = availableTransferBalance + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 更新资产转让合同转让款项余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updateTransferBalance(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set transferBalance = transferBalance + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 更新资产转让合同待回购可用余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updateAvailableRepurchaseBalance(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set availableRepurchaseBalance = availableRepurchaseBalance + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 更新资产转让合同待回购余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updateRepurchaseBalance(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set repurchaseBalance = repurchaseBalance + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 更新资产转让已收利息（待收款）余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updateWaitReceivedInterest(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set waitReceivedInterest = waitReceivedInterest + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 更新资产转让已收利息（已收款）余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updateReceivedInterest(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set receivedInterest = receivedInterest + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 更新资产转让已付利息（待支付）余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updateWaitPaidInterest(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set waitPaidInterest = waitPaidInterest + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 更新资产转让已付利息（已支付）余额
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws Exception
	 */
	public long updatePaidInterest(long lContractID, double dAmount) throws SecuritiesDAOException 
	{
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" update SEC_APPLYCONTRACT set paidInterest = paidInterest + ? where ID=? \n");
			prepareStatement(sbSQL.toString());
			transPS.setDouble(1, dAmount);
			transPS.setLong(2, lContractID);
			executeUpdate();
		}catch (SQLException e) {

			e.printStackTrace();
		}catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 根据资产转让通知单的类型，保存或保存并提交通知单时更新资产转让合同的各种余额信息
	 * （转让款项余额、转让款项可用余额、待购回余额、待购回可用余额、已收利息（已收款）、已收利息（待收款）、已付利息（已支付）、已付利息（待支付））
	 * @param lNoticeTypeId
	 */
	public void submitBalanceAndInterestOfContract(NoticeInfo nInfo) throws SecuritiesException
	{
		try {
			//支付转让款项  则合同中转让款项可用余额减少
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_PAYMENT)
			{
				updateAvailableTransferBalance(nInfo.getContractId(), -1*(nInfo.getNoticeAmount()));
			}
			//利息收回通知单  则合同中已收利息（待收款）余额增加
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYBACK)
			{
				updateWaitReceivedInterest(nInfo.getContractId(), nInfo.getNoticeAmount());
			}
			//买入（回购）购回通知单  则合同中待购回可用余额减少
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_CAPITAL
					|| nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_CAPITAL)
			{
				updateAvailableRepurchaseBalance(nInfo.getContractId(), -1*(nInfo.getNoticePrice()));
			}
			//收到转让款项  则合同中转让款项可用余额减少
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_PAYBACK)
			{
				updateAvailableTransferBalance(nInfo.getContractId(), -1*(nInfo.getNoticeAmount()));
			}
			//利息支付通知单 则合同中已付利息（待支付）增加
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT)
			{
				updateWaitPaidInterest(nInfo.getContractId(), nInfo.getNoticeAmount());
			}
		}catch (SecuritiesDAOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据资产转让通知单的类型，取消通知单时更新资产转让合同的各种余额信息
	 * （转让款项余额、转让款项可用余额、待购回余额、待购回可用余额、已收利息（已收款）、已收利息（待收款）、已付利息（已支付）、已付利息（待支付））
	 * @param lNoticeTypeId
	 */
	public void cancelBalanceAndInterestOfContract(NoticeInfo nInfo) throws SecuritiesException
	{
		try {
			
			//支付转让款项  则合同中转让款项可用余额增加
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_PAYMENT)
			{
				updateAvailableTransferBalance(nInfo.getContractId(), nInfo.getNoticeAmount());
			}
			//利息收回通知单  则合同中已收利息（待收款）余额减少
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYBACK)
			{
				updateWaitReceivedInterest(nInfo.getContractId(), -1*(nInfo.getNoticeInterest()));
			}
			//购回通知单  则合同中待购回可用余额增加
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_CAPITAL
					|| nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_CAPITAL)
			{
				updateAvailableRepurchaseBalance(nInfo.getContractId(), nInfo.getNoticePrice());
				if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_CAPITAL)
					updateWaitReceivedInterest(nInfo.getContractId(), -1*(nInfo.getNoticeInterest()));
				else
					updateWaitPaidInterest(nInfo.getContractId(), -1*(nInfo.getNoticeInterest()));

			}
			//收到转让款项  则合同中转让款项可用余额增加
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_PAYBACK)
			{
				updateAvailableTransferBalance(nInfo.getContractId(), nInfo.getNoticeAmount());
			}
			//利息支付通知单 则合同中已付利息（待支付）减少
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT)
			{
				updateWaitPaidInterest(nInfo.getContractId(), -1*(nInfo.getNoticeInterest()));
			}
		}catch (SecuritiesDAOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		SecuritiesContractDao dao=new SecuritiesContractDao();
		
		//dao.setUseMaxID();
		
		//SecuritiesContractQueryInfo qInfo=new SecuritiesContractQueryInfo();
		//qInfo.setQueryPurpose(5);
		//info.setApplyId( 99 );
		long aa[]=new long[1];
		aa[0]=10780;
		//info=(SecuritiesContractInfo)dao.findByID( 2,info.getClass());
			try
			{
				//ContractBalanceInfo info=dao.getContractBalance( 181 );
				//System.out.println(info.getBalance() );
				dao.endContract( aa );
				
			} catch (Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	
	}
}
