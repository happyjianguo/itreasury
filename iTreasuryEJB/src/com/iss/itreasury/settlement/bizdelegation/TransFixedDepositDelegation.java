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
//import com.iss.itreasury.settlement.bankinterface.bizlogic.BankInstruction;
//import com.iss.itreasury.settlement.bankinterface.dataentity.BankInstructionInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.*;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.util.*;
/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFixedDepositDelegation
{
	//private com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositEJB ejb;
	private TransFixedDeposit transFixedDepositFacade;
		
	public TransFixedDepositDelegation() throws RemoteException,IRollbackException
	{
		
		try
		{
			TransFixedDepositHome home = (TransFixedDepositHome)EJBHomeFactory.getFactory().lookUpHome(TransFixedDepositHome.class);
			transFixedDepositFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransFixedDepositDelegation", e);			
		}
	}	 
	  
	/**
	 * 定期（通知）开立交易的暂存方法：
	 * @param info, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,定期（通知）本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long openTempSave(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openTempSave(info);	
	}
	/**
	 * 换开定期存单交易的暂存方法：
	 * @param info, TransFixedChangeInfo, 交易实体类（info）
	 * @return long ,定期（通知）本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long changeTempSave(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeTempSave(info);	
	}
	/**
	 * 换开定期存单交易的暂存方法：
	 * @param info, TransFixedChangeInfo, 交易实体类（info）
	 * @return long ,换开定期存单交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long openChangeSave(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeTempSave(info);	
	}
	/**
	 * 定期（通知）支取交易的暂存方法：
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return long ,定期（通知）本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long drawTempSave(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawTempSave(info);	
	}
	/**
	 * 定期续期转存交易的暂存方法：
	 * @param info, TransFixedContinueInfo, 交易实体类（info）
	 * @return long ,定期（通知）本金交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */
	public long continueTempSave(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueTempSave(info);			
	}
	
	/**
	 * 开立交易的保存
	 * @param Assemble, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,定期（通知）开立交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long openSave(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openSave(info);	
	}
	/**
	 * 换开定期存单交易的保存
	 * @param Assemble, TransFixedChangeInfo, 交易实体类（info）
	 * @return long ,换开定期存单交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long changeSave(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeSave(info);	
	}
	
	/**
	 * 支取交易的保存
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return long ,定期（通知）支取交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long drawSave(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawSave(info);	
	}	
	/**
	 * 续期转存交易的保存
	 * @param info, TransFixedContinueInfo, 交易实体类（info）
	 * @return long ,交易的标识，如果失败，返回-1
	 * @throws java.rmi.RemoteException
	 */	
	public long continueSave(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueSave(info);			
	}
	/**
	 * 定期（通知）开立交易的删除方法：
	 * @param info, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,被删除的定期（通知）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long openDelete(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openDelete(info);
	}
	/**
	 * 换开定期存单交易的删除方法：
	 * @param info, TransFixedChangeInfo, 交易实体类（info）
	 * @return long ,被删除的换开定期存单交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long changeDelete(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeDelete(info);
	}
	/**
	 * 定期（通知）支取交易的删除方法：
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return long ,被删除的定期（通知）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long drawDelete(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawDelete(info);			
	}
	/**
	 * 定期续期转存交易的删除方法：
	 * @param info, TransFixedContinueInfo, 交易实体类（info）
	 * @return long ,被删除的定期）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long continueDelete(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueDelete(info);			
	}

	/**
	 * 定期（通知）开立交易的复核方法：
	 * @param info, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,被复核的定期（通知）开立交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long openCheck(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFixedDepositFacade.openCheck(info);
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
	 * 换开定期存单交易的复核方法：
	 * @param info, TransFixedChangeInfo, 交易实体类（info）
	 * @return long ,被复核的定期（通知）开立交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long changeCheck(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFixedDepositFacade.changeCheck(info);
		return res;
		
	}
	/**
	 * 定期（通知）支取交易的复核方法：
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return long ,被复核的定期（通知）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long drawCheck(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		long res = transFixedDepositFacade.drawCheck(info);
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
	 * 定期（通知）支取交易的复核方法取得部分支取产生的新存单号：
	 * @author 马现福
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return TransFixedOpenInfo ,被复核的定期（通知）交易的新存单号码
	 * @throws RemoteException
	 */
	public TransFixedOpenInfo getNewDepositNo(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		TransFixedOpenInfo nInfo = null;
		if(null != info){
			String OldDepositNo=info.getDepositNo();
			nInfo = transFixedDepositFacade.openFindByOldDepositNo(OldDepositNo);
		}
		return nInfo;
	}
	
	/**
	 * 定期续期转存交易的复核方法：
	 * @param info, TransFixedContinueInfo, 交易实体类（info）
	 * @return long ,被复核的定期）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long continueCheck(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
			long res = transFixedDepositFacade.continueCheck(info);	
//			//发送银行指令
//			BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//			try
//			{
//				bankInstructDAO.sendBankInstruction(info.getTransNo());
//			}
//			catch (IException e)
//			{
//				Log.print("----------------------------发送银行指令出现异常----------------");
//				throw new IRollbackException(null,e.getMessage(),e);
//			}
			return res;
	}
	/**
	 * 定期（通知）存款交易的取消复核方法：
	 * @param info, TransFixedOpenInfo, 交易实体类（info）
	 * @return long ,被取消复核的定期（通知）本金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long openCancelCheck(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openCancelCheck(info);	
	}
	/**
	 * 换开定期存单交易的取消复核方法：
	 * @param info, TransFixedChangeInfo, 交易实体类（info）
	 * @return long ,被取消复核的定期（通知）本金交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long changeCancelCheck(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeCancelCheck(info);	
	}
	/**
	 * 定期（通知）支取交易的取消复核方法：
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return long ,被取消复核的定期（通知）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long drawCancelCheck(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawCancelCheck(info);			
	}
	/**
	 * 定期续期转存交易的取消复核方法：
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return long ,被取消复核的定期（通知）交易的标识，如果失败，返回-1
	 * @throws RemoteException
	 */
	public long continueCancelCheck(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueCancelCheck(info);
			
	}
	/**
	 * 定期（通知）支取交易的继续的方法：
	 * @param info, TransFixedDrawInfo, 交易实体类（info）
	 * @return info, TransFixedDrawInfo, 交易实体类（info）
	 * @throws RemoteException
	 */
	public TransFixedDrawInfo drawNext(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawNext(info);	
	}
	/**
	 * 定期续期转存交易的继续的方法：
	 * @param info, TransFixedContinueInfo, 交易实体类（info）
	 * @return info, TransFixedContinueInfo, 交易实体类（info）
	 * @throws RemoteException
	 */
	public TransFixedContinueInfo continueNext(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueNext(info);			
	}

	/**
	 * 根据标识查询定期（通知）开立交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return FixedDepositAssemble,定期（通知）交易实体类
	 * @throws RemoteException
	 */
	public TransFixedOpenInfo openFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByID(lID);	
	}
	/**
	 * 根据标识查询换开定期存单交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return TransFixedChangeInfo,定期（通知）交易实体类
	 * @throws RemoteException
	 */
	public TransFixedChangeInfo changeFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeFindByID(lID);	
	}
	/**
	 * 根据交易号查询定期（通知）开立交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return FixedDepositAssemble,定期（通知）交易实体类
	 * @throws RemoteException
	 */
	public TransFixedOpenInfo openFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByTransNo(strTransNo);	
	}
	/**
	 * 根据交易号查询换开定期存单交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return TransFixedChangeInfo,定期（通知）交易实体类
	 * @throws RemoteException
	 */
	public TransFixedChangeInfo changeFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeFindByTransNo(strTransNo);	
	}
	/**
	 * 根据标识查询定期（通知）支取交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return FixedDepositAssemble,定期（通知）交易实体类
	 * @throws RemoteException
	 */
	public TransFixedDrawInfo drawFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawFindByID(lID);	
	}
	/**
	 * 根据标识查询定期（续期转存交易明细的方法：
	 * @param lID long , 交易的ID
	 * @return FixedDepositAssemble,定期交易实体类
	 * @throws RemoteException
	 */
	public TransFixedContinueInfo continueFindByID(long lID) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueFindByID(lID);
			
	}
	/**
	 * 根据交易号查询定期（续期转存交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return FixedDepositAssemble,定期交易实体类
	 * @throws RemoteException
	 */
	public TransFixedContinueInfo continueFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueFindByTransNo(strTransNo);
			
	}
	//	added by qhzhou 2007.6.26
	//开立交易的链接查找,过滤由于定期支取生成的开立存单

	/**
	 * 开立根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info,boolean isFilt) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByStatus(info,isFilt);	
	}
	/**
	 * 开立根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByStatus(info);	
	}
	
	/**
	 * 换开定期存单根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection changeFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		System.out.println("换开定期存单(findByStatus):----------(开始Delegation)");
		Collection coll=transFixedDepositFacade.changeFindByStatus(info);
		System.out.println("换开定期存单(findByStatus):----------(结束Delegation)");
		return coll;
		
	}
	/**
	 * 续期转存根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection continueFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueFindByStatus(info);
			
	}
	/**
	 * 支取根据状态查询的方法：
	 * @param QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * @return Collection ,结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection drawFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawFindByStatus(info);	
	}

	/**
	 * 根据交易号查询定期支取交易明细的方法：
	 * @param strTransNo String , 交易号
	 * @return FixedDepositAssemble,定期交易实体类
	 * @throws RemoteException
	 */
	public TransFixedDrawInfo drawFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.drawFindByTransNo(strTransNo);
			
	}
	/**
	 * 开立复核匹配的方法：
	 * @param info , TransFixedOpenInfo,定期（通知）开立实体类
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection openMatch(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openMatch(info);	
	}
	/**
	 * 换开定期存单复核匹配的方法：
	 * @param info , TransFixedChangeInfo,换开定期存单实体类
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection changeMatch(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeMatch(info);	
	}
	/**
	 * 支取复核匹配的方法：
	 * @param info , TransFixedDrawInfo,定期（通知）支取实体类
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection drawMatch(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawMatch(info);	
	}
	/**
	 * 续期转存复核匹配的方法：
	 * @param info , TransFixedContinueInfo,续期转存实体类
	 * @return Collection ,包含FixedDepositResultInfo查询结果实体类的记录集
	 * @throws RemoteException
	 */
	public Collection continueMatch(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueMatch(info);			
	}
	/**
	 * 方法说明：审批方法(开立)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransFixedOpenInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.doApproval(info);
	}
	/**
	 * 方法说明：审批方法(支取)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransFixedDrawInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.doApproval(info);
	}
	/**
	 * 方法说明：审批方法(转存)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransFixedContinueInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.doApproval(info);
	}
	
	/**
	 * 方法说明：取消审批方法(定期开立，通知开立)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransFixedOpenInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.cancelApproval(info);
	}
	/**
	 * 方法说明：取消审批方法(定期支取，通知支取)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransFixedDrawInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.cancelApproval(info);
	}
	/**
	 * 方法说明：取消审批方法(转存)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransFixedContinueInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.cancelApproval(info);
	}
}
