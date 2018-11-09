/*
 * Created on 2004-11-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ytcui
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TurnInBankTransSettingInfo implements Serializable
{
	private Timestamp DTTURNINENDTIME = null; //自动入账截至时间

	/**
	 * @return Returns the dTTURNINENDTIME.
	 */
	public Timestamp getDTTURNINENDTIME()
	{
		return DTTURNINENDTIME;
	}
	/**
	 * @param dtturninendtime The dTTURNINENDTIME to set.
	 */
	public void setDTTURNINENDTIME(Timestamp dtturninendtime)
	{
		DTTURNINENDTIME = dtturninendtime;
	}
}
