/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transloan.bizlogic;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.SessionBean;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.loanpaynotice.bizlogic.PayNoticeOperation;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.BankInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.setting.bizlogic.SettTaxRatePlanSettingBiz;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.dao.DAOHelper;
import com.iss.itreasury.settlement.transloan.dao.Sett_SyndicationLoanInterestDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.GrantConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.GrantTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentInterestConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransLoanEJB implements SessionBean
{
	private final long SETT_TRUST_LOAN = 1;			//信托
	private final long SETT_CONSIGN_LOAN = 2;		//委托
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private static  Object lockObj = new Object();  //静态
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	/***************************************************************************************************************************************************************************************************
	 * ******************************贷款发放开始*************************** ****************************************************************
	 */
	/**
	 * 保存前的操作
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 */
	public long preSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return 1;
	}
	/**
	 * 信托贷款发放暂存操作
	 * 
	 * @param info
	 *            TransGrantLoanInfo
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		long infoId = -1;
		try
		{
			log.debug("----------校验-------------");
			validateTransGrantLoanInfo(info);
			log.debug("----------保存信息-------------");
			infoId = this.partSave(info, dao);
			log.debug("----------修改状态-------------");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("----------成功-------------");
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e){
			throw e;
		}
		catch (IRollbackException e){
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			//Modified by zwsun, 2007/8/8
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx,e.getMessage());
			}
		}
	
		return infoId;
		}
	}
	/**
	 *  贷款发放保存
	 * 
	 * @param info
	 * @param isTrustLoan
	 * @return TransGrantLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long lID = -1;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
			log.debug("====开始保存====");
			//log.debug("====校验====");
			//validateTransGrantLoanInfo(info);//不能用这个
			String transNo = info.getTransNo();
			boolean bNewTransNo = false;
			log.debug("====交易号====" + transNo);
			if (transNo == null || transNo.equals(""))
			{
				log.debug("====生成交易号====");
				bNewTransNo = true;
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				info.setTransNo(transNo);
				Log.print("----开始校验放款通知单状态----");
				if (!dao.checkPayForm(info.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.CHECK))
				{
					Log.print("----放款通知单已经被使用----");
					throw new IRollbackException(mySessionCtx, "放款通知单已经被使用");
				}
				log.debug("====改变放款通知单状态为已使用====");
				dao.updateLoanPayFormStatus(info.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.USED);
				log.debug("====改变完成====");
			}
			else
			{
				synchronized(lockObj){
				//校验状态
				TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
				log.print("====info.getStatusID()====" + info.getStatusID());
				log.print("====newInfo.getStatusID()====" + newInfo.getStatusID());
				log.print("====errMsg====" + errMsg);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====被非法修改过====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//删除账簿信息
					log.debug("====删除账簿信息====");
					TransGrantLoanInfo oldTransInfo = dao.findByID(info.getID());
					if (oldTransInfo == null)
					{
						log.debug("====无法找到交易对应的账户信息，交易失败====");
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					}
					accountBookOperation.deleteGrantLoan(oldTransInfo);
				}
				}
			}
			log.debug("====保存信息====");
			lID = this.partSave(info, dao);
			//保存账簿信息
			info.setID(lID);
			//保存账簿信息
			log.debug("====保存账簿信息====");
			accountBookOperation.saveGrantLoan(info);
			//更新状态到：保存（未复核）
			log.debug("====更新状态到：保存（未复核====");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
		
			//Added by zwsun , 2007-06-20, 审批流保存
			if(info.getInutParameterInfo()!=null)
			{
			log.debug("------提交审批--------");
			//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
			InutParameterInfo tempInfo = info.getInutParameterInfo();
			tempInfo.setUrl(tempInfo.getUrl()+lID);
			tempInfo.setTransID(transNo);//这里保存的是交易编号
			tempInfo.setDataEntity(info);
			
			//提交审批
			FSWorkflowManager.initApproval(info.getInutParameterInfo());
			//更新状态到审批中
			dao.updateStatus(info.getID(),SETTConstant.TransactionStatus.APPROVALING);
			log.debug("------提交审批成功--------");
			
			}
		}
		//modified by mzh_fu 2007/05/011
		/*		
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		return lID;
		}
	}
	/**
	 * 检查是否更改
	 * 
	 * @param info
	 * @param dao
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	private boolean isTouch(TransGrantLoanInfo info, Sett_TransGrantLoanDAO dao) throws RemoteException, IRollbackException
	{
		try
		{
			log.debug("======是否被修改？======");
			Timestamp lastTouchDate = dao.findByID(info.getID()).getModify();
			Timestamp currentTouchDate = info.getModify();
			Log.print("数据库当前时间：" + lastTouchDate.toString());
			Log.print("操作对象的修改时间：" + currentTouchDate.toString());
			if (currentTouchDate == null || lastTouchDate == null || !lastTouchDate.toString().equals(currentTouchDate.toString()))
			{
				log.debug("======被修改======");
				log.debug("======currentTouchDate======" + currentTouchDate);
				log.debug("======lastTouchDate======" + lastTouchDate);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
	}
	/**
	 * 保存TransGrantLoanInfo
	 * 
	 * @param info
	 * @param dao
	 * @return TransGrantLoanInfo ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long partSave(TransGrantLoanInfo info, Sett_TransGrantLoanDAO dao) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try
		{
			if (info.getID() > 0)
			{
				log.debug("----------修改信息-------------");
				dao.update(info);
			}
			else
			{
				log.debug("----------增加信息-------------");
				dao.add(info);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	/**
	 * 校验贷款发放信息
	 * 
	 * @param info
	 *            TransGrantLoanInfo
	 * @param isNew
	 * @throws IRollbackException
	 */
	private void validateTransGrantLoanInfo(TransGrantLoanInfo info) throws IRollbackException, RemoteException
	{
		log.debug("----------校验中。。。。。。-------------");
		if (info.getID() > 0)
		{
			TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
		}
	}
	/**
	 * 根据id查找信托贷款发放信息
	 * 
	 * @param id
	 *            信托贷款发放信息id
	 * @return TrustLoanInfoAssembler
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo FindGrantDetailByID(long id) throws RemoteException, IRollbackException
	{
		TransGrantLoanInfo info = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			info = dao.findByID(id);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	
	/*
	 * 根据放款通知单ID查找信托贷款发放信息
	 * 
	 * @param id 信托贷款发放信息的放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo FindGrantDetailByLoanNoteID(long lLoanNoteID) throws RemoteException, IRollbackException
	{
		TransGrantLoanInfo info = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			Log.print("根据放款通知单ID查找信托贷款发放信息1:" + lLoanNoteID);
			info = dao.findByLoanNoteID(lLoanNoteID);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	
	/**
	 * 链接查询信托贷款发放信息集合
	 * 
	 * @param info
	 *            TrustLoanInfo
	 * @param orderType
	 * @param isDesc
	 * @return 信托贷款发放信息集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findByCondition(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			collection = dao.findByCondition(info);
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return collection;
	}
	/**
	 * 匹配查询信托贷款发放信息
	 * 
	 * @param info
	 * @return 信托贷款发放信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo match(GrantTrustLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		TransGrantLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			//Added by zwsun , 2007/6/28, 匹配审批流
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));
					
			helper.setInfo(info);
			helper.setStrict(true);
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BREAK_INVESTADDITIONALRECORDINGGRANT)
			{
			helper.setNotMatch("getDepositAccountID");
			helper.setNotMatch("getBankID");
			}
			helper.setNotMatch("getInterestStart");
			helper.setNotMatch("getCashFlowID");
			helper.setNotMatch("getPaySuretyFeeAccountID");
			helper.setNotMatch("getReceiveSuretyFeeAccountID");
			helper.setNotMatch("getPayTypeID");
			//helper.setNotMatch("getInputUserID");
			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransGrantLoanInfo) iterator.next();
				break;
			}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * 匹配查询信托贷款发放信息
	 * 
	 * @param info
	 * @return 信托贷款发放信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo match(GrantConsignLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		TransGrantLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			Log.print("进入match..........");
			//Added by zwsun , 2007/6/28, 匹配审批流
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));
			
			helper.setInfo(info);
			helper.setStrict(true);
			helper.setNotMatch("getPayTypeID"); // 放款方式不作为查询条件
			if (info.getPayInterestAccountID() < -1)
			{ //小于默认值,不作为匹配条件
				helper.setNotMatch("getPayInterestAccountID");
			}
			if (info.getReceiveInterestAccountID() < -1)
			{ //小于默认值,不作为匹配条件
				helper.setNotMatch("getReceiveInterestAccountID");
			}
			if (info.getPayCommisionAccountID() < -1)
			{ //小于默认值,不作为匹配条件
				helper.setNotMatch("getPayCommisionAccountID");
			}
			
			//if (info.getInterestTaxRate() < -1)
			//{ //小于默认值,不作为匹配条件
				helper.setNotMatch("getInterestTaxRate");
			//}
			
			//匹配条件之 利息税费率 改成 利息税费率编号
			if(info.getInterestTaxPlanId() < -1)
			{
				helper.setNotMatch("getInterestTaxPlanId");
			}

			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				Log.print("得到结果");
				returninfo = (TransGrantLoanInfo) iterator.next();
				break;
			}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * 得到贷款条件信息
	 * 
	 * @param info
	 *            LoanConditionInfo
	 * @return LoanConditionInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public LoanPayFormDetailInfo findLoanPayFormDetailByCondition(LoanPayFormDetailInfo info) throws RemoteException, IRollbackException
	{
		LoanPayFormDetailInfo returnInfo = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			returnInfo = dao.findPayFormDetailByCondition(info);
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returnInfo;
	}
	/**
	 * 删除贷款记录
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long id = -1;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		
		try
		{
				//对客户加锁
				sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
	
				TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, dao))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E131");
				}
				else
				{
					//删除交易记录
					accountBookOperation.deleteGrantLoan(newInfo);
					//返回ID
					if (info.getStatusID()== SETTConstant.TransactionStatus.SAVE){		//如果是保存，更改放款通知单状态,如果是暂存不变
						if (!dao.checkPayForm(info.getLoanNoteID(),LOANConstant.LoanPayNoticeStatus.USED)){
							throw new IRollbackException(mySessionCtx,"放款通知单已经被修改");
						}
						dao.updateLoanPayFormStatus(info.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.CHECK);
					}
	
					return dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
				}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		}
	}
	/**
	 * 复核或者取消复核
	 * 
	 * @param info
	 * @param checkOrCancel
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransGrantLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		TransGrantLoanInfo transInfo = new TransGrantLoanInfo();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//对客户加锁
		try
		{
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		if (checkOrCancel)
		{
			//复核
			TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E133");
			}
		}
		else
		{
			//取消复核
			TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
		}
		try
		{
			dao.update(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try{
			transInfo = dao.findByID(info.getID());		//查找数据库中的完整纪录
			transInfo.setCheckAbstract(info.getCheckAbstract());	//复核备注
			transInfo.setCheckUserID(info.getCheckUserID());		//复核人
			//*  modify by zcwang 2008-10-06 银团开款关于代理费收取方式已经修改，原来逻辑注释
			if(transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
			{
				//银团提款查询
				ArrayList bankLoancol = null;
				BankLoanQuery bankLoanQuery =new BankLoanQuery();
				bankLoancol =(ArrayList)bankLoanQuery.findByFormID(transInfo.getLoanNoteID());
				transInfo.setSyndicationLoan(bankLoancol);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
		if (checkOrCancel)
		{
			//复核交易记录
			accountBookOperation.checkGrantLoan(transInfo);
			try
			{
				dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			//是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			//是否需要生成银行指令
			boolean bCreateInstruction = false;
			long bankID = info.getBankID();
			try {
				//调用此方法后，bankID的值变为银行类型ID
				bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
			} catch (Exception e1) {				
				log.error("判断传入的银行ID是否需要生成银行指令出错！");
				e1.printStackTrace();
			}
			
			try
			{
				if(bIsValid && bCreateInstruction) {
					Log.print("*******************开始产生自营贷款发放或委托贷款发放等付款指令，并保存**************************");
					try {
						log.debug("------开始自营贷款发放或委托贷款发放等付款指令生成--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成自营贷款发放或委托贷款发放等付款指令成功--------");
						
					} catch (Throwable e) {
						log.error("生成自营贷款发放或委托贷款发放等付款指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成自营贷款发放或委托贷款发放等付款指令失败："+e.getMessage());
					}
				}
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, "保存银行转账指令出错！" + e.getMessage());
			}
		}
		else
		{
			//取消复核交易记录
			accountBookOperation.cancelCheckGrantLoan(transInfo);
			//更新状态到：未复核
			try
			{
				//Added by zwsun , 2007/8/4, 判断是否挂了审批流
				InutParameterInfo pInfo=new InutParameterInfo();
				pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				pInfo.setOfficeID(info.getOfficeID());
				pInfo.setCurrencyID(info.getCurrencyID());
				pInfo.setTransTypeID(info.getTransactionTypeID());
				pInfo.setActionID(-1);
				if(FSWorkflowManager.isNeedApproval(pInfo)){
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.APPROVALED, info.getCheckAbstract(), info.getCheckUserID());					
				}else{
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());					
				}								
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		//解锁
		try
		{
			if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	/***************************************************************************************************************************************************************************************************
	 * ******************************贷款发放结束*************************** ****************************************************************
	 */
	/***************************************************************************************************************************************************************************************************
	 * ******************************贷款收回开始*************************** ****************************************************************
	 */
	/**
	 * 保存前的操作
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 */
	public long preSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		return SETTConstant.PreSaveResult.NORMAL;
	}
	/**
	 * 信托贷款收回暂存操作
	 * 
	 * @param info
	 *            TransRepaymentLoanInfo
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		long infoId = -1;
		try
		{
			log.debug("----------校验-------------");
			validateTransRepaymentLoanInfo(info);
			log.debug("----------保存信息-------------");
			infoId = this.partSave(info, dao);
			log.debug("----------修改状态-------------");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("----------成功-------------");
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return infoId;
		}
	}
	/**
	 *  贷款收回保存
	 * 
	 * @param info
	 * @return TransRepaymentLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long lID = -1;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
			log.debug("====开始保存====");
			log.debug("====校验====");
			validateTransRepaymentLoanInfo(info);
			String transNo = "";
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				Log.print("===当前交易为多笔贷款收回,取得临时交易号===");
				transNo = info.getTempTransNO();
			}
			else {
				Log.print("===当前交易不是多笔贷款收回,取得交易号===");
				transNo = info.getTransNo();
			}
			/**
			 * 
			 */
			boolean bNewTransNo = false;
			log.debug("====交易号====" + transNo);
			if (transNo == null || transNo.equals(""))
			{
				log.debug("====生成交易号====");
				bNewTransNo = true;
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				if (info.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
					Log.print("===新交易,置入新临时交易号===");
					info.setTempTransNO(transNo);
				}
				else{
					Log.print("===新交易,置入新交易号===");
					info.setTransNo(transNo);
				}
				/**
				 * 开始改变免还通知单和提前还款通知单的状态
				 */
				if (info.getPreFormID()>0)
				{
					Log.print("===使用了提前还款通知单===");
					Log.print("===开始检查提前还款通知单状态===");
					if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK)){
						throw new IRollbackException(mySessionCtx,"提前还款通知单已经被使用");
					}
					Log.print("===开始改变提前还款通知单状态为已使用===");
