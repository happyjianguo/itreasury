/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settadjustinterestrate.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.settadjustinterestrate.dao.Sett_RateAdjustPayConditionDAO;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestConditionInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestQueryInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettRateAdjustPayConditionInfo;
import com.iss.itreasury.settlement.settpaynotice.dao.SettPayNoticeDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;

/**
 * @author jinchen
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettAdjustInterestRateEJB implements SessionBean
{
    private javax.ejb.SessionContext mySessionCtx = null;

    final static long serialVersionUID = 3212393123460846163L;

    /**
     * ejbActivate method comment
     * 
     * @exception java.rmi.RemoteException
     *                异常说明。
     */
    public void ejbActivate() throws java.rmi.RemoteException
    {
    }

    /**
     * ejbCreate method comment
     * 
     * @exception javax.ejb.CreateException
     *                异常说明。
     * @exception java.rmi.RemoteException
     *                异常说明。
     */
    public void ejbCreate() throws javax.ejb.CreateException,
            java.rmi.RemoteException
    {
    }

    /**
     * ejbPassivate method comment
     * 
     * @exception java.rmi.RemoteException
     *                异常说明。
     */
    public void ejbPassivate() throws java.rmi.RemoteException
    {
    }

    /**
     * ejbRemove method comment
     * 
     * @exception java.rmi.RemoteException
     *                异常说明。
     */
    public void ejbRemove() throws java.rmi.RemoteException
    {
    }

    /**
     * getSessionContext method comment
     * 
     * @return javax.ejb.SessionContext
     */
    public javax.ejb.SessionContext getSessionContext()
    {
        return mySessionCtx;
    }

    /**
     * setSessionContext method comment
     * 
     * @param ctx
     *            javax.ejb.SessionContext
     * @exception java.rmi.RemoteException
     *                异常说明。
     */
    public void setSessionContext(javax.ejb.SessionContext ctx)
            throws java.rmi.RemoteException
    {
        mySessionCtx = ctx;
    }

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
            throws SettlementException, RemoteException
    {
        /*
         * long lID = -1; long lLoanInterestRateID = -1; long lContractID = -1;
         * long lLetoutRequisitionID = -1; double dRate = 0.0; double
         * dAdjustRate = 0.0; double dStaidAdjustRate = 0.0; Timestamp
         * tsValidate = null; String strReason = null; long lCurrencyID = -1;
         * long lOfficeID = -1; long lInputUserID = -1; Timestamp tsInputDate =
         * null;
         */

        String strErrorMessage = null;
        long lID = -1;
        long lStatusID = -1;
        long lSaveId = -1;
        lID = info.getId();

        strErrorMessage = this.checkAddValidity(info);
        //      抛出自定义异常

        try
        {
            if (strErrorMessage != null && strErrorMessage.length() > 0)
            {
                throw new SettlementException("Sett_E180", strErrorMessage,
                        null);
            }
            Log.print("新增利率调整数据通过验证...................................");

            if (lID <= 0)
            {
                lStatusID = SETTConstant.SettAdjustInterestStatus.SUBMIT;
                info.setStatusId(lStatusID);
                Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();

                lSaveId = dao.add(info);

            } else
            {
                lStatusID = SETTConstant.SettAdjustInterestStatus.SUBMIT;
                info.setStatusId(lStatusID);
                info.setNextCheckLevel(1);
                info.setNextCheckUserId(info.getInputUserId());
                lSaveId = info.getId();
                Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
                dao.update(info);

            }

        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lSaveId;
    }

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
            throws java.rmi.RemoteException
    //SettlementException
    {

        SettAdjustInterestConditionInfo settAdjustInterestConditionInfo = null;

        Sett_RateAdjustPayConditionDAO sett_RateAdjustPayConditionDAO = new Sett_RateAdjustPayConditionDAO();
        try
        {
            settAdjustInterestConditionInfo = sett_RateAdjustPayConditionDAO
                    .findAdjustInterestRateByID(id);
        } catch (SettlementDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (settAdjustInterestConditionInfo != null)
        {
            Timestamp tsTemp = DataFormat
                    .getPreviousDate(settAdjustInterestConditionInfo
                            .getValidate());

            if (settAdjustInterestConditionInfo.getLoanPayNoticeID() > 0)
            {

                SettPayNoticeDAO settPayNoticeDao = new SettPayNoticeDAO();
                try
                {
                    settAdjustInterestConditionInfo
                            .setFInterestRate(settPayNoticeDao.getLatelyRate(
                                    settAdjustInterestConditionInfo
                                            .getLoanPayNoticeID(), tsTemp));
                } catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
            } else
            {
                //SettContractDAO settContractDAO = new
                // SettContractDAO("LOAN_CONTRACTFORM");

                // rateInfo =
                // sett_RateAdjustPayConditionDAO.getLatelyRate((long)-1,settAdjustInterestConditionInfo.getContractID(),tsTemp);

                try
                {
                    settAdjustInterestConditionInfo
                            .setFInterestRate(UtilOperation
                                    .getLoanInterestRate(null, (long) -1,
                                            settAdjustInterestConditionInfo
                                                    .getContractID(), tsTemp));
                } catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return settAdjustInterestConditionInfo;
    }

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
            SettAdjustInterestQueryInfo info) throws java.rmi.RemoteException
    {

        Collection c = null;
        Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
        try
        {
            c = dao.findAdjustInterestByMultiOption(info);
        } catch (SettlementDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new java.rmi.RemoteException();
        }

        return c;
    }

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
            throws RemoteException
    {
        return -1;
    }

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
            throws java.rmi.RemoteException
    {
        //      Connection con = null;
        //		PreparedStatement ps = null;
        //		ResultSet rs = null;
        String strSQL = null;
        long lResult = -1;

        Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
        //		//定义相应操作常量
        //		//贷款
        //		long lModuleID = Constant.ModuleType.LOAN;
        //		//模块类型
        //		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
        //		long lActionID = Constant.ApprovalAction.INTEREST_ADJUST;
        //
        //
        //		long lApprovalID = -1;
        //		long lContractID = -1;
        //		long lLoanPayNoticeID = -1;
        //		ApprovalBiz appbiz = new ApprovalBiz();
        /*
         * try { //获得ApprovalID lApprovalID =
         * getApprovalID(lModuleID,lLoanTypeID,lActionID); //连结数据库 con =
         * Database.getConnection(); //先删除以前的审核记录（物理删除）
         */
        SettRateAdjustPayConditionInfo info = new SettRateAdjustPayConditionInfo();
        info.setId(lAdjustConditionID);
        //		将loan_rateadjustpaycondition的装态置为无效
        info.setStatusId(Constant.RecordStatus.INVALID);

        try
        {
            dao.update(info);
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            dao.cancelAdjustInterestRate(lAdjustConditionID);
        } catch (SettlementDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lResult;
    }

    /**
     * 检查新增利率调整信息数据的有效性。如果无效返回无效
     * 
     * @param info
     * @return 如果无效返回无效原因. 有效返回null
     * @throws SettlementException
     */
    private String checkAddValidity(SettRateAdjustPayConditionInfo info)
            throws RemoteException
    {
        String strErrorMessage = null;
        Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
        try
        {
            if (dao.findRateAdjustPayConditionByCondition(info))
            {
                strErrorMessage = "存在相同的利率调整记录，请重新输入！";
            }
        } catch (SettlementDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new java.rmi.RemoteException();
        }
        return strErrorMessage;
    }

    /**
     * 复合利率调整申请
     * 
     * @param lID
     * @param userID
     * @return @throws
     *         java.rmi.RemoteException
     */
    public long checkAdjustInterestRate(long lID, long userID)
            throws java.rmi.RemoteException
    {
        try
        {
            Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
            SettRateAdjustPayConditionInfo info = new SettRateAdjustPayConditionInfo();
            info.setId(lID);
            info.setNextCheckUserId(userID);
            info.setStatusId(SETTConstant.SettAdjustInterestStatus.CHECK);

            dao.update(info);

        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
            throw new java.rmi.RemoteException();
        }
        return lID;
    }

}