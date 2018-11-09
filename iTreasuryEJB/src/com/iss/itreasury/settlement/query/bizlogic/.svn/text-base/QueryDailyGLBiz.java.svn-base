package com.iss.itreasury.settlement.query.bizlogic;

import com.iss.itreasury.settlement.bizdelegation.GLQueryDelegation;
import com.iss.itreasury.settlement.query.Dao.QueryGLDao;
import com.iss.itreasury.settlement.query.paraminfo.AccountRecordConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.AccountRecordInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;

public class QueryDailyGLBiz
{
  QueryGLDao dao = new QueryGLDao();

  public PagerInfo getDailyGL(AccountRecordConditionInfo conditionInfo, long lUnit,String lCurrencyType) throws Exception
  {
    PagerInfo pagerInfo = null;
    String sql = null;
    try
    {
      pagerInfo = new PagerInfo();

      sql = this.dao.findDailyAccountRecord(conditionInfo);
      pagerInfo.setSqlString(sql);
      Map map = new HashedMap();
      map.put("condition", conditionInfo);
      map.put("unit", Long.valueOf(lUnit));
      map.put("currency", lCurrencyType);

      pagerInfo.setExtensionMothod(QueryDailyGLBiz.class, "resultSetHandle", map);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new Exception("====>≤È—Ø“Ï≥£", e);
    }
    return pagerInfo;
  }

