/*
 * Created on 2004-06-23
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.market.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author kewen hu 2004-06-23 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MarketConditionInfo extends SECBaseDataEntity implements java.io.Serializable {
	private long id = -1;					//Id
	private String secSecuritiesCode = "";	// 证券代码
    private String secSecuritiesName = "";	// 证券名称
    private long secSecuritiesTypeID = -1;	// 证券类别
    private long secSecuritiesID = -1;		// 证券ID
    private long secOrderParam = -1;		// 排序
    private long secAscOrDesc = -1;			// 升或降
    private String secCloseStart = ""; 		// 收盘日起始
    private String secCloseEnd = "";		// 收盘日截止
	/**
	 * @return Returns the secAscOrDesc.
	 */
	public long getSecAscOrDesc() {
		return secAscOrDesc;
	}
	/**
	 * @param secAscOrDesc The secAscOrDesc to set.
	 */
	public void setSecAscOrDesc(long secAscOrDesc) {
		this.secAscOrDesc = secAscOrDesc;
	}
	/**
	 * @return Returns the secOrderParam.
	 */
	public long getSecOrderParam() {
		return secOrderParam;
	}
	/**
	 * @param secOrderParam The secOrderParam to set.
	 */
	public void setSecOrderParam(long secOrderParam) {
		this.secOrderParam = secOrderParam;
	}
	/**
	 * @return Returns the secSecuritiesCode.
	 */
	public String getSecSecuritiesCode() {
		return secSecuritiesCode;
	}
	/**
	 * @param secSecuritiesCode The secSecuritiesCode to set.
	 */
	public void setSecSecuritiesCode(String secSecuritiesCode) {
		this.secSecuritiesCode = secSecuritiesCode;
	}
	/**
	 * @return Returns the secSecuritiesID.
	 */
	public long getSecSecuritiesID() {
		return secSecuritiesID;
	}
	/**
	 * @param secSecuritiesID The secSecuritiesID to set.
	 */
	public void setSecSecuritiesID(long secSecuritiesID) {
		this.secSecuritiesID = secSecuritiesID;
	}
	/**
	 * @return Returns the secSecuritiesName.
	 */
	public String getSecSecuritiesName() {
		return secSecuritiesName;
	}
	/**
	 * @param secSecuritiesName The secSecuritiesName to set.
	 */
	public void setSecSecuritiesName(String secSecuritiesName) {
		this.secSecuritiesName = secSecuritiesName;
	}
	/**
	 * @return Returns the secSecuritiesTypeID.
	 */
	public long getSecSecuritiesTypeID() {
		return secSecuritiesTypeID;
	}
	/**
	 * @param secSecuritiesTypeID The secSecuritiesTypeID to set.
	 */
	public void setSecSecuritiesTypeID(long secSecuritiesTypeID) {
		this.secSecuritiesTypeID = secSecuritiesTypeID;
	}
	/**
	 * @return Returns the secCloseEnd.
	 */
	public String getSecCloseEnd() {
		return secCloseEnd;
	}
	/**
	 * @param secCloseEnd The secCloseEnd to set.
	 */
	public void setSecCloseEnd(String secCloseEnd) {
		this.secCloseEnd = secCloseEnd;
	}
	/**
	 * @return Returns the secCloseStart.
	 */
	public String getSecCloseStart() {
		return secCloseStart;
	}
	/**
	 * @param secCloseStart The secCloseStart to set.
	 */
	public void setSecCloseStart(String secCloseStart) {
		this.secCloseStart = secCloseStart;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
}