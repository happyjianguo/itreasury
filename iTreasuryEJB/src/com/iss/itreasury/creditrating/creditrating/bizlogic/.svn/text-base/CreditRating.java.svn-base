/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.creditrating.creditrating.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.iss.itreasury.creditrating.creditrating.dataentity.CreditRatingInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.system.dao.PageLoader;
/**
 * @author zcwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface CreditRating extends javax.ejb.EJBObject
{
	public String nextOneStep(CreditRatingInfo info) throws IRollbackException,RemoteException;
	public ITreasuryBaseDataEntity getCreditRatingByCondition(long ID,Class className) throws IRollbackException,RemoteException;
	public long nextTwoStepSave(CreditRatingInfo info) throws IRollbackException,RemoteException;
	public long save(CreditRatingInfo info) throws IRollbackException,RemoteException;
	public long doApproval(CreditRatingInfo info) throws IRollbackException,RemoteException;
	public long cancelApproval(CreditRatingInfo info) throws IRollbackException,RemoteException;
    public String findSubtandardratingNameByRatingID(long ratingProjectID,double ratingnumeric)throws IRollbackException, RemoteException;
    public Map findSubCreditRatingValueByRatingID(long ratingID)throws IRollbackException, RemoteException ;
	public Collection findSubtandardratingNamesByProjectID(long ratingProjectID)throws IRollbackException, RemoteException ;
}
