package com.iss.itreasury.settlement.integration.client.info;

import java.util.Date;
/**
 * 
 * @author zpli
 *
 */
public class SystemStatusInfo implements java.io.Serializable  {
	private int status=-1;//1������״̬ 	2���ػ������ڹػ�  	0����ƥ���¼

	private Date openSystemDate=null;
	
	
	public Date getOpenSystemDate() {
		return openSystemDate;
	}	
	public void setOpenSystemDate(Date openSystemDate) {
		this.openSystemDate = openSystemDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
