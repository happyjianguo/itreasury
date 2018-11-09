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
	 * �����������������֤��ҵ���¼��״̬,ͬʱ����������¼���¼
	 * ����ҵ�����
	 * @param lResultID �������ID
	 * @param tranTypeId ҵ������ID
	 * @param settID ҵ���¼ID
	 * @return
	 */
	public long updateDataStatusID3(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		long lApplyID = -1;
		long lApprovalContentID = info.getApprovalContentID();
		try{
			
			long settStatusID=-1;//������¼״̬
			//�ȴ���������¼������
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//�����Ǿܾ�\�����޸�\����ͨ��\�����ɶ�����һ����¼
			lReturn = appbiz.saveApprovalTracing(info); //����������Ϣ
			
			//��˾ܾ�,�߼�ɾ��������¼�����е���˼�¼
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//��������������жϽ�����¼״̬
			
			if(lResultID==Constant.ApprovalDecision.PASS){//���ͨ��,״̬Ϊ������
				settStatusID=SECConstant.ApplyFormStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//�����ܾ���״̬Ϊɾ��
				settStatusID=SECConstant.ApplyFormStatus.REJECTED;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//�������أ�״̬Ϊδ����
				settStatusID=SECConstant.ApplyFormStatus.SUBMITED;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)
			{//������ɣ�״̬Ϊ����
				settStatusID=SECConstant.ApplyFormStatus.CHECKED;	
				SEC_ApplyDAO appdao = new SEC_ApplyDAO();
					
					 appdao.doAfterCheckOver(lApprovalContentID); 
               
			}
			//����ҵ�����ͺ�������������½�����¼״̬��Ϣ
			ApplyInfo aInfo = new ApplyInfo();
			aInfo.setId( settID );
			aInfo.setStatusId( settStatusID );
			
			if( tranTypeId > 0 )
			{	
				SEC_ApplyDAO dao = new SEC_ApplyDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)  //�޸�ԭ��¼��״̬,״̬Ϊɾ��
				{
					lReturn=dao.updateStatusID( aInfo );
				}
				else if ( lResultID==Constant.ApprovalDecision.FINISH )  //�޸�ԭ��¼��״̬,�������
				{
					aInfo.setNextCheckUserId( info.getNextUserID() );
					lReturn=dao.updateStatusID( aInfo );
				}
				else  //�޸�ԭ��¼��״̬,״̬Ϊ������
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
	 * �����������������֤��ҵ���¼��״̬,ͬʱ����������¼���¼
	 * ��ͬҵ�����
	 * @param lResultID �������ID
	 * @param tranTypeId ҵ������ID
	 * @param settID ҵ���¼ID
	 * @return
	 */
	public long updateDataStatusID4(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//������¼״̬
			//�ȴ���������¼������
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//�����Ǿܾ�\�����޸�\����ͨ��\�����ɶ�����һ����¼
			lReturn = appbiz.saveApprovalTracing(info); //����������Ϣ
			
			//��˾ܾ�,�߼�ɾ��������¼�����е���˼�¼
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//��������������жϽ�����¼״̬
			
			if(lResultID==Constant.ApprovalDecision.PASS){//���ͨ��,״̬Ϊ������
				settStatusID=SECConstant.ContractStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//�����ܾ���״̬Ϊɾ��
				settStatusID=SECConstant.ContractStatus.REFUSE;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//�������أ�״̬Ϊδ����
				settStatusID=SECConstant.ContractStatus.SUBMIT;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)
			{//������ɣ�״̬Ϊ����
				settStatusID=SECConstant.ContractStatus.CHECK;	
				
			}
			//����ҵ�����ͺ�������������½�����¼״̬��Ϣ
			SecuritiesContractInfo ContractInfo = new SecuritiesContractInfo();
			ContractInfo.setId( settID );
			ContractInfo.setStatusId( settStatusID );
			
			if( tranTypeId > 0 )
			{		
				SEC_ApplyDAO dao = new SEC_ApplyDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)
				{
					//�޸�ԭ��¼��״̬
					lReturn=dao.updateStatusID2( ContractInfo );
					System.out.println(settID+"^^^^^^^ɾ��^^^^^^^"+settStatusID);
				}
				else
				{
					ContractInfo.setNextCheckUserId( info.getNextUserID() );
					
					//�޸�ԭ��¼��״̬
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
	 * �����������������֤��ҵ���¼��״̬,ͬʱ����������¼���¼
	 * ֪ͨ������
	 * @param lResultID �������ID
	 * @param tranTypeId ҵ������ID
	 * @param settID ҵ���¼ID
	 * @return
	 */
	public long updateDataStatusID5(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//������¼״̬
			//�ȴ���������¼������
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//�����Ǿܾ�\�����޸�\����ͨ��\�����ɶ�����һ����¼
			lReturn = appbiz.saveApprovalTracing(info); //����������Ϣ
			
			//��˾ܾ�,�߼�ɾ��������¼�����е���˼�¼
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//��������������жϽ�����¼״̬
			
			if(lResultID==Constant.ApprovalDecision.PASS){//���ͨ��,״̬Ϊ������
				settStatusID=SECConstant.NoticeFormStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//�����ܾ���״̬Ϊɾ��
				settStatusID=SECConstant.NoticeFormStatus.REJECTED;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//�������أ�״̬Ϊδ����
				settStatusID=SECConstant.NoticeFormStatus.SUBMITED;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)
			{//������ɣ�״̬Ϊ����
				settStatusID=SECConstant.NoticeFormStatus.CHECKED;				
			}
			//����ҵ�����ͺ�������������½�����¼״̬��Ϣ
			NoticeInfo nInfo = new NoticeInfo();
			nInfo.setId(settID);
			nInfo.setStatusId(settStatusID);
			
			if( tranTypeId > 0 )
			{
				//Sett_TransCurrentDepositDAO bankdao=new Sett_TransCurrentDepositDAO();		
				SEC_NoticeDAO nodao = new SEC_NoticeDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)
				{
					//�޸�ԭ��¼��״̬
					lReturn=nodao.updateStatusID(nInfo);
					System.out.println(settID+"^^^^^^^ɾ��^^^^^^^"+settStatusID);
				}
				else
				{
					nInfo.setNextCheckUserId(info.getNextUserID());
					
					//�޸�ԭ��¼��״̬
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
	 * �����������������֤��ҵ���¼��״̬,ͬʱ����������¼���¼
	 * ����ҵ�����
	 * @param lResultID �������ID
	 * @param tranTypeId ҵ������ID
	 * @param settID ҵ���¼ID
	 * @return
	 */
	public long updateDataStatusID1(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//������¼״̬
			//�ȴ���������¼������
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//�����Ǿܾ�\�����޸�\����ͨ��\�����ɶ�����һ����¼
			lReturn = appbiz.saveApprovalTracing(info); //����������Ϣ
			
			//��˾ܾ�,�߼�ɾ��������¼�����е���˼�¼
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//��������������жϽ�����¼״̬
			
			if(lResultID==Constant.ApprovalDecision.PASS){  //���ͨ��,״̬Ϊ������
				settStatusID=SECConstant.ApplyFormStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){  //�����ܾ���״̬Ϊɾ��
				settStatusID=SECConstant.ApplyFormStatus.REJECTED;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){  //�������أ�״̬Ϊδ����
				settStatusID=SECConstant.ApplyFormStatus.SUBMITED;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH)  //������ɣ�״̬Ϊ����
			{
				settStatusID=SECConstant.ApplyFormStatus.CHECKED;	
			}
			
			//����ҵ�����ͺ�������������½�����¼״̬��Ϣ
			ApplyInfo aInfo = new ApplyInfo();
			aInfo.setId( settID );
			aInfo.setStatusId( settStatusID );
			
			if( tranTypeId > 0 )
			{	
				SEC_ApplyDAO dao = new SEC_ApplyDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)  //�޸�ԭ��¼��״̬,״̬Ϊɾ��
				{
					lReturn=dao.updateStatusID( aInfo );
				}
				else if ( lResultID==Constant.ApprovalDecision.FINISH )  //�޸�ԭ��¼��״̬,�������
				{
					aInfo.setNextCheckUserId( info.getNextUserID() );
					lReturn=dao.updateStatusID( aInfo );
					
					/*������ɺ���Ҫ���Ĳ���*/
					SEC_ApplyDAO ApplyDAO = new SEC_ApplyDAO();
					ApplyDAO.doAfterCheckOver(settID);
				}
				else  //�޸�ԭ��¼��״̬,״̬Ϊ������
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
	 * �����������������֤��ҵ���¼��״̬,ͬʱ����������¼���¼
	 * ҵ��֪ͨ������
	 * @param lResultID �������ID
	 * @param tranTypeId ҵ������ID
	 * @param settID ҵ���¼ID
	 * @return
	 */
	public long updateDataStatusID2(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//������¼״̬
			//�ȴ���������¼������
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//�����Ǿܾ�\�����޸�\����ͨ��\�����ɶ�����һ����¼
			lReturn = appbiz.saveApprovalTracing(info); //����������Ϣ
			
			//��˾ܾ�,�߼�ɾ��������¼�����е���˼�¼
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			/****��������������жϽ�����¼״̬****/
			if(lResultID==Constant.ApprovalDecision.PASS)  //���ͨ��,״̬Ϊ������
			{
				settStatusID=SECConstant.NoticeFormStatus.APPROVALING;	
			}
			else if(lResultID==Constant.ApprovalDecision.REFUSE)  //�����ܾ���״̬Ϊɾ��
			{
				settStatusID=SECConstant.NoticeFormStatus.REJECTED;				
			}
			else if(lResultID==Constant.ApprovalDecision.RETURN)  //�������أ�״̬Ϊδ����
			{
				settStatusID=SECConstant.NoticeFormStatus.SUBMITED;		
			}
			else if(lResultID==Constant.ApprovalDecision.FINISH)  //������ɣ�״̬Ϊ�����
			{
				settStatusID=SECConstant.NoticeFormStatus.CHECKED;
			}
			
			/****����ҵ�����ͺ�������������½�����¼״̬��Ϣ****/
			NoticeInfo nInfo = new NoticeInfo();
			nInfo.setId(settID);
			nInfo.setStatusId(settStatusID);
			
			if( tranTypeId > 0 )
			{
				SEC_NoticeDAO nodao = new SEC_NoticeDAO();
				if( lResultID==Constant.ApprovalDecision.REFUSE )  //��˾ܾ�
				{
					lReturn=nodao.updateStatusID( nInfo );
				}
				else if ( lResultID==Constant.ApprovalDecision.PASS )  //���ͨ��
				{
					nInfo.setNextCheckUserId(info.getNextUserID());
					
					lReturn=nodao.updateStatusID( nInfo );
					
					/**�ʽ𻮲�ҵ��**/
					//����˵�ʱ��,���ȥ�ж��Ƿ�͸֧,���͸֧,�׳��쳣,Ȼ��ҳ�������ʾѡ���Ƿ����
					//���ѡ�����,��getIsFinish()���Ϊfalse,����ʱ������Ƿ�͸֧,���Լ��������
					System.out.println("isfinish="+info.getIsFinish());

					if(info.getIsFinish()==SECConstant.TRUE)
					{ 
					  //�ж�����Ƿ�֧
					  SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
					  DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
					  SEC_NoticeDAO noticeDAO = new SEC_NoticeDAO();
					  NoticeInfo noticeInfo = new NoticeInfo();
					  //Ϊ�˻��deliveryid
					  noticeInfo = (NoticeInfo) noticeDAO.findByID(nInfo.getId(),noticeInfo.getClass());
					  deliveryOrderInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(noticeInfo.getDeliveryOrderId(),deliveryOrderInfo.getClass());
					  System.out.println("deliveryorderinof.gettransactiontypeid()="+deliveryOrderInfo.getTransactionTypeId());
					  //������ʽ𻮲���һҵ�����͵�
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
							* Ϊ�˼���͸֧,��ApprovalTracingInfo���isFinish������ʱ��Ϊ�Ƿ�͸֧��־λ
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
				else if ( lResultID==Constant.ApprovalDecision.FINISH )  //������
				{
					nInfo.setNextCheckUserId(info.getNextUserID());
					
					lReturn=nodao.updateStatusID( nInfo );
					
					/**�ʽ𻮲�ҵ��**/
					try 
					{
						//������ʽ𻮲������ɣ�����ı�������
						SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
						DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
						SEC_NoticeDAO noticeDAO = new SEC_NoticeDAO();
						NoticeInfo noticeInfo = new NoticeInfo();
						//Ϊ�˻��deliveryid
						noticeInfo = (NoticeInfo) noticeDAO.findByID(nInfo.getId(),noticeInfo.getClass());
						deliveryOrderInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(noticeInfo.getDeliveryOrderId(),deliveryOrderInfo.getClass());
						System.out.println("deliveryorderinof.gettransactiontypeid()="+deliveryOrderInfo.getTransactionTypeId());
						//������ʽ𻮲���һҵ�����͵�
						if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85"))
						{
							DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
							try
							{
								System.out.println("before do save");
								/**
								* Ϊ�˼���͸֧,��ApprovalTracingInfo���isFinish������ʱ��Ϊ�Ƿ�͸֧��־λ
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
						
						//�������������ί�����(�漰֤ȯ)��ծȯ����(ծȯ��ȡ֪ͨ��)
						//��ҵ��,������������Ʒ�¼
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
	 * ֤�������������ʹ��  boxu add 2006-11-30
	 * ����ģ��ID��ҵ������ID������ѯ������������ID
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
			/*֤�����*/
			if ( ActionID == 1 )
			{
				long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,typeId);  //����
				lSubLoanTypeID=result[0];
				lActionID=result[1];
			}
			else if ( ActionID == 2 )
			{
				long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,typeId);  //ҵ��
				lSubLoanTypeID=result[0];
				lActionID=result[1];
			}
			else if ( ActionID == 3 )
			{
				long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,typeId);  //��ͬ
				lSubLoanTypeID=result[0];
				lActionID=result[1];
			}
			
			String sql="select approvalid from loan_approvalrelation ";
			sql+=" where moneysegment = (select max(Moneysegment) from loan_approvalrelation";
			sql+=" where moduleid="+moduleid+" and moneysegment<="+amount+" ";
			
			//�޸�
			sql+=" and actionid="+lActionID+" and subloantypeid = "+lSubLoanTypeID+" ";

			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +")";
			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +" ";
			//ZCQ ��2007-1-30��Ϊ��ʾ��һ�������˵Ĳ�ѯ�������ģ����������
			sql+=" and moduleid="+moduleid;
			//�޸�
			sql+=" and actionid="+lActionID+" and subloantypeid = "+lSubLoanTypeID+" ";
			
			sql+=" and approvalid in (select id from loan_approvalsetting where nstatusid=2 and nofficeid="+officeID+" and ncurrencyid="+ currencyID +" )";
			log.print("��ѯ������IDSQL====="+sql);
			System.out.println("��ѯ������IDSQL======="+sql);
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
	 * ����������ID���û�ID������û�����������
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
			log.print("��ѯ��������IDSQL====="+sql);
			System.out.println("��ѯ��������IDSQL====="+sql);
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
