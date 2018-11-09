package com.iss.itreasury.credit.setting.dao;

import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupViewInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;

public class CreditDao {

	/**
	 * 授信变更dao层
	 * @param amountFormInfo
	 * @return
	 * @throws Exception
	 */	
	 public String queryCreditSQL(AmountFormInfo amountFormInfo){
		 
			StringBuffer sql = new StringBuffer();
			
			sql.append("select t1.*, t2.name clientName, t3.sname inputUserName \n");
			sql.append("from CREDIT_AMOUNTFORM t1, client_clientinfo t2, userinfo t3 \n");
			sql.append("where t1.clientid = t2.id(+) \n");
			sql.append("and t1.inputuserid = t3.id(+) \n");
			
			if(amountFormInfo.getClientId() > 0){
				sql.append(" and t1.clientId = "+amountFormInfo.getClientId());
        	}
//	        if(amountFormInfo.getCreditModel() > 0){
//	        	sql.append(" and t1.CreditModel = "+amountFormInfo.getCreditModel());
//			}
        	if(amountFormInfo.getCreditAmount() > 0){
        		sql.append(" and t1.creditAmount <= "+amountFormInfo.getCreditAmount());
        	}
        	if(amountFormInfo.getStartDate() != null && amountFormInfo.getEndDate() == null){
        		sql.append(" and t1.STARTDATE >= TO_DATE('"+DataFormat.getDateString(amountFormInfo.getStartDate())+"','yyyy-mm-dd')");
        	}
        	if(amountFormInfo.getEndDate() != null && amountFormInfo.getStartDate() == null){
        		sql.append(" and t1.ENDDATE <= TO_DATE('"+DataFormat.getDateString(amountFormInfo.getEndDate())+"','yyyy-mm-dd')");
        	}
        	if(amountFormInfo.getStartDate() != null && amountFormInfo.getEndDate() != null){
        		sql.append(" and t1.STARTDATE >= TO_DATE('"+DataFormat.getDateString(amountFormInfo.getStartDate())+"','yyyy-mm-dd')");
        		sql.append(" and t1.ENDDATE <= TO_DATE('"+DataFormat.getDateString(amountFormInfo.getEndDate())+"','yyyy-mm-dd')");
        	}
        	sql.append(" and t1.STATE = '"+LOANConstant.CreditFlowStatus.SAVE+"'" +"\n");
        	sql.append(" and t1.officeId = "+amountFormInfo.getOfficeId() +"\n");
        	sql.append(" and t1.currencyId = "+amountFormInfo.getCurrencyId() +"\n");
			
			return sql.toString();
		}
	 
