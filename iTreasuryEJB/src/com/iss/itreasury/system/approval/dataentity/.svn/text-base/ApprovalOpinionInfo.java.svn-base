/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						此为该功能添加的统一的标准审批意见设置功能 
 */
package com.iss.itreasury.system.approval.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class ApprovalOpinionInfo extends ITreasuryBaseDataEntity 
{
	private long id = -1;          			//主键
	private long officeID = -1;				//办事处ID
    private long currencyID = -1;			//币种ID	
    private long moduleID = -1;				//模块标示 
    private String code = "";				//编号
	private String description = "";		//意见描述 
	private long statusID = -1;				//状态	
	private long inputUserID = -1;          //录入人
	private Timestamp inputDate = null;		//录入时间
	
	public long getId() 
	{
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
		putUsedField("id", id);
	}
	public String getCode() 
	{
		return code;
	}
	public void setCode(String code) 
	{
		this.code = code;
		putUsedField("code", code);
	}
	public long getCurrencyID() 
	{
		return currencyID;
	}
	public void setCurrencyID(long currencyID) 
	{
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
		putUsedField("description", description);
	}
	public Timestamp getInputDate()
	{
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) 
	{
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) 
	{
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}
	public long getModuleID() 
	{
		return moduleID;
	}
	public void setModuleID(long moduleID) 
	{
		this.moduleID = moduleID;
		putUsedField("moduleID", moduleID);
	}
	public long getOfficeID() 
	{
		return officeID;
	}
	public void setOfficeID(long officeID) 
	{
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}
	public long getStatusID() 
	{
		return statusID;
	}
	public void setStatusID(long statusID) 
	{
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
}
