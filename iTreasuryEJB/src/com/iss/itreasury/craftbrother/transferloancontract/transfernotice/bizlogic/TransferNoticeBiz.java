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
	 * 信贷转让通知单查询
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
	 * 根据ID查询信贷转让合同
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
	 * 更改状态
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
	 * 删除
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
	 * 根据ID查询信贷转让合同上次结息日
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
	 * 方法说明：生成并返回通知单编号(新)
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
	 * 信贷转让付款通知单保存
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
				 * 如果noticeInfo中的InutParameterInfo不为空,则需要提交审批 
				 */
				if(noticeInfo.getInutParameterInfo() != null)
				{
					log.debug("------提交审批--------");	
					InutParameterInfo tempInfo = noticeInfo.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + lReturn);
					tempInfo.setTransID(String.valueOf(lReturn));// 这里保存的是交易ID
					tempInfo.setDataEntity(noticeInfo);
	                //提交审批
					FSWorkflowManager.initApproval(noticeInfo.getInutParameterInfo());
					log.debug("------提交审批成功--------");
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
	 * 信贷转让通知单明细查询
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
	 * 审批
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
           //提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
           //如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel())
			{
				noticeInfo.setStatusId(CRAconstant.TransactionStatus.APPROVALED);
				noticeDao.update(noticeInfo);
                //审批通过将数据存入评级结果表中
			}
           //如果是最后一级,且为审批拒绝,更新状态为已保存
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
	 * 取消审批
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
				
				//将审批记录表内的该交易的审批记录状态置为无效
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
	 * 根据ID查询信贷转让合同上次结息日
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
	 * 根据条件查找通知单信息
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
			//业务类型为卖出回购
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
			//业务类型为卖出卖断
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
	 * 交易类型为卖出回购，匡算利息
	 * @param interestRate 利率
	 * @param startBalance 期初余额
	 * @param startDate 计息开始日期
	 * @param endDate 计息结束日期
	 * @return 利息值
	 * @throws IException
	 */
	public double calculateInterest(Timestamp startDate,double startBalance,double interestRate,Timestamp endDate) throws IException
	{
		double lReturn = 0.00;				
		try
		{
            //起息日
			Timestamp dtStartDate = startDate;
            //止息日
			Timestamp dtEndDate= endDate;
			//期初余额
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
	 * 根据付款通知单ID查询付款通知单及其明细
	 * @param payNoticeId 付款通知单ID
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
			//业务类型为卖出回购
			if(info.getTranstypeid() == CRAconstant.CraTransactionType.REPURCHASE_NOTIFY)
			{
				TransPayNoticeDetailDao detailDao = new TransPayNoticeDetailDao();
				coll = detailDao.searchTransPayNoticeDetailByNoticeID(id);
				info.setColl(coll);
			}	
             //业务类型为卖出卖断
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
