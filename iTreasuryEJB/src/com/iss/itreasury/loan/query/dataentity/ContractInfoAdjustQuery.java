/**
 * ContractInfoAdjustQuery.java
 * Created on 2008��6��19��
 */
package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;

/**
 * ��ͬ���ʵ�����ѯ
 * @author ���ָ�
 * 2008-6-19
 * @version 
 */
public class ContractInfoAdjustQuery extends Object implements java.io.Serializable {

	/** Creates new ContractInfoAdjustQuery */
	public static final long TX=1;//����
	public static final long LOAN=2;//�Ŵ�
	public static final long ZTX=3;//��Ӫ����
	public static final long DB=4;//����
	
	public ContractInfoAdjustQuery() {
		super();
	}
	/**
	 * ��ѯ���� ��ͬ��š��ſ�֪ͨ����ʼ�����ɡ��ſ�֪ͨ����ʼ���ڵ�
	 */
	//��ͬ���
	private String ContractCode="";    	
	//�ſ�֪ͨ����ʼ������
	private String minStartDate=null;	
	//�ſ�֪ͨ����ʼ���ڵ�
	private String maxStartDate=null;
	//���´�
	private long officeID  = -1;
	//����
	private long currencyID = -1;	
	//�Ƿ�ѭ��
	private long isCircle=-1;	
	//��ѯ����
	private String queryLevel="";	
	//��ѯĿ��		
	public long queryPurpose=LOAN;
	//�����ֶ�
	private long orderParam=-1;	
	//desc
	private long desc=-1;
	private long ID = -1 ; //�ſ�֪ͨ����ʶID
	private long ContractID = -1 ; //��ͬID
	private long DrawNoticeID = -1 ; //�������֪ͨ����ʾ
	
	/**
	 * ��ѯ���
	 */
	private Timestamp DtoutDate;//�ſʼ����
	private String Code;//�ͻ����
	private String Name;//�ͻ�����
	private String SContractCode;//��ͬ���
	private String SCode;//�ſ�֪ͨ�����
	private long NIsCircle;//�Ƿ�ѭ������
	private String strIsCircle;//�Ƿ�ѭ������ 1:�� 2����
	private double MAmount;//�ſ���
	private double MInterestRate;//ִ������
	private double NIntervalNoticeNum;//�ſ�����
	
	/**
	 * getter and setter
	 * @return
	 */		
	public String getContractCode() {
		return ContractCode;
	}

	public void setContractCode(String contractCode) {
		ContractCode = contractCode;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(long isCircle) {
		this.isCircle = isCircle;
	}

	public String getMaxStartDate() {
		return maxStartDate;
	}

	public void setMaxStartDate(String maxStartDate) {
		this.maxStartDate = maxStartDate;
	}

	public String getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(String minStartDate) {
		this.minStartDate = minStartDate;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public String getQueryLevel() {
		return queryLevel;
	}

	public void setQueryLevel(String queryLevel) {
		this.queryLevel = queryLevel;
	}

	public long getQueryPurpose() {
		return queryPurpose;
	}

	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}

	public long getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public Timestamp getDtoutDate() {
		return DtoutDate;
	}

	public void setDtoutDate(Timestamp dtoutDate) {
		DtoutDate = dtoutDate;
	}

	public double getMAmount() {
		return MAmount;
	}

	public void setMAmount(double amount) {
		MAmount = amount;
	}

	public double getMInterestRate() {
		return MInterestRate;
	}

	public void setMInterestRate(double interestRate) {
		MInterestRate = interestRate;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getNIntervalNoticeNum() {
		return NIntervalNoticeNum;
	}

	public void setNIntervalNoticeNum(double intervalNoticeNum) {
		NIntervalNoticeNum = intervalNoticeNum;
	}

	public long getNIsCircle() {
		return NIsCircle;
	}

	public void setNIsCircle(long isCircle) {
		NIsCircle = isCircle;
	}

	public String getSCode() {
		return SCode;
	}

	public void setSCode(String code) {
		SCode = code;
	}

	public String getSContractCode() {
		return SContractCode;
	}

	public void setSContractCode(String contractCode) {
		SContractCode = contractCode;
	}

	public String getStrIsCircle() {
		return strIsCircle;
	}

	public void setStrIsCircle(String strIsCircle) {
		this.strIsCircle = strIsCircle;
	}

	public long getContractID() {
		return ContractID;
	}

	public void setContractID(long contractID) {
		ContractID = contractID;
	}

	public long getDrawNoticeID() {
		return DrawNoticeID;
	}

	public void setDrawNoticeID(long drawNoticeID) {
		DrawNoticeID = drawNoticeID;
	}

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}
	
		
}