//					增加判断，如果归还金额等于还款通知单金额，改变通知单状态为以使用，否则不改变其状态。华联新加，一个还款
					//通知单可以多次还清。
					double  AllAmount=  DataFormat.formatDouble(dao.getTotalMountByPReformid(info.getPreFormID(),info.getID()));//获得该通知单所有还款金额
		            double  noticeAmout = DataFormat.formatDouble(dao.getAMountByPReformid (info.getPreFormID()));  //获得还款通知单金额         
				    System.out.println("-----AllAmount-----===="+AllAmount+"=====noticeAmout====="+noticeAmout);
					AllAmount+=info.getAmount();
					if(DataFormat.formatDouble(AllAmount)==DataFormat.formatDouble(noticeAmout)){
					dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED);
					}
				}
				//如果修改的时候不再使用提前还款通知单，需要将还款通知单的状态改变.
				TransRepaymentLoanInfo oldInfo = dao.findByID(info.getID());
				if(oldInfo!=null&&oldInfo.getPreFormID()>0&&info.getPreFormID()<0){
					dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
				}
				if (info.getFreeFormID()>0)
				{
					Log.print("===使用了免还通知单===");
					Log.print("===开始检查免还通知单的状态===");
					if (!dao.isFreeFormStatusCorrect(info.getFreeFormID(),LOANConstant.FreeApplyStatus.CHECK)){
						throw new IRollbackException(mySessionCtx,"免还通知单已经被使用");
					}
					Log.print("===开始改变免还通知单的状态为已使用");
					dao.updateFreeFormStatus(info.getFreeFormID(),LOANConstant.FreeApplyStatus.USED);
				}				
			}
			else
			{
				synchronized(lockObj){
					TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(info.getID());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
	
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
	
					if (this.isTouch(info, dao))
					{
						log.debug("====被非法修改过====");
						throw new IRollbackException(mySessionCtx, "@TBD:被非法修改过");
					}
	
					if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						if (newInfo.getPreFormID() <= 0 && info.getPreFormID() > 0)
						{
							Log.print("===使用了提前还款通知单===");
							Log.print("===开始检查提前还款通知单状态===");
							if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK)){
								throw new IRollbackException(mySessionCtx,"提前还款通知单已经被使用");
							}
							Log.print("===开始改变提前还款通知单状态为已使用===");
							dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED);
						}
						if (newInfo.getFreeFormID() <= 0 && info.getFreeFormID() > 0)
						{
							Log.print("===使用了免还通知单===");
							Log.print("===开始检查免还通知单的状态===");
							if (!dao.isFreeFormStatusCorrect(info.getFreeFormID(),LOANConstant.FreeApplyStatus.CHECK)){
								throw new IRollbackException(mySessionCtx,"免还通知单已经被使用");
							}
							Log.print("===开始改变免还通知单的状态为已使用");
							dao.updateFreeFormStatus(info.getFreeFormID(),LOANConstant.FreeApplyStatus.USED);
						}
					}
					/**
					 * add by kevin(刘连凯)2012-06-25
					 * 解决还款通知单由：有-无-有时通知单状态没有变化的问题
					 * 开始改变免还通知单和提前还款通知单的状态
					 */
					if (info.getPreFormID()>0)
					{
						Log.print("===使用了提前还款通知单===");
						if(newInfo.getPreFormID()<0){
							Log.print("===开始检查提前还款通知单状态===");
							if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK)){
								throw new IRollbackException(mySessionCtx,"提前还款通知单已经被使用");
							}
						}
						
						Log.print("===开始改变提前还款通知单状态为已使用===");
						//增加判断，如果归还金额等于还款通知单金额，改变通知单状态为以使用，否则不改变其状态。华联新加，一个还款
						//通知单可以多次还清。
						double  AllAmount=  DataFormat.formatDouble(dao.getTotalMountByPReformid(info.getPreFormID(),info.getID()));//获得该通知单所有还款金额
			            double  noticeAmout = DataFormat.formatDouble(dao.getAMountByPReformid (info.getPreFormID()));  //获得还款通知单金额         
					    System.out.println("-----AllAmount-----===="+AllAmount+"=====noticeAmout====="+noticeAmout);
						AllAmount+=info.getAmount();
						if(DataFormat.formatDouble(AllAmount)==DataFormat.formatDouble(noticeAmout)){
							dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED);
						}else{
							if(newInfo.getPreFormID()>0){
								dao.updatePreFormStatus(newInfo.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
							}
						}
					}
					//如果修改的时候不再使用提前还款通知单，需要将还款通知单的状态改变.
					if(newInfo!=null&&newInfo.getPreFormID()>0&&info.getPreFormID()<0){
						dao.updatePreFormStatus(newInfo.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
					}			
					
					//删除账簿信息
					log.debug("====删除账簿信息====");
					TransRepaymentLoanInfo oldTransInfo = dao.findByID(info.getID());
					if (oldTransInfo == null)
					{
						log.debug("====无法找到交易对应的账户信息，交易失败====");
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					}
					/**
					 * 删除账簿信息需要判断交易类型.
					 */
					if (oldTransInfo.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE)
					{
						accountBookOperation.deleteMultiLoanReceive(oldTransInfo);
					}
					else 
					{
						accountBookOperation.deleteRepaymentLoan(oldTransInfo);
					}
				}
			}
			log.debug("====保存信息====");
			lID = this.partSave(info, dao);
			//保存账簿信息
			info.setID(lID);
			//保存账簿信息
			log.debug("====保存账簿信息====");
			/**
			 * 保存账簿信息,需要判断交易类型
			 */
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				accountBookOperation.saveMultiLoanReceive(info);
			}
			else {
				accountBookOperation.saveRepaymentLoan(info);
			}
			//更新状态到：保存（未复核）
			log.debug("====更新状态到：保存（未复核====");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		return lID;
		}
	}
	/**
	 * 检查是否更改
	 * 
	 * @param info
	 * @param dao
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	private boolean isTouch(TransRepaymentLoanInfo info, Sett_TransRepaymentLoanDAO dao) throws RemoteException, IRollbackException
	{
		try
		{
			log.debug("======是否被修改？======");
			Timestamp lastTouchDate = dao.findByID(info.getID()).getModify();
			Timestamp currentTouchDate = info.getModify();
			Log.print("数据库时间：" + lastTouchDate.toString());
			Log.print("当前纪录时间：" + currentTouchDate.toString());
			if (currentTouchDate == null || lastTouchDate == null || !lastTouchDate.toString().equals(currentTouchDate.toString()))
			{
				log.debug("======被修改======");
				log.debug("======currentTouchDate======" + currentTouchDate);
				log.debug("======lastTouchDate======" + lastTouchDate);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
	}
	/**
	 * 保存银团贷款利息信息
	 * @param info
	 * @param dao
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private void saveSyndicationLoan(long lReceiveLoanID,ArrayList list) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			if(list!=null && list.size()>0)
			{
				Iterator it =null;
				it=list.iterator();
				deletePhysicsSyndicationLoan(lReceiveLoanID);
				long id = dao.getMaxID();
				while(it!=null && it.hasNext())
				{					
					SyndicationLoanInterestInfo info =(SyndicationLoanInterestInfo)it.next();
//					if(info.getSyndicationLoanReceiveID()>0)
//					{
//						 dao.update(info);
//					}
//					else
//					{		
						
						info.setID(id);			
						info.setSyndicationLoanReceiveID(lReceiveLoanID);
						info.setStatusID(SETTConstant.TransactionStatus.SAVE);
						dao.add(info);
						id++;
					//}
				}
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
	}
	/**
	 * 根据ID查找银团贷款利息数据
	 * @param lReceiveLoanID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private ArrayList findSyndicationByReceiveLoanID(long lReceiveLoanID) throws RemoteException, IRollbackException
	{
		ArrayList list = null;
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			list = (ArrayList)dao.findBySyndicationLoanReceiveID(lReceiveLoanID);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
	    return list;
	}
	/**
	 * 删除银团贷款利息数据
	 * @param info
	 * @param dao
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	private void deleteSyndicationLoan(long lReceiveLoanID) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			dao.updateStatus(lReceiveLoanID,SETTConstant.TransactionStatus.DELETED);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
	}
	/**
	 * @param lReceiveLoanID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private void deletePhysicsSyndicationLoan(long lReceiveLoanID) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			dao.deletePhysicsSyndicationLoan(lReceiveLoanID);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
	}
	/**
	 * 保存TransRepaymentLoanInfo
	 * 
	 * @param info
	 * @param dao
	 * @return TransRepaymentLoanInfo ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long partSave(TransRepaymentLoanInfo info, Sett_TransRepaymentLoanDAO dao) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try
		{
			if (info.getID() > 0)
			{
				log.debug("----------修改信息-------------");
				dao.update(info);
				if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{				
					log.info("------------保存银团贷款信息----------");
					saveSyndicationLoan(info.getID(),info.getSyndicationLoanInterest());
				}
			}
			else
			{
				log.debug("----------增加信息-------------");
				long id = dao.add(info);
				
				if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{				
					log.info("------------增加银团贷款信息----------");
					saveSyndicationLoan(id,info.getSyndicationLoanInterest());
				}
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------是网银指令----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecute());
					financeInfo.setFinishDate(info.getExecute());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	/**
	 * 校验贷款收回信息
	 * 
	 * @param info
	 *            TransRepaymentLoanInfo
	 * @throws IRollbackException
	 */
	private void validateTransRepaymentLoanInfo(TransRepaymentLoanInfo info) throws IRollbackException
	{
		log.debug("----------校验中。。。。。。-------------");
		if (info.getID() > 0)
		{
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), info.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
		}
	}
	/**
	 * 根据id查找信托贷款收回信息
	 * 
	 * @param id
	 *            信托贷款收回信息id
	 * @return TransRepaymentLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo FindRepaymentDetailByID(long id) throws RemoteException, IRollbackException
	{
		Log.print("进入FindRepaymentDetailByID............");
		TransRepaymentLoanInfo info = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			Log.print("ID:" + id);
			info = dao.findByID(id);
			log.info("----------判断是否银团贷款----------------");
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				list = findSyndicationByReceiveLoanID(id);
				info.setSyndicationLoanInterest(list);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	/**
	 * 链接查询信托贷款收回信息集合
	 * 
	 * @param info
	 *            TrustLoanInfo
	 * @param orderType
	 * @param isDesc
	 * @return 信托贷款发放信息集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findByCondition(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			collection = dao.findByCondition(info);
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return collection;
	}
	/**
	 * 匹配查询信托贷款收回信息
	 * 
	 * @param info
	 * @return 信托贷款收回信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentTrustLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			helper.setInfo(info);
			helper.setStrict(true);
			if (info.getPreFormID() < -1)
			{
				helper.setNotMatch("getPreFormID");
			}
			if (info.getPayInterestAccountID() < -1)
			{
				helper.setNotMatch("getPayInterestAccountID");
			}
			if (info.getInterestBankID() < -1)
			{
				helper.setNotMatch("getInterestBankID");
			}
			if (info.getPaySuertyFeeAccountID() < -1)
			{
				helper.setNotMatch("getPaySuertyFeeAccountID");
			}
			if (info.getSuertyFeeBankID() < -1)
			{
				helper.setNotMatch("getSuertyFeeBankID");
			}
			if (info.getReceiveSuertyFeeAccountID() < -1)
			{
				helper.setNotMatch("getReceiveSuertyFeeAccountID");
			}

			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransRepaymentLoanInfo) iterator.next();
				log.info("----------判断是否银团贷款----------------");
				if(returninfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
					ArrayList list = null;
					list = findSyndicationByReceiveLoanID(returninfo.getID());
					returninfo.setSyndicationLoanInterest(list);
				}
				break;
			}
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * 匹配查询信托贷款收回信息
	 * 
	 * @param info
	 * @return 信托贷款收回信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentConsignLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			Log.print("进入match..........");
			helper.setInfo(info);
			helper.setHelpType(DAOHelper.HELPER_SELECT);
			helper.setStrict(true);
			helper.setNotEquals("getInputUserID");
			if (info.getPreFormID() < -1)
			{ //如果提前还款通知单不作为匹配条件
				helper.setNotMatch("getPreFormID");
			}
			if (info.getFreeFormID() < -1)
			{ //如果免还通知单不作为匹配条件
				helper.setNotMatch("getFreeFormID");
			}
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransRepaymentLoanInfo) iterator.next();
				break;
			}
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * 匹配查询利息收回信息
	 * 
	 * @param info
	 * @return 利息收回信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentInterestConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			Log.print("进入match..........");
			helper.setInfo(info);
			helper.setStrict(true);
			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransRepaymentLoanInfo) iterator.next();
				break;
			}
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
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
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			return dao.getIDByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
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
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			return dao.getIDByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
	}
	/**
	 * 通过交易号查找交易ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentGetIDByTransNoAndSerialNo(String strTransNo,long lSerialNo) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			return dao.getIDByTransNoAndSerialNo(strTransNo,lSerialNo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
	}

	/**
	 * 得到贷款条件信息
	 * 
	 * @param info
	 *            SubLoanAccountDetailInfo
	 * @return SubLoanAccountDetailInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubLoanAccountDetailInfo findSubLoanAccountDetailByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException
	{

		
		SubLoanAccountDetailInfo returnInfo = null;
		SubAccountLoanInfo subAccInfo = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		InterestOperation io = null;
		try
		{
			returnInfo = dao.findSubLoanAccountDetailByCondition(info);
			Log.print("----已经得到子账户详细信息----");
			Log.print("当前账户结清情况:"+(returnInfo.isAccountClear()?"已经结清":"没有结清"));
			
			if (returnInfo.isAccountClear()){
				Log.print("执行日:"+returnInfo.getExecute());
				Log.print("办事处:"+returnInfo.getOfficeID());
				Log.print("主账户:"+returnInfo.getLoanAccountID());
				Log.print("子账户:"+returnInfo.getSubAccountID());
				Log.print("起息日:"+returnInfo.getInterestStart());

				Log.print("----开始利息倒填----");
				sett_TransAccountDetailDAO tadiDAO = new sett_TransAccountDetailDAO();
				Collection col = tadiDAO.findByIsBackward(returnInfo.getLoanAccountID(),
						returnInfo.getSubAccountID(),
							returnInfo.getOfficeID(),
								returnInfo.getCurrencyID(),
									returnInfo.getExecute());
				Iterator iter = col.iterator();
				while(iter.hasNext()){
					TransAccountDetailInfo tadInfo = (TransAccountDetailInfo)iter.next();
					InterestBatch interestBatch = new InterestBatch();				
					
					//modified by mzh_fu 2007/12/14
/*					interestBatch.accountInterestCalculationBackward(
							returnInfo.getLoanAccountID(),
								returnInfo.getSubAccountID(),
									returnInfo.getInterestStart(),
										returnInfo.getAmount(),
											returnInfo.getOfficeID(),
												returnInfo.getCurrencyID(),
													returnInfo.getExecute(),
														SETTConstant.BooleanValue.ISFALSE);*/
					
					//added by mzh_fu 2007/12/14 begin
					long tmpTransAccountID = -1; // 交易账户
					long tmpSubAccountID = -1; // 交易子账户
					Timestamp interestStartDate = null; // 起息日
					double backAmount = 0.0;

					tmpTransAccountID = tadInfo.getTransAccountID();
					tmpSubAccountID = tadInfo.getTransSubAccountID();
					interestStartDate = tadInfo.getDtInterestStart();
					backAmount = tadInfo.getAmount();

					interestBatch.accountInterestCalculationBackward(
							tmpTransAccountID, tmpSubAccountID,
							interestStartDate, backAmount, info.getOfficeID(),
							info.getCurrencyID(), info.getExecute(),
							SETTConstant.BooleanValue.ISFALSE);
					//added by mzh_fu 2007/12/14 end
					
				}
				Log.print("----利息倒填结束----");
				
				//add by bingliu 20120307 解决以下问题
				/*自营贷款、委托贷款合同做利率调整（往历史调），结息，关机后再次结息，系统算出的利息没有减去上次已经结过的利息。
				 *结息之前没有倒填利息，会导致两个问题：1.结的利息不对；2.关机时会倒填利息，导致之后的利息都不正确，没有减去上次结的利息。
				 *解决办法：在结息之前的计算方法中加入利率倒算方法。
				 */
				log.info("-------------判断是否有单户利率倒填---------");
				Collection coll2 = null;
				coll2 = sett_SubAccountDAO.findAccountLoanBankInterestAdjust(info.getOfficeID(), info.getCurrencyID(), 
						returnInfo.getExecute(),info.getSubAccountID());
				Iterator itResult2 = null;
				if (coll2 != null && coll2.size() > 0)
				{
					InterestBatch ib = new InterestBatch();
					itResult2 = coll2.iterator();
					if (itResult2 != null && itResult2.hasNext())
					{
						while (itResult2.hasNext())
						{
							BankInterestAdjustInfo backinfo = (BankInterestAdjustInfo) itResult2.next();
							log.info("-------------开始单户利率倒填---------");
							long flag = ib.accountInterestCalculationBackward(backinfo.getAccountID(),
									backinfo.getSubAccountID(), backinfo.getBackDate(), 0, 
									info.getOfficeID(), info.getCurrencyID(),
									Env.getSystemDate(info.getOfficeID(), info
											.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
							long lUpdateReturn = sett_SubAccountDAO.updateLoanRateAdjustPayDetail(backinfo.getLoanRateAdjustPayDetailID());
							   log.debug("贷款利率调整详细信息更改标志lUpdateReturn = "+lUpdateReturn);
							if (flag < 0||lUpdateReturn < 0)
							{
								throw new IException("单户利率倒填失败");
							}
							log.info("-------------单户利率倒填结束---------");
						}
					}
				}
				log.info("-------------判断是否有单户利率倒填结束---------");
				
				Log.print("----判断贷款类型----");
				long lContractType = returnInfo.getContractType(); //合同类型
				long lLoanType = -1;								//贷款类型
				if (lContractType==LOANConstant.LoanType.YT
					||lContractType==LOANConstant.LoanType.ZGXE
					||lContractType==LOANConstant.LoanType.ZY){//如果是信托
					lLoanType = SETT_TRUST_LOAN;
					Log.print("当前交易为自营贷款收回");
				}
				else if(lContractType==LOANConstant.LoanType.WT ){//如果是委托
					lLoanType = SETT_CONSIGN_LOAN;
					Log.print("当前交易为委托贷款收回");
				}
				
				Log.print("----构造算息操作类----");
				io = new InterestOperation();
				
				/**
				 * 得到当前系统时间
				 */
				Timestamp tsSys = Env.getSystemDate(returnInfo.getOfficeID(),returnInfo.getCurrencyID());
				
				//本次利息
				Log.print("----开始计算利息----");
				
				Log.print("办事处:"+returnInfo.getOfficeID());
				Log.print("币种:"+returnInfo.getCurrencyID());
				Log.print("主账户:"+returnInfo.getLoanAccountID());
				Log.print("子账户:"+returnInfo.getSubAccountID());
				Log.print("起息日:"+returnInfo.getInterestStart());
				Log.print("执行日:"+returnInfo.getExecute());
				Log.print("系统时间:"+tsSys);
				
				LoanAccountInterestInfo interest = io.GetLoanAccountInterest(
						returnInfo.getOfficeID(),
						returnInfo.getCurrencyID(),
						returnInfo.getLoanAccountID(),
						returnInfo.getSubAccountID(),
						returnInfo.getInterestStart(),
						tsSys);
				returnInfo.setInterest(UtilOperation.Arith.round(interest.getInterest(), 2));
				
				//上一个结息日从子账户中获取 Boxu Update 2008年3月25日
				subAccInfo = sett_SubAccountDAO.findByID(returnInfo.getSubAccountID()).getSubAccountLoanInfo();
				returnInfo.setLatestClearInterest(subAccInfo.getClearInterestDate());
				//returnInfo.setLatestClearInterest(interest.getSDate());  //上次结息日
				
				double dInterestRate = UtilOperation.Arith.round(UtilOperation.Arith.div(interest.getRate(),100),6);
				returnInfo.setLoanRepaymentRate(dInterestRate);				//利率
				//计提利息
				Log.print("----开始计算计提利息----");
							
				AccountOperation ao = new AccountOperation();
				long lAccountType = ao.getAccountTypeBySubAccountID(returnInfo.getSubAccountID());
				SubAccountPredrawInterestInfo interestPredraw = io.getLoanAccountPredrawInterest(
						returnInfo.getLoanAccountID(),
						returnInfo.getSubAccountID(),
						lAccountType,
						returnInfo.getInterestStart());
				returnInfo.setInterestReceiveAble(interestPredraw.getPredrawInterest());
				Log.print("计提利息:"+interestPredraw.getPredrawInterest());
							
				//利息
				Log.print("----开始计算本次利息----");
				double dInterestIncome = UtilOperation.Arith.sub(UtilOperation.Arith.round(returnInfo.getInterest(),2),UtilOperation.Arith.round(returnInfo.getInterestReceiveAble(),2));
				returnInfo.setInterestIncome(dInterestIncome);
				Log.print("本次利息:"+returnInfo.getInterestIncome());
					//调用中国国电贷款逾期复利罚息计算方法 Boxu 2008-10-06
					//逾期复利
					Log.print("----开始计算复利----");
					LoanAccountInterestInfo compoundInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);		//最后一个是类型码
					returnInfo.setCompoundInterest(UtilOperation.Arith.round(compoundInterest.getInterest(),2));		//得到复利
					returnInfo.setCompoundInterestStart(compoundInterest.getSDate());	//得到复利起息日
					returnInfo.setCompoundAmount(compoundInterest.getBalance());		//得到复利本金
					double dCompoundRate = UtilOperation.Arith.round(UtilOperation.Arith.div(compoundInterest.getRate(),100),6);
					returnInfo.setCompoundRate(dCompoundRate);				//得到复利的利率
					Log.print("复利:"+compoundInterest.getInterest());
								
					//逾期罚息
					Log.print("----开始计算逾期罚息----");
					LoanAccountInterestInfo overDueInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.FORFEITINTEREST);//最后一个是类型码
					returnInfo.setOverDueInterest(UtilOperation.Arith.round(overDueInterest.getInterest(),2));		//得到逾期罚息
					returnInfo.setOverDueStart(overDueInterest.getSDate());				//得到逾期罚息起息日
					returnInfo.setOverDueAmount(overDueInterest.getBalance());			//得到逾期罚息本金
					double dOverDueRate = UtilOperation.Arith.round(UtilOperation.Arith.div(overDueInterest.getRate(),100),6);
					returnInfo.setOverDueRate(dOverDueRate);							//得到逾期罚息利率
					Log.print("逾期罚息:"+returnInfo.getOverDueInterest());			
				/*}
				else
				{
					//复利
					Log.print("----开始计算复利----");
					LoanAccountInterestInfo compoundInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);		//最后一个是类型码
					returnInfo.setCompoundInterest(compoundInterest.getInterest());		//得到复利
					returnInfo.setCompoundInterestStart(compoundInterest.getSDate());	//得到复利起息日
					returnInfo.setCompoundAmount(compoundInterest.getBalance());		//得到复利本金
					double dCompoundRate = UtilOperation.Arith.round(UtilOperation.Arith.div(compoundInterest.getRate(),100),6);
					returnInfo.setCompoundRate(dCompoundRate);				//得到复利的利率
					Log.print("复利:"+returnInfo.getCompoundInterest());
								
					//逾期罚息
					Log.print("----开始计算逾期罚息----");
					LoanAccountInterestInfo overDueInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.FORFEITINTEREST);//最后一个是类型码
					returnInfo.setOverDueInterest(overDueInterest.getInterest());		//得到逾期罚息
					returnInfo.setOverDueStart(overDueInterest.getSDate());				//得到逾期罚息起息日
					returnInfo.setOverDueAmount(overDueInterest.getBalance());			//得到逾期罚息本金
					double dOverDueRate = UtilOperation.Arith.round(UtilOperation.Arith.div(overDueInterest.getRate(),100),6);
					returnInfo.setOverDueRate(dOverDueRate);							//得到逾期罚息利率
					Log.print("逾期罚息:"+returnInfo.getOverDueInterest());
				}*/
				
				if (lLoanType == SETT_TRUST_LOAN){				//信托计算担保费
					//担保费
					Log.print("----开始计算担保费----");
					LoanAccountInterestInfo suretyInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.ASSURE);//最后一个是类型码
					returnInfo.setSuretyFee(UtilOperation.Arith.round(suretyInterest.getInterest(),2));
					returnInfo.setSuretyFeeStart(suretyInterest.getSDate());
					double dSuretyFeeRate = UtilOperation.Arith.round(UtilOperation.Arith.div(suretyInterest.getRate(),100),6);
					returnInfo.setSuretyFeeRate(dSuretyFeeRate);
					Log.print("担保费:"+returnInfo.getSuretyFee());
				}
				if (lLoanType == SETT_CONSIGN_LOAN){		//委托才计算手续费和利息税
					//手续费
				
					LoanAccountInterestInfo commission = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.COMMISION);		//最后一个是类型码
					returnInfo.setCommisson(UtilOperation.Arith.round(commission.getInterest(),2));
					returnInfo.setCommissionStart(commission.getSDate());
					double dCommissionRate = UtilOperation.Arith.round(UtilOperation.Arith.div(commission.getRate(),100),6);
					returnInfo.setCommissionRate(dCommissionRate);
					Log.print("手续费:"+returnInfo.getCommisson());
								
					//利息税费
					Log.print("----开始计算利息税费----");
					double dInterestShouldTax = 
						UtilOperation.Arith.add(
							returnInfo.getOverDueInterest(),
							UtilOperation.Arith.add(returnInfo.getInterest(),
								returnInfo.getCompoundInterest()));  //应缴税利息
					//modify by zcwang 2007-3-31 新方法从利率计划中取利息费率
					InterestTaxInfo tax = io.getInterestTaxByPlan(
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							dInterestShouldTax
							);
							//	
					returnInfo.setInterestPlanId(tax.getInterestTaxPlanId());
					SettTaxRatePlanSettingBiz planBiz = new SettTaxRatePlanSettingBiz();
					String allRateBuffer[] = planBiz.findAllInterestTaxRate(tax.getInterestTaxPlanId()).split(",");
					double interestTaxRateMain = 0.0;
					double interestTaxRate = 0.0;
					for(int i=0;i<allRateBuffer.length;i++)
					{
						if(i==0)
						{
							interestTaxRateMain=Double.valueOf(allRateBuffer[i]).doubleValue();
						}
						else 
						{
							interestTaxRate+=interestTaxRateMain*Double.valueOf(allRateBuffer[i]).doubleValue()/100;
						}
					}
					returnInfo.setInterestTaxRate(interestTaxRateMain+interestTaxRate);
					Log.print("利息税费率:"+returnInfo.getInterestTaxRate());
					returnInfo.setInterestTax(UtilOperation.Arith.round(tax.getInterestTax(),2));
					Log.print("利息税费:"+returnInfo.getInterestTax());
				}
			}
