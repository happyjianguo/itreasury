/*
 * Created on 2006-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.print.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintrecordInfo extends ITreasuryBaseDataEntity
{
	private long id = -1;
	private long nofficeid = -1;
	private long ncurrency = -1;
	private long nclientid = -1;
	private long nprintcontentid = -1;
	private long ndeptid = -1;
	private long nbillid = -1;
	private long ntempid = -1;
	private long nprintnum = -1;
	private Timestamp ninputdate = null;
	private long ntransactiontypeid = -1;
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
	 * @return Returns the nprintnum.
	 */
	public long getNprintnum() {
		return nprintnum;
	}
	/**
	 * @param nprintnum The nprintnum to set.
	 */
	public void setNprintnum(long nprintnum) {
		this.nprintnum = nprintnum;
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
	 * @return Returns the ntransactiontypeid.
	 */
	public long getNtransactiontypeid() {
		return ntransactiontypeid;
	}
	/**
	 * @param ntransactiontypeid The ntransactiontypeid to set.
	 */
	public void setNtransactiontypeid(long ntransactiontypeid) {
		this.ntransactiontypeid = ntransactiontypeid;
	}
}
