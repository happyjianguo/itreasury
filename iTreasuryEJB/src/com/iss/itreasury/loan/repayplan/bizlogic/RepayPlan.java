package com.iss.itreasury.loan.repayplan.bizlogic;

import java.rmi.RemoteException;
import java.security.Identity;
import javax.ejb.*;
import java.util.*;
import java.sql.*;
//
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustPayConditionInfo;
import com.iss.itreasury.loan.repayplan.dataentity.*;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public interface RepayPlan extends EJBObject
{

	/**
	 * 新增还款计划
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>新增或修改还款计划</b>
	 * <ul>
	 * <li>还款金额不能大于合理还款数
	 *
	 * <li>操作数据库表ContractPayPlan、ContractPayPlanVersion
	 * <li>新增：（lID == 0）
	 * <li>根据nAddANewVersion： 1--新增一条版本纪录  0--不需新增版本纪录
	 * <li>如果nAddANewVersion=1，ContractPayPlanVersion中新增一条纪录，版本号为空
	 * <li>                      复制ContractPayPlan中最近的版本纪录，nContractPayPlanVersionID
	 * <li>                      值为ContractPayPlanVersion中新增纪录的ID
	 * <li>                      ContractPayPlan中插入新增的纪录
	 * <li>如果nAddANewVersion=0，ContractPayPlan中插入新增的纪录，nContractPayPlanVersionID
	 * <li>                      值为该贷款纪录在ContractPayPlanVersion中的最大ID
	 * <li>修改:  (lID > 0)
	 * <li>两步操作：复制和修改
	 * <li>其他同新增
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lID                    还款计划标示
	 * @param     long        lLoanID                贷款标示
	 * @param     long        lContractID            合同标示
	 * @param     Timestamp   tsPlanDate             原始计划日期
	 * @param     int         nLoanOrRepay           贷/还
	 * @param     double      dAmount                金额
	 * @param     String      strType                类型（本金）
	 * @param     long        lInputUserID           用户标示
	 * @param     Timestamp   tsInputDate            新增时间/修改时间
	 * @param     long        lExtendApplyID         展期申请标示
	 * @param     long        lOverdueApplyID        逾期申请标示
	 * @param     long        lIsOverdue             是否逾期
	 * @param     long        lUserTypeID            修改来源（申请、展期、逾期、菜单）
	 * @param     int         nAddANewVersion        是否新增一个版本号
	 *
	 * @param     long       lOfficeID               办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    long     新增或修改成功，返回值 ContractPayPlanVersion中纪录的ID，失败，返回值 == 0。主要用于修改信息
	 *
	 * @exception Exception
	**/
	public long savePlan(PlanDetailInfo o) throws RemoteException, IRollbackException;

	/**
	 * 修改还款计划（复核后）
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>修改还款计划</b>
	 * <ul>
	 * <li>操作数据库表ContractPayPlanVersion
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lID                    还款计划标示
	 * @param     long        lCheckUserID           复核人标示
	 * @param     Timestamp   tsCheckDate            复核时间
	 *
	 * @param     long       lOfficeID               办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    long     修改成功，返回值 == 1，失败，返回值 == 0。主要用于修改信息
	 *
	 * @exception Exception
	**/
	public long savePlan(long lID, long lCheckUserID, Timestamp tsCheckDate, long lOfficeID) throws RemoteException;

	/**
	 * 自动新增还款计划
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>新增还款计划</b>
	 * <ul>
	 * <li>操作数据库表ContractPayPlan、ContractPayPlanVersion
	 * <li>往ContractPayPlanVersion中插入一条版本号为空的纪录
	 * <li>详细计划插入ContractPayPlan
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lLoanID                 贷款标示
	 * @param     int         nLoanType               放款方式类型
	 * @param     Timestamp   tsLoanStartDate         起始贷款日期
	 * @param     int         nRepayType              还款方式类型
	 * @param     Timestamp   tsRepayStartDate        起始还款日起
	 * @param     Timestamp   tsInputDate             新增时间/修改时间
	 * @param     String      strType                   类型（本金）
	 *
	 * @param     long        lUserID            用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long        lOfficeID          办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    long     新增或修改成功，返回值 ContractPayPlanVersion中纪录的ID，失败，返回值 == 0。主要用于修改信息
	 *
	 * @exception Exception
	**/
	public long autosavePlan(PlanAssignInfo o) throws RemoteException, IRollbackException;

	/**
	 * 确认新版还款计划
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>确认新版还款计划</b>
	 * <ul>
	 * <li>点击“完成”执行此方法
	 * <li>操作数据库表ContractPayPlanVersion
	 * <li>补充ContractPayPlanVersion中纪录的版本号
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long        lID                     版本纪录标示
	 *
	 * @param     long        lUserID            用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long        lOfficeID          办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    long        成功，返回值 == 1，失败，返回值 == 0。
	 *
	 * @exception Exception
	**/
	public long createPlanVersion(long lID, long lLoanID, long lUserID, long lOfficeID, long lCurrencyID) throws RemoteException, IRollbackException;

	/**
	 * 根据贷款合同号查找计划信息
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>查找贷款信息</b>
	 * <ul>
	 * <li>操作数据库表ContractPayPlanVersion，ContractPayPlan
	 * <li>得到版本号最高的信息
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     lContractID                 贷款合同标示
	 *
	 * @param     long     lPageLineCount         每页页行数条件
	 * @param     long     lPageNo                第几页条件
	 * @param     long     lOrderParam            排序条件，根据此参数决定结果集排序条件
	 * @param     long     lDesc                  升序或降序
	 *
	 * @return    Collection     
	 *
	 * @exception Exception
	**/
	public Collection findPlanByContract(long lContractID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException,IException;

	/**
	 * 根据ContractPayPlanVersion中的ID查找计划信息
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>查找贷款信息</b>
	 * <ul>
	 * <li>不关心版本号
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     ContractPayPlanVersionID    ContractPayPlanVersion中的ID
	 *
	 * @param     long     lPageLineCount         每页页行数条件
	 * @param     long     lPageNo                第几页条件
	 * @param     long     lOrderParam            排序条件，根据此参数决定结果集排序条件
	 * @param     long     lDesc                  升序或降序
	 *
	 * @param     long     lUserID               用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long     lOfficeID             办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    Collection     
	 *
	 * @exception Exception
	**/
	public Collection findPlanByVer(
		long ContractPayPlanVersionID,
		long lPageLineCount,
		long lPageNo,
		long lOrderParam,
		long lDesc,
		long lUserID,
		long lOfficeID)
		throws RemoteException;

	/**
	 * 根据计划标示查找还款计划
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>查找还款计划</b>
	 * <ul>
	 * <li>操作数据库表ContractPayPlan
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long       lID                还款计划标示       
	 *
	 * @param     long       lUserID            用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long       lOfficeID          办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    PayPlanInfo     
	 *
	 * @exception Exception
	**/
	public RepayPlanInfo findPlanByID(long lID, long lUserID, long lOfficeID) throws RemoteException;

	/**
	 * 删除还款计划
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>删除lID[]指定的还款计划</b>
	 * <ul>
	 * <li>操作数据库表ContractPayPlan
	 * <li>注意：是否要产生新的版本信息，如果要，要首先复制最近一次的还款计划，
	 * <li>产生新的还款纪录
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     lID[]              计划标示数组   
	 * @param     long     lLoanID            贷款标示
	 * @param     int      nAddANewVersion    是否新增一个版本号
	 *
	 * @param     long     lUserID            用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long     lOfficeID          办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    long     删除成功，返回值 等于 删除的记录数，失败，返回值 == -1。
	 *
	 * @exception Exception
	**/
	// 增加一个参数，用来区分是否需要保存操作日志。isFirst=true时不需要保存，=false时需要保存
//	public long deletePlan(long lID[], long lLoanID, long nAddANewVersion, long lUserID, long lOfficeID) throws RemoteException, IRollbackException;
	public long deletePlan(long lID[], long lLoanID, long nAddANewVersion, long lUserID, long lOfficeID, boolean isFirst) throws RemoteException, IRollbackException;

	/**
		 * 执行计划更改合同查找（合同的状态为“执行中”）
		 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	   * @param nIsHaveNew  是否有需要复核的计划版本
		 * @param lCurrencyID 币种标识
		 * @param lOfficeID 办事处标识
		 * @param lContractIDFrom 合同编号起始
		 * @param lContractIDTo 合同编号结束
		 * @param lClientID 借款单位标识
	 	 * @param lPeriod  期限
		 * @param dAmountFrom 金额起始
		 * @param dAmountTo 金额结束
	   * @param tsUpdateFrom  起始修改日期
	   * @param tsUpdateTo    截至修改日期
	   * @param lStatusID  状态
		 * @return Collection 
		 * @exception Exception
		 */
	public Collection findContractByMultiOption(QueryContractInfo o) throws RemoteException;

	/**
		 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
		 * <p>    
		 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	   *
	   * @param     long     lContractID    ContractPayPlanVersion中的lContractID
	   *
	   * @param     long     lPageLineCount         每页页行数条件
	   * @param     long     lPageNo                第几页条件
	   * @param     long     lOrderParam            排序条件，根据此参数决定结果集排序条件
	   * @param     long     lDesc                  升序或降序
	   *
	   * @param     long     lUserID               用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	   * @param     long     lOfficeID             办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	   *
		 * @return    Collection     
	   *
		 * @exception Exception
	 **/
	public Collection findPlanVerByContract(long lContractID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc, long lUserID, long lOfficeID)
		throws RemoteException;

	// 通过合同ID得到最大的PlanID
	public long findMaxVersionID(long lContractID) throws RemoteException;
	
	//add by zwxiao 通过合同ID得到最大的PlanVersion
	public long findMaxVersionCode(long lContractID) throws RemoteException;

	/**
	* 取消已提交的合同执行计划修改
	* <p>
	* @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	*
	* @param     long        lPlanModifyID    合同执行计划修改记录标示
	* @param     long        lUserID               修改人
	* @return    long        成功，返回值 == 1，失败，返回值 == -1
	* @exception long 
	* */
	public long cancelRepayPlan(long lPlanModifyID, long lUserID) throws RemoteException, IRollbackException;

	/**
		 * 审核核更改的合同执行计划
		 * <p>    
		 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	     *
		 * @param     long        nReviewContentID      审批内容类型ContractPayPlanVersion中的ID
		 * @param     String      sOpinion              审批意见
		 * @param     long        nUserID               审批人标示
		 * @param     long        nNextUserID           下一个审批人标示
		 * @param     long        lAction               审批，拒绝，修改，最后审批
		 * @return    long        成功，返回值 == 1，失败，返回值 == -1
		 * @exception long
	  **/
	public long checkRepayPlan(long lApprovalContentID, String sOpinion, long lUserID, long lNextUserID, long lAction,long lCurrencyID,long lOfficeID) throws RemoteException, IRollbackException;

	/**
	 * 判断合同执行计划是否能够被指定的来源修改
	 * <p>    
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     lID                   合同标示
	 * @param     long     lSourceID             来源标示
	 * @param     long     lUserID               用户标示
	 *
	 * @return    Collection     
	 *
	 * @exception long
	 **/
	public long findCanBeModify(long lContractID, long lSourceID, long lUserID) throws RemoteException;
	
	public long commitAndApprovalInit(PlanModifyInfo info) throws RemoteException, IRollbackException;
	
	public long doApproval(PlanModifyInfo cInfo) throws RemoteException,IRollbackException ;
	
	public long cancelApproval(PlanModifyInfo Info)throws RemoteException, IRollbackException;
	
	/**
     * @author yunchang
     * @date 2010-06-03
     * @function:
     * upRepayPlanRate  融资租赁利率调整
     * 操作 LOAN_RATEADJUSTPAYCONDITION 数据表
     * 更新表中利率
     * 返回值为-1标识失败，n>0标识更新的条数
     * @return long  成功返回ID标识，失败返回0
     * @throws RemoteException
     */
    public Collection adjustRzzlRate (AdjustPayConditionInfo adjustPayConditionInfo,Collection repayColl,ContractInfo contractInfo,long planID) throws java.rmi.RemoteException, IRollbackException;  
    
    /**
     * @author zwxiao
     * @date 2010-06-20
     * @function:
     * 更新融资租赁的利率和还款计划
     * 返回值为-1标识失败，n>0标识更新的条数
     * @return long  成功返回ID标识，失败返回0
     * @throws RemoteException
     */
    public long saveAdjustPlanAndRate (PlanModifyInfo pinfo,Collection repayColl,AdjustPayConditionInfo conditionInfo,ContractInfo contractInfo,long planID) throws java.rmi.RemoteException, IRollbackException; 
    
    /**
     * @author zwxiao
     * @date 2010-06-20
     * @function:
     * 删除融资租赁的利率和还款计划
     * 返回值为-1标识失败，n>0标识更新的条数
     * @return long  成功返回ID标识，失败返回0
     * @throws RemoteException
     */
    public long deleteAdjustPlanAndRate (AdjustPayConditionInfo conditionInfo,ContractInfo contractInfo,long planID) throws java.rmi.RemoteException, IRollbackException;

    /**
     * @author zwxiao
     * @date 2010-06-20
     * @function:
     * 通过合同ID得到最大的PlanID
     * 返回值为-1标识失败，n>0标识更新的条数
     * @return long  成功返回ID标识，失败返回0
     * @throws RemoteException
     */
	public long findNewMaxVersionID(long lContractID,long planID) throws RemoteException, IRollbackException;
	
	//add by zwxiao 2010-07-08 取得利率调整的最原始的版本ID
	public long findOldMaxVersionID(long lContractID) throws java.rmi.RemoteException, IRollbackException;
	
	//add by zwxiao 2010-07-08 取得利率调整的最原始的版本code
	public long findOldMaxVersionCode(long lContractID) throws java.rmi.RemoteException, IRollbackException;
}
