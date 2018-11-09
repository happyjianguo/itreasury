/*
 * Created on 2005-12-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;


/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RepurchaseLoanFormWhereInfo extends ITreasuryBaseDataEntity {
    private String codeOfRepurchaseForm ="";
    private String contractCodeFrom ="";
    private long bankId = -1;
    private long borrowClientId=-1;
    private double amountFrom = 0.0;
    private double amountTo = 0.0;
    private Timestamp repurchaseDateFrom = null;
    private Timestamp repurchaseDateTo = null;
    private long orderBySign = -1;
    private long statusId = -1;
    private String orderByColumnName = "id";
    private long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
    public void setContractCodeFrom(String contractCodeFrom)
    {
    	putUsedField("contractCodeFrom", contractCodeFrom);		
    this.contractCodeFrom=contractCodeFrom;	
    }
    public String getContractCodeFrom()
    {
    	return contractCodeFrom;
    }

    public long getId() {
        // TODO Auto-generated method stub
        return -1;
    }
    public void setId(long id) {
        // TODO Auto-generated method stub
    }

    public double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(double amountFrom) {
        putUsedField("amountFrom", amountFrom);
        this.amountFrom = amountFrom;
    }

    public double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(double amountTo) {
        putUsedField("amountTo", amountTo);
        this.amountTo = amountTo;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        putUsedField("bankId", bankId);
        this.bankId = bankId;
    }

    public String getCodeOfRepurchaseForm() {
        return codeOfRepurchaseForm;
    }

    public void setCodeOfRepurchaseForm(String codeOfRepurchaseForm) {
        putUsedField("codeOfRepurchaseForm", codeOfRepurchaseForm);
        this.codeOfRepurchaseForm = codeOfRepurchaseForm;
    }

    public Timestamp getRepurchaseDateFrom() {
        return repurchaseDateFrom;
    }

    public void setRepurchaseDateFrom(Timestamp repurchaseDateFrom) {
        putUsedField("repurchaseDateFrom", repurchaseDateFrom);
        this.repurchaseDateFrom = repurchaseDateFrom;
    }

    public Timestamp getRepurchaseDateTo() {
        return repurchaseDateTo;
    }

    public void setRepurchaseDateTo(Timestamp repurchaseDateTo) {
        putUsedField("repurchaseDateTo", repurchaseDateTo);
        this.repurchaseDateTo = repurchaseDateTo;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        putUsedField("statusId", statusId);
        this.statusId = statusId;
    }
    public long getOrderBySign() {
        return orderBySign;
    }
    public void setOrderBySign(long orderBySign) {
        putUsedField("orderBySign", orderBySign);
        this.orderBySign = orderBySign;
    }
    public long getBorrowClientId()
    {
    return borrowClientId;	
    }
    
    public void setBorrowClientId(long borrowClientId) {
    	this.borrowClientId=borrowClientId;
        // TODO Auto-generated method stub
    }
    public long getLDesc() {
        return lDesc;
    }
    public void setLDesc(long lDesc) {
        this.lDesc = lDesc;
    }
}