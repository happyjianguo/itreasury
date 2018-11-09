package com.iss.itreasury.ebank.obquery.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.Timestamp;
import com.iss.itreasury.util.*;
public class OBQueryInfo implements java.io.Serializable
{

	/** Creates new QueryForm */
	public OBQueryInfo () 
	{
	}
	
	private long lTransType = -1; // 网上银行交易类型
	private long lStatus = -1; // 交易指令状态
	private long lPayerAcctID = -1; // 付款方账户ID
	private long lPayeeAcctID = -1; // 收款方账户ID
	private long lChildClientID = -1;//下属单位ID
	private long lInterestPayeeAcctID = -1; //利息收款方账户ID
	private double dAmount = 0.0; // 交易金额-本金
	private double dInterestAmount = 0.0; // 交易金额-利息
	private String sExecuteDate = null; // 执行日期
	private String sContractNo = ""; //合同号
	private long lRemitType = -1; // 汇款方式
	private long lInterestRemitType = -1; // 利息付款方式
	private long lFixedDepositTime = -1; // 定期存款期限（月）
	private long lNoticeDay = -1; // 通知存款品种（天）
	private String sDepositNo = ""; //定期（通知）存款单据号
	private long lSubAccountID = -1; //子账户ID
	private String  sLetOutCode = ""; // 方款通知单号(放款日期)
	private long lContractID = -1; //合同ID
	private long lLoanNoteID = -1; //放款通知单ID
	private double dRealInterest = 0.0; // 实付贷款利息
	private double dRealCompoundInterest = 0.0; // 实付复利
	private double dRealOverdueInterest = 0.0; // 实付逾期罚息
	private double dRealSuretyFee = 0.0; // 实付担保费
	private double dRealCommission = 0.0; // 实付手续费
	private String tsClearInterest = null; //结息日期
	private String strNewStartDate = null;// 续期新定期存款起始日
	private long fixedInterestDeal = -1;//1为到期续存，2为到期转存
	private long lInterestToAccountID = -1;
	private long lFIXEDNEXTPERIOD = -1;
	private String sDepositBillNo = "";//定期存单号
	// 必选条件
	private long lOperationTypeID=-1;//根据页面菜单，取值来自Notes
	private long lClientID = -1;//取值session
	private long lCurrencyID = -1;//取值session
	private long lUserID = -1;//取值session
	private long lofficeid=-1;
	private long isAutoContinue = -1;//是否自动转续存
	private long autocontinuetype = -1;//自动转续存类型（本金or本息）
	private long autocontinueaccountid = -1;//利息转至活期账户号	
	
	public long getLofficeid() {
		return lofficeid;
	}

	public void setLofficeid(long lofficeid) {
		this.lofficeid = lofficeid;
	}

	/**
	 * @return
	 */
	public double getAmount() {
		return dAmount;
	}

	/**
	 * @return
	 */
	public double getInterestAmount() {
		return dInterestAmount;
	}

	/**
	 * @return
	 */
	public double getRealCommission() {
		return dRealCommission;
	}

	/**
	 * @return
	 */
	public double getRealCompoundInterest() {
		return dRealCompoundInterest;
	}

	/**
	 * @return
	 */
	public double getRealInterest() {
		return dRealInterest;
	}

	/**
	 * @return
	 */
	public double getRealOverdueInterest() {
		return dRealOverdueInterest;
	}

	/**
	 * @return
	 */
	public double getRealSuretyFee() {
		return dRealSuretyFee;
	}

	/**
	 * @return
	 */
	public long getClientID() {
		return lClientID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}

	/**
	 * @return
	 */
	public long getFixedDepositTime() {
		return lFixedDepositTime;
	}

	/**
	 * @return
	 */
	public long getInterestPayeeAcctID() {
		return lInterestPayeeAcctID;
	}

	/**
	 * @return
	 */
	public long getInterestRemitType() {
		return lInterestRemitType;
	}

	/**
	 * @return
	 */
	public long getNoticeDay() {
		return lNoticeDay;
	}

	/**
	 * @return
	 */
	public long getOperationTypeID() {
		return lOperationTypeID;
	}

	/**
	 * @return
	 */
	public long getPayeeAcctID() {
		return lPayeeAcctID;
	}

	/**
	 * @return
	 */
	public long getPayerAcctID() {
		return lPayerAcctID;
	}

	/**
	 * @return
	 */
	public long getRemitType() {
		return lRemitType;
	}

	/**
	 * @return
	 */
	public long getStatus() {
		return lStatus;
	}

	/**
	 * @return
	 */
	public long getTransType() {
		return lTransType;
	}

	/**
	 * @return
	 */
	public long getUserID() {
		return lUserID;
	}

	/**
	 * @return
	 */
	public String getContractNo() {
		return sContractNo;
	}

