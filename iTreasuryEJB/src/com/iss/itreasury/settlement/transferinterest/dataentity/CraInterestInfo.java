package com.iss.itreasury.settlement.transferinterest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author xwhe
 *
 * ��Ϣ���ý���ҳ���ѯ������Ϣʵ�塣
 * ������Ҫ��������ҳ��Ĳ�ѯ����������Ϊ��ѯDao�ķ���������
 * ��ʵ���������JavaBean�Ĺ淶���Ա�ʵ�ִ�JSPҳ�浽ʵ����Զ���ֵ�Ĺ��ܣ�
 * ��Ӧ�ó���Ŀ������̡�
 * Ϊ����һ��ҳ����ʾ����Ҫ����ʵ����ͬʱ�������ݿ������Ҫ�Ĺؼ��ֶ�ֵ�����磺ID,
 * ��ҳ����ʾ�����ݣ����磺No��
 */
public class CraInterestInfo implements Serializable{
	private long OfficeID = -1;	                    //���´�ID
	private long CurrencyID = -1;	                //����ID
	private long UserID  = -1 ;                     //������
	private long lCraContractIDFrom = -1;           //ת�ú�ͬ ID (��ʼ)
    private String strCraContractNoFrom = "";       //ת�ú�ͬ���(��ʼ)
    private long lCraContractIDTo = -1;             //ת�ú�ͬ ID (����)
    private String strCraContractNoTo = "";         //ת�ú�ͬ���(����)
    private Timestamp dtClearInterest = null;       //��Ϣ��
    private long lCraCounterID = -1;	            //���׶���ID
    private String strCraCounterCode = "";          //���׶��ֱ��
    private long lCraContractTypeId = -1;           //ת�ú�ͬ����
	public long getCurrencyID() {
		return CurrencyID;
	}
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	public Timestamp getDtClearInterest() {
		return dtClearInterest;
	}
	public void setDtClearInterest(Timestamp dtClearInterest) {
		this.dtClearInterest = dtClearInterest;
	}
	public long getLCraContractIDFrom() {
		return lCraContractIDFrom;
	}
	public void setLCraContractIDFrom(long craContractIDFrom) {
		lCraContractIDFrom = craContractIDFrom;
	}
	public long getLCraContractIDTo() {
		return lCraContractIDTo;
	}
	public void setLCraContractIDTo(long craContractIDTo) {
		lCraContractIDTo = craContractIDTo;
	}
	public long getLCraContractTypeId() {
		return lCraContractTypeId;
	}
	public void setLCraContractTypeId(long craContractTypeId) {
		lCraContractTypeId = craContractTypeId;
	}
	public long getLCraCounterID() {
		return lCraCounterID;
	}
	public void setLCraCounterID(long craCounterID) {
		lCraCounterID = craCounterID;
	}
	public long getOfficeID() {
		return OfficeID;
	}
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	public String getStrCraContractNoFrom() {
		return strCraContractNoFrom;
	}
	public void setStrCraContractNoFrom(String strCraContractNoFrom) {
		this.strCraContractNoFrom = strCraContractNoFrom;
	}
	public String getStrCraContractNoTo() {
		return strCraContractNoTo;
	}
	public void setStrCraContractNoTo(String strCraContractNoTo) {
		this.strCraContractNoTo = strCraContractNoTo;
	}
	public String getStrCraCounterCode() {
		return strCraCounterCode;
	}
	public void setStrCraCounterCode(String strCraCounterCode) {
		this.strCraCounterCode = strCraCounterCode;
	}
	public long getUserID() {
		return UserID;
	}
	public void setUserID(long userID) {
		UserID = userID;
	}

}
