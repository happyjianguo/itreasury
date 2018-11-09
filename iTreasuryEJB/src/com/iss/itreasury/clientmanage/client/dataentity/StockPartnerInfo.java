


/* Generated by Together */
package com.iss.itreasury.clientmanage.client.dataentity;


import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
*企业资本构成信息DataEntity，对应于表Client_PartnerOfClient
* */
public class StockPartnerInfo extends ITreasuryBaseDataEntity {
	
	private long lID=-1; //表Client_CorporationInfo id
	private long clientid=-1;//客户id
	
	private long partnerid=-1;//控股单位客户id
	private String partnerNo =""; //控股单位客户编号
	private String stockCharacter="";//控股性质
	private String contributiveAmount="";//出资金额
	private String contributiveCurrency="";//出资币种
	private double stockRate=0.00;//持股比例
	private String stockWay="";//入股方式
	private String clientNo = "";  //客户编号
	private String clientName = ""; //客户名称
	private String stockholderNameCHN = ""; //股东中文名称
	private long isStockholder = -1 ;  //是否控股人
	private String stockholderNameEN = ""; //股东英文名称
	private Timestamp  contributiveDate=null; //资金到位时间
	private String stockholderCensor = ""; //股东资格审查机构
	private String stockholderPassNo = ""; //股东资格批准文号
	private Timestamp  stockholderPassDate=null; //股东资格批准时间
	private String strAbstract = "";  //备注
	private long nCurrency = -1; //币种id
	private long statusID = -1; //状态
	private long stockHoldertype = -1;//内外部单位标示
	
	
	
	
	public Timestamp getContributiveDate() {
		return contributiveDate;
	}
	public void setContributiveDate(Timestamp contributiveDate) {
		this.contributiveDate = contributiveDate;
		putUsedField("contributiveDate", contributiveDate);
	}
	public String getStockholderCensor() {
		return stockholderCensor;
	}
	public void setStockholderCensor(String stockholderCensor) {
		this.stockholderCensor = stockholderCensor;
		putUsedField("stockholderCensor", stockholderCensor);
	}
	public String getStockholderPassNo() {
		return stockholderPassNo;
	}
	public void setStockholderPassNo(String stockholderPassNo) {
		this.stockholderPassNo = stockholderPassNo;
		putUsedField("stockholderPassNo", stockholderPassNo);
	}
	public Timestamp getStockholderPassDate() {
		return stockholderPassDate;
	}
	public void setStockholderPassDate(Timestamp stockholderPassDate) {
		this.stockholderPassDate = stockholderPassDate;
		putUsedField("stockholderPassDate", stockholderPassDate);
	}
	public String getStrAbstract() {
		return strAbstract;
	}
	public void setStrAbstract(String strAbstract) {
		this.strAbstract = strAbstract;
		putUsedField("strAbstract", strAbstract);
	}
	public long getNCurrency() {
		return nCurrency;
	}
	public void setNCurrency(long ncurrency) {
		this.nCurrency = ncurrency;
		putUsedField("ncurrency", ncurrency);
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	public String getStockholderNameEN() {
		return stockholderNameEN;
	}
	public void setStockholderNameEN(String stockholderNameEN) {
		this.stockholderNameEN = stockholderNameEN;
		putUsedField("stockholderNameEN", stockholderNameEN);
	}
	public long getClientid() {
		return clientid;
	}
	public void setClientid(long clientid) {
		this.clientid = clientid;
		putUsedField("clientid", clientid);
	}
	public long getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(long partnerid) {
		this.partnerid = partnerid;
		putUsedField("partnerid", partnerid);
		
	}
	public String getStockCharacter() {
		return stockCharacter;
	}
	public void setStockCharacter(String stockCharacter) {
		this.stockCharacter = stockCharacter;
		putUsedField("stockCharacter", stockCharacter);
	}
	public String getContributiveAmount() {
		return contributiveAmount;
	}
	public void setContributiveAmount(String contributiveAmount) {
		this.contributiveAmount = contributiveAmount;
		putUsedField("contributiveAmount", contributiveAmount);
	}
	public String getContributiveCurrency() {
		return contributiveCurrency;
	}
	public void setContributiveCurrency(String contributiveCurrency) {
		this.contributiveCurrency = contributiveCurrency;
		putUsedField("contributiveCurrency", contributiveCurrency);
	}
	public double getStockRate() {
		return stockRate;
	}
	public void setStockRate(double stockRate) {
		this.stockRate = stockRate;
		putUsedField("stockRate", stockRate);
	}
	public String getStockWay() {
		return stockWay;
	}
	public void setStockWay(String stockWay) {
		this.stockWay = stockWay;
		putUsedField("stockWay", stockWay);
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
		putUsedField("clientNo", clientNo);
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
		putUsedField("clientName", clientName);
	}
	public String getStockholderNameCHN() {
		return stockholderNameCHN;
	}
	public void setStockholderNameCHN(String stockholderNameCHN) {
		this.stockholderNameCHN = stockholderNameCHN;
		putUsedField("stockholderNameCHN", stockholderNameCHN);
	}
	public long getIsStockholder() {
		return isStockholder;
	}
	public void setIsStockholder(long isStockholder) {
		this.isStockholder = isStockholder;
		putUsedField("isStockholder", isStockholder);
	}
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
		putUsedField("partnerNo", partnerNo);
	}
	public long getLID() {
		return lID;
	}
	public void setLID(long lID) {
		this.lID = lID;
		putUsedField("lID", lID);
	}
	public long getStockHoldertype() {
		return stockHoldertype;
	}
	public void setStockHoldertype(long stockHoldertype) {
		this.stockHoldertype = stockHoldertype;
	}   
	
	
	
}
	
