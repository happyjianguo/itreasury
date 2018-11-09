/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settadjustinterestrate.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestConditionInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestQueryInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettRateAdjustPayConditionInfo;

/**
 * @author jinchen
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface SettAdjustInterestRate extends javax.ejb.EJBObject
{
    /**
     * updateInterestRate 普通贷款银行利率修改 提交银行利率修改申请 操作 ContractRateSetting 数据表
     * 提交银行利率修改申请 将被修改的合同的查询条件写入表中 lID = 0 ,新增。不能修改纪录
     * 
     * @param lID
     *            long 利率修改条件标示
     * @param lLoanInterestRateID
     *            long 贷款利率标示
     * @param lContractID
     *            long 合同标示
     * @param lLetoutRequisitionID
     *            long 放款通知单标示
     * @param dRate
     *            double 利率值
     * @param tsValidate
     *            Timestamp 生效日
     * @param strReason
     *            String 调整原因
     * @param lCurrencyID
     *            long 币种
     * @param lOfficeID
     *            long 办事处ID
     * @param lInputUserID
     *            long 录入人ID
     * @param tsInputDate
     *            Timestamp 录入日期
     * @param 以上参数全部封装到类
     *            SettAdjustInterestSaveInfo
     * @return long 成功返回ID标识，失败返回0
     * @throws RemoteException
     * @throws SettlementException
     */
    public long saveAdjustInterestRate(SettRateAdjustPayConditionInfo info)
            throws SettlementException, RemoteException;
    

    /**
     * findUpdateInterestRateByID 查找利率修改条件信息 根据条件查找利率修改条件信息 操作
     * ContractRateSetting 数据表 查询相应记录
     * 
     * @param long
     *            lID 利率修改条件标示
     * @return AdjustInterestConditionInfo
     * @throws RemoteException
     * @throws SettlementDAOException
     * @throws SettlementDAOException
     */
    public SettAdjustInterestConditionInfo findAdjustInterestRateByID(long id)
            throws java.rmi.RemoteException;
    //SettlementException
   

    /**
     * findUpdateInterestByMultiOption 查找应复核的利率修改 根据条件查找应该复核的利率修改 操作 Contract
     * RateSetting 数据表 查询相应记录
     * 
     * @param long
     *            lActionID 操作标示1：修改查询；2：复核查询
     * @param Timestamp
     *            tsStartDate 利率修改开始日
     * @param Timestamp
     *            tsEndDate 利率修改结束日
     * @param long
     *            lStatusID 利率修改状态
     * @param long
     *            lCurrencyID 币种
     * @param long
     *            lOfficeID 办事处ID
     * @param long
     *            lUserID 操作人ID
     * @param long
     *            lPageLineCount 每页页行数条件
     * @param long
     *            lPageNo 第几页条件
     * @param long
     *            lOrderParam 排序条件，根据此参数决定结果集排序条件
     * @param long
     *            lDesc 升序或降序
     * @return Collection 结果集
     * @throws RemoteException
     */
    public Collection findAdjustInterestByMultiOption(
            SettAdjustInterestQueryInfo info) throws java.rmi.RemoteException;
    

    /**
     * 新增审批意见
     * <p>
     * <b>&nbsp; </b>
     * <ol>
     * <b>新增审批意见 </b>
     * <ul>
     * <li>操作数据库表loan_approvalTracing,loan_rateadjustpaycondition
     * <li>如果审批决定是拒绝，修改带审批的主体状态
     * </ul>
     * </ol>
     * 
     * @Copyright (c) Jan. 2003, by iSoftStone Inc. All Rights Reserved
     * @param long
     *            nReviewTypeID 审批类型
     * @param long
     *            nReviewContentID 审批内容类型
     * @param String
     *            sOpinion 审批意见
     * @param long
     *            nUserID 审批人标示
     * @param long
     *            nNextUserID 下一个审批人标示
     * @param long
     *            action 审批，拒绝，修改，最后审批
     * @return long 成功，返回值 == 1，失败，返回值 == -1
     * @exception Exception
     */
    public long checkAdjustInterestRate(long lApprovalContentID,
            String sOpinion, long lUserID, long lNextUserID, long lAction)
            throws RemoteException;
    

    /**
     * cancelUpdateInterestRate 取消普通贷款银行利率修改 取消普通贷款银行利率修改 操作 AdjustedContract
     * 数据表 更新相应记录 应首先检查审核状态
     * 
     * @param lAdjustConditionID :
     *            ContractRateSetting.ID
     * @return long 成功返回ID标识，失败返回0
     * @throws RemoteException
     */
    public long cancelAdjustInterestRate(long lAdjustConditionID)
            throws java.rmi.RemoteException;
    

   

    /**
     * 复合利率调整申请
     * 
     * @param lID
     * @param userID
     * @return @throws
     *         java.rmi.RemoteException
     */
    public long checkAdjustInterestRate(long lID, long userID)
            throws java.rmi.RemoteException;
   
}
