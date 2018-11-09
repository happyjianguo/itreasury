package com.iss.itreasury.loan.aftercredit.dataentity;
import java.sql.Timestamp;



import com.iss.itreasury.util.ITreasuryBaseDataEntity;
public class FiveSortDetailInfo  extends ITreasuryBaseDataEntity implements java.io.Serializable{
	
	 private long id = -1;
     private long fivesortid = -1;
     private long contractid = -1;
     private long sortresolution = -1;
     private long presort = -1;//调整前分类
     private String explain = "";
     private long status = -1;
     private long inputuserid = -1;
     private Timestamp inputdate = null;
     
     //
     private String clientname = "";
     private String contractcode = "";
     private long risklevel = -1;
     private String checkreportcode = "";
     private long advice =-1;
     //add 2008-9-22
     private long checkreportid = -1;
     private double mloanamount = 0;//贷款 金额
     private Timestamp dtstartdate = null;//合同开始日期
     private Timestamp dtenddate = null;//合同结束日期
     
     
	public double getMloanamount() {
		return mloanamount;
	}
	public void setMloanamount(double mloanamount) {
		this.mloanamount = mloanamount;
	}
	public Timestamp getDtstartdate() {
		return dtstartdate;
	}
	public void setDtstartdate(Timestamp dtstartdate) {
		this.dtstartdate = dtstartdate;
	}
	public Timestamp getDtenddate() {
		return dtenddate;
	}
	public void setDtenddate(Timestamp dtenddate) {
		this.dtenddate = dtenddate;
	}
	public long getPresort() {
		return presort;
	}
	public void setPresort(long presort) {
		this.presort = presort;
	}
	public long getCheckreportid() {
		return checkreportid;
	}
	public void setCheckreportid(long checkreportid) {
		this.checkreportid = checkreportid;
	}
	public long getContractid() {
		return contractid;
	}
	public void setContractid(long contractid) {
		this.contractid = contractid;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public long getFivesortid() {
		return fivesortid;
	}
	public void setFivesortid(long fivesortid) {
		this.fivesortid = fivesortid;
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
	public long getSortresolution() {
		return sortresolution;
	}
	public void setSortresolution(long sortresolution) {
		this.sortresolution = sortresolution;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getAdvice() {
		return advice;
	}
	public void setAdvice(long advice) {
		this.advice = advice;
	}
	public String getCheckreportcode() {
		return checkreportcode;
	}
	public void setCheckreportcode(String checkreportcode) {
		this.checkreportcode = checkreportcode;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getContractcode() {
		return contractcode;
	}
	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
	}
	public long getRisklevel() {
		return risklevel;
	}
	public void setRisklevel(long risklevel) {
		this.risklevel = risklevel;
	}
     
     
     

}
