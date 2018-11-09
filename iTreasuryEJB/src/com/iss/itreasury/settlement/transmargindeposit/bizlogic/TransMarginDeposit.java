/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transmargindeposit.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author gqfang
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransMarginDeposit extends javax.ejb.EJBObject
{

	/** ��֤���� - ���� start* */
	// �������׵ı���
	public long openSave(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// �������׵��ݴ�
	public long openTempSave(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// �������׵�ƥ��
	public Collection openMatch(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// �������׵����Ӳ���
	public Collection openFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;


	// �������׵ĸ���ID����
	public TransMarginOpenInfo openFindByID(long lID) throws IRollbackException, RemoteException;


	// �������׵ĸ��ݽ��׺Ų���
	public TransMarginOpenInfo openFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;


	// �������׵ĸ���
	public long openCheck(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// �������׵�ȡ������
	public long openCancelCheck(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// �������׵�ɾ��
	public long openDelete(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	/** ��֤���� - ���� end* */

	
	
	
	/** ��֤���� - ֧ȡ start* */
	// ֧ȡ���׵ı���
	public long drawSave(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// ֧ȡ���׵��ݴ�
	public long drawTempSave(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// ֧ȡ���׵�ƥ��
	public Collection drawMatch(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// ֧ȡ���׵����Ӳ���
	public Collection drawFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;


	// ֧ȡ���׵ĸ��ݽ��׺Ų���
	public TransMarginWithdrawInfo drawFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;


	// ֧ȡ���׵ļ���
	public TransMarginWithdrawInfo drawNext(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// ֧ȡ���׵ĸ���ID����
	public TransMarginWithdrawInfo drawFindByID(long lID) throws IRollbackException, RemoteException;


	// ֧ȡ���׵ĸ���
	public long drawCheck(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// ֧ȡ���׵�ȡ������
	public long drawCancelCheck(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// ֧ȡ���׵�ɾ��
	public long drawDelete(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;
	/** ��֤���� - ֧ȡ end* */
    
	
	
    //	����������������/
	public long doApproval(TransMarginOpenInfo info)throws RemoteException, IRollbackException;
    //	����������������/
	public long cancelApproval(TransMarginOpenInfo info)throws RemoteException, IRollbackException;
	public long cancelApproval(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	
	
	//����������֧ȡ��/
	public long doApproval(TransMarginWithdrawInfo info)throws RemoteException, IRollbackException;
}
