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
 * ��Ϣ���ý����Ϣ����ҵ�������Ҫʵ�ֵĹ��ܰ�����
 *
 * 
 *
 * ע������:Ϊ��ʹ�����ݷ��ʶ��������������ͷ������������ṩ֧�֣�
 *          
 */
public class InterestEstimate
{
    /**
     * ����Ǹ���ķ�������ά������Ӧ�ô�����ȡ�����ݿ����ӻ���ֱ��ͨ��JDBC���ʣ�ȱʡΪ������ȡ�á�
     */
    private boolean bConnectionFromContainer = true;
    
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	
	/**
	 * �����������п���Ϣ��¼�Ĳ�ѯ
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ�����
	 *            ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param queryInfo ������ѯ������ʵ�壬never null.
	 * @return InterestEstimateQueryResultInfo[] ���������Ľ�Ϣ��¼������Ϣ���飬������Ϣ����Ϣ
	 *         ��Ҫ���ü�Ϣ�ӿڼ���ó������û�з��������Ľ�Ϣ��¼����ô���� null.
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public Vector findEstimateRecords(Connection con, InterestEstimateQueryInfo queryInfo)
		throws Exception
	{

		Vector resultVec = new Vector(); //����ֵ

		//�ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
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
		}
		try
		{
			//ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			//�����÷�������������һ����ɲ��֡�			
			//��ѯ��¼��
			Vector rstVec = new Vector();
			rstVec = this.selectEstimateRecords(conInternal, queryInfo);
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()���㣡��������������");
				return resultVec;
			}
			//��Ϣ
			getInterestResult(queryInfo, resultVec, conInternal, rstVec);
			//�������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
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
			//ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			//�׳�����֪ͨ�������֯�ߡ�
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
			//�����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
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
	 * �����������ж��ڴ���Ϣ��¼�Ĳ�ѯ
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ�����
	 *            ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param queryInfo ������ѯ������ʵ�壬never null.
	 * @return InterestEstimateQueryResultInfo[] ���������Ľ�Ϣ��¼������Ϣ���飬������Ϣ����Ϣ
	 *         ��Ҫ���ü�Ϣ�ӿڼ���ó������û�з��������Ľ�Ϣ��¼����ô���� null.
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public Vector findFixedEstimateRecords(Connection con, InterestEstimateQueryInfo queryInfo)
		throws Exception
	{
		Vector resultVec = new Vector(); //����ֵ

		//�ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
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
		}
		try
		{
			//ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			//�����÷�������������һ����ɲ��֡�			
			//��ѯ��¼��
			Vector rstVec = new Vector();
			rstVec = this.selectFixedEstimateRecords(conInternal, queryInfo);
			System.out.println(rstVec.size()+"%^^^^^^^^^^^^^^^^^^^");
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()���㣡��������������");
				return resultVec;
			}
			
			
			
			//��Ϣ			�п����Ǵ˴���Ϣ��������
			queryInfo.setIsSelectInterest(1);//ȱʡ����Ϣ
			
			//queryInfo.getAccountTypeID();
			
			getBEIHFInterestResult(queryInfo, resultVec, conInternal, rstVec);
			//�������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
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
			//ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			//�׳�����֪ͨ�������֯�ߡ�
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
			//�����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
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
	//��ѯ���ڴ���������
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
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //����ֵ

		//�����ѯ�������		
		buffer.append(" SELECT \n");
		buffer.append(" account.ID AS accountID, \n");
		buffer.append(" account.sAccountNo AS accountNo, \n"); //�˻���
		buffer.append(" account.nAccountTypeID AS accountTypeID,\n"); //�˻�����
		buffer.append(" client.sname ClientName, \n");                              
		buffer.append(" client.scode ClientNo, \n");   
		buffer.append(" subaccount.ID AS subAccountID, \n"); //���˻� ID
		buffer.append(" subaccount.AF_sDepositNo AS fixedDepositNo,\n "); //���ڵ��ݺ�		
		buffer.append(" subaccount.AF_mRate AS Rate, \n"); //����
		buffer.append(" subaccount.mBalance AS Balance, \n"); //��ǰ���
		buffer.append(" subaccount.mOpenAmount AS Amount, \n"); //�������
		buffer.append(" subaccount.af_dtstart AS OpenDate \n"); //��ʼ��
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
		
		//���ڴ��
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
			//�������Ӳ���
			ps = con.prepareStatement(strSQL);
			int nIndex = 0;			
			if(qInfo.getDateTo()!=null && !qInfo.getDateTo().equals("")){ 
				ps.setTimestamp(1,qInfo.getDateTo());
			}
			
			//��ǰҳ����Ϣ    
			

			//ִ�в�ѯ
			rs = ps.executeQuery();

			//ȡ�ý��
			log.info("***********���÷��ؽ��***********"+rs);
			result = fetchDataFromNoticeRS(rs);
			
			for(int i=0;i<10;i++)
				System.out.println("=========�õ������ݽ���ĳ���Ϊ:"+result.size());
			  
			
		}
		catch (Exception e)
		{
			log.error("������Ϣ��¼ʱ��������" + e.getMessage());
			throw e;
		}
		finally
		{ //�ͷ���Դ
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
	 * �����ѯ������Ϣ
	 * @param queryInfo     ��ѯ���� 
	 * @param resultVec     ���ؽ��
	 * @param conInternal   ���ݿ�����
	 * @param rstVec        ��ѯ���
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
		log.info("-------��ʼ�����ѯ����--------");
		InterestOperation io = new InterestOperation(conInternal);
		InterestBatch ib = new InterestBatch(conInternal);
		//�ж��Ƿ�������
		 sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
		for (int i = 0; i < rstVec.size(); i++)
		{
			log.info("lhj debug info ===����ѭ����Ϣ=======");
			InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
			resultInfo = (InterestEstimateQueryResultInfo) rstVec.elementAt(i);	
	        log.debug("---------�ж��˻�����------------");
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
					log.info("lhj debug info ===���붨����Ϣ=======");
					FixedDepositAccountPayableInterestInfo fixedInterestInfo =
						new FixedDepositAccountPayableInterestInfo();
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						//����֪ͨ���
						if(accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("lhj debug info ===���붨�ڼ�����Ϣ=======");
							//ִ�ж��ڼ���ļ��㣬�����µļ�¼����
							SubAccountPredrawInterestInfo subInterestInfo =
								new SubAccountPredrawInterestInfo();
							subInterestInfo =
								io.getFixedAccountPredrawInterest(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									resultInfo.getAccountTypeID(),
									queryInfo.getClearInterestDate());
							//���		
							fixedInterestInfo.setBalance(subInterestInfo.getBalance());
							fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							//lhj modify===���漸�����Ժ���û�б�Ҫ��ʾ����------2003-11-26-----------------------
							fixedInterestInfo.setDays(subInterestInfo.getDays());
							fixedInterestInfo.setEDate(subInterestInfo.getEDate());
							fixedInterestInfo.setRate(subInterestInfo.getRate());
							fixedInterestInfo.setSDate(subInterestInfo.getSDate());
							//lhj modify==---------------------------------------2003-11-26-------------
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//������Ϣ
							newResultInfo.setInterest(fixedInterestInfo.getInterest());
							//��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							
							resultVec.addElement(newResultInfo);
							log.info("lhj debug info ===�������ڼ�����Ϣ=======");
						}					
					}
					else if (queryInfo.getIsSelectInterest()>0)
					{
						if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("--------------��ʼ֪ͨ��Ϣ------------");
							fixedInterestInfo =
								io.EstimateNoticeDepositAccountInterest(queryInfo.getOfficeID(),queryInfo.getCurrencyID(),
									resultInfo.getAccountID(),resultInfo.getSubAccountID(),
									queryInfo.getDateFrom(),queryInfo.getDateTo(),
									resultInfo.getRate(),
									Env.getSystemDate(conInternal,queryInfo.getOfficeID(),queryInfo.getCurrencyID()),1);
							log.info("--------------��ʼ֪ͨ��Ϣ------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//��Ϣ���
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);
						}
						else
						{
							log.info("--------------��ʼ������Ϣ------------");
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
							log.info("--------------����������Ϣ------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//��Ϣ���
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);						
						}					
					}
					log.info("lhj debug info ===����������Ϣ=======");				
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) //����
				{
					//��Ϣ
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("lhj debug info ===���������Ϣ=======");
						CurrentDepositAccountInterestInfo currInterestInfo =
							new CurrentDepositAccountInterestInfo();
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ������Ϣ����---------");
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
									throw new IException("��������ʧ��");
								}
								log.info("-------------�����������---------");
								}
							}
						}
						log.info("-------------�ж��Ƿ���Ҫ�����������---------");
						log.info("-------------��Ϣ��ʼ---------");
						log.info("lhj debug info == ����getCurrentDepositAccountInterest.......");
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
						//��Ϣ���
						resultInfo.setInterestBalance(currInterestInfo.getNormalBalance());
						resultInfo.setInterest(currInterestInfo.getNormalInterest());						
						resultInfo.setStartDate(currInterestInfo.getSDate());
						
						//��Ϣ����
						resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
			
						log.info("-------------��Ϣ����---------");
											
						resultVec.addElement(resultInfo);
						log.info("lhj debug info ===����������Ϣ=======");
					}
			
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //����
				{
					log.info("lhj debug info ===���������Ϣ=======");
					LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
			
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("��interestSettlement 346��lhj debug settlement info >>>>>>>>>>>�����˻���Ҫ��Ϣ! ");
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ������Ϣ����---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
								}
							}
						}
						log.info("-------------�ж��Ƿ���Ҫ�����������---------");
			
						//��Ϣ
						log.info("-------------��Ϣ��ʼ---------");
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
						log.info("----------��ӡ������Ϣ-------------");
						log.debug(UtilOperation.dataentityToString(loanInterestInfo));
						log.info("-------------��Ϣ����---------");
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//��Ϣ
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//��Ϣ���					
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
						
						newResultInfo.setDays(loanInterestInfo.getDays());
						
						
						log.info("��interestSettlement 415��lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID�ǣ� "+newResultInfo.getAccountTypeID());
				        log.debug("---------�ж��˻�����------------");
				        AccountTypeInfo newAccountTypeInfo = null;
				        try {
				        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
						} catch (SQLException e) {
							e.printStackTrace();
						}
							if (newAccountTypeInfo != null) {
							//ί�д����˻�
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
								
								//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
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
								//�д�����
								log.info("--------���Ŵ��������������Ϣ���ݰ��д�����ת��");
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
						log.info("��interestSettlement 445��lhj debug settlement info >>>>>>>>>>>�����˻�������Ϣ-------");
						
						//ִ�д��������Ϣ�ļ��㣬�����µļ�¼����
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
						//������Ϣ
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						resultVec.addElement(newResultInfo);
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
					{
						log.info("��interestSettlement 475��lhj debug settlement info >>>>>>>>>>>�����˻�������-------");	
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
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
						//������
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
																
						resultVec.addElement(newResultInfo);
			
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
					{
						log.info("��interestSettlement 551��lhj debug settlement info >>>>>>>>�����˻�������-------");	
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
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
						//������
						newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
						//��Ϣ˰��
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						log.info("��interestSettlement 611��������Ϣ˰��");
						
						/**
						taxInfo = io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getAssuranceCharge());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						**/
						
