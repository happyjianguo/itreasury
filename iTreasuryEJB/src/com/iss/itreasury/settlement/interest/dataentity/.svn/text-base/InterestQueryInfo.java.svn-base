/*
 * Created on 2003-10-28
 *
 * InterestQueryInfo.java
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.AccessibleObject;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * @author Allan Liu
 *
 * 利息费用结算页面查询条件信息实体。
 * 该类主要用来保存页面的查询条件，并作为查询Dao的方法参数。
 * 该实体必须遵守JavaBean的规范，以便实现从JSP页面到实体的自动赋值的功能，
 * 简化应用程序的开发过程。
 * 为了下一个页面显示的需要，本实体中同时保存数据库访问需要的关键字段值，比如：ID,
 * 和页面显示的内容，比如：No。
 */
public class InterestQueryInfo extends BaseDataEntity implements Serializable{
	
	private long OfficeID = -1;	                //办事处ID
	private long CurrencyID = -1;	                //币种ID
	private long UserID  = -1 ;                     //操作人 
	private long lAccountIDFrom = -1;	            //账户 ID (开始)
	private String strAccountNoFrom = "";           //账户编号(开始)
	private String strAccountIDFromCtrlCtrl1 = "";  //页面分段显示账户时用
	private String strAccountIDFromCtrlCtrl2 = "";  //页面分段显示账户时用
	private String strAccountIDFromCtrlCtrl3 = "";  //页面分段显示账户时用
	private String strAccountIDFromCtrlCtrl4 = "";  //页面分段显示账户时用
	private long lAccountIDTo = -1;	                //账户 ID (结束)
	private String strAccountNoTo = "";             //账户编号(结束)
	private String strAccountIDToCtrlCtrl1 = "";    //页面分段显示账户时用
	private String strAccountIDToCtrlCtrl2 = "";    //页面分段显示账户时用
	private String strAccountIDToCtrlCtrl3 = "";    //页面分段显示账户时用
	private String strAccountIDToCtrlCtrl4 = "";    //页面分段显示账户时用
    private long lFixedDepositIDFrom = -1;          //定期单据 ID (开始)
    private String strFixedDepositNoFrom = "";      //定期单据编号(开始)
    private long lFixedDepositIDTo = -1;            //定期单据 ID (结束)
    private String strFixedDepositNoTo = "";        //定期单据编号(结束)
    private long lContractIDFrom = -1;              //合同 ID (开始)
    private String strContractNoFrom = "";          //合同编号(开始)
    private long lContractIDTo = -1;                //合同 ID (结束)
    private String strContractNoTo = "";            //合同编号(结束)
    private long lPayFormIDFrom = -1;               //放款通知单 ID (开始)
    private String strPayFormNoFrom = "";           //放款通知单编号(开始)
    private long lPayFormIDTo = -1;                 //放款通知单 ID (结束)
    private String strPayFormNoTo = "";             //放款通知单编号(结束)
    private long lLoanTypeValue = -1;               //贷款种类(数据库中的值)
    private String strLoanTypeLabel = "";           //贷款种类(页面显示的内容)
    private String lSubLoanTypeValue = "";			//贷款子类型(结合页面,将贷款类型修改为子类型,所以新增子类型)
    private String[] lSubLoanTypeValueLeft = null;		//页面未选中的multiple的select子类型
    private String[] lSubLoanTypeValueRight = null;		//页面选中的multiple的select子类型    
    private long lLoanTermValue = -1;               //贷款期限(数据库中的值)
    private String strLoanTermLabel = "";           //贷款期限(页面显示的内容)
    private long lYearTermValue = -1;               //年期(数据库中的值)
    private String strYearTermLabel = "";           //年期(页面显示的内容)
    private long lConsignID = -1;                   //委托方(数据库中的值)
    private String strConsignLabel = "";            //委托方(页面显示的内容)
    private Timestamp dtClearInterest = null;       //结息日
    private boolean bInterest = false;              //是否计算利息
    private boolean bCompoundInterest = false;      //是否计算复利
    private boolean bForfeitInterest = false;       //是否计算罚息
    private long lFeeType = -1;                     //费用类型：1-计提利息；2-手续费；3-担保费
	private long Desc = -1;      //升序降序
	private long OrderField = -1;     //排序字段
	private long isClearNull = -1; //是否滤空
	
