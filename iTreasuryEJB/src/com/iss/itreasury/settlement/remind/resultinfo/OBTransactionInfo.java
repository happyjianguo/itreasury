/*
 * Created on 2003-12-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.remind.resultinfo;
import java.sql.Timestamp;
/**
 * @author wlming
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class OBTransactionInfo implements java.io.Serializable {
	private long InstructionID = -1;//指令ID
	private String InstructionNo = "";//指令号
	private long InstructionTypeID = -1;//指令类型ID
	private long InstructionStatusID = -1;//指令状态ID
	private long PayAccountID = -1;//付款方账户ID
	private String PayAccountNo = "";//付款方账户号
	private String PayClientName = "";//付款方名称
	private long ReceiveAccountID = -1;//收款方账户ID
	private String ReceiveAccountNo = "";//收款方账户号
	private String ReceiveClientName = "";//收款方名称
	private long RemitTypeID = -1;//汇款方式ID
	private double Amount = 0.00;//金额
	private Timestamp Execute = null;//执行日
	private String Abstract = "";//摘要
	private long TransactionTypeID = -1;//结算交易类型ID
	/**
	 * @return Returns the abstract.
	 */
	public String getAbstract() {
		return Abstract;
	}
	/**
	 * @param abstract1
	 *            The abstract to set.
	 */
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return Amount;
	}
	/**
	 * @param amount
	 *            The amount to set.
	 */
	public void setAmount(double amount) {
		Amount = amount;
	}
	/**
	 * @return Returns the execute.
	 */
	public Timestamp getExecute() {
		return Execute;
	}
	/**
	 * @param execute
	 *            The execute to set.
	 */
	public void setExecute(Timestamp execute) {
		Execute = execute;
	}
	/**
	 * @return Returns the instructionID.
	 */
	public long getInstructionID() {
		return InstructionID;
	}
	/**
	 * @param instructionID
	 *            The instructionID to set.
	 */
	public void setInstructionID(long instructionID) {
		InstructionID = instructionID;
	}
	/**
	 * @return Returns the instructionNo.
	 */
	public String getInstructionNo() {
		return InstructionNo;
	}
	/**
	 * @param instructionNo
	 *            The instructionNo to set.
	 */
	public void setInstructionNo(String instructionNo) {
		InstructionNo = instructionNo;
	}
	/**
	 * @return Returns the instructionStatusID.
	 */
	public long getInstructionStatusID() {
		return InstructionStatusID;
	}
	/**
	 * @param instructionStatusID
	 *            The instructionStatusID to set.
	 */
	public void setInstructionStatusID(long instructionStatusID) {
		InstructionStatusID = instructionStatusID;
	}
	/**
	 * @return Returns the instructionTypeID.
	 */
	public long getInstructionTypeID() {
		return InstructionTypeID;
	}
	/**
	 * @param instructionTypeID
	 *            The instructionTypeID to set.
	 */
	public void setInstructionTypeID(long instructionTypeID) {
		InstructionTypeID = instructionTypeID;
	}
	/**
	 * @return Returns the payAccountID.
	 */
	public long getPayAccountID() {
		return PayAccountID;
	}
	/**
	 * @param payAccountID
	 *            The payAccountID to set.
	 */
	public void setPayAccountID(long payAccountID) {
		PayAccountID = payAccountID;
	}
	/**
	 * @return Returns the payAccountNo.
	 */
	public String getPayAccountNo() {
		return PayAccountNo;
	}
	/**
	 * @param payAccountNo
	 *            The payAccountNo to set.
	 */
	public void setPayAccountNo(String payAccountNo) {
		PayAccountNo = payAccountNo;
	}
	/**
	 * @return Returns the payClientName.
	 */
	public String getPayClientName() {
		return PayClientName;
	}
	/**
	 * @param payClientName
	 *            The payClientName to set.
	 */
	public void setPayClientName(String payClientName) {
		PayClientName = payClientName;
	}
	/**
	 * @return Returns the recieveAccountID.
	 */
	public long getReceiveAccountID() {
		return ReceiveAccountID;
	}
	/**
	 * @param recieveAccountID
	 *            The recieveAccountID to set.
	 */
	public void setReceiveAccountID(long recieveAccountID) {
		ReceiveAccountID = recieveAccountID;
	}
	/**
	 * @return Returns the recieveAccountNo.
	 */
	public String getReceiveAccountNo() {
		return ReceiveAccountNo;
	}
	/**
	 * @param recieveAccountNo
	 *            The recieveAccountNo to set.
	 */
	public void setReceiveAccountNo(String recieveAccountNo) {
		ReceiveAccountNo = recieveAccountNo;
	}
	/**
	 * @return Returns the recieveClientName.
	 */
	public String getReceiveClientName() {
		return ReceiveClientName;
	}
	/**
	 * @param recieveClientName
	 *            The recieveClientName to set.
	 */
	public void setReceiveClientName(String recieveClientName) {
		ReceiveClientName = recieveClientName;
	}
	/**
	 * @return Returns the remitTypeID.
	 */
	public long getRemitTypeID() {
		return RemitTypeID;
	}
	/**
	 * @param remitTypeID
	 *            The remitTypeID to set.
	 */
	public void setRemitTypeID(long remitTypeID) {
		RemitTypeID = remitTypeID;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		TransactionTypeID = transactionTypeID;
	}
}