//			else{
//				//add by zhouxiang 20111026
//				//计算部分还款时的应付利息
//				PayNoticeOperation payOperation = new PayNoticeOperation();
//				double payInterest = payOperation.findRepayInterestByID(
//						info.getAmount(),
//						info.getPayFormID(),
//						info.getContractID(),
//						info.getInterestStart(),
//						info.getOfficeID(),
//						info.getCurrencyID()
//						);
//				returnInfo.setInterest(payInterest);
//				returnInfo.setInterestIncome(payInterest);
//				//add end
//			}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
			}
		}
		finally{
			if(io!=null){
				io.closeConnection();
			}
		}
		
		return returnInfo;
	}
	/**
	 * 删除贷款记录
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long id = -1;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			
			//对客户加锁
			Log.print("开始删除方法......");
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
			TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
			Log.print("errMsgLength:" + errMsg.length() + " errMsgSpace:" + (errMsg == ""));
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//删除交易记录
				Log.print("准备调用删除子账簿方法");
				TransRepaymentLoanInfo repaymentInfo = dao.findByID(info.getID());
				accountBookOperation.deleteRepaymentLoan(repaymentInfo);
				Log.print("成功调用删除子账簿方法");
				
				//修改网银指令状态
				if(repaymentInfo.getInstructionNo()!=null && repaymentInfo.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(repaymentInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
				/**
				 * 开始改变免还通知单和提前还款通知单的状态
				 */
				if (info.getStatusID()==SETTConstant.TransactionStatus.SAVE){
					if (info.getPreFormID()>0){
						Log.print("===使用了提前还款通知单===");
						Log.print("===开始检查提前还款通知单状态===");
						/* 由于一个还款通知单需要多次收回，对还款交易进行多次删除的时候，第一次删除还款交易还款通知单状态改变为
						 * 以审批，之后的删除还款交易无法删除，故注释掉此方法 add by wjyang3 2008-4-22
						if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED)){
							throw new IRollbackException(mySessionCtx,"提前还款通知单已经被修改");
						}
						*/
						Log.print("===开始改变提前还款通知单状态为已复核===");
						dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
					}
					
					if (info.getFreeFormID()>0){
						Log.print("===使用了免还通知单===");
						Log.print("===开始检查免还通知单的状态===");
						if (!dao.isFreeFormStatusCorrect(info.getFreeFormID(),LOANConstant.FreeApplyStatus.USED)){
							throw new IRollbackException(mySessionCtx,"免还通知单已经被修改");
						}
						Log.print("===开始改变免还通知单的状态为已复核");
						dao.updateFreeFormStatus(info.getFreeFormID(),LOANConstant.FreeApplyStatus.CHECK);
					}
				}
				//如果修改的时候不再使用提前还款通知单，需要将还款通知单的状态改变.
				TransRepaymentLoanInfo oldInfo = dao.findByID(info.getID());
				if(oldInfo!=null&&oldInfo.getPreFormID()>0&&info.getPreFormID()<0){
					dao.updatePreFormStatus(oldInfo.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
				}
				
			}
			//返回ID
			Log.print("到达逻辑删除点");
			log.info("------------判断是否银团贷款--------------");
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				deleteSyndicationLoan(info.getID());
			}
			return dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		}
	}
	/**
	 * 复核或者取消复核
	 * 
	 * @param info
	 * @param checkOrCancel
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransRepaymentLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long lStatus = -1; //当前操作的表单的状态,依据当前传入的checkOrCancel判断
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo transInfo = new TransRepaymentLoanInfo();
		UtilOperation utilOperation = new UtilOperation();
		SubAccountLoanInfo subAccountLoan = new SubAccountLoanInfo();
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		//对客户加锁
		try
		{
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		Log.print("检验状态前");
		if (checkOrCancel)
		{ //如果是已经复合的,检验是否取消复核,如果未复核,检验复合
			lStatus = SETTConstant.Actions.CHECK;
		}
		else
		{
			lStatus = SETTConstant.Actions.CANCELCHECK;
		}
		TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(info.getID());
		String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), lStatus);
		Log.print("检验状态后：" + errMsg);
		//被修改过
		if (errMsg != null && !errMsg.equals(""))
		{
			throw new IRollbackException(mySessionCtx, errMsg);
		}
		if (isTouch(info, dao))
		{
			throw new IRollbackException(mySessionCtx, "Sett_E133");
		}
		try
		{
			//dao.update(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try{
			transInfo = dao.findByID(info.getID());  //从数据库中查出记录
			transInfo.setCheckAbstract(info.getCheckAbstract());
			transInfo.setCheckUserID(info.getCheckUserID());
			
			log.info("----------判断是否银团贷款----------------");
			if(transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				list = findSyndicationByReceiveLoanID(transInfo.getID());
				transInfo.setSyndicationLoanInterest(list);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
		if (checkOrCancel)
		{
			/**
			 * 复核交易记录,需要判断交易类型 
			 */
			try
			{
			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				Log.print("====调用多笔贷款收回复核方法====");
				accountBookOperation.checkMultiLoanReceive(transInfo);
			}
			else {
				accountBookOperation.checkRepaymentLoan(transInfo);
			}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),e);}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			try
			{
				dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------是网银指令----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecute());
					financeInfo.setFinishDate(info.getExecute());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
				Log.print("开始复核当前余额");
				dao.updateCurrentBalance(info.getID(),info.getSubAccountID());
				
				//Modify by leiyang  date 2007-6-14
				/*subAccountLoan = dao.getSubAccountLoanInfoByID(info.getSubAccountID());
				
				//是否免还剩余贷款利息
				if(info.getIsRemitInterest() == 1)
				{
					subAccountLoan.setInterest(0.0);
					
					subAccountLoan.setPreDrawInterest(0.0);
				}
				else 
				{
					if(subAccountLoan.getBalance()==0.0) //贷款收回,当贷款所有金额全部结清时,结算利息等,将子账户金额利息字段清零.
					{
						subAccountLoan.setInterest(info.getInterest() - info.getRealInterest());
						
						//按贷款实际支取比例扣减计提利息 Boxu Add 2008年1月30日
						if(info.getInterestReceiveAble() > 0)
						{
							subAccountLoan.setPreDrawInterest(UtilOperation.Arith.sub(info.getInterestReceiveAble(), info.getRealInterestReceiveAble()));
						}
					}
				}
					
				//是否免还剩余复利
				if(info.getIsRemitCompoundInterest() == 1) {
					subAccountLoan.setCompoundInterest(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //贷款收回,当贷款所有金额全部结清时,结算利息等,将子账户金额利息字段清零.
					{
						subAccountLoan.setCompoundInterest(info.getCompoundInterest() - info.getRealCompoundInterest());
					}
				}
				//是否免还剩余逾期罚息
				if(info.getIsRemitOverDueInterest() == 1) {
					subAccountLoan.setOverDueInterest(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //贷款收回,当贷款所有金额全部结清时,结算利息等,将子账户金额利息字段清零.
					{
						subAccountLoan.setOverDueInterest(info.getOverDueInterest() - info.getRealOverDueInterest());
					}
				}
				//是否免还剩余担保费
				if(info.getIsRemitSuretyFee() == 1) {
					subAccountLoan.setSuretyFee(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //贷款收回,当贷款所有金额全部结清时,结算利息等,将子账户金额利息字段清零.
					{
						subAccountLoan.setSuretyFee(info.getSuretyFee() - info.getRealSuretyFee());
					}
				}
				//是否免还剩余手续费
				if(info.getIsRemitCommission() == 1) {
					subAccountLoan.setCommission(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //贷款收回,当贷款所有金额全部结清时,结算利息等,将子账户金额利息字段清零.
					{
						subAccountLoan.setCommission(info.getCommission() - info.getRealCommission());
					}
				}
				//利息税费
				if(subAccountLoan.getBalance()==0.0) //贷款收回,当贷款所有金额全部结清时,结算利息等,将子账户金额利息字段清零.
				{
					subAccountLoan.setInterestTax(info.getInterestTax() - info.getRealInterestTax());
				}
				if(subAccountLoan.getBalance()==0.0) //贷款收回,当贷款所有金额全部结清时,结算利息等,将子账户金额利息字段清零.
				{
					dao.updateSubAccountLoanInfo(subAccountLoan);
				}*/
				
				//modify by zcwang 2007-6-1 借用大唐修改
				//有一笔贷款在本金已经还清，做还款业务时对利息做了免还处理，但季度结息时仍然能够结算出当时做了免还的利息金额
				//if(info.getIsRemitInterest() == 1)
				//	dao.updateCurrentBalance2(info.getSubAccountID());
				
			}
			//modified by mzh_fu 2007/05/011
			/*catch (RemoteException e)
			{
				throw e;
			}
			catch (IRollbackException e)
			{
				throw e;
			}*/
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			//是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			
			try
			{
				if(bIsValid) {
					Log.print("*******************开始产生贷款收回指令，并保存**************************");
					try {
						log.debug("------开始贷款收回--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setInputUserID(info.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成贷款收回指令成功--------");
						
					} catch (Throwable e) {
						log.error("生成贷款收回指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成贷款收回指令失败："+e.getMessage());
					}
				}	
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
			}
			catch (Exception e)
			{
				log.debug("-----保存贷款收回指令指令失败");
				throw new IRollbackException(mySessionCtx, "保存贷款收回指令出错！" + e.getMessage());
			}
		
		}
		else
		{
			/**
			 * 取消复核交易记录,需要判断交易类型 
			 */
			try
			{
			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				Log.print("====调用多笔贷款收回取消复核方法====");
				accountBookOperation.cancelCheckMultiLoanReceive(transInfo);
			}
			else {
				accountBookOperation.cancelCheckRepaymentLoan(transInfo);
			}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),e);}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			//更新状态到：未复核
			try
			{
				//Added by zwsun , 2007/8/4, 判断是否挂了审批流
				InutParameterInfo pInfo=new InutParameterInfo();
				pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				pInfo.setOfficeID(info.getOfficeID());
				pInfo.setCurrencyID(info.getCurrencyID());
				pInfo.setTransTypeID(info.getTransactionTypeID());
				pInfo.setActionID(-1);
				if(FSWorkflowManager.isNeedApproval(pInfo)){
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.APPROVALED, info.getCheckAbstract(), info.getCheckUserID());					
				}else{
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());					
				}
										
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------是网银指令----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecute());
					financeInfo.setFinishDate(info.getExecute());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
				Log.print("开始复核当前余额");
				dao.updateCurrentBalance(info.getID(),info.getSubAccountID());
				
				//Boxu Update 2008年3月28日 开机日当天利息费用支付取消复核没有问题,但是结息日提前,子账户利息回填错误
				//Boxu Update 2008年4月9日 发现在回写处理担保费的时候下面的方法处理错误
				/*if(info.getInterestClear().compareTo(Env.getSystemDate(info.getOfficeID(), info.getCurrencyID())) == 0)
				{
					//Modify by leiyang  date 2007-6-14
					subAccountLoan = dao.getSubAccountLoanInfoByID(info.getSubAccountID());
					//贷款利息
					if(info.getInterest()!=0.0)
					{
						subAccountLoan.setInterest(info.getInterest());
					}
					//复利
					if(info.getCompoundInterest()!=0.0)
					{
						subAccountLoan.setCompoundInterest(info.getCompoundInterest());
					}
					//逾期罚息
					if(info.getOverDueInterest()!=0.0)
					{
						subAccountLoan.setOverDueInterest(info.getOverDueInterest());
					}
					//担保费
					if(info.getSuretyFee()!=0.0)
					{
						subAccountLoan.setSuretyFee(info.getSuretyFee());
					}
					//手续费
					if(info.getCommission()!=0.0)
					{
						subAccountLoan.setCommission(info.getCommission());
					}
					//利息税费
					if(info.getInterestTax()!=0.0)
					{
						subAccountLoan.setInterestTax(info.getInterestTax());
					}
					//计提利息
					if(info.getInterestReceiveAble()!=0.0)
					{
						subAccountLoan.setPreDrawInterest(info.getInterestReceiveAble());
					}
					dao.updateSubAccountLoanInfo(subAccountLoan);
				}*/
			}
			catch (Exception e)
			{
				if(e instanceof IRollbackException){
					throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
				}else{
					throw new IRollbackException(mySessionCtx, e.getMessage(), e);
				}
			}
		}
		//解锁
		try
		{
			if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	
	public boolean squareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Log.print("====开始进入勾账方法====");
		long[] sessionIDs = null;
		//DAO
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//总账操作接口类
		GeneralLedgerOperation glOperation = new GeneralLedgerOperation();

		try
		{
			Log.print("----开始对客户加锁----");
			sessionIDs = new long[infos.length];
			for (int i = 0; i < infos.length; i++)
			{
				sessionIDs[i] = utilOperation.waitUtilSuccessLock(infos[i].getLoanAccountID());
			}
			
			//校验状态是否正确
			for (int i = 0; i < infos.length; i++)
			{
				TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(infos[i].getID());

				String errMsg = "";

				errMsg =
					UtilOperation.checkStatus(
						infos[i].getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.SQUAREUP);

				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}

				//判断是否被修改
				if (isTouch(infos[i], dao))
					throw new IRollbackException(mySessionCtx, "Sett_E047");
			}
			
			//交易收付平衡
			double dPayAmount = 0.0;
			double dReceiveAmount = 0.0;
			for (int i = 0; i < infos.length; i++)
			{
				
				if(infos[i].getTransDirectionID() == SETTConstant.MultiLoanType.PAYMENT)
				{
					dPayAmount += infos[i].getAmount();
				}
				else if(infos[i].getTransDirectionID() == SETTConstant.MultiLoanType.TRUSTLOAN ||
						infos[i].getTransDirectionID() == SETTConstant.MultiLoanType.CONSIGNLOAN)
				{
					dReceiveAmount += infos[i].getAmount();
				}
			}
			
			if(dPayAmount != dReceiveAmount)
			{
				throw new IRollbackException(mySessionCtx, "收付金额不平衡，勾账失败");
			}
			Log.print("----校验收付平衡结束----");
			//获得正式交易号
			log.debug("------开始获取正式交易号--------");
			String transNo = utilOperation.getNewTransactionNo(infos[0].getOfficeID(), infos[0].getCurrencyID(),infos[0].getTransactionTypeID());
			log.debug("------正式交易号是:" + transNo + "--------");

			for (int i = 0; i < infos.length; i++)
			{
				infos[i].setTransNo(transNo);
				infos[i].setSerialNo(i+1);
				
				dao.updateTransNo(infos[i].getID(),transNo,i+1);
			}			

			//勾账交易记录
			log.info("--------------开始AccountBook勾账交易记录--------------");
			for (int i = 0; i < infos.length; i++)
			{
				log.info("--------------开始勾账交易记录:" + infos[i].getID() + "--------------");
				accountBookOperation.squareMultiLoanReceive(infos[i]);

				dao.updateStatus(infos[i].getID(), SETTConstant.TransactionStatus.CIRCLE);
				log.info("--------------结束勾账交易记录:" + infos[i].getID() + "--------------");

			}

			//生成银行指令(针对SEFC)
			if (infos[0].getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE)
			{
				//判断是否启用银企接口
				//if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false)) {
					//构造参数
				  if(false){//中交项目不需要生成利息指令
					CreateInstructionParam instructionParam = new CreateInstructionParam();
					instructionParam.setTransactionTypeID(infos[0].getTransactionTypeID());						
					instructionParam.setTransNo(infos[0].getTransNo());
					instructionParam.setOfficeID(infos[0].getOfficeID());
					instructionParam.setCurrencyID(infos[0].getCurrencyID());
					instructionParam.setCheckUserID(infos[0].getCheckUserID());
					instructionParam.setInputUserID(infos[0].getInputUserID());
					
					//生成银行指令并保存
					IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
					bankInstruction.createBankInstructionFromTransDetail(instructionParam);
						
				}
				else {
					log.info("当前系统没有提供银企接口服务！");
				}
			}

			log.info("--------------结束AccountBook勾账交易记录--------------");
			
			log.debug("-----检查本交易号产生的分录是否借贷平衡-----");
			boolean checkRes = glOperation.checkTransDCBalance(transNo);
			if (!checkRes)
			{
				log.debug("-----借贷平衡不平衡，分录产生失败-------");
				throw new IRollbackException(mySessionCtx, "借贷平衡不平衡，分录产生失败");
			}	
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		finally
		{
			//解锁
			if (sessionIDs != null)
			{
				for (int i = 0; i < sessionIDs.length; i++)
				{
					if (sessionIDs[i] != -1)
					{
						//初始值已经改变，说明已经加锁，因此需要解锁
						try
						{
							utilOperation.releaseLock(infos[i].getLoanAccountID(), sessionIDs[i]);
						}
						catch (SQLException e)
						{
							throw new IRollbackException(mySessionCtx, e.getMessage(), e);
						}
					}
				}
			}
		}

		return true;
		}
	}

	public boolean cancelSquareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long[] sessionIDs = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//对客户加锁
			sessionIDs = new long[infos.length];
			for (int i = 0; i < infos.length; i++)
			{
				sessionIDs[i] = utilOperation.waitUtilSuccessLock(infos[i].getLoanAccountID());
			}

			//校验状态是否正确
			for (int i = 0; i < infos.length; i++)
			{
				TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(infos[i].getID());

				String errMsg = "";

				errMsg =
					UtilOperation.checkStatus(
						infos[i].getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.CANCELSQUAREUP);

				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}

				//判断是否被修改
				if (isTouch(infos[i], dao))
					throw new IRollbackException(mySessionCtx, "Sett_E051");
			}

			//删除正式交易号
			for (int i = 0; i < infos.length; i++)
			{				
				dao.updateTransNo(infos[i].getID(),null,-1);
			}			

			//取消勾账交易记录
			log.info("--------------开始AccountBook取消勾账交易记录--------------");
			for (int i = 0; i < infos.length; i++)
			{
				log.info("--------------开始取消勾账交易记录:" + infos[i].getID() + "--------------");
				accountBookOperation.cancelSquareMultiLoanReceive(infos[i]);

				dao.updateStatus(infos[i].getID(), SETTConstant.TransactionStatus.CHECK);
				log.info("--------------结束取消勾账交易记录:" + infos[i].getID() + "--------------");

			} 
			log.info("--------------结束AccountBook取消勾账交易记录--------------");
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		finally
		{
			//解锁
			if (sessionIDs != null)
			{
				for (int i = 0; i < sessionIDs.length; i++)
				{
					if (sessionIDs[i] != -1)
					{
						//初始值已经改变，说明已经加锁，因此需要解锁
						try
						{
							utilOperation.releaseLock(infos[i].getLoanAccountID(), sessionIDs[i]);
						}
						catch (SQLException e)
						{
							throw new IRollbackException(mySessionCtx, e.getMessage(), e);
						}
					}
				}
			}
		}

		return true;
		}
	}
	/**
	 * 审批流：审批方法(自营/委贷)
	 * Added by zwsun, 2007-06-21
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransGrantLoanInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getID();
		try
		{
			TransGrantLoanInfo loanInfo = new TransGrantLoanInfo();
			loanInfo=dao.findByID(info.getID());
			inutParameterInfo.setDataEntity(loanInfo);
			//提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				dao.updateStatus(
					info.getID(),
					SETTConstant.TransactionStatus.APPROVALED);
				//如果是自动复核
				if(FSWorkflowManager.isAutoCheck())
				{
					TransGrantLoanInfo loanInfo1 = new TransGrantLoanInfo();
					loanInfo1=dao.findByID(info.getID());
					//构造check参数
					//TransFixedOpenInfo depositInfo = new TransFixedOpenInfo();
					//depositInfo = this.openFindByID(info.getID());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setAbstract("机核");
					loanInfo1.setCheckUserID(returnInfo.getUserID());	//最后审批人为复核人				
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					check(loanInfo1,true);
				}	
			}
			//	如果是最后一级,且为审批拒绝,更新状态为已保存
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(
						info.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return loanId;
		}
	}	
	/**
	 * 审批流：取消审批方法（自营/委贷）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransGrantLoanInfo loanInfo)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransGrantLoanDAO loanDao = new Sett_TransGrantLoanDAO();		
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				this.check(loanInfo,false);
				//取消审批
				lReturn = loanDao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//取消审批
				lReturn = loanDao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
			}
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}	
	
	
	/**
	 * 多笔贷款收回勾账查询
	 * @param condiInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findSquareUpRecordsByCondition(TransRepaymentLoanInfo condiInfo)throws RemoteException,IRollbackException{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Collection coll = null;
		try{
			coll = dao.findSquareUpRecordsByCondition(condiInfo);
		}
		catch (IException e){
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		catch (Exception e){
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return coll;
	}
	
	/**
	 * 通过交易号查询多笔贷款收回的结果集
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection getRepaymentCollectionByTransNo(String strTransNo)throws RemoteException,IRollbackException{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Log.print("----开始进入getRepaymentCollectionByTransNo----");
		Log.print("----传入的交易号是:"+ strTransNo + "----");
		Collection coll = null;
		try{
			coll = dao.getRepaymentCollectionByTransNo(strTransNo);
		}catch (IException e){
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx,e.getMessage(),e);
			}
		}catch (Exception e){
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return coll;
	}
	/***************************************************************************************************************************************************************************************************
	 * ******************************贷款收回结束*************************** ****************************************************************
	 */
	/*
	 * 根据放款通知单ID查找信托贷款收回信息
	 * 
	 * @param id 信托贷款发放信息的放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection grantFindInterestByLoanNoteID(long lLoanNoteID,long nOfficeID,long nCurrencyID,long lLoanAccountID,long lContractID,long lSubAccountID) throws RemoteException, IRollbackException
	{
		Collection c = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
		AccountInfo accountInfo = null;
       //根据SubAccountID查找对应的子账户记录
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
		try
		{
			try
			{
				accountInfo = sett_AccountDAO.findByID(lLoanAccountID);
				if (accountInfo == null)
				{
					throw new IException(true, "主账户表中没有对应记录，查询失败", null);
				}
			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			try
			{
				subAccountAssemblerInfo = sett_SubAccountDAO.findByID(lSubAccountID);
				resultInfo.setID(lSubAccountID);
				resultInfo = sett_SubAccountDAO.querySubAccountInfo(resultInfo);
				if (subAccountAssemblerInfo == null)
				{
					throw new IException(true, "子账户表中没有对应记录，查询失败", null);
				}

			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			Timestamp tsLastInterestDate = resultInfo.getClearInterestDate();//通过子账户获取上一个结息日
					
			Log.print("根据放款通知单ID查找信托贷款发放信息1:" + lLoanNoteID);
			c = dao.findInterestByNoteID(lLoanNoteID,nOfficeID,nCurrencyID,resultInfo.getClearInterestDate());
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return c;
	}
	
	/**
	 * 根据合同和放款通知单ID查询实际支付的利息、手续费等汇总信息
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo grantFindInterestByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException
	{
		TransRepaymentLoanInfo reInfo=null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try{
			reInfo=dao.grantFindInterestByCondition(info);
			
		}catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return reInfo;
	}
}