    /** 修改的地方，增加贴现的查询条件
     */
	private Timestamp dtStartDiscount = null;  //贴现日开始
	private Timestamp dtEndDiscount = null;  //贴现日结束
	
	//Boxu Add 2010-10-29 查询条件增加账户类型
	private long IsCheckedType = -1;
	private long lAccountTypeID1 = -1;	//账户类型1
	private long lAccountTypeID2 = -1;	//账户类型2
	
	//yuxia add 2012-5-23新增标识计提利息独立功能字段
	private long isPrewDraw = -1; //标识是否为计提利息处理功能
	
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	
	public long getIsCheckedType() {
		return IsCheckedType;
	}

	public void setIsCheckedType(long isCheckedType) {
		IsCheckedType = isCheckedType;
	}

	public long getLAccountTypeID1() {
		return lAccountTypeID1;
	}

	public void setLAccountTypeID1(long accountTypeID1) {
		lAccountTypeID1 = accountTypeID1;
	}

	public long getLAccountTypeID2() {
		return lAccountTypeID2;
	}

	public void setLAccountTypeID2(long accountTypeID2) {
		lAccountTypeID2 = accountTypeID2;
	}

	public Timestamp getDtEndDiscount() 
    {
		return dtEndDiscount;
	}

	public void setDtEndDiscount(Timestamp dtEndDiscount) 
	{
		this.dtEndDiscount = dtEndDiscount;
	}

	public Timestamp getDtStartDiscount() 
	{
		return dtStartDiscount;
	}

	public void setDtStartDiscount(Timestamp dtStartDiscount) 
	{
		this.dtStartDiscount = dtStartDiscount;
	}

	/**
     * @return 是否计算复利
     */
    public boolean isCompoundInterest() {
        return this.bCompoundInterest;
    }

    /**
     * @param compoundInterest 是否计算复利
     */
    public void setCompoundInterest(boolean compoundInterest) {
        this.bCompoundInterest = compoundInterest;
    }

    /**
     * @return 是否计算罚息
     */
    public boolean isForfeitInterest() {
        return this.bForfeitInterest;
    }

    /**
     * @param forfeitInterest 是否计算罚息
     */
    public void setForfeitInterest(boolean forfeitInterest) {
        this.bForfeitInterest = forfeitInterest;
    }

    /**
     * @return 是否计算利息
     */
    public boolean isInterest() {
        return this.bInterest;
    }

    /**
     * @param interest 是否计算利息
     */
    public void setInterest(boolean interest) {
        this.bInterest = interest;
    }

    /**
     * @return 结息日
     */
    public Timestamp getClearInterest() {
        return this.dtClearInterest;
    }

    /**
     * @param clearInterest 结息日
     */
    public void setClearInterest(Timestamp clearInterest) {
        this.dtClearInterest = clearInterest;
    }
    /**
     * @return 账户 ID (开始)
     */
    public long getAccountIDFrom() {
        return this.lAccountIDFrom;
    }

    /**
     * @param accountIDFrom 账户 ID (开始)
     */
    public void setAccountIDFrom(long accountIDFrom) {
        this.lAccountIDFrom = accountIDFrom;
    }
    
    /**
     * @return 账户 ID (结束)
     */
    public long getAccountIDTo() {
        return this.lAccountIDTo;
    }

    /**
     * @param accountIDTo 账户 ID (结束)
     */
    public void setAccountIDTo(long accountIDTo) {
        this.lAccountIDTo = accountIDTo;
    }
    
    /**
     * @return 委托方(数据库中的值)
     */
    public long getConsignID() {
        return this.lConsignID;
    }

    /**
     * @param consignID 委托方(数据库中的值)
     */
    public void setConsignID(long consignID) {
        this.lConsignID = consignID;
    }
    
    /**
     * @return 合同 ID (开始)
     */
    public long getContractIDFrom() {
        return this.lContractIDFrom;
    }

    /**
     * @param contractIDFrom 合同 ID (开始)
     */
    public void setContractIDFrom(long contractIDFrom) {
        this.lContractIDFrom = contractIDFrom;
    }
    
