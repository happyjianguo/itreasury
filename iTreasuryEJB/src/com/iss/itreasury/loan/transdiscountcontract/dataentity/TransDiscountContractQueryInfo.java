/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */

package com.iss.itreasury.loan.transdiscountcontract.dataentity;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.loan.util.LOANConstant;

import java.sql.Timestamp;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountContractQueryInfo extends LoanBaseDataEntity {

    private long id = -1;
    private long currencyId = -1;//
    private long officeId = -1;//
    private long userId = -1;//
    private String strUser = "";//
    
    private long queryPurpose = -1;//�������ͣ�1Ϊ�޸Ĳ��ң�2Ϊ��˲��ң�
    private long pageLineCount = LOANConstant.PageControl.CODE_PAGELINECOUNT;//ÿҳ��¼��Ŀ
    private long pageCount = 0;//��ҳ��
    private long pageNo = 1;//��ǰҳ��
    private long orderParam = -1;//����ֵ
    private String orderParamString = "";//�����ַ�
    private long desc = -1;//��������
    private long firstSearch = -1;//�Ƿ���ʾ���ҽ��
    
    private long recordCount=-1;//��¼��ѯ�����¼��
    private double totalAmount =-1;//��¼��ѯ����ܽ��
    
    private long actionID = -1; //�������޸Ļ��߸���
    

    private long startTransDiscountContractId = -1;//��ʼ��ͬ��ʶ
    private long endTransDiscountContractId = -1;//������ͬ��ʶ
    private String startTransDiscountContractCode = "";//��ʼ��ͬ���
    private String endTransDiscountContractCode = "";//������ͬ���
    private double startAmount = 0;//��ʼ���
    private double endAmount = 0;//�������
    private long clientId = -1;//�ͻ���ʶ
    private String clientCode = "";//�ͻ����
    private String clientName = "";//�ͻ�����
    private long queryStatusId = -1;//��ͬ״̬
    private Timestamp startInputDate = null;//��ʼ��ͬ¼������
    private Timestamp endInputDate = null;//������ͬ¼������
    private Timestamp startLoanDate = null;//��ʼ��������
    private Timestamp endLoanDate = null;//������������
    
    // ͬ�� - Ʊ�ݲ�ѯ
    private Timestamp startSContractDate=null;	// ��ͬ��ʼ����(��)
    private Timestamp startEContractDate=null;	// ��ͬ��ʼ����(��)
    private Timestamp endSContractDate=null;	// ��ͬ��������(��)
    private Timestamp endEContractDate=null; 	// ��ͬ��������(��)    
    private long inputUserId; // ¼���� (��ͬ������)
    
    //ת���ֲ�ѯ
    private long innorout = -1;//���������
    private long discounttype = -1;//��ϻ�ع�

	public long getInnorout() {
		return innorout;
	}
	public long getDiscounttype() {
		return discounttype;
	}
	public void setInnorout(long innorout) {
		this.innorout = innorout;
	}
	public void setDiscounttype(long discounttype) {
		this.discounttype = discounttype;
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
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(double amount) {
		totalAmount = amount;
	}  
	/**
	 * @return Returns the clientCode.
	 */
	public String getClientCode() {
		return clientCode;
	}
	/**
	 * @param clientCode The clientCode to set.
	 */
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId() {
		return clientId;
	}
	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return Returns the currencyId.
	 */
	public long getCurrencyId() {
		return currencyId;
	}
	/**
	 * @param currencyId The currencyId to set.
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
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
	 * @return Returns the endAmount.
	 */
	public double getEndAmount() {
		return endAmount;
	}
	/**
	 * @param endAmount The endAmount to set.
	 */
	public void setEndAmount(double endAmount) {
		this.endAmount = endAmount;
	}
	/**
	 * @return Returns the endInputDate.
	 */
	public Timestamp getEndInputDate() {
		return endInputDate;
	}
	/**
	 * @param endInputDate The endInputDate to set.
	 */
	public void setEndInputDate(Timestamp endInputDate) {
		this.endInputDate = endInputDate;
	}
	/**
	 * @return Returns the endLoanDate.
	 */
	public Timestamp getEndLoanDate() {
		return endLoanDate;
	}
	/**
	 * @param endLoanDate The endLoanDate to set.
	 */
	public void setEndLoanDate(Timestamp endLoanDate) {
		this.endLoanDate = endLoanDate;
	}
	/**
	 * @return Returns the endTransDiscountContractCode.
	 */
	public String getEndTransDiscountContractCode() {
		return endTransDiscountContractCode;
	}
	/**
	 * @param endTransDiscountContractCode The endTransDiscountContractCode to set.
	 */
	public void setEndTransDiscountContractCode(
			String endTransDiscountContractCode) {
		this.endTransDiscountContractCode = endTransDiscountContractCode;
	}
	/**
	 * @return Returns the endTransDiscountContractId.
	 */
	public long getEndTransDiscountContractId() {
		return endTransDiscountContractId;
	}
	/**
	 * @param endTransDiscountContractId The endTransDiscountContractId to set.
	 */
	public void setEndTransDiscountContractId(long endTransDiscountContractId) {
		this.endTransDiscountContractId = endTransDiscountContractId;
	}
	/**
	 * @return Returns the firstSearch.
	 */
	public long getFirstSearch() {
		return firstSearch;
	}
	/**
	 * @param firstSearch The firstSearch to set.
	 */
	public void setFirstSearch(long firstSearch) {
		this.firstSearch = firstSearch;
	}
	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
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
	 * @return Returns the queryStatusId.
	 */
	public long getQueryStatusId() {
		return queryStatusId;
	}
	/**
	 * @param queryStatusId The queryStatusId to set.
	 */
	public void setQueryStatusId(long queryStatusId) {
		this.queryStatusId = queryStatusId;
	}
	/**
	 * @return Returns the startAmount.
	 */
	public double getStartAmount() {
		return startAmount;
	}
	/**
	 * @param startAmount The startAmount to set.
	 */
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
	}
	/**
	 * @return Returns the startInputDate.
	 */
	public Timestamp getStartInputDate() {
		return startInputDate;
	}
	/**
	 * @param startInputDate The startInputDate to set.
	 */
	public void setStartInputDate(Timestamp startInputDate) {
		this.startInputDate = startInputDate;
	}
	/**
	 * @return Returns the startLoanDate.
	 */
	public Timestamp getStartLoanDate() {
		return startLoanDate;
	}
	/**
	 * @param startLoanDate The startLoanDate to set.
	 */
	public void setStartLoanDate(Timestamp startLoanDate) {
		this.startLoanDate = startLoanDate;
	}
	/**
	 * @return Returns the startTransDiscountContractCode.
	 */
	public String getStartTransDiscountContractCode() {
		return startTransDiscountContractCode;
	}
	/**
	 * @param startTransDiscountContractCode The startTransDiscountContractCode to set.
	 */
	public void setStartTransDiscountContractCode(
			String startTransDiscountContractCode) {
		this.startTransDiscountContractCode = startTransDiscountContractCode;
	}
	/**
	 * @return Returns the startTransDiscountContractId.
	 */
	public long getStartTransDiscountContractId() {
		return startTransDiscountContractId;
	}
	/**
	 * @param startTransDiscountContractId The startTransDiscountContractId to set.
	 */
	public void setStartTransDiscountContractId(
			long startTransDiscountContractId) {
		this.startTransDiscountContractId = startTransDiscountContractId;
	}
	/**
	 * @return Returns the strUser.
	 */
	public String getUser() {
		return strUser;
	}
	/**
	 * @param strUser The strUser to set.
	 */
	public void setUser(String strUser) {
		this.strUser = strUser;
	}
	/**
	 * @return Returns the userId.
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
    }
	/**
	 * @return Returns the actionID.
	 */
	public long getActionID() {
		return actionID;
	}
	/**
	 * @param actionID The actionID to set.
	 */
	public void setActionID(long actionID) {
		this.actionID = actionID;
	}
	public Timestamp getEndEContractDate() {
		return endEContractDate;
	}
	public void setEndEContractDate(Timestamp endEContractDate) {
		this.endEContractDate = endEContractDate;
	}
	public Timestamp getEndSContractDate() {
		return endSContractDate;
	}
	public void setEndSContractDate(Timestamp endSContractDate) {
		this.endSContractDate = endSContractDate;
	}
	public Timestamp getStartEContractDate() {
		return startEContractDate;
	}
	public void setStartEContractDate(Timestamp startEContractDate) {
		this.startEContractDate = startEContractDate;
	}
	public Timestamp getStartSContractDate() {
		return startSContractDate;
	}
	public void setStartSContractDate(Timestamp startSContractDate) {
		this.startSContractDate = startSContractDate;
	}
	public long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}
}
