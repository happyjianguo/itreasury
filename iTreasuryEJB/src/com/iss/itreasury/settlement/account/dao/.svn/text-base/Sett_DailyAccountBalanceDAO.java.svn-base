package com.iss.itreasury.settlement.account.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.util.SETTConstant;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-8-23
 */
public class Sett_DailyAccountBalanceDAO extends SettlementDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public Sett_DailyAccountBalanceDAO()
	{
		super.strTableName = "sett_dailyaccountbalance";
	}
	
	/**
	 *根据日期以及帐户类型查询该帐户的余额信息
	*/
	public Collection queryAccountBalance(String queryDate,String accountType) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer buffer = new StringBuffer();
		AccountInfo accountInfo = null;
		try
		{
			conn = getConnection();
			buffer.append("select da.mbalance mbalance,acc.saccountno saccountno,da.dtdate dtdate,acc.sname  sname\n");
			buffer.append(" FROM  sett_dailyaccountbalance da,sett_account acc " );
			buffer.append(" WHERE 1=1 \n");
			buffer.append(" and acc.nstatusid in (1,2) \n");
			buffer.append(" and acc.id=da.naccountid \n");
			if(queryDate!=null && !queryDate.equals("")){
				buffer.append(" and da.dtdate =to_date('" +queryDate+"','yyyy-mm-dd')" );
			}
			if(accountType!=null && !accountType.equals("")){
				buffer.append(" and acc.naccounttypeid in ("+accountType+") ");
			}
			log.print("sql:"+buffer.toString());
			ps = conn.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				accountInfo = new AccountInfo();
				//append one BankBillInfo to the LinkedList object
				accountInfo.setAccountNo(rs.getString("saccountno"));
				accountInfo.setOpenDate(rs.getTimestamp("dtdate"));
				accountInfo.setMonthSumAmount(rs.getDouble("mbalance"));
				accountInfo.setAccountName(rs.getString("sname"));
				v.add(accountInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * add by kevin(刘连凯)2011-08-05
	 *根据日期以及帐户类型查询该帐户的余额信息
	*/
	public Collection queryAccountBalance(AccountInfo queryInfo) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer buffer = new StringBuffer();
		AccountInfo accountInfo = null;
		try
		{
			conn = getConnection();
			buffer.append("select da.mbalance mbalance,acc.saccountno saccountno,da.dtdate dtdate,acc.sname  sname\n");
			buffer.append(" FROM  sett_dailyaccountbalance da,sett_account acc ,sett_accounttype acctype " );
			buffer.append(" WHERE  acc.id=da.naccountid  and acc.naccounttypeid = acctype.id \n");	
			buffer.append(" and acc.nofficeid="+queryInfo.getOfficeID()+"   and acc.ncurrencyid="+queryInfo.getCurrencyID()+" \n");
			buffer.append(" and acc.nstatusid in ("+SETTConstant.AccountStatus.NORMAL+","+SETTConstant.AccountStatus.FREEZE+") \n");
			buffer.append(" and acctype.officeid="+queryInfo.getOfficeID()+"   and acctype.currencyid="+queryInfo.getCurrencyID()+" \n");
			buffer.append(" and da.dtdate = to_date('"+DataFormat.formatDate(queryInfo.getEndDate())+"','yyyy-mm-dd') \n");
			buffer.append(" and acctype.saccounttypecode in ("+queryInfo.getAccountTypeCode()+") ");		
			log.print("sql:"+buffer.toString());
			ps = conn.prepareStatement(buffer.toString());
			rs = ps.executeQuery();		
			while (rs.next())
			{
				accountInfo = new AccountInfo();				
				accountInfo.setAccountNo(rs.getString("saccountno"));
				accountInfo.setOpenDate(rs.getTimestamp("dtdate"));
				accountInfo.setMonthSumAmount(rs.getDouble("mbalance"));
				accountInfo.setAccountName(rs.getString("sname"));
				v.add(accountInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}
}
