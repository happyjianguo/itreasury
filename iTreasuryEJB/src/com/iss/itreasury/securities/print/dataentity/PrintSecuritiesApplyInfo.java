/*
 * �������� 2004-5-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
 
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author ����
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class PrintSecuritiesApplyInfo extends SECBaseDataEntity	implements Serializable {

	//֤ȯ����
	private String securitiesName          = "";
	//������
	private String deliveryOrderCode   = "";		
	//ҵ��λ����
	private String clientName          = "";
	//�ɶ��ʻ�����
	private String stockHolderAccountName = ""; 	
	//ҵ������name
	private String businessTypeName    = "";
	//ҵ������id
	private long businessTypeId        = -1;
	//��������name
	private String transactionTypeName = "";
	//��������Id
	private long transactionTypeID     = -1;
	//ҵ������ID
	private long businessAttributeID     = -1;
	//��������
	private String issueUnderwriter = "";
	//ָ������Ӫҵ��������ҵ������Id�Ĳ�ͬ����ͬ��
	private String counterPartName     = "";
	//�ʽ��ʺ�
	private String accountcode         = "";	
	//�깺1 - �깺����
	private Timestamp applyDate  = null;
	//�깺2 - ����
	private double applyQuantity     = 0.0;
	//�깺3 - �۸�
	private double applyPrice     = 0.0;
	//�깺4 - ���
	private double applyAmount     = 0.0;
	//�깺5 -ʵ���ո�
	private double applyNetIncome  = 0.0;
	//�����������ҳ���ϼ� % ��
	private double marginRate     = 0.0;	
	//�б�1 - �б�����
	private Timestamp confirmDate  = null;
	//�б�2 - ����
	private double confirmQuantity     = 0.0;
	//�б�3 - �۸�
	private double confirmPrice     = 0.0;
	//�б�4 - ���
	private double confirmAmount     = 0.0;
	//Ԥ�������ѷ���
	private double drawbackAmount     = 0.0;
	//������1 - �۸�
	private double sellingPrice         = 0.0;
	//������2 - ����
	private double sellingQuantity      = 0.0;
	//������3 - ���
	private double sellingAmount        = 0.0;
	
    //�ּ�
    private double closePrice = 0.0;           
	
    //ʣ��ɱ�
	private double cost = 0.0;                
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub

	}



	/**
	 * @return
	 */
	public String getAccountcode() {
		return accountcode;
	}

	/**
	 * @return
	 */
	public double getApplyAmount() {
		return applyAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getApplyDate() {
		return applyDate;
	}

	/**
	 * @return
	 */
	public double getApplyPrice() {
		return applyPrice;
	}

	/**
	 * @return
	 */
	public double getApplyQuantity() {
		return applyQuantity;
	}

	/**
	 * @return
	 */
	public long getBusinessAttributeID() {
		return businessAttributeID;
	}

	/**
	 * @return
	 */
	public long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @return
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @return
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @return
	 */
	public double getConfirmAmount() {
		return confirmAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getConfirmDate() {
		return confirmDate;
	}

	/**
	 * @return
	 */
	public double getConfirmPrice() {
		return confirmPrice;
	}

	/**
	 * @return
	 */
	public double getConfirmQuantity() {
		return confirmQuantity;
	}

	/**
	 * @return
	 */
	public String getCounterPartName() {
		return counterPartName;
	}

	/**
	 * @return
	 */
	public String getDeliveryOrderCode() {
		return deliveryOrderCode;
	}

	/**
	 * @return
	 */
	public double getDrawbackAmount() {
		return drawbackAmount;
	}

	/**
	 * @return
	 */
	public String getIssueUnderwriter() {
		return issueUnderwriter;
	}

	/**
	 * @return
	 */
	public double getMarginRate() {
		return marginRate;
	}

	/**
	 * @return
	 */
	public String getSecuritiesName() {
		return securitiesName;
	}

	/**
	 * @return
	 */
	public String getStockHolderAccountName() {
		return stockHolderAccountName;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}

	/**
	 * @return
	 */
	public String getTransactionTypeName() {
		return transactionTypeName;
	}

	/**
	 * @param string
	 */
	public void setAccountcode(String string) {
		accountcode = string;
	}

	/**
	 * @param d
	 */
	public void setApplyAmount(double d) {
		applyAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setApplyDate(Timestamp timestamp) {
		applyDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setApplyPrice(double d) {
		applyPrice = d;
	}

	/**
	 * @param d
	 */
	public void setApplyQuantity(double d) {
		applyQuantity = d;
	}

	/**
	 * @param l
	 */
	public void setBusinessAttributeID(long l) {
		businessAttributeID = l;
	}

	/**
	 * @param l
	 */
	public void setBusinessTypeId(long l) {
		businessTypeId = l;
	}

	/**
	 * @param string
	 */
	public void setBusinessTypeName(String string) {
		businessTypeName = string;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string) {
		clientName = string;
	}

	/**
	 * @param d
	 */
	public void setConfirmAmount(double d) {
		confirmAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setConfirmDate(Timestamp timestamp) {
		confirmDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setConfirmPrice(double d) {
		confirmPrice = d;
	}

	/**
	 * @param d
	 */
	public void setConfirmQuantity(double d) {
		confirmQuantity = d;
	}

	/**
	 * @param string
	 */
	public void setCounterPartName(String string) {
		counterPartName = string;
	}

	/**
	 * @param string
	 */
	public void setDeliveryOrderCode(String string) {
		deliveryOrderCode = string;
	}

	/**
	 * @param d
	 */
	public void setDrawbackAmount(double d) {
		drawbackAmount = d;
	}

	/**
	 * @param string
	 */
	public void setIssueUnderwriter(String string) {
		issueUnderwriter = string;
	}

	/**
	 * @param d
	 */
	public void setMarginRate(double d) {
		marginRate = d;
	}

	/**
	 * @param string
	 */
	public void setSecuritiesName(String string) {
		securitiesName = string;
	}

	/**
	 * @param string
	 */
	public void setStockHolderAccountName(String string) {
		stockHolderAccountName = string;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l) {
		transactionTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setTransactionTypeName(String string) {
		transactionTypeName = string;
	}

	/**
	 * @return
	 */
	public double getApplyNetIncome()
	{
		return applyNetIncome;
	}

	/**
	 * @param d
	 */
	public void setApplyNetIncome(double d)
	{
		applyNetIncome = d;
	}

	
	/**
	 * @return
	 */
	public double getSellingAmount()
	{
		return sellingAmount;
	}

	/**
	 * @return
	 */
	public double getSellingPrice()
	{
		return sellingPrice;
	}

	/**
	 * @return
	 */
	public double getSellingQuantity()
	{
		return sellingQuantity;
	}

	/**
	 * @param d
	 */
	public void setSellingAmount(double d)
	{
		sellingAmount = d;
	}

	/**
	 * @param d
	 */
	public void setSellingPrice(double d)
	{
		sellingPrice = d;
	}

	/**
	 * @param d
	 */
	public void setSellingQuantity(double d)
	{
		sellingQuantity = d;
	}
	/**
	 * @return Returns the closePrice.
	 */
	public double getClosePrice() {
		return closePrice;
	}
	/**
	 * @param closePrice The closePrice to set.
	 */
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	/**
	 * @return Returns the cost.
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * @param cost The cost to set.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
}
