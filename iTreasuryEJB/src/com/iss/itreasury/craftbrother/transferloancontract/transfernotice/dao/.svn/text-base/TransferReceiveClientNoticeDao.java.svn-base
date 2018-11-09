/**
 * 
 */
package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestConditionInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * @author xintan
 *
 *	信贷资产转让--代收通知单
 */
public class TransferReceiveClientNoticeDao extends ITreasuryDAO {

	/**
	 * 此方法通过放款通知单的ID查询该单据的利息.
	 * add by xwhe 2008-07-18
	 * @param 
	 * @return
	 * @throws IException
	 */
	public double findRepayInterestByID(
			double amount,
			long lLoanPayNoticeID,
			Timestamp lastClearDate,
			Timestamp clearInterestDate,
			long nOfficeID,
			long nCurrencyID) throws IException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Timestamp tsLastInterestDate = null;
		AdjustInterestConditionInfo adjustInfo =  new AdjustInterestConditionInfo();
		TransRepaymentLoanInfo info = new TransRepaymentLoanInfo();
		LoanPayNoticeDao loanNoticeDao = new LoanPayNoticeDao();
		try {
			con = Database.getConnection();
			String strSQL1 = " select n.naccountid,n.id, m.dtoutdate,nvl(m.minterestrate, 0) interestrate,nvl(m.mAdjustRate, 0) mAdjustRate,nvl(m.mStaidAdjustRate, 0) mStaidAdjustRate" +
					         " from sett_transgrantloan t, sett_subaccount n,loan_payform m  " +
					         " where t.nloanaccountid = n.naccountid and t.nloannoteid = m.id and n.al_nloannoteid = ? and m.id = ?  and t.nofficeid= ? and t.ncurrencyid=? and t.nstatusid= "+SETTConstant.TransactionStatus.CHECK ;
			ps = con.prepareStatement(strSQL1);
			int index = 1;
			ps.setLong(index++, lLoanPayNoticeID);
			ps.setLong(index++, lLoanPayNoticeID);
			ps.setLong(index++, nOfficeID);
			ps.setLong(index++, nCurrencyID);			
			rs = ps.executeQuery();
			if(rs.next()){
				info.setInterestIncome(loanNoticeDao.getLatelyRate(lLoanPayNoticeID, null));  
				info.setInterestStart(rs.getTimestamp("dtoutdate"));//放款日期
				//info.setInterestIncome(rs.getDouble("interestrate")* (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate") );//放款单利率
				info.setSubAccountID(rs.getLong("id"));
				info.setLoanAccountID(rs.getLong("naccountid"));
				if(info.getSubAccountID()>0)
				{
				   try
					{
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
									
					subAccountAssemblerInfo = sett_SubAccountDAO.findByID(info.getSubAccountID());
					resultInfo.setID(info.getSubAccountID());
					resultInfo = sett_SubAccountDAO.querySubAccountInfo(resultInfo);
				    tsLastInterestDate = resultInfo.getClearInterestDate();//通过子账户获取上一个结息日
					}
				    catch (Exception ie)
					{
						throw new IException(true, "子账户表中没有对应记录，查询失败", null);
					}
				    Timestamp tsRepayStartDate  =  lastClearDate;//放款开始日期
				    Timestamp tsRepayEndDate  =   clearInterestDate;//还款日期
				    InterestCalculation interestCalculation = new InterestCalculation(con);
				    int repayDays = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
				    int interestDays = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
				    if(repayDays>=interestDays)
				    {
				    	ResultSet rs1 = null ;
						String strSQL2 = "";
						long lRecordCount = -1;
						double	interestRate =  info.getInterestIncome();
						strSQL2 = " SELECT nvl(cc.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate FROM   loan_rateadjustpaydetail  bb ,loan_InterestRate cc ";
						strSQL2+=" where bb.nloanpaynoticeid ="+lLoanPayNoticeID;
						strSQL2+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
						strSQL2+=" and bb.nBankInterestID = cc.id(+) ";
						strSQL2+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
						strSQL2+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
						ps = con.prepareStatement(strSQL2.toString());
						rs1 = ps.executeQuery();
						while(rs1 != null && rs1.next())
						{
							interestRate = rs1.getDouble("mrate") * (1 + rs1.getDouble("mAdjustRate") / 100)+ rs1.getDouble("mStaidAdjustRate");
						}
						String strSQL3 = "";
						strSQL3 =" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ";
						strSQL3+=" where bb.nloanpaynoticeid ="+lLoanPayNoticeID;
						strSQL3+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
						strSQL3+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
						strSQL3+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
						ps = con.prepareStatement(strSQL3.toString());
						rs = ps.executeQuery();
						if (rs != null && rs.next())
		                {
		                    lRecordCount = rs.getLong(1);
		                }
						if (lRecordCount <= 0)
		                {
							int Day = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
							double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, interestRate), Day), 36000);
							info.setInterest(interest);
		                }
						else
						{
							String strSQL4 = "";
							strSQL4 = " select nvl(cc.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate  ";
							strSQL4+= " FROM   loan_rateadjustpaydetail bb,loan_InterestRate cc ";
							strSQL4+= " where bb.nloanpaynoticeid = "+lLoanPayNoticeID;
							strSQL4+="  and bb.STATUS ="+Constant.RecordStatus.VALID;
							strSQL4+= " and bb.nBankInterestID = cc.id(+) ";
							strSQL4+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
							strSQL4+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
							strSQL4+= " order by bb.dtstartdate ";
							ps = con.prepareStatement(strSQL4.toString());
							rs = ps.executeQuery();
							double interest2 = 0.0;
							int compareDay2 = 0;	
							while (rs != null && rs.next())
							{
								adjustInfo.setDAdjustRate(rs.getDouble("mrate") * (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate"));
								adjustInfo.setTsRateValidate(rs.getTimestamp("dtstartdate"));
							    compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, adjustInfo.getTsRateValidate(), 1);
							    System.out.println("还款天数:" + compareDay2);
							    System.out.println("还款利率:" + interestRate);
							    interest2 = interest2+ UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, interestRate), compareDay2), 36000);
							    System.out.println("还款利息:" + interest2);
							    tsLastInterestDate = adjustInfo.getTsRateValidate();
							    interestRate = adjustInfo.getDAdjustRate();
							}
							compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
							System.out.println("还款天数:" + compareDay2);
							System.out.println("还款利率:" + adjustInfo.getDAdjustRate());
							interest2 = interest2+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, adjustInfo.getDAdjustRate()), compareDay2), 36000);	
							System.out.println("还款利息:" + interest2);
							info.setInterest(interest2);
						}
				    }
				    else
				    {
				    	long lRecordCount = -1;
				    	String strSQL5 = "";
				    	double	interestRate =  info.getInterestIncome();
				    	strSQL5 =" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ";
				    	strSQL5+=" where bb.nloanpaynoticeid ="+lLoanPayNoticeID;
				    	strSQL5+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
				    	strSQL5+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
				    	strSQL5+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
						ps = con.prepareStatement(strSQL5.toString());
						rs = ps.executeQuery();
						if (rs != null && rs.next())
		                {
		                    lRecordCount = rs.getLong(1);
		                }
						if (lRecordCount <= 0)
		                {
							int Day = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
							double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, info.getInterestIncome()), Day), 36000);
							info.setInterest(interest);
		                }
						else
						{
							String strSQL6 = "";
							strSQL6 = " select nvl(cc.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate  ";
							strSQL6+= " FROM   loan_rateadjustpaydetail bb,loan_InterestRate cc  ";
							strSQL6+= " where bb.nloanpaynoticeid = "+lLoanPayNoticeID;
							strSQL6+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
							strSQL6+= " and bb.nBankInterestID = cc.id(+) ";
							strSQL6+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
							strSQL6+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
							strSQL6+= " order by bb.dtstartdate ";
							ps = con.prepareStatement(strSQL6.toString());
							rs = ps.executeQuery();
							double interest2 = 0.0;
							int compareDay2 = 0;	
							while (rs != null && rs.next())
							{
								adjustInfo.setDAdjustRate(rs.getDouble("mrate") * (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate"));
								adjustInfo.setTsRateValidate(rs.getTimestamp("dtstartdate"));
							    compareDay2 = (int) interestCalculation.getIntervalDays(tsRepayStartDate, adjustInfo.getTsRateValidate(), 1);
							    System.out.println("还款天数:" + compareDay2);
							    System.out.println("还款利率:" + interestRate);
							    interest2 = interest2+ UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, interestRate), compareDay2), 36000);
							    System.out.println("还款利息:" + interest2);
							    tsRepayStartDate = adjustInfo.getTsRateValidate();
							    interestRate = adjustInfo.getDAdjustRate();
							}
							compareDay2 = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
							System.out.println("还款天数:" + compareDay2);
							System.out.println("还款利率:" + adjustInfo.getDAdjustRate());
							interest2 = interest2+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, adjustInfo.getDAdjustRate()), compareDay2), 36000);	
							System.out.println("还款利息:" + interest2);
							info.setInterest(interest2);			    	
				    }
				}				
			}

		} 
		}catch (Exception e) {
			throw new IException("查询银团提款通知单ID失败");
		} finally {
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
			} catch (Exception e) {
				throw new IException("Gen_E001");
			}
		}
		return info.getInterest();
	}
}
