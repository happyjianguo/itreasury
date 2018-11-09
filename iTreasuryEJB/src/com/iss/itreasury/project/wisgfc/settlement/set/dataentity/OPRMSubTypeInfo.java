package com.iss.itreasury.project.wisgfc.settlement.set.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author xlchang 2010-10-29
 * �����������
 */
public class OPRMSubTypeInfo extends ITreasuryBaseDataEntity{
	private static final long serialVersionUID = -1402206232790982692L;
	
	private long id;
	private long nOfficeID;    //���´�id
	private long nCurrencyID;  //����id
	private String sCode;      //����
	private String sName;      //����
	private long nStatusID;    //״̬
	
	String q_startCode = "";      //��ѯ����-���ͱ�����
	String q_endCode = "";      //��ѯ����-���ͱ��뵽
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	public long getNOfficeID() {
		return nOfficeID;
	}
	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
		putUsedField("nOfficeID",officeID);
	}
	public long getNCurrencyID() {
		return nCurrencyID;
	}
	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
		putUsedField("nCurrencyID",nCurrencyID);
	}
	public String getSCode() {
		return sCode;
	}
	public void setSCode(String code) {
		sCode = code;
		putUsedField("sCode",sCode);
	}
	public String getSName() {
		return sName;
	}
	public void setSName(String name) {
		sName = name;
		putUsedField("sName",sName);
	}
	public long getNStatusID() {
		return nStatusID;
	}
	public void setNStatusID(long statusID) {
		nStatusID = statusID;
		putUsedField("nStatusID",nStatusID);
	}
	public String getQ_startCode() {
		return q_startCode;
	}
	public void setQ_startCode(String code) {
		q_startCode = code;
	}
	public String getQ_endCode() {
		return q_endCode;
	}
	public void setQ_endCode(String code) {
		q_endCode = code;
	}
	
	
}