  public ArrayList resultSetHandle(ResultSet rs, Map map) throws Exception
  {
    ArrayList resultList = new ArrayList();
    ArrayList cellList = null;
    ResultPagerRowInfo rowInfo = null;

    AccountRecordInfo accountRecordInfo = null;
    GLQueryDelegation delegation = new GLQueryDelegation();

    long id = -1L;
    String m_strAccount = "";
    double m_dStartBalance = 0.0D;
    String m_strName = "";
    long m_lDebitNumber = 0L;
    double m_dDebitAmount = 0.0D;
    long m_lCreditNumber = 0L;
    double m_dLoanAmount = 0.0D;
    double m_dEndBalance = 0.0D;

    AccountRecordConditionInfo conditionInfo = (AccountRecordConditionInfo)map.get("condition");
    long lUnit = ((Long)map.get("unit")).longValue();
    String currencyType= (String) map.get("currency");
    try
    {
      if (rs != null)
      {
        while (rs.next())
        {
          id = rs.getLong("id");
          m_strAccount = rs.getString("m_strAccount");
          conditionInfo.setId(id);
          conditionInfo.setSubjectCode(m_strAccount);
          accountRecordInfo = delegation.getDailyAccountRecord(conditionInfo);

          String printString = "¥Ú”°";

          String strAccount = accountRecordInfo.m_strAccount;

          String strName = accountRecordInfo.m_strName;

//          String startBalance = "";
//          if (accountRecordInfo.m_dStartBalance < 0.0D)
//          {
//            startBalance = "<font color=red>" + currencyType+ "-"+ DataFormat.formatDisabledAmount(-accountRecordInfo.m_dStartBalance / lUnit, 2) + "</font>";
//          }
//          else
//          {
//            startBalance = currencyType + DataFormat.formatDisabledAmount(accountRecordInfo.m_dStartBalance / lUnit, 2);
//          }
          String startDebitBalance = "";
          if (accountRecordInfo.m_dStartDebitBalance < 0.0D)
          {
        	  startDebitBalance = "<font color=red>" + currencyType+ "-"+ DataFormat.formatDisabledAmount(-accountRecordInfo.m_dStartDebitBalance / lUnit, 2) + "</font>";
          }
          else
          {
        	  startDebitBalance = currencyType + DataFormat.formatDisabledAmount(accountRecordInfo.m_dStartDebitBalance / lUnit, 2);
          }
          String startCreditBalance = "";
          if (accountRecordInfo.m_dStartCreditBalance < 0.0D)
          {
        	  startCreditBalance = "<font color=red>" + currencyType+ "-"+ DataFormat.formatDisabledAmount(-accountRecordInfo.m_dStartCreditBalance / lUnit, 2) + "</font>";
          }
          else
          {
        	  startCreditBalance = currencyType + DataFormat.formatDisabledAmount(accountRecordInfo.m_dStartCreditBalance / lUnit, 2);
          }

          String DebitAmout = "";
          if(m_dDebitAmount < 0){
        	  DebitAmout = (accountRecordInfo.m_lDebitNumber > 0L) && (accountRecordInfo.m_dDebitAmount == 0.0D) ? "0.00" : ("<font color=red>" + DataFormat.formatDisabledAmount(accountRecordInfo.m_dDebitAmount / lUnit, 1) + "</font>");
          }else{
        	  DebitAmout = (accountRecordInfo.m_lDebitNumber > 0L) && (accountRecordInfo.m_dDebitAmount == 0.0D) ? "0.00" : DataFormat.formatDisabledAmount(accountRecordInfo.m_dDebitAmount / lUnit, 1);
          }
          String loanAmount = "";
          if(m_dLoanAmount < 0){
        	  loanAmount = (accountRecordInfo.m_lCreditNumber > 0L) && (accountRecordInfo.m_dLoanAmount == 0.0D) ? "0.00" : ("<font color=red>" + DataFormat.formatDisabledAmount(accountRecordInfo.m_dLoanAmount / lUnit, 1) + "</font>");
          }else{
        	  loanAmount = (accountRecordInfo.m_lCreditNumber > 0L) && (accountRecordInfo.m_dLoanAmount == 0.0D) ? "0.00" : DataFormat.formatDisabledAmount(accountRecordInfo.m_dLoanAmount / lUnit, 1);
          }
          
          String DebitNumber = DataFormat.formatListLong(accountRecordInfo.m_lDebitNumber);

          String CreditNumber = DataFormat.formatListLong(accountRecordInfo.m_lCreditNumber);

//          String endBalance = "";
//          if (accountRecordInfo.m_dEndBalance < 0.0D)
//          {
//            endBalance = "<font color=red>" + currencyType +"-"+ DataFormat.formatDisabledAmount(-accountRecordInfo.m_dEndBalance / lUnit, 2) + "</font>";
//          }
//          else
//          {
//            endBalance = currencyType + DataFormat.formatDisabledAmount(accountRecordInfo.m_dEndBalance / lUnit, 2);
//          }
          String endDebitBalance = "";
          if (accountRecordInfo.m_dEndDebitBalance < 0.0D)
          {
        	  endDebitBalance = "<font color=red>" + currencyType +"-"+ DataFormat.formatDisabledAmount(-accountRecordInfo.m_dEndDebitBalance / lUnit, 2) + "</font>";
          }
          else
          {
        	  endDebitBalance = currencyType + DataFormat.formatDisabledAmount(accountRecordInfo.m_dEndDebitBalance / lUnit, 2);
          }
          String endCreditBalance = "";
          if (accountRecordInfo.m_dEndCreditBalance < 0.0D)
          {
        	  endCreditBalance = "<font color=red>" + currencyType +"-"+ DataFormat.formatDisabledAmount(-accountRecordInfo.m_dEndCreditBalance / lUnit, 2) + "</font>";
          }
          else
          {
        	  endCreditBalance = currencyType + DataFormat.formatDisabledAmount(accountRecordInfo.m_dEndCreditBalance / lUnit, 2);
          }
          
          long id2 = accountRecordInfo.getId();
          String account = accountRecordInfo.m_strAccount;

          cellList = new ArrayList();
          PagerTools.returnCellList(cellList, printString + "," + accountRecordInfo.getId() + "," + accountRecordInfo.m_strAccount);
          PagerTools.returnCellList(cellList, strAccount + "," + accountRecordInfo.getId() + "," + accountRecordInfo.m_strAccount);
          PagerTools.returnCellList(cellList, strName);
          PagerTools.returnCellList(cellList, startDebitBalance);
          PagerTools.returnCellList(cellList, startCreditBalance);
          PagerTools.returnCellList(cellList, DebitAmout + "#" + id2 + "#" + account);
          PagerTools.returnCellList(cellList, loanAmount + "#" + id2 + "#" + account);
          PagerTools.returnCellList(cellList, DebitNumber);
          PagerTools.returnCellList(cellList, CreditNumber);
          PagerTools.returnCellList(cellList, endDebitBalance + "#" + accountRecordInfo.getId() + "#" + accountRecordInfo.m_strAccount);
          PagerTools.returnCellList(cellList, endCreditBalance + "#" + accountRecordInfo.getId() + "#" + accountRecordInfo.m_strAccount);


          rowInfo = new ResultPagerRowInfo();
          rowInfo.setCell(cellList);
          rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

          resultList.add(rowInfo);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new IException(e.getMessage());
    }

    return resultList;
  }
}