package com.iss.itreasury.settlement.generalledger.bizlogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.generalledger.dao.QueryGLDao;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLEntryDefinitionDAO;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLEntryDefinitionTempDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionTempInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class GeneralLedgerUnCheckBiz 
{
	  QueryGLDao dao = new QueryGLDao();

	  public PagerInfo queryGeneralLedger(String strState,long officeID, long currencyID) throws Exception
	  {
	    PagerInfo pagerInfo = null;
	    String sql = null;
	    try
	    {
	      pagerInfo = new PagerInfo();

	      sql = dao.findAllTemp(strState, officeID, currencyID);
	      pagerInfo.setSqlString(sql);
	    
	      pagerInfo.setExtensionMothod(GeneralLedgerUnCheckBiz.class, "resultSetHandle");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw new Exception("====>查询异常", e);
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

	        	tempInfo = new GLEntryDefinitionTempInfo();

            	tempInfo.setOfficeID(rset.getLong("NOFFICEID"));
            	tempInfo.setCurrencyID(rset.getLong("NCURRENCYID"));
            	tempInfo.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
            	tempInfo.setSubTransactionType(rset.getLong("nSubTransactionType"));                
            	tempInfo.setCapitalType(rset.getLong("NCAPITALTYPE"));
            	tempInfo.setEntryType(rset.getLong("NENTRYTYPE"));
            	tempInfo.setDirection(rset.getLong("NDIRECTION"));
            	tempInfo.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	tempInfo.setSubjectCode(subjectCode);
                }
                                
                tempInfo.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                tempInfo.setAmountType(rset.getLong("NAMOUNTTYPE"));
                tempInfo.setOfficeType(rset.getLong("NOFFICETYPE"));
                tempInfo.setID(rset.getLong("ID"));
                tempInfo.setNID(rset.getLong("nID"));
                tempInfo.setNOperateType(rset.getLong("nOperateType"));
                tempInfo.setInputUserID(rset.getLong("inputUserID"));
                tempInfo.setInputDate(rset.getTimestamp("inputDate"));
                tempInfo.setCheckUserID(rset.getLong("checkUserID"));
                tempInfo.setCheckDate(rset.getTimestamp("checkDate"));
                tempInfo.setRowspan(rset.getLong("rowspan"));
                tempInfo.setNStatusID(rset.getLong("nStatusID"));
               
                
	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, tempInfo.getID());
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
	          PagerTools.returnCellList(cellList, tempInfo.getInputUserID());
	          PagerTools.returnCellList(cellList, tempInfo.getTransactionType());
	          PagerTools.returnCellList(cellList, DataFormat.formatDate(tempInfo.getInputDate(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS));
	          PagerTools.returnCellList(cellList, tempInfo.getID());

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
	    

	  
		/**
		 * 按照交易类型 复核 会计分录设置
		 * @return 成功返回 >= 0 , 失败返回 -1
		 * @throws IRollbackException
		 */
		public long checkGLEntryDefinitionTemp_UI2(String strTransActionType,String strInputDate,String strID,long checkUserID,long officeID, long currencyID) throws Exception
		{
			Collection coll = null;
			long lReturn = -1;
			GLEntryDefinitionTempInfo checkInfo = null;
			Connection conn = null;
			try
			{
				
				conn = Database.getConnection();
				conn.setAutoCommit(false);
				
				Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO(conn,true);
				
				Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO(conn,true);
				
				String[]  strTransTypes =  strTransActionType.split(",");
				String[]  strInputDates =  strInputDate.split(",");
				String[]  strIDs =  strID.split(",");
				if(strIDs!=null && strIDs.length == strTransTypes.length && strIDs.length == strInputDates.length)
				{	
					for (int i = 0 ; i< strIDs.length; i++){
						
						checkInfo = tempDao.findByTempID(Long.parseLong(strIDs[i]));
						
						 if(checkInfo==null){
							 throw new Exception("有已被撤销的会计分录设置,请刷新页面重新复核");
						 }else if( checkInfo != null && checkInfo.getNStatusID()== SETTConstant.GeneralLedgerStatus.CHECK){
							 throw new Exception("有已被复核的会计分录设置,请刷新页面重新复核");
						 }else if( checkInfo != null && !DataFormat.formatDate(checkInfo.getInputDate(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS).equals(strInputDates[i]))
						 {
							 throw new Exception("有已被修改的会计分录设置,请刷新页面重新复核");
						 }

					}
				}

				coll = tempDao.findUncheckGLEntryDefinitionTempByIDs(strID,officeID,currencyID);
				
				lReturn = tempDao.checkGLEntryDefinitionTempByIDs(strID, checkUserID,officeID,currencyID);
				
				if( coll !=null && coll.size()>0){
					Iterator it = coll.iterator();
					while(it.hasNext()){
						
						GLEntryDefinitionTempInfo tempInfo = (GLEntryDefinitionTempInfo)it.next();
						
						GLEntryDefinitionInfo info = switchTempInfoToGLEntryDefinitionInfo(tempInfo);
						
						if(info.getID()>0){
							
							if(tempInfo.getNOperateType() == SETTConstant.GeneralLedgerOperationType.DELETE){
								dao.deletePhysically(info.getID());
							}else{
								dao.update(info);
							}
							
						}else{
							dao.add(info);
						}
						
					}
					
				}else{
						
						throw new Exception("该业务类型的会计分录设置已被复核或撤销");
				
				}
				
				
				conn.commit();

				
			}
			catch (Exception ex)
			{
				conn.rollback();
				conn = null;
				throw ex;
			}
	        finally
	        {
	        	try {
	        		if(conn!=null){
						conn.close();
						conn = null;
	        		}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
	        }
			return lReturn;

		}
		
	  

/**
* 由会计分录未生效设置INfo类 转换 会计分录设置已生效设置类
* @return  info GLEntryDefinitionInfo
* @throws IRollbackException
*/
public GLEntryDefinitionInfo switchTempInfoToGLEntryDefinitionInfo(GLEntryDefinitionTempInfo tempInfo) {
	GLEntryDefinitionInfo info = null ;
	try
	{

			info = new GLEntryDefinitionInfo();
			
			info.setID(tempInfo.getNID());
          info.setOfficeID(tempInfo.getOfficeID());
          info.setCurrencyID(tempInfo.getCurrencyID());
          info.setTransactionType(tempInfo.getTransactionType());
			info.setSubTransactionType(tempInfo.getSubTransactionType());                
          info.setCapitalType(tempInfo.getCapitalType());
          info.setEntryType(tempInfo.getEntryType());
          info.setDirection(tempInfo.getDirection());
          info.setSubjectType(tempInfo.getSubjectType());
			info.setSubjectCode(tempInfo.getSubjectCode());
          info.setAmountDirection(tempInfo.getAmountDirection());
          info.setAmountType(tempInfo.getAmountType());
          info.setOfficeType(tempInfo.getOfficeType());

	}
	catch (Exception ex)
	{
		ex.printStackTrace();
		ex.getMessage();
	}
	
	return info;
	
}


}
