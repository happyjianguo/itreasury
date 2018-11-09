/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;
import java.sql.Timestamp;
/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

 
public class SpecialOperationInfo implements java.io.Serializable
{
      /**
       * SpecialOperationInfo 构造子注解。
       */
      public long m_lID = -1;
      public long m_lOfficeID = -1;
      public long m_lCurrencyID = -1;
      public String m_strName = "";
      public String m_strContent = "";
      public long m_lStatus = -1;
      
      public long m_Ispayaccount = -1;//是否显示付款方客户信息
      public long m_Ispaybank = -1;//是否显示付款方开户行信息
      public long m_IspayGlEntry = -1;//是否显示付款方总账信息
      public long m_IsRecAccount = -1;//是否显示收款方客户信息
      public long m_IsRecBank = -1;//是否显示收款方开户行信息
      public long m_IsRecGlEntry = -1;//是否显示收款方总账信息      
      
      public long payRelation = 0;
      public long gatheringRelation = 0;
      
      public long m_lPageCount = -1;
      
      public long lID;	//标识
      public long lTransactionNoID;	//交易号标识
      public String strTransactionNo; //交易号
      public long lOperationTypeID;	//特殊业务类型标识
      public String strOperationType;
      
      public long lPayClientID;	//付款方客户标识
      public String strPayClientCode;    //付款方客户编号
      public String strPayClientName;    //付款方客户
      public long lPayAccountID;	//付款方账户标识
      public String strPayAccountNo;    //付款方账户号
      public long lPayFixedDepositID;	//付款方定期账户存单标识
      public String strPayFixedDeposit;
      public long lPayContractID;	//付款方合同号标识
      public String strPayContract;
      public long lPayDueBillID;	//付款方贷款借据标识
      public String strPayDueBill;
      public long lPayBankID;	//付款方开户行标识
      public String strPayBank;
      public long lPayCashFlowID;	//付款方现金流向标识
      public double dPay;	//付款方金额
      public double dPayInterest;	//付款方利息
      public long lPayDirection;	//付款方借贷标识
      
      public long lRepaymentClientID;	//收款方客户标识
      public String strRepaymentClientCode; 	//收款方客户编号
      public String strRepaymentClientName; 	//收款方客户
      public long lRepaymentAccountID;	//收款方账户标识
      public String strRepaymentAccountNo;    //收款方账户号
      public long lRepaymentFixedDepositID;	//收款方定期账户存单标识
      public String strRepaymentFixedDeposit;
      public long lRepaymentContractID;	//收款方合同标识
      public String strRepaymentContract;
      public long lRepaymentDueBillID;	//收款方贷款借据标识
      public String strRepaymentDueBill;
      public long lRepaymentBankID;	//收款方开户行标识
      public String strRepaymentBank;
      public long lRepaymentCashFlowID;	//收款方现金流向标识
      public double dRepayment;	//收款方金额
      public double dRepaymentInterest;	//收款方利息
      public long lRepaymentDirection;	//收款方借贷标识
      
      public String strVoucherNo;	//委托付款凭证号
      public String strVoucherPwd;	//委托付款凭证密码
      
      public String strBankAccount;	//银行账户号
      public String strBankClient;	//银行账户客户名称
      public String strBankChequeNo;	//银行支票号
      public String strDeclarationNo;	//银行报单号
      public String strRemitInProvince;	//汇入地(省)
      public String strRemitInCity;	// 汇入地(市)
      public String strRemitInBank;	//汇入行
      
      public Timestamp tsInterestStart;	//起息日
      public Timestamp tsExecute;	//执行日
      public String strAbstract;	//摘要
      
      public long lInputUserID;	//录入人标识
      public String strInputUserName;    //录入人
      public Timestamp tsInput;	//录入日期
      public long lModifyUserID;  //修改人标识
      public String strModifyUserName;    //修改人
      public Timestamp tsModify;  //修改时间
      public long lCheckUserID;  //复核人标识
      public String strCheckUserName;    //复核人
      public Timestamp tsCheck;  //复核时间
      public String strCheckNote; //复核备注
      public long lAffirmUserID;  //确认人标识
      public String strAffirmUserName;    //确认人
      public Timestamp tsAffirm;  //确认时间
      public String strAffirmNote; //确认备注
      public long lStatusID;  //状态标识
      
      public String m_strPayLetOutRequisitionNo = "";
      public String m_strRepaymentLetOutRequisitionNo = "";
      
      public long lPageCount; //页数
      