    /**
     * @return 合同 ID (结束)
     */
    public long getContractIDTo() {
        return this.lContractIDTo;
    }

    /**
     * @param contractIDTo 合同 ID (结束)
     */
    public void setContractIDTo(long contractIDTo) {
        this.lContractIDTo = contractIDTo;
    }
    
    /**
     * @return 费用类型：1-计提利息；2-手续费；3-担保费
     */
    public long getFeeType() {
        return this.lFeeType;
    }

    /**
     * @param feeType 费用类型：1-计提利息；2-手续费；3-担保费
     */
    public void setFeeType(long feeType) {
        this.lFeeType = feeType;
    }
    
    /**
     * @return 定期单据 ID (开始)
     */
    public long getFixedDepositIDFromAccountID() {
        return this.lFixedDepositIDFrom;
    }

    /**
     * @param fixedDepositIDFrom 定期单据 ID (开始)
     */
    public void setFixedDepositIDFromAccountID(long fixedDepositIDFrom) {
        this.lFixedDepositIDFrom = fixedDepositIDFrom;
    }
    
    /**
     * @return 定期单据 ID (结束)
     */
    public long getFixedDepositIDToAccountID() {
        return this.lFixedDepositIDTo;
    }

    /**
     * @param fixedDepositIDTo 定期单据 ID (结束)
     */
    public void setFixedDepositIDToAccountID(long fixedDepositIDTo) {
        this.lFixedDepositIDTo = fixedDepositIDTo;
    }
    
    /**
     * @return 贷款期限(数据库中的值)
     */
    public long getLoanTerm() {
        return this.lLoanTermValue;
    }

    /**
     * @param loanTermValue 贷款期限(数据库中的值)
     */
    public void setLoanTerm(long loanTermValue) {
        this.lLoanTermValue = loanTermValue;
    }
    
    /**
     * @return 贷款种类(数据库中的值)
     */
    public long getLoanType() {
        return this.lLoanTypeValue;
    }

    /**
     * @param loanTypeValue 贷款种类(数据库中的值)
     */
    public void setLoanType(long loanTypeValue) {
        this.lLoanTypeValue = loanTypeValue;
    }
    
    /**
     * @return 放款通知单 ID (开始)
     */
    public long getPayFormIDFrom() {
        return this.lPayFormIDFrom;
    }

    /**
     * @param payFormIDFrom 放款通知单 ID (开始)
     */
    public void setPayFormIDFrom(long payFormIDFrom) {
        this.lPayFormIDFrom = payFormIDFrom;
    }
    
    /**
     * @return 放款通知单 ID (结束)
     */
    public long getPayFormIDTo() {
        return this.lPayFormIDTo;
    }
    
    /**
     * @return 放款通知单 ID (结束)
     */
    public void setPayFormIDTo(long payFormIDTo) {
        this.lPayFormIDTo = payFormIDTo;
    }

    /**
     * @return 年期(数据库中的值)
     */
    public long getYearTerm() {
        return this.lYearTermValue;
    }

    /**
     * @param yearTermValue 年期(数据库中的值)
     */
    public void setYearTerm(long yearTermValue) {
        this.lYearTermValue = yearTermValue;
    }
    
    /**
     * @return 账户编号(开始)
     */
    public String getAccountIDFromCtrl() {
        return this.strAccountNoFrom;
    }

    /**
     * @param accountNoFrom 账户编号(开始)
     */
    public void setAccountIDFromCtrl(String accountNoFrom) {
        this.strAccountNoFrom = accountNoFrom;
    }
    

    /**
     * @return 账户编号(结束)
     */
    public String getAccountIDToCtrl() {
        return this.strAccountNoTo;
    }

    /**
     * @param accountNoTo 账户编号(结束)
     */
    public void setAccountIDToCtrl(String accountNoTo) {
        this.strAccountNoTo = accountNoTo;
    }

    /**
     * @return 委托方(页面显示的内容)
     */
    public String getConsignIDCtrl() {
        return this.strConsignLabel;
    }

    /**
     * @param consignLabel 委托方(页面显示的内容)
     */
    public void setConsignIDCtrl(String consignLabel) {
        this.strConsignLabel = consignLabel;
    }

    /**
     * @return 合同编号(开始)
     */
    public String getContractIDFromCtrl() {
        return this.strContractNoFrom;
    }

