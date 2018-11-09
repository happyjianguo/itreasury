/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.treasuryplan.reportapproval.dataentity;

import com.iss.itreasury.treasuryplan.util.*;
import com.iss.itreasury.util.*;
import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TreasuryPlanInfo extends TreasuryPlanBaseDataEntity
{
	
	private long id = -1;				//ID
	private long officeId = -1;			//机构ID
	private long currencyId	= -1;		//币种ID
	private long departmentId = -1;		//编制部门
	private Timestamp planDate = null;	//计划编制日期
	private Timestamp startDate = null;	//计划起日
	private Timestamp endDate = null;	//计划止日
	private long amountUnit	= -1;		//金额单位
	private long nextCheckUserId = -1; 	//下一级审核人
	private long nextCheckLevel = -1; 	//下一级审核级别
	private long inputUserId = -1; 		//录入人
	private Timestamp inputDate = null; //录入时间
	private long updateUserId = -1; 	//修改人
	private Timestamp updateDate = null; //修改时间
	private long statusId = -1; 		//状态
	private Timestamp timestamp = null; //实践戳
	private long isCompany = Constant.YesOrNo.NO;	//是否公司
	
	private Timestamp checkDate =null;
	
	private long approvalId = -1; //审批设置ID
	private long recordCount = 0; //记录数
	private long pageCount = 0; //页数
	
	private String code=""; 		//版本编号
	private String lastCheckUserId = "";		//最后审核人
    private String sRemark = "";
    private long gatherType = -1;
    public long getGatherType() {
		return gatherType;
	}
	public void setGatherType(long gatherType) {
		this.gatherType = gatherType;
	    putUsedField("gatherType", gatherType);
	}
	public String getSRemark() {
		return sRemark;
	}
	public void setSRemark(String remark) {
		sRemark = remark;
	  
	}
	/**
     * @return Returns the amountUnit.
     */
    public long getAmountUnit()
    {
        return amountUnit;
    }
    /**
     * @param amountUnit The amountUnit to set.
     */
    public void setAmountUnit(long amountUnit)
    {
        this.amountUnit = amountUnit;
        putUsedField("amountUnit", amountUnit);
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        this.endDate = endDate;
        putUsedField("endDate", endDate);
    }
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
        putUsedField("id", id);
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
        putUsedField("inputDate", inputDate);
    }
    /**
     * @return Returns the inputUserId.
     */
    public long getInputUserId()
    {
        return inputUserId;
    }
    /**
     * @param inputUserId The inputUserId to set.
     */
    public void setInputUserId(long inputUserId)
    {
        this.inputUserId = inputUserId;
        putUsedField("inputUserId", inputUserId);
    }    
    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return nextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        this.nextCheckLevel = nextCheckLevel;
        putUsedField("nextCheckLevel", nextCheckLevel);
    }
    /**
     * @return Returns the nextCheckUserId.
     */
    public long getNextCheckUserId()
    {
        return nextCheckUserId;
    }
    /**
     * @param nextCheckUserId The nextCheckUserId to set.
     */
    public void setNextCheckUserId(long nextCheckUserId)
    {
        this.nextCheckUserId = nextCheckUserId;
        putUsedField("nextCheckUserId", nextCheckUserId);
    }
    /**
     * @return Returns the pageCount.
     */
    public long getPageCount()
    {
        return pageCount;
    }
    /**
     * @param pageCount The pageCount to set.
     */
    public void setPageCount(long pageCount)
    {
        this.pageCount = pageCount;
    }
    /**
     * @return Returns the planDate.
     */
    public Timestamp getPlanDate()
    {
        return planDate;
    }
    /**
     * @param planDate The planDate to set.
     */
    public void setPlanDate(Timestamp planDate)
    {
        this.planDate = planDate;
        putUsedField("planDate", planDate);
    }
    /**
     * @return Returns the recordCount.
     */
    public long getRecordCount()
    {
        return recordCount;
    }
    /**
     * @param recordCount The recordCount to set.
     */
    public void setRecordCount(long recordCount)
    {
        this.recordCount = recordCount;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        this.startDate = startDate;
        putUsedField("startDate", startDate);
    }
    /**
     * @return Returns the statusId.
     */
    public long getStatusId()
    {
        return statusId;
    }
    /**
     * @param statusId The statusId to set.
     */
    public void setStatusId(long statusId)
    {
        this.statusId = statusId;
        putUsedField("statusId", statusId);
    }
    /**
     * @return Returns the timestamp.
     */
    public Timestamp getTimestamp()
    {
        return timestamp;
    }
    /**
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
        putUsedField("timestamp", timestamp);
    }
    /**
     * @return Returns the updateDate.
     */
    public Timestamp getUpdateDate()
    {
        return updateDate;
    }
    /**
     * @param updateDate The updateDate to set.
     */
    public void setUpdateDate(Timestamp updateDate)
    {
        this.updateDate = updateDate;
        putUsedField("updateDate", updateDate);
    }
    /**
     * @return Returns the updateUserId.
     */
    public long getUpdateUserId()
    {
        return updateUserId;
    }
    /**
     * @param updateUserId The updateUserId to set.
     */
    public void setUpdateUserId(long updateUserId)
    {
        this.updateUserId = updateUserId;
        putUsedField("updateUserId", updateUserId);
    }
    /**
     * @return Returns the isCompany.
     */
    public long getIsCompany()
    {
        return isCompany;
    }
    /**
     * @param isCompany The isCompany to set.
     */
    public void setIsCompany(long isCompany)
    {
        this.isCompany = isCompany;
        putUsedField("isCompany", isCompany);
    }
    /**
     * @return Returns the approvalId.
     */
    public long getApprovalId()
    {
        return approvalId;
    }
    /**
     * @param approvalId The approvalId to set.
     */
    public void setApprovalId(long approvalId)
    {
        this.approvalId = approvalId;
        putUsedField("approvalId", approvalId);
    }
    /**
     * @return Returns the currencyId.
     */
    public long getCurrencyId()
    {
        return currencyId;
    }
    /**
     * @param currencyId The currencyId to set.
     */
    public void setCurrencyId(long currencyId)
    {
        this.currencyId = currencyId;
        putUsedField("currencyId", currencyId);
    }
    /**
     * @return Returns the departmentId.
     */
    public long getDepartmentId()
    {
        return departmentId;
    }
    /**
     * @param departmentId The departmentId to set.
     */
    public void setDepartmentId(long departmentId)
    {
        this.departmentId = departmentId;
        putUsedField("departmentId", departmentId);
    }
    /**
     * @return Returns the officeId.
     */
    public long getOfficeId()
    {
        return officeId;
    }
    /**
     * @param officeId The officeId to set.
     */
    public void setOfficeId(long officeId)
    {
        this.officeId = officeId;
        putUsedField("officeId", officeId);
    }
	/**
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		code = string;
		putUsedField("code", code);
	}

	/**
	 * @return
	 */
	public String getLastCheckUserId()
	{
		return lastCheckUserId;
	}

	/**
	 * @param l
	 */
	public void setLastCheckUserId(String l)
	{
		lastCheckUserId = l;
	}

	/**
	 * @return
	 */
	public Timestamp getCheckDate()
	{
		return checkDate;
	}

	/**
	 * @param timestamp
	 */
	public void setCheckDate(Timestamp timestamp)
	{
		checkDate = timestamp;
		putUsedField("checkDate", checkDate);
	}

}
