/*
 * Created on 2008-11-18
 * 
 */
package com.iss.itreasury.system.user.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author leiyang3
 *
 */
public class LoginUserInfo extends ITreasuryBaseDataEntity
{
	private long id = -1;
	private long officeID = -1;
	private long currencyId = -1;
	private String currencyIdSymbol = "";
	private long clientID = -1;
	private long saId = -1;
	private String username = "";
	private String certSerialNumber = "";
	private String subjectCommonName = "";
	private String clientName = "";
	private String clientCode = "";
	private String simpleName = "";
	private String officeName = "";
	private String officeNo = "";
	private Timestamp lastLoginTime = null;
	private String certcn = "";
	private String certsn = "";
	
	
	public String getCertcn() {
		return certcn;
	}
	public void setCertcn(String certcn) {
		this.certcn = certcn;
	}
	public String getCertsn() {
		return certsn;
	}
	public void setCertsn(String certsn) {
		this.certsn = certsn;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public long getSaId() {
		return saId;
	}
	public void setSaId(long saId) {
		this.saId = saId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCertSerialNumber() {
		return certSerialNumber;
	}
	public void setCertSerialNumber(String certSerialNumber) {
		this.certSerialNumber = certSerialNumber;
	}
	public String getSubjectCommonName() {
		return subjectCommonName;
	}
	public void setSubjectCommonName(String subjectCommonName) {
		this.subjectCommonName = subjectCommonName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getOfficeNo() {
		return officeNo;
	}
	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}
	public String getCurrencyIdSymbol() {
		return currencyIdSymbol;
	}
	public void setCurrencyIdSymbol(String currencyIdSymbol) {
		this.currencyIdSymbol = currencyIdSymbol;
	}
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
