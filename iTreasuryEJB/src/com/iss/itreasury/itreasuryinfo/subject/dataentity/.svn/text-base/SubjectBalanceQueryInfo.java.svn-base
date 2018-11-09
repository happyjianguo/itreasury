package com.iss.itreasury.itreasuryinfo.subject.dataentity;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import com.iss.itreasury.util.Constant;
/**
 * 用于查询资金项目余额信息，包含全部查询条件字段属性
 * @author gmqiu
 *
 */
public class SubjectBalanceQueryInfo {

	private long id = -1;
	private String q_dtBalanceDateStart = "";
	private String q_dtBalanceDateEnd = "";
	private long q_nStatusId = -1;
	private long q_nKind = -1;
	private String q_sSubjectName = "";
	
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
	public String getQ_dtBalanceDateStart() {
		return q_dtBalanceDateStart;
	}
	public void setQ_dtBalanceDateStart(String qDtBalanceDateStart) {
		q_dtBalanceDateStart = qDtBalanceDateStart;
	}
	public String getQ_dtBalanceDateEnd() {
		return q_dtBalanceDateEnd;
	}
	public void setQ_dtBalanceDateEnd(String qDtBalanceDateEnd) {
		q_dtBalanceDateEnd = qDtBalanceDateEnd;
	}
	public long getQ_nStatusId() {
		return q_nStatusId;
	}
	public void setQ_nStatusId(long qNStatusId) {
		q_nStatusId = qNStatusId;
	}
	public long getQ_nKind() {
		return q_nKind;
	}
	public void setQ_nKind(long qNKind) {
		q_nKind = qNKind;
	}
	public String getQ_sSubjectName() {
		return q_sSubjectName;
	}
	public void setQ_sSubjectName(String qSSubjectName) {
		q_sSubjectName = qSSubjectName;
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
