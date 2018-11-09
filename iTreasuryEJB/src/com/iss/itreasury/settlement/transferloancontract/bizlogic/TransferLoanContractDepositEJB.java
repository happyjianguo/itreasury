/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transferloancontract.bizlogic;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;

import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterparBankDao;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.bizlogic.TransferContractBiz;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferNoticeDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transferinterest.bizlogic.DateUtil;
import com.iss.itreasury.settlement.transferinterest.dataentity.TransferInterestRecordInfo;
import com.iss.itreasury.settlement.transferloancontract.dao.Sett_TransferLoanContractDepositDAO;
import com.iss.itreasury.settlement.transferloancontract.dao.Sett_TransferLoanContractDetailDepositDAO;
import com.iss.itreasury.settlement.transferloancontract.dao.TransferLoanAmountDetailDao;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetailConditionInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetialResultInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanAmountDetailinfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractDetailInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransferLoanContractDepositEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private final static  Object lockObj = new Object();  //静态
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
		throws RemoteException
	{
		mySessionCtx = ctx;
	}

	private boolean isTouch(
			TransferLoanContractInfo info,
			Sett_TransferLoanContractDepositDAO dao)
		throws RemoteException, IRollbackException
	{
		try
		{
			//判断是否被非法修改
			Timestamp lastTouchDate;
			lastTouchDate = dao.findTouchDate(info.getID());
			Timestamp curTouchDate = info.getNMODIFY();
			if (curTouchDate == null
				|| lastTouchDate.compareTo(curTouchDate) != 0)
				return true;
			else
				return false;
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(
				mySessionCtx,
				"SQLException in TransCurrentDepositEJB",
				e);
		}
	}
	/**
	 * 
	 * Method check
	 * @descriptin  信贷资产转让处理复核和取消复核
	 * @param  checkOrCancelCheck 复核还是取消复核
	 * @return long 
	 * @throws RemoteException，IException
	 */
	public long transferCheck(boolean checkOrCancel, TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransferLoanContractDepositDAO dao = new Sett_TransferLoanContractDepositDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		TransferLoanContractInfo transInfo = new TransferLoanContractInfo();
		TransferContractInfo contractInfo = new TransferContractInfo();
		if(checkOrCancel)
		{
            //复核
			TransferLoanContractInfo newInfo = this.findByID(info);
			String errMsg = UtilOperation.checkStatus(info.getSTATUSID(), newInfo.getSTATUSID(), SETTConstant.Actions.CHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}	
			info.setSTATUSID(SETTConstant.TransactionStatus.CHECK);
		}
		else
		{
            //取消复核
			TransferLoanContractInfo newInfo = this.findByID(info);
			String errMsg = UtilOperation.checkStatus(info.getSTATUSID(), newInfo.getSTATUSID(), SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			info.setSTATUSID(SETTConstant.TransactionStatus.SAVE);
			
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
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try
		{
			TransferNoticeInfo noticeInfo = new TransferNoticeInfo();			
			TransferNoticeDao noticeDao = new TransferNoticeDao();
			TransferContractBiz contractBiz = new TransferContractBiz();
			noticeInfo.setId(transInfo.getNOTICEID());
			noticeInfo = noticeDao.findByID(noticeInfo);		//查找数据库中转让合同ID
			contractInfo  = contractBiz.findInfoById(noticeInfo.getCracontractId());   //通过合同ID查找转让操作类型
			transInfo.setTRANSFERCONTRACTID(noticeInfo.getCracontractId());
			transInfo.setTRANSFERTYPE(contractInfo.getTranstypeId());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try
		{			
			TransferInterestRecordInfo recordInfo = new TransferInterestRecordInfo();
			recordInfo = dao.getTransferPreDrawDateInterest(contractInfo);
			double unPreDrawInterest = 0.0;
			double perDrawInterest = 0.0;
			//说明：首先查询计提记录表，针对该笔转让合同有计提记录的话，从计提日到该笔交易的起息日计算的利息为本次未计提的利息
			//若计提记录表没有数据的话，本次利息就是本次未计提利息，计提利息为零
			if(recordInfo.getDtinterestsettlement()!=null)
			{
			   unPreDrawInterest =  DateUtil.caculateInterest(
						  transInfo.getAMOUNT() 
						, recordInfo.getDtinterestsettlement()
						, info.getINTERESTSTART()
						, DateUtil.InterestCalculationMode.FACTDAY
						, contractInfo.getDrate()
						, DateUtil.InterestRateTypeFlag.YEAR
						, DateUtil.InterestRateDaysFlag.DAYS_360 );
			
			   perDrawInterest = UtilOperation.Arith.sub (info.getINTEREST(),UtilOperation.Arith.round(unPreDrawInterest,2));
			}
			else
			{
			  unPreDrawInterest = info.getINTEREST();
			}			 
			transInfo.setPreDrawInterest(perDrawInterest);
			transInfo.setUnPreDrawInterest(UtilOperation.Arith.round(unPreDrawInterest,2));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		if(checkOrCancel)
		{
            //复核交易记录
			accountBookOperation.checkTransferContract(transInfo);
            //是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			//是否需要生成银行指令
			boolean bCreateInstruction = false;
			long bankID = info.getPAYBANKID();
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
					Log.print("*******************开始产生资产转让付款指令，并保存**************************");
					try {
						log.debug("------开始资产转让付款指令生成--------");
						//卖出买断付款业务中,手续费为先扣收特殊处理,回购付款和后返还付款时手续费字段为空
						info.setAMOUNT(info.getAMOUNT()-info.getCOMMISSION());
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTRANSACTIONTYPEID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOFFICEID());
						instructionParam.setCurrencyID(info.getCURRENCYID());
						instructionParam.setCheckUserID(info.getCHECKUSERID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getINPUTUSERID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成资产转让付款指令成功--------");
						
					} catch (Throwable e) {
						log.error("生成资产转让付款指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成资产转让付款指令失败："+e.getMessage());
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
		  accountBookOperation.cancelCheckTransferContract(transInfo);
		  
		try
		{
			
		   dao.updateCheckUser(info.getID(), -1);
		   
		}
		catch(Exception e){
			
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
		return info.getID();
		}
	}


	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long tempSave(TransferLoanContractInfo info)
		throws RemoteException, IRollbackException
	{
		Sett_TransferLoanContractDepositDAO transferDepositDao =
			new Sett_TransferLoanContractDepositDAO();
		long depositId = partSave(info, transferDepositDao);
		//更新状态到：暂存
		try
		{
			transferDepositDao.updateStatus(info.getID(),SETTConstant.TransactionStatus.TEMPSAVE);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return depositId;
	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long preSave(TransferLoanContractInfo info)
		throws RemoteException, IRollbackException
	{
		//long sessionID = -1;
		long depositId = -1;
		Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
		TransferNoticeDao noticeDAO = new TransferNoticeDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try{
            //对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//获取当前交易号
			String transNo = info.getSTRANSNO();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.equalsIgnoreCase(""))
			{  //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				transNo =
					utilOperation.getNewTransactionNo(
						info.getOFFICEID(),
						info.getCURRENCYID(),
						info.getTRANSACTIONTYPEID());
				info.setSTRANSNO(transNo);
				log.debug("------新交易号是:" + transNo + "--------");
			}
			else
			{
                //被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("------开始判断是否被非法修改过--------");
				//校验状态是否正确
				TransferLoanContractInfo newInfo =
					transferDepositDao.findByID(info.getID());
				if (newInfo == null)
				{
					throw new IRollbackException(
						mySessionCtx,
						"无法找到交易ID对应的旧交易信息，交易失败");
				}
				String errMsg =
					UtilOperation.checkStatus(
						info.getSTATUSID(),
						newInfo.getSTATUSID(),
						SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, transferDepositDao))
				{
					log.debug("------被非法修改过--------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
			}
			if(info.getNOTICEID()>0)
			{
				noticeDAO.updateStatus(info.getNOTICEID(),CRAconstant.TransactionStatus.USED);
			}
			depositId  = partSave(info, transferDepositDao);
			info.setID(depositId);
			transferDepositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				//if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				//	utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(
					mySessionCtx,
					"@TBD:Error Code--",
					e);
			}
		}
		return depositId;
	}
	

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long delete(TransferLoanContractInfo info)
		throws RemoteException, IRollbackException
		{
		        Sett_TransferLoanContractDepositDAO transferDepositDao =  new Sett_TransferLoanContractDepositDAO();
		        TransferNoticeDao noticeDAO = new TransferNoticeDao();
                //工具操作接口类
			    UtilOperation utilOperation = new UtilOperation();
			 try{
                    //校验状态是否正确
					TransferLoanContractInfo newInfo = transferDepositDao.findByID(info.getID());
					if (newInfo == null)
					{
						throw new IRollbackException(
							mySessionCtx,
							"无法找到交易ID对应的旧交易信息，交易失败");
					}
					String errMsg =
						UtilOperation.checkStatus(
							info.getSTATUSID(),
							newInfo.getSTATUSID(),
							SETTConstant.Actions.DELETE);
                    //被修改过
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					if (isTouch(info, transferDepositDao))
					{
						log.debug("------被非法修改过--------");
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					if(info.getNOTICEID()>0)
					{
						noticeDAO.updateStatus(info.getNOTICEID(),CRAconstant.TransactionStatus.APPROVALED);
					}
					return transferDepositDao.delete(info.getID());
					
			 }
			 catch (Exception e)
				{
					throw new IRollbackException(mySessionCtx, e.getMessage(), e);
				}
		}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param TransferLoanContractInfo info
	 * @return TransferLoanContractInfo
	 */
	public TransferLoanContractInfo transferMatch(TransferLoanContractInfo info)throws RemoteException, IRollbackException
	{

		Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
				
		try
		{			
			return transferDepositDao.match(info);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	private long partSave(
			TransferLoanContractInfo info,
			Sett_TransferLoanContractDepositDAO transferDepositDao)
		throws RemoteException, IRollbackException
	 {
		synchronized(lockObj){

		try
		{

			if (info.getID()<0)
			{
				//新增交易信息
				transferDepositDao.add(info);
				if(info.getRepaycoll()!=null&&info.getRepaycoll().size()>0)
				{
					TransferLoanAmountDetailDao detaildao=new TransferLoanAmountDetailDao();
					TransferLoanAmountDetailinfo detailinfo=null;
					for(int i=0;i<info.getRepaycoll().size();i++)
					{
						detailinfo=(TransferLoanAmountDetailinfo)info.getRepaycoll().toArray()[i];
						detailinfo.setTransferamountid(info.getID());
						detaildao.add(detailinfo);
					}
				}
			}
			else
				//更新
			{
				transferDepositDao.update(info);
				if(info.getRepaycoll()!=null&&info.getRepaycoll().size()>0)
				{
					TransferLoanAmountDetailDao detaildao=new TransferLoanAmountDetailDao();
					TransferLoanAmountDetailinfo detailinfo=null;
					for(int i=0;i<info.getRepaycoll().size();i++)
					{
						detailinfo=(TransferLoanAmountDetailinfo)info.getRepaycoll().toArray()[i];
						detaildao.update(detailinfo);
					}
				}
			}			
	   }

		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info.getID();
		}
	}
	public Collection findByConditions(TransferLoanContractInfo info)
			throws RemoteException, IRollbackException
		{
	      
		  Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
			try
			{
				return transferDepositDao.findByConditions(info);
			}
			catch (RemoteException e)
			{
				throw e;
			}
			catch (IRollbackException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
	public TransferLoanContractInfo findByID(TransferLoanContractInfo info)throws RemoteException, IRollbackException
    {
  
	          Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
		   try
		     {
			     return transferDepositDao.findByID(info);
		     }
		   catch (RemoteException e)
		     {
			     throw e;
		     }
		  catch (IRollbackException e)
		     {
			    throw e;
		     }
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
    }
	/**
	 * 查询代收收款通知单和收款业务明细组装数据
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findNoticeAndAgentDetial(NoticeAndAgentDetailConditionInfo info)throws RemoteException, IRollbackException
    {
  
	       Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
		    try
	        {
			     return transferDepositDao.findNoticeAndAgentDetial(info);
		    }
		    catch (RemoteException e)
		    {
			     throw e;
		    }
		    catch (IRollbackException e)
		    {
			    throw e;
		    }
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
    }
	/**
	 * 
	 * Method check
	 * @descriptin  信贷资产转让收款处理复核和取消复核
	 * @param  checkOrCancelCheck 复核还是取消复核
	 * @return long 
	 * @throws RemoteException，IException
	 */
	public long repaytransferCheck(boolean checkOrCancel, TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransferLoanContractDepositDAO dao = new Sett_TransferLoanContractDepositDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		TransferLoanContractInfo transInfo = new TransferLoanContractInfo();
		AccountOperation accountOperation =new AccountOperation();
		TransferLoanAmountDetailDao detaildao= new TransferLoanAmountDetailDao();
		Collection coll=null;
		long loanSubAccountID=-1;
		if(checkOrCancel)
		{
            //复核
			TransferLoanContractInfo newInfo = this.findByID(info);
			String errMsg = UtilOperation.checkStatus(info.getSTATUSID(), newInfo.getSTATUSID(), SETTConstant.Actions.CHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}	
			info.setSTATUSID(SETTConstant.TransactionStatus.CHECK);
		}
		else
		{
            //取消复核
			TransferLoanContractInfo newInfo = this.findByID(info);
			String errMsg = UtilOperation.checkStatus(info.getSTATUSID(), newInfo.getSTATUSID(), SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			info.setSTATUSID(SETTConstant.TransactionStatus.SAVE);
			
		}
		try
		{
			if(dao.updateCheckStatus(info)<=0)
			{
				throw new IRollbackException(mySessionCtx,"该笔业务已经处理！");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try{
			
			transInfo = dao.findByID(info.getID());		//查找数据库中的完整纪录
			coll=detaildao.findDetailinfoByTransferId(info);
			transInfo.setRepaycoll(coll);
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try
		{
			TransferNoticeInfo noticeInfo = new TransferNoticeInfo();
			TransferContractInfo contractInfo = new TransferContractInfo();
			TransferNoticeDao noticeDao = new TransferNoticeDao();
			TransferContractBiz contractBiz = new TransferContractBiz();
			noticeInfo.setId(transInfo.getNOTICEID());
			noticeInfo = noticeDao.findByID(noticeInfo);		//查找数据库中转让合同ID
			contractInfo  = contractBiz.findTransferContractInfoID(noticeInfo.getCracontractId());   //通过合同ID查找转让操作类型
			transInfo.setTRANSFERCONTRACTID(noticeInfo.getCracontractId());
			transInfo.setTRANSFERTYPE(contractInfo.getTranstypeId());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		if(checkOrCancel)
		{
            //复核交易记录
			accountBookOperation.repaycheckTransferContract(transInfo);
			
			TransferLoanAmountDetailinfo detailinfo=null;
			
			for(int i=0;i<transInfo.getRepaycoll().size();i++)
			{
				detailinfo=(TransferLoanAmountDetailinfo)transInfo.getRepaycoll().toArray()[i];
				detailinfo.setTransferamountid(transInfo.getID());
				TransAccountDetailInfo loanTadi = new TransAccountDetailInfo();
				
				loanTadi.setOfficeID(transInfo.getOFFICEID());
				loanTadi.setCurrencyID(transInfo.getCURRENCYID());
				loanTadi.setTransactionTypeID(transInfo.getTRANSACTIONTYPEID());
				loanTadi.setDtExecute(transInfo.getEXECUTE());
				loanTadi.setTransNo(transInfo.getSTRANSNO());
				loanTadi.setAbstractID(transInfo.getABSTRACTID());
				loanTadi.setAbstractStr(transInfo.getSCHECKABSTRACT());
				loanTadi.setDtInterestStart(transInfo.getINTERESTSTART());

				loanTadi.setAmount(detailinfo.getAmount());
				loanTadi.setTransAccountID(detailinfo.getLoanaccountid());
				loanTadi.setLoanNoteID(detailinfo.getNoticeformid());
				loanTadi.setTransSubAccountID(-1);//在后面得方法中会写子账户id
				loanTadi.setOppAccountID(-1);
				//为账户对账单信息查询 所加
				if(transInfo.getPAYBANKID() > 0)
				{
					CounterparBankDao counterparbankdao = new CounterparBankDao();
					try {
						CounterpartBankInfo bankinfo = (CounterpartBankInfo)counterparbankdao.findByID(transInfo.getPAYBANKID(),CounterpartBankInfo.class);
						loanTadi.setOppAccountNo(bankinfo.getCounterpartbankno());
						loanTadi.setOppAccountName(bankinfo.getCounterparname());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//账户金额查询区分金额类型增加
				loanTadi.setAmountType(SETTConstant.AmountType.AmountType_1);
				Timestamp backDate = info.getINTERESTSTART();
				if (backDate.before(info.getEXECUTE()))
				{
					loanTadi.setInterestBackFlag(1);
				}
				try
				{
				loanSubAccountID = accountOperation.repayLoan(loanTadi);
				}
				catch(Exception e)
				{
					if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				}
				
			}
		}
		else
		{
          //取消复核交易记录
		  accountBookOperation.repaycancelCheckTransferContract(transInfo);
		  //账户余额回填,累计未复核金额回填，删除交易记录
		  TransferLoanAmountDetailinfo detailinfo=null;
			
			for(int i=0;i<transInfo.getRepaycoll().size();i++)
			{
				detailinfo=(TransferLoanAmountDetailinfo)transInfo.getRepaycoll().toArray()[i];
				detailinfo.setTransferamountid(transInfo.getID());
				TransAccountDetailInfo loanTadi = new TransAccountDetailInfo();
				
				loanTadi.setOfficeID(transInfo.getOFFICEID());
				loanTadi.setCurrencyID(transInfo.getCURRENCYID());
				loanTadi.setTransactionTypeID(transInfo.getTRANSACTIONTYPEID());
				loanTadi.setDtExecute(transInfo.getEXECUTE());
				loanTadi.setTransNo(transInfo.getSTRANSNO());
				loanTadi.setAbstractID(transInfo.getABSTRACTID());
				loanTadi.setAbstractStr(transInfo.getSCHECKABSTRACT());
				loanTadi.setDtInterestStart(transInfo.getINTERESTSTART());

				loanTadi.setAmount(detailinfo.getAmount());
				loanTadi.setTransAccountID(detailinfo.getLoanaccountid());
				loanTadi.setLoanNoteID(detailinfo.getNoticeformid());
				loanTadi.setTransSubAccountID(-1);//在后面得方法中会写子账户id
				loanTadi.setOppAccountID(-1);
				//为账户对账单信息查询 所加
				if(transInfo.getPAYBANKID() > 0)
				{
					CounterparBankDao counterparbankdao = new CounterparBankDao();
					try {
						CounterpartBankInfo bankinfo = (CounterpartBankInfo)counterparbankdao.findByID(transInfo.getPAYBANKID(),CounterpartBankInfo.class);
						loanTadi.setOppAccountNo(bankinfo.getCounterpartbankno());
						loanTadi.setOppAccountName(bankinfo.getCounterparname());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//账户金额查询区分金额类型增加
				loanTadi.setAmountType(SETTConstant.AmountType.AmountType_1);
				Timestamp backDate = info.getINTERESTSTART();
				if (backDate.before(info.getEXECUTE()))
				{
					loanTadi.setInterestBackFlag(1);
				}
				try
				{
					 loanSubAccountID=accountOperation.cancelRepayLoan(loanTadi);
				}
				catch(Exception e)
				{
					if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				}
			}
		 
		  
		try
		{
			
		   dao.updateCheckUser(info.getID(), -1);
		   
		}
		catch(Exception e){
			
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
		return info.getID();
		}
	}
	/**
	 * 查询代收收款通知单和收款业务明细组装数据(为错误时返回原修改后的数据)
	 * @param info
	 * @param coll
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findNoticeAndAgentDetialForFalse(NoticeAndAgentDetailConditionInfo info,Collection coll)throws RemoteException, IRollbackException
    {
  
	       Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
	       Collection resultcoll = null;
		    try
	        {
		    	resultcoll = transferDepositDao.findNoticeAndAgentDetial(info);
			     if(resultcoll!=null && coll.size()>0 && coll!=null && coll.size()>0)
					{
						Iterator it = resultcoll.iterator();
						while(it.hasNext())
						{
							NoticeAndAgentDetialResultInfo detailInfo =(NoticeAndAgentDetialResultInfo) it.next();
							Iterator it1 = coll.iterator();
							while(it1.hasNext())
							{
								TransferLoanContractDetailInfo cdetailInfo = (TransferLoanContractDetailInfo)it1.next();
								if(detailInfo.getNoticeDetailID()==cdetailInfo.getCraNoticeDetailID())
								{
									detailInfo.setPayAccountID(cdetailInfo.getPayaccountid());
								}
							}
						}
					}
			     
		    }
		    catch (RemoteException e)
		    {
			     throw e;
		    }
		    catch (IRollbackException e)
		    {
			    throw e;
		    }
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return resultcoll;
    }
	/**
	 * 代收成员单位暂存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempClientSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		try
		{
			lReturn = tempSave(info);
			if(lReturn>0)
			{
				Collection coll = info.getColl();
				if(coll!=null && coll.size()>0)
				{
					Iterator it = coll.iterator();
					Sett_TransferLoanContractDetailDepositDAO detailDao = new Sett_TransferLoanContractDetailDepositDAO();
					while(it.hasNext())
					{
						TransferLoanContractDetailInfo detailInfo = (TransferLoanContractDetailInfo)it.next();
						detailInfo.setTransferLoanAmountID(lReturn);
						detailDao.add(detailInfo);
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		return lReturn;
	}
	
	/**
	 * 代收成员单保存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long preClientSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		try
		{
			String sTranNo = info.getSTRANSNO();
			lReturn = preSave(info);
			if(lReturn>0)
			{
				AccountOperation accountOperation = new AccountOperation();
				Collection coll = info.getColl(); 
				if(coll!=null && coll.size()>0)
				{
					
					Sett_TransferLoanContractDetailDepositDAO detailDao = new Sett_TransferLoanContractDetailDepositDAO();
					//删除原来数据付款方累计付款金额,暂存数据是执行此方法的
					if(info.getID()>0 && !sTranNo.equals(""))
					{
						TransferLoanContractDetailInfo condition = new TransferLoanContractDetailInfo();
						condition.setTransferLoanAmountID(info.getID());
						Collection oldDetailColl = detailDao.findByCondition(condition);
						if(oldDetailColl!=null && oldDetailColl.size()>0)
						{
							Iterator OldIt = oldDetailColl.iterator();
							while(OldIt.hasNext())
							{
								TransferLoanContractDetailInfo OldDetailInfo = (TransferLoanContractDetailInfo)OldIt.next();
								accountOperation.subtractCurrentUncheckAmount(OldDetailInfo.getPayaccountid(), OldDetailInfo.getAmount()+OldDetailInfo.getInterest());
							}
						}
					}
					//删除明细对应记录
					detailDao.deleteTransferDetailByID(lReturn);
					Iterator it = coll.iterator();
					while(it.hasNext())
					{
						TransferLoanContractDetailInfo detailInfo = (TransferLoanContractDetailInfo)it.next();
						//增加付款方累计未复核金额
						accountOperation.addCurrentUncheckAmount(detailInfo.getPayaccountid(),-1,detailInfo.getAmount()+detailInfo.getInterest());
						detailInfo.setTransferLoanAmountID(lReturn);
						detailDao.add(detailInfo);
					}
				}
			}
			lReturn = info.getID() ;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		return lReturn;
	}
	/**
	 * 代收成员单删除
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long clientDelete(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		try
		{
			delete(info);
			if(info.getID()>0)
			{
				Sett_TransferLoanContractDetailDepositDAO detailDao = new Sett_TransferLoanContractDetailDepositDAO();
					//删除数据付款方累计付款金额
				if(!info.getSTRANSNO().equals(""))
				{
					AccountOperation accountOperation = new AccountOperation();
					TransferLoanContractDetailInfo condition = new TransferLoanContractDetailInfo();
					condition.setTransferLoanAmountID(info.getID());
					Collection oldDetailColl = detailDao.findByCondition(condition);
					if(oldDetailColl!=null && oldDetailColl.size()>0)
					{
						Iterator OldIt = oldDetailColl.iterator();
						while(OldIt.hasNext())
						{
							TransferLoanContractDetailInfo OldDetailInfo = (TransferLoanContractDetailInfo)OldIt.next();
							accountOperation.subtractCurrentUncheckAmount(OldDetailInfo.getPayaccountid(), OldDetailInfo.getAmount()+OldDetailInfo.getInterest());
						}
					}
				}
				detailDao.deleteTransferDetailByID(info.getID());
				
			}
			lReturn = info.getID() ;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		return lReturn;
	}
	/**
	 * 
	 * Method check
	 * @descriptin  信贷资产转让处理复核和取消复核
	 * @param  checkOrCancelCheck 复核还是取消复核
	 * @return long 
	 * @throws RemoteException，IException
	 */
	public long transferClientCheck(boolean checkOrCancel, TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransferLoanContractDepositDAO dao = new Sett_TransferLoanContractDepositDAO();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		TransferLoanContractInfo transInfo = new TransferLoanContractInfo();
		if(checkOrCancel)
		{
            //复核
			TransferLoanContractInfo newInfo = this.findByID(info);
			String errMsg = UtilOperation.checkStatus(info.getSTATUSID(), newInfo.getSTATUSID(), SETTConstant.Actions.CHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}	
			info.setSTATUSID(SETTConstant.TransactionStatus.CHECK);
		}
		else
		{
            //取消复核
			TransferLoanContractInfo newInfo = this.findByID(info);
			String errMsg = UtilOperation.checkStatus(info.getSTATUSID(), newInfo.getSTATUSID(), SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			info.setSTATUSID(SETTConstant.TransactionStatus.SAVE);
			
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
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try
		{
			TransferNoticeInfo noticeInfo = new TransferNoticeInfo();
			TransferContractInfo contractInfo = new TransferContractInfo();
			TransferNoticeDao noticeDao = new TransferNoticeDao();
			TransferContractBiz contractBiz = new TransferContractBiz();
			noticeInfo.setId(transInfo.getNOTICEID());
			noticeInfo = noticeDao.findByID(noticeInfo);		//查找数据库中转让合同ID
			contractInfo  = contractBiz.findInfoById(noticeInfo.getCracontractId());   //通过合同ID查找转让操作类型
			transInfo.setTRANSFERCONTRACTID(noticeInfo.getCracontractId());
			transInfo.setTRANSFERTYPE(contractInfo.getTranstypeId());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		if(checkOrCancel)
		{
			//减活期付款金额
			try {
				AccountOperation accountOperation = new AccountOperation();
				TransferLoanContractDetailInfo condition = new TransferLoanContractDetailInfo();
				condition.setTransferLoanAmountID(info.getID());
				Sett_TransferLoanContractDetailDepositDAO detailDao = new Sett_TransferLoanContractDetailDepositDAO();
				Collection detailColl = detailDao.findByCondition(condition);
					if(detailColl!=null && detailColl.size()>0)
					{
						Iterator it = detailColl.iterator();
						while(it.hasNext())
						{
							TransferLoanContractDetailInfo detailInfo = (TransferLoanContractDetailInfo)it.next();
							TransAccountDetailInfo tadi =  new TransAccountDetailInfo();
							tadi.setAbstractStr(info.getSABSTRACT());
							tadi.setAmount(detailInfo.getAmount()+detailInfo.getInterest());
							tadi.setCurrencyID(info.getCURRENCYID());
							tadi.setDtExecute(info.getEXECUTE());
							tadi.setDtInterestStart(info.getINTERESTSTART());
							tadi.setOfficeID(info.getOFFICEID());
							tadi.setTransNo(info.getSTRANSNO());
							tadi.setTransactionTypeID(info.getTRANSACTIONTYPEID());
							tadi.setStatusID(info.getSTATUSID());
							tadi.setTransAccountID(detailInfo.getPayaccountid());
							tadi.setAbstractID(info.getABSTRACTID());
							//账户金额查询区分金额类型增加
							tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
							//
							accountOperation.withdrawCurrent(tadi);
						}
					}
				} catch (Exception e) {
					throw new IRollbackException(mySessionCtx, "复核减活期付款金额失败："+e.getMessage());
				}
            //复核交易记录
			accountBookOperation.repayClientcheckTransferContract(transInfo);
		
			
            //是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			//是否需要生成银行指令
			boolean bCreateInstruction = false;
			long bankID = info.getPAYBANKID();
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
					Log.print("*******************开始产生资产转让代收款指令，并保存**************************");
					try {
						log.debug("------开始资产转让代收款指令生成--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTRANSACTIONTYPEID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOFFICEID());
						instructionParam.setCurrencyID(info.getCURRENCYID());
						instructionParam.setCheckUserID(info.getCHECKUSERID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getINPUTUSERID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成资产转让代收款指令成功--------");
						
					} catch (Throwable e) {
						log.error("生成资产转让代收款指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成资产转让代收款指令失败："+e.getMessage());
					}
				}
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, "保存资产转让代收款指令出错！" + e.getMessage());
			}
		}
		else
		{
			//回滚累计未复核金额
			try {
				AccountOperation accountOperation = new AccountOperation();
				TransferLoanContractDetailInfo condition = new TransferLoanContractDetailInfo();
				condition.setTransferLoanAmountID(info.getID());
				Sett_TransferLoanContractDetailDepositDAO detailDao = new Sett_TransferLoanContractDetailDepositDAO();
				Collection detailColl = detailDao.findByCondition(condition);
					if(detailColl!=null && detailColl.size()>0)
					{
						Iterator it = detailColl.iterator();
						while(it.hasNext())
						{
							TransferLoanContractDetailInfo detailInfo = (TransferLoanContractDetailInfo)it.next();
							TransAccountDetailInfo tadi =  new TransAccountDetailInfo();
							tadi.setAbstractStr(info.getSABSTRACT());
							tadi.setAmount(detailInfo.getAmount()+detailInfo.getInterest());
							tadi.setCurrencyID(info.getCURRENCYID());
							tadi.setDtExecute(info.getEXECUTE());
							tadi.setDtInterestStart(info.getINTERESTSTART());
							tadi.setOfficeID(info.getOFFICEID());
							tadi.setTransNo(info.getSTRANSNO());
							tadi.setTransactionTypeID(info.getTRANSACTIONTYPEID());
							tadi.setStatusID(info.getSTATUSID());
							tadi.setTransAccountID(detailInfo.getPayaccountid());
							tadi.setAbstractID(info.getABSTRACTID());
							//账户金额查询区分金额类型增加
							tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
							//
							accountOperation.cancelWithdrawCurrent(tadi);
						}
					}
				} catch (Exception e) {
					throw new IRollbackException(mySessionCtx, "取消复核回滚累计未复核金额失败："+e.getMessage());
				}
          //取消复核交易记录
		  accountBookOperation.repayClientcancelCheckTransferContract(transInfo);
		  
		try
		{
			
		   dao.updateCheckUser(info.getID(), -1);
		   
		}
		catch(Exception e){
			
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
		return info.getID();
		}
	}
	
	public Collection findTransferloandetailByTransferId(TransferLoanContractInfo info)throws RemoteException, IRollbackException
    {
  
		TransferLoanAmountDetailDao detaildao = new TransferLoanAmountDetailDao();
		   try
		     {
			     return detaildao.findDetailinfoByTransferId(info);
		     }
		   catch (RemoteException e)
		     {
			     throw e;
		     }
		  catch (IRollbackException e)
		     {
			    throw e;
		     }
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
    }
	
	
	/**
	 *卖出买断，卖出回购非代收保存方法
	 *作者：赵敏
	 *时间：2009-9-8上午09:10:22
	 *@param info
	 *@return
	 *@throws RemoteException
	 *@throws IRollbackException
	 *long
	 */
	public long preSaveNoProxy(TransferLoanContractInfo info)
		throws RemoteException, IRollbackException
	{
		//long sessionID = -1;
		long depositId = -1;
		Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
		TransferNoticeDao noticeDAO = new TransferNoticeDao();
		TransferLoanContractInfo newInfo = null;
		TransferLoanAmountDetailDao detaildao=new TransferLoanAmountDetailDao();
		Collection coll=null;
		TransferLoanAmountDetailinfo detailinfo=null;
		AccountOperation accountOperation =new AccountOperation();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try{
            //对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//获取当前交易号
			String transNo = info.getSTRANSNO();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.equalsIgnoreCase(""))
			{  //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				transNo =
					utilOperation.getNewTransactionNo(
						info.getOFFICEID(),
						info.getCURRENCYID(),
						info.getTRANSACTIONTYPEID());
				info.setSTRANSNO(transNo);
				log.debug("------新交易号是:" + transNo + "--------");
			}
			else
			{
                //被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("------开始判断是否被非法修改过--------");
				//校验状态是否正确
				newInfo =transferDepositDao.findByID(info.getID());
				if (newInfo == null)
				{
					throw new IRollbackException(
						mySessionCtx,
						"无法找到交易ID对应的旧交易信息，交易失败");
				}
				String errMsg =
					UtilOperation.checkStatus(
						info.getSTATUSID(),
						newInfo.getSTATUSID(),
						SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, transferDepositDao))
				{
					log.debug("------被非法修改过--------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
			}
			if(info.getNOTICEID()>0)
			{
				noticeDAO.updateStatus(info.getNOTICEID(),CRAconstant.TransactionStatus.USED);
			}
			//查询明细信息以删除旧的未复核金额
			coll=detaildao.findDetailinfoByTransferId(info);
			//update是重新计算未复核金额
			if(newInfo!=null)
			{
				if(newInfo.getSTATUSID()!=SETTConstant.TransactionStatus.TEMPSAVE)
				{
					for(int i=0;i<coll.size();i++)
					{
						detailinfo=(TransferLoanAmountDetailinfo)coll.toArray()[i];
						accountOperation.addLoanUncheckAmount(detailinfo.getLoanaccountid(), detailinfo.getNoticeformid(), -detailinfo.getAmount());
					}
				}
			}
			//保存数据
			depositId  = partSave(info, transferDepositDao);
			//重新查询明细信息保存新的未复核金额
			coll=detaildao.findDetailinfoByTransferId(info);
			//保存累计为复核金额
			for(int i=0;i<coll.size();i++)
			{
				detailinfo=(TransferLoanAmountDetailinfo)coll.toArray()[i];
				accountOperation.addLoanUncheckAmount(detailinfo.getLoanaccountid(), detailinfo.getNoticeformid(), detailinfo.getAmount());
			}
			
			info.setID(depositId);
			transferDepositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				//if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				//	utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(
					mySessionCtx,
					"@TBD:Error Code--",
					e);
			}
		}
		return depositId;
	}
	
	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long deleteNoProxy(TransferLoanContractInfo info)
		throws RemoteException, IRollbackException
		{
		        Sett_TransferLoanContractDepositDAO transferDepositDao =  new Sett_TransferLoanContractDepositDAO();
		        TransferNoticeDao noticeDAO = new TransferNoticeDao();
		        TransferLoanAmountDetailDao detaildao=new TransferLoanAmountDetailDao();
		        TransferLoanAmountDetailinfo detailinfo= null;
		        AccountOperation accountOperation =new AccountOperation();
		        Collection coll=null;
		        TransferLoanContractInfo newInfo =null;
		        long returnid=-1;
                //工具操作接口类
			    UtilOperation utilOperation = new UtilOperation();
			 try{
                    //校验状态是否正确
					newInfo = transferDepositDao.findByID(info.getID());
					if (newInfo == null)
					{
						throw new IRollbackException(
							mySessionCtx,
							"无法找到交易ID对应的旧交易信息，交易失败");
					}
					String errMsg =
						UtilOperation.checkStatus(
							info.getSTATUSID(),
							newInfo.getSTATUSID(),
							SETTConstant.Actions.DELETE);
                    //被修改过
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					if (isTouch(info, transferDepositDao))
					{
						log.debug("------被非法修改过--------");
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					if(info.getNOTICEID()>0)
					{
						noticeDAO.updateStatus(info.getNOTICEID(),CRAconstant.TransactionStatus.APPROVALED);
					}
					returnid= transferDepositDao.delete(info.getID());
					if(newInfo.getSTATUSID()!=SETTConstant.TransactionStatus.TEMPSAVE)
					{
						coll=detaildao.findDetailinfoByTransferId(info);
						//删除时回复保存未复核金额
						if(coll!=null&&coll.size()>0)
						{
							for(int i=0;i<coll.size();i++)
							{
								detailinfo=(TransferLoanAmountDetailinfo)coll.toArray()[i];
								accountOperation.addLoanUncheckAmount(detailinfo.getLoanaccountid(), detailinfo.getNoticeformid(), -detailinfo.getAmount());
							}
						}
					}
					
					detaildao.deleteTransferDetail(info.getID());
					return returnid;
					
			 }
			 catch (Exception e)
				{
					throw new IRollbackException(mySessionCtx, e.getMessage(), e);
				}
		}
	public TransferLoanContractInfo findInfoByTransNo(TransferLoanContractInfo info)throws RemoteException, IRollbackException
    {
  
	          Sett_TransferLoanContractDepositDAO transferDepositDao = new Sett_TransferLoanContractDepositDAO();
		   try
		     {
			     return transferDepositDao.findInfoByTransNo(info);
		     }
		   catch (RemoteException e)
		     {
			     throw e;
		     }
		  catch (IRollbackException e)
		     {
			    throw e;
		     }
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
    }

}
