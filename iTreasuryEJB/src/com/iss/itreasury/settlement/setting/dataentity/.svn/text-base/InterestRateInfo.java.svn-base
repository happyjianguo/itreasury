/*
 * Created on 2004-10-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;
import java.io.ObjectOutputStream.PutField;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
* 此处插入类型说明。
* 创建日期：(2002-1-15 17:50:47)
* @author：Administrator
*/
public class InterestRateInfo extends SettlementBaseDataEntity implements java.io.Serializable {
    /**
     * InterestRateInfo 构造子注解。
     */
    public InterestRateInfo() {
        super();
    }


    public long m_lID;//ID
    public long m_lRateID;//银行利率标识
    public long m_lIRS_ID; //利率调整序列号  added by wLi
    public String m_strCode; //银行利率编号
    public String m_strName ;//银行利率名称
    public String m_strFirstName;//一级名称
    public Timestamp m_tsEffective; //生效日期
    public long m_lInputUserID;  //利率调整提交录入人ID
    public Timestamp m_tsInput;  //利率调整提交录入时间
    public String m_strInputUserName; //利率调整录入人姓名
    public long m_lCheckUserID;  //利率调整复核人ID
    public Timestamp m_tsCheck;  //利率调整复核时间
    public String m_strCheckUserName; //利率调整复核人姓名
    public double m_dRate;  //银行利率
    public long m_lOfficeID;              //办事处标识
    public int m_nStatusID;
    public long m_lPageCount;
    public long m_lContractID;     //合同标示
    public long m_lLoanType;       //贷款类型
    public long m_lConsignCompanyID; //委托单位
    public long m_lPeriod;         //贷款期限
    public String strReason;       //调整原因
    public String strContractCode;  //合同编号
    public String strConsignClientName;//委托单位
    public long m_lBankInterestID;   //BANKLOANINTERESTRATE的ID
    public long m_lSerialNo = -1;
    public long m_nAccountTypeID;
    public long m_nFixeddepositmonthID;
    public long m_nPrimno = -1; //关联复核表ID
    
    
    
    private long Id =-1;
    private long nSerialno=-1;
    private String sName="";
    private Timestamp dtEffective=null;
    private long nInputuserid=-1;
    private Timestamp dtInput=null;
    private double frate=0;
    private long nStatusid=-1;
    private long nCurrencyid=-1;
    private long nOfficeid=-1;
    private long  nAccounttypeid=-1;
    private long nFixeddepositmonthid=-1;
    
    private String userName = "";
    private String DepositMonthDesc="";
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
		super.putUsedField("Id",id);
	}
	public long getnSerialno() {
		return nSerialno;
	}
	public void setnSerialno(long nSerialno) {
		this.nSerialno = nSerialno;
		super.putUsedField("nSerialno",nSerialno);
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
		super.putUsedField("sName",sName);
	}
	public Timestamp getDtEffective() {
		return dtEffective;
	}
	public void setDtEffective(Timestamp dtEffective) {
		this.dtEffective = dtEffective;
		super.putUsedField("dtEffective",dtEffective);
	}
	public long getnInputuserid() {
		return nInputuserid;
	}
	public void setnInputuserid(long nInputuserid) {
		this.nInputuserid = nInputuserid;
		super.putUsedField("nInputuserid",nInputuserid);
	}
	public Timestamp getDtInput() {
		return dtInput;
	}
	public void setDtInput(Timestamp dtInput) {
		this.dtInput = dtInput;
		super.putUsedField("dtInput",dtInput);
	}
	public double getFrate() {
		return frate;
	}
	public void setFrate(double frate) {
		this.frate = frate;
		super.putUsedField("frate",frate);
	}
	public long getnStatusid() {
		return nStatusid;
	}
	public void setnStatusid(long nStatusid) {
		this.nStatusid = nStatusid;
		super.putUsedField("nStatusid",nStatusid);
	}
	public long getnCurrencyid() {
		return nCurrencyid;
	}
	public void setnCurrencyid(long nCurrencyid) {
		this.nCurrencyid = nCurrencyid;
		super.putUsedField("nCurrencyid",nCurrencyid);
	}
	public long getnOfficeid() {
		return nOfficeid;
	}
	public void setnOfficeid(long nOfficeid) {
		this.nOfficeid = nOfficeid;
		super.putUsedField("nOfficeid",nOfficeid);
	}
	public long getnAccounttypeid() {
		return nAccounttypeid;
	}
	public void setnAccounttypeid(long nAccounttypeid) {
		this.nAccounttypeid = nAccounttypeid;
		super.putUsedField("nAccounttypeid",nAccounttypeid);
	}
	public long getnFixeddepositmonthid() {
		return nFixeddepositmonthid;
	}
	public void setnFixeddepositmonthid(long nFixeddepositmonthid) {
		this.nFixeddepositmonthid = nFixeddepositmonthid;
		super.putUsedField("nFixeddepositmonthid",nFixeddepositmonthid);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepositMonthDesc() {
		return DepositMonthDesc;
	}
	public void setDepositMonthDesc(String depositMonthDesc) {
		DepositMonthDesc = depositMonthDesc;
	}

}
