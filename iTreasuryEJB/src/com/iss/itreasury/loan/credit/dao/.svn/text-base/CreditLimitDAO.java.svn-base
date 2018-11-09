/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.credit.dao;

import java.sql.*;

import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.actiontrace.dataentity.*;
import com.iss.itreasury.loan.actiontrace.bizlogic.*;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.credit.dataentity.*;
import com.iss.itreasury.loan.loanapply.dataentity.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import java.util.*;
import com.iss.itreasury.system.bizlogic.*;
import com.iss.itreasury.system.dataentity.*;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.loan.credit.bizlogic.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditLimitDAO
    extends LoanDAO {
  protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
  public CreditLimitDAO() {
    super("loan_creditLimit");
  }

  public CreditLimitDAO(Connection conn) {
    super("loan_creditLimit", conn);
  }

  public Collection findByMultiOption(CreditLimitQueryInfo qInfo) throws
      ITreasuryDAOException {
    Collection c = null;
    ArrayList v = null;
    String strSQL = "";

    long queryPurpose = qInfo.getQueryPurpose();

    try {
      /*-----------------init DAO --------------------*/
      try {
        initDAO();
      }
      catch (ITreasuryDAOException e) {
        throw e;
      }
      /*----------------end of init------------------*/

      try {
    	
    	  //modified by mzh_fu 2007/03/22　解决查询时没有区分办事处的问题
    	 /** 
        strSQL = "select * from loan_creditLimit" + " where 1=1";

        if (queryPurpose == 1) { //for modify
        }
        else if (queryPurpose == 2) { //激活/取消激活 查询
          strSQL += " and statusID in(" + LOANConstant.CreditStatus.SUBMIT;
          strSQL += "," + LOANConstant.CreditStatus.CHECK;
          strSQL += "," + LOANConstant.CreditStatus.ACTIVE + ") ";
        }
        else if (queryPurpose == 3) { //冻结/取消冻结 查询
          strSQL += " and statusID in(";
          strSQL += LOANConstant.CreditStatus.FREEZE;
          strSQL += "," + LOANConstant.CreditStatus.ACTIVE + ") ";
        }
        else if (queryPurpose == 4) { //for end contract
        }
        else if (queryPurpose == 5) { //
        }
        else if (queryPurpose == 6) { //授信设置查询
          strSQL += " and statusID>0 ";
        }
        else if (queryPurpose == 7) { //当前使用情况查询
          strSQL += " and ( statusID=" + LOANConstant.CreditStatus.ACTIVE;
          strSQL += " or statusID=" + LOANConstant.CreditStatus.FREEZE + ") ";
        }

        if (qInfo.getStartClientID() > 0) { //客户ID开始
          strSQL += " and ClientID>=" + qInfo.getStartClientID();
        }
        if (qInfo.getEndClientID() > 0) { //客户ID结束
          strSQL += " and clientID<=" + qInfo.getEndClientID();
        }
        if (qInfo.getClientID() > 0) {
          strSQL += " and clientID=" + qInfo.getClientID();
        }
        if (qInfo.getStartAmount() > 0) { //授信额度开始
          strSQL += " and amount>=" + qInfo.getStartAmount();
        }
        if (qInfo.getEndAmount() > 0) { //授信额度结束
          strSQL += " and amount<=" + qInfo.getEndAmount();
        }
        //////////////////////////////////新加的信用额度和担保额度//////////////////////////////////////

        if (qInfo.getStartCreditAmount() > 0) { //信用额度开始
          strSQL += " and creditAmount>=" + qInfo.getStartCreditAmount();
        }
        if (qInfo.getEndCreditAmount() > 0) { //信用额度结束
          strSQL += " and creditAmount<=" + qInfo.getEndCreditAmount();
        }

        if (qInfo.getStartAssureAmount() > 0) { //担保额度开始
          strSQL += " and assureAmount>=" + qInfo.getStartAssureAmount();
        }
        if (qInfo.getEndAssureAmount() > 0) { //担保额度结束
          strSQL += " and assureAmount<=" + qInfo.getEndAssureAmount();
        }

        ///////////////////////////////end//////////////////////////////////////////////////

        //录入日期开始
        if (qInfo.getStartInputDate() != null &&
            qInfo.getStartInputDate().length() > 0) {
          strSQL += " and INPUTDATE>=to_date('" + qInfo.getStartInputDate();
          strSQL += "','yyyy-mm-dd')";
        }
        //录入日期结束
        if (qInfo.getEndInputDate() != null &&
            qInfo.getEndInputDate().length() > 0) {
          strSQL += " and INPUTDATE<=to_date('" + qInfo.getEndInputDate();
          strSQL += "','yyyy-mm-dd')";
        }
        if (qInfo.getStatusID() > 0) {
          strSQL += " and statusID=" + qInfo.getStatusID();
        }

        ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
        int nIndex = 0;
        String orderParamString = qInfo.getOrderParamString();

        nIndex = orderParamString.indexOf(".");

        if (nIndex > 0) {
          if (orderParamString.substring(0,
                                         nIndex).toLowerCase().equals(
              "loan_creditlimit")) {
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
    	  
    	  // added by mzh_fu 2007/03/22 将此信息与客户关联以区分办事处   	  
          strSQL = "select lc.* from loan_creditLimit lc,CLIENT_CLIENTINFO cc  " +
                	" where cc.id = lc.Clientid and cc.OFFICEID = "+qInfo.getOfficeId();

          if (queryPurpose == 1) { //for modify
          }
          else if (queryPurpose == 2) { //激活/取消激活 查询
            strSQL += " and lc.statusID in(" + LOANConstant.CreditStatus.SUBMIT;
            strSQL += "," + LOANConstant.CreditStatus.CHECK;
            strSQL += "," + LOANConstant.CreditStatus.ACTIVE + ") ";
          }
          else if (queryPurpose == 3) { //冻结/取消冻结 查询
            strSQL += " and lc.statusID in(";
            strSQL += LOANConstant.CreditStatus.FREEZE;
            strSQL += "," + LOANConstant.CreditStatus.ACTIVE + ") ";
          }
          else if (queryPurpose == 4) { //for end contract
          }
          else if (queryPurpose == 5) { //
          }
          else if (queryPurpose == 6) { //授信设置查询
            strSQL += " and lc.statusID>0 ";
          }
          else if (queryPurpose == 7) { //当前使用情况查询
            strSQL += " and ( lc.statusID=" + LOANConstant.CreditStatus.ACTIVE;
            strSQL += " or lc.statusID=" + LOANConstant.CreditStatus.FREEZE + ") ";
          }

          if (qInfo.getStartClientID() > 0) { //客户ID开始
            strSQL += " and lc.ClientID>=" + qInfo.getStartClientID();
          }
          if (qInfo.getEndClientID() > 0) { //客户ID结束
            strSQL += " and lc.clientID<=" + qInfo.getEndClientID();
          }
          if (qInfo.getClientID() > 0) {
            strSQL += " and lc.clientID=" + qInfo.getClientID();
          }
          if (qInfo.getStartAmount() > 0) { //授信额度开始
            strSQL += " and lc.amount>=" + qInfo.getStartAmount();
          }
          if (qInfo.getEndAmount() > 0) { //授信额度结束
            strSQL += " and lc.amount<=" + qInfo.getEndAmount();
          }
          //////////////////////////////////新加的信用额度和担保额度//////////////////////////////////////

          if (qInfo.getStartCreditAmount() > 0) { //信用额度开始
            strSQL += " and lc.creditAmount>=" + qInfo.getStartCreditAmount();
          }
          if (qInfo.getEndCreditAmount() > 0) { //信用额度结束
            strSQL += " and lc.creditAmount<=" + qInfo.getEndCreditAmount();
          }

          if (qInfo.getStartAssureAmount() > 0) { //担保额度开始
            strSQL += " and lc.assureAmount>=" + qInfo.getStartAssureAmount();
          }
          if (qInfo.getEndAssureAmount() > 0) { //担保额度结束
            strSQL += " and lc.assureAmount<=" + qInfo.getEndAssureAmount();
          }

          ///////////////////////////////end//////////////////////////////////////////////////

          //录入日期开始
          if (qInfo.getStartInputDate() != null &&
              qInfo.getStartInputDate().length() > 0) {
            strSQL += " and lc.INPUTDATE>=to_date('" + qInfo.getStartInputDate();
            strSQL += "','yyyy-mm-dd')";
          }
          //录入日期结束
          if (qInfo.getEndInputDate() != null &&
              qInfo.getEndInputDate().length() > 0) {
            strSQL += " and lc.INPUTDATE<=to_date('" + qInfo.getEndInputDate();
            strSQL += "','yyyy-mm-dd')";
          }
          if (qInfo.getStatusID() > 0) {
            strSQL += " and lc.statusID=" + qInfo.getStatusID();
          }

          ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
          int nIndex = 0;
          String orderParamString = qInfo.getOrderParamString();

          nIndex = orderParamString.indexOf(".");

          if (nIndex > 0) {
            if (orderParamString.substring(0,
                                           nIndex).toLowerCase().equals(
                "loan_creditlimit")) {
              strSQL += " order by lc." + orderParamString.substring(nIndex + 1);
            }
          }
          else {
            strSQL += " order by lc.ID";
          }

          if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
            strSQL += " desc";
          }
    	  
        log4j.info("sql= \n" + strSQL);
        prepareStatement(strSQL);
        executeQuery();
        c = getDataEntitiesFromResultSet(CreditLimitInfo.class);

        v = (ArrayList) c;
        CreditLimitInfo info = null;
        CreditBiz cbiz=new CreditBiz();
        for (int i = 0; i < v.size(); i++) {
          info = (CreditLimitInfo) v.get(i);
          info.setApplyAmount(cbiz.getApplyCreditAmount(info.getClientID())+cbiz.getApplyAssureAmount(info.getClientID()));
          info.setUsedAmount(cbiz.getuseCreditAmount(info.getClientID())+cbiz.getuseAssureAmount(info.getClientID()));
          info.setCanUseAmount(cbiz.getValidAssureAmount(info.getClientID())+cbiz.getValidCreditAmount(info.getClientID()));
        }

      }
      catch (Exception e) {
        throw new ITreasuryDAOException("查询授信设置出错", e);
      }

      /*----------------finalize Dao-----------------*/
      try {
        finalizeDAO();
      }
      catch (ITreasuryDAOException e) {
        throw e;
      }
      /*----------------end of finalize---------------*/
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      finalizeDAO();
    }
    return (v.size() > 0 ? v : null);
  }

  public Collection findByDateOption(CreditLimitInfo Info) throws
      ITreasuryDAOException {
    Collection c = null;
    String strSQL = "";

    if (Info.getStartDate() == null || Info.getEndDate() == null) {

      return c;
    }

    try {
      /*-----------------init DAO --------------------*/
      try {
        initDAO();
      }
      catch (ITreasuryDAOException e) {
        throw e;
      }
      /*----------------end of init------------------*/

      try {
        strSQL = "select * from loan_creditLimit ";
        strSQL += " where clientID =" + Info.getClientID();
        strSQL += " and statusID > 0";
        strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
        strSQL += " and statusID !=" + LOANConstant.CreditStatus.OVERTIME;

        strSQL += " and ((STARTDATE<=to_date('" +
            Info.getStartDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')";
        strSQL += " and ENDDATE>=to_date('" +
            Info.getEndDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd'))";

        strSQL += " or ( STARTDATE>=to_date('" +
            Info.getStartDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')";
        strSQL += " and STARTDATE<=to_date('" +
            Info.getEndDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd'))";

        strSQL += " or ( ENDDATE>=to_date('" +
            Info.getStartDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')";
        strSQL += " and ENDDATE<=to_date('" +
            Info.getEndDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')))";

        if (Info.getId() > 0) {
          strSQL += " and id !=" + Info.getId();
        }

        log4j.info("sql= \n" + strSQL);
        prepareStatement(strSQL);
        executeQuery();
        c = getDataEntitiesFromResultSet(CreditLimitInfo.class);

      }
      catch (Exception e) {
        throw new ITreasuryDAOException("查询授信设置出错", e);
      }

      /*----------------finalize Dao-----------------*/
      try {
        finalizeDAO();
      }
      catch (ITreasuryDAOException e) {
        throw e;
      }
      /*----------------end of finalize---------------*/
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
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
      strSQL =
          "select sum(a.mLoanAmount) from loan_loanForm a,loan_contractForm b"
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

      strSQL =
          "select sum(a.mLoanAmount) from loan_loanForm a,loan_contractForm b"
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
          + LOANConstant.LoanType.ZTX
          + ")";
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
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    finally {
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
      strSQL =
          "select sum(mExamineAmount) from loan_contractForm"
          + " where 1=1"
          + " and nStatusID>="
          + LOANConstant.ContractStatus.NOTACTIVE
          + " and nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT
          + " and nBorrowClientID="
          + clientID
          + " and nTypeID in ("
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

      strSQL =
          "select sum(mExamineAmount) from loan_contractForm"
          + " where 1=1"
          + " and nStatusID>="
          + LOANConstant.ContractStatus.NOTACTIVE
          + " and nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT
          + " and nBorrowClientID="
          + clientID
          + " and nTypeID in ("
          + LOANConstant.LoanType.TX
          + ","
          + LOANConstant.LoanType.ZTX
          + ")";
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
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    finally {
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

  public CreditLimitInfo findCreditLimitByClientID(long clientID) throws
      Exception {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    CreditLimitInfo info = null;

    try {
      conn = Database.getConnection();
      strSQL =
          " select * from Loan_CreditLimit "
          + " where 1 = 1 "
          + " and StatusID in ( "
          + LOANConstant.CreditStatus.ACTIVE
          + " , "
          + LOANConstant.CreditStatus.FREEZE
          + " ) "
          + " and clientID = "
          + clientID;

      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        info = new CreditLimitInfo();
        info.setId(rs.getLong("ID"));
        info.setClientID(rs.getLong("clientID"));
        ///////////////////////////////////////////////////////////////////////////////
        info.setCreditAmount(rs.getDouble("creditAmount"));
        info.setAmount(rs.getDouble("amount"));
        info.setAssureAmount(rs.getDouble("assureAmount"));
        /////////////////////////////////////////////////////////////////////////////////
        info.setStartDate(rs.getTimestamp("startDate"));
        info.setEndDate(rs.getTimestamp("endDate"));
        info.setStatusID(rs.getLong("statusID"));
        info.setInputDate(rs.getTimestamp("inputDate"));
        info.setInputUserID(rs.getLong("inputUserID"));
        info.setApplyAmount(findApplyAmountByClient(info.getClientID()));
        info.setUsedAmount(findUseAmountByClient(info.getClientID()));
        info.setCanUseAmount(info.getAmount() -
                             DataFormat.formatDouble(info.getApplyAmount(), 2) -
                             DataFormat.formatDouble(info.getUsedAmount(), 2));
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
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    finally {
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
      strSQL =
          "select a.*,b.nstatusID as contractStatusID from loan_loanForm a,loan_contractForm b"
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
          + clientID;

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
        if (applyInfo.getTypeID() == LOANConstant.LoanType.TX ||
            applyInfo.getTypeID() == LOANConstant.LoanType.ZTX) {
          applyInfo.setUseCreditAmount(applyInfo.getLoanAmount() * ttRate);
        }
        else {
          applyInfo.setUseCreditAmount(applyInfo.getLoanAmount() * ddRate);
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
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    finally {
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
      strSQL =
          "select * from loan_contractForm"
          + " where 1=1"
          + " and nStatusID>="
          + LOANConstant.ContractStatus.NOTACTIVE
          + " and nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT
          + " and nBorrowClientID="
          + clientID;

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
        if (info.getLoanTypeID() == LOANConstant.LoanType.TX ||
            info.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
          info.setUseCreditAmount( (info.getExamineAmount() -
                                    this.
                                    getDiscountRepayAmountByContract(conn,
              info.getContractID())) * ttRate);
        }
        else {
          info.setUseCreditAmount( (info.getExamineAmount() -
                                    this.
                                    getRepayAmountByContract(conn,
              info.getContractID())) *
                                  ddRate);
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
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    finally {
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

  public long[] findForAutoActive(Timestamp tsToday) throws Exception {
    String strSQL = "";
    long[] id = new long[1000];
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int i = 0;
    try {
      conn = Database.getConnection();
      strSQL = "select ID from loan_creditLimit" + " where 1=1" +
          " and statusID=" + LOANConstant.CreditStatus.CHECK
          +" or statusID="+LOANConstant.CreditStatus.SUBMIT;

      log4j.info("sql= \n" + strSQL);
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        
        id[i++] = rs.getLong("ID");
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
    }
    catch (Exception e) {
      throw new ITreasuryDAOException("查询授信设置出错", e);
    }
    finally {
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

  public static void main(String args[]) {
    CreditLimitDAO dao = new CreditLimitDAO();
    try {long[] a=null;
      a=dao.findIsControlProduct();
      for(int i=0;i<a.length;i++){
          System.out.println(a[i]);
      }
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public double getRepayAmount(Connection con, long clientID) throws Exception {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select mAmount "
          +
          " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and a.nClientID=" + clientID
          + " and b.id=a.nLoanContractID"
          + " and b.nTypeID in (1,2,6,7,8,10)"
          + " ) ";

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

    }
    catch (SQLException e) {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      e.printStackTrace();
    }
    finally {
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

  public double getRepayAmountByContract(Connection con, long contractID) throws
      Exception {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select mAmount "
          +
          " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and b.id=a.nLoanContractID"
          + " and a.nLoanContractID=" + contractID
          + " and b.nTypeID in (1,2,6,7,8,10)"
          + " ) ";

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

    }
    catch (SQLException e) {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      e.printStackTrace();
    }
    finally {
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

  public double getGrantAmount(Connection con, long clientID) throws Exception {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select a.mAmount "
          +
          " from sett_transgrantloan a,loan_ContractForm b where a.nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and b.id=a.nLoanContractID"
          + " and b.nTypeID in (1,2,6,7,8,10)"
          + " and b.nBorrowClientID=" + clientID
          + " ) ";

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
    }
    catch (SQLException e) {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      e.printStackTrace();
    }
    finally {
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

  public double getDiscountRepayAmount(Connection con, long clientID) throws
      Exception {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select mAmount "
          + " from Sett_Transrepaymentdiscount "
          + " where nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and nClientID=" + clientID
          + " ) ";

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

    }
    catch (SQLException e) {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      e.printStackTrace();
    }
    finally {
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
                                                 long contractID) throws
      Exception {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select mAmount "
          + " from Sett_Transrepaymentdiscount "
          + " where nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and NDISCOUNTCONTRACTID=" + contractID
          + " ) ";

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

    }
    catch (SQLException e) {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      e.printStackTrace();
    }
    finally {
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

  public double getDiscountGrantAmount(Connection con, long clientID) throws
      Exception {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select a.MDISCOUNTBILLAMOUNT as mAmount"
          + " from SETT_TRANSGRANTDISCOUNT a,loan_contractForm b "
          + " where a.nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and b.id= a.nDisCountContractID"
          + " and b.nBorrowClientID=" + clientID
          + " ) ";

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
    }
    catch (SQLException e) {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      e.printStackTrace();
    }
    finally {
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
   * 将已经到期的授信记录置为过期 by daifeng 2005-5-17
   * @throws Exception
   */
  public void updateOverTime() throws Exception {
    String strSQL = "";
    Connection conn = null;
    PreparedStatement ps = null;

    strSQL = " update Loan_CreditLimit set statusid=" +
        LOANConstant.CreditStatus.OVERTIME
        + " where StatusId in (" + LOANConstant.CreditStatus.FREEZE
        + "," + LOANConstant.CreditStatus.ACTIVE
        + "," + LOANConstant.CreditStatus.CHECK
        + "," + LOANConstant.CreditStatus.SUBMIT + ")"
        + " and EndDate< TO_DATE('"
        + Env.getSystemDateString() + "','yyyy-mm-dd')";
    //log4j.debug("SQL="+strSQL);
    try {
      conn = Database.getConnection();
      ps = conn.prepareStatement(strSQL);
      int i = ps.executeUpdate();
      log4j.debug(i + " lines effected!");

      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException e) {
      throw e;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
  }
  public long[] findIsControlProduct()throws Exception{
      long[] a=null;
      int i=0;
      
      ResultSet rs = null;
      PreparedStatement ps=null;
      String strSQL = "";
      try {
            try {
                initDAO();
            } catch (ITreasuryDAOException e) {
                throw e;
            }
            strSQL="select count(credittypeid) from loan_creditproduct "
            +" where 1=1 and ISCONTROL=1 and STATUSID=1";
            ps=transConn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if(rs.next())
                a=new long[rs.getInt(1)];
            if (rs != null) {
                rs.close();
                rs = null;
            }
            strSQL="select credittypeid from loan_creditproduct "
                    +" where ISCONTROL=1 and STATUSID=1 order by id";
           this.prepareStatement(strSQL);
           rs=this.executeQuery();
           while(rs.next()){
               a[i]=rs.getLong("CREDITTYPEID");
               i++;
           }
      }catch(SQLException e){
          e.printStackTrace();
      }finally {
          finalizeDAO();
          }
      return a;
  }
  
}
