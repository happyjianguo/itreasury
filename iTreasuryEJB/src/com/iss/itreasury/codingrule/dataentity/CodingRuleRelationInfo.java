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
package com.iss.itreasury.codingrule.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CodingRuleRelationInfo extends ITreasuryBaseDataEntity {
	private long id = -1; // 主键

	private long officeID = -1; // 办事处ID

	private long currencyID = -1; // 币种ID

	private long moduleID = -1; // 模块标示

	private long transTypeID = -1; // 业务类型标识

	private long actionID = -1; // 操作标识

	private long codingruleID = -1; // 编码规则ID

	private long serialnumberID = -1; //流水号ID
	
	private long subActionID = -1; //二级操作标识
	
	public long getSubActionID() {
		return subActionID;
	}

	public void setSubActionID(long subActionID) {
		this.subActionID = subActionID;
		putUsedField("subActionID", subActionID);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public long getActionID() {
		return actionID;
	}

	public void setActionID(long actionID) {
		this.actionID = actionID;
		putUsedField("actionID", actionID);
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}

	public long getModuleID() {
		return moduleID;
	}

	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
		putUsedField("moduleID", moduleID);
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}

	public long getTransTypeID() {
		return transTypeID;
	}

	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
		putUsedField("transTypeID", transTypeID);
	}

	public long getCodingruleID() {
		return codingruleID;
	}

	public void setCodingruleID(long codingruleID) {
		this.codingruleID = codingruleID;
		putUsedField("codingruleID", codingruleID);
	}

	public long getSerialnumberID() {
		return serialnumberID;
	}

	public void setSerialnumberID(long serialnumberID) {
		this.serialnumberID = serialnumberID;
		putUsedField("serialnumberID", serialnumberID);
	}

	
}
