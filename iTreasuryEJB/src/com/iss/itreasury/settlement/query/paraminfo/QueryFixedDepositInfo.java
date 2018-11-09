/*
 * Created on 2003-11-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryFixedDepositInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryFixedDepositInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long OfficeID = -1;                      //办事处  
	private long CurrencyID = -1;                    //币种
	private String ClientNoFrom = "";                //客户编号
    private String ClientNoTo = "";                  //客户编号
    private String ClientNameFrom = "";              //客户名称
    private String ClientNameTo = "";                //客户名称
    private String AccountNoFrom = "";               //账户编号
    private String AccountNoTo = "";                 //账户编号
    //add by 2012-05-18 添加指定账户编号
	private String appointAccountNo = "";
    private long DepositOrgin = -1;                  //存款来源
    private long ClientSort = -1;                    //客户分类 
    private long ClientType = -1;  					 //企业类型
    private long AccountType = -1;  				 //账户类型
    private long ParentCompany1  = -1;                //上级单位1
	private long ParentCompany2  = -1;                //上级单位2
    private long ClientManager = -1;     			  //客户经理id
	private long EnterpriseTypeID1 = -1;//客户属性1
	private long EnterpriseTypeID2 = -1;//客户属性2
	private long EnterpriseTypeID3 = -1;//客户属性3
	private long EnterpriseTypeID4 = -1;//客户属性4
	private long EnterpriseTypeID5 = -1;//客户属性5
	private long EnterpriseTypeID6 = -1;//客户属性6
    private long IsFixedDeposit = -1;                 //是否查询定期存款   
    private String FixedDepositNoFrom = "";           //定期单据号         
    private String FixedDepositNoTo = "";             //定期单据号	     
    private Timestamp FixedStartDateFrom = null;      //定期开始日期      
    private Timestamp FixedStartDateTo = null;        //定期开始日期       
	private Timestamp FixedEndDateFrom = null;        //定期结束日期         
	private Timestamp FixedEndDateTo = null;          //定期结束日期    
	private long FixedDepositStatus = -1;             //定期状态       
	private long FixedDepositTermFrom = -1;           //定期存款期限
	private long FixedDepositTermTo = -1;             //定期存款期限      
	private double FixedAmountFrom = 0.0;             //定期金额      
	private double FixedAmountTo = 0.0;               //定期金额  
	private double FixedRate = 0.0;                   //定期利率  
	private Timestamp FixedEndDate = null;            //定期截止日期  
	                                                            
	private long IsNoticeDeposit = -1;                //是否查询通知存款 
    private String NoticeDepositNoFrom = "";          //通知存款单据号
    private String NoticeDepositNoTo = "";            //通知存款单据号	
    private long NoticeDays =-1;                      //通知存款天数                         
    private Timestamp NoticeStartDateFrom = null;     //通知存款开始日期	
    private Timestamp NoticeStartDateTo = null;       //通知存款开始日期	
	private long NoticeDepositStatus = -1;	          //通知存款状态
	private double NoticeBalanceFrom = 0.0;           //通知存款余额
	private double NoticeBalanceTo = 0.0;             //通知存款余额
	private double NoticeDrawAmountFrom = 0.0;        //通知存款支取金额
	private double NoticeDrawAmountTo = 0.0;          //通知存款支取金额 
	private Timestamp NoticeEndDate = null;           //通知存款截止日期	
	
	private long ExtendAttribute1 = -1;					//扩展属性1
    private long ExtendAttribute2 = -1;					//扩展属性2
    private long ExtendAttribute3 = -1;					//扩展属性3
    private long ExtendAttribute4 = -1;					//扩展属性4
    
    private String ExtendAttribute5 = "";               //扩展属性5
    
    // 保证金存款查询
    private long IsMarginDeposit = -1;		// 是否查询保证金存款
    private String MarginDepositNoFrom = "";	// 保证金存款单据号
    private String MarginDepositNoTo = "";
    private Timestamp MarginStartDateFrom = null; // 起始日期
    private Timestamp MarginStartDateTo = null;
    private Timestamp MarginEndDateFrom = null;	// 到期日期
    private Timestamp MarginEndDateTo = null;
	private long MarginDepositStatus = -1;		// 保证金存款状态
	private double MarginAmountFrom = 0.0;		// 保证金存款金额
	private double MarginAmountTo = 0.0;
	private double MarginRate = 0.0;	// 利率
	private Timestamp MarginEndDate = null;	// 定期截止日期
	
    
	private long IsLeaching  = -1;                    //滤空    
    private long Desc = 1;                            //排序方式
    private long OrderField = 1;                      //排序字段
    
    private long DepositNoChoose = -1;				//查询存单方式选择
    
    private long unit = 1;
    private String ControlSource = null;
    
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
	public String getClientNameFrom()
	{
		return ClientNameFrom;
	}

	/**
	 * @return Returns the clientManager.
	 */
	public long getClientManager() {
		return ClientManager;
	}
	/**
	 * @param clientManager The clientManager to set.
	 */
	public void setClientManager(long clientManager) {
		ClientManager = clientManager;
	}
	/**
	 * @return
	 */
	
	public String getClientNameTo()
	{
		return ClientNameTo;
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
	public long getClientSort()
	{
		return ClientSort;
	}

	/**
	 * @return
	 */
	public long getClientType()
	{
		return ClientType;
	}

	/**
	 * @return
	 */
	public long getDepositOrgin()
	{
		return DepositOrgin;
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
	public double getFixedAmountFrom()
	{
		return FixedAmountFrom;
	}

	/**
	 * @return
	 */
	public double getFixedAmountTo()
	{
		return FixedAmountTo;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNoFrom()
	{
		return FixedDepositNoFrom;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNoTo()
	{
		return FixedDepositNoTo;
	}

	/**
	 * @return
	 */
	public long getFixedDepositStatus()
	{
		return FixedDepositStatus;
	}
	

	/**
	 * @return
	 */
	public Timestamp getFixedEndDate()
	{
		return FixedEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedEndDateFrom()
	{
		return FixedEndDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedEndDateTo()
	{
		return FixedEndDateTo;
	}

	/**
	 * @return
	 */
	public double getFixedRate()
	{
		return FixedRate;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedStartDateFrom()
	{
		return FixedStartDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedStartDateTo()
	{
		return FixedStartDateTo;
	}

	/**
	 * @return
	 */
	public long getIsFixedDeposit()
	{
		return IsFixedDeposit;
	}

	/**
	 * @return
	 */
	public long getIsLeaching()
	{
		return IsLeaching;
	}

	/**
	 * @return
	 */
	public long getIsNoticeDeposit()
	{
		return IsNoticeDeposit;
	}

	/**
	 * @return
	 */
	public double getNoticeBalanceFrom()
	{
		return NoticeBalanceFrom;
	}

	/**
	 * @return
	 */
	public double getNoticeBalanceTo()
	{
		return NoticeBalanceTo;
	}

	/**
	 * @return
	 */
	public String getNoticeDepositNoFrom()
	{
		return NoticeDepositNoFrom;
	}

	/**
	 * @return
	 */
	public String getNoticeDepositNoTo()
	{
		return NoticeDepositNoTo;
	}

	/**
	 * @return
	 */
	public long getNoticeDepositStatus()
	{
		return NoticeDepositStatus;
	}

	/**
	 * @return
	 */
	public double getNoticeDrawAmountFrom()
	{
		return NoticeDrawAmountFrom;
	}

	/**
	 * @return
	 */
	public double getNoticeDrawAmountTo()
	{
		return NoticeDrawAmountTo;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeEndDate()
	{
		return NoticeEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeStartDateFrom()
	{
		return NoticeStartDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeStartDateTo()
	{
		return NoticeStartDateTo;
	}

	/**
	 * @return
	 */
	public long getOrderField()
	{
		return OrderField;
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
	 * @param string
	 */
	public void setClientNameFrom(String string)
	{
		ClientNameFrom = string;
	}

	/**
	 * @param string
	 */
	public void setClientNameTo(String string)
	{
		ClientNameTo = string;
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
	 * @param l
	 */
	public void setClientSort(long l)
	{
		ClientSort = l;
	}

	/**
	 * @param l
	 */
	public void setClientType(long l)
	{
		ClientType = l;
	}

	/**
	 * @param l
	 */
	public void setDepositOrgin(long l)
	{
		DepositOrgin = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		Desc = l;
	}

	/**
	 * @param d
	 */
	public void setFixedAmountFrom(double d)
	{
		FixedAmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setFixedAmountTo(double d)
	{
		FixedAmountTo = d;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNoFrom(String string)
	{
		FixedDepositNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNoTo(String string)
	{
		FixedDepositNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositStatus(long l)
	{
		FixedDepositStatus = l;
	}
	

	/**
	 * @param timestamp
	 */
	public void setFixedEndDate(Timestamp timestamp)
	{
		FixedEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedEndDateFrom(Timestamp timestamp)
	{
		FixedEndDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedEndDateTo(Timestamp timestamp)
	{
		FixedEndDateTo = timestamp;
	}

	/**
	 * @param d
	 */
	public void setFixedRate(double d)
	{
		FixedRate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedStartDateFrom(Timestamp timestamp)
	{
		FixedStartDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedStartDateTo(Timestamp timestamp)
	{
		FixedStartDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setIsFixedDeposit(long l)
	{
		IsFixedDeposit = l;
	}

	/**
	 * @param l
	 */
	public void setIsLeaching(long l)
	{
		IsLeaching = l;
	}

	/**
	 * @param l
	 */
	public void setIsNoticeDeposit(long l)
	{
		IsNoticeDeposit = l;
	}

	/**
	 * @param d
	 */
	public void setNoticeBalanceFrom(double d)
	{
		NoticeBalanceFrom = d;
	}

	/**
	 * @param d
	 */
	public void setNoticeBalanceTo(double d)
	{
		NoticeBalanceTo = d;
	}

	/**
	 * @param string
	 */
	public void setNoticeDepositNoFrom(String string)
	{
		NoticeDepositNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setNoticeDepositNoTo(String string)
	{
		NoticeDepositNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setNoticeDepositStatus(long l)
	{
		NoticeDepositStatus = l;
	}

	/**
	 * @param d
	 */
	public void setNoticeDrawAmountFrom(double d)
	{
		NoticeDrawAmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setNoticeDrawAmountTo(double d)
	{
		NoticeDrawAmountTo = d;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeEndDate(Timestamp timestamp)
	{
		NoticeEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeStartDateFrom(Timestamp timestamp)
	{
		NoticeStartDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeStartDateTo(Timestamp timestamp)
	{
		NoticeStartDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l)
	{
		OrderField = l;
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
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getFixedDepositTermFrom()
	{
		return FixedDepositTermFrom;
	}

	/**
	 * @return
	 */
	public long getFixedDepositTermTo()
	{
		return FixedDepositTermTo;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositTermFrom(long l)
	{
		FixedDepositTermFrom = l;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositTermTo(long l)
	{
		FixedDepositTermTo = l;
	}

	/**
	 * @return
	 */
	public long getParentCompany1()
	{
		return ParentCompany1;
	}

	/**
	 * @return
	 */
	public long getParentCompany2()
	{
		return ParentCompany2;
	}

	/**
	 * @param l
	 */
	public void setParentCompany1(long l)
	{
		ParentCompany1 = l;
	}

	/**
	 * @param l
	 */
	public void setParentCompany2(long l)
	{
		ParentCompany2 = l;
	}

	/**
	 * @return
	 */
	public long getNoticeDays()
	{
		return NoticeDays;
	}

	/**
	 * @param l
	 */
	public void setNoticeDays(long l)
	{
		NoticeDays = l;
	}

	/**
	 * @return Returns the extendAttribute1.
	 */
	public long getExtendAttribute1()
	{
		return ExtendAttribute1;
	}
	/**
	 * @param extendAttribute1 The extendAttribute1 to set.
	 */
	public void setExtendAttribute1(long extendAttribute1)
	{
		ExtendAttribute1 = extendAttribute1;
	}
	/**
	 * @return Returns the extendAttribute2.
	 */
	public long getExtendAttribute2()
	{
		return ExtendAttribute2;
	}
	/**
	 * @param extendAttribute2 The extendAttribute2 to set.
	 */
	public void setExtendAttribute2(long extendAttribute2)
	{
		ExtendAttribute2 = extendAttribute2;
	}
	/**
	 * @return Returns the extendAttribute3.
	 */
	public long getExtendAttribute3()
	{
		return ExtendAttribute3;
	}
	/**
	 * @param extendAttribute3 The extendAttribute3 to set.
	 */
	public void setExtendAttribute3(long extendAttribute3)
	{
		ExtendAttribute3 = extendAttribute3;
	}
	/**
	 * @return Returns the extendAttribute4.
	 */
	public long getExtendAttribute4()
	{
		return ExtendAttribute4;
	}
	/**
	 * @param extendAttribute4 The extendAttribute4 to set.
	 */
	public void setExtendAttribute4(long extendAttribute4)
	{
		ExtendAttribute4 = extendAttribute4;
	}
	/**
	 * @return Returns the accountType.
	 */
	public String getExtendAttribute5() {
		return ExtendAttribute5;
	}
	/**
	 * @return Returns the accountType.
	 */
	public void setExtendAttribute5(String extendAttribute5) {
		ExtendAttribute5 = extendAttribute5;
	}
	/**
	 * @return Returns the accountType.
	 */
	public long getAccountType() {
		return AccountType;
	}
	/**
	 * @param accountType The accountType to set.
	 */
	public void setAccountType(long accountType) {
		AccountType = accountType;
	}
	
	/**
	 * @return Returns the enterpriseTypeID1.
	 */
	public long getEnterpriseTypeID1() {
		return EnterpriseTypeID1;
	}
	/**
	 * @param enterpriseTypeID1 The enterpriseTypeID1 to set.
	 */
	public void setEnterpriseTypeID1(long enterpriseTypeID1) {
		EnterpriseTypeID1 = enterpriseTypeID1;
	}
	/**
	 * @return Returns the enterpriseTypeID2.
	 */
	public long getEnterpriseTypeID2() {
		return EnterpriseTypeID2;
	}
	/**
	 * @param enterpriseTypeID2 The enterpriseTypeID2 to set.
	 */
	public void setEnterpriseTypeID2(long enterpriseTypeID2) {
		EnterpriseTypeID2 = enterpriseTypeID2;
	}
	/**
	 * @return Returns the enterpriseTypeID3.
	 */
	public long getEnterpriseTypeID3() {
		return EnterpriseTypeID3;
	}
	/**
	 * @param enterpriseTypeID3 The enterpriseTypeID3 to set.
	 */
	public void setEnterpriseTypeID3(long enterpriseTypeID3) {
		EnterpriseTypeID3 = enterpriseTypeID3;
	}
	/**
	 * @return Returns the enterpriseTypeID4.
	 */
	public long getEnterpriseTypeID4() {
		return EnterpriseTypeID4;
	}
	/**
	 * @param enterpriseTypeID4 The enterpriseTypeID4 to set.
	 */
	public void setEnterpriseTypeID4(long enterpriseTypeID4) {
		EnterpriseTypeID4 = enterpriseTypeID4;
	}
	/**
	 * @return Returns the enterpriseTypeID5.
	 */
	public long getEnterpriseTypeID5() {
		return EnterpriseTypeID5;
	}
	/**
	 * @param enterpriseTypeID5 The enterpriseTypeID5 to set.
	 */
	public void setEnterpriseTypeID5(long enterpriseTypeID5) {
		EnterpriseTypeID5 = enterpriseTypeID5;
	}
	/**
	 * @return Returns the enterpriseTypeID6.
	 */
	public long getEnterpriseTypeID6() {
		return EnterpriseTypeID6;
	}
	/**
	 * @param enterpriseTypeID6 The enterpriseTypeID6 to set.
	 */
	public void setEnterpriseTypeID6(long enterpriseTypeID6) {
		EnterpriseTypeID6 = enterpriseTypeID6;
	}
	/**
	 * @return Returns the depositNoChoose.
	 */
	public long getDepositNoChoose() {
		return DepositNoChoose;
	}
	/**
	 * @param depositNoChoose The depositNoChoose to set.
	 */
	public void setDepositNoChoose(long depositNoChoose) {
		DepositNoChoose = depositNoChoose;
	}

	/**
	 * 是否查询保证金存款
	 * @return 返回 isMarginDeposit。
	 */
	public long getIsMarginDeposit() {
		return IsMarginDeposit;
	}

	/**
	 * 是否查询保证金存款
	 * @param isMarginDeposit 要设置的 isMarginDeposit。
	 */
	public void setIsMarginDeposit(long isMarginDeposit) {
		IsMarginDeposit = isMarginDeposit;
	}

	/**
	 * 保证金存款金额 - 起始
	 * @return 返回 marginAmountFrom。
	 */
	public double getMarginAmountFrom() {
		return MarginAmountFrom;
	}

	/**
	 * 保证金存款金额 - 起始
	 * @param marginAmountFrom 要设置的 marginAmountFrom。
	 */
	public void setMarginAmountFrom(double marginAmountFrom) {
		MarginAmountFrom = marginAmountFrom;
	}

	/**
	 * 保证金存款金额 - 结束
	 * @return 返回 marginAmountTo。
	 */
	public double getMarginAmountTo() {
		return MarginAmountTo;
	}

	/**
	 * 保证金存款金额 - 结束
	 * @param marginAmountTo 要设置的 marginAmountTo。
	 */
	public void setMarginAmountTo(double marginAmountTo) {
		MarginAmountTo = marginAmountTo;
	}

	/**
	 * 保证金存款单据号 - 起始
	 * @return 返回 marginDepositNoFrom。
	 */
	public String getMarginDepositNoFrom() {
		return MarginDepositNoFrom;
	}

	/**
	 * 保证金存款单据号 - 起始
	 * @param marginDepositNoFrom 要设置的 marginDepositNoFrom。
	 */
	public void setMarginDepositNoFrom(String marginDepositNoFrom) {
		MarginDepositNoFrom = marginDepositNoFrom;
	}

	/**
	 * 保证金存款单据号 - 结束
	 * @return 返回 marginDepositNoTo。
	 */
	public String getMarginDepositNoTo() {
		return MarginDepositNoTo;
	}

	/**
	 * 保证金存款单据号 - 结束
	 * @param marginDepositNoTo 要设置的 marginDepositNoTo。
	 */
	public void setMarginDepositNoTo(String marginDepositNoTo) {
		MarginDepositNoTo = marginDepositNoTo;
	}

	/**
	 * 保证金存款状态
	 * @return 返回 marginDepositStatus。
	 */
	public long getMarginDepositStatus() {
		return MarginDepositStatus;
	}

	/**
	 * 保证金存款状态
	 * @param marginDepositStatus 要设置的 marginDepositStatus。
	 */
	public void setMarginDepositStatus(long marginDepositStatus) {
		MarginDepositStatus = marginDepositStatus;
	}

	/**
	 * 定期截止日期
	 * @return 返回 marginEndDate。
	 */
	public Timestamp getMarginEndDate() {
		return MarginEndDate;
	}

	/**
	 * 定期截止日期
	 * @param marginEndDate 要设置的 marginEndDate。
	 */
	public void setMarginEndDate(Timestamp marginEndDate) {
		MarginEndDate = marginEndDate;
	}

	/**
	 * 到期日期 - 起始
	 * @return 返回 marginEndDateFrom。
	 */
	public Timestamp getMarginEndDateFrom() {
		return MarginEndDateFrom;
	}

	/**
	 * 到期日期 - 起始
	 * @param marginEndDateFrom 要设置的 marginEndDateFrom。
	 */
	public void setMarginEndDateFrom(Timestamp marginEndDateFrom) {
		MarginEndDateFrom = marginEndDateFrom;
	}

	/**
	 * 到期日期 - 结束
	 * @return 返回 marginEndDateTo。
	 */
	public Timestamp getMarginEndDateTo() {
		return MarginEndDateTo;
	}

	/**
	 * 到期日期 - 结束
	 * @param marginEndDateTo 要设置的 marginEndDateTo。
	 */
	public void setMarginEndDateTo(Timestamp marginEndDateTo) {
		MarginEndDateTo = marginEndDateTo;
	}

	/**
	 * 利率
	 * @return 返回 marginRate。
	 */
	public double getMarginRate() {
		return MarginRate;
	}

	/**
	 * 利率
	 * @param marginRate 要设置的 marginRate。
	 */
	public void setMarginRate(double marginRate) {
		MarginRate = marginRate;
	}

	/**
	 * 起始日期 - 起始
	 * @return 返回 marginStartDateFrom。
	 */
	public Timestamp getMarginStartDateFrom() {
		return MarginStartDateFrom;
	}

	/**
	 * 起始日期 - 起始
	 * @param marginStartDateFrom 要设置的 marginStartDateFrom。
	 */
	public void setMarginStartDateFrom(Timestamp marginStartDateFrom) {
		MarginStartDateFrom = marginStartDateFrom;
	}

	/**
	 * 起始日期 - 结束
	 * @return 返回 marginStartDateTo。
	 */
	public Timestamp getMarginStartDateTo() {
		return MarginStartDateTo;
	}

	/**
	 * 起始日期 - 结束
	 * @param marginStartDateTo 要设置的 marginStartDateTo。
	 */
	public void setMarginStartDateTo(Timestamp marginStartDateTo) {
		MarginStartDateTo = marginStartDateTo;
	}

	//add by 2012-05-18 添加指定账户编号
	public String getAppointAccountNo() {
		return appointAccountNo;
	}

	public void setAppointAccountNo(String appointAccountNo) {
		this.appointAccountNo = appointAccountNo;
	}

	public long getUnit() {
		return unit;
	}

	public void setUnit(long unit) {
		this.unit = unit;
	}

	public String getControlSource() {
		return ControlSource;
	}

	public void setControlSource(String controlSource) {
		ControlSource = controlSource;
	}
	
}
