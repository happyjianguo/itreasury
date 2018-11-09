package com.iss.itreasury.loan.report.dao;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import java.util.ArrayList;

import com.iss.itreasury.loan.report.dataentity.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.loanpaynotice.dao.*;
import com.iss.itreasury.loan.freeapply.dataentity.FreeApplyInfo;
import com.iss.itreasury.loan.extendapply.dataentity.ExtendContractInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.*;
public class ReportDao
{
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

	private void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	public Collection getWTLoanDetail(ReportCondition condition) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		ArrayList aList = new ArrayList();
		long tmpWTID = -1;
		long tmpClientID = -1;
		double sumPay = 0;
		double sumBalance = 0;
		double sumCharge = 0;
		double sumInterest = 0;
		double sumLoanInterest = 0;
		double totalPay = 0;
		double totalBalance = 0;
		double totalCharge = 0;
		double totalInterest = 0;
		double totalLoanInterest = 0;
		double sumTotalPay = 0;
		double sumTotalBalance = 0;
		double sumTotalCharge = 0;
		double sumTotalInterest = 0;
		double sumTotalLoanInterest = 0;
		long sumTotalCount = 0;
		long sNo = 0;
		long officeID=condition.getOfficeID() ;
		long currencyID=condition.getCurrencyID() ;
		String strBeginDate=DataFormat.getDateString(condition.getClearDateFrom()) ;
		String strEndDate=DataFormat.getDateString(condition.getClearDateTo()) ;
		//String strBeginDate="2002-01-01";
		//String strEndDate="2003-12-15";
		//long officeID=1;
		//long currencyID=1;
		//condition.setClearDateTo(Env.getSystemDate() );

		try
		{
			con = Database.getConnection();
			strSQL ="select c1.sName as borrowClientName, c.nBorrowClientID,"
				+" c2.sName as consignClientName,c.nConsignClientID,c.sContractCode,c.nIntervalNum,"
				+" s.Al_Nloannoteid,s.ID,s.Naccountid,s.mopenAmount,c.ID as contractID,c3.dtStart,c3.dtEnd"
				+" from loan_contractform c,client c1,client c2,sett_subaccount s,loan_payform c3"
				+" where c1.id(+)=c.Nborrowclientid"
				//+" and TRUNC(s.dtopen)>=To_Date('" + strBeginDate + "','yyyy-mm-dd') "
				//+" and TRUNC(s.dtopen)<=To_Date('" + strEndDate + "','yyyy-mm-dd') "
				+" and c2.id(+)=c.Nconsignclientid "
				+" and s.Al_Nloannoteid is not null "
				+" and s.AL_NloanNoteid>0 "
				+" and c3.ID(+)=s.Al_Nloannoteid "
				+" and c.id=c3.nContractID"
				+" and c.ntypeid in ("+LOANConstant.LoanType.WT+" ) "
				+" 	and s.Al_Nloannoteid in "
				+"("
				+" 	select distinct nLoanNoteID from"
				+" 	("
				+" 	select NLOANNOTEID"
				+" 	from sett_transgrantloan where nStatusID>=3"
				+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	UNION ALL"
				+" 	select NLOANNOTEID"
				+" 	from sett_transRepaymentloan where nStatusID>=3"
				+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	union all"
				+" 	select b.Al_nLoanNoteID as nLoanNoteID"
				+" 	from sett_TransInterestSettlement a,sett_SubAccount b"
				+" 	where a.nSubAccountID=b.ID and a.nIsKeepAccount = 1 and a.nStatusID in  (3,4,5,6)"
				+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	union all"
				+" 	select distinct b.AL_nLoanNoteID as nLoanNoteID"
				+" 	from sett_dailyaccountbalance a,sett_subaccount b"
				+" 	where a.Nsubaccountid=b.ID"
				+" 	and TRUNC(dtdate)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtdate)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	and a.mBalance>0"
				+" 	)"
				+") "
				+" and s.nstatusid>0 ";

			/*加上查询条件*/
			if (condition != null)
			{

				if (condition.getBorrowIDFrom() > 0)
					strSQL += " and c.nBorrowClientID>=" + condition.getBorrowIDFrom();
				if (condition.getBorrowIDTo() > 0)
					strSQL += " and c.nBorrowClientID<=" + condition.getBorrowIDTo();
				if (condition.getConsignIDFrom() > 0)
					strSQL += " and c.nConsignClientID>=" + condition.getConsignIDFrom();
				if (condition.getConsignIDTo() > 0)
					strSQL += " and c.nConsignClientID<=" + condition.getConsignIDTo();
				if (condition.getContractCodeFrom() != null && condition.getContractCodeFrom().length() > 0)
					strSQL += " and c.sContractCode>='" + condition.getContractCodeFrom() + "'";
				if (condition.getContractCodeTo() != null && condition.getContractCodeTo().length() > 0)
					strSQL += " and c.sContractCode<='" + condition.getContractCodeTo() + "'";
			}
			strSQL += " order by c.nconsignclientid asc ,c.nborrowclientid asc ,c.id asc, s.dtOpen asc";
			log4j.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				/*新的借款单位，增加借款单位小计*/
				if (rs.getLong("nBorrowClientID") != tmpClientID && tmpClientID != -1)
				{
					ReportWTLoanDetail wtDetail = new ReportWTLoanDetail();
					wtDetail.setClientName("小计");
					wtDetail.setPayAmount(sumPay);
					wtDetail.setBalance(sumBalance);
					wtDetail.setCharge(sumCharge);
					wtDetail.setInterest(sumInterest);
					wtDetail.setSumLoanInterest(sumLoanInterest);
					aList.add(wtDetail);

					/*将借款单位小计清零*/
					sumPay = 0;
					sumBalance = 0;
					sumCharge = 0;
					sumInterest = 0;
					sumLoanInterest = 0;
				}

				/*新的委托单位，增加委托分组*/
				if (rs.getLong("nConsignClientID") != tmpWTID)
				{
					/*如果已经有委托记录，应该首先增加委托单位和计*/
					if (tmpWTID != -1)
					{
						/*如果刚好新的借款单位和上个借款单位是同一个单位，也要小计*/
						if (tmpClientID == rs.getLong("nBorrowClientID"))
						{
							ReportWTLoanDetail wtDetail = new ReportWTLoanDetail();
							wtDetail.setClientName("小计");
							wtDetail.setPayAmount(sumPay);
							wtDetail.setBalance(sumBalance);
							wtDetail.setCharge(sumCharge);
							wtDetail.setInterest(sumInterest);
							wtDetail.setSumLoanInterest(sumLoanInterest);
							aList.add(wtDetail);

							/*将借款单位小计清零*/
							sumPay = 0;
							sumBalance = 0;
							sumCharge = 0;
							sumInterest = 0;
							sumLoanInterest = 0;
						}
						ReportWTLoanDetail wtDetail = new ReportWTLoanDetail();
						wtDetail.setClientName("委托单位合计");
						wtDetail.setPayAmount(totalPay);
						wtDetail.setBalance(totalBalance);
						wtDetail.setCharge(totalCharge);
						wtDetail.setInterest(totalInterest);
						wtDetail.setSumLoanInterest(totalLoanInterest);
						aList.add(wtDetail);

						/*将委托单位合计清零*/
						totalPay = 0;
						totalBalance = 0;
						totalCharge = 0;
						totalInterest = 0;
						totalLoanInterest = 0;
					}
				}
				long accountID=rs.getLong("nAccountID");
				long subAccountID=rs.getLong("ID");
				long noticeID=rs.getLong("Al_Nloannoteid");

				double[] balanceInfo={0,0};
				double commission=0;
				try
				{
					balanceInfo=getHistoryBalance(con,accountID,subAccountID,condition.getClearDateTo());
					commission=getHistoryCommission(officeID,currencyID,accountID,subAccountID,condition.getClearDateTo(),noticeID);
				}catch (Exception ee){
					ee.printStackTrace();
				}


				/*增加一条放款记录*/
				ReportWTLoanDetail wtDetail = new ReportWTLoanDetail();
				wtDetail.setWtClientName(rs.getString("consignClientName"));
				wtDetail.setClientName(rs.getString("borrowClientName"));
				wtDetail.setContractCode(rs.getString("sContractCode"));
				wtDetail.setIntervalNum(rs.getLong("nIntervalNum"));
				wtDetail.setPayAmount(rs.getDouble("mOpenAmount"));
				wtDetail.setStartDate(rs.getTimestamp("dtStart"));
				wtDetail.setEndDate(rs.getTimestamp("dtEnd"));
				wtDetail.setBalance(balanceInfo[0]);
				wtDetail.setCharge(commission);
				wtDetail.setInterest(balanceInfo[1]);
				wtDetail.setSumLoanInterest(balanceInfo[0]+balanceInfo[1]);
				wtDetail.setNo(++sNo);
				aList.add(wtDetail);

				/*保存临时标示*/
				tmpWTID = rs.getLong("nConsignClientID");
				tmpClientID = rs.getLong("nBorrowClientID");

				/*保存借款单位合计*/
				sumPay += rs.getDouble("mOpenAmount");
				sumBalance += balanceInfo[0];
				sumCharge += commission;
				sumInterest += balanceInfo[1];
				sumLoanInterest += balanceInfo[0]+balanceInfo[1];

				/*保存委托单位合计*/
				totalPay += rs.getDouble("mOpenAmount");
				totalBalance += balanceInfo[0];
				totalCharge += commission;
				totalInterest += balanceInfo[1];
				totalLoanInterest += balanceInfo[0]+balanceInfo[1];

				/*保存委托总合计*/
				sumTotalPay += rs.getDouble("mOpenAmount");
				sumTotalBalance += balanceInfo[0];
				sumTotalCharge += commission;
				sumTotalInterest += balanceInfo[1];
				sumTotalLoanInterest += balanceInfo[0]+balanceInfo[1];
				sumTotalCount++;
			}

