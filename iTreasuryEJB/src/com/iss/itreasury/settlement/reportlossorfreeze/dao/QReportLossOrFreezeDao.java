package com.iss.itreasury.settlement.reportlossorfreeze.dao;

import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

public class QReportLossOrFreezeDao {

	
	public String getReportLossOrFreezeSQL(ReportLossOrFreezeQueryInfo info){
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append("   \n    re.*,c.SCODE clientcode,c.sname clientname ,u.sname checkUserName, sa.SACCOUNTNO accountNo  \n");
		StringBuffer m_sbFrom = new StringBuffer();

		m_sbFrom.append("  sett_reportlossorfreeze re ,sett_account sa, client c, userinfo u \n");
		StringBuffer m_sbWhere = new StringBuffer();
		if (info.getTransActionType() == SETTConstant.TransactionType.REPORTLOSS)
		{
			m_sbWhere.append("         TRANSACTIONTYPE in (" + SETTConstant.TransactionType.REPORTLOSS + ","
					+ SETTConstant.TransactionType.REPORTFIND + "," + SETTConstant.TransactionType.CHANGECERTIFICATE
					+ ") \n");
		}
		else if (info.getTransActionType() == SETTConstant.TransactionType.FREEZE)
		{
			m_sbWhere.append("         TRANSACTIONTYPE in (" + SETTConstant.TransactionType.FREEZE + ","
					+ SETTConstant.TransactionType.DEFREEZE + ") \n");
		}
		else
		{
			m_sbWhere.append("         TRANSACTIONTYPE>0 \n");
		}
		if (info.getClientId() > 0)
		{
			m_sbWhere.append(" and clientId =" + info.getClientId() + " \n");
		}
		if (info.getStartDate() != null)
		{
			m_sbWhere.append("        and executedate >= to_date('" + DataFormat.getDateString(info.getStartDate())
					+ "','yyyy-mm-dd')   \n");
		}
		if (info.getEndDate() != null)
		{
			m_sbWhere.append("        and executedate <= to_date('" + DataFormat.getDateString(info.getEndDate())
					+ "','yyyy-mm-dd')   \n");
		}
		m_sbWhere.append(" and status=" + info.getStatus() + " \n");
		m_sbWhere.append(" and currencyid = " +info.getCurrencyId()+ "\n");
		m_sbWhere.append(" and officeid = " +info.getOfficeId()+ "\n");
		
		m_sbWhere.append(" and re.clientid=c.id(+) and re.checkuserid=u.id(+) and re.accountid=sa.id(+) " + " \n");
				
		return "SELECT "+m_sbSelect.toString()+" FROM "+m_sbFrom.toString()+" WHERE " + m_sbWhere.toString();
	}
}
