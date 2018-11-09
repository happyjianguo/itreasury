/*
 * Created on 2004-02-02
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.obinstruction.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryOBFinanceInstrInfo extends BaseDataEntity implements Serializable
{

	/**
	 * 
	 */
	public QueryOBFinanceInstrInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long OfficeID = -1;                      //���´�  
	private long CurrencyID = -1;                    //����
	
	private long TransType = -1;                     //������������
	private long Status = -1;                        //ָ��״̬
//	private Timestamp ConfirmDateFrom = null;        //ȷ��ʱ��
//	private Timestamp ConfirmDateTo = null;          //ȷ��ʱ��
	private double AmountFrom = 0.0;                 //���׽��
	private double AmountTo = 0.0;                   //���׽��
	private String DateRadio = "";						//����ѡ��
	private Timestamp ExecuteDateFrom = null;        //ִ��ʱ��
	private Timestamp ExecuteDateTo = null;          //ִ��ʱ��
	private Timestamp CheckDateFrom = null;        //ִ��ʱ��
	private Timestamp CheckDateTo = null;          //ִ��ʱ��
	private Timestamp SignDateFrom = null;        //ִ��ʱ��
	private Timestamp SignDateTo = null;          //ִ��ʱ��
	private String TransNo = "";                     //���׺�
	private long InstructionNo = -1;                 //ָ���
	
	private String AccountNoFrom = "";               //�˻����
    private String AccountNoTo = "";                 //�˻����
    
	private String ClientNoFrom = "";               //�ͻ����
	private String ClientNoTo = "";                 //�ͻ����
	
	private long Desc = 1;                           //����ʽ
   	private long OrderField = 1;                      //�����ֶ�

 	private long userID = -1;                      //��¼��ID  Ϊ�������������������н��渴���ж� 	
 	
 	private long Source = -1;                      //������Դ��1����̨ 2������ 3������ 4���ϣ�SAP���ⲿϵͳ��
 	private String ApplyCode = "";				   //����ָ����
 	 
 	private String bankName = "";                  //������ȫ��
 	
 	private Timestamp openDate = null;          //��ǰ��������
 	
 	private long id = -1;
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
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
//	public Timestamp getConfirmDateFrom() {
//		return ConfirmDateFrom;
//	}
//
//	/**
//	 * @return
//	 */
//	public Timestamp getConfirmDateTo() {
//		return ConfirmDateTo;
//	}
	

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDateFrom() {
		return ExecuteDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDateTo() {
		return ExecuteDateTo;
	}

	/**
	 * @return
	 */
	public long getInstructionNo() {
		return InstructionNo;
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
	public long getStatus() {
		return Status;
	}

	/**
	 * @return
	 */
	public String getTransNo() {
		return TransNo;
	}

	/**
	 * @return
	 */
	public long getTransType() {
		return TransType;
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
	 * @param timestamp
	 */
//	public void setConfirmDateFrom(Timestamp timestamp) {
//		ConfirmDateFrom = timestamp;
//	}
//
//	/**
//	 * @param timestamp
//	 */
//	public void setConfirmDateTo(Timestamp timestamp) {
//		ConfirmDateTo = timestamp;
//	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateFrom(Timestamp timestamp) {
		ExecuteDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateTo(Timestamp timestamp) {
		ExecuteDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInstructionNo(long l) {
		InstructionNo = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		Status = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string) {
		TransNo = string;
	}

	/**
	 * @param l
	 */
	public void setTransType(long l) {
		TransType = l;
	}

	/**
	 * @return
	 */
	public long getDesc() {
		return Desc;
	}

	/**
	 * @return
	 */
	public long getOrderField() {
		return OrderField;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l) {
		Desc = l;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l) {
		OrderField = l;
	}

	/**
	 * @return
	 */
	public String getAccountNoFrom() {
		return AccountNoFrom;
	}

	/**
	 * @return
	 */
	public String getAccountNoTo() {
		return AccountNoTo;
	}

	/**
	 * @param string
	 */
	public void setAccountNoFrom(String string) {
		AccountNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNoTo(String string) {
		AccountNoTo = string;
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
	 * @return ���� checkDateFrom��
	 */
	public Timestamp getCheckDateFrom() {
		return CheckDateFrom;
	}

	/**
	 * @param checkDateFrom Ҫ���õ� checkDateFrom��
	 */
	public void setCheckDateFrom(Timestamp checkDateFrom) {
		CheckDateFrom = checkDateFrom;
	}

	/**
	 * @return ���� checkDateTo��
	 */
	public Timestamp getCheckDateTo() {
		return CheckDateTo;
	}

	/**
	 * @param checkDateTo Ҫ���õ� checkDateTo��
	 */
	public void setCheckDateTo(Timestamp checkDateTo) {
		CheckDateTo = checkDateTo;
	}

	/**
	 * @return ���� signDateFrom��
	 */
	public Timestamp getSignDateFrom() {
		return SignDateFrom;
	}

	/**
	 * @param signDateFrom Ҫ���õ� signDateFrom��
	 */
	public void setSignDateFrom(Timestamp signDateFrom) {
		SignDateFrom = signDateFrom;
	}

	/**
	 * @return ���� signDateTo��
	 */
	public Timestamp getSignDateTo() {
		return SignDateTo;
	}

	/**
	 * @param signDateTo Ҫ���õ� signDateTo��
	 */
	public void setSignDateTo(Timestamp signDateTo) {
		SignDateTo = signDateTo;
	}

	/**
	 * @return ���� dateRadio��
	 */
	public String getDateRadio() {
		return DateRadio;
	}

	/**
	 * @param dateRadio Ҫ���õ� dateRadio��
	 */
	public void setDateRadio(String dateRadio) {
		DateRadio = dateRadio;
	}

	public long getSource() {
		return Source;
	}

	public void setSource(long source) {
		Source = source;
	}

	public String getApplyCode() {
		return ApplyCode;
	}

	public void setApplyCode(String applyCode) {
		ApplyCode = applyCode;
	}

	public Timestamp getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
