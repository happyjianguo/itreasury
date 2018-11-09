/*
 * 创建日期 2004-11-24
 * 
 * TODO 要更改此生成的文件的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.iss.itreasury.settlement.reportlossorfreeze.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.base.SettlementBaseBean;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.reportlossorfreeze.dao.Sett_ReportLossOrFreezeDAO;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.QuerySubjectInfo;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.SessionMng;
import com.iss.system.dao.PageLoader;

/**
 * @author jinchen
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ReportLossOrFreezeBean extends SettlementBaseBean
{

	public final static int	OrderBy_TransNo				= 1;

	public final static int	OrderBy_ClientCode			= 2;

	public final static int	OrderBy_ClientName			= 3;

	public final static int	OrderBy_AccountNo			= 4;

	public final static int	OrderBy_OldDepositNo		= 5;

	public final static int	OrderBy_EffectiveDate		= 6;

	public final static int	OrderBy_ExecuteDate			= 7;

	public final static int	OrderBy_CheckUserName		= 8;

	public final static int	OrderBy_NewDepositNo		= 9;

	public final static int	OrderBy_FreezeAmount		= 10;

	public final static int	OrderBy_ExcuteGovernment	= 11;

	public final static int	OrderBy_FreezeType			= 12;

	//
	public StringBuffer		m_sbSelect					= null;

	public StringBuffer		m_sbFrom					= null;

	public StringBuffer		m_sbWhere					= null;

	public StringBuffer		m_sbOrderBy					= null;

	/**
	 * 
	 */
	public ReportLossOrFreezeBean()
	{

		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * Method preSave.
	 * 
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 */
	public long preSave(ReportLossOrFreezeInfo info) throws SettlementException
	{

		return 0;
	}

	/**
	 * Method save.
	 * 
	 * @param info
	 * @return long
	 * @throws IException
	 * @throws RemoteException
	 */
	public long save(ReportLossOrFreezeInfo info) throws IException
	{
        System.out.println("------------------------save Start---------------------------------");
		long lReturn = -1;
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		String strErr3 = null;
		ReportLossOrFreezeInfo newInfo = null;
		InutParameterInfo paraInfo = null;
		Connection transConn = null;
		try
		{
			transConn = this.initConnection();
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(transConn);
			AccountBean accountBean = new AccountBean(transConn);
			UtilOperation utilOperation = new UtilOperation();
			/*
			 * 检查生效时间
			 * 
			 */
			if (info.getTransActionType() == SETTConstant.TransactionType.CHANGECERTIFICATE)

			{
				ReportLossOrFreezeInfo tInfo = new ReportLossOrFreezeInfo();
				tInfo = sett_ReportLossOrFreezeDAO.findRecordLossOrFreeze(
						SETTConstant.TransactionType.CHANGECERTIFICATE, info.getAccountId(), info.getOldDepositNo());
				System.out.println("换发证书生效日期" + info.getEffectiveDate() + "\t" + "挂失业务生效日期" + tInfo.getEffectiveDate());
				if (tInfo.getEffectiveDate() != null && tInfo.getEffectiveDate().after(info.getEffectiveDate()))
				{
					throw new IException("换发证书业务的生效日期不能早于挂失业务生效日期");
				}
				//换发证书新生成的存单号，按照定期开立存单规则生成 add by zcwang 2007-8-11
				info.setNewDepositNo(utilOperation.getOpenDepositNoBackGround(info.getAccountId()));
				//
			}

			else if (info.getTransActionType() == SETTConstant.TransactionType.REPORTFIND)
			{
				ReportLossOrFreezeInfo tInfo = new ReportLossOrFreezeInfo();
				tInfo = sett_ReportLossOrFreezeDAO.findRecordLossOrFreeze(
						SETTConstant.TransactionType.CHANGECERTIFICATE, info.getAccountId(), info.getOldDepositNo());
				if (tInfo.getEffectiveDate() != null && tInfo.getEffectiveDate().after(info.getEffectiveDate()))
				{
					throw new IException("解挂业务的生效日期不能早于挂失业务生效日期");
				}
			}
			else if (info.getTransActionType() == SETTConstant.TransactionType.DEFREEZE)
			{
				ReportLossOrFreezeInfo tInfo = new ReportLossOrFreezeInfo();
				tInfo = sett_ReportLossOrFreezeDAO.findRecordLossOrFreeze(SETTConstant.TransactionType.DEFREEZE, info
						.getAccountId(), info.getOldDepositNo());
				if (tInfo.getEffectiveDate() != null && tInfo.getEffectiveDate().after(info.getEffectiveDate()))
				{
					throw new IException("解冻业务的生效日期不能早于冻结业务生效日期");
				}
				if(info.getFreezeAmount()!=tInfo.getFreezeAmount())
				{
					throw new IException("解冻业务的解冻金额不等于冻结金额" + tInfo.getFreezeAmount());
				}
			}

			else
			{

			}

			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{

				newInfo = (ReportLossOrFreezeInfo) sett_ReportLossOrFreezeDAO.findByID(info.getId(),
						ReportLossOrFreezeInfo.class);
				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{

					throw new SettlementException("Sett_E180", strErr2, null);

				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}
			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.SAVE);
            //抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				throw new IException(strErr1);

			}

		
			
			// 获取新交易号
			transNo = utilOperation.getNewTransactionNo(info.getOfficeId(), info.getCurrencyId(),info.getTransActionType());

			info.setTransNo(transNo);
			info.setStatus(SETTConstant.TransactionStatus.SAVE);

			lReturn = sett_ReportLossOrFreezeDAO.add(info);
			System.out.println("************lReturn*********************" + lReturn);
			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.SAVE);
			transConn.commit();
			this.finalizeConnection(transConn);

			// 判断是否有审批,如果InutParameterInfo不为空的话就添加审批流
			if (info.getInutParameterInfo() != null)
			{
				info.setId(lReturn);
				this.initApproval(info,transConn);
			}
			

		}
		catch (SettlementException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			this.finalizeConnection(transConn);
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			this.finalizeConnection(transConn);
			throw new SettlementException("数据库操作异常", e);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.finalizeConnection(transConn);
			throw new SettlementException("其他异常", e);
		}
		finally
		{
			try
			{
				this.finalizeConnection(transConn);
			}
			catch (SettlementException e1)
			{
				// TODO 自动生成 catch 块
				e1.printStackTrace();
				throw new SettlementException("数据库释放连接异常", e1);
			}
		}
		System.out.println("------------------------save END---------------------------------");
		return lReturn;
	}

	/**
	 * Method delete.
	 * 
	 * @param info
	 * @return long 删除的记录ID
	 * @throws IException
	 * @throws RemoteException
	 */
	public long delete(ReportLossOrFreezeInfo info) throws IException
	{

		long lReturn = 1;
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		ReportLossOrFreezeInfo newInfo = null;
		Connection transConn = null;
		try
		{
			transConn = this.initConnection();
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			AccountBean accountBean = new AccountBean(transConn);
			// UtilOperation utilOperation = new UtilOperation();

			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{
				newInfo = (ReportLossOrFreezeInfo) sett_ReportLossOrFreezeDAO.findByID(info.getId(),
						ReportLossOrFreezeInfo.class);
				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{
					throw new SettlementException("Sett_E180", strErr2, null);
				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}

			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.DELETE);
			// 抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				throw new IException(strErr1);
			}
            
			info.setStatus(SETTConstant.TransactionStatus.DELETED);
			info.setModifyDate(Env.getSystemDateTime());
			sett_ReportLossOrFreezeDAO.update(info);
			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.DELETE);
			transConn.commit();
			this.finalizeConnection(transConn);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (SettlementException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e)
		{
			lReturn = -1;
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("数据库操作异常", e);
		}
		catch (Exception e)
		{
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("其他异常", e);
		}
		finally
		{
			try
			{
				this.finalizeConnection(transConn);
			}
			catch (SettlementException e1)
			{
				// TODO 自动生成 catch 块
				e1.printStackTrace();
				throw new SettlementException("数据库释放连接异常", e1);
			}
		}
		return lReturn;
	}

	/**
	 * Method delete.
	 * 
	 * @param info
	 * @return long 复核的记录ID
	 * @throws IException
	 * @throws RemoteException
	 */
	public long check(ReportLossOrFreezeInfo info) throws IException
	{
		System.out.println("------------------------check Start---------------------------------");
		long lReturn = -1;
		lReturn = info.getTransActionType();
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		ReportLossOrFreezeInfo newInfo = null;
		Connection transConn = null;

		try
		{
			transConn = this.initConnection();
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			AccountBean accountBean = new AccountBean(transConn);

			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{
				newInfo = (ReportLossOrFreezeInfo) sett_ReportLossOrFreezeDAO.findByID(info.getId(),
						ReportLossOrFreezeInfo.class);
				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{
					throw new SettlementException("Sett_E180", strErr2, null);
				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}

			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.CHECK);
			// 抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				throw new IException(strErr1);
			}
			info.setStatus(SETTConstant.TransactionStatus.CHECK);
			info.setModifyDate(Env.getSystemDateTime());
			sett_ReportLossOrFreezeDAO.update(info);
			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.CHECK);
			transConn.commit();
			this.finalizeConnection(transConn);

		}

		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (SettlementException e)
		{
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e)
		{
			lReturn = -1;
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("数据库操作异常", e);
		}
		catch (Exception e)
		{
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("其他异常", e);
		}
		finally
		{
			try
			{
				this.finalizeConnection(transConn);
			}
			catch (SettlementException e1)
			{
				// TODO 自动生成 catch 块
				e1.printStackTrace();
				throw new SettlementException("数据库释放连接异常", e1);
			}
		}
		System.out.println("------------------------check End---------------------------------");
		return lReturn;

	}

	/**
	 * Method delete.
	 * 
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws IException
	 * @throws RemoteException
	 */
	public long cancelCheck(ReportLossOrFreezeInfo info) throws IException
	{

		long lReturn = -1;
		long lCheckStatusID = -1;
		lReturn = info.getTransActionType();
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		ReportLossOrFreezeInfo newInfo = null;
		Connection transConn = null;

		try
		{
			transConn = this.initConnection();
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			AccountBean accountBean = new AccountBean(transConn);

			if (info.getTransActionType() == SETTConstant.TransactionType.CHANGECERTIFICATE)
			{
				if (sett_ReportLossOrFreezeDAO.checkTransValid(info))
				{
					throw new IException("此证书已经进行过交易");
				}
			}
			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{
				newInfo = (ReportLossOrFreezeInfo) sett_ReportLossOrFreezeDAO.findByID(info.getId(),
						ReportLossOrFreezeInfo.class);
				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{
					throw new SettlementException("Sett_E180", strErr2, null);
				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}

			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.CANCELCHECK);
			// 抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				System.out.println("strErr1===================" + strErr1);
				throw new IException(strErr1);
			}

			lCheckStatusID = FSWorkflowManager.getSettCheckStatus(info.getInutParameterInfo());
			System.out.println("----------=======lCheckStatusID======--------------" + lCheckStatusID);
			info.setStatus(lCheckStatusID);
			System.out.println("-----------cancelCheck---------info.getStatus()------------------------------------"
					+ info.getStatus());
			info.setModifyDate(Env.getSystemDateTime());
			sett_ReportLossOrFreezeDAO.update(info);
			sett_ReportLossOrFreezeDAO.updateCheckDate(info.getId());
			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.CANCELCHECK);
			transConn.commit();
			this.finalizeConnection(transConn);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (SettlementException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e)
		{
			lReturn = -1;
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("数据库操作异常", e);
		}
		catch (Exception e)
		{
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("其他异常", e);
		}
		finally
		{
			try
			{
				this.finalizeConnection(transConn);
			}
			catch (SettlementException e1)
			{
				// TODO 自动生成 catch 块
				e1.printStackTrace();
				throw new SettlementException("数据库释放连接异常", e1);
			}
		}
		return lReturn;
	}

	public Collection findByConditions(ReportLossOrFreezeQueryInfo qInfo, int orderByType, boolean isDesc)
			throws SettlementException
	{

		return null;
	}

	/**
	 * 方法说明：根据查询条件匹配 Method match.
	 * 
	 * @param ReportLossOrFreezeInfo info
	 * @return ReportLossOrFreezeInfo
	 * @throws SettlementException
	 */
	public ReportLossOrFreezeInfo match(long transactionType, ReportLossOrFreezeInfo qInfo) throws SettlementException
	{

		ReportLossOrFreezeInfo info = null;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			info = (ReportLossOrFreezeInfo) dao.match(transactionType, qInfo);
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结业务发生异常", e);
		}
		return info;
	}

	/**
	 * 方法说明：根据查询条件匹配 Method match.
	 * 
	 * @param ReportLossOrFreezeInfo info
	 * @return ReportLossOrFreezeInfo
	 * @throws SettlementException
	 */
	public TransFixedOpenInfo findDepositByNo(String depositno)
	{

		TransFixedOpenInfo info = new TransFixedOpenInfo();
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			info = (TransFixedOpenInfo) dao.findDepositByNo(depositno);
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			// throw new SettlementException("查询挂失冻结业务发生异常",e);
		}
		return info;
	}

	/**
	 * 方法说明：根据查询条件匹配 Method match.
	 * 
	 * @param ReportLossOrFreezeInfo info
	 * @return ReportLossOrFreezeInfo
	 * @throws SettlementException
	 */
	public TransFixedOpenInfo findDepositById(long id)
	{

		TransFixedOpenInfo rInfo = new TransFixedOpenInfo();
		ReportLossOrFreezeInfo info = new ReportLossOrFreezeInfo();
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			info = (ReportLossOrFreezeInfo) dao.findByID(id, ReportLossOrFreezeInfo.class);
			rInfo = (TransFixedOpenInfo) dao.findDepositByCondition(info);
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			// throw new SettlementException("查询挂失冻结业务发生异常",e);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return rInfo;
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * 
	 * @param lID
	 * @return ReportLossOrFreezeInfo
	 * @throws Exception 
	 * @throws IRollbackException
	 */
	public ReportLossOrFreezeInfo findByID(long lID) throws Exception
	{

		ReportLossOrFreezeInfo info = null;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			info = (ReportLossOrFreezeInfo) dao.findById(lID);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结业务发生异常", e);
		}
		return info;

	}
	/**
	 *  方法说明：根据账户ID，得到账户信息 add by zcwang 2007-5-31
	 * @param lID
	 * @return
	 * @throws Exception 
	 */
	public ReportLossOrFreezeInfo findByid(long lID) throws Exception
	{
		
		//ReportLossOrFreezeInfo info = null;
		//Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO(transConn);

		
		ReportLossOrFreezeInfo info = null;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		dao.setSelfManagedConn(true);
		try
		{
			info = dao.findById(lID);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结业务发生异常", e);
		}
		return info;

	}
	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * 
	 * @param lID
	 * @return ReportLossOrFreezeInfo
	 * @throws IRollbackException
	 */
	public long findSubAccountStatus(long lAccountId, String strDepositNo) throws SettlementException
	{

		long lStatus = -1;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			lStatus = dao.findSubAccountStatus(lAccountId, strDepositNo);
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结业务发生异常", e);
		}
		return lStatus;

	}

	private String checkInputValid(ReportLossOrFreezeInfo info, long lTransactionOperate) throws IException
	{
       System.out.println("----------------------checkInputValid----------start------------------");
		String strErrMsg = null;
		long lTransActionType = -1;
		boolean lReturn = false;
		lTransActionType = info.getTransActionType();
		System.out.println("lTransActionType====================" + info.getTransActionType());
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		ReportLossOrFreezeInfo rinfo = new ReportLossOrFreezeInfo(); 
		long lSubAccountID = -1;
		long maxId = -1;
		try
		{
			if (info == null)
			{
				throw new IException("Sett_E056");
			}
			if (info.getTransActionType() == SETTConstant.TransactionType.CHANGECERTIFICATE)
			{
				if (lTransactionOperate == SETTConstant.TransactionOperate.SAVE)
				{
					lSubAccountID = dao.findSubAccountId(info.getAccountId(), info.getOldDepositNo());

				}
				else
				{
					lSubAccountID = dao.findSubAccountId(info.getAccountId(), info.getNewDepositNo());

				}

			}
			else if(info.getTransActionType() == SETTConstant.TransactionType.FREEZE 
					 || info.getTransActionType() == SETTConstant.TransactionType.DEFREEZE)
			{
				lSubAccountID = dao.findSubAccountId(info.getAccountId(), info.getOldDepositNo());
                if(lTransactionOperate == SETTConstant.TransactionOperate.SAVE)
                {
            		lReturn = dao.isReportLossOrFreeze(info);
            		if(lReturn){
                			if(info.getTransActionType() == SETTConstant.TransactionType.FREEZE){
                		        strErrMsg = " 冻结账号失败,请检查账号状态 ";
                			}else{
                				strErrMsg = " 解冻账号失败,请检查账号状态 ";
                			}
            		}
                }
                else if(lTransactionOperate == SETTConstant.TransactionOperate.DELETE)
                {
                	if(info.getId() > 0){
                		if(info.getStatus()!=SETTConstant.TransactionStatus.SAVE)
                		{
                			strErrMsg = " 删除账号失败,请检查账号状态 ";
                		}
                	}
                }
                else if(lTransactionOperate == SETTConstant.TransactionOperate.CHECK)
                {
                	    rinfo = dao.findById(info.getId());
                		
                    		if(rinfo.getStatus()!= info.getStatus()
                    		   || !dao.isValidAccount(lSubAccountID,info.getTransActionType(),lTransactionOperate))
                    		{
                    			if(info.getTransActionType() == SETTConstant.TransactionType.FREEZE){
                    		        strErrMsg = " 冻结复核账号失败,请检查账号状态 ";
                    			}else{
                    				strErrMsg = " 解冻复核账号失败,请检查账号状态 ";
                    			}
                    		}	
                }
                else if(lTransactionOperate == SETTConstant.TransactionOperate.CANCELCHECK)
                {

                		if(info.getTransActionType() == SETTConstant.TransactionType.DEFREEZE)
                		{
                			
                    		lReturn = dao.isReportLossOrFreeze(info);
                    		if(lReturn)
                    		{
                    			 strErrMsg = " 此帐户已被重新冻结不能取消复核 ";
                    		}
                    		else
                    		{
                    			 maxId = dao.findMaxId(info);
                    			 if(info.getId() != maxId)
                    			 {
                    				 strErrMsg = " 解冻取消复核失败,请检查账号状态 ";
                    			 }
                    			 else
                    			 {
                            		if(info.getStatus()!=  SETTConstant.TransactionStatus.CHECK
                            		   || !dao.isValidAccount(lSubAccountID,info.getTransActionType(),lTransactionOperate))
                            		{
                            			if(info.getTransActionType() == SETTConstant.TransactionType.DEFREEZE){
                            				strErrMsg = " 解冻取消复核失败,请检查账号状态 ";
                            			}
                            		}
                            		else
                            		{
		                            	info.setTransActionType(SETTConstant.TransactionType.FREEZE);
		                   				 long maxId2 = dao.findMaxId(info);
		                   				 lReturn = dao.isReportLossOrFreeze(info);
		                   				 if(lReturn || (maxId2 > maxId))
		                   				 {
		                   					 strErrMsg = " 账户已被重新冻结不能取消复核 ";
		                   				 }
		                   				 info.setTransActionType(SETTConstant.TransactionType.DEFREEZE);
                            		}
                            	 }
                    		}
                		}
                		else
                		{
                			 maxId = dao.findMaxId(info);
                			 if(info.getId() != maxId)
                			 {
                				 strErrMsg = " 冻结取消复核顺序不正确 ";
                			 }
                			 else
                			 {
                				 info.setTransActionType(SETTConstant.TransactionType.DEFREEZE);
                				 long maxId2 = dao.findMaxId(info);
                				 lReturn = dao.isReportLossOrFreeze(info);
                				 if(lReturn || (maxId2 > maxId))
                				 {
                					 strErrMsg = " 账户已被解冻不能取消复核 ";
                				 }
                				 info.setTransActionType(SETTConstant.TransactionType.FREEZE);
                			 }
	                    	
	                    }
//                		if(!dao.isValidAccount(lSubAccountID,info.getTransActionType(),lTransactionOperate))
//                		{
//                			if(info.getTransActionType() == SETTConstant.TransactionType.FREEZE){
//                		        strErrMsg = " 冻结取消复核失败,请检查账号状态 ";
//                			}else{
//                				strErrMsg = " 解冻取消复核失败,请检查账号状态 ";
//                			}
//                		}
                }
                	
            }
			else
			{
				lSubAccountID = dao.findSubAccountId(info.getAccountId(), info.getOldDepositNo());
				if (!dao.isValidAccount(lSubAccountID, lTransActionType, lTransactionOperate))
    			{
    				strErrMsg = " 挂失账号不合法,请检查账号状态 ";
    			}
			}
            
			
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块

			e.printStackTrace();
			;
			throw new SettlementException("检查操作账号发生异常", e);
		}
		catch (Exception e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return strErrMsg;

	}

	/**
	 * 判断状态是否正确
	 * 
	 * @param 页面操作类型，页面传过来的状态，后台取到的状态
	 * @return boolean false 状态不匹配
	 * @throws java.rmi.RemoteException
	 * @throws IRollbackException
	 */
	private String checkStatus(long lStatusID, long lNewStatusID, long lActionID)
	{

		String strErrMsg = null;
		System.out.println("------lStatusID====:" + lStatusID + "-------lNewStatusID====:" + lNewStatusID);
		if (lStatusID != lNewStatusID)
		{
			// strErrMsg += "当前交易已经被修改";
			strErrMsg = "当前交易已经被修改";
		}
		return strErrMsg;
	}
	/**
	 * 
	 * Method isTouch
	 * 
	 * @descriptin 判断是否被非法修改过
	 * @return long 根据条件查找
	 * @throws RemoteException
	 */
	private boolean isTouch(ReportLossOrFreezeInfo info)
	{

		boolean bReturn = false;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			// 判断是否被非法修改过
			Timestamp lastTouchDate;
			lastTouchDate = dao.findTouchDate(info.getId());
			System.out.println("-----------------lastTouchDate--------------------------" + lastTouchDate);
			// @TBD: get touch date from info class
			Timestamp curTouchDate = info.getModifyDate();
			System.out.println("---------------------curTouchDate----------------------" + curTouchDate);
			if (curTouchDate == null || lastTouchDate.compareTo(curTouchDate) != 0)
			{
				bReturn = true;
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
		return bReturn;
	}

	/**
	 * 方法说明：根据ID查找修改时间
	 * 
	 * @param transCurrentDepositID
	 * @return Timestamp
	 * @throws SettlementException
	 * @throws SettlementDAOException
	 * @throws IException
	 */
	public Collection findByConditions(long lUserId, String strDate, long lStatus, long lTransactionType)
			throws SettlementException
	{

		Collection c = null;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			c = dao.findByConditions(lUserId, strDate, lStatus, lTransactionType);
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结信息发生异常", e);
		}
		return c;

	}

	/**
	 * 查询-账户信息查询
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryReportLossOrFreezeInfo(ReportLossOrFreezeQueryInfo qInfo) throws Exception
	{

		getReportLossOrFreezeInfoSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * @param info
	 */
	public void getReportLossOrFreezeInfoSQL(ReportLossOrFreezeQueryInfo info)
	{

		// TODO 自动生成方法存根
		m_sbSelect = new StringBuffer();
		// select
		m_sbSelect
				.append("   \n    re.*,c.SCODE clientcode,c.sname clientname ,u.sname checkUserName, sa.SACCOUNTNO accountNo  \n");

		// from
		m_sbFrom = new StringBuffer();

		m_sbFrom.append("  sett_reportlossorfreeze re ,sett_account sa, client c, userinfo u \n");

		// where
		m_sbWhere = new StringBuffer();
		if (info.getTransActionType() == SETTConstant.TransactionType.REPORTLOSS)
		{
			m_sbWhere.append("         TRANSACTIONTYPE in (" + SETTConstant.TransactionType.REPORTLOSS + ","
					+ SETTConstant.TransactionType.REPORTFIND + "," + SETTConstant.TransactionType.CHANGECERTIFICATE
					+ ") \n");
		}
		else if (info.getTransActionType() == SETTConstant.TransactionType.FREEZE)
		{
			m_sbWhere.append("         TRANSACTIONTYPE in (" + SETTConstant.TransactionType.FREEZE + ","
					+ SETTConstant.TransactionType.DEFREEZE + ") \n");
		}
		else
		{
			m_sbWhere.append("         TRANSACTIONTYPE>0 \n");
		}
		if (info.getClientId() > 0)
		{
			m_sbWhere.append(" and clientId =" + info.getClientId() + " \n");
		}
		if (info.getStartDate() != null)
		{
			m_sbWhere.append("        and executedate >= to_date('" + DataFormat.getDateString(info.getStartDate())
					+ "','yyyy-mm-dd')   \n");
		}
		if (info.getEndDate() != null)
		{
			m_sbWhere.append("        and executedate <= to_date('" + DataFormat.getDateString(info.getEndDate())
					+ "','yyyy-mm-dd')   \n");
		}

		m_sbWhere.append(" and status=" + info.getStatus() + " \n");
		//add by wjliu 区分办事处和币种 2007-5-30
		m_sbWhere.append(" and currencyid = " +info.getCurrencyId()+ "\n");
		m_sbWhere.append(" and officeid = " +info.getOfficeId()+ "\n");
		
		m_sbWhere.append(" and re.clientid=c.id(+) and re.checkuserid=u.id(+) and re.accountid=sa.id(+) " + " \n");
		

		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDesc() == 1 ? " desc " : "";
		switch ((int) info.getOrderField())
		{

			case OrderBy_TransNo :
				m_sbOrderBy.append(" \n order by TRANSNO" + strDesc);
				break;

			case OrderBy_ClientCode :
				m_sbOrderBy.append(" \n order by clientcode" + strDesc);
				break;
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n order by clientname" + strDesc);
				break;
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by accountNo" + strDesc);
				break;

			case OrderBy_OldDepositNo :
				m_sbOrderBy.append(" \n order by OLDDEPOSITNO" + strDesc);
				break;
			case OrderBy_NewDepositNo :
				m_sbOrderBy.append(" \n order by NEWDEPOSITNO" + strDesc);
				break;
			case OrderBy_EffectiveDate :
				m_sbOrderBy.append(" \n order by EFFECTIVEDATE" + strDesc);
				break;
			case OrderBy_ExecuteDate :
				m_sbOrderBy.append(" \n order by EXECUTEDATE" + strDesc);
				break;
			case OrderBy_CheckUserName :
				m_sbOrderBy.append(" \n order by checkUserName" + strDesc);
				break;
			case OrderBy_FreezeAmount :
				m_sbOrderBy.append(" \n order by freezeAmount" + strDesc);
				break;
			case OrderBy_FreezeType :
				m_sbOrderBy.append(" \n order by freezetype" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by TRANSNO" + strDesc);
				break;
		}
	}

	/**
	 * 方法说明：根据ID查找修改时间
	 * 
	 * @param transCurrentDepositID
	 * @return Timestamp
	 * @throws SettlementException
	 * @throws SettlementDAOException
	 * @throws IException
	 */
	public Collection findByConditions(ReportLossOrFreezeQueryInfo qInfo) throws SettlementException
	{

		Collection c = null;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			c = dao.findByConditions(qInfo);
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结信息发生异常", e);
		}
		return c;

	}

	public static void main(String[] args)
	{

	}

	/**
	 * @return 返回 m_sbOrderBy。
	 */
	public StringBuffer getOrderBy()
	{

		return m_sbOrderBy;
	}

	/**
	 * @param orderBy 要设置的 m_sbOrderBy。
	 */
	public void setOrderBy(StringBuffer orderBy)
	{

		m_sbOrderBy = orderBy;
	}

	public ReportLossOrFreezeInfo findRecordLossOrFreeze(long lTransType, long lAccountId, String strDepositNo)
			throws SettlementException
	{

		ReportLossOrFreezeInfo info = null;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO();
		try
		{
			info = (ReportLossOrFreezeInfo) dao.findRecordLossOrFreeze(lTransType, lAccountId, strDepositNo);
		}
		catch (SettlementDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结业务发生异常", e);
		}
		return info;
	}

	/** ***************added by mzh_fu 2007/05/06 为了使这些方法被EJB调用,且不影响其它地方的调用,所做的下面的方法(无耐之举)*begin********************** */

	/**
	 * Method save.
	 * 
	 * @param info
	 * @return long
	 * @throws IException
	 * @throws RemoteException
	 */
	public long save(ReportLossOrFreezeInfo info, Connection transConn) throws IException
	{

		long lReturn = -1;
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		String strErr3 = null;
		ReportLossOrFreezeInfo newInfo = null;
		try
		{
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			//sett_ReportLossOrFreezeDAO.setSelfManagedConn(true);

			// Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(
			// transConn);
			// sett_SubAccountDAO.setSelfManagedConn(true);

			AccountBean accountBean = new AccountBean(transConn);
			UtilOperation utilOperation = new UtilOperation();
			/*
			 * 检查生效时间
			 * 
			 */
			if (info.getTransActionType() == SETTConstant.TransactionType.CHANGECERTIFICATE)

			{
				ReportLossOrFreezeInfo tInfo = new ReportLossOrFreezeInfo();
				tInfo = sett_ReportLossOrFreezeDAO.findRecordLossOrFreeze(
						SETTConstant.TransactionType.CHANGECERTIFICATE, info.getAccountId(), info.getOldDepositNo());
				System.out.println("换发证书生效日期" + info.getEffectiveDate() + "\t" + "挂失业务生效日期" + tInfo.getEffectiveDate());
				if (tInfo.getEffectiveDate() != null && tInfo.getEffectiveDate().after(info.getEffectiveDate()))
				{
					throw new IException("换发证书业务的生效日期不能早于挂失业务生效日期");
				}
				//				换发证书新生成的存单号，按照定期开立存单规则生成 add by zcwang 2007-8-11
				info.setNewDepositNo(utilOperation.getOpenDepositNoBackGround(info.getAccountId()));
				//
			}

			else if (info.getTransActionType() == SETTConstant.TransactionType.REPORTFIND)
			{
				ReportLossOrFreezeInfo tInfo = new ReportLossOrFreezeInfo();
				tInfo = sett_ReportLossOrFreezeDAO.findRecordLossOrFreeze(
						SETTConstant.TransactionType.CHANGECERTIFICATE, info.getAccountId(), info.getOldDepositNo());
				if (tInfo.getEffectiveDate() != null && tInfo.getEffectiveDate().after(info.getEffectiveDate()))
				{
					throw new IException("解挂业务的生效日期不能早于挂失业务生效日期");
				}
			}
			else if (info.getTransActionType() == SETTConstant.TransactionType.DEFREEZE)
			{
				ReportLossOrFreezeInfo tInfo = new ReportLossOrFreezeInfo();
				tInfo = sett_ReportLossOrFreezeDAO.findRecordLossOrFreeze(SETTConstant.TransactionType.DEFREEZE, info
						.getAccountId(), info.getOldDepositNo());
				if (tInfo.getEffectiveDate() != null && tInfo.getEffectiveDate().after(info.getEffectiveDate()))
				{
					throw new IException("解冻业务的生效日期不能早于冻结业务生效日期");
				}
			}

			else
			{

			}

			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{

				newInfo = sett_ReportLossOrFreezeDAO.findById(info.getId());

				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{

					throw new SettlementException("Sett_E180", strErr2, null);

				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}
			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.SAVE);
			// 抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				throw new IException(strErr1);

			}

			// 获取新交易号
			transNo = utilOperation.getNewTransactionNo(info.getOfficeId(), info.getCurrencyId(),info.getTransActionType());

			info.setTransNo(transNo);
			info.setStatus(SETTConstant.TransactionStatus.SAVE);
			lReturn = sett_ReportLossOrFreezeDAO.add(info);

			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.SAVE);
			//			 判断是否有审批,如果InutParameterInfo不为空的话就添加审批流
			if (info.getInutParameterInfo() != null)
			{
				info.setId(lReturn);
				this.initApproval(info,transConn);
			}

		}
		catch (SettlementException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
			this.finalizeConnection(transConn);
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.finalizeConnection(transConn);
			throw new SettlementException("其他异常", e);
		}
		return lReturn;
	}

	/**
	 * Method delete.
	 * 
	 * @param info
	 * @return long 删除的记录ID
	 * @throws IException
	 * @throws RemoteException
	 */
	public long delete(ReportLossOrFreezeInfo info, Connection transConn) throws IException
	{

		long lReturn = 1;
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		ReportLossOrFreezeInfo newInfo = null;
		try
		{
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			//sett_ReportLossOrFreezeDAO.setSelfManagedConn(true);

			AccountBean accountBean = new AccountBean(transConn);
			// UtilOperation utilOperation = new UtilOperation();

			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{
				newInfo = sett_ReportLossOrFreezeDAO.findById(info.getId());

				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{
					throw new SettlementException("Sett_E180", strErr2, null);
				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}

			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.DELETE);
			// 抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				throw new IException(strErr1);
			}

			info.setStatus(SETTConstant.TransactionStatus.DELETED);
			info.setModifyDate(Env.getSystemDateTime());
			sett_ReportLossOrFreezeDAO.update(info);
			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.DELETE);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (SettlementException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("其他异常", e);
		}
		return lReturn;
	}

	/**
	 * Method delete.
	 * 
	 * @param info
	 * @return long 复核的记录ID
	 * @throws IException
	 * @throws RemoteException
	 */
	public long check(ReportLossOrFreezeInfo info, Connection transConn) throws IException
	{

		long lReturn = -1;
		lReturn = info.getTransActionType();
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		ReportLossOrFreezeInfo newInfo = null;
		try
		{
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			//sett_ReportLossOrFreezeDAO.setSelfManagedConn(true);

			AccountBean accountBean = new AccountBean(transConn);

			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{
				newInfo = sett_ReportLossOrFreezeDAO.findById(info.getId());

				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{
					throw new SettlementException("Sett_E180", strErr2, null);
				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}

			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.CHECK);
			// 抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				throw new IException(strErr1);
			}
			info.setStatus(SETTConstant.TransactionStatus.CHECK);
			info.setModifyDate(Env.getSystemDateTime());
			sett_ReportLossOrFreezeDAO.update(info);
			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.CHECK);
		}

		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (SettlementException e)
		{
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("其他异常", e);
		}
		return lReturn;

	}

	/**
	 * Method delete.
	 * 
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws IException
	 * @throws RemoteException
	 */
	public long cancelCheck(ReportLossOrFreezeInfo info, Connection transConn) throws IException
	{

		long lReturn = -1;
		long lCheckStatusID = -1;
		lReturn = info.getTransActionType();
		String transNo = "";
		String strErr1 = null;
		String strErr2 = null;
		ReportLossOrFreezeInfo newInfo = null;

		try
		{
			Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
					"Sett_ReportLossOrFreeze", transConn);
			//sett_ReportLossOrFreezeDAO.setSelfManagedConn(true);
			AccountBean accountBean = new AccountBean(transConn);

			if (info.getTransActionType() == SETTConstant.TransactionType.CHANGECERTIFICATE)
			{
				if (sett_ReportLossOrFreezeDAO.checkTransValid(info))
				{
					throw new IException("此证书已经进行过交易");
				}
			}
			/*
			 * 检查交易状态合法性
			 */
			if (info.getId() > 0)
			{
				newInfo = sett_ReportLossOrFreezeDAO.findById(info.getId());

				strErr2 = this.checkStatus(info.getStatus(), newInfo.getStatus(), -1);
				if (strErr2 != null && strErr2.length() > 0)
				{
					throw new SettlementException("Sett_E180", strErr2, null);
				}
				/*
				 * 检查修改时间合法性
				 */
				if (isTouch(info))
				{
					throw new SettlementException("Sett_E130", null);
				}

			}

			/*
			 * 检查输入数据合法性
			 */
			strErr1 = this.checkInputValid(info, SETTConstant.TransactionOperate.CANCELCHECK);
			// 抛出自定义异常
			if (strErr1 != null && strErr1.length() > 0)
			{
				throw new IException(strErr1);
			}
			if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE
					|| info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE)
			{
				lCheckStatusID = FSWorkflowManager.getSettCheckStatus(info.getInutParameterInfo());
			}
			else
			{
				lCheckStatusID = SETTConstant.TransactionOperate.SAVE;
			}
			System.out.println("----------=======lCheckStatusID======--------------" + lCheckStatusID);
			info.setStatus(lCheckStatusID);
			info.setModifyDate(Env.getSystemDateTime());
			sett_ReportLossOrFreezeDAO.update(info);
			sett_ReportLossOrFreezeDAO.updateCheckDate(info.getId());
			accountBean.reportLossOrFreeze(info, SETTConstant.TransactionOperate.CANCELCHECK);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO 自动生成 catch 块
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("挂失冻结业务发生", e);
		}
		catch (SettlementException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			lReturn = -1;
			e.printStackTrace();
			throw new SettlementException("其他异常", e);
		}
		return lReturn;
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * 
	 * @param lID
	 * @return ReportLossOrFreezeInfo
	 * @throws IRollbackException
	 */
	public ReportLossOrFreezeInfo findByID(long lID, Connection transConn) throws SettlementException
	{

		ReportLossOrFreezeInfo info = null;
		Sett_ReportLossOrFreezeDAO dao = new Sett_ReportLossOrFreezeDAO(transConn);

		dao.setSelfManagedConn(true);
		try
		{
			info = dao.findById(lID);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException("查询挂失冻结业务发生异常", e);
		}
		return info;

	}

	/**
	 * 提交审批新添加的方法
	 * 
	 * @throws SettlementException
	 * @throws ITreasuryDAOException
	 * 
	 */
	public void initApproval(ReportLossOrFreezeInfo info ,Connection transConn) throws IException
	{

		Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
				"Sett_ReportLossOrFreeze", transConn);
		/**
		 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
		 */
		//sett_ReportLossOrFreezeDAO.setSelfManagedConn(true);
		InutParameterInfo tempInfo = info.getInutParameterInfo();
		tempInfo.setUrl(tempInfo.getUrl() + info.getId());
		tempInfo.setTransID(info.getTransNo());
		tempInfo.setDataEntity(info);
		try
		{
			// 提交审批
			FSWorkflowManager.initApproval(info.getInutParameterInfo());
			// 更新状态到审批中
			sett_ReportLossOrFreezeDAO.updateStatus(info.getId(), SETTConstant.TransactionStatus.APPROVALING, info.getModifyDate());
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SettlementException("提交审批冻结业务发生", e);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new SettlementException("提交审批冻结业务发生", e);
		}
		
		
		// log.debug("------提交审批成功--------");
		System.out.println("提交审批成功====:" + info.getId());
	}

	public Timestamp findModifyDate(long id) throws IException
	{

		return new Sett_ReportLossOrFreezeDAO().findTouchDate(id);

	}
	
	
	/**
	 * added by mzh_fu end
	 * @param info
	 * @param transConn
	 * @throws Exception
	 */
	public void doApproval(ReportLossOrFreezeInfo info,Connection transConn) throws Exception
	{
		Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO("Sett_ReportLossOrFreeze", transConn);
		//sett_ReportLossOrFreezeDAO.setSelfManagedConn(true);
		InutParameterInfo returnInfo = new InutParameterInfo();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		try
		{	
			
			ReportLossOrFreezeInfo freezeInfo = new ReportLossOrFreezeInfo();
			freezeInfo = sett_ReportLossOrFreezeDAO.findById(info.getId());
			inutParameterInfo.setDataEntity(freezeInfo);
			
			//提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				sett_ReportLossOrFreezeDAO.updateStatus(info.getId(),SETTConstant.TransactionStatus.APPROVALED, info.getModifyDate());
				//				如果是自动复核
				if(FSWorkflowManager.isAutoCheck())
				{
					ReportLossOrFreezeInfo freezeInfo1 = sett_ReportLossOrFreezeDAO.findById(info.getId());
					freezeInfo1.setCheckUserId(freezeInfo1.getInputUserId());
					freezeInfo1.setCheckDate(DataFormat.getDateTime(Env.getSystemDateString(info.getOfficeId(), info.getCurrencyId())));
					check(freezeInfo1,transConn);
				}
			}
			else if(returnInfo.isRefuse())
			{
				sett_ReportLossOrFreezeDAO.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE, info.getModifyDate());
			}
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SettlementException("审批冻结业务发生", e);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new SettlementException("审批冻结业务发生", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new SettlementException("审批冻结业务发生", e);
		}
		
		
	}
	
	
	
	/**
	 * 取消审批方法（特殊）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws Exception 
	 */
	public void cancelApproval(ReportLossOrFreezeInfo info,Connection transConn)throws Exception
	{
		long lReturn = -1;
		//long lCheckStatus = -1;
		Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO("Sett_ReportLossOrFreeze", transConn);
		//sett_ReportLossOrFreezeDAO.setSelfManagedConn(true);
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SessionMng sessionMng  = inutParameterInfo.getSessionMng();
		try
		{
			
			ReportLossOrFreezeInfo freezeInfo = new ReportLossOrFreezeInfo();
			freezeInfo = sett_ReportLossOrFreezeDAO.findById(info.getId());
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && freezeInfo.getStatus() == SETTConstant.TransactionStatus.CHECK)
			{
			   lReturn = this.cancelCheck(freezeInfo,transConn);
			   sett_ReportLossOrFreezeDAO.updateStatus(info.getId(),SETTConstant.TransactionStatus.SAVE,info.getModifyDate());
			}
			else
			{
				sett_ReportLossOrFreezeDAO.updateStatus(info.getId(),SETTConstant.TransactionStatus.SAVE,info.getModifyDate());
			}
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}	
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SettlementException("取消审批冻结业务发生", e);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new SettlementException("取消审批冻结业务发生", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new SettlementException("取消审批冻结业务发生", e);
		}
	
	}
	
	public double getAmountSumForQuery(QuerySubjectInfo info,long transdirection) throws RemoteException,
	IException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		String str1 =null;
		double sumamount = 0.0;

		try {
			getSubjectInfoSQL(info);
			str1="select sum(mamount) sumamount from ( " ;
			con = Database.getConnection();
			// 查询
			strSQL = str1+"select"+m_sbSelect+" from "+m_sbFrom+"where"+m_sbWhere+m_sbOrderBy+")where ntransdirection=? ";
            System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, transdirection);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				sumamount = rs.getDouble("sumamount"); // 金额合计
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;

		} catch (Exception e) {
			
			throw new IException("Gen_E001");
		}

		finally {                                  
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
			
				throw new IException("Gen_E001");
			}
		}

		return sumamount ;
	}
	
	
	/**
	 * 查询-科目明细查询
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader querySubjectInfo(QuerySubjectInfo qInfo) throws Exception
	{

		getSubjectInfoSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.reportlossorfreeze.dataentity.QuerySubjectInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	public void getSubjectInfoSQL(QuerySubjectInfo info)
	{

		// TODO 自动生成方法存根
		m_sbSelect = new StringBuffer();
		// select
		m_sbSelect.append("* from (");
		m_sbSelect.append(" select distinct  sy.id,sy.ssubjectcode,sy.stransno,sy.ntransactiontypeid,sy.ntransdirection,sy.mamount,sn.execute Dtexecute ,sn.intereststart Dtintereststart,");
		m_sbSelect.append(" sy.sabstract,sy.ninputuserid,sy.ncheckuserid,''as OPPACCOUNTNO,'' as OPPACCOUNTNAME,u1.sname  InputUserName ,u2.sname CheckUserName,");
		m_sbSelect.append(" decode( sa.sname,'',sh.SENTERPRISENAME,null,sh.SENTERPRISENAME,sa.sname) AccountName ,");
		m_sbSelect.append(" decode( sa.saccountno,'',sh.SBANKACCOUNTCODE,null,sh.SBANKACCOUNTCODE, sa.saccountno) AccountNo,");
		m_sbSelect.append(" st.sextclientname,st.sextaccountno, st.nreceiveaccountid,st.npayaccountid,st.nbankid ,");
		m_sbSelect.append(" sh.SENTERPRISENAME, sh.SBANKACCOUNTCODE");
		// from
		m_sbFrom = new StringBuffer();

		m_sbFrom.append("  sett_glentry sy,sett_transaccountdetail sl ,userinfo u1 ,userinfo u2 , sett_account sa, sett_transcurrentdeposit st,\n");
		m_sbFrom.append("  sett_vtransaction  sn, sett_branch   sh ");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  sy.nstatusid = 3   and sy.stransno = sl.stransno(+) \n");
		m_sbWhere.append("  and sy.mamount = sl.mamount(+) \n");
		m_sbWhere.append("  and sy.ntransdirection=sl.ntransdirection(+) \n");
		m_sbWhere.append("  and sn.inputuserid=u1.id(+)  and sn.checkuserid=u2.id(+)  and sl.ntransaccountid=sa.id(+)\n");
		m_sbWhere.append("  and sl.stransno=st.stransno(+) \n");
		m_sbWhere.append("  and sy.stransno=sn.transno(+) and sn.BankID=sh.id(+)  \n");
		m_sbWhere.append("  and sy.ntransactiontypeid not in (1,2) \n");//不是银行付款或者银行收款

		
		m_sbWhere.append("   UNION select  distinct sy.id ,sy.ssubjectcode,sy.stransno,sy.ntransactiontypeid,sy.ntransdirection,sy.mamount,sy.dtexecute,sy.dtintereststart,");
		m_sbWhere.append("   sy.sabstract,sy.ninputuserid,sy.ncheckuserid,sl.OPPACCOUNTNO,sl.OPPACCOUNTNAME,u1.sname  InputUserName ,u2.sname CheckUserName,");
		m_sbWhere.append("   sa.sname  AccountName ,sa.saccountno  AccountNo, st.SEXTCLIENTNAME,st.SEXTACCOUNTNO, st.NRECEIVEACCOUNTID,st.NPAYACCOUNTID,st.NBANKID,");
		m_sbWhere.append("   sh.senterprisename,sh.sbankaccountcode");
		m_sbWhere.append("	 from  sett_glentry sy,sett_transaccountdetail sl ,userinfo u1 ,userinfo u2 , sett_account sa, sett_transcurrentdeposit st,\n");
		m_sbWhere.append("   sett_branch   sh");
		m_sbWhere.append("  where sy.nstatusid = 3 and sy.stransno = sl.stransno(+) \n");
		//m_sbWhere.append("  and sy.mamount = sl.mamount(+) \n");
		//m_sbWhere.append("  and sy.ntransdirection=sl.ntransdirection(+) \n");
		m_sbWhere.append("  and sy.ninputuserid=u1.id(+)  and sy.ncheckuserid=u2.id(+)  and sl.ntransaccountid=sa.id(+)\n");
		m_sbWhere.append("  and sl.stransno=st.stransno(+) \n");
		m_sbWhere.append("  and st.NBANKID=sh.id(+)\n");
		m_sbWhere.append("  and sy.ntransactiontypeid  in (1,2) \n");//银行收款或者银行付款

		m_sbWhere.append(") ");
		m_sbWhere.append(" where 1=1 ");
		if (info.getStartDate() != null)
		{
			m_sbWhere.append("     and   dtexecute >= to_date('" + DataFormat.getDateString(info.getStartDate())
					+ "','yyyy-mm-dd')   \n");
		}
		if (info.getEndDate() != null)
		{
			m_sbWhere.append("        and dtexecute <= to_date('" + DataFormat.getDateString(info.getEndDate())
					+ "','yyyy-mm-dd')   \n");
		}	
		if(info.getDtintereststartFrom()!=null)
		{
			m_sbWhere.append("     and   Dtintereststart >= to_date('" + DataFormat.getDateString(info.getDtintereststartFrom())
					+ "','yyyy-mm-dd')   \n");			
		}
		
		if(info.getDtintereststartTo()!=null)
		{
			m_sbWhere.append("     and   Dtintereststart <= to_date('" + DataFormat.getDateString(info.getDtintereststartTo())
					+ "','yyyy-mm-dd')   \n");			
		}		
		if(!info.getStrTransNoFrom().equals(""))
		{
			m_sbWhere.append(" and stransno >= '"+info.getStrTransNoFrom()+"'");
		}
		
		if(!info.getStrTransNoTo().equals(""))
		{
			m_sbWhere.append(" and stransno <= '"+info.getStrTransNoTo()+"'");
		}
		
		if(!info.getStrTransactionType().equals(""))
		{
			m_sbWhere.append(" and ntransactiontypeid in ("+info.getStrTransactionType()+")");
		}
		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by  stransno,ntransdirection,mamount\n " );
		
		System.out.println("打印出sql------------"+"select"+m_sbSelect+" from "+m_sbFrom+"where"+m_sbWhere+m_sbOrderBy);
		
		
	}	

	
}
































