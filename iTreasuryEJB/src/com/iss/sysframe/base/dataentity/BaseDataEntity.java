/**
 * Created on 2009-05-15
 */

package com.iss.sysframe.base.dataentity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;


/**
 * @author xintan
 *
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
		usedFieldMap.put(key.trim(),new Object[]{Long.class.getName(), new Long(value)});
	}
	protected void putUsedField(String key, double value)
	{
		usedFieldMap.put(key.trim(),new Object[]{Double.class.getName(), new Double(value)});
	}
	protected void putUsedField(String key, String value)
	{
		usedFieldMap.put(key.trim(),new Object[]{String.class.getName(), value});
	}
	protected void putUsedField(String key, Date value)
	{
		usedFieldMap.put(key.trim(),new Object[]{Date.class.getName(), value});
	}
	protected void putUsedField(String key, Timestamp value)
	{
		usedFieldMap.put(key.trim(),new Object[]{Timestamp.class.getName(), value});
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