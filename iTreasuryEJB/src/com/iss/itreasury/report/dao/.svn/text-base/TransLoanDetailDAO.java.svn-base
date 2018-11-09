package com.iss.itreasury.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.report.dateentity.TransLoanGatherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;

public class TransLoanDetailDAO {
/**
 * create by yoyLi 2010-12-16
 * @param info
 * @return
 * @throws Exception
 */
	public Collection getTransLoanList(TransLoanGatherInfo info) throws Exception
	{
		Collection list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransLoanGatherInfo gatherInfo = null;
		int iTag = 1;
		StringBuffer sbSQL = new StringBuffer();
		if(info.getTsQueryDateString() == null || "".equals(info.getTsQueryDateString().trim()))
		{
			throw new Exception("请输入查询起始日期!");
		}
		if(info.getTsEndDate() == null || "".equals(info.getTsEndDate().trim()))
		{
			throw new Exception("请输入查询结束日期!");
		}
		String startDate = info.getTsQueryDateString();
		String endDate   = info.getTsEndDate();
		String loanType  = info.getTsLoanType();
		sbSQL.append("SELECT executedate tsTransDate,clientname strClientName,contractcode strLoanContractCode,rate dbLoanRate,accountno strLoanAccountNo,interestclear tsLoanEndDate,abstract strAbstract,grantloan dbGrantLoanAmount,repayment dbRepaymentAmount,amount dbLoanAmount \n");
		
		sbSQL.append(" from ");
		sbSQL.append("( \n");
		sbSQL.append(" select pay.dtexecute executedate, \n");
		sbSQL.append(" cli.name clientname,\n");
		sbSQL.append(" form.scontractcode contractcode,\n");
		sbSQL.append(" form.minterestrate rate,\n");
		sbSQL.append(" acc.saccountno accountno,\n");
//		sbSQL.append(" pay.dtinterestclear interestclear,\n");
		sbSQL.append(" form.dtenddate interestclear, \n");
		sbSQL.append(" pay.sabstract abstract,\n");
		sbSQL.append(" 0.000000 / 10000 grantloan,\n");
		sbSQL.append(" pay.mamount / 10000 repayment,\n");
		sbSQL.append(" form.mloanamount / 10000 amount\n");
		sbSQL.append(" from sett_transrepaymentloan pay,\n");
		sbSQL.append(" client_clientinfo cli,\n");
		sbSQL.append(" loan_contractform form,\n");
		sbSQL.append(" sett_subaccount sub,\n");
		sbSQL.append(" sett_account acc \n");
		sbSQL.append(" where form.nborrowclientid = cli.id \n");
		sbSQL.append(" and pay.nsubaccountid = sub.id \n");
		sbSQL.append(" and sub.naccountid = acc.id \n");
		sbSQL.append(" and pay.nloancontractid = form.id \n");
		sbSQL.append(" and cli.id = ? \n");
		sbSQL.append(" and pay.dtexecute >= to_date('" + startDate + "','yyyy-mm-dd') \n");
		sbSQL.append(" and pay.dtexecute <= to_date('" + endDate + "','yyyy-mm-dd') \n");
		sbSQL.append(" and pay.nstatusid = " + SETTConstant.TransactionStatus.CHECK);
		sbSQL.append(" and pay.ntransactiontypeid in (" + SETTConstant.TransactionType.TRUSTLOANRECEIVE + "," + SETTConstant.TransactionType.CONSIGNLOANRECEIVE + ") ");
		sbSQL.append(" and form.nstatusid in (" + LOANConstant.ContractStatus.OVERDUE + "," + LOANConstant.ContractStatus.ACTIVE + "," + LOANConstant.ContractStatus.EXTEND + "," + LOANConstant.ContractStatus.FINISH + "," + LOANConstant.ContractStatus.DELAYDEBT + "," + LOANConstant.ContractStatus.BADDEBT + ") \n");
		if(loanType.equals("zy"))
		{
			sbSQL.append(" and form.ntypeid = " + LOANConstant.LoanType.ZY);
		}else if(loanType.equals("wd"))
		{
			sbSQL.append(" and form.ntypeid = " + LOANConstant.LoanType.WT);
		}
		sbSQL.append(" union all \n");
		sbSQL.append(" select fang.dtexecute executedate,\n");
		sbSQL.append(" cli.name clientname,\n");
		sbSQL.append(" form.scontractcode contractcode,\n");
		sbSQL.append(" form.minterestrate rate,\n");
		sbSQL.append(" acc.saccountno accountno,\n");
//		sbSQL.append(" fang.dtinterestclear interestclear, \n");
		sbSQL.append(" form.dtenddate interestclear, \n");
		sbSQL.append(" fang.sabstract abstract,\n");
		sbSQL.append(" fang.mamount / 10000 grantloan,\n");
		sbSQL.append(" 0 / 10000 repayment,\n");
		sbSQL.append(" form.mloanamount / 10000 amount \n");
		sbSQL.append(" from sett_transgrantloan fang, \n");
		sbSQL.append(" client_clientinfo cli, \n");
		sbSQL.append(" loan_contractform  form,\n");
		sbSQL.append(" loan_loanform ll, \n");
		sbSQL.append(" sett_account acc \n");
		sbSQL.append(" where form.nloanid = ll.id \n");
		sbSQL.append(" and ll.nborrowclientid = cli.id \n");
		sbSQL.append(" and fang.nloanaccountid = acc.id \n");
		sbSQL.append(" and fang.nloancontractid = form.id \n");
		sbSQL.append(" and cli.id = ? \n");
		sbSQL.append(" and fang.dtexecute >= to_date('" + startDate + "','yyyy-mm-dd') \n");
		sbSQL.append(" and fang.dtexecute <= to_date('" + endDate + "','yyyy-mm-dd') \n");
		sbSQL.append(" and fang.nstatusid = " + SETTConstant.TransactionStatus.CHECK);
		sbSQL.append(" and fang.ntransactiontypeid in (" + SETTConstant.TransactionType.TRUSTLOANGRANT + "," + SETTConstant.TransactionType.CONSIGNLOANGRANT + ") ");
		sbSQL.append(" and form.nstatusid in (" + LOANConstant.ContractStatus.OVERDUE + "," + LOANConstant.ContractStatus.ACTIVE + "," + LOANConstant.ContractStatus.EXTEND + "," + LOANConstant.ContractStatus.FINISH + "," + LOANConstant.ContractStatus.DELAYDEBT + "," + LOANConstant.ContractStatus.BADDEBT + ") \n");
		if(loanType.equals("zy"))
		{
			sbSQL.append(" and form.ntypeid = " + LOANConstant.LoanType.ZY);
		}else if(loanType.equals("wd"))
		{
			sbSQL.append(" and form.ntypeid = " + LOANConstant.LoanType.WT);
		}
		sbSQL.append(" ) \n");
		sbSQL.append(" order by tstransdate,strloancontractcode asc \n");
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(sbSQL.toString());
			if(info.getLClientID() > 0)
			{
				ps.setLong(iTag++, info.getLClientID());
				ps.setLong(iTag++, info.getLClientID());
			}
			rs = ps.executeQuery();
			while(rs.next())
			{
				gatherInfo = new TransLoanGatherInfo();
				gatherInfo.setStrClientName(rs.getString("strClientName"));              //单位名称
				gatherInfo.setStrLoanContractCode(rs.getString("strLoanContractCode"));  //合同编号
				gatherInfo.setDbLoanRate(rs.getDouble("dbLoanRate"));                    //利率
				gatherInfo.setStrLoanAccountNo(rs.getString("strLoanAccountNo"));        //贷款账户编号
				gatherInfo.setTsLoanEndDate(rs.getTimestamp("tsLoanEndDate"));           //贷款到期日
				gatherInfo.setStrAbstract(rs.getString("strAbstract"));                  //摘要
				gatherInfo.setDbGrantLoanAmount(rs.getDouble("dbGrantLoanAmount"));      //贷款放款金额
				gatherInfo.setDbRepaymentAmount(rs.getDouble("dbRepaymentAmount"));      //贷款还款金额
				gatherInfo.setDbLoanAmount(rs.getDouble("dbLoanAmount"));                //贷款金额
				gatherInfo.setTsTransDate(rs.getTimestamp("tsTransDate"));               //贷款交易日
				
				list.add(gatherInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ps!=null)
				{
					ps.close();
					ps=null;
				}
				if(rs!=null)
				{
					rs.close();
					rs=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		System.out.println(sbSQL);
		return list;
	}
	
	public Double getBegainMoney(TransLoanGatherInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Double money = null;
		int iTag = 1;
		StringBuffer sbSQL = new StringBuffer();
		if(info.getTsQueryDateString() == null || "".equals(info.getTsQueryDateString().trim()))
		{
			throw new Exception("请输入查询起始日期!");
		}
		if(info.getTsEndDate() == null || "".equals(info.getTsEndDate().trim()))
		{
			throw new Exception("请输入查询结束日期!");
		}
		String startDate = info.getTsQueryDateString();
		sbSQL.append(" SELECT sum(nvl(sd.mbalance,0))/10000 balance ");
		sbSQL.append(" from sett_dailyaccountbalance sd, ");
		sbSQL.append("      sett_subaccount          ss, ");
		sbSQL.append("      loan_contractform        lc, ");
		sbSQL.append("      loan_payform             lp, ");
		sbSQL.append("      loan_loanform            ll ");
		sbSQL.append(" where sd.dtdate = to_date('" + startDate + "','yyyy-mm-dd') - 1 ");
		sbSQL.append(" and sd.nsubaccountid = ss.id ");
		sbSQL.append(" and ss.al_nloannoteid = lp.id ");
		sbSQL.append(" and lp.ncontractid = lc.id ");
		sbSQL.append(" and lc.nloanid = ll.id ");
		sbSQL.append(" and ll.nborrowclientid = ? ");
		sbSQL.append(" and lc.nstatus in (" + LOANConstant.ContractStatus.OVERDUE + "," + LOANConstant.ContractStatus.ACTIVE + "," + LOANConstant.ContractStatus.EXTEND + "," + LOANConstant.ContractStatus.FINISH + "," + LOANConstant.ContractStatus.DELAYDEBT + "," + LOANConstant.ContractStatus.BADDEBT + ") \n");
		sbSQL.append(" group by ll.nborrowclientid ");
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(sbSQL.toString());
			if(info.getLClientID() > 0)
			{
				ps.setLong(iTag++, info.getLClientID());
			}
			rs = ps.executeQuery();
			while(rs.next())
			{
				money = new Double(rs.getDouble("balance"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ps!=null)
				{
					ps.close();
					ps=null;
				}
				if(rs!=null)
				{
					rs.close();
					rs=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if(money == null || money.doubleValue() <= 0)
		{
			money = new Double(0.00);
		}
		System.out.println(sbSQL);
		return money;
	}
}
