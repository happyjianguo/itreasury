package com.iss.itreasury.settlement.craftbrother.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.craftbrother.dao.TransCraInterestPreDrawDAO;
import com.iss.itreasury.settlement.craftbrother.dataentity.CraInterestCalcInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.SumCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;


/**
 * 同业交易结算处理
 * @author qhzhou
 *
 */
public class TransCraftbrotherDelegation {
	
	TransCraftbrother facede = null;
	
	public TransCraftbrotherDelegation() throws RemoteException
	{
		try
		{
			TransCraftbrotherHome home = (TransCraftbrotherHome) EJBHomeFactory.getFactory().lookUpHome(TransCraftbrotherHome.class);
			facede = (TransCraftbrother) home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}
	
	/** 交易暂存和修改暂存接口 */
	public long tempSave(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.tempSave(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 交易新增和修改保存接口 */
	public long save(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.save(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 交易删除接口 */
	public long delete(TransCraftbrotherInfo info) throws IException{
		
		try{
			return facede.delete(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ID查找交易 */
	public TransCraftbrotherInfo findByID(long lId) throws IException {
		try{
			return facede.findByID(lId);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 匹配查找交易 */
	public TransCraftbrotherInfo match(TransCraftbrotherInfo info)throws IException {
		try{
			return facede.match(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 链接查找交易 */
	public Collection linkSearch(long lQueryPurpose,long lTransactionTypeId,long lStatusId, long lUserId,
			int nOrderIndex, boolean lIsDesc) throws IException {
		try{
			return facede.linkSearch(lQueryPurpose,lTransactionTypeId,lStatusId, lUserId, nOrderIndex, lIsDesc);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 交易复核接口 */
	public long check(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.check(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 交易取消复核接口 */
	public long cancelCheck(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.cancelCheck(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/**同业利息计算接口*/
	public Collection calcInterest(CraInterestCalcInfo calcInfo) throws IException{
		try{
			return facede.calcInterest(calcInfo);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	
	/** 同业计提交易新增保存接口 */
	public long saveInterestPreDraw(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return facede.saveInterestPreDraw(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 同业计提交易删除接口 */
	public long deleteInterestPreDraw(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return facede.deleteInterestPreDraw(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** 结算同业利息计提交易查找接口 */
	public PageLoader searchTransInterestPerDraw(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return facede.searchTransInterestPerDraw(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/** 结算同业利息计提交易查汇总信息 */
	public SumCraInterestPreDrawInfo getSumCraPreDrawInterest(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return new TransCraInterestPreDrawDAO().getSumCraPreDrawInterest(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/** 结算同业利息计提交易单笔查询 */
	public TransCraInterestPreDrawInfo findCraInterestPreDrawById(long id) throws IException{
		try{
			return (TransCraInterestPreDrawInfo)(new TransCraInterestPreDrawDAO().findByID(id, TransCraInterestPreDrawInfo.class));
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/** 条件查找交易 */
	public TransCraftbrotherInfo findByTransNo(String sTransNo) throws IException {
		try{
			return facede.findByTransNo(sTransNo);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
}
