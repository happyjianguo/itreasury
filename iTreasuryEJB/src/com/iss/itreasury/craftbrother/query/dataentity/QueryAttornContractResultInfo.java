package com.iss.itreasury.craftbrother.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class QueryAttornContractResultInfo  extends SECBaseDataEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id = -1;

	private long officeId = -1;				//���´�ID
	private long currencyId = -1;			//����ID
	private long transcationTypeId = -1;    //��������ID
	private String transcationType = "";    //������������
	private double totalSaleAmount = 0.0;   //�ۼ��������/�ۼ�������
	private double totalBuyAmount = 0.0;   //�ۼ��ջؽ��/�ۼ�����ع����
	private String contractCode = "";      //��ͬ���
	private double contractAmount = 0.0;   //��ͬ���
	private double buyAmount = 0.0;        //����Ľ��/�������
	private double saleAmount = 0.0;       //�ջصĽ��/����ع����
	private double contractRate = 0.0;     //��ͬ����
	private double noticeRate = 0.0;       //֪ͨ������
	private Timestamp inputDate = null;    //��ͬ¼��ʱ��
	private Timestamp startDate = null;    //��ѯ��ʼʱ��
	private Timestamp endDate = null;    //��ѯ����ʱ��
	
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public double getContractRate() {
		return contractRate;
	}
	public void setContractRate(double contractRate) {
		this.contractRate = contractRate;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public double getTotalBuyAmount() {
		return totalBuyAmount;
	}
	public void setTotalBuyAmount(double totalBuyAmount) {
		this.totalBuyAmount = totalBuyAmount;
	}
	public double getTotalSaleAmount() {
		return totalSaleAmount;
	}
	public void setTotalSaleAmount(double totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}
	public String getTranscationType() {
		return transcationType;
	}
	public void setTranscationType(String transcationType) {
		this.transcationType = transcationType;
	}
	public long getTranscationTypeId() {
		return transcationTypeId;
	}
	public void setTranscationTypeId(long transcationTypeId) {
		this.transcationTypeId = transcationTypeId;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
	public double getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(double saleAmount) {
		this.saleAmount = saleAmount;
	}
	public double getNoticeRate() {
		return noticeRate;
	}
	public void setNoticeRate(double noticeRate) {
		this.noticeRate = noticeRate;
	}
	
}
