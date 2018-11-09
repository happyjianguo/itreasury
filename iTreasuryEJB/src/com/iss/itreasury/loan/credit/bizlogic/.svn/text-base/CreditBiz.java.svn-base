/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.credit.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.actiontrace.dataentity.*;
import com.iss.itreasury.loan.actiontrace.bizlogic.*;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.credit.dao.*;
import com.iss.itreasury.loan.credit.dataentity.*;
import com.iss.itreasury.loan.loanapply.dao.LoanApplyDao;
import com.iss.itreasury.loan.loanapply.dataentity.AssureInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
//import com.sun.rsasign.e;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditBiz {
	private static Object lockObject = new Object();
  public long getCreditTypeByLoanType(long loanTypeID) {
    long ret = -1;
    switch ( (int) loanTypeID) {
      case (int) LOANConstant.LoanType.ZY:
        ret = LOANConstant.CreditProduct.ZY;
        break;
      case (int) LOANConstant.LoanType.ZGXE:
        ret = LOANConstant.CreditProduct.ZY;
        break;
      case (int) LOANConstant.LoanType.YT:
        ret = LOANConstant.CreditProduct.ZY;
        break;
      case (int) LOANConstant.LoanType.TX:
        ret = LOANConstant.CreditProduct.SP;
        break;
    }
    return ret;

  }

  public CreditInfo findCreditInfo(long clientID, long loanTypeID) throws
      Exception {
    CreditInfo creditInfo = new CreditInfo();
    CreditLimitDAO dao = new CreditLimitDAO();

    creditInfo.setProductInfo(findProductInfoByLoanType(getCreditTypeByLoanType(
        loanTypeID)));

    creditInfo.setLimitInfo(dao.findCreditLimitByClientID(clientID));
    return creditInfo;
  }
  public CreditLimitInfo  findCreditInfoByClientID(long clientID) throws
  Exception {
      CreditLimitInfo  creditlimitInfo = new CreditLimitInfo();
      CreditCheckReportDAO dao = new CreditCheckReportDAO();
   creditlimitInfo=dao.check(clientID);
   return creditlimitInfo;
}
 
  public CreditProductInfo findProductInfoByLoanType(long loanTypeID) throws
      Exception {
    CreditProductInfo info = null;
    CreditProductDAO dao = new CreditProductDAO();

    try {
      info = dao.findByLoanTypeID(loanTypeID);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return info;
  }

  public CreditProductInfo findProductInfoByCreditTypeID(long loanTypeID) throws
      Exception {
    CreditProductInfo info = null;
    CreditProductDAO dao = new CreditProductDAO();

    try {CreditCheckReportDAO ccdao=new CreditCheckReportDAO();
      info = dao.findByCreditTypeID(ccdao.untypeTrans(loanTypeID));
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return info;
  }

  public long saveProductInfo(CreditProductInfo info) throws Exception {
    long ret = -1;
    CreditProductDAO dao = new CreditProductDAO();

    try {
      if (info.getCreditTypeID() > 0) {
        CreditProductInfo infoTemp = dao.findByCreditTypeID(info.
            getCreditTypeID());

        if (infoTemp != null && infoTemp.getEngrossRate() > 0) {
          if (info.getId() <= 0) {
            info.setId(infoTemp.getId());
          }

          dao.update(info);
        }
        else {
          dao.setUseMaxID();
          ret = dao.add(info);
        }
      }
    }
    catch (ITreasuryDAOException e) {
      e.printStackTrace();
      throw e;
    }
    return ret;
  }

  public void deleteProductInfo(long id) throws Exception {
    CreditProductDAO dao = new CreditProductDAO();
    CreditProductInfo info = new CreditProductInfo();

    try {
      info = (CreditProductInfo) dao.findByID(id, CreditProductInfo.class);

      long count = dao.getCountCreditByCreditTypeID(info.getCreditTypeID());

      if (count > 0) {
        throw new IException("Loan_E120");
      }
      //info.setId(id);
      info.setStatusID(Constant.RecordStatus.INVALID);
      dao.update(info);
    }
    catch (ITreasuryDAOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public void deleteProductInfoByCreditTypeID(long CreditTypeID) throws
      Exception, IException {
    if (CreditTypeID <= 0) {
      return;
    }
    CreditProductDAO dao = new CreditProductDAO();

    try {
      long count = dao.getCountCreditByCreditTypeID(CreditTypeID);

      if (count > 0) {
        throw new IException("Loan_E120");
      }

      dao.deleteByCreditTypeID(CreditTypeID);

    }
    catch (IException ie) {
      ie.printStackTrace();
      throw ie;
    }
  }

  public Collection findLimitInfoByCondition(CreditLimitQueryInfo qInfo) throws
      Exception {
    Collection c = null;
    CreditLimitDAO dao = new CreditLimitDAO();

    try {
      c = dao.findByMultiOption(qInfo);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return c;
  }

  public long saveLimitInfo(CreditLimitInfo info) throws Exception, IException {
    long ret = -1;
    Connection conn = Database.getConnection();

    try {
      conn.setAutoCommit(false);

      CreditLimitDAO dao = new CreditLimitDAO(conn);
      ActionTraceBean abean = new ActionTraceBean();

      Collection c = dao.findByDateOption(info);

      if (c != null) {
        throw new IException("Loan_E121");
      }

      if (info.getId() > 0) {
        //记日志
        ActionTraceInfo ainfo = new ActionTraceInfo();
        ainfo.setModuleID(Constant.ModuleType.LOAN);
        ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
        ainfo.setItemID(info.getId());
        ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.MODIFY);

        if (info.getAddOrMinus() == 1) {
          ainfo.setChangeAmount(info.getCreditChange());
        }
        else if (info.getAddOrMinus() == 2) {
          ainfo.setChangeAmount( -info.getCreditChange());
        }

        ainfo.setAmount(info.getAmount());
        ainfo.setInputUserID(info.getInputUserID());
        ainfo.setInputDate(DataFormat.getDateTime(conn));
        ainfo.setStartDate(info.getStartDate());
        ainfo.setEndDate(info.getEndDate());

        if (info.getAddOrMinus() == 1) {
          info.setCreditAmount(info.getCreditAmount() + info.getCreditChange());
        }
        else if (info.getAddOrMinus() == 2) {
          info.setCreditAmount(info.getCreditAmount() - info.getCreditChange());
        }
        dao.update(info);

        abean.writeActionTrace(ainfo, conn);
      }
      else {
        dao.setUseMaxID();
        ret = dao.add(info);
        //记日志
        ActionTraceInfo ainfo = new ActionTraceInfo();
        ainfo.setModuleID(Constant.ModuleType.LOAN);
        ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
        ainfo.setItemID(ret);
        ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.ADDNEW);
        ainfo.setChangeAmount(info.getCreditChange());
        ainfo.setAmount(info.getAmount());
        ainfo.setInputUserID(info.getInputUserID());
        ainfo.setInputDate(DataFormat.getDateTime(conn));
        ainfo.setStartDate(info.getStartDate());
        ainfo.setEndDate(info.getEndDate());
        abean.writeActionTrace(ainfo, conn);
      }

      conn.commit();
      conn.setAutoCommit(true);
      if (conn != null) {
        conn.close();
        conn = null;
      }

    }
    catch (IException e) {
      conn.rollback();
      conn.setAutoCommit(true);

      if (conn != null) {
        conn.close();
        conn = null;
      }
      e.printStackTrace();
      throw e;
    }
    return ret;
  }

  public void deleteLimitInfo(long id, long lInputUserID) throws Exception {
    Connection conn = Database.getConnection();

    try {
      conn.setAutoCommit(false);

      CreditLimitDAO dao = new CreditLimitDAO(conn);
      CreditLimitInfo info = new CreditLimitInfo();

      info.setId(id);
      info.setStatusID(LOANConstant.CreditStatus.DELETE);

      dao.update(info);

      //记日志
      ActionTraceInfo ainfo = new ActionTraceInfo();
      ainfo.setModuleID(Constant.ModuleType.LOAN);
      ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
      ainfo.setItemID(id);
      ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.DELETE);
      ainfo.setInputUserID(lInputUserID);
      ainfo.setInputDate(DataFormat.getDateTime(conn));

      ActionTraceBean abean = new ActionTraceBean();
      abean.writeActionTrace(ainfo, conn);

      conn.commit();
      conn.setAutoCommit(true);

      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (ITreasuryDAOException e) {
      conn.rollback();
      conn.setAutoCommit(true);
      if (conn != null) {
        conn.close();
        conn = null;
      }
      e.printStackTrace();
      throw e;
    }
  }

  public CreditLimitInfo findLimitInfoByID(long id) throws Exception {
    CreditLimitDAO dao = new CreditLimitDAO();
    CreditLimitInfo info = null;
    try {
      info = (CreditLimitInfo) dao.findByID(id, CreditLimitInfo.class);
      info.setApplyAmount(dao.findApplyAmountByClient(info.getClientID()));
      info.setUsedAmount(dao.findUseAmountByClient(info.getClientID()));
      info.setCanUseAmount(info.getCreditAmount() - info.getApplyAmount() -
                           info.getUsedAmount());

    }
    catch (ITreasuryDAOException e) {
      e.printStackTrace();
      throw e;
    }
    return info;
  }

  public void cancelLimitInfoByID(long id) throws Exception {
    CreditLimitDAO dao = new CreditLimitDAO();
    try {
      CreditLimitInfo info = new CreditLimitInfo();

      info.setId(id);
      info.setStatusID(Constant.RecordStatus.INVALID);

      dao.update(info);
    }
    catch (ITreasuryDAOException e) {
      e.printStackTrace();
      throw e;
    }

  }

  public void active(long[] id, long lInputUserID) throws Exception {
    Connection conn = null;
    CreditLimitDAO dao = null;

    long lStatus = LOANConstant.CreditStatus.ACTIVE;

    conn = Database.getConnection();

    dao = new CreditLimitDAO(conn);

    try {
      conn.setAutoCommit(false);

      for (int i = 0; i < id.length; i++) {
        if (id[i] > 0) {
          CreditLimitInfo info = (CreditLimitInfo) dao.findByID(id[i],
              CreditLimitInfo.class);

          if (info.getStatusID() == LOANConstant.CreditStatus.SUBMIT ||
              info.getStatusID() == LOANConstant.CreditStatus.CHECK) {
            //判断将用户提交的授信额度变为已激活 还是 已批准 状态
            if (DataFormat.getDateTime(conn).before(info.getStartDate())) {
              lStatus = LOANConstant.CreditStatus.CHECK;
            }
            else {
              lStatus = LOANConstant.CreditStatus.ACTIVE;
            }

            //将已有的已激活状态的授信额度变为已过期状态
            if (lStatus == LOANConstant.CreditStatus.ACTIVE) {
              CreditLimitQueryInfo qInfo = new CreditLimitQueryInfo();

              qInfo.setClientID(info.getClientID());
              qInfo.setStatusID(LOANConstant.CreditStatus.ACTIVE);

              Collection c = dao.findByMultiOption(qInfo);

              if (c != null && c.size() > 0) {
                Iterator it = c.iterator();
                while (it.hasNext()) {
                  CreditLimitInfo cinfo = (CreditLimitInfo) it.next();
                  CreditLimitInfo info2 = new CreditLimitInfo();

                  info2.setId(cinfo.getId());
                  info2.setStatusID(LOANConstant.CreditStatus.OVERTIME);
                  dao.update(info2);

                  //记日志
                  ActionTraceInfo ainfo = new ActionTraceInfo();
                  ainfo.setModuleID(Constant.ModuleType.LOAN);
                  ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
                  ainfo.setItemID(cinfo.getId());
                  ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.
                                    OVERTIME);
                  ainfo.setChangeAmount(0);
                  ainfo.setAmount(cinfo.getAmount());
                  ainfo.setInputUserID(lInputUserID);
                  ainfo.setInputDate(DataFormat.getDateTime(conn));
                  ainfo.setStartDate(cinfo.getStartDate());
                  ainfo.setEndDate(cinfo.getEndDate());

                  ActionTraceBean abean = new ActionTraceBean();
                  abean.writeActionTrace(ainfo, conn);
                }
              }
            }

            //将用户提交的授信额度变为已激活 或 已批准 状态
            CreditLimitInfo info2 = new CreditLimitInfo();
            info2.setId(id[i]);
            info2.setStatusID(lStatus);
            dao.update(info2);

            //记日志
            ActionTraceInfo ainfo = new ActionTraceInfo();
            ainfo.setModuleID(Constant.ModuleType.LOAN);
            ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
            ainfo.setItemID(id[i]);
            ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.ACTIVE);
            ainfo.setChangeAmount(0);
            ainfo.setAmount(info.getAmount());
            ainfo.setInputUserID(lInputUserID);
            ainfo.setInputDate(DataFormat.getDateTime(conn));
            ainfo.setStartDate(info.getStartDate());
            ainfo.setEndDate(info.getEndDate());

            ActionTraceBean abean = new ActionTraceBean();
            abean.writeActionTrace(ainfo, conn);

          }
        }
        else {
          break;
        }
      }
      conn.commit();
      conn.setAutoCommit(true);

      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      conn.rollback();
      conn.setAutoCommit(true);
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
  }

  public void cancelActive(long[] id, long lInputUserID) throws Exception {
    Connection conn = null;
    CreditLimitDAO dao = null;

    conn = Database.getConnection();
    dao = new CreditLimitDAO(conn);

    try {
      conn.setAutoCommit(false);

      for (int i = 0; i < id.length; i++) {
        if (id[i] > 0) {
          CreditLimitInfo info = (CreditLimitInfo) dao.findByID(id[i],
              CreditLimitInfo.class);

          if (info.getStatusID() == LOANConstant.CreditStatus.ACTIVE ||
              info.getStatusID() == LOANConstant.CreditStatus.CHECK) {
            CreditLimitInfo info2 = new CreditLimitInfo();
            info2.setId(id[i]);
            info2.setStatusID(LOANConstant.CreditStatus.SUBMIT);
            dao.update(info2);

            //记日志
            ActionTraceInfo ainfo = new ActionTraceInfo();
            ainfo.setModuleID(Constant.ModuleType.LOAN);
            ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
            ainfo.setItemID(id[i]);
            ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.
                              CANCELACTIVE);
            ainfo.setChangeAmount(0);
            ainfo.setAmount(info.getAmount());
            ainfo.setInputUserID(lInputUserID);
            ainfo.setInputDate(DataFormat.getDateTime(conn));
            ainfo.setStartDate(info.getStartDate());
            ainfo.setEndDate(info.getEndDate());

            ActionTraceBean abean = new ActionTraceBean();
            abean.writeActionTrace(ainfo, conn);
          }
        }
      }

      conn.commit();
      conn.setAutoCommit(true);

      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      conn.rollback();
      conn.setAutoCommit(true);
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
  }

  public void freeze(long[] id, long lInputUserID) throws Exception {
    Connection conn = null;
    CreditLimitDAO dao = null;

    conn = Database.getConnection();
    dao = new CreditLimitDAO(conn);

    try {
      conn.setAutoCommit(false);

      for (int i = 0; i < id.length; i++) {
        if (id[i] > 0) {
          CreditLimitInfo info = (CreditLimitInfo) dao.findByID(id[i],
              CreditLimitInfo.class);

          if (info.getStatusID() == LOANConstant.CreditStatus.ACTIVE) {
            CreditLimitInfo info2 = new CreditLimitInfo();
            info2.setId(id[i]);
            info2.setStatusID(LOANConstant.CreditStatus.FREEZE);
            dao.update(info2);

            //记日志
            ActionTraceInfo ainfo = new ActionTraceInfo();
            ainfo.setModuleID(Constant.ModuleType.LOAN);
            ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
            ainfo.setItemID(id[i]);
            ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.FREEZE);
            ainfo.setChangeAmount(0);
            ainfo.setAmount(info.getAmount());
            ainfo.setInputUserID(lInputUserID);
            ainfo.setInputDate(DataFormat.getDateTime(conn));
            ainfo.setStartDate(info.getStartDate());
            ainfo.setEndDate(info.getEndDate());

            ActionTraceBean abean = new ActionTraceBean();
            abean.writeActionTrace(ainfo, conn);
          }
        }
      }

      conn.commit();
      conn.setAutoCommit(true);

      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      conn.rollback();
      conn.setAutoCommit(true);
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
  }

  public void cancelFreeze(long[] id, long lInputUserID) throws Exception {
    Connection conn = null;
    CreditLimitDAO dao = null;

    conn = Database.getConnection();
    dao = new CreditLimitDAO(conn);

    try {
      conn.setAutoCommit(false);

      for (int i = 0; i < id.length; i++) {
        if (id[i] > 0) {
          CreditLimitInfo info = (CreditLimitInfo) dao.findByID(id[i],
              CreditLimitInfo.class);

          if (info.getStatusID() == LOANConstant.CreditStatus.FREEZE) {
            CreditLimitInfo info2 = new CreditLimitInfo();
            info2.setId(id[i]);
            info2.setStatusID(LOANConstant.CreditStatus.ACTIVE);
            dao.update(info2);

            //记日志
            ActionTraceInfo ainfo = new ActionTraceInfo();
            ainfo.setModuleID(Constant.ModuleType.LOAN);
            ainfo.setTraceTypeID(LOANConstant.TraceType.CREDIT);
            ainfo.setItemID(id[i]);
            ainfo.setActionID(LOANConstant.ActionTrace.CreditAction.
                              CANCELFREEZE);
            ainfo.setChangeAmount(0);
            ainfo.setAmount(info.getAmount());
            ainfo.setInputUserID(lInputUserID);
            ainfo.setInputDate(DataFormat.getDateTime(conn));
            ainfo.setStartDate(info.getStartDate());
            ainfo.setEndDate(info.getEndDate());

            ActionTraceBean abean = new ActionTraceBean();
            abean.writeActionTrace(ainfo, conn);
          }
        }
      }
      conn.commit();
      conn.setAutoCommit(true);
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      conn.rollback();
      conn.setAutoCommit(true);
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
  }

  public Collection findApplyDetail(long clientID) throws Exception {
    CreditLimitDAO dao = new CreditLimitDAO();

    return dao.findApplyDetail(clientID);
  }

  public Collection findUseDetail(long clientID) throws Exception {
    CreditLimitDAO dao = new CreditLimitDAO();

    return dao.findUseDetail(clientID);
  }

  public void autoActive(Timestamp ts) throws Exception {
    CreditLimitDAO dao = new CreditLimitDAO();
    long[] id = null;

    id = dao.findForAutoActive(ts);
    active(id, -2);
  }
//客户作为借款人的所有正在申请的信用贷款总额
  public double getApplyCreditAmount(long id,long loanType,Connection conn) throws Exception{
      double amount=0;
      double rate = 0;
      double erate = 0;
      double loanAmount = 0;
      CreditCheckReportDAO dao= new CreditCheckReportDAO();
      Collection al=dao.getApplyCreditAmount(id,loanType,conn);
      Iterator it=al.iterator();
      CreditProductDAO cpDao = new CreditProductDAO();
      CreditProductInfo cpInfo = new CreditProductInfo();
      
      while(it.hasNext()){
          LoanApplyInfo lainfo= (LoanApplyInfo)it.next() ;
          cpInfo = cpDao.findByCreditTypeID(dao.untypeTrans(lainfo.getTypeID()));
          erate = cpInfo.getEngrossRate();
          amount=lainfo.getExamineAmount();
         // System.out.println("amount="+amount);
          rate=lainfo.getAdjustRate();
      //    System.out.println("rate="+rate);
          loanAmount += DataFormat.formatDouble(amount * erate * rate/100,2);
      }
      return loanAmount;
  }
  //客户作为借款人的指定贷款类型正在申请的担保贷款总额
  public double getApplyAssureAmount(long id,long loanType,Connection conn) throws Exception{
      double amount=0;
      double rate = 0;
      double erate = 0;
      double loanAmount = 0;
      CreditCheckReportDAO dao= new CreditCheckReportDAO();
      Collection al=dao.getApplyAssureAmount(id,loanType,conn);
      Iterator it=al.iterator();
      CreditProductDAO cpDao = new CreditProductDAO();
      CreditProductInfo cpInfo = new CreditProductInfo();
      
      while(it.hasNext()){
          LoanApplyInfo lainfo= (LoanApplyInfo)it.next() ;
          cpInfo = cpDao.findByCreditTypeID(dao.untypeTrans(lainfo.getTypeID()));
          erate = cpInfo.getEngrossRate();
          amount=lainfo.getExamineAmount();
      //    System.out.println("amount="+amount);
          rate=lainfo.getAdjustRate();
       //   System.out.println("rate="+rate);
          loanAmount += DataFormat.formatDouble(amount * erate * rate/100,2);
      }
      return loanAmount;
  }
  //得到客户作为借款人指定贷款类型的客户未清余额中担保占用部分之和
  public double getBalanceAssureAmountAsApply(long clientid, long loanType,Connection conn)
            throws Exception {
        
        double loanAmount = 0;
        CreditCheckReportDAO dao = new CreditCheckReportDAO();
          
        Collection al = dao.getBalanceAssureAmountAsApply(clientid, loanType,conn);
        Iterator it = al.iterator();
        while (it.hasNext()) {
            ContractInfo cinfo = (ContractInfo) it.next();
            loanAmount += DataFormat.formatDouble(cinfo.getUseCreditAmount(),2);
          // System.out.println("getUseCreditAmountdb="+cinfo.getUseCreditAmount());
           
        }
        return loanAmount;
    }
//得到客户作为借款人指定贷款类型的客户未清余额中信用占用部分之和
  public double getBalanceCreditAmountAsApply(long clientid, long loanType,Connection conn) throws Exception
  {
      double loanAmount = 0;
      CreditCheckReportDAO dao= new CreditCheckReportDAO();
      Collection al=dao.getBalanceCreditAmountAsApply(clientid,loanType,conn);
      Iterator it=al.iterator();
      while(it.hasNext()){
        ContractInfo cinfo=(ContractInfo)it.next();
        loanAmount += DataFormat.formatDouble(cinfo.getUseCreditAmount(),2);
     //   System.out.println("getUseCreditAmountcredit="+cinfo.getUseCreditAmount());
       
       
       
    }
    return loanAmount;
      }
  //客户作为担保人的指定贷款类型正在申请的信用贷款总额
  public double getAssureCreditAmount(long clientID, long loanType,Connection conn) throws Exception
  {
      double amount=0;
      double rate = 0;
      double erate = 0;
      double loanAmount = 0;
      CreditCheckReportDAO dao= new CreditCheckReportDAO();
      CreditProductDAO cpDao = new CreditProductDAO();
      CreditProductInfo cpInfo = new CreditProductInfo();
      
      Collection al=dao.getAssureCreditAmount(clientID,loanType,conn);
      Iterator it=al.iterator();
      while(it.hasNext()){
          LoanApplyInfo lainfo= (LoanApplyInfo)it.next() ;
          cpInfo = cpDao.findByCreditTypeID(dao.untypeTrans(dao.untypeTrans(lainfo.getTypeID())));
          erate = cpInfo.getEngrossRate();
          amount=lainfo.getExamineAmount();
          //System.out.println("dbamount="+amount);
          rate=lainfo.getAdjustRate();
        //  System.out.println("dbrate="+rate);
          loanAmount += DataFormat.formatDouble(amount * erate * rate/100,2);
      }
    return loanAmount;
    }
  //客户作为担保人的指定贷款类型正在申请的担保贷款总额 
  public double getAssureAssureAmount(long clientID, long loanType,Connection conn)
  throws Exception {
      double amount=0;
      double rate = 0;
      double erate = 0;
      double loanAmount = 0;
      CreditCheckReportDAO dao= new CreditCheckReportDAO();
      CreditProductDAO cpDao = new CreditProductDAO();
      CreditProductInfo cpInfo = new CreditProductInfo();
      
      Collection al=dao.getAssureAssureAmount(clientID,loanType,conn);
      Iterator it=al.iterator();
      while(it.hasNext()){
          LoanApplyInfo lainfo= (LoanApplyInfo)it.next() ;
          cpInfo = cpDao.findByCreditTypeID(dao.untypeTrans(lainfo.getTypeID()));
          erate = cpInfo.getEngrossRate();
          amount=lainfo.getExamineAmount();
          rate=lainfo.getAdjustRate();
          loanAmount += DataFormat.formatDouble(amount * erate * rate/100,2);
      }
    return loanAmount;
  }
  //得到客户作为担保人指定贷款类型的客户未清余额中信用占用部分之和
  public double getBalancecreditAmountAsAssure(long clientid, long loanType,Connection conn)
  throws Exception {
     
      double loanAmount = 0;
      CreditCheckReportDAO dao= new CreditCheckReportDAO();
      Collection al=dao.getBalancecreditAmountAsAssure(clientid,loanType,conn);
    Iterator it=al.iterator();
    while(it.hasNext()){
        ContractInfo cinfo=(ContractInfo)it.next();
        loanAmount += DataFormat.formatDouble(cinfo.getUseCreditAmount(),2);
   //     System.out.println("getUseCreditAmountcredit="+cinfo.getUseCreditAmount());
    }
    return loanAmount;
  }
  //得到客户作为担保人指定贷款类型的客户未清余额中担保占用部分之和
  public double getBalanceAssureAmountAsAssure(long clientid, long loanType,Connection conn)
  throws Exception {
     
      double loanAmount = 0;
      CreditCheckReportDAO dao= new CreditCheckReportDAO();
      Collection al=dao.getBalanceAssureAmountAsAssure(clientid,loanType,conn);
    Iterator it=al.iterator();
    while(it.hasNext()){
        ContractInfo cinfo=(ContractInfo)it.next();
        loanAmount += DataFormat.formatDouble(cinfo.getUseCreditAmount(),2);
    //    System.out.println("getUseCreditAmountcredit="+cinfo.getUseCreditAmount());
    }
    return loanAmount;
  }
  //检查授信额度状态,提交日期是否符合要求
  public String check(CreditCheckReportInfo reinfo)throws Exception{
      String result="";
      try{
          CreditCheckReportDAO dao= new CreditCheckReportDAO();
         
          if(dao.check(reinfo.getClientID())==null){
              result="借款单位"+reinfo.getClientname()+"授信额度超过有效期";  
          }
          else 
          { if(reinfo.getAssureClientID()!=null)
            {long a[]=new long[reinfo.getAssureClientID().length];
              a=reinfo.getAssureClientID();
             if(a.length>0)
             { for(int i=0;i<a.length;i++)
                 {
                  if(dao.check(a[i])==null)
                     {result="担保单位"+reinfo.getAssureClientName()[i]+"授信额度超过有效期" ; 
                      break;
                     }
                 }
              }
            }
          }
      }catch(Exception e){
          e.printStackTrace();
      }
      return result;
  }
  //客户已申请的授信额度信用部分
  public double getApplyCreditAmount(long clientid)throws Exception{
      double applyamount=0;//已申请总额
      Connection conn=null;
      try{
          conn=Database.getConnection();
          double a = getApplyCreditAmount(clientid,-1,conn);
         double  b=getAssureCreditAmount(clientid,-1,conn);
          applyamount=a+b;
        //  System.out.println("111111111=="+a); 
      //   System.out.println("2222222=="+b);
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          if(conn!=null)
          {
              conn.close();
              conn=null;
          }
          }
         
      
      return applyamount;
  }
//客户已占用的授信额度信用部分
  public double getuseCreditAmount(long clientid)throws Exception{
      double useamount=0;//已占用总额
      Connection conn=null;
      try{conn=Database.getConnection();
          useamount=getBalanceCreditAmountAsApply(clientid,-1,conn)
          +getBalancecreditAmountAsAssure(clientid,-1,conn);
      }catch(Exception e){
          e.printStackTrace();
      }finally{ 
          if(conn!=null){
              conn.close();
              conn=null;
          }
         
         
      } return useamount;
  }
  //客户可用授信额度信用部分
  public double getValidCreditAmount(long clientid)throws Exception{
      double validamount=0;
      double amount=0;
      
     try{ 
         CreditCheckReportDAO dao= new CreditCheckReportDAO();
         
         amount=dao.check(clientid).getCreditAmount();
         
         validamount=DataFormat.formatDouble(amount-getApplyCreditAmount(clientid)-getuseCreditAmount(clientid),2);
        }catch(Exception e){
            e.printStackTrace();
        }
     
      return validamount;
  }
  //客户已申请的授信额度担保部分
  public double getApplyAssureAmount(long clientid)throws Exception{
      double applyamount=0;//已申请总额
      Connection conn=null;
      try{conn=Database.getConnection();
          applyamount = getApplyAssureAmount(clientid,-1,conn)+ getAssureAssureAmount(clientid,-1,conn);
                 
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          if(conn!=null){
              conn.close();
              conn=null;
          }
         }
      return applyamount;
  }
         
 
//客户已占用的授信额度担保部分
  public double getuseAssureAmount(long clientid)throws Exception{
      double useamount=0;//已占用总额
      Connection conn=null;
      try{conn=Database.getConnection();
          useamount=getBalanceAssureAmountAsApply(clientid,-1,conn)
          +getBalanceAssureAmountAsAssure(clientid,-1,conn);
          
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          if(conn!=null){
              conn.close();
              conn=null;
          }
          
      }
      
      return useamount;
  }
//客户可用授信额度担保部分
  public double getValidAssureAmount(long clientid)throws Exception{
      double validamount=0;
      double amount=0;
     
     try{ 
         CreditCheckReportDAO dao= new CreditCheckReportDAO();
         amount=dao.check(clientid).getAssureAmount();
               
         validamount=DataFormat.formatDouble(amount-getApplyAssureAmount(clientid)-getuseAssureAmount(clientid),2);
        }catch(Exception e){
            e.printStackTrace();
        }
     
      return validamount;
  }
  //授信额度检查报告
 public CreditCheckReportInfo checkreport(CreditCheckReportInfo ccrinfo){
     CreditCheckReportInfo cinfo=new CreditCheckReportInfo();
     double appcreditamount=0;//借款单位申请信用金额
     double appassureamount=0;//借款单位申请担保金额
     double erate = 0;//授信产品占用比例
     double appvalidcredit=0;//借款单位可用信用金额
     double appvalidassure=0;//借款单位可用担保金额
     double camount=0;//客户授信额度设置中的信用金额
     double aAmount=0;//客户授信额度设置中的担保金额
     
     boolean assurecheck=true;//检查担保单位是否有效
     boolean result=true;//最终结果是超没超
     try {
            CreditCheckReportDAO dao = new CreditCheckReportDAO();
            
             int t = ccrinfo.getAssureClientID().length;
             double[] dbcamount = new double[t];//担保单位授信额度设置中的信用金额
             double[] dbaAmount = new double[t];//担保单位授信额度设置中的担保金额
             double[] dbusecreditamount = new double[t];//担保人已用信用金额
             double[] dbuseassureamount = new double[t];//担保人已用担保金额
             long[] a = new long[t];//担保单位id
             double[] dbvalidcredit = new double[t]; // 担保单位可用信用金额
             double[] dbvalidassure = new double[t]; // 担保单位可用担保金额
             double[] dbappamount = new double[t];//担保单位替别人担保的金额
             long[] dbcreditresult = new long[t];//担保单位信用结果
             long[] dbassureresult = new long[t];//担保单位担保结果
             CreditLimitInfo clinfo=new CreditLimitInfo();
             clinfo=dao.check(ccrinfo.getClientID());
             CreditProductDAO cpDao = new CreditProductDAO();
             CreditProductInfo cpInfo = new CreditProductInfo();
             cpInfo = cpDao.findByCreditTypeID(ccrinfo.getCreditProductTypeID());
             cinfo.setCreditProductTypeID(ccrinfo.getCreditProductTypeID());
             cinfo.setClientID(ccrinfo.getClientID());
             cinfo.setClientname(ccrinfo.getClientname());
             erate = cpInfo.getEngrossRate() ;//得到产品比例
             cinfo.setProductRate(erate);
             cinfo.setCreditRate(ccrinfo.getCreditRate());
             cinfo.setAmount(ccrinfo.getAmount());
             if(clinfo!=null)
             {
              appcreditamount =DataFormat.formatDouble(ccrinfo.getAmount() * ccrinfo.getCreditRate()/ 100,2);
                    
              appvalidcredit = this.getValidCreditAmount(ccrinfo.getClientID());
              cinfo.setValidcreditamount(appvalidcredit);
                if (DataFormat.formatDouble(appcreditamount * erate,2) - appvalidcredit <= 0) {
                    cinfo.setCreditresult(1);//未超过
                } else
                    cinfo.setCreditresult(2);//超过

                camount = clinfo.getCreditAmount();//得到授信额度设置中的信用额度
                cinfo.setJkcreditamount(camount);//该客户作为借款单位授信总金额信用部分
                cinfo.setUsecreditamount(getApplyCreditAmount(ccrinfo
                        .getClientID())
                        + getuseCreditAmount(ccrinfo.getClientID()));

                appassureamount =DataFormat.formatDouble( ccrinfo.getAmount()*(100 - ccrinfo.getCreditRate()) / 100,2);//借款单位申请担保金额
                appvalidassure = getValidAssureAmount(ccrinfo.getClientID());
                cinfo.setValidassureamount(appvalidassure);
                if (DataFormat.formatDouble(appassureamount * erate,2) - appvalidassure <= 0)
                    cinfo.setAssureresult(1);
                else
                    cinfo.setAssureresult(2);
                if (cinfo.getCreditresult() == 2
                        || cinfo.getAssureresult() == 2)
                    result = false;
                aAmount =DataFormat.formatDouble(clinfo.getAssureAmount(),2);
                cinfo.setJkassureamount(aAmount);
                cinfo.setUseassureamount(getApplyAssureAmount(ccrinfo
                        .getClientID())
                        + getuseAssureAmount(ccrinfo.getClientID()));
             }else cinfo.setResultID(3);
                //*******以下是担保人
            if (ccrinfo.getAssureClientID() != null
                        && ccrinfo.getAssureClientID().length > 0) 
               {
                    a = ccrinfo.getAssureClientID();
                    cinfo.setAssureClientID(a);
                    cinfo.setAssureClientName(ccrinfo.getAssureClientName());
                    cinfo.setAssureRate(ccrinfo.getAssureRate());
                    cinfo.setCreditTypeID(ccrinfo.getCreditTypeID());
                    for(int c=0;c<t;c++){
                        if(dao.check(a[c])==null)
                          assurecheck=false;  
                    }
                    
                    if(assurecheck==true && cinfo.getResultID()<0)//每个担保单位的授信设置都是有效的并且申请单位也是有效的
                    {
                           
                       for (int i = 0; i < t; i++) 
                      {
                       
                        dbappamount[i] = ccrinfo.getAmount()
                                * ccrinfo.getAssureRate()[i] / 100;//担保单位替别人担保的金额
                        dbvalidcredit[i] = getValidCreditAmount(a[i]); //担保单位可用信用金额
                        dbvalidassure[i] = getValidAssureAmount(a[i]);//担保单位可用担保金额
                        dbcamount[i] = dao.check(a[i]).getCreditAmount();//担保单位授信额度设置中的信用金额
                        
                        dbaAmount[i] = dao.check(a[i]).getAssureAmount();//担保单位授信额度设置中的担保金额
                        dbusecreditamount[i] = getApplyCreditAmount(a[i])
                                + getuseCreditAmount(a[i]);
                        dbuseassureamount[i] = getApplyAssureAmount(a[i])
                                + getuseAssureAmount(a[i]);
                        if (ccrinfo.getCreditTypeID()[i] == 1)//担保单位占用的是自己的信用金额
                        {
                            if (DataFormat.formatDouble(dbappamount[i] * erate,2) - dbvalidcredit[i] <= 0) {
                               // System.out.println("dbappamount-dbvalidcredit["+i+"]");
                             //   System.out.println("dbvalidcredit["+i+"]="+dbvalidcredit[i]);
                                dbcreditresult[i] = 1; //未超过
                            } else
                                dbcreditresult[i] = 2;
                            dbassureresult[i] = 1;//未超过
                        } else if (ccrinfo.getCreditTypeID()[i] == 2)//担保单位占用的是自己的担保金额
                        { System.out.println("dbappamount-dbvalidassure=="+DataFormat.formatDouble(dbappamount[i] * erate,2));
                        System.out.println("dbvalidassure["+i+"]="+dbvalidassure[i]);
                            if (DataFormat.formatDouble(dbappamount[i] * erate,2) - dbvalidassure[i] <= 0) {
                               System.out.println("dbappamount-dbvalidassure=="+DataFormat.formatDouble(dbappamount[i] * erate,2));
                              System.out.println("dbvalidassure["+i+"]="+dbvalidassure[i]);
                                dbassureresult[i] = 1;//未超过
                            } else
                                dbassureresult[i] = 2;
                            dbcreditresult[i] = 1;
                        }
                        if (dbcreditresult[i] == 2 || dbassureresult[i] == 2)
                            result = false;
                       
                      }
                    

                      cinfo.setDbcreditresult(dbcreditresult);
                      cinfo.setDbassureresult(dbassureresult);
                      cinfo.setDbusecreditamount(dbusecreditamount);
                      cinfo.setDbuseassureamount(dbuseassureamount);
                      cinfo.setDbcreditamount(dbcamount);
                      cinfo.setDbassureamount(dbaAmount);
                      cinfo.setDbvalidcreditamount(dbvalidcredit);
                      cinfo.setDbvalidassureamount(dbvalidassure);
                    }
                    else cinfo.setResultID(3);
               }
                if (result == false && cinfo.getResultID()<0)
                    cinfo.setResultID(2);//超过授信额度
                else if(result == true && cinfo.getResultID()<0)
                    cinfo.setResultID(1);//未超过授信额度
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cinfo;
    }
 public long savecreditcheckreport(CreditCheckReportInfo reinfo)throws SQLException {
     long id=-1;
     try{
         CreditCheckReportDAO dao=new CreditCheckReportDAO();
         id=dao.savecreditcheckreport(reinfo);
         
     }catch(Exception e){
         e.printStackTrace();
     }
     
     return id;
 }
 public CreditCheckReportInfo findCheckReportByID(long id)throws Exception {
     CreditCheckReportInfo ccrinfo=new CreditCheckReportInfo();
     try{
         CreditCheckReportDAO dao=new CreditCheckReportDAO();
         ccrinfo=dao.findCheckReportByID(id);
     }catch(Exception e){
         e.printStackTrace();
     }
     
     return ccrinfo;
     
 }
 //客户作为借款人的指定产品类型正在申请的贷款信息,信用比例 
public Collection getApplyCreditInfo(long clientID,long creditproducttype)throws Exception {
      Collection al=new ArrayList();
      Connection conn=null;
      try{
          conn=Database.getConnection();
          CreditCheckReportDAO dao=new CreditCheckReportDAO();
          al=dao.getApplyCreditAmount(clientID,creditproducttype,conn);
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          if(conn!=null){
              conn.close();
              conn=null;
          }
      }
      return al;
   }
//客户作为借款人的指定产品类型正在申请的贷款信息,担保比例
public Collection getApplyAssureInfo(long clientID,long creditproducttype)throws Exception {
    Collection al=new ArrayList();
    Connection conn=null;
    try{
        conn=Database.getConnection();
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        al=dao.getApplyAssureAmount(clientID,creditproducttype,conn);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
            conn=null;
        }
    }
    return al;
 }
//得到客户作为借款人指定产品类型的客户合同信息,还款金额,信用比例
public Collection getBalanceCreditInfoAsApply(long clientID,long creditproducttype)throws Exception {
    Collection al=new ArrayList();
    Connection conn=null;
    try{
        conn=Database.getConnection();
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        al=dao.getBalanceCreditAmountAsApply(clientID,creditproducttype,conn);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
            conn=null;
        }
    }
    return al;
 }
//作为借款人得到指定产品类型的客户合同信息,还款金额,担保比例
public Collection getBalanceAssureInfoAsApply(long clientID,long creditproducttype)throws Exception {
    Collection al=new ArrayList();
    Connection conn=null;
    try{
        conn=Database.getConnection();
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        al=dao.getBalanceAssureAmountAsApply(clientID,creditproducttype,conn);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
            conn=null;
        }
    }
    return al;
 }
//客户作为担保人的指定产品类型正在申请的贷款信息,信用比例
public Collection getAssureCreditInfo(long clientID,long creditproducttype)throws Exception {
    Collection al=new ArrayList();
    Connection conn=null;
    try{
        conn=Database.getConnection();
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        al=dao.getAssureCreditAmount(clientID,creditproducttype,conn);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
            conn=null;
        }
    }
    return al;
 }
