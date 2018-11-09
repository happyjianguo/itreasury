package com.iss.itreasury.ebank.obextendapply.bizlogic;

import java.rmi.*;
import javax.ejb.*;

import java.util.*;
import com.iss.itreasury.ebank.obrepayplan.dataentity.*;
import com.iss.itreasury.ebank.obextendapply.dataentity.*;
import com.iss.itreasury.ebank.obrepayplan.dao.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obextendapply.dao.*;

public class OBExtendApplyEJB implements SessionBean {
	SessionContext sessionContext;
	public void ejbCreate() {
  	}
  	public void ejbRemove() {
  	}
  	public void ejbActivate() {
  	}
  	public void ejbPassivate() {
  	}
  	public void setSessionContext(SessionContext sessionContext) {
    	this.sessionContext = sessionContext;
  	}

	/**
	 * 展期申请合同查找
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param OBQueryContractInfo  查询条件
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findContractByMultiOption(OBQueryContractInfo o) throws RemoteException,IException
	{
		Collection cResult = null;
		OBExtendApplyDao ExtendApplyDAO = new OBExtendApplyDao();
		try
		{
			cResult = ExtendApplyDAO.findContractByMultiOption(o);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw new IException("");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException( "Gen_E001", e.getMessage());
		}
		finally
		{
		}
		return cResult;

	}

	/**
	 * 查找合同的最新版本还款计划。不翻页
	 * 最新的计划版本，
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID 合同标识
	 * @return Collection  (if 合同计划正在修改返回null)
	 * @exception RemoteException
	 */
	public Collection findPlanByContract( long lID ,OBSecurityInfo sInfo) throws RemoteException,IException
 	{
		 Collection cResult = null;
		 OBExtendApplyDao ExtendApplyDAO = new OBExtendApplyDao();
		 try
		 {
			 cResult = ExtendApplyDAO.findPlanByContract(lID,sInfo);
		 }
		 catch (RemoteException e)
		 {
			 throw e;
		 }
		 catch (IRollbackException e)
		 {
			 throw new IException("");
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
			 throw new IException( "Gen_E001", e.getMessage());
		 }
		 finally
		 {
		 }
		 return cResult;

 	}

	/**
	 * 查找延期申请
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param     long        lID              延期申请标示
	 * @param     OBSecurityInfo        sInfo              安全信息
	 * @return    ExtendApplyInfo
	 * @exception Exception
	 **/
	public OBExtendApplyInfo findExtendByID( long lID,long lContractID,OBSecurityInfo sInfo ) throws RemoteException,IException
	{
		OBExtendApplyInfo cResult = new OBExtendApplyInfo();
		OBExtendApplyDao ExtendApplyDAO = new OBExtendApplyDao();
		try
		{
			cResult = ExtendApplyDAO.findExtendByID(lID,lContractID,sInfo);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw new IException("");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001", e.getMessage());
		}
		finally
		{
		}
		return cResult;

	}

	/**
	 * 新增/修改展期申请
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBExtendApplyInfo       appInfo   展期申请信息
	 * @return    long     操作成功，返回值 == 1，失败，返回值 == 0。
	 * @exception Exception
	 **/
	public long saveExtendApply( OBExtendApplyInfo appInfo)  throws RemoteException,IException
	{
		long lResult = -1;
		OBExtendApplyDao ExtendApplyDAO = new OBExtendApplyDao();
		try
		{
			lResult = ExtendApplyDAO.saveExtendApply(appInfo);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw new IException();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException( "Gen_E001", e.getMessage());
		}
		finally
		{
		}
		return lResult;

	}

	/**
	 * 取消展期申请。
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lExtendApplyID 展期申请标识
	 * @param OBSecurityInfo sInfo 安全信息
	 * @return 1-成功，
	 * 0-操作失败
	 * －1 状态不对
	 * @exception RemoteException
	 */
	public long updateStatus( long lExtendApplyID,long lStatusID,OBSecurityInfo sInfo ) throws RemoteException	,IException
	{
		long lResult = -1;
		OBExtendApplyDao ExtendApplyDAO = new OBExtendApplyDao();
		try
		{
			lResult = ExtendApplyDAO.updateStatus(lExtendApplyID,lStatusID,sInfo);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw new IException("");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001", e.getMessage());
		}
		finally
		{
		}
		return lResult;

	}

}