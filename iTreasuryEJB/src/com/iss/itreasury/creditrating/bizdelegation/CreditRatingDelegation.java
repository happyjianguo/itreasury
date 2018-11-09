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
     * 点击下一步得到编号
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
     * 通过ID,返回信用评级信息（主）
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
     * 点击下一步保存(2)
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
     * 点击保存
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
     * 通过评级方案ID，评级分数的到评级结果（例：AAA）
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
     * 通过信用评级ID,得到评级子表信息
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
	 * 通过评级方案ID,得到评级标准名称（例：AAA;;AA;;A）
	 * @param ratingProjectID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findSubtandardratingNamesByProjectID(long ratingProjectID)throws IRollbackException, RemoteException 
	{
		return creditRatingFacade.findSubtandardratingNamesByProjectID(ratingProjectID);	
	}
    /**
     * 审批
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
     * 取消审批
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