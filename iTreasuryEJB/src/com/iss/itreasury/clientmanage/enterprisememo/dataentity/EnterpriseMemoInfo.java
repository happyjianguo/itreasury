/* Generated by Together */

package com.iss.itreasury.clientmanage.enterprisememo.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;
import com.iss.itreasury.util.DataFormat;

/**
*企业大事记信息DataEntity，对应与表Client_EnterpriseMemo
* */
public class EnterpriseMemoInfo extends CimsBaseDataEntity {
	private long clientID=-1;//客户id
	private String eventRecordNo="";//大事件编号
	private Timestamp eventDate;//发生日期
	private String memoDescribe="";//大事描述
	private String Abstract="";//备注
	private long count = -1;//总计	
	public long statusID=-1;//状态
	private String code="";//客户编号（企业大事件新增）
	private String name="";//客户名称（企业大事件新增）
	private String describedetail="";//重大事件详述（企业大事件新增）
	private long client_info_id=-1;
	
	

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		putUsedField("statusID",statusID);
		this.statusID = statusID;
	}

	/**
	 * @return Returns the count.
	 */
	public long getCount() {
		return count;
	}
	/**
	 * @param count The count to set.
	 */
	public void setCount(long count) {
		this.count = count;
	}
	public String getAbstract() {
		return Abstract;
	}
	
	public void setAbstract(String abstract1) {
		putUsedField("Abstract",abstract1);
		Abstract = abstract1;
	}
	
	public long getClientID() {
		return clientID;
	}
	
	public void setClientID(long clientID) {
		putUsedField("clientID",clientID);
		this.clientID = clientID;
	}
	
	public Timestamp getEventDate() {
		return eventDate;
	}
	 public String getFormatEventDate()
		{
			return DataFormat.getDateString(eventDate);
		}
	
	public void setEventDate(Timestamp eventDate) {
		putUsedField("eventDate",eventDate);
		this.eventDate = eventDate;
	}

	public String getEventRecordNo() {
		return eventRecordNo;
	}
	
	public void setEventRecordNo(String eventRecordNo) {
		putUsedField("eventRecordNo",eventRecordNo);
		this.eventRecordNo = eventRecordNo;
	}

	
	public String getMemoDescribe() {
		return memoDescribe;
	}
	
	public void setMemoDescribe(String memoDescribe) {
		putUsedField("memoDescribe",memoDescribe);
		this.memoDescribe = memoDescribe;
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

	public String getDescribedetail() {
		return describedetail;
	}

	public void setDescribedetail(String describedetail) {
		putUsedField("describedetail",describedetail);
		this.describedetail = describedetail;
	}

	public long getClient_info_id() {
		return client_info_id;
	}

	public void setClient_info_id(long client_info_id) {
		this.client_info_id = client_info_id;
	}
}