    /**
     * @param contractNoFrom 合同编号(开始)
     */
    public void setContractIDFromCtrl(String contractNoFrom) {
        this.strContractNoFrom = contractNoFrom;
    }

    /**
     * @return 合同编号(结束)
     */
    public String getContractIDToCtrl() {
        return this.strContractNoTo;
    }

    /**
     * @param contractNoTo 合同编号(结束)
     */
    public void setContractIDToCtrl(String contractNoTo) {
        this.strContractNoTo = contractNoTo;
    }

    /**
     * @return 定期单据编号(开始)
     */
    public String getFixedDepositIDFromCtrl() {
        return this.strFixedDepositNoFrom;
    }

    /**
     * @param fixedDepositNoFrom 定期单据编号(开始)
     */
    public void setFixedDepositIDFromCtrl(String fixedDepositNoFrom) {
        this.strFixedDepositNoFrom = fixedDepositNoFrom;
    }

    /**
     * @return 定期单据编号(结束)
     */
    public String getFixedDepositIDToCtrl() {
        return this.strFixedDepositNoTo;
    }

    /**
     * @param fixedDepositNoTo 定期单据编号(结束)
     */
    public void setFixedDepositIDToCtrl(String fixedDepositNoTo) {
        this.strFixedDepositNoTo = fixedDepositNoTo;
    }

    /**
     * @return 贷款期限(页面显示的内容)
     */
    public String getLoanTermLabel() {
        return this.strLoanTermLabel;
    }

    /**
     * @param loanTermLabel 贷款期限(页面显示的内容)
     */
    public void setLoanTermLabel(String loanTermLabel) {
        this.strLoanTermLabel = loanTermLabel;
    }

    /**
     * @return 贷款种类(页面显示的内容)
     */
    public String getLoanTypeLabel() {
        return this.strLoanTypeLabel;
    }

    /**
     * @param loanTypeLabel 贷款种类(页面显示的内容)
     */
    public void setLoanTypeLabel(String loanTypeLabel) {
        this.strLoanTypeLabel = loanTypeLabel;
    }

    /**
     * @return 放款通知单编号(开始)
     */
    public String getPayFormIDFromCtrl() {
        return this.strPayFormNoFrom;
    }

    /**
     * @param payFormNoFrom 放款通知单编号(开始)
     */
    public void setPayFormIDFromCtrl(String payFormNoFrom) {
        this.strPayFormNoFrom = payFormNoFrom;
    }

    /**
     * @return 放款通知单编号(结束)
     */
    public String getPayFormIDToCtrl() {
        return this.strPayFormNoTo;
    }

    /**
     * @param payFormNoTo 放款通知单编号(结束)
     */
    public void setPayFormIDToCtrl(String payFormNoTo) {
        this.strPayFormNoTo = payFormNoTo;
    }

    /**
     * @return 年期(页面显示的内容)
     */
    public String getYearTermLabel() {
        return this.strYearTermLabel;
    }

    /**
     * @param yearTermLabel 年期(页面显示的内容)
     */
    public void setYearTermLabel(String yearTermLabel) {
        this.strYearTermLabel = yearTermLabel;
    }
    
    /**
     * @return
     */
    public String getAccountIDFromCtrlCtrl1() {
        return this.strAccountIDFromCtrlCtrl1;
    }

    /**
     * @param accountIDFromCtrlCtrl1
     */
    public void setAccountIDFromCtrlCtrl1(String accountIDFromCtrlCtrl1) {
        this.strAccountIDFromCtrlCtrl1 = accountIDFromCtrlCtrl1;
    }

    /**
     * @return
     */
    public String getAccountIDFromCtrlCtrl2() {
        return this.strAccountIDFromCtrlCtrl2;
    }

    /**
     * @param accountIDFromCtrlCtrl2
     */
    public void setAccountIDFromCtrlCtrl2(String accountIDFromCtrlCtrl2) {
        this.strAccountIDFromCtrlCtrl2 = accountIDFromCtrlCtrl2;
    }

    /**
     * @return
     */
    public String getAccountIDFromCtrlCtrl3() {
        return this.strAccountIDFromCtrlCtrl3;
    }

