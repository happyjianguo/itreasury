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
 * Title: ���������տ�֪ͨ��
 * </p>
 *
 * <p>
 * Description: �Ŵ�������������
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
     * �����������ʵ���֪ͨ���ı������
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
                /** ����֪ͨ���� */
                setUseMaxID();
                returnRes = add(noticeinfo);
            } else if (noticeinfo.getId() > 0) {
                /** ����֪ͨ���� */
                update(noticeinfo);
                returnRes = noticeinfo.getId();
            }
        } catch (Exception e) {
            log.error(" ��������֪ͨ���ı������ʧ��ԭ��\r\n" + e);
            throw new LoanException("Gen_E001", e);
        }
        return returnRes;
    }

    /**
     * �����������ʵ���֪ͨ����ȡ������
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
             * �������������տ�֪ͨ����״̬�뵣���շ�֪ͨ��״̬���о��� �����շ�֪ͨ��״̬
             * </p>
             */
            noticeinfo.setNstatusID(AssureChargeNoticeStatus.CANCEL);
            update(noticeinfo);
        } catch (Exception e) {
            log.error(" ���������տ�֪ͨ����ȡ������ʧ��ԭ��\r\n" + e);
            throw new LoanException("Gen_E001", e);
        }
    }

    /**
     * �����������ʵ���֪ͨ������˲���
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
        // ������Ӧ��������
        // ģ������
        long lModuleID = LOANConstant.ModuleType.LOAN;
        info.setModuleID(lModuleID);
        // ��������
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
                // ���ApprovalID
                lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID,
                        lActionID, info.getOfficeID(), info.getCurrencyID());
                info.setApprovalID(lApprovalID);
            } catch (Exception e1) {
                log.error("���ApprovalIDʧ��ԭ����:\r\n" + e1);
                throw new LoanDAOException(" ���ApprovalIDʧ�� ", e1);
            }
            // �����������
            switch ((int) info.getCheckActionID()) {
                case (int) LOANConstant.Actions.REJECT: {
                    // �ܾ�
                    // �������״̬
                    lStatusID = RecordStatus.VALID;
                    // ������������
                    lResultID = ApprovalDecision.REFUSE;
                    break;
                }
                case (int) LOANConstant.Actions.CHECK: {
                    // ����
                    lStatusID = RecordStatus.VALID;
                    lResultID = ApprovalDecision.PASS;
                    break;
                }
                case (int) LOANConstant.Actions.CHECKOVER: {
                    // ����&&���
                    lStatusID = RecordStatus.VALID;
                    lResultID = ApprovalDecision.FINISH;
                    // ������ɺ���Ҫ���Ĳ���
                    break;
                }
                case (int) LOANConstant.Actions.RETURN: {
                    // �޸�
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
                // ���֪ͨ��
                check(info);
                try {
                    appbiz.saveApprovalTracing(info);
                } catch (Exception e) {
                    log.error(" ������������տ�֪ͨ��ʱ���� :\r\n" + e);
                    throw new LoanDAOException(" ������������տ�֪ͨ��ʱ���� ", e);
                }
            }
        }
    }

    /**
     * �����������ʵ���֪ͨ���Ķ�ʲ�ѯ����
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
        // ������Ӧ��������
        // ģ������
        long lModuleID = ModuleType.LOAN;
        // ҵ������
        long lLoanTypeID = LOANConstant.LoanType.RZZL;
        // ��������
        long lActionID = ApprovalAction.LEASEHOLDPAY_NOTICE;

        LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
        ApprovalDelegation appBiz = new ApprovalDelegation();

        // �õ�������ID
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

        // ��ѯ����������˵�ʵ���û�����ʵ�򴫸���������ˣ���
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
            log.error("������������������¼����ʧ��ԭ��\r\n" + e2);
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
        Timestamp startDate = paynoticeinfo.getQueryStartDate(); // ��������
        Timestamp endDate = paynoticeinfo.getQueryEndDate(); // ��������
        long statusID = paynoticeinfo.getNstatusID(); // ״̬
        long currencyID = paynoticeinfo.getCurrencyId(); // ����
        long officeID = paynoticeinfo.getOfficeId(); // ���´�

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
            // �����¼����
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
            // ////////////////////��ѯ����////////////////////////////

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
            // //////////////////////////������///////////////////////
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
                log.error("������ѯ֪ͨ�������������� : " + e.getMessage());
                throw new LoanDAOException("������ѯ֪ͨ��������������", e);
            } catch (SQLException e) {
                log.error("������ѯ֪ͨ�������������� : " + e.getMessage());
                throw new LoanDAOException("������ѯ֪ͨ��������������", e);
            }
            //������ҳ��
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
            // ////////////////��������Ľ����////////////////////////////////////
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
                    assureChargeNoticeInfo.setId(rs1.getLong("ID")); // ���
                    assureChargeNoticeInfo.setNcontractID(rs1
                            .getLong("NCONTRACTID")); // CONTRACTID
                    assureChargeNoticeInfo.setNofficeID(rs1.getLong("NOFFICEID")); // ���´�
                    assureChargeNoticeInfo.setNcurrencyID(rs1
                            .getLong("NCURRENCYID")); // ����

                    ContractInfo cinfo = c_dao.findByID(rs1.getLong("NCONTRACTID"));
                    ContractAmountInfo ainfor = cinfo.getAInfo();
                    assureChargeNoticeInfo.setBalanceAmount(ainfor.getBalanceAmount()); // �������
                    assureChargeNoticeInfo.setContractCode(cinfo.getContractCode());
                    assureChargeNoticeInfo.setClientName(cinfo.getBorrowClientName());
                    assureChargeNoticeInfo.setBeforRate(cinfo.getLateRateString());
                    assureChargeNoticeInfo.setIntervalNum(cinfo.getIntervalNum());

                    assureChargeNoticeInfo.setSreason(rs1
                            .getString("SREASON")); // ԭ��
                    assureChargeNoticeInfo.setDtinputDate(rs1
                            .getTimestamp("DTVALIDATE")); // ���ʵ������ڿ�ʼ����
                  assureChargeNoticeInfo.setNnextCheckLevel(rs1
                            .getLong("NNEXTCHECKLEVEL")); // ��һ����˼���
                    assureChargeNoticeInfo.setNinputUserID(rs1
                            .getLong("NINPUTUSERID")); // ¼����
                    assureChargeNoticeInfo.setDtinputDate(rs1
                            .getTimestamp("DTINPUTDATE")); // ¼��ʱ��
                    assureChargeNoticeInfo.setNstatusID(rs1.getLong("NSTATUSID")); // ״̬
                    // ����û�е��ֶ�
                    assureChargeNoticeInfo.setRecordCount(recordCount); // ��¼��
                    assureChargeNoticeInfo.setPageCount(pageCount); // ҳ��
                    // �¼ӵ��ֶ�2006��3��20
                    assureChargeNoticeInfo.setMrate(rs1.getDouble("MRATE"));
                    v.add(assureChargeNoticeInfo);
                }
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException("������ѯ�������������", e);
            } catch (SQLException e) {
                throw new LoanDAOException("������ѯ�������������", e);
            }
            try {
                finalizeDAO();
            } catch (ITreasuryDAOException e) {
                log.error("������ѯ֪ͨ�������������� : " + e.getMessage());
                throw new LoanDAOException(e);
            }
        } catch (Exception e) {
            throw new LoanDAOException("������ѯ֪ͨ��������������", e);
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
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) { // �ܾ�
            aInfo.setId(lApprovalContentID);
            tempInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.REFUSE);
            try {
                update(aInfo);
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException(e);
            }
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) { // ����
        	aInfo.setId(lApprovalContentID);
            aInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
            aInfo.setNnextCheckUserID(lNextUserID);
            ApprovalDelegation appbiz = new ApprovalDelegation();
            try {
                aInfo.setNnextCheckLevel(appbiz.findApprovalUserLevel(
                        lApprovalID, lNextUserID));
            } catch (Exception e) {
                throw new LoanDAOException("������һ����˼���ʱ����", e);
            }
            try {
                update(aInfo);
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException(" �������³��� ", e);
            }
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) { // ����&&���
            aInfo.setId(lApprovalContentID);          
            aInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.CHECK);
            aInfo.setNnextCheckUserID(lNextUserID);
            log.info("999999999999999999999999=="+lNextUserID);
            try {
                update(aInfo);
            } catch (ITreasuryDAOException e) {
                throw new LoanDAOException(e);
            }
            // ������ɺ���Ҫ���Ĳ���
            log.info("999999999999999999999999lApprovalContentID:"+lApprovalContentID);
            doAfterCheckOver(lApprovalContentID);
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<:");
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) { // �޸�
            aInfo.setId(lApprovalContentID);
            aInfo.setNstatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
            aInfo.setNnextCheckUserID(ATInfo.getInputUserID());
            // ����һ�����Ϊ1
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
   	   
   	    long nplanversion=-1;//�汾��
   	    
   	    LeaseholdInterestAdjustInfo lao=new LeaseholdInterestAdjustInfo();
   	    PlanVersionInfo pvi=new PlanVersionInfo();
   	    
   	    double dTotal = 0;//����
   	    double dRecognizanceAmount = 0;//��֤��
   	    double dRate = 0;//����
   	    double dInterestAmount = 0;//��Ϣ
   	    long lInterestCountType = -1;//��Ϣ���㷽ʽ
   	    long nPayType = -1;//��𳥻���ʽ
   	    long lRepayNum = -1;//����Ƶ��
   	    long lIntervalNum = -1;//����
   	    long lNewPlanID = -1;
   	    long planCount = -1;
   	    Timestamp dtInput=null; //¼������ 
   	    long n=0;
   	    long   planDetailID=-1;
   	    
    	try
    	{
    		conn=Database.getConnection();
    		//���ݵ���֪ͨID����µ����ʺͺ�ͬID�Լ��ٹ�����ͬ��ȡ�ú�ͬ�������ʡ���Ϣ���㷽ʽ�����ʽ
    		strSQL=" select lr.mrate,lr.sreason,lr.ncontractid,lc.* from loan_leaseholdrateadjust lr,loan_contractform lc where lr.ncontractid=lc.id and lr.id="+lNoticeID;
    		
    		log.info("����֪ͨ��IDbefor��"+strSQL);
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
            log.info("����֪ͨ��ID��ȡsql��䣺"+strSQL);
            cleanup(rs);
            cleanup(ps);            
            
            
            /* ���Ȼ�øñʺ�ͬ�����İ汾��ID */
            strSQL=" select max(llc.nplanversion) nplanversion from loan_loancontractplan llc  where llc.ncontractid="+lao.getNcontractID();
            ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if(rs.next())
            {
            	nplanversion=rs.getLong("nplanversion");
            }
            log.info("��ͬ�����İ汾��sql��"+strSQL);
            log.info("��ͬ�����İ汾��ID��"+nplanversion);
            cleanup(rs);
            cleanup(ps);
            
            //���ݺ�ͬID��÷ſ�ƻ���¼
            strSQL=" select *  from loan_loancontractplan where ncontractid="+lao.getNcontractID()+" and  NPLANVERSION="+nplanversion;
            //System.out.println("loan_loancontractplan��"+strSQL);
            log.info("loan_loancontractplan��"+strSQL);
            ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            nplanversion=nplanversion+1;//�汾id��1
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
            //����ƻ��汾
            lNewPlanID = this.insertPlanInfo(pvi);
            System.out.println("lNewPlanID:"+lNewPlanID);
            
            //��ȡ���°汾�ļƻ�
            Vector planVector = (Vector) findByLoanContractPlanID(pvi.getID());
            PlanDetailInfo planInfo = null;
            PlanDetailInfo planInfo1 = new PlanDetailInfo();
			if ((planVector != null) && (planVector.size() > 0))
			{
				planCount = planVector.size();
//			�жϻ���Ƶ��
				switch ( (int)nPayType )
				{
					case  (int)LOANConstant.ChargeRatePayType.YEAR  :     //��
                    
                	   lRepayNum = 12;
                	   break;
					case  (int)LOANConstant.ChargeRatePayType.HALFYEAR  :     //����
                    
                    lRepayNum = 6;
                    break;
					case (int)LOANConstant.ChargeRatePayType.QUARTER  :     //��
                    
                    lRepayNum = 3;    
                    break;
					case  (int)LOANConstant.ChargeRatePayType.MONTH :    //��
                    
                	   lRepayNum = 1;    
                	   break;
				}
				dtInput=pvi.getInput();//���ʵ�������
				
	             System.out.println("planCount:"+planCount);
	             //��ʼѭ�����뾭���ʵ�����ĳ����ƻ���
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
										  if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEAMOUNT)//�ȶ�
											{
												planInfo1.setInterestAmount( (dTotal*lao.getMrate()/12/100) * lRepayNum );
												//�ȶ���㹫ʽ��������*����/12/100��*���������ޣ��£�/������
												System.out.print(" �ȶ���Ϣ="+planInfo1.getInterestAmount());
											}
											else if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEPRINCIPAL)//�ȱ�
											{
												double d = planCount;
												planInfo1.setInterestAmount((d-i)/d*dTotal * (lao.getMrate()/12/100) * lRepayNum);										
												System.out.print(" �ȱ���Ϣ="+planInfo1.getInterestAmount());
												//�ȱ����㷽ʽ���������ѻ�����*������/12/100��* ���������ޣ��£�/������
											}
								    }
						}
						planInfo1.setRecognizanceAmount(planInfo.getRecognizanceAmount());
                        //������ͬ���޳��ƻ���Ϣ��
						insertPlanDetail(planInfo1);
					} //END FOR IF
					
					
				} // END FOR planVector
				
			} //END FOR
            cleanup(conn);
            
            
            
    	}
    	catch (Exception e) {
    		throw new LoanDAOException("���޳��������ɼƻ�����", e);
    		
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
	         
	         //��õ�ǰ���³����ƻ������ID
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
	         /* ִ�в������*/
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
            /*���Ȼ�ø�loanID��Ӧ�ļƻ��汾*/
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
        //�����������޳����ƻ���
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
