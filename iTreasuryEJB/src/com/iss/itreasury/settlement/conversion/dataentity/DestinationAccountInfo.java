/*
 * Created on 2004-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.dataentity;

import java.sql.Timestamp;
import java.sql.Date;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DestinationAccountInfo {
	//银行分支机构代码
	private String bank_code = "";
	//外汇账户账号
	private String account = "";
	//银行分支机构名称
	private String bank_name= "";
	//开户公司单位代码
	private String en_code = "";
	//单位中文名称
	private String Chinese_name="";
	//外汇账户性质码
	private String Account_type="";
	//账户币种
	private String Currence_code="";
	//开户日期
	private Timestamp Open_date =null;
	//开户、变更核准件编号
	private String File_number="";
	//录入标识，1-已录入，0-未录入
	private String Successed = "";
	//录入柜员
	private String Tlrno = "";

	/**
	 * @return Returns the account.
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account The account to set.
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return Returns the account_type.
	 */
	public String getAccount_type() {
		return Account_type;
	}
	/**
	 * @param account_type The account_type to set.
	 */
	public void setAccount_type(String account_type) {
		Account_type = account_type;
	}
	/**
	 * @return Returns the bank_code.
	 */
	public String getBank_code() {
		return bank_code;
	}
	/**
	 * @param bank_code The bank_code to set.
	 */
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	/**
	 * @return Returns the bank_name.
	 */
	public String getBank_name() {
		return bank_name;
	}
	/**
	 * @param bank_name The bank_name to set.
	 */
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	/**
	 * @return Returns the chinese_name.
	 */
	public String getChinese_name() {
		return Chinese_name;
	}
	/**
	 * @param chinese_name The chinese_name to set.
	 */
	public void setChinese_name(String chinese_name) {
		Chinese_name = chinese_name;
	}
	/**
	 * @return Returns the currence_code.
	 */
	public String getCurrence_code() {
		return Currence_code;
	}
	/**
	 * @param currence_code The currence_code to set.
	 */
	public void setCurrence_code(String currence_code) {
		Currence_code = currence_code;
	}
	/**
	 * @return Returns the en_code.
	 */
	public String getEn_code() {
		return en_code;
	}
	/**
	 * @param en_code The en_code to set.
	 */
	public void setEn_code(String en_code) {
		this.en_code = en_code;
	}
	/**
	 * @return Returns the file_number.
	 */
	public String getFile_number() {
		return File_number;
	}
	/**
	 * @param file_number The file_number to set.
	 */
	public void setFile_number(String file_number) {
		File_number = file_number;
	}
	/**
	 * @return Returns the open_date.
	 */
	public Timestamp getOpen_date() {
		return Open_date;
	}
	/**
	 * @param open_date The open_date to set.
	 */
	public void setOpen_date(Timestamp open_date) {
		Open_date = open_date;
	}
	/**
	 * @return Returns the successed.
	 */
	public String getSuccessed() {
		return Successed;
	}
	/**
	 * @param successed The successed to set.
	 */
	public void setSuccessed(String successed) {
		Successed = successed;
	}
	/**
	 * @return Returns the tlrno.
	 */
	public String getTlrno() {
		return Tlrno;
	}
	/**
	 * @param tlrno The tlrno to set.
	 */
	public void setTlrno(String tlrno) {
		Tlrno = tlrno;
	}
}
