/*
 * Created on 2006-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.matureremind.dataentity;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MatureRemindInfo implements java.io.Serializable{

	private String maturity = "";//到期日
	private String content = "";//提醒内容
	private long  officeId ;  //办事处id
	private long currencyid ;
	
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the maturity.
	 */
	public String getMaturity() {
		return maturity;
	}
	/**
	 * @param maturity The maturity to set.
	 */
	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}
	
}
