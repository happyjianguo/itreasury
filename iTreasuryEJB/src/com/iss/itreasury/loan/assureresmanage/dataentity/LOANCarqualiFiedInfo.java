package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LOANCarqualiFiedInfo implements Serializable {

private   long        id                = -1;     
	
    private   long        gageid            = -1;       //����Ʒ��ϢID
    
    private   String      qualifiednumber   = "";       //�ϸ�֤��
    
    private   String      vincode           = "";       //VIN��
    
    private   String      enginenumber      = "";       //��������
    
    private   String      carmodel          = "";       //����
    
    private   String      color             = "";       //��ɫ
    
    private   double      saleprice         = 0;        //���ۼ۸�
    
    private   Timestamp   dateofissue       = null;     //ǩ������
    
    private   long        stockpilestatus   = -1;       //���״̬
    
    private   Timestamp   ckdate            = null;     //��������
    
    private   String      comments          = "";       //��ע
    
    private   long        officeid          = -1;       //���´�
    
    private   long        currencyid        = -1;       //����
    
    private   long        inputuserid       = -1;       //¼����
    
    private   Timestamp   inputdate         = null;     //¼������
    
    private   long        status            = -1;       //״̬
    
    private   String      warrantCode       = "";
    
    private   String      warrantName       = "";
    
    private   long        warrantType       = -1; 
    
    private   String      strCarID          = "";
    
    private  String lendPerson = "";                  //����/������
    
    private  Timestamp lendDate = null;               //��������ʱ��
    
    private  Timestamp anticipateDate = null;         //��������Ԥ�ƹ黹ʱ��
    
    private  String   lendCause = "";                 //����/����ԭ��
    
    private  Timestamp realityDate = null;            //ʵ�ʹ黹ʱ��


	public String getCarmodel() {
		return carmodel;
	}

	public void setCarmodel(String carmodel) {
		this.carmodel = carmodel;
	}

	public Timestamp getCkdate() {
		return ckdate;
	}

	public void setCkdate(Timestamp ckdate) {
		this.ckdate = ckdate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}

	public Timestamp getDateofissue() {
		return dateofissue;
	}

	public void setDateofissue(Timestamp dateofissue) {
		this.dateofissue = dateofissue;
	}

	public String getEnginenumber() {
		return enginenumber;
	}

	public void setEnginenumber(String enginenumber) {
		this.enginenumber = enginenumber;
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

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}

	public String getQualifiednumber() {
		return qualifiednumber;
	}

	public void setQualifiednumber(String qualifiednumber) {
		this.qualifiednumber = qualifiednumber;
	}


	public double getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(double saleprice) {
		this.saleprice = saleprice;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public long getStockpilestatus() {
		return stockpilestatus;
	}

	public void setStockpilestatus(long stockpilestatus) {
		this.stockpilestatus = stockpilestatus;
	}

	public String getStrCarID() {
		return strCarID;
	}

	public void setStrCarID(String strCarID) {
		this.strCarID = strCarID;
	}

	public String getVincode() {
		return vincode;
	}

	public void setVincode(String vincode) {
		this.vincode = vincode;
	}

	public String getWarrantCode() {
		return warrantCode;
	}

	public void setWarrantCode(String warrantCode) {
		this.warrantCode = warrantCode;
	}

	public String getWarrantName() {
		return warrantName;
	}

	public void setWarrantName(String warrantName) {
		this.warrantName = warrantName;
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
}
