/*
 * Created on 2003-9-27
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.sql.Timestamp;


public class OpenDateInfo  implements java.io.Serializable
{
	private Timestamp tsOpenDate = null;
	private long SystemStatusID = -1;
	
	/**
	 * @return
	 */
	public long getSystemStatusID() {
		return SystemStatusID;
	}

	/**
	 * @return
	 */
	public Timestamp getOpenDate() {
		return tsOpenDate;
	}

	/**
	 * @param l
	 */
	public void setSystemStatusID(long l) {
		SystemStatusID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setOpenDate(Timestamp timestamp) {
		tsOpenDate = timestamp;
	}

}

