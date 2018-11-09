/*
 * Created on 2003-10-28
 * 
 * InterestSettlement.java
 */

package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.interest.dao.Sett_CompoundInterestSettingDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_InterestFeeSettingDetailDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_TransInterestSettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.BankInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.ClearLoanAccountInterestConditionInfo;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.FixedDepositAccountPayableInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestAccountIDInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author Allan Liu
 * 
 * 利息费用结算结息处理业务对象，主要实现的功能包括：
 * 
 * 1.查询结息记录。 2.批量计提利息。 3.批量冲销计提利息。 4.批量结算利息。
 * 
 * 注意事项:为了使该数据访问对象对容器的事务和非容器的事务都提供支持，
 * 
 */
public class InterestSettlement
{

	/**
	 * 如果是该类的方法负责维护事务，应该从容器取得数据库连接还是直接通过JDBC访问，缺省为从容器取得。
	 */
	private boolean	bConnectionFromContainer	= true;

	private Log4j	log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 从容器或直接通过JDBC取得数据库连接。
	 * 
	 * @return 数据库连接。
	 */
	private Connection getConnection() throws Exception
	{

		Connection con = null;
		if (bConnectionFromContainer)
		{
			con = Database.getConnection();
		}
		else
		{
			con = Database.get_jdbc_connection();
		}
		return con;
	}

	/**
	 * 设置数据库连接的来源。
	 * 
	 * @param bConnectionFromContainer true - 从容器取得数据库连接。 false - 直接通过JDBC取得数据库连接。
	 */
	public void setConnectionFromContainer(boolean bConnectionFromContainer)
	{

		this.bConnectionFromContainer = bConnectionFromContainer;
	}

	/**
	 * 取得数据库连接的来源。
	 * 
	 * @return 数据库连接的来源。 true - 从容器取得数据库连接。 false - 直接通过JDBC取得数据库连接。
	 */
	public boolean getConnectionFromContainer()
	{

		return bConnectionFromContainer;
	}

	/**
	 * 根据条件进行结息记录的查询，为计提利息、冲销计提利息和结算功能提供支持。
	 * 
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建 连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param queryInfo 包含查询条件的实体，never null.
	 * @return InterestQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息 需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector findSettlementRecords(Connection con, InterestQueryInfo queryInfo) throws Exception
	{

		Vector resultVec = new Vector(); // 返回值

		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
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
			// 业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			// 表明该方法是这个事务的一个组成部分。

			// 创建利息费用结算结息处理数据访问对象实例
			Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
			// 判断是否续倒填
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
			// 判断是否利率倒填
			Sett_SubAccountDAO subaccDAO = new Sett_SubAccountDAO(conInternal);
			// 查询结息记录。
			// 暂不处理分页，先注掉
			/*
			 * if(queryInfo.needFreshQuiry()){ long lTotalRecord = dao.getRecordCount(conInternal, queryInfo);
			 * queryInfo.initPageInfo(lTotalRecord); }
			 */

