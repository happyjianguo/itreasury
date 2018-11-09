package com.iss.itreasury.ebank.bizdelegation;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;

import com.iss.itreasury.ebank.obbudget.bizlogic.OBBudget;
import com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetHome;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class OBBudgetDelegation {
	private OBBudget obBudget = null;
	//构造函数
	public OBBudgetDelegation() throws RemoteException{
		try{
			OBBudgetHome home = (OBBudgetHome) EJBHomeFactory.getFactory().lookUpHome(OBBudgetHome.class);
			obBudget = home.create();
		}catch (RemoteException e){
			throw e;
		}catch (IException e){
			e.printStackTrace();
			throw new RemoteException();
		}catch (CreateException e){
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	
	//用款新增时增加主记录和子记录
	public long saveAll(OBBudgetInfo info,List listDates,List listAmounts) throws IException{
		try{
			return obBudget.saveAll(info, listDates, listAmounts);
		
		}catch(Exception e){
			
			throw new IException("用款新增出现异常，操作已取消",e);			
			
		}
	}
	
	//用款调整时增加主记录和子记录，同时修改原主记录和子记录
	public long saveAdjust(OBBudgetInfo info,List list,List listAmounts) throws IException{
		try{
			return obBudget.saveAdjust(info,list,listAmounts);
		}catch (Exception e) {
			throw new IException("用款调整新增出现异常，操作已取消",e);	
		}
	}
	
	/**
	 * 查询时修改已保存状态的主记录和子记录
	 */
	public long updateBudget(OBBudgetInfo info,List list,List listAmounts) throws IException{
		try{
			return obBudget.updateBudget(info,list,listAmounts);
		}catch (Exception e) {
			throw new IException("用款调整新增出现异常，操作已取消",e);	
		}
	}
	
	/**
	 * 新增审批，Added by zwsun, 2007/7/19 
	 * @param info
	 * @return
	 * @throws IException
	 */
	public long doApproval(OBBudgetInfo info)throws IException{
		try{
			return obBudget.doApproval(info);
		}catch (Exception e) {
			throw new IException("用款调整新增出现异常，操作已取消",e);	
		}		
	}
	/**
	 * 新增取消审批，Added by zwsun, 2007/7/19 
	 * @param info
	 * @return
	 * @throws IException
	 */
	public long cancelApproval(OBBudgetInfo info)throws IException{
		try{
			return obBudget.cancelApproval(info);
		}catch (Exception e) {
			throw new IException("用款调整新增出现异常，操作已取消",e);	
		}		
	}	
}
