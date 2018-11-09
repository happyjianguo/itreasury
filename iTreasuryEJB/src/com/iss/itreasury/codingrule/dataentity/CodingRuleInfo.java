/* Generated by Together */

package com.iss.itreasury.codingrule.dataentity;

import java.util.Collection;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/* Author:YanLiu */

public class CodingRuleInfo extends ITreasuryBaseDataEntity {
    public CodingRuleInfo() {
    }

    private long id = -1;
    private long officeid = -1;
    private String name = "";
    private long statusID = -1;
    private long serialNo = -1;
    private String ndesc ="";
    private long digit = -1;
    private long codingSectID = -1;
    private String returnFormat ="";
    private Collection codingRuleDetail = null;
    private long codingruleid=-1;
    public long getCodingruleid() {
		return codingruleid;
	}
	public void setCodingruleid(long codingruleid) {
		this.codingruleid = codingruleid;
		putUsedField("codingruleid", codingruleid);
	}
	public Collection getCodingRuleDetail() {
		return codingRuleDetail;
	}
	public void setCodingRuleDetail(Collection codingRuleDetail) {
		this.codingRuleDetail = codingRuleDetail;
	}
	public long getCodingSectID() {
		return codingSectID;
	}
	public void setCodingSectID(long codingSectID) {
		this.codingSectID = codingSectID;
		putUsedField("codingSectID", codingSectID);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);
	}
	public String getReturnFormat() {
		return returnFormat;
	}
	public void setReturnFormat(String returnFormat) {
		this.returnFormat = returnFormat;
		putUsedField("returnFormat", returnFormat);
	}
	public long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
		putUsedField("serialNo", serialNo);
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}

	public long getDigit() {
		return digit;
	}
	public void setDigit(long digit) {
		this.digit = digit;
		putUsedField("digit", digit);
	}
	public String getNdesc() {
		return ndesc;
	}
	public void setNdesc(String ndesc) {
		this.ndesc = ndesc;
		putUsedField("ndesc", ndesc);
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid", officeid);
	}
}