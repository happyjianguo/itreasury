/*
 * Created on 2006-4-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfinance.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.SessionBean;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.leasehold.dao.LoanAssureChargeFormDao;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transfinance.dao.Sett_TransReturnFinanceDao;
import com.iss.itreasury.settlement.transfinance.dao.Sett_TransReceiveFinanceDao;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Env;
import org.json.*;
import com.iss.itreasury.util.DataFormat;
/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFinanceEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * @exception RemoteException 异常说明。
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception RemoteException 异常说明。
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception RemoteException 异常说明。
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
	 * @exception RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		mySessionCtx = ctx;
	}

	//******用户自加入代码*******//
	/**
	 * 融资租赁收款交易的暂存方法：
	 * 
	 * 1、参数：
	 *    TransReceiveFinanceInfo,收款 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,融资租赁收款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）如果lID不是-1，调用方法receiveCheckIsTouched,判断要暂存的记录是否被修改过
	 * 。
	 *             调用方法Sett_TransReceiveFinanceDAO.update()保存交易记录信息。
	 *             
	 * 调用方法Sett_TransReceiveFinanceDAO.updateStatus()更改记录的状态为未保存。
	 *   
	 * （2）如果lID是-1，调用方法Sett_TransReceiveFinanceDAO.add()保存交易记录信息。
	 *            
	 * 调用方法Sett_TransReceiveFinanceDAO.updateStatus()更改记录的状态为未保存。
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long receiveTempSave(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//		新增或更新信息
			if (info.getID() <= 0) //新增
			{
				lReturn = dao.add(info);
				if (lReturn != -1)
				{
					//更新状态为未保存
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
				}
			}
			else //修改暂存
				{
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				boolean flag = receiveCheckIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					for(int i=0;i<50;i++)
						System.out.println("55555555555555555555555555555555555");
					
					Log.print("----收款暂存方法中更新贷款的状态----");
					Log.print("----开始校验放款通知单状态----");
					if (!dao.checkPayForm(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----放款通知单已经被使用----");
						throw new IRollbackException(mySessionCtx, "放款通知单已经被使用");
					}
//					log.debug("====改变放款通知单状态为已使用====");
//					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
//					log.debug("====改变完成====");
					
					//暂存的时候，不用更改放款通知单的状态，全哨修改 2010-7-2
					lReturn = dao.update(info);

					if (lReturn != -1)
					{
						//更新状态为未保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	
	
	//付款交易的继续
	public TransReceiveFinanceInfo receiveNext(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException
	{
		TransReceiveFinanceInfo receiveFinanceInfo = new TransReceiveFinanceInfo();
		
		//数据访问对象
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		try {
			log.debug("---------开始receiveNext---------------");
			receiveFinanceInfo=dao.next(info);
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//				throw e;
//		}
//		catch (IRollbackException e)
//		{
//				throw e;
//		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			
		}
		log.debug("---------结束receiveSave---------------");
		
		return receiveFinanceInfo;
	}
	

	/**
	 * 融资租赁收款 收款交易的保存方法：
	 * 
	 * 1、参数： TransReceiveFinanceInfo, 交易实体类
	 * 
	 * 2、返回值： long ,融资租赁收款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）判断参数TransReceiveFinanceInfo,中的本金交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入TransMarginOpenInfo 。
	 * 如果非空，说明是修改保存: 调用方法this.receiveCheckIsTouched,判断要暂存的记录是否被修改过。
	 * 调用方法：this.receiveFindByID(),得到包含原来的交易实体类TransReceiveFinanceInfo。
	 * 
	 * 调用方法：AccountDetail.deleteMarginDeposit()。回滚原来的财务处理。注意参数是原来 --kkf
	 * 的实体TransReceiveFinanceInfo。 （2）调用方法：Sett_TransReceiveFinanceDAO.add()
	 * 保存信息。 （3）调用方法：AccountDetail.saveOpenMarginDeposit()。进行财务处理。 --kkf
	 * （4）调用方法：Sett_TransReceiveFinanceDAO.updateStatus() 。修改交易的状态为保存。
	 * 
	 * @roseuid 3F73AE99038F
	 * 
	 * @throws RemoteException,IRollbackException
	 */
	public long receiveSave(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		long sessionID = -1;
		
		//数据访问对象
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		
		log.debug("---------开始receiveSave---------------");
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//获得当前状态
			long lStatus = info.getStatusID();
			//暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE)
			{
				
				for(int i=0;i<50;i++)
					System.out.println("11111111111111111111111111111111");
				log.debug("----------当前状态为暂存-------------");
				
				/*
				//				判断存单号是否重复
				if (!dao.checkDepositNo(info))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				//判断是否被非法修改过										
				log.debug("----------判断是否被非法修改过-------------");
				if (this.receiveCheckIsTouched(info.getID(), info.getModifyDate()))
				{
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//未非法修改
					log.debug("----------开始更新融资租赁交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));	//只是在后台打出来而己 
					lReturn = dao.update(info);
					log.debug("----------结束更新融资租赁交易信息-------------");
					//财务处理，设置账户余额
					log.debug("----------开始accountBookOperation::saveReceiveFinance-------------");
					accountBookOperation.saveReceiveFinance(info);
					log.debug("----------结束accountBookOperation::saveReceiveFinance-------------");
					//通过工具操作接口类获取新交易号
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					log.debug("----------新交易号是: " + transNo + " -------------");
					info.setTransNo(transNo);
					//修改
					lReturn = dao.update(info);
					//修改交易的状态为保存
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====改变完成====");
				}
			}
			else //不是暂存
				{
				
				for(int i=0;i<50;i++)
					System.out.println("22222222222222222222222222222");
				log.debug("----------当前状态不是暂存-------------");
				
				/*
				//判断存单号是否重复
				if (!dao.checkDepositNo(info))
				{
					log.debug("----------存单号重复，交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//获取当前交易号
				String transNo = info.getTransNo();
				//标志位：是否是新增交易号
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals(""))
				{
					for(int i=0;i<50;i++)
						System.out.println("33333333333333333333333333333333333");
					
					log.debug("----------是新增交易-------------");
					//未被保存过，生成新交易号
					bNewTransNo = true;
					//通过工具操作接口类获取新交易号					
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------新交易号是: " + transNo + " -------------");
					//保存					
					log.debug("----------开始新增定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					
					// （3）调用方法：进行财务处理。
					log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveReceiveFinance(info);
					log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					lReturn = dao.add(info);
					log.debug("----------结束新增定期交易信息-------------");
					/*
					//回写网银指令
					if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
					*/
					Log.print("----收款保存方法中更新贷款的状态----");
					Log.print("----开始校验放款通知单状态----");
					if (!dao.checkPayForm(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----放款通知单已经被使用----");
						throw new IRollbackException(mySessionCtx, "放款通知单已经被使用");
					}
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====改变完成====");
					
					//修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					
				}
				else
				{
					for(int i=0;i<50;i++)
						System.out.println("444444444444444444444444444444444444");
					
					//被保存过， 即保存再保存
					//判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
					if (newInfo == null)
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					long lNewStatusID = newInfo.getStatusID();
					//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
					String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//被修改过
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					//判断是否被非法修改过							
					log.debug("----------是保存再保存-------------");				
					if (this.receiveCheckIsTouched(info.getID(), info.getModifyDate()))
					{
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					else
					{
						//财务处理，删除数据库中的已有的存单
						log.debug("-------删除账簿中旧的信息--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						//删除账簿信息
						log.debug("------开始删除账簿信息--------");					
						accountBookOperation.deleteReceiveFinance(newInfo);
						log.debug("------结束删除账簿信息--------");
						
						//未非法修改
						log.debug("----------开始更新定期交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新定期交易信息-------------");
						//					财务处理，设置账户余额
						log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveReceiveFinance(info);
						log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
						//修改
						lReturn = dao.update(info);
						//					修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
						log.debug("====改变放款通知单状态为已使用====");
						dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
						log.debug("====改变完成====");
					}
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束receiveSave---------------");
		return lReturn;
	}

	/**
	 * 融资租赁收款 收款交易的删除方法：
	 * 
	 * 1、参数：
	 *    TransMarginOpenInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,被删除的融资租赁收款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法this.opencheckIsTouched,判断要删除的记录是否被修改过。
	 *   （2）判断参数TransMarginOpenInfo 中的本金交易实体类的状态，
	 *        如果是暂存：
	 *            
	 * 调用方法：Sett_TransReceiveFinanceDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 *        如果是保存：
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransMarginOpenInfo
	 *            
	 * 调用方法：Sett_TransReceiveFinanceDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 * @roseuid 3F73AE9E010B
	 */
	public long receiveDelete(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//加锁时使用		
		long sessionID = -1;
		
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------开始receiveDelete---------------");
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			
			System.out.println("得到页面传进来的记录状态为info.getStatusID():"+info.getStatusID());
			System.out.println("刚刚从数据库里查出来的状态为lNewStatusID:"+lNewStatusID);
			
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			
			
			//检测是否被修改过
			boolean flag = this.receiveCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//判断是否暂存
				if (info.getTransNo() == null || info.getTransNo().equals(""))
				{
					for(int i=0;i<50;i++)
						System.out.println("66666666666666666666666666666666666");
					
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
				}
				else
				{
					for(int i=0;i<50;i++)
						System.out.println("77777777777777777777777777777777777777777");
					
					log.debug("---------开始accountBookOperation::deleteOpenFixedDeposit---------------");
					log.debug(UtilOperation.dataentityToString(info));
					TransReceiveFinanceInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteReceiveFinance(delInfo);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
					log.debug("---------结束accountBookOperation::deleteOpenFixedDeposit---------------");
					
					/*
					if(delInfo.getInstructionNo()!=null && delInfo.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(delInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
					*/
				//}
				
					//更新贷款收款单的状态
					//if (info.getStatusID()== SETTConstant.TransactionStatus.SAVE){	//如果是保存，更改放款通知单状态,如果是暂存不变
				
						if (!dao.checkPayForm(info.getReceiveFormID(),LOANConstant.LoanPayNoticeStatus.USED)){
						throw new IRollbackException(mySessionCtx,"放款通知单已经被修改");
						}
					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.CHECK);
					}  //删除暂存的话，不用判断状态。全哨修改，20100702
				
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束receiveDelete---------------");
		return lReturn;
	}

	/**
	 * 融资租赁收款存款交易的复核方法：
	 * 
	 * 1、参数：
	 *   TransMarginOpenInfo 交易实体类
	 * 2、返回值：
	 *    long ,被复核的融资租赁收款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要复核的单据已被修改，请检查！”。
	 *   （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 *   
	 * （3）调用方法：Sett_TransReceiveFinanceDAO.updateStatus。修改交易的状态为复核?
	 * @roseuid 3F73AEAF02F9
	 */
	public long receiveCheck(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;

		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);

			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = receiveCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）
				System.out.println("info.getPayBailAccountID():"+info.getPayBailAccountID());
				System.out.println("info.getPayBailBankID():"+info.getPayBailBankID());
				System.out.println("info.getPayPoundageAccountID():"+info.getPayPoundageAccountID());
				System.out.println("info.getPayBailAccountID():"+info.getPayPoundageBankID());
				
				
				//财务操作，放开账户余额等，
				accountBookOperation.checkReceiveFinance(info);
				log.debug("-------收款复核：更新状态到已复核---------");
				/*
				 	if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
				*/

				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
				}
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = true;
				/*long bankID = transInfo.getBankID();
				try {
					//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = this.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("判断传入的银行ID是否需要生成银行指令出错！");
					e1.printStackTrace();
				}
				*/
				if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
					Log.print("*******************开始产生成融资租赁收款指令，并保存**************************");
					try {
						
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(info.getPayBailBankID());
						instructionParam.setInputUserID(info.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成融资租赁收款指令结束--------");
						
					} catch (Throwable e) {
						log.error("生成融资租赁收款指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成融资租赁收款指令失败："+e.getMessage());
					}
				}
				else 
				{
					log.debug("没有银行接口或不需要生成银行指令！");
				}
				
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------收款复核：结束---------");
		return lReturn;
	}

	/**
	 * 融资租赁收款存款交易的取消复核方法：
	 * 
	 * 1、参数：
	 *   TransMarginOpenInfo 交易实体类
	 * 2、返回值：
	 *    long ,被取消复核的融资租赁收款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 *   
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处
	 * 理。
	 *   
	 * （3）调用方法：Sett_TransReceiveFinanceDAO.updateStatus。修改交易的状态为保存?
	 * @roseuid 3F73AEB30222
	 */
	public long receiveCancelCheck(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		System.out.println("从页面传过来的输入人的getInputUserID为"+info.getInputUserID());
		System.out.println("从页面传过来的复核人的getCheckUserID为"+info.getCheckUserID());
		System.out.println("从页面传过来的输入人的getStatusID为"+info.getStatusID());
		
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		
		Sett_TransReceiveFinanceDao depositDao = new Sett_TransReceiveFinanceDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransReceiveFinanceInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = receiveCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				//复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）
				System.out.println("info.getPayBailAccountID():"+info.getPayBailAccountID());
				System.out.println("info.getPayBailBankID():"+info.getPayBailBankID());
				System.out.println("info.getPayPoundageAccountID():"+info.getPayPoundageAccountID());
				System.out.println("info.getPayBailAccountID():"+info.getPayPoundageBankID());
				
				//取消复核
				accountBookOperation.cancelCheckReceiveFinance(info);
				/*
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
				*/
				
				//更新自己业务的状态
				lReturn = depositDao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
				}

			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		
		return lReturn;
	}

	/**
	 * 根据标识查询融资租融收款 交易明细的方法：
	 * 
	 * 1、参数：
	 *    lID long , 收款交易的ID
	 * 
	 * 2、返回值：
	 *    TransReceiveFinanceInfo,融资租融收款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransReceiveFinanceDao.findByID() 
	 * 得到收款交易的明细类TransReceiveFinanceInfo
	 * @roseuid 3F73AEB8007A
	 */
	public TransReceiveFinanceInfo receiveFindByID(long lID) throws IRollbackException,RemoteException
	{
		TransReceiveFinanceInfo info = new TransReceiveFinanceInfo();
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//
			info = dao.findByID(lID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * 根据交易号查询融资租赁收款交易明细的方法：
	 * 
	 * 1、参数：
	 *    strTransNo , 收款交易号
	 * 
	 * 2、返回值：
	 *    TransMarginOpenInfo,融资租赁 收款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransReceiveFinanceDao.findByID() 
	 * 得到收款交易的明细类TransReceiveFinanceInfo。
	 * @roseuid 3F73AEB8007A
	 */
	public TransReceiveFinanceInfo receiveFindByTransNo(String strTransNo) throws IRollbackException,RemoteException
	{
		TransReceiveFinanceInfo info = new TransReceiveFinanceInfo();
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//
			info = dao.findByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * 根据状态查询的方法：
	 * 
	 * 1、参数：
	 *    QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransReceiveFinanceInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用Sett_TransReceiveFinanceDao.findByStatus()方法。
	 * @roseuid 3F73AEBB0273
	 */
	public Collection receiveFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//
			coll = dao.findByStatus(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * 复核匹配的方法：
	 * 
	 * 1、参数：
	 *    TransReceiveFinanceInfo,融资租赁 收款交易查询条件实体类
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransReceiveFinanceInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用方法：Sett_TransReceiveFinanceDao.match()
	 * @roseuid 3F73AEC000C1
	 */
	public Collection receiveMatch(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//匹配
			coll = dao.match(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		return coll;
	}

	/**
	 * 判断交易记录是否被修改过的私有方法：
	 * 
	 * 1、参数：
	 *    lID, 交易ID。
	 *    long, 原来的TouchTime。
	 * 
	 * 2、返回值：
	 *    boolean, 如果被修改，返回true；否则，返回false。
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法：Sett_TransReceiveFinanceDao.findByID,得到最新的交易。
	 *   
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * @roseuid 3F73AEC40379
	 */
	private boolean receiveCheckIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException,RemoteException
	{
		
		try
		{
			Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
			TransReceiveFinanceInfo info = dao.findByID(lID);
			//		判断是否被非法修改过
			Timestamp lastTouchDate1 = info.getModifyDate();
			
			System.out.println("得到页面传进来的记录修改时间为tsTouchTime:"+tsTouchTime);
			System.out.println("得到页面传进来的记录修改时间为lastTouchDate1:"+lastTouchDate1);

			if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
				return true;
			else
				return false;
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	///////////////////////////////////////////////////////////////////
	/**
	 * 融资租赁还款交易的暂存方法：
	 * 
	 * 1、参数：
	 *    TransReturnFinanceInfo,还款 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,融资租赁还款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）如果lID不是-1，调用方法ReturnCheckIsTouched,判断要暂存的记录是否被修改过
	 * 。
	 *             调用方法Sett_TransReturnFinanceDAO.update()保存交易记录信息。
	 *             
	 * 调用方法Sett_TransReturnFinanceDAO.updateStatus()更改记录的状态为未保存。
	 *   
	 * （2）如果lID是-1，调用方法Sett_TransReturnFinanceDAO.add()保存交易记录信息。
	 *            
	 * 调用方法Sett_TransReturnFinanceDAO.updateStatus()更改记录的状态为未保存。
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long returnTempSave(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//		新增或更新信息
			if (info.getID() <= 0) //新增
			{
				lReturn = dao.add(info);
				if (lReturn != -1)
				{
					//更新状态为未保存
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
				}
			}
			else //修改暂存
				{
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				boolean flag = returnCheckIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					Log.print("----开始校验放款通知单状态----");
					if (!dao.checkPayForm(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----放款通知单已经被使用----");
						throw new IRollbackException(mySessionCtx, "放款通知单已经被使用");
					}
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateLoanReturnFormStatus(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====改变完成====");
					lReturn = dao.update(info);

					if (lReturn != -1)
					{
						//更新状态为未保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	
	
	//付款交易的继续
	public TransReturnFinanceInfo returnNext(TransReturnFinanceInfo info) throws IRollbackException, RemoteException
	{
		TransReturnFinanceInfo ReturnFinanceInfo = new TransReturnFinanceInfo();
		
		//数据访问对象
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		try {
			log.debug("---------开始ReturnNext---------------");
			ReturnFinanceInfo=dao.next(info);
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//				throw e;
//		}
//		catch (IRollbackException e)
//		{
//				throw e;
//		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			
		}
		log.debug("---------结束ReturnSave---------------");
		
		return ReturnFinanceInfo;
	}
	

	/**
	 * 融资租赁还款 还款交易的保存方法：
	 * 
	 * 1、参数： TransReturnFinanceInfo, 交易实体类
	 * 
	 * 2、返回值： long ,融资租赁还款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）判断参数TransReturnFinanceInfo,中的本金交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入TransMarginOpenInfo 。
	 * 如果非空，说明是修改保存: 调用方法this.ReturnCheckIsTouched,判断要暂存的记录是否被修改过。
	 * 调用方法：this.ReturnFindByID(),得到包含原来的交易实体类TransReturnFinanceInfo。
	 * 
	 * 调用方法：AccountDetail.deleteMarginDeposit()。回滚原来的财务处理。注意参数是原来 --kkf
	 * 的实体TransReturnFinanceInfo。 （2）调用方法：Sett_TransReturnFinanceDAO.add()
	 * 保存信息。 （3）调用方法：AccountDetail.saveOpenMarginDeposit()。进行财务处理。 --kkf
	 * （4）调用方法：Sett_TransReturnFinanceDAO.updateStatus() 。修改交易的状态为保存。
	 * 
	 * @roseuid 3F73AE99038F
	 * 
	 * @throws RemoteException,IRollbackException
	 */
	public long returnSave(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		long sessionID = -1;
		
		//数据访问对象
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		
		log.debug("---------开始ReturnSave---------------");
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//获得当前状态
			long lStatus = info.getStatusID();
			//暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE)
			{
				log.debug("----------当前状态为暂存-------------");
				
				/*
				//				判断存单号是否重复
				if (!dao.checkDepositNo(info))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				//判断是否被非法修改过										
				log.debug("----------判断是否被非法修改过-------------");
				if (this.returnCheckIsTouched(info.getID(), info.getModifyDate()))
				{
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//未非法修改
					log.debug("----------开始更新融资租赁交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));	//只是在后台打出来而己 
					lReturn = dao.update(info);
					log.debug("----------结束更新融资租赁交易信息-------------");
					//财务处理，设置账户余额
					log.debug("----------开始accountBookOperation::saveReturnFinance-------------");
					accountBookOperation.saveReturnFinance(info);
					log.debug("----------结束accountBookOperation::saveReturnFinance-------------");
					//通过工具操作接口类获取新交易号
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					log.debug("----------新交易号是: " + transNo + " -------------");
					info.setTransNo(transNo);
					//修改
					lReturn = dao.update(info);
					//修改交易的状态为保存
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
				}
			}
			else //不是暂存
				{
				log.debug("----------当前状态不是暂存-------------");
				
				/*
				//判断存单号是否重复
				if (!dao.checkDepositNo(info))
				{
					log.debug("----------存单号重复，交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//获取当前交易号
				String transNo = info.getTransNo();
				//标志位：是否是新增交易号
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals(""))
				{
					log.debug("----------是新增交易-------------");
					//未被保存过，生成新交易号
					bNewTransNo = true;
					//通过工具操作接口类获取新交易号					
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------新交易号是: " + transNo + " -------------");
					//保存					
					log.debug("----------开始新增定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					
					// （3）调用方法：进行财务处理。
					log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveReturnFinance(info);
					log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					//暂存的记录只更新， Modified by zwsun, 2007/7/24
					if(info.getID()>0){
						lReturn = dao.update(info);
					}else{
						lReturn = dao.add(info);
					}
					log.debug("----------结束新增定期交易信息-------------");
					/*
					//回写网银指令
					if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
					*/
					Log.print("----收款保存方法中更新贷款的状态----");
					Log.print("----开始校验放款通知单状态----");
					if (!dao.checkPayForm(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----放款通知单已经被使用----");
						throw new IRollbackException(mySessionCtx, "放款通知单已经被使用");
					}
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateLoanReturnFormStatus(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====改变完成====");
					//修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);					
					
				}
				else
				{
					//被保存过， 即保存再保存
					//判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
					if (newInfo == null)
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					long lNewStatusID = newInfo.getStatusID();
					//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
					String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//被修改过
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					//判断是否被非法修改过							
					log.debug("----------是保存再保存-------------");				
					if (this.returnCheckIsTouched(info.getID(), info.getModifyDate()))
					{
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					else
					{
						//财务处理，删除数据库中的已有的存单
						log.debug("-------删除账簿中旧的信息--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						//删除账簿信息
						log.debug("------开始删除账簿信息--------");					
						accountBookOperation.deleteReturnFinance(newInfo);
						log.debug("------结束删除账簿信息--------");
						
						//未非法修改
						log.debug("----------开始更新定期交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新定期交易信息-------------");
						//					财务处理，设置账户余额
						log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveReturnFinance(info);
						log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
						//修改
						lReturn = dao.update(info);
						//					修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					}
				}
				//Added by zwsun , 2007-06-20, 审批流保存
				if(info.getInutParameterInfo()!=null)
				{
					log.debug("------提交审批--------");
					//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
					InutParameterInfo tempInfo = info.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+lReturn);
					tempInfo.setTransID(transNo);//这里保存的是交易编号
					tempInfo.setDataEntity(info);
					
					//提交审批
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					//更新状态到审批中
					dao.updateStatus(info.getID(),SETTConstant.TransactionStatus.APPROVALING);
					log.debug("------提交审批成功--------");
				
				}					
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束ReturnSave---------------");
		return lReturn;
	}
	/**
	 * 审批流：审批方法(融资租赁还款)
	 * Added by zwsun, 2007-06-21
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransReturnFinanceInfo info)throws RemoteException, IRollbackException
	{
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getID();
		try
		{
			TransReturnFinanceInfo loanInfo = new TransReturnFinanceInfo();
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
					TransReturnFinanceInfo loanInfo1 = new TransReturnFinanceInfo();
					loanInfo1=dao.findByID(info.getID());
					//构造check参数
					//TransFixedOpenInfo depositInfo = new TransFixedOpenInfo();
					//depositInfo = this.openFindByID(info.getID());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setAbstract("机核");
					loanInfo1.setCheckUserID(returnInfo.getUserID());	//最后审批人为复核人				
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					returnCheck(loanInfo1);
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return loanId;
	}	
	/**
	 * 审批流：取消审批方法（融资租赁还款）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransReturnFinanceInfo loanInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_TransReturnFinanceDao loanDao = new Sett_TransReturnFinanceDao();		
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				this.returnCancelCheck(loanInfo);
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}	

	/**
	 * 融资租赁还款 还款交易的删除方法：
	 * 
	 * 1、参数：
	 *    TransMarginOpenInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,被删除的融资租赁还款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法this.opencheckIsTouched,判断要删除的记录是否被修改过。
	 *   （2）判断参数TransMarginOpenInfo 中的本金交易实体类的状态，
	 *        如果是暂存：
	 *            
	 * 调用方法：Sett_TransReturnFinanceDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 *        如果是保存：
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransMarginOpenInfo
	 *            
	 * 调用方法：Sett_TransReturnFinanceDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 * @roseuid 3F73AE9E010B
	 */
	public long returnDelete(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//加锁时使用		
		long sessionID = -1;
		
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------开始ReturnDelete---------------");
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.DELETE);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = this.returnCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//判断是否暂存
				if (info.getTransNo() == null || info.getTransNo().equals(""))
				{
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
				}
				else
				{
					log.debug("---------开始accountBookOperation::deleteOpenFixedDeposit---------------");
					log.debug(UtilOperation.dataentityToString(info));
					TransReturnFinanceInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteReturnFinance(delInfo);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
					log.debug("---------结束accountBookOperation::deleteOpenFixedDeposit---------------");
					
					/*
					if(delInfo.getInstructionNo()!=null && delInfo.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(delInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
					*/
					
					
						if (!dao.checkPayForm(info.getReturnFormID(),LOANConstant.LoanPayNoticeStatus.USED)){
						throw new IRollbackException(mySessionCtx,"放款通知单已经被修改");
						}
						dao.updateLoanReturnFormStatus(info.getReturnFormID(),  LOANConstant.LoanPayNoticeStatus.CHECK);
					
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束ReturnDelete---------------");
		return lReturn;
	}

	/**
	 * 融资租赁还款存款交易的复核方法：
	 * 
	 * 1、参数：
	 *   TransReturnFinanceInfo 交易实体类
	 * 2、返回值：
	 *    long ,被复核的融资租赁还款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要复核的单据已被修改，请检查！”。
	 *   （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 *   
	 * （3）调用方法：Sett_TransReturnFinanceDAO.updateStatus。修改交易的状态为复核?
	 * @roseuid 3F73AEAF02F9
	 */
	public long returnCheck(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);

			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = returnCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）
				
				//财务操作，放开账户余额，
				accountBookOperation.checkReturnFinance(info);
				log.debug("-------还款复核：更新状态到已复核---------");
				
			 	//如果是网银指令,则要更新指令信息
				/*
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------是网银指令----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
				**/
					
				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
				}
				//added by xiong fei 2010-07-25調用調整計劃的方法
				this.ajustPlan(info.getContractID(),Env.getSystemDate(info.getOfficeID(), info.getCurrencyID()));
				
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = true;
				/*long bankID = transInfo.getReturnCorpusBankID();
				try {
						//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = this.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("判断传入的银行ID是否需要生成银行指令出错！");
					e1.printStackTrace();
				}
				*/
				Log.print("**************************"+ bIsValid + bCreateInstruction);
				if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
					Log.print("*******************开始产生成融资租赁还款指令，并保存**************************");
					try {
						
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(info.getReturnCorpusBankID());
						instructionParam.setInputUserID(info.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成融资租赁还款指令结束--------");
						
					} catch (Throwable e) {
						log.error("生成融资租赁还款指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成融资租赁还款指令失败："+e.getMessage());
					}
				}
				else 
				{
					log.debug("没有银行接口或不需要生成银行指令！");
				}
				
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------还款复核：结束---------");
		return lReturn;
	}

	/**
	 * 融资租赁还款存款交易的取消复核方法：
	 * 
	 * 1、参数：
	 *   TransReturnFinanceInfo 交易实体类
	 * 2、返回值：
	 *    long ,被取消复核的融资租赁还款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 *   
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处
	 * 理。
	 *   
	 * （3）调用方法：Sett_TransReturnFinanceDAO.updateStatus。修改交易的状态为保存?
	 * @roseuid 3F73AEB30222
	 */
	public long returnCancelCheck(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		
		Sett_TransReturnFinanceDao depositDao = new Sett_TransReturnFinanceDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransReturnFinanceInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = returnCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				//取消复核
				accountBookOperation.cancelCheckReturnFinance(info);
				
				//如果是网银指令,则要更新指令信息
				/*
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
				*/
				lReturn = depositDao.update(info);
				if(lReturn!=-1)
				{	
					//Modified by zwsun ,8/6,取消复核判断是否关联审批流
					InutParameterInfo pInfo=new InutParameterInfo();
					pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					pInfo.setOfficeID(info.getOfficeID());
					pInfo.setCurrencyID(info.getCurrencyID());
					pInfo.setTransTypeID(info.getTransactionTypeID());
					pInfo.setActionID(-1);
					if(FSWorkflowManager.isNeedApproval(pInfo)){
						lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.APPROVALED);					
					}else{
						lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);					
					}					
				}
				this.deletePlan(info.getContractID());
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				/*
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		
		return lReturn;
	}

	/**
	 * 根据标识查询融资租融还款 交易明细的方法：
	 * 
	 * 1、参数：
	 *    lID long , 还款交易的ID
	 * 
	 * 2、返回值：
	 *    TransReturnFinanceInfo,融资租融还款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransReturnFinanceDao.findByID() 
	 * 得到还款交易的明细类TransReturnFinanceInfo
	 * @roseuid 3F73AEB8007A
	 */
	public TransReturnFinanceInfo returnFindByID(long lID) throws IRollbackException,RemoteException
	{
		TransReturnFinanceInfo info = new TransReturnFinanceInfo();
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//
			info = dao.findByID(lID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * 根据交易号查询融资租赁还款交易明细的方法：
	 * 
	 * 1、参数：
	 *    strTransNo , 还款交易号
	 * 
	 * 2、返回值：
	 *    TransMarginOpenInfo,融资租赁 还款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransReturnFinanceDao.findByID() 
	 * 得到还款交易的明细类TransReturnFinanceInfo。
	 * @roseuid 3F73AEB8007A
	 */
	public TransReturnFinanceInfo returnFindByTransNo(String strTransNo) throws IRollbackException,RemoteException
	{
		TransReturnFinanceInfo info = new TransReturnFinanceInfo();
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//
			info = dao.findByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * 根据状态查询的方法：
	 * 
	 * 1、参数：
	 *    QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransReturnFinanceInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用Sett_TransReturnFinanceDao.findByStatus()方法。
	 * @roseuid 3F73AEBB0273
	 */
	public Collection returnFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//
			coll = dao.findByStatus(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * 复核匹配的方法：
	 * 
	 * 1、参数：
	 *    TransReturnFinanceInfo,融资租赁 还款交易查询条件实体类
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransReturnFinanceInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用方法：Sett_TransReturnFinanceDao.match()
	 * @roseuid 3F73AEC000C1
	 */
	public Collection returnMatch(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//Added by zwsun , 2007/6/28, 匹配审批流
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));			
			//匹配
			coll = dao.match(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		return coll;
	}

	/**
	 * 判断交易记录是否被修改过的私有方法：
	 * 
	 * 1、参数：
	 *    lID, 交易ID。
	 *    long, 原来的TouchTime。
	 * 
	 * 2、返回值：
	 *    boolean, 如果被修改，返回true；否则，返回false。
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法：Sett_TransReturnFinanceDao.findByID,得到最新的交易。
	 *   
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * @roseuid 3F73AEC40379
	 */
	private boolean returnCheckIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException,RemoteException
	{
		
		try
		{
			Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
			TransReturnFinanceInfo info = dao.findByID(lID);
			//		判断是否被非法修改过
			Timestamp lastTouchDate1 = info.getModifyDate();

			if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
				return true;
			else
				return false;
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		
	}
	
	/**
	 * 批量还款----生成数据库相关信息记录
	 * @author afzhu
	 * @param trfi 
	 * @param balanceType 结算方式
	 * @throws Exception 
	 */
	public int quantityRepaymentBalance_createRecord(TransReturnFinanceNewInfo trfi,String balanceType) throws IRollbackException, RemoteException
	{
		System.out.println("========================批量还款结算---生成与交易相关的信息记录==========================");
		int isFail = 0;//失败标识; 0为成功,-1为失败
		String transNo="";//交易号
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		/*生成交易号*/
		String deleteParam = "";
		try{
			deleteParam = trfi.getAccountOperation().replaceAll(";","&");
			System.out.println("========================批量还款结算---生成与交易相关的信息记录JSONObject==========================begin："+deleteParam);
			JSONObject jo=new JSONObject(deleteParam);
			System.out.println("========================批量还款结算---生成与交易相关的信息记录JSONObject==========================end");
			String detailOp[] = jo.getString("detail").split("&");
			double marginMoney = 0;//保证金账户扣款总额
			double currentMoney = 0;//活期账户扣款总额
			/*if(detailOp.length>1)
			{
				for(int i=1;i<detailOp.length;i++)
				{
					marginMoney+=Double.parseDouble(detailOp[i].split("-")[1]);
				}
			}*/
			for(int i=0;i<detailOp.length;i++)
			{
				if(detailOp[i].split("-")[2].equals("2"))//为2是保证金账户扣款,1是活期账户扣款
				{
					marginMoney+=Double.parseDouble(detailOp[i].split("-")[1]);
				}
				if(detailOp[i].split("-")[2].equals("1"))//为1是活期账户扣款
				{
					currentMoney+=Double.parseDouble(detailOp[i].split("-")[1]);
				}
			}
			transNo =utilOperation.getNewTransactionNo(
					trfi.getNofficeid(),
					trfi.getNcurrencyid(),
					SETTConstant.TransactionType.RETURNFINANCE);
			trfi.setTransno(transNo);//设置交易号
			trfi.setTransactionTypeID(SETTConstant.TransactionType.RETURNFINANCE);//设置交易类型
			trfi.setMarginMoney(marginMoney);//保证金账户扣款总额
			trfi.setCurrentMoney(currentMoney);//活期账户扣款总额
			/***************************************************/
			System.out.println("========================批量还款结算---开始生成账户明细==========================");
			createAccountDetailInfo(trfi,balanceType);//生成账户明细
			System.out.println("========================批量还款结算---结束生成账户明细==========================");
			System.out.println("========================批量还款结算---开始生成会计分录==========================");
			createGenerateGLEntryParam(trfi,balanceType);//生成会计分录
			System.out.println("========================批量还款结算---结束生成会计分录==========================");
			System.out.println("========================批量还款结算---开始对账户扣钱==========================");
			accountDeduckMoney(trfi);//账户更新 
			System.out.println("========================批量还款结算---结束对账户扣钱==========================");
			System.out.println("========================批量还款结算---开始生成结算记录==========================");
			update_TransReturnFinance(trfi);//生成结算记录
			System.out.println("========================批量还款结算---结束生成结算记录==========================");
			System.out.println("========================批量还款结算---开始生成链接查询记录==========================");
			createHrefFind(trfi);//生成链接查询记录
			System.out.println("========================批量还款结算---结束生成链接查询记录==========================");
			/***************************************************/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("========================批量还款结算---生成与交易相关的信息记录出现异常==========================");
			isFail = -1;
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return isFail;
	}
	
	/**
	 *  批量还款----生成账户明细
	 * @author afzhu
	 * @param trfn
	 * @param balanceType
	 */
	public void createAccountDetailInfo(TransReturnFinanceNewInfo trfn,String balanceType) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		TransAccountDetailInfo debit_tadi = null;//借方账户明细实体
		List resultList = new ArrayList();
		String deleteParam = "";
		try
		{
		deleteParam = trfn.getAccountOperation().replaceAll(";","&");
		JSONObject jo=new JSONObject(deleteParam);
		String detailOp[] = jo.getString("detail").split("&");
		for(int i=0;i<jo.getInt("count");i++)
		{
			debit_tadi = new TransAccountDetailInfo();
			debit_tadi.setOfficeID(trfn.getNofficeid());//办事处ID
			debit_tadi.setCurrencyID(trfn.getNcurrencyid());//币种
			debit_tadi.setTransactionTypeID(trfn.getTransactionTypeID());//交易类型
			debit_tadi.setDtExecute(DataFormat.getDateTime(trfn.getSysDateS()));//执行日--开机日
			debit_tadi.setTransNo(trfn.getTransno());//交易号
			debit_tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);//状态
			if(LOANConstant.BalanceType.CURRENTACCOUNT == Integer.parseInt(balanceType))//如果结算方式是活期账户
			{
			debit_tadi.setTransAccountID(trfn.getCurrentAccountID());//交易账户ID,借方该值为活期账户ID
			debit_tadi.setTransSubAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//交易子账户ID,借方该值为活期子账户ID
			debit_tadi.setAmount(Double.parseDouble(detailOp[i].split("-")[1]));//交易发生额,也就是本期还款金额
			}
			else if(LOANConstant.BalanceType.FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT == Integer.parseInt(balanceType))//如果结算方式是先活期后保证金
			{
				//如果i=0的话,证明这次循环是第一次扣钱的账户,也就是活期,第二次以后才是保证金,因为只有保证金账户才可以扣多次
				if(i==0)
				{
					debit_tadi.setTransAccountID(trfn.getCurrentAccountID());//交易账户ID,借方该值为活期账户ID
					debit_tadi.setTransSubAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//交易子账户ID,借方该值为活期子账户ID
					debit_tadi.setAmount(Double.parseDouble(detailOp[i].split("-")[1]));//交易发生额,也就是本期还款金额
				}
				else
				{
					debit_tadi.setTransAccountID(trfn.getMarginAccountID());//交易账户ID,借方该值为保证金账户ID
					debit_tadi.setTransSubAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//交易子账户ID,借方该值为活期子账户ID
					debit_tadi.setAmount(Double.parseDouble(detailOp[i].split("-")[1]));//交易发生额,也就是本期还款金额
				}
			}
			debit_tadi.setTransDirection(SETTConstant.ControlDirection.DEBIT);//交易方向(借)
			//debit_tadi.setOppAccountID(trfn.getOppAccountID());//对方账户ID
			//debit_tadi.setOppAccountNo(trfn.getOppAccountNo());//对方账户号
			//debit_tadi.setOppSubAccountID(trfn.getOppSubAccountID());//对方子账户ID
			//debit_tadi.setOppAccountName(trfn.getOppClientName());//对方账户名称(也就是客户名称)
			//System.out.println("===========开始保存账户明细===========");
			dao.saveAccountDetailInfo(debit_tadi);//保存
			//System.out.println("===========结束保存账户明细===========");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
	}
	/**
	 * 批量还款----生成会计分录
	 * 会计分录也根据账户发生情况来生成,如果只有活期账户变量,那就生成两条,要是又有活期又有保证金,那就生成四条,分别对应借贷
	 * @author afzhu
	 * @param trfn
	 * @param balanceType
	 */
	public void createGenerateGLEntryParam(TransReturnFinanceNewInfo trfn,String balanceType) throws Exception
	{
		GenerateGLEntryParam param = null;//活期账户会计分录
		GenerateGLEntryParam param1 = null;//保证金账户会计分录
		String deleteParam="";
		try{
			deleteParam = trfn.getAccountOperation().replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
			int type =-1;//0代表只有活期扣款,1代表只有保证金扣款,2代表活期和保证金全扣款
			String detailOp[] = jo.getString("detail").split("&");
			if(detailOp.length==1)
			{
				if(detailOp[0].split("-")[2].equals("2"))
				{
					type = 1;//只有保证金扣款
				}
				if(detailOp[0].split("-")[2].equals("1"))
				{
					type = 0;//只有活期扣款
				}
			}
			else
			{
				type = 1;//默认为只有保证金扣款
				if(detailOp[0].split("-")[2].equals("2")){
					type = 1;//只有保证金扣款
				}else{
					//for(int i=0;i<detailOp.length;i++)
					//{
					//	if(detailOp[i].split("-")[2].equals("1"))
					//	{
							type = 2;//活期和保证金全扣款
					//	}
					//}
				}
			}
			if(type == 2)//活期账户和保证金账户都发生扣款的情况
			{	
//				for(int i=0;i<detailOp.length;i++)
//				{
//					param = new GenerateGLEntryParam();//会计分录
//					param.setOfficeID(trfn.getNofficeid());//办事处ID
//					param.setCurrencyID(trfn.getNcurrencyid());//币种
//					param.setTransactionTypeID(trfn.getTransactionTypeID());//交易类型
//					param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//执行日
//					param.setTransNo(trfn.getTransno());//交易号
//					param.setInputUserID(trfn.getUserID());//录入人ID
//					param.setCheckUserID(trfn.getUserID());//复核人
//					param.setPrincipalType(SETTConstant.CapitalType.IRRESPECTIVE);// //本金流向，可空1内部转账2银行
//					//param.setInterestType(1);//利息流向，可空1内部转账2银行
//					param.setEntryType(SETTConstant.EntryType.DIVIDE);//分录类型,可空0无关1合并2分拆3反冲
//					//param.setReceiveAccountID(trfn.getOppAccountID());//收款方账户ID 
//					if(i==0)
//					{
//						param.setPayAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//付款方账户ID
//						param.setPrincipalOrTransAmount(Double.parseDouble(detailOp[0].split("-")[1]));//交易发生额(本金)
//						param.setTotalPrincipalAndInterest(Double.parseDouble(detailOp[0].split("-")[1]));//本息合计
//					}
//					else
//					{
//						param.setPayFinanceAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//保证金付款账户
//						param.setPrincipalOrTransAmount(Double.parseDouble(detailOp[i].split("-")[1]));//交易发生额(本金)
//						param.setFinanceAllAmount(Double.parseDouble(detailOp[i].split("-")[1]));//本金利息合计
//					}
//					boolean res = glopOperation.generateGLEntry(param);//生成会计分录
//					if (!res)
//					{
//						throw new IRollbackException(mySessionCtx, "产生会计分录错误2");
//					}
//				}
				param = new GenerateGLEntryParam();//会计分录
				param.setOfficeID(trfn.getNofficeid());//办事处ID
				param.setCurrencyID(trfn.getNcurrencyid());//币种
				param.setTransactionTypeID(trfn.getTransactionTypeID());//交易类型
				param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//执行日
				param.setTransNo(trfn.getTransno());//交易号
				param.setInputUserID(trfn.getUserID());//录入人ID
				param.setCheckUserID(trfn.getUserID());//复核人
				param.setPrincipalType(SETTConstant.CapitalType.INTERNAL);// //本金流向，可空1内部转账2银行
				//param.setInterestType(1);//利息流向，可空1内部转账2银行
				param.setEntryType(SETTConstant.EntryType.DIVIDE);//分录类型,可空0无关1合并2分拆3反冲
				//param.setReceiveAccountID(trfn.getOppAccountID());//收款方账户ID 
				param.setPayAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//付款方账户ID
				param.setTotalPrincipalAndInterest(trfn.getCurrentPaymentAmount());//本息合计
				param.setCurrentPrincipalAndInterest(trfn.getCurrentMoney());//活期本金利息合计
				//param.setPrincipalOrTransAmount(trfn.getPrincipal());//交易发生额(本金)
				param.setTotalInterest(trfn.getInterest());//利息合计
				param.setPayFinanceAccountID(Integer.parseInt(detailOp[1].split("-")[0]));//保证金付款账户
				param.setFinanceAllAmount(trfn.getMarginMoney());//本金利息合计
				boolean res = glopOperation.generateGLEntry(param);//生成会计分录
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "产生会计分录错误2");
				}
			}
			else if(type == 0)//只有活期扣款
			{
				param = new GenerateGLEntryParam();//会计分录
				param.setOfficeID(trfn.getNofficeid());//办事处ID
				param.setCurrencyID(trfn.getNcurrencyid());//币种
				param.setTransactionTypeID(trfn.getTransactionTypeID());//交易类型
				param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//执行日
				param.setTransNo(trfn.getTransno());//交易号
				param.setInputUserID(trfn.getUserID());//录入人ID
				param.setCheckUserID(trfn.getUserID());//复核人
				param.setPrincipalType(SETTConstant.CapitalType.INTERNAL);// //本金流向，可空1内部转账2银行
				//param.setInterestType(1);//利息流向，可空1内部转账2银行
				param.setEntryType(SETTConstant.EntryType.DIVIDE);//分录类型,可空0无关1合并2分拆3反冲
				//param.setReceiveAccountID(trfn.getOppAccountID());//收款方账户ID 
				param.setPayAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//付款方账户ID
				//param.setPrincipalOrTransAmount(trfn.getPrincipal());//交易发生额(本金)
				param.setTotalPrincipalAndInterest(trfn.getCurrentMoney());//本息合计
				param.setCurrentPrincipalAndInterest(trfn.getCurrentMoney());//活期本金利息合计
				param.setTotalInterest(trfn.getInterest());//利息合计
				//param.setFinanceAllAmount(trfn.getMarginMoney());//本金利息合计
				boolean res = glopOperation.generateGLEntry(param);//生成会计分录
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "产生会计分录错误2");
				}
			}
			else//只有保证金账户扣款
			{
				param = new GenerateGLEntryParam();//会计分录
				param.setOfficeID(trfn.getNofficeid());//办事处ID
				param.setCurrencyID(trfn.getNcurrencyid());//币种
				param.setTransactionTypeID(trfn.getTransactionTypeID());//交易类型
				param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//执行日
				param.setTransNo(trfn.getTransno());//交易号
				param.setInputUserID(trfn.getUserID());//录入人ID
				param.setCheckUserID(trfn.getUserID());//复核人
				param.setPrincipalType(SETTConstant.CapitalType.INTERNAL);// //本金流向，可空1内部转账2银行
				//param.setInterestType(1);//利息流向，可空1内部转账2银行
				param.setEntryType(SETTConstant.EntryType.DIVIDE);//分录类型,可空0无关1合并2分拆3反冲
				//param.setReceiveAccountID(trfn.getOppAccountID());//收款方账户ID 
				//param.setPrincipalOrTransAmount(trfn.getPrincipal());//交易发生额(本金)
				param.setCurrentPrincipalAndInterest(trfn.getCurrentMoney());//本息合计
				param.setTotalPrincipalAndInterest(trfn.getCurrentPaymentAmount());//本息合计
				param.setPayFinanceAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//保证金付款账户
				param.setTotalInterest(trfn.getInterest());//利息合计
				param.setFinanceAllAmount(trfn.getMarginMoney());//本金利息合计
				boolean res = glopOperation.generateGLEntry(param);//生成会计分录
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "产生会计分录错误2");
				}
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
	}
	
	/**
	 * 批量还款----账户更新
	 * @author afzhu
	 * @param trfn
	 */
	public void accountDeduckMoney(TransReturnFinanceNewInfo trfn) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		String deleteParam="";
		try{
			deleteParam = trfn.getAccountOperation().replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			String detailOp[] = jo.getString("detail").split("&");
			//String sk = trfn.getOppSubAccountID() + "-" + trfn.getCurrentPaymentAmount();//收款
			//dao.accountDeduckMoney(sk,1);//收款更新
			for(int i=0;i<detailOp.length;i++)
			{
				dao.accountDeduckMoney(detailOp[i],0);//付款更新
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	
	/**
	 * 批量还款---生成结算记录
	 * @author afzhu
	 * @param trfn
	 * @throws Exception
	 */
	public void update_TransReturnFinance(TransReturnFinanceNewInfo trfn) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try{
			dao.saveTransReturnFinance(trfn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 批量还款---生成连接查询记录
	 * @author afzhu
	 * @param trfn
	 * @throws Exception
	 */
	public void createHrefFind(TransReturnFinanceNewInfo trfn) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		String deleteParam="";
		try{
			deleteParam = trfn.getAccountOperation().replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			//String sk = trfn.getOppSubAccountID() + "-" + trfn.getCurrentPaymentAmount();//收款
			String detailOp =jo.getString("detail");
			dao.createHrefFind(trfn, detailOp);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	/**
	 * 批量还款---链接查找---删除操作
	 * @author afzhu
	 * @param deleteParam
	 * @throws Exception
	 */
	public void hrefFindDelete(String deleteParam) throws IRollbackException, RemoteException
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try{
			deleteParam = deleteParam.replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			String detailOpp[] = jo.getString("detail").split("&");//操作明细
			String transNo[] = jo.getString("transNo").split("&");//交易号
			//更新子账户中的金额
			for(int i=0;i<detailOpp.length;i++) 
			{
				dao.hrefFindDelete(detailOpp[i],0);
			}
			//更新会计分录,账户明细,结算表中的记录
			for(int j=0;j<transNo.length;j++)
			{
				dao.hrefFindDelete(transNo[j],1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
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
		double tempMbailamount = 0.0;
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try
		{
			tempMbailamount = dao.getMbailamount(constractID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return tempMbailamount;
	}
	
	/**
	 * added by xiong fei 2010-07-16
	 * 提前还款复核成功后重新安排还款计划
	 * @param constractID
	 * @return long
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long ajustPlan(long contractID,Timestamp ts) throws IRollbackException, RemoteException
	{
		long returnRes = -1;
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try
		{
			returnRes = dao.ajustPlan(contractID,ts);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return returnRes;
	}
	
	/**
	 * added by xiong fei 2010-07-16
	 * 提前还款复核成功后重新安排还款计划
	 * @param constractID
	 * @return long
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long deletePlan(long contractID) throws IRollbackException, RemoteException
	{
		long returnRes = -1;
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try
		{
			returnRes = dao.deletePlan(contractID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return returnRes;
	}
}