/*
 * Created on 2004-5-21
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attornmentapply.bizlogic;


import java.util.Collection;

import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentQueryInfo;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.IException;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface AttornmentApply extends javax.ejb.EJBObject
{
	/**
	 * 保存贷款转让申请，包括新增和修改
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long save(AttornmentApplyInfo aInfo) throws java.rmi.RemoteException,IException;
	
	/**
	 * 保存贷款转让得申请合同
	 * @param aInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long saveAttornmentContract(AttornmentContractInfo[] aInfo) throws java.rmi.RemoteException,IException;
	
	/**
	 * 取消贷款转让申请
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException,IException;
	/**
	 *审批操作
	*/
	public long doApproval(AttornmentApplyInfo info) throws java.rmi.RemoteException, IException;
	/**
	 *申请书的取消审批操作
	*/
	public long cancelApproval(AttornmentApplyInfo info) throws java.rmi.RemoteException, IException;	
	/**
	 * 更新合同未转让债权余额
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void setLeftoversAttornment(long lContractID, double leftoversAttornment) throws java.rmi.RemoteException,IException;
	/**
	 * 查找贷款转让申请信息
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public AttornmentApplyInfo findById(long lID) throws java.rmi.RemoteException,IException;
	
	/**
	 * 修改查找贷款转让申请信息
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findByMultioption(AttornmentQueryInfo qInfo) throws java.rmi.RemoteException,IException;
	
	/**
	 * 根据申请ID查找转让的合同列表
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findAttornmentContractByApplyId(long lID) throws java.rmi.RemoteException,IException;
	
	/**
	 * 审核贷款转让申请
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException,IException;

	/*************	信贷管理之资产转让开始	********************/
	/**
	 * 保存信贷管理之资产转让申请合同
	 * @param aInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long saveAttormentForLoan(AttornmentApplyInfo info) throws java.rmi.RemoteException,IException;
	
	/**
	 * 取消信贷管理之资产转让
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long cancelAttormentForLoan(long oid) throws java.rmi.RemoteException,IException;
	
	/**
	 * 查找保存信贷管理之资产转让申请信息
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public AttornmentApplyInfo findAttormentForLoanById(long oid) throws java.rmi.RemoteException,IException;
	
	/**
	 * 修改查找保存信贷管理之资产转让申请合同申请信息
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findAttormentForLoanByMultioption(AttornmentQueryInfo qinfo) throws java.rmi.RemoteException,IException;
		
	/**
	 * 复核保存信贷管理之资产转让申请
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void checkAttormentForLoan(AttornmentApplyInfo appinfo) throws java.rmi.RemoteException,IException;
	
	/**
	 * 取消复核保存信贷管理之资产转让申请
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void cancelCheckAttormentForLoan(AttornmentApplyInfo appinfo) throws java.rmi.RemoteException,IException;
	
	/*************	信贷管理之资产转让结束	********************/
}
