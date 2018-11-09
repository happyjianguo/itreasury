package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.query.Dao.QuerySubjectBalanceDao;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * 科目余额查询
 * @author songwenxiao
 *
 */

public class QuerySubjectBalanceBiz
{
  QuerySubjectBalanceDao dao = new QuerySubjectBalanceDao();

  public PagerInfo findSubjectBalance(long lOfficeID,long lCurrencyID,Timestamp tsStart,Timestamp tsEnd,String strSubjectStart,String strSubjectEnd,String lCurrencyType, long lUnit) throws Exception
  {
    PagerInfo pagerInfo = null;
    String sql = null;
    Map map=new HashedMap();
    map.put("currency", lCurrencyType);
    map.put("unit", lUnit);
    try
    {
      pagerInfo = new PagerInfo();

      sql = this.dao.querySubjectBalanceSQL(lOfficeID, lCurrencyID, tsStart, tsEnd, strSubjectStart, strSubjectEnd, lUnit);
      pagerInfo.setSqlString(sql);
      String temp = "";
    /*  if (lTypeID == 1L) {
        temp = "借方合计金额";
      }
      else {
        temp = "贷方合计金额";
      }*/
     
      pagerInfo.setUsertext(" 合计{nvl(mDebitBalance,0)-nvl(mCreditBalance,0)}");
      
      pagerInfo.setExtensionMothod(QuerySubjectBalanceBiz.class, "resultSetHandle",map);
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
    Long lUnit=(Long) map.get("unit");
    
    Timestamp m_tsDate = null;
    String m_strSubject = "";
    String m_strName="";
    double m_dSumBalance = 0.00;
    double m_dBalance = 0.00;
    
    try
    {
      conn = Database.getConnection();
      if (rs != null)
      {
        while (rs.next())
        {
          m_strSubject = rs.getString("sGlSubjectCode"); 
          m_strName = rs.getString("sSubjectName"); 
          m_tsDate = rs.getTimestamp("dtGlDate"); 
          
          String tsDate = DataFormat.getDateString(m_tsDate);
          
          long lSubjectTypeID = rs.getLong("nSubjectType");
			if ( SETTConstant.SubjectAttribute.getDirection(lSubjectTypeID) == SETTConstant.DebitOrCredit.CREDIT )
			{
				m_dBalance = rs.getDouble("mCreditBalance") - rs.getDouble("mDebitBalance"); 
			}
			else
			{
				m_dBalance = rs.getDouble("mDebitBalance") - rs.getDouble("mCreditBalance"); 
			}
			String balance="";
          
			if (m_dBalance < 0)
          	{
				balance=currencyType+"-"+DataFormat.formatListAmount((0 - m_dBalance));
               
          	}else
          	{
          		balance= currencyType+DataFormat.formatListAmount(m_dBalance);
            }
         

          cellList = new ArrayList();
          PagerTools.returnCellList(cellList, tsDate);
          PagerTools.returnCellList(cellList, m_strSubject);
          PagerTools.returnCellList(cellList, m_strName);
          PagerTools.returnCellList(cellList, balance);

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