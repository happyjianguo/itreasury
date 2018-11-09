package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.bizlogic;


import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransPayNoticeDetailDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferNoticeDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferNoticeDetailDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetailConditionInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.NoticeQueryInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransPayNoticeDetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.settlement.transferinterest.bizlogic.DateUtil;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransferNoticeBiz 
{
	private TransferNoticeDao noticeDao;
	private Log4j log = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	public TransferNoticeBiz() throws IException
	{
		noticeDao = new TransferNoticeDao();
	}
	
	/**
	 * �Ŵ�ת��֪ͨ����ѯ
	 * @param queryInfo
	 * @return
	 * @throws IException
	 */
	public PageLoader findByQueryInfo(NoticeQueryInfo queryInfo) throws IException
	{
		PageLoader pageLoader = null;
		try
		{
			pageLoader = noticeDao.findByConditions(queryInfo);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return pageLoader;
	}
	
	/**
	 * ����ID��ѯ�Ŵ�ת�ú�ͬ
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TransferNoticeInfo findNoticeInfoById(long id) throws Exception
	{
		TransferNoticeInfo info = new TransferNoticeInfo();
		TransferNoticeDetailDao detaildao=new TransferNoticeDetailDao();
		Collection coll=null;
		try
		{
			info = (TransferNoticeInfo)noticeDao.findByID(id, info.getClass());
			coll=detaildao.searchTransferNoticeDetailByNoticeID(info.getId());
			info.setColl(coll);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return info;
	}
	
	/**
	 * ����״̬
	 * @param id,statusId
	 * @return long
	 * @throws IException,Exception
	 */
	public long updateStatus(long id, long statusId) throws IException,Exception
	{
		long lReturn = -1;
		try{
			lReturn = noticeDao.updateStatus(id, statusId);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lReturn;
	}
	
	/**
	 * ɾ��
	 * @param id,statusId
	 * @return long
	 * @throws IException,Exception
	 */
	public long deleteTransferNotice(long id) throws IException,Exception
	{
		long lReturn = -1;
		Connection con = null;
		try
		{
			con = Database.getConnection();
			con.setAutoCommit(false);
			try
			{
				long lID = noticeDao.validate(id);
				if(lID > 0)
				{
					lReturn = -2;
				}
				else
				{
					lReturn = noticeDao.updateStatus(id, CRAconstant.TransactionStatus.DELETED);
					TransPayNoticeDetailDao detailDao = new TransPayNoticeDetailDao(con);
					detailDao.deleteTransPayNoticeDetailByNoticeID(id);
					lReturn = id;
				}
				con.commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				con.rollback();
				throw new IException(e.getMessage());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		finally
		{
			try
			{
				if (con != null ) {
					con.close();
					con = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return lReturn;
	}
	
	/**
	 * ����ID��ѯ�Ŵ�ת�ú�ͬ�ϴν�Ϣ��
	 * @param contractId
	 * @return
	 * @throws IException,Exception
	 */
	public Timestamp findLastClearInterest(long contractId,long transferTypeId) throws IException,Exception
	{
		Timestamp lastClearInterest = null;
		try{
			lastClearInterest = noticeDao.getLastClearInterest(contractId,transferTypeId);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lastClearInterest;
	}
	
	/**
	 * ����˵�������ɲ�����֪ͨ�����(��)
	 * add by xwhe 2009-7-2
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return 
	 * @throws IException
	 */
	public String getTransferNoticeCode(long lOfficeID, long lCurrencyID) throws Exception
	{
		String strTransNo = "";
		try
		{
			strTransNo = noticeDao.getNewTransactionNo(lOfficeID, lCurrencyID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return strTransNo;
	}
	
	/**
	 * �Ŵ�ת�ø���֪ͨ������
	 * @param noticeInfo
	 * @return
	 * @throws IException
	 */
	public long saveTransferNoticeForm(TransferNoticeInfo noticeInfo) throws IException
	{
		long lReturn = -1;
		Connection con = null;
		try
		{
			con = Database.getConnection();
			con.setAutoCommit(false);
			try
			{
				if(noticeInfo.getId()>0)
				{
					noticeDao.update(noticeInfo);
					TransPayNoticeDetailDao detailDao = new TransPayNoticeDetailDao(con);
					
					detailDao.deleteTransPayNoticeDetailByNoticeID(noticeInfo.getId());
					Collection coll = noticeInfo.getColl();
					if(coll!=null && coll.size()>0)
					{
						Iterator it = coll.iterator();
						while(it.hasNext())
						{
							TransPayNoticeDetailInfo detailInfo = (TransPayNoticeDetailInfo)it.next();
							detailInfo.setTransferPayNoticeID(noticeInfo.getId());
							detailDao.add(detailInfo);
						}
					}
					lReturn = noticeInfo.getId();
				}
				else
				{
					noticeInfo.setNoticeCode(this.getTransferNoticeCode(noticeInfo.getOfficeId(), noticeInfo.getCurrencyId()));
					lReturn = noticeDao.add(noticeInfo);
					Collection coll = noticeInfo.getColl();
					if(coll!=null && coll.size()>0)
					{
						TransPayNoticeDetailDao detailDao = new TransPayNoticeDetailDao(con);
						Iterator it = coll.iterator();
						while(it.hasNext())
						{
							TransPayNoticeDetailInfo detailInfo = (TransPayNoticeDetailInfo)it.next();
							detailInfo.setTransferPayNoticeID(lReturn);
							detailDao.add(detailInfo);
						}
					}
				}
				/**
				 * ���noticeInfo�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� 
				 */
				if(noticeInfo.getInutParameterInfo() != null)
				{
					log.debug("------�ύ����--------");	
					InutParameterInfo tempInfo = noticeInfo.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + lReturn);
					tempInfo.setTransID(String.valueOf(lReturn));// ���ﱣ����ǽ���ID
					tempInfo.setDataEntity(noticeInfo);
	                //�ύ����
					FSWorkflowManager.initApproval(noticeInfo.getInutParameterInfo());
					log.debug("------�ύ�����ɹ�--------");
				}
				con.commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				con.rollback();
				throw new IException(e.getMessage());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		finally
		{
			try
			{
				if (con != null ) {
					con.close();
					con = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return lReturn;
	}
	
	/**
	 * �Ŵ�ת��֪ͨ����ϸ��ѯ
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	public TransferNoticeInfo findInfoByID(TransferNoticeInfo noticeInfo) throws Exception
	{
		TransferNoticeInfo info = null;
		try
		{
			info = noticeDao.findByID(noticeInfo);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException("");
		}
		
		return info;
	}
	
	/**
	 * ����
	 * @param noticeInfo
	 * @return
	 * @throws IException
	 */
	public long doApproval(TransferNoticeInfo info) throws IException,Exception
	{
		long lReturn = -1; 
		InutParameterInfo returnInfo = new InutParameterInfo();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		try{
            TransferNoticeInfo noticeInfo = new TransferNoticeInfo();
            noticeInfo = this.findNoticeInfoById(info.getId());
			inutParameterInfo.setDataEntity(noticeInfo);
           //�ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
           //��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel())
			{
				noticeInfo.setStatusId(CRAconstant.TransactionStatus.APPROVALED);
				noticeDao.update(noticeInfo);
                //����ͨ�������ݴ��������������
			}
           //��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if (returnInfo.isRefuse())
			{
				noticeInfo.setStatusId(CRAconstant.TransactionStatus.SAVE);
				noticeDao.update(noticeInfo);
			}
			lReturn = noticeInfo.getId();
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lReturn;
	}
	
	/**
	 * ȡ������
	 * @param noticeInfo
	 * @return
	 * @throws IException
	 */
	public long cancelApproval(TransferNoticeInfo info) throws IException,Exception
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		try{
			long lID = noticeDao.validate(info.getId());
			if(lID > 0){
				lReturn = -2;
			}
			else{
				lReturn = noticeDao.updateStatus(info.getId(), CRAconstant.TransactionStatus.SAVE);
				
				//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
	            if (inutParameterInfo.getApprovalEntryID() > 0)
	            {
	            	FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
	            }
			}
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lReturn;
	}
	
	/**
	 * ����ID��ѯ�Ŵ�ת�ú�ͬ�ϴν�Ϣ��
	 * @param contractId
	 * @return
	 * @throws IException,Exception
	 */
	public String findBankNoByID(long bankId) throws IException,Exception
	{
		String bankNo = "";
		try{
			bankNo = noticeDao.findBankNoByID(bankId);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return bankNo;
	}
	
	/**
	 * ������������֪ͨ����Ϣ
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public Collection findNoticeOrNoticeDetial(ContractAndNoticeDetailConditionInfo conditionInfo) throws IException
	{
		Collection coll = null;
		try
		{
			coll = new ArrayList();
			//ҵ������Ϊ�����ع�
			if(conditionInfo.getTransTypeID()==CRAconstant.CraTransactionType.REPURCHASE_NOTIFY)
			{
				Collection resultColl = noticeDao.findReceiveNoticeDetial(conditionInfo);
				if(resultColl!=null && resultColl.size()>0)
				{
					Iterator it = resultColl.iterator();
					while(it.hasNext())
					{
						TransPayNoticeDetailInfo detailInfo = (TransPayNoticeDetailInfo)it.next();
						double amount = detailInfo.getAmount();
						Timestamp dtStartDate = detailInfo.getLastClearInterest();
						conditionInfo.setDtstartDate(dtStartDate);
						Timestamp dtClearDate = noticeDao.queryLastclearDate(conditionInfo);
						if(dtClearDate!=null && dtClearDate.after(dtStartDate))
						{
						 detailInfo.setLastClearInterest(dtClearDate);
						}
						amount = amount - noticeDao.findPayBalanceByConId(detailInfo.getTransferRepayNoticeID());
						detailInfo.setAmount(amount);
						if(amount!=0)
						{
							coll.add(detailInfo);
						}
					}
				}
			}
			//ҵ������Ϊ��������
			else if(conditionInfo.getTransTypeID()==CRAconstant.CraTransactionType.BREAK_NOTIFY)
			{
				TransPayNoticeDetailInfo noticeDetailInfo = new TransPayNoticeDetailInfo();
				double sumBalance = noticeDao.findBalanceByConId(conditionInfo.getTransferContractFormID());
				double sumInterest = noticeDao.findInterestByConId(conditionInfo.getTransferContractFormID());
				double sumCommsion = noticeDao.findCommsionByConId(conditionInfo.getTransferContractFormID());
				noticeDetailInfo.setTransferContractID(conditionInfo.getTransferContractFormID());
				noticeDetailInfo.setAmount(sumBalance);
				noticeDetailInfo.setInterest(sumInterest);	
				noticeDetailInfo.setPayCommsion(sumCommsion);
				coll.add(noticeDetailInfo);
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
	 * ��������Ϊ�����ع���������Ϣ
	 * @param interestRate ����
	 * @param startBalance �ڳ����
	 * @param startDate ��Ϣ��ʼ����
	 * @param endDate ��Ϣ��������
	 * @return ��Ϣֵ
	 * @throws IException
	 */
	public double calculateInterest(Timestamp startDate,double startBalance,double interestRate,Timestamp endDate) throws IException
	{
		double lReturn = 0.00;				
		try
		{
            //��Ϣ��
			Timestamp dtStartDate = startDate;
            //ֹϢ��
			Timestamp dtEndDate= endDate;
			//�ڳ����
			double dtStartBalance = startBalance;
       
			lReturn = DateUtil.caculateInterest(
						  dtStartBalance 
						, dtStartDate
						, dtEndDate
						, DateUtil.InterestCalculationMode.FACTDAY
						, interestRate
						, DateUtil.InterestRateTypeFlag.YEAR
						, DateUtil.InterestRateDaysFlag.DAYS_360 );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lReturn;
	}
	
	/**
	 * ���ݸ���֪ͨ��ID��ѯ����֪ͨ��������ϸ
	 * @param payNoticeId ����֪ͨ��ID
	 * @return
	 * @throws Exception
	 */
	public TransferNoticeInfo findPayNoticeDetailByNoticeID(long id) throws Exception
	{
		TransferNoticeInfo info = new TransferNoticeInfo();
		Collection coll = null;
		try
		{				
			info = (TransferNoticeInfo)noticeDao.findByID(id, info.getClass());
			//ҵ������Ϊ�����ع�
			if(info.getTranstypeid() == CRAconstant.CraTransactionType.REPURCHASE_NOTIFY)
			{
				TransPayNoticeDetailDao detailDao = new TransPayNoticeDetailDao();
				coll = detailDao.searchTransPayNoticeDetailByNoticeID(id);
				info.setColl(coll);
			}	
             //ҵ������Ϊ��������
			else if(info.getTranstypeid()==CRAconstant.CraTransactionType.BREAK_NOTIFY)
			{
				coll = new Vector();
				TransPayNoticeDetailInfo noticeDetailInfo = new TransPayNoticeDetailInfo();
				noticeDetailInfo.setTransferContractID(info.getCracontractId());
				noticeDetailInfo.setAmount(info.getAmount());
				noticeDetailInfo.setInterest(info.getInterest());				
				coll.add(noticeDetailInfo);
				info.setColl(coll);
			}	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return info;
	}

}
