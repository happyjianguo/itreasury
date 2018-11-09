package com.iss.itreasury.ebank.obinterest.bizlogic;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.ejb.*;
import com.iss.itreasury.ebank.obinterest.dao.OBInterestDao;
import com.iss.itreasury.settlement.interest.dao.Sett_LoanNoticeDAO;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.settlement.query.queryobj.QLoanNotice;
public class OBInterestEJB implements SessionBean
{
	private static Log4j log4j = null;
	SessionContext sessionContext;
	public OBInterestEJB()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	public void ejbCreate()
	{
	}
	public void ejbRemove()
	{
	}
	public void ejbActivate()
	{
	}
	public void ejbPassivate()
	{
	}
	public void setSessionContext(SessionContext sessionContext)
	{
		this.sessionContext = sessionContext;
	}
	/**
	 * Method disPatchLoanNotice.
	 * ����֪ͨ�����ͣ����зַ�
	 * ����Ǽ��Ƚ�Ϣ���ѣ���Ӧ����Ϣ֪ͨ��
	 * ���ȸ��ݺ�ͬ���ҳ��ú�ͬ�µķſ�֪ͨ��
	 * �ڵ���findLoanNotice����
	 * ����ֱ�ӵ���
	 * @param info
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @throws IException
	 */
	public void dispatchLoanNotice(LoanNoticeInfo info) throws IRollbackException, RemoteException, IException
	{
		OBInterestDao obInterestDao = new OBInterestDao();
		try
		{
			if (info.getFormTypeID() == SETTConstant.LoanNoticeType.LoanInterestNotice)
			{
				long[] tempLong = obInterestDao.findPayFormByContractID(info.getContractID());
				if (tempLong.length == 1 && tempLong[0] == -1)
				{
					throw new IException("�ú�ͬ�����޷ſ�֪ͨ����");
				}
				else
				{
					for (int i = 0; i < tempLong.length; i++)
					{
						info.setLoanNoteID(tempLong[i]);
						findLoanNotice(info);
					}
				}
			}
			else
			{
				findLoanNotice(info);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Method findLoanNotice.
	 * ���ȸ���Info�д���ķſ�֪ͨ��id�Լ�֪ͨ�����ͣ�
	 * ��ѯ��sett_LoanNotice�Ƿ��Ѿ�����ָ��֪ͨ�����͵�֪ͨ��
	 * ����Ѿ������򷵻ز�ѯ�����
	 * ������������ڱ�sett_LoanNotice�в���һ�����ݿ��¼
	 * @param Info
	 * @return Vector
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void findLoanNotice(LoanNoticeInfo info) throws IRollbackException, RemoteException, IException
	{
		Vector vctResult = null;
		OBInterestDao obInterestDao = new OBInterestDao();
		LoanNoticeInfo loanNoticeInfo = null;
		String strAccountNo = "";
		long lAccountType = -1;
		String strTemp = "";
		try
		{
			log4j.info("====findLoanNotice start====");
			vctResult = obInterestDao.queryLoanNotice(info);
			if (vctResult != null && vctResult.size() == 1)
			{
				loanNoticeInfo = (LoanNoticeInfo) vctResult.elementAt(0);
				if (loanNoticeInfo.getID() < 0)
				{
					log4j.info("====the loannotices are not existed!====");
					//��ʼ����������ѯ������֪ͨ����Ϣ�����б���
					//�����ͬ��
					log4j.info("��ͬ�ţ�" + info.getContractID());
					info.setContractNo(NameRef.getContractNoByID(info.getContractID()));
					//�ſ�֪ͨ����
					info.setPayFormNo(NameRef.getPayFormNoByID(info.getLoanNoteID()));
					log4j.info("�ſ�֪ͨ���ţ�" + info.getPayFormNo());
					//�˻�����
					strAccountNo = NameRef.getAccountNoByID(info.getAccountID());
					lAccountType = NameRef.getAccountTypeByID(info.getAccountID());
					if (lAccountType > 0)
					{
						if (SETTConstant.AccountType.isTrustAccountType(lAccountType)
								|| SETTConstant.AccountType.isConsignAccountType(lAccountType))
						{
							info.setAccountTypeID(lAccountType);
						}
					}
					//��Ϣ�գ��Ѿ���dao�ķ��ؽ����������
					log4j.info("====query contractinfo start====");
					ContractDao contractDao = new ContractDao();
					ContractInfo contractInfo = contractDao.findByID(info.getContractID());
					if (contractInfo != null)
					{
						//���������
						info.setBorrowClientName(NameRef.getClientNameByID(contractInfo.getBorrowClientID()));
						log4j.info("��������ƣ�" + info.getBorrowClientName());
						//���´�
						info.setOfficeID(contractInfo.getOfficeID());
						//����
						info.setCurrencyID(contractInfo.getCurrencyID());
						//������
						info.setLoanAmount(contractInfo.getLoanAmount());
						//������ʼ����
						info.setLoanStartDate(contractInfo.getLoanStart());
						//�����������
						info.setLoanEndDate(contractInfo.getLoanEnd());
						//��������
						info.setLoanTerm(contractInfo.getIntervalNum());
					}
					//���õ�����Ϣ����queryNoticeInterest����Ҫ�ĳ�ʼ����
					log4j.info("====set query interest condition start====");
					InterestEstimateQueryInfo interestEstimateQueryInfo = new InterestEstimateQueryInfo();
					interestEstimateQueryInfo.setCurrencyID(info.getCurrencyID());
					interestEstimateQueryInfo.setOfficeID(info.getOfficeID());
					interestEstimateQueryInfo.setClearInterestDate(info.getClearInterestDate());
					log4j.print("===֪ͨ������:" + info.getFormTypeID());
					interestEstimateQueryInfo.setNoticetype(info.getFormTypeID());
					log4j.info("====��Ϣ����:" + interestEstimateQueryInfo.getClearInterestDate());
					if (SETTConstant.AccountType.isTrustAccountType(info.getAccountTypeID()))
					{
						interestEstimateQueryInfo.setFeeType(2);
						log4j.info("�˻����ͣ���Ӫ");
					}
					if (SETTConstant.AccountType.isConsignAccountType(info.getAccountTypeID()))
					{
						interestEstimateQueryInfo.setFeeType(3);
						log4j.info("�˻����ͣ�ί��");
					}
					interestEstimateQueryInfo.setIsSelectInterest(1);
					interestEstimateQueryInfo.setIsSelectForfeitInterest(1);
					interestEstimateQueryInfo.setIsSelectCompoundInterest(1);
					//��������Ϣ�ӿ�
					log4j.info("====start query interest info====");
					QLoanNotice qLoanNotice = new QLoanNotice();
					Vector vctTemp = new Vector();
					LoanNoticeInfo copyInfo = new LoanNoticeInfo();
					//��ʼ��������
					copyInfo.setOfficeID(info.getOfficeID());
					copyInfo.setCurrencyID(info.getCurrencyID());
					copyInfo.setLoanNoteID(info.getLoanNoteID());
					copyInfo.setPayFormNo(info.getPayFormNo());
					copyInfo.setSubAccountID(info.getSubAccountID());
					copyInfo.setAccountID(info.getAccountID());
					copyInfo.setAccountTypeID(info.getAccountTypeID());
					copyInfo.setContractID(info.getContractID());
					copyInfo.setBorrowClientID(info.getBorrowClientID());
					copyInfo.setClearInterestDate(info.getClearInterestDate());
					copyInfo.setLoanAmount(info.getLoanAmount());
					copyInfo.setLoanStartDate(info.getLoanStartDate());
					copyInfo.setLoanEndDate(info.getLoanEndDate());
					copyInfo.setLoanTerm(info.getLoanTerm());
					copyInfo.setFormTypeID(info.getFormTypeID());
					vctTemp.add(copyInfo);
					Vector vctQuery = null;
					vctQuery = qLoanNotice.queryNoticeInterest(null, vctTemp, interestEstimateQueryInfo);
					if (vctQuery != null && vctQuery.size() > 0)
					{
						LoanNoticeInfo tempInfo = (LoanNoticeInfo) vctQuery.elementAt(0);
						if (tempInfo != null)
						{
							//�������
							info.setLoanBalance(tempInfo.getLoanBalance());
							//ԭ��ͬ����
							info.setOriginalContractRate(tempInfo.getOriginalContractRate());
							//�������ͬ����
							info.setNewExecuteRate(tempInfo.getNewExecuteRate());
							//ִ������
							info.setExecuteRate(tempInfo.getExecuteRate());
							//������ִ������
							info.setNewExecuteRate(tempInfo.getNewExecuteRate());
							//��������
							info.setExecuteRateValidDate(tempInfo.getExecuteRateValidDate());
							//֪ͨ�����
							info.setFormYear(tempInfo.getFormYear());
							//֪ͨ����
							info.setFormNo(tempInfo.getFormNo());
							log4j.info("֪ͨ����:" + info.getFormNo());
							//���մ���
							info.setFormNum(tempInfo.getFormNum());
							log4j.info("���մ���:" + info.getFormNum());
							//������Ϣ
							info.setInterest(tempInfo.getInterest());
							//������
							info.setSuretyFee(tempInfo.getSuretyFee());
							//����
							info.setCompoundInterest(tempInfo.getCompoundInterest());
							//��������
							info.setCommissionRate(tempInfo.getCommissionRate());
							//������
							info.setCommission(tempInfo.getCommission());
							//���ڷ�Ϣ����
							info.setOverDueInterestRate(tempInfo.getOverDueInterestRate());
							//���ڷ�Ϣ
							info.setOverDueInterest(tempInfo.getOverDueInterest());
							//������Ϣ����Ϣ��
							info.setInterestStartDate(tempInfo.getInterestStartDate());
							//*****�����ĸ��ֶ�û�д洢
							//�����ͻ�
							//������ͬ���ݷ�
							//��Ϣ�ϼ�
							//��Ϣ�ϼ�
							//*****�����ĸ��ֶ�û�д洢
						}
					}
					//��table sett_loannotice �в���һ����¼
					log4j.info("====start insert a loannotice record====");
					Sett_LoanNoticeDAO sett_LoanNoticeDAO = new Sett_LoanNoticeDAO();
					long lID = sett_LoanNoticeDAO.add(null, info);
					if (lID > 0)
					{
						log4j.info("lID:" + lID);
						log4j.info("===========sett_LoanNoticeDAO add success!===");
					}
					vctResult = new Vector();
					vctResult.add(info);
				}
			}
			else
			{
				log4j.info("===========the loannotices are already existed!===");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		try
		{	OBInterestEJB obInterestEJB = new OBInterestEJB();
			LoanNoticeInfo info = new LoanNoticeInfo();
			info.setContractID(390);
			info.setLoanNoteID(1546);
			info.setFormTypeID(1);
			obInterestEJB.dispatchLoanNotice(info);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}