package com.iss.itreasury.configtool.configmanage.dataentity;

public class ConfigTableInfo implements java.io.Serializable
{
	private String name="";  //配置项表名称
	private String tablename="";  //配置项对应英文名称
	private String desc="";  //描述
	private String vale="";  //配置项的值
	
	public String getDesc()
	{
		return desc;
	}
	
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getTablename()
	{
		return tablename;
	}
	
	public void setTablename(String tablename)
	{
		this.tablename = tablename;
	}
	
	public String getVale()
	{
		return vale;
	}
	
	public void setVale(String vale)
	{
		this.vale = vale;
	}
}
