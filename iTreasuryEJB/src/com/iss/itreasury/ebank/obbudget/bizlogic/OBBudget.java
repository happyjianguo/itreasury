package com.iss.itreasury.ebank.obbudget.bizlogic;

import java.rmi.RemoteException;
import java.util.List;

import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.util.IRollbackException;

public interface OBBudget extends javax.ejb.EJBObject{
	/**
	 * ���������ÿ�ƻ�
	 */
	public long saveAll(OBBudgetInfo info,List listDates,List listAmounts) throws IRollbackException,RemoteException;
	
	/**
	 * �ÿ��������
	 */
	public long saveAdjust(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException;
	/**
	 * ��ѯʱ�޸��ѱ���״̬���ÿ�ƻ�
	 */
	public long updateBudget(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException;
	/**
	 * �ÿ�ƻ���������
	 */
	public long doApproval(OBBudgetInfo info)throws RemoteException, IRollbackException;
	public long cancelApproval(OBBudgetInfo financeInfo)throws RemoteException, IRollbackException;
}
