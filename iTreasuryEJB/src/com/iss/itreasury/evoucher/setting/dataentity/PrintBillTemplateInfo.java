package com.iss.itreasury.evoucher.setting.dataentity;

public class PrintBillTemplateInfo {

	private long id = -1;
	private long nBillID = -1;//��Ӧ����id
	private long nIsNeedSeal = -1;//�Ƿ���Ҫǩ��
	private int lEntryNum = -1;//������Ŀ����
	private String sDescription = "";//��������
	private String strTemplateContent = "";//���ݴ�ӡ��ʽ
	private long lCoupletNo = -1;//ģ���Ӧ��������
	private String nUrl = "";//ģ������·��
	
	private String strBillName = "";//��������
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNBillID() {
		return nBillID;
	}
	public void setNBillID(long billID) {
		nBillID = billID;
	}
	public long getNIsNeedSeal() {
		return nIsNeedSeal;
	}
	public void setNIsNeedSeal(long isNeedSeal) {
		nIsNeedSeal = isNeedSeal;
	}
	public String getNUrl() {
		return nUrl;
	}
	public void setNUrl(String url) {
		nUrl = url;
	}
	public String getSDescription() {
		return sDescription;
	}
	public void setSDescription(String description) {
		sDescription = description;
	}
	public String getStrTemplateContent() {
		return strTemplateContent;
	}
	public void setStrTemplateContent(String strTemplateContent) {
		this.strTemplateContent = strTemplateContent;
	}
	public int getLEntryNum() {
		return lEntryNum;
	}
	public void setLEntryNum(int entryNum) {
		lEntryNum = entryNum;
	}
	public long getLCoupletNo() {
		return lCoupletNo;
	}
	public void setLCoupletNo(long coupletNo) {
		lCoupletNo = coupletNo;
	}
	public String getStrBillName() {
		return strBillName;
	}
	public void setStrBillName(String strBillName) {
		this.strBillName = strBillName;
	}	
	
}
