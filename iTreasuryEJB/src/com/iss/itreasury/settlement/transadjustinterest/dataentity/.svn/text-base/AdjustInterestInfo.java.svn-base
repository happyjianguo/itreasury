package com.iss.itreasury.settlement.transadjustinterest.dataentity;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * 累计费用及利息调整值明细记录。
 * 创建日期：(2005-9-6 18:29:50)
 * @author：feiye
 */
public class AdjustInterestInfo extends SettlementBaseDataEntity
{
	private long lID = -1;						//记录ID
    private long lOfficeID=-1;					//办事处ID
    private long lCurrencyID=-1;				//币种ID		
    //
    private long lAccountID = -1;				//账户ID
    private String strAccountNo = "";			//账户号
    private long lContractID = -1;				//合同ID
    private String strContractCode = "";		//合同号
    private long lDueBillID = -1;				//放款通知单ID
    private String strLetoutRequisitionNo="";	//放款通知单号
    //
    private long lAddOrReduce = -1;				//加或减 1:加 0:减
    private Timestamp tsExecute = null;			//执行日
    private double dInterest = 0;				//累计利息调整量
    private double dCommission = 0;				//累计手续费调整量
    private double dSuretyFee = 0;				//累计担保费调整量
    private double dInterestTax = 0;			//累计利息税费调整量
    //
    private long lInputUserID=-1;				//输入人ID
    private String strInputUser = "";			//输入人姓名	
    private Timestamp tsInput = null;			//输入日期
    
    private long lCheckUserID=-1;				//复核人ID
    private String strCheckUser = "";			//复核人姓名	
    private Timestamp tsCheck = null;			//复核日期
    //
    private long lStatusID = -1;				//记录状态
    private String strAdjustReason = ""; 		//备注
    
    private long lDepositTypeID = -1;			//贷款类型ID
    //
    private long lSubAccountID = -1;			//子账户ID
    
    private InutParameterInfo inutParameterInfo = null;
    
    //-------------有待考是否需要
    //public long m_lLoanTypeID = -1;
    //public long m_lLoanID = -1;
    //public long m_lFixedDepositID = -1;
    //public String m_strFixedDepositFormNo = "";
    /**
	 * @return Returns the lID.
	 */
	public long getLID() {
		return lID;
	}
	/**
	 * @param id The lID to set.
	 */
	public void setLID(long lID) {
		this.lID = lID;
	}
    /**
	 * @return Returns the lOfficeID.
	 */
	public long getLOfficeID() {
		return lOfficeID;
	}
	/**
	 * @param id The lOfficeID to set.
	 */
	public void setLOfficeID(long lOfficeID) {
		this.lOfficeID = lOfficeID;
	}
	/**
	 * @return Returns the lCurrencyID.
	 */
	public long getLCurrencyID() {
		return lCurrencyID;
	}
	/**
	 * @param id The lCurrencyID to set.
	 */
	public void setLCurrencyID(long lCurrencyID) {
		this.lCurrencyID = lCurrencyID;
	}
	/**
	 * @return Returns the lAccountID.
	 */
	public long getLAccountID() {
		return lAccountID;
	}
	/**
	 * @param id The lAccountID to set.
	 */
	public void setLAccountID(long lAccountID) {
		this.lAccountID = lAccountID;
	}
	
	/**
	 * @return Returns the strAccountNo.
	 */
	public String getSAccountNo() {
		return strAccountNo;
	}
	/**
	 * @param id The strAccountNo to set.
	 */
	public void setSAccountNo(String strAccountNo) {
		this.strAccountNo = strAccountNo;
	}

	/**
	 * @return Returns the lContractID.
	 */
	public long getLContractID() {
		return lContractID;
	}
	/**
	 * @param id The lContractID to set.
	 */
	public void setLContractID(long lContractID) {
		this.lContractID = lContractID;
	}
	
	/**
	 * @return Returns the strContractCode.
	 */
	public String getSContractCode() {
		return strContractCode;
	}
	/**
	 * @param id The strAccountNo to set.
	 */
	public void setSContractCode(String strContractCode) {
		this.strContractCode = strContractCode;
	}

	/**
	 * @return Returns the lDueBillID.
	 */
	public long getLDueBillID() {
		return lDueBillID;
	}
	/**
	 * @param id The lDueBillID to set.
	 */
	public void setLDueBillID(long lDueBillID) {
		this.lDueBillID = lDueBillID;
	}
	
	/**
	 * @return Returns the strLetoutRequisitionNo.
	 */
	public String getSLetoutRequisitionNo() {
		return strLetoutRequisitionNo;
	}
	/**
	 * @param id The strAccountNo to set.
	 */
	public void setSLetoutRequisitionNo(String strLetoutRequisitionNo) {
		this.strLetoutRequisitionNo = strLetoutRequisitionNo;
	}
	
