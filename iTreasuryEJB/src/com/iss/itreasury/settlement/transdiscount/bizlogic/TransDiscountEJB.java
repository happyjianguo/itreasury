/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transdiscount.bizlogic;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransRepaymentDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountSubjectInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transgeneralledger.dao.Sett_TransGeneralLedgerDAO;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private static  Object lockObj = new Object();  //静态
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	
	
	/** and by qulaian
	 * Method updateDiscountBillSave.
	 * 
	 * @param info
	 * @return int 票据修改接口 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateDiscountBillSave(long discount)  throws RemoteException, IRollbackException
	{
		
		//TransRepaymentDiscountInfo info = null;
		long res=0;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		try
		{
			res = dao.updateDiscountBillSave(discount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return res;
	//return discount;
	}
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
	 * 贴现发放交易——检测交易是否被修改过
	 * 逻辑操作：
	 * （1）调用方法this.findDetailByID(),得到一个新的特殊交易实体类。
	 * （2）对比传入参数特殊交易实体类的dtModify和查询出的新的特殊交易实体类的dtModify，是否相同。
	 * @param info TransGrantDiscountInfo 特殊交易实体类
	 * @return boolean True,被修改；false,未被修改。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	private boolean grantIsTouch(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		try
		{
			TransGrantDiscountInfo tmpInfo = this.grantFindDetailByID(info.getID());
			String dateParam = info.getModifyDate().toString();
			String dateQuery = tmpInfo.getModifyDate().toString();

			Log.print("\n\n 传入ID为: " + info.getID() + "\n\n");
			Log.print("\n\n 从数据库查出来的ModifyDate为: " + dateQuery + "\n\n");
			Log.print("\n\n 传入的ModifyDate为: " + dateParam + "\n\n");
			if (dateParam.equals(dateQuery))
				return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return true;
	}

	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public long grantPreSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		//		//根据付款方账户号和付款金额判断是否透支 
		long lRtn = -1;
		return lRtn;
	}

	/**
	 * 贴现发放交易——暂存
	 * 逻辑操作：
	 * （1）如果lID不是-1，调用方法this.isTouched(),判断要暂存的记录是否被修改过。
	 *      调用方法Sett_TransGrantDiscountDAO.update()保存交易记录信息。         
	 * 		调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransGrantDiscountDAO.add()保存交易记录信息。
	 * 		调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * @param info TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 暂存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantTempSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			//修改暂存
			if (info.getID() != -1)
			{
				//校验状态是否匹配
				TransGrantDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getStatusID();
				long nStatusIDFromJsp = info.getStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.MODIFYTEMPSAVE);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				//判断要暂存的记录是否被修改过
				if (this.grantIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				
				//保存交易记录信息
				lRtn = dao.update(info);
				//更改记录的状态为未保存
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			}
			//新增暂存
			else if (info.getID() == -1)
			{
				lRtn = dao.add(info);
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}

	public long grantModifyTempSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransGrantDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getStatusID();
				long nStatusIDFromJsp = info.getStatusID();
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), nStatusIDFromDB, SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");

				//判断要暂存的记录是否被修改过
				if (this.grantIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				//保存交易记录信息
				lRtn = dao.update(info);
				//更改记录的状态为未保存
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			}
			else if (info.getID() == -1)
			{
				lRtn = dao.add(info);
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}

	/**
	 * 贴现发放交易——保存
	 * 逻辑操作：
	 * （1）判断参数TransGrantDiscountInfo交易实体类中的交易编号是否为空。
	 *      如果是空，说明是新增保存：
	 * 			调用方法：UtilOperation.getNewTransactionNo()得到一个交易号，并将其写入TransGrantDiscountInfo 。
	 *      如果非空，说明是修改保存:
	 *          调用方法：this.isTouch,判断要暂存的记录是否被修改过。
	 *          调用方法：this.FindDetailByID(),得到原来的贴现发放交易实体类TransGrantDiscountInfo。
	 * 			调用方法：AccountBookOperation.deleteGrantDiscount()。回滚原来的财务处理。注意参数是原来
	 * 						的实体TransGrantDiscountInfo。
	 * （2）判断ID是否为－1，若是，则调用方法为：Sett_TransGrantDiscountDAO.add() 。
	 *                       不是，则调用方法为：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 
	 * （3）调用方法：AccountBookOperation.saveGrantDiscount()。进行财务处理。
	 * （4）调用方法：Sett_TransGrantDiscountDAO.updateStatus()。 修改交易的状态为保存。
	 * @param info TransGrantDiscountInfo 贴现贷款发放交易实体类
	 * @return long 保存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public long grantSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		if (info == null)
		{
			return lRtn;
		}
		//加锁时使用
		long sessionID = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try
		{
			AccountBookOperation accbkOprn = new AccountBookOperation();
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getID());
			//判断交易号是否为空
			String strTransNo = info.getTransNo();
			if (strTransNo == null || strTransNo.equals("")) //新增保存
			{
				String strNewTransNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				info.setTransNo(strNewTransNo);
				
				//修改loan_DiscountCredence 中的记录状态
				if(info.isPayForm()){
					dao.updateLoanPayFormDiscountStatus(info.getDiscountNoteID(), 1);

				}else
					dao.updateLoanCredenceDiscountStatus(info.getDiscountNoteID(), 1);
			}
			else // 修改保存
			{
				TransGrantDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getStatusID();
				long nStatusIDFromJsp = info.getStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.MODIFYSAVE);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");
				//查看记录是否被修改过.若被修改过，直接抛出异常
				if (this.grantIsTouch(info))
				{
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				}
				
				//获取原来的交易实体
				TransGrantDiscountInfo oldInfo = this.grantFindDetailByID(info.getID());
				//回滚原来的财务处理				
				accbkOprn.deleteGrantDiscount(oldInfo);
			}
			
			//判断ID，作出不同的处理
			if (info.getID() == -1)
			{
				lRtn = dao.add(info);
			}
			else
			{
				lRtn = dao.update(info);
			}
			
			//修改交易的状态为保存
			lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);

			//Added by zwsun , 2007-06-20, 审批流保存
			if(info.getInutParameterInfo()!=null)
			{
				//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+lRtn);
				tempInfo.setTransID(info.getTransNo());//这里保存的是交易编号
				tempInfo.setDataEntity(info);
				
				//提交审批
				FSWorkflowManager.initApproval(info.getInutParameterInfo());
				//更新状态到审批中
				dao.updateStatus(info.getID(),SETTConstant.TransactionStatus.APPROVALING);
			
			}			
			
			//进行财务处理
			accbkOprn.saveGrantDiscount(info);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1)
				{
					utilOperation.releaseLock(info.getID(), sessionID);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
		}
		return lRtn;
		}
	}
	/**
	 * 贴现发放交易——删除
	 * 逻辑说明：
	 * （1）调用方法this.isTouched,判断要删除的记录是否被修改过。
	 * （2）判断参数TransGrantDiscountInfo 中的交易实体类的状态，
	 *      如果是暂存：
	 * 	 		调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 *      如果是保存：
	 * 			调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来
	 * 						的实体TransGrantDiscountInfo.
	 * 			调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 * @param info TransGrantDiscountInfo 贴现发放交易实体类
	 * @return boolean；true，删除成功；false,失败。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public long grantDelete(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		//加锁时使用
		long sessionID = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getID());
			
			TransGrantDiscountInfo infoFromDB = dao.findByID(info.getID());
			long nStatusIDFromDB = infoFromDB.getStatusID();
			long nStatusIDFromJsp = info.getStatusID();
			String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(this.mySessionCtx, errMsg);
			}
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
				throw new IRollbackException(this.mySessionCtx, "Sett_E018");
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
				throw new IRollbackException(this.mySessionCtx, "Sett_E019");
			//检查是否修改。
			if (this.grantIsTouch(info))
			{
				throw new IRollbackException(this.mySessionCtx, "Sett_E131");
			}
			//如果原来是保存状态,则修改loan_DiscountCredence 中的记录状态
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
			{
				if(info.isPayForm()){
					dao.updateLoanPayFormDiscountStatus(info.getDiscountNoteID(), 2);
				}else
					dao.updateLoanCredenceDiscountStatus(info.getDiscountNoteID(), 2);
			}

			//执行删除操作
			lRtn = dao.delete(info.getID(), info.getInputUserID(), info.getAbstract(), info.getCheckAbstract());
			//进行财务处理
			AccountBookOperation accbkOprn = new AccountBookOperation();
			accbkOprn.deleteGrantDiscount(info);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1)
				{
					utilOperation.releaseLock(info.getID(), sessionID);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
		}

		return lRtn;
		}
	}
	/**
	 * 审批流：审批方法(自营/委贷)
	 * Added by zwsun, 2007-06-21
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransGrantDiscountInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getID();
		try
		{
			TransGrantDiscountInfo loanInfo = new TransGrantDiscountInfo();
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
					TransGrantDiscountInfo loanInfo1 = new TransGrantDiscountInfo();
					loanInfo1=dao.findByID(info.getID());
					//构造check参数
					//TransFixedOpenInfo depositInfo = new TransFixedOpenInfo();
					//depositInfo = this.openFindByID(info.getID());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setAbstract("机核");
					loanInfo1.setCheckUserID(returnInfo.getUserID());	//最后审批人为复核人				
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					grantCheck(loanInfo1);
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
	}	
	/**
	 * 审批流：取消审批方法（自营/委贷）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransGrantDiscountInfo loanInfo)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransGrantDiscountDAO loanDao = new Sett_TransGrantDiscountDAO();		
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				loanInfo.setCheckUserID(-1); //取消复核复核人清空
				this.grantCancelCheck(loanInfo);
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
	}		

	/**
	 * 贴现发放交易——复核
	 * 逻辑操作：
	 * （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常"您要复核的单据已被修改，请检查！”。"
	 * （2）调用方法：AccountBookOperation.checkGrantDiscount()。进行复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。
	 * @param info TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantCheck(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransGrantDiscountInfo infoFromDB = dao.findByID(info.getID());
				info.setPayForm(infoFromDB.isPayForm());//add by dwj 添加是否是通知单的信息
				long nStatusIDFromDB = infoFromDB.getStatusID();
				long nStatusIDFromJsp = info.getStatusID();

				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				//如果是暂存,则不允许复核
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.TEMPSAVE)
					throw new IRollbackException(
						this.mySessionCtx,
						"Sett_E133");
				else if (
					nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(
						this.mySessionCtx,
						"Sett_E021");
				else if (
					nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(
						this.mySessionCtx,
						"Sett_E022");

				//判断要复核的记录是否被修改过
				if (this.grantIsTouch(info))
				{
					throw new IRollbackException(this.mySessionCtx, "Sett_E133");
				}

				//设置交易状态
				//Added by zwsun , 2007/6/28, 匹配审批流
				long status = FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
						info.getCurrencyID(),
						Constant.ModuleType.SETTLEMENT,
						info.getTransactionTypeID(),
						-1));
				if (info.getStatusID() == status)
				{
					lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK, info.getCheckUserID(), info.getAbstract(), info.getCheckAbstract());
					AccountBookOperation accbkOprn = new AccountBookOperation();
					//进行财务处理
					accbkOprn.checkGrantDiscount(info);				
				
//					//是否有银企接口
//					boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
//					//是否需要生成银行指令
//					boolean bCreateInstruction = false;
//					long bankID = info.getBankID();
//					try {
//						//调用此方法后，bankID的值变为银行类型ID
//						bCreateInstruction = accbkOprn.isCreateInstruction(bankID);
//					} catch (Exception e1) {				
//						e1.printStackTrace();
//					}
//					
//					try
//					{
//						if(bIsValid) {
//							try {
//								//构造参数
//								CreateInstructionParam instructionParam = new CreateInstructionParam();
//								instructionParam.setTransactionTypeID(info.getTransactionTypeID());
//								instructionParam.setObjInfo(info);
//								instructionParam.setOfficeID(info.getOfficeID());
//								instructionParam.setCurrencyID(info.getCurrencyID());
//								instructionParam.setCheckUserID(info.getCheckUserID());
//								instructionParam.setBankType(bankID);
//								instructionParam.setInputUserID(info.getInputUserID());
//								
//								//生成银行指令并保存
//								IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
//								bankInstruction.createBankInstruction(instructionParam);
//								
//								
//							} catch (Throwable e) {
//								e.printStackTrace();
//								throw new IRollbackException(mySessionCtx, "生成贴现发放交易指令失败："+e.getMessage());
//							}
//						}
//						else {
//							Log.print("没有银行接口或不需要生成银行指令！");
//						}
//					}
//					catch (Exception e)
//					{
//						throw new IRollbackException(this.mySessionCtx, "保存银行转账指令出错！" + e.getMessage());
//					}
				
				}else{
					throw new Exception("业务已复核");
				}
			}else{
				throw new Exception("该笔交易不存在");
			}			
		}
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
////			throw e;
//			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
//			
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}

		return lRtn;
		}
	}

	/**
	 * 贴现发放交易——取消复核
	 * 逻辑操作：
	 * （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常" 您要取消复核的单据已被修改，请检查！"。
	 * （2）调用方法：AccountBookOperation.cancelCheckGrantDiscount()。进行取消复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为保存。
	 * @param info TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 取消复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public long grantCancelCheck(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransGrantDiscountInfo infoFromDB=dao.findByID(info.getID());
				long nStatusIDFromDB=infoFromDB.getStatusID();
				long nStatusIDFromJsp=info.getStatusID();				
				String errMsg =UtilOperation.checkStatus(nStatusIDFromJsp,nStatusIDFromDB,SETTConstant.Actions.CANCELCHECK);			
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx,errMsg);
				}
				//如果是暂存,则不允许复核
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.TEMPSAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E024");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E023");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E025");
				//判断要暂存的记录是否被修改过
				if (this.grantIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E133");

				if (info.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
//					long lCheckUID=info.getCheckUserID();
					//long lCheckUID=-1; 
					
					//Modified by zwsun ,8/6,取消复核判断是否关联审批流
					InutParameterInfo pInfo=new InutParameterInfo();
					pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					pInfo.setOfficeID(info.getOfficeID());
					pInfo.setCurrencyID(info.getCurrencyID());
					pInfo.setTransTypeID(info.getTransactionTypeID());
					pInfo.setActionID(-1);
					if(FSWorkflowManager.isNeedApproval(pInfo)){
						lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.APPROVALED,info.getCheckUserID() , info.getAbstract(), info.getCheckAbstract());
					
					}else{
						lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE,info.getCheckUserID() , info.getAbstract(), info.getCheckAbstract());
										
					}
					
				}
				
				//进行财务处理
				AccountBookOperation accbkOprn = new AccountBookOperation();
				accbkOprn.cancelCheckGrantDiscount(info);
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//		//	throw e;
//			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}
	/**
	 * 链接查找(按状态查询)
	 * 逻辑操作：
	 *    调用方法：Sett_TransGrantDiscountDAO.findByStatus()方法。
	 * @param info QueryByStatusConditionInfo 按状态查询条件实体类
	 * @return Vector 包含特殊交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public Collection grantFindByConditions(QueryConditionInfo info) throws RemoteException, IRollbackException
	{
		Collection cln = null;
		try
		{
			Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
			cln = dao.findByCondition(info);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}

		return cln;
	}

	/**
	 * 贴现发放交易——复核匹配
	 * 逻辑操作：
	 * 		调用方法：Sett_TransGrantDiscountDAO.match()方法。
	 * @param info TransGrantDiscountInfo 贴现发放交易实体类（条件）
	 * @return Collection 包含贴现发放交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	*/

	public TransGrantDiscountInfo grantMatch(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		TransGrantDiscountInfo tgdi = null;
		try
		{
			//Added by zwsun , 2007/6/28, 匹配审批流
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));
			Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
			tgdi = dao.match(info);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return tgdi;
	}

	/**
	 * 根据贴现收回交易号查找贴现收回交易ID
	 */

	public long repaymentGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		try
		{
			return dao.getIDByTransNo(strTransNo);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
	}

	/**
	 * 根据贴现发放交易号查找贴现发放交易ID
	 */

	public long grantGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			return dao.getIDByTransNo(strTransNo);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
	}

	/**
	 * 根据贴现发放交易ID，得到贴现发放交易详细信息
	 * 逻辑操作：
	 * 		调用方法：Sett_TransGrantDiscountDAO.findByID()方法。
	 * @param lTransID 贴现发放交易ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public TransGrantDiscountInfo grantFindDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		TransGrantDiscountInfo tgdi = null;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			tgdi = dao.findByID(lTransID);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return tgdi;

	}
	/**
	 * 根据贴现凭证ID，得到放款的信息
	 * 逻辑操作：
	 * 		调用方法：(信贷的方法，目前用SQL先实现。)
	 * @param lDiscountNoteID 贴现凭证ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantDiscountInfo grantFindGrantDetailByNoteID(long lDiscountNoteID) throws RemoteException, IRollbackException
	{
		TransGrantDiscountInfo tgdi = null;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			tgdi = dao.findInfoByDiscountNoteID(lDiscountNoteID);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return tgdi;
	}
	
	/**
	 * 根据转贴现凭证ID，得到放款的信息
	 * 逻辑操作：
	 * 		调用方法：(信贷的方法，目前用SQL先实现。)
	 * @param lDiscountNoteID 贴现凭证ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransDiscountDetailInfo findTransDiscountByNoteID(long transDiscountNoteID) throws RemoteException, IRollbackException
	{
		TransDiscountDetailInfo tgdi = null;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			tgdi = dao.findInfoByTransDiscountNoteID(transDiscountNoteID);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return tgdi;
	}

	/******************贴现收回方法*********************/
	/**
			 * Method preSave.
			 * 
			 * @param info
			 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
			 * @throws RemoteException
			 * @throws IRollbackException
			 */
	public long repaymentPreSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		long nRtn = -1;
		return nRtn;
	}

	/**
	 * 贴现发放交易——暂存 逻辑操作： （1）如果lID不是-1，调用方法this.isTouched(),判断要暂存的记录是否被修改过。
	 * 调用方法Sett_TransGrantDiscountDAO.update()保存交易记录信息。 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransGrantDiscountDAO.add()保存交易记录信息。
	 * 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 暂存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentTempSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Log.print("\n\n repaymentTempSave \n\n");
		long lRtn = -1;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		try
		{
			//修改暂存
			if (info.getID() != -1)
			{
				TransRepaymentDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getNStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.MODIFYTEMPSAVE);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");

				//判断要暂存的记录是否被修改过
				if (this.repaymentIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				//保存交易记录信息
				lRtn = dao.update(info);
				//更改记录的状态为未保存
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			}
			//新增暂存
			else if (info.getID() == -1)
			{
				lRtn = dao.add(info);
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}

	public long repaymentModifyTempSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Log.print("\n\n EJB repaymentModifyTempSave 开始 \n\n");
		long lRtn = -1;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransRepaymentDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getNStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(info.getNStatusID(), nStatusIDFromDB, SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");

				//判断要暂存的记录是否被修改过
				if (this.repaymentIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				//保存交易记录信息
				lRtn = dao.update(info);
				//更改记录的状态为未保存
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			}
			else if (info.getID() == -1)
			{
				lRtn = dao.add(info);
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		Log.print("\n\n EJB repaymentModifyTempSave 结束 \n\n");
		return lRtn;
		}

	}

	/**
	 * 贴现发放交易——保存 逻辑操作： （1）判断参数TransGrantDiscountInfo交易实体类中的交易编号是否为空。 如果是空，说明是新增保存：
	 * 调用方法：UtilOperation.getNewTransactionNo()得到一个交易号，并将其写入TransGrantDiscountInfo 。 如果非空，说明是修改保存:
	 * 调用方法：this.isTouch,判断要暂存的记录是否被修改过。 调用方法：this.FindDetailByID(),得到原来的贴现发放交易实体类TransGrantDiscountInfo。
	 * 调用方法：AccountBookOperation.deleteGrantDiscount()。回滚原来的财务处理。注意参数是原来 的实体TransGrantDiscountInfo。
	 * （2）判断ID是否为－1，若是，则调用方法为：Sett_TransGrantDiscountDAO.add() 。 不是，则调用方法为：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 
	 * （3）调用方法：AccountBookOperation.saveGrantDiscount()。进行财务处理。 （4）调用方法：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 修改交易的状态为保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现贷款发放交易实体类
	 * @return long 保存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		if (info == null)
		{
			return lRtn;
		}
		//加锁时使用
		long sessionID = -1;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try
		{
			AccountBookOperation accbkOprn = new AccountBookOperation();
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getID());
			//判断交易号是否为空
			String strTransNo = info.getSTransNo();
			Log.print("\n\n 传入的交易号为 strTransNo=" + strTransNo + "\n\n");
			if (strTransNo == null || strTransNo.equals("")) //新增保存
			{
				String strNewTransNo = utilOperation.getNewTransactionNo(info.getNOfficeID(), info.getNCurrencyID(),info.getNTransactionTypeID());
				info.setSTransNo(strNewTransNo);
			}
			else // 修改保存
				{
				TransRepaymentDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getNStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.MODIFYSAVE);
				Log.print("\n\n nStatusIDFromDB" + nStatusIDFromDB + "\n\n");
				Log.print("\n\n nStatusIDFromJsp" + nStatusIDFromJsp + "\n\n");
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");

				//查看记录是否被修改过.若被修改过，直接抛出异常
				if (this.repaymentIsTouch(info))
				{
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				}
				//获取原来的交易实体
				TransRepaymentDiscountInfo oldInfo = this.repaymentFindDetailByID(info.getID());

				//回滚原来的财务处理				
				accbkOprn.deleteRepaymentDiscount(oldInfo);

			}
			//判断ID，作出不同的处理
			if (info.getID() == -1)
			{
				lRtn = dao.add(info);
			}
			else
			{
				lRtn = dao.update(info);
			}

			//进行财务处理
			accbkOprn.saveRepaymentDiscount(info);

			//修改交易的状态为保存
			lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		finally
		{
			//解锁
			try
			{
				if (sessionID != -1)
				{
					utilOperation.releaseLock(info.getID(), sessionID);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
		}
		return lRtn;
		}
	}
	/**
	 * 贴现发放交易——删除 逻辑说明： （1）调用方法this.isTouched,判断要删除的记录是否被修改过。 （2）判断参数TransGrantDiscountInfo 中的交易实体类的状态， 如果是暂存：
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。 如果是保存：
	 * 调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来 的实体TransGrantDiscountInfo.
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return boolean；true，删除成功；false,失败。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentDelete(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		//加锁时使用
		long sessionID = -1;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try
		{
			TransRepaymentDiscountInfo infoFromDB = dao.findByID(info.getID());
			long nStatusIDFromDB = infoFromDB.getNStatusID();
			long nStatusIDFromJsp = info.getNStatusID();
			String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(this.mySessionCtx, errMsg);
			}
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
				throw new IRollbackException(this.mySessionCtx, "Sett_E018");
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getID());
			if (this.repaymentIsTouch(info))
			{
				throw new IRollbackException(this.mySessionCtx, "Sett_E131");
			}
			/*	
			//如果原来是保存状态,则修改loan_DiscountCredence 中的记录状态
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
			{
				Sett_TransGrantDiscountDAO dao2 = new Sett_TransGrantDiscountDAO();
				if (!dao2.updateLoanCredenceDiscountStatus(info.getNDiscountNoteID(), 2))
					return -2;
			}
			*/

			//执行删除操作
			lRtn = dao.delete(info.getID(), info.getNInputUserID(), info.getSAbstract(), info.getSCheckAbstract());
			//进行财务处理
			AccountBookOperation accbkOprn = new AccountBookOperation();
			if(info.getNStatusID()!=SETTConstant.TransactionStatus.TEMPSAVE && info.getNDiscountAccountID()>0 &&  info.getNDiscountNoteID()>0){
				accbkOprn.deleteRepaymentDiscount(info);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1)
				{
					utilOperation.releaseLock(info.getID(), sessionID);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
		}

		return lRtn;
		}
	}

	/**
	 * 贴现发放交易——复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常"您要复核的单据已被修改，请检查！”。"
	 * （2）调用方法：AccountBookOperation.checkGrantDiscount()。进行复核的财务处理。 （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCheck(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransRepaymentDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getNStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				//如果是暂存,则不允许复核
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.TEMPSAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E133");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E021");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E022");

				//判断要暂存的记录是否被修改过
				if (this.repaymentIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E133");

				//设置交易状态
				if (info.getNStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
					lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK, info.getNCheckUserID(), info.getSAbstract(), info.getSCheckAbstract());

				}
				//进行财务处理
				AccountBookOperation accbkOprn = new AccountBookOperation();
				accbkOprn.checkRepaymentDiscount(info);
				//更新累计还款余额
				lRtn = dao.updateSumReceiveAmount(info.getID(), info.getMAmount(), info.getNSubAccountID());
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = false;
				long bankID = info.getNBankID();
				try {
					//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = accbkOprn.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					e1.printStackTrace();
				}
				
				try
				{
					if(bIsValid && bCreateInstruction) {
						try {
							//构造参数
							CreateInstructionParam instructionParam = new CreateInstructionParam();
							instructionParam.setTransactionTypeID(info.getNTransactionTypeID());
							instructionParam.setObjInfo(info);
							instructionParam.setOfficeID(info.getNOfficeID());
							instructionParam.setCurrencyID(info.getNCurrencyID());
							instructionParam.setCheckUserID(info.getNCheckUserID());
							instructionParam.setBankType(bankID);
							instructionParam.setInputUserID(info.getNInputUserID());
							
							//生成银行指令并保存
							IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
							bankInstruction.createBankInstruction(instructionParam);
							
							
						} catch (Throwable e) {
							e.printStackTrace();
							throw new IRollbackException(this.getSessionContext(), "生成贴现收回交易指令失败："+e.getMessage());
						}
					}
				}		
					
				
				catch (Exception e)
				{
					throw new IRollbackException(this.getSessionContext(), "保存银行转账指令出错！" + e.getMessage());
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}

	/**
	 * 贴现发放交易——取消复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常" 您要取消复核的单据已被修改，请检查！"。
	 * （2）调用方法：AccountBookOperation.cancelCheckGrantDiscount()。进行取消复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为保存。
	 * 
	 * @param info  TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 取消复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCancelCheck(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransRepaymentDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getNStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.CANCELCHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}

				//如果是暂存,则不允许复核
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.TEMPSAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E024");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E023");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E025");

				//判断要暂存的记录是否被修改过
				if (this.repaymentIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E133");

				if (info.getNStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE, info.getNCheckUserID(), info.getSAbstract(), info.getSCheckAbstract());
				}
			}
			//进行财务处理
			AccountBookOperation accbkOprn = new AccountBookOperation();
			accbkOprn.cancelCheckRepaymentDiscount(info);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}
	/**
	 * 链接查找(按状态查询) 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.findByStatus()方法。
	 * 
	 * @param info
	 *            QueryByStatusConditionInfo 按状态查询条件实体类
	 * @return Vector 包含特殊交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection repaymentFindByConditions(QueryConditionInfo info) throws RemoteException, IRollbackException
	{
		Collection cln = null;
		try
		{
			Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
			cln = dao.findByCondition(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}

		return cln;
	}

	/**
	 * 贴现发放交易——复核匹配 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.match()方法。
	 * 
	 * @param info
	 *            TransRepaymentDiscountInfo 贴现发放交易实体类（条件）
	 * @param bCheckOverdue 是否退款
	 * @return Collection 包含贴现发放交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentDiscountInfo repaymentMatch(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		TransRepaymentDiscountInfo tgdi = null;
		try
		{
			Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
			tgdi = dao.match(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return tgdi;
	}

	/**
	 * 根据贴现发放交易ID，得到贴现发放交易详细信息 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.findByID()方法。
	 * 
	 * @param lTransID
	 *            贴现发放交易ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentDiscountInfo repaymentFindDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		TransRepaymentDiscountInfo info = null;
		Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
		try
		{
			info = dao.findByID(lTransID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	private boolean repaymentIsTouch(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		try
		{
			TransRepaymentDiscountInfo tmpInfo = this.repaymentFindDetailByID(info.getID());
			String dateParam = info.getDtModify().toString();
			String dateQuery = tmpInfo.getDtModify().toString();

			Log.print("\n\n 传入ID为: " + info.getID() + "\n\n");
			Log.print("\n\n 从数据库查出来的ModifyDate为: " + dateQuery + "\n\n");
			Log.print("\n\n 传入的ModifyDate为: " + dateParam + "\n\n");
			if (dateParam.equals(dateQuery))
				return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return true;
	}

	//由于打印方面的需要,增加一个方法
	public DiscountCredenceInfo findDiscountCredenceByID(long lDiscountCredenceID) throws RemoteException, IRollbackException
	{
		Log.print("\n\n 进入EJB \n\n");
		DiscountCredenceInfo info = null;
		try
		{
			Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
			info = dao.findDiscountCredenceByID(lDiscountCredenceID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		Log.print("\n\n info=" + info + "---- \n\n");
		Log.print("\n\n 调用EJB结束 \n\n");
		return info;
	}
   
//	/贴现票据查询  
	public Collection findDiscountBillInfo(            
            long lPageLineCount,                                           
            long lPageNo,                                               
            long lOrderParam,                                           
            long   lDesc,
            long   nContractId,   //票据id 
            long             nContractIdFrom,       // 合同From
                    long             nContractIdTo,         // 合同to
                    String           sCode,               //汇票号码
                   double           mAmountFrom,           //汇票金额from
                   double           mAmountTo,            //汇票金额to
                    Timestamp        dtCheckDateFrom,    //审查日期From
                     Timestamp        dtCheckDateTo,    //审查日期To
                    long             nCheckStatus          //'审查状态';
    ) throws RemoteException,IException
			  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbNum = new StringBuffer();
		StringBuffer sbDetail = new StringBuffer();
	

		Vector v = new Vector();
		long lRecordCount = -1;      
		
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		double dTotalAmount = 0; //总票据金额
		double dTotalCheckAmount = 0; //总票据金额
		try
		{
			
			  con = Database.getConnection();
			
			  sbNum.append(" select count(*), sum(mAmount),sum(MCHECKAMOUNT) ");
			  sbNum.append(" from Loan_DiscountContractBill  where 1=1 ");
			  
			    if (nContractIdFrom !=-1 )
			    {
				  sbNum.append(" and  NCONTRACTID>="+nContractIdFrom);
			    }
			    if (nContractIdTo!=-1)
			    {
			    	sbNum.append(" and NCONTRACTID<="+nContractIdTo);
			    }
			    if (sCode != null && sCode.length() > 0)
			    {
			    	sbNum.append(" and SCODE ="+ sCode);
			    }
			    if (mAmountFrom!=0)
			    {
			    	sbNum.append(" and  mAmount>="+mAmountFrom);
			    }
			    if (mAmountTo!=0)
			    {
			    	sbNum.append(" and  mAmount<="+mAmountTo);
			    }
			    if (dtCheckDateFrom!=null)
			    {
			    	sbNum.append(" and  DtcheckDate>=? ");//+dtCheckDateFrom);
			    }
			    if (dtCheckDateTo!=null)
			    {
			    	sbNum.append(" and  DtcheckDate<=?");//+dtCheckDateTo);
			    }
			    if (nCheckStatus!=-1)
			    {
			    	sbNum.append(" and  NCHECKSTATUS ="+nCheckStatus);
			    }
				  
				
				    Log.print(sbNum.toString());
				 
				ps = con.prepareStatement(sbNum.toString());
				   int index=1;
				    
					if(dtCheckDateFrom!=null)
					{
						ps.setTimestamp(index,dtCheckDateFrom);
						index++;
					}
					if(dtCheckDateTo!=null)
					{
						ps.setTimestamp(index,dtCheckDateTo);
						index++;
					}
				rs = ps.executeQuery();

				if (rs != null && rs.next())
				{
					lRecordCount = rs.getLong(1);
					dTotalAmount =rs.getDouble(2);
					dTotalCheckAmount=rs.getDouble(3);
				}

				rs.close();
				rs = null;
				ps.close();
				ps = null;

				lPageCount = lRecordCount / lPageLineCount;

				if ((lRecordCount % lPageLineCount) != 0)
				{
					lPageCount++;
				}
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//返回需求的结果集
				lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
				lRowNumEnd = lRowNumStart + lPageLineCount - 1;
             
			
            sbDetail.append("SELECT * FROM (");
            sbDetail.append(" SELECT A.*,rownum r from (select ");
            sbDetail.append(" A.ID  ID, A.NCONTRACTID   NCONTRACTID, A.NSERIALNO  NSERIALNO,A.SUSERNAME  SUSERNAME, A.SBANK  SBANK, "  );               
            sbDetail.append("A.NISLOCAL   NISLOCAL, A.DTCREATE  DTCREATE,  A.DTEND   DTEND, A.SCODE     SCODE,  " );              
            sbDetail.append("A.MAMOUNT    MAMOUNT, A.NSTATUSID  NSTATUSID, A.NADDDAYS   NADDDAYS,A.NDISCOUNTCREDENCEID   NDISCOUNTCREDENCEID,");    
            sbDetail.append("A.OB_NDISCOUNTCREDENCEID   OB_NDISCOUNTCREDENCEID, A.NACCEPTPOTYPEID  NACCEPTPOTYPEID,  A.SFORMEROWNER   SFORMEROWNER,");           
            sbDetail.append("A.MCHECKAMOUNT    MCHECKAMOUNT, A.NBILLSOURCETYPEID   NBILLSOURCETYPEID,  A.NISCANSELL   NISCANSELL, " );           
            sbDetail.append("A.REPURCHASEDATE   REPURCHASEDATE,  A.REPURCHASETERM      REPURCHASETERM,");         
            sbDetail.append("A.NISABATEMENT    NISABATEMENT, A.NSELLSTATUSID    NSELLSTATUSID,   A.NCHECKSTATUS   NCHECKSTATUS," );          
            sbDetail.append("A.SCHECKCODE    SCHECKCODE,   A.DTCHECKDATE     DTCHECKDATE, A.NBILLSTATUSID   NBILLSTATUSID, A.DTCANCELDATE  DTCANCELDATE, B.sContractCode  sContractCode    from    LOAN_DISCOUNTCONTRACTBILL   A,  loan_ContractForm  B   WHERE   A.NCONTRACTID=B.ID  ");
		   		    
		    if (nContractIdFrom !=-1 )
		    {
		    	sbDetail.append(" and  NCONTRACTID>="+nContractIdFrom);
		    }
		    if (nContractIdTo!=-1)
		    {
		    	sbDetail.append(" and NCONTRACTID<="+nContractIdTo);
		    }
		    if (sCode != null && sCode.length() > 0)
		    {
		    	sbDetail.append(" and SCODE ="+ sCode);
		    }
		    if (mAmountFrom!=0)
		    {
		    	sbDetail.append(" and  mAmount>="+mAmountFrom);
		    }
		    if (mAmountTo!=0)
		    {
		    	sbDetail.append(" and  mAmount<="+mAmountTo);
		    }
		    if (dtCheckDateFrom!=null)
		    {
		    	sbDetail.append(" and  DtcheckDate>=? ");//+dtCheckDateFrom);
		    }
		    if (dtCheckDateTo!=null)
		    {
		    	sbDetail.append(" and  DtcheckDate<=? ");//+dtCheckDateTo);
		    }
		    if (nCheckStatus!=-1)
		    {
		    	sbDetail.append(" and  NCHECKSTATUS ="+nCheckStatus);
		    }
		  
			switch ((int) lOrderParam)
			{
				case 1 :
					sbDetail.append(" order by nSerialNo ");
					break;
				case 2 :
					sbDetail.append(" order by sUserName ");
					break;
				case 3 :
					sbDetail.append(" order by sBank ");
					break;
				case 4 :
					sbDetail.append(" order by dtCreate ");
					break;
				case 5 :
					sbDetail.append(" order by  dtEnd ");
					break;
				case 6 :
					sbDetail.append(" order by  sCode ");
					break;
				case 7 :
					sbDetail.append(" order by  mAmount ");
					break;
				case 8 :
					 sbDetail.append(" order by  nAcceptPOTypeID ");
					break;
				default :
					sbDetail.append(" order by   ID ");
			}

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				 sbDetail.append(" desc") ;
			}
			
			  sbDetail.append(" ) A )  WHERE   r between "+lRowNumStart+" and "+lRowNumEnd+"");
			Log.print(sbDetail.toString());
			ps = con.prepareStatement(sbDetail.toString());

			   index=1;
			    
				if(dtCheckDateFrom!=null)
				{
					ps.setTimestamp(index,dtCheckDateFrom);
					index++;
				}
				if(dtCheckDateTo!=null)
				{
					ps.setTimestamp(index,dtCheckDateTo);
					index++;
				}  
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();

				dbill.setDiscountContractID(rs.getLong("NCONTRACTID")); //贴现标示
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("r")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
				dbill.setRealAmount(rs.getDouble("MCHECKAMOUNT")); //汇票实付金额
				
				 dbill.setNcheckStatus(rs.getLong("NcheckStatus"));//审查状态
				 dbill.setScheckID(rs.getString("ScheckCODE"));//复查编号
				 dbill.setDtcheckDate(rs.getTimestamp("DtcheckDate"));//审查日期
				  
				dbill.setDiscountContractCode(rs.getString("sContractCode"));
				
				dbill.setNbillStatusId(rs.getLong("NBILLSTATUSID")); //票据状态-haier
				dbill.setDcancelDate(rs.getTimestamp("DTCANCELDATE"));//销账日期-haier
				
				
				dbill.setCount(lRecordCount);
				dbill.setTotalAmount(dTotalAmount);
				dbill.setTotalRealAmount(dTotalCheckAmount);
				v.add(dbill);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;	
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				//log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return (v.size() > 0 ? v : null);
		}
	
// /贴现票据状态修改
	public Collection saveDiscountBillInfo(long recordId, // 记录编号
			long lPageLineCount, long lPageNo, Timestamp nCheckDate, // 审查日期
			String nCheckId, // '查复编号';
			long nBillStatus // 票据状态';
	) throws RemoteException, IException, IRollbackException {
	synchronized(lockObj){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		Vector v = new Vector();

		try {
			Log.print("============= saveCheck() begin ============");
			conn = Database.getConnection();
			strSQL = " update  LOAN_DISCOUNTCONTRACTBILL  set NBILLSTATUSID=? where ID=? ";
			Log.print(strSQL);

			ps = conn.prepareStatement(strSQL);

			ps.setLong(1, nBillStatus);
			ps.setLong(2, recordId);

			ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			Log.print("============= saveCheck() end ============");
		} catch (Exception ex) {
			//modified by mzh_fu 2007/05/011
//			ex.printStackTrace();
//			throw new IException("Gen_E001");
			throw new IRollbackException(
					mySessionCtx,
					"Gen_E001",
					ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				//modified by mzh_fu 2007/05/011
//				ex.printStackTrace();
//				throw new IException("Gen_E001");
				throw new IRollbackException(
						mySessionCtx,
						"Gen_E001",
						ex);
			}
		}

		return (v.size() > 0 ? v : null);
	}
	}
// /贴现票据查询
	public Collection findDiscountBillStatusInfo(            
            long lPageLineCount,                                           
            long lPageNo,                                               
            long lOrderParam,                                           
            long   lDesc,
            long   nContractId,   //票据id 
            long             nContractIdFrom,       // 合同From
            long             nContractIdTo,         // 合同to
            String           sCode,               //汇票号码
            double           mAmountFrom,           //汇票金额from
            double           mAmountTo,            //汇票金额to
            Timestamp        dtCheckDateFrom,    //审查日期From
            Timestamp        dtCheckDateTo,    //审查日期To
            long             nCheckStatus,          //'审查状态';	
			
            long             nBillStatusId,       //票据状态-
			Timestamp         dCancelDateFrom,      //销账日期From-
			Timestamp         dCancelDateTo ,     //销账日期To-
			
			String             checkcodestr,       //复查编号
            Timestamp          dtendFrom,          //票据到期日期 from
            Timestamp           dtendTo            //票据到期日期 to
			
    ) throws RemoteException,IException
			  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbNum = new StringBuffer();
		StringBuffer sbDetail = new StringBuffer();
	

		Vector v = new Vector();
		long lRecordCount = -1;      
		
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		double dTotalAmount = 0; //总票据金额
		double dTotalCheckAmount = 0; //总票据金额
		try
		{
			
			  con = Database.getConnection();
			
			  sbNum.append(" select count(*), sum(mAmount),sum(MCHECKAMOUNT) ");
			  sbNum.append(" from Loan_DiscountContractBill  where 1=1 ");
			  
			    if (nContractIdFrom !=-1 )
			    {
				  sbNum.append(" and  NCONTRACTID>="+nContractIdFrom);
			    }
			    if (nContractIdTo!=-1)
			    {
			    	sbNum.append(" and NCONTRACTID<="+nContractIdTo);
			    }
			    if (sCode != null && sCode.length() > 0)
			    {
			    	sbNum.append(" and SCODE ="+ sCode);
			    }
			    if (mAmountFrom!=0)
			    {
			    	sbNum.append(" and  mAmount>="+mAmountFrom);
			    }
			    if (mAmountTo!=0)
			    {
			    	sbNum.append(" and  mAmount<="+mAmountTo);
			    }
			    if (dtCheckDateFrom!=null)
			    {
			    	sbNum.append(" and  DtcheckDate>=? ");//+dtCheckDateFrom);
			    }
			    if (dtCheckDateTo!=null)
			    {
			    	sbNum.append(" and  DtcheckDate<=?");//+dtCheckDateTo);
			    }
			    if (nCheckStatus!=-1)
			    {
			    	sbNum.append(" and  NCHECKSTATUS ="+nCheckStatus);
			    }
			    if (nBillStatusId!=-1)
			    {
			    	sbNum.append(" and  NBILLSTATUSID ="+nBillStatusId);
			    }
			    if (dCancelDateFrom!=null)
			    {
			    	sbNum.append(" and  DTCANCELDATE>=? ");//+dtCheckDateFrom);
			    }
			    if (dCancelDateTo!=null)
			    {
			    	sbNum.append(" and  DTCANCELDATE<=?");//+dtCheckDateTo);
			    }
			    if (checkcodestr != null && checkcodestr.length() > 0)
			    {
			    	sbNum.append(" and SCHECKCODE ='"+ checkcodestr+"'");
			    }
			    if (dtendFrom!=null)
			    {
			    	sbNum.append(" and  DTEND>=? ");//+dtCheckDateFrom);
			    }
			    if (dtendTo!=null)
			    {
			    	sbNum.append(" and  DTEND<=? ");//+dtCheckDateFrom);
			    }
			    
			    
				
				    Log.print(sbNum.toString());
				 
				ps = con.prepareStatement(sbNum.toString());
				   int index=1;
				    
					if(dtCheckDateFrom!=null)
					{
						ps.setTimestamp(index,dtCheckDateFrom);
						index++;
					}
					if(dtCheckDateTo!=null)
					{
						ps.setTimestamp(index,dtCheckDateTo);
						index++;
					}
					if(dCancelDateFrom!=null)
					{
						System.out.println("dCancelDateFrom=="+dCancelDateFrom);
						ps.setTimestamp(index,dCancelDateFrom);
						index++;
					}
					if(dCancelDateTo!=null)
					{ 
						System.out.println("dCancelDateTo=="+dCancelDateTo);
						ps.setTimestamp(index,dCancelDateTo);
						index++;
					}
					if(dtendFrom!=null)
					{
						System.out.println("dtendFrom=="+dtendTo);
						ps.setTimestamp(index,dtendFrom);
						index++;
					}
					if(dtendTo!=null)
					{
						System.out.println("dtendTo=="+dtendTo);
						ps.setTimestamp(index,dtendTo);
						index++;
					}
				rs = ps.executeQuery();

				if (rs != null && rs.next())
				{
					lRecordCount = rs.getLong(1);
					dTotalAmount =rs.getDouble(2);
					dTotalCheckAmount=rs.getDouble(3);
				}

				rs.close();
				rs = null;
				ps.close();
				ps = null;

				lPageCount = lRecordCount / lPageLineCount;

				if ((lRecordCount % lPageLineCount) != 0)
				{
					lPageCount++;
				}
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//返回需求的结果集
				lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
				lRowNumEnd = lRowNumStart + lPageLineCount - 1;
             
			
            sbDetail.append("SELECT * FROM (");
            sbDetail.append(" SELECT A.*,rownum r from (select ");
            sbDetail.append(" A.ID  ID, A.NCONTRACTID   NCONTRACTID, A.NSERIALNO  NSERIALNO,A.SUSERNAME  SUSERNAME, A.SBANK  SBANK, "  );               
            sbDetail.append("A.NISLOCAL   NISLOCAL, A.DTCREATE  DTCREATE,  A.DTEND   DTEND, A.SCODE     SCODE,  " );              
            sbDetail.append("A.MAMOUNT    MAMOUNT, A.NSTATUSID  NSTATUSID, A.NADDDAYS   NADDDAYS,A.NDISCOUNTCREDENCEID   NDISCOUNTCREDENCEID,");    
            sbDetail.append("A.OB_NDISCOUNTCREDENCEID   OB_NDISCOUNTCREDENCEID, A.NACCEPTPOTYPEID  NACCEPTPOTYPEID,  A.SFORMEROWNER   SFORMEROWNER,");           
            sbDetail.append("A.MCHECKAMOUNT    MCHECKAMOUNT, A.NBILLSOURCETYPEID   NBILLSOURCETYPEID,  A.NISCANSELL   NISCANSELL, " );           
            sbDetail.append("A.REPURCHASEDATE   REPURCHASEDATE,  A.REPURCHASETERM      REPURCHASETERM,");         
            sbDetail.append("A.NISABATEMENT    NISABATEMENT, A.NSELLSTATUSID    NSELLSTATUSID,   A.NCHECKSTATUS   NCHECKSTATUS," );          
            sbDetail.append("A.SCHECKCODE    SCHECKCODE,   A.DTCHECKDATE     DTCHECKDATE, A.NBILLSTATUSID  NBILLSTATUSID, A.DTCANCELDATE DTCANCELDATE, B.sContractCode  sContractCode    from    LOAN_DISCOUNTCONTRACTBILL   A,  loan_ContractForm  B   WHERE   A.NCONTRACTID=B.ID  ");
		   		    
		    if (nContractIdFrom !=-1 )  
		    {
		    	sbDetail.append(" and  NCONTRACTID>="+nContractIdFrom);
		    }
		    if (nContractIdTo!=-1)
		    {
		    	sbDetail.append(" and NCONTRACTID<="+nContractIdTo);
		    }
		    if (sCode != null && sCode.length() > 0)
		    {
		    	sbDetail.append(" and SCODE ="+ sCode);
		    }
		    if (mAmountFrom!=0)
		    {
		    	sbDetail.append(" and  mAmount>="+mAmountFrom);
		    }
		    if (mAmountTo!=0)
		    {
		    	sbDetail.append(" and  mAmount<="+mAmountTo);
		    }
		    if (dtCheckDateFrom!=null)
		    {
		    	sbDetail.append(" and  DtcheckDate>=? ");//+dtCheckDateFrom);
		    }
		    if (dtCheckDateTo!=null)
		    {
		    	sbDetail.append(" and  DtcheckDate<=? ");//+dtCheckDateTo);
		    }
		    if (nCheckStatus!=-1)
		    {
		    	sbDetail.append(" and  NCHECKSTATUS ="+nCheckStatus);
		    }
		    if (nBillStatusId!=-1)
		    {
		    	sbDetail.append(" and  NBILLSTATUSID ="+nBillStatusId);
		    }
		    
		    
		    if (dCancelDateFrom!=null)
		    {
		    	sbDetail.append(" and  DTCANCELDATE>=? ");//+dtCheckDateFrom);
		    }
		    if (dCancelDateTo!=null)
		    {
		    	sbDetail.append(" and  DTCANCELDATE<=? ");//+dtCheckDateTo);
		    }
		    
		    if (checkcodestr != null && checkcodestr.length() > 0)
		    {
		    	sbDetail.append(" and SCHECKCODE ='"+ checkcodestr+"'");
		    }
		    if (dtendFrom!=null)
		    {
		    	sbDetail.append(" and  DTEND>=? ");//+dtCheckDateFrom);
		    }
		    if (dtendTo!=null)
		    {
		    	sbDetail.append(" and  DTEND<=? ");//+dtCheckDateFrom);
		    }
		    
			switch ((int) lOrderParam)
			{
				case 1 :
					sbDetail.append(" order by nSerialNo ");
					break;
				case 2 :
					sbDetail.append(" order by sUserName ");
					break;
				case 3 :
					sbDetail.append(" order by sBank ");
					break;
				case 4 :
					sbDetail.append(" order by dtCreate ");
					break;
				case 5 :
					sbDetail.append(" order by  dtEnd ");
					break;
				case 6 :
					sbDetail.append(" order by  sCode ");
					break;
				case 7 :
					sbDetail.append(" order by  mAmount ");
					break;
				case 8 :
					 sbDetail.append(" order by  nAcceptPOTypeID ");
					break;
				default :
					sbDetail.append(" order by   ID ");
			}

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				 sbDetail.append(" desc") ;
			}
			
			  sbDetail.append(" ) A )  WHERE   r between "+lRowNumStart+" and "+lRowNumEnd+"");
			Log.print(sbDetail.toString());
			ps = con.prepareStatement(sbDetail.toString());

			   index=1;
			    
				if(dtCheckDateFrom!=null)
				{
					ps.setTimestamp(index,dtCheckDateFrom);
					index++;
				}
				if(dtCheckDateTo!=null)
				{
					ps.setTimestamp(index,dtCheckDateTo);
					index++;
				}  
				if(dCancelDateFrom!=null)
				{
					System.out.println("dCancelDateFrom2=="+dCancelDateFrom);
					ps.setTimestamp(index,dCancelDateFrom);
					index++;
				}
				if(dCancelDateTo!=null)
				{
					System.out.println("dCancelDateTo2=="+dCancelDateTo);
					ps.setTimestamp(index,dCancelDateTo);
					index++;
				}
				if(dtendFrom!=null)
				{
					System.out.println("dtendFrom=="+dtendFrom);
					ps.setTimestamp(index,dtendFrom);
					index++;
				}
				if(dtendTo!=null)
				{
					System.out.println("dtendTo=="+dtendTo);
					ps.setTimestamp(index,dtendTo);
					index++;
				}
			rs = ps.executeQuery();
            
			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();

				dbill.setDiscountContractID(rs.getLong("NCONTRACTID")); //贴现标示
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("r")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
				dbill.setRealAmount(rs.getDouble("MCHECKAMOUNT")); //汇票实付金额
				//dbill.setAccrual(rs.getDouble(""));//贴现利息
				 dbill.setNcheckStatus(rs.getLong("NcheckStatus"));//审查状态
				 dbill.setScheckID(rs.getString("ScheckCODE"));//复查编号
				 dbill.setDtcheckDate(rs.getTimestamp("DtcheckDate"));//审查日期
				  
				dbill.setDiscountContractCode(rs.getString("sContractCode"));
				
				dbill.setNbillStatusId(rs.getLong("NBILLSTATUSID")); //票据状态-haier
				dbill.setDcancelDate(rs.getTimestamp("DTCANCELDATE"));//销账日期-haier
				
				dbill.setCount(lRecordCount);
				dbill.setTotalAmount(dTotalAmount);
				dbill.setTotalRealAmount(dTotalCheckAmount);
				v.add(dbill);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;	
			
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
			ex.printStackTrace();
			//log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
		
				throw new IException("Gen_E001");
			}
		}
		return (v.size() > 0 ? v : null);
		}
	/**
	 * 转贴现发放交易——暂存
	 * 逻辑操作：
	 * （1）如果lID不是-1，调用方法this.isTouched(),判断要暂存的记录是否被修改过。
	 *      调用方法Sett_TransGrantDiscountDAO.update()保存交易记录信息。         
	 * 		调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransGrantDiscountDAO.add()保存交易记录信息。
	 * 		调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * @param info TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 暂存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantTempSave(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			//修改暂存
			if (info.getID() != -1)
			{
				//校验状态是否匹配
				TransGrantDiscountInfo infoFromDB = dao.findByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.MODIFYTEMPSAVE);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				//判断要暂存的记录是否被修改过
				if (this.grantIsTouch(info))
					throw new IRollbackException(this.mySessionCtx, "Sett_E130");
				
				//保存交易记录信息
				//lRtn = dao.update(info);
				//更改记录的状态为未保存
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			}
			//新增暂存
			else if (info.getID() == -1)
			{
				//lRtn = dao.add(info);
				lRtn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
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
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}
	/**
	 * 转贴现发放交易——保存
	 * 逻辑操作：
	 * （1）判断参数TransDiscountDetailInfo交易实体类中的交易编号是否为空。
	 *      如果是空，说明是新增保存：
	 * 			调用方法：UtilOperation.getNewTransactionNo()得到一个交易号，并将其写入TransGrantDiscountInfo 。
	 *      如果非空，说明是修改保存:
	 *          调用方法：this.isTouch,判断要暂存的记录是否被修改过。
	 *          调用方法：this.FindDetailByID(),得到原来的贴现发放交易实体类TransGrantDiscountInfo。
	 * 			调用方法：AccountBookOperation.deleteGrantDiscount()。回滚原来的财务处理。注意参数是原来
	 * 						的实体TransGrantDiscountInfo。
	 * （2）判断ID是否为－1，若是，则调用方法为：Sett_TransGrantDiscountDAO.add() 。
	 *                       不是，则调用方法为：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 
	 * （3）调用方法：AccountBookOperation.saveGrantDiscount()。进行财务处理。
	 * （4）调用方法：Sett_TransGrantDiscountDAO.updateStatus()。 修改交易的状态为保存。
	 * @param info TransGrantDiscountInfo 贴现贷款发放交易实体类
	 * @return long 保存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public long grantSave(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		if (info == null)
		{
			return lRtn;
		}
		//加锁时使用
		long sessionID = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try
		{
			AccountBookOperation accbkOprn = new AccountBookOperation();
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getID());
			//判断交易号是否为空
			String strTransNo = info.getSTransNo();
			if (strTransNo == null || strTransNo.equals("")) //新增保存
			{
				String strNewTransNo = utilOperation.getNewTransactionNo(info.getNOfficeID(), info.getNCurrencyID(),info.getNTransactionTypeID());
				info.setSTransNo(strNewTransNo);
				
				//修改loan_DiscountCredence 中的记录状态
				dao.updateLoanCredenceDiscountStatus(info.getNDiscountCredence(), 1);
			}
			else // 修改保存
			{
				TransDiscountDetailInfo infoFromDB = dao.findInfoByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getNStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.MODIFYSAVE);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(this.mySessionCtx, "Sett_E016");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E017");
				//查看记录是否被修改过.若被修改过，直接抛出异常
			//	if (this.grantIsTouch(info))
			//	{
			//		throw new IRollbackException(this.mySessionCtx, "Sett_E130");
			//	}				
			}
			
			//判断ID，作出不同的处理
			if (info.getID() == -1)
			{
				lRtn = dao.add(info);
			}
			else
			{
				lRtn = dao.update(info);
			}
			
			//修改交易的状态为保存
			lRtn = dao.updateTransStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);

			//Added by zwsun , 2007-06-20, 审批流保存
			if(info.getInutParameterInfo()!=null)
			{
				//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+lRtn);
				tempInfo.setTransID(info.getSTransNo());//这里保存的是交易编号
				tempInfo.setDataEntity(info);
				
				//提交审批
				FSWorkflowManager.initApproval(info.getInutParameterInfo());
				//更新状态到审批中
				dao.updateStatus(info.getID(),SETTConstant.TransactionStatus.APPROVALING);
			
			}			
			
			//进行财务处理
			//accbkOprn.saveGrantDiscount(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1)
				{
					utilOperation.releaseLock(info.getID(), sessionID);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
		}
		return lRtn;
		}
	}	
	/**
	 * 转贴现发放交易——检测交易是否被修改过
	 * 逻辑操作：
	 * （1）调用方法this.findDetailByID(),得到一个新的特殊交易实体类。
	 * （2）对比传入参数特殊交易实体类的dtModify和查询出的新的特殊交易实体类的dtModify，是否相同。
	 * @param info TransGrantDiscountInfo 特殊交易实体类
	 * @return boolean True,被修改；false,未被修改。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	private boolean grantIsTouch(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		try
		{
			TransDiscountDetailInfo tmpInfo = this.findTransDetailByID(info.getID());
			String dateParam = info.getDtModify().toString();
			String dateQuery = tmpInfo.getDtModify().toString();

			Log.print("\n\n 传入ID为: " + info.getID() + "\n\n");
			Log.print("\n\n 从数据库查出来的ModifyDate为: " + dateQuery + "\n\n");
			Log.print("\n\n 传入的ModifyDate为: " + dateParam + "\n\n");
			if (dateParam.equals(dateQuery))
				return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return true;
	}
	//转贴现科目号的保存
	public long grantSubjectSave(TransDiscountSubjectInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		if (info == null)
		{
			return lRtn;
		}
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{	
			//判断ID，作出不同的处理
			if (info.getID() == -1)
			{
				lRtn = dao.addSubject(info);
			}
			else
			{
			//	lRtn = dao.updateSubject(info);
			}
								
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
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
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
		}
		return lRtn;
		}
	}	
	/**
	 * 转贴现发放交易——复核匹配
	 * 逻辑操作：
	 * 		调用方法：Sett_TransGrantDiscountDAO.match()方法。
	 * @param info TransDiscountDetailInfo 转贴现发放交易实体类（条件）
	 * @return Collection 包含贴现发放交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	*/

	public TransDiscountDetailInfo grantDiscountMatch(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		TransDiscountDetailInfo tgdi = null;
		try
		{
			Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
			tgdi = dao.match(info);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return tgdi;
	}
	
	public Collection findSubjectInfo(TransDiscountSubjectInfo info)
	throws RemoteException, IRollbackException
    {
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
	try
	{
		return dao.findByConditions(info);
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
	 * 根据转贴现发放交易ID，得到转贴现发放交易详细信息
	 * 逻辑操作：
	 * 		调用方法：Sett_TransGrantDiscountDAO.findByID()方法。
	 * @param lTransID 转贴现发放交易ID
	 * @return TransGrantDiscountInfo 转贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public TransDiscountDetailInfo findTransDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		TransDiscountDetailInfo tgdi = null;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			tgdi = dao.findInfoByID(lTransID);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}
		return tgdi;

	}
	/**
	 * 转贴现发放交易——复核
	 * 逻辑操作：
	 * （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常"您要复核的单据已被修改，请检查！”。"
	 * （2）调用方法：AccountBookOperation.checkGrantDiscount()。进行复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。
	 * @param info TransDiscountDetailInfo 转贴现发放交易实体类
	 * @return long 复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantCheck(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransDiscountDetailInfo infoFromDB = dao.findInfoByID(info.getID());
				long nStatusIDFromDB = infoFromDB.getNStatusID();
				long nStatusIDFromJsp = info.getNStatusID();
				String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx, errMsg);
				}
				//如果是暂存,则不允许复核
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.TEMPSAVE)
					throw new IRollbackException(
						this.mySessionCtx,
						"Sett_E133");
				else if (
					nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
					throw new IRollbackException(
						this.mySessionCtx,
						"Sett_E021");
				else if (
					nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(
						this.mySessionCtx,
						"Sett_E022");

				//判断要复核的记录是否被修改过
			//	if (this.grantIsTouch(info))
			//	{
			//		throw new IRollbackException(this.mySessionCtx, "Sett_E133");
			//	}

				if (info.getNStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
					lRtn = dao.updateTransStatus(info.getID(), SETTConstant.TransactionStatus.CHECK, info.getNCheckUserID(), info.getSAbstract(), info.getSConfirmAbstract());
					AccountBookOperation accbkOprn = new AccountBookOperation();
					
					//进行财务处理
					accbkOprn.checkTransDiscount(info);				
				}else{
					throw new Exception("业务已复核");
				}
			}else{
				throw new Exception("该笔交易不存在");
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}

		return lRtn;
		}
	}
	/**
	 * 链接查找(按状态查询)
	 * 逻辑操作：
	 *    调用方法：Sett_TransGrantDiscountDAO.findByStatus()方法。
	 * @param info QueryByStatusConditionInfo 按状态查询条件实体类
	 * @return Vector 包含特殊交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public Collection findByConditions(QueryConditionInfo info) throws RemoteException, IRollbackException
	{
		Collection cln = null;
		try
		{
			Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
			cln = dao.findByConditions(info);
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
			throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
		}

		return cln;
	}
	/**
	 * 转贴现发放交易——删除
	 * 逻辑说明：
	 * （1）调用方法this.isTouched,判断要删除的记录是否被修改过。
	 * （2）判断参数TransGrantDiscountInfo 中的交易实体类的状态，
	 *      如果是暂存：
	 * 	 		调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 *      如果是保存：
	 * 			调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来
	 * 						的实体TransGrantDiscountInfo.
	 * 			调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 * @param info TransGrantDiscountInfo 转贴现发放交易实体类
	 * @return boolean；true，删除成功；false,失败。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public long grantDelete(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		//加锁时使用
		long sessionID = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getID());
			
			TransDiscountDetailInfo infoFromDB = dao.findInfoByID(info.getID());
			long nStatusIDFromDB = infoFromDB.getNStatusID();
			long nStatusIDFromJsp = info.getNStatusID();
			String errMsg = UtilOperation.checkStatus(nStatusIDFromJsp, nStatusIDFromDB, SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(this.mySessionCtx, errMsg);
			}
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.CHECK)
				throw new IRollbackException(this.mySessionCtx, "Sett_E018");
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
				throw new IRollbackException(this.mySessionCtx, "Sett_E019");
			//检查是否修改。
		//	if (this.grantIsTouch(info))
		//	{
		//		throw new IRollbackException(this.mySessionCtx, "Sett_E131");
		//	}
			//如果原来是保存状态,则修改loan_DiscountCredence 中的记录状态
			if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
			{
				dao.updateLoanCredenceDiscountStatus(info.getNDiscountCredence(), 2);
			}

			//执行删除操作
			lRtn = dao.delDiscountInfo(info);
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1)
				{
					utilOperation.releaseLock(info.getID(), sessionID);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
		}

		return lRtn;
		}
	}
	/**
	 * 转贴现发放交易——取消复核
	 * 逻辑操作：
	 * （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常" 您要取消复核的单据已被修改，请检查！"。
	 * （2）调用方法：AccountBookOperation.cancelCheckGrantDiscount()。进行取消复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为保存。
	 * @param info TransDiscountDetailInfo 转贴现发放交易实体类
	 * @return long 取消复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public long grantCancelCheck(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lRtn = -1;
		Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
		try
		{
			if (info.getID() != -1)
			{
				TransDiscountDetailInfo infoFromDB=dao.findInfoByID(info.getID());
				long nStatusIDFromDB=infoFromDB.getNStatusID();
				long nStatusIDFromJsp=info.getNStatusID();				
				String errMsg =UtilOperation.checkStatus(nStatusIDFromJsp,nStatusIDFromDB,SETTConstant.Actions.CANCELCHECK);			
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(this.mySessionCtx,errMsg);
				}
				//如果是暂存,则不允许复核
				if (nStatusIDFromDB == SETTConstant.TransactionStatus.TEMPSAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E024");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.SAVE)
					throw new IRollbackException(this.mySessionCtx, "Sett_E023");
				else if (nStatusIDFromDB == SETTConstant.TransactionStatus.DELETED)
					throw new IRollbackException(this.mySessionCtx, "Sett_E025");
				//判断要暂存的记录是否被修改过
			//	if (this.grantIsTouch(info))
			//		throw new IRollbackException(this.mySessionCtx, "Sett_E133");

				if (info.getNStatusID() == SETTConstant.TransactionStatus.CHECK)
				{				
					lRtn = dao.updateTransStatus(info.getID(), SETTConstant.TransactionStatus.SAVE,info.getNCheckUserID() , info.getSAbstract(), info.getSConfirmAbstract());
					dao.updateLoanCredenceDiscountStatus(info.getNDiscountCredence(), 2);
	
				}
				
				//进行财务处理
	//			AccountBookOperation accbkOprn = new AccountBookOperation();
	//			accbkOprn.cancelCheckGrantDiscount(info);
			}
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}

		return lRtn;
		}
	}
	
	/** and by lkliu 2011-05-12
	 * Method updateDiscountBillOfCancelCheck
	 * 
	 * @param info
	 * @return int 票据修改接口 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateDiscountBillOfCancelCheck(long lID)  throws RemoteException, IRollbackException
	{
		synchronized(lockObj){//控制并发添加	
			long res=0;
			Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
			long lBillStatusID=SETTConstant.BillStatus.KC;
			try
			{
				res = dao.updateDiscountBillOfCancelCheck(lID,null,lBillStatusID);				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage(), e);
			}
			return res;
		}	
	}
	
	
}
