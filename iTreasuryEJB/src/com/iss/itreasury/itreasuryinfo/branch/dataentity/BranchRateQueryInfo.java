package com.iss.itreasury.itreasuryinfo.branch.dataentity;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
/**
 * ���ڲ�ѯ�����˻��������У�������Ϣ������ȫ����ѯ�����ֶ�����
 * @author gmqiu
 *
 */
public class BranchRateQueryInfo extends BranchRateInfo {

	private static final long serialVersionUID = 1L;
	
	private long id = -1;	                /** ID������ */
	private long nOfficeId = -1; 	        /** ���´����ID(Ĭ��-1) */
	private long nCurrencyId = -1;	        /** ����ID(Ĭ��-1) */
	private long branchId = -1; 	        /** �����˻��������У�ID������sett_branch ���ID(Ĭ��-1) */
	private Timestamp dtValidate = null;	/** ������Ч���� */
	private double mRate = 0.0;		        /** ����ֵ�������ʣ�*/
	private long nInputUserId = -1;	        /** ¼����ID(Ĭ��-1) */
	private Timestamp dtInputDate = null;	/** ¼������ */
	private long nCheckUserId = -1;			/** ������ID */
	private Timestamp dtCheckDate = null;   /** �������� */
	
	//��ѯ����
	private Timestamp dtValidateStart = null;   /** ������Ч��ʼ���� */
	private Timestamp dtValidateEnd = null;   /** ������Ч�������� */
	private String branchName = "";        /** ���������� */
	private long nStatusId = -1;	       /** ״̬(0--��ɾ����1--�ѱ��棬2--δ���ˣ�3--�Ѹ���) */
	
	private String[] ids = null;
	
	private String validateStart = "";   /** ������Ч��ʼ���� */
	private String validateEnd = "";   /** ������Ч�������� */
	private String sBanchCode = "";
	private String sName = "";   /** ���������� */
	
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
