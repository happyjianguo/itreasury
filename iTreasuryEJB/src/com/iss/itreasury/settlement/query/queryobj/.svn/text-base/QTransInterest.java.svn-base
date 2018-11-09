/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.QueryCraTransInterestConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransInterestConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.InterestInfoForPrint;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransInterestInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QTransInterest extends BaseQueryObject
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QTransInterest()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	/**
	 * 产生查询SQL
	 * @param info
	 */
	public void getTransInterestSQL(QueryTransInterestConditionInfo info)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" TransID,TransNo,TransactionTypeID,ExecuteDate, \n");
		m_sbSelect.append(" InterestSettlementDate,AutoExecuteDate,SubAccountID,PayInterestAccountID, \n");
		m_sbSelect.append(" ReceiveInterestAccountID,Interest,InputUserID,CheckUserID,ClientID, \n");
		m_sbSelect.append(" ClientNo,ClientName,AccountTypeID,AccountID, \n");
		m_sbSelect.append(" AccountNo,DepositNo,DepositTerm,ContractID, \n");
		m_sbSelect.append(" ContractNo,PayFormID,PayFormNo,LoanTypeID, \n");
		m_sbSelect.append(" LoanTerm,LoanYear,InterestRatePlanID,OfficeID, \n");
		m_sbSelect.append(" CurrencyID,IsTrans,IsRecord,ConsignClientID, \n");
		m_sbSelect.append(" CLIENTTYPEID1,CLIENTTYPEID2,CLIENTTYPEID3, CLIENTTYPEID4, CLIENTTYPEID5, CLIENTTYPEID6,  \n");
		m_sbSelect.append(" InterestStart,InterestEnd,InterestDays,InterestTypeID, \n");
		m_sbSelect.append(" InterestRate,Capital,InterestTaxRate,Abstract,PayInterestAccountNo,ReceiveInterestAccountNo \n");

		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_vTransInterestDetail,client \n");
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1=1 ");
		m_sbWhere.append(" and sett_vTransInterestDetail.clientID = client.id(+)");
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
		if (info.getInterestSettlementType() == 1) //结算交易
			m_sbWhere.append(" and IsTrans=1");
		else if (info.getInterestSettlementType() == 2) //结息记录
			m_sbWhere.append(" and IsRecord=1");
		else if (info.getInterestSettlementType() == 3) //记提利息
			m_sbWhere.append(" and InterestTypeID=" + SETTConstant.InterestFeeType.PREDRAWINTEREST);
		else if (info.getInterestSettlementType() == 4) //冲销记提利息
			m_sbWhere.append(" and InterestTypeID=8");

		String strInterestTypeArray = "";
		if (info.getInterestType() != null && info.getInterestType().length > 0)
		{
			for (int i = 0; i < info.getInterestType().length; i++)
			{
				if (i == 0)
				{
					strInterestTypeArray += info.getInterestType()[i];
				}
				else
				{
					strInterestTypeArray += "," + info.getInterestType()[i];
				}
			}
			m_sbWhere.append(" and InterestTypeID in (" + strInterestTypeArray + ")");
		}
		if (info.getTransNo() != null && info.getTransNo().trim().length() > 0)
			m_sbWhere.append(" and TransNo='" + info.getTransNo() + "'");
		if (info.getAccountNoStart() != null && info.getAccountNoStart().trim().length() > 0)
		{
			m_sbWhere.append(" and AccountNo >='" + info.getAccountNoStart() + "'");
		}
		else
		{
			if (info.getClientNoStart() != null && info.getClientNoStart().trim().length() > 0)
				m_sbWhere.append(" and ClientNo >='" + info.getClientNoStart() + "'");			
		}
		if (info.getAccountNoEnd() != null && info.getAccountNoEnd().trim().length() > 0)
		{
			m_sbWhere.append(" and AccountNo <='" + info.getAccountNoEnd() + "'");
		}
		else
		{
			if (info.getClientNoEnd() != null && info.getClientNoEnd().trim().length() > 0)
				m_sbWhere.append(" and ClientNo <='" + info.getClientNoEnd() + "'");
		}
		//add by 2012-05-18 添加指定账户编号
		if(info.getAppointAccountNo() != null && info.getAppointAccountNo().length() > 0){
			m_sbWhere.append(" and AccountNo in ('"+info.getAppointAccountNo()+"')");
		}
		if (info.getFixedDepositNoStart() != null && info.getFixedDepositNoStart().trim().length() > 0)
			m_sbWhere.append(" and DepositNo >='" + info.getFixedDepositNoStart() + "'");
		if (info.getFixedDepositNoEnd() != null && info.getFixedDepositNoEnd().trim().length() > 0)
			m_sbWhere.append(" and DepositNo <='" + info.getFixedDepositNoEnd() + "'");
		if (info.getNotifyDepositNoStart() != null && info.getNotifyDepositNoStart().trim().length() > 0)
			m_sbWhere.append(" and DepositNo >='" + info.getNotifyDepositNoStart() + "'");
		if (info.getNotifyDepositNoEnd() != null && info.getNotifyDepositNoEnd().trim().length() > 0)
			m_sbWhere.append(" and DepositNo <='" + info.getNotifyDepositNoEnd() + "'");
		if (info.getContractNoStart() != null && info.getContractNoStart().trim().length() > 0)
			m_sbWhere.append(" and ContractNo >='" + info.getContractNoStart() + "'");
		if (info.getContractNoEnd() != null && info.getContractNoEnd().trim().length() > 0)
			m_sbWhere.append(" and ContractNo <='" + info.getContractNoEnd() + "'");
		if (info.getPayFormNoStart() != null && info.getPayFormNoStart().trim().length() > 0)
			m_sbWhere.append(" and PayFormNo >='" + info.getPayFormNoStart() + "'");
		if (info.getPayFormNoEnd() != null && info.getPayFormNoEnd().trim().length() > 0)
			m_sbWhere.append(" and PayFormNo <='" + info.getPayFormNoEnd() + "'");
		if (info.getConsignClientID() > 0)
			m_sbWhere.append(" and ConsignClientID =" + info.getConsignClientID() + "");
		if (info.getEnterpriseTypeID1() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID1 = " + info.getEnterpriseTypeID1());
		if (info.getEnterpriseTypeID2() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID2 = " + info.getEnterpriseTypeID2());
		if (info.getEnterpriseTypeID3() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID3 = " + info.getEnterpriseTypeID3());
		if (info.getEnterpriseTypeID4() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID4 = " + info.getEnterpriseTypeID4());
		if (info.getEnterpriseTypeID5() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID5 = " + info.getEnterpriseTypeID5());
		if (info.getEnterpriseTypeID6() > 0)
			m_sbWhere.append("    and   nCLIENTTYPEID6 = " + info.getEnterpriseTypeID6());
		if (info.getAutoExecuteDateStart() != null)
			m_sbWhere.append(" and AutoExecuteDate>=to_date('" + DataFormat.getDateString(info.getAutoExecuteDateStart()) + "','yyyy-mm-dd HH:MI:SS')");
		if (info.getAutoExecuteDateEnd() != null)
			m_sbWhere.append(" and AutoExecuteDate<=to_date('" + DataFormat.getDateString(info.getAutoExecuteDateEnd()) + "','yyyy-mm-dd HH:MI:SS')");
		if (info.getExecuteDateStart() != null)
			m_sbWhere.append(" and ExecuteDate>=to_date('" + DataFormat.getDateString(info.getExecuteDateStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteDateEnd() != null)
			m_sbWhere.append(" and ExecuteDate<=to_date('" + DataFormat.getDateString(info.getExecuteDateEnd()) + "','yyyy-mm-dd')");
		if (info.getCalInterestDateStart() != null)
			m_sbWhere.append(" and InterestSettlementDate>=to_date('" + DataFormat.getDateString(info.getCalInterestDateStart()) + "','yyyy-mm-dd')");
		if (info.getCalInterestDateEnd() != null)
			m_sbWhere.append(" and InterestSettlementDate<=to_date('" + DataFormat.getDateString(info.getCalInterestDateEnd()) + "','yyyy-mm-dd')");
		if (info.getDepositTerm() > 0)
			m_sbWhere.append(" and DepositTerm=" + info.getDepositTerm() + "");
		if (info.getAccountTypeID() > 0)
			m_sbWhere.append(" and AccountTypeID=" + info.getAccountTypeID() + "");
		if (info.getLoanTypeID() > 0)
			m_sbWhere.append(" and LoanTypeID=" + info.getLoanTypeID() + "");
		if (info.getLoanTerm() > 0)
			m_sbWhere.append(" and LoanTerm=" + info.getLoanTerm() + "");
		if (info.getLoanYear() > 0)
			m_sbWhere.append(" and LoanYear=" + info.getLoanYear() + "");
		
		//上海电气新增扩展属性条件
		if (info.getExtendAttribute1()!=-1 && info.getExtendAttribute1()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE1 = " + info.getExtendAttribute1() + " \n");
		}
		if (info.getExtendAttribute2()!=-1 && info.getExtendAttribute2()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE2 = " + info.getExtendAttribute2() + " \n");
		}
		if (info.getExtendAttribute3()!=-1 && info.getExtendAttribute3()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE3 = " + info.getExtendAttribute3() + " \n");
		}
		if (info.getExtendAttribute4()!=-1 && info.getExtendAttribute4()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE4 = " + info.getExtendAttribute4() + " \n");
		}
		
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";

		switch ((int) info.getOrderID())
		{
			case (int) 1 : //查询
				m_sbOrderBy.append(" order by InterestSettlementDate,TransNo,AccountNo" + strDesc + " \n");
				break;
			default : //打印
				m_sbOrderBy.append(" order by AccountNo,TransNo" + strDesc + " \n");
				break;
		}
		//logger.debug(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}
	/**
	 * 获得借方金额合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getDebitAmountSum(QueryTransInterestConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//

		try
		{
			getTransInterestSQL(info);
			// select 
			strSelect = " select sum(round(Interest,2)) DebitAmountSum \n";

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			sql += " and PayInterestAccountID > 0.0"; // 有付息账户
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("DebitAmountSum");
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
		return dReturn;
	}

	/**
	 * 获得贷方金额合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getCreditAmountSum(QueryTransInterestConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//

		try
		{
			getTransInterestSQL(info);
			// select 
			strSelect = " select sum(round(Interest,2)) CreditAmountSum \n";

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			sql += " and ReceiveInterestAccountID > 0.0"; // 有收息账户
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("CreditAmountSum");
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
		return dReturn;
	}
	/**
	 * 获得结息金额合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getAmountSum(QueryTransInterestConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//

		try
		{
			getTransInterestSQL(info);
			// select 
			strSelect = " select sum(round(Interest,2)) AmountSum \n";

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("AmountSum");
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
		return dReturn;
	}
	
	
	/**
	 * 获得协定利率合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getNegotiateInterestSum(QueryTransInterestConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; 

		try
		{
			getTransInterestSQL(info);
			// select 
			strSelect = " select sum(round(mNegotiateInterest,2)) NegotiateInterestSum from sett_TransInterestSettlement where id in (select transid \n";

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() +")";
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("NegotiateInterestSum");
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
		return dReturn;
	}
	
	/**
	 * 获得协定利率合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getNegotiateInterestSum(QueryTransInterestConditionInfo info,String InterestTypeID) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; 

		try
		{
			getTransInterestSQL(info);
			// select 
			strSelect = " select sum(round(mNegotiateInterest,2)) NegotiateInterestSum from sett_TransInterestSettlement where id in (select transid \n";

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			sql += " and InterestTypeID = " + InterestTypeID +")";
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("NegotiateInterestSum");
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
		return dReturn;
	}

	/**
	* 获得利息费用支付的本金
	* @param info
	* @return
	* @throws Exception
	*/
	private double getInterestFreeCapitalForPrint(long lPayFormID, Timestamp tsInterestStart) throws Exception
	{

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		double dReturn = 0.0; //
		try
		{
			con = this.getConnection();
			sql = "select d.mBalance,d.dtDate from sett_DailyAccountBalance d,sett_SubAccount sa ";
			sql += " where sa.ID=d.nSubAccountID and sa.nStatusID>0 and sa.al_nLoanNoteID=? and d.dtDate>=?";
			sql += " order by dtDate";
			logger.info(sql);
			logger.info("lPayFormID=" + lPayFormID);
			logger.info("tsInterestStart=" + tsInterestStart);

			ps = con.prepareStatement(sql);
			ps.setLong(1, lPayFormID);
			ps.setTimestamp(2, tsInterestStart);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				dReturn = rs.getDouble("mBalance");
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
		return dReturn;

	}
	/**
	 * 获得打印所需信息，通过交易号
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public InterestInfoForPrint getInterestInfoForPrint(QueryTransInterestConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		QueryTransInterestInfo[] searchResults = null;
		InterestInfoForPrint interestinfoforprint = null;

		try
		{
			PageLoader pageloader = this.queryTransInterestInfo(info);
			searchResults = (QueryTransInterestInfo[]) pageloader.listAll();

			if (searchResults != null && searchResults.length > 0)
			{
				interestinfoforprint = new InterestInfoForPrint();
				for (int i = 0; i < searchResults.length; i++)
				{
					QueryTransInterestInfo transinterestinfo = (QueryTransInterestInfo) searchResults[i];

					//贷款利息通知单、利息通知单共有信息
					interestinfoforprint.setBrrowClientName(transinterestinfo.getClientName());
					interestinfoforprint.setConsignClientID(transinterestinfo.getConsignClientID());
					interestinfoforprint.setAccountTypeID(transinterestinfo.getAccountTypeID());
					interestinfoforprint.setTransNo(transinterestinfo.getTransNo());
					interestinfoforprint.setAccountID(transinterestinfo.getAccountID());
					interestinfoforprint.setReceiveInterestAccountID(transinterestinfo.getReceiveInterestAccountID());
					interestinfoforprint.setPayInterestAccountID(transinterestinfo.getPayInterestAccountID());
					interestinfoforprint.setConractID(transinterestinfo.getContractID());
					interestinfoforprint.setLoanBillID(transinterestinfo.getPayFormID());
					interestinfoforprint.setLoanBillNo(transinterestinfo.getPayFormNo());
					interestinfoforprint.setDepositNo(transinterestinfo.getDepositNo());
					interestinfoforprint.setInputUserID(transinterestinfo.getInputUserID());
					interestinfoforprint.setCheckerUserID(transinterestinfo.getCheckUserID());
					interestinfoforprint.setExecuteDate(transinterestinfo.getExecuteDate());
					interestinfoforprint.setAccountTypeID(transinterestinfo.getAccountTypeID());
					interestinfoforprint.setTransactionTypeID(transinterestinfo.getTransactionTypeID());
					interestinfoforprint.setTransId(transinterestinfo.getTransID());

					switch ((int) transinterestinfo.getInterestTypeID())
					{
						case (int) SETTConstant.InterestFeeType.INTEREST : //正常利息
							System.out.println("================1111111111111====================================");
							interestinfoforprint.setNormalInterest(transinterestinfo.getInterest());
							System.out.println("=a=1-transinterestinfo.getInterest()========="+transinterestinfo.getInterest());
							interestinfoforprint.setNormalStartDate(transinterestinfo.getInterestStart());
							System.out.println("=b=1-transinterestinfo.getInterestStart()===="+transinterestinfo.getInterestStart());
							interestinfoforprint.setNormalEndDate(transinterestinfo.getInterestEnd());
							System.out.println("=c=1-transinterestinfo.setNormalEndDate()===="+transinterestinfo.getInterestEnd());
							interestinfoforprint.setNormalAmount(transinterestinfo.getCapital());
							interestinfoforprint.setNormalRate(transinterestinfo.getInterestRate());
							if (transinterestinfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
							{
								interestinfoforprint.setNormalAmount(getInterestFreeCapitalForPrint(transinterestinfo.getPayFormID(), transinterestinfo.getInterestStart()));
							}
							break;
						case (int) SETTConstant.InterestFeeType.COMPOUNDINTEREST : //复利
							System.out.println("================22222222222222====================================");
							interestinfoforprint.setCompoundInterest(transinterestinfo.getInterest());
							interestinfoforprint.setCompoundStartDate(transinterestinfo.getInterestStart());
							interestinfoforprint.setCompoundEndDate(transinterestinfo.getInterestEnd());
							interestinfoforprint.setCompoundAmount(transinterestinfo.getCapital());
							interestinfoforprint.setCompoundRate(transinterestinfo.getInterestRate());
							break;
						case (int) SETTConstant.InterestFeeType.FORFEITINTEREST : //罚息
							System.out.println("================33333333333333333====================================");
							interestinfoforprint.setOverDueInterest(transinterestinfo.getInterest());
							interestinfoforprint.setOverDueStartDate(transinterestinfo.getInterestStart());
							interestinfoforprint.setOverDueEndDate(transinterestinfo.getInterestEnd());
							interestinfoforprint.setOverDueAmount(transinterestinfo.getCapital());
							interestinfoforprint.setOverDueRate(transinterestinfo.getInterestRate());
							break;
						case (int) SETTConstant.InterestFeeType.COMMISION : //手续费
							System.out.println("================44444444444444444444====================================");
							interestinfoforprint.setCommission(transinterestinfo.getInterest());
							interestinfoforprint.setCommissionStartDate(transinterestinfo.getInterestStart());
							interestinfoforprint.setCommissionEndDate(transinterestinfo.getInterestEnd());
							interestinfoforprint.setCommissionAmount(transinterestinfo.getCapital());
							interestinfoforprint.setCommissionRate(transinterestinfo.getInterestRate());
							if (transinterestinfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
							{
								interestinfoforprint.setCommissionAmount(getInterestFreeCapitalForPrint(transinterestinfo.getPayFormID(), transinterestinfo.getInterestStart()));
							}
							break;
						case (int) SETTConstant.InterestFeeType.ASSURE : //担保费
							System.out.println("================55555555555555555====================================");
							interestinfoforprint.setSuretyFee(transinterestinfo.getInterest());
							interestinfoforprint.setSuretyFeeStartDate(transinterestinfo.getInterestStart());
							interestinfoforprint.setSuretyFeeEndDate(transinterestinfo.getInterestEnd());
							interestinfoforprint.setSuretyFeeAmount(transinterestinfo.getCapital());
							interestinfoforprint.setSuretyFeeRate(transinterestinfo.getInterestRate());
							break;
						case (int) SETTConstant.InterestFeeType.PREDRAWINTEREST :
							System.out.println("==========ZZZZZ======1111111111111====================================");
							interestinfoforprint.setNormalInterest(transinterestinfo.getInterest());
							System.out.println("=ZZZZ=1-transinterestinfo.getInterest()========="+transinterestinfo.getInterest());
							interestinfoforprint.setNormalStartDate(transinterestinfo.getInterestStart());
							System.out.println("=ZZ=1-transinterestinfo.getInterestStart()===="+transinterestinfo.getInterestStart());
							interestinfoforprint.setNormalEndDate(transinterestinfo.getInterestEnd());
							System.out.println("=ZZZZ=1-transinterestinfo.setNormalEndDate()===="+transinterestinfo.getInterestEnd());
							interestinfoforprint.setNormalAmount(transinterestinfo.getCapital());
							interestinfoforprint.setNormalRate(transinterestinfo.getInterestRate());
							if (transinterestinfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
							{
								interestinfoforprint.setNormalAmount(getInterestFreeCapitalForPrint(transinterestinfo.getPayFormID(), transinterestinfo.getInterestStart()));
							}
							break;
							
					}

					//如果是活期利息还要设置协定利息信息
					if (SETTConstant.AccountType.isCurrentAccountType(transinterestinfo.getAccountTypeID())
					        ||SETTConstant.AccountType.isOtherDepositAccountType(transinterestinfo.getAccountTypeID()))
					{
						this.setNegotiateInfo(interestinfoforprint);
					}
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}

		return interestinfoforprint;
	}
	/**
	 * 设置协定存款信息
	 * @param info
	 * @return
	 */
	private InterestInfoForPrint setNegotiateInfo(InterestInfoForPrint interestinfoforprint) throws Exception
	{
		Connection contemp = null;
		PreparedStatement pstemp = null;
		ResultSet rstemp = null;
		String strSQL = "";
		try
		{
			contemp = this.getConnection();
			// select 
			strSQL = " select * from sett_TransInterestSettlement where sTransNo='" + interestinfoforprint.getTransNo() + "'";

			pstemp = contemp.prepareStatement(strSQL);
			rstemp = pstemp.executeQuery();
			if (rstemp.next())
			{
				interestinfoforprint.setConcertedInterest(rstemp.getDouble("mNegotiateInterest")); //协定利息
				interestinfoforprint.setConcertedStartDate(rstemp.getTimestamp("dtInterestStart")); //协定利息起息日
				interestinfoforprint.setConcertedEndDate(rstemp.getTimestamp("dtInterestEnd")); //协定利息终息日
				interestinfoforprint.setConcertedAmount(rstemp.getDouble("mNegotiateBalance")); //协定利息本金
				interestinfoforprint.setConcertedRate(rstemp.getDouble("mNegotiateRate")); //协定利息利率
			}

			cleanup(rstemp);
			cleanup(pstemp);
			cleanup(contemp);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rstemp);
			cleanup(pstemp);
			cleanup(contemp);
		}
		return interestinfoforprint;
	}

	/**
	 * 获得所有的交易号
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Collection getAllTransNos(QueryTransInterestConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		Vector v = new Vector(); //
		//

		try
		{
			getTransInterestSQL(info);
			// select 
			strSelect = " select TransNo \n";

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();

			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				v.add(rs.getString("TransNo"));
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
		return v;
	}

	/**
	 * 获得总记录数
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getCount(QueryTransInterestConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		long lReturn = -1; //
		//

		try
		{
			getTransInterestSQL(info);
			// select 
			strSelect = " select count(*) count \n";

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("count");
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
		return lReturn;
	}
	
	public void getCraTransInterestSQL(QueryCraTransInterestConditionInfo info)
	{
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();
		m_sbWhere = new StringBuffer();
		m_sbOrderBy = new StringBuffer();
		m_sbSelect.append(" settrecord.stransno tranNo,settrecord.dtinterestsettlement tsCleardate,");
		m_sbSelect.append(" settrecord.payinterestaccountid payIntrestAcountId,");
		m_sbSelect.append(" acount.saccountno payIntrestCode,");
		m_sbSelect.append(" cratrans.contractcode craContractCode,");
		m_sbSelect.append(" loanform.scontractcode loanContractCode,");
		m_sbSelect.append(" payform.scode payNoticeForm,");
		m_sbSelect.append(" cracou.counterpartname countpartName,");
		m_sbSelect.append(" cl.sname clientName,");
		m_sbSelect.append(" settrecord.drate rate,");
		m_sbSelect.append(" settrecord.amount amount,");
		m_sbSelect.append(" settrecord.minterest intrest,");
		m_sbSelect.append(" userinfo.sname username,");
		m_sbSelect.append(" settrecord.inputdate inputdate,");
		m_sbSelect.append(" settrecord.ninteresttype ninteresttype");
		m_sbFrom.append("sett_transferintersetrecord settrecord,");
		m_sbFrom.append("loan_payform                payform,");
		m_sbFrom.append("cra_contractdetail          cracon,");
		m_sbFrom.append("loan_contractform           loanform,");
		m_sbFrom.append("client                      cl,");
		m_sbFrom.append("cra_transfercontractform    cratrans,");
		m_sbFrom.append("cra_counterpart    cracou,");
		m_sbFrom.append("sett_account    acount,");
		m_sbFrom.append("userinfo    userinfo");
	    m_sbWhere.append(" settrecord.cracontractdetailid = cracon.id \n ");
	    m_sbWhere.append(" and cracon.loannoteid=payform.id    \n ");
	    m_sbWhere.append(" and loanform.id=payform.ncontractid \n ");
	    m_sbWhere.append(" and loanform.nborrowclientid=cl.id  \n ");
	    m_sbWhere.append(" and cratrans.id=cracon.transfercontractformid  \n ");
	    m_sbWhere.append(" and cratrans.counterpartid=cracou.id  \n ");
	    m_sbWhere.append(" and acount.id=settrecord.payinterestaccountid  \n ");
	    m_sbWhere.append(" and userinfo.id=settrecord.inputuserid  \n ");
	    m_sbWhere.append(" and settrecord.statusid<> "+SETTConstant.TransactionStatus.DELETED+" \n ");
		m_sbWhere.append(" and settrecord.officeid = " +info.getLOfficeID() +"\n ");
		m_sbWhere.append(" and settrecord.currencyid = " +info.getLCurrencyID() +"\n ");
		if(!info.getClientCodeStart().equals(""))
		{
			m_sbWhere.append(" and cl.sCode >= '" +info.getClientCodeStart() +"'\n ");
		}
		if(!info.getClientCodeEnd().equals(""))
		{
			m_sbWhere.append(" and cl.sCode <= '" +info.getClientCodeEnd() +"'\n ");
		}
		if(info.getLInterestSettlementType()>0)
		{
			m_sbWhere.append(" and settrecord.ninteresttype = " +info.getLInterestSettlementType() +"\n ");
		}
		if(!info.getLContractIDStart().equals(""))
		{
			m_sbWhere.append(" and loanform.scontractcode >= '" +info.getLContractIDStart() +"'\n ");
		}
		if(!info.getLContractIDEnd().equals(""))
		{
			m_sbWhere.append(" and loanform.scontractcode <= '" +info.getLContractIDEnd() +"'\n ");
		}
		if(!info.getContractCodeFrom().equals(""))
		{
			m_sbWhere.append(" and cratrans.contractcode >= '" +info.getContractCodeFrom() +"'\n ");
		}
		if(!info.getContractCodeTo().equals(""))
		{
			m_sbWhere.append(" and cratrans.contractcode <= '" +info.getContractCodeTo() +"'\n ");
		}
		if(info.getCounterpartid()>0)
		{
			m_sbWhere.append(" and cratrans.counterpartid = " +info.getCounterpartid() +"\n ");
		}
		if(!info.getTsCalInterestDateStart().equals(""))
		{
			m_sbWhere.append(" and settrecord.dtinterestsettlement >= to_date('" +info.getTsCalInterestDateStart() +"','yyyy-mm-dd') \n ");
		}
		if(!info.getTsCalInterestDateEnd().equals(""))
		{
			m_sbWhere.append(" and settrecord.dtinterestsettlement <= to_date('" +info.getTsCalInterestDateEnd() +"','yyyy-mm-dd') \n ");
		}
	}
	
	public void getCraTransPredrawInterestSQL(QueryCraTransInterestConditionInfo info)
	{
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();
		m_sbWhere = new StringBuffer();
		m_sbOrderBy = new StringBuffer();
		
		m_sbSelect.append(" settrecord.stransno tranNo,settrecord.dtinterestsettlement tsCleardate,");
		m_sbSelect.append(" cratrans.contractcode craContractCode,");
		m_sbSelect.append(" cracou.counterpartname countpartName,");
		m_sbSelect.append(" settrecord.drate rate,");
		m_sbSelect.append(" settrecord.amount amount,");
		m_sbSelect.append(" settrecord.minterest intrest,");
		m_sbSelect.append(" userinfo.sname username,");
		m_sbSelect.append(" settrecord.inputdate inputdate,");
		m_sbSelect.append(" settrecord.ninteresttype ninteresttype");
		m_sbFrom.append("sett_transferintersetrecord settrecord,");
		m_sbFrom.append("cra_transfercontractform    cratrans,");
		m_sbFrom.append("cra_counterpart    cracou,");
		m_sbFrom.append("userinfo    userinfo");
	    m_sbWhere.append(" settrecord.cracontractid = cratrans.id \n ");
	    m_sbWhere.append(" and cratrans.counterpartid=cracou.id  \n ");
	    m_sbWhere.append(" and userinfo.id=settrecord.inputuserid  \n ");
	    m_sbWhere.append(" and settrecord.statusid<> "+SETTConstant.TransactionStatus.DELETED+" \n ");
		m_sbWhere.append(" and settrecord.officeid = " +info.getLOfficeID() +"\n ");
		m_sbWhere.append(" and settrecord.currencyid = " +info.getLCurrencyID() +"\n ");
		if(info.getLInterestSettlementType()>0)
		{
			m_sbWhere.append(" and settrecord.ninteresttype = " +info.getLInterestSettlementType() +"\n ");
		}
		if(!info.getContractCodeFrom().equals(""))
		{
			m_sbWhere.append(" and cratrans.contractcode >= '" +info.getContractCodeFrom() +"'\n ");
		}
		if(!info.getContractCodeTo().equals(""))
		{
			m_sbWhere.append(" and cratrans.contractcode <= '" +info.getContractCodeTo() +"'\n ");
		}
		if(info.getCounterpartid()>0)
		{
			m_sbWhere.append(" and cratrans.counterpartid = " +info.getCounterpartid() +"\n ");
		}
		if(!info.getTsCalInterestDateStart().equals(""))
		{
			m_sbWhere.append(" and settrecord.dtinterestsettlement >= to_date('" +info.getTsCalInterestDateStart() +"','yyyy-mm-dd') \n ");
		}
		if(!info.getTsCalInterestDateEnd().equals(""))
		{
			m_sbWhere.append(" and settrecord.dtinterestsettlement <= to_date('" +info.getTsCalInterestDateEnd() +"','yyyy-mm-dd') \n ");
		}
	}

	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryTransInterestInfo(QueryTransInterestConditionInfo info) throws Exception
	{

		getTransInterestSQL(info);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.query.resultinfo.QueryTransInterestInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 *作者：赵敏
	 *时间：2009-9-7上午11:16:26
	 *@param info
	 *@return
	 *@throws Exception
	 *PageLoader
	 */
	public PageLoader queryCraTransInterestInfo(QueryCraTransInterestConditionInfo info) throws Exception
	{

		if(info.getLInterestSettlementType()==SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
		{
			getCraTransInterestSQL(info);
		}
		else
		{
			getCraTransPredrawInterestSQL(info);
		}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.query.resultinfo.QueryCraTransInterestResultInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

}