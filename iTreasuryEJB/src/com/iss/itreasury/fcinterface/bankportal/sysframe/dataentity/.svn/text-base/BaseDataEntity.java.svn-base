/**
 * Title:        		BankPortal
 * Description:         
 * Copyright:           Copyright (c) 2005
 * Company:             iSoftStone
 * @author              mxzhou 
 * @version
 * Date of Creation     2005-5-10
 */

package com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import com.iss.itreasury.fcinterface.bankportal.constant.DataType;
import com.iss.itreasury.fcinterface.bankportal.util.Env;

/**
 * @author mxzhou TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class BaseDataEntity extends BaseBean
{
	
	/** 获取ID的抽象方法，在子类中实现，DAO的通用操作中使用，没有ID的数据库表不使用 */
	public abstract long getId();

	/** 设置ID的抽象方法，在子类中实现，DAO的通用操作中使用，没有ID的数据库表不使用 */
	public abstract void setId(long id);

	/**
	 * 记录被使用的DataEntity中的字段，在所有的setXXX函数中必须(!!)增加以下操作：
	 * usedFields.put("FieldName", fieldValue); 便于DAO中update时只针对需要更新的数据进行更新
	 */
	protected HashMap usedFieldMap = new HashMap();
	
	/**
	 * 增加对多办事处的支持 2006-10-01 chengli
	 */
    private long officeID=-1;
    
    
	public long getOfficeID()
	{
		return officeID;
	}

	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
		putUsedField("officeid", this.officeID);
	}

	//是否总部
	public  boolean isHQ()
	{
		return Env.isHQ(officeID);
	}
	/**
	 * 获取所有被设置过的成员名称及其值
	 * 
	 * @param
	 * @param
	 * @return Hashtable
	 * @throws
	 */
	final public HashMap getUsedFieldMap()
	{
		return usedFieldMap;
	}
	
	/**
	 * 获取所有被设置过的成员名称
	 * 
	 * @param
	 * @param
	 * @return Hashtable
	 * @throws
	 */
	final public String[] getUsedField()
	{
		String[] fields = null;
		HashMap hashMap = this.getUsedFieldMap();
		Set fieldNames = hashMap.keySet();
		if(fieldNames.size()>0)
		{			
			fields = (String[])fieldNames.toArray(new String[0]);
		}
		return fields;
	}

	/**
	 * put被使用的成员变量到已使用表
	 * 
	 * @param 变量名
	 * @param long型变量值
	 * @return @throws
	 */
	protected void putUsedField(String key, long value)
	{
		usedFieldMap.put(DataType.getPrefix(DataType.LONG)+key.trim(),
				new Object[]{Long.class.getName(), new Long(value)});
	}
	protected void putUsedField(String key, double value)
	{
		usedFieldMap.put(DataType.getPrefix(DataType.DOUBLE)+key.trim(),
				new Object[]{Double.class.getName(), new Double(value)});
	}
	protected void putUsedField(String key, String value)
	{
		usedFieldMap.put(DataType.getPrefix(DataType.STRING)+key.trim(),
				new Object[]{String.class.getName(), value});
	}
	protected void putUsedField(String key, Date value)
	{
		usedFieldMap.put(DataType.getPrefix(DataType.DATE)+key.trim(),
				new Object[]{Date.class.getName(), value});
	}

	/**
	 * 清除已使用变量
	 * 
	 * @param
	 * @param
	 * @return @throws
	 */
	public void clearUsedField()
	{
		if (usedFieldMap != null)
		{
			usedFieldMap.clear();
		}
	}
}