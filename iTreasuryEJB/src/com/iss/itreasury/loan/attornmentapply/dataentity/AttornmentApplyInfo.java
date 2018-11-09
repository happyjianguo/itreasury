/*
 * Created on 2004-5-21
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attornmentapply.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import java.sql.Timestamp;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttornmentApplyInfo extends SECBaseDataEntity
{
	private long id = -1;
	private String code = "";				//申请书编号
	private long repurchaseApplyId = -1;	//回购申请ID
	private String repurchaseApplyCode = "";
	private double repurchaseAmount = 0;	//保险标的
	private Timestamp repurchaseStartDate = null;	//开始日期
	private Timestamp repurchaseEndDate = null;		//结束日期
	private double attornmentAmount = 0;			//转让债券金额
	private long inputUserId=-1;					//录入人
	private Timestamp inputDate = null;				//录入日期
	private long updateUserId = -1;						//修改人
	private Timestamp updateDate = null;			//修改日期
	private long statusId = -1;						//状态
	private Timestamp timestamp = null;				//时间戳	
	private long nextCheckUserId = -1;
	private long nextCheckLevel = -1;
	
	private AttornmentContractInfo contractInfo = null;//合同信息	
	private long recordCount = -1;
	
    private long TypeID;                    //贷款类型
    private long subTypeId;                //贷款子类型
    private InutParameterInfo inutParameterInfo = null;
    private long officeId;
    private long currencyId;
	
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", id);
	}

	/**
	 * @return
	 */
	public double getAttornmentAmount()
	{
		return attornmentAmount;
	}

	/**
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}

	/**
	 * @return
	 */
	public long getInputUserId()
	{
		return inputUserId;
	}

	/**
	 * @return
	 */
	public double getRepurchaseAmount()
	{
		return repurchaseAmount;
	}

	/**
	 * @return
	 */
	public long getRepurchaseApplyId()
	{
		return repurchaseApplyId;
	}

	/**
	 * @return
	 */
	public Timestamp getRepurchaseEndDate()
	{
		return repurchaseEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getRepurchaseStartDate()
	{
		return repurchaseStartDate;
	}

	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @return
	 */
	public Timestamp getTimestamp()
	{
		return timestamp;
	}

	/**
	 * @return
	 */
	public Timestamp getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * @return
	 */
	public long getUpdateUserId()
	{
		return updateUserId;
	}

	/**
	 * @param d
	 */
	public void setAttornmentAmount(double d)
	{
		attornmentAmount = d;
		putUsedField("attornmentAmount",attornmentAmount);
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		code = string;
		putUsedField("code",code);
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		inputDate = timestamp;
		putUsedField("inputDate",inputDate);
	}

	/**
	 * @param l
	 */
	public void setInputUserId(long l)
	{
		inputUserId = l;
		putUsedField("inputUserId",inputUserId);
	}

	/**
	 * @param d
	 */
	public void setRepurchaseAmount(double d)
	{
		repurchaseAmount = d;
		putUsedField("repurchaseAmount",repurchaseAmount);
	}

	/**
	 * @param l
	 */
	public void setRepurchaseApplyId(long l)
	{
		repurchaseApplyId = l;
		putUsedField("repurchaseApplyId",repurchaseApplyId);
	}

	/**
	 * @param timestamp
	 */
	public void setRepurchaseEndDate(Timestamp timestamp)
	{
		repurchaseEndDate = timestamp;
		putUsedField("repurchaseEndDate",repurchaseEndDate);
	}

	/**
	 * @param timestamp
	 */
	public void setRepurchaseStartDate(Timestamp timestamp)
	{
		repurchaseStartDate = timestamp;
		putUsedField("repurchaseStartDate",repurchaseStartDate);
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
		putUsedField("statusId",statusId);
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
		putUsedField("timestamp",timestamp);
	}

	/**
	 * @param timestamp
	 */
	public void setUpdateDate(Timestamp timestamp)
	{
		updateDate = timestamp;
		putUsedField("inputDate",inputDate);
		putUsedField("UPDATEDATE",updateDate);
	}

	/**
	 * @param l
	 */
	public void setUpdateUserId(long l)
	{
		updateUserId = l;
		putUsedField("UpdateUserId",updateUserId);
	}

	/**
	 * @return
	 */
	public long getNextCheckUserId()
	{
		return nextCheckUserId;
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserId(long l)
	{
		nextCheckUserId = l;
		putUsedField("nextCheckUserId",nextCheckUserId);
	}

	/**
	 * @return
	 */
	public long getNextCheckLevel()
	{
		return nextCheckLevel;
	}

	/**
	 * @param l
	 */
	public void setNextCheckLevel(long l)
	{
		nextCheckLevel = l;
		putUsedField("nextCheckLevel",nextCheckLevel);
	}

	/**
	 * @return
	 */
	public String getRepurchaseApplyCode()
	{
		return repurchaseApplyCode;
	}

	/**
	 * @param string
	 */
	public void setRepurchaseApplyCode(String string)
	{
		repurchaseApplyCode = string;
	}

	public AttornmentContractInfo getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(AttornmentContractInfo contractInfo) {
		this.contractInfo = contractInfo;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		recordCount = recordCount;
	}

	public long getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(long subTypeId) {
		this.subTypeId = subTypeId;
		putUsedField("SUBTYPEID",subTypeId);
	}

	public long getTypeID() {
		return TypeID;
	}

	public void setTypeID(long typeID) {
		TypeID = typeID;
		putUsedField("TYPEID",typeID);
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId",officeId);
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId",currencyId);
	}

}
