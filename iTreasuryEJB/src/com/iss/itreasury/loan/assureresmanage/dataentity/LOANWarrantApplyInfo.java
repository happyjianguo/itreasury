package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;


public class LOANWarrantApplyInfo implements Serializable {

    private  long       id               = -1;
	
    private  String     applycode        = "";        //���뵥���
    
    private  long       applytype        = -1;        //��������
    
    private  long       gageid           = -1;        //����Ʒ��ϢID(���ǳ��⡢���衢�黹��Ȩ֤��id)
    
    private  long       gagetype         = -1;        //����Ʒ����(���ǳ��⡢���衢�黹��Ȩ֤������)
     
    private  long       officeid         = -1;        //���´�
    
    private  long       currencyid       = -1;        //����
    
    private  long       inputuserid      = -1;        //¼����
    
    private  Timestamp  inputdate        = null;      //¼��ʱ��
    
    private  long       status           = -1;        //״̬
    
    private  long       nextcheckuserid  = -1;        //��һ�������
    
    private  long       nextchecklevel   = -1;        //��һ����˼���
    
    private  long       islowlevel       = -1;        //�������
    
    private  long       loanType         = -1;
    
    private  long       subloanType      = -1;
    
    private  String     applyUserName = "";
    
    private  String lendPerson = "";                  //����/������
    
    private  Timestamp lendDate = null;               //��������ʱ��
    
    private  Timestamp anticipateDate = null;         //��������Ԥ�ƹ黹ʱ��
    
    private  String   lendCause = "";                 //����/����ԭ��
    
    private  Timestamp realityDate = null;            //ʵ�ʹ黹ʱ��
    
    private  LOANGageInforMationInfo loanGageinformationInfo = null;

	public String getApplycode() {
		return applycode;
	}

	public void setApplycode(String applycode) {
		this.applycode = applycode;
	}

	public long getApplytype() {
		return applytype;
	}

	public void setApplytype(long applytype) {
		this.applytype = applytype;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
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

	public long getIslowlevel() {
		return islowlevel;
	}

	public void setIslowlevel(long islowlevel) {
		this.islowlevel = islowlevel;
	}


	public LOANGageInforMationInfo getLoanGageinformationInfo() {
		return loanGageinformationInfo;
	}

	public void setLoanGageinformationInfo(
			LOANGageInforMationInfo loanGageinformationInfo) {
		this.loanGageinformationInfo = loanGageinformationInfo;
	}

	public long getLoanType() {
		return loanType;
	}

	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}

	public long getNextchecklevel() {
		return nextchecklevel;
	}

	public void setNextchecklevel(long nextchecklevel) {
		this.nextchecklevel = nextchecklevel;
	}

	public long getNextcheckuserid() {
		return nextcheckuserid;
	}

	public void setNextcheckuserid(long nextcheckuserid) {
		this.nextcheckuserid = nextcheckuserid;
	}

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public long getSubloanType() {
		return subloanType;
	}

	public void setSubloanType(long subloanType) {
		this.subloanType = subloanType;
	}

	public Timestamp getAnticipateDate() {
		return anticipateDate;
	}

	public void setAnticipateDate(Timestamp anticipateDate) {
		this.anticipateDate = anticipateDate;
	}

	public String getLendCause() {
		return lendCause;
	}

	public void setLendCause(String lendCause) {
		this.lendCause = lendCause;
	}

	public Timestamp getLendDate() {
		return lendDate;
	}

	public void setLendDate(Timestamp lendDate) {
		this.lendDate = lendDate;
	}

	public String getLendPerson() {
		return lendPerson;
	}

	public void setLendPerson(String lendPerson) {
		this.lendPerson = lendPerson;
	}

	public Timestamp getRealityDate() {
		return realityDate;
	}

	public void setRealityDate(Timestamp realityDate) {
		this.realityDate = realityDate;
	}

	public long getGagetype() {
		return gagetype;
	}

	public void setGagetype(long gagetype) {
		this.gagetype = gagetype;
	}
}
