package com.iss.itreasury.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * 用于保存提示信息
 * @author rongyang
 */
public class ActionMessages implements Serializable
{
	/**
	 * 存储所有提示信息
	 */
	private Collection m_collMessages = new ArrayList(8);
	/**
	 * ActionException默认构造方法。
	 * @see java.lang.Object#Object()
	 */
	public ActionMessages()
	{
		super();
	}

	/**
	 * 用ActionException异常构造一个对象
	 * @param actionException
	 */
	public ActionMessages(Exception exception)
	{
		String strTemp = IExceptionMessage.getExceptionMSG(exception);
		if (strTemp != null && !"".equals(strTemp))
		{
			this.m_collMessages.add(strTemp);
		}
	}
	/**
	 * 构造一个指定的信息ActionMessages。
	 * @param message 信息。
	 */
	public ActionMessages(String message)
	{
		this.m_collMessages.add(message);
	}

	/**
	 * 向当前对象中添加一条信息
	 * @param messageKey
	 */
	public void addMessage(String message)
	{
		this.m_collMessages.add(message);
	}

	/**
	 * 将一个异常对象中的消息添加到当前对象中
	 * @param actionException
	 */
	public void addMessage(Exception exception)
	{
		if (exception != null)
		{
			String strTemp = IExceptionMessage.getExceptionMSG(exception);
			if (strTemp != null && !"".equals(strTemp))
			{
				this.m_collMessages.add(strTemp);
			}
		}
	}

	/**
	 * 获得所有信息
	 * @return Enumeration
	 */
	public Collection getMessages()
	{
		return this.m_collMessages;
	}

	/**
	 * 判断当前ActionError对象中是否有错误信息。
	 * @return boolean
	 */
	public boolean isEmpty()
	{
		return this.m_collMessages.isEmpty();
	}

	/**
	 * 清空当前对象中的消息
	 */
	public void clear()
	{
		this.m_collMessages.clear();
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer(128);

		Iterator itTemp = this.m_collMessages.iterator();

		while (itTemp.hasNext())
		{
			sb.append((String) itTemp.next());
		}

		return sb.toString();
	}

}
