package com.iss.itreasury.settlement.query.bizlogic;

import com.iss.itreasury.settlement.query.Dao.QueryGLDao;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

public class QueryGLBiz
{
  QueryGLDao dao = new QueryGLDao();

  public PagerInfo getTodayLoanAcountBalace(long lOfficeID, long lCurrencyID, Timestamp tsDate, String strBankCodeStart, String strBankCodeEnd, long lUnit,String lCurrencyType) throws Exception
  {
    PagerInfo pagerInfo = null;
    String sql = null;
    Map map=new HashedMap();
    map.put("currency", lCurrencyType);
    
    try
    {
      pagerInfo = new PagerInfo();
      sql = this.dao.queryGLSQL(lOfficeID, lCurrencyID, tsDate, strBankCodeStart, strBankCodeEnd, lUnit);
      pagerInfo.setSqlString(sql);
      pagerInfo.setUsertext("期初余额合计{m_dStartBalance};借方金额合计{m_dDebitAmount};借方张数合计{m_lDebitNumber}#n;贷方金额合计{m_dLoanAmount};贷方张数合计{m_lCreditNumber}#n;期末余额合计{m_dEndBalance}");
      pagerInfo.setExtensionMothod(QueryGLBiz.class, "resultSetHandle",map);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new Exception("====>查询异常", e);
    }
    return pagerInfo;
  }

  public ArrayList resultSetHandle(ResultSet rs,Map map) throws Exception
  {
    ArrayList resultList = new ArrayList();
    ArrayList cellList = null;
    ResultPagerRowInfo rowInfo = null;
    
   String currencyType= (String) map.get("currency");
    
    String m_strBankCode = " ";
    String m_strBankName = "";
    String m_strRootSubject = "";
    double m_dStartBalance = 0.0D;
    double m_dDebitAmount = 0.0D;
    long m_lDebitNumber = -1L;
    double m_dLoanAmount = 0.0D;
    long m_lCreditNumber = -1L;
    double m_dEndBalance = 0.0D;
    long subjectId = -1L;
    try
    {
      if (rs != null)
      {
        while (rs.next())
        {
          m_strBankCode = rs.getString("sbankCode");
          m_strBankName = rs.getString("sbankName");
          m_strRootSubject = rs.getString("ssegmentcode");
          m_dStartBalance = rs.getDouble("m_dStartBalance");
          m_dDebitAmount = rs.getDouble("m_dDebitAmount");
          m_lDebitNumber = rs.getLong("m_lDebitNumber");
          m_dLoanAmount = rs.getDouble("m_dLoanAmount");
          m_lCreditNumber = rs.getLong("m_lCreditNumber");
          m_dEndBalance = rs.getDouble("m_dEndBalance");
          subjectId = rs.getLong("nSubjectId");

          String startBalance = "";
          if ((m_dStartBalance > 0.0D) || (m_dStartBalance < 0.0D)) {
            startBalance = currencyType + DataFormat.formatAmount(m_dStartBalance);
          }
          else {
            startBalance = currencyType+"0.00";
          }

          String DebitAmout = "";
          if ((m_dDebitAmount > 0.0D) || (m_dDebitAmount < 0.0D))
          {
            DebitAmout = currencyType + DataFormat.formatListAmount(m_dDebitAmount);
          }
          else {
            DebitAmout = currencyType+"0.00";
          }

          String loanAmount = "";
          if ((m_dLoanAmount > 0.0D) || (m_dLoanAmount < 0.0D))
          {
            loanAmount = currencyType + DataFormat.formatListAmount(m_dLoanAmount);
          }
          else {
            loanAmount =currencyType+"0.00";
          }

          String endBalance = "";
          if (m_dEndBalance < 0.0D)
          {
            endBalance =currencyType + "-"+ DataFormat.formatListAmount(Math.abs(m_dEndBalance));
          }
          else {
            endBalance = currencyType + DataFormat.formatListAmount(m_dEndBalance);
          }

          cellList = new ArrayList();
          PagerTools.returnCellList(cellList, m_strBankCode);
          PagerTools.returnCellList(cellList, m_strBankName);
          PagerTools.returnCellList(cellList, m_strRootSubject);
          PagerTools.returnCellList(cellList, startBalance);
          if (m_dDebitAmount > 0.0D) {
            PagerTools.returnCellList(cellList, DebitAmout + "#" + subjectId + "#" + m_strRootSubject);
          }
          else
          {
            PagerTools.returnCellList(cellList, DebitAmout + "," + ",");
          }
          PagerTools.returnCellList(cellList, Long.valueOf(m_lDebitNumber));
          if (m_dLoanAmount > 0.0D) {
            PagerTools.returnCellList(cellList, loanAmount + "#" + subjectId + "#" + m_strRootSubject);
          }
          else
          {
            PagerTools.returnCellList(cellList, loanAmount + "," + ",");
          }
          PagerTools.returnCellList(cellList, Long.valueOf(m_lCreditNumber));
          PagerTools.returnCellList(cellList, endBalance);

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