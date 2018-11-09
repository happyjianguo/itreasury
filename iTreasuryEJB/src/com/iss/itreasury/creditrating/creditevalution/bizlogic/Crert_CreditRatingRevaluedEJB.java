package com.iss.itreasury.creditrating.creditevalution.bizlogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.creditrating.becominginvalid.dao.Crert_CreditRatingCancelDAO;
import com.iss.itreasury.creditrating.creditevalution.dao.Crert_CreditRatingRevaluedDDAO;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingRevaluedInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

public class Crert_CreditRatingRevaluedEJB implements SessionBean {

	private SessionContext context;
	
	private Log4j log4j = new Log4j(Constant.ModuleType.CREDITRATING, this);
	
	private Crert_CreditRatingRevaluedDDAO crert_CreditRatingRevaluedDDAO = new Crert_CreditRatingRevaluedDDAO();
	
	private Crert_CreditRatingCancelDAO crert_CreditRatingCancelDAO = null;
	
	public Crert_CreditRatingRevaluedEJB() 
	{
		
	}  

	private Log4j log = new Log4j(Constant.ModuleType.CREDITRATING, this);

	/**
	 * ejbActivate method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbActivate() throws RemoteException
	{
		
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                �쳣˵����
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
		
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbPassivate() throws RemoteException
	{
		
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbRemove() throws RemoteException
	{
		
	}

	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		  return context;
	}

	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)throws RemoteException
	{
		   context = ctx;
	}
    
	public long save(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException 
	{
		long crert_CreditRatingRevaluedID = -1;
		
		crert_CreditRatingCancelDAO = new Crert_CreditRatingCancelDAO();
		
		try{
			
			if(crert_CreditRatingRevaluedDDAO.checkRatingCode(crert_CreditRatingRevaluedInfo.getRatingCode(),crert_CreditRatingRevaluedInfo.getID()))
			{
				throw new IException("ҵ�����ظ�,�����޸ģ�");
			}
			if(crert_CreditRatingRevaluedDDAO.checkSave(crert_CreditRatingRevaluedInfo.getCreditRatingID()))
			{
				throw new IException("�����������������������ع�����������,���ܱ��棡");
			}
			if(crert_CreditRatingCancelDAO.checkSave(crert_CreditRatingRevaluedInfo.getCreditRatingID()))
			{
				throw new IException("�����������������ϲ��������У����ܱ��棡");
			}
			if(crert_CreditRatingRevaluedInfo.getID() > 0)
			{
				crert_CreditRatingRevaluedID = crert_CreditRatingRevaluedDDAO.doUpdate(crert_CreditRatingRevaluedInfo);
			}
			else
			{
				crert_CreditRatingRevaluedID = crert_CreditRatingRevaluedDDAO.doInsert(crert_CreditRatingRevaluedInfo);
			}
			
			if(crert_CreditRatingRevaluedInfo.getInutParameterInfo() != null)
			{
				log4j.info("--------------------------------�ύ����--------------------------------------");
				InutParameterInfo inutParameterInfo = crert_CreditRatingRevaluedInfo.getInutParameterInfo();
				inutParameterInfo.setUrl(inutParameterInfo.getUrl()+crert_CreditRatingRevaluedID);
				inutParameterInfo.setTransID(String.valueOf(crert_CreditRatingRevaluedID));
				inutParameterInfo.setDataEntity(crert_CreditRatingRevaluedInfo);
				FSWorkflowManager.initApproval(inutParameterInfo);
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
		}
		
		
		return crert_CreditRatingRevaluedID;
	}
	
	public Crert_CreditRatingDetailInfo findByIDCreditRatingDetail(long creditID)throws IRollbackException,RemoteException
	{
		Crert_CreditRatingDetailInfo crert_CreditRatingDetailInfo = null;
		try{
			
			crert_CreditRatingDetailInfo = crert_CreditRatingRevaluedDDAO.findByIDCreditRatingDetail(creditID);
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
		}
		return crert_CreditRatingDetailInfo;
	}
	
	public Crert_CreditRatingRevaluedInfo findByID(long ID)throws IRollbackException,RemoteException
	{
		Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo = null;
        try{
			
        	crert_CreditRatingRevaluedInfo = crert_CreditRatingRevaluedDDAO.findByID(ID);
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
		}
		return crert_CreditRatingRevaluedInfo;
	}

	
	public long doApproval(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo1 = new InutParameterInfo(); 
		InutParameterInfo inutParameterInfo2 =  crert_CreditRatingRevaluedInfo.getInutParameterInfo();
		try{
			Crert_CreditRatingRevaluedInfo creditRatingRevaluedInfo = new Crert_CreditRatingRevaluedInfo();
			creditRatingRevaluedInfo = this.findByID(crert_CreditRatingRevaluedInfo.getID());
			inutParameterInfo2.setDataEntity(creditRatingRevaluedInfo);
			inutParameterInfo1 = FSWorkflowManager.doApproval(inutParameterInfo2);
			
			if(inutParameterInfo1.isLastLevel())
			{
				Crert_CreditRatingRevaluedDDAO crert_CreditRatingRevaluedDDAO = new Crert_CreditRatingRevaluedDDAO();
				
				//����ͨ����������������ع����״̬�������ع�����
				
				long result = crert_CreditRatingRevaluedDDAO.updateStatus(CreRtConstant.CreRtStatus.APPROVALED,creditRatingRevaluedInfo.getID());
				
				if(result > 0)
				{
					//�����ع�״̬�ɹ�������ع����������������������������
					crert_CreditRatingRevaluedDDAO.updateRatingResult(creditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().getID(),creditRatingRevaluedInfo.getRevalueDresult());
				}
				
			}
			else if(inutParameterInfo1.isRefuse())
			{
				//���һ�����������������ܾ�,��״̬����Ϊ�ѱ���
				crert_CreditRatingRevaluedDDAO.updateStatus(CreRtConstant.CreRtStatus.SAVE,creditRatingRevaluedInfo.getID());
			}
			
			
			lReturn  = creditRatingRevaluedInfo.getID();
			
		}catch(Exception e){		
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
			
		}
		
		return lReturn;
	}
	
	public long cancelApproval(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException
	{
		long lReturn  = -1;
		Crert_CreditRatingRevaluedDDAO crert_CreditRatingRevaluedDDAO = new Crert_CreditRatingRevaluedDDAO();
		InutParameterInfo inutParameterInfo =  crert_CreditRatingRevaluedInfo.getInutParameterInfo();
		try{
			Crert_CreditRatingRevaluedInfo creditRatingRevaluedInfo = new Crert_CreditRatingRevaluedInfo();
			creditRatingRevaluedInfo = this.findByID(crert_CreditRatingRevaluedInfo.getID());
			
			if(crert_CreditRatingRevaluedDDAO.checkCancelApproval(crert_CreditRatingRevaluedInfo.getID()))
			{
				throw new IException("�������ع������������ع����ڵ��ع���¼,����ȡ������!");
			}
			//ȡ�������󽫽������Ϊ�ѱ��档
			crert_CreditRatingRevaluedDDAO.updateStatus(CreRtConstant.CreRtStatus.SAVE,creditRatingRevaluedInfo.getID());
			
			//������������е������������Ϊ�ع�ǰ�Ľ��
			crert_CreditRatingRevaluedDDAO.updateRatingResult(creditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().getID(),creditRatingRevaluedInfo.getRatingOldResult());
			
			if(inutParameterInfo.getApprovalEntryID() > 0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			
			lReturn = creditRatingRevaluedInfo.getID();
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
			
		}
		
		return lReturn;
	}
	public long delete(long ID)throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		try{
			
			lReturn = crert_CreditRatingRevaluedDDAO.updateStatus(CreRtConstant.CreRtStatus.DELETE,ID);
			
		}catch(Exception e){
		  
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
			
		}
		return lReturn;
	}
	
    public List getCreditRatingRevaluedList(long creditRatingID)throws IRollbackException,RemoteException
    {
    	List creditRatingRevaluedList = new ArrayList();
    	try{
    		
    		creditRatingRevaluedList = crert_CreditRatingRevaluedDDAO.getCreditRatingRevaluedList(creditRatingID);
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		throw new IRollbackException(context,e.getMessage(),e);
    		
    	}
        return creditRatingRevaluedList;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
