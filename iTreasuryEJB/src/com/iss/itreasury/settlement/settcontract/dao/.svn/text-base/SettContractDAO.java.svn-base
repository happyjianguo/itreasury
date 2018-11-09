/*
 * Created on 2004-9-8
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.settlement.settcontract.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractAmountInfo;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettContractDAO extends SettlementDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	String m_sbSelect = null;
	String m_sbFrom = null;
	String m_sbWhere = null;
	String m_sbOrderBy = null;
	
	public SettContractDAO(String strTable)
	{
		//super("Loan_LoanForm");
		super(strTable, true);
	}
	/**
	 *合同的多笔查询操作
	 */
	public Collection findByMultiOption(SettContractQueryInfo qInfo) throws java.rmi.RemoteException, SettlementDAOException
	{
		Vector v = new Vector();
		StringBuffer sbCount = new StringBuffer();
		StringBuffer sbSelect = new StringBuffer();
		StringBuffer sbSQL = new StringBuffer();
		StringBuffer sbOrder = new StringBuffer();
		String strSQL = "";
		long queryPurpose = qInfo.getActionID();
		long officeID = qInfo.getOfficeID();
		long currencyID = qInfo.getCurrencyID();
		long loanTypeID = qInfo.getLoanTypeID();
		long loanSubTypeID = qInfo.getLoanSubTypeID();
		long clientID = qInfo.getClientID();
		long consignClientID = qInfo.getConsignClientID();
		long statusID = qInfo.getStatusID();
		long userID = qInfo.getUserID();
		long inputUserID = qInfo.getInputUserID();
		long contractIDFrom = qInfo.getContractIDFrom();
		long contractIDTo = qInfo.getContractIDTo();
		long intervalNum = qInfo.getIntervalNum();
		double amountFrom = qInfo.getAmountFrom();
		double amountTo = qInfo.getAmountTo();
		Timestamp loanStart = qInfo.getLoanStart();
		Timestamp loanEnd = qInfo.getLoanEnd();
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		double totalAmount = 0.0;
		long recordCount = -1;
		long pageCount = -1;
		long rowNumStart = -1;
		long rowNumEnd = -1;
		try
		{
			initDAO();
			//计算记录总数   
			sbCount.setLength(0);
			sbCount.append(" \n select count(*),sum(aa.mExamineAmount) ");
			sbSQL.setLength(0);
			sbSQL.append(" \n from Loan_ContractForm aa ");
			sbSQL.append(" \n where 1 = 1 ");
			if (queryPurpose == 1) //for modify
			{
				sbSQL.append(" \n and aa.nInputUserID = " + userID);
				sbSQL.append(" \n and aa.nStatusID in ( " + SETTConstant.SettContractStatus.SAVE + "," + SETTConstant.SettContractStatus.SUBMIT + "," + SETTConstant.SettContractStatus.CHECK + ","
						+ SETTConstant.SettContractStatus.NOTACTIVE + ")");
			}
			if (queryPurpose == 2) //for examine
			{
				sbSQL.append(" \n and aa.nStatusID = " + SETTConstant.SettContractStatus.SUBMIT);
			}
			if (loanTypeID > 0)
			{
				sbSQL.append(" \n and aa.nTypeID = " + loanTypeID);
			}
			if (loanSubTypeID > 0)
			{
				sbSQL.append(" \n and aa.nSubTypeID = " + loanSubTypeID);
			}			
			
			if (clientID > 0)
			{
				sbSQL.append(" \n and aa.nBorrowClientID = " + clientID);
			}
			if (consignClientID > 0)
			{
				sbSQL.append(" \n and aa.nConsignClientID = " + consignClientID);
			}
			//		不做人员的限制
			//      if (inputUserID > 0)
			//      {
			//          sbSQL.append(" \n and aa.nInputUserID = " + inputUserID);
			//      }
			if (contractIDFrom > 0)
			{
				sbSQL.append(" \n and aa.ID >= " + contractIDFrom);
			}
			if (contractIDTo > 0)
			{
				sbSQL.append(" \n and aa.ID <= " + contractIDTo);
			}
			if (intervalNum > 0)
			{
				sbSQL.append(" \n and aa.nIntervalNum = " + intervalNum);
			}
			if (currencyID > 0)
			{
				sbSQL.append(" \n and aa.nCurrencyID = " + currencyID);
			}
			if (officeID > 0)
			{
				sbSQL.append(" \n and aa.nOfficeID = " + officeID);
			}
			if (statusID > 0)
			{
				sbSQL.append(" \n and aa.nStatusID = " + statusID);
			}
			if (amountFrom > 0)
			{
				sbSQL.append(" \n and aa.mExamineAmount >= " + amountFrom);
			}
			if (amountTo > 0)
			{
				sbSQL.append(" \n and aa.mExamineAmount <= " + amountTo);
			}
			if (loanStart != null)
			{
				sbSQL.append(" \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(loanStart) + "'");
			}
			if (loanEnd != null)
			{
				sbSQL.append(" \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(loanEnd) + "'");
			}
			////////////////////////////排序处理/////////////////
			sbOrder.setLength(0);
			int nIndex = 0;
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0)
			{
				if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_contractform"))
				{
					sbOrder.append(" \n order by aa." + orderParamString.substring(nIndex + 1));
				}
			}
			else
			{
				sbOrder.append(" \n order by aa.ID ");
			}
			if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				sbOrder.append(" desc ");
			}
			strSQL = sbCount.toString() + sbSQL.toString();
			log4j.debug(strSQL);
			try
			{
				prepareStatement(strSQL);
				ResultSet rs = executeQuery();
				if (rs != null && rs.next())
				{
					recordCount = rs.getLong(1);
					totalAmount = rs.getDouble(2);
				}
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException("批量查询合同笔数产生错误", e);
			}
			catch (SQLException e)
			{
				throw new SettlementDAOException("批量查询合同笔数产生错误", e);
			}
			log4j.info("recordCount:" + recordCount);
			log4j.info("totalAmount:" + DataFormat.formatAmount(totalAmount));
			pageCount = recordCount / pageLineCount;
			if ((recordCount % pageLineCount) != 0)
			{
				pageCount++;
			}
			//pageInfo[0] = recordCount;
			//pageInfo[1] = pageCount;
			//v.add(pageInfo);
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			rowNumStart = (pageNo - 1) * pageLineCount + 1;
			rowNumEnd = rowNumStart + pageLineCount - 1;
			sbSelect.append(" \n select id from ( select aa.*,rownum r from ( select * ");
			sbOrder.append(" \n ) aa ) where r between " + rowNumStart + " and " + rowNumEnd);
			strSQL = sbSelect.toString() + sbSQL.toString() + sbOrder.toString();
			log4j.debug(strSQL);
			try
			{
				SettContractDAO dao = new SettContractDAO(this.strTableName);
				prepareStatement(strSQL);
				ResultSet rs1 = executeQuery();
				while (rs1 != null && rs1.next())
				{
					SettContractInfo info = new SettContractInfo();
					info.setId(rs1.getLong("ID"));
					if (info.getId() > 0)
					{
						info = (SettContractInfo) dao.findByID(info.getId(), info.getClass());
						//当前查找总数
						info.setPageCount(pageCount);
						//info.setPageNo(pageNo);
						//info.setRecordCount(lRecordCount);
						//info.setTotalAmount(totalAmount);
					}
					v.add(info);
				}
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException("批量查询合同产生错误", e);
			}
			catch (SQLException e)
			{
				throw new SettlementDAOException("批量查询合同产生错误", e);
			}
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
		}
		return (v.size() > 0 ? v : null);
	}
	/**
	 *合同的审核操作
	 */
	/*public long check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, SettlementDAOException
	{
		return -1;
	}*/
	/**
	 * 根据贷款种类生成合同编号
	 * 操作数据库表
	 * @return    String   strContractCode         合同编号
	 **/
	public String createContractCode(long lLoanTypeID) throws java.rmi.RemoteException, SettlementDAOException
	{
		ResultSet rs = null;
		String strSelect = "";
		String strYear = "";
		String strLoanCode = "";
		long lMaxCode = 0;
		String strContractCode = "";
		try
		{
			initDAO();
			//取两位的年份
			strSelect = " select to_char(sysdate,'yyyy') from dual ";
			prepareStatement(strSelect);
			rs = executeQuery();
			if (rs.next())
			{
				strYear = rs.getString(1);
				//Log.print("Two Bits Year is: " + strYear);
			}
			rs.close();
			rs = null;
			//计算最大序列号
			//strLoanCode = SETTConstant.SettLoanType.getTypeCode(lLoanTypeID);
			strLoanCode = LOANConstant.SubLoanType.getCode(lLoanTypeID);
			int nLen = strLoanCode.length() + strYear.length();
			strSelect = " select nvl(max(substr(sContractCode,"+ (nLen+1) +",3)),0) + 1 from Loan_ContractForm where 1 = 1 " + " and sContractCode like '" + strYear + strLoanCode + "%'";
			Log.print(strSelect);
			prepareStatement(strSelect);
			rs = executeQuery();
			if (rs.next())
			{
				lMaxCode = rs.getLong(1);
				Log.print("MaxCode is: " + lMaxCode);
			}
			rs.close();
			rs = null;
			finalizeDAO();
			//
			strContractCode = strYear + strLoanCode + DataFormat.formatInt(lMaxCode, 3, true);
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SettlementDAOException(e);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
					rs = null;
					
					finalizeDAO();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return (strContractCode);
	}
	/**
	 * 得到合同当前金额
	 * Create Date: 2003-10-15
	 * @param lContractID 合同ID
	 * @return ContractAmountInfo
	 * @exception Exception
	 */
	public SettContractAmountInfo getLateAmount(long lContractID) throws Exception
	{
		SettContractAmountInfo info = new SettContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			if (lContractID > 0)
			{
				sbSQL.append(" SELECT SUM(a.mOpenAmount) OpenAmount,SUM(a.mBalance) Balance");
				sbSQL.append(" FROM sett_subAccount a,loan_payform b");
				sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				//sbSQL.append(" AND a.nStatusID =" + SETTConstant.SubAccountStatus.NORMAL);
				sbSQL.append(" AND b.nContractID = ? ");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					info.setBalanceAmount(rs.getDouble("Balance")); //合同余额
					info.setOpenAmount(rs.getDouble("OpenAmount")); //合同已发放金额
					info.setRepayAmount(rs.getDouble("OpenAmount") - rs.getDouble("Balance")); //合同已还金额
				}
				ps.close();
				ps = null;
				sbSQL.setLength(0);
				sbSQL.append(" SELECT c.mExamineAmount,c.nTypeID");
				sbSQL.append(" FROM loan_contractform c");
				sbSQL.append(" WHERE c.id = ? ");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				double dTmp = 0;
				if (rs.next())
				{
					/*
					 if (rs.getLong("nTypeID") == LOANConstant.LoanType.ZGXEDQ || rs.getLong("nTypeID") == LOANConstant.LoanType.ZGXEZCQ)
					 {
					 dTmp = rs.getDouble("mExamineAmount") - info.getBalanceAmount();
					 }
					 else
					 {
					 dTmp = rs.getDouble("mExamineAmount") - info.getOpenAmount();
					 }
					 */
					dTmp = rs.getDouble("mExamineAmount") - info.getOpenAmount();
					info.setUnPayAmount(dTmp); //未发放金额
				}
				ps.close();
				ps = null;
				sbSQL.setLength(0);
				sbSQL.append(" SELECT sum(mAmount) as aheadAmount ");
				sbSQL.append(" FROM LOAN_AHEADREPAYFORM");
				sbSQL.append(" WHERE nContractID = ? ");
				sbSQL.append(" and nStatusID in (2,3) ");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				double aheadAmount = 0;
				if (rs.next())
				{
					aheadAmount = rs.getDouble("aheadAmount");
					info.setAheadAmount(aheadAmount);
				}
				ps.close();
				ps = null;
			}
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
		catch (Exception e)
		{
			log4j.error(e.toString());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	public SettContractInfo findByID(long lID)
	{
		SettContractInfo info = null;
		
		try
		{
			info = (SettContractInfo) this.findByID(lID,SettContractInfo.class);
			this.initDAO();
			String strSQL = "select * from " + this.strTableName + " where id="+lID;
			transPS  = this.prepareStatement(strSQL);
			transRS  = this.executeQuery();
			if (transRS.next())
			{
				info.setIsPurchaserInterest(transRS.getLong("IsPurchaserInterest"));
				info.setPurchaserInterestRate(transRS.getDouble("PurchaserInterestRate"));
			}
		} catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return info;
	}
	/**
	 * 查找是否有重复合同编码
	 * 合同资料新增时使用
	 */
	public long checkCode(String code, long clientid)throws Exception
	{  long result=-1;
	   Connection con=null;
	   PreparedStatement ps=null;
	   ResultSet rs=null;
	   String sql="";
	
		try {
			con=Database.getConnection();
			sql="select count(id) from Loan_ContractForm where SCONTRACTCODE='"+code+"' and id!="+clientid;
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if (rs!=null&& rs.next()) {
				result=rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (rs!=null) {
				rs.close();
				rs=null;
				
			}
			if (ps!=null) {
				ps.close();
				ps=null;
				
			}
			if (con!=null) {
				con.close();
				con=null;
				
			}
		}
		return result;
	}
	public long updateDiscountDate(String discountdate, long contractid)throws Exception
	{  long result=-1;
	   Connection con=null;
	   PreparedStatement ps=null;
	   
	   String sql="";
	
		try {
			con=Database.getConnection();
			sql="update Loan_ContractForm set DTDISCOUNTDATE=to_date('"+discountdate+"','mm/dd/yyyy') where id="+contractid;
			
			ps=con.prepareStatement(sql);
			result=ps.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			if (ps!=null) {
				ps.close();
				ps=null;
				
			}
			if (con!=null) {
				con.close();
				con=null;
				
			}
		}
		return result;
	}	
	/**
	 *合同的分页
	 */
	public PageLoader getMultiOptin(SettContractQueryInfo qInfo)throws java.rmi.RemoteException, SettlementException {
		
		long queryPurpose = qInfo.getActionID();
		long officeID = qInfo.getOfficeID();
		long currencyID = qInfo.getCurrencyID();
		long loanTypeID = qInfo.getLoanTypeID();
		long loanSubTypeID = qInfo.getLoanSubTypeID();
		long clientID = qInfo.getClientID();
		long consignClientID = qInfo.getConsignClientID();
		long statusID = qInfo.getStatusID();
		long userID = qInfo.getUserID();
		//long inputUserID = qInfo.getInputUserID();
		long contractIDFrom = qInfo.getContractIDFrom();
		long contractIDTo = qInfo.getContractIDTo();
		long intervalNum = qInfo.getIntervalNum();
		double amountFrom = qInfo.getAmountFrom();
		double amountTo = qInfo.getAmountTo();
		Timestamp loanStart = qInfo.getLoanStart();
		Timestamp loanEnd = qInfo.getLoanEnd();
		//long pageLineCount = qInfo.getPageLineCount();
		//long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		m_sbSelect=" aa.ID,NLOANID,SCONTRACTCODE ContractCode,ISEXTEND,DTACTIVE,aa.NTYPEID TypeID,aa.NCURRENCYID,aa.NOFFICEID,SAPPLYCODE,NCONSIGNCLIENTID ConsignClientID," +
				"NBORROWCLIENTID BorrowClientID,MLOANAMOUNT,SLOANREASON,SLOANPURPOSE,SOTHER,NISCIRCLE,NISSALEBUY,NISTECHNICAL,aa.NINPUTUSERID InputUserID,aa.DTINPUTDATE TINPUTDATE," +
				"NISCREDIT,NISASSURE,NISIMPAWN,NISPLEDGE,NINTERESTTYPEID,MEXAMINEAMOUNT ExamineAmount,NINTERVALNUM IntervalNum,NBANKINTERESTID,aa.NSTATUSID StatusID," +
				"aa.NNEXTCHECKUSERID,MCHARGERATE,DTSTARTDATE,DTENDDATE,ISCANMODIFY,NCHARGERATETYPEID,SCLIENTINFO,MSELFAMOUNT,NRISKLEVEL,NTYPEID1,NTYPEID2,NTYPEID3," +
				"NBANKACCEPTPO,NBIZACCEPTPO,MCHECKAMOUNT,MDISCOUNTRATE,DTDISCOUNTDATE,MINTERESTRATE InterestRate,MADJUSTRATE,LASTATTORNMENTAMOUNT,aa.NNEXTCHECKLEVEL," +
				"MSTAIDADJUSTRATE,NINOROUT,NDISCOUNTTYPEID,NREPURCHASETYPEID,MEXAMINESELFAMOUNT,DISCOUNTCLIENTID,PURCHASERINTERESTRATE,ISLOWLEVEL,DISCOUNTCLIENTNAME," +
				"ISPURCHASERINTEREST,ASSURECHARGERATE,ASSURECHARGETYPEID,BENEFICIARY,ASSURETYPEID1,ASSURETYPEID2,ISRECOGNIZANCE,PROJECTINFO,ISREPURCHASE,SELLCLIENTID,SELFRATE," +
				"SELLCONTRACTAMOUNT,NTYPEID4,NLIBORRATEID,NSUBTYPEID SubTypeID,NCREDITCHECKREPORTID,NINTERESTCOUNTTYPEID,MCHARGEAMOUNT,MRECOGNIZANCEAMOUNT,MMATURENOMINALAMOUNT," +
				"aa.ATTACHID,LEFTOVERSATTORNMENTAMOUNT,OVERDUEDATE,DTMODIFYSTARTDATE,DTMODIFYENDDATE,NLOANNOTEID,MORIGIONAMOUNT,MPREAMOUNT,MCHARGEAMOUNTRATE,DTFACTENDDATE,MDISCOUNTACCRUAL,MPURCHASERAMOUNT ";
		m_sbFrom=" LOAN_CONTRACTFORM aa ";
		m_sbWhere=" 1=1 and id not in(select NCONTRACTID from LOAN_DISCOUNTCREDENCE where nStatusID<>0) and id not in(select NCONTRACTID from loan_payForm where nStatusID<>0) ";
		StringBuffer sbSQL = new StringBuffer();
		if (queryPurpose == 1) //for modify
		{
			sbSQL.append(" \n and aa.nInputUserID = " + userID);
			sbSQL.append(" \n and aa.nStatusID in ( " + SETTConstant.SettContractStatus.SAVE + "," + SETTConstant.SettContractStatus.SUBMIT + "," + SETTConstant.SettContractStatus.CHECK + ","
					+ SETTConstant.SettContractStatus.NOTACTIVE + ")");
		}
		if (queryPurpose == 2) //for examine
		{
			sbSQL.append(" \n and aa.nStatusID = " + SETTConstant.SettContractStatus.SUBMIT);
		}
		if (loanTypeID > 0)
		{
			sbSQL.append(" \n and aa.nTypeID = " + loanTypeID);
		}
		if (loanSubTypeID > 0)
		{
			sbSQL.append(" \n and aa.nSubTypeID = " + loanSubTypeID);
		}			
		
		if (clientID > 0)
		{
			sbSQL.append(" \n and aa.nBorrowClientID = " + clientID);
		}
		if (consignClientID > 0)
		{
			sbSQL.append(" \n and aa.nConsignClientID = " + consignClientID);
		}
		//		不做人员的限制
		//      if (inputUserID > 0)
		//      {
		//          sbSQL.append(" \n and aa.nInputUserID = " + inputUserID);
		//      }
		if (contractIDFrom > 0)
		{
			sbSQL.append(" \n and aa.ID >= " + contractIDFrom);
		}
		if (contractIDTo > 0)
		{
			sbSQL.append(" \n and aa.ID <= " + contractIDTo);
		}
		if (intervalNum > 0)
		{
			sbSQL.append(" \n and aa.nIntervalNum = " + intervalNum);
		}
		if (currencyID > 0)
		{
			sbSQL.append(" \n and aa.nCurrencyID = " + currencyID);
		}
		if (officeID > 0)
		{
			sbSQL.append(" \n and aa.nOfficeID = " + officeID);
		}
		if (statusID > 0)
		{
			sbSQL.append(" \n and aa.nStatusID = " + statusID);
		}
		if (amountFrom > 0)
		{
			sbSQL.append(" \n and aa.mExamineAmount >= " + amountFrom);
		}
		if (amountTo > 0)
		{
			sbSQL.append(" \n and aa.mExamineAmount <= " + amountTo);
		}
		if (loanStart != null)
		{
			sbSQL.append(" \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(loanStart) + "'");
		}
		if (loanEnd != null)
		{
			sbSQL.append(" \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(loanEnd) + "'");
		}
		m_sbWhere =m_sbWhere+sbSQL.toString();
		StringBuffer sbOrder = new StringBuffer();
		int nIndex = 0;
		nIndex = orderParamString.indexOf(".");
		if (nIndex > 0)
		{
			if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_contractform"))
			{
				sbOrder.append(" \n order by aa." + orderParamString.substring(nIndex + 1));
			}
		}
		else
		{
			sbOrder.append(" \n order by aa.ID ");
		}
		if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			sbOrder.append(" desc ");
		}
		
		m_sbOrderBy=sbOrder.toString();
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy + " ");
		return pageLoader;
	}
	
	public static void main(String[] args) throws Exception {
		SettContractDAO dao=new SettContractDAO("");
		dao.updateDiscountDate("09/16/2005",7);
	}
}
