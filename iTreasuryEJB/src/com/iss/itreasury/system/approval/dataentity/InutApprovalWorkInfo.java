/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能
 */
package com.iss.itreasury.system.approval.dataentity;

import java.io.Serializable;
import java.util.Date;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class InutApprovalWorkInfo  implements Serializable
{
	private long taskID = -1;			//任务id
	private long entryID = -1;			//审批实例id	
	private long stepID = -1;			//审批环节id
	private long stepCode = -1;			//审批环节code
	private long actionID = -1;			//审批动作id
	private long actionCode = -1;		//审批动作code
	private String entryName = "";		//审批实例名称
	private String wfDefineName = "";	//审批流程名称
	private String stepName = "";		//审批环节名称
	private Date startDate = null;		//审批开始时间
	private Date dueDate = null;		//最迟完成时间
	private String owner = "";			//上一级审批人
	private String status = "";			//状态
	
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public long getEntryID() {
		return entryID;
	}
	public void setEntryID(long entryID) {
		this.entryID = entryID;
	}
	public String getEntryName() {
		return entryName;
	}
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public long getTaskID() 
	{
		return taskID;
	}
	public void setTaskID(long taskID) 
	{
		this.taskID = taskID;
	}
	public long getActionID() {
		return actionID;
	}
	public void setActionID(long actionID) {
		this.actionID = actionID;
	}
	public long getStepID() 
	{
		return stepID;
	}
	public void setStepID(long stepID) 
	{
		this.stepID = stepID;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public long getActionCode() {
		return actionCode;
	}
	public void setActionCode(long actionCode) {
		this.actionCode = actionCode;
	}
	public long getStepCode() {
		return stepCode;
	}
	public void setStepCode(long stepCode) {
		this.stepCode = stepCode;
	}
	public String getWfDefineName() {
		return wfDefineName;
	}
	public void setWfDefineName(String wfDefineName) {
		this.wfDefineName = wfDefineName;
	}
	
}
