/*
 * Created on 2004-12-22
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.clientcenter.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountAmountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryOtherDepositInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryOtherDepositSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author ygzhao
 *  
 */
public class QOtherDeposit extends BaseQueryObject
{
    public final static int OrderBy_AccountNo = 1;	
	public final static int OrderBy_ClientName = 2;	
	public final static int OrderBy_Amount = 3;
	public final static int OrderBy_BALANCE = 4;
	public final static int OrderBy_Status = 5;
	public final static int OrderBy_Interest = 6;
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
    /**
     * 
     */
    public QOtherDeposit()
    {
        super();
        logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
    }
    
    public void getOtherDepositSQL(QueryOtherDepositInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();	
		m_sbWhere = new StringBuffer();
		// select		
		m_sbSelect.append("acct.sAccountNo as AccountNo, \n");
		m_sbSelect.append("client.sname ClientName, \n");		
		m_sbSelect.append("acct.id as AccountID, \n");
		m_sbSelect.append("subAcct.id as SubAccountID, \n");
		m_sbSelect.append("acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("subAcct.mOpenAmount as Amount, \n");
		m_sbSelect.append("round(nvl(subAcct.mBalance,0),2) as Balance, \n");
		m_sbSelect.append("subAcct.nStatusID as subAccountStatusID,  \n");
		m_sbSelect.append("subAcct.mInterest as Interest \n");
		// from 		
		if (DataFormat.formatDate(Env.getSystemDate(qInfo.getOfficeID(), qInfo.getCurrencyID())).equals(DataFormat.formatDate(qInfo.getDate())))
		{
			log.print("当前");		
			m_sbFrom.append("sett_account acct, sett_subAccount subAcct, client \n");
			m_sbWhere.append("subAcct.nAccountID=acct.id and acct.nclientid=client.id  \n");			
		}
		else
		{
			log.print("历史");
			m_sbFrom.append("sett_account acct, sett_subAccount subAcct, client,sett_dailyaccountbalance \n");	
			m_sbWhere.append(" Client.id = acct.nclientid and  acct.id = subAcct.naccountid and acct.id = sett_dailyaccountbalance.naccountid \n");
			m_sbWhere.append(" and sett_dailyaccountbalance.dtdate = to_date('" + DataFormat.formatDate(qInfo.getDate()) + "','yyyy-mm-dd') \n");
		}
		
		m_sbWhere.append("and acct.nAccountTypeID in (" + qInfo.getAccountTypeID() + ")  \n");				
		
		m_sbWhere.append(" and acct.nofficeid="+ qInfo.getOfficeID() + " \n");
		m_sbWhere.append(" and acct.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		m_sbWhere.append(" and acct.nStatusID != 0 \n");//add  by xfma 
		m_sbWhere.append(" and subAcct.nStatusID <> 0 \n");
		m_sbWhere.append(" and subAcct.nStatusID <> 2 \n");
		if (qInfo.getEnterpriseTypeID1() > 0)
		{
		    m_sbWhere.append(" and client.nclienttypeid1 = " + qInfo.getEnterpriseTypeID1() + " \n");
		}
		if (qInfo.getEnterpriseTypeID2() > 0)
		{
			m_sbWhere.append(" and client.nclienttypeid2 = " + qInfo.getEnterpriseTypeID2() + " \n");
		}
		if (qInfo.getEnterpriseTypeID3() > 0)
		{
			m_sbWhere.append(" and client.nclienttypeid3 = " + qInfo.getEnterpriseTypeID3() + " \n");
		}
		if (qInfo.getEnterpriseTypeID4() > 0)
		{
			m_sbWhere.append(" and client.nclienttypeid4 = " + qInfo.getEnterpriseTypeID4() + " \n");
		}
		if (qInfo.getEnterpriseTypeID5() > 0)
		{
			m_sbWhere.append(" and client.nclienttypeid5 = " + qInfo.getEnterpriseTypeID5() + " \n");
		}
		if (qInfo.getEnterpriseTypeID6() > 0)
		{
			m_sbWhere.append(" and client.nclienttypeid6 = " + qInfo.getEnterpriseTypeID6() + " \n");
		}
		if (qInfo.getLClientManager() > 0)
		{
			m_sbWhere.append(" and client.ncustomermanageruserid = " + qInfo.getLClientManager() + " \n");
		}
//		上海电气新增扩展属性条件
		if (qInfo.getExtendAttribute1()!=-1 && qInfo.getExtendAttribute1()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE1 = " + qInfo.getExtendAttribute1() + " \n");
		}
		if (qInfo.getExtendAttribute2()!=-1 && qInfo.getExtendAttribute2()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE2 = " + qInfo.getExtendAttribute2() + " \n");
		}
		if (qInfo.getExtendAttribute3()!=-1 && qInfo.getExtendAttribute3()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE3 = " + qInfo.getExtendAttribute3() + " \n");
		}
		if (qInfo.getExtendAttribute4()!=-1 && qInfo.getExtendAttribute4()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE4 = " + qInfo.getExtendAttribute4() + " \n");
		}
		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by ");		
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n AccountNo " + strDesc + ", ");
				break;			
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n ClientName " + strDesc + ", ");
				break;			
			case OrderBy_Amount :
				m_sbOrderBy.append(" \n Amount " + strDesc + ", ");
				break;
			case OrderBy_BALANCE :
				m_sbOrderBy.append(" \n Balance " + strDesc + ", ");
				break;
			case OrderBy_Status :
				m_sbOrderBy.append(" \n subAccountStatusID " + strDesc + ", ");
				break;
			case OrderBy_Interest :
				m_sbOrderBy.append(" \n Interest " + strDesc + ", ");
				break;			
		}		
		m_sbOrderBy.append(" AccountTypeID,AccountID ");		
	}
    
    public PageLoader queryOtherDepositInfo(QueryOtherDepositInfo qInfo) throws Exception
	{
        getOtherDepositSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
            new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryOtherDepositResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
    
    public QueryOtherDepositSumInfo queryOtherDepositSum(QueryOtherDepositInfo qInfo) throws Exception
	{
        QueryOtherDepositSumInfo sumObj = new QueryOtherDepositSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String sql = "";
		//
		try
		{
		    getOtherDepositSQL(qInfo);
			// select 
		    if (DataFormat.formatDate(Env.getSystemDate(qInfo.getOfficeID(), qInfo.getCurrencyID())).equals(DataFormat.formatDate(qInfo.getDate())))
			{
				log.print("QOtherDeposit---当前余额");
				strSelect = " select sum(round(subAcct.mOpenAmount,2)) Amount,sum(round(subAcct.mBalance,2)) balance,sum(round(subAcct.mInterest,2)) Interest \n";			
			}
		    else
		    {
		        log.print("QOtherDeposit---历史余额");
		        strSelect = " select sum(round(subAcct.mOpenAmount,2)) Amount,sum(round(sett_dailyaccountbalance.mbalance,2)) Balance,sum(round(subAcct.mInterest,2)) Interest \n";
		    }
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			//logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				sumObj.setDepositAmountSum(rs.getDouble("Amount"));
				sumObj.setDepositBalanceSum(rs.getDouble("balance"));
				sumObj.setDepositInterestSum(rs.getDouble("Interest"));
			}
			cleanup(rs);
			cleanup(ps);			
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;
	}
    /**
     * Sets the orderBy.
     * @param orderBy The orderBy to set
     */
    public void setOrderBy(QueryOtherDepositInfo qInfo) {
        // create orderby
        m_sbOrderBy = new StringBuffer();
        String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
        switch ((int) qInfo.getOrderField()) {
            case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo " + strDesc );
				break;			
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n order by ClientName " + strDesc );
				break;			
			case OrderBy_Amount :
				m_sbOrderBy.append(" \n order by Amount " + strDesc );
				break;
			case OrderBy_BALANCE :
				m_sbOrderBy.append(" \n order by Balance " + strDesc );
				break;
			case OrderBy_Status :
				m_sbOrderBy.append(" \n order by subAccountStatusID " + strDesc );
				break;
			case OrderBy_Interest :
				m_sbOrderBy.append(" \n order by Interest " + strDesc );
				break;			
            default :
                m_sbOrderBy.append(" \n order by AccountNo " + strDesc);
                break;
        }
    }
    /**
     * Returns the orderBy.
     * @return StringBuffer
     */
    public StringBuffer getOrderBy() {
        return m_sbOrderBy;
    }
}