	 /**
		 * 授信激活dao层
		 * @param amountFormInfo
		 * @return
		 * @throws Exception
		 */	 
	 public String queryCreditStatusSQL(AmountSetupInfo amountSetupInfo){
		 
			StringBuffer sql = new StringBuffer();
		
			sql.append("select t1.*, t2.name clientName, t3.sname inputUserName \n");
			sql.append("from CREDIT_AMOUNTSETUP t1, client_clientinfo t2, userinfo t3 \n");
			sql.append("where t1.clientid = t2.id(+) \n");
			sql.append("and t1.inputuserid = t3.id(+) \n");
			sql.append("and t1.officeId = "+amountSetupInfo.getOfficeId() + " \n");
			sql.append("and t1.currencyId = "+amountSetupInfo.getCurrencyId() + " \n");
			if(amountSetupInfo.getClientId() > 0){
				sql.append(" and t1.clientId ="+ amountSetupInfo.getClientId()+" \n");
        	}
	        if(amountSetupInfo.getCreditModel() > 0){
	        	sql.append(" and t1.CreditModel = "+ amountSetupInfo.getCreditModel()+" \n");
			}
        	if(amountSetupInfo.getCreditAmount() > 0.0){
        		sql.append(" and t1.creditAmount <= "+ amountSetupInfo.getCreditAmount()+" \n");
        	}
        	if(amountSetupInfo.getStartDate() != null && amountSetupInfo.getEndDate() == null){
        		sql.append(" and t1.STARTDATE >= TO_DATE('"+DataFormat.getDateString(amountSetupInfo.getStartDate())+"','yyyy-mm-dd')");
        	}
        	if(amountSetupInfo.getEndDate() != null && amountSetupInfo.getStartDate() == null){
        		sql.append(" and t1.ENDDATE <=TO_DATE('"+DataFormat.getDateString(amountSetupInfo.getEndDate())+"','yyyy-mm-dd')");
        	}
        	if(amountSetupInfo.getStartDate() != null && amountSetupInfo.getEndDate() != null){
        		sql.append(" and t1.STARTDATE >= TO_DATE('"+DataFormat.getDateString(amountSetupInfo.getStartDate())+"','yyyy-mm-dd')");
        		sql.append(" and t1.ENDDATE <= TO_DATE('"+DataFormat.getDateString(amountSetupInfo.getEndDate())+"','yyyy-mm-dd')");
        	}
	        if(amountSetupInfo.getState() > 0){
	        	if(amountSetupInfo.getState() == LOANConstant.CreditFlowStatus.CHECK){
	        		sql.append(" and t1.STATE = "+amountSetupInfo.getState() +" \n");
	        		sql.append(" and t1.ACTIVESTATE is null");
	        	}
	        	else {
	        		sql.append(" and t1.STATE = "+amountSetupInfo.getState() +" \n");
	        	}
			}
	        else {
	        	sql.append(" and t1.STATE > 0");
	        }
			return sql.toString();
		}
	 /**
	    * 授信可用额度查询 biz层
	    * @param amountFormViewInfo
	    * @return
	    * @throws Exception
	    */ 
	 public String queryCreditQuerySQL(AmountFormViewInfo amountFormViewInfo){
		 
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("SELECT F.ID AS FID,");
			sbSQL.append("F.CREDITCODE AS CREDITCODE,");
			sbSQL.append("(SELECT C.SNAME FROM CLIENT C WHERE C.ID = F.CLIENTID) AS CLIENTNAME,");
			sbSQL.append(" F.CREDITMODEL AS CREDITMODEL,");
			sbSQL.append(" F.CREDITAMOUNT AS CREDITAMOUNT,");
			sbSQL.append(" F.STARTDATE AS STARTDATE,");
			sbSQL.append(" F.ENDDATE AS ENDDATE,");
			sbSQL.append(" F.INPUTUSERID AS INPUTUSERID,");
			sbSQL.append(" (SELECT U.SNAME FROM USERINFO U WHERE U.ID = F.INPUTUSERID) AS INPUTUSERNAME,");
			sbSQL.append(" F.INPUTDATE AS INPUTDATE,");
			sbSQL.append(" F.STATE AS STATE,");
			sbSQL.append(" F.OFFICEID AS OFFICEID,");
			sbSQL.append(" F.CURRENCYID AS CURRENCYID,");
			sbSQL.append(" F.CLIENTID AS CLIENTID,");
			sbSQL.append(" F.CONTROLTYPE AS CONTROLTYPE,");
			sbSQL.append(" F.GROUPCREDITID AS GROUPCREDITID \n");
			sbSQL.append(" FROM CREDIT_AMOUNTFORM F \n");
			sbSQL.append(" WHERE 1=1");
			sbSQL.append(" AND F.STATE = " + LOANConstant.CreditFlowStatus.SAVE);
			sbSQL.append(" AND F.OFFICEID = " + amountFormViewInfo.getOfficeId());
			sbSQL.append(" AND F.CURRENCYID = " + amountFormViewInfo.getCurrencyId());
			
			if(amountFormViewInfo.getClientId() > 0)
			{
				sbSQL.append(" AND F.CLIENTID = " + amountFormViewInfo.getClientId());
			}
			if(amountFormViewInfo.getCreditAmount() > 0)
			{
				sbSQL.append(" AND F.CREDITAMOUNT <= "+amountFormViewInfo.getCreditAmount());
			}
			
			if(amountFormViewInfo.getStartDate() != null )
			{
				sbSQL.append(" AND F.STARTDATE  <= TO_DATE('"+DataFormat.getDateString(amountFormViewInfo.getStartDate())+"','yyyy-mm-dd')");
				sbSQL.append(" AND F.ENDDATE  >= TO_DATE('"+DataFormat.getDateString(amountFormViewInfo.getStartDate())+"','yyyy-mm-dd')");
			}
			
			sbSQL.append(" order by CLIENTNAME ");
			
			return sbSQL.toString();
		}
	 /**
	    * 授信记录查询 biz层
	    * @param amountFormViewInfo
	    * @return
	    * @throws Exception
	    */ 
	 public String queryCreditRecordSQL(AmountSetupViewInfo amountSetupViewInfo){
		 
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append("SELECT * FROM (SELECT S.ID AS ID,");
			sbSQL.append("S.CLIENTID AS CLIENTID,");
			sbSQL.append(" (SELECT C.SNAME FROM CLIENT C WHERE C.ID = S.CLIENTID) AS CLIENTNAME,");
			sbSQL.append("  S.CREDITMODEL AS CREDITMODEL,");
			sbSQL.append(" S.OPERATIONTYPE AS OPERATIONTYPE,");
			sbSQL.append("  S.CREDITAMOUNT AS CREDITAMOUNT,");
			sbSQL.append("  S.STARTDATE AS STARTDATE,");
			sbSQL.append(" S.ENDDATE AS ENDDATE,");
			sbSQL.append(" S.INPUTUSERID AS INPUTUSERID,");
			sbSQL.append(" (SELECT U.SNAME FROM USERINFO U WHERE U.ID = S.INPUTUSERID) AS INPUTUSERNAME,");
			sbSQL.append(" S.INPUTDATE AS INPUTDATE,");
			sbSQL.append(" S.OFFICEID AS OFFICEID,");
			sbSQL.append("  S.CURRENCYID AS CURRENCYID,");
			sbSQL.append(" S.CREDITCODE AS CREDITCODE,");
			sbSQL.append(" CASE WHEN (S.STATE = "+LOANConstant.CreditFlowStatus.CHECK+" AND S.ACTIVESTATE = "+LOANConstant.CreditFlowStatus.ACTIVE+") THEN S.ACTIVESTATE \n");
			sbSQL.append(" ELSE S.STATE END AS STATE \n");
			sbSQL.append(" FROM CREDIT_AMOUNTSETUP S \n");
			sbSQL.append(" WHERE S.OFFICEID = "+amountSetupViewInfo.getOfficeId()+"  \n");
			sbSQL.append(" AND S.CURRENCYID = "+amountSetupViewInfo.getCurrencyId()+" \n");
			sbSQL.append(" AND S.OPERATIONTYPE IN ("+LOANConstant.OperationType.NEW+","+LOANConstant.OperationType.CHANGE+") \n");
			
			if(amountSetupViewInfo.getClientId() > 0)
			{
				sbSQL.append(" AND S.CLIENTID  = " + amountSetupViewInfo.getClientId());
			}
			if(amountSetupViewInfo.getStartDate() != null && amountSetupViewInfo.getEndDate() == null)
			{
				sbSQL.append(" AND STARTDATE  <= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getStartDate())+"','yyyy-mm-dd')");
				sbSQL.append(" AND ENDDATE  >= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getStartDate())+"','yyyy-mm-dd')");
			}
			if(amountSetupViewInfo.getStartDate() == null && amountSetupViewInfo.getEndDate() != null)
			{
				sbSQL.append(" AND STARTDATE  <= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getEndDate())+"','yyyy-mm-dd')");
				sbSQL.append(" AND ENDDATE  >= TO_DATE('"+DataFormat.getDateString(amountSetupViewInfo.getEndDate())+"','yyyy-mm-dd')");
			}
			if(amountSetupViewInfo.getStartDate() != null && amountSetupViewInfo.getEndDate() != null)
			{
				sbSQL.append(" and ((STARTDATE >= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getStartDate()) +"', 'yyyy-mm-dd')");
				sbSQL.append("      and STARTDATE  <= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getEndDate()) +"', 'yyyy-mm-dd')) or");
				sbSQL.append("      (ENDDATE >= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getStartDate()) +"', 'yyyy-mm-dd')");
				sbSQL.append("      and ENDDATE <= to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getEndDate()) +"', 'yyyy-mm-dd')) or");
				sbSQL.append("      (STARTDATE < to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getStartDate()) +"', 'yyyy-mm-dd')");
				sbSQL.append("      and ENDDATE > to_date('"+ DataFormat.getDateString(amountSetupViewInfo.getEndDate()) +"', 'yyyy-mm-dd')))");	
			}
			if(amountSetupViewInfo.getCreditAmount() > 0)
			{
				sbSQL.append(" AND S.CREDITAMOUNT <= " + amountSetupViewInfo.getCreditAmount());
			}
			if(amountSetupViewInfo.getOperationType() > 0)
			{
				sbSQL.append(" AND S.OPERATIONTYPE = " + amountSetupViewInfo.getOperationType());
			}
			else
			{
				sbSQL.append(" AND S.OPERATIONTYPE IN("+LOANConstant.OperationType.NEW+","+LOANConstant.OperationType.CHANGE+")");
			}
			if(amountSetupViewInfo.getState() > 0)
			{
				if(amountSetupViewInfo.getState() == LOANConstant.CreditFlowStatus.ACTIVE)
				{
					sbSQL.append(" AND S.STATE = "+LOANConstant.CreditFlowStatus.CHECK+" AND S.ACTIVESTATE = "+LOANConstant.CreditFlowStatus.ACTIVE +")");
				}
				else if(amountSetupViewInfo.getState() == LOANConstant.CreditFlowStatus.CHECK)
				{
					sbSQL.append(" AND S.STATE = "+LOANConstant.CreditFlowStatus.CHECK+" AND S.ACTIVESTATE IS NULL )");
				}
				else
				{
					sbSQL.append(" AND S.STATE = " + amountSetupViewInfo.getState()+")");
				}
			}
			else
			{
				sbSQL.append(" AND S.STATE > 0 )");
			}
			
			
			return sbSQL.toString();
		}
	 
}
