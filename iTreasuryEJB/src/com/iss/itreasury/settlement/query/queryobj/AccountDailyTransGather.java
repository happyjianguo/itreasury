/*
 * Created on 2005-8-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.AccountDailyTransGatherConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.AccountDailyTransGatherResultInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;

/**
 * @author hkzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountDailyTransGather extends BaseQueryObject{
	
	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_ClientCode = 2;
	public final static int OrderBy_ClientName = 3;
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbGroupBy = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public AccountDailyTransGather()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

    public Vector findAccountDailyTransGatherInfos(AccountDailyTransGatherConditionInfo aInfo) throws Exception
    {  	
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			conn = getConnection();
			// select 
			m_sbSQL.append(" select account.ID,account.sAccountNo,client.sName,transDetail.nTransDirection,count(*),sum(TransDetail.mAmount) ,Balance.mbalance balance \n");
			m_sbSQL.append(" from sett_TransAccountDetail transDetail,sett_Account account,Client client\n");
			m_sbSQL.append(" ,(select sum(mbalance) mbalance, nAccountId from sett_DailyAccountBalance where dtDate = to_date('"+ DataFormat.formatDate(DataFormat.getPreviousDate(aInfo.getStartDate())) +"','yyyy-mm-dd') ");
			//if( !aInfo.getEndAccountNo().equals("") )
			//{
		    //    m_sbSQL.append(" and nAccountId in ("+aInfo.getAccountNo()+")   ");
			//}
			m_sbSQL.append(" group by nAccountId )balance");
			m_sbSQL.append(" where transDetail.nTransAccountID = account.ID and account.nClientID=client.ID and balance.nAccountID(+) = account.ID and transDetail.nStatusID =3 \n");
			//m_sbSQL.append(" and transdetail.nsubaccountid = balance.nsubaccountid   \n");
			m_sbSQL.append(" and dtExecute >= ");
			m_sbSQL.append(" to_date('"+ DataFormat.formatDate(aInfo.getStartDate()) + "','yyyy-mm-dd')");
			m_sbSQL.append(" and dtExecute <= ");
			m_sbSQL.append(" to_date('"+ DataFormat.formatDate(aInfo.getEndDate())+"','yyyy-mm-dd')");
            if( !aInfo.getStartAccountNo().equals("")  )
            {
                m_sbSQL.append(" and  account.saccountno >= " + "'" + aInfo.getStartAccountNo()+ "'");
            }
            if( !aInfo.getEndAccountNo().equals("") )
            {
                m_sbSQL.append(" and  account.saccountno <= " + "'" + aInfo.getEndAccountNo()+ "'");
            }
            //add by rxie
            if(!aInfo.getAccountNo().equalsIgnoreCase("all"))
            {
            	m_sbSQL.append(" and account.ID in ("+aInfo.getAccountNo()+") ");
            }
             //add by xwhe
            if(aInfo.getOfficeId() > 0)
            {
            	//m_sbSQL.append(" and transDetail.Nofficeid = " +aInfo.getOfficeId() );
            	//modify by bingliu 2011-11-11 不应该限制交易发生地，而应该限制账户的开户地
            	m_sbSQL.append(" and account.Nofficeid = " +aInfo.getOfficeId() );
            }
            if(aInfo.getCurrencyId() > 0)
            {
            	m_sbSQL.append(" and transDetail.Ncurrencyid = " +aInfo.getCurrencyId());
            }
            
			m_sbSQL.append(" group by account.ID,account.sAccountNo,client.sName,transDetail.nTransDirection ,Balance.mbalance \n");
			
			//Boxu Add 2008年5月9日 当同时选择定期账户号和活期账户号或者通知账户号，点击右下脚的“打印”按钮，定期账户的客户账户日结单分借和贷显示两次问题
			m_sbSQL.append(" order by sAccountNo ");
			
			System.out.println("SQL is : "+ m_sbSQL);
			ps = conn.prepareStatement(m_sbSQL.toString());
			
			rs = ps.executeQuery();
			AccountDailyTransGatherResultInfo resultInfo = new AccountDailyTransGatherResultInfo();
			while(rs.next())
			{
				if(!rs.getString("sAccountNo").equals(resultInfo.getAccountNo())){
					resultInfo = new AccountDailyTransGatherResultInfo();
					resultInfo.setAccountNo(rs.getString("sAccountNo"));
					resultInfo.setClientName(rs.getString("sName"));
					resultInfo.setYestodayBalance(rs.getDouble("Balance"));
				}
				else{
					v.remove(resultInfo);
				}
				resultInfo.setAccountID(rs.getLong("ID"));
				if(rs.getInt("nTransDirection")==1){
				resultInfo.setPayAmount(rs.getDouble("sum(TransDetail.mAmount)"));
				resultInfo.setPayTimes(rs.getLong("Count(*)"));
				}
				if(rs.getInt("nTransDirection")==2){
					resultInfo.setReceiveAmount(rs.getDouble("sum(TransDetail.mAmount)"));
					resultInfo.setReceiveTimes(rs.getLong("Count(*)"));
				}
				//modify by zcwang 2008-4-18 对于客户 存款账户属于他的资产,贷款账户属于他的负债
				double balance = 0.0;
				if(SETTConstant.AccountType.isDepositAccountType(NameRef.getAccountTypeByID(resultInfo.getAccountID())))
				{
					balance = resultInfo.getYestodayBalance()-resultInfo.getPayAmount()+resultInfo.getReceiveAmount();
					resultInfo.setBalance(balance);
				}
				else if(SETTConstant.AccountType.isLoanAccountType(NameRef.getAccountTypeByID(resultInfo.getAccountID())))
				{
					balance = resultInfo.getYestodayBalance()-resultInfo.getReceiveAmount()+resultInfo.getPayAmount();
					resultInfo.setBalance(balance);
				}
				v.add(resultInfo);
			}
			
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
            throw new Exception(e.getMessage());
		}
		finally 
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
    	
    	return v;
    	
    }
	
	
}
