package com.iss.itreasury.ebank.obdiscountapply.dataentity;
import java.sql.Timestamp;
/**
 * @author gqzhang
 *贴现申请信息 To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class DiscountInfo implements java.io.Serializable
{
	public DiscountInfo()
	{
		super();
	}
	private long ID; //贴现ID标识
	private String DiscountCode; //贴现编号
	private String ContractCode; //贴现合同编号
	private long ApplyClientID; //申请单位编号
	private String ApplyClientName; //申请单位名称
	private String ApplyAccount; //申请单位账号
	private String ApplyBank; //申请单位开户银行
	private long ApplyOfficeID; //申请单位开户办事处标示
	private String ApplyOfficeName; //申请单位开户办事处名称
	private String ApplyLOfficeAccount; //申请单位开户办事处账号
	private long TypeID; //贷款类型
	private long CurrencyID; //币种
	private long OfficeID; //办事处标示
	private String OfficeName; //办事处名称
	private String LOfficeAccount; //办事处账号
	private long StatusID; //状态
	private long InputUserID; //录入人标示
	private String InputUserName; //录入人名称
	private Timestamp InputDate; //录入时间
	private long NextCheckUserID; //下一个审核人标示
	private long LsReviewUserID; //最后审核人ID
	private String LsReviewUserName; //最后审核人名称
	private long ReviewStatusID; //最后审核状态
	private long IsCheck; //是否审核过
	private double ApplyDiscountAmount; //申请贴现金额
	private double ExamineAmount; //批准金额
	private double CheckAmount; //核定金额
	private double DiscountRate; //利率
	private String DiscountPurpose; //贴现用途
	private String DiscountReason; //贴现原因
	private Timestamp DiscountDate; //贴现时间
	private Timestamp DiscountStartDate; //贴现开始时间
	private Timestamp DiscountEndDate; //贴现到期时间
	private long ApplyDiscountPO; //申请贴现汇票（张数）
	private long BankAccepPO; //银行承兑汇票（张数）
	private long BizAcceptPO; //商业承兑汇票（张数）
	private long BankCount; //银行承兑汇票（张数）
	private long BizCount; //商业承兑汇票（张数）
	private double Interest; //贴现利息
	private long BillCount; //汇票总张数
	private double BillAmount; //汇票总金额
	private long Count; //记录数
	private String DocumentType = ""; //随表报送书面材料
    private long isPurchaserInterest = 2;	//是否买方付息
    private long discountClientID = -1;		//出票人
    private String discountClientName = "";	//出票人名称
    private long subTypeId=-1;
	public long getDiscountClientID() {
		return discountClientID;
	}
	public void setDiscountClientID(long discountClientID) {
		this.discountClientID = discountClientID;
	}
	public String getDiscountClientName() {
		return discountClientName;
	}
	public void setDiscountClientName(String discountClientName) {
		this.discountClientName = discountClientName;
	}
	public long getIsPurchaserInterest() {
		return isPurchaserInterest;
	}
	public void setIsPurchaserInterest(long isPurchaserInterest) {
		this.isPurchaserInterest = isPurchaserInterest;
	}
	/**
	 * Returns the applyAccount.
	 * @return String
	 */
	public String getApplyAccount()
	{
		return ApplyAccount;
	}
	/**
	 * Returns the applyBank.
	 * @return String
	 */
	public String getApplyBank()
	{
		return ApplyBank;
	}
	/**
	 * Returns the applyClientID.
	 * @return long
	 */
	public long getApplyClientID()
	{
		return ApplyClientID;
	}
	/**
	 * Returns the applyClientName.
	 * @return String
	 */
	public String getApplyClientName()
	{
		return ApplyClientName;
	}
	/**
	 * Returns the applyDiscountAmount.
	 * @return double
	 */
	public double getApplyDiscountAmount()
	{
		return ApplyDiscountAmount;
	}
	/**
	 * Returns the applyDiscountPO.
	 * @return long
	 */
	public long getApplyDiscountPO()
	{
		return ApplyDiscountPO;
	}
	/**
	 * Returns the applyLOfficeAccount.
	 * @return String
	 */
	public String getApplyLOfficeAccount()
	{
		return ApplyLOfficeAccount;
	}
	/**
	 * Returns the applyOfficeID.
	 * @return long
	 */
	public long getApplyOfficeID()
	{
		return ApplyOfficeID;
	}
	/**
	 * Returns the applyOfficeName.
	 * @return String
	 */
	public String getApplyOfficeName()
	{
		return ApplyOfficeName;
	}
	/**
	 * Returns the bankAccepPO.
	 * @return long
	 */
	public long getBankAccepPO()
	{
		return BankAccepPO;
	}
	/**
	 * Returns the bankCount.
	 * @return long
	 */
	public long getBankCount()
	{
		return BankCount;
	}
	/**
	 * Returns the billAmount.
	 * @return double
	 */
	public double getBillAmount()
	{
		return BillAmount;
	}
	/**
	 * Returns the billCount.
	 * @return long
	 */
	public long getBillCount()
	{
		return BillCount;
	}
	/**
	 * Returns the bizAcceptPO.
	 * @return long
	 */
	public long getBizAcceptPO()
	{
		return BizAcceptPO;
	}
	/**
	 * Returns the bizCount.
	 * @return long
	 */
	public long getBizCount()
	{
		return BizCount;
	}
	/**
	 * Returns the checkAmount.
	 * @return double
	 */
	public double getCheckAmount()
	{
		return CheckAmount;
	}
	/**
	 * Returns the contractCode.
	 * @return String
	 */
	public String getContractCode()
	{
		return ContractCode;
	}
	/**
	 * Returns the count.
	 * @return long
	 */
	public long getCount()
	{
		return Count;
	}
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * Returns the discountCode.
	 * @return String
	 */
	public String getDiscountCode()
	{
		return DiscountCode;
	}
	/**
	 * Returns the discountDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountDate()
	{
		return DiscountDate;
	}
	/**
	 * Returns the discountEndDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountEndDate()
	{
		return DiscountEndDate;
	}
	/**
	 * Returns the discountPurpose.
	 * @return String
	 */
	public String getDiscountPurpose()
	{
		return DiscountPurpose;
	}
	/**
	 * Returns the discountRate.
	 * @return double
	 */
	public double getDiscountRate()
	{
		return DiscountRate;
	}
	/**
	 * Returns the discountReason.
	 * @return String
	 */
	public String getDiscountReason()
	{
		return DiscountReason;
	}
	/**
	 * Returns the discountStartDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountStartDate()
	{
		return DiscountStartDate;
	}
	/**
	 * Returns the examineAmount.
	 * @return double
	 */
	public double getExamineAmount()
	{
		return ExamineAmount;
	}
	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}
	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * Returns the inputUserName.
	 * @return String
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}
	/**
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest()
	{
		return Interest;
	}
	/**
	 * Returns the isCheck.
	 * @return long
	 */
	public long getIsCheck()
	{
		return IsCheck;
	}
	/**
	 * Returns the lOfficeAccount.
	 * @return String
	 */
	public String getLOfficeAccount()
	{
		return LOfficeAccount;
	}
	/**
	 * Returns the lsReviewUserID.
	 * @return long
	 */
	public long getLsReviewUserID()
	{
		return LsReviewUserID;
	}
	/**
	 * Returns the lsReviewUserName.
	 * @return String
	 */
	public String getLsReviewUserName()
	{
		return LsReviewUserName;
	}
	/**
	 * Returns the nextCheckUserID.
	 * @return long
	 */
	public long getNextCheckUserID()
	{
		return NextCheckUserID;
	}
	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * Returns the officeName.
	 * @return String
	 */
	public String getOfficeName()
	{
		return OfficeName;
	}
	/**
	 * Returns the reviewStatusID.
	 * @return long
	 */
	public long getReviewStatusID()
	{
		return ReviewStatusID;
	}
	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * Returns the typeID.
	 * @return long
	 */
	public long getTypeID()
	{
		return TypeID;
	}
	/**
	 * Sets the applyAccount.
	 * @param applyAccount The applyAccount to set
	 */
	public void setApplyAccount(String applyAccount)
	{
		ApplyAccount = applyAccount;
	}
	/**
	 * Sets the applyBank.
	 * @param applyBank The applyBank to set
	 */
	public void setApplyBank(String applyBank)
	{
		ApplyBank = applyBank;
	}
	/**
	 * Sets the applyClientID.
	 * @param applyClientID The applyClientID to set
	 */
	public void setApplyClientID(long applyClientID)
	{
		ApplyClientID = applyClientID;
	}
	/**
	 * Sets the applyClientName.
	 * @param applyClientName The applyClientName to set
	 */
	public void setApplyClientName(String applyClientName)
	{
		ApplyClientName = applyClientName;
	}
	/**
	 * Sets the applyDiscountAmount.
	 * @param applyDiscountAmount The applyDiscountAmount to set
	 */
	public void setApplyDiscountAmount(double applyDiscountAmount)
	{
		ApplyDiscountAmount = applyDiscountAmount;
	}
	/**
	 * Sets the applyDiscountPO.
	 * @param applyDiscountPO The applyDiscountPO to set
	 */
	public void setApplyDiscountPO(long applyDiscountPO)
	{
		ApplyDiscountPO = applyDiscountPO;
	}
	/**
	 * Sets the applyLOfficeAccount.
	 * @param applyLOfficeAccount The applyLOfficeAccount to set
	 */
	public void setApplyLOfficeAccount(String applyLOfficeAccount)
	{
		ApplyLOfficeAccount = applyLOfficeAccount;
	}
	/**
	 * Sets the applyOfficeID.
	 * @param applyOfficeID The applyOfficeID to set
	 */
	public void setApplyOfficeID(long applyOfficeID)
	{
		ApplyOfficeID = applyOfficeID;
	}
	/**
	 * Sets the applyOfficeName.
	 * @param applyOfficeName The applyOfficeName to set
	 */
	public void setApplyOfficeName(String applyOfficeName)
	{
		ApplyOfficeName = applyOfficeName;
	}
	/**
	 * Sets the bankAccepPO.
	 * @param bankAccepPO The bankAccepPO to set
	 */
	public void setBankAccepPO(long bankAccepPO)
	{
		BankAccepPO = bankAccepPO;
	}
	/**
	 * Sets the bankCount.
	 * @param bankCount The bankCount to set
	 */
	public void setBankCount(long bankCount)
	{
		BankCount = bankCount;
	}
	/**
	 * Sets the billAmount.
	 * @param billAmount The billAmount to set
	 */
	public void setBillAmount(double billAmount)
	{
		BillAmount = billAmount;
	}
	/**
	 * Sets the billCount.
	 * @param billCount The billCount to set
	 */
	public void setBillCount(long billCount)
	{
		BillCount = billCount;
	}
	/**
	 * Sets the bizAcceptPO.
	 * @param bizAcceptPO The bizAcceptPO to set
	 */
	public void setBizAcceptPO(long bizAcceptPO)
	{
		BizAcceptPO = bizAcceptPO;
	}
	/**
	 * Sets the bizCount.
	 * @param bizCount The bizCount to set
	 */
	public void setBizCount(long bizCount)
	{
		BizCount = bizCount;
	}
	/**
	 * Sets the checkAmount.
	 * @param checkAmount The checkAmount to set
	 */
	public void setCheckAmount(double checkAmount)
	{
		CheckAmount = checkAmount;
	}
	/**
	 * Sets the contractCode.
	 * @param contractCode The contractCode to set
	 */
	public void setContractCode(String contractCode)
	{
		ContractCode = contractCode;
	}
	/**
	 * Sets the count.
	 * @param count The count to set
	 */
	public void setCount(long count)
	{
		Count = count;
	}
	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}
	/**
	 * Sets the discountCode.
	 * @param discountCode The discountCode to set
	 */
	public void setDiscountCode(String discountCode)
	{
		DiscountCode = discountCode;
	}
	/**
	 * Sets the discountDate.
	 * @param discountDate The discountDate to set
	 */
	public void setDiscountDate(Timestamp discountDate)
	{
		DiscountDate = discountDate;
	}
	/**
	 * Sets the discountEndDate.
	 * @param discountEndDate The discountEndDate to set
	 */
	public void setDiscountEndDate(Timestamp discountEndDate)
	{
		DiscountEndDate = discountEndDate;
	}
	/**
	 * Sets the discountPurpose.
	 * @param discountPurpose The discountPurpose to set
	 */
	public void setDiscountPurpose(String discountPurpose)
	{
		DiscountPurpose = discountPurpose;
	}
	/**
	 * Sets the discountRate.
	 * @param discountRate The discountRate to set
	 */
	public void setDiscountRate(double discountRate)
	{
		DiscountRate = discountRate;
	}
	/**
	 * Sets the discountReason.
	 * @param discountReason The discountReason to set
	 */
	public void setDiscountReason(String discountReason)
	{
		DiscountReason = discountReason;
	}
	/**
	 * Sets the discountStartDate.
	 * @param discountStartDate The discountStartDate to set
	 */
	public void setDiscountStartDate(Timestamp discountStartDate)
	{
		DiscountStartDate = discountStartDate;
	}
	/**
	 * Sets the examineAmount.
	 * @param examineAmount The examineAmount to set
	 */
	public void setExamineAmount(double examineAmount)
	{
		ExamineAmount = examineAmount;
	}
	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}
	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
	}
	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}
	/**
	 * Sets the inputUserName.
	 * @param inputUserName The inputUserName to set
	 */
	public void setInputUserName(String inputUserName)
	{
		InputUserName = inputUserName;
	}
	/**
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}
	/**
	 * Sets the isCheck.
	 * @param isCheck The isCheck to set
	 */
	public void setIsCheck(long isCheck)
	{
		IsCheck = isCheck;
	}
	/**
	 * Sets the lOfficeAccount.
	 * @param lOfficeAccount The lOfficeAccount to set
	 */
	public void setLOfficeAccount(String lOfficeAccount)
	{
		LOfficeAccount = lOfficeAccount;
	}
	/**
	 * Sets the lsReviewUserID.
	 * @param lsReviewUserID The lsReviewUserID to set
	 */
	public void setLsReviewUserID(long lsReviewUserID)
	{
		LsReviewUserID = lsReviewUserID;
	}
	/**
	 * Sets the lsReviewUserName.
	 * @param lsReviewUserName The lsReviewUserName to set
	 */
	public void setLsReviewUserName(String lsReviewUserName)
	{
		LsReviewUserName = lsReviewUserName;
	}
	/**
	 * Sets the nextCheckUserID.
	 * @param nextCheckUserID The nextCheckUserID to set
	 */
	public void setNextCheckUserID(long nextCheckUserID)
	{
		NextCheckUserID = nextCheckUserID;
	}
	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}
	/**
	 * Sets the officeName.
	 * @param officeName The officeName to set
	 */
	public void setOfficeName(String officeName)
	{
		OfficeName = officeName;
	}
	/**
	 * Sets the reviewStatusID.
	 * @param reviewStatusID The reviewStatusID to set
	 */
	public void setReviewStatusID(long reviewStatusID)
	{
		ReviewStatusID = reviewStatusID;
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}
	/**
	 * Sets the typeID.
	 * @param typeID The typeID to set
	 */
	public void setTypeID(long typeID)
	{
		TypeID = typeID;
	}
	/**
	 * Returns the documentType.
	 * @return String
	 */
	public String getDocumentType()
	{
		return DocumentType;
	}
	/**
	 * Sets the documentType.
	 * @param documentType The documentType to set
	 */
	public void setDocumentType(String documentType)
	{
		DocumentType = documentType;
	}
	public long getSubTypeId() {
		return subTypeId;
	}
	public void setSubTypeId(long subTypeId) {
		this.subTypeId = subTypeId;
	}
}