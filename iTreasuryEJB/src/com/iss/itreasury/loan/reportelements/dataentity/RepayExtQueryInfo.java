package com.iss.itreasury.loan.reportelements.dataentity;

import java.util.Date;

/**
 * ���ʻ���֪ͨ����Ҫ��¼����Ϣ�У�������ϸ��ע ҳ���ѯ����
 * 
 * @date 2012��6��14��
 * @author ylwue
 * 
 */
public class RepayExtQueryInfo {
	// ��ѯ����
	/* ��ѯ��ʼ��ͬ��� */
	private String scontractcodeFrist = "";

	/* ��ѯ������ͬ��� */
	private String scontractcodeLast = "";

	/* ������ϸ��ע,1������ϸ,2�����Ѳ�¼ */
	private int statu = -1;

	// ��ѯ���
	/* �ſ�֪ͨ���� */
	private String payCode = "";

	/* ����֪ͨ���� */
	private String repayCode = "";

	/* ������ϸ��ע */
	private String sdetailremarks = "";
	/* ��ͬ��� */
	private String scontractcodeID = "";
	/* �������� */
	private long loanType = -1;
	/* ��λID */
	private long clientID = -1;
	/* ��λ���� */
	private String borrowClientName = "";

	/* �ſ��� */
	private double payMoney = 0;

	/* ������ */
	private double repayMoney = 0;
	/* �������� */
	private Date repayDate;
	/* �Ƿ���ǰ���� */
	private long nisahead = -1;

	/**
	 * @return the scontractcodeFrist
	 */
	public String getScontractcodeFrist() {
		return scontractcodeFrist;
	}

	/**
	 * @param scontractcodeFrist
	 *            the scontractcodeFrist to set
	 */
	public void setScontractcodeFrist(String scontractcodeFrist) {
		this.scontractcodeFrist = scontractcodeFrist;
	}

	/**
	 * @return the scontractcodeLast
	 */
	public String getScontractcodeLast() {
		return scontractcodeLast;
	}

	/**
	 * @param scontractcodeLast
	 *            the scontractcodeLast to set
	 */
	public void setScontractcodeLast(String scontractcodeLast) {
		this.scontractcodeLast = scontractcodeLast;
	}

	/**
	 * @return the statu
	 */
	public int getStatu() {
		return statu;
	}

	/**
	 * @param statu
	 *            the statu to set
	 */
	public void setStatu(int statu) {
		this.statu = statu;
	}

	/**
	 * @return the payCode
	 */
	public String getPayCode() {
		return payCode;
	}

	/**
	 * @param payCode
	 *            the payCode to set
	 */
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	/**
	 * @return the repayCode
	 */
	public String getRepayCode() {
		return repayCode;
	}

	/**
	 * @param repayCode
	 *            the repayCode to set
	 */
	public void setRepayCode(String repayCode) {
		this.repayCode = repayCode;
	}

	/**
	 * @return the sdetailremarks
	 */
	public String getSdetailremarks() {
		return sdetailremarks;
	}

	/**
	 * @param sdetailremarks
	 *            the sdetailremarks to set
	 */
	public void setSdetailremarks(String sdetailremarks) {
		this.sdetailremarks = sdetailremarks;
	}

	/**
	 * @return the scontractcodeID
	 */
	public String getScontractcodeID() {
		return scontractcodeID;
	}

	/**
	 * @param scontractcodeID
	 *            the scontractcodeID to set
	 */
	public void setScontractcodeID(String scontractcodeID) {
		this.scontractcodeID = scontractcodeID;
	}

	/**
	 * @return the loanType
	 */
	public long getLoanType() {
		return loanType;
	}

	/**
	 * @param loanType
	 *            the loanType to set
	 */
	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}

	/**
	 * @return the clientID
	 */
	public long getClientID() {
		return clientID;
	}

	/**
	 * @param clientID
	 *            the clientID to set
	 */
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	/**
	 * @return the payMoney
	 */
	public double getPayMoney() {
		return payMoney;
	}

	/**
	 * @param payMoney
	 *            the payMoney to set
	 */
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	/**
	 * @return the repayMoney
	 */
	public double getRepayMoney() {
		return repayMoney;
	}

	/**
	 * @param repayMoney
	 *            the repayMoney to set
	 */
	public void setRepayMoney(double repayMoney) {
		this.repayMoney = repayMoney;
	}

	/**
	 * @return the repayDate
	 */
	public Date getRepayDate() {
		return repayDate;
	}

	/**
	 * @param repayDate
	 *            the repayDate to set
	 */
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	/**
	 * @return the nisahead
	 */
	public long getNisahead() {
		return nisahead;
	}

	/**
	 * @param nisahead
	 *            the nisahead to set
	 */
	public void setNisahead(long nisahead) {
		this.nisahead = nisahead;
	}

	/**
	 * @return the borrowClientName
	 */
	public String getBorrowClientName() {
		return borrowClientName;
	}

	/**
	 * @param borrowClientName
	 *            the borrowClientName to set
	 */
	public void setBorrowClientName(String borrowClientName) {
		this.borrowClientName = borrowClientName;
	}

}