			Vector rstVec = new Vector();
			rstVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.INTEREST);
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()是零！！！！！！！！");
				return resultVec;
			}
			log.info("-------开始处理查询数据--------");
			InterestOperation io = new InterestOperation(conInternal);
			InterestBatch ib = new InterestBatch(conInternal);
			for (int i = 0; i < rstVec.size(); i++)
			{
				log.info("lhj debug info ===进入循环算息=======");
				InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
				resultInfo = (InterestQueryResultInfo) rstVec.elementAt(i);
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
						FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
						if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						{
							log.info("lhj debug info ===进入定期计提利息=======");
							// 执行定期计提的计算，生成新的纪录返回
							SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
							subInterestInfo = io.getFixedAccountPredrawInterest(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo.getAccountTypeID(), queryInfo.getClearInterest());
							fixedInterestInfo.setBalance(subInterestInfo.getBalance());
							fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							// lhj
							// modify===下面几个属性好象没有必要显示出来------2003-11-26-----------------------
							fixedInterestInfo.setDays(subInterestInfo.getDays());
							fixedInterestInfo.setEDate(subInterestInfo.getEDate());
							fixedInterestInfo.setRate(subInterestInfo.getRate());
							fixedInterestInfo.setSDate(subInterestInfo.getSDate());
							// lhj
							// modify==---------------------------------------2003-11-26-------------
							InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
							// 计提利息
							newResultInfo.setDrawingInterest(fixedInterestInfo.getInterest());
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							resultVec.addElement(newResultInfo);
							log.info("lhj debug info ===结束定期计提利息=======");
						}
						else if (queryInfo.isBInterest())
						{
							log.info("--------------开始定期算息------------");
							fixedInterestInfo = io.getFixedDepositAccountPayableInterest(resultInfo.getAccountID(),
									resultInfo.getSubAccountID(), queryInfo.getClearInterest());
							log.info("--------------结束定期算息------------");
							InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							newResultInfo.setDrawingResult(true);
							resultVec.addElement(newResultInfo);
						}
						log.info("lhj debug info ===结束定期算息=======");
					}
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||//备付金
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||//准备金
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||//拆借
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) // 活期
					{
						// 利息
						if (queryInfo.isInterest())
						{
							log.info("lhj debug info ===进入活期算息=======");
							CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
							Collection coll = null;
							log.info("-------------判断是否需要单户倒填---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
	
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------开始单户算息倒填---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
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
							currInterestInfo = io.getCurrentDepositAccountInterest(queryInfo.getOfficeID(), queryInfo
									.getCurrencyID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(), Env
									.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()));
	
							resultInfo.setDays(currInterestInfo.getIntervalDays());
							resultInfo.setEndDate(currInterestInfo.getEDate());
							resultInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNegotiationBalance(), currInterestInfo.getAverageNormalBalance()));
							resultInfo.setInterest(currInterestInfo.getNormalInterest());
							resultInfo.setInterestRate(currInterestInfo.getNormalInterestRate());
							resultInfo.setStartDate(currInterestInfo.getSDate());
	
							// //协定利息
							resultInfo.setNegotiateInterest(currInterestInfo.getNegotiationInterest());
							resultInfo.setNegotiateBalance(currInterestInfo.getNegotiationBalance());
							resultInfo.setNegotiateInterestRate(currInterestInfo.getNegotiationInterestRate());
							// 利息类型
							resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							// 结息日
							resultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("lhj debug info ==天数： " + currInterestInfo.getIntervalDays());
							log.info("lhj debug info ==开始日期： " + currInterestInfo.getSDate());
							log.info("lhj debug info ==结束日期： " + currInterestInfo.getEDate());
							log.info("lhj debug info ==正常余额： " + currInterestInfo.getNormalBalance());
							log.info("lhj debug info ==正常利息： " + currInterestInfo.getNormalInterest());
							log.info("lhj debug info ==利率： " + currInterestInfo.getNormalInterestRate());
							log.info("lhj debug info ==协定利息： " + currInterestInfo.getNegotiationInterest());
							log.info("lhj debug info ==协定余额： " + currInterestInfo.getNegotiationBalance());
							log.info("lhj debug info ==协定利率： " + currInterestInfo.getNegotiationInterestRate());
							log.info("lhj debug info ==利率类型： " + SETTConstant.InterestFeeType.INTEREST);
							log.info("lhj debug info ==结息日期： " + queryInfo.getClearInterest());
							log.info("-------------算息结束---------");
	
							// 得到收息账户号与付息账户号
							log.info("-------------得到收息账户号与付息账户号开始---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo.getInterestType());
							resultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							resultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							log.info("-------------得到收息账户号与付息账户号结束---------");
							resultVec.addElement(resultInfo);
							log.info("lhj debug info ===结束活期算息=======");
						}
	
					}
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) // 贷款
					{
						log.info("lhj debug info ===进入贷款算息=======");
						LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
	
						if (queryInfo.isInterest())
						{
							log.info("（interestSettlement 346）lhj debug settlement info >>>>>>>>>>>贷款账户需要计息! ");
							Collection coll = null;
							log.info("-------------判断是否需要单户倒填---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------开始单户倒填---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("单户倒填失败");
										}
										log.info("-------------单户倒填结束---------");
									}
								}
							}
							log.info("-------------判断是否需要单户倒填结束---------");
							
							//add by bingliu 20120307 解决以下问题
							/*自营贷款、委托贷款合同做利率调整（往历史调），结息，关机后再次结息，系统算出的利息没有减去上次已经结过的利息。
							 *结息之前没有倒填利息，会导致两个问题：1.结的利息不对；2.关机时会倒填利息，导致之后的利息都不正确，没有减去上次结的利息。
							 *解决办法：在结息之前的计算方法中加入利率倒算方法。
							 */
							log.info("-------------判断是否有单户利率倒填---------");
							Collection coll2 = null;
							coll2 = subaccDAO.findAccountLoanBankInterestAdjust(queryInfo.getOfficeID(), queryInfo.getCurrencyID(), 
									queryInfo.getClearInterest(),resultInfo.getSubAccountID());
							Iterator itResult2 = null;
							if (coll2 != null && coll2.size() > 0)
							{
								itResult2 = coll2.iterator();
								if (itResult2 != null && itResult2.hasNext())
								{
									while (itResult2.hasNext())
									{
										BankInterestAdjustInfo backinfo = (BankInterestAdjustInfo) itResult2.next();
										log.info("-------------开始单户利率倒填---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), backinfo.getBackDate(), 0, 
												queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										long lUpdateReturn = subaccDAO.updateLoanRateAdjustPayDetail(backinfo.getLoanRateAdjustPayDetailID());
										   log.debug("贷款利率调整详细信息更改标志lUpdateReturn = "+lUpdateReturn);
										if (flag < 0||lUpdateReturn < 0)
										{
											throw new IException("单户利率倒填失败");
										}
										log.info("-------------单户利率倒填结束---------");
									}
								}
							}
							log.info("-------------判断是否有单户利率倒填结束---------");
							
	
							// 利息
							log.info("-------------算息开始---------");
							loanInterestInfo = io.GetLoanAccountInterest(queryInfo.getOfficeID(),
									queryInfo.getCurrencyID(), resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getClearInterest(), Env.getSystemDate(conInternal, queryInfo.getOfficeID(),
											queryInfo.getCurrencyID()));
							log.info("----------打印返回信息-------------");
							log.debug(UtilOperation.dataentityToString(loanInterestInfo));
							log.info("-------------算息结束---------");
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// 利息
							newResultInfo.setInterest(loanInterestInfo.getInterest());
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							newResultInfo.setDays(loanInterestInfo.getDays());
							// 可以计提
							newResultInfo.setDrawingResult(true);
							log.info("（interestSettlement 415）lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID是： "
											+ newResultInfo.getAccountTypeID());
					        log.debug("---------判断账户类型------------");
					        AccountTypeInfo newAccountTypeInfo = null;
					        try {
					        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
							} catch (SQLException e) {
								e.printStackTrace();
							}
							if (newAccountTypeInfo != null) {
								// 委托贷款账户
								if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
								{
									InterestTaxInfo taxInfo = new InterestTaxInfo();
		
									/**
									 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(),
									 * newResultInfo.getSubAccountID(), newResultInfo.getInterest());
									 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
									 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
									 */
		
									// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
									taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(), newResultInfo
											.getSubAccountID(), newResultInfo.getInterest());
									newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
									newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
									newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
		
								}
		
								// 得到收息账户号与付息账户号
								InterestAccountIDInfo interestAccountIDInfo = null;
								interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), newResultInfo.getInterestType());
								newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
								newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
								resultVec.addElement(newResultInfo);
							}
						}
						if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						{
							log.info("（interestSettlement 445）lhj debug settlement info >>>>>>>>>>>贷款账户计提利息-------");
	
							// 执行贷款计提利息的计算，生成新的纪录返回
							SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
							subInterestInfo = io.getLoanAccountPredrawInterest(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo.getAccountTypeID(), queryInfo.getClearInterest());
							loanInterestInfo.setBalance(subInterestInfo.getBalance());
							loanInterestInfo.setDays(subInterestInfo.getDays());
							loanInterestInfo.setEDate(subInterestInfo.getEDate());
							loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							loanInterestInfo.setRate(subInterestInfo.getRate());
							loanInterestInfo.setSDate(subInterestInfo.getSDate());
	
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// 计提利息
							newResultInfo.setDrawingInterest(loanInterestInfo.getInterest());
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							resultVec.addElement(newResultInfo);
						}
						else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
						{
							log.info("（interestSettlement 475）lhj debug settlement info >>>>>>>>>>>贷款账户手续费-------");
							Collection coll = null;
							log.info("-------------判断是否需要单户倒填---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------开始单户倒填---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("单户倒填失败");
										}
										log.info("-------------单户倒填结束---------");
									}
								}
							}
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.COMMISION);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// 手续费
							newResultInfo.setHandlingCharge(loanInterestInfo.getInterest());
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------得到收息账户号与付息账户号---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
	
						}
						else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
						{
							log.info("（interestSettlement 551）lhj debug settlement info >>>>>>>>贷款账户担保费-------");
							Collection coll = null;
							log.info("-------------判断是否需要单户倒填---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------开始单户算息倒填---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("单户倒填失败");
										}
										log.info("-------------单户倒填结束---------");
									}
								}
							}
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.ASSURE);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// 担保费
							newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
							// 利息税费
							/*
							 * InterestTaxInfo taxInfo = new InterestTaxInfo(); log.info("（interestSettlement 611）计算利息税费");
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getAssuranceCharge());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 */
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------得到收息账户号与付息账户号---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
	
						}
						if (queryInfo.isCompoundInterest())
						{
							log.info("（interestSettlement 635）lhj debug info>>>>>贷款复利.....");
							Collection coll = null;
							log.info("-------------判断是否需要单户倒填---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------开始单户算息倒填---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("单户倒填失败");
										}
										log.info("-------------单户倒填结束---------");
									}
								}
							}
							// 复利
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.COMPOUNDINTEREST);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// 复利
							newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMPOUNDINTEREST);
							// 利息税费
							InterestTaxInfo taxInfo = new InterestTaxInfo();
	
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getCompoundInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
							// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
							taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(),
									newResultInfo.getSubAccountID(), newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
	
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------得到收息账户号与付息账户号---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
						}
						if (queryInfo.isForfeitInterest())
						{
							log.info("（interestSettlement 721）lhj debug info>>>>>贷款罚息.....");
	
							Collection coll = null;
							log.info("-------------判断是否需要单户倒填---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------开始单户算息倒填---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("单户倒填失败");
										}
										log.info("-------------单户倒填结束---------");
									}
								}
							}
							// 罚息
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.FORFEITINTEREST);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// 逾期罚息
							newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
							// 利息类型
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);
							// 利息税费
							InterestTaxInfo taxInfo = new InterestTaxInfo();
	
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getForfeitInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
	
							// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
							taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(),
									newResultInfo.getSubAccountID(), newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
	
							// 结息日
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------得到收息账户号与付息账户号---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
						}
						log.info("lhj debug info ===结束贷款算息=======");
					}
				}
			}
			log.info("-------处理查询数据结束--------");
			// 如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
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
			// 执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			// 抛出用以通知事务的组织者。
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
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
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
	 * 根据条件进行结息记录的查询，为计提利息、冲销计提利息和结算功能提供支持。
	 * 
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建 连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param queryInfo 包含查询条件的实体，never null.
	 * @return InterestQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息 需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector selectSettlementRecords(Connection con, InterestQueryInfo queryInfo) throws Exception
	{

		Vector resultVec = new Vector(); // 返回值

		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
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
			// 业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			// 表明该方法是这个事务的一个组成部分。
			// 创建利息费用结算结息处理数据访问对象实例

			Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
			// 判断是否续倒填
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
			
			// 判断是否利率倒填
			Sett_SubAccountDAO subaccDAO = new Sett_SubAccountDAO(conInternal);

			InterestOperation io = new InterestOperation(conInternal);

			InterestBatch ib = new InterestBatch(conInternal);

			// 查询结息记录。
			Vector settlmentVec = null;
			Vector assuerVec = null;
			Vector commissionVec = null;
			Vector compoundVec = null;
			Vector forfeitVec = null;
			Vector preDrawVec = null;

			/**
			 * 进入 info.setInterest(Interest); --利息 true
			 */

			if (queryInfo.isBInterest()) // 是否计算利息
			{
				settlmentVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.INTEREST);

				if (settlmentVec != null && settlmentVec.size() > 0)
				{
					log.info("-------开始处理利息查询数据--------");

					for (int i = 0; i < settlmentVec.size(); i++)
					{

						log.info("lhj debug info ===进入循环算息=======");

						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) settlmentVec.elementAt(i);

				        log.debug("---------判断账户类型------------");
						long accountTypeID = resultInfo.getAccountTypeID();
				        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
				        AccountTypeInfo accountTypeInfo = null;
				        
				        try 
				        {
							accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
						} 
				        catch (SQLException e) 	
				        {
							e.printStackTrace();
						}
				        
						if (accountTypeInfo != null) 
						{
							/**
							 * 如果是贴现帐户传入参数增加 贴现利率 info.getInterestRate() 结束时间 resultInfo.getEndDate()
							 */
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
							{
								resultInfo = this.getDiscountInterestInfo( resultInfo.getFixedDepositNo()
										, resultInfo.getLoanPreDrawInterest()
										, resultInfo.getContractNo()
										, resultInfo.getPayFormNo()
										, resultInfo.getSubAccountID()
										, resultInfo.getAccountID()
										, resultInfo.getAccountTypeID()
										, resultInfo.getInterestRate() // 合同获得利率
										, resultInfo.getEndDate()
										, resultInfo.getContractID()
										, resultInfo.getDiscreID()
										, queryInfo.getOfficeID()
										, queryInfo.getCurrencyID()
										, queryInfo.getClearInterest()
										, Env.getSystemDate(conInternal , queryInfo.getOfficeID(), queryInfo.getCurrencyID())
										, SETTConstant.InterestFeeType.INTEREST
										, io
										, ib
										, transDetailDAO );
							}
							// 是否活期账户组类型
							// 是否保证金账户组类型
							else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING
								   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN )
							{
								resultInfo = this.getInterestInfo( resultInfo.getFixedDepositNo()
										, resultInfo.getLoanPreDrawInterest()
										, resultInfo.getContractNo()
										, resultInfo.getPayFormNo()
										, resultInfo.getSubAccountID()
										, resultInfo.getAccountID()
										, resultInfo.getAccountTypeID()
										, queryInfo.getOfficeID()
										, queryInfo.getCurrencyID()
										,  queryInfo.getClearInterest()
										, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
										, SETTConstant.InterestFeeType.INTEREST
										, io
										, ib
										, transDetailDAO );
							}
							else
							{
								//add by zcwang 2007-6-23
								long payFormID=-1;
								payFormID=resultInfo.getPayFormID();

								resultInfo = this.getInterestInfo( resultInfo.getFixedDepositNo()
										, resultInfo.getLoanPreDrawInterest()
										, resultInfo.getContractNo()
										, resultInfo.getPayFormNo()
										, resultInfo.getSubAccountID()
										, resultInfo.getAccountID()
										, resultInfo.getAccountTypeID()
										, queryInfo.getOfficeID()
										, queryInfo.getCurrencyID()
										, queryInfo.getClearInterest()
										, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
										, SETTConstant.InterestFeeType.INTEREST
										, io
										, ib
										, transDetailDAO
										, subaccDAO);
								
								//add by zcwang 2007-6-23
								resultInfo.setPayFormID(payFormID);
							}
	
							// 处理利息值
							resultInfo.setInterest(UtilOperation.Arith.round(resultInfo.getInterest(), 2));
	
							settlmentVec.setElementAt(resultInfo, i);
	
							// 加入返回结果
							resultVec.addElement(resultInfo);
						}
					}

					log.info("-------处理利息查询数据结束--------");
				}

			}

			if (queryInfo.isBCompoundInterest())
			{
				compoundVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.COMPOUNDINTEREST);

				if (compoundVec != null && compoundVec.size() > 0)
				{
					log.info("-------开始处理复利数据--------");
					for (int i = 0; i < compoundVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) compoundVec.elementAt(i);
						//add by zcwang 2007-6-23
						long payFormID=-1;
						payFormID=resultInfo.getPayFormID();
						resultInfo = this.getInterestInfo(resultInfo.getFixedDepositNo(), resultInfo
								.getLoanPreDrawInterest(), resultInfo.getContractNo(), resultInfo.getPayFormNo(),
								resultInfo.getSubAccountID(), resultInfo.getAccountID(), resultInfo.getAccountTypeID(),
								queryInfo.getOfficeID(), queryInfo.getCurrencyID(), queryInfo.getClearInterest(),
								Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.COMPOUNDINTEREST, io, ib, transDetailDAO);
						//add by zcwang 2007-6-23
						resultInfo.setPayFormID(payFormID);
						compoundVec.setElementAt(resultInfo, i);

						//加入返回结果
						resultVec.addElement(resultInfo);
					}
					log.info("-------处理复利数据结束--------");
				}
			}
			
			if (queryInfo.isBForfeitInterest())
			{
				forfeitVec = dao.selectSettlementRecords(conInternal, queryInfo,
						SETTConstant.InterestFeeType.FORFEITINTEREST);
				if (forfeitVec != null && forfeitVec.size() > 0)
				{
					log.info("-------开始处理罚息数据--------");
					for (int i = 0; i < forfeitVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) forfeitVec.elementAt(i);
						//		add by zcwang 2007-6-23
						long payFormID=-1;
						payFormID=resultInfo.getPayFormID();
						//
						resultInfo = this.getInterestInfo(resultInfo.getFixedDepositNo(), resultInfo
								.getLoanPreDrawInterest(), resultInfo.getContractNo(), resultInfo.getPayFormNo(),
								resultInfo.getSubAccountID(), resultInfo.getAccountID(), resultInfo.getAccountTypeID(),
								queryInfo.getOfficeID(), queryInfo.getCurrencyID(), queryInfo.getClearInterest(),
								Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.FORFEITINTEREST, io, ib, transDetailDAO);
					    //		add by zcwang 2007-6-23
						resultInfo.setPayFormID(payFormID);
						//

						forfeitVec.setElementAt(resultInfo, i);

						// 加入返回结果
						resultVec.addElement(resultInfo);
					}
					log.info("-------处理罚息数据结束--------");
				}

			}
			
			if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
			{
				commissionVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.COMMISION);
				if (commissionVec != null && commissionVec.size() > 0)
				{
					log.info("-------开始处理手续费数据--------");
					for (int i = 0; i < commissionVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) commissionVec.elementAt(i);
						resultInfo = this.getInterestInfo(
								  resultInfo.getFixedDepositNo()
								, resultInfo.getLoanPreDrawInterest()
								, resultInfo.getContractNo()
								, resultInfo.getPayFormNo()
								, resultInfo.getSubAccountID()
								, resultInfo.getAccountID()
								, resultInfo.getAccountTypeID()
								, queryInfo.getOfficeID()
								, queryInfo.getCurrencyID()
								, queryInfo.getClearInterest()
								, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
								, SETTConstant.InterestFeeType.COMMISION
								, io
								, ib
								, transDetailDAO);
						
						commissionVec.setElementAt(resultInfo, i);

						// 加入返回结果
						resultVec.addElement(resultInfo);
					}
					log.info("-------处理手续费数据结束--------");
				}
			}
			
			if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
			{
				assuerVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.ASSURE);
				if (assuerVec != null && assuerVec.size() > 0)
				{
					log.info("-------开始处理担保费数据--------");
					for (int i = 0; i < assuerVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) assuerVec.elementAt(i);
						resultInfo = this.getInterestInfo(resultInfo.getFixedDepositNo(), resultInfo
								.getLoanPreDrawInterest(), resultInfo.getContractNo(), resultInfo.getPayFormNo(),
								resultInfo.getSubAccountID(), resultInfo.getAccountID(), resultInfo.getAccountTypeID(),
								queryInfo.getOfficeID(), queryInfo.getCurrencyID(), queryInfo.getClearInterest(),
								Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.ASSURE, io, ib, transDetailDAO);
						assuerVec.setElementAt(resultInfo, i);

						// 加入返回结果
						resultVec.addElement(resultInfo);
					}
					log.info("-------处理担保费数据结束--------");
				}
			}

			/**
			 * 进入 info.setFeeType(lFeeType); --计提利息 6
			 */

			// 计提利息
			if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{

				preDrawVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.PREDRAWINTEREST);

				if (preDrawVec != null && preDrawVec.size() > 0)
				{
					log.info("-------开始处理计提利息数据--------");

					for (int i = 0; i < preDrawVec.size(); i++)
					{

						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) preDrawVec.elementAt(i);
						//add by zcwang 2007-6-23
						long payFormID=-1;
						payFormID=resultInfo.getPayFormID();

						resultInfo = this.getInterestInfo(
								resultInfo.getFixedDepositNo()
								, resultInfo.getLoanPreDrawInterest()
								, resultInfo.getContractNo()
								, resultInfo.getPayFormNo()
								, resultInfo.getSubAccountID()
								, resultInfo.getAccountID()
								, resultInfo.getAccountTypeID()
								, queryInfo.getOfficeID()
								, queryInfo.getCurrencyID()
								, queryInfo.getClearInterest()
								, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
								, SETTConstant.InterestFeeType.PREDRAWINTEREST  //6
								, io
								, ib
								, transDetailDAO );
						
						//add by zcwang 2007-6-23
						resultInfo.setPayFormID(payFormID);
						
						preDrawVec.setElementAt(resultInfo, i);

						//加入返回结果
						resultVec.addElement(resultInfo);
					}

					log.info("-------处理计提利息数据结束--------");

				}

			}

			log.info("-------开始处理查询数据--------");
			log.info("-------处理查询数据结束--------");

			// 如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
			if (con == null)
			{
				try
				{
					conInternal.commit();
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}

		}

		catch (Exception e)
		{
			// 执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			// 抛出用以通知事务的组织者。
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
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
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
	 * 贴现算息(只供贴现使用)
	 * 
	 * @param resultInfo
	 * @param fixedInterestInfo
	 * @return
	 */
	public InterestQueryResultInfo getDiscountInterestInfo(String strFixDepsitNo, double mloanPredrawInterest,
			String strContractNo, String strPayFormNo, long nSubAccountID, long nAccountID, long nAccountTYpe,
			double dInterestRate, Timestamp enddate, long contractID, long discreID, long nOfficeID, long nCurrencyID,
			Timestamp dtClearInterest, Timestamp dtSysdate, long nInterestType // 1
			, InterestOperation io, InterestBatch ib, sett_TransAccountDetailDAO transDetailDAO) throws Exception
	{

		InterestQueryResultInfo interestInfo = new InterestQueryResultInfo();
		// 设置返回结果的初始信息
		InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();

		resultInfo.setAccountID(nAccountID);
		resultInfo.setAccountNo(NameRef.getAccountNoByID(nAccountID));
		resultInfo.setSubAccountID(nSubAccountID);
		resultInfo.setAccountTypeID(nAccountTYpe);
		resultInfo.setContractNo(strContractNo);
		resultInfo.setPayFormNo(strPayFormNo);
		resultInfo.setFixedDepositNo(strFixDepsitNo);
		resultInfo.setLoanPreDrawInterest(mloanPredrawInterest);

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
			// 是否贴现贷款账户组类型
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
			{
				log.info("lhj debug info ===进入贴现贷款算息=======");
	
				LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
				FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
	
				// 利息
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
	
					log.info("贴现账户需要计息! ");
					/*
					 * 2011-03-18 马现福 注释：贴现账户不结息，不倒填利息
					 *
					Collection coll = null;
	
					log.info("-------------判断是否需要单户倒填---------");
	
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
	
					Iterator itResult = null;
	
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------开始贴现单户算息倒填---------");
	
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
	
								if (flag < 0)
								{
									throw new IException("贴现单户倒填失败");
								}
	
								log.info("-------------贴现单户倒填结束---------");
	
							}
						}
					}
	
					log.info("-------------判断是否需要贴现单户倒填结束---------");
	
					// 利息
	
					// loanInterestInfo = io.GetDiscountAccountInterest(
					// nOfficeID
					// , nCurrencyID
					// , resultInfo.getAccountID()
					// , resultInfo.getSubAccountID()
					// , dtClearInterest
					// , dtSysdate
					// , dInterestRate
					// );
					 * 
					 */
	
					/**
					 * 修改的地方
					 */
					log.info("-------------贴现算息开始---------");
					// 计算利息然后返回
					fixedInterestInfo = io.getDiscountAccountPayableInterest(resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), dtClearInterest // 页面录入"结息日"
							, dInterestRate // 贴现合同中的利率
							, enddate, contractID, discreID);
	
					log.info("-------------贴现算息结束---------");
	
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
	
					// 利息
					// sett_DailyAccountBalance表字段累计利息/累计正常利息MINTEREST
					// 计提的时候用的是此数据
					// 累计利息 - 子帐户中的计提利息
					newResultInfo.setInterest(fixedInterestInfo.getInterest());
	
					/**
					 * 可能需要修改的地方 如果委贷多次计提 计提利息 = 累计利息 - 子帐户贷款计提利息
					 */
					// if ( SETTConstant.AccountType.isConsignAccountType(
					// resultInfo.getAccountTypeID() ) )
					// {
					// 子账户
					// Sett_SubAccountDAO sett_SubAccountDAO = new
					// Sett_SubAccountDAO();
					// SubAccountLoanInfo subAccInfo = null;
					// subAccInfo =
					// sett_SubAccountDAO.findByID(resultInfo.getSubAccountID()).getSubAccountLoanInfo();
					// newResultInfo.setDrawingInterest(loanInterestInfo.getInterest()
					// - subAccInfo.getPreDrawInterest());
					// }
					// 利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					// 结息日
					newResultInfo.setCreateDate(dtClearInterest);
					// newResultInfo.setDays(loanInterestInfo.getDays());
	
					// 可以计提
					newResultInfo.setDrawingResult(true);
	
					// 计提利息
					newResultInfo.setDrawingInterest(fixedInterestInfo.getDrawinterest());
	
					log.info("贴现newResultInfo.getAccountTypeID是： " + newResultInfo.getAccountTypeID());
	
					// 得到收息账户号与付息账户号
					InterestAccountIDInfo interestAccountIDInfo = null;
	
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(),
							resultInfo.getSubAccountID(), nInterestType);
	
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
	
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					return newResultInfo;
				}
	
				// 计提利息 6
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
	
					log.info("贴现贷款账户计提利息-------");
	
					// 执行贷款计提利息的计算，生成新的纪录返回
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getLoanAccountPredrawInterest(resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), resultInfo.getAccountTypeID(), dtClearInterest);
	
					// loanInterestInfo.setBalance(subInterestInfo.getBalance());
					/**
					 * 修改的地方
					 */
					// 计算利息然后返回
					fixedInterestInfo = io.getDiscountAccountPayableInterest(resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), dtClearInterest // 页面录入"结息日"
							, dInterestRate // 贴现合同中的利率
							, enddate, contractID, discreID);
					// 不取子帐户中的余额字段,取没有到期票的总金额
					loanInterestInfo.setBalance(fixedInterestInfo.getBalance());
	
					loanInterestInfo.setDays(subInterestInfo.getDays());
					loanInterestInfo.setEDate(subInterestInfo.getEDate());
	
					loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest()); // 子帐户表中的计提利息
	
					loanInterestInfo.setRate(subInterestInfo.getRate());
					loanInterestInfo.setSDate(subInterestInfo.getSDate());
	
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
	
					// 计提利息
					// 从表sett_SubAccount获得以前的AF_MPREDRAWINTEREST计提利息字段的值
					newResultInfo.setDrawingInterest(loanInterestInfo.getInterest());
	
					// 利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST); // 计提利息
	
					// 结息日
					newResultInfo.setCreateDate(dtClearInterest); // 页面录入结息日
	
					return newResultInfo;
				}
				else if (nInterestType == SETTConstant.InterestFeeType.COMMISION)
				{
				}
	
				else if (nInterestType == SETTConstant.InterestFeeType.ASSURE)
				{
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
				}
				log.info("lhj debug info ===结束贴现贷款算息=======");
			}
		}
		return interestInfo;
	}
	
	/**
	 * 根据查询记录返回已经算完息的记录
	 * 
	 * @param resultInfo
	 * @param fixedInterestInfo
	 * @return
	 */
	public InterestQueryResultInfo getInterestInfo( String strFixDepsitNo
			, double mloanPredrawInterest
			, String strContractNo
			, String strPayFormNo
			, long nSubAccountID
			, long nAccountID
			, long nAccountTYpe
			, long nOfficeID
			, long nCurrencyID
			, Timestamp dtClearInterest
			, Timestamp dtSysdate
			, long nInterestType // 1
			, InterestOperation io
			, InterestBatch ib
			, sett_TransAccountDetailDAO transDetailDAO) throws Exception
	{
		return this.getInterestInfo(strFixDepsitNo
				, mloanPredrawInterest
				, strContractNo
				, strPayFormNo
				, nSubAccountID
				, nAccountID
				, nAccountTYpe
				, nOfficeID
				, nCurrencyID
				, dtClearInterest
				, dtSysdate
				, nInterestType // 1
				, io
				, ib
				, transDetailDAO
				, null);
	}

	/**
	 * 根据查询记录返回已经算完息的记录
	 * 
	 * @param resultInfo
	 * @param fixedInterestInfo
	 * @return
	 */
	public InterestQueryResultInfo getInterestInfo( String strFixDepsitNo
			, double mloanPredrawInterest
			, String strContractNo
			, String strPayFormNo
			, long nSubAccountID
			, long nAccountID
			, long nAccountTYpe
			, long nOfficeID
			, long nCurrencyID
			, Timestamp dtClearInterest
			, Timestamp dtSysdate
			, long nInterestType // 1
			, InterestOperation io
			, InterestBatch ib
			, sett_TransAccountDetailDAO transDetailDAO
			, Sett_SubAccountDAO subaccDAO) throws Exception
	{

		InterestQueryResultInfo interestInfo = new InterestQueryResultInfo();
		// 设置返回结果的初始信息
		InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();

		resultInfo.setAccountID(nAccountID);
		resultInfo.setAccountNo(NameRef.getAccountNoByID(nAccountID));
		resultInfo.setSubAccountID(nSubAccountID);
		resultInfo.setAccountTypeID(nAccountTYpe);
		resultInfo.setContractNo(strContractNo);
		resultInfo.setPayFormNo(strPayFormNo);
		resultInfo.setFixedDepositNo(strFixDepsitNo);
		resultInfo.setLoanPreDrawInterest(mloanPredrawInterest);

        log.debug("---------判断账户类型------------");
        
		long accountTypeID = resultInfo.getAccountTypeID();
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        
        try 
        {
			accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		
		if (accountTypeInfo != null) 
		{
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED || // 是否定期账户组类型
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY) // 是否通知账户组类型
			{
				log.info("lhj debug info ===进入定期算息=======");
	
				FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
	
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST) // 利息
				{
					log.info("--------------开始定期算息------------");
	
					//计算利息然后返回
					fixedInterestInfo = io.getNewDepositAccountInterest(
							resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest // 页面录入"结息日"
							);
	
					log.info("--------------结束定期算息------------");
	
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
	
					//利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST); // 类型为利息
	
					//结息日
					newResultInfo.setCreateDate(dtClearInterest);
	
					//是否计提成功标志：对定期存款账户无效（一定成功）
					newResultInfo.setDrawingResult(true);
	
					//计提利息
					newResultInfo.setDrawingInterest(fixedInterestInfo.getDrawinterest());
	
					log.info("lhj debug info ===结束定期算息=======");
	
					return newResultInfo;
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST) // 计提利息
				{
	
					log.info("lhj debug info ===进入定期计提利息=======");
	
					//执行定期计提的计算，生成新的纪录返回
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getFixedAccountPredrawInterest(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, resultInfo.getAccountTypeID()
							, dtClearInterest);
	
					fixedInterestInfo.setBalance(subInterestInfo.getBalance());
					fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
					//lhj
					//modify===下面几个属性好象没有必要显示出来------2003-11-26-----------------------
					fixedInterestInfo.setDays(subInterestInfo.getDays());
					
					//2008年2月25日 结束日期应该取计提日期 Boxu Add
					//fixedInterestInfo.setEDate(subInterestInfo.getEDate());
					fixedInterestInfo.setEDate(subInterestInfo.getPredrawDate());
					
					fixedInterestInfo.setRate(subInterestInfo.getRate());
					fixedInterestInfo.setSDate(subInterestInfo.getSDate());
					//lhj
					//modify==---------------------------------------2003-11-26-------------
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
					//计提利息
					newResultInfo.setDrawingInterest(fixedInterestInfo.getInterest());
					newResultInfo.setInterest(0);
					//利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
					//结息日
					newResultInfo.setCreateDate(dtClearInterest); // 页面录入"结息日"
	
					log.info("lhj debug info ===结束定期计提利息=======");
	
					return newResultInfo;
				}
			}
	
			//是否其他存款账户组类型
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
			{
				//利息
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					log.info("lhj debug info ===进入活期（保证金）存款算息======= 。。。");
	
					CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
					Collection coll = null;
	
					log.info("-------------判断是否需要单户倒填---------");
	
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
	
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------开始单户算息倒填---------");
	
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
	
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
	
					currInterestInfo = io.getCurrentDepositAccountInterest(nOfficeID, nCurrencyID, resultInfo
							.getSubAccountID(), dtClearInterest, dtSysdate);
	
					resultInfo.setDays(currInterestInfo.getIntervalDays());
					resultInfo.setEndDate(currInterestInfo.getEDate());
					// 改存平均余额
					// resultInfo.setBalance(currInterestInfo.getNormalBalance());
					// resultInfo.setBalance(currInterestInfo.getAverageNormalBalance());
	
					// 该存平均余额+平均协定余额
					resultInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNormalBalance(), currInterestInfo.getAverageNegotiationBalance()));
	
					/**
					 * 活期算息
					 */
					// 获得了帐户余额表中的累计利息数据
					// 是否活期账户组类型
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT)
					{
						resultInfo.setInterest(UtilOperation.Arith.sub(UtilOperation.Arith.round(currInterestInfo.getNormalInterest(),2), UtilOperation.Arith.round(mloanPredrawInterest,2)));
					}
					else
					{
						resultInfo.setInterest(currInterestInfo.getNormalInterest());
					}
					
					resultInfo.setInterestRate(currInterestInfo.getNormalInterestRate());
	
					//页面显示"开始日期"
					resultInfo.setStartDate(currInterestInfo.getSDate());
	
					//协定利息
					resultInfo.setNegotiateInterest(currInterestInfo.getNegotiationInterest());
					
					//Boxu Add 2008年2月25日 其他存款结息完成后显示利息应该为"0"
					if(currInterestInfo.getSDate().compareTo(dtClearInterest)!=0)
					{
						//
					}
					else
					{
						resultInfo.setInterest(0.0);
						
						//协定利息
						resultInfo.setNegotiateInterest(0.0);
					}
					
					//改存平均协定余额
					//resultInfo.setNegotiateBalance(currInterestInfo.getNegotiationBalance());
					resultInfo.setNegotiateBalance(currInterestInfo.getAverageNegotiationBalance());
					resultInfo.setNegotiateInterestRate(currInterestInfo.getNegotiationInterestRate());
	
					//利息类型
					resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					//结息日
					resultInfo.setCreateDate(dtClearInterest);
	
					log.info("-------------算息结束---------");
	
					//得到收息账户号与付息账户号
					log.info("-------------得到收息账户号与付息账户号开始---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
	
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
	
					resultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					resultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					log.info("-------------得到收息账户号与付息账户号结束---------");
					log.info("lhj debug info ===结束活期（保证金）算息=======");
	
					return resultInfo;
				}
			}
			
			//新增加活期计提 新增加保证金计提
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || // 是否活期账户组类型
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK || // 是否备付金账户组类型
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE || // 是否准备金账户组类型
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING || // 是否拆借账户组类型
					 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) // 是否保证金账户组类型
			{
				//利息
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					log.info("lhj debug info ===进入活期（保证金）存款算息=======");
	
					CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
					Collection coll = null;
	
					log.info("-------------判断是否需要单户倒填---------");
	
					//coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nCurrencyID, nOfficeID, dtClearInterest);
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nOfficeID, nCurrencyID, dtClearInterest);
					
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------开始单户算息倒填---------");
	
								long flag = ib.accountInterestCalculationBackward( resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE );
	
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
	
					currInterestInfo = io.getCurrentDepositAccountInterest( nOfficeID
																		  , nCurrencyID
																		  , resultInfo.getSubAccountID()
																		  , dtClearInterest
																		  , dtSysdate );
	
					resultInfo.setDays(currInterestInfo.getIntervalDays());
					resultInfo.setEndDate(currInterestInfo.getEDate());
					
					//改存平均余额
					//resultInfo.setBalance(currInterestInfo.getNormalBalance());
					//resultInfo.setBalance(currInterestInfo.getAverageNormalBalance());
	
					//改存平均余额 + 平均协定余额
					resultInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNormalBalance(), currInterestInfo.getAverageNegotiationBalance()));
					
					/**
					 * 活期算息
					 */
					// 获得了帐户余额表中的累计利息数据
					// 是否活期账户组类型
					// if (
					// SETTConstant.AccountType.isCurrentAccountType(resultInfo.getAccountTypeID())
					// )
					// {
					
					//Boxu Add 2008年2月25日 活期结息完成后显示利息应该为"0"
					if(currInterestInfo.getSDate().compareTo(dtClearInterest)!=0)
					{
						//modify by xwhe 2008-11-07 当累计计提利息大于零时，我们从sett_dailyaccountbalance表取出计提日的前一天的累计
						//活期利息和累计协定计提利息
						if(mloanPredrawInterest > 0.01)
						{
							Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailyDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conInternal);
							InterestCalculation inter_cal = new InterestCalculation();
							SubAccountCurrentInfo subAccInfo = null;
							
							subAccInfo = sett_SubAccountDAO.findByID(nSubAccountID).getSubAccountCurrenctInfo();
							Timestamp dtLastDate = inter_cal.getNextNDay(subAccInfo.getPreDrawDate(), -1);
							Timestamp dtLastDate1 = inter_cal.getNextNDay(dtClearInterest, -1);
							
							DailyAccountBalanceInfo balanceInfo = dailyDAO.findAllBySubAccountIDAndDate(
									  nSubAccountID
									, nOfficeID
									, nCurrencyID
									, dtLastDate);
							DailyAccountBalanceInfo balanceInfo1 = dailyDAO.findAllBySubAccountIDAndDate(
									  nSubAccountID
									, nOfficeID
									, nCurrencyID
									, dtLastDate1);			
							/** 重新获取利息 **/	
							resultInfo.setInterest(UtilOperation.Arith.sub(balanceInfo1.getInterest(),balanceInfo.getInterest()));
						
							resultInfo.setNegotiateInterest(UtilOperation.Arith.sub(balanceInfo1.getAc_mNegotiateInterest(), balanceInfo.getAc_mNegotiateInterest()));
						}
						else
						{
							resultInfo.setInterest(UtilOperation.Arith.sub(UtilOperation.Arith.round(currInterestInfo.getNormalInterest(),2), UtilOperation.Arith.round(mloanPredrawInterest,2)));
							
							//协定利息
							resultInfo.setNegotiateInterest(currInterestInfo.getNegotiationInterest());
						}
					}
					else
					{
						resultInfo.setInterest(0.0);
						
						//协定利息
						resultInfo.setNegotiateInterest(0.0);
					}
					
					// }
					// else
					// {
					// resultInfo.setInterest(currInterestInfo.getNormalInterest());
					// }
	
					resultInfo.setInterestRate(currInterestInfo.getNormalInterestRate());
	
					//页面显示"开始日期"
					resultInfo.setStartDate(currInterestInfo.getSDate());
					
					//改存平均协定余额
					//resultInfo.setNegotiateBalance(currInterestInfo.getNegotiationBalance());
					resultInfo.setNegotiateBalance(currInterestInfo.getAverageNegotiationBalance());
					resultInfo.setNegotiateInterestRate(currInterestInfo.getNegotiationInterestRate());
	
					//利息类型
					resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					//结息日
					resultInfo.setCreateDate(dtClearInterest);
	
					log.info("-------------算息结束---------");
	
					//得到收息账户号与付息账户号
					log.info("-------------得到收息账户号与付息账户号开始---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
	
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
	
					resultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					resultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					log.info("-------------得到收息账户号与付息账户号结束---------");
					log.info("lhj debug info ===结束活期（保证金）算息=======");
	
					//保存计提利息
					resultInfo.setDrawingInterest(currInterestInfo.getDrawInterest());
	
					return resultInfo;
				}
	
				//计提利息(采用定期计提利息方法)
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
	
					log.info("lhj debug info ===进入活期计提利息=======");
	
					FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
	
					//执行定期计提的计算，生成新的纪录返回
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getFixedAccountPredrawInterest(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, resultInfo.getAccountTypeID()
							, dtClearInterest);
	
					/**
					 * 修改计提显示余额,显示为平均余额
					 */
					CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
					currInterestInfo = io.getCurrentDepositAccountInterest(
							  nOfficeID
							, nCurrencyID
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate);
					//未处理直接取数据库余额字段
					//fixedInterestInfo.setBalance(subInterestInfo.getBalance());
					fixedInterestInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNormalBalance(), currInterestInfo.getAverageNegotiationBalance()));
	
					//计提利息
					fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
	
					//lhj
					//modify===下面几个属性好象没有必要显示出来------2003-11-26-----------------------
					fixedInterestInfo.setDays(subInterestInfo.getDays());
					
					//2008年2月25日 结束日期应该取计提日期 Boxu Add
					//fixedInterestInfo.setEDate(subInterestInfo.getEDate());
					fixedInterestInfo.setEDate(subInterestInfo.getPredrawDate());
					
					fixedInterestInfo.setRate(subInterestInfo.getRate());
					fixedInterestInfo.setSDate(subInterestInfo.getSDate());
					//lhj
					//modify==---------------------------------------2003-11-26-------------
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
	
					//计提利息
					newResultInfo.setDrawingInterest(fixedInterestInfo.getInterest());
	
					newResultInfo.setInterest(0);
					//利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
					//结息日
					newResultInfo.setCreateDate(dtClearInterest);
	
					log.info("lhj debug info ===结束活期计提利息=======");
	
					return newResultInfo;
	
				}
	
			}
			//是否贷款账户组类型
			else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT )
			{
				log.info("lhj debug info ===进入贷款算息=======");
	
				LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
	
				//利息
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					log.info("（interestSettlement 346）lhj debug settlement info >>>>>>>>>>>贷款账户需要计息! ");
	
					Collection coll = null;
	
					log.info("-------------判断是否需要单户倒填---------");
	
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, nCurrencyID
							, nOfficeID
							, dtClearInterest );
	
					Iterator itResult = null;
	
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------开始单户算息倒填---------");
	
								long flag = ib.accountInterestCalculationBackward( resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE );
	
								if (flag < 0)
								{
									throw new IException("单户倒填失败");
								}
	
								log.info("-------------单户倒填结束---------");
	
							}
						}
					}
					
					// 判断是否利率倒填
					if(subaccDAO!=null)
					{
						log.info("-------------判断是否需要单户倒填结束---------");
						
						//add by bingliu 20120307 解决以下问题
						/*自营贷款、委托贷款合同做利率调整（往历史调），结息，关机后再次结息，系统算出的利息没有减去上次已经结过的利息。
						 *结息之前没有倒填利息，会导致两个问题：1.结的利息不对；2.关机时会倒填利息，导致之后的利息都不正确，没有减去上次结的利息。
						 *解决办法：在结息之前的计算方法中加入利率倒算方法。
						 */
						log.info("-------------判断是否有单户利率倒填---------");
						Collection coll2 = null;
						coll2 = subaccDAO.findAccountLoanBankInterestAdjust(nOfficeID, nCurrencyID, 
								dtClearInterest,resultInfo.getSubAccountID());
						Iterator itResult2 = null;
						if (coll2 != null && coll2.size() > 0)
						{
							itResult2 = coll2.iterator();
							if (itResult2 != null && itResult2.hasNext())
							{
								while (itResult2.hasNext())
								{
									BankInterestAdjustInfo backinfo = (BankInterestAdjustInfo) itResult2.next();
									log.info("-------------开始单户利率倒填---------");
									long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
											resultInfo.getSubAccountID(), backinfo.getBackDate(), 0, 
											nOfficeID, nCurrencyID,
											dtSysdate, SETTConstant.BooleanValue.ISFALSE);
									long lUpdateReturn = subaccDAO.updateLoanRateAdjustPayDetail(backinfo.getLoanRateAdjustPayDetailID());
									   log.debug("贷款利率调整详细信息更改标志lUpdateReturn = "+lUpdateReturn);
									if (flag < 0||lUpdateReturn < 0)
									{
										throw new IException("单户利率倒填失败");
									}
									log.info("-------------单户利率倒填结束---------");
								}
							}
						}
						log.info("-------------判断是否有单户利率倒填结束---------");
					}
	
					//利息
					log.info("-------------算息开始---------");
	
					loanInterestInfo = io.GetLoanAccountInterest( nOfficeID
							, nCurrencyID
							, resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate );
	
					log.info("----------打印返回信息-------------");
	
					log.debug(UtilOperation.dataentityToString(loanInterestInfo));
	
					log.info("-------------算息结束---------");
	
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
	
					//利息
					//sett_DailyAccountBalance表字段累计利息/累计正常利息MINTEREST
					//计提的时候用的是此数据
					//累计利息 - 子帐户中的计提利息
					/**** 
					 * Boxu Add 2008年2月22日 解决问题:计提之后选择计提日之前的日期,计算出现负利息的问题
					 * 类/iTreasuryEJB/src/com/iss/itreasury/settlement/interest/bizlogic/InterestCalculation.java 1944-1949行 
					 * *****/
					//if(UtilOperation.Arith.sub(loanInterestInfo.getInterest(), mloanPredrawInterest) < 0)
					//{
					//	newResultInfo.setInterest(0.0);
					//}
					//else
					//{
						newResultInfo.setInterest(UtilOperation.Arith.sub(UtilOperation.Arith.round(loanInterestInfo.getInterest(),2), UtilOperation.Arith.round(mloanPredrawInterest,2)));
					//}
					
					//Boxu Add 2008年2月25日 其他存款结息完成后显示利息应该为"0"
					if(loanInterestInfo.getSDate().compareTo(dtClearInterest)!=0)
					{
						//
					}
					else
					{
						newResultInfo.setInterest(0.0);
					}
						
					/**
					 * 可能需要修改的地方 如果委贷多次计提 计提利息 = 累计利息 - 子帐户贷款计提利息
					 */
					// if ( SETTConstant.AccountType.isConsignAccountType(
					// resultInfo.getAccountTypeID() ) )
					// {
					// 子账户
					// Sett_SubAccountDAO sett_SubAccountDAO = new
					// Sett_SubAccountDAO();
					// SubAccountLoanInfo subAccInfo = null;
					// subAccInfo =
					// sett_SubAccountDAO.findByID(resultInfo.getSubAccountID()).getSubAccountLoanInfo();
					// newResultInfo.setDrawingInterest(loanInterestInfo.getInterest()
					// - subAccInfo.getPreDrawInterest());
					// }
						
					//利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					//结息日
					newResultInfo.setCreateDate(dtClearInterest);
					newResultInfo.setDays(loanInterestInfo.getDays());
	
					//以计提
					newResultInfo.setDrawingResult(true);
	
					//保存计提利息
					newResultInfo.setDrawingInterest(loanInterestInfo.getDrawInterest());
	
					log.info("（interestSettlement 415）lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID是： " + newResultInfo.getAccountTypeID());

			        log.debug("---------判断账户类型------------");
			        AccountTypeInfo newAccountTypeInfo = null;
			        
			        try 
			        {
			        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
					} 
			        catch (SQLException e) 
					{
						e.printStackTrace();
					}
			        
					if (newAccountTypeInfo != null) 
					{
						//委托贷款账户
						if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							InterestTaxInfo taxInfo = new InterestTaxInfo();
		
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getInterest()); newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
		
							//求取利息税费的方式发生变更，改成从利息税费率计划中获取
							taxInfo = io.getInterestTaxByPlan( newResultInfo.getAccountID()
									, newResultInfo.getSubAccountID()
									//修改计算利息税费传入的利息值,取账户余额表中的利息
									//, newResultInfo.getInterest() );
									, loanInterestInfo.getInterest() );
		
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
		
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
		
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
		
						}
		
						//得到收息账户号与付息账户号
						InterestAccountIDInfo interestAccountIDInfo = null;
		
						interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
		
						newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
		
						newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
		
						return newResultInfo;
		
					}
				}
	
				//计提利息
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
					log.info("（interestSettlement 445）lhj debug settlement info >>>>>>>>>>>贷款账户计提利息-------");
	
					//执行贷款计提利息的计算，生成新的纪录返回
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getLoanAccountPredrawInterest(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, resultInfo.getAccountTypeID()
							, dtClearInterest);
	
					loanInterestInfo.setBalance(subInterestInfo.getBalance());
					loanInterestInfo.setDays(subInterestInfo.getDays());
					
					//2008年2月25日 结束日期应该取计提日期 Boxu Add
					//fixedInterestInfo.setEDate(subInterestInfo.getEDate());
					loanInterestInfo.setEDate(subInterestInfo.getPredrawDate());
	
					loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest()); // 子帐户表中的计提利息
	
					loanInterestInfo.setRate(subInterestInfo.getRate());
					loanInterestInfo.setSDate(subInterestInfo.getSDate());
	
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
	
					// 计提利息
					// 从表sett_SubAccount获得以前的AF_MPREDRAWINTEREST计提利息字段的值
					newResultInfo.setDrawingInterest(loanInterestInfo.getInterest());
	
					// 利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST); // 计提利息
	
					// 结息日
					newResultInfo.setCreateDate(dtClearInterest); // 页面录入结息日
	
					return newResultInfo;
	
				}
	
				else if (nInterestType == SETTConstant.InterestFeeType.COMMISION)
				{
					log.info("（interestSettlement 475）lhj debug settlement info >>>>>>>>>>>贷款账户手续费-------");
					Collection coll = null;
					log.info("-------------判断是否需要单户倒填---------");
					coll = transDetailDAO.findByIsBackward(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, nCurrencyID
							, nOfficeID
							, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------开始单户算息倒填---------");
								long flag = ib.accountInterestCalculationBackward(
										  resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("单户倒填失败");
								}
								log.info("-------------单户倒填结束---------");
							}
						}
					}
					loanInterestInfo = io.getLoanAccountFee(
							  nOfficeID
							, nCurrencyID
							, resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate
							, SETTConstant.InterestFeeType.COMMISION);
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// 手续费
					newResultInfo.setHandlingCharge(loanInterestInfo.getInterest());
					// 利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
					// 结息日
					newResultInfo.setCreateDate(dtClearInterest);
					log.info("-------------得到收息账户号与付息账户号---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					return newResultInfo;
				}
	
				else if (nInterestType == SETTConstant.InterestFeeType.ASSURE)
				{
					log.info("（interestSettlement 551）lhj debug settlement info >>>>>>>>贷款账户担保费-------");
					Collection coll = null;
					log.info("-------------判断是否需要单户倒填---------");
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------开始单户倒填---------");
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("单户倒填失败");
								}
								log.info("-------------单户倒填结束---------");
							}
						}
					}
					loanInterestInfo = io.getLoanAccountFee(nOfficeID, nCurrencyID, resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), dtClearInterest, dtSysdate, SETTConstant.InterestFeeType.ASSURE);
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// 担保费
					newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
					// 利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
	
					// 利息税费（担保费没有利息税费）
					/*
					 * InterestTaxInfo taxInfo = new InterestTaxInfo(); log.info("（interestSettlement 611）计算利息税费"); taxInfo =
					 * io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
					 * newResultInfo.getAssuranceCharge()); newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					 */
					// 结息日
					newResultInfo.setCreateDate(dtClearInterest);
					log.info("-------------得到收息账户号与付息账户号---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(),
							resultInfo.getSubAccountID(), nInterestType);
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					return newResultInfo;
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
					log.info("（interestSettlement 635）lhj debug info>>>>>贷款复利.....");
					Collection coll = null;
					log.info("-------------判断是否需要单户倒填---------");
					coll = transDetailDAO.findByIsBackward(
							resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, nCurrencyID
							, nOfficeID
							, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------开始单户算息倒填---------");
								long flag = ib.accountInterestCalculationBackward(
										resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("单户倒填失败");
								}
								log.info("-------------单户倒填结束---------");
							}
						}
					}
					
					//复利
					loanInterestInfo = io.getLoanAccountFee(
							nOfficeID
							, nCurrencyID
							, resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate
							, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					//Boxu Update 2008年11月10日 中国国电计算贷款逾期复利方法
//					loanInterestInfo = io.getLoanGuoDianAccountFee(
//							nOfficeID
//							, nCurrencyID
//							, resultInfo.getAccountID()
//							, resultInfo.getSubAccountID()
//							, dtClearInterest
//							, dtSysdate
//							, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// 复利
					newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
					// 利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMPOUNDINTEREST);
			        log.debug("---------判断账户类型------------");
			        AccountTypeInfo newAccountTypeInfo = null;
			        
			        try 
			        {
			        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
					} 
			        catch (SQLException e) 
					{
						e.printStackTrace();
					}
					
					if (newAccountTypeInfo != null) 
					{
						// 利息税费（只有委贷的有）
						if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							InterestTaxInfo taxInfo = new InterestTaxInfo();
		
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getCompoundInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
		
							// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
							taxInfo = io.getInterestTaxByPlan(
									newResultInfo.getAccountID()
									, newResultInfo.getSubAccountID()
									, newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						}
						// 结息日
						newResultInfo.setCreateDate(dtClearInterest);
						log.info("-------------得到收息账户号与付息账户号---------");
						InterestAccountIDInfo interestAccountIDInfo = null;
						interestAccountIDInfo = ib.getInterestAccountID(
								resultInfo.getAccountID()
								, resultInfo.getSubAccountID()
								, nInterestType);
						newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
						newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
		
						return newResultInfo;
					}
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
					log.info("（interestSettlement 721）lhj debug info>>>>>贷款罚息.....");
	
					Collection coll = null;
					log.info("-------------判断是否需要单户倒填---------");
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------开始单户算息倒填---------");
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("单户倒填失败");
								}
								log.info("-------------单户倒填结束---------");
							}
						}
					}
					
					//罚息
					loanInterestInfo = io.getLoanAccountFee(
							nOfficeID, 
							nCurrencyID, 
							resultInfo.getAccountID(), 
							resultInfo.getSubAccountID(), 
							dtClearInterest, 
							dtSysdate, 
							SETTConstant.InterestFeeType.FORFEITINTEREST);
					//Boxu Update 2008年11月10日 中国国电计算贷款逾期罚息方法
