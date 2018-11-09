/*
 * Created on 2004-5-21
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attornmentapply.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttornmentContractInfo extends SECBaseDataEntity
{
	private long id = -1;
	private long attornmentApplyId = -1;			//����ת������ID
	private long contractId = -1;					//��ͬID
	private String contractCode = "";				//��ͬ���
	private long clientId = -1;						//�ͻ�ID
	private String borrowClientName="";				
	private String clientCode = "";					//�ͻ����
	private long loanTypeId = -1;					//��������
	private Timestamp loanStartDate = null;			//������ʼ��
	private Timestamp loanEndDate = null;			//������ֹ��
	private double loanAmount = 0;					//������
	private double lastAttornmentAmount = 0;		//��ת��ծȯ���
	private double attornmentAmount = 0;			//����װ�ý��
	private long statusId = -1;						//״̬
	private double balanceForAttornment = 0;
	
	//Boxu Add ������Ŀ����ϸת�õ��ſ 2008��11��10��
	private long payId = -1;						//�ſID
	private double leftoversattornmentamount = 0;	//δת��ծȯ���(��ת��)
	
	public double getLeftoversattornmentamount() {
		return leftoversattornmentamount;
	}

	public void setLeftoversattornmentamount(double leftoversattornmentamount) {
		this.leftoversattornmentamount = leftoversattornmentamount;
		putUsedField("leftoversattornmentamount", leftoversattornmentamount);
	}

	public long getPayId() {
		return payId;
	}

	public void setPayId(long payId) {
		this.payId = payId;
		putUsedField("payId", payId);
	}

	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", id);
	}

	/**
	 * @return
	 */
	public double getAttornmentAmount()
	{
		return attornmentAmount;
	}

	/**
	 * @return
	 */
	public long getAttornmentApplyId()
	{
		return attornmentApplyId;
	}

	/**
	 * @return
	 */
	public String getClientCode()
	{
		return clientCode;
	}

	/**
	 * @return
	 */
	public long getClientId()
	{
		return clientId;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return contractCode;
	}

	/**
	 * @return
	 */
	public long getContractId()
	{
		return contractId;
	}

	/**
	 * @return
	 */
	public double getLastAttornmentAmount()
	{
		return lastAttornmentAmount;
	}

	/**
	 * @return
	 */
	public double getLoanAmount()
	{
		return loanAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanEndDate()
	{
		return loanEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanStartDate()
	{
		return loanStartDate;
	}

	/**
	 * @return
	 */
	public long getLoanTypeId()
	{
		return loanTypeId;
	}

	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @param d
	 */
	public void setAttornmentAmount(double d)
	{
		attornmentAmount = d;
		putUsedField("attornmentAmount",attornmentAmount);
	}

	/**
	 * @param l
	 */
	public void setAttornmentApplyId(long l)
	{
		attornmentApplyId = l;
		putUsedField("attornmentApplyId",attornmentApplyId);
	}

	/**
	 * @param string
	 */
	public void setClientCode(String string)
	{
		clientCode = string;
		putUsedField("clientCode",clientCode);
	}

	/**
	 * @param l
	 */
	public void setClientId(long l)
	{
		clientId = l;
		putUsedField("clientId",clientId);
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		contractCode = string;
		putUsedField("contractCode",contractCode);
	}

	/**
	 * @param l
	 */
	public void setContractId(long l)
	{
		contractId = l;
		putUsedField("contractId",contractId);
	}

	/**
	 * @param d
	 */
	public void setLastAttornmentAmount(double d)
	{
		lastAttornmentAmount = d;
		putUsedField("lastAttornmentAmount",lastAttornmentAmount);
	}

	/**
	 * @param d
	 */
	public void setLoanAmount(double d)
	{
		loanAmount = d;
		putUsedField("loanAmount",loanAmount);
	}

	/**
	 * @param timestamp
	 */
	public void setLoanEndDate(Timestamp timestamp)
	{
		loanEndDate = timestamp;
		putUsedField("loanEndDate",loanEndDate);
	}

	/**
	 * @param timestamp
	 */
	public void setLoanStartDate(Timestamp timestamp)
	{
		loanStartDate = timestamp;
		putUsedField("loanStartDate",loanStartDate);
	}

	/**
	 * @param l
	 */
	public void setLoanTypeId(long l)
	{
		loanTypeId = l;
		putUsedField("loanTypeId",loanTypeId);
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
		putUsedField("statusId",statusId);
	}

	/**
	 * @return
	 */
	public double getBalanceForAttornment()
	{
		return balanceForAttornment;
	}

	/**
	 * @param d
	 */
	public void setBalanceForAttornment(double d)
	{
		balanceForAttornment = d;
	}

	/**
	 * @return
	 */
	public String getBorrowClientName()
	{
		return borrowClientName;
	}

	/**
	 * @param string
	 */
	public void setBorrowClientName(String string)
	{
		borrowClientName = string;
	}

}