	/**
	 * @return
	 */
	public String getDepositNo() {
		return sDepositNo;
	}

	/**
	 * @return
	 */
	public String getExecuteDate() {
		return sExecuteDate;
	}

	/**
	 * @return
	 */
	public String getLetOutCode() {
		return sLetOutCode;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		dAmount = d;
	}

	/**
	 * @param d
	 */
	public void setInterestAmount(double d) {
		dInterestAmount = d;
	}

	/**
	 * @param d
	 */
	public void setRealCommission(double d) {
		dRealCommission = d;
	}

	/**
	 * @param d
	 */
	public void setRealCompoundInterest(double d) {
		dRealCompoundInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRealInterest(double d) {
		dRealInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRealOverdueInterest(double d) {
		dRealOverdueInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRealSuretyFee(double d) {
		dRealSuretyFee = d;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		lClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		lCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositTime(long l) {
		lFixedDepositTime = l;
	}

	/**
	 * @param l
	 */
	public void setInterestPayeeAcctID(long l) {
		lInterestPayeeAcctID = l;
	}

	/**
	 * @param l
	 */
	public void setInterestRemitType(long l) {
		lInterestRemitType = l;
	}

	/**
	 * @param l
	 */
	public void setNoticeDay(long l) {
		lNoticeDay = l;
	}

	/**
	 * @param l
	 */
	public void setOperationTypeID(long l) {
		lOperationTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setPayeeAcctID(long l) {
		lPayeeAcctID = l;
	}

	/**
	 * @param l
	 */
	public void setPayerAcctID(long l) {
		lPayerAcctID = l;
	}

	/**
	 * @param l
	 */
	public void setRemitType(long l) {
		lRemitType = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		lStatus = l;
	}

	/**
	 * @param l
	 */
	public void setTransType(long l) {
		lTransType = l;
	}

	/**
	 * @param l
	 */
	public void setUserID(long l) {
		lUserID = l;
	}

	/**
	 * @param string
	 */
	public void setContractNo(String string) {
		sContractNo = string;
	}

	/**
	 * @param string
	 */
	public void setDepositNo(String string) {
		sDepositNo = string;
	}

	/**
	 * @param string
	 */
	public void setExecuteDate(String string) {
		sExecuteDate = string;
	}

	/**
	 * @param string
	 */
	public void setLetOutCode(String string) {
		sLetOutCode = string;
	}

	/**
	 * @return
	 */
	public long getSubAccountID() {
		return lSubAccountID;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l) {
		lSubAccountID = l;
	}

	/**
	 * @return
	 */
	public long getContractID() {
		return lContractID;
	}

	/**
	 * @return
	 */
	public long getLoanNoteID() {
		return lLoanNoteID;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l) {
		lContractID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanNoteID(long l) {
		lLoanNoteID = l;
	}

	/**
	 * @return
	 */
	public String getClearInterest() {
		return tsClearInterest;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterest(String timestamp) {
		tsClearInterest = timestamp;
	}

	/**
	 * @return
	 */
	public long getChildClientID()
	{
		return lChildClientID;
	}

	/**
	 * @param l
	 */
	public void setChildClientID(long l)
	{
		lChildClientID = l;
	}

	public long getLFIXEDNEXTPERIOD() {
		return lFIXEDNEXTPERIOD;
	}

	public void setLFIXEDNEXTPERIOD(long lfixednextperiod) {
		lFIXEDNEXTPERIOD = lfixednextperiod;
	}

	public long getLInterestToAccountID() {
		return lInterestToAccountID;
	}

	public void setLInterestToAccountID(long interestToAccountID) {
		lInterestToAccountID = interestToAccountID;
	}

	public String getStrNewStartDate() {
		return strNewStartDate;
	}

	public void setStrNewStartDate(String strNewStartDate) {
		this.strNewStartDate = strNewStartDate;
	}

	public long getFixedInterestDeal() {
		return fixedInterestDeal;
	}

	public void setFixedInterestDeal(long fixedInterestDeal) {
		this.fixedInterestDeal = fixedInterestDeal;
	}

	public String getSDepositBillNo() {
		return sDepositBillNo;
	}

	public void setSDepositBillNo(String depositBillNo) {
		sDepositBillNo = depositBillNo;
	}
	
	public long getIsAutoContinue() {
		return isAutoContinue;
	}

	public void setIsAutoContinue(long isAutoContinue) {
		this.isAutoContinue = isAutoContinue;
	}

	public long getAutocontinuetype() {
		return autocontinuetype;
	}

	public void setAutocontinuetype(long autocontinuetype) {
		this.autocontinuetype = autocontinuetype;
	}

	public long getAutocontinueaccountid() {
		return autocontinueaccountid;
	}

	public void setAutocontinueaccountid(long autocontinueaccountid) {
		this.autocontinueaccountid = autocontinueaccountid;
	}	

}
