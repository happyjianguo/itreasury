package com.iss.itreasury.evoucher.setting.dataentity;

import java.io.Serializable;
import java.util.Date;

import com.iss.itreasury.evoucher.base.VoucherBaseDataEntity;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class ClientRightEntity extends BaseDataEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id = -1;

	// ���´�id
	private long nofficeid = -1;

	// �ͻ�id
	private long nclientid = -1;

	private String clientName = "";
	
	private String clientCode=""; //�ͻ���� ��
	
	private String clientCode2="";//�ͻ���� ��
	// �Ƿ���Ȩ
	private long nissignature = 0;

	private long startClientNo = -1;

	private long endClientNo = -1;

	// ¼��ʱ��
	private Date dtinputdate;

	// ¼����
	private String inputuserid = "";

	private String nofficename = "";

	private String startClientCode="";//��ѯ����ʼ�ͻ����
	
	private String endClientCode="";//��ֹ�ͻ����
	
	
	
	public String getEndClientCode() {
		return endClientCode;
	}

	public void setEndClientCode(String endClientCode) {
		this.endClientCode = endClientCode;
	}

	public String getStartClientCode() {
		return startClientCode;
	}

	public void setStartClientCode(String startClientCode) {
		this.startClientCode = startClientCode;
	}

	public String getNofficename() {
		return nofficename;
	}

	public void setNofficename(String nofficename) {
		this.nofficename = nofficename;
	}

	public Date getDtinputdate() {
		return dtinputdate;
	}

	public void setDtinputdate(Date dtinputdate) {
		this.dtinputdate = dtinputdate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(String inputuserid) {
		this.inputuserid = inputuserid;
	}

	public long getNclientid() {
		return nclientid;
	}

	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
	}

	public long getNissignature() {
		return nissignature;
	}

	public void setNissignature(long nissignature) {
		this.nissignature = nissignature;
	}

	public long getNofficeid() {
		return nofficeid;
	}

	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}

	public long getEndClientNo() {
		return endClientNo;
	}

	public void setEndClientNo(long endClientNo) {
		this.endClientNo = endClientNo;
	}

	public long getStartClientNo() {
		return startClientNo;
	}

	public void setStartClientNo(long startClientNo) {
		this.startClientNo = startClientNo;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientCode2() {
		return clientCode2;
	}

	public void setClientCode2(String clientCode2) {
		this.clientCode2 = clientCode2;
	}

}
