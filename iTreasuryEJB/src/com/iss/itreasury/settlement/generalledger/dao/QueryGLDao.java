package com.iss.itreasury.settlement.generalledger.dao;

import com.iss.itreasury.settlement.util.SETTConstant;

public class QueryGLDao 
{
	 private static final String strTableName = "sett_GLEntryDefinition_temp";
	 
	  public String findAll(long officeID, long currencyID, long transactionType)
	  {
		  StringBuffer buffer = new StringBuffer(64);
	        buffer.append(
	            "select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
	        buffer.append(
	            " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID \n");
	        buffer.append("from SETT_GLENTRYDEFINITION where 1=1 ");

	        if (officeID != -1)
	        {
	            buffer.append(" and nOfficeID=").append(officeID);
	        }

	        if (currencyID != -1)
	        {
	            buffer.append(" and nCurrencyID=").append(currencyID);
	        }
	        
	        if (transactionType > 0)
	        {
	            buffer.append(" and ntransactiontype=").append(transactionType);
	        }

	    return buffer.toString();
	  }
	  
	  
	  public String findAllUnUseAndUsed(long officeID, long currencyID, long transactionType)
	  {

	       StringBuffer buffer = new StringBuffer(64);
	        buffer.append(" select * from ( ");
	        
	        buffer.append("select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
		    buffer.append(" NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, nID, id, noperatetype,nstatusid,inputUserID,  \n");
		    buffer.append(" ((select count(*) from Sett_Glentrydefinition r1 where r1.NTRANSACTIONTYPE = t.ntransactiontype and r1.nofficeid = t.nofficeid and r1.ncurrencyid = t.ncurrencyid)  \n");
		    buffer.append(" +(select count(*) from " + strTableName + " r2 where r2.NTRANSACTIONTYPE = t.ntransactiontype and r2.nofficeid = t.nofficeid and r2.ncurrencyid = t.ncurrencyid and r2.noperatetype = "+SETTConstant.GeneralLedgerOperationType.ADD+" and r2.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" ) ) rowspan  \n");
		    buffer.append(" from " + strTableName + "  t where t.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" \n");
	    
		    buffer.append(" union \n");
		    
	        buffer.append("select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
	        buffer.append(" NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID nID, -1 id, -1 noperatetype, \n");
	        buffer.append(" decode((select count(*) from " + strTableName + "  r  where r.NTRANSACTIONTYPE = t.ntransactiontype  and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = "+SETTConstant.GeneralLedgerStatus.UNCHECK+"),0,"+SETTConstant.GeneralLedgerStatus.CHECK+","+SETTConstant.GeneralLedgerStatus.UNCHECK+") nStatusID, \n");
	        buffer.append(" decode((select count(*) from " + strTableName + "  r  where r.NTRANSACTIONTYPE = t.ntransactiontype  and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = "+SETTConstant.GeneralLedgerStatus.UNCHECK+"),0,-1,(select r.inputuserid from " + strTableName + "  r  where r.NTRANSACTIONTYPE = t.ntransactiontype  and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" and rownum = 1 )) inputUserID, \n");
		    buffer.append(" ((select count(*) from Sett_Glentrydefinition r1 where r1.NTRANSACTIONTYPE = t.ntransactiontype and r1.nofficeid = t.nofficeid and r1.ncurrencyid = t.ncurrencyid)  \n");
		    buffer.append(" +(select count(*) from " + strTableName + "  r2 where r2.NTRANSACTIONTYPE = t.ntransactiontype and r2.nofficeid = t.nofficeid and r2.ncurrencyid = t.ncurrencyid and r2.noperatetype = "+SETTConstant.GeneralLedgerOperationType.ADD+" and r2.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" ) ) rowspan  \n");
	        buffer.append(" from SETT_GLENTRYDEFINITION t where t.id not in (select nID from " + strTableName + "  d where d.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" and d.nofficeid = t.nOfficeID and d.ncurrencyid = t.ncurrencyid) \n");
	        

	        buffer.append(" ) tt where 1 = 1  ");
	        
	        if (officeID != -1)
	        {
	            buffer.append(" and  tt.nOfficeID=").append(officeID);
	        }

	        if (currencyID != -1)
	        {
	            buffer.append(" and  tt.nCurrencyID=").append(currencyID);
	        }

	        if (transactionType > 0)
	        {
	            buffer.append(" and tt.ntransactiontype=").append(transactionType);
	        }
	        
	    return buffer.toString();
	  }
	  
	  public String findAllTemp(String strState,long officeID, long currencyID)
	  {
		 
		  StringBuffer buffer = new StringBuffer(64);
	        buffer.append(
	            "select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
	        buffer.append(
	            " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID \n");
	        buffer.append(
	        " , NID ,INPUTUSERID,INPUTDATE,CHECKUSERID,CHECKDATE ,NOPERATETYPE ,NSTATUSID \n");
	        buffer.append(
	        " , (select count(*) from " + strTableName + " r where r.NTRANSACTIONTYPE = t.ntransactiontype and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = t. NSTATUSID ) rowspan \n");
	        
	        buffer.append("from " + strTableName + " t where 1 = 1  ");

	        if(strState.trim().length()>0){
	        	
	        	buffer.append(" and  NSTATUSID in (").append(strState.trim()).append(")");
	        }
	        if (officeID != -1)
	        {
	            buffer.append(" and  nOfficeID=").append(officeID);
	        }

	        if (currencyID != -1)
	        {
	            buffer.append(" and  nCurrencyID=").append(currencyID);
	        }

	        buffer.append(" order by NTRANSACTIONTYPE ");
	        
		  
		  return buffer.toString();
	  }
	  public String findAllPagerLoaderTemp(long nStatusID,long officeID, long currencyID)
	  {
		  
	  StringBuffer sbSelect = new StringBuffer();
	  StringBuffer sbFrom = new StringBuffer();
	  StringBuffer sbWhere = new StringBuffer(); 
		 sbSelect.append(
          " NOFFICEID OfficeID, NCURRENCYID CurrencyID, NTRANSACTIONTYPE TransactionType,nSubTransactionType SubTransactionType, NCAPITALTYPE CapitalType, NENTRYTYPE EntryType,\n");
      sbSelect.append(
          " NDIRECTION Direction, NSUBJECTTYPE SubjectType, SSUBJECTCODE SubjectCode, NAMOUNTDIRECTION AmountDirection, NAMOUNTTYPE AmountType,NOFFICETYPE OfficeType, ID \n");
      sbSelect.append(
      " , NID ,INPUTUSERID,INPUTDATE,CHECKUSERID,CHECKDATE ,NOPERATETYPE ,NSTATUSID \n");
      sbSelect.append(
      " , (select count(*) from " + strTableName + " r where r.NTRANSACTIONTYPE = t.ntransactiontype and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = t. NSTATUSID ) rowspan \n");
      
      sbFrom.append(" " + strTableName + " t  ");
      
      sbWhere.append(" 1 = 1 ");

      if(nStatusID >0){
      	
      	sbWhere.append(" and  NSTATUSID = ").append(nStatusID);
      }
      if (officeID != -1)
      {
      	sbWhere.append(" and  nOfficeID=").append(officeID);
      }

      if (currencyID != -1)
      {
      	sbWhere.append(" and  nCurrencyID=").append(currencyID);
      }
      
	String sql ="select "+sbSelect+" from "+sbFrom +" where "+sbWhere;	  
		  
	 return sql;
 }

}
