package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;



public class LOANLandHousingWarrantInfo implements Serializable {

    private  long       id                       = -1; 
	
    private  long       gageid               = -1;              //����Ʒ��ϢID
    
    private  String     warrantcode          = "";    //Ȩ֤���
    
    private  String     warrantname          = "";    //Ȩ֤����
    
    private  long       warrantType          = -1;    //Ȩ֤����(���������������)
     
    private  String     landDirtid           = "";              //��������Ȩ��֤��
    
    private  String     landLocation         = "";            //��������
    
    private  String     landFieldsnumber     = "";        //���صغ�
    
    private  String     landChartnumber      = "";         //����ͼ��
    
    private  String     landProperties       = "";          //����Ȩ������
    
    private  String     landUsearea          = "";              //����ʹ��Ȩ���
    
    private  String     landPurpose          = "";             //���ص��ࣨ��;��
    
    private  String     landUsetype          = "";             //����ʹ��Ȩ����
    
    private  String     landCategoryscope    = "";       //��������Ȩ�����༰��Χ
    
    private  String     landCheckinorgan     = "";        //���صǼǻ���
    
    private  Timestamp  landCheckindate      = null;         //���صǼ�����
    
    private  Timestamp  landSetdate          = null;             //�����趨����
    
    private  String     landTitleorder       = "";          //����Ȩ��˳��
    
    private  String     landTimelimit        = "";           //���ش�������
    
    private  String     landAdversaria       = "";          //���ؼ���
    
    
    private  String     houseDroitnumber      = "";          //��������Ȩ֤��
    
    private  String     houseLocation         = "";     //��������
    
    private  String     houseDroittype        = "";            //Ȩ������
    
    
    private  Timestamp  houseSetdate           = null;               //�趨����
    
    private  Timestamp  houseFaithdate         = null;             //Լ������
    
    private  Timestamp  houseLogoutdate        = null;            //ע������
    
    private  String     houseCheckinorgan      = "";          //�Ǽǻ���
    
    private  Timestamp  houseCheckindate       = null;           //�Ǽ�����
    
    
    private  long       status               = -1;              //״̬
    
    private  long       inputuserid          = -1;         //¼����
    
    private  Timestamp  inputdate            = null;           //¼������
    
    private  long       officeid             = -1;            //���´�
    
    private  long       currencyid           = -1;          //����
    
    private  double     hdroitValue          = 0;          //Ȩ����ֵ
    
    
    private  String lendPerson = "";                  //����/������
    
    private  Timestamp lendDate = null;               //��������ʱ��
    
    private  Timestamp anticipateDate = null;         //��������Ԥ�ƹ黹ʱ��
    
    private  String   lendCause = "";                 //����/����ԭ��
    
    private  Timestamp realityDate = null;            //ʵ�ʹ黹ʱ��
    


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

	public double getHdroitValue() {
		return hdroitValue;
	}

	public void setHdroitValue(double hdroitValue) {
		this.hdroitValue = hdroitValue;
	}

	public Timestamp getHouseCheckindate() {
		return houseCheckindate;
	}

	public void setHouseCheckindate(Timestamp houseCheckindate) {
		this.houseCheckindate = houseCheckindate;
	}

	public String getHouseCheckinorgan() {
		return houseCheckinorgan;
	}

	public void setHouseCheckinorgan(String houseCheckinorgan) {
		this.houseCheckinorgan = houseCheckinorgan;
	}

	public String getHouseDroitnumber() {
		return houseDroitnumber;
	}

	public void setHouseDroitnumber(String houseDroitnumber) {
		this.houseDroitnumber = houseDroitnumber;
	}

	public String getHouseDroittype() {
		return houseDroittype;
	}

	public void setHouseDroittype(String houseDroittype) {
		this.houseDroittype = houseDroittype;
	}

	public Timestamp getHouseFaithdate() {
		return houseFaithdate;
	}

	public void setHouseFaithdate(Timestamp houseFaithdate) {
		this.houseFaithdate = houseFaithdate;
	}

	public String getHouseLocation() {
		return houseLocation;
	}

	public void setHouseLocation(String houseLocation) {
		this.houseLocation = houseLocation;
	}

	public Timestamp getHouseLogoutdate() {
		return houseLogoutdate;
	}

	public void setHouseLogoutdate(Timestamp houseLogoutdate) {
		this.houseLogoutdate = houseLogoutdate;
	}

	public Timestamp getHouseSetdate() {
		return houseSetdate;
	}

	public void setHouseSetdate(Timestamp houseSetdate) {
		this.houseSetdate = houseSetdate;
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

	public String getLandAdversaria() {
		return landAdversaria;
	}

	public void setLandAdversaria(String landAdversaria) {
		this.landAdversaria = landAdversaria;
	}

	public String getLandCategoryscope() {
		return landCategoryscope;
	}

	public void setLandCategoryscope(String landCategoryscope) {
		this.landCategoryscope = landCategoryscope;
	}

	public String getLandChartnumber() {
		return landChartnumber;
	}

	public void setLandChartnumber(String landChartnumber) {
		this.landChartnumber = landChartnumber;
	}

	public Timestamp getLandCheckindate() {
		return landCheckindate;
	}

	public void setLandCheckindate(Timestamp landCheckindate) {
		this.landCheckindate = landCheckindate;
	}

	public String getLandCheckinorgan() {
		return landCheckinorgan;
	}

	public void setLandCheckinorgan(String landCheckinorgan) {
		this.landCheckinorgan = landCheckinorgan;
	}

	public String getLandDirtid() {
		return landDirtid;
	}

	public void setLandDirtid(String landDirtid) {
		this.landDirtid = landDirtid;
	}

	public String getLandFieldsnumber() {
		return landFieldsnumber;
	}

	public void setLandFieldsnumber(String landFieldsnumber) {
		this.landFieldsnumber = landFieldsnumber;
	}

	public String getLandLocation() {
		return landLocation;
	}

	public void setLandLocation(String landLocation) {
		this.landLocation = landLocation;
	}

	public String getLandProperties() {
		return landProperties;
	}

	public void setLandProperties(String landProperties) {
		this.landProperties = landProperties;
	}

	public String getLandPurpose() {
		return landPurpose;
	}

	public void setLandPurpose(String landPurpose) {
		this.landPurpose = landPurpose;
	}

	public Timestamp getLandSetdate() {
		return landSetdate;
	}

	public void setLandSetdate(Timestamp landSetdate) {
		this.landSetdate = landSetdate;
	}

	public String getLandTimelimit() {
		return landTimelimit;
	}

	public void setLandTimelimit(String landTimelimit) {
		this.landTimelimit = landTimelimit;
	}

	public String getLandTitleorder() {
		return landTitleorder;
	}

	public void setLandTitleorder(String landTitleorder) {
		this.landTitleorder = landTitleorder;
	}


	public String getLandUsearea() {
		return landUsearea;
	}

	public void setLandUsearea(String landUsearea) {
		this.landUsearea = landUsearea;
	}

	public String getLandUsetype() {
		return landUsetype;
	}

	public void setLandUsetype(String landUsetype) {
		this.landUsetype = landUsetype;
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

	public String getWarrantcode() {
		return warrantcode;
	}

	public void setWarrantcode(String warrantcode) {
		this.warrantcode = warrantcode;
	}

	public String getWarrantname() {
		return warrantname;
	}

	public void setWarrantname(String warrantname) {
		this.warrantname = warrantname;
	}

	public long getWarrantType() {
		return warrantType;
	}

	public void setWarrantType(long warrantType) {
		this.warrantType = warrantType;
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
