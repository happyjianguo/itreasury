package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.settlement.query.Dao.QueryGLDao;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

public class QueryGLDetailUnderSubjectBiz
{
  QueryGLDao dao = new QueryGLDao();

  public PagerInfo findGLDetailsUnderSubject(String strRootAccount, String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd,String lCurrencyType) throws Exception
  {
    PagerInfo pagerInfo = null;
    String sql = null;
    Map map=new HashedMap();
    map.put("currency", lCurrencyType);
    try
    {
      pagerInfo = new PagerInfo();

      sql = this.dao.queryGLDetailUnderSubSQL(strRootAccount, strAccount, strTransNo, lOfficeID, lCurrencyID, lTypeID, lTransTypeID, tsDateStart, tsDateEnd);
      pagerInfo.setSqlString(sql);

      pagerInfo.setExtensionMothod(QueryGLDetailUnderSubjectBiz.class, "resultSetHandle",map);
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
    Connection conn = null;
    ArrayList resultList = new ArrayList();
    ArrayList cellList = null;
    ResultPagerRowInfo rowInfo = null;
    
    String currencyType= (String) map.get("currency");
    
    Timestamp m_tsExecute = null;
    String m_strTransNo = "";
    String m_strTransactionType = "";
    long m_lTransactionTypeID = -1L;
    String m_strAccountRecord = "";
    double m_dAmount = 0.0D;
    String m_strDirection = "";
    long m_lStatusID = -1L;
    String m_strAbstract = "";
    String m_strInputUser = "";
    String m_strCheckUser = "";
    long m_lDirectionID = -1L;
    try
    {
      conn = Database.getConnection();
      if (rs != null)
      {
        while (rs.next())
        {
          m_tsExecute = rs.getTimestamp("dtExecute");
          m_strTransNo = rs.getString("sTransNo");
          m_lTransactionTypeID = rs.getLong("nTransactionTypeID");
          m_strAccountRecord = rs.getString("sSubjectCode");
          m_dAmount = rs.getDouble("mAmount");
          m_lDirectionID = rs.getLong("nTransDirection");
          m_lStatusID = rs.getLong("nStatusID");
          m_strAbstract = rs.getString("sAbstract");
          m_strInputUser = rs.getString("sInputUserName");
          m_strCheckUser = rs.getString("sCheckUserName");

          String tsExecute = DataFormat.formatDate(m_tsExecute);
          m_strTransactionType = SETTConstant.TransactionType.getName(m_lTransactionTypeID);
          m_strDirection = SETTConstant.DebitOrCredit.getName(m_lDirectionID);

          String Amount = currencyType + DataFormat.formatListAmount(m_dAmount);

          if (m_lDirectionID == 2L)
          {
            m_strDirection = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + m_strDirection;
          }

          String m_strStatus = DataFormat.formatString(SETTConstant.EntryStatus.getName(m_lStatusID));

          if ((m_strInputUser == null) || (m_strInputUser.length() <= 0))
          {
            m_strInputUser = "机制";
          }
          if ((m_strCheckUser == null) || (m_strCheckUser.length() <= 0))
          {
            m_strCheckUser = "机核";
          }

          cellList = new ArrayList();
          PagerTools.returnCellList(cellList, tsExecute);
          PagerTools.returnCellList(cellList, m_strTransNo);
          PagerTools.returnCellList(cellList, m_strTransactionType);
          PagerTools.returnCellList(cellList, m_strAccountRecord);
          PagerTools.returnCellList(cellList, Amount);
          PagerTools.returnCellList(cellList, m_strDirection);
          PagerTools.returnCellList(cellList, m_strStatus);
          PagerTools.returnCellList(cellList, m_strAbstract);
          PagerTools.returnCellList(cellList, m_strInputUser);
          PagerTools.returnCellList(cellList, m_strCheckUser);

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