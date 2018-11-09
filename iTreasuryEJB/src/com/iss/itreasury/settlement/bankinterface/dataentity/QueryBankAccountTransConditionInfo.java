package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryBankAccountTransConditionInfo implements Serializable,Cloneable
{
	//排序列编号
	private long Order = 0;
	//升降序标识
	private long Desc =-1;
	private Timestamp StartDate = null;      //开始日期      
	private Timestamp EndDate = null;        //结束日期	    
	private double AmountFrom = 0.0;             //金额      
	private double AmountTo = 0.0;               //金额  
	
	private long OfficeID = -1;               //办事处 
	private long CurrencyID = -1;             //币种	
	private long BranchID = -1;               //开户行id	
	private long BankType = -1;	               //银行种类
	private String[] BankAccountNos = null;       //银行账号
	private long UpClientID = -1;                //所属单位
	//8.12新增
	private long ClientID=-1;
	
	private long FilialeBankAccountType = -1;   //账户类型（收入，支出）
	private long TransDirection =-1;            //交易收付方向 
	
	//上海电气新增
	private long[] turnInResult = null; //入账结果状态
	private long  isDeleteByBank = -1 ;//是否被银行删除
	
	//报表加(qijiang 2005-08-19)
	private String oppositeAccountNo = "";		//下属单位账号
	
	//加(qijiang 2005-08-22)
	private long ID = -1;						//id
	private long autoTurnInResult = -1;			//银行返回结果
	private long isToTurnIn = -1;				//是否主动上收
	
	//克隆
	public Object clone()
	{
		QueryBankAccountTransConditionInfo o = null;
		try{
			o = (QueryBankAccountTransConditionInfo)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * Constructor for QueryBankAccontTransConditionInfo.
	 */
	public QueryBankAccountTransConditionInfo()
	{
		super();
	}
	
	/**
	 * @return
	 */
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * @return
	 */
	public long getOrder()
	{
		return Order;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		Desc = l;
	}

	/**
	 * @param l
	 */
	public void setOrder(long l)
	{
		Order = l;
	}
	/**
	 * @return
	 */
	public double getAmountFrom() {
		return AmountFrom;
	}

	/**
	 * @return
	 */
	public double getAmountTo() {
		return AmountTo;
	}

	/**
	 * @return
	 */
	public String[] getBankAccountNos() {
		return BankAccountNos;
	}

	/**
	 * @return
	 */
	public long getBankType() {
		return BankType;
	}

	/**
	 * @return
	 */
	public long getBranchID() {
		return BranchID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate() {
		return EndDate;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate() {
		return StartDate;
	}

	/**
	 * @param d
	 */
	public void setAmountFrom(double d) {
		AmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setAmountTo(double d) {
		AmountTo = d;
	}

	/**
	 * @param string
	 */
	public void setBankAccountNos(String[] string) {
		BankAccountNos = string;
	}

	/**
	 * @param l
	 */
	public void setBankType(long l) {
		BankType = l;
	}

	/**
	 * @param l
	 */
	public void setBranchID(long l) {
		BranchID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp) {
		EndDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp) {
		StartDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getTransDerection() {
		return TransDirection;
	}

	/**
	 * @param l
	 */
	public void setTransDerection(long l) {
		TransDirection = l;
	}


	/**
	 * @return
	 */
	public long getUpClientID() {
		return UpClientID;
	}

	/**
	 * @return
	 */
	public long getFilialeBankAccountType() {
		return FilialeBankAccountType;
	}

	/**
	 * @return
	 */
	public long getTransDirection() {
		return TransDirection;
	}

	/**
	 * @param l
	 */
	public void setUpClientID(long l) {
		UpClientID = l;
	}

	/**
	 * @param l
	 */
	public void setFilialeBankAccountType(long l) {
		FilialeBankAccountType = l;
	}

	/**
	 * @param l
	 */
	public void setTransDirection(long l) {
		TransDirection = l;
	}

	/**
	 * Returns the turnInResult.
	 * @return long
	 */
	public long[] getTurnInResult()
	{		
		return turnInResult;
	}

	/**
	 * Sets the turnInResult.
	 * @param turnInResult The turnInResult to set
	 */
	public void setTurnInResult(long[] turnInResult)
	{
		this.turnInResult = turnInResult;
	}

    /**
     * @return Returns the isDeleteByBank.
     */
    public long getIsDeleteByBank() {
        return isDeleteByBank;
    }
    /**
     * @param isDeleteByBank The isDeleteByBank to set.
     */
    public void setIsDeleteByBank(long isDeleteByBank) {
        this.isDeleteByBank = isDeleteByBank;
    }

	public long getClientID() {
		return ClientID;
	}

	public void setClientID(long clientID) {
		ClientID = clientID;
	}

	/**
	 * @return Returns the oppositeAccountNo.
	 */
	public String getOppositeAccountNo() {
		return oppositeAccountNo;
	}

	/**
	 * @param oppositeAccountNo The oppositeAccountNo to set.
	 */
	public void setOppositeAccountNo(String oppositeAccountNo) {
		this.oppositeAccountNo = oppositeAccountNo;
	}

	/**
	 * @return Returns the autoTurnInResult.
	 */
	public long getAutoTurnInResult() {
		return autoTurnInResult;
	}

	/**
	 * @param autoTurnInResult The autoTurnInResult to set.
	 */
	public void setAutoTurnInResult(long autoTurnInResult) {
		this.autoTurnInResult = autoTurnInResult;
	}

	/**
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}

	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		ID = id;
	}

	/**
	 * @return Returns the isToTurnIn.
	 */
	public long getIsToTurnIn() {
		return isToTurnIn;
	}

	/**
	 * @param isToTurnIn The isToTurnIn to set.
	 */
	public void setIsToTurnIn(long isToTurnIn) {
		this.isToTurnIn = isToTurnIn;
	}
}
