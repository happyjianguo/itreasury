/*
 * Created on 2006-12-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.approvaltran.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.evoucher.setting.dataentity.PRINTBaseDataEntity;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Evo_ApprovalApplyInfo extends PRINTBaseDataEntity
{
	//��ӡ�����ѯ
	private long id = -1;
	private long nofficeid = -1;  //�������´�ID
	private long ncurrency = -1;  //����ID
	private long nprintcontentid = -1;  //����ID
	private String nprintcontentno = "";  //���ױ��
	private long ndeptid = -1;  //��ӡ����ID
	private long nbillid = -1;  //����ID
	private long ntempid = -1;  //����ģ��ID
	private long nstatusid = -1;  //״̬
	private long ischapter = -1;  //�Ƿ�ǩ��
	private long nclientid = -1;  //��Ա��λ��ʶ
	private long ninputuserid = -1;  //������
	private Timestamp ninputdate = null;  //����ʱ��
	private long ntypeid = -1;  //ҵ������
	private double receiveamount = 0.0;  //���׽��տ��
	private String username = "";  //�û�����
	private String clientname = "";  //�ͻ�����
	private String billname = "";  //��������
	private String tempname = "";  //ģ������
	private long coupletno = -1;  //ģ����������
	private long countclient = -1;  //�ͻ�����
	
	/**
	 * @return Returns the countclient.
	 */
	public long getCountclient() {
		return countclient;
	}
	/**
	 * @param countclient The countclient to set.
	 */
	public void setCountclient(long countclient) {
		this.countclient = countclient;
	}
	/**
	 * @return Returns the billname.
	 */
	public String getBillname() {
		return billname;
	}
	/**
	 * @param billname The billname to set.
	 */
	public void setBillname(String billname) {
		this.billname = billname;
	}
	/**
	 * @return Returns the coupletno.
	 */
	public long getCoupletno() {
		return coupletno;
	}
	/**
	 * @param coupletno The coupletno to set.
	 */
	public void setCoupletno(long coupletno) {
		this.coupletno = coupletno;
	}
	/**
	 * @return Returns the tempname.
	 */
	public String getTempname() {
		return tempname;
	}
	/**
	 * @param tempname The tempname to set.
	 */
	public void setTempname(String tempname) {
		this.tempname = tempname;
	}
	/**
	 * @return Returns the clientname.
	 */
	public String getClientname() {
		return clientname;
	}
	/**
	 * @param clientname The clientname to set.
	 */
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return Returns the receiveamount.
	 */
	public double getReceiveamount() {
		return receiveamount;
	}
	/**
	 * @param receiveamount The receiveamount to set.
	 */
	public void setReceiveamount(double receiveamount) {
		this.receiveamount = receiveamount;
	}
	/**
	 * @return Returns the nprintcontentno.
	 */
	public String getNprintcontentno() {
		return nprintcontentno;
	}
	/**
	 * @param nprintcontentno The nprintcontentno to set.
	 */
	public void setNprintcontentno(String nprintcontentno) {
		this.nprintcontentno = nprintcontentno;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the ischapter.
	 */
	public long getIschapter() {
		return ischapter;
	}
	/**
	 * @param ischapter The ischapter to set.
	 */
	public void setIschapter(long ischapter) {
		this.ischapter = ischapter;
	}
	/**
	 * @return Returns the nbillid.
	 */
	public long getNbillid() {
		return nbillid;
	}
	/**
	 * @param nbillid The nbillid to set.
	 */
	public void setNbillid(long nbillid) {
		this.nbillid = nbillid;
	}
	/**
	 * @return Returns the nclientid.
	 */
	public long getNclientid() {
		return nclientid;
	}
	/**
	 * @param nclientid The nclientid to set.
	 */
	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
	}
	/**
	 * @return Returns the ncurrency.
	 */
	public long getNcurrency() {
		return ncurrency;
	}
	/**
	 * @param ncurrency The ncurrency to set.
	 */
	public void setNcurrency(long ncurrency) {
		this.ncurrency = ncurrency;
	}
	/**
	 * @return Returns the ndeptid.
	 */
	public long getNdeptid() {
		return ndeptid;
	}
	/**
	 * @param ndeptid The ndeptid to set.
	 */
	public void setNdeptid(long ndeptid) {
		this.ndeptid = ndeptid;
	}
	/**
	 * @return Returns the ninputdate.
	 */
	public Timestamp getNinputdate() {
		return ninputdate;
	}
	/**
	 * @param ninputdate The ninputdate to set.
	 */
	public void setNinputdate(Timestamp ninputdate) {
		this.ninputdate = ninputdate;
	}
	/**
	 * @return Returns the ninputuserid.
	 */
	public long getNinputuserid() {
		return ninputuserid;
	}
	/**
	 * @param ninputuserid The ninputuserid to set.
	 */
	public void setNinputuserid(long ninputuserid) {
		this.ninputuserid = ninputuserid;
	}
	/**
	 * @return Returns the nofficeid.
	 */
	public long getNofficeid() {
		return nofficeid;
	}
	/**
	 * @param nofficeid The nofficeid to set.
	 */
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}
	/**
	 * @return Returns the nprintcontentid.
	 */
	public long getNprintcontentid() {
		return nprintcontentid;
	}
	/**
	 * @param nprintcontentid The nprintcontentid to set.
	 */
	public void setNprintcontentid(long nprintcontentid) {
		this.nprintcontentid = nprintcontentid;
	}
	/**
	 * @return Returns the nstatusid.
	 */
	public long getNstatusid() {
		return nstatusid;
	}
	/**
	 * @param nstatusid The nstatusid to set.
	 */
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	/**
	 * @return Returns the ntempid.
	 */
	public long getNtempid() {
		return ntempid;
	}
	/**
	 * @param ntempid The ntempid to set.
	 */
	public void setNtempid(long ntempid) {
		this.ntempid = ntempid;
	}
	/**
	 * @return Returns the ntypeid.
	 */
	public long getNtypeid() {
		return ntypeid;
	}
	/**
	 * @param ntypeid The ntypeid to set.
	 */
	public void setNtypeid(long ntypeid) {
		this.ntypeid = ntypeid;
	}
}
