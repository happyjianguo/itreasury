/**
 * 
 */
package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.ContractDetailDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.TransferContractDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.ContractdetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.ReceiveTransferNoticeDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferNoticeDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferNoticeDetailDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferReceiveClientNoticeDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeDetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.ebank.system.util.Arithmetic;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.settlement.transferinterest.bizlogic.CommissionCalculation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author xintan
 *
 *	����֪ͨ��
 */
public class TransferReceiveClientNoticeBiz {

	private Log4j logger = new Log4j(Constant.ModuleType.CRAFTBROTHER, TransferReceiveClientNoticeBiz.class);
	/**
	 * ��������֪ͨ����������������֪ͨ���ĳ�ʼ��
	 */
	public TransferNoticeInfo createReceiveNoticeInfo(TransferContractInfo contractInfo) throws IException
	{
		TransferNoticeInfo newNoticeInfo = null;
		try{
			//������Ϊ֪ͨ����Ĭ��ִ����
			Timestamp dtOpenDate 
				= Env.getSystemDate(contractInfo.getOfficeId(),contractInfo.getCurrencyId());
			
			//********����֪ͨ��������Ϣ***************//
			newNoticeInfo = new TransferNoticeInfo();
			newNoticeInfo.setOfficeId(contractInfo.getOfficeId());
			newNoticeInfo.setCurrencyId(contractInfo.getCurrencyId());
			newNoticeInfo.setCracontractId(contractInfo.getId());
			newNoticeInfo.setCracontractCode(contractInfo.getContractCode());
			newNoticeInfo.setDtclearInterest(dtOpenDate);
			newNoticeInfo.setTranstypeid(CRAconstant.CraTransactionType.BREAK_NOTIFY);
			newNoticeInfo.setAgentType(CRAconstant.AgentType.AMOUNT);//Ĭ��Ϊֻ�ձ���
	
			//��ѯת�ú�ͬ��ϸ
			ContractDetailDao contractDetailDao = new ContractDetailDao();
			
			Collection colContractDetails 
				= contractDetailDao.queryContractDetailInfos(contractInfo.getId());
			
			TransferNoticeDetailInfo aNoticeDetailInfo = null;
			double sumAmount = 0.0; //�����ۼƣ����ڼ�������֪ͨ���Ĵ��ձ���
			double sumInterest = 0.0; //��Ϣ�ۼ�
			
			Collection colNoticeDetails = new ArrayList();
			ContractdetailInfo aContractDetailInfo = null;
			
			//********����֪ͨ����ϸ��Ϣ***************//
			if(colContractDetails!=null){
				Iterator it = colContractDetails.iterator();
				
				while(it.hasNext()){
					aContractDetailInfo = (ContractdetailInfo)it.next();
					
					aNoticeDetailInfo = generateReceiveNoticeDetail(aContractDetailInfo);
					
					//�����ת�ú�ͬ��ϸ��ǰ��ʣ�౾��
					double sumReceiveAmount 
						= contractDetailDao.sumNoticeFormAmount(
							aContractDetailInfo, 
							CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT,
							CRAconstant.ISURROGATEPAY.ISNOT,
							dtOpenDate);
					
					double sumUrrogatePayAmount
						= contractDetailDao.sumNoticeFormAmount(
								aContractDetailInfo, 
								CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT,
								CRAconstant.ISURROGATEPAY.ISTRUE, 
								dtOpenDate);
					
					double balance = Arithmetic.sub(sumReceiveAmount, sumUrrogatePayAmount);
					
					aNoticeDetailInfo.setBalance(balance); //ת���Ӻ�ͬ���
					aNoticeDetailInfo.setAmount(balance);//֪ͨ������
					sumAmount = Arithmetic.add(sumAmount, balance);
					
					//ͳ�Ƹ�ת�ú�ͬ��ϸ���ϴν�Ϣ��
					Timestamp lastClearInterestDate 
						= contractDetailDao.getLastClearInterestDate(aContractDetailInfo);
					
					aNoticeDetailInfo.setLastClearInterest(lastClearInterestDate);
					
					//�����Ӫ����ſ�ķſ�����
					double loanInterestRate = 0.0;
					LoanPayNoticeDao loanNoticeDao = new LoanPayNoticeDao();
					loanInterestRate = loanNoticeDao.getLatelyRate(
							aContractDetailInfo.getLoannoteid(),
							dtOpenDate);
					
					aNoticeDetailInfo.setRate(loanInterestRate);
					aNoticeDetailInfo.setInterest(0.0);
					
					colNoticeDetails.add(aNoticeDetailInfo);
				}
			} 
			
			newNoticeInfo.setAmount(sumAmount);
			newNoticeInfo.setInterest(sumInterest);
			
			newNoticeInfo.setColNoticeDetailInfos(colNoticeDetails);
			double dCommission = calculateCommission(newNoticeInfo);
			newNoticeInfo.setMcommission(dCommission);
			
		}catch(Exception exp){
			exp.printStackTrace();
			logger.error(exp.getMessage());
			throw new IException("����֪ͨ��ʱ����" + exp.getMessage());
		}
		
		return newNoticeInfo;
	}
	