//客户作为担保人的指定产品类型正在申请的贷款信息,担保比例
public Collection getAssureAssureInfo(long clientID,long creditproducttype)throws Exception {
    Collection al=new ArrayList();
    Connection conn=null;
    try{
        conn=Database.getConnection();
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        al=dao.getAssureAssureAmount(clientID,creditproducttype,conn);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
            conn=null;
        }
    }
    return al;
 }
//得到客户作为担保人指定产品类型的客户合同信息,还款金额,信用比例
public Collection getBalancecreditInfoAsAssure(long clientID,long creditproducttype)throws Exception {
    Collection al=new ArrayList();
    Connection conn=null;
    try{
        conn=Database.getConnection();
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        al=dao.getBalancecreditAmountAsAssure(clientID,creditproducttype,conn);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
            conn=null;
        }
    }
    return al;
 }
//得到客户作为担保人指定产品类型的客户合同信息,还款金额,担保比例
public Collection getBalanceAssureInfoAsAssure(long clientID,long creditproducttype)throws Exception {
    Collection al=new ArrayList();
    Connection conn=null;
    try{
        conn=Database.getConnection();
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        al=dao.getBalanceAssureAmountAsAssure(clientID,creditproducttype,conn);
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
            conn=null;
        }
    }
    return al;
 }

