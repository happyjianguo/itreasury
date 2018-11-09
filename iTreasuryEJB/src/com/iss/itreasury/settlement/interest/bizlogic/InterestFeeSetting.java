package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_InterestFeeSettingDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_InterestFeeSettingDetailDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_TransInterestSettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.InterestFeeSettingDetailInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestFeeSettingInfo;
import com.iss.itreasury.settlement.interest.dataentity.QueryInterestFeeSettingDetailInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Scheduler;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InterestFeeSetting
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 私有数据库链接对象
	 */
	private Connection m_Conn = null;

	/**
	 * 是否是本地事务
	 * true:内部控制事务 
	 * false:外部控制事务
	 */
	private boolean m_isLocalTransaction = false;

	/**
	 * Constructor for InterestFeeSetting.
	 */
	public InterestFeeSetting()
	{
		this.m_Conn = null;
		this.m_isLocalTransaction = true;
	}

	/**
	 * 外部传入Connection构造当前业务对象，事务也由外部程序保证
	 * @param conn
	 * @throws Exception
	 */
	public InterestFeeSetting(Connection conn) throws Exception
	{
		if (conn == null)
			throw new Exception("Connection is null!");

		this.m_Conn = conn;
		this.m_isLocalTransaction = false;
	}

	/**
	 * 获得数据库链接，当事务由外部程序保证时，返回勾账当前业务对象时
	 * 传人的Connection；当事务由当前对象保证时，获得新的Connection。
	 * @return Connection
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception
	{
		if (this.m_isLocalTransaction && this.m_Conn == null)
		{
			Connection conn = Database.getConnection();
			//don't auto commit
			//conn.setAutoCommit(false);

			this.m_Conn = conn;
		}

		return this.m_Conn;
	}

	/**
	 * 释放数据库链接
	 * 当事务由外部程序保证时，此方法不作任何处理
	 * @param conn
	 */
	private void clearup(Connection conn)
	{
		if (this.m_isLocalTransaction)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				log.error("Cannot close connection!");
			}
		}
	}

	/**
	 * 提交数据库操作
	 * 当事务由外部程序保证时，此方法不作任何处理
	 * @param conn
	 */
	private void commit(Connection conn)
	{
		if (this.m_isLocalTransaction)
		{
			try
			{
				conn.commit();
			}
			catch (SQLException e)
			{
				log.error("Cannot commit connection!");
			}
		}
	}

	/**
	 * 回滚数据库操作
	 * 当事务由外部程序保证时，此方法不作任何处理
	 * @param conn
	 */
	private void rollback(Connection conn)
	{
		if (this.m_isLocalTransaction)
		{
			try
			{
				conn.rollback();
			}
			catch (SQLException e)
			{
				log.error("Cannot rollback connection!");
			}
		}
	}

	public long create(InterestFeeSettingInfo info) throws IException
	{
		long result = -1;
		boolean bFlag = false;

		Sett_InterestFeeSettingDAO transInterestFeeDAO = null;

		Connection conn = null;

		try
		{
			conn = this.getConnection();

			//给sett_interestfeesettingdetail表设置‘share row exclusive mode’锁，
			//防止在设置明细信息时对改表的修改，但不影响对该表的读操作
			Statement st = conn.createStatement();
			st.execute("lock table sett_interestfeesettingdetail in share row exclusive mode");
			st.close();

			transInterestFeeDAO = new Sett_InterestFeeSettingDAO(conn);

			//设置状态
			info.setIsExecute(SETTConstant.BooleanValue.ISFALSE);
			info.setStatusID(SETTConstant.TransactionStatus.SAVE);

			result = transInterestFeeDAO.add(info);

			//save successed,to save details
			if (result != -1)
			{
				info.setID(result);

				bFlag = this.createAllInterestFeeSettingDetailForInternal(info);
			}

			//加入自动执行计划
			try
			{
				Scheduler scheduler = Scheduler.getInstance();

				AutoInterestSettlementTask task = new AutoInterestSettlementTask(info.getID());

				task.setRunDateTime(info.getCalculateDate());

				scheduler.addTask(task);

				log.info("新建自动结息计划成功加入自动执行计划！");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("新建自动结息计划无法加入自动执行计划！");
			}

		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return result;
	}

	public long modify(InterestFeeSettingInfo info) throws IException
	{
		long result = -1;
		boolean bFlag = false;

		Sett_InterestFeeSettingDAO transInterestFeeDAO = null;

		Connection conn = null;

		try
		{
			conn = this.getConnection();

			//给sett_interestfeesettingdetail表设置‘share row exclusive mode’锁，
			//防止在设置明细信息时对改表的修改，但不影响对该表的读操作
			Statement st = conn.createStatement();
			st.execute("lock table sett_interestfeesettingdetail in share row exclusive mode");
			st.close();

			transInterestFeeDAO = new Sett_InterestFeeSettingDAO(conn);

			InterestFeeSettingInfo oldSettingInfo = transInterestFeeDAO.findByID(info.getID());

			result = transInterestFeeDAO.update(info);

			//save successed,to save details
			if (result == info.getID())
			{
				log.info("开始删除设置详细信息");
				bFlag = this.deleteAllInterestFeeSettingDetailInfoForInternal(info.getID());
				log.info("结束删除设置详细信息");
				if (bFlag)
				{
					bFlag = this.createAllInterestFeeSettingDetailForInternal(info);
				}
			}

			//加入自动执行计划
			log.info("开始加入自动执行计划");
			try
			{
				log.debug("old calculate time:" + oldSettingInfo.getCalculateDate());
				log.debug("new calculate time:" + info.getCalculateDate());
				if (!isEquals(oldSettingInfo.getCalculateDate(), info.getCalculateDate()))
				{
					Scheduler scheduler = Scheduler.getInstance();

					AutoInterestSettlementTask task = new AutoInterestSettlementTask(info.getID());

					task.setRunDateTime(info.getCalculateDate());

					scheduler.addTask(task);

					log.info("成功加入");
				}
				else
				{
					log.info("修改的自动结息计划计算时间未变");
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("修改的自动结息计划无法加入自动执行计划！" + e.getMessage());
			}

			log.info("结束加入自动执行计划");
		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return result;
	}

	public long delete(InterestFeeSettingInfo info) throws IException
	{
		long result = -1;
		boolean bFlag = false;

		Sett_InterestFeeSettingDAO transInterestFeeDAO = null;

		Connection conn = null;

		try
		{
			conn = this.getConnection();

			transInterestFeeDAO = new Sett_InterestFeeSettingDAO(conn);

			result = transInterestFeeDAO.delete(info.getID());

			//save successed,to save details
			if (result == info.getID())
			{
				bFlag = this.deleteAllInterestFeeSettingDetailInfoForInternal(info.getID());
			}
		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return result;
	}

	/**
	 * 执行自动结息操作
	 * @param info
	 * @return boolean
	 * @throws IException
	 */
	public boolean execute(InterestFeeSettingInfo info) throws IException
	{
		log.debug("========开始执行自动结息操作===========");
		boolean bFlag = false;

		Sett_InterestFeeSettingDetailDAO settingDetailDAO = null;

		Sett_InterestFeeSettingDAO settingDAO = null;

		Connection conn = null;

		try
		{
			conn = this.getConnection();
			settingDAO = new Sett_InterestFeeSettingDAO(conn);
			settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conn);

			log.debug("=========查找所有设置的账户信息==========");
			Collection collTemp = this.findAllInterestFeeSettingDetailBySettingIDForInternal(info.getID());

			if (collTemp != null)
			{
				Iterator itTemp = collTemp.iterator();

				if (itTemp != null)
				{
					Timestamp currentDate = Env.getSystemDate(conn, info.getOfficeID(), info.getCurrencyID());

					if (this.isEquals(info.getExecuteDate(), currentDate))
					{
						//结息日等于当前系统开机时间的不算匡算
						if (!info.getInterestDate().after(currentDate))
						{
							log.info("结息日早于等于当前系统开机时间，可以执行结息");

							InterestSettlement interestSettlement = new InterestSettlement();
							sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conn);
							InterestOperation interestOperation = new InterestOperation(conn);
							InterestBatch interestBatch = new InterestBatch(conn);

							InterestSettmentInfo settmentInfo = new InterestSettmentInfo();
							settmentInfo.setOfficeID(info.getOfficeID());
							settmentInfo.setCurrencyID(info.getCurrencyID());
							//设置批次号
							settmentInfo.setBatchNo(this.getBatchNo(conn, info.getOfficeID(), info.getCurrencyID()));
							//设置结息执行日期
							settmentInfo.setExecuteDate(info.getExecuteDate());
							//设置操作类型
							settmentInfo.setOperationType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);

							while (itTemp.hasNext())
							{
								InterestFeeSettingDetailInfo settingDetailInfo =
									(InterestFeeSettingDetailInfo) itTemp.next();

								log.debug(
									"当前处理的记录id:"
										+ settingDetailInfo.getID()
										+ "  AccountID:"
										+ settingDetailInfo.getAccountID()
										+ " SubAccountID:"
										+ settingDetailInfo.getSubAccountID());

								if (settingDetailInfo.getIsSuccess() == SETTConstant.BooleanValue.ISTRUE)
								{
									//已经结过息的账户，不再处理
									log.debug("当前设置已经结过息，不再处理");
									continue;
								}

								if (settingDetailInfo.getIsSave() != SETTConstant.BooleanValue.ISTRUE
									&& settingDetailInfo.getIsKeepAccount() != SETTConstant.BooleanValue.ISTRUE)
								{
									//已经结过息的账户，不再处理
									log.debug("当前设置不保存、不记账，不再处理");
									continue;
								}

								log.debug("=============算息==============");
								//算息
								InterestQueryResultInfo calcResultInfo =
									interestSettlement.getInterestInfo(
										"",
										0,
										settingDetailInfo.getContractNo(),
										settingDetailInfo.getLoanNotesNo(),
										settingDetailInfo.getSubAccountID(),
										settingDetailInfo.getAccountID(),
										settingDetailInfo.getAccountTypeID(),
										settingDetailInfo.getOfficeID(),
										settingDetailInfo.getCurrencyID(),
										info.getInterestDate(),
										currentDate,
										settingDetailInfo.getInterestType(),
										interestOperation,
										interestBatch,
										transDetailDAO);

								if (calcResultInfo != null)
								{
									//保存起息日
									settingDetailInfo.setInterestStartDate(calcResultInfo.getStartDate());
									//保存终息日
									settingDetailInfo.setInterestEndDate(calcResultInfo.getEndDate());
									//保存利息
									settingDetailInfo.setInterest(calcResultInfo.getInterest());
									//保存计息天数
									settingDetailInfo.setInterestDays(calcResultInfo.getDays());
									//保存计息金额
									settingDetailInfo.setBaseBalance(calcResultInfo.getBalance());
									//保存执行利率
									settingDetailInfo.setRate(calcResultInfo.getInterestRate());
									//保存精确的利息值
									settingDetailInfo.setPecisionInterest(calcResultInfo.getPecisionInterest());
									//保存利息
									settingDetailInfo.setInterest(calcResultInfo.getInterest());
									//保存协定存款计息金额
									settingDetailInfo.setNegotiateBalance(calcResultInfo.getNegotiateBalance());
									//保存协定利率
									settingDetailInfo.setNegotiateRate(calcResultInfo.getNegotiateInterestRate());
									//保存精确的协定利息值
									settingDetailInfo.setNegotiatePecisionInterest(
										calcResultInfo.getNegotiatePecisionInterest());
									//保存协定利息值
									settingDetailInfo.setNegotiateInterest(calcResultInfo.getNegotiateInterest());
									//保存利息税率
									settingDetailInfo.setInterestTaxRate(calcResultInfo.getInterestTaxRate());
									//保存利息税
									settingDetailInfo.setInterestTax(calcResultInfo.getInterestTaxCharge());

									//结息
									log.info("===============结息==============");

									Vector vTemp = new Vector(1);
									vTemp.add(calcResultInfo);

									//结息时使用此对象的该属性设置结息交易记录的关联条件id
									settmentInfo.setTransInterestFeeID(settingDetailInfo.getID());
									settmentInfo.setIsSave(settingDetailInfo.getIsSave());
									settmentInfo.setIsKeepAccount(settingDetailInfo.getIsKeepAccount());
									
									//结息操作使用独立的事务
									boolean bTransactionFlag = false;
									Connection connForBalance = Database.getConnection();
									connForBalance.setAutoCommit(false);
									try
									{
										interestSettlement.balanceInterest(connForBalance, vTemp, settmentInfo);
										settingDetailInfo.setIsSuccess(SETTConstant.BooleanValue.ISTRUE);
										settingDetailInfo.setFaultReason("");
										
										bTransactionFlag = true;
									}
									catch (Exception e)
									{
										log.error("结息失败");
										e.printStackTrace();
										//结息失败，保存失败原因，继续后续处理
										String strFaultReason = IExceptionMessage.getExceptionMSG(e);
										if (strFaultReason == null || "".equals(strFaultReason.trim()))
										{
											strFaultReason = "发生未知错误";
										}
										settingDetailInfo.setIsSuccess(SETTConstant.BooleanValue.ISFALSE);
										settingDetailInfo.setFaultReason(strFaultReason);
										
										bTransactionFlag = false;
									}
									finally
									{
										if(bTransactionFlag)
										{
											log.debug("结息成功，提交事务");
											connForBalance.commit();
										}
										else
										{
											log.debug("结息失败，回滚事务");											
											connForBalance.rollback();											
										}
										
										connForBalance.close();
									}
								}
								else
								{
									log.debug("无算息结果");
								}

								//回写设置表
								log.debug("保存处理结果");
								settingDetailDAO.update(settingDetailInfo);

							} //end while

							log.debug("=========更新设置记录的执行状态为已执行==========");
							settingDAO.updateIsExecuteStatus(info.getID(), SETTConstant.BooleanValue.ISTRUE);
						}
						else
						{
							log.info("结息日晚于当前系统开机时间，不执行结息操作，开始保存错误原因");
							//结息时间晚于当前业务系统时间
							//结息失败，保存失败原因
							while (itTemp.hasNext())
							{
								InterestFeeSettingDetailInfo settingDetailInfo =
									(InterestFeeSettingDetailInfo) itTemp.next();

								log.debug(
									"当前处理的记录id:"
										+ settingDetailInfo.getID()
										+ "  AccountID:"
										+ settingDetailInfo.getAccountID()
										+ " SubAccountID:"
										+ settingDetailInfo.getSubAccountID());

								if (settingDetailInfo.getIsSuccess() == SETTConstant.BooleanValue.ISTRUE)
								{
									//已经结过息的账户，不再处理
									log.debug("当前设置已经结过息，不再处理");
									continue;
								}

								if (settingDetailInfo.getIsSave() != SETTConstant.BooleanValue.ISTRUE
									&& settingDetailInfo.getIsKeepAccount() != SETTConstant.BooleanValue.ISTRUE)
								{
									//已经结过息的账户，不再处理
									log.debug("当前设置不保存、不记账，不再处理");
									continue;
								}

								settingDetailInfo.setIsSuccess(SETTConstant.BooleanValue.ISFALSE);
								settingDetailInfo.setFaultReason("结息时间未到");

								settingDetailDAO.update(settingDetailInfo);
							}
						} //end else if
					}
					else
					{
						log.info("自动结息设置的执行日期不等于当前开机日期，不执行结息操作，开始保存错误原因");
						//结息时间晚于当前业务系统时间
						//结息失败，保存失败原因
						while (itTemp.hasNext())
						{
							InterestFeeSettingDetailInfo settingDetailInfo =
								(InterestFeeSettingDetailInfo) itTemp.next();

							log.debug(
								"当前处理的记录id:"
									+ settingDetailInfo.getID()
									+ "  AccountID:"
									+ settingDetailInfo.getAccountID()
									+ " SubAccountID:"
									+ settingDetailInfo.getSubAccountID());

							if (settingDetailInfo.getIsSuccess() == SETTConstant.BooleanValue.ISTRUE)
							{
								//已经结过息的账户，不再处理
								log.debug("当前设置已经结过息，不再处理");
								continue;
							}

							if (settingDetailInfo.getIsSave() != SETTConstant.BooleanValue.ISTRUE
								&& settingDetailInfo.getIsKeepAccount() != SETTConstant.BooleanValue.ISTRUE)
							{
								//已经结过息的账户，不再处理
								log.debug("当前设置不保存、不记账，不再处理");
								continue;
							}

							settingDetailInfo.setIsSuccess(SETTConstant.BooleanValue.ISFALSE);
							settingDetailInfo.setFaultReason("自动结息设置的执行日期不等于当前开机日期");

							settingDetailDAO.update(settingDetailInfo);
						}
					}
				}
				else
				{
					throw new IException("没有相应的详细设置信息！");
				}
			}

			bFlag = true;
		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return bFlag;
	}

	/**
	 * 获得批此号
	 * @param conn
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return long
	 * @throws Exception
	 */
	private long getBatchNo(Connection conn, long lOfficeID, long lCurrencyID) throws Exception
	{
		Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();

		return dao.getNextBatchNo(conn, lOfficeID, lCurrencyID);
	}

	/**
	 * 批量更新详细设置的是否保存和是否记账的状态
	 * 
	 * @param lSelectedRecords 总的被选定的所有记录的id
	 * @param lSelectedToIsSave 被选定要保存的所有记录的id
	 * @param lSelectedToIsKeepAccount 被选定要记账的所有记录的id 
	 * @return boolean
	 * @throws IException
	 */
	public boolean updateSettingDetailStatus(
		long[] lSelectedRecords,
		long[] lSelectedToIsSave,
		long[] lSelectedToIsKeepAccount)
		throws IException
	{
		boolean bFlag = true;

		Sett_InterestFeeSettingDetailDAO settingDetailDAO = null;

		Connection conn = null;

		//选定不保存的所有记录的id
		long[] lSelectedToDontSave = null;
		//选定不记账的所有记录的id
		long[] lSelectedToDontKeepAccount = null;

		if (lSelectedRecords == null || lSelectedRecords.length == 0)
		{
			return false;
		}

		try
		{
			Arrays.sort(lSelectedRecords);
			//得到选定不保存的所有记录的id
			if (lSelectedToIsSave != null && lSelectedToIsSave.length > 0)
			{
				Arrays.sort(lSelectedToIsSave);
				if (lSelectedRecords.length - lSelectedToIsSave.length > 0)
				{
					lSelectedToDontSave = new long[lSelectedRecords.length - lSelectedToIsSave.length];
					int index = 0;
					for (int i = 0; i < lSelectedRecords.length; i++)
					{
						if (Arrays.binarySearch(lSelectedToIsSave, lSelectedRecords[i]) < 0)
						{
							lSelectedToDontSave[index] = lSelectedRecords[i];
							index++;
						}
					}
				}
			}
			else
			{
				lSelectedToDontSave = lSelectedRecords;
			}

			//得到选定不记账的所有记录的id
			if (lSelectedToIsKeepAccount != null && lSelectedToIsKeepAccount.length > 0)
			{
				Arrays.sort(lSelectedToIsKeepAccount);
				if (lSelectedRecords.length - lSelectedToIsKeepAccount.length > 0)
				{
					lSelectedToDontKeepAccount = new long[lSelectedRecords.length - lSelectedToIsKeepAccount.length];

					int index = 0;
					for (int i = 0; i < lSelectedRecords.length; i++)
					{
						if (Arrays.binarySearch(lSelectedToIsKeepAccount, lSelectedRecords[i]) < 0)
						{
							lSelectedToDontKeepAccount[index] = lSelectedRecords[i];
							index++;
						}
					}
				}
			}
			else
			{
				lSelectedToDontKeepAccount = lSelectedRecords;
			}

			conn = this.getConnection();
			//校验重付设置
			//锁定表

			//更新记录			
			settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conn);

			//更新保存的设置记录
			log.debug("=============更新保存的设置记录============");
			if (lSelectedToIsSave != null && lSelectedToIsSave.length > 0)
			{
				bFlag = settingDetailDAO.updateIsSaveStatus(lSelectedToIsSave, SETTConstant.BooleanValue.ISTRUE);
			}
			log.debug("=============更新不保存的设置记录============");
			//更新不保存的设置记录
			if (bFlag && lSelectedToDontSave != null && lSelectedToDontSave.length > 0)
			{
				bFlag = settingDetailDAO.updateIsSaveStatus(lSelectedToDontSave, SETTConstant.BooleanValue.ISFALSE);
			}
			log.debug("=============更新记账的设置记录============");
			//更新记账的设置记录
			if (bFlag && lSelectedToIsKeepAccount != null && lSelectedToIsKeepAccount.length > 0)
			{
				bFlag =
					settingDetailDAO.updateIsKeepAccountStatus(
						lSelectedToIsKeepAccount,
						SETTConstant.BooleanValue.ISTRUE);
			}
			log.debug("=============更新不记账的设置记录============");
			//更新不记账的设置记录
			if (bFlag && lSelectedToDontKeepAccount != null && lSelectedToDontKeepAccount.length > 0)
			{
				bFlag =
					settingDetailDAO.updateIsKeepAccountStatus(
						lSelectedToDontKeepAccount,
						SETTConstant.BooleanValue.ISFALSE);
			}
		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return bFlag;
	}

	public InterestFeeSettingInfo findByID(long lID) throws IException
	{
		InterestFeeSettingInfo result = null;

		boolean bFlag = false;

		Sett_InterestFeeSettingDAO interestFeeSettingDAO = null;

		Connection conn = null;

		try
		{
			conn = this.getConnection();

			interestFeeSettingDAO = new Sett_InterestFeeSettingDAO(conn);

			result = interestFeeSettingDAO.findByID(lID);

			bFlag = true;
		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return result;
	}

	public Collection findByCondition(InterestFeeSettingInfo searchInfo, int orderByType, boolean isDesc)
		throws IException
	{
		Collection result = null;

		boolean bFlag = false;

		Sett_InterestFeeSettingDAO transInterestFeeDAO = null;

		Connection conn = null;

		try
		{
			conn = this.getConnection();

			transInterestFeeDAO = new Sett_InterestFeeSettingDAO(conn);

			result = transInterestFeeDAO.findByConditions(searchInfo, orderByType, isDesc);

			bFlag = true;
		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return result;
	}

	/**
	 * 查询指定设置id的所有结息设置信息，该方法类维护事务，供外部对象使用
	 * @param lID
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findAllInterestFeeSettingDetailByCondition(QueryInterestFeeSettingDetailInfo searchInfo)
		throws IException
	{
		Collection result = null;

		Connection conn = null;

		boolean bFlag = false;

		try
		{
			conn = this.getConnection();

			Sett_InterestFeeSettingDetailDAO settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conn);

			result = settingDetailDAO.findByConditions(searchInfo);

			bFlag = true;
		}
		catch (IException e)
		{
			bFlag = false;
			throw e;
		}
		catch (Exception e)
		{
			bFlag = false;
			e.printStackTrace();
		}
		finally
		{
			if (bFlag)
			{
				this.commit(conn);
			}
			else
			{
				this.rollback(conn);
			}
			
			this.clearup(conn);
		}

		return result;
	}

	/**
	 * 创建交易明细表的详细信息，方法内不维护事务，此方法供对象内部使用
	 * @param info
	 * @return boolean
	 * @throws Exception
	 */
	private boolean createAllInterestFeeSettingDetailForInternal(InterestFeeSettingInfo info) throws Exception
	{
		log.debug("=============开始保存detail==============");

		boolean bFlag = false;

		Connection conn = getConnection();

		Sett_TransInterestSettlementDAO transInterestSettlementDAO = new Sett_TransInterestSettlementDAO();

		Sett_InterestFeeSettingDetailDAO settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conn);

		//构造设置详细信息对象
		InterestFeeSettingDetailInfo settingDetailInfo = new InterestFeeSettingDetailInfo();

		settingDetailInfo.setOfficeID(info.getOfficeID());
		settingDetailInfo.setCurrencyID(info.getCurrencyID());
		settingDetailInfo.setInterestType(info.getInterestType());
		settingDetailInfo.setOperationType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
		settingDetailInfo.setIsSave(Constant.YesOrNo.YES);
		settingDetailInfo.setIsKeepAccount(Constant.YesOrNo.YES);
		settingDetailInfo.setInterestFeeSettingID(info.getID());
		settingDetailInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);

		//勾账查询条件
		InterestQueryInfo searchInfo = new InterestQueryInfo();
		searchInfo.setOfficeID(info.getOfficeID());
		searchInfo.setCurrencyID(info.getCurrencyID());
		//起始账户号
		searchInfo.setAccountIDFromCtrl(info.getAccountNoStart());
		//终止账户号
		searchInfo.setAccountIDToCtrl(info.getAccountNoEnd());
		//起始合同号
		searchInfo.setContractIDFromCtrl(info.getContractNoStart());
		//终止合同号
		searchInfo.setContractIDToCtrl(info.getContractNoEnd());
		//起始放款通知单号
		searchInfo.setPayFormIDFromCtrl(info.getLoanNotesNoStart());
		//终止放款通知单号
		searchInfo.setPayFormIDToCtrl(info.getLoanNotesNoEnd());
		//贷款类型
		searchInfo.setLoanType(info.getLoanTypeID());
		//贷款期限
		searchInfo.setLoanTerm(info.getLoanIntervalNumMonth());
		//年期
		searchInfo.setYearTerm(info.getLoanIntervalNumYear());
		//委托人
		searchInfo.setConsignID(info.getConsignClientID());
		//结息日
		searchInfo.setClearInterest(info.getInterestDate());
		//是否滤空
		searchInfo.setIsClearNull(info.getIsClear());

		Collection resultColl =
			transInterestSettlementDAO.selectSettlementRecords(conn, searchInfo, info.getInterestType());

		if (resultColl == null || resultColl.size() == 0)
		{
			throw new IException("没有符合条件的账户！");
		}
		else
		{
			Iterator itTemp = resultColl.iterator();

			InterestSettlement interestSettlement = new InterestSettlement();
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conn);
			InterestOperation interestOperation = new InterestOperation(conn);
			InterestBatch interestBatch = new InterestBatch(conn);

			Timestamp dtSystem = Env.getSystemDate(conn, info.getOfficeID(), info.getCurrencyID());

			while (itTemp.hasNext())
			{
				InterestQueryResultInfo resultInfo = (InterestQueryResultInfo) itTemp.next();

				settingDetailInfo.setAccountID(resultInfo.getAccountID());
				settingDetailInfo.setSubAccountID(resultInfo.getSubAccountID());

				if (this.verifyIsRepeatSetting(settingDetailInfo, info.getInterestDate()))
				{
					throw new IException(
						"账户NO：'" + resultInfo.getAccountNo() + "' 子账户ID：'" + resultInfo.getSubAccountID() + "'已经设置！");
				}

				log.debug("=============算息==============");
				//算息
				InterestQueryResultInfo calcResultInfo =
					interestSettlement.getInterestInfo(
						"",
						0,
						"",
						"",
						settingDetailInfo.getSubAccountID(),
						settingDetailInfo.getAccountID(),
						resultInfo.getAccountTypeID(),
						settingDetailInfo.getOfficeID(),
						settingDetailInfo.getCurrencyID(),
						info.getInterestDate(),
						dtSystem,
						settingDetailInfo.getInterestType(),
						interestOperation,
						interestBatch,
						transDetailDAO);

				if (calcResultInfo != null)
				{
					//保存起息日
					settingDetailInfo.setInterestStartDate(calcResultInfo.getStartDate());
					//保存终息日
					settingDetailInfo.setInterestEndDate(calcResultInfo.getEndDate());
					//保存利息
					settingDetailInfo.setInterest(calcResultInfo.getInterest());
					//保存计息天数
					settingDetailInfo.setInterestDays(calcResultInfo.getDays());
					//保存计息金额
					settingDetailInfo.setBaseBalance(calcResultInfo.getBalance());
					//保存执行利率
					settingDetailInfo.setRate(calcResultInfo.getInterestRate());
					//保存精确的利息值
					settingDetailInfo.setPecisionInterest(calcResultInfo.getPecisionInterest());
					//保存利息
					double dTemp = calcResultInfo.getInterest();
					if (settingDetailInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						dTemp = calcResultInfo.getInterest();
					}
					else if (settingDetailInfo.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
					{
						dTemp = calcResultInfo.getHandlingCharge();
					}
					else if (settingDetailInfo.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
					{
						dTemp = calcResultInfo.getAssuranceCharge();
					}
					settingDetailInfo.setInterest(dTemp);
					//保存协定存款计息金额
					settingDetailInfo.setNegotiateBalance(calcResultInfo.getNegotiateBalance());
					//保存协定利率
					settingDetailInfo.setNegotiateRate(calcResultInfo.getNegotiateInterestRate());
					//保存精确的协定利息值
					settingDetailInfo.setNegotiatePecisionInterest(calcResultInfo.getNegotiatePecisionInterest());
					//保存协定利息值
					settingDetailInfo.setNegotiateInterest(calcResultInfo.getNegotiateInterest());
					//保存利息税率
					settingDetailInfo.setInterestTaxRate(calcResultInfo.getInterestTaxRate());
					//保存利息税
					settingDetailInfo.setInterestTax(calcResultInfo.getInterestTaxCharge());
				}
				else
				{
					System.out.println(
						"Calculate Interest failed. AccountID:"
							+ settingDetailInfo.getSubAccountID()
							+ " SubAccountID:"
							+ settingDetailInfo.getAccountID());
				}

				//新增设置详细信息
				try
				{
					long lTemp = settingDetailDAO.add(settingDetailInfo);
					log.debug("=============保存detail ID " + lTemp + "==============");
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw e;
				}
			}

			bFlag = true;
		}

		return bFlag;
	}

	/**
	 * 交易当前设置信息是否重复
	 * @param settingDetailInfo
	 * @param interestDate 结息日期
	 * @return boolean true 重复;false 不重复
	 */
	private boolean verifyIsRepeatSetting(InterestFeeSettingDetailInfo settingDetailInfo, Timestamp interestDate)
		throws Exception
	{
		boolean result = true;

		Connection conn = getConnection();

		Sett_InterestFeeSettingDetailDAO settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conn);

		//构造设置详细信息对象
		QueryInterestFeeSettingDetailInfo searchInfo = new QueryInterestFeeSettingDetailInfo();

		searchInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);
		searchInfo.setAccountID(settingDetailInfo.getAccountID());
		searchInfo.setSubAccountID(settingDetailInfo.getSubAccountID());
		searchInfo.setInterestType(settingDetailInfo.getInterestType());
		//既不记账，又不保存的记录不作操作，因此不作为重复设置。记账必须保存，因此只需判断是否保存即可
		searchInfo.setIsSave(SETTConstant.BooleanValue.ISTRUE);
		//结息日期早于等于当前结息日的算重复设置
		searchInfo.setInterestDate(interestDate);

		Collection collTemp = settingDetailDAO.findByConditions(searchInfo);

		if (collTemp == null || collTemp.size() == 0)
		{
			result = false;
		}

		return result;
	}

	/**
	 * 查询指定设置id的所有结息设置信息，该方法类不维护事务，供对象内部使用
	 * @param lID
	 * @return Collection
	 * @throws Exception
	 */
	private Collection findAllInterestFeeSettingDetailBySettingIDForInternal(long lID) throws Exception
	{
		if (lID == -1)
			return null;

		Collection result = null;

		Connection conn = this.getConnection();

		Sett_InterestFeeSettingDetailDAO settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conn);

		QueryInterestFeeSettingDetailInfo searchInfo = new QueryInterestFeeSettingDetailInfo();
		searchInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);
		searchInfo.setInterestFeeSettingID(lID);

		result = settingDetailDAO.findByConditions(searchInfo);

		return result;
	}

	/**
	 * 删除指定设置id的所有结息设置信息，该方法类不维护事务，供对象内部使用
	 * @param lID
	 * @return Collection
	 * @throws Exception
	 */
	private boolean deleteAllInterestFeeSettingDetailInfoForInternal(long lID) throws Exception
	{
		if (lID == -1)
			return false;

		Connection conn = this.getConnection();

		Sett_InterestFeeSettingDetailDAO settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conn);

		Collection collTemp = this.findAllInterestFeeSettingDetailBySettingIDForInternal(lID);

		if (collTemp != null)
		{
			Iterator itTemp = collTemp.iterator();

			long lTemp = -1;

			while (itTemp.hasNext())
			{
				InterestFeeSettingDetailInfo tempInfo = (InterestFeeSettingDetailInfo) itTemp.next();

				lTemp = settingDetailDAO.delete(tempInfo.getID());

				if (lTemp != tempInfo.getID())
					throw new Exception("删除详细信息失败！");
			}
		}

		return true;
	}

	public static AutoInterestSettlementTask[] getAllAutoTask()
	{
		AutoInterestSettlementTask[] tasks = null;

		InterestFeeSetting bizObject = new InterestFeeSetting();

		InterestFeeSettingInfo searchInfo = new InterestFeeSettingInfo();
		searchInfo.setIsExecute(SETTConstant.BooleanValue.ISFALSE);
		searchInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);
		try
		{
			Collection collTemp = bizObject.findByCondition(searchInfo, -1, false);

			if (collTemp != null)
			{
				Iterator itTemp = collTemp.iterator();
				if (itTemp != null)
				{
					tasks = new AutoInterestSettlementTask[collTemp.size()];

					for (int i = 0; itTemp.hasNext(); i++)
					{
						InterestFeeSettingInfo settingInfo = (InterestFeeSettingInfo) itTemp.next();

						AutoInterestSettlementTask taskTemp = new AutoInterestSettlementTask(settingInfo.getID());

						taskTemp.setRunDateTime(settingInfo.getCalculateDate());

						tasks[i] = taskTemp;
					}
				}
			}
			tasks = (tasks != null && tasks.length > 0) ? tasks : null;
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
		return tasks;
	}

	private boolean isEquals(Timestamp tsOne, Timestamp tsTwo)
	{
		boolean bFlag = (tsOne.getYear() == tsTwo.getYear());

		bFlag &= (tsOne.getMonth() == tsTwo.getMonth());

		bFlag &= (tsOne.getDay() == tsTwo.getDay());

		bFlag &= (tsOne.getHours() == tsTwo.getHours());

		bFlag &= (tsOne.getMinutes() == tsTwo.getMinutes());

		bFlag &= (tsOne.getSeconds() == tsTwo.getSeconds());

		return bFlag;
	}
}