			/*如果有记录，则要对前边的数据进行小计，合计和总计*/
			if (sumTotalCount > 0)
			{
				/*进行小计*/
				ReportWTLoanDetail wtDetail1 = new ReportWTLoanDetail();
				wtDetail1.setClientName("小计");
				wtDetail1.setPayAmount(sumPay);
				wtDetail1.setBalance(sumBalance);
				wtDetail1.setCharge(sumCharge);
				wtDetail1.setInterest(sumInterest);
				wtDetail1.setSumLoanInterest(sumLoanInterest);
				aList.add(wtDetail1);

				/*进行合计*/
				ReportWTLoanDetail wtDetail2 = new ReportWTLoanDetail();
				wtDetail2.setClientName("委托单位合计");
				wtDetail2.setPayAmount(totalPay);
				wtDetail2.setBalance(totalBalance);
				wtDetail2.setCharge(totalCharge);
				wtDetail2.setInterest(totalInterest);
				wtDetail2.setSumLoanInterest(totalLoanInterest);
				aList.add(wtDetail2);

				/*进行总计*/
				ReportWTLoanDetail wtDetail = new ReportWTLoanDetail();
				wtDetail.setRecordType(5);
				wtDetail.setWtClientName("委托贷款总计");
				wtDetail.setPayAmount(sumTotalPay);
				wtDetail.setBalance(sumTotalBalance);
				wtDetail.setCharge(sumTotalCharge);
				wtDetail.setInterest(sumTotalInterest);
				wtDetail.setSumLoanInterest(sumTotalLoanInterest);
				aList.add(wtDetail);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}

		return aList;
	}

	public Collection getLoanDetail(ReportCondition condition) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		ArrayList aList = new ArrayList();
		long tmpType = -1;
		long tmpClientID = -1;
		double sumPay = 0;
		double sumBalance = 0;
		double totalPay = 0;
		double totalBalance = 0;
		double sumTotalPay = 0;
		double sumTotalBalance = 0;
		long sumCount = 0;
		long totalCount = 0;
		long sumTotalCount = 0;
		String strBeginDate=DataFormat.getDateString(condition.getClearDateFrom()) ;
		String strEndDate=DataFormat.getDateString(condition.getClearDateTo()) ;
		long officeID=condition.getOfficeID() ;
		long currencyID=condition.getCurrencyID() ;
		//String strBeginDate="2002-01-01";
		//String strEndDate="2003-12-15";
		//condition.setClearDateTo(Env.getSystemDate() );

		try
		{
			con = Database.getConnection();
			strSQL="select c1.sName as borrowClientName,c.nBorrowClientID,c1.sname as clientTypeName,c1.nclienttypeid1 as nLoanClientTypeID," 
//				+"c1.Nloanclienttypeid," 
       			+" s.Al_Nloannoteid,c3.DtStart,c.dtenddate,c.sContractCode,c.ID as contractID "
                +" ,nvl(s.mopenAmount,0) mopenAmount"
       			+" ,MONTHS_BETWEEN(c3.Dtend,To_Date('" + strEndDate + "','yyyy-mm-dd')) as intervalNumLeft ,"
       			+" s.id,s.nAccountID,c.nIntervalNum,"
       			+" c.Nrisklevel,c.Niscredit,c.Nisassure,c.Nispledge,c.Nisimpawn," 
       			+" c.nTypeID1,c.Ntypeid2,c.Ntypeid3";

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */

//       		if(Env.getProjectName().equals(Constant.ProjectName.CEC))	
//       		{
//				strSQL += " ,c.Ntypeid4 ";
//       		}

       		//以中电子为准，去掉项目判断
			strSQL += " ,c.Ntypeid4 ";
			
			/*  TOCONFIG—END  */
			
			strSQL +=" from loan_contractform c,client c1,sett_subaccount s," 
//				+"loan_clienttype c2," 
				+"loan_payForm c3 "
				+" where c1.id(+)=c.Nborrowclientid"
//				+" and c2.id(+)=c1.Nloanclienttypeid"
				+" and s.Al_Nloannoteid is not null"
				+" and s.AL_NloanNoteid>0 "
				+" and c3.ID(+)=s.Al_Nloannoteid "
				+" and c.ID=c3.ncontractid"
				+" and c.ntypeid in ( "+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.ZGXE +","+LOANConstant.LoanType.YT +" ) "
				+" 	and s.Al_Nloannoteid in "
				+"("
				+" 	select distinct nLoanNoteID from "
				+" 	("
				+" 	select NLOANNOTEID"
				+" 	from sett_transgrantloan where nStatusID>=3"
				+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	UNION ALL"
				+" 	select NLOANNOTEID"
				+" 	from sett_transRepaymentloan where nStatusID>=3"
				+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	union all"
				+" 	select b.Al_nLoanNoteID as nLoanNoteID"
				+" 	from sett_TransInterestSettlement a,sett_SubAccount b"
				+" 	where a.nSubAccountID=b.ID and a.nIsKeepAccount = 1 and a.nStatusID in  (3,4,5,6)"
				+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	union all"
				+" 	select distinct b.AL_nLoanNoteID as nLoanNoteID"
				+" 	from sett_dailyaccountbalance a,sett_subaccount b"
				+" 	where a.Nsubaccountid=b.ID"
				+" 	and TRUNC(dtdate)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')"
				+" 	and TRUNC(dtdate)<=To_Date('"+strEndDate+"','yyyy-mm-dd')"
				+" 	and a.mBalance>0"
				+" 	)"
				+") "
				+" and c.nCurrencyID= "+currencyID
				+" and c.nOfficeID= "+officeID
				+" and s.nstatusid>0 ";

			/*加上查询条件*/
			if (condition != null)
			{
				if (condition.getBorrowIDFrom() > 0)
					strSQL += " and c.nBorrowClientID>=" + condition.getBorrowIDFrom();
				if (condition.getBorrowIDTo() > 0)
					strSQL += " and c.nBorrowClientID<=" + condition.getBorrowIDTo();
				if (condition.getContractCodeFrom() != null && condition.getContractCodeFrom().length() > 0)
					strSQL += " and c.sContractCode>='" + condition.getContractCodeFrom() + "'";
				if (condition.getContractCodeTo() != null && condition.getContractCodeTo().length() > 0)
					strSQL += " and c.sContractCode<='" + condition.getContractCodeTo() + "'";
			}
			strSQL += " order by c.Nborrowclientid asc" 		
//					+",c1.Nloanclienttypeid asc"
					+", c.id asc, s.dtOpen asc" ;

			ps = con.prepareStatement(strSQL);
			log4j.print(strSQL);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$strSQL:"+strSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{

				/*新的借款单位，增加借款单位小计*/
				if (rs.getLong("nBorrowClientID") != tmpClientID && tmpClientID != -1)
				{
					ReportLoanDetail loanDetail = new ReportLoanDetail();
					loanDetail.setClientName("小计");
					loanDetail.setContractCode(String.valueOf(sumCount));
					loanDetail.setPayAmount(sumPay);
					loanDetail.setBalance(sumBalance);
					aList.add(loanDetail);

					/*将借款单位小计清零*/
					sumCount = 0;
					sumPay = 0;
					sumBalance = 0;
				}

				/*新的类型，增加类型分组*/
				if (rs.getLong("nLoanClientTypeID") != tmpType)/////////////////////////////
				{
					/*如果已经有贷款类型分组记录，应该首先增加贷款分组和计*/
					if (tmpType != -1)
					{
						/*如果刚好新的借款单位和上个借款单位是同一个单位，也要小计*/
						if (tmpClientID == rs.getLong("nBorrowClientID"))
						{
							ReportLoanDetail loanDetail = new ReportLoanDetail();
							loanDetail.setClientName("小计");
							loanDetail.setContractCode(String.valueOf(sumCount));
							loanDetail.setPayAmount(sumPay);
							loanDetail.setBalance(sumBalance);
							aList.add(loanDetail);

							/*将借款单位小计清零*/
							sumCount = 0;
							sumPay = 0;
							sumBalance = 0;
						}
						ReportLoanDetail loanDetail = new ReportLoanDetail();
						loanDetail.setClientTypeName("合计");
						loanDetail.setContractCode(String.valueOf(totalCount));
						loanDetail.setPayAmount(totalPay);
						loanDetail.setBalance(totalBalance);
						aList.add(loanDetail);

						/*将委托单位合计清零*/
						totalCount = 0;
						totalPay = 0;
						totalBalance = 0;
					}
				}

				/*增加一条放款记录*/
				ReportLoanDetail loanDetail = new ReportLoanDetail();
				loanDetail.setClientTypeName(rs.getString("clientTypeName"));
				loanDetail.setClientName(rs.getString("borrowClientName"));
				loanDetail.setContractCode(rs.getString("sContractCode"));
				loanDetail.setPayAmount(rs.getDouble("mOpenAmount"));

				/*获得余额信息*/
				long accountID=rs.getLong("nAccountID");
				long subAccountID=rs.getLong("ID");
				double[] balanceInfo=getHistoryBalance(con,accountID,subAccountID,condition.getClearDateTo() );
				loanDetail.setBalance(balanceInfo[0]);

				/*获得利率信息*/
				long nID = rs.getLong("contractID");
				ContractDao dao = new ContractDao();
				RateInfo rateInfo = dao.getLatelyRate(-1, nID, condition.getClearDateTo() );
				loanDetail.setContractRate(rateInfo.getRate());
				long nPayID=rs.getLong("Al_Nloannoteid");
				LoanPayNoticeDao pDao=new LoanPayNoticeDao();
				double payRate=pDao.getLatelyRate(nPayID,null);
				loanDetail.setInterestRate(payRate);

				/*获得风险状态，地区，行业名称*/
				loanDetail.setRiskLevalName(LOANConstant.VentureLevel.getName(rs.getLong("nRiskLevel")));
				loanDetail.setTypeName1(LOANConstant.AreaType.getName(rs.getLong("nTypeID1")));

				/*  TOCONFIG—TODELETE  */
				/*
				 * 产品化不再区分项目 
				 * ninh 
				 * 2005-03-24
				 */

//				if(Env.getProjectName().equals(Constant.ProjectName.CEC))	
//				{
//					loanDetail.setTypeName4(LOANConstant.IndustryType3.getName(rs.getLong("nTypeID4")));
//				}
				
				//以中电子为准，去掉项目判断
				loanDetail.setTypeName4(LOANConstant.IndustryType3.getName(rs.getLong("nTypeID4")));
			
				/*  TOCONFIG—END  */
				
				loanDetail.setTypeName2(LOANConstant.IndustryType1.getName(rs.getLong("nTypeID2")));
				loanDetail.setTypeName3(LOANConstant.IndustryType2.getName(rs.getLong("nTypeID3")));

				/*获得担保方式*/
				String tmpAssure = "";
				if (rs.getLong("nIsAssure") == 1)
				{
					tmpAssure += LOANConstant.AssureType.getName(LOANConstant.AssureType.ASSURE);
				}
				if (rs.getLong("nIsCredit") == 1)
				{
					tmpAssure += " " + LOANConstant.AssureType.getName(LOANConstant.AssureType.CREDIT);
				}
				if (rs.getLong("nIsPledge") == 1)
				{
					tmpAssure += " " + LOANConstant.AssureType.getName(LOANConstant.AssureType.PLEDGE);
				}
				if (rs.getLong("nIsImpawn") == 1)
				{
					tmpAssure += " " + LOANConstant.AssureType.getName(LOANConstant.AssureType.IMPAWN);
				}
				loanDetail.setAssureType(tmpAssure);
				loanDetail.setBeginInterest(rs.getTimestamp("dtStart"));
				loanDetail.setEndInterest(rs.getTimestamp("dtEndDate"));
				loanDetail.setIntervalNum(rs.getLong("nIntervalNum"));
				/*计算剩余期限*/
				if (rs.getLong("intervalNumLeft") <= 0)
				{
					loanDetail.setIntervalNumLeft(0);
				}
				else
				{
					long a = (long) rs.getDouble("intervalNumLeft");
					if (rs.getDouble("intervalNumLeft") > a)
						a++;
					loanDetail.setIntervalNumLeft(a);
				}

				aList.add(loanDetail);

				/*保存临时标示*/
				tmpType = rs.getLong("nLoanClientTypeID");///////////////////////////////////
				tmpClientID = rs.getLong("nBorrowClientID");

				/*保存借款单位合计*/
				sumCount++;
				sumPay += rs.getDouble("mOpenAmount");
				sumBalance += balanceInfo[0];

				/*保存单位类型合计*/
				totalCount++;
				totalPay += rs.getDouble("mOpenAmount");
				totalBalance += balanceInfo[0];

				/*保存总合计*/
				sumTotalCount++;
				sumTotalPay += rs.getDouble("mOpenAmount");
				sumTotalBalance += balanceInfo[0];
			}
			/*如果前边有记录，则应该对上边最后一组做小计，合计，总计*/
			if (sumTotalCount > 0)
			{
				/*小计*/
				ReportLoanDetail loanDetail1 = new ReportLoanDetail();
				loanDetail1.setClientName("小计");
				loanDetail1.setContractCode(String.valueOf(sumCount));
				loanDetail1.setPayAmount(sumPay);
				loanDetail1.setBalance(sumBalance);
				aList.add(loanDetail1);

				/*合计*/
				ReportLoanDetail loanDetail2 = new ReportLoanDetail();
				loanDetail2.setClientTypeName("合计");
				loanDetail2.setContractCode(String.valueOf(totalCount));
				loanDetail2.setPayAmount(totalPay);
				loanDetail2.setBalance(totalBalance);
				aList.add(loanDetail2);

				/*进行总计*/
				ReportLoanDetail loanDetail = new ReportLoanDetail();
				loanDetail.setClientTypeName("总计");
				loanDetail.setContractCode(String.valueOf(sumTotalCount));
				loanDetail.setPayAmount(sumTotalPay);
				loanDetail.setBalance(sumTotalBalance);
				aList.add(loanDetail);
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}

		return aList;
	}

	public Collection getCreditDetail(CreditReportCondition condition) throws Exception
	{
		Connection con = null;
		Connection tmpCon=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		ArrayList aList = new ArrayList();
		long tmpType = -1;
		String tmpTypeName="";
		long tmpClientID = -1;

		double tmpBalance=0;
		double tmpIn=0;
		double tmpOut=0;

		Timestamp BeginDate=condition.getStartDate();
		Timestamp FinishDate=condition.getEndDate();
		int interval = 1;
		long sNo=1;

		CreditReportCell[] sumInfo=null;
		CreditReportCell[] totalInfo=null;
		Timestamp[] startDate=null;
		Timestamp[] endDate=null;
		double sumCredit=0;
		double totalCredit=0;
		double sumUnUsedCredit=0;
                double sumUnGrant=0;
		double totalUnUsedCredit=0;
		double creditAmount=0;
		Timestamp currentSystemDate=null;
		try
		{
			con = Database.getConnection();
			tmpCon=Database.getConnection() ;
			currentSystemDate=Env.getSystemDate( condition.getOfficeID() ,condition.getCurrencyID());

			//确定名细列区间个数
			if (condition.getReportType()==3)//名细表
			{

				strSQL="select MONTHS_BETWEEN(TO_DATE('"+DataFormat.getDateString(FinishDate)+"','yyyy-mm-dd'),"
					+"TO_DATE('"+DataFormat.getDateString(BeginDate)+"','yyyy-mm-dd') ) from dual";
				ps=con.prepareStatement( strSQL );
				log4j.print( "第一个strSQL："+strSQL );
				System.out.println("第一个strSQL："+strSQL );
				rs=ps.executeQuery() ;
				if (rs.next() )
				{
					interval=rs.getInt( 1 )+1;
				}
			}
			cleanup(rs);
			cleanup(ps);

			sumInfo=new CreditReportCell[interval+1];
			totalInfo=new CreditReportCell[interval+1];
			for (int i=0;i<=interval;i++)
			{
				sumInfo[i]=new CreditReportCell();
				totalInfo[i]=new CreditReportCell();
			}

			startDate=new Timestamp[interval+1];
			endDate=new Timestamp[interval+1];

			//确定列内容（确定每组时间范围）

			endDate[0]=DataFormat.getLastDateOfMonth( DataFormat.getPreviousMonth( BeginDate ,1));
			startDate[0]=DataFormat.getFirstDateOfMonth( endDate[0]);

			if (condition.getReportType()==3)//名细表
			{
				for (int i=1;i<=interval;i++)
				{
					startDate[i]=DataFormat.getNextMonth( startDate[i-1],1);
					endDate[i]=DataFormat.getLastDateOfMonth( startDate[i]);
				}
				if (endDate[interval].after( currentSystemDate ))
				{
					endDate[interval]=DataFormat.getPreviousDate( currentSystemDate );
				}
			}
			else
			{
				startDate[1]=DataFormat.getFirstDateOfMonth( BeginDate );
				endDate[1]=DataFormat.getLastDateOfMonth( FinishDate);
				if (endDate[1].after( currentSystemDate ))
				{
					endDate[1]=DataFormat.getPreviousDate( currentSystemDate );
				}

			}

			//组装表头
			CreditReportDetailInfo title=new CreditReportDetailInfo();
			title.setLineType( 1 );
			title.setSNo( "序号");
			title.setClientName("  电  厂  ");
			title.setCreditLevel( "信用等级");
			title.setCreditAmount("综合授信额度");
			title.setUnUsedCredit( "未使用额度");
                        title.setUnGrant("未发放余额") ;
			title.setRemark( "备注");

			CreditReportCell[] titleCell=new CreditReportCell[interval+1];
			titleCell[0]=new CreditReportCell();
			titleCell[0].setBalanceTitle(DataFormat.getChineseMonthString( endDate[0])+"底余额");
			if (condition.getReportType()==3)	//名细
			{
				for (int i=1;i<=interval;i++)
				{
					titleCell[i]=new CreditReportCell();
					titleCell[i].setInTitle( DataFormat.getChineseMonthString( startDate[i])+"发放");
					titleCell[i].setOutTitle( DataFormat.getChineseMonthString( startDate[i])+"收回");
					titleCell[i].setBalanceTitle( DataFormat.getChineseMonthString( startDate[i])+"底余额");
				}
			}
			else if (condition.getReportType()==1) //最近使用情况
			{
				titleCell[1]=new CreditReportCell();
				titleCell[1].setInTitle( DataFormat.getChineseMonthString( startDate[1])+"发放");
				titleCell[1].setOutTitle( DataFormat.getChineseMonthString( startDate[1])+"收回");
				titleCell[1].setBalanceTitle( DataFormat.getChineseMonthString( startDate[1])+"底余额");
			}
			else		//统计表
			{
				titleCell[1]=new CreditReportCell();
				titleCell[1].setInTitle( DataFormat.getChineseMonthString( startDate[1])+"到"+DataFormat.getChineseMonthString( endDate[1])+"发放");
				titleCell[1].setOutTitle( DataFormat.getChineseMonthString( startDate[1])+"到"+DataFormat.getChineseMonthString( endDate[1])+"收回");
				titleCell[1].setBalanceTitle( DataFormat.getChineseMonthString( endDate[1])+"底余额");
			}
			title.setCellInfo( titleCell);

			aList.add(title);

			//取符合条件的企业列表
			strSQL="select c1.sName as clientName,c.amount,c.clientID,c1.nCreditLevelID" 
//				+",c1.Nloanclienttypeid" 
				+",c2.sname as clientTypeName "
				+" from loan_creditLimit c,client c1" 
//				+",loan_clienttype c2 "
				+" where c1.id(+)=c.clientid"
//				+" and c2.id(+)=c1.Nloanclienttypeid"
//				+" and c1.nloanclienttypeid>-1"
				+" and c.statusID in ("+LOANConstant.CreditStatus.ACTIVE
				+","+LOANConstant.CreditStatus.FREEZE
				+")";

			if (condition != null)
			{
				if (condition.getStartClientID()  > 0)
					strSQL += " and c.clientID>=" + condition.getStartClientID() ;
				if (condition.getEndClientID()  > 0)
					strSQL += " and c.clientID<=" + condition.getEndClientID();
				if (condition.getClientName().length()>0)
				strSQL += " and c1.sName like'%" + condition.getClientName()+"%'";
			}
			strSQL += " order by " 
//					+"c1.Nloanclienttypeid asc," 
					+"c.clientID asc";

			ps = con.prepareStatement(strSQL);
			log4j.print("第二个strSQL：:"+strSQL);
			System.out.println("第二个strSQL："+strSQL );
			rs = ps.executeQuery();


			while (rs.next())
			{
				/*新的类型，增加类型分组*/
				if (rs.getLong("nLoanClientTypeID") != tmpType)
				{
					/*如果已经有贷款类型分组记录，增加分组和计信息*/
					if (tmpType != -1)
					{
						CreditReportDetailInfo detailInfo = new CreditReportDetailInfo();
						System.out.println("ClientName:"+tmpTypeName);
						if (tmpTypeName==null)
						{
							detailInfo.setClientName("小计");	
						}
						else
						{	
						detailInfo.setClientName(tmpTypeName+"小计");
						}
						detailInfo.setLineType( 3 );
						//授信金额小计
						detailInfo.setCreditAmount( DataFormat.formatDisabledAmount( sumCredit/10000));

						CreditReportCell[] cellInfo=new CreditReportCell[interval+1];
						//保存区间前的余额
						cellInfo[0]=new CreditReportCell();
						cellInfo[0].setBalance( sumInfo[0].getBalance() );

						//取其他月份金额信息
						for (int i=1;i<=interval;i++)
						{
							cellInfo[i]=new CreditReportCell();
							cellInfo[i].setOutAmount( sumInfo[i].getOutAmount() );
							cellInfo[i].setBalance(sumInfo[i].getBalance());
							cellInfo[i].setInAmount( sumInfo[i].getInAmount()  );
						}

						detailInfo.setCellInfo( cellInfo );

						//保存未使用授信额度信息
						detailInfo.setUnUsedCredit( DataFormat.formatDisabledAmount( sumUnUsedCredit/10000 ) );

						//保存区间内金额信息
						aList.add(detailInfo);

						//清小组小计信息
						sumCredit=0;
						sumInfo[0].setBalance( 0 );
						sumUnUsedCredit=0;
						for (int i=1;i<=interval;i++)
						{
							//保存小计金额
							sumInfo[i].setBalance( 0 );
							sumInfo[i].setInAmount( 0 );
							sumInfo[i].setOutAmount( 0 );
						}

					}
				}

				//增加一条企业记录
				CreditReportDetailInfo detailInfo = new CreditReportDetailInfo();
				detailInfo.setClientName(rs.getString("clientName"));
				detailInfo.setSNo( String.valueOf(sNo++) );
				detailInfo.setLineType( 2 );
				detailInfo.setCreditLevel( LOANConstant.CreditLevel.getName(rs.getLong("nCreditLevelID")));
				creditAmount=rs.getDouble("amount");

				//保存小计授信金额
				sumCredit+=creditAmount;
				totalCredit+=creditAmount;

				detailInfo.setCreditAmount(DataFormat.formatDisabledAmount(creditAmount/10000));
				tmpClientID=rs.getLong("clientID");
				CreditReportCell[] cellInfo=new CreditReportCell[interval+1];

				//取区间前一个月的余额
				tmpBalance=this.getHistoryBalance(tmpCon,tmpClientID,endDate[0],-1,-1 );
				cellInfo[0]=new CreditReportCell();
				cellInfo[0].setBalance(tmpBalance);
				System.out.println("******************");
				System.out.println("期间前余额："+tmpBalance);
				System.out.println("******************");

				//保存小计区间前一个余额
				sumInfo[0].setBalance(sumInfo[0].getBalance()+cellInfo[0].getBalance() );
				totalInfo[0].setBalance(totalInfo[0].getBalance() +cellInfo[0].getBalance() );

				//取其他月份金额信息
				for (int i=1;i<=interval;i++)
				{
					cellInfo[i]=new CreditReportCell();
					cellInfo[i].setOutAmount( this.getGrantAmount( tmpCon,tmpClientID,startDate[i],endDate[i]));
					cellInfo[i].setBalance(this.getHistoryBalance( tmpCon,tmpClientID,endDate[i],-1,-1));
					cellInfo[i].setInAmount( this.getRepayAmount( tmpCon,tmpClientID,startDate[i],endDate[i]) );

					//保存小计金额
					sumInfo[i].setBalance( sumInfo[i].getBalance() + cellInfo[i].getBalance() );
					sumInfo[i].setInAmount( sumInfo[i].getInAmount() + cellInfo[i].getInAmount() );
					sumInfo[i].setOutAmount( sumInfo[i].getOutAmount() + cellInfo[i].getOutAmount() );

					totalInfo[i].setBalance( totalInfo[i].getBalance() + cellInfo[i].getBalance() );
					totalInfo[i].setInAmount( totalInfo[i].getInAmount() + cellInfo[i].getInAmount() );
					totalInfo[i].setOutAmount( totalInfo[i].getOutAmount() + cellInfo[i].getOutAmount() );
				}
				detailInfo.setCellInfo( cellInfo );

                                //取未发放余额
				//保存未使用授信额度信息
				detailInfo.setUnUsedCredit( DataFormat.formatDisabledAmount( (creditAmount- cellInfo[interval].getBalance())/10000 ) );
				sumUnUsedCredit+=creditAmount- cellInfo[interval].getBalance();
				totalUnUsedCredit+=creditAmount- cellInfo[interval].getBalance();

				aList.add(detailInfo);

				/*保存临时标示*/
//				tmpType = rs.getLong("nLoanClientTypeID");
				tmpTypeName=rs.getString("clientTypeName");
			}

			//如果前边有记录，则应该对上边最后一组做小计
			if (sNo > 0)
			{
				CreditReportDetailInfo detailInfo = new CreditReportDetailInfo();
				detailInfo.setClientName(tmpTypeName+"小计");
				detailInfo.setLineType( 3 );
				//授信金额小计
				detailInfo.setCreditAmount( DataFormat.formatDisabledAmount( sumCredit/10000));

				CreditReportCell[] cellInfo=new CreditReportCell[interval+1];
				//保存区间前的余额
				cellInfo[0]=new CreditReportCell();
				cellInfo[0].setBalance( sumInfo[0].getBalance() );

				//保存区间内的金额
				for (int i=1;i<=interval;i++)
				{
					cellInfo[i]=new CreditReportCell();
					cellInfo[i].setOutAmount( sumInfo[i].getOutAmount() );
					cellInfo[i].setBalance(sumInfo[i].getBalance());
					cellInfo[i].setInAmount( sumInfo[i].getInAmount()  );
				}

				detailInfo.setCellInfo( cellInfo );

				//保存未使用授信额度信息
				detailInfo.setUnUsedCredit( DataFormat.formatDisabledAmount( sumUnUsedCredit/10000 ) );

				//保存区间内金额信息
				aList.add(detailInfo);

				//清小组小计信息
				sumCredit=0;
				sumInfo[0].setBalance( 0 );
				sumUnUsedCredit=0;
				for (int i=1;i<=interval;i++)
				{
					//保存小计金额
					sumInfo[i].setBalance( 0 );
					sumInfo[i].setInAmount( 0 );
					sumInfo[i].setOutAmount( 0 );
				}
			}

			//如果前边有纪录，做合计
			if (sNo > 0)
			{
				CreditReportDetailInfo detailInfo = new CreditReportDetailInfo();
				detailInfo.setClientName("授信企业贷款合计");
				detailInfo.setLineType( 3 );
				//授信金额合计
				detailInfo.setCreditAmount( DataFormat.formatDisabledAmount( totalCredit/10000));

				CreditReportCell[] cellInfo=new CreditReportCell[interval+1];
				//保存区间前的余额
				cellInfo[0]=new CreditReportCell();
				cellInfo[0].setBalance( totalInfo[0].getBalance() );

				//保存区间内的金额
				for (int i=1;i<=interval;i++)
				{
					cellInfo[i]=new CreditReportCell();
					cellInfo[i].setOutAmount( totalInfo[i].getOutAmount() );
					cellInfo[i].setBalance(totalInfo[i].getBalance());
					cellInfo[i].setInAmount( totalInfo[i].getInAmount()  );
				}

				detailInfo.setCellInfo( cellInfo );

				//保存未使用授信额度信息
				detailInfo.setUnUsedCredit( DataFormat.formatDisabledAmount( totalUnUsedCredit/10000 ) );

				//保存区间内金额信息
				aList.add(detailInfo);
			}

			{	//取未授信企业信息
				double tmpDouble=0;
				CreditReportDetailInfo detailInfo = new CreditReportDetailInfo();
				detailInfo.setClientName("未授信企业贷款合计");
				detailInfo.setLineType( 3 );

				CreditReportCell[] cellInfo=new CreditReportCell[interval+1];
				//保存区间内的金额
				for (int i=0;i<=interval;i++)
				{
					//保存区间前的余额
					tmpDouble=this.getHistoryBalance( tmpCon,-1,endDate[i],condition.getStartClientID() ,condition.getEndClientID() );
					System.out.println("******************");
					System.out.println("总余额"+tmpDouble);
					System.out.println("******************");
					cellInfo[i]=new CreditReportCell();
					cellInfo[i].setBalance( tmpDouble - totalInfo[i].getBalance() );
					totalInfo[i].setBalance( tmpDouble );
				}

				detailInfo.setCellInfo( cellInfo );
				//保存区间内金额信息
				aList.add(detailInfo);
			}

			//最后贷款总计
			{
				CreditReportDetailInfo detailInfo = new CreditReportDetailInfo();
				detailInfo.setClientName("贷款合计");
				detailInfo.setLineType( 3 );

				CreditReportCell[] cellInfo=new CreditReportCell[interval+1];
				//保存区间内的金额
				for (int i=0;i<=interval;i++)
				{
					//保存区间前的余额
					cellInfo[i]=new CreditReportCell();
					cellInfo[i].setBalance( totalInfo[i].getBalance() );
					cellInfo[i].setInAmount( totalInfo[i].getInAmount() );
					cellInfo[i].setOutAmount( totalInfo[i].getOutAmount() );
				}

				detailInfo.setCellInfo( cellInfo );
				//保存区间内金额信息
				aList.add(detailInfo);
			}


			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			cleanup(tmpCon);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			cleanup(tmpCon);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			cleanup(tmpCon);
		}

		return aList;
	}

	/**
	 * @param accountID 父账户
	 * @param subAccountID 子账户
	 * @param endDate 日期
	 * @return 0：余额 1：利息
	 * @throws Exception
	 */
	public double[] getHistoryBalance(Connection con,long accountID,long subAccountID,Timestamp endDate) throws Exception
	{
		double result[]=new double[2];
		//Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		try
		{
			if ( (accountID<=0)||(subAccountID<=0)||(endDate==null) )
				return result;

			//con=Database.getConnection() ;

			strSQL="select nvl(mbalance,0) mbalance,nvl(mInterest,0) mInterest "
				+" from SETT_DAILYACCOUNTBALANCE"
				+" where nAccountID="+accountID
				+" and nSubAccountID="+subAccountID
				+" and TRUNC(dtDate) = TRUNC(to_date('"+ DataFormat.formatDate(endDate) +"','yyyy-mm-dd')) "
                +" order by dtdate desc ";
			log4j.print("getBalance "+strSQL);
			ps=con.prepareStatement(strSQL);
			//ps.setTimestamp(1,endDate);
			rs=ps.executeQuery() ;
			if (rs.next())
			{
				result[0]=rs.getDouble("mBalance");
				result[1]=rs.getDouble("mInterest");
				//Log.print(result[0]);
				//Log.print(result[1]);
			}
			cleanup(rs);
			cleanup(ps);
			//cleanup(con);
		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			//cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			//cleanup(con);
		}
		return result;
	}

	public double getRepayAmount(Connection con,long clientID,Timestamp startDate,Timestamp endDate) throws Exception
	{
		double result=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		try
		{
			strSQL="select sum(mAmount)"
				+" from ( "
				+ " select mAmount "
				+ " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
				+ SETTConstant.TransactionStatus.CHECK
				+ " and a.nClientID="+ clientID
				+" and b.id=a.nLoanContractID"
				+" and b.nTypeID in (1,2,6,7,8,10)"
				+" and a.dtExecute>=TO_DATE('"+DataFormat.getDateString(startDate)+"','yyyy-mm-dd')"
				+" and a.dtExecute<=TO_DATE('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd')"
				+ " union all "
				+ " select mAmount "
				+ " from Sett_Transrepaymentdiscount "
				+ " where nStatusID>="
				+ SETTConstant.TransactionStatus.CHECK
				+ " and nClientID="+ clientID
				+" and dtExecute>=TO_DATE('"+DataFormat.getDateString(startDate)+"','yyyy-mm-dd')"
				+" and dtExecute<=TO_DATE('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd')"
				+" ) ";

			log4j.print("getRepayAmount For:"+clientID+strSQL);
			ps=con.prepareStatement(strSQL);
			rs=ps.executeQuery() ;
			if (rs.next())
			{
				result=rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);

		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
		}
		return result;

	}
	public double getGrantAmount(Connection con,long clientID,Timestamp startDate,Timestamp endDate) throws Exception
	{
		double result=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		try
		{
			strSQL="select sum(mAmount)"
				+" from ( "
				+ " select a.mAmount "
				+ " from sett_transgrantloan a,loan_ContractForm b where a.nStatusID>="
				+ SETTConstant.TransactionStatus.CHECK
				+" and b.id=a.nLoanContractID"
				+" and b.nTypeID in (1,2,6,7,8,10)"
				+ " and b.nBorrowClientID="+ clientID
				+" and a.dtExecute>=TO_DATE('"+DataFormat.getDateString(startDate)+"','yyyy-mm-dd')"
				+" and a.dtExecute<=TO_DATE('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd')"
				+ " union all "
				+ " select a.MDISCOUNTBILLAMOUNT as mAmount"
				+ " from SETT_TRANSGRANTDISCOUNT a,loan_contractForm b "
				+ " where a.nStatusID>="
				+ SETTConstant.TransactionStatus.CHECK
				+" and b.id= a.nDisCountContractID"
				+ " and b.nBorrowClientID="+ clientID
				+" and a.dtExecute>=TO_DATE('"+DataFormat.getDateString(startDate)+"','yyyy-mm-dd')"
				+" and a.dtExecute<=TO_DATE('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd')"
				+" ) ";

			log4j.print("getGrantAmount For:"+clientID+strSQL);
			ps=con.prepareStatement(strSQL);
			rs=ps.executeQuery() ;
			if (rs.next())
			{
				result=rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);

		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
		}
		return result;
	}

	/**
	 * @param accountID 父账户
	 * @param subAccountID 子账户
	 * @param endDate 日期
	 * @return 0：余额 1：利息
	 * @throws Exception
	 */
	public double getHistoryBalance(Connection con,long clientID,Timestamp endDate,long beginClientID,long endClientID) throws Exception
	{
		double result=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		try
		{
			strSQL="select sum(mbalance)"
				+" from SETT_DAILYACCOUNTBALANCE a, "
				+"("
				+" select s.id "
				+" from loan_contractform c1,sett_subaccount s,loan_payForm c3 "
				+" where 1=1"
				+" and s.Al_Nloannoteid is not null"
				+" and s.AL_NloanNoteid>0 "
				+" and c3.ID(+)=s.Al_Nloannoteid "
				+" and c1.ID=c3.ncontractid"
				+" and c1.nTypeID in (1,2,6,7,8,10)";
			if (clientID>0)
			{
				strSQL+=" and c1.nBorrowClientID="+clientID;
			}
			if (beginClientID>0)
			{
				strSQL+=" and c1.nBorrowClientID>="+beginClientID;
			}
			if (endClientID>0)
			{
				strSQL+= " and c1.nBorrowClientID<="+endClientID;
			}
			strSQL+=" union all"
				+" select s.id "
				+" from loan_contractform c1,sett_subaccount s,LOAN_DISCOUNTCREDENCE c3 "
				+" where 1=1"
				+" and s.Al_Nloannoteid is not null"
				+" and s.AL_NloanNoteid>0 "
				+" and c3.ID(+)=s.Al_Nloannoteid "
				+" and c1.ID=c3.ncontractid";
			if (clientID>0)
			{
				strSQL+=" and c1.nBorrowClientID="+clientID;
			}
			if (beginClientID>0)
			{
				strSQL+=" and c1.nBorrowClientID>="+beginClientID;
			}
			if (endClientID>0)
			{
				strSQL+= " and c1.nBorrowClientID<="+endClientID;
			}

			strSQL+=") b"
				+" where a.dtDate=TO_DATE('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd')"
				+" and a.nSubAccountID=b.ID";
			log4j.print("getBalance For:"+clientID+strSQL);
			ps=con.prepareStatement(strSQL);
			rs=ps.executeQuery() ;
			if (rs.next())
			{
				result=rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);

		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
		}
		return result;
	}
	/**
	 * @param accountID 父账户
	 * @param subAccountID 子账户
	 * @param endDate 日期
	 * @return 0：余额 1：利息
	 * @throws Exception
	 */
	private Vector getOverDueInfo(Connection con,long subAccountID) throws Exception
	{
		Vector v=new Vector();
		//Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		try
		{
			//con=Database.getConnection() ;
			strSQL="select c.mBalance as overDueAmount,"
				+" (sysdate - c.dtClearInterest) as overdueInterval, "
				+" c.mInterest,d.punishInterest"
				+" from sett_subAccount c, "
				+" (select ID,mInterest as punishInterest from sett_subAccount where (sysdate - dtClearInterest)>=90) d"
				+" where d.ID(+)=c.ID"
				+" and c.ID="+subAccountID;

			log4j.print("getOverDue "+strSQL);
			ps=con.prepareStatement(strSQL);
			rs=ps.executeQuery() ;
			if (rs.next())
			{
				Double overDueAmount=new Double(rs.getDouble("overDueAmount"));
				Long overDueInterval=new Long(rs.getLong("overDueInterval"));
				Double interest=new Double(rs.getDouble("mInterest"));
				Double punishAmount=new Double(rs.getLong("punishInterest"));
				v.add(overDueAmount);
				v.add(overDueInterval);
				v.add(interest);
				v.add(punishAmount);
			}
			cleanup(rs);
			cleanup(ps);
			//cleanup(con);
		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			//cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			//cleanup(con);
		}
		return v;
	}

	/**
	 * @param accountID 父账户
	 * @param subAccountID 子账户
	 * @param endDate 日期
	 * @return 0：余额 1：利息
	 * @throws Exception
	 */
	public double getHistoryCommission(long officeID,long currencyID,long accountID,long subAccountID,Timestamp endDate,long noticeID) throws Exception
	{
		double result=0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		double commission=0;
		double payCommission=0;
		try
		{
			if ( (accountID<=0)||(subAccountID<=0)||(endDate==null) )
				return result;

			con=Database.getConnection();

			/*取该时手续费合计*/
			InterestOperation operation=new InterestOperation(con);
			LoanAccountInterestInfo info=operation.getLoanAccountFee(officeID,currencyID,accountID,subAccountID,endDate, endDate,SETTConstant.InterestFeeType.COMMISION);
			commission=info.getInterest();
			log4j.print("get Commssion to Pay:"+commission);

			//上面这个方法取出来就是应付手续费，而不是合计
            //所以后面不用再减已还手续费
            // ninh 2004-06-16

			cleanup(con);

			/*取已还手续费*/
			con=Database.getConnection() ;
			/*
			strSQL= "select sum(mInterest) as mInterest from "
				+" ( "
				+" select mCommission as mInterest from sett_TransRepaymentLoan where nStatusID in (3,4,5,6)"
				+" and nLoanNoteID ="+noticeID
				+" union all "
				+" select a.mInterest from sett_TransInterestSettlement a,sett_SubAccount b "
				+" where a.nSubAccountID=b.ID   and a.nInterestType = 2 and a.nStatusID in  (3,4,5,6)"
				+" and Al_nLoanNoteID ="+noticeID
				+" ) ";
            //
            //========从视图sett_vtransinterestdetail取已还手续费=========//
            strSQL = " select sum(t.interest) mInterest "
             +" from sett_vtransinterestdetail t"
             +" where 1=1"
             +" and t.interesttypeid =2"
             +" and t.payformid =  "+noticeID;

			log4j.print(strSQL);
			ps=con.prepareStatement( strSQL );
			rs=ps.executeQuery() ;
			if ( rs.next() )
			{
				payCommission=rs.getDouble("mInterest");
			}
            //*/
			result=commission;//-payCommission;
			if (result<0) result=0;
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}


	/**
	* 得到利率
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return Collection 执行利率
	* @exception Exception
	*/
	public Collection getRateHistory(long lContractID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Vector v = new Vector();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0)
			{
				sbSQL.append(" SELECT a.dtStartDate,b.mRate, b.ID,c.mAdjustRate ");
				sbSQL.append(" FROM loan_rateAdjustContractDetail a,loan_interestRate b,loan_contractForm c");
				sbSQL.append(" WHERE c.ID = a.nContractID ");
				//sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')");
				sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
				sbSQL.append(" AND a.nContractID = ? ");
				sbSQL.append(" ORDER BY  a.dtStartDate DESC ");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				while (rs.next())
				{
					RateInfo info = new RateInfo();
					info.setLateBasicRate(rs.getDouble("mRate")); //调整后的基准利率
					info.setLateRate(info.getLateBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100));
					//调整后的利率
					info.setLateBankInterestID(rs.getLong("ID"));
					//调整后的基准利率ID
					info.setAdjustDate(rs.getTimestamp("dtStartDate")); //调整时间
					v.addElement(info);
				}
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{

			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* 得到免还情况
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return Collection
	* @exception Exception
	*/
	public Collection getFreeList(long lContractID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Vector v = new Vector();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0)
			{
				sbSQL.append(" SELECT a.dtFreeDate,a.mFreeAmount,a.mInterest");
				sbSQL.append(" FROM loan_freeform a");
				sbSQL.append(" WHERE a.nStatusID = " + LOANConstant.FreeApplyStatus.CHECK);
				sbSQL.append(" AND a.nContractID = ? ");
				sbSQL.append(" ORDER BY a.dtFreeDate ");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				while (rs.next())
				{
					FreeApplyInfo info = new FreeApplyInfo();
					info.setFreeDate(rs.getTimestamp("dtFreeDate"));
					info.setFreeAmount(rs.getDouble("mFreeAmount"));
					info.setAmount(rs.getDouble("mInterest"));
					v.addElement(info);
				}
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{

			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* 得到展期情况
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return Collection
	* @exception Exception
	*/
	public Collection getExtendList(long lContractID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Vector v = new Vector();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0)
			{
				sbSQL.append(" SELECT a.mExtendAmount,a.dtExtendBeginDate,");
				sbSQL.append(" a.dtExtendEndDate,b.mInterestAdjust");
				sbSQL.append(" FROM loan_extendDetail a,loan_extendForm b,loan_extendContract c");
				sbSQL.append(" WHERE b.id = a.nExtendFormID");
				sbSQL.append(" AND b.id = c.nExtendID");
				sbSQL.append(" AND c.nStatusID = " + LOANConstant.ExtendStatus.CHECK);
				sbSQL.append(" AND b.nContractID = ? ");
				sbSQL.append(" ORDER BY a.dtExtendBeginDate ");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				while (rs.next())
				{
					ExtendContractInfo info = new ExtendContractInfo();
					info.m_dExtendAmount = rs.getDouble("mExtendAmount")/10000;
					info.m_dExtendInterestRate = rs.getDouble("mInterestAdjust");
					info.m_tsExtendStart = rs.getTimestamp("dtExtendBeginDate");
					info.m_tsExtendEnd = rs.getTimestamp("dtExtendEndDate");
					v.addElement(info);
				}
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{

			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* 得到贷款本金发放，收回情况
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return ReportContractDetailInfo
	* @exception Exception
	*/
	public Collection getCorpusList(long lContractID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Vector v = new Vector();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0)
			{
				sbSQL.append(" SELECT mAmount,dtExecute,1 as payType");
				sbSQL.append(" FROM sett_transgrantloan ");
				sbSQL.append(" WHERE nStatusID>=" + SETTConstant.TransactionStatus.CHECK);
				sbSQL.append(" AND nLoancontractID=? ");
				sbSQL.append(" AND mAmount>0 ");
				sbSQL.append(" UNION ");
				sbSQL.append(" SELECT mAmount,dtExecute, 0 as payType");
				sbSQL.append(" FROM sett_transRepaymentloan ");
				sbSQL.append(" WHERE nStatusID>=" + SETTConstant.TransactionStatus.CHECK);
				sbSQL.append(" AND nLoancontractID=? ");
				sbSQL.append(" AND mAmount>0 ");
				sbSQL.append(" ORDER BY dtExecute");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				ps.setLong(2, lContractID);
				rs = ps.executeQuery();

				double dPayAmount = 0;
				double dRepayAmount = 0;
				double dBalance = 0;

				while (rs.next())
				{
					ReportCorpusInfo info = new ReportCorpusInfo();
					info.setCorpusDate(DataFormat.getDateString(rs.getTimestamp("dtExecute")));
					if (rs.getLong("payType") == 1)
					{
						info.setPayAmount(DataFormat.formatDisabledAmount(rs.getDouble("mAmount")/10000));
						dPayAmount += rs.getDouble("mAmount")/10000;
					}
					else if (rs.getLong("payType") == 0)
					{
						info.setRepayAmount(DataFormat.formatDisabledAmount(rs.getDouble("mAmount")/10000));
						dRepayAmount += rs.getDouble("mAmount")/10000;
					}
					dBalance = dPayAmount - dRepayAmount;
					if (dBalance < 0)
					{
						dBalance = 0;
					}
					info.setBalanceAmount(DataFormat.formatDisabledAmount(dBalance));
					v.addElement(info);
				}
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{

			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return (v.size() > 0 ? v : null);
	}

	/**
	 * 获得国机台账查询的sql语句
	 * @param qInfo
	 * @return
	 */
	private String getGJTZSQL(ReportCondition condition)
	{
		String strSQL="";
		String strBeginDate=DataFormat.getDateString(condition.getClearDateFrom()) ;
		String strEndDate=DataFormat.getDateString(condition.getClearDateTo()) ;
		String clientSQL="";

		if (condition.getBorrowIDFrom()>0)
			clientSQL=" and c.nBorrowClientID="+condition.getBorrowIDFrom();

		String noteSQL="(\n"
		+" 	select distinct nLoanNoteID from\n"
		+" 	(\n"
		+" 	select NLOANNOTEID\n"
		+" 	from sett_transgrantloan where nStatusID>=3\n"
		+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')\n"
		+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')\n"
		+" 	UNION ALL\n"
		+" 	select NLOANNOTEID\n"
		+" 	from sett_transRepaymentloan where nStatusID>=3\n"
		+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')\n"
		+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')\n"
		+" 	union all\n"
		+" 	select b.Al_nLoanNoteID as nLoanNoteID\n"
		+" 	from sett_TransInterestSettlement a,sett_SubAccount b\n"
		+" 	where a.nSubAccountID=b.ID and a.nIsKeepAccount = 1 and a.nStatusID in  (3,4,5,6)\n"
		+" 	and TRUNC(dtexecute)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')\n"
		+" 	and TRUNC(dtexecute)<=To_Date('"+strEndDate+"','yyyy-mm-dd')\n"
		+" 	union all\n"
		+" 	select distinct b.AL_nLoanNoteID as nLoanNoteID\n"
		+" 	from sett_dailyaccountbalance a,sett_subaccount b\n"
		+" 	where a.Nsubaccountid=b.ID\n"
		+" 	and TRUNC(dtdate)>=To_Date('"+strBeginDate+"','yyyy-mm-dd')\n"
		+" 	and TRUNC(dtdate)<=To_Date('"+strEndDate+"','yyyy-mm-dd')\n"
		+" 	and a.mBalance>0\n"
		+" 	)\n"
		+") \n";

	    strSQL="select c1.sName as borrowClientName,c.nBorrowClientID,c.sContractCode,c.mExamineAmount,\n"
	    	+" s2.sCode as payNoteCode,s1.mOpenAmount,s.mAmount as repayAmount,s.dtExecute,c.ID as nContractID,\n"
	    	+"s1.id,s1.nAccountID,c.nStatusID,"
	    	+" s.mRealInterestIncome,s.dtExecute as interestExecute,s1.mInterest,s1.al_nLoanNoteID\n"
	    	+" from loan_contractform c,client c1,sett_subaccount s1,loan_payform s2,sett_transRepaymentloan s \n"
	    	+" where \n"
	    	+" s2.id(+)=s.nLoanNoteID\n"
	    	+" and s1.Al_nLoanNoteID(+)=s.nLoanNoteID\n"
	    	+" and c.ID=s2.nContractID\n"
	    	+" and c1.id(+)=c.nBorrowCLientID\n"
			+" and s.nloannoteid is not null\n"
			+ clientSQL
			+" and (s.mAmount>0 or s.mRealInterestIncome>0)"
			+" and s.nloanNoteid>0 \n"
			+" and c.ntypeid in ( "+LOANConstant.LoanType.ZY+" ,"+LOANConstant.LoanType.ZGXE + " ) \n"
			+" 	and s.nloannoteid in \n"
			+ noteSQL
			+" union all \n"
			+"select c1.sName as borrowClientName,c.nBorrowClientID,c.sContractCode,c.mExamineAmount,\n"
			+" s2.sCode as payNoteCode,s1.mOpenAmount,0 as repayAmount,null as dtExecute,c.ID as nContractID,\n"
			+"s1.id,s1.nAccountID,c.nStatusID,"
			+" s.mInterest as mRealInterestIncome,s.dtExecute as interestExecute,s1.mInterest,s1.al_nLoanNoteID\n"
			+" from loan_contractform c,client c1,sett_subaccount s1,loan_payform s2,sett_TransInterestSettlement s \n"
			+" where \n"
			+" s.nSubAccountID=s1.ID\n"
			+" and s.nInterestType=1\n"
			+" and s.nIsKeepAccount=1\n"
			+" and s.nStatusID in  (3,4,5,6)"
			+" and c.ID=s2.nContractID\n"
			+" and s1.Al_nLoanNoteID=s2.ID\n"
			+" and c1.id(+)=c.nBorrowCLientID\n"
			+" and s1.AL_nloannoteid is not null\n"
			+ clientSQL
			+" and s1.AL_nloanNoteid>0 \n"
			+" and c.ntypeid in ( "+LOANConstant.LoanType.ZY+" ,"+LOANConstant.LoanType.ZGXE + " ) \n"
			+" 	and s1.AL_nloannoteid in \n"
			+ noteSQL;

		return strSQL;
	}

	/**
	 * 为国机台账而设的方法,查询记录集
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */

	public Collection getGJTZ(ReportCondition qInfo) throws Exception
	{
		ArrayList aList=new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			String sql=getGJTZSQL(qInfo);
			log4j.print(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery() ;

			while(rs.next())
			{
				ReportDesk info=new ReportDesk();
				info.setBorrowClientName( rs.getString("borrowClientName"));
				info.setContractCode( rs.getString("sContractCode"));
				info.setLoanAmount( rs.getDouble("mExamineAmount"));
				info.setPayNoteCode( rs.getString("payNoteCode"));
				info.setPayAmount(rs.getDouble("mOpenAmount"));
				info.setRepayAmount( rs.getDouble("repayAmount"));
				info.setRepayTime( rs.getTimestamp("dtExecute"));
				info.setRepayInterest( rs.getDouble("mRealInterestIncome"));
				info.setRepayInterestTime( rs.getTimestamp("interestExecute"));
				//info.setSolidInterest( rs.getDouble("mInterest"));

				/*获得余额信息*/
				long accountID=rs.getLong("nAccountID");
				long subAccountID=rs.getLong("ID");
				double[] balanceInfo=getHistoryBalance(con,accountID,subAccountID,qInfo.getClearDateTo() );
				info.setPayBalance(balanceInfo[0]);
				info.setSolidInterest(balanceInfo[1]);

				//获得逾期信息
				if ( rs.getLong("nStatusID")==LOANConstant.ContractStatus.OVERDUE)
				{
					Vector v=getOverDueInfo(con,subAccountID);
					if (v.size()>0);
					{
						info.setOverDueAmount(((Double)v.get(0)).doubleValue());
						info.setOverDueInterval(((Long)v.get(1)).longValue());
						info.setSolidInterest(((Double)v.get(2)).doubleValue());
						info.setPubishInterest(((Double)v.get(3)).doubleValue());
					}
				}

				/*获得利率信息-合同利率*/
				long nID = rs.getLong("ncontractID");
				ContractDao dao = new ContractDao();
				RateInfo rateInfo = dao.getLatelyRate(-1, nID, qInfo.getClearDateTo() );
				info.setContractRate(rateInfo.getRate());

				/*获得利率信息-执行利率*/
				long nPayID=rs.getLong("Al_Nloannoteid");
				LoanPayNoticeDao pDao=new LoanPayNoticeDao();
				double payRate=pDao.getLatelyRate(nPayID,null);
				info.setInterestRate(payRate);

				aList.add(info);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}

		return aList;
	}


	/**
	* 得到贷款利息，费用收回情况
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return Collection
	* @exception Exception
	*/
	public Collection getRePayList(long lContractID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Vector v = new Vector();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0)
			{/*
				sbSQL.append(" SELECT dtExecute,mRealInterest,mInterest,mCommission,");
				sbSQL.append(" mSuretyFee,mInterestTax,");
				sbSQL.append(" mCompoundInterest,mOverDueInterest,");
				sbSQL.append(" mInterestReceivable");
				sbSQL.append(" FROM sett_transRepaymentloan");
				sbSQL.append(" WHERE nStatusID>=" + SETTConstant.TransactionStatus.CHECK);
                sbSQL.append(" and mRealInterest>0.0 ");
                //sbSQL.append(" and ( (nStatusID=3 and nTransactionTypeID != 23) ");
                //sbSQL.append("        or(nStatusID=7 and nTransactionTypeID=23))");
				sbSQL.append(" AND nLoancontractID=? ");//*/
                //*
				//sbSQL.append(" union");

                //=======view SETT_VTRANSINTERESTDETAIL 包含了上面SQL========//
                //=======ninh 2004-06-11 利息，费用收回情况列表不正确=========//

				sbSQL.append(" SELECT ExecuteDate dtExecute,");
				sbSQL.append(" decode(InterestTypeID,1,Interest,0) mInterest,");
				sbSQL.append(" decode(InterestTypeID,2,Interest,0) mCommission,");
				sbSQL.append(" decode(InterestTypeID,3,Interest,0) mSuretyFee,");
				sbSQL.append(" 0 mInterestTax,");
				sbSQL.append(" decode(InterestTypeID,4,Interest,0) mCompoundInterest,");
				sbSQL.append(" decode(InterestTypeID,5,Interest,0) mOverDueInterest,");
				sbSQL.append(" 0 mInterestReceivable");
				sbSQL.append(" FROM SETT_VTRANSINTERESTDETAIL");
				sbSQL.append(" WHERE nvl(contractID,0)= ?" );//*/

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				//ps.setLong(2, lContractID);
				rs = ps.executeQuery();

				while (rs.next())
				{
					if (rs.getDouble("mInterest") > 0)
					{
						ReportRepayInfo info = new ReportRepayInfo();
						info.setLoanDate(rs.getTimestamp("dtExecute"));
						info.setAmount(rs.getDouble("mInterest"));
						info.setType("利息");
						v.addElement(info);
					}

					if (rs.getDouble("mCommission") > 0)
					{
						ReportRepayInfo info = new ReportRepayInfo();
						info.setLoanDate(rs.getTimestamp("dtExecute"));
						info.setAmount(rs.getDouble("mCommission"));
						info.setType("手续费");
						v.addElement(info);
					}

					if (rs.getDouble("mSuretyFee") > 0)
					{
						ReportRepayInfo info = new ReportRepayInfo();
						info.setLoanDate(rs.getTimestamp("dtExecute"));
						info.setAmount(rs.getDouble("mSuretyFee"));
						info.setType("担保费");
						v.addElement(info);
					}

					if (rs.getDouble("mInterestTax") > 0)
					{
						ReportRepayInfo info = new ReportRepayInfo();
						info.setLoanDate(rs.getTimestamp("dtExecute"));
						info.setAmount(rs.getDouble("mInterestTax"));
						info.setType("利息税费");
						v.addElement(info);
					}

					if (rs.getDouble("mCompoundInterest") > 0)
					{
						ReportRepayInfo info = new ReportRepayInfo();
						info.setLoanDate(rs.getTimestamp("dtExecute"));
						info.setAmount(rs.getDouble("mCompoundInterest"));
						info.setType("复利");
						v.addElement(info);
					}

					if (rs.getDouble("mOverDueInterest") > 0)
					{
						ReportRepayInfo info = new ReportRepayInfo();
						info.setLoanDate(rs.getTimestamp("dtExecute"));
						info.setAmount(rs.getDouble("mOverDueInterest"));
						info.setType("罚息");
						v.addElement(info);
					}

					if (rs.getDouble("mInterestReceivable") > 0)
					{
						ReportRepayInfo info = new ReportRepayInfo();
						info.setLoanDate(rs.getTimestamp("dtExecute"));
						info.setAmount(rs.getDouble("mInterestReceivable"));
						info.setType("计提利息");
						v.addElement(info);
					}
				}
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{

			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* 得到利息情况
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return ReportInterestInfo
	* @exception Exception
	*/
	public ReportInterestInfo getInterestInfo(long lContractID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		ReportInterestInfo info = new ReportInterestInfo();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0)
			{
				sbSQL.append(" SELECT SUM(NVL(a.mInterest,0)) Amount,1 as payType ");
				sbSQL.append(" FROM sett_subAccount a,loan_payform b");
				sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				sbSQL.append(" AND a.nStatusID =" + SETTConstant.SubAccountStatus.NORMAL);
				sbSQL.append(" AND b.nContractID = ? ");
				sbSQL.append(" UNION ");
				sbSQL.append(" SELECT SUM(NVL(mRealInterest,0)) Amount, 0 as payType");
				sbSQL.append(" FROM sett_transRepaymentloan ");
				sbSQL.append(" WHERE nStatusID>=" + SETTConstant.TransactionStatus.CHECK);
				sbSQL.append(" AND nLoancontractID=? ");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				ps.setLong(2, lContractID);
				rs = ps.executeQuery();

				double dPayAmount = 0;
				double dRepayAmount = 0;

				while (rs.next())
				{
					if (rs.getLong("payType") == 1)
					{
						info.setLackOfInterest(DataFormat.formatDisabledAmount(rs.getDouble("Amount")));
						dPayAmount += rs.getDouble("Amount");
					}
					else if (rs.getLong("payType") == 0)
					{
						info.setRealInterest(DataFormat.formatDisabledAmount(rs.getDouble("Amount")));
						dRepayAmount += rs.getDouble("Amount");
					}
				}
				info.setReceiveInterest(DataFormat.formatDisabledAmount(dPayAmount + dRepayAmount));

			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{

			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return info;
	}
	
	public Collection getLoanInfoforHaierTZ(ContractQueryInfo qInfo) throws IException
	{
	    Vector v = new Vector();
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		

		

		
		StringBuffer sb = new StringBuffer();
		int lIndex = 1;

		try
		{
			con = Database.getConnection();

			sb.append(" SELECT * FROM loan_ContractForm where 1=1 ");
			//sb.append(" AND nStatusID >=" + Constant.RecordStatus.VALID);
			//sb.append(" AND nStatusID <=" + LOANConstant.ContractStatus.SUBMIT);
			sb.append(" and ( nTypeID=" + LOANConstant.LoanType.ZY);
			sb.append(" or nTypeID=" + LOANConstant.LoanType.YT);
			sb.append(" or nTypeID=" + LOANConstant.LoanType.ZGXE+")");
			sb.append(" AND nCurrencyID=?");
			sb.append(" AND nOfficeID=?");
			
			//查询条件
			/*strSQL_con = " WHERE a.nTypeID=? ";
			strSQL_con += " AND a.nInputUserID=?";
			strSQL_con += " AND a.nStatusID >=" + Constant.RecordStatus.VALID;
			strSQL_con += " AND a.nStatusID <=" + LOANConstant.ContractStatus.SUBMIT;
			strSQL_con += " AND a.nCurrencyID=?";
			strSQL_con += " AND a.nOfficeID=?";*/

			//lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0)
			{
				//strSQL_con += " AND a.ID >= ?";
				sb.append(" AND ID >= ?");
			}

			//lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0)
			{
				//strSQL_con += " AND a.ID <= ?";
				sb.append(" AND ID <= ?");
			}

			

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				//strSQL_con += " AND a.nBorrowClientID = ?";
				sb.append(" AND nBorrowClientID = ?");
			}

			
			//tsLoanStart贷款日期起
			if (qInfo.getLoanStart() != null)
			{
				sb.append(" AND TO_CHAR(dtStartDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')");
			}

			//tsLoanEnd贷款日期止
			if (qInfo.getLoanEnd() != null)
			{
			    sb.append(" AND TO_CHAR(dtStartDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')");
			}

			//lIntervalNum期限
			if (qInfo.getIntervalNum() > 0)
			{
				//strSQL_con += " AND a.nIntervalNum = ?";
				sb.append(" AND nIntervalNum = ?");
			}

			

			//lStatusID　借款合同状态
			if (qInfo.getStatusID() > 0)
			{
				//strSQL_con += " AND a.nStatusID = ?";
				sb.append(" AND nStatusID = ?");
			}

		
			sb.append(" order by id ");

			System.out.println(sb.toString());
			ps = con.prepareStatement(sb.toString());

			//ps.setLong(lIndex++, qInfo.getTypeID());
			//ps.setLong(lIndex++, qInfo.getUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractIDFrom());
			}

			//lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractIDTo());
			}

			

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			

			

			if (qInfo.getLoanStart() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getLoanStart());
			}

			if (qInfo.getLoanEnd() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getLoanEnd());
			}

			//lIntervalNum期限
			if (qInfo.getIntervalNum() > 0)
			{
				ps.setLong(lIndex++, qInfo.getIntervalNum());
			}
	

			//lStatusID　借款合同状态
			if (qInfo.getStatusID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getStatusID());
			}
	

			rs = ps.executeQuery();


			while (rs != null && rs.next())
			{
				ContractInfo info = new ContractInfo();
				info.setContractID(rs.getLong("ID")); //合同的ID
/*				info.setLoanID(rs.getLong("nLoanID")); //贷款ID
				info.setApplyCode(rs.getString("sApplyCode")); //申请书编号
				info.setContractCode(rs.getString("sContractCode")); //合同编号
				info.setBorrowClientName(rs.getString("sName")); //借款单位
				info.setLoanAmount(rs.getDouble("mLoanAmount")); //借款金额
				info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
				info.setCheckAmount(rs.getDouble("mCheckAmount")); //汇总贴现核定金额
				info.setInputUserName(rs.getString("InputUserName")); //录入人
				info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				info.setInterestRate(rInfo.getLateRate()); //执行利率
				info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款起始日期
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款到期日期
				info.setIntervalNum(rs.getLong("nIntervalNum")); //期限
				info.setInputDate(rs.getTimestamp("dtInputDate")); //合同录入日期
				info.setStatusID(rs.getLong("nStatusID")); //合同状态
				info.setStatus(LOANConstant.ContractStatus.getName(info.getStatusID()));
				//合同状态名称
				info.setPageCount(lPageCount); //记录总的页面数
*/
				v.addElement(info);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}

		catch (Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
		    try
            {
		        System.out.println("#############################");
                cleanup(rs);
				cleanup(ps);
				cleanup(con);
            } catch (SQLException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
		}
	    
	    return (v.size() > 0 ? v : null);
		
	}

	public static void main(String args[])
	{
		/*ReportDao dao = new ReportDao();
		ArrayList a = null;
		Connection conn = null;
		Timestamp endDate = null;
		try
		{
			//a = (ArrayList) dao.getWTLoanDetail(new ReportCondition() );
			//System.out.println(a.size());
			//conn = Database.getConnection();
			//endDate = new Timestamp(104,4,17,0,0,0,0);
			//dao.getHistoryBalance(conn,641,709,endDate) ;
			CreditReportCondition connn=new CreditReportCondition();
			connn.setReportType( 3 );
			connn.setStartDate(DataFormat.getDateTime("2004-04-1"));
			connn.setEndDate(DataFormat.getDateTime("2004-08-1"));
			connn.setStartClientID( 28);
			connn.setEndClientID( 28 );
			dao.getCreditDetail( connn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/
	    
	    ReportDao dao = new ReportDao();
		Vector v = new Vector();
		ContractQueryInfo  qInfo = new ContractQueryInfo();
		qInfo.setOfficeID(1);
		qInfo.setCurrencyID(1);
		
		try
		{
		    v = (Vector) dao.getLoanInfoforHaierTZ(qInfo);
		    System.out.println("===========+++++++++++++++++++++\t"+v.size());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
