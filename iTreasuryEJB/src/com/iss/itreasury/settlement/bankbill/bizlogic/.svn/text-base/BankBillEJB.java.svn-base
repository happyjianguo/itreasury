/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bankbill.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.bankbill.dao.Sett_BankAllowedBillTypeDAO;
import com.iss.itreasury.settlement.bankbill.dao.Sett_BankBillDAO;
import com.iss.itreasury.settlement.bankbill.dao.Sett_BillTypeDAO;
import com.iss.itreasury.settlement.bankbill.dao.Sett_DepositRelatedBankBillDAO;
import com.iss.itreasury.settlement.bankbill.dataentity.BankBillInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.BankBillTypeInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.BankBillDailyReportInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.DepositRelatedBankBillInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_Others_BankBill;
import com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_Others_DepositBankBill;
import com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_BankBill;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BankBillEJB implements SessionBean
{
	private javax.ejb.SessionContext ctx = null;
	final static long serialVersionUID = 3206093459760846163L;
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
		return ctx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		this.ctx = ctx;
	}
	/** 票据管理-查询详细的方法
	 * @param QueryCondition_Sett_Others_BankBill object
	 * @return BankBillInfo Collection
	 * @exception
	 */
	public Collection findDetail(QueryCondition_Sett_Others_BankBill objQueryCondition_Settlement_Setting_BankBill) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		try
		{
			Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
			//调用Sett_BankBill.findDetail()方法
			coll = objSett_BankBillDAO.findDetail(objQueryCondition_Settlement_Setting_BankBill);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception exp)
		{
			throw new IRollbackException(ctx, exp.getMessage(), exp);
		}
		return coll;
	}
	/** 票据查询-查询详细的方法
		 * @param QueryCondition_Sett_Others_BankBill object
		 * @return BankBillInfo Collection
		 * @exception
		 */
		public Collection findDetail(QueryCondition_Sett_BankBill objQueryCondition_Settlement_Setting_BankBill) throws RemoteException, IRollbackException
		{
			Collection coll = null;
			try
			{
				Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
				//调用Sett_BankBill.findDetail()方法
				coll = objSett_BankBillDAO.findDetail(objQueryCondition_Settlement_Setting_BankBill);
			}
			catch (RemoteException e)
			{
				throw e;
			}
			catch (IRollbackException e)
			{
				throw e;
			}
			catch (Exception exp)
			{
				throw new IRollbackException(ctx, exp.getMessage(), exp);
			}
			return coll;
		}
	/** 票据管理-查询每种类型的详细信息的方法
	 * @param lTypeID 票据类型ID
	 * @return BankBillTypeInfo 
	 * @exception
	 */
	public BankBillTypeInfo findTypeInfoByTypeID(long lTypeID) throws RemoteException, IRollbackException
	{
		BankBillTypeInfo info = null;
		try
		{
			Sett_BillTypeDAO objSett_BillTypeDAO = new Sett_BillTypeDAO();
			//调用Sett_BankBill.findDetail()方法
			info = objSett_BillTypeDAO.findByID(lTypeID);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception exp)
		{
			throw new IRollbackException(ctx, exp.getMessage(), exp);
		}
		return info;
	}

	/**票据注册的方法
	 * @param Collection
	 * @return void
	 * @exception
	 */
	public void registerBankBill(Collection colBankBillInfo) throws RemoteException, IRollbackException
	{
		Iterator iter = null;
		BankBillInfo initialBankBillInfo = new BankBillInfo();
		BankBillInfo otherBankBillInfo = null;
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		try
		{
			if (colBankBillInfo.size() > 0)
			{
				iter = colBankBillInfo.iterator();
				while (iter.hasNext())
				{
					initialBankBillInfo = (BankBillInfo) iter.next();
					//检测是否有重复的票据号码。
					otherBankBillInfo = new BankBillInfo();
					otherBankBillInfo = objSett_BankBillDAO.findByCombKey(initialBankBillInfo.getBankID(), initialBankBillInfo.getTypeID(), initialBankBillInfo.getBillNo());
					if (otherBankBillInfo == null)
					{
						initialBankBillInfo.setStatusID(SETTConstant.BankBillStatus.REGISTER);
						//调用方法SET_BillDAO.add()
						objSett_BankBillDAO.add(initialBankBillInfo);
					}
					else
					{
						throw new IRollbackException(ctx, "Sett_E040", initialBankBillInfo.getBillNo());
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}
	/**票据申领的方法
	 * @param Collection
	 * @return void
	 * @exception
	 */
	public void requireBankBill(Collection requireBankBill) throws RemoteException, IRollbackException
	{
		Iterator iter = null;
		BankBillInfo initialBankBillInfo = null;
		BankBillInfo terminalBankBillInfo = null;
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		try
		{
			Log.print("***进入票据申领方法***");
			if (requireBankBill != null && requireBankBill.size() > 0)
			{
				iter = requireBankBill.iterator();
				while (iter.hasNext())
				{
					initialBankBillInfo = new BankBillInfo();
					initialBankBillInfo = (BankBillInfo) iter.next();
					//得到新的实体类对象
					terminalBankBillInfo = new BankBillInfo();
					terminalBankBillInfo = objSett_BankBillDAO.findByCombKey(initialBankBillInfo.getBankID(), initialBankBillInfo.getTypeID(), initialBankBillInfo.getBillNo());

					Log.print("BankID = " + initialBankBillInfo.getBankID());
					Log.print("TypeID = " + initialBankBillInfo.getTypeID());
					Log.print("BillNo = " + initialBankBillInfo.getBillNo());
					Log.print("StatusID = " + terminalBankBillInfo.getStatusID());

					//判断其状态，只有状态为“注册”，才能申领。
					if (terminalBankBillInfo != null && terminalBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.REGISTER)
					{
						terminalBankBillInfo.setRequireDate(initialBankBillInfo.getRequireDate());
						terminalBankBillInfo.setRequireUserID(initialBankBillInfo.getRequireUserID());
						terminalBankBillInfo.setRequireClientID(initialBankBillInfo.getRequireClientID());
						terminalBankBillInfo.setRequireClientName(initialBankBillInfo.getRequireClientName());
						terminalBankBillInfo.setStatusID(SETTConstant.BankBillStatus.REQUIRE);

						//更新票据状态。
						objSett_BankBillDAO.update(terminalBankBillInfo);
					}
					else
					{
						throw new IRollbackException(ctx, "Sett_E041", terminalBankBillInfo.getBillNo());
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}
	/**票据注销的方法
	 * @param lArrayID 票据ID
	 * @param lDeleteUserID 注销人
	 * @param tsDeleteDate 注销日期
	 * @return void
	 * @exception
	 */
	public void deleteBankBill(long lArrayID, long lDeleteUserID, Timestamp tsDeleteDate) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		try
		{
			objBankBillInfo = objSett_BankBillDAO.findByID(lArrayID);
			if (objBankBillInfo != null)
			{
				//只有状态为“注册”的才可以被注销，否则提示错误。
				if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.REGISTER)
				{
					//修改返回的信息类的状态为“注销”.
					objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.DELETE);
					//增加注销人及时间信息
					objBankBillInfo.setDeleteUserID(lDeleteUserID);
					objBankBillInfo.setDeleteDate(tsDeleteDate);
					//更新票据。
					objSett_BankBillDAO.update(objBankBillInfo);
				}
				else
				{
					throw new IRollbackException(ctx, "Sett_E034", objBankBillInfo.getBillNo());
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}
	/**票据挂失的方法
	 * @param lArrayID 票据ID
	 * @param lReportLossUserID 挂失人
	 * @param tsReportLossDate 挂失日期
	 * @return void
	 * @exception
	 */
	public void reportLoss(long lArrayID, long lReportLossUserID, Timestamp tsReportLossDate) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		try
		{
			objBankBillInfo = objSett_BankBillDAO.findByID(lArrayID);

			if (objBankBillInfo != null)
			{
				//(1)、对于证实书类型的票据：只有状态为“使用”的才可以被挂失，否则提示错误。
				if (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || objBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
				{
					if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.USE)
					{
						//修改返回的信息类的字段nIsReportLoss（是否挂失）为1。
						//objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.REPORTLOSS);
						//将对应的挂失信息(ReportLossUserID,ReportLossDate）写入返回的信息类
						objBankBillInfo.setIsReportLoss(SETTConstant.BooleanValue.ISTRUE);
						objBankBillInfo.setReportLossUserID(lReportLossUserID);
						objBankBillInfo.setReportLossDate(tsReportLossDate);
						objSett_BankBillDAO.update(objBankBillInfo);
					}
					else
					{
						throw new IRollbackException(ctx, "Sett_E035", objBankBillInfo.getBillNo());
					}

				}
				//(2)、对于非证实书类型的票据：只有状态为“申领”的才可以被挂失，否则提示错误。
				else
				{
					if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.REQUIRE)
					{
						//修改返回的信息类的字段nIsReportLoss（是否挂失）为1。
						//objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.REPORTLOSS);
						//将对应的挂失信息(ReportLossUserID,ReportLossDate）写入返回的信息类
						objBankBillInfo.setIsReportLoss(SETTConstant.BooleanValue.ISTRUE);
						objBankBillInfo.setReportLossUserID(lReportLossUserID);
						objBankBillInfo.setReportLossDate(tsReportLossDate);
						objSett_BankBillDAO.update(objBankBillInfo);
					}
					else
					{
						throw new IRollbackException(ctx, "Sett_E035", objBankBillInfo.getBillNo());
					}
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			// TODO Auto-generated catch block
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}
	/**票据取消挂失的方法
	 * @param lArrayID 票据ID
	 * @param lCancelReportLossUserID 取消挂失人
	 * @param tsCancelReportLossDate 取消挂失日期
	 * @return void
	 * @exception
	 */
	public void cancelReportLoss(long lArrayID, long lCancelReportLossUserID, Timestamp tsCancelReportLossDate) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		try
		{
			objBankBillInfo = objSett_BankBillDAO.findByID(lArrayID);
			if (objBankBillInfo != null)
			{
				if (objBankBillInfo.getIsReportLoss() == SETTConstant.BooleanValue.ISTRUE)
				{
					//(1)、对于非证实书类型的票据，只有状态为“挂失”的才可以被取消挂失，否则提示错误。
					boolean bTag = false;
					bTag = bTag || (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.CASH_CHEQUE);
					bTag = bTag || (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.TRANSFER_ACCOUNT_CHEQUE);
					bTag = bTag || (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.CABLE_TRANSFER);
					bTag = bTag || (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.DRAFT_TRUST_DEED);
					bTag = bTag || (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.SEAL_CARD);
					//(2)、对于证实书类型的票据，只有状态为“挂失”，并且还没有重新签发新证实书的才可以被取消挂失，否则提示错误。
					bTag =
						bTag
							|| (((objBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION) || (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION))
								&& (objBankBillInfo.getStatusID() != SETTConstant.BankBillStatus.TERMINATE));
					if (bTag)
					{
						//将对应的取消挂失信息(CancelReportLossUserID,CancelReportLossDate）写入返回的信息类。
						//objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.REPORTLOSS);
						objBankBillInfo.setIsReportLoss(SETTConstant.BooleanValue.ISFALSE);
						objBankBillInfo.setCancelRequireUserID(lCancelReportLossUserID);
						objBankBillInfo.setCancelRequire(tsCancelReportLossDate);
						objSett_BankBillDAO.update(objBankBillInfo);

					}
					else
					{
						throw new IRollbackException(ctx, "Sett_E036", objBankBillInfo.getBillNo());
					}
				}
				else
				{
					throw new IRollbackException(ctx, "Sett_E036", objBankBillInfo.getBillNo());
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}
	/**票据取消申领的方法：
	 * @param lArrayID 票据ID
	 * @param lCancelRequireUserID 取消申领人
	 * @param tsCancelRequireDate 取消申领日期
	 * @return void
	 * @exception
	 */
	public void cancelRequire(long lID, long lCancelRequireUserID, Timestamp tsCancelRequireDate) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		try
		{
			Log.print("***进入取消票据申领方法***");
			objBankBillInfo = objSett_BankBillDAO.findByID(lID);

			if (objBankBillInfo != null)
			{
				//判断状态：只有状态为“申领”的才可以被取消申领，否则提示错误。
				if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.REQUIRE)
				{
					objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.REGISTER);
					//修改返回的信息类的状态为“注册”，同时将申领客户ID(nRequireClientID)置为-1。
					objBankBillInfo.setRequireClientID(-1);
					//将对应的取消申领信息(CancelRequireUserID,CancelRequireDate）写入返回的信息类。
					objBankBillInfo.setCancelRequireUserID(lCancelRequireUserID);
					objBankBillInfo.setCancelRequire(tsCancelRequireDate);
					objSett_BankBillDAO.update(objBankBillInfo);

				}
				else
				{
					throw new IRollbackException(ctx, "Sett_E037", objBankBillInfo.getBillNo());
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}
	/**重新签发证实书的方法：
	 * @param DepositRelatedBankBillInfo
	 * @return void
	 * @exception
	 */
	public void resignDepositBankBill(DepositRelatedBankBillInfo objDepositRelatedBankBill) throws RemoteException, IRollbackException
	{
		BankBillInfo oldBankBillInfo = new BankBillInfo();
		BankBillInfo newBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		Sett_DepositRelatedBankBillDAO objSett_DepositRelatedBankBillDAO = new Sett_DepositRelatedBankBillDAO();

		try
		{
			//根据旧证实书号ID，调用方法Sett_BankBillDAO.findByID()得到对应的实体类。
			oldBankBillInfo = objSett_BankBillDAO.findByID(objDepositRelatedBankBill.getOldBillID());
			//判断返回的旧证实书号的实体类的状态：只有挂失的，才可以允许重新签发新证实书。
			if (oldBankBillInfo != null && oldBankBillInfo.getIsReportLoss() == SETTConstant.BooleanValue.ISTRUE)
			{
				//Collection lList = new LinkedList();
				newBankBillInfo = objSett_BankBillDAO.findByID(objDepositRelatedBankBill.getBillID());
				//判断是否为证实书类型
				if (newBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || newBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
				{

					newBankBillInfo.setRequireClientID(objDepositRelatedBankBill.getRequireClientID());
					newBankBillInfo.setRequireUserID(objDepositRelatedBankBill.getSignUserID());
					newBankBillInfo.setRequireDate(objDepositRelatedBankBill.getSignDate());
					/*
					lList.add(newBankBillInfo);
					
					//调用方法：requireBankBill()
					this.requireBankBill(lList);
					*/
					//调用方法：useBankBill()
					this.useBankBill(
						newBankBillInfo.getBankID(),
						newBankBillInfo.getTypeID(),
						newBankBillInfo.getBillNo(),
						objDepositRelatedBankBill.getRequireClientID(),
						objDepositRelatedBankBill.getSignDate(),
						objDepositRelatedBankBill.getSignUserID());

					//将原来的票据号终止
					this.terminateBankBill(oldBankBillInfo.getBankID(), oldBankBillInfo.getTypeID(), oldBankBillInfo.getBillNo());
				}
				else
				{
					throw new IRollbackException(ctx, "Sett_E043", newBankBillInfo.getBillNo());
				}
				objSett_DepositRelatedBankBillDAO.add(objDepositRelatedBankBill);
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E038", oldBankBillInfo.getBillNo());
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}
	/**
	 * 查询每日日报表的信息
	 * @param lOfficeID
	 * @param tsDate
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public BankBillDailyReportInfo findDailyReportInfo(long lOfficeID, String strQueryDate,String strOpenDate) throws RemoteException, IRollbackException
	{
		BankBillDailyReportInfo reportInfo		= null;

		try
		{
			Sett_BankBillDAO objSett_BankBillDAO 	= new Sett_BankBillDAO();
			reportInfo= objSett_BankBillDAO.findDailyReportInfo(lOfficeID,strQueryDate,strOpenDate);
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return reportInfo;
	}
	
	/**
	 * 获取所有的有效的注册状态的票据（申领时使用）
	 * @param lBankID
	 * @param lTypeID
	 * @param strBillNoStart
	 * @param strBillNoEnd
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findAllBillForRequire(long lBankID, long lTypeID, String strBillNoStart, String strBillNoEnd) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		try
		{
			Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
			coll = objSett_BankBillDAO.findAllBillForRequire(lBankID, lTypeID, strBillNoStart, strBillNoEnd);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception exp)
		{
			throw new IRollbackException(ctx, exp.getMessage(), exp);
		}
		return coll;
	}
		
	/**票据使用时调用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @param lRequireClientID 申领客户ID
	 * @param tsDate 使用日期
	 * @param lUserID 操作人
	 * @return void
	 * @exception
	 */
	public void useBankBill(long lBankID, long lTypeID, String strBillNo, long lRequireClientID, Timestamp tsDate, long lUserID) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		Collection lList = new LinkedList();
		try
		{
			Log.print("***进入票据使用方法***");
			objBankBillInfo = objSett_BankBillDAO.findByCombKey(lBankID, lTypeID, strBillNo);
			Log.print("lBankID="+lBankID);
			Log.print("lTypeID="+lTypeID);
			Log.print("strBillNo="+strBillNo);
			if (objBankBillInfo != null)
			{
				if (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || objBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
				{
					if (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || objBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
					{
						objBankBillInfo.setRequireClientID(lRequireClientID);
						objBankBillInfo.setRequireUserID(lUserID);
						objBankBillInfo.setRequireDate(tsDate);
						lList.add(objBankBillInfo);

						//调用方法：requireBankBill()
						requireBankBill(lList);
						objBankBillInfo = objSett_BankBillDAO.findByCombKey(lBankID, lTypeID, strBillNo);
					}
				}

				//判断状态，只有状态为“申领”的才可以被使用。
				if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.REQUIRE && objBankBillInfo.getIsReportLoss() != SETTConstant.BooleanValue.ISTRUE)
				{
					//更改返回的实体类的状态为“使用”
					objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.USE);
					objSett_BankBillDAO.update(objBankBillInfo);
				}
				else
				{
					throw new IRollbackException(ctx, "Sett_E039", objBankBillInfo.getBillNo());
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}
	/**票据终止时调用的方法（仅用于定期存款证实书、通知存款证实书）：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @return void
	 * @exception
	 */
	public void terminateBankBill(long lBankID, long lTypeID, String strBillNo) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		Collection lList = new LinkedList();
		try
		{
			objBankBillInfo = objSett_BankBillDAO.findByCombKey(lBankID, lTypeID, strBillNo);
			if (objBankBillInfo != null)
			{
				if (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || objBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
				{
					//判断状态，只有状态为“使用”的才可以被终止
					if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.USE)
					{
						//更改返回的实体类的状态为“终止”
						objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.TERMINATE);
						objSett_BankBillDAO.update(objBankBillInfo);
					}
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}

	/**票据取消终止时调用的方法（仅用于定期存款证实书、通知存款证实书）：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @return void
	 * @exception
	 */
	public void cancelTerminateBankBill(long lBankID, long lTypeID, String strBillNo) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		Collection lList = new LinkedList();
		try
		{
			objBankBillInfo = objSett_BankBillDAO.findByCombKey(lBankID, lTypeID, strBillNo);
			if (objBankBillInfo != null)
			{
				if (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || objBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
				{
					//判断状态，只有状态为“终止”的才可以被取消终止
					if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.TERMINATE)
					{
						//更改返回的实体类的状态为“使用”
						objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.USE);
						objSett_BankBillDAO.update(objBankBillInfo);
					}
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}

	/**检查票据是否可用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @param lRequireClientID 申领客户ID
	 * @return long 1, 可用；0, 不可用
	 * @exception
	 */
	public long checkIsUseable(long lTypeID, long lBankID, String strBillNo, long lRequireClientID) throws RemoteException, IRollbackException
	{
		long lRtn = 1;
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		Collection lList = new LinkedList();
		try
		{
			objBankBillInfo = objSett_BankBillDAO.findByCombKey(lBankID, lTypeID, strBillNo);

			if (objBankBillInfo != null)
			{
				//如果是证实书类，判断状态是否为注册
				if (objBankBillInfo.getTypeID() == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || objBankBillInfo.getTypeID() == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
				{
					if (objBankBillInfo.getStatusID() != SETTConstant.BankBillStatus.REGISTER)
					{
						lRtn = 0;
					}
				}
				else
				{
					////如果是证实书类，状态必须是申领，请申领客户与传入的客户必须一致
					if (!(objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.REQUIRE && objBankBillInfo.getRequireClientID() == lRequireClientID))
					{
						lRtn = 0;
					}
				}
			}
			else
			{
				//没有查到相应记录
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return lRtn;
	}
	/**票据取消使用（删除交易）时调用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @param tsDate 操作日期
	 * @param lUserID 操作人
	 * @return void
	 * @exception
	 */
	public void cancelUseBankBill(long lBankID, long lBankBillTypeID, String strBankBillNo, Timestamp tsDate, long lUserID) throws RemoteException, IRollbackException
	{
		BankBillInfo objBankBillInfo = new BankBillInfo();
		Sett_BankBillDAO objSett_BankBillDAO = new Sett_BankBillDAO();
		try
		{
			Log.print("***进入取消票据使用方法***");			
			objBankBillInfo = objSett_BankBillDAO.findByCombKey(lBankID, lBankBillTypeID, strBankBillNo);
			Log.print("lBankID="+lBankID);
			Log.print("lTypeID="+lBankBillTypeID);
			Log.print("strBillNo="+strBankBillNo);
			if (objBankBillInfo != null)
			{
				//判断状态，只有状态为“使用”的才可以被取消使用。
				if (objBankBillInfo.getStatusID() == SETTConstant.BankBillStatus.USE)
				{
					//更改返回的实体类的状态为“使用”
					objBankBillInfo.setStatusID(SETTConstant.BankBillStatus.REQUIRE);
					objSett_BankBillDAO.update(objBankBillInfo);

				}
				else
				{
					throw new IRollbackException(ctx, "Sett_E044", objBankBillInfo.getBillNo());
				}
				//判断是否为证实书类票据，如果是，调用申领的方法 requireBankBill()。
				if (lBankBillTypeID == SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION || lBankBillTypeID == SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION)
				{
					this.cancelRequire(objBankBillInfo.getID(), lUserID, tsDate);
				}
			}
			else
			{
				throw new IRollbackException(ctx, "Sett_E042");
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}
	/**查询重新签发的证实书的方法：
	 * @param QueryCondition_Sett_Others_DepositBankBill object
	 * @return Collection
	 * @exception
	 */
	public Collection findResignedDepositBankBill(QueryCondition_Sett_Others_DepositBankBill objQueryCondition_Sett_Others_DepositBankBill) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		Sett_DepositRelatedBankBillDAO objSett_DepositRelatedBankBillDAO = new Sett_DepositRelatedBankBillDAO();
		try
		{
			//调用方法：Sett_DepositRelatedBankBillDAO.findDetail()
			coll = objSett_DepositRelatedBankBillDAO.findDetail(objQueryCondition_Sett_Others_DepositBankBill);
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return coll;
	}
	
	/**增加票据发放的银行（开户行）：
	 * @param lBankID 银行ID
	 * @param aryBankBillTypeID 票据类型ID的数据
	 * @return void
	 * @exception
	 */
	public void addGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException
	{
		Sett_BankAllowedBillTypeDAO dao = new Sett_BankAllowedBillTypeDAO();
		try
		{
			for (int i=0; i<aryBankBillTypeID.length; i++)
			{
				if (dao.isGrantBillBank(lBankID,aryBankBillTypeID[i]) == false)
				{
					dao.addGrantBillBank(lBankID,aryBankBillTypeID[i]);
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}
	
	/**删除票据发放的银行（开户行）：
	 * @param lBankID 银行ID
	 * @param aryBankBillTypeID 票据类型ID的数据
	 * @return void
	 * @exception
	 */
	public void dropGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException
	{
		Sett_BankAllowedBillTypeDAO dao = new Sett_BankAllowedBillTypeDAO();
		try
		{
			for (int i=0; i<aryBankBillTypeID.length; i++)
			{
				if (dao.isGrantBillBank(lBankID,aryBankBillTypeID[i]) == true)
				{
					dao.dropGrantBillBank(lBankID,aryBankBillTypeID[i]);
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}
	
	/**判断银行是否是发票银行的方法：
	 * @param lBankID 银行ID
	 * @return void
	 * @exception
	 */
	public boolean isGrantBillBank(long lBankID) throws RemoteException, IRollbackException
	{
		Sett_BankAllowedBillTypeDAO dao = new Sett_BankAllowedBillTypeDAO();
		try
		{
			return dao.isGrantBillBank(lBankID,-1);
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
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}
}
