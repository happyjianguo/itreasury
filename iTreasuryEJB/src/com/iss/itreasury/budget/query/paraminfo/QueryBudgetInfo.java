/*
 * Created on 2005-6-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.query.paraminfo;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.budget.dataentity.BudgetBaseDataEntity;
public class QueryBudgetInfo extends BudgetBaseDataEntity implements Cloneable{

    private long budgetSystemID = -1;		//Ԥ��ϵͳID
    private long clientID = -1;				//�ͻ�ID    
    private long budgetPeriodID = -1;		//Ԥ������ID
    private Timestamp startDate = null;		//Ԥ�����俪ʼ����
    private Timestamp endDate = null;		//Ԥ�������������
    private long budgetFlag= -1;     		//Ԥ���־ 0 ����Ԥ�� 1 ����Ԥ�� 2 ����Ԥ��

    private Timestamp relativeStartDate = null;		//�Ա�Ԥ�����俪ʼ����
    private Timestamp  relativeEndDate = null;		//�Ա�Ԥ�������������
    private long budgetUnit = -1;                //������λ

    private String versionNo = "";			//Ԥ��汾��
    private long queryType = -1;			//��ѯ���  1,Ԥ��汾��ѯ 2,Ԥ�������ѯ 3,Ԥ����ʷ������ѯ
    private long planID = -1;				//Ԥ��ID
    private long itemID = -1;				//Ԥ����Ŀ
    private long isContainLower = -1;		//�Ƿ�����¼���λ
    private String strItemId="";            //ѡ�����Ŀ��ɵ��ַ���

    
    //Ԥ�㵥λ�Աȷ��������ֶ�
    private long clientBID = -1;			//B�ͻ�ID
    private ArrayList itemInfo = null;  //��ŵ�λ������������е���Ŀ
    
    private long officeID = -1;
    private long currencyID = -1;
    
    //��ѯ����ʱ�õ��ֶ�
    private long[] showColumn = null;	//��ʾ��Щ�� BUDGETConstant.BudgetColumnList��
	
    public long getBudgetPeriodID() {
		return budgetPeriodID;
	}
	public void setBudgetPeriodID(long budgetPeriodID) {
		this.budgetPeriodID = budgetPeriodID;
	}
	public long getBudgetSystemID() {
		return budgetSystemID;
	}
	public void setBudgetSystemID(long budgetSystemID) {
		this.budgetSystemID = budgetSystemID;
	}
	public long getBudgetUnit() {
		return budgetUnit;
	}
	public void setBudgetUnit(long budgetUnit) {
		this.budgetUnit = budgetUnit;
	}
	public long getClientBID() {
		return clientBID;
	}
	public void setClientBID(long clientBID) {
		this.clientBID = clientBID;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public long getIsContainLower() {
		return isContainLower;
	}
	public void setIsContainLower(long isContainLower) {
		this.isContainLower = isContainLower;
	}
	public long getItemID() {
		return itemID;
	}
	public void setItemID(long itemID) {
		this.itemID = itemID;
	}
	public ArrayList getItemInfo() {
		return itemInfo;
	}
	public void setItemInfo(ArrayList itemInfo) {
		this.itemInfo = itemInfo;
	}
	public long getPlanID() {
		return planID;
	}
	public void setPlanID(long planID) {
		this.planID = planID;
	}
	public long getQueryType() {
		return queryType;
	}
	public void setQueryType(long queryType) {
		this.queryType = queryType;
	}
	public Timestamp getRelativeEndDate() {
		return relativeEndDate;
	}
	public void setRelativeEndDate(Timestamp relativeEndDate) {
		this.relativeEndDate = relativeEndDate;
	}
	public Timestamp getRelativeStartDate() {
		return relativeStartDate;
	}
	public void setRelativeStartDate(Timestamp relativeStartDate) {
		this.relativeStartDate = relativeStartDate;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getStrItemId() {
		return strItemId;
	}
	public void setStrItemId(String strItemId) {
		this.strItemId = strItemId;
	}
	public long[] getShowColumn() {
		return showColumn;
	}
	public void setShowColumn(long[] showColumn) {
		this.showColumn = showColumn;
	}

    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID() {
        return currencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID) {
        this.currencyID = currencyID;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID() {
        return officeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID) {
        this.officeID = officeID;
    }
    /**
     * @return Returns the budgetFlag.
     */
    public long getBudgetFlag() {
        return budgetFlag;
    }
    /**
     * @param budgetFlag The budgetFlag to set.
     */
    public void setBudgetFlag(long budgetFlag) {
        this.budgetFlag = budgetFlag;
    }
    
    public Object clone()
    {
        QueryBudgetInfo info = new QueryBudgetInfo();
        info.setBudgetSystemID(getBudgetSystemID());
        info.setClientID(getClientID());
        info.setBudgetPeriodID(getBudgetPeriodID());
        info.setBudgetFlag(getBudgetFlag());
        info.setStartDate(getStartDate());
        info.setEndDate(getEndDate());
        info.setVersionNo(getVersionNo());
        info.setShowColumn(getShowColumn());
        info.setItemID(getItemID());
        return  info;
    }
}
