package com.iss.itreasury.settlement.transfinancialmargin.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.util.IRollbackException;


public interface TransFinancialMargin extends javax.ejb.EJBObject
{
	// 融资租赁保证金保后处理支取交易的暂存
	public long drawTempSave(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	// 融资租赁保证金保后处理支取交易的保存
	public long drawSave(TransFinancialMarginInfo info) throws IRollbackException,RemoteException;
	
	// 融资租赁保证金保后处理支取交易的匹配
	public Collection drawMatch(TransFinancialMarginInfo info,long typeFlag) throws IRollbackException, RemoteException;
	
	// 融资租赁保证金保后处理 按ID来查找
	public TransFinancialMarginInfo drawFindByID(long ID) throws IRollbackException, RemoteException;
	
	// 融资租赁保证金保后处理 复核
	public long drawCheck(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	// 融资租赁保证金保后处理 按状态来查找 业务复核
	public Collection drawFindByStatus4Check(TransFinancialMarginInfo info,long[] lStatusIDs) throws IRollbackException, RemoteException;
	// 融资租赁保证金保后处理 按状态来查找 业务处理
	public Collection drawFindByStatus4Deal(TransFinancialMarginInfo info,long[] lStatusIDs) throws IRollbackException, RemoteException;
   
	// 融资租赁保证金保后处理 取消
	public long drawCancel(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	// 融资租赁保证金保后处理 取消复核
	public long drawCancelCheck(TransFinancialMarginInfo info) throws IRollbackException, RemoteException;
	
	//融资租赁保证金保后处理 根据交易号查询
	public TransFinancialMarginInfo findByTransNO(String strTransNO) throws IRollbackException, RemoteException;
}


