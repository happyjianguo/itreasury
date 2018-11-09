package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LOANCapitalRatingGeneralInfo implements Serializable {

	private    long      id                    = -1;   
    
    private    long      gageid                = -1;   //����Ʒ��ϢID
    
    private    long      equipmenttype         = -1;   //�豸����
    
    private    String    procesverbalcode      = "";   //�ʲ�������������
    
    private    long      periodofvalidity      = -1;   //������Ч��
    
    private    Timestamp benchmarkday          = null; //������׼��
    
    private    Timestamp maturity              = null;  //����������
    
    private    String    checkinmachine        = "";    //�Ǽǻ���
    
    private    Timestamp checkindate           = null;  //�Ǽ�����
    
    private    long      officeid              = -1;    //���´�
    
    private    long      currencyid            = -1;    //����
    
    private    long      inputuserid           = -1;    //¼����
    
    private    Timestamp inputdate             = null;  //¼������
    
    private    long      status                = -1;    //״̬

	public Timestamp getBenchmarkday() {
		return benchmarkday;
	}

	public void setBenchmarkday(Timestamp benchmarkday) {
		this.benchmarkday = benchmarkday;
	}

	public Timestamp getCheckindate() {
		return checkindate;
	}

	public void setCheckindate(Timestamp checkindate) {
		this.checkindate = checkindate;
	}

	public String getCheckinmachine() {
		return checkinmachine;
	}

	public void setCheckinmachine(String checkinmachine) {
		this.checkinmachine = checkinmachine;
	}

	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}

	public long getEquipmenttype() {
		return equipmenttype;
	}

	public void setEquipmenttype(long equipmenttype) {
		this.equipmenttype = equipmenttype;
	}

	public long getGageid() {
		return gageid;
	}

	public void setGageid(long gageid) {
		this.gageid = gageid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getInputdate() {
		return inputdate;
	}

	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
	}

	public long getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
	}

	public Timestamp getMaturity() {
		return maturity;
	}

	public void setMaturity(Timestamp maturity) {
		this.maturity = maturity;
	}

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}

	public long getPeriodofvalidity() {
		return periodofvalidity;
	}

	public void setPeriodofvalidity(long periodofvalidity) {
		this.periodofvalidity = periodofvalidity;
	}

	public String getProcesverbalcode() {
		return procesverbalcode;
	}

	public void setProcesverbalcode(String procesverbalcode) {
		this.procesverbalcode = procesverbalcode;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
}
