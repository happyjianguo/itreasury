/*
 * Created on 2004-5-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesTypeCreditLineInfo extends SECBaseDataEntity {
	private long id = -1;
	private long SecuritiesTypeId = -1;//证券类别ID
	private double CreditLine = 0.0;//授信额度
	private long StatusId = -1;//状态  0=已删除 	2=已保存 	3=已复核
	private long InputUserId = -1;//录入人
	private Timestamp InputDate = null;//录入时间
	private long UpdateUserId = -1;//修改人
	private Timestamp UpdateDate = null;//修改时间
	private long CheckUserId = -1;//复核人
	private Timestamp CheckDate = null;//复核时间
	
	//查询条件
	private long QuerySecuritiesTypeId = -1;
	private double QueryCreditLineFrom = 0.0;
	private double QueryCreditLineTo = 0.0;
	private long QueryInputUserId = -1;

	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate() {
		return CheckDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate) {
		CheckDate = checkDate;
		putUsedField("checkDate", checkDate);
		CheckDate = checkDate;
	}
	/**
	 * @return Returns the checkUserId.
	 */
	public long getCheckUserId() {
		return CheckUserId;
	}
	/**
	 * @param checkUserId The checkUserId to set.
	 */
	public void setCheckUserId(long checkUserId) {
		CheckUserId = checkUserId;
		putUsedField("checkUserId", checkUserId);
		CheckUserId = checkUserId;
	}
	/**
	 * @return Returns the creditLine.
	 */
	public double getCreditLine() {
		return CreditLine;
	}
	/**
	 * @param creditLine The creditLine to set.
	 */
	public void setCreditLine(double creditLine) {
		CreditLine = creditLine;
		putUsedField("creditLine", creditLine);
		CreditLine = creditLine;
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
		putUsedField("id", id);
		this.id = id;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
		putUsedField("inputDate", inputDate);
		InputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserId.
	 */
	public long getInputUserId() {
		return InputUserId;
	}
	/**
	 * @param inputUserId The inputUserId to set.
	 */
	public void setInputUserId(long inputUserId) {
		InputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
		InputUserId = inputUserId;
	}
	/**
	 * @return Returns the securitiesTypeId.
	 */
	public long getSecuritiesTypeId() {
		return SecuritiesTypeId;
	}
	/**
	 * @param securitiesTypeId The securitiesTypeId to set.
	 */
	public void setSecuritiesTypeId(long securitiesTypeId) {
		SecuritiesTypeId = securitiesTypeId;
		putUsedField("securitiesTypeId", securitiesTypeId);
		SecuritiesTypeId = securitiesTypeId;
	}
	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId() {
		return StatusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId) {
		StatusId = statusId;
		putUsedField("statusId", statusId);
		StatusId = statusId;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate() {
		return UpdateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate) {
		UpdateDate = updateDate;
		putUsedField("updateDate", updateDate);
		UpdateDate = updateDate;
	}
	/**
	 * @return Returns the updateUserId.
	 */
	public long getUpdateUserId() {
		return UpdateUserId;
	}
	/**
	 * @param updateUserId The updateUserId to set.
	 */
	public void setUpdateUserId(long updateUserId) {
		UpdateUserId = updateUserId;
		putUsedField("updateUserId", updateUserId);
		UpdateUserId = updateUserId;
	}
	/**
	 * @return Returns the queryCreditLineFrom.
	 */
	public double getQueryCreditLineFrom() {
		return QueryCreditLineFrom;
	}
	/**
	 * @param queryCreditLineFrom The queryCreditLineFrom to set.
	 */
	public void setQueryCreditLineFrom(double queryCreditLineFrom) {
		QueryCreditLineFrom = queryCreditLineFrom;
	}
	/**
	 * @return Returns the queryCreditLineTo.
	 */
	public double getQueryCreditLineTo() {
		return QueryCreditLineTo;
	}
	/**
	 * @param queryCreditLineTo The queryCreditLineTo to set.
	 */
	public void setQueryCreditLineTo(double queryCreditLineTo) {
		QueryCreditLineTo = queryCreditLineTo;
	}
	/**
	 * @return Returns the queryInputUserId.
	 */
	public long getQueryInputUserId() {
		return QueryInputUserId;
	}
	/**
	 * @param queryInputUserId The queryInputUserId to set.
	 */
	public void setQueryInputUserId(long queryInputUserId) {
		QueryInputUserId = queryInputUserId;
	}
	/**
	 * @return Returns the querySecuritiesTypeId.
	 */
	public long getQuerySecuritiesTypeId() {
		return QuerySecuritiesTypeId;
	}
	/**
	 * @param querySecuritiesTypeId The querySecuritiesTypeId to set.
	 */
	public void setQuerySecuritiesTypeId(long querySecuritiesTypeId) {
		QuerySecuritiesTypeId = querySecuritiesTypeId;
	}
}