						//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
											
						resultVec.addElement(newResultInfo);
			
					}
					if (queryInfo.getIsSelectCompoundInterest()>0)
					{
						log.info("��interestSettlement 635��lhj debug info>>>>>�����.....");
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
								}
							}
						}
						//����
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
						//����
						newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						//��Ϣ˰��
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
						
						//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//�д�����
							log.info("--------���Ŵ��������������Ϣ���ݰ��д�����ת��");
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
						log.info("��interestSettlement 721��lhj debug info>>>>>���Ϣ.....");
									
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
								}
							}
						}
						//��Ϣ
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
						//���ڷ�Ϣ
						newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);
						//��Ϣ˰��
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
						
						//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{ 
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//�д�����
							log.info("--------���Ŵ��������������Ϣ���ݰ��д�����ת��");
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
					log.info("lhj debug info ===����������Ϣ=======");
				}
			}
		}
		log.info("-------�����ѯ���ݽ���--------");
	}
	/**
	 * ������������֪ͨ����Ϣ��¼�Ĳ�ѯ
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ�����
	 *            ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param queryInfo ������ѯ������ʵ�壬never null.
	 * @return InterestEstimateQueryResultInfo[] ���������Ľ�Ϣ��¼������Ϣ���飬������Ϣ����Ϣ
	 *         ��Ҫ���ü�Ϣ�ӿڼ���ó������û�з��������Ľ�Ϣ��¼����ô���� null.
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public Vector findNoticeEstimateRecords(Connection con, InterestEstimateQueryInfo queryInfo)
		throws Exception
	{
		Vector resultVec = new Vector(); //����ֵ

		//�ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
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
		}
		try
		{
			//ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			//�����÷�������������һ����ɲ��֡�			
			//��ѯ��¼��
			Vector rstVec = new Vector();
			rstVec = this.selectNoticeEstimateRecords(conInternal, queryInfo);
			System.out.println(rstVec.size()+"%^^^^^^^^^^^^^^^^^^^");
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()���㣡��������������");
				return resultVec;
			}
			
			
			
			//��Ϣ			�п����Ǵ˴���Ϣ��������
			queryInfo.setIsSelectInterest(1);//ȱʡ����Ϣ
			
			//queryInfo.getAccountTypeID();
			
			getInterestResult(queryInfo, resultVec, conInternal, rstVec);
			//�������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
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
			//ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			//�׳�����֪ͨ�������֯�ߡ�
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
			//�����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
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
	 * ����֪ͨ�鱣��
	 * @return int value:
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public void saveLoanNotice(Connection con, Vector resultVec)
		throws Exception
	{		

		//�ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
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
		}
		try
		{
			//ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			//�����÷�������������һ����ɲ��֡�		
			Sett_LoanNoticeDAO loanNoticeDao = new Sett_LoanNoticeDAO();
			log.info("-------------��ʼ����---------");
			for (int i = 0; i < resultVec.size(); i++)
			{
				LoanNoticeInfo info = new LoanNoticeInfo();
				info = (LoanNoticeInfo)resultVec.elementAt(i);
				info.setStatusID(SETTConstant.BooleanValue.ISTRUE);
				loanNoticeDao.add(conInternal,info);
			}
			log.info("-------------�������---------");

			//�������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
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
			//ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			//�׳�����֪ͨ�������֯�ߡ�
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
			//�����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
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
	}
	/**
	 * �����ѯ������Ϣ
	 * @param queryInfo     ��ѯ���� 
	 * @param resultVec     ���ؽ��
	 * @param conInternal   ���ݿ�����
	 * @param rstVec        ��ѯ���
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
		log.info("-------��ʼ�����ѯ����--------");
		InterestOperation io = new InterestOperation(conInternal);
		InterestBatch ib = new InterestBatch(conInternal);
		//�ж��Ƿ�������
		 sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
		for (int i = 0; i < rstVec.size(); i++)
		{
			log.info("lhj debug info ===����ѭ����Ϣ=======");
			InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
			resultInfo = (InterestEstimateQueryResultInfo) rstVec.elementAt(i);	
	        log.debug("---------�ж��˻�����------------");
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
					log.info("lhj debug info ===���붨����Ϣ=======");
					FixedDepositAccountPayableInterestInfo fixedInterestInfo =
						new FixedDepositAccountPayableInterestInfo();
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						//����֪ͨ���
						if(accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("lhj debug info ===���붨�ڼ�����Ϣ=======");
							//ִ�ж��ڼ���ļ��㣬�����µļ�¼����
							SubAccountPredrawInterestInfo subInterestInfo =
								new SubAccountPredrawInterestInfo();
							subInterestInfo =
								io.getFixedAccountPredrawInterest(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									resultInfo.getAccountTypeID(),
									queryInfo.getClearInterestDate());
							//���		
							fixedInterestInfo.setBalance(subInterestInfo.getBalance());
							fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							//lhj modify===���漸�����Ժ���û�б�Ҫ��ʾ����------2003-11-26-----------------------
							fixedInterestInfo.setDays(subInterestInfo.getDays());
							fixedInterestInfo.setEDate(subInterestInfo.getEDate());
							fixedInterestInfo.setRate(subInterestInfo.getRate());
							fixedInterestInfo.setSDate(subInterestInfo.getSDate());
							//lhj modify==---------------------------------------2003-11-26-------------
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//������Ϣ
							newResultInfo.setInterest(fixedInterestInfo.getInterest());
							//��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							
							resultVec.addElement(newResultInfo);
							log.info("lhj debug info ===�������ڼ�����Ϣ=======");
						}					
					}
					else if (queryInfo.getIsSelectInterest()>0)
					{
						if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
						{
							log.info("--------------��ʼ֪ͨ��Ϣ------------");
							fixedInterestInfo =
								io.EstimateNoticeDepositAccountInterest(queryInfo.getOfficeID(),queryInfo.getCurrencyID(),
									resultInfo.getAccountID(),resultInfo.getSubAccountID(),
									queryInfo.getDateFrom(),queryInfo.getDateTo(),
									resultInfo.getRate(),
									Env.getSystemDate(conInternal,queryInfo.getOfficeID(),queryInfo.getCurrencyID()),1);
							log.info("--------------��ʼ֪ͨ��Ϣ------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//��Ϣ���
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);
						}
						else
						{
							log.info("--------------��ʼ������Ϣ------------");
							fixedInterestInfo =
								io.getFixedDepositAccountPayableInterest(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									queryInfo.getClearInterestDate());
							log.info("--------------����������Ϣ------------");
							InterestEstimateQueryResultInfo newResultInfo =
								getFixedInfo(resultInfo, fixedInterestInfo);
							//��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							//��Ϣ���
							newResultInfo.setInterestBalance(fixedInterestInfo.getBalance());
							
							resultVec.addElement(newResultInfo);						
						}					
					}
					log.info("lhj debug info ===����������Ϣ=======");				
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) //����
				{
					//��Ϣ
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("lhj debug info ===���������Ϣ=======");
						CurrentDepositAccountInterestInfo currInterestInfo =
							new CurrentDepositAccountInterestInfo();
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ������Ϣ����---------");
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
									throw new IException("��������ʧ��");
								}
								log.info("-------------�����������---------");
								}
							}
						}
						log.info("-------------�ж��Ƿ���Ҫ�����������---------");
						log.info("-------------��Ϣ��ʼ---------");
						log.info("lhj debug info == ����getCurrentDepositAccountInterest.......");
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
						//��Ϣ���
						resultInfo.setInterestBalance(currInterestInfo.getNormalBalance());
						resultInfo.setInterest(currInterestInfo.getNormalInterest());						
						resultInfo.setStartDate(currInterestInfo.getSDate());
						
						//��Ϣ����
						resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
			
						log.info("-------------��Ϣ����---------");
											
						resultVec.addElement(resultInfo);
						log.info("lhj debug info ===����������Ϣ=======");
					}
			
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //����
				{
					log.info("lhj debug info ===���������Ϣ=======");
					LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
			
					if (queryInfo.getIsSelectInterest()>0)
					{
						log.info("��interestSettlement 346��lhj debug settlement info >>>>>>>>>>>�����˻���Ҫ��Ϣ! ");
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ������Ϣ����---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
								}
							}
						}
						log.info("-------------�ж��Ƿ���Ҫ�����������---------");
			
						//��Ϣ
						log.info("-------------��Ϣ��ʼ---------");
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
						log.info("----------��ӡ������Ϣ-------------");
						log.debug(UtilOperation.dataentityToString(loanInterestInfo));
						log.info("-------------��Ϣ����---------");
						InterestEstimateQueryResultInfo newResultInfo =
							getLoanInfo(resultInfo, loanInterestInfo);
						//��Ϣ
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//��Ϣ���					
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
						
						newResultInfo.setDays(loanInterestInfo.getDays());
						
						
						log.info("��interestSettlement 415��lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID�ǣ� "+newResultInfo.getAccountTypeID());
				        log.debug("---------�ж��˻�����------------");
				        AccountTypeInfo newAccountTypeInfo = null;
				        try {
				        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
						} catch (SQLException e) {
							e.printStackTrace();
						}
							if (newAccountTypeInfo != null) {
							//ί�д����˻�
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
								
								//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
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
								//�д�����
								log.info("--------���Ŵ��������������Ϣ���ݰ��д�����ת��");
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
						log.info("��interestSettlement 445��lhj debug settlement info >>>>>>>>>>>�����˻�������Ϣ-------");
						
						//ִ�д��������Ϣ�ļ��㣬�����µļ�¼����
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
						//������Ϣ
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						resultVec.addElement(newResultInfo);
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
					{
						log.info("��interestSettlement 475��lhj debug settlement info >>>>>>>>>>>�����˻�������-------");	
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
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
						//������
						newResultInfo.setInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
																
						resultVec.addElement(newResultInfo);
			
					}
					else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
					{
						log.info("��interestSettlement 551��lhj debug settlement info >>>>>>>>�����˻�������-------");	
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
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
						//������
						newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
						//��Ϣ˰��
						InterestTaxInfo taxInfo = new InterestTaxInfo();
						log.info("��interestSettlement 611��������Ϣ˰��");
						
						/**
						taxInfo = io.getInterestTax(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getAssuranceCharge());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						**/
						
						//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
											
						resultVec.addElement(newResultInfo);
			
					}
					if (queryInfo.getIsSelectCompoundInterest()>0)
					{
						log.info("��interestSettlement 635��lhj debug info>>>>>�����.....");
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
								}
							}
						}
						//����
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
						//����
						newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						//��Ϣ˰��
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
						
						//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//�д�����
							log.info("--------���Ŵ��������������Ϣ���ݰ��д�����ת��");
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
						log.info("��interestSettlement 721��lhj debug info>>>>>���Ϣ.....");
									
						Collection coll = null;
						log.info("-------------�ж��Ƿ���Ҫ��������---------");
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
									log.info("-------------��ʼ��������---------");
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
										throw new IException("��������ʧ��");
									}
									log.info("-------------�����������---------");
								}
							}
						}
						//��Ϣ
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
						//���ڷ�Ϣ
						newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
						//��Ϣ����
						newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);
						//��Ϣ˰��
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
						
						//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
						taxInfo =
							io.getInterestTaxByPlan(
								newResultInfo.getAccountID(),
								newResultInfo.getSubAccountID(),
								newResultInfo.getInterest());
						newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
						newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
						newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						
						//��Ϣ���
						newResultInfo.setInterestBalance(loanInterestInfo.getBalance());
						/*if(newResultInfo.getLoanTypeID()==LOANConstant.LoanType.YT)
						{
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//�д�����
							log.info("--------���Ŵ��������������Ϣ���ݰ��д�����ת��");
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
					log.info("lhj debug info ===����������Ϣ=======");
				}
			}
		}
		log.info("-------�����ѯ���ݽ���--------");
	}
	private InterestEstimateQueryResultInfo getFixedInfo(
	InterestEstimateQueryResultInfo resultInfo,
		FixedDepositAccountPayableInterestInfo fixedInterestInfo)
	{
		//�����µļ�¼���뵽Vector
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
		
		//��Ϣ
		//newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
		newResultInfo.setRate(loanInterestInfo.getRate());
		newResultInfo.setDays(loanInterestInfo.getDays());
		return newResultInfo;
	}
	//��ѯ��Ϣ������������
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
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //����ֵ

		//�����ѯ�������		
		buffer.append(" SELECT distinct\n");
		buffer.append(" account.ID AS accountID, \n");
		buffer.append(" account.sAccountNo AS accountNo, \n"); //�˻���
		buffer.append(" account.nAccountTypeID AS accountTypeID,\n"); //�˻�����		
		buffer.append(" contractform.nTypeID AS LoanTypeID,\n"); //��������
		buffer.append(" payform.id AS payFormID, \n"); //�ſ�֪ͨ��ID
		buffer.append(" client.sname ClientName, \n");                              
		buffer.append(" client.scode ClientNo, \n");    		
		buffer.append(" subaccount.ID AS subAccountID, \n"); //���˻� ID
		buffer.append(" subaccount.AF_sDepositNo AS fixedDepositNo,\n "); //���ڵ��ݺ�		
		buffer.append(" subaccount.AF_mRate AS Rate, \n"); //����
		buffer.append(" subaccount.mBalance AS Balance, \n"); //��ǰ���
		buffer.append(" subaccount.mOpenAmount AS Amount, \n"); //�������		
		buffer.append(" contractform.sContractCode AS contractNo, \n"); //��ͬ��
		buffer.append(" payform.sCode AS payFormNo \n"); //�ſ�֪ͨ����
		
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
			{                                    //��Ϣ��				
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
		//��Ӫ��������
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
		//ί�д�������
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
		//��Ӫ��������		
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
			// add by kevin(������)2011-08-01 ��ѡ�����Ӫ��������ʱ����ͬ����ӦĬ������Ӫ
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
			//�������Ӳ���
			ps = con.prepareStatement(strSQL);
			int nIndex = 1;			
			
			//��ǰҳ����Ϣ    
			

			//ִ�в�ѯ
			rs = ps.executeQuery();

			//ȡ�ý��
			log.info("***********���÷��ؽ��***********");
			result = fetchDataFromRS(rs);
		}
		catch (Exception e)
		{
			log.error("������¼ʱ��������" + e.getMessage());
			throw e;
		}
		finally
		{ //�ͷ���Դ
			rs.close();
			ps.close();
			if(con==null)
			{
				conInternal.close();
			}
		}

		return result;
	}
	//��ѯ֪ͨ����������
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
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //����ֵ

		//�����ѯ�������		
		buffer.append(" SELECT \n");
		buffer.append(" account.ID AS accountID, \n");
		buffer.append(" account.sAccountNo AS accountNo, \n"); //�˻���
		buffer.append(" account.nAccountTypeID AS accountTypeID,\n"); //�˻�����
		buffer.append(" client.sname ClientName, \n");                              
		buffer.append(" client.scode ClientNo, \n");   
		buffer.append(" subaccount.ID AS subAccountID, \n"); //���˻� ID
		buffer.append(" subaccount.AF_sDepositNo AS fixedDepositNo,\n "); //���ڵ��ݺ�		
		buffer.append(" subaccount.AF_mRate AS Rate, \n"); //����
		buffer.append(" subaccount.mBalance AS Balance, \n"); //��ǰ���
		buffer.append(" subaccount.mOpenAmount AS Amount, \n"); //�������
		buffer.append(" subaccount.dTopen AS OpenDate \n"); //��ʼ��
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
		
		//֪ͨ���
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
			//�������Ӳ���
			ps = con.prepareStatement(strSQL);
			int nIndex = 0;			
			if(qInfo.getDateTo()!=null && !qInfo.getDateTo().equals("")){ 
				ps.setTimestamp(1,qInfo.getDateTo());
			}
			
			//��ǰҳ����Ϣ    
			

			//ִ�в�ѯ
			rs = ps.executeQuery();

			//ȡ�ý��
			log.info("***********���÷��ؽ��***********"+rs);
			result = fetchDataFromNoticeRS(rs);
			
			for(int i=0;i<10;i++)
				System.out.println("=========�õ������ݽ���ĳ���Ϊ:"+result.size());
			
			
		}
		catch (Exception e)
		{
			log.error("������Ϣ��¼ʱ��������" + e.getMessage());
			throw e;
		}
		finally
		{ //�ͷ���Դ
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
	 * �ӽ����������ʵ�帳ֵ(ȫ��)
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
	 * �ӽ����������ʵ�帳ֵ(ȫ��)
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
     * ��������ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
     * @return ���ݿ����ӡ�
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
     * �������ݿ����ӵ���Դ��
     * @param bConnectionFromContainer
     *        true - ������ȡ�����ݿ����ӡ�
     *        false - ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
     */
    public void setConnectionFromContainer(boolean bConnectionFromContainer){
        this.bConnectionFromContainer = bConnectionFromContainer;
    }
    
    /**
     * ȡ�����ݿ����ӵ���Դ��
     * @return ���ݿ����ӵ���Դ��
     *        true - ������ȡ�����ݿ����ӡ�
     *        false - ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
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
