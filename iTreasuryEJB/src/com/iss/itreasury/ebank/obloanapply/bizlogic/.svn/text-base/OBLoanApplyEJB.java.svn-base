/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.bizlogic;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.util.*;

import com.iss.itreasury.ebank.obloanapply.dataentity.*;
import com.iss.itreasury.ebank.obloanapply.dao.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.util.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanApplyEJB implements SessionBean
{
	/**
	 * @see javax.ejb.SessionBean#setContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	/**
	 * No argument constructor required by container.
	 */
	public OBLoanApplyEJB()
	{
	}
	/**
	 * Create method specified in EJB 1.1 section 6.10.3
	 */
	public void ejbCreate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove()
	{
	}

	/**
	 * 新增一笔贷款申请信息
	 * @param o LoanCreateInfo 贷款新建信息
	 * @return 1:成功 -1 失败
	 * @throws RemoteException
	 * @throws IException
	 */
	public long add(OBLoanCreateInfo o) throws RemoteException, IException
	{
		OBLoanApplyDao dao = new OBLoanApplyDao();
		OBLoanPlanDao pDao = new OBLoanPlanDao();
		OBLoanPlanInfo plInfo = new OBLoanPlanInfo();
		OBSecurityInfo sInfo = o.getSecurityInfo();
		long ret = -1;
		try
		{
			/*新增贷款申请信息*/
			ret = dao.saveLoanCreate(o);

			if (o.getLoanID() <= 0)
			{
				/*新增贷款计划版本信息*/
				plInfo.setID(-1);
				plInfo.setLoanID(ret);
				plInfo.setInputUserID(sInfo.getUserID());
				plInfo.setIsUsed(1);
				plInfo.setUserType(1);
				pDao.insert(plInfo);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return ret;

	}

	/**
	 * 更新贷款基本资料
	 * @param o LoanBasicInfo 贷款基本信息
	 * @return 1:成功 -1 失败
	 * @throws RemoteException
	 * @throws IException
	 */
	public long updateBasic(OBLoanBasicInfo o) throws RemoteException, IException
	{
		OBLoanApplyDao dao = new OBLoanApplyDao();
		long loanID = o.getLoanID();

		long ret = -1;
		try
		{
			/*监测安全信息*/
			//dao.checkLoanSecurity(o.getLoanID(),o.getSecurityInfo());

			/*保存信息*/
			ret = dao.saveLoanBasic(o);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return ret;
	}

	/**
	 * 保存贷款属性信息
	 * @param o LoanPropertyInfo 贷款属性信息
	 * @return 1:成功 -1 失败
	 * @throws RemoteException
	 * @throws IException
	 */
	public long updateProperty(OBLoanPropertyInfo o) throws RemoteException, IException
	{
		OBLoanApplyDao dao = new OBLoanApplyDao();
		long loanID = o.getLoanID();
		long ret = -1;

		try
		{
			/*监测安全信息*/
			//dao.checkLoanSecurity(o.getLoanID(),o.getSecurityInfo());
			/*保存贷款基本属性信息*/
			ret = dao.saveLoanProperty(o);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return ret;
	}

	/**
	 * 更新保证信息
	 * @param aPlanID
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long saveAssure(OBAssureInfo o) throws RemoteException, IException
	{
		OBLoanAssureDao fDao = new OBLoanAssureDao();
		long ret = -1;
		long loanID = o.getLoanID();
		OBLoanApplyDao dao = new OBLoanApplyDao();

		try
		{
			/*监测安全信息*/
			//dao.checkLoanSecurity(o.getLoanID(),o.getSecurityInfo());
			long assureID = o.getID();
			if (assureID <= 0)
			{
				/*保存保证信息*/
				ret = fDao.insert(o);
			}
			else
			{
				ret = fDao.update(o);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}

		return ret;
	}

	/**
	 * 删除指定的保证信息
	 * @param aPlanID
	 * @param sInfo 安全验证信息
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long deleteAssure(long aID[], OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanAssureDao fDao = new OBLoanAssureDao();
		OBAssureInfo assInfo = null;
		OBLoanApplyDao dao = new OBLoanApplyDao();
		long loanID = 1;
		long ret = -1;
		try
		{

			for (int i = 0; i < aID.length; i++)
			{
				if (aID[i] <= 0)
					continue;
				assInfo = fDao.findByID(aID[i]);
				loanID = assInfo.getLoanID();
				fDao.delete(aID[i]);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return 1;
	}

	/**
	 * 根据情况（修改或保存）放还款计划
	 * @param aPlanID
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long savePlan(Collection cList) throws RemoteException, IException
	{
		OBLoanPlanDetailDao pdDao = new OBLoanPlanDetailDao();
		OBLoanPlanDao pDao = new OBLoanPlanDao();
		OBLoanPlanDetailInfo pdInfo = null;
		long planID = -1;
		long loanID = -1;
		long ret = -1;

		Vector v = (Vector) cList;
		try
		{
			/*增加贷款详细信息*/
			int vSize = v.size();
			for (int i = 0; i < vSize; i++)
			{
				pdInfo = (OBLoanPlanDetailInfo) v.get(i);
				planID = pdInfo.getPlanID();
				if (pdInfo.getID() <= 0)
					pdDao.insert(pdInfo);
				else
					pdDao.update(pdInfo);
			}
			return 1;
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * 删除贷款计划的详细信息
	 * @param aPlanID
	 * @param sInfo 安全信息
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long deletePlan(long aPlanID[], OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanPlanDetailDao pdDao = new OBLoanPlanDetailDao();
		OBLoanPlanDao pDao = new OBLoanPlanDao();
		OBLoanApplyDao dao = new OBLoanApplyDao();
		OBLoanPlanDetailInfo detailInfo = null;

		long planVersion = -1;
		long loanID = -1;
		long ret = -1;

		try
		{
			/*顺序删除loan plan detail 表中的纪录*/
			for (int i = 0; i < aPlanID.length; i++)
			{
				if (aPlanID[i] <= 0)
					continue;
				detailInfo = pdDao.findByID(aPlanID[i]);
				planVersion = detailInfo.getPlanID();
				pdDao.delete(aPlanID[i]);
			}
			return 1;
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}

	}

	/**
	 * 根据条件，自动安排放还款计划
	 * 根据条件，调用LoanRepayPlanDetailDao中的insert()，逐条插入
	 * @param aPlanID
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long autoAddPlan(OBAutoPlanInfo apInfo) throws RemoteException, IException
	{
		OBLoanPlanDao pDao = new OBLoanPlanDao();
		long ret = -1;
		long lLoanID = apInfo.getLLoanID();
		try
		{

			/* 删除该申请的所有计划明细 */
			pDao.delete(lLoanID);

			/* 自动生成放还款信息*/
			ret = pDao.autoSavePlanDetail(apInfo);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return ret;
	}

	/**
	 * 取消一笔贷款申请
	 * @param lLoanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long cancel(long lLoanID, OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanApplyDao dao = new OBLoanApplyDao();
		long ret = -1;
		try
		{
			dao.updateLoanStatus(lLoanID, OBConstant.LoanInstrStatus.CANCEL);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return ret;
	}

	/**
	 * 提交贷款申请
	 * @param lLoanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public long commit(long lLoanID, OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanApplyDao dao = new OBLoanApplyDao();
		long ret = -1;

		try
		{
			dao.updateLoanStatus(lLoanID, OBConstant.LoanInstrStatus.SUBMIT);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return ret;
	}

	/**
	 * 根据贷款的ID来获得贷款申请的信息
	 * @param lLoanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public OBLoanApplyInfo findByID(long lLoanID, OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanApplyDao dao = new OBLoanApplyDao();
		OBLoanApplyInfo applyInfo = null;
		try
		{
			applyInfo = dao.findByID(lLoanID);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
		return applyInfo;
	}

	/**
	 * 获得一条贷款保证信息
	 * @param aPlanID
	 * @param sInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public OBAssureInfo findAssureByID(long aID, OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanAssureDao fDao = new OBLoanAssureDao();
		OBAssureInfo aInfo = null;

		try
		{
			aInfo = fDao.findByID(aID);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}

		return aInfo;
	}

	/**
	* 查询某条贷款计划的详细信息
	* @param aPlanID
	* @param sInfo
	* @return
	* @throws RemoteException
	* @throws IException
	*/
	public OBLoanPlanDetailInfo findPlanByID(long aPlanID, OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanPlanDetailDao pdDao = new OBLoanPlanDetailDao();
		OBLoanPlanDetailInfo pdInfo = null;

		try
		{
			pdInfo = pdDao.findByID(aPlanID);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}

		return pdInfo;
	}

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
	public Collection findPlanByLoanID(long lLoanID, OBPageInfo pInfo, OBSecurityInfo sInfo) throws RemoteException, IException
	{
		OBLoanPlanDao pDao = new OBLoanPlanDao();
		Collection c = null;

		try
		{
			c = pDao.findByLoanID(lLoanID, pInfo);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}

		return c;
	}
	public static void main(String[] args)
	{
	}
}
