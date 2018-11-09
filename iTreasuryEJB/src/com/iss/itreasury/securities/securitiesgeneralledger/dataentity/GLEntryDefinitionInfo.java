/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.securities.securitiesgeneralledger.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GLEntryDefinitionInfo extends SECBaseDataEntity
{
	private long id = -1;                     //记录的ID
	private long recordId = -1;               //记录的ID
	private long officeID = -1;               //办事处的ID
	private long currencyID = -1;             //币种
	private long businessAttributeId = -1;    //业务性质                
	private long businessTypeId = -1;         //业务类型
	private long transactionTypeId = -1;      //交易类型
	private long subTransactionType = -1;     //交易子类型（备用）  
	private long capitalType = -1;            //资金流向（备用）
	private long entryType = -1;              //分录类型（备用）
	private long direction = -1;              //借贷方向
	private long subjectType = -1;            //科目类型
	private String subjectCode = null;        //科目号
	private long subjectId = -1;              //科目号标识
	private long amountDirection = -1;        //金额方向 
	private long amountType = -1;             //金额类型
	private String remark = null;             //默认摘要
	private long statusID = -1;               //状态 
	private long inputUserID = -1;            //录入人 
	private Timestamp inputDate = null;       //录入时间 
	private long updateUserID = -1;           //修改人 
	private Timestamp updateDate = null;      //修改时间
	private long checkUserID = -1;            //复核人
	private Timestamp checkDate = null;       //复核时间
	private long orderType = -1;              //排序方式
	private long descOrAsc = -1;              //升序或降序排列
	/**
	 * @return Returns the amountDirection.
	 */
	public long getAmountDirection()
	{
		return amountDirection;
	}
	/**
	 * @param amountDirection The amountDirection to set.
	 */
	public void setAmountDirection(long amountDirection)
	{
		putUsedField("amountDirection", amountDirection);
		this.amountDirection = amountDirection;
	}
	/**
	 * @return Returns the amountType.
	 */
	public long getAmountType()
	{
		return amountType;
	}
	/**
	 * @param amountType The amountType to set.
	 */
	public void setAmountType(long amountType)
	{
		putUsedField("amountType", amountType);
		this.amountType = amountType;
	}
	/**
	 * @return Returns the businessTypeID.
	 */
	public long getBusinessTypeId()
	{
		return businessTypeId;
	}
	/**
	 * @param businessTypeID The businessTypeID to set.
	 */
	public void setBusinessTypeId(long businessTypeID)
	{
		putUsedField("businessTypeID", businessTypeID);
		this.businessTypeId = businessTypeID;
	}
	/**
	 * @return Returns the capitalType.
	 */
	public long getCapitalType()
	{
		return capitalType;
	}
	/**
	 * @param capitalType The capitalType to set.
	 */
	public void setCapitalType(long capitalType)
	{
		putUsedField("capitalType", capitalType);
		this.capitalType = capitalType;
	}
	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate()
	{
		return checkDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate)
	{
		putUsedField("checkDate", checkDate);
		this.checkDate = checkDate;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID()
	{
		return checkUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID)
	{
		putUsedField("checkUserID", checkUserID);
		this.checkUserID = checkUserID;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the direction.
	 */
	public long getDirection()
	{
		return direction;
	}
	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(long direction)
	{
		putUsedField("direction", direction);
		this.direction = direction;
	}
	/**
	 * @return Returns the entryType.
	 */
	public long getEntryType()
	{
		return entryType;
	}
	/**
	 * @param entryType The entryType to set.
	 */
	public void setEntryType(long entryType)
	{
		putUsedField("entryType", entryType);
		this.entryType = entryType;
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
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID)
	{
		putUsedField("inputUserID", inputUserID);
		this.inputUserID = inputUserID;
	}
	/**
	 * @return Returns the oficeID.
	 */
	public long getOfficeID()
	{
		return officeID;
	}
	/**
	 * @param oficeID The oficeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark()
	{
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark)
	{
		putUsedField("remark", remark);
		this.remark = remark;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID()
	{
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID)
	{
		putUsedField("statusID", statusID);
		this.statusID = statusID;
	}
	/**
	 * @return Returns the subjectCode.
	 */
	public String getSubjectCode()
	{
		return subjectCode;
	}
	/**
	 * @param subjectCode The subjectCode to set.
	 */
	public void setSubjectCode(String subjectCode)
	{
		putUsedField("subjectCode", subjectCode);
		this.subjectCode = subjectCode;
	}
	/**
	 * @return Returns the subjectType.
	 */
	public long getSubjectType()
	{
		return subjectType;
	}
	/**
	 * @param subjectType The subjectType to set.
	 */
	public void setSubjectType(long subjectType)
	{
		putUsedField("subjectType", subjectType);
		this.subjectType = subjectType;
	}
	/**
	 * @return Returns the subTransactionType.
	 */
	public long getSubTransactionType()
	{
		return subTransactionType;
	}
	/**
	 * @param subTransactionType The subTransactionType to set.
	 */
	public void setSubTransactionType(long subTransactionType)
	{
		putUsedField("subTransactionType", subTransactionType);
		this.subTransactionType = subTransactionType;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeId()
	{
		return transactionTypeId;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeId(long transactionTypeId)
	{
		putUsedField("transactionTypeID", transactionTypeId);
		this.transactionTypeId = transactionTypeId;
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
		putUsedField("updateDate", updateDate);
		this.updateDate = updateDate;
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID()
	{
		return updateUserID;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserID(long updateUserID)
	{
		putUsedField("updateUserID", updateUserID);
		this.updateUserID = updateUserID;
	}
	/**
	 * @return
	 */
	public long getOrderType()
	{
		return orderType;
	}
	/**
	 * @param l
	 */
	public void setOrderType(long l)
	{
		orderType = l;
	}
	/**
	 * @return
	 */
	public long getBusinessAttributeId()
	{
		return businessAttributeId;
	}
	/**
	 * @param l
	 */
	public void setBusinessAttributeId(long l)
	{
		businessAttributeId = l;
	}
	/**
	 * @return
	 */
	public long getSubjectId()
	{
		return subjectId;
	}
	/**
	 * @param l
	 */
	public void setSubjectId(long l)
	{
		subjectId = l;
	}
	/**
	 * @return
	 */
	public long getRecordId()
	{
		return recordId;
	}
	/**
	 * @param l
	 */
	public void setRecordId(long l)
	{
		recordId = l;
	}
	/**
	 * @return
	 */
	public long getId() 
	{
		return this.id;
	}
	/**
	 * @param l
	 */
	public void setId(long id) 
	{
		this.id = id;
		putUsedField("id", id);		
	}
	/**
	 * @return
	 */
	public long getDescOrAsc()
	{
		return descOrAsc;
	}

	/**
	 * @param l
	 */
	public void setDescOrAsc(long l)
	{
		descOrAsc = l;
	}

}
