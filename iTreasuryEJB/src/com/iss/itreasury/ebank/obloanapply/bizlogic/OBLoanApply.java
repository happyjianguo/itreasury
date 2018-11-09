/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.bizlogic;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;

import com.iss.itreasury.ebank.obloanapply.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obdataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface OBLoanApply extends EJBObject
{
	/**
	 * 新增一笔贷款申请信息
	 * @param o LoanCreateInfo 贷款新建信息
	 * @return 1:成功 -1 失败
	 * @throws RemoteException
	 * @throws IException
	 */
	public long add(OBLoanCreateInfo o) throws RemoteException,IException;
	
	/**
	 * 保存贷款基本信息
	 * @param o LoanBasicInfo 贷款基本信息
	 * @return 1:成功 -1 失败
	 * @throws RemoteException
	 * @throws IException
	 */
	public long updateBasic(OBLoanBasicInfo o)  throws RemoteException, IException;

	/**
	 * 保存贷款属性信息
	 * @param o LoanPropertyInfo 贷款属性信息
	 * @return 1:成功 -1 失败
	 * @throws RemoteException
	 * @throws IException
	 */
	public long updateProperty(OBLoanPropertyInfo o) throws RemoteException, IException;
	

	/**
	 * 更新保证信息
	 * @param aPlanID
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */    
	public long saveAssure(OBAssureInfo o) throws RemoteException, IException;

	/**
	 * 删除指定的保证信息
	 * @param aPlanID
	 * @param sInfo 安全验证信息
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */    
	public long deleteAssure(long aID[],OBSecurityInfo sInfo) throws RemoteException, IException;


	/**
	 * 根据情况（修改或保存）放还款计划
	 * @param aPlanID
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */    
	public long savePlan(Collection cList) throws RemoteException, IException;

	/**
	 * 删除贷款计划的详细信息
	 * @param aPlanID
	 * @param sInfo 安全信息
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long deletePlan(long aPlanID[],OBSecurityInfo sInfo) throws RemoteException, IException;

	/**
	 * 根据条件，自动安排放还款计划
	 * 根据条件，调用LoanRepayPlanDetailDao中的insert()，逐条插入
	 * @param aPlanID
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */    
	public long autoAddPlan(OBAutoPlanInfo apInfo) throws RemoteException, IException;
	
	/**
	 * 取消一笔贷款申请
	 * @param lLoanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long cancel(long lLoanID,OBSecurityInfo sInfo) throws RemoteException, IException;
	

	/**
	 * 提交贷款申请
	 * @param lLoanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */    
	public long commit(long lLoanID,OBSecurityInfo sInfo) throws RemoteException, IException;
	
	/**
	 * 根据贷款的ID来获得贷款申请的信息
	 * @param lLoanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public OBLoanApplyInfo findByID(long lLoanID,OBSecurityInfo sInfo) throws RemoteException, IException;


	/**
	 * 获得一条贷款保证信息
	 * @param aPlanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */    
	public OBAssureInfo findAssureByID(long aID,OBSecurityInfo sInfo) throws RemoteException, IException;
	

	/**
	* 查询某条贷款计划的详细信息
	* @param aPlanID
	* @param sInfo
	* @return
	* @throws RemoteException
	* @throws IException
	*/
	public OBLoanPlanDetailInfo findPlanByID(long aPlanID,OBSecurityInfo sInfo) throws RemoteException, IException;
	
	/**
	* 查询贷款申请的所有计划明细
	* 调用OBLoanRepayPlanDao.java的findByLoanID()
	* @param aPlanID
	* @param pInfo
	* @param sInfo
	* @return
	* @throws RemoteException
	* @throws IException
	*/    
   public Collection findPlanByLoanID(long lLoanID,OBPageInfo pInfo,OBSecurityInfo sInfo) throws RemoteException, IException;
	
}
