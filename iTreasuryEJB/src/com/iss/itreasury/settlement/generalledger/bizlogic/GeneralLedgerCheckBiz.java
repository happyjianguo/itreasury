package com.iss.itreasury.settlement.generalledger.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.settlement.generalledger.dao.QueryGLDao;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionTempInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class GeneralLedgerCheckBiz 
{
	  QueryGLDao dao = new QueryGLDao();

	  public PagerInfo queryGeneralLedger(long nStatusID,long officeID, long currencyID) throws Exception
	  {
	    PagerInfo pagerInfo = null;
	    String sql = null;
	    try
	    {
	      pagerInfo = new PagerInfo();

	      sql = dao.findAllPagerLoaderTemp(nStatusID, officeID, currencyID);
	      pagerInfo.setSqlString(sql);
	    
	      pagerInfo.setExtensionMothod(GeneralLedgerCheckBiz.class, "resultSetHandle");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw new Exception("====>≤È—Ø“Ï≥£", e);
	    }
	    return pagerInfo;
	  }
	  public ArrayList resultSetHandle(ResultSet rset) throws Exception
	  {
	    ArrayList resultList = new ArrayList();
	    ArrayList cellList = null;
	    ResultPagerRowInfo rowInfo = null;
	    GLEntryDefinitionTempInfo tempInfo = null;
	    int index=0;
	    try
	    {
	      if (rset != null)
	      {
	        while (rset.next())
	        {
	        	
	        	tempInfo = new GLEntryDefinitionTempInfo();


            	tempInfo.setOfficeID(rset.getLong("OfficeID"));
            	tempInfo.setCurrencyID(rset.getLong("CurrencyID"));
            	tempInfo.setTransactionType(rset.getLong("TransactionType"));
            	tempInfo.setSubTransactionType(rset.getLong("SubTransactionType"));                
            	tempInfo.setCapitalType(rset.getLong("CapitalType"));
            	tempInfo.setEntryType(rset.getLong("EntryType"));
            	tempInfo.setDirection(rset.getLong("Direction"));
            	tempInfo.setSubjectType(rset.getLong("SubjectType"));
                
                String subjectCode=rset.getString("SubjectCode");
                
                if(subjectCode!=null)
                {
                	tempInfo.setSubjectCode(subjectCode);
                }
                                
                tempInfo.setAmountDirection(rset.getLong("AmountDirection"));
                tempInfo.setAmountType(rset.getLong("AmountType"));
                tempInfo.setOfficeType(rset.getLong("OfficeType"));
                tempInfo.setID(rset.getLong("ID"));
                tempInfo.setNID(rset.getLong("nID"));
                tempInfo.setInputUserID(rset.getLong("INPUTUSERID"));
                tempInfo.setInputDate(rset.getTimestamp("INPUTDATE"));
                tempInfo.setCheckUserID(rset.getLong("CHECKUSERID"));
                tempInfo.setCheckDate(rset.getTimestamp("CHECKDATE"));
                tempInfo.setNOperateType(rset.getLong("NOPERATETYPE"));
                tempInfo.setNStatusID(rset.getLong("NSTATUSID"));
               
                
	          cellList = new ArrayList();
	         // PagerTools.returnCellList(cellList, tempInfo.getID());
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
	          PagerTools.returnCellList(cellList, NameRef.getUserNameByID(tempInfo.getInputUserID()));
	          PagerTools.returnCellList(cellList, DataFormat.formatDate(tempInfo.getInputDate(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS));
	          PagerTools.returnCellList(cellList, NameRef.getUserNameByID(tempInfo.getCheckUserID()));
	          PagerTools.returnCellList(cellList, DataFormat.formatDate(tempInfo.getCheckDate(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS));

	          rowInfo = new ResultPagerRowInfo();
	          rowInfo.setCell(cellList);
	          rowInfo.setId(String.valueOf(rset.getLong("rownum1")));

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
