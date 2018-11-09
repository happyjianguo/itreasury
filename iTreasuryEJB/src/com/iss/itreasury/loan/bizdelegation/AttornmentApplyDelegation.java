/*
 * Created on 2004-5-25
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.CreateException;

import com.iss.itreasury.loan.attornmentapply.bizlogic.AttornmentApply;
import com.iss.itreasury.loan.attornmentapply.bizlogic.AttornmentApplyHome;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentQueryInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractQueryInfo;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.apply.dataentity.ApplyQueryInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttornmentApplyDelegation
{

	private AttornmentApply attornmentFacade = null;
	
	public AttornmentApplyDelegation() throws RemoteException
	{
		try
		{
			AttornmentApplyHome home;
			try
			{
				home =
					(AttornmentApplyHome) EJBHomeFactory
						.getFactory()
						.lookUpHome(
						AttornmentApplyHome.class);
			} catch (IException e)
			{
				throw new RemoteException("EJBHomeFactory连接错误", e);
			}
			attornmentFacade = (AttornmentApply) home.create();
		} catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException", ce);
		}

	}

	/**
	 * 保存贷款转让申请，包括新增和修改
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long save(AttornmentApplyInfo info) throws java.rmi.RemoteException,IException
	{
		return attornmentFacade.save(info);
	}

	/**
	 *审批操作
	*/
	public long doApproval(AttornmentApplyInfo info) throws java.rmi.RemoteException, IException{
		return attornmentFacade.doApproval(info);
	}
	/**
	 *申请书的取消审批操作
	*/
	public long cancelApproval(AttornmentApplyInfo info) throws java.rmi.RemoteException, IException{
		return attornmentFacade.cancelApproval(info);
	}
	
	
	/**
	 * 保存贷款转让得申请合同
	 * @param aInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long saveAttornmentContract(AttornmentContractInfo[] aInfo) throws java.rmi.RemoteException,IException
	{
		return attornmentFacade.saveAttornmentContract(aInfo);
	}
	
	/**
	 * 取消贷款转让申请
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException,IException
	{
		return attornmentFacade.cancel(lID);
	}
	
	/**
	 * 查找贷款转让申请信息
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public AttornmentApplyInfo findById(long lID) throws java.rmi.RemoteException,IException
	{
		return attornmentFacade.findById(lID);
	}
	
	/**
	 * 修改查找贷款转让申请信息
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findByMultioption(AttornmentQueryInfo qInfo) throws java.rmi.RemoteException,IException
	{
		return attornmentFacade.findByMultioption(qInfo);
	}
	
	/**
	 * 根据申请ID查找转让的合同列表
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findAttornmentContractByApplyId(long lID) throws java.rmi.RemoteException,IException
	{
		return attornmentFacade.findAttornmentContractByApplyId(lID);
	}
	
	/**
	 * 审核贷款转让申请
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException,IException
	{
		attornmentFacade.check(atInfo);
	}	
	
	public Collection findRepurchaseApply(ApplyQueryInfo qInfo) throws java.rmi.RemoteException,IException
	{
		Collection c = null;
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		try {
			c = dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			throw new IException("",e);
		}
		return c;
			
	}
	public ApplyInfo findRepurchaseApplyById( long lID ) throws java.rmi.RemoteException,IException
	{
		ApplyInfo c=null;
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		try {
			c = (ApplyInfo)dao.findByID( lID,ApplyInfo.class );
		} catch (Exception e) {
			throw new IException("",e);
		}
		return c;		
	}
	
	public Collection findContractForAttornment(ContractQueryInfo qInfo) throws java.rmi.RemoteException,IException
	{
		Collection c=null;
		ContractDao dao = new ContractDao();
		try {
			c = dao.findContractForAttornment(qInfo);
		} catch (Exception e) {
			throw new IException("",e);
		}
		return c;		
	}

	public Collection findContractByIDS(long[] lID) throws java.rmi.RemoteException,IException
	{
		Vector c=new Vector();
		ContractDao dao = new ContractDao();
		
		try {
			for ( int i=0;i<lID.length;i++ )
			{
				if (lID[i]>0)
				{
					//ContractInfo aInfo=dao.findByID( lID[i] );
					ContractInfo aInfo=dao.findGuoDianByID(-1, lID[i]);
					if (aInfo!=null) c.add(aInfo);
				}
			}
		} catch (Exception e) {
			throw new IException("",e);
		}
		return c;		
	}
	
	/*************	信贷管理之资产转让开始	********************/
        /**
         * 保存信贷管理之资产转让申请
         * 资产转让一般是证券部业务，在上海浦发项目中，没有证券模块，所以由信贷部发起
         * 以下程序是信贷部处理资产转让的程序
         *
         * @param info AttornmentApplyInfo
         * @return long
         * @throws RemoteException
         * @throws IException
         */
        public long saveAttormentForLoan(AttornmentApplyInfo info) throws java.rmi.RemoteException,IException
        {
        	return attornmentFacade.saveAttormentForLoan(info);
        }

        /**
         * 取消信贷管理之资产回购
         *
         * @param oid long
         * @return long
         * @throws RemoteException
         * @throws IException
         */
        public long cancelAttormentForLoan(long oid) throws java.rmi.RemoteException,IException
        {
        	return attornmentFacade.cancelAttormentForLoan(oid);
	}

        /**
         * 查找保存信贷管理之资产回购申请信息
         *
         * @param oid long
         * @return AttornmentApplyInfo
         * @throws RemoteException
         * @throws IException
         */
        public AttornmentApplyInfo findAttormentForLoanById(long oid) throws java.rmi.RemoteException,IException
        {
        	return attornmentFacade.findAttormentForLoanById(oid);
        }

        /**
         * 修改查找保存信贷管理之资产回购申请合同申请信息
         *
         * @param qinfo AttornmentQueryInfo
         * @return Collection
         * @throws RemoteException
         * @throws IException
         */
        public Collection findAttormentForLoanByMultioption(AttornmentQueryInfo qinfo) throws java.rmi.RemoteException,IException
        {
        	return attornmentFacade.findAttormentForLoanByMultioption(qinfo);
        }

        /**
         * 复核保存信贷管理之资产回购申请
         *
         * @param appinfo ApprovalTracingInfo
         * @throws RemoteException
         * @throws IException
         */
        public void checkAttormentForLoan(AttornmentApplyInfo appinfo) throws java.rmi.RemoteException,IException
        {
        	attornmentFacade.checkAttormentForLoan(appinfo);
        }
        
        /**
         * 取消复核保存信贷管理之资产回购申请
         *
         * @param appinfo ApprovalTracingInfo
         * @throws RemoteException
         * @throws IException
         */
        public void cancelCheckAttormentForLoan(AttornmentApplyInfo appinfo) throws java.rmi.RemoteException,IException
        {
        	attornmentFacade.cancelCheckAttormentForLoan(appinfo);
        }

	/*************	信贷管理之资产回购结束	********************/
        public void setLeftoversAttornment(long lContractID, double leftoversAttornment)throws java.rmi.RemoteException,IException{
        	attornmentFacade.setLeftoversAttornment(lContractID, leftoversAttornment);
        }
	
}
