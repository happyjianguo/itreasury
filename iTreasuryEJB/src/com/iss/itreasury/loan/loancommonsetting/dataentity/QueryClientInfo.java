/**
 * 
 */
package com.iss.itreasury.loan.loancommonsetting.dataentity;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.Constant;

/**
 * @author weihuang
 *
 */
public class QueryClientInfo extends LoanBaseDataEntity{
   private String code="";	
   private String name="";
   private String name1="";
   private String name2="";
   private String LicenceCode="";//营业执照号码
  // private String Account="";//财务公司账户号
   //private String LoanCardNo;//贷款卡号
  // private String Province="";
   //private String City="";
   //private String Address="";
   //private long nIsfinancingcollection= -1;//是否资金规集
// 信用等级
   private long CreditLevelID=-1;
// 上海电气 显示行业分类 
	private long industryTypeID = -1;
// 2 个扩展属性
	private long extendAttributeID1 = -1;
	private long extendAttributeID2 = -1;
   private long LoanClientTypeID=-1;
   private long SettClientTypeID=-1;
	//	Loan_LoanTypeSetting表中没有的字段
	private long id = -1;
	private long queryPurpose = -1;
    private long recordCount = 0;
    private long pageLineCount = 0;
    private long pageCount = 0;
    private long pageNo = 0;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    private long orderParam = -1;
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
    private String orderParamString = "";
	/**
	 * @return Returns the desc.
	 */
	public long getDesc() {
		return desc;
	}
	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(long desc) {
		this.desc = desc;
	}
	/**
	 * @return Returns the orderParam.
	 */
	public long getOrderParam() {
		return orderParam;
	}
	/**
	 * @param orderParam The orderParam to set.
	 */
	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}
	/**
	 * @return Returns the orderParamString.
	 */
	public String getOrderParamString() {
		return orderParamString;
	}
	/**
	 * @param orderParamString The orderParamString to set.
	 */
	public void setOrderParamString(String orderParamString) {
		this.orderParamString = orderParamString;
	}
	/**
	 * @return Returns the pageCount.
	 */
	public long getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount The pageCount to set.
	 */
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return Returns the pageLineCount.
	 */
	public long getPageLineCount() {
		return pageLineCount;
	}
	/**
	 * @param pageLineCount The pageLineCount to set.
	 */
	public void setPageLineCount(long pageLineCount) {
		this.pageLineCount = pageLineCount;
	}
	/**
	 * @return Returns the pageNo.
	 */
	public long getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo The pageNo to set.
	 */
	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return Returns the queryPurpose.
	 */
	public long getQueryPurpose() {
		return queryPurpose;
	}
	/**
	 * @param queryPurpose The queryPurpose to set.
	 */
	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}
	/**
	 * @return Returns the recordCount.
	 */
	public long getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount The recordCount to set.
	 */
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	/**
	 * @return Returns the rowNumEnd.
	 */
	public long getRowNumEnd() {
		return rowNumEnd;
	}
	/**
	 * @param rowNumEnd The rowNumEnd to set.
	 */
	public void setRowNumEnd(long rowNumEnd) {
		this.rowNumEnd = rowNumEnd;
	}
	/**
	 * @return Returns the rowNumStart.
	 */
	public long getRowNumStart() {
		return rowNumStart;
	}
	/**
	 * @param rowNumStart The rowNumStart to set.
	 */
	public void setRowNumStart(long rowNumStart) {
		this.rowNumStart = rowNumStart;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the licenceCode.
	 */
	public String getLicenceCode() {
		return LicenceCode;
	}
	/**
	 * @param licenceCode The licenceCode to set.
	 */
	public void setLicenceCode(String licenceCode) {
		LicenceCode = licenceCode;
	}
	
	/**
	 * @return Returns the loanClientTypeID.
	 */
	public long getLoanClientTypeID() {
		return LoanClientTypeID;
	}
	/**
	 * @param loanClientTypeID The loanClientTypeID to set.
	 */
	public void setLoanClientTypeID(long loanClientTypeID) {
		LoanClientTypeID = loanClientTypeID;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the name1.
	 */
	public String getName1() {
		return name1;
	}
	/**
	 * @param name1 The name1 to set.
	 */
	public void setName1(String name1) {
		this.name1 = name1;
	}
	/**
	 * @return Returns the name2.
	 */
	public String getName2() {
		return name2;
	}
	/**
	 * @param name2 The name2 to set.
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}
	
	
	/**
	 * @return Returns the settClientTypeID.
	 */
	public long getSettClientTypeID() {
		return SettClientTypeID;
	}
	/**
	 * @param settClientTypeID The settClientTypeID to set.
	 */
	public void setSettClientTypeID(long settClientTypeID) {
		SettClientTypeID = settClientTypeID;
	}
	/**
	 * @return Returns the creditLevelID.
	 */
	public long getCreditLevelID() {
		return CreditLevelID;
	}
	/**
	 * @param creditLevelID The creditLevelID to set.
	 */
	public void setCreditLevelID(long creditLevelID) {
		CreditLevelID = creditLevelID;
	}
	/**
	 * @return Returns the industryTypeID.
	 */
	public long getIndustryTypeID() {
		return industryTypeID;
	}
	/**
	 * @param industryTypeID The industryTypeID to set.
	 */
	public void setIndustryTypeID(long industryTypeID) {
		this.industryTypeID = industryTypeID;
	}
	/**
	 * @return Returns the extendAttributeID1.
	 */
	public long getExtendAttributeID1() {
		return extendAttributeID1;
	}
	/**
	 * @param extendAttributeID1 The extendAttributeID1 to set.
	 */
	public void setExtendAttributeID1(long extendAttributeID1) {
		this.extendAttributeID1 = extendAttributeID1;
	}
	/**
	 * @return Returns the extendAttributeID2.
	 */
	public long getExtendAttributeID2() {
		return extendAttributeID2;
	}
	/**
	 * @param extendAttributeID2 The extendAttributeID2 to set.
	 */
	public void setExtendAttributeID2(long extendAttributeID2) {
		this.extendAttributeID2 = extendAttributeID2;
	}

}
