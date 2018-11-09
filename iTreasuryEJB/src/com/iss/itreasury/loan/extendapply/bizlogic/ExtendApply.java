package com.iss.itreasury.loan.extendapply.bizlogic;


import java.rmi.RemoteException;
import java.security.Identity;
import javax.ejb.*;
import java.util.*;
import java.sql.*;
import javax.ejb.EJBObject;
//
import com.iss.itreasury.loan.extendapply.dataentity.*;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * Created 2003-8-15 14:50:59
 * Code generated by the Forte for Java EJB Module
 * @author yfan
 */

public interface ExtendApply extends EJBObject {

  
  /**
	 * 新增/修改展期申请
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>新增/修改展期申请</b>
	 * <br>将对应的计划版本的nIsUsed置为“是”，使该计划版本暂不能被其他途径修改,只能被展期中的“修改执行计划”修改
	 * <ul>
	 * <li>操作数据库表Loan_ExtendForm和Loan_ExtendDetail
   * <li>延期申请标示等于0，新增，大于0，修改
   * <li>新增展期申请时，随实际展期情况，生成一个版本号为空的还款计划
   * <li>修改展期申请时，要随之修改新增时生成的还款计划
   * <li>Collection结构: 
   *         long        lExtendListID         展期明细标示
   *         long        lPlanID               计划明细标示
   *         double      dLoanBalance          贷款余额
   *         double      dExtendAmount         展期金额
   *         Timestamp   dtExtendBeginDate     展期起始日期
   *         Timestamp   dtExtendEndDate       展期截止日期
   *         int         nExtendIntervalNum    展期月份
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
   *
   * @param     long        lID                     延期申请标示
   * @param     long        lContractID             贷款合同标示
   * @param     long        lPlanVersionID          计划版本标示
   * @param     Collection  cExtendList             展期明细集合
   * @param     long        lExtendTypeID           展期类型（展期、转期）
   * @param     long        lInterestTyepID         利率类型（普通 or Libor）
   * @param     double      dExtendRate             展期利率
   * @param     long        lLiborRateID            Libor利率类型
   * @param     double      dAdjustValue            调整值
   * @param     double      dAdjustRate             浮动比例
   * @param     double      dStaidAdjustRate        固定浮动利率
   * @param     String      strExtendReason         展期原因
   * @param     String      strReturnPostPend       归还延期还款本息资金
   * @param     String      strOtherContent         其他事项
   * @param     long        lUserID              录入人标示
   *
	 * @return    long     操作成功，返回值 == 1，失败，返回值 == 0。
   *
	 * @exception Exception
   **/
   public long saveExtendApply(long lID,
            long  lContractID,
            long lPlanVersionID,
            Collection cExtendList,
            long lExtendTypeID,
            long lInterestTypeID,
            long lBankRateTypeID,
            double dExtendRate,
            long lLiborRateID,
            double dAdjustValue,
            double dAdjustRate,
            double dStaidAdjustRate,
            String strExtendReason,
            String strReturnPostPend,
            String strOtherContent,
            long lUserID,
            long lCurrencyID,
    		long lOfficeID,
    		InutParameterInfo pInfo) throws RemoteException, IRollbackException;



  /**
	 * 合同查找，根据条件查询ContractInfo和LoanInfo表。
	 * <br>lContractIDFrom和lContractIDTo，同一类型的合同的流水号的部分作为查询范围
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lCurrencyID 币种标识
	 * @param lOfficeID 办事处标识
	 * @param lTypeID 贷款申请类型标识
	 * @param lContractIDFrom 合同编号起始
	 * @param lContractIDTo 合同编号结束
	 * @param lClientID 借款单位标识
	 * @return Collection ContractInfo
	 * @exception Exception
	 */
	public Collection findContractByMultiOption(
								long lUserID,
								long lCurrencyID,
								long lOfficeID,
								long lTypeID,
								long lContractIDFrom,
								long lContractIDTo,
								long lClientID,
				                long lPageLineCount,
				                long lPageNo,
				                long lOrderParam,
				                long lDesc
								) throws RemoteException;


