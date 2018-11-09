package com.iss.itreasury.settlement.transfinance.dataentity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author afzhu
 *融资租赁匡算、批量还款查询显示结果的实体类
 */
public class TransReturnFinanceNewInfo  implements Serializable{
	private String clientCode;//客户编号
	private String clientName;//客户名称 
	private int contractID;//合同id
	private String contractCode;//合同编号
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private int subtypeID;//贷款子类型id
	private int issue;//还款期数
	private String sissue;//前台显示期数 如2,3
	private Date planDate;//还款日期
	private double repaymentBalance=0.0;//还款余额
	private double amountfrom=0.0;//已还款总额数
	private double rate;//还款利率
	private double currentPaymentAmount=0.0;//本期还款金额
	private double principal=0.0;//本金
	private double interest=0.0;//利息
	private int currentAccountID=-1;//活期账户ID
	private int marginAccountID=-1;//保证金账户ID
	private String currentAccount="";//活期账户
	private String marginAccount="";//保证金账户
	private int num;//编号
	private String sname;//生成人
	private Date dtinputdate;//生成时间
	private int lxwf=1;//连续未付次数;
	private int ljcf=0;//累计迟付次数;
	private long ntypeid;//贷款类型ID
	private long nstatusid;//合同状态ID
	private long nofficeid;//办事处ID
	private long ncurrencyid;//币种ID
	private double mexamineamount;//批准金额
	private String remark="";//备注
	private int flag=0;//判断结算情况,-1为该条记录结算失败,0为记录结算成功
	private String sysDateS;//开机日
	private Date sysDateD;//开机日
	private long transSubAccountID = -1;//交易子账户ID	
	private long oppAccountID = 1;//对方(收取租金)账户ID
	private long oppSubAccountID = 1;//对方(收取租金)子账户ID
	private String oppAccountNo = "";//对方(收取租金)账户号
	private String oppClientName = "";//对方(收取租金)账户名
	long userID = 0;//当前登录用户
	private int contractDetailID = 0;//合同明细ID
	private String transno="";//交易号
	private long transactionTypeID;//交易类型
	private double marginMoney=0;//扣除保证金账户总金额
	private double currentMoney=0;//扣除活期账户总金额
	private String performanceDate ="";//执行日
	private String accountOperation="";//默认为对贷方账户的操作明细,格式为:{count:xx,detail:id-xx;id-xx} 其中count为操作几个账户的数量,detail为操作账户的明细比如:先在活期账户扣钱
									   //然后在保证金账户扣钱,那么其中的第一个ID为活期账户ID,后面的xx是扣的钱数,第二个ID为保证金ID,后面的xx为扣的钱数,这样以此类推
	//add by yunchang
	//date 2010-08-04
	//function 为了匡算中的逾期未付租金
	private double allNoPayMount = 0.0d;
	//add by yunchang
	//date 2010-08-06
	//function 融资租赁--结算--还款通知单--要求保证金金额
	private double recognizanceAmount = 0.0d;
	//保证金余额
	private double surplusRecognizanceAmount = 0.0d;
	//add by yunchang
	//date 2010-08-07
	//function 融资租赁--结算--匡算--本期应付租金,
	private double lastPayMount = 0.0d;
	//function 融资租赁--结算--匡算--本期应付总计
	private double lastPayMountAll = 0.0d;
	//逾期未付期数
	//add by yunchang
	//date 2010-08-09
	//function 融资租赁--结算--匡算--逾期期数
	private String issueList = "";
	//add by yunchang
	//date 2010-08-09
	//function 融资租赁--结算--匡算--本期租金偿付对应期数
	private long issuebenqi = -1L;
	
	//内部类，租金支付通知和租金催收通知书实体
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
