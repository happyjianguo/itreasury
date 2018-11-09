package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 授信额度对象
 * 
 * @author leiyang
 *
 */
public class AmountFormViewInfo extends ITreasuryBaseDataEntity {

	
	private long      id            = -1;
	private String    creditCode    = "";
	private long      clientId      = -1;
	private String    clientCode    = "";
	private String    clientName    = "";
	private long      creditModel   = -1;
	private long      groupCreditId = -1;
	private String groupCreditCode = "";
	private Timestamp groupStartDate = null;
	private Timestamp groupEndDate = null;
	private double    creditAmount  = 0.0;
	private double    usedAmount = 0.0;
	private double    remainAmount = 0.0;
	private long      controlType   = -1;
	private Timestamp startDate     = null;
	private Timestamp endDate       = null;
	private long      officeId      = -1;
	private long      currencyId    = -1;
	private long      inputUserId   = -1;
	private String    inputUserName = "";
	private Timestamp inputDate     = null;
	private long      state         = -1;
	private List      subAmountFormList = new ArrayList();
	
	private Timestamp currentSystemDate = null;
	public SubAmountFormInfo subAmountFormInfo = null;
	
	//授信已占用额度明细列表 add by xfma3(马现福) 2009-5-22
	private List      applyInfoList = new ArrayList();//申请信息
	private List      contractInfoList = new ArrayList();//合同信息
	private List      repayInfoList = new ArrayList();//还款信息
	private List      subClientUsedList = new ArrayList();//集团授信下属单位已占用额度信息
	private List      loanUsedAmountInfoList = new ArrayList();//自营贷款放款单金额

	public List getLoanUsedAmountInfoList() {
		return loanUsedAmountInfoList;
	}

	public void setLoanUsedAmountInfoList(List loanUsedAmountInfoList) {
		this.loanUsedAmountInfoList = loanUsedAmountInfoList;
	}

	public SubAmountFormInfo getSubAmountFormInfo() {
		return subAmountFormInfo;
	}

	public void setSubAmountFormInfo(SubAmountFormInfo subAmountFormInfo) {
		this.subAmountFormInfo = subAmountFormInfo;
	}
	
	public AmountFormInfo getAmountFormInfo()
	{
		AmountFormInfo amountFormInfo = new AmountFormInfo();
		amountFormInfo.setId(id);
		amountFormInfo.setCreditCode(creditCode);
		amountFormInfo.setClientId(clientId);
		amountFormInfo.setCreditModel(creditModel);
		amountFormInfo.setGroupCreditId(groupCreditId);
		amountFormInfo.setCreditAmount(creditAmount);
		amountFormInfo.setControlType(controlType);
		amountFormInfo.setStartDate(startDate);
		amountFormInfo.setEndDate(endDate);
		amountFormInfo.setOfficeId(officeId);
		amountFormInfo.setCurrencyId(currencyId);
		amountFormInfo.setInputUserId(inputUserId);
		amountFormInfo.setInputDate(inputDate);
		amountFormInfo.setState(state);
		return amountFormInfo;
	}
	
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public long getControlType() {
		return controlType;
	}

	public void setControlType(long controlType) {
		this.controlType = controlType;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public long getCreditModel() {
		return creditModel;
	}

	public void setCreditModel(long creditModel) {
		this.creditModel = creditModel;
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	public Timestamp getEndDate() {
		
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public long getGroupCreditId() {
		return groupCreditId;
	}

	public void setGroupCreditId(long groupCreditId) {
		this.groupCreditId = groupCreditId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public long getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}

	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public double getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(double usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public Timestamp getCurrentSystemDate() {
		return currentSystemDate;
	}

	public void setCurrentSystemDate(Timestamp currentSystemDate) {
		this.currentSystemDate = currentSystemDate;
	}

	public String getGroupCreditCode() {
		return groupCreditCode;
	}

	public void setGroupCreditCode(String groupCreditCode) {
		this.groupCreditCode = groupCreditCode;
	}

	public Timestamp getGroupStartDate() {
		return groupStartDate;
	}

	public void setGroupStartDate(Timestamp groupStartDate) {
		this.groupStartDate = groupStartDate;
	}

	public Timestamp getGroupEndDate() {
		return groupEndDate;
	}

	public void setGroupEndDate(Timestamp groupEndDate) {
		this.groupEndDate = groupEndDate;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public List getSubAmountFormList() {
		return subAmountFormList;
	}

	public void setSubAmountFormList(List subAmountFormList) {
		this.subAmountFormList = subAmountFormList;
	}

	public List getApplyInfoList() {
		return applyInfoList;
	}

	public void setApplyInfoList(List applyInfoList) {
		this.applyInfoList = applyInfoList;
	}

	public List getContractInfoList() {
		return contractInfoList;
	}

	public void setContractInfoList(List contractInfoList) {
		this.contractInfoList = contractInfoList;
	}

	public List getRepayInfoList() {
		return repayInfoList;
	}

	public void setRepayInfoList(List repayInfoList) {
		this.repayInfoList = repayInfoList;
	}

	public List getSubClientUsedList() {
		return subClientUsedList;
	}

	public void setSubClientUsedList(List subClientUsedList) {
		this.subClientUsedList = subClientUsedList;
	}
}
