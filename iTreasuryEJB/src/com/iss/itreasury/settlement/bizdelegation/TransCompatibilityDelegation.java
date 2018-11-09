/*
 * Created on 2004-7-30
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.ejb.CreateException;
import com.iss.itreasury.settlement.transcompatibility.bizlogic.TransCompatibility;
import com.iss.itreasury.settlement.transcompatibility.bizlogic.TransCompatibilityHome;
//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
/**
 * @author gqzhang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TransCompatibilityDelegation
{
	private TransCompatibility transCompatibility = null;
	public TransCompatibilityDelegation() throws RemoteException
	{
		try
		{
			TransCompatibilityHome home;
			try
			{
				home = (TransCompatibilityHome) EJBHomeFactory.getFactory().lookUpHome(TransCompatibilityHome.class);
			}
			catch (IException e)
			{
				throw new RemoteException("EJBHomeFactory连接错误", e);
			}
			transCompatibility = (TransCompatibility) home.create();
		}
		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException", ce);
		}
	}
	/**
	 * Method findAllTransTypeSetting.
	 * 查询所有有效的兼容业务类型
	 * @return Vector
	*/
	public Vector findAllTransTypeSetting(long lOfficeID, long lCurrencyID) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.findAllTransTypeSetting(lOfficeID, lCurrencyID);
		//return null;
	}
	/**
	 * Method findTypeSettingDetailByID.
	 * 查找交易类型设置信息
	 * @param lSettingID
	 * @return CompatibilityTypeSettingInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public CompatibilityTypeSettingInfo findTypeSettingDetailByID(long lSettingID) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.findTypeSettingDetailByID(lSettingID);
		//return null;
	}
	/**
	 * Method presave.
	 * 保存业务前的检查
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long presave(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.presave(info);
		//return -1;
	}
	/**
	 * Method tempsave.
	 * 保存前的校验：重复交易
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long tempsave(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.tempsave(info);
		//return -1;
	}
	/**
	 * Method save.
	 * 保存兼容交易
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long save(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.save(info);
		//return -1;
	}
	/**
	 * Method delete.
	 * 删除兼容交易
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long delete(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.delete(info);
		//return -1;
	}
	/**
	 * Method findCompatibilityByQueryCondition.
	 * 根据组合条件查找兼容交易
	 * @param info
	 * @return Vector
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public Vector findCompatibilityByQueryCondition(QueryByStatusConditionInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.findCompatibilityByQueryCondition(info);
		//return null;
	}
	/**
	 * Method findCompatibilityInfoByID.
	 *  根 据交易id查找交易信息
	 * @param lTransID
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransCompatibilityInfo findCompatibilityInfoByID(long lTransID) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.findCompatibilityInfoByID(lTransID);
		//return null;
	}
	/**
	 * Method cancelCheck.
	 * 取消复核
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long cancelCheck(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.cancelCheck(info);
		//return -1;
	}
	/**
	 * Method check.
	 * 复核交易
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long check(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		long res = transCompatibility.check(info);
////		发送银行指令
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		try
//		{
//			bankInstructDAO.sendBankInstruction(info.getTransNo());
//		}
//		catch (IException e)
//		{
//			Log.print("----------------------------发送银行指令出现异常----------------");
//			throw new IRollbackException(null,e.getMessage(),e);
//		}
		return res;
		//return -1;
	}
	/**
	 * Method match.
	 * @param info
	 * @param specialoperationid
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransCompatibilityInfo match(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.match(info);
		//return null;
	}
	/**
	 * Method getIDByTransNo.
	 * @param strTransNo
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException, SettlementException
	{
		return transCompatibility.getIDByTransNo(strTransNo);
	}
}