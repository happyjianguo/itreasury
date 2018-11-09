/*
 * Created on 2003-11-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.account.dataentity.*;
import com.iss.itreasury.settlement.account.dao.*;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;

import com.iss.itreasury.util.Log4j;

import com.iss.itreasury.settlement.query.resultinfo.*;
import com.iss.itreasury.settlement.setting.dao.Sett_TaxRatePlanSettingItemDAO;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.*;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QSubAccountDao extends BaseQueryObject
{
	Log4j logger = null;
	public QSubAccountDao()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}

	//ͨ�����˻�ID��ѯ���ڴ���Ϣ
	public QuerySubFixedInfo findFixedInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		QuerySubFixedInfo fixInfo = null;
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			fixInfo = new QuerySubFixedInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{
				fixInfo.setID(subInfo.getSubAccountFixedInfo().getID());
				fixInfo.setAccountID(subInfo.getSubAccountFixedInfo().getAccountID());
				fixInfo.setAF_sDepositNo(subInfo.getSubAccountFixedInfo().getDepositNo());
				fixInfo.setAF_dtStart(subInfo.getSubAccountFixedInfo().getStartDate());
				fixInfo.setAF_nDepositTerm(subInfo.getSubAccountFixedInfo().getDepositTerm());
				fixInfo.setAF_dtEnd(subInfo.getSubAccountFixedInfo().getEndDate());
				fixInfo.setAF_mRate(subInfo.getSubAccountFixedInfo().getRate());
				fixInfo.setMOpenAmount(subInfo.getSubAccountFixedInfo().getOpenAmount());
				fixInfo.setMBalance(subInfo.getSubAccountFixedInfo().getBalance());
				fixInfo.setNStatusID(subInfo.getSubAccountFixedInfo().getStatusID());
				fixInfo.setDtOpen(subInfo.getSubAccountFixedInfo().getOpenDate());
				fixInfo.setDtFinish(subInfo.getSubAccountFixedInfo().getFinishDate());
				fixInfo.setAF_mPreDrawInterest(subInfo.getSubAccountFixedInfo().getPreDrawInterest());
				
				//��֤�������Ϣ
				fixInfo.setAL_mPreDrawInterest(subInfo.getSubAccountCurrenctInfo().getDrawInterest());
				
				fixInfo.setAF_mInterest(subInfo.getSubAccountFixedInfo().getInterest());
				fixInfo.setLPayFormID(subInfo.getSubAccountLoanInfo().getLoanNoteID()); //��������ר��
				
				fixInfo.setIsAutoContinue(subInfo.getSubAccountFixedInfo().getIsAutoContinue());//�Ƿ��Զ�����
				fixInfo.setAutoContinueType(subInfo.getSubAccountFixedInfo().getAutoContinueType());//�Զ����淽ʽ
				fixInfo.setAutoContinueAccountID(subInfo.getSubAccountFixedInfo().getInterestAccountID());//������Ϣ�˻�id�����淽ʽΪ��������ʱ��
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return fixInfo;

	}

	//ͨ�����˻�ID��ѯ֪ͨ�浥��Ϣ
	public QuerySubNoteInfo findNoteInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubNoteInfo info = null;
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubNoteInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{
				info.setID(subInfo.getSubAccountFixedInfo().getID());
				info.setAccountID(subInfo.getSubAccountFixedInfo().getAccountID());
				info.setAF_sDepositNo(subInfo.getSubAccountFixedInfo().getDepositNo());
				info.setAF_dtStart(subInfo.getSubAccountFixedInfo().getStartDate());
				info.setAF_nNoticeDay(subInfo.getSubAccountFixedInfo().getNoticeDay());
				info.setMOpenAmount(subInfo.getSubAccountFixedInfo().getOpenAmount());
				info.setMBalance(subInfo.getSubAccountFixedInfo().getBalance());
				info.setNStatusID(subInfo.getSubAccountFixedInfo().getStatusID());
				info.setDtOpen(subInfo.getSubAccountFixedInfo().getOpenDate());
				info.setDtFinish(subInfo.getSubAccountFixedInfo().getFinishDate());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}
	/**
	 * ����ۼƻ�Ϣ
	 * @param nSubAccountID
	 * @return
	 * @throws Exception
	 */
	public double getTotalRepayInterest(long nSubAccountID) throws Exception
	{
		double dInterest = 0.0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSQL = "";		
		try
		{
			con = this.getConnection();
			strSQL = "select sum(Interest) Interest from sett_vTransInterestDetail where subaccountID="+nSubAccountID;
			strSQL += " and InterestTypeID not in ("+SETTConstant.InterestFeeType.ASSURE+","+SETTConstant.InterestFeeType.COMMISION+","+SETTConstant.InterestFeeType.INTERESTTAX+","+SETTConstant.InterestFeeType.PREDRAWINTEREST+" ";
			
			//Boxu Add 2008��3��14�� ����ͼ��ѯ����������Ϣ��ʱ����ʾnInterestTypeΪ"8",���Բ�ѯ�������ۼƻ�Ϣ�����˳����������Ϣ
			strSQL += " , 8 ) ";
			
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				dInterest = rs.getDouble("Interest");	
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);			
		}
		return dInterest;
	}


	//ͨ�����˻�ID��ѯ���д�����Ϣ
	public QuerySubAffianceInfo findAffianceInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubAffianceInfo info = null;
		//���˻�
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubAffianceInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{

				info.setID(subInfo.getSubAccountLoanInfo().getID());
				info.setAccountID(subInfo.getSubAccountLoanInfo().getAccountID());
				info.setAL_nLoanNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID()); //�ſ�֪ͨ��					
				info.setNStatusID(subInfo.getSubAccountLoanInfo().getStatusID()); //״̬				

				info.setNPayInterestAccountID(subInfo.getSubAccountLoanInfo().getPayInterestAccountID()); //��Ϣ�˻�
								
				info.setAL_nPayInterestAccountID(subInfo.getSubAccountLoanInfo().getPaySuretyAccountID()); //����˻���
				info.setAL_nSuretyAccountID(subInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID()); //�������˻���
				info.setMBalance(subInfo.getSubAccountLoanInfo().getBalance()); //��ǰ���				
				info.setAC_mNegotiateInterest(subInfo.getSubAccountLoanInfo().getInterest()); //�ۼ���Ϣ
				info.setAF_mPreDrawInterest(subInfo.getSubAccountLoanInfo().getPreDrawInterest()); //�Ѽ�����Ϣ
				info.setDtOpen(subInfo.getSubAccountLoanInfo().getOpenDate()); //��������
				info.setDtFinish(subInfo.getSubAccountLoanInfo().getFinishDate()); //�廧����				
				// �ſ�״̬�����˻�״̬��
				info.setSubAccountStatusID(subInfo.getSubAccountLoanInfo().getStatusID());

				//ͨ���ſ�˻��Ų�ѯ�������Ϣ
				Sett_TransGrantLoanDAO loanDao = new Sett_TransGrantLoanDAO();
				LoanPayFormDetailInfo tmpInfo = new LoanPayFormDetailInfo();
				tmpInfo.setLoadNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
				LoanPayFormDetailInfo loanInfo = loanDao.findPayFormDetailByCondition(tmpInfo);
				if (loanInfo != null)
				{

					//��ͬ��
					info.setContractCode(loanInfo.getContractCode());
					//��������
					info.setLoanType(loanInfo.getLoanType());
					//��������
					info.setLoanTerm(loanInfo.getLoanTerm());
					//������  
					info.setAmount(loanInfo.getAmount());
					//��ʼ����
					info.setAF_dtStart(loanInfo.getStart()); //��ʼ����				
					//��������
					info.setAF_dtEnd(loanInfo.getEnd()); //��������
					//����
					info.setLoanInterestRate(loanInfo.getInterestRate()); //����	
					info.setAL_mSuretyFee(loanInfo.getAssureRate()); //��������
					info.setDtOut(loanInfo.getInterestStart());//�ſ�����
					info.setCommissionRate(loanInfo.getCommissionRate());
					info.setTypeID1(loanInfo.getTypeID1());//��������
					info.setTypeID2(loanInfo.getTypeID2());//��ҵ����1
					info.setTypeID3(loanInfo.getTypeID3());//��ҵ����2
				}
				//����ۼƻ�Ϣ
				info.setTotalRepaymentInterest(this.getTotalRepayInterest(info.getID()));
				
				if(info.getLoanType()==LOANConstant.LoanType.YT)
				{
					double ytRate = 0.0;
					BankLoanQuery bankLoanQuery =new BankLoanQuery();
					//�д�����				
					/*
					ytRate=bankLoanQuery.findRateByFormID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
					if(ytRate>0)
					{
						info.setAC_mNegotiateInterest(info.getAC_mNegotiateInterest()/ytRate*100);
					}
					*/
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	//ͨ�����˻�ID��ѯί�д�����Ϣ
	public QuerySubConsignInfo findConsignInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubConsignInfo info = null;
		//���˻�
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubConsignInfo();
			subInfo = dao.findByID(nSubAccountID);
			Sett_TaxRatePlanSettingItemDAO iteDAO = new Sett_TaxRatePlanSettingItemDAO();
    		
    		//�õ���Ϣ˰���ʼƻ���Ӧ��������
    		double mainRate = iteDAO.getMainRate(subInfo.getSubAccountLoanInfo().getInterestTaxRatePlanID());
			if (subInfo != null)
			{

				info.setID(subInfo.getSubAccountLoanInfo().getID()); //���˻�ID
                info.setAccountID(subInfo.getSubAccountLoanInfo().getAccountID());// ���˻�ID						
				info.setAL_nLoanNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID()); //�ſ�֪ͨ��				
				info.setNStatusID(subInfo.getSubAccountLoanInfo().getStatusID()); //״̬	
				info.setAF_mRate(subInfo.getSubAccountLoanInfo().getInterestTaxRate()); //����	
				info.setAL_nPayInterestAccountID(subInfo.getSubAccountLoanInfo().getPayInterestAccountID()); //�����Ϣ�˻�
                //// ������
                Sett_AccountDAO acctDao = new Sett_AccountDAO();
                AccountInfo ai = acctDao.findByID(subInfo.getSubAccountLoanInfo().getAccountID());
                //
                InterestOperation io = new InterestOperation();
                Timestamp tsQueryDate = Env.getSystemDate(ai.getOfficeID(), ai.getCurrencyID());
                
                LoanAccountInterestInfo laii = io.getLoanAccountFee(ai.getOfficeID(),ai.getCurrencyID(),ai.getAccountID(),subInfo.getSubAccountLoanInfo().getID(),tsQueryDate,tsQueryDate,SETTConstant.InterestFeeType.COMMISION);
                
				info.setAL_mCommission(laii.getInterest());//������
				
				info.setAL_nCommissionAccountID(subInfo.getSubAccountLoanInfo().getCommissionAccountID()); //֧���������˻���
				
				info.setAL_mInterestTaxRate(mainRate); //��Ϣ˰����
				info.setAL_dtEffectiveTax(subInfo.getSubAccountLoanInfo().getEffectiveTaxDate()); //��Ч����
				info.setAL_nInterestTaxAccountID(subInfo.getSubAccountLoanInfo().getInterestTaxAccountID()); //֧����Ϣ˰���˻���
				
				info.setConsignAccountID(subInfo.getSubAccountLoanInfo().getConsignAccountID()); //ί�д���˻���
				
				info.setAL_nReceiveInterestAccountID(subInfo.getSubAccountLoanInfo().getReceiveInterestAccountID()); //ί�з���Ϣ�˻�
				
				info.setAL_nIsInterest(subInfo.getSubAccountLoanInfo().getIsInterest()); //�Ƿ��Ϣ
				info.setMBalance(subInfo.getSubAccountLoanInfo().getBalance()); //��ǰ���				
				info.setAC_mNegotiateInterest(subInfo.getSubAccountLoanInfo().getInterest()); //�ۼ���Ϣ
				info.setAF_mPreDrawInterest(subInfo.getSubAccountLoanInfo().getPreDrawInterest()); //�Ѽ�����Ϣ
				info.setDtOpen(subInfo.getSubAccountLoanInfo().getOpenDate()); //��������
				info.setDtFinish(subInfo.getSubAccountLoanInfo().getFinishDate()); //�廧����
				

				//ͨ���ſ�˻��Ų�ѯ�������Ϣ
				Sett_TransGrantLoanDAO loanDao = new Sett_TransGrantLoanDAO();
				LoanPayFormDetailInfo tmpInfo = new LoanPayFormDetailInfo();
				tmpInfo.setLoadNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
				LoanPayFormDetailInfo loanInfo = loanDao.findPayFormDetailByCondition(tmpInfo);
				if (loanInfo != null)
				{
					//��������
					LoanPayNoticeDao loanpayDao = new LoanPayNoticeDao();
					PayNoticeRateInfo pnInfo=loanpayDao.getRateValue(Constant.RateType.CHARGE,
							subInfo.getSubAccountLoanInfo().getLoanNoteID(),
							null);
	System.out.println("\n\n �ſ�֪ͨ�� ="+subInfo.getSubAccountLoanInfo().getLoanNoteID()+"\n\n");
	System.out.println("\n\n pnInfo="+pnInfo+"\n\n");
					info.setAL_mCommissionRate(pnInfo.getInterestRate()); 
					//��ͬ��
					info.setContractCode(loanInfo.getContractCode());
					//��������
					info.setLoanType(loanInfo.getLoanType());
					//��������
					info.setLoanTerm(loanInfo.getLoanTerm());
					//������  
					info.setAmount(loanInfo.getAmount());
					//ί�з�                
					info.setClientCode(loanInfo.getClientCode());
					//Added by zwsun, 2007/7/4, ί�з�����
					info.setClientName(loanInfo.getClientName());
					//��ʼ����	
					info.setAF_dtStart(loanInfo.getStart());
					//��������
					info.setAF_dtEnd(loanInfo.getEnd());
					//�ſ�����
					info.setDtOut(loanInfo.getInterestStart());
					//��������ȡ��ʽ  getChargeRateTypeID
					info.setChargeRateTypeID(loanInfo.getChargeRateTypeID());

					//��������
					if (loanInfo.getStart() != null)
					{
						info.setNYear(Long.parseLong(loanInfo.getStart().toString().substring(0, 4)));
					}
					
					info.setTypeID1(loanInfo.getTypeID1());//��������
					info.setTypeID2(loanInfo.getTypeID2());//��ҵ����1
					info.setTypeID3(loanInfo.getTypeID3());//��ҵ����2					
				}
				//����ۼƻ�Ϣ
				info.setTotalRepaymentInterest(this.getTotalRepayInterest(info.getID()));				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	//ͨ�����˻�ID��ѯ���ַ�����Ϣ
	public QuerySubDiscountInfo findDiscountInfoBySubaccountID(long nSubAccountID) throws Exception
	{
		QuerySubDiscountInfo info = null;
		//���˻�
		SubAccountAssemblerInfo subInfo = null;
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			info = new QuerySubDiscountInfo();
			subInfo = dao.findByID(nSubAccountID);
			if (subInfo != null)
			{
				info.setID(subInfo.getSubAccountLoanInfo().getID());
				info.setAccountID(subInfo.getSubAccountLoanInfo().getAccountID());
				info.setNStatusID(subInfo.getSubAccountLoanInfo().getStatusID()); //״̬				
				info.setMBalance(subInfo.getSubAccountLoanInfo().getBalance()); //��ǰ���
				info.setDtOpen(subInfo.getSubAccountLoanInfo().getOpenDate()); //��������
				info.setDtFinish(subInfo.getSubAccountLoanInfo().getFinishDate()); //�廧����
				info.setAL_mInterestTax(subInfo.getSubAccountLoanInfo().getInterest()); //�� Ϣ
				
				//ͨ���ſ�˻��Ų�ѯ�������Ϣ
				Sett_TransGrantDiscountDAO discountDao = new Sett_TransGrantDiscountDAO();
				
				TransGrantDiscountInfo discountInfo = discountDao.findSubQueryInfoByDiscountNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
				System.out.println("\n\n �ſ�֪ͨ�� LoanNoteID=" + subInfo.getSubAccountLoanInfo().getLoanNoteID() + " \n\n");
				if (discountInfo != null)
				{
					//��ͬ��  ���� loan_discountcredence
					info.setLoanContractID(discountInfo.getDiscountContractID());
					info.setAL_nLoanNoteID(subInfo.getSubAccountLoanInfo().getLoanNoteID());
					//����ƾ֤���	���� loan_discountcredence				
					info.setAL_nLoanNoteNo(discountInfo.getTransNo());
					//��Ʊ��� ���� loan_discountcredence
					info.setAmount(discountInfo.getDiscountBillAmount());
					//�˶����
					info.setDiscountAmount(discountInfo.getDiscountAmount());
					//��Ϣ
					info.setAL_mInterestTax(discountInfo.getInterest());	
					//��ʼ����					
					Sett_TransGrantDiscountDAO dtDao = new Sett_TransGrantDiscountDAO();
					TransGrantDiscountInfo dtInfo = dtDao.findDateInfoByDiscountNoteID(discountInfo.getDiscountContractID());
					info.setAF_dtStart(dtInfo.getInputDate());
					//��Ʊ����  �������� loan_discountcontractbill					
					info.setNDraftTypeID(discountDao.findPaytypeIDByID(subInfo.getSubAccountLoanInfo().getLoanNoteID()));

					//info.setTypeID1(loanInfo.getTypeID1());//��������
					//info.setTypeID2(loanInfo.getTypeID2());//��ҵ����1
					//info.setTypeID3(loanInfo.getTypeID3());//��ҵ����2
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	//ͨ�����˻�ID��ѯ�˻�����
	public long findAccountTypeBySubAccountID(long nSubAccountID) throws Exception
	{
		System.out.println("\n\n ��̨,����Ĳ���Ϊ: nSubAccountID=" + nSubAccountID + "\n\n");
		long nAccountTypeID = -1;

		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		Sett_AccountDAO accntDao = new Sett_AccountDAO();
		SubAccountAssemblerInfo subInfo = null;
		AccountInfo accountInfo = null;

		try
		{
			subInfo = dao.findByID(nSubAccountID);
			System.out.println("\n\n subInfo=" + subInfo + "\n\n");
			if (subInfo != null)
			{
				long nAccountID = subInfo.getSubAccountFixedInfo().getAccountID();
				System.out.println("\n\n ��̨,nAccountID=" + nAccountID + "\n\n");
				accountInfo = accntDao.findByID(nAccountID);
				System.out.println("\n\n ��̨,accountInfo=" + accountInfo + "\n\n");
				if (accountInfo != null)
				{
					nAccountTypeID = accountInfo.getAccountTypeID();
					System.out.println("\n\n ��̨,nAccountTypeID=" + nAccountTypeID + "\n\n");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return nAccountTypeID;
	}
}
