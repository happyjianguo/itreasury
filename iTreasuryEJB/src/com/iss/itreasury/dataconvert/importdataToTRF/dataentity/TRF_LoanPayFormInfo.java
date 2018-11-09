/*
 * Created on 2006-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TRF_LoanPayFormInfo {
    //id
    private long id=-1;
    //合同编号
    private String sContractCode="";
    //放款单编号
    private String sCode="";
    //放款日期
    private Timestamp dtOutDate=null;
    //放款金额
    private double mAmount=0.0;
    //委托方委存账户号
    private String sConsignAccount="";
    //放款通知单利率%
    private double payFormRate=0.0;
    //手续费率%
    private double mCommissionRate=0.0;
    //担保费率%
    private double mSuretyfeeRate=0.0;
    //放款起始日期
    private Timestamp dtStart=null;
    //放款结束日期
    private Timestamp dtEnd=null;
    //收款单位名称
    private String sReceiveClientName="";
    //收款单位银行账户
    private String sReceiveAccount="";
    //汇入行
    private String sRemitBank="";
    //状态
    private String status="";
    //发放至活期账户
    private String grantCurrentAccount="";
    //放款方式
    private String grantType="";
    //汇出行
    private String remitOutBank="";
    //汇入地(省)
    private String sRemitInProvince="";
    //汇入地(市)
    private String sRemitInCity="";
    
    
    public Timestamp getDtEnd() {
        return dtEnd;
    }
    public void setDtEnd(Timestamp dtEnd) {
        this.dtEnd = dtEnd;
    }
    public Timestamp getDtOutDate() {
        return dtOutDate;
    }
    public void setDtOutDate(Timestamp dtOutDate) {
        this.dtOutDate = dtOutDate;
    }
    public Timestamp getDtStart() {
        return dtStart;
    }
    public void setDtStart(Timestamp dtStart) {
        this.dtStart = dtStart;
    }
    public String getGrantCurrentAccount() {
        return grantCurrentAccount;
    }
    public void setGrantCurrentAccount(String grantCurrentAccount) {
        this.grantCurrentAccount = grantCurrentAccount;
    }
    public String getGrantType() {
        return grantType;
    }
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
    public double getMAmount() {
        return mAmount;
    }
    public void setMAmount(double amount) {
        mAmount = amount;
    }
    public double getMCommissionRate() {
        return mCommissionRate;
    }
    public void setMCommissionRate(double commissionRate) {
        mCommissionRate = commissionRate;
    }
    public double getMSuretyfeeRate() {
        return mSuretyfeeRate;
    }
    public void setMSuretyfeeRate(double suretyfeeRate) {
        mSuretyfeeRate = suretyfeeRate;
    }
    public double getPayFormRate() {
        return payFormRate;
    }
    public void setPayFormRate(double payFormRate) {
        this.payFormRate = payFormRate;
    }
    public String getRemitOutBank() {
        return remitOutBank;
    }
    public void setRemitOutBank(String remitOutBank) {
        this.remitOutBank = remitOutBank;
    }
    public String getSCode() {
        return sCode;
    }
    public void setSCode(String code) {
        sCode = code;
    }
    public String getSConsignAccount() {
        return sConsignAccount;
    }
    public void setSConsignAccount(String consignAccount) {
        sConsignAccount = consignAccount;
    }
    public String getSContractCode() {
        return sContractCode;
    }
    public void setSContractCode(String contractCode) {
        sContractCode = contractCode;
    }
    public String getSReceiveAccount() {
        return sReceiveAccount;
    }
    public void setSReceiveAccount(String receiveAccount) {
        sReceiveAccount = receiveAccount;
    }
    public String getSReceiveClientName() {
        return sReceiveClientName;
    }
    public void setSReceiveClientName(String receiveClientName) {
        sReceiveClientName = receiveClientName;
    }
    public String getSRemitBank() {
        return sRemitBank;
    }
    public void setSRemitBank(String remitBank) {
        sRemitBank = remitBank;
    }
    public String getSRemitInCity() {
        return sRemitInCity;
    }
    public void setSRemitInCity(String remitInCity) {
        sRemitInCity = remitInCity;
    }
    public String getSRemitInProvince() {
        return sRemitInProvince;
    }
    public void setSRemitInProvince(String remitInProvince) {
        sRemitInProvince = remitInProvince;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}