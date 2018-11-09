/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	���� 
 * Company:             iSoftStone
 * @version				iTreasury3.2����
 * Description:         ��Ʒ��3.2�ڽ���,��������������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						Ϊ��iNut��������ҵ�����,�����������ù���
 */
package com.iss.itreasury.system.approval.dataentity;

import java.io.Serializable;
import java.util.Date;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class InutApprovalWorkInfo  implements Serializable
{
	private long taskID = -1;			//����id
	private long entryID = -1;			//����ʵ��id	
	private long stepID = -1;			//��������id
	private long stepCode = -1;			//��������code
	private long actionID = -1;			//��������id
	private long actionCode = -1;		//��������code
	private String entryName = "";		//����ʵ������
	private String wfDefineName = "";	//������������
	private String stepName = "";		//������������
	private Date startDate = null;		//������ʼʱ��
	private Date dueDate = null;		//������ʱ��
	private String owner = "";			//��һ��������
	private String status = "";			//״̬
	
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
