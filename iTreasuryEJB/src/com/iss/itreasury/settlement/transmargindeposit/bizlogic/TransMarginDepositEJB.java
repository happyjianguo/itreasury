/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transmargindeposit.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransMarginWithdrawDAO;
import com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransOpenMarginDepositDAO;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
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
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransMarginDepositEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private static  Object lockObj = new Object();  //静态
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
	 * 保证金存款开立交易的暂存方法：
	 * 
	 * 1、参数：
	 *    TransMarginOpenInfo,开立 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,保证金存款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）如果lID不是-1，调用方法this.
	IsTouched,判断要暂存的记录是否被修改过
	 * 。
	 *             调用方法Sett_TransOpenMarginDepositDAO.update()保存交易记录信息。
	 *             
	 * 调用方法Sett_TransOpenMarginDepositDAO.updateStatus()更改记录的状态为未保存。
	 *   
	 * （2）如果lID是-1，调用方法Sett_TransOpenMarginDepositDAO.add()保存交易记录信息。
	 *            
	 * 调用方法Sett_TransOpenMarginDepositDAO.updateStatus()更改记录的状态为未保存。
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long openTempSave(TransMarginOpenInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		try
		{
			//判断存单号是否重复
			if (!dao.checkDepositNo(info))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
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
				TransMarginOpenInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYTEMPSAVE);
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				boolean flag = openCheckIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					lReturn = dao.update(info);

					if (lReturn != -1)
					{
						//更新状态为未保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
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
		return lReturn;
		}
	}
	/**
	 * 保证金存款 开立交易保存前检测是否重复的方法：
	 * 
	 * 1、参数：
	 *    TransMarginOpenInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数TransMarginOpenInfo,中的交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            用方法：Sett_TransOpenMarginDeposit.checkIsDuplicate()判断是否重复。
	 * @roseuid 3F73AE9300E8
	 */	

	/**
	 * 保证金存款 开立交易的保存方法：
	 * 
	 * 1、参数：
	 * TransMarginOpenInfo, 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,保证金存款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数TransMarginOpenInfo,中的本金交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入TransMarginOpenInfo 。
	 *        如果非空，说明是修改保存:
	 *            调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过。
	 *            调用方法：this.openFindByID(),得到包含原来的交易实体类TransMarginOpenInfo。
	 *            
	 * 调用方法：AccountDetail.deleteMarginDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransMarginOpenInfo。
	 *   （2）调用方法：Sett_TransOpenMarginDepositDAO.add() 保存信息。
	 *   （3）调用方法：AccountDetail.saveOpenMarginDeposit()。进行财务处理。
	 *   （4）调用方法：Sett_TransOpenMarginDepositDAO.updateStatus() 
	 * 。修改交易的状态为保存。
	 * @roseuid 3F73AE99038F
	 *
		*  @throws RemoteException,IRollbackException
		*/
	public long openSave(TransMarginOpenInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		long sessionID = -1;
		//数据访问对象
		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		log.debug("---------开始openSave---------------");
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//获得当前状态
			long lStatus = info.getStatusID();
			//暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE)
			{
				log.debug("----------当前状态为暂存-------------");
				
          //	modify by xwhe  date:2007-08-03
				String DepositNo=info.getDepositNo();
				if(DepositNo==null || DepositNo.equals(""))
				{
					DepositNo=utilOperation.getOpenDepositNoBackGround(info.getAccountID());
					info.setDepositNo(DepositNo);
				}
				
				//				判断存单号是否重复
				if (!dao.checkDepositNo(info))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransMarginOpenInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				//判断是否被非法修改过										
				log.debug("----------判断是否被非法修改过-------------");
				if (this.openCheckIsTouched(info.getID(), info.getModifyDate()))
				{
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//未非法修改
					log.debug("----------开始更新保证金交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------结束更新保证金交易信息-------------");
					//财务处理，设置账户余额
					log.debug("----------开始accountBookOperation::saveOpenMarginDeposit-------------");
					accountBookOperation.saveOpenMarginDeposit(info);
					log.debug("----------结束accountBookOperation::saveOpenMarginDeposit-------------");
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
				//	modify by xwhe  date:2007-08-03
				String DepositNo=info.getDepositNo();
				if(DepositNo==null || DepositNo.equals(""))
				{
					DepositNo=utilOperation.getOpenDepositNoBackGround(info.getAccountID());
					info.setDepositNo(DepositNo);
				}
				
				//判断存单号是否重复
				if (!dao.checkDepositNo(info))
				{
					log.debug("----------存单号重复，交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
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
					log.debug("----------开始新增保证金交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------结束新增保证金交易信息-------------");
					// （3）调用方法：进行财务处理。
					log.debug("----------开始accountBookOperation::saveOpenMarginDeposit-------------");
					accountBookOperation.saveOpenMarginDeposit(info);
					log.debug("----------结束accountBookOperation::saveOpenMarginDeposit-------------");
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
					if (dao.checkIsUsed(info.getLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED))
					{
						Log.print("----收款通知单号已经被使用----");
						throw new IRollbackException(mySessionCtx, "收款通知单号已经被使用");
					}
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateAssureFormStatus(info.getLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====改变完成====");
					//修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					
				}
				else
				{
					//被保存过， 即保存再保存
					//判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransMarginOpenInfo newInfo = dao.findByID(info.getID());
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
					if (this.openCheckIsTouched(info.getID(), info.getModifyDate()))
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
						accountBookOperation.deleteOpenMarginDeposit(newInfo);
					log.debug("------结束删除账簿信息--------");
						
						//未非法修改
						log.debug("----------开始更新保证金交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新保证金交易信息-------------");
						//					财务处理，设置账户余额
						log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveOpenMarginDeposit(info);
						log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
						//修改
						lReturn = dao.update(info);
						//					修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
						log.debug("====改变放款通知单状态为已使用====");
						dao.updateAssureFormStatus(info.getLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED);
						log.debug("====改变完成====");
					}
				}
				
				/**
				 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
				 */
					if(info.getInutParameterInfo()!=null)
					{
					log.debug("------提交审批--------");
					//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
					InutParameterInfo tempInfo = info.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+lReturn);
					tempInfo.setTransID(transNo);//这里保存的是交易编号
					tempInfo.setDataEntity(info);
					System.out.println("@@@@@@@@@@@@@@@@@@@@------提交审批--------@@@@@@@@@@@@@@@@@@@@@");
					//提交审批
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					//更新状态到审批中
					dao.updateStatus(info.getID(),SETTConstant.TransactionStatus.APPROVALING);
					log.debug("------提交审批成功--------");
					System.out.println("@@@@@@@@@@@@@@@@@@@@------提交审批成功--------@@@@@@@@@@@@@@@@@@@@@");
					
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束openSave---------------");
		return lReturn;
		}
	}

	/**
	 * 保证金存款 开立交易的删除方法：
	 * 
	 * 1、参数：
	 *    TransMarginOpenInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,被删除的保证金存款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法this.opencheckIsTouched,判断要删除的记录是否被修改过。
	 *   （2）判断参数TransMarginOpenInfo 中的本金交易实体类的状态，
	 *        如果是暂存：
	 *            
	 * 调用方法：Sett_TransOpenMarginDepositDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 *        如果是保存：
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransMarginOpenInfo
	 *            
	 * 调用方法：Sett_TransOpenMarginDepositDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 * @roseuid 3F73AE9E010B
	 */
	public long openDelete(TransMarginOpenInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;

		//加锁时使用		
		long sessionID = -1;

		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------开始openDelete---------------");
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransMarginOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.DELETE);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info.getModifyDate());
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
					TransMarginOpenInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteOpenMarginDeposit(delInfo);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
					log.debug("---------结束accountBookOperation::deleteOpenFixedDeposit---------------");
					
					if(delInfo.getInstructionNo()!=null && delInfo.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(delInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
				}
//				更新贷款收款单的状态
				if (info.getStatusID()== SETTConstant.TransactionStatus.SAVE){		//如果是保存，更改放款通知单状态,如果是暂存不变
					dao.updateAssureFormStatus(info.getLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK);
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束openDelete---------------");
		return lReturn;
		}
	}

	/**
	 * 保证金存款存款交易的复核方法：
	 * 
	 * 1、参数：
	 *   TransMarginOpenInfo 交易实体类
	 * 2、返回值：
	 *    long ,被复核的保证金存款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要复核的单据已被修改，请检查！”。
	 *   （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 *   
	 * （3）调用方法：Sett_TransOpenMarginDepositDAO.updateStatus。修改交易的状态为复核?
	 * @roseuid 3F73AEAF02F9
	 */
	public long openCheck(TransMarginOpenInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;

		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();

		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransMarginOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）

				//财务操作，放开账户余额，
				accountBookOperation.checkOpenMarginDeposit(info);
				log.debug("-------开立复核：更新状态到已复核---------");
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
				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
				}
				/***********构造银行付款指令**********/
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
				
				if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
					Log.print("*******************开始产生保证金开立指令，并保存**************************");
					try {
						
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
						
						log.debug("------生成保证金开立指令结束--------");
						
					} catch (Throwable e) {
						log.error("生成保证金开立指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成保证金开立指令失败："+e.getMessage());
					}
				}
				else 
				{
					log.debug("没有银行接口或不需要生成银行指令！");
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------开立复核：结束---------");
		return lReturn;
		}
	}

	/**
	 * 保证金存款存款交易的取消复核方法：
	 * 
	 * 1、参数：
	 *   TransMarginOpenInfo 交易实体类
	 * 2、返回值：
	 *    long ,被取消复核的保证金存款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 *   
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处
	 * 理。
	 *   
	 * （3）调用方法：Sett_TransOpenMarginDepositDAO.updateStatus。修改交易的状态为保存?
	 * @roseuid 3F73AEB30222
	 */
	public long openCancelCheck(TransMarginOpenInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;

		Sett_TransOpenMarginDepositDAO depositDao = new Sett_TransOpenMarginDepositDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransMarginOpenInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				//取消复核
				accountBookOperation.cancelCheckOpenMarginDeposit(info);
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
				lReturn = depositDao.update(info);
				if(lReturn!=-1)
				{				
					//Added by zwsun , 2007/8/4, 判断是否挂了审批流
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * 根据标识查询保证金存款 开立交易明细的方法：
	 * 
	 * 1、参数：
	 *    lID long , 开立交易的ID
	 * 
	 * 2、返回值：
	 *    TransMarginOpenInfo,保证金存款开立交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransOpenMarginDepositDAO.findByID() 
	 * 得到开立交易的明细类TransMarginOpenInfo。
	 * @roseuid 3F73AEB8007A
	 */
	public TransMarginOpenInfo openFindByID(long lID) throws IRollbackException,RemoteException
	{
		TransMarginOpenInfo info = new TransMarginOpenInfo();

		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		try
		{
			info = dao.findByID(lID);

		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * 根据交易号查询保证金存款 开立交易明细的方法：
	 * 
	 * 1、参数：
	 *    strTransNo , 开立交易号
	 * 
	 * 2、返回值：
	 *    TransMarginOpenInfo,保证金存款 开立交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransOpenMarginDepositDAO.findByID() 
	 * 得到开立交易的明细类TransMarginOpenInfo。
	 * @roseuid 3F73AEB8007A
	 */
	public TransMarginOpenInfo openFindByTransNo(String strTransNo) throws IRollbackException,RemoteException
	{
		TransMarginOpenInfo info = new TransMarginOpenInfo();

		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		try
		{
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
	 *    Collection ,包含TransMarginOpenInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用Sett_TransOpenMarginDepositDAO.findByStatus()方法。
	 * @roseuid 3F73AEBB0273
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		try
		{
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
	 *    TransMarginOpenInfo,保证金存款开立交易查询条件实体类
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransMarginOpenInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用方法：Sett_TransOpenMarginDepositDAO.match()
	 * @roseuid 3F73AEC000C1
	 */
	public Collection openMatch(TransMarginOpenInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		try
		{
			//Added by zwsun , 2007/8/14, 匹配审批流
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));			
			coll = dao.match(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * 判断交易记录是否被修改过的暂存方法：
	 * 
	 * 1、参数：
	 *    lID, 交易ID。
	 *    long, 原来的TouchTime。
	 * 
	 * 2、返回值：
	 *    boolean, 如果被修改，返回true；否则，返回false。
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法：Sett_TransOpenMarginDepositDAO.findByID,得到最新的交易。
	 *   
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * @roseuid 3F73AEC40379
	 */
	private boolean openCheckIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException,RemoteException
	{
		try
		{

			Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
			TransMarginOpenInfo info = dao.findByID(lID);
			//		判断是否被非法修改过
			Timestamp lastTouchDate1 = info.getModifyDate();

			if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	

	/**
	* 保证金存款 （转活期/支取）交易的暂存方法：
	* 
	* 1、参数：
	*    FixedDrawInfo,开立 交易实体类
	* 
	* 2、返回值：
	*    long ,保证金存款 交易的标识，如果失败，返回-1
	* 
	* 3、逻辑说明：
	*   
	* （1）如果lID不是-1，调用方法this.DrawCheckIsTouched,判断要暂存的记录是否被修改过
	* 。
	*             调用方法Sett_TransMarginWithdrawDAO.update()保存交易记录信息。
	*             
	* 调用方法Sett_TransMarginWithdrawDAO.updateStatus()更改记录的状态为未保存。
	*   （2）如果lID是-1，调用方法Sett_TransMarginWithdrawDAO.add()保存交易记录信息。
	*            
	* 调用方法Sett_TransMarginWithdrawDAO.updateStatus()更改记录的状态为未保存。
	* @roseuid 3F73AECB006C
	*/
	public long drawTempSave(TransMarginWithdrawInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		try
		{
			//		新增或更新活期信息
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
				boolean flag = drawCheckIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					lReturn = dao.update(info);

					if (lReturn != -1)
					{
						//更新状态为未保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
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
		return lReturn;
		}
	}

	/**
	 * 保证金存款（转活期/支取）交易的保存方法：
	 * 
	 * 1、参数：
	 * FixedDrawInfo, 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,保证金存款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数FixedDrawInfo,中的本金交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入FixedDrawInfo 。
	 *        如果非空，说明是修改保存:
	 *            调用方法this.drawCheckIsTouched,判断要暂存的记录是否被修改过。
	 *            调用方法：this.drawFindByID(),得到包含原来的交易实体类FixedDrawInfo。
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体FixedDrawInfo。
	 *   （2）调用方法：Sett_TransMarginWithdrawDAO.add() 保存信息。
	 *   （3）调用方法：AccountDetail.saveOpenFixedDeposit()。进行财务处理。
	 *   （4）调用方法：Sett_TransMarginWithdrawDAO.updateStatus() 
	 * 。修改交易的状态为保存。
	 * @roseuid 3F73AECF0111
	 */
	public long drawSave(TransMarginWithdrawInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		long sessionID = -1;
		//数据访问对象
		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//获得当前状态
			long lStatus = info.getStatusID();
			//暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE)
			{
				log.debug("----------当前状态为暂存-------------");				
				
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransMarginWithdrawInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				//判断是否被非法修改过										
				log.debug("----------判断是否被非法修改过-------------");
				if (this.drawCheckIsTouched(info.getID(), info.getModifyDate()))
				{
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//未非法修改
					log.debug("----------开始更新保证金存款交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------结束更新保证金存款交易信息-------------");
					//财务处理，设置账户余额
					log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveWithdrawMarginDeposit(info);
					log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
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
					log.debug("----------开始新增保证金存款交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------结束新增保证金存款交易信息-------------");
					// （3）调用方法：进行财务处理。
					log.debug("----------开始accountBookOperation::saveWithdrawMarginDeposit-------------");
					accountBookOperation.saveWithdrawMarginDeposit(info);
					log.debug("----------结束accountBookOperation::saveWithdrawMarginDeposit-------------");
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
					if (!dao.checkIsUsed(info.getLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----保后处理通知单号已经被使用----");
						throw new IRollbackException(mySessionCtx, "保后处理通知单号已经被使用");
					}
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateAssureFormStatus(info.getLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====改变完成====");
					//修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);

				} 
				else
				{
					//被保存过， 即保存再保存
					//判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransMarginWithdrawInfo newInfo = dao.findByID(info.getID());
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
					if (this.drawCheckIsTouched(info.getID(), info.getModifyDate()))
					{
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					else
					{
						//财务处理，删除数据库中的已有的存单
						log.debug("-------删除账簿中旧的信息--------");
						log.debug(UtilOperation.dataentityToString(newInfo));						
						accountBookOperation.deleteWithdrawMarginDeposit(newInfo);
						//未非法修改
						log.debug("----------开始更新保证金存款交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新保证金存款交易信息-------------");
						//					财务处理，设置账户余额
						log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveWithdrawMarginDeposit(info);
						log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
						//修改
						lReturn = dao.update(info);
						//					修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					}
				}
				   /**
				   * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
				   */
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}

	}

	/**
	 * 保证金存款支取/转活期交易的删除方法：
	 * 
	 * 1、参数：
	 *    FixedDrawInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,被删除的保证金存款 交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法this.drawCheckIsTouched,判断要删除的记录是否被修改过。
	 *   （2）判断参数FixedDrawInfo 中的本金交易实体类的状态，
	 *        如果是暂存：
	 *            
	 * 调用方法：Sett_TransMarginWithdrawDAO.updateStatus。修改交易的状态为删除（无效）?
	 * 
	 *        如果是保存：
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransMarginOpenInfo
	 *            
	 * 调用方法：Sett_TransMarginWithdrawDAO.updateStatus。修改交易的状态为删除（无效）?
	 * @roseuid 3F73AED3008A
	 */
	public long drawDelete(TransMarginWithdrawInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;

		//加锁时使用		
		long sessionID = -1;

		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransMarginWithdrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();			
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = drawCheckIsTouched(info.getID(), info.getModifyDate());
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
					TransMarginWithdrawInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteWithdrawMarginDeposit(delInfo);
					//删除网银指令
					if(delInfo.getInstructionNo()!=null && delInfo.getInstructionNo().length()>0)
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(delInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
				}
//				更新贷款收款单的状态
				if (info.getStatusID()== SETTConstant.TransactionStatus.SAVE){		//如果是保存，更改放款通知单状态,如果是暂存不变
					dao.updateAssureFormStatus(info.getLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK);
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * 保证金存款 存款交易的取消复核方法：
	 * 
	 * 1、参数：
	 *   FixedDrawInfo 交易实体类
	 * 2、返回值：
	 *    long ,被取消复核的保证金存款交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.drawcheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 *   
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处
	 * 理。
	 *   （3）调用方法：Sett_TransMarginWithdrawDAO.updateStatus。修改交易的状态为保存。
	 * @roseuid 3F73AED60143
	 */
	public long drawCancelCheck(TransMarginWithdrawInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;

		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransMarginWithdrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = drawCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				//取消复核
				accountBookOperation.cancelCheckWithdrawMarginDeposit(info);
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
				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * 保证金存款存款交易的复核方法：
	 * 
	 * 1、参数：
	 *   FixedDrawInfo 交易实体类
	 * 2、返回值：
	 *    long ,被复核的保证金存款 本金交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.drawcheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要复核的单据已被修改，请检查！”。
	 *   （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 *   （3）调用方法：Sett_TransMarginWithdrawDAO.updateStatus。修改交易的状态为复核。
	 * @roseuid 3F73AEDA0102
	 */
	public long drawCheck(TransMarginWithdrawInfo info) throws IRollbackException,RemoteException
	{
		synchronized(lockObj){
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;

		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();

		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransMarginWithdrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = drawCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）

				//财务操作，放开账户余额，
				accountBookOperation.checkWithdrawMarginDeposit(info);
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
				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
				}
				//是否需要生成银行指令
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);	
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
						Log.print("*******************开始产生保证金支取交易指令，并保存**************************");
						try {
							log.debug("------开始保证金支取交易付款指令生成--------");
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
							
							log.debug("------生成保证金支取交易付款指令成功--------");
							
						} catch (Throwable e) {
							log.error("生成保证金支取交易付款指令(本金)失败");
							e.printStackTrace();
							throw new IRollbackException(mySessionCtx, "生成保证金支取交易付款指令失败："+e.getMessage());
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * 根据标识查询保证金存款 支取/转活期交易明细的方法：
	 * 
	 * 1、参数：
	 *    lID long , 交易的ID
	 * 
	 * 2、返回值：
	 *    FixedDrawInfo,保证金存款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransFixedWithDrawtDAO.findByID() 
	 * 得到开立交易的明细类FixedDrawInfo。
	 * @roseuid 3F73AEDD03A5
	 */
	public TransMarginWithdrawInfo drawFindByID(long lID) throws IRollbackException,RemoteException
	{
		TransMarginWithdrawInfo info = new TransMarginWithdrawInfo();

		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		try
		{
			info = dao.findByID(lID);

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
	 *    Collection ,包含FixedDrawInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用Sett_TransMarginWithdrawDAO.findByStatus()方法。
	 * @roseuid 3F73AEE1021A
	 */
	public Collection drawFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();

		try
		{
			coll = dao.findByStatus(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}
	/**
	 * 根据交易号查询保证金支取交易明细的方法：
	 * 
	 * 1、参数：
	 *    strTransNo , 开立交易号
	 * 
	 * 2、返回值：
	 *    FixedDrawInfo,保证金存款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransFixedContinueDAO.findByID() 
	 * 得到开立交易的明细类FixedContinueInfo。
	 * @roseuid 3F73AEF900AA
	 */
	public TransMarginWithdrawInfo drawFindByTransNo(String strTransNo) throws IRollbackException,RemoteException
	{
		TransMarginWithdrawInfo info = new TransMarginWithdrawInfo();

		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		try
		{
			info = dao.findByTransNo(strTransNo);

		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * 复核匹配的方法：
	 * 
	 * 1、参数：
	 *    FixedDrawInfo,保证金存款交易查询条件实体类
	 * 
	 * 2、返回值：
	 *    Collection ,包含FixedDrawInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用方法：Sett_TransMarginWithdrawDAO.match()
	 * @roseuid 3F73AEE4034A
	 */
	public Collection drawMatch(TransMarginWithdrawInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		try
		{
			//Added by zwsun , 2007/8/14, 匹配审批流
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));				
			coll = dao.match(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * 判断交易记录是否被修改过的暂存方法：
	 * 
	 * 1、参数：
	 *    lID, 交易ID。
	 *    long, 原来的TouchTime。
	 * 
	 * 2、返回值：
	 *    boolean, 如果被修改，返回true；否则，返回false。
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法：Sett_TransMarginWithdrawDAO.findByID,得到最新的交易。
	 *   
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * @roseuid 3F73AEE702CC
	 */
	private boolean drawCheckIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException,RemoteException
	{
		try
		{

			Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
			TransMarginWithdrawInfo info = dao.findByID(lID);
			//		判断是否被非法修改过
			Timestamp lastTouchDate1 = info.getModifyDate();

			if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	/**
	 * 保证金存款（支取/转活期）交易的继续的方法：
	 * 
	 * 1、参数：
	 *    FixedDrawInfo 交易实体类
	 * 
	 * 2、返回值：
	 *   TransMarginWithdrawInfo 交易实体类
	 * 
	 * 
	 * 3、逻辑说明：
	 *  （1）调用方法：XXX.findByID(),得到保证金子账户的信息。(从子账户表中取值)
	 *  （2）将保证金子账户的信息回写到交易实体TransMarginWithdrawInfo ，并返回。
	 * @roseuid 3F73AEEA01B8
	 */
	public TransMarginWithdrawInfo drawNext(TransMarginWithdrawInfo info) throws IRollbackException,RemoteException
	 {
	  TransMarginWithdrawInfo infoReturn = new TransMarginWithdrawInfo();
	  //账户操作接口类
	  AccountOperation acctOperation = new AccountOperation();
	  log.debug("----------保证金转活期继续-------------");
		  
	  SubAccountAssemblerInfo fixedInfo=acctOperation.findSubAccountByID(info.getSubAccountID());
	  log.debug("----------保证金转活期继续-------------");  
	  //基本信息
	  //infoReturn.setExecuteDate(info.getExecuteDate());
	  infoReturn.setInterestStartDate(info.getInterestStartDate());
	  infoReturn.setSubAccountID(info.getSubAccountID());	  
	  infoReturn.setAccountID(fixedInfo.getSubAccountFixedInfo().getAccountID());
	  infoReturn.setAccountNo(NameRef.getAccountNoByID(infoReturn.getAccountID()));
	  infoReturn.setClientID(NameRef.getClientIDByAccountID(infoReturn.getAccountID()));
	  infoReturn.setClientName(NameRef.getClientNameByAccountID(infoReturn.getAccountID()));
	  infoReturn.setStartDate(fixedInfo.getSubAccountFixedInfo().getStartDate());	  
	  infoReturn.setEndDate(fixedInfo.getSubAccountFixedInfo().getEndDate());
	  infoReturn.setDepositTerm(fixedInfo.getSubAccountFixedInfo().getDepositTerm());
	  infoReturn.setNoticeDay(fixedInfo.getSubAccountFixedInfo().getNoticeDay());	  
	  //通知与保证金支取利率处理方式不同
	  if(info.getTransactionTypeID()==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
	  {
	   infoReturn.setRate(fixedInfo.getSubAccountFixedInfo().getRate());   
	  }
	  else if(info.getTransactionTypeID()==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
	  {
	   infoReturn.setRate(info.getRate());
	   infoReturn.setDepositBalance(fixedInfo.getSubAccountFixedInfo().getBalance()-info.getDrawAmount());
	  }
		  
	  infoReturn.setAmount(fixedInfo.getSubAccountFixedInfo().getOpenAmount());
		  
	  InterestOperation io = new InterestOperation();
	  InterestsInfo ioInfo = new InterestsInfo();
	  
	  //通知与保证金利息处理也不同
	  if(info.getTransactionTypeID()==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
	  {	 
		double interest = 0.0; 
		try
		 {		 	  	   		   
		  	interest = io.calculateNoticeDepositAccountInterest(info.getCurrencyID(),info.getRate(),SETTConstant.InterestRateTypeFlag.YEAR,info.getDrawAmount(),infoReturn.getStartDate(),infoReturn.getInterestStartDate());
		 }
		 catch (IException e)
		 {   
		  throw new IRollbackException(mySessionCtx,e.getErrorCode());
		  
		 }
		infoReturn.setDrawInterest(interest); 
	  }
	  else if(info.getTransactionTypeID()==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
	  {
		try
		  {
			double baseAmount = 0.0;
			if(info.getDrawAmount() == 0)
				baseAmount = info.getAmount();
			else
				baseAmount = info.getDrawAmount();
		   ioInfo = io.calculateFixedDepositAccountInterest(info.getSubAccountID(),baseAmount,info.getExecuteDate());
		  }
		  catch (IException e)
		  {   
		   throw new IRollbackException(mySessionCtx,e.getErrorCode());
				   
		  }
		infoReturn.setPreDrawInterest(ioInfo.getPreDrawInterest());
	  	infoReturn.setStrikePreDrawInterest(ioInfo.getStrikePreDrawInterest());
	  	infoReturn.setCurrentInterest(ioInfo.getCurrentInterest());  
	  	infoReturn.setPayableInterest(ioInfo.getInterestPayment());  
	  }	    
	  //infoReturn.setTotalInterest(ioInfo.getTotalInterest());  
	  log.debug("----------保证金转活期成功-------------");
	  return infoReturn;
	 }
	
	
	
	/**
	 * 审批方法（开立）。
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransMarginOpenInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId =info.getID();
		try
		{
			TransMarginOpenInfo depositInfo = new TransMarginOpenInfo();
			depositInfo=this.openFindByID(info.getID());
			inutParameterInfo.setDataEntity(depositInfo);
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
					TransMarginOpenInfo depositInfo1 = new TransMarginOpenInfo();
					depositInfo1=this.openFindByID(info.getID());
					//构造check参数
					//TransFixedOpenInfo depositInfo = new TransFixedOpenInfo();
					//depositInfo = this.openFindByID(info.getID());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setAbstract("机核");
					depositInfo1.setCheckUserID(returnInfo.getUserID());	//最后审批人为复核人				
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					//调用openCheck方法
					this.openCheck(depositInfo1);
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
		return depositId;
		}
	}	
	/**
	 * 审批流：取消审批方法。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransMarginOpenInfo loanInfo)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();	
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				this.openCancelCheck(loanInfo);
				//取消审批
				lReturn = dao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//取消审批
				lReturn = dao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
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
	 * 审批流：取消审批方法。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransMarginWithdrawInfo loanInfo)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				this.drawCancelCheck(loanInfo);
				//取消审批
				lReturn = dao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//取消审批
				lReturn = dao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
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
	 * 审批方法（支取）。
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransMarginWithdrawInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId =info.getID();
		try
		{
			TransMarginWithdrawInfo depositInfo = new TransMarginWithdrawInfo();
			depositInfo = this.drawFindByID(info.getID());
			inutParameterInfo.setDataEntity(depositInfo);
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
					TransMarginWithdrawInfo depositInfo1 = new TransMarginWithdrawInfo();
					depositInfo1 = this.drawFindByID(info.getID());
					//构造check参数
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setAbstract("机核");
					depositInfo1.setCheckUserID(returnInfo.getUserID());					
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					//调用openCheck方法
					this.drawCheck(depositInfo1);
				}	
			}
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(
						info.getID(),
						SETTConstant.TransactionStatus.SAVE);
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
		return depositId;
		}
	}

	
	
}
