package com.iss.itreasury.util;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;


import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
/**
 * EJB Home Factory, maintains a simple hashmap cache of EJBHomes
 * For a production implementations, exceptions such as NamingException
 * can be wrapped with a factory exception to futher simplify
 * the client.
 */
public class EJBHomeFactory
{
	private Map ejbHomes;
	private static EJBHomeFactory aFactorySingleton;
	Context ctx;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * EJBHomeFactory private constructor.
	 */
	private EJBHomeFactory() throws NamingException
	{
		try
		{
			ctx = new InitialContext();
			this.ejbHomes = Collections.synchronizedMap(new HashMap());
		}
		catch (Exception e)
		{
			//can't do anything about this
			//client will catch errors upon trying to
			//do a lookup
		}
	}
	/*
	 * Returns the singleton instance of the EJBHomeFactory
	 * The sychronized keyword is intentionally left out the
	 * as I don't think the potential to intialize the singleton
	 * twice at startup time (which is not a destructive event)
	 * is worth creating a sychronization bottleneck on this
	 * VERY frequently used class, for the lifetime of the
	 * client application.
	 */
	public static EJBHomeFactory getFactory() throws IException
	{
		try
		{
			if (EJBHomeFactory.aFactorySingleton == null)
			{
				EJBHomeFactory.aFactorySingleton = new EJBHomeFactory();
			}
		}
		catch (NamingException e)
		{
			throw new IException("EJBHomeFactory NamingException", e);
		}
		return EJBHomeFactory.aFactorySingleton;
	}
	/**
	 * Lookup and cache an EJBHome object using a home class.
	 * Assumes that the JNDI name of the EJB Home being looked for
	 * is the same as the fully qualified class name of the
	 * same EJB Home.
	 * If EJB-REF tags are being used externally, then the classname
	 * of the EJB Home can be mapped to the actual JNDI name of the
	 * deployed bean transaprently at deployment time.
	 * If EJB-REF tags are not used, then the EJB's must be deployed
	 * with JNDI names equal to their fully qualified home interfaces.
	 */
	public EJBHome lookUpHome(Class homeClass) throws IException
	{
		EJBHome anEJBHome;
		String jndiName = "";
		anEJBHome = (EJBHome) this.ejbHomes.get(homeClass);
		try
		{
			String ResourceReference = homeClass.getName().substring(homeClass.getName().lastIndexOf(".") + 1, homeClass.getName().length());
			if (anEJBHome == null)
			{
				anEJBHome = EJBObject.getEJBHome(ResourceReference);
				//anEJBHome = (EJBHome) PortableRemoteObject.narrow(ctx.lookup(jndiName), AccountHome.class);

				this.ejbHomes.put(homeClass, anEJBHome);
				log.info(ResourceReference + " lookup successfully.");
			}
		}
		catch (ClassCastException e)
		{
			throw new IException("EJBHomeFactory ClassCastException", e);
		}
		catch (NamingException e)
		{
			throw new IException("EJBHomeFactory ClassCastException", e);
		}
		catch (Exception e)
		{
			Log.print(jndiName + " home not found - " + "Is bean registered with JNDI?: " + e.toString());
		}
		return anEJBHome;
	}
	/**
	 * Lookup and cache an EJBHome object using a home class.
	 * Assumes that the JNDI name of the EJB Home being looked for
	 * is the same as the fully qualified class name of the
	 * same EJB Home.
	 * If EJB-REF tags are being used externally, then the classname
	 * of the EJB Home can be mapped to the actual JNDI name of the
	 * deployed bean transaprently at deployment time.
	 * If EJB-REF tags are not used, then the EJB's must be deployed
	 * with JNDI names equal to their fully qualified home interfaces.
	 */
	public EJBHome lookUpHome(Class homeClass,String EJBRefName) throws IException
	{
		EJBHome anEJBHome;
		String jndiName = "";
		anEJBHome = (EJBHome) this.ejbHomes.get(homeClass);
		try
		{
			String ResourceReference = EJBRefName;
			if (anEJBHome == null)
			{
				anEJBHome = EJBObject.getEJBHome(ResourceReference);
				//anEJBHome = (EJBHome) PortableRemoteObject.narrow(ctx.lookup(jndiName), AccountHome.class);

				this.ejbHomes.put(homeClass, anEJBHome);
				log.info(ResourceReference + " lookup successfully.");
			}
		}
		catch (ClassCastException e)
		{
			throw new IException("EJBHomeFactory ClassCastException", e);
		}
		catch (NamingException e)
		{
			throw new IException("EJBHomeFactory ClassCastException", e);
		}
		catch (Exception e)
		{
			Log.print(jndiName + " home not found - " + "Is bean registered with JNDI?: " + e.toString());
		}
		return anEJBHome;
	}	
//	/**
//	 * Lookup and cache an EJBHome object.
//	 * This 'alternate' implementation delegates JNDI name knowledge
//	 * to the client. It is included here for example only.
//	 */
//	public EJBHome lookUpHome(Class homeClass, String jndiName) throws IException
//	{
//		EJBHome anEJBHome;
//		anEJBHome = (EJBHome) this.ejbHomes.get(homeClass);
//		try
//		{
//			if (anEJBHome == null)
//			{
//				System.out.println("finding HOME for first time");
//				anEJBHome = (EJBHome) PortableRemoteObject.narrow(ctx.lookup(jndiName), homeClass);
//				this.ejbHomes.put(homeClass, anEJBHome);
//			}
//		}
//		catch (ClassCastException e)
//		{
//			throw new IException("EJBHomeFactory ClassCastException", e);
//		}
//		catch (NamingException e)
//		{
//			throw new IException("EJBHomeFactory ClassCastException", e);
//		}
//		return anEJBHome;
//	}
	
	/**
	 * 获取对应的MDB的QueueSender
	 * 使用的队列连接厂为iTreasuryQcf
	 * 队列名称MDB类命规则：MDB 类名＋Queue
	 * @return res[0] QueueSender
	 * return res[1] ObjectMessage
	 * */
	public Object[] getMDBQueueSession(String mdbName){
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueSender             queueSender = null;
        Object               res[] = new Object[2];
        try {

            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup
                ("java:comp/env/jms/iTreasuryQcf");
            String queueName = mdbName +"Queue";
            queue = (Queue) jndiContext.lookup("java:comp/env/jms/"+queueName);
        } catch (NamingException e) {
            System.out.println("JNDI lookup failed: " +
                e.toString());
            return null;
        }
        
        
        
        try {
            queueConnection =
                queueConnectionFactory.createQueueConnection();
            queueSession =
                queueConnection.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            
            queueSender = queueSession.createSender(queue);
            ObjectMessage message = queueSession.createObjectMessage();
            res[0] = queueSender;
            res[1] = message;

        } catch (Throwable e) {
            System.out.println("Exception occurred: " + e.toString());
        } // finally		
		return res;
	}	
}
