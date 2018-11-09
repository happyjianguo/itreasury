package com.iss.itreasury.securities.notice.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountOperation;
import com.iss.itreasury.securities.securitiescontract.dao.SecuritiesContractDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.bizlogic.SecuritiesGeneralLedgerOperation;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.notice.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.apply.dataentity.ApplyQueryInfo;
import com.iss.itreasury.securities.bizdelegation.ContractDelegation;
import com.iss.itreasury.securities.bizdelegation.NoticeDelegation;
import com.iss.itreasury.securities.deliveryorder.dao.*;
import com.iss.itreasury.securities.deliveryorder.dataentity.*;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author fanyang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SEC_NoticeDAO extends SecuritiesDAO {
	 
	public static void main(String[] args){
		SEC_NoticeDAO dao = new SEC_NoticeDAO();
		NoticeInfo info = new NoticeInfo();
		info.setCode("yangfan");
		info.setDeliveryOrderId(25);
		try 
		{
			//dao.add(info);
			dao.updataDeliveyOrderStatus(222,6);
		}
		catch (SecuritiesDAOException e1)
		{
			e1.printStackTrace();
		}
	}

    protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SEC_NoticeDAO(){
		super("SEC_NoticeForm");
	}
	private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(CallableStatement cs) throws SQLException {
		try {
			if (cs != null) {
				cs.close();
				cs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}
	
	//进行审核操作
	public long check(ApprovalTracingInfo ATInfo) throws SecuritiesException, RemoteException {
		
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
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
		long lUserID = ATInfo.getUserID();
		
//		zpli add 2005-09-14
		long lOfficeID=ATInfo.getOfficeID();
		long lCurrencyID=ATInfo.getCurrencyID();
		////
		
		String sOpinion = ATInfo.getOpinion();

		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalBiz appbiz = new ApprovalBiz();
		NoticeInfo nInfo = new NoticeInfo();
		
		try {
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		try {
            //获得ApprovalID
			if (lApprovalID <= 0 )
			{
//				zpli modify 2005-09-14
				lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
				//lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID);
			}
			} catch (Exception e) {
			throw new SecuritiesDAOException("",e);
        }

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		strSQL = "";

		if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
		{
			//逻辑删除
			try 
			{
				//zpli modify 2005-09-14  lModuleID, lLoanTypeID,  lActionID, lOfficeID, lCurrencyID, lApprovalContentID, lTypeID
				appbiz.deleteApprovalTracing(lModuleID, lLoanTypeID,  lActionID, lOfficeID, lCurrencyID, lApprovalContentID, 2);
				//appbiz.deleteApprovalTracing(lApprovalID,lApprovalContentID,2);
	        } catch (Exception e) 
			{
	            log4j.error("deleteApprovalTracing fail");
	            e.printStackTrace();
	        }

			lStatusID = Constant.RecordStatus.INVALID;
			lResultID = Constant.ApprovalDecision.REFUSE;
			//strSQL = "update Loan_DiscountCredence set nStatusID=" + LOANConstant.DiscountCredenceStatus.REFUSE + " where ID=" + lApprovalContentID;
			
			nInfo.setId(lApprovalContentID);
			nInfo.setStatusId(SECConstant.NoticeFormStatus.REJECTED);
			try {
				update(nInfo);
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
		{
			lStatusID = Constant.RecordStatus.VALID;
			lResultID = Constant.ApprovalDecision.PASS;
			//strSQL = "update Loan_DiscountCredence set nNextCheckUserID=" + lNextUserID + ", nStatusID=" + LOANConstant.DiscountCredenceStatus.SUBMIT + " where ID=" + lApprovalContentID;
			
			nInfo.setId(lApprovalContentID);
			nInfo.setStatusId(SECConstant.NoticeFormStatus.SUBMITED);
			nInfo.setNextCheckUserId(lNextUserID);
			//将下一个审批级别加一
			nInfo.setNextCheckLevel(getNextCheckLevelByNoticeID(lApprovalContentID)+1);
			System.out.println("nextCheckLevel="+nInfo.getNextCheckLevel());
			try 
			{
				update(nInfo);
//刚审核的时候,会进去判断是否透支,如果透支,抛出异常,然后页面会有提示选项是否继续
//如果选择继续,则getIsFinish()会变为false,则这时不检查是否透支,而自己进行审核
				System.out.println("isfinish="+ATInfo.getIsFinish());
				System.out.println("isfinish="+ATInfo.getIsFinish());
				System.out.println("isfinish="+ATInfo.getIsFinish());
				System.out.println("isfinish="+ATInfo.getIsFinish());
				if(ATInfo.getIsFinish()==SECConstant.TRUE)  
				{ 
				  //判断余额是否超支
				  SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
				  DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
				  SEC_NoticeDAO noticeDAO = new SEC_NoticeDAO();
				  NoticeInfo noticeInfo = new NoticeInfo();
				  //为了获得deliveryid
				  noticeInfo = (NoticeInfo) noticeDAO.findByID(nInfo.getId(),noticeInfo.getClass());
				  deliveryOrderInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(noticeInfo.getDeliveryOrderId(),deliveryOrderInfo.getClass());
				  System.out.println("deliveryorderinof.gettransactiontypeid()="+deliveryOrderInfo.getTransactionTypeId());
				  //如果是资金划拨这一业务类型的
				  System.out.println("hehrehreh"+deliveryOrderInfo.getTransactionTypeId());
				  if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85"))
				  {
					System.out.println("hehrehreh");
					  SecuritiesAccountOperation securitiesAccountOperation = new SecuritiesAccountOperation(true); 
					  try
					  {
						System.out.println("before do"+deliveryOrderInfo.getAccountId());
						System.out.println("before do"+deliveryOrderInfo.getTransactionTypeId());
						System.out.println("before do"+deliveryOrderInfo.getNetIncome());
						System.out.println("before do");
										
						  /** 
						   * 为了检验透支,把ApprovalTracingInfo里的isFinish变量暂时作为是否透支标志位
						   */
										
						  deliveryOrderInfo.setIsCheckOverDraft(ATInfo.getIsFinish());
						  securitiesAccountOperation.checkIsOverDraft(deliveryOrderInfo.getAccountId(),deliveryOrderInfo.getTransactionTypeId(),deliveryOrderInfo.getNetIncome());
						System.out.println("after do save");
					  }
					  catch (SecuritiesException e2)
					  {
						  e2.printStackTrace();
						  throw e2;
					  }
				  }
				}
//																																		
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
		}

		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
		{
			lStatusID = Constant.RecordStatus.VALID;
			lResultID = Constant.ApprovalDecision.FINISH;
			//strSQL = " update Loan_DiscountCredence set nNextCheckUserID = "
			//		+ lNextUserID + ", nStatusID = " + LOANConstant.DiscountCredenceStatus.CHECK
			//		+ " where ID = " + lApprovalContentID;
					
			nInfo.setId(lApprovalContentID);
			nInfo.setStatusId(SECConstant.NoticeFormStatus.CHECKED);
			nInfo.setNextCheckUserId(lNextUserID);
			try 
			{
				update(nInfo);
				//如果是资金划拨审核完成，则需改变余额操作
				SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
				DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
				SEC_NoticeDAO noticeDAO = new SEC_NoticeDAO();
				NoticeInfo noticeInfo = new NoticeInfo();
				//为了获得deliveryid
				noticeInfo = (NoticeInfo) noticeDAO.findByID(nInfo.getId(),noticeInfo.getClass());
				deliveryOrderInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(noticeInfo.getDeliveryOrderId(),deliveryOrderInfo.getClass());
				log4j.debug("deliveryorderinof.gettransactiontypeid()="+deliveryOrderInfo.getTransactionTypeId());
				//如果是资金划拨这一业务类型的
				if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85"))
				{
					DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
					try
					{
						log4j.debug("before do save");
						
						/**
						 * 为了检验透支,把ApprovalTracingInfo里的isFinish变量暂时作为是否透支标志位
						 */
						
						deliveryOrderInfo.setIsCheckOverDraft(ATInfo.getIsFinish());
						deliveryOrderServiceOperation.saveDeliveryOrder(deliveryOrderInfo);
						log4j.debug("after do save");
					}
					catch (SecuritiesException e2)
					{
						e2.printStackTrace();
						throw e2;
					}
				}
				//如果交易类型是委托理财(涉及证券)和债券承销(债券收取通知单)
				//的业务,就在这里做会计分录
				log4j.debug("here="+noticeInfo.getTransactionTypeId());
				//if(Long.toString(noticeInfo.getTransactionTypeId()).substring(0,2).equals("81")||Long.toString(noticeInfo.getTransactionTypeId()).substring(0,2).equals("73"))
				if(noticeInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY||noticeInfo.getTransactionTypeId()==SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_PAYMENT_NOTIFY||noticeInfo.getTransactionTypeId()==SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_DRAWBACK_NOTIFY)
				{
					generateGLEntry(noticeInfo,deliveryOrderInfo,this.transConn);
				}
				
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
			//审批完成后需要做的操作
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
		{
			lStatusID = Constant.RecordStatus.VALID;
			lResultID = Constant.ApprovalDecision.RETURN;
			//strSQL = " update Loan_DiscountCredence set nNextCheckUserID=nInputUserID, nStatusID=" + LOANConstant.DiscountCredenceStatus.SUBMIT + " where ID=" + lApprovalContentID;
		
			nInfo.setId(lApprovalContentID);
			nInfo.setStatusId(SECConstant.NoticeFormStatus.SUBMITED);
			nInfo.setNextCheckUserId(ATInfo.getInputUserID());
			//将下个审批级别设为1
			nInfo.setNextCheckLevel(1);
			try {
				update(nInfo);
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		

		//////////////////////
						
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		log4j.debug("check end");
		
		return lApprovalContentID;    
	}
	
	/*
	 *  (non-Javadoc)
	 * 
	 */
	public String getNoticeCode(long lTransactionTypeID) throws SecuritiesDAOException {

	    String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2,4);
		
	    try {
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	    
		strSQL = " select max(nvl(Code,0)) Code from SEC_NoticeForm where Code like 'TZ" + strYear + lTransactionTypeID + "%'";
		log4j.debug(strSQL);

		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
		
		    if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				log4j.debug(strCode);
				if (strCode!=null&&strCode.length() == 11)
				{
				    lCode = Long.parseLong(strCode.substring(8)) + 1;
				}
				else
				{
				    lCode = 1;
				}
				strCode = "TZ" + strYear + lTransactionTypeID + DataFormat.formatInt(lCode, 3, true);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("",e);
		}
			
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		log4j.debug(":::::::::: ::::strcode::::::" + strCode);

		return strCode;
	}

	
	/*
	 *  (non-Javadoc)
	 * 
	 */
	public void cancel(NoticeInfo info) throws SecuritiesDAOException 
	{
		try
		{
			update(info);
		}
		catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("取消通知单产生错误",e);
		}
		
	}
	

	
	/*public NoticeInfo findByID(long id,NoticeInfo cInfo) throws SecuritiesDAOException
	{
		NoticeInfo info = new NoticeInfo();
		try
		{
			info = (NoticeInfo)findByID(id,cInfo);
		}
		catch(SecuritiesDAOException e)
		{
			throw new SecuritiesDAOException("单笔查询通知单产生错误",e);
		}
		return info;
	}*/
	
	/**
	 *通知单的多笔查询操作(和交割单关联)
	*/
	public Collection findByMultiOption(NoticeQueryInfo qInfo) throws SecuritiesDAOException
	{
		String strSelect = null;
		String strSQL = null;
		Vector v = new Vector ();
		long transactionTypeID = qInfo.getTransactionTypeId();
		long clientID = qInfo.getClientId();
		long counterpartID = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusId();
		long userID = qInfo.getInputUserId();
		long queryPurpose = qInfo.getQueryPurpose();
		String systemTransactionCode = qInfo.getSystemTransactionCode();
		Timestamp executeDate = qInfo.getExecuteDate();
		double startQuantity = qInfo.getStartQuantity(); 
		double endQuantity = qInfo.getEndQuantity();
		double startNetPrice = qInfo.getStartNetPrice();
		double endNetPrice = qInfo.getEndNetPrice();
		long accountId = qInfo.getAccountId(); 
		long securitiesId = qInfo.getSecuritiesId();
		
		double startPrice = qInfo.getStartPrice();
		double endPrice = qInfo.getEndPrice();
		double startOppositeQuantity = qInfo.getStartOppositeQuantity();
		double endOppositeQuantity = qInfo.getEndOppositeQuantity();
		
		double startNetIncome = qInfo.getStartNetIncome();
		double endNetIncome = qInfo.getEndNetIncome();
		
		long oppositeSecuritiesId = qInfo.getOppositeSecuritiesId();
		
		String strUser = qInfo.getStrUser();
		log4j.debug("strUser==="+qInfo.getStrUser());
		log4j.debug("strUser==="+qInfo.getStrUser());
		log4j.debug("strUser==="+qInfo.getStrUser());
		log4j.debug("strUser==="+qInfo.getStrUser());
                
		long pageLineCount = qInfo.getPageLineCount() ;
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam() ;
		long desc = qInfo.getDesc() ;
		String orderParamString = qInfo.getOrderParamString();      
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long approvalID = -1;

		long lModuleID = Constant.ModuleType.LOAN;
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		ApprovalBiz appbiz = new ApprovalBiz();        	

	    try {
			initDAO();
	    } catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
    
	    //计算记录总数				
		if ( queryPurpose == 1 )		//for modify
		{				
			/* 取该种业务的对应的approvalSetting ID */
			ApprovalBiz approvalBiz = new ApprovalBiz();
			//approvalID = approvalBiz.getApprovalID(Constant.ModuleType.LOAN,loanType,Constant.ApprovalAction.LOAN_APPLY );
			log4j.debug("----get approvalID:"+approvalID);

			/*****************end of get approval Setting ID ***************/
		
			strSQL = "";
			
			strSelect = " select count(*) ";
			strSQL = " from SEC_NoticeForm aa ,sec_DeliveryOrder bb"
			    + " where 1=1 and aa.DeliveryOrderId = bb.id "
			    + " and aa.StatusID >= " + SECConstant.ApplyFormStatus.SUBMITED
				+ " and aa.StatusID <= " + SECConstant.ApplyFormStatus.CHECKED 
				//+ " and aa.TransactionTypeID = " + transactionTypeID
				+ " and aa.InputUserID = " + userID ;

			//////////////////////查询条件////////////////////////////////////////////////////
			if ( statusID == SECConstant.NoticeFormStatus.SUBMITED )
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
				strSQL += " and aa.NextCheckLevel = 1";//第一级
			}
			else if(statusID == SECConstant.NoticeFormStatus.CHECKED)
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.CHECKED;	
				strSQL += " and aa.NextCheckUserID = -2 ";
			}
			
			else
			{
				strSQL += " and (aa.NextCheckLevel = 1 or aa.NextCheckUserID = -2) ";
			}
		}
		else if (queryPurpose == 2)	//for examine
		{
			strSelect = " select count(*) ";
			strSQL = " from SEC_NoticeForm aa ,sec_DeliveryOrder bb "
			    + " where 1=1 and aa.DeliveryOrderId = bb.id "
				+ " and aa.NextCheckUserID in  " + strUser + "  and aa.TransactionTypeID = " + transactionTypeID;
				
			if ( statusID == SECConstant.ApplyFormStatus.SUBMITED )//只查提交的
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
			}
			else
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
			}				
		}

		if ( transactionTypeID > 0 && transactionTypeID < 1000 )
		{
			String strSQL1 = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedNoticeForm = 1 and BusinessTypeID = " + transactionTypeID;
		    strSQL += " and aa.transactionTypeID in (" + NameRef.getNamesByID(strSQL1,"ID") + ") ";				
		}
		else if ( transactionTypeID > 1000 )
		{
			strSQL += " and aa.transactionTypeID = " + transactionTypeID;	
		}
		/*if (transactionTypeID != -1)
		{
			strSQL += " and aa.transactionTypeID = " + transactionTypeID;
		}*/
		if (systemTransactionCode!=null&&!systemTransactionCode.equals(""))
		{
			strSQL += " and bb.systemTransactionCode like '%"+systemTransactionCode+"%'";
		}
		if (executeDate!=null)
		{
			strSQL += " and to_char(aa.executeDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(executeDate) + "'";
		}
		if (clientID != -1) {
			strSQL += " and bb.ClientID = " + clientID;
		}
        
		if (counterpartID != -1) {
			strSQL += " and bb.CounterpartID = " + counterpartID;                    
		}
        
		if (startAmount > 0) {
			strSQL += " and bb.Amount >= " + startAmount;
		}
        
		if (endAmount > 0) {
			strSQL += " and bb.Amount <= " + endAmount;
		}
        
		if (startDate != null) {
			strSQL += " and to_char(aa.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
		}
        
		if (endDate != null) {
			strSQL += " and to_char(aa.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
		}
		
		if (startQuantity > 0)
		{
			strSQL += " and bb.quantity >= " + startQuantity;
		}
		
		if (endQuantity > 0)
		{
			strSQL += " and bb.quantity <= " + endQuantity;
		}
		
		if (startNetPrice > 0)
		{
			strSQL += " and bb.netPrice >= " + startNetPrice;
		}
		
		if (endNetPrice > 0)
		{
			strSQL += " and bb.netPrice <= " + endNetPrice;
		}

		if (securitiesId != -1)
		{
			strSQL += " and bb.securitiesId = " + securitiesId;
		}

		if (accountId != -1)
		{
			strSQL += " and bb.accountId = " + accountId;
		}
		
		if (startPrice > 0 )
		{
			strSQL += " and bb.price >= " + startPrice;
		}
		
		if (endPrice > 0)
		{
			strSQL += " and bb.price <= " + endPrice;
		}
		
		if (startOppositeQuantity > 0)
		{
			strSQL += " and bb.OppositeQuantity >= " + startOppositeQuantity;
		}
		
		if (endOppositeQuantity > 0)
		{
			strSQL += " and bb.OppositeQuantity <= " + endOppositeQuantity;
		}
		
		if (oppositeSecuritiesId != -1)
		{
			strSQL += " and bb.oppositeSecuritiesId = " + oppositeSecuritiesId;
		}
		
		//
		if (startNetIncome > 0)
		{
			strSQL += " and bb.netIncome >= " + startNetIncome;
		}
		
		if (endNetIncome > 0)
		{
			strSQL += " and bb.netIncome <= " + endNetIncome;
		}
		////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
		int nIndex = 0;
		nIndex = orderParamString.indexOf(".");
		if (nIndex > 0)
		{
		    if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_noticeform"))
		    {
		        strSQL += " order by aa" + orderParamString.substring(nIndex);
		    }
		    if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_deliveryorder"))
		    {
		        strSQL += " order by bb" + orderParamString.substring(nIndex);
		    }
		}
		else
		{
		    strSQL += " order by aa.ID";
		}
		

		if (desc == Constant.PageControl.CODE_ASCORDESC_DESC) {
			strSQL += " desc";
		}

		log4j.debug(strSelect+strSQL);

		try {
			prepareStatement(strSelect+strSQL);
			ResultSet rs = executeQuery ();
		    if (rs != null && rs.next()) {
				lRecordCount = rs.getLong (1);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("",e);
		}
	
		lPageCount = lRecordCount / pageLineCount;

		if ((lRecordCount % pageLineCount) != 0) {
			lPageCount ++;
		}
		pageInfo[0]=lRecordCount;
		pageInfo[1]=lPageCount;
		//v.add(pageInfo);
            
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//返回需求的结果集
		//lRowNumStart = (pageNo-1)*pageLineCount + 1;
		//lRowNumEnd = lRowNumStart + pageLineCount - 1;
        
		strSQL = " select * from ( select aa.*,rownum r from ( select aa.*,"
			   + "bb.counterpartid,"
			   + "bb.clientId, "
		       + "bb.systemTransactionCode, "
		       + "bb.transactionDate,"
		       + "bb.valueDate,"
		       + "bb.maturityDate,"
		       + "bb.amount,"
		       + "bb.rate,"
		       + "bb.term,"
		       + "bb.suspenseInterest,"
		       + "bb.interest,"
		       + "bb.maturityAmount,"
			   + "bb.pledgeSecuritiesAmount,"
		       + "bb.netIncome, "
			   + "bb.deliveryDate,"
			   + "bb.pledgeSecuritiesId,"
			   + "bb.pledgeSecuritiesQuantity,"
			   + "bb.pledgeRate, "
			   + "bb.securitiesId, "
			   + "bb.price, "
			   + "bb.quantity, "
			   + "bb.counterpartTrusteeCode,"
			   + "bb.perSuspenseInterest,"
			   + "bb.netPrice,"
			   + "bb.netPriceAmount, "
			   + "bb.accountId ,"
			   + "bb.tax, "
			   + "bb.oppositeSecuritiesId, "
			   + "bb.oppositeQuantity "
		       + strSQL;
		
		strSQL += " ) aa ) where r between " + "1" + " and " + "100000";
		//strSQL += " ) aa ) where r between " + lRowNumStart + " and " + lRowNumEnd;
        
		log4j.debug(strSQL);
	
		try {
			System.out.print("strSQL:"+strSQL);
			
			prepareStatement(strSQL);

			ResultSet rs1 = executeQuery();

			while (rs1 != null && rs1.next()) 
			{

				NoticeInfo noticeInfo = new NoticeInfo ();
				//applyInfo = (ApplyInfo) getDataEntityFromResultSet(applyInfo.getClass());
				noticeInfo.setSystemTransactionCode(rs1.getString("SystemTransactionCode"));
				noticeInfo.setTransactionDate(rs1.getTimestamp("TransactionDate"));
				noticeInfo.setValueDate(rs1.getTimestamp("ValueDate"));
				noticeInfo.setMaturityDate(rs1.getTimestamp("MaturityDate"));
				noticeInfo.setAmount(rs1.getDouble("Amount"));

				noticeInfo.setRate(rs1.getDouble("Rate"));
				noticeInfo.setTerm(rs1.getLong("Term"));
				noticeInfo.setSuspenseInterest(rs1.getDouble("SuspenseInterest"));
				noticeInfo.setInterest(rs1.getDouble("Interest"));
				noticeInfo.setMaturityAmount(rs1.getDouble("MaturityAmount"));
				noticeInfo.setNetIncome(rs1.getDouble("NetIncome"));
				noticeInfo.setId(rs1.getLong("id"));										//币种
				noticeInfo.setCode(rs1.getString("code"));									//申请书编号
				noticeInfo.setTax(rs1.getDouble("tax"));

				noticeInfo.setDeliveryOrderId(rs1.getLong("deliveryorderid"));				//交割单ID
				noticeInfo.setTransactionTypeId(rs1.getLong("transactionTypeId"));			//交易类型
				noticeInfo.setExecuteDate(rs1.getTimestamp("executedate"));					//收付款日期
				noticeInfo.setCounterpartBankId(rs1.getLong("counterpartbankId"));			//交易对手开户行[债券分销商/基金管理公司]
				noticeInfo.setCounterpartAccountId(rs1.getLong("counterpartaccountid"));	//交易对手银行帐号

				noticeInfo.setCompanyBankId(rs1.getLong("companybankid"));					//公司开户行
				noticeInfo.setCompanyAccountId(rs1.getLong("companyaccountid"));			//公司银行帐号
				noticeInfo.setInputUserId(rs1.getLong("inputUserId"));						//录入人
				noticeInfo.setInputDate(rs1.getTimestamp("inputDate"));						//录入时间
				
				noticeInfo.setUpdateUserId(rs1.getLong("updateUserId"));					//修改人
				noticeInfo.setStatusId(rs1.getLong("statusId"));							//状态
				noticeInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId"));				//下一级审核人
				noticeInfo.setUpdateDate(rs1.getTimestamp("updateDate"));					//修改时间			
				noticeInfo.setTimestamp(rs1.getTimestamp("timestamp"));						//实践戳
				noticeInfo.setPledgeSecuritiesAmount(rs1.getDouble("pledgeSecuritiesAmount"));//券面金额
				
				noticeInfo.setAccountId(rs1.getLong("accountId"));//交易帐号

				noticeInfo.setDeliveryDate(rs1.getTimestamp("deliveryDate"));				//首次结算日
				noticeInfo.setPledgeSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("pledgeSecuritiesId")));//抵押债券名称
				noticeInfo.setPledgeSecuritiesQuantity(rs1.getDouble("pledgeSecuritiesQuantity"));//抵押债券数量
				noticeInfo.setPledgeRate(rs1.getDouble("pledgeRate"));//折算比例
				noticeInfo.setPrice(rs1.getDouble("price"));//成交价格
				noticeInfo.setQuantity(rs1.getDouble("quantity"));//成交数量
				
				noticeInfo.setCounterpartTrusteeCode(rs1.getString("counterpartTrusteeCode"));
				noticeInfo.setPerSuspenseInterest(rs1.getDouble("persuspenseInterest"));
				noticeInfo.setNetPrice(rs1.getDouble("netPrice"));
				noticeInfo.setNetPriceAmount(rs1.getDouble("netPriceAmount"));
				
				noticeInfo.setSecuritiesId(rs1.getLong("securitiesId"));
				noticeInfo.setOppositeSecuritiesId(rs1.getLong("oppositeSecuritiesId"));
				noticeInfo.setOppositeQuantity(rs1.getDouble("oppositeQuantity"));
				
				//下一个审批级别
				noticeInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel"));
				
				//SEC_NoticeForm表中没有的字段          
				noticeInfo.setClientName(NameRef.getClientNameByID(rs1.getLong("clientId")));				//业务单位
				noticeInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId")));	//交易对手
				noticeInfo.setCounterpartBankName(NameRef.getCounterpartBankNameByBankID(rs1.getLong("counterpartBankId")));//交易对手开户行名称
				
				noticeInfo.setCounterpartAccountCode(NameRef.getCounterpartAccountCodeByBankID(rs1.getLong("counterpartBankId")));//交易对手银行帐号
				noticeInfo.setCounterpartAccountName(NameRef.getCounterpartAccountNameByBankID(rs1.getLong("counterpartBankId")));//交易对手帐户名称
				
				noticeInfo.setCompanyBankName(NameRef.getClientBankNameByBankID(rs1.getLong("companyBankId")));//公司开户行名称
				noticeInfo.setCompanyAccountCode(NameRef.getClientAccountCodeByBankID(rs1.getLong("companyBankId")));//公司银行帐号
				
				noticeInfo.setCompanyAccountName(NameRef.getClientAccountNameByBankID(rs1.getLong("companyBankId")));//公司帐户名称
				noticeInfo.setInputUserName(NameRef.getUserNameCodeByID(rs1.getLong("inputUserId")));
				
				noticeInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("securitiesId")));//证券名称
				
				noticeInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(rs1.getLong("accountId")));//基金帐户代码
				noticeInfo.setStockHolderAccountName(NameRef.getStockHolderAccountNameByAccountId(rs1.getLong("accountId")));//基金帐户名称
				
				//noticeInfo.setRecordCount(lRecordCount);	//记录数
				//noticeInfo.setPageCount(lPageCount);		//页数
				
				v.add (noticeInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (v.size () > 0 ? v : null);
	}
	
	/*
	 * boxu add 2006-12-6
	 * 加入金额审批效验
	 * (和交割单关联)
	 */
	public Collection findByMultiOption2(NoticeQueryInfo qInfo,long userID,long CurrencyID,long ActionID,long officeID) throws SecuritiesDAOException
	{
		String sql="";
		
		/*SUBLOANTYPEID----ACTIONID*/
		long lSubLoanTypeID = -1;
		long lActionID = -1;
		
		Collection c = new ArrayList();
		long transactionTypeID = qInfo.getTransactionTypeId();
		
		/*证卷操作*/
		if ( ActionID == 1 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //申请
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		else if ( ActionID == 2 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //业务
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		
		System.out.println("lSubLoanTypeID=="+lSubLoanTypeID+"==lActionID=="+lActionID);
		
		long clientID = qInfo.getClientId();
		long counterpartID = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusId();
		//long userID = qInfo.getInputUserId();
		long queryPurpose = qInfo.getQueryPurpose();
		String systemTransactionCode = qInfo.getSystemTransactionCode();
		Timestamp executeDate = qInfo.getExecuteDate();
		double startQuantity = qInfo.getStartQuantity(); 
		double endQuantity = qInfo.getEndQuantity();
		double startNetPrice = qInfo.getStartNetPrice();
		double endNetPrice = qInfo.getEndNetPrice();
		long accountId = qInfo.getAccountId(); 
		long securitiesId = qInfo.getSecuritiesId();
		
		double startPrice = qInfo.getStartPrice();
		double endPrice = qInfo.getEndPrice();
		double startOppositeQuantity = qInfo.getStartOppositeQuantity();
		double endOppositeQuantity = qInfo.getEndOppositeQuantity();
		
		double startNetIncome = qInfo.getStartNetIncome();
		double endNetIncome = qInfo.getEndNetIncome();
		
		long oppositeSecuritiesId = qInfo.getOppositeSecuritiesId();
		
		String strUser = qInfo.getStrUser();
                
		long pageLineCount = qInfo.getPageLineCount() ;
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam() ;
		long desc = qInfo.getDesc() ;
		String orderParamString = qInfo.getOrderParamString();      
		
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long approvalID = -1;
		
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			sql="(SELECT c.*,-1 moneysegment,-1 approvalid, ";
			
			//交割单表中数据
			sql+= "bb.counterpartid,";
			sql+= "bb.clientId, ";
			sql+= "bb.systemTransactionCode, ";
			sql+= "bb.transactionDate,";
			sql+= "bb.valueDate,";
			sql+= "bb.maturityDate,";
			sql+= "bb.amount,";
			sql+= "bb.rate,";
			sql+= "bb.term,";
			sql+= "bb.suspenseInterest,";
			sql+= "bb.interest,";
			sql+= "bb.maturityAmount,";
	       	sql+= "bb.pledgeSecuritiesAmount,";
	       	sql+= "bb.netIncome, ";
	       	sql+= "bb.deliveryDate,";
	       	sql+= "bb.pledgeSecuritiesId,";
	       	sql+= "bb.pledgeSecuritiesQuantity,";
			sql+= "bb.pledgeRate, ";
			sql+= "bb.securitiesId, ";
			sql+= "bb.price, ";
			sql+= "bb.quantity, ";
			sql+= "bb.counterpartTrusteeCode,";
			sql+= "bb.perSuspenseInterest,";
			sql+= "bb.netPrice,";
			sql+= "bb.netPriceAmount, ";
			sql+= "bb.accountId ,";
			sql+= "bb.tax, ";
			sql+= "bb.oppositeSecuritiesId, ";
			sql+= "bb.oppositeQuantity ";
			
			sql+=" from SEC_NoticeForm c ";
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" ";
			
			//修改
			sql+=" and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
			
			sql+="  and a.NMODULEID="+Constant.ModuleType.SECURITIES+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			
			//与交割单关联
			sql += " ,sec_DeliveryOrder bb ";
			
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.NoticeFormStatus.APPROVALING+"";
			
			//与交割单关联
			sql += " and c.DeliveryOrderId = bb.id ";
			
			if (transactionTypeID != -1)
			{
				sql += " and c.transactiontypeid = " + transactionTypeID;
			}
			if (systemTransactionCode!=null&&!systemTransactionCode.equals(""))
			{
				sql += " and bb.systemTransactionCode like '%"+systemTransactionCode+"%'";
			}
			if (executeDate!=null)
			{
				sql += " and to_char(c.executeDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(executeDate) + "'";
			}
			if (clientID != -1) {
				sql += " and bb.ClientID = " + clientID;
			}
			if (counterpartID != -1) {
				sql += " and bb.CounterpartID = " + counterpartID;                    
			}
			if (startAmount > 0) {
				sql += " and bb.Amount >= " + startAmount;
			}
			if (endAmount > 0) {
				sql += " and bb.Amount <= " + endAmount;
			}
			if (startDate != null) {
				sql += " and to_char(c.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null) {
				sql += " and to_char(c.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}
			if (startQuantity > 0)
			{
				sql += " and bb.quantity >= " + startQuantity;
			}
			if (endQuantity > 0)
			{
				sql += " and bb.quantity <= " + endQuantity;
			}
			if (startNetPrice > 0)
			{
				sql += " and bb.netPrice >= " + startNetPrice;
			}
			if (endNetPrice > 0)
			{
				sql += " and bb.netPrice <= " + endNetPrice;
			}
			if (securitiesId != -1)
			{
				sql += " and bb.securitiesId = " + securitiesId;
			}
			if (accountId != -1)
			{
				sql += " and bb.accountId = " + accountId;
			}
			if (startPrice > 0 )
			{
				sql += " and bb.price >= " + startPrice;
			}
			if (endPrice > 0)
			{
				sql += " and bb.price <= " + endPrice;
			}
			if (startOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity >= " + startOppositeQuantity;
			}
			if (endOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity <= " + endOppositeQuantity;
			}
			if (oppositeSecuritiesId != -1)
			{
				sql += " and bb.oppositeSecuritiesId = " + oppositeSecuritiesId;
			}
			if (startNetIncome > 0)
			{
				sql += " and bb.netIncome >= " + startNetIncome;
			}
			if (endNetIncome > 0)
			{
				sql += " and bb.netIncome <= " + endNetIncome;
			}
		    
		    sql+=") union ( ";
		    
			sql += " select d.* from ( ";
			sql += " select aaa.* ";
			sql += " from ( ";
			sql += " select aa.*,rr.moneysegment,rr.approvalid, ";
			
			//交割单表中数据
			sql+= "bb.counterpartid,";
			sql+= "bb.clientId, ";
			sql+= "bb.systemTransactionCode, ";
			sql+= "bb.transactionDate,";
			sql+= "bb.valueDate,";
			sql+= "bb.maturityDate,";
			sql+= "bb.amount,";
			sql+= "bb.rate,";
			sql+= "bb.term,";
			sql+= "bb.suspenseInterest,";
			sql+= "bb.interest,";
			sql+= "bb.maturityAmount,";
	       	sql+= "bb.pledgeSecuritiesAmount,";
	       	sql+= "bb.netIncome, ";
	       	sql+= "bb.deliveryDate,";
	       	sql+= "bb.pledgeSecuritiesId,";
	       	sql+= "bb.pledgeSecuritiesQuantity,";
			sql+= "bb.pledgeRate, ";
			sql+= "bb.securitiesId, ";
			sql+= "bb.price, ";
			sql+= "bb.quantity, ";
			sql+= "bb.counterpartTrusteeCode,";
			sql+= "bb.perSuspenseInterest,";
			sql+= "bb.netPrice,";
			sql+= "bb.netPriceAmount, ";
			sql+= "bb.accountId ,";
			sql+= "bb.tax, ";
			sql+= "bb.oppositeSecuritiesId, ";
			sql+= "bb.oppositeQuantity ";
			
			sql += " from sec_noticeform aa,loan_approvalrelation rr ";
			
			//与交割单关联
			sql += " ,sec_DeliveryOrder bb ";
			
			//增加关于币种的判断-mhjin-东方电气
			sql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and rr.officeid = "+officeID+" and rr.currencyid =" +CurrencyID+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
			
			//如果是资金划拨业务查询金额字段
			if ( transactionTypeID == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN || transactionTypeID == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT )
			{
				sql += " and bb.netincome>rr.moneysegment ";
			}
			else 
			{
				sql += " and bb.amount>rr.moneysegment ";
			}
			
			//与交割单关联
			sql += " and aa.DeliveryOrderId = bb.id ";
			
			//lSubLoanTypeID 和 lActionID
			sql += " and rr.actionid = " + lActionID ;
			sql += " and rr.subloantypeid = " + lSubLoanTypeID ;
			
			if (transactionTypeID != -1)
			{
				sql += " and aa.transactiontypeid = " + transactionTypeID;
			}
			if (systemTransactionCode!=null&&!systemTransactionCode.equals(""))
			{
				sql += " and bb.systemTransactionCode like '%"+systemTransactionCode+"%'";
			}
			if (executeDate!=null)
			{
				sql += " and to_char(aa.executeDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(executeDate) + "'";
			}
			if (clientID != -1) {
				sql += " and bb.ClientID = " + clientID;
			}
			if (counterpartID != -1) {
				sql += " and bb.CounterpartID = " + counterpartID;                    
			}
			if (startAmount > 0) {
				sql += " and bb.Amount >= " + startAmount;
			}
			if (endAmount > 0) {
				sql += " and bb.Amount <= " + endAmount;
			}
			if (startDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}	
			if (startQuantity > 0)
			{
				sql += " and bb.quantity >= " + startQuantity;
			}
			if (endQuantity > 0)
			{
				sql += " and bb.quantity <= " + endQuantity;
			}
			if (startNetPrice > 0)
			{
				sql += " and bb.netPrice >= " + startNetPrice;
			}
			if (endNetPrice > 0)
			{
				sql += " and bb.netPrice <= " + endNetPrice;
			}
			if (securitiesId != -1)
			{
				sql += " and bb.securitiesId = " + securitiesId;
			}
			if (accountId != -1)
			{
				sql += " and bb.accountId = " + accountId;
			}
			if (startPrice > 0 )
			{
				sql += " and bb.price >= " + startPrice;
			}
			if (endPrice > 0)
			{
				sql += " and bb.price <= " + endPrice;
			}
			if (startOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity >= " + startOppositeQuantity;
			}
			if (endOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity <= " + endOppositeQuantity;
			}
			if (oppositeSecuritiesId != -1)
			{
				sql += " and bb.oppositeSecuritiesId = " + oppositeSecuritiesId;
			}
			if (startNetIncome > 0)
			{
				sql += " and bb.netIncome >= " + startNetIncome;
			}
			if (endNetIncome > 0)
			{
				sql += " and bb.netIncome <= " + endNetIncome;
			}
		    
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from sec_noticeform aa,loan_approvalrelation rr";
			
			//与交割单关联
			sql += " ,sec_DeliveryOrder bb ";
			
			//增加关于币种的判断-mhjin-东方电气
			sql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and rr.officeid = "+officeID+" and rr.currencyid =" +CurrencyID+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
			
			//如果是资金划拨业务查询金额字段
			if ( transactionTypeID == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN || transactionTypeID == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT )
			{
				sql += " and bb.netincome>rr.moneysegment ";
			}
			else 
			{
				sql += " and bb.amount>rr.moneysegment ";
			}
			
			//与交割单关联
			sql += " and aa.DeliveryOrderId = bb.id ";
			
			//lSubLoanTypeID 和 lActionID
			sql += " and rr.actionid = " + lActionID ;
			sql += " and rr.subloantypeid = " + lSubLoanTypeID ;
			
			if (transactionTypeID != -1)
			{
				sql += " and aa.transactiontypeid = " + transactionTypeID;
			}
			if (systemTransactionCode!=null&&!systemTransactionCode.equals(""))
			{
				sql += " and bb.systemTransactionCode like '%"+systemTransactionCode+"%'";
			}
			if (executeDate!=null)
			{
				sql += " and to_char(aa.executeDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(executeDate) + "'";
			}
			if (clientID != -1) {
				sql += " and bb.ClientID = " + clientID;
			}
			if (counterpartID != -1) {
				sql += " and bb.CounterpartID = " + counterpartID;                    
			}
			if (startAmount > 0) {
				sql += " and bb.Amount >= " + startAmount;
			}
			if (endAmount > 0) {
				sql += " and bb.Amount <= " + endAmount;
			}   
			if (startDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			} 
			if (endDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}
			if (startQuantity > 0)
			{
				sql += " and bb.quantity >= " + startQuantity;
			}
			if (endQuantity > 0)
			{
				sql += " and bb.quantity <= " + endQuantity;
			}
			if (startNetPrice > 0)
			{
				sql += " and bb.netPrice >= " + startNetPrice;
			}
			if (endNetPrice > 0)
			{
				sql += " and bb.netPrice <= " + endNetPrice;
			}
			if (securitiesId != -1)
			{
				sql += " and bb.securitiesId = " + securitiesId;
			}
			if (accountId != -1)
			{
				sql += " and bb.accountId = " + accountId;
			}
			if (startPrice > 0 )
			{
				sql += " and bb.price >= " + startPrice;
			}
			if (endPrice > 0)
			{
				sql += " and bb.price <= " + endPrice;
			}
			if (startOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity >= " + startOppositeQuantity;
			}
			if (endOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity <= " + endOppositeQuantity;
			}
			if (oppositeSecuritiesId != -1)
			{
				sql += " and bb.oppositeSecuritiesId = " + oppositeSecuritiesId;
			}
			if (startNetIncome > 0)
			{
				sql += " and bb.netIncome >= " + startNetIncome;
			}
			if (endNetIncome > 0)
			{
				sql += " and bb.netIncome <= " + endNetIncome;
			}
		    
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
			sql +=")";
			System.out.println("查询语句SQL^^^^^^^^^^^===="+sql);
			
			prepareStatement(sql);
			ResultSet rs1 = executeQuery();
			
			//得到显示数据
			while (rs1 != null && rs1.next()) 
			{

				NoticeInfo noticeInfo = new NoticeInfo ();
				//applyInfo = (ApplyInfo) getDataEntityFromResultSet(applyInfo.getClass());
				noticeInfo.setSystemTransactionCode(rs1.getString("SystemTransactionCode"));
				noticeInfo.setTransactionDate(rs1.getTimestamp("TransactionDate"));
				noticeInfo.setValueDate(rs1.getTimestamp("ValueDate"));
				noticeInfo.setMaturityDate(rs1.getTimestamp("MaturityDate"));
				noticeInfo.setAmount(rs1.getDouble("Amount"));

				noticeInfo.setRate(rs1.getDouble("Rate"));
				noticeInfo.setTerm(rs1.getLong("Term"));
				noticeInfo.setSuspenseInterest(rs1.getDouble("SuspenseInterest"));
				noticeInfo.setInterest(rs1.getDouble("Interest"));
				noticeInfo.setMaturityAmount(rs1.getDouble("MaturityAmount"));
				noticeInfo.setNetIncome(rs1.getDouble("NetIncome"));
				noticeInfo.setId(rs1.getLong("id"));										//币种
				noticeInfo.setCode(rs1.getString("code"));									//申请书编号
				noticeInfo.setTax(rs1.getDouble("tax"));

				noticeInfo.setDeliveryOrderId(rs1.getLong("deliveryorderid"));				//交割单ID
				noticeInfo.setTransactionTypeId(rs1.getLong("transactionTypeId"));			//交易类型
				noticeInfo.setExecuteDate(rs1.getTimestamp("executedate"));					//收付款日期
				noticeInfo.setCounterpartBankId(rs1.getLong("counterpartbankId"));			//交易对手开户行[债券分销商/基金管理公司]
				noticeInfo.setCounterpartAccountId(rs1.getLong("counterpartaccountid"));	//交易对手银行帐号

				noticeInfo.setCompanyBankId(rs1.getLong("companybankid"));					//公司开户行
				noticeInfo.setCompanyAccountId(rs1.getLong("companyaccountid"));			//公司银行帐号
				noticeInfo.setInputUserId(rs1.getLong("inputUserId"));						//录入人
				noticeInfo.setInputDate(rs1.getTimestamp("inputDate"));						//录入时间
				
				noticeInfo.setUpdateUserId(rs1.getLong("updateUserId"));					//修改人
				noticeInfo.setStatusId(rs1.getLong("statusId"));							//状态
				noticeInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId"));				//下一级审核人
				noticeInfo.setUpdateDate(rs1.getTimestamp("updateDate"));					//修改时间			
				noticeInfo.setTimestamp(rs1.getTimestamp("timestamp"));						//实践戳
				noticeInfo.setPledgeSecuritiesAmount(rs1.getDouble("pledgeSecuritiesAmount"));//券面金额
				
				noticeInfo.setAccountId(rs1.getLong("accountId"));//交易帐号

				noticeInfo.setDeliveryDate(rs1.getTimestamp("deliveryDate"));				//首次结算日
				noticeInfo.setPledgeSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("pledgeSecuritiesId")));//抵押债券名称
				noticeInfo.setPledgeSecuritiesQuantity(rs1.getDouble("pledgeSecuritiesQuantity"));//抵押债券数量
				noticeInfo.setPledgeRate(rs1.getDouble("pledgeRate"));//折算比例
				noticeInfo.setPrice(rs1.getDouble("price"));//成交价格
				noticeInfo.setQuantity(rs1.getDouble("quantity"));//成交数量
				
				noticeInfo.setCounterpartTrusteeCode(rs1.getString("counterpartTrusteeCode"));
				noticeInfo.setPerSuspenseInterest(rs1.getDouble("persuspenseInterest"));
				noticeInfo.setNetPrice(rs1.getDouble("netPrice"));
				noticeInfo.setNetPriceAmount(rs1.getDouble("netPriceAmount"));
				
				noticeInfo.setSecuritiesId(rs1.getLong("securitiesId"));
				noticeInfo.setOppositeSecuritiesId(rs1.getLong("oppositeSecuritiesId"));
				noticeInfo.setOppositeQuantity(rs1.getDouble("oppositeQuantity"));
				
				//下一个审批级别
				noticeInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel"));
				
				//SEC_NoticeForm表中没有的字段          
				noticeInfo.setClientName(NameRef.getClientNameByID(rs1.getLong("clientId")));				//业务单位
				noticeInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId")));	//交易对手
				noticeInfo.setCounterpartBankName(NameRef.getCounterpartBankNameByBankID(rs1.getLong("counterpartBankId")));//交易对手开户行名称
				
				noticeInfo.setCounterpartAccountCode(NameRef.getCounterpartAccountCodeByBankID(rs1.getLong("counterpartBankId")));//交易对手银行帐号
				noticeInfo.setCounterpartAccountName(NameRef.getCounterpartAccountNameByBankID(rs1.getLong("counterpartBankId")));//交易对手帐户名称
				
				noticeInfo.setCompanyBankName(NameRef.getClientBankNameByBankID(rs1.getLong("companyBankId")));//公司开户行名称
				noticeInfo.setCompanyAccountCode(NameRef.getClientAccountCodeByBankID(rs1.getLong("companyBankId")));//公司银行帐号
				
				noticeInfo.setCompanyAccountName(NameRef.getClientAccountNameByBankID(rs1.getLong("companyBankId")));//公司帐户名称
				noticeInfo.setInputUserName(NameRef.getUserNameCodeByID(rs1.getLong("inputUserId")));
				
				noticeInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("securitiesId")));//证券名称
				
				noticeInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(rs1.getLong("accountId")));//基金帐户代码
				noticeInfo.setStockHolderAccountName(NameRef.getStockHolderAccountNameByAccountId(rs1.getLong("accountId")));//基金帐户名称
				
				//noticeInfo.setRecordCount(lRecordCount);	//记录数
				//noticeInfo.setPageCount(lPageCount);		//页数
				
				c.add (noticeInfo);
			}
			
			//c = getDataEntitiesFromResultSet(NoticeInfo.class);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		catch (Exception e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return (c.size() > 0 ? c : null);
	}
	
	/**
	 *通知单的多笔查询操作(和合同关联)
	*/
	public Collection findByMultiOption1(NoticeQueryInfo qInfo) throws SecuritiesDAOException
	{
		String strSelect = null;
		String strSQL = null;
		Vector v = new Vector ();
		long transactionTypeID = qInfo.getTransactionTypeId();
		//新加
		long startContractID = qInfo.getStartContractId();
		long endContractID = qInfo.getEndContractId();
		double startTransactionAmount = qInfo.getStartTransactionAmount();
		double endTransactionAmount = qInfo.getEndTransactionAmount();
		Timestamp startInputDate = qInfo.getStartInputDate();
		Timestamp endInputDate = qInfo.getEndInputDate();
		
		long clientID = qInfo.getClientId();
		long counterpartID = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusId();
		long userID = qInfo.getInputUserId();
		long queryPurpose = qInfo.getQueryPurpose();
		String systemTransactionCode = qInfo.getSystemTransactionCode();
		Timestamp executeDate = qInfo.getExecuteDate();
		double startQuantity = qInfo.getStartQuantity(); 
		double endQuantity = qInfo.getEndQuantity();
		double startNetPrice = qInfo.getStartNetPrice();
		double endNetPrice = qInfo.getEndNetPrice();
		long accountId = qInfo.getAccountId(); 
		long securitiesId = qInfo.getSecuritiesId();
		
		String strUser = qInfo.getStrUser();
                
		long pageLineCount = qInfo.getPageLineCount() ;
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam() ;
		long desc = qInfo.getDesc() ;
		String orderParamString = qInfo.getOrderParamString();      
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long approvalID = -1;

		long lModuleID = Constant.ModuleType.LOAN;
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		ApprovalBiz appbiz = new ApprovalBiz();        	

	    try {
			initDAO();
	    } catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
    
	    //计算记录总数				
		if ( queryPurpose == 1 )		//for modify
		{				
			/* 取该种业务的对应的approvalSetting ID */
			//ApprovalBiz approvalBiz = new ApprovalBiz();
			//approvalID = approvalBiz.getApprovalID(Constant.ModuleType.LOAN,loanType,Constant.ApprovalAction.LOAN_APPLY );
			//log4j.debug("----get approvalID:"+approvalID);

			/*****************end of get approval Setting ID ***************/
		
			strSQL = "";
			
			strSelect = " select count(*) ";
			strSQL = " from SEC_NoticeForm aa ,sec_applyContract bb"
			    + " where 1=1 and aa.contractId = bb.id "
			    + " and aa.StatusID >= " + SECConstant.ApplyFormStatus.SUBMITED
				+ " and aa.StatusID <= " + SECConstant.ApplyFormStatus.CHECKED 
				//+ " and aa.TransactionTypeID = " + transactionTypeID
				+ " and aa.InputUserID = " + userID ;

			//////////////////////查询条件////////////////////////////////////////////////////
			if ( statusID == SECConstant.NoticeFormStatus.SUBMITED )
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
				strSQL += " and aa.NextCheckLevel = 1 ";
			}
			else if(statusID == SECConstant.NoticeFormStatus.CHECKED)
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.CHECKED;	
				strSQL += " and aa.NextCheckUserID = -2 ";
			}
			else
			{
				strSQL += " and (aa.NextCheckLevel = 1 or aa.NextCheckUserID = -2) ";
			}
		}
		else if (queryPurpose == 2)	//for examine
		{
			strSelect = " select count(*) ";
			strSQL = " from SEC_NoticeForm aa ,sec_applyContract bb "
			    + " where 1=1 and aa.contractId = bb.id "
				+ " and aa.NextCheckUserID in " + strUser ;
				//+ " and aa.TransactionTypeID = " + transactionTypeID;
				
			if ( statusID == SECConstant.ApplyFormStatus.SUBMITED )//只查提交的
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
			}
			else
			{
				strSQL += " and aa.StatusID = " + SECConstant.ApplyFormStatus.SUBMITED;
			}				
		}
		//通知单名称,这里就是交易类型
		if ( transactionTypeID > 0 && transactionTypeID < 1000 )
		{
			String strSQL1 = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedNoticeForm = 1 and BusinessTypeID = " + transactionTypeID;
		    strSQL += " and aa.transactionTypeID in (" + NameRef.getNamesByID(strSQL1,"ID") + ") ";				
		}
		else if ( transactionTypeID > 1000 )
		{
			strSQL += " and aa.transactionTypeID = " + transactionTypeID;	
		}
		/*if (transactionTypeID != -1)
		{
			strSQL += " and aa.transactionTypeID = " + transactionTypeID;
		}*/
		//合同编号
		if(startContractID > -1)
		{
			strSQL += " and bb.id >= " + startContractID;
		}
		if(endContractID > -1)
		{
			strSQL += " and bb.id <= " + endContractID;
		}
		//交易对手
		if (counterpartID > -1) 
		{
			strSQL += " and bb.CounterpartID = " + counterpartID;                    
		}
		//通知单金额
		if (startTransactionAmount > 0)
		{
			strSQL += " and nvl(aa.noticeAmount,0) + nvl(aa.noticeInterest,0) >= " + startTransactionAmount;
		}
		if (endTransactionAmount > 0)
		{
			strSQL += " and nvl(aa.noticeAmount,0) + nvl(aa.noticeInterest,0) <= " + endTransactionAmount;
		}
		if (startInputDate != null)
		{
			strSQL += " and to_char(aa.inputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startInputDate) + "'";
		}
		if (endInputDate != null)
		{
			strSQL += " and to_char(aa.inputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endInputDate) + "'";
		}
		
		////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
		int nIndex = 0;
		nIndex = orderParamString.indexOf(".");
		if (nIndex > 0)
		{
		    if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_noticeform"))
		    {
		        strSQL += " order by aa" + orderParamString.substring(nIndex);
		    }
		    if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_applycontract"))
		    {
		        strSQL += " order by bb" + orderParamString.substring(nIndex);
		    }
		    //交易金额（通知单的本金金额＋实付利息）
		    if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_others"))
		    {
		        strSQL += " order by aa.noticeAmount+aa.noticeInterest ";
		    }
		}
		else
		{
		    strSQL += " order by aa.ID";
		}
		

		if (desc == Constant.PageControl.CODE_ASCORDESC_DESC) {
			strSQL += " desc";
		}

		//log4j.debug(strSelect+strSQL);

		/*try {
			prepareStatement(strSelect+strSQL);
			ResultSet rs = executeQuery ();
		    if (rs != null && rs.next()) {
				lRecordCount = rs.getLong (1);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("",e);
		}
	
		lPageCount = lRecordCount / pageLineCount;

		if ((lRecordCount % pageLineCount) != 0) {
			lPageCount ++;
		}
		pageInfo[0]=lRecordCount;
		pageInfo[1]=lPageCount;*/
		//v.add(pageInfo);
            
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//返回需求的结果集
		//lRowNumStart = (pageNo-1)*pageLineCount + 1;
		//lRowNumEnd = lRowNumStart + pageLineCount - 1;
        
		strSQL = " select * from ( select s.*,rownum r from ( select aa.*,"
			   + "bb.code as contractCode,"
			   + "bb.counterpartId,"
			   + "bb.amount,"
			   + "bb.currencyId "
		       + strSQL;
		
		strSQL += " ), sec_noticeformview s ) where r between " + "1" + " and " + "100000";
		//strSQL += " ) aa ) where r between " + lRowNumStart + " and " + lRowNumEnd;
        
		log4j.debug(strSQL);
		System.out.println("打印的SQL"+strSQL);
	
		try {
			prepareStatement(strSQL);

			ResultSet rs1 = executeQuery();

			while (rs1 != null && rs1.next()) 
			{

				NoticeInfo noticeInfo = new NoticeInfo ();
				//applyInfo = (ApplyInfo) getDataEntityFromResultSet(applyInfo.getClass());
				noticeInfo.setId(rs1.getLong("id"));//通知单id
				noticeInfo.setContractId(rs1.getLong("contractId"));//合同id
				noticeInfo.setCode(rs1.getString("code"));//业务单据编号
				noticeInfo.setContractCode(rs1.getString("contractCode"));//合同编号
				noticeInfo.setTransactionTypeId(rs1.getLong("transactionTypeId"));//业务单据名称Id
				noticeInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId")));	//交易对手
				noticeInfo.setAmount(rs1.getDouble("Amount"));//合同金额
				noticeInfo.setNoticeAmount(rs1.getDouble("noticeAmount"));//本金金额
				noticeInfo.setNoticeInterest(rs1.getDouble("noticeInterest"));//利息金额
				noticeInfo.setCurrencyId(rs1.getLong("currencyId"));//币种
				noticeInfo.setExecuteDate(rs1.getTimestamp("executeDate"));//交易执行日期
				noticeInfo.setInputDate(rs1.getTimestamp("inputDate"));//提交日期
				noticeInfo.setStatusId(rs1.getLong("statusId"));//业务单据状态
				noticeInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId"));//下一级审核人
				noticeInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel"));//下一个审批级别
				noticeInfo.setPublishEndDate(rs1.getTimestamp("publishEndDate"));//发行终止日
				noticeInfo.setCommissionChargeRate(rs1.getDouble("commissionChargeRate"));//手续费率
				
				v.add (noticeInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (v.size () > 0 ? v : null);
	}
	//更新相应交个单的状态,提交时将它置为已使用,取消时将它置为已复核
	public void updataDeliveyOrderStatus(long id,long statusId) throws SecuritiesDAOException
	{
		String sql = "";
		sql = " update sec_deliveryorder set statusid = " + statusId;
		sql +=" where id = " + id;
		try {
			initDAO();
			prepareStatement(sql);
			executeUpdate();
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
	}
	
	/**
	 * 更新相应通知单的交易金额为证券的交易总额
	 * @param id
	 * @param noticeAmount
	 * @throws SecuritiesDAOException
	 */
	public void updataNoticeAmount(long id,double noticeAmount) throws SecuritiesDAOException
	{
		String sql = "";
		sql = " update sec_noticeform set noticeamount = " + noticeAmount;
		sql +=" where id = " + id;
		try {
			initDAO();
			prepareStatement(sql);
			executeUpdate();
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
	}
	
	/**
	 * 获得该通知单的当前审批级别
	 * @param noticeId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	private long getNextCheckLevelByNoticeID(long noticeId) throws SecuritiesDAOException
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nextCheckLevel from sec_noticeform where 1 = 1 ";
		strSQL += " and id = " + noticeId;
		try
		{
			initDAO();
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			try
			{
				while (rs != null && rs.next()) 
				{
					nextCheckLevel = rs.getLong("nextCheckLevel");
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch(ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return nextCheckLevel;
	}
	
	//获得该通知单的合同下所有通知单的已发放委托金额和已收回委托金额
	public NoticeInfo getBuyBackAndReceivedAmount(NoticeInfo info) throws SecuritiesDAOException
	{
		try
		{
			String strSQL = "";
			strSQL = " select sum(a.price*a.quantity) amount from sec_noticewithsecurities a, sec_noticeform b,sec_applycontract c " 
					+" where a.noticeid = b.id "
					+" and a.statusid = " + Constant.RecordStatus.VALID
					+" and b.contractid = c.id "
					+" and c.id = " + info.getContractId()
					+" and b.statusid in ( " + SECConstant.NoticeFormStatus.CHECKED + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + ")"
					//证券转出业务
					+" and b.transactiontypeid = " + SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_PAYMENT_NOTIFY;
			initDAO();
			log4j.debug(strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			try
			{
				if(rs != null && rs.next()) 
				{
					info.setBuyBackAmount(rs.getDouble("amount"));
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			//证券收回业务
			strSQL = "";
			strSQL = " select sum(a.price*a.quantity) amount from sec_noticewithsecurities a, sec_noticeform b,sec_applycontract c "
					+" where a.noticeid = b.id "
					+" and a.statusid = " + Constant.RecordStatus.VALID
					+" and b.contractid = c.id "
					+" and c.id = " + info.getContractId()
					+" and b.statusid in ( " + SECConstant.NoticeFormStatus.CHECKED + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + ")"
					//证券转出业务
					+" and b.transactiontypeid = " + SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_DRAWBACK_NOTIFY;
			log4j.debug(strSQL);
			prepareStatement(strSQL);
			rs = executeQuery(); 
			try
			{
				if(rs != null && rs.next()) 
				{
					info.setReceivedAmount(rs.getDouble("amount"));
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch(ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return info;
	}
	
	/*
	 * 扫描所有的合同,将其中状态是未执行的状态改为已执行,条件是未执行状态的合同
	 * 下至少有一个通知单的状态是已审核
	 */
	public void updateContractStatus() throws Exception
	{
		String sql = "";
		sql = " update sec_applycontract set statusid = " + SECConstant.ContractStatus.ACTIVE
			+ " where id in ( "
       		+ " select distinct a.id from sec_applycontract a,sec_noticeform b "
       		+ " where a.id =  b.contractid "
         	+ " and a.statusid = " + SECConstant.ContractStatus.NOTACTIVE
         	+ " and b.statusid = " + SECConstant.NoticeFormStatus.CHECKED + ")";

		try {
			initDAO();
			prepareStatement(sql);
			executeUpdate();
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	
	
	/**生成会计分录
	 * @param info
	 * @param sumAmount
	 * @throws Exception
	 */
	private void generateGLEntry(NoticeInfo nInfo,DeliveryOrderInfo info,Connection conn) throws SecuritiesException,RemoteException
	{
		try
		{
			log4j.info("生成通知单会计分录");
			GenerateGLEntryParam generateGLEntryParam;
			SecuritiesGeneralLedgerOperation securitiesGeneralLedgerOperation = new SecuritiesGeneralLedgerOperation(conn);
			generateGLEntryParam = new GenerateGLEntryParam();
			
			//通过合同号获承销方式
			SecuritiesContractInfo cInfo = new SecuritiesContractInfo();
			SecuritiesContractDao cDao = new SecuritiesContractDao();
			try
			{
				cInfo = (SecuritiesContractInfo)cDao.findByID(nInfo.getContractId(),cInfo.getClass());
			}
			catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
			//如果交易类型是债券收取,且承销方式是全额包销,则不做操作
			if(nInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY && cInfo.getInterestTypeId() == SECConstant.BoldSaleType.ALLSALE)
			{
				return;
			}
			//根据不通交易类型进行子交易类型和accountype的赋值
			//债券承销
			if(Long.toString(nInfo.getTransactionTypeId()).substring(0,2).equals("81"))
			{
				generateGLEntryParam.setSubTransactionType(cInfo.getInterestTypeId());
				generateGLEntryParam.setAccountType(SECConstant.EntryAccountType.AccountType_13);
			}
			else//委托理财
			if(Long.toString(nInfo.getTransactionTypeId()).substring(0,2).equals("73"))
			{
				//generateGLEntryParam.setSubTransactionType(cInfo.getInterestTypeId());
				generateGLEntryParam.setAccountType(SECConstant.EntryAccountType.AccountType_14);
			}
			generateGLEntryParam.setNetIncome(info.getNetIncome());
			generateGLEntryParam.setOfficeID(info.getOfficeId());
			generateGLEntryParam.setCurrencyID(info.getCurrencyId());
			generateGLEntryParam.setInputUserID(nInfo.getInputUserId());
			generateGLEntryParam.setCheckUserID(nInfo.getInputUserId());
			generateGLEntryParam.setTransactionType(nInfo.getTransactionTypeId());
			
			generateGLEntryParam.setDeliverOrderCode(nInfo.getCode());
			//generateGLEntryParam.setSuspenseInterest(dContractInterest);
			generateGLEntryParam.setExecuteDate(nInfo.getExecuteDate());
			generateGLEntryParam.setDeliveryDate(nInfo.getExecuteDate());
			generateGLEntryParam.setTransactionDate(Env.getSystemDate());
						
			securitiesGeneralLedgerOperation.generateGLEntry(generateGLEntryParam);
			log4j.info("生成通知单会计分录完成");
		}
		catch(SecuritiesException e)
		{
			log4j.debug("生成通知单会计分录出现异常");
			e.printStackTrace();
			throw e;
		}
	}


	public void deleteGLEntry(String code)  throws SecuritiesException,RemoteException
	{
		try
		{
			SecuritiesGeneralLedgerOperation operation = new SecuritiesGeneralLedgerOperation();
			operation.deleteGLEntry(code);
		}
		catch(SecuritiesException e)
		{
			log4j.debug("删除会计分录出现异常");
			throw e;
		}
	}
//	/**
//	 * 更改合同相应值(通知单使用后)供关机时使用!
//	 * @param noticeId
//	 * @return
//	 * @throws SecuritiesDAOException
//	 */
//	public long updateContractAfterNoticeUsed(long noticeId) throws SecuritiesDAOException, RemoteException
//	{
//		SecuritiesContractInfo contractInfo = new SecuritiesContractInfo();
//		NoticeInfo noticeInfo = new NoticeInfo();
//		ContractDelegation contractDelegation = new ContractDelegation();
//		NoticeDelegation noticeDelegation = new NoticeDelegation();
//		noticeInfo = (NoticeInfo)noticeDelegation.findByID(noticeId);
//		contractInfo = (SecuritiesContractInfo)contractDelegation.findByID(noticeInfo.getContractId());
//		
//		//当通知单被使用时,则合同的执行利率改为该通知单的利率,生效日就是本通知单的起息日
//		contractInfo.setRate(noticeInfo.getExecuteRate());
//		contractInfo.setTransactionDate(noticeInfo.getStartInterestDate());
//		//将合同的执行利率改为通知单的执行利率
//		contractInfo.setRate(noticeInfo.getExecuteRate());
//		
//		//根据交易类型进行操作
//		switch ((int) noticeInfo.getTransactionTypeId())
//		{
//			//回购
//			case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY :
//				//累计应付利息更新为起息日
//				contractInfo.setInterestUpdateDate(noticeInfo.getStartInterestDate());
//				break;
//			//购回
//			case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY :
//				//累计应付利息更新为结息日
//				contractInfo.setInterestUpdateDate(noticeInfo.getEndInterestDate());
//				break;
//			//利息支付
//			case (int) SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT :
//				//累计应付利息更新为结息日
//				contractInfo.setInterestUpdateDate(noticeInfo.getEndInterestDate());
//				break;
//			//本金支付
//			case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY :
//				contractInfo.setInterestUpdateDate(noticeInfo.getStartInterestDate());
//				break;
//			//本金收回
//			case (int) SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY :
//				contractInfo.setInterestUpdateDate(noticeInfo.getEndInterestDate());
//				break;
//			//收益收回
//			case (int) SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY :
//				contractInfo.setInterestUpdateDate(noticeInfo.getEndInterestDate());
//				break;
//			//支付投资款
//			case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY :
//				contractInfo.setInterestUpdateDate(noticeInfo.getStartInterestDate());
//				break;
//			//投资款收回
//			case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY :
//				contractInfo.setInterestUpdateDate(noticeInfo.getEndInterestDate());
//				break;
//			//投资收益收回
//			case (int) SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY :
//				contractInfo.setInterestUpdateDate(noticeInfo.getEndInterestDate());
//				break;
//		
//		String strSQL = "";
//		strSQL = " select nextCheckLevel from sec_noticeform where 1 = 1 ";
//		strSQL += " and id = " + noticeId;
//		try
//		{
//			initDAO();
//			prepareStatement(strSQL);
//			ResultSet rs = executeQuery();
//			try
//			{
//				while (rs != null && rs.next()) 
//				{
//					nextCheckLevel = rs.getLong("nextCheckLevel");
//				}
//			}
//			catch (SQLException e1)
//			{
//				e1.printStackTrace();
//			}
//		}
//		catch(ITreasuryDAOException e)
//		{
//			throw new SecuritiesDAOException(e);
//		}
//		try {
//			finalizeDAO();
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);
//		}
//		return nextCheckLevel;
//	}
	
	/*
	 * Boxu add 2006-12-17
	 * 业务通知单审批使用
	 */
	public long updateStatusID( NoticeInfo nInfo ) throws Exception
	{
		long lReturn=-1;
		StringBuffer strSQLBuffer = new StringBuffer();
		strSQLBuffer.append("UPDATE sec_noticeform ");
		strSQLBuffer.append(" SET StatusID = "+nInfo.getStatusId()+", updatedate = sysdate, nextcheckuserid = "+nInfo.getNextCheckUserId()+" ");
		strSQLBuffer.append(" WHERE ID = "+nInfo.getId()+" ");
		
		System.out.println("sql========"+strSQLBuffer.toString());
		try {
			initDAO();
			prepareStatement(strSQLBuffer.toString());
			lReturn = executeUpdate();
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return lReturn;
	}
	
	/**
	 * 同业往来合同关联时使用
	 * 2006-12-26 jincan
	 * 通知单的多笔查询操作(和合同关联)
	*/
	public Collection findByMultiOptionCraf1(NoticeQueryInfo qInfo,long ActionID) throws SecuritiesDAOException
	{ 
		String strSelect = null;
		/*SUBLOANTYPEID----ACTIONID*/
		long lSubLoanTypeID = -1;
		long lActionID = -1;
		
		Vector v = new Vector();
		long transactionTypeID = qInfo.getTransactionTypeId();
		
		/*证卷操作*/
		if ( ActionID == 1 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //申请
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		else if ( ActionID == 2 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //业务
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		
		String strSQL = null;

		//新加
		long startContractID = qInfo.getStartContractId();
		long endContractID = qInfo.getEndContractId();
		double startTransactionAmount = qInfo.getStartTransactionAmount();
		double endTransactionAmount = qInfo.getEndTransactionAmount();
		Timestamp startInputDate = qInfo.getStartInputDate();
		Timestamp endInputDate = qInfo.getEndInputDate();
		long currencyID = qInfo.getCurrencyID();
		long officeid = qInfo.getOfficeID();
		long clientID = qInfo.getClientId();
		long counterpartID = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusId();
		long userID = qInfo.getInputUserId();
		long queryPurpose = qInfo.getQueryPurpose();
		String systemTransactionCode = qInfo.getSystemTransactionCode();
		Timestamp executeDate = qInfo.getExecuteDate();
		double startQuantity = qInfo.getStartQuantity(); 
		double endQuantity = qInfo.getEndQuantity();
		double startNetPrice = qInfo.getStartNetPrice();
		double endNetPrice = qInfo.getEndNetPrice();
		long accountId = qInfo.getAccountId(); 
		long securitiesId = qInfo.getSecuritiesId();
		
		String strUser = qInfo.getStrUser();
                
		long pageLineCount = qInfo.getPageLineCount() ;
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam() ;
		long desc = qInfo.getDesc() ;
		String orderParamString = qInfo.getOrderParamString();      
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long approvalID = -1;


		ApprovalBiz appbiz = new ApprovalBiz();        	

	    try {
			initDAO();
	    } catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
    
	   try{
		   
		   strSQL="(SELECT c.*,-1 moneysegment,-1 approvalid, ";
		   
		   //交割单表中数据
			strSQL+= " bb.code as contractCode, ";
			strSQL+= " bb.counterpartId, ";
			strSQL+= " bb.amount, ";
			strSQL+= " bb.currencyId ";
		   
		   strSQL+=" from SEC_NoticeForm c ";
		   
		   //与合同关联
			strSQL += " ,sec_applyContract bb ";
		   
		   strSQL+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			strSQL+=" where (a.NNEXTUSERID="+userID+" ";
			
			//修改
			strSQL+=" and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
			
			strSQL+="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			strSQL+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			strSQL+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.NoticeFormStatus.APPROVALING+"";
			
			//与合同关联
			strSQL+= " and c.contractid = bb.id ";
			
		if (transactionTypeID != -1)
		{
			strSQL += " and c.transactionTypeID = " + transactionTypeID;
		}
		//合同编号
		if(startContractID > -1)
		{
			strSQL += " and bb.id >= " + startContractID;
		}
		if(endContractID > -1)
		{
			strSQL += " and bb.id <= " + endContractID;
		}
		//交易对手
		if (counterpartID > -1) 
		{
			strSQL += " and bb.CounterpartID = " + counterpartID;                    
		}
		//通知单金额
		if (startTransactionAmount > 0)
		{
			strSQL += " and nvl(c.noticeAmount,0) + nvl(c.noticeInterest,0) >= " + startTransactionAmount;
		}
		if (endTransactionAmount > 0)
		{
			strSQL += " and nvl(c.noticeAmount,0) + nvl(c.noticeInterest,0) <= " + endTransactionAmount;
		}
		if (startInputDate != null)
		{
			strSQL += " and to_char(c.inputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startInputDate) + "'";
		}
		if (endInputDate != null)
		{
			strSQL += " and to_char(c.inputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endInputDate) + "'";
		}
		
		
		strSQL+=") union ( ";
	    
		strSQL += " select d.* from (";
		strSQL += " select aaa.* from (";
		strSQL += " select aa.*,rr.moneysegment,rr.approvalid, ";

		//交割单表中数据
		strSQL+= " bb.code as contractCode, ";
		strSQL+= " bb.counterpartId, ";
		strSQL+= " bb.amount, ";
		strSQL+= " bb.currencyId ";
		
		strSQL += " from sec_noticeform aa,loan_approvalrelation rr ";
		
		//与合同关联
		strSQL += " ,sec_applyContract bb ";
		
		
		
		
		//增加关于币种的判断-mhjin-东方电气
		strSQL += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.noticeAmount>rr.moneysegment and rr.officeid = "+officeid+" and rr.currencyid =" +currencyID+" and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
		
		
		//与合同关联
		strSQL+= " and aa.contractid = bb.id ";
		
		
		//lSubLoanTypeID 和 lActionID
		strSQL += " and rr.actionid = " + lActionID ;
		strSQL += " and rr.subloantypeid = " + lSubLoanTypeID ;
		
		
		if (transactionTypeID != -1)
		{
			strSQL += " and aa.transactionTypeID = " + transactionTypeID;
		}
		//合同编号
		if(startContractID > -1)
		{
			strSQL += " and bb.id >= " + startContractID;
		}
		if(endContractID > -1)
		{
			strSQL += " and bb.id <= " + endContractID;
		}
		//交易对手
		if (counterpartID > -1) 
		{
			strSQL += " and bb.CounterpartID = " + counterpartID;                    
		}
		//通知单金额
		if (startTransactionAmount > 0)
		{
			strSQL += " and nvl(aa.noticeAmount,0) + nvl(aa.noticeInterest,0) >= " + startTransactionAmount;
		}
		if (endTransactionAmount > 0)
		{
			strSQL += " and nvl(aa.noticeAmount,0) + nvl(aa.noticeInterest,0) <= " + endTransactionAmount;
		}
		if (startInputDate != null)
		{
			strSQL += " and to_char(aa.inputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startInputDate) + "'";
		}
		if (endInputDate != null)
		{
			strSQL += " and to_char(aa.inputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endInputDate) + "'";
		}
		
		
		strSQL += " ) aaa,(";
		strSQL += " select aa.id,max(rr.moneysegment) maxamount, ";
		
		//交割单表中数据
		strSQL+= " bb.code as contractCode, ";
		strSQL+= " bb.counterpartId, ";
		strSQL+= " bb.amount, ";
		strSQL+= " bb.currencyId ";
		
		strSQL += " from SEC_NoticeForm aa,loan_approvalrelation rr ";
		
		//与合同关联
		strSQL += " ,sec_applyContract bb ";
		
		//增加关于币种的判断-mhjin-东方电气
		strSQL += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.noticeAmount>rr.moneysegment and rr.officeid = "+officeid+" and rr.currencyid =" +currencyID+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
		
		//lSubLoanTypeID 和 lActionID
		strSQL += " and rr.actionid = " + lActionID ;
		strSQL += " and rr.subloantypeid = " + lSubLoanTypeID ;
		
		//与合同关联
		strSQL+= " and aa.contractid = bb.id ";
		
		if (transactionTypeID != -1)
		{
			strSQL += " and aa.transactionTypeID = " + transactionTypeID;
		}
		//合同编号
		if(startContractID > -1)
		{
			strSQL += " and bb.id >= " + startContractID;
		}
		if(endContractID > -1)
		{
			strSQL += " and bb.id <= " + endContractID;
		}
		//交易对手
		if (counterpartID > -1) 
		{
			strSQL += " and bb.CounterpartID = " + counterpartID;                    
		}
		//通知单金额
		if (startTransactionAmount > 0)
		{
			strSQL += " and nvl(aa.noticeAmount,0) + nvl(aa.noticeInterest,0) >= " + startTransactionAmount;
		}
		if (endTransactionAmount > 0)
		{
			strSQL += " and nvl(aa.noticeAmount,0) + nvl(aa.noticeInterest,0) <= " + endTransactionAmount;
		}
		if (startInputDate != null)
		{
			strSQL += " and to_char(aa.inputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startInputDate) + "'";
		}
		if (endInputDate != null)
		{
			strSQL += " and to_char(aa.inputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endInputDate) + "'";
		}
		
		strSQL += " group by aa.id ) bbb";
		strSQL += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
		strSQL += " loan_approvalsetting e,loan_approvalitem f";
		strSQL += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
		strSQL +=")";
		System.out.println("查询语句SQL^^^^^^^^^^^===="+strSQL);

		prepareStatement(strSQL);
		ResultSet rs1 = executeQuery();
		long rep = -1;

			while (rs1 != null && rs1.next()) 
			{
				rep = rs1.getLong("id");
				NoticeInfo noticeInfo = new NoticeInfo ();
				//applyInfo = (ApplyInfo) getDataEntityFromResultSet(applyInfo.getClass());	
				noticeInfo.setId(rs1.getLong("id"));//通知单id
				noticeInfo.setContractId(rs1.getLong("contractId"));//合同id
				noticeInfo.setCode(rs1.getString("code"));//业务单据编号
				noticeInfo.setContractCode(rs1.getString("contractCode"));//合同编号
				noticeInfo.setTransactionTypeId(rs1.getLong("transactionTypeId"));//业务单据名称Id
				noticeInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId")));	//交易对手
				noticeInfo.setAmount(rs1.getDouble("Amount"));//合同金额
				noticeInfo.setNoticeAmount(rs1.getDouble("noticeAmount"));//本金金额
				noticeInfo.setNoticeInterest(rs1.getDouble("noticeInterest"));//利息金额
				noticeInfo.setCurrencyId(rs1.getLong("currencyId"));//币种
				noticeInfo.setExecuteDate(rs1.getTimestamp("executeDate"));//交易执行日期
				noticeInfo.setInputDate(rs1.getTimestamp("inputDate"));//提交日期
				noticeInfo.setStatusId(rs1.getLong("statusId"));//业务单据状态
				noticeInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId"));//下一级审核人
				noticeInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel"));//下一个审批级别
				noticeInfo.setPublishEndDate(rs1.getTimestamp("publishEndDate"));//发行终止日
				noticeInfo.setCommissionChargeRate(rs1.getDouble("commissionChargeRate"));//手续费率
				
				v.add (noticeInfo);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return (v.size() > 0 ? v : null);
	}
	
	/*
	 * 同业往来中使用
	 * lirinan add 2006-12-26
	 * 加入金额审批效验
	 * (和交割单关联)
	 */
	public Collection findByMultiOptionCraf2(NoticeQueryInfo qInfo,long userID,long CurrencyID,long ActionID,long officeID) throws SecuritiesDAOException
	{
		String sql="";
		
		/*SUBLOANTYPEID----ACTIONID*/
		long lSubLoanTypeID = -1;
		long lActionID = -1;
		
		Collection c = new ArrayList();
		long transactionTypeID = qInfo.getTransactionTypeId();
		
		/*证卷操作*/
		if ( ActionID == 1 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //申请
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		else if ( ActionID == 2 )
		{
			long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transactionTypeID);  //业务
			lSubLoanTypeID=result[0];
			lActionID=result[1];
		}
		
		System.out.println("lSubLoanTypeID=="+lSubLoanTypeID+"==lActionID=="+lActionID);
		
		long clientID = qInfo.getClientId();
		long counterpartID = qInfo.getCounterpartId();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusId();
		//long userID = qInfo.getInputUserId();
		long queryPurpose = qInfo.getQueryPurpose();
		String systemTransactionCode = qInfo.getSystemTransactionCode();
		Timestamp executeDate = qInfo.getExecuteDate();
		double startQuantity = qInfo.getStartQuantity(); 
		double endQuantity = qInfo.getEndQuantity();
		double startNetPrice = qInfo.getStartNetPrice();
		double endNetPrice = qInfo.getEndNetPrice();
		long accountId = qInfo.getAccountId(); 
		long securitiesId = qInfo.getSecuritiesId();
		
		double startPrice = qInfo.getStartPrice();
		double endPrice = qInfo.getEndPrice();
		double startOppositeQuantity = qInfo.getStartOppositeQuantity();
		double endOppositeQuantity = qInfo.getEndOppositeQuantity();
		
		double startNetIncome = qInfo.getStartNetIncome();
		double endNetIncome = qInfo.getEndNetIncome();
		
		long oppositeSecuritiesId = qInfo.getOppositeSecuritiesId();
		
		String strUser = qInfo.getStrUser();
                
		long pageLineCount = qInfo.getPageLineCount() ;
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam() ;
		long desc = qInfo.getDesc() ;
		String orderParamString = qInfo.getOrderParamString();      
		
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long approvalID = -1;
		
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try
		{
			sql="(SELECT c.*,-1 moneysegment,-1 approvalid, ";
			
			//交割单表中数据
			sql+= "bb.counterpartid,";
			sql+= "bb.clientId, ";
			sql+= "bb.systemTransactionCode, ";
			sql+= "bb.transactionDate,";
			sql+= "bb.valueDate,";
			sql+= "bb.maturityDate,";
			sql+= "bb.amount,";
			sql+= "bb.rate,";
			sql+= "bb.term,";
			sql+= "bb.suspenseInterest,";
			sql+= "bb.interest,";
			sql+= "bb.maturityAmount,";
	       	sql+= "bb.pledgeSecuritiesAmount,";
	       	sql+= "bb.netIncome, ";
	       	sql+= "bb.deliveryDate,";
	       	sql+= "bb.pledgeSecuritiesId,";
	       	sql+= "bb.pledgeSecuritiesQuantity,";
			sql+= "bb.pledgeRate, ";
			sql+= "bb.securitiesId, ";
			sql+= "bb.price, ";
			sql+= "bb.quantity, ";
			sql+= "bb.counterpartTrusteeCode,";
			sql+= "bb.perSuspenseInterest,";
			sql+= "bb.netPrice,";
			sql+= "bb.netPriceAmount, ";
			sql+= "bb.accountId ,";
			sql+= "bb.tax, ";
			sql+= "bb.oppositeSecuritiesId, ";
			sql+= "bb.oppositeQuantity ";
			
			sql+=" from SEC_NoticeForm c ";
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" ";
			
			//修改
			sql+=" and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
			
			sql+="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			
			//与交割单关联
			sql += " ,sec_DeliveryOrder bb ";
			
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.NoticeFormStatus.APPROVALING+"";
			
			//与交割单关联
			sql += " and c.DeliveryOrderId = bb.id ";
			
			if (transactionTypeID != -1)
			{
				sql += " and c.transactiontypeid = " + transactionTypeID;
			}
			if (systemTransactionCode!=null&&!systemTransactionCode.equals(""))
			{
				sql += " and bb.systemTransactionCode like '%"+systemTransactionCode+"%'";
			}
			if (executeDate!=null)
			{
				sql += " and to_char(c.executeDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(executeDate) + "'";
			}
			if (clientID != -1) {
				sql += " and bb.ClientID = " + clientID;
			}
			if (counterpartID != -1) {
				sql += " and bb.CounterpartID = " + counterpartID;                    
			}
			if (startAmount > 0) {
				sql += " and bb.Amount >= " + startAmount;
			}
			if (endAmount > 0) {
				sql += " and bb.Amount <= " + endAmount;
			}
			if (startDate != null) {
				sql += " and to_char(c.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null) {
				sql += " and to_char(c.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}
			if (startQuantity > 0)
			{
				sql += " and bb.quantity >= " + startQuantity;
			}
			if (endQuantity > 0)
			{
				sql += " and bb.quantity <= " + endQuantity;
			}
			if (startNetPrice > 0)
			{
				sql += " and bb.netPrice >= " + startNetPrice;
			}
			if (endNetPrice > 0)
			{
				sql += " and bb.netPrice <= " + endNetPrice;
			}
			if (securitiesId != -1)
			{
				sql += " and bb.securitiesId = " + securitiesId;
			}
			if (accountId != -1)
			{
				sql += " and bb.accountId = " + accountId;
			}
			if (startPrice > 0 )
			{
				sql += " and bb.price >= " + startPrice;
			}
			if (endPrice > 0)
			{
				sql += " and bb.price <= " + endPrice;
			}
			if (startOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity >= " + startOppositeQuantity;
			}
			if (endOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity <= " + endOppositeQuantity;
			}
			if (oppositeSecuritiesId != -1)
			{
				sql += " and bb.oppositeSecuritiesId = " + oppositeSecuritiesId;
			}
			if (startNetIncome > 0)
			{
				sql += " and bb.netIncome >= " + startNetIncome;
			}
			if (endNetIncome > 0)
			{
				sql += " and bb.netIncome <= " + endNetIncome;
			}
		    
		    sql+=") union ( ";
		    
			sql += " select d.* from ( ";
			sql += " select aaa.* ";
			sql += " from ( ";
			sql += " select aa.*,rr.moneysegment,rr.approvalid, ";
			
			//交割单表中数据
			sql+= "bb.counterpartid,";
			sql+= "bb.clientId, ";
			sql+= "bb.systemTransactionCode, ";
			sql+= "bb.transactionDate,";
			sql+= "bb.valueDate,";
			sql+= "bb.maturityDate,";
			sql+= "bb.amount,";
			sql+= "bb.rate,";
			sql+= "bb.term,";
			sql+= "bb.suspenseInterest,";
			sql+= "bb.interest,";
			sql+= "bb.maturityAmount,";
	       	sql+= "bb.pledgeSecuritiesAmount,";
	       	sql+= "bb.netIncome, ";
	       	sql+= "bb.deliveryDate,";
	       	sql+= "bb.pledgeSecuritiesId,";
	       	sql+= "bb.pledgeSecuritiesQuantity,";
			sql+= "bb.pledgeRate, ";
			sql+= "bb.securitiesId, ";
			sql+= "bb.price, ";
			sql+= "bb.quantity, ";
			sql+= "bb.counterpartTrusteeCode,";
			sql+= "bb.perSuspenseInterest,";
			sql+= "bb.netPrice,";
			sql+= "bb.netPriceAmount, ";
			sql+= "bb.accountId ,";
			sql+= "bb.tax, ";
			sql+= "bb.oppositeSecuritiesId, ";
			sql+= "bb.oppositeQuantity ";
			
			sql += " from sec_noticeform aa,loan_approvalrelation rr ";
			
			//与交割单关联
			sql += " ,sec_DeliveryOrder bb ";
			
			//增加关于币种的判断-mhjin-东方电气
			sql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and bb.amount>rr.moneysegment and rr.officeid = "+officeID+" and rr.currencyid =" +CurrencyID+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
			
			//与交割单关联
			sql += " and aa.DeliveryOrderId = bb.id ";
			
			//lSubLoanTypeID 和 lActionID
			sql += " and rr.actionid = " + lActionID ;
			sql += " and rr.subloantypeid = " + lSubLoanTypeID ;
			
			if (transactionTypeID != -1)
			{
				sql += " and aa.transactiontypeid = " + transactionTypeID;
			}
			if (systemTransactionCode!=null&&!systemTransactionCode.equals(""))
			{
				sql += " and bb.systemTransactionCode like '%"+systemTransactionCode+"%'";
			}
			if (executeDate!=null)
			{
				sql += " and to_char(aa.executeDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(executeDate) + "'";
			}
			if (clientID != -1) {
				sql += " and bb.ClientID = " + clientID;
			}
			if (counterpartID != -1) {
				sql += " and bb.CounterpartID = " + counterpartID;                    
			}
			if (startAmount > 0) {
				sql += " and bb.Amount >= " + startAmount;
			}
			if (endAmount > 0) {
				sql += " and bb.Amount <= " + endAmount;
			}
			if (startDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}	
			if (startQuantity > 0)
			{
				sql += " and bb.quantity >= " + startQuantity;
			}
			if (endQuantity > 0)
			{
				sql += " and bb.quantity <= " + endQuantity;
			}
			if (startNetPrice > 0)
			{
				sql += " and bb.netPrice >= " + startNetPrice;
			}
			if (endNetPrice > 0)
			{
				sql += " and bb.netPrice <= " + endNetPrice;
			}
			if (securitiesId != -1)
			{
				sql += " and bb.securitiesId = " + securitiesId;
			}
			if (accountId != -1)
			{
				sql += " and bb.accountId = " + accountId;
			}
			if (startPrice > 0 )
			{
				sql += " and bb.price >= " + startPrice;
			}
			if (endPrice > 0)
			{
				sql += " and bb.price <= " + endPrice;
			}
			if (startOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity >= " + startOppositeQuantity;
			}
			if (endOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity <= " + endOppositeQuantity;
			}
			if (oppositeSecuritiesId != -1)
			{
				sql += " and bb.oppositeSecuritiesId = " + oppositeSecuritiesId;
			}
			if (startNetIncome > 0)
			{
				sql += " and bb.netIncome >= " + startNetIncome;
			}
			if (endNetIncome > 0)
			{
				sql += " and bb.netIncome <= " + endNetIncome;
			}
		    
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from sec_noticeform aa,loan_approvalrelation rr";
			
			//与交割单关联
			sql += " ,sec_DeliveryOrder bb ";
			
			//增加关于币种的判断-mhjin-东方电气
			sql += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and bb.amount>rr.moneysegment and rr.officeid = "+officeID+" and rr.currencyid =" +CurrencyID+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
			
			//与交割单关联
			sql += " and aa.DeliveryOrderId = bb.id ";
			
			//lSubLoanTypeID 和 lActionID
			sql += " and rr.actionid = " + lActionID ;
			sql += " and rr.subloantypeid = " + lSubLoanTypeID ;
			
			if (transactionTypeID != -1)
			{
				sql += " and aa.transactiontypeid = " + transactionTypeID;
			}
			if (systemTransactionCode!=null&&!systemTransactionCode.equals(""))
			{
				sql += " and bb.systemTransactionCode like '%"+systemTransactionCode+"%'";
			}
			if (executeDate!=null)
			{
				sql += " and to_char(aa.executeDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(executeDate) + "'";
			}
			if (clientID != -1) {
				sql += " and bb.ClientID = " + clientID;
			}
			if (counterpartID != -1) {
				sql += " and bb.CounterpartID = " + counterpartID;                    
			}
			if (startAmount > 0) {
				sql += " and bb.Amount >= " + startAmount;
			}
			if (endAmount > 0) {
				sql += " and bb.Amount <= " + endAmount;
			}   
			if (startDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
			} 
			if (endDate != null) {
				sql += " and to_char(aa.InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
			}
			if (startQuantity > 0)
			{
				sql += " and bb.quantity >= " + startQuantity;
			}
			if (endQuantity > 0)
			{
				sql += " and bb.quantity <= " + endQuantity;
			}
			if (startNetPrice > 0)
			{
				sql += " and bb.netPrice >= " + startNetPrice;
			}
			if (endNetPrice > 0)
			{
				sql += " and bb.netPrice <= " + endNetPrice;
			}
			if (securitiesId != -1)
			{
				sql += " and bb.securitiesId = " + securitiesId;
			}
			if (accountId != -1)
			{
				sql += " and bb.accountId = " + accountId;
			}
			if (startPrice > 0 )
			{
				sql += " and bb.price >= " + startPrice;
			}
			if (endPrice > 0)
			{
				sql += " and bb.price <= " + endPrice;
			}
			if (startOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity >= " + startOppositeQuantity;
			}
			if (endOppositeQuantity > 0)
			{
				sql += " and bb.OppositeQuantity <= " + endOppositeQuantity;
			}
			if (oppositeSecuritiesId != -1)
			{
				sql += " and bb.oppositeSecuritiesId = " + oppositeSecuritiesId;
			}
			if (startNetIncome > 0)
			{
				sql += " and bb.netIncome >= " + startNetIncome;
			}
			if (endNetIncome > 0)
			{
				sql += " and bb.netIncome <= " + endNetIncome;
			}
		    
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
			sql +=")";
			System.out.println("查询语句SQL^^^^^^^^^^^===="+sql);
			
			prepareStatement(sql);
			ResultSet rs1 = executeQuery();
			
			//得到显示数据
			while (rs1 != null && rs1.next()) 
			{

				NoticeInfo noticeInfo = new NoticeInfo ();
				//applyInfo = (ApplyInfo) getDataEntityFromResultSet(applyInfo.getClass());
				noticeInfo.setSystemTransactionCode(rs1.getString("SystemTransactionCode"));
				noticeInfo.setTransactionDate(rs1.getTimestamp("TransactionDate"));
				noticeInfo.setValueDate(rs1.getTimestamp("ValueDate"));
				noticeInfo.setMaturityDate(rs1.getTimestamp("MaturityDate"));
				noticeInfo.setAmount(rs1.getDouble("Amount"));

				noticeInfo.setRate(rs1.getDouble("Rate"));
				noticeInfo.setTerm(rs1.getLong("Term"));
				noticeInfo.setSuspenseInterest(rs1.getDouble("SuspenseInterest"));
				noticeInfo.setInterest(rs1.getDouble("Interest"));
				noticeInfo.setMaturityAmount(rs1.getDouble("MaturityAmount"));
				noticeInfo.setNetIncome(rs1.getDouble("NetIncome"));
				noticeInfo.setId(rs1.getLong("id"));										//币种
				noticeInfo.setCode(rs1.getString("code"));									//申请书编号
				noticeInfo.setTax(rs1.getDouble("tax"));

				noticeInfo.setDeliveryOrderId(rs1.getLong("deliveryorderid"));				//交割单ID
				noticeInfo.setTransactionTypeId(rs1.getLong("transactionTypeId"));			//交易类型
				noticeInfo.setExecuteDate(rs1.getTimestamp("executedate"));					//收付款日期
				noticeInfo.setCounterpartBankId(rs1.getLong("counterpartbankId"));			//交易对手开户行[债券分销商/基金管理公司]
				noticeInfo.setCounterpartAccountId(rs1.getLong("counterpartaccountid"));	//交易对手银行帐号

				noticeInfo.setCompanyBankId(rs1.getLong("companybankid"));					//公司开户行
				noticeInfo.setCompanyAccountId(rs1.getLong("companyaccountid"));			//公司银行帐号
				noticeInfo.setInputUserId(rs1.getLong("inputUserId"));						//录入人
				noticeInfo.setInputDate(rs1.getTimestamp("inputDate"));						//录入时间
				
				noticeInfo.setUpdateUserId(rs1.getLong("updateUserId"));					//修改人
				noticeInfo.setStatusId(rs1.getLong("statusId"));							//状态
				noticeInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId"));				//下一级审核人
				noticeInfo.setUpdateDate(rs1.getTimestamp("updateDate"));					//修改时间			
				noticeInfo.setTimestamp(rs1.getTimestamp("timestamp"));						//实践戳
				noticeInfo.setPledgeSecuritiesAmount(rs1.getDouble("pledgeSecuritiesAmount"));//券面金额
				
				noticeInfo.setAccountId(rs1.getLong("accountId"));//交易帐号

				noticeInfo.setDeliveryDate(rs1.getTimestamp("deliveryDate"));				//首次结算日
				noticeInfo.setPledgeSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("pledgeSecuritiesId")));//抵押债券名称
				noticeInfo.setPledgeSecuritiesQuantity(rs1.getDouble("pledgeSecuritiesQuantity"));//抵押债券数量
				noticeInfo.setPledgeRate(rs1.getDouble("pledgeRate"));//折算比例
				noticeInfo.setPrice(rs1.getDouble("price"));//成交价格
				noticeInfo.setQuantity(rs1.getDouble("quantity"));//成交数量
				
				noticeInfo.setCounterpartTrusteeCode(rs1.getString("counterpartTrusteeCode"));
				noticeInfo.setPerSuspenseInterest(rs1.getDouble("persuspenseInterest"));
				noticeInfo.setNetPrice(rs1.getDouble("netPrice"));
				noticeInfo.setNetPriceAmount(rs1.getDouble("netPriceAmount"));
				
				noticeInfo.setSecuritiesId(rs1.getLong("securitiesId"));
				noticeInfo.setOppositeSecuritiesId(rs1.getLong("oppositeSecuritiesId"));
				noticeInfo.setOppositeQuantity(rs1.getDouble("oppositeQuantity"));
				
				//下一个审批级别
				noticeInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel"));
				
				//SEC_NoticeForm表中没有的字段          
				noticeInfo.setClientName(NameRef.getClientNameByID(rs1.getLong("clientId")));				//业务单位
				noticeInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId")));	//交易对手
				noticeInfo.setCounterpartBankName(NameRef.getCounterpartBankNameByBankID(rs1.getLong("counterpartBankId")));//交易对手开户行名称
				
				noticeInfo.setCounterpartAccountCode(NameRef.getCounterpartAccountCodeByBankID(rs1.getLong("counterpartBankId")));//交易对手银行帐号
				noticeInfo.setCounterpartAccountName(NameRef.getCounterpartAccountNameByBankID(rs1.getLong("counterpartBankId")));//交易对手帐户名称
				
				noticeInfo.setCompanyBankName(NameRef.getClientBankNameByBankID(rs1.getLong("companyBankId")));//公司开户行名称
				noticeInfo.setCompanyAccountCode(NameRef.getClientAccountCodeByBankID(rs1.getLong("companyBankId")));//公司银行帐号
				
				noticeInfo.setCompanyAccountName(NameRef.getClientAccountNameByBankID(rs1.getLong("companyBankId")));//公司帐户名称
				noticeInfo.setInputUserName(NameRef.getUserNameCodeByID(rs1.getLong("inputUserId")));
				
				noticeInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(rs1.getLong("securitiesId")));//证券名称
				
				noticeInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(rs1.getLong("accountId")));//基金帐户代码
				noticeInfo.setStockHolderAccountName(NameRef.getStockHolderAccountNameByAccountId(rs1.getLong("accountId")));//基金帐户名称
				
				//noticeInfo.setRecordCount(lRecordCount);	//记录数
				//noticeInfo.setPageCount(lPageCount);		//页数
				
				c.add (noticeInfo);
			}
			
			//c = getDataEntitiesFromResultSet(NoticeInfo.class);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		catch (Exception e)
		{
			throw new SecuritiesDAOException("批量查询申请书产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return (c.size() > 0 ? c : null);
	}
	
	
	/**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long update(long loanID, long userID, long newStatusID)
	throws Exception {
       PreparedStatement ps = null;
       Connection conn = null;
       String strSQL = null;
       long lResult = -1;

      try {
	conn = Database.getConnection();
	strSQL = " update SEC_NoticeForm set StatusID=? where ID=?";

	ps = conn.prepareStatement(strSQL);
	ps.setLong(1, newStatusID);
	ps.setLong(2, loanID);

	lResult = ps.executeUpdate();
	cleanup(ps);
	cleanup(conn);

	if (lResult < 0) {
		log.info("update SEC_NoticeForm property info error:" + lResult);
		return -1;
	} else {
		return loanID;
	}
} catch (Exception e) {

	cleanup(ps);
	cleanup(conn);
	throw e;
} finally {

	cleanup(ps);
	cleanup(conn);
}

}
	/**
     * added by xwhe 2007/01/17
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long updateTrans(long loanID, long userID, long newStatusID)
	throws Exception {
       PreparedStatement ps = null;
       Connection conn = null;
       String strSQL = null;
       long lResult = -1;

      try {
	conn = Database.getConnection();
	strSQL = " update Sett_Transsecurities set nStatusID=? where nsecuritiesnoticeid=?";

	ps = conn.prepareStatement(strSQL);
	ps.setLong(1, newStatusID);
	ps.setLong(2, loanID);

	lResult = ps.executeUpdate();
	cleanup(ps);
	cleanup(conn);

	if (lResult < 0) {
		log.info("update Sett_Transsecurities property info error:" + lResult);
		return -1;
	} else {
		return loanID;
	}
} catch (Exception e) {

	cleanup(ps);
	cleanup(conn);
	throw e;
} finally {

	cleanup(ps);
	cleanup(conn);
}

}
	
	/**
	 *通知单的多笔查询操作(和合同关联)
	 *得到合同下所有交易类型为transactionTypeID的通知单
	*/
	public Collection findByMultiOption2(long lContractID,long transactionTypeID) throws SecuritiesDAOException
	{
		String strSelect = null;
		String strSQL = null;
		Vector v = new Vector ();
		
	    try {
			initDAO();
	    } catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		strSQL = "";
		
		strSQL = "select aa.id id,aa.contractid contractId, aa.code code,bb.code contractCode, aa.transactiontypeid transactionTypeId, aa.counterpartaccountid counterpartId, bb.amount Amount, aa.noticeamount noticeAmount, aa.noticeinterest noticeInterest,aa.currencyid currencyId,aa.executedate executeDate, aa.inputdate inputDate,aa.statusid statusId "
			+ " from SEC_NoticeForm aa ,sec_applyContract bb"
		    + " where 1=1 and aa.contractId = bb.id "
		    + " and aa.StatusID >= " + SECConstant.ApplyFormStatus.SUBMITED;


		//通知单名称,这里就是交易类型
		if ( transactionTypeID > 0 && transactionTypeID < 1000 )
		{
			String strSQL1 = " select ID from SEC_TransactionType t where StatusID = 3 and IsNeedNoticeForm = 1 and BusinessTypeID = " + transactionTypeID;
		    strSQL += " and aa.transactionTypeID in (" + NameRef.getNamesByID(strSQL1,"ID") + ") ";				
		}
		else if ( transactionTypeID > 1000 )
		{
			strSQL += " and aa.transactionTypeID = " + transactionTypeID;	
		}
		//合同编号
		if(lContractID > -1)
		{
			strSQL += " and bb.id = " + lContractID;
		}
		
		log4j.debug(strSQL);
		System.out.println("打印的SQL"+strSQL);
	
		try {
			prepareStatement(strSQL);

			ResultSet rs1 = executeQuery();

			while (rs1 != null && rs1.next()) 
			{

				NoticeInfo noticeInfo = new NoticeInfo ();
				//applyInfo = (ApplyInfo) getDataEntityFromResultSet(applyInfo.getClass());
				noticeInfo.setId(rs1.getLong("id"));//通知单id
				noticeInfo.setContractId(rs1.getLong("contractId"));//合同id
				noticeInfo.setCode(rs1.getString("code"));//业务单据编号
				noticeInfo.setContractCode(rs1.getString("contractCode"));//合同编号
				noticeInfo.setTransactionTypeId(rs1.getLong("transactionTypeId"));//业务单据名称Id
				noticeInfo.setCounterpartName(NameRef.getCounterpartNameByID(rs1.getLong("counterpartId")));	//交易对手
				noticeInfo.setAmount(rs1.getDouble("Amount"));//合同金额
				noticeInfo.setNoticeAmount(rs1.getDouble("noticeAmount"));//本金金额
				noticeInfo.setNoticeInterest(rs1.getDouble("noticeInterest"));//利息金额
				noticeInfo.setCurrencyId(rs1.getLong("currencyId"));//币种
				noticeInfo.setExecuteDate(rs1.getTimestamp("executeDate"));//交易执行日期
				noticeInfo.setInputDate(rs1.getTimestamp("inputDate"));//提交日期
				noticeInfo.setStatusId(rs1.getLong("statusId"));//业务单据状态
//				noticeInfo.setNextCheckUserId(rs1.getLong("nextCheckUserId"));//下一级审核人
//				noticeInfo.setNextCheckLevel(rs1.getLong("nextCheckLevel"));//下一个审批级别
//				noticeInfo.setPublishEndDate(rs1.getTimestamp("publishEndDate"));//发行终止日
//				noticeInfo.setCommissionChargeRate(rs1.getDouble("commissionChargeRate"));//手续费率
//				
				v.add (noticeInfo);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SecuritiesDAOException("",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (v.size () > 0 ? v : null);
	}	
	
}