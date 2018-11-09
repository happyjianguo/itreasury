/*
 * Created on 2006-4-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transfinance.bizlogic.*;
import com.iss.itreasury.settlement.transfinance.dataentity.*;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.util.*;
/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFinanceDelegation
{
	//private com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositEJB ejb;
	private TransFinance transFinanceFacade;
	
	//构造函数初始化此EJB
	public TransFinanceDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			TransFinanceHome home = (TransFinanceHome)EJBHomeFactory.getFactory().lookUpHome(TransFinanceHome.class);
			transFinanceFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransFixedDepositDelegation", e);			
		}
	}	 
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批方法
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransReturnFinanceInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transFinanceFacade.doApproval(info);
	}
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批拒绝
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransReturnFinanceInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transFinanceFacade.cancelApproval(info);
	}	 
	/**
	 * 融资租赁收款继续的方法：
	 * @param lID long , 交易的ID
	 * @return FixedDepositAssemble,交易实体类
	 * @throws RemoteException
	 */
	public TransReceiveFinanceInfo receiveNext(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveNext(info);	
	}
	
	/**
	 * 根据标识查询融资租赁收款交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return FixedDepositAssemble,交易实体类
	 * @throws RemoteException
	 */
	public TransReceiveFinanceInfo receiveFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveFindByID(lID);	
	}
	
	/**
	 * 根据交易号查询融资租赁收款交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return FixedDepositAssemble,交易实体类
	 * @throws RemoteException
	 */
	public TransReceiveFinanceInfo receiveFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveFindByTransNo(strTransNo);	
	}
	
	/**
	 * 融资租赁收款根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection receiveFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveFindByStatus(info);	
	}
	
	/**
	 * 融资租赁收款交易的暂存方法：
	 * @param info, TransReceiveFinanceInfo, 交易实体类（info）
	 * @return long ,本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long receiveTempSave(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveTempSave(info);	
	}
	
	/**
	 * 融资租赁收款交易的保存
	 * @param Assemble, TransReceiveFinanceInfo, 交易实体类（info）
	 * @return long ,融资租赁收款交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long receiveSave(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveSave(info);	
	}
	
	/**
	 * 融资租赁收款交易的删除
	 * @param Assemble, TransReceiveFinanceInfo, 交易实体类（info）
	 * @return long ,融资租赁收款交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long receiveDelete(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveDelete(info);
	}

	/**
	 * 融资租赁收款复核匹配的方法：
	 * @param info , TransReceiveFinanceInfo,融资租赁收款实体类
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection receiveMatch(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveMatch(info);	
	}
	
	/**
	 * 融资租赁收款交易的复核方法：
	 * @param info, TransReceiveFinanceInfo, 交易实体类（info）
	 * @return long ,被复核的融资租赁收款交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long receiveCheck(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFinanceFacade.receiveCheck(info);
		return res;
	}
	
	/**
	 * 融资租赁收款交易的取消复核方法：
	 * @param info, TransReceiveFinanceInfo, 交易实体类（info）
	 * @return long ,被取消复核的本金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long receiveCancelCheck(TransReceiveFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.receiveCancelCheck(info);	
	}
	
	
	/**
	 * 融资租赁收款继续的方法：
	 * @param lID long , 交易的ID
	 * @Return FixedDepositAssemble,交易实体类
	 * @throws RemoteException
	 */
	public TransReturnFinanceInfo ReturnNext(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnNext(info);	
	}
	
	/**
	 * 根据标识查询融资租赁收款交易明细的方法：
	 * @param lID long , 交易的ID
	 * @Return FixedDepositAssemble,交易实体类
	 * @throws RemoteException
	 */
	public TransReturnFinanceInfo ReturnFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnFindByID(lID);	
	}
	
	/**
	 * 根据交易号查询融资租赁收款交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @Return FixedDepositAssemble,交易实体类
	 * @throws RemoteException
	 */
	public TransReturnFinanceInfo ReturnFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnFindByTransNo(strTransNo);	
	}
	
	/**
	 * 融资租赁收款根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @Return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection ReturnFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnFindByStatus(info);	
	}
	
	/**
	 * 融资租赁收款交易的暂存方法：
	 * @param info, TransReturnFinanceInfo, 交易实体类（info）
	 * @Return long ,本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long ReturnTempSave(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnTempSave(info);	
	}
	
	/**
	 * 融资租赁收款交易的保存
	 * @param Assemble, TransReturnFinanceInfo, 交易实体类（info）
	 * @Return long ,融资租赁收款交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long ReturnSave(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnSave(info);	
	}
	
	/**
	 * 融资租赁收款交易的删除
	 * @param Assemble, TransReturnFinanceInfo, 交易实体类（info）
	 * @Return long ,融资租赁收款交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long ReturnDelete(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnDelete(info);
	}

	/**
	 * 融资租赁收款复核匹配的方法：
	 * @param info , TransReturnFinanceInfo,融资租赁收款实体类
	 * @Return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection ReturnMatch(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnMatch(info);	
	}
	
	/**
	 * 融资租赁收款交易的复核方法：
	 * @param info, TransReturnFinanceInfo, 交易实体类（info）
	 * @Return long ,被复核的融资租赁收款交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long ReturnCheck(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFinanceFacade.returnCheck(info);
		return res;
	}
	
	/**
	 * 融资租赁收款交易的取消复核方法：
	 * @param info, TransReturnFinanceInfo, 交易实体类（info）
	 * @Return long ,被取消复核的本金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long ReturnCancelCheck(TransReturnFinanceInfo info) throws RemoteException,IRollbackException
	{
		return transFinanceFacade.returnCancelCheck(info);	
	}
	
	/**
	 * @author yunchang
	 * @date 2010-07-02
	 * @function 融资租赁-收款-已收保证金金额
	 * @param long 
	 * @return double
	 * @throws IRollbackException RemoteException
	 */
	public double getMbailamount(long constractID) throws IRollbackException, RemoteException
	{
		return transFinanceFacade.getMbailamount(constractID);
	}
	
}
