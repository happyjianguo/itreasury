/*
 * Created on 2003-9-11
 *
 */
package com.iss.itreasury.settlement.generalledger.dataentity;

import java.io.Serializable;
import java.sql.*;
import com.iss.itreasury.settlement.util.*;

/**
 * 
 * 总账定义信息
 * @author yqwu 
 * 
 */
public class GLSubjectDefinitionInfo implements Serializable
{
    long ID = -1;

    long OfficeID = -1; //机构ID
    long CurrencyID = -1; //币种ID

    String SegmentCode1 = ""; //第一段
    String SegmentCode2 = ""; //科目代码
    String SegmentCode3 = ""; //第三段
    String SegmentCode4 = ""; //第四段
	String SegmentCode5 = ""; //第五段
	String SegmentCode6 = ""; //第六段
	String SegmentCode7 = ""; //第七段
	//add by xuteng
	String subcode = ""; //科目代码 

    String SegmentName1 = ""; //第一段名称
    String SegmentName2 = ""; //科目名称2,保留
    String SegmentName3 = ""; //第三段名称
    String SegmentName4 = ""; //第四段名称
	String SegmentName5 = ""; //第五段名称
	String SegmentName6 = ""; //第六段名称
	String SegmentName7 = ""; //第七段名称

    long SubjectType = -1; //科目属性
    boolean Leaf; //末级科目
    boolean Root; //总账科目

    long ParentSubjectID = -1; //上级科目代码ID,Default -1
    long BalanceDirection = -1; //控制余额方向
    long AmountDirection = 9; //控制发生额方向,Default 9
    long StatusID = -1; //状态
    long LederType = -1; //账类

    //保留属性
    long SecurityLevel = -1; //安全级别
    long UseScope = -1; //使用范围
    long Flag = -1; //保留
    Timestamp ValidDate; //生效日期

    //辅助属性
    String OfficeName = ""; //机构名
    String CurrencyName = ""; //币种名
    String subjectTypeName = ""; //科目名
    String StatusName = ""; //状态名称
    String BalanceDirectionName = ""; //控制余额方向名称
    String AmountDirectionName = ""; //控制发生额方向名称

    String SrootSubJect = "";
    String SrootSubJectName = "";
    String SsubjectCode = "";
    String SsubjectName = "";
    //add by xuteng
    //排序
    long orderBy = 1; //默认科目号
    long desc = -1; 
    
    boolean Customer = false;//是否需要客户辅助项目
    
