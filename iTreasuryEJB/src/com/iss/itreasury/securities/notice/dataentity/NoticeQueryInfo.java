package com.iss.itreasury.securities.notice.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author fanyang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */                                                      
public class NoticeQueryInfo extends SECBaseDataEntity{
	private long id = -1;  						//id
	private long transactionTypeId = -1;		//��������
	private long clientId = -1;					//ҵ��λ����
	private long counterpartId = -1;			//���׶�������
	private double startAmount = 0;				//�������
	private double endAmount = 0;				//�����ֹ
	private Timestamp startDate = null;			//ҵ��֪ͨ��¼��������
	private Timestamp endDate = null;			//ҵ��֪ͨ��¼������ֹ
	private long statusId = -1;					//ҵ��֪ͨ��״̬
	private	String systemTransactionCode = "";	//����ϵͳ�ɽ����
	private Timestamp executeDate = null; 		//�ո�������
	private double startQuantity = 0;			//�ɽ�������
	private double endQuantity = 0;				//�ɽ�����ֹ
	
	private long pageLineCount = -1;			//ÿҳ����
	private long pageNo = -1;					//�ڼ�ҳ
	private long orderParam = -1;				//����
	private long desc = -1;						//����.����
	private long inputUserId = -1;				//¼����
	private long nextCheckUserId = -1;			//��һ�����
	private long queryPurpose = -1;	// 1:for modify 2:for examine
	private String orderParamString = "";		//�ַ�������
	private double startNetPrice = 0;			//��ֵ��
	private double endNetPrice = 0;				//��ֵֹ
	private long securitiesId = -1;				//֤ȯID
	private long accountId = -1;				//�ʽ��ʺ�id
	
	//Ϊ����ͬ��ͬ������֪ͨ������
	private long startContractId = -1;			//��ͬ�����
	private long endContractId = -1;			//��ͬ���ֹ
	private Timestamp startInputDate = null;	//�ύ������
	private Timestamp endInputDate = null;		//�ύ����ֹ
	private double startTransactionAmount = 0.0;//֪ͨ�������,Ҳ���ǽ��׽��(������+��Ϣ���)
	private double endTransactionAmount = 0.0;//֪ͨ�����ֹ,Ҳ���ǽ��׽��(������+��Ϣ���)
	
	private long approvalId = -1;				//���id
	private String strUser = "";				//Ȩ��ת�Ƶ�����
	private long moduleId = -1;
	private long loanTypeId = -1;
	private long actionId = -1;
	
	//zpli add 2005-09-19
	//TODO: ֤ȯ ֪ͨ�����Ƿ�������������ֶδ�ȷ��
	private long lCurrencyID=-1;				//���´�ID
	private long lOfficeID=-1;					//����ID
	////
	
	private double startPrice = 0;				//ת�ɼ۸���
	private double endPrice = 0;				//ת�ɼ۸�ֹ
	private double startOppositeQuantity = 0;	//ת�ɵĹ�Ʊ������
	private double endOppositeQuantity = 0;		//ת�ɵĹ�Ʊ����ֹ
	private long oppositeSecuritiesId = -1;		//ת�ɹ�Ʊ����Id
	
	private double startNetIncome = 0;			//���������
	private double endNetIncome = 0;			//�������ֹ
	
	
	
