package com.iss.itreasury.craftbrother.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class QueryFundLendingRequisitionInfo extends SECBaseDataEntity{
					private long 		id = -1;									//
					private long 		deliveryOrderId=-1;							//交割单ID值
					private long 		requisitionId=-1;							//通知单Id
					private long 		secTransactionTypeId=-1;					//交易类型
					private long  		userId=-1;									//
					private String		fundLendingCode="";							//资金拆借通知单编号
					private long 		secClientId=-1;								//业务单位编号ID
					private long 		idElementIniValue=-1l;						//
					private String		code="";									//交割单Name
					private String      deliveryOrderCode="";						//交割单编号
					private String 		secCounterpartName="";						//交易对手名称
					private long		secCounterpartId=-1;						//交易对手编号
					private long 		currencyId=-1;								//币种
					private long		officeId=-1;								//财务公司
					private long		inputUserId=-1;								//录入人
					private Timestamp	inputDate=null;								//录入时间
					private long 		updateUserId=-1;							//修改人
					private Timestamp	updateDate=null;							//修改时间
					private long 		statusId=-1;								//资金拆借通知单状态
					private Timestamp   transactionDateFrom=null;  					//录入起始日期
					private Timestamp  	transactionDateTo=null; 					//录入结束日期
					private double		secPledgeSecuritiesAmountFrom=0;			//拆借金额（始）
					private double		secPledgeSecuritiesAmountTo=0;				//拆借金额（终）
					private double 		pledgesecuritiesamount=0;				//拆借金额
					public long getId() {
						return id;
					}
					public void setId(long l) {
						id = l;
						putUsedField("id", id);
					}
					public long getRequisitionId() {
						return requisitionId;
					}
					public void setRequisitionId(long l) {
						requisitionId = l;
						putUsedField("requisitionId", requisitionId);
					}
					public long getDeliveryOrderId() {
						return deliveryOrderId;
					}
					public void setDeliveryOrderId(long l) {
						deliveryOrderId = l;
						putUsedField("deliveryOrderId", deliveryOrderId);
					}
					public long getSecTransactionTypeId() {
						return secTransactionTypeId;
					}
					public void setSecTransactionTypeId(long l) {
						secTransactionTypeId = l;
						putUsedField("secTransactionTypeId", secTransactionTypeId);
					}
					public long getUserId() {
						return userId;
					}
					public void setUserId(long l) {
						userId = l;
						putUsedField("userId", userId);
					}
					
					public String getDeliveryOrderCode() {
						return deliveryOrderCode;
					}
					public void setDeliveryOrderCode(String s) {
						deliveryOrderCode = s;
						putUsedField("deliveryOrderCode", deliveryOrderCode);
					}
					public String getFundLendingCode() {
						return fundLendingCode;
					}
					public void setFundLendingCode(String s) {
						fundLendingCode = s;
						putUsedField("fundLendingCode", fundLendingCode);
					}
					
					public long getSecClientId() {
						return secClientId;
					}
					public void setSecClientId(long l) {
						secClientId = l;
						putUsedField("secClientId", secClientId);
					}
					public long getIdElementIniValue() {
						return idElementIniValue;
					}
					public void setIdElementIniValue(long l) {
						idElementIniValue = l;
						putUsedField("idElementIniValue", idElementIniValue);
					}
					public double getPledgesecuritiesamount() {
						return pledgesecuritiesamount;
					}
					public void setPledgesecuritiesamount(double d) {
						pledgesecuritiesamount = d;
						putUsedField("pledgesecuritiesamount", pledgesecuritiesamount);
					}
					public String getCode() {
						return code;
					}
					public void setCode(String s) {
						code = s;
						putUsedField("code", code);
					}
					public String getSecCounterpartName() {
						return secCounterpartName;
					}
					public void setSecCounterpartName(String s) {
						secCounterpartName = s;
						putUsedField("secCounterpartName", secCounterpartName);
					}
					public long getSecCounterpartId() {
						return secCounterpartId;
					}
					public void setSecCounterpartId(long l) {
						secCounterpartId = l;
						putUsedField("secCounterpartId", secCounterpartId);
					}
					public long getCurrencyId() {
						return currencyId;
					}
					public void setCurrencyId(long l) {
						currencyId = l;
						putUsedField("currencyId", currencyId);
					}
					public long getOfficeId() {
						return officeId;
					}
					public void setOfficeId(long l) {
						officeId = l;
						putUsedField("officeId", officeId);
					}
					public long getInputUserId() {
						return inputUserId;
					}
					public void setInputUserId(long l) {
						inputUserId = l;
						putUsedField("inputUserId", inputUserId);
					}
					public Timestamp getInputDate() {
						return inputDate;
					}
					public void setInputDate(Timestamp t) {
						inputDate = t;
						putUsedField("inputDate", inputDate);
					}
					public long getUpdateUserId() {
						return updateUserId;
					}
					public void setUpdateUserId(long l) {
						updateUserId = l;
						putUsedField("updateUserId", updateUserId);
					}
					public Timestamp getUpdateDate() {
						return updateDate;
					}
					public void setUpdateDate(Timestamp t) {
						updateDate = t;
						putUsedField("updateDate", updateDate);
					}
					public long getStatusId() {
						return statusId;
					}
					public void setStatusId(long l) {
						statusId = l;
						putUsedField("statusId", statusId);
					}
					public Timestamp getTransactionDateFrom() {
						return transactionDateFrom;
					}
					public void setTransactionDateFrom(Timestamp t) {
						transactionDateFrom = t;
						putUsedField("transactionDateFrom", transactionDateFrom);
					}
					public Timestamp getTransactionDateTo() {
						return transactionDateTo;
					}
					public void setTransactionDateTo(Timestamp t) {
						transactionDateTo = t;
						putUsedField("transactionDateTo", transactionDateTo);
					}
					public double getSecPledgeSecuritiesAmountFrom() {
						return secPledgeSecuritiesAmountFrom;
					}
					public void setSecPledgeSecuritiesAmountFrom(
							double d) {
						secPledgeSecuritiesAmountFrom = d;
						putUsedField("secPledgeSecuritiesAmountFrom", secPledgeSecuritiesAmountFrom);
					}
					public double getSecPledgeSecuritiesAmountTo() {
						return secPledgeSecuritiesAmountTo;
					}
					public void setSecPledgeSecuritiesAmountTo(double d) {
						secPledgeSecuritiesAmountTo = d;
						putUsedField("secPledgeSecuritiesAmountTo", secPledgeSecuritiesAmountTo);
					}
					public double getPledgeSecuritiesAmount() {
						return pledgesecuritiesamount;
					}
					public void setPledgeSecuritiesAmount(double d) {
						pledgesecuritiesamount = d;
						putUsedField("pledgesecuritiesamount", pledgesecuritiesamount);
					}
				
					
}
