/*
 * Created on 2007-6-29
 */
package com.iss.itreasury.settlement.logger.dataentity;

import java.io.Serializable;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author leiyang
 *
 */
public class ErrorBussinessInfo implements Serializable {
	private long id = -1;
	private String transNo = "";
	private long transTypeId = -1;
	private long statusId = -1;
	private long serialNo = -1;
	private String transTypeName = "";
	private String statusName = "";
	private long quantity = -1;

	
	public long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		setStatusName(SETTConstant.TransactionStatus.getName(statusId));
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public long getTransTypeId() {
		return transTypeId;
	}
	public void setTransTypeId(long transTypeId) {
		this.transTypeId = transTypeId;
		setTransTypeName(SETTConstant.TransactionType.getName(transTypeId));
	}
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