  /**
	 * 查找合同的最新版本还款计划。不翻页
	 * 最新的计划版本，
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID 合同标识
	 * @return Collection 
	 * @exception RemoteException
	 */
  public Collection findPlanByContract(long lID) throws RemoteException;


	/**
	 * 查找延期申请
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>根据申请标示查找延期申请</b>
	 * <ul>
	 * <li>操作数据库表ReviewOpinion
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
   *
   * @param     long        lID              延期申请标示
   *
   *
	 * @return    ExtendApplyInfo     
   *
	 * @exception Exception
   **/
  public ExtendApplyInfo findExtendByID(long lID) throws RemoteException;


  /**
	 * 组合查找贷款展期信息
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>根据组合条件查找贷款展期信息</b>
	 * <ul>
	 * <li>操作数据库表Loan_ExtendForm及相关表
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
   *
	 * @param     int      nType                  贷款类型
   * @param     long     lCurrencyID            币种
	 * @param     long     lStartContractID       起始合同号         
	 * @param     long     lEndContractID         截至合同号        
	 * @param     long     lClientID              贷款单位         
	 * @param     double   dStartAmount           起始展期金额         
	 * @param     double   dEndAmount             且值展期金额  
	 * @param     Timestamp  tsStartDate          起始展期日         
	 * @param     Timestamp  tsEndDate            截至展期日    
	 * @param     int      nStatusID              状态         
   *
   * @param     long     lPageLineCount          每页页行数条件
   * @param     long     lPageNo                 第几页条件
   * @param     long     lOrderParam             排序条件，根据此参数决定结果集排序条件
   * @param     long     lDesc                   升序或降序
   *
   * @param     long     lOfficeID          办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
   *
	 * @return    Collection    
   *
	 * @exception Exception
   **/
  public Collection findExtendByMultiOption(long lAction,
  										   long lUserID,
                                           long lCurrencyID,                                           
                                           long lOfficeID,
                                           long lLoanTypeID,
                                           long lStartContractID,
                                           long lEndContractID,
                                           long lClientID,
                                           double dStartAmount,
                                           double dEndAmount,
                                           Timestamp tsStartDate,
                                           Timestamp tsEndDate,
                                           int nStatusID,
                                           long lPageLineCount,
                                           long lPageNo, 
                                           long lOrderParam,
                                           long lDesc) throws RemoteException;
                                           
    /**
	 * 组合查找贷款展期合同信息
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>根据组合条件查找贷款展期合同信息</b>
	 * <ul>
	 * <li>操作数据库表Loan_ExtendForm及相关表
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
   *
	 * @return    Collection    
   *
	 * @exception Exception
   **/
  public Collection findExtendContractByMultiOption(                                           
                                           long lTypeID,
                                           long lCurrencyID,
                                           long lOfficeID,
                                           long lActionID,
                                           long lUserID,
                                           long lContractIDFrom,
                                           long lContractIDTo,
                                           long lClientID,
                                           long lStatusID,
										   long lSignID, 
                                           long lPageLineCount,
                                           long lPageNo,
                                           long lOrderParam,
                                           long lDesc) throws RemoteException;
                                          
           
  /**
	* 取消展期申请。
	* @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	* @param lExtendApplyID 展期申请标识
	* @return 1-成功，
    * 0-操作失败
    * －1 状态不对
	* @exception RemoteException
	*/
  public long cancelExtendApply(long lExtendApplyID) throws RemoteException, IRollbackException;
  
  /**
	* 取消展期合同。
	* @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	* @param lID 展期合同标示标识
  	* @param lExtendContractID 展期合同标识
  	* @return 1-成功，
    * 0-操作失败
    * －1 状态不对
    */
  public long cancelExtendContract(long lExtendContractID) throws RemoteException, IRollbackException;