	/**
	 * @return Returns the clientID.
	 */
	public long getClientId() {
		return clientId;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return Returns the counterpartID.
	 */
	public long getCounterpartId() {
		return counterpartId;
	}
	/**
	 * @param counterpartID The counterpartID to set.
	 */
	public void setCounterpartId(long counterpartId) {
		this.counterpartId = counterpartId;
	}
	/**
	 * @return Returns the desc.
	 */
	public long getDesc() {
		return desc;
	}
	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(long desc) {
		this.desc = desc;
	}
	/**
	 * @return Returns the endAmount.
	 */
	public double getEndAmount() {
		return endAmount;
	}
	/**
	 * @param endAmount The endAmount to set.
	 */
	public void setEndAmount(double endAmount) {
		this.endAmount = endAmount;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Timestamp getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserId() {
		return inputUserId;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}
	/**
	 * @return Returns the nextCheckUserID.
	 */
	public long getNextCheckUserId() {
		return nextCheckUserId;
	}
	/**
	 * @param nextCheckUserID The nextCheckUserID to set.
	 */
	public void setNextCheckUserId(long nextCheckUserId) {
		this.nextCheckUserId = nextCheckUserId;
	}
	/**
	 * @return Returns the orderParam.
	 */
	public long getOrderParam() {
		return orderParam;
	}
	/**
	 * @param orderParam The orderParam to set.
	 */
	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}
	/**
	 * @return Returns the orderParamString.
	 */
	public String getOrderParamString() {
		return orderParamString;
	}
	/**
	 * @param orderParamString The orderParamString to set.
	 */
	public void setOrderParamString(String orderParamString) {
		this.orderParamString = orderParamString;
	}
	/**
	 * @return Returns the pageLineCount.
	 */
	public long getPageLineCount() {
		return pageLineCount;
	}
	/**
	 * @param pageLineCount The pageLineCount to set.
	 */
	public void setPageLineCount(long pageLineCount) {
		this.pageLineCount = pageLineCount;
	}
	/**
	 * @return Returns the pageNo.
	 */
	public long getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo The pageNo to set.
	 */
	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return Returns the queryPurpose.
	 */
	public long getQueryPurpose() {
		return queryPurpose;
	}
	/**
	 * @param queryPurpose The queryPurpose to set.
	 */
	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}
	/**
	 * @return Returns the startAmount.
	 */
	public double getStartAmount() {
		return startAmount;
	}
	/**
	 * @param startAmount The startAmount to set.
	 */
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
	}
	/**
	 * @return Returns the startDate.
	 */
	public Timestamp getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return Returns the systemTransactionCode.
	 */
	public String getSystemTransactionCode() {
		return systemTransactionCode;
	}
	/**
	 * @param systemTransactionCode The systemTransactionCode to set.
	 */
	public void setSystemTransactionCode(String systemTransactionCode) {
		this.systemTransactionCode = systemTransactionCode;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the endQuantity.
	 */
	public double getEndQuantity()
	{
		return endQuantity;
	}
	/**
	 * @param endQuantity The endQuantity to set.
	 */
	public void setEndQuantity(double endQuantity)
	{
		this.endQuantity = endQuantity;
	}
	/**
	 * @return Returns the startQuantity.
	 */
	public double getStartQuantity()
	{
		return startQuantity;
	}
	/**
	 * @param startQuantity The startQuantity to set.
	 */
	public void setStartQuantity(double startQuantity)
	{
		this.startQuantity = startQuantity;
	}
	/**
	 * @return Returns the endNetPrice.
	 */
	public double getEndNetPrice()
	{
		return endNetPrice;
	}
	/**
	 * @param endNetPrice The endNetPrice to set.
	 */
	public void setEndNetPrice(double endNetPrice)
	{
		this.endNetPrice = endNetPrice;
	}
	/**
	 * @return Returns the startNetPrice.
	 */
	public double getStartNetPrice()
	{
		return startNetPrice;
	}
	/**
	 * @param startNetPrice The startNetPrice to set.
	 */
	public void setStartNetPrice(double startNetPrice)
	{
		this.startNetPrice = startNetPrice;
	}
	/**
	 * @return Returns the accountId.
	 */
	public long getAccountId()
	{
		return accountId;
	}
	/**
	 * @param accountId The accountId to set.
	 */
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}
	/**
	 * @return Returns the securitiesId.
	 */
	public long getSecuritiesId()
	{
		return securitiesId;
	}
	/**
	 * @param securitiesId The securitiesId to set.
	 */
	public void setSecuritiesId(long securitiesId)
	{
		this.securitiesId = securitiesId;
	}
	/**
	 * @return Returns the endContractId.
	 */
	public long getEndContractId()
	{
		return endContractId;
	}
	/**
	 * @param endContractId The endContractId to set.
	 */
	public void setEndContractId(long endContractId)
	{
		this.endContractId = endContractId;
	}
	/**
	 * @return Returns the endInputDate.
	 */
	public Timestamp getEndInputDate()
	{
		return endInputDate;
	}
	/**
	 * @param endInputDate The endInputDate to set.
	 */
	public void setEndInputDate(Timestamp endInputDate)
	{
		this.endInputDate = endInputDate;
	}
	/**
	 * @return Returns the startContractId.
	 */
	public long getStartContractId()
	{
		return startContractId;
	}
	/**
	 * @param startContractId The startContractId to set.
	 */
	public void setStartContractId(long startContractId)
	{
		this.startContractId = startContractId;
	}
	/**
	 * @return Returns the startInputDate.
	 */
	public Timestamp getStartInputDate()
	{
		return startInputDate;
	}
	/**
	 * @param startInputDate The startInputDate to set.
	 */
	public void setStartInputDate(Timestamp startInputDate)
	{
		this.startInputDate = startInputDate;
	}
	/**
	 * @return Returns the endTransactionAmount.
	 */
	public double getEndTransactionAmount()
	{
		return endTransactionAmount;
	}
	/**
	 * @param endTransactionAmount The endTransactionAmount to set.
	 */
	public void setEndTransactionAmount(double endTransactionAmount)
	{
		this.endTransactionAmount = endTransactionAmount;
	}
	/**
	 * @return Returns the startTransactionAmount.
	 */
	public double getStartTransactionAmount()
	{
		return startTransactionAmount;
	}
	/**
	 * @param startTransactionAmount The startTransactionAmount to set.
	 */
	public void setStartTransactionAmount(double startTransactionAmount)
	{
		this.startTransactionAmount = startTransactionAmount;
	}
	/**
	 * @return Returns the approvalId.
	 */
	public long getApprovalId()
	{
		return approvalId;
	}
	/**
	 * @param approvalId The approvalId to set.
	 */
	public void setApprovalId(long approvalId)
	{
		this.approvalId = approvalId;
	}
	/**
	 * @return Returns the strUser.
	 */
	public String getStrUser()
	{
		return strUser;
	}
	/**
	 * @param strUser The strUser to set.
	 */
	public void setStrUser(String strUser)
	{
		this.strUser = strUser;
	}
	/**
	 * @return Returns the actionId.
	 */
	public long getActionId()
	{
		return actionId;
	}
	/**
	 * @param actionId The actionId to set.
	 */
	public void setActionId(long actionId)
	{
		this.actionId = actionId;
	}
	/**
	 * @return Returns the loanTypeId.
	 */
	public long getLoanTypeId()
	{
		return loanTypeId;
	}
	/**
	 * @param loanTypeId The loanTypeId to set.
	 */
	public void setLoanTypeId(long loanTypeId)
	{
		this.loanTypeId = loanTypeId;
	}
	/**
	 * @return Returns the moduleId.
	 */
	public long getModuleId()
	{
		return moduleId;
	}
	/**
	 * @param moduleId The moduleId to set.
	 */
	public void setModuleId(long moduleId)
	{
		this.moduleId = moduleId;
	}
	/**
	 * @return
	 */
	public double getEndOppositeQuantity()
	{
		return endOppositeQuantity;
	}

	/**
	 * @return
	 */
	public double getEndPrice()
	{
		return endPrice;
	}

	/**
	 * @return
	 */
	public double getStartOppositeQuantity()
	{
		return startOppositeQuantity;
	}

	/**
	 * @return
	 */
	public double getStartPrice()
	{
		return startPrice;
	}

	/**
	 * @param d
	 */
	public void setEndOppositeQuantity(double d)
	{
		endOppositeQuantity = d;
	}

	/**
	 * @param d
	 */
	public void setEndPrice(double d)
	{
		endPrice = d;
	}

	/**
	 * @param d
	 */
	public void setStartOppositeQuantity(double d)
	{
		startOppositeQuantity = d;
	}

	/**
	 * @param d
	 */
	public void setStartPrice(double d)
	{
		startPrice = d;
	}


	/**
	 * @return
	 */
	public long getOppositeSecuritiesId()
	{
		return oppositeSecuritiesId;
	}

	/**
	 * @param l
	 */
	public void setOppositeSecuritiesId(long l)
	{
		oppositeSecuritiesId = l;
	}
  
	/**
	 * @return
	 */
	public double getEndNetIncome()
	{
		return endNetIncome;
	}

	/**
	 * @return
	 */
	public double getStartNetIncome()
	{
		return startNetIncome;
	}

	/**
	 * @param d
	 */
	public void setEndNetIncome(double d)
	{
		endNetIncome = d;
	}

	/**
	 * @param d
	 */
	public void setStartNetIncome(double d)
	{
		startNetIncome = d;
	}
	/**
	 * @return Returns the lCurrencyID.
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}
	/**
	 * @param currencyID The lCurrencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}
	/**
	 * @return Returns the lOfficeID.
	 */
	public long getOfficeID() {
		return lOfficeID;
	}
	/**
	 * @param officeID The lOfficeID to set.
	 */
	public void setOfficeID(long officeID) {
		lOfficeID = officeID;
	}

}