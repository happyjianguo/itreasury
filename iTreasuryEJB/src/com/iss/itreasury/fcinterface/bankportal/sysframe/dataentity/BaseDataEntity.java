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
	
	/** ��ȡID�ĳ��󷽷�����������ʵ�֣�DAO��ͨ�ò�����ʹ�ã�û��ID�����ݿ��ʹ�� */
	public abstract long getId();

	/** ����ID�ĳ��󷽷�����������ʵ�֣�DAO��ͨ�ò�����ʹ�ã�û��ID�����ݿ��ʹ�� */
	public abstract void setId(long id);

	/**
	 * ��¼��ʹ�õ�DataEntity�е��ֶΣ������е�setXXX�����б���(!!)�������²�����
	 * usedFields.put("FieldName", fieldValue); ����DAO��updateʱֻ�����Ҫ���µ����ݽ��и���
	 */
	protected HashMap usedFieldMap = new HashMap();
	
	/**
	 * ���ӶԶ���´���֧�� 2006-10-01 chengli
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

	//�Ƿ��ܲ�
	public  boolean isHQ()
	{
		return Env.isHQ(officeID);
	}
	/**
	 * ��ȡ���б����ù��ĳ�Ա���Ƽ���ֵ
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
	 * ��ȡ���б����ù��ĳ�Ա����
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
	 * put��ʹ�õĳ�Ա��������ʹ�ñ�
	 * 
	 * @param ������
	 * @param long�ͱ���ֵ
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
	 * �����ʹ�ñ���
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