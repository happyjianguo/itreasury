/*
 * Created on 2004-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.util;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author yiwang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessageQueueSender
{

	private MessageQueueSender()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 通用的消息对象发送方法。
	 * <br>实现消息对象的发送，无论是什么对象只要实现了序列化接口都可以通过该方法发送。
	 * <br>至于由什么系统发送给哪个系统，由外部使用者决定，在该方法中对此并无任何限制。
	 * 
	 * @param sendInfo
	 * @throws Exception
	 */
	public static void sendObject(Serializable sendInfo) throws Exception
	{
		Context jndiContext = null;
		QueueConnectionFactory queueConnectionFactory = null;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		Queue queue = null;
		QueueSender queueSender = null;
		ObjectMessage message = null;
		final int NUM_MSGS = 1;

		// 以下发送消息bean, 不能再有任何数据库jdbc或EJB的调用。
		System.out.println(" ===== Message queue is sending ....pls wait.");
		try
		{
			jndiContext = new InitialContext();
		}
		catch (NamingException e)
		{
			System.out.println(
				"Could not create JNDI " + "context: " + e.toString());
			throw new Exception();
		}

		try
		{
			queueConnectionFactory =
				(QueueConnectionFactory) jndiContext.lookup(
					"java:comp/env/jms/MyQcf");
			queue = (Queue) jndiContext.lookup("java:comp/env/jms/MyQueue");
		}
		catch (NamingException e)
		{
			System.out.println("JNDI lookup failed: " + e.toString());
			throw new Exception();
		}

		try
		{
			queueConnection = queueConnectionFactory.createQueueConnection();
			queueSession =
				queueConnection.createQueueSession(
					false,
					Session.AUTO_ACKNOWLEDGE);
			queueSender = queueSession.createSender(queue);
			message = queueSession.createObjectMessage();

			message.setObject(sendInfo);

			queueSender.send(message);

			System.out.println(" Message queue had been sent.");

		}
		catch (Throwable e)
		{
			System.out.println("Exception occurred: " + e.toString());
			throw new Exception();
		}
		finally
		{
			if (queueConnection != null)
			{
				try
				{
					queueSender.close();
					queueSession.close();
					queueConnection.close();

					System.out.println("closed queueConnection resources");
				}
				catch (JMSException e)
				{
				}
			} // if

		} // finally
	}
	
	public static void main(String[] args)
	{
		MessageQueueSender instrQueueSender = new MessageQueueSender();
		try
		{
			//instrQueueSender.mapFinanceInfoToTransCurrentDepositInfo(1);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}