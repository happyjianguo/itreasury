/*
 * Created on 2006-4-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transnoteacceptance.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAdvancedReceviceNoteAcceptanceInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author feiye
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransNoteAcceptance extends javax.ejb.EJBObject
{

	/** ��ҵƱ�ݳж� - ���ڳж� start* */
	// ���ڳжҽ��׵ı���
	public long acceptanceSave(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵��ݴ�
	public long acceptanceTempSave(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵�ƥ��
	public Collection acceptanceMatch(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵����Ӳ���
	public Collection acceptanceFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵ĸ���ID����
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByID(long lID) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵ĸ��ݽ��׺Ų���
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵ĸ���
	public long acceptanceCheck(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵�ȡ������
	public long acceptanceCancelCheck(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// ���ڳжҽ��׵�ɾ��
	public long acceptanceDelete(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	//���ڳжҽ��׵ļ���
	public TransAcceptanceNoteAcceptanceInfo acceptanceNext(TransAcceptanceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	/** ��ҵƱ�ݳж� - ���ڳж� end* */
	
	
	/** ��ҵƱ�ݳж� - �渶�ջ� start* */
	// �渶�ջؽ��׵ı���
	public long advancedReceviceSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵��ݴ�
	public long advancedReceviceTempSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵�ƥ��
	public Collection advancedReceviceMatch(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵����Ӳ���
	public Collection advancedReceviceFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵ĸ��ݽ��׺Ų���
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵ļ���
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceNext(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵ĸ���ID����
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByID(long lID) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵ĸ���
	public long advancedReceviceCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	// �渶�ջؽ��׵�ȡ������
	public long advancedReceviceCancelCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;

	//  �渶�ջؽ��׵�ɾ��
	public long advancedReceviceDelete(TransAdvancedReceviceNoteAcceptanceInfo info) throws IRollbackException, RemoteException;
	/** ��ҵƱ�ݳж� - �渶�ջ� end* */
}
