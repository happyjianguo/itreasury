package com.iss.itreasury.settlement.transferinterest.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.bizlogic.TransferContractBiz;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_TransInterestSettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.ClearLoanAccountInterestConditionInfo;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.transferinterest.dao.CalculateCraInterestDao;
import com.iss.itreasury.settlement.transferinterest.dao.TransferInterestRecordDao;
import com.iss.itreasury.settlement.transferinterest.dataentity.CraInterestInfo;
import com.iss.itreasury.settlement.transferinterest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.transferinterest.dataentity.TransferInterestRecordInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetialResultInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanAmountDetailinfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

public class CraInterestSettlement {
	/**
	 * 如果是该类的方法负责维护事务，应该从容器取得数据库连接还是直接通过JDBC访问，缺省为从容器取得。
	 */
	private boolean	bConnectionFromContainer	= true;
	private Log4j	log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private CalculateCraInterestDao calculateCraInterestDao;
	public CraInterestSettlement() throws IException
	{
		calculateCraInterestDao = new CalculateCraInterestDao();
	}
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
	 * 根据条件进行结息记录的查询，为计提利息、冲销计提利息和结算功能提供支持。
	 * 
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建 连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param queryInfo 包含查询条件的实体，never null.
	 * @return InterestQueryResultInfo[] 满足条件的结息记录主体信息数组，关于利息等信息 需要调用计息接口计算得出。如果没有符合条件的结息记录，那么返回 null.
	 * @throws Excetion 任何系统异常发生时。
	 */
	public Collection selectCraTransferRecords(Connection con, CraInterestInfo queryInfo) throws Exception
	{

		Collection coll = null; 
		Collection conInterestColl = null;

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
			if(queryInfo.getLCraContractTypeId()==SETTConstant.CraSettTransactionType.REPURCHASE_NOTIFY)
			{
			    log.info("-------开始处理卖出回购查询数据--------");			
			    coll = calculateCraInterestDao.getClearContract(conInternal,queryInfo);
			    if(coll != null && coll.size() > 0)
			     {
				     conInterestColl = getContractSubsection(conInternal,queryInfo, coll);
				
			     }
			log.info("-------处理卖出回购查询数据结束--------");
			}
			else if(queryInfo.getLCraContractTypeId()==SETTConstant.CraSettTransactionType.BREAK_NOTIFY)
			{
				
				log.info("-------开始处理卖出卖断查询数据--------");			
				coll = calculateCraInterestDao.getClearContract(conInternal,queryInfo);
				if(coll != null && coll.size() > 0)
			     {
				     conInterestColl = getBreakContractSubsection(conInternal,queryInfo, coll);
				
			     }
			}
			
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

		return conInterestColl;
	}
	/**
	 * 组装处理卖出回购
	 * @param 
	 * @return Collection
	 * @throws IException
	 */
	public Collection getContractSubsection(Connection con,CraInterestInfo qInfo, Collection contractColl) throws Exception
	{
		TransferContractInfo conInfo = new TransferContractInfo();
		Vector coll = new Vector();
		Collection conColl = contractColl;
		TransferLoanContractInfo info = null;		
		try{
			if(conColl != null && conColl.size() > 0)
			{
				Iterator itcontract = conColl.iterator();
				while(itcontract.hasNext())
				{
					conInfo = (TransferContractInfo) itcontract.next();		
					InterestQueryResultInfo recordInfo = new InterestQueryResultInfo();
					/**********/
					Timestamp dtStartDate = null;
					Timestamp dtEndDate= null;
					double dtStartBalance = 0.0;
					double interestRate = 0.0;
					double sumPayInterest = 0.0;
					double sumInterest = 0.0;
					double interest = 0.0;
					double interestrest = 0.0;
					double prawInterest = 0.0;
					long intervalDays = -1;
					//起息日
					//dtStartDate = calculateCraInterestDao.findDtStratDate(con,conInfo);	
					dtStartDate = calculateCraInterestDao.findNewDtStratDate(con,conInfo);	
					//止息日
					dtEndDate = DateUtil.getNextNDay(qInfo.getDtClearInterest(), -1);
					conInfo.setPreclearInterstDate(dtStartDate);
					conInfo.setClearInterstDate(dtEndDate);
					//期初余额
					dtStartBalance = calculateCraInterestDao.findStartBalance(con, conInfo);
					//合同利率
					interestRate = calculateCraInterestDao.queryInterestRate(con,conInfo);
					//查询收款或付款本金交易
					Collection colTrans = (Collection)calculateCraInterestDao.queryTransInfo(con, conInfo);
					//查询结息日当天收付款利息交易
					sumPayInterest = calculateCraInterestDao.querySumInterest(con, conInfo);
					//查询结息日当天的计提利息
					prawInterest = calculateCraInterestDao.queryPrawInterest(con,conInfo);					
					intervalDays =  DateUtil.getIntervalDays(dtStartDate,qInfo.getDtClearInterest() ,DateUtil.InterestCalculationMode.FACTDAY);
					recordInfo.setStartDate(dtStartDate);
					recordInfo.setEndDate(dtEndDate);
					recordInfo.setDays(intervalDays);
					recordInfo.setCraContractNo(conInfo.getContractCode());
					recordInfo.setInterestRate(interestRate);
					recordInfo.setCraCounterPartName(conInfo.getCounterPartName());
					recordInfo.setCraCounterpartID(conInfo.getCounterPartId());
					recordInfo.setCraTransActionID(conInfo.getTranstypeId());
					recordInfo.setCraTransActionType(CRAconstant.CraTransactionType.getName(conInfo.getTranstypeId()));
					recordInfo.setCraContractID(conInfo.getId());
					
					
					if(colTrans != null && colTrans.size() > 0)
					{
						Iterator itcon = colTrans.iterator();
						while(itcon.hasNext())
						{
							info = (TransferLoanContractInfo)itcon.next();
							dtEndDate = info.getINTERESTSTART();
							interest = DateUtil.caculateInterest(
									  dtStartBalance 
									, dtStartDate
									, dtEndDate
									, DateUtil.InterestCalculationMode.FACTDAY
									, interestRate
									, DateUtil.InterestRateTypeFlag.YEAR
									, DateUtil.InterestRateDaysFlag.DAYS_360 );
							
							if(info.getTRANSACTIONTYPEID()== SETTConstant.TransactionType.TRANSFERREPAY)//收款
							{
								dtStartBalance = UtilOperation.Arith.add(dtStartBalance ,info.getAMOUNT());
							}
							else if(info.getTRANSACTIONTYPEID()== SETTConstant.TransactionType.TRANSFERPAY)//付款
							{
								dtStartBalance = UtilOperation.Arith.sub(dtStartBalance, info.getAMOUNT());
							}	
							dtStartDate = dtEndDate;
							sumInterest = sumInterest + interest;
						}
						  interestrest = DateUtil.caculateInterest(
								  dtStartBalance 
								, dtStartDate
								, qInfo.getDtClearInterest()
								, DateUtil.InterestCalculationMode.FACTDAY
								, interestRate
								, DateUtil.InterestRateTypeFlag.YEAR
								, DateUtil.InterestRateDaysFlag.DAYS_360 );						
					}
					else
					{
						interestrest = DateUtil.caculateInterest(
								  dtStartBalance 
								, dtStartDate
								, qInfo.getDtClearInterest()
								, DateUtil.InterestCalculationMode.FACTDAY
								, interestRate
								, DateUtil.InterestRateTypeFlag.YEAR
								, DateUtil.InterestRateDaysFlag.DAYS_360 );	
						
					}
					recordInfo.setBalance(dtStartBalance);
					recordInfo.setInterest(UtilOperation.Arith.add(interestrest,sumInterest));
					recordInfo.setDrawingInterest(UtilOperation.Arith.sub(prawInterest, sumPayInterest));
					coll.add(recordInfo);					
				}
			}			
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return coll;
	}
	/**
	 * 组装处理卖出卖断
	 * @param 
	 * @return Collection
	 * @throws IException
	 */
	public Collection getBreakContractSubsection(Connection con,CraInterestInfo qInfo, Collection contractColl) throws Exception
	{
		
		Collection conColl = contractColl;		
		Vector collcontract = new Vector();
		TransferLoanAmountDetailinfo noticeInfo = new TransferLoanAmountDetailinfo();
		TransferLoanAmountDetailinfo transNoticeInfo = new TransferLoanAmountDetailinfo();
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		
		TransferContractBiz contractBiz = new TransferContractBiz();
	   
		CommissionCalculation commission = new CommissionCalculation();
		try{
			if(conColl != null && conColl.size() > 0)
			{
				Iterator itcontract = conColl.iterator();
				while(itcontract.hasNext())
				{
					Vector coll = new Vector();
					TransferContractInfo conInfo = new TransferContractInfo();
					conInfo = (TransferContractInfo) itcontract.next();					
					/**********/
					Timestamp dtStartDate = null;
					Timestamp dtEndDate= null;					
					//起息日
					dtStartDate = calculateCraInterestDao.queryDtStratDate(con,conInfo);	
					//止息日
					dtEndDate = DateUtil.getNextNDay(qInfo.getDtClearInterest(), -1);
					conInfo.setPreclearInterstDate(dtStartDate);
					conInfo.setClearInterstDate(dtEndDate);
					conInfo.setOfficeId(qInfo.getOfficeID());
					conInfo.setCurrencyId(qInfo.getCurrencyID());
					
					TransferContractInfo  contractInfo = new TransferContractInfo() ;
				    try
				    {
				    	contractInfo = contractBiz.findInfoById(conInfo.getId());
				    }
				    catch(Exception e)
				    {
				    	e.printStackTrace();
				    }
					InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
					//通过转让合同找到对应的贷款通知单信息
					Collection colNotice = calculateCraInterestDao.queryLoanNoteDetail(con,conInfo);
					if(colNotice != null && colNotice.size() > 0)
					{
						Iterator itnotice = colNotice.iterator();
						while(itnotice.hasNext())
						{
							InterestQueryResultInfo recordInfo = new InterestQueryResultInfo();
							
							double dtStartBalance = 0.0;
							double interestRate = 0.0;
							double sumPayInterest = 0.0;
							double sumInterest = 0.0;
							double interest = 0.0;
							double payCommission = 0.0;
							dtStartDate = conInfo.getPreclearInterstDate();
							dtEndDate = conInfo.getClearInterstDate();
							
							
							noticeInfo = (TransferLoanAmountDetailinfo)itnotice.next();
							noticeInfo.setInterestsettlement(qInfo.getDtClearInterest());
							noticeInfo.setCracontractid(conInfo.getId());
							noticeInfo.setPerinterestsettlement(conInfo.getPreclearInterstDate());
							noticeInfo.setOfficeid(qInfo.getOfficeID());
							noticeInfo.setCurrencyid(qInfo.getCurrencyID());
 				            //显示信息
						    long intervalDays =  DateUtil.getIntervalDays(dtStartDate,qInfo.getDtClearInterest() ,DateUtil.InterestCalculationMode.FACTDAY);
							recordInfo.setStartDate(dtStartDate);
							recordInfo.setEndDate(DateUtil.getNextNDay(qInfo.getDtClearInterest(), -1));
							recordInfo.setDays(intervalDays);
							recordInfo.setCraContractNo(conInfo.getContractCode());
							recordInfo.setCraCounterPartName(conInfo.getCounterPartName());
							recordInfo.setCraCounterpartID(conInfo.getCounterPartId());
							recordInfo.setCraTransActionID(conInfo.getTranstypeId());
							recordInfo.setCraTransActionType(CRAconstant.CraTransactionType.getName(conInfo.getTranstypeId()));
							recordInfo.setCraContractID(conInfo.getId());
							
							dtStartBalance = calculateCraInterestDao.queryBreakNoticeBalance(con,noticeInfo);
							//查询该笔通知单对应的收付款交易
							Collection colTransNotice = calculateCraInterestDao.queryBreakTransDetailInfo(con,noticeInfo);
							if( colTransNotice !=null && colTransNotice.size()>0 )
							{
								Iterator itTranNotice = colTransNotice.iterator();
								while(itTranNotice.hasNext())
								{
									transNoticeInfo =(TransferLoanAmountDetailinfo )itTranNotice.next();
									dtEndDate = transNoticeInfo.getIntereststart();
									interestRate = dao.getLatelyRate(transNoticeInfo.getNoticeformid(), dtEndDate);
									interest = DateUtil.caculateInterest(
											  dtStartBalance 
											, dtStartDate
											, dtEndDate
											, DateUtil.InterestCalculationMode.FACTDAY
											, interestRate
											, DateUtil.InterestRateTypeFlag.YEAR
											, DateUtil.InterestRateDaysFlag.DAYS_360 );
									if(transNoticeInfo.getTranstypeid()== SETTConstant.TransactionType.TRANSFERREPAY)//收款
									{
										dtStartBalance = UtilOperation.Arith.add(dtStartBalance ,transNoticeInfo.getAmount());
									}
									else if(transNoticeInfo.getTranstypeid()== SETTConstant.TransactionType.AGENTTRANSFERREPAY)//付款
									{
										dtStartBalance = UtilOperation.Arith.sub(dtStartBalance, transNoticeInfo.getAmount());
									}
									dtStartDate = dtEndDate;
									sumInterest = sumInterest + interest;
								}
								   interestRate = dao.getLatelyRate(transNoticeInfo.getNoticeformid(), DateUtil.getNextNDay(qInfo.getDtClearInterest(), -1));
								   double interestrest = DateUtil.caculateInterest(
											  dtStartBalance 
											, dtStartDate
											, qInfo.getDtClearInterest()
											, DateUtil.InterestCalculationMode.FACTDAY
											, interestRate
											, DateUtil.InterestRateTypeFlag.YEAR
											, DateUtil.InterestRateDaysFlag.DAYS_360 );
									recordInfo.setBalance(dtStartBalance);
									recordInfo.setInterest(UtilOperation.Arith.sub(UtilOperation.Arith.add(interestrest,sumInterest), sumPayInterest));
									payCommission = commission.calculateCommission(dtStartBalance, recordInfo.getInterest(), contractInfo.getChargeRate(), 8, CRAconstant.ChargeCommisonPayType.CHARGEINTEREST);
									//转让合同明细ID
									transNoticeInfo.setCracontractid(contractInfo.getId());
									transNoticeInfo.setOfficeid(contractInfo.getOfficeId());
									transNoticeInfo.setCurrencyid(contractInfo.getCurrencyId());
									long transferContractDetailID = calculateCraInterestDao.queryContractDetailID(con,transNoticeInfo);
									//显示信息
									recordInfo.setCommission(payCommission);
									recordInfo.setInterestRate(interestRate);
									recordInfo.setLoanNoteID(transNoticeInfo.getNoticeformid());
									recordInfo.setLoanContractNo(transNoticeInfo.getContractcode());
									recordInfo.setLoanNoteNo(transNoticeInfo.getPaycode());
									recordInfo.setCraContractDetailID(transferContractDetailID);
									recordInfo.setBorrowClientID(transNoticeInfo.getBorrowClientID());
								}
							else{
								if(dtStartBalance>0.0)
								{
									interestRate = dao.getLatelyRate(noticeInfo.getNoticeformid(), dtEndDate);
									interest  = DateUtil.caculateInterest(
											  dtStartBalance 
											, dtStartDate
											, qInfo.getDtClearInterest()
											, DateUtil.InterestCalculationMode.FACTDAY
											, interestRate
											, DateUtil.InterestRateTypeFlag.YEAR
											, DateUtil.InterestRateDaysFlag.DAYS_360 );
									
									
									payCommission = commission.calculateCommission(dtStartBalance, interest, contractInfo.getChargeRate(), 8, CRAconstant.ChargeCommisonPayType.CHARGEINTEREST);									
								}
								recordInfo.setBalance(dtStartBalance);
								recordInfo.setCommission(payCommission);
								recordInfo.setInterest(UtilOperation.Arith.sub(interest, sumPayInterest));
								recordInfo.setLoanNoteID(noticeInfo.getNoticeformid());								
								recordInfo.setLoanContractNo(NameRef.getContractNoByNoticeID(recordInfo.getLoanNoteID()));
								recordInfo.setLoanNoteNo(NameRef.getCraFormNoByID(noticeInfo.getNoticeformid()));	
								recordInfo.setInterestRate(interestRate);
							}
							System.out.print("sdfsdfsdfsdfs"+recordInfo.getBalance());
							coll.add(recordInfo);
						  }	
						resultInfo.setColl(coll);
						resultInfo.setCraCounterPartName(conInfo.getCounterPartName());
						resultInfo.setCraContractNo(conInfo.getContractCode());
						resultInfo.setCraTransActionType(CRAconstant.CraTransactionType.getName(conInfo.getTranstypeId()));
					   }
					collcontract.add(resultInfo);
					}
				
		    }		
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return collcontract;
	}
	/**
	 * 批量计提利息。 计提只针对利息。 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultInfo 包含要计提的账户相关信息的实体，never null.
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	 */
	public int drawingInterest(Connection con, Vector resultVec, TransferInterestRecordInfo settmentInfo) throws Exception
	{

		int nResult = 0; // 返回值
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
			log.info("-------------开始计提---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestQueryResultInfo info = new InterestQueryResultInfo();

				info = (InterestQueryResultInfo) resultVec.elementAt(i);
		        
				if (info != null) 
				{

					    TransferInterestRecordInfo recordInfo = new TransferInterestRecordInfo();
					    settmentInfo.setCracontractid(info.getCraContractID());
					    settmentInfo.setInterestType(SETTConstant.InterestOperateType.PREDRAWINTEREST);
					    recordInfo = calculateCraInterestDao.queryPreDateByContractID(conInternal, settmentInfo);	
						log.info("---------------判断利息是否等于0-----------------");
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// 取SETT_TRANSFERINTERSETRECORD表的计提利息字段
							if (recordInfo.getMinterest() > 0
									&& recordInfo.getDtinterestsettlement().equals(info.getCreateDate()))
							{
								throw new IException("转让合同号：" + info.getCraContractNo() + " 交易对手名称："
										+ info.getCraCounterPartName() + " 已经计提");
							}
							else
							{
								throw new IException("转让合同号：" + info.getCraContractNo() + " 交易对手名称：" + info.getCraCounterPartName() + " 利息为空不能计提");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{

							// 保存结息记录
							log.info("----------保存开始---------");
							
							/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
							settmentInfo.setTransActionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW);
							
							settmentInfo.setInterestType(SETTConstant.InterestOperateType.PREDRAWINTEREST);

							TransferInterestRecordInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
							// 计提利息
							// 利息费用计算的费用类型
							saveInfo.setInterestType(SETTConstant.InterestOperateType.PREDRAWINTEREST);

							saveInfo.setTransActionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW);

							log.debug(UtilOperation.dataentityToString(saveInfo));
	
							// 保存进入结息表SETT_TRANSFERINTERSETRECORD
							long l = calculateCraInterestDao.add(conInternal, saveInfo);
	
							if (l < 0)
							{
								throw new IException("结息保存失败");
							}
	
							log.info("----------保存结束---------");
							log.info("-------------贷款利息计提开始---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
							{
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
								{
									log.info("--------生成会计分录开始---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();	
									// 计提利息，可空
									param.setPreDrawInterest(saveInfo.getMinterest());
                                    param.setCraContractID(saveInfo.getCracontractid());
									param.setOfficeID(saveInfo.getOfficeid());
									param.setCurrencyID(saveInfo.getCurrencyid());	
									// 自营贷款计提应收利息（含冲销）
									param.setTransactionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW );									
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeid(),
											settmentInfo.getCurrencyid()));
									param.setInterestStartDate(saveInfo.getDtinterestsettlement());
									param.setTransNo(saveInfo.getStransno());
									param.setAbstractStr(saveInfo.getSabstract());
									param.setInputUserID(saveInfo.getInputuserid());
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------生成会计分录结束---------");
								}
							}
	
							log.info("-------------贷款利息计提结束---------");
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
	
	private TransferInterestRecordInfo getSaveInfo(Connection con,TransferInterestRecordInfo settmentInfo, InterestQueryResultInfo info) throws IException, Exception
	{
		// 结息表
		TransferInterestRecordInfo saveInfo = new TransferInterestRecordInfo();

		// 交易号
		UtilOperation uo = new UtilOperation();

		String strTransNo = "";
		try
		{
			strTransNo = uo.getNewTransactionNo(con,settmentInfo.getOfficeid(), settmentInfo.getCurrencyid(),settmentInfo.getTransActionTypeID());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("没有取到交易号！");
		}

		saveInfo.setStransno(strTransNo);
		// 公共信息
		saveInfo.setStatusid(Constant.RecordStatus.VALID);
		saveInfo.setCurrencyid(settmentInfo.getCurrencyid());
		saveInfo.setOfficeid(settmentInfo.getOfficeid());
		saveInfo.setInputuserid(settmentInfo.getInputuserid());
		saveInfo.setSabstract(settmentInfo.getSabstract());
		saveInfo.setIsSave(settmentInfo.getIsSave());
		saveInfo.setIsKeepAccount(settmentInfo.getIsKeepAccount());
		saveInfo.setIsSuccess(settmentInfo.getIsSuccess());
		saveInfo.setFaultReason(settmentInfo.getFaultReason());
		saveInfo.setDtexecute(settmentInfo.getDtexecute());
		saveInfo.setCracontractid(settmentInfo.getCracontractid());
		saveInfo.setCraContractDetailID(info.getCraContractDetailID());

		// 差异信息
		saveInfo.setDtinterestsettlement(settmentInfo.getDtinterestsettlement());
		saveInfo.setDtstart(info.getStartDate());
		saveInfo.setDtend(info.getEndDate());
		saveInfo.setDays(info.getDays());
		saveInfo.setAmount(info.getBalance());
		saveInfo.setDrate(info.getInterestRate());
		saveInfo.setInterestType(settmentInfo.getInterestType());
		// 计提利息
		// 利息
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setMinterest(UtilOperation.Arith.round(info.getInterest(), 2));
		}

		// 计提利息
		// 利息类型
		if (saveInfo.getInterestType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			
		}
		saveInfo.setMinterest(UtilOperation.Arith.round(info.getInterest(), 2));
		saveInfo.setCommission(UtilOperation.Arith.round(info.getCommission(), 2));
		saveInfo.setPayInterestAccountID(info.getPayInterestAccountID());

		//saveInfo.setAbstract(getAbstract(saveInfo, info));

		return saveInfo;
	}
	
	/**
	 * 冲销计提利息。
	 * 
	 * @param con 数据库连接，可以由外部传入，如果为null，那么在本方法内部创建 连接，并且该方法应该负责事务的维护；否则，直接使用即可。
	 * @param resultInfo[] 包含要冲销计提的账户相关信息的实体列表.
	 * @return int value
	 * @throws Excetion 任何系统异常发生时。
	 */
	public int clearDrawingInterest(Connection con, Vector resultVec, TransferInterestRecordInfo settmentInfo)
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
			log.info("-------------开始冲销计提---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestQueryResultInfo info = new InterestQueryResultInfo();
				info = (InterestQueryResultInfo) resultVec.elementAt(i);

						log.info("---------判断计提利息是否大于0---------------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("转让合同号：" + info.getCraContractNo() + " 交易对手名称：" + info.getCraCounterPartName() + " 计提利息为空不能冲销计提");
						}
						else if (info.getDrawingInterest() > 0)
						{
							log.info("-------------冲销计提开始---------");
	
								if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // 保存
								{
									// 保存结息记录
									log.info("----------保存开始---------");
	
									/*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
									settmentInfo.setCracontractid(info.getCraContractID());
									settmentInfo.setTransActionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW);
									settmentInfo.setInterestType(SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST);
									TransferInterestRecordInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
									saveInfo.setMinterest((-1)*saveInfo.getMinterest());//存入交易记录为负
									long l = calculateCraInterestDao.add(conInternal, saveInfo);
									saveInfo.setMinterest((-1)*saveInfo.getMinterest());
	
									if (l < 0)
									{
										throw new IException("结息保存失败！！");
									}
	
									log.info("----------保存结束---------");
	
	
									if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
									{
										log.info("--------生成会计分录开始---------");
	
										GenerateGLEntryParam param = new GenerateGLEntryParam();
	
										// 计提利息，可空
										param.setPreDrawInterest((-1) * saveInfo.getMinterest());	
																				
										param.setOfficeID(saveInfo.getOfficeid());
										param.setCurrencyID(saveInfo.getCurrencyid());
										param.setTransactionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW);					
										param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeid(),saveInfo.getCurrencyid()));
										param.setInterestStartDate(saveInfo.getDtinterestsettlement());
										param.setTransNo(saveInfo.getStransno());
										param.setAbstractStr(saveInfo.getSabstract());
										param.setCraContractID(saveInfo.getCracontractid());
										param.setInputUserID(saveInfo.getInputuserid());
										boolean bFlag = glo.generateGLEntry(param, conInternal);
	
										log.info("--------生成会计分录结束---------");
									}
								}						
							log.info("-------------贷款利息冲销计提结束---------");
						}
					 else
					{
						
						throw new IException("转让合同号："
								+ info.getCraContractNo()
								+" 交易对手名称：" + info.getCraCounterPartName() +  " 无法冲销计提");
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
	 * 信贷资产转让卖出卖断批量结算。
	 * 
	 * @param con 数据库连接，由外部传入，never null.
	 * @param resultVec 包含要计提的账户相关信息的实体，never null.
	 * @param isSave 是否保存
	 * @param settmentInfo 存放公用信息 是否记账，是否保存等
	 * @return int value:
	 * @throws Excetion 任何系统异常发生时。
	 */
	public int balanceInterest(Connection con, Vector resultVec, TransferInterestRecordInfo settmentInfo) throws Exception
	{

		int nResult = 0; // 返回值
		long lsubPayInterestID = -1;
		// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
		Connection conInternal = null;
		Vector vec = new  Vector();
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
				AccountOperation aco = new AccountOperation(conInternal);
				GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
				
				log.info("-------------开始校验结息---------");
				
				for(int k = 0; k < resultVec.size(); k++)
				{
					InterestQueryResultInfo queryInfoCheck = new InterestQueryResultInfo();
					queryInfoCheck = (InterestQueryResultInfo) resultVec.elementAt(k);
					
					for( int j = 0; j < queryInfoCheck.getColl().size(); j++ )
					{
						
						InterestQueryResultInfo infoCheck = new InterestQueryResultInfo();
					    vec = (Vector)queryInfoCheck.getColl();
					    infoCheck = (InterestQueryResultInfo)vec.elementAt(j);
					    
					    if(infoCheck.getPayInterestAccountID()<0)
					    {
					    	throw new IException("转让合同号：" + infoCheck.getCraContractNo() + " 交易对手名称："
									+ infoCheck.getCraCounterPartName() + " 放款通知单号"+infoCheck.getLoanNoteNo()+"的付息账户号为空");
					    }
					}
				}
				 log.info("-------------开始结息---------");
		
				for (int i = 0; i < resultVec.size(); i++)
				{
					InterestQueryResultInfo queryInfo = new InterestQueryResultInfo();
					queryInfo = (InterestQueryResultInfo) resultVec.elementAt(i);
					
					for (int m = 0; m < queryInfo.getColl().size(); m++)
					{
				    log.info("-------------卖出卖断开始结息---------");
				    InterestQueryResultInfo info = new InterestQueryResultInfo();
				    vec = (Vector)queryInfo.getColl();
				    info = (InterestQueryResultInfo)vec.elementAt(m);
				    
				    if(info.getPayInterestAccountID()<0)
				    {
				    	throw new IException("转让合同号：" + info.getCraContractNo() + " 交易对手名称："
								+ info.getCraCounterPartName() + " 放款通知单号"+info.getLoanNoteNo()+"的付息账户号为空");
				    }
					//利息
				    TransferInterestRecordInfo recordInfo = new TransferInterestRecordInfo();
			        settmentInfo.setCracontractid(info.getCraContractID());
			        settmentInfo.setInterestType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
			        recordInfo = calculateCraInterestDao.queryPreDateByContractID(conInternal, settmentInfo);
			        
				    if (DataFormat.formatDouble(info.getInterest()) == 0)
					{
						// 取SETT_TRANSFERINTERSETRECORD表的计提利息字段
						if (recordInfo.getMinterest() > 0 && recordInfo.getDtinterestsettlement().compareTo(Env.getSystemDate(settmentInfo.getOfficeid(), settmentInfo.getCurrencyid())) == 0)
							{
								throw new IException("转让合同号：" + info.getCraContractNo() + " 交易对手名称："
										+ info.getCraCounterPartName() + " 已经结息");
							}
							else
							{
								throw new IException("转让合同号：" + info.getCraContractNo() + " 交易对手名称：" + info.getCraCounterPartName() + " 利息为空不能结息");
							}
					 }

				    if (info.getInterest() > 0)
						{
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE)  //保存
									
							 {								
							        log.info("----------保存开始---------");
							        //保存结息记录										
							        /*****在调用该方法前传入交易类型(供生成交易编号使用)*****/
										
									settmentInfo.setTransActionTypeID(SETTConstant.TransactionType.BREAKINTERESTNOTIFY);

									TransferInterestRecordInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
									
                                     //结算利息
									// 利息费用计算的费用类型
									saveInfo.setInterestType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
									saveInfo.setTransActionTypeID(SETTConstant.TransactionType.BREAKINTERESTNOTIFY);
									
									// 保存进入结息表SETT_TRANSFERINTERSETRECORD
									long l = calculateCraInterestDao.add(conInternal, saveInfo);
									if (l < 0)
									{
										throw new IException("结息保存失败！！");
									}
									log.info("----------保存结束---------");
									if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // 记账
									{										
										if (info.getPayInterestAccountID() <= 0)
											{
												throw new IException("转让合同" + info.getCraContractNo() + "没有付息账户");
											}
																														
										if (info.getPayInterestAccountID() > 0)
										{
											log.info("-------------活期支取开始---------");
											TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getStransno(), INTEREST_CURRENT_WITHDRAW);
											
											withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.BREAKINTERESTNOTIFY);
																							
											//利息调用
											withdrawTadi.setCommonOperation(false);
											lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
											if (lsubPayInterestID < 0)
											{
												throw new IException("活期支取失败！");
											}
											log.info("-------------活期支取结束---------");
										}											
										    log.info("------------生成贷款会计分录开始--------------");

											GenerateGLEntryParam param = new GenerateGLEntryParam();
																					
											TransferContractBiz contractBiz = new TransferContractBiz();
										    TransferContractInfo  contractInfo = null;
										    try
										    {
										    	contractInfo = contractBiz.findInfoById(saveInfo.getCracontractid());
										    }
										    catch(Exception e)
										    {
										    	e.printStackTrace();
										    }
											/**
											 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
											 */
											long lPrincipalType = -1;
											//流向 无关
										    lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
											//分录类型 无关
											long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
								            //交易子类型
											param.setSubTransactionType(SETTConstant.SubTransactionType.BREAK_NOTIFY);	
											double interest = saveInfo.getMinterest();
											double commission = saveInfo.getCommission();
											param.setPayAccountID(lsubPayInterestID);
											param.setPrincipalType(lPrincipalType);
											param.setEntryType(lEntryType);
											if(contractInfo.getCommissionPaymentType()==CRAconstant.CommisonPayType.FANHUAN)
											{
												param.setTotalInterest(interest);											
												param.setReallyReceiveInterest(interest);
											}
											else
											{												
												param.setTotalInterest(interest);
												param.setReallyReceiveInterest(UtilOperation.Arith.sub(interest,commission));
												param.setCommissionFee(commission);

											}
											param.setTransactionTypeID(SETTConstant.TransactionType.BREAKINTERESTNOTIFY);
											param.setTransNo(saveInfo.getStransno());
											param.setOfficeID(saveInfo.getOfficeid());
											param.setCurrencyID(saveInfo.getCurrencyid());
											param.setExecuteDate(saveInfo.getDtexecute());
											param.setInterestStartDate(saveInfo.getDtinterestsettlement());
											param.setAbstractStr(saveInfo.getSabstract());
											param.setInputUserID(saveInfo.getInputuserid());
											param.setCheckUserID(saveInfo.getInputuserid());
											param.setCraContractID(saveInfo.getCracontractid());
											boolean res = glo.generateGLEntry(param,conInternal);
							    												
											
											log.info("------------生成卖出卖断会计分录结束--------------");
										}
									
									log.info("-------------卖出卖断利息结息结束---------");	
								}													
				          }
				
				     log.info("-------------结息结束---------");
                 }
		   }		
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

		return nResult;
	}
	private int	INTEREST_CURRENT_WITHDRAW	= 0;
	/** 转换利息结算参数到账户操作参数 */
	private TransAccountDetailInfo transferInterestQueryResultInfoToTransAccountDetailInfo(TransferInterestRecordInfo isInfo,InterestQueryResultInfo info, String transNo, int transType)
	{

		TransAccountDetailInfo tadi = null;
		tadi = new TransAccountDetailInfo();
		tadi.setAbstractStr(isInfo.getSabstract());
		tadi.setCurrencyID(isInfo.getCurrencyid());
		tadi.setDtExecute(isInfo.getDtexecute());
		tadi.setDtInterestStart(info.getCreateDate());
		tadi.setOfficeID(isInfo.getOfficeid());
		tadi.setTransNo(transNo);
		tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
		tadi.setGroup(-1);
		if (transType == INTEREST_CURRENT_WITHDRAW)
		{
			tadi.setTransAccountID(info.getPayInterestAccountID());
			tadi.setAmount(isInfo.getMinterest());
		}
		

		return tadi;
	}
  }

