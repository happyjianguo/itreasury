/*
 * Created on 2006-03-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.bizlogic.TransMarginDeposit;
import com.iss.itreasury.settlement.transmargindeposit.bizlogic.TransMarginDepositHome;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author gqfang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransMarginDepositDelegation
{
	//private com.iss.itreasury.settlement.transMargindeposit.bizlogic.TransMarginDepositEJB ejb;
	private TransMarginDeposit transMarginDepositFacade;

	public TransMarginDepositDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			TransMarginDepositHome home = (TransMarginDepositHome)EJBHomeFactory.getFactory().lookUpHome(TransMarginDepositHome.class);
			transMarginDepositFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransMarginDepositDelegation", e);			
		}
	}	 
	  
	/**
	 * 保证金开立交易的暂存方法：
	 * @param info, TransMarginOpenInfo, 交易实体类（info）
	 * @return long ,保证金本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long openTempSave(TransMarginOpenInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openTempSave(info);	
	}
	/**
	 * 保证金支取交易的暂存方法：
	 * @param info, TransMarginDrawInfo, 交易实体类（info）
	 * @return long ,保证金本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long drawTempSave(TransMarginWithdrawInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawTempSave(info);	
	}
	
	
	/**
	 * 开立交易的保存
	 * @param Assemble, TransMarginOpenInfo, 交易实体类（info）
	 * @return long ,保证金开立交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long openSave(TransMarginOpenInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openSave(info);	
	}
	/**
	 * 支取交易的保存
	 * @param info, TransMarginWithdrawInfo, 交易实体类（info）
	 * @return long ,保证金支取交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long drawSave(TransMarginWithdrawInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawSave(info);	
	}	
	
	/**
	 * 保证金开立交易的删除方法：
	 * @param info, TransMarginOpenInfo, 交易实体类（info）
	 * @return long ,被删除的保证金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long openDelete(TransMarginOpenInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openDelete(info);
	}
	/**
	 * 保证金支取交易的删除方法：
	 * @param info, TransMarginWithdrawInfo, 交易实体类（info）
	 * @return long ,被删除的保证金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long drawDelete(TransMarginWithdrawInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawDelete(info);			
	}
	

	/**
	 * 保证金开立交易的复核方法：
	 * @param info, TransMarginOpenInfo, 交易实体类（info）
	 * @return long ,被复核的保证金开立交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long openCheck(TransMarginOpenInfo info) throws RemoteException,IRollbackException
	{
		long res =  transMarginDepositFacade.openCheck(info);
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
	 * 保证金支取交易的复核方法：
	 * @param info, TransMarginWithdrawInfo, 交易实体类（info）
	 * @return long ,被复核的保证金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long drawCheck(TransMarginWithdrawInfo info) throws RemoteException,IRollbackException
	{
		long res = transMarginDepositFacade.drawCheck(info);
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
	 * 保证金存款交易的取消复核方法：
	 * @param info, TransMarginOpenInfo, 交易实体类（info）
	 * @return long ,被取消复核的保证金本金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long openCancelCheck(TransMarginOpenInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openCancelCheck(info);	
	}
	/**
	 * 保证金支取交易的取消复核方法：
	 * @param info, TransMarginWithdrawInfo, 交易实体类（info）
	 * @return long ,被取消复核的保证金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long drawCancelCheck(TransMarginWithdrawInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawCancelCheck(info);			
	}
	
	/**
	 * 保证金支取交易的继续的方法：
	 * @param info, TransMarginWithdrawInfo, 交易实体类（info）
	 * @return info, TransMarginWithdrawInfo, 交易实体类（info）
	 * @throws RemoteException
	 */
	public TransMarginWithdrawInfo drawNext(TransMarginWithdrawInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawNext(info);	
	}
	

	/**
	 * 根据标识查询保证金开立交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return MarginDepositAssemble,保证金交易实体类
	 * @throws RemoteException
	 */
	public TransMarginOpenInfo openFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openFindByID(lID);	
	}
	/**
	 * 根据交易号查询保证金开立交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return MarginDepositAssemble,保证金交易实体类
	 * @throws RemoteException
	 */
	public TransMarginOpenInfo openFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openFindByTransNo(strTransNo);	
	}
	/**
	 * 根据标识查询保证金支取交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return MarginDepositAssemble,保证金交易实体类
	 * @throws RemoteException
	 */
	public TransMarginWithdrawInfo drawFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawFindByID(lID);	
	}
	
	/**
	 * 开立根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含MarginDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openFindByStatus(info);	
	}
	
	/**
	 * 支取根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection drawFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawFindByStatus(info);	
	}
	/**
	 * 根据交易号查询保证金支取交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return MarginDepositAssemble,保证金交易实体类
	 * @throws RemoteException
	 */
	public TransMarginWithdrawInfo drawFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		
		return transMarginDepositFacade.drawFindByTransNo(strTransNo);
			
	}
	/**
	 * 开立复核匹配的方法：
	 * @param info , TransMarginOpenInfo,保证金开立实体类
	 * @return Collection ,包含MarginDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection openMatch(TransMarginOpenInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.openMatch(info);	
	}
	/**
	 * 支取复核匹配的方法：
	 * @param info , TransMarginWithdrawInfo,保证金支取实体类
	 * @return Collection ,包含MarginDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection drawMatch(TransMarginWithdrawInfo info) throws RemoteException,IRollbackException
	{
		return transMarginDepositFacade.drawMatch(info);	
	}
	
	
	
	/**
	 * 方法说明：审批方法(开立)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval(TransMarginOpenInfo info)
		throws RemoteException, IRollbackException
	{
		return transMarginDepositFacade.doApproval(info);
	}
	/**
	 * 方法说明：取消审批方法(开立)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval(TransMarginOpenInfo info)
		throws RemoteException, IRollbackException
	{
		return transMarginDepositFacade.cancelApproval(info);
	}	
	/**
	 * 方法说明：取消审批方法(开立)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval(TransMarginWithdrawInfo info)
		throws RemoteException, IRollbackException
	{
		return transMarginDepositFacade.cancelApproval(info);
	}	
	/**
	 * 方法说明：审批方法(支取)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval(TransMarginWithdrawInfo info)
		throws RemoteException, IRollbackException
	{
		return transMarginDepositFacade.doApproval(info);
	}
	

}
