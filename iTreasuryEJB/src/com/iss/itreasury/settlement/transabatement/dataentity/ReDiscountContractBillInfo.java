/*
 * Created on 2004-9-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transabatement.dataentity;

import java.sql.Timestamp;

/**
 * @author ycliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReDiscountContractBillInfo {
	
	private long id = -1 ;                //Ʊ��id
	private double amount = 0.0;           //Ʊ�ݽ��
	
	private long billSourceTypeId = -1;   //Ʊ����Դ����
	private long nSerialNo = -1;               //���к�
	private String sCode ="";                   //��Ʊ����
	//��Ʊ���
	private long nContractId =-1;              //���ֺ�ͬ //����ƾ֤ 
	private long discountCredenceId = -1;       //��Ӧ����ƾ֤id
	
	private long nReContractId = -1;           //ת���������ͬ
	private long reDiscountCredenceId = -1;     //ת����ƾ֤ 
	private Timestamp dtCreate =null;            //��Ʊ�� 
	private Timestamp dtEnd = null;               //������ 
	private long nAcceptpoTypeId =-1;          //��Ʊ���� 
	private long nInOrOut =-1;                //ת�������� 
	private long nDiscountTypeId = -1;          //ת�������� 

	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the billSourceTypeId.
	 */
	public long getBillSourceTypeId() {
		return billSourceTypeId;
	}
	/**
	 * @param billSourceTypeId The billSourceTypeId to set.
	 */
	public void setBillSourceTypeId(long billSourceTypeId) {
		this.billSourceTypeId = billSourceTypeId;
	}
	/**
	 * @return Returns the discountCredenceId.
	 */
	public long getDiscountCredenceId() {
		return discountCredenceId;
	}
	/**
	 * @param discountCredenceId The discountCredenceId to set.
	 */
	public void setDiscountCredenceId(long discountCredenceId) {
		this.discountCredenceId = discountCredenceId;
	}
	/**
	 * @return Returns the dtCreate.
	 */
	public Timestamp getDtCreate() {
		return dtCreate;
	}
	/**
	 * @param dtCreate The dtCreate to set.
	 */
	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}
	/**
	 * @return Returns the dtEnd.
	 */
	public Timestamp getDtEnd() {
		return dtEnd;
	}
	/**
	 * @param dtEnd The dtEnd to set.
	 */
	public void setDtEnd(Timestamp dtEnd) {
		this.dtEnd = dtEnd;
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
	 * @return Returns the nAcceptpoTypeId.
	 */
	public long getNAcceptpoTypeId() {
		return nAcceptpoTypeId;
	}
	/**
	 * @param acceptpoTypeId The nAcceptpoTypeId to set.
	 */
	public void setNAcceptpoTypeId(long acceptpoTypeId) {
		nAcceptpoTypeId = acceptpoTypeId;
	}
	/**
	 * @return Returns the nContractId.
	 */
	public long getNContractId() {
		return nContractId;
	}
	/**
	 * @param contractId The nContractId to set.
	 */
	public void setNContractId(long contractId) {
		nContractId = contractId;
	}
	/**
	 * @return Returns the nDiscountTypeId.
	 */
	public long getNDiscountTypeId() {
		return nDiscountTypeId;
	}
	/**
	 * @param discountTypeId The nDiscountTypeId to set.
	 */
	public void setNDiscountTypeId(long discountTypeId) {
		nDiscountTypeId = discountTypeId;
	}
	/**
	 * @return Returns the nInOrOut.
	 */
	public long getNInOrOut() {
		return nInOrOut;
	}
	/**
	 * @param inOrOut The nInOrOut to set.
	 */
	public void setNInOrOut(long inOrOut) {
		nInOrOut = inOrOut;
	}
	/**
	 * @return Returns the nReContractId.
	 */
	public long getNReContractId() {
		return nReContractId;
	}
	/**
	 * @param reContractId The nReContractId to set.
	 */
	public void setNReContractId(long reContractId) {
		nReContractId = reContractId;
	}
	/**
	 * @return Returns the nSerialNo.
	 */
	public long getNSerialNo() {
		return nSerialNo;
	}
	/**
	 * @param serialNo The nSerialNo to set.
	 */
	public void setNSerialNo(long serialNo) {
		nSerialNo = serialNo;
	}
	/**
	 * @return Returns the reDiscountCredenceId.
	 */
	public long getReDiscountCredenceId() {
		return reDiscountCredenceId;
	}
	/**
	 * @param reDiscountCredenceId The reDiscountCredenceId to set.
	 */
	public void setReDiscountCredenceId(long reDiscountCredenceId) {
		this.reDiscountCredenceId = reDiscountCredenceId;
	}
	/**
	 * @return Returns the sCode.
	 */
	public String getSCode() {
		return sCode;
	}
	/**
	 * @param code The sCode to set.
	 */
	public void setSCode(String code) {
		sCode = code;
	}
}
