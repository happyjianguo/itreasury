/*
 * Created on 2006-4-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.*;
import com.iss.itreasury.settlement.transnoteacceptance.bizlogic.*;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.*;
import com.iss.itreasury.util.*;

public class TransNoteAcceptanceDelegation
{
	private TransNoteAcceptance transNoteAcceptanceFacade;
	
	//构造函数初始化此EJB
	public TransNoteAcceptanceDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			TransNoteAcceptanceHome home = (TransNoteAcceptanceHome)EJBHomeFactory.getFactory().lookUpHome(TransNoteAcceptanceHome.class);
			transNoteAcceptanceFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransFixedDepositDelegation", e);			
		}
	}	 
	 
	/**
	 * 商业票据承兑--到期承兑交易继续的方法：
	 * @param lID long , 交易的ID
	 * @return TransAcceptanceNoteAcceptanceInfo,交易实体类
	 * @throws RemoteException
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceNext(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceNext(info);	
	}
	
	/**
	 * 根据标识查询商业票据承兑--到期承兑交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return TransAcceptanceNoteAcceptanceInfo,交易实体类
	 * @throws RemoteException
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceFindByID(lID);	
	}
	
	/**
	 * 根据交易号查询商业票据承兑--到期承兑交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return TransAcceptanceNoteAcceptanceInfo,交易实体类
	 * @throws RemoteException
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceFindByTransNo(strTransNo);	
	}
	
	/**
	 * 融资租赁收款根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含TransAcceptanceNoteAcceptanceInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection acceptanceFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceFindByStatus(info);	
	}
	
	/**
	 * 商业票据承兑--到期承兑交易的暂存方法：
	 * @param info, TransAcceptanceNoteAcceptanceInfo, 交易实体类（info）
	 * @return long ,本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long acceptanceTempSave(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceTempSave(info);	
	}
	
	/**
	 * 商业票据承兑--到期承兑交易的保存
	 * @param Assemble, TransAcceptanceNoteAcceptanceInfo, 交易实体类（info）
	 * @return long ,商业票据承兑--到期承兑交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long acceptanceSave(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceSave(info);	
	}
	
	/**
	 * 商业票据承兑--到期承兑交易的删除
	 * @param Assemble, TransAcceptanceNoteAcceptanceInfo, 交易实体类（info）
	 * @return long ,商业票据承兑--到期承兑交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long acceptanceDelete(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceDelete(info);
	}

	/**
	 * 商业票据承兑--到期承兑交易复核匹配的方法：
	 * @param info , TransAcceptanceNoteAcceptanceInfo商业票据承兑--到期承兑交易实体类
	 * @return Collection ,包含TransAcceptanceNoteAcceptanceInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection acceptanceMatch(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceMatch(info);	
	}
	
	/**
	 * 商业票据承兑--到期承兑交易的复核方法：
	 * @param info, TransAcceptanceNoteAcceptanceInfo, 交易实体类（info）
	 * @return long ,被复核的商业票据承兑--到期承兑交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long acceptanceCheck(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transNoteAcceptanceFacade.acceptanceCheck(info);
		return res;
	}
	
	/**
	 * 商业票据承兑--到期承兑交易的取消复核方法：
	 * @param info, TransAcceptanceNoteAcceptanceInfo, 交易实体类（info）
	 * @return long ,被取消复核的本金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long acceptanceCancelCheck(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceCancelCheck(info);	
	}
	
	
	/**
	 * 商业票据承兑--垫付收回交易继续的方法：
	 * @param lID long , 交易的ID
	 * @Return TransAdvancedReceviceNoteAcceptanceInfo,交易实体类
	 * @throws RemoteException
	 */
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceNext(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceNext(info);	
	}
	
	/**
	 * 根据标识查询商业票据承兑--垫付收回交易明细的方法：
	 * @param lID long , 交易的ID
	 * @Return TransAdvancedReceviceNoteAcceptanceInfo,交易实体类
	 * @throws RemoteException
	 */
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceFindByID(lID);	
	}
	
	/**
	 * 根据交易号查询商业票据承兑--垫付收回交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @Return TransAdvancedReceviceNoteAcceptanceInfo,交易实体类
	 * @throws RemoteException
	 */
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceFindByTransNo(strTransNo);	
	}
	
	/**
	 * 商业票据承兑--垫付收回交易根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @Return Collection ,包含TransAdvancedReceviceNoteAcceptanceInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection AdvancedReceviceFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceFindByStatus(info);	
	}
	
	/**
	 *商业票据承兑--垫付收回交易的暂存方法：
	 * @param info, TransAdvancedReceviceNoteAcceptanceInfo, 交易实体类（info）
	 * @Return long ,本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long AdvancedReceviceTempSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceTempSave(info);	
	}
	
	/**
	 * 商业票据承兑--垫付收回交易的保存
	 * @param Assemble, TransAdvancedReceviceNoteAcceptanceInfo, 交易实体类（info）
	 * @Return long ,商业票据承兑--到期承兑交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long AdvancedReceviceSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceSave(info);	
	}
	
	/**
	 * 商业票据承兑--垫付收回交易的删除
	 * @param Assemble, TransAdvancedReceviceNoteAcceptanceInfo, 交易实体类（info）
	 * @Return long ,商业票据承兑--到期承兑交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long advancedReceviceDelete(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceDelete(info);
	}

	/**
	 * 商业票据承兑--垫付收回交易复核匹配的方法：
	 * @param info , TransAdvancedReceviceNoteAcceptanceInfo,融资租赁收款实体类
	 * @Return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection advancedReceviceMatch(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceMatch(info);	
	}
	
	/**
	 * 商业票据承兑--垫付收回交易的复核方法：
	 * @param info, TransAdvancedReceviceNoteAcceptanceInfo, 交易实体类（info）
	 * @Return long ,被复核的商业票据承兑--到期承兑交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long advancedReceviceCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transNoteAcceptanceFacade.advancedReceviceCheck(info);
		return res;
	}
	
	/**
	 * 商业票据承兑--垫付收回交易的取消复核方法：
	 * @param info, TransAdvancedReceviceNoteAcceptanceInfo, 交易实体类（info）
	 * @Return long ,被取消复核的本金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long advancedReceviceCancelCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceCancelCheck(info);	
	}
	
}
