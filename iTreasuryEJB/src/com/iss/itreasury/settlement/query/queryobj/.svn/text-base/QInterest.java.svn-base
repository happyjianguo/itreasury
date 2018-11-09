/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code 

Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryInterestSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTConstant.InterestFeeType;
import com.iss.itreasury.settlement.util.SETTConstant.InterestOperateType;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - 

Code Generation - Code
 * and Comments
 */
public class QInterest extends BaseQueryObject
{

	public final static int OrderBy_AccountNo = 1;	
	public final static int OrderBy_ContractNo = 2;
	public final static int OrderBy_PayFormNo = 3;
	public final static int OrderBy_StartDate = 4;
	public final static int OrderBy_EndDate = 5;
	public final static int OrderBy_Days = 6;
	public final static int OrderBy_Balance = 7;
	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QInterest()
	{
		super();		
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getInterestInfoSQL(InterestQueryInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append(" account.ID AS AccountID, \n");  
		m_sbSelect.append(" account.sAccountNo AS AccountNo, \n");  //账户号
		m_sbSelect.append(" account.nAccountTypeID AS AccountTypeID, \n");  //账户类型
		m_sbSelect.append(" subaccount.ID AS SubAccountID, \n");  //子账户ID
		m_sbSelect.append(" contractform.sContractCode AS contractNo, \n");  //合同号
		m_sbSelect.append(" payform.sCode AS payFormNo, \n");  		
		m_sbSelect.append(" interest.dtInterestStart AS InterestStartDate, \n");  
		m_sbSelect.append(" interest.dtInterestEnd AS InterestEndDate,  \n");
		m_sbSelect.append(" interest.nInterestDays AS InterestDays,  \n");
		m_sbSelect.append(" interest.mInterest AS Interest, \n");
		m_sbSelect.append(" interest.mRate AS Rate, \n");
		m_sbSelect.append(" interest.mNegotiateInterest AS NegotiateInterest, \n");
		m_sbSelect.append(" interest.mBaseBalance AS BaseBalance, \n");
		m_sbSelect.append(" interest.mInterestTax AS InterestTax, \n");
		m_sbSelect.append(" interest.sTransNo AS TransNo, \n");
		m_sbSelect.append(" interest.nInputUserID AS InputUserID, \n");
		m_sbSelect.append(" interest.dtExecute AS ExecuteDate, \n");
		m_sbSelect.append(" interest.nInterestType AS InterestType, \n");	
		m_sbSelect.append(" interest.nOperationType AS OperationType, \n");	
		m_sbSelect.append(" interest.NPAYINTERESTACCOUNTID AS PayInterestAccountID, \n");	
		m_sbSelect.append(" interest.NRECEIVEINTERESTACCOUNTID AS ReceiveInterestAccountID, \n");	
		m_sbSelect.append(" interest.ID AS ID \n");				
		
		// from 
		m_sbFrom = new StringBuffer();
		
		m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, SETT_TRANSINTERESTSETTLEMENT interest,loan_ContractForm contractform, loan_LoanForm loanform \n");
		
		// where 
		m_sbWhere = new StringBuffer();
		
		m_sbWhere.append(" account.ID = interest.nAccountID ");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" interest.nSubAccountID = subaccount.ID  ");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID(+)  ");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID(+)  ");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  ");
		
