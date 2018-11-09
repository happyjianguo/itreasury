/*
 * Created on 2004-07-19
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_OtherIndustryItemInfo extends TreasuryPlanBaseDataEntity implements Serializable {
	private	String code = null;	//	Name
	private	long id	= -1;//	����
	private	long lineID	= -1;//	�ƻ�����ĿID
	private	long moduleID = -1;	//	ģ��ID
	private	String clientCode = null;	//	�ͻ�/ҵ��λ���
	private	String accountCode = null;	//	�˻���/�ʽ��˻�
	private	String contractCode = null;	//	��ͬ��/�浥��
	private	String payFormCode = null;	//	�ſ�֪ͨ����
	private	String counterpartName = null;	//	���׶�������
	private	String securitiesName = null;	//	֤ȯ/��Ʒ����
	private	long accountTypeId = -1;	//	�˻�����
	private	String glSubjectCode = null;	//	��Ŀ��
	private	long amountDirection = -1;	//	������������
	private	String parameter = null;	//	�Զ������
	private	long amountFlag = -1;	//	����־
	private	String calculateFlag = null;	//	�����־

	/**
	 * @return Returns the accountCode.
	 */
	public String getAccountCode() {
		return accountCode;
	}
	/**
	 * @param accountCode The accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		putUsedField("accountCode", accountCode);
		this.accountCode = accountCode;
	}
	/**
	 * @return Returns the accountTypeId.
	 */
	public long getAccountTypeId() {
		return accountTypeId;
	}
	/**
	 * @param accountTypeId The accountTypeId to set.
	 */
	public void setAccountTypeId(long accountTypeId) {
		putUsedField("accountTypeId", accountTypeId);
		this.accountTypeId = accountTypeId;
	}
	/**
	 * @return Returns the amountDirection.
	 */
	public long getAmountDirection() {
		return amountDirection;
	}
	/**
	 * @param amountDirection The amountDirection to set.
	 */
	public void setAmountDirection(long amountDirection) {
		putUsedField("amountDirection", amountDirection);
		this.amountDirection = amountDirection;
	}
	/**
	 * @return Returns the amountFlag.
	 */
	public long getAmountFlag() {
		return amountFlag;
	}
	/**
	 * @param amountFlag The amountFlag to set.
	 */
	public void setAmountFlag(long amountFlag) {
		putUsedField("amountFlag", amountFlag);
		this.amountFlag = amountFlag;
	}
	/**
	 * @return Returns the calculateFlag.
	 */
	public String getCalculateFlag() {
		return calculateFlag;
	}
	/**
	 * @param calculateFlag The calculateFlag to set.
	 */
	public void setCalculateFlag(String calculateFlag) {
		putUsedField("calculateFlag", calculateFlag);
		this.calculateFlag = calculateFlag;
	}
	/**
	 * @return Returns the clientCode.
	 */
	public String getClientCode() {
		return clientCode;
	}
	/**
	 * @param clientCode The clientCode to set.
	 */
	public void setClientCode(String clientCode) {
		putUsedField("clientCode", clientCode);
		this.clientCode = clientCode;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		putUsedField("code", code);
		this.code = code;
	}
	/**
	 * @return Returns the contractCode.
	 */
	public String getContractCode() {
		return contractCode;
	}
	/**
	 * @param contractCode The contractCode to set.
	 */
	public void setContractCode(String contractCode) {
		putUsedField("contractCode", contractCode);
		this.contractCode = contractCode;
	}
	/**
	 * @return Returns the counterpartName.
	 */
	public String getCounterpartName() {
		return counterpartName;
	}
	/**
	 * @param counterpartName The counterpartName to set.
	 */
	public void setCounterpartName(String counterpartName) {
		putUsedField("counterpartName", counterpartName);
		this.counterpartName = counterpartName;
	}
	/**
	 * @return Returns the glSubjectCode.
	 */
	public String getGlSubjectCode() {
		return glSubjectCode;
	}
	/**
	 * @param glSubjectCode The glSubjectCode to set.
	 */
	public void setGlSubjectCode(String glSubjectCode) {
		putUsedField("glSubjectCode", glSubjectCode);
		this.glSubjectCode = glSubjectCode;
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
		putUsedField("id", id);
		this.id = id;
	}
	/**
	 * @return Returns the lineID.
	 */
	public long getLineID() {
		return lineID;
	}
	/**
	 * @param lineID The lineID to set.
	 */
	public void setLineID(long lineID) {
		putUsedField("lineID", lineID);
		this.lineID = lineID;
	}
	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID() {
		return moduleID;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID(long moduleID) {
		putUsedField("moduleID", moduleID);
		this.moduleID = moduleID;
	}
	/**
	 * @return Returns the parameter.
	 */
	public String getParameter() {
		return parameter;
	}
	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(String parameter) {
		putUsedField("parameter", parameter);
		this.parameter = parameter;
	}
	/**
	 * @return Returns the payFormCode.
	 */
	public String getPayFormCode() {
		return payFormCode;
	}
	/**
	 * @param payFormCode The payFormCode to set.
	 */
	public void setPayFormCode(String payFormCode) {
		putUsedField("payFormCode", payFormCode);
		this.payFormCode = payFormCode;
	}
	/**
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName() {
		return securitiesName;
	}
	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName) {
		putUsedField("securitiesName", securitiesName);
		this.securitiesName = securitiesName;
	}
}