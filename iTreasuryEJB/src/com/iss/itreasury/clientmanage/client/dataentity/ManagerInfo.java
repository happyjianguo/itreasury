package com.iss.itreasury.clientmanage.client.dataentity;

public class ManagerInfo {
	
	public ManagerInfo()
	{
		
	}
	private String mail = ""; // ����������
	private String managername = ""; // ����������
	private String position = ""; // ������ְ��
	private long id=-1;           //������ID
	private String code = "";  //�ͻ����
	private String name = ""; // �ͻ�����
	private String tel ="";   //�����߰칫�绰
	private long clientid=-1;  //�ͻ�id
	private String beizhu="";  //��ע
	private long client_info_id=-1;
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public long getClientid() {
		return clientid;
	}
	public void setClientid(long clientid) {
		this.clientid = clientid;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public long getClient_info_id() {
		return client_info_id;
	}
	public void setClient_info_id(long client_info_id) {
		this.client_info_id = client_info_id;
	}
	
}