   /**
	  * 审核展期申请，操作Loan_ExtendForm表
	  * <br>最后一级审核后，将对应的计划版本的nIsUsed置为“否”
	  * @param     long        nReviewContentID      审批内容类型
	  * @param     String      sOpinion              审批意见
	  * @param     long        nUserID               审批人标示
	  * @param     long        nNextUserID           下一个审批人标示
	  * @param     long        lAction               审批，拒绝，修改，最后审批
	  * @return    long        成功，返回值 == 1，失败，返回值 == -1
	  */
  public long checkExtendApply(
	  	long lApprovalContentID,
		String sOpinion,
		long lUserID,
		long lNextUserID,
		long lAction,
		long lCurrencyID,
		long lOfficeID )
	  throws RemoteException, IRollbackException;
	  
	  
   /**
	  * 审核展期合同，操作Loan_ExtendForm表
	  * <br>最后一级审核后，将对应的计划版本的nIsUsed置为“否”
	  * @param     long        nReviewContentID      审批内容类型
	  * @param     String      sOpinion              审批意见
	  * @param     long        nUserID               审批人标示
	  * @param     long        nNextUserID           下一个审批人标示
	  * @param     long        lAction               审批，拒绝，修改，最后审批
	  * @return    long        成功，返回值 == 1，失败，返回值 == -1
	  */
  public long checkExtendContract(
	  	long lApprovalContentID,
		String sOpinion,
		long lUserID,
		long lNextUserID,
		long lAction,
		long lCurrencyID,
		long lOfficeID)
	  throws RemoteException, IRollbackException;
  
  /**
	  * 审核展期合同，增加审批动作为不影响其他动用上边方法，操作Loan_ExtendForm表
	  * <br>最后一级审核后，将对应的计划版本的nIsUsed置为“否”
	  * @param     long        nReviewContentID      审批内容类型
	  * @param     String      sOpinion              审批意见
	  * @param     long        nUserID               审批人标示
	  * @param     long        nNextUserID           下一个审批人标示
	  * @param     InutParameterInfo        pInfo     审批INFO
	  * @param     long        lAction               审批，拒绝，修改，最后审批
	  * @return    long        成功，返回值 == 1，失败，返回值 == -1
	  */
public long checkExtendContract(
	  	long lApprovalContentID,
		String sOpinion,
		long lUserID,
		long lNextUserID,
		long lAction,
		long lCurrencyID,
		long lOfficeID,
		InutParameterInfo pInfo, String sExCode)
	  throws RemoteException, IRollbackException;
  /**
   * 更新展期申请是否走最小审核级别字段
   * @param extendID
   * @param isLowLevel
   * @return
   * @throws RemoteException
   * @throws IException
   */    
	public long updateExtendCheckLevel(long extendID, long isLowLevel) throws RemoteException,IException;
	/**
	 * 展期申请审批方法。add by zcwang 2007-5-20
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(InutParameterInfo pInfo,long lExtendApplyID, String sOpinion, long lUserID, long lNextUserID, long lAction,long lCurrencyID,long lOfficeID)throws RemoteException, IRollbackException;
	
	/**
	 * 展期合同审批方法。add by zcwang 2007-5-20
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(long lApprovalContentID,String sOpinion,long lUserID,long lNextUserID,long lAction,long lCurrencyID,long lOfficeID,InutParameterInfo pInfo)throws RemoteException, IRollbackException;
	
	/**
	 * Modify by leiyang date 2007/07/10
	 * 审批流：校验合同状态
	 * @param loanInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long checkContract(long lExtendApplyID)throws RemoteException, IRollbackException;
	
	/**
	 * Modify by leiyang date 2007/07/12
	 * 审批流：取消审批方法（展期申请处理）
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(InutParameterInfo inutParameterInfo,long lExtendApplyID)throws RemoteException, IRollbackException;
	
	/**
	 * Modify by leiyang date 2007/07/12
	 * 审批流：取消审批方法（展期合同处理）
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApprovalContract(InutParameterInfo inutParameterInfo,long lExtendContractID)throws RemoteException, IRollbackException;
	

}
