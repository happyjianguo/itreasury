package com.iss.itreasury.creditrating.becominginvalid.bizlogic;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.creditrating.becominginvalid.dao.Crert_CreditRatingCancelDAO;
import com.iss.itreasury.creditrating.becominginvalid.dataentity.Crert_CreditRatingCancelInfo;
import com.iss.itreasury.creditrating.creditevalution.dao.Crert_CreditRatingRevaluedDDAO;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.FSWorkflowManager;

public class Crert_CreditRatingCancelEJB implements SessionBean {

    private SessionContext context;
	
	private Crert_CreditRatingRevaluedDDAO crert_CreditRatingRevaluedDDAO = null;
	
	private Crert_CreditRatingCancelDAO crert_CreditRatingCancelDAO = null;
	
	public Crert_CreditRatingCancelEJB() 
	{
		
	}

	private Log4j log = new Log4j(Constant.ModuleType.CREDITRATING, this);

	/**
	 * ejbActivate method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbActivate() throws RemoteException
	{
		
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
		
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws RemoteException
	{
		
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
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
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)throws RemoteException
	{
		   context = ctx;
	}
	
	public long save(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException 
	{
		long lReturn = -1;
		crert_CreditRatingCancelDAO = new Crert_CreditRatingCancelDAO();
		crert_CreditRatingRevaluedDDAO = new Crert_CreditRatingRevaluedDDAO();
		try{
			
			if(crert_CreditRatingCancelDAO.checkCancelRatingCode(crert_CreditRatingCancelInfo.getRatingCode(),crert_CreditRatingCancelInfo.getID()))
			{
				throw new IException("业务编号重复,返回修改！");
			}
			if(crert_CreditRatingRevaluedDDAO.checkSave(crert_CreditRatingCancelInfo.getRatingProjectID()))
			{
				throw new IException("此信用评级已经做过信用重估操作,不能保存！");
			}
			if(crert_CreditRatingCancelDAO.checkSave(crert_CreditRatingCancelInfo.getRatingProjectID()))
			{
				throw new IException("此信用评级已经做过作废操作,不能保存！");
			}
			if(crert_CreditRatingCancelInfo.getID() > 0)
			{
				lReturn = crert_CreditRatingCancelDAO.doUpdate(crert_CreditRatingCancelInfo);
			}
			else
			{
				lReturn = crert_CreditRatingCancelDAO.doInsert(crert_CreditRatingCancelInfo);
			}
			
			if(crert_CreditRatingCancelInfo.getInutParameterInfo() != null)
			{
				InutParameterInfo inutParameterInfo = crert_CreditRatingCancelInfo.getInutParameterInfo();
				inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturn);
				inutParameterInfo.setTransID(String.valueOf(lReturn));
				inutParameterInfo.setDataEntity(crert_CreditRatingCancelInfo);
				FSWorkflowManager.initApproval(inutParameterInfo);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
		}
		return lReturn;
	}
	
	
	
	public Crert_CreditRatingDetailInfo getCrert_CreditRatingDetailInfo(long ID)throws IRollbackException,RemoteException
	{
		crert_CreditRatingRevaluedDDAO = new Crert_CreditRatingRevaluedDDAO();
		Crert_CreditRatingDetailInfo crert_CreditRatingDetailInfo = new Crert_CreditRatingDetailInfo();
		
		try{
			
			crert_CreditRatingDetailInfo = crert_CreditRatingRevaluedDDAO.findByIDCreditRatingDetail(ID);
			crert_CreditRatingDetailInfo.setCreditRatingRevaluedList(crert_CreditRatingRevaluedDDAO.getCreditEvalutionByCondition(crert_CreditRatingDetailInfo.getID()));
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
		}
		
	    return crert_CreditRatingDetailInfo;
	}
	
	public Crert_CreditRatingCancelInfo getCreditRatingCancelByCondition(long ID)throws IRollbackException,RemoteException
	{
		Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo = null;
		crert_CreditRatingCancelDAO = new Crert_CreditRatingCancelDAO(); 
		crert_CreditRatingRevaluedDDAO = new Crert_CreditRatingRevaluedDDAO();
		try{
			
			crert_CreditRatingCancelInfo = crert_CreditRatingCancelDAO.getCreditRatingCancelByCondition(ID);
			crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setCreditRatingRevaluedList(crert_CreditRatingRevaluedDDAO.getCreditEvalutionByCondition(crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().getID()));
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
			
		}
		return crert_CreditRatingCancelInfo;
	}
	
	public long doApproval(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo1 = new InutParameterInfo();
		InutParameterInfo inutParameterInfo2 = crert_CreditRatingCancelInfo.getInutParameterInfo();
		try{
			
			crert_CreditRatingCancelDAO = new Crert_CreditRatingCancelDAO(); 
			Crert_CreditRatingCancelInfo creditRatingCancelInfo = this.getCreditRatingCancelByCondition(crert_CreditRatingCancelInfo.getID());
			inutParameterInfo2.setDataEntity(creditRatingCancelInfo);
			inutParameterInfo1 = FSWorkflowManager.doApproval(inutParameterInfo2);
			
			if(inutParameterInfo1.isLastLevel())
			{
				
				if(crert_CreditRatingCancelDAO.updateStatus(CreRtConstant.CreRtStatus.APPROVALED,creditRatingCancelInfo.getID()) > 0)
				{
					crert_CreditRatingCancelDAO.updateCreditRatingStatus(CreRtConstant.CreRtStatus.CANCEL,creditRatingCancelInfo.getCrert_CreditRatingDetailInfo().getID());
				}
				
			}
			else if(inutParameterInfo1.isRefuse())
			{
				crert_CreditRatingCancelDAO.updateStatus(CreRtConstant.CreRtStatus.SAVE,creditRatingCancelInfo.getID());
			}
			
			lReturn = creditRatingCancelInfo.getID();
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
		}
		
		return lReturn;
	}
	
	public long cancelApproval(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException
	{
		long lReturn  = -1;
		
		InutParameterInfo inutParameterInfo = crert_CreditRatingCancelInfo.getInutParameterInfo();
		
		try{
			
			crert_CreditRatingCancelDAO = new Crert_CreditRatingCancelDAO(); 
			
			Crert_CreditRatingCancelInfo creditRatingCancelInfo = getCreditRatingCancelByCondition(crert_CreditRatingCancelInfo.getID());
			
			if(crert_CreditRatingCancelDAO.updateStatus(CreRtConstant.CreRtStatus.SAVE,creditRatingCancelInfo.getID()) > 0)
			{
				crert_CreditRatingCancelDAO.updateCreditRatingStatus(CreRtConstant.CreRtStatus.APPROVALED,creditRatingCancelInfo.getCrert_CreditRatingDetailInfo().getID());
			}
			if(inutParameterInfo.getApprovalEntryID() > 0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			
			lReturn  = creditRatingCancelInfo.getID();
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IRollbackException(context,e.getMessage(),e);
		}
		
		return lReturn;
	}
	
	public long updateStatus(long lStatus,long ID)throws IRollbackException,RemoteException
	{
	    long lReturn  = -1;
	    crert_CreditRatingCancelDAO = new Crert_CreditRatingCancelDAO(); 
	    try{
	    	
	        lReturn = 	crert_CreditRatingCancelDAO.updateStatus(lStatus,ID);
	    	
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    	throw new IRollbackException(context,e.getMessage(),e);
	    	
	    }
	    
	    return lReturn;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
