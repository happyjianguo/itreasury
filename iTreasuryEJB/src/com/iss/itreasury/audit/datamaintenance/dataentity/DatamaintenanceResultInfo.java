package com.iss.itreasury.audit.datamaintenance.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DatamaintenanceResultInfo implements Serializable
{
    
    private long indexid = 0;//ָ�����Ʊ�ID
    private long basicid = 0;//�������ݱ�ID
    private String sort = "";//ָ�����
    private String attribute4 = "";//��ţ�������ʾ�ã�
    private String indexname = "";//ָ������
    private String sdefine = "";//ָ�궨��
    private String sfrequency = "";//���ʱ��
    private String basicname = "";//������������
    private String procedure = "";//�洢������
    private double value = 0.0;//�洢ֵ
    private double calValue = 0.0;//����ֵ
    
    private String rptname = "";
	private String rptitemcode = "";
	private String rptitemname = "";
	private double rptdefault;
	private String memo  = "";
	private long rptitemid;
    
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	public long getBasicid() {
		return basicid;
	}
	public void setBasicid(long basicid) {
		this.basicid = basicid;
	}
	public String getBasicname() {
		return basicname;
	}
	public void setBasicname(String basicname) {
		this.basicname = basicname;
	}
	public double getCalValue() {
		return calValue;
	}
	public void setCalValue(double calValue) {
		this.calValue = calValue;
	}
	public long getIndexid() {
		return indexid;
	}
	public void setIndexid(long indexid) {
		this.indexid = indexid;
	}
	public String getIndexname() {
		return indexname;
	}
	public void setIndexname(String indexname) {
		this.indexname = indexname;
	}
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	public String getSdefine() {
		return sdefine;
	}
	public void setSdefine(String sdefine) {
		this.sdefine = sdefine;
	}
	public String getSfrequency() {
		return sfrequency;
	}
	public void setSfrequency(String sfrequency) {
		this.sfrequency = sfrequency;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public double getRptdefault() {
		return rptdefault;
	}
	public void setRptdefault(double rptdefault) {
		this.rptdefault = rptdefault;
	}
	public String getRptitemcode() {
		return rptitemcode;
	}
	public void setRptitemcode(String rptitemcode) {
		this.rptitemcode = rptitemcode;
	}
	public long getRptitemid() {
		return rptitemid;
	}
	public void setRptitemid(long rptitemid) {
		this.rptitemid = rptitemid;
	}
	public String getRptitemname() {
		return rptitemname;
	}
	public void setRptitemname(String rptitemname) {
		this.rptitemname = rptitemname;
	}
	public String getRptname() {
		return rptname;
	}
	public void setRptname(String rptname) {
		this.rptname = rptname;
	}
	
}
