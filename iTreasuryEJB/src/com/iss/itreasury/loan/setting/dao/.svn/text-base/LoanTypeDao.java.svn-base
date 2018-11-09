package com.iss.itreasury.loan.setting.dao;

import com.iss.itreasury.loan.setting.dataentity.LoanAssortSettingInfo;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;

public class LoanTypeDao {

	/**
	 * 贷款类型分类设置dao层
	 * add by liaiyi
	 * @return
	 * @throws Exception
	 */	
	public String queryLoanTypeSQL(LoanTypeSettingInfo info ){
		
	StringBuffer sql = new StringBuffer();
	
	sql.append("SELECT * FROM  Loan_LoanTypeSetting l \n");
	sql.append("WHERE StatusID = " + LOANConstant.RecordStatus.VALID +"\n");
	
	if (info.getLoanTypeID()>-1 )
		sql.append("and l.LoanTypeID =" + info.getLoanTypeID());
	
	if(info.getCode()!=null && !info.getCode().equals(""))
		sql.append(" and l.code = '"+info.getCode()+"'");
	
//	if(info.getName()!=null && !info.getName().equals(""))
//		sql.append(" and l.name = '"+info.getName()+"'");
	
	sql.append(" order by id");
	
	return sql.toString();
}
	/**
	 * 贷款合同分类设置dao层
	 * add by liaiyi
	 * @return
	 * @throws Exception
	 */	
	public String queryLoanAssortSQL(LoanAssortSettingInfo loanAssortSettingInfo ){
		
		StringBuffer sql = new StringBuffer();

		sql.append("select a.*, b.sname inputuserName, a.Name assortName \n");
		sql.append("from loan_assortsetting a, userinfo b \n");
		sql.append("where 1 = 1 \n");
		sql.append("and a.inputuserid = b.id \n");
		sql.append("and a.STATUSID = " + Constant.TRUE +"\n");
//		sql.append("and a.OFFICEID = 1 \n");
//		sql.append("and a.CURRENCYID = 1 \n");
		if (loanAssortSettingInfo.getOfficeId() > 0) {
			sql.append(" and a.OFFICEID=" + loanAssortSettingInfo.getOfficeId());
		}
		if (loanAssortSettingInfo.getCurrencyId() > 0) {
			sql.append(" and a.CURRENCYID=" + loanAssortSettingInfo.getCurrencyId());
		}
		if (loanAssortSettingInfo.getName()!="") {
			sql.append(" and a.NAME='" + loanAssortSettingInfo.getName()+"'");
		}
		if (loanAssortSettingInfo.getAssortTypeId() > -1) 
			sql.append(" and a.ASSORTTYPEID=" + loanAssortSettingInfo.getAssortTypeId());
		
		
		return sql.toString();
	}
}