	public double calculateCommission(TransferNoticeInfo noticeInfo) throws IException {
		try{
			CommissionCalculation calculator = new CommissionCalculation();
			return calculator.calculateCommission(
					noticeInfo.getAmount(), 
					noticeInfo.getInterest(), 
					noticeInfo.getCommissionRate(), 
					noticeInfo.getRate(), 
					noticeInfo.getCommissionPaymentType());
		}catch(Exception exp){
			throw new IException("������������ʱ����" + exp.getMessage(), exp);
		}
	}



	/**
	 * ����ת�ú�ͬ��ϸ��������֪ͨ�����տ���ϸ��Ϣ
	 * 
	 * @param contractDetailInfo  ת�ú�ͬ��ϸ
	 * @return
	 */
	private TransferNoticeDetailInfo generateReceiveNoticeDetail(ContractdetailInfo contractDetailInfo) {
		
		TransferNoticeDetailInfo noticeDetailInfo = new TransferNoticeDetailInfo();
		
		noticeDetailInfo.setOfficeID(contractDetailInfo.getOfficeid());
		noticeDetailInfo.setCurrencyID(contractDetailInfo.getCurrencyid());
		noticeDetailInfo.setCraContractID(contractDetailInfo.getTransfercontractformid());
		noticeDetailInfo.setContractDetailID(contractDetailInfo.getId());
		noticeDetailInfo.setContractID(contractDetailInfo.getLoancontractid());
		noticeDetailInfo.setLoanContractCode(contractDetailInfo.getLoanContractCode());
		noticeDetailInfo.setBorrowClientID(contractDetailInfo.getBorrowClientId());
		noticeDetailInfo.setBorrowClientName(contractDetailInfo.getBorrowClientName());
		noticeDetailInfo.setPayFormID(contractDetailInfo.getLoannoteid());
		noticeDetailInfo.setLoanPayNoticeCode(contractDetailInfo.getLoanNoticeCode());
		noticeDetailInfo.setBalance(contractDetailInfo.getBalance());
		noticeDetailInfo.setClearInterestDate(null);
		
		return noticeDetailInfo;
	}
	
	/**
	 * 
	 * �˷���ͨ���ſ�֪ͨ����ID��ѯ�õ��ݵ���Ϣ
	 * @param lLoanPayNoticeID �ſ�֪ͨ����ʶ
	 */
	public double findRepayInterestByID(double amount ,long lLoanPayNoticeID,Timestamp lastClearDate,Timestamp clearInterestDate,long nOfficeID,long nCurrencyID) throws IException
	{
		double interestOne = 0;
		try
		{
			TransferReceiveClientNoticeDao noticeDao = new TransferReceiveClientNoticeDao();
			interestOne = noticeDao.findRepayInterestByID(amount,lLoanPayNoticeID,lastClearDate, clearInterestDate,nOfficeID,nCurrencyID);
		}
		catch (Exception e)
		{
			throw new IException("ͨ���ſ�֪ͨ����ID��ѯ�õ��ݵ���Ϣ����");
		}
		return interestOne;
	}
	