    public Timestamp m_dtLoanInfoStartDate=null;//放款收回凭证打印所用放出日期  xrzhang
    public Timestamp m_dtLoanInfoEndDate=null;//放款收回凭证打印所用放出日期  xrzhang
    public double m_dRemainBalance = 0.00; ////凭证产生时结欠金额  xrzhang
    
    public String m_strRepaymentSubjectCode = "";
    public String m_strPaySubjectCode = "";
    public long m_lGeneralLedgerPay = -1;
    public long m_lGeneralLedgerRepay = -1;
    public String m_strGeneralLedgerPay = "";
    public String m_strGeneralLedgerRepay = "";
    public String m_strGeneralLedgerPayCode = "";
    public String m_strGeneralLedgerRepayCode = "";
    
    public long m_lPaySingleAccountID=-1;
    public long m_lRepaymentSingleAccountID=-1;
      
	/**
	 * @return Returns the dPay.
	 */
	public double getDPay() {
		return dPay;
	}
	/**
	 * @param pay The dPay to set.
	 */
	public void setDPay(double pay) {
		dPay = pay;
	}
	/**
	 * @return Returns the dPayInterest.
	 */
	public double getDPayInterest() {
		return dPayInterest;
	}
	/**
	 * @param payInterest The dPayInterest to set.
	 */
	public void setDPayInterest(double payInterest) {
		dPayInterest = payInterest;
	}
	/**
	 * @return Returns the dRepayment.
	 */
	public double getDRepayment() {
		return dRepayment;
	}
	/**
	 * @param repayment The dRepayment to set.
	 */
	public void setDRepayment(double repayment) {
		dRepayment = repayment;
	}
	/**
	 * @return Returns the dRepaymentInterest.
	 */
	public double getDRepaymentInterest() {
		return dRepaymentInterest;
	}
	/**
	 * @param repaymentInterest The dRepaymentInterest to set.
	 */
	public void setDRepaymentInterest(double repaymentInterest) {
		dRepaymentInterest = repaymentInterest;
	}
	/**
	 * @return Returns the lAffirmUserID.
	 */
	public long getLAffirmUserID() {
		return lAffirmUserID;
	}
	/**
	 * @param affirmUserID The lAffirmUserID to set.
	 */
	public void setLAffirmUserID(long affirmUserID) {
		lAffirmUserID = affirmUserID;
	}
	/**
	 * @return Returns the lCheckUserID.
	 */
	public long getLCheckUserID() {
		return lCheckUserID;
	}
	/**
	 * @param checkUserID The lCheckUserID to set.
	 */
	public void setLCheckUserID(long checkUserID) {
		lCheckUserID = checkUserID;
	}
	/**
	 * @return Returns the lID.
	 */
	public long getLID() {
		return lID;
	}
	/**
	 * @param lid The lID to set.
	 */
	public void setLID(long lid) {
		lID = lid;
	}
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getLInputUserID() {
		return lInputUserID;
	}
	/**
	 * @param inputUserID The lInputUserID to set.
	 */
	public void setLInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}
	/**
	 * @return Returns the lModifyUserID.
	 */
	public long getLModifyUserID() {
		return lModifyUserID;
	}
	/**
	 * @param modifyUserID The lModifyUserID to set.
	 */
	public void setLModifyUserID(long modifyUserID) {
		lModifyUserID = modifyUserID;
	}
	/**
	 * @return Returns the lOperationTypeID.
	 */
	public long getLOperationTypeID() {
		return lOperationTypeID;
	}
	/**
	 * @param operationTypeID The lOperationTypeID to set.
	 */
	public void setLOperationTypeID(long operationTypeID) {
		lOperationTypeID = operationTypeID;
	}
	/**
	 * @return Returns the lPageCount.
	 */
	public long getLPageCount() {
		return lPageCount;
	}
	/**
	 * @param pageCount The lPageCount to set.
	 */
	public void setLPageCount(long pageCount) {
		lPageCount = pageCount;
	}
	/**
	 * @return Returns the lPayAccountID.
	 */
	public long getLPayAccountID() {
		return lPayAccountID;
	}
	/**
	 * @param payAccountID The lPayAccountID to set.
	 */
	public void setLPayAccountID(long payAccountID) {
		lPayAccountID = payAccountID;
	}
	/**
	 * @return Returns the lPayBankID.
	 */
	public long getLPayBankID() {
		return lPayBankID;
	}
	/**
	 * @param payBankID The lPayBankID to set.
	 */
	public void setLPayBankID(long payBankID) {
		lPayBankID = payBankID;
	}
	/**
	 * @return Returns the lPayCashFlowID.
	 */
	public long getLPayCashFlowID() {
		return lPayCashFlowID;
	}
	/**
	 * @param payCashFlowID The lPayCashFlowID to set.
	 */
	public void setLPayCashFlowID(long payCashFlowID) {
		lPayCashFlowID = payCashFlowID;
	}
	/**
	 * @return Returns the lPayClientID.
	 */
	public long getLPayClientID() {
		return lPayClientID;
	}
	/**
	 * @param payClientID The lPayClientID to set.
	 */
	public void setLPayClientID(long payClientID) {
		lPayClientID = payClientID;
	}
	/**
	 * @return Returns the lPayContractID.
	 */
	public long getLPayContractID() {
		return lPayContractID;
	}
	/**
	 * @param payContractID The lPayContractID to set.
	 */
	public void setLPayContractID(long payContractID) {
		lPayContractID = payContractID;
	}
	/**
	 * @return Returns the lPayDirection.
	 */
	public long getLPayDirection() {
		return lPayDirection;
	}
	/**
	 * @param payDirection The lPayDirection to set.
	 */
	public void setLPayDirection(long payDirection) {
		lPayDirection = payDirection;
	}
	/**
	 * @return Returns the lPayDueBillID.
	 */
	public long getLPayDueBillID() {
		return lPayDueBillID;
	}
	/**
	 * @param payDueBillID The lPayDueBillID to set.
	 */
	public void setLPayDueBillID(long payDueBillID) {
		lPayDueBillID = payDueBillID;
	}
	/**
	 * @return Returns the lPayFixedDepositID.
	 */
	public long getLPayFixedDepositID() {
		return lPayFixedDepositID;
	}
	/**
	 * @param payFixedDepositID The lPayFixedDepositID to set.
	 */
	public void setLPayFixedDepositID(long payFixedDepositID) {
		lPayFixedDepositID = payFixedDepositID;
	}
	/**
	 * @return Returns the lRepaymentAccountID.
	 */
	public long getLRepaymentAccountID() {
		return lRepaymentAccountID;
	}
	/**
	 * @param repaymentAccountID The lRepaymentAccountID to set.
	 */
	public void setLRepaymentAccountID(long repaymentAccountID) {
		lRepaymentAccountID = repaymentAccountID;
	}
	/**
	 * @return Returns the lRepaymentBankID.
	 */
	public long getLRepaymentBankID() {
		return lRepaymentBankID;
	}
	/**
	 * @param repaymentBankID The lRepaymentBankID to set.
	 */
	public void setLRepaymentBankID(long repaymentBankID) {
		lRepaymentBankID = repaymentBankID;
	}
	/**
	 * @return Returns the lRepaymentCashFlowID.
	 */
	public long getLRepaymentCashFlowID() {
		return lRepaymentCashFlowID;
	}
	/**
	 * @param repaymentCashFlowID The lRepaymentCashFlowID to set.
	 */
	public void setLRepaymentCashFlowID(long repaymentCashFlowID) {
		lRepaymentCashFlowID = repaymentCashFlowID;
	}
	/**
	 * @return Returns the lRepaymentClientID.
	 */
	public long getLRepaymentClientID() {
		return lRepaymentClientID;
	}
	/**
	 * @param repaymentClientID The lRepaymentClientID to set.
	 */
	public void setLRepaymentClientID(long repaymentClientID) {
		lRepaymentClientID = repaymentClientID;
	}
	/**
	 * @return Returns the lRepaymentContractID.
	 */
	public long getLRepaymentContractID() {
		return lRepaymentContractID;
	}
	/**
	 * @param repaymentContractID The lRepaymentContractID to set.
	 */
	public void setLRepaymentContractID(long repaymentContractID) {
		lRepaymentContractID = repaymentContractID;
	}
	/**
	 * @return Returns the lRepaymentDirection.
	 */
	public long getLRepaymentDirection() {
		return lRepaymentDirection;
	}
	/**
	 * @param repaymentDirection The lRepaymentDirection to set.
	 */
	public void setLRepaymentDirection(long repaymentDirection) {
		lRepaymentDirection = repaymentDirection;
	}
	/**
	 * @return Returns the lRepaymentDueBillID.
	 */
	public long getLRepaymentDueBillID() {
		return lRepaymentDueBillID;
	}
	/**
	 * @param repaymentDueBillID The lRepaymentDueBillID to set.
	 */
	public void setLRepaymentDueBillID(long repaymentDueBillID) {
		lRepaymentDueBillID = repaymentDueBillID;
	}
	/**
	 * @return Returns the lRepaymentFixedDepositID.
	 */
	public long getLRepaymentFixedDepositID() {
		return lRepaymentFixedDepositID;
	}
	/**
	 * @param repaymentFixedDepositID The lRepaymentFixedDepositID to set.
	 */
	public void setLRepaymentFixedDepositID(long repaymentFixedDepositID) {
		lRepaymentFixedDepositID = repaymentFixedDepositID;
	}
	/**
	 * @return Returns the lStatusID.
	 */
	public long getLStatusID() {
		return lStatusID;
	}
	/**
	 * @param statusID The lStatusID to set.
	 */
	public void setLStatusID(long statusID) {
		lStatusID = statusID;
	}
	/**
	 * @return Returns the lTransactionNoID.
	 */
	public long getLTransactionNoID() {
		return lTransactionNoID;
	}
	/**
	 * @param transactionNoID The lTransactionNoID to set.
	 */
	public void setLTransactionNoID(long transactionNoID) {
		lTransactionNoID = transactionNoID;
	}
	/**
	 * @return Returns the m_dRemainBalance.
	 */
	public double getM_dRemainBalance() {
		return m_dRemainBalance;
	}
	/**
	 * @param remainBalance The m_dRemainBalance to set.
	 */
	public void setM_dRemainBalance(double remainBalance) {
		m_dRemainBalance = remainBalance;
	}
	/**
	 * @return Returns the m_dtLoanInfoEndDate.
	 */
	public Timestamp getM_dtLoanInfoEndDate() {
		return m_dtLoanInfoEndDate;
	}
	/**
	 * @param loanInfoEndDate The m_dtLoanInfoEndDate to set.
	 */
	public void setM_dtLoanInfoEndDate(Timestamp loanInfoEndDate) {
		m_dtLoanInfoEndDate = loanInfoEndDate;
	}
	/**
	 * @return Returns the m_dtLoanInfoStartDate.
	 */
	public Timestamp getM_dtLoanInfoStartDate() {
		return m_dtLoanInfoStartDate;
	}
	/**
	 * @param loanInfoStartDate The m_dtLoanInfoStartDate to set.
	 */
	public void setM_dtLoanInfoStartDate(Timestamp loanInfoStartDate) {
		m_dtLoanInfoStartDate = loanInfoStartDate;
	}
	/**
	 * @return Returns the m_lCurrencyID.
	 */
	public long getM_lCurrencyID() {
		return m_lCurrencyID;
	}
	/**
	 * @param currencyID The m_lCurrencyID to set.
	 */
	public void setM_lCurrencyID(long currencyID) {
		m_lCurrencyID = currencyID;
	}
	/**
	 * @return Returns the m_lGeneralLedgerPay.
	 */
	public long getM_lGeneralLedgerPay() {
		return m_lGeneralLedgerPay;
	}
	/**
	 * @param generalLedgerPay The m_lGeneralLedgerPay to set.
	 */
	public void setM_lGeneralLedgerPay(long generalLedgerPay) {
		m_lGeneralLedgerPay = generalLedgerPay;
	}
	/**
	 * @return Returns the m_lGeneralLedgerRepay.
	 */
	public long getM_lGeneralLedgerRepay() {
		return m_lGeneralLedgerRepay;
	}
	/**
	 * @param generalLedgerRepay The m_lGeneralLedgerRepay to set.
	 */
	public void setM_lGeneralLedgerRepay(long generalLedgerRepay) {
		m_lGeneralLedgerRepay = generalLedgerRepay;
	}
	/**
	 * @return Returns the m_lID.
	 */
	public long getM_lID() {
		return m_lID;
	}
	/**
	 * @param m_lid The m_lID to set.
	 */
	public void setM_lID(long m_lid) {
		m_lID = m_lid;
	}
	/**
	 * @return Returns the m_lOfficeID.
	 */
	public long getM_lOfficeID() {
		return m_lOfficeID;
	}
	/**
	 * @param officeID The m_lOfficeID to set.
	 */
	public void setM_lOfficeID(long officeID) {
		m_lOfficeID = officeID;
	}
	/**
	 * @return Returns the m_lPageCount.
	 */
	public long getM_lPageCount() {
		return m_lPageCount;
	}
	/**
	 * @param pageCount The m_lPageCount to set.
	 */
	public void setM_lPageCount(long pageCount) {
		m_lPageCount = pageCount;
	}
	/**
	 * @return Returns the m_lPaySingleAccountID.
	 */
	public long getM_lPaySingleAccountID() {
		return m_lPaySingleAccountID;
	}
	/**
	 * @param paySingleAccountID The m_lPaySingleAccountID to set.
	 */
	public void setM_lPaySingleAccountID(long paySingleAccountID) {
		m_lPaySingleAccountID = paySingleAccountID;
	}
	/**
	 * @return Returns the m_lRepaymentSingleAccountID.
	 */
	public long getM_lRepaymentSingleAccountID() {
		return m_lRepaymentSingleAccountID;
	}
	/**
	 * @param repaymentSingleAccountID The m_lRepaymentSingleAccountID to set.
	 */
	public void setM_lRepaymentSingleAccountID(long repaymentSingleAccountID) {
		m_lRepaymentSingleAccountID = repaymentSingleAccountID;
	}
	/**
	 * @return Returns the m_lStatus.
	 */
	public long getM_lStatus() {
		return m_lStatus;
	}
	/**
	 * @param status The m_lStatus to set.
	 */
	public void setM_lStatus(long status) {
		m_lStatus = status;
	}
	/**
	 * @return Returns the m_strContent.
	 */
	public String getM_strContent() {
		return m_strContent;
	}
	/**
	 * @param content The m_strContent to set.
	 */
	public void setM_strContent(String content) {
		m_strContent = content;
	}
	/**
	 * @return Returns the m_strGeneralLedgerPay.
	 */
	public String getM_strGeneralLedgerPay() {
		return m_strGeneralLedgerPay;
	}
	/**
	 * @param generalLedgerPay The m_strGeneralLedgerPay to set.
	 */
	public void setM_strGeneralLedgerPay(String generalLedgerPay) {
		m_strGeneralLedgerPay = generalLedgerPay;
	}
	/**
	 * @return Returns the m_strGeneralLedgerPayCode.
	 */
	public String getM_strGeneralLedgerPayCode() {
		return m_strGeneralLedgerPayCode;
	}
	/**
	 * @param generalLedgerPayCode The m_strGeneralLedgerPayCode to set.
	 */
	public void setM_strGeneralLedgerPayCode(String generalLedgerPayCode) {
		m_strGeneralLedgerPayCode = generalLedgerPayCode;
	}
	/**
	 * @return Returns the m_strGeneralLedgerRepay.
	 */
	public String getM_strGeneralLedgerRepay() {
		return m_strGeneralLedgerRepay;
	}
	/**
	 * @param generalLedgerRepay The m_strGeneralLedgerRepay to set.
	 */
	public void setM_strGeneralLedgerRepay(String generalLedgerRepay) {
		m_strGeneralLedgerRepay = generalLedgerRepay;
	}
	/**
	 * @return Returns the m_strGeneralLedgerRepayCode.
	 */
	public String getM_strGeneralLedgerRepayCode() {
		return m_strGeneralLedgerRepayCode;
	}
	/**
	 * @param generalLedgerRepayCode The m_strGeneralLedgerRepayCode to set.
	 */
	public void setM_strGeneralLedgerRepayCode(String generalLedgerRepayCode) {
		m_strGeneralLedgerRepayCode = generalLedgerRepayCode;
	}
	/**
	 * @return Returns the m_strName.
	 */
	public String getM_strName() {
		return m_strName;
	}
	/**
	 * @param name The m_strName to set.
	 */
	public void setM_strName(String name) {
		m_strName = name;
	}
	/**
	 * @return Returns the m_strPayLetOutRequisitionNo.
	 */
	public String getM_strPayLetOutRequisitionNo() {
		return m_strPayLetOutRequisitionNo;
	}
	/**
	 * @param payLetOutRequisitionNo The m_strPayLetOutRequisitionNo to set.
	 */
	public void setM_strPayLetOutRequisitionNo(String payLetOutRequisitionNo) {
		m_strPayLetOutRequisitionNo = payLetOutRequisitionNo;
	}
	/**
	 * @return Returns the m_strPaySubjectCode.
	 */
	public String getM_strPaySubjectCode() {
		return m_strPaySubjectCode;
	}
	/**
	 * @param paySubjectCode The m_strPaySubjectCode to set.
	 */
	public void setM_strPaySubjectCode(String paySubjectCode) {
		m_strPaySubjectCode = paySubjectCode;
	}
	/**
	 * @return Returns the m_strRepaymentLetOutRequisitionNo.
	 */
	public String getM_strRepaymentLetOutRequisitionNo() {
		return m_strRepaymentLetOutRequisitionNo;
	}
	/**
	 * @param repaymentLetOutRequisitionNo The m_strRepaymentLetOutRequisitionNo to set.
	 */
	public void setM_strRepaymentLetOutRequisitionNo(
			String repaymentLetOutRequisitionNo) {
		m_strRepaymentLetOutRequisitionNo = repaymentLetOutRequisitionNo;
	}
	/**
	 * @return Returns the m_strRepaymentSubjectCode.
	 */
	public String getM_strRepaymentSubjectCode() {
		return m_strRepaymentSubjectCode;
	}
	/**
	 * @param repaymentSubjectCode The m_strRepaymentSubjectCode to set.
	 */
	public void setM_strRepaymentSubjectCode(String repaymentSubjectCode) {
		m_strRepaymentSubjectCode = repaymentSubjectCode;
	}
	/**
	 * @return Returns the strAbstract.
	 */
	public String getStrAbstract() {
		return strAbstract;
	}
	/**
	 * @param strAbstract The strAbstract to set.
	 */
	public void setStrAbstract(String strAbstract) {
		this.strAbstract = strAbstract;
	}
	/**
	 * @return Returns the strAffirmNote.
	 */
	public String getStrAffirmNote() {
		return strAffirmNote;
	}
	/**
	 * @param strAffirmNote The strAffirmNote to set.
	 */
	public void setStrAffirmNote(String strAffirmNote) {
		this.strAffirmNote = strAffirmNote;
	}
	/**
	 * @return Returns the strAffirmUserName.
	 */
	public String getStrAffirmUserName() {
		return strAffirmUserName;
	}
	/**
	 * @param strAffirmUserName The strAffirmUserName to set.
	 */
	public void setStrAffirmUserName(String strAffirmUserName) {
		this.strAffirmUserName = strAffirmUserName;
	}
	/**
	 * @return Returns the strBankAccount.
	 */
	public String getStrBankAccount() {
		return strBankAccount;
	}
	/**
	 * @param strBankAccount The strBankAccount to set.
	 */
	public void setStrBankAccount(String strBankAccount) {
		this.strBankAccount = strBankAccount;
	}
	/**
	 * @return Returns the strBankChequeNo.
	 */
	public String getStrBankChequeNo() {
		return strBankChequeNo;
	}
	/**
	 * @param strBankChequeNo The strBankChequeNo to set.
	 */
	public void setStrBankChequeNo(String strBankChequeNo) {
		this.strBankChequeNo = strBankChequeNo;
	}
	/**
	 * @return Returns the strBankClient.
	 */
	public String getStrBankClient() {
		return strBankClient;
	}
	/**
	 * @param strBankClient The strBankClient to set.
	 */
	public void setStrBankClient(String strBankClient) {
		this.strBankClient = strBankClient;
	}
	/**
	 * @return Returns the strCheckNote.
	 */
	public String getStrCheckNote() {
		return strCheckNote;
	}
	/**
	 * @param strCheckNote The strCheckNote to set.
	 */
	public void setStrCheckNote(String strCheckNote) {
		this.strCheckNote = strCheckNote;
	}
	/**
	 * @return Returns the strCheckUserName.
	 */
	public String getStrCheckUserName() {
		return strCheckUserName;
	}
	/**
	 * @param strCheckUserName The strCheckUserName to set.
	 */
	public void setStrCheckUserName(String strCheckUserName) {
		this.strCheckUserName = strCheckUserName;
	}
	/**
	 * @return Returns the strDeclarationNo.
	 */
	public String getStrDeclarationNo() {
		return strDeclarationNo;
	}
	/**
	 * @param strDeclarationNo The strDeclarationNo to set.
	 */
	public void setStrDeclarationNo(String strDeclarationNo) {
		this.strDeclarationNo = strDeclarationNo;
	}
	/**
	 * @return Returns the strInputUserName.
	 */
	public String getStrInputUserName() {
		return strInputUserName;
	}
	/**
	 * @param strInputUserName The strInputUserName to set.
	 */
	public void setStrInputUserName(String strInputUserName) {
		this.strInputUserName = strInputUserName;
	}
	/**
	 * @return Returns the strModifyUserName.
	 */
	public String getStrModifyUserName() {
		return strModifyUserName;
	}
	/**
	 * @param strModifyUserName The strModifyUserName to set.
	 */
	public void setStrModifyUserName(String strModifyUserName) {
		this.strModifyUserName = strModifyUserName;
	}
	/**
	 * @return Returns the strOperationType.
	 */
	public String getStrOperationType() {
		return strOperationType;
	}
	/**
	 * @param strOperationType The strOperationType to set.
	 */
	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}
	/**
	 * @return Returns the strPayAccountNo.
	 */
	public String getStrPayAccountNo() {
		return strPayAccountNo;
	}
	/**
	 * @param strPayAccountNo The strPayAccountNo to set.
	 */
	public void setStrPayAccountNo(String strPayAccountNo) {
		this.strPayAccountNo = strPayAccountNo;
	}
	/**
	 * @return Returns the strPayBank.
	 */
	public String getStrPayBank() {
		return strPayBank;
	}
	/**
	 * @param strPayBank The strPayBank to set.
	 */
	public void setStrPayBank(String strPayBank) {
		this.strPayBank = strPayBank;
	}
	/**
	 * @return Returns the strPayClientCode.
	 */
	public String getStrPayClientCode() {
		return strPayClientCode;
	}
	/**
	 * @param strPayClientCode The strPayClientCode to set.
	 */
	public void setStrPayClientCode(String strPayClientCode) {
		this.strPayClientCode = strPayClientCode;
	}
	/**
	 * @return Returns the strPayClientName.
	 */
	public String getStrPayClientName() {
		return strPayClientName;
	}
	/**
	 * @param strPayClientName The strPayClientName to set.
	 */
	public void setStrPayClientName(String strPayClientName) {
		this.strPayClientName = strPayClientName;
	}
	/**
	 * @return Returns the strPayContract.
	 */
	public String getStrPayContract() {
		return strPayContract;
	}
	/**
	 * @param strPayContract The strPayContract to set.
	 */
	public void setStrPayContract(String strPayContract) {
		this.strPayContract = strPayContract;
	}
	/**
	 * @return Returns the strPayDueBill.
	 */
	public String getStrPayDueBill() {
		return strPayDueBill;
	}
	/**
	 * @param strPayDueBill The strPayDueBill to set.
	 */
	public void setStrPayDueBill(String strPayDueBill) {
		this.strPayDueBill = strPayDueBill;
	}
	/**
	 * @return Returns the strPayFixedDeposit.
	 */
	public String getStrPayFixedDeposit() {
		return strPayFixedDeposit;
	}
	/**
	 * @param strPayFixedDeposit The strPayFixedDeposit to set.
	 */
	public void setStrPayFixedDeposit(String strPayFixedDeposit) {
		this.strPayFixedDeposit = strPayFixedDeposit;
	}
	/**
	 * @return Returns the strRemitInBank.
	 */
	public String getStrRemitInBank() {
		return strRemitInBank;
	}
	/**
	 * @param strRemitInBank The strRemitInBank to set.
	 */
	public void setStrRemitInBank(String strRemitInBank) {
		this.strRemitInBank = strRemitInBank;
	}
	/**
	 * @return Returns the strRemitInCity.
	 */
	public String getStrRemitInCity() {
		return strRemitInCity;
	}
	/**
	 * @param strRemitInCity The strRemitInCity to set.
	 */
	public void setStrRemitInCity(String strRemitInCity) {
		this.strRemitInCity = strRemitInCity;
	}
	/**
	 * @return Returns the strRemitInProvince.
	 */
	public String getStrRemitInProvince() {
		return strRemitInProvince;
	}
	/**
	 * @param strRemitInProvince The strRemitInProvince to set.
	 */
	public void setStrRemitInProvince(String strRemitInProvince) {
		this.strRemitInProvince = strRemitInProvince;
	}
	/**
	 * @return Returns the strRepaymentAccountNo.
	 */
	public String getStrRepaymentAccountNo() {
		return strRepaymentAccountNo;
	}
	/**
	 * @param strRepaymentAccountNo The strRepaymentAccountNo to set.
	 */
	public void setStrRepaymentAccountNo(String strRepaymentAccountNo) {
		this.strRepaymentAccountNo = strRepaymentAccountNo;
	}
	/**
	 * @return Returns the strRepaymentBank.
	 */
	public String getStrRepaymentBank() {
		return strRepaymentBank;
	}
	/**
	 * @param strRepaymentBank The strRepaymentBank to set.
	 */
	public void setStrRepaymentBank(String strRepaymentBank) {
		this.strRepaymentBank = strRepaymentBank;
	}
	/**
	 * @return Returns the strRepaymentClientCode.
	 */
	public String getStrRepaymentClientCode() {
		return strRepaymentClientCode;
	}
	/**
	 * @param strRepaymentClientCode The strRepaymentClientCode to set.
	 */
	public void setStrRepaymentClientCode(String strRepaymentClientCode) {
		this.strRepaymentClientCode = strRepaymentClientCode;
	}
	/**
	 * @return Returns the strRepaymentClientName.
	 */
	public String getStrRepaymentClientName() {
		return strRepaymentClientName;
	}
	/**
	 * @param strRepaymentClientName The strRepaymentClientName to set.
	 */
	public void setStrRepaymentClientName(String strRepaymentClientName) {
		this.strRepaymentClientName = strRepaymentClientName;
	}
	/**
	 * @return Returns the strRepaymentContract.
	 */
	public String getStrRepaymentContract() {
		return strRepaymentContract;
	}
	/**
	 * @param strRepaymentContract The strRepaymentContract to set.
	 */
	public void setStrRepaymentContract(String strRepaymentContract) {
		this.strRepaymentContract = strRepaymentContract;
	}
	/**
	 * @return Returns the strRepaymentDueBill.
	 */
	public String getStrRepaymentDueBill() {
		return strRepaymentDueBill;
	}
	/**
	 * @param strRepaymentDueBill The strRepaymentDueBill to set.
	 */
	public void setStrRepaymentDueBill(String strRepaymentDueBill) {
		this.strRepaymentDueBill = strRepaymentDueBill;
	}
	/**
	 * @return Returns the strRepaymentFixedDeposit.
	 */
	public String getStrRepaymentFixedDeposit() {
		return strRepaymentFixedDeposit;
	}
	/**
	 * @param strRepaymentFixedDeposit The strRepaymentFixedDeposit to set.
	 */
	public void setStrRepaymentFixedDeposit(String strRepaymentFixedDeposit) {
		this.strRepaymentFixedDeposit = strRepaymentFixedDeposit;
	}
	/**
	 * @return Returns the strTransactionNo.
	 */
	public String getStrTransactionNo() {
		return strTransactionNo;
	}
	/**
	 * @param strTransactionNo The strTransactionNo to set.
	 */
	public void setStrTransactionNo(String strTransactionNo) {
		this.strTransactionNo = strTransactionNo;
	}
	/**
	 * @return Returns the strVoucherNo.
	 */
	public String getStrVoucherNo() {
		return strVoucherNo;
	}
	/**
	 * @param strVoucherNo The strVoucherNo to set.
	 */
	public void setStrVoucherNo(String strVoucherNo) {
		this.strVoucherNo = strVoucherNo;
	}
	/**
	 * @return Returns the strVoucherPwd.
	 */
	public String getStrVoucherPwd() {
		return strVoucherPwd;
	}
	/**
	 * @param strVoucherPwd The strVoucherPwd to set.
	 */
	public void setStrVoucherPwd(String strVoucherPwd) {
		this.strVoucherPwd = strVoucherPwd;
	}
	/**
	 * @return Returns the tsAffirm.
	 */
	public Timestamp getTsAffirm() {
		return tsAffirm;
	}
	/**
	 * @param tsAffirm The tsAffirm to set.
	 */
	public void setTsAffirm(Timestamp tsAffirm) {
		this.tsAffirm = tsAffirm;
	}
	/**
	 * @return Returns the tsCheck.
	 */
	public Timestamp getTsCheck() {
		return tsCheck;
	}
	/**
	 * @param tsCheck The tsCheck to set.
	 */
	public void setTsCheck(Timestamp tsCheck) {
		this.tsCheck = tsCheck;
	}
	/**
	 * @return Returns the tsExecute.
	 */
	public Timestamp getTsExecute() {
		return tsExecute;
	}
	/**
	 * @param tsExecute The tsExecute to set.
	 */
	public void setTsExecute(Timestamp tsExecute) {
		this.tsExecute = tsExecute;
	}
	/**
	 * @return Returns the tsInput.
	 */
	public Timestamp getTsInput() {
		return tsInput;
	}
	/**
	 * @param tsInput The tsInput to set.
	 */
	public void setTsInput(Timestamp tsInput) {
		this.tsInput = tsInput;
	}
	/**
	 * @return Returns the tsInterestStart.
	 */
	public Timestamp getTsInterestStart() {
		return tsInterestStart;
	}
	/**
	 * @param tsInterestStart The tsInterestStart to set.
	 */
	public void setTsInterestStart(Timestamp tsInterestStart) {
		this.tsInterestStart = tsInterestStart;
	}
	/**
	 * @return Returns the tsModify.
	 */
	public Timestamp getTsModify() {
		return tsModify;
	}
	/**
	 * @param tsModify The tsModify to set.
	 */
	public void setTsModify(Timestamp tsModify) {
		this.tsModify = tsModify;
	}
	public long getPayRelation() {
		return payRelation;
	}
	public void setPayRelation(long payRelation) {
		this.payRelation = payRelation;
	}
	public long getGatheringRelation() {
		return gatheringRelation;
	}
	public void setGatheringRelation(long gatheringRelation) {
		this.gatheringRelation = gatheringRelation;
	}
}

