/*
 * Created on 2003-5-14
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.fcinterface.bankportal.system;
import java.sql.Timestamp;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoginInfo
{
	private long m_lUserID = -1;
	private String m_strUserName = null;
	private Timestamp m_tsLoginDate = null;
	private String m_strAccessType = null;
	private String m_strModule = null;
	private String m_strOffice = null;
	private String m_strCurrency = null;
	private long m_lTotalPages = -1;
	private String m_strSort = " asc ";
	private long m_lCurrentPage = 1;
	private String m_strOrder = "sOffice,dtLogin";
	private Timestamp m_tsStartDate = null;
	private Timestamp m_tsEndDate = null;
	
	private String m_strLoginName = "";//网银用户登陆名
	private String m_strClientName = "";//网银用户客户名称
	private String m_strClientCode = "";//网银用户客户编号
	private long m_lClientId = -1;//网银用户客户编号
	
	private long m_lRecordCount = -1;//查询结果记录条数
	
	private String sIpAddress=""; 
	
	/**
	 * @return
	 */
	public long getUserID()
	{
		return m_lUserID;
	}
	/**
	 * @return
	 */
	public String getAccessType()
	{
		return m_strAccessType;
	}
	/**
	 * @return
	 */
	public String getModule()
	{
		return m_strModule;
	}
	/**
	 * @return
	 */
	public String getOffice()
	{
		return m_strOffice;
	}
	/**
	 * @return
	 */
	public String getUserName()
	{
		return m_strUserName;
	}
	/**
	 * @return
	 */
	public Timestamp getLoginDate()
	{
		return m_tsLoginDate;
	}
	/**
	 * @param l
	 */
	public void setUserID(long l)
	{
		m_lUserID = l;
	}
	/**
	 * @param long
	 */
	public void setAccessType(long lType)
	{
		if (lType == 1)
			m_strAccessType = "普通方式";
		if (lType == 2)
			m_strAccessType = "安全方式";
	}
	
	/**
	 * @param long
	 */
	public void setOffice(long lOfficeID)
	{
		if (lOfficeID == 1)
			m_strOffice = "北京总部";
		else if (lOfficeID == 2)
			m_strOffice = "大庆办事处";
		else if (lOfficeID == 3)
			m_strOffice = "沈阳办事处";
		else if (lOfficeID == 4)
			m_strOffice = "吉林办事处";
		else if (lOfficeID == 5)
			m_strOffice = "西安办事处";
	}
	/**
	 * @param string
	 */
	public void setUserName(String string)
	{
		m_strUserName = string;
	}
	/**
	 * @param timestamp
	 */
	public void setLoginDate(Timestamp timestamp)
	{
		m_tsLoginDate = timestamp;
	}
	/**
	 * @return
	 */
	public String getCurrency()
	{
		return m_strCurrency;
	}
	
	/**
	 * @return
	 */
	public long getCurrentPage()
	{
		return m_lCurrentPage;
	}
	/**
	 * @return
	 */
	public String getSort()
	{
		return m_strSort;
	}
	/**
	 * @return
	 */
	public long getTotalPages()
	{
		return m_lTotalPages;
	}
	/**
	 * @return
	 */
	public String getOrder()
	{
		return m_strOrder;
	}
	/**
	 * @param l
	 */
	public void setCurrentPage(long l)
	{
		m_lCurrentPage = l;
	}
	/**
	 * @param l
	 */
	public void setSort(long l)
	{
		if (l == 1)
			m_strSort = " asc ";
		if (l == 2)
			m_strSort = " desc ";
	}
	/**
	 * @param l
	 */
	public void setSort(String str)
	{
		m_strSort = str;
	}
	/**
	 * @param l
	 */
	public void setTotalPages(long l)
	{
		m_lTotalPages = l;
	}
	/**
	 * @param string
	 */
	public void setAccessType(String string)
	{
		m_strAccessType = string;
	}
	/**
	 * @param string
	 */
	public void setCurrency(String string)
	{
		m_strCurrency = string;
	}
	/**
	 * @param string
	 */
	public void setModule(String string)
	{
		m_strModule = string;
	}
	/**
	 * @param string 
	 */
	public void setOffice(String string)
	{
		m_strOffice = string;
	}
	/**
	 * @param string
	 */
	public void setOrder(String string)
	{
		m_strOrder = string;
	}
	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return m_tsEndDate;
	}
	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return m_tsStartDate;
	}
	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		m_tsEndDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		m_tsStartDate = timestamp;
	}
	/**
	 * @return Returns the m_strClientCode.
	 */
	public String getClientCode() {
		return m_strClientCode;
	}
	/**
	 * @param clientCode The m_strClientCode to set.
	 */
	public void setClientCode(String clientCode) {
		m_strClientCode = clientCode;
	}
	/**
	 * @return Returns the m_strClientName.
	 */
	public String getClientName() {
		return m_strClientName;
	}
	/**
	 * @param clientName The m_strClientName to set.
	 */
	public void setClientName(String clientName) {
		m_strClientName = clientName;
	}
	/**
	 * @return Returns the m_strLoginName.
	 */
	public String getLoginName() {
		return m_strLoginName;
	}
	/**
	 * @param loginName The m_strLoginName to set.
	 */
	public void setLoginName(String loginName) {
		m_strLoginName = loginName;
	}
	/**
	 * @return Returns the m_lClientId.
	 */
	public long getClientId() {
		return m_lClientId;
	}
	/**
	 * @param clientId The m_lClientId to set.
	 */
	public void setClientId(long clientId) {
		m_lClientId = clientId;
	}
	/**
	 * @return Returns the m_lRecordCount.
	 */
	public long getRecordCount() {
		return m_lRecordCount;
	}
	/**
	 * @param recordCount The m_lRecordCount to set.
	 */
	public void setRecordCount(long recordCount) {
		m_lRecordCount = recordCount;
	}
	
	
	/**
	 * @return Returns the sIpAddress.
	 */
	public String getSIpAddress() {
		return sIpAddress;
	}
	/**
	 * @param ipAddress The sIpAddress to set.
	 */
	public void setSIpAddress(String ipAddress) {
		sIpAddress = ipAddress;
	}
}
