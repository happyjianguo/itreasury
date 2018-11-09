package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.ReceiveTransferNoticeDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferNoticeDetailDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetailConditionInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetialResultInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeDetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * 收款通知单代收成员单位BIZ
 * @author zcwang
 * @version 1.1
 */
public class ReceiveTransferNoticeBiz {

		private ReceiveTransferNoticeDao noticeDao;
		private Log4j log = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
		public ReceiveTransferNoticeBiz() throws IException
		{
			noticeDao = new ReceiveTransferNoticeDao();
		}
	

		/**
		 * @deprecated 代收不再使用该方法
		 * 
		 * 查询收款通知单,转让合同明细组装数据
		 * @param conditionInfo
		 * @return
		 * @throws IException
		 */
		public Collection findContractOrNoticeDetial(ContractAndNoticeDetailConditionInfo conditionInfo) throws IException
		{
			Collection coll = null;
			try
			{
				coll = noticeDao.findContractOrNoticeDetial(conditionInfo);
			}
			catch(IException e)
			{
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			
			return coll;
		}

		/**
		 *  查询收款通知单,转让合同明细组装数据（错误，组装）
		 * @param conditionInfo
		 * @return
		 * @throws IException
		 */
		public Collection findContractOrNoticeDetialForFalse(ContractAndNoticeDetailConditionInfo conditionInfo,Collection receiveNoticeDetailColl) throws IException
		{
			Collection coll = null;
			try
			{
				coll = noticeDao.findContractOrNoticeDetial(conditionInfo);
				if(coll!=null && coll.size()>0 && receiveNoticeDetailColl!=null && receiveNoticeDetailColl.size()>0)
				{
					Iterator it = coll.iterator();
					while(it.hasNext())
					{
						ContractAndNoticeDetialResultInfo detailInfo =(ContractAndNoticeDetialResultInfo) it.next();
						if(receiveNoticeDetailColl!=null)
						{
							Iterator it1 = receiveNoticeDetailColl.iterator();
							while(it1.hasNext())
							{
								TransferNoticeDetailInfo noticeDetailInfo = (TransferNoticeDetailInfo)it1.next();
								if(detailInfo.getContractDetailID()==noticeDetailInfo.getContractDetailID())
								{
									detailInfo.setChecked(true);
									detailInfo.setBalance(noticeDetailInfo.getAmount());
									detailInfo.setInterest(noticeDetailInfo.getInterest());
									detailInfo.setRate(noticeDetailInfo.getRate());
									detailInfo.setPayAccountID(noticeDetailInfo.getPayAccountID());
								}
							}
						}
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
		 * 方法说明：生成并返回通知单编号(新)
		 * add by xwhe 2009-7-2
		 * @param lOfficeID
		 * @param lCurrencyID
		 * @return 
		 * @throws SQLException
		 */
		public String getNewTransactionNo(long lOfficeID, long lCurrencyID) throws IException, Exception
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
		 * @deprecated 卖出卖断不再使用此方法
		 * 卖出卖断收款仍在使用
		 * 
		 * 信贷转让通知单保存
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
				try
				{
					ReceiveTransferNoticeDao receiveNoticeDao = new ReceiveTransferNoticeDao(con);
					if(noticeInfo.getId()>0)
					{
						receiveNoticeDao.update(noticeInfo);
						TransferNoticeDetailDao detailDao = new TransferNoticeDetailDao(con);
						
						detailDao.deleteTransferNoticeDetailByNoticeID(noticeInfo.getId());
						Collection coll = noticeInfo.getColl();
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
						noticeInfo.setNoticeCode(this.getTransferNoticeCode(noticeInfo.getOfficeId(), noticeInfo.getCurrencyId()));
						lReturn =  receiveNoticeDao.add(noticeInfo);
						Collection coll = noticeInfo.getColl();
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
			finally{
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
		 * 删除收款通知单
		 * @param noticeInfo
		 * @return
		 * @throws IException
		 */
		public long deleteReceiveNotice(TransferNoticeInfo noticeInfo)throws IException
		{
			long lReturn = -1;
			Connection con = null;
			try
			{
			    con = Database.getConnection();
				con.setAutoCommit(false);
				try
				{
					ReceiveTransferNoticeDao receiveNoticeDao = new ReceiveTransferNoticeDao(con);
					if(noticeInfo.getId()>0)
					{
						receiveNoticeDao.update(noticeInfo);
						TransferNoticeDetailDao detailDao = new TransferNoticeDetailDao(con);
						detailDao.deleteTransferNoticeDetailByNoticeID(noticeInfo.getId());
						
					}
					lReturn = noticeInfo.getId();
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
			finally{
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
		 * 根据ID查询信贷转让收款通知单
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public TransferNoticeInfo findNoticeInfoById(long id) throws Exception
		{
			TransferNoticeInfo info = new TransferNoticeInfo();
			try
			{
				info = (TransferNoticeInfo)noticeDao.findByID(id, info.getClass());
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
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
				TransferNoticeInfo noticeInfo = this.findNoticeInfoById(info.getId());
				noticeInfo.setStatusId(CRAconstant.TransactionStatus.SAVE);
				 noticeDao.update(noticeInfo);
	            //将审批记录表内的该交易的审批记录状态置为无效
	            if (inutParameterInfo.getApprovalEntryID() > 0)
	            {
	            	FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
	            }
	            lReturn = info.getId();
			}
			catch(IException e)
			{
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			return lReturn;
		}

}

