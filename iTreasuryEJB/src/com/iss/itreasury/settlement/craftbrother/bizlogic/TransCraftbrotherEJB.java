package com.iss.itreasury.settlement.craftbrother.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionBean;
import com.iss.itreasury.craftbrother.notice.dao.SEC_NoticeDAO;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcredence.bizlogic.TransDiscountCredenceBiz;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.craftbrother.dao.TransCraInterestPreDrawDAO;
import com.iss.itreasury.settlement.craftbrother.dao.TransCraftbrotherDAO;
import com.iss.itreasury.settlement.craftbrother.dataentity.CalcNoticeInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.CraInterestCalcInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.DraftAmortizationInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author qhzhou
 *
 */
public class TransCraftbrotherEJB implements SessionBean {

	private javax.ejb.SessionContext mySessionCtx = null;
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private static final long serialVersionUID = 4084208537156611761L;

	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException,
			java.rmi.RemoteException {
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException {
	}

	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
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
	public void setSessionContext(javax.ejb.SessionContext ctx)
			throws RemoteException {
		mySessionCtx = ctx;
	}

	/** 交易暂存和修改暂存接口 */
	public long tempSave(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException {
		
		long lReturn = -1;
		try{
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			//账簿操作接口类 
			AccountOperation accountOperation = new AccountOperation();
			if(info.getId() < 0){
				info.setNstatusId(SETTConstant.TransactionStatus.TEMPSAVE);
				lReturn = transDao.add(info);
			}else{
				lReturn = info.getId();
				//1.判断状态是否被修改
				if(isTouch(info, transDao)){
					throw new IRollbackException(mySessionCtx, "Sett_E153");
				}
				//2.判断状态是否合法
				String errMsg = checkStatus(SETTConstant.Actions.MODIFYTEMPSAVE, info, transDao);
				if (errMsg != null && !errMsg.equals("")){
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				//修改暂存
				info.setDtModify(Env.getSystemDateTime());//修改时间
				transDao.update(info);
			}
			accountOperation.saveExternalAccount(info.getExternalAccountInfo());
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** 交易新增和修改保存接口 */
	public long save(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException {
		long lReturn = -1;
		try{
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			//定义工具类
			UtilOperation utilOperation = new UtilOperation();
			//账簿操作接口类 
			AccountOperation accountOperation = new AccountOperation();
			String stransNo = info.getStransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (stransNo == null || stransNo.equalsIgnoreCase(""))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				stransNo = utilOperation.getNewTransactionNo(
								info.getNofficeId(),
								info.getNcurrencyId(),
								info.getNtransactionTypeId());
				info.setStransNo(stransNo);
				Log.print("----开始校验通知单(凭证)状态----");
				if(info.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_RECEIVE)
				{
					if (transDao.checkTransactionRecord(info.getNnoticeId(),SETTConstant.TransactionStatus.SAVE))
					{
						Log.print("----该合同的凭证已经存在交易记录----");
						throw new IRollbackException(mySessionCtx, "该凭证已经存在交易记录");
					}
					
				}else{
					if (!transDao.checkPayForm(info.getNnoticeId(),info.getNcraBusinessTypeId()))
					{
						Log.print("----通知单（凭证）已经被使用----");
						throw new IRollbackException(mySessionCtx, "通知单（凭证）已经被使用");
					}
				}
			}
					
			if(info.getId() < 0){
				info.setNstatusId(SETTConstant.TransactionStatus.SAVE);
				lReturn = transDao.add(info);
			}else{
				lReturn = info.getId();
				info.setNstatusId(SETTConstant.TransactionStatus.SAVE);
				//1.判断状态是否被修改
				if(isTouch(info, transDao)){
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				//2.判断状态是否合法
				String errMsg = checkStatus(SETTConstant.Actions.MODIFYSAVE, info, transDao);
				if (errMsg != null && !errMsg.equals("")){
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				//修改保存
				info.setDtModify(Env.getSystemDateTime());//修改时间
				transDao.update(info);
			}
			accountOperation.saveExternalAccount(info.getExternalAccountInfo());
			if(lReturn > 0){
				if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.DISCOUNT){//转贴现业务
					//更新凭证的状态为“已使用”
					TransDiscountCredenceDAO dao=new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
					dao.update(info.getNnoticeId(),info.getNinputUserId(),LOANConstant.DiscountCredenceStatus.USED);
				}else if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.FUND_ATTORN    
						|| info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS){//资金拆借//资产转让
				    SEC_NoticeDAO nDao = new SEC_NoticeDAO();
				    nDao.updateStatusID(info.getNnoticeId(),SECConstant.NoticeFormStatus.USED);
				}else{
					throw new IRollbackException(mySessionCtx, "同业业务类型错误，更新通知单状态失败!");
				}
			}
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** 交易删除接口 */
	public long delete(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException {
		long lReturn = info.getId();
		try{
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			//1.判断状态是否被修改
			if(isTouch(info, transDao)){
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			//2.判断状态是否合法
			String errMsg = checkStatus(SETTConstant.Actions.DELETE, info, transDao);
			if (errMsg != null && !errMsg.equals("")){
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			TransCraftbrotherInfo tmpinfo = new TransCraftbrotherInfo();
			tmpinfo.setId(lReturn);
			tmpinfo.setNstatusId(SETTConstant.TransactionStatus.DELETED);
			transDao.update(tmpinfo);
			if(lReturn > 0){
				if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.DISCOUNT){//转贴现业务
					//更新凭证的状态为“已审批”
					TransDiscountCredenceDAO dao=new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
					if(info.getNtransactionTypeId() != SETTConstant.TransactionType.TRANS_DISCOUNT_RECEIVE)
						dao.update(info.getNnoticeId(),info.getNinputUserId(),LOANConstant.DiscountCredenceStatus.CHECK);
				}else if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.FUND_ATTORN    
						|| info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS){//资金拆借//资产转让
				    SEC_NoticeDAO nDao = new SEC_NoticeDAO();
				    nDao.updateStatusID(info.getNnoticeId(),SECConstant.NoticeFormStatus.CHECKED);
				}else{
					throw new IRollbackException(mySessionCtx, "同业业务类型错误，更新通知单状态失败!");
				}
			}
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** ID查找交易 */
	public TransCraftbrotherInfo findByID(long lId) throws RemoteException {
		TransCraftbrotherInfo rInfo = null;
		try{
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			rInfo = (TransCraftbrotherInfo)transDao.findByID(lId, TransCraftbrotherInfo.class);
		}catch(Exception e){
			throw new RemoteException("Gen_E001",e);
		}
		return rInfo;
	}
	/** 交易号条件查找交易 */
	public TransCraftbrotherInfo findByTransNo(String sTransNo) throws RemoteException {
		TransCraftbrotherInfo retInfo = null;
		try{
			
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			retInfo = transDao.findByTransNo(sTransNo);
		}catch(Exception e){
			throw new RemoteException("Gen_E001",e);
		}
		return retInfo;
	}
	/** 匹配查找交易 */
	public TransCraftbrotherInfo match(TransCraftbrotherInfo qInfo)throws RemoteException {
		TransCraftbrotherInfo rInfo = null;
		try{
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			rInfo = (TransCraftbrotherInfo)transDao.match(qInfo);
		}catch(Exception e){
			throw new RemoteException("Gen_E001",e);
		}
		return rInfo;
	}

	/**功能能说明：链接查找交易
	 * 参数说明：
	 * @param lQueryPurpose 1:修改连接查找 2:复核链接查找
	 * @param lStatusId 查询状态
	 * @param lUserId   当前查询用户
	 * @param nOrderByCode 排序字段名称
	 * @param lIsDesc 是否降序
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection linkSearch(long lQueryPurpose,long lTransactionTypeId,long lStatusId, long lUserId,
			int nOrderIndex, boolean lIsDesc) throws RemoteException {
		
		try{
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			return transDao.linkSearch(lQueryPurpose,lTransactionTypeId, lStatusId, lUserId, nOrderIndex, lIsDesc);
		}catch(Exception e){
			throw new RemoteException(e.getMessage());
		}
	}

	/** 交易复核接口 */
	public long check(TransCraftbrotherInfo info) throws RemoteException,IRollbackException {
		long lReturn = -1;
		try{
			lReturn = this.check(SETTConstant.Actions.CHECK, info);
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** 交易取消复核接口 */
	public long cancelCheck(TransCraftbrotherInfo info) throws RemoteException,IRollbackException {
		long lReturn = -1;
		try{
			lReturn = this.check(SETTConstant.Actions.CANCELCHECK, info);
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** 同业计提交易新增保存接口 */
	public long saveInterestPreDraw(TransCraInterestPreDrawInfo info)
			throws RemoteException, IRollbackException {

		long lReturn = -1;
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		List draftList = null;
		DraftAmortizationInfo DraftInfo = null;
		try{
			//定义数据操作DAO
			TransCraInterestPreDrawDAO transDao = new TransCraInterestPreDrawDAO();
			TransCraftbrotherDAO draftDao = new TransCraftbrotherDAO("DraftAmortization");
			draftDao.setUseMaxID();
			//定义工具类
			UtilOperation utilOperation = new UtilOperation();
			String stransNo = info.getStransno();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (stransNo == null || stransNo.equalsIgnoreCase(""))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				stransNo = utilOperation.getNewTransactionNo(
								info.getNofficeid(),
								info.getNcurrencyid(),
								info.getNtransactiontypeid());
				info.setStransno(stransNo);
			}
			info.setNstatusid(SETTConstant.TransactionStatus.CHECK);
			//第一步：生成计提交易
			lReturn = transDao.add(info);
			//第二步：生成票据摊销信息
			
			draftList = info.getDraftList();
			if(draftList!=null)
			{
				Iterator it = draftList.iterator();
				while(it.hasNext())
				{
					DraftInfo = (DraftAmortizationInfo)it.next();
					DraftInfo.setAmortizationID(lReturn);
					DraftInfo.setNstatus(SETTConstant.TransactionStatus.SAVE);
					draftDao.add(DraftInfo);
				}
			}
			
			//第三步：生成计提会计分录
			if(lReturn > 0){
				/**
				 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
				 */
				GenerateGLEntryParam param = new GenerateGLEntryParam();
				/**
				 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
				 */
				long lPrincipalType = -1;
				long lBankId = -1;
				
				//本金流向是 "无关"
				lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
				
				//分录类型 无关
				long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
				//收款方账户
				long receiveAccountID = -1;
				//付款方账户
				long payAccountID = -1;
				//本金开户行ID
				long principalBankID = lBankId;
				//发生额
				double dAmount = info.getMpredrawinterest();
				param.setOfficeID(info.getNofficeid());
				param.setCurrencyID(info.getNcurrencyid());
				param.setTransactionTypeID(info.getNtransactiontypeid());
				param.setSubTransactionType(info.getNsubtransactiontypeid());
				//param.setExecuteDate(info.getDtinterestend());
				param.setExecuteDate(Env.getSystemDate(info.getNofficeid(),info.getNcurrencyid()));//执行日：结算开机日
				param.setInterestStartDate(DataFormat.getNextDate(info.getDtinterestend()));
				param.setTransNo(info.getStransno());
				param.setAbstractStr(info.getSabstract());
				param.setInputUserID(info.getNmakeuserid());
				param.setCheckUserID(info.getNmakeuserid());
				param.setPrincipalType(lPrincipalType);
				param.setEntryType(lEntryType);
				param.setReceiveAccountID(receiveAccountID);
				param.setPayAccountID(payAccountID);
				param.setPrincipalOrTransAmount(0.00);//本金/交易金额
				param.setTotalInterest(info.getMpredrawinterest());//利息合计
				param.setTotalPrincipalAndInterest(dAmount);//本息合计
				param.setPrincipalBankID(principalBankID);
				
				param.setCraBusinessType(info.getNcrabusinesstypeid());
				param.setCounterpartId(info.getNcounterpartid());
				log.print("----------ACCOUNTBOOKEJB:checkCurrentAccountDetails是否生成银行指令:false"+"-----------");
				param.setAutoCreateBankInstruction(false);
				boolean res = glopOperation.generateGLEntry(param);
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "产生会计分录错误");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** 同业计提交易删除接口 */
	public long deleteInterestPreDraw(TransCraInterestPreDrawInfo info) throws RemoteException,
			IRollbackException {
		long lReturn = info.getId();
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		String preTransNo = "";
		try{
			//定义数据操作DAO
			TransCraInterestPreDrawDAO transDao = new TransCraInterestPreDrawDAO();
			//第一步：校验业务逻辑
			preTransNo = transDao.validateTransaction(info);
			if(!preTransNo.equals(""))
			{
				throw new Exception("请先删除交易号为"+preTransNo+"的计提记录!");
			}
			//第二步：删除计提交易
			TransCraInterestPreDrawInfo tmpinfo = new TransCraInterestPreDrawInfo();
			tmpinfo.setId(lReturn);
			tmpinfo.setNstatusid(SETTConstant.TransactionStatus.DELETED);
			transDao.update(tmpinfo);
			//第三步：删除票据摊销记录
			transDao.updateDraftAmortization(lReturn);
			
			//第四步：删除计提会计分录
			if(lReturn > 0){
				TransCraInterestPreDrawInfo infotmp = (TransCraInterestPreDrawInfo)transDao.findByID(lReturn, TransCraInterestPreDrawInfo.class);
				glopOperation.deleteGLEntry(infotmp.getStransno());
			}
				
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}


	/** 判断交易是否被非法修改 */
	private boolean isTouch(TransCraftbrotherInfo info, TransCraftbrotherDAO dao)
			throws IException {
		boolean bReturn = false;
		try
		{
			//判断是否被非法修改过
			Timestamp lastTouchDate;
			TransCraftbrotherInfo lastinfo = null;
			lastinfo =(TransCraftbrotherInfo)dao.findByID(info.getId(), TransCraftbrotherInfo.class);
			lastTouchDate = lastinfo.getDtModify();
			Timestamp curTouchDate = info.getDtModify();
			if (curTouchDate == null || curTouchDate.compareTo(lastTouchDate) != 0)
				bReturn = true;
			else
				bReturn = false;
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001",e);
		}
		return bReturn;
	}

	/** 判断交易状态对于当前动作是否合法 */
	private String checkStatus(long lActionID, TransCraftbrotherInfo info,TransCraftbrotherDAO dao) 
	       	throws IException {
		String errMsg = "";
		try
		{
			TransCraftbrotherInfo newInfo =(TransCraftbrotherInfo)dao.findByID(info.getId(), TransCraftbrotherInfo.class);
			
			errMsg =
				UtilOperation.checkStatus(
						info.getNstatusId(),
						newInfo.getNstatusId(),
						lActionID);
		}
		catch (Exception e)
		{
			throw new IException(errMsg,e);
		}
		return errMsg;
	}
	/**
	 * Method check
	 * @descriptin  内部方法处理复核和取消复核
	 * @param  checkOrCancelCheck 复核还是取消复核
	 * @return long 
	 * @throws RemoteException，IException
	 */
	private long check(long checkOrCancelCheck,TransCraftbrotherInfo info)
										throws RemoteException, IRollbackException
	{
		long lReturn = info.getId();
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		try{
			//定义数据操作DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			
			//第一步：检查交易状态是否合法，开始-----------------------------------------------------------------
			String errMsg = checkStatus(checkOrCancelCheck, info, transDao);
			if (errMsg != null && !errMsg.equals("")){
				throw new IException(errMsg);
			}
			//第一步：结束-------------------------------------------------------------------------------------
			
			
			//第二步：检查交易是否被非法修改，开始--------------------------------------------------------------
			if(isTouch(info, transDao)){
				if(checkOrCancelCheck == SETTConstant.Actions.CHECK){
					throw new IException("Sett_E020");
				}else if(checkOrCancelCheck == SETTConstant.Actions.CANCELCHECK){
					throw new IException("Sett_E024");
				}
			}
			//第二步：结束------------------------------------------------------------------------------------
			
			
			//第三步：账簿、会计分录，开始---------------------------------------------------------------------------
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			if(checkOrCancelCheck == SETTConstant.Actions.CHECK){				
				
				accountBookOperation.checkTransCraftbrother(info);
				
			}else if(checkOrCancelCheck == SETTConstant.Actions.CANCELCHECK){
				//取消复核：删除会计分录
				//删除会计分录
				//glopOperation.deleteGLEntry(info.getStransNo());
				accountBookOperation.cancelCheckTransCraftbrother(info);
			}
			//第三步：结束------------------------------------------------------------------------------------
			
			
			//第四步：交易复核:开始----------------------------------------------------------------------------
			TransCraftbrotherInfo tmpInfo = new TransCraftbrotherInfo();
			tmpInfo.setId(lReturn);
			if(checkOrCancelCheck == SETTConstant.Actions.CHECK){
				//复核：更新交易状态“已复核”，写入复核人ID
				tmpInfo.setNcheckUserId(info.getNcheckUserId());
				tmpInfo.setDtModify(Env.getSystemDate());
				tmpInfo.setNstatusId(SETTConstant.TransactionStatus.CHECK);
				
			}else if(checkOrCancelCheck == SETTConstant.Actions.CANCELCHECK){
				//取消复核：更新交易状态“已保存”，清空复核人ID
				tmpInfo.setNcheckUserId(-1);
				tmpInfo.setDtModify(Env.getSystemDateTime());
				tmpInfo.setNstatusId(SETTConstant.TransactionStatus.SAVE);
			}
			transDao.update(tmpInfo);
			//第四步：结束------------------------------------------------------------------------------------
			
			
			//第五步：发送银行指令：开始----------------------------------------------------------------
			if(checkOrCancelCheck == SETTConstant.Actions.CHECK){				
				if((info.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT&&(info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.BREAK_INVEST||info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.REPURCHASE_INVEST))
						||(info.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_REPURCHASE&&info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.REPURCHASE_NOTIFY)
						||(info.getNtransactionTypeId()==SETTConstant.TransactionType.FUND_ATTORN && info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.CAPITAL_OUT)
						||(info.getNtransactionTypeId()==SETTConstant.TransactionType.FUND_ATTORN_REPAY&&info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.CAPITAL_IN)
						||(info.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT&&(info.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_INVEST || info.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_INVEST ))
						||(info.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE&&info.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_NOTIFY)
				        ||(info.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_INTERESTPROCESS))
				{
				log.info("--------------开始生成银行指令--------------");
				
				/***********构造银行付款指令**********/
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = false;
				long bankID = info.getNbankId();
				try {
					//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("判断传入的银行ID是否需要生成银行指令出错！");
					e1.printStackTrace();
				}
				
				if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
					Log.print("*******************开始产生银行收款指令，并保存**************************");
					try {
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getNtransactionTypeId());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getNofficeId());
						instructionParam.setCurrencyID(info.getNcurrencyId());
						instructionParam.setCheckUserID(info.getNcheckUserId());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getNinputUserId());
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成银行活期指令结束--------");
						
					} catch (Throwable e) {
						log.error("生成银行付款指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成银行付款指令失败："+e.getMessage());
					}
				}
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
				log.info("--------------结束生成银行指令--------------");
			}
			}
			
			//第五步：结束------------------------------------------------------------------------------------
		}catch(ITreasuryDAOException e){
			
			throw new IRollbackException(mySessionCtx,
					"SQLException in TransCraftbrotherEJB",e);
		}
		catch (Exception e)
		{
			throw new IRollbackException( mySessionCtx, e.getMessage(),e);
		}
		return lReturn;
	}
	/**同业利息计算接口*/
	public Collection calcInterest(CraInterestCalcInfo calcInfo) 
			throws RemoteException, IRollbackException{
		Collection coll = null;
		try{
			//定义数据操作DAO
			TransCraInterestPreDrawDAO dao = new TransCraInterestPreDrawDAO();
			coll = dao.findCredencesByCondition(calcInfo);
			if(coll != null && coll.size() > 0){
				//开始针对集合里的每张凭证计算利息
				Iterator it = coll.iterator();
				//定义临时变量
				Timestamp startInterestDate = null;//本次计提起息日
				Timestamp endInterestDate = null;//本次计提结息日
				Timestamp endCredenceDate = null;//凭证到期日
				Timestamp repurchaseDate  = null;//合同回购日期
				Timestamp discountDate  = null;//转贴现日期
				boolean isLastAmortize = false;  //是否最后一次摊销
				boolean isNext = false;  //迭代器是否指向下一个
				
				double interest = 0.00;//本次摊销利息
				long days = 0;//本次实际计息天数
			//	int i=0;
				while(it.hasNext()){
					isNext = true;

					isLastAmortize = false;
					CalcNoticeInfo info = (CalcNoticeInfo)it.next();
		        	
		        	if(info.getInorout() == LOANConstant.TransDiscountInOrOut.IN && info.getDiscountType() == LOANConstant.TransDiscountType.BUYBREAK){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_INVEST_BREAK);//转帖现买入（回购）
		        	}else if(info.getInorout() == LOANConstant.TransDiscountInOrOut.IN && info.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE);//转帖现买入（买断）
		        	}else if(info.getInorout() == LOANConstant.TransDiscountInOrOut.OUT && info.getDiscountType() == LOANConstant.TransDiscountType.BUYBREAK){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_AVERAGE_BREAK);//转帖现卖出（买断）
		        	}else if(info.getInorout() == LOANConstant.TransDiscountInOrOut.OUT && info.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_AVERAGE_REPURCHASE);//转帖现卖出（回购）
		        	}
		        	startInterestDate = info.getInterestStartDate();//本次计提起息日
		        	endInterestDate = info.getInterestEndDate();//本次计提结息日
		        	discountDate = info.getTransDate();//转贴现日期
		        	if(endInterestDate.compareTo(startInterestDate) < 0){//开始日期大于结束日期，计算下个凭证
	        			//interest = 0.00;
	        			//days = 0;
		        		if(isNext)
		        		{
		        			isNext = false;
		        			it.remove();
		        			continue;//计算下一个凭证
		        		}
	        		}
		        	
		        	if(info.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){//（回购）式
		        		repurchaseDate = info.getRepurchaseDate();
		        		repurchaseDate = DataFormat.getNextDate(repurchaseDate,-1);
		        		if(repurchaseDate.compareTo(startInterestDate) < 0){//回购日期在本次摊销日期之前
		        			//interest = 0.00;
		        			//days = 0;
		        			if(isNext)
		        			{
		        				isNext = false;
			        			it.remove();
			        			continue;//计算下一个凭证
		        			}
		        		}
		        		
		        		if(endInterestDate.compareTo(repurchaseDate) >= 0){//如果本次结息日>=回购日期，结息日截止为回购日期
		        			endInterestDate = repurchaseDate;
		        			//最后一次摊销
		        			isLastAmortize = true;
		        		}
		        		//计算实际摊销天数
		        		days = DataFormat.getIntervalDays(startInterestDate, endInterestDate);
	        			//开始查找凭证票据
	        			
	        			
	        			//long draftInterestDay = 0;  //实际计息天数
	        			long remainDay = 0;  //剩余天数
	        			long additionalDay = 0; //额外天数（节假日+非本地）
	        			double sumBillInterest = 0.00;  //摊销总利息（各票据摊销利息和）
	        			double draftInterest = 0.00;  //票据利息
	        			double draftAmortizeInterest = 0.00;  //单张票据摊销利息
	        			double restAmortizeInterest = 0.00; //剩余可摊销金额
	        			double totalDraftInterest = 0.00;  //利息总额
	        			double sumDraftAmortizeInterest = 0.00;  //票据已摊销总额
	        			double sumNotDraftAmortizeInterest = 0.00;  //票据未摊销利息总额
	        			String draftMessage = "";  //票据信息
		        		TransDiscountCredenceBiz crebiz = new TransDiscountCredenceBiz();
		        		Collection bills = crebiz.findBillByTransDiscountCredenceID(info.getId());
		        		if(bills !=null && bills.size() > 0){
		        			Iterator bIt = bills.iterator();
		        			while(bIt.hasNext()){
		        				additionalDay = 0;
		        				TransDiscountContractBillInfo bInfo = (TransDiscountContractBillInfo)bIt.next();
		        				additionalDay += bInfo.getAddDays();//+节假日增加天数
		        				// 回购式的不加非本地天数
		        				/*if(bInfo.getIsLocal()==Constant.YesOrNo.NO){//非本地+3天
		        					additionalDay += 3;//+非本地3
		        				}*/
		        				//计算票据计息天数
		        				//draftInterestDay = additionalDay + DataFormat.getIntervalDays(discountDate,repurchaseDate);
		        				
		        				//计算单张票据利息
		        				draftInterest = bInfo.getAccrual();
		        				//计算剩余可摊销金额
		        				restAmortizeInterest = UtilOperation.Arith.sub(draftInterest, bInfo.getDraftAmortizeInterest());
		        				if(isLastAmortize)
		        				{
		        					draftAmortizeInterest = restAmortizeInterest;
		        					
		        				}else
		        				{
			        				//计算摊销剩余天数
			        				remainDay = DataFormat.getIntervalDays(startInterestDate,repurchaseDate) + additionalDay;

			        				//计算单张票据摊销利息
			        				draftAmortizeInterest = restAmortizeInterest * days / remainDay;	
			        				//draftAmortizeInterest = DataFormat.roundDouble(draftAmortizeInterest, 2);
		        				}
		        				if(draftAmortizeInterest!=0.00)
		        				{
		        					draftMessage = draftMessage + bInfo.getId() + "@@" + String.valueOf(draftAmortizeInterest) + "&&";
		        				}
		        				//计算可摊销利息
		        				sumBillInterest = UtilOperation.Arith.add(sumBillInterest, draftAmortizeInterest);
		        				//计算利息总额
		        				totalDraftInterest = UtilOperation.Arith.add(totalDraftInterest,draftInterest);
		        				//计算已摊销利息
		        				sumDraftAmortizeInterest = UtilOperation.Arith.add(sumDraftAmortizeInterest,bInfo.getDraftAmortizeInterest());
		        			}
		        		}
		        		
		        		interest = sumBillInterest;
		        		//计算未摊销利息总额
		        		sumNotDraftAmortizeInterest = UtilOperation.Arith.sub(UtilOperation.Arith.sub(totalDraftInterest, sumDraftAmortizeInterest),interest);
		        		
		        		info.setDays(days);
		        		//本次摊销利息
		        		info.setInterest(interest);
		        		//利息总额
		        		info.setTotalInterest(totalDraftInterest);
		        		//已摊销利息总额
		        		info.setSumAmortizeInterest(sumDraftAmortizeInterest);
		        		//未摊销利息总额
		        		info.setSumNotAmortizeInterest(sumNotDraftAmortizeInterest);
		        		
		        		info.setInterestEndDate(endInterestDate);
		        		if(draftMessage.length()>2)
		        		{
		        			info.setDraftMessage(draftMessage.substring(0, draftMessage.length()-2));
		        		}
		        	}else{//（买断）式
		        		//开始查找凭证票据

	        			double sumBillInterest = 0.00;
	        			Timestamp billEndDate = null;//票据到期日
	        			long additionalDay = 0; //额外天数（节假日+非本地）
	        			//long draftInterestDay = 0; //实际计息天数
	        			long remainDay = 0;  //剩余天数
	        			
	        			double draftInterest = 0.00;  //票据利息
	        			double restAmortizeInterest = 0.00; //剩余可摊销金额
	        			double draftAmortizeInterest = 0.00;  //单张票据摊销利息
	        			double totalDraftInterest = 0.00;  //利息总额
	        			double sumDraftAmortizeInterest = 0.00;  //已摊销利息总额
	        			double sumNotDraftAmortizeInterest = 0.00; //未摊销利息总额
	        			//买断式取最大天数
	        			Timestamp maxDate = null; //取最大截止日
	        			long maxDay = 0;  //取最大天数
	        			boolean billNext = false;
	        			String draftMessage = "";
		        		TransDiscountCredenceBiz crebiz = new TransDiscountCredenceBiz();
		        		Collection bills = crebiz.findBillByTransDiscountCredenceID(info.getId());
		        		if(bills !=null && bills.size() > 0){
		        			Iterator bIt = bills.iterator();
		        			while(bIt.hasNext()){
		        				isLastAmortize = false;
		        				billNext = true;
		        				endInterestDate = info.getInterestEndDate();
		        				TransDiscountContractBillInfo bInfo = (TransDiscountContractBillInfo)bIt.next();
		        				additionalDay = 0;
		        				billEndDate = bInfo.getEnd();
		        				billEndDate = DataFormat.getNextDate(billEndDate,-1);
		        				
				        		if(endInterestDate.compareTo(billEndDate) >=0){//如果本次结息日>=票据到期日，结息日截止为票据到期日
				        			endInterestDate = billEndDate;
				        			//最后一次摊销
				        			isLastAmortize = true;				        			
				        		}
				        		//开始计算实际摊销天数
				        		days = DataFormat.getIntervalDays(startInterestDate, endInterestDate);
				        		
				        		//计算额外天数
			        			additionalDay += bInfo.getAddDays();//+节假日增加天数
		        				if(bInfo.getIsLocal()==Constant.YesOrNo.NO){//非本地+3天
		        					additionalDay += 3;//+非本地3
		        				}

		        				//计算票据计息天数
		        				//draftInterestDay = additionalDay + DataFormat.getIntervalDays(discountDate,billEndDate);
			        				
		        				//计算单张票据利息
		        				//draftInterest = bInfo.getAmount()*info.getRate()*draftInterestDay/36000.00;
		        				if(info.getInorout()==LOANConstant.TransDiscountInOrOut.OUT)
		        				{
		        					draftInterest = bInfo.getBreaknotifyAccrual();
		        				}
		        				else
		        				{
		        					draftInterest = bInfo.getAccrual();
		        				}
		        				//计算剩余可摊销金额
		        				restAmortizeInterest = UtilOperation.Arith.sub(draftInterest, bInfo.getDraftAmortizeInterest());
		        				if(isLastAmortize)
		        				{
		        					draftAmortizeInterest = restAmortizeInterest;
		        				}else
		        				{
			        				//计算摊销剩余天数
			        				remainDay = DataFormat.getIntervalDays(startInterestDate,billEndDate) + additionalDay;		        
			        				
			        				//计算单张票据摊销利息
			        				draftAmortizeInterest = restAmortizeInterest * days / remainDay;	
			        				//draftAmortizeInterest = DataFormat.roundDouble(draftAmortizeInterest, 2);
			        				
		        				}
	       
		        				//sumBillInterest = UtilOperation.Arith.add(sumBillInterest, draftAmortizeInterest);
		        				totalDraftInterest = UtilOperation.Arith.add(totalDraftInterest,draftInterest);
		        				//计算已摊销利息
		        				sumDraftAmortizeInterest = UtilOperation.Arith.add(sumDraftAmortizeInterest,bInfo.getDraftAmortizeInterest());		
		        				
		        				//取所有票据最大天数
		        				maxDay = Math.max(days, maxDay);
		        				if(maxDate==null)
		        				{
		        					maxDate = endInterestDate;
		        				}
		        				else
		        				{
		        					if(maxDate.compareTo(endInterestDate)<0)
		        					{
		        						maxDate = endInterestDate;
		        					}
		        				}
		        				
		        				if(billEndDate.compareTo(startInterestDate) < 0){//票据到期日期在本次计提起息日之前
				        			//interest = 0.00;
				        			//days = 0;
		        					if(billNext)
		        					{
		        						billNext = false;
		        						bIt.remove();
					        			continue;//计算下张票
		        					}
				        		}		  
		        				if(draftAmortizeInterest>0.00)
		        				{
		        					draftMessage = draftMessage + bInfo.getId() + "@@" + String.valueOf(draftAmortizeInterest) + "&&";
		        				}		        				
		        				sumBillInterest = UtilOperation.Arith.add(sumBillInterest, draftAmortizeInterest);
		        			}
		        			
		        			interest = sumBillInterest;//各票据摊销利息之和

		        		}
		        		
        				if(maxDate.compareTo(startInterestDate) < 0){//票据到期日最大日期在本次计提起息日之前
		        			//interest = 0.00;
		        			//days = 0;
        					if(isNext)
        					{
        						isNext = false;
        						it.remove();
			        			continue;//计算下一个凭证
        					}
		        		}		        		
		        		
		        		//计算未摊销利息总额
		        		sumNotDraftAmortizeInterest = UtilOperation.Arith.sub(UtilOperation.Arith.sub(totalDraftInterest, sumDraftAmortizeInterest),interest);
		        		
		        		info.setDays(maxDay);
		        		//本次摊销利息
		        		info.setInterest(interest);
		        		//利息总额
		        		info.setTotalInterest(totalDraftInterest);
		        		//记录已摊销利息总额
		        		info.setSumAmortizeInterest(sumDraftAmortizeInterest);
		        		
		        		info.setSumNotAmortizeInterest(sumNotDraftAmortizeInterest);
		        		
		        		info.setInterestEndDate(maxDate);
		        		if(draftMessage.length()>2)
		        		{
		        			info.setDraftMessage(draftMessage.substring(0, draftMessage.length()-2));
		        		}
		        	}

				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e);
		}
		return coll;
	}

	/** 结算同业利息计提交易查找接口 */
	public PageLoader searchTransInterestPerDraw(
			TransCraInterestPreDrawInfo info) throws RemoteException{
		try{
			//定义数据操作DAO
			TransCraInterestPreDrawDAO transDao = new TransCraInterestPreDrawDAO();
			return transDao.searchTransInterestPerDraw(info);
		}catch(Exception e){
			throw new RemoteException(e.getMessage());
		}
	}
}
