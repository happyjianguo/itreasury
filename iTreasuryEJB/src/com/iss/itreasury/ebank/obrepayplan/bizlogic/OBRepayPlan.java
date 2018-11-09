package com.iss.itreasury.ebank.obrepayplan.bizlogic;

import java.rmi.*;
import java.util.*;
import javax.ejb.*;
import javax.ejb.EJBObject;
import com.iss.itreasury.ebank.obrepayplan.dataentity.*;
import com.iss.itreasury.ebank.obrepayplan.dao.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obdataentity.*;

public interface OBRepayPlan extends EJBObject {

	/**
	 * 执行计划更改合同查找（合同的状态为“执行中”）
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param OBQueryContractInfo  查询条件
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findContractByMultiOption(OBQueryContractInfo o) throws RemoteException,IException;

	/**
	 * 判断合同执行计划是否能够被指定的来源修改
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lID                   合同标示
	 * @param     OBSecurityInfo     sInfo       安全信息
	 * @return    long
	 **/
	public long findCanBeModify(long lContractID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
     * @param     long     lContractID
     * @param     OBPageInfo     分页和排序的信息
     * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
     * @exception Exception
	 **/
	public Collection findPlanVerByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException,IException;

	/**
	 * 生成一个版本号为空的新版本，并复制版本明细
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBPlanDetailInfo     dInfo
	 * @param     OBSecurityInfo     sInfo
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection addTempVersion(OBPlanDetailInfo dInfo) throws RemoteException,IException;

	/**
	 * 根据执行计划版本的ID查找计划明细信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     versionID
	 * @param     OBPageInfo     pInfo 翻页信息
	 * @param     OBSecurityInfo sInfo 安全相关信息
	 * @return    Collection
	 * @exception Exception
	**/
	public Collection findPlanByVer(long versionID,OBPageInfo pInfo,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * 根据合同的ID查找合同的信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     contractID
	 * @param     OBSecurityInfo sInfo 安全相关信息
	 * @return    Collection
	 * @exception Exception
	**/
	public ContractInfo findContractByID(long contractID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * 自动新增还款计划
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBPlanAssignInfo     o
	 * @return    long     新增或修改成功，
	 * @exception Exception
	**/
	public long autoSavePlan(OBPlanAssignInfo o) throws RemoteException,IException;

	/**
	 * 根据计划标示查找还款计划
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param     long       lID                还款计划标示
	 * @return    PayPlanInfo
	 * @exception Exception
	**/
	public OBRepayPlanInfo findPlanDetailByID(long lID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	* 新增还款计划或者修改
	* @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	* @param     Collection       detailList
	* @param     OBSecurityInfo         sInfo
	* @return    long     新增或修改成功
	* @exception Exception
	**/
	public long savePlan(Collection detailList) throws RemoteException,IException;

	/**
	* 删除原始计划明细
	* @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	* @param     long[]       lID
	* @param     OBSecurityInfo         sInfo
	* @return    long     新增或修改成功
	* @exception Exception
	**/
	public long deletePlan(long[] lID,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * 确认新版还款计划
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long        lID                     版本纪录标示
	 * @param     OBSecurityInfo sInfo
	 * @return    long        成功，返回值 == 1，失败，返回值 == 0。
	 * @exception Exception
	**/
	public long commit(long lID, OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * 确认新版还款计划
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long        lID                     版本纪录标示
	 * @param     OBSecurityInfo sInfo
	 * @return    long        成功，返回值 == 1，失败，返回值 == 0。
	 * @exception Exception
	**/
	public long cancel(long lID, OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     分页和排序的信息
	 * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findPlanByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException ,IException;


	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     分页和排序的信息
	 * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findTempPlanVerByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException ,IException;

	/**
	 * 根据执行计划版本的ID查找计划明细信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     versionID
	 * @param     OBPageInfo     pInfo 翻页信息
	 * @param     OBSecurityInfo sInfo 安全相关信息
	 * @return    Collection
	 * @exception Exception
	**/
	public Collection findTempPlanByVer(long versionID,OBPageInfo pInfo,OBSecurityInfo sInfo) throws RemoteException,IException;

	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     分页和排序的信息
	 * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findTempPlanByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws RemoteException ,IException;

}