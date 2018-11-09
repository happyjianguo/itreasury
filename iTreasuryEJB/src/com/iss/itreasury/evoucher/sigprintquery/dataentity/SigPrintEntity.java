package com.iss.itreasury.evoucher.sigprintquery.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class SigPrintEntity extends BaseDataEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id = -1;

	private long nofficeid = -1;// 办事处ID

	private long ncurrencyid = -1;// 币种ID

	private long nclientid = -1;// 客户id

	private String stransno = "";// 交易编号

	private long nbilltypeid = -1;// 交易单据类型编号
	
	private String clientcode="";//客户编号 由
	
	private String clientcode2="";//客户编号 至
	
	private long clientid = -1;//客户id
	
	private String inputusername="";//用户名

	private String printuser = "";// 交易人
	
	private String printusername="";//打印人名称
	
	private String clientCode3="";//打印人名称

	private Timestamp dtprintdate = null;// 打印时间

	private Timestamp qstartprintdate = null;// 查询用--打印开始日期

	private Timestamp qendprintdate = null;// 查询用--打印截止日期
	
	private Timestamp startPrintDate = null;// 查询用--打印开始日期
	
	private Timestamp endPrintDate = null;// 查询用--打印截止日期

	private long startofficeid = -1;// 查询用--办事处起始编号

	private long endofficeid = -1;// 查询用--办事处终止编号

	private String startCode="";
	
	private String endCode="";
	
	private long nprintcount=-1;
	
	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getStartCode() {
		return startCode;
	}

	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}

	public long getEndofficeid() {
		return endofficeid;
	}

	public void setEndofficeid(long endofficeid) {
		this.endofficeid = endofficeid;
	}

	public long getStartofficeid() {
		return startofficeid;
	}

	public void setStartofficeid(long startofficeid) {
		this.startofficeid = startofficeid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNbilltypeid() {
		return nbilltypeid;
	}

	public void setNbilltypeid(long nbilltypeid) {
		this.nbilltypeid = nbilltypeid;
	}

	public long getNcurrencyid() {
		return ncurrencyid;
	}

	public void setNcurrencyid(long ncurrencyid) {
		this.ncurrencyid = ncurrencyid;
	}

	public long getNofficeid() {
		return nofficeid;
	}

	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}

	public String getPrintuser() {
		return printuser;
	}

	public void setPrintuser(String printuser) {
		this.printuser = printuser;
	}

	public Timestamp getQendprintdate() {
		return qendprintdate;
	}



	public Timestamp getDtprintdate() {
		return dtprintdate;
	}

	public void setDtprintdate(Timestamp dtprintdate) {
		this.dtprintdate = dtprintdate;
	}

	public Timestamp getQstartprintdate() {
		return qstartprintdate;
	}

	public void setQstartprintdate(Timestamp qstartprintdate) {
		this.qstartprintdate = qstartprintdate;
	}

	public void setQendprintdate(Timestamp qendprintdate) {
		this.qendprintdate = qendprintdate;
	}

	public String getStransno() {
		return stransno;
	}

	public void setStransno(String stransno) {
		this.stransno = stransno;
	}

	public long getNclientid() {
		return nclientid;
	}

	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
	}

	public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}

	public long getClientid() {
		return clientid;
	}

	public void setClientid(long clientid) {
		this.clientid = clientid;
	}

	public String getInputusername() {
		return inputusername;
	}

	public void setInputusername(String inputusername) {
		this.inputusername = inputusername;
	}

	public String getPrintusername() {
		return printusername;
	}

	public void setPrintusername(String printusername) {
		this.printusername = printusername;
	}

	public long getNprintcount() {
		return nprintcount;
	}

	public void setNprintcount(long nprintcount) {
		this.nprintcount = nprintcount;
	}

	public String getClientcode2() {
		return clientcode2;
	}

	public void setClientcode2(String clientcode2) {
		this.clientcode2 = clientcode2;
	}

	public Timestamp getStartPrintDate() {
		return startPrintDate;
	}

	public void setStartPrintDate(Timestamp startPrintDate) {
		this.startPrintDate = startPrintDate;
	}

	public Timestamp getEndPrintDate() {
		return endPrintDate;
	}

	public void setEndPrintDate(Timestamp endPrintDate) {
		this.endPrintDate = endPrintDate;
	}

	public String getClientCode3() {
		return clientCode3;
	}

	public void setClientCode3(String clientCode3) {
		this.clientCode3 = clientCode3;
	}

}
