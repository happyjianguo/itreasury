/*
 * Created on 2006-7-13
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * @author liuq
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLISSAcntVchInfo implements Serializable
{
	private int LedgerID = 1;
	private int Annual = 2006;
	private String CurrCode = "R";
	private String BranchCode = "000";
	private String UserCode = "isoftstone";
	
	private int TransId = 0;
	private Timestamp VchDate = null;
	private int VchGrp = 0;
	private int VchNo = 0;
	private int Affix = 0;
	private int Rows = 0;
	private String VchMemo = "";
	private String ItemCode = "";
	
	private double Quantity = 0;
	private double DAmount = 0;
	private double CAmount = 0;
	
	private Timestamp IntrDate = null;
	private int NoteType = 0;
	private String Note = "";
	private int MGId = 0;
	
	private String Checker = "";  //yyhe添加复核人 2006.11.02 用于中交项目
     
	/**
	 * @return Returns the affix.
	 */
	public int getAffix() {
		return Affix;
	}
	/**
	 * @param affix The affix to set.
	 */
	public void setAffix(int affix) {
		Affix = affix;
	}
	/**
	 * @return Returns the annual.
	 */
	public int getAnnual() {
		return Annual;
	}
	/**
	 * @param affix The annual to set.
	 */
	public void setAnnaul(int annual) {
		Annual = annual;
	}
	/**
	 * @return Returns the branchCode.
	 */
	public String getBranchCode() {
		return BranchCode;
	}
	/**
	 * @param branchCode The branchCode to set.
	 */
	public void setBranchCode(String branchCode) {
		BranchCode = branchCode;
	}
	/**
	 * @return Returns the cAmount.
	 */
	public double getCAmount() {
		return CAmount;
	}
	/**
	 * @param amount The cAmount to set.
	 */
	public void setCAmount(double amount) {
		CAmount = amount;
	}
	/**
	 * @return Returns the currCode.
	 */
	public String getCurrCode() {
		return CurrCode;
	}
	/**
	 * @param currCode The currCode to set.
	 */
	public void setCurrCode(String currCode) {
		CurrCode = currCode;
	}
	/**
	 * @return Returns the dAmount.
	 */
	public double getDAmount() {
		return DAmount;
	}
	/**
	 * @param amount The dAmount to set.
	 */
	public void setDAmount(double amount) {
		DAmount = amount;
	}
	/**
	 * @return Returns the intrDate.
	 */
	public Timestamp getIntrDate() {
		return IntrDate;
	}
	/**
	 * @param intrDate The intrDate to set.
	 */
	public void setIntrDate(Timestamp intrDate) {
		IntrDate = intrDate;
	}
	/**
	 * @return Returns the itemCode.
	 */
	public String getItemCode() {
		return ItemCode;
	}
	/**
	 * @param itemCode The itemCode to set.
	 */
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}
	/**
	 * @return Returns the ledgerID.
	 */
	public int getLedgerID() {
		return LedgerID;
	}
	/**
	 * @param ledgerID The ledgerID to set.
	 */
	public void setLedgerID(int ledgerID) {
		LedgerID = ledgerID;
	}
	/**
	 * @return Returns the mGId.
	 */
	public int getMGId() {
		return MGId;
	}
	/**
	 * @param id The mGId to set.
	 */
	public void setMGId(int id) {
		MGId = id;
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return Note;
	}
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		Note = note;
	}
	/**
	 * @return Returns the noteType.
	 */
	public int getNoteType() {
		return NoteType;
	}
	/**
	 * @param noteType The noteType to set.
	 */
	public void setNoteType(int noteType) {
		NoteType = noteType;
	}
	/**
	 * @return Returns the quantity.
	 */
	public double getQuantity() {
		return Quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(double quantity) {
		Quantity = quantity;
	}
	/**
	 * @return Returns the rows.
	 */
	public int getRows() {
		return Rows;
	}
	/**
	 * @param rows The rows to set.
	 */
	public void setRows(int rows) {
		Rows = rows;
	}
	/**
	 * @return Returns the transId.
	 */
	public int getTransId() {
		return TransId;
	}
	/**
	 * @param transId The transId to set.
	 */
	public void setTransId(int transId) {
		TransId = transId;
	}
	/**
	 * @return Returns the userCode.
	 */
	public String getUserCode() {
		return UserCode;
	}
	/**
	 * @param userCode The userCode to set.
	 */
	public void setUserCode(String userCode) {
		UserCode = userCode;
	}
	/**
	 * @return Returns the vchDate.
	 */
	public Timestamp getVchDate() {
		return VchDate;
	}
	/**
	 * @param vchDate The vchDate to set.
	 */
	public void setVchDate(Timestamp vchDate) {
		VchDate = vchDate;
	}
	/**
	 * @return Returns the vchGrp.
	 */
	public int getVchGrp() {
		return VchGrp;
	}
	/**
	 * @param vchGrp The vchGrp to set.
	 */
	public void setVchGrp(int vchGrp) {
		VchGrp = vchGrp;
	}
	/**
	 * @return Returns the vchMemo.
	 */
	public String getVchMemo() {
		return VchMemo;
	}
	/**
	 * @param vchMemo The vchMemo to set.
	 */
	public void setVchMemo(String vchMemo) {
		VchMemo = vchMemo;
	}
	/**
	 * @return Returns the vchNo.
	 */
	public int getVchNo() {
		return VchNo;
	}
	/**
	 * @param vchNo The vchNo to set.
	 */
	public void setVchNo(int vchNo) {
		VchNo = vchNo;
	}
	public String getChecker() {
		return Checker;
	}
	public void setChecker(String checker) {
		Checker = checker;
	}
}
