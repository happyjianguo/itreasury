package com.iss.itreasury.settlement.transdiscount.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;

public class TransDiscountSubjectInfo implements Serializable{
	private long ID = -1; //  is PK     
	private long transDiscountID = -1;//转贴现交易号
	private long transTypeID = -1; //	is '交易类型';                          
	private long mdirection = -1; //	is '借贷方向';                  
	private double mamount = 0.0; //	is '金额';
	private long generalLedger = -1 ;// 转贴现类型
	private long generalLedgerOne = -1; //	is '转贴现类型1';           
	private long dirOne = -1; //	is '借贷方向1';                     
	private double amountOne = 0.0; //	is '金额1';                        
	private long generalLedgerTwo = -1; //	is '转贴现类型2';           
	private long dirTwo = -1; //	is '借贷方向2';                     
	private double amountTwo = 0.0; //	is '金额2';                        
	private long generalLedgerThree = -1; //	is '转贴现类型3';         
	private long dirThree = -1; //	is '借贷方向3';                   
	private double amountThree = 0.0; //	is '金额3';      
	private long generalLedgerFour = -1; //	is '转贴现类型4';         
	private long dirFour = -1; //	is '借贷方向4';                   
	private double amountFour = 0.0; //	is '金额4';
	public double getAmountFour() {
		return amountFour;
	}
	public void setAmountFour(double amountFour) {
		this.amountFour = amountFour;
	}
	public double getAmountOne() {
		return amountOne;
	}
	public void setAmountOne(double amountOne) {
		this.amountOne = amountOne;
	}
	public double getAmountThree() {
		return amountThree;
	}
	public void setAmountThree(double amountThree) {
		this.amountThree = amountThree;
	}
	public double getAmountTwo() {
		return amountTwo;
	}
	public void setAmountTwo(double amountTwo) {
		this.amountTwo = amountTwo;
	}
	public long getDirFour() {
		return dirFour;
	}
	public void setDirFour(long dirFour) {
		this.dirFour = dirFour;
	}
	public long getDirOne() {
		return dirOne;
	}
	public void setDirOne(long dirOne) {
		this.dirOne = dirOne;
	}
	public long getDirThree() {
		return dirThree;
	}
	public void setDirThree(long dirThree) {
		this.dirThree = dirThree;
	}
	public long getDirTwo() {
		return dirTwo;
	}
	public void setDirTwo(long dirTwo) {
		this.dirTwo = dirTwo;
	}
	public long getGeneralLedgerFour() {
		return generalLedgerFour;
	}
	public void setGeneralLedgerFour(long generalLedgerFour) {
		this.generalLedgerFour = generalLedgerFour;
	}
	public long getGeneralLedgerOne() {
		return generalLedgerOne;
	}
	public void setGeneralLedgerOne(long generalLedgerOne) {
		this.generalLedgerOne = generalLedgerOne;
	}
	public long getGeneralLedgerThree() {
		return generalLedgerThree;
	}
	public void setGeneralLedgerThree(long generalLedgerThree) {
		this.generalLedgerThree = generalLedgerThree;
	}
	public long getGeneralLedgerTwo() {
		return generalLedgerTwo;
	}
	public void setGeneralLedgerTwo(long generalLedgerTwo) {
		this.generalLedgerTwo = generalLedgerTwo;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public double getMamount() {
		return mamount;
	}
	public void setMamount(double mamount) {
		this.mamount = mamount;
	}
	public long getMdirection() {
		return mdirection;
	}
	public void setMdirection(long mdirection) {
		this.mdirection = mdirection;
	}
	public long getTransDiscountID() {
		return transDiscountID;
	}
	public void setTransDiscountID(long transDiscountID) {
		this.transDiscountID = transDiscountID;
	}
	public long getTransTypeID() {
		return transTypeID;
	}
	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}
	public long getGeneralLedger() {
		return generalLedger;
	}
	public void setGeneralLedger(long generalLedger) {
		this.generalLedger = generalLedger;
	}
}
