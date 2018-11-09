/*
 * Created on 2003-10-28
 *
 * InterestEstimate.java
 */
 
package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.interest.dao.Sett_LoanNoticeDAO;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.FixedDepositAccountPayableInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author xrli
 *
 * 利息费用结算结息处理业务对象，主要实现的功能包括：
 *
 * 
 *
 * 注意事项:为了使该数据访问对象对容器的事务和非容器的事务都提供支持，
 *          
 */
public class InterestEstimate
{
    /**
     * 如果是该类的方法负责维护事务，应该从容器取得数据库连接还是直接通过JDBC访问，缺省为从容器取得。
     */
    private boolean bConnectionFromContainer = true;
    
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	
	/**
	 * 根据条件进行匡算息记录的查询
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建
	 *            连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param queryInfo 包含查询条件的实体，never null.
	 * @return InterestEstimateQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息
	 *         需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector findEstimateRecords(Connection con, InterestEstimateQueryInfo queryInfo)
		throws Exception
	{

		Vector resultVec = new Vector(); //返回值

		//判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;
		}
		try
		{
			//业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			//表明该方法是这个事务的一个组成部分。			
			//查询记录。
			Vector rstVec = new Vector();
			rstVec = this.selectEstimateRecords(conInternal, queryInfo);
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()是零！！！！！！！！");
				return resultVec;
			}
			//算息
			getInterestResult(queryInfo, resultVec, conInternal, rstVec);
			//如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}
		}
		catch (Exception e)
		{
			//执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			//抛出用以通知事务的组织者。
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new IException("事务回退异常");
				}
			}
			throw e;
		}
		finally
		{
			//不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (con == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					;
				}
			}
		}

		return resultVec;
	}
	/**
	 * 根据条件进行定期存款匡息记录的查询
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建
	 *            连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param queryInfo 包含查询条件的实体，never null.
	 * @return InterestEstimateQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息
	 *         需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector findFixedEstimateRecords(Connection con, InterestEstimateQueryInfo queryInfo)
		throws Exception
	{
		Vector resultVec = new Vector(); //返回值

		//判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;
		}
		try
		{
			//业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			//表明该方法是这个事务的一个组成部分。			
			//查询记录。
			Vector rstVec = new Vector();
			rstVec = this.selectFixedEstimateRecords(conInternal, queryInfo);
			System.out.println(rstVec.size()+"%^^^^^^^^^^^^^^^^^^^");
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()是零！！！！！！！！");
				return resultVec;
			}
			
			
			
			//算息			有可能是此处算息出了问题
			queryInfo.setIsSelectInterest(1);//缺省算利息
			
			//queryInfo.getAccountTypeID();
			
			getBEIHFInterestResult(queryInfo, resultVec, conInternal, rstVec);
			//如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}
		}
		catch (Exception e)
		{
			//执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			//抛出用以通知事务的组织者。
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new IException("事务回退异常");
				}
			}
			throw e;
		}
		finally
		{
			//不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (con == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					;
				}
			}
		}

		return resultVec;
	}
	//查询定期存款匡算数据
	public Vector selectFixedEstimateRecords(Connection con, InterestEstimateQueryInfo qInfo)
		throws Exception
	{	
		StringBuffer buffer = new StringBuffer();
		String strSQL = "";
		
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //返回值

		//构造查询语句主体		
		buffer.append(" SELECT \n");
		buffer.append(" account.ID AS accountID, \n");
		buffer.append(" account.sAccountNo AS accountNo, \n"); //账户号
		buffer.append(" account.nAccountTypeID AS accountTypeID,\n"); //账户类型
		buffer.append(" client.sname ClientName, \n");                              
		buffer.append(" client.scode ClientNo, \n");   
		buffer.append(" subaccount.ID AS subAccountID, \n"); //子账户 ID
		buffer.append(" subaccount.AF_sDepositNo AS fixedDepositNo,\n "); //定期单据号		
		buffer.append(" subaccount.AF_mRate AS Rate, \n"); //利率
		buffer.append(" subaccount.mBalance AS Balance, \n"); //当前余额
		buffer.append(" subaccount.mOpenAmount AS Amount, \n"); //开户金额
		buffer.append(" subaccount.af_dtstart AS OpenDate \n"); //起始日
		buffer.append(" FROM \n");
		buffer.append(" sett_Account account, sett_SubAccount subaccount,sett_accountType accountType,client \n");
		buffer.append(" WHERE \n");
		
		buffer.append(" subaccount.nAccountID = account.ID \n");
		buffer.append(" AND ");
		buffer.append(" account.nClientID = client.ID \n");
		 
		if(qInfo.getDateTo()!=null && !qInfo.getDateTo().equals("")){ 
		//	buffer.append(" and subaccount.dTopen<= ? \n");
			buffer.append(" and subaccount.af_dtstart<= ? \n");
		}
				 
		if (qInfo.getAccountNoFrom() != null && ! qInfo.getAccountNoFrom().equals("-1") && ! qInfo.getAccountNoFrom().equals(""))
			buffer.append(" and account.id>='" + qInfo.getAccountNoFrom() + "' \n");
		if (qInfo.getAccountNoTo() != null && ! qInfo.getAccountNoTo().equals("-1") && ! qInfo.getAccountNoTo().equals(""))
			buffer.append(" and account.id<='" + qInfo.getAccountNoTo() + "' \n");
		if(qInfo.getDepositNoFrom()!=null && ! qInfo.getDepositNoFrom().equals("")){
			buffer.append(" and subaccount.AF_sDepositNo>='" + qInfo.getDepositNoFrom() + "' \n");
		}
		if(qInfo.getDepositNoTo()!=null && ! qInfo.getDepositNoTo().equals("")){
			buffer.append(" and subaccount.AF_sDepositNo<='" + qInfo.getDepositNoTo() + "' \n");
		}

		if(qInfo.getNoticeDays() >0)
		{				
			buffer.append("and subaccount.AF_nNoticeDay =" + qInfo.getNoticeDays() + " \n");
		}
		
		//定期存款
		buffer.append(" AND account.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		buffer.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		buffer.append("and account.nAccountTypeID = accountType.id and accountType.nAccountGroupID= " + SETTConstant.AccountGroupType.FIXED + "  \n");
		if (qInfo.getOfficeID() > 0){
			buffer.append(" AND  account.nOfficeID = " + qInfo.getOfficeID() + " \n");
		}
		if(qInfo.getCurrencyID()>0){
			buffer.append(" AND  account.nCurrencyID = " + qInfo.getCurrencyID() + " \n");			
		}
		
		buffer.append(" Order By ");
		buffer.append(" account.sAccountNo");
		

		strSQL = buffer.toString();		
		log.info(strSQL);
		try
		{
			//设置连接参数
			ps = con.prepareStatement(strSQL);
			int nIndex = 0;			
			if(qInfo.getDateTo()!=null && !qInfo.getDateTo().equals("")){ 
				ps.setTimestamp(1,qInfo.getDateTo());
			}
			
			//当前页的信息    
			

			//执行查询
			rs = ps.executeQuery();

			//取得结果
			log.info("***********设置返回结果***********"+rs);
			result = fetchDataFromNoticeRS(rs);
			
			for(int i=0;i<10;i++)
				System.out.println("=========得到的数据结果的长度为:"+result.size());
			  
			
		}
		catch (Exception e)
		{
			log.error("检索结息记录时发生错误：" + e.getMessage());
			throw e;
		}
		finally
		{ //释放资源
			rs.close();
			ps.close();
			if(con==null)
			{
				conInternal.close();
			}
		}		
		return result;
	}
	/**
	 * 处理查询数据算息
	 * @param queryInfo     查询条件 
	 * @param resultVec     返回结果
	 * @param conInternal   数据库联接
	 * @param rstVec        查询结果
	 * @throws IException
	 * @throws Exception
	 */
	private void getBEIHFInterestResult(
		InterestEstimateQueryInfo queryInfo,
		Vector resultVec,
		Connection conInternal,
		Vector rstVec)
		throws IException, Exception
	{
		log.info("-------开始处理查询数据--------");
		InterestOperation io = new InterestOperation(conInternal);
		InterestBatch ib = new InterestBatch(conInternal);
		//判断是否续倒填
		 sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
		for (int i = 0; i < rstVec.size(); i++)
		{
			log.info("lhj debug info ===进入循环算息=======");
			InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
			resultInfo = (InterestEstimateQueryResultInfo) rstVec.elementAt(i);	
	        log.debug("---------判断账户类型------------");
			long accountTypeID = resultInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
				{
					log.info("lhj debug info ===进入定期算息=======");
					FixedDepositAccountPayableInterestInfo fixedInterestInfo =
						new FixedDepositAccountPayableInterestInfo();
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						//不是通知存款
						if(accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("lhj debug info ===进入定期计提利息=======");
							//执行定期计提的计算，生成新的纪录返回
							SubAccountPredrawInterestInfo subInterestInfo =
								new SubAccountPredrawInterestInfo();
							subInterestInfo =
								io.getFixedAccountPredrawInterest(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									resultInfo.getAccountTypeID(),
									queryInfo.getClearInterestDate());
							//余额		
							fixedInterestInfo.setBalance(subInterestInfo.getBalance());
							fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							//lhj modify===下面几个属性好象没有必要显示出来------2003-11-26-----------------------
							fixedInterestInfo.setDays(subInterestInfo.getDays());
							fixedInterestInfo.setEDate(subInterestInfo.getEDate());
							fixedInterestInfo.setRate(subInterestInfo.getRate());
							fixedInterestInfo.setSDate(subInterestInfo.getSDate());
							//lhj modify==---------------------------------------2003-11-26-------------
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//计提利息
							newResultInfo.setInterest(fixedInterestInfo.getInterest());
							//利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							
							resultVec.addElement(newResultInfo);
							log.info("lhj debug info ===结束定期计提利息=======");
						}					
					}
					else if (queryInfo.getIsSelectInterest()>0)
					{
						if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("--------------开始通知算息------------");
							fixedInterestInfo =
								io.EstimateNoticeDepositAccountInterest(queryInfo.getOfficeID(),queryInfo.getCurrencyID(),
									resultInfo.getAccountID(),resultInfo.getSubAccountID(),
									queryInfo.getDateFrom(),queryInfo.getDateTo(),
									resultInfo.getRate(),
									Env.getSystemDate(conInternal,queryInfo.getOfficeID(),queryInfo.getCurrencyID()),1);
							log.info("--------------开始通知算息------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//计息金额
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);
						}
						else
						{
							log.info("--------------开始定期算息------------");
				/*			fixedInterestInfo =
								io.getFixedDepositAccountPayableInterest(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									queryInfo.getClearInterestDate());
				*/	 
							fixedInterestInfo =
								io.EstimateFixedDepositAccountInterest(queryInfo.getOfficeID(),queryInfo.getCurrencyID(),
									resultInfo.getAccountID(),resultInfo.getSubAccountID(),
									queryInfo.getDateFrom(),queryInfo.getDateTo(),
									resultInfo.getRate(),
									Env.getSystemDate(conInternal,queryInfo.getOfficeID(),queryInfo.getCurrencyID()),1);
							log.info("--------------结束定期算息------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//计息金额
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);						
						}					
					}
					log.info("lhj debug info ===结束定期算息=======");				
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) //活期
				{
					//利息
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("lhj debug info ===进入活期算息=======");
						CurrentDepositAccountInterestInfo currInterestInfo =
							new CurrentDepositAccountInterestInfo();
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
			
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户算息倒填---------");
									long flag =
									ib.accountInterestCalculationBackward(
										resultInfo.getAccountID(),
										resultInfo.getSubAccountID(),
										detailInfo.getDtInterestStart(),
										detailInfo.getAmount(),
										queryInfo.getOfficeID(),
										queryInfo.getCurrencyID(),								
										Env.getSystemDate(conInternal,queryInfo.getOfficeID(),queryInfo.getCurrencyID()),SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("单户倒填失败");
								}
								log.info("-------------单户倒填结束---------");
								}
							}
						}
						log.info("-------------判断是否需要单户倒填结束---------");
						log.info("-------------算息开始---------");
						log.info("lhj debug info == 运行getCurrentDepositAccountInterest.......");
						currInterestInfo =
							io.getCurrentDepositAccountInterest(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()));
			
						resultInfo.setDays(currInterestInfo.getIntervalDays());
						resultInfo.setEndDate(currInterestInfo.getEDate());
						//计息金额
						resultInfo.setInterestBalance(currInterestInfo.getNormalBalance());
						resultInfo.setInterest(currInterestInfo.getNormalInterest());						
						resultInfo.setStartDate(currInterestInfo.getSDate());
						
						//利息类型
						resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
			
						log.info("-------------算息结束---------");
											
						resultVec.addElement(resultInfo);
						log.info("lhj debug info ===结束活期算息=======");
					}
			
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //贷款
				{
					log.info("lhj debug info ===进入贷款算息=======");
					LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
			
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("（interestSettlement 346）lhj debug settlement info >>>>>>>>>>>贷款账户需要计息! ");
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户算息倒填---------");
									long flag =
										ib.accountInterestCalculationBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID()),SETTConstant.BooleanValue.ISFALSE);								
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						log.info("-------------判断是否需要单户倒填结束---------");
			
						//利息
						log.info("-------------算息开始---------");
						loanInterestInfo =
							io.GetLoanAccountInterest(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()));
						log.info("----------打印返回信息-------------");
						log.debug(UtilOperation.dataentityToString(loanInterestInfo));
						log.info("-------------算息结束---------");
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//利息
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//计息金额					
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
						
						newResultInfo.setDays(loanInterestInfo.getDays());
						
						
						log.info("（interestSettlement 415）lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID是： "+newResultInfo.getAccountTypeID());
				        log.debug("---------判断账户类型------------");
				        AccountTypeInfo newAccountTypeInfo = null;
				        try {
				        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
						} catch (SQLException e) {
							e.printStackTrace();
						}
							if (newAccountTypeInfo != null) {
							//委托贷款账户
							if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								InterestTaxInfo taxInfo = new InterestTaxInfo();
								
								/**
								taxInfo =
									io.getInterestTax(
										newResultInfo.getAccountID(),
										newResultInfo.getSubAccountID(),
										newResultInfo.getInterest());
								newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
								newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
								**/
								
								//求取利息税费的方式发生变更，改成从利息税费率计划中获取
								taxInfo =
									io.getInterestTaxByPlan(
										newResultInfo.getAccountID(),
										newResultInfo.getSubAccountID(),
										newResultInfo.getInterest());
								newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
								newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
								newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
							}
							
							/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
							{
								BankLoanQuery bankLoanQuery =new BankLoanQuery();
								//承贷比例
								log.info("--------银团贷款操作——将利息数据按承贷比例转换");
								double dRate = 0.0;
								dRate=bankLoanQuery.findRateByFormID(newResultInfo.getPayFormID());					
								
								if(dRate>0)
								{
									newResultInfo.setInterest(newResultInfo.getInterest()/dRate*100);
									newResultInfo.setInterestBalance(newResultInfo.getInterestBalance()/dRate*100);							
								}
							}*/
							resultVec.addElement(newResultInfo);
						}
					}
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("（interestSettlement 445）lhj debug settlement info >>>>>>>>>>>贷款账户计提利息-------");
						
						//执行贷款计提利息的计算，生成新的纪录返回
						SubAccountPredrawInterestInfo subInterestInfo =
							new SubAccountPredrawInterestInfo();
						subInterestInfo =
							io.getLoanAccountPredrawInterest(
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								resultInfo.getAccountTypeID(),
								queryInfo.getClearInterestDate());
						loanInterestInfo.setBalance(subInterestInfo.getBalance());
						loanInterestInfo.setDays(subInterestInfo.getDays());
						loanInterestInfo.setEDate(subInterestInfo.getEDate());
						loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
						loanInterestInfo.setRate(subInterestInfo.getRate());
						loanInterestInfo.setSDate(subInterestInfo.getSDate());
			
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//计提利息
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						resultVec.addElement(newResultInfo);
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
					{
						log.info("（interestSettlement 475）lhj debug settlement info >>>>>>>>>>>贷款账户手续费-------");	
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.COMMISION);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//手续费
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
																
						resultVec.addElement(newResultInfo);
			
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
					{
						log.info("（interestSettlement 551）lhj debug settlement info >>>>>>>>贷款账户担保费-------");	
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),
												SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.ASSURE);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//担保费
						newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
						//利息税费
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						log.info("（interestSettlement 611）计算利息税费");
						
						/**
						taxInfo = io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getAssuranceCharge());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						**/
						
						//求取利息税费的方式发生变更，改成从利息税费率计划中获取
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
											
						resultVec.addElement(newResultInfo);
			
					}
					if (queryInfo.getIsSelectCompoundInterest()>0)
					{
						log.info("（interestSettlement 635）lhj debug info>>>>>贷款复利.....");
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						//复利
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//复利
						newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						//利息税费
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						
						/**
						taxInfo =
							io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getCompoundInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());		
						**/
						
						//求取利息税费的方式发生变更，改成从利息税费率计划中获取
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//承贷比例
							log.info("--------银团贷款操作——将利息数据按承贷比例转换");
							double dRate = 0.0;
							dRate=bankLoanQuery.findRateByFormID(newResultInfo.getPayFormID());					
							
							if(dRate>0)
							{
								newResultInfo.setInterest(newResultInfo.getInterest()/dRate*100);
								newResultInfo.setInterestBalance(newResultInfo.getAmount()/dRate*100);							
							}
						}*/
						resultVec.addElement(newResultInfo);
					}
					if (queryInfo.getIsSelectForfeitInterest()>0)
					{
						log.info("（interestSettlement 721）lhj debug info>>>>>贷款罚息.....");
									
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						//罚息
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.FORFEITINTEREST);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//逾期罚息
						newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);
						//利息税费
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						
						/**
						taxInfo =
							io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getForfeitInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						**/
						
						//求取利息税费的方式发生变更，改成从利息税费率计划中获取
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{ 
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//承贷比例
							log.info("--------银团贷款操作——将利息数据按承贷比例转换");
							double dRate = 0.0;
							dRate=bankLoanQuery.findRateByFormID(newResultInfo.getPayFormID());					
							
							if(dRate>0)
							{
								newResultInfo.setInterest(newResultInfo.getInterest()/dRate*100);
								newResultInfo.setInterestBalance(newResultInfo.getAmount()/dRate*100);							
							}
						}*/
						resultVec.addElement(newResultInfo);
					}
					log.info("lhj debug info ===结束贷款算息=======");
				}
			}
		}
		log.info("-------处理查询数据结束--------");
	}
	/**
	 * 根据条件进行通知存款匡息记录的查询
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建
	 *            连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param queryInfo 包含查询条件的实体，never null.
	 * @return InterestEstimateQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息
	 *         需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector findNoticeEstimateRecords(Connection con, InterestEstimateQueryInfo queryInfo)
		throws Exception
	{
		Vector resultVec = new Vector(); //返回值

		//判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;
		}
		try
		{
			//业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			//表明该方法是这个事务的一个组成部分。			
			//查询记录。
			Vector rstVec = new Vector();
			rstVec = this.selectNoticeEstimateRecords(conInternal, queryInfo);
			System.out.println(rstVec.size()+"%^^^^^^^^^^^^^^^^^^^");
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()是零！！！！！！！！");
				return resultVec;
			}
			
			
			
			//算息			有可能是此处算息出了问题
			queryInfo.setIsSelectInterest(1);//缺省算利息
			
			//queryInfo.getAccountTypeID();
			
			getInterestResult(queryInfo, resultVec, conInternal, rstVec);
			//如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}
		}
		catch (Exception e)
		{
			//执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			//抛出用以通知事务的组织者。
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new IException("事务回退异常");
				}
			}
			throw e;
		}
		finally
		{
			//不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (con == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					;
				}
			}
		}

		return resultVec;
	}
	/**
	 * 贷款通知书保存
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	 */
	public void saveLoanNotice(Connection con, Vector resultVec)
		throws Exception
	{		

		//判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;			
		}
		try
		{
			//业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			//表明该方法是这个事务的一个组成部分。		
			Sett_LoanNoticeDAO loanNoticeDao = new Sett_LoanNoticeDAO();
			log.info("-------------开始保存---------");
			for (int i = 0; i < resultVec.size(); i++)
			{
				LoanNoticeInfo info = new LoanNoticeInfo();
				info = (LoanNoticeInfo)resultVec.elementAt(i);
				info.setStatusID(SETTConstant.BooleanValue.ISTRUE);
				loanNoticeDao.add(conInternal,info);
			}
			log.info("-------------保存结束---------");

			//如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
			if (con == null)
			{
				try
				{
					log.info("*****提交******");
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new Exception("事务提交异常");
				}
			}
		}
		catch (Exception e)
		{
			//执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			//抛出用以通知事务的组织者。
			e.printStackTrace();
			if (con == null)
			{
				try
				{					
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new Exception("事务回退异常");
				}
			}
			throw e;
		}
		finally
		{
			//不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (con == null)
			{
				try
				{
					log.info("****关闭连接******");
					conInternal.close();
				}
				catch (Exception eClose)
				{
					throw new Exception("事务异常");
				}
			}
		}		
	}
	/**
	 * 处理查询数据算息
	 * @param queryInfo     查询条件 
	 * @param resultVec     返回结果
	 * @param conInternal   数据库联接
	 * @param rstVec        查询结果
	 * @throws IException
	 * @throws Exception
	 */
	private void getInterestResult(
		InterestEstimateQueryInfo queryInfo,
		Vector resultVec,
		Connection conInternal,
		Vector rstVec)
		throws IException, Exception
	{
		log.info("-------开始处理查询数据--------");
		InterestOperation io = new InterestOperation(conInternal);
		InterestBatch ib = new InterestBatch(conInternal);
		//判断是否续倒填
		 sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
		for (int i = 0; i < rstVec.size(); i++)
		{
			log.info("lhj debug info ===进入循环算息=======");
			InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
			resultInfo = (InterestEstimateQueryResultInfo) rstVec.elementAt(i);	
	        log.debug("---------判断账户类型------------");
			long accountTypeID = resultInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
				{
					log.info("lhj debug info ===进入定期算息=======");
					FixedDepositAccountPayableInterestInfo fixedInterestInfo =
						new FixedDepositAccountPayableInterestInfo();
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						//不是通知存款
						if(accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("lhj debug info ===进入定期计提利息=======");
							//执行定期计提的计算，生成新的纪录返回
							SubAccountPredrawInterestInfo subInterestInfo =
								new SubAccountPredrawInterestInfo();
							subInterestInfo =
								io.getFixedAccountPredrawInterest(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									resultInfo.getAccountTypeID(),
									queryInfo.getClearInterestDate());
							//余额		
							fixedInterestInfo.setBalance(subInterestInfo.getBalance());
							fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							//lhj modify===下面几个属性好象没有必要显示出来------2003-11-26-----------------------
							fixedInterestInfo.setDays(subInterestInfo.getDays());
							fixedInterestInfo.setEDate(subInterestInfo.getEDate());
							fixedInterestInfo.setRate(subInterestInfo.getRate());
							fixedInterestInfo.setSDate(subInterestInfo.getSDate());
							//lhj modify==---------------------------------------2003-11-26-------------
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//计提利息
							newResultInfo.setInterest(fixedInterestInfo.getInterest());
							//利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							
							resultVec.addElement(newResultInfo);
							log.info("lhj debug info ===结束定期计提利息=======");
						}					
					}
					else if (queryInfo.getIsSelectInterest()>0)
					{
						if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("--------------开始通知算息------------");
							fixedInterestInfo =
								io.EstimateNoticeDepositAccountInterest(queryInfo.getOfficeID(),queryInfo.getCurrencyID(),
									resultInfo.getAccountID(),resultInfo.getSubAccountID(),
									queryInfo.getDateFrom(),queryInfo.getDateTo(),
									resultInfo.getRate(),
									Env.getSystemDate(conInternal,queryInfo.getOfficeID(),queryInfo.getCurrencyID()),1);
							log.info("--------------开始通知算息------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//计息金额
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);
						}
						else
						{
							log.info("--------------开始定期算息------------");
							fixedInterestInfo =
								io.getFixedDepositAccountPayableInterest(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									queryInfo.getClearInterestDate());
							log.info("--------------结束定期算息------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//计息金额
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);						
						}					
					}
					log.info("lhj debug info ===结束定期算息=======");				
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) //活期
				{
					//利息
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("lhj debug info ===进入活期算息=======");
						CurrentDepositAccountInterestInfo currInterestInfo =
							new CurrentDepositAccountInterestInfo();
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
			
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户算息倒填---------");
									long flag =
									ib.accountInterestCalculationBackward(
										resultInfo.getAccountID(),
										resultInfo.getSubAccountID(),
										detailInfo.getDtInterestStart(),
										detailInfo.getAmount(),
										queryInfo.getOfficeID(),
										queryInfo.getCurrencyID(),								
										Env.getSystemDate(conInternal,queryInfo.getOfficeID(),queryInfo.getCurrencyID()),SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("单户倒填失败");
								}
								log.info("-------------单户倒填结束---------");
								}
							}
						}
						log.info("-------------判断是否需要单户倒填结束---------");
						log.info("-------------算息开始---------");
						log.info("lhj debug info == 运行getCurrentDepositAccountInterest.......");
						currInterestInfo =
							io.getCurrentDepositAccountInterest(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()));
			
						resultInfo.setDays(currInterestInfo.getIntervalDays());
						resultInfo.setEndDate(currInterestInfo.getEDate());
						//计息金额
						resultInfo.setInterestBalance(currInterestInfo.getNormalBalance());
						resultInfo.setInterest(currInterestInfo.getNormalInterest());						
						resultInfo.setStartDate(currInterestInfo.getSDate());
						
						//利息类型
						resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
			
						log.info("-------------算息结束---------");
											
						resultVec.addElement(resultInfo);
						log.info("lhj debug info ===结束活期算息=======");
					}
			
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //贷款
				{
					log.info("lhj debug info ===进入贷款算息=======");
					LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
			
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("（interestSettlement 346）lhj debug settlement info >>>>>>>>>>>贷款账户需要计息! ");
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户算息倒填---------");
									long flag =
										ib.accountInterestCalculationBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID()),SETTConstant.BooleanValue.ISFALSE);								
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						log.info("-------------判断是否需要单户倒填结束---------");
			
						//利息
						log.info("-------------算息开始---------");
						loanInterestInfo =
							io.GetLoanAccountInterest(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()));
						log.info("----------打印返回信息-------------");
						log.debug(UtilOperation.dataentityToString(loanInterestInfo));
						log.info("-------------算息结束---------");
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//利息
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//计息金额					
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
						
						newResultInfo.setDays(loanInterestInfo.getDays());
						
						
						log.info("（interestSettlement 415）lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID是： "+newResultInfo.getAccountTypeID());
				        log.debug("---------判断账户类型------------");
				        AccountTypeInfo newAccountTypeInfo = null;
				        try {
				        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
						} catch (SQLException e) {
							e.printStackTrace();
						}
							if (newAccountTypeInfo != null) {
							//委托贷款账户
							if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								InterestTaxInfo taxInfo = new InterestTaxInfo();
								
								/**
								taxInfo =
									io.getInterestTax(
										newResultInfo.getAccountID(),
										newResultInfo.getSubAccountID(),
										newResultInfo.getInterest());
								newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
								newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
								**/
								
								//求取利息税费的方式发生变更，改成从利息税费率计划中获取
								taxInfo =
									io.getInterestTaxByPlan(
										newResultInfo.getAccountID(),
										newResultInfo.getSubAccountID(),
										newResultInfo.getInterest());
								newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
								newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
								newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
							}
							
							/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
							{
								BankLoanQuery bankLoanQuery =new BankLoanQuery();
								//承贷比例
								log.info("--------银团贷款操作——将利息数据按承贷比例转换");
								double dRate = 0.0;
								dRate=bankLoanQuery.findRateByFormID(newResultInfo.getPayFormID());					
								
								if(dRate>0)
								{
									newResultInfo.setInterest(newResultInfo.getInterest()/dRate*100);
									newResultInfo.setInterestBalance(newResultInfo.getInterestBalance()/dRate*100);							
								}
							}*/
							resultVec.addElement(newResultInfo);
						}
					}
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("（interestSettlement 445）lhj debug settlement info >>>>>>>>>>>贷款账户计提利息-------");
						
						//执行贷款计提利息的计算，生成新的纪录返回
						SubAccountPredrawInterestInfo subInterestInfo =
							new SubAccountPredrawInterestInfo();
						subInterestInfo =
							io.getLoanAccountPredrawInterest(
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								resultInfo.getAccountTypeID(),
								queryInfo.getClearInterestDate());
						loanInterestInfo.setBalance(subInterestInfo.getBalance());
						loanInterestInfo.setDays(subInterestInfo.getDays());
						loanInterestInfo.setEDate(subInterestInfo.getEDate());
						loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
						loanInterestInfo.setRate(subInterestInfo.getRate());
						loanInterestInfo.setSDate(subInterestInfo.getSDate());
			
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//计提利息
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						resultVec.addElement(newResultInfo);
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
					{
						log.info("（interestSettlement 475）lhj debug settlement info >>>>>>>>>>>贷款账户手续费-------");	
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.COMMISION);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//手续费
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
																
						resultVec.addElement(newResultInfo);
			
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
					{
						log.info("（interestSettlement 551）lhj debug settlement info >>>>>>>>贷款账户担保费-------");	
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.ASSURE);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//担保费
						newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
						//利息税费
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						log.info("（interestSettlement 611）计算利息税费");
						
						/**
						taxInfo = io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getAssuranceCharge());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						**/
						
						//求取利息税费的方式发生变更，改成从利息税费率计划中获取
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
											
						resultVec.addElement(newResultInfo);
			
					}
					if (queryInfo.getIsSelectCompoundInterest()>0)
					{
						log.info("（interestSettlement 635）lhj debug info>>>>>贷款复利.....");
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						//复利
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//复利
						newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						//利息税费
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						
						/**
						taxInfo =
							io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getCompoundInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());		
						**/
						
						//求取利息税费的方式发生变更，改成从利息税费率计划中获取
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//承贷比例
							log.info("--------银团贷款操作——将利息数据按承贷比例转换");
							double dRate = 0.0;
							dRate=bankLoanQuery.findRateByFormID(newResultInfo.getPayFormID());					
							
							if(dRate>0)
							{
								newResultInfo.setInterest(newResultInfo.getInterest()/dRate*100);
								newResultInfo.setInterestBalance(newResultInfo.getAmount()/dRate*100);							
							}
						}*/
						resultVec.addElement(newResultInfo);
					}
					if (queryInfo.getIsSelectForfeitInterest()>0)
					{
						log.info("（interestSettlement 721）lhj debug info>>>>>贷款罚息.....");
									
						Collection coll = null;
						log.info("-------------判断是否需要单户倒填---------");
						coll =
							transDetailDAO.findByIsBackward(							
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getCurrencyID(),
								queryInfo.getOfficeID(),
								queryInfo.getClearInterestDate());
						Iterator itResult = null;
						if (coll != null && coll.size() > 0)
						{
							itResult = coll.iterator();
							if (itResult != null && itResult.hasNext())
							{
								while (itResult.hasNext())
								{
									TransAccountDetailInfo detailInfo =
										(TransAccountDetailInfo) itResult.next();
									log.info("-------------开始单户倒填---------");
									long flag =
										ib.accountInterestSettlelmentBackward(
											resultInfo.getAccountID(),
											resultInfo.getSubAccountID(),
											detailInfo.getDtInterestStart(),
											detailInfo.getAmount(),
											queryInfo.getOfficeID(),
											queryInfo.getCurrencyID(),
											Env.getSystemDate(conInternal,
												queryInfo.getOfficeID(),
												queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
									if (flag < 0)
									{
										throw new IException("单户倒填失败");
									}
									log.info("-------------单户倒填结束---------");
								}
							}
						}
						//罚息
						loanInterestInfo =
							io.getLoanAccountFee(
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								queryInfo.getClearInterestDate(),
								Env.getSystemDate(conInternal,
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.FORFEITINTEREST);
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//逾期罚息
						newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
						//利息类型
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);
						//利息税费
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						
						/**
						taxInfo =
							io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getForfeitInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						**/
						
						//求取利息税费的方式发生变更，改成从利息税费率计划中获取
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//计息金额
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//承贷比例
							log.info("--------银团贷款操作——将利息数据按承贷比例转换");
							double dRate = 0.0;
							dRate=bankLoanQuery.findRateByFormID(newResultInfo.getPayFormID());					
							
							if(dRate>0)
							{
								newResultInfo.setInterest(newResultInfo.getInterest()/dRate*100);
								newResultInfo.setInterestBalance(newResultInfo.getAmount()/dRate*100);							
							}
						}*/		
						resultVec.addElement(newResultInfo);
					}
					log.info("lhj debug info ===结束贷款算息=======");
				}
			}
		}
		log.info("-------处理查询数据结束--------");
	}
	private InterestEstimateQueryResultInfo getFixedInfo(
	InterestEstimateQueryResultInfo resultInfo,
		FixedDepositAccountPayableInterestInfo fixedInterestInfo)
	{
		//生成新的纪录加入到Vector
		InterestEstimateQueryResultInfo newResultInfo = new InterestEstimateQueryResultInfo();
		newResultInfo.setAccountNo(resultInfo.getAccountNo());
		newResultInfo.setAccountTypeID(resultInfo.getAccountTypeID());
		newResultInfo.setClientName(resultInfo.getClientName());
		newResultInfo.setAccountID(resultInfo.getAccountID());
		newResultInfo.setSubAccountID(resultInfo.getSubAccountID());
		newResultInfo.setFixedDepositNo(resultInfo.getFixedDepositNo());
		newResultInfo.setContractNo(resultInfo.getContractNo());
		newResultInfo.setPayFormNo(resultInfo.getPayFormNo());		
		newResultInfo.setStartDate(fixedInterestInfo.getSDate());  
		newResultInfo.setEndDate(fixedInterestInfo.getEDate());
		newResultInfo.setBalance(fixedInterestInfo.getBalance());
		newResultInfo.setInterest(fixedInterestInfo.getInterest());
		newResultInfo.setRate(fixedInterestInfo.getRate());
		newResultInfo.setDays(fixedInterestInfo.getDays());
		newResultInfo.setStrStartDate(fixedInterestInfo.getStrStartDate());
		newResultInfo.setStrEndDate(fixedInterestInfo.getStrEndDate());
		newResultInfo.setStrDays(fixedInterestInfo.getStrDays());
		newResultInfo.setStrRate(fixedInterestInfo.getStrRate());
		newResultInfo.setStrInterest(fixedInterestInfo.getStrInterest());
		return newResultInfo;
	}

	private InterestEstimateQueryResultInfo getLoanInfo(
		InterestEstimateQueryResultInfo resultInfo,
		LoanAccountInterestInfo loanInterestInfo)
	{
		InterestEstimateQueryResultInfo newResultInfo = new InterestEstimateQueryResultInfo();
		newResultInfo.setAccountNo(resultInfo.getAccountNo());
		newResultInfo.setAccountTypeID(resultInfo.getAccountTypeID());
		newResultInfo.setLoanTypeID(resultInfo.getLoanTypeID());
		newResultInfo.setClientName(resultInfo.getClientName());
		newResultInfo.setAccountID(resultInfo.getAccountID());
		newResultInfo.setPayFormID(resultInfo.getPayFormID());
		newResultInfo.setSubAccountID(resultInfo.getSubAccountID());
		newResultInfo.setFixedDepositNo(resultInfo.getFixedDepositNo());
		newResultInfo.setContractNo(resultInfo.getContractNo());
		newResultInfo.setPayFormNo(resultInfo.getPayFormNo());
		newResultInfo.setStartDate(loanInterestInfo.getSDate());
		newResultInfo.setEndDate(loanInterestInfo.getEDate());
		newResultInfo.setBalance(loanInterestInfo.getBalance());
		
		//罚息
		//newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
		newResultInfo.setRate(loanInterestInfo.getRate());
		newResultInfo.setDays(loanInterestInfo.getDays());
		return newResultInfo;
	}
	//查询利息匡算表匡算数据
	public Vector selectEstimateRecords(Connection con, InterestEstimateQueryInfo qInfo)
		throws Exception
	{	
		StringBuffer buffer = new StringBuffer();
		String strSQL = "";
		
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //返回值

		//构造查询语句主体		
		buffer.append(" SELECT distinct\n");
		buffer.append(" account.ID AS accountID, \n");
		buffer.append(" account.sAccountNo AS accountNo, \n"); //账户号
		buffer.append(" account.nAccountTypeID AS accountTypeID,\n"); //账户类型		
		buffer.append(" contractform.nTypeID AS LoanTypeID,\n"); //贷款类型
		buffer.append(" payform.id AS payFormID, \n"); //放款通知单ID
		buffer.append(" client.sname ClientName, \n");                              
		buffer.append(" client.scode ClientNo, \n");    		
		buffer.append(" subaccount.ID AS subAccountID, \n"); //子账户 ID
		buffer.append(" subaccount.AF_sDepositNo AS fixedDepositNo,\n "); //定期单据号		
		buffer.append(" subaccount.AF_mRate AS Rate, \n"); //利率
		buffer.append(" subaccount.mBalance AS Balance, \n"); //当前余额
		buffer.append(" subaccount.mOpenAmount AS Amount, \n"); //开户金额		
		buffer.append(" contractform.sContractCode AS contractNo, \n"); //合同号
		buffer.append(" payform.sCode AS payFormNo \n"); //放款通知单号
		
		buffer.append(" FROM \n");
		//buffer.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client \n");
		buffer.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client,client consigner \n");
		buffer.append(" WHERE \n");
		
		buffer.append(" subaccount.nAccountID = account.ID \n");
		buffer.append(" AND ");
		buffer.append(" account.nClientID = client.ID \n");
		buffer.append(" AND ");
		buffer.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		buffer.append(" AND ");
		buffer.append(" payform.nContractID = contractform.ID \n");
		buffer.append(" AND ");
		buffer.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if(qInfo.getIsSelectConsigner()>0)
		{			
			buffer.append(" AND ");
			buffer.append(" contractform.nConsignClientID = consigner.ID \n");		
						
		}
		
		buffer.append(" AND account.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		buffer.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		if(qInfo.getIsSelectClientNo()>0)
		{		
			if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
				buffer.append(" and client.scode>='" + qInfo.getClientNoFrom() + "' \n");
			if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
				buffer.append(" and client.scode<='" + qInfo.getClientNoTo() + "' \n");
		}	
		if(qInfo.getIsSelectClearInterestDate()>0)
		{
			if(qInfo.getClearInterestDate() != null)
			{                                    //结息日				
				buffer.append("and subaccount.dtClearInterest<= to_date('"+DataFormat.formatDate(qInfo.getClearInterestDate())+"','yyyy-mm-dd') \n");
			}	    	
		}
		
		if(qInfo.getIsSelectSelfLoanSort()>0 && qInfo.getIsSelectConsignLoanSort() >0)
		{
			if(qInfo.getSelfLoanSort() >0 && qInfo.getConsignLoanSort() >0)
			{
				buffer.append(" and contractform.nsubtypeid in (" + qInfo.getSelfLoanSort() + ", " + qInfo.getConsignLoanSort() + ") \n");
			}
			else if(qInfo.getSelfLoanSort() >0)
			{				
				buffer.append("and contractform.nsubtypeid=" + qInfo.getSelfLoanSort() + " \n");
			}
			else if(qInfo.getConsignLoanSort() >0)
			{				
				buffer.append("and contractform.nsubtypeid=" + qInfo.getConsignLoanSort() + " \n");
			}
		}
		//自营贷款种类
		else if(qInfo.getIsSelectSelfLoanSort()>0)
		{
			if(qInfo.getSelfLoanSort() >0)
			{				
				buffer.append("and contractform.nsubtypeid=" + qInfo.getSelfLoanSort() + " \n");
			}
			if(qInfo.getSelfLoanSort() ==0)
			{				
				buffer.append("and contractform.ntypeid =1  \n"); 
			}
		}
		//委托贷款种类
		else if(qInfo.getIsSelectConsignLoanSort()>0)
		{
			if(qInfo.getConsignLoanSort() >0)
			{				
				buffer.append("and contractform.nsubtypeid=" + qInfo.getConsignLoanSort() + " \n");
			}
			if(qInfo.getSelfLoanSort() ==0)
			{				
				buffer.append("and contractform.ntypeid =2  \n"); 
			}
		}
		//自营贷款期限		
		if(qInfo.getIsSelectSelfLoanTerm()>0)
		{
			if(qInfo.getSelfLoanTermFrom() >0)
			{				
				buffer.append("and contractform.nIntervalNum >=" + qInfo.getSelfLoanTermFrom() + " \n");
			}
			if(qInfo.getSelfLoanTermTo() >0)
			{				
				buffer.append("and contractform.nIntervalNum <=" + qInfo.getSelfLoanTermTo() + " \n");				
			}
			// add by kevin(刘连凯)2011-08-01 当选择的自营贷款期限时，合同类型应默认是自营
			buffer.append("and contractform.ntypeid ="+LOANConstant.LoanType.ZY+" \n"); 
		}
		if(qInfo.getIsSelectConsigner()>0)
		{
			if (qInfo.getConsignerFrom() != null && qInfo.getConsignerFrom().length() > 0)
			{                                    
				buffer.append("and consigner.scode>='" + qInfo.getConsignerFrom() + "' \n");				
			}
			if (qInfo.getConsignerTo() != null && qInfo.getConsignerTo().length() > 0)
			{                                    
				buffer.append("and consigner.scode<='" + qInfo.getConsignerTo() + "' \n");				
			}						
		}
		if(qInfo.getIsSelectContractNo()>0)
		{
			if (qInfo.getContractNoFrom() != null && qInfo.getContractNoFrom().length() > 0)
			{                                    
				buffer.append("and contractform.sContractCode>='" + qInfo.getContractNoFrom() + "' \n");				
			}
			if (qInfo.getContractNoTo() != null && qInfo.getContractNoTo().length() > 0)
			{                                    
				buffer.append("and contractform.sContractCode<='" + qInfo.getContractNoTo() + "' \n");				
			}			
		}
		if(qInfo.getIsSelectPayFormNo()>0)
		{
			if (qInfo.getPayFormNoFrom() != null && qInfo.getPayFormNoFrom().length() > 0)
			{                                    
				buffer.append("and payform.sCode>='" + qInfo.getPayFormNoFrom() + "' \n");				
			}
			if (qInfo.getPayFormNoTo() != null && qInfo.getPayFormNoTo().length() > 0)
			{                                    
				buffer.append("and payform.sCode<='" + qInfo.getPayFormNoTo() + "' \n");				
			}			
		}
		buffer.append(" AND ");
		buffer.append(" account.nOfficeID = " + qInfo.getOfficeID() + " \n");
		buffer.append(" AND ");
		buffer.append(" account.nCurrencyID = " + qInfo.getCurrencyID() + " \n");

		buffer.append(" Order By ");
		buffer.append(" account.sAccountNo, contractform.sContractCode, payform.sCode");
		// buffer.append(" ) rs_temp ");
		// buffer.append(" ) WHERE r BETWEEN ? AND ? ");

		strSQL = buffer.toString();
		//	    log.debug("strSQL is : " + strSQL);
		log.info(strSQL);
		try
		{
			//设置连接参数
			ps = con.prepareStatement(strSQL);
			int nIndex = 1;			
			
			//当前页的信息    
			

			//执行查询
			rs = ps.executeQuery();

			//取得结果
			log.info("***********设置返回结果***********");
			result = fetchDataFromRS(rs);
		}
		catch (Exception e)
		{
			log.error("检索记录时发生错误：" + e.getMessage());
			throw e;
		}
		finally
		{ //释放资源
			rs.close();
			ps.close();
			if(con==null)
			{
				conInternal.close();
			}
		}

		return result;
	}
	//查询通知存款匡算数据
	public Vector selectNoticeEstimateRecords(Connection con, InterestEstimateQueryInfo qInfo)
		throws Exception
	{	
		StringBuffer buffer = new StringBuffer();
		String strSQL = "";
		
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //返回值

		//构造查询语句主体		
		buffer.append(" SELECT \n");
		buffer.append(" account.ID AS accountID, \n");
		buffer.append(" account.sAccountNo AS accountNo, \n"); //账户号
		buffer.append(" account.nAccountTypeID AS accountTypeID,\n"); //账户类型
		buffer.append(" client.sname ClientName, \n");                              
		buffer.append(" client.scode ClientNo, \n");   
		buffer.append(" subaccount.ID AS subAccountID, \n"); //子账户 ID
		buffer.append(" subaccount.AF_sDepositNo AS fixedDepositNo,\n "); //定期单据号		
		buffer.append(" subaccount.AF_mRate AS Rate, \n"); //利率
		buffer.append(" subaccount.mBalance AS Balance, \n"); //当前余额
		buffer.append(" subaccount.mOpenAmount AS Amount, \n"); //开户金额
		buffer.append(" subaccount.dTopen AS OpenDate \n"); //起始日
		buffer.append(" FROM \n");
		buffer.append(" sett_Account account, sett_SubAccount subaccount,sett_accountType accountType,client \n");
		buffer.append(" WHERE \n");
		
		buffer.append(" subaccount.nAccountID = account.ID \n");
		buffer.append(" AND ");
		buffer.append(" account.nClientID = client.ID \n");
		 
		if(qInfo.getDateTo()!=null && !qInfo.getDateTo().equals("")){ 
			buffer.append(" and subaccount.dTopen< ? \n");
		}
				 
		if (qInfo.getAccountNoFrom() != null && ! qInfo.getAccountNoFrom().equals("-1") && ! qInfo.getAccountNoFrom().equals(""))
			buffer.append(" and account.id>='" + qInfo.getAccountNoFrom() + "' \n");
		if (qInfo.getAccountNoTo() != null && ! qInfo.getAccountNoTo().equals("-1") && ! qInfo.getAccountNoTo().equals(""))
			buffer.append(" and account.id<='" + qInfo.getAccountNoTo() + "' \n");			
		if(qInfo.getDepositNoFrom()!=null && ! qInfo.getDepositNoFrom().equals("")){
			buffer.append(" and subaccount.AF_sDepositNo>='" + qInfo.getDepositNoFrom() + "' \n");
		}
		if(qInfo.getDepositNoTo()!=null && ! qInfo.getDepositNoTo().equals("")){
			buffer.append(" and subaccount.AF_sDepositNo<='" + qInfo.getDepositNoTo() + "' \n");
		}

		if(qInfo.getNoticeDays() >0)
		{				
			buffer.append("and subaccount.AF_nNoticeDay =" + qInfo.getNoticeDays() + " \n");
		}
		
		//通知存款
		buffer.append(" AND account.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
			buffer.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		buffer.append("and account.nAccountTypeID = accountType.id and accountType.nAccountGroupID= " + SETTConstant.AccountGroupType.NOTIFY + "  \n");
		if (qInfo.getOfficeID() > 0){
			buffer.append(" AND  account.nOfficeID = " + qInfo.getOfficeID() + " \n");
		}
		if(qInfo.getCurrencyID()>0){
			buffer.append(" AND  account.nCurrencyID = " + qInfo.getCurrencyID() + " \n");			
		}
		
		buffer.append(" Order By ");
		buffer.append(" account.sAccountNo");
		

		strSQL = buffer.toString();		
		log.info(strSQL);
		try
		{
			//设置连接参数
			ps = con.prepareStatement(strSQL);
			int nIndex = 0;			
			if(qInfo.getDateTo()!=null && !qInfo.getDateTo().equals("")){ 
				ps.setTimestamp(1,qInfo.getDateTo());
			}
			
			//当前页的信息    
			

			//执行查询
			rs = ps.executeQuery();

			//取得结果
			log.info("***********设置返回结果***********"+rs);
			result = fetchDataFromNoticeRS(rs);
			
			for(int i=0;i<10;i++)
				System.out.println("=========得到的数据结果的长度为:"+result.size());
			
			
		}
		catch (Exception e)
		{
			log.error("检索结息记录时发生错误：" + e.getMessage());
			throw e;
		}
		finally
		{ //释放资源
			rs.close();
			ps.close();
			if(con==null)
			{
				conInternal.close();
			}
		}		
		return result;
	}
	/**
	 * 从结果集向数据实体赋值(全部)
	 */
	private Vector fetchDataFromRS(ResultSet rs) throws Exception
	{
		Vector rstVec = new Vector();		
		while (rs.next())
		{
			InterestEstimateQueryResultInfo info = new InterestEstimateQueryResultInfo();
			info.setAccountNo(rs.getString("accountNo"));
			info.setAccountTypeID(rs.getLong("accountTypeID"));
			info.setLoanTypeID(rs.getLong("LoanTypeID"));
			info.setAccountID(rs.getLong("accountID"));
			info.setSubAccountID(rs.getLong("subAccountID"));
			info.setClientName(rs.getString("ClientName"));
			info.setFixedDepositNo(rs.getString("fixedDepositNo"));
			info.setContractNo(rs.getString("contractNo"));
			info.setPayFormNo(rs.getString("payFormNo"));
			info.setPayFormID(rs.getLong("PayFormID"));
			info.setBalance(rs.getDouble("Balance"));
			info.setAmount(rs.getDouble("Amount"));
			info.setRate(rs.getDouble("Rate"));	
			
			rstVec.addElement(info);			
		}
		return rstVec;
	}	
	/**
	 * 从结果集向数据实体赋值(全部)
	 */
	private Vector fetchDataFromNoticeRS(ResultSet rs) throws Exception
	{
		Vector rstVec = new Vector();		
		while (rs.next())
		{
			InterestEstimateQueryResultInfo info = new InterestEstimateQueryResultInfo();
			info.setAccountNo(rs.getString("accountNo"));
			info.setAccountTypeID(rs.getLong("accountTypeID"));
			info.setAccountID(rs.getLong("accountID"));
			info.setSubAccountID(rs.getLong("subAccountID"));
			info.setClientName(rs.getString("ClientName"));
			info.setFixedDepositNo(rs.getString("fixedDepositNo"));			
			info.setBalance(rs.getDouble("Balance"));
			info.setAmount(rs.getDouble("Amount"));
			info.setRate(rs.getDouble("Rate"));					
			rstVec.addElement(info);			
			
		}
		return rstVec;
	}
    /**
     * 从容器或直接通过JDBC取得数据库连接。
     * @return 数据库连接。
     */
    private Connection getConnection() throws Exception{
        
        Connection con = null;
        if(bConnectionFromContainer){
            con = Database.getConnection();
        }else{
            con = Database.getConnection();
        }
        return con;
    }
    
    /**
     * 设置数据库连接的来源。
     * @param bConnectionFromContainer
     *        true - 从容器取得数据库连接。
     *        false - 直接通过JDBC取得数据库连接。
     */
    public void setConnectionFromContainer(boolean bConnectionFromContainer){
        this.bConnectionFromContainer = bConnectionFromContainer;
    }
    
    /**
     * 取得数据库连接的来源。
     * @return 数据库连接的来源。
     *        true - 从容器取得数据库连接。
     *        false - 直接通过JDBC取得数据库连接。
     */
    public boolean getConnectionFromContainer(){
        return bConnectionFromContainer;
    }    
	
    public static void main(String[] args) throws Exception{
     
      InterestSettlement settlement = new InterestSettlement();
      InterestQueryInfo info = new InterestQueryInfo();
      settlement.setConnectionFromContainer(false);
        
      Vector rst = null;
	  rst = settlement.findSettlementRecords(null, info);
      /*for(int i = 0; i < 7; i++){
          System.out.println(info.getPageInfo());
          rst = settlement.findSettlementRecords(null, info);
          System.out.println(rst.length);
          info.nextPage();
      }
      info.gotoPage(5);
      rst = settlement.findSettlementRecords(null, info);
      System.out.println(rst.length);
      System.out.println(info.getPageInfo());
      info.nextPage();*/
   }
}
