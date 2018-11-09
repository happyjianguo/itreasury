package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LOANNegotiableseCuritiesInfo implements Serializable {

    private     long       id                   = -1;  
    
    private     long       gageid               = -1;       //担保品信息ID
    
    private     String     certificatenumber    = "";       //证券质押登记证明书号
    
    private     String     accountnumber        = "";       //出质人证券账户号
    
    private     String     emissionorgcode      = "";       //发行公司代码
    
    private     String     emissionorgname      = "";       //发行公司名称
    
    private     String     stockproperty        = "";       //股份性质
    
    private     Timestamp  dateofissue          = null;     //签发日期
    
    private     long       nenumbeer            = -1;       //证券数量
    
    private     String     chinesenumeral       = "";       //大写
    
    private     Timestamp  freezedatefrom       = null;     //冻结日期从
    
    private     Timestamp  freezedateto         = null;     //冻结日期到
    
    private     String     checkinorgan         = "";       //登记机关
    
    private     Timestamp  checkindate          = null;     //登记日期
    
    private     long       status               = -1;       //状态
    
    private     long       inputuserid          = -1;       //录入人
    
    private     Timestamp  inputdate            = null;     //录入日期
    
    private     long       officeid             = -1;       //办事处
    
    private     long       currencyid           = -1;       //币种
    
    private     double     hdroitValue          = 0;
    
    private     String     warrantcode   = "";
    
    private     String     warrantname = "";
    
    private     long       warrantType  = -1;
    
    private  String lendPerson = "";                  //出库/出借人
    
    private  Timestamp lendDate = null;               //出库或出借时间
    
    private  Timestamp anticipateDate = null;         //出库或出借预计归还时间
    
    private  String   lendCause = "";                 //出库/出借原因
    
    private  Timestamp realityDate = null;            //实际归还时间

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}


	public String getCertificatenumber() {
		return certificatenumber;
	}

	public void setCertificatenumber(String certificatenumber) {
		this.certificatenumber = certificatenumber;
	}

	public Timestamp getCheckindate() {
		return checkindate;
	}

	public void setCheckindate(Timestamp checkindate) {
		this.checkindate = checkindate;
	}

	public String getCheckinorgan() {
		return checkinorgan;
	}

	public void setCheckinorgan(String checkinorgan) {
		this.checkinorgan = checkinorgan;
	}

	public String getChinesenumeral() {
		return chinesenumeral;
	}

	public void setChinesenumeral(String chinesenumeral) {
		this.chinesenumeral = chinesenumeral;
	}

	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}

	public Timestamp getDateofissue() {
		return dateofissue;
	}

	public void setDateofissue(Timestamp dateofissue) {
		this.dateofissue = dateofissue;
	}

	public String getEmissionorgcode() {
		return emissionorgcode;
	}

	public void setEmissionorgcode(String emissionorgcode) {
		this.emissionorgcode = emissionorgcode;
	}

	public String getEmissionorgname() {
		return emissionorgname;
	}

	public void setEmissionorgname(String emissionorgname) {
		this.emissionorgname = emissionorgname;
	}

	public Timestamp getFreezedatefrom() {
		return freezedatefrom;
	}

	public void setFreezedatefrom(Timestamp freezedatefrom) {
		this.freezedatefrom = freezedatefrom;
	}

	public Timestamp getFreezedateto() {
		return freezedateto;
	}

	public void setFreezedateto(Timestamp freezedateto) {
		this.freezedateto = freezedateto;
	}

	public long getGageid() {
		return gageid;
	}

	public void setGageid(long gageid) {
		this.gageid = gageid;
	}

	public long getWarrantType() {
		return warrantType;
	}

	public void setWarrantType(long warrantType) {
		this.warrantType = warrantType;
	}

	public double getHdroitValue() {
		return hdroitValue;
	}

	public void setHdroitValue(double hdroitValue) {
		this.hdroitValue = hdroitValue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getInputdate() {
		return inputdate;
	}

	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
	}

	public long getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
	}


	public long getNenumbeer() {
		return nenumbeer;
	}

	public void setNenumbeer(long nenumbeer) {
		this.nenumbeer = nenumbeer;
	}

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}


	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getStockproperty() {
		return stockproperty;
	}

	public void setStockproperty(String stockproperty) {
		this.stockproperty = stockproperty;
	}

	public String getWarrantcode() {
		return warrantcode;
	}

	public void setWarrantcode(String warrantcode) {
		this.warrantcode = warrantcode;
	}

	public String getWarrantname() {
		return warrantname;
	}

	public void setWarrantname(String warrantname) {
		this.warrantname = warrantname;
	}

	public Timestamp getAnticipateDate() {
		return anticipateDate;
	}

	public void setAnticipateDate(Timestamp anticipateDate) {
		this.anticipateDate = anticipateDate;
	}

	public String getLendCause() {
		return lendCause;
	}

	public void setLendCause(String lendCause) {
		this.lendCause = lendCause;
	}

	public Timestamp getLendDate() {
		return lendDate;
	}

	public void setLendDate(Timestamp lendDate) {
		this.lendDate = lendDate;
	}

	public String getLendPerson() {
		return lendPerson;
	}

	public void setLendPerson(String lendPerson) {
		this.lendPerson = lendPerson;
	}

	public Timestamp getRealityDate() {
		return realityDate;
	}

	public void setRealityDate(Timestamp realityDate) {
		this.realityDate = realityDate;
	}
}
