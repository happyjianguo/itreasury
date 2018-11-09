/*
 * PeopleInfo.java
 *
 * Created on 2001年12月30日, 上午9:21
 */
package com.iss.itreasury.system.dataentity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.util.*;
/**
 *
 * @author  yiwang
 * @version
 */
public class EBUserInfo implements Serializable
{
	/** Creates new PeopleInfo */
	public EBUserInfo()
	{
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
	public long lClientID = -1;
	public String strClientCode = "";//客户号
	public String strClientName = "";//客户名称
	//
	public long lStatusID = 0;
	public String strLevel = "";
	// 系统管理员
	public long lSAID = -1;
	public Collection cAccountOwnedByUser = null;//可用账户号
	//
	public long lTotalPages = -1;//页数
	public long lLinesOfPage = Constant.PageControl.CODE_PAGELINECOUNT;//每页行数
	public String strSort = " asc ";//升降序
	public long lCurrentPage = 1;
	public String strOrder = "";
}