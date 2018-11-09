/*
 * Created on 2004-4-9
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.account.dao.Sett_CapitalSuperviseSettingDAO;
import com.iss.itreasury.settlement.account.dataentity.CapitalSuperviseSettingInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;


/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CapitalSuperviseSettingDelegation {

	private Sett_CapitalSuperviseSettingDAO dao = new Sett_CapitalSuperviseSettingDAO();
	
	/**
	 * 新增资金集中管理设置
	 * 
	 * @param capitalSuperviseSettingInfo
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long add(CapitalSuperviseSettingInfo capitalSuperviseSettingInfo) throws Exception
	{
		long lReturn = -1;
		lReturn = dao.add(capitalSuperviseSettingInfo);
		return lReturn;
	}
	
	/**
	 * 保存资金设置
	 * @param capitalSuperviseSettingInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(CapitalSuperviseSettingInfo info,Collection superColl,Collection childColl) throws Exception
	{
		long lReturn = -1;
		try
		{
			lReturn=dao.save(info,superColl,childColl);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		return lReturn;
	}
	/**
	 * 删除资金设置
	 * @param info
	 * @param superColl
	 * @param childColl
	 * @return
	 * @throws Exception
	 */
	public String delete(CapitalSuperviseSettingInfo info, Collection superColl) throws Exception
	{
		String errMsg="";
		try
		{
			errMsg=dao.delete(info,superColl);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		return errMsg;
		
	}
	//校验上级账户号
	public String checkSuper(CapitalSuperviseSettingInfo info,Collection superColl,Collection childColl) throws Exception
	{
		String errMsg="";
		try
		{
			errMsg=dao.checkSuper(info,superColl,childColl);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		return errMsg;
	}
	//校验负息账户号
	public String checkDebit(CapitalSuperviseSettingInfo info,Collection superColl,Collection childColl) throws Exception
	{
		String errMsg="";
		try
		{
			errMsg=dao.checkDebit(info,superColl,childColl);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		return errMsg;
	}
	/**
	 * 校验是否占用下级单位资金
	 */
	public String chekcSuperBanlance(Collection superColl) throws Exception
	{
		String errMsg="";
		try
		{
			errMsg=dao.chekcSuperBanlance(superColl);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		return errMsg;
	}
	//校验下级账户号
	public String checkChild(CapitalSuperviseSettingInfo info,Collection superColl,Collection childColl) throws Exception
	{
		String errMsg="";
		try
		{
			errMsg=dao.checkChild(info,superColl,childColl);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		return errMsg;
	}
	//校验上级客户
	public long checkSuperClient(long lSuperClientID,long lOfficeID,long lCurrencyID) throws Exception
	{
		long  rtn=-1;
		try
		{
			rtn=dao.checkSuperClient(lSuperClientID,lOfficeID,lCurrencyID);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		return rtn;
	}
	/**
	 * Update的方法：必须填入ID 
	 */
	public void updateByID(CapitalSuperviseSettingInfo capitalSuperviseSettingInfo) throws RemoteException, IRollbackException
	{
		try
		{
			dao.update(capitalSuperviseSettingInfo);
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 根据SuperClientID查询资金集中管理设置 
	 */
	public Collection findBySuperClientID(long lSuperClientID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException
	{
		Collection c = null;
		try
		{
			c = dao.findBySuperClientID(lSuperClientID,lOfficeID,lCurrencyID);
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	/**
	 * 根据SuperClientID查询资金集中管理设置上级账户，负息账户 
	 */
	public Collection findSuperBySuperClientID(long lSuperClientID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException
	{
		Collection c = null;
		try
		{
			c = dao.findSuperBySuperClientID(lSuperClientID,lOfficeID,lCurrencyID);
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	/**
	 * 根据SuperClientID查询资金集中管理设置下级账户
	 */
	public Collection findChildBySuperClientID(long lSuperClientID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException
	{
		Collection c = null;
		try
		{
			c = dao.findChildBySuperClientID(lSuperClientID,lOfficeID,lCurrencyID);
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	/**
	 * 根据SuperClientID修改资金集中管理设置 
	 */
	public long  updateBySuperClientID(long lSuperClientID,long lOfficeID,long lCurrencyID,long lStatusID) throws RemoteException, IRollbackException
	{
		long c = -1;
		try
		{
			c = dao.updateBySuperClientID(lSuperClientID,lOfficeID,lCurrencyID,lStatusID);
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	
	/**
	 * 查询资金集中管理设置 
	 */
	public Collection findAll(long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException
	{
		Collection c = null;
		try
		{
			c = dao.findAll(lOfficeID,lCurrencyID);
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * 根据ID查询
	 */
	public CapitalSuperviseSettingInfo findByID(CapitalSuperviseSettingInfo capitalSuperviseSettingInfo) throws RemoteException, IRollbackException
	{
		CapitalSuperviseSettingInfo returnInfo = new CapitalSuperviseSettingInfo();
		try
		{
			returnInfo = (CapitalSuperviseSettingInfo)dao.findByID(capitalSuperviseSettingInfo.getId(),capitalSuperviseSettingInfo.getClass());
			return returnInfo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnInfo;
	}
	/**
	 * 通过传入的 主账户ID 得到 负息资金账户ID 或者 ： 由 负息资金账户ID 得到 主账户 ID
	 */
	public long getSuperOrDebitInterestAccountID(long lAccountID) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		try
		{
			lReturn = dao.getSuperOrDebitInterestAccountID(lAccountID);
			return lReturn;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lReturn;
	}
	
}
