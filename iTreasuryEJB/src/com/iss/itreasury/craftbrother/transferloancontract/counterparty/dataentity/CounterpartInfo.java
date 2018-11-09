package com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CounterpartInfo extends ITreasuryBaseDataEntity 
{
	public long id=-1;//Ö÷¼ü
	public long officeid=-1;
	public long currencyid=-1;
	public String counterpartcode="";
	public String counterpartname="";

	public String financecode="";
	public String businesscode="";
	public String linkman="";
	public String tel="";
	public String postcode="";
	public String corporativer="";
	public String address="";
	public String fax="";
	public String email="";
	public long businesstypeid=-1;
	public long statusid=-1;
	public long inputuserid=-1;
	private Timestamp inputdate=null;
	
	private String oldbankinfoid="";
	
	public String getOldbankinfoid() {
		return oldbankinfoid;
	}
	public void setOldbankinfoid(String oldbankinfoid) {
		this.oldbankinfoid = oldbankinfoid;
	}
	public CounterpartBankInfo[] info=null;
	public long getId() {
		return id;
	}
	public CounterpartBankInfo[] getInfo() {
		return info;
	}
	public void setInfo(CounterpartBankInfo[] info) {
		this.info = info;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid",officeid);
	}
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
		putUsedField("currencyid",currencyid);
	}
	public String getCounterpartcode() {
		return counterpartcode;
	}
	public void setCounterpartcode(String counterpartcode) {
		this.counterpartcode = counterpartcode;
		putUsedField("counterpartcode",counterpartcode);
	}
	public String getCounterpartname() {
		return counterpartname;
	}
	public void setCounterpartname(String counterpartname) {
		this.counterpartname = counterpartname;
		putUsedField("counterpartname",counterpartname);
	}
	public String getFinancecode() {
		return financecode;
	}
	public void setFinancecode(String financecode) {
		this.financecode = financecode;
		putUsedField("financecode",financecode);
	}
	public String getBusinesscode() {
		return businesscode;
	}
	public void setBusinesscode(String businesscode) {
		this.businesscode = businesscode;
		putUsedField("businesscode",businesscode);
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
		putUsedField("linkman",linkman);
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
		putUsedField("tel",tel);
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
		putUsedField("postcode",postcode);
	}
	public String getCorporativer() {
		return corporativer;
	}
	public void setCorporativer(String corporativer) {
		this.corporativer = corporativer;
		putUsedField("corporativer",corporativer);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
		putUsedField("address",address);
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
		putUsedField("fax",fax);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
		putUsedField("email",email);
	}
	public long getBusinesstypeid() {
		return businesstypeid;
	}
	public void setBusinesstypeid(long businesstypeid) {
		this.businesstypeid = businesstypeid;
		putUsedField("businesstypeid",businesstypeid);
	}
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid",statusid);
	}
	public long getInputuserid() {
		return inputuserid;
	}
	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid",inputuserid);
	}
	public Timestamp getInputdate() {
		return inputdate;
	}
	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField("inputdate",inputdate);
	}
	
	
	

	

}
