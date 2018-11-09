package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.settlement.setting.dataentity.QueryBranchInfo;
import com.iss.itreasury.util.Constant;

public class Sett_BranchQueryDAO {
	
	public String queryBranchSql(QueryBranchInfo qbInfo) {
		
		StringBuffer sbSQL = null;
		sbSQL = new StringBuffer();
		sbSQL.append(
		  " select id,SCODE,SNAME,SSUBJECTCODE,SBRANCHPROVINCE,SBRANCHCITY,BANKSUBJECTTYPE,SBANKACCOUNTCODE,SENTERPRISENAME,( case NISSINGLE when 1 then 'ÊÇ' when 2 then '·ñ' else '' END )NISSINGLE,SCASHCREDITBOOKEDACCOUNT,SCASHDEBITBOOKEDACCOUNT,STRANSFERCREDITBOOKEDACCOUNT,STRANSFERDEBITBOOKEDACCOUNT,( case NISAUTOVIREMENTBYBANK when 1 then 'ÊÇ' when 2 then '·ñ' else '' END )NISAUTOVIREMENTBYBANK,NBANKTYPE " +
		 " from SETT_BRANCH  where nStatusID >0 and nOfficeID = "
				+ qbInfo.getOfficeID()
				+ " and nCurrencyID= "
				+ qbInfo.getCurrencyID());
		if(qbInfo.getIsSingleBank()==Constant.YesOrNo.YES || qbInfo.getIsSingleBank()==Constant.YesOrNo.NO )
			sbSQL.append(" and nIsSingle = "					
				+ qbInfo.getIsSingleBank());
		if(qbInfo.getBranchStartID()>=0)
			sbSQL.append( " and SCODE >="
				+ qbInfo.getBranchStartID());
		if(qbInfo.getBranchEndID()>=0)
			sbSQL.append( " and SCODE <="
				+ qbInfo.getBranchEndID());
		if(qbInfo.getBankType() > 0)
			sbSQL.append( " and NBANKTYPE ="
				+ qbInfo.getBankType());
			
		if(qbInfo.getBANKACCOUNTCODE() != null && qbInfo.getBANKACCOUNTCODE().trim().length() > 0)
			sbSQL.append( " and SBANKACCOUNTCODE = '"
				+ qbInfo.getBANKACCOUNTCODE() +"'");
		sbSQL.append( " order by sCode");
				
		return sbSQL.toString();
	}

}
