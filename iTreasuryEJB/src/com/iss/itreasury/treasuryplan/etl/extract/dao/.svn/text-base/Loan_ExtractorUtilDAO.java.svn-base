/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-12
 */

package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.loan.LoanContractPlanInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Loan_ExtractorUtilDAO extends AbstractExtractorUtilDAO
{

	private Log4j	log	= new Log4j(Constant.ModuleType.LOAN, this);


	public Loan_ExtractorUtilDAO() throws Exception
	{

		super(Constant.ModuleType.LOAN);
	}


	/**
	 * 根据条件获取自营/委托d的放款/收回贷款预计信息
	 */
	public Collection getLoanForcastInfoByCondition(long officeID, long currencyID, Timestamp startDate,Timestamp endDate, long loanTransTypeID) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		String strLoanTypeCodition = "";
		if (loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.ZYGrant || loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.ZYRepayment)
		{
			strLoanTypeCodition = "1,2,6,7";
		}
		else if (loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTGrant || loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTRepayment)
		{
			strLoanTypeCodition = "3,4";
		}

		long payTypeID = -1;
		if (loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.ZYGrant || loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTGrant)
		{
			payTypeID = 1;
		}
		else if (loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.ZYRepayment || loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTRepayment)
		{
			payTypeID = 2;
		}

		ArrayList list = new ArrayList();
		bufferSQL.append("select c.NCURRENCYID,c.scontractcode code,c.ntypeid,c.nborrowclientid,c.nconsignclientid,d.mamount,d.dtplandate ,c.mChargeRate \n");
		bufferSQL.append(" from (select a.id,a.ncontractid from loan_LoanContractPlan a, \n");
		bufferSQL.append(" (select ncontractid,max(nplanversion) nplanversion \n");
		bufferSQL.append(" from loan_LoanContractPlan group by ncontractid ) b \n");
		bufferSQL.append(" where a.ncontractid=b.ncontractid and a.nplanversion=b.nplanversion) p, \n");
		bufferSQL.append(" (select * from loan_LoanContractPlandetail where npaytypeid= " + payTypeID + " and dtplandate >= ?  and  dtplandate <= ? ) d, \n");
		bufferSQL.append(" (select * from loan_ContractForm where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid in (" + strLoanTypeCodition + ")");
		bufferSQL.append("  and nStatusID not in (0,10,11,12)) c where  p.ncontractid =c.id and d.nContractPlanID=p.id \n");
		bufferSQL.append(" union \n");
		bufferSQL.append(" select c.NCURRENCYID,c.sapplycode code,c.ntypeid,c.nborrowclientid,c.nconsignclientid,d.mamount,d.dtplandate ,c.mChargeRate \n");
		bufferSQL.append(" from (select a.id,a.nloanid from loan_LoanFormPlan  a, \n");
		bufferSQL.append(" (select nloanid,max(nplanversion) nplanversion from loan_LoanFormPlan  group by nloanid ) b \n");
		bufferSQL.append("  where a.nloanid=b.nloanid and a.nplanversion=b.nplanversion) p, \n");
		bufferSQL.append(" (select * from loan_LoanFormPlandetail where npaytypeid= " + payTypeID + " and dtplandate >= ?  and  dtplandate <= ? ) d, \n");
		bufferSQL.append(" (select * from loan_loanForm where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid in (" + strLoanTypeCodition + ") and nStatusID in (2)) c \n");
		bufferSQL.append(" where  p.nloanid =c.id and d.nPlanID=p.id \n");
		
		if( payTypeID == 1 )
		{
			System.out.println("1	自营贷款发放本金相关数据的预测  \n"+bufferSQL.toString());
		}
		else if (payTypeID == 2)
		{
			System.out.println("2	自营贷款收回本金相关数据的预测  \n"+bufferSQL.toString());
		}
		else if (payTypeID == 3)
		{
			System.out.println("3	委托贷款发放本金相关数据的预测  \n"+bufferSQL.toString());
		}
		else if (payTypeID == 4)
		{
			System.out.println("4	委托贷款本金收回相关数据的预测  \n"+bufferSQL.toString());
		}
		
			
		

		try 
		{
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			localPS.setTimestamp(1, startDate);
			localPS.setTimestamp(2, endDate);
			localPS.setTimestamp(3, startDate);
			localPS.setTimestamp(4, endDate);
			ResultSet localRS = executeQuery();
			while (localRS.next()) 
			{
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				contractPlanInfo.setCurrencyID(localRS.getLong("NCURRENCYID"));
				contractPlanInfo.setAmount(localRS.getDouble("mamount"));
				contractPlanInfo.setBorrowClientID(localRS.getLong("nborrowclientid"));
				contractPlanInfo.setChargeRate(localRS.getDouble("mChargeRate"));
				contractPlanInfo.setConsignClientID(localRS.getLong("nconsignclientid"));
				contractPlanInfo.setContractCode(localRS.getString("code"));
				contractPlanInfo.setPlanDate(localRS.getTimestamp("dtplandate"));
				contractPlanInfo.setTypeID(localRS.getLong("ntypeid"));
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	/**
	 * 根据条件获取贴现贷款发放预计信息
	 */
	public Collection getDiscountGrantForecastInfoByCondtion(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		bufferSQL.append(" select sapplycode code,nborrowclientid, dtdiscountdate, mexamineamount, mcheckamount from loan_loanForm  \n");
		bufferSQL.append(" where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid = 5 and nStatusID = 2  and dtdiscountdate >= ? and dtdiscountdate <= ? \n");
		bufferSQL.append(" union \n");
		bufferSQL.append(" select scontractcode code,nborrowclientid, dtdiscountdate, mexamineamount, mcheckamount from loan_contractForm \n");
		bufferSQL.append(" where nOfficeID =  " + officeID + " and nCurrencyID = " + currencyID + "  and ntypeid  = 5 and nStatusID not in (0,10,11,12) and dtdiscountdate >= ? and dtdiscountdate <= ? ");
		
		System.out.println("5	取贴现贷款发放预计信息  \n"+bufferSQL.toString());
		
		ArrayList list = new ArrayList();

		try 
		{
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			localPS.setTimestamp(1, startDate);
			localPS.setTimestamp(2, endDate);
			localPS.setTimestamp(3, startDate);
			localPS.setTimestamp(4, endDate);
			ResultSet localRS = executeQuery();
			while (localRS.next()) 
			{
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				contractPlanInfo.setContractCode(localRS.getString("code"));
				contractPlanInfo.setBorrowClientID(localRS.getLong("nborrowclientid"));
				contractPlanInfo.setDiscountDate(localRS.getTimestamp("dtdiscountdate"));
				contractPlanInfo.setExamineAmount(localRS.getDouble("mexamineamount"));
				contractPlanInfo.setCheckAmount(localRS.getDouble("mcheckamount"));
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	/**
	 * 根据条件获取贴现票据到期收回本金/利息相关数据的预测
	 */

	public Collection getDiscountRepaymentForecastInfoByCondtion(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		String strForcastStartDate = transferTimestampToTo_DateString(startDate);
		String strForcastEndDate = transferTimestampToTo_DateString(endDate);
		StringBuffer bufferSQL = new StringBuffer();
		bufferSQL.append(" select a.dtEnd dtEnd,a.mcheckamount mcheckamount,a.mamount mamount,a.SCODE code,b.nclientid nclientid from  \n");
		bufferSQL.append(" (select * from loan_DiscountContractBill bill \n");
		bufferSQL.append(" where bill.nstatusid = 1 and bill.dtend >= " + strForcastStartDate + " and bill.dtend <= " + strForcastEndDate );
		bufferSQL.append(" and bill.nDiscountCredenceID > 0) a, \n");
		bufferSQL.append(" (select id,NBORROWCLIENTID nclientid from loan_ContractForm \n");
		bufferSQL.append(" where ntypeid = 5 and nOfficeID = 1 and nCurrencyID = " + currencyID + " and nofficeid = " + officeID + " and nStatusID not in (0,10,11,12) \n");
		bufferSQL.append("  )b where a.nContractid = b.id \n");
		bufferSQL.append(" union \n");
		bufferSQL.append(" select a.dtEnd,a.mcheckamount mcheckamount,a.mamount mamount,a.SCODE code,b.nclientid nclientid from \n");
		bufferSQL.append(" (select dtEnd,mcheckamount ,mamount,SCODE, nloanid from loan_DiscountFormBill where nstatusid=1 and dtend >= " + strForcastStartDate + " and dtend <= " + strForcastEndDate );
		bufferSQL.append(" ) a ,(select id,NBORROWCLIENTID nclientid from loan_loanForm where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid = 5 and nStatusID in (1,2))b \n");
		bufferSQL.append(" where a.nloanid = b.id \n");
		
		System.out.println("6	贴现票据到期收回本金/利息相关数据的预测  \n"+bufferSQL.toString());
		
		ArrayList list = new ArrayList();

		try
		{
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());

			ResultSet localRS = executeQuery();
			while (localRS.next()) 
			{
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				contractPlanInfo.setContractCode(localRS.getString("code"));
				contractPlanInfo.setBorrowClientID(localRS.getLong("nclientid"));
				contractPlanInfo.setDiscountDate(localRS.getTimestamp("dtEnd"));
				// contractPlanInfo.setExamineAmount(localRS.getDouble("mexamineamount"));
				contractPlanInfo.setCheckAmount(localRS.getDouble("mcheckamount"));
				contractPlanInfo.setAmount(localRS.getDouble("mamount"));
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	/**
	 * 根据条件获取银团贷款放款/收回贷款预计信息
	 */
	public Collection getYTLoanForcastInfoByCondition(long officeID, long currencyID, Timestamp startDate,Timestamp endDate, long payTypeID) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		String strLoanTypeCodition = "";

		ArrayList list = new ArrayList();
		bufferSQL.append("select c.NCURRENCYID,c.scontractcode code,c.ntypeid,c.nborrowclientid,c.nconsignclientid,d.mamount,d.dtplandate ,c.mChargeRate ,y.mrate \n");
		bufferSQL.append(" from (select a.id,a.ncontractid from loan_LoanContractPlan a, \n");
		bufferSQL.append(" (select ncontractid,max(nplanversion) nplanversion \n");
		bufferSQL.append(" from loan_LoanContractPlan group by ncontractid ) b \n");
		bufferSQL.append(" where a.ncontractid=b.ncontractid and a.nplanversion=b.nplanversion) p, \n");
		bufferSQL.append(" (select * from loan_LoanContractPlandetail where npaytypeid= " + payTypeID + " and dtplandate >= ? and dtplandate <= ? ) d, \n");
		bufferSQL.append(" (select * from loan_ContractForm where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid = 8 ");
		bufferSQL.append("  and nStatusID not in (0,10,11,12)) c, \n");
		bufferSQL.append("   (select * from loan_YT_LoanContractBankAssign where nishead=1) y \n");
		bufferSQL.append(" where  p.ncontractid =c.id and d.nContractPlanID=p.id and y.ncontractid=c.id \n");
		bufferSQL.append(" union \n");
		bufferSQL.append(" select c.NCURRENCYID,c.sapplycode code,c.ntypeid,c.nborrowclientid,c.nconsignclientid,d.mamount,d.dtplandate ,c.mChargeRate,y.mrate \n");
		bufferSQL.append(" from (select a.id,a.nloanid from loan_LoanFormPlan  a, \n");
		bufferSQL.append(" (select nloanid,max(nplanversion) nplanversion from loan_LoanFormPlan  group by nloanid ) b \n");
		bufferSQL.append("  where a.nloanid=b.nloanid and a.nplanversion=b.nplanversion) p, \n");
		bufferSQL.append(" (select * from loan_LoanFormPlandetail where npaytypeid= " + payTypeID + " and dtplandate >= ? and dtplandate <= ? ) d, \n");
		bufferSQL.append(" (select * from loan_loanForm where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid =8 and nStatusID in (2)) c, \n");
		bufferSQL.append("  (select * from loan_YT_LoanFormBankAssign where nishead=1) y ");
		bufferSQL.append(" where  p.nloanid =c.id and d.nPlanID=p.id and y.nloanid=c.id \n");

		try {
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			localPS.setTimestamp(1, startDate);
			localPS.setTimestamp(2, endDate);
			localPS.setTimestamp(3, startDate);
			localPS.setTimestamp(4, endDate);
			ResultSet localRS = executeQuery();
			while (localRS.next()) 
			{
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				getLoanContractInfoFromRS(localRS, contractPlanInfo);
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	/**
	 * 查找该客户所属第一个活期子账户
	 */
	public long getFirstSubAccountIDOfClient(long clientID) throws Exception
	{

		long subAccountID;
		try {
			subAccountID = -1;
			String strSQL = "select id from sett_subaccount where \n" + " naccountid = (select id from sett_account a where nclientid = " + clientID + " and naccounttypeid = 1 and nstatusid > 0 and rownum = 1) and rownum = 1";
			PreparedStatement localPS = prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) {
				subAccountID = localRS.getLong("id");
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return subAccountID;

	}


	public Collection getLoanContractPlanInfoForInterestSettlemenet(long officeID, long currencyID, long ZYorWT) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		String loanTypeField = "";
		if (ZYorWT == 1) {
			loanTypeField = "1,2,6,7";
		}
		else {
			loanTypeField = "3,4";
		}
		ArrayList list;
		try {
			list = new ArrayList();
			bufferSQL.append(" select id,1 source,  ncurrencyid, scontractcode code,ntypeid,nborrowclientid,nconsignclientid, minterestrate ,mChargeRate \n");
			bufferSQL.append(" from loan_ContractForm where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid in (" + loanTypeField + ") and nStatusID not in (0,10,11,12) \n");
			bufferSQL.append(" union \n");
			bufferSQL.append(" select id,2 source,ncurrencyid, sapplycode code,ntypeid,nborrowclientid,nconsignclientid, minterestrate ,mChargeRate \n");
			bufferSQL.append(" from loan_loanForm where nOfficeID = " + officeID + " and nCurrencyID = " + currencyID + " and ntypeid in (" + loanTypeField + ") and nStatusID in (2) \n");
			prepareStatement(bufferSQL.toString());
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				contractPlanInfo.setCurrencyID(localRS.getLong("NCURRENCYID"));
				// contractPlanInfo.setAmount(localRS.getDouble("mamount"));
				contractPlanInfo.setBorrowClientID(localRS.getLong("nborrowclientid"));
				contractPlanInfo.setChargeRate(localRS.getDouble("mChargeRate"));
				contractPlanInfo.setRate(localRS.getDouble("minterestrate"));
				contractPlanInfo.setConsignClientID(localRS.getLong("nconsignclientid"));
				contractPlanInfo.setContractCode(localRS.getString("code"));
				contractPlanInfo.setTypeID(localRS.getLong("ntypeid"));
				contractPlanInfo.setId(localRS.getLong("id"));
				contractPlanInfo.setSource(localRS.getLong("source"));
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	public Collection getYTLoanContractPlanInfoForInterestSettlemenet(long officeID, long currencyID) throws Exception
	{

		ArrayList list;
		try {
			StringBuffer bufferSQL = new StringBuffer();
			list = new ArrayList();
			bufferSQL.append(" select a.id,1 source,  a.ncurrencyid, a.scontractcode code,a.ntypeid,a.nborrowclientid,a.nconsignclientid, a.minterestrate ,a.mChargeRate,b.mrate \n");
			bufferSQL.append(" from loan_ContractForm a,loan_YT_LoanContractBankAssign b where a.nOfficeID = " + officeID + " and a.nCurrencyID = " + currencyID + " and a.ntypeid in (8) and a.nStatusID not in (0,10,11,12) and b.nishead=1 and b.ncontractid=a.id \n");
			bufferSQL.append(" union \n");
			bufferSQL.append(" select a.id,2 source,a.ncurrencyid, a.sapplycode code,a.ntypeid,a.nborrowclientid,a.nconsignclientid, a.minterestrate ,a.mChargeRate,b.mrate \n");
			bufferSQL.append(" from loan_loanForm a, loan_YT_LoanformBankAssign b where a.nOfficeID = " + officeID + " and a.nCurrencyID = " + currencyID + " and a.ntypeid in (8) and a.nStatusID in (2)  and b.nishead=1 and b.nloanid=a.id \n");
			prepareStatement(bufferSQL.toString());
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				contractPlanInfo.setCurrencyID(localRS.getLong("NCURRENCYID"));
				contractPlanInfo.setBorrowClientID(localRS.getLong("nborrowclientid"));
				contractPlanInfo.setChargeRate(localRS.getDouble("mChargeRate"));
				contractPlanInfo.setRate(localRS.getDouble("minterestrate"));
				contractPlanInfo.setConsignClientID(localRS.getLong("nconsignclientid"));
				contractPlanInfo.setContractCode(localRS.getString("code"));
				contractPlanInfo.setTypeID(localRS.getLong("ntypeid"));
				contractPlanInfo.setId(localRS.getLong("id"));
				contractPlanInfo.setSource(localRS.getLong("source"));
				contractPlanInfo.setRate(localRS.getDouble("mrate"));
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	// --对于合同：交易明细
	public Collection getInfoFromLoanContractPlanForForecastInterest(long contractID) throws Exception
	{

		ArrayList list;
		try {
			StringBuffer bufferSQL = new StringBuffer();
			list = new ArrayList();
			bufferSQL.append(" select d.dtplandate, decode(d.npaytypeid,1,1,2,-1)*d.mamount mamount \n");
			bufferSQL.append(" from (select a.id,a.ncontractid from loan_LoanContractPlan  a, \n");
			bufferSQL.append(" (select ncontractid,max(nplanversion) nplanversion \n");
			bufferSQL.append(" from loan_LoanContractPlan where ncontractid= " + contractID + "  group by ncontractid ) b \n");
			bufferSQL.append(" where a.ncontractid=b.ncontractid and a.nplanversion=b.nplanversion) p, \n");
			bufferSQL.append(" (select * from loan_LoanContractPlandetail ) d where  d.nContractPlanID=p.id order by d.dtplandate \n");
			prepareStatement(bufferSQL.toString());
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				contractPlanInfo.setAmount(localRS.getDouble("mamount"));
				contractPlanInfo.setPlanDate(localRS.getTimestamp("dtplandate"));
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	// --对于申请：交易明细
	public Collection getInfoFromContractFormLoanformPlanForForecastInterest(long loanID) throws Exception
	{

		ArrayList list;
		try {
			StringBuffer bufferSQL = new StringBuffer();
			list = new ArrayList();
			bufferSQL.append(" select d.dtplandate, decode(d.npaytypeid,1,1,2,-1)*d.mamount mamount \n");
			bufferSQL.append(" from (select a.id,a.nloanid from loan_LoanformPlan  a, \n");
			bufferSQL.append(" (select nloanid,max(nplanversion) nplanversion \n");
			bufferSQL.append(" from loan_LoanformPlan where nloanid= " + loanID + " group by nloanid ) b \n");
			bufferSQL.append(" where a.nloanid=b.nloanid and a.nplanversion=b.nplanversion) p, \n");
			bufferSQL.append(" (select * from loan_LoanformPlandetail ) d where  d.nPlanID=p.id order by d.dtplandate \n");
			prepareStatement(bufferSQL.toString());
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				LoanContractPlanInfo contractPlanInfo = new LoanContractPlanInfo();
				contractPlanInfo.setAmount(localRS.getDouble("mamount"));
				contractPlanInfo.setPlanDate(localRS.getTimestamp("dtplandate"));
				list.add(contractPlanInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}


	/**
	 * @param accountParam
	 * @param
	 * @return
	 * @throws
	 */
	private void getLoanContractInfoFromRS(ResultSet localRS, LoanContractPlanInfo contractPlanInfo) throws SQLException
	{

		contractPlanInfo.setCurrencyID(localRS.getLong("NCURRENCYID"));
		contractPlanInfo.setAmount(localRS.getDouble("mamount"));
		contractPlanInfo.setBorrowClientID(localRS.getLong("nborrowclientid"));
		contractPlanInfo.setChargeRate(localRS.getDouble("mChargeRate"));
		contractPlanInfo.setRate(localRS.getDouble("mRate"));
		contractPlanInfo.setConsignClientID(localRS.getLong("nconsignclientid"));
		contractPlanInfo.setContractCode(localRS.getString("code"));
		contractPlanInfo.setPlanDate(localRS.getTimestamp("dtplandate"));
		contractPlanInfo.setTypeID(localRS.getLong("ntypeid"));
	}
}