    /**
     * @param accountIDFromCtrlCtrl3
     */
    public void setAccountIDFromCtrlCtrl3(String accountIDFromCtrlCtrl3) {
        this.strAccountIDFromCtrlCtrl3 = accountIDFromCtrlCtrl3;
    }

    /**
     * @return
     */
    public String getAccountIDFromCtrlCtrl4() {
        return this.strAccountIDFromCtrlCtrl4;
    }

    /**
     * @param accountIDFromCtrlCtrl4
     */
    public void setAccountIDFromCtrlCtrl4(String accountIDFromCtrlCtrl4) {
        this.strAccountIDFromCtrlCtrl4 = accountIDFromCtrlCtrl4;
    }

    /**
     * @return
     */
    public String getAccountIDToCtrlCtrl1() {
        return this.strAccountIDToCtrlCtrl1;
    }

    /**
     * @param accountIDToCtrlCtrl1
     */
    public void setAccountIDToCtrlCtrl1(String accountIDToCtrlCtrl1) {
        this.strAccountIDToCtrlCtrl1 = accountIDToCtrlCtrl1;
    }

    /**
     * @return
     */
    public String getAccountIDToCtrlCtrl2() {
        return this.strAccountIDToCtrlCtrl2;
    }

    /**
     * @param accountIDToCtrlCtrl2
     */
    public void setAccountIDToCtrlCtrl2(String accountIDToCtrlCtrl2) {
        this.strAccountIDToCtrlCtrl2 = accountIDToCtrlCtrl2;
    }

    /**
     * @return
     */
    public String getAccountIDToCtrlCtrl3() {
        return this.strAccountIDToCtrlCtrl3;
    }

    /**
     * @param accountIDToCtrlCtrl3
     */
    public void setAccountIDToCtrlCtrl3(String accountIDToCtrlCtrl3) {
        this.strAccountIDToCtrlCtrl3 = accountIDToCtrlCtrl3;
    }

    /**
     * @return
     */
    public String getAccountIDToCtrlCtrl4() {
        return this.strAccountIDToCtrlCtrl4;
    }

    /**
     * @param accountIDToCtrlCtrl4
     */
    public void setAccountIDToCtrlCtrl4(String accountIDToCtrlCtrl4) {
        this.strAccountIDToCtrlCtrl4 = accountIDToCtrlCtrl4;
    }
    