    public long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(long orderBy) {
		this.orderBy = orderBy;
	}

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}

	public String getSrootSubJect() 
    {
		return SrootSubJect;
	}

	public void setSrootSubJect(String srootSubJect)
	{
		SrootSubJect = srootSubJect;
	}

	public String getSrootSubJectName() 
	{
		return SrootSubJectName;
	}

	public void setSrootSubJectName(String srootSubJectName) 
	{
		SrootSubJectName = srootSubJectName;
	}

	public String getSsubjectCode() 
	{
		return SsubjectCode;
	}

	public void setSsubjectCode(String ssubjectCode) 
	{
		SsubjectCode = ssubjectCode;
	}

	public String getSsubjectName() 
	{
		return SsubjectName;
	}

	public void setSsubjectName(String ssubjectName) 
	{
		SsubjectName = ssubjectName;
	}

	/**
     * @return
     */
    public long getAmountDirection()
    {
        return AmountDirection;
    }

    /**
     * @return
     */
    public long getBalanceDirection()
    {
        return BalanceDirection;
    }

    /**
     * @return
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @return
     */
    public long getFlag()
    {
        return Flag;
    }

    /**
     * @return
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @return
     */
    public boolean isLeaf()
    {
        return Leaf;
    }

    /**
     * @return
     */
    public long getLederType()
    {
        return LederType;
    }

    /**
     * @return
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @return
     */
    public long getParentSubjectID()
    {
        return ParentSubjectID;
    }

    /**
     * @return
     */
    public boolean isRoot()
    {
        return Root;
    }

    /**
     * @return
     */
    public long getSecurityLevel()
    {
        return SecurityLevel;
    }

    /**
     * @return
     */
    public String getSegmentCode1()
    {
        return SegmentCode1;
    }

    /**
     * @return
     */
    public String getSegmentCode3()
    {
        return SegmentCode3;
    }

    /**
     * @return
     */
    public String getSegmentCode4()
    {
        return SegmentCode4;
    }

    /**
     * @return
     */
    public String getSegmentName1()
    {
        return SegmentName1;
    }

    /**
     * @return
     */
    public String getSegmentName3()
    {
        return SegmentName3;
    }

    /**
     * @return
     */
    public String getSegmentName4()
    {
        return SegmentName4;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @return
     */
    public String getSegmentCode2()
    {
        return SegmentCode2;
    }

    /**
     * @return
     */
    public String getSegmentName2()
    {
        return SegmentName2;
    }

    /**
     * @return
     */
    public long getSubjectType()
    {
        return SubjectType;
    }

    /**
     * @return
     */
    public long getUseScope()
    {
        return UseScope;
    }

    /**
     * @return
     */
    public Timestamp getValidDate()
    {
        return ValidDate;
    }

    /**
     * @param l
     */
    public void setAmountDirection(long l)
    {
        AmountDirection = l;
        this.BalanceDirectionName = SETTConstant.ControlDirection.getName(l);
    }

    /**
     * @param l
     */
    public void setBalanceDirection(long l)
    {
        BalanceDirection = l;
        this.BalanceDirectionName = SETTConstant.ControlDirection.getName(l);
    }

    /**
     * @param l
     */
    public void setCurrencyID(long l)
    {
        CurrencyID = l;
        this.CurrencyName = SETTConstant.CurrencyType.getName(l);
    }

    /**
     * @param l
     */
    public void setFlag(long l)
    {
        Flag = l;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param b
     */
    public void setLeaf(boolean b)
    {
        Leaf = b;
    }

    /**
     * @param l
     */
    public void setLederType(long l)
    {
        LederType = l;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;

        //在NameRef相应方法取消Exception后取消try block
        try
        {
            NameRef.getOfficeNameByID(l);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @param l
     */
    public void setParentSubjectID(long l)
    {
        ParentSubjectID = l;
    }

    /**
     * @param b
     */
    public void setRoot(boolean b)
    {
        Root = b;
    }

    /**
     * @param l
     */
    public void setSecurityLevel(long l)
    {
        SecurityLevel = l;
    }

    /**
     * @param string
     */
    public void setSegmentCode1(String string)
    {
        SegmentCode1 = string;
    }

    /**
     * @param string
     */
    public void setSegmentCode3(String string)
    {
        SegmentCode3 = string;
    }

    /**
     * @param string
     */
    public void setSegmentCode4(String string)
    {
        SegmentCode4 = string;
    }

    /**
     * @param string
     */
    public void setSegmentName1(String string)
    {
        SegmentName1 = string;
    }

    /**
     * @param string
     */
    public void setSegmentName3(String string)
    {
        SegmentName3 = string;
    }

    /**
     * @param string
     */
    public void setSegmentName4(String string)
    {
        SegmentName4 = string;
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        StatusID = l;
        this.StatusName = SETTConstant.RecordStatus.getName(l);
    }

    /**
     * @param string
     */
    public void setSegmentCode2(String string)
    {
        SegmentCode2 = string;
    }

    /**
     * @param string
     */
    public void setSegmentName2(String string)
    {
		SegmentName2 = string;
    }

    /**
     * @param l
     */
    public void setSubjectType(long l)
    {
        SubjectType = l;
        this.subjectTypeName = SETTConstant.SubjectAttribute.getName(l);
		setBalanceDirection(SETTConstant.SubjectAttribute.getDirection(l));
    }

    /**
     * @param l
     */
    public void setUseScope(long l)
    {
        UseScope = l;
    }

    /**
     * @param date
     */
    public void setValidDate(Timestamp date)
    {
        ValidDate = date;
    }

    /**
     * @return
     */
    public String getCurrencyName()
    {
        return CurrencyName;
    }

    /**
     * @return
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

    /**
     * @return
     */
    public String getSubjectTypeName()
    {
        return subjectTypeName;
    }
    /**
     * @return
     */
    public String getStatusName()
    {
        return StatusName;
    }

    /**
     * @return
     */
    public String getAmountDirectionName()
    {
        return AmountDirectionName;
    }

    /**
     * @return
     */
    public String getBalanceDirectionName()
    {
        return BalanceDirectionName;
    }

	/**
	 * @return
	 */
	public String getSegmentCode5()
	{
		return SegmentCode5;
	}

	/**
	 * @return
	 */
	public String getSegmentCode6()
	{
		return SegmentCode6;
	}

	/**
	 * @return
	 */
	public String getSegmentCode7()
	{
		return SegmentCode7;
	}

	/**
	 * @return
	 */
	public String getSegmentName5()
	{
		return SegmentName5;
	}

	/**
	 * @return
	 */
	public String getSegmentName6()
	{
		return SegmentName6;
	}

	/**
	 * @return
	 */
	public String getSegmentName7()
	{
		return SegmentName7;
	}

	/**
	 * @param string
	 */
	public void setSegmentCode5(String string)
	{
		SegmentCode5 = string;
	}

	/**
	 * @param string
	 */
	public void setSegmentCode6(String string)
	{
		SegmentCode6 = string;
	}

	/**
	 * @param string
	 */
	public void setSegmentCode7(String string)
	{
		SegmentCode7 = string;
	}

	/**
	 * @param string
	 */
	public void setSegmentName5(String string)
	{
		SegmentName5 = string;
	}

	/**
	 * @param string
	 */
	public void setSegmentName6(String string)
	{
		SegmentName6 = string;
	}

	/**
	 * @param string
	 */
	public void setSegmentName7(String string)
	{
		SegmentName7 = string;
	}

	/**
	 * @param string
	 */
	public void setAmountDirectionName(String string)
	{
		AmountDirectionName = string;
	}

	/**
	 * @param string
	 */
	public void setBalanceDirectionName(String string)
	{
		BalanceDirectionName = string;
	}

	/**
	 * @param string
	 */
	public void setCurrencyName(String string)
	{
		CurrencyName = string;
	}

	/**
	 * @param string
	 */
	public void setOfficeName(String string)
	{
		OfficeName = string;
	}

	/**
	 * @param string
	 */
	public void setStatusName(String string)
	{
		StatusName = string;
	}

	/**
	 * @param string
	 */
	public void setSubjectTypeName(String string)
	{
		subjectTypeName = string;
	}

	public String getSubcode() {
		return subcode;
	}

	public void setSubcode(String subcode) {
			if(subcode != null){
				String[] sub = subcode.split("\\.");
				int curIndex = 0;
				if(curIndex <= sub.length - 1 && sub[curIndex] != null && sub[curIndex].trim().length() > 0)
					SegmentCode1 = sub[curIndex];
				curIndex++;
				if(curIndex <= sub.length - 1  && sub[curIndex] != null && sub[curIndex].trim().length() > 0)
					SegmentCode2 = sub[curIndex];
				curIndex++;
					
				if(curIndex <= sub.length - 1  && sub[curIndex] != null && sub[curIndex].trim().length() > 0)
					SegmentCode3 = sub[curIndex];
				curIndex++;
				
				if(curIndex <= sub.length - 1  && sub[curIndex] != null && sub[curIndex].trim().length() > 0)
					SegmentCode4 = sub[curIndex];
				curIndex++;
				
				if(curIndex <= sub.length - 1  && sub[curIndex] != null && sub[curIndex].trim().length() > 0)
					SegmentCode5 = sub[curIndex];
				curIndex++;
				
				if(curIndex <= sub.length - 1  && sub[curIndex] != null && sub[curIndex].trim().length() > 0)
					SegmentCode6 = sub[curIndex];
				curIndex++;
				
				if(curIndex <= sub.length - 1  && sub[curIndex] != null && sub[curIndex].trim().length() > 0)
					SegmentCode7 = sub[curIndex];
				curIndex++;
			}
			
			this.subcode = subcode;
		}

	public boolean isCustomer() {
		return Customer;
	}

	public void setCustomer(boolean isCustomer) {
		this.Customer = isCustomer;
	}
	}
