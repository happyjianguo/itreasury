package com.iss.itreasury.itreasuryinfo.lending.dataentity;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import com.iss.itreasury.util.Constant;
/**
 * ���ڲ�ѯ���뵥��Ϣ������ȫ����ѯ�����ֶ�����
 * @author gmqiu
 *
 */
public class LendingFormQueryInfo {

	private static final long serialVersionUID = 950923643638392924L;
	
	private long id = -1;	                /** ID������ */
	private long q_nOfficeId = -1;	            /** ���´����ID */
	private long q_nCurrencyId = -1;	            /** ����ID */
	private String q_dtStartDate = "";		/** �������ڣ���ʼ���ڣ�*/
	private String q_dtEndDate = "";			/** �������ڣ������գ�*/
	private String q_sCounterParty = "";		    /** ���ַ����� */
	private String q_sRecBankAccountNo = "";	    /** �տ��˻��ţ������ʽ��أ� */
	private long q_nStatusId = -1;	            /** ����״̬(0--��ɾ��,1--���뱣��,3--�Ѳ���,4--�����,5--�ѻ���) */
	private boolean q_isReview = false;        /** �Ƿ���д����˲�ѯ */
	private long q_currentUserId = -1;
	private String q_sCounterPartys = "";
	
	//��ҳ����
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
	public String getQ_dtStartDate() {
		return q_dtStartDate;
	}
	public void setQ_dtStartDate(String qDtStartDate) {
		q_dtStartDate = qDtStartDate;
	}
	public String getQ_dtEndDate() {
		return q_dtEndDate;
	}
	public void setQ_dtEndDate(String qDtEndDate) {
		q_dtEndDate = qDtEndDate;
	}
	public String getQ_sCounterParty() {
		return q_sCounterParty;
	}
	public void setQ_sCounterParty(String qSCounterParty) {
		q_sCounterParty = qSCounterParty;
	}
	public String getQ_sCounterPartys() {
		return q_sCounterPartys;
	}
	public void setQ_sCounterPartys(String qSCounterPartys) {
		q_sCounterPartys = qSCounterPartys;
	}
	public String getQ_sRecBankAccountNo() {
		return q_sRecBankAccountNo;
	}
	public void setQ_sRecBankAccountNo(String qSRecBankAccountNo) {
		q_sRecBankAccountNo = qSRecBankAccountNo;
	}
	public long getQ_nStatusId() {
		return q_nStatusId;
	}
	public void setQ_nStatusId(long qNStatusId) {
		q_nStatusId = qNStatusId;
	}
	public boolean isQ_isReview() {
		return q_isReview;
	}
	public void setQ_isReview(boolean qIsReview) {
		q_isReview = qIsReview;
	}
	public long getQ_nOfficeId() {
		return q_nOfficeId;
	}
	public void setQ_nOfficeId(long qNOfficeId) {
		q_nOfficeId = qNOfficeId;
	}
	public long getQ_nCurrencyId() {
		return q_nCurrencyId;
	}
	public void setQ_nCurrencyId(long qNCurrencyId) {
		q_nCurrencyId = qNCurrencyId;
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
	public long getQ_currentUserId() {
		return q_currentUserId;
	}
	public void setQ_currentUserId(long qCurrentUserId) {
		q_currentUserId = qCurrentUserId;
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
