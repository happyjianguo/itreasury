/*
 * 创建日期 2003-10-13
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.Collection;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.TransLoan;
import com.iss.itreasury.settlement.transloan.bizlogic.TransLoanHome;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.GrantConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.GrantTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentInterestConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
/**
 * 贷款账户
 * @author yqwu
 */
public class TransLoanDelegation
{
	private TransLoan transLoanFacade;
	public TransLoanDelegation() throws RemoteException
	{
		try
		{
			TransLoanHome home = (TransLoanHome) EJBHomeFactory.getFactory().lookUpHome(TransLoanHome.class);
			transLoanFacade = home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}
	/**
	 * (信托/委托)贷款发放——继续的方法
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public LoanPayFormDetailInfo grantNext(LoanPayFormDetailInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.findLoanPayFormDetailByCondition(info);
		//return new LoanPayFormDetailInfo();
	}
	/**
	 * (信托/委托)贷款发放——复核
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantCheck(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		long res = this.transLoanFacade.check(info,true);
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
		 * (信托/委托)贷款发放——取消复核
		 * @param info
		 * @return
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public long grantCancelCheck(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.check(info,false);
		//return 1;
	}
	/**
	 * (信托/委托)贷款发放——删除
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantDelete(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.delete(info);
		//return 1;
	}
	/**
	 * (信托/委托)贷款发放——链接查找方法
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection grantFindByCondition(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.findByCondition(info);
		//return null;
	}
	/**
	 * (信托/委托)贷款发放——根据id查找信息
	 * @param lTransID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo grantFindDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.FindGrantDetailByID(lTransID);
		//return new TransGrantLoanInfo();
	}
	/**
	 * (信托/委托)贷款收回——根据放款通知单ID查找信托贷款发放信息
	 * @param lLoanNoteID 放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo grantFindDetailByLoanNoteID(long lLoanNoteID) throws RemoteException, IRollbackException
	{
		Log.print("根据放款通知单ID查找信托贷款发放信息:" + lLoanNoteID);
		//return this.transLoanFacade.FindGrantDetailByID(521);
		return this.transLoanFacade.FindGrantDetailByLoanNoteID(lLoanNoteID);
	}
	
	/**
	 * 信托贷款发放——复核匹配查询
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo grantMatch(GrantTrustLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.match(info);
		//return null;
	}
	/**
		 * 委托贷款发放——复核匹配查询
		 * @param info
		 * @return
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public TransGrantLoanInfo grantMatch(GrantConsignLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.match(info);
		//return null;
	}
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批方法
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransGrantLoanInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.doApproval(info);
	}
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批拒绝
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransGrantLoanInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.cancelApproval(info);
	}	
	/**
	 * (信托/委托)贷款发放——保存前的操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantPreSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.preSave(info);
		//return 1;
	}
	/**
	 * (信托/委托)贷款发放——保存操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.save(info);
		//return 1;
	}
	/**
	 * (信托/委托)贷款发放——暂存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantTempSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.tempSave(info);
		//return 1;
	}
	/**
	 * (信托/委托)贷款收回——继续的方法
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubLoanAccountDetailInfo repaymentNext(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.findSubLoanAccountDetailByCondition(info);
		//return new SubLoanAccountDetailInfo();
	}
	/**
	 * (信托/委托)贷款收回——复核
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCheck(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		long res = transLoanFacade.check(info,true);
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
		//return 1;
	}
	/**
		 * (信托/委托)贷款收回——复核
		 * @param info
		 * @return
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public long repaymentCancelCheck(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.check(info,false);
		//return 1;
	}
	/**
	 * (信托/委托)贷款收回——删除
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentDelete(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.delete(info);
		//return 1;
	}
	/**
	 * (信托/委托)贷款收回——链接查找方法
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection repaymentFindByCondition(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.findByCondition(info);
		//return null;
	}
	/**
	 * (信托/委托)贷款收回——根据id查找信息
	 * @param lID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo repaymentFindDetailByID(long lID) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.FindRepaymentDetailByID(lID);
		//return new TransRepaymentLoanInfo();
	}
	/**
	 * 信托贷款收回——复核匹配查询
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo repaymentMatch(RepaymentTrustLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.match(info);
		//return null;
	}
	/**
		 * 委托贷款收回——复核匹配查询
		 * @param info
		 * @return
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public TransRepaymentLoanInfo repaymentMatch(RepaymentConsignLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.match(info);
		//return null;
	}
	/**
		 * 利息收回——复核匹配查询
		 * @param info
		 * @return
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public TransRepaymentLoanInfo repaymentMatch(RepaymentInterestConditionInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.match(info);
		//return null;
	}
	/**
	 * (信托/委托)贷款收回——保存前的操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentPreSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.preSave(info);
		//return 0;
	}
	/**
	 * (信托/委托)贷款收回——保存操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{	
		return this.transLoanFacade.save(info);
		//return 1;
	}
	/**
	 * (信托/委托)贷款收回——暂存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentTempSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.tempSave(info);
		//return 1;
	}
	/**
	 * 通过交易号查找交易ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.repaymentGetIDByTransNo(strTransNo);
	}
	
	public long repaymentGetIDByTransNoAndSerialNo(String strTransNo,long lSerialNo) throws RemoteException,IRollbackException
	{
		return this.transLoanFacade.repaymentGetIDByTransNoAndSerialNo(strTransNo,lSerialNo);
	}
	
	/**
	 * 通过交易号查找交易ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.grantGetIDByTransNo(strTransNo);
	}
	/**
	 * 多笔贷款收回勾账
	 * @param infos
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public boolean squareUp(TransRepaymentLoanInfo[] infos) throws RemoteException,IRollbackException{
		boolean bool = this.transLoanFacade.squareUp(infos);
//		//发送银行指令
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		
//		try
//		{	
//			Log.print("=============准备发送银行指令=======");
//			Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
//			TransRepaymentLoanInfo currentInfo = dao.findByID(infos[0].getID());
//			if (currentInfo!=null)
//			{
//				Log.print("================currentInfo.getTransNo()="+ currentInfo.getTransNo() + "================");
//				bankInstructDAO.sendBankInstruction(currentInfo.getTransNo());
//			}
//		}
//		catch (Exception e)
//		{
//			System.out.println("----------------------------发送银行指令出现异常----------------");
//			e.printStackTrace();
//			throw new IRollbackException(null,e.getMessage(),e);
//		}
		return bool;
	}
	/**
	 * 多笔贷款收回取消勾账
	 * @param infos
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public boolean cancelSquareUp(TransRepaymentLoanInfo[] infos) throws RemoteException,IRollbackException{
		return this.transLoanFacade.cancelSquareUp(infos);
	}
	
	/**
	 * 多笔贷款收回勾账查询
	 * @param condiInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findSquareUpRecordsByCondition(TransRepaymentLoanInfo condiInfo) throws RemoteException,IRollbackException{
		return this.transLoanFacade.findSquareUpRecordsByCondition(condiInfo);
	}
	
	/**
	 * 多笔贷款收回根据交易号查询结果集
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection getRepaymentCollectionByTransNo(String strTransNo)throws RemoteException,IRollbackException{
		return this.transLoanFacade.getRepaymentCollectionByTransNo(strTransNo);
	}
	/**
	 * (信托/委托)贷款收回——根据放款通知单ID查找信托贷款收回信息
	 * @param lLoanNoteID 放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection grantFindInterestByLoanNoteID(long lLoanNoteID,long nOfficeID,long nCurrencyID,long lContractID,long lLoanAccountID,long lSubAccountID) throws RemoteException, IRollbackException
	{
		Log.print("根据放款通知单ID查找信托贷款收回信息:" + lLoanNoteID);
		return this.transLoanFacade.grantFindInterestByLoanNoteID(lLoanNoteID,nOfficeID,nCurrencyID,lContractID,lLoanAccountID,lSubAccountID);
	}
	
	/**
	 * (信托/委托)贷款收回——合同ID和放款通知单ID汇总已经实际支付的利息和手续费等信息
	 * @param lLoanNoteID 放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo grantFindInterestByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException
	{
		return this.transLoanFacade.grantFindInterestByCondition(info);
	}
	
}
