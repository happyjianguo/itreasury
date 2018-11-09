package com.iss.itreasury.ebank.obbatchpayment.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class OBBatchPayInfo extends ITreasuryBaseDataEntity{
	
	//private long id = -1;
	private String sPayerAcctNo = null;
	private long lRemitType = -1;
	private String sPayeeName = null;
	private String sPayeeAcctNo = null;
	private String sPayeeProv = null;
	private String sPayeeCity = null;
	private String sPayeeBankName = null;
	private String sPayeeBankCNAPSNO = null;
	private String sPayeeBankOrgNO = null;
	private String sPayeeBankExchangeNO = null;
	private String dAmount = null;
	private long isSameBank = -1;
	private long lRemitArea = -1;
	private long lRemitSpeed = -1;
	private String sNote = null;
	public String getsPayeeBankCNAPSNO()
	{
		return sPayeeBankCNAPSNO;
	}
	public void setsPayeeBankCNAPSNO(String payeeBankCNAPSNO)
	{
		this.sPayeeBankCNAPSNO = payeeBankCNAPSNO;
	}
	
	public String getsPayeeBankOrgNO()
	{
		return sPayeeBankOrgNO;
	}
	public void setsPayeeBankOrgNO(String payeeBankOrgNO)
	{
		this.sPayeeBankOrgNO = payeeBankOrgNO;
	}
	
	public String getsPayeeBankExchangeNO()
	{
		return sPayeeBankExchangeNO;
	}
	
	public void setsPayeeBankExchangeNO(String payeeBankExchangeNO)
	{
		this.sPayeeBankExchangeNO = payeeBankExchangeNO;
	}
	
	public String getsPayerAcctNo() {
		return sPayerAcctNo;
	}
	public void setsPayerAcctNo(String payerAcctNo) {
		sPayerAcctNo = payerAcctNo;
	}
	public long getlRemitType() {
		return lRemitType;
	}
	public void setlRemitType(long remitType) {
		lRemitType = remitType;
	}
	public String getsPayeeName() {
		return sPayeeName;
	}
	public void setsPayeeName(String payeeName) {
		sPayeeName = payeeName;
	}
	public String getsPayeeAcctNo() {
		return sPayeeAcctNo;
	}
	public void setsPayeeAcctNo(String payeeAcctNo) {
		sPayeeAcctNo = payeeAcctNo;
	}
	public String getsPayeeProv() {
		return sPayeeProv;
	}
	public void setsPayeeProv(String payeeProv) {
		sPayeeProv = payeeProv;
	}
	public String getsPayeeCity() {
		return sPayeeCity;
	}
	public void setsPayeeCity(String payeeCity) {
		sPayeeCity = payeeCity;
	}
	public String getdAmount() {
		return dAmount;
	}
	public void setdAmount(String amount) {
		dAmount = amount;
	}
	public long getIsSameBank() {
		return isSameBank;
	}
	public void setIsSameBank(long isSameBank) {
		this.isSameBank = isSameBank;
	}
	public long getlRemitArea() {
		return lRemitArea;
	}
	public void setlRemitArea(long remitArea) {
		lRemitArea = remitArea;
	}
	public long getlRemitSpeed() {
		return lRemitSpeed;
	}
	public void setlRemitSpeed(long remitSpeed) {
		lRemitSpeed = remitSpeed;
	}
	public String getsNote() {
		return sNote;
	}
	public void setsNote(String note) {
		sNote = note;
	}
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	public String getsPayeeBankName() {
		return sPayeeBankName;
	}
	public void setsPayeeBankName(String payeeBankName) {
		sPayeeBankName = payeeBankName;
	}
	
	
	

}
