package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.overdueapply.dataentity.OverDueInfo;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.interest.dao.Sett_CompoundInterestSettingDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dataentity.BankInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.ClearLoanAccountInterestConditionInfo;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestAccountIDInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestCalculationParameterInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestRate;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.interest.dataentity.LiborInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransOpenMarginDepositDAO;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.RecordAccountInfo;
/**
 * @author Liu huijun
 * Date of Creation    2003-11-06
 * **********��Ϣ����ҵ��ӿڣ���Ҫʵ�ֵĹ��ܰ���****************************
 * 01:��ѯ�˻����ո�Ϣ�˻�ID����ѯ�˻����ո�Ϣ�˻�ID����Ϣ����ʱ�á�
 * 02:����/Э���˻���Ϣ�����ڵ�����Ϣʱ���������˻����е�ǰ�ۼ���Ϣ����һ��Ϣ�յ���Ϣ��
 * 03:����/Э���˻���Ϣ�����ף����ںʹ����˻�������Ϣ��¼ɾ����ȡ������ʱ���������˻���
 * 04:�����˻���Ϣ/���á�
 * 05:�����˻���Ϣ/���÷����ס�
 * 06:������Ϣ�����
 * 07:������Ϣ�����
 * 
 * **********������ҵ��ӿڣ���Ҫʵ�ֵĹ��ܰ���������������������������������������������������������������
 * 08:������������ڴ���
 * 09:�˻�����ս�
 * 10:ÿ����Ϣ
 * 11:��Ϣ�յ����
 * 12:��Ϣ�յ����
 * 13:����������ʵ����
 * 14:�����������ʵ����         
 */

public class InterestBatch
{

	public final static long LOAN_ACCOUNT_HASOVERDUED = 9;
	//��־
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private InterestCalculation interestCalculation = null;
	private Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = null;
	private Connection conn_Out = null;
	/**
	 * ���췽��һ���Լ�����һ��Connection.
	 * 
	 */
	public InterestBatch()
	{
		try
		{
			conn_Out = Database.getConnection();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		interestCalculation = new InterestCalculation(conn_Out);
		sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn_Out);
	}

	/**
	 * ���췽��������Ҫ����һ������Connection
	 * 
	 * @param conn
	 */

	public InterestBatch(Connection connection)
	{
		this.conn_Out = connection;
		interestCalculation = new InterestCalculation(connection);
		sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(connection);
	}

