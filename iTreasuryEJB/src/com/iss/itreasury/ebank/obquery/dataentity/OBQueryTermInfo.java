/*
 * Created on 2004-2-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obquery.dataentity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import com.iss.itreasury.util.*;
import java.sql.Timestamp;

public class OBQueryTermInfo implements java.io.Serializable
{
	public static final long TX=2;
	public static final long CONTRACT=1;
	long clientID=-1;
	long typeID=99;					//����ID������ָ�����࣬�������࣬��ͬ�����
	long statusID=99;				//״̬����������״̬
	long currencyID=0;				//����
	String inputDateBegin="";	//�������ڿ�ʼ
	String inputDateEnd="";	//�������ڽ���
	String codeBegin="";			//��ſ�ʼ
	long contractID=-1;
	String codeEnd="";				//��Ž���
	String instrNoBegin="";			//ָ���ſ�ʼ
	String instrNoEnd="";			//ָ���Ž���
	long inputUserID=-1;			//������ ID
	String inputUserName="";
	long borrowClientID=-1;			//��λID
	String borrowClientName="";
	String borrowClientAccount="";	//��λ�˺�
	long consignClientID=-1;		//ί�е�λID
	String consignClientName="";
	String consignClientAccount="";	//ί�е�λ�˺�
	double amountBegin=0;			//��ʼ
	double amountEnd=0;				//������
	double amountBegin2=0;
	double amountEnd2=0;
	long intervalNum=-1;			//����
	String loanDateBegin="";	//�������ڿ�ʼ
	String loanDateEnd="";		//�������ڽ���
	long assureTypeID=-1;			//��֤��ʽ
	long creditLevelID=-1;			//���õȼ�
	long isPartner=-1;				//�Ƿ�ɶ�
	long isTechnical=-1;			//�Ƿ񼼸�
	long isCircle=-1;				//�Ƿ�ѭ��
	long loanClientTypeID=-1;		//��Ӫ����ͻ�����
	long parentCorpID=-1;			//�ϼ����ܲ���
	long riskLevelID=-1;			//��������
	long typeID1=-1;				//����������
	long typeID2=-1;				//����ҵ����1
	long typeID3=-1;				//����ҵ����2
	long txtPageNo=1;
	long lPageCount=1;
	long lOrderParam=99;
	long lDesc= Constant.PageControl.CODE_ASCORDESC_ASC;
	long queryPurpose=1;
	
	public void reset()
	{
		clientID=-1;
		typeID=99;					//����ID������ָ�����࣬�������࣬��ͬ�����
		statusID=99;				//״̬����������״̬
		inputDateBegin="";	//�������ڿ�ʼ
		inputDateEnd="";	//�������ڽ���
		codeBegin="";			//��ſ�ʼ
		contractID=-1;
		codeEnd="";				//��Ž���
		instrNoBegin="";			//ָ���ſ�ʼ
		instrNoEnd="";			//ָ���Ž���
		inputUserID=-1;			//������ ID
		inputUserName="";
		borrowClientID=-1;			//��λID
		borrowClientName="";
		borrowClientAccount="";	//��λ�˺�
		consignClientID=-1;		//ί�е�λID
		consignClientName="";
		consignClientAccount="";	//ί�е�λ�˺�
		amountBegin=0;			//��ʼ
		amountEnd=0;				//������
		amountBegin2=0;
		amountEnd2=0;
		intervalNum=-1;			//����
		loanDateBegin="";	//�������ڿ�ʼ
		loanDateEnd="";		//�������ڽ���
		assureTypeID=-1;			//��֤��ʽ
		creditLevelID=-1;			//���õȼ�
		isPartner=-1;				//�Ƿ�ɶ�
		isTechnical=-1;			//�Ƿ񼼸�
		isCircle=-1;				//�Ƿ�ѭ��
		loanClientTypeID=-1;		//��Ӫ����ͻ�����
		parentCorpID=-1;			//�ϼ����ܲ���
		riskLevelID=-1;			//��������
		typeID1=-1;				//����������
		typeID2=-1;				//����ҵ����1
		typeID3=-1;				//����ҵ����2
		txtPageNo=1;
		lPageCount=1;
		lOrderParam=99;
		lDesc= Constant.PageControl.CODE_ASCORDESC_ASC;
		queryPurpose=1;
	}
	/**
	 * @return
	 */
	public double getAmountBegin()
	{
		return amountBegin;
	}

	/**
	 * @return
	 */
	public double getAmountEnd()
	{
		return amountEnd;
	}

	/**
	 * @return
	 */
	public long getAssureTypeID()
	{
		return assureTypeID;
	}

	/**
	 * @return
	 */
	public String getBorrowClientAccount()
	{
		return borrowClientAccount;
	}

	/**
	 * @return
	 */
	public long getBorrowClientID()
	{
		return borrowClientID;
	}

	/**
	 * @return
	 */
	public String getCodeBegin()
	{
		return codeBegin;
	}

	/**
	 * @return
	 */
	public String getCodeEnd()
	{
		return codeEnd;
	}

	/**
	 * @return
	 */
	public String getConsignClientAccount()
	{
		return consignClientAccount;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return consignClientID;
	}

	/**
	 * @return
	 */
	public long getCreditLevelID()
	{
		return creditLevelID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDateBegin()
	{
		return DataFormat.getDateTime( inputDateBegin );
	}

	/**
	 * @return
	 */
	public Timestamp getInputDateEnd()
	{
		return DataFormat.getDateTime(inputDateEnd);
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}

	/**
	 * @return
	 */
	public String getInstrNoBegin()
	{
		return instrNoBegin;
	}

	/**
	 * @return
	 */
	public String getInstrNoEnd()
	{
		return instrNoEnd;
	}

	/**
	 * @return
	 */
	public long getIntervalNum()
	{
		return intervalNum;
	}

	/**
	 * @return
	 */
	public long getIsCircle()
	{
		return isCircle;
	}

	/**
	 * @return
	 */
	public long getIsPartner()
	{
		return isPartner;
	}

	/**
	 * @return
	 */
	public long getIsTechnical()
	{
		return isTechnical;
	}

	/**
	 * @return
	 */
	public long getLoanClientTypeID()
	{
		return loanClientTypeID;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanDateBegin()
	{
		return DataFormat.getDateTime(loanDateBegin);
	}

	/**
	 * @return
	 */
	public Timestamp getLoanDateEnd()
	{
		return DataFormat.getDateTime(loanDateEnd);
	}

	/**
	 * @return
	 */
	public long getParentCorpID()
	{
		return parentCorpID;
	}

	/**
	 * @return
	 */
	public long getRiskLevelID()
	{
		return riskLevelID;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * @return
	 */
	public long getTypeID()
	{
		return typeID;
	}

	/**
	 * @return
	 */
	public long getTypeID1()
	{
		return typeID1;
	}

	/**
	 * @return
	 */
	public long getTypeID2()
	{
		return typeID2;
	}

	/**
	 * @return
	 */
	public long getTypeID3()
	{
		return typeID3;
	}

	/**
	 * @param d
	 */
	public void setAmountBegin(double d)
	{
		amountBegin = d;
	}

	/**
	 * @param d
	 */
	public void setAmountEnd(double d)
	{
		amountEnd = d;
	}

	/**
	 * @param l
	 */
	public void setAssureTypeID(long l)
	{
		assureTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setBorrowClientAccount(String string)
	{
		borrowClientAccount = string;
	}

	/**
	 * @param l
	 */
	public void setBorrowClientID(long l)
	{
		borrowClientID = l;
	}

	/**
	 * @param string
	 */
	public void setCodeBegin(String string)
	{
		if (!string.equals("-1"))
			codeBegin = string;
		else
			codeBegin="";	
	}

	/**
	 * @param string
	 */
	public void setCodeEnd(String string)
	{
		if (!string.equals("-1"))
			codeEnd = string;
		else
			codeEnd="";	
	}

	/**
	 * @param string
	 */
	public void setConsignClientAccount(String string)
	{
		consignClientAccount = string;
	}

	/**
	 * @param l
	 */
	public void setConsignClientID(long l)
	{
		consignClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCreditLevelID(long l)
	{
		creditLevelID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDateBegin(String timestamp)
	{
		inputDateBegin = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDateEnd(String timestamp)
	{
		inputDateEnd = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		inputUserID = l;
	}

	/**
	 * @param string
	 */
	public void setInstrNoBegin(String string)
	{
		if (!string.equals("-1"))
			instrNoBegin = string;
		else
			instrNoBegin="";	
	}

	/**
	 * @param string
	 */
	public void setInstrNoEnd(String string)
	{
		if (!string.equals("-1"))
			instrNoEnd = string;
		else
			instrNoEnd="";
	}

	/**
	 * @param l
	 */
	public void setIntervalNum(long l)
	{
		intervalNum = l;
	}

	/**
	 * @param l
	 */
	public void setIsCircle(long l)
	{
		isCircle = l;
	}

	/**
	 * @param l
	 */
	public void setIsPartner(long l)
	{
		isPartner = l;
	}

	/**
	 * @param l
	 */
	public void setIsTechnical(long l)
	{
		isTechnical = l;
	}

	/**
	 * @param l
	 */
	public void setLoanClientTypeID(long l)
	{
		loanClientTypeID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setLoanDateBegin(String timestamp)
	{
		loanDateBegin = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setLoanDateEnd(String timestamp)
	{
		loanDateEnd = timestamp;
	}

	/**
	 * @param l
	 */
	public void setParentCorpID(long l)
	{
		parentCorpID = l;
	}

	/**
	 * @param l
	 */
	public void setRiskLevelID(long l)
	{
		riskLevelID = l;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID(long l)
	{
		typeID = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID1(long l)
	{
		typeID1 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID2(long l)
	{
		typeID2 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID3(long l)
	{
		typeID3 = l;
	}


	/**
	 * @return
	 */
	public String getInputUserName() {
		return inputUserName;
	}

	/**
	 * @param string
	 */
	public void setInputUserName(String string) {
		inputUserName = string;
	}

	/**
	 * @return
	 */
	public long getLDesc() {
		return lDesc;
	}

	/**
	 * @return
	 */
	public long getLOrderParam() {
		return lOrderParam;
	}

	/**
	 * @return
	 */
	public long getLPageCount() {
		return lPageCount;
	}

	/**
	 * @return
	 */
	public long getTxtPageNo() {
		return txtPageNo;
	}

	/**
	 * @param l
	 */
	public void setLDesc(long l) {
		lDesc = l;
	}

	/**
	 * @param l
	 */
	public void setLOrderParam(long l) {
		lOrderParam = l;
	}

	/**
	 * @param l
	 */
	public void setLPageCount(long l) {
		lPageCount = l;
	}

	/**
	 * @param l
	 */
	public void setTxtPageNo(long l) {
		txtPageNo = l;
	}

	/**
	 * @return
	 */
	public String getBorrowClientName() {
		return borrowClientName;
	}

	/**
	 * @param string
	 */
	public void setBorrowClientName(String string) {
		borrowClientName = string;
	}

	/**
	 * @return
	 */
	public String getConsignClientName() {
		return consignClientName;
	}

	/**
	 * @param string
	 */
	public void setConsignClientName(String string) {
		consignClientName = string;
	}

	/**
	 * @return
	 */
	public long getClientID() {
		return clientID;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		clientID = l;
	}

	/**
	 * @return
	 */
	public double getAmountBegin2() {
		return amountBegin2;
	}

	/**
	 * @return
	 */
	public double getAmountEnd2() {
		return amountEnd2;
	}

	/**
	 * @param d
	 */
	public void setAmountBegin2(double d) {
		amountBegin2 = d;
	}

	/**
	 * @param d
	 */
	public void setAmountEnd2(double d) {
		amountEnd2 = d;
	}

	/**
	 * @return
	 */
	public long getContractID() {
		return contractID;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l) {
		contractID = l;
	}

	/**
	 * @return
	 */
	public long getQueryPurpose() {
		return queryPurpose;
	}

	/**
	 * @param l
	 */
	public void setQueryPurpose(long l) {
		queryPurpose = l;
	}

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
}
