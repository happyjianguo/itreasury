/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transsecurities.bizlogic;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import javax.ejb.SessionBean;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderService;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceHome;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.dao.SEC_NoticeDAO;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.transsecurities.dao.Sett_TransSecuritiesDAO;
import com.iss.itreasury.settlement.transsecurities.dataentity.QueryInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
/**
 * @author xirenli
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransSecuritiesEJB implements SessionBean
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
	 * 交易的暂存方法：
	 * 
	 * 1、参数：
	 *    TransSecuritiesInfo,交易实体类
	 * 
	 * 2、返回值：
	 *    long ,交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）如果lID不是-1，调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过
	 * 。
	 *             调用方法Sett_TransSecuritiesDAO.update()保存交易记录信息。
	 *             
	 * 调用方法Sett_TransSecuritiesDAO.updateStatus()更改记录的状态为未保存。
	 *   
	 * （2）如果lID是-1，调用方法Sett_TransSecuritiesDAO.add()保存交易记录信息。
	 *            
	 * 调用方法Sett_TransSecuritiesDAO.updateStatus()更改记录的状态为未保存。
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long tempSave(TransSecuritiesInfo info) throws IRollbackException, RemoteException
	{
		long lReturn = -1;
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
		SEC_NoticeDAO secDao = new SEC_NoticeDAO();
		try
		{
			//新增或更新信息
			if (info.getID() <= 0) //新增
			{
				lReturn = dao.add(info);
				if (lReturn != -1)
				{
					//修改证券业务通知单状态
					if (info.getSecuritiesNoticeID() > 0)
					{
						secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.USED);
					}
					//更新状态为未保存
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
				}
			}
			else
			//修改暂存
			{
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransSecuritiesInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYTEMPSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				boolean flag = checkIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					lReturn = dao.update(info);
					if (lReturn != -1)
					{
						//修改证券业务通知单状态
						if (info.getSecuritiesNoticeID() > 0)
						{
							secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.USED);
						}
						//更新状态为未保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
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
		return lReturn;
	}
	/**
	 * 开立交易的保存方法：
	 * 
	 * 1、参数：
	 * TransSecuritiesInfo, 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *    （1）判断参数TransSecuritiesInfo,中的本金交易实体类的交易编号是否为空。
	 *        如果是空，说明是新增保存：
	 *            
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入FixdOpenInfo 。
	 *        如果非空，说明是修改保存:
	 *            调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过。
	 *            调用方法：this.openFindByID(),得到包含原来的交易实体类FixdOpenInfo。
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransFixedOpenInfo。
	 *   （2）调用方法：TransSecuritiesInfo.add() 保存信息。
	 *   （3）调用方法：AccountDetail.saveOpenFixedDeposit()。进行财务处理。
	 *   （4）调用方法：TransSecuritiesInfo.updateStatus() 
	 * 。修改交易的状态为保存。
	 * @roseuid 3F73AE99038F
	 *
	 *  @throws RemoteException,IRollbackException
	 */
	public long save(TransSecuritiesInfo info) throws IRollbackException, RemoteException
	{
		long lReturn = -1;
		long sessionID = -1;
		//数据访问对象
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
		SEC_NoticeDAO secDao = new SEC_NoticeDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		log.debug("---------开始save---------------");
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
				//证券业务通知单判断
				NoticeInfo aInfo = new NoticeInfo();
				aInfo = (NoticeInfo) secDao.findByID(info.getSecuritiesNoticeID(), aInfo.getClass());
				if (aInfo.getStatusId() != SECConstant.NoticeFormStatus.USED)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E053");
				}
				//判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransSecuritiesInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				//判断是否被非法修改过										
				log.debug("----------判断是否被非法修改过-------------");
				if (this.checkIsTouched(info.getID(), info.getModifyDate()))
				{
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//未非法修改
					log.debug("----------开始更新交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------结束更新交易信息-------------");
					//通过工具操作接口类获取新交易号
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					transNo = "S" + transNo;
					log.debug("----------新交易号是: " + transNo + " -------------");
					info.setTransNo(transNo);
					//修改
					lReturn = dao.update(info);
					//修改证券业务通知单状态
					secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.USED);
					//修改交易的状态为保存
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
				}
			}
			else
			//不是暂存
			{
				log.debug("----------当前状态不是暂存-------------");
				//获取当前交易号
				String transNo = info.getTransNo();
				//标志位：是否是新增交易号
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals(""))
				{
					//证券业务通知单判断
					NoticeInfo aInfo = new NoticeInfo();
					aInfo = (NoticeInfo) secDao.findByID(info.getSecuritiesNoticeID(), aInfo.getClass());
					if (aInfo.getStatusId() != SECConstant.NoticeFormStatus.CHECKED)
					{
						throw new IRollbackException(mySessionCtx, "Sett_E053");
					}
					log.debug("----------是新增交易-------------");
					//未被保存过，生成新交易号
					bNewTransNo = true;
					//通过工具操作接口类获取新交易号					
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					transNo = "S" + transNo;
					info.setTransNo(transNo);
					log.debug("----------新交易号是: " + transNo + " -------------");
					//保存					
					log.debug("----------开始新增交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------结束新增交易信息-------------");
					//修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					//修改证券业务通知单状态
					secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.USED);
					
					//保存时的财务处理
					log.debug("-------开始保存时的财务处理--------");
					accountBookOperation.saveSecuritiesDetails(info);
					log.debug("-------结束保存时的财务处理--------");
				}
				else
				{
					//证券业务通知单判断
					NoticeInfo aInfo = new NoticeInfo();
					aInfo = (NoticeInfo) secDao.findByID(info.getSecuritiesNoticeID(), aInfo.getClass());
					if (aInfo.getStatusId() != SECConstant.NoticeFormStatus.USED)
					{
						throw new IRollbackException(mySessionCtx, "Sett_E053");
					}
					//被保存过， 即保存再保存
					//判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransSecuritiesInfo newInfo = dao.findByID(info.getID());
					if (newInfo == null)
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					long lNewStatusID = newInfo.getStatusID();
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
					//被修改过
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					//判断是否被非法修改过							
					log.debug("----------是保存再保存-------------");
					if (this.checkIsTouched(info.getID(), info.getModifyDate()))
					{
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					else
					{
						//财务处理，删除数据库中的已有的存单
						log.debug("-------开始删除账簿中旧的信息--------");
						accountBookOperation.deleteSecuritiesDetails(newInfo);
						log.debug("-------结束删除账簿中旧的信息--------");
						
						//未非法修改
						log.debug("----------开始更新交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新交易信息-------------");
						//修改
						lReturn = dao.update(info);
						//修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
						//修改证券业务通知单状态
						secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.USED);
						
						//保存时的财务处理
						log.debug("-------开始保存时的财务处理--------");
						accountBookOperation.saveSecuritiesDetails(info);
						log.debug("-------结束保存时的财务处理--------");
					}
				}
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				//if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				//utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束save---------------");
		return lReturn;
	}
	/**
	 * 交易的删除方法：
	 * 
	 * 1、参数：
	 *    TransSecuritiesInfo 交易实体类
	 * 
	 * 2、返回值：
	 *    long ,被删除的交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   （1）调用方法this.checkIsTouched,判断要删除的记录是否被修改过。
	 *   （2）判断参数TransSecuritiesInfo 中的本金交易实体类的状态，
	 *        如果是暂存：
	 *            
	 * 调用方法：Sett_TransSecuritiesDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 *        如果是保存：
	 *            
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransSecuritiesInfo
	 *            
	 * 调用方法：Sett_TransSecuritiesDAO.updateStatus。修改交易的状态为删除（无效
	 * ）。
	 * @roseuid 3F73AE9E010B
	 */
	public long delete(TransSecuritiesInfo info) throws IRollbackException, RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		SEC_NoticeDAO secDao = new SEC_NoticeDAO();
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		log.debug("---------开始delete---------------");
		try
		{
			//对客户加锁			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransSecuritiesInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.DELETE);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.DELETE);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//检测是否被修改过
			boolean flag = checkIsTouched(info.getID(), info.getModifyDate());
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
					//修改证券业务通知单状态
					secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.CHECKED);
				}
				else
				{
					log.debug("---------开始:delete---------------");
					log.debug(UtilOperation.dataentityToString(info));
					TransSecuritiesInfo delInfo = dao.findByID(info.getID());
					//accountBookOperation.deleteOpenFixedDeposit(delInfo);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
					log.debug("---------结束:delete---------------");
					//修改证券业务通知单状态
					secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.CHECKED);
					//财务处理，删除数据库中的已有的存单
					log.debug("-------开始删除账簿中旧的信息--------");
					accountBookOperation.deleteSecuritiesDetails(newInfo);
					log.debug("-------结束删除账簿中旧的信息--------");
				}
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				//if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				//utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束delete---------------");
		return lReturn;
	}
	/**
	 * 交易的复核方法：
	 * 
	 * 1、参数：
	 *   TransSecuritiesInfo 交易实体类
	 * 2、返回值：
	 *    long ,被复核的交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要复核的单据已被修改，请检查！”。
	 *   （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 *   
	 * （3）调用方法：Sett_TransSecuritiesDAO.updateStatus。修改交易的状态为复核?
	 * @roseuid 3F73AEAF02F9
	 */
	public long check(TransSecuritiesInfo info) throws IRollbackException, RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
		SEC_NoticeDAO secDao = new SEC_NoticeDAO();
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
			TransSecuritiesInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//检测是否被修改过			
			boolean flag = checkIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）
				log.debug("-------复核：更新状态到已复核---------");
				lReturn = dao.update(info);
				if (lReturn != -1)
				{
					//修改证券业务通知单状态													  			
					secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.COMPLETED);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
				}
				log.debug("-------复核：开始结算复核财务处理---------");
				accountBookOperation.checkSecuritiesDetails(info);
				log.debug("-------复核：结束结算复核财务处理---------");
				
				//生成会计分录
				log.debug("-------复核：开始生成证券的会计分录---------");
				Connection con = Database.getConnection();
				DeliveryOrderServiceOperation deliveryOpration = new DeliveryOrderServiceOperation(con);
				NoticeInfo nInfo = new NoticeInfo();
				nInfo = (NoticeInfo) secDao.findByID(info.getSecuritiesNoticeID(), nInfo.getClass());
				DeliveryOrderInfo doInfo = new DeliveryOrderInfo();
				SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
				doInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(nInfo.getDeliveryOrderId(), doInfo.getClass());
				doInfo.setCompanyBankId(info.getBankID());
				doInfo.setDeliveryDate(info.getExecuteDate());
				doInfo.setNetIncome(info.getAmount());
				doInfo.setIsSettlementInvoke(SECConstant.TRUE);
				doInfo.setRemark(newInfo.getAbstract());
				log.info("-------------资金账户-------------"+doInfo.getAccountId());	
				deliveryOpration.checkDeliveryOrder(doInfo);
				//更改交割单状态到已使用-add by Huang Ye
				deliveryOrderDAO.updateStatus(doInfo.getId(), SECConstant.DeliveryOrderStatus.USED);
				log.debug("-------复核：结束生成证券的会计分录成功---------");
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
		catch (IException e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e);
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
				//if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				//	utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------复核：结束---------");
		return lReturn;
	}
	/**
	 * 交易的取消复核方法：
	 * 
	 * 1、参数：
	 *   TransSecuritiesInfo 交易实体类
	 * 2、返回值：
	 *    long ,被取消复核的交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 *   
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 *   
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处
	 * 理。
	 *   
	 * （3）调用方法：Sett_TransSecuritiesDAO.updateStatus。修改交易的状态为保存?
	 * @roseuid 3F73AEB30222
	 */
	public long cancelCheck(TransSecuritiesInfo info) throws IRollbackException, RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		Sett_TransSecuritiesDAO depositDao = new Sett_TransSecuritiesDAO();
		SEC_NoticeDAO secDao = new SEC_NoticeDAO();
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
			TransSecuritiesInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//检测是否被修改过
			boolean flag = checkIsTouched(info.getID(), info.getModifyDate());
			if (flag) //被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				lReturn = depositDao.update(info);
				if (lReturn != -1)
				{
					//修改证券业务通知单状态
					secDao.updateStatus(info.getSecuritiesNoticeID(), SECConstant.NoticeFormStatus.USED);
					lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
				}
				log.debug("-------复核：开始结算取消复核财务处理---------");
				accountBookOperation.cancelCheckSecuritiesDetails(info);
				log.debug("-------复核：结束结算取消复核财务处理---------");
				
				//取消复核				
				log.debug("-------取消复核 开始证券删除会计分录---------");
				Connection con = Database.getConnection();
				DeliveryOrderServiceOperation deliveryOpration = new DeliveryOrderServiceOperation(con);
				NoticeInfo nInfo = new NoticeInfo();
				nInfo = (NoticeInfo) secDao.findByID(info.getSecuritiesNoticeID(), nInfo.getClass());
				DeliveryOrderInfo doInfo = new DeliveryOrderInfo();
				SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
				doInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(nInfo.getDeliveryOrderId(), doInfo.getClass());
				doInfo.setCompanyBankId(info.getBankID());
				doInfo.setDeliveryDate(info.getExecuteDate());
				doInfo.setAmount(info.getAmount());
				deliveryOpration.cancelCheckDeliveryOrder(doInfo);
				//更改交割单状态到已保存-add by Huang Ye				
				deliveryOrderDAO.updateStatus(doInfo.getId(), SECConstant.DeliveryOrderStatus.SAVED);
				log.debug("-------取消复核成功 结束证券删除会计分录---------");
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
		catch (IException e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e);
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
				//if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				//utilOperation.releaseLock(info.getAccountID(), sessionID);
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
	 * 根据标识查询交易明细的方法：
	 * 
	 * 1、参数：
	 *    lID long , 交易的ID
	 * 
	 * 2、返回值：
	 *    TransSecuritiesInfo,交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransSecuritiesDAO.findByID() 
	 * 得到开立交易的明细类TransFixedOpenInfo。
	 * @roseuid 3F73AEB8007A
	 */
	public TransSecuritiesInfo findByID(long lID) throws IRollbackException, RemoteException
	{
		TransSecuritiesInfo info = new TransSecuritiesInfo();
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
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
	/*
	 * 判断报单号是否重复
	 */
	public long checkFormNo(TransSecuritiesInfo info) throws IRollbackException, RemoteException
	{
		long rtnFlag = -1;
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
		try
		{
			rtnFlag = dao.checkFromNo(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return rtnFlag;
	}
	/**
	 * 根据交易号查询交易明细的方法：
	 * 
	 * 1、参数：
	 *    strTransNo , 交易号
	 * 
	 * 2、返回值：
	 *    TransSecuritiesInfo,交易实体类
	 * 
	 * 3、逻辑说明：
	 *    （1）调用方法：Sett_TransSecuritiesDAO.findByID() 
	 * 得到开立交易的明细类TransFixedOpenInfo。
	 * @roseuid 3F73AEB8007A
	 */
	public TransSecuritiesInfo findByTransNo(String strTransNo) throws IRollbackException, RemoteException
	{
		TransSecuritiesInfo info = new TransSecuritiesInfo();
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
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
	 *    QueryInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值：
	 *    Collection ,包含Sett_TransSecuritiesDAO查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用Sett_TransSecuritiesDAO.findByStatus()方法。
	 * @roseuid 3F73AEBB0273
	 */
	public Collection findByStatus(QueryInfo info) throws IRollbackException, RemoteException
	{
		Collection coll = null;
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
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
	 *    TransSecuritiesInfo,交易查询条件实体类
	 * 
	 * 2、返回值：
	 *    Collection ,包含TransSecuritiesInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明：
	 *    调用方法：Sett_TransOpenFixedDepositDAO.match()
	 * @roseuid 3F73AEC000C1
	 */
	public Collection match(TransSecuritiesInfo info) throws IRollbackException, RemoteException
	{
		Collection coll = null;
		Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
		try
		{
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
	 *   （1）调用方法：Sett_TransOpenFixedDepositDAO.findByID,得到最新的交易。
	 *   
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * @roseuid 3F73AEC40379
	 */
	private boolean checkIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_TransSecuritiesDAO dao = new Sett_TransSecuritiesDAO();
			TransSecuritiesInfo info = dao.findByID(lID);
			//判断是否被非法修改过
			Timestamp lastTouchDate1 = info.getModifyDate();
			if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
			{
				return true;
			}
			else
			{
				return false;
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
}
