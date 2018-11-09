package com.iss.itreasury.settlement.transfinancialmargin.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.util.IRollbackException;


public interface TransFinancialMargin extends javax.ejb.EJBObject
{
	// �������ޱ�֤�𱣺���֧ȡ���׵��ݴ�
	public long drawTempSave(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	// �������ޱ�֤�𱣺���֧ȡ���׵ı���
	public long drawSave(TransFinancialMarginInfo info) throws IRollbackException,RemoteException;
	
	// �������ޱ�֤�𱣺���֧ȡ���׵�ƥ��
	public Collection drawMatch(TransFinancialMarginInfo info,long typeFlag) throws IRollbackException, RemoteException;
	
	// �������ޱ�֤�𱣺��� ��ID������
	public TransFinancialMarginInfo drawFindByID(long ID) throws IRollbackException, RemoteException;
	
	// �������ޱ�֤�𱣺��� ����
	public long drawCheck(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	// �������ޱ�֤�𱣺��� ��״̬������ ҵ�񸴺�
	public Collection drawFindByStatus4Check(TransFinancialMarginInfo info,long[] lStatusIDs) throws IRollbackException, RemoteException;
	// �������ޱ�֤�𱣺��� ��״̬������ ҵ����
	public Collection drawFindByStatus4Deal(TransFinancialMarginInfo info,long[] lStatusIDs) throws IRollbackException, RemoteException;
   
	// �������ޱ�֤�𱣺��� ȡ��
	public long drawCancel(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	// �������ޱ�֤�𱣺��� ȡ������
	public long drawCancelCheck(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	//�������ޱ�֤�𱣺��� ���ݽ��׺Ų�ѯ
	public TransFinancialMarginInfo findByTransNO(String strTransNO) throws IRollbackException, RemoteException;
}


