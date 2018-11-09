/*
 * AdjustInterestConditionInfo.java
 *
 * Created on 2002年3月11日, 上午10:13
 */

package com.iss.itreasury.loan.leasehold.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.Constant;


/**
 *
 * @author  yanliu
 * @version
 */
public class LeaseholdInterestAdjustInfo extends LoanBaseDataEntity implements
        java.io.Serializable {
    private long id = -1;
    private long ncontractID = -1;
    private double mrate = 0.0;
    private String sreason = "";
    private long ninputUserID = -1;
    private Timestamp dtinputDate = null;
    private long nnextCheckUserID = -1;
    private long nnextCheckLevel = -1;
    private long nstatusID = -1;
    private long nofficeID = -1;
    private long ncurrencyID = -1;
    //查询用
    private String clientName = "";
    private long currencyId = -1l; //币种
    private long officeId = -1l; //办事处
    private double balanceAmount = 0.0; //贷款余额
    private long intervalNum = -1l; //期限
    private String contractCode = "";
    private String beforRate = "";
    private Timestamp queryEndDate; //利率日期
    private Timestamp queryStartDate; //利率日期
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC; //排序方式
    private long orderParam = -1l;
    private String orderParamString = "";
    private long queryPurpose = 1l;
    //查询显示用
    private long recordCount = 0l;
    private long pageCount = 0l;
    private long pageLineCount;
    private long pageNo;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    //其他（审核等）
    private long userID = -1;
    private String strUser = "";

    public long getNcontractID() {
        return ncontractID;
    }

    public void setNcontractID(long ncontractID) {
        this.ncontractID = ncontractID;
        putUsedField("NCONTRACTID", this.ncontractID);
    }

    public long getNcurrencyID() {
        return ncurrencyID;
    }

    public void setNcurrencyID(long ncurrencyID) {
        this.ncurrencyID = ncurrencyID;
        putUsedField("NCURRENCYID", this.ncurrencyID);
    }

    public Timestamp getDtinputDate() {
        return dtinputDate;
    }

    public void setDtinputDate(Timestamp dtinputDate) {
        this.dtinputDate = dtinputDate;
        putUsedField("DTINPUTDATE", this.dtinputDate);
    }

    public long getNinputUserID() {
        return ninputUserID;
    }

    public void setNinputUserID(long ninputUserID) {
        this.ninputUserID = ninputUserID;
        putUsedField("NINPUTUSERID", this.ninputUserID);
    }

    public long getNnextCheckLevel() {
        return nnextCheckLevel;
    }

    public void setNnextCheckLevel(long nnextCheckLevel) {
        this.nnextCheckLevel = nnextCheckLevel;
        putUsedField("NNEXTCHECKLEVEL", this.nnextCheckLevel);
    }

    public long getNnextCheckUserID() {
        return nnextCheckUserID;
    }

    public void setNnextCheckUserID(long nnextCheckUserID) {
        this.nnextCheckUserID = nnextCheckUserID;
        putUsedField("NNEXTCHECKUSERID", this.nnextCheckUserID);
    }

    public long getNofficeID() {
        return nofficeID;
    }

    public void setNofficeID(long nofficeID) {
        this.nofficeID = nofficeID;
        putUsedField("NOFFICEID", this.nofficeID);
    }

    public double getMrate() {
        return mrate;
    }

    public void setMrate(double mrate) {
        this.mrate = mrate;
        putUsedField("MRATE", this.mrate);
    }

    public String getSreason() {
        return sreason;
    }

    public void setSreason(String sreason) {
        this.sreason = sreason;
        putUsedField("SREASON", this.sreason);
    }

    public long getNstatusID() {
        return nstatusID;
    }

    public String getBeforRate() {
        return beforRate;
    }

    public String getContractCode() {
        return contractCode;
    }

    public long getDesc() {
        return desc;
    }

    public long getIntervalNum() {
        return intervalNum;
    }

    public long getOrderParam() {
        return orderParam;
    }

    public String getOrderParamString() {
        return orderParamString;
    }

    public long getPageCount() {
        return pageCount;
    }

    public long getPageLineCount() {
        return pageLineCount;
    }

    public long getPageNo() {
        return pageNo;
    }

    public Timestamp getQueryEndDate() {
        return queryEndDate;
    }

    public long getQueryPurpose() {
        return queryPurpose;
    }

    public Timestamp getQueryStartDate() {
        return queryStartDate;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public long getRowNumEnd() {
        return rowNumEnd;
    }

    public long getRowNumStart() {
        return rowNumStart;
    }

    public String getStrUser() {
        return strUser;
    }

    public long getUserID() {
        return userID;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public long getOfficeId() {
        return officeId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setNstatusID(long nstatusID) {
        this.nstatusID = nstatusID;
        putUsedField("NSTATUSID", this.nstatusID);
    }

    public void setBeforRate(String beforRate) {
        this.beforRate = beforRate;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public void setDesc(long desc) {
        this.desc = desc;
    }

    public void setIntervalNum(long intervalNum) {
        this.intervalNum = intervalNum;
    }

    public void setQueryStartDate(Timestamp queryStartDate) {
        this.queryStartDate = queryStartDate;
    }

    public void setQueryPurpose(long queryPurpose) {
        this.queryPurpose = queryPurpose;
    }

    public void setQueryEndDate(Timestamp queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageLineCount(long pageLineCount) {
        this.pageLineCount = pageLineCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public void setOrderParamString(String orderParamString) {
        this.orderParamString = orderParamString;
    }

    public void setOrderParam(long orderParam) {
        this.orderParam = orderParam;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public void setRowNumEnd(long rowNumEnd) {
        this.rowNumEnd = rowNumEnd;
    }

    public void setRowNumStart(long rowNumStart) {
        this.rowNumStart = rowNumStart;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
     */
    public long getId() {
        // TODO Auto-generated method stub
        return id;
    }

    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
     */
    public void setId(long id) {
        // TODO Auto-generated method stub
        this.id = id;
        putUsedField("ID", id);
    }
}
