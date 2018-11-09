package com.iss.itreasury.settlement.query.Dao;

import com.iss.itreasury.settlement.query.paraminfo.QueryClientGatherWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * 账户查询数据层
 * @author xiang
 *
 */
public class QClientGatherDao {
	
	public String getClientGatherSQL(QueryClientGatherWhereInfo qcgi){
		
		StringBuffer m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" ID ClientID,sCode ClientCode,sName ClientName,AccountCount,nvl(DepositCount,0) FixedCount,nvl(LoanCount,0) LoanCount \n");
		
		// from
		//获取存款账户类型编码
		StringBuffer strDepositAccountTypeCode = new StringBuffer("-1");
		long[] laDepositAccountTypeCode = SETTConstant.AccountType.getDepositAccountTypeCode();
		for(int i = 0 ;i < laDepositAccountTypeCode.length ; i++ ){
			strDepositAccountTypeCode.append(",");
			strDepositAccountTypeCode.append(laDepositAccountTypeCode[i]);
		}
		
		//获取贷款账户类型编码
		StringBuffer strLoanAccountTypeCode = new StringBuffer("-1");
		long[] laLoanAccountTypeCode = SETTConstant.AccountType.getLoanAccountTypeCode();
		for(int i = 0 ;i < laLoanAccountTypeCode.length ; i++ ){
			strLoanAccountTypeCode.append(",");
			strLoanAccountTypeCode.append(laLoanAccountTypeCode[i]);
		}
		
