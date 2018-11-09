/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-7
 */
package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ActualAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ActualBalanceInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.conf.*;
import com.iss.itreasury.treasuryplan.etl.extract.util.*;
import com.iss.itreasury.treasuryplan.util.TPConstant;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 * 针对结算模块数据访问的方法集合，不是针对某一个具体表的操作 此类操作的数据库连接为长连接，通过方法:
 * establishConnectionByModuleID(long moduleID) 建立连接 通过finalize()方法释放连接
 * 在以上两个函数之间的本对象的方法使用同一个数据库连接
 *  
 */
public class Sett_ExtractorUtilDAO extends AbstractExtractorUtilDAO {   

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	private boolean isAccessSubAccount = false;//是否取账户余额
	
	public Sett_ExtractorUtilDAO() throws Exception {
		super(Constant.ModuleType.SETTLEMENT);
	}

	/**
	 * 根据参数条件获取账户交易明细
	 */
	public void extractDataFromTransAccountDetailToActualAmount(long officeID,
			long currencyID, Timestamp startDate, Timestamp endDate)
			throws Exception 
	{
		Connection tpConn = null;
		try
		{
			System.out.println("-------------开始账户发生额数据抽取----------------");

			/**
			 * 建立资金计划使用的数据库连接(可能和原数据库不同)
			 */
			tpConn = establishConnectionByModuleID(TPConstant.ModuleType.PLAN);

			tpConn.setAutoCommit(false);
			Trea_ActualAmountDAO actualAmountDAO = new Trea_ActualAmountDAO(
					tpConn);

			String strSQL1 = "select a.ntransdirection,a.DTEXECUTE,a.NTRANSACCOUNTID, a.NSUBACCOUNTID,a.MAMOUNT,a.STRANSNO,a.NTRANSACTIONTYPEID,a.SABSTRACT,"
					+ "c.SACCOUNTNO,c.NACCOUNTTYPEID,f.SCODE clientcode ,d.scode,e.SCONTRACTCODE"
					+ " from sett_transaccountdetail a,  sett_subaccount b, sett_account c , LOAN_PAYFORM d, LOAN_CONTRACTFORM e,client f"
					+ " where  a.DTEXECUTE >= ? and a.DTEXECUTE <= ?  and a.NSUBACCOUNTID = b.id  and b.naccountid = c.id  and b.al_nloannoteid=d.id(+) and d.nContractID = e.id(+)"
					+ "and a.nstatusid > 0  and a.NOFFICEID = "
					+ officeID
					+ " and a.NCURRENCYID = "
					+ currencyID
					+ " and f.id = c.nclientid";

			PreparedStatement localPS = prepareStatement(strSQL1);
			localPS.setTimestamp(1, startDate);
            localPS.setTimestamp(2, endDate);
			ResultSet localRS = executeQuery();
			System.out.println("strSQL1 ---> "+strSQL1);
			System.out.println("startDate ---> "+startDate);
			System.out.println("endDate ---> "+endDate);
			while (localRS.next()) 
			{	
				long NTRANSACTIONTYPEID = localRS.getLong("NTRANSACTIONTYPEID");
				String transNo = localRS.getString("STRANSNO");
				System.out.println("STRANSNO========================"+transNo);
				long NTRANSACCOUNTID = localRS.getLong("NTRANSACCOUNTID");
				long NACCOUNTTYPEID = localRS.getLong("NACCOUNTTYPEID");
				long transdirectionid = localRS.getLong("ntransdirection");
				double amount = localRS.getDouble("MAMOUNT");			
				Timestamp transactionDate = localRS.getTimestamp("DTEXECUTE");
                
/*					if ((SETTConstant.TransactionType.isCurrentTransaction(NTRANSACTIONTYPEID) 
							|| SETTConstant.TransactionType.isFixedTransaction(NTRANSACTIONTYPEID) ) && !isBankTADI(NTRANSACTIONTYPEID, transNo,
							NTRANSACCOUNTID))
						continue;
*/
				
					sett_GLEntryDAO entryDAO = new sett_GLEntryDAO(tpConn);	
					
					/**
					 * 如果是活期账户,且
					 * 银行收款、银行付款、一付多收、总账类、委托存款、保证金存款、特殊业务、
					 * 交易费用处理、定期开立、定期支取、定期续存、通知开立、通知支取
					 * 
					 */
					
					if(NACCOUNTTYPEID != SETTConstant.AccountGroupType.CONSIGN) //add by rxie
					{
						System.out.println("不是委托贷款账户");
						//不是委托存款账户的才这样处理
						if(SETTConstant.AccountType.isCurrentAccountType(NACCOUNTTYPEID))
						{
							System.out.println("是活期账户");
							if(NTRANSACTIONTYPEID == SETTConstant.TransactionType.BANKRECEIVE
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.BANKRECEIVE_GATHERING
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.BANKPAY
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.GENERALLEDGER
									//|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.INTERNALVIREMENT
									//|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.CONSIGNSAVE
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.CAUTIONMONEYSAVE
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.SPECIALOPERATION
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.TRANSFEE
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.DISCOUNTGRANT
									|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.DISCOUNTRECEIVE
									//|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.OPENFIXEDDEPOSIT
									//|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
									//|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER
									//|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW
									//|| NTRANSACTIONTYPEID == SETTConstant.TransactionType.OPENNOTIFYACCOUNT))
							)
							{					
								System.out.println("活期存款业务类型 " + NTRANSACTIONTYPEID + " ---> " + SETTConstant.TransactionType.getName(NTRANSACTIONTYPEID));
								if(!entryDAO.isInvolvedWithBankForTP(officeID,currencyID,transNo,amount,transdirectionid,NACCOUNTTYPEID))
								{
									System.out.println("分录表sett_glentry中不存在相同交易号、相同金额、借贷方向相反、科目号like '102%'的分录");
									System.out.println("跳出循环 transNo is ："+transNo);
									continue;//不涉及外部银行,继续
								}
								else
								{
									System.out.println("没跳出循环 transNo is ："+transNo);
								}
							}//一付多收需特殊处理
							else if (NTRANSACTIONTYPEID == SETTConstant.TransactionType.ONETOMULTI)
							{
								System.out.println("活期存款业务类型 " + NTRANSACTIONTYPEID + " ---> " + SETTConstant.TransactionType.getName(NTRANSACTIONTYPEID));
								if(!entryDAO.isInvolvedWithBankForTP(officeID,currencyID,transNo,0,transdirectionid,NACCOUNTTYPEID))
								{
									System.out.println("分录表sett_glentry中不存在相同交易号、相同金额、借贷方向相反、科目号like '102%'的分录");
									System.out.println("跳出循环 transNo is ："+transNo);
									continue;//不涉及外部银行,继续
								}
								else
								{
									//amount = entryDAO.getAmontOfOppositeForTP(officeID,currencyID,transNo,0,transdirectionid);
									System.out.println("没跳出循环 transNo is ："+transNo);
								}
							}
							else
							{
								System.out.println("跳出循环 transNo is ："+transNo);
								continue;
							}
							
						}
						else if(SETTConstant.AccountType.isFixAccountType(NACCOUNTTYPEID))//如果是定期账户
						{
							System.out.println("是定期账户");
							System.out.println("定期存款业务类型 " + NTRANSACTIONTYPEID + " ---> " + SETTConstant.TransactionType.getName(NTRANSACTIONTYPEID));
							if(!entryDAO.isInvolvedWithBankForTP(officeID,currencyID,transNo,amount,transdirectionid,NACCOUNTTYPEID))
							{
								System.out.println("分录表sett_glentry中不存在相同交易号、相同金额、借贷方向相反、科目号like '102%'的分录");
								System.out.println("跳出循环 transNo is ："+transNo);
								continue;//不涉及外部银行,继续
							}
							else
							{
								System.out.println("没跳出循环 transNo is ："+transNo);
							}
						}
						else if(SETTConstant.AccountType.isNotifyAccountType(NACCOUNTTYPEID))//如果是通知存款账户///////////add by fangguiquan
						{
							System.out.println("是通知存款账户");
							System.out.println("通知存款业务类型 " + NTRANSACTIONTYPEID + " ---> " + SETTConstant.TransactionType.getName(NTRANSACTIONTYPEID));
							if(!entryDAO.isInvolvedWithBankForTP(officeID,currencyID,transNo,amount,transdirectionid,NACCOUNTTYPEID))
							{
								System.out.println("分录表sett_glentry中不存在相同交易号、相同金额、借贷方向相反、科目号like '102%'的分录");
								System.out.println("跳出循环 transNo is ："+transNo);
								continue;//不涉及外部银行,继续
							}
							else
							{
								System.out.println("没跳出循环 transNo is ："+transNo);
							}
						}
						
					}
					
				System.out.println("开始执行业务操作 ：" + transNo);
				//strSQL1 += " and a.NOPPACCOUNTID < 0";
				ActualAmountInfo actualAmountInfo = new ActualAmountInfo();


				//long AL_NLOANNOTEID = localRS.getLong("AL_NLOANNOTEID");
				long NSUBACCOUNTID = localRS.getLong("NSUBACCOUNTID");
				AccountOperation accountOperation;
				String glSubjectCode = getSubjectCodeBySubAccountID(
						NSUBACCOUNTID, AccountOperation.SUBJECT_TYPE_ACCOUNT);


				//String clientCode =
				// NameRef.getClientCodeByAccountID(NTRANSACCOUNTID);
				String clientCode = localRS.getString("clientcode");
				String transTypeName = SETTConstant.TransactionType
						.getName(NTRANSACTIONTYPEID);
				if (clientCode != null)
					actualAmountInfo.setClientCode(clientCode);
				//String accountCode =
				// NameRef.getAccountNoByID(NTRANSACCOUNTID);
				String accountCode = localRS.getString("SACCOUNTNO");
				if (accountCode != null)
					actualAmountInfo.setAccountCode(accountCode);
				if (glSubjectCode != null)
					actualAmountInfo.setGlSubjectCode(glSubjectCode);
				if (transTypeName != null)
					actualAmountInfo.setTransactionName(transTypeName);

				String payFormCode = localRS.getString("scode");
				if (payFormCode != null)
					actualAmountInfo.setPayFormCode(payFormCode);

				String contractCode = localRS.getString("SCONTRACTCODE");
				if (contractCode != null)
					actualAmountInfo.setContractCode(contractCode);

				long accountTypeID = localRS.getLong("NACCOUNTTYPEID");
				long accountGroupType = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(accountTypeID);
				if (accountGroupType == SETTConstant.AccountGroupType.CURRENT
						|| accountGroupType == SETTConstant.AccountGroupType.FIXED
						|| accountGroupType == SETTConstant.AccountGroupType.NOTIFY) 
				{
					System.out.println("---------活期、定期、通知 Deposit:");
					if (transdirectionid == SETTConstant.DebitOrCredit.DEBIT) 
					{
						actualAmountInfo.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
						System.out.println("借 -> 减少");
					} else {
						actualAmountInfo.setAmountDirection(TPConstant.AmountDirection.PLUS);
						System.out.println("贷 -> 增加");
					}
				} else {//贷款账户
					System.out.println("---------贷款 Loan:");
					if (transdirectionid == SETTConstant.DebitOrCredit.DEBIT) {
						actualAmountInfo
								.setAmountDirection(TPConstant.AmountDirection.PLUS);
						System.out.println("借 -> 增加");
					} else {
						actualAmountInfo
								.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
						System.out.println("贷 -> 减少");
					}
				}

				actualAmountInfo.setOfficeID(officeID);
				actualAmountInfo.setCurrencyID(currencyID);
				actualAmountInfo.setExecuteDate(Env.getSystemDate());
				actualAmountInfo.setModuleID(TPConstant.ModuleType.SETTLEMENT);
				actualAmountInfo.setTransactionDate(transactionDate);

				actualAmountInfo.setActualAmount(amount);
				actualAmountInfo.setTransactionCode(transNo);
				actualAmountInfo.setRemark(localRS.getString("SABSTRACT"));
				actualAmountInfo.setAccountTypeId(accountTypeID);

				System.out.println("actualAmountInfo保存之前");
				System.out.println("	交易编号 = "+actualAmountInfo.getTransactionCode());
				System.out.println("	客户编号 = "+actualAmountInfo.getClientCode());
				System.out.println("	账户编号 = "+actualAmountInfo.getAccountCode());
				System.out.println("	科目编号 = "+actualAmountInfo.getGlSubjectCode());
				System.out.println("	账户类型 = "+actualAmountInfo.getAccountTypeId());
				System.out.println("	交易详细信息 = actualAmountInfo : \n "+actualAmountInfo );
				actualAmountDAO.add(actualAmountInfo);
				System.out.println("actualAmountInfo保存结束");
			}
			tpConn.commit();
			finalizeDAO();

			System.out.println("-------------结束账户发生额数据抽取----------------");

			System.out.println("-------------开始科目发生额数据抽取----------------");
			String strSQL2 = "select ssubjectcode,ntransactiontypeid,mamount,stransno,NTRANSDIRECTION,sabstract , dtexecute  from  sett_glentry where nstatusid>0 and dtexecute >= ? and dtexecute <= ? and ssubjectcode not in (select GlSubjectCode from Trea_ActualAmount where AccountTypeId>0 and ModuleID=1 and GlSubjectCode is not null)"
					+ "and nOfficeID = "
					+ officeID
					+ " and nCurrencyID = "
					+ currencyID;
			localPS = prepareStatement(strSQL2);
			localPS.setTimestamp(1, startDate);
            localPS.setTimestamp(2, endDate);
			localRS = executeQuery();

			while (localRS.next()) {
				ActualAmountInfo actualAmountInfo = new ActualAmountInfo();
				actualAmountInfo.setExecuteDate(Env.getSystemDate());
				long NTRANSDIRECTION = localRS.getLong("NTRANSDIRECTION");
				long NTRANSACTIONTYPEID = localRS.getLong("NTRANSACTIONTYPEID");
				String stransno = localRS.getString("stransno");
				String subjectCode = localRS.getString("ssubjectcode");
				String transTypeName = SETTConstant.TransactionType
						.getName(NTRANSACTIONTYPEID);
				long subjectType = getSubjectTypeBySubjectCode(subjectCode);
				double mamount = localRS.getDouble("mamount");
				String sabstract = localRS.getString("sabstract");
                Timestamp glTransactionDate = localRS.getTimestamp("dtexecute");

				if ((subjectType == SETTConstant.SubjectAttribute.ASSET || subjectType == SETTConstant.SubjectAttribute.PAYOUT)) {
					System.out.println("科目是资产类和收入类");
					if (NTRANSDIRECTION == SETTConstant.DebitOrCredit.DEBIT) {
						actualAmountInfo
								.setAmountDirection(TPConstant.AmountDirection.PLUS);
						System.out.println("借 -> 增加");
					} else {
						actualAmountInfo
								.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
						System.out.println("贷 -> 增加");
					}
				} else {
					System.out.println("科目是其他类型");
					if (NTRANSDIRECTION == SETTConstant.DebitOrCredit.DEBIT) {
						actualAmountInfo
								.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
						System.out.println("借 -> 减少");
					} else {
						actualAmountInfo
								.setAmountDirection(TPConstant.AmountDirection.PLUS);
						System.out.println("贷 -> 增加");
					}
				}
				if (subjectCode != null)
					actualAmountInfo.setGlSubjectCode(subjectCode);

				if (transTypeName != null)
					actualAmountInfo.setTransactionName(transTypeName);

				actualAmountInfo.setActualAmount(mamount);

				if (stransno != null)
					actualAmountInfo.setTransactionCode(stransno);

				if (sabstract != null)
					actualAmountInfo.setRemark(sabstract);

				actualAmountInfo.setOfficeID(officeID);
				actualAmountInfo.setCurrencyID(currencyID);
				actualAmountInfo.setExecuteDate(Env.getSystemDate());
				actualAmountInfo.setModuleID(TPConstant.ModuleType.SETTLEMENT);
				actualAmountInfo.setTransactionDate(glTransactionDate);
				
				System.out.println("actualAmountInfo保存之前");
				System.out.println("	交易编号 = "+actualAmountInfo.getTransactionCode());
				System.out.println("	科目编号 = "+actualAmountInfo.getGlSubjectCode());
				System.out.println("	交易详细信息 = actualAmountInfo : \n "+actualAmountInfo );
				actualAmountDAO.add(actualAmountInfo);
				System.out.println("actualAmountInfo保存结束");
			}
			tpConn.commit();
			finalizeDAO();
			tpConn.close();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (RemoteException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (IRollbackException e) {
			e.printStackTrace();
			throw e;
		} catch (IException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
			tpConn.close();
		}

	}

	/**
	 * @param accountParam
	 * @param
	 * @return @throws
	 */
	public String getSubjectCodeBySubAccountID(long subAccountID,
			int subjectType) throws RemoteException, IRollbackException,
			IException {
		AccountOperation accountOperation = new AccountOperation(transConn);
		String glSubjectCode = "";
		try {
			glSubjectCode = accountOperation.getSubjectBySubAccountID(
					subAccountID, subjectType);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return glSubjectCode;
	}

	private void closeRSAndPS() throws SQLException {
		transRS.close();
		transPS.close();
	}

	public long getSubjectTypeBySubjectCode(String subjectCode)
			throws ITreasuryDAOException, SQLException {
		String strSQL = "select NSUBJECTTYPE from SETT_VGLSUBJECTDEFINITION where SSUBJECTCODE = '"
				+ subjectCode + "'";
		long NSUBJECTTYPE;
		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			NSUBJECTTYPE = -1;
			if (localRS.next())
				NSUBJECTTYPE = localRS.getLong("NSUBJECTTYPE");
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return NSUBJECTTYPE;

	}

	/**
	 * 根据参数条件获取账户交易明细
	 * @param officeID
	 * @param currencyID
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public void extractDataFromActualBalanceToActualBalance(long officeID,long currencyID, Timestamp startDate, Timestamp endDate)	throws Exception 
	{

		String strStartDate = this.transferTimestampToTo_DateString(startDate);
        String strEndDate = this.transferTimestampToTo_DateString(endDate);
		System.out.println("-------------开始账户余额数据抽取----------------");
		Connection tpConn = null;
		try
		{
			/**
			 * 建立资金计划使用的数据库连接(可能和原数据库不同)
			 */
			tpConn = establishConnectionByModuleID(TPConstant.ModuleType.PLAN);
			tpConn.setAutoCommit(false);
			Trea_ActualBalanceDAO actualBalanceDAO = new Trea_ActualBalanceDAO(tpConn);

			String strSQL1 = "select a.nofficeid,a.ncurrencyid,a.naccounttypeid,c.scode,a.saccountno,d.nsubaccountid,d.mbalance,d.dtdate , f.scode as payformCode,g.SCONTRACTCODE \n"
					+ "from sett_account a,client c,sett_dailyaccountbalance d,sett_subaccount e,loan_payform f,LOAN_CONTRACTFORM g \n"
					+ "where d.dtdate >= to_date('"
					+ DataFormat.formatDate(startDate)
                    +"','yyyy-mm-dd')"
                    + "and  d.dtdate <= to_date('"
                    + DataFormat.formatDate(endDate)
                    +"','yyyy-mm-dd')"
					+ " and d.naccountid=a.id \n"
					+ "and a.nclientid=c.id \n"
					+ "and d.nsubaccountid = e.id \n"
					+ "and e.al_nloannoteid = f.id(+) \n"
					+ "and f.nContractID = g.id(+) \n"
					+ "and a.NOFFICEID = "
					+ officeID + " and a.NCURRENCYID = " + currencyID;

			prepareStatement(strSQL1);
			ResultSet localRS = executeQuery();

			int balanceNum = addActualBalance(startDate, endDate,actualBalanceDAO, localRS);

			finalizeDAO();

			if (balanceNum == 0) //节假日，即关机日不等于实际日期，取当前账户余额
			{
				isAccessSubAccount = true;
				
				strSQL1 = " select a.nofficeid,a.ncurrencyid,a.naccounttypeid,c.scode,a.saccountno,e.id,e.mbalance,f.scode as payformCode,g.SCONTRACTCODE  \n"
						+ " from sett_account a,client c,sett_subaccount e,loan_payform f,LOAN_CONTRACTFORM g  \n"
						+ " where  \n"
						+ " e.naccountid=a.id  \n"
						+ " and a.nclientid=c.id  \n"
						+ " and e.al_nloannoteid = f.id(+)  \n"
						+ " and f.nContractID = g.id(+)  \n"
						+ " and a.NOFFICEID = " + officeID
						+ " and a.NCURRENCYID = " + currencyID;
				
				prepareStatement(strSQL1);
				
				localRS = executeQuery();
				
				addActualBalance(startDate, endDate, actualBalanceDAO,localRS);
			}

			System.out.println("-------------结束账户余额数据抽取----------------");

			tpConn.commit();
			tpConn.close();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			tpConn.close();
			finalizeDAO();
		}

	}

	/**
	 * @param executeDate
	 * @param transDate
	 * @param actualBalanceDAO
	 * @param localRS
	 * @throws SQLException
	 * @throws IException
	 * @throws IRollbackException
	 * @throws Exception
	 */
	private int addActualBalance(Timestamp startDate, Timestamp endDate,
			Trea_ActualBalanceDAO actualBalanceDAO, ResultSet localRS)
			throws Exception {
		int i = 0;
		while (localRS.next()) {
			ActualBalanceInfo actualBalanceInfo = new ActualBalanceInfo();

			String clientCode = localRS.getString("scode");
			String accountNo = localRS.getString("saccountno");
			long accountTypeId = localRS.getLong("naccounttypeid");
			String payFormCode = localRS.getString("payformCode");
			String contractCode = localRS.getString("SCONTRACTCODE");
			
			Timestamp transactionDate = null;
			if( !isAccessSubAccount )
			{
				//从dailyAccountBalance中取余额
				transactionDate = localRS.getTimestamp("dtdate");
			}
			else
			{
				//从subAccount中取余额
				transactionDate = startDate;
				
				startDate = DataFormat.getNextDate(startDate,1);
			}
			
			
			if (clientCode != null)
				actualBalanceInfo.setClientCode(clientCode);

			if (accountNo != null)
				actualBalanceInfo.setAccountCode(accountNo);

			if (payFormCode != null)
				actualBalanceInfo.setPayFormCode(payFormCode);

			if (contractCode != null)
				actualBalanceInfo.setContractCode(contractCode);

			actualBalanceInfo.setOfficeID(localRS.getLong("nofficeid"));
			actualBalanceInfo.setCurrencyID(localRS.getLong("ncurrencyid"));
			actualBalanceInfo.setExecuteDate(Env.getSystemDate());
			actualBalanceInfo.setModuleID(TPConstant.ModuleType.SETTLEMENT);
			actualBalanceInfo.setTransactionDate(transactionDate);
			actualBalanceInfo.setActualBalance(localRS.getDouble("mbalance"));
			actualBalanceInfo.setAccountTypeId(localRS.getLong("naccounttypeid"));
			
			String glSubjectCode = getSubjectCodeBySubAccountID(
					localRS.getLong("NSUBACCOUNTID"), AccountOperation.SUBJECT_TYPE_ACCOUNT);
			System.out.println("Before Save actualBalanceInfo: " + glSubjectCode);
			if (glSubjectCode != null)
				actualBalanceInfo.setGlSubjectCode(glSubjectCode);
			
			actualBalanceDAO.add(actualBalanceInfo);
			System.out.println("Finish saving actualBalanceInfo: "+ actualBalanceInfo.getGlSubjectCode());
			i++;
		}
		return i;
	}

	public Collection getFixedDepositThatEndAtForcastDay(long officeID,
			long currencyID, Timestamp forcastStartDate, Timestamp forcastEndDate) throws Exception {
		ArrayList resList = new ArrayList();
		String strStartDate = transferTimestampToTo_DateString(forcastStartDate);
        String strEndDate = transferTimestampToTo_DateString(forcastEndDate);
		String strSQL = "select b.id,b.MBALANCE,b.AF_SDEPOSITNO, a.NCLIENTID,a.SACCOUNTNO,a.NACCOUNTTYPEID , b.af_dtend from sett_account a,sett_subaccount b \n"
				+ "where \n"
				+ "naccounttypeid=2 \n"
				+ "and a.nstatusid>0 \n"
				+ "and a.nOfficeID = "
				+ officeID
				+ " \n"
				+ "and a.nCurrencyID = "
				+ currencyID
				+ " \n"
				+ "and b.nstatusid>0 \n"
				+ "and b.af_dtend >= "
				+ strStartDate
                + "and b.af_dtend <= "
                + strEndDate
				+ " and a.id=b.naccountid";

		try {
			PreparedStatement localPS = prepareStatement(strSQL);
			//localPS.setTimestamp(1, forcastDate);
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				ForecastAmountInfo fAmountInfo = new ForecastAmountInfo();
				fAmountInfo.setSubAccountID(localRS.getLong("id"));
				//fAmountInfo.setBalance(localRS.getLong("MBALANCE"));
				//			fixedInfo.setAccountID()
				fAmountInfo.setContractCode(localRS.getString("AF_SDEPOSITNO"));
				fAmountInfo.setClientID(localRS.getLong("NCLIENTID"));
				String cCode = NameRef.getClientCodeByID(fAmountInfo
						.getClientID());
				if (cCode != null)
					fAmountInfo.setClientCode(cCode);
				fAmountInfo.setAccountCode(localRS.getString("SACCOUNTNO"));
				fAmountInfo.setAccountTypeId(localRS.getLong("NACCOUNTTYPEID"));
				//使用forecastAmount来传递定期自账户余额
				fAmountInfo.setForecastAmount(localRS.getLong("MBALANCE"));
                fAmountInfo.setAf_dtend(localRS.getTimestamp("af_dtend"));
                
				resList.add(fAmountInfo);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return resList;
	}

	/**
	 * 根据子账户ID获取同一个客户下特定账户类型的子账户ID SETTConstant.AccountType.CURRENTDEPOSIT
	 */
	public ForecastAmountInfo getFirstSubAccountIDUnderSameClientBySubAccountID(
			long subAccountID, long returnAccountType) throws Exception {
		String strSQL = "select sub.id as nsubaccountID, "
				+ " acc.saccountno, "
				+ " nvl(acc.naccounttypeid, "
				+ returnAccountType
				+ ") naccounttypeid ,"
				+ " cl.SCODE"
				+ " from sett_account acc, sett_subaccount sub, client cl \n"
				+ "where acc.nclientid = \n"
				+ "(  select c.id from client c, sett_account a ,sett_subaccount s \n"
				+ "where s.id = " + subAccountID
				+ " and s.naccountid = a.id and a.nclientid = c.id) \n"
				+ " and acc.NACCOUNTTYPEID = " + returnAccountType
				+ " and acc.id = sub.naccountid and cl.id = acc.nclientid";
		ForecastAmountInfo res = new ForecastAmountInfo();
		try {
			PreparedStatement localPS = prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) {
				res.setAccountCode(localRS.getString("saccountno"));
				res.setClientCode(localRS.getString("SCODE"));
				res.setAccountTypeId(localRS.getLong("naccounttypeid"));
				res.setSubAccountID(localRS.getLong("nsubaccountID"));
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return res;
	}

	/**
	 * 获取某个客户下某种账户类型的子账户ID
	 */
	public ForecastAmountInfo getFirstSubAccountByClientIDToForecastAmountInfo(
			long clientID, long accountTypeID) throws Exception {
		ForecastAmountInfo res = new ForecastAmountInfo();
		String strSQL = "select cl.scode,acc.saccountno,sub.id from (select id,naccountid from sett_subaccount "
				+ "where naccountid in (select id from sett_account  where nclientid = "
				+ clientID
				+ " and NACCOUNTTYPEID = "
				+ accountTypeID
				+ " and rownum = 1) "
				+ "and rownum = 1) sub,sett_account acc,client cl where sub.naccountid = acc.id and acc.nclientid=cl.id";
		try {
			PreparedStatement localPS = prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) {
				res.setAccountCode(localRS.getString("saccountno"));
				res.setClientCode(localRS.getString("SCODE"));
				res.setSubAccountID(localRS.getLong("id"));
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return res;
	}

	/**
	 * 获取客户下某种账户类型的第一个账户的代码
	 */
	public String getFirstAccountNoOfClient(long clientID, long accountTypeID)
			throws Exception {
		String strSQL = "select saccountno from sett_account  where nclientid = "
				+ clientID
				+ " and NACCOUNTTYPEID = "
				+ accountTypeID
				+ " and rownum = 1";
		String accountCode;
		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			accountCode = "";
			if (localRS.next()) {
				accountCode = localRS.getString("saccountno");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return accountCode;
	}

	public String getLoanSubjectCodeLoanTypeID(long currencyID,
			long loanTypeID, long accountTypeID) throws Exception {
		String strSQL = "select SSUBJECTCODE from SETT_SUBACCOUNTTYPE_LOAN where \n"
				+ " NCURRENCYID = "
				+ currencyID
				+ " and NLOANTYPEID = "
				+ loanTypeID
				+ " and NSTATUSID = 1 and naccounttypeid = "
				+ accountTypeID;
		if (accountTypeID == SETTConstant.AccountGroupType.DISCOUNT)
			strSQL += " and ndrafttypeid = 1 ";
		String subjectCode = "";
		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) {
				subjectCode = localRS.getString("SSUBJECTCODE");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return subjectCode;
	}

	/**
	 * 获取子账户对应的账户号
	 */
	public String getAccountCodeBySubAccountID(long subAccountID)
			throws Exception {
		String accountCode = "";
		String strSQL = "select SACCOUNTNO from sett_account a, sett_subaccount s where s.naccountid = a.id and s.id = "
				+ subAccountID;
		try {
			PreparedStatement localPS = prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) {
				accountCode = localRS.getString("SACCOUNTNO");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return accountCode;
	}

	/**
	 * 获取某个客户下某种账户类型的子账户ID
	 */
	public long getFirstSubAccountIDByClientID(long clientID, long accountTypeID)
			throws Exception {
		long subAccountID = -1;
		String strSQL = "select id from sett_subaccount where naccountid in (select id from sett_account  where nclientid = "
				+ clientID
				+ " and NACCOUNTTYPEID = "
				+ accountTypeID
				+ " and rownum = 1) and rownum = 1";
		try {
			PreparedStatement localPS = prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) {
				subAccountID = localRS.getLong("id");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return subAccountID;
	}

	/**
	 *  
	 */
	public boolean isSettlementInterestDate(Timestamp date) throws Exception {
		boolean res = false;
		String strSQL = "select DtCOMPOUNDINTEREST from SETT_COMPOUNDINTERESTSETTING";
		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				Timestamp compoundInterestDate = localRS
						.getTimestamp("DtCOMPOUNDINTEREST");
				if (compoundInterestDate.equals(date)) {
					res = true;
					break;
				}
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return res;
	}

	public Collection getAllCurrentAccount(long officeID, long currencyID)
			throws Exception {
		String strSQL = "select b.id,a.naccounttypeid,a.saccountno,a.nclientid from sett_account a,sett_subaccount b where naccounttypeid in ( select id from sett_accounttype where naccountgroupid = 1 and nstatusid > 0 and officeId="+officeID+" and currencyId="+currencyID+") and a.nstatusid>0 and a.nOfficeID = "
				+ officeID
				+ " and a.nCurrencyID = "
				+ currencyID
				+ " and a.id=b.naccountid";
		ArrayList resList = new ArrayList();

		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				ForecastAmountInfo amountInfo = new ForecastAmountInfo();
				amountInfo.setSubAccountID(localRS.getLong("id"));
				amountInfo.setAccountCode(localRS.getString("saccountno"));
				amountInfo.setClientID(localRS.getLong("nclientid"));
				amountInfo.setClientCode(NameRef.getClientCodeByID(amountInfo
						.getClientID()));
				amountInfo.setAccountTypeId(localRS.getLong("naccounttypeid"));
				resList.add(amountInfo);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return resList;
	}

	public Collection getAlTrustLoanAccount(long officeID, long currencyID,
			Timestamp transactionDate) throws Exception {
		String strTransDate = transferTimestampToTo_DateString(transactionDate);
		String strSQL = "select a.naccounttypeid,b.id subAccountid,a.id accountid,a.saccountno,a.nclientid from sett_account a,sett_subaccount b ,loan_payform c where naccounttypeid in (8) and a.nstatusid>0 and b.nstatusid>0 and a.nOfficeID = "
				+ officeID
				+ " and a.nCurrencyID = "
				+ currencyID
				+ " and a.id=b.naccountid and b.al_nloannoteid=c.id and c.dtend >= "
				+ strTransDate;
		ArrayList resList = new ArrayList();

		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				ForecastAmountInfo amountInfo = new ForecastAmountInfo();
				amountInfo.setSubAccountID(localRS.getLong("subAccountid"));
				amountInfo.setAccountID(localRS.getLong("accountid"));
				amountInfo.setAccountCode(localRS.getString("saccountno"));
				amountInfo.setClientID(localRS.getLong("nclientid"));
				amountInfo.setAccountTypeId(localRS.getLong("naccounttypeid"));
				amountInfo.setClientCode(NameRef.getClientCodeByID(amountInfo
						.getClientID()));
				resList.add(amountInfo);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return resList;
	}

	public Collection getAlConsignLoanAccount(long officeID, long currencyID,
			Timestamp transactionDate) throws Exception {
		String strTransDate = transferTimestampToTo_DateString(transactionDate);
		String strSQL = "select a.naccounttypeid,b.id subAccountid,a.id accountid,a.saccountno,a.nclientid from sett_account a,sett_subaccount b ,loan_payform c where naccounttypeid in (9) and a.nstatusid>0 and b.nstatusid>0 and a.nOfficeID = "
				+ officeID
				+ " and a.nCurrencyID = "
				+ currencyID
				+ " and a.id=b.naccountid and b.al_nloannoteid=c.id and c.dtend >= "
				+ strTransDate;
		ArrayList resList = new ArrayList();

		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			while (localRS.next()) {
				ForecastAmountInfo amountInfo = new ForecastAmountInfo();
				amountInfo.setSubAccountID(localRS.getLong("subAccountid"));
				amountInfo.setAccountID(localRS.getLong("accountid"));
				amountInfo.setAccountCode(localRS.getString("saccountno"));
				amountInfo.setClientID(localRS.getLong("nclientid"));
				amountInfo.setAccountTypeId(localRS.getLong("naccounttypeid"));
				amountInfo.setClientCode(NameRef.getClientCodeByID(amountInfo
						.getClientID()));
				resList.add(amountInfo);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return resList;
	}

	/**
	 * 
	 * @param officeID
	 * @param currencyID
	 * @param executeDate
	 * @param transDate
	 * @throws Exception
	 */
	public void extractDataFromTransInvestmentToActualAmount(long officeID,
			long currencyID, Timestamp executeDate, Timestamp transDate)
			throws Exception {
		XmlConfigInfo xmlConfigInfo = ReadXmlConfig.getXmlConfig();
		Vector vTrans = xmlConfigInfo.getTrans();
		Vector vActualAmount = new Vector(100, 100);

		if (vTrans == null) {

		} else {
			for (int i = 0; i < vTrans.size(); i++) {
				TransConfigInfo transConfigInfo = (TransConfigInfo) vTrans
						.get(i);
				vActualAmount.addAll(this.getTransInvestment(officeID,
						currencyID, executeDate, transDate, this.transConn,
						transConfigInfo));
			}
		}

		System.out.println("size:" + vActualAmount.size());

		Connection tpConn = null;
		try {
			tpConn = establishConnectionByModuleID(TPConstant.ModuleType.PLAN);
			tpConn.setAutoCommit(false);
			Trea_ActualAmountDAO actualAmountDAO = new Trea_ActualAmountDAO(
					tpConn);
			actualAmountDAO.deleteByTransactionDateAndModuleID(executeDate,transDate,
					Constant.ModuleType.SECURITIES, officeID, currencyID);

			//		  将所有结果写入数据库
			for (int i = 0; i < vActualAmount.size(); i++) {
				ActualAmountInfo actualAmountInfo = (ActualAmountInfo) vActualAmount
						.get(i);
				actualAmountDAO.add(actualAmountInfo);
			}
			tpConn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tpConn != null) {
				tpConn.close();
			}
		}

	}

	/**
	 * 
	 * @param officeID
	 * @param currencyID
	 * @param executeDate
	 * @param transDate
	 * @param conn
	 * @param transConfigInfo
	 * @throws Exception
	 */
	private Vector getTransInvestment(long officeID, long currencyID,
			Timestamp executeDate, Timestamp transDate, Connection conn,
			TransConfigInfo transConfigInfo) throws Exception {
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Vector rnt = new Vector();
		try {
			this.initDAO();
			pstat = prepareStatement(this.dealStrSql(transConfigInfo
					.getStrSql(), officeID, currencyID, executeDate, transDate));
			rs = executeQuery(); 

			while (rs.next()) {
				ActualAmountInfo actualAmountInfo = this.getInfobyRs(rs,
						transConfigInfo);
				actualAmountInfo.setOfficeID(officeID);
				actualAmountInfo.setCurrencyID(currencyID);
				actualAmountInfo.setModuleID(TPConstant.ModuleType.SECURITIES);
				actualAmountInfo.setTransactionDate(transDate);
				actualAmountInfo
						.setTransactionName(SETTConstant.SubInvestMentType
								.getName(actualAmountInfo.getAccountTypeId()));
				rnt.add(actualAmountInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pstat != null)
				pstat.close();
			this.finalizeDAO();
		}

		return rnt;
	}

	/**
	 * 
	 * @param rs
	 * @param transConfigInfo
	 * @return @throws
	 *         Exception
	 */
	private ActualAmountInfo getInfobyRs(ResultSet rs,
			TransConfigInfo transConfigInfo) throws Exception {
		ActualAmountInfo actualAmountInfo = new ActualAmountInfo();

		Vector vField = transConfigInfo.getFields();

		if (vField == null) {

		} else {
			for (int i = 0; i < vField.size(); i++) {
				FieldInfo fieldInfo = (FieldInfo) vField.get(i);
				Method[] methods = actualAmountInfo.getClass().getMethods();
				for (int j = 0; j < methods.length; j++) {
					String methodName = methods[j].getName();
					if (methodName != null && methodName.length() > 3
							&& methodName.substring(0, 3).equals("set")
							&& methods[j].getParameterTypes().length == 1) {
						if (methodName.substring(3, methodName.length()).trim()
								.toLowerCase().equals(
										fieldInfo.getEntityFieldName().trim()
												.toLowerCase())) {
							Object[] args = new Object[1];
							args[0] = this.getFieldValue(rs, fieldInfo,
									methods[j].getParameterTypes()[0]);
							methods[j].invoke(actualAmountInfo, args);

						}
					}
				}
			}
		}
		return actualAmountInfo;
	}

	/**
	 * 
	 * @param rs
	 * @param fieldInfo
	 * @param valueType
	 * @return @throws
	 *         Exception
	 */
	private Object getFieldValue(ResultSet rs, FieldInfo fieldInfo,
			Class valueType) throws Exception {
		Object rnt = null;
		if (valueType.getName().equals("java.lang.String")) {
			if (fieldInfo.isGetDataFromDatabase()) {
				rnt = rs.getString(fieldInfo.getDatabaseFieldName());
			} else {
				rnt = fieldInfo.getDefaultValue();
			}

		} else if (valueType.getName().equals("long")) {
			if (fieldInfo.isGetDataFromDatabase()) {
				rnt = new Long(rs.getLong(fieldInfo.getDatabaseFieldName()));
			} else {
				if (fieldInfo.getDefaultValue() == null
						|| fieldInfo.getDefaultValue().equals("")) {
					rnt = new Long(0);
				} else {
					if (fieldInfo.getDefaultValue().toLowerCase().equals(
							"TPConstant.AmountDirection.PLUS".toLowerCase())) {
						rnt = new Long(TPConstant.AmountDirection.PLUS);
					} else if (fieldInfo.getDefaultValue().toLowerCase()
							.equals(
									"TPConstant.AmountDirection.SUBTRACT"
											.toLowerCase())) {
						rnt = new Long(TPConstant.AmountDirection.SUBTRACT);
					} else {
						rnt = new Long(fieldInfo.getDefaultValue());
					}
				}

			}

		} else if (valueType.getName().equals("java.sql.Timestamp")) {
			if (fieldInfo.isGetDataFromDatabase()) {
				rnt = (Object) rs
						.getTimestamp(fieldInfo.getDatabaseFieldName());
			} else {
				rnt = Timestamp.valueOf(fieldInfo.getDefaultValue()
						+ " 00:00:00");
			}

		} else if (valueType.getName().equals("java.sql.Date")) {
			if (fieldInfo.isGetDataFromDatabase()) {
				rnt = (Object) rs.getDate(fieldInfo.getDatabaseFieldName());
			} else {
				rnt = Date.valueOf(fieldInfo.getDefaultValue());
			}

		} else if (valueType.getName().equals("double")) {
			if (fieldInfo.isGetDataFromDatabase()) {
				rnt = new Double(rs.getDouble(fieldInfo.getDatabaseFieldName()));
			} else {
				rnt = new Double(fieldInfo.getDefaultValue());
			}

			/*
			 * }else if(valueType.getName().equals("")){ }else
			 * if(valueType.getName().equals("")){ }else
			 * if(valueType.getName().equals("")){
			 */
		}

		return rnt;
	}

	/**
	 * 替换strSql中的
	 * 
	 * @officeID @officeID @executeDate @transDate为对应参数 @param
	 *           strSql
	 * @param officeID
	 * @param currencyID
	 * @param executeDate
	 * @param transDate
	 * @return
	 */
	private String dealStrSql(String strSql, long officeID, long currencyID,
			Timestamp executeDate, Timestamp transDate) {

		if (strSql != null) {

			strSql = strSql.replaceAll("@officeID", String.valueOf(officeID));
			strSql = strSql.replaceAll("@currencyID", String
					.valueOf(currencyID));
			strSql = strSql.replaceAll("@executeDate", String
					.valueOf("to_date('"
							+ DataFormat.getDateString(executeDate)
							+ "','yyyy-mm-dd')"));
			strSql = strSql
					.replaceAll("@transDate", String.valueOf("to_date('"
							+ DataFormat.getDateString(transDate)
							+ "','yyyy-mm-dd') "));
		}
		return strSql;
	}

	private boolean isBankTADI(long transacationTypeID, String stransNo,
			long accountID) throws Exception {
		boolean res = false;
		long bankID = getBankIDFromTransactionDetail(transacationTypeID,
				stransNo);
		if (transacationTypeID == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER) {
			return false;
		} else {
			if (bankID > 0)
				res = true;
			else
				res = false;

		}
		return res;

	}

	private boolean isReceieveInterestAccountInFixedContinueTransfer(
			String transNo, long accountID) throws Exception {
		String strSQL = "select NRECEIVEINTERESTACCOUNTID  from SETT_TRANSFIXEDCONTINUE where stransno = '"
				+ transNo + "' and nstatusid > 0";
		boolean res = false;
		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			long receieveAccountID;
			if (localRS.next())
				receieveAccountID = localRS
						.getLong("NRECEIVEINTERESTACCOUNTID");

		} finally {
			finalizeDAO();
		}
		return res;
	}

	private long getBankIDFromTransactionDetail(long transactionTypeID,
			String stransNo) throws Exception {
		String[] res = getTransTableNameByTransTypeID(transactionTypeID);
		long bankID = -1;

		String strSQL = "select " + res[1] + " from " + res[0]
				+ " where STRANSNO = '" + stransNo + "' and nstatusid > 0";
		try {
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next())
				bankID = localRS.getLong(res[1]);
		} finally {
			finalizeDAO();
		}
		return bankID;
	}

	private String[] getTransTableNameByTransTypeID(long transactionTypeID) {
		String tableName = "";
		String bankIDName = "NBankID";
		String res[] = { tableName, bankIDName };
		if (SETTConstant.TransactionType.isCurrentTransaction(transactionTypeID))
			tableName = "sett_TransCurrentDeposit";

		switch ((int) transactionTypeID) {
		case (int) SETTConstant.TransactionType.OPENFIXEDDEPOSIT:
		case (int) SETTConstant.TransactionType.OPENNOTIFYACCOUNT: {
			tableName = "sett_TransOpenFixedDeposit";
		}
			break;
		case (int) SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER:
		case (int) SETTConstant.TransactionType.NOTIFYDEPOSITDRAW: {
			tableName = "sett_TransFixedWithDraw";
		}
			break;
		case (int) SETTConstant.TransactionType.FIXEDCONTINUETRANSFER: {
			tableName = "sett_TransFixedContinue";
			bankIDName = "NINTERESTBANKID";
		}
			break;

		}
		res[0] = tableName;
		res[1] = bankIDName;
		return res;
	}
}