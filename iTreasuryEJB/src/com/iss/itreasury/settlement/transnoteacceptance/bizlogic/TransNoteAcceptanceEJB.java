/*
 * Created on 2006-4-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transnoteacceptance.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dao.Sett_TransAcceptanceNoteAcceptanceDao;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAdvancedReceviceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransNoteAcceptanceEJB implements SessionBean
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
	 * 商业票据承兑-到期承兑交易的暂存方法：
	 * 
	 * 1、参数：
	 *    TransAcceptanceNoteAcceptanceInfo,收款 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,商业票据承兑-到期承兑交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）如果lID不是-1，调用方法receiveCheckIsTouched,判断要暂存的记录是否被修改过
	 * 。
	 *             调用方法Sett_TransAcceptanceNoteAcceptanceDao.update()保存交易记录信息。
	 *             
	 * 调用方法Sett_TransAcceptanceNoteAcceptanceDao.updateStatus()更改记录的状态为未保存。
	 *   
	 * （2）如果lID是-1，调用方法Sett_TransAcceptanceNoteAcceptanceDao.add()保存交易记录信息。
	 *            
	 * 调用方法Sett_TransAcceptanceNoteAcceptanceDao.updateStatus()更改记录的状态为未保存。
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long acceptanceTempSave(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		
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
				TransAcceptanceNoteAcceptanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				boolean flag = acceptanceCheckIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					Log.print("----商票承兑——到期承兑暂存方法中更新贷款的状态----");
					Log.print("----开始校验放款通知单状态----");
					if (!dao.checkPayForm(info.getAcceptanceFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----放款通知单已经被使用----");
						throw new IRollbackException(mySessionCtx, "放款通知单已经被使用");
					}
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateLoanReceiveFormStatus(info.getAcceptanceFormID(), LOANConstant.LoanPayNoticeStatus.USED);
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
	
	
	//商业票据承兑-到期承兑交易的继续
	public TransAcceptanceNoteAcceptanceInfo acceptanceNext(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException
	{
		TransAcceptanceNoteAcceptanceInfo AcceptanceInfo = new TransAcceptanceNoteAcceptanceInfo();
		
		//数据访问对象
		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		try {
			log.debug("---------开始AcceptanceNext---------------");
			AcceptanceInfo=dao.next(info);
		}
		catch (RemoteException e)
		{
				throw e;
		}
		catch (IRollbackException e)
		{
				throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			
		}
		log.debug("---------结束acceptanceNext---------------");
		
		return AcceptanceInfo;
	}
	

	/**
	 * 商业票据承兑-到期承兑交易的保存方法：
	 * 
	 * 1、参数： TransAcceptanceNoteAcceptanceInfo, 交易实体类
	 * 
	 * 2、返回值： long ,商业票据承兑-到期承兑交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）判断参数TransAcceptanceNoteAcceptanceInfo,中的本金交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入TransMarginOpenInfo 。
	 * 如果非空，说明是修改保存: 调用方法this.receiveCheckIsTouched,判断要暂存的记录是否被修改过。
	 * 调用方法：this.AcceptanceFindByID(),得到包含原来的交易实体类TransAcceptanceNoteAcceptanceInfo。
	 * 
	 * 调用方法：AccountDetail.deleteMarginDeposit()。回滚原来的财务处理。注意参数是原来 --kkf
	 * 的实体TransAcceptanceNoteAcceptanceInfo。 （2）调用方法：Sett_TransReceiveFinanceDAO.add()
	 * 保存信息。 （3）调用方法：AccountDetail.saveOpenMarginDeposit()。进行财务处理。 --kkf
	 * （4）调用方法：Sett_TransReceiveFinanceDAO.updateStatus() 。修改交易的状态为保存。
	 * 
	 * @roseuid 3F73AE99038F
	 * 
	 * @throws RemoteException,IRollbackException
	 */
	public long acceptanceSave(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//数据访问对象
		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//帐户操作接口类
		
		log.debug("---------开始AcceptanceSave---------------");
		try
		{
			//获得当前状态
			long lStatus = info.getStatusID();
			//暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE)
			{
				log.debug("----------当前状态为暂存-------------");
				
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransAcceptanceNoteAcceptanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				//判断是否被非法修改过										
				log.debug("----------判断是否被非法修改过-------------");
				if (this.acceptanceCheckIsTouched(info.getID(), info.getModifyDate()))
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
					//财务处理，设置帐户余额
					log.debug("----------开始accountBookOperation::saveReceiveFinance-------------");
					accountBookOperation.saveAcceptanceNoteAcceptance(info);
					log.debug("----------结束accountBookOperation::saveReceiveFinance-------------");
					//通过工具操作接口类获取新交易号
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID());
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
				if (transNo == null || transNo.equals(""))
				{
					log.debug("----------是新增交易-------------");
					transNo =
						utilOperation.getNewTransactionNo(
								info.getOfficeID(),
								info.getCurrencyID(),
							info.getTransactionTypeID());
					
					
					
					info.setTransNo(transNo);
					log.debug("----------新交易号是: " + transNo + " -------------");
					//保存					
					log.debug("----------开始新增定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					
					// （3）调用方法：进行财务处理。
					log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveAcceptanceNoteAcceptance(info);
					log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					lReturn = dao.add(info);
					log.debug("----------结束新增定期交易信息-------------");
					Log.print("----收款保存方法中更新贷款的状态----");
					Log.print("----开始校验放款通知单状态----(由于贷款不通暂不做调试)");
					
					//检查放款通知单状态
					if (!dao.checkPayForm(info.getAcceptanceFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----放款通知单已经被使用----");
						throw new IRollbackException(mySessionCtx, "放款通知单已经被使用");
					}
					log.debug("====改变放款通知单状态为已使用====");
					dao.updateLoanReceiveFormStatus(info.getAcceptanceFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====改变完成====");
					
					//修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					
				}
				else
				{
					//被保存过， 即保存再保存
					//判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransAcceptanceNoteAcceptanceInfo newInfo = dao.findByID(info.getID());
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
					if (this.acceptanceCheckIsTouched(info.getID(), info.getModifyDate()))
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
						accountBookOperation.deleteAcceptanceNoteAcceptance(newInfo);
						log.debug("------结束删除账簿信息--------");
						
						//未非法修改
						log.debug("----------开始更新定期交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新定期交易信息-------------");
						//					财务处理，设置帐户余额
						log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveAcceptanceNoteAcceptance(info);
						log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
						//修改
						lReturn = dao.update(info);
						//					修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
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
		log.debug("---------结束AcceptanceSave---------------");
		return lReturn;
	}

	/**
	 * 融资租赁收款 收款交易的删除方法：
	 * 
	 * 1、参数：
	 *    TransMarginOpenInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,被删除的商业票据承兑-到期承兑交易的标识，如果失败，返回-1
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
	public long acceptanceDelete(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------开始AcceptanceDelete---------------");
		try
		{	
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransAcceptanceNoteAcceptanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			
			
			//检测是否被修改过
			boolean flag = this.acceptanceCheckIsTouched(info.getID(), info.getModifyDate());
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
					TransAcceptanceNoteAcceptanceInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteAcceptanceNoteAcceptance(delInfo);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
					log.debug("---------结束accountBookOperation::deleteOpenFixedDeposit---------------");
				}
					
					//update by dwj 20090907 如果是暂存就删除不了，因为暂存时放款通知单的状态是已审核，不是已使用所以会抛出异常。
					//更新贷款收款单的状态
					if (info.getStatusID()== SETTConstant.TransactionStatus.SAVE){		//如果是保存，更改放款通知单状态,如果是暂存不变
						if (!dao.checkPayForm(info.getAcceptanceFormID(),LOANConstant.LoanPayNoticeStatus.USED)){
						throw new IRollbackException(mySessionCtx,"放款通知单已经被修改");
						}
					dao.updateLoanReceiveFormStatus(info.getAcceptanceFormID(), LOANConstant.LoanPayNoticeStatus.CHECK);
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
		log.debug("---------结束receiveDelete---------------");
		return lReturn;
	}

	/**
	 * 商票承兑--到期承兑交易的复核方法：
	 * 
	 * 1、参数：
	 *   TransAcceptanceNoteAcceptanceInfo 交易实体类
	 * 2、返回值：
	 *    long ,被复核的商业票据承兑-到期承兑交易的标识，如果失败，返回-1
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
	public long acceptanceCheck(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//加锁时使用		

		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransAcceptanceNoteAcceptanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);

			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = acceptanceCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//财务操作，放开帐户余额等，
				accountBookOperation.checkAcceptanceNoteAcceptance(info);
				log.debug("-------收款复核：更新状态到已复核---------");
				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
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
	 * 商票承兑--到期承兑交易的取消复核方法：
	 * 
	 * 1、参数：
	 *   TransMarginOpenInfo 交易实体类
	 * 2、返回值：
	 *    long ,被取消复核的商业票据承兑-到期承兑交易的标识，如果失败，返回-1
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
	public long acceptanceCancelCheck(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		Sett_TransAcceptanceNoteAcceptanceDao depositDao = new Sett_TransAcceptanceNoteAcceptanceDao();
		try
		{	
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransAcceptanceNoteAcceptanceInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//检测是否被修改过
			boolean flag = acceptanceCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				//更新自己业务的状态
				lReturn = depositDao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
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
		return lReturn;
	}

	/**
	 * 根据标识查询融资租融收款 交易明细的方法：
	 * 
	 * 1、参数：
	 *    lID long , 收款交易的ID
	 * 
	 * 2、返回值：
	 *    TransAcceptanceNoteAcceptanceInfo,融资租融收款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransAcceptanceNoteAcceptanceDao.findByID() 
	 * 得到收款交易的明细类TransAcceptanceNoteAcceptanceInfo
	 * @roseuid 3F73AEB8007A
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByID(long lID) throws IRollbackException,RemoteException
	{
		TransAcceptanceNoteAcceptanceInfo info = new TransAcceptanceNoteAcceptanceInfo();
		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		
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
	 * 根据交易号查询商业票据承兑-到期承兑交易明细的方法：
	 * 
	 * 1、参数：
	 *    strTransNo , 收款交易号
	 * 
	 * 2、返回值：
	 *    TransMarginOpenInfo,融资租赁 收款交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransAcceptanceNoteAcceptanceDao.findByID() 
	 * 得到收款交易的明细类TransAcceptanceNoteAcceptanceInfo。
	 * @roseuid 3F73AEB8007A
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByTransNo(String strTransNo) throws IRollbackException,RemoteException
	{
		TransAcceptanceNoteAcceptanceInfo info = new TransAcceptanceNoteAcceptanceInfo();
		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		
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
	 *    Collection ,包含TransAcceptanceNoteAcceptanceInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用Sett_TransAcceptanceNoteAcceptanceDao.findByStatus()方法。
	 * @roseuid 3F73AEBB0273
	 */
	public Collection acceptanceFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		
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
	 *    TransAcceptanceNoteAcceptanceInfo,融资租赁 收款交易查询条件实体类
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransAcceptanceNoteAcceptanceInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用方法：Sett_TransAcceptanceNoteAcceptanceDao.match()
	 * @roseuid 3F73AEC000C1
	 */
	public Collection acceptanceMatch(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
		
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
	 *   （1）调用方法：Sett_TransAcceptanceNoteAcceptanceDao.findByID,得到最新的交易。
	 *   
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * @roseuid 3F73AEC40379
	 */
	private boolean acceptanceCheckIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException,RemoteException
	{
		
		try
		{
			Sett_TransAcceptanceNoteAcceptanceDao dao = new Sett_TransAcceptanceNoteAcceptanceDao();
			
			TransAcceptanceNoteAcceptanceInfo info = dao.findByID(lID);
			//		判断是否被非法修改
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

	
	//垫付收回交易的保存
	public long advancedReceviceSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException{
		return -1;
	}

	// 垫付收回交易的暂存
	public long advancedReceviceTempSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException{
		return -1;
	}
	

	// 垫付收回交易的匹配
	public Collection advancedReceviceMatch(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException{
		return null;
	}

	// 垫付收回交易的链接查找
	public Collection advancedReceviceFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException{
		return null;
	}

	// 垫付收回交易的根据交易号查找
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByTransNo(String strTransNo) throws IRollbackException, RemoteException{
		return null;
	}

	// 垫付收回交易的继续
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceNext(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException{
		return null;
	}
	

	// 垫付收回交易的根据ID查找
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByID(long lID) throws IRollbackException, RemoteException{
		return null;
	}

	// 垫付收回交易的复核
	public long advancedReceviceCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException{
		return -1;
	}

	// 垫付收回交易的取消复核
	public long advancedReceviceCancelCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException{
		return -1;
	}

	//  垫付收回交易的删除
	public long advancedReceviceDelete(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException{
		return -1;
	}
	
	public static void main(String [] args){
		
		TransNoteAcceptanceEJB a;
		TransAcceptanceNoteAcceptanceInfo info =new TransAcceptanceNoteAcceptanceInfo();
		try {
			
			a = new TransNoteAcceptanceEJB();
			a.acceptanceSave(info);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
