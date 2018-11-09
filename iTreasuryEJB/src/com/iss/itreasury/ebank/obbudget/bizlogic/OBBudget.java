package com.iss.itreasury.ebank.obbudget.bizlogic;

import java.rmi.RemoteException;
import java.util.List;

import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.util.IRollbackException;

public interface OBBudget extends javax.ejb.EJBObject{
	/**
	 * 新增所有用款计划
	 */
	public long saveAll(OBBudgetInfo info,List listDates,List listAmounts) throws IRollbackException,RemoteException;
	
	/**
	 * 用款调整新增
	 */
	public long saveAdjust(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException;
	/**
	 * 查询时修改已保存状态的用款计划
	 */
	public long updateBudget(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException;
	/**
	 * 用款计划新增审批
	 */
	public long doApproval(OBBudgetInfo info)throws RemoteException, IRollbackException;
	public long cancelApproval(OBBudgetInfo financeInfo)throws RemoteException, IRollbackException;
}
