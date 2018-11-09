package com.iss.itreasury.settlement.transadjustinterest.bizlogic;
/**
 * @author zwsun, 2007/7/9
 */
import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfo;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.IRollbackException;

public class TransAdjustInterestEJB implements SessionBean{
	private final static long serialVersionUID = 3206093459760846175L;
	private SessionContext ctx;
	
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException{
		
	}	
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		this.ctx = ctx;
	}
	/**
	 * ������Ϣ
	 * @param info
	 * @return
	 * @throws IRollbackException
	 */
	public long addAdjust(AdjustInterestInfo info) throws IRollbackException, RemoteException{
		long result=-1;
		long sessionID=-1;
		UtilOperation utilOperation = new UtilOperation();
		try{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getLAccountID());
			
			result=new TransAdjustInterestBiz().addAdjust(info);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
		}finally{
			//����
			try{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLAccountID(), sessionID);
			}catch (Exception e){
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
			}
		}		
		return result;
	}
	/**
	 * ɾ����Ϣ
	 * @param lID
	 * @return
	 * @throws IRollbackException
	 */
    public long delAdjust(long lID) throws IRollbackException,RemoteException{
		long result=-1;
		try{			
			result=new TransAdjustInterestBiz().delAdjust(lID);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
		}		
		return result;
    }
    /**
     * ֵ����
     * @param adjustInfo
     * @return
     * @throws IRollbackException
     */
    public long valueAdjust(AdjustInterestInfo info) throws IRollbackException, RemoteException{
		long result=-1;
		long sessionID=-1;
		UtilOperation utilOperation = new UtilOperation();
		try{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getLAccountID());
			
			result=new TransAdjustInterestBiz().valueAdjust(info);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
		}finally{
			//����
			try{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLAccountID(), sessionID);
			}catch (Exception e){
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
			}
		}		
		return result;    	
    }
    /**
     * ����
     * @param info
     * @return
     */
	public long doApproval(AdjustInterestInfo info) throws IRollbackException, RemoteException{
		long result=-1;
		long sessionID=-1;
		UtilOperation utilOperation = new UtilOperation();
		try{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getLAccountID());
			
			result=new TransAdjustInterestBiz().doApproval(info);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
		}finally{
			//����
			try{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLAccountID(), sessionID);
			}catch (Exception e){
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
			}
		}		
		return result;
	} 
	/**
	 * ȡ������
	 * @param info
	 * @return
	 * @throws IRollbackException
	 */
	public long cancelApproval(AdjustInterestInfo info) throws IRollbackException, RemoteException{
		long result=-1;
		long sessionID=-1;
		UtilOperation utilOperation = new UtilOperation();
		try{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getLAccountID());
			
			result=new TransAdjustInterestBiz().cancelApproval(info);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
		}finally{
			//����
			try{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLAccountID(), sessionID);
			}catch (Exception e){
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
			}
		}		
		return result;
	} 
	/**
	 * ����
	 * @param info
	 * @return
	 * @throws IRollbackException
	 */
    public long check(AdjustInterestInfo info, boolean isCancel) throws IRollbackException, RemoteException{
		long result=-1;
		long sessionID=-1;
		UtilOperation utilOperation = new UtilOperation();
		try{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getLAccountID());
			
			result=new TransAdjustInterestBiz().check(info, isCancel);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
		}finally{
			//����
			try{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLAccountID(), sessionID);
			}catch (Exception e){
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001",e.getMessage());
			}
		}		
		return result;
	} 	
}
