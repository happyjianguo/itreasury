package com.iss.itreasury.securities.approvaltran.dao;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector; 

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransGeneralLedgerDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transgeneralledger.dao.Sett_TransGeneralLedgerDAO;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.apply.dataentity.ApplyQueryInfo;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountOperation;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.notice.dao.SEC_NoticeDAO;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
public class Sec_ApprovalTranDao extends SecuritiesDAO
{

    public Sec_ApprovalTranDao(){
    	super("loan_approvaltracing");
        super.setUseMaxID();  
    }
    
    /**
	 * 根据审批结果来更新证卷业务记录的状态,同时处理审批记录表记录
	 * 申请业务操作
	 * @param lResultID 审批结果ID
	 * @param tranTypeId 业务类型ID
	 * @param settID 业务记录ID
	 * @return
	 */
	public long updateDataStatusID3(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		long lApplyID = -1;
		long lApprovalContentID = info.getApprovalContentID();
		try{
			
			long settStatusID=-1;//结算表记录状态
			//先处理审批记录表内容
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//无论是拒绝\返回修改\审批通过\审核完成都新增一条记录
			lReturn = appbiz.saveApprovalTracing(info); //保存审批信息
			
			//审核拒绝,逻辑删除本条记录的所有的审核记录
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//根据审批结果来判断结算表记录状态
			
			if(lResultID==Constant.ApprovalDecision.PASS){//审核通过,状态为审批中
				settStatusID=SECConstant.ApplyFormStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//审批拒绝，状态为删除
				settStatusID=SECConstant.ApplyFormStatus.REJECTED;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//审批返回，状态为未审批
				settStatusID=SECConstant.ApplyFormStatus.SUBMITED;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)
			{//审批完成，状态为保存
				settStatusID=SECConstant.ApplyFormStatus.CHECKED;	
				SEC_ApplyDAO appdao = new SEC_ApplyDAO();
					
					 appdao.doAfterCheckOver(lApprovalContentID); 
               
			}
			//根据业务类型和审批结果来更新结算表记录状态信息
			ApplyInfo aInfo = new ApplyInfo();
			aInfo.setId( settID );
			aInfo.setStatusId( settStatusID );
			
			if( tranTypeId > 0 )
			{	
				SEC_ApplyDAO dao = new SEC_ApplyDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)  //修改原记录的状态,状态为删除
				{
					lReturn=dao.updateStatusID( aInfo );
				}
				else if ( lResultID==Constant.ApprovalDecision.FINISH )  //修改原记录的状态,审批完成
				{
					aInfo.setNextCheckUserId( info.getNextUserID() );
					lReturn=dao.updateStatusID( aInfo );
				}
				else  //修改原记录的状态,状态为审批中
				{
					aInfo.setNextCheckUserId( info.getNextUserID() );
					//aInfo.setNextCheckLevel();
					lReturn=dao.updateStatusID( aInfo );
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lReturn;				
	}
  
    /**
	 * 根据审批结果来更新证卷业务记录的状态,同时处理审批记录表记录
	 * 合同业务操作
	 * @param lResultID 审批结果ID
	 * @param tranTypeId 业务类型ID
	 * @param settID 业务记录ID
	 * @return
	 */
	public long updateDataStatusID4(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//结算表记录状态
			//先处理审批记录表内容
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//无论是拒绝\返回修改\审批通过\审核完成都新增一条记录
			lReturn = appbiz.saveApprovalTracing(info); //保存审批信息
			
			//审核拒绝,逻辑删除本条记录的所有的审核记录
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//根据审批结果来判断结算表记录状态
			
			if(lResultID==Constant.ApprovalDecision.PASS){//审核通过,状态为审批中
				settStatusID=SECConstant.ContractStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//审批拒绝，状态为删除
				settStatusID=SECConstant.ContractStatus.REFUSE;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//审批返回，状态为未审批
				settStatusID=SECConstant.ContractStatus.SUBMIT;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)
			{//审批完成，状态为保存
				settStatusID=SECConstant.ContractStatus.CHECK;	
				
			}
			//根据业务类型和审批结果来更新结算表记录状态信息
			SecuritiesContractInfo ContractInfo = new SecuritiesContractInfo();
			ContractInfo.setId( settID );
			ContractInfo.setStatusId( settStatusID );
			
			if( tranTypeId > 0 )
			{		
				SEC_ApplyDAO dao = new SEC_ApplyDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)
				{
					//修改原记录的状态
					lReturn=dao.updateStatusID2( ContractInfo );
					System.out.println(settID+"^^^^^^^删除^^^^^^^"+settStatusID);
				}
				else
				{
					ContractInfo.setNextCheckUserId( info.getNextUserID() );
					
					//修改原记录的状态
					lReturn=dao.updateStatusID2( ContractInfo );
					System.out.println(settID+"^^^^^^^^^^^^^^^^"+settStatusID);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lReturn;				
	}
    
    /**
	 * 根据审批结果来更新证卷业务记录的状态,同时处理审批记录表记录
	 * 通知单操作
	 * @param lResultID 审批结果ID
	 * @param tranTypeId 业务类型ID
	 * @param settID 业务记录ID
	 * @return
	 */
	public long updateDataStatusID5(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//结算表记录状态
			//先处理审批记录表内容
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//无论是拒绝\返回修改\审批通过\审核完成都新增一条记录
			lReturn = appbiz.saveApprovalTracing(info); //保存审批信息
			
			//审核拒绝,逻辑删除本条记录的所有的审核记录
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//根据审批结果来判断结算表记录状态
			
			if(lResultID==Constant.ApprovalDecision.PASS){//审核通过,状态为审批中
				settStatusID=SECConstant.NoticeFormStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//审批拒绝，状态为删除
				settStatusID=SECConstant.NoticeFormStatus.REJECTED;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//审批返回，状态为未审批
				settStatusID=SECConstant.NoticeFormStatus.SUBMITED;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)
			{//审批完成，状态为保存
				settStatusID=SECConstant.NoticeFormStatus.CHECKED;				
			}
			//根据业务类型和审批结果来更新结算表记录状态信息
			NoticeInfo nInfo = new NoticeInfo();
			nInfo.setId(settID);
			nInfo.setStatusId(settStatusID);
			
			if( tranTypeId > 0 )
			{
				//Sett_TransCurrentDepositDAO bankdao=new Sett_TransCurrentDepositDAO();		
				SEC_NoticeDAO nodao = new SEC_NoticeDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)
				{
					//修改原记录的状态
					lReturn=nodao.updateStatusID(nInfo);
					System.out.println(settID+"^^^^^^^删除^^^^^^^"+settStatusID);
				}
				else
				{
					nInfo.setNextCheckUserId(info.getNextUserID());
					
					//修改原记录的状态
					lReturn=nodao.updateStatusID(nInfo);
					System.out.println(settID+"^^^^^^^^^^^^^^^^"+settStatusID);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lReturn;				
	}
	
    /**
	 * 根据审批结果来更新证卷业务记录的状态,同时处理审批记录表记录
	 * 申请业务操作
	 * @param lResultID 审批结果ID
	 * @param tranTypeId 业务类型ID
	 * @param settID 业务记录ID
	 * @return
	 */
	public long updateDataStatusID1(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//结算表记录状态
			//先处理审批记录表内容
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//无论是拒绝\返回修改\审批通过\审核完成都新增一条记录
			lReturn = appbiz.saveApprovalTracing(info); //保存审批信息
			
			//审核拒绝,逻辑删除本条记录的所有的审核记录
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//根据审批结果来判断结算表记录状态
			
			if(lResultID==Constant.ApprovalDecision.PASS){  //审核通过,状态为审批中
				settStatusID=SECConstant.ApplyFormStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){  //审批拒绝，状态为删除
				settStatusID=SECConstant.ApplyFormStatus.REJECTED;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){  //审批返回，状态为未审批
				settStatusID=SECConstant.ApplyFormStatus.SUBMITED;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)  //审批完成，状态为保存
			{
				settStatusID=SECConstant.ApplyFormStatus.CHECKED;	
			}
			
			//根据业务类型和审批结果来更新结算表记录状态信息
			ApplyInfo aInfo = new ApplyInfo();
			aInfo.setId( settID );
			aInfo.setStatusId( settStatusID );
			
			if( tranTypeId > 0 )
			{	
				SEC_ApplyDAO dao = new SEC_ApplyDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)  //修改原记录的状态,状态为删除
				{
					lReturn=dao.updateStatusID( aInfo );
				}
				else if ( lResultID==Constant.ApprovalDecision.FINISH )  //修改原记录的状态,审批完成
				{
					aInfo.setNextCheckUserId( info.getNextUserID() );
					lReturn=dao.updateStatusID( aInfo );
					
					/*审批完成后需要做的操作*/
					SEC_ApplyDAO ApplyDAO = new SEC_ApplyDAO();
					ApplyDAO.doAfterCheckOver(settID);
				}
				else  //修改原记录的状态,状态为审批中
				{
					aInfo.setNextCheckUserId( info.getNextUserID() );
					//aInfo.setNextCheckLevel();
					lReturn=dao.updateStatusID( aInfo );
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lReturn;				
	}
	
	/**
	 * 根据审批结果来更新证卷业务记录的状态,同时处理审批记录表记录
	 * 业务通知单操作
	 * @param lResultID 审批结果ID
	 * @param tranTypeId 业务类型ID
	 * @param settID 业务记录ID
	 * @return
	 */
	public long updateDataStatusID2(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//结算表记录状态
			//先处理审批记录表内容
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//无论是拒绝\返回修改\审批通过\审核完成都新增一条记录
			lReturn = appbiz.saveApprovalTracing(info); //保存审批信息
			
			//审核拒绝,逻辑删除本条记录的所有的审核记录
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			/****根据审批结果来判断结算表记录状态****/
			if(lResultID==Constant.ApprovalDecision.PASS)  //审核通过,状态为审批中
			{
				settStatusID=SECConstant.NoticeFormStatus.APPROVALING;	
			}
			else if(lResultID==Constant.ApprovalDecision.REFUSE)  //审批拒绝，状态为删除
			{
				settStatusID=SECConstant.NoticeFormStatus.REJECTED;				
			}
			else if(lResultID==Constant.ApprovalDecision.RETURN)  //审批返回，状态为未审批
			{
				settStatusID=SECConstant.NoticeFormStatus.SUBMITED;		
			}
			else if(lResultID==Constant.ApprovalDecision.FINISH)  //审批完成，状态为已审核
			{
				settStatusID=SECConstant.NoticeFormStatus.CHECKED;
			}
			
			/****根据业务类型和审批结果来更新结算表记录状态信息****/
			NoticeInfo nInfo = new NoticeInfo();
			nInfo.setId(settID);
			nInfo.setStatusId(settStatusID);
			
			if( tranTypeId > 0 )
			{
				SEC_NoticeDAO nodao = new SEC_NoticeDAO();
				if( lResultID==Constant.ApprovalDecision.REFUSE )  //审核拒绝
				{
					lReturn=nodao.updateStatusID( nInfo );
				}
				else if ( lResultID==Constant.ApprovalDecision.PASS )  //审核通过
				{
					nInfo.setNextCheckUserId(info.getNextUserID());
					
					lReturn=nodao.updateStatusID( nInfo );
					
					/**资金划拨业务**/
					//刚审核的时候,会进去判断是否透支,如果透支,抛出异常,然后页面会有提示选项是否继续
					//如果选择继续,则getIsFinish()会变为false,则这时不检查是否透支,而自己进行审核
					System.out.println("isfinish="+info.getIsFinish());

					if(info.getIsFinish()==SECConstant.TRUE)
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
											
							deliveryOrderInfo.setIsCheckOverDraft(info.getIsFinish());
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
									
				}
				else if ( lResultID==Constant.ApprovalDecision.FINISH )  //审核完成
				{
					nInfo.setNextCheckUserId(info.getNextUserID());
					
					lReturn=nodao.updateStatusID( nInfo );
					
					/**资金划拨业务**/
					try 
					{
						//如果是资金划拨审核完成，则需改变余额操作
						SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
						DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
						SEC_NoticeDAO noticeDAO = new SEC_NoticeDAO();
						NoticeInfo noticeInfo = new NoticeInfo();
						//为了获得deliveryid
						noticeInfo = (NoticeInfo) noticeDAO.findByID(nInfo.getId(),noticeInfo.getClass());
						deliveryOrderInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(noticeInfo.getDeliveryOrderId(),deliveryOrderInfo.getClass());
						System.out.println("deliveryorderinof.gettransactiontypeid()="+deliveryOrderInfo.getTransactionTypeId());
						//如果是资金划拨这一业务类型的
						if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85"))
						{
							DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
							try
							{
								System.out.println("before do save");
								/**
								* 为了检验透支,把ApprovalTracingInfo里的isFinish变量暂时作为是否透支标志位
								*/
								System.out.println("isfinish="+info.getIsFinish());
								
								deliveryOrderInfo.setIsCheckOverDraft(info.getIsFinish());
								deliveryOrderServiceOperation.saveDeliveryOrder(deliveryOrderInfo);
								System.out.println("after do save");
							}
							catch (SecuritiesException e2)
							{
								e2.printStackTrace();
								throw e2;
							}
						}
						
						//如果交易类型是委托理财(涉及证券)和债券承销(债券收取通知单)
						//的业务,就在这里做会计分录
						//System.out.println("here="+noticeInfo.getTransactionTypeId());
						//if(Long.toString(noticeInfo.getTransactionTypeId()).substring(0,2).equals("81")||Long.toString(noticeInfo.getTransactionTypeId()).substring(0,2).equals("73"))
						//if(noticeInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY||noticeInfo.getTransactionTypeId()==SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_PAYMENT_NOTIFY||noticeInfo.getTransactionTypeId()==SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_DRAWBACK_NOTIFY)
						//{
						//	generateGLEntry(noticeInfo,deliveryOrderInfo,this.transConn);
						//}
						
					} catch (ITreasuryDAOException e1) {
						throw new SecuritiesDAOException(e1);
					}
					catch (RemoteException e)
					{
						e.printStackTrace();
					}
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lReturn;				
	}
	
	/**
	 * 证卷管理申请审批使用  boxu add 2006-11-30
	 * 根据模块ID，业务类型ID，金额查询所属的审批流ID
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
	public long getApprovalId1(long moduleid,long typeId,long officeID,long currencyID,double amount,long ActionID){
		long lReturn=-1;
		try{
			initDAO();
			
			/*SUBLOANTYPEID----ACTIONID*/
			long lSubLoanTypeID = -1;
			long lActionID = -1;
			/*证卷操作*/
			if ( ActionID == 1 )
			{
				long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,typeId);  //申请
				lSubLoanTypeID=result[0];
				lActionID=result[1];
			}
			else if ( ActionID == 2 )
			{
				long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,typeId);  //业务
				lSubLoanTypeID=result[0];
				lActionID=result[1];
			}
			else if ( ActionID == 3 )
			{
				long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,typeId);  //合同
				lSubLoanTypeID=result[0];
				lActionID=result[1];
			}
			
			String sql="select approvalid from loan_approvalrelation ";
			sql+=" where moneysegment = (select max(Moneysegment) from loan_approvalrelation";
			sql+=" where moduleid="+moduleid+" and moneysegment<="+amount+" ";
			
			//修改
			sql+=" and actionid="+lActionID+" and subloantypeid = "+lSubLoanTypeID+" ";

			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +")";
			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +" ";
			//ZCQ 于2007-1-30日为显示下一级审批人的查询方法添加模块限制条件
			sql+=" and moduleid="+moduleid;
			//修改
			sql+=" and actionid="+lActionID+" and subloantypeid = "+lSubLoanTypeID+" ";
			
			sql+=" and approvalid in (select id from loan_approvalsetting where nstatusid=2 and nofficeid="+officeID+" and ncurrencyid="+ currencyID +" )";
			log.print("查询审批流IDSQL====="+sql);
			System.out.println("查询审批流IDSQL======="+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();
			while(transRS.next()){
				lReturn=transRS.getLong("approvalid");	
			}
				
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}
    
	/**
	 * 根据审批流ID，用户ID查出此用户的审批级别
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
	public long getLevelId(long approvalID,long userID){
		long lReturn=-1;
		try{
			initDAO();
			String sql="select nlevel from loan_approvalitem";
			sql+="  where napprovalid="+approvalID+" and nuserid="+userID+"";			
			log.print("查询审批级别IDSQL====="+sql);
			System.out.println("查询审批级别IDSQL====="+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();
			while(transRS.next()){
				lReturn=transRS.getLong("nlevel");	
			}				
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}
	
}
