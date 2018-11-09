package com.iss.itreasury.settlement.interest.dao;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTConstant.InterestFeeType;
import com.iss.itreasury.settlement.util.SETTConstant.InterestOperateType;
import com.iss.itreasury.util.DataFormat;

public class InterestQueryDao {

	/**
	 * 利息费用结算处理，获得查询SQL
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryInterestSQL(InterestQueryInfo qInfo) {

		StringBuffer sbSQL = new StringBuffer();

		sbSQL.append(" select \n");
		sbSQL.append(" AccountID, \n");
		sbSQL.append(" AccountNo, \n"); // 账户号
		sbSQL.append(" AccountTypeID, \n"); // 账户类型
		sbSQL.append(" SubAccountID, \n"); // 子账户ID
		sbSQL.append(" contractNo, \n"); // 合同号
		sbSQL.append(" payFormNo, \n");
		sbSQL.append(" InterestStartDate, \n");
		sbSQL.append(" InterestEndDate,  \n");
		sbSQL.append(" case when InterestDays<'0' then '' else InterestDays||'' end as InterestDays,  \n");
		sbSQL.append(" Interest, \n");
		sbSQL.append(" Rate, \n");
		sbSQL.append(" NegotiateInterest, \n");
		sbSQL.append(" BaseBalance, \n");
		sbSQL.append(" InterestTax, \n");
		sbSQL.append(" TransNo, \n");
		sbSQL.append(" InputUserID, \n");
		sbSQL.append(" ExecuteDate, \n");
		sbSQL.append(" InterestType, \n");
		sbSQL.append(" OperationType, \n");
		sbSQL.append(" PayInterestAccountID, \n");
		sbSQL.append(" ReceiveInterestAccountID, \n");
		sbSQL.append(" ID, \n");
		sbSQL.append(" OfficeID, \n");
		sbSQL.append(" CurrencyID, \n");
		sbSQL.append(" StatusID, \n");
		sbSQL.append(" InterestSettlement, \n");
		sbSQL.append(" InterestFeeSettingDetailID, \n");
		sbSQL
				.append(" InterestType || '##' || OperationType as InterestType_ex1, \n");
		sbSQL
				.append(" InterestType || '##' || Interest || '##' || NegotiateInterest as Interest_ex2 \n");

		sbSQL.append(" from \n");
		sbSQL.append(" sett_queryInterest \n");

		sbSQL.append(" where \n");
		sbSQL.append(" 1 = 1 ");
		if (qInfo.getOfficeID() > 0) {
			sbSQL.append(" AND ");
			sbSQL.append(" OfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			sbSQL.append(" AND ");
			sbSQL.append(" CurrencyID = " + qInfo.getCurrencyID());
		}
		sbSQL.append(" and StatusID <>  "
				+ SETTConstant.TransactionStatus.DELETED + " ");
		if (qInfo.getClearInterest() != null) {
			sbSQL.append(" AND ");
			sbSQL.append(" InterestSettlement= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterest())
					+ "','yyyy-mm-dd')");
		}
		if (qInfo.getUserID() > 0) {
			sbSQL.append(" AND( ");
			sbSQL.append(" InputUserID= " + qInfo.getUserID() + "");
			sbSQL.append(" or InterestFeeSettingDetailID != -1 ");
			sbSQL.append(" ) ");
		}
		if (qInfo.getIsPrewDraw() > 0) {
			sbSQL.append(" AND InterestType = "
					+ InterestFeeType.PREDRAWINTEREST
					+ " AND OperationType in ("
					+ InterestOperateType.PREDRAWINTEREST + ","
					+ InterestOperateType.CLEANPREDRAWINTEREST + ")");
		}

		return sbSQL.toString();
	}

	/**
	 * 打印到期贷款通知书、逾期贷款催收通知书，获得查询SQL
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryloanMatureNoticeSQL(InterestEstimateQueryInfo qInfo) {
StringBuffer m_sbSelect = new StringBuffer();
		
		
		m_sbSelect.append(" distinct account.ID AS accountID, \n");  
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");                      
		m_sbSelect.append(" account.nOfficeID AS OfficeID, \n");
		m_sbSelect.append(" account.nCurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect.append(" subaccount.dtclearinterest as ClearInterestDate, \n");
		m_sbSelect.append(" subaccount.ID AS subAccountID, \n");
		m_sbSelect.append(" subaccount.AL_NLOANNOTEID AS LoanNoteID, \n");		
		m_sbSelect.append(" contractform.ID AS ContractID, \n");
		m_sbSelect.append(" contractform.sContractCode AS contractNo, \n");
		m_sbSelect.append(" contractform.nBorrowClientID AS BorrowClientID, \n");
		m_sbSelect.append(" contractform.mLoanAmount AS LoanAmount, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID,\n"); //贷款类型
		//m_sbSelect.append(" contractform.dtStartDate AS LoanStartDate, \n");
		//m_sbSelect.append(" contractform.dtEndDate AS LoanEndDate, \n");		
		m_sbSelect.append(" payform.mAmount AS PayFormAmount, \n");
		m_sbSelect.append(" payform.dtStart AS LoanStartDate, \n");
		m_sbSelect.append(" payform.dtEnd AS LoanEndDate, \n");
		m_sbSelect.append(" contractform.nInterValNum as LoanTerm, \n");				
		m_sbSelect.append(" payform.sCode AS payFormNo \n");								
		// from 
		StringBuffer m_sbFrom = new StringBuffer();		
		m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client,client consigner \n");
		// where 
		StringBuffer m_sbWhere = new StringBuffer();
		
		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+) \n");
		if(qInfo.getIsSelectConsigner()>0)
		{
			
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" contractform.nConsignClientID = consigner.ID \n");		
						
		}			
		
		m_sbWhere.append(" AND account.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" and subaccount.dtClearInterest is not null \n");
		if(qInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if(qInfo.getCurrencyID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}	
		if(qInfo.getIsSelectClientNo()>0)
		{		
			if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append(" and client.scode>='" + qInfo.getClientNoFrom() + "'");
			if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo() + "'");
		}	
		if(qInfo.getIsSelectClearInterestDate()>0)
		{
			if(qInfo.getClearInterestDate() != null)
			{                                    //结息日				
				m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"+DataFormat.formatDate(qInfo.getClearInterestDate())+"','yyyy-mm-dd')");
			}	    	
		}
		if(qInfo.getIsSelectSelfLoanSort()>0)
		{
			if(qInfo.getSelfLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getSelfLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getSelfLoanSort() + "' \n");
			}
			if(qInfo.getSelfLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (1,2,5,6,7,8) ");
			}
		}
		if(qInfo.getIsSelectSelfLoanTerm()>0)
		{
			if(qInfo.getSelfLoanTermFrom() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum >=" + qInfo.getSelfLoanTermFrom() + "");
			}
			if(qInfo.getSelfLoanTermTo() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum <=" + qInfo.getSelfLoanTermTo() + "");				
			}
			// add by kevin(刘连凯)2011-08-01 当选择的自营贷款期限时，合同类型应默认是自营
			m_sbWhere.append("and contractform.ntypeid ="+LOANConstant.LoanType.ZY+" \n"); 
		}
		if(qInfo.getIsSelectConsignLoanSort()>0)
		{
			if(qInfo.getConsignLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getConsignLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getConsignLoanSort() + "' \n");
			}
			if(qInfo.getConsignLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (3,4) ");
			}
		}
		if(qInfo.getIsSelectConsigner()>0)
		{
			if (qInfo.getConsignerFrom() != null && qInfo.getConsignerFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode>='" + qInfo.getConsignerFrom() + "'");				
			}
			if (qInfo.getConsignerTo() != null && qInfo.getConsignerTo().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode<='" + qInfo.getConsignerTo() + "'");				
			}						
		}
		if(qInfo.getIsSelectContractNo()>0)
		{
			if (qInfo.getContractNoFrom() != null && qInfo.getContractNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode>='" + qInfo.getContractNoFrom() + "'");				
			}
			if (qInfo.getContractNoTo() != null && qInfo.getContractNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode<='" + qInfo.getContractNoTo() + "'");				
			}			
		}
		if(qInfo.getIsSelectPayFormNo()>0)
		{
			if (qInfo.getPayFormNoFrom() != null && qInfo.getPayFormNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode>='" + qInfo.getPayFormNoFrom() + "'");				
			}
			if (qInfo.getPayFormNoTo() != null && qInfo.getPayFormNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode<='" + qInfo.getPayFormNoTo() + "'");				
			}			
		}
				
		return  "select " + m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString();
	}

	public String calculateInterestSQL(InterestQueryInfo qInfo) {

		StringBuffer sbSQL = new StringBuffer();

		long lInterestType = -1;
		if (qInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION) {
			lInterestType = SETTConstant.InterestFeeType.COMMISION;
		}
		if (qInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE) {
			lInterestType = SETTConstant.InterestFeeType.ASSURE;
		}
		if (qInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST) {
			lInterestType = SETTConstant.InterestFeeType.PREDRAWINTEREST;
		}

		// 构造查询语句主体
		sbSQL.append(" SELECT ");
		sbSQL.append(" -1 contractID, -1 discreID, ");
		sbSQL
				.append(" contractform.dtenddate enddate, -1 discountRate, account.ID AS accountID, ");
		sbSQL.append(" account.sAccountNo AS accountNo, "); // 账户号
		sbSQL.append(" account.nAccountTypeID AS accountTypeID,"); // 账户类型
		sbSQL.append(" subaccount.ID AS subAccountID, "); // 子账户 ID
		sbSQL.append(" subaccount.AF_sDepositNo AS fixedDepositNo, "); // 定期单据号

		// 活期、保证金、委贷、自营用此字段
		sbSQL
				.append(" subaccount.AL_mPreDrawInterest AS loanPreDrawInterest, ");

		// 通知、定期
		sbSQL.append(" contractform.sContractCode AS contractNo, "); // 合同号
		sbSQL.append(" payform.sCode AS payFormNo, "); // 放款通知单号
		sbSQL.append(" payform.ID AS payFormID "); // 放款通知单号ID MODIFY BY ZCWANG
													// 2007-6-23
		sbSQL.append(" FROM ");
		sbSQL
				.append(" sett_Account account, sett_SubAccount subaccount, (select id,scode,nContractID from loan_PayForm union select id,code scode,ContractID nContractID from LOAN_ASSURECHARGEFORM) payform, loan_ContractForm contractform, loan_LoanForm loanform");
		sbSQL.append(" , Sett_AccountType acctype ");
		sbSQL.append(" WHERE ");
		sbSQL.append(" account.ID = subaccount.nAccountID ");
		sbSQL.append(" and account.nAccountTypeID = acctype.id ");
		if (qInfo.getIsCheckedType() == 1) {
			if (qInfo.getLAccountTypeID1() > 0) {
				sbSQL.append(" and acctype.id = " + qInfo.getLAccountTypeID1()
						+ " ");
			}
		} else if (qInfo.getIsCheckedType() == 2) {
			if (qInfo.getLAccountTypeID2() > 0) {
				sbSQL.append(" and acctype.naccountgroupid = "
						+ qInfo.getLAccountTypeID2() + " ");
			}
		}
		sbSQL.append(" AND account.NSTATUSID in ("
				+ SETTConstant.AccountStatus.NORMAL + ","
				+ SETTConstant.AccountStatus.FREEZE + ","
				+ SETTConstant.AccountStatus.ALLFREEZE + ","
				+ SETTConstant.AccountStatus.PARTFREEZE + ")");
		sbSQL.append(" AND account.NCHECKSTATUSID = "
				+ SETTConstant.AccountCheckStatus.CHECK);
		sbSQL.append(" AND subaccount.NSTATUSID in ( "
				+ SETTConstant.SubAccountStatus.NORMAL + ","
				+ SETTConstant.SubAccountStatus.ONLYPAYFREEZE + ","
				+ SETTConstant.SubAccountStatus.ALLFREEZE + ","
				+ SETTConstant.SubAccountStatus.PARTFREEZE + ")");
		sbSQL.append(" AND ");
		sbSQL.append(" subaccount.AL_nLoanNoteID = payform.ID (+) ");
		sbSQL.append(" AND ");
		sbSQL.append(" payform.nContractID = contractform.ID (+) ");
		sbSQL.append(" AND ");
		sbSQL.append(" contractform.nLoanID = loanform.ID (+) ");
		if (!qInfo.getAccountIDFromCtrl().trim().equals("")) { // 开始账户号
			sbSQL.append(" AND ");
			sbSQL.append(" account.sAccountNo >= '"
					+ qInfo.getAccountIDFromCtrl() + "'");
		}
		if (!qInfo.getAccountIDToCtrl().trim().equals("")) { // 结束账户号
			sbSQL.append(" AND ");
			sbSQL.append(" account.sAccountNo <= '"
					+ qInfo.getAccountIDToCtrl() + "'");
		}
		if (!qInfo.getFixedDepositIDFromCtrl().trim().equals("")) { // 定期单据号开始
			sbSQL.append(" AND( ");
			sbSQL.append(" subaccount.AF_sDepositNo >= '"
					+ qInfo.getFixedDepositIDFromCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getFixedDepositIDToCtrl().trim().equals("")) { // 定期单据号结束
			sbSQL.append(" AND( ");
			sbSQL.append(" subaccount.AF_sDepositNo <= '"
					+ qInfo.getFixedDepositIDToCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getContractIDFromCtrl().trim().equals("")) { // 合同号开始
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.sContractCode >= '"
					+ qInfo.getContractIDFromCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getContractIDToCtrl().trim().equals("")) { // 合同号结束
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.sContractCode <= '"
					+ qInfo.getContractIDToCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getPayFormIDFromCtrl().trim().equals("")) { // 放款通知单号开始
			sbSQL.append(" AND( ");
			sbSQL.append(" payform.sCode >= '" + qInfo.getPayFormIDFromCtrl()
					+ "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getPayFormIDToCtrl().trim().equals("")) { // 放款通知单号结束
			sbSQL.append(" AND( ");
			sbSQL.append(" payform.sCode <= '" + qInfo.getPayFormIDToCtrl()
					+ "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getLSubLoanTypeValue().trim().equals("")) {// 如果将multiple的select值选到右边,再全选到左边,此时控件的值为-1
			if (!qInfo.getLSubLoanTypeValue().trim().equals("-1")) {
				// 贷款子类型
				sbSQL.append(" AND( ");
				sbSQL.append(" contractform.NSUBTYPEID in ("
						+ qInfo.getLSubLoanTypeValue());
				sbSQL.append(" )) ");
			}
		}
		if (qInfo.getLoanTerm() != -1) { // 贷款期限
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.nIntervalNum = " + qInfo.getLoanTerm());
			sbSQL.append(" ) ");
		}
		if (qInfo.getYearTerm() != -1) { // 年期
			sbSQL.append(" AND( ");
			sbSQL.append(" substr(contractform.sContractCode,0,4) = '"
					+ qInfo.getYearTerm() + "'");
			sbSQL.append(" ) ");
		}
		if (qInfo.getConsignID() != -1) { // 委托方
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.nConsignClientID = "
					+ qInfo.getConsignID());
			sbSQL.append(" ) ");
		}
		if (lInterestType == SETTConstant.InterestFeeType.ASSURE) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearSureFee < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd') and subaccount.AL_MSURETYFEE >0 ");
			}
		}
		if (qInfo.isBInterest()) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.dtClearInterest <= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
			}
		}
		if (lInterestType == SETTConstant.InterestFeeType.COMMISION) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearCommission < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
			}
		}
		if (qInfo.isBCompoundInterest()) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearCompound < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd') and subaccount.AL_MCOMPOUNDINTEREST >0");
				
			}
		}
		if (lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND( ");
				sbSQL.append(" subaccount.AF_dtPreDraw <= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" or subaccount.AL_dtPreDraw <= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" ) and (subaccount.af_mpredrawinterest > 0 or subaccount.al_mpredrawinterest > 0)");

			}
		}
		if (qInfo.isBForfeitInterest()) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearOverDue < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd') and subaccount.AL_MOVERDUEINTEREST>0");
				
				
			}
		}

		if (qInfo.getIsClearNull() == SETTConstant.BooleanValue.ISTRUE) { // 是否滤空
			sbSQL.append(" AND ");
			sbSQL.append(" subaccount.MBALANCE > 0");
		}
		sbSQL.append(" AND ");
		sbSQL.append(" account.nOfficeID = " + qInfo.getOfficeID());
		sbSQL.append(" AND ");
		sbSQL.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		sbSQL.append(" AND ");

		// 贴现贷款
		sbSQL
				.append(" account.nAccountTypeID not in (select id from sett_accounttype where nAccountGroupID="
						+ SETTConstant.AccountGroupType.DISCOUNT
						+ " and nstatusId=1 and officeId="
						+ qInfo.getOfficeID()
						+ " and currencyId="
						+ qInfo.getCurrencyID() + ")");

		/**
		 * 修改的地方
		 */
		// 加入贴现记录查询 start----------
		sbSQL.append(" union ");
		sbSQL.append(" SELECT ");