    /**
     * @return a string representing the current object using reflect api.
     */
    public String toString(){
        
        Field[] allFields = getClass().getDeclaredFields();
        StringBuffer buffer = new StringBuffer();
        
        try{
            AccessibleObject.setAccessible(allFields, true);
            buffer.append("InterestQueryInfo[");
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
    /**
     * 私有内部类，主要用来保存页面信息，为翻页提供支持。
     */
   /* private class PageInfo implements Serializable{

        public long lTotalRecords = 0;      //总的记录数
        public long lCurrentPage = 1;       //当前是第几页
        public long lTotalPages = 1;        //总的页数
        public long lRowPerPage = 10;       //每页显示的件数
        public boolean bFreshQuiry = true;  //是否需要刷新显示的数据，即重新检索数据库中的实时数据
        
    }*/
    
    //private PageInfo pageInfo = new PageInfo(); //页面信息
    
    /**
     * 初始化页面信息
     * 该方法在检索完要显示的记录信息的时候，进行设置。
     * @param lTotalRecords 检索出的记录总数。
     */
   /* public void initPageInfo(long lTotalRecords){
        
        pageInfo.lTotalRecords = lTotalRecords;
        if(pageInfo.lTotalRecords%pageInfo.lRowPerPage == 0){
            pageInfo.lTotalPages = pageInfo.lTotalRecords/pageInfo.lRowPerPage;
        }else{
            pageInfo.lTotalPages = pageInfo.lTotalRecords/pageInfo.lRowPerPage + 1;
        }
        pageInfo.bFreshQuiry = false;
    }
    
    /**
     * 重置页面信息
     */
    /*public void resetPageInfo(){
        pageInfo.lTotalRecords = 0;
        pageInfo.lCurrentPage = 1;
        pageInfo.lTotalPages = 1;
        pageInfo.lRowPerPage = 0;
        pageInfo.bFreshQuiry = true;
    }
    
    /**
     * @return 总页数。
     */
    /*public long getTotalPages(){
        return pageInfo.lTotalPages;
    }
    
    /**
     * @return 总页数。
     */
    /*public boolean needFreshQuiry(){
        return pageInfo.bFreshQuiry;
    }
    
    /**
     * @return 当前数。
     */
    /*public long getCurrentPage(){
        return pageInfo.lCurrentPage;
    }
    
    /**
     * 设置每页显示件数。
     * @param lRowPerPage 每页显示件数。
     */
    /*public void setRowPerPage(long lRowPerPage){
        pageInfo.lRowPerPage = lRowPerPage;
    }
    
    /**
     * 下一页操作。
     */
    /*public void nextPage(){
        if(pageInfo.lCurrentPage <= pageInfo.lTotalPages){
            pageInfo.lCurrentPage++;
        }
    }

    /**
     * 上一页操作。
     */    
    /*public void previousPage(){
        if(pageInfo.lCurrentPage > 1){
            pageInfo.lCurrentPage--;
        }
    }
    
    /**
     * 最后一页操作。
     */    
    /*public void lastPage(){
        pageInfo.lCurrentPage = pageInfo.lTotalPages;
    }
    
    /**
     * 第一页操作。
     */    
    /*public void firstPage(){
        pageInfo.lCurrentPage = 1;
    }
    
    /**
     * 转到某一页操作。
     * @param lPageNo 要转到的页号。
     */ 
    /*public void gotoPage(long lPageNo){
        if(lPageNo > pageInfo.lTotalPages){
            lPageNo = pageInfo.lTotalPages;
        }
        if(lPageNo < 1){
            lPageNo = 1;
        }
        pageInfo.lCurrentPage = lPageNo;
    }
    
    /**
     * 取得检索的开始记录处。
     * 本方法用在后台数据库检索的时候。
     * @param return 检索的开始记录处。
     */ 
    /*public long getRowStart(){
        if(pageInfo.lCurrentPage == 1){
            return 1;
        }else {
            return (pageInfo.lCurrentPage - 1) * pageInfo.lRowPerPage + 1;
        }
    }
    
     /**
     * 取得检索的开始记录处。
     * 本方法用在后台数据库检索的时候。
     * @param return 检索的开始记录处。
     */ 
    /*public long getRowEnd(){
        return getRowStart() + pageInfo.lRowPerPage - 1;
    }
    
    /**
     * 取得页面信息的描述字符串。
     */     
    /*public String getPageInfo(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("PageInfo[");
        buffer.append("total records " + pageInfo.lTotalRecords);
        buffer.append(",total pages " + pageInfo.lTotalPages);
        buffer.append(",current page " + pageInfo.lCurrentPage);
        buffer.append(",row perpage " + pageInfo.lRowPerPage);
        buffer.append(",is fresh query " + pageInfo.bFreshQuiry);
        buffer.append(",query start row " + getRowStart());
        buffer.append("]");
        return buffer.toString();
    }*/
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
	public long getOrderField()
	{
		return OrderField;
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
	public void setOrderField(long l)
	{
		OrderField = l;
	}

	/**
	 * @return
	 */
	public boolean isBCompoundInterest()
	{
		return bCompoundInterest;
	}

	/**
	 * @return
	 */
	public boolean isBForfeitInterest()
	{
		return bForfeitInterest;
	}

	/**
	 * @return
	 */
	public boolean isBInterest()
	{
		return bInterest;
	}

	/**
	 * @return
	 */
	public Timestamp getDtClearInterest()
	{
		return dtClearInterest;
	}

	/**
	 * @return
	 */
	public long getLAccountIDFrom()
	{
		return lAccountIDFrom;
	}

	/**
	 * @return
	 */
	public long getLAccountIDTo()
	{
		return lAccountIDTo;
	}

	/**
	 * @return
	 */
	public long getLConsignID()
	{
		return lConsignID;
	}

	/**
	 * @return
	 */
	public long getLContractIDFrom()
	{
		return lContractIDFrom;
	}

	/**
	 * @return
	 */
	public long getLContractIDTo()
	{
		return lContractIDTo;
	}

	
	/**
	 * @return
	 */
	public long getLFeeType()
	{
		return lFeeType;
	}

	/**
	 * @return
	 */
	public long getLFixedDepositIDFrom()
	{
		return lFixedDepositIDFrom;
	}

	/**
	 * @return
	 */
	public long getLFixedDepositIDTo()
	{
		return lFixedDepositIDTo;
	}

	/**
	 * @return
	 */
	public long getLLoanTermValue()
	{
		return lLoanTermValue;
	}

	/**
	 * @return
	 */
	public long getLLoanTypeValue()
	{
		return lLoanTypeValue;
	}

	

	/**
	 * @return
	 */
	public long getLPayFormIDFrom()
	{
		return lPayFormIDFrom;
	}

	/**
	 * @return
	 */
	public long getLPayFormIDTo()
	{
		return lPayFormIDTo;
	}

	/**
	 * @return
	 */
	public long getLYearTermValue()
	{
		return lYearTermValue;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDFromCtrlCtrl1()
	{
		return strAccountIDFromCtrlCtrl1;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDFromCtrlCtrl2()
	{
		return strAccountIDFromCtrlCtrl2;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDFromCtrlCtrl3()
	{
		return strAccountIDFromCtrlCtrl3;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDFromCtrlCtrl4()
	{
		return strAccountIDFromCtrlCtrl4;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDToCtrlCtrl1()
	{
		return strAccountIDToCtrlCtrl1;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDToCtrlCtrl2()
	{
		return strAccountIDToCtrlCtrl2;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDToCtrlCtrl3()
	{
		return strAccountIDToCtrlCtrl3;
	}

	/**
	 * @return
	 */
	public String getStrAccountIDToCtrlCtrl4()
	{
		return strAccountIDToCtrlCtrl4;
	}

	/**
	 * @return
	 */
	public String getStrAccountNoFrom()
	{
		return strAccountNoFrom;
	}

	/**
	 * @return
	 */
	public String getStrAccountNoTo()
	{
		return strAccountNoTo;
	}

	/**
	 * @return
	 */
	public String getStrConsignLabel()
	{
		return strConsignLabel;
	}

	/**
	 * @return
	 */
	public String getStrContractNoFrom()
	{
		return strContractNoFrom;
	}

	/**
	 * @return
	 */
	public String getStrContractNoTo()
	{
		return strContractNoTo;
	}

	/**
	 * @return
	 */
	public String getStrFixedDepositNoFrom()
	{
		return strFixedDepositNoFrom;
	}

	/**
	 * @return
	 */
	public String getStrFixedDepositNoTo()
	{
		return strFixedDepositNoTo;
	}

	/**
	 * @return
	 */
	public String getStrLoanTermLabel()
	{
		return strLoanTermLabel;
	}

	/**
	 * @return
	 */
	public String getStrLoanTypeLabel()
	{
		return strLoanTypeLabel;
	}

	/**
	 * @return
	 */
	public String getStrPayFormNoFrom()
	{
		return strPayFormNoFrom;
	}

	/**
	 * @return
	 */
	public String getStrPayFormNoTo()
	{
		return strPayFormNoTo;
	}

	/**
	 * @return
	 */
	public String getStrYearTermLabel()
	{
		return strYearTermLabel;
	}

	/**
	 * @param b
	 */
	public void setBCompoundInterest(boolean b)
	{
		bCompoundInterest = b;
	}

	/**
	 * @param b
	 */
	public void setBForfeitInterest(boolean b)
	{
		bForfeitInterest = b;
	}

	/**
	 * @param b
	 */
	public void setBInterest(boolean b)
	{
		bInterest = b;
	}

	/**
	 * @param timestamp
	 */
	public void setDtClearInterest(Timestamp timestamp)
	{
		dtClearInterest = timestamp;
	}

	/**
	 * @param l
	 */
	public void setLAccountIDFrom(long l)
	{
		lAccountIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setLAccountIDTo(long l)
	{
		lAccountIDTo = l;
	}

	/**
	 * @param l
	 */
	public void setLConsignID(long l)
	{
		lConsignID = l;
	}

	/**
	 * @param l
	 */
	public void setLContractIDFrom(long l)
	{
		lContractIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setLContractIDTo(long l)
	{
		lContractIDTo = l;
	}

	
	/**
	 * @param l
	 */
	public void setLFeeType(long l)
	{
		lFeeType = l;
	}

	/**
	 * @param l
	 */
	public void setLFixedDepositIDFrom(long l)
	{
		lFixedDepositIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setLFixedDepositIDTo(long l)
	{
		lFixedDepositIDTo = l;
	}

	/**
	 * @param l
	 */
	public void setLLoanTermValue(long l)
	{
		lLoanTermValue = l;
	}

	/**
	 * @param l
	 */
	public void setLLoanTypeValue(long l)
	{
		lLoanTypeValue = l;
	}

	

	/**
	 * @param l
	 */
	public void setLPayFormIDFrom(long l)
	{
		lPayFormIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setLPayFormIDTo(long l)
	{
		lPayFormIDTo = l;
	}

	/**
	 * @param l
	 */
	public void setLYearTermValue(long l)
	{
		lYearTermValue = l;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDFromCtrlCtrl1(String string)
	{
		strAccountIDFromCtrlCtrl1 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDFromCtrlCtrl2(String string)
	{
		strAccountIDFromCtrlCtrl2 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDFromCtrlCtrl3(String string)
	{
		strAccountIDFromCtrlCtrl3 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDFromCtrlCtrl4(String string)
	{
		strAccountIDFromCtrlCtrl4 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDToCtrlCtrl1(String string)
	{
		strAccountIDToCtrlCtrl1 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDToCtrlCtrl2(String string)
	{
		strAccountIDToCtrlCtrl2 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDToCtrlCtrl3(String string)
	{
		strAccountIDToCtrlCtrl3 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountIDToCtrlCtrl4(String string)
	{
		strAccountIDToCtrlCtrl4 = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountNoFrom(String string)
	{
		strAccountNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setStrAccountNoTo(String string)
	{
		strAccountNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setStrConsignLabel(String string)
	{
		strConsignLabel = string;
	}

	/**
	 * @param string
	 */
	public void setStrContractNoFrom(String string)
	{
		strContractNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setStrContractNoTo(String string)
	{
		strContractNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setStrFixedDepositNoFrom(String string)
	{
		strFixedDepositNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setStrFixedDepositNoTo(String string)
	{
		strFixedDepositNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setStrLoanTermLabel(String string)
	{
		strLoanTermLabel = string;
	}

	/**
	 * @param string
	 */
	public void setStrLoanTypeLabel(String string)
	{
		strLoanTypeLabel = string;
	}

	/**
	 * @param string
	 */
	public void setStrPayFormNoFrom(String string)
	{
		strPayFormNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setStrPayFormNoTo(String string)
	{
		strPayFormNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setStrYearTermLabel(String string)
	{
		strYearTermLabel = string;
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
	 * Returns the isClearNull.
	 * @return long
	 */
	public long getIsClearNull()
	{
		return isClearNull;
	}

	/**
	 * Sets the isClearNull.
	 * @param isClearNull The isClearNull to set
	 */
	public void setIsClearNull(long isClearNull)
	{
		this.isClearNull = isClearNull;
	}

	/**
	 * @return
	 */
	public long getUserID()
	{
		return UserID;
	}

	/**
	 * @param l
	 */
	public void setUserID(long l)
	{
		UserID = l;
	}

	public String getLSubLoanTypeValue() {
		return lSubLoanTypeValue;
	}

	public void setLSubLoanTypeValue(String subLoanTypeValue) {
		lSubLoanTypeValue = subLoanTypeValue;
	}

	public String[] getLSubLoanTypeValueLeft() {
		return lSubLoanTypeValueLeft;
	}

	public void setLSubLoanTypeValueLeft(String[] subLoanTypeValueLeft) {
		lSubLoanTypeValueLeft = subLoanTypeValueLeft;
	}

	public String[] getLSubLoanTypeValueRight() {
		return lSubLoanTypeValueRight;
	}

	public void setLSubLoanTypeValueRight(String[] subLoanTypeValueRight) {
		lSubLoanTypeValueRight = subLoanTypeValueRight;
	}

	public void setIsPrewDraw(long isPrewDraw) {
		this.isPrewDraw = isPrewDraw;
	}

	public long getIsPrewDraw() {
		return isPrewDraw;
	}
}
