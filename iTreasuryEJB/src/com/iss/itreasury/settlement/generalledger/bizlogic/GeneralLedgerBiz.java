package com.iss.itreasury.settlement.generalledger.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.generalledger.dao.QueryGLDao;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionTempInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneralLedgerBiz 
{
	  QueryGLDao dao = new QueryGLDao();

	  public PagerInfo queryGeneralLedger(long officeID, long currencyID , long transactionType) throws Exception
	  {
	    PagerInfo pagerInfo = null;
	    String sql = null;
	    try
	    {
	      pagerInfo = new PagerInfo();

	      sql = dao.findAll(officeID, currencyID, transactionType);
	      pagerInfo.setSqlString(sql);
	    
	      pagerInfo.setExtensionMothod(GeneralLedgerBiz.class, "resultSetHandle");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw new Exception("====>查询异常", e);
	    }
	    return pagerInfo;
	  }
	  
	  
	  public PagerInfo queryAllUnUseAndUsedGenerlLegerInfo(long officeID, long currencyID , long transactionType) throws Exception
	  {
	    PagerInfo pagerInfo = null;
	    String sql = null;
	    try
	    {
	      pagerInfo = new PagerInfo();

	      sql = dao.findAllUnUseAndUsed(officeID, currencyID, transactionType);
	      pagerInfo.setSqlString(sql);
	    
	      pagerInfo.setExtensionMothod(GeneralLedgerBiz.class, "resultSetHandle2");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw new Exception("====>查询异常", e);
	    }
	    return pagerInfo;
	  }
	  
	  public ArrayList resultSetHandle(ResultSet rs) throws Exception
	  {
	    ArrayList resultList = new ArrayList();
	    ArrayList cellList = null;
	    ResultPagerRowInfo rowInfo = null;
	    GLEntryDefinitionInfo info = null;
	    int index=0;
	    try
	    {
	      if (rs != null)
	      {
	        while (rs.next())
	        {
	        	
	        	info = new GLEntryDefinitionInfo();

                info.setOfficeID(rs.getLong("NOFFICEID"));
                info.setCurrencyID(rs.getLong("NCURRENCYID"));
                info.setTransactionType(rs.getLong("NTRANSACTIONTYPE"));
				info.setSubTransactionType(rs.getLong("nSubTransactionType"));                
                info.setCapitalType(rs.getLong("NCAPITALTYPE"));
                info.setEntryType(rs.getLong("NENTRYTYPE"));
                info.setDirection(rs.getLong("NDIRECTION"));
                info.setSubjectType(rs.getLong("NSUBJECTTYPE"));
                String subjectCode=rs.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
					info.setSubjectCode(subjectCode);
                }
                info.setAmountDirection(rs.getLong("NAMOUNTDIRECTION"));
                info.setAmountType(rs.getLong("NAMOUNTTYPE"));
                info.setOfficeType(rs.getLong("NOFFICETYPE"));
                info.setID(rs.getLong("ID"));
                int temp=++index;
                
	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, temp + "," + info.getID());
	          PagerTools.returnCellList(cellList, info.getTransactionTypeName());
	          PagerTools.returnCellList(cellList, info.getSubTransactionTypeName());
	          PagerTools.returnCellList(cellList, info.getOfficeTypeName());
	          PagerTools.returnCellList(cellList, info.getCapitalTypeName());
	          PagerTools.returnCellList(cellList, info.getEntryTypeName());
	          PagerTools.returnCellList(cellList, info.getDirectionName());
	          PagerTools.returnCellList(cellList, info.getSubjectTypeName());
	          PagerTools.returnCellList(cellList, info.getSubjectCode());
	          PagerTools.returnCellList(cellList, info.getAmountDirectionName());
	          PagerTools.returnCellList(cellList, info.getAmountTypeName());

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

	  public ArrayList resultSetHandle2(ResultSet rs) throws Exception
	  {
	    ArrayList resultList = new ArrayList();
	    ArrayList cellList = null;
	    ResultPagerRowInfo rowInfo = null;
	    GLEntryDefinitionTempInfo tempInfo = null;
	    int index=0;
	    try
	    {
	      if (rs != null)
	      {
	        while (rs.next())
	        {
	        	
	        	tempInfo = new GLEntryDefinitionTempInfo();

	        	tempInfo.setOfficeID(rs.getLong("NOFFICEID"));
	        	tempInfo.setCurrencyID(rs.getLong("NCURRENCYID"));
	        	tempInfo.setTransactionType(rs.getLong("NTRANSACTIONTYPE"));
	        	tempInfo.setSubTransactionType(rs.getLong("nSubTransactionType"));                
	        	tempInfo.setCapitalType(rs.getLong("NCAPITALTYPE"));
	        	tempInfo.setEntryType(rs.getLong("NENTRYTYPE"));
	        	tempInfo.setDirection(rs.getLong("NDIRECTION"));
	        	tempInfo.setSubjectType(rs.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rs.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	tempInfo.setSubjectCode(subjectCode);
                }
                                
                tempInfo.setAmountDirection(rs.getLong("NAMOUNTDIRECTION"));
                tempInfo.setAmountType(rs.getLong("NAMOUNTTYPE"));
                tempInfo.setOfficeType(rs.getLong("NOFFICETYPE"));
                tempInfo.setID(rs.getLong("ID"));
                tempInfo.setNID(rs.getLong("nID"));
                tempInfo.setNOperateType(rs.getLong("nOperateType"));
                tempInfo.setInputUserID(rs.getLong("inputUserID"));
                //tempInfo.setInputDate(rs.getTimestamp("inputDate"));
                //tempInfo.setCheckUserID(rs.getLong("checkUserID"));
                //tempInfo.setCheckDate(rs.getTimestamp("checkDate"));
                tempInfo.setRowspan(rs.getLong("rowspan"));
                tempInfo.setNStatusID(rs.getLong("nStatusID"));
                
                int temp=++index;
                
	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, temp + "," + tempInfo.getID()+ "," + tempInfo.getNID());
	          PagerTools.returnCellList(cellList, tempInfo.getTransactionTypeName());
	          PagerTools.returnCellList(cellList, tempInfo.getSubTransactionTypeName());
	          PagerTools.returnCellList(cellList, tempInfo.getOfficeTypeName());
	          PagerTools.returnCellList(cellList, tempInfo.getCapitalTypeName());
	          PagerTools.returnCellList(cellList, tempInfo.getEntryTypeName());
	          PagerTools.returnCellList(cellList, tempInfo.getDirectionName());
	          PagerTools.returnCellList(cellList, tempInfo.getSubjectTypeName());
	          PagerTools.returnCellList(cellList, tempInfo.getSubjectCode());
	          PagerTools.returnCellList(cellList, tempInfo.getAmountDirectionName());
	          PagerTools.returnCellList(cellList, tempInfo.getAmountTypeName());
	          
	          PagerTools.returnCellList(cellList, SETTConstant.GeneralLedgerOperationType.getName(tempInfo.getNOperateType()));
	          PagerTools.returnCellList(cellList, SETTConstant.GeneralLedgerDisplayStatus.getName(tempInfo.getNStatusID()));	          
	          
	          PagerTools.returnCellList(cellList, tempInfo.getInputUserID());
	          PagerTools.returnCellList(cellList, tempInfo.getTransactionType());
	          PagerTools.returnCellList(cellList, tempInfo.getInputDate());

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
