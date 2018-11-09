/*
 * Created on 2004-9-10
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.settlement.settpaynotice.bizlogic.SettPayNotice;
import com.iss.itreasury.settlement.settpaynotice.dao.SettDiscountBillDAO;
import com.iss.itreasury.settlement.settpaynotice.dataentity.*;
import com.iss.itreasury.settlement.settpaynotice.bizlogic.SettPayNoticeHome;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;
import java.util.*;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettPayNoticeDelegation
{
    private SettPayNotice settPayNoticeFacade = null;
	public SettPayNoticeDelegation() throws RemoteException
	{
		try
		{
		    SettPayNoticeHome home = (SettPayNoticeHome) EJBHomeFactory.getFactory().lookUpHome(SettPayNoticeHome.class);
		    settPayNoticeFacade = home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}
	/**
	 * 查询放款通知单
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public Collection findPayNoticeByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.findPayNoticeByMultioption( qInfo );
	}
	/**
	 * 查询放款通知单LD 2010-12-14
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public PageLoader getPayNoticeByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.getPayNoticeByMultioption( qInfo );
	}
	
	/**
	 * 查询贴现通知单
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public Collection findDiscountCredenceByMultioption(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.findDiscountCredenceByMultioption( qInfo );
	}
	
	/**
	 * 查询贴现通知单
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public PageLoader getMultiOptinList(SettPayNoticeQueryInfo qInfo) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.getMultiOptinList( qInfo );
	}
	
	
	/**
	 * 保存放款通知单信息
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long savePayNotice(SettPayNoticeInfo info) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.savePayNotice( info );
	}
	
	/**
	 * 保存贴现放款通知单信息
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountCredence(SettDiscountCredenceInfo info) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.saveDiscountCredence( info );
	}
	
	/**
	 * 复合放款通知单
	 * @param lID
	 * @param userID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long checkPayNotice(long lID,long userID) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.checkPayNotice( lID,userID);
	}

	/**
	 * 复合贴现放款通知单
	 * @param lID
	 * @param userID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long checkDiscountCredence(long lID,long userID) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.checkDiscountCredence( lID,userID );
	}
	
	/**
	 * 根据ID查找放款通知单
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettPayNoticeInfo findPayNoticeByID(long lID) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.findPayNoticeByID( lID);
	}
	
	/**
	 * 根据ID查找贴现放款通知单
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettDiscountCredenceInfo findDiscountCredenceByID(long lID) throws java.rmi.RemoteException
	{
		return settPayNoticeFacade.findDiscountCredenceByID( lID );
	}
	
	/**
	 * 根据凭证ID查找贴现票据
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public SettDiscountBillInfo findDiscountBillByCredenceID(long lID) throws java.rmi.RemoteException
	{	    
	    return settPayNoticeFacade.findDiscountBillByCredenceID(lID);
	}
	
	/**
	 * 保存贴现票据
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public long saveDiscountBill(SettDiscountBillInfo info) throws java.rmi.RemoteException
	{
	    return settPayNoticeFacade.saveDiscountBill(info);
	}
	/**
	 * 保存贴现票据信息，操作LOAN_DISCOUNTCONTRACTBILL表
	 * @param DiscountBillInfo info,
	 * @param String tsCreate,
	 * @param String tsEnd,
	 * @param long lCurrencyID,
	 * @param long lOfficeID
	 * @return long
	 * @throws RemoteException,IException
	 */
	public long saveDiscountList(
		DiscountBillInfo info,
		String tsCreate,
		String tsEnd,
		long lCurrencyID,
		long lOfficeID)
		throws RemoteException,IException {
		return settPayNoticeFacade.saveDiscountList(info,tsCreate, tsEnd,lCurrencyID,lOfficeID);
	}
	
	/**
	 * 保存贴现票据信息，操作DiscountBill表
	 * @param lDiscountBillID 贴现票据标识，如果<=0，新增，否则，修改。新增ID取最大值
	 * @param lDiscountApplyID 贴现标识
	 * @param strUser 原始出票人
	 * @param strBank 承兑银行
	 * @param lIsInBeijing 是否在北京
	 * @param tsCreate 出票日
	 * @param tsEnd 到期日
	 * @param strCode 汇票号码
	 * @param dAmount 汇票金额
	 */
	public long saveDiscountList1(
		long nContractId,
		long nNum,
		String strUser,
		String strBank,
		long lIsLocal,
		String tsCreate,
		String tsEnd,
		String strCode,
		double dAmount,
		long lAddDay,
		long lAcceptPOTypeID,
		String strFormerOwner,
		long lCurrencyID,
		long lOfficeID)
		throws RemoteException,IException {
		return settPayNoticeFacade.saveDiscountList1(nContractId,nNum, strUser, strBank, lIsLocal,tsCreate, tsEnd, strCode, dAmount, lAddDay, lAcceptPOTypeID, strFormerOwner,lCurrencyID,lOfficeID);
	}
	
}
