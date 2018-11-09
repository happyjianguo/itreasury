package com.iss.itreasury.report.dateentity;

/**
 * ��Ϣ�����ѯInfo
 * @author yunzhou
 * @date 2011-01-10
 */
public class LoanInterestReportInfo {
	
	private long lId = -1;
	private long lContractId = -1;					//�����ͬID
	private long lLoanAccountId = -1;				//�����˻�ID
	private long lLoanReceivedAccountId = -1;		//������Ϣ�˻�ID
	private long lLoanClientId = -1;				//����ͻ�ID
	private String strContractCode = null;			//��ͬ���
	private String strAccountCode = null;			//�����˻���
	private double dbYSInterest = 0.00;				//Ӧ����Ϣ
	private double dbSSInterest = 0.00;				//ʵ����Ϣ
	private double dbWSInterest = 0.00;				//δ����Ϣ
	private double dbSBQInterest = 0.00;			//�ձ�����Ϣ
	private double dbSQQInterest = 0.00;			//��ǰ����Ϣ
	private double dbBQQXInterest = 0.00;			//����ǷϢ
	private long lLoanSubType = -1;					//����������
	private long lLoanType = -1;					//��������
	private String strDate = "";					//��Ϣ���� 
	private String strInterestDate = "";			//��Ϣ����
	
	public LoanInterestReportInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getDbBQQXInterest() {
		return dbBQQXInterest;
	}
	public void setDbBQQXInterest(double dbBQQXInterest) {
		this.dbBQQXInterest = dbBQQXInterest;
	}
	public double getDbSBQInterest() {
		return dbSBQInterest;
	}
	public void setDbSBQInterest(double dbSBQInterest) {
		this.dbSBQInterest = dbSBQInterest;
	}
	public double getDbSQQInterest() {
		return dbSQQInterest;
	}
	public void setDbSQQInterest(double dbSQQInterest) {
		this.dbSQQInterest = dbSQQInterest;
	}
	public double getDbSSInterest() {
		return dbSSInterest;
	}
	public void setDbSSInterest(double dbSSInterest) {
		this.dbSSInterest = dbSSInterest;
	}
	public double getDbWSInterest() {
		return dbWSInterest;
	}
	public void setDbWSInterest(double dbWSInterest) {
		this.dbWSInterest = dbWSInterest;
	}
	public double getDbYSInterest() {
		return dbYSInterest;
	}
	public void setDbYSInterest(double dbYSInterest) {
		this.dbYSInterest = dbYSInterest;
	}
	public long getLId() {
		return lId;
	}
	public void setLId(long id) {
		lId = id;
	}
	public String getStrAccountCode() {
		return strAccountCode;
	}
	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}
	public String getStrContractCode() {
		return strContractCode;
	}
	public void setStrContractCode(String strContractCode) {
		this.strContractCode = strContractCode;
	}
	public long getLContractId() {
		return lContractId;
	}
	public void setLContractId(long contractId) {
		lContractId = contractId;
	}
	public long getLLoanAccountId() {
		return lLoanAccountId;
	}
	public void setLLoanAccountId(long loanAccountId) {
		lLoanAccountId = loanAccountId;
	}
	public long getLLoanSubType() {
		return lLoanSubType;
	}
	public void setLLoanSubType(long loanSubType) {
		lLoanSubType = loanSubType;
	}
	public long getLLoanType() {
		return lLoanType;
	}
	public void setLLoanType(long loanType) {
		lLoanType = loanType;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	public long getLLoanClientId() {
		return lLoanClientId;
	}
	public void setLLoanClientId(long loanClientId) {
		lLoanClientId = loanClientId;
	}
	public long getLLoanReceivedAccountId() {
		return lLoanReceivedAccountId;
	}
	public void setLLoanReceivedAccountId(long loanReceivedAccountId) {
		lLoanReceivedAccountId = loanReceivedAccountId;
	}
	public String getStrInterestDate() {
		return strInterestDate;
	}
	public void setStrInterestDate(String strInterestDate) {
		this.strInterestDate = strInterestDate;
	}
	
	

}
