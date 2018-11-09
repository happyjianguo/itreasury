package com.iss.itreasury.settlement.craftbrother.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

import com.iss.itreasury.settlement.craftbrother.dao.TransCraftbrotherDAO;
import com.iss.itreasury.settlement.craftbrother.dataentity.CraInterestCalcInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;

public interface TransCraftbrother extends EJBObject {
	
	/** 交易暂存和修改暂存接口 */
	public long tempSave(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException;

	/** 交易新增和修改保存接口 */
	public long save(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException;

	/** 交易删除接口 */
	public long delete(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException;

	/** ID查找交易 */
	public TransCraftbrotherInfo findByID(long lId) throws RemoteException;

	/** 匹配查找交易 */
	public TransCraftbrotherInfo match(TransCraftbrotherInfo info)
			throws RemoteException;

	/** 链接查找交易 */
	public Collection linkSearch(long lQueryPurpose,long lTransactionTypeId,long lStatusId, long lUserId,
			int nOrderIndex, boolean lIsDesc) throws RemoteException;

	/** 交易复核接口 */
	public long check(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException;

	/** 交易取消复核接口 */
	public long cancelCheck(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException;
	
	/**同业利息计算接口*/
	public Collection calcInterest(CraInterestCalcInfo calcInfo) 
			throws RemoteException, IRollbackException;

	/** 同业计提交易新增保存接口 */
	public long saveInterestPreDraw(TransCraInterestPreDrawInfo info)
			throws RemoteException, IRollbackException;

	/** 同业计提交易删除接口 */
	public long deleteInterestPreDraw(TransCraInterestPreDrawInfo info) throws RemoteException,
			IRollbackException;

	/** 结算同业利息计提交易查找接口 */
	public PageLoader searchTransInterestPerDraw(
			TransCraInterestPreDrawInfo info) throws RemoteException;
	/** 条件查找交易 */
	public TransCraftbrotherInfo findByTransNo(String sTransNo) throws RemoteException;

}
