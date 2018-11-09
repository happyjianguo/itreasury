/*
 * Created on 2003-10-28
 *
 * InterestEstimateInfo.java
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;
import java.lang.reflect.Field;
import java.lang.reflect.AccessibleObject;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * @author xrli
 *
 * 利息匡算页面查询信息实体。
 * 该类主要用来保存页面的查询条件。
 * 该实体必须遵守JavaBean的规范，以便实现从JSP页面到实体的自动赋值的功能，
 * 简化应用程序的开发过程。
 * 该实体中的一些信息直接来自数据库，另外一些信息来自算息模块的计算。
 */
public class InterestEstimateQueryInfo extends BaseDataEntity implements Serializable{

	public InterestEstimateQueryInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
    private long OfficeID = -1;                      //办事处  
	private long CurrencyID = -1;                    //币种
	private long IsSelectClientNo = -1;              //是否选择客户编号   
	private String ClientNoFrom = "";                //客户编号
    private String ClientNoTo = "";                  //客户编号
    private long IsSelectClearInterestDate = -1;     //是否选择结息日期   
    private Timestamp ClearInterestDate = null;      //结息日期
    private long IsSelectSelfLoanSort = -1;          //是否选择自营贷款种类
    private long SelfLoanSort = -1;                  //自营贷款种类
    private long IsSelectSelfLoanTerm = -1;          //是否选择自营贷款期限
    private long SelfLoanTermFrom = -1;              //自营贷款期限
    private long SelfLoanTermTo = -1;                //自营贷款期限
    private long IsSelectConsignLoanSort = -1;       //是否选择委托贷款种类
    private long ConsignLoanSort = -1;               //委托贷款种类
    private long IsSelectConsigner = -1;             //是否选择委托方
    private long ConsignerIDFrom =-1 ;               //委托方ID                        
	private long ConsignerIDTo =-1 ;                 //委托方ID
    private String ConsignerFrom = "";               //委托方
    private String ConsignerTo = "";                 //委托方        
    private long IsSelectCircleLoan = -1;            //是否选择循环贷款
    private long IsSelectShortLoan = -1;             //是否选择短期贷款
    private long IsSelectContractNo = -1;            //是否选择合同号
    private String ContractNoFrom = "";              //合同号
    private String ContractNoTo = "";                //合同号          
    private long IsSelectPayFormNo = -1;             //是否选择放款通知单号
    private String PayFormNoFrom = "";               //放款通知单号
    private String PayFormNoTo = "";                 //放款通知单号
    private long IsSelectInterest = -1;              //是否选择利息
    private long IsSelectCompoundInterest = -1;      //是否选择复利
    private long IsSelectForfeitInterest = -1;       //是否选择罚息
    private long FeeType  = -1;                      //2 手续费 3 担保费
             
    //add by qianggao 2008-09-01 
    private String DepositNoFrom="";                 //存单号
    private String DepositNoTo="";                   //存单号
    	 
    
    private String AccountNoFrom = "";               //账户编号
    private String AccountNoTo = "";                 //账户编号
    private Timestamp DateFrom = null;                //日期      
    private Timestamp DateTo = null;                  //日期
    private long NoticeDays = -1;                     //通知存款品种
    private double Rate = 0.0;                        //利率 
	
	private long IsSelectUnClearInterest  = -1;       //是否选择未结息部分
	
	private long Noticetype = -1;                     //通知书类型
	
	private Vector NotcieClientOrAccount =null;       //通知书条件       
	private String subTypeIds = "";//贷款子类型ID集合
	
	private long doFilterDate = -1;                   // 是否过滤(当放款通知单的结束日期早于选择的匡算日期--匡算到的那一天之前，贷款合同已经结束了）
													  //是否过滤 1 过滤 -1 不过滤
    
    /**
     * @return a string representing the current object using reflect api.
     */
    public String toString(){
        
        Field[] allFields = getClass().getDeclaredFields();
        StringBuffer buffer = new StringBuffer();
        
        try{
            AccessibleObject.setAccessible(allFields, true);
            buffer.append("InterestEstimateInfo[");
            for(int i = 0; i < allFields.length; i++){
                buffer.append(allFields[i].getName());
                buffer.append("=");
                buffer.append(allFields[i].get(this));
                buffer.append("\n");
            }
        }catch(Exception e){
            return e.getMessage();
        }
        buffer.append("]");
        return buffer.toString();
    }  
    