	 /**
	 * @return Returns the lAddOrReduce.
	 */
	public long getLAddOrReduce() {
		return lAddOrReduce;
	}
	/**
	 * @param id The lAddOrReduce to set.
	 */
	public void setLAddOrReduce(long lAddOrReduce) {
		this.lAddOrReduce = lAddOrReduce;
	}
	
	/**
	 * @return Returns the tsExecute.
	 */
	public Timestamp getTsExecute()
	{
		return tsExecute;
	}
	/**
	 * @param modifyDate The tsExecute to set.
	 */
	public void setTsExecute(Timestamp tsExecute)
	{
		this.tsExecute = tsExecute;
		//putUsedField("modifyDate", modifyDate);
	}
	
	 /**
	 * @return Returns the dInterest.
	 */
	public double getDInterest() {
		return dInterest;
	}
	/**
	 * @param id The dInterest to set.
	 */
	public void setDInterest(double dInterest) {
		this.dInterest = dInterest;
	}
	
	 /**
	 * @return Returns the dCommission.
	 */
	public double getDCommission() {
		return dCommission;
	}
	/**
	 * @param id The dCommission to set.
	 */
	public void setDCommission(double dCommission) {
		this.dCommission = dCommission;
	}
	
	 /**
	 * @return Returns the dSuretyFee.
	 */
	public double getDSuretyFee() {
		return dSuretyFee;
	}
	/**
	 * @param id The dSuretyFee to set.
	 */
	public void setDSuretyFee(double dSuretyFee) {
		this.dSuretyFee = dSuretyFee;
	}
	
	/**
	 * @return Returns the dInterestTax.
	 */
	public double getDInterestTax() {
		return dInterestTax;
	}
	/**
	 * @param id The dInterestTax to set.
	 */
	public void setDInterestTax(double dInterestTax) {
		this.dInterestTax = dInterestTax;
	}
	
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getLInputUserID() {
		return lInputUserID;
	}
	/**
	 * @param id The lInputUserID to set.
	 */
	public void setLInputUserID(long lInputUserID) {
		this.lInputUserID = lInputUserID;
	}
	
	/**
	 * @return Returns the strInputUser.
	 */
	public String getSInputUser() {
		return strInputUser;
	}
	/**
	 * @param id The strInputUser to set.
	 */
	public void setSInputUser(String strInputUser) {
		this.strInputUser = strInputUser;
	}
	
	/**
	 * @return Returns the tsInput.
	 */
	public Timestamp getTsInput()
	{
		return tsInput;
	}
	/**
	 * @param modifyDate The tsInput to set.
	 */
	public void setTsInput(Timestamp tsInput)
	{
		this.tsInput = tsInput;
		//putUsedField("modifyDate", modifyDate);
	}
	
	
	
	/**
	 * @return Returns the lCheckUserID.
	 */
	public long getLCheckUserID() {
		return lCheckUserID;
	}
	/**
	 * @param id The lCheckUserID to set.
	 */
	public void setLCheckUserID(long lCheckUserID) {
		this.lCheckUserID = lCheckUserID;
	}
	
	/**
	 * @return Returns the strCheckUser.
	 */
	public String getSCheckUser() {
		return strCheckUser;
	}
	/**
	 * @param id The strCheckUser to set.
	 */
	public void setSCheckUser(String strCheckUser) {
		this.strCheckUser = strCheckUser;
	}
	
	/**
	 * @return Returns the tsCheck.
	 */
	public Timestamp getTsCheck()
	{
		return tsCheck;
	}
	/**
	 * @param modifyDate The tsCheck to set.
	 */
	public void setTsCheck(Timestamp tsCheck)
	{
		this.tsCheck = tsCheck;
		//putUsedField("modifyDate", modifyDate);
	}
	
	
	/**
	 * @return Returns the lStatusID.
	 */
	public long getLStatusID() {
		return lStatusID;
	}
	/**
	 * @param id The lStatusID to set.
	 */
	public void setLStatusID(long lStatusID) {
		this.lStatusID = lStatusID;
	}
	
	/**
	 * @return Returns the strAdjustReason.
	 */
	public String getSAdjustReason() {
		return strAdjustReason;
	}
	/**
	 * @param id The strAdjustReason to set.
	 */
	public void setSAdjustReason(String strAdjustReason) {
		this.strAdjustReason = strAdjustReason;
	}
	
	
	/**
	 * @return Returns the lDepositTypeID.
	 */
	public long getLDepositTypeID() {
		return lDepositTypeID;
	}
	/**
	 * @param id The lStatusID to set.
	 */
	public void setLDepositTypeID(long lDepositTypeID) {
		this.lDepositTypeID = lDepositTypeID;
	}
	
	/**
	 * @return Returns the lSubAccountID.
	 */
	public long getLSubAccountID() {
		return lSubAccountID;
	}
	/**
	 * @param id The lSubAccountID to set.
	 */
	public void setLSubAccountID(long lSubAccountID) {
		this.lSubAccountID = lSubAccountID;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
}