		// 从合同表中查询出贴现利率
		sbSQL.append(" contractform.id contractID, discredence.id discreID, ");
		sbSQL
				.append(" contractform.dtenddate enddate, contractform.mdiscountrate AS discountRate, account.ID AS accountID, ");
		sbSQL.append(" account.sAccountNo AS accountNo, "); // 帐户号
		sbSQL.append(" account.nAccountTypeID AS accountTypeID,"); // 帐户类型
		sbSQL.append(" subaccount.ID AS subAccountID, "); // 子帐户 ID
		sbSQL.append(" subaccount.AF_sDepositNo AS fixedDepositNo, "); // 定期单据号
		sbSQL
				.append(" subaccount.AL_mPreDrawInterest AS loanPreDrawInterest, "); // 贴现计提利息
		sbSQL.append(" contractform.sContractCode AS contractNo, "); // 合同号
		sbSQL.append(" discredence.sCode AS payFormNo, "); // 放款通知单号
		sbSQL.append(" -1 payFormID "); // 放款通知单号ID MODIFY BY ZCWANG 2007-6-23
		sbSQL.append(" FROM ");
		sbSQL
				.append(" sett_Account account, sett_SubAccount subaccount, loan_discountcredence discredence, loan_ContractForm contractform, loan_LoanForm loanform");
		sbSQL.append(" , Sett_AccountType acctype ");
		sbSQL.append(" WHERE ");
		sbSQL.append(" account.ID = subaccount.nAccountID ");
		sbSQL.append(" and account.nAccountTypeID = acctype.id ");
		if (qInfo.getIsCheckedType() == 1) {
			if (qInfo.getLAccountTypeID1() > 0) {
				sbSQL.append(" and acctype.id = " + qInfo.getLAccountTypeID1()
						+ " ");
			}
		} else if (qInfo.getIsCheckedType() == 2) {
			if (qInfo.getLAccountTypeID2() > 0) {
				sbSQL.append(" and acctype.naccountgroupid = "
						+ qInfo.getLAccountTypeID2() + " ");
			}
		}
		sbSQL.append(" AND account.NSTATUSID in ("
				+ SETTConstant.AccountStatus.NORMAL + ","
				+ SETTConstant.AccountStatus.FREEZE + ","
				+ SETTConstant.AccountStatus.ALLFREEZE + ","
				+ SETTConstant.AccountStatus.PARTFREEZE + ")");
		sbSQL.append(" AND account.NCHECKSTATUSID = "
				+ SETTConstant.AccountCheckStatus.CHECK);
		sbSQL.append(" AND subaccount.NSTATUSID in ( "
				+ SETTConstant.SubAccountStatus.NORMAL + ","
				+ SETTConstant.SubAccountStatus.ONLYPAYFREEZE + ","
				+ SETTConstant.SubAccountStatus.ALLFREEZE + ","
				+ SETTConstant.SubAccountStatus.PARTFREEZE + ")");
		sbSQL.append(" AND ");
		sbSQL.append(" subaccount.al_nloannoteid=discredence.id(+) ");
		sbSQL.append(" AND ");
		sbSQL.append(" discredence.ncontractid=contractform.ID (+) ");
		sbSQL.append(" AND ");
		sbSQL.append(" contractform.nLoanID = loanform.ID (+) ");

