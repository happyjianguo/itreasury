/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transgeneralledger.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.transgeneralledger.dao.Sett_TransGeneralLedgerDAO;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransGeneralLedgerEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private static  Object lockObj = new Object();  //静态
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
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
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}

	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 */
	public long preSave(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		TransGeneralLedgerInfo queryInfo = info.getQeureyInfo();

		Collection c = this.findByConditions(queryInfo, -1, false);
		if (c != null && c.size() > 0)
		{
			if (c.size() == 1)
			{
				TransGeneralLedgerInfo tmpInfo = (TransGeneralLedgerInfo) ((ArrayList) c).get(0);
				if (tmpInfo.getID() == info.getID())
					return SETTConstant.PreSaveResult.NORMAL;
			}
			return SETTConstant.PreSaveResult.REPEATED;
		}

		return SETTConstant.PreSaveResult.NORMAL;
		}
	}

	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long tempSave(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		long depositId = -1;

		try
		{
			//更新状态到：暂存
			info.setStatusID(SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("------开始　tempSave--------");
			if (info.getID() == -1)
				depositId = dao.add(info);
			else
				depositId = dao.update(info);
			log.debug("------结束　tempSave--------");			
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
		return depositId;
		}
	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long save(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long depositId = -1;
		//Sett_TransGeneralLedger数据访问对象
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//获取当前的交易号
			String transNo = info.getTransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.equalsIgnoreCase(""))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransActionTypeID());
				info.setTransNo(transNo);
				log.debug("------新交易号是:" + transNo + "--------");
			}
			else
			{
				//被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("------开始判断是否被非法修改过--------");

				//校验状态是否正确
				TransGeneralLedgerInfo newInfo = this.findByID(info.getID());
				String errMsg =
					UtilOperation.checkStatus(
						info.getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, dao))
				{
					log.debug("------被非法修改过--------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//删除账簿信息
					log.debug("------开始删除账簿信息--------");
					TransGeneralLedgerInfo oldTransInfo = dao.findByID(info.getID());
					if (oldTransInfo == null)
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					log.debug(oldTransInfo.toString());
					log.debug(newInfo.toString());
					accountBookOperation.deleteGeneralLedgerOperation(oldTransInfo);
					log.debug("------结束删除账簿信息--------");
				}
			}
			log.debug("------开始　Save--------");
			if (info.getID() == -1)
				depositId = dao.add(info);
			else
				depositId = dao.update(info);
			log.debug("------结束　Save--------");
			info.setID(depositId);

			//保存账簿信息	
			log.debug("------开始保存账簿信息--------");
			accountBookOperation.saveGeneralLedgerOperation(info);
			log.debug("------结束保存账簿信息并开始更新状态到未复核--------");
			//更新状态到：2保存（未复核）
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
			log.debug("------更新状态到未复核成功--------");
			
			
			/**
			 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
			 */
				if(info.getInutParameterInfo()!=null)
				{
				log.debug("------提交审批--------");
				//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+depositId);
				
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
				throw new IRollbackException(mySessionCtx, "@TBD:Error Code--", e);
			}
		}

		return depositId;
		}
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long delete(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//校验状态是否正确
			TransGeneralLedgerInfo newInfo = this.findByID(info.getID());
			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改过			
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//删除交易记录
				if (info.getStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
					accountBookOperation.deleteGeneralLedgerOperation(newInfo);
				}
			}
			//返回ID
			return dao.delete(info.getID());
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
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		}
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long check(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//校验状态是否正确
			TransGeneralLedgerInfo newInfo = this.findByID(info.getID());

			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);

			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			//判断是否被修改
			if (isTouch(info, dao)){
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}

			//复核交易记录
			log.info("--------------开始AccountBook复核交易记录--------------");
			accountBookOperation.checkGeneralLedgerOperation(info);

			info.setStatusID(SETTConstant.TransactionStatus.CHECK);
			dao.update(info);
			log.info("--------------结束AccountBook复核交易记录--------------");
			
			//判断交易是否需要生成指令，如果存在活期账借贷则指令生成
			log.debug("------构造总账银行付款指令开始--------");
			if( info.getAccountID()>0 ) {
				/***********构造银行付款指令**********/
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				long bankID = info.getBillTypeID();//银行类型id
				
				if(bIsValid) {//有银企接口并且是需要生成银行指令
					Log.print("*******************开始产生银行总账指令，并保存**************************");
					try {
						log.debug("------开始总账指令生成--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransActionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成银行总账指令结束--------");
						
					} catch (Throwable e) {
						log.error("生成银行总账指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成银行总账指令失败："+e.getMessage());
					}
				}
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
			}
			else {
				log.info("无活期账号参与借贷业务不需要指令形成！");
			}

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
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, "@TBD:Error Code--", e);
			}
		}

		return info.getID();
		}
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{   
		synchronized(lockObj){
		long lReturn = -1;
		long sessionID = -1;
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation;
		accountBookOperation = new AccountBookOperation();

		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//校验状态是否正确
			TransGeneralLedgerInfo newInfo = this.findByID(info.getID());

			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);

			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			//判断是否被修改
			if (isTouch(info, dao))
				throw new IRollbackException(mySessionCtx, "Sett_E024");

			//取消复核交易记录
			log.info("--------------开始取消AccountBook复核交易记录--------------");
			accountBookOperation.cancelCheckGeneralLedgerOperation(info);

			//更新状态到：未复核
			info.setStatusID(SETTConstant.TransactionStatus.SAVE);
			dao.update(info);

			log.info("--------------结束取消AccountBook复核交易记录--------------");

			
//			更新状态到：未复核或已审批(根据该业务是否有审批流判断)
			long lCancelCheckStatus = FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransActionTypeID(),
					-1));
			info.setStatusID(lCancelCheckStatus);
			lReturn = dao.update(info);
			
			
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
				throw new IRollbackException(mySessionCtx, "@TBD:Error Code--", e);
			}
		}
		return info.getID();
		}
	}

	public Collection findByConditions(TransGeneralLedgerInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger数据访问对象
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		try
		{
			return dao.findByConditions(info, orderByType, isDesc);
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
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransGeneralLedgerInfo match(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger数据访问对象
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		try
		{
//			控制匹配复核状态
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
																		info.getCurrencyID(),
																		Constant.ModuleType.SETTLEMENT,
																		info.getTransActionTypeID(),
																		-1)));		
			return dao.match(info);
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
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransGeneralLedgerInfo
	 * @throws IRollbackException
	 */
	public TransGeneralLedgerInfo findByID(long lID) throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger数据访问对象
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		try
		{
			return dao.findByID(lID);
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

	private boolean isTouch(TransGeneralLedgerInfo info, Sett_TransGeneralLedgerDAO dao)
		throws RemoteException, IRollbackException
	{
		try
		{
			//判断是否被非法修改过
			Timestamp lastTouchDate;
			lastTouchDate = dao.findTouchDate(info.getID());

			//@TBD: get touch date from info class
			Timestamp curTouchDate = info.getModifyDate();
			if (curTouchDate == null || lastTouchDate.compareTo(curTouchDate) != 0)
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
			throw new IRollbackException(mySessionCtx, "SQLException in TransCurrentDepositEJB", e);
		}

	}
	/**
	 * 审批方法（总账）。
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransGeneralLedgerInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		TransGeneralLedgerInfo transGeneralLedgerInfo = info.getTransGeneralLedgerInfo();
		depositId =info.getID();
		try
		{
         //将业务记录置入pinfo,转换成标准map传递到审批流引擎
			transGeneralLedgerInfo = this.findByID(depositId);	
			inutParameterInfo.setDataEntity(transGeneralLedgerInfo);
			
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
					//构造check参数
					TransGeneralLedgerInfo depositInfo = new TransGeneralLedgerInfo();
					depositInfo = this.findByID(info.getID());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo.setAbstract("机核");
					depositInfo.setCheckUserID(returnInfo.getUserID());					
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					//调用openCheck方法
					this.check(depositInfo);
				}	
			}
        //如果是最后一级,且为审批拒绝,更新状态为已保存
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(
						transGeneralLedgerInfo.getID(),
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
		return depositId;
		}
	}
	
	
	/**
	 * 取消审批方法（总帐）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransGeneralLedgerInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && info.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				this.cancelCheck(info);
				//取消审批
				lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && info.getStatusID()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//取消审批
				lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
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
	}
	
}
