package com.iss.itreasury.creditrating.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;

import com.iss.itreasury.creditrating.creditrating.bizlogic.CreditRating;
import com.iss.itreasury.creditrating.creditrating.bizlogic.CreditRatingHome;
import com.iss.itreasury.creditrating.creditrating.dataentity.CreditRatingInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.system.dao.PageLoader;

public class CreditRatingDelegation
{
    private CreditRating creditRatingFacade =null;
    
    
    /**
     * Constructor for CreditRatingDelegation.
     */
    public CreditRatingDelegation() throws RemoteException, IRollbackException
    {
        try
        {

        	CreditRatingHome home =  (CreditRatingHome) EJBHomeFactory.getFactory().lookUpHome(CreditRatingHome.class);
            
        	creditRatingFacade =  home.create();
            
        }
        catch (RemoteException e)
        {
            throw e;
        }
        catch (IException e)
        {
            e.printStackTrace();
            e.printStackTrace();throw new RemoteException();
        }
        catch (CreateException e)
        {
            e.printStackTrace();
            e.printStackTrace();throw new RemoteException();
        }
    }
    /**
     * �����һ���õ����
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public String nextOneStep(CreditRatingInfo info) throws RemoteException,IRollbackException
	{
		return creditRatingFacade.nextOneStep(info);	
	}
    /**
     * ͨ��ID,��������������Ϣ������
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public ITreasuryBaseDataEntity getCreditRatingByCondition(long ID,Class className) throws RemoteException,IRollbackException
	{
		return creditRatingFacade.getCreditRatingByCondition(ID,className);	
	}
    
    /**
     * �����һ������(2)
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long nextTwoStepSave(CreditRatingInfo info) throws RemoteException,IRollbackException
	{
		return creditRatingFacade.nextTwoStepSave(info);	
	}
    /**
     * �������
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long save(CreditRatingInfo info) throws RemoteException,IRollbackException
	{
		return creditRatingFacade.save(info);	
	}
    /**
     * ͨ����������ID�����������ĵ��������������AAA��
     * @param ratingID
     * @param ratingnumeric
     * @return
     * @throws IRollbackException
     * @throws RemoteException
     */
    public String findSubtandardratingNameByRatingID(long ratingProjectID,double ratingnumeric)throws IRollbackException, RemoteException 
    {
    	return creditRatingFacade.findSubtandardratingNameByRatingID(ratingProjectID,ratingnumeric);	
    }
    /**
     * ͨ����������ID,�õ������ӱ���Ϣ
     * @param ratingID
     * @return
     * @throws IRollbackException
     * @throws RemoteException
     */
    public Map findSubCreditRatingValueByRatingID(long ratingID)throws IRollbackException, RemoteException 
    {
    	return creditRatingFacade.findSubCreditRatingValueByRatingID(ratingID);	
    }
    /**
	 * ͨ����������ID,�õ�������׼���ƣ�����AAA;;AA;;A��
	 * @param ratingProjectID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findSubtandardratingNamesByProjectID(long ratingProjectID)throws IRollbackException, RemoteException 
	{
		return creditRatingFacade.findSubtandardratingNamesByProjectID(ratingProjectID);	
	}
    /**
     * ����
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long doApproval(CreditRatingInfo info) throws RemoteException,IRollbackException
	{
		return creditRatingFacade.doApproval(info);	
	}
    /**
     * ȡ������
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long cancelApproval(CreditRatingInfo info) throws RemoteException,IRollbackException
	{
		return creditRatingFacade.cancelApproval(info);	
	}
   
}