	/**
	 * �Ŵ�ת��֪ͨ������
	 * @param noticeInfo
	 * @return
	 * @throws IException
	 */
	public long saveTransferReceiveNoticeForm(TransferNoticeInfo noticeInfo) throws IException
	{
		long lReturn = -1;
		Connection con = null;
		try
		{
			con = Database.getConnection();
			con.setAutoCommit(false);
			
			ReceiveTransferNoticeDao receiveNoticeDao = new ReceiveTransferNoticeDao(con);
			if(noticeInfo.getId()>0)
			{
				receiveNoticeDao.update(noticeInfo);
				TransferNoticeDetailDao detailDao = new TransferNoticeDetailDao(con);
				
				detailDao.deleteTransferNoticeDetailByNoticeID(noticeInfo.getId());
				Collection coll = noticeInfo.getColNoticeDetailInfos();
				if(coll!=null && coll.size()>0)
				{
					Iterator it = coll.iterator();
					while(it.hasNext())
					{
						TransferNoticeDetailInfo detailInfo = (TransferNoticeDetailInfo)it.next();
						detailInfo.setNoticeFormID(noticeInfo.getId());
						detailDao.add(detailInfo);
					}
				}
				lReturn = noticeInfo.getId() ;
			}
			else
			{
				noticeInfo.setNoticeCode(getNewTransactionNo(noticeInfo.getOfficeId(), noticeInfo.getCurrencyId()));
				lReturn =  receiveNoticeDao.add(noticeInfo);
				Collection coll = noticeInfo.getColNoticeDetailInfos();
				if(coll!=null && coll.size()>0)
				{
					TransferNoticeDetailDao detailDao = new TransferNoticeDetailDao(con);
					Iterator it = coll.iterator();
					while(it.hasNext())
					{
						TransferNoticeDetailInfo detailInfo = (TransferNoticeDetailInfo)it.next();
						detailInfo.setNoticeFormID(lReturn);
						detailDao.add(detailInfo);
					}
				}
				
			}
			/**
			 * ���noticeInfo�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� 
			 */
			if(noticeInfo.getInutParameterInfo() != null)
			{
				logger.debug("------�ύ����--------");	
				InutParameterInfo tempInfo = noticeInfo.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl() + lReturn);
				tempInfo.setTransID(String.valueOf(lReturn));// ���ﱣ����ǽ���ID
				tempInfo.setDataEntity(noticeInfo);
                //�ύ����
				FSWorkflowManager.initApproval(noticeInfo.getInutParameterInfo());
				logger.debug("------�ύ�����ɹ�--------");
			}
			con.commit();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
			try{
				if(con!=null) con.rollback();
			}catch(Exception exp){
				exp.printStackTrace();
			}
			throw new IException(e.getMessage());

		}
		finally{
			try
			{
				if (con != null) con.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return lReturn;
	}	
	
	/**
	 * ����˵�������ɲ�����֪ͨ�����(��)
	 * add by xwhe 2009-7-2
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return 
	 * @throws SQLException
	 */
	private String getNewTransactionNo(long lOfficeID, long lCurrencyID) throws IException, Exception
	{
		String strTransNo = "";
		HashMap map = new HashMap();
		try
		{
			map.put("officeID",String.valueOf(lOfficeID));
			map.put("currencyID",String.valueOf(lCurrencyID));
			map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
			map.put("transTypeID",String.valueOf(Constant.ApprovalAction.CRA_TRANSLOANNOTICE));
			strTransNo=CreateCodeManager.createCode(map);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return strTransNo;
	}	
	
	/**
	 * 
	 * ��ѯ����֪ͨ����Ϣ������֪ͨ������Ϣ
	 * 
	 * @param noticeID ֪ͨ��ID
	 * @return
	 * @throws IException
	 */
	public TransferNoticeInfo findReceiveNoticeInfo(long noticeID) throws IException
	{
		if(noticeID<=0) throw new IException("֪ͨ��ID��Ч��" + noticeID);
		
		TransferNoticeInfo noticeInfo = null;
		
		try{
			//��ѯ֪ͨ��������Ϣ
			TransferNoticeDao baseNoticeDao = new TransferNoticeDao();
			noticeInfo = (TransferNoticeInfo)baseNoticeDao.findByID(noticeID, TransferNoticeInfo.class);
			
			//��ѯ֪ͨ����ϸ
			TransferNoticeDetailDao noticeDetailDao = new TransferNoticeDetailDao();
			
			TransferNoticeDetailInfo queryInfo = new TransferNoticeDetailInfo();
			queryInfo.setNoticeFormID(noticeID);
			queryInfo.setStatusID(CRAconstant.TransactionStatus.SAVE);
			
			HashMap noticeDetailsMap = new HashMap();
			Collection colNoticeDetails = noticeDetailDao.findByCondition(queryInfo);
			
			if(colNoticeDetails!=null && colNoticeDetails.size()>0)
			{
				Iterator it = colNoticeDetails.iterator();
				TransferNoticeDetailInfo aDetailInfo = null;
				while(it.hasNext()){
					aDetailInfo = (TransferNoticeDetailInfo) it.next();
					noticeDetailsMap.put(aDetailInfo.getContractDetailID()+"", aDetailInfo);
				}
			}

			//��ѯת�ú�ͬ������Ϣ
			TransferContractDao contractDao = new TransferContractDao();
			TransferContractInfo contractInfo 
				= contractDao.findTransferContractInfoByID(noticeInfo.getCracontractId());
			
			//��ѯת�ú�ͬ��ϸ
			ContractDetailDao contractDetailDao = new ContractDetailDao();
			
			Collection colContractDetails 
				= contractDetailDao.queryContractDetailInfos(noticeInfo.getCracontractId());
			
			//������Ϊ֪ͨ����Ĭ��ִ����
			Timestamp dtOpenDate 
				= Env.getSystemDate(contractInfo.getOfficeId(),contractInfo.getCurrencyId());
			
			TransferNoticeDetailInfo aNoticeDetailInfo = null;
			ArrayList newColNoticeDetails=new ArrayList();
						
			//********����֪ͨ����ϸ��Ϣ***************//
			if(colContractDetails!=null){
				Iterator it = colContractDetails.iterator();
				ContractdetailInfo aContractDetailInfo = null;
				
				while(it.hasNext()){
					aContractDetailInfo = (ContractdetailInfo)it.next();
					
					aNoticeDetailInfo 
						= (TransferNoticeDetailInfo) noticeDetailsMap
							.get(aContractDetailInfo.getId()+"");
					
					//�������֪ͨ������Ϣ�����ڣ������ת�ú�ͬ����Ϣ����ʼ��
					if(aNoticeDetailInfo==null || aNoticeDetailInfo.getId()<=0)
					{
						aNoticeDetailInfo = generateReceiveNoticeDetail(aContractDetailInfo);
						aNoticeDetailInfo.setChecked(false);						
					}else{
						//�������֪ͨ������Ϣ���ڣ�����Ҫ��info�в����Ӧ�ĺ�ͬ��Ϣ
						//add by minzhao 2009-08-14
						aNoticeDetailInfo.setLoanContractCode(aContractDetailInfo.getLoanContractCode());
						aNoticeDetailInfo.setBorrowClientName(aContractDetailInfo.getBorrowClientName());
						//add end
						aNoticeDetailInfo.setChecked(true);
					}
					
					//�����ת�ú�ͬ��ϸ��ǰ��ʣ�౾��
					double sumReceiveAmount 
						= contractDetailDao.sumNoticeFormAmount(
							aContractDetailInfo, 
							CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT,
							CRAconstant.ISURROGATEPAY.ISNOT,
							dtOpenDate);
					
					double sumUrrogatePayAmount
						= contractDetailDao.sumNoticeFormAmount(
								aContractDetailInfo, 
								CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT,
								CRAconstant.ISURROGATEPAY.ISTRUE, 
								dtOpenDate);
					
					double balance = Arithmetic.sub(sumReceiveAmount, sumUrrogatePayAmount);
					
					aNoticeDetailInfo.setBalance(balance);
					
					//ͳ�Ƹ�ת�ú�ͬ��ϸ���ϴν�Ϣ��
					Timestamp lastClearInterestDate 
						= contractDetailDao.getLastClearInterestDate(aContractDetailInfo);
					
					aNoticeDetailInfo.setLastClearInterest(lastClearInterestDate);
					
					//�����Ӫ����ſ�ķſ�����
					double loanInterestRate = 0.0;
					LoanPayNoticeDao loanNoticeDao = new LoanPayNoticeDao();
					loanInterestRate = loanNoticeDao.getLatelyRate(
							aContractDetailInfo.getLoannoteid(),
							dtOpenDate);
					
					aNoticeDetailInfo.setRate(loanInterestRate);
					
					newColNoticeDetails.add(aNoticeDetailInfo);
				}
			} 
			
			noticeInfo.setColNoticeDetailInfos(newColNoticeDetails);
			
		}catch(Exception exp){
			exp.printStackTrace();
			logger.error(exp.getMessage());
			throw new IException("��ѯ֪ͨ��ʱ����" + exp.getMessage());
		}
		
		return noticeInfo;
	}
}
