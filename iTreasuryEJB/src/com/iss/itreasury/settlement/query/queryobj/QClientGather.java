/*
 * Created on 2003-12-5
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.settlement.query.paraminfo.QueryClientGatherWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryClientGatherResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QClientGather extends BaseQueryObject
{

	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_ClientCode = 2;
	public final static int OrderBy_ClientName = 3;
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbGroupBy = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QClientGather()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getClientGatherSQL(QueryClientGatherWhereInfo qcgi)
	{
		m_sbSelect = new StringBuffer();
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
		m_sbFrom = new StringBuffer();
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
		m_sbWhere = new StringBuffer();
		if(qcgi.getIsFilter() >0)
		{
			m_sbWhere.append(" nvl(LoanCount,0) + nvl(DepositCount,0) >0 ");
		}
		//group by
		m_sbGroupBy = new StringBuffer();
		
		m_sbOrderBy = new StringBuffer();
		if(qcgi.getLdesc() == 2)
		{
			m_sbOrderBy.append(" order by sCode desc ");
		}
		else
		{
			m_sbOrderBy.append(" order by sCode ");
		}
	}

	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryClientGather(QueryClientGatherWhereInfo qcgi) throws Exception
	{

		getClientGatherSQL(qcgi);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		AppContext appcontext = new AppContext();

		pageLoader.initPageLoader(
			appcontext,
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryClientGatherResultInfo",
			null);

		pageLoader.setOrderBy(" "+m_sbOrderBy.toString()+" ");

		pageLoader.setGroupBy(" " + m_sbGroupBy.toString() + " ");
		log.print("Java-Select :" + m_sbFrom);
		log.print("Java-From :" + m_sbSelect);
		log.print("Java-where :" + m_sbWhere);
		log.print("Java-GroupBy :" + m_sbGroupBy);
		log.print("Java-OrderBy：" + m_sbOrderBy);
		return pageLoader;
	}

}