		if (!qInfo.getAccountIDFromCtrl().trim().equals("")) { // 开始帐户号
			sbSQL.append(" AND ");
			sbSQL.append(" account.sAccountNo >= '"
					+ qInfo.getAccountIDFromCtrl() + "'");
		}
		if (!qInfo.getAccountIDToCtrl().trim().equals("")) { // 结束帐户号
			sbSQL.append(" AND ");
			sbSQL.append(" account.sAccountNo <= '"
					+ qInfo.getAccountIDToCtrl() + "'");
		}
		if (!qInfo.getFixedDepositIDFromCtrl().trim().equals("")) { // 定期单据号开始
			sbSQL.append(" AND( ");
			sbSQL.append(" subaccount.AF_sDepositNo >= '"
					+ qInfo.getFixedDepositIDFromCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getFixedDepositIDToCtrl().trim().equals("")) { // 定期单据号结束
			sbSQL.append(" AND( ");
			sbSQL.append(" subaccount.AF_sDepositNo <= '"
					+ qInfo.getFixedDepositIDToCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getContractIDFromCtrl().trim().equals("")) { // 合同号开始
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.sContractCode >= '"
					+ qInfo.getContractIDFromCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getContractIDToCtrl().trim().equals("")) { // 合同号结束
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.sContractCode <= '"
					+ qInfo.getContractIDToCtrl() + "'");
			sbSQL.append(" ) ");
		}
		if (!qInfo.getLSubLoanTypeValue().trim().equals("")) {// 如果将multiple的select值选到右边,再全选到左边,此时控件的值为-1
			if (!qInfo.getLSubLoanTypeValue().trim().equals("-1")) {
				// 贷款子类型
				sbSQL.append(" AND( ");
				sbSQL.append(" contractform.NSUBTYPEID in ("
						+ qInfo.getLSubLoanTypeValue());
				sbSQL.append(" )) ");
			}
		}
		if (qInfo.getLoanTerm() != -1) { // 贷款期限
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.nIntervalNum = " + qInfo.getLoanTerm());
			sbSQL.append(" ) ");
		}
		if (qInfo.getYearTerm() != -1) { // 年期
			sbSQL.append(" AND( ");
			sbSQL.append(" substr(contractform.sContractCode,0,4) = '"
					+ qInfo.getYearTerm() + "'");
			sbSQL.append(" ) ");
		}
		if (qInfo.getConsignID() != -1) { // 委托方
			sbSQL.append(" AND( ");
			sbSQL.append(" contractform.nConsignClientID = "
					+ qInfo.getConsignID());
			sbSQL.append(" ) ");
		}
		if (lInterestType == SETTConstant.InterestFeeType.ASSURE) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearSureFee < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" and acctype.naccountgroupid <> 6 ");
			}
		}
		if (qInfo.isBInterest()) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.dtClearInterest <= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
			}
		}
		if (lInterestType == SETTConstant.InterestFeeType.COMMISION) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearCommission < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" and acctype.naccountgroupid <> 6 ");
			}
		}
		if (qInfo.isBCompoundInterest()) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearCompound < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" and acctype.naccountgroupid <> 6 ");
			}
		}
		if (lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND( ");
				sbSQL.append(" subaccount.AF_dtPreDraw <= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" or subaccount.AL_dtPreDraw <= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" )  and (subaccount.af_mpredrawinterest > 0 or subaccount.al_mpredrawinterest > 0)");
			}
		}
		if (qInfo.isBForfeitInterest()) {
			if (qInfo.getClearInterest() != null) { // 结息日
				sbSQL.append(" AND ");
				sbSQL.append(" subaccount.AL_dtClearOverDue < to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterest())
						+ "','yyyy-mm-dd')");
				sbSQL.append(" and acctype.naccountgroupid <> 6 ");
			}
		}

		if (qInfo.getIsClearNull() == SETTConstant.BooleanValue.ISTRUE) { // 是否滤空
			sbSQL.append(" AND ");
			sbSQL.append(" subaccount.MBALANCE > 0");
		}

		// 贴现开始日期
		if (qInfo.getDtStartDiscount() != null) {
			sbSQL.append(" and ");
			sbSQL
					.append(" to_char(contractform.dtDiscountDate,'yyyy-mm-dd') >= '"
							+ DataFormat.getDateString(qInfo
									.getDtStartDiscount()) + "'");
		}
		// 贴现结束日期
		if (qInfo.getDtEndDiscount() != null) {
			sbSQL.append(" and ");
			sbSQL
					.append(" to_char(contractform.dtDiscountDate,'yyyy-mm-dd') <= '"
							+ DataFormat
									.getDateString(qInfo.getDtEndDiscount())
							+ "'");
		}

		sbSQL.append(" AND ");
		sbSQL.append(" account.nOfficeID = " + qInfo.getOfficeID());
		sbSQL.append(" AND ");
		sbSQL.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		sbSQL.append(" AND ");
		sbSQL
				.append(" account.nAccountTypeID in (select id from sett_accounttype where nAccountGroupID="
						+ SETTConstant.AccountGroupType.DISCOUNT
						+ " and nstatusId=1 and officeId="
						+ qInfo.getOfficeID()
						+ " and currencyId="
						+ qInfo.getCurrencyID()
						+ ") Order By accountNo, contractNo, payFormNo");
		// 加入贴现记录查询 end ----------

		System.out.println(sbSQL.toString());

		return sbSQL.toString();

	}

	public String getInterestEstimateClientInfoSQL(
			InterestEstimateQueryInfo qInfo) {
		StringBuffer m_sbSelect = new StringBuffer();

		m_sbSelect.append(" distinct client.sname ClientName, \n");
		m_sbSelect.append(" client.scode ClientNo, \n");

		// Boxu Add 只有选择委托方才需要
		if (qInfo.getIsSelectConsigner() > 0) {
			// Added by ylguo at 2009-02-09,用来解决委托方名称显示错误的问题
			m_sbSelect
					.append(" consigner.id ConsignClientID, --增加了委托方的客户id \n");
			m_sbSelect
					.append(" consigner.scode ConsignClientCode, --增加了委托方的客户编号 \n");
			m_sbSelect
					.append(" consigner.sName ConsignClientName, --增加了委托方客户名称 \n");
			// 添加完毕 ylguo
		}

		m_sbSelect.append(" client.ID ClientID \n");

		// from
		StringBuffer m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account, SETT_ACCOUNTTYPE
		// accountType,sett_SubAccount subaccount, client,loan_PayForm payform,
		// loan_ContractForm contractform, loan_LoanForm loanform \n");
		m_sbFrom
				.append(" sett_Account account,SETT_ACCOUNTTYPE accountType, sett_SubAccount subaccount, client,loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client consigner \n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();

		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if (qInfo.getIsSelectConsigner() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");

		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		// 不等于活期
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.CURRENT + " \n");

		// 不等于定期，疑问
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.FIXED + " \n");
		// 不是通知
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.NOTIFY + " \n");
		// 不是贴现
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.DISCOUNT + " \n");
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		if (qInfo.getIsSelectClientNo() > 0) {
			if (qInfo.getClientNoFrom() != null
					&& qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append(" and client.scode>='"
						+ qInfo.getClientNoFrom() + "' \n");
			if (qInfo.getClientNoTo() != null
					&& qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo()
						+ "' \n");
		}
		if (qInfo.getIsSelectClearInterestDate() > 0) {
			if (qInfo.getClearInterestDate() != null) { // 结息日
				m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterestDate())
						+ "','yyyy-mm-dd') \n");
			}
		}

		if (qInfo.getIsSelectSelfLoanSort() > 0
				&& qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0 && qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid in ( "
						+ qInfo.getSelfLoanSort() + ", "
						+ qInfo.getConsignLoanSort() + " ) ");
			} else if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getSelfLoanSort() + "");
			} else if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getConsignLoanSort() + "");
			} else {
				m_sbWhere.append(" and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ", "
						+ LOANConstant.LoanType.WT + ") ");
			}
		}
		// 是否选择自营贷款种类
		else if (qInfo.getIsSelectSelfLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getSelfLoanSort() + "");
			}
			if (qInfo.getSelfLoanSort() == 0 || qInfo.getSelfLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ") ");
			}
		}
		// 是否选择委托贷款种类
		else if (qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getConsignLoanSort() + "");
			}

			if (qInfo.getConsignLoanSort() == 0
					|| qInfo.getConsignLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.WT + ") ");
			}
		}

		if (qInfo.getIsSelectSelfLoanTerm() > 0) {
			if (qInfo.getSelfLoanTermFrom() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum >='"
						+ qInfo.getSelfLoanTermFrom() + "' \n");
			}
			if (qInfo.getSelfLoanTermTo() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum <='"
						+ qInfo.getSelfLoanTermTo() + "' \n");
			}
			// add by kevin(刘连凯)2011-08-01 当选择的自营贷款期限时，合同类型应默认是自营
			m_sbWhere.append("and contractform.ntypeid ="
					+ LOANConstant.LoanType.ZY + " \n");
		}
		if (qInfo.getIsSelectConsigner() > 0) {
			if (qInfo.getConsignerFrom() != null
					&& qInfo.getConsignerFrom().length() > 0) {
				m_sbWhere.append(" and consigner.scode>='"
						+ qInfo.getConsignerFrom() + "' \n");
			}
			if (qInfo.getConsignerTo() != null
					&& qInfo.getConsignerTo().length() > 0) {
				m_sbWhere.append(" and consigner.scode<='"
						+ qInfo.getConsignerTo() + "' \n");
			}
		}
		if (qInfo.getIsSelectContractNo() > 0) {
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "' \n");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "' \n");
			}
		}
		if (qInfo.getIsSelectPayFormNo() > 0) {
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append(" and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "' \n");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append(" and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "' \n");
			}
		}
		if (qInfo.getDoFilterDate() == 1) { // 是否过滤 1 过滤 -1 不过滤
			m_sbWhere.append(" and payform.DTEND >= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterestDate())
					+ "','yyyy-mm-dd') \n");
		}
		return "SELECT " + m_sbSelect + " FROM " + m_sbFrom + " WHERE "
				+ m_sbWhere;
	}

	public String getInterestEstimateAccountInfoSQL(
			InterestEstimateQueryInfo qInfo) {
		StringBuffer m_sbSelect = new StringBuffer();

		m_sbSelect.append(" distinct  account.ID AS accountID, \n");
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect.append(" client.sname ClientName, \n");
		m_sbSelect.append(" client.scode ClientNo, \n");
		m_sbSelect.append(" client.ID ClientID \n");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account,SETT_ACCOUNTTYPE accountType,
		// sett_SubAccount subaccount, client,loan_PayForm payform,
		// loan_ContractForm contractform, loan_LoanForm loanform \n");
		m_sbFrom
				.append(" sett_Account account,SETT_ACCOUNTTYPE accountType, sett_SubAccount subaccount, client,loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client consigner,loan_loantypesetting subtype \n");

		// where
		StringBuffer m_sbWhere = new StringBuffer();

		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nAccountTypeID = accountType.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere
				.append(" payform.nContractID = contractform.ID and subtype.id=contractform.nsubtypeid \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if (qInfo.getIsSelectConsigner() > 0) {

			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");

		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		// 不等于活期
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.CURRENT + " \n");

		// 不等于定期，疑问
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.FIXED + " \n");
		// 不是通知
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.NOTIFY + " \n");
		// 不是贴现
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.DISCOUNT + " \n");
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		if (qInfo.getIsSelectClientNo() > 0) {
			if (qInfo.getClientNoFrom() != null
					&& qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append("and client.scode>='"
						+ qInfo.getClientNoFrom() + "'");
			if (qInfo.getClientNoTo() != null
					&& qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append("and client.scode<='" + qInfo.getClientNoTo()
						+ "'");
		}
		if (qInfo.getIsSelectClearInterestDate() > 0) {
			if (qInfo.getClearInterestDate() != null) { // 结息日
				m_sbWhere.append("and subaccount.dtClearInterest<= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterestDate())
						+ "','yyyy-mm-dd')");
			}
		}

		if (qInfo.getIsSelectSelfLoanSort() > 0
				&& qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0 && qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid in ( "
						+ qInfo.getSelfLoanSort() + ", "
						+ qInfo.getConsignLoanSort() + " ) ");
			} else if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getSelfLoanSort() + "");
			} else if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getConsignLoanSort() + "");
			} else {
				m_sbWhere.append(" and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ", "
						+ LOANConstant.LoanType.WT + ") ");
			}
		}
		// 是否选择自营贷款种类
		else if (qInfo.getIsSelectSelfLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getSelfLoanSort() + "");
			}
			if (qInfo.getSelfLoanSort() == 0 || qInfo.getSelfLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ") ");
			}
		}
		// 是否选择委托贷款种类
		else if (qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getConsignLoanSort() + "");
			}

			if (qInfo.getConsignLoanSort() == 0
					|| qInfo.getConsignLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.WT + ") ");
			}
		}

		if (qInfo.getIsSelectSelfLoanTerm() > 0) {
			if (qInfo.getSelfLoanTermFrom() > 0) {
				m_sbWhere.append("and contractform.nIntervalNum >="
						+ qInfo.getSelfLoanTermFrom() + "");
			}
			if (qInfo.getSelfLoanTermTo() > 0) {
				m_sbWhere.append("and contractform.nIntervalNum <="
						+ qInfo.getSelfLoanTermTo() + "");
			}
			// add by kevin(刘连凯)2011-08-01 当选择的自营贷款期限时，合同类型应默认是自营
			m_sbWhere.append("and contractform.ntypeid ="
					+ LOANConstant.LoanType.ZY + " \n");
		}
		if (qInfo.getIsSelectConsigner() > 0) {
			if (qInfo.getConsignerFrom() != null
					&& qInfo.getConsignerFrom().length() > 0) {
				m_sbWhere.append("and consigner.scode>='"
						+ qInfo.getConsignerFrom() + "'");
			}
			if (qInfo.getConsignerTo() != null
					&& qInfo.getConsignerTo().length() > 0) {
				m_sbWhere.append("and consigner.scode<='"
						+ qInfo.getConsignerTo() + "'");
			}
		}
		if (qInfo.getIsSelectContractNo() > 0) {
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "'");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "'");
			}
		}
		if (qInfo.getIsSelectPayFormNo() > 0) {
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append("and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "'");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append("and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "'");
			}
		}
		if (qInfo.getDoFilterDate() == 1) { // 是否过滤 1 过滤 -1 不过滤
			m_sbWhere.append(" and payform.DTEND >= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterestDate())
					+ "','yyyy-mm-dd') \n");
		}
		return "SELECT " + m_sbSelect + " FROM " + m_sbFrom + " WHERE "
				+ m_sbWhere;
	}

	public String queryLoanNoticeClientYs(InterestEstimateQueryInfo qInfo,
			String ids1) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct  account.ID AS accountID, \n");
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");
		m_sbSelect.append(" account.nOfficeID AS OfficeID, \n");
		m_sbSelect.append(" account.nCurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect
				.append(" subaccount.dtclearinterest as ClearInterestDate, \n");
		m_sbSelect.append(" subaccount.ID AS subAccountID, \n");
		m_sbSelect.append(" subaccount.AL_NLOANNOTEID AS LoanNoteID, \n");
		m_sbSelect.append(" contractform.ID AS ContractID, \n");
		m_sbSelect.append(" contractform.sContractCode AS contractNo, \n");
		m_sbSelect
				.append(" contractform.nBorrowClientID AS BorrowClientID, \n");
		m_sbSelect.append(" contractform.mLoanAmount AS LoanAmount, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID,\n"); // 贷款类型
		// m_sbSelect.append(" contractform.dtStartDate AS LoanStartDate, \n");
		// m_sbSelect.append(" contractform.dtEndDate AS LoanEndDate, \n");
		m_sbSelect.append(" payform.dtStart AS LoanStartDate, \n");
		m_sbSelect.append(" payform.dtEnd AS LoanEndDate, \n");
		m_sbSelect.append(" payform.mAmount AS PayFormAmount, \n");
		m_sbSelect.append(" contractform.nInterValNum as LoanTerm, \n");
		m_sbSelect.append(" payform.sCode AS payFormNo \n");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount,
		// loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm
		// loanform,client \n");
		m_sbFrom
				.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client,client consigner \n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();

		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if (qInfo.getIsSelectConsigner() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");

		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		if (qInfo.getClientNoFrom() != null
				&& qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append(" and client.scode>='" + qInfo.getClientNoFrom()
					+ "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo()
					+ "'");

		if (qInfo.getClearInterestDate() != null) { // 结息日
			m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterestDate())
					+ "','yyyy-mm-dd')");
		}
		if (qInfo.getIsSelectSelfLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0) {
				// m_sbWhere.append(" and loanform.nTypeID<=" +
				// qInfo.getSelfLoanSort() + "");
				// m_sbWhere.append(" and contractform.nTypeID='" +
				// qInfo.getSelfLoanSort() + "' \n");
				// modify by xwhe 查询的条件应该取合同的贷款子类型，不应该取贷款类型
				m_sbWhere.append(" and contractform.nsubtypeid='"
						+ qInfo.getSelfLoanSort() + "' \n");
			}
			if (qInfo.getSelfLoanSort() == 0) {
				m_sbWhere.append("and contractform.nTypeID in (1,2,5,6,7,8) ");
			}
		}
		if (qInfo.getIsSelectSelfLoanTerm() > 0) {
			if (qInfo.getSelfLoanTermFrom() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum >="
						+ qInfo.getSelfLoanTermFrom() + "");
			}
			if (qInfo.getSelfLoanTermTo() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum <="
						+ qInfo.getSelfLoanTermTo() + "");
			}
		}
		if (qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getConsignLoanSort() > 0) {
				// m_sbWhere.append(" and loanform.nTypeID<=" +
				// qInfo.getConsignLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='"
						+ qInfo.getConsignLoanSort() + "' \n");
			}
			if (qInfo.getConsignLoanSort() == 0) {
				m_sbWhere.append("and contractform.nTypeID in (3,4) ");
			}
		}
		if (qInfo.getIsSelectConsigner() > 0) {
			if (qInfo.getConsignerFrom() != null
					&& qInfo.getConsignerFrom().length() > 0) {
				m_sbWhere.append(" and consigner.scode>='"
						+ qInfo.getConsignerFrom() + "'");
			}
			if (qInfo.getConsignerTo() != null
					&& qInfo.getConsignerTo().length() > 0) {
				m_sbWhere.append(" and consigner.scode<='"
						+ qInfo.getConsignerTo() + "'");
			}
		}
		if (qInfo.getIsSelectContractNo() > 0) {
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "'");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "'");
			}
		}
		if (qInfo.getIsSelectPayFormNo() > 0) {
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append(" and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "'");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append(" and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "'");
			}
		}
		if (ids1 != null && ids1.length() > 0) {
			String[] ids = ids1.split(",");
			for (int i = 0; i < ids.length; i++) {
				if (ids.length > 1) {
					if (i == 0) {
						m_sbWhere
								.append(" and (client.id = " + ids[i] + "  \n");
					} else if (i == ids.length - 1) {
						m_sbWhere.append(" or client.id = " + ids[i] + ")  \n");
					} else {
						m_sbWhere.append(" or client.id = " + ids[i] + "  \n");
					}
				} else {
					m_sbWhere.append(" and client.id = " + ids[0] + "  \n");
				}
			}
		}
		return "SELECT rownum as ROWNUM_1,a.* from(select " + m_sbSelect + " FROM " + m_sbFrom + " WHERE "
				+ m_sbWhere+") a ";
	}

	public String queryLoanNoticeAccountYs(InterestEstimateQueryInfo qInfo,
			String  ids1) {
		StringBuffer m_sbSelect = new StringBuffer();

		m_sbSelect.append(" distinct  account.ID AS accountID, \n");
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");
		m_sbSelect.append(" account.nOfficeID AS OfficeID, \n");
		m_sbSelect.append(" account.nCurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect
				.append(" subaccount.dtclearinterest as ClearInterestDate, \n");
		m_sbSelect.append(" subaccount.ID AS subAccountID, \n");
		m_sbSelect.append(" subaccount.AL_NLOANNOTEID AS LoanNoteID, \n");
		m_sbSelect.append(" contractform.ID AS ContractID, \n");
		m_sbSelect.append(" contractform.sContractCode AS contractNo, \n");
		m_sbSelect
				.append(" contractform.nBorrowClientID AS BorrowClientID, \n");
		m_sbSelect.append(" contractform.mLoanAmount AS LoanAmount, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID,\n"); // 贷款类型
		// m_sbSelect.append(" contractform.dtStartDate AS LoanStartDate, \n");
		// m_sbSelect.append(" contractform.dtEndDate AS LoanEndDate, \n");
		m_sbSelect.append(" payform.dtStart AS LoanStartDate, \n");
		m_sbSelect.append(" payform.dtEnd AS LoanEndDate, \n");
		m_sbSelect.append(" payform.mAmount AS PayFormAmount, \n");
		m_sbSelect.append(" contractform.nInterValNum as LoanTerm, \n");
		m_sbSelect.append(" payform.sCode AS payFormNo \n");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount,
		// loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm
		// loanform,client \n");
		m_sbFrom
				.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client,client consigner \n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();

		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if (qInfo.getIsSelectConsigner() > 0) {

			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");

		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		if (qInfo.getClientNoFrom() != null
				&& qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append(" and client.scode>='" + qInfo.getClientNoFrom()
					+ "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo()
					+ "'");

		if (qInfo.getClearInterestDate() != null) { // 结息日
			m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterestDate())
					+ "','yyyy-mm-dd')");
		}
		if (qInfo.getIsSelectSelfLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0) {
				// m_sbWhere.append(" and loanform.nTypeID<=" +
				// qInfo.getSelfLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='"
						+ qInfo.getSelfLoanSort() + "' \n");
			}
			if (qInfo.getSelfLoanSort() == 0) {
				m_sbWhere.append("and contractform.nTypeID in (1,2,5,6,7,8) ");
			}
		}
		if (qInfo.getIsSelectSelfLoanTerm() > 0) {
			if (qInfo.getSelfLoanTermFrom() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum >="
						+ qInfo.getSelfLoanTermFrom() + "");
			}
			if (qInfo.getSelfLoanTermTo() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum <="
						+ qInfo.getSelfLoanTermTo() + "");
			}
		}
		if (qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getConsignLoanSort() > 0) {
				// m_sbWhere.append(" and loanform.nTypeID<=" +
				// qInfo.getConsignLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='"
						+ qInfo.getConsignLoanSort() + "' \n");
			}
			if (qInfo.getConsignLoanSort() == 0) {
				m_sbWhere.append("and contractform.nTypeID in (3,4) ");
			}
		}
		if (qInfo.getIsSelectConsigner() > 0) {
			if (qInfo.getConsignerFrom() != null
					&& qInfo.getConsignerFrom().length() > 0) {
				m_sbWhere.append(" and consigner.scode>='"
						+ qInfo.getConsignerFrom() + "'");
			}
			if (qInfo.getConsignerTo() != null
					&& qInfo.getConsignerTo().length() > 0) {
				m_sbWhere.append(" and consigner.scode<='"
						+ qInfo.getConsignerTo() + "'");
			}
		}
		if (qInfo.getIsSelectContractNo() > 0) {
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "'");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "'");
			}
		}
		if (qInfo.getIsSelectPayFormNo() > 0) {
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append(" and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "'");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append(" and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "'");
			}
		}
		if (ids1 != null && ids1.length() > 0) {
			String[] ids = ids1.split(",");
			for (int i = 0; i < ids.length; i++) {
				if (ids.length > 1) {
					if (i == 0) {
						m_sbWhere.append(" and (account.id = " + ids[i]
								+ "  \n");
					} else if (i == ids.length - 1) {
						m_sbWhere
								.append(" or account.id = " + ids[i] + ")  \n");
					} else {
						m_sbWhere.append(" or account.id = " + ids[i] + "  \n");
					}
				} else {
					m_sbWhere.append(" and account.id = " + ids[0] + "  \n");
				}
			}
		}

		return "select rownum as ROWNUM_1,a.* from(select " + m_sbSelect.toString() + "from "
				+ m_sbFrom.toString() + "where " + m_sbWhere.toString()+") a ";
	}
}
