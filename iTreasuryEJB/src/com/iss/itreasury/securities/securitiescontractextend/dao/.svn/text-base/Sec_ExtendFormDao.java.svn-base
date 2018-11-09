package com.iss.itreasury.securities.securitiescontractextend.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiescontract.dao.SecuritiesContractDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.ContractBalanceInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.securitiescontractextend.dataentity.SecuritiesContractExtendInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.bizlogic.SecuritiesGeneralLedgerOperation;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.securities.stock.bizlogic.StockOperation;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @Name: Sec_ExtendFormDao.java @Description: 委托理财合同展期 @Author: gqfang @Create
 * Date: 2005-4-19 To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class Sec_ExtendFormDao extends SecuritiesDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public Sec_ExtendFormDao()
	{
		super("Sec_ExtendForm");
		// TODO Auto-generated constructor stub
	}

	//取合同的展期序列号
	public String getSerialNo(long applyContractId) throws SecuritiesDAOException
	{
		StringBuffer sql = new StringBuffer();
		long lSerial = -1;
		String strSerialNo = "";

		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}

		sql.append(" select nvl(max(serialno) + 1,1) serialno from sec_extendform  \n  ");
		sql.append("  where statusId > 0   and  applyContractId =  " + applyContractId);

		log4j.debug(sql.toString());
		try
		{
			prepareStatement(sql.toString());
			ResultSet rs = executeQuery();
			if (rs != null && rs.next())
			{
				lSerial = rs.getLong(1);

				strSerialNo = "0" + lSerial;
			}

			sql.setLength(0);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("取合同的展期序列号产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("取合同的展期序列号产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		log4j.debug(":::::::::: ::::合同的展期序列号::::::" + strSerialNo);

		return strSerialNo;
	}

	//取得展期合同号
	public String getContractCode(long lTransactionTypeID) throws SecuritiesDAOException
	{
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2, 4);
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		strSQL = " select max(nvl(Code,0)) Code from Sec_ApplyContract where Code like 'HT" + strYear + lTransactionTypeID + "%'";
		log4j.debug(strSQL);
		try
		{
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				log4j.debug(strCode);
				if (strCode != null && strCode.length() == 12)
				{
					lCode = Long.parseLong(strCode.substring(9)) + 1;
				}
				else
				{
					lCode = 1;
				}
				strCode = "HT" + strYear + lTransactionTypeID + DataFormat.formatInt(lCode, 3, true);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("生成展期合同编号产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("生成展期合同编号产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		log4j.debug(":::::::::: ::::委托理财展期合同编号::::::" + strCode);
		return strCode;
	}
	//取得合同当前余额
	public ContractBalanceInfo getContractBalance(long contractID) throws SecuritiesDAOException
	{
		String strSQL = "";
		ContractBalanceInfo info = new ContractBalanceInfo();
		double totalInterest = 0;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			strSQL = "select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n" + " from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+ " where bb.id(+)=aa.nsecuritiesnoticeid\n" + " and cc.id(+)=bb.contractid\n" + " and dd.id(+)=bb.transactiontypeid\n" + " and aa.nstatusid=3\n"
					+ " and dd.capitaldirection in (4,7)\n" + " and cc.id=" + contractID;
			log.print(strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next())
			{
				info.setTotalReceivedAmount(rs.getDouble(1) - rs.getDouble(2));
				System.out.println("sum(mAmount)-----" + rs.getDouble(1));
				System.out.println("sum(mInterest)-----" + rs.getDouble(2));
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			strSQL = "select sum(aa.mAmount),sum(bb.NOTICEINTEREST)\n" + " from Sett_Transsecurities aa,Sec_Noticeform bb,sec_applycontract cc,Sec_Transactiontype dd\n"
					+ " where bb.id(+)=aa.nsecuritiesnoticeid\n" + " and cc.id(+)=bb.contractid\n" + " and dd.id(+)=bb.transactiontypeid\n" + " and aa.nstatusid=3\n"
					+ " and dd.capitaldirection in (5,6)\n" + " and cc.id=" + contractID;
			log.print(strSQL);
			prepareStatement(strSQL);
			rs = executeQuery();
			if (rs != null && rs.next())
			{
				System.out.println("sum(mAmount)-----" + rs.getDouble(1));
				System.out.println("sum(mInterest)-----" + rs.getDouble(2));
				info.setTotalPaiedAmount(rs.getDouble(1) - rs.getDouble(2));
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			info.setBalance(Math.abs(info.getTotalReceivedAmount() - info.getTotalPaiedAmount()) + totalInterest);
			System.out.println("Balance-----" + info.getBalance());
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("取合同余额时产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("取合同余额时产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return info;
	}
	/**
	 * 修改查找和审核查找
	 * 
	 * @param qInfo
	 * @return @throws
	 * SecuritiesDAOException
	 */
	public Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws SecuritiesDAOException
	{
		Collection c = null;
		String strSQL = "";
		ApprovalBiz appBiz = new ApprovalBiz();
		String strUser = "";
		long lModuleID = -1;
		long lLoanTypeID = -1;
		long lActionID = -1;
		long lApprovalID = -1;
		long queryPurpose = qInfo.getQueryPurpose();
		
//		zpli add 2005-09-14
		long lOfficeID=qInfo.getOfficeId();
		long lCurrencyID=qInfo.getCurrencyId();
		////
		
		/*-----------------init DAO --------------------*/
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/
		try
		{
			//获得ApprovalID
			if (lApprovalID <= 0)
			{
				//zpli modify 2005-09-14
				lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(), 1,lOfficeID,lCurrencyID);
				//lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(), 1);
			}
		}
		catch (Exception e1)
		{
			log4j.error("getApprovalID fail");
			e1.printStackTrace();
		}
		try
		{
			
			//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
			//zpli modify 2005-09-14			
			strUser = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID, lOfficeID, lCurrencyID, qInfo.getUserId());
			//strUser = appBiz.findTheVeryUser(lApprovalID, qInfo.getUserId());
		
		}
		catch (Exception e2)
		{
			log4j.error("findTheVeryUser fail");
			e2.printStackTrace();
		}
		try
		{
			strSQL = "select aa.*,bb.boldScale from SEC_ApplyContract aa, \n" + " (\n" + "  	 select contractFormID,sum(amount) as boldScale \n" + "			from SEC_ContractFormBondType \n"
					+ "			group by contractFormID \n" + " ) bb \n" + " where 1=1 \n" + " and bb.contractFormID(+)=aa.ID \n";
			if (queryPurpose == 1) //for modify
			{
				strSQL += " and ( (StatusID = " + SECConstant.ContractStatus.CHECK + " and InputUserID = " + qInfo.getUserId() + " ) or (StatusID = " + SECConstant.ContractStatus.SAVE
						+ " and InputUserID = " + qInfo.getUserId() + " ) or (statusID=" + SECConstant.ContractStatus.SUBMIT + " " + " and InputUserID = " + qInfo.getUserId()
						+ " and NextCheckUserID = " + qInfo.getUserId() + " ) )";
				if (qInfo.getTransactionTypeId() > 0 && qInfo.getTransactionTypeId() < 1000)
				{
					String tmpSQL = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedApplyForm = 1 and BusinessTypeID = " + qInfo.getTransactionTypeId();
					strSQL += " and transactionTypeID in (" + NameRef.getNamesByID(tmpSQL, "ID") + ") ";
				}
				else if (qInfo.getTransactionTypeId() > 1000)
				{
					strSQL += " and transactionTypeID = " + qInfo.getTransactionTypeId();
				}
				if (qInfo.getStatusId() == SECConstant.ContractStatus.SUBMIT)
				{
					strSQL += " and NextCheckUserID = " + qInfo.getUserId();
					strSQL += " and StatusID = " + SECConstant.ContractStatus.SUBMIT;
				}
			}
			else if (queryPurpose == 2) //for check
			{
				strSQL += " and NextCheckUserID in " + strUser + " and TransactionTypeID = " + qInfo.getTransactionTypeId() + " and StatusID = " + SECConstant.ContractStatus.SUBMIT;
			}
			else if (queryPurpose == 3) //for active
			{
				strSQL += " and statusID=" + SECConstant.ContractStatus.CHECK + " and inputUserID=" + qInfo.getUserId();
				if (qInfo.getTransactionTypeId() > 0 && qInfo.getTransactionTypeId() < 1000)
				{
					String tmpSQL = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedApplyForm = 1 and BusinessTypeID = " + qInfo.getTransactionTypeId();
					strSQL += " and transactionTypeID in (" + NameRef.getNamesByID(tmpSQL, "ID") + ") ";
				}
				else if (qInfo.getTransactionTypeId() > 1000)
				{
					strSQL += " and transactionTypeID = " + qInfo.getTransactionTypeId();
				}
			}
			else if (queryPurpose == 4) //for end contract
			{
				strSQL += " and statusID=" + SECConstant.ContractStatus.ACTIVE + " and inputUserID=" + qInfo.getUserId() + " and transactionTypeID="
						+ SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING;
			}
			else if (queryPurpose == 5) //合同权限转移-合同查找
			{
				strSQL += " and statusID !=" + SECConstant.ContractStatus.CANCEL;
				strSQL += " and statusID !=" + SECConstant.ContractStatus.REFUSE;
				if (qInfo.getTransactionTypeId() > 0)
				{
					strSQL += " and transactionTypeID=" + qInfo.getTransactionTypeId();
				}
				if (qInfo.getInputUserId() > 0)
				{
					strSQL += " and inputUserID=" + qInfo.getInputUserId();
				}
			}
			if (qInfo.getClientId() != -1)
			{
				strSQL += " and ClientID = " + qInfo.getClientId();
			}
			if (qInfo.getCounterpartId() != -1)
			{
				strSQL += " and CounterpartID = " + qInfo.getCounterpartId();
			}
			if (qInfo.getStartAmount() != 0)
			{
				strSQL += " and Amount >= " + qInfo.getStartAmount();
			}
			if (qInfo.getEndAmount() != 0)
			{
				strSQL += " and Amount <= " + qInfo.getEndAmount();
			}
			if (qInfo.getStartContractId() != -1)
			{
				strSQL += " and ID>=" + qInfo.getStartContractId();
			}
			if (qInfo.getEndContractId() != -1)
			{
				strSQL += " and ID<=" + qInfo.getEndContractId();
			}
			if (qInfo.getIntervalNum() != -1)
			{
				strSQL += " and term=" + qInfo.getIntervalNum();
			}
			if (qInfo.getSettlementTypeId() != -1)
			{
				strSQL += " and settlementTypeID=" + qInfo.getSettlementTypeId();
			}
			if (qInfo.getInterestTypeId() != -1)
			{
				strSQL += " and interestTypeID= " + qInfo.getInterestTypeId();
			}
			if (qInfo.getCommissionChargeRate() != 0)
			{
				strSQL += " and commissionChargeRate=" + qInfo.getCommissionChargeRate();
			}
			if (qInfo.getBeginBoldScale() != 0)
			{
				strSQL += " and sumBold>=" + qInfo.getBeginBoldScale();
			}
			if (qInfo.getEndBoldScale() != 0)
			{
				strSQL += " and sumBold<=" + qInfo.getEndBoldScale();
			}
			if (qInfo.getStartBeginDate() != null)
			{
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartBeginDate()) + "'";
			}
			if (qInfo.getEndBeginDate() != null)
			{
				strSQL += " and to_char(TransactionStartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndBeginDate()) + "'";
			}
			if (qInfo.getStartEndDate() != null)
			{
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartEndDate()) + "'";
			}
			if (qInfo.getEndEndDate() != null)
			{
				strSQL += " and to_char(TransactionEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndEndDate()) + "'";
			}
			if (qInfo.getStartInputDate() != null)
			{
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartInputDate()) + "'";
			}
			if (qInfo.getEndInputDate() != null)
			{
				strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndInputDate()) + "'";
			}
			if (qInfo.getStatusId() > 0)
			{
				strSQL += " and statusID=" + qInfo.getStatusId();
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			int nIndex = 0;
			String orderParamString = qInfo.getOrderParamString();
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0)
			{
				if (orderParamString.substring(0, nIndex).toLowerCase().equals("sec_applycontract"))
				{
					strSQL += " order by aa." + orderParamString.substring(nIndex + 1);
				}
			}
			else
			{
				strSQL += " order by aa.ID";
			}
			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			prepareStatement(strSQL);
			executeQuery();
			c = getDataEntitiesFromResultSet(SecuritiesContractInfo.class);
		}
		catch (Exception e)
		{
			throw new SecuritiesDAOException("查找合同时产生错误", e);
		}
		/*----------------finalize Dao-----------------*/
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/
		return (c.size() > 0 ? c : null);
	}
	//审核展期合同
	public long check(ApprovalTracingInfo ATInfo) throws SecuritiesDAOException, Exception
	{
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";
		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();

		SecuritiesContractExtendInfo aInfo = new SecuritiesContractExtendInfo();
		SecuritiesContractExtendInfo eInfo = new SecuritiesContractExtendInfo();
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();
		SecuritiesContractExtendInfo tmpInfo = new SecuritiesContractExtendInfo();

		long applyContractID = -1;
		eInfo = (SecuritiesContractExtendInfo) dao.findByID(lApprovalContentID, eInfo.getClass());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
		{
			try
			{
				tmpInfo = (SecuritiesContractExtendInfo) this.findByID(lApprovalContentID, tmpInfo.getClass());
				applyContractID = tmpInfo.getId();
			}
			catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.REFUSE);
			try
			{
				update(aInfo);
				dao.updateStatus(applyContractID, SECConstant.ApplyFormStatus.REJECTED);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.SUBMIT);
			aInfo.setNextCheckUserId(lNextUserID);
			aInfo.setNextCheckLevel(getNextCheckLevelByApplyID(lApprovalContentID) + 1);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
		{
			System.out.println("1      PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.CHECK);
			aInfo.setNextCheckUserId(lNextUserID);
			aInfo.setApplyContractId( eInfo.getApplyContractId()  );
			aInfo.setExtendEndDate(eInfo.getExtendEndDate());
			
			try
			{
				System.out.println("2       PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
				//更新展期合同的状态
				update(aInfo);
				System.out.println("3       PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
				//展期合同审核通过之后对原合同的操作
				doAfterCheckOver( aInfo );
				System.out.println("4       PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.SUBMIT);
			aInfo.setNextCheckUserId(ATInfo.getInputUserID());
			aInfo.setNextCheckLevel(1);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		log4j.debug("check end");
		return lApprovalContentID;
	}
	//展期合同审核通过之后，更新原合同的状态、到期日
	private void doAfterCheckOver( SecuritiesContractExtendInfo info ) throws SecuritiesDAOException, SQLException
	{
		StringBuffer sbUpdate = new StringBuffer();
		Date newContractEndDate = null;
		
		//日期格式：Timestamp 转换成 Date
		newContractEndDate = DataFormat.getDate(  DataFormat.formatDate( getContractEndDate(info) )  ) ;
		
		try
		{
			//initDAO();
			
			sbUpdate.append(" UPDATE SEC_APPLYCONTRACT SET transactionEndDate = to_date('" + newContractEndDate + "' ,'yyyy-mm-dd')");
			sbUpdate.append("  , statusId = "+SECConstant.ContractStatus.EXTEND);
			sbUpdate.append("  WHERE ID = "+info.getApplyContractId());
			
			System.out.println(sbUpdate.toString());
			
			prepareStatement(sbUpdate.toString());
			
		    executeUpdate();
		    
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		
	}
	
	
	/**
	 * 获得该申请书的当前审批级别
	 * 
	 * @param applyId
	 * @return @throws
	 * SecuritiesDAOException
	 */
	private long getNextCheckLevelByApplyID(long applyId) throws SecuritiesDAOException
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nextCheckLevel from SEC_APPLYCONTRACT where 1 = 1 ";
		strSQL += " and id = " + applyId;
		try
		{
			initDAO();
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			try
			{
				while (rs != null && rs.next())
				{
					nextCheckLevel = rs.getLong("nextCheckLevel");
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return nextCheckLevel;
	}
	/**
	 * 根据原合同Id得到展期后，原合同的新的到期日
	 * 
	 * @return contractId
	 */
	private Timestamp getContractEndDate( SecuritiesContractExtendInfo info ) throws SecuritiesDAOException, SQLException
	{
		Timestamp tsEndDate = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT transactionEndDate FROM SEC_APPLYCONTRACT WHERE ID = "+info.getApplyContractId());
		System.out.println(sb.toString());
		try
		{
			initDAO();
			prepareStatement(sb.toString());
			ResultSet rs = executeQuery();
			if( rs != null && rs.next() )
			{
				tsEndDate = rs.getTimestamp("transactionEndDate");
				
				System.out.println("**********  tsEndDate = "+tsEndDate);
				System.out.println("**********  info.getExtendEndDate() = "+info.getExtendEndDate());
				
				if( tsEndDate.before( info.getExtendEndDate() ) )
				{
					tsEndDate = info.getExtendEndDate();
				}
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		//try
		//{
		//	finalizeDAO();
		//}
		//catch (ITreasuryDAOException e)
		//{
		//	throw new SecuritiesDAOException(e);
		//}
		return tsEndDate;
	}
	/**
	 * 生成会计分录
	 * 
	 * @param info
	 * @param sumAmount
	 * @throws Exception
	 */
	private void generateGLEntry(SecuritiesContractInfo info, double sumAmount, Connection conn) throws java.rmi.RemoteException, SecuritiesException
	{
		try
		{
			log4j.info("生成合同会计分录");
			GenerateGLEntryParam generateGLEntryParam;
			SecuritiesGeneralLedgerOperation securitiesGeneralLedgerOperation = new SecuritiesGeneralLedgerOperation(conn);
			generateGLEntryParam = new GenerateGLEntryParam();
			generateGLEntryParam.setNetIncome(sumAmount);
			generateGLEntryParam.setAccountType(SECConstant.EntryAccountType.AccountType_13);
			generateGLEntryParam.setOfficeID(info.getOfficeId());
			generateGLEntryParam.setCurrencyID(info.getCurrencyId());
			generateGLEntryParam.setInputUserID(info.getInputUserId());
			generateGLEntryParam.setCheckUserID(info.getInputUserId());
			generateGLEntryParam.setTransactionType(SECConstant.BusinessType.BOND_UNDERWRITING.CONTRACT_END);
			generateGLEntryParam.setSubTransactionType(info.getInterestTypeId());
			generateGLEntryParam.setDeliverOrderCode(info.getCode());
			//generateGLEntryParam.setSuspenseInterest(dContractInterest);
			generateGLEntryParam.setExecuteDate(Env.getSystemDate());
			generateGLEntryParam.setDeliveryDate(Env.getSystemDate());
			generateGLEntryParam.setTransactionDate(Env.getSystemDate());
			securitiesGeneralLedgerOperation.generateGLEntry(generateGLEntryParam);
			log4j.debug("生成合同会计分录成功");
		}
		catch (SecuritiesException e)
		{
			log4j.debug("生成合同会计分录失败");
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 通过合同id转入证券库存
	 * 
	 * @param lContractID
	 * @return @throws
	 * SecuritiesDAOException
	 */
	public void saveStockByContractId(long lContractID) throws java.rmi.RemoteException, SecuritiesException
	{
		System.out.println("heredao");
		String strSQL = "";
		Vector v = new Vector();
		double sumAmount = 0.0;//交易合计,会计分录用
		SecuritiesContractDao dao = new SecuritiesContractDao();
		SecuritiesContractInfo cInfo = new SecuritiesContractInfo();
		//获得合同的帐户和clientid
		try
		{
			cInfo = (SecuritiesContractInfo) dao.findByID(lContractID, cInfo.getClass());
		}
		catch (ITreasuryDAOException e1)
		{
			e1.printStackTrace();
		}
		//只有债券承销需要进行这些操作
		if (cInfo.getTransactionTypeId() != SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
		{
			log4j.debug("transactionTypeId=" + cInfo.getTransactionTypeId());
			return;
		}
		StockOperation operation = new StockOperation();
		//库存操作的entity
		SecuritiesStockParam param = new SecuritiesStockParam();
		param.setAccountID(cInfo.getAccountId());
		param.setClient(cInfo.getClientId());
		param.setDeliveryDate(Env.getSystemDate());
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		strSQL = "select securitiesid,nvl(nvl(sum(receivedNumber),0)-nvl(sum(saledNumber),0),0) balanceNumber,nvl(nvl(sum(receivedAmount),0)-nvl(sum(saledAmount),0),0) balanceAmount " + " from("
				+ " select a.id,a.securitiesid,a.ratename,a.facesumamount/100 receivedNumber,facesumamount receivedAmount," + " d.saledNumber,d.saledAmount " + " from sec_noticeWithSecurities a,"
				+ " sec_noticeform b," + " sec_applycontract c," + " (select sum(faceSumAmount/100) saledNumber,sum(faceSumAmount) saledAmount,relatedId "
				+ " from sec_noticewithsecurities aa,sec_noticeform bb" + " where aa.noticeid = bb.id" + " and bb.statusid = " + SECConstant.NoticeFormStatus.CHECKED + " group by relatedId)d "
				+ " where " + " a.noticeid = b.id " + " and a.statusid = " + Constant.RecordStatus.VALID + " and b.contractid = c.id " + " and c.id = " + lContractID + " and b.statusid = "
				+ SECConstant.NoticeFormStatus.CHECKED + " and b.transactiontypeid = " + SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY + " and a.id = d.relatedId(+) "
				+ " ) group by securitiesid ";
		log4j.debug(strSQL);
		try
		{
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next())
			{
				param.setSecuritiesID(rs.getLong("securitiesid"));
				param.setQuantity(rs.getDouble("balanceNumber"));
				param.setAmount(rs.getDouble("balanceAmount"));
				if (param.getAmount() > 0)
				{
					sumAmount += param.getAmount();
					operation.enterStock(param);
				}
			}
			//生成会计分录
			if (sumAmount > 0)
			{
				generateGLEntry(cInfo, sumAmount, this.transConn);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("通过合同id转入证券库存产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("通过合同id转入证券库存产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
}