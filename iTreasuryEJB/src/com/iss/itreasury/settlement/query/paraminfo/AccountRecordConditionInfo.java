package com.iss.itreasury.settlement.query.paraminfo;

import java.sql.Timestamp;

/**
 * 此处插入类型说明。
 * 创建日期：(2007-6-11)
 * @author：
 */
public class AccountRecordConditionInfo implements java.io.Serializable{
	
	private long id = -1; //科目ID
	private String subjectCode = ""; //科目号
	private long officeId = -1; //办事处
	private long currencyId = -1; //币种
	private Timestamp tsDateStart = null; //起始日期
	private Timestamp tsDateEnd = null; //结束日期
	
	private long lPageCount = -1; //每页行数
	private long lPageNo = -1; //当前页
	private long lOrderParam = -1; //order by 的参数
	private long lDesc = -1; //order by aes & desc
	//科目汇总查询增加科目区间信息，增加是否虑空信息
	private String subjectCodeStart = ""; //科目号开始
	private String subjectCodeEnd = ""; //科目号结束
	/**by lidi 20101027 用于多选科目*/
	private String allSub = ""; //所有科目
	private long iflv = -1; //是否虑空
	
	
	public String getSubjectCodeStart() {
		return subjectCodeStart;
	}
	public void setSubjectCodeStart(String subjectCodeStart) {
		this.subjectCodeStart = subjectCodeStart;
	}
	public String getSubjectCodeEnd() {
		return subjectCodeEnd;
	}
	public void setSubjectCodeEnd(String subjectCodeEnd) {
		this.subjectCodeEnd = subjectCodeEnd;
	}
	public long getIflv() {
		return iflv;
	}
	public void setIflv(long iflv) {
		this.iflv = iflv;
	}
	public long getLPageCount() {
		return lPageCount;
	}
	public void setLPageCount(long pageCount) {
		lPageCount = pageCount;
	}
	public long getLDesc() {
		return lDesc;
	}
	public void setLDesc(long desc) {
		lDesc = desc;
	}
	public long getLOrderParam() {
		return lOrderParam;
	}
	public void setLOrderParam(long orderParam) {
		lOrderParam = orderParam;
	}
	public long getLPageNo() {
		return lPageNo;
	}
	public void setLPageNo(long pageNo) {
		lPageNo = pageNo;
	}
	public Timestamp getTsDateEnd() {
		return tsDateEnd;
	}
	public void setTsDateEnd(Timestamp tsDateEnd) {
		this.tsDateEnd = tsDateEnd;
	}
	public Timestamp getTsDateStart() {
		return tsDateStart;
	}
	public void setTsDateStart(Timestamp tsDateStart) {
		this.tsDateStart = tsDateStart;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getAllSub() {
		return allSub;
	}
	public void setAllSub(String allSub) {
		this.allSub = allSub;
	}
	
	
	
}
