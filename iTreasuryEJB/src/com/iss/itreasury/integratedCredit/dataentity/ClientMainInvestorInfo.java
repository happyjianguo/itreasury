package com.iss.itreasury.integratedCredit.dataentity;

/**
 * �ͻ���ҪͶ���˱�
 * �������ڣ�(2009-11-25)
 * @author��������
 */

public class ClientMainInvestorInfo implements java.io.Serializable
{
	public ClientMainInvestorInfo()
	{
		super();
	}
	
	private long id=0;
	private long clientid=0;  //�ͻ�id
	private long invertId=0;  //Ͷ����id
	private String smainInvestorName="";//Ͷ��������
	private long nactualInvestment=0;//ʵ��Ͷ�ʽ��
	private double npaidCapitalRate=0;//ռ��Ͷ�ʵı���
	private String sabstract="";//��ע
	private long nstatusid=0;//״̬
	private long nofficeid=0;//�칫id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getClientid() {
		return clientid;
	}
	public void setClientid(long clientid) {
		this.clientid = clientid;
	}
	public long getInvertId() {
		return invertId;
	}
	public void setInvertId(long invertId) {
		this.invertId = invertId;
	}
	public String getSmainInvestorName() {
		return smainInvestorName;
	}
	public void setSmainInvestorName(String smainInvestorName) {
		this.smainInvestorName = smainInvestorName;
	}
	public long getNactualInvestment() {
		return nactualInvestment;
	}
	public void setNactualInvestment(long nactualInvestment) {
		this.nactualInvestment = nactualInvestment;
	}
	public double getNpaidCapitalRate() {
		return npaidCapitalRate;
	}
	public void setNpaidCapitalRate(double npaidCapitalRate) {
		this.npaidCapitalRate = npaidCapitalRate;
	}
	public String getSabstract() {
		return sabstract;
	}
	public void setSabstract(String sabstract) {
		this.sabstract = sabstract;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}
	
}
