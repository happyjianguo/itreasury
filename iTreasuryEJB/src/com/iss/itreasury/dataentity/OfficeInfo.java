package com.iss.itreasury.dataentity;

/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 18:25:18)
 * @author：Administrator
 */
public class OfficeInfo implements java.io.Serializable {
	public long m_lID;
	public String m_strCode = "";
	public String m_strName = "";
	public long m_lReservedAccountID;
	public String m_strSubjectCode = "";
	public long m_lPageCount;
	public String m_strReservedAccountNo = "";
	public String m_currencyID="";

    //确认关机时间
    public java.sql.Timestamp m_dtAffrimDate = null;

	public String getM_currencyID() {
		return m_currencyID;
	}

	public void setM_currencyID(String m_currencyid) {
		m_currencyID = m_currencyid;
	}

	public long getM_lID() {
		return m_lID;
	}

	public void setM_lID(long m_lid) {
		m_lID = m_lid;
	}

	public String getM_strName() {
		return m_strName;
	}

	public void setM_strName(String name) {
		m_strName = name;
	}
}