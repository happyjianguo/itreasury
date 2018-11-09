package com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity;
/**
 * fszhu
 * 2008-11-27
 * 
 */
import java.util.Date;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;
 
public class AreaCodeInfo extends BaseDataEntity {

	
	private String areaCode;             //地区码
	private String areaCity;             //县级名称
	private String areaLevel;            //级别名称（地级、县级）
	private String areaName;             //市级名称
	private String areaProvince;         //省级名称
	private long id;                     //
		
	private Date inputTime;              //录入时间 
	private long inputUserId;            //录入人Id
	private Date modifyTime;             //修改时间
	private long modifyUserId;           //修改人Id
	private String inputUserName;        //录入人姓名
	private String modifyUserName;       //修改人姓名
	
	public Date getInputTime() 
	{
		return inputTime;
	}
	public void setInputTime(Date inputTime) 
	{
		this.inputTime = inputTime;
		putUsedField("inputTime", this.inputTime);
	}
	public long getInputUserId() 
	{
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) 
	{
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", this.inputUserId);
	}
	public Date getModifyTime() 
	{
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) 
	{
		this.modifyTime = modifyTime;
		putUsedField("modifyTime", this.modifyTime);
	}
	public long getModifyUserId() 
	{
		return modifyUserId;
	}
	public void setModifyUserId(long modifyUserId) 
	{
		this.modifyUserId = modifyUserId;
		putUsedField("modifyUserId", this.modifyUserId);
	}
	public String getAreaCode() 
	{
		return areaCode;
	}
	public void setAreaCode(String areaCode) 
	{
		this.areaCode = areaCode;
		putUsedField("areaCode", this.areaCode);
	}	
	public String getAreaCity() 
	{
		return areaCity;
	}
	public void setAreaCity(String areaCity) 
	{
		this.areaCity = areaCity;
		putUsedField("areaCity", this.areaCity);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
		putUsedField("id",this.id);
	}
	public String getAreaName() 
	{
		return areaName;
	}
	public void setAreaName(String areaName) 
	{
		this.areaName = areaName;
		putUsedField("areaName",this.areaName);
	}
	public String getAreaProvince() 
	{
		return areaProvince;
	}
	public void setAreaProvince(String areaProvince) 
	{
		this.areaProvince = areaProvince;
		putUsedField("areaProvince",this.areaProvince);
	}
	public String getAreaLevel() 
	{
		return areaLevel;
	}
	public void setAreaLevel(String areaLevel) 
	{
		this.areaLevel = areaLevel;
		putUsedField("areaLevel",this.areaLevel);
	}
	public String getInputUserName() 
	{
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) 
	{
		this.inputUserName = inputUserName;
	}
	public String getModifyUserName() 
	{
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) 
	{
		this.modifyUserName = modifyUserName;
	}
	
}
