package com.iss.itreasury.settlement.account.dao;

import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;

public class Sett_QueryAccountDAO {
	
	//主要是获得SQl
	public String getSQL(QueryAccountConditionInfo qaci) {
		StringBuffer sbSQL = new StringBuffer();
		if ( (qaci.getStartClientCode() != null && qaci.getStartClientCode().length() > 0)  
		  || (qaci.getEndClientCode() != null && qaci.getEndClientCode().length() > 0 ) )
		{
			sbSQL.append(" select a.*,c.SCODE,c.isinstitutionalclient from sett_Account a, Client c ");
			/*
			sbSQL.append(" where a.nClientID = c.id and a.nStatusID in( "
																	+ SETTConstant.AccountStatus.NORMAL + ","
																	+ SETTConstant.AccountStatus.FREEZE + ","
																	+ SETTConstant.AccountStatus.SEALUP + ","
																	+ SETTConstant.AccountStatus.REPORTLOSS + ","
																	+ SETTConstant.AccountStatus.ALLFREEZE + ","
																	+ SETTConstant.AccountStatus.PARTFREEZE + " ) ");
			*/
			sbSQL.append(" where a.nClientID = c.id ");
			
		} 
		else 
		{
			sbSQL.append(" select * from sett_Account a ");
			sbSQL.append(" where 1 = 1 and a.nStatusID > 0 ");
		}
		//appends TypeID to the query where condition
		if (qaci.getOfficeID() > 0) 
		{
			sbSQL.append(" and a.nOfficeID ="+qaci.getOfficeID());
		}
		if (qaci.getCurrencyID() > 0) 
		{
			sbSQL.append(" and a.nCurrencyID = "+qaci.getCurrencyID());
		}
		if (qaci.getCheckUserID() > 0) 
		{
			sbSQL.append(" and a.nInputUserID <> "+qaci.getCheckUserID());
		}
		if (qaci.getInputUserID() > 0 && qaci.getStrQuery().equals("")) 
		{
			sbSQL.append(" and a.nInputUserID = "+qaci.getInputUserID());
		}
		
		if ( qaci.getStartClientCode() != null
		  && qaci.getEndClientCode() != null
		  && qaci.getStartClientCode().length() > 0
		  && qaci.getEndClientCode().length() > 0 ) 
		{
			sbSQL.append(" and ( c.SCODE between '"+ qaci.getStartClientCode() +"' and '" + qaci.getEndClientCode() +"' )");
		}
		else if(qaci.getStartClientCode() != null && qaci.getStartClientCode().length() > 0 )
		{
			sbSQL.append(" and c.SCODE >= '"+qaci.getStartClientCode()+"' ");
		}
		else if(qaci.getEndClientCode() != null && qaci.getEndClientCode().length() > 0 )
		{
			sbSQL.append(" and c.SCODE <= '"+qaci.getEndClientCode()+"' ");
		}
		
		if ( qaci.getStartAccountCode() != null
		  && qaci.getEndAccountCode() != null
		  && qaci.getStartAccountCode().length() > 0
		  && qaci.getEndAccountCode().length() > 0) 
		{
			sbSQL.append(" and ( a.sAccountNo between '"+qaci.getStartAccountCode()+"' and '"+qaci.getEndAccountCode()+"' )");
		}
		else if(qaci.getStartAccountCode() != null && qaci.getStartAccountCode().length() > 0 )
		{
			sbSQL.append(" and a.sAccountNo >= '"+qaci.getStartAccountCode()+"' ");
		}
		else if(qaci.getEndAccountCode() != null && qaci.getEndAccountCode().length() > 0 )
		{
			sbSQL.append(" and a.sAccountNo <= '"+qaci.getEndAccountCode()+"' ");
		}
		
		if (qaci.getAccountTypeID() > 0) 
		{
			sbSQL.append(" and a.nAccountTypeID = "+qaci.getAccountTypeID());
		}
		if (qaci.getCheckStatusID() > 0) 
		{
			//2007-11-12 Boxu update 增加页面显示"修改未复核"条件选择,只查询符合状态的数据
			if (qaci.getStrAction().equals("QueryAction"))
			{
				if (qaci.getCheckStatusID() == SETTConstant.AccountCheckStatus.OLDSAVE)
				{
					sbSQL.append(" and ( a.nCheckStatusID = "+qaci.getCheckStatusID()+" and a.nInputUserID = "+qaci.getInputUserID()+" ) ");
				}
				else
				{
					sbSQL.append(" and a.nCheckStatusID = "+qaci.getCheckStatusID());
				}
			}
			else if( qaci.getStrQuery().equals("MODIFYSAVE") )
			{
				sbSQL.append(" and ( a.nCheckStatusID = "+qaci.getCheckStatusID()+"or ( a.nCheckStatusID= "
															+ SETTConstant.AccountCheckStatus.OLDSAVE
															+ " and a.nInputUserID= "
															+ qaci.getInputUserID() + " ) ) ");
			}
			else
			{
				sbSQL.append(" and a.nCheckStatusID = "+qaci.getCheckStatusID());
			}
		}
		if (qaci.getStatusID() > 0) 
		{
			sbSQL.append(" and a.nStatusID = "+qaci.getStatusID());
		}
		
		//状态不等于0
		sbSQL.append(" and a.nStatusID != 0 ");
		
		if (qaci.getBatchUpdateID() > 0) 
		{
			sbSQL.append(" and a.nBatchUpdateID = "+qaci.getBatchUpdateID());
		}

		
		return sbSQL.toString();
		
	}

}