		//如果没有选择查询全部账户类型
		if(qcgi.getAccountType() == null || qcgi.getAccountType().length() == 0 || qcgi.getAccountType().equals("0")){
			qcgi.setAccountType(strDepositAccountTypeCode.toString() + "," + strLoanAccountTypeCode.toString());
		}
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( \n");
		m_sbFrom.append(" select Client.ID,Client.sCode,Client.sName,count(sett_Account.ID) AccountCount, \n");
		m_sbFrom.append(" sum(SUBACCOUNTCOUNT_LOAN.SUBCOUNT) loancount,sum(SUBACCOUNTCOUNT_FIXED.SUBCOUNT) depositcount \n");
		m_sbFrom.append(" from client,sett_Account,sett_AccountType, \n");
		//统计存款笔数
		m_sbFrom.append(" (select nAccountID,count(*) SubCount from SETT_ACCOUNT,SETT_SUBACCOUNT \n");
		m_sbFrom.append(" where decode(sett_SubAccount.dtFinish,null,to_date('3000-01-01','yyyy-mm-dd'),");
		m_sbFrom.append(" sett_SubAccount.dtFinish) >= decode('"+DataFormat.formatDate(qcgi.getDate())+"',");
		m_sbFrom.append(" null,to_date('1000-01-01','yyyy-mm-dd'),to_date('"+DataFormat.formatDate(qcgi.getDate())+"',");
		m_sbFrom.append(" 'yyyy-mm-dd')) and sett_SubAccount.nStatusID > 0 ");
		m_sbFrom.append(" and SETT_SUBACCOUNT.naccountid = SETT_ACCOUNT.ID AND ");
		m_sbFrom.append(" SETT_ACCOUNT.naccounttypeid IN (" + strDepositAccountTypeCode.toString() + ") AND ");
		m_sbFrom.append(" SETT_ACCOUNT.naccounttypeid IN (" + qcgi.getAccountType() + ") AND ");
		m_sbFrom.append(" SETT_ACCOUNT.nstatusid > 0 AND SETT_SUBACCOUNT.NSTATUSID > 0 ");
		m_sbFrom.append(" group by nAccountID) SUBACCOUNTCOUNT_FIXED, \n");
		//统计贷款笔数
		m_sbFrom.append(" (select nAccountID,count(*) SubCount from SETT_ACCOUNT,SETT_SUBACCOUNT \n");
		m_sbFrom.append(" where decode(sett_SubAccount.dtFinish,null,to_date('3000-01-01','yyyy-mm-dd'),");
		m_sbFrom.append(" sett_SubAccount.dtFinish) >= decode('"+DataFormat.formatDate(qcgi.getDate())+"',");
		m_sbFrom.append(" null,to_date('1000-01-01','yyyy-mm-dd'),to_date('"+DataFormat.formatDate(qcgi.getDate())+"',");
		m_sbFrom.append(" 'yyyy-mm-dd')) and sett_SubAccount.nStatusID > 0 ");
		m_sbFrom.append(" and SETT_SUBACCOUNT.naccountid = SETT_ACCOUNT.ID AND ");
		m_sbFrom.append(" SETT_ACCOUNT.naccounttypeid IN (" + strLoanAccountTypeCode.toString() + ") AND ");
		m_sbFrom.append(" SETT_ACCOUNT.naccounttypeid IN (" + qcgi.getAccountType() + ") AND ");
		m_sbFrom.append(" SETT_ACCOUNT.nstatusid > 0 AND SETT_SUBACCOUNT.NSTATUSID > 0 ");
		m_sbFrom.append(" group by nAccountID) SUBACCOUNTCOUNT_LOAN \n");
				
		m_sbFrom.append(" where Client.ID=sett_Account.nClientID(+) and ");
		m_sbFrom.append(" sett_Account.ID=SUBACCOUNTCOUNT_FIXED.nAccountID(+) and ");
		m_sbFrom.append(" sett_Account.ID=SUBACCOUNTCOUNT_LOAN.nAccountID(+) and ");
		m_sbFrom.append(" sett_Account.nAccountTypeID=sett_AccountType.ID(+) \n");
		m_sbFrom.append(" and sett_Account.nAccountTypeID in ("+qcgi.getAccountType()+") \n");
		m_sbFrom.append(" and sett_Account.nOfficeID = "+qcgi.getOfficeID()+" and sett_Account.nCurrencyID = "+qcgi.getCurrencyID()+" and sett_Account.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		m_sbFrom.append(" and decode(sett_Account.dtFinish,null,to_date('3000-01-01','yyyy-mm-dd'),sett_Account.dtFinish) \n");
		m_sbFrom.append(" >= decode('"+DataFormat.formatDate(qcgi.getDate())+"',null,to_date('1000-01-01','yyyy-mm-dd'),to_date('"+DataFormat.formatDate(qcgi.getDate())+"','yyyy-mm-dd'))  and sett_Account.nStatusID > 0 \n");
		m_sbFrom.append(" and ( 1= 1 ");
		
		//modified by zhangfuxing 2006-11-22
		if(qcgi.getStartClientCode() != null && qcgi.getStartClientCode().length() > 0 ) 
		{
			m_sbFrom.append(" and Client.sCode >= '"+qcgi.getStartClientCode() + "'");
		}
		if(qcgi.getEndClientCode() != null && qcgi.getEndClientCode().length() > 0)
		{
			m_sbFrom.append(" and Client.sCode <= '"+qcgi.getEndClientCode()+ "' \n");
		}
		
		
//		
//		if(qcgi.getStartClientCode() != null && qcgi.getStartClientCode().length() > 0 && qcgi.getEndClientCode() != null && qcgi.getEndClientCode().length() > 0)
//		{
//			m_sbFrom.append(" and Client.sCode between '"+qcgi.getStartClientCode()+"' and '"+qcgi.getEndClientCode()+"' \n");
//		}
		if(qcgi.getIsIncluedSub() >0)//包括子公司 即子公司在选择的范围之内
		{
			
			m_sbFrom.append(" or nParentCorpID1 between "+qcgi.getStartClientID()+" and "+qcgi.getEndClientID()+" \n");
			m_sbFrom.append(" or nParentCorpID2 between "+qcgi.getStartClientID()+" and "+qcgi.getEndClientID()+" \n");
		}
		if(qcgi.getIsIncludeParent() >0)//包括母公司 即母公司在选择的范围之内
		{
			m_sbFrom.append(" or Client.id in (select nParentCorpID1 from Client where id between "+qcgi.getStartClientID()+" and "+qcgi.getEndClientID()+" union " +
					                          "select nParentCorpID2 from Client where id between "+qcgi.getStartClientID()+" and "+qcgi.getEndClientID()+" )\n");
		}
		m_sbFrom.append(" ) ");
		if(qcgi.getAccountStatusID() >0)
		{
			m_sbFrom.append(" and sett_Account.nStatusID = "+qcgi.getAccountStatusID()+" \n");
		}
		m_sbFrom.append(" group by client.ID,client.sCode,client.sName \n");		
		m_sbFrom.append(" ) \n");
		
		System.out.println(m_sbFrom.toString());
		// where
		StringBuffer m_sbWhere = new StringBuffer("");
		if(qcgi.getIsFilter() >0)
		{
			m_sbWhere.append(" nvl(LoanCount,0) + nvl(DepositCount,0) >0 ");
		}
		
		String sb = "select" + m_sbSelect.toString()+" from "+m_sbFrom.toString()+ (m_sbWhere.toString().equals("")?"":(" where "+m_sbWhere.toString()));
		
		return sb.toString();
	}

}
