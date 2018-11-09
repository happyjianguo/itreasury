/*
 * PeopleInfo.java
 *
 * Created on 2001��12��30��, ����9:21
 */

package com.iss.itreasury.dataentity;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author  yiwang
 * @version
 */
public class PeopleInfo implements Serializable {

	/** Creates new PeopleInfo */
	public PeopleInfo() {
	}
	// people fields
	public long lUid = -1; // userid
	public String strLogin = "";
	public String strPassword = "";
	public String strUserName = "";
	public long lOfficeID = -1;
	public String strOfficeName = "";
	public String strGroupName = "";
	public long lCurrencyID = -1;
	public long lInputUserID = -1;
	public String strInputUserName = "";
	public java.sql.Date dtInput = null;
	public java.util.Vector vModule = null;
	//
	public Vector vGroupID = null;
	//
	public long lStatusID = 0;
	public String strLevel = "";
	public long lCloseSystem = 0;
	public long lIsAdminUser = 2;    //�Ƿ�ϵͳ����Ա1���ǣ�2����
	public long lIsVirtualUser = 2;  //�Ƿ������û�1���ǣ�2����
}