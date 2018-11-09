package com.iss.itreasury.loan.obinterface.dataentity;

/**
 * @author yanhuang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.io.Serializable;
import java.sql.*;

public class OBPayNoticeInfo implements Serializable{
	
		private long ID = -1 ; //标识
		private String Code = "" ; //代码，放款通知单编号
		private long ContractID = -1 ; //合同ID
		private Timestamp OutDate = null ; //放款日期
		private double Amount = 0.0 ; //金额
		private String ConsignAccount = "" ; //委托方账户号
		private long BankInterestID = -1 ; //利率ID
		private double CommissionRate = 0.0 ; //手续费率
		private double SuretyFeeRate = 0.0 ; //担保费率
		private Timestamp Start = null ; //贷款起始日期
		private Timestamp End = null ; //贷款结束日期
		private String ReceiveClientName = "" ; //收款单位名称
		private String ReceiveAccount = "" ; //收款单位账户
		private String RemitBank = "" ; //汇入行
		private String CompanyLeader = "" ; //公司领导
		private String HandlingPerson = "" ; //经办人
		private String DepartmentLeader = "" ; //部门领导
		private long StatusID = -1 ; //状态
		private long InputUserID = -1 ; //录入用户ID
		private String InputUserName = "";
		private Timestamp InputDate = null ; //录入日期
		private long NextCheckUserID = -1 ; //下一审批人
		private long GrantCurrentAccountID = -1 ; //发放至活期账户
		private long GrantTypeID = -1 ; //放款方式
		private String RemitinProvince = "" ; //汇入地（省）
		private String RemitinCity = "" ; //汇入地（市）
		private String LoanAccount = "" ; //借款单位账户
		private String CheckPerson = "" ; //复核人
		private long AccountBankID = -1 ; //开户银行ID
		private double WTInterestRate = 0.0 ; //贷款利率，委托贷款使用
		private long DrawNoticeID = -1 ; //银团提款通知单标示
		private String strDrawNoticeCode = "" ; //银团提款通知单单号
		//新增
		private Timestamp InDate = null ; //还款日期
		private String LoanClientName = "" ; //借款单位名称
		private String LoanClientCode = "" ; //借款单位编码
		private long LoanClientID = -1 ; //借款单位ID
		private String LoanZipCode = "" ; //借款单位邮编
		private String LoanPhone = "" ; //借款单位电话
		private String LoanAddress = "" ; //借款单位地址
		private String LoanKind = "" ; //放款种类
		private double LoanAmount = 0.0 ; //合同金额
		private String ConsignClientName = "" ; //委托方客户名称
		private long AddOrDecrease = 1 ; //浮动利率正负标识（正1负2）
		private double AdjustRate = -1 ; //浮动利率
		private double ContractRate = 0.0 ; //合同利率
		private long IntervalNum = -1 ; //贷款期限
		private String LoanPurpose = "" ; //原因
		private String ContractCode = "" ; //贷款合同编号
		private double Mbalance = 0.0 ; //贷款余额
		private long Count = -1 ; // 记录数
		private long PageCount = -1 ; // Page Count
		private long AllPage = 0 ; //记录总页数
		private double InterestRate = 0.0 ; //利率
		private String GrantCurrentAccount = "" ; //活期账户号
		private String GrantCurrentName = "" ; //活期账户单位名称
		private long ModuleID = -1 ; //模块ID
		private long LoanTypeID = -1 ; //类型ID
		private long ActionID = -1 ; //操作ID
		private long ApprovalID = -1 ; //lApprovalID
		private long lOfficeID = -1 ; //lApprovalID
		private double Interest = 0.0 ; //当前利息
		private double Balance = 0.0 ; //账户当前余额
		private String AccountBankName = "" ; //开户银行
		private String AccountBankCode = "" ; //银行账户编号
		private String ReceiveClientCode = "" ; //收款单位账号
		private double UnPayAmount = 0;//
		private double OpenAmount = 0;// 
		private double RepayAmount = 0;//
		private double ExamineAmount = 0;
		
		
		/**
		 * Returns the accountBankCode.
		 * @return String
		 */
		public String getAccountBankCode() {
			return AccountBankCode;
		}

		/**
		 * Returns the accountBankID.
		 * @return long
		 */
		public long getAccountBankID() {
			return AccountBankID;
		}

		/**
		 * Returns the accountBankName.
		 * @return String
		 */
		public String getAccountBankName() {
			return AccountBankName;
		}

		/**
		 * Returns the actionID.
		 * @return long
		 */
		public long getActionID() {
			return ActionID;
		}

		/**
		 * Returns the addOrDecrease.
		 * @return long
		 */
		public long getAddOrDecrease() {
			return AddOrDecrease;
		}

		/**
		 * Returns the adjustRate.
		 * @return double
		 */
		public double getAdjustRate() {
			return AdjustRate;
		}

		/**
		 * Returns the allPage.
		 * @return long
		 */
		public long getAllPage() {
			return AllPage;
		}

		/**
		 * Returns the amount.
		 * @return double
		 */
		public double getAmount() {
			return Amount;
		}

		/**
		 * Returns the approvalID.
		 * @return long
		 */
		public long getApprovalID() {
			return ApprovalID;
		}

		/**
		 * Returns the balance.
		 * @return double
		 */
		public double getBalance() {
			return Balance;
		}

		/**
		 * Returns the bankInterestID.
		 * @return long
		 */
		public long getBankInterestID() {
			return BankInterestID;
		}

		/**
		 * Returns the checkPerson.
		 * @return String
		 */
		public String getCheckPerson() {
			return CheckPerson;
		}

		/**
		 * Returns the code.
		 * @return String
		 */
		public String getCode() {
			return Code;
		}

		/**
		 * Returns the commissionRate.
		 * @return double
		 */
		public double getCommissionRate() {
			return CommissionRate;
		}

		/**
		 * Returns the companyLeader.
		 * @return String
		 */
		public String getCompanyLeader() {
			return CompanyLeader;
		}

		/**
		 * Returns the consignAccount.
		 * @return String
		 */
		public String getConsignAccount() {
			return ConsignAccount;
		}

		/**
		 * Returns the consignClientName.
		 * @return String
		 */
		public String getConsignClientName() {
			return ConsignClientName;
		}

		/**
		 * Returns the contractCode.
		 * @return String
		 */
		public String getContractCode() {
			return ContractCode;
		}

		/**
		 * Returns the contractID.
		 * @return long
		 */
		public long getContractID() {
			return ContractID;
		}

		/**
		 * Returns the contractRate.
		 * @return double
		 */
		public double getContractRate() {
			return ContractRate;
		}

		/**
		 * Returns the count.
		 * @return long
		 */
		public long getCount() {
			return Count;
		}

		/**
		 * Returns the departmentLeader.
		 * @return String
		 */
		public String getDepartmentLeader() {
			return DepartmentLeader;
		}

		/**
		 * Returns the drawNoticeID.
		 * @return long
		 */
		public long getDrawNoticeID() {
			return DrawNoticeID;
		}

		/**
		 * Returns the end.
		 * @return Timestamp
		 */
		public Timestamp getEnd() {
			return End;
		}

		/**
		 * Returns the grantCurrentAccount.
		 * @return String
		 */
		public String getGrantCurrentAccount() {
			return GrantCurrentAccount;
		}

		/**
		 * Returns the grantCurrentAccountID.
		 * @return long
		 */
		public long getGrantCurrentAccountID() {
			return GrantCurrentAccountID;
		}

		/**
		 * Returns the grantCurrentName.
		 * @return String
		 */
		public String getGrantCurrentName() {
			return GrantCurrentName;
		}

		/**
		 * Returns the grantTypeID.
		 * @return long
		 */
		public long getGrantTypeID() {
			return GrantTypeID;
		}

		/**
		 * Returns the handlingPerson.
		 * @return String
		 */
		public String getHandlingPerson() {
			return HandlingPerson;
		}

		/**
		 * Returns the iD.
		 * @return long
		 */
		public long getID() {
			return ID;
		}

		/**
		 * Returns the inDate.
		 * @return Timestamp
		 */
		public Timestamp getInDate() {
			return InDate;
		}

		/**
		 * Returns the inputDate.
		 * @return Timestamp
		 */
		public Timestamp getInputDate() {
			return InputDate;
		}

		/**
		 * Returns the inputUserID.
		 * @return long
		 */
		public long getInputUserID() {
			return InputUserID;
		}

		/**
		 * Returns the inputUserName.
		 * @return String
		 */
		public String getInputUserName() {
			return InputUserName;
		}

		/**
		 * Returns the interest.
		 * @return double
		 */
		public double getInterest() {
			return Interest;
		}

		/**
		 * Returns the interestRate.
		 * @return double
		 */
		public double getInterestRate() {
			return InterestRate;
		}

		/**
		 * Returns the intervalNum.
		 * @return long
		 */
		public long getIntervalNum() {
			return IntervalNum;
		}

		/**
		 * Returns the loanAccount.
		 * @return String
		 */
		public String getLoanAccount() {
			return LoanAccount;
		}

		/**
		 * Returns the loanAddress.
		 * @return String
		 */
		public String getLoanAddress() {
			return LoanAddress;
		}

		/**
		 * Returns the loanAmount.
		 * @return double
		 */
		public double getLoanAmount() {
			return LoanAmount;
		}

		/**
		 * Returns the loanClientCode.
		 * @return String
		 */
		public String getLoanClientCode() {
			return LoanClientCode;
		}

		/**
		 * Returns the loanClientID.
		 * @return long
		 */
		public long getLoanClientID() {
			return LoanClientID;
		}

		/**
		 * Returns the loanClientName.
		 * @return String
		 */
		public String getLoanClientName() {
			return LoanClientName;
		}

		/**
		 * Returns the loanKind.
		 * @return String
		 */
		public String getLoanKind() {
			return LoanKind;
		}

		/**
		 * Returns the loanPhone.
		 * @return String
		 */
		public String getLoanPhone() {
			return LoanPhone;
		}

		/**
		 * Returns the loanPurpose.
		 * @return String
		 */
		public String getLoanPurpose() {
			return LoanPurpose;
		}

		/**
		 * Returns the loanTypeID.
		 * @return long
		 */
		public long getLoanTypeID() {
			return LoanTypeID;
		}

		/**
		 * Returns the loanZipCode.
		 * @return String
		 */
		public String getLoanZipCode() {
			return LoanZipCode;
		}

		/**
		 * Returns the lOfficeID.
		 * @return long
		 */
		public long getLOfficeID() {
			return lOfficeID;
		}

		/**
		 * Returns the mbalance.
		 * @return double
		 */
		public double getMbalance() {
			return Mbalance;
		}

		/**
		 * Returns the moduleID.
		 * @return long
		 */
		public long getModuleID() {
			return ModuleID;
		}

		/**
		 * Returns the nextCheckUserID.
		 * @return long
		 */
		public long getNextCheckUserID() {
			return NextCheckUserID;
		}

		/**
		 * Returns the outDate.
		 * @return Timestamp
		 */
		public Timestamp getOutDate() {
			return OutDate;
		}

		/**
		 * Returns the pageCount.
		 * @return long
		 */
		public long getPageCount() {
			return PageCount;
		}

		/**
		 * Returns the receiveAccount.
		 * @return String
		 */
		public String getReceiveAccount() {
			return ReceiveAccount;
		}

		/**
		 * Returns the receiveClientCode.
		 * @return String
		 */
		public String getReceiveClientCode() {
			return ReceiveClientCode;
		}

		/**
		 * Returns the receiveClientName.
		 * @return String
		 */
		public String getReceiveClientName() {
			return ReceiveClientName;
		}

		/**
		 * Returns the remitBank.
		 * @return String
		 */
		public String getRemitBank() {
			return RemitBank;
		}

		/**
		 * Returns the remitinCity.
		 * @return String
		 */
		public String getRemitinCity() {
			return RemitinCity;
		}

		/**
		 * Returns the remitinProvince.
		 * @return String
		 */
		public String getRemitinProvince() {
			return RemitinProvince;
		}

		/**
		 * Returns the start.
		 * @return Timestamp
		 */
		public Timestamp getStart() {
			return Start;
		}

		/**
		 * Returns the statusID.
		 * @return long
		 */
		public long getStatusID() {
			return StatusID;
		}

		/**
		 * Returns the strDrawNoticeCode.
		 * @return String
		 */
		public String getStrDrawNoticeCode() {
			return strDrawNoticeCode;
		}

		/**
		 * Returns the suretyFeeRate.
		 * @return double
		 */
		public double getSuretyFeeRate() {
			return SuretyFeeRate;
		}

		/**
		 * Returns the wTInterestRate.
		 * @return double
		 */
		public double getWTInterestRate() {
			return WTInterestRate;
		}

		/**
		 * Sets the accountBankCode.
		 * @param accountBankCode The accountBankCode to set
		 */
		public void setAccountBankCode(String accountBankCode) {
			AccountBankCode = accountBankCode;
		}

		/**
		 * Sets the accountBankID.
		 * @param accountBankID The accountBankID to set
		 */
		public void setAccountBankID(long accountBankID) {
			AccountBankID = accountBankID;
		}

		/**
		 * Sets the accountBankName.
		 * @param accountBankName The accountBankName to set
		 */
		public void setAccountBankName(String accountBankName) {
			AccountBankName = accountBankName;
		}

		/**
		 * Sets the actionID.
		 * @param actionID The actionID to set
		 */
		public void setActionID(long actionID) {
			ActionID = actionID;
		}

		/**
		 * Sets the addOrDecrease.
		 * @param addOrDecrease The addOrDecrease to set
		 */
		public void setAddOrDecrease(long addOrDecrease) {
			AddOrDecrease = addOrDecrease;
		}

		/**
		 * Sets the adjustRate.
		 * @param adjustRate The adjustRate to set
		 */
		public void setAdjustRate(double adjustRate) {
			AdjustRate = adjustRate;
		}

		/**
		 * Sets the allPage.
		 * @param allPage The allPage to set
		 */
		public void setAllPage(long allPage) {
			AllPage = allPage;
		}

		/**
		 * Sets the amount.
		 * @param amount The amount to set
		 */
		public void setAmount(double amount) {
			Amount = amount;
		}

		/**
		 * Sets the approvalID.
		 * @param approvalID The approvalID to set
		 */
		public void setApprovalID(long approvalID) {
			ApprovalID = approvalID;
		}

		/**
		 * Sets the balance.
		 * @param balance The balance to set
		 */
		public void setBalance(double balance) {
			Balance = balance;
		}

		/**
		 * Sets the bankInterestID.
		 * @param bankInterestID The bankInterestID to set
		 */
		public void setBankInterestID(long bankInterestID) {
			BankInterestID = bankInterestID;
		}

		/**
		 * Sets the checkPerson.
		 * @param checkPerson The checkPerson to set
		 */
		public void setCheckPerson(String checkPerson) {
			CheckPerson = checkPerson;
		}

		/**
		 * Sets the code.
		 * @param code The code to set
		 */
		public void setCode(String code) {
			Code = code;
		}

		/**
		 * Sets the commissionRate.
		 * @param commissionRate The commissionRate to set
		 */
		public void setCommissionRate(double commissionRate) {
			CommissionRate = commissionRate;
		}

		/**
		 * Sets the companyLeader.
		 * @param companyLeader The companyLeader to set
		 */
		public void setCompanyLeader(String companyLeader) {
			CompanyLeader = companyLeader;
		}

		/**
		 * Sets the consignAccount.
		 * @param consignAccount The consignAccount to set
		 */
		public void setConsignAccount(String consignAccount) {
			ConsignAccount = consignAccount;
		}

		/**
		 * Sets the consignClientName.
		 * @param consignClientName The consignClientName to set
		 */
		public void setConsignClientName(String consignClientName) {
			ConsignClientName = consignClientName;
		}

		/**
		 * Sets the contractCode.
		 * @param contractCode The contractCode to set
		 */
		public void setContractCode(String contractCode) {
			ContractCode = contractCode;
		}

		/**
		 * Sets the contractID.
		 * @param contractID The contractID to set
		 */
		public void setContractID(long contractID) {
			ContractID = contractID;
		}

		/**
		 * Sets the contractRate.
		 * @param contractRate The contractRate to set
		 */
		public void setContractRate(double contractRate) {
			ContractRate = contractRate;
		}

		/**
		 * Sets the count.
		 * @param count The count to set
		 */
		public void setCount(long count) {
			Count = count;
		}

		/**
		 * Sets the departmentLeader.
		 * @param departmentLeader The departmentLeader to set
		 */
		public void setDepartmentLeader(String departmentLeader) {
			DepartmentLeader = departmentLeader;
		}

		/**
		 * Sets the drawNoticeID.
		 * @param drawNoticeID The drawNoticeID to set
		 */
		public void setDrawNoticeID(long drawNoticeID) {
			DrawNoticeID = drawNoticeID;
		}

		/**
		 * Sets the end.
		 * @param end The end to set
		 */
		public void setEnd(Timestamp end) {
			End = end;
		}

		/**
		 * Sets the grantCurrentAccount.
		 * @param grantCurrentAccount The grantCurrentAccount to set
		 */
		public void setGrantCurrentAccount(String grantCurrentAccount) {
			GrantCurrentAccount = grantCurrentAccount;
		}

		/**
		 * Sets the grantCurrentAccountID.
		 * @param grantCurrentAccountID The grantCurrentAccountID to set
		 */
		public void setGrantCurrentAccountID(long grantCurrentAccountID) {
			GrantCurrentAccountID = grantCurrentAccountID;
		}

		/**
		 * Sets the grantCurrentName.
		 * @param grantCurrentName The grantCurrentName to set
		 */
		public void setGrantCurrentName(String grantCurrentName) {
			GrantCurrentName = grantCurrentName;
		}

		/**
		 * Sets the grantTypeID.
		 * @param grantTypeID The grantTypeID to set
		 */
		public void setGrantTypeID(long grantTypeID) {
			GrantTypeID = grantTypeID;
		}

		/**
		 * Sets the handlingPerson.
		 * @param handlingPerson The handlingPerson to set
		 */
		public void setHandlingPerson(String handlingPerson) {
			HandlingPerson = handlingPerson;
		}

		/**
		 * Sets the iD.
		 * @param iD The iD to set
		 */
		public void setID(long iD) {
			ID = iD;
		}

		/**
		 * Sets the inDate.
		 * @param inDate The inDate to set
		 */
		public void setInDate(Timestamp inDate) {
			InDate = inDate;
		}

		/**
		 * Sets the inputDate.
		 * @param inputDate The inputDate to set
		 */
		public void setInputDate(Timestamp inputDate) {
			InputDate = inputDate;
		}

		/**
		 * Sets the inputUserID.
		 * @param inputUserID The inputUserID to set
		 */
		public void setInputUserID(long inputUserID) {
			InputUserID = inputUserID;
		}

		/**
		 * Sets the inputUserName.
		 * @param inputUserName The inputUserName to set
		 */
		public void setInputUserName(String inputUserName) {
			InputUserName = inputUserName;
		}

		/**
		 * Sets the interest.
		 * @param interest The interest to set
		 */
		public void setInterest(double interest) {
			Interest = interest;
		}

		/**
		 * Sets the interestRate.
		 * @param interestRate The interestRate to set
		 */
		public void setInterestRate(double interestRate) {
			InterestRate = interestRate;
		}

		/**
		 * Sets the intervalNum.
		 * @param intervalNum The intervalNum to set
		 */
		public void setIntervalNum(long intervalNum) {
			IntervalNum = intervalNum;
		}

		/**
		 * Sets the loanAccount.
		 * @param loanAccount The loanAccount to set
		 */
		public void setLoanAccount(String loanAccount) {
			LoanAccount = loanAccount;
		}

		/**
		 * Sets the loanAddress.
		 * @param loanAddress The loanAddress to set
		 */
		public void setLoanAddress(String loanAddress) {
			LoanAddress = loanAddress;
		}

		/**
		 * Sets the loanAmount.
		 * @param loanAmount The loanAmount to set
		 */
		public void setLoanAmount(double loanAmount) {
			LoanAmount = loanAmount;
		}

		/**
		 * Sets the loanClientCode.
		 * @param loanClientCode The loanClientCode to set
		 */
		public void setLoanClientCode(String loanClientCode) {
			LoanClientCode = loanClientCode;
		}

		/**
		 * Sets the loanClientID.
		 * @param loanClientID The loanClientID to set
		 */
		public void setLoanClientID(long loanClientID) {
			LoanClientID = loanClientID;
		}

		/**
		 * Sets the loanClientName.
		 * @param loanClientName The loanClientName to set
		 */
		public void setLoanClientName(String loanClientName) {
			LoanClientName = loanClientName;
		}

		/**
		 * Sets the loanKind.
		 * @param loanKind The loanKind to set
		 */
		public void setLoanKind(String loanKind) {
			LoanKind = loanKind;
		}

		/**
		 * Sets the loanPhone.
		 * @param loanPhone The loanPhone to set
		 */
		public void setLoanPhone(String loanPhone) {
			LoanPhone = loanPhone;
		}

		/**
		 * Sets the loanPurpose.
		 * @param loanPurpose The loanPurpose to set
		 */
		public void setLoanPurpose(String loanPurpose) {
			LoanPurpose = loanPurpose;
		}

		/**
		 * Sets the loanTypeID.
		 * @param loanTypeID The loanTypeID to set
		 */
		public void setLoanTypeID(long loanTypeID) {
			LoanTypeID = loanTypeID;
		}

		/**
		 * Sets the loanZipCode.
		 * @param loanZipCode The loanZipCode to set
		 */
		public void setLoanZipCode(String loanZipCode) {
			LoanZipCode = loanZipCode;
		}

		/**
		 * Sets the lOfficeID.
		 * @param lOfficeID The lOfficeID to set
		 */
		public void setLOfficeID(long lOfficeID) {
			this.lOfficeID = lOfficeID;
		}

		/**
		 * Sets the mbalance.
		 * @param mbalance The mbalance to set
		 */
		public void setMbalance(double mbalance) {
			Mbalance = mbalance;
		}

		/**
		 * Sets the moduleID.
		 * @param moduleID The moduleID to set
		 */
		public void setModuleID(long moduleID) {
			ModuleID = moduleID;
		}

		/**
		 * Sets the nextCheckUserID.
		 * @param nextCheckUserID The nextCheckUserID to set
		 */
		public void setNextCheckUserID(long nextCheckUserID) {
			NextCheckUserID = nextCheckUserID;
		}

		/**
		 * Sets the outDate.
		 * @param outDate The outDate to set
		 */
		public void setOutDate(Timestamp outDate) {
			OutDate = outDate;
		}

		/**
		 * Sets the pageCount.
		 * @param pageCount The pageCount to set
		 */
		public void setPageCount(long pageCount) {
			PageCount = pageCount;
		}

		/**
		 * Sets the receiveAccount.
		 * @param receiveAccount The receiveAccount to set
		 */
		public void setReceiveAccount(String receiveAccount) {
			ReceiveAccount = receiveAccount;
		}

		/**
		 * Sets the receiveClientCode.
		 * @param receiveClientCode The receiveClientCode to set
		 */
		public void setReceiveClientCode(String receiveClientCode) {
			ReceiveClientCode = receiveClientCode;
		}

		/**
		 * Sets the receiveClientName.
		 * @param receiveClientName The receiveClientName to set
		 */
		public void setReceiveClientName(String receiveClientName) {
			ReceiveClientName = receiveClientName;
		}

		/**
		 * Sets the remitBank.
		 * @param remitBank The remitBank to set
		 */
		public void setRemitBank(String remitBank) {
			RemitBank = remitBank;
		}

		/**
		 * Sets the remitinCity.
		 * @param remitinCity The remitinCity to set
		 */
		public void setRemitinCity(String remitinCity) {
			RemitinCity = remitinCity;
		}

		/**
		 * Sets the remitinProvince.
		 * @param remitinProvince The remitinProvince to set
		 */
		public void setRemitinProvince(String remitinProvince) {
			RemitinProvince = remitinProvince;
		}

		/**
		 * Sets the start.
		 * @param start The start to set
		 */
		public void setStart(Timestamp start) {
			Start = start;
		}

		/**
		 * Sets the statusID.
		 * @param statusID The statusID to set
		 */
		public void setStatusID(long statusID) {
			StatusID = statusID;
		}

		/**
		 * Sets the strDrawNoticeCode.
		 * @param strDrawNoticeCode The strDrawNoticeCode to set
		 */
		public void setStrDrawNoticeCode(String strDrawNoticeCode) {
			this.strDrawNoticeCode = strDrawNoticeCode;
		}

		/**
		 * Sets the suretyFeeRate.
		 * @param suretyFeeRate The suretyFeeRate to set
		 */
		public void setSuretyFeeRate(double suretyFeeRate) {
			SuretyFeeRate = suretyFeeRate;
		}

		/**
		 * Sets the wTInterestRate.
		 * @param wTInterestRate The wTInterestRate to set
		 */
		public void setWTInterestRate(double wTInterestRate) {
			WTInterestRate = wTInterestRate;
		}

		/**
		 * Returns the openAmount.
		 * @return double
		 */
		public double getOpenAmount() {
			return OpenAmount;
		}

		/**
		 * Returns the unPayAmount.
		 * @return double
		 */
		public double getUnPayAmount() {
			return UnPayAmount;
		}

		/**
		 * Sets the openAmount.
		 * @param openAmount The openAmount to set
		 */
		public void setOpenAmount(double openAmount) {
			OpenAmount = openAmount;
		}

		/**
		 * Sets the unPayAmount.
		 * @param unPayAmount The unPayAmount to set
		 */
		public void setUnPayAmount(double unPayAmount) {
			UnPayAmount = unPayAmount;
		}

		/**
		 * Returns the replayAmount.
		 * @return double
		 */
		public double getRepayAmount() {
			return RepayAmount;
		}

		/**
		 * Sets the replayAmount.
		 * @param replayAmount The replayAmount to set
		 */
		public void setRepayAmount(double repayAmount) {
			RepayAmount = repayAmount;
		}



		/**
		 * Returns the examineAmount.
		 * @return double
		 */
		public double getExamineAmount() {
			return ExamineAmount;
		}

		/**
		 * Sets the examineAmount.
		 * @param examineAmount The examineAmount to set
		 */
		public void setExamineAmount(double examineAmount) {
			ExamineAmount = examineAmount;
		}

}
