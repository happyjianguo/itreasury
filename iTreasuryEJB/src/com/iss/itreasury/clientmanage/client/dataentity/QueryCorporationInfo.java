/**
 * 
 */
package com.iss.itreasury.clientmanage.client.dataentity;

import java.io.Serializable;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author niweinan
 *
 */
public class QueryCorporationInfo extends BaseDataEntity implements Serializable {
	
	public QueryCorporationInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
   
	long OfficeID = -1;  //办事处id
	private long CurrencyID = -1;
	private long lClientIDStart = -1;   //客户id 由
	private long lClientIDEnd = -1;   //客户id 至
	private String lClientNOStart = ""; //客户编号 由
	private String lClientNOEnd = ""; //客户编号 至
	private String appointAccountNo = "";
	
	public long getOfficeID() {
		return OfficeID;
	}
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}
	

	public long getLClientIDStart() {
		return lClientIDStart;
	}
	
	public void setLClientIDStart(long lClientIDStart) {
		this.lClientIDStart = lClientIDStart;
		putUsedField("lClientIDStart", lClientIDStart);
	}
	
	public long getLClientIDEnd() {
		return lClientIDEnd;
	}
	
	public void setLClientIDEnd(long lClientIDEnd) {
		this.lClientIDEnd = lClientIDEnd;
		putUsedField("lClientIDEnd", lClientIDEnd);
	}
	
	public String getLClientNOStart() {
		return lClientNOStart;
	}
	
	public void setLClientNOStart(String lClientNOStart) {
		this.lClientNOStart = lClientNOStart;
		putUsedField("lClientNOStart", lClientNOStart);
	}
	
	public String getLClientNOEnd() {
		return lClientNOEnd;
	}
	
	public void setLClientNOEnd(String lClientNOEnd) {
		this.lClientNOEnd = lClientNOEnd;
		putUsedField("lClientNOEnd", lClientNOEnd);
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	public long getCurrencyID() {
		return CurrencyID;
	}
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	
	public String getAppointAccountNo() {
		return appointAccountNo;
	}
	public void setAppointAccountNo(String appointAccountNo) {
		this.appointAccountNo = appointAccountNo;
	}
	
}
