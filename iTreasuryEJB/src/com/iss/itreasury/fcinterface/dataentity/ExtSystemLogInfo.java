package com.iss.itreasury.fcinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 外部系统指令接收日志
 * @author xiangzhou
 *
 */
public class ExtSystemLogInfo extends ITreasuryBaseDataEntity implements Serializable{
	
	private long 	id  = -1;				//id
	private long 	source  = -1;			//数据来源
	private String  applycode="";			//申请指令编号
	private long 	nstatus = -1;			//状态
	private String  remark = "";			//备注
	private Timestamp createtime = null;	//创建日期
 	
	//指令状态查询用
	private String extSystemCode = "";	//外部系统code
	private long bankStatus = -1;		//银企状态
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getApplycode() {
		return applycode;
	}
	public void setApplycode(String applycode) {
		this.applycode = applycode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getNstatus() {
		return nstatus;
	}
	public void setNstatus(long nstatus) {
		this.nstatus = nstatus;
	}
	public long getSource() {
		return source;
	}
	public void setSource(long source) {
		this.source = source;
	}
	public String getExtSystemCode() {
		return extSystemCode;
	}
	public void setExtSystemCode(String extSystemCode) {
		this.extSystemCode = extSystemCode;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public long getBankStatus() {
		return bankStatus;
	}
	public void setBankStatus(long bankStatus) {
		this.bankStatus = bankStatus;
	}

}