    public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return
	 */
	public String getAccountNoFrom()
	{
		return AccountNoFrom;
	}

	/**
	 * @return
	 */
	public String getAccountNoTo()
	{
		return AccountNoTo;
	}

	/**
	 * @return
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * @return
	 */
	public String getClientNoFrom()
	{
		return ClientNoFrom;
	}

	/**
	 * @return
	 */
	public String getClientNoTo()
	{
		return ClientNoTo;
	}

	/**
	 * @return
	 */
	public String getConsignerFrom()
	{
		return ConsignerFrom;
	}

	/**
	 * @return
	 */
	public String getConsignerTo()
	{
		return ConsignerTo;
	}

	/**
	 * @return
	 */
	public long getConsignLoanSort()
	{
		return ConsignLoanSort;
	}

	/**
	 * @return
	 */
	public String getContractNoFrom()
	{
		return ContractNoFrom;
	}

	/**
	 * @return
	 */
	public String getContractNoTo()
	{
		return ContractNoTo;
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
	public Timestamp getDateFrom()
	{
		return DateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getDateTo()
	{
		return DateTo;
	}

	/**
	 * @return
	 */
	public long getFeeType()
	{
		return FeeType;
	}

	/**
	 * @return
	 */
	public long getIsSelectCircleLoan()
	{
		return IsSelectCircleLoan;
	}

	/**
	 * @return
	 */
	public long getIsSelectClearInterestDate()
	{
		return IsSelectClearInterestDate;
	}

	/**
	 * @return
	 */
	public long getIsSelectClientNo()
	{
		return IsSelectClientNo;
	}

	/**
	 * @return
	 */
	public long getIsSelectCompoundInterest()
	{
		return IsSelectCompoundInterest;
	}

	/**
	 * @return
	 */
	public long getIsSelectConsigner()
	{
		return IsSelectConsigner;
	}

	/**
	 * @return
	 */
	public long getIsSelectConsignLoanSort()
	{
		return IsSelectConsignLoanSort;
	}

	/**
	 * @return
	 */
	public long getIsSelectContractNo()
	{
		return IsSelectContractNo;
	}

	/**
	 * @return
	 */
	public long getIsSelectForfeitInterest()
	{
		return IsSelectForfeitInterest;
	}

	/**
	 * @return
	 */
	public long getIsSelectInterest()
	{
		return IsSelectInterest;
	}

	/**
	 * @return
	 */
	public long getIsSelectPayFormNo()
	{
		return IsSelectPayFormNo;
	}

	/**
	 * @return
	 */
	public long getIsSelectSelfLoanSort()
	{
		return IsSelectSelfLoanSort;
	}

	/**
	 * @return
	 */
	public long getIsSelectSelfLoanTerm()
	{
		return IsSelectSelfLoanTerm;
	}

	/**
	 * @return
	 */
	public long getIsSelectShortLoan()
	{
		return IsSelectShortLoan;
	}

	/**
	 * @return
	 */
	public long getIsSelectUnClearInterest()
	{
		return IsSelectUnClearInterest;
	}

	/**
	 * @return
	 */
	public long getNoticeDays()
	{
		return NoticeDays;
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
	public String getPayFormNoFrom()
	{
		return PayFormNoFrom;
	}

	/**
	 * @return
	 */
	public String getPayFormNoTo()
	{
		return PayFormNoTo;
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}

	/**
	 * @return
	 */
	public long getSelfLoanSort()
	{
		return SelfLoanSort;
	}

	/**
	 * @return
	 */
	public long getSelfLoanTermFrom()
	{
		return SelfLoanTermFrom;
	}

	/**
	 * @return
	 */
	public long getSelfLoanTermTo()
	{
		return SelfLoanTermTo;
	}

	/**
	 * @param string
	 */
	public void setAccountNoFrom(String string)
	{
		AccountNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNoTo(String string)
	{
		AccountNoTo = string;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterestDate(Timestamp timestamp)
	{
		ClearInterestDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setClientNoFrom(String string)
	{
		ClientNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setClientNoTo(String string)
	{
		ClientNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setConsignerFrom(String string)
	{
		ConsignerFrom = string;
	}

	/**
	 * @param string
	 */
	public void setConsignerTo(String string)
	{
		ConsignerTo = string;
	}

	/**
	 * @param l
	 */
	public void setConsignLoanSort(long l)
	{
		ConsignLoanSort = l;
	}

	/**
	 * @param string
	 */
	public void setContractNoFrom(String string)
	{
		ContractNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setContractNoTo(String string)
	{
		ContractNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setDateFrom(Timestamp timestamp)
	{
		DateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setDateTo(Timestamp timestamp)
	{
		DateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setFeeType(long l)
	{
		FeeType = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectCircleLoan(long l)
	{
		IsSelectCircleLoan = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectClearInterestDate(long l)
	{
		IsSelectClearInterestDate = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectClientNo(long l)
	{
		IsSelectClientNo = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectCompoundInterest(long l)
	{
		IsSelectCompoundInterest = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectConsigner(long l)
	{
		IsSelectConsigner = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectConsignLoanSort(long l)
	{
		IsSelectConsignLoanSort = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectContractNo(long l)
	{
		IsSelectContractNo = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectForfeitInterest(long l)
	{
		IsSelectForfeitInterest = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectInterest(long l)
	{
		IsSelectInterest = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectPayFormNo(long l)
	{
		IsSelectPayFormNo = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectSelfLoanSort(long l)
	{
		IsSelectSelfLoanSort = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectSelfLoanTerm(long l)
	{
		IsSelectSelfLoanTerm = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectShortLoan(long l)
	{
		IsSelectShortLoan = l;
	}

	/**
	 * @param l
	 */
	public void setIsSelectUnClearInterest(long l)
	{
		IsSelectUnClearInterest = l;
	}

	/**
	 * @param l
	 */
	public void setNoticeDays(long l)
	{
		NoticeDays = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param string
	 */
	public void setPayFormNoFrom(String string)
	{
		PayFormNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setPayFormNoTo(String string)
	{
		PayFormNoTo = string;
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}

	/**
	 * @param l
	 */
	public void setSelfLoanSort(long l)
	{
		SelfLoanSort = l;
	}

	/**
	 * @param l
	 */
	public void setSelfLoanTermFrom(long l)
	{
		SelfLoanTermFrom = l;
	}

	/**
	 * @param l
	 */
	public void setSelfLoanTermTo(long l)
	{
		SelfLoanTermTo = l;
	}

	/**
	 * @return
	 */
	public long getNoticetype()
	{
		return Noticetype;
	}

	/**
	 * @param l
	 */
	public void setNoticetype(long l)
	{
		Noticetype = l;
	}

	/**
	 * @return
	 */
	public long getConsignerIDFrom()
	{
		return ConsignerIDFrom;
	}

	/**
	 * @return
	 */
	public long getConsignerIDTo()
	{
		return ConsignerIDTo;
	}

	/**
	 * @param l
	 */
	public void setConsignerIDFrom(long l)
	{
		ConsignerIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setConsignerIDTo(long l)
	{
		ConsignerIDTo = l;
	}

	/**
	 * @return
	 */
	public Vector getNotcieClientOrAccount() {
		return NotcieClientOrAccount;
	}

	/**
	 * @param vector
	 */
	public void setNotcieClientOrAccount(Vector vector) {
		NotcieClientOrAccount = vector;
	}


	public String getDepositNoFrom() {
		return DepositNoFrom;
	}


	public void setDepositNoFrom(String depositNoFrom) {
		DepositNoFrom = depositNoFrom;
	}


	public String getDepositNoTo() {
		return DepositNoTo;
	}


	public void setDepositNoTo(String depositNoTo) {
		DepositNoTo = depositNoTo;
	}


	public long getDoFilterDate() {
		return doFilterDate;
	}


	public void setDoFilterDate(long doFilterDate) {
		this.doFilterDate = doFilterDate;
	}


	public String getSubTypeIds() {
		return subTypeIds;
	}


	public void setSubTypeIds(String subTypeIds) {
		this.subTypeIds = subTypeIds;
	}

}