//					loanInterestInfo = io.getLoanGuoDianAccountFee(
//							nOfficeID, 
//							nCurrencyID, 
//							resultInfo.getAccountID(), 
//							resultInfo.getSubAccountID(), 
//							dtClearInterest, 
//							dtSysdate, 
//							SETTConstant.InterestFeeType.FORFEITINTEREST);
					
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// 逾期罚息
					newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
					// 利息类型
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);

			        log.debug("---------判断账户类型------------");
			        AccountTypeInfo newAccountTypeInfo = null;
			        try {
			        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (newAccountTypeInfo != null) {
						// 利息税费（只有委贷的有）
						if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							InterestTaxInfo taxInfo = new InterestTaxInfo();
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getForfeitInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
		
							// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
							taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
									newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						}
					}
					// 结息日
					newResultInfo.setCreateDate(dtClearInterest);
					log.info("-------------得到收息账户号与付息账户号---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(),
							resultInfo.getSubAccountID(), nInterestType);
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
					return newResultInfo;
				}

				log.info("lhj debug info ===结束贷款算息=======");
			}
		}
		
		return interestInfo;
	}

	private InterestQueryResultInfo getFixedInfo(InterestQueryResultInfo resultInfo,
			FixedDepositAccountPayableInterestInfo fixedInterestInfo)
	{

		// 生成新的纪录加入到Vector
		InterestQueryResultInfo newResultInfo = new InterestQueryResultInfo();
		newResultInfo.setAccountNo(resultInfo.getAccountNo());
		newResultInfo.setAccountTypeID(resultInfo.getAccountTypeID());
		newResultInfo.setAccountID(resultInfo.getAccountID());
		newResultInfo.setSubAccountID(resultInfo.getSubAccountID());
		newResultInfo.setFixedDepositNo(resultInfo.getFixedDepositNo());
		newResultInfo.setContractNo(resultInfo.getContractNo());
		newResultInfo.setPayFormNo(resultInfo.getPayFormNo());
		newResultInfo.setStartDate(fixedInterestInfo.getSDate());
		newResultInfo.setEndDate(fixedInterestInfo.getEDate());

		newResultInfo.setBalance(fixedInterestInfo.getBalance());

		newResultInfo.setInterest(fixedInterestInfo.getInterest());
		newResultInfo.setInterestRate(fixedInterestInfo.getRate());
		newResultInfo.setDays(fixedInterestInfo.getDays());
		return newResultInfo;
	}

	private InterestQueryResultInfo getLoanInfo(InterestQueryResultInfo resultInfo,
			LoanAccountInterestInfo loanInterestInfo)
	{

		InterestQueryResultInfo newResultInfo = new InterestQueryResultInfo();

		newResultInfo.setAccountNo(resultInfo.getAccountNo());
		newResultInfo.setAccountTypeID(resultInfo.getAccountTypeID());
		newResultInfo.setAccountID(resultInfo.getAccountID());
		newResultInfo.setSubAccountID(resultInfo.getSubAccountID());
		newResultInfo.setFixedDepositNo(resultInfo.getFixedDepositNo());
		newResultInfo.setContractNo(resultInfo.getContractNo());
		newResultInfo.setPayFormNo(resultInfo.getPayFormNo());

		// subaccount.AL_mPreDrawInterest AS loanPreDrawInterest, "); //贷款计提利息
		// 从表sett_subaccount获得
		newResultInfo.setLoanPreDrawInterest(resultInfo.getLoanPreDrawInterest()); // 贷款计提利息

		newResultInfo.setStartDate(loanInterestInfo.getSDate());
		newResultInfo.setEndDate(loanInterestInfo.getEDate());
		newResultInfo.setBalance(loanInterestInfo.getBalance());

		// 罚息
		// newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
		newResultInfo.setInterestRate(loanInterestInfo.getRate());
		newResultInfo.setDays(loanInterestInfo.getDays());

		return newResultInfo;

	}

	private int	INTEREST_CURRENT_WITHDRAW	= 0;

	private int	INTEREST_CURRENT_DEPOSIT	= 1;

	private int	INTEREST_LOAN_WITHDRAW		= 2;

	private int	INTEREST_LOAN_DEPOSIT		= 3;

	private int	INTEREST_MARGIN_DEPOSIT		= 4;

	/** 转换利息结算参数到账户操作参数 */
	private TransAccountDetailInfo transferInterestQueryResultInfoToTransAccountDetailInfo(InterestSettmentInfo isInfo,
			InterestQueryResultInfo info, String transNo, int transType)
	{

		TransAccountDetailInfo tadi = null;

		tadi = new TransAccountDetailInfo();
		tadi.setAbstractStr(isInfo.getAbstract());
		// tadi.setBankChequeNo(info.getb);
		// tadi.setBillTypeID(info.getBillTypeID());
		// tadi.setBillNo(info.getBillNo());
		tadi.setCurrencyID(isInfo.getCurrencyID());
		tadi.setDtExecute(isInfo.getExecuteDate());
		tadi.setDtInterestStart(info.getCreateDate());
		tadi.setOfficeID(isInfo.getOfficeID());
		tadi.setTransNo(transNo);
		// tadi.setTransactionTypeID(info.getTransactionTypeID());
		tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
		tadi.setGroup(-1);
		if (transType == INTEREST_CURRENT_WITHDRAW)
		{
			tadi.setTransAccountID(isInfo.getPayInterestAccountID());
			tadi.setOppAccountID(isInfo.getReceiveInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(isInfo.getInterest(), isInfo.getNegotiateInterest()), 2));
		}
		else if (transType == INTEREST_CURRENT_DEPOSIT)
		{
			tadi.setTransAccountID(isInfo.getReceiveInterestAccountID());
			tadi.setOppAccountID(isInfo.getPayInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(isInfo.getInterest(), isInfo.getNegotiateInterest()), 2));
		}
		else if (transType == INTEREST_MARGIN_DEPOSIT)
		{
			tadi.setTransAccountID(isInfo.getReceiveInterestAccountID());
			tadi.setOppAccountID(isInfo.getPayInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(isInfo.getInterest(), isInfo.getNegotiateInterest()), 2));
		}
		else if (transType == INTEREST_LOAN_DEPOSIT)
		{
			tadi.setTransAccountID(info.getRecieveInterestAccountID());
			tadi.setOppAccountID(info.getPayInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.sub(isInfo.getInterest(), isInfo.getInterestTax()), 2));
		}
		else if (transType == INTEREST_LOAN_WITHDRAW)
		{
			tadi.setTransAccountID(info.getPayInterestAccountID());
			tadi.setOppAccountID(info.getRecieveInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(isInfo.getInterest(), 2));
		}

		return tadi;
	}

	/**
	 * 批量结算。
	 * 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultVec 包含要计提的账户相关信息的实体，never null.
	 * @param isSave 是否保存
	 * @param settmentInfo 存放公用信息 是否记账，是否保存等
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Vector balanceInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo) throws Exception
	{

		Vector result = new Vector ();
		int nResult = 0; // 返回值
		long lsubReceiveInterestID = -1;
		long lsubPayInterestID = -1;
		String[] sTransNo = new String[resultVec.size()];
		//发送银行指令
		boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
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
			conInternal.setAutoCommit(false);
		}
		try
		{
			try
			{
				// 业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
				// 表明该方法是这个事务的一个组成部分。
				InterestBatch ib = new InterestBatch(conInternal);
				AccountOperation aco = new AccountOperation(conInternal);
				GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
				UtilOperation uo = new UtilOperation(conInternal);

				log.info("-------------开始结息---------");

				for (int i = 0; i < resultVec.size(); i++)
				{
					InterestQueryResultInfo info = new InterestQueryResultInfo();
					info = (InterestQueryResultInfo) resultVec.elementAt(i);
					
					/*取得正常未计提利息以及协定未计提利息
					 * */
					double unpredrawInterest = info.getInterest();
					double unpredrawNegotiateInterest = info.getNegotiateInterest();
                  
					try
						{
						/** 重新获取利息 **/
						//还原利息 = 未计提利息 + 计提利息
						//info.setInterest(UtilOperation.Arith.add(info.getInterest(), info.getDrawingInterest()));
						Sett_DailyAccountBalanceDAO dailyDAO = new Sett_DailyAccountBalanceDAO(conInternal);
						DailyAccountBalanceInfo balanceInfo = dailyDAO.findAllBySubAccountIDAndDate(
								  info.getSubAccountID()
								, settmentInfo.getOfficeID()
								, settmentInfo.getCurrencyID()
								, info.getEndDate());
					
						/* if(info.getBalance()<0){
							 throw new IException("计息余额不能为负值");
						 }*/
						/** 重新获取利息 **/
						if(balanceInfo!=null)
						{
							info.setInterest(balanceInfo.getInterest());
							info.setNegotiateInterest(balanceInfo.getAc_mNegotiateInterest());
						}
						
						//取结息日期 DTCLEARINTEREST
						Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
						SubAccountAssemblerInfo  AssemblerInfo =null;
						SubAccountCurrentInfo subAccInfo = null;
						SubAccountLoanInfo    subLoanInfo = null;
						AssemblerInfo = subAccountDAO.findByID(info.getSubAccountID());
						subAccInfo = AssemblerInfo.getSubAccountCurrenctInfo();
						subLoanInfo = AssemblerInfo.getSubAccountLoanInfo();
						if(info.getInterestType()==SETTConstant.InterestFeeType.INTEREST)
						{
							info.setStartDate(subAccInfo.getClearInterestDate());
						}
						else if(info.getInterestType()==SETTConstant.InterestFeeType.COMPOUNDINTEREST)
						{
							Timestamp time =uo.getStartCompoundinterestDay(info.getSubAccountID());
							if(time!=null&&!(subLoanInfo.getClearCompoundDate().before(time)&&settmentInfo.getExecuteDate().before(time)))
							{
								info.setStartDate(time);
							}
							else
							{
								info.setStartDate(subLoanInfo.getClearCompoundDate());
							}
						}
						else if(info.getInterestType()==SETTConstant.InterestFeeType.FORFEITINTEREST)
						{
							info.setStartDate(subLoanInfo.getClearOverDueDate());
						}
						else
						{
							info.setStartDate(subAccInfo.getClearInterestDate());
						}
						
						//重新计算时间天数
						InterestCalculation interestCalculation = new InterestCalculation();
						long intervalDays = interestCalculation.getIntervalDays(info.getStartDate(), interestCalculation.getNextNDay(info.getEndDate(), 1), InterestCalculation.INTERVALDAYSFLAG_FACTDAY);
						info.setDays(intervalDays);
						
				        log.debug("---------判断账户类型------------");
						long accountTypeID = info.getAccountTypeID();
				        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
				        AccountTypeInfo accountTypeInfo = null;
				        
				        try 
				        {
							accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
						} 
				        catch (SQLException e) 
				        {
							e.printStackTrace();
						}
				        
						if (accountTypeInfo != null)
						{
							//判断如果是贴现账户,定期账户,通知账户都不能"计息"
							if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT )
							{
								throw new IException(" 贴现账户["+NameRef.getAccountNameByID(info.getAccountID())+"]"+ info.getAccountNo() +"不能结息 ");
							}
							else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED )
							{
								throw new IException(" 定期账户["+NameRef.getAccountNameByID(info.getAccountID())+"]"+ info.getAccountNo() +"不能结息 ");
							}
							else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY )
							{
								throw new IException(" 通知账户["+NameRef.getAccountNameByID(info.getAccountID())+"]"+ info.getAccountNo() +"不能结息 ");
							}
							
							//是否活期账户组类型
							//是否其他存款账户组类型
							//是否保证金账户组类型
							if (  accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT 
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN )
							{
								SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
								preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
								
								if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
								{
									throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
								}
								
								//判断结息日和当前页面录入日期
								if (preDrawInterestInfo.getEDate().compareTo(settmentInfo.getInputClearInterest()) == 0)
								{
									throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
								}
								
								log.info("------------- 活期/保证金 利息开始结息---------");
		
								/**
								 * 保证金账户结息 注意：保证金账户在开户时是设置了“收息账户”为活期账户，所以利息应该存入该活期账户 如果找到收息账户，则结息；找不到则抛异常
								 */
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
								{
									long marginReceiveInterestCurrentAccountID = -1; // 保证金账户对应的收息活期账户
		
									log.info("----------结息保证金账户 --------- info.getAccountID() = " + info.getAccountID());
		
									//得到保证金收息账户
									Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		
									marginReceiveInterestCurrentAccountID = sett_SubAccountDAO.getReceiveInterestAccountByMarginAccountID(info.getAccountID());
		
									log.info("----------保证金账户对应的收息活期账户 --------- marginReceiveInterestCurrentAccountID = " + marginReceiveInterestCurrentAccountID);
		
									info.setRecieveInterestAccountID(marginReceiveInterestCurrentAccountID);
								}
		
								//利息类型
								if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
								{
									log.info("----------判断利息是否为0---------");
		
									//利息是0，不处理 修改为可以结负的利息
									if (UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()) != 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
										{
											log.info("-------------活期/协定账户结息开始---------");
											long flag = ib.clearCurrentDepositAccountInterest( info.getAccountID()
																							 , info.getSubAccountID()
																							 , info.getCreateDate()
																							 , info.getInterest()
																							 , info.getNegotiateInterest() );
		
											if (flag < 0)
											{
												throw new IException("活期/协定账户结息失败");
											}
		
											log.info("-------------活期/协定账户结息结束---------");
		
											//若保存，且结息日期 < 执行日期，则调用单户结息倒填处理
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------开始单户倒填---------");
		
												long flag1 = ib.accountInterestSettlelmentBackward( info.getAccountID()
																								  , info.getSubAccountID()
																								  , info.getCreateDate()
																								  , UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest())
																								  , settmentInfo.getOfficeID()
																								  , settmentInfo.getCurrencyID()
																								  , Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()) 
																								  ,SETTConstant.InterestFeeType.INTEREST);
		
												if (flag1 < 0)
												{
													throw new IException("单户倒填失败");
												}
												log.info("-------------开始单户结束---------");
											}
											// 保存结息记录
											log.info("-------------保存结息记录开始---------");
		
											/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);
											}
											else
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
											}
											//modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal ,settmentInfo, info);
											//解决当不选择生成会计分录，也需要生成银行指令 add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
		
											//修改活期结息的计息金额(从上个结息日到现在的平均正常余额) boxu 2007-8-18
											//Boxu Add 2008年2月28日 这里只有正常余额,应该添加协定余额
											if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT )
											{
												Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(con);
												//正常余额
												double sumInterestBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//协定余额
												double sumNormalBalance = dailyBalanceDAO.sumNegotiateBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//平均余额
												double averageNormalBalance = 0.0;
												
												if(sumInterestBalance == 0 && sumNormalBalance == 0)
												{
													averageNormalBalance = 0.0;
												}
												else if(intervalDays == 0)
												{
													averageNormalBalance = UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance);
												}
												else
												{
													//平均正常余额
													averageNormalBalance = UtilOperation.Arith.div(
															UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance)
															, intervalDays);
												}
												
												saveInfo.setBaseBalance( averageNormalBalance );
											}
											
											//保存结息记录
											long l = dao.add(conInternal, saveInfo);
		
											if (l < 0)
											{
												throw new IException("结息失败！！");
											}
		
											log.info("-------------保存结息记录结束---------");
											log.info("-------------保存结息记录记账开始............");
		
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)  // 记账
											{
												System.out.println("***************** 收息账户号 is " + info.getRecieveInterestAccountID());
												
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
												{
													if (info.getRecieveInterestAccountID() > 0)
													{
														log.info("-------------保证金 存入开始---------");
		
														// 活期存入
														// TransAccountDetailInfo
														// accountDetailInfo = new
														// TransAccountDetailInfo();
														// accountDetailInfo.setAmount(info.getInterest());
														// accountDetailInfo.setTransAccountID(info.getAccountID());
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(
																									  saveInfo
																									, info
																									, saveInfo.getTransNo()
																									, INTEREST_MARGIN_DEPOSIT );
														
														tadi.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);
														
														lsubReceiveInterestID = aco.depositCurrent(tadi, conInternal);
		
														if (lsubReceiveInterestID < 0)
														{
															throw new IException("保证金存入失败");
														}
														log.info("-------------保证金存入结束---------");
													}
		
													log.info("----保证金--------生成会计分录开始--------------");
		
													GenerateGLEntryParam param = new GenerateGLEntryParam();
		
													if (lsubReceiveInterestID < 0)
													{
														lsubReceiveInterestID = aco.getCurrentSubAccoutIDByAccoutID(saveInfo.getReceiveInterestAccountID());
													}
		
													// 修改收息方子账户，重新取值为保证金账户的子账户
													param.setReceiveAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(info.getNegotiateInterest(),2)), 2));
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2), UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));  //豁免利息/正常利息
													param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));  //实收利息/税后利息/协定利息
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													// param.setCheckUserID(settmentInfo.getc);
													// param.setPrincipalType(lPrincipalType);
													// param.setInterestType(lInterestType);
													// param.setCommisionType(lCommissionType);
													// param.setEntryType(lEntryType);
		
													//计提利息，可空
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//未计提利息，可空
													param.setUnPreDrawInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] =param.getTransNo();
		
													log.info("------保证金------生成会计分录结束--------------");
												}
												else
												{
													if (info.getRecieveInterestAccountID() > 0)
													{
														log.info("-------------活期存入开始---------");
														// 活期存入
														// TransAccountDetailInfo
														// accountDetailInfo = new
														// TransAccountDetailInfo();
														// accountDetailInfo.setAmount(info.getInterest());
														// accountDetailInfo.setTransAccountID(info.getAccountID());
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_CURRENT_DEPOSIT);
														
														tadi.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
														
														lsubReceiveInterestID = aco.depositCurrent(tadi, conInternal);
														if (lsubReceiveInterestID < 0)
														{
															throw new IException("活期存入失败！");
														}
														log.info("-------------活期存入结束---------");
													}
													log.info("----活期-------生成会计分录开始--------------");
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													if (lsubReceiveInterestID < 0)
													{
														lsubReceiveInterestID = aco.getCurrentSubAccoutIDByAccoutID(saveInfo.getReceiveInterestAccountID());
													}
													
													log.info("----活期-------lsubReceiveInterestID--------------" + lsubReceiveInterestID);
													param.setReceiveAccountID(lsubReceiveInterestID);
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()), 2));
													
													//利息合计
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2),UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													System.out.println("===param.setTotalInterest()=== "+param.getTotalInterest());
													//豁免利息/正常利息
													//param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));
													
													//实收利息/税后利息/协定利息
													//param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
													
													/*modifyed by bingliu  20120604 
													 * 未计提利息要区分正常利息和协定利息
													 * 重新取页面上传过来的协定利息和正常利息，此两个利息都是减掉了计提的部分。
													 * */
													//豁免利息/正常利息
													 if(subAccInfo.getIsNegotiate()!=SETTConstant.BooleanValue.ISTRUE)
														{
															param.setRemissionInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(
																	UtilOperation.Arith.round(param.getTotalInterest(),2),
																	UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)),2));
														}
														//只有为协定存款的时候走下面的代码
														else{
														param.setRemissionInterest(UtilOperation.Arith.round(unpredrawInterest, 2));
														System.out.println("===param.setRemissionInterest()=== "+param.getRemissionInterest());
														
														//实收利息/税后利息/协定利息
														/*//可空(总利息减去计提利息，再减去正常未计提利息)*/
														param.setReallyReceiveInterest(UtilOperation.Arith.round(
																						UtilOperation.Arith.sub(
																								UtilOperation.Arith.sub(
																										UtilOperation.Arith.round(param.getTotalInterest(),2),
																										UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2))
																											, UtilOperation.Arith.round(param.getRemissionInterest(),2)), 2));
													    }

													System.out.println("===param.getReallyReceiveInterest()=== "+param.getReallyReceiveInterest());
													
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													// param.setCheckUserID(settmentInfo.getc);
													// param.setPrincipalType(lPrincipalType);
													// param.setInterestType(lInterestType);
													// param.setCommisionType(lCommissionType);
													// param.setEntryType(lEntryType);
													
													//计提利息，可空
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													System.out.println("===param.setPreDrawInterest()=== "+param.getPreDrawInterest());
													
													/*//未计提利息，可空(正常利息加上协定利息减去计提利息获得)*/
													param.setUnPreDrawInterest(UtilOperation.Arith.round(
																					UtilOperation.Arith.sub(
																							UtilOperation.Arith.add(
																									UtilOperation.Arith.round(info.getInterest(),2),
																									UtilOperation.Arith.round(info.getNegotiateInterest(),2))
																										, UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
													
													log.info("------活期------生成会计分录结束--------------");
												}
		
											}
										}
										log.info("-------------活期/保证金利息结息结束---------");
									}
								}
							}
							
							//是否备付金账户组类型
							//是否准备金账户组类型
							//是否拆借账户组类型
							if (  accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK 
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							{
								SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
								preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
								
								if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
								{
									throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
								}
								
								//判断结息日和当前页面录入日期
								if (preDrawInterestInfo.getEDate().compareTo(settmentInfo.getInputClearInterest()) == 0)
								{
									throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
								}
								
								log.info("------------- 利息开始结息---------");
		
		
								//利息类型
								if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
								{
									log.info("----------判断利息是否为0---------");
		
									//利息是0，不处理 修改为可以结负的利息
									if (UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()) != 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
										{
											log.info("-------------备付金、准备金账户结息开始---------");
											long flag = ib.clearCurrentDepositAccountInterest( info.getAccountID()
																							 , info.getSubAccountID()
																							 , info.getCreateDate()
																							 , info.getInterest()
																							 , info.getNegotiateInterest() );
		
											if (flag < 0)
											{
												throw new IException("备付金、准备金账户结息失败");
											}
		
											log.info("-------------备付金、准备金定账户结息结束---------");
		
											//若保存，且结息日期 < 执行日期，则调用单户结息倒填处理
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------开始单户倒填---------");
		
												long flag1 = ib.accountInterestSettlelmentBackward( info.getAccountID()
																								  , info.getSubAccountID()
																								  , info.getCreateDate()
																								  , UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest())
																								  , settmentInfo.getOfficeID()
																								  , settmentInfo.getCurrencyID()
																								  , Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()) 
																								  ,SETTConstant.InterestFeeType.INTEREST);
		
												if (flag1 < 0)
												{
													throw new IException("单户倒填失败");
												}
												log.info("-------------开始单户结束---------");
											}
											// 保存结息记录
											log.info("-------------保存结息记录开始---------");
		
											/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_INTEREST);
											}
											//modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal ,settmentInfo, info);
											//解决当不选择生成会计分录，也需要生成银行指令 add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
		
											//修改活期结息的计息金额(从上个结息日到现在的平均正常余额) boxu 2007-8-18
											//Boxu Add 2008年2月28日 这里只有正常余额,应该添加协定余额
											if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT )
											{
												Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(con);
												//正常余额
												double sumInterestBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//协定余额
												double sumNormalBalance = dailyBalanceDAO.sumNegotiateBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//平均余额
												double averageNormalBalance = 0.0;
												
												if(sumInterestBalance == 0 && sumNormalBalance == 0)
												{
													averageNormalBalance = 0.0;
												}
												else if(intervalDays == 0)
												{
													averageNormalBalance = UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance);
												}
												else
												{
													//平均正常余额
													averageNormalBalance = UtilOperation.Arith.div(
															UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance)
															, intervalDays);
												}
												
												saveInfo.setBaseBalance( averageNormalBalance );
											}
											
											//保存结息记录
											long l = dao.add(conInternal, saveInfo);
		
											if (l < 0)
											{
												throw new IException("结息失败！！");
											}
		
											log.info("-------------保存结息记录结束---------");
											log.info("-------------保存结息记录记账开始............");
		
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)  // 记账
											{
												
												
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
												{ 
													System.out.println("***************** 付息账户号 is " + info.getPayInterestAccountID());
													if (info.getPayInterestAccountID() > 0)
													{//对拆借账户来说是付息账户
														
														log.info("-------------拆借付息账户支取开始---------");
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_CURRENT_WITHDRAW);
														
														tadi.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_INTEREST);
														//结息使用，不扣减累计未复核金额
														tadi.setCommonOperation(false);
														lsubPayInterestID = aco.withdrawCurrent(tadi, conInternal);
														log.info("----拆借付息子账户-------lsubPayInterestID--------------" + lsubPayInterestID);
														if (lsubPayInterestID < 0)
														{
															throw new IException("拆借付息账户支取失败！");
														}
														log.info("-------------拆借付息账户支取结束---------");
													}
													log.info("----拆借账户结息-------生成会计分录开始--------------");
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													param.setReceiveAccountID(info.getSubAccountID());
													param.setPayInterestAccountID(lsubPayInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()), 2));
													
													//利息合计
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2),UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													
													//豁免利息/正常利息
													param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));
													
													//实收利息/税后利息/协定利息
													param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
													
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_INTEREST);
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													
													//计提利息，可空
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//未计提利息，可空(正常利息减去计提利息获得)
													param.setUnPreDrawInterest(UtilOperation.Arith.round(
																					UtilOperation.Arith.sub(
																									UtilOperation.Arith.round(info.getInterest(),2),
																										UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
													
													log.info("------拆借------生成会计分录结束--------------");
												
												}
												else
												{
													System.out.println("***************** 收息账户号 is " + info.getRecieveInterestAccountID());
													if (info.getRecieveInterestAccountID() > 0)
													{
														log.info("-------------备付金、准备金存入开始---------");
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_CURRENT_DEPOSIT);
														
														if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
														{
															tadi.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST);
														}
														else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
														{
															tadi.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_INTEREST);
														}
														
														lsubReceiveInterestID = aco.depositCurrent(tadi, conInternal);
														log.info("----备付金、准备金收息子账户-------lsubReceiveInterestID--------------" + lsubReceiveInterestID);
														if (lsubReceiveInterestID < 0)
														{
															throw new IException("备付金、准备金存入失败！");
														}
														log.info("-------------备付金、准备金存入结束---------");
													}
													log.info("----备付金、准备金结息-------生成会计分录开始--------------");
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													param.setReceiveAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()), 2));
													
													//利息合计
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2),UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													
													//豁免利息/正常利息
													param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));
													
													//实收利息/税后利息/协定利息
													param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
													
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_INTEREST);
													}
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													
													//计提利息，可空
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//未计提利息，可空(正常利息减去计提利息获得)
													param.setUnPreDrawInterest(UtilOperation.Arith.round(
																					UtilOperation.Arith.sub(
																									UtilOperation.Arith.round(info.getInterest(),2),
																										UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
													
													log.info("------备付金、准备金------生成会计分录结束--------------");
												}
		
											}
										}
										log.info("-------------利息结息结束---------");
									}
								}
							}
							
							//1.信托贷款 2.委托贷款 3.其他贷款 4.银团贷款
							if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
								 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
								preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
								//查询可用余额
								double availableBalance = aco.findAvailableBalance(info.getPayInterestAccountID(),settmentInfo.getOfficeID(), settmentInfo.getCurrencyID());
								log.info("-------------贷款开始结息---------");
								//利息
								if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
								{
									if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									{
										throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
									}
									
									//判断结息日和当前页面录入日期
									if (preDrawInterestInfo.getEDate().compareTo(settmentInfo.getInputClearInterest()) == 0)
									{
										throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
									}
									
									log.info("----------判断利息是否大于0------------");
									if (info.getInterest() > 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE)  //保存
										{
											log.info("-------------贷款利息开始结息---------");
											
											//取贷款计提利息
											SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
											preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
											
											log.info("-------------贷款结算利息开始---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											
											//应付计提利息
											clearLoanInfo.setInterestReceivable(preDrawInfo.getPredrawInterest());
											
											
											//应付利息
											clearLoanInfo.setInterest(info.getInterest());
											
											
											if(availableBalance>0&&availableBalance<info.getInterest()&&settmentInfo.isClearPartInterest())
											{
												//实付利息
												clearLoanInfo.setRealInterest(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												//实付利息
												clearLoanInfo.setRealInterest(UtilOperation.Arith.round(info.getInterest(), 2));
											}
											if(availableBalance>0&&availableBalance<preDrawInfo.getPredrawInterest()&&settmentInfo.isClearPartInterest())
											{
												//实付计提利息
												clearLoanInfo.setRealInterestReceivable(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												//实付计提利息
												clearLoanInfo.setRealInterestReceivable(UtilOperation.Arith.round(preDrawInfo.getPredrawInterest(), 2));
											}
											
											//应付利息
											//clearLoanInfo.setInterest(info.getInterest() + clearLoanInfo.getRealInterestReceivable() );
											
											//实付利息
											//clearLoanInfo.setRealInterest(UtilOperation.Arith.round(info.getInterest() + clearLoanInfo.getRealInterestReceivable(), 2) );
											info.setRealInterest(clearLoanInfo.getRealInterest());
											clearLoanInfo.setClearInterest(true);
											//若保存，且结息日期<执行日期，则调用单户结息倒填处理
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------开始单户倒填---------");
												long flag =-1;
												if(info.getInterestType()==SETTConstant.InterestFeeType.INTEREST)
												{
													 flag = ib.accountInterestSettlelmentBackward( info.getAccountID()
															, info.getSubAccountID()
															, info.getCreateDate()
															, UtilOperation.Arith.add(info.getRealInterest(), info.getNegotiateInterest())
															, settmentInfo.getOfficeID()
															, settmentInfo.getCurrencyID()
															, Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())
															,SETTConstant.InterestFeeType.INTEREST);
												}
												if (flag < 0)
												{
													throw new IException("单户倒填失败");
												}
												log.info("-------------单户倒填结束---------");
											}
											long flagClear = ib.clearLoanAccountInterest(clearLoanInfo);
											
											if (flagClear < 0)
											{
												throw new IException("贷款利息结息失败");
											}
											
											log.info("-------------贷款结算利息结束---------");
		
											log.info("----------保存开始---------");
											//保存结息记录
											
											/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)
											{
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
												}
												else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
												}
												else
												{
													settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
												}
											}
											else
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
											}
											//modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//解决当不选择生成会计分录，也需要生成银行指令 add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("结息保存失败！！");
											}
											log.info("----------保存结束---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
											{
												//委托贷款
												log.info("----------判断委托贷款是否有收付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(委托贷款)没有付息账户");
													}
													if (info.getRecieveInterestAccountID() <= 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(委托贷款)没有收息账户");
													}
												}
												log.info("----------判断自营贷款是否有付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(自营贷款)没有付息账户");
													}
												}
												
												//WZC ADD
												log.info("----------判断银团贷款是否有付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) 
												{
													if (info.getPayInterestAccountID() <= 0) 
													{
														throw new IException("账户" + info.getAccountNo() + "(银团贷款)没有付息账户");
													}
												}
												
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------活期支取开始---------");
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													
													//WZC ADD
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													
													else
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													
													//利息调用
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("活期支取失败！");
													}
													log.info("-------------活期支取结束---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------活期存入开始---------");
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													
													//WZC ADD
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													
													else
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
		
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("活期存入失败！");
													}
													log.info("-------------活期存入结束---------");
												}
		
												log.info("------------生成贷款会计分录开始--------------");
		
												// 查找贷款计提利息
												// 前面已经取过了，而且结清了，重新再取的话为0
												/*
												 * SubAccountPredrawInterestInfo preDrawInfo = new
												 * SubAccountPredrawInterestInfo(); preDrawInfo =
												 * uo.getPredrawInterestBySubAccountIDAndAccountType( info.getSubAccountID(),
												 * info.getAccountTypeID());
												 */
												
												//modify by zcwang 2007-6-23 银团贷款和普通贷款会计分录不同
												if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													BankLoanQuery bankloanquery = new BankLoanQuery();
													ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
													
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													param.setReceiveAccountID(info.getSubAccountID());
													param.setPayAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPayInterestAccountID(lsubPayInterestID);
													
													//本金/交易金额
													param.setPrincipalOrTransAmount(saveInfo.getInterest());
													
													param.setTotalInterest(saveInfo.getInterest());
													
													//计提利息，可空
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//未计提利息，可空
													param.setUnPreDrawInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(saveInfo.getInterest(),2),UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)),2 ));
													
													param.setOfficeID(saveInfo.getOfficeID());
													param.setCurrencyID(saveInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													param.setExecuteDate(saveInfo.getExecuteDate());
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													param.setInterestTaxFee(info.getInterestTaxCharge());
													param.setReallyReceiveInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(info.getInterestTaxCharge(),2)), 2));
													param.setList(list);
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
		
													log.info("------------生成银团贷款会计分录结束--------------");
												}
												else
												{
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													
													param.setReceiveAccountID(info.getSubAccountID());
													param.setPayAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPayInterestAccountID(lsubPayInterestID);
													
													//本金/交易金额
													param.setPrincipalOrTransAmount(saveInfo.getInterest());
													
													//利息合计，可空
													param.setTotalInterest(saveInfo.getInterest());
													
													//计提利息，可空
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//未计提利息，可空
													param.setUnPreDrawInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(saveInfo.getInterest(),2), UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													param.setOfficeID(saveInfo.getOfficeID());
													param.setCurrencyID(saveInfo.getCurrencyID());
													
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													
													param.setExecuteDate(saveInfo.getExecuteDate());
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													param.setInterestTaxFee(info.getInterestTaxCharge());
													
													//实收利息/税后利息，可空
													//利息税费  info.getInterestTaxCharge()
													param.setReallyReceiveInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(info.getInterestTaxCharge(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													
													//sTransNo[i] = param.getTransNo();
													
													log.info("------------生成贷款会计分录结束--------------");
												}
											}
											else
											{
												log.info("--------生成红字冲销会计分录开始---------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												//param.setRemissionInterest(saveInfo.getInterest());
												
												param.setPreDrawInterest((-1) * preDrawInfo.getPredrawInterest());
												param.setPrincipalOrTransAmount((-1) * preDrawInfo.getPredrawInterest());
												param.setTotalInterest((-1) * preDrawInfo.getPredrawInterest());
												
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
												param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),settmentInfo.getCurrencyID()));
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
		
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												log.info("--------生成红字冲销会计分录结束---------");
											}
		
										}
										/*
										 * else { InterestSettmentInfo saveInfo = getSaveInfo(settmentInfo, info);
										 * Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO(); long l =
										 * dao.add(conInternal, saveInfo); if (l < 0) { throw new IException("结息保存失败！"); } }
										 */
										log.info("-------------贷款利息结息结束---------");
		
									}
								}
								
								//担保费
								if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("账户：" + info.getAccountNo() + " 账户名称："
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
									//}
									
									log.info("----------判断担保费是否大于0-----------");
									if (info.getAssuranceCharge() > 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
										{
											log.info("-------------贷款结算担保费开始---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setSuretyFee(info.getAssuranceCharge());
											clearLoanInfo.setRealSuretyFee(UtilOperation.Arith.round(info.getAssuranceCharge(),
													2));
		
											long flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("贷款担保费结息失败");
											}
											log.info("-------------贷款结算担保费结束---------");
											log.info("-------------保存开始--------");
											//保存结息记录
											
											/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
											settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
	                                        //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//解决当不选择生成会计分录，也需要生成银行指令 add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("结息保存失败！！");
											}
											log.info("-------------保存结束---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
											{
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------活期支取开始---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
													withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
													//利息调用
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("活期支取失败！");
													}
													log.info("-------------活期支取结束---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------活期存入开始---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("活期存入失败！");
													}
													log.info("-------------活期存入结束---------");
												}
												log.info("------------生成担保费会计分录开始--------------");
												
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												// param.setReceiveInterestAccountID(lsubReceiveInterestID);
												// param.setPayInterestAccountID(lsubPayInterestID);
												param.setPaySuertyFeeAccountID(lsubPayInterestID);
												param.setReceieveSuertyFeeAccountID(lsubReceiveInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setSuretyFee(saveInfo.getInterest());
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												
												log.info("------------生成担保费会计分录结束--------------");
											}
										}
										/*
										 * else { InterestSettmentInfo saveInfo = getSaveInfo(settmentInfo, info);
										 * Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO(); long l =
										 * dao.add(conInternal, saveInfo); if (l < 0) { throw new IException("结息保存失败！"); } }
										 */
										log.info("-------------贷款担保费结息结束---------");
		
									}
								}
		
								//手续费
								if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("账户：" + info.getAccountNo() + " 账户名称："
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
									//}
									
									log.info("----------判断手续费是否>0----------------");
									if (info.getHandlingCharge() > 0)
									{
										log.info("-------------贷款手续费开始结息---------");
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
										{
											log.info("-------------贷款结算手续费开始---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setCommision(info.getHandlingCharge());
											clearLoanInfo.setRealCommission(UtilOperation.Arith.round(info.getHandlingCharge(),
													2));
		
											long flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("手续费结息失败");
											}
											log.info("-------------贷款结算手续费结束---------");
											log.info("-------------保存开始---------");
											// 保存结息记录
											
											/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
											settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
	                                        //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//解决当不选择生成会计分录，也需要生成银行指令 add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("结息保存失败！！");
											}
											log.info("-------------保存结束---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
											{
												// 委托贷款
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() < 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(委托贷款)没有付手续费账户");
													}
												}
												
												/*
												//银团贷款
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) {
													if (info.getPayInterestAccountID() < 0) {
														throw new IException("账户"
																+ info.getAccountNo()
																+ "(银团贷款)没有付手续费账户");
													}
												}
												*/
												
												if (info.getPayInterestAccountID() > 0)
												{
													if (info.getPayInterestAccountID() > 0)
													{
														log.info("-------------活期支取开始---------");
		
														// accountDetailInfo.setAmount(info.getInterest());
														// accountDetailInfo.setTransAccountID(info.getAccountID());
														TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
		
														//withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
														
														//委托贷款
														if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
														{
															withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
														}
														/*
														//银团贷款
														else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
															{
																withdrawTadi
																	.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_COMMISION_FEE);
															}
														*/
														
														// 利息调用
														withdrawTadi.setCommonOperation(false);
														lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
														if (lsubPayInterestID < 0)
														{
															throw new IException("活期支取失败！");
														}
														log.info("-------------活期支取结束---------");
													}
												}
												log.info("------------生成手续费会计分录开始--------------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												param.setReceiveInterestAccountID(lsubReceiveInterestID);
												//param.setPayInterestAccountID(lsubPayInterestID);
												param.setPayCommissionAccountID(lsubPayInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setCommissionFee(saveInfo.getInterest());
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												
												//param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
												
												//委托贷款
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
												}
												/*
													else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_COMMISION_FEE);
													}
												*/
												
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												
												log.info("------------生成手续费会计分录结束--------------");
											}
										}
										log.info("-------------贷款手续费结息结束---------");
									}
								}
		
								//复利
								if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("账户：" + info.getAccountNo() + " 账户名称："
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
									//}
									
									log.info("-----------判断复利是否大于0------------------");
									if (info.getCompoundInterest() > 0)
									{
										log.info("-------------贷款复利开始结息---------");
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
										{
											log.info("-------------贷款结算复利开始---------");
											long flag = -1;
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setCompoundInterest(info.getCompoundInterest());
											
											if(availableBalance>0&&availableBalance<info.getCompoundInterest()&&settmentInfo.isClearPartInterest())
											{
												clearLoanInfo.setRealCompoundInterest(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												clearLoanInfo.setRealCompoundInterest(UtilOperation.Arith.round(info.getCompoundInterest(), 2));	
											}
											info.setRealInterest(clearLoanInfo.getRealCompoundInterest());
											clearLoanInfo.setClearInterest(true);
											//若保存，且结息日期<执行日期，则调用单户结息倒填处理
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------开始单户倒填---------");
												if(info.getInterestType()==SETTConstant.InterestFeeType.COMPOUNDINTEREST)
												{
													 flag = ib.accountInterestSettlelmentBackward( info.getAccountID()
															, info.getSubAccountID()
															, info.getCreateDate()
															, info.getRealInterest()
															, settmentInfo.getOfficeID()
															, settmentInfo.getCurrencyID()
															, Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())
															,SETTConstant.InterestFeeType.COMPOUNDINTEREST);
												}
												if (flag < 0)
												{
													throw new IException("单户倒填失败");
												}
												log.info("-------------单户倒填结束---------");
											}
											flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("复利结息失败");
											}
											log.info("-------------贷款结算复利结束---------");
											log.info("-------------保存开始---------");
											//保存结息记录
											
											/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
											}
											else 
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
											}
	                                        //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)										
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//解决当不选择生成会计分录，也需要生成银行指令 add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("结息保存失败！！");
											}
											log.info("-------------保存结束---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
											{
												//委托贷款
												log.info("----------判断委托贷款是否有收付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() < 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(委托贷款)没有付息账户");
													}
													if (info.getRecieveInterestAccountID() < 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(委托贷款)没有收息账户");
													}
												}
												log.info("----------判断自营贷款是否有付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(自营贷款)没有付息账户");
													}
												}
		
												log.info("----------判断银团贷款是否有付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) 
												{
													if (info.getPayInterestAccountID() <= 0) 
													{
														throw new IException("账户" + info.getAccountNo() + "(银团贷款)没有付息账户");
													}
												}
												
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------活期支取开始---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
		
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else 
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													//利息调用
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("活期支取失败！");
													}
													log.info("-------------活期支取结束---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------活期存入开始---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("活期存入失败！");
													}
													log.info("-------------活期存入结束---------");
												}
		
												log.info("------------生成复利会计分录开始--------------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(saveInfo.getSubAccountID());
												param.setPayAccountID(saveInfo.getSubAccountID());
												param.setReceiveInterestAccountID(lsubReceiveInterestID);
												param.setPayInterestAccountID(lsubPayInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setCompoundInterest(saveInfo.getInterest());
												param.setReallyReceiveInterest(UtilOperation.Arith.round(saveInfo.getInterest(),2) - UtilOperation.Arith.round(saveInfo.getInterestTax(),2));
												param.setOfficeID(saveInfo.getOfficeID());
												param.setCurrencyID(saveInfo.getCurrencyID());
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
												}
												else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													BankLoanQuery bankloanquery = new BankLoanQuery();
													ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
													param.setList(list);
												}
												else
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
												}
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												log.info("------------生成复利会计分录结束--------------");
											}
										}
										log.info("-------------贷款复利结息结束---------");
		
									}
								}
		
								//罚息
								if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("账户：" + info.getAccountNo() + " 账户名称："
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经结息");
									//}
		
									log.info("---------判断罚息是否>0------------------");
									if (info.getForfeitInterest() > 0)
									{
										log.info("-------------贷款罚息开始结息---------");
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
										{
											log.info("-------------贷款结算罚息开始---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setOverDueInterest(info.getForfeitInterest());
											clearLoanInfo.setRealOverDueInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));
		
											if(availableBalance>0&&availableBalance<info.getForfeitInterest()&&settmentInfo.isClearPartInterest())
											{
												clearLoanInfo.setRealOverDueInterest(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												clearLoanInfo.setRealOverDueInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));	
											}
											info.setRealInterest(clearLoanInfo.getRealOverDueInterest());
											clearLoanInfo.setClearInterest(true);
											
											long flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("罚息结息失败");
											}
											log.info("-------------贷款结算罚息结束---------");
											log.info("-------------保存开始---------");
											// 保存结息记录
											
											/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
											}
											else
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
											}
	                                       //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//解决当不选择生成会计分录，也需要生成银行指令 add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("结息保存失败！！");
											}
											log.info("-------------保存结束---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
											{
												// 委托贷款
												log.info("----------判断委托贷款是否有收付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() < 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(委托贷款)没有付息账户");
													}
													if (info.getRecieveInterestAccountID() < 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(委托贷款)没有收息账户");
													}
												}
												log.info("----------判断自营贷款是否有付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(自营贷款)没有付息账户");
													}
												}
												log.info("----------判断银团贷款是否有付息账户------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("账户" + info.getAccountNo() + "(银团贷款)没有付息账户");
													}
												}
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------活期支取开始---------");
		
													// accountDetailInfo.setAmount(info.getInterest());
													// accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(
															saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													
													// 利息调用
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("活期支取失败！");
													}
													log.info("-------------活期支取结束---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------活期存入开始---------");
		
													// accountDetailInfo.setAmount(info.getInterest());
													// accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(
															saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("活期存入失败！");
													}
													log.info("-------------活期存入结束---------");
												}
												log.info("------------生成罚息会计分录开始--------------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												param.setReceiveInterestAccountID(lsubReceiveInterestID);
												param.setPayInterestAccountID(lsubPayInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setOverFee(saveInfo.getInterest());
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												param.setReallyReceiveInterest(UtilOperation.Arith.round(saveInfo.getInterest(),2) - UtilOperation.Arith.round(saveInfo.getInterestTax(),2));
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
												}
												else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													BankLoanQuery bankloanquery = new BankLoanQuery();
													ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
													param.setList(list);
												}
												else
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
												}
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												log.info("------------生成罚息会计分录结束--------------");
											}
										}
										log.info("-------------贷款罚息结息结束---------");
									}
								}
							}
						}
						if(bIsValid)
						{
							Log.print("*******************开始产生银行活期利息指令，并保存**************************");

							// 构造参数
							Collection coll = new ArrayList();
								try
								{
									CreateInstructionParam instructionParam = new CreateInstructionParam();
									
									if (accountTypeInfo != null) 
									{
										/**
										 * 无法获得委存户的账户类型
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT)
										{
											// 委存户不发送指令
											continue;
										}
										*/
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT)//活期
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);// 活期
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)//保证金
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);// 保证金
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)//自营
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);// 自营
										}
										//WZC ADD
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)//银团
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);// 银团
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)//委贷
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);// 委贷
										}
										
										instructionParam.setObjInfo((InterestQueryResultInfo) resultVec.elementAt(i));
										instructionParam.setOfficeID(settmentInfo.getOfficeID());
										instructionParam.setCurrencyID(settmentInfo.getCurrencyID());
										instructionParam.setCheckUserID(settmentInfo.getInputUserID());
										instructionParam.setTransNo(sTransNo[i]);
										//instructionParam.setBankType(((InterestQueryResultInfo)resultVec.elementAt(i)).bankID);
										instructionParam.setInputUserID(settmentInfo.getInputUserID());
										coll.add(instructionParam);
										log.debug("------生成银行活期利息指令结束--------" + sTransNo[i]);
									}
								}
								catch (Exception e)
								{
									log.print("----------------------------发送银行指令出现异常:sTransNo=" + sTransNo[i] + "----------------");
									log.print("----------------------------继续发送其他指令!----------------");
									throw e;
								}
							// 生成银行指令并保存
							IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
							bankInstruction.createSpecialBankInstruction(coll);
						
						}
						
						info.setSuccess(true);
						result.addElement(info);
						conInternal.commit();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					//modify by kevin(刘连凯)2011-10-20
					log.error("账户"+info.getAccountNo()+"结息发生异常，原因："+IExceptionMessage.getExceptionMSG(e)) ;
					info.setSuccess(false);
				info.setStrPromptMessage(IExceptionMessage.getExceptionMSG(e));//提示信息						
					result.addElement(info);
					conInternal.rollback();					
				}
				}
				
				log.info("-------------结息结束---------");

				// 如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
				if (con == null)
				{
					try
					{
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
				// 执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
				// 抛出用以通知事务的组织者。
				e.printStackTrace();
				if (con == null)
				{
					try
					{
						log.info("****连接回滚******");
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

		}
		catch (Exception eRollback)
		{
			//2009年2月20日 Boxu 抛异常有问题
			//throw new IException(eRollback.getMessage());
			throw new IException(eRollback.getMessage(), eRollback);
		}
		finally
		{
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (con == null)
			{
				try
				{
					log.info("****关闭连接******");
					conInternal.close();
				}
				catch (Exception eClose)
				{
					throw new IException("关闭连接失败！");
				}
			}
		}

		return result;
	}

	private InterestSettmentInfo getSaveInfo(InterestSettmentInfo settmentInfo, InterestQueryResultInfo info) throws IException, Exception
	{
		// 结息表
		InterestSettmentInfo saveInfo = new InterestSettmentInfo();

		// 交易号
		UtilOperation uo = new UtilOperation();

		String strTransNo = "";
		try
		{
			strTransNo = uo.getNewTransactionNo(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID(),settmentInfo.getTransactionTypeID());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("没有取到交易号！");
		}

		saveInfo.setTransNo(strTransNo);
		// 公共信息
		saveInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
		saveInfo.setCurrencyID(settmentInfo.getCurrencyID());
		saveInfo.setOfficeID(settmentInfo.getOfficeID());
		// saveInfo.setTransactionTypeID(settmentInfo.getTransactionTypeID());
		saveInfo.setInputUserID(settmentInfo.getInputUserID());
		saveInfo.setAbstract(settmentInfo.getAbstract());
		saveInfo.setCheckAbstract(settmentInfo.getCheckAbstract());
		saveInfo.setIsSave(settmentInfo.getIsSave());
		saveInfo.setIsKeepAccount(settmentInfo.getIsKeepAccount());
		saveInfo.setIsSuccess(settmentInfo.getIsSuccess());
		saveInfo.setFaultReason(settmentInfo.getFaultReason());
		saveInfo.setTransInterestFeeID(settmentInfo.getTransInterestFeeID());
		// saveInfo.setExecuteDate(
		// Env.getSystemDate(settmentInfo.getOfficeID(),
		// settmentInfo.getCurrencyID()));
		saveInfo.setExecuteDate(settmentInfo.getExecuteDate());

		// 差异信息
		saveInfo.setAccountID(info.getAccountID());
		saveInfo.setAccountTypeID(info.getAccountTypeID());
		saveInfo.setSubAccountID(info.getSubAccountID());
		saveInfo.setInterestSettlementDate(info.getCreateDate());
		saveInfo.setInterestStartDate(info.getStartDate());
		saveInfo.setInterestEndDate(info.getEndDate());
		saveInfo.setInterestDays(info.getDays());
		saveInfo.setBaseBalance(info.getBalance());
		saveInfo.setRate(info.getInterestRate());
		saveInfo.setInterestType(info.getInterestType());

		// 活期相关
		saveInfo.setNegotiateBalance(info.getNegotiateBalance());
		saveInfo.setNegotiateInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
		saveInfo.setNegotiateRate(info.getNegotiateInterestRate());
		saveInfo.setNegotiatePecisionInterest(info.getNegotiateInterest());

		// 利息税费
		saveInfo.setInterestTax(info.getInterestTaxCharge());
		saveInfo.setInterestTaxRate(info.getInterestTaxRate());

		// 计提利息没有收付息账户号
		// 计提利息
		if (settmentInfo.getOperationType() != SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			// 收付息账户号
			saveInfo.setReceiveInterestAccountID(info.getRecieveInterestAccountID());
			saveInfo.setPayInterestAccountID(info.getPayInterestAccountID());
		}

		// 特殊处理
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.INTEREST)
			{
				throw new IException("选中账户不能计提！");
			}
		}

		// 冲销计提利息
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{
				throw new IException("选中账户不能冲销计提！");
			}
		}

		// 担保费
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getAssuranceCharge(), 2));
			saveInfo.setPecisionInterest(info.getAssuranceCharge());
		}

		// 手续费
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getHandlingCharge(), 2));
			saveInfo.setPecisionInterest(info.getHandlingCharge());
		}

		// 复利
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getCompoundInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getCompoundInterest());
		}

		// 罚息
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getForfeitInterest());
		}

		// 利息
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getInterest());
		}

		// 计提利息
		// 利息类型
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
		{
			// info.getDrawingInterest() 存放计提利息

			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getDrawingInterest(), 2));
			saveInfo.setPecisionInterest(info.getDrawingInterest());
		}

		// 交易类型
		// 根据“账户类型”、“利息费用计算的费用类型”和“利息费用结算的操作类型”获得利息交易类型
		saveInfo.setTransactionTypeID(SETTConstant.TransactionType.getTransactionType(saveInfo.getAccountTypeID(), saveInfo.getInterestType(), saveInfo.getOperationType()));

		saveInfo.setAbstract(getAbstract(saveInfo, info));

		return saveInfo;
	}
     
	private InterestSettmentInfo getSaveInfo(Connection con,InterestSettmentInfo settmentInfo, InterestQueryResultInfo info) throws IException, Exception
	{
		// 结息表
		InterestSettmentInfo saveInfo = new InterestSettmentInfo();

		// 交易号
		UtilOperation uo = new UtilOperation();

		String strTransNo = "";
		try
		{
			strTransNo = uo.getNewTransactionNo(con,settmentInfo.getOfficeID(), settmentInfo.getCurrencyID(),settmentInfo.getTransactionTypeID());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("没有取到交易号！");
		}

		saveInfo.setTransNo(strTransNo);
		// 公共信息
		saveInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
		saveInfo.setCurrencyID(settmentInfo.getCurrencyID());
		saveInfo.setOfficeID(settmentInfo.getOfficeID());
		// saveInfo.setTransactionTypeID(settmentInfo.getTransactionTypeID());
		saveInfo.setInputUserID(settmentInfo.getInputUserID());
		saveInfo.setAbstract(settmentInfo.getAbstract());
		saveInfo.setCheckAbstract(settmentInfo.getCheckAbstract());
		saveInfo.setIsSave(settmentInfo.getIsSave());
		saveInfo.setIsKeepAccount(settmentInfo.getIsKeepAccount());
		saveInfo.setIsSuccess(settmentInfo.getIsSuccess());
		saveInfo.setFaultReason(settmentInfo.getFaultReason());
		saveInfo.setTransInterestFeeID(settmentInfo.getTransInterestFeeID());
		// saveInfo.setExecuteDate(
		// Env.getSystemDate(settmentInfo.getOfficeID(),
		// settmentInfo.getCurrencyID()));
		saveInfo.setExecuteDate(settmentInfo.getExecuteDate());

		// 差异信息
		saveInfo.setAccountID(info.getAccountID());
		saveInfo.setAccountTypeID(info.getAccountTypeID());
		saveInfo.setSubAccountID(info.getSubAccountID());
		saveInfo.setInterestSettlementDate(info.getCreateDate());
		saveInfo.setInterestStartDate(info.getStartDate());
		saveInfo.setInterestEndDate(info.getEndDate());
		saveInfo.setInterestDays(info.getDays());
		saveInfo.setBaseBalance(info.getBalance());
		saveInfo.setRate(info.getInterestRate());
		saveInfo.setInterestType(info.getInterestType());

		// 活期相关
		saveInfo.setNegotiateBalance(info.getNegotiateBalance());
		saveInfo.setNegotiateInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
		saveInfo.setNegotiateRate(info.getNegotiateInterestRate());
		saveInfo.setNegotiatePecisionInterest(info.getNegotiateInterest());

		// 利息税费
		saveInfo.setInterestTax(info.getInterestTaxCharge());
		saveInfo.setInterestTaxRate(info.getInterestTaxRate());

		// 计提利息没有收付息账户号
		// 计提利息
		if (settmentInfo.getOperationType() != SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			// 收付息账户号
			saveInfo.setReceiveInterestAccountID(info.getRecieveInterestAccountID());
			saveInfo.setPayInterestAccountID(info.getPayInterestAccountID());
		}

		// 特殊处理
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.INTEREST)
			{
				throw new IException("选中账户不能计提！");
			}
		}

		// 冲销计提利息
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{
				throw new IException("选中账户不能冲销计提！");
			}
		}

		// 担保费
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getAssuranceCharge(), 2));
			saveInfo.setPecisionInterest(info.getAssuranceCharge());
		}

		// 手续费
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getHandlingCharge(), 2));
			saveInfo.setPecisionInterest(info.getHandlingCharge());
		}

		// 复利
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getCompoundInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getCompoundInterest());
		}

		// 罚息
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getForfeitInterest());
		}

		// 利息
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getInterest());
		}

		// 计提利息
		// 利息类型
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
		{
			// info.getDrawingInterest() 存放计提利息

			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getDrawingInterest(), 2));
			saveInfo.setPecisionInterest(info.getDrawingInterest());
		}

		// 交易类型
		// 根据“账户类型”、“利息费用计算的费用类型”和“利息费用结算的操作类型”获得利息交易类型
		saveInfo.setTransactionTypeID(SETTConstant.TransactionType.getTransactionType(saveInfo.getAccountTypeID(), saveInfo.getInterestType(), saveInfo.getOperationType()));

		saveInfo.setAbstract(getAbstract(saveInfo, info));

		return saveInfo;
	}
	/*
	 * 得到摘要
	 */
	private String getAbstract(InterestSettmentInfo info, InterestQueryResultInfo qRInfo)
	{

		String strAbstract = "";
        log.debug("---------判断账户类型------------");
		long accountTypeID = info.getAccountTypeID();
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        
        try 
        {
			accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
		} 
        catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		if (accountTypeInfo != null) 
		{
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || 
				 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "活期存款结息-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "活期存款计提-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "活期存款冲销计提-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK )
				{
					if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
					{
						strAbstract = "备付金账户结息-" + DataFormat.formatDate(info.getExecuteDate());
						return strAbstract;
					}
					if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						strAbstract = "备付金账户计提-" + DataFormat.formatDate(info.getExecuteDate());
						return strAbstract;
					}
					if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						strAbstract = "备付金账户冲销计提-" + DataFormat.formatDate(info.getExecuteDate());
						return strAbstract;
					}
				}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "准备金账户结息-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "准备金账户计提-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "准备金账户冲销计提-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "拆借账户结息-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "拆借账户计提-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "拆借账户冲销计提-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "定期存款计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "定期存款冲销计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "通知存款计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "通知存款冲销计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
			}
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "保证金存款结息-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "保证金计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "保证金冲销计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
			}
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
			{
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
				{
					if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
					{
						strAbstract = "委托贷款结担保费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
					{
						strAbstract = "委托贷款结手续费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
						{
							strAbstract = "委托贷款结息-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
						if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
						{
							strAbstract = "委托贷款计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
					{
						strAbstract = "委托贷款结复利-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
					{
						strAbstract = "委托贷款结罚息" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
						{
							strAbstract = "委托贷款冲销计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
				}
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
				{
					if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
					{
						strAbstract = "自营贷款结担保费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
					{
						strAbstract = "自营贷款结手续费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
						{
							strAbstract = "自营贷款结息-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
						if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
						{
							strAbstract = "自营贷款计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
					{
						strAbstract = "自营贷款结复利-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
					{
						strAbstract = "自营贷款结罚息" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
						{
							strAbstract = "自营贷款冲销计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
				}
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
				{
					if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
					{
						strAbstract = "银团贷款结担保费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
					{
						strAbstract = "银团贷款结手续费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
						{
							strAbstract = "银团贷款结息-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
						if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
						{
							strAbstract = "银团贷款计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
					{
						strAbstract = "银团贷款结复利-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
					{
						strAbstract = "银团贷款结罚息" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
						{
							strAbstract = "银团贷款冲销计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
				}
			}
			
			//新增贴现计提摘要
			if(accountTypeInfo.getAccountGroupID()==SETTConstant.AccountGroupType.DISCOUNT)
			{
				if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
				{
					strAbstract = "贴现贷款结担保费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
				{
					strAbstract = "贴现贷款结手续费-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
				{
					if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
					{
						strAbstract = "贴现贷款结息-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						strAbstract = "贴现贷款计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
					strAbstract = "贴现贷款结复利-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
					strAbstract = "贴现贷款结罚息" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
					if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						strAbstract = "贴现贷款冲销计提-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
				}
			}
		}

		return strAbstract;
	}

	/**
	 * 批量反结息(删除)。
	 * 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultInfo 包含要计提的账户相关信息的实体，never null.
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	 */
	public int delInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo) throws Exception
	{

		log.info("-------------进入反结息模块---------");
		int nResult = 0; // 返回值
		long subAccountStatus = -1;

		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
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
			conInternal.setAutoCommit(false);
		}

		try
		{
			// 业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			// 表明该方法是这个事务的一个组成部分。
			AccountOperation aco = new AccountOperation(conInternal);
			InterestBatch ib = new InterestBatch(conInternal);
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO(conInternal);
			Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
			UtilOperation uo = new UtilOperation(conInternal);

			log.info("-------------开始删除---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestSettmentInfo infoResult = new InterestSettmentInfo();
				infoResult = (InterestSettmentInfo) resultVec.elementAt(i);

				InterestSettmentInfo info = new InterestSettmentInfo();

				info = dao.findByID(conInternal, infoResult.getID());
				
				//检查子账户状态
				subAccountStatus = subAccountDao.findStatusByID(info.getSubAccountID());
				if(subAccountStatus == SETTConstant.SubAccountStatus.FINISH)
				{
					throw new IException("账户状态为"+SETTConstant.SubAccountStatus.getName(subAccountStatus)+", 不能进行删除操作");
				}
				System.out.println("执行日："+info.getExecuteDate());
				System.out.println("开机日："+Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
				if ( info.getExecuteDate().after( Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()) )
				  || Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getExecuteDate()) )
				{
					throw new IException("只能删除系统开机日当天的记录");
				}

				//效验删除顺序,必须按操作顺序进行删除,首先删除上一次操作
				InterestSettmentInfo interestInfo = new InterestSettmentInfo();
				interestInfo = dao.findOperation(conInternal, info);
				if(interestInfo.getTransNo().length()>0 && interestInfo.getOperationType()!=-1)
				{
					throw new IException("请先删除交易号为 "+interestInfo.getTransNo()+" 的 "+SETTConstant.InterestOperateType.getName(interestInfo.getOperationType())+" 操作");
				}
					
				// InterestAccountIDInfo interestAccountIDInfo = null;
				// interestAccountIDInfo =
				// ib.getInterestAccountID(info.getAccountID(),info.getSubAccountID(),info.getInterestType());
				log.debug(UtilOperation.dataentityToString(info));

		        log.debug("---------判断账户类型------------");
				long accountTypeID = info.getAccountTypeID();
		        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
		        AccountTypeInfo accountTypeInfo = null;
		        
		        try 
		        {
					accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
				} 
		        catch (SQLException e) 
		        {
					e.printStackTrace();
				}
		        
				if (accountTypeInfo != null) 
				{
					// 记账之后有活期存入与支取
					if (info.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)
					{
						// 如果付息账户号大于0
						if (info.getPayInterestAccountID() > 0)
						{
							log.info("-------------活期取消支取开始------");
	
							// 是否活期账户组类型
							// 是否其他存款账户组类型
							TransAccountDetailInfo accountDetailInfo = new TransAccountDetailInfo();
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
							{
								accountDetailInfo.setAmount(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()));
							}
	
							// 是否贷款账户组类型
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								accountDetailInfo.setAmount(info.getInterest());
							}
	
							accountDetailInfo.setTransAccountID(info.getPayInterestAccountID());
							// 利息使用
							accountDetailInfo.setCommonOperation(false);
							aco.cancelWithdrawCurrent(accountDetailInfo, conInternal);
	
							log.info("-------------活期取消支取结束---------");
						}
	
						// 如果收息账户号大于0
						if (info.getReceiveInterestAccountID() > 0)
						{
							log.info("-------------活期取消存入开始---------");
							// 活期存入
							TransAccountDetailInfo accountDetailInfo = new TransAccountDetailInfo();
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
								||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
								||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
									)
							{
								accountDetailInfo.setAmount(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()));
							}
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								accountDetailInfo.setAmount(UtilOperation.Arith.sub(info.getInterest(), info.getInterestTax()));
							}
	
							accountDetailInfo.setTransAccountID(info.getReceiveInterestAccountID());
							aco.cancelDepositCurrent(accountDetailInfo, conInternal);
	
							log.info("-------------活期取消存入结束---------");
						}
					}
	
					// 单户结息倒填
					if (info.getExecuteDate().after(info.getInterestSettlementDate()))
					{
						if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
						{
							log.info("-------------删除时单户倒填开始------------");
	
							ib.accountInterestSettlelmentBackward(info.getAccountID(), info.getSubAccountID(), info
									.getInterestSettlementDate(), (-1)
									* (UtilOperation.Arith.add(info.getInterest(), info.getNegotiatePecisionInterest())), info
									.getOfficeID(), info.getCurrencyID(), info.getExecuteDate(),SETTConstant.InterestFeeType.INTEREST);
	
							log.info("-------------删除时单户倒填结束------------");
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
						{
							log.info("-------------删除时单户倒填开始------------");
	
							ib.accountInterestSettlelmentBackward(info.getAccountID(), info.getSubAccountID(), info
									.getInterestSettlementDate(), (-1)
									*info.getInterest(), info
									.getOfficeID(), info.getCurrencyID(), info.getExecuteDate(),SETTConstant.InterestFeeType.COMPOUNDINTEREST);
	
							log.info("-------------删除时单户倒填结束------------");
						}
					}
	
					// 添加一个保证金类型 add by wjliu --2007-4-9
					// 是否活期账户组类型
					// 是否保证金账户组类型
					// 添加备付金、准备金、拆借账户组，操作同活期
					if ( (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
							|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING
					   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) 
					   && info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT )
					{
						log.info("------------活期反结息开始----------");
	
						ib.clearCurrentDepositAccountInterestReverse(
								  info.getAccountID()
								, info.getSubAccountID()
								, info.getInterestStartDate()
								, info.getPecisionInterest()
								, info.getNegotiatePecisionInterest() );
	
						log.info("------------活期反结息结束----------");
					}
					
					//是否贷款账户组类型
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) && 
						info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
					{
						log.info("------------贷款反结息开始----------");
	
						ClearLoanAccountInterestConditionInfo loanInfo = new ClearLoanAccountInterestConditionInfo();
						loanInfo.setAccountID(info.getAccountID());
	
						if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
						{
							loanInfo.setInterest(info.getPecisionInterest());
							loanInfo.setRealInterest(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
						{
							loanInfo.setCommision(info.getPecisionInterest());
							loanInfo.setRealCommission(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
						{
							loanInfo.setCompoundInterest(info.getPecisionInterest());
							loanInfo.setRealCompoundInterest(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
						{
							loanInfo.setOverDueInterest(info.getPecisionInterest());
							loanInfo.setRealOverDueInterest(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
						{
							loanInfo.setSuretyFee(info.getPecisionInterest());
							loanInfo.setRealSuretyFee(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						{
							loanInfo.setInterestReceivable(info.getPecisionInterest());
							loanInfo.setRealInterestReceivable(info.getInterest());
						}
	
						loanInfo.setInterestDate(info.getInterestStartDate());
						loanInfo.setClearInterestDate(info.getInterestSettlementDate());
	
						loanInfo.setSubAccountID(info.getSubAccountID());
	
						long lFlag = ib.clearLoanAccountInterestReverse(loanInfo);
	
						if (lFlag < 0)
						{
							throw new IException("贷款账户反结息失败");
						}
	
						log.info("------------贷款反结息结束----------");
					}
					
					// 贷款计提利息删除
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) && 
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------贷款反计提开始----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, preDrawInfo.getPredrawInterest() - UtilOperation.Arith.round( info.getInterest(), 2)
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------贷款反计提结束----------");
					}
					//贷款冲销计提删除
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) && 
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------贷款反冲销计提开始----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, -info.getInterest()
								
								, info.getInterestEndDate()
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								
								
								, info.getAccountTypeID());
	
						log.info("------------贷款反冲销计提结束----------");
					}
					// 是否定期账户组类型
					// 是否通知账户组类型
					// 是否保证金账户组类型
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) &&
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------定期反计提开始----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, preDrawInfo.getPredrawInterest() - UtilOperation.Arith.round( info.getInterest(), 2)
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------定期反计提结束----------");
					}
					// 是否定期账户组类型
					// 是否通知账户组类型
					// 是否保证金账户组类型
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) &&
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------定期反冲销计提开始----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, info.getInterest()
								
								, info.getInterestEndDate()
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								
								, info.getAccountTypeID());
	
						log.info("------------定期反冲销计提结束----------");
					}
	
					// 活期反计提
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							&& 
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------活期反计提开始----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								
								//, preDrawInfo.getPredrawInterest() - info.getInterest()
								, UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
										UtilOperation.Arith.add(
												UtilOperation.Arith.round( info.getInterest(), 2), UtilOperation.Arith.round( info.getNegotiateInterest(), 2)
												)
										)
								
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------活期反计提结束----------");
					}
	
					// 活期反冲销
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							&&
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------活期反冲销计提开始----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								
								//2008年2月29日 Add Boxu 加上协定利息
								//, UtilOperation.Arith.add(preDrawInfo.getPredrawInterest(), info.getInterest())
								//, UtilOperation.Arith.add(preDrawInfo.getPredrawInterest(),
								//		UtilOperation.Arith.add(
								//				info.getInterest(), info.getNegotiateInterest()
								//				)
								//		)
								, info.getInterest()
								
								//时间应该取
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								, info.getInterestEndDate()
								
								, info.getAccountTypeID());
	
						log.info("------------活期反冲销计提结束----------");
					}
	
					// 贴现反计提
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT &&
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------贴现反计提开始----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, preDrawInfo.getPredrawInterest() - UtilOperation.Arith.round( info.getInterest(), 2)
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------贴现反计提结束----------");
					}
	
					// 贴现反冲销
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT &&
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------贴现反冲销计提开始----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
						
						// 修改子账户信息
						log.info("-------------修改子账户信息---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, -info.getInterest()
								
								, info.getInterestEndDate()
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								
								, info.getAccountTypeID());
	
						log.info("------------贴现反冲销计提结束----------");
					}
	
					log.info("-----------删除明细账开始--------");
					aco.deleteTransAccountDetail(info.getTransNo(), conInternal);
					log.info("-----------删除明细账结束--------");
	
					log.info("-----------删除会计分录开始--------");
					glo.deleteGLEntry(info.getTransNo(), conInternal);
					log.info("-----------删除会计分录结束--------");
	
					log.info("-----------删除结息记录开始--------");
					long l = dao.updateStatus(conInternal, info.getID(), SETTConstant.TransactionStatus.DELETED);
	
					if (l < 0)
					{
						throw new IException("删除结息记录失败");
					}
	
					// 删除成功更改利息费用设置详细信息的执行状态
					if (info.getTransInterestFeeID() > -1)
					{
						Sett_InterestFeeSettingDetailDAO settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conInternal);
	
						settingDetailDAO.updateIsSuccessStatus(info.getTransInterestFeeID(), SETTConstant.BooleanValue.ISFALSE);
					}
	
					log.info("-----------删除结息记录结束--------");
	
				}
			}
			log.info("-------------删除结束---------");
			// 进行结息操作。
			// result = dao.selectSettlementRecords(conInternal, queryInfo);
			// 如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
			if (con == null)
			{
				try
				{
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
			// 执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			// 抛出用以通知事务的组织者
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					log.info("----------事务回滚------------");
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
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
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

		return nResult;
	}

	/**
	 * 批量计提利息。 计提只针对利息,复利和罚息不能作计提。 首先，假设账户组中的每个账户号都允许进行计提（在前面的业务中进行过判断）。 其次，对账户组中的每个自营贷款账户，进行计提操作并记录计提是否成功的标志。
	 * 再次，对账户组中的每个定期存款账户，直接进行计提操作。
	 * 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultInfo 包含要计提的账户相关信息的实体，never null.
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	 */
	public int drawingInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo) throws Exception
	{

		int nResult = 0; // 返回值

		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
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
			conInternal.setAutoCommit(false);
		}

		try
		{
			// InterestCalculation interestCalculation = new
			// InterestCalculation();

			// 业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			// 表明该方法是这个事务的一个组成部分。
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			UtilOperation uo = new UtilOperation(conInternal);
			Sett_TransInterestSettlementDAO interestDao = new Sett_TransInterestSettlementDAO();
			Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO(conInternal);

			log.info("-------------开始计提---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestQueryResultInfo info = new InterestQueryResultInfo();

				info = (InterestQueryResultInfo) resultVec.elementAt(i);
				//计提时实付利息等于应付利息
				info.setRealInterest(info.getInterest());
		        log.debug("---------判断账户类型------------");
				long accountTypeID = info.getAccountTypeID();
		        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
		        AccountTypeInfo accountTypeInfo = null;
		        
		        try 
		        {
					accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
				} 
		        catch (SQLException e) 
		        {
					e.printStackTrace();
				}
		        
				if (accountTypeInfo != null) 
				{
					/**
					 * 说明 是否贷款账户组类型 包含帐户类型为: 1.自营贷款
					 * 2.委托贷款 3.贴现贷款 4.其他贷款
					 */
					// 委托贷款账户组类型可以计提
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) &&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST // 利息
					)
					{
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						log.info("---------------判断利息是否等于0-----------------");
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// 取sett_SubAccount表的计提利息字段
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称："
										+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经计提");
							}
							else
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 利息为空不能计提");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							// 自营贷款只允许计提一次
							log.info("---------------判断利息是否大于0-----------------");
							/**
							 * 需要修改的说明: 贷款可以多次计提 计提条件限制一天内只允许计提一次 preDrawInfo1.getPredrawDate() 计提日期 info.getCreateDate()
							 * 页面录入"结息日"
							 */

							// 保存结息记录
							log.info("----------保存开始---------");
	
							// info.getInterestType() ==
							// SETTConstant.InterestFeeType.INTEREST //利息
							
							/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
							settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
							{
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
							}
                            //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)

							InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);

							// 计提利息
							// 利息费用计算的费用类型
							saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
							// 自营贷款计提应收利息（含冲销）
							saveInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
							{
								// 贴现贷款计提应收利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								// 委托贷款计提应收利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								// 银团贷款计提应收利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
							}
	
							log.debug(UtilOperation.dataentityToString(saveInfo));
	
							// 保存进入结息表sett_TransInterestSettlement
							long l = interestDao.add(conInternal, saveInfo);
	
							if (l < 0)
							{
								throw new IException("结息保存失败");
							}
	
							log.info("----------保存结束---------");
							log.info("-------------贷款利息计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								// AL_MPREDRAWINTEREST sett_SubAccount表 计提利息
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
	
								// 修改子账户信息
								log.info("-------------修改子账户信息---------");
	
								// 修改sett_SubAccount表中的计提利息 AF_mPreDrawInterest
								// 更改计提利息与计提日期
								// preDrawInfo.getPredrawInterest()
								// 表sett_SubAccount中的字段AL_MPREDRAWINTEREST计提利息
								// saveInfo.getInterest()
								// 表sett_DailyAccountBalance中的字段MINTEREST累计利息/累计正常利息
								subAccountDao.updatePreDrawInterestAndDate( info.getSubAccountID()
										, UtilOperation.Arith.round(UtilOperation.Arith.add(saveInfo.getInterest()
										, preDrawInfo.getPredrawInterest()), 2)
										, info.getCreateDate(), info.getAccountTypeID() );
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("--------生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
	
									// 计提利息，可空
									param.setPreDrawInterest(saveInfo.getInterest());
	
									// 本金/交易金额
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									// 利息合计，可空
									param.setTotalInterest(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// 自营贷款计提应收利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
									{
										// 贴现贷款计提应收利息（含冲销）
										param.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
									{
										// 委托贷款计提应收利息（含冲销）
										param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										// 银团贷款计提应收利息（含冲销）
										param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
										
										BankLoanQuery bankloanquery = new BankLoanQuery();
										ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
										param.setList(list);
									}
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
	
							log.info("-------------贷款利息计提结束---------");
						}
					}
					// 是否定期账户组类型
					// 是否通知账户组类型
					else if (( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ) 
							&& info.getInterestType() == SETTConstant.InterestFeeType.INTEREST )
					{
						log.info("---------判断利息是否大于0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// 取sett_SubAccount表的计提利息字段
							if (preDrawInterestInfo.getPredrawInterest() > 0 && preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经计提");
							}
							else
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 利息为空不能计提");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------定期利息计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
	
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// 利息类型为: 计提利息
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								// 定期存款计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									// 通知存款计提应付利息（含冲销）
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("结息保存失败");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 修改子账户信息
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
	
									log.info("--------生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
	
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									param.setTotalInterest(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// 定期存款计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
									{
										// 通知存款计提应付利息（含冲销）
										param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
	
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									param.setInputUserID(saveInfo.getInputUserID());
									param.setCheckUserID(saveInfo.getInputUserID());
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
							log.info("-------------定期利息计提结束---------");
						}
					}
	
					// 是否保证金账户组类型
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN &&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						log.info("---------判断利息是否大于0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(),
								info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// 取sett_SubAccount表的计提利息字段
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称："
										+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经计提");
							}
							else
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 利息为空不能计提");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------保证金利息计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
	
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// 计提利息
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								// 保证金存款计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("保证金结息保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 修改子账户信息
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("-----保证金---生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setTotalInterest(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// 保证金存款计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
							log.info("------------保证金利息计提结束---------");
						}
					}
					
					// 是否备付金、准备金账户组类型
					else if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK 
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)&&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						log.info("---------判断利息是否大于0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(),
								info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// 取sett_SubAccount表的计提利息字段
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称："
										+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经计提");
							}
							else
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 利息为空不能计提");
							}
						}
						else //if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------备付金、准备金利息计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
	
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// 计提利息
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								//根据账户组类型设置交易类型
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("备付金、准备金计提保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 修改子账户信息
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("-----备付金、准备金计提---生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setTotalInterest(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									//根据账户组类型设置交易类型
									if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
									}
									else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
							log.info("------------保证金利息计提结束---------");
						}
					}
					
					// 是否拆借账户组类型
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING &&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						log.info("---------判断利息是否大于0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(),
								info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// 取sett_SubAccount表的计提利息字段
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称："
										+ NameRef.getAccountNameByID(info.getAccountID()) + " 已经计提");
							}
							else
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 利息为空不能计提");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------拆借利息计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
	
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// 计提利息
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								// 保证金存款计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("拆借计提保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 修改子账户信息
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("-----拆借计提---生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setTotalInterest(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// 拆借计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
							log.info("------------保证金利息计提结束---------");
						}
					}
	
					/**
					 * 加入活期计提 是否活期账户组类型
					 */
					else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
						   && info.getInterestType() == SETTConstant.InterestFeeType.INTEREST )
					{
						log.info("---------判断利息是否大于0-------------");
	
						/**
						 * 取消东电
						 */
						//info.setInterest(info.getDrawingInterest());
						//计提交易记录开始日期取计提开始日期
						//info.setStartDate(info.getDrawStartDate());
						//info.setDays(interestCalculation.getIntervalDays(
						//info.getStartDate(),
						//interestCalculation.getNextNDay(info.getEndDate(),1), 1)
						//);
						//天数
						//long days = getIntervalDays(sDate, getNextNDay(eDate, 1),
						//INTERVALDAYSFLAG_FACTDAY);
						
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						if (UtilOperation.Arith.round(UtilOperation.Arith.add(
									UtilOperation.Arith.round(info.getInterest(),2),
									UtilOperation.Arith.round(info.getNegotiateInterest(),2)
								),2) == 0)
						{
							//取sett_SubAccount表的计提利息字段
							if (preDrawInterestInfo.getPredrawInterest() > 0 && preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 已经计提");
							}
							else
							{
								throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 利息为空不能计提");
							}
						}
						/*if (UtilOperation.Arith.round(UtilOperation.Arith.add(
								UtilOperation.Arith.round(info.getInterest(),2),
								UtilOperation.Arith.round(info.getNegotiateInterest(),2)
							),2) > 0)*/
						else
						{
							log.info("-------------活期利息计提开始---------");
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								//保存结息记录
								log.info("----------保存开始---------");
								
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								//活期存款计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("结息保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
	
								//修改子帐户信息
								subAccountDao.updatePreDrawInterestAndDate(
										  info.getSubAccountID()
										
										/** Boxu Add 2008年2月28日 计提利息=活期利息+协定利息 **/
										//, UtilOperation.Arith.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2)
										, UtilOperation.Arith.round(
												UtilOperation.Arith.add(
														UtilOperation.Arith.add(UtilOperation.Arith.round(saveInfo.getInterest(), 2), preDrawInfo.getPredrawInterest())
														, UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2))
												, 2)
										  
										, info.getCreateDate()  //页面录入结息日
	
										//修改东电的去掉这个时间
										//, DataFormat.getNextDate(info.getEndDate())
	
										, info.getAccountTypeID() );
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记帐
								{
									log.info("--------生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									
									/** 计提利息=活期利息+协定利息 **/
									param.setPreDrawInterest(UtilOperation.Arith.add(UtilOperation.Arith.round(saveInfo.getInterest(), 2),UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2) ));
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
											UtilOperation.Arith.add(
													UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest())
													, saveInfo.getNegotiateInterest())
											, 2));
									param.setTotalInterest(UtilOperation.Arith.round(
												UtilOperation.Arith.add(
													UtilOperation.Arith.add(UtilOperation.Arith.round(saveInfo.getInterest(), 2), preDrawInfo.getPredrawInterest())
													, UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2))
												, 2));
									
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// 活期存款计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
									//modify by kevin(刘连凯)2012-04-01
									//国药项目要求把‘协定利息’单独对账
									param.setRemissionInterest(UtilOperation.Arith.round(saveInfo.getInterest(), 2));
									param.setReallyReceiveInterest(UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2));
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
							log.info("-------------定期利息计提结束---------");
						}
					}
					else
					{
						throw new IException("账户："
								+ info.getAccountNo()
								// + "放款单:"
								+ (info.getPayFormNo() != null && info.getPayFormNo().length() > 0 ? " 放款单："
										+ info.getPayFormNo() : "") + " 账户类型："
								+ SETTConstant.AccountType.getName(info.getAccountTypeID()) + " 无法计提");
					}
				}
			}

			log.info("-------------计提结束---------");

			// 如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
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
			// 执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			// 抛出用以通知事务的组织者。
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					log.info("*****回滚******");
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
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
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

		return nResult;
	}

	/**
	 * 批量计提利息(工行-联通项目)。 计提针对活期存款账户。 1、判断结息日与当前日期是否相同，如果相同进行如下操作。 2、查询所有的要计提利息的活期账户。 3、计提利息。
	 * 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultInfo 包含要计提的账户相关信息的实体，never null.
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	 */
	public int drawingInterestForICBC(Connection con, long lOfficeID, long lCurrencyID, Timestamp dtCreateDate)
			throws Exception
	{

		int nResult = 0; // 返回值
		Vector resultVec = null;
		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
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
			conInternal.setAutoCommit(false);
		}

		try
		{
			// 业务逻辑，比如：调用其它的业务方法和数据访问方法，一定要将 conInternal 作为参数传给要被调用的方法，
			// 表明该方法是这个事务的一个组成部分。
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			UtilOperation uo = new UtilOperation(conInternal);
			Sett_TransInterestSettlementDAO interestDao = new Sett_TransInterestSettlementDAO();
			Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO(conInternal);

			Sett_CompoundInterestSettingDAO compoundinterestsettingDAO = new Sett_CompoundInterestSettingDAO(
					conInternal);
			long lIsSettlement = compoundinterestsettingDAO.findByCompoundInterestDate(lOfficeID, lCurrencyID,
					dtCreateDate);

			// 如果开机日是结息日，进行利息计提处理
			if (lIsSettlement == 1)
			{
				// 查询所有要计提的活期利息
				resultVec = (Vector) subAccountDao.findAllInterestForICBC(lOfficeID, lCurrencyID);

				log.info("-------------工行联通项目--开始计提/结息---------");
				for (int i = 0; i < resultVec.size(); i++)
				{
					InterestQueryResultInfo info = new InterestQueryResultInfo();
					info = (InterestQueryResultInfo) resultVec.elementAt(i);

			        log.debug("---------判断账户类型------------");
					long accountTypeID = info.getAccountTypeID();
			        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
			        AccountTypeInfo accountTypeInfo = null;
			        try {
						accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (accountTypeInfo != null) {
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
						{
							if (info.getInterest() <= 0)
							{
								log.info("-------------贷款利息计提开始---------");
								// 修改子账户信息
								subAccountDao.updatePreDrawInterestAndDateForICBC(info.getSubAccountID(), info
										.getInterest(), dtCreateDate, info.getAccountTypeID());
								log.info("-------------贷款利息计提结束---------");
							}
							else
							{
								log.info("-------------存款利息结息开始---------");
								// 增加账户余额
	
								// 生成结息记录
								this.drawDepositInterestForICBC(conInternal, lOfficeID, lCurrencyID, info.getAccountID(),
										info.getSubAccountID(), info.getInterest(), dtCreateDate);
								// 修改子账户信息
								subAccountDao.updateDepositInterestForICBC(info.getSubAccountID(), dtCreateDate, info
										.getAccountTypeID());
								log.info("-------------存款利息结息结束---------");
							}
						}
						else
						{
							throw new IException("账户:" + info.getAccountNo() + "账户类型错误！");
						}
					}
				}
				log.info("-------------工行联通项目--计提结束/结息---------");
			}

			// 如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
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
			// 执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			// 抛出用以通知事务的组织者。
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					log.info("*****回滚******");
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
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
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

		return nResult;
	}

	/**
	 * 冲销计提利息。
	 * 
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建 连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param resultInfo[] 包含要冲销计提的账户相关信息的实体列表.
	 * @return int value
	 * @throws Excetion 任何系统异常发生时。
	 */
	public int clearDrawingInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo)
			throws Exception
	{

		int nResult = 0; // 返回值

		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
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
			conInternal.setAutoCommit(false);
		}

		try
		{
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			UtilOperation uo = new UtilOperation(conInternal);
			Sett_TransInterestSettlementDAO interestDao = new Sett_TransInterestSettlementDAO();
			Sett_SubAccountDAO subAcctontDao = new Sett_SubAccountDAO(conInternal);
			SubAccountAssemblerInfo saai = null;
			log.info("-------------开始冲销计提---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestQueryResultInfo info = new InterestQueryResultInfo();
				info = (InterestQueryResultInfo) resultVec.elementAt(i);

		        log.debug("---------判断账户类型------------");
				long accountTypeID = info.getAccountTypeID();
		        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
		        AccountTypeInfo accountTypeInfo = null;
		        try {
					accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (accountTypeInfo != null) 
				{
					// 是否贷款账户组类型
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------判断计提利息是否大于0---------------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 计提利息为空不能冲销计提");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------贷款利息冲销计提开始---------");
	
							// 是否贷款账户组类型
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
								{
									// 保存结息记录
									log.info("----------保存开始---------");
	
									/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
									{
										settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
									{
										settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
									}
                                    //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
									InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
									// 自营贷款计提应收利息（含冲销）
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
									{
										// 贴现贷款计提应收利息（含冲销）
										saveInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
									{
										// 委托贷款计提应收利息（含冲销）
										saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										// 银团贷款计提应收利息（含冲销）
										saveInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
									}
	
									saveInfo.setInterest((-1)*saveInfo.getInterest());//存入交易记录为负
									long l = interestDao.add(conInternal, saveInfo);
									saveInfo.setInterest((-1)*saveInfo.getInterest());
	
									if (l < 0)
									{
										throw new IException("结息保存失败！！");
									}
	
									log.info("----------保存结束---------");
	
									SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
									preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
									Timestamp dtInterestStart = null;
	
									dtInterestStart = interestDao.findInterestStartDate( conInternal
											, info.getSubAccountID()
											, SETTConstant.InterestFeeType.PREDRAWINTEREST
											, SETTConstant.InterestOperateType.PREDRAWINTEREST );
	
									if (dtInterestStart == null)
									{
										throw new IException("没有做计提操作，无法冲销计提！");
									}
	
									//取子账户的结息日
									saai = new SubAccountAssemblerInfo();
									saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
									
									// 修改子账户信息
									// preDrawInfo.getPredrawInterest() 子帐户中的计提利息
									// saveInfo.getInterest() 页面显示计提利息
									subAcctontDao.updatePreDrawInterestAndDateReverse( saveInfo.getSubAccountID()
											, UtilOperation.Arith.round(preDrawInfo.getPredrawInterest() -UtilOperation.Arith.round( saveInfo.getInterest(), 2), 2)
											
											, saai.getSubAccountCurrenctInfo().getClearInterestDate()
											
											, saveInfo.getAccountTypeID() );
	
									if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
									{
										log.info("--------生成会计分录开始---------");
	
										GenerateGLEntryParam param = new GenerateGLEntryParam();
										param.setReceiveAccountID(info.getSubAccountID());
										param.setPayAccountID(info.getSubAccountID());
										// param.setRemissionInterest(saveInfo.getInterest());
	
										// 计提利息，可空
										param.setPreDrawInterest((-1) * saveInfo.getInterest());
	
										// 本金/交易金额
										param.setPrincipalOrTransAmount((-1)
												* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo
														.getInterest(), 2)), 2));
	
										// 利息合计，可空
										param.setTotalInterest((-1)
												* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
	
										param.setOfficeID(saveInfo.getOfficeID());
										param.setCurrencyID(saveInfo.getCurrencyID());
	
										// 自营贷款计提应收利息（含冲销）
										param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
										{
											// 贴现贷款计提应收利息（含冲销）
											param.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
										{
											// 委托贷款计提应收利息（含冲销）
											param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
										{
											// 银团贷款计提应收利息（含冲销）
											param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
											
											BankLoanQuery bankloanquery = new BankLoanQuery();
											ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
											param.setList(list);
										}
	
										param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(),
												saveInfo.getCurrencyID()));
										param.setInterestStartDate(saveInfo.getInterestSettlementDate());
										param.setTransNo(saveInfo.getTransNo());
										param.setAbstractStr(saveInfo.getAbstract());
	
										boolean bFlag = glo.generateGLEntry(param, conInternal);
	
										log.info("--------生成会计分录结束---------");
									}
								}
							}
							log.info("-------------贷款利息冲销计提结束---------");
						}
					}
					// 是否定期账户组类型
					// 是否通知账户组类型
					else if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY) &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------判断计提利息是否大于0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 计提利息为空不能冲销计提");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------定期利息冲销计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
	
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// 定期存款计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									// 通知存款计提应付利息（含冲销）
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("结息保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 得到上次结息日期
								Timestamp dtInterestStart = null;
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("没有做计提操作，无法冲销计提！");
								}
	
								//取子账户的结息日
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
								
								// 修改子账户信息
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("--------生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// 定期存款计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
									{
										// 通知存款计提应付利息（含冲销）
										//modified by qhzhou 2008-02-20
										param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
										//saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("冲销计提");
									
									//added by qhzhou 2008-02-20
									param.setInputUserID(saveInfo.getInputUserID());
									param.setCheckUserID(saveInfo.getInputUserID());
									
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
							log.info("-------------定期利息冲销计提结束---------");
						}
					}
					// 是否保证金账户组类型
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------判断计提利息是否大于0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 计提利息为空不能冲销计提");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------保证金利息冲销计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
								
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// 保证金存款计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("保证金结息保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 得到上次结息日期
								Timestamp dtInterestStart = null;
	
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("没有做计提操作，无法冲销计提！");
								}
								
								//取子账户的结息日
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
	
								// 修改子账户信息
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("---保证金-----生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// 保证金存款计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("冲销计提");
	
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("-----保证金---生成会计分录结束---------");
								}
							}
							log.info("-------------保证金利息冲销计提结束---------");
						}
					}
					// 是否备付金、准备金账户组类型
					else if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE) &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------判断计提利息是否大于0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 计提利息为空不能冲销计提");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------备付金、准备金利息冲销计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
								
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								//根据账户组类型设置交易类型
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								//根据账户组类型设置交易类型
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("保证金结息保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 得到上次结息日期
								Timestamp dtInterestStart = null;
	
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("没有做计提操作，无法冲销计提！");
								}
								
								//取子账户的结息日
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
	
								// 修改子账户信息
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("---备付金、准备金-----生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									//根据账户组类型设置交易类型
									if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
									}
									else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("冲销计提");
	
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("-----保证金---生成会计分录结束---------");
								}
							}
							log.info("-------------保证金利息冲销计提结束---------");
						}
					}
					// 是否拆借账户组类型
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------判断计提利息是否大于0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 计提利息为空不能冲销计提");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------拆借利息冲销计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
								
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// 拆借计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("拆借冲销计提保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// 得到上次结息日期
								Timestamp dtInterestStart = null;
	
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("没有做计提操作，无法冲销计提！");
								}
								
								//取子账户的结息日
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
	
								// 修改子账户信息
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("---拆借冲销计提-----生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// 保证金存款计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("冲销计提");
	
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("-----保证金---生成会计分录结束---------");
								}
							}
							log.info("-------------保证金利息冲销计提结束---------");
						}
					}
					//是否活期账户组类型
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
						  && info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------判断计提利息是否大于0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("账户：" + info.getAccountNo() + " 账户名称：" + NameRef.getAccountNameByID(info.getAccountID()) + " 计提利息为空不能冲销计提");
						}
						else// if (info.getDrawingInterest() > 0)
						{
							log.info("-------------活期利息冲销计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
								// 保存结息记录
								log.info("----------保存开始---------");
	
								/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (重载获取交易号的方法，将conInternal作为参数维护事务)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								//活期存款计提应付利息（含冲销）
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("结息保存失败！！");
								}
	
								log.info("----------保存结束---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
	
								// 得到上次结息日期
								Timestamp dtInterestStart = null;
								dtInterestStart = interestDao.findInterestStartDate(conInternal
										, info.getSubAccountID()
										, SETTConstant.InterestFeeType.PREDRAWINTEREST
										, SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("没有做计提操作，无法冲销计提！");
								}
								
								//取子账户的结息日
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
								
								//修改子账户信息
								subAcctontDao.updatePreDrawInterestAndDateReverse(
										  saveInfo.getSubAccountID()
										  
										  //Boxu Add 2008年2月28日 原利息=计提利息-(活期利息+协定利息)
										  //, UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), saveInfo.getInterest()), 2)
										  , UtilOperation.Arith.round(
												  UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
														  UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2))
														  )
												  , 2)
										  
										  //取子账户的结息日
										  //, dtInterestStart
										  , saai.getSubAccountCurrenctInfo().getClearInterestDate()
										  
										  , saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("--------生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									
									//原利息=计提利息-(活期利息+协定利息)
									param.setPreDrawInterest((-1) * UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2)));
									param.setPrincipalOrTransAmount((-1) * 
																		  UtilOperation.Arith.round(
																		  UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
																				  UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2))
																				  )
																		  , 2)
																		  );
									param.setTotalInterest((-1) * 
																		  UtilOperation.Arith.round(
																		  UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
																				  UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2))
																				  )
																		  , 2)
																		  );
									
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// 活期存款计提应付利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("冲销计提");
									//modify by kevin(刘连凯)2012-04-01
									//国药项目要求把‘协定利息’单独记账
									param.setRemissionInterest((-1)*UtilOperation.Arith.round(saveInfo.getInterest(), 2));
									param.setReallyReceiveInterest((-1)*UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2));
	
	
									//param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
							log.info("-------------定期利息冲销计提结束---------");
						}
					}
					else
					{
						// throw new IException("账户:"
						// + info.getAccountNo()
						// + "放款单:"
						// + info.getPayFormNo() + "无法冲销计提");
	
						throw new IException("账户："
								+ info.getAccountNo()
								// + "放款单:"
								+ (info.getPayFormNo() != null && info.getPayFormNo().length() > 0 ? " 放款单："
										+ info.getPayFormNo() : "") + " 账户类型："
								+ SETTConstant.AccountType.getName(info.getAccountTypeID()) + " 无法冲销计提");
					}
				}
			}
			log.info("-------------冲销计提结束---------");

			// 如果本方法负责维护事务，一切都顺利完成，提交该事务，完成整个业务逻辑。
			if (con == null)
			{
				try
				{
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
			// 执行过程中出现了异常，如果事务是在该方法内部启动的，那么该方法要负责事务的回退；否则只需将异常再次
			// 抛出用以通知事务的组织者。
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
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
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

		return nResult;

	}

	/**
	 * 结存款类利息（工行-联通项目）
	 * 
	 * @param officeID
	 * @param currencyID
	 * @param accountID
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	private void drawDepositInterestForICBC(Connection conn, long officeID, long currencyID, long accountID,
			long subAccountID, double dInterest, Timestamp dtSettlementDate) throws Exception
	{

		// 获取子账户信息
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
		AccountBean accountbean = new AccountBean(conn);

		try
		{
			subAccountAssemblerInfo = subAccountDAO.findByAccountID(accountID);
			if (subAccountAssemblerInfo == null)
			{
				throw new Exception("根据账户ID：" + accountID + "找不到子账户信息");
			}
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();

			Timestamp lastclearInterestDate = subAccountCurrentInfo.getClearInterestDate();// 结息日期
			long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();

			// 若利息大于0(存款利息)，则执行执行结息操作
			if (dInterest > 0)
			{
				UtilOperation utilOperation = new UtilOperation();
				String strNewTransNo = utilOperation.getNewTransactionNo(officeID, currencyID,SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
				strNewTransNo = DataFormat.formatDate(dtSettlementDate).replaceAll("-", "")
						+ strNewTransNo.substring(8);

				// 保存结息纪录
				Sett_TransInterestSettlementDAO transInterestSettlementDAO = new Sett_TransInterestSettlementDAO();
				InterestSettmentInfo interestSettmentInfo = new InterestSettmentInfo();
				interestSettmentInfo.setOfficeID(officeID);
				interestSettmentInfo.setCurrencyID(currencyID);
				interestSettmentInfo.setTransNo(strNewTransNo);
				interestSettmentInfo.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
				interestSettmentInfo.setAccountID(accountID);
				interestSettmentInfo.setSubAccountID(subAccountID);
				interestSettmentInfo.setAccountTypeID(NameRef.getAccountTypeByID(accountID));
				interestSettmentInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
				interestSettmentInfo.setOperationType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
				interestSettmentInfo.setInterestSettlementDate(dtSettlementDate);
				interestSettmentInfo.setInterestStartDate(lastclearInterestDate);
				interestSettmentInfo.setInterestEndDate(new Timestamp(IDate.before(dtSettlementDate, -1).getTime()));
				interestSettmentInfo.setInterestDays(IDate.intervalDays(lastclearInterestDate, interestSettmentInfo
						.getInterestEndDate()));
				interestSettmentInfo.setRate(BaseQueryObject.getCurrentInterestRate(dtSettlementDate,
						interestSettmentInfo.getInterestEndDate(), interestRatePlanID, 0));
				interestSettmentInfo.setReceiveInterestAccountID(accountID);
				interestSettmentInfo.setReceiveInterestAccountID(accountID);
				interestSettmentInfo.setExecuteDate(Env.getSystemDate(officeID, currencyID));
				interestSettmentInfo.setInputUserID(Constant.MachineUser.InputUser);
				interestSettmentInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
				interestSettmentInfo.setIsKeepAccount(Constant.TRUE);
				interestSettmentInfo.setPecisionInterest(dInterest);
				interestSettmentInfo.setInterest(dInterest);
				transInterestSettlementDAO.add(conn, interestSettmentInfo);

				// 增加活期账户
				TransAccountDetailInfo tadi = new TransAccountDetailInfo();
				tadi.setAmount(dInterest);
				tadi.setCurrencyID(currencyID);
				tadi.setDtExecute(dtSettlementDate);
				tadi.setDtInterestStart(dtSettlementDate);
				tadi.setOfficeID(officeID);
				tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
				tadi.setTransAccountID(accountID);
				tadi.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
				tadi.setTransSubAccountID(subAccountID);
				tadi.setTransNo(interestSettmentInfo.getTransNo());
				tadi.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
				accountbean.depositCurrent(tadi);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
}
