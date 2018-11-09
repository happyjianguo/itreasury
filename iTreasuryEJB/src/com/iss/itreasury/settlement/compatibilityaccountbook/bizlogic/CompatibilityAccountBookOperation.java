/*
 * Created on 2004-8-4
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.compatibilityaccountbook.bizlogic;
import java.rmi.RemoteException;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
/**
 * @author gqzhang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompatibilityAccountBookOperation
{
	private CompatibilityAccountBook accountBook;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public CompatibilityAccountBookOperation() throws RemoteException, IRollbackException
	{
		try
		{
			CompatibilityAccountBookHome home = (CompatibilityAccountBookHome) EJBHomeFactory.getFactory().lookUpHome(CompatibilityAccountBookHome.class);
			accountBook = (CompatibilityAccountBook) home.create();
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new RemoteException();
		}
	}
	/**
	 * Method saveCompatibilityAccountDetails.
	 * 保存兼容业务账薄信息
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveCompatibilityAccountDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.saveCompatibilityAccountDetails(transInfo);
	}
	/**
		 * Method deleteTransCompatibility.
		 * 兼容业务账薄删除
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void deleteTransCompatibility(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.deleteTransCompatibility(transInfo);
	}
	/**
		 * Method checkCompatibilityDetails.
		 * 复核兼容业务
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void checkCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.checkCompatibilityDetails(transInfo);
	}
	/**
	 * Method cancelCheckCompatibilityDetails.
	 * 取消复核兼容业务
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.cancelCheckCompatibilityDetails(transInfo);
	}
	/**
		 * Method saveAbamentInfoAccountDetails.保存转贴现卖出自动冲销业务账薄信息
		 * 
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void saveAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.saveAbamentInfoAccountDetails(transInfo);
	}
	/**
		 * Method deleteTransAbament. 删除转贴现卖出自动冲销账薄信息
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void deleteTransAbament(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.deleteTransAbament(transInfo);
	}
	/**
	 * Method checkAbamentInfoAccountDetails.复核转贴现卖出自动冲销业务
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.checkAbamentInfoAccountDetails(transInfo);
	}
	/**
	 * Method cancelCheckAbamentInfoAccountDetails.取消复核转贴现卖出自动冲销业务
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.cancelCheckAbamentInfoAccountDetails(transInfo);
	}
}