	/**
	 * added by mzh_fu Ϊ����ڴ�й¶�������ӣ����ã�
	 * 
	 * �ر����ݿ�����
	 * @throws IException
	 */
	public void closeConnection() throws IException {
		try {
			if (conn_Out != null && conn_Out.isClosed() == false)
				conn_Out.close();
		} catch (SQLException e) {
			throw new IException(e.getMessage());
		}
	}
	public static void main(String[] args)
	{
		/**		
		Connection con = null;
				try
				{
					con = Database.getConnection();
				}
				catch (Exception sqle)
				{
					sqle.printStackTrace();
				}		

		//	//Timestamp date1 = new Timestamp(101, 1, 27, 0, 0, 0, 0);
		//	//	Timestamp date2 = new Timestamp(101, 5, 45, 0, 0, 0, 0);
		//
		//		System.out.println("����date1��: " + date1.toString());
		//		System.out.println("����date2�ǣ�" + date2.toString());
		
		//System.out.println("start����������������");
		
		//InterestBatch interestBatch = new InterestBatch();
		//
		//Timestamp tsCloseDate = Env.getSystemDate(1,1);
		//System.out.println("tsCloseDate = "+tsCloseDate);
		//tsCloseDate = DataFormat.getPreviousDate(tsCloseDate);
		//
		//System.out.println("tsCloseDate = "+tsCloseDate);
				InterestBatch interestBatch = new InterestBatch();
				Timestamp tsCloseDate = Env.getSystemDate(1,1);
				tsCloseDate = DataFormat.getPreviousDate(tsCloseDate);
				System.out.println("����tsCloseDate�ǣ�" + tsCloseDate);
		try {
			interestBatch.dailyCalculateInterest(con,1,1,tsCloseDate);
		}
		catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	/**
		* �ӿ����ƣ���ѯ�˻��ո�Ϣ�˻�ID
		* ������������ѯ�˻����ո�Ϣ�˻�ID����Ϣ����ʱ�á�
		* @param accountID       ���˻�ID
		* @param SubAccountID    ���˻�ID    
		* @param InterestType    �������� 1����Ϣ��2�������ѣ�3�������ѣ�4��������5����Ϣ
		* @return InterestAccountID �ö������������ԣ���Ϣ���跽���˻�ID,��Ϣ���������˻�ID.
		* @throws Exception 
		*/
	public InterestAccountIDInfo getInterestAccountID(long AccountID, long SubAccountID, long InterestType) throws IException
	{
		log.info("lhj debug info == ���˻�AccountID" + AccountID);
		log.info("lhj debug info == ���˻�SubAccountID" + SubAccountID);
		log.info("lhj debug info == ��Ϣ����InterestType" + InterestType);
		InterestAccountIDInfo interestAccountID = null;
		long sett_AccountTypeID = -1; //���˻�sett_Account���е��˻�����ID
		long ac_interestAccountID = -1; //�������˻��е�����Ϣ�˻�ID
		long PayInterestAccountID = -1; //��Ϣ���跽���˻�ID
		long ReceiveInterestAccountID = -1; //��Ϣ���������˻�ID
		Connection conn = this.conn_Out;
		//����AccountID�������˻���¼��
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		if (accountInfo != null)
		{
			sett_AccountTypeID = accountInfo.getAccountTypeID();
		}
		//����SubAccountID���Ҷ�Ӧ�����˻���¼
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
			if (subAccountAssemblerInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		//�˻����������˻���ȡ��
        log.debug("---------�ж��˻�����------------");
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        try {
			accountTypeInfo = sett_AccountTypeDAO.findByID(sett_AccountTypeID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (accountTypeInfo != null) {
			//�����˻�����ID���в�ͬ�Ĵ���	
			log.info("lhj -------------sett_AccountTypeID ==" + sett_AccountTypeID);
			//--------�����˻��������˻������м�Ϣ---------------------------------------------------------
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
			{
				PayInterestAccountID = -1;
				ReceiveInterestAccountID = -1;
			}
			//---------���ڻ����˻�--------------------------------------------------------------------     
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
			{
				if (subAccountAssemblerInfo != null)
				{
					ac_interestAccountID = subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID();
					log.debug("���˻��ǣ�["+SubAccountID + "] ��ac_interestAccountID = "+ac_interestAccountID);
					if (ac_interestAccountID < 1)
					{
						PayInterestAccountID = -1;
						//��Ϣ�˻�ID��Ϊ���˻�ID
						ReceiveInterestAccountID = AccountID;
					}
					else
					{
						PayInterestAccountID = -1;
						//��Ϣ�˻�ID�����˻��е�����Ϣ�˻�ID
						ReceiveInterestAccountID = ac_interestAccountID;
					}
				}
			}
			//---------���ڲ���˻�--------------------------------------------------------------------     
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
			{
				if (subAccountAssemblerInfo != null)
				{
					ac_interestAccountID = subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID();
					log.debug("���˻��ǣ�["+SubAccountID + "] ��ac_interestAccountID = "+ac_interestAccountID);
					//����˻��ĸ�Ϣ�˻��ǹ��õĻ����˻���Ϣ�˻��ֶ�
					PayInterestAccountID = ac_interestAccountID;
				}
			}
			//---------���ڴ����˻������ݲ�ͬ�ķ������ͣ���ѯ�˻����ո�Ϣ�˻�ID------------------------
			else if  (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
			{
				if (InterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					//����������1����Ϣ
					if (subAccountAssemblerInfo != null)
					{
						//��Ϣ�˻�ID�����˻��д����Ϣ�˻�ID			
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPayInterestAccountID();
						//��Ϣ�˻�ID�����˻���ί�з���Ϣ�˻�ID
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveInterestAccountID();
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.ASSURE)
				{
					//����������2��������
					if (subAccountAssemblerInfo != null)
					{
						//��Ϣ�˻�ID�����˻��е������˻�ID
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPaySuretyAccountID();
						//��Ϣ�˻�ID,2003-12-11   
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID();
	
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.COMMISION)
				{
					//����������3��������
					if (subAccountAssemblerInfo != null)
					{
						//��Ϣ�˻�ID�����˻����������˻�ID						
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getCommissionAccountID();
						ReceiveInterestAccountID = -1;
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
					//����������4������
					if (subAccountAssemblerInfo != null)
					{
						//��Ϣ�˻�ID�����˻��д����Ϣ�˻�ID			
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPayInterestAccountID();
						//��Ϣ�˻�ID�����˻���ί�з���Ϣ�˻�ID
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveInterestAccountID();
					}
				}
				if (InterestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
					//����������5����Ϣ
					if (subAccountAssemblerInfo != null)
					{
						//��Ϣ�˻�ID�����˻��д����Ϣ�˻�ID			
						PayInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getPayInterestAccountID();
						//��Ϣ�˻�ID�����˻���ί�з���Ϣ�˻�ID
						ReceiveInterestAccountID = subAccountAssemblerInfo.getSubAccountLoanInfo().getReceiveInterestAccountID();
					}
				}
			}
		}
		
		//���ɷ��ض���
		//ע�⣺��һ�������Ǹ�Ϣ���跽���˻�ID,�ڶ�����������Ϣ���������˻�ID       
		interestAccountID = new InterestAccountIDInfo(PayInterestAccountID, ReceiveInterestAccountID);
		log.debug(" getInterestAccountID() == ��Ϣ���跽���˻�ID" + interestAccountID.getPayInterestAccountID());
		log.debug(" getInterestAccountID() == ��Ϣ���������˻�ID" + interestAccountID.getReceiveInterestAccountID());
		return interestAccountID;
	}

	/**
	 * �ӿ����ƣ������˻�/Э���˻���Ϣ��
	 * ���������� �����˻�������Ϣʱ�򣬸������˻����е�ǰ�ۼ���Ϣ����һ��Ϣ�յ���Ϣ��
	 * ע   ��1�����ڡ�֪ͨ�������˻���׼���ô˽ӿڣ�
	 * ע   ��2�����ڽ�Ϣ�������ֽ�Ϣ�ͻ�����Ϣ��
	 * @param AccountID        ���˻�ID
	 * @param SubAccountID     ���˻�ID
	 * @param interestDate     ��Ϣ��
	 * @param PecisionInterest Ӧ��/����Ϣ
	 * @param NogotiatePecisionInterest Ӧ��/��Э����Ϣ
	 * @return long -1:���ɹ���1���ɹ�
	 * @throws IException
	 * 
	 * �޸���ʷ�����뱣֤���˻���Ϣ��jason,2006-04-10
	 */

	/*lhj 2003-11-26 ��Ҫ����Connection !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11*/
	public long clearCurrentDepositAccountInterest(long AccountID, long SubAccountID, Timestamp interestDate, double PrecisionInterest, double NegotiatePrecisionInterest) throws IException
	{
		log.info("interestBatch(322)lhj debug info == ��������˻�/Э���˻�/��֤���˻� ��Ϣ......");

		Connection conn = this.conn_Out;

		long isSuccess = -1;
		//�������˻�AccountID��sett_Account���ж�λ���˻���¼
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			log.info("---0---");
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		log.info("interestBatch(288)lhj debug info == AccountID�ǣ�"+ AccountID);
		log.info("interestBatch(288)lhj debug info == SubAccountID�ǣ�"+ SubAccountID); 
		log.info("interestBatch(288)lhj debug info == PecisionInterest�ǣ�"+ PrecisionInterest);
		log.info("interestBatch(288)lhj debug info == NegotiatePecisionInterest�ǣ�"+ NegotiatePrecisionInterest);   

		if (accountInfo != null)
		{
			//�˻����������˻���ȡ��
	        log.debug("---------�ж��˻�����------------");
			long accountTypeID = accountInfo.getAccountTypeID();
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
				//���ӿ�ֻ����ڻ����˻�����ֻ���������˻����ͣ�	
				//������׼���𡢲���˻��Ĵ���ʽͬ���� modify by bingliu 20110721
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
				{
					//�������˻�SubAccountID��sett_SubAccount���ж�λ���˻���¼��
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
						}
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
					}
					if (subAccountAssemblerInfo != null)
					{
						//boxu add ������ʱ��ż�����ͼ������ڵķ���
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
						
						//�����˻�
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						
						subAccountFixedInfo.setPreDrawInterest(subAccountCurrentInfo.getDrawInterest());  //������Ϣ
						subAccountFixedInfo.setPreDrawDate(subAccountCurrentInfo.getPreDrawDate());  //��������
						
						double interest = 0.0;  //��Ϣ����Ϣ
						double negotiateInterest = 0.0;  //��Ϣ��Э����Ϣ
						//��Ϣǰ��������Ϣ
						double oldInterest = subAccountCurrentInfo.getInterest();
						//��Ϣǰ��Э����Ϣ
						double oldNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();
						long isNegotiate = -1;  //�Ƿ���Э�����
						
						log.info("���ڽ�Ϣǰ��������Ϣ��:" + oldInterest);
						log.info("���ڽ�Ϣǰ�ĵ�Э����Ϣ��:" + oldNegotiateInterest);
						log.info("���ڽ�Ϣǰ�Ľ�Ϣ������:" + subAccountCurrentInfo.getClearInterestDate());
	
						//������Ϣ�Ľ�Ϣ
						interest = UtilOperation.Arith.sub(oldInterest, PrecisionInterest);
						
						//�Ƿ�Э�����
						isNegotiate = subAccountCurrentInfo.getIsNegotiate();
	
						if (isNegotiate == SETTConstant.BooleanValue.ISTRUE)
						{
							negotiateInterest = UtilOperation.Arith.sub(oldNegotiateInterest, NegotiatePrecisionInterest);
						}
	
						log.info("interestBatch(368)lhj debug info == ��Ϣ���������Ϣ��:" + interest);
						log.info("interestBatch(368)lhj debug info == ��Ϣ���Э����Ϣ��:" + negotiateInterest);
						log.info("interestBatch(366)lhj debug info == ��Ϣ������:" + interestDate);
						//����Ϣ��ĵ�ǰ��Ϣ�͵�ǰЭ����Ϣ��д�����˻�������
						subAccountCurrentInfo.setInterest(interest);
						subAccountCurrentInfo.setNegotiateInterest(negotiateInterest);
						//���½�Ϣ����
						subAccountCurrentInfo.setClearInterestDate(interestDate);
						
						//��ǰ����û�м������,���������˻��ڵļ������,���Խ�Ϣ����ռ�����Ϣ�ͼ�������(�ڻ�Ʒ�¼������"������Ϣ")
						subAccountCurrentInfo.setDrawInterest(0.0);
						//2008��1��28�� ���������޸ı���Ϊ��Ϣ��
						subAccountCurrentInfo.setPreDrawDate(interestDate);

						try
						{
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							if (sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo) > -1)
							{
								//Modify by chuanliu,for margin subaccount's status(finish)
								//��Ϣ�󽫱�֤���˻�״̬��Ϊ"�ѽ���"
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
								{
									if (subAccountCurrentInfo.getBalance() < 0.01 && interest < 0.01)
									{
										sett_SubAccountDAO.updateFinishDateAndStatus(subAccountCurrentInfo.getID(), SETTConstant.SubAccountStatus.FINISH, interestDate);
									}
								}
								
								log.info("����/��֤���Ϣ��ɣ��������˻��ɹ���");
								isSuccess = 1;
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
					}
				}
			}
		}

		return isSuccess;
	}

	/**
	 * �ӿ����ƣ������˻�/Э���˻���Ϣ�����ס�
	 * ���������������˻�������Ϣ��¼ɾ����ȡ������ʱ�����ô˽ӿڸ������˻���
	 * ע   ��1���������ֻ���
	 * @param AccountID     ���˻�ID
	 * @param SubAccountID  ���˻�ID
	 * @param LastInterestDate ��һ��Ϣ��
	 * @param PrecisionInterest Ӧ��/����Ϣ
	 * @param NegotiatePrecisionInterest Ӧ��/��Э����Ϣ
	 * @return long -1:���ɹ���1���ɹ�
	 * @throws IException
	 */

	public long clearCurrentDepositAccountInterestReverse(long AccountID, long SubAccountID, Timestamp LastInterestDate, double PrecisionInterest, double NegotiatePrecisionInterest) throws IException
	{
		log.info("==============�����˻�/Э���˻���Ϣ������==================");
		Connection conn = this.conn_Out;
		long isSuccess = -1;
		//�������˻�AccountID��sett_Account���ж�λ���˻���¼
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		if (accountInfo != null)
		{
	        log.debug("---------�ж��˻�����------------");
			long accountTypeID = accountInfo.getAccountTypeID();
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
				//���ӿ�ֻ����ڻ����˻�����ֻ���������˻����ͣ�			��һ����֤������ add by wjliu--2007-4-9
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) 
				{
					//�������˻�SubAccountID��sett_SubAccount���ж�λ���˻���¼��
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
						}
	
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
					}
					if (subAccountAssemblerInfo != null)
					{
						//����ȡ����ǰ�ļ�����Ϣ�ͼ�������
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
						
						//�����˻�
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						//����Ϣǰ�������Ϣ
						double interest = 0.0; //����Ϣ���������Ϣ
						double negotiateInterest = 0.0; //����Ϣ���Э����Ϣ
						double oldInterest = 0.0; //����Ϣǰ��������Ϣ
						double oldNegotiateInterest = 0.0; //����Ϣǰ��Э����Ϣ
						Timestamp oldClearInterestDate = null; //����Ϣǰ�Ľ�Ϣ����
						long isNegotiate = -1; //�Ƿ���Э�����

						//����Ϣǰ��������Ϣ
						oldInterest = subAccountCurrentInfo.getInterest();
						//����Ϣǰ��Э����Ϣ
						oldNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();
						//����Ϣǰ�Ľ�Ϣ����
						oldClearInterestDate = subAccountCurrentInfo.getClearInterestDate();
	
						//���з���Ϣ....................................................
	
						//������Ϣ�ķ���Ϣ
						interest = UtilOperation.Arith.add(oldInterest, PrecisionInterest);
						//Э����Ϣ�Ľ�Ϣ
						isNegotiate = subAccountCurrentInfo.getIsNegotiate();
						if (isNegotiate == SETTConstant.BooleanValue.ISTRUE)
						{
							//Э����Ϣ�ķ���Ϣ
							negotiateInterest = UtilOperation.Arith.add(oldNegotiateInterest, NegotiatePrecisionInterest);
						}

						log.info("================================================================================");
						log.info("���ڷ���Ϣǰ��������Ϣ��:" + oldInterest);
						log.info("���ڷ���Ϣǰ��Э����Ϣ��:" + oldNegotiateInterest);
						log.info("���ڷ���Ϣǰ�Ľ�Ϣ������:" + oldClearInterestDate);
						log.info("================================================================================");
						log.info("���ڷ���Ϣ��Ľ�Ϣ������:" + LastInterestDate);
						log.info("���ڷ���Ϣ���������Ϣ��:" + interest);
						log.info("���ڷ���Ϣ���Э����Ϣ��:" + negotiateInterest);
						log.info("================================================================================");
	
						//���ָ���һ�ν�Ϣʱ�ĵ�ǰ��Ϣ�͵�ǰЭ����Ϣ��д�����˻�������
						subAccountCurrentInfo.setInterest(interest);
						subAccountCurrentInfo.setNegotiateInterest(negotiateInterest);
						//��Ϣ���ڸ���Ϊ��һ�ν�Ϣ��
						subAccountCurrentInfo.setClearInterestDate(LastInterestDate);
						
						//������Ϣ
						subAccountCurrentInfo.setDrawInterest(subAccountFixedInfo.getPreDrawInterest());
						//��������
						subAccountCurrentInfo.setPreDrawDate(subAccountFixedInfo.getPreDrawDate());
						
						try
						{
							//�����ʱ��ŵļ�����Ϣ�ͼ�������
							subAccountFixedInfo.setPreDrawInterest(0.0);
							subAccountFixedInfo.setPreDrawDate(null);
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							
							if (sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo) > -1)
							{
								//Modify by chuanliu,for margin subaccount's status(NORMAL)
								//��Ϣ�󽫱�֤���˻�״̬��Ϊ"δ����"
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
								{
									if (subAccountCurrentInfo.getBalance() != 0 || interest > 0.01)
									{
										sett_SubAccountDAO.updateFinishDateAndStatus(subAccountCurrentInfo.getID(), SETTConstant.SubAccountStatus.NORMAL, null);
									}
								}
								
								isSuccess = 1;
								log.info("lhj debug info == ����Ϣ�ĸ������˻��ɹ�����");
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
						
					}
				}
			}
		}
		
		return isSuccess;
	}

	/**
	  * �ӿ����ƣ������˻���Ϣ/����
		* ���������������˻�������Ϣʱ�������˻���ǰ�ۼ���Ϣ���㣬�Բ��ֽ�Ϣ����ǰ�ۼ���Ϣ��ȥ�ѽ���Ϣ�����˻�
		*           һ��Ϣ���޸�Ϊ���ν�Ϣ�ա�
		* ע   ��1��ֻ��Դ����˻����������ֻ���
		* ע   ��2�����ڣ�֪ͨ�������˻���������ñ��ӿڡ�
		* ע   ��3: lhj 2004-06-03 ���ݶ������޸��ĵ����Ը÷��������޸�
		*           �����ڡ��͡��֮���Ϊ��ȥ�Ĵ���						 
		* @param  ClearLoanAccountInterestConditionInfo �����˻���Ϣ/������������
		* @return long -1:���ɹ���1���ɹ�
		* @throws IException
		*/

	public long clearLoanAccountInterest(ClearLoanAccountInterestConditionInfo clearLoanAccountInterestConditionInfo) throws IException
	{
		log.print("lhj debug info ��ʼ���д����Ϣ-------------------------------------------------------------");
		log.debug(UtilOperation.dataentityToString(clearLoanAccountInterestConditionInfo));
		Connection conn = this.conn_Out;
		long AccountID = clearLoanAccountInterestConditionInfo.getAccountID();
		//���˻�ID
		long SubAccountID = clearLoanAccountInterestConditionInfo.getSubAccountID();
		//���˻�ID
		Timestamp InterestDate = clearLoanAccountInterestConditionInfo.getInterestDate();
		//��Ϣ��
		double Interest = clearLoanAccountInterestConditionInfo.getInterest();
		//Ӧ����Ϣ
		double InterestReceivable = clearLoanAccountInterestConditionInfo.getInterestReceivable();
		//Ӧ��������Ϣ
		double SuretyFee = clearLoanAccountInterestConditionInfo.getSuretyFee();
		//Ӧ��������
		double Commision = clearLoanAccountInterestConditionInfo.getCommision();
		//Ӧ��������
		double CompoundInterest = clearLoanAccountInterestConditionInfo.getCompoundInterest();
		//Ӧ������
		double OverDueInterest = clearLoanAccountInterestConditionInfo.getOverDueInterest();
		//Ӧ�����ڷ�Ϣ
		double RealInterest = clearLoanAccountInterestConditionInfo.getRealInterest();
		//ʵ����Ϣ
		double RealInterestReceivable = clearLoanAccountInterestConditionInfo.getRealInterestReceivable();
		//ʵ��������Ϣ
		double RealSuretyFee = clearLoanAccountInterestConditionInfo.getRealSuretyFee();
		//ʵ��������
		double RealCommision = clearLoanAccountInterestConditionInfo.getRealCommision();
		//ʵ��������
		double RealCompoundInterest = clearLoanAccountInterestConditionInfo.getRealCompoundInterest();
		//ʵ������
		double RealOverDueInterest = clearLoanAccountInterestConditionInfo.getRealOverDueInterest();
		//ʵ�����ڷ�Ϣ
		long isRemitInterest = clearLoanAccountInterestConditionInfo.getIsRemitInterest();
		//�Ƿ��⻹ʣ����Ϣ
		long isRemitSuretyFee = clearLoanAccountInterestConditionInfo.getIsRemitSuretyFee();
		//�Ƿ��⻹ʣ�ൣ����
		long isRemitCommision = clearLoanAccountInterestConditionInfo.getIsRemitCommision();
		// �Ƿ��⻹ʣ��������
		long isRemitCompoundInterest = clearLoanAccountInterestConditionInfo.getIsRemitCompoundInterest();
		//�Ƿ��⻹ʣ�ิ��
		long isRemitOverDueInterest = clearLoanAccountInterestConditionInfo.getIsRemitOverDueInterest();
		//�Ƿ��⻹ʣ�����ڷ�Ϣ

		long isSuccess = -1;
		//����AccountID��sett_account���ж�λ���˻���¼
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		if (Interest > 0 && CompoundInterest > 0 && OverDueInterest > 0)
		{
			log.print("lhj debug info �����ջأ�������Ϣ֧����[��Ϣ����������Ϣ]ȫ��>0!!!");

		}
		else
		{
			if (Interest > 0)
			{
				log.print("lhj debug info ��Ϣ����Ϣ");
			}
			if (CompoundInterest > 0)
			{
				log.print("lhj debug info ��������Ϣ");
			}
			if (OverDueInterest > 0)
			{
				log.print("lhj debug info ��Ϣ����Ϣ");
			}
		}

		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		if (accountInfo != null)
		{
			//���´�
			long officeID = accountInfo.getOfficeID();
			//����
			long currencyID = accountInfo.getCurrencyID();
			//��ǰ���ػ�����
			Timestamp closeDate = null;
			closeDate = Env.getSystemDate(conn, officeID, currencyID);
	        log.debug("---------�ж��˻�����------------");
			long accountTypeID = accountInfo.getAccountTypeID();
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
				//���ӿ�ֻӦ���ڴ����˻���
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //���ڴ���
				{
					//����SubAccountID��sett_SubAccount���ж�λһ����Ӧ��¼
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
	
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
						}
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
					}
	
					if (subAccountAssemblerInfo != null)
					{
						log.print("lhj debug infp �����Ϣǰ��������--------------------------------------------------------");
						log.print("lhj debug info ����Ҫ��Ϣ��Ӧ����Ϣ��  :" + Interest);
						log.print("lhj debug info ����Ҫ��Ϣ��ʵ����Ϣ��  :" + RealInterest);
						if (isRemitInterest == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :����ʣ����Ϣ���� ");
						}
						log.print("lhj debug info ����Ҫ��Ϣ��Ӧ��������  :" + CompoundInterest);
						log.print("lhj debug info ����Ҫ��Ϣ��ʵ��������  :" + RealCompoundInterest);
						if (isRemitCompoundInterest == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :����ʣ�ิ������ ");
						}
						log.print("lhj debug info ����Ҫ��Ϣ��Ӧ����������:" + Commision);
						log.print("lhj debug info ����Ҫ��Ϣ��ʵ����������:" + RealCommision);
						if (isRemitCommision == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :����ʣ�������ѣ��� ");
						}
						log.print("lhj debug info ����Ҫ��Ϣ��Ӧ����Ϣ��  :" + OverDueInterest);
						log.print("lhj debug info ����Ҫ��Ϣ��ʵ����Ϣ��  :" + RealOverDueInterest);
						if (isRemitOverDueInterest == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :����ʣ�����ڷ�Ϣ���� ");
						}
						log.print("lhj debug info ����Ҫ��Ϣ��Ӧ����������:" + SuretyFee);
						log.print("lhj debug info ����Ҫ��Ϣ��ʵ����������:" + RealSuretyFee);
						if (isRemitSuretyFee == SETTConstant.BooleanValue.ISTRUE){
							log.print("lhj debug info =====================   :����ʣ�ൣ���ѣ��� ");
						}
						log.print("lhj debug info ����Ҫ��Ϣ��Ӧ��������Ϣ:" + InterestReceivable);
						log.print("lhj debug info ����Ҫ��Ϣ��ʵ��������Ϣ:" + RealInterestReceivable);
						log.print("lhj debug info ����Ҫ��Ϣ�Ľ�Ϣ������  :" + InterestDate);
						log.print("lhj debug infp �����Ϣǰ��������--------------------------------------------------------");
						//�����˻�
						SubAccountLoanInfo subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
						//�����˻���2003-12-12 ����������Ϣ����
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						//�����˻���2004-03-03 ��������ǷϢ��
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
						//���ݲ���ֵ��������Ӧ�Ľ�Ϣ����
						
                        //�ϴμ������� modify by xwhe 2009-03-23
						Timestamp al_dtpredraw = subAccountLoanInfo.getPreDrawDate();
						/**
						if (Interest > 0 ){
							subAccountLoanInfo.setClearInterestDate(InterestDate);
						}
						if (SuretyFee > 0){
							subAccountLoanInfo.setClearSureFeeDate(InterestDate);
						}
						if (Commision > 0){
							subAccountLoanInfo.setClearCommissionDate(InterestDate);
						}
						if (CompoundInterest > 0){
							subAccountLoanInfo.setClearCompoundDate(InterestDate);
						}
						if (OverDueInterest > 0 ){	
							subAccountLoanInfo.setClearOverDueDate(InterestDate);
						}
						*/
						
						//Boxu Update 2009��02��16�� ����ڵ������������Ѳ��ı��Ϣ���޸�
						//��Ϣ
						if(Interest > 0)  //Ӧ����Ϣ������
						{
							if (isRemitInterest == SETTConstant.BooleanValue.ISTRUE)  //����ʣ����Ϣ
							{
								subAccountLoanInfo.setClearInterestDate(InterestDate);
								subAccountLoanInfo.setPreDrawDate(InterestDate);
							}
							if (UtilOperation.Arith.round(Interest,2) == UtilOperation.Arith.round(RealInterest,2))  //ʵ����Ϣ����Ӧ����Ϣ
							{
								subAccountLoanInfo.setClearInterestDate(InterestDate);
								subAccountLoanInfo.setPreDrawDate(InterestDate);
							}
						}
						
						//������
						if (SuretyFee > 0)
						{
							if (isRemitSuretyFee == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearSureFeeDate(InterestDate);
							}
							if (UtilOperation.Arith.round(SuretyFee,2) == UtilOperation.Arith.round(RealSuretyFee,2))
							{
								subAccountLoanInfo.setClearSureFeeDate(InterestDate);
							}
						}
						
						//������
						if (Commision > 0)
						{
							if (isRemitCommision == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearCommissionDate(InterestDate);
							}
							if (UtilOperation.Arith.round(Commision,2) == UtilOperation.Arith.round(RealCommision,2))
							{
								subAccountLoanInfo.setClearCommissionDate(InterestDate);
							}
						}
						
						//����
						if (CompoundInterest > 0)
						{
							if (isRemitCompoundInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearCompoundDate(InterestDate);
							}
							if (UtilOperation.Arith.round(CompoundInterest,2) == UtilOperation.Arith.round(RealCompoundInterest,2))
							{
								subAccountLoanInfo.setClearCompoundDate(InterestDate);
							}
						}
						
						//��Ϣ
						if (OverDueInterest > 0 )
						{
							if (isRemitOverDueInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearOverDueDate(InterestDate);
							}
							if (UtilOperation.Arith.round(OverDueInterest,2) == UtilOperation.Arith.round(RealOverDueInterest,2))
							{
								subAccountLoanInfo.setClearOverDueDate(InterestDate);
							}
						}
	
						//������ʱ����
						double tmpInterest                 = subAccountLoanInfo.getInterest(); //��Ϣ
						double tmpPreDrawInterest          = subAccountLoanInfo.getPreDrawInterest(); //������Ϣ
						double tmpSuretyFee                = subAccountLoanInfo.getSuretyFee(); //������
						double tmpCommission               = subAccountLoanInfo.getCommission(); //������
						double tmpCompoundInterest         = subAccountLoanInfo.getCompoundInterest(); //����
						double tmpOverDueInterest          = subAccountLoanInfo.getOverDueInterest(); //��Ϣ
						double tmpArrearageInterest        = subAccountLoanInfo.getArrearageInterest(); //�ۼ�ǷϢ					
						double tmpOverDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest(); //����ǷϢ
	
						/*-----��ʱ����Ϣǰ����liu����Ϣֵ������AC_������------*/
						if (tmpArrearageInterest > 0)
						{
							subAccountCurrentInfo.setCapitalLimitAmount(tmpArrearageInterest); //�ۼ�ǷϢ��Ӧ���ʽ����ƽ��
						}
						if (SuretyFee > 0){
							subAccountCurrentInfo.setFirstLimitAmount(tmpSuretyFee); //�����Ѷ�Ӧ��һ͸֧���
						}
						if (Commision > 0){
							subAccountCurrentInfo.setSecondLimitAmount(tmpCommission); //�����Ѷ�Ӧ�ڶ�͸֧���
						}
						if (CompoundInterest > 0){
							subAccountCurrentInfo.setThirdLimitAmount(tmpCompoundInterest); //������Ӧ����͸֧���
						}
						if (OverDueInterest > 0){
							subAccountCurrentInfo.setMonthLimitAmount(tmpOverDueInterest); //��Ϣ��Ӧ�¶�͸֧���
						}
						subAccountCurrentInfo.setNegotiateInterest(tmpPreDrawInterest); //������Ϣ��ӦЭ����Ϣ
//						if (tmpOverDueArrearageInterest > 0)
//						{
//							if (Interest > 0)
//							{
//								subAccountCurrentInfo.setNegotiateAmount(tmpOverDueArrearageInterest); //����ǷϢ��Ӧ��Э����� 2004-01-013	
//								subAccountCurrentInfo.setNegotiateUnit(tmpOverDueArrearageInterest);
//								subAccountFixedInfo.setPreDrawInterest(tmpOverDueArrearageInterest);
//							}
//							if (CompoundInterest > 0)
//							{
//								subAccountCurrentInfo.setNegotiateUnit(tmpOverDueArrearageInterest);
//								subAccountFixedInfo.setPreDrawInterest(tmpOverDueArrearageInterest);
//							}
//							if (OverDueInterest > 0)
//							{
//								subAccountFixedInfo.setPreDrawInterest(tmpOverDueArrearageInterest);
//							}
//						}
						/*-----��ʱ����Ϣǰ����liu����Ϣֵ������AC_������------*/
						log.print("===========================================================================");
						log.print("lhj debug infp ���˻��и�������--------------------------------------------------------");
						log.print("lhj debug info ���˻��е�������Ϣ�� :" + tmpInterest);
						log.print("lhj debug info ���˻��еļ�����Ϣ�� :" + tmpPreDrawInterest);
						log.print("lhj debug info ���˻��еĸ�����     :" + tmpCompoundInterest);
						log.print("lhj debug info ���˻��е���������   :" + tmpCommission);
						log.print("lhj debug info ���˻��еĵ�������   :" + tmpSuretyFee);
						log.print("lhj debug info ���˻��еķ�Ϣ��     :" + tmpOverDueInterest);
						log.print("lhj debug info ���˻��е��ۼ�ǷϢ�� :" + tmpArrearageInterest);
						log.print("lhj debug info ���˻��е�����ǷϢ�� :" + tmpOverDueArrearageInterest);
						log.print("lhj debug info ����Ľ�Ϣ������     :" + InterestDate);
						log.print("===========================================================================");
						//�ſ�֪ͨ��ID
						long loanNoteID = subAccountLoanInfo.getLoanNoteID();
						UtilOperation utilOperation = new UtilOperation(conn);
						//�ж��Ƿ��Ѿ�����
						//OverDueInfo overDueInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);
						//�Ƿ���ո�����
						//long isCompoundInterest = -1;
						//�Ƿ��Ѿ�����
						//long isOverDue = -1;
						
						log.print("�ж��Ƿ����ڣ��Ƿ���ո���..................................");
						/*if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
						{
							isOverDue = 1; //�Ѿ����ڣ���
							isCompoundInterest = overDueInfo.getIsCompoundInterest();
							if (isCompoundInterest == Constant.YesOrNo.YES)
							{
								log.print("�������������������������ڣ��������ո�����������������������������");
							}
							else
							{
								log.print("�������������������������ڣ����������ո���������������������������");
							}
						}*/
						
						//��Ϣ������������Ϣ�Ͳ��ֽ�����Ϣ
						log.print("��ʼ��ʽ���н�Ϣ������Ϣ����֧���������������������������������������������");
						if (Interest > 0)
						{
							double oddInterest         = 0.0;
							double oddCompoundInterest = 0.0;  //����Ϣ���ʣ�ิ��
							double oddForFeitInterest  = 0.0;  //����Ϣ���ʣ�෣Ϣ
							
							//Boxu Update 2008��4��23�� �޸��⻹���߼�
							if ((UtilOperation.Arith.round(Interest, 2) > UtilOperation.Arith.round(RealInterest, 2)) && isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								tmpInterest = UtilOperation.Arith.sub(tmpInterest, Interest);
							}
							//else if (UtilOperation.Arith.round(Interest, 2) == UtilOperation.Arith.round(RealInterest, 2))
							//{
							//	tmpInterest = UtilOperation.Arith.sub(tmpInterest, Interest);
						    //}
							else
							{
								tmpInterest = UtilOperation.Arith.sub(tmpInterest, RealInterest);
						    }
							
							/**
							 * Boxu Update 2009��02��16��
							 * ���˻���Ϣ - ʵ����Ϣ���н�λ��û�н�λ��������� = ʣ����Ϣ
							 * ��λ��ʣ����ϢΪ��������ϵͳ���˴���
							 * ����λ��ʣ����Ϣ����һ��С��0.01������
							 * ���������д���
							 */
							//if(tmpInterest < 0)
							if(tmpInterest < 0.01)
							{
								tmpInterest = 0.0;
							}
						
							//Boxu Update 2008��4��23�� ������Ϣ
							if (UtilOperation.Arith.round(InterestReceivable, 2) > UtilOperation.Arith.round(RealInterestReceivable, 2) && isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, InterestReceivable);
							}
							else
							{
								tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, RealInterestReceivable);
							}
							
							if(tmpPreDrawInterest < 0)
							{
								tmpPreDrawInterest = 0.0;
							}
							
							//��ʱ�����������
							if (subAccountLoanInfo.getPreDrawDate() != null)
							{
							//	subAccountFixedInfo.setPreDrawDate(subAccountLoanInfo.getPreDrawDate());
								subAccountFixedInfo.setPreDrawDate(al_dtpredraw);
								
							}
						}

						//������Ϣ�������⣬δ���岿�ֱ��������ݿ����Ա��պ������RealInterestReceivable
						/*if (InterestReceivable > 0)
						{
							if (isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								//tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, InterestReceivable);
								tmpPreDrawInterest = 0.0;
							}
							else
							{
								tmpPreDrawInterest = UtilOperation.Arith.sub(tmpPreDrawInterest, RealInterestReceivable);
							}
						}*/
							
						//�������ۼ�δ�����
						if (SuretyFee > 0)
						{
							log.print("��ʼ�ᵣ����..............................start.....");
							if ((UtilOperation.Arith.round(SuretyFee, 2) == UtilOperation.Arith.round(RealSuretyFee, 2)) || isRemitSuretyFee == SETTConstant.BooleanValue.ISTRUE)
								tmpSuretyFee = 0;
							else
								tmpSuretyFee = UtilOperation.Arith.sub(SuretyFee, RealSuretyFee);
							log.print("��ʼ�ᵣ����..............................end.....");
						}
						
						//�������ۼ�δ�����
						if (Commision > 0)
						{
							log.print("��ʼ��������..............................start.....");
							if ((UtilOperation.Arith.round(Commision, 2) == UtilOperation.Arith.round(RealCommision, 2)) || isRemitCommision == SETTConstant.BooleanValue.ISTRUE)
								tmpCommission = 0;
							else
								tmpCommission = UtilOperation.Arith.sub(Commision, RealCommision);
							log.print("��ʼ��������..............................end.....");
						}
						
						//�����ۼ�δ�����
						if (CompoundInterest > 0 )
						{
							log.print("��ʼ�Ḵ��..............................start.....");
							if ((UtilOperation.Arith.round(CompoundInterest, 2) == UtilOperation.Arith.round(RealCompoundInterest, 2)) || isRemitCompoundInterest == SETTConstant.BooleanValue.ISTRUE){
								tmpCompoundInterest = 0;
							}else{
								tmpCompoundInterest = UtilOperation.Arith.sub(tmpCompoundInterest, RealCompoundInterest);
							}
						}
	
						//���ڷ�Ϣ�ۼ�δ�����
						if (OverDueInterest > 0 )
						{
							if (isRemitOverDueInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								//tmpOverDueInterest = 0.0;
								tmpOverDueInterest = UtilOperation.Arith.sub(subAccountLoanInfo.getOverDueInterest(), OverDueInterest);
							}
							else
							{
								log.print("��ʼ�����ڷ�Ϣ..............................start.....");
								/*if ((UtilOperation.Arith.round(OverDueInterest, 2) == UtilOperation.Arith.round(RealOverDueInterest, 2))) {
									tmpOverDueInterest = 0.0;
								}
								else {
									tmpOverDueInterest = UtilOperation.Arith.sub(OverDueInterest, RealOverDueInterest);
									
								}*/
								tmpOverDueInterest = UtilOperation.Arith.sub(subAccountLoanInfo.getOverDueInterest(), RealOverDueInterest);
								
								log.print("��ʼ�����ڷ�Ϣ..............................end.....");
							}

						}
						
						
						
						LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
						if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE)
						{
							if(!InterestDate.before(closeDate))
							{
								//��Ϣ����֧������Ϣ��Ϣ�������ۼ�ǷϢ
								//�������׻�Ϣ����Ҫ�ж��Ƿ���Ҫ����ۼ�ǷϢ
								InterestsInfo preinterestsInfo = null;
								InterestsInfo todayinterestsInfo = null;
								double payInterest = 0;
								double payCompoundInterest = 0;
								double payOverDueInterest = 0;
								try
								{
									preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
									todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
									payInterest = preinterestsInfo.getPayminterest()+todayinterestsInfo.getPayminterest();
									payCompoundInterest = preinterestsInfo.getPaymcompoundinterest()+todayinterestsInfo.getPaymcompoundinterest();
									payOverDueInterest = preinterestsInfo.getPaymforfeitinterest()+todayinterestsInfo.getPaymforfeitinterest();
								}
								catch(Exception e)
								{
									throw new IException(e.getMessage());
								}
								if(Interest>0)
								{
									if(preinterestsInfo.getMinterest()-payInterest>RealInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,RealInterest);
									}
									else if(preinterestsInfo.getMinterest()-payInterest<RealInterest&&preinterestsInfo.getMinterest()>payInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),payInterest));
									}
									else
									{
										tmpArrearageInterest = 0;
									}
								}
								if(CompoundInterest>0)
								{
									if(preinterestsInfo.getMcompoundinterest()-payCompoundInterest>RealCompoundInterest)
									{
										tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,RealCompoundInterest);
									}
									else if(preinterestsInfo.getMcompoundinterest()-payCompoundInterest<RealCompoundInterest&&preinterestsInfo.getMcompoundinterest()>payCompoundInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),payCompoundInterest));
									}
									else
									{
										tmpArrearageInterest = 0;
									}
								
								}
								if(OverDueInterest>0)
								{
									if(preinterestsInfo.getMforfeitinterest()-payOverDueInterest>RealOverDueInterest)
									{
										tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,RealOverDueInterest);
									}
									else if(preinterestsInfo.getMforfeitinterest()-payOverDueInterest<RealOverDueInterest&&preinterestsInfo.getMforfeitinterest()>payOverDueInterest)
									{
										tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),payOverDueInterest));
									}
									else
									{
										tmpArrearageInterest = 0;
									}
								}
							}
							else
							{
								//��ѯ���ջ������
								InterestsInfo returnInfo  = interestCalculation.calculationLoanAccountCompoundInterest(conn, officeID, currencyID, AccountID, SubAccountID, InterestDate, closeDate, RealCompoundInterest, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
								//���ݵ��ջ�������¼��㵱�ո����ۼ�
								tmpCompoundInterest = returnInfo.getMcompoundinterest() ;
								tmpArrearageInterest =returnInfo.getArrearageInterest() ;
							}
							if(tmpArrearageInterest<0.01)
							{
								tmpArrearageInterest = 0;
							}
						}
						//Ϊ�����˻�����ֵ
						subAccountLoanInfo.setInterest(tmpInterest);
						subAccountLoanInfo.setSuretyFee(tmpSuretyFee);
						subAccountLoanInfo.setPreDrawInterest(tmpPreDrawInterest);
						subAccountLoanInfo.setCommission(tmpCommission);
						subAccountLoanInfo.setCompoundInterest(tmpCompoundInterest);
						subAccountLoanInfo.setOverDueInterest(tmpOverDueInterest);
						subAccountLoanInfo.setArrearageInterest(tmpArrearageInterest);
						//2004-1-29
						subAccountLoanInfo.setOverDueArrearageInterest(tmpOverDueArrearageInterest);
						
						//Boxu add 2007-6-27 �޸����˻���������
						//Boxu update 2009-2-13 �ƶ����ж��Ƿ�����Ϣ��֮��
						//subAccountLoanInfo.setPreDrawDate(InterestDate);
						
						log.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						log.print("lhj debug infp �����Ϣ���������--------------------------------------------------------");
						log.print("lhj debug info �����Ϣ���������Ϣ��:" + tmpInterest);
						log.print("lhj debug info �����Ϣ��ļ�����Ϣ��:" + tmpPreDrawInterest);
						log.print("lhj debug info �����Ϣ��ĸ�����    :" + tmpCompoundInterest);
						log.print("lhj debug info �����Ϣ�����������  :" + tmpCommission);
						log.print("lhj debug info �����Ϣ��ĵ�������  :" + tmpSuretyFee);
						log.print("lhj debug info �����Ϣ��ķ�Ϣ��    :" + tmpOverDueInterest);
						log.print("lhj debug info �����Ϣ����ۼ�ǷϢ��:" + tmpArrearageInterest);
						log.print("lhj debug info �����Ϣ�������ǷϢ��:" + tmpOverDueArrearageInterest);
						log.print("lhj debug info ����Ľ�Ϣ������      :" + InterestDate);
						log.print("lhj debug infp �����Ϣ���������--------------------------------------------------------");
	
						//update���˻���sett_SubAccount
						try
						{
							//2003-12-12 �ڻ����˻��б������ַ���
							subAccountCurrentInfo.setInterest(tmpInterest);
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							if (sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo) > -1)
							{
								isSuccess = 1;
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
					}
				}
			}
		}
		return isSuccess;

	}

	/**
	 * �ӿ����ƣ������˻���Ϣ/���÷�����
	 * ���������������˻�������Ϣ/���÷�����ʱ���ָ����˻���ǰ�ۼ���Ϣ����һ��Ϣ�յ���ϢΪ��Ϣǰ����Ϣ��
	 * ע   ��1��ֻ��Դ����˻������������˻���
	 * ע   ��2�����ڣ�֪ͨ�������˻���������ô˽ӿڡ� 
	 * @param  ClearLoanAccountInterestConditionInfo �����˻���Ϣ/������������
	 * @return long -1:���ɹ���1���ɹ�
	 * @throws IException
	 */
	/*lhj 2003-11-26 ��Ҫ����Connection !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11*/
	public long clearLoanAccountInterestReverse(ClearLoanAccountInterestConditionInfo clearLoanAccountInterestConditionInfo) throws IException
	{
		//log.info("lhj debug infp ��ʼ���д����Ϣ-------------------------------------------------------------");
		log.debug(UtilOperation.dataentityToString(clearLoanAccountInterestConditionInfo));
		Connection conn = this.conn_Out;
		long AccountID = clearLoanAccountInterestConditionInfo.getAccountID(); //���˻�ID
		long SubAccountID = clearLoanAccountInterestConditionInfo.getSubAccountID(); //���˻�ID
		Timestamp LastInterestDate = clearLoanAccountInterestConditionInfo.getInterestDate(); //��һ��Ϣ��
		log.info("lhj debug info ��һ��Ϣ�� = " + LastInterestDate + " !!!!!!!!!!!!!!!!");
		double Interest = clearLoanAccountInterestConditionInfo.getInterest(); //Ӧ����Ϣ
		double InterestReceivable = clearLoanAccountInterestConditionInfo.getInterestReceivable(); //Ӧ��������Ϣ
		double SuretyFee = clearLoanAccountInterestConditionInfo.getSuretyFee(); //Ӧ��������
		double Commision = clearLoanAccountInterestConditionInfo.getCommision(); //Ӧ��������
		double CompoundInterest = clearLoanAccountInterestConditionInfo.getCompoundInterest(); //Ӧ������
		double OverDueInterest = clearLoanAccountInterestConditionInfo.getOverDueInterest(); //Ӧ�����ڷ�Ϣ
		double RealInterest = clearLoanAccountInterestConditionInfo.getRealInterest(); //ʵ����Ϣ
		double RealInterestReceivable = clearLoanAccountInterestConditionInfo.getRealInterestReceivable(); //ʵ��������Ϣ
		double RealSuretyFee = clearLoanAccountInterestConditionInfo.getRealSuretyFee(); //ʵ��������
		double RealCommision = clearLoanAccountInterestConditionInfo.getRealCommision(); //ʵ��������
		double RealCompoundInterest = clearLoanAccountInterestConditionInfo.getRealCompoundInterest(); //ʵ������
		double RealOverDueInterest = clearLoanAccountInterestConditionInfo.getRealOverDueInterest(); //ʵ�����ڷ�Ϣ
		long isRemitInterest = clearLoanAccountInterestConditionInfo.getIsRemitInterest(); //�Ƿ��⻹ʣ����Ϣ
		long isRemitSuretyFee = clearLoanAccountInterestConditionInfo.getIsRemitSuretyFee(); //�Ƿ��⻹ʣ�ൣ����
		long isRemitCommision = clearLoanAccountInterestConditionInfo.getIsRemitCommision(); // �Ƿ��⻹ʣ��������
		long isRemitCompoundInterest = clearLoanAccountInterestConditionInfo.getIsRemitCompoundInterest(); //�Ƿ��⻹ʣ�ิ��
		long isRemitOverDueInterest = clearLoanAccountInterestConditionInfo.getIsRemitOverDueInterest(); //�Ƿ��⻹ʣ�����ڷ�Ϣ

		long isSuccess = -1;

		//����AccountID��sett_account���ж�λ���˻���¼
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		if (accountInfo != null)
		{
	        log.debug("---------�ж��˻�����------------");
			long accountTypeID = accountInfo.getAccountTypeID();
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
				//���ӿ�ֻӦ���ڴ����˻���
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) //���ڴ���
				{
					//����SubAccountID��sett_SubAccount���ж�λһ����Ӧ��¼
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
					SubAccountAssemblerInfo subAccountAssemblerInfo;
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
						}
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
					}
					if (subAccountAssemblerInfo != null)
					{
						log.info("lhj debug info �����Ϣǰ��������--------------------------------------------------------");
						log.info("lhj debug info == ��һ��Ϣ���ǣ�" + LastInterestDate);
	
						log.info("lhj debug info �����Ϣǰ��Ӧ����Ϣ��  :" + Interest);
						log.info("lhj debug info �����Ϣǰ��ʵ����Ϣ��  :" + RealInterest);
						log.info("lhj debug info �����Ϣǰ��Ӧ��������  :" + CompoundInterest);
						log.info("lhj debug info �����Ϣǰ��ʵ��������  :" + RealCompoundInterest);
						log.info("lhj debug info �����Ϣǰ��Ӧ����������:" + Commision);
						log.info("lhj debug info �����Ϣǰ��ʵ����������:" + RealCommision);
						log.info("lhj debug info �����Ϣǰ��Ӧ�����ڷ�Ϣ:" + OverDueInterest);
						log.info("lhj debug info �����Ϣǰ��ʵ�����ڷ�Ϣ:" + RealOverDueInterest);
						log.info("lhj debug info �����Ϣǰ��Ӧ����������:" + SuretyFee);
						log.info("lhj debug info �����Ϣǰ��ʵ����������:" + RealSuretyFee);
						log.info("lhj debug info �����Ϣǰ��Ӧ��������Ϣ:" + InterestReceivable);
						log.info("lhj debug info �����Ϣǰ��ʵ��������Ϣ:" + RealInterestReceivable);
	
						log.info("lhj debug infp �����Ϣǰ��������--------------------------------------------------------");
	
						//�����˻�
						SubAccountLoanInfo subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
						//�����˻���2004-01-14 �����Ž�Ϣǰ�����ַ�����Ϣֵ��
						SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
						//�����˻���2004-03-03 ����������ǷϢ��
						SubAccountFixedInfo subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
	
						//���ݲ���ֵ��������Ӧ�Ľ�Ϣ����
						if (Interest > 0&&UtilOperation.Arith.round(Interest,2)==UtilOperation.Arith.round(RealInterest,2))
							subAccountLoanInfo.setClearInterestDate(LastInterestDate);
						if (SuretyFee > 0)
							subAccountLoanInfo.setClearSureFeeDate(LastInterestDate);
						if (Commision > 0)
							subAccountLoanInfo.setClearCommissionDate(LastInterestDate);
						if (OverDueInterest > 0&&UtilOperation.Arith.round(OverDueInterest,2)==UtilOperation.Arith.round(RealOverDueInterest,2))
							subAccountLoanInfo.setClearOverDueDate(LastInterestDate);
						if (CompoundInterest > 0&&UtilOperation.Arith.round(CompoundInterest,2)==UtilOperation.Arith.round(RealCompoundInterest,2))
							subAccountLoanInfo.setClearCompoundDate(LastInterestDate);
						//������ʱ����
						double tmpInterest = subAccountLoanInfo.getInterest();
						double tmpPreDrawInterest = subAccountLoanInfo.getPreDrawInterest();
						double tmpSuretyFee = subAccountLoanInfo.getSuretyFee();
						double tmpCommission = subAccountLoanInfo.getCommission();
						double tmpCompoundInterest = subAccountLoanInfo.getCompoundInterest();
						double tmpOverDueInterest = subAccountLoanInfo.getOverDueInterest();
						double tmpArrearageInterest = subAccountLoanInfo.getArrearageInterest();
						//����ǷϢ
						double tmpOverDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest();
	
						//�ӻ����˻���ȡ����Ϣǰ�ķ���ֵ
						double trueSuretyFee = subAccountCurrentInfo.getFirstLimitAmount(); //��һ͸֧����Ӧ��������
						double trueCommission = subAccountCurrentInfo.getSecondLimitAmount(); //�ڶ�͸֧����Ӧ��������
						double trueCompoundInterest = subAccountCurrentInfo.getThirdLimitAmount(); //����͸֧����Ӧ������ 
						double trueOverDueInterest = subAccountCurrentInfo.getMonthLimitAmount(); //�¶��ۼ�����Ӧ����Ϣ
						double trueArrearageInterest = subAccountCurrentInfo.getCapitalLimitAmount(); //�ʽ���������Ӧ���ۼ�ǷϢ
						double truePredrawInterest = subAccountCurrentInfo.getNegotiateInterest(); //��Ӧ�ڴ��������Ϣ
						//����ǷϢ
						double trueOverDueArrearageInterest = 0;
						if (Interest > 0)
							trueOverDueArrearageInterest = subAccountCurrentInfo.getNegotiateAmount(); //��Ӧ�ڴ�������ǷϢ 2004-01-13
						if (CompoundInterest > 0)
							trueOverDueArrearageInterest = subAccountCurrentInfo.getNegotiateUnit();
						if (OverDueInterest > 0)
							trueOverDueArrearageInterest = subAccountFixedInfo.getPreDrawInterest();
	
						log.info("===========================================================================");
						log.info("lhj debug inf  Ŀǰ���˻��и�������--------------------------------------------------------");
						log.info("lhj debug info ���˻��е�������Ϣ��:" + tmpInterest);
						log.info("lhj debug info ���˻��еļ�����Ϣ��:" + tmpPreDrawInterest);
						log.info("lhj debug info ���˻��еĸ�����    :" + tmpCompoundInterest);
						log.info("lhj debug info ���˻��е���������  :" + tmpCommission);
						log.info("lhj debug info ���˻��еĵ�������  :" + tmpSuretyFee);
						log.info("lhj debug info ���˻��еķ�Ϣ��    :" + tmpOverDueInterest);
						log.info("lhj debug info ���˻��е��ۼ�ǷϢ��    :" + tmpArrearageInterest);
						log.info("lhj debug info ���˻��е�����ǷϢ��    :" + tmpOverDueArrearageInterest);
						log.info("===========================================================================");
	
						//��Ϣ������������Ϣ�Ͳ��ֽ�����Ϣ
						if (Interest > 0)
						{
							log.info("lhj debug info �����Ϣ UtilOperation.Arith.round(Interest,2)== " + UtilOperation.Arith.round(Interest, 2));
							log.info("lhj debug info �����Ϣ UtilOperation.Arith.round(RealInterest,2)== " + UtilOperation.Arith.round(RealInterest, 2));
							log.info("isRemitInterest = " + isRemitInterest);
							if ((UtilOperation.Arith.round(Interest, 2) == UtilOperation.Arith.round(RealInterest, 2)) || isRemitInterest == SETTConstant.BooleanValue.ISTRUE)
							{
								log.info("lhj debug info == �����Ϣ == ��ȣ�");
								tmpInterest = UtilOperation.Arith.add(tmpInterest, Interest);
							}
							else
							{
								log.info("lhj debug info == �����Ϣ == �����!!!!");
								tmpInterest = UtilOperation.Arith.add(tmpInterest, RealInterest);
							}
	
						}
	
						//������Ϣ�������⣬δ���岿�ֱ��������ݿ����Ա��պ������
						tmpPreDrawInterest = truePredrawInterest;
						//�������ۼ�δ�����
						if (SuretyFee > 0)
							tmpSuretyFee = trueSuretyFee;
						//�������ۼ�δ�����
						if (Commision > 0)
							tmpCommission = trueCommission;
						//�����ۼ�δ�����
						if (CompoundInterest > 0)
						{
							//						trueOverDueArrearageInterest = subAccountCurrentInfo.getNegotiateUnit();
							//						if (OverDueInterest > 0)
							//							trueOverDueArrearageInterest = subAccountFixedInfo.getPreDrawInterest();
							tmpCompoundInterest = trueCompoundInterest;
						}
						//���ڷ�Ϣ�ۼ�δ�����
						if (OverDueInterest > 0)
							tmpOverDueInterest = trueOverDueInterest;
						//trueOverDueArrearageInterest = subAccountFixedInfo.getPreDrawInterest();
	
						log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
						log.info("lhj debug info �����Ϣ���������--------------------------------------------------------");
	
						log.info("lhj debug info �����Ϣ���������Ϣ��:" + tmpInterest);
						log.info("lhj debug info �����Ϣ��ļ�����Ϣ��:" + tmpPreDrawInterest);
						log.info("lhj debug info �����Ϣ��ĸ�����    :" + tmpCompoundInterest);
						log.info("lhj debug info �����Ϣ�����������  :" + tmpCommission);
						log.info("lhj debug info �����Ϣ��ĵ�������  :" + tmpSuretyFee);
						log.info("lhj debug info �����Ϣ��ķ�Ϣ��    :" + tmpOverDueInterest);
						log.info("lhj debug info �����Ϣ����ۼ�ǷϢ�����    :" + trueArrearageInterest);
						log.info("lhj debug info �����Ϣ�������ǷϢ�����    :" + trueOverDueArrearageInterest);
						log.info("lhj debug info �����Ϣ���������--------------------------------------------------------");
	
						//Ϊ�����˻�����ֵ
						subAccountLoanInfo.setInterest(tmpInterest);
						subAccountLoanInfo.setSuretyFee(tmpSuretyFee);
						
						//Boxu Add 2008��3��4�� Ϊ���޸�"����","��Ϣ"��ɾ��ʱ��д������Ϣ�ͼ������ڵ�����
						if(Interest>0)
						{
							subAccountLoanInfo.setPreDrawInterest(tmpPreDrawInterest);
						}
						Timestamp closeDate = Env.getSystemDate(accountInfo.getOfficeID(), accountInfo.getCurrencyID());
						UtilOperation utilOperation = new UtilOperation(conn);
						LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(subAccountLoanInfo.getLoanNoteID());
						if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE)
						{
							DailyAccountBalanceInfo predaily =null;
							InterestsInfo preinterestsInfo = null;
							InterestsInfo todayinterestsInfo = null;
							double payInterest = 0;
							double payCompoundInterest = 0;
							double payOverDueInterest = 0.0;
							try
							{
								predaily  = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, accountInfo.getOfficeID(), accountInfo.getCurrencyID(), getNextNDay(closeDate, -1));
								preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
								todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),closeDate);
								if(clearLoanAccountInterestConditionInfo.getClearInterestDate().equals(closeDate))
								{
									payInterest = todayinterestsInfo.getPayminterest()-RealInterest;
									payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest()-RealCompoundInterest;
									payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest()-RealOverDueInterest;
								}
								else
								{
									payInterest = todayinterestsInfo.getPayminterest();
									payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest();
									payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest();
								}
								tmpArrearageInterest = predaily.getAl_mArrearageInterest();
								tmpCompoundInterest = predaily.getMcompoundinterest();
							}
							catch(Exception e)
							{
								throw new IException(e.getMessage());
							}
								if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>payInterest)
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,payInterest);
								}
								else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<payInterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
								}
								if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>payCompoundInterest)
								{
									tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payCompoundInterest);
								}
								else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<payCompoundInterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
								}
								if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>payOverDueInterest)
								{
									tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payOverDueInterest);
								}
								else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<payOverDueInterest&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
								{
									tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
								}
							tmpCompoundInterest = UtilOperation.Arith.sub(tmpCompoundInterest,payCompoundInterest);
						}
						if(tmpArrearageInterest<0.01)
						{
							tmpArrearageInterest = 0;
						}
						subAccountLoanInfo.setCommission(tmpCommission);
						subAccountLoanInfo.setCompoundInterest(tmpCompoundInterest);
						subAccountLoanInfo.setOverDueInterest(tmpOverDueInterest);
						subAccountLoanInfo.setArrearageInterest(tmpArrearageInterest);
						subAccountLoanInfo.setOverDueArrearageInterest(trueOverDueArrearageInterest);
	
						//update���˻���sett_SubAccount
						try
						{
							subAccountCurrentInfo.setFirstLimitAmount(0);
							subAccountCurrentInfo.setSecondLimitAmount(0);
							subAccountCurrentInfo.setThirdLimitAmount(0);
							subAccountCurrentInfo.setMonthLimitAmount(0);
							subAccountCurrentInfo.setCapitalLimitAmount(0);
	
							if (Interest > 0)
								subAccountCurrentInfo.setNegotiateAmount(0);
							if (CompoundInterest > 0)
								subAccountCurrentInfo.setNegotiateUnit(0);
							if (OverDueInterest > 0)
								subAccountFixedInfo.setPreDrawInterest(0);
							
							//��ԭ��������
							//Boxu Add 2008��3��4�� Ϊ���޸�"����","��Ϣ"��ɾ��ʱ��д������Ϣ�ͼ������ڵ�����
							if(Interest>0)
							{
								if ( subAccountFixedInfo.getPreDrawDate() != null&&UtilOperation.Arith.round(Interest,2)==UtilOperation.Arith.round(RealInterest,2) )
								{
									subAccountLoanInfo.setPreDrawDate(subAccountFixedInfo.getPreDrawDate());
									subAccountFixedInfo.setPreDrawDate(null);
								}
							}
	
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
	
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							if (sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo) > -1)
							{
								isSuccess = 1;
							}
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}catch(Exception e3)
					    {
						    e3.printStackTrace();
							throw new IException("Gen_E001");
						}
					}
				}
			}
		}
		return isSuccess;

	}

	/**
		* �ӿ����ƣ�������Ϣ����
		* ����������1��������Ϣ��Ϣ��������˻�����ս���и��˻���ÿһ�յ�����Ϣ��������Ϣ��
		*              �ۼ���Ϣ��Ϣ���Լ����˻����еĵ�ǰ�ۼ���Ϣ��
		*           2������Ϣ���ý��㡱������Ϣ����֧������������黹���Ƚ��׽����˻���Ϣʱ�Լ� ���ػ�ʱ
		*              ��Ϣ��/���ʵ�������߳̾�����ñ��ӿڡ�ʵ�ִ����˻����쵹�������Ϣ��
		*              �ߵ��쵹���������ʵ����Ϣ�Ĺ��ܡ�
		*           3����Ϣ����������������ʵ�����ʵ���ʱ�����䣬���ʸı䡣
		*           4���Ե�������׺�һ�㵹��׷ֿ�ʵ�֡�
		*           5�����˻�������ϸ���������˻������־,�Է�ֹ�����ظ��Ĳ������ڱ��㷨�����棡��
		* @param AccountID    ���˻�ID
		* @param SubAccountID ���˻�ID
		* @param BackDate     �������Ϣ��
		* @param BackAmount   ����׽��
		* @param ExecuteDate  ִ����/�ػ�����
		* @param isCloseSystem �Ƿ�ػ�����
		* @return long -1:���ɹ���1���ɹ�
		* @throws IException
		*/

	public long accountInterestCalculationBackward(
		long AccountID,
		long SubAccountID,
		Timestamp BackDate,
		double BackAmount,
		long lOfficeID,
		long lCurrencyID,
		Timestamp ExecuteDate,
		long isCloseSystem)
		throws IException
	{
		long isSuccess = -1; //�ɹ���־
		Connection conn = this.conn_Out;
		double accumulateNormalInterestAdjust = 0.0; //�ۼ�������Ϣ������
		double accumulateNegotiateInterestAdjust = 0.0; //�ۼ�Э����Ϣ������
		double accumulateCompoundInterestAdjust = 0.0; //�ۼƸ�����Ϣ������
		double accumulateOverDueInterestAdjust = 0.0; //�ۼƷ�Ϣ��Ϣ������
		double newAccumulateNormalInterest = 0.0; //�µ��ۼ�������Ϣ������
		double newAccumulateNegotiateInterest = 0.0; //�µ��ۼ�Э����Ϣ������
		double newAccumulateCompoundInterest = 0.0; //�µ��ۼƸ�����Ϣ������
		double newAccumulateOverDueInterest = 0.0; //�µ��ۼƷ�Ϣ��Ϣ������
		double executeDateNormalInteres = 0.0; //�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ���������Ϣ��
		double executeDateNegotiateInterest = 0.0; //�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ���Э����Ϣ��
		double executeDateLoanInterest = 0.0; //�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ�����Ϣ��
		double executeDateMarginInterest = 0.0; //��֤�����˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��ı�֤����Ϣ��
		double executeDateCompoundInterest = 0.0; //�������˻��ĵ�ǰ���������ڹػ���Ϣ��Ĵ������
		double executeDateOverDueInterest = 0.0; //�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ��Ϣ��
		//double todayCompoundIntersetAdjust = 0.0; //���ո������������һ��
		double arrearageInterest = 0.0; //ǷϢ���

		
		//����AccountID��sett_account���ж�λ���˻���¼
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		UtilOperation utilOperation = new UtilOperation(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		log.info("������Ϣ������ȡ���˻��ɹ���");
		//����SubAccountID��sett_SubAccount���ж�λ���˻���¼
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			log.info("������Ϣ������ȡ���˻�....");
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
			throw new IException(true, "�޷��������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		log.info("������Ϣ������ȡ���˻��ɹ���");
		//���˻��еĻ��ڴ����Ϣ
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		//���˻��еĴ�����Ϣ
		SubAccountLoanInfo subAccountLoanInfo = null;
		//���˻��б�֤����Ϣ
		SubAccountFixedInfo subAccountFixedInfo = null;
		//�˻���ǰ���
		double currentBalance = -1;
		if (subAccountAssemblerInfo != null)
		{
			//ȡ���ڹػ�����ǰ�ĵ�ǰ���˻���������Ϣ��Э����Ϣ���������˻�������ǰ��Ϣ���������˻���
			//Ŀ�ģ��ڽ����ػ�����󣬸���ÿ����Ϣ������˻����˻�����ս���¼
			//      �����ۼ���Ϣ�ֶΣ�
			//      �µ��ۼ���Ϣ = �ػ�����ǰ����Ϣ + �ۼ���Ϣ������
			//      �˲������ڽ���ѭ�����Ҹ��������˻������޸ĵ����־֮ǰ����

			//�������˻�------------------------------------------------------------------	
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
			//�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ�����Ϣ��
			executeDateNormalInteres = subAccountCurrentInfo.getInterest();
			//�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ�����Ϣ��
			executeDateNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();
			//�������˻�------------------------------------------------------------------

			//�������˻�------------------------------------------------------------------
			subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
			//�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ�����Ϣ��
			executeDateLoanInterest = subAccountLoanInfo.getInterest();
			//�������˻��ĵ�ǰ���������ڹػ���Ϣ��Ĵ������
			executeDateCompoundInterest = subAccountLoanInfo.getCompoundInterest();
			//�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ��Ϣ��
			executeDateOverDueInterest = subAccountLoanInfo.getOverDueInterest();
			//�������˻�------------------------------------------------------------------
			
			//��֤�����˻�----------------------------------------------------------------
			subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
			executeDateMarginInterest = subAccountFixedInfo.getInterest();
			//��֤�����˻�----------------------------------------------------------------
		}
		else
		{
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		
		CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = null;

		if (accountInfo != null)
		{

			long currencyID = accountInfo.getCurrencyID(); //���˻��б���
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				//���ӿڶԶ��ڴ��������˻������м�Ϣ
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					//����ǰ��ÿ���˻����
					DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
					{
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, getNextNDay(BackDate, -1));
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						
						//�����ڴ���ѭ��ǰ
						if (dailyAccountBalanceInfo != null && dailyAccountBalanceInfo.getAccountID() > 0)
						{
							accumulateNormalInterestAdjust = dailyAccountBalanceInfo.getInterest();
							newAccumulateNormalInterest = accumulateNormalInterestAdjust;
							
							//�ۼƸ�����Ϣ������
							accumulateCompoundInterestAdjust = dailyAccountBalanceInfo.getMcompoundinterest();
							newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
							
							//�ۼƷ�Ϣ��Ϣ������
							accumulateOverDueInterestAdjust = dailyAccountBalanceInfo.getMforfeitinterest();
							newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
							//��ʼ���� -- ǷϢ���
							arrearageInterest = dailyAccountBalanceInfo.getAl_mArrearageInterest();
						}
					}
					
					//����ѭ������
					InterestCalculation interestCalculation = new InterestCalculation(conn);
					int loopDays = (int) interestCalculation.getIntervalDays(BackDate, ExecuteDate, 1);
					//				log.info("ѭ�������� " + loopDays);
	
					Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
					//				log.info("lhj debug info =========������Ϣ����ѭ����ʼ......");
					for (int i = 0; i < loopDays; i++)
					{ //��������ѭ����ʼ����������������ExecuteDate���죭����������������������������������������������
						//�����м����-------------------------------------------------------start-------
						//�µļ�Ϣ���
						double newInterestBalance = 0.0;
						//����������Ϣ������
						double todayNormalInterestAdjust = 0.0;
						//����Э����Ϣ������
						double todayNegotiateInterestAdjust = 0.0;
						//���ո�����Ϣ
						double todayCompoundIntersetAdjust = 0.0;
						//���շ�Ϣ��Ϣ
						double todayOverDueIntersetAdjust = 0.0;
						//��ǰ��Ϣ����
						Timestamp tmpBackDate = getNextNDay(BackDate, i);
						//��ǰ��Ϣ����+1
						Timestamp tmpBackDateAddOne = getNextNDay(tmpBackDate, 1);
						//					log.info("lhj debug info ======��ǰ������tmpBackDate" + tmpBackDate);
	
						//�������˻�AccountID,���˻�SubAccountID,��Ϣ����tmpBackDate���˻�����ս��-�в�ѯ��ؼ�¼��
	
						//DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
						try
						{
							//						log.info("lhj debug info =====���˻�����ս���в�ѯ��ؼ�¼�����ֵ��������һ�㵹���......");
	
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, tmpBackDate);
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						long dabi_AccountID = -1;
						if (dailyAccountBalanceInfo != null)
						{
							dabi_AccountID = dailyAccountBalanceInfo.getAccountID();
							//						log.info("lhj debug info ===========dabi_AccountID�ǣ� " + dabi_AccountID);
						}
						if (dailyAccountBalanceInfo == null || dabi_AccountID < 0)
						{
							//---------------------------------------���������-------start-------------------------------------------
							//						log.info("���˻�����ս����û�м�¼����ʼ���С���������ס�......");
							dailyAccountBalanceInfo = new DailyAccountBalanceInfo();
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
							{
								//--------���������--------�����˻���Ϣ-----------start-----------------------------------------------
								//�µļ�Ϣ���
								//							log.info("����������ס��п�ʼ���л����˻���Ϣ......");
								newInterestBalance = BackAmount;
								
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
								{//����ǲ���˻�����������뱾if�е������˻����෴
									newInterestBalance = -BackAmount;
								}
								//							���ʼƻ�ID						   
								long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();
								//							���ʼƻ�����
								long lInterestRatePlanTypeID = NameRef.getInterestRatePlanTypeIDByID(interestRatePlanID);
								//							Э�������
								double negotiateAmount = subAccountCurrentInfo.getNegotiateAmount();
								//							Э����λ��Ԫ��
								double negotiateUnit = subAccountCurrentInfo.getNegotiateUnit();
								//long negotiateRatePlanID = -1;  //2003-11-10���ֶδ����ݿ���ɾ����������������dataentity���޴˷�����������������
								//�޸�ΪЭ������ 2003-11-23 lhj*********************
								//							Э������
								double negotiateRate = subAccountCurrentInfo.getNegotiateRate();
								//
								//Modify by leiyang 2008-06-30 
								long IsNegotiate = subAccountCurrentInfo.getIsNegotiate(); //�Ƿ�Э�����
	
								//negotiateRatePlanID = subAccountCurrentInfo.getNegotiateRatePlanID();//Э��������ʼƻ�ID
								//							������ڴ��ı����������������ʣ�������Ϣ��Э����Э�����ʣ�Э����Ϣ		
								
								if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
								{//log.info("���ʼƻ�����Ϊ�վ�����Ϣ�������վ������Ϣ���� modify by yanliu");
									
									Timestamp tsDateStart = BackDate;
									Timestamp tsDateEnd = tmpBackDateAddOne;
									double averageBalance = newInterestBalance;		
	
									//Modify by leiyang ����У��Э�����ķ�ʽ
									//���� IsNegotiate �������Ƿ�ΪЭ�����
									currentDepositAccountInterestInfo =
										interestCalculation.calculateCurrentDepositAccountInterestForAverageBalance(
											lOfficeID,
											lCurrencyID,
											interestRatePlanID,
											averageBalance,			//�վ����
											newInterestBalance,			//���ռ�Ϣ���
											tsDateStart,	//��Ϣ��
											tsDateEnd,		//�ػ�����һ��
											negotiateAmount,
											negotiateUnit,
											negotiateRate,
											IsNegotiate);										
								}
								else
								{//log.info("���ʼƻ����Ͳ����վ�����Ϣ������һ����Ϣ���� modify by yanliu");		
									//Modify by leiyang ����У��Э�����ķ�ʽ
									//���� IsNegotiate �������Ƿ�ΪЭ�����
									currentDepositAccountInterestInfo =
										interestCalculation.calculateCurrentDepositAccountInterest(
											lOfficeID,
											lCurrencyID,
											interestRatePlanID,
											newInterestBalance,
											tmpBackDate,
											tmpBackDateAddOne,
											negotiateAmount,
											negotiateUnit,
											negotiateRate,
											IsNegotiate);
								}
								
								//---ȡ�����Ľṹ------------------------------------------------------------------------
								//�����������
								double todayNormalBalance = currentDepositAccountInterestInfo.getNormalBalance();
								//����Э�����
								double todayNegotiateBalance = currentDepositAccountInterestInfo.getNegotiationBalance();
								//������������
								double todayNormalInterestRate = currentDepositAccountInterestInfo.getNormalInterestRate();
								//����Э������
								double todayNegotiateInterestRate = negotiateRate;
								//����������Ϣ			
								double todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();
								//����Э����Ϣ
								double todayNegotiateInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
								//---ȡ�����Ľṹ����------------------------------------------------------------------------
	
								//����������Ϣ���Ӷ�
								todayNormalInterestAdjust = todayNormalInterest;
								//����Э����Ϣ���Ӷ�
								todayNegotiateInterestAdjust = todayNegotiateInterest;
	
								//Ϊÿ���˻�������ֵ
								//							���˻�
								dailyAccountBalanceInfo.setAccountID(AccountID);
								//							���˻�
								dailyAccountBalanceInfo.setSubAccountID(SubAccountID);
								//							����
								dailyAccountBalanceInfo.setDate(tmpBackDate);
								//���˻�״̬
								dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
								//���
								dailyAccountBalanceInfo.setBalance(0.0); //�������
								//��Ϣ����������
								dailyAccountBalanceInfo.setInterestBalance(todayNormalBalance);
								//Э�����
								dailyAccountBalanceInfo.setAc_mNegotiateBalance(todayNegotiateBalance);
								//��������
								dailyAccountBalanceInfo.setInterestRate(todayNormalInterestRate);
								//������Ϣ
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
								//Э������
								dailyAccountBalanceInfo.setAc_mNegotiateRate(todayNegotiateInterestRate);
								//Э����Ϣ
								dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(todayNegotiateInterest);
								//�ۼƵ�������Ϣ�ĵ���ֵ
								if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
								{
									accumulateNormalInterestAdjust = todayNormalInterestAdjust;
								}
								else
								{
									accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								}
								//�ۼƵ�Э����Ϣ�ĵ���ֵ
								accumulateNegotiateInterestAdjust = UtilOperation.Arith.add(accumulateNegotiateInterestAdjust, todayNegotiateInterestAdjust);
								//�µ��ۼ�������Ϣ
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								//							�µ��ۼ�Э����Ϣ
								newAccumulateNegotiateInterest = accumulateNegotiateInterestAdjust;
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								dailyAccountBalanceInfo.setAc_mNegotiateInterest(newAccumulateNegotiateInterest);
	
								//							log.info("lhj debug info ==�ۼƵ���������Ϣ��:" + newAccumulateNormalInterest);
								//							log.info("lhj debug info ==�ۼƵ���Э����Ϣ��:" + newAccumulateNegotiateInterest);
	
								//	//--------���������--------�����˻���Ϣ----------end------------------------------------------------
								//							log.info("����������ס��п�ʼ���л����˻���Ϣ����");
							}
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								//----���������--------�����˻���Ϣ--------------start--------------------------------------------
								log.info("����������ס��п�ʼ���д����˻���Ϣ......");
								//�µļ�Ϣ���
								newInterestBalance = -BackAmount;
								//�ſ��
								long loanNoteID = subAccountLoanInfo.getLoanNoteID();
								
								//���ݷſ�֪ͨ��ȡ������
								InterestRate interestRate = interestCalculation.getInterestRateByPayForm(loanNoteID, tmpBackDate);
								//��������
								double AL_InterestRate = interestRate.getRate();
								//������������					
								long AL_InterestType = interestRate.getType();
	
								/*���㱾�մ�����Ϣ*/
								double todayLoanInterest = interestCalculation.calculateLoanAccountInterest(currencyID, AL_InterestRate, AL_InterestType, newInterestBalance, tmpBackDate, tmpBackDateAddOne);
	
								//����������Ϣ������
								todayNormalInterestAdjust = todayLoanInterest;
								//Ϊÿ���˻�������ֵ=================================================
								//���˻�
								dailyAccountBalanceInfo.setAccountID(AccountID);
								//���˻�
								dailyAccountBalanceInfo.setSubAccountID(SubAccountID);
								//����
								dailyAccountBalanceInfo.setDate(tmpBackDate);
								//���˻�״̬
								dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
								//�˻������0����������
								dailyAccountBalanceInfo.setBalance(0);
								//��Ϣ���
								dailyAccountBalanceInfo.setInterestBalance(newInterestBalance);
								//���մ�������	     
								dailyAccountBalanceInfo.setInterestRate(AL_InterestRate);
								//����������������
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterestAdjust);
								//�ۼƵĴ�����Ϣ�ĵ�����							
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								//�µ��ۼƴ�����Ϣ
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								//
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
	
								//log.info("lhj debug info ---�ۼƴ�����Ϣ��:" + newAccumulateNormalInterest);
								//----���������--------�����˻���Ϣ----------------end------------------------------------------  
	
								//log.info("����������ס��п�ʼ���д����˻���Ϣ����");
								
								//modify by leiyang3
								//2010/11/28
								//���ڼ�����Ӫ����ں�ķ�Ϣ
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
								{
									LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
									
									if(loanPayNoticeInfo == null){
										throw new IException("û���ҵ���Ч�ķſ��Ϣ");
									}
									
									Timestamp loanStartDate = loanPayNoticeInfo.getStart();
									Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
									
									//-- �������㿪ʼ --
									if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�и������ԣ��ж��Ƿ���и�������
									{
										boolean boolClearDay = utilOperation.isClearInterestDay(SubAccountID, tmpBackDate);
										double compoundAccumulateBalance = 0.0;
				
										//ÿ�ո����������н�Ϣ�պ󣬵���Ϊ��Ϣ�����������һ���ۻ�δ����Ϣ  + ��һ���ۻ�δ������  + ��һ���ۻ�δ����Ϣ
										if(boolClearDay == true)
										{
											compoundAccumulateBalance = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateCompoundInterest);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateOverDueInterest);
											arrearageInterest = compoundAccumulateBalance;
										}
										//ÿ�ո����������н�Ϣ�պ󣬵��첻Ϊ��Ϣ������������˻�δ����Ϣ
										else
										{
											if(arrearageInterest>0)//��һ����ǷϢ�����շǽ�Ϣ�ռ��㱾��ǰϦ
											{
												InterestsInfo preinterestsInfo = null;
												InterestsInfo interestsInfo = null;
												try
												{
													preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
													interestsInfo = utilOperation.findAllClearCompoundInterset(SubAccountID, tmpBackDate);
												}
												catch(Exception e)
												{
													throw new IException(e.getMessage());
												}
												if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>interestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,interestsInfo.getPayminterest());
												}
												else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<interestsInfo.getPayminterest()&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
												}
												if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>interestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymcompoundinterest());
												}
												else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<interestsInfo.getPaymcompoundinterest()&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
												}
												if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>interestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymforfeitinterest());
												}
												else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<interestsInfo.getPaymforfeitinterest()&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
												}
											}
											}
										
										//��������
										InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
										paramInfo.setLoanNoteID(loanNoteID);
										paramInfo.setValidDate(tmpBackDate);
										InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
										
										//������ÿ�ո�����Ϣ
										todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, compoundRate.getRate(), compoundRate.getType(), arrearageInterest, tmpBackDate, tmpBackDateAddOne);
										//�������ۼƸ�����Ϣ
										accumulateCompoundInterestAdjust = UtilOperation.Arith.add(newAccumulateCompoundInterest, todayCompoundIntersetAdjust);
										newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
										
										//����ÿ�ո�����Ϣ
										dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
										//�ۼƸ�����Ϣ
										dailyAccountBalanceInfo.setMcompoundinterest(newAccumulateCompoundInterest);
										//ǷϢ
										dailyAccountBalanceInfo.setAl_mArrearageInterest(arrearageInterest);
									}
									//-- ����������� --
									
									//-- ��Ϣ���㿪ʼ  --
									//�Ƿ���з�Ϣ
									if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�з�Ϣ���ԣ��ж��Ƿ���з�Ϣ
									{
										if(!loanEndDate.after(tmpBackDate))
										{
											InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
											paramInfo.setLoanNoteID(loanNoteID);
											paramInfo.setValidDate(tmpBackDate);
											
											InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
											//ÿ�շ�Ϣ�ܽ�����δ�����
											double overDueAccumulateBalance = newInterestBalance;
											//������ÿ�շ�Ϣ��Ϣ
											todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//�������ۼƷ�Ϣ��Ϣ
											accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//����ÿ�շ�Ϣ��Ϣ
											dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//�ۼƷ�Ϣ��Ϣ
											dailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueInterest);
											//���շ�Ϣ����	     
											dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
											//�������õ���������������
											dailyAccountBalanceInfo.setDailyInterest(0.0);
											//���������ۼƴ�����Ϣ
											accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
									//-- ��Ϣ������� --
								}
								
							}
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
							{
								System.out.println("--------���������--------��֤���˻���Ϣ�����㷽ʽ�ͻ����˻����ƣ�-----------start-----------------------------------------------");
								//�µļ�Ϣ���
								//							log.info("����������ס��п�ʼ���б�֤���˻��˻���Ϣ......");
								newInterestBalance = BackAmount;
	
								//��������
								double normalInterestRate = subAccountFixedInfo.getRate();
								
								System.out.println("--------��������: "+normalInterestRate);
								
	                            //������Ϣ 
								double commonInterest = interestCalculation.caculateInterest(newInterestBalance, tmpBackDate, tmpBackDateAddOne, SETTConstant.InterestRateDaysFlag.DAYS_360 , normalInterestRate, SETTConstant.InterestRateTypeFlag.YEAR, SETTConstant.InterestRateDaysFlag.DAYS_360);

								//---ȡ�����Ľṹ------------------------------------------------------------------------
								//�����������
								double todayNormalBalance = newInterestBalance;
								
								//������������
								double todayNormalInterestRate = normalInterestRate;
								
								//����������Ϣ			
								double todayNormalInterest = commonInterest;
								
								//---ȡ�����Ľṹ����------------------------------------------------------------------------
	
								//����������Ϣ���Ӷ�
								todayNormalInterestAdjust = todayNormalInterest;
	
								//Ϊÿ���˻�������ֵ
								//							���˻�
								dailyAccountBalanceInfo.setAccountID(AccountID);
								//							���˻�
								dailyAccountBalanceInfo.setSubAccountID(SubAccountID);
								//							����
								dailyAccountBalanceInfo.setDate(tmpBackDate);
								//���˻�״̬
								dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
								//���
								dailyAccountBalanceInfo.setBalance(0.0); //�������
								//��Ϣ����������
								dailyAccountBalanceInfo.setInterestBalance(todayNormalBalance);
								
								//��������
								dailyAccountBalanceInfo.setInterestRate(todayNormalInterestRate);
								//������Ϣ
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
								
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								
								//�µ��ۼ�������Ϣ
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
							}

							//Ϊ�˻�����ս��������һ���¼�¼
							try
							{
								//							log.info("����������ס���ʼ�˻��ս��������������" + tmpBackDate.toString() + "������.....");
								sett_DailyAccountBalanceDAO.add(dailyAccountBalanceInfo);
							}
							catch (Exception e3)
							{
								e3.printStackTrace();
								throw new IException("����ʧ�ܣ������ԣ�");
							}
						//						log.info("����������ס���ʼ�˻��ս��������������" + tmpBackDate.toString() + "�����ݳɹ���");
						} //-------------------------------���������----------------------end-----------------------------------------
						else if (dailyAccountBalanceInfo != null && dabi_AccountID > 0)
						{
							//-------------------------------һ�㵹���----------------------start---------------------------------------
							//						log.info("���˻�����ս���д��ڼ�¼����ʼ���С�һ�㵹��ס�......");
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT||
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							{
								//--------һ�㵹���--------�����˻���Ϣ-------------start--------------------------------------------
								double oldBalance = 0.0; //�ػ�ǰ�ı��˻������							
								double oldInterestBalance = 0.0; //�ػ�ǰ���ϵļ�Ϣ���							
								double oldDailyInterest = 0.0; //�ػ�ǰ���˻���ÿ��������Ϣ
								double oldInterest = 0.0; //�ػ�ǰ�ı��˻���������Ϣ
	
								double oldNegotiateBalance = 0.0; //�ػ�ǰ�ı��˻���Э�����
								double oldDailyNegotiateInterest = 0.0; //�ػ�ǰ�ı��˻���ÿ��Э����Ϣ							
								double oldNegotiateInterest = 0.0; //�ػ�ǰ�ı��˻���Э����Ϣ
	
								oldBalance = dailyAccountBalanceInfo.getBalance();
								oldInterestBalance = dailyAccountBalanceInfo.getInterestBalance();
								oldDailyInterest = dailyAccountBalanceInfo.getDailyInterest();
								oldInterest = dailyAccountBalanceInfo.getInterest();
	
								oldNegotiateBalance = dailyAccountBalanceInfo.getAc_mNegotiateBalance();
								oldDailyNegotiateInterest = dailyAccountBalanceInfo.getAc_mDailyNegotiateInterest();
								oldNegotiateInterest = dailyAccountBalanceInfo.getAc_mNegotiateInterest();
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == ����ǰ-���������:" + oldBalance);
								log.info("lhj debug info == ����ǰ-������Ϣ�����:" + oldInterestBalance);
								log.info("lhj debug info == ����ǰ-ÿ��������Ϣ��:" + oldDailyInterest);
								log.info("lhj debug info == ����ǰ-������Ϣֵ��:" + oldInterest);
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == ����ǰ-Э�������:" + oldNegotiateBalance);
								log.info("lhj debug info == ����ǰ-ÿ��Э����Ϣ��:" + oldDailyInterest);
								log.info("lhj debug info == ����ǰ-Э����Ϣֵ��:" + oldNegotiateInterest);
								log.info("---------------------------------------------------------------");
								log.info("��һ�㵹��ס��п�ʼ���л����˻���Ϣ......");
	
								//���ռ�Ϣ���
								newInterestBalance = UtilOperation.Arith.add(UtilOperation.Arith.add(oldInterestBalance, oldNegotiateBalance), BackAmount);
								
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
								{//����ǲ���˻�����������뱾if�е������˻����෴
									newInterestBalance = UtilOperation.Arith.sub(UtilOperation.Arith.add(oldInterestBalance, oldNegotiateBalance), BackAmount);
								}
								
								long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();
								//�������ʼƻ�ID
								double negotiateAmount = subAccountCurrentInfo.getNegotiateAmount();
								//����Э�������
								
								//Modify by leiyang 2008-10-15
								//�޸Ĺ���Э�������Ч�յ�����
								//����Э����λ��Ԫ������ʷ��û�м�¼ֻ��ȡ���ʻ���Э����λ
								double negotiateUnit = subAccountCurrentInfo.getNegotiateUnit();
								//long negotiateRatePlanID = -1;
								//2003-11-10���ֶδ����ݿ���ɾ����������������dataentity���޴˷�����������������
								//negotiateRatePlanID = subAccountCurrentInfo.getNegotiateRatePlanID();//Э��������ʼƻ�ID
								//����Э��������ʣ�ֻ��ȡ��ʷ�ϵ�Э���������
								double negotiateRate = dailyAccountBalanceInfo.getAc_mNegotiateRate();

								
								//Modify by leiyang 2008-07-03 
								long IsNegotiate = subAccountCurrentInfo.getIsNegotiate(); //�Ƿ�Э�����
	
								//������ڴ��ı����������������ʣ�������Ϣ��Э����Э�����ʣ�Э����Ϣ		
								//Modify by leiyang ����У��Э�����ķ�ʽ
								//���� IsNegotiate �������Ƿ�ΪЭ�����
								currentDepositAccountInterestInfo =
									interestCalculation.calculateCurrentDepositAccountInterest(
										lOfficeID,
										lCurrencyID,
										interestRatePlanID,
										newInterestBalance,
										tmpBackDate,
										tmpBackDateAddOne,
										negotiateAmount,
										negotiateUnit,
										negotiateRate,
										IsNegotiate);
	
								//added by mzh_fu 2008/01/03 ��������ʼƻ�����û�б�ȡ������,��ôȡ�ս���е�����
								if(currentDepositAccountInterestInfo.getNormalInterestRate() <= 0){	
									//Modify by leiyang ����У��Э�����ķ�ʽ
									//���� IsNegotiate �������Ƿ�ΪЭ�����
									currentDepositAccountInterestInfo =
										interestCalculation.calculateCurrentDepositAccountInterest(
											dailyAccountBalanceInfo.getInterestRate(),
											newInterestBalance,
											tmpBackDate,
											tmpBackDateAddOne,
											negotiateAmount,
											negotiateUnit,
											negotiateRate,
											IsNegotiate);
								}
								
								//������������
								double todayNormalBalance = currentDepositAccountInterestInfo.getNormalBalance();
								//�����Э�����
								double todayNegotiateBalance = currentDepositAccountInterestInfo.getNegotiationBalance();
								//�����������Ϣ
								double todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();
								//�����Э����Ϣ
								double todayNegotiateInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
								//�����������Ϣ���Ӷ�		
								todayNormalInterestAdjust = UtilOperation.Arith.sub(todayNormalInterest, oldDailyInterest);
								//�����Э����Ϣ���Ӷ�
								todayNegotiateInterestAdjust = UtilOperation.Arith.sub(todayNegotiateInterest, oldDailyNegotiateInterest);
	
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == �����#���������:" + todayNormalBalance);
								//log.info("lhj debug info == �����-������Ϣ�����:" + oldInterestBalance);
								log.info("lhj debug info == �����#ÿ��������Ϣ��:" + oldDailyInterest);
								//log.info("lhj debug info == �����#������Ϣֵ��:" + todayNormalInterest);
								log.info("---------------------------------------------------------------");
								log.info("lhj debug info == �����#Э�������:" + todayNegotiateBalance);
								//log.info("lhj debug info == �����#ÿ��Э����Ϣ��:" + oldDailyInterest);
								//log.info("lhj debug info == �����#Э����Ϣֵ��:" + oldNegotiateInterest);
	
								//Ϊÿ���˻�������ֵ
								//������Ϣ���
								dailyAccountBalanceInfo.setInterestBalance(todayNormalBalance);
								//Э����Ϣ���
								dailyAccountBalanceInfo.setAc_mNegotiateBalance(todayNegotiateBalance);
								//ÿ��������Ϣ
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
								//ÿ��Э����Ϣ
								dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(todayNegotiateInterest);
								//��������
								dailyAccountBalanceInfo.setInterestRate(currentDepositAccountInterestInfo.getNormalInterestRate());
								//Э������
								dailyAccountBalanceInfo.setAc_mNegotiateRate(negotiateRate);
								//Э����Ϣ
								dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(todayNegotiateInterest);
								//--------һ�㵹���-----------�����˻���Ϣ----------end-----------------------------------------------
								//������Ϣ���ۼƵ�����
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								//Э����Ϣ���ۼƵ�����
								accumulateNegotiateInterestAdjust = UtilOperation.Arith.add(accumulateNegotiateInterestAdjust, todayNegotiateInterestAdjust);
								//�µ��ۼ�������Ϣ							
								newAccumulateNormalInterest = UtilOperation.Arith.add(oldInterest, accumulateNormalInterestAdjust);
								//�µ��ۼ�Э����Ϣ
								newAccumulateNegotiateInterest = UtilOperation.Arith.add(oldNegotiateInterest, accumulateNegotiateInterestAdjust);
								//							log.info("lhj debug info == �����#�ۼ�������Ϣ��:" + newAccumulateNormalInterest);
								//							log.info("lhj debug info == �����#�ۼ�Э����Ϣ��:" + newAccumulateNegotiateInterest);
								//							log.info("lhj debug info ==Э��������:" + negotiateRate);
								//
								//							log.info("---------------------------------------------------------------");
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								dailyAccountBalanceInfo.setAc_mNegotiateInterest(newAccumulateNegotiateInterest);
								//							log.info("��һ�㵹��ס��п�ʼ���л����˻���Ϣ����");
							}
							else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								//--------һ�㵹���------------�����˻���Ϣ---------start----------------------------------------------
								log.info("��һ�㵹��ס��п�ʼ���д����˻���Ϣ......");

								//�µļ�Ϣ���
								newInterestBalance = UtilOperation.Arith.sub(dailyAccountBalanceInfo.getInterestBalance(), BackAmount);
								//����ǰ�Ĵ�����Ϣ
								//double oldLoanInterest = dailyAccountBalanceInfo.getInterest();
								//�ſ��
								long loanNoteID = -1;
								loanNoteID = subAccountLoanInfo.getLoanNoteID();
								/*���㱾�մ�����Ϣ*/
								InterestRate interestRate = interestCalculation.getInterestRateByPayForm(loanNoteID, tmpBackDate);
								//��û��ʵ��2003-11-10//2003-11-20����UtilOperation������һ������,����������com.iss.itreasury.loan.loanpaynotice.dao�е�getRateValue����
								double AL_InterestRate = interestRate.getRate();
								//���ݷſ�֪ͨ��ȡ������
								long AL_InterestType = interestRate.getType();

								//���ݷſ�֪ͨ��ȡ����������
								log.debug("********************************************************************");
								log.debug("lhj debug info ==�����ǰ-�µļ�Ϣ��" + newInterestBalance);
								log.debug("lhj debug info ==�ſ�֪ͨ�����룺" + loanNoteID);
								log.debug("lhj debug info ==���������ǣ� " + tmpBackDate);
								log.debug("lhj debug info ==���������ǣ� " + AL_InterestRate);
								log.debug("********************************************************************");
								//����������Ϣ
								double todayNormalInterest = interestCalculation.calculateLoanAccountInterest(currencyID, AL_InterestRate, AL_InterestType, newInterestBalance, tmpBackDate, tmpBackDateAddOne);
								//������Ϣ���Ӷ�
								//todayNormalInterestAdjust = UtilOperation.Arith.sub(todayNormalInterest, dailyAccountBalanceInfo.getDailyInterest());
								todayNormalInterestAdjust = todayNormalInterest;
								//Ϊÿ���˻�������ֵ
								//							��Ϣ���
								dailyAccountBalanceInfo.setInterestBalance(newInterestBalance);
								//���մ�������
								dailyAccountBalanceInfo.setInterestRate(AL_InterestRate);
								//����������Ϣ
								dailyAccountBalanceInfo.setDailyInterest(todayNormalInterestAdjust);
	
								//--------һ�㵹���-------------�����˻���Ϣ----------end------------------------------------------------
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								//accumulateNegotiateInterestAdjust = UtilOperation.Arith.add(accumulateNegotiateInterestAdjust, todayNegotiateInterestAdjust);
								InterestsInfo interestsInfo = null;
								//��ȥ���ջ�����
								try {
									interestsInfo = utilOperation.findAllClearCompoundInterset(SubAccountID, tmpBackDate);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									throw new IException(e.getMessage());
								}
								if(interestsInfo!=null)
								{
									accumulateNormalInterestAdjust = UtilOperation.Arith.sub(accumulateNormalInterestAdjust,interestsInfo.getPayminterest());
								}
								//�������ۼ�������Ϣ
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								
								//modify by leiyang3
								//2010/11/28
								//���ڼ�����Ӫ����ں�ķ�Ϣ
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
								{
									LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
									if(loanPayNoticeInfo == null){
										throw new IException("û���ҵ���Ч�ķſ��Ϣ");
									}
									
									Timestamp loanStartDate = loanPayNoticeInfo.getStart();
									Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
									
									//-- �������㿪ʼ --
									if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�и������ԣ��ж��Ƿ���и�������
									{
										boolean boolClearDay = utilOperation.isClearInterestDay(SubAccountID, tmpBackDate);
										double compoundAccumulateBalance = 0.0;
				
										//ÿ�ո����������н�Ϣ�պ󣬵���Ϊ��Ϣ�����������һ���ۻ�δ����Ϣ  + ��һ���ۻ�δ������  + ��һ���ۻ�δ����Ϣ
										if(boolClearDay == true)
										{
											compoundAccumulateBalance = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateCompoundInterest);
											compoundAccumulateBalance = UtilOperation.Arith.add(compoundAccumulateBalance, newAccumulateOverDueInterest);
											arrearageInterest = compoundAccumulateBalance;
										}
										//ÿ�ո����������н�Ϣ�պ󣬵��첻Ϊ��Ϣ������������˻�δ����Ϣ
										else
										{
											if(arrearageInterest>0)//��һ����ǷϢ�����շǽ�Ϣ�ռ��㱾��ǰϦ
											{
												InterestsInfo preinterestsInfo = null;
												try
												{
													preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
												}
												catch(Exception e)
												{
													throw new IException(e.getMessage());
												}
												if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>interestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,interestsInfo.getPayminterest());
												}
												else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<interestsInfo.getPayminterest()&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
												}
												if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>interestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymcompoundinterest());
												}
												else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<interestsInfo.getPaymcompoundinterest()&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
												}
												if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>interestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest =UtilOperation.Arith.sub( arrearageInterest ,interestsInfo.getPaymforfeitinterest());
												}
												else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<interestsInfo.getPaymforfeitinterest()&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
												{
													arrearageInterest = UtilOperation.Arith.sub(arrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
												}
											}
											}
										
										//��������
										InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
										paramInfo.setLoanNoteID(loanNoteID);
										paramInfo.setValidDate(tmpBackDate);
										InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
										
										//������ÿ�ո�����Ϣ
										todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, compoundRate.getRate(), compoundRate.getType(), arrearageInterest, tmpBackDate, tmpBackDateAddOne);
										//�������ۼƸ�����Ϣ
										accumulateCompoundInterestAdjust = UtilOperation.Arith.add(newAccumulateCompoundInterest, todayCompoundIntersetAdjust);
										newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
										
										//����ÿ�ո�����Ϣ
										dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
										//�ۼƸ�����Ϣ
										dailyAccountBalanceInfo.setMcompoundinterest(newAccumulateCompoundInterest);
										//ǷϢ
										dailyAccountBalanceInfo.setAl_mArrearageInterest(arrearageInterest);
									}
									//-- ����������� --
									
									//-- ��Ϣ���㿪ʼ  --
									//�Ƿ���з�Ϣ
									if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�з�Ϣ���ԣ��ж��Ƿ���з�Ϣ
									{
										if(!loanEndDate.after(tmpBackDate))
										{
											InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
											paramInfo.setLoanNoteID(loanNoteID);
											paramInfo.setValidDate(tmpBackDate);
											
											InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
											//ÿ�շ�Ϣ�ܽ�����δ�����
											double overDueAccumulateBalance = newInterestBalance;
											//������ÿ�շ�Ϣ��Ϣ
											todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//�������ۼƷ�Ϣ��Ϣ
											accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//����ÿ�շ�Ϣ��Ϣ
											dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//�ۼƷ�Ϣ��Ϣ
											dailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueInterest);
											//���շ�Ϣ����	     
											dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
											//�������õ���������������
											dailyAccountBalanceInfo.setDailyInterest(0.0);
											//���������ۼƴ�����Ϣ
											accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
									//-- ��Ϣ������� --
								}
	
								//log.info("lhj debug info ==[" + SubAccountID + "]�����ۼ�������Ϣ��:" + newAccumulateNormalInterest);
								//log.info("��һ�㵹��ס��п�ʼ���д����˻���Ϣ����");
							}
	
							//                         Ϊ�˻�����ս���и���һ���¼�¼
	
							try
							{
								//log.info("��һ�㵹��ס��п�ʼ�����˻�����ս����������" + tmpBackDate.toString() + "������.....");
								sett_DailyAccountBalanceDAO.update(dailyAccountBalanceInfo);
							}
							catch (Exception e4)
							{
								e4.printStackTrace();
								throw new IException("����ʧ�ܣ������ԣ�");
							}
							//log.info("��һ�㵹��ס��п�ʼ�����˻�����ս����������" + tmpBackDate.toString() + "�����ݳɹ���");
							//--------------------------------һ�㵹���----------------------end---------------------------------------
						}
					} //��������ѭ��������������������������������������������������������������������������������������������������
					//log.info("ѭ������");
					//�������˻���
					try
					{
						//log.info("��ʼ�������˻���........");
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
						{
							//log.info("��ʼ�������˻����л����˻���Ϣ........");
							//---------------------------------------------------------------------------------  
							double newSubNormalInterest = UtilOperation.Arith.add(executeDateNormalInteres, accumulateNormalInterestAdjust);
							double newSubNegotiateInterest = UtilOperation.Arith.add(executeDateNegotiateInterest, accumulateNegotiateInterestAdjust);
							subAccountCurrentInfo.setInterest(newSubNormalInterest);
							subAccountCurrentInfo.setNegotiateInterest(newSubNegotiateInterest);
							//---------------------------------------------------------------------------------
							//�������˻��еĻ����˻�,���³ɹ���������Ϣ�����ֵ����Ϊ1 
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
							//�ڹػ�����ʱ�򣬸������һ�յ��˻�����ս���¼��ֻ�޸���ϢmInterest
							if (isCloseSystem == SETTConstant.BooleanValue.ISTRUE)
							{
								//							DailyAccountBalanceInfo lastCurrentDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, ExecuteDate);
								//							if (lastCurrentDailyAccountBalanceInfo == null)
								//							{
								//								throw new IException(true, "�޷����˻��������ҵ���Ӧ����Ϣ������ʧ��", null);
								//							}
								//							lastCurrentDailyAccountBalanceInfo.setInterest(newSubNormalInterest);
								//							lastCurrentDailyAccountBalanceInfo.setAc_mNegotiateInterest(newSubNegotiateInterest);
								//							sett_DailyAccountBalanceDAO.update(lastCurrentDailyAccountBalanceInfo);
	
							}
	
						}
						//Boxu Add 2008��3��21�� ��֤�� 
						else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
						{
							log.info("��ʼ�������˻����б�֤���˻���Ϣ........");
							//---------------------------------------------------------------------------------  
							double newSubNormalInterest = UtilOperation.Arith.add(executeDateMarginInterest, accumulateNormalInterestAdjust);
							subAccountFixedInfo.setInterest(newSubNormalInterest);
							//---------------------------------------------------------------------------------
							//�������˻��еı�֤���˻�,���³ɹ���������Ϣ�����ֵ����Ϊ1 
							sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
							//�ڹػ�����ʱ�򣬸������һ�յ��˻�����ս���¼��ֻ�޸���ϢmInterest
							if (isCloseSystem == SETTConstant.BooleanValue.ISTRUE)
							{
								//							DailyAccountBalanceInfo lastCurrentDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, ExecuteDate);
								//							if (lastCurrentDailyAccountBalanceInfo == null)
								//							{
								//								throw new IException(true, "�޷����˻��������ҵ���Ӧ����Ϣ������ʧ��", null);
								//							}
								//							lastCurrentDailyAccountBalanceInfo.setInterest(newSubNormalInterest);
								//							lastCurrentDailyAccountBalanceInfo.setAc_mNegotiateInterest(newSubNegotiateInterest);
								//							sett_DailyAccountBalanceDAO.update(lastCurrentDailyAccountBalanceInfo);
	
							}
	
						}
						else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
						{
							//�������˻�������Ϣ
							double newSubLoanInterest = accumulateNormalInterestAdjust;
							subAccountLoanInfo.setInterest(newSubLoanInterest);
							//�������˻�������Ϣ
							double newSubLoanCompoundInterest =  accumulateCompoundInterestAdjust;
							subAccountLoanInfo.setCompoundInterest(newSubLoanCompoundInterest);
							
							//�������˻���Ϣ��Ϣ
							double newSubLoanOverDueInterest =   accumulateOverDueInterestAdjust;
							subAccountLoanInfo.setOverDueInterest(newSubLoanOverDueInterest);
							
							//�������˻�δ����Ϣ
							subAccountLoanInfo.setArrearageInterest(arrearageInterest);
							
							sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
						
						}
						else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
						{
							//log.info("��ʼ�������˻����д�����Ϣ........");
							//**********************************************************************************
							double newSubLoanInterest =  accumulateNormalInterestAdjust;
							subAccountLoanInfo.setInterest(newSubLoanInterest);

							//�������˻��еĴ����˻� ,���³ɹ���������Ϣ�����ֵ����Ϊ1 
							sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
	
							if (isCloseSystem == SETTConstant.BooleanValue.ISTRUE)
							{
								//							//log.info("�������˻����д�����Ϣ�ɹ���");
								//							//EXECUTE����ĵ�����ѭ��������У�//2003-12-05
								//							//�ڹػ�����ʱ�򣬸������һ�յ��˻�����ս���¼��ֻ�޸���ϢmInterest
								//							DailyAccountBalanceInfo lastLoanDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, ExecuteDate);
								//							if (lastLoanDailyAccountBalanceInfo == null)
								//							{
								//								throw new IException(true, "�޷����˻��������ҵ���Ӧ����Ϣ������ʧ��", null);
								//							}
								//							lastLoanDailyAccountBalanceInfo.setInterest(newSubLoanInterest);
								//							sett_DailyAccountBalanceDAO.update(lastLoanDailyAccountBalanceInfo);
							}
							//*************************************************************************************
						}
						//������׵�״̬�޸ģ�2003-11-25 lhj 
						sett_TransAccountDetailDAO sett_TransAccountDetailDAO = new sett_TransAccountDetailDAO(conn);
						//�޸��˻����׵����־������
						sett_TransAccountDetailDAO.updateInterestBackFlag(AccountID, SubAccountID, lCurrencyID, BackDate, ExecuteDate);
						//����ɹ�
						isSuccess = 1;
					}
					catch (IException e)
					{
						e.printStackTrace();
						throw e;
					}
					catch (Exception e2)
					{
						e2.printStackTrace();
						throw new IException("Gen_E001");
					}
				} //���ڴ��������˻������м�Ϣ
			}
		}
		return isSuccess;
	}

	/**
	 * �ӿ����ƣ�������Ϣ�����
	 * ����������1��������Ϣ����ʱ�������˻�����ս���и��˻�ÿһ�յ��ۼ���Ϣ���ۼ�ǷϢ����Ϣ��
	 *           2������Ϣ���ý��㡱������Ϣ����֧������������黹���Ƚ��׽����˻���Ϣʱ����Ϣ��С��
	 *              ��ǰ��ʱ������ִ�б��ӿڡ�
	 *           3��ɾ�������Ϣ��¼ʱҲҪ���ñ��ӿڡ�
	 * @param AccountID    ���˻�ID
	 * @param SubAccountID ���˻�ID
	 * @param BackDate     �������Ϣ��
	 * @param BackAmount   ����׽��
	 * @param ExecuteDate  ִ����/�ػ�����
	 * @return long -1:���ɹ���1���ɹ�
	 * @throws IException
	 */
	public long accountInterestSettlelmentBackward(long AccountID, long SubAccountID, Timestamp BackDate, double BackAmount, long lofficeID, long lCurrencyID, Timestamp ExecuteDate,long amountType) throws IException
	{
		log.info("������Ϣ����� AccountID == " + AccountID);
		log.info("������Ϣ����� SubAccountID == " + SubAccountID);
		log.info("������Ϣ����� BackDate == " + BackDate);
		log.info("������Ϣ����� BackAmount == " + BackAmount);
		log.info("������Ϣ����� ExecuteDate == " + ExecuteDate);

		//����ֵ
		long isSuccess = -1;
		Connection conn = this.conn_Out;
		double accumulateNormalInterestAdjust = 0.0; //�ۼ�������Ϣ������
		double accumulateNegotiateInterestAdjust = 0.0; //�ۼ�Э����Ϣ������
		double accumulateCompoundInterestAdjust = 0.0;//�ۼƸ�������
		double accumulateOverDueInterestAdjust = 0.0; //�ۼ�������Ϣ������
		double tmpArrearageInterest = 0;

		//����AccountID��sett_account���ж�λ���˻���¼
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "���˻���û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}

		//����SubAccountID��sett_SubAccount���ж�λ���˻���¼
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		//���˻��еĻ��ڴ����Ϣ
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		//�˻���ǰ���
		double currentBalance = 0.0;
		try
		{
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
			if (subAccountAssemblerInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
			//���˻��еĻ����˻�����	
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException SQLe1)
		{
			// TODO Auto-generated catch block
			SQLe1.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}

		//	CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = null;

		if (accountInfo != null && accountInfo.getAccountID() > 0)
		{

			long currencyID = accountInfo.getCurrencyID(); //���˻��б���
			long accountTypeID = accountInfo.getAccountTypeID(); //���˻�����
			log.info("lhj debug info == ���˻��˻������ǣ�" + accountTypeID);
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				//���ڴ��������˻������м�Ϣ
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					//�Ƿ�Э�����
					long isNegotiate = subAccountCurrentInfo.getIsNegotiate();
					if (isNegotiate != 1)
					{
						//һ����ڻ��ߴ���
						//log.info("һ����ڻ��������ۼ�������Ϣ��������ۼ�Э����Ϣ������");
						accumulateNormalInterestAdjust = BackAmount;
						accumulateNegotiateInterestAdjust = 0.0;
						
						if(amountType == SETTConstant.InterestFeeType.FORFEITINTEREST){
							accumulateOverDueInterestAdjust =  BackAmount;
						}
					}
					else
					{
						//Э�����
						//log.info("Э���������ۼ�������Ϣ��������ۼ�Э����Ϣ������");
						//�����Ϣ�յ�����
						Timestamp backDateDecreaseOne = getNextNDay(BackDate, -1);
	
						DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lofficeID, lCurrencyID, backDateDecreaseOne);
							if (dailyAccountBalanceInfo == null)
							{
								throw new IException(true, "�˻�������û�ж�Ӧ��¼������ʧ��", null);
							}
						}
						catch (IException ie)
						{
							ie.printStackTrace();
							throw ie;
						}
						catch (SQLException SQLe1)
						{
							SQLe1.printStackTrace();
							throw new IException("Gen_E001");
						}
						if (dailyAccountBalanceInfo != null && dailyAccountBalanceInfo.getAccountID() > 0)
						{
							//���BackAmount�Ǹ��������ʾ��ɾ����Ϣ������������ۼ���Ϣ��������ۼ�Э����Ϣ������ȡ��
							long isNegative = -1;
							if (BackAmount < 0){
								isNegative = -1;
							}else{
								isNegative = 1;
							}
							//�ۼ�������Ϣ������	
							accumulateNormalInterestAdjust = UtilOperation.Arith.mul(isNegative, dailyAccountBalanceInfo.getInterest());
							//�ۼ�Э����Ϣ������
							accumulateNegotiateInterestAdjust = UtilOperation.Arith.mul(isNegative, dailyAccountBalanceInfo.getAc_mNegotiateInterest());
							log.info("lhj debug info ==������Ϣ�����==�ۼ�������Ϣ������== " + accumulateNormalInterestAdjust);
							log.info("lhj debug info ==������Ϣ�����==�ۼ�Э����Ϣ������== " + accumulateNegotiateInterestAdjust);
						}
					}

					//ѭ�������µ��ۼ���Ϣ��������µ��ۼ�Э����Ϣ������
					//ѭ����������ΪBackDate��ExecuteDate֮��� ����
					int loopDays = (int) interestCalculation.getIntervalDays(BackDate, ExecuteDate, 1);
					log.info("��������ѭ����ʼ������������������������������ExecuteDate���죭������������������������������������");
					for (int i = 0; i < loopDays; i++)
					{
						//��������ѭ����ʼ������������������������������ExecuteDate���죭������������������������������������
						Timestamp tmpBackDate = getNextNDay(BackDate, i);
	
						DailyAccountBalanceInfo tmpDailyAccountBalanceInfo = null;
	
						try
						{
							tmpDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lofficeID, lCurrencyID, tmpBackDate);
						}
						catch (SQLException SQLe2)
						{
							SQLe2.printStackTrace();
							throw new IException("Gen_E001");
						}
						//�µ��ۼ�������Ϣ
						double newAccumulateNormalInterest = 0.0;
						//�µ��ۼ�Э����Ϣ
						double newAccumulateNegotiateInterest = 0.0;
						//�µ��ۼ�ǷϢ
						double newAccumulateArrearageInterest = 0.0;
						//�µ�����ǷϢ
						double newAccumulateOverDueAmount = 0.0;
	
						if (tmpDailyAccountBalanceInfo != null && tmpDailyAccountBalanceInfo.getAccountID() > 0)
						{
							//�µ��ۼ�������Ϣ
							newAccumulateNormalInterest = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getInterest(), accumulateNormalInterestAdjust);
							//�µ��ۼ�Э����Ϣ
							newAccumulateNegotiateInterest = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getAc_mNegotiateInterest(), accumulateNegotiateInterestAdjust);
							log.debug( "��������0610����������"+ tmpBackDate +"�ۼ���Ϣֵ��"+tmpDailyAccountBalanceInfo.getInterest());
	                        log.debug( "��������0610����������"+ tmpBackDate +"�ۼ�ǷϢֵ��"+tmpDailyAccountBalanceInfo.getAl_mArrearageInterest());
							if (tmpDailyAccountBalanceInfo.getAl_mArrearageInterest() > 0)
							{
								log.debug( "��������0610����������"+ tmpBackDate +"�ۼ�ǷϢֵ���е���......��");
	                            log.debug( "��������0610����������"+ tmpBackDate +"�ۼ�������Ϣ������:"+accumulateNormalInterestAdjust);
								newAccumulateArrearageInterest = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getAl_mArrearageInterest(), accumulateNormalInterestAdjust);
								log.debug( "��������0610����������"+ tmpBackDate +"�ۼ�ǷϢֵ������ֵ��"+newAccumulateArrearageInterest);
	                            if (newAccumulateArrearageInterest < 0)
								{
									newAccumulateArrearageInterest = 0;
								}
							}
							//---------------------------------------------------------------------------------------
							//if (tmpDailyAccountBalanceInfo.getAl_mOverDueAmount() > 0)
							//{
							//	newOverDueAmount = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getAl_mOverDueAmount(), accumulateNormalInterestAdjust);
							//	if (newOverDueAmount < 0)
							//	{
							//		newOverDueAmount = 0;
							//	}
							//}
							//---------------------------------------------------------------------------------------
							log.info("lhj debug info &&������Ϣ�����&&�µ��ۼ�������Ϣ&& " + newAccumulateNormalInterest);
							log.info("lhj debug info &&������Ϣ�����&&�µ��ۼ�Э����Ϣ&& " + newAccumulateNegotiateInterest);
							log.info("lhj debug info &&������Ϣ�����&&&&�µ��ۼ�ǷϢ&&&& " + newAccumulateArrearageInterest);
							//log.info("lhj debug info &&������Ϣ�����&&&&�µ�����ǷϢ&&&& " + newOverDueAmount);
							//���µ��ۼ�������Ϣ���µ��ۼ�Э����Ϣ���µ��ۼ�ǷϢ���µ�ÿ���˻�����sett_DailyAccountBalance��
							tmpDailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
							tmpDailyAccountBalanceInfo.setAc_mNegotiateInterest(newAccumulateNegotiateInterest);
							tmpDailyAccountBalanceInfo.setAl_mArrearageInterest(newAccumulateArrearageInterest);
							//tmpDailyAccountBalanceInfo.setAl_mOverDueAmount(newOverDueAmount);
							
							//��������Ӫ����Ḵ�������⴦��
							UtilOperation utilOperation = new UtilOperation(conn);
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
							{
								long loanNoteID = subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID();
								LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
								if(loanPayNoticeInfo == null){
									throw new IException("û���ҵ���Ч�ķſ��Ϣ");
								}
								
								Timestamp loanStartDate = loanPayNoticeInfo.getStart();
								Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
								
								//-- �������㿪ʼ --
								if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�и������ԣ��ж��Ƿ���и�������
								{
									//��Ϣ����֧������Ϣ��Ϣ�������ۼ�ǷϢ
									//�������׻�Ϣ����Ҫ�ж��Ƿ���Ҫ����ۼ�ǷϢ
									InterestsInfo preinterestsInfo = null;
									InterestsInfo todayinterestsInfo = null;
									DailyAccountBalanceInfo preDailyAccountBalanceInfo = null;
									try
									{
										preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
										todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
										preDailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lofficeID, lCurrencyID,getNextNDay(tmpBackDate, -1));
										tmpArrearageInterest = preDailyAccountBalanceInfo.getAl_mArrearageInterest();
										accumulateCompoundInterestAdjust = preDailyAccountBalanceInfo.getMcompoundinterest();
									}
									catch(Exception e)
									{
										throw new IException(e.getMessage());
									}
									double Payminterest = 0;
									double Paymcompoundinterest = 0;
									double Paymforfeitinterest = 0;
									if(amountType==SETTConstant.InterestFeeType.INTEREST&&i==0)
									{
										Payminterest = UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPayminterest());
									}
									else
									{
										Payminterest = todayinterestsInfo.getPayminterest();
									}
									if(amountType==SETTConstant.InterestFeeType.COMPOUNDINTEREST&&i==0)
									{
										Paymcompoundinterest = UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPaymcompoundinterest());
									}
									else
									{
										Paymcompoundinterest = todayinterestsInfo.getPaymcompoundinterest();
									}
									if(amountType==SETTConstant.InterestFeeType.FORFEITINTEREST&&i==0)
									{
										Paymforfeitinterest = UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPaymforfeitinterest());
									}
									else
									{
										Paymforfeitinterest = todayinterestsInfo.getPaymforfeitinterest();
									}
									if(!utilOperation.isClearInterestDay(SubAccountID, tmpBackDate))
									{
									
										if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>Payminterest)
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,Payminterest);
										}
										else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<Payminterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
										}
										if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>Paymcompoundinterest)
										{
											tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,Paymcompoundinterest);
										}
										else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<Paymcompoundinterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
										}
									
										if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>Paymforfeitinterest)
										{
											tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,Paymforfeitinterest);
										}
										else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<Paymforfeitinterest&&preinterestsInfo.getMforfeitinterest()>-preinterestsInfo.getPaymforfeitinterest())
										{
											tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
										}
									}
									else
									{
										tmpArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(UtilOperation.Arith.sub(preDailyAccountBalanceInfo.getInterest(),Payminterest),
										UtilOperation.Arith.sub(preDailyAccountBalanceInfo.getMcompoundinterest(),Paymcompoundinterest)),
										UtilOperation.Arith.sub(preDailyAccountBalanceInfo.getMforfeitinterest(),Paymforfeitinterest));
									}
									if(amountType==SETTConstant.InterestFeeType.COMPOUNDINTEREST&&i==0)
									{
										todayinterestsInfo.setPaymcompoundinterest(UtilOperation.Arith.add(BackAmount,todayinterestsInfo.getPaymcompoundinterest()));
									}
									accumulateCompoundInterestAdjust = UtilOperation.Arith.sub(accumulateCompoundInterestAdjust ,todayinterestsInfo.getPaymcompoundinterest());
									
									if(accumulateCompoundInterestAdjust<0.01)
									{
										accumulateCompoundInterestAdjust = 0;
									}
									if(tmpArrearageInterest<0.01)
									{
										tmpArrearageInterest = 0;
									}
										
									
									//��������
									InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
									paramInfo.setLoanNoteID(subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID());
									paramInfo.setValidDate(tmpBackDate);
									InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
									double todayCompoundIntersetAdjust=0;
									//������ÿ�ո�����Ϣ
									todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, compoundRate.getRate(), compoundRate.getType(), tmpArrearageInterest, tmpBackDate, getNextNDay(tmpBackDate, 1));
									//�������ۼƸ�����Ϣ
									accumulateCompoundInterestAdjust = UtilOperation.Arith.add(accumulateCompoundInterestAdjust, todayCompoundIntersetAdjust);
									tmpDailyAccountBalanceInfo.setAl_mArrearageInterest(tmpArrearageInterest);
									
									//����ÿ�ո�����Ϣ
									tmpDailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
									//�ۼƸ�����Ϣ
									tmpDailyAccountBalanceInfo.setMcompoundinterest(accumulateCompoundInterestAdjust);
								}
								//-- ����������� --
								
								//-- ��Ϣ���㿪ʼ  --
								//�Ƿ���з�Ϣ
								if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�з�Ϣ���ԣ��ж��Ƿ���з�Ϣ
								{
									if(!loanEndDate.after(tmpBackDate))
									{
										if(amountType == SETTConstant.InterestFeeType.FORFEITINTEREST)
										{
											//�µ��ۼƷ�Ϣ��Ϣ
											newAccumulateOverDueAmount = UtilOperation.Arith.sub(tmpDailyAccountBalanceInfo.getMforfeitinterest(), accumulateOverDueInterestAdjust);

											//ÿ�շ�Ϣ�ܽ�����δ�����
											//double overDueAccumulateBalance = newInterestBalance;
											//������ÿ�շ�Ϣ��Ϣ
											//todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(lCurrencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//�������ۼƷ�Ϣ��Ϣ
											//accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											//newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//����ÿ�շ�Ϣ��Ϣ
											//dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//�ۼƷ�Ϣ��Ϣ
											tmpDailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueAmount);
											//�������õ���������������
											//dailyAccountBalanceInfo.setDailyInterest(0.0);
											//���������ۼƴ�����Ϣ
											//accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											//newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											//dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
								}
								//-- ��Ϣ������� --
							}
							//��Ӫ����Ḵ������
							//update the sett_DailyAccountBalance
							try
							{
								//log.info("��ʼ����" + tmpBackDate.toString() + "���˻�����ս���е�һ����¼...");
								sett_DailyAccountBalanceDAO.update(tmpDailyAccountBalanceInfo);
								//log.info("����" + tmpBackDate.toString() + "�ɹ���");
							}
							catch (Exception e2)
							{
								e2.printStackTrace();
								throw new IException("Gen_E001");
							}
						}
					} //��������ѭ������������������������������������������������������������������������������������������������������
					//log.info("//��������ѭ������������������������������������������������������������������������������������������������������");
					isSuccess = 1;
				} //���ڴ��������˻�����Ϣ	
				else
				{
					//log.info("lhj debug info == ���ڴ��������˻�����Ϣ");
					isSuccess = 1; //hjliu
				}
			}
		}
		return isSuccess;
	}
	
	/**
	 * �ӿ����ƣ�������Ϣ�����
	 * ����������1��������Ϣ����ʱ�������˻�����ս���и��˻�ÿһ�յ��ۼ���Ϣ���ۼ�ǷϢ����Ϣ��
	 *           2������Ϣ���ý��㡱������Ϣ����֧������������黹���Ƚ��׽����˻���Ϣʱ����Ϣ��С��
	 *              ��ǰ��ʱ������ִ�б��ӿڡ�
	 *           3��ɾ�������Ϣ��¼ʱҲҪ���ñ��ӿڡ�
	 * @param AccountID    ���˻�ID
	 * @param SubAccountID ���˻�ID
	 * @param BackDate     �������Ϣ��
	 * @param BackAmount   ����׽��
	 * @param ExecuteDate  ִ����/�ػ�����
	 * @return long -1:���ɹ���1���ɹ�
	 * @throws IException
	 */
	public long accountInterestSettlelmentBackward(TransRepaymentLoanInfo transInfo) throws IException
	{
		log.info("������Ϣ����� AccountID == " + transInfo.getLoanAccountID());
		log.info("������Ϣ����� LoanNoteID == " + transInfo.getLoanNoteID());
		//log.info("������Ϣ����� SubAccountID == " + SubAccountID);
		log.info("������Ϣ����� BackDate == " + transInfo.getInterestClear());
		//log.info("������Ϣ����� BackAmount == " + BackAmount);
		log.info("������Ϣ����� ExecuteDate == " + transInfo.getExecute());
		
		Timestamp backDate = transInfo.getInterestClear();
		Timestamp executeDate = transInfo.getExecute();

		long isSuccess = -1; //�ɹ���־
		Connection conn = this.conn_Out;
		double accumulateNormalInterestAdjust = 0.0; //�ۼ�������Ϣ������
		double accumulateCompoundInterestAdjust = 0.0; //�ۼƸ�����Ϣ������
		double accumulateOverDueInterestAdjust = 0.0; //�ۼƷ�Ϣ��Ϣ������
		double newAccumulateNormalInterest = 0.0; //�µ��ۼ�������Ϣ������
		double newAccumulateCompoundInterest = 0.0; //�µ��ۼƸ�����Ϣ������
		double newAccumulateOverDueInterest = 0.0; //�µ��ۼƷ�Ϣ��Ϣ������
		double executeDateLoanInterest = 0.0; //�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ�����Ϣ��
		double executeDateCompoundInterest = 0.0; //�������˻��ĵ�ǰ���������ڹػ���Ϣ��Ĵ������
		double executeDateOverDueInterest = 0.0; //�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ��Ϣ��
		double arrearageInterest = 0.0; //ǷϢ���
		//��Ϣ���
		double interestBalance = 0.0;

		
		//����AccountID��sett_account���ж�λ���˻���¼

		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		UtilOperation utilOperation = new UtilOperation(conn);
		AccountInfo accountInfo = null;
		try
		{
			accountInfo = sett_AccountDAO.findByID(transInfo.getLoanAccountID());
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		log.info("������Ϣ������ȡ���˻��ɹ���");
		//����SubAccountID��sett_SubAccount���ж�λ���˻���¼
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			log.info("������Ϣ������ȡ���˻�....");
			subAccountAssemblerInfo = sett_SubAccountDAO.findByLoanNoteID(transInfo.getLoanAccountID(), transInfo.getLoanNoteID());
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
			throw new IException(true, "�޷��������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		log.info("������Ϣ������ȡ���˻��ɹ���");
		//���˻��еĴ�����Ϣ
		SubAccountLoanInfo subAccountLoanInfo = null;

		if (subAccountAssemblerInfo != null)
		{
			//ȡ���ڹػ�����ǰ�ĵ�ǰ���˻���������Ϣ��Э����Ϣ���������˻�������ǰ��Ϣ���������˻���
			//Ŀ�ģ��ڽ����ػ�����󣬸���ÿ����Ϣ������˻����˻�����ս���¼
			//      �����ۼ���Ϣ�ֶΣ�
			//      �µ��ۼ���Ϣ = �ػ�����ǰ����Ϣ + �ۼ���Ϣ������
			//      �˲������ڽ���ѭ�����Ҹ��������˻������޸ĵ����־֮ǰ����

			//�������˻�------------------------------------------------------------------
			subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
			//�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ�����Ϣ��
			executeDateLoanInterest = subAccountLoanInfo.getInterest();
			//�������˻��ĵ�ǰ���������ڹػ���Ϣ��Ĵ������
			executeDateCompoundInterest = subAccountLoanInfo.getCompoundInterest();
			//�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ��Ϣ��
			executeDateOverDueInterest = subAccountLoanInfo.getOverDueInterest();
			//�������˻�------------------------------------------------------------------

		}
		else
		{
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}

		if (accountInfo != null)
		{

			long currencyID = accountInfo.getCurrencyID(); //���˻��б���
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				//���ӿڶԶ��ڴ��������˻������м�Ϣ
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
					try
					{
						dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountLoanInfo.getID(), transInfo.getOfficeID(), transInfo.getCurrencyID(), getNextNDay(backDate, -1));
					}
					catch (SQLException e2)
					{
						e2.printStackTrace();
						throw new IException("Gen_E001");
					}
					//��ʼ���� -- �ۻ���Ϣ
					//�ۼ�������Ϣ������
					if(transInfo.getIsRemitInterest() == SETTConstant.BooleanValue.ISTRUE) {
						accumulateNormalInterestAdjust = 0.0; 
						newAccumulateNormalInterest = 0.0;
					}
					else {
						accumulateNormalInterestAdjust =  dailyAccountBalanceInfo.getInterest();
						if(transInfo.getAmount()>=0)
						{
							accumulateNormalInterestAdjust = UtilOperation.Arith.sub(accumulateNormalInterestAdjust, transInfo.getRealInterest());
						}
						newAccumulateNormalInterest = accumulateNormalInterestAdjust;
					}
					
					//�ۼƸ�����Ϣ������
					if(transInfo.getIsRemitCompoundInterest() == SETTConstant.BooleanValue.ISTRUE) {
						accumulateCompoundInterestAdjust = 0.0; 
						newAccumulateCompoundInterest = 0.0;
					}
					else{
						accumulateCompoundInterestAdjust =  dailyAccountBalanceInfo.getMcompoundinterest();
						accumulateCompoundInterestAdjust = UtilOperation.Arith.sub(accumulateCompoundInterestAdjust, transInfo.getRealCompoundInterest());
						newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
					}
					
					//�ۼƷ�Ϣ��Ϣ������
					if(transInfo.getIsRemitOverDueInterest() == SETTConstant.BooleanValue.ISTRUE) {
						accumulateOverDueInterestAdjust = 0.0; 
						newAccumulateOverDueInterest = 0.0;
					}
					else{
						accumulateOverDueInterestAdjust =  dailyAccountBalanceInfo.getMforfeitinterest();
						accumulateOverDueInterestAdjust = UtilOperation.Arith.sub(accumulateOverDueInterestAdjust, transInfo.getRealOverDueInterest());
						newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
					}
					
					//��ʼ���� -- ǷϢ���
					arrearageInterest = dailyAccountBalanceInfo.getAl_mArrearageInterest();
					//��ʼ���� -- ��Ϣ���
					interestBalance = dailyAccountBalanceInfo.getInterestBalance();
					
	
					//����ѭ������
					InterestCalculation interestCalculation = new InterestCalculation(conn);
					int loopDays = (int) interestCalculation.getIntervalDays(backDate, executeDate, 1);
					//				log.info("ѭ�������� " + loopDays);
	
					//				log.info("lhj debug info =========������Ϣ����ѭ����ʼ......");
					for (int i = 0; i < loopDays; i++)
					{ //��������ѭ����ʼ����������������ExecuteDate���죭����������������������������������������������
						//�����м����-------------------------------------------------------start-------
						//����������Ϣ������
						double todayNormalInterestAdjust = 0.0;
						//���ո�����Ϣ
						double todayCompoundIntersetAdjust = 0.0;
						//���շ�Ϣ��Ϣ
						double todayOverDueIntersetAdjust = 0.0;
						//��ǰ��Ϣ����
						Timestamp tmpBackDate = getNextNDay(backDate, i);
						//��ǰ��Ϣ����+1
						Timestamp tmpBackDateAddOne = getNextNDay(tmpBackDate, 1);
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountLoanInfo.getID(), transInfo.getOfficeID(), transInfo.getCurrencyID(), tmpBackDate);
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						long dabi_AccountID = -1;
						if (dailyAccountBalanceInfo != null)
						{
							dabi_AccountID = dailyAccountBalanceInfo.getAccountID();
							//						log.info("lhj debug info ===========dabi_AccountID�ǣ� " + dabi_AccountID);
						}

						if (dailyAccountBalanceInfo != null && dabi_AccountID > 0)
						{
							//-------------------------------һ�㵹���----------------------start---------------------------------------
							//						log.info("���˻�����ս���д��ڼ�¼����ʼ���С�һ�㵹��ס�......");
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
									accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								//--------һ�㵹���------------�����˻���Ϣ---------start----------------------------------------------
								log.info("��һ�㵹��ס��п�ʼ���д����˻���Ϣ......");
								todayNormalInterestAdjust = dailyAccountBalanceInfo.getDailyInterest();
								
								//�������ۼ�������Ϣ
								accumulateNormalInterestAdjust = UtilOperation.Arith.add(accumulateNormalInterestAdjust, todayNormalInterestAdjust);
								newAccumulateNormalInterest = accumulateNormalInterestAdjust;
								dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
								
								//modify by leiyang3
								//2010/11/28
								//���ڼ�����Ӫ����ں�ķ�Ϣ
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
								{
									LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(transInfo.getLoanNoteID());
									if(loanPayNoticeInfo == null){
										throw new IException("û���ҵ���Ч�ķſ��Ϣ");
									}
									
									Timestamp loanStartDate = loanPayNoticeInfo.getStart();
									Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
									
									//-- �������㿪ʼ --
									if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�и������ԣ��ж��Ƿ���и�������
									{
											//��Ϣ����֧������Ϣ��Ϣ�������ۼ�ǷϢ
											//�������׻�Ϣ����Ҫ�ж��Ƿ���Ҫ����ۼ�ǷϢ
											InterestsInfo preinterestsInfo = null;
											InterestsInfo todayinterestsInfo = null;
											DailyAccountBalanceInfo predailyAccountBalanceInfo = null;
											double payInterest = 0;
											double payCompoundInterest = 0;
											double payOverDueInterest = 0;
											double tmpArrearageInterest = 0;
											try
											{
												preinterestsInfo = utilOperation.findPayInterestAmount(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
												todayinterestsInfo = utilOperation.findAllClearCompoundInterset(subAccountAssemblerInfo.getSubAccountLoanInfo().getID(),tmpBackDate);
												predailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountLoanInfo.getID(), transInfo.getOfficeID(), transInfo.getCurrencyID(), getNextNDay(tmpBackDate,-1));
												tmpArrearageInterest = predailyAccountBalanceInfo.getAl_mArrearageInterest();
												accumulateCompoundInterestAdjust = predailyAccountBalanceInfo.getMcompoundinterest();
											}
											catch(Exception e)
											{
												throw new IException(e.getMessage());
											}
											if(transInfo.getAmount()>=0)
											{
												if(i==0)
												{
													payInterest = transInfo.getRealInterest()+todayinterestsInfo.getPayminterest();
													payCompoundInterest = transInfo.getRealCompoundInterest()+todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = transInfo.getRealOverDueInterest()+todayinterestsInfo.getPaymforfeitinterest();
												}
												else
												{
													payInterest = todayinterestsInfo.getPayminterest();
													payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest();
												}
												//��Ϣ���ж�
												if(utilOperation.isClearInterestDay(dailyAccountBalanceInfo.getSubAccountID(), tmpBackDate))
												{
													double temp1 = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getInterest() ,payInterest);
													double temp2  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMcompoundinterest() , payCompoundInterest);
													double temp3  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMforfeitinterest() , payOverDueInterest);
													
													tmpArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(temp1,temp2),temp3);
												}
												else
												{
														if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>payInterest)
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,payInterest);
														}
														else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<payInterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
														}
														if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>payCompoundInterest)
														{
															tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payCompoundInterest);
														}
														else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<payCompoundInterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
														}
														if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>payOverDueInterest)
														{
															tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payOverDueInterest);
														}
														else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<payOverDueInterest&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
														{
															tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
														}
												}
											}
											else
											{//ȡ�����˷���
												if(i==0)
												{
													payInterest = -transInfo.getRealInterest()+todayinterestsInfo.getPayminterest();
													payCompoundInterest = -transInfo.getRealCompoundInterest()+todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = -transInfo.getRealOverDueInterest()+todayinterestsInfo.getPaymforfeitinterest();
												}
												else
												{
													payInterest = todayinterestsInfo.getPayminterest();
													payCompoundInterest = todayinterestsInfo.getPaymcompoundinterest();
													payOverDueInterest = todayinterestsInfo.getPaymforfeitinterest();
												}
												//��Ϣ���ж�
												if(utilOperation.isClearInterestDay(dailyAccountBalanceInfo.getSubAccountID(), tmpBackDate))
												{
													double temp1 = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getInterest() , payInterest);
													double temp2  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMcompoundinterest() , payCompoundInterest);
													double temp3  = UtilOperation.Arith.sub(predailyAccountBalanceInfo.getMforfeitinterest() , payOverDueInterest);
													tmpArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(temp1,temp2),temp3);
												}
												else
												{
													if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()>payInterest)
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,payInterest);
													}
													else if(preinterestsInfo.getMinterest()-preinterestsInfo.getPayminterest()<payInterest&&preinterestsInfo.getMinterest()>preinterestsInfo.getPayminterest())
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMinterest(),preinterestsInfo.getPayminterest()));
													}
													if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()>payCompoundInterest)
													{
														tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payCompoundInterest);
													}
													else if(preinterestsInfo.getMcompoundinterest()-preinterestsInfo.getPaymcompoundinterest()<payCompoundInterest&&preinterestsInfo.getMcompoundinterest()>preinterestsInfo.getPaymcompoundinterest())
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMcompoundinterest(),preinterestsInfo.getPaymcompoundinterest()));
													}
													if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()>payOverDueInterest)
													{
														tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payOverDueInterest);
													}
													else if(preinterestsInfo.getMforfeitinterest()-preinterestsInfo.getPaymforfeitinterest()<payOverDueInterest&&preinterestsInfo.getMforfeitinterest()>preinterestsInfo.getPaymforfeitinterest())
													{
														tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(preinterestsInfo.getMforfeitinterest(),preinterestsInfo.getPaymforfeitinterest()));
													}
												}
											}
											if(tmpArrearageInterest<0.01)
											{
												tmpArrearageInterest = 0;
											}
											
										
										//��������
										InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
										paramInfo.setLoanNoteID(transInfo.getLoanNoteID());
										paramInfo.setValidDate(tmpBackDate);
										InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
										
										//������ÿ�ո�����Ϣ
										todayCompoundIntersetAdjust = interestCalculation.calculateLoanAccountInterest(transInfo.getCurrencyID(), compoundRate.getRate(), compoundRate.getType(), tmpArrearageInterest, tmpBackDate, tmpBackDateAddOne);
										//�������ۼƸ�����Ϣ
										accumulateCompoundInterestAdjust = UtilOperation.Arith.add(UtilOperation.Arith.add(newAccumulateCompoundInterest, todayCompoundIntersetAdjust),todayinterestsInfo.getPaymcompoundinterest());
										newAccumulateCompoundInterest = accumulateCompoundInterestAdjust;
										if(newAccumulateCompoundInterest<0.01)
										{
											newAccumulateCompoundInterest = 0;
										}
										dailyAccountBalanceInfo.setAl_mArrearageInterest(tmpArrearageInterest);
										
										//����ÿ�ո�����Ϣ
										dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundIntersetAdjust);
										//�ۼƸ�����Ϣ
										dailyAccountBalanceInfo.setMcompoundinterest(newAccumulateCompoundInterest);
									}
									//-- ����������� --
									
									//-- ��Ϣ���㿪ʼ  --
									//�Ƿ���з�Ϣ
									if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�з�Ϣ���ԣ��ж��Ƿ���з�Ϣ
									{
										if(!loanEndDate.after(tmpBackDate))
										{
											InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
											paramInfo.setLoanNoteID(transInfo.getLoanNoteID());
											paramInfo.setValidDate(tmpBackDate);
											
											InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
											//ÿ�շ�Ϣ�ܽ�����δ�����
											double overDueAccumulateBalance = interestBalance;
											//������ÿ�շ�Ϣ��Ϣ
											todayOverDueIntersetAdjust = interestCalculation.calculateLoanAccountInterest(transInfo.getCurrencyID(), overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, tmpBackDate, tmpBackDateAddOne);
											//�������ۼƷ�Ϣ��Ϣ
											accumulateOverDueInterestAdjust = UtilOperation.Arith.add(newAccumulateOverDueInterest, todayOverDueIntersetAdjust);
											newAccumulateOverDueInterest = accumulateOverDueInterestAdjust;
											
											//����ÿ�շ�Ϣ��Ϣ
											dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueIntersetAdjust);
											//�ۼƷ�Ϣ��Ϣ
											dailyAccountBalanceInfo.setMforfeitinterest(newAccumulateOverDueInterest);
											//���շ�Ϣ����	     
											dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
											//�������õ���������������
											dailyAccountBalanceInfo.setDailyInterest(0.0);
											//���������ۼƴ�����Ϣ
											accumulateNormalInterestAdjust = UtilOperation.Arith.sub(newAccumulateNormalInterest, todayNormalInterestAdjust);
											newAccumulateNormalInterest = accumulateNormalInterestAdjust;
											dailyAccountBalanceInfo.setInterest(newAccumulateNormalInterest);
										}
									}
									//-- ��Ϣ������� --
								}
	
								//log.info("lhj debug info ==[" + SubAccountID + "]�����ۼ�������Ϣ��:" + newAccumulateNormalInterest);
								//log.info("��һ�㵹��ס��п�ʼ���д����˻���Ϣ����");
							}
	
							//                         Ϊ�˻�����ս���и���һ���¼�¼
	
							try
							{
								//log.info("��һ�㵹��ס��п�ʼ�����˻�����ս����������" + tmpBackDate.toString() + "������.....");
								sett_DailyAccountBalanceDAO.update(dailyAccountBalanceInfo);
							}
							catch (Exception e4)
							{
								e4.printStackTrace();
								throw new IException("����ʧ�ܣ������ԣ�");
							}
							//log.info("��һ�㵹��ס��п�ʼ�����˻�����ս����������" + tmpBackDate.toString() + "�����ݳɹ���");
							//--------------------------------һ�㵹���----------------------end---------------------------------------
						}
					} //��������ѭ��������������������������������������������������������������������������������������������������
					//log.info("ѭ������");
						isSuccess = 1;
				} //���ڴ��������˻������м�Ϣ
				else
				{
					isSuccess = 1;
				}
			}
		}
		return isSuccess;
	}
	

	/**
	 * �ӿ����ƣ�������������ڴ���
	 * ����������1:�ڸ����������ڵ���ػ�ʱ�����˻��ϵ��ۼ���Ϣ��mInterest�����뵽�����˻����ۼ�ǷϢ��AL_mArrearageInterest����
	 *           2:���ӿ�ֻ��������˻���
	 *           3:2003-12-29����ȷ�ϣ��Ը���ҲҪ������Ϣ�����������ĸ����������ԣ��ڸ����������ڵ��죬Ҫ�Դ���ĸ�������һ��
	 *             ��Ϣ������ĸ�����������Ϣ�ϼ�Ϊ���˻���ǷϢ�����ڼ�����һ�ڵĸ�����
	 * @param conn    �������ݿ����ӣ��ɷ����������ṩ���룬����Ϊ�գ�
	 * @param officeID�������´�ID����
	 * @param currencyID������
	 * @param closeDate�� �ػ����ڡ�
	 * @return long -1 ���ɹ���1���ɹ�
	 * @throws IException
	 */
	public long loanCompoundCalculateDateDeal(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		Collection loanAccountCollection = null;
		try
		{
			//�жϵ�ǰ�ػ������Ƿ����[���������������ñ�]�е�һ������			
			Sett_CompoundInterestSettingDAO sett_compoundInterestSettingDAO = new Sett_CompoundInterestSettingDAO(conn);
			long returnValue = sett_compoundInterestSettingDAO.findByCompoundInterestDate(officeID, currencyID, closeDate);
			if (returnValue == 1)
			{

				Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
				InterestCalculation interestCalculation = new InterestCalculation(conn);
				UtilOperation utilOperation = new UtilOperation(conn);
				//				
				//��ѯ���еĴ����˻�
				loanAccountCollection = sett_SubAccountDAO.findAllLoanAccount(officeID, currencyID);
				if (loanAccountCollection != null && loanAccountCollection.size() > 0)
				{
					Iterator loanAccountIterator = loanAccountCollection.iterator();
					log.info("%%%%%%%%%%%%%%��������ڴ���%%%%%%%%%%%%%%%%%%%%%%%");
					while (loanAccountIterator.hasNext())
					{
						SubAccountAssemblerInfo subAccountAssemblerInfo = (SubAccountAssemblerInfo) loanAccountIterator.next();
						SubAccountLoanInfo subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
						long accountID = subAccountLoanInfo.getAccountID();
						long subAccountID = subAccountLoanInfo.getID();
						long loanNoteID = subAccountLoanInfo.getLoanNoteID();
						//���
						double loanBalance = subAccountLoanInfo.getBalance();
						//����
						double compoundInterest = 0.0;
						//��Ϣ
						double forfeitInterest = 0.0;
						//����ǷϢ
						double overDueArrearageInterest = 0.0;
						overDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest();
						//�жϸû��Ƿ�������!!!!!!!!!!!!!!!!!!!!!!!1
						OverDueInfo overDueInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);

						//2004-01-16 �޸�������
						//����û��������Ҽ��㸴��Al_nloannoteid in (select npayformid from loan_overdueform where nstatusid=2 and NISCOMPOUNDINTEREST=��)

						if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
						{ //����û�������
							long isCompoundInterest = -1;
							isCompoundInterest = overDueInfo.getIsCompoundInterest();
							log.info("�����˻����Ƿ���ȡ������" + isCompoundInterest);
							//��ѡ�˼��ո���������������������������������������
							if (isCompoundInterest == Constant.YesOrNo.YES)
							{
								//�޸� 2004-1-29 -----------------------------------------------------------------

								// ��������˻��ĵ�ǰ��Ϣ��ע��������ʱ����������ǰ��Ϣ�������������ͣ�㡣
								LoanAccountInterestInfo loanAccountInterestInfo = interestCalculation.getLoanAccountInterest(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate);
								double loanInterest = loanAccountInterestInfo.getInterest();
								// ��������˻��ĵ�ǰ������ע��������ʱ����������ǰ�����������������ͣ��
								LoanAccountInterestInfo loanAccountCompoundInterestInfo =
									interestCalculation.getLoanAccountFee(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
								compoundInterest = loanAccountCompoundInterestInfo.getInterest();
								//��������˻��ĵ�ǰ��Ϣ  
								LoanAccountInterestInfo loanForfeitInterestInfo =
									interestCalculation.getLoanAccountFee(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate, SETTConstant.InterestFeeType.FORFEITINTEREST);
								//��ǰ��Ϣ
								forfeitInterest = loanForfeitInterestInfo.getInterest();
								log.info("�����������Ѿ����ڣ������ո�����������");
								log.info("���ǰ���˻�  �� " + subAccountID);
								log.info("���ǰ���    �� " + loanBalance);
								log.info("���ǰ��Ϣ    �� " + loanInterest);
								log.info("���ǰ����    �� " + compoundInterest);
								log.info("���ǰ��Ϣ    �� " + forfeitInterest);
								log.info("���ǰ����ǷϢ�� " + overDueArrearageInterest);
								overDueArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(loanInterest, compoundInterest), forfeitInterest);
								// �޸� 2004-1-29 -----------------------------------------------------------------

								log.info("���ո����������ǷϢ�� " + overDueArrearageInterest);
								log.info("�����������Ѿ����ڣ������ո�����������");
								//�������˻�������ǷϢ
								sett_SubAccountDAO.updateArrearageInterest(subAccountID, overDueArrearageInterest, SETTConstant.InterestFeeType.FORFEITINTEREST);

							}
						}
						else
						{ //����û�û������

							//���㵱ǰ�����˻��ĵ�ǰ����
							LoanAccountInterestInfo loanAccountInterestInfo =
								interestCalculation.getLoanAccountFee(conn, officeID, currencyID, accountID, subAccountID, closeDate, closeDate, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
							//��ǰ����
							compoundInterest = loanAccountInterestInfo.getInterest();
							log.info("����������û�����ڣ�������");
							log.info("���ǰ���˻�  �� " + subAccountID);
							log.info("���ǰ����    �� " + compoundInterest);
							log.info("���ǰ��Ϣ    �� " + subAccountLoanInfo.getInterest());
							log.info("���ǰ�ۼ�ǷϢ�� " + compoundInterest + subAccountLoanInfo.getInterest());
							log.info("����������û�����ڣ�������");
							//�������˻����ۼ�ǷϢ
							sett_SubAccountDAO.updateArrearageInterest(subAccountID, compoundInterest, SETTConstant.InterestFeeType.COMPOUNDINTEREST);

						}
					}
					log.info("%%%%%%%%%%%%%%��������ڴ���%%%%%%%%%%%%%%%%%%%%%%%");
					lReturn = 1;

				}

			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException("Gen_E001");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lReturn;
	}

	/**
	 * �ӿ����ƣ��˻�����սᡣ
	 * �����������ػ�ʱ���ñ��ӿڣ������˻������˻����е� �����Ϣ���뵽�˻�����ս���С�
	 * @param conn ���ݿ����ӣ��ɷ����ĵ������ṩ��never null.
	 * @param officeID ���˻��еİ��´�ID��
	 * @param currencyID ���˻��еı���ID��
	 * @param closeDate  ���ιػ�ʱ�䡣
	 * @return long -1�����ɹ���1���ɹ�
	 * @throws IException
	 */

	public long dailyAccountBalanceSettlement(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{

		long lReturn = -1;

		Collection subAccountInfoCollection = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);

		//�����˻����в��������˻�״̬��δ�廧�����˻�����
		try
		{
			//log.info("��ʼ�����˻����в�ѯ���з��廧״̬�����˻�����......");
			subAccountInfoCollection = sett_SubAccountDAO.findByStatus(officeID, currencyID);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		if (subAccountInfoCollection != null && subAccountInfoCollection.size() > 0)
		{
			//��ʱ���󱣴��ۼ�δ���˽���������˻�
			ArrayList _alTemp = new ArrayList(16);

			//log.info("���˻����й���ѯ��" + subAccountInfoCollection.size() + "���廧״̬�����˻���¼");
			Iterator subAccountAssemblerInfoIterator = subAccountInfoCollection.iterator();
			while (subAccountAssemblerInfoIterator.hasNext())
			{
				SubAccountAssemblerInfo subAccountAssemblerInfo = (SubAccountAssemblerInfo) subAccountAssemblerInfoIterator.next();
				/////////////////////////////////////////////////////////////////////////////////////
				//subAccountAssemblerInfo�к������ֲ�ͬ��ʵ�����
				//subAccountCurrentInfo,subAccountLoanInfo,subAccountFixedInfo
				//��Ϊ������ڹ��е����ԣ�����ȡһ��ʵ����󣺻��ڶ���
				/////////////////////////////////////////////////////////////////////////////////////
				SubAccountCurrentInfo subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
				//���˻���nAccountID
				long accountID = subAccountCurrentInfo.getAccountID();
				Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
				AccountInfo accountInfo = null;

				try
				{
					//log.info("�������˻���Ӧ�����˻�ID��ѯ���˻�......");
					accountInfo = sett_AccountDAO.findByID(accountID);
					if (accountInfo == null)
					{
						throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
					}
					//�½�һ���˻�����ս�����
					DailyAccountBalanceInfo dailyAccountBalanceInfo = new DailyAccountBalanceInfo();

					dailyAccountBalanceInfo.setDate(closeDate); //����
					//
					if (accountInfo != null)
					{
						//log.info("��ʼΪ�½����˻�����ս�����ֵ.........");
						dailyAccountBalanceInfo.setAccountID(accountID);
						//���˻�ID
						dailyAccountBalanceInfo.setSubAccountID(subAccountCurrentInfo.getID());
						//���˻�ID
						dailyAccountBalanceInfo.setAccountStatusID(accountInfo.getStatusID());
						//���˻�״̬�ɣ�
						dailyAccountBalanceInfo.setBalance(subAccountCurrentInfo.getBalance());
						//�˻����
						if (subAccountCurrentInfo.getIsInterest() == SETTConstant.BooleanValue.ISTRUE)
						{ //��Ϣ
							dailyAccountBalanceInfo.setInterestBalance(subAccountCurrentInfo.getBalance());
							//��Ϣ���
						}
						if (subAccountCurrentInfo.getIsInterest() == SETTConstant.BooleanValue.ISFALSE)
						{ //����Ϣ
							double interestBalance = 0.0;
							dailyAccountBalanceInfo.setInterestBalance(interestBalance); //��Ϣ���Ϊ�㣡

						}
						dailyAccountBalanceInfo.setSubAccountStatusid(subAccountCurrentInfo.getStatusID()) ;
						dailyAccountBalanceInfo.setFreezeAmount(subAccountCurrentInfo.getCapitalLimitAmount());
						log.print("dailyAccountBalanceInfo.setSubAccountID="+dailyAccountBalanceInfo.getSubAccountID());
						log.print("dailyAccountBalanceInfo.setFreezeAmount="+dailyAccountBalanceInfo.getFreezeAmount());
						//log.info("��ֵ��������ʼ��sett_DailyAccountBalance��������һ���µļ�¼......");
						Sett_DailyAccountBalanceDAO sett_DailyAccountBalance = new Sett_DailyAccountBalanceDAO(conn);
						DailyAccountBalanceInfo newDailyAccountBalanceInfo = null;
						newDailyAccountBalanceInfo = sett_DailyAccountBalance.findAllBySubAccountIDAndDate(subAccountCurrentInfo.getID(), officeID, currencyID, closeDate);
						if (newDailyAccountBalanceInfo != null){
							System.out.println("���ڣ�" + closeDate + ", ���˻���" + subAccountCurrentInfo.getID() + "���˻������¼�Ѿ����ڣ�������ʧ��");
							throw new IException(true, "���ڣ�" + closeDate + ", ���˻���" + subAccountCurrentInfo.getID() + "���˻������¼�Ѿ����ڣ�������ʧ��", null);
						}
						if (sett_DailyAccountBalance.add(dailyAccountBalanceInfo) > 0)
						{
							System.out.println("sett_DailyAccountBalance��������һ���µļ�¼�ɹ���");
						}
					}
				}
				catch (IException ie)
				{
					ie.printStackTrace();
					throw ie;
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					throw new IException("Gen_E001");
				}

				if (subAccountCurrentInfo.getDailyUncheckAmount() != 0.0)
				{
					_alTemp.add(subAccountCurrentInfo);
				}

			} //ѭ�����������÷���ֵΪ1

			RecordAccountInfo _record = new RecordAccountInfo();
			_record.recordAccountInfo(_alTemp);

			//log.info("�˻�����ս������");
			lReturn = 1;
		}

		return lReturn;
	}

	/**
	 * �ӿ����ƣ�ÿ����Ϣ
	 * ����˵����1�����˻�����ս�����ڱ��ϼ�����Ϣ��
	 *           2�����Դ����е��������ʺ͵������ʽ���ÿ�ռ�Ϣ��
	 *           3�������������ݸ��µ��˻�����ս������˻����С�
	 * @param conn       ���ݿ����ӣ��ɷ��������ߴ��룬����Ϊ�գ�
	 * @param officeID   ���´�ID��
	 * @param currencyID ���˻����֡�
	 * @param closeDate  �ػ�����
	 * @return long -1:���ɹ�   1:�ɹ�
	 * @throws IException
	 */

	public long dailyCalculateInterest(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		
		//
		log.info(closeDate.toString() + "�Ĺػ���Ϣ��ʼ......");
		Collection dailyAccountBalanceInfoVector = null;
		DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
		//
		String closeDateString = closeDate.toString();
		int closeDateDay = new Integer(closeDateString.substring(8, 10)).intValue();
		Timestamp closeDateAddOne = getNextNDay(closeDate, 1);

		Sett_DailyAccountBalanceDAO dailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		try
		{
			//��ѯ�˻�����ս��������ΪcloseDate�����м���
			//System.out.println("��ʼ��ѯ�˻�����ս��������Ϊ��ǰ�ػ����ڵ����м�¼....");
			dailyAccountBalanceInfoVector = dailyAccountBalanceDAO.findByDate(officeID, currencyID, closeDate);

			if (dailyAccountBalanceInfoVector != null && dailyAccountBalanceInfoVector.size() > 0)
			{
				log.info("��ѯ����������ѯ��" + dailyAccountBalanceInfoVector.size() + "���˻�����ս��¼");
				//��Ҫ���ü���ӿڵķ���
				InterestCalculation interestCalculation = new InterestCalculation(conn);
				//��Ҫ�����˻�����ս��Sett_DailyAccountBalanceDAO�ķ����������˻�����ս��

				//��Ҫ�������˻�Sett_SubAccountDAO�ķ������������˻�
				Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
				Iterator vectorIterator = dailyAccountBalanceInfoVector.iterator();
				while (vectorIterator.hasNext())
				{
					dailyAccountBalanceInfo = (DailyAccountBalanceInfo) vectorIterator.next();
					long accountID = dailyAccountBalanceInfo.getAccountID();
					// ���˻�ID
					long subAccountID = dailyAccountBalanceInfo.getSubAccountID();
					// ���˻�ID
					//�������˻�ID��λһ�����˻���¼��
					AccountInfo accountInfo = null;
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
					UtilOperation utilOperation = new UtilOperation(conn);
					accountInfo = sett_AccountDAO.findByID(accountID);
					if (accountInfo == null)
					{
						throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
					}
					if (accountInfo != null)
					{
						//�˻����������˻���ȡ��
						long accountTypeID = accountInfo.getAccountTypeID();
				        log.debug("---------�ж��˻�����------------");
				        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
				        AccountTypeInfo accountTypeInfo = null;
				        try {
							accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						if (accountTypeInfo != null) {
							//���ڡ�֪ͨ����˻��������˻�������ÿ�ռ�Ϣ                
							if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
								accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
								accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
							{
								//�������˻�ID��λһ�����˻���¼��
								subAccountAssemblerInfo = sett_SubAccountDAO.findByID(subAccountID);
								//
								SubAccountCurrentInfo subAccountCurrentInfo = null;
								SubAccountLoanInfo subAccountLoanInfo = null;
								SubAccountFixedInfo subAccountFixedInfo = null;
								
								if (subAccountAssemblerInfo == null)
								{
									throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
								}
								if (subAccountAssemblerInfo != null)
								{
									//���˻��л���ʵ�����
									subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
									
									//���˻��д���ʵ�����
									subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
									
									//���˻��ж��ڣ���֤��ʵ�����
									subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();

									//������ڴ��ı����������������ʡ�������Ϣ��Э����Э�����ʡ�Э����Ϣ
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
									{
										long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();
										//���ʼƻ�ID
										//ֻ�������ʼƻ�����-1�����˻�========2003-11-14=======
										if (interestRatePlanID > 0)
										{	
											long lInterestRatePlanTypeID = NameRef.getInterestRatePlanTypeIDByID(interestRatePlanID);
											double balance = subAccountCurrentInfo.getBalance();//���˻���ǰ���
											double negotiateAmount = subAccountCurrentInfo.getNegotiateAmount();// Э�������
											double negotiateUnit = subAccountCurrentInfo.getNegotiateUnit();//Э����λ��Ԫ��
											//�޸�Ϊֱ��ȡ��Э������nNegotiateRate 2003-11-19 ���ݾ�
											double negotiateRate = subAccountCurrentInfo.getNegotiateRate();
											double subInterest = subAccountCurrentInfo.getInterest();//���˻���ǰ��Ϣ
											double subNegotiateInterest = subAccountCurrentInfo.getNegotiateInterest();//���˻���ǰЭ����Ϣ
											//Modify by leiyang 2008-06-30 
											long IsNegotiate = subAccountCurrentInfo.getIsNegotiate(); //�Ƿ�Э�����
											
											//����
											CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = null;
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												log.info("---------------���ʼƻ�����Ϊ�վ�����Ϣ�������վ������Ϣ���� modify by yanliu");
												log.info("---------------���ʼƻ� = "+interestRatePlanID+"--- ���ʼƻ����� = "+lInterestRatePlanTypeID);
												log.info("---------------���˻�ID = "+subAccountCurrentInfo.getID());
												Timestamp tsDateStart = subAccountCurrentInfo.getClearInterestDate();
												Timestamp tsDateEnd = closeDateAddOne;
												double sumBalance = dailyAccountBalanceDAO.sumAccountBalanceBySubAccountIDAndDate(subAccountCurrentInfo.getID(),tsDateStart,tsDateEnd);
												double averageBalance = UtilOperation.Arith.div(sumBalance,interestCalculation.getIntervalDays(tsDateStart,tsDateEnd,1));
												
												//Modify by leiyang ����У��Э�����ķ�ʽ
												//���� IsNegotiate �������Ƿ�ΪЭ�����
												currentDepositAccountInterestInfo =
													interestCalculation.calculateCurrentDepositAccountInterestForAverageBalance(
														officeID,
														currencyID,
														interestRatePlanID,
														averageBalance,		//�վ����
														balance,			//���ռ�Ϣ���
														subAccountCurrentInfo.getClearInterestDate(),	//�ϴν�Ϣ����
														closeDateAddOne,								//�ػ�����һ��
														negotiateAmount,
														negotiateUnit,
														negotiateRate,
														IsNegotiate);										
											}
											else
											{
												System.out.println("-----------���ʼƻ����Ͳ����վ�����Ϣ������һ����Ϣ���� modify by yanliu");										
												System.out.println("---------------���ʼƻ� = "+interestRatePlanID+"--- ���ʼƻ����� = "+lInterestRatePlanTypeID);
												//Modify by leiyang ����У��Э�����ķ�ʽ
												//���� IsNegotiate �������Ƿ�ΪЭ�����
												currentDepositAccountInterestInfo =
													interestCalculation.calculateCurrentDepositAccountInterest(
														officeID,
														currencyID,
														interestRatePlanID,
														balance,
														closeDate,
														closeDateAddOne,
														negotiateAmount,
														negotiateUnit,
														negotiateRate,
														IsNegotiate);
											}
	
											//
											double normalInterestRate = currentDepositAccountInterestInfo.getNormalInterestRate();
											double negotiateInterestRate = currentDepositAccountInterestInfo.getNegotiationInterestRate();
											//����������Ϣ
											double todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();
											double todayNegotiateInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
											//�����˻�����ս��
											double newInterest = 0.0;
											//����������Ϣ����˻�����ս���е��µ��ۼ�������Ϣ
											double newNegotiateInterest = 0.0;
											//����������Ϣ����˻�����ս���е��µ��ۼ�Э����Ϣ
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												newInterest = todayNormalInterest;
											}
											else
											{
												newInterest = UtilOperation.Arith.add(subInterest, todayNormalInterest);											
											}
											newNegotiateInterest = UtilOperation.Arith.add(subNegotiateInterest, todayNegotiateInterest);
	
											log.debug(	" ���˻�ID�ǣ�" + subAccountID + "���µ�Э����Ϣֵ��:" + newNegotiateInterest + " == " + "���˻�Э����Ϣֵ��" + subNegotiateInterest + "��������������Э����Ϣֵ�� " + todayNegotiateInterest);
											dailyAccountBalanceInfo.setInterest(newInterest);
											dailyAccountBalanceInfo.setAc_mNegotiateInterest(newNegotiateInterest);
											//add yanliu 2005-11-24 
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												//�վ�����Ϣ���������todayNormalInterest�ǽ�ֹ�����յ��ۼ�Ӧ����Ϣ�����Խ�����ϢΪtodayNormalInterest-subInterest
												dailyAccountBalanceInfo.setDailyInterest(UtilOperation.Arith.sub(todayNormalInterest,subInterest));
											}
											else
											{
												//���վ�����Ϣ�����todayNormalInterest�ǽ��յ���Ϣ
												dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
											}
											dailyAccountBalanceInfo.setInterestRate(normalInterestRate);
											dailyAccountBalanceInfo.setAc_mNegotiateRate(negotiateInterestRate);
											dailyAccountBalanceInfo.setAc_mNegotiateBalance(currentDepositAccountInterestInfo.getNegotiationBalance());//Э�����
											dailyAccountBalanceInfo.setInterestBalance(currentDepositAccountInterestInfo.getNormalBalance());//��Ϣ���
											dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(currentDepositAccountInterestInfo.getNegotiationInterest());//����Э����Ϣ
											//
											dailyAccountBalanceDAO.update(dailyAccountBalanceInfo);

											//�������˻�
											if(lInterestRatePlanTypeID == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
											{
												subAccountCurrentInfo.setInterest( todayNormalInterest);
											}
											else
											{
												subAccountCurrentInfo.setInterest(UtilOperation.Arith.add(subInterest, todayNormalInterest));											
											}

											double tmpNegotiateInterest = UtilOperation.Arith.add(subNegotiateInterest, todayNegotiateInterest);
											subAccountCurrentInfo.setNegotiateInterest(tmpNegotiateInterest);
											//
											sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
										}
									}
									else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
									{
										/**
										 * ��֤������Ϣ���㷽ʽ������˻����ƣ�ÿ�춼��Ϣ��
										 * ���ʵ�ȡֵ������ػ����ڱ�֤����Ľ�������֮ǰ�������������ڣ�����ȡ��֤������������¼������ʣ�
										 *           ����ػ����ڱ�֤����Ľ�������֮�󣨺��������ڣ�����ȡ�˻����õ����ʼƻ������ʡ�
										 */
										log.info("��ʼ��֤����ÿ����Ϣ..........");
										log.info("**************** �˻� ��"+accountID);
										log.info("**************** ���˻� ��"+subAccountID);
										log.info("**************** ��Ϣ���� ��"+closeDate);
										log.info("**************** ��֤��浥������ ��"+subAccountFixedInfo.getEndDate());
										subAccountFixedInfo.setAccountID(accountID);
										subAccountFixedInfo.setID(subAccountID);

										log.info("��֤������δ���ڣ�����ȡ��֤��浥����¼�������.....");
										
										//�ӱ�֤��浥�еõ�����ֵ:
										double marginInterestRate = 0.00;
										
										double balance = 0.00;              //���˻���ǰ���
										double negotiateAmount = 0.00;      //Э�������
										double negotiateUnit = 0.00;        //Э����λԪ��
										double negotiateRate = 0.00;        //Э������
										double subInterest = 0.00;          //���˻���ǰ��Ϣ
										double subNegotiateInterest = 0.00; //���˻���ǰЭ����Ϣ

										//������־
										long daysFlag = 1;
										//���ʱ�־
										long interestRateDaysFlag = SETTConstant.InterestRateDaysFlag.DAYS_360;
										//������Ϣ
										double commonInterest = 0.0;
										
										//�������
										double commonBalance = 0.0;
										//��������
										double normalInterestRate = 0.0;

										normalInterestRate = 0.0;
										
										//����������Ϣ
										double todayNormalInterest = 0.0;
										//�����˻�����ս��
										double newInterest = 0.0;
										//����������Ϣ����˻�����ս���е��µ��ۼ�������Ϣ
										double newNegotiateInterest = 0.0;
										//����������Ϣ����˻�����ս���е��µ��ۼ�Э����Ϣ
										
										//һ����֤���˻������ж���浥��ÿ���浥�ֱ��Ϣ

										//��ʼ��Ϣ
										balance = subAccountFixedInfo.getBalance();
										
										subInterest = subAccountFixedInfo.getInterest();
										
										CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = new CurrentDepositAccountInterestInfo();
										
										//��������
										normalInterestRate = subAccountFixedInfo.getRate();
										
										//������Ϣ 
										commonInterest = interestCalculation.caculateInterest(balance, closeDate, closeDateAddOne, SETTConstant.InterestRateDaysFlag.DAYS_360 , normalInterestRate, SETTConstant.InterestRateTypeFlag.YEAR, SETTConstant.InterestRateDaysFlag.DAYS_360);
										
										//�������
										commonBalance = balance;
										
										long intervalDays = interestCalculation.getIntervalDays(closeDate, closeDate, SETTConstant.InterestRateTypeFlag.YEAR);
										
										currentDepositAccountInterestInfo.setIntervalDays(intervalDays); 
										currentDepositAccountInterestInfo.setNegotiationBalance(0.00);
										currentDepositAccountInterestInfo.setNegotiationInterest(0.00);
										currentDepositAccountInterestInfo.setNormalBalance(commonBalance);
										currentDepositAccountInterestInfo.setNormalInterest(commonInterest);
										currentDepositAccountInterestInfo.setNormalInterestRate(normalInterestRate);
										currentDepositAccountInterestInfo.setNegotiationInterestRate(0.00);

										normalInterestRate = currentDepositAccountInterestInfo.getNormalInterestRate();
										
										//����������Ϣ
										todayNormalInterest = currentDepositAccountInterestInfo.getNormalInterest();

										newInterest = UtilOperation.Arith.add(subInterest, todayNormalInterest);
										
										dailyAccountBalanceInfo.setInterest(newInterest);
										dailyAccountBalanceInfo.setAc_mNegotiateInterest(newNegotiateInterest);
										
										dailyAccountBalanceInfo.setDailyInterest(todayNormalInterest);
										
										dailyAccountBalanceInfo.setInterestRate(normalInterestRate);
										dailyAccountBalanceInfo.setInterestBalance(currentDepositAccountInterestInfo.getNormalBalance());//��Ϣ���
										
										dailyAccountBalanceDAO.update(dailyAccountBalanceInfo);

										//�������˻�
										subAccountFixedInfo.setInterest(UtilOperation.Arith.add(subInterest, todayNormalInterest));											
										
										sett_SubAccountDAO.updateSubAccountFix(subAccountFixedInfo);
										log.info("������֤����ÿ����Ϣ.....");
									}
									else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN || 
										accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										long loanNoteID = subAccountLoanInfo.getLoanNoteID(); //�ſ��
										double loanBalance = subAccountLoanInfo.getBalance(); //�������
										double loanInterest = subAccountLoanInfo.getInterest(); //���ǰ��Ϣ
										double loanCompoundInterest = subAccountLoanInfo.getCompoundInterest(); //���ǰ����
										double loanOverDueInterest = subAccountLoanInfo.getOverDueInterest(); //���ǰ��Ϣ
										double loanArrearageInterest = subAccountLoanInfo.getArrearageInterest();//���ǰǷϢ
										double newInterest = 0.0;
										double newCompoundInterest = 0.0;
										double newOverDueInterest = 0.0;
										InterestRate interestRateInfo = null;

										
										interestRateInfo = interestCalculation.getInterestRateByPayForm(loanNoteID, closeDate);
										double tmpRate = interestRateInfo.getRate();
										long tmpType = interestRateInfo.getType();
										//���մ�����Ϣ
										double todayLoanInterest = 0.0;
										todayLoanInterest = interestCalculation.calculateLoanAccountInterest(currencyID, tmpRate, tmpType, loanBalance, closeDate, closeDateAddOne);
										//����ǷϢ���
										double todayArrearageInterest = 0.0;
										todayArrearageInterest = subAccountLoanInfo.getArrearageInterest();
										//�����˻�����ս��

										newInterest = UtilOperation.Arith.add(loanInterest, todayLoanInterest);
										//System.out.println("lhj debug info ==���˻�[" + subAccountID + "]==���µĴ�����Ϣ�ǣ�" + newInterest);
										//System.out.println("lhj debug info ==��ԭ�д�����Ϣ " + newInterest + "��������������������Ϣ " + todayLoanInterest + "���ã�");
										//System.out.println("������Ϣ��������ʼ�����˻�����ս��....");
										//��������Ϣ
										dailyAccountBalanceInfo.setInterest(newInterest);
										//���մ�����Ϣ
										dailyAccountBalanceInfo.setDailyInterest(todayLoanInterest);
										//�ۼ�ǷϢ���
										dailyAccountBalanceInfo.setAl_mArrearageInterest(todayArrearageInterest);
										//��������
										dailyAccountBalanceInfo.setInterestRate(tmpRate);
										
										//modify by leiyang3
										//2010/11/28
										//���ڼ�����Ӫ����ں�ķ�Ϣ
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
										{
											LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(loanNoteID);
											
											if(loanPayNoticeInfo == null){
												throw new IException("û���ҵ���Ч�ķſ��Ϣ");
											}
											
											Timestamp loanStartDate = loanPayNoticeInfo.getStart();
											Timestamp loanEndDate = loanPayNoticeInfo.getEnd();
											
											//-- �������㿪ʼ --
											if(loanPayNoticeInfo.getIsRemitCompoundInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�и������ԣ��ж��Ƿ���и�������
											{
												//�ػ�ʱ��Ҫ�жϵ����Ƿ�Ϊ��Ϣ�գ���������ǽ�Ϣ�յ��ǲ����ڽ�Ϣ����ʱ����Ҫ���¼��㸴����������Ϊ��Ϣ�յ���û�й黹�κ���Ϣ
												boolean boolClearDay = utilOperation.isClearInterestDay(dailyAccountBalanceInfo.getSubAccountID(), closeDate);
												if(boolClearDay)
												{
													loanArrearageInterest =UtilOperation.Arith.add( UtilOperation.Arith.add(subAccountLoanInfo.getInterest(),subAccountLoanInfo.getCompoundInterest()),subAccountLoanInfo.getOverDueInterest());
												}
												//��������
												InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
												paramInfo.setLoanNoteID(loanNoteID);
												paramInfo.setValidDate(closeDate);
												InterestRate compoundRate = interestCalculation.getCompoundInterestRate(paramInfo);
												
												//ÿ�ո�����Ϣ
												double todayCompoundInterset = interestCalculation.calculateLoanAccountInterest(currencyID, compoundRate.getRate(), compoundRate.getType(), loanArrearageInterest, closeDate, closeDateAddOne);
												//�������ۼƸ�����Ϣ
												newCompoundInterest = UtilOperation.Arith.add(loanCompoundInterest, todayCompoundInterset);
												
												//����ÿ�ո�����Ϣ
												dailyAccountBalanceInfo.setMcompounddailyinterset(todayCompoundInterset);
												//�ۼƸ�����Ϣ
												dailyAccountBalanceInfo.setMcompoundinterest(newCompoundInterest);
												//����ǷϢ
												dailyAccountBalanceInfo.setAl_mArrearageInterest(loanArrearageInterest);
												
											}
											//-- ����������� --

											//-- ��Ϣ���㿪ʼ  --
											//�Ƿ���з�Ϣ
											if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //ͨ����ͬ�Ƿ�ʱ�з�Ϣ���ԣ��ж��Ƿ���з�Ϣ
											{
												if(!loanEndDate.after(closeDate))
												{
													InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
													paramInfo.setLoanNoteID(loanNoteID);
													paramInfo.setValidDate(closeDate);
													
													InterestRate overDueRate = interestCalculation.getOverDueInterestRate(paramInfo);
													
													//ÿ�շ�Ϣ�ܽ�����δ�����
													double overDueAccumulateBalance = loanBalance;
													//ÿ�շ�Ϣ��Ϣ
													double todayOverDueInterset = interestCalculation.calculateLoanAccountInterest(currencyID, overDueRate.getRate(), overDueRate.getType(), overDueAccumulateBalance, closeDate, closeDateAddOne);
													//�ۼƷ�Ϣ��Ϣ
													newOverDueInterest = UtilOperation.Arith.add(loanOverDueInterest, todayOverDueInterset);
													
													//����ÿ�շ�Ϣ��Ϣ
													dailyAccountBalanceInfo.setMforfeitdailyinterset(todayOverDueInterset);
													//�ۼƷ�Ϣ��Ϣ
													dailyAccountBalanceInfo.setMforfeitinterest(newOverDueInterest);
													//���շ�Ϣ����	     
													dailyAccountBalanceInfo.setMForfeitInterestRate(overDueRate.getRate());
													//�������õ���������������
													dailyAccountBalanceInfo.setDailyInterest(0.0);
													//���������ۼƴ�����Ϣ
													newInterest = UtilOperation.Arith.sub(newInterest, todayLoanInterest);
													dailyAccountBalanceInfo.setInterest(newInterest);
												}
											}
													
											//-- ��Ϣ�������  --
										}
												
										
//										//�жϸô����˻��Ƿ��Ѿ����ڣ�������������������
//										OverDueInfo overDueInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);
//										if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
//										{
//											/*
//											 *************************************************************
//											 * ����û�������,ֹͣ���������Ϣ��������ת�����㷣Ϣ��
//											 *************************************************************
//											 */
//											//��Ϣ�������Ϊ��
//											dailyAccountBalanceInfo.setInterestBalance(0.0);
//											//������ϢΪ��
//											dailyAccountBalanceInfo.setDailyInterest(0.0);
//											//�ۼ���Ϣ
//											dailyAccountBalanceInfo.setInterest(loanInterest);
//											newInterest = loanInterest;
//											//�ۼ�ǷϢΪ�㣬���ټ��㸴��
//											dailyAccountBalanceInfo.setAl_mArrearageInterest(0.0);
//											//���˻�����ǷϢ
//											double overDueArrearageInterest = subAccountLoanInfo.getOverDueArrearageInterest();
//											double overDueAmount = 0.0;
//											overDueAmount = UtilOperation.Arith.add(loanBalance, overDueArrearageInterest);
//											//
//											log.info("$$$$$$$$$$$$���ڴ���ÿ����Ϣ���������˻���" + subAccountID);
//											log.info("$$$$$$$$$$$$���ڴ���ÿ����Ϣ�������  ��" + loanBalance);
//											log.info("$$$$$$$$$$$$���ڴ���ÿ����Ϣ������ǷϢ  ��" + overDueArrearageInterest);
//											log.info("$$$$$$$$$$$$���ڴ���ÿ����Ϣ�����ڽ��  ��" + overDueAmount);
//											log.info("---------------------------------------------------------");
//											//���ڽ��
//											dailyAccountBalanceInfo.setAl_mOverDueAmount(overDueAmount);											
//												System.out.println("�й��������㷣Ϣ��ʼ----");
//												
//												DailyAccountBalanceInfo accountBalanceInfo = null;
//												try
//												{
//													accountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountID, officeID, currencyID, getNextNDay(closeDate, -1));
//												}
//												catch (SQLException e)
//												{
//													e.printStackTrace();
//													throw new IException("Gen_E001");
//												}
//												
//												//���Ϣ�ܺ�
//												double sumOverdueInterest = 0.0;
//												sumOverdueInterest = accountBalanceInfo.getMforfeitinterest();
//												
//												//ÿ�մ��Ϣ
//												double overdueInterest = 0.0;
//												overdueInterest = 
//													UtilOperation.Arith.div(
//														UtilOperation.Arith.mul(loanBalance, overDueInfo.getFineInterestRate()), 36000);
//												
//												sumOverdueInterest = UtilOperation.Arith.add(sumOverdueInterest, overdueInterest);
//												
//												dailyAccountBalanceInfo.setMforfeitdailyinterset(overdueInterest);
//												dailyAccountBalanceInfo.setMforfeitinterest(sumOverdueInterest);
//												
//												System.out.println("�й��������㷣Ϣ����----");
//												
//												//�Ƿ���㸴��
//												if(overDueInfo.getIsCompoundInterest() == SETTConstant.BooleanValue.ISTRUE)
//												{
//													System.out.println("�й��������㸴����ʼ----");
//													//������ܺ�
//													double sumCompoundinterest = 0.0;
//													sumCompoundinterest = accountBalanceInfo.getMcompoundinterest();
//													
//													//ÿ�ո���
//													double compoundinterest = 0.0;
//													compoundinterest = 
//														UtilOperation.Arith.div(
//															UtilOperation.Arith.mul(loanInterest, overDueInfo.getFineInterestRate()), 36000);
//													
//													sumCompoundinterest = UtilOperation.Arith.add(sumCompoundinterest, compoundinterest);
//												
//													dailyAccountBalanceInfo.setMcompounddailyinterset(compoundinterest);
//													dailyAccountBalanceInfo.setMcompoundinterest(sumCompoundinterest);
//													System.out.println("�й��������㸴������----");
//												}
//											//}
//										}
		
										dailyAccountBalanceDAO.update(dailyAccountBalanceInfo);

										//�������˻���
										subAccountLoanInfo.setInterest(newInterest);
										subAccountLoanInfo.setCompoundInterest(newCompoundInterest);
										subAccountLoanInfo.setOverDueInterest(newOverDueInterest);
										subAccountLoanInfo.setArrearageInterest(loanArrearageInterest);
										sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
									}	
								} //���˻���Ϊ��	
							} //���ڴ���˻��������˻�������ÿ�ռ�Ϣ
						}//�˻����Ͳ�Ϊ��	
					} //���˻���Ϊ��
				} //ѭ������
				System.out.println(closeDate.toString() + "�յ���Ϣȫ�����������÷���ֵΪ1");
				lReturn = 1;
			} //�˻�����ս���ϲ�Ϊ�գ�
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new IException(true, "�޷������˻������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lReturn;

	}

	/**
	 * Copy from  Huangye��s InterestCalculation
	 * 2003-11-14
	 * ȥ��ǰ���ڵĺ��棨ǰ�棩N�������
	 * @param CurrentDay ��ǰ����
	 * @param n>0 means get furture day, else means get past day
	 * @return nextDay
	*/
	private Timestamp getNextNDay(Timestamp currentDay, int n)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDay);
		calendar.add(Calendar.DATE, n);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());
	}
	
	/**
	 * �жϱ�֤�����Ƿ���
	 * ���ݴ���ı�֤�����˻������˻���
	 * �жϸ����˻���Ӧ�ı�֤���������Ƿ��Ѿ�����
	 * 
	 * @param SubAccountCurrentInfo
	 * @return boolean
	 * @throws Exception
	 */
	private boolean isMarginDepositAtTerm(SubAccountFixedInfo info) throws Exception
	{
		boolean flag = false;
		
		
		return flag;
	}

	/**
	 * �ӿ�����:��Ϣ�յ����
	 * ����˵��:���˻�������ϸ��,��ִ�����ǽ������Ϣ�����ڽ���Ľ��׼�¼��Ӧ���˻�,�Ӽ�Ϣ�յ�ִ���յ�ǰһ��,���¼�����ÿһ�յļ���������Ϣ.
	 *          �����˻�����ս��.
	 * @param conn ���ݿ�����,���ⲿ�����ߴ��������,����Ϊ��!
	 * @param lOfficeID  ���´�
	 * @param lCurrencyID ���� 
	 * @param closeDate �ػ�����
	 * @return long -1:���ɹ�  1:�ɹ�
	 * @throws IException
	 * 
	 */

	public long interestStartDateBackward(Connection conn, long lOfficeID, long lCurrencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		//ȡ����Ҫ�����Ľ����˻��Լ����������transAccountDetailCollection
		this.conn_Out = conn;
		Collection transAccountDetailCollection = null;
		sett_TransAccountDetailDAO sett_TransAccountDetailDAO = new sett_TransAccountDetailDAO(conn);
		try
		{
			transAccountDetailCollection = sett_TransAccountDetailDAO.findByCloseDate(lOfficeID, lCurrencyID, closeDate);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		if (transAccountDetailCollection != null && transAccountDetailCollection.size() > 0)
		{
			int sum = transAccountDetailCollection.size();
			//log.print("����ѯ����Ҫ����Ľ����˻�������: " + transAccountDetailCollection.size());
			Iterator transAccountDetailIterator = transAccountDetailCollection.iterator();
			TransAccountDetailInfo transAccountDetailInfo = null;
			int i = 0; //������,������.hjliu 2003-11-20
			while (transAccountDetailIterator.hasNext())
			{
				transAccountDetailInfo = (TransAccountDetailInfo) transAccountDetailIterator.next();

				long tmpTransAccountID = -1; //�����˻�
				long tmpSubAccountID = -1; //�������˻�          
				Timestamp interestStartDate = null; //��Ϣ��

				double backAmount = 0.0;
				//������,�����ݿ���㷨��:sum(decode(nTransDirection,1,-mAmount,2,mAmount))

				tmpTransAccountID = transAccountDetailInfo.getTransAccountID();
				tmpSubAccountID = transAccountDetailInfo.getTransSubAccountID();
				interestStartDate = transAccountDetailInfo.getDtInterestStart();
				backAmount = transAccountDetailInfo.getAmount();
				long isCloseSystem = SETTConstant.BooleanValue.ISTRUE;
				//				log.info("lhj debug info =======tmpTransAccountID�ǣ�" + tmpTransAccountID);
				//				log.info("lhj debug info =======tmpSubAccountID�ǣ�" + tmpSubAccountID);
				//				log.info("lhj debug info =======interestStartDate�ǣ�" + interestStartDate);
				//				log.info("lhj debug info =======backAmount��" + backAmount);
				//���õ�����Ϣ�����				
				if (accountInterestCalculationBackward(tmpTransAccountID, tmpSubAccountID, interestStartDate, backAmount, lOfficeID, lCurrencyID, closeDate, isCloseSystem) == 1)
				{
					i++;

				}
			}
			//			log.print("ѭ������----------------------------------------");
			//			log.print("�ܼƵ����ɹ����˻�������: " + i);
			lReturn = 1;
		}
		return lReturn;
	}

	/**
	 * �ӿ�����:����������ʵ����
	 * ����˵��:���������ʸı䣬������Ч�����ڵ�ǰϵͳ����ʱ����á����������еĴ�
	 * @param officeID
	 * @param currencyID
	 * @param closeDate
	 * @return
	 */
	public long depositBankInterestBackward(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		Collection depositBankInterestCollection = null;

		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		try
		{
			depositBankInterestCollection = sett_SubAccountDAO.findAllDepositBankInterestAdjust(officeID, currencyID, closeDate);

			if (depositBankInterestCollection != null && depositBankInterestCollection.size() > 0)
			{
				Iterator depositBankInterestIterator = depositBankInterestCollection.iterator();
				while (depositBankInterestIterator.hasNext())
				{
					BankInterestAdjustInfo depositBankInterestAdjustInfo = (BankInterestAdjustInfo) depositBankInterestIterator.next();
					long accountID = depositBankInterestAdjustInfo.getAccountID();
					long subAccountID = depositBankInterestAdjustInfo.getSubAccountID();
					Timestamp backDate = depositBankInterestAdjustInfo.getBackDate();
					//					log.info("����������ʵ���==��ǰ���˻�ID��" + subAccountID);
					//					log.info("����������ʵ���==��ǰ���˻�ID��" + accountID);
					//					log.info("����������ʵ���==�������ڣ�" + backDate);

					//���õ�����Ϣ����
					long interestReturnValue = this.accountInterestCalculationBackward(accountID, subAccountID, backDate, 0, officeID, currencyID, closeDate, SETTConstant.BooleanValue.ISTRUE);
					if (interestReturnValue != 1)
					{
						log.info("������ʵ�������ĵ�����Ϣ����ɹ������˻�ID��" + subAccountID);
						throw new IException(true, "������ʵ�����������Ϣ����ɹ�������ʧ��", null);
					}

				}
				lReturn = 1;

			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");

		}

		return lReturn;
	}
    
	/**
		 * �ӿ�����:�����������ʵ����
		 * ����˵��:�������������ʵ���ʱ�򣬲�����Ч�����ڵ�ǰϵͳ����ʱ����á����������еĴ��
		 * @param officeID
		 * @param currencyID
		 * @param closeDate
		 * @return
		 */
		public long loanBankInterestBackward(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
		{
			long lReturn = -1;
			Collection loanBankInterestCollection = null;

			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
			try
			{
				loanBankInterestCollection = sett_SubAccountDAO.findAllLoanBankInterestAdjust(officeID, currencyID, closeDate);

				if (loanBankInterestCollection != null && loanBankInterestCollection.size() > 0)
				{
					Iterator loanBankInterestIterator = loanBankInterestCollection.iterator();
					while (loanBankInterestIterator.hasNext())
					{
						BankInterestAdjustInfo loanBankInterestAdjustInfo = (BankInterestAdjustInfo) loanBankInterestIterator.next();
						long accountID = loanBankInterestAdjustInfo.getAccountID();
						long subAccountID = loanBankInterestAdjustInfo.getSubAccountID();
						Timestamp backDate = loanBankInterestAdjustInfo.getBackDate();
						long loanRateAdjustID = loanBankInterestAdjustInfo.getLoanRateAdjustPayDetailID();
						
						log.debug("�����������ʵ���==��ǰ���˻�ID��" + subAccountID);
						log.debug("�����������ʵ���==��ǰ���˻�ID��" + accountID);
						log.debug("�����������ʵ���==�������ʵ�����ϸ��Ϣ��ID��" + loanRateAdjustID);
						log.debug("�����������ʵ���==�������ڣ�" + backDate);

						//���õ�����Ϣ����
						long interestReturnValue = this.accountInterestCalculationBackward(accountID, subAccountID, backDate, 0, officeID, currencyID, closeDate, SETTConstant.BooleanValue.ISTRUE);
						if (interestReturnValue != 1)
						{
							log.info("�������ʵ�������ĵ�����Ϣ����ɹ������˻�ID��" + subAccountID);
							throw new IException(true, "�������ʵ�����������Ϣ����ɹ�������ʧ��", null);
						}else{
						   //������loanBankInterestAdjustInfo��Ӧ��LOAN_RATEADJUSTPAYDETAIL��NISCOUNTINTEREST�޸�Ϊ0
						   //��������ظ�����
						   long lUpdateReturn = sett_SubAccountDAO.updateLoanRateAdjustPayDetail(loanRateAdjustID);
						   log.debug("�������ʵ�����ϸ��Ϣ���ı�־lUpdateReturn = "+lUpdateReturn);
						}

					}
					lReturn = 1;

				}

			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");

			}

			return lReturn;
		}
		
		/**
		 * �ӿ�����:����Libor���ʵ����
		 * ����˵��:������Libor���ʵ���ʱ�򣬲�����Ч�����ڵ�ǰϵͳ����ʱ����á����������еĴ��
		 * @param officeID
		 * @param currencyID
		 * @param closeDate
		 * @return
		 */
		public long loanLiborInterestBackward(Connection conn, long officeID, long currencyID, Timestamp closeDate) throws IException
		{
			long lReturn = -1;
			Collection loanLiborInterestCollection = null;

			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
			try
			{
				loanLiborInterestCollection = sett_SubAccountDAO.findAllLoanLiborInterestAdjust(officeID, currencyID, closeDate);

				if (loanLiborInterestCollection != null && loanLiborInterestCollection.size() > 0)
				{
					Iterator loanLiborInterestIterator = loanLiborInterestCollection.iterator();
					while (loanLiborInterestIterator.hasNext())
					{
						LiborInterestAdjustInfo loanLiborInterestAdjustInfo = (LiborInterestAdjustInfo) loanLiborInterestIterator.next();
						long accountID = loanLiborInterestAdjustInfo.getAccountID();
						long subAccountID = loanLiborInterestAdjustInfo.getSubAccountID();
						Timestamp backDate = loanLiborInterestAdjustInfo.getBackDate();
						long loanRateAdjustID = loanLiborInterestAdjustInfo.getLoanLiborInformID();
						
						log.debug("����LIBOR���ʵ���==��ǰ���˻�ID��" + subAccountID);
						log.debug("����LIBOR���ʵ���==��ǰ���˻�ID��" + accountID);
						log.debug("����LIBOR���ʵ���==LIBOR���ʵ�����ϸ��Ϣ��ID��" + loanRateAdjustID);
						log.debug("����LIBOR���ʵ���==�������ڣ�" + backDate);

						//���õ�����Ϣ����
						long interestReturnValue = this.accountInterestCalculationBackward(accountID, subAccountID, backDate, 0, officeID, currencyID, closeDate, SETTConstant.BooleanValue.ISTRUE);
						if (interestReturnValue != 1)
						{
							log.info("����LIBOR���ʵ�������ĵ�����Ϣ����ɹ������˻�ID��" + subAccountID);
							throw new IException(true, "����LIBOR���ʵ�����������Ϣ����ɹ�������ʧ��", null);
						}else{
						   //������LiborInterestAdjustInfo��Ӧ��LOAN_LIBORINFORM��ISCOUNTINTEREST�޸�Ϊ0
						   //��������ظ�����
						   long lUpdateReturn = sett_SubAccountDAO.updateLoanLiborInform(loanRateAdjustID);
						   log.debug("����LIBOR���ʵ�����ϸ��Ϣ���ı�־lUpdateReturn = "+lUpdateReturn);
						}

					}
					lReturn = 1;

				}

			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");

			}

			return lReturn;
		}
	/**
	 * ���ܣ�����ſ�֪ͨ������ʱ����δ����ı���������Ϣ�������ȣ�����Ϊ���㷣Ϣ�Ļ���������Ϣ������ȡ��Ϣ��
	 * @param conn    ���ݿ����ӣ��ⲿ�����ߴ��롣
	 * @param lOfficeID ���´�
	 * @param lCurrencyID ����
	 * @param closeDate  ��ǰ�ػ�����
	 * @return lReturn ��1�����ɹ���1:�ɹ�
	 * @throws IException:
	 */
	public long loanAccountOverDue(Connection conn, long lOfficeID, long lCurrencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		Collection overDueLoanCollection = null;

		UtilOperation utilOperation = new UtilOperation(conn);
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		InterestCalculation interestCalculation = new InterestCalculation(conn);
		Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		try
		{
			overDueLoanCollection = utilOperation.findAllOverDueLoanAccount(lOfficeID, lCurrencyID, closeDate);

			//
			if (overDueLoanCollection != null && overDueLoanCollection.size() > 0)
			{
				Iterator overDueLoanIterator = overDueLoanCollection.iterator();
				while (overDueLoanIterator.hasNext())
				{
					OverDueInfo overDueLoan = (OverDueInfo) overDueLoanIterator.next();

					Timestamp fineDate = overDueLoan.getFineDate();
					long loanNoteID = overDueLoan.getLoanPayID();
					SubAccountAssemblerInfo subAccount = sett_SubAccountDAO.findByLoanNoteID(0, loanNoteID);
					SubAccountLoanInfo subLoanAccount = subAccount.getSubAccountLoanInfo();
					//���ڴ���Ƿ�Э������־��������������ʱ�����Ƿ��������ڴ���
					SubAccountCurrentInfo subCurrentAccount = subAccount.getSubAccountCurrenctInfo();
					//���˻�ID,�����˻�ID
					long accountID = subLoanAccount.getAccountID();
					long subAccountID = subLoanAccount.getID();
					//���
					double loanBalance = 0.0;
					loanBalance = subLoanAccount.getBalance();
					//��������˻�������δ����Ϣ
					LoanAccountInterestInfo loanInterestInfo = interestCalculation.getLoanAccountInterest(conn, lOfficeID, lCurrencyID, accountID, subAccountID, fineDate, closeDate);
					double loanInterest = loanInterestInfo.getInterest();
					//���˻���������Ϣ�޸�Ϊ�����յ�������Ϣ�����ٸı䣡2004-02-23 ���ݾ�
					subLoanAccount.setInterest(loanInterest);

					//��������˻�������δ������
					LoanAccountInterestInfo LoanCompoundInterestInfo =
						interestCalculation.getLoanAccountFee(conn, lOfficeID, lCurrencyID, accountID, subAccountID, fineDate, closeDate, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					double loanCompoundInterest = LoanCompoundInterestInfo.getInterest();

					//ֹͣ���㸴��
					subLoanAccount.setArrearageInterest(0);
					//��ʼ����Ϣ��Ϣ�գ������������󣬼��㷣Ϣʱ�Ͳ�����Ҫ�����Ƿ��һ�η���
					subLoanAccount.setClearOverDueDate(fineDate);
					//�����˻�����ǷϢ
					double overDueArrearageInterest = 0.0;
					overDueArrearageInterest = UtilOperation.Arith.add(UtilOperation.Arith.round(loanInterest, 2), UtilOperation.Arith.round(loanCompoundInterest, 2));
					subLoanAccount.setOverDueArrearageInterest(overDueArrearageInterest);

					log.info("$$$$$$$$$$$$���ڴ�������˻�ID��" + accountID);
					log.info("$$$$$$$$$$$$���ڴ�������˻�ID��" + subAccountID);
					log.info("$$$$$$$$$$$$���ڴ����������Ϣ��" + loanInterest);
					log.info("$$$$$$$$$$$$���ڴ�����������" + loanCompoundInterest);
					log.info("$$$$$$$$$$$$���ڴ��������ǷϢ��" + overDueArrearageInterest);
					log.info("$$$$$$$$$$$$���ڴ���������" + loanBalance);

					//�������˻�
					/*
					 * �����˻��е��Ƿ�Э�����AC_nIsNegotiate�����ж��Ƿ��Ѿ��������ڴ���
					 * ����Ѿ��������ڣ��򽫸��ֶ�����Ϊ��9
					 * ��ֹ�ظ����ڴ���
					 * 2004-02-04
					 */
					subCurrentAccount.setIsNegotiate(LOAN_ACCOUNT_HASOVERDUED);
					sett_SubAccountDAO.updateSubAccountCurrent(subCurrentAccount);
					//�������˻�
					sett_SubAccountDAO.updateSubAccountLoan(subLoanAccount);
					double overDueAmount = 0.0;

					//���¸����˻����˻������¼
					if (fineDate.before(closeDate))
						overDueAmount = UtilOperation.Arith.add(overDueArrearageInterest, loanBalance);
					log.info("$$$$$$$$$$$$���ڴ�������ڽ�" + overDueAmount);
					sett_DailyAccountBalanceDAO.updateOverDueArrearageAmount(subAccountID, overDueAmount, loanInterest, fineDate);

				}
				lReturn = 1;

			}

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return lReturn;
	}
	
	/**
	 * �ӿ�����:������Ϣ�����
	 * ����˵��:�й�����������ڴ���ӿ� Boxu 2008-10-06
	 * @param conn ���ݿ�����,���ⲿ�����ߴ��������,����Ϊ��
	 * @param lOfficeID  ���´�
	 * @param lCurrencyID ���� 
	 * @param closeDate �ػ�����
	 * @return long -1:���ɹ�  1:�ɹ�
	 * @throws IException
	 */
	public long interestGuoDianStartDateBackward(Connection conn, long lOfficeID, long lCurrencyID, Timestamp closeDate) throws IException
	{
		long lReturn = -1;
		//ȡ����Ҫ�����Ľ����˻��Լ����������transAccountDetailCollection
		this.conn_Out = conn;
		Collection transAccountDetailCollection = null;
		sett_TransAccountDetailDAO sett_TransAccountDetailDAO = new sett_TransAccountDetailDAO(conn);
		
		try
		{
			transAccountDetailCollection = sett_TransAccountDetailDAO.findGuoDianByCloseDate(lOfficeID, lCurrencyID, closeDate);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		if (transAccountDetailCollection != null && transAccountDetailCollection.size() > 0)
		{
			Iterator transAccountDetailIterator = transAccountDetailCollection.iterator();
			TransAccountDetailInfo transAccountDetailInfo = null;
			
			int i = 0;  //������,������.hjliu 2003-11-20
			
			while (transAccountDetailIterator.hasNext())
			{
				transAccountDetailInfo = (TransAccountDetailInfo) transAccountDetailIterator.next();

				long tmpTransAccountID = -1;  			//�����˻�
				long tmpSubAccountID = -1;  			//�������˻�          
				Timestamp interestStartDate = null;  	//��Ϣ��
				double backAmount = 0.0;  				//������,��ÿ����Ϣ�����л���˻����
				long iscompound = -1;					//�Ƿ���㸴��
				double dRate = 0.0;						//��Ϣ����
				
				tmpTransAccountID = transAccountDetailInfo.getTransAccountID();
				tmpSubAccountID = transAccountDetailInfo.getTransSubAccountID();
				interestStartDate = transAccountDetailInfo.getDtInterestStart();
				iscompound = transAccountDetailInfo.getTransDirection();
				dRate = transAccountDetailInfo.getRate();
				//backAmount = transAccountDetailInfo.getAmount();
				long isCloseSystem = SETTConstant.BooleanValue.ISTRUE;
				
				//���õ�����Ϣ�����
				if (accountGuoDianInterestCalculationBackward(tmpTransAccountID, tmpSubAccountID, interestStartDate, backAmount, lOfficeID, lCurrencyID, closeDate, isCloseSystem, iscompound, dRate) == 1)
				{
					i++;
				}
			}
			
			lReturn = 1;
		}
		
		return lReturn;
	}
	
	/**
	* �ӿ�����:������Ϣ�����
	* ����˵��:�й�����������ڴ���ӿ� Boxu 2008-10-06
	* @param AccountID    ���˻�ID
	* @param SubAccountID ���˻�ID
	* @param BackDate     �������Ϣ��
	* @param BackAmount   ����׽��
	* @param ExecuteDate  ִ����/�ػ�����
	* @param isCloseSystem �Ƿ�ػ�����
	* @return long -1:���ɹ���1���ɹ�
	* @throws IException
	*/
	public long accountGuoDianInterestCalculationBackward(
		long AccountID,
		long SubAccountID,
		Timestamp BackDate,
		double BackAmount,
		long lOfficeID,
		long lCurrencyID,
		Timestamp ExecuteDate,
		long isCloseSystem,
		long iscompound,
		double dRate)
		throws IException
	{
		long isSuccess = -1;  //�ɹ���־
		Connection conn = this.conn_Out;
		double executeDateLoanInterest = 0.0;  //�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ�����Ϣ��
		
		//����AccountID��sett_account���ж�λ���˻���¼
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
		AccountInfo accountInfo = null;
		
		try
		{
			accountInfo = sett_AccountDAO.findByID(AccountID);
			if (accountInfo == null)
			{
				throw new IException(true, "���˻�����û�ж�Ӧ��¼������ʧ��", null);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		log.info("������Ϣ������ȡ���˻��ɹ���");
		
		//����SubAccountID��sett_SubAccount���ж�λ���˻���¼
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		
		try
		{
			log.info("������Ϣ������ȡ���˻�....");
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(SubAccountID);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
			throw new IException(true, "�޷��������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}
		
		log.info("������Ϣ������ȡ���˻��ɹ���");
		//���˻��еĴ�����Ϣ
		SubAccountLoanInfo subAccountLoanInfo = null;
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		
		if (subAccountAssemblerInfo != null)
		{
			//�������˻�------------------------------------------------------------------
			subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();
			//�������˻��ĵ�ǰ��Ϣ�����ڹػ���Ϣ��Ĵ�����Ϣ��
			executeDateLoanInterest = subAccountLoanInfo.getInterest();
			//�������˻�------------------------------------------------------------------
			
			//�������˻�------------------------------------------------------------------	
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();
			//�������˻�------------------------------------------------------------------
		}
		else
		{
			throw new IException(true, "�޷������˻������ҵ���Ӧ����Ϣ������ʧ��", null);
		}

		if (accountInfo != null)
		{
			long accountTypeID = accountInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (accountTypeInfo != null) {
				//���ӿڶԶ��ڴ��������˻������м�Ϣ
				if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.FIXED && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.NOTIFY && 
					accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.DISCOUNT)
				{
					//����ѭ������
					InterestCalculation interestCalculation = new InterestCalculation(conn);
					int loopDays = (int) interestCalculation.getIntervalDays(BackDate, ExecuteDate, 1);
					//log.info("ѭ�������� " + loopDays);
					
					Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
					for (int i = 0; i < loopDays; i++)
					{
						//��Ϣ���
						double newInterestBalance = 0.0;
						//��ǰ��Ϣ����
						Timestamp tmpBackDate = getNextNDay(BackDate, i);
						//��ǰ��Ϣ����+1
						//Timestamp tmpBackDateAddOne = getNextNDay(tmpBackDate, 1);
	
						//�������˻�AccountID,���˻�SubAccountID,��Ϣ����tmpBackDate���˻�����ս��-�в�ѯ��ؼ�¼��
						DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
						
						try
						{
							dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, tmpBackDate);
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
							throw new IException("Gen_E001");
						}
						
						long dabi_AccountID = -1;
						if (dailyAccountBalanceInfo != null)
						{
							dabi_AccountID = dailyAccountBalanceInfo.getAccountID();
						}
						
						if (dailyAccountBalanceInfo != null && dabi_AccountID > 0)
						{
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								System.out.println("�й��������㷣Ϣ��ʼ----");
								//��Ϣ���
								if(dailyAccountBalanceInfo.getInterestBalance() > 0) {
									newInterestBalance = dailyAccountBalanceInfo.getInterestBalance();
								} else {
									//ϵͳ�������ڴ���,���ڼ���������Ϣ					
									newInterestBalance = dailyAccountBalanceInfo.getBalance();
									
								}
								System.out.println("�й��������㷣Ϣ��ʼ----00000000000��������������������"+ getNextNDay(tmpBackDate, -1));
								//���Ϣ�ܺ�
								DailyAccountBalanceInfo accountBalanceInfo = new DailyAccountBalanceInfo();
								try
								{
									accountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, lOfficeID, lCurrencyID, getNextNDay(tmpBackDate, -1));
									System.out.println("�й��������㷣Ϣ��ʼ----00000000000��������������������"+ accountBalanceInfo);
								}
								catch (SQLException e)
								{
									e.printStackTrace();
									throw new IException("Gen_E001");
								}
								if(accountBalanceInfo!=null){
									double sumOverdueInterest = 0.0;
									sumOverdueInterest = accountBalanceInfo.getMforfeitinterest();
									
									//ÿ�մ��Ϣ
									double overdueInterest = 0.0;
									overdueInterest = 
										UtilOperation.Arith.div(
											UtilOperation.Arith.mul(newInterestBalance, dRate), 36000);
									
									sumOverdueInterest = UtilOperation.Arith.add(sumOverdueInterest, overdueInterest);
									
									dailyAccountBalanceInfo.setMforfeitdailyinterset(overdueInterest);
									dailyAccountBalanceInfo.setMforfeitinterest(sumOverdueInterest);
									
									System.out.println("�й��������㷣Ϣ����----");	
									
									//�Ƿ���㸴��
									if(iscompound == SETTConstant.BooleanValue.ISTRUE)
									{
										System.out.println("�й��������㸴����ʼ----");
										//������ܺ�
										double sumCompoundinterest = 0.0;
										sumCompoundinterest = accountBalanceInfo.getMcompoundinterest();
										
										//ÿ�ո���
										double compoundinterest = 0.0;
										compoundinterest = 
											UtilOperation.Arith.div(
												UtilOperation.Arith.mul(accountBalanceInfo.getInterest(), dRate), 36000);
										sumCompoundinterest = UtilOperation.Arith.add(sumCompoundinterest, compoundinterest);
									
										dailyAccountBalanceInfo.setMcompounddailyinterset(compoundinterest);
										dailyAccountBalanceInfo.setMcompoundinterest(sumCompoundinterest);
										System.out.println("�й��������㸴������----");	
									}
								}
								
								
							}
							//Ϊ�˻�����ս���и���һ���¼�¼
							try
							{
								sett_DailyAccountBalanceDAO.update(dailyAccountBalanceInfo);
							}
							catch (Exception e4)
							{
								e4.printStackTrace();
								throw new IException("����ʧ�ܣ������ԣ�");
							}
						}
					}

					//�������˻���
					try
					{
						System.out.println("��ʼ�������˻���........");
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							//log.info("��ʼ�������˻����д�����Ϣ........");
							//�������˻�
							/*
							 * �����˻��е��Ƿ�Э�����AC_nIsNegotiate�����ж��Ƿ��Ѿ��������ڴ���
							 * ����Ѿ��������ڣ��򽫸��ֶ�����Ϊ��9
							 * ��ֹ�ظ����ڴ���
							 */
							subAccountCurrentInfo.setIsNegotiate(LOAN_ACCOUNT_HASOVERDUED);
							sett_SubAccountDAO.updateSubAccountCurrent(subAccountCurrentInfo);
							
							subAccountLoanInfo.setClearOverDueDate(BackDate);		//���·�Ϣ����
							
							if(iscompound == SETTConstant.BooleanValue.ISTRUE)
							{
								subAccountLoanInfo.setClearCompoundDate(BackDate);	//���¸�������
							}

							//�������˻��еĴ���
							sett_SubAccountDAO.updateSubAccountLoan(subAccountLoanInfo);
						}
						System.out.println("�����������˻���........");
						//����ɹ�
						isSuccess = 1;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						throw new IException("Gen_E001");
					}
				}
			}
		}
		
		return isSuccess;
	}
	
}