/*
 * Created on 2004-4-1
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.report.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportDesk implements Serializable 
{
	//��λ
	private String borrowClientName="";
	//��ͬ���
	private String contractCode="";
	//��ͬ���
	private double loanAmount=0;
	//�ſ��
	private String payNoteCode="";
	//�ſ���
	private double payAmount=0;
	//�ſ����
	private double payBalance=0;
	//��ͬ����
	private double contractRate=0;
	//ִ������
	private double interestRate=0;
	//�黹����
	private double repayAmount=0;
	//�黹����ʱ��
	private Timestamp repayTime=null;
	//�黹��Ϣ
	private double repayInterest=0;
	//�黹��Ϣʱ��
	private Timestamp repayInterestTime=null;
	//���ڱ���
	private double overDueAmount=0;
	//Ӧ����Ϣ
	private double solidInterest=0;
	//��������
	private long overDueInterval=-1;
	//������Ϣ
	private double pubishInterest=0;
	/**
	 * @return
	 */
	public String getBorrowClientName() {
		return borrowClientName;
	}

	/**
	 * @return
	 */
	public String getContractCode() {
		return contractCode;
	}

	/**
	 * @return
	 */
	public double getContractRate() {
		return contractRate;
	}

	/**
	 * @return
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * @return
	 */
	public double getLoanAmount() {
		return loanAmount;
	}

	/**
	 * @return
	 */
	public double getOverDueAmount() {
		return overDueAmount;
	}

	/**
	 * @return
	 */
	public long getOverDueInterval() {
		return overDueInterval;
	}

	/**
	 * @return
	 */
	public double getPayAmount() {
		return payAmount;
	}

	/**
	 * @return
	 */
	public double getPayBalance() {
		return payBalance;
	}

	/**
	 * @return
	 */
	public String getPayNoteCode() {
		return payNoteCode;
	}

	/**
	 * @return
	 */
	public double getPubishInterest() {
		return pubishInterest;
	}

	/**
	 * @return
	 */
	public double getRepayAmount() {
		return repayAmount;
	}

	/**
	 * @return
	 */
	public double getRepayInterest() {
		return repayInterest;
	}

	/**
	 * @return
	 */
	public Timestamp getRepayInterestTime() {
		return repayInterestTime;
	}

	/**
	 * @return
	 */
	public Timestamp getRepayTime() {
		return repayTime;
	}

	/**
	 * @return
	 */
	public double getSolidInterest() {
		return solidInterest;
	}

	/**
	 * @param string
	 */
	public void setBorrowClientName(String string) {
		borrowClientName = string;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string) {
		contractCode = string;
	}

	/**
	 * @param d
	 */
	public void setContractRate(double d) {
		contractRate = d;
	}

	/**
	 * @param d
	 */
	public void setInterestRate(double d) {
		interestRate = d;
	}

	/**
	 * @param d
	 */
	public void setLoanAmount(double d) {
		loanAmount = d;
	}

	/**
	 * @param d
	 */
	public void setOverDueAmount(double d) {
		overDueAmount = d;
	}

	/**
	 * @param l
	 */
	public void setOverDueInterval(long l) {
		overDueInterval = l;
	}

	/**
	 * @param d
	 */
	public void setPayAmount(double d) {
		payAmount = d;
	}

	/**
	 * @param d
	 */
	public void setPayBalance(double d) {
		payBalance = d;
	}

	/**
	 * @param string
	 */
	public void setPayNoteCode(String string) {
		payNoteCode = string;
	}

	/**
	 * @param d
	 */
	public void setPubishInterest(double d) {
		pubishInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRepayAmount(double d) {
		repayAmount = d;
	}

	/**
	 * @param d
	 */
	public void setRepayInterest(double d) {
		repayInterest = d;
	}

	/**
	 * @param timestamp
	 */
	public void setRepayInterestTime(Timestamp timestamp) {
		repayInterestTime = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setRepayTime(Timestamp timestamp) {
		repayTime = timestamp;
	}

	/**
	 * @param d
	 */
	public void setSolidInterest(double d) {
		solidInterest = d;
	}

}
