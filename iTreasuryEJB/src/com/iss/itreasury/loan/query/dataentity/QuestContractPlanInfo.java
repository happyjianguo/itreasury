/*
 * QuestContractPlanInfo.java
 *
 * Created on 2002年4月9日, 下午4:56
 */

package com.iss.itreasury.loan.query.dataentity;

import java.sql.*;

/**
 *
 * @author  yzhang
 * @version
 */
public class QuestContractPlanInfo implements java.io.Serializable {

	/** Creates new QuestContractPlanInfo */
	public QuestContractPlanInfo() {
		super();
	}

    //合同标示
    private long lContractID=-1;

	//合同编号
    private String ContractCode;

    //下一级审核人
    private String nextCheckUserName = "";
	//计划标示
	private long PlanID;

	//版本号
	private long PlanVersion;

	//版本日期及时间
	public Timestamp InputDate;

	//修改人
	private String Modifier;

	//总页数
	public long lAllPage;

	//总纪录数
	public long lAllRecord;

	//当前时间
	public Timestamp tsNow;

	public long lType12 = -1; // 12种贷款类型
    /**
     * @param
     * function 得到/设置
     * return long
     */
    public long getPlanID()
    {
        return PlanID;
    }

    /**
     * @param
     * function 得到/设置
     * return void
     */
    public void setPlanID(long l)
    {
        PlanID = l;
    }

    /**
     * @param
     * function 得到/设置
     * return long
     */
    public long getPlanVersion()
    {
        return PlanVersion;
    }

    /**
     * @param
     * function 得到/设置
     * return void
     */
    public void setPlanVersion(long l)
    {
        PlanVersion = l;
    }

    /**
     * @param
     * function 得到/设置
     * return String
     */
    public String getModifier()
    {
        return Modifier;
    }

    /**
     * @param
     * function 得到/设置
     * return void
     */
    public void setModifier(String string)
    {
        Modifier = string;
    }

    /**
     * @param
     * function 得到/设置
     * return String
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @param
     * function 得到/设置
     * return void
     */
    public void setContractCode(String string)
    {
        this.ContractCode = string;
    }

    /**
     * @param
     * function 得到/设置
     * return long
     */
    public long getContractID()
    {
        return lContractID;
    }

    /**
     * @param
     * function 得到/设置
     * return void
     */
    public void setContractID(long l)
    {
        this.lContractID = l;
    }

    /**
     * function 得到/设置版本日期及时间
     * return Timestamp
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }

    /**
     * @param ts
     * function 得到/设置版本日期及时间
     * return void
     */
    public void setInputDate(Timestamp ts)
    {
        this.InputDate = ts;
    }
    /**
     * @return
     */
    public String getNextCheckUserName()
    {
            return nextCheckUserName;
    }

    /**
     * @param string
     */
    public void setNextCheckUserName(String string)
    {
            nextCheckUserName = string;
    }

}