package com.iss.itreasury.settlement.transfinance.dataentity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author afzhu
 *�������޿��㡢���������ѯ��ʾ�����ʵ����
 */
public class TransReturnFinanceNewInfo  implements Serializable{
	private String clientCode;//�ͻ����
	private String clientName;//�ͻ����� 
	private int contractID;//��ͬid
	private String contractCode;//��ͬ���
	private Date startDate;//��ʼʱ��
	private Date endDate;//����ʱ��
	private int subtypeID;//����������id
	private int issue;//��������
	private String sissue;//ǰ̨��ʾ���� ��2,3
	private Date planDate;//��������
	private double repaymentBalance=0.0;//�������
	private double amountfrom=0.0;//�ѻ����ܶ���
	private double rate;//��������
	private double currentPaymentAmount=0.0;//���ڻ�����
	private double principal=0.0;//����
	private double interest=0.0;//��Ϣ
	private int currentAccountID=-1;//�����˻�ID
	private int marginAccountID=-1;//��֤���˻�ID
	private String currentAccount="";//�����˻�
	private String marginAccount="";//��֤���˻�
	private int num;//���
	private String sname;//������
	private Date dtinputdate;//����ʱ��
	private int lxwf=1;//����δ������;
	private int ljcf=0;//�ۼƳٸ�����;
	private long ntypeid;//��������ID
	private long nstatusid;//��ͬ״̬ID
	private long nofficeid;//���´�ID
	private long ncurrencyid;//����ID
	private double mexamineamount;//��׼���
	private String remark="";//��ע
	private int flag=0;//�жϽ������,-1Ϊ������¼����ʧ��,0Ϊ��¼����ɹ�
	private String sysDateS;//������
	private Date sysDateD;//������
	private long transSubAccountID = -1;//�������˻�ID	
	private long oppAccountID = 1;//�Է�(��ȡ���)�˻�ID
	private long oppSubAccountID = 1;//�Է�(��ȡ���)���˻�ID
	private String oppAccountNo = "";//�Է�(��ȡ���)�˻���
	private String oppClientName = "";//�Է�(��ȡ���)�˻���
	long userID = 0;//��ǰ��¼�û�
	private int contractDetailID = 0;//��ͬ��ϸID
	private String transno="";//���׺�
	private long transactionTypeID;//��������
	private double marginMoney=0;//�۳���֤���˻��ܽ��
	private double currentMoney=0;//�۳������˻��ܽ��
	private String performanceDate ="";//ִ����
	private String accountOperation="";//Ĭ��Ϊ�Դ����˻��Ĳ�����ϸ,��ʽΪ:{count:xx,detail:id-xx;id-xx} ����countΪ���������˻�������,detailΪ�����˻�����ϸ����:���ڻ����˻���Ǯ
									   //Ȼ���ڱ�֤���˻���Ǯ,��ô���еĵ�һ��IDΪ�����˻�ID,�����xx�ǿ۵�Ǯ��,�ڶ���IDΪ��֤��ID,�����xxΪ�۵�Ǯ��,�����Դ�����
	//add by yunchang
	//date 2010-08-04
	//function Ϊ�˿����е�����δ�����
	private double allNoPayMount = 0.0d;
	//add by yunchang
	//date 2010-08-06
	//function ��������--����--����֪ͨ��--Ҫ��֤����
	private double recognizanceAmount = 0.0d;
	//��֤�����
	private double surplusRecognizanceAmount = 0.0d;
	//add by yunchang
	//date 2010-08-07
	//function ��������--����--����--����Ӧ�����,
	private double lastPayMount = 0.0d;
	//function ��������--����--����--����Ӧ���ܼ�
	private double lastPayMountAll = 0.0d;
	//����δ������
	//add by yunchang
	//date 2010-08-09
	//function ��������--����--����--��������
	private String issueList = "";
	//add by yunchang
	//date 2010-08-09
	//function ��������--����--����--������𳥸���Ӧ����
	private long issuebenqi = -1L;
	
	//�ڲ��࣬���֧��֪ͨ��������֪ͨ��ʵ��
	public class SubReturnFinance
	{
		private int id;
		private int contractId;
		private String contractNum;
		private Date contractYear;
		private String scontractYear;
		private int num;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getContractId() {
			return contractId;
		}
		public void setContractId(int contractId) {
			this.contractId = contractId;
		}
		public String getContractNum() {
			return contractNum;
		}
		public void setContractNum(String contractNum) {
			this.contractNum = contractNum;
		}
		public Date getContractYear() {
			return contractYear;
		}
		public void setContractYear(Date contractYear) {
			this.contractYear = contractYear;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getScontractYear() {
			return scontractYear;
		}
		public void setScontractYear(String scontractYear) {
			this.scontractYear = scontractYear;
		}
		
	}
	public SubReturnFinance getSubReturnFinance()
	{
		return new SubReturnFinance();
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getContractID() {
		return contractID;
	}
	public void setContractID(int contractID) {
		this.contractID = contractID;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getSubtypeID() {
		return subtypeID;
	}
	public void setSubtypeID(int subtypeID) {
		this.subtypeID = subtypeID;
	}
	public int getIssue() {
		return issue;
	}
	public void setIssue(int issue) {
		this.issue = issue;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public double getRepaymentBalance() {
		return repaymentBalance;
	}
	public void setRepaymentBalance(double repaymentBalance) {
		this.repaymentBalance = repaymentBalance;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getCurrentPaymentAmount() {
		return currentPaymentAmount;
	}
	public void setCurrentPaymentAmount(double currentPaymentAmount) {
		this.currentPaymentAmount = currentPaymentAmount;
	}
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public int getCurrentAccountID() {
		return currentAccountID;
	}
	public void setCurrentAccountID(int currentAccountID) {
		this.currentAccountID = currentAccountID;
	}
	public int getMarginAccountID() {
		return marginAccountID;
	}
	public void setMarginAccountID(int marginAccountID) {
		this.marginAccountID = marginAccountID;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSissue() {
		return sissue;
	}
	public void setSissue(String sissue) {
		this.sissue = sissue;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public Date getDtinputdate() {
		return dtinputdate;
	}
	public void setDtinputdate(Date dtinputdate) {
		this.dtinputdate = dtinputdate;
	}
	public String getCurrentAccount() {
		return currentAccount;
	}
	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}
	public String getMarginAccount() {
		return marginAccount;
	}
	public void setMarginAccount(String marginAccount) {
		this.marginAccount = marginAccount;
	}
	public int getLxwf() {
		return lxwf;
	}
	public void setLxwf(int lxwf) {
		this.lxwf = lxwf;
	}
	public int getLjcf() {
		return ljcf;
	}
	public void setLjcf(int ljcf) {
		this.ljcf = ljcf;
	}
	public long getNtypeid() {
		return ntypeid;
	}
	public void setNtypeid(long ntypeid) {
		this.ntypeid = ntypeid;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}
	public long getNcurrencyid() {
		return ncurrencyid;
	}
	public void setNcurrencyid(long ncurrencyid) {
		this.ncurrencyid = ncurrencyid;
	}
	public double getAmountfrom() {
		return amountfrom;
	}
	public void setAmountfrom(double amountfrom) {
		this.amountfrom = amountfrom;
	}
	public double getMexamineamount() {
		return mexamineamount;
	}
	public void setMexamineamount(double mexamineamount) {
		this.mexamineamount = mexamineamount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getSysDateS() {
		return sysDateS;
	}
	public void setSysDateS(String sysDateS) {
		this.sysDateS = sysDateS;
	}
	public Date getSysDateD() {
		return sysDateD;
	}
	public void setSysDateD(Date sysDateD) {
		this.sysDateD = sysDateD;
	}
	public long getTransSubAccountID() {
		return transSubAccountID;
	}
	public void setTransSubAccountID(long transSubAccountID) {
		this.transSubAccountID = transSubAccountID;
	}
	public long getOppAccountID() {
		return oppAccountID;
	}
	public void setOppAccountID(long oppAccountID) {
		this.oppAccountID = oppAccountID;
	}
	public long getOppSubAccountID() {
		return oppSubAccountID;
	}
	public void setOppSubAccountID(long oppSubAccountID) {
		this.oppSubAccountID = oppSubAccountID;
	}
	public String getOppAccountNo() {
		return oppAccountNo;
	}
	public void setOppAccountNo(String oppAccountNo) {
		this.oppAccountNo = oppAccountNo;
	}
	public String getAccountOperation() {
		return accountOperation;
	}
	public void setAccountOperation(String accountOperation) {
		this.accountOperation = accountOperation;
	}
	public String getOppClientName() {
		return oppClientName;
	}
	public void setOppClientName(String oppClientName) {
		this.oppClientName = oppClientName;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public int getContractDetailID() {
		return contractDetailID;
	}
	public void setContractDetailID(int contractDetailID) {
		this.contractDetailID = contractDetailID;
	}
	public String getTransno() {
		return transno;
	}
	public void setTransno(String transno) {
		this.transno = transno;
	}
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	public void setTransactionTypeID(long transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
	}
	public double getMarginMoney() {
		return marginMoney;
	}
	public void setMarginMoney(double marginMoney) {
		this.marginMoney = marginMoney;
	}
	public double getCurrentMoney() {
		return currentMoney;
	}
	public void setCurrentMoney(double currentMoney) {
		this.currentMoney = currentMoney;
	}
	public String getPerformanceDate() {
		return performanceDate;
	}
	public void setPerformanceDate(String performanceDate) {
		this.performanceDate = performanceDate;
	}
	public double getAllNoPayMount() {
		return allNoPayMount;
	}
	public void setAllNoPayMount(double allNoPayMount) {
		this.allNoPayMount = allNoPayMount;
	}
	public double getSurplusRecognizanceAmount() {
		return surplusRecognizanceAmount;
	}
	public void setSurplusRecognizanceAmount(double surplusRecognizanceAmount) {
		this.surplusRecognizanceAmount = surplusRecognizanceAmount;
	}
	public double getRecognizanceAmount() {
		return recognizanceAmount;
	}
	public void setRecognizanceAmount(double recognizanceAmount) {
		this.recognizanceAmount = recognizanceAmount;
	}
	public double getLastPayMount() {
		return lastPayMount;
	}
	public void setLastPayMount(double lastPayMount) {
		this.lastPayMount = lastPayMount;
	}
	public double getLastPayMountAll() {
		return lastPayMountAll;
	}
	public void setLastPayMountAll(double lastPayMountAll) {
		this.lastPayMountAll = lastPayMountAll;
	}
	public String getIssueList() {
		return issueList;
	}
	public void setIssueList(String issueList) {
		this.issueList = issueList;
	}
	public long getIssuebenqi() {
		return issuebenqi;
	}
	public void setIssuebenqi(long issuebenqi) {
		this.issuebenqi = issuebenqi;
	}


	
}
