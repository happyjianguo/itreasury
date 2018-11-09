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
 *	代收通知单
 */
public class TransferReceiveClientNoticeBiz {

	private Log4j logger = new Log4j(Constant.ModuleType.CRAFTBROTHER, TransferReceiveClientNoticeBiz.class);
	/**
	 * 创建代收通知单，用于新增代收通知单的初始化
	 */
	public TransferNoticeInfo createReceiveNoticeInfo(TransferContractInfo contractInfo) throws IException
	{
		TransferNoticeInfo newNoticeInfo = null;
		try{
			//开机日为通知单的默认执行日
			Timestamp dtOpenDate 
				= Env.getSystemDate(contractInfo.getOfficeId(),contractInfo.getCurrencyId());
			
			//********创建通知单基本信息***************//
			newNoticeInfo = new TransferNoticeInfo();
			newNoticeInfo.setOfficeId(contractInfo.getOfficeId());
			newNoticeInfo.setCurrencyId(contractInfo.getCurrencyId());
			newNoticeInfo.setCracontractId(contractInfo.getId());
			newNoticeInfo.setCracontractCode(contractInfo.getContractCode());
			newNoticeInfo.setDtclearInterest(dtOpenDate);
			newNoticeInfo.setTranstypeid(CRAconstant.CraTransactionType.BREAK_NOTIFY);
			newNoticeInfo.setAgentType(CRAconstant.AgentType.AMOUNT);//默认为只收本金
	
			//查询转让合同明细
			ContractDetailDao contractDetailDao = new ContractDetailDao();
			
			Collection colContractDetails 
				= contractDetailDao.queryContractDetailInfos(contractInfo.getId());
			
			TransferNoticeDetailInfo aNoticeDetailInfo = null;
			double sumAmount = 0.0; //本金累计，用于计算整个通知单的代收本金
			double sumInterest = 0.0; //利息累计
			
			Collection colNoticeDetails = new ArrayList();
			ContractdetailInfo aContractDetailInfo = null;
			
			//********创建通知单明细信息***************//
			if(colContractDetails!=null){
				Iterator it = colContractDetails.iterator();
				
				while(it.hasNext()){
					aContractDetailInfo = (ContractdetailInfo)it.next();
					
					aNoticeDetailInfo = generateReceiveNoticeDetail(aContractDetailInfo);
					
					//计算该转让合同明细当前的剩余本金
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
					
					aNoticeDetailInfo.setBalance(balance); //转让子合同余额
					aNoticeDetailInfo.setAmount(balance);//通知单本金
					sumAmount = Arithmetic.add(sumAmount, balance);
					
					//统计该转让合同明细的上次结息日
					Timestamp lastClearInterestDate 
						= contractDetailDao.getLastClearInterestDate(aContractDetailInfo);
					
					aNoticeDetailInfo.setLastClearInterest(lastClearInterestDate);
					
					//获得自营贷款放款单的放款利率
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
			throw new IException("创建通知单时出错，" + exp.getMessage());
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
			throw new IException("计算手续费率时出错，" + exp.getMessage(), exp);
		}
	}



	/**
	 * 根据转让合同明细产生代收通知单的收款明细信息
	 * 
	 * @param contractDetailInfo  转让合同明细
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
	 * 此方法通过放款通知单的ID查询该单据的利息
	 * @param lLoanPayNoticeID 放款通知单标识
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
			throw new IException("通过放款通知单的ID查询该单据的利息出错");
		}
		return interestOne;
	}
	
	/**
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
			 * 如果noticeInfo中的InutParameterInfo不为空,则需要提交审批 
			 */
			if(noticeInfo.getInutParameterInfo() != null)
			{
				logger.debug("------提交审批--------");	
				InutParameterInfo tempInfo = noticeInfo.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl() + lReturn);
				tempInfo.setTransID(String.valueOf(lReturn));// 这里保存的是交易ID
				tempInfo.setDataEntity(noticeInfo);
                //提交审批
				FSWorkflowManager.initApproval(noticeInfo.getInutParameterInfo());
				logger.debug("------提交审批成功--------");
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
	 * 方法说明：生成并返回通知单编号(新)
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
	 * 查询代收通知单信息，包括通知单子信息
	 * 
	 * @param noticeID 通知单ID
	 * @return
	 * @throws IException
	 */
	public TransferNoticeInfo findReceiveNoticeInfo(long noticeID) throws IException
	{
		if(noticeID<=0) throw new IException("通知单ID无效：" + noticeID);
		
		TransferNoticeInfo noticeInfo = null;
		
		try{
			//查询通知单基本信息
			TransferNoticeDao baseNoticeDao = new TransferNoticeDao();
			noticeInfo = (TransferNoticeInfo)baseNoticeDao.findByID(noticeID, TransferNoticeInfo.class);
			
			//查询通知单明细
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

			//查询转让合同基本信息
			TransferContractDao contractDao = new TransferContractDao();
			TransferContractInfo contractInfo 
				= contractDao.findTransferContractInfoByID(noticeInfo.getCracontractId());
			
			//查询转让合同明细
			ContractDetailDao contractDetailDao = new ContractDetailDao();
			
			Collection colContractDetails 
				= contractDetailDao.queryContractDetailInfos(noticeInfo.getCracontractId());
			
			//开机日为通知单的默认执行日
			Timestamp dtOpenDate 
				= Env.getSystemDate(contractInfo.getOfficeId(),contractInfo.getCurrencyId());
			
			TransferNoticeDetailInfo aNoticeDetailInfo = null;
			ArrayList newColNoticeDetails=new ArrayList();
						
			//********创建通知单明细信息***************//
			if(colContractDetails!=null){
				Iterator it = colContractDetails.iterator();
				ContractdetailInfo aContractDetailInfo = null;
				
				while(it.hasNext()){
					aContractDetailInfo = (ContractdetailInfo)it.next();
					
					aNoticeDetailInfo 
						= (TransferNoticeDetailInfo) noticeDetailsMap
							.get(aContractDetailInfo.getId()+"");
					
					//如果代收通知单子信息不存在，则根据转让合同子信息作初始化
					if(aNoticeDetailInfo==null || aNoticeDetailInfo.getId()<=0)
					{
						aNoticeDetailInfo = generateReceiveNoticeDetail(aContractDetailInfo);
						aNoticeDetailInfo.setChecked(false);						
					}else{
						//如果代收通知单子信息存在，则需要在info中补充对应的合同信息
						//add by minzhao 2009-08-14
						aNoticeDetailInfo.setLoanContractCode(aContractDetailInfo.getLoanContractCode());
						aNoticeDetailInfo.setBorrowClientName(aContractDetailInfo.getBorrowClientName());
						//add end
						aNoticeDetailInfo.setChecked(true);
					}
					
					//计算该转让合同明细当前的剩余本金
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
					
					//统计该转让合同明细的上次结息日
					Timestamp lastClearInterestDate 
						= contractDetailDao.getLastClearInterestDate(aContractDetailInfo);
					
					aNoticeDetailInfo.setLastClearInterest(lastClearInterestDate);
					
					//获得自营贷款放款单的放款利率
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
			throw new IException("查询通知单时出错，" + exp.getMessage());
		}
		
		return noticeInfo;
	}
}
