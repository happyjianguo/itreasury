/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.register.bizlogic;
import java.rmi.RemoteException;

import javax.ejb.SessionBean;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
//import com.iss.itreasury.securities.register.dataentity.IRegisterParam;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RegisterEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
	/**
	 * 登记薄登记
	 * @param regiesterInfo 登记薄信息
	 * */
	public long register(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		log.debug("------开始登记薄处理::注册-----------");		
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());
		long id = registerBean.register(doInfo);
		log.debug("------结束登记薄处理::注册-----------");		
		return 	id;	
	}
	
	/**
	 * 登记薄取消登记
	 * @param doInfo 登记薄信息
	 * */
	public void cancelRegister(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		log.debug("------开始登记薄处理::取消注册-----------");		
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());
		registerBean.cancelRegister(doInfo);
		log.debug("------结束资金账户处理::取消注册-----------");		
	}	
	
	/**
	 * 登记薄登记到期
	 * @param doInfo 登记薄信息
	 * */	
	public void maturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		log.debug("------开始登记薄处理::到期-----------");		
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());	
		log.debug("------开始登记薄处理"+registerBean);
		log.debug("------交割单"+doInfo);
		registerBean.maturate(doInfo);		
		log.debug("------结束资金账户处理::到期-----------");		
	}
	
	/**
	 * 登记薄登记取消到期
	 * @param doInfo 登记薄信息
	 * */	
	public void cancelMaturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		log.debug("------开始登记薄处理::取消到期-----------");		
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());		
		registerBean.cancelMaturate(doInfo);			
		log.debug("------结束资金账户处理::取消到期-----------");		
	}		
	
	
	/**
	 * 登记薄申购确认，仅仅针对申购相关业务
	 * @param doInfo 登记薄信息
	 * */
	public void confirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());		
		registerBean.confirmApplication(doInfo);					
	}
	/**
	 * 登记薄申购取消确认，仅仅针对申购相关业务
	 * @param doInfo 登记薄信息
	 * */
	public void cancelConfirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());		
		registerBean.cancelConfirmApplication(doInfo);							
	}
	
	/**
	 * 申购卖出
	 * 一级卖出时，修改证券申购登记簿
	 * @param doInfo 登记薄信息
	 * */
	public void sellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());		
		registerBean.sellOut(doInfo);									
	}
	
	/**
	 * 申购取消卖出
	 * 修改证券申购登记簿
	 * @param doInfo 登记薄信息
	 * */	
	public void cancelSellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());		
		registerBean.cancelSellOut(doInfo);											
	}	
	
	
}
