package com.iss.itreasury.itreasuryinfo.branch.dataentity;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
/**
 * 用于查询银行账户（开户行）利率信息，包含全部查询条件字段属性
 * @author gmqiu
 *
 */
public class BranchRateQueryInfo extends BranchRateInfo {

	private static final long serialVersionUID = 1L;
	
	private long id = -1;	                /** ID，主键 */
	private long nOfficeId = -1; 	        /** 办事处编号ID(默认-1) */
	private long nCurrencyId = -1;	        /** 币种ID(默认-1) */
	private long branchId = -1; 	        /** 银行账户（开户行）ID，来自sett_branch 表的ID(默认-1) */
	private Timestamp dtValidate = null;	/** 利率生效日期 */
	private double mRate = 0.0;		        /** 利率值（年利率）*/
	private long nInputUserId = -1;	        /** 录入人ID(默认-1) */
	private Timestamp dtInputDate = null;	/** 录入日期 */
	private long nCheckUserId = -1;			/** 复核人ID */
	private Timestamp dtCheckDate = null;   /** 复核日期 */
	
	//查询条件
	private Timestamp dtValidateStart = null;   /** 利率生效开始日期 */
	private Timestamp dtValidateEnd = null;   /** 利率生效结束日期 */
	private String branchName = "";        /** 开户行名称 */
	private long nStatusId = -1;	       /** 状态(0--已删除，1--已保存，2--未复核，3--已复核) */
	
	private String[] ids = null;
	
	private String validateStart = "";   /** 利率生效开始日期 */
	private String validateEnd = "";   /** 利率生效结束日期 */
	private String sBanchCode = "";
	private String sName = "";   /** 开户行名称 */
	
	//分页变量
	private long queryPurpose = 1;
    private long recordCount = 0;
    private long pageLineCount = 0;
    private long pageCount = 0;
    private long pageNo = 0;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    private long orderParam = -1;
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
    private String orderParamString = "";

    
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getnOfficeId() {
		return nOfficeId;
	}


	public void setnOfficeId(long nOfficeId) {
		this.nOfficeId = nOfficeId;
	}


	public long getnCurrencyId() {
		return nCurrencyId;
	}


	public void setnCurrencyId(long nCurrencyId) {
		this.nCurrencyId = nCurrencyId;
	}

    
	public long getBranchId() {
		return branchId;
	}


	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}


	public Timestamp getDtValidate() {
		return dtValidate;
	}


	public void setDtValidate(Timestamp dtValidate) {
		this.dtValidate = dtValidate;
	}


	public double getmRate() {
		return mRate;
	}


	public void setmRate(double mRate) {
		this.mRate = mRate;
	}


	public long getnInputUserId() {
		return nInputUserId;
	}


	public void setnInputUserId(long nInputUserId) {
		this.nInputUserId = nInputUserId;
	}


	public Timestamp getDtInputDate() {
		return dtInputDate;
	}


	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
	}


	public long getnCheckUserId() {
		return nCheckUserId;
	}


	public void setnCheckUserId(long nCheckUserId) {
		this.nCheckUserId = nCheckUserId;
	}


	public Timestamp getDtCheckDate() {
		return dtCheckDate;
	}


	public void setDtCheckDate(Timestamp dtCheckDate) {
		this.dtCheckDate = dtCheckDate;
	}


	public Timestamp getDtValidateStart() {
		return dtValidateStart;
	}


	public void setDtValidateStart(Timestamp dtValidateStart) {
		this.dtValidateStart = dtValidateStart;
	}


	public Timestamp getDtValidateEnd() {
		return dtValidateEnd;
	}


	public void setDtValidateEnd(Timestamp dtValidateEnd) {
		this.dtValidateEnd = dtValidateEnd;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public long getnStatusId() {
		return nStatusId;
	}


	public void setnStatusId(long nStatusId) {
		this.nStatusId = nStatusId;
	}

    
	public String[] getIds() {
		return ids;
	}


	public void setIds(String[] ids) {
		this.ids = ids;
	}


	public long getQueryPurpose() {
		return queryPurpose;
	}


	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}


	public long getRecordCount() {
		return recordCount;
	}


	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}


	public long getPageLineCount() {
		return pageLineCount;
	}


	public void setPageLineCount(long pageLineCount) {
		this.pageLineCount = pageLineCount;
	}


	public long getPageCount() {
		return pageCount;
	}


	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}


	public long getPageNo() {
		return pageNo;
	}


	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}


	public long getRowNumStart() {
		return rowNumStart;
	}


	public void setRowNumStart(long rowNumStart) {
		this.rowNumStart = rowNumStart;
	}


	public long getRowNumEnd() {
		return rowNumEnd;
	}


	public void setRowNumEnd(long rowNumEnd) {
		this.rowNumEnd = rowNumEnd;
	}


	public long getOrderParam() {
		return orderParam;
	}


	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}


	public long getDesc() {
		return desc;
	}


	public void setDesc(long desc) {
		this.desc = desc;
	}


	public String getOrderParamString() {
		return orderParamString;
	}


	public void setOrderParamString(String orderParamString) {
		this.orderParamString = orderParamString;
	}

    
	public String getValidateStart() {
		return validateStart;
	}


	public void setValidateStart(String validateStart) {
		this.validateStart = validateStart;
	}


	public String getValidateEnd() {
		return validateEnd;
	}


	public void setValidateEnd(String validateEnd) {
		this.validateEnd = validateEnd;
	}


	public String getsBanchCode() {
		return sBanchCode;
	}


	public void setsBanchCode(String sBanchCode) {
		this.sBanchCode = sBanchCode;
	}


	public String getsName() {
		return sName;
	}


	public void setsName(String sName) {
		this.sName = sName;
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
	
}
