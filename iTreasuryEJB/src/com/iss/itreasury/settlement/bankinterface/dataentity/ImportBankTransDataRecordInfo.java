package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;
import java.sql.Date;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ImportBankTransDataRecordInfo implements Serializable
{
	//导入交易的账户号
	private String accountNo = null;
	//导入交易的账户名称
	private String accountName = null;
	//导入成功的交易数
	private long recordCount = -1;
	//账户所属银行
	private long bankType = -1;
	//导入交易记录的开始日期（包含这一天）
	private Date beginDate = null;
	//导入交易记录的截止日期（包含这一天）
	private Date endDate = null;
	//导入操作的执行日期，精确到秒
	private Date executeDate = null;
	//本次操作是否可疑：0为验证通过，1为验证没通过
	private long isShadiness = Constant.FALSE;
	//操作人id
	private long operatorID = -1;
	//是否导入数据，配合页面用
	private boolean isImportData = false;
	//导入操作结果，配合页面用。 对应常量类SETTConstant.ImportBankDataExecuteStatus
	private long executeResult = SETTConstant.ImportBankDataExecuteStatus.NOTHING;
	
	//新增,标识该账户是开户行账户还是二级账户(true表示开户行账户,false表示二级账户)
	private boolean isUpper = false;
	
	//中行所需的联行号和机构号
	private String bankExchangeCode = null;
	private String branchCodeOfBank = null;
	
	/**
	 * Constructor for ImportBankTransDataRecordInfo.
	 */
	public ImportBankTransDataRecordInfo()
	{
		super();
	}

/**
 * @return Returns the accountName.
 */
public String getAccountName()
{
	return accountName;
}
/**
 * @param accountName The accountName to set.
 */
public void setAccountName(String accountName)
{
	this.accountName = accountName;
}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return accountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}
	/**
	 * @return Returns the bankType.
	 */
	public long getBankType()
	{
		return bankType;
	}
	/**
	 * @param bankType The bankType to set.
	 */
	public void setBankType(long bankType)
	{
		this.bankType = bankType;
	}
	/**
	 * @return Returns the beginDate.
	 */
	public Date getBeginDate()
	{
		return beginDate;
	}
	/**
	 * @param beginDate The beginDate to set.
	 */
	public void setBeginDate(Date beginDate)
	{
		this.beginDate = beginDate;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Date getEndDate()
	{
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Date getExecuteDate()
	{
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Date executeDate)
	{
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the executeResult.
	 */
	public long getExecuteResult()
	{
		return executeResult;
	}
	/**
	 * @param executeResult The executeResult to set.
	 */
	public void setExecuteResult(long executeResult)
	{
		this.executeResult = executeResult;
	}
	/**
	 * @return Returns the isImportData.
	 */
	public boolean isImportData()
	{
		return isImportData;
	}
	/**
	 * @param isImportData The isImportData to set.
	 */
	public void setImportData(boolean isImportData)
	{
		this.isImportData = isImportData;
	}
	/**
	 * @return Returns the operatorID.
	 */
	public long getOperatorID()
	{
		return operatorID;
	}
	/**
	 * @param operatorID The operatorID to set.
	 */
	public void setOperatorID(long operatorID)
	{
		this.operatorID = operatorID;
	}
	/**
	 * @param strOperatorID The operatorID to set.
	 * 重载供pageloader使用
	 */
	public void setOperatorID(String strOperatorID)
	{
		this.operatorID = Long.parseLong(strOperatorID);
	}
	
	/**
	 * @return Returns the isShadiness.
	 */
	public long getIsShadiness()
	{
		return isShadiness;
	}
	/**
	 * @param isShadiness The isShadiness to set.
	 */
	public void setIsShadiness(long isShadiness)
	{
		this.isShadiness = isShadiness;
	}
	/**
	 * @return Returns the recordCount.
	 */
	public long getRecordCount()
	{
		return recordCount;
	}
	/**
	 * @param recordCount The recordCount to set.
	 */
	public void setRecordCount(long recordCount)
	{
		this.recordCount = recordCount;
	}
	/**
	 * @return Returns the isUpper.
	 */
	public boolean getIsUpper()
	{
		return isUpper;
	}
	/**
	 * @param isUpper The isUpper to set.
	 */
	public void setIsUpper(boolean isUpper)
	{
		this.isUpper=isUpper;
	}

	/**
	 * @return Returns the bankExchangeCode.
	 */
	public String getBankExchangeCode() {
		return bankExchangeCode;
	}

	/**
	 * @param bankExchangeCode The bankExchangeCode to set.
	 */
	public void setBankExchangeCode(String bankExchangeCode) {
		this.bankExchangeCode = bankExchangeCode;
	}

	/**
	 * @return Returns the branchCodeOfBank.
	 */
	public String getBranchCodeOfBank() {
		return branchCodeOfBank;
	}

	/**
	 * @param branchCodeOfBank The branchCodeOfBank to set.
	 */
	public void setBranchCodeOfBank(String branchCodeOfBank) {
		this.branchCodeOfBank = branchCodeOfBank;
	}
}
