package com.iss.itreasury.loan.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 数据库表实体类<br>
 * <Li>表名称：Loan_ContractAssortSetting<br>
 * <Li>表属性：贷款合同类型分类表<br>
 * 
 * @author kaishao
 *
 */
public class LoanContractAssortSettingInfo extends ITreasuryBaseDataEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long ID = -1;// 主键id

	private long currencyID = -1;// 币种

	private long officeID = -1;// 办事处

	private String name = "";// 分类名称

	private long inputUserID = -1;// 录入人ID

	private Timestamp inputDate = null;// 录入时间

	private long statusID = -1;// 状态
	
	private String inputuserName = "";
	
	private long FieldID=-1;//字段属性

	
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		return ID;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		this.ID = id;
		putUsedField("ID", id);
	}
	

	/**
	 * @return
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * @param inputDate
	 */
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	/**
	 * @return
	 */
	public long getInputUserID() {
		return inputUserID;
	}

	/**
	 * @param inputUserID
	 */
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return currencyID;
	}

	/**
	 * @param currencyID
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return officeID;
	}

	/**
	 * @param officeID
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}

	/**
	 * @return
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * @param statusID
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}



	/**
	 * @return
	 */
	public String getName(){
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		putUsedField("NAME", name);
	}

	/**
	 * @return
	 */
	public String getInputuserName() {
		return inputuserName;
	}

	/**
	 * @param inputuserName
	 */
	public void setInputuserName(String inputuserName) {
		this.inputuserName = inputuserName;
		putUsedField("inputuserName",inputuserName);
	}

	/**
	 * @return
	 */
	public long getFieldID() {
		return FieldID;
	}

	/**
	 * @param FieldID
	 */
	public void setFieldID(long FieldID) {
		this.FieldID = FieldID;
		putUsedField("FieldID", FieldID);
	}

	
}   
