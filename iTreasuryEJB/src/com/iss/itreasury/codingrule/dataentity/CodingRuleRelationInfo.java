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
package com.iss.itreasury.codingrule.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CodingRuleRelationInfo extends ITreasuryBaseDataEntity {
	private long id = -1; // ����

	private long officeID = -1; // ���´�ID

	private long currencyID = -1; // ����ID

	private long moduleID = -1; // ģ���ʾ

	private long transTypeID = -1; // ҵ�����ͱ�ʶ

	private long actionID = -1; // ������ʶ

	private long codingruleID = -1; // �������ID

	private long serialnumberID = -1; //��ˮ��ID
	
	private long subActionID = -1; //����������ʶ
	
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
