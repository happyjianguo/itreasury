/*
 * Created on 2003-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.interest.bizlogic.CompoundInterestSetting;
import com.iss.itreasury.settlement.interest.bizlogic.CompoundInterestSettingHome;
import com.iss.itreasury.settlement.interest.dataentity.CompoundInterestSettingInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompoundInterestSettingDelegation
{
	//private com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositEJB ejb;
	private CompoundInterestSetting CompoundInterestSettingFacade;

	public CompoundInterestSettingDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			CompoundInterestSettingHome home = (CompoundInterestSettingHome)EJBHomeFactory.getFactory().lookUpHome(CompoundInterestSettingHome.class);
			CompoundInterestSettingFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in CompoundInterestSettingDelegation", e);			
		}
	}	 
	  
	/**
	 * 开立交易的保存
	 * @param Assemble, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,定期（通知）开立交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long save(CompoundInterestSettingInfo info) throws RemoteException,IRollbackException
	{
		return CompoundInterestSettingFacade.save(info);	
	}
	/**
	 * 定期（通知）开立交易的删除方法：
	 * @param info, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,被删除的定期（通知）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long delete(CompoundInterestSettingInfo info) throws RemoteException,IRollbackException
	{
		return CompoundInterestSettingFacade.delete(info);
	}
	/**
	 * 根据标识查询定期（通知）开立交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return FixedDepositAssemble,定期（通知）交易实体类
	 * @throws RemoteException
	 */
	public CompoundInterestSettingInfo findByID(long lID) throws RemoteException,IRollbackException
	{
		return CompoundInterestSettingFacade.findByID(lID);	
	}
	/**
	 * 开立根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return CompoundInterestSettingFacade.findByStatus(info);	
	}
}
