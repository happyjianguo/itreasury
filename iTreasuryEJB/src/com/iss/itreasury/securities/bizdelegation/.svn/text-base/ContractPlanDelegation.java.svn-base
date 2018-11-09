/*
 * Created on 2004-4-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
 
 
package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import java.util.*;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.securitiescontractplan.bizlogic.*;
import com.iss.itreasury.securities.securitiescontractplan.dao.*;
import com.iss.itreasury.securities.securitiescontractplan.dataentity.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.system.approval.dataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractPlanDelegation {

	private SecuritiesContractPlan planFacade = null;
	
	public ContractPlanDelegation() throws RemoteException
	{
		try
		{
			SecuritiesContractPlanHome home;
			try
			{
				home =
					(SecuritiesContractPlanHome) EJBHomeFactory
						.getFactory()
						.lookUpHome(
						SecuritiesContractPlanHome.class);
			} catch (IException e)
			{
				throw new RemoteException("EJBHomeFactory连接错误", e);
			}
			planFacade = (SecuritiesContractPlan) home.create();
		} catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException", ce);
		}

	}

	/**
	 * 修改执行计划-查找合同
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */    
	public java.util.Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return planFacade.findByMultiOption( qInfo );
	}

	/**
	 * 根据合同查找合同的版本列表
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findPlanVerByContract(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
		return planFacade.findPlanVerByContract(lID);
	}
	
	/**
	 * 根据执行计划版本查找该版本计划明细列表
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findPlanDetailByVer(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
		return planFacade.findPlanDetailByVer(lID);
	}
	
	/**
	 * 根据合同查找最新版本的计划明细
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findPlanDetailByContract(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
		return planFacade.findPlanDetailByContract( lID );
	}
	
	/**
	 * 根据明细ID查找明细的详细内容
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractPlanDetailInfo findByID(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
		return planFacade.findByID( lID );
	}
	
	/**
	 * 自动安排计划
	 * @param autoInfo
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void autoSavePlan(SecuritiesContractAutoPlanInfo autoInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		planFacade.autoSavePlan( autoInfo );
	}
	
	/**
	 * 保存计划明细，包括新增和修改
	 * @param c
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void savePlanDetail(java.util.Collection c) throws java.rmi.RemoteException,SecuritiesException
	{
		planFacade.savePlanDetail( c );
	}
	
	/**
	 * 删除计划明细，支持批量删除
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void deletePlanDetail(long lID[]) throws java.rmi.RemoteException,SecuritiesException
	{
		planFacade.deletePlanDetail( lID );
	}
	
	/**
	 * 在从就版本修改时需要先生成一个新版本并复制明细，然后修改新版本的明细
	 * @param lID
	 * @param lContractID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long createPlanVersion(long lID,long lContractID) throws java.rmi.RemoteException,SecuritiesException
	{
		return planFacade.createPlanVersion( lID,lContractID );
	}

	public SecuritiesContractPlanVersionInfo findPlanVersionByID(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
		SecuritiesContractPlanDao dao = new SecuritiesContractPlanDao();
		SecuritiesContractPlanVersionInfo info = null;
		
		try
		{
			info=(SecuritiesContractPlanVersionInfo)dao.findByID(lID,SecuritiesContractPlanVersionInfo.class );
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}
		
		return info;
	}
	
	/**
	 * 审核执行计划修改
	 * @param atInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void checkPlan(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		planFacade.checkPlan( atInfo );
	}
	 
	public long createPlanVersion(long lID, long lContractID, long lUserID, long lOfficeID) throws Exception
	{
		SecuritiesContractPlanDao dao = new SecuritiesContractPlanDao();
		long newVer=-1;
		
		try
		{
			newVer=dao.createPlanVersion(lID,lContractID,lUserID,lOfficeID);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return newVer;	
	
	}
	
	public SecuritiesContractPlanVersionInfo findLastPlanVersionByContract(long lID) throws Exception
	{
		SecuritiesContractPlanDao dao = new SecuritiesContractPlanDao();
		SecuritiesContractPlanVersionInfo info = null;
		
		try
		{
			info=dao.findLastPlanVersionByContract( lID );
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return info;	
	}
	public PlanModifyInfo findPlanModifyInfoByContract(long lID) throws Exception
	{
		SecuritiesPlanModifyDao dao = new SecuritiesPlanModifyDao();
		PlanModifyInfo info = null;
		
		try
		{
			info=dao.findPlanModifyInfoByContract( lID );
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return info;	
	}

	public long createTempVersion(long planID,long contractID,long userID,long officeID) throws Exception
	{
		SecuritiesContractPlanDao dao = new SecuritiesContractPlanDao();
		long newVer=-1;
		
		try
		{
			newVer=dao.createTempVersion(planID,contractID,userID,officeID);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return newVer;	
	}
	
}