		if(qInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" interest.nOfficeID = " + qInfo.getOfficeID());
		}
		if(qInfo.getCurrencyID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" interest.nCurrencyID = " + qInfo.getCurrencyID());
		}	
		m_sbWhere.append(" and interest.nStatusID <>  " + SETTConstant.TransactionStatus.DELETED +  " ");
		if(qInfo.getClearInterest()!=null)
		{		
			m_sbWhere.append(" AND ");		
			m_sbWhere.append(" interest.dtInterestSettlement= to_date('"+DataFormat.formatDate(qInfo.getClearInterest())+"','yyyy-mm-dd')");
		}
		if(qInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" AND ");		
			m_sbWhere.append(" interest.nOfficeID= " + qInfo.getOfficeID() + " ");			
		}
		if(qInfo.getCurrencyID()>0)
		{
			m_sbWhere.append(" AND ");		
			m_sbWhere.append(" interest.nCurrencyID= " + qInfo.getCurrencyID() + "");			
		}
		if(qInfo.getUserID()>0)
		{
			m_sbWhere.append(" AND( ");		
			m_sbWhere.append(" interest.nInputUserID= " + qInfo.getUserID() + "");
			m_sbWhere.append(" or interest.nInterestFeeSettingDetailID!= -1");
			m_sbWhere.append(" ) ");				
		}
		if(qInfo.getIsPrewDraw() > 0)
		{
			m_sbWhere.append(" AND interest.nInterestType = " + InterestFeeType.PREDRAWINTEREST +" AND interest.nOperationType in (" + InterestOperateType.PREDRAWINTEREST + "," + InterestOperateType.CLEANPREDRAWINTEREST + ")");	
		}
		
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by interest.AccountNo" + strDesc);
				break;
			case OrderBy_ContractNo :
				m_sbOrderBy.append(" \n order by interest.ContractNo" + strDesc);
				break;
			case OrderBy_PayFormNo :
				m_sbOrderBy.append(" \n order by interest.PayFormNo" + strDesc);
				break;
			case OrderBy_StartDate :
				m_sbOrderBy.append(" \n order by interest.StartDate" + strDesc);
				break;
			case OrderBy_EndDate :
				m_sbOrderBy.append(" \n order by interest.EndDate" + strDesc);
				break;
			case OrderBy_Days :
				m_sbOrderBy.append(" \n order by interest.days" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by interest.sTransNo desc ");
				break;	
		}
		
		log.debug("select " + m_sbSelect.toString()+"from "+m_sbFrom.toString()+"where "+m_sbWhere.toString());
	}
	
	//加入贴现的记录查询 Boxu add
	public void getAllTypeInterestInfoSQL(InterestQueryInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append(" AccountID, \n");  
		m_sbSelect.append(" AccountNo, \n");  //账户号
		m_sbSelect.append(" AccountTypeID, \n");  //账户类型
		m_sbSelect.append(" SubAccountID, \n");  //子账户ID
		m_sbSelect.append(" contractNo, \n");  //合同号
		m_sbSelect.append(" payFormNo, \n");  		
		m_sbSelect.append(" InterestStartDate, \n");  
		m_sbSelect.append(" InterestEndDate,  \n");
		m_sbSelect.append(" InterestDays,  \n");
		m_sbSelect.append(" Interest, \n");
		m_sbSelect.append(" Rate, \n");
		m_sbSelect.append(" NegotiateInterest, \n");
		m_sbSelect.append(" BaseBalance, \n");
		m_sbSelect.append(" InterestTax, \n");
		m_sbSelect.append(" TransNo, \n");
		m_sbSelect.append(" InputUserID, \n");
		m_sbSelect.append(" ExecuteDate, \n");
		m_sbSelect.append(" InterestType, \n");	
		m_sbSelect.append(" OperationType, \n");	
		m_sbSelect.append(" PayInterestAccountID, \n");	
		m_sbSelect.append(" ReceiveInterestAccountID, \n");	
		m_sbSelect.append(" ID, \n");				
		
		m_sbSelect.append(" OfficeID, \n");
		m_sbSelect.append(" CurrencyID, \n");
		m_sbSelect.append(" StatusID, \n");
		m_sbSelect.append(" InterestSettlement, \n");
		m_sbSelect.append(" InterestFeeSettingDetailID \n");
		
		// from 
		m_sbFrom = new StringBuffer();
		
		m_sbFrom.append(" sett_queryInterest \n");
		
		// where 
		m_sbWhere = new StringBuffer();
		
		m_sbWhere.append(" 1 = 1 ");
		
		if(qInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" OfficeID = " + qInfo.getOfficeID());
		}
		if(qInfo.getCurrencyID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" CurrencyID = " + qInfo.getCurrencyID());
		}	
		
		m_sbWhere.append(" and StatusID <>  " + SETTConstant.TransactionStatus.DELETED +  " ");
		
		if(qInfo.getClearInterest()!=null)
		{		
			m_sbWhere.append(" AND ");		
			m_sbWhere.append(" InterestSettlement= to_date('"+DataFormat.formatDate(qInfo.getClearInterest())+"','yyyy-mm-dd')");
		}
		if(qInfo.getUserID()>0)
		{
			m_sbWhere.append(" AND( ");		
			m_sbWhere.append(" InputUserID= " + qInfo.getUserID() + "");
			m_sbWhere.append(" or InterestFeeSettingDetailID != -1 ");
			m_sbWhere.append(" ) ");
		}
		if(qInfo.getIsPrewDraw() > 0)
		{
			m_sbWhere.append(" AND InterestType = " + InterestFeeType.PREDRAWINTEREST +" AND OperationType in (" + InterestOperateType.PREDRAWINTEREST + "," + InterestOperateType.CLEANPREDRAWINTEREST + ")");
		}
		
		//
		m_sbOrderBy = new StringBuffer();
		
		m_sbOrderBy.append(" order by TransNo ");
		
		log.debug("select " + m_sbSelect.toString()+"from "+m_sbFrom.toString()+"where "+m_sbWhere.toString());
	}
	
	public QueryInterestSumInfo queryInterestSum(InterestQueryInfo qInfo) throws Exception
	{
		QueryInterestSumInfo sumInfo = new QueryInterestSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strSelectInterest = "";
		String strSelectCommission = "";
		String strSelectAssure = "";
		String strSelectCompoundInterest = "";
		String strSelectForfeitInterest = "";
		String strInterestWhere = "";
		String strCommissionWhere = "";
		String strAssureWhere = "";
		String strCompoundInterestWhere = "";
		String strForfeitInterestWhere = "";
		String sql = "";
		//

		try
		{
			getInterestInfoSQL(qInfo);
			// select 
			strSelect = " select sum(round(interest.mBaseBalance,2)) BalanceSum,sum(round(interest.mInterestTax,2)) InterestTaxSum \n";			
			strSelectCommission = " select sum(round(interest.mInterest,2)) CommisionSum  \n";
			strSelectAssure = " select sum(round(interest.mInterest,2)) AssureSum  \n";
			strSelectCompoundInterest = " select sum(round(interest.mInterest,2)) CompoundInterest  \n";
			strSelectForfeitInterest = " select sum(round(interest.mInterest,2)) ForfeitInterest  \n";
			strSelectInterest = " select sum(round(interest.mInterest,2)) InterestSum  \n";
			
			strInterestWhere = "    and interest.nInterestType = " + SETTConstant.InterestFeeType.INTEREST + " \n";
			strCommissionWhere = "  and interest.nInterestType = " + SETTConstant.InterestFeeType.COMMISION + " \n";
			strAssureWhere = "    and interest.nInterestType = " + SETTConstant.InterestFeeType.ASSURE + " \n";
			strCompoundInterestWhere = "    and interest.nInterestType = " + SETTConstant.InterestFeeType.COMPOUNDINTEREST + " \n";
			strForfeitInterestWhere = "    and interest.nInterestType = " + SETTConstant.InterestFeeType.FORFEITINTEREST + " \n";
			
			con = this.getConnection();
			
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			
			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				sumInfo.setBalanceSum(rs.getDouble(1));
				sumInfo.setInterestTaxSum(rs.getDouble(2));
			}
			cleanup(rs);
			cleanup(ps);
			
			sql = strSelectInterest + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strInterestWhere;
			
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//利息合计
				sumInfo.setInterestSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectCommission + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strCommissionWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//手续费合计
				sumInfo.setCommisionSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectCompoundInterest + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strCompoundInterestWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//复利合计
				sumInfo.setCompoundInterestSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectForfeitInterest + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strForfeitInterestWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//罚息合计
				sumInfo.setForfeitInterestSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectAssure + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strAssureWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//担保费合计
				sumInfo.setAssureSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);						
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumInfo;
	}
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryInterestInfo(InterestQueryInfo qInfo) throws Exception
	{

		getInterestInfoSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) 

		com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
            new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 
	 * @param Boxu
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAllTypeInterestInfo(InterestQueryInfo qInfo) throws Exception
	{

		getAllTypeInterestInfoSQL(qInfo);

		PageLoader pageLoader = (PageLoader) 

		com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
            new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	//加入贴现的记录查询 Boxu add
	public QueryInterestSumInfo queryAllTypeInterestSum(InterestQueryInfo qInfo) throws Exception
	{
		QueryInterestSumInfo sumInfo = new QueryInterestSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strSelectInterest = "";
		String strSelectCommission = "";
		String strSelectAssure = "";
		String strSelectCompoundInterest = "";
		String strSelectForfeitInterest = "";
		String strInterestWhere = "";
		String strCommissionWhere = "";
		String strAssureWhere = "";
		String strCompoundInterestWhere = "";
		String strForfeitInterestWhere = "";
		String sql = "";
		//

		try
		{
			getAllTypeInterestInfoSQL(qInfo);
			// select 
			strSelect = " select sum(round( BaseBalance, 2 )) BalanceSum, sum(round( InterestTax, 2 )) InterestTaxSum \n";			
			strSelectCommission = " select sum(round( Interest, 2 )) CommisionSum  \n";
			strSelectAssure = " select sum(round( Interest, 2 )) AssureSum  \n";
			strSelectCompoundInterest = " select sum(round( Interest, 2 )) CompoundInterest  \n";
			strSelectForfeitInterest = " select sum(round( Interest, 2 )) ForfeitInterest  \n";
			strSelectInterest = " select sum(round( Interest, 2 )) InterestSum  \n";
			
			strInterestWhere = " and InterestType = " + SETTConstant.InterestFeeType.INTEREST + " \n";
			strCommissionWhere = " and InterestType = " + SETTConstant.InterestFeeType.COMMISION + " \n";
			strAssureWhere = " and InterestType = " + SETTConstant.InterestFeeType.ASSURE + " \n";
			strCompoundInterestWhere = " and InterestType = " + SETTConstant.InterestFeeType.COMPOUNDINTEREST + " \n";
			strForfeitInterestWhere = " and InterestType = " + SETTConstant.InterestFeeType.FORFEITINTEREST + " \n";
			
			con = this.getConnection();
			
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			
			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				sumInfo.setBalanceSum(rs.getDouble(1));
				sumInfo.setInterestTaxSum(rs.getDouble(2));
			}
			cleanup(rs);
			cleanup(ps);
			
			sql = strSelectInterest + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strInterestWhere;
			
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//利息合计
				sumInfo.setInterestSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectCommission + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strCommissionWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//手续费合计
				sumInfo.setCommisionSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectCompoundInterest + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strCompoundInterestWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//复利合计
				sumInfo.setCompoundInterestSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectForfeitInterest + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strForfeitInterestWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//罚息合计
				sumInfo.setForfeitInterestSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelectAssure + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strAssureWhere;
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//担保费合计
				sumInfo.setAssureSum(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);						
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumInfo;
	}
	
	public ArrayList queryAllTypePrintInterestInfo(InterestQueryInfo qInfo) throws Exception
	{
		ArrayList list = new ArrayList();
		QInterestEstimate qInterestEstimate = new QInterestEstimate();
		list = qInterestEstimate.queryAllTypeInterestInfo(qInfo);
		return list;
	}
	
}