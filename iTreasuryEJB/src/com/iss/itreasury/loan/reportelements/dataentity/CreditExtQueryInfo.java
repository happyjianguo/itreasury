/**
 * @author  xiao qiao
 * @Date		2012-6-19
 */
package com.iss.itreasury.loan.reportelements.dataentity;

import java.sql.Timestamp;


public class CreditExtQueryInfo {
	
	private long   			id;						//主键
	private String 			creditCode;				//授信编号
	private String 			clientId; 				//客户编号
	private String 			sName;					//客户名称
	private long   			creditModel; 			//授信方式。。。 
	private double			creditAmount;			//授信金额
	private Timestamp   	startDate;				//授信开始日期
	private Timestamp   	endDate;				//授信结束日期
	private String 			scheckCode;				//信审复书编号
	private String 			scode;					//客户编号
	private long 			Desc; 					//降序排列
	private long 			order;	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getSName() {
		return sName;
	}
	public void setSName(String name) {
		sName = name;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getScheckCode() {
		return scheckCode;
	}
	public void setScheckCode(String scheckCode) {
		this.scheckCode = scheckCode;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public long getCreditModel() {
		return creditModel;
	}
	public void setCreditModel(long creditModel) {
		this.creditModel = creditModel;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public long getDesc() {
		return Desc;
	}
	public void setDesc(long desc) {
		Desc = desc;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	
}
