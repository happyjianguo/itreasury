/*
 * Created on 2005-6-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.credit.dao;

import java.sql.*;

import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.credit.dataentity.*;
import com.iss.itreasury.loan.loanapply.dataentity.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import java.util.*;

import com.iss.itreasury.settlement.util.*;

/**
 * @author yfan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditCheckReportDAO extends LoanDAO {
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

    public CreditCheckReportDAO() {
        super("loan_creditCheckReport");
    }

    public CreditCheckReportDAO(Connection conn) {
        super("loan_creditCheckReport", conn);
    }

    public long typeTrans(long creditProductType) throws Exception {
        long loanType = -1;
        try {
            switch ((int) creditProductType) {
            case (int) LOANConstant.CreditProduct.ZY:
                loanType = LOANConstant.LoanType.ZY;
                break;
            case (int) LOANConstant.CreditProduct.SP:
                loanType = LOANConstant.LoanType.TX;
                break;
            case (int) LOANConstant.CreditProduct.BH:
                loanType = LOANConstant.LoanType.DB;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loanType;
    }
    public long untypeTrans(long loanType) throws Exception {
        long productType = -1;
        try {
            switch ((int) loanType) {
            case (int) LOANConstant.LoanType.ZY:
            
                productType = LOANConstant.CreditProduct.ZY;
                break;
            case (int) LOANConstant.LoanType.TX:
            
                productType = LOANConstant.CreditProduct.SP;
                break;
            case (int) LOANConstant.LoanType.DB:
            
                productType = LOANConstant.CreditProduct.BH;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productType;
    }
    public Collection findByMultiOption(CheckReportQueryInfo qInfo)
            throws ITreasuryDAOException {
        Collection c = null;
        ArrayList v = null;
        String strSQL = "";

        long queryPurpose = -1;

        try {
            /*-----------------init DAO --------------------*/
            try {
                initDAO();
            } catch (ITreasuryDAOException e) {
                throw e;
            }
            /*----------------end of init------------------*/

            try {
            	//modified by mzh_fu 2007/03/22 解决查询时未区分办事处的问题
            	/**
               strSQL = "select * from LOAN_CREDITCHECKREPORT" + " where 1=1";
                     
                if (qInfo.getStartClientID() > 0) { //客户ID开始
                  strSQL += " and ClientID>=" + qInfo.getStartClientID();
                }
                if (qInfo.getEndClientID() > 0) { //客户ID结束
                  strSQL += " and clientID<=" + qInfo.getEndClientID();
                }
                if (qInfo.getClientID() > 0) {
                  strSQL += " and clientID=" + qInfo.getClientID();
                }
                if (qInfo.getStartAmount() > 0) { //申请金额开始
                  strSQL += " and amount>=" + qInfo.getStartAmount();
                }
                if (qInfo.getEndAmount() > 0) { //申请金额结束
                  strSQL += " and amount<=" + qInfo.getEndAmount();
                }
                if(!qInfo.getStartReportCode().equals(""))
                {
                    strSQL += " and code>="+qInfo.getStartReportCode();
                }
                if(!qInfo.getEndReportCode().equals(""))
                {
                    strSQL += " and code<="+qInfo.getEndReportCode();
                }
               

                ///////////////////////////////end//////////////////////////////////////////////////

                //录入日期开始
                if (qInfo.getStartInputDate() != null &&
                    qInfo.getEndInputDate().length() > 0) {
                  strSQL += " and INPUTDATETIME>=to_date('" + qInfo.getStartInputDate();
                  strSQL += "','yyyy-mm-dd')";
                }
                //录入日期结束
                if (qInfo.getEndInputDate() != null &&
                    qInfo.getEndInputDate().length() > 0) {
                  strSQL += " and INPUTDATETIME<=to_date('" + qInfo.getEndInputDate();
                  strSQL += "','yyyy-mm-dd')";
                }
                if (qInfo.getStatusID() > 0) {
                  strSQL += " and STATUSID=" + qInfo.getStatusID();
                }

                ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
                int nIndex = 0;
                String orderParamString = qInfo.getOrderParamString();

                nIndex = orderParamString.indexOf(".");

                if (nIndex > 0) {
                      //System.out.println("###########="+orderParamString.substring(0,nIndex).toLowerCase());
                  if (orderParamString.substring(0,nIndex).toUpperCase().equals("LOAN_CREDITCHECKREPORT")) {
                    strSQL += " order by " + orderParamString.substring(nIndex + 1);
                  
                  }
                }
                else {
                  strSQL += " order by ID";
                }

                if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
                  strSQL += " desc";
                }
*/
            	
            	//added by mzh_fu 2007/03/22 将此信息与客户关联以区分办事处
                strSQL = "select lc.* from LOAN_CREDITCHECKREPORT lc,CLIENT_CLIENTINFO cc  " +
                		" where cc.id=lc.Clientid and cc.OFFICEID = "+qInfo.getOfficeId();
                
                if (qInfo.getStartClientID() > 0) { //客户ID开始
                  strSQL += " and lc.ClientID>=" + qInfo.getStartClientID();
                }
                if (qInfo.getEndClientID() > 0) { //客户ID结束
                  strSQL += " and lc.clientID<=" + qInfo.getEndClientID();
                }
                if (qInfo.getClientID() > 0) {
                  strSQL += " and lc.clientID=" + qInfo.getClientID();
                }
                if (qInfo.getStartAmount() > 0) { //申请金额开始
                  strSQL += " and lc.amount>=" + qInfo.getStartAmount();
                }
                if (qInfo.getEndAmount() > 0) { //申请金额结束
                  strSQL += " and lc.amount<=" + qInfo.getEndAmount();
                }
                if(!qInfo.getStartReportCode().equals(""))
                {
                    strSQL += " and lc.code>="+qInfo.getStartReportCode();
                }
                if(!qInfo.getEndReportCode().equals(""))
                {
                    strSQL += " and lc.code<="+qInfo.getEndReportCode();
                }
               

                ///////////////////////////////end//////////////////////////////////////////////////

                //录入日期开始
                if (qInfo.getStartInputDate() != null &&
                    qInfo.getEndInputDate().length() > 0) {
                  strSQL += " and lc.INPUTDATETIME>=to_date('" + qInfo.getStartInputDate();
                  strSQL += "','yyyy-mm-dd')";
                }
                //录入日期结束
                if (qInfo.getEndInputDate() != null &&
                    qInfo.getEndInputDate().length() > 0) {
                  strSQL += " and lc.INPUTDATETIME<=to_date('" + qInfo.getEndInputDate();
                  strSQL += "','yyyy-mm-dd')";
                }
                if (qInfo.getStatusID() > 0) {
                  strSQL += " and lc.STATUSID=" + qInfo.getStatusID();
                }

                ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
                int nIndex = 0;
                String orderParamString = qInfo.getOrderParamString();

                nIndex = orderParamString.indexOf(".");

                if (nIndex > 0) {
                      //System.out.println("###########="+orderParamString.substring(0,nIndex).toLowerCase());
                  if (orderParamString.substring(0,nIndex).toUpperCase().equals("LOAN_CREDITCHECKREPORT")) {
                    strSQL += " order by lc." + orderParamString.substring(nIndex + 1);
                  
                  }
                }
                else {
                  strSQL += " order by lc.ID";
                }

                if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
                  strSQL += " desc";
                }
            	
                
                System.out.println("sql= \n" + strSQL);
                prepareStatement(strSQL);
                executeQuery();
                c = getDataEntitiesFromResultSet(CreditCheckReportInfo.class);

                v = (ArrayList) c;
               
               

            } catch (Exception e) {
                throw new ITreasuryDAOException("查询授信检查报告出错", e);
            }

            /*----------------finalize Dao-----------------*/
            try {
                finalizeDAO();
            } catch (ITreasuryDAOException e) {
                throw e;
            }
            /*----------------end of finalize---------------*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            finalizeDAO();
        }
        return (v.size() > 0 ? v : null);
    }

    public Collection findByDateOption(CreditLimitInfo Info)
            throws ITreasuryDAOException {
        Collection c = null;
        String strSQL = "";

        if (Info.getStartDate() == null || Info.getEndDate() == null) {
            return c;
        }

        try {
            /*-----------------init DAO --------------------*/
            try {
                initDAO();
            } catch (ITreasuryDAOException e) {
                throw e;
            }
            /*----------------end of init------------------*/

            try {
                strSQL = "select * from loan_creditLimit ";
                strSQL += " where clientID =" + Info.getClientID();
                strSQL += " and statusID > 0";
                strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
                strSQL += " and statusID !="
                        + LOANConstant.CreditStatus.OVERTIME;

                strSQL += " and ((STARTDATE<=to_date('"
                        + Info.getStartDate().toString().substring(0, 10);
                strSQL += "','yyyy-mm-dd')";
                strSQL += " and ENDDATE>=to_date('"
                        + Info.getEndDate().toString().substring(0, 10);
                strSQL += "','yyyy-mm-dd'))";

                strSQL += " or ( STARTDATE>=to_date('"
                        + Info.getStartDate().toString().substring(0, 10);
                strSQL += "','yyyy-mm-dd')";
                strSQL += " and STARTDATE<=to_date('"
                        + Info.getEndDate().toString().substring(0, 10);
                strSQL += "','yyyy-mm-dd'))";

                strSQL += " or ( ENDDATE>=to_date('"
                        + Info.getStartDate().toString().substring(0, 10);
                strSQL += "','yyyy-mm-dd')";
                strSQL += " and ENDDATE<=to_date('"
                        + Info.getEndDate().toString().substring(0, 10);
                strSQL += "','yyyy-mm-dd')))";

                if (Info.getId() > 0) {
                    strSQL += " and id !=" + Info.getId();
                }

                log4j.info("sql= \n" + strSQL);
                prepareStatement(strSQL);
                executeQuery();
                c = getDataEntitiesFromResultSet(CreditLimitInfo.class);

            } catch (Exception e) {
                throw new ITreasuryDAOException("查询授信设置出错", e);
            }

            /*----------------finalize Dao-----------------*/
            try {
                finalizeDAO();
            } catch (ITreasuryDAOException e) {
                throw e;
            }
            /*----------------end of finalize---------------*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            finalizeDAO();
        }
        return (c.size() > 0 ? c : null);
    }

    public double findApplyAmountByClient(long clientID) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;
        double zyAmount = 0;
        double spAmount = 0;

        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate();
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);

            conn = Database.getConnection();
            strSQL = "select sum(a.mLoanAmount) from loan_loanForm a,loan_contractForm b"
                    + " where a.id=b.nloanID(+)"
                    + " and ((a.nStatusID="
                    + LOANConstant.LoanStatus.CHECK
                    + " and b.nStatusID >="
                    + LOANConstant.ContractStatus.SAVE
                    + " and b.nStatusID <="
                    + LOANConstant.ContractStatus.CHECK
                    + " )or a.nstatusID ="
                    + LOANConstant.LoanStatus.SUBMIT
                    + " )and a.nBorrowClientID="
                    + clientID
                    + " and a.nTypeID in ("
                    + LOANConstant.LoanType.ZY
                    + ","
                    + LOANConstant.LoanType.ZGXE
                    + ","
                    + LOANConstant.LoanType.YT
                    + ","
                    + LOANConstant.LoanType.WT
                    + ")";
            log4j.print(strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                zyAmount = rs.getDouble(1) * ddRate;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }

            strSQL = "select sum(a.mLoanAmount) from loan_loanForm a,loan_contractForm b"
                    + " where a.id=b.nloanID(+)"
                    + " and ((a.nStatusID="
                    + LOANConstant.LoanStatus.CHECK
                    + " and b.nStatusID >="
                    + LOANConstant.ContractStatus.SAVE
                    + " and b.nStatusID <="
                    + LOANConstant.ContractStatus.CHECK
                    + " )or a.nstatusID ="
                    + LOANConstant.LoanStatus.SUBMIT
                    + " )and a.nBorrowClientID="
                    + clientID
                    + " and a.nTypeID in ("
                    + LOANConstant.LoanType.TX
                    + ","
                    + LOANConstant.LoanType.ZTX + ")";
            log4j.print(strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                spAmount = rs.getDouble(1) * ttRate;
            }

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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
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
        return (zyAmount + spAmount);
    }

    public double findUseAmountByClient(long clientID) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;
        double zyAmount = 0;
        double spAmount = 0;
        double repayAmount = 0;
        double discountRepayAmount = 0;

        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate();
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);

            conn = Database.getConnection();

            repayAmount = this.getRepayAmount(conn, clientID);
            discountRepayAmount = this.getDiscountRepayAmount(conn, clientID);
            strSQL = "select sum(mExamineAmount) from loan_contractForm"
                    + " where 1=1" + " and nStatusID>="
                    + LOANConstant.ContractStatus.NOTACTIVE
                    + " and nStatusID<=" + LOANConstant.ContractStatus.BADDEBT
                    + " and nBorrowClientID=" + clientID + " and nTypeID in ("
                    + LOANConstant.LoanType.ZY + ","
                    + LOANConstant.LoanType.ZGXE + ","
                    + LOANConstant.LoanType.YT + "," + LOANConstant.LoanType.WT
                    + ")";
            log4j.print(strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                zyAmount = (rs.getDouble(1) - repayAmount) * ddRate;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }

            strSQL = "select sum(mExamineAmount) from loan_contractForm"
                    + " where 1=1" + " and nStatusID>="
                    + LOANConstant.ContractStatus.NOTACTIVE
                    + " and nStatusID<=" + LOANConstant.ContractStatus.BADDEBT
                    + " and nBorrowClientID=" + clientID + " and nTypeID in ("
                    + LOANConstant.LoanType.TX + ","
                    + LOANConstant.LoanType.ZTX + ")";
            log4j.print(strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                spAmount = (rs.getDouble(1) - discountRepayAmount) * ttRate;
            }

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
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
        return (zyAmount + spAmount);
    }

    public CreditLimitInfo findCreditLimitByClientID(long clientID)
            throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditLimitInfo info = null;
        try {
            conn = Database.getConnection();
            strSQL = " select * from Loan_CreditLimit " + " where 1 = 1 "
                    + " and StatusID in ( " + LOANConstant.CreditStatus.ACTIVE
                    + " , " + LOANConstant.CreditStatus.FREEZE + " ) "
                    + " and clientID = " + clientID;

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                info = new CreditLimitInfo();
                info.setId(rs.getLong("ID"));
                info.setClientID(rs.getLong("clientID"));
                info.setCreditAmount(rs.getDouble("creditAmount"));
                info.setStartDate(rs.getTimestamp("startDate"));
                info.setEndDate(rs.getTimestamp("endDate"));
                info.setStatusID(rs.getLong("statusID"));
                info.setInputDate(rs.getTimestamp("inputDate"));
                info.setInputUserID(rs.getLong("inputUserID"));
                info
                        .setApplyAmount(findApplyAmountByClient(info
                                .getClientID()));
                info.setUsedAmount(findUseAmountByClient(info.getClientID()));
                info.setCanUseAmount(info.getCreditAmount()
                        - DataFormat.formatDouble(info.getApplyAmount(), 2)
                        - DataFormat.formatDouble(info.getUsedAmount(), 2));
            }
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
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
        return info;
    }

    public Collection findApplyDetail(long clientID) throws Exception {
        Vector v = new Vector();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;

        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate();
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);

            conn = Database.getConnection();
            strSQL = "select a.*,b.nstatusID as contractStatusID from loan_loanForm a,loan_contractForm b"
                    + " where a.id=b.nloanID(+)"
                    + " and ((a.nStatusID="
                    + LOANConstant.LoanStatus.CHECK
                    + " and b.nStatusID >="
                    + LOANConstant.ContractStatus.SAVE
                    + " and b.nStatusID <="
                    + LOANConstant.ContractStatus.CHECK
                    + " )or a.nstatusID ="
                    + LOANConstant.LoanStatus.SUBMIT
                    + " )and a.nBorrowClientID=" + clientID;

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                LoanApplyInfo applyInfo = new LoanApplyInfo();
                applyInfo.setID(rs.getLong("ID"));
                applyInfo.setTypeID(rs.getLong("ntypeid"));
                applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
                applyInfo.setOfficeID(rs.getLong("nofficeid"));
                applyInfo.setApplyCode(rs.getString("sapplycode"));
                applyInfo.setConsignClientID(rs.getLong("nconsignclientid"));
                applyInfo.setBorrowClientID(rs.getLong("nborrowclientid"));
                applyInfo.setInputUserID(rs.getLong("ninputuserid"));
                applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
                applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
                applyInfo.setLoanReason(rs.getString("sloanreason"));
                applyInfo.setLoanPurpose(rs.getString("sloanpurpose"));
                applyInfo.setOther(rs.getString("sother"));
                applyInfo.setBankInterestID(rs.getLong("nbankinterestid"));
                applyInfo.setChargeRate(rs.getDouble("mchargerate"));
                applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
                applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
                applyInfo.setEndDate(rs.getTimestamp("dtenddate"));
                applyInfo.setClientInfo(rs.getString("sclientinfo"));
                applyInfo.setSelfAmount(rs.getDouble("mselfamount"));
                applyInfo.setInterestRate(rs.getDouble("minterestrate"));
                applyInfo.setIsCircle(rs.getLong("niscircle"));
                applyInfo.setIsSaleBuy(rs.getLong("nissalebuy"));
                applyInfo.setIsTechnical(rs.getLong("nistechnical"));
                applyInfo.setIsCredit(rs.getLong("niscredit"));
                applyInfo.setIsAssure(rs.getLong("nisassure"));
                applyInfo.setIsImpawn(rs.getLong("nisimpawn"));
                applyInfo.setIsPledge(rs.getLong("nispledge"));
                applyInfo.setInterestTypeID(rs.getLong("nInterestTypeID"));
                applyInfo.setExamineAmount(rs.getDouble("mExamineAmount"));
                applyInfo.setStatusID(rs.getLong("nStatusID"));
                applyInfo.setNextCheckUserID(rs.getLong("nNextCheckUserID"));
                applyInfo.setIsCanModify(rs.getLong("IsCanModify"));
                applyInfo.setChargeRateTypeID(rs.getLong("nChargeRateTypeID"));
                applyInfo.setRiskLevel(rs.getLong("nRiskLevel"));
                applyInfo.setTypeID1(rs.getLong("nTypeID1"));
                applyInfo.setTypeID2(rs.getLong("nTypeID2"));
                applyInfo.setTypeID3(rs.getLong("nTypeID3"));
                applyInfo.setNBankAcceptPO(rs.getLong("nBankAcceptPO"));
                applyInfo.setNBizAcceptPO(rs.getLong("nBizAcceptPO"));
                applyInfo.setCheckAmount(rs.getDouble("mCheckAmount"));
                applyInfo.setDiscountRate(rs.getDouble("mDiscountRate"));
                applyInfo.setDiscountDate(rs.getTimestamp("dtDiscountDate"));
                applyInfo.setAdjustRate(rs.getDouble("mAdjustRate"));
                applyInfo.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
                if (applyInfo.getTypeID() == LOANConstant.LoanType.TX
                        || applyInfo.getTypeID() == LOANConstant.LoanType.ZTX) {
                    applyInfo.setUseCreditAmount(applyInfo.getLoanAmount()
                            * ttRate);
                } else {
                    applyInfo.setUseCreditAmount(applyInfo.getLoanAmount()
                            * ddRate);
                }
                v.add(applyInfo);
            }

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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
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

    public Collection findUseDetail(long clientID) throws Exception {
        Vector v = new Vector();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;

        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate();
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);

            conn = Database.getConnection();
            strSQL = "select * from loan_contractForm" + " where 1=1"
                    + " and nStatusID>="
                    + LOANConstant.ContractStatus.NOTACTIVE
                    + " and nStatusID<=" + LOANConstant.ContractStatus.BADDEBT
                    + " and nBorrowClientID=" + clientID;

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                ContractInfo info = new ContractInfo();
                info.setContractID(rs.getLong("ID")); //合同的ID
                info.setLoanID(rs.getLong("nLoanID")); //贷款ID
                info.setLoanTypeID(rs.getLong("nTypeID"));
                info.setContractCode(rs.getString("sContractCode")); //合同编号
                info.setLoanAmount(rs.getDouble("mLoanAmount")); //借款金额
                info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
                info.setCheckAmount(rs.getDouble("mCheckAmount")); //汇总贴现核定金额
                info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
                info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款起始日期
                info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款到期日期
                info.setIntervalNum(rs.getLong("nIntervalNum")); //期限
                info.setInputDate(rs.getTimestamp("dtInputDate")); //合同录入日期
                info.setStatusID(rs.getLong("nStatusID")); //合同状态
                if (info.getLoanTypeID() == LOANConstant.LoanType.TX
                        || info.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getDiscountRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ttRate);
                } else {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ddRate);
                }
                v.addElement(info);
            }
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
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

    public double getRepayAmount(Connection con, long clientID)
            throws Exception {
        double result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        try {
            strSQL = "select sum(mAmount)"
                    + " from ( "
                    + " select mAmount "
                    + " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
                    + SETTConstant.TransactionStatus.CHECK
                    + " and a.nClientID=" + clientID
                    + " and b.id=a.nLoanContractID"
                    + " and b.nTypeID in (1,2,6,7,8,10)" + " ) ";

            log4j.print("getRepayAmount For:" + clientID + strSQL);
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }

        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return result;

    }

    public double getRepayAmountByContract(Connection con, long contractID)
            throws Exception {
        double result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        try {
            strSQL = "select sum(mAmount)"
                    + " from ( "
                    + " select mAmount "
                    + " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
                    + SETTConstant.TransactionStatus.CHECK
                    + " and b.id=a.nLoanContractID" + " and a.nLoanContractID="
                    + contractID + " and b.nTypeID in (1,2,6,7,8,10)" + " ) ";

            log4j.print("getRepayAmount For:" + contractID + strSQL);
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }

        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return result;

    }

    public double getGrantAmount(Connection con, long clientID)
            throws Exception {
        double result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        try {
            strSQL = "select sum(mAmount)"
                    + " from ( "
                    + " select a.mAmount "
                    + " from sett_transgrantloan a,loan_ContractForm b where a.nStatusID>="
                    + SETTConstant.TransactionStatus.CHECK
                    + " and b.id=a.nLoanContractID"
                    + " and b.nTypeID in (1,2,6,7,8,10)"
                    + " and b.nBorrowClientID=" + clientID + " ) ";

            log4j.print("getGrantAmount For:" + clientID + strSQL);
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return result;
    }

    public double getDiscountRepayAmount(Connection con, long clientID)
            throws Exception {
        double result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        try {
            strSQL = "select sum(mAmount)" + " from ( " + " select mAmount "
                    + " from Sett_Transrepaymentdiscount "
                    + " where nStatusID>="
                    + SETTConstant.TransactionStatus.CHECK + " and nClientID="
                    + clientID + " ) ";

            log4j.print("getRepayAmount For:" + clientID + strSQL);
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }

        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return result;

    }

    public double getDiscountRepayAmountByContract(Connection con,
            long contractID) throws Exception {
        double result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        try {
            strSQL = "select sum(mAmount)" + " from ( " + " select mAmount "
                    + " from Sett_Transrepaymentdiscount "
                    + " where nStatusID>="
                    + SETTConstant.TransactionStatus.CHECK
                    + " and NDISCOUNTCONTRACTID=" + contractID + " ) ";

            log4j.print("getRepayAmount For:" + contractID + strSQL);
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
                //System.out.println("result="+result);
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }

        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return result;

    }

    public double getDiscountGrantAmount(Connection con, long clientID)
            throws Exception {
        double result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        try {
            strSQL = "select sum(mAmount)" + " from ( "
                    + " select a.MDISCOUNTBILLAMOUNT as mAmount"
                    + " from SETT_TRANSGRANTDISCOUNT a,loan_contractForm b "
                    + " where a.nStatusID>="
                    + SETTConstant.TransactionStatus.CHECK
                    + " and b.id= a.nDisCountContractID"
                    + " and b.nBorrowClientID=" + clientID + " ) ";

            log4j.print("getGrantAmount For:" + clientID + strSQL);
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return result;
    }

    /**
     * 客户作为借款人的指定产品类型正在申请的贷款信息,信用比例 author weihuang
     * 
     * @param 借款单位id,产品类型
     *            creditproducttype==-1 3种产品类型都算
     * @return
     */
    public Collection getApplyCreditAmount(long clientID,
            long creditproducttype, Connection conn) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList al = new ArrayList();
        double loanAmount = 0;//累加金额
        double amount = 0;//每一个结果的金额
        double rate = 0;
        double erate = 0;
        //   long type=-1;
        String strSQL = "";
        try {

            strSQL = "select nvl(a.MEXAMINEAMOUNT,a.MLOANAMOUNT)MEXAMINEAMOUNT,a.NTYPEID,a.SAPPLYCODE,a.NSTATUSID,nvl(c.CREDITRATE,100) CREDITRATE " //MEXAMINEAMOUNT
                    + " from loan_loanForm a,loan_contractForm b,LOAN_CREDITCHECKREPORT c"
                    + " where a.id=b.nloanid(+) and  a.NCREDITCHECKREPORTID=c.id(+) ";
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in( "
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);
            strSQL += " and a.NBORROWCLIENTID=" + clientID
                    + " and ((a.nStatusID=" + LOANConstant.LoanStatus.CHECK
                    + " and (b.nStatusID =" + LOANConstant.ContractStatus.SAVE
                    + " or b.nStatusID =" + LOANConstant.ContractStatus.SUBMIT
                    + " or b.nStatusID=" + LOANConstant.ContractStatus.CHECK
                    + " )) or a.nstatusID =" + LOANConstant.LoanStatus.SUBMIT
                    + " )";
            System.out.println("#######%%%@@@指定贷款类型信用贷款总额=" + strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                LoanApplyInfo lainfo = new LoanApplyInfo();
                lainfo.setExamineAmount(rs.getDouble("MEXAMINEAMOUNT"));
                lainfo.setAdjustRate(rs.getDouble("CREDITRATE"));//借用adjustrate存信用比例
                lainfo.setTypeID(rs.getLong("NTYPEID"));
                lainfo.setApplyCode(rs.getString("SAPPLYCODE"));
                lainfo.setStatusID(rs.getLong("NSTATUSID"));
                al.add(lainfo);
            }

        } catch (SQLException sqle) {
            // TODO Auto-generated catch block
            sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;
    }

    /**
     * 客户作为借款人的指定产品类型正在申请的贷款信息,担保比例
     * 
     * @author weihuang
     * @param 借款单位id,产品类型
     * @return
     */
    public Collection getApplyAssureAmount(long clientID,
            long creditproducttype, Connection conn) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList al = new ArrayList();

        String strSQL = null;
        try {

            strSQL = "select nvl(a.MEXAMINEAMOUNT,a.MLOANAMOUNT)MEXAMINEAMOUNT,a.NTYPEID,a.SAPPLYCODE,a.NSTATUSID,a.id,nvl(100-c.CREDITRATE,0) ass "
                    + " from loan_loanform a,loan_contractForm b"
                    + " , LOAN_CREDITCHECKREPORT c"
                    + " where a.id=b.nloanid(+) and  a.NCREDITCHECKREPORTID=c.id(+) ";
            // + " and a.NTYPEID = "
            // + typeTrans(creditproducttype)
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in( "
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);
            strSQL += " and a.NBORROWCLIENTID=" + clientID
                    + " and ((a.nStatusID=" + LOANConstant.LoanStatus.CHECK
                    + " and (b.nStatusID =" + LOANConstant.ContractStatus.SAVE
                    + " or b.nStatusID =" + LOANConstant.ContractStatus.SUBMIT
                    + " or b.nStatusID=" + LOANConstant.ContractStatus.CHECK
                    + " )) or a.nstatusID =" + LOANConstant.LoanStatus.SUBMIT
                    + " )";
            log4j.print("指定贷款类型担保贷款总额=" + strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                LoanApplyInfo lainfo = new LoanApplyInfo();
                lainfo.setExamineAmount(rs.getDouble("MEXAMINEAMOUNT"));
                lainfo.setAdjustRate(rs.getDouble("ASS"));//借用adjustrate存信用比例
                lainfo.setTypeID(rs.getLong("NTYPEID"));
                lainfo.setApplyCode(rs.getString("SAPPLYCODE"));
                lainfo.setStatusID(rs.getLong("NSTATUSID"));
                
                lainfo.setID(rs.getLong("ID"));
                al.add(lainfo);
            }

        } catch (SQLException sqle) {
            // TODO Auto-generated catch block
            sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;
    }

    /**
     * 得到客户作为借款人指定产品类型的客户合同信息,还款金额,信用比例
     * 
     * @author
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public Collection getBalanceCreditAmountAsApply(long clientid,
            long creditproducttype, Connection conn) throws Exception {
        ArrayList al = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;
        double bhRate = 0;
        double creditrate = 0;
        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate();
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.BH);
            if (cpInfo != null) {
                bhRate = cpInfo.getEngrossRate();
            }
            Log.print("保函授信产品占用额度比例：" + bhRate);

            strSQL = "select a.*,nvl(b.CREDITRATE,100) creditrate from loan_contractForm a,LOAN_CREDITCHECKREPORT b"
                    + " where 1=1"
                    + " and a.NCREDITCHECKREPORTID=b.id(+)"
                    + " and nStatusID>="
                    + LOANConstant.ContractStatus.NOTACTIVE
                    + " and nStatusID<="
                    + LOANConstant.ContractStatus.BADDEBT
                    + " and nBorrowClientID=" + clientid;
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in( "
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                ContractInfo info = new ContractInfo();
                info.setContractID(rs.getLong("ID")); //合同的ID
                info.setLoanID(rs.getLong("nLoanID")); //贷款ID
                info.setLoanTypeID(rs.getLong("nTypeID"));
                info.setContractCode(rs.getString("sContractCode")); //合同编号
                info.setLoanAmount(rs.getDouble("mLoanAmount")); //借款金额
                info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
                info.setCheckAmount(rs.getDouble("mCheckAmount")); //汇总贴现核定金额
                info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
                info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款起始日期
                info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款到期日期
                info.setIntervalNum(rs.getLong("nIntervalNum")); //期限
                info.setInputDate(rs.getTimestamp("dtInputDate")); //合同录入日期
                info.setStatusID(rs.getLong("nStatusID")); //合同状态

                creditrate = rs.getDouble("creditrate") / 100;
                info.setCreditRate(creditrate);
                if (info.getLoanTypeID() == LOANConstant.LoanType.TX
                        || info.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getDiscountRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ttRate * creditrate);
                    //            System.out.println("exa1="+info.getExamineAmount());
                    //            System.out.println("Repay1="+this.getDiscountRepayAmountByContract(conn,
                    // info
                    //                            .getContractID()));
                    //              System.out.println("conid="+info.getContractID());

                } else if (info.getLoanTypeID() == LOANConstant.LoanType.ZY) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ddRate * creditrate);
                    //           System.out.println("exa2="+info.getExamineAmount());
                    //            System.out.println("Repay2="+this.getRepayAmountByContract(conn,
                    // info
                    //                            .getContractID()));
                } else if (info.getLoanTypeID() == LOANConstant.LoanType.DB) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * bhRate * creditrate);
                    //             System.out.println("exa3="+info.getExamineAmount());
                    //           System.out.println("Repay3="+this.getRepayAmountByContract(conn,
                    // info
                    //                             .getContractID()));
                }
                al.add(info);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;

    }

    /**
     * 作为借款人得到指定产品类型的客户合同信息,还款金额,担保比例
     * 
     * @author weihuang
     * @param clientID
     * @param
     * @return
     * @throws Exception
     */
    public Collection getBalanceAssureAmountAsApply(long clientid,
            long creditproducttype, Connection conn) throws Exception {
        ArrayList al = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;
        double bhRate = 0;
        double dbrate = 0;
        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate() ;
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.BH);
            if (cpInfo != null) {
                bhRate = cpInfo.getEngrossRate() ;
            }
            Log.print("保函授信产品占用额度比例：" + bhRate);

            strSQL = "select a.*,nvl(100-b.CREDITRATE,0)ass from loan_contractForm a,LOAN_CREDITCHECKREPORT b"
                    + " where 1=1"
                    + " and a.NCREDITCHECKREPORTID=b.id(+)"
                    + " and nStatusID>="
                    + LOANConstant.ContractStatus.NOTACTIVE
                    + " and nStatusID<="
                    + LOANConstant.ContractStatus.BADDEBT
                    + " and nBorrowClientID=" + clientid;
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in("
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                ContractInfo info = new ContractInfo();
                info.setContractID(rs.getLong("ID")); //合同的ID
                info.setLoanID(rs.getLong("nLoanID")); //贷款ID
                info.setLoanTypeID(rs.getLong("nTypeID"));
                info.setContractCode(rs.getString("sContractCode")); //合同编号
                info.setLoanAmount(rs.getDouble("mLoanAmount")); //借款金额
                info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
                info.setCheckAmount(rs.getDouble("mCheckAmount")); //汇总贴现核定金额
                info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
                info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款起始日期
                info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款到期日期
                info.setIntervalNum(rs.getLong("nIntervalNum")); //期限
                info.setInputDate(rs.getTimestamp("dtInputDate")); //合同录入日期
                info.setStatusID(rs.getLong("nStatusID")); //合同状态

                dbrate = rs.getDouble("ass") / 100;
                info.setAssureRate(dbrate);
                if (info.getLoanTypeID() == LOANConstant.LoanType.TX
                        || info.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getDiscountRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ttRate * dbrate);
                    //              System.out.println("exa1="+info.getExamineAmount());
                    //              System.out.println("Repay1="+this.getDiscountRepayAmountByContract(conn,
                    // info
                    //                              .getContractID()));
                    //               System.out.println("conid"+info.getContractID());

                } else if (info.getLoanTypeID() == LOANConstant.LoanType.ZY) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ddRate * dbrate);
                    //               System.out.println("exa2="+info.getExamineAmount());
                    //             System.out.println("Repay2="+this.getRepayAmountByContract(conn,
                    // info
                    //                             .getContractID()));
                } else if (info.getLoanTypeID() == LOANConstant.LoanType.DB) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * bhRate * dbrate);
                    //           System.out.println("exa3="+info.getExamineAmount());
                    //          System.out.println("Repay3="+this.getRepayAmountByContract(conn,
                    // info
                    //                        .getContractID()));
                }
                al.add(info);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;

    }

    /**
     * 客户作为担保人的指定产品类型正在申请的贷款信息,信用比例 author weihuang
     * 
     * @param 借款单位id,产品类型
     * @return
     */
    public Collection getAssureCreditAmount(long clientID,
            long creditproducttype, Connection conn) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList al = new ArrayList();
        //   long type=-1;
        String strSQL = null;
        try {

            strSQL = "select nvl(a.MEXAMINEAMOUNT,a.MLOANAMOUNT)MEXAMINEAMOUNT,a.SAPPLYCODE,a.NSTATUSID,a.NTYPEID,nvl(c.ASSURERATE,100) CREDITRATE"
                    + " from loan_loanForm a,loan_contractForm b,LOAN_CREDITCHECKREPORTDETAIL c"
                    + " where a.id=b.nloanid(+) and  a.NCREDITCHECKREPORTID=c.CREDITCHECKREPORTID(+)";
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in( "
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);

            strSQL +=" and c.ASSURECLIENTID=" + clientID
                    + " and c.CREDITTYPEID=1" //LOAN_CREDITCHECKREPORTDETAIL中的CREDITTYPEID为1授信类型是信用,2为担保
                    + " and ((a.nStatusID=" + LOANConstant.LoanStatus.CHECK
                    + " and (b.nStatusID =" + LOANConstant.ContractStatus.SAVE
                    + " or b.nStatusID =" + LOANConstant.ContractStatus.SUBMIT
                    + " or b.nStatusID=" + LOANConstant.ContractStatus.CHECK
                    + " )) or a.nstatusID =" + LOANConstant.LoanStatus.SUBMIT
                    + " )";
          //  System.out.println(" 指定贷款类型信用贷款总额=" + strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                LoanApplyInfo lainfo = new LoanApplyInfo();
                lainfo.setExamineAmount(rs.getDouble("MEXAMINEAMOUNT"));
                lainfo.setAdjustRate(rs.getDouble("CREDITRATE"));//借用adjustrate存信用比例
                lainfo.setApplyCode(rs.getString("SAPPLYCODE"));
                lainfo.setStatusID(rs.getLong("NSTATUSID"));
                lainfo.setTypeID(rs.getLong("NTYPEID"));
                al.add(lainfo);
            }

        } catch (SQLException sqle) {
            // TODO Auto-generated catch block
            sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;
    }

    /**
     * 客户作为担保人的指定产品类型正在申请的贷款信息,担保比例 author weihuang
     * 
     * @param 担保单位id,产品类型
     * @return
     */
    public Collection getAssureAssureAmount(long clientID,
            long creditproducttype, Connection conn) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList al = new ArrayList();
        //   long type=-1;
        String strSQL = null;
        try {

            strSQL = "select nvl(a.MEXAMINEAMOUNT,a.MLOANAMOUNT)MEXAMINEAMOUNT,a.SAPPLYCODE,a.NSTATUSID,a.NTYPEID,nvl(c.ASSURERATE,0) ASSURERATE"
                    + " from loan_loanForm a,loan_contractForm b,LOAN_CREDITCHECKREPORTDETAIL c"
                    + " where a.id=b.nloanid(+) and  a.NCREDITCHECKREPORTID=c.CREDITCHECKREPORTID(+) ";
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in( "
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);
            strSQL +=" and c.ASSURECLIENTID=" + clientID
                    + " and c.CREDITTYPEID=2" //LOAN_CREDITCHECKREPORTDETAIL中的CREDITTYPEID为1授信类型是信用2为担保
                    + " and ((a.nStatusID=" + LOANConstant.LoanStatus.CHECK
                    + " and (b.nStatusID =" + LOANConstant.ContractStatus.SAVE
                    + " or b.nStatusID =" + LOANConstant.ContractStatus.SUBMIT
                    + " or b.nStatusID=" + LOANConstant.ContractStatus.CHECK
                    + " )) or a.nstatusID =" + LOANConstant.LoanStatus.SUBMIT
                    + " )";
            System.out.println("担保人指定贷款类型正在申请信用贷款总额担保=" + strSQL);
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                LoanApplyInfo lainfo = new LoanApplyInfo();
                lainfo.setExamineAmount(rs.getDouble("MEXAMINEAMOUNT"));
                lainfo.setAdjustRate(rs.getDouble("ASSURERATE"));//借用adjustrate存信用比例
                lainfo.setApplyCode(rs.getString("SAPPLYCODE"));
                lainfo.setStatusID(rs.getLong("NSTATUSID"));
                lainfo.setTypeID(rs.getLong("NTYPEID"));
                al.add(lainfo);
            }

        } catch (SQLException sqle) {
            // TODO Auto-generated catch block
            sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;
    }

    /**
     * 得到客户作为担保人指定产品类型的客户合同信息,还款金额,信用比例
     * 
     * @author weihuang
     * @param clientID
     * @param 产品类型
     * @return
     * @throws Exception
     */
    public Collection getBalancecreditAmountAsAssure(long clientid,
            long creditproducttype, Connection conn) throws Exception {
        ArrayList al = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;
        double bhRate = 0;
        double creditrate = 0;
        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate();
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.BH);
            if (cpInfo != null) {
                bhRate = cpInfo.getEngrossRate();
            }
            Log.print("保函授信产品占用额度比例：" + bhRate);

            strSQL = "select a.*,nvl(b.ASSURERATE,100) creditrate from loan_contractForm a,LOAN_CREDITCHECKREPORTDETAIL b"
                    + " where 1=1"
                    + " and a.NCREDITCHECKREPORTID=b.CREDITCHECKREPORTID(+)"
                    + " and b.CREDITTYPEID=1"
                    + " and nStatusID>="
                    + LOANConstant.ContractStatus.NOTACTIVE
                    + " and nStatusID<="
                    + LOANConstant.ContractStatus.BADDEBT
                    + " and b.ASSURECLIENTID="
                    + clientid;
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in( "
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                ContractInfo info = new ContractInfo();
                info.setContractID(rs.getLong("ID")); //合同的ID
                info.setLoanID(rs.getLong("nLoanID")); //贷款ID
                info.setLoanTypeID(rs.getLong("nTypeID"));
                info.setContractCode(rs.getString("sContractCode")); //合同编号
                info.setLoanAmount(rs.getDouble("mLoanAmount")); //借款金额
                info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
                info.setCheckAmount(rs.getDouble("mCheckAmount")); //汇总贴现核定金额
                info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
                info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款起始日期
                info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款到期日期
                info.setIntervalNum(rs.getLong("nIntervalNum")); //期限
                info.setInputDate(rs.getTimestamp("dtInputDate")); //合同录入日期
                info.setStatusID(rs.getLong("nStatusID")); //合同状态

                creditrate = rs.getDouble("creditrate") / 100;
                info.setCreditRate(creditrate);
                if (info.getLoanTypeID() == LOANConstant.LoanType.TX
                        || info.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getDiscountRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ttRate * creditrate);
                    //           System.out.println("exa1="+info.getExamineAmount());
                    //           System.out.println("Repay1="+this.getDiscountRepayAmountByContract(conn,
                    // info
                    //                            .getContractID()));
                    //            System.out.println("conid="+info.getContractID());

                } else if (info.getLoanTypeID() == LOANConstant.LoanType.ZY) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ddRate * creditrate);
                    //             System.out.println("exa2="+info.getExamineAmount());
                    //              System.out.println("Repay2="+this.getRepayAmountByContract(conn,
                    // info
                    //                              .getContractID()));
                } else if (info.getLoanTypeID() == LOANConstant.LoanType.DB) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * bhRate * creditrate);
                    //            System.out.println("exa3="+info.getExamineAmount());
                    //             System.out.println("Repay3="+this.getRepayAmountByContract(conn,
                    // info
                    //                            .getContractID()));
                }
                al.add(info);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;

    }

    /**
     * 得到客户作为担保人指定产品类型的客户合同信息,还款金额,担保比例
     * 
     * @author weihuang
     * @param clientID
     * @param 产品类型
     * @return
     * @throws Exception
     */
    public Collection getBalanceAssureAmountAsAssure(long clientid,
            long creditproducttype, Connection conn) throws Exception {
        ArrayList al = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        CreditProductInfo cpInfo = null;
        CreditProductDAO cpDao = new CreditProductDAO();
        double ddRate = 0;
        double ttRate = 0;
        double bhRate = 0;
        double assurerate = 0;
        try {
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.ZY);
            if (cpInfo != null) {
                ddRate = cpInfo.getEngrossRate();
            }
            Log.print("自营授信产品占用额度比例：" + ddRate);

            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.SP);
            if (cpInfo != null) {
                ttRate = cpInfo.getEngrossRate();
            }
            Log.print("商票授信产品占用额度比例：" + ttRate);
            cpInfo = cpDao.findByCreditTypeID(LOANConstant.CreditProduct.BH);
            if (cpInfo != null) {
                bhRate = cpInfo.getEngrossRate();
            }
            Log.print("保函授信产品占用额度比例：" + bhRate);

            strSQL = "select a.*,nvl(b.ASSURERATE,0) ASSURERATE from loan_contractForm a,LOAN_CREDITCHECKREPORTDETAIL b"
                    + " where 1=1"
                    + " and a.NCREDITCHECKREPORTID=b.CREDITCHECKREPORTID(+)"
                    + " and b.CREDITTYPEID=2"
                    + " and nStatusID>="
                    + LOANConstant.ContractStatus.NOTACTIVE
                    + " and nStatusID<="
                    + LOANConstant.ContractStatus.BADDEBT
                    + " and b.ASSURECLIENTID="
                    + clientid;
            if (creditproducttype == -1) {
                strSQL += " and a.NTYPEID in( "
                        + typeTrans(LOANConstant.CreditProduct.ZY) + ","
                        + typeTrans(LOANConstant.CreditProduct.SP) + ","
                        + typeTrans(LOANConstant.CreditProduct.BH) + ")";
            } else
                strSQL += " and a.NTYPEID=" + typeTrans(creditproducttype);

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                ContractInfo info = new ContractInfo();
                info.setContractID(rs.getLong("ID")); //合同的ID
                info.setLoanID(rs.getLong("nLoanID")); //贷款ID
                info.setLoanTypeID(rs.getLong("nTypeID"));
                info.setContractCode(rs.getString("sContractCode")); //合同编号
                info.setLoanAmount(rs.getDouble("mLoanAmount")); //借款金额
                info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
                info.setCheckAmount(rs.getDouble("mCheckAmount")); //汇总贴现核定金额
                info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
                info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款起始日期
                info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款到期日期
                info.setIntervalNum(rs.getLong("nIntervalNum")); //期限
                info.setInputDate(rs.getTimestamp("dtInputDate")); //合同录入日期
                info.setStatusID(rs.getLong("nStatusID")); //合同状态

                assurerate = rs.getDouble("ASSURERATE") / 100;
                info.setAssureRate(assurerate);
                if (info.getLoanTypeID() == LOANConstant.LoanType.TX
                        || info.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getDiscountRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ttRate * assurerate);
                    //       System.out.println("exa1="+info.getExamineAmount());
                    //       System.out.println("Repay1="+this.getDiscountRepayAmountByContract(conn,
                    // info
                    //                        .getContractID()));
                    //         System.out.println("conid="+info.getContractID());

                } else if (info.getLoanTypeID() == LOANConstant.LoanType.ZY) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * ddRate * assurerate);
                    //      System.out.println("exa2="+info.getExamineAmount());
                    //         System.out.println("Repay2="+this.getRepayAmountByContract(conn,
                    // info
                    //                     .getContractID()));
                } else if (info.getLoanTypeID() == LOANConstant.LoanType.DB) {
                    info.setUseCreditAmount((info.getExamineAmount() - this
                            .getRepayAmountByContract(conn, info
                                    .getContractID()))
                            * bhRate * assurerate);
                    //         System.out.println("exa3="+info.getExamineAmount());
                    //         System.out.println("Repay3="+this.getRepayAmountByContract(conn,
                    // info
                    //                         .getContractID()));
                }
                al.add(info);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return al;

    }

    /**
     * 检查授信额度状态,提交日期是否符合要求(提交日期必须是在该客户的授信额度有效期内)
     * 
     * @author weihuang
     * @param clientid
     * @return CreditLimitInfo
     */
    public CreditLimitInfo check(long clientid) throws Exception {
        CreditLimitInfo clInfo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = "";
        try {
            conn = Database.getConnection();
            strSQL = "select * from LOAN_CREDITLIMIT " + " where STATUSID="
                    + LOANConstant.CreditStatus.ACTIVE + " and CLIENTID="
                    + clientid
                    + " and to_char(sysdate,'yyyy-mm-dd')>= to_char(startdate,'yyyy-mm-dd')" +
                    		" and to_char(sysdate,'yyyy-mm-dd')<=to_char(enddate,'yyyy-mm-dd')";
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                clInfo = new CreditLimitInfo();
                clInfo.setId(rs.getLong("id"));
                clInfo.setClientID(rs.getLong("CLIENTID"));
                clInfo.setCreditAmount(rs.getDouble("CREDITAMOUNT"));
                clInfo.setStatusID(rs.getLong("STATUSID"));
                clInfo.setAmount(rs.getDouble("AMOUNT"));
                clInfo.setAssureAmount(rs.getDouble("ASSUREAMOUNT"));
                clInfo.setStartDate(rs.getTimestamp("STARTDATE"));
                clInfo.setEndDate(rs.getTimestamp("ENDDATE"));
                clInfo.setRemark(rs.getString("REMARK"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return clInfo;
    }

    /**
     * 保存授信检查报告 return 0:未超过授信额度 1:超过授信额度
     * 
     * @throws SQLException
     * 
     * @author weihuang
     * 
     * TODO To change the template for this generated type comment go to Window -
     * Preferences - Java - Code Style - Code Templates CreditCheckReportInfo
     * reinfo
     */
    public long savecreditcheckreport(CreditCheckReportInfo reinfo)
            throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        long id = -1;
        long clientid = -1;
        long inputuserid = -1;
        long resultid = -1;
        double amount = 0;
        double creditrate = 0;
        long creditproducttype = -1;
        String code = "";
        String strSQL = "";
        String year = "";
        long len = -1;

        try {
            conn = Database.getConnection();

            strSQL = "select nvl(max(id)+1,1) from LOAN_CREDITCHECKREPORT ";
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next())
                id = rs.getLong(1);
            System.out.println("id=" + id);
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            strSQL = " select to_char(sysdate,'yyyy') from dual ";
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();

            if (rs.next()) {
                year = rs.getString(1);

            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            strSQL = "select nvl(length(max(CODE)),8) from LOAN_CREDITCHECKREPORT where "
                    + " code like '" + year + "%'";
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                len = rs.getLong(1);

            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            strSQL = "select nvl(substr(max(CODE),5," + (len - 4)
                    + ")+1,1) from LOAN_CREDITCHECKREPORT "
                    + " where code like '" + year + "%'";

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                long tcode = rs.getLong(1);
                if (tcode < 10) {
                    code = year + "000" + tcode;
                } else if (tcode < 100)
                    code = year + "00" + tcode;
                else if (tcode < 1000)
                    code = year + "0" + tcode;
                else
                    code = year + tcode;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            amount = reinfo.getAmount();
            clientid = reinfo.getClientID();
            creditrate = reinfo.getCreditRate();
            creditproducttype = reinfo.getCreditProductTypeID();
            // LOANConstant.CreditStatus.ACTIVE;
            resultid = reinfo.getResultID();
            strSQL = "insert into LOAN_CREDITCHECKREPORT(id,CLIENTID,AMOUNT,CREDITRATE,STATUSID,"
                    + "INPUTUSERID,INPUTDATETIME,RESULTID,CODE,CREDITPRODUCTTYPEID) values(?,?,?,?,?,?,sysdate,?,?,?)";
            ps = conn.prepareStatement(strSQL);
            ps.setLong(1, id);
            ps.setLong(2, clientid);
            ps.setDouble(3, amount);
            ps.setDouble(4, creditrate);
            ps.setLong(5, Constant.RecordStatus.VALID);
            ps.setLong(6, reinfo.getInputUserID());
            ps.setLong(7, resultid);
            ps.setString(8, code);
            ps.setLong(9, creditproducttype);
            ps.executeUpdate();
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            if (reinfo.getAssureClientID() != null
                    && reinfo.getAssureClientID() != null) {
                for (int i = 0; i < reinfo.getAssureClientID().length; i++) {
                    long assid = -1;
                    strSQL = " select nvl(max(id)+1,1) from LOAN_CREDITCHECKREPORTDETAIL";
                    ps = conn.prepareStatement(strSQL);
                    rs = ps.executeQuery();
                    if (rs.next())
                        assid = rs.getLong(1);
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (ps != null) {
                        ps.close();
                        ps = null;
                    }
                    strSQL = "insert into LOAN_CREDITCHECKREPORTDETAIL(ID,CREDITCHECKREPORTID,ASSURECLIENTID,ASSURERATE,CREDITTYPEID)"
                            + " values(?,?,?,?,?)";
                    ps = conn.prepareStatement(strSQL);
                    ps.setLong(1, assid);
                    ps.setLong(2, id);
                    ps.setLong(3, reinfo.getAssureClientID()[i]);
                    ps.setDouble(4, reinfo.getAssureRate()[i]);
                    ps.setLong(5, reinfo.getCreditTypeID()[i]);
                    ps.executeUpdate();
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (ps != null) {
                        ps.close();
                        ps = null;
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return id;
    }

    public CreditCheckReportInfo findCheckReportByID(long id) throws Exception {
        CreditCheckReportInfo ccrinfo = new CreditCheckReportInfo();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strsql = "";
        long[] assureClientID = null; //担保单位
        double[] assureRate = null; //担保比例
        long[] creditTypeID = null; //授信额度类型（信用，担保）
        String[] assureClientName=null;
        int t=0;
        try {
            conn = Database.getConnection();
            strsql = "select * from LOAN_CREDITCHECKREPORT where id=" + id;
            ps = conn.prepareStatement(strsql);
            rs = ps.executeQuery();
            if (rs.next()) {
                ccrinfo.setCode(rs.getString("CODE"));
                ccrinfo.setId(rs.getLong("id"));
                ccrinfo.setAmount(rs.getDouble("AMOUNT"));
                ccrinfo.setClientID(rs.getLong("CLIENTID"));
                ccrinfo.setClientname(LOANNameRef.getClientNameById(ccrinfo.getClientID()));
                ccrinfo.setCreditRate(rs.getDouble("CREDITRATE"));
                ccrinfo.setStatusID(rs.getLong("STATUSID"));
                ccrinfo.setInputUserID(rs.getLong("INPUTUSERID"));
                ccrinfo.setInputDateTime(rs.getTimestamp("INPUTDATETIME"));
                ccrinfo.setResultID(rs.getLong("RESULTID"));
                ccrinfo.setCreditProductTypeID(rs.getLong("CREDITPRODUCTTYPEID"));
            }
            if(rs!=null){
                rs.close();
                rs=null;
            }
            if(ps!=null){
                ps.close();
                ps=null;
            }
            strsql = "select count(id) from LOAN_CREDITCHECKREPORTDETAIL where CREDITCHECKREPORTID=" + id;
            ps = conn.prepareStatement(strsql);
            rs = ps.executeQuery();
           if(rs.next()) {
                t=rs.getInt(1);
            }
           if(rs!=null){
               rs.close();
               rs=null;
           }
           if(ps!=null){
               ps.close();
               ps=null;
           }
           assureClientID=new long[t];
           assureClientName=new String[t];
           assureRate=new double[t];
           creditTypeID=new long[t];
           strsql = "select ASSURECLIENTID,ASSURERATE,CREDITTYPEID from LOAN_CREDITCHECKREPORTDETAIL where CREDITCHECKREPORTID=" + id;
           ps = conn.prepareStatement(strsql);
           rs = ps.executeQuery();
           int i=0;
           
          while(rs.next()) {
              assureClientID[i]=rs.getLong("ASSURECLIENTID");
              assureClientName[i]=LOANNameRef.getClientNameById(assureClientID[i]);
              assureRate[i]=rs.getDouble("ASSURERATE");
              creditTypeID[i]=rs.getLong("CREDITTYPEID");
              i++;
              if(i==t)
                  break;
           }
          if(rs!=null){
              rs.close();
              rs=null;
          }
          if(ps!=null){
              ps.close();
              ps=null;
          }
          ccrinfo.setAssureClientID(assureClientID);
          ccrinfo.setAssureClientName(assureClientName);
          ccrinfo.setAssureRate(assureRate);
          ccrinfo.setCreditTypeID(creditTypeID);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return ccrinfo;

    }
  

    public static void main(String args[]) {
        try {
            long i = -1;
            CreditCheckReportDAO dao = new CreditCheckReportDAO();
            //i=dao.typeTrans(LOANConstant.CreditProduct.SP);
            i = dao.savecreditcheckreport(new CreditCheckReportInfo());
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}