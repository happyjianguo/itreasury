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
	 * ����Ǹ���ķ�������ά������Ӧ�ô�����ȡ�����ݿ����ӻ���ֱ��ͨ��JDBC���ʣ�ȱʡΪ������ȡ�á�
	 */
	private boolean	bConnectionFromContainer	= true;
	private Log4j	log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private CalculateCraInterestDao calculateCraInterestDao;
	public CraInterestSettlement() throws IException
	{
		calculateCraInterestDao = new CalculateCraInterestDao();
	}
	/**
	 * ��������ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
	 * 
	 * @return ���ݿ����ӡ�
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
	 * �����������н�Ϣ��¼�Ĳ�ѯ��Ϊ������Ϣ������������Ϣ�ͽ��㹦���ṩ֧�֡�
	 * 
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ����� ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param queryInfo ������ѯ������ʵ�壬never null.
	 * @return InterestQueryResultInfo[] ���������Ľ�Ϣ��¼������Ϣ���飬������Ϣ����Ϣ ��Ҫ���ü�Ϣ�ӿڼ���ó������û�з��������Ľ�Ϣ��¼����ô���� null.
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public Collection selectCraTransferRecords(Connection con, CraInterestInfo queryInfo) throws Exception
	{

		Collection coll = null; 
		Collection conInterestColl = null;

		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
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
				throw new IException("�޷�ȡ�����ݿ�����");
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
			    log.info("-------��ʼ���������ع���ѯ����--------");			
			    coll = calculateCraInterestDao.getClearContract(conInternal,queryInfo);
			    if(coll != null && coll.size() > 0)
			     {
				     conInterestColl = getContractSubsection(conInternal,queryInfo, coll);
				
			     }
			log.info("-------���������ع���ѯ���ݽ���--------");
			}
			else if(queryInfo.getLCraContractTypeId()==SETTConstant.CraSettTransactionType.BREAK_NOTIFY)
			{
				
				log.info("-------��ʼ�����������ϲ�ѯ����--------");			
				coll = calculateCraInterestDao.getClearContract(conInternal,queryInfo);
				if(coll != null && coll.size() > 0)
			     {
				     conInterestColl = getBreakContractSubsection(conInternal,queryInfo, coll);
				
			     }
			}
			
			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("�����ύ�쳣");
				}
			}

		}

		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
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
					throw new IException("��������쳣");
				}
			}
			throw e;
		}

		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
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
	 * ��װ���������ع�
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
					//��Ϣ��
					//dtStartDate = calculateCraInterestDao.findDtStratDate(con,conInfo);	
					dtStartDate = calculateCraInterestDao.findNewDtStratDate(con,conInfo);	
					//ֹϢ��
					dtEndDate = DateUtil.getNextNDay(qInfo.getDtClearInterest(), -1);
					conInfo.setPreclearInterstDate(dtStartDate);
					conInfo.setClearInterstDate(dtEndDate);
					//�ڳ����
					dtStartBalance = calculateCraInterestDao.findStartBalance(con, conInfo);
					//��ͬ����
					interestRate = calculateCraInterestDao.queryInterestRate(con,conInfo);
					//��ѯ�տ�򸶿����
					Collection colTrans = (Collection)calculateCraInterestDao.queryTransInfo(con, conInfo);
					//��ѯ��Ϣ�յ����ո�����Ϣ����
					sumPayInterest = calculateCraInterestDao.querySumInterest(con, conInfo);
					//��ѯ��Ϣ�յ���ļ�����Ϣ
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
							
							if(info.getTRANSACTIONTYPEID()== SETTConstant.TransactionType.TRANSFERREPAY)//�տ�
							{
								dtStartBalance = UtilOperation.Arith.add(dtStartBalance ,info.getAMOUNT());
							}
							else if(info.getTRANSACTIONTYPEID()== SETTConstant.TransactionType.TRANSFERPAY)//����
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
	 * ��װ������������
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
					//��Ϣ��
					dtStartDate = calculateCraInterestDao.queryDtStratDate(con,conInfo);	
					//ֹϢ��
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
					//ͨ��ת�ú�ͬ�ҵ���Ӧ�Ĵ���֪ͨ����Ϣ
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
 				            //��ʾ��Ϣ
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
							//��ѯ�ñ�֪ͨ����Ӧ���ո����
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
									if(transNoticeInfo.getTranstypeid()== SETTConstant.TransactionType.TRANSFERREPAY)//�տ�
									{
										dtStartBalance = UtilOperation.Arith.add(dtStartBalance ,transNoticeInfo.getAmount());
									}
									else if(transNoticeInfo.getTranstypeid()== SETTConstant.TransactionType.AGENTTRANSFERREPAY)//����
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
									//ת�ú�ͬ��ϸID
									transNoticeInfo.setCracontractid(contractInfo.getId());
									transNoticeInfo.setOfficeid(contractInfo.getOfficeId());
									transNoticeInfo.setCurrencyid(contractInfo.getCurrencyId());
									long transferContractDetailID = calculateCraInterestDao.queryContractDetailID(con,transNoticeInfo);
									//��ʾ��Ϣ
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
	 * ����������Ϣ�� ����ֻ�����Ϣ�� 
	 * @param con ���ݿ����ӣ����ⲿ���룬never null.
	 * @param resultInfo ����Ҫ������˻������Ϣ��ʵ�壬never null.
	 * @return int value:
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public int drawingInterest(Connection con, Vector resultVec, TransferInterestRecordInfo settmentInfo) throws Exception
	{

		int nResult = 0; // ����ֵ
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
				throw new IException("�޷�ȡ�����ݿ�����");
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
			log.info("-------------��ʼ����---------");

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
						log.info("---------------�ж���Ϣ�Ƿ����0-----------------");
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// ȡSETT_TRANSFERINTERSETRECORD��ļ�����Ϣ�ֶ�
							if (recordInfo.getMinterest() > 0
									&& recordInfo.getDtinterestsettlement().equals(info.getCreateDate()))
							{
								throw new IException("ת�ú�ͬ�ţ�" + info.getCraContractNo() + " ���׶������ƣ�"
										+ info.getCraCounterPartName() + " �Ѿ�����");
							}
							else
							{
								throw new IException("ת�ú�ͬ�ţ�" + info.getCraContractNo() + " ���׶������ƣ�" + info.getCraCounterPartName() + " ��ϢΪ�ղ��ܼ���");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{

							// �����Ϣ��¼
							log.info("----------���濪ʼ---------");
							
							/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
							settmentInfo.setTransActionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW);
							
							settmentInfo.setInterestType(SETTConstant.InterestOperateType.PREDRAWINTEREST);

							TransferInterestRecordInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
							// ������Ϣ
							// ��Ϣ���ü���ķ�������
							saveInfo.setInterestType(SETTConstant.InterestOperateType.PREDRAWINTEREST);

							saveInfo.setTransActionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW);

							log.debug(UtilOperation.dataentityToString(saveInfo));
	
							// ��������Ϣ��SETT_TRANSFERINTERSETRECORD
							long l = calculateCraInterestDao.add(conInternal, saveInfo);
	
							if (l < 0)
							{
								throw new IException("��Ϣ����ʧ��");
							}
	
							log.info("----------�������---------");
							log.info("-------------������Ϣ���Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();	
									// ������Ϣ���ɿ�
									param.setPreDrawInterest(saveInfo.getMinterest());
                                    param.setCraContractID(saveInfo.getCracontractid());
									param.setOfficeID(saveInfo.getOfficeid());
									param.setCurrencyID(saveInfo.getCurrencyid());	
									// ��Ӫ�������Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW );									
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeid(),
											settmentInfo.getCurrencyid()));
									param.setInterestStartDate(saveInfo.getDtinterestsettlement());
									param.setTransNo(saveInfo.getStransno());
									param.setAbstractStr(saveInfo.getSabstract());
									param.setInputUserID(saveInfo.getInputuserid());
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
	
							log.info("-------------������Ϣ�������---------");
						}
					}																		
				}
			log.info("-------------�������---------");

			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					log.info("*****�ύ******");
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new Exception("�����ύ�쳣");
				}
			}
		}
		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					log.info("*****�ع�******");
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new Exception("��������쳣");
				}
			}

			throw e;
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					log.info("****�ر�����******");
					conInternal.close();
				}
				catch (Exception eClose)
				{
					throw new Exception("�����쳣");
				}
			}
		}

		return nResult;
	}
	
	private TransferInterestRecordInfo getSaveInfo(Connection con,TransferInterestRecordInfo settmentInfo, InterestQueryResultInfo info) throws IException, Exception
	{
		// ��Ϣ��
		TransferInterestRecordInfo saveInfo = new TransferInterestRecordInfo();

		// ���׺�
		UtilOperation uo = new UtilOperation();

		String strTransNo = "";
		try
		{
			strTransNo = uo.getNewTransactionNo(con,settmentInfo.getOfficeid(), settmentInfo.getCurrencyid(),settmentInfo.getTransActionTypeID());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("û��ȡ�����׺ţ�");
		}

		saveInfo.setStransno(strTransNo);
		// ������Ϣ
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

		// ������Ϣ
		saveInfo.setDtinterestsettlement(settmentInfo.getDtinterestsettlement());
		saveInfo.setDtstart(info.getStartDate());
		saveInfo.setDtend(info.getEndDate());
		saveInfo.setDays(info.getDays());
		saveInfo.setAmount(info.getBalance());
		saveInfo.setDrate(info.getInterestRate());
		saveInfo.setInterestType(settmentInfo.getInterestType());
		// ������Ϣ
		// ��Ϣ
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setMinterest(UtilOperation.Arith.round(info.getInterest(), 2));
		}

		// ������Ϣ
		// ��Ϣ����
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
	 * ����������Ϣ��
	 * 
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ����� ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param resultInfo[] ����Ҫ����������˻������Ϣ��ʵ���б�.
	 * @return int value
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public int clearDrawingInterest(Connection con, Vector resultVec, TransferInterestRecordInfo settmentInfo)
			throws Exception
	{

		int nResult = 0; // ����ֵ

		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
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
				throw new IException("�޷�ȡ�����ݿ�����");
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
			log.info("-------------��ʼ��������---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestQueryResultInfo info = new InterestQueryResultInfo();
				info = (InterestQueryResultInfo) resultVec.elementAt(i);

						log.info("---------�жϼ�����Ϣ�Ƿ����0---------------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("ת�ú�ͬ�ţ�" + info.getCraContractNo() + " ���׶������ƣ�" + info.getCraCounterPartName() + " ������ϢΪ�ղ��ܳ�������");
						}
						else if (info.getDrawingInterest() > 0)
						{
							log.info("-------------�������Ὺʼ---------");
	
								if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									// �����Ϣ��¼
									log.info("----------���濪ʼ---------");
	
									/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
									settmentInfo.setCracontractid(info.getCraContractID());
									settmentInfo.setTransActionTypeID(SETTConstant.TransactionType.REPURCHASEDRAW);
									settmentInfo.setInterestType(SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST);
									TransferInterestRecordInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
									saveInfo.setMinterest((-1)*saveInfo.getMinterest());//���뽻�׼�¼Ϊ��
									long l = calculateCraInterestDao.add(conInternal, saveInfo);
									saveInfo.setMinterest((-1)*saveInfo.getMinterest());
	
									if (l < 0)
									{
										throw new IException("��Ϣ����ʧ�ܣ���");
									}
	
									log.info("----------�������---------");
	
	
									if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
									{
										log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
										GenerateGLEntryParam param = new GenerateGLEntryParam();
	
										// ������Ϣ���ɿ�
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
	
										log.info("--------���ɻ�Ʒ�¼����---------");
									}
								}						
							log.info("-------------������Ϣ�����������---------");
						}
					 else
					{
						
						throw new IException("ת�ú�ͬ�ţ�"
								+ info.getCraContractNo()
								+" ���׶������ƣ�" + info.getCraCounterPartName() +  " �޷���������");
					}
				}
			log.info("-------------�����������---------");

			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new Exception("�����ύ�쳣");
				}
			}
		}
		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
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
					throw new Exception("��������쳣");
				}
			}
			throw e;
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
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
	 * �Ŵ��ʲ�ת�����������������㡣
	 * 
	 * @param con ���ݿ����ӣ����ⲿ���룬never null.
	 * @param resultVec ����Ҫ������˻������Ϣ��ʵ�壬never null.
	 * @param isSave �Ƿ񱣴�
	 * @param settmentInfo ��Ź�����Ϣ �Ƿ���ˣ��Ƿ񱣴��
	 * @return int value:
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public int balanceInterest(Connection con, Vector resultVec, TransferInterestRecordInfo settmentInfo) throws Exception
	{

		int nResult = 0; // ����ֵ
		long lsubPayInterestID = -1;
		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
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
				throw new IException("�޷�ȡ�����ݿ�����");
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
				// ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
				AccountOperation aco = new AccountOperation(conInternal);
				GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
				
				log.info("-------------��ʼУ���Ϣ---------");
				
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
					    	throw new IException("ת�ú�ͬ�ţ�" + infoCheck.getCraContractNo() + " ���׶������ƣ�"
									+ infoCheck.getCraCounterPartName() + " �ſ�֪ͨ����"+infoCheck.getLoanNoteNo()+"�ĸ�Ϣ�˻���Ϊ��");
					    }
					}
				}
				 log.info("-------------��ʼ��Ϣ---------");
		
				for (int i = 0; i < resultVec.size(); i++)
				{
					InterestQueryResultInfo queryInfo = new InterestQueryResultInfo();
					queryInfo = (InterestQueryResultInfo) resultVec.elementAt(i);
					
					for (int m = 0; m < queryInfo.getColl().size(); m++)
					{
				    log.info("-------------�������Ͽ�ʼ��Ϣ---------");
				    InterestQueryResultInfo info = new InterestQueryResultInfo();
				    vec = (Vector)queryInfo.getColl();
				    info = (InterestQueryResultInfo)vec.elementAt(m);
				    
				    if(info.getPayInterestAccountID()<0)
				    {
				    	throw new IException("ת�ú�ͬ�ţ�" + info.getCraContractNo() + " ���׶������ƣ�"
								+ info.getCraCounterPartName() + " �ſ�֪ͨ����"+info.getLoanNoteNo()+"�ĸ�Ϣ�˻���Ϊ��");
				    }
					//��Ϣ
				    TransferInterestRecordInfo recordInfo = new TransferInterestRecordInfo();
			        settmentInfo.setCracontractid(info.getCraContractID());
			        settmentInfo.setInterestType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
			        recordInfo = calculateCraInterestDao.queryPreDateByContractID(conInternal, settmentInfo);
			        
				    if (DataFormat.formatDouble(info.getInterest()) == 0)
					{
						// ȡSETT_TRANSFERINTERSETRECORD��ļ�����Ϣ�ֶ�
						if (recordInfo.getMinterest() > 0 && recordInfo.getDtinterestsettlement().compareTo(Env.getSystemDate(settmentInfo.getOfficeid(), settmentInfo.getCurrencyid())) == 0)
							{
								throw new IException("ת�ú�ͬ�ţ�" + info.getCraContractNo() + " ���׶������ƣ�"
										+ info.getCraCounterPartName() + " �Ѿ���Ϣ");
							}
							else
							{
								throw new IException("ת�ú�ͬ�ţ�" + info.getCraContractNo() + " ���׶������ƣ�" + info.getCraCounterPartName() + " ��ϢΪ�ղ��ܽ�Ϣ");
							}
					 }

				    if (info.getInterest() > 0)
						{
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE)  //����
									
							 {								
							        log.info("----------���濪ʼ---------");
							        //�����Ϣ��¼										
							        /*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
										
									settmentInfo.setTransActionTypeID(SETTConstant.TransactionType.BREAKINTERESTNOTIFY);

									TransferInterestRecordInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
									
                                     //������Ϣ
									// ��Ϣ���ü���ķ�������
									saveInfo.setInterestType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
									saveInfo.setTransActionTypeID(SETTConstant.TransactionType.BREAKINTERESTNOTIFY);
									
									// ��������Ϣ��SETT_TRANSFERINTERSETRECORD
									long l = calculateCraInterestDao.add(conInternal, saveInfo);
									if (l < 0)
									{
										throw new IException("��Ϣ����ʧ�ܣ���");
									}
									log.info("----------�������---------");
									if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
									{										
										if (info.getPayInterestAccountID() <= 0)
											{
												throw new IException("ת�ú�ͬ" + info.getCraContractNo() + "û�и�Ϣ�˻�");
											}
																														
										if (info.getPayInterestAccountID() > 0)
										{
											log.info("-------------����֧ȡ��ʼ---------");
											TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getStransno(), INTEREST_CURRENT_WITHDRAW);
											
											withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.BREAKINTERESTNOTIFY);
																							
											//��Ϣ����
											withdrawTadi.setCommonOperation(false);
											lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
											if (lsubPayInterestID < 0)
											{
												throw new IException("����֧ȡʧ�ܣ�");
											}
											log.info("-------------����֧ȡ����---------");
										}											
										    log.info("------------���ɴ����Ʒ�¼��ʼ--------------");

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
											 * �����/���������˻�ID>0���򱾽� ����lPrincipalType =2���У����򱾽�����lPrincipalType =1 �ڲ�ת��
											 */
											long lPrincipalType = -1;
											//���� �޹�
										    lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
											//��¼���� �޹�
											long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
								            //����������
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
							    												
											
											log.info("------------�����������ϻ�Ʒ�¼����--------------");
										}
									
									log.info("-------------����������Ϣ��Ϣ����---------");	
								}													
				          }
				
				     log.info("-------------��Ϣ����---------");
                 }
		   }		
				// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
				if (con == null)
				{
					try
					{
						conInternal.commit();
						conInternal.setAutoCommit(true);
					}
					catch (Exception eCommit)
					{
						throw new Exception("�����ύ�쳣");
					}
				}

			
			}
			catch (Exception e)
			{
				// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
				// �׳�����֪ͨ�������֯�ߡ�
				e.printStackTrace();
				if (con == null)
				{
					try
					{
						log.info("****���ӻع�******");
						conInternal.rollback();
						conInternal.setAutoCommit(true);
					}
					catch (Exception eRollback)
					{
						throw new IException("��������쳣");
					}
				}
				throw e;
			}

		}
		catch (Exception eRollback)
		{
			//2009��2��20�� Boxu ���쳣������
			//throw new IException(eRollback.getMessage());
			throw new IException(eRollback.getMessage(), eRollback);
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					log.info("****�ر�����******");
					conInternal.close();
				}
				catch (Exception eClose)
				{
					throw new IException("�ر�����ʧ�ܣ�");
				}
			}
		}

		return nResult;
	}
	private int	INTEREST_CURRENT_WITHDRAW	= 0;
	/** ת����Ϣ����������˻��������� */
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

