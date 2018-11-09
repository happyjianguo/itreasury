/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.bizlogic;
import java.rmi.RemoteException;
import java.util.Vector;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationAssembler;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author Yuqiangliao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransSpecial extends javax.ejb.EJBObject
{
	public long presave(TransSpecialOperationInfo info) throws RemoteException, IRollbackException;

	public long tempsave(TransSpecialOperationInfo info) throws RemoteException, IRollbackException;

	public long save(TransSpecialOperationInfo info) throws RemoteException, IRollbackException;

	public boolean delete(TransSpecialOperationInfo info) throws RemoteException, IRollbackException;

	public SpecialOperationInfo findSettingDetailByID(long lSettingID) throws RemoteException, IRollbackException;

	public long check(TransSpecialOperationInfo info) throws RemoteException, IRollbackException;

	public long cancelCheck(TransSpecialOperationInfo info) throws RemoteException, IRollbackException;

	public TransSpecialOperationInfo match(TransSpecialOperationInfo info, long specialoperationinfoid) throws RemoteException, IRollbackException;

	public Vector findByStatus(QueryByStatusConditionInfo info, String strOrderByName, boolean isDesc) throws RemoteException, IRollbackException;
	
	public Vector findBySubtransSpecialIDandStatus(QueryBySubSpecialTypeAndStatusInfo info, String strOrderByName, boolean isDesc) throws RemoteException, IRollbackException;

	public TransSpecialOperationInfo findDetailByID(long lTransID) throws RemoteException, IRollbackException;

	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException;
	
	public TransSpecialOperationAssembler saveAndCheckAutomatically(TransSpecialOperationAssembler assemble)throws RemoteException, IRollbackException;
	
	public long doApproval(TransSpecialOperationInfo tsoi)throws RemoteException, IRollbackException;
	
	public long cancelApproval(TransSpecialOperationInfo tsoi)throws RemoteException, IRollbackException;
}
