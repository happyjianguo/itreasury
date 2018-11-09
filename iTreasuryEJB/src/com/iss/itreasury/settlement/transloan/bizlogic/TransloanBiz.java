package com.iss.itreasury.settlement.transloan.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestConditionInfo;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dao.TransloanDao;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class TransloanBiz {

	TransloanDao transloanDao = new TransloanDao();

	public PagerInfo queryTransloan(TransRepaymentLoanInfo qInfo)
			throws Exception {
		// TODO Auto-generated method stub
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try {
				pagerInfo = new PagerInfo();
				// 得到查询SQL
				sql = transloanDao.queryTransloanSQL(qInfo);
				pagerInfo.setSqlString(sql);

				ArrayList depictList = new ArrayList();
				PagerDepictBaseInfo baseInfo = null;

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("stransno");
				baseInfo.setExtension(true);
				baseInfo.setExtensionName(new String[] { "stransno", "id" });
				baseInfo.setExtensionType(new long[] {
						PagerTypeConstant.STRING, PagerTypeConstant.LONG });
				baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + ","
						+ PagerTypeConstant.LOGOTYPE);
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("ntransactiontypeid");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.TransactionType.class,
						"getName", new Class[] { long.class });
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("npayinterestaccountid");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID",
						new Class[] { long.class });
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nloanaccountid");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID",
						new Class[] { long.class });
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nloancontractid");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getContractNoByID",
						new Class[] { long.class });
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nloannoteid");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getPayFormNoByID",
						new Class[] { long.class });
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("dtintereststart");
				baseInfo.setDisplayType(PagerTypeConstant.DATE);
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("sabstract");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nstatusid");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(
						SETTConstant.TransactionStatus.class, "getName",
						new Class[] { long.class });
				depictList.add(baseInfo);

				pagerInfo.setDepictList(depictList);

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}

	}

	public PagerInfo grantFindInterestByLoanNoteID(HashMap map) throws Exception {
		PagerInfo pagerInfo = new PagerInfo();

		long lLoanNoteID = Long.valueOf((String) map.get("lloannoteid"))
				.longValue();
		long nOfficeID = Long.valueOf((String) map.get("nofficeid"))
				.longValue();
		long nCurrencyID = Long.valueOf((String) map.get("ncurrencyid"))
				.longValue();
		long lLoanAccountID = Long.valueOf((String) map.get("loanaccountid"))
				.longValue();
		long lContractID = Long.valueOf((String) map.get("lcontractid"))
				.longValue();
		long lSubAccountID = Long.valueOf((String) map.get("lsubaccountid"))
				.longValue();

		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
		AccountInfo accountInfo = null;
		// 根据SubAccountID查找对应的子账户记录
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
		try {
			accountInfo = sett_AccountDAO.findByID(lLoanAccountID);
			if (accountInfo == null) {
				throw new IException(true, "主账户表中没有对应记录，查询失败", null);
			}
			subAccountAssemblerInfo = sett_SubAccountDAO
					.findByID(lSubAccountID);
			resultInfo.setID(lSubAccountID);
			resultInfo = sett_SubAccountDAO.querySubAccountInfo(resultInfo);
			if (subAccountAssemblerInfo == null) {
				throw new IException(true, "子账户表中没有对应记录，查询失败", null);
			}

		} catch (IException ie) {
			ie.printStackTrace();
			throw ie;
		}
		Timestamp tsLastInterestDate = resultInfo.getClearInterestDate();// 通过子账户获取上一个结息日
		String sql = transloanDao.findInterestByNoteID(lLoanNoteID, nOfficeID,
				nCurrencyID);
		pagerInfo.setSqlString(sql);
		pagerInfo.setExtensionMothod(TransloanBiz.class,
				"extensionResultSetHandle", map);
		return pagerInfo;
	}
	
	public ArrayList extensionResultSetHandle(ResultSet rss , Map map) throws Exception{
		long lLoanNoteID = Long.valueOf((String) map.get("lloannoteid"))
		.longValue();
		long nOfficeID = Long.valueOf((String) map.get("nofficeid"))
				.longValue();
		long nCurrencyID = Long.valueOf((String) map.get("ncurrencyid"))
				.longValue();
		long lLoanAccountID = Long.valueOf((String) map.get("loanaccountid"))
				.longValue();
		long lContractID = Long.valueOf((String) map.get("lcontractid"))
				.longValue();
		long lSubAccountID = Long.valueOf((String) map.get("lsubaccountid"))
				.longValue();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		TransRepaymentLoanInfo info = null;
		Timestamp tsLastInterestDate = null;
		String tsDate = Env.getSystemDateString(nOfficeID, nCurrencyID);
		AdjustInterestConditionInfo adjustInfo =  new AdjustInterestConditionInfo();

		try{
			conn =  Database.getConnection();
		while(rss.next())
		{

			info = resultInterestToInfo(rss);
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
			}
			    Timestamp tsRepayStartDate  =  info.getInterestStart();//还款开始日期
			    Timestamp tsRepayEndDate  =  info.getExecute();//还款结束日期
			    InterestCalculation interestCalculation = new InterestCalculation(conn);
			    int repayDays = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
			    int interestDays = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
			if(repayDays<interestDays)
			{
				String strSQL = null;
				ResultSet rs = null;
				long lRecordCount = -1;
				Timestamp startInterestDate = null;
				
				StringBuffer sSQL = new StringBuffer(128);
				sSQL.append(" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ");
				sSQL.append(" WHERE   bb.nContractID= "+info.getLoanContractID());
				sSQL.append(" and bb.nloanpaynoticeid ="+lLoanNoteID);
				sSQL.append(" and bb.STATUS ="+Constant.RecordStatus.VALID);
				sSQL.append(" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')");
				sSQL.append(" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')");	//当天还款不取当天生效日的利率			
				pstmt = conn.prepareStatement(sSQL.toString());
				rs = pstmt.executeQuery();
				//prepareStatement(strSQL);
               // rs = executeQuery();
				if (rs != null && rs.next())
                {
                    lRecordCount = rs.getLong(1);
                }
				if (lRecordCount <= 0)
                {
					int Day = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
					double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), info.getInterestIncome()), Day), 36000);
					info.setInterest(interest);
                }
				else
				{				
				strSQL = " select nvl(cc.mRate,0) mRate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate ";
				strSQL+= " FROM   loan_rateadjustpaydetail bb, loan_InterestRate cc ";
				strSQL+= " where bb.nContractID = "+info.getLoanContractID();
				strSQL+= " and bb.nloanpaynoticeid = "+lLoanNoteID;
				strSQL+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
				strSQL+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
				strSQL+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
				strSQL+= " and bb.nBankInterestID = cc.id(+) ";
				strSQL+= " order by bb.dtstartdate ";
				
				pstmt = conn.prepareStatement(strSQL.toString());
				rs = pstmt.executeQuery();
				//rs = executeQuery();
				double interest1 = 0.0;
				int compareDay = 0;
				double interestRate = info.getInterestIncome();
				while (rs != null && rs.next())
	            {				
					adjustInfo.setDAdjustRate(rs.getDouble("mrate") * (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate"));
					adjustInfo.setTsRateValidate(rs.getTimestamp("dtstartdate"));
					compareDay = (int) interestCalculation.getIntervalDays(tsRepayStartDate, adjustInfo.getTsRateValidate(), 1);
					interest1 = interest1 + UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), compareDay), 36000);
					tsRepayStartDate = adjustInfo.getTsRateValidate();
					interestRate = adjustInfo.getDAdjustRate();
				}
				 compareDay =(int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
				 interest1 = interest1+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), adjustInfo.getDAdjustRate()), compareDay), 36000);
				 System.out.println("还款利息:" + interest1);
				 info.setInterest(interest1);
				}
			}
			
		
				/*
				 * 国电桑旭提出
				 * 利息费用支付：利息页面，点击“还款单利息计算”按钮打开的页面，利息计算有误，显示的是从发放到开机日的所有利息，
				 * 但是此放款单之前做过结息，此次利息应该是从上次结息日到开机日的利息
				*/
				if(repayDays >= interestDays)
				{
					info.setInterestStart(tsLastInterestDate);
					String sSQL = "";
					double	interestRate =  info.getInterestIncome();
					//取最新的利率
					sSQL = " select nvl(cc.mRate,0) mRate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate ";
					sSQL+= " FROM   loan_rateadjustpaydetail bb ,loan_InterestRate cc  ";
					sSQL+= " where bb.nContractID = "+info.getLoanContractID();
					sSQL+= " and bb.nloanpaynoticeid = "+lLoanNoteID;
					sSQL+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
					sSQL+= " and bb.dtstartdate< to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
					sSQL+= " and bb.nBankInterestID = cc.id(+) ";
					sSQL+= " order by bb.dtstartdate desc";
					pstmt = conn.prepareStatement(sSQL.toString());
					ResultSet rsRate = null;
					rsRate = pstmt.executeQuery();
					if (rsRate != null && rsRate.next())
	                {
						interestRate = rsRate.getDouble("mrate") * (1 + rsRate.getDouble("mAdjustRate") / 100)+ rsRate.getDouble("mStaidAdjustRate");
	                }
					//
					ResultSet rs = null;
					long lRecordCount = -1;
					
					ResultSet rs1 = null;
								
					String sSQL1 = "";
					sSQL1 =" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ";
					sSQL1+=" WHERE   bb.nContractID= "+info.getLoanContractID();
					sSQL1+=" and bb.nloanpaynoticeid ="+lLoanNoteID;
					sSQL1+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
					sSQL1+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
					sSQL1+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
					pstmt = conn.prepareStatement(sSQL1.toString());
					rs = pstmt.executeQuery();
					if (rs != null && rs.next())
	                {
	                    lRecordCount = rs.getLong(1);
	                }
					
					if (lRecordCount <= 0)
	                {
						int Day = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
						double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), Day), 36000);
						info.setInterest(interest);
	                }
					else
					{
						sSQL = " select nvl(cc.mRate,0) mRate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate ";
						sSQL+= " FROM   loan_rateadjustpaydetail bb ,loan_InterestRate cc ";
						sSQL+= " where bb.nContractID = "+info.getLoanContractID();
						sSQL+= " and bb.nloanpaynoticeid = "+lLoanNoteID;
						sSQL+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
						sSQL+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
						sSQL+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
						sSQL+= " and bb.nBankInterestID = cc.id(+) ";
						sSQL+= " order by bb.dtstartdate ";
						//prepareStatement(sSQL);
						pstmt = conn.prepareStatement(sSQL.toString());
						rs1 = pstmt.executeQuery();
						//rs1 = executeQuery();
						double interest2 = 0.0;
						int compareDay2 = 0;
						//double interestRate = info.getInterestIncome();
						while (rs1 != null && rs1.next())
						{
							adjustInfo.setDAdjustRate(rs1.getDouble("mrate") * (1 + rs1.getDouble("mAdjustRate") / 100)+ rs1.getDouble("mStaidAdjustRate"));
							adjustInfo.setTsRateValidate(rs1.getTimestamp("dtstartdate"));
						    compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, adjustInfo.getTsRateValidate(), 1);
						    System.out.println("还款天数:" + compareDay2);
						    System.out.println("还款利率:" + interestRate);
						    interest2 = interest2+ UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), interestRate), compareDay2), 36000);
						    System.out.println("还款利息:" + interest2);
						    tsLastInterestDate = adjustInfo.getTsRateValidate();
						    interestRate = adjustInfo.getDAdjustRate();
						}
						compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
						System.out.println("还款天数:" + compareDay2);
						System.out.println("还款利率:" + adjustInfo.getDAdjustRate());
						interest2 = interest2+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(info.getAmount(), adjustInfo.getDAdjustRate()), compareDay2), 36000);	
						System.out.println("还款利息:" + interest2);
						info.setInterest(interest2);
				   }	
				}
			
			
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,com.iss.itreasury.util.DataFormat.formatDouble(info.getInterest(),2) );
			PagerTools.returnCellList(cellList,NameRef.getClientNameByID(info.getClientID()));
			PagerTools.returnCellList(cellList,NameRef.getClientCodeByID(info.getClientID()));
			PagerTools.returnCellList(cellList,NameRef.getAccountNoByID(info.getLoanAccountID()));
			PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getContractNoByID(info.getLoanContractID())));
			PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getAheadPayFormNoByID(info.getPreFormID())));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getInterestStart()));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getExecute() ));
			PagerTools.returnCellList(cellList,Constant.CurrencyType.getSymbol(nCurrencyID)+DataFormat.formatDisabledAmount(info.getAmount()));
			PagerTools.returnCellList(cellList,Constant.CurrencyType.getSymbol(nCurrencyID)+DataFormat.formatDisabledAmount(info.getInterest()));
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(rss.getLong("rownum1")));
			
			//返回数据
			resultList.add(rowInfo);
		}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
		    this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return resultList;
	}
	private static TransRepaymentLoanInfo resultInterestToInfo(ResultSet rset) throws SQLException
	{
		TransRepaymentLoanInfo info = new TransRepaymentLoanInfo();
		info.setLoanAccountID(rset.getLong("nloanaccountid"));
		info.setLoanContractID(rset.getLong("nloancontractid"));
		info.setClientID(rset.getLong("nclientid"));
		info.setInterestStart(rset.getTimestamp("dtoutdate"));
		info.setExecute(rset.getTimestamp("dtintereststart"));
		info.setInterestIncome(rset.getDouble("interestrate")* (1 + rset.getDouble("mAdjustRate") / 100)+ rset.getDouble("mStaidAdjustRate") );
		info.setAmount(rset.getDouble("mamount"));
		info.setPreFormID(rset.getLong("npreformid"));
		info.setSubAccountID(rset.getLong("nsubaccountid"));
		
		return info;
	  }
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			//Log.print("进入关闭PS方法");
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	private void cleanup(Statement stmt) throws SQLException
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
                stmt = null;
            }
        }
        catch (SQLException sqle)
        {
			Log.print(sqle.toString());
        }
    }
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			//Log.print("进入关闭连接方法");
			/**transConn　不为空表明这个数据库连接相关的事务不是容器维护的，因此不在此处关闭
			 * 即　Assert(con == transConn)
			 */
			//Log.print("con ="+con);
			//Log.print("transConn ="+transConn);
			if (con != null && con.isClosed()==false )
			{
				//Log.print("关闭连接--开始");
				con.close();
				con = null;
				//Log.print("关闭连接--结束");
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			//Log.print("进入关闭RS方法");
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
}
