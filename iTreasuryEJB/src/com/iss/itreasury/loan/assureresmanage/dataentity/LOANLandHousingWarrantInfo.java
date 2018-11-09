package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;



public class LOANLandHousingWarrantInfo implements Serializable {

    private  long       id                       = -1; 
	
    private  long       gageid               = -1;              //担保品信息ID
    
    private  String     warrantcode          = "";    //权证编号
    
    private  String     warrantname          = "";    //权证名称
    
    private  long       warrantType          = -1;    //权证类型(房屋他项、国有土地)
     
    private  String     landDirtid           = "";              //土地他项权益证号
    
    private  String     landLocation         = "";            //土地座落
    
    private  String     landFieldsnumber     = "";        //土地地号
    
    private  String     landChartnumber      = "";         //土地图号
    
    private  String     landProperties       = "";          //土地权属性质
    
    private  String     landUsearea          = "";              //土地使用权面积
    
    private  String     landPurpose          = "";             //土地地类（用途）
    
    private  String     landUsetype          = "";             //土地使用权类型
    
    private  String     landCategoryscope    = "";       //土地他项权利种类及范围
    
    private  String     landCheckinorgan     = "";        //土地登记机关
    
    private  Timestamp  landCheckindate      = null;         //土地登记日期
    
    private  Timestamp  landSetdate          = null;             //土地设定日期
    
    private  String     landTitleorder       = "";          //土地权利顺序
    
    private  String     landTimelimit        = "";           //土地存续期限
    
    private  String     landAdversaria       = "";          //土地记事
    
    
    private  String     houseDroitnumber      = "";          //房屋所有权证号
    
    private  String     houseLocation         = "";     //房屋座落
    
    private  String     houseDroittype        = "";            //权利种类
    
    
    private  Timestamp  houseSetdate           = null;               //设定日期
    
    private  Timestamp  houseFaithdate         = null;             //约定日期
    
    private  Timestamp  houseLogoutdate        = null;            //注销日期
    
    private  String     houseCheckinorgan      = "";          //登记机关
    
    private  Timestamp  houseCheckindate       = null;           //登记日期
    
    
    private  long       status               = -1;              //状态
    
    private  long       inputuserid          = -1;         //录入人
    
    private  Timestamp  inputdate            = null;           //录入日期
    
    private  long       officeid             = -1;            //办事处
    
    private  long       currencyid           = -1;          //币种
    
    private  double     hdroitValue          = 0;          //权利价值
    
    
    private  String lendPerson = "";                  //出库/出借人
    
    private  Timestamp lendDate = null;               //出库或出借时间
    
    private  Timestamp anticipateDate = null;         //出库或出借预计归还时间
    
    private  String   lendCause = "";                 //出库/出借原因
    
    private  Timestamp realityDate = null;            //实际归还时间
    


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
