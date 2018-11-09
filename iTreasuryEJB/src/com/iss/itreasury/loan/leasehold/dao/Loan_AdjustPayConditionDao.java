package com.iss.itreasury.loan.leasehold.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdInterestAdjustInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPlanDetailInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPlanInfo;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.loan.util.LOANConstant.AssureChargeNoticeStatus;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant.ApprovalAction;
import com.iss.itreasury.util.Constant.ApprovalDecision;
import com.iss.itreasury.util.Constant.ModuleType;
import com.iss.itreasury.util.Constant.PageControl;
import com.iss.itreasury.util.Constant.RecordStatus;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.repayplan.dataentity.*;

/**
 *
 * <p>
 * Title: 融资租赁收款通知单
 * </p>
 *
 * <p>
 * Description: 信贷管理－融资租赁
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 *
 * <p>
 * Company: iSoftStone
 * </p>
 *
 * @author liuxz
 * @version 1.0
 */
public class Loan_AdjustPayConditionDao extends LoanDAO {

    protected Log4j log = new Log4j(ModuleType.LOAN, this);
    private void cleanup(ResultSet rs) throws SQLException
    {
        try
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
        }catch (SQLException sqle){ }
    }
    private void cleanup(CallableStatement cs) throws SQLException
    {
        try
        {
            if (cs != null)
            {
                cs.close();
                cs = null;
            }
        }catch (SQLException sqle){ }
     }
     private void cleanup(PreparedStatement ps) throws SQLException
     {
        try
        {
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
         }catch (SQLException sqle){}
     }
     private void cleanup(Connection con) throws SQLException
     {
        try
        {
            if (con != null)
            {
                con.close();
                con = null;
            }
         }catch (SQLException sqle){ }
    }

    public Loan_AdjustPayConditionDao() {
        super("LOAN_LEASEHOLDRATEADJUST");
    }

    /**
     * 融资租赁利率调整通知单的保存操作
     *
     * @param paynoticeinfo
     *            LeaseholdPayNoticeInfo
     * @return long
     * @throws LoanException
     */
    public long saveAdjustNotice(LeaseholdInterestAdjustInfo noticeinfo) throws
            LoanException {
        long returnRes = -1l;
        try {
            if (noticeinfo == null) {
                return returnRes;
            } else if (noticeinfo.getId() <= 0) {
                /** 更新通知单表 */
                setUseMaxID();
                returnRes = add(noticeinfo);
            } else if (noticeinfo.getId() > 0) {
                /** 更新通知单表 */
                update(noticeinfo);
                returnRes = noticeinfo.getId();
            }
        } catch (Exception e) {
            log.error(" 融资租赁通知单的保存操作失败原因：\r\n" + e);
            throw new LoanException("Gen_E001", e);
        }
        return returnRes;
    }

    /**
     * 融资租赁利率调整通知单的取消操作
     *
     * @param noticeid
     *            long
     * @throws LoanException
     */
    public void cancelAdjustNotice(long noticeid) throws LoanException {
        LeaseholdInterestAdjustInfo noticeinfo;
        try {
            noticeinfo = new LeaseholdInterestAdjustInfo();
            noticeinfo.setId(noticeid);
            /**
             * <p>
             * 由于融资租赁收款通知单的状态与担保收费通知单状态所有就用 担保收费通知单状态
             * </p>
             */
            noticeinfo.setNstatusID(AssureChargeNoticeStatus.CANCEL);
            update(noticeinfo);
        } catch (Exception e) {
            log.error(" 融资租赁收款通知单的取消操作失败原因：\r\n" + e);
            throw new LoanException("Gen_E001", e);
        }
    }

    /**
     * 融资租赁利率调整通知单的审核操作
     *
     * @param info
     *            ApprovalTracingInfo
     * @throws LoanDAOException
     */
    public void checkAdjustNotice(ApprovalTracingInfo info) throws
            LoanDAOException {
        long lCount = 0;
        long lStatusID = -1;
        long lResultID = -1;
        long lApprovalID = -1;
        long[] lApprovalContentIDList;
        // 定义相应操作常量
        // 模块类型
        long lModuleID = LOANConstant.ModuleType.LOAN;
        info.setModuleID(lModuleID);
        // 操作类型
        long lActionID = LOANConstant.ApprovalAction.INTEREST_ADJUST;
        info.setActionID(lActionID);
        String sContractID = LOANNameRef.getNameByID("ncontractid",
                "loan_leaseholdrateadjust", "id", String.valueOf(info
                .getApprovalContentIDList()[0]), null);

        
        log.info("sContractID=="+sContractID);
        long conID = -1;
        if (sContractID != null && sContractID.length() > 0) {
            conID = Long.valueOf(sContractID).longValue();
        }
        long lLoanTypeID = -1;
        String sSubType = LOANNameRef.getSubTypeByContractID(conID);
        if (sSubType != null && sSubType.length() > 0) {
            lLoanTypeID = Long.valueOf(sSubType).longValue();
        }
        info.setLoanTypeID(lLoanTypeID);
        ApprovalDelegation appbiz = new ApprovalDelegation();
        lApprovalContentIDList = info.getApprovalContentIDList();
        if (lApprovalContentIDList.length > 0) {
            try {
                // 获得ApprovalID
                lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID,
                        lActionID, info.getOfficeID(), info.getCurrencyID());
                info.setApprovalID(lApprovalID);
            } catch (Exception e1) {
                log.error("获得ApprovalID失败原因是:\r\n" + e1);
                throw new LoanDAOException(" 获得ApprovalID失败 ", e1);
            }
            // 处理审批意见
            switch ((int) info.getCheckActionID()) {
                case (int) LOANConstant.Actions.REJECT: {
                    // 拒绝
                    // 审批意见状态
                    lStatusID = RecordStatus.VALID;
                    // 审批操作类型
                    lResultID = ApprovalDecision.REFUSE;
                    break;
                }
                case (int) LOANConstant.Actions.CHECK: {
                    // 审批
                    lStatusID = RecordStatus.VALID;
                    lResultID = ApprovalDecision.PASS;
                    break;
                }
                case (int) LOANConstant.Actions.CHECKOVER: {
                    // 审批&&最后
                    lStatusID = RecordStatus.VALID;
                    lResultID = ApprovalDecision.FINISH;
                    // 审批完成后需要做的操作
                    break;
                }
                case (int) LOANConstant.Actions.RETURN: {
                    // 修改
                    lStatusID = RecordStatus.VALID;
                    lResultID = ApprovalDecision.RETURN;
                    break;
                }
            }
            info.setApprovalID(lApprovalID);
            info.setResultID(lResultID);
            info.setStatusID(lStatusID);
            lCount = lApprovalContentIDList.length;
            for (int i = 0; i < lCount; i++) {
                if (lApprovalContentIDList[i] > 0) {
                    info.setApprovalContentID(lApprovalContentIDList[i]);
                } else {
                    break;
                }
                // 审核通知单
                check(info);
                try {
                    appbiz.saveApprovalTracing(info);
                } catch (Exception e) {
                    log.error(" 审核融资租赁收款通知单时出错 :\r\n" + e);
                    throw new LoanDAOException(" 审核融资租赁收款通知单时出错 ", e);
                }
            }
        }
    }

    /**
     * 融资租赁利率调整通知单的多笔查询操作
     *
     * @param paynoticeinfo
     *            LeaseholdPayNoticeInfo
     * @return Collection
     * @throws LoanException
     */
    public Collection findAdjustNoticeByMultiOption(LeaseholdInterestAdjustInfo
            noticeinfo) throws LoanException {
        Collection c = null;
        long lApprovalID = -1l;
        String strUser = " ( 0 ) ";
        // 定义相应操作常量
        // 模块类型
        long lModuleID = ModuleType.LOAN;
        // 业务类型
        long lLoanTypeID = LOANConstant.LoanType.RZZL;
        // 操作类型
        long lActionID = ApprovalAction.LEASEHOLDPAY_NOTICE;

        LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
        ApprovalDelegation appBiz = new ApprovalDelegation();

        // 得到审批流ID
        try {
            lApprovalID = appBiz.getApprovalID(lModuleID, lLoanTypeID,
                                               lActionID,
                                               noticeinfo.getOfficeId(),
                                               noticeinfo
                                               .getCurrencyId());
        } catch (Exception e1) {
            log.error("getApprovalID fail");
            e1.printStackTrace();
        }

        // 查询可以做该审核的实际用户（真实或传给的虚拟的人！）
        try {
            long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
                    noticeinfo.getOfficeId(), noticeinfo.getCurrencyId(),
                    new long[] {lLoanTypeID});
            if (a_SubLoanType != null && a_SubLoanType.length > 0) {
                strUser = "( ";
                for (int i = 0; i < a_SubLoanType.length; i++) {
                    strUser = strUser
                              + " (a.NEXTCHECKUSERID in "
                              + appBiz.findTheVeryUser(lModuleID,
                            a_SubLoanType[i], lActionID, noticeinfo
                            .getOfficeId(), noticeinfo
                            .getCurrencyId(), noticeinfo
                            .getUserID())
                              + " and b.NSUBTYPEID = " + a_SubLoanType[i] + ")";
                    if (i < a_SubLoanType.length - 1)
                        strUser += " or ";
                    else
                        strUser += " ) ";
                }
            } else {
                return null;
            }
            noticeinfo.setStrUser(strUser);
        } catch (Exception e2) {
            log.error("获得真正来审批这个记录的人失败原因：\r\n" + e2);
            throw new LoanException("Gen_E001", e2);
        }

        try {
            c = findByMultiObj(noticeinfo);
        } catch (Exception e) {
            throw new LoanException("Gen_E001", e);
        }
        return c;
    }

    protected Collection findByMultiObj(LeaseholdInterestAdjustInfo
                                        paynoticeinfo) throws LoanDAOException {
        String strSQL = "";
        String strSelect = "";

        Vector v = new Vector();
        Timestamp startDate = paynoticeinfo.getQueryStartDate(); // 租赁日期
        Timestamp endDate = paynoticeinfo.getQueryEndDate(); // 租赁日期
        long statusID = paynoticeinfo.getNstatusID(); // 状态
        long currencyID = paynoticeinfo.getCurrencyId(); // 币种
        long officeID = paynoticeinfo.getOfficeId(); // 办事处

        long userID = paynoticeinfo.getNinputUserID();
        String strUser = paynoticeinfo.getStrUser();
        long queryPurpose = paynoticeinfo.getQueryPurpose();

        long pageLineCount = paynoticeinfo.getPageLineCount();
        long pageNo = paynoticeinfo.getPageNo();
        long desc = paynoticeinfo.getDesc();
        String orderParamString = paynoticeinfo.getOrderParamString();
        long recordCount = -1l;
        long pageCount = -1l;
        long rowNumStart = -1l;
        long rowNumEnd = -1l;
        try {
            initDAO();
            // 计算记录总数
            if (queryPurpose == 1l) { // for modify
                strSQL = "";
                strSelect = " select count(*) ";
                strSQL =
                        " from loan_leaseholdrateadjust a,Loan_ContractForm b,Client c "
                        + " where a.NCONTRACTID = b.ID and b.nBorrowClientID = c.ID(+) "
                        //+ " and a.NStatusID >= "
                        //+ LOANConstant.InterestRateSettingStatus.SUBMIT
                        //+ " and a.NStatusID <= "
                        //+ LOANConstant.LoanPayNoticeStatus.CHECK
                        + " and a.NINPUTUSERID = " + userID;
                if (statusID == LOANConstant.InterestRateSettingStatus.SUBMIT) {
                    strSQL += " and a.nnextchecklevel = 1 ";
                    strSQL += " and a.NStatusID = "
                            + LOANConstant.InterestRateSettingStatus.SUBMIT;
                } else {
                    strSQL += " and a.NStatusID = "
                            + LOANConstant.InterestRateSettingStatus.CHECK;
                }
            } else if (queryPurpose == 2l) { // for examine
                strSelect = " select count(*) ";
                strSQL =
                        " from loan_leaseholdrateadjust a,Loan_ContractForm b,Client c "
                        +
                        " where a.NCONTRACTID = b.ID and b.nBorrowClientID = c.ID(+) "
                        + " and " + strUser;
                if (statusID == LOANConstant.InterestRateSettingStatus.SUBMIT) {
                    strSQL += " and a.NStatusID = "
                            + LOANConstant.InterestRateSettingStatus.SUBMIT;
                } else {
                    strSQL += " and a.NStatusID = "
                            + LOANConstant.InterestRateSettingStatus.SUBMIT;
                }
            }
            // ////////////////////查询条件////////////////////////////

            if (officeID > 0) {
                strSQL += " and a.NOfficeID = " + officeID;
            }
            if (currencyID > 0) {
                strSQL += " and a.NcurrencyID = " + currencyID;
            }
            if (startDate != null) {
                strSQL += " and to_char(a.DTINPUTDATE,'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate) + "'";
            }
            if (endDate != null) {
                strSQL += " and to_char(a.DTINPUTDATE,'yyyy-mm-dd') <= '"
                        + DataFormat.getDateString(endDate) + "'";
            }
            // //////////////////////////排序处理///////////////////////
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0) {
                if (orderParamString.substring(0, nIndex).toLowerCase().equals(
                        "loan_leaseholdrateadjust")) {
                    strSQL += " order by a."
                            + orderParamString.substring(nIndex + 1);
                } else if (orderParamString.substring(0, nIndex).toLowerCase()
                           .equals("loan_contractform")) {
                    strSQL += " order by b."
                            + orderParamString.substring(nIndex + 1);
                } else if (orderParamString.substring(0, nIndex).toLowerCase()
                           .equals("client")) {
                    strSQL += " order by c."
                            + orderParamString.substring(nIndex + 1);
                }
            } else {
                strSQL += " order by a.ID";
            }
            if (desc == PageControl.CODE_ASCORDESC_DESC) {
                strSQL += " desc";
            }
            try {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next()) {
                    recordCount = rs.getLong(1);
                }
            } catch (ITreasuryDAOException e) {
                log.error("批量查询通知单笔数产生错误 : " + e.getMessage());
                throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            } catch (SQLException e) {
                log.error("批量查询通知单笔数产生错误 : " + e.getMessage());
                throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            }
            //计算总页数
            if (recordCount > pageLineCount) {
                pageCount = recordCount / pageLineCount;
                if ((recordCount % pageLineCount) != 0) {
                    pageCount++;
                }
            } else if (recordCount > 0 &&
                       recordCount <= pageLineCount) {
                pageCount = 1;
            } else {
                pageCount = 0;
            }
            // ////////////////返回需求的结果集////////////////////////////////////
            rowNumStart = (pageNo - 1) * pageLineCount + 1;
            rowNumEnd = rowNumStart + pageLineCount - 1;
            strSelect =
                    " select a.* ";
            strSQL = " select * from ( select aa.*,rownum r from ( "
                     + strSelect + strSQL;
            strSQL += " ) aa ) where r between " + rowNumStart + " and "
                    + rowNumEnd;
            try {
                System.out.println("=====================strSQL1" + strSQL);
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                ContractDao c_dao =  new ContractDao();
                while (rs1 != null && rs1.next()) {
                    LeaseholdInterestAdjustInfo assureChargeNoticeInfo = new
                            LeaseholdInterestAdjustInfo();
                    assureChargeNoticeInfo.setId(rs1.getLong("ID")); // 编号
                    assureChargeNoticeInfo.setNcontractID(rs1
                            .getLong("NCONTRACTID")); // CONTRACTID
                    assureChargeNoticeInfo.setNofficeID(rs1.getLong("NOFFICEID")); // 办事处
                    assureChargeNoticeInfo.setNcurrencyID(rs1
                            .getLong("NCURRENCYID")); // 币种

                    ContractInfo cinfo = c_dao.findByID(rs1.getLong("NCONTRACTID"));
                    ContractAmountInfo ainfor = cinfo.getAInfo();
                    assureChargeNoticeInfo.setBalanceAmount(ainfor.getBalanceAmount()); // 贷款余额
                    assureChargeNoticeInfo.setContractCode(cinfo.getContractCode());
                    assureChargeNoticeInfo.setClientName(cinfo.getBorrowClientName());
                    assureChargeNoticeInfo.setBeforRate(cinfo.getLateRateString());
                    assureChargeNoticeInfo.setIntervalNum(cinfo.getIntervalNum());

                    assureChargeNoticeInfo.setSreason(rs1
                            .getString("SREASON")); // 原因
                    assureChargeNoticeInfo.setDtinputDate(rs1
                            .getTimestamp("DTVALIDATE")); // 利率调整日期开始日期
                  assureChargeNoticeInfo.setNnextCheckLevel(rs1
                            .getLong("NNEXTCHECKLEVEL")); // 下一级审核级别
                    assureChargeNoticeInfo.setNinputUserID(rs1
                            .getLong("NINPUTUSERID")); // 录入人
                    assureChargeNoticeInfo.setDtinputDate(rs1
                            .getTimestamp("DTINPUTDATE")); // 录入时间
                    assureChargeNoticeInfo.setNstatusID(rs1.getLong("NSTATUSID")); // 状态
                    // 表中没有的字段
                    assureChargeNoticeInfo.setRecordCount(recordCount); // 记录数
                    assureChargeNoticeInfo.setPageCount(pageCount); // 页数
                    // 新加的字段2006－3－20
                    assureChargeNoticeInfo.setMrate(rs1.getDouble("MRATE"));
                    v.add(assureChargeNoticeInfo);
                }
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException("批量查询申请书产生错误", e);
            } catch (SQLException e) {
                throw new LoanDAOException("批量查询申请书产生错误", e);
            }
            try {
                finalizeDAO();
            } catch (ITreasuryDAOException e) {
                log.error("批量查询通知单笔数产生错误 : " + e.getMessage());
                throw new LoanDAOException(e);
            }
        } catch (Exception e) {
            throw new LoanDAOException("批量查询通知单笔数产生错误", e);
        } finally {
            try {
                finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                throw new LoanDAOException(e1);
            }
        }
        return (v.size() > 0 ? v : null);
    }

    protected long check(ApprovalTracingInfo ATInfo) throws LoanDAOException {
        long lApprovalContentID = ATInfo.getApprovalContentID();
        long lNextUserID = ATInfo.getNextUserID();
        long lApprovalID = ATInfo.getApprovalID();
        LeaseholdInterestAdjustInfo aInfo = new LeaseholdInterestAdjustInfo();
        LeaseholdInterestAdjustInfo tempInfo = new LeaseholdInterestAdjustInfo();
        try {
            tempInfo = (LeaseholdInterestAdjustInfo) findByID(lApprovalContentID,
                    tempInfo.getClass());
        } catch (ITreasuryDAOException e) {
            throw new LoanDAOException(e);
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) { // 拒绝
            aInfo.setId(lApprovalContentID);
            tempInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.REFUSE);
            try {
                update(aInfo);
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException(e);
            }
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) { // 审批
        	aInfo.setId(lApprovalContentID);
            aInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
            aInfo.setNnextCheckUserID(lNextUserID);
            ApprovalDelegation appbiz = new ApprovalDelegation();
            try {
                aInfo.setNnextCheckLevel(appbiz.findApprovalUserLevel(
                        lApprovalID, lNextUserID));
            } catch (Exception e) {
                throw new LoanDAOException("设置下一个审核级别时出错：", e);
            }
            try {
                update(aInfo);
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException(" 审批更新出错 ", e);
            }
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) { // 审批&&最后
            aInfo.setId(lApprovalContentID);          
            aInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.CHECK);
            aInfo.setNnextCheckUserID(lNextUserID);
            log.info("999999999999999999999999=="+lNextUserID);
            try {
                update(aInfo);
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException(e);
            }
            // 审批完成后需要做的操作
            log.info("999999999999999999999999lApprovalContentID:"+lApprovalContentID);
            doAfterCheckOver(lApprovalContentID);
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<:");
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) { // 修改
            aInfo.setId(lApprovalContentID);
            aInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
            aInfo.setNnextCheckUserID(ATInfo.getInputUserID());
            // 置下一级审核为1
            aInfo.setNnextCheckLevel(1);
            try {
                update(aInfo);
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException(e);
            }
        }
        log.debug("check end");
        return lApprovalContentID;
    }
    
    /*@author liwang
    * @version 1.0
    * */
    private void doAfterCheckOver(long lNoticeID) throws LoanDAOException {
    	
    	PreparedStatement ps = null;
        ResultSet rs=null;        
   	    Connection conn = null;
   	    String strSQL = null;   	    
   	   
   	    long nplanversion=-1;//版本号
   	    
   	    LeaseholdInterestAdjustInfo lao=new LeaseholdInterestAdjustInfo();
   	    PlanVersionInfo pvi=new PlanVersionInfo();
   	    
   	    double dTotal = 0;//本金
   	    double dRecognizanceAmount = 0;//保证金
   	    double dRate = 0;//利率
   	    double dInterestAmount = 0;//利息
   	    long lInterestCountType = -1;//利息计算方式
   	    long nPayType = -1;//租金偿还方式
   	    long lRepayNum = -1;//偿还频率
   	    long lIntervalNum = -1;//期限
   	    long lNewPlanID = -1;
   	    long planCount = -1;
   	    Timestamp dtInput=null; //录入日期 
   	    long n=0;
   	    long   planDetailID=-1;
   	    
    	try
    	{
    		conn=Database.getConnection();
    		//根据调整通知ID获得新的利率和合同ID以及再关联合同表取得合同本金、利率、利息计算方式、还款方式
    		strSQL=" select lr.mrate,lr.sreason,lr.ncontractid,lc.* from loan_leaseholdrateadjust lr,loan_contractform lc where lr.ncontractid=lc.id and lr.id="+lNoticeID;
    		
    		log.info("根据通知单IDbefor："+strSQL);
    		ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if(rs.next())
            {
            	lao.setDtinputDate(rs.getTimestamp("dtinputdate"));	
            	lao.setMrate(rs.getDouble("mrate"));
            	lao.setNcontractID(rs.getLong("id"));
            	lao.setSreason(rs.getString("sreason"));             	
            	dTotal = rs.getDouble("MLOANAMOUNT");//benjin
            	nPayType = rs.getLong("ASSURECHARGETYPEID");
            	lInterestCountType = rs.getLong("NINTERESTCOUNTTYPEID");
            	lIntervalNum = rs.getLong("NINTERVALNUM");
            }
            log.info("根据通知单ID获取sql语句："+strSQL);
            cleanup(rs);
            cleanup(ps);            
            
            
            /* 首先获得该笔合同下最大的版本的ID */
            strSQL=" select max(llc.nplanversion) nplanversion from loan_loancontractplan llc  where llc.ncontractid="+lao.getNcontractID();
            ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if(rs.next())
            {
            	nplanversion=rs.getLong("nplanversion");
            }
            log.info("合同下最大的版本的sql："+strSQL);
            log.info("合同下最大的版本的ID："+nplanversion);
            cleanup(rs);
            cleanup(ps);
            
            //根据合同ID获得放款计划记录
            strSQL=" select *  from loan_loancontractplan where ncontractid="+lao.getNcontractID()+" and  NPLANVERSION="+nplanversion;
            //System.out.println("loan_loancontractplan："+strSQL);
            log.info("loan_loancontractplan："+strSQL);
            ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            nplanversion=nplanversion+1;//版本id加1
            if(rs.next())
            {
            	pvi.setID(rs.getLong("ID"));
            	pvi.setContractID(lao.getNcontractID());
            	pvi.setLoanID(rs.getLong("NLOANID"));
            	pvi.setPlanVersion(nplanversion);
            	pvi.setStatusID(rs.getLong("NSTATUSID"));
            	pvi.setIsUsed(rs.getLong("NISUSED"));
            	pvi.setUserType(rs.getLong("NUSERTYPE"));
            	pvi.setInput(Env.getSystemDate());
            	pvi.setSReason(lao.getSreason());
            }         
            //插入计划版本
            lNewPlanID = this.insertPlanInfo(pvi);
            System.out.println("lNewPlanID:"+lNewPlanID);
            
            //获取最新版本的计划
            Vector planVector = (Vector) findByLoanContractPlanID(pvi.getID());
            PlanDetailInfo planInfo = null;
            PlanDetailInfo planInfo1 = new PlanDetailInfo();
			if ((planVector != null) && (planVector.size() > 0))
			{
				planCount = planVector.size();
//			判断还款频率
				switch ( (int)nPayType )
				{
					case  (int)LOANConstant.ChargeRatePayType.YEAR  :     //年
                    
                	   lRepayNum = 12;
                	   break;
					case  (int)LOANConstant.ChargeRatePayType.HALFYEAR  :     //半年
                    
                    lRepayNum = 6;
                    break;
					case (int)LOANConstant.ChargeRatePayType.QUARTER  :     //季
                    
                    lRepayNum = 3;    
                    break;
					case  (int)LOANConstant.ChargeRatePayType.MONTH :    //月
                    
                	   lRepayNum = 1;    
                	   break;
				}
				dtInput=pvi.getInput();//利率调整日期
				
	             System.out.println("planCount:"+planCount);
	             //开始循环插入经利率调整后的偿付计划表
				for (int i = 0; i < planCount; i++) 
				{
					planInfo = (PlanDetailInfo) planVector.get(i);
					if (dtInput!=null && planInfo.getPlanDate()!=null)
					{
						
						
						planInfo1.setPlanID(lNewPlanID);
						planInfo1.setPlanDate(planInfo.getPlanDate());
		            	planInfo1.setPayTypeID(planInfo.getPayTypeID());
		            	planInfo1.setAmount(planInfo.getAmount());
		            	planInfo1.setType(planInfo.getType());
		            	planInfo1.setModifyDate(planInfo.getModifyDate());
		            	planInfo1.setExtendApplyID(planInfo.getExtendApplyID());
		            	planInfo1.setOverdueApplyID(planInfo.getOverdueApplyID());
		            	planInfo1.setLastVersionPlanID(planInfo.getLastVersionPlanID());
		            	
						if (nPayType== LOANConstant.ChargeRatePayType.ONETIME)
						{
							
			            	planInfo1.setInterestAmount((lao.getMrate() * dTotal / 12 / 100) * lIntervalNum);
			            	
						}
						else
						{
								  if (planInfo.getPlanDate().getMonth()<=dtInput.getMonth()
										&& planInfo.getPlanDate().getDay()!=dtInput.getDay()	
									 )
										    {
								            	planInfo1.setInterestAmount(planInfo.getInterestAmount());
								             
										    }
								  else 
								    {
										  if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEAMOUNT)//等额
											{
												planInfo1.setInterestAmount( (dTotal*lao.getMrate()/12/100) * lRepayNum );
												//等额计算公式：（本金*利率/12/100）*（租赁期限（月）/期数）
												System.out.print(" 等额利息="+planInfo1.getInterestAmount());
											}
											else if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEPRINCIPAL)//等本
											{
												double d = planCount;
												planInfo1.setInterestAmount((d-i)/d*dTotal * (lao.getMrate()/12/100) * lRepayNum);										
												System.out.print(" 等本利息="+planInfo1.getInterestAmount());
												//等本计算方式：（本金－已还本金）*（利率/12/100）* （租赁期限（月）/期数）
											}
								    }
						}
						planInfo1.setRecognizanceAmount(planInfo.getRecognizanceAmount());
                        //新增合同租赁尝计划信息表
						insertPlanDetail(planInfo1);
					} //END FOR IF
					
					
				} // END FOR planVector
				
			} //END FOR
            cleanup(conn);
            
            
            
    	}
    	catch (Exception e) {
    		throw new LoanDAOException("租赁偿付表生成计划出错：", e);
    		
        }finally{
        	try{
        		if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
        	}
        	catch(Exception e)
        	{
        	
        		rs=null;
        		ps=null;
        		conn=null;
        	}
        }
    
    }
    /*@author liwang
     * @version 1.0
     * */
    private long insertPlanInfo(PlanVersionInfo pvi)throws Exception
    {
	   	 PreparedStatement ps = null;
		 
	     ResultSet rs = null;
	     Connection conn = null;
	     String strSQL = null;
	     long lMaxId =-1;
	     long  lResult=-1;
	     long   planDetailID=-1;
	     
	     try
	     {
	         conn=Database.getConnection();
	         
	         //获得当前最新偿付计划表最大ID
	         strSQL=" select max(id)+1 id from loan_loancontractplan";
	         ps=conn.prepareStatement(strSQL);
	         rs=ps.executeQuery();
	         if(rs.next())
	         {
	        	 lMaxId=rs.getLong("id");
	         }
	         cleanup(rs);
	         cleanup(ps);
	         
	        
	         if ( lMaxId<0 )
	         {
	             log.info("Get Next ID Error when Insert a loan_loancontractplan Version");
	             
	         }
	         /* 执行插入操作*/
	         strSQL="Insert into loan_loancontractplan("
	             +"ID, nLoanID,ncontractid,nPlanVersion, nStatusID, sreason,nisused,nusertype,dtinputdate "
	             
	             +") values(?,?,?,?,?,?,?,?,?)";
	         
	         ps=conn.prepareStatement(strSQL);
	         int n=1;
	         ps.setLong(n++,lMaxId);
	         ps.setLong(n++,pvi.getLoanID());
	         ps.setLong(n++,pvi.getContractID());
	         ps.setLong(n++,pvi.getPlanVersion());
	         ps.setLong(n++,pvi.getStatusID());
	         ps.setString(n++,pvi.getSReason());
	         ps.setLong(n++,pvi.getIsUsed());
	         ps.setLong(n++,pvi.getUserType());	         	         
	         ps.setTimestamp(n++,pvi.getInput());
	         lResult=ps.executeUpdate();
	         
	         if ( lResult<0 )
	         {
				throw new IException("Loan_E020");
	         }
	         cleanup(rs);
	         cleanup(ps);
	     }
	     catch(Exception e)
	     {
	    	 cleanup(rs);
	 		 cleanup(ps);
	 		 cleanup(conn);
	    	 throw e;
	     }
	     finally
	     {
	    	 cleanup(rs);
	 		 cleanup(ps);
	 		 cleanup(conn);
	     }
	     return lMaxId;
    }
    /*@author liwang
     * @version 1.0
     * */
    private void insertPlanDetail(PlanDetailInfo planInfo) throws Exception
    {
    	 PreparedStatement ps = null;
    	 ResultSet rs=null;  
         
         Connection conn = null;
         String strSQL = null;
         long  lResult=-1;
         int n = -1;	
         long planDetailID=-1;
         
        
         try
         {
             conn=Database.getConnection();
             strSQL = "select nvl(max(ID)+1,1) oID from LOAN_LOANCONTRACTPLANDETAIL";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					planDetailID = rs.getLong("oID");
				}
				 cleanup(rs);
	             cleanup(ps);
             
					strSQL = "insert into LOAN_LOANCONTRACTPLANDETAIL(id,ncontractplanid,dtplandate,npaytypeid,"
							+ "mamount,stype,dtmodifydate,nlastextendid,nlastoverdueid,nlastversionplanid,MINTERESTAMOUNT,MRECOGNIZANCEAMOUNT)"
							+ "values (?,?,?,?,?,?,?,?,?,?,?,? )";
					
					
						ps = conn.prepareStatement(strSQL);
						
						n = 1;
								ps.setLong(n++,planDetailID);
								ps.setLong(n++, planInfo.getPlanID());
								ps.setTimestamp(n++, planInfo.getPlanDate());
								ps.setLong(n++, planInfo.getPayTypeID());
								ps.setDouble(n++, planInfo.getAmount());
								ps.setString(n++, planInfo.getType());
								ps.setTimestamp(n++, planInfo.getModifyDate());
								ps.setLong(n++, planInfo.getExtendApplyID());
								ps.setLong(n++, planInfo.getIsOverdue());
								ps.setLong(n++, planInfo.getLastVersionPlanID());				
								ps.setDouble(n++, planInfo.getInterestAmount());
								ps.setDouble(n++,planInfo.getRecognizanceAmount());	
						
						ps.execute();
						cleanup(ps);
						cleanup(conn);
			
         }
         catch (Exception e)
         {
 			
 			cleanup(ps);
 			cleanup(conn);
             e.printStackTrace();
             throw e;
         }finally{
 			
 			cleanup(ps);
 			cleanup(conn);
         }
    }
    /*@author liwang
     * @version 1.0
     * */
    private Collection findByLoanContractPlanID(long planDetailID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        Vector v=null;
        long nLastVersionPlanID=0;
        if ( planDetailID<=0 )
        {
            log.info("find repay Plan detail fail:loanID<0");
            return null;
        }
        
        
        try
        {
            conn=Database.getConnection();
            strSQL = "select nvl(max(nLastVersionPlanID),1) nLastVersionPlanID from LOAN_LOANCONTRACTPLANDETAIL where NCONTRACTPLANID=?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1,planDetailID);
			rs = ps.executeQuery();
			if (rs.next()) {
				nLastVersionPlanID = rs.getLong("nLastVersionPlanID");
				
			}
			 cleanup(rs);
             cleanup(ps);
            /*首先获得该loanID对应的计划版本*/
            strSQL="select * from LOAN_LOANCONTRACTPLANDETAIL where NCONTRACTPLANID=? and nLastVersionPlanID=?";
            System.out.println("strSQL:"+strSQL);
            
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,planDetailID);
            ps.setLong(2,nLastVersionPlanID);
            rs=ps.executeQuery();
            v=new Vector();
            nLastVersionPlanID=nLastVersionPlanID+1;
            while (rs.next())
            {
            	
            	PlanDetailInfo planInfo = new PlanDetailInfo(); 
            	planInfo.setID(rs.getLong("ID"));
            	planInfo.setPlanID(rs.getLong("NCONTRACTPLANID"));
            	planInfo.setPlanDate(rs.getTimestamp("DTPLANDATE"));
            	planInfo.setPayTypeID(rs.getLong("nPayTypeID"));
            	planInfo.setAmount(rs.getDouble("mAmount"));
            	planInfo.setType(rs.getString("sType"));
            	planInfo.setModifyDate(rs.getTimestamp("DTMODIFYDATE"));
            	planInfo.setExtendApplyID(rs.getLong("nLastExtendID"));
            	planInfo.setOverdueApplyID(rs.getLong("nLastOverdueID"));
            	planInfo.setLastVersionPlanID(nLastVersionPlanID);
            	planInfo.setInterestAmount(rs.getDouble("MINTERESTAMOUNT"));
            	planInfo.setRecognizanceAmount(rs.getDouble("MRECOGNIZANCEAMOUNT"));
                v.add(planInfo);
                System.out.println("rs.getLong:"+planInfo.getID());
            }
            rs.close();
            rs=null;
            ps.close();
			ps = null;

			conn.close();
			conn = null;
       
        }
        catch (Exception e)
        {
        	if (rs != null) {
				rs.close();
				rs = null;
			}
			
			if (conn != null) {
				conn.close();
				conn = null;
			}
            e.printStackTrace();
            throw e;
        }finally{
        	if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
        }
		return v;
    }
    public static void main(String[] args) {
        Loan_AdjustPayConditionDao dao = new Loan_AdjustPayConditionDao();
        //调试融资租赁偿付计划表
        try {
        	Loan_AdjustPayConditionDao lpo=new Loan_AdjustPayConditionDao();
        	lpo.doAfterCheckOver(9);
            System.out.println("ok = ");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
