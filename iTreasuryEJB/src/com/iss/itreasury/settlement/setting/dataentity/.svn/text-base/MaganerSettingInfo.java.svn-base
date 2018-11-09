package com.iss.itreasury.settlement.setting.dataentity;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * 经理查询设置实体类
 * @author gqfang
 */
public class MaganerSettingInfo extends SettlementBaseDataEntity
{

	long	id			= -1;	// PK

	String	itemUrl		= null;	// 查询功能点的地址

	String	itemName	= null;	// 查询功能点的名称

	long	moduleId	= -1;	// 查询功能点所属的模块

	long	statusId	= -1;	// 0表示无效设置；1表示有效设置


	public long getId()
	{

		return id;
	}


	public void setId(long id)
	{

		this.id = id;
		putUsedField("id",id);
	}


	public String getItemName()
	{

		return itemName;
	}


	public void setItemName(String itemName)
	{

		this.itemName = itemName;
		putUsedField("itemName",itemName);
	}


	public String getItemUrl()
	{

		return itemUrl;
	}


	public void setItemUrl(String itemUrl)
	{

		this.itemUrl = itemUrl;
		putUsedField("itemUrl",itemUrl);
	}


	public long getModuleId()
	{

		return moduleId;
	}


	public void setModuleId(long moduleId)
	{

		this.moduleId = moduleId;
		putUsedField("moduleId",moduleId);
	}


	public long getStatusId()
	{

		return statusId;
	}


	public void setStatusId(long statusId)
	{

		this.statusId = statusId;
		putUsedField("statusId",statusId);
	}
}