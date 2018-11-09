package com.iss.itreasury.ebank.obpaynotice.dataentity;
import java.io.Serializable;
/**
 * @author yanhuang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ShowGrantLoanInfo  implements Serializable
{

	public String Year = ""; //年
			public String Month = ""; //月
			public String Day = ""; //日
			public String Amount = ""; //本金金额
			public String CurrencyName = ""; //币种
			public String TransNo = ""; //交易号
			public String ChineseAmount = ""; //本金金额（大写）
			public String InputUserName = ""; //录入人
			public String CheckUserName = ""; //复核人

			public String ManagerName = ""; //部门经理
			public String ManagerLeaderName = ""; //主管总经理

			public String Abstract = ""; //摘要
			public String Note = ""; //备注
			public String ContractRate = ""; //合同利率
			public String ChargeRate = ""; //手续费率
			public String LoanType = ""; //贷款种类
			public String LoanUnit = ""; //借款单位
			public String OpenBankName = ""; //开户行名称
			public String AccountNo = ""; //账号
			public String ContractCode = ""; //合同编号
			public String BillCode = ""; //放款单号
			public String ConsignUnit = ""; //委托单位
			public String LoanInterval = ""; //贷款期限
			public String LoanAccountNo = ""; //贷款账号
			/**
			 * Returns the a.
			 * @return String
			 */
			public String getAbstract() {
				return Abstract;
			}

			/**
			 * Returns the accountNo.
			 * @return String
			 */
			public String getAccountNo() {
				return AccountNo;
			}

			/**
			 * Returns the amount.
			 * @return String
			 */
			public String getAmount() {
				return Amount;
			}

			/**
			 * Returns the billCode.
			 * @return String
			 */
			public String getBillCode() {
				return BillCode;
			}

			/**
			 * Returns the chargeRate.
			 * @return String
			 */
			public String getChargeRate() {
				return ChargeRate;
			}

			/**
			 * Returns the checkUserName.
			 * @return String
			 */
			public String getCheckUserName() {
				return CheckUserName;
			}

			/**
			 * Returns the chineseAmount.
			 * @return String
			 */
			public String getChineseAmount() {
				return ChineseAmount;
			}

			/**
			 * Returns the consignUnit.
			 * @return String
			 */
			public String getConsignUnit() {
				return ConsignUnit;
			}

			/**
			 * Returns the contractCode.
			 * @return String
			 */
			public String getContractCode() {
				return ContractCode;
			}

			/**
			 * Returns the contractRate.
			 * @return String
			 */
			public String getContractRate() {
				return ContractRate;
			}

			/**
			 * Returns the currencyName.
			 * @return String
			 */
			public String getCurrencyName() {
				return CurrencyName;
			}

			/**
			 * Returns the day.
			 * @return String
			 */
			public String getDay() {
				return Day;
			}

			/**
			 * Returns the inputUserName.
			 * @return String
			 */
			public String getInputUserName() {
				return InputUserName;
			}

			/**
			 * Returns the loanAccountNo.
			 * @return String
			 */
			public String getLoanAccountNo() {
				return LoanAccountNo;
			}

			/**
			 * Returns the loanInterval.
			 * @return String
			 */
			public String getLoanInterval() {
				return LoanInterval;
			}

			/**
			 * Returns the loanType.
			 * @return String
			 */
			public String getLoanType() {
				return LoanType;
			}

			/**
			 * Returns the loanUnit.
			 * @return String
			 */
			public String getLoanUnit() {
				return LoanUnit;
			}

			/**
			 * Returns the managerLeaderName.
			 * @return String
			 */
			public String getManagerLeaderName() {
				return ManagerLeaderName;
			}

			/**
			 * Returns the managerName.
			 * @return String
			 */
			public String getManagerName() {
				return ManagerName;
			}

			/**
			 * Returns the month.
			 * @return String
			 */
			public String getMonth() {
				return Month;
			}

			/**
			 * Returns the note.
			 * @return String
			 */
			public String getNote() {
				return Note;
			}

			/**
			 * Returns the openBankName.
			 * @return String
			 */
			public String getOpenBankName() {
				return OpenBankName;
			}

			/**
			 * Returns the transNo.
			 * @return String
			 */
			public String getTransNo() {
				return TransNo;
			}

	/**
	 * Returns the year.
	 * @return String
	 */
	public String getYear() {
		return Year;
	}

			/**
			 * Sets the a.
			 * @param a The a to set
			 */
			public void setAbstract(String a) {
				Abstract = a;
			}

			/**
			 * Sets the accountNo.
			 * @param accountNo The accountNo to set
			 */
			public void setAccountNo(String accountNo) {
				AccountNo = accountNo;
			}

			/**
			 * Sets the amount.
			 * @param amount The amount to set
			 */
			public void setAmount(String amount) {
				Amount = amount;
			}

			/**
			 * Sets the billCode.
			 * @param billCode The billCode to set
			 */
			public void setBillCode(String billCode) {
				BillCode = billCode;
			}

			/**
			 * Sets the chargeRate.
			 * @param chargeRate The chargeRate to set
			 */
			public void setChargeRate(String chargeRate) {
				ChargeRate = chargeRate;
			}

			/**
			 * Sets the checkUserName.
			 * @param checkUserName The checkUserName to set
			 */
			public void setCheckUserName(String checkUserName) {
				CheckUserName = checkUserName;
			}

			/**
			 * Sets the chineseAmount.
			 * @param chineseAmount The chineseAmount to set
			 */
			public void setChineseAmount(String chineseAmount) {
				ChineseAmount = chineseAmount;
			}

			/**
			 * Sets the consignUnit.
			 * @param consignUnit The consignUnit to set
			 */
			public void setConsignUnit(String consignUnit) {
				ConsignUnit = consignUnit;
			}

			/**
			 * Sets the contractCode.
			 * @param contractCode The contractCode to set
			 */
			public void setContractCode(String contractCode) {
				ContractCode = contractCode;
			}

			/**
			 * Sets the contractRate.
			 * @param contractRate The contractRate to set
			 */
			public void setContractRate(String contractRate) {
				ContractRate = contractRate;
			}

			/**
			 * Sets the currencyName.
			 * @param currencyName The currencyName to set
			 */
			public void setCurrencyName(String currencyName) {
				CurrencyName = currencyName;
			}

			/**
			 * Sets the day.
			 * @param day The day to set
			 */
			public void setDay(String day) {
				Day = day;
			}

			/**
			 * Sets the inputUserName.
			 * @param inputUserName The inputUserName to set
			 */
			public void setInputUserName(String inputUserName) {
				InputUserName = inputUserName;
			}

			/**
			 * Sets the loanAccountNo.
			 * @param loanAccountNo The loanAccountNo to set
			 */
			public void setLoanAccountNo(String loanAccountNo) {
				LoanAccountNo = loanAccountNo;
			}

			/**
			 * Sets the loanInterval.
			 * @param loanInterval The loanInterval to set
			 */
			public void setLoanInterval(String loanInterval) {
				LoanInterval = loanInterval;
			}

			/**
			 * Sets the loanType.
			 * @param loanType The loanType to set
			 */
			public void setLoanType(String loanType) {
				LoanType = loanType;
			}

			/**
			 * Sets the loanUnit.
			 * @param loanUnit The loanUnit to set
			 */
			public void setLoanUnit(String loanUnit) {
				LoanUnit = loanUnit;
			}

			/**
			 * Sets the managerLeaderName.
			 * @param managerLeaderName The managerLeaderName to set
			 */
			public void setManagerLeaderName(String managerLeaderName) {
				ManagerLeaderName = managerLeaderName;
			}

			/**
			 * Sets the managerName.
			 * @param managerName The managerName to set
			 */
			public void setManagerName(String managerName) {
				ManagerName = managerName;
			}

			/**
			 * Sets the month.
			 * @param month The month to set
			 */
			public void setMonth(String month) {
				Month = month;
			}

			/**
			 * Sets the note.
			 * @param note The note to set
			 */
			public void setNote(String note) {
				Note = note;
			}

			/**
			 * Sets the openBankName.
			 * @param openBankName The openBankName to set
			 */
			public void setOpenBankName(String openBankName) {
				OpenBankName = openBankName;
			}

			/**
			 * Sets the transNo.
			 * @param transNo The transNo to set
			 */
			public void setTransNo(String transNo) {
				TransNo = transNo;
			}

	/**
	 * Sets the year.
	 * @param year The year to set
	 */
	public void setYear(String year) {
		Year = year;
	}

}
