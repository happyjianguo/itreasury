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
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;

import java.util.*;
import java.sql.*;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditBean {
    
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

  public CreditProductInfo findProductInfoByCreditTypeID(long CreditTypeID) throws
      Exception {
    CreditProductInfo info = null;
    CreditProductDAO dao = new CreditProductDAO();

    try {
      info = dao.findByCreditTypeID(CreditTypeID);
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

        if (infoTemp != null && infoTemp.getId() >0) {
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
          info.setAmount(info.getAmount() + info.getCreditChange());
        }
        else if (info.getAddOrMinus() == 2) {
          info.setAmount(info.getAmount() - info.getCreditChange());
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
      info.setCanUseAmount(info.getAmount() - info.getApplyAmount() -
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
    //Logger Log = Logger.getLogger(this.getClass().getName());
    //PropertyConfigurator.configure ( "loanLog4j.properties" ) ;
    try {
      conn.setAutoCommit(false);
      System.out.println("idlength=="+id.length);
      for (int i = 0; i < id.length; i++) {
        if (id[i] > 0) {
          CreditLimitInfo info = (CreditLimitInfo) dao.findByID(id[i],
              CreditLimitInfo.class);
    System.out.println("i====="+i);
          if (info.getStatusID() == LOANConstant.CreditStatus.SUBMIT ||
              info.getStatusID() == LOANConstant.CreditStatus.CHECK) {
              System.out.println("1111111111结束时间晚于当前时间=="+Env.getSystemDate());
              System.out.println("222222222结束时间晚于当前时间=="+info.getEndDate());
            //如果该条授信额度信息的结束时间早于当前时间，则不对它进行激活操作
            if (!Env.getSystemDate().after(info.getEndDate())) {
                System.out.println("#33333333333333===========================");
              //判断将用户提交的授信额度变为已激活 还是 已批准 状态
              if (DataFormat.getDateTime(conn).before(info.getStartDate())) {
                lStatus = LOANConstant.CreditStatus.CHECK;
                System.out.println("#44444444444===========================");
              }
              else {
                lStatus = LOANConstant.CreditStatus.ACTIVE;
                System.out.println("########44444--111111lStatus=="+lStatus);
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
                    System.out.println("#55555555555555================="+cinfo.getId());
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
            }else lStatus=LOANConstant.CreditStatus.OVERTIME;
            System.out.println("6666666@@@@@@@@@@@@@将状态lStatus=="+lStatus);
            //将用户提交的授信额度变为已激活 或 已批准 状态
            CreditLimitInfo info2 = new CreditLimitInfo();
            info2.setId(id[i]);
            info2.setStatusID(lStatus);
            dao.update(info2);
            System.out.println("7777777777=================");
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
      System.out.println("#8888888888===========================");
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
    }finally{
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
      Logger Log = Logger.getLogger(this.getClass().getName());
      //PropertyConfigurator.configure ( "loanLog4j.properties" ) ;
      CreditLimitDAO dao = new CreditLimitDAO();
    long[] id = null;
    Log.info("start============");
    id = dao.findForAutoActive(ts);
    active(id, -2);
    dao.updateOverTime();
  }
  
  public static void main(String args[]) {
    CreditBean bean1 = new CreditBean();
    try {
      long B = 2;
      //System.out.println(bean1.getValidCreditAmount(23));
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
