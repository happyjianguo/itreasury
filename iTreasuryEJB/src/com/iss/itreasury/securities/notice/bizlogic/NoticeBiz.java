/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.notice.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.craftbrother.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.dao.SEC_NoticeDAO;
import com.iss.itreasury.securities.notice.dao.SEC_NoticeWithSecuritiesDAO;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
import com.iss.itreasury.securities.notice.dataentity.NoticeQueryInfo;
import com.iss.itreasury.securities.notice.dataentity.NoticeWithSecuritiesInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NoticeBiz implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	
	final static long serialVersionUID = 3206093459760846163L;
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	/**
	 *通知单的保存操作
	*/
	public long save(NoticeInfo info) throws java.rmi.RemoteException,SecuritiesException
	{
	    SEC_NoticeDAO dao = new SEC_NoticeDAO();
	    long lID = -1;
	    String applyCode = "";
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
	        	try {
					applyCode = applyCode=CreateCodeManager.createCode(map);
				} 
				catch (Exception e) {
					e.printStackTrace();
					try {
						throw new IException();
					} catch (IException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
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
			}catch (IException e)
			{
				e.printStackTrace();
				try {
					throw new IException("未关联编码设置",e);
				} catch (IException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
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
	 *通知单的多笔查询操作(和交割单关联)同业往来
	*/
	public Collection findByMultiOption3(NoticeQueryInfo qInfo) throws java.rmi.RemoteException,SecuritiesException
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
		    // c = dao.findByMultiOption3(qInfo);
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
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(NoticeInfo info)
			throws RemoteException, IRollbackException {
		
		long lReturnId = -1;
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
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
		 long lTransDiscountApplyID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				SEC_NoticeDAO dao = new SEC_NoticeDAO();
   			     //TransDiscountApplyInfo	appInfo=dao.findByID(info.getId());
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
   							lTransDiscountApplyID,
   							lUserID,
   							SECConstant.NoticeFormStatus.CHECKED );
   					
   					//将贷款申请信息复制到合同表				  		
   					//dao.doAfterApprovalOver(lTransDiscountApplyID);
   				}
   				//如果是最后一级,且为审批拒绝,更新状态为已保存
   				else if(returnInfo.isRefuse())
   				{
   					dao.update(
   							lTransDiscountApplyID,
   							lUserID,
   							SECConstant.NoticeFormStatus.SUBMITED);
   				}	
   		 
   	 }
   	 catch(Exception e){
   		 throw new IRollbackException(mySessionCtx, e.getMessage(), e); 
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
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SEC_NoticeDAO dao = new SEC_NoticeDAO();
		
		try
		{
			//取消审批
			lReturn = dao.update(info.getId(), info.getInputUserId(), SECConstant.NoticeFormStatus.SUBMITED);
			
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	/**
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long updateApplyAndApprovalInit(NoticeInfo info)
			throws RemoteException, IRollbackException {
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
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}

		return lReturnId;
	} 

}
