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
	//���뽻�׵��˻���
	private String accountNo = null;
	//���뽻�׵��˻�����
	private String accountName = null;
	//����ɹ��Ľ�����
	private long recordCount = -1;
	//�˻���������
	private long bankType = -1;
	//���뽻�׼�¼�Ŀ�ʼ���ڣ�������һ�죩
	private Date beginDate = null;
	//���뽻�׼�¼�Ľ�ֹ���ڣ�������һ�죩
	private Date endDate = null;
	//���������ִ�����ڣ���ȷ����
	private Date executeDate = null;
	//���β����Ƿ���ɣ�0Ϊ��֤ͨ����1Ϊ��֤ûͨ��
	private long isShadiness = Constant.FALSE;
	//������id
	private long operatorID = -1;
	//�Ƿ������ݣ����ҳ����
	private boolean isImportData = false;
	//���������������ҳ���á� ��Ӧ������SETTConstant.ImportBankDataExecuteStatus
	private long executeResult = SETTConstant.ImportBankDataExecuteStatus.NOTHING;
	
	//����,��ʶ���˻��ǿ������˻����Ƕ����˻�(true��ʾ�������˻�,false��ʾ�����˻�)
	private boolean isUpper = false;
	
	//������������кźͻ�����
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
	 * ���ع�pageloaderʹ��
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
