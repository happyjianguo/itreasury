/*
 * Created on 2003-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;


//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.transsecurities.bizlogic.TransSecurities;
import com.iss.itreasury.settlement.transsecurities.bizlogic.TransSecuritiesHome;
import com.iss.itreasury.settlement.transsecurities.dataentity.QueryInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.util.*;
/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransSecuritiesDelegation
{
	//private com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositEJB ejb;
	private TransSecurities transSecuritiesFacade;

	public TransSecuritiesDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			TransSecuritiesHome home = (TransSecuritiesHome)EJBHomeFactory.getFactory().lookUpHome(TransSecuritiesHome.class);
			transSecuritiesFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransSecuritiesDelegation", e);			
		}
	}	 
	  
	/**
	 * 交易的暂存方法：
	 * @param info, TransSecuritiesInfo, 交易实体类（info）
	 * @return long ,本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long tempSave(TransSecuritiesInfo info) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.tempSave(info);	
	}
	/**
	 * 交易的保存
	 * @param Assemble, TransSecuritiesInfo, 交易实体类（info）
	 * @return long ,交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long save(TransSecuritiesInfo info) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.save(info);	
	}
	/**
	 * 交易的删除方法：
	 * @param info, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,被删除的交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long delete(TransSecuritiesInfo info) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.delete(info);
	}
	/**
	 * 交易的复核方法：
	 * @param info, TransSecuritiesInfo, 交易实体类（info）
	 * @return long ,被复核的交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long check(TransSecuritiesInfo info) throws RemoteException,IRollbackException
	{
		long res =  transSecuritiesFacade.check(info);
//		//发送银行指令
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
	}
	/**
	 * 交易的取消复核方法：
	 * @param info TransSecuritiesInfo,  交易实体类（info）
	 * @return long ,被取消复核的交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long cancelCheck(TransSecuritiesInfo info) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.cancelCheck(info);	
	}
	/**
	 * 根据标识查询交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return 交易实体类
	 * @throws RemoteException
	 */
	public TransSecuritiesInfo findByID(long lID) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.findByID(lID);	
	}
	/**
	 * 校验报单号是否重复
	 * @param strFormNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long checkFormNo(TransSecuritiesInfo info) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.checkFormNo(info);	
	}
	/**
	 * 根据交易号查询交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return 交易实体类
	 * @throws RemoteException
	 */
	public TransSecuritiesInfo findByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.findByTransNo(strTransNo);	
	}
	/**
	 * 根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection findByStatus(QueryInfo info) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.findByStatus(info);	
	}
	/**
	 * 复核匹配的方法：
	 * @param info , TransSecuritiesInfo,实体类
	 * @return Collection ,包含查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection match(TransSecuritiesInfo info) throws RemoteException,IRollbackException
	{
		return transSecuritiesFacade.match(info);	
	}
}
