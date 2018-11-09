package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestRate;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.IException;

/**
 * 
 * @author hjliu
 * Date of creation:2003-12-8
 * 考虑到在系统生产运行的过程中，有可能出现一些异常情况，例如：
 *     1:用户在录入交易时，输错了起息日期；
 *     2:利率调整时，输错了生效日期；
 *     3:结息/清户时，输错了结息日期；
 *     4:账户当日倒填当日结息，则在结息时已按倒填交易将此账户的历史计息信息调整过了。但用户在结息后又将这些倒填交易进行取消复核，引起数据混乱。
 *     5:由于应用系统的逻辑错误而导致系统计息数据混乱；
 * 本补丁程序，主要用于修改系统计算数据。
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

public class InterestFaultTolerance
{
	private Connection connection = null;
	/**
	 * 构造方法
	 *
	 */
	public InterestFaultTolerance(Connection conn_out)
	{

		connection = conn_out;

	}

	// 日志
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * @author hjliu
	 * 2003-12-08
	 * 前提是：在账户余额表在某天（initDate）是正确的数据，从(initDate + 1)补充或者修改账户余额表
	 * 调整计息数据的补丁程序
	 * 
	 * @param StartDate 计算其始日期
	 * @param EndDate 计算终止日期
	 * @param SubAccountID 子账户ID
	 * @return lReturn -1:不成功，1：成功
	 * @throws IException
	 */

	public long ReCalculateInterestPatch(long lOfficeID, long lCurrencyID, Timestamp StartDate, Timestamp EndDate, long SubAccountID) throws IException
	{
		long lReturn = -1;
		
		Connection conn = this.connection;
		//初始条件
		Timestamp dealDate = StartDate;
		InterestCalculation interestCalculation = new InterestCalculation();
		InterestBatch interestBatch = new InterestBatch(conn);
		Timestamp dealDateDecreaseOne = interestCalculation.getNextNDay(dealDate, -1);
		Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		Sett_SubAccountDAO sett_subAccountDAO = new Sett_SubAccountDAO(conn);
		try
		{
			DailyAccountBalanceInfo dailyAccountBalanceInfo_Init = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, dealDateDecreaseOne);
			if (dailyAccountBalanceInfo_Init == null)
			{
				log.print("注意！！！补丁程序中，初始条件在账户余额表中不存在！！！");
				return lReturn;
			}
			//返回该账户账户类型
			long accountID = dailyAccountBalanceInfo_Init.getAccountID();
			AccountInfo accountInfo = sett_AccountDAO.findByID(accountID);
			if (accountInfo == null)
			{
				throw new IException(true, "主账户表中没有对应记录，交易失败", null);
			}
			//账户类型
			long accountTypeID = accountInfo.getAccountTypeID();

			SubAccountAssemblerInfo subAccountInfo = sett_subAccountDAO.findByID(SubAccountID);
			if (subAccountInfo == null)
			{
				throw new IException(true, "子账户表中没有对应记录，交易失败", null);
			}
			//放款单ID
			long loanNoteID = subAccountInfo.getSubAccountLoanInfo().getLoanNoteID();
			//累计欠息余额
			double arrearageInterest = subAccountInfo.getSubAccountLoanInfo().getArrearageInterest();
			//子账户余额
			double subBalance = 0.0;
			//子账户利息
			double subInterest = 0.0;
			//账户状态
			long statusID = -1;

			//利率计划ID
			long interestRatePlanID = subAccountInfo.getSubAccountCurrenctInfo().getInterestRatePlanID();
			//协定余额
			double negotiateAmount = subAccountInfo.getSubAccountCurrenctInfo().getNegotiateAmount();
			//协定利率
			double negotiateRate = subAccountInfo.getSubAccountCurrenctInfo().getNegotiateRate();
			//协定单元
			double negotiateUnit = subAccountInfo.getSubAccountCurrenctInfo().getNegotiateUnit();
			//Modify by leiyang 2008-07-03 
			//是否协定存款
			long IsNegotiate = subAccountInfo.getSubAccountCurrenctInfo().getIsNegotiate(); 

			//初始条件中的各个属性
			double initBalance = dailyAccountBalanceInfo_Init.getBalance();
			double initInterestBalance = dailyAccountBalanceInfo_Init.getInterestBalance();
			double initInterest = dailyAccountBalanceInfo_Init.getInterest();
			double initNegotiateInterest = dailyAccountBalanceInfo_Init.getAc_mNegotiateInterest();
			double initNegotiateBalance = dailyAccountBalanceInfo_Init.getAc_mNegotiateBalance();
			//			log.debug("初始协定利率："+negotiateRate);
			//			log.debug("初始余额："+initBalance);
			//			log.debug("初始计息余额："+initInterestBalance);
			//			log.debug("初始协定余额："+initNegotiateBalance);
			//			log.debug("初始利息："+initInterest);
			//			log.debug("初始协定利息："+initNegotiateInterest);

			//循环中的临时变量
			double tmpBalance = initBalance;
			double tmpInterestBalance = initInterestBalance;
			double tmpNegotiateBalance = initNegotiateBalance;
			double tmpInterest = initInterest;
			double tmpNegotiateInterest = initNegotiateInterest;
			double todayInterest = 0.0;
			double todayNegotiateInterest = 0.0;
			double rate = 0.0;
			//循环天数
			long loopDays = interestCalculation.getIntervalDays(StartDate, interestCalculation.getNextNDay(EndDate, 1), 1);
			log.info("容错处理开始更新账户余额表(共要循环" + loopDays + ")......");
	        log.debug("---------判断账户类型------------");
			//long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < loopDays; i++)
			{ //注意：循环中不包含计算终止日期当天
				Timestamp tmpDate = DataFormat.getDateTime(DataFormat.formatDate(interestCalculation.getNextNDay(StartDate, i)));
				//如果tmpDate不是复利计算日期！！！！
				
				DailyAccountBalanceInfo tmpDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, tmpDate);
				sett_TransAccountDetailDAO transAccountDetailDAO = new sett_TransAccountDetailDAO(conn);
				TransAccountDetailInfo tadi_Balance = transAccountDetailDAO.findByFaultTolerance(accountID, SubAccountID, lOfficeID, lCurrencyID, tmpDate, SETTConstant.BooleanValue.ISTRUE);
				TransAccountDetailInfo tadi_InterestBalance = transAccountDetailDAO.findByFaultTolerance(accountID, SubAccountID, lOfficeID, lCurrencyID, tmpDate, SETTConstant.BooleanValue.ISFALSE);
				double transBalance = tadi_Balance.getAmount();
				double transInterestBalance = tadi_InterestBalance.getAmount();
				//log.debug("transBalance ==" + transBalance);
				//log.debug("transInterestBalance == " + transInterestBalance);
				//transInterestBalance = 0;
				Timestamp tmpDateAddOne = interestCalculation.getNextNDay(tmpDate, 1);
				DailyAccountBalanceInfo tmpDailyAccountBalance = new DailyAccountBalanceInfo();

				if (accountTypeInfo != null) {
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
					{
						//贷款账户
						//余额
						tmpBalance -= transBalance;
						//计息余额
						tmpInterestBalance -= transInterestBalance;
						InterestRate interestRate = interestCalculation.getInterestRateByPayForm(loanNoteID, tmpDate);
						rate = interestRate.getRate();
						
						////////////////////////
						System.out.println("/n日期1是"+tmpDate+"进行复利计算，累计欠息＝"+arrearageInterest);
	                    if(tmpDate.equals(DataFormat.getDateTime("2004-03-21")))
	                    {
	                    	arrearageInterest = tmpInterest+this.getCompoundInterest20040321(SubAccountID);
	                    	
	                    }
	                    else if (tmpDate.equals(DataFormat.getDateTime("2004-06-21")))
	                    {
	                    	arrearageInterest = tmpInterest+this.getCompoundInterest20040621(SubAccountID);
	                    }
	                    	
	                    //////////////////////////
	                    
						//今日利息
						todayInterest = interestCalculation.calculateLoanAccountInterest(lCurrencyID, rate, interestRate.getType(), tmpInterestBalance, tmpDate, tmpDateAddOne);
						//累计利息
						tmpInterest = UtilOperation.Arith.add(tmpInterest, todayInterest);
	
						statusID = subAccountInfo.getSubAccountLoanInfo().getStatusID();
	
					}
					else
					{
						double balance = tmpBalance;
						//存款账户
						//余额
						tmpBalance = UtilOperation.Arith.add(tmpBalance, transBalance);
	
						//计息余额
						tmpInterestBalance = UtilOperation.Arith.add(balance, transInterestBalance);
	
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
						{
							//活期存款
							//Modify by leiyang 更改校验协定存款的方式
							//增加 IsNegotiate 参数，是否为协定存款
							CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo =
								interestCalculation.calculateCurrentDepositAccountInterest(
									lOfficeID,
									lCurrencyID,
									interestRatePlanID,
									tmpInterestBalance,
									tmpDate,
									tmpDateAddOne,
									negotiateAmount,
									negotiateUnit,
									negotiateRate,
									IsNegotiate);
							rate = currentDepositAccountInterestInfo.getNormalInterestRate();
							//今日正常利息
							todayInterest = currentDepositAccountInterestInfo.getNormalInterest();
							tmpInterestBalance = currentDepositAccountInterestInfo.getNormalBalance();
							tmpNegotiateBalance = currentDepositAccountInterestInfo.getNegotiationBalance();
							//今日协定利息
							todayNegotiateInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
							//累计正常利息
							tmpInterest = UtilOperation.Arith.add(tmpInterest, todayInterest);
							//累计协定利息
							tmpNegotiateInterest = UtilOperation.Arith.add(tmpNegotiateInterest, todayNegotiateInterest);
	
							statusID = subAccountInfo.getSubAccountCurrenctInfo().getStatusID();
	
						}
	
					}
					//System.out.println("存款账户余额tmpBalance = " + tmpBalance);
					//System.out.println("存款计息余额tmpInterestBalance = " + tmpInterestBalance);
					//log.debug("协定余额:" + tmpNegotiateBalance);
	
					tmpDailyAccountBalance.setAccountID(accountID);
					tmpDailyAccountBalance.setSubAccountID(SubAccountID);
					tmpDailyAccountBalance.setBalance(tmpBalance);
					tmpDailyAccountBalance.setInterestBalance(tmpInterestBalance);
					tmpDailyAccountBalance.setInterest(tmpInterest);
					tmpDailyAccountBalance.setAc_mNegotiateInterest(tmpNegotiateInterest);
					tmpDailyAccountBalance.setAc_mNegotiateBalance(tmpNegotiateBalance);
					tmpDailyAccountBalance.setDailyInterest(todayInterest);
					tmpDailyAccountBalance.setAc_mDailyNegotiateInterest(todayNegotiateInterest);
					tmpDailyAccountBalance.setAl_mArrearageInterest(arrearageInterest);
					tmpDailyAccountBalance.setAc_mNegotiateRate(negotiateRate);
					tmpDailyAccountBalance.setInterestRate(rate);
					tmpDailyAccountBalance.setDate(tmpDate);
					tmpDailyAccountBalance.setAccountStatusID(statusID);
	
					//追加或者更新账户余额表
					DailyAccountBalanceInfo dabi_Patch = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, tmpDate);
					if (dabi_Patch == null)
					{
						//追加
						sett_DailyAccountBalanceDAO.add(tmpDailyAccountBalance);
	
					}
					else
					{
						if (dabi_Patch.getAccountID() < 0)
						{
							//追加
							sett_DailyAccountBalanceDAO.add(tmpDailyAccountBalance);
	
						}
						else
						{
							//更新
							sett_DailyAccountBalanceDAO.update(tmpDailyAccountBalance);
						}
	
					}
					
					log.info(tmpDate + "账户余额表更新成功--------");
					
				   //如果tmpDate等于复利计算日期，那么进行复利计算！
				}
			}
			//log.info("容错处理账户余额表成功！！！");
			//log.info("容错处理开始更新子账户......");
			//更新子账户.
			if (accountTypeInfo != null) {
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
				{
					long ac_nisNegotiate = -1;
					ac_nisNegotiate = subAccountInfo.getSubAccountCurrenctInfo().getIsNegotiate();
					//本贷款账户如果是逾期，则AC_nIsNegotiate ＝＝ 9
					if (ac_nisNegotiate == 9)
					{
						subAccountInfo.getSubAccountCurrenctInfo().setIsNegotiate(ac_nisNegotiate);
						log.info("本账户[" + SubAccountID + "]为逾期贷！！！！！！！！！！！！！！！！！！！！！！！");
				
						Timestamp clearDate = subAccountInfo.getSubAccountCurrenctInfo().getClearInterestDate();
						log.debug("clearDate 1111 = "+clearDate);
						sett_subAccountDAO.updateSubAccountCurrent(subAccountInfo.getSubAccountCurrenctInfo());
					}
					
					subAccountInfo.getSubAccountLoanInfo().setBalance(tmpBalance);
					subAccountInfo.getSubAccountLoanInfo().setInterest(tmpInterest);
					subAccountInfo.getSubAccountLoanInfo().setArrearageInterest(arrearageInterest);
					Timestamp clearDate1 =  subAccountInfo.getSubAccountLoanInfo().getClearInterestDate();
					log.debug("clearDate1 2222 = "+clearDate1);
					sett_subAccountDAO.updateSubAccountLoan(subAccountInfo.getSubAccountLoanInfo());
				}
				else
				{
					subAccountInfo.getSubAccountCurrenctInfo().setBalance(tmpBalance);
					subAccountInfo.getSubAccountCurrenctInfo().setInterest(tmpInterest);
					subAccountInfo.getSubAccountCurrenctInfo().setNegotiateInterest(tmpNegotiateInterest);
					sett_subAccountDAO.updateSubAccountCurrent(subAccountInfo.getSubAccountCurrenctInfo());
				}
				log.info("容错处理更新子账户成功！！！");
			}
		}
		catch (SQLException sqlE)
		{
			// TODO Auto-generated catch block
			sqlE.printStackTrace();
			throw new IException("GEN_0001");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("GEN_0001");
		}

		return lReturn;
	}
	
	private double getCompoundInterest20040321(long lSubAccountID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rset = null;
		Connection conn = null;
		
		double dRtn = 0.0;
		try
		{
			conn = Database.getConnection();
			InterestFaultTolerance interestFT = new InterestFaultTolerance(conn);
			StringBuffer  sbSQL = new StringBuffer();
			sbSQL.append(" select round(sum(AL_MARREARAGEINTEREST*MINTERESTRATE/36000),6) mCompoundInterest from sett_DailyAccountBalance ");
			sbSQL.append(" where dtDate >= to_date('20031221','yyyymmdd') and dtDate < to_date('20040321','yyyymmdd') and nSubAccountID="+lSubAccountID);
			
			ps = conn.prepareStatement(sbSQL.toString());
			rset = ps.executeQuery();

			if (rset != null && rset.next())
			{
				dRtn = rset.getDouble("mCompoundInterest");
   			}
			if (rset != null)
			{
				rset.close();
				rset = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(); 
		}
		finally
		{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
		}
		return dRtn;
	}
	private double getCompoundInterest20040621(long lSubAccountID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rset = null;
		Connection conn = null;
		
		double dRtn = 0.0;
		try
		{
			conn = Database.getConnection();
			InterestFaultTolerance interestFT = new InterestFaultTolerance(conn);
			StringBuffer  sbSQL = new StringBuffer();
			sbSQL.append(" select round(sum(AL_MARREARAGEINTEREST*MINTERESTRATE/36000),6) mCompoundInterest from sett_DailyAccountBalance ");
			sbSQL.append(" where dtDate >= to_date('20031221','yyyymmdd') and dtDate < to_date('20040621','yyyymmdd') and nSubAccountID="+lSubAccountID);
			
			ps = conn.prepareStatement(sbSQL.toString());
			rset = ps.executeQuery();

			if (rset != null && rset.next())
			{
				dRtn = rset.getDouble("mCompoundInterest");
   			}
			if (rset != null)
			{
				rset.close();
				rset = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(); 
		}
		finally
		{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
		}
		return dRtn;
	}

}