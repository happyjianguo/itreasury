package com.iss.itreasury.project.gzbfcl.settlement.dataentity;

import java.util.Collection;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class FoundsdispatchInfo  extends ITreasuryBaseDataEntity{
	
	private long Id=-1;//资金调度令 ID
	private String foundsDispatchCode="";//资金调度令编号
	private String foundsDispatchReceive="";//资金调度令接收方
	private long inputUserID=-1;//录入人ID
	private Timestamp inputTime=null;//录入时间
	private long officeID=-1;//办事处ID
	private long currentID=-1;//币种ID
	private long modifyUserID=-1;//修改人ID
	private Timestamp modifyTime=null;//修改时间
	private long statusID=-1;//状态
	private Collection detail;//资金调度令明细
	/**
	 * @return the id
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
		putUsedField("id", id);
	}
	/**
	 * @return the foundsDispatchCode
	 */
	public String getFoundsDispatchCode() {
		return foundsDispatchCode;
	}
	/**
	 * @param foundsDispatchCode the foundsDispatchCode to set
	 */
	public void setFoundsDispatchCode(String foundsDispatchCode) {
		this.foundsDispatchCode = foundsDispatchCode;
		putUsedField("foundsDispatchCode", foundsDispatchCode);
	}
	/**
	 * @return the foundsDispatchReceive
	 */
	public String getFoundsDispatchReceive() {
		return foundsDispatchReceive;
	}
	/**
	 * @param foundsDispatchReceive the foundsDispatchReceive to set
	 */
	public void setFoundsDispatchReceive(String foundsDispatchReceive) {
		this.foundsDispatchReceive = foundsDispatchReceive;
		putUsedField("foundsDispatchReceive", foundsDispatchReceive);
	}
	/**
	 * @return the inputUserID
	 */
	public long getInputUserID() {
		return inputUserID;
	}
	/**
	 * @param inputUserID the inputUserID to set
	 */
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}
	/**
	 * @return the inputTime
	 */
	public Timestamp getInputTime() {
		return inputTime;
	}
	/**
	 * @param inputTime the inputTime to set
	 */
	public void setInputTime(Timestamp inputTime) {
		this.inputTime = inputTime;
		putUsedField("inputTime", inputTime);
	}
	/**
	 * @return the officeID
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID the officeID to set
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}
	/**
	 * @return the currentID
	 */
	public long getCurrentID() {
		return currentID;
	}
	/**
	 * @param currentID the currentID to set
	 */
	public void setCurrentID(long currentID) {
		this.currentID = currentID;
		putUsedField("currentID", currentID);
	}
	/**
	 * @return the modifyUserID
	 */
	public long getModifyUserID() {
		return modifyUserID;
	}
	/**
	 * @param modifyUserID the modifyUserID to set
	 */
	public void setModifyUserID(long modifyUserID) {
		this.modifyUserID = modifyUserID;
		putUsedField("modifyUserID", modifyUserID);
	}
	/**
	 * @return the modifyTime
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
		putUsedField("modifyTime", modifyTime);
	}
	/**
	 * @return the statusID
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID the statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	/**
	 * @return the detail
	 */
	public Collection getDetail() {
		return detail;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(Collection detail) {
		this.detail = detail;
	}
	




}
