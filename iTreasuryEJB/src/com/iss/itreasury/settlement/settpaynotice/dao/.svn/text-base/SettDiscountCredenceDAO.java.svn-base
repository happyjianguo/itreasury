/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settpaynotice.dao;
import java.sql.*;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractQueryInfo;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.settpaynotice.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.system.dao.PageLoader;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettDiscountCredenceDAO extends SettlementDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public SettDiscountCredenceDAO()
	{
		//super("Loan_LoanForm");
		super("loan_discountCredence", true);
	}
	public SettDiscountCredenceDAO(Connection conn)
	{
		//super("Loan_LoanForm");
		super("loan_discountCredence", true, conn);
	}
	public Collection findByMultiOption(SettPayNoticeQueryInfo qInfo) throws ITreasuryDAOException
	{
		Collection c = null;
		String strSQL = "";
		long queryPurpose = qInfo.getQueryPurpose();
		try
		{
			/*-----------------init DAO --------------------*/
			initDAO();
			/*----------------end of init------------------*/
			strSQL = "select a.*,b.nBorrowClientID,b.sContractCode,b.mExamineAmount as mContractAmount from loan_discountCredence a,loan_contractForm b" + " where 1=1" + " and b.id=a.nContractID";
			if (queryPurpose == 1) //修改查找
			{
				strSQL += " and a.nStatusID=" + SETTConstant.SettPayNoticeStatus.SUBMIT;
				strSQL += " and a.nInputUserID=" + qInfo.getInputUserID();
			}
			else if (queryPurpose == 2) //复合查找
			{
				strSQL += " and ( (a.nStatusID=" + SETTConstant.SettPayNoticeStatus.SUBMIT + " and a.nInputUserID<>" + qInfo.getInputUserID() + ") or (a.nStatusID="
						+ SETTConstant.SettPayNoticeStatus.CHECK + " and a.NNEXTCHECKUSERID=" + qInfo.getInputUserID() + " ) )";
			}
			if (qInfo.getClientID() > 0)
			{
				strSQL += " and b.NBORROWCLIENTID=" + qInfo.getClientID();
			}
			if (qInfo.getOfficeID() > 0)
			{
				strSQL += " and b.nOfficeID=" + qInfo.getOfficeID();
			}
			if (qInfo.getCurrencyID() > 0)
			{
				strSQL += " and b.nCurrencyID=" + qInfo.getCurrencyID();
			}
			if (qInfo.getContractID() > 0)
			{
				strSQL += " and b.ID=" + qInfo.getContractID();
			}
			if (qInfo.getStartAmount() > 0) //授信额度开始
			{
				strSQL += " and b.mExamineAmount>=" + qInfo.getStartAmount();
			}
			if (qInfo.getEndAmount() > 0) //授信额度结束
			{
				strSQL += " and b.mExamineAmount<=" + qInfo.getEndAmount();
			}
			//录入日期开始
			if (qInfo.getStartInputDate() != null)
			{
				strSQL += " and a.dtINPUTDATE>=to_date('" + qInfo.getStartInputDate();
				strSQL += "','yyyy-mm-dd')";
			}
			//录入日期结束
			if (qInfo.getEndInputDate() != null)
			{
				strSQL += " and a.dtINPUTDATE<=to_date('" + qInfo.getEndInputDate();
				strSQL += "','yyyy-mm-dd')";
			}
			if (qInfo.getStatusID() > 0)
			{
				strSQL += " and a.nStatusID" + qInfo.getStatusID();
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			int nIndex = 0;
			String orderParamString = qInfo.getOrderParamString();
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0)
			{
				if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_discountCredence"))
				{
					strSQL += " order by " + orderParamString.substring(nIndex + 1);
				}
			}
			else
			{
				strSQL += " order by a.ID";
			}
			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			log4j.info("sql= \n" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			c = getDataEntitiesFromResultSet(SettDiscountCredenceInfo.class);
			
			/*----------------finalize Dao-----------------*/
			finalizeDAO();
			/*----------------end of finalize---------------*/
		}
		catch (Exception e)
		{
			throw new ITreasuryDAOException("查询贴现放款通知单出错", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw e;
			}
			
		}
		return (c.size() > 0 ? c : null);
	}
	
	/**
	 * 凭证查询分页ld新增
	 * @param SettPayNoticeQueryInfo info
	 * @return PageLoader
	 * @throws ITreasuryDAOException
	 */
	public PageLoader getMultiOptinList(SettPayNoticeQueryInfo qInfo)throws ITreasuryDAOException {
		String m_sbSelect=" a.id,a.nContractID ContractID,a.DTINPUTDATE INPUTDATE,a.NDRAFTTYPEID DRAFTTYPEID,a.SDRAFTCODE DRAFTCODE,a.DTPUBLICDATE PUBLICDATE,a.DTATTERM ATTERM," +
				"a.SAPPLYCLIENT APPLYCLIENT,a.SAPPLYACCOUNT APPLYACCOUNT,a.SAPPLYBANK APPLYBANK,a.SACCEPTCLIENT ACCEPTCLIENT,a.SACCEPTACCOUNT ACCEPTACCOUNT,a.SACCEPTBANK ACCEPTBANK," +
				"a.MAMOUNT AMOUNT,a.MRATE RATE,a.MINTEREST INTEREST,a.NSTATUSID STATUSID,a.NINPUTUSERID INPUTUSERID,a.NNEXTCHECKUSERID NEXTCHECKUSERID,a.DTFILLDATE FILLDATE,a.SCODE CODE," +
				"a.NGRANTTYPEID GRANTTYPEID,a.NACCOUNTBANKID ACCOUNTBANKID,a.SRECEIVEACCOUNT RECEIVEACCOUNT,a.SRECEIVECLIENTNAME RECEIVECLIENTNAME,a.SREMITBANK REMITBANK,a.SREMITINPROVINCE REMITINPROVINCE," +
				"a.SREMITINCITY REMITINCITY,a.NGRANTCURRENTACCOUNTID GRANTCURRENTACCOUNTID,a.NNEXTCHECKLEVEL NEXTCHECKLEVEL,a.NTYPEID TYPEID,a.NBILLSOURCETYPEID BILLSOURCETYPEID," +
				"a.NOFFICEID OFFICEID,a.DTREPURCHASEDATE REPURCHASEDATE,a.NTRANSDISCOUNTCREDENCEID TRANSDISCOUNTCREDENCEID,a.NREPURCHASETERM REPURCHASETERM,a.PURCHASERINTEREST,a.NCURRENCYID CurrencyID,a.ATTACHID,b.nBorrowClientID BorrowClientID,b.sContractCode ContractCode," +
				"b.mExamineAmount as ContractAmount ";
		String m_sbFrom=" loan_discountCredence a,loan_contractForm b ";
		String m_sbWhere=" 1=1 and b.id=a.nContractID ";
		
		long queryPurpose = qInfo.getQueryPurpose();
		if (queryPurpose == 1) //修改查找
		{
			m_sbWhere += " and a.nStatusID="+ SETTConstant.SettPayNoticeStatus.CHECK;
			m_sbWhere += " and a.nInputUserID=" + qInfo.getInputUserID();
			
		}
		else if (queryPurpose == 2) //复合查找
		{
			m_sbWhere += " and ( (a.nStatusID=" + SETTConstant.SettPayNoticeStatus.SUBMIT + " and a.nInputUserID<>" + qInfo.getInputUserID() + ") or (a.nStatusID="
					+ SETTConstant.SettPayNoticeStatus.CHECK + " and a.NNEXTCHECKUSERID=" + qInfo.getInputUserID() + " ) )";
		}
		if (qInfo.getClientID() > 0)
		{
			m_sbWhere += " and b.NBORROWCLIENTID=" + qInfo.getClientID();
		}
		if (qInfo.getOfficeID() > 0)
		{
			m_sbWhere += " and b.nOfficeID=" + qInfo.getOfficeID();
		}
		if (qInfo.getCurrencyID() > 0)
		{
			m_sbWhere += " and b.nCurrencyID=" + qInfo.getCurrencyID();
		}
		if (qInfo.getContractID() > 0)
		{
			m_sbWhere += " and b.ID=" + qInfo.getContractID();
		}
		if (qInfo.getStartAmount() > 0) //授信额度开始
		{
			m_sbWhere += " and b.mExamineAmount>=" + qInfo.getStartAmount();
		}
		if (qInfo.getEndAmount() > 0) //授信额度结束
		{
			m_sbWhere += " and b.mExamineAmount<=" + qInfo.getEndAmount();
		}
		//录入日期开始
		if (qInfo.getStartInputDate() != null)
		{
			m_sbWhere += " and a.dtINPUTDATE>=to_date('" + qInfo.getStartInputDate();
			m_sbWhere += "','yyyy-mm-dd')";
		}
		//录入日期结束
		if (qInfo.getEndInputDate() != null)
		{
			m_sbWhere += " and a.dtINPUTDATE<=to_date('" + qInfo.getEndInputDate();
			m_sbWhere += "','yyyy-mm-dd')";
		}
		if (qInfo.getStatusID() > 0)
		{
			m_sbWhere += " and a.nStatusID" + qInfo.getStatusID();
		}
		
		String m_sbOrderBy="";
		int nIndex = 0;
		String orderParamString = qInfo.getOrderParamString();
		nIndex = orderParamString.indexOf(".");
		if (nIndex > 0)
		{
			if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_discountCredence"))
			{
				m_sbOrderBy +=" order by "+ orderParamString.substring(nIndex + 1);
			}
		}
		else
		{
			m_sbOrderBy += " order by a.ID ";
		}
		if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			m_sbOrderBy += " desc";
		}
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.settpaynotice.dataentity.SettDiscountCredenceInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy + " ");
		return pageLoader;
	}
	protected long geSequenceID() throws ITreasuryDAOException
	{
		/**
		 * 此方法只能在DAO中被调用，即不重新创建数据库资源，因此也不需要 关闭数据库资源
		 */
		long id = -1;
		String strSeqName = "SEQ_" + strTableName;
		String sql = "SELECT Seq_Loan_PayFrm_DiscountCred.nextval nextid from dual";
		//prepareStatement(sql);
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		try
		{ //内部维护RS和PS，否则将会产生冲突,但Connection使用同一个	
			localPS = transConn.prepareStatement(sql);
			localRS = localPS.executeQuery();
			if (localRS.next())
			{
				id = localRS.getLong("nextid");
			}
			if (localRS != null)
				localRS.close();
			if (localPS != null)
				localPS.close();
		}
		catch (SQLException e)
		{
			new ITreasuryDAOException("数据库获取ID产异常", e);
		}
		return id;
	}
	public String createCredenceCode(long lContractID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		try
		{
			con = Database.getConnection();
			strSQL = " select nvl(max(sCode),0) sCode from Loan_DiscountCredence where nContractID = ? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				lCode = Long.parseLong(strCode) + 1;
				strCode = DataFormat.formatInt(lCode, 3, true);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception("exception : " + e.toString());
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
		Log.print(":::::::::: ::::strcode::::::" + strCode);
		return strCode;
	}
	
	public SettDiscountCredenceInfo findByID(long lID)
	{
		SettDiscountCredenceInfo info = null;
		
		try
		{
			info = (SettDiscountCredenceInfo) this.findByID(lID,SettDiscountCredenceInfo.class);
			this.initDAO();
			String strSQL = "select * from " + this.strTableName + " where id="+lID;
			transPS  = this.prepareStatement(strSQL);
			transRS  = this.executeQuery();
			if (transRS.next())
			{
				info.setPurchaserInterest(transRS.getDouble("PurchaserInterest"));
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
	
	public static void main(String[] args) throws ITreasuryDAOException
	{
		SettDiscountCredenceDAO dao = new SettDiscountCredenceDAO();
		SettDiscountCredenceInfo info = (SettDiscountCredenceInfo)dao.findByID(1745);
		System.out.println(info.getPurchaserInterest());
	}
	
}