public Collection findByMultiOption(CheckReportQueryInfo qInfo)
        throws ITreasuryDAOException {
    ArrayList v = null;
    try{
        CreditCheckReportDAO dao=new CreditCheckReportDAO();
        v=(ArrayList)dao.findByMultiOption(qInfo);
    }catch(Exception e){
        e.printStackTrace();
    }
    
    
    return v;
}
/*
 * 检查贷款申请和检查报告是否匹配
 * @param 检查报告id loanid
 * return 0:不匹配  1：检查报告的结果未超过 2：检查报告的结果超过
 * weihuang
 */
public long checkLoanAndReportMatch(long loanid,long reportid)throws Exception{
    long r=-1;
   try{
       LoanApplyDao ldao=new LoanApplyDao();
       LoanApplyInfo lainfo=new LoanApplyInfo();
       CreditCheckReportInfo ccrinfo=new CreditCheckReportInfo();
       AssureInfo assinfo=new AssureInfo();
       lainfo=ldao.findBorrowInfoByID(loanid);
       ccrinfo=this.findCheckReportByID(reportid);
       if(ccrinfo.getCreditProductTypeID()==this.untypeTrans(lainfo.getTypeID())&&
          ccrinfo.getClientID()==lainfo.getBorrowClientID()&& DataFormat.formatDouble(ccrinfo.getAmount(),2)==DataFormat.formatDouble(lainfo.getLoanAmount(),2))
       { 
           long[] a=new long[ccrinfo.getAssureClientID().length];
           double[] b=new double[ccrinfo.getAssureClientID().length];
           b=ccrinfo.getAssureRate();
           a=ccrinfo.getAssureClientID();
           Collection al=(ArrayList)ldao.findAssureByID(loanid);
           Iterator it=al.iterator();
           
               while(it.hasNext())
               {boolean c=false;
                   assinfo=(AssureInfo)it.next();
                   for(int i=0;i<a.length;i++)
                   {
                     if(a[i]==assinfo.getClientID()&& DataFormat.formatDouble(b[i]/100*ccrinfo.getAmount(),2)==DataFormat.formatDouble(assinfo.getAmount(),2))
                    {
                     c=true;
                    }
                   }
                   if(c==false)
                   {
                       r=0;
                       break;
                   }
                       
               }    
               if(r<0)
                   r=ccrinfo.getResultID();
          
       }else r=0;
       
   }catch(Exception e){
       e.printStackTrace();
   }
    
    return r;
    
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
public long findIsControlProduct(long loantype)throws Exception{
    long r=-1;
    try{
        CreditProductDAO dao=new CreditProductDAO();
        r=dao.findIsControlProduct(loantype);
        
    }catch(Exception e){
        e.printStackTrace();
    }
    return r;
}

public String getCode()throws Exception
{
	String strCreditCdoe = "";
	synchronized(lockObject)
	{
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		strCreditCdoe = simpleFormat.format(Env.getSystemDateTime());
		Thread.sleep(1000);	
	}
	return strCreditCdoe;
}
public static void main(String args[]) {
      CreditBiz bean1 = new CreditBiz();
    try {
      long B = 2;
     // System.out.println(bean1.getApplyCreditAmount(5));
    }
    catch (Exception e) {
      
      e.printStackTrace();
    }
  }
}
