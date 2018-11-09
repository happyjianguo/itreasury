/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transloan.dataentity;
import java.io.Serializable;
/**
 * @author xrli
 * 贷款提款的信息
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BankLoanDrawInfo implements Serializable 
{
	private long ContractID = -1;//合同ID
	private String BankName = ""; //银行名称
	private double LoanAmount=0.0; //贷款金额
	private double Rate=0.0;     //利率（承贷比例）
	private double DrawAmount=0.0; //提款金额
	private double Commission=0.0; //手续费（代理费）
	private long BankID = -1;     //银行id
	private long IsHead = -1;     //是否牵头行
    
	

	

	/**
	 * @return
	 */
	public String getBankName() {
		return BankName;
	}

	/**
	 * @return
	 */
	public double getCommission() {
		return Commission;
	}

	/**
	 * @return
	 */
	public long getContractID() {
		return ContractID;
	}

	/**
	 * @return
	 */
	public double getDrawAmount() {
		return DrawAmount;
	}

	/**
	 * @return
	 */
	public double getLoanAmount() {
		return LoanAmount;
	}

	/**
	 * @return
	 */
	public double getRate() {
		return Rate;
	}

	/**
	 * @param string
	 */
	public void setBankName(String string) {
		BankName = string;
	}

	/**
	 * @param d
	 */
	public void setCommission(double d) {
		Commission = d;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l) {
		ContractID = l;
	}

	/**
	 * @param d
	 */
	public void setDrawAmount(double d) {
		DrawAmount = d;
	}

	/**
	 * @param d
	 */
	public void setLoanAmount(double d) {
		LoanAmount = d;
	}

	/**
	 * @param d
	 */
	public void setRate(double d) {
		Rate = d;
	}

	
	/**
	 * @return
	 */
	public long getBankID() {
		return BankID;
	}

	/**
	 * @param l
	 */
	public void setBankID(long l) {
		BankID = l;
	}

	/**
	 * @return
	 */
	public long getIsHead() {
		return IsHead;
	}

	/**
	 * @param l
	 */
	public void setIsHead(long l) {
		IsHead = l;
	}

}
