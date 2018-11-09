/*
 * Created on 2004-8-2
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.bizlogic;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import javax.ejb.SessionBean;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.compatibilityaccountbook.bizlogic.CompatibilityAccountBookOperation;
import com.iss.itreasury.settlement.compatibilitysetting.dao.Sett_CompatibilityTypeSettingDao;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingInfo;
import com.iss.itreasury.settlement.transcompatibility.dao.Sett_TransCompatibilityDAO;
import com.iss.itreasury.settlement.transcompatibility.dao.Sett_TransCompatibilityDetailDAO;
import com.iss.itreasury.settlement.transcompatibility.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityDetailInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
/**
 * SessionBean
 * 
 * @author gqzhang
 */
public class TransCompatibilityEJB implements SessionBean
{
	final static private int ACTION_CHECK = 0;
	final static private int ACTION_CANCEL_CHECK = 1;
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 * 异常说明。
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * 异常说明。
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
	 * javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	/**
	 * Method findAllTransTypeSetting. 查询所有有效的兼容业务类型
	 * 
	 * @return Vector
	 */
	public Vector findAllTransTypeSetting(long lOfficeID, long lCurrencyID) throws RemoteException, IRollbackException, SettlementException
	{
		log.print("====TransCompatibilityEJB：查询所有有效的兼容业务类型===");
		Vector vctReturn = null;
		Sett_CompatibilityTypeSettingDao dao = new Sett_CompatibilityTypeSettingDao();
		vctReturn = dao.findAllTypeSetting(lOfficeID, lCurrencyID);
		log.print("====TransCompatibilityEJB：结束查询所有有效的兼容业务类型===");
		return vctReturn;
	}
	/**
	 * Method findTypeSettingDetailByID. 根据兼容业务设置查询设置detail
	 * 
	 * @param lSettingID
	 * @return CompatibilityTypeSettingInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public CompatibilityTypeSettingInfo findTypeSettingDetailByID(long lSettingID) throws RemoteException, IRollbackException, SettlementException
	{
		log.print("====TransCompatibilityEJB：进入查找兼容业务类型设置===");
		log.print("====TransCompatibilityEJB：lID:" + lSettingID);
		Sett_CompatibilityTypeSettingDao dao = new Sett_CompatibilityTypeSettingDao();
		CompatibilityTypeSettingInfo rtnInfo = null;
		rtnInfo = dao.findTypeSettingByID(lSettingID);
		log.print("====TransCompatibilityEJB：完成查找兼容业务类型设置===");
		return rtnInfo;
	}
	/**
	 * Method findCompatibilityInfoByID. 根据交易id查找兼容交易信息，包括明细
	 * 
	 * @param lTransID
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransCompatibilityInfo findCompatibilityInfoByID(long lTransID) throws RemoteException, IRollbackException, SettlementException
	{
		log.print("====TransCompatibilityEJB：进入根据交易id查找兼容交易信息===");
		log.print("====TransCompatibilityEJB：lTransID：" + lTransID);
		Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
		Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
		TransCompatibilityInfo returnInfo = null;
		returnInfo = findCompatibilityInfoByID(lTransID, dao, detailDao);
		log.print("====TransCompatibilityEJB：结束根据交易id查找兼容交易信息===");
		return returnInfo;
	}
	/**
	 * Method tempsave. 暂存兼容业务信息
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempsave(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
		Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
		long depositId = -1;
		log.debug("=========开始暂存兼容业务======");
		try
		{
			//检验记录是否被修改
			if (info.getId() > 0)
			{
				//修改暂存再进行暂存
				TransCompatibilityInfo newInfo = this.findCompatibilityInfoByID(info.getId());
				if (newInfo == null)
				{
					log.debug("=========无法找到交易ID对应的旧交易信息，交易失败======");
					throw new IRollbackException(mySessionCtx, "无法找到交易ID对应的旧交易信息，交易失败");
				}
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equalsIgnoreCase(""))
				{
					log.debug("=========交易被修改过======");
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (checkIsTouched(info, dao))
				{
					log.debug("=========被非法修改过=========");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
			}
			info.setTransNo("");
			depositId = partSave(info, dao, detailDao);
			info.setId(depositId);
			//更新状态到：暂存
			dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("=========结束暂存兼容业务======");
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"暂存兼容业务产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("暂存兼容业务产生错误", e);
//		}
		return depositId;
	}
	/**
	 * Method save. 保存业务信息
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	/**
	 * @param info
	 * @return @throws
	 * RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long save(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("=========开始保存兼容业务======");
		long sessionID = -1;
		CompatibilityAccountBookOperation accountBookOperation = new CompatibilityAccountBookOperation();
		UtilOperation utilOperation = new UtilOperation();
		TransCompatibilityDetailInfo detailInfo = null;
		Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
		Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
		long[] lPayAccountIDs = null;
		Vector vctTemp = null;
		long lTransId = -1;
		try
		{
			//对付款方客户加锁
			log.debug("===========开始对付款方客户进行加锁");
			lPayAccountIDs = findPayAccountIDByTransInfo(info); //查找业务中的付款方ID
			if (lPayAccountIDs != null && lPayAccountIDs.length > 0)
			{
				for (int i = 0; i < lPayAccountIDs.length; i++)
				{
					sessionID = utilOperation.waitUtilSuccessLock(lPayAccountIDs[i]);
				}
			}
			//获取当前记录交易号
			String transNo = info.getTransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.trim().length() <= 0)
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("==========开始获取新交易号===========");
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				info.setTransNo(transNo);
				log.debug("=========新交易号是:" + transNo + "=========");
			}
			else
			{
				log.debug("=========已经生成交易号：" + info.getTransNo());
				//被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("=========开始判断是否被非法修改过======");
				//校验状态是否正确
				log.debug("==========主交易信息的id:" + info.getId());
				TransCompatibilityInfo newInfo = this.findCompatibilityInfoByID(info.getId());
				if (newInfo == null)
				{
					log.debug("=========无法找到交易ID对应的旧交易信息，交易失败======");
					throw new IRollbackException(mySessionCtx, "无法找到交易ID对应的旧交易信息，交易失败");
				}
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equalsIgnoreCase(""))
				{
					log.debug("=========交易被修改过======");
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (checkIsTouched(info, dao))
				{
					log.debug("=========被非法修改过=========");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{ //删除账簿信息
					log.debug("=========开始删除账簿信息=========");
					accountBookOperation.deleteTransCompatibility(newInfo);
					log.debug("=========结束删除账簿信息=========");
				}
			}
			log.debug("============开始PartSave===========");
			//调用保存方法
			lTransId = partSave(info, dao, detailDao);
			log.debug("============结束PartSave===========");
			//组装完整的交易信息，将主交易信息的id置入明细中
			log.debug("------开始组织交易信息--------");
			info.setId(lTransId);
			Vector vctDetail = info.getTransCompatibilityDetailInfo();
			Vector vctNewDetail = null;
			if (vctDetail != null && vctDetail.size() > 0)
			{
				vctNewDetail = new Vector();
				for (int i = 0; i < vctDetail.size(); i++)
				{
					TransCompatibilityDetailInfo detailInfoTemp = (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
					if (detailInfoTemp != null)
					{
						detailInfoTemp.setCompatibilityID(lTransId);
						//获取并设置子账户id
						detailInfoTemp.setSubAccountID(getSubAccountIDByDetaiInfo(detailInfoTemp));
						vctNewDetail.add(detailInfoTemp);
					}
				}
				if (vctNewDetail != null && vctNewDetail.size() > 0)
				{
					info.setTransCompatibilityDetailInfo(vctNewDetail);
				}
			}
			//保存账簿信息
			log.debug("------开始保存账簿信息--------");
			log.debug(UtilOperation.dataentityToString(info));
			accountBookOperation.saveCompatibilityAccountDetails(info);
			log.debug("------结束保存账簿信息--------");
			log.debug("------开始更新状态到未复核--------");
			//更新状态到：2保存（未复核）
			dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
			log.debug("------更新状态到未复核成功--------");
			log.debug("=========结束保存兼容业务======");
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"保存兼容业务产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("保存兼容业务产生错误", e);
//		}
//		catch (SQLException e)
//		{
//			throw new SettlementException("保存兼容业务产生错误", e);
//		}
		finally
		{
			//解锁
			try
			{
				if (lPayAccountIDs != null && lPayAccountIDs.length > 0 && sessionID != -1)
				{
					for (int i = 0; i < lPayAccountIDs.length; i++)
					{
						utilOperation.releaseLock(lPayAccountIDs[i], sessionID);
					}
				}
			}
			catch (SQLException e)
			{
				throw new IRollbackException(
						mySessionCtx,
						"保存兼容业务解锁产生错误",
						e);
//				throw new SettlementException("保存兼容业务解锁产生错误", e);
			}
		}
		return lTransId;
	}
	/**
	 * Method delete.
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("===========开始删除兼容业务======");
		Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
		Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类
		CompatibilityAccountBookOperation accountBookOperation = new CompatibilityAccountBookOperation();
		long sessionID = -1;
		long[] lPayAccountIDs = null;
		long lReturnID = info.getId();
		try
		{
			//对客户加锁
			log.debug("===========开始对付款方客户进行加锁");
			lPayAccountIDs = findPayAccountIDByTransInfo(info); //查找业务中的付款方ID
			if (lPayAccountIDs != null && lPayAccountIDs.length > 0)
			{
				for (int i = 0; i < lPayAccountIDs.length; i++)
				{
					sessionID = utilOperation.waitUtilSuccessLock(lPayAccountIDs[i]);
				}
			}
			log.debug("===========结束对付款方客户进行加锁============");
			//校验状态是否正确
			TransCompatibilityInfo newInfo = this.findCompatibilityInfoByID(info.getId());
			log.debug("===========记录状态：" + info.getStatusID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改过
			if (checkIsTouched(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//删除交易记录
				if (info.getStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
					accountBookOperation.deleteTransCompatibility(newInfo);
				}
			}
			dao.delete(info.getId());
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"删除兼容业产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("删除兼容业产生错误", e);
//		}
//		catch (SQLException e)
//		{
//			throw new SettlementException("删除兼容业产生错误", e);
//		}
		finally
		{
			//解锁
			try
			{
				if (lPayAccountIDs != null && lPayAccountIDs.length > 0 && sessionID != -1)
				{
					for (int i = 0; i < lPayAccountIDs.length; i++)
					{
						utilOperation.releaseLock(lPayAccountIDs[i], sessionID);
					}
				}
			}
			catch (SQLException e)
			{
				//modified by mzh_fu 2007/05/011
				throw new IRollbackException(
						mySessionCtx,
						"删除兼容业务解锁产生错误",e);

//				throw new SettlementException("删除兼容业务解锁产生错误", e);
			}
		}
		log.debug("===========结束删除兼容业务======");
		return lReturnID;
	}
	/**
	 * Method findCompatibilityByQueryCondition. 链接查找
	 * 
	 * @param info
	 * @return Vector
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Vector findCompatibilityByQueryCondition(QueryByStatusConditionInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("===========开始按组合条件查找======");
		Vector vctResult = null;
		Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
		vctResult = dao.findCompatibilityByQueryCondition(info);
		log.debug("===========结束按组合条件查找======");
		return vctResult;
	}
	/**
	 * Method presave.检验交易是否重复保存 如果返回结果大于零，则交易重复
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long presave(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		//暂时保留接口
		log.debug("===========开始保存前检查======");
		log.debug("===========结束保存前检查======");
		return -1;
	}
	/**
	 * Method check. 复核交易
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long check(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return check(ACTION_CHECK, info);
	}
	/**
	 * Method cancelCheck. 取消复核交易
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long cancelCheck(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return check(ACTION_CANCEL_CHECK, info);
	}
	/**
	 * Method match. 匹配
	 * 
	 * @param info
	 * @param specialoperationinfoid
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransCompatibilityInfo match(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("===========开始匹配兼容业务======");
		TransCompatibilityInfo returnInfo = null;
		TransCompatibilityDetailInfo detailMatchInfo = null;
		TransCompatibilityDetailInfo detailInfo = null;
		boolean IsNeedContinueMatchNextTime = true; //下一次匹配是否需要继续
		Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
		Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
		Vector vctMain = null;
		Vector vctDetail = new Vector();
		Vector vctDetailTemp = new Vector();
		log.debug("===========开始匹配主要兼容业务信息======");
		vctMain = dao.match(info);
		if (vctMain != null && vctMain.size() > 0)
		{
			log.debug("=========匹配到的主交易信息条数为：" + vctMain.size());
			for (int j = 0; j < vctMain.size(); j++)
			{
				log.debug("=========进行第" + j + "次匹配=====");
				if (IsNeedContinueMatchNextTime)
				{
					//标示本次匹配过程中是否需要继续匹配
					boolean IsNeedContinueMatchThisTime = true;
					returnInfo = (TransCompatibilityInfo) vctMain.elementAt(j);
					if (returnInfo != null)
					{
						log.debug("===========主交易信息不为空======");
						vctDetail = info.getTransCompatibilityDetailInfo();
						if (vctDetail != null && vctDetail.size() > 0)
						{
							vctDetailTemp = new Vector();
							log.debug("=========== vctDetail.size():" + vctDetail.size());
							for (int i = 0; i < vctDetail.size(); i++)
							{
								if (IsNeedContinueMatchThisTime)
								{
									detailMatchInfo = (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
									log.debug("===========开始匹配本次兼容业务明细信息======");
									if (detailMatchInfo != null)
									{
										//限定主交易
										log.debug("==========本次主交易id:" + returnInfo.getId());
										detailMatchInfo.setCompatibilityID(returnInfo.getId());
										log.debug("===========开始匹配本次兼容业务明细信息" + i);
										detailInfo = detailDao.match(detailMatchInfo);
										if (detailInfo == null)
										{
											log.debug("===========本次匹配明细" + i + "失败");
											IsNeedContinueMatchThisTime = false;
										}
										else
										{
											log.debug("===========本次匹配明细" + i + "成功");
											vctDetailTemp.add(detailInfo);
										}
									}
								}
								else
								{
									break;
								}
							}
						}
						if (IsNeedContinueMatchThisTime)
						{
							//本次匹配成功
							log.debug("======本次匹配成功====");
							if (vctDetailTemp != null && vctDetailTemp.size() > 0)
							{
								returnInfo.setTransCompatibilityDetailInfo(vctDetailTemp);
							}
							IsNeedContinueMatchNextTime = false;
						}
						else
						{
							//本次明细匹配不成功
							log.debug("======本次匹配失败====");
							IsNeedContinueMatchNextTime = true;
							returnInfo = null;
						}
					}
				}
				else
				{
					break;
				}
			}
		}
		log.debug("===========结束匹配兼容业务======");
		return returnInfo;
	}
	/**
	 * Method check. 内部方法处理复核和取消复核
	 * 
	 * @param checkOrCancelCheck
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long check(int checkOrCancelCheck, TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("=========开始复核（取消复核）兼容业务======");
		long sessionID = -1;
		CompatibilityAccountBookOperation accountBookOperation = new CompatibilityAccountBookOperation();
		UtilOperation utilOperation = new UtilOperation();
		TransCompatibilityDetailInfo detailInfo = null;
		Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
		Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
		long[] lPayAccountIDs = null;
		try
		{
			//对付款方客户进行加锁
			log.debug("===========开始对付款方客户进行加锁");
			lPayAccountIDs = findPayAccountIDByTransInfo(info); //查找业务中的付款方ID
			if (lPayAccountIDs != null && lPayAccountIDs.length > 0)
			{
				for (int i = 0; i < lPayAccountIDs.length; i++)
				{
					sessionID = utilOperation.waitUtilSuccessLock(lPayAccountIDs[i]);
				}
			}
			//检验状态是否正确
			TransCompatibilityInfo newInfo = this.findCompatibilityInfoByID(info.getId());
			if (newInfo == null)
			{
				log.debug("=========无法找到交易ID对应的旧交易信息，交易失败======");
				throw new IRollbackException(mySessionCtx, "无法找到交易ID对应的旧交易信息，交易失败");
			}
			String errMsg = "";
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
			}
			else
			{
				errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
			}
			//被修改过
			if (errMsg != null && !errMsg.equalsIgnoreCase(""))
			{
				log.debug("=========交易被修改过======");
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				if (checkIsTouched(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				if (checkIsTouched(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			dao.update(info);
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				log.debug("=========开始复核兼容业务======");
				//处理账薄和会计账
				accountBookOperation.checkCompatibilityDetails(info);
				//更新交易状态到复核
				dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.CHECK);
			}
			else
			{
				log.debug("=========开始取消复核兼容业务======");
				//取消处理账薄和会计账
				accountBookOperation.cancelCheckCompatibilityDetails(info);
				//更新交易状态到未复核即保存状态
				dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
			}
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"复核（取消复核）兼容业务产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("复核（取消复核）兼容业务产生错误", e);
//		}
//		catch (SQLException e)
//		{
//			throw new SettlementException("复核（取消复核）兼容业务产生错误", e);
//		}
		finally
		{
			//解锁
			try
			{
				if (lPayAccountIDs != null && lPayAccountIDs.length > 0 && sessionID != -1)
				{
					for (int i = 0; i < lPayAccountIDs.length; i++)
					{
						utilOperation.releaseLock(lPayAccountIDs[i], sessionID);
					}
				}
			}
			catch (SQLException e)
			{
				//modified by mzh_fu 2007/05/011				
				throw new IRollbackException(
						mySessionCtx,
						"复核（取消复核）兼容业务解锁产生错误",
						e);
//				throw new SettlementException("复核（取消复核）兼容业务解锁产生错误", e);
			}
		}
		log.debug("=========结束复核（取消复核）兼容业务======");
		return info.getId();
	}
	/**
	 * Method partSave.
	 * 
	 * @param info
	 * @param dao
	 * @return long
	 * @throws IRollbackException 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long partSave(TransCompatibilityInfo info, Sett_TransCompatibilityDAO dao, Sett_TransCompatibilityDetailDAO detailDao) throws SettlementException, IRollbackException
	{
		try
		{
			log.info("================进入方法partSave===========");
			if (dao.findByID(info.getId()) == null)
			{
				log.info("================新增===========");
				info.setModifyDate(Env.getSystemDateTime());
				long lID = dao.add(info);
				log.info("================交易ID===========:" + lID);
				TransCompatibilityDetailInfo detailInfo = null;
				Vector vctTemp = info.getTransCompatibilityDetailInfo();
				if (vctTemp != null && vctTemp.size() > 0)
				{
					for (int i = 0; i < vctTemp.size(); i++)
					{
						detailInfo = (TransCompatibilityDetailInfo) vctTemp.elementAt(i);
						if (detailInfo != null)
						{
							detailInfo.setCompatibilityID(lID);
							detailInfo.setSubAccountID(getSubAccountIDByDetaiInfo(detailInfo));
							detailDao.add(detailInfo);
						}
					}
				}
			}
			else
			{
				log.info("================更新===========");
				info.setModifyDate(Env.getSystemDateTime());
				dao.update(info);
				TransCompatibilityDetailInfo detailInfo = null;
				Vector vctTemp = info.getTransCompatibilityDetailInfo();
				if (vctTemp != null && vctTemp.size() > 0)
				{
					for (int i = 0; i < vctTemp.size(); i++)
					{
						detailInfo = (TransCompatibilityDetailInfo) vctTemp.elementAt(i);
						if (detailInfo != null)
						{
							log.info("=====明细主关键字:" + detailInfo.getId());
							detailInfo.setSubAccountID(getSubAccountIDByDetaiInfo(detailInfo));
						}
						detailDao.update(detailInfo);
					}
				}
			}
			log.info("================退出方法partSave===========");
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"部分保存兼容业务产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("部分保存兼容业务产生错误", e);
//		}
//		catch (SettlementException e)
//		{
//			throw new SettlementException("部分保存兼容业务产生错误", e);
//		}
		return info.getId();
	}
	/**
	 * Method findPayAccountIDByTransInfo ，此方法需区分中油还是国机 根 据兼容交易的信息查找对应的付款方账户id集
	 * 合
	 * 
	 * @param info
	 * @return long[]
	 * @throws Exception
	 */
	private long[] findPayAccountIDByTransInfo(TransCompatibilityInfo info) throws SettlementException
	{
		log.debug("===========开始查找与交易相关的付款方账户Id集合");
		Vector vctTemp = null;
		TransCompatibilityDetailInfo detailInfo = null;
		vctTemp = info.getTransCompatibilityDetailInfo();
		long lAccountID = -1;
		long lAccountTypeID = -1; //账户类型id
		long lTransDirection = -1; //交易方向
		long lAccountTypeBalanceDirection = -1; //账户类型余额方向
		long[] lReturnIDs = null;
		try
		{
			AccountOperation accountOperation = new AccountOperation();
			AccountInfo accountInfo = null;
			Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
			AccountTypeInfo accountTypeInfo = null;
			if (vctTemp != null && vctTemp.size() > 0)
			{
				lReturnIDs = new long[vctTemp.size()];
				for (int i = 0; i < vctTemp.size(); i++)
				{
					detailInfo = (TransCompatibilityDetailInfo) vctTemp.elementAt(i);
					if (detailInfo != null)
					{
						lAccountID = detailInfo.getAccountID();
						lTransDirection = detailInfo.getTransDirectionID();
						accountInfo = accountOperation.findAccountByID(lAccountID);
						if (accountInfo != null)
						{
							lAccountTypeID = accountInfo.getAccountTypeID();
							accountTypeInfo = sett_AccountTypeDAO.findByID(lAccountTypeID);
							if (accountTypeInfo != null)
							{
								lAccountTypeBalanceDirection = accountTypeInfo.getBalanceDirection();
							}
							if (lTransDirection > 0 && lAccountTypeBalanceDirection > 0 && lTransDirection != lAccountTypeBalanceDirection)
							{
								lReturnIDs[i] = lAccountID;
							}
							else
							{
								lReturnIDs[i] = -1;
							}
						}
					}
				}
			}
		}
		catch (RemoteException e)
		{
			throw new SettlementException("查找与交易相关的付款方账户Id集合产生错误", e);
		}
		catch (IRollbackException e)
		{
			throw new SettlementException("查找与交易相关的付款方账户Id集合产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementException("查找与交易相关的付款方账户Id集合产生错误", e);
		}
		log.debug("===========结束查找与交易相关的付款方账户Id集合");
		return lReturnIDs;
	}
	/**
	 * Method checkIsTouched. 检验记录是否被非法修改过
	 * 
	 * @param info
	 * @return boolean
	 * @throws Exception
	 */
	private boolean checkIsTouched(TransCompatibilityInfo info, Sett_TransCompatibilityDAO dao) throws SettlementException
	{
		//判断是否被非法修改过
		Timestamp lastTouchDate;
		log.debug("=====开始查找修改时间:");
		lastTouchDate = dao.findTouchDate(info.getId());
		log.debug("=====最新修改时间:" + lastTouchDate);
		//@TBD: get touch date from info class
		Timestamp curTouchDate = info.getModifyDate();
		log.debug("=====当前修改时间:" + curTouchDate);
		/*
		 * if (curTouchDate == null || lastTouchDate.compareTo(curTouchDate) !=
		 * 0) { log.debug("=====true"); return true; }
		 */
		if (curTouchDate == null)
		{
			log.debug("=====true1");
			return true;
		}
		else
			if (!lastTouchDate.equals(curTouchDate))
			{
				log.debug("=====true2");
				return true;
			}
			else
			{
				log.debug("=====false");
				return false;
			}
	}
	/**
	 * Method findCompatibilityInfoByID. 查找兼容业务的完整信息，包括主要信息和明细信息
	 * 
	 * @param lTransID
	 * @param dao
	 * @param detailDao
	 * @return TransCompatibilityInfo
	 */
	private TransCompatibilityInfo findCompatibilityInfoByID(long lTransID, Sett_TransCompatibilityDAO dao, Sett_TransCompatibilityDetailDAO detailDao) throws SettlementException
	{
		TransCompatibilityInfo returnInfo = null;
		returnInfo = dao.findByID(lTransID);
		if (returnInfo != null)
		{
			Vector vctTemp = null;
			vctTemp = detailDao.findByMainID(lTransID);
			if (vctTemp != null && vctTemp.size() > 0)
			{
				returnInfo.setTransCompatibilityDetailInfo(vctTemp);
			}
		}
		return returnInfo;
	}
	/**
	 * Method getSubAccountIDByDetaiInfo.
	 * 根据明细信息取对应的子账户id
	 * @param detailInfo
	 * @return long
	 * @throws SettlementException
	 */
	private long getSubAccountIDByDetaiInfo(TransCompatibilityDetailInfo detailInfo) throws SettlementException
	{
		long lSubAccountID = -1;
		try
		{
			AccountOperation accountOperation = new AccountOperation();
			AccountBean accountBean = new AccountBean();
			AccountInfo accountInfo = null;
			long lAccountTypeID = -1;
			long lAccountGroupID = -1;
			accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
			if (accountInfo != null)
			{
				lAccountTypeID = accountInfo.getAccountTypeID();
				lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
				if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
						|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
				{
					//活期账户
					log.info("========活期账户=========");
					try
					{
						lSubAccountID = accountBean.getCurrentSubAccoutIDByAccoutID(detailInfo.getAccountID());
					}
					catch (IException e)
					{
						throw new IRollbackException(mySessionCtx, e.getMessage());
					}
				}
				if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
						|| lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
				{
					//定期账户
					log.info("========定期账户=========");
					try
					{
						lSubAccountID = accountBean.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(detailInfo.getAccountID(), detailInfo.getDepositForm());
					}
					catch (IException e)
					{
						throw new IRollbackException(mySessionCtx, e.getMessage());
					}
				}
				if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
						|| lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
						|| lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
						|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
				{
					//贷款账户
					log.info("========贷款账户=========");
					try
					{
						lSubAccountID = accountBean.getLoanSubAccountIDByAccountIDAndLoanNoteID(detailInfo.getAccountID(), detailInfo.getDueBillID());
					}
					catch (IException e)
					{
						throw new IRollbackException(mySessionCtx, e.getMessage());
					}
				}
			}
		}
		catch (RemoteException e)
		{
			throw new SettlementException("获取子账户信息产生异常", e);
		}
		catch (IRollbackException e)
		{
			throw new SettlementException("获取子账户信息产生异常", e);
		}
		return lSubAccountID;
	}
	/**
	 * Method getIDByTransNo.
	 * @param strTransNo
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException, SettlementException
	{
		log.info("========根据交易号查找兼容交易主键=========");
		long lID = -1;
		Sett_TransCompatibilityDAO sett_TransCompatibilityDAO = new Sett_TransCompatibilityDAO();
		lID = sett_TransCompatibilityDAO.getIDByTransNo(strTransNo);
		return lID;
	}
}