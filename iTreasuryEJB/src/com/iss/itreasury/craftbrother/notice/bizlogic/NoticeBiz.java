package com.iss.itreasury.craftbrother.notice.bizlogic;

import java.util.Calendar;
import java.util.Collection;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;

import javax.ejb.SessionContext;

import com.iss.itreasury.craftbrother.craftbrothercontract.bizlogic.CraftbrotherContractBiz;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentContractDao;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.notice.dataentity.*;
import com.iss.itreasury.securities.notice.dao.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.securitiescontract.dao.SecuritiesContractDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.bizdelegation.*;
import com.iss.itreasury.securities.deliveryorder.dataentity.*;
import com.iss.itreasury.securities.deliveryorder.dao.*;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.dao.SEC_NoticeWithSecuritiesDAO;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.transsecurities.dao.Sett_TransSecuritiesDAO;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;

public class NoticeBiz 
{
	private javax.ejb.SessionContext mySessionCtx = null;
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	
	/**
	 *通知单的保存操作
	*/
	public long save(NoticeInfo info) throws java.rmi.RemoteException,SecuritiesException,Exception
	{
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();
	    SecuritiesContractDao securitiesContractDao = new SecuritiesContractDao();
	    String applyCode = "";
	    long lID = -1;
	    if (info.getId() < 0)
	    {
	        try 
			{
	        	HashMap map = new HashMap();
	        	map.put("officeID",String.valueOf(info.getOfficeId()));
	        	map.put("currencyID",String.valueOf(info.getCurrencyId()));
	        	map.put("moduleID",String.valueOf(info.getModuleId()));
	        	map.put("transTypeID",String.valueOf(info.getTypeId()));
	        	map.put("actionID",String.valueOf(info.getActionTypeId()));
	        	map.put("subActionID",String.valueOf(info.getTransactionTypeId()));
	        	applyCode=CreateCodeManager.createCode(map);
				if (info.getContractId() <= 0)
				{
					DeliveryOrderInfo dInfo = new DeliveryOrderInfo();
					//判断交割单的时间戳是否改变,如果改变了就不能保存
					SEC_DeliveryOrderDAO dDao = new SEC_DeliveryOrderDAO();
					//先判断时间戳变过没有
					dInfo = (DeliveryOrderInfo) dDao.findByID(info
							.getDeliveryOrderId(), dInfo.getClass());
					//将页面获得的时间戳赋给交割单info去比较
					dInfo.setTimeStamp(info.getDeliveryOrderTimestamp());
					dDao.checkTimeStamp(dInfo);
					//更新交割单的时间戳
					
					dInfo.setTimeStamp(Env.getSystemDateTime());
					dDao.update(dInfo);
					info.setCode(applyCode);
					//info.setCode("TZ041301001");
					//将下一个审批级别设为1
					info.setNextCheckLevel(1);
					lID = dao.add(info);
					//将交割单的状态改为已使用
					dao.updataDeliveyOrderStatus(info.getDeliveryOrderId(),
							SECConstant.DeliveryOrderStatus.USED);
				}else//如果是合同生成的通知单
				{
					info.setCode(applyCode);
					//info.setCode("TZ041301001");
					//将下一个审批级别设为1
					info.setNextCheckLevel(1);
					lID = dao.add(info);
				}
            } catch (ITreasuryDAOException e) {
				mySessionCtx.setRollbackOnly();            	
            	throw new SecuritiesException("Sec_E131",e);
			} catch (SecuritiesException e1) {
				mySessionCtx.setRollbackOnly();
            	throw e1;
			}catch(IException e)
			{
				e.printStackTrace();
				throw new IException("未关联编码设置",e);
			}

	    }
	    else
	    {
	    	System.out.println("before");
            try {
				dao.update(info);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
            System.out.println("end");
	        lID = info.getId();
	    }
	    return lID;
	}
	
	/**
	 *通知单的多笔查询操作(和交割单关联)
	*/
	public Collection findByMultiOption(NoticeQueryInfo qInfo) throws java.rmi.RemoteException,SecuritiesException
	{
	    Collection c = null;
	    log4j.debug("ejbbefore!");
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();
	    ApprovalBiz biz =  new ApprovalBiz();
	    try {
		    	if(qInfo.getQueryPurpose() == 2)
		    	{
		    	//找到转移权限后可以审批的人，没有转就是当前用户
		    	
		    	//zpli modify 2005-09-19
		    	qInfo.setStrUser(biz.findTheVeryUser(qInfo.getModuleId(),qInfo.getLoanTypeId(),qInfo.getActionId(),qInfo.getOfficeID(),qInfo.getCurrencyID(),qInfo.getNextCheckUserId()));
		    	/*if(qInfo.getApprovalId()>=0)
		    	{	 
		    		qInfo.setStrUser(biz.findTheVeryUser(qInfo.getModuleId(),qInfo.getLoanTypeId(),qInfo.getActionId(),qInfo.getOfficeID(),qInfo.getCurrencyID(),qInfo.getNextCheckUserId()));
		    	}
		    	else
		    	{
		    		qInfo.setStrUser(biz.findTheVeryUser(qInfo.getModuleId(),qInfo.getLoanTypeId(),qInfo.getActionId(),qInfo.getOfficeID(),qInfo.getCurrencyID(),qInfo.getNextCheckUserId()));
		    	}*/
	    	}
		    c = dao.findByMultiOption(qInfo);
		    log4j.debug("ejbafter!");
        } catch (SecuritiesDAOException e) {
            throw new SecuritiesException("",e);
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
	    return c;
	}

	/**
	 *通知单的多笔查询操作(和合同关联)
	*/
	public Collection findByMultiOption1(NoticeQueryInfo qInfo) throws java.rmi.RemoteException,SecuritiesException
	{
	    Collection c = null;
	    log4j.debug("ejbbefore!");
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();
	    ApprovalBiz biz =  new ApprovalBiz();
	    try {
	    	if(qInfo.getQueryPurpose() ==2)
	    	{
		    	//找到转移权限后可以审批的人，没有转就是当前用户
	    		
	    		//zpli modify 2005-09-19
	    		qInfo.setStrUser(biz.findTheVeryUser(qInfo.getModuleId(),qInfo.getLoanTypeId(),qInfo.getActionId(),qInfo.getOfficeID(),qInfo.getCurrencyID(),qInfo.getNextCheckUserId()));
		    	/*if(qInfo.getApprovalId()>=0)
		    	{
		    		qInfo.setStrUser(biz.findTheVeryUser(qInfo.getApprovalId(),qInfo.getNextCheckUserId()));
		    	}
		    	else
		    	{
		    		qInfo.setStrUser(biz.findTheVeryUser(qInfo.getModuleId(),qInfo.getLoanTypeId(),qInfo.getActionId(),qInfo.getNextCheckUserId()));
		    	}*/
	    		
	    		
	    	}
		    c = dao.findByMultiOption1(qInfo);
		    log4j.debug("ejbafter!");
        } catch (SecuritiesDAOException e) {
            throw new SecuritiesException("",e);
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
	    return c;
	}
	
	/**
	 *通知单的单笔查询操作
	*/
	public NoticeInfo findByID(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();
	    NoticeInfo aInfo = new NoticeInfo();
	    try {
		    aInfo = (NoticeInfo)dao.findByID(lID,aInfo.getClass());
		    //如果是委托理财的发放收回通知单,则要得到已发放(收回)金额
		    if(aInfo.getTransactionTypeId()==SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_PAYMENT_NOTIFY||aInfo.getTransactionTypeId()==SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_DRAWBACK_NOTIFY)
		    {
		    	aInfo = dao.getBuyBackAndReceivedAmount(aInfo);
		    }
        } catch (ITreasuryDAOException e) {
            throw new SecuritiesDAOException(e);
        }
	    return aInfo;
	}
	
	/**
	 *通知单的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();
	    try 
		{
		    dao.delete(lID);
		    //通过通知单id查找交割单id
		    NoticeInfo nInfo = new NoticeInfo();
		    nInfo = (NoticeInfo)findByID(lID);
		    log4j.debug("hehrehhrhehehrherhehdeliveryorderid ==="+nInfo.getDeliveryOrderId());
		    if(nInfo.getDeliveryOrderId()>0)
		    {
		    	//将交割单的状态置为已复核
		    	dao.updataDeliveyOrderStatus(nInfo.getDeliveryOrderId(),SECConstant.DeliveryOrderStatus.CHECKED);
		    }
	        //如果是资金划拨审核完成后的取消操作，则需改变余额操作
	        changeBalance(lID);
	        if(Long.toString(nInfo.getTransactionTypeId()).substring(0,2).equals("73")||Long.toString(nInfo.getTransactionTypeId()).substring(0,2).equals("81"))
	        {
	        	dao.deleteGLEntry(nInfo.getCode());
	        }
	        //如果是有虚拟交割单的业务,则将虚拟交割单的状态置为无效,现在虚拟交割单业务的交易类型
	        //前两位可能为71,73,77,81,85
	        {
	        	String temp = Long.toString(nInfo.getTransactionTypeId()).substring(0,2);
	        	if (temp.equals("71")||temp.equals("73")||temp.equals("77")||temp.equals("81")||temp.equals("85"))
	        	{
	        		SEC_DeliveryOrderDAO dDao = new SEC_DeliveryOrderDAO();
	        		DeliveryOrderInfo dInfo = new DeliveryOrderInfo();
	        		dInfo.setId(nInfo.getDeliveryOrderId());
	        		dInfo.setStatusId(SECConstant.DeliveryOrderStatus.DELETED);
	        		dDao.update(dInfo);
	        	}
	        }
		} catch (ITreasuryDAOException e) {
            throw new SecuritiesDAOException(e);
        }
	}
	
	/**
	 *通知单的审核操作
	*/
	public void check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		try{
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();

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
		
		//zpli add 2005-09-14
		long lOfficeID=ATInfo.getOfficeID();
		long lCurrencyID=ATInfo.getCurrencyID();
		////
		
		System.out.println("lApprovalid===="+lApprovalID);
		long lUserID = ATInfo.getUserID();
		String sOpinion = ATInfo.getOpinion();

		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalBiz appbiz = new ApprovalBiz();
		
		lApprovalContentIDList = ATInfo.getApprovalContentIDList();
		
		if (lApprovalContentIDList.length > 0)
		{		
		    try 
			{   
		    	if(lApprovalID <=0 )
		    	{
		    		//获得ApprovalID
		    		//zpli modify 2005-09-14
		    		lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
		    		//lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID);
		    	}
		    } catch (Exception e1) {
	            log4j.error("getApprovalID fail");
	            e1.printStackTrace();
	        }
		        
		    lCount = lApprovalContentIDList.length;

		    for(int i=0; i<lCount; i++)
		    {
			    if (lApprovalContentIDList[i] > 0)
		        {
			        info.setApprovalContentID(lApprovalContentIDList[i]);
			        ATInfo.setApprovalContentID(lApprovalContentIDList[i]);
			    }
			    else
			    {
			        break;
			    }

			    //审核申请书
		        dao.check(ATInfo);
				//处理审批意见
		        if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
				{				
				    //审批意见状态
					lStatusID = Constant.RecordStatus.INVALID;
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
				System.out.println("lApprovalid===="+lApprovalID);
				info.setApprovalID(lApprovalID);			
				info.setModuleID(lModuleID);
				info.setLoanTypeID(lLoanTypeID);
				info.setActionID(lActionID);
				//info.setApprovalContentID(lApprovalContentID);
				//info.setSerialID(lSerialID);
				info.setUserID(lUserID);
				info.setNextUserID(lNextUserID);
				info.setOpinion(sOpinion);
				info.setResultID(lResultID);
				info.setStatusID(lStatusID);
				
				info.setCurrencyID(lCurrencyID);
				info.setOfficeID(lOfficeID);
				
				log4j.debug("saveApprovalTracing begin");
				try {
		            appbiz.saveApprovalTracing(info);
		        } catch (Exception e) {
		            log4j.error("saveApprovalTracing fail");
		            e.printStackTrace();
		        }
				log4j.debug("saveApprovalTracing end");
		    }
		}
		}catch(SecuritiesException e){
			mySessionCtx.setRollbackOnly();
			throw e;
		}
	}
	
	
	/**
	 *通知单的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();
	    try {
	        dao.delete(lID);
        } catch (ITreasuryDAOException e) {
            throw new SecuritiesDAOException(e);
        }
	}
	
	//
	private void changeBalance(long lID) throws ITreasuryDAOException, RemoteException
	{
		//如果是资金划拨审核完成后的取消操作，则需改变余额操作
		System.out.println("changebalance");
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		SEC_NoticeDAO noticeDAO = new SEC_NoticeDAO();
		NoticeInfo noticeInfo = new NoticeInfo();
		//为了获得deliveryid
		noticeInfo = (NoticeInfo) noticeDAO.findByID(lID,noticeInfo.getClass());
		//如果是审核完成
		System.out.println("nextcheckuserid=="+noticeInfo.getNextCheckUserId());
		System.out.println("ididid="+noticeInfo.getDeliveryOrderId());
		if(noticeInfo.getNextCheckUserId() == -2)
		{
			deliveryOrderInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(noticeInfo.getDeliveryOrderId(),deliveryOrderInfo.getClass());
			log4j.debug("deliveryorderinof.gettransactiontypeid()="+deliveryOrderInfo.getTransactionTypeId());
			//如果是资金划拨这一业务类型的
			if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85"))
			{
				DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation();
				try
				{
					log4j.debug("before do delete");
					try
					{
						deliveryOrderServiceOperation.deleteDeliveryOrder(deliveryOrderInfo);
					}
					catch (RemoteException e1)
					{
						e1.printStackTrace();
					}
					log4j.debug("after do delete");
				}
				catch (SecuritiesException e2)
				{
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
	 *委托理财业务通知单新增所选证券信息
	*/
	public long saveNoticeWithSecurities(NoticeWithSecuritiesInfo info) throws java.rmi.RemoteException,SecuritiesException
	{
		SEC_NoticeWithSecuritiesDAO dao = new SEC_NoticeWithSecuritiesDAO();
		SEC_NoticeDAO nDao = new SEC_NoticeDAO();
		
		NoticeInfo nInfo = new NoticeInfo();
		//除此之外还要更新(虚拟)交割单的交易金额
		SEC_DeliveryOrderDAO dDao = new SEC_DeliveryOrderDAO();
		DeliveryOrderInfo dInfo = new DeliveryOrderInfo();

		try
		{
			nInfo = (NoticeInfo)nDao.findByID(info.getNoticeId(),nInfo.getClass());
		}
		catch (ITreasuryDAOException e1)
		{
			e1.printStackTrace();
		}
		long lID = -1;
		if (info.getId() < 0)
		{
			try 
			{
				dao.setUseMaxID();    
				lID = dao.add(info);
			}catch (ITreasuryDAOException e) 
			{
				throw new SecuritiesDAOException(e);
			}
		}
		else
		{  
			System.out.println("before");
			try 
			{
				dao.update(info);
			} catch (ITreasuryDAOException e) 
			{
				throw new SecuritiesDAOException(e);
			}
			System.out.println("end");
			lID = info.getId();
		}
		//获得交易总额
		double secAmount = 0;
		//如果是债券承销的,则直接取faceSumAmount或sumAmount的和,其它则计算
		//if(Long.toString(nInfo.getTransactionTypeId()).substring(0,2).equals("81"))
		if(nInfo.getTransactionTypeId()==SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY)
		{
			secAmount = dao.getSecAmountByNoticeIdForBOND_UNDERWRITING(info.getNoticeId());
		}
		else
		{
			secAmount = dao.getSecAmountByNoticeId(info.getNoticeId());
		}	
		//在通知单里更新它
		nDao.updataNoticeAmount(info.getNoticeId(),secAmount);
		//更新虚拟交割单
		if(nInfo != null)
		{
			dInfo.setId(nInfo.getDeliveryOrderId());
			dInfo.setNetIncome(secAmount);
			try
			{
				dDao.update(dInfo);
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
			}
		}
		return lID;
	}
	
	/**
	 *委托理财业务通知单删除所选证券信息
	*/
	public void deleteNoticeWithSecurities(long[] lIDList) throws java.rmi.RemoteException,SecuritiesException
	{
		long lCount = 0;
		SEC_NoticeWithSecuritiesDAO dao = new SEC_NoticeWithSecuritiesDAO();
		SEC_NoticeDAO nDao = new SEC_NoticeDAO();
		NoticeInfo nInfo = new NoticeInfo();
		//除此之外还要更新(虚拟)交割单的交易金额
		SEC_DeliveryOrderDAO dDao = new SEC_DeliveryOrderDAO();
		DeliveryOrderInfo dInfo = new DeliveryOrderInfo();
		NoticeWithSecuritiesInfo info = new NoticeWithSecuritiesInfo();
		try
		{
			//通过id获得通知单id
			info = (NoticeWithSecuritiesInfo)dao.findByID(lIDList[0],info.getClass());
			nInfo = (NoticeInfo)nDao.findByID(info.getNoticeId(),nInfo.getClass());
		}
		catch (ITreasuryDAOException e1)
		{
			e1.printStackTrace();
		}
		try {
			lCount = lIDList.length;
			for(int i=0; i<lCount; i++)
			{
				if (lIDList[i] > 0)
				{
					dao.delete(lIDList[i]);
				}
			}	   
			if(info != null)
			{
				//获得交易总额
				double secAmount = 0;
				//如果是债券承销的债券收取,则直接取faceSumAmount的和,其它则计算
				if(nInfo.getTransactionTypeId()==SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY)
				{
					secAmount = dao.getSecAmountByNoticeIdForBOND_UNDERWRITING(info.getNoticeId());
				}
				else
				{
					secAmount = dao.getSecAmountByNoticeId(info.getNoticeId());
				}	
				//更新通知单的交易金额
				nDao.updataNoticeAmount(info.getNoticeId(),secAmount);  
				
				if(nInfo.getDeliveryOrderId() > 0)
				{
					//更新虚拟交割单的交易金额
					dInfo.setId(nInfo.getDeliveryOrderId());
					dInfo.setNetIncome(secAmount);
					dDao.update(dInfo);
				}
			}   
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	
	/**
	 *委托理财业务通知单查询所选证券信息
	*/
	public Collection findNoticeWithSecuritiesByNoticeID(long lNoticeID) throws java.rmi.RemoteException,SecuritiesException
	{
		Collection c = null;
		SEC_NoticeWithSecuritiesDAO dao = new SEC_NoticeWithSecuritiesDAO();
		try {
			c = dao.findByNoticeID(lNoticeID);
		} catch (SecuritiesDAOException e) {
			throw new SecuritiesException("",e);
		}
		return c;
	}
	
	/**
	 *债券承销业务通知单查询合同执行情况
	*/
	public Collection findContractWithSecuritiesByContractID(long lContractID) throws java.rmi.RemoteException,SecuritiesException
	{
		Collection c = null;
		SEC_NoticeWithSecuritiesDAO dao = new SEC_NoticeWithSecuritiesDAO();
		try {
			c = dao.findByContractID(lContractID);
		} catch (SecuritiesDAOException e) {
			throw new SecuritiesException("",e);
		}
		return c;
	}
	 /**
     * added by xwhe 2007/06/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(NoticeInfo info)
			throws java.rmi.RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		CraftbrotherContractBiz biz = new CraftbrotherContractBiz(); 
		try {
			
			lReturnId = save(info);
			SEC_NoticeDAO dao = new SEC_NoticeDAO();
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(lReturnId));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturnId);
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
			dao.update(lReturnId, info.getInputUserId(),SECConstant.NoticeFormStatus.APPROVALING);
			
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
    public long updateApplyAndApprovalInit(NoticeInfo info)
    throws java.rmi.RemoteException, IRollbackException {
		long lReturnId = -1;
		try {
	
			SEC_NoticeDAO dao = new SEC_NoticeDAO();
			lReturnId = save(info);
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(info.getId()));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+info.getId());
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
			dao.update(info.getId(),info.getInputUserId(),SECConstant.NoticeFormStatus.APPROVALING);
			
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
    public long examinePass(NoticeInfo info)
	throws RemoteException, IRollbackException{
   	 long lReturnId = -1;
		 long lTransDiscountContractID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				SEC_NoticeDAO dao = new SEC_NoticeDAO();
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
   					//合同状态检查
					SecuritiesContractDao secContractDao = new SecuritiesContractDao();

  					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							SECConstant.NoticeFormStatus.CHECKED);

   					//审核完成以后，在结算交易表里面处理业务				  		  
   					//获取当前交易号
   					TransSecuritiesInfo transInfo = new TransSecuritiesInfo();
   				
   					//标志位：是否是新增交易号
   					boolean bNewTransNo = false;

   						//证券业务通知单判断
   						NoticeInfo aInfo = new NoticeInfo();
                        // 工具操作接口类
   						UtilOperation utilOperation = new UtilOperation();
   						Sett_TransSecuritiesDAO setDao = new Sett_TransSecuritiesDAO();
                        //账簿操作接口类 
   						AccountBookOperation accountBookOperation = new AccountBookOperation();
   						aInfo = (NoticeInfo) dao.findByID(info.getId(), aInfo.getClass());
   						if (aInfo.getStatusId() != SECConstant.NoticeFormStatus.CHECKED)
   						{
   							throw new IRollbackException(mySessionCtx, "Sett_E053");
   						}
   						
   						System.out.println("----------是新增交易-------------");
			

   						//保存					
   						System.out.println("----------开始新增交易信息-------------");
   						System.out.println(UtilOperation.dataentityToString(transInfo));
   						long lReturn = -1;
   						transInfo.setOfficeID(aInfo.getOfficeId());
   						transInfo.setCurrencyID(aInfo.getCurrencyId());
   						transInfo.setAmount(aInfo.getNoticeAmount());
   						transInfo.setBankID(aInfo.getCompanyBankId());
   						transInfo.setSecuritiesNoticeID(info.getId());
   						//transInfo.setReceiveAmount();
   						transInfo.setInputUserID(aInfo.getInputUserId());
   						lReturn = setDao.add(transInfo);
   						System.out.println("----------结束新增交易信息-------------");
   						//修改交易的状态为保存。
   						lReturn = setDao.updateStatus(transInfo.getID(), SETTConstant.TransactionStatus.CHECK);
   						//修改证券业务通知单状态,方正项目修改，结算处理通知单后变成已使用
   						//dao.updateStatus(info.getId(), SECConstant.NoticeFormStatus.USED);
   						
   						//保存时的财务处理
   						System.out.println("-------开始保存时的财务处理--------");
   						accountBookOperation.saveSecuritiesDetails(transInfo);
   						System.out.println("-------结束保存时的财务处理--------");
   						//更新资产转让合同状态未已结束状态

   						/**
   						 * 如果是合同卖出购回（回购）通知单，做完通知单后回复贷款合同可转让金额，可再次转让
   						 */
   					if(info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY){
   						//资产转让合同信息
   						SecuritiesContractInfo secContractInfo = new SecuritiesContractInfo();
   						secContractInfo = (SecuritiesContractInfo)secContractDao.findByID(info.getContractId(), SecuritiesContractInfo.class);
   						//资产转让申请信息
   						SEC_ApplyDAO secApplydao = new SEC_ApplyDAO();
   						ApplyInfo applyInfo = new ApplyInfo();
   						applyInfo = (ApplyInfo)secApplydao.findByID(secContractInfo.getApplyId(), ApplyInfo.class);
   						//得到贷款转让申请信息
   						AttornmentApplyDao attornmentApplyDao = new AttornmentApplyDao();
   						AttornmentApplyInfo attornmentApplyInfo = new AttornmentApplyInfo();
   						attornmentApplyInfo = attornmentApplyDao.findAttornmentByRepurchaseID(applyInfo.getId());
   						//根据申请得到申请下所有转让的贷款合同
   						Collection attControctColl  = attornmentApplyDao.findAttornmentContractByApplyId(attornmentApplyInfo.getId());
   						if(attControctColl != null && attControctColl.size() > 0){
   							Iterator it = attControctColl.iterator();
   							while(it.hasNext()){
   								AttornmentContractInfo attornmentContractInfo = (AttornmentContractInfo)it.next();
   								//贷款合同
   								long loanContractID = attornmentContractInfo.getContractId();
   								ContractDao contractDao = new ContractDao();
   								//清空转让贷款合同的lastattornmentamount,leftoversattornmentamount字段，贷款合同可以再次转让
   								contractDao.clearLastOrLeftOverAttormentAmount(loanContractID);
   							}
   						}
   				     }
   					//secContractDao.update(info.getContractId(), lUserID, LOANConstant.ContractStatus.FINISH);   
   					//SECConstant.NoticeFormStatus.USED
   					//以下代码日后删除
   		/*			CraftbrotherContractBiz biz1 = new CraftbrotherContractBiz(); 
   					SecuritiesContractInfo info1 = new SecuritiesContractInfo();
   					info1 = biz1.findByID(info.getContractId());	
   					
   					if(info1.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK&&info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_PAYMENT)
   					{
   					//通知单状态
						dao.update(
	   							lTransDiscountContractID,
	   							lUserID,
	   							SECConstant.NoticeFormStatus.USED);
						//合同状态
						secContractDao.update(info.getContractId(), lUserID, LOANConstant.ContractStatus.FINISH);   					
   					}
   					else if(info1.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY&&(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_PAYMENT||info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYBACK))
   					{
   						//通知单状态
						dao.update(
	   							lTransDiscountContractID,
	   							lUserID,
	   							SECConstant.NoticeFormStatus.USED);
						//合同状态
						secContractDao.update(info.getContractId(), lUserID, LOANConstant.ContractStatus.ACTIVE);   
   					}
   					else if(info1.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY&&info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_CAPITAL)
   					{
   						//通知单状态
						dao.update(
	   							lTransDiscountContractID,
	   							lUserID,
	   							SECConstant.NoticeFormStatus.USED);
						//合同状态
						secContractDao.update(info.getContractId(), lUserID, LOANConstant.ContractStatus.FINISH);     						
   					}
   					else if(info1.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY&&info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_PAYBACK)
   					{
   						//通知单状态
						dao.update(
	   							lTransDiscountContractID,
	   							lUserID,
	   							SECConstant.NoticeFormStatus.USED);
						//合同状态
						secContractDao.update(info.getContractId(), lUserID, LOANConstant.ContractStatus.FINISH);      						
   					}*/
   					
   					
   				}
   				//如果是最后一级,且为审批拒绝,更新状态为已保存
   				else if(returnInfo.isRefuse())
   				{
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							SECConstant.NoticeFormStatus.SUBMITED);
   				}	
   		 
   	 }
   	 catch(Exception e){
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
	public long cancelApproval(NoticeInfo info)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		long result =-1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SEC_NoticeDAO dao = new SEC_NoticeDAO();
		
		try
		{
//			// 合同状态检查
			SecuritiesContractDao secContractDao = new SecuritiesContractDao();
//			SecuritiesContractInfo secContractInfo = (SecuritiesContractInfo) secContractDao
//					.findByID(info.getContractId(),
//							SecuritiesContractInfo.class);
//			long lStatus = secContractInfo.getStatusId();
//
//			if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY
//					|| info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY) {
//				
//				if (lStatus != SECConstant.ContractStatus.FINISH) {
//					// mySessionCtx.setRollbackOnly();
//					throw new IRollbackException(mySessionCtx, "合同["
//							+ secContractInfo.getCode() + "]状态为\""
//							+ SECConstant.ContractStatus.getName(lStatus)
//							+ "\",取消通知单失败");
//				}
//				
//			} else
			if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY
					|| info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY) {
//				if (lStatus != SECConstant.ContractStatus.ACTIVE) {
//					// mySessionCtx.setRollbackOnly();
//					throw new IRollbackException(mySessionCtx, "合同["
//							+ secContractInfo.getCode() + "]状态为\""
//							+ SECConstant.ContractStatus.getName(lStatus)
//							+ "\",取消通知单失败");
//				}
				//查询合同下的购回通知单,先取消购回通知单
				SEC_NoticeDAO snDao = new SEC_NoticeDAO();
				NoticeQueryInfo ncInfo = new NoticeQueryInfo();
				Collection nColl = null;
				if(info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY){
					nColl = snDao.findByMultiOption2(info.getContractId(),SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY);
				}else{
					nColl = snDao.findByMultiOption2(info.getContractId(),SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY);
				}
				if(nColl != null){
					Iterator it = nColl.iterator();
				    if(it.hasNext()){
				    	NoticeInfo nInfo = (NoticeInfo)it.next();
				    	if(nInfo.getStatusId() == SECConstant.NoticeFormStatus.APPROVALING || nInfo.getStatusId() == SECConstant.NoticeFormStatus.APPROVED || nInfo.getStatusId() == SECConstant.NoticeFormStatus.USED){
				    		throw new IRollbackException(mySessionCtx, "请先取消"+SECConstant.BusinessType.CAPITAL_REPURCHASE.getName(nInfo.getTransactionTypeId())+"通知单");
				    	}else{
				    		 dao.delete(nInfo.getId());
				    		 
				    	}
				    }
				}
				
			}
//			else {
//				if (lStatus != SECConstant.ContractStatus.FINISH) {
//					// mySessionCtx.setRollbackOnly();
//					throw new IRollbackException(mySessionCtx, "合同["
//							+ secContractInfo.getCode() + "]状态为\""
//							+ SECConstant.ContractStatus.getName(lStatus)
//							+ "\",取消通知单失败");
//				}
//			}
		   
			//取消审批
			lReturn = dao.update(info.getId(), info.getInputUserId(), SECConstant.NoticeFormStatus.SUBMITED);
			result = dao.updateTrans(info.getId(), info.getInputUserId(), SECConstant.NoticeFormStatus.CANCELED);
			if(lReturn > 0){
				//将审批记录表内的该交易的审批记录状态置为无效
				if(inutParameterInfo.getApprovalEntryID()>0)
				{
					FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
				}
			}
			
			//更新合同状态 ,modified 方正，关机统一处理合同状态
		   //if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY
		   //			|| info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_NOTIFY) {
			//	secContractDao.updateStatus(info.getContractId(),
			//			SECConstant.ContractStatus.ACTIVE);
			//} 
//		   else if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY
//					|| info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY) {
//				secContractDao.updateStatus(info.getContractId(),
//						SECConstant.ContractStatus.NOTACTIVE);
//			} 
		   //else {
			//	secContractDao.updateStatus(info.getContractId(),
			//			SECConstant.ContractStatus.NOTACTIVE);
			//}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}

}
