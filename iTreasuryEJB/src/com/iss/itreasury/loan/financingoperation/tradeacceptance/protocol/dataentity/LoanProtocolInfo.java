package com.iss.itreasury.loan.financingoperation.tradeacceptance.protocol.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LoanProtocolInfo implements Serializable{
	
	private long id =-1;//ID	NUMBER
	private long aid=-1;//申请id
	private long paymentPorId = -1;//付款单位ID	paymentporID	NUMBER discountclientid
	private long gatheringPorId = -1;//收款单位ID	gatheringportype	NUMBER
	private long gatheringPorType = -1;//收款单位类型	gatheringportype	NUMBER
	private double mOrder = 0.0;//汇款金额	Morder	NUMBER(21,6)
	private double scale = 0.0;//比例	Scale	NUMBER(15,12)
	private long term = -1;//期限	Term	NUMBER
	private double commission = 0.0;
	
	private String applicationCode = "";//申请编号	Applicationtype	Varchar2(10)
	private String applicationReason = "";//申请原因	Applicationreason	Varchar2(1000)
	private String applicationPurpose = "";//申请用途	Applicationpurpose	VARCHAR2(1000)
	private String applicationRimea = "";//承兑来源及措施	Applicationrimea	Varchar2(1000)
	private String corporationcircs = "";//该单位情况简介	Corporationcircs	Varchar2(1000)
	private long uploadId = -1;//上传文件ID	UploadID 	NUMBER
	private long status = -1;// 状态	Status	NUMBER
	private long officeId = -1;//办事处	OFFICEID	NUMBER
	private long nextCheckuserId = -1;//下一个审核人		NEXTCHECKUSERID	NUMBER
	private long nextChecklevel = -1;//下一个审批级别	NEXTCHECKLEVEL	NUMBER
	private long isLowlevel = -1;//审批流程	ISLOWLEVEL	NUMBER
	private long inputuserId = -1;//录入人	INPUTUSERID	NUMBER
	private Timestamp inputDate = null;//录入时间	INPUTDATE	NUMBER
	private long subtypeid=19;  //商业承兑汇票的贷款子类型
	private long nTypeId = -1;//贷款类型
	private long currencyId = -1;//币种

	private long creditcontrolType = -1;// 授信控制 授信方式（授信向下，非授信向下）
	
	
//	显示用
	private String sname = "";//单位名称(出票人，付款方)
	private String scode = "";//单位编号(出票人，付款方)
	private String creditlevel = "";//信用等级
	private String sname1 = "";//内部单位名称(收款方)
	private String scode1 = "";//内部单位编号(收款方)
	private String sname2 = "";//外部单位名称(收款方)
	private String slegalpersoncodecert2 = "";//外部单位企业法人(收款方)
	
	private String uploadName = "";//上传文件名称
	private String uploadPath = "";//上传文件路径
	
	//审批流使用
    private String heckOpinion="";//审批建议
    private long UserID=-1;//当前登陆的用户
	
	/*
	 * a.scontractcode,--协议编号
        a.sloanamountcode,--汇票号码
        a.dtstartdate,--起始日期
        a.dtenddate,--结束日期
	 */
	
	private Timestamp dtstartdate = null;//起始日期
	private Timestamp dtenddate = null;//结束日期
	private String sloanamountcode = "";//汇票号码
	private String scontractcode = "";//协议编号
	
	public String getApplicationCode() {
		return applicationCode;
	}
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	public String getApplicationPurpose() {
		return applicationPurpose;
	}
	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}
	public String getApplicationReason() {
		return applicationReason;
	}
	public void setApplicationReason(String applicationReason) {
		this.applicationReason = applicationReason;
	}
	public String getApplicationRimea() {
		return applicationRimea;
	}
	public void setApplicationRimea(String applicationRimea) {
		this.applicationRimea = applicationRimea;
	}
	public String getCorporationcircs() {
		return corporationcircs;
	}
	public void setCorporationcircs(String corporationcircs) {
		this.corporationcircs = corporationcircs;
	}
	public long getCreditcontrolType() {
		return creditcontrolType;
	}
	public void setCreditcontrolType(long creditcontrolType) {
		this.creditcontrolType = creditcontrolType;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public Timestamp getDtenddate() {
		return dtenddate;
	}
	public void setDtenddate(Timestamp dtenddate) {
		this.dtenddate = dtenddate;
	}
	public Timestamp getDtstartdate() {
		return dtstartdate;
	}
	public void setDtstartdate(Timestamp dtstartdate) {
		this.dtstartdate = dtstartdate;
	}
	public long getGatheringPorId() {
		return gatheringPorId;
	}
	public void setGatheringPorId(long gatheringPorId) {
		this.gatheringPorId = gatheringPorId;
	}
	public long getGatheringPorType() {
		return gatheringPorType;
	}
	public void setGatheringPorType(long gatheringPorType) {
		this.gatheringPorType = gatheringPorType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getInputuserId() {
		return inputuserId;
	}
	public void setInputuserId(long inputuserId) {
		this.inputuserId = inputuserId;
	}
	public long getIsLowlevel() {
		return isLowlevel;
	}
	public void setIsLowlevel(long isLowlevel) {
		this.isLowlevel = isLowlevel;
	}
	public double getMOrder() {
		return mOrder;
	}
	public void setMOrder(double order) {
		mOrder = order;
	}
	public long getNextChecklevel() {
		return nextChecklevel;
	}
	public void setNextChecklevel(long nextChecklevel) {
		this.nextChecklevel = nextChecklevel;
	}
	public long getNextCheckuserId() {
		return nextCheckuserId;
	}
	public void setNextCheckuserId(long nextCheckuserId) {
		this.nextCheckuserId = nextCheckuserId;
	}
	public long getNTypeId() {
		return nTypeId;
	}
	public void setNTypeId(long typeId) {
		nTypeId = typeId;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getPaymentPorId() {
		return paymentPorId;
	}
	public void setPaymentPorId(long paymentPorId) {
		this.paymentPorId = paymentPorId;
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public String getScontractcode() {
		return scontractcode;
	}
	public void setScontractcode(String scontractcode) {
		this.scontractcode = scontractcode;
	}
	public String getSloanamountcode() {
		return sloanamountcode;
	}
	public void setSloanamountcode(String sloanamountcode) {
		this.sloanamountcode = sloanamountcode;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getTerm() {
		return term;
	}
	public void setTerm(long term) {
		this.term = term;
	}
	public long getUploadId() {
		return uploadId;
	}
	public void setUploadId(long uploadId) {
		this.uploadId = uploadId;
	}
	public String getCreditlevel() {
		return creditlevel;
	}
	public void setCreditlevel(String creditlevel) {
		this.creditlevel = creditlevel;
	}
	public String getHeckOpinion() {
		return heckOpinion;
	}
	public void setHeckOpinion(String heckOpinion) {
		this.heckOpinion = heckOpinion;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getScode1() {
		return scode1;
	}
	public void setScode1(String scode1) {
		this.scode1 = scode1;
	}
	public String getSlegalpersoncodecert2() {
		return slegalpersoncodecert2;
	}
	public void setSlegalpersoncodecert2(String slegalpersoncodecert2) {
		this.slegalpersoncodecert2 = slegalpersoncodecert2;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSname1() {
		return sname1;
	}
	public void setSname1(String sname1) {
		this.sname1 = sname1;
	}
	public String getSname2() {
		return sname2;
	}
	public void setSname2(String sname2) {
		this.sname2 = sname2;
	}
	public String getUploadName() {
		return uploadName;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public long getUserID() {
		return UserID;
	}
	public void setUserID(long userID) {
		UserID = userID;
	}
	public long getSubtypeid() {
		return subtypeid;
	}
	public void setSubtypeid(long subtypeid) {
		this.subtypeid = subtypeid;
	}
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}


}
