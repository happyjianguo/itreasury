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

    private long budgetSystemID = -1;		//预算系统ID
    private long clientID = -1;				//客户ID    
    private long budgetPeriodID = -1;		//预算周期ID
    private Timestamp startDate = null;		//预算区间开始日期
    private Timestamp endDate = null;		//预算区间结束日期
    private long budgetFlag= -1;     		//预算标志 0 正常预算 1 调整预算 2 汇总预算

    private Timestamp relativeStartDate = null;		//对比预算区间开始日期
    private Timestamp  relativeEndDate = null;		//对比预算区间结束日期
    private long budgetUnit = -1;                //计量单位

    private String versionNo = "";			//预算版本号
    private long queryType = -1;			//查询类别  1,预算版本查询 2,预算调整查询 3,预算历史分析查询
    private long planID = -1;				//预算ID
    private long itemID = -1;				//预算项目
    private long isContainLower = -1;		//是否包括下级单位
    private String strItemId="";            //选择的项目组成的字符串

    
    //预算单位对比分析所用字段
    private long clientBID = -1;			//B客户ID
    private ArrayList itemInfo = null;  //存放单位情况分析条件中的项目
    
    private long officeID = -1;
    private long currencyID = -1;
    
    //查询编制时用的字段
    private long[] showColumn = null;	//显示哪些列 BUDGETConstant.BudgetColumnList类
	
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
