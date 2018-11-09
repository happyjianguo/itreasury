package com.iss.itreasury.craftbrother.apply.bizlogic;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ejb.SessionContext;

import com.iss.itreasury.craftbrother.apply.dao.CraLoanContentDao;
import com.iss.itreasury.craftbrother.apply.dao.CreditCheckDAO;
import com.iss.itreasury.craftbrother.apply.dataentity.CraLoanContentInfo;
import com.iss.itreasury.craftbrother.credit.dao.CreditSettingDAO;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditSettingInfo;
import com.iss.itreasury.craftbrother.util.CRANameRef;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.query.bizlogic.QueryCapitalLandingFormBean;
import com.iss.itreasury.securities.query.dataentity.QueryCapitalLandingFormParam;
import com.iss.itreasury.securities.setting.dao.SEC_CounterPartBankAccountDAO;
import com.iss.itreasury.securities.setting.dao.SEC_CounterPartDAO;
import com.iss.itreasury.securities.setting.dataentity.CounterPartBankAccountInfo;
import com.iss.itreasury.securities.setting.dataentity.CounterPartInfo;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.*;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.*;
import com.iss.system.dao.PageLoader;
public class ApplyBiz 
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	
	/**
	 *申请书的保存操作
	 * @throws Exception 
	*/
	public long save(ApplyInfo info) throws Exception
	{
		Connection conn = null;

		//DeliveryOrderServiceOperation dso = new DeliveryOrderServiceOperation();
		//UsableCreditLineOfSecuritiesInfo creditInfo1 = new UsableCreditLineOfSecuritiesInfo();
		//UsableCreditLineOfSecuritiesInfo creditInfo2 = new UsableCreditLineOfSecuritiesInfo();
		long lID = -1;
		long ret = -1;
		String strError = "";
		String applyCode = "";
        try
        {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			SEC_ApplyDAO dao = new SEC_ApplyDAO(conn);
			CreditSettingInfo creditInfo =null;
			CreditSettingDAO cdao = new CreditSettingDAO(conn);
			
			if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN || info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
			{
				//资金拆借额度检查
				log4j.print("资金拆借额度检查");
				strError = dao.checkCapitalLandingCreditExtension(info.getCounterpartId(), info.getTransactionTypeId(), info.getPledgeSecuritiesAmount(), info.getId());
				if (strError != null && strError.length() > 0)
				{
					throw new SecuritiesException("Sec_E180", strError, null);
				}
			}
			else if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY
					|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK)
	        {
				System.out.println("资产转让合同买入（回购）授信检查");
	        	CreditCheckDAO checkDao = new CreditCheckDAO();
	        	
	        	//modify by xwhe
	        	CreditSettingDAO creditDao =new CreditSettingDAO();
				
				String counterpartName = NameRef.getCounterpartNameByID(info.getCounterpartId());
				
				creditInfo = creditDao.findCreditAmountByDate(info.getTransactionStartDate(),info.getTransactionEndDate(),info.getCounterpartId(),CRANameRef.getPartenerInfo(info.getOfficeId()).getClientID(),1,info.getTransactionTypeId(),counterpartName,"");
	        	strError = checkDao.checkAttormCredit(info);
	        	System.out.println("异常信息"+strError);
	        	if (strError != null && strError.length() > 0)
				{
					throw new SecuritiesException("Sec_E180", strError, null);
				}
	        }
			// 合同卖出（回购）、合同卖出（买断）
			else if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY 
					|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
			{
				System.out.println("资产转让合同卖出（回购,买断）授信检查");
				CreditSettingDAO creditDao =new CreditSettingDAO();
				//CreditSettingInfo creditInfo =null;
				
				String counterpartName = NameRef.getCounterpartNameByID(info.getCounterpartId());
				
				creditInfo = creditDao.findCreditAmountByDate(info.getTransactionStartDate(),info.getTransactionEndDate(),info.getCounterpartId(),CRANameRef.getPartenerInfo(info.getOfficeId()).getClientID(),2,info.getTransactionTypeId(),counterpartName,"");
				if(creditInfo != null)
				{
					if(info.getAmount() > 10000*creditInfo.getAmount())
					{
						strError = "该资产回购申请的回购金额"+DataFormat.formatDisabledAmount(info.getAmount())+"元，超过了交易对手\""
							+ counterpartName + "\"对财务公司对于"+SECConstant.BusinessType.CAPITAL_REPURCHASE.getName(info.getTransactionTypeId())
							+"交易类型授信的"+DataFormat.formatDisabledAmount(10000*creditInfo.getAmount(),2)+"元的总授信额度，请修改后再提交";
						throw new SecuritiesException("Sec_E180", strError, null);
					}
				}
			}
			if (info.getId() < 0)
			{
				try
				{
					
					HashMap map = new HashMap();
					map.put("officeID",String.valueOf(info.getOfficeId())); 
					map.put("currencyID",String.valueOf(info.getCurrencyId()));
					map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
					map.put("transTypeID",String.valueOf(info.getTypeId()));
					map.put("actionID",String.valueOf(info.getActionTypeId()));
					map.put("subActionID",String.valueOf(info.getTransactionTypeId()));
					applyCode = CreateCodeManager.createCode(map);
					info.setCode(applyCode);
					//将下一个审批级别设为1
					//info.setNextCheckLevel(1);
					info.setCreditId(creditInfo.getID());
					lID = dao.add(info);
					//保存原贷款合同信息
					CraLoanContentDao craDao = new CraLoanContentDao(conn);
					if(info.getCraColl() != null)
					{
						Iterator it = info.getCraColl().iterator();
						while(it.hasNext())
						{
							CraLoanContentInfo craInfo = (CraLoanContentInfo)it.next();
							craInfo.setApplyId(lID);
							craDao.setUseMaxID();
							craDao.add(craInfo);
						}
					}
					//保存原贷款合同信息结束
					if(info.getCreditId() >0 ){              
	                	ret = cdao.updateStatus(info.getCreditId(),CRAconstant.TransactionStatus.USED); 
	                	System.out.print("修改的授信ID是:"+ret);
	                }
				}
				catch (ITreasuryDAOException e)
				{
					throw new SecuritiesDAOException(e);
				}
			}
			else
			{
				try
				{
					dao.update(info);
					if(info.getCraColl() != null)
					{
						//更新合同信息(先删除原信息)
						Collection craColl = null;
						CraLoanContentDao craDao = new CraLoanContentDao(conn);
						craColl = findByApplyID(info.getId());
						Iterator it = craColl.iterator();
						while(it.hasNext()){
							CraLoanContentInfo craInfo = (CraLoanContentInfo)it.next();
							craDao.deletePhysically(craInfo.getId());
						}
						//更新合同信息结束
						//保存原贷款合同信息
						CraLoanContentDao craDaoS = new CraLoanContentDao(conn);
						Iterator itSave = info.getCraColl().iterator();
						while(itSave.hasNext())
						{
							CraLoanContentInfo craInfoSave = (CraLoanContentInfo)itSave.next();
							craInfoSave.setApplyId(info.getId());
							craDaoS.setUseMaxID();
							craDaoS.add(craInfoSave);
						}
						//保存原贷款合同信息结束
					}
				}
				catch (ITreasuryDAOException e)
				{
					throw new SecuritiesDAOException(e);
				}
				lID = info.getId();
			}
		
        }catch(Exception ex){
				ex.printStackTrace();
				throw ex;   
		}	
       finally
       {
			conn.commit();
			if(conn != null) {
				conn.close();
				conn = null;
			}	
       }
        
		return lID;
	}
	/**
	 * 贷款合同信息的保存操作
	 */
	public void saveContract(CraLoanContentInfo cinfo) throws Exception
	{
		CraLoanContentDao dao = new CraLoanContentDao();
		try
		{
			dao.setUseMaxID();
			dao.add(cinfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 贷款合同信息删除操作
	 */
	public void deleteContract(long lID) throws Exception
	{
		CraLoanContentDao dao = new CraLoanContentDao();
		try
		{
			dao.updateStatus(lID, SECConstant.NoticeFormStatus.CANCELED);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 *申请书的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		try
		{
			dao.delete(lID);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 *申请书的审核操作
	*/
	public void check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, SecuritiesException, Exception
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();

		long lCount = 0;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		long[] lApprovalContentIDList;
		String strSQL = "";

		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();

		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		
		//zpli add 2005-09-14
		long lOfficeID=ATInfo.getOfficeID();
		long lCurrencyID=ATInfo.getCurrencyID();
		////
		String sOpinion = ATInfo.getOpinion();

		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalBiz appbiz = new ApprovalBiz();

		lApprovalContentIDList = ATInfo.getApprovalContentIDList();

		if (lApprovalContentIDList.length > 0)
		{
			try
			{
				//获得ApprovalID
				if (lApprovalID <= 0)
				{
					//zpli modify 2005-09-14
					lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID);
					//lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID);
				}
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}

			//处理审批意见
			if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
			{
				//审批意见状态
				lStatusID = Constant.RecordStatus.VALID;
				//审批操作类型
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//审批完成后需要做的操作
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.RETURN;
			}
			ATInfo.setApprovalID(lApprovalID);
			ATInfo.setResultID(lResultID);
			ATInfo.setStatusID(lStatusID);

			lCount = lApprovalContentIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lApprovalContentIDList[i] > 0)
				{
					/*
					if (ATInfo.getNextLevel() <= 0)
					{
					    ApplyInfo aInfo = new ApplyInfo();
					    try
					    {
					        aInfo = (ApplyInfo)dao.findByID(lApprovalContentIDList[i],aInfo.getClass());
					    } 
					    catch (ITreasuryDAOException e2)
					    {
					        e2.printStackTrace();
					    }
					    ATInfo.setNextLevel(aInfo.getNextCheckLevel());
					}
					*/
					ATInfo.setApprovalContentID(lApprovalContentIDList[i]);
					Log.print("ATInfo.getApprovalContentID()=" + ATInfo.getApprovalContentID());
				}
				else
				{
					break;
				}
				//审核申请书
				dao.check(ATInfo);

				log4j.debug("saveApprovalTracing begin");
				try
				{
					appbiz.saveApprovalTracing(ATInfo);
				}
				catch (Exception e)
				{
					log4j.error("saveApprovalTracing fail");
					e.printStackTrace();
				}
				log4j.debug("saveApprovalTracing end");
			}
		}
	}

	/**
	 *申请书的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, SecuritiesException,Exception
	{
		Connection conn = null;
		try
		{
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			SEC_ApplyDAO dao = new SEC_ApplyDAO(conn);
			//删除贷款合同信息
			Collection craColl = null;
			CraLoanContentDao craDao = new CraLoanContentDao(conn);
			craColl = findByApplyID(lID);
			Iterator it = craColl.iterator();
			while(it.hasNext()){
				CraLoanContentInfo craInfo = (CraLoanContentInfo)it.next();
				craDao.delete(craInfo.getId());
			}
			
			dao.delete(lID);
			conn.commit();
			if(conn != null) {
				conn.close();
				conn = null;
			}				
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 *申请书的单笔查询操作
	*/
	public ApplyInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();

		try
		{
			aInfo = (ApplyInfo) dao.findByID(lID, aInfo.getClass());
			//SEC_ApplyForm表中没有的字段
			aInfo.setClientName(NameRef.getClientNameByID(aInfo.getClientId())); //业务单位名称
			aInfo.setCounterpartCode(NameRef.getCounterpartCodeByID(aInfo.getCounterpartId()));//交易对手编号
			aInfo.setCounterpartName(NameRef.getCounterpartNameByID(aInfo.getCounterpartId())); //交易对手名称
			aInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(aInfo.getSecuritiesId())); //证券名称
			aInfo.setAccountNo(NameRef.getAccountNobyAccountID(aInfo.getAccountId())); //资金账户代码
			aInfo.setAccountName(NameRef.getAccountNameById(aInfo.getAccountId())); //资金账户名称
			aInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(aInfo.getAccountId())); //股东账户代码
			aInfo.setStockName(NameRef.getSecuritiesNameByID(aInfo.getStockId())); //转成股票名称
			if (aInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN || aInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
			{
				aInfo = dao.findCounterpartCreditInfo(aInfo);
			}
			if (dao.checkTransactionTypeID(aInfo.getTransactionTypeId()))
			{
				aInfo.setCapitalLandingCreditExtensionMessage(
					dao.checkApplyCreditExtension(aInfo.getSecuritiesId(), Long.valueOf(NameRef.getSecuritiesTypeIDBySecuritiesID(aInfo.getSecuritiesId())).longValue(), aInfo.getAmount()));
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return aInfo;
	}
	/**
	 *申请书贷款合同信息查询
	 */
	public Collection findByApplyID(long lID) throws SecuritiesException
	{	
		CraLoanContentInfo craInfo = new CraLoanContentInfo();
		CraLoanContentDao craDao = new CraLoanContentDao();
		Collection coll = null;
		
		try
		{
			coll = craDao.findByApplyID(lID, craInfo.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return coll;
	}

	/**
	 *申请书的多笔查询操作
	*/
	public Collection findByMultiOption(ApplyQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		Collection c = null;
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		ApprovalBiz appBiz = new ApprovalBiz();
		String strUser = "";
		long lModuleID = -1;
		long lLoanTypeID = -1;
		long lActionID = -1;
		long lApprovalID = -1;
		
		//zpli add 2005-09-14		
		lModuleID=qInfo.getModuleId();
		lLoanTypeID=qInfo.getLoanTypeId();
		lActionID=qInfo.getActionId();
		long lOfficeID=qInfo.getOfficeId();
		long lCurrencyID=qInfo.getCurrencyId();
		////
		
		try
		{
			try
			{
				lApprovalID = qInfo.getApprovalId();
				//获得ApprovalID
				if (lApprovalID <= 0)
				{
					//zpli modify 2005-09-14
					lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(), 1,qInfo.getOfficeId(),qInfo.getCurrencyId());
					//lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(), 1);
				}
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}
			try
			{
				//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
				//zpli modify 2005-09-14
				strUser = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,qInfo.getUserId());
				//strUser = appBiz.findTheVeryUser(lApprovalID, qInfo.getUserId());
			}
			catch (Exception e2)
			{
				log4j.error("findTheVeryUser fail");
				e2.printStackTrace();
			}
			qInfo.setStrUser(strUser);
			c = dao.findByMultiOption(qInfo);
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesException("", e);
		}
		return c;
	}

	/**
	 *申请书下的投标区间保存操作
	*/
	public long saveBidRange(BidRangeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_BidRangeDAO dao = new SEC_BidRangeDAO();
		SEC_ApplyDAO applydao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();
		long lID = -1;

		try
		{
			dao.setUseMaxID();
			lID = dao.add(info);

			/*
			aInfo.setId(info.getApplyFormId());
			//aInfo.setAmount(dao.findMaxAmount(info.getApplyFormId()));
			if (aInfo.getAmount() > 0)
			{
				applydao.update(aInfo);
			}
			*/
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return lID;
	}

	/**
	 *申请书下的投标区间查询操作
	*/
	public Collection findBidRangeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		Collection c = null;
		SEC_BidRangeDAO dao = new SEC_BidRangeDAO();
		try
		{
			c = dao.findByApplyID(lApplyID);
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesException("", e);
		}
		return c;
	}

	/**
	 *申请书下的投标区间删除操作
	*/
	public void deleteBidRange(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		long lCount = 0;
		SEC_BidRangeDAO dao = new SEC_BidRangeDAO();
		SEC_ApplyDAO applydao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();
		BidRangeInfo bInfo = new BidRangeInfo();
		try
		{
			if (lIDList[0] > 0)
			{
				bInfo = (BidRangeInfo) dao.findByID(lIDList[0], bInfo.getClass());
			}
			lCount = lIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lIDList[i] > 0)
				{
					dao.delete(lIDList[i]);
				}
			}
			/*
			if (bInfo.getApplyFormId() > 0)
			{
				aInfo.setId(bInfo.getApplyFormId());
				//aInfo.setAmount(dao.findMaxAmount(bInfo.getApplyFormId()));
				if (aInfo.getAmount() > 0)
				{
					applydao.update(aInfo);
				}
			}
			*/
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 *申请书下的债券种类保存操作
	*/
	public long saveBondType(BondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_BondTypeDAO dao = new SEC_BondTypeDAO();
		long lID = -1;

		try
		{
			if (info.getId() < 0)
			{
				dao.setUseMaxID();
				info.setStatusId(1);
				lID = dao.add(info);
			}
			else
			{
				info.setStatusId(1);
				dao.update(info);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return lID;
	}

	/**
	 *申请书下的债券种类查询操作
	*/
	public Collection findBondTypeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		Collection c = null;
		SEC_BondTypeDAO dao = new SEC_BondTypeDAO();
		try
		{
			c = dao.findByApplyID(lApplyID);
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesException("", e);
		}
		return c;
	}

	/**
	 *申请书下的债券种类删除操作
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		long lCount = 0;
		SEC_BondTypeDAO dao = new SEC_BondTypeDAO();
		try
		{
			lCount = lIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lIDList[i] > 0)
				{
					dao.delete(lIDList[i]);
				}
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	* 转移申请书管理人权限，支持批量转移
	* @param lID
	* @param lUserID
	* @throws java.rmi.RemoteException
	* @throws SecuritiesException
	*/
	public void transferApplyRight(long[] lID, long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();

		for (int i = 0; i < lID.length; i++)
		{
			try
			{
				if (lID[i] > 0 && lUserID > 0)
				{
					ApplyInfo info = new ApplyInfo();
					info.setId(lID[i]);
					info.setInputUserId(lUserID);
					dao.update(info);
				}
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new SecuritiesException("", e);
			}
		}
	}
	
	/**
	 * 通过ID返回交易对象资料 
	 * @param counterPartID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CounterPartInfo findCounterPartById(long counterPartId) throws SecuritiesDAOException
	{
		log4j.debug("lhj debug info CounterPartBean :: findCounterPartByID start!");
		log4j.debug("--lhj dubug info 交易对象ID == " + counterPartId);
		CounterPartInfo counterPartInfo = new CounterPartInfo();
		SEC_CounterPartDAO counterPartDAO = new SEC_CounterPartDAO();
		try
		{
			log4j.debug("----lhj dubug info  1111111111");
			counterPartInfo = (CounterPartInfo) counterPartDAO.findByID(counterPartId, counterPartInfo.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}

		log4j.debug("lhj debug info CounterPartBean :: findCounterPartByID end!");
		return counterPartInfo;

	}
	
	/**
	 * 通过ID返回交易对象开户行资料 
	 * @param counterPartBankAccountID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CounterPartBankAccountInfo findCounterPartBankAccountById(long counterPartBankAccountId) throws SecuritiesDAOException
	{
		log4j.debug("lhj debug info CounterPartBean :: findCounterPartBankAccountById start!");
		CounterPartBankAccountInfo counterPartBankAccountInfo = new CounterPartBankAccountInfo();
		SEC_CounterPartBankAccountDAO counterPartBankAccountDAO = new SEC_CounterPartBankAccountDAO();

		try
		{
			counterPartBankAccountInfo = (CounterPartBankAccountInfo) counterPartBankAccountDAO.findByID(counterPartBankAccountId, counterPartBankAccountInfo.getClass());
			log4j.debug("lhj debug info CounterPartBean :: findCounterPartBankAccountById end!");
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}

		return counterPartBankAccountInfo;

	}
	
	/**
	 * 申请单查询,返回一个PageLoader
	 * @author 王怡
	 * @param QueryCapitalLandingFormParam 业务通知单查询页面条件
	 * @throws SecuritiesException
	 */
	public PageLoader queryCapitalLandingForm(QueryCapitalLandingFormParam queryParam)
		throws SecuritiesException {
		PageLoader queryPageLoader = null;
		QueryCapitalLandingFormBean queryBean = new QueryCapitalLandingFormBean();

		log4j.debug("queryDelegation debug info ::::queryCapitalLandingForm start");
		queryPageLoader =
		queryBean.queryCapitalLandingFormInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::queryCapitalLandingForm end");
		return queryPageLoader;
	}
	  /**
     * added by xwhe 2007/06/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(ApplyInfo info)
			throws java.rmi.RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		try {
			
			lReturnId = save(info);
			SEC_ApplyDAO dao = new SEC_ApplyDAO();
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(lReturnId));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturnId);
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
			dao.update(lReturnId, info.getInputUserId(),SECConstant.ApplyFormStatus.APPROVALING);
			
		} catch (Exception e) {
			throw new IRollbackException(context, e.getMessage(), e);
		}

		return lReturnId;
	} 
    
    /**
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long examinePass(ApplyInfo info)
	throws RemoteException, IRollbackException{
   	 long lReturnId = -1;
		 long lTransDiscountContractID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				SEC_ApplyDAO dao = new SEC_ApplyDAO();
   				//TransDiscountContractInfo	appInfo=dao.findByID(info.getId());
                //lLoanTypeID=appInfo.getTypeID();
   				//lLoanTypeID=appInfo.getSubTypeId();
   				//long status=appInfo.getStatusId() ;
   				

   				//---- added by xwhe 2007/09/12 审批流 begin
   				
   				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
   				InutParameterInfo returnInfo = new InutParameterInfo();
   				
   				//将业务记录置入pinfo,转换成标准map传递到审批流引擎
   				inutParameterInfo.setDataEntity(info);
   				
   				//提交审批
   				returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
   				
   				//如果是最后一级,且为审批通过,更新状态为已审批
   				if(returnInfo.isLastLevel())
   				{	
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							SECConstant.ApplyFormStatus.CHECKED);
   					
   					//审核完成以后,生成合同	
   					if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY
   							|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK){
   					dao.doAfterCheckOver(lTransDiscountContractID);
   				}
   				}
   				//如果是最后一级,且为审批拒绝,更新状态为已保存
   				else if(returnInfo.isRefuse())
   				{
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							SECConstant.ApplyFormStatus.SUBMITED);
   				}	
   		 
   	 }
   	 catch(Exception e){
   		 throw new IRollbackException(context, e.getMessage(), e); 
   	 }
   	 return lReturnId;
    }
    /**
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long updateApplyAndApprovalInit(ApplyInfo info)
			throws RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		try {
			lReturnId = save(info);
			SEC_ApplyDAO dao = new SEC_ApplyDAO();
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(info.getId()));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+info.getId());
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
			dao.update(info.getId(), info.getInputUserId(),SECConstant.ApplyFormStatus.APPROVALING);
			
		} catch (Exception e) {
			throw new IRollbackException(context, e.getMessage(), e);
		}

		return lReturnId;
	}
    /**
	 * Modify by xwge date 2007/09/10
	 * 审批流：取消审批方法
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(ApplyInfo info)throws java.rmi.RemoteException, SecuritiesException
	{
		long lReturn = -1;
		long ret = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		AttornmentApplyDao adao = new AttornmentApplyDao();
		
		boolean ifUsed=false;
		if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY
				|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK){
		try{
			ifUsed=dao.checkStatuID(info.getId());
		}catch (SecuritiesDAOException e) {
			throw new SecuritiesException();
		}
		if(ifUsed){
			throw new SecuritiesException("请先取消合同,再取消申请!",null);
		}
		}
		if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY||
		    info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
		{
		try{
				ifUsed=dao.checkAttornStatuID(info.getId());
			}catch (SecuritiesDAOException e) {
				throw new SecuritiesException();
			}
			if(ifUsed){
				throw new SecuritiesException("请先取消贷款资产转让申请,再取消申请!",null);
			}
		}
		
		try
		{
			
			dao.cancelContract(info.getId());//取消合同的保存状态
		    dao.updateAttornByID(info.getId());//取消贷款转让申请的保存状态
			
			//取消审批
			lReturn = dao.update(info.getId(), info.getInputUserId(), SECConstant.ApplyFormStatus.SUBMITED);
			
			if(lReturn > 0){
				//将审批记录表内的该交易的审批记录状态置为无效
				if(inutParameterInfo.getApprovalEntryID()>0)
				{
					FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
				}
			}
		
		}
		catch (Exception e)
		{
			throw new SecuritiesException();
		}
		return lReturn;
	}

}
