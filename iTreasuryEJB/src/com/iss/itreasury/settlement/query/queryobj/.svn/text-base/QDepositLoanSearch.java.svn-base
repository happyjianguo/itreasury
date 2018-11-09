/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

import com.iss.itreasury.settlement.query.paraminfo.QueryDepositLoanWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryDepositLoanResultInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_DepositLoanSearchSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.DepositLoanSearchSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;
/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QDepositLoanSearch extends BaseQueryObject
{

	//
	Log4j logger = null;

	public Collection queryDepositLoan(QueryDepositLoanWhereInfo qdlwi) throws Exception
	{

		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			Sett_DepositLoanSearchSettingDAO qobj = new Sett_DepositLoanSearchSettingDAO();
			Collection AccountTypeColl = null;
			Iterator it = null;
			AccountTypeColl = qobj.query(qdlwi.getCurrencyID(),qdlwi.getOfficeID());
			if (AccountTypeColl != null)
			{
				it = AccountTypeColl.iterator();
			}
			if (it != null && it.hasNext())
			{
				while (it.hasNext())
				{
					DepositLoanSearchSettingInfo dlsi = (DepositLoanSearchSettingInfo) it.next();

					m_sbSQL.setLength(0);
					// select
					if (DataFormat.formatDate(Env.getSystemDate(qdlwi.getOfficeID(), qdlwi.getCurrencyID())).equals(DataFormat.formatDate(qdlwi.getDate())))
					{
						log.print("当前余额");
						m_sbSQL.append(" select sum(round(sett_subaccount.mbalance,2)) mBalance from Client,sett_account,sett_subaccount \n");
						m_sbSQL.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_subaccount.naccountid \n");
						//add by xfma(马现福) 2008-7-9
						//查找出来的存款结果中，把删除的账户也显示出来了，不对，应该过滤删除账户
						m_sbSQL.append(" and sett_account.nstatusid !=0 and sett_subaccount.nstatusid !=0 \n");
						m_sbSQL.append(" and sett_account.nofficeid = " + qdlwi.getOfficeID() + " and sett_account.ncurrencyid = " + qdlwi.getCurrencyID() + "\n");
						if (qdlwi.getEnterpriseTypeID1() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID1 = " + qdlwi.getEnterpriseTypeID1() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID2() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID2 = " + qdlwi.getEnterpriseTypeID2() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID3() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID3 = " + qdlwi.getEnterpriseTypeID3() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID4() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID4 = " + qdlwi.getEnterpriseTypeID4() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID5() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID5 = " + qdlwi.getEnterpriseTypeID5() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID6() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID6 = " + qdlwi.getEnterpriseTypeID6() + " \n");
						}
						m_sbSQL.append(" and sett_account.naccounttypeid in (" + dlsi.getAccountTypeID() + ") \n");
					}
					else
					{
						log.print("历史余额");
						//去掉了sett_subaccount 表的关联，可以修改了汇总余额数据不对的BUG
						m_sbSQL.append(" select sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance from Client,sett_account,sett_dailyaccountbalance \n");
						m_sbSQL.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_dailyaccountbalance.naccountid  \n");
						//add by xfma(马现福) 2008-7-9
						//查找出来的存款结果中，把删除的账户也显示出来了，不对，应该过滤删除账户
						m_sbSQL.append(" and sett_account.nstatusid !=0 \n");
						m_sbSQL.append(" and sett_account.nofficeid = " + qdlwi.getOfficeID() + " and sett_account.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
						if (qdlwi.getEnterpriseTypeID1() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID1 = " + qdlwi.getEnterpriseTypeID1() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID2() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID2 = " + qdlwi.getEnterpriseTypeID2() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID3() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID3 = " + qdlwi.getEnterpriseTypeID3() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID4() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID4 = " + qdlwi.getEnterpriseTypeID4() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID5() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID5 = " + qdlwi.getEnterpriseTypeID5() + " \n");
						}
						if (qdlwi.getEnterpriseTypeID6() > 0)
						{
							m_sbSQL.append(" and client.nClienttypeID6 = " + qdlwi.getEnterpriseTypeID6() + " \n");
						}
						m_sbSQL.append(" and sett_account.naccounttypeid in (" + dlsi.getAccountTypeID() + ") \n");
						m_sbSQL.append(" and sett_dailyaccountbalance.dtdate = to_date('" + DataFormat.formatDate(qdlwi.getDate()) + "','yyyy-mm-dd') \n");
					}
					if (qdlwi.getClientManager() > 0 )
					{	
						m_sbSQL.append("  and client.nCustomerManagerUserId = " + 
								qdlwi.getClientManager() + " \n");
					}
					//上海电气新增扩展属性条件
					if (qdlwi.getExtendAttribute1()!=-1 && qdlwi.getExtendAttribute1()!=0)
					{
						m_sbSQL.append(" and client.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
					}
					if (qdlwi.getExtendAttribute2()!=-1 && qdlwi.getExtendAttribute2()!=0)
					{
						m_sbSQL.append(" and client.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
					}
					if (qdlwi.getExtendAttribute3()!=-1 && qdlwi.getExtendAttribute3()!=0)
					{
						m_sbSQL.append(" and client.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
					}
					if (qdlwi.getExtendAttribute4()!=-1 && qdlwi.getExtendAttribute4()!=0)
					{
						m_sbSQL.append(" and client.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
					}

					conn = getConnection();
					log.print("存款贷款汇总查询SQL:" + m_sbSQL.toString());
					//后台打印SQL
					System.out.println("存款贷款汇总查询SQL:" +m_sbSQL.toString());
					ps = conn.prepareStatement(m_sbSQL.toString());
					rs = ps.executeQuery();

					while (rs.next())
					{
						QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
						qdlri.setAccountTypeName(dlsi.getName());
						qdlri.setSumBalance(rs.getDouble("mBalance"));
						qdlri.setDepositLoanSearchID(dlsi.getID());
						log.print("Balance=====" + qdlri.getSumBalance());
						v.add(qdlri);
					}
					cleanup(rs);
					cleanup(ps);
					cleanup(conn);

				} //end AccountType while
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return v.size() > 0 ? v : null;
	}
	public Collection queryDepositLoan_Deposit(QueryDepositLoanWhereInfo qdlwi) throws Exception
	{

		Vector v = new Vector();
		Connection conn = null;
		conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement AccountGroupPs = null;
		ResultSet AccountGroupRs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			Sett_DepositLoanSearchSettingDAO qobj = new Sett_DepositLoanSearchSettingDAO();
			DepositLoanSearchSettingInfo dlsi = null;
			dlsi = new DepositLoanSearchSettingInfo();
			dlsi = qobj.findByID(qdlwi.getDepositLoanSearchID());
			
			String strSQL = " select * from sett_AccountType where ID in (" + dlsi.getAccountTypeID() + ") order by ID";
			log.print(strSQL);
			AccountGroupPs = conn.prepareStatement(strSQL);
			AccountGroupRs = AccountGroupPs.executeQuery();
			while (AccountGroupRs.next())
			{
				m_sbSQL.setLength(0);
				// select
				if (DataFormat.formatDate(Env.getSystemDate(qdlwi.getOfficeID(), qdlwi.getCurrencyID())).equals(DataFormat.formatDate(qdlwi.getDate())))
				{
					log.print("当前余额");
					m_sbSQL.append(" select sum(round(sett_subaccount.mbalance,2)) mBalance from Client,sett_account,sett_subaccount \n");
					m_sbSQL.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_subaccount.naccountid \n");
					m_sbSQL.append(" and sett_account.nofficeid = " + qdlwi.getOfficeID() + " and sett_account.ncurrencyid = " + qdlwi.getCurrencyID() + "\n");
					if (qdlwi.getEnterpriseTypeID1() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid1 = " + qdlwi.getEnterpriseTypeID1() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID2() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid2 = " + qdlwi.getEnterpriseTypeID2() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID3() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid3 = " + qdlwi.getEnterpriseTypeID3() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID4() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid4 = " + qdlwi.getEnterpriseTypeID4() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID5() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid5 = " + qdlwi.getEnterpriseTypeID5() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID6() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid6 = " + qdlwi.getEnterpriseTypeID6() + " \n");
					}
					if (qdlwi.getClientManager() > 0)
					{
						m_sbSQL.append(" and client.ncustomermanageruserid = " + qdlwi.getClientManager() + " \n");
					}
					m_sbSQL.append(" and sett_account.naccounttypeid in (" + AccountGroupRs.getLong("ID") + ") \n");
				}
				else
				{
					log.print("历史余额");
					m_sbSQL.append(" select sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance from Client,sett_account,sett_dailyaccountbalance \n");
					m_sbSQL.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_dailyaccountbalance.naccountid \n");
					m_sbSQL.append(" and sett_account.nofficeid = " + qdlwi.getOfficeID() + " and sett_account.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
					if (qdlwi.getEnterpriseTypeID1() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid1 = " + qdlwi.getEnterpriseTypeID1() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID2() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid2 = " + qdlwi.getEnterpriseTypeID2() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID3() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid3 = " + qdlwi.getEnterpriseTypeID3() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID4() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid4 = " + qdlwi.getEnterpriseTypeID4() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID5() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid5 = " + qdlwi.getEnterpriseTypeID5() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID6() > 0)
					{
						m_sbSQL.append(" and client.nclienttypeid6 = " + qdlwi.getEnterpriseTypeID6() + " \n");
					}
					if (qdlwi.getClientManager() > 0)
					{
						m_sbSQL.append(" and client.ncustomermanageruserid = " + qdlwi.getClientManager() + " \n");
					}
					m_sbSQL.append(" and sett_account.naccounttypeid in (" + AccountGroupRs.getLong("ID") + ") \n");
					m_sbSQL.append(" and sett_dailyaccountbalance.dtdate = to_date('" + DataFormat.formatDate(qdlwi.getDate()) + "','yyyy-mm-dd') \n");
				}
//				上海电气新增扩展属性条件
				if (qdlwi.getExtendAttribute1()!=-1 && qdlwi.getExtendAttribute1()!=0)
				{
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
				}
				if (qdlwi.getExtendAttribute2()!=-1 && qdlwi.getExtendAttribute2()!=0)
				{
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
				}
				if (qdlwi.getExtendAttribute3()!=-1 && qdlwi.getExtendAttribute3()!=0)
				{
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
				}
				if (qdlwi.getExtendAttribute4()!=-1 && qdlwi.getExtendAttribute4()!=0)
				{
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
				}
				
				log.print("存款贷款汇总-信托存款-查询SQL:" + m_sbSQL.toString());
				ps = conn.prepareStatement(m_sbSQL.toString());
				rs = ps.executeQuery();

				while (rs.next())
				{
					QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
					qdlri.setAccountTypeID(AccountGroupRs.getLong("ID"));
					qdlri.setAccountTypeName(SETTConstant.AccountType.getName(AccountGroupRs.getLong("ID")));
					qdlri.setSumBalance(rs.getDouble("mBalance"));
					log.print("Balance=====" + qdlri.getSumBalance());
					qdlri.setDepositLoanSearchID(qdlwi.getDepositLoanSearchID());
					v.add(qdlri);
				}
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(AccountGroupRs);
			cleanup(AccountGroupPs);
			cleanup(conn);
}
catch (Exception e)
{
	e.printStackTrace();
	throw e;
}
return v.size() > 0 ? v : null;
}

public Collection queryDepositLoan_FixedNotice(QueryDepositLoanWhereInfo qdlwi) throws Exception
{

	Vector v = new Vector();
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuffer m_sbSQL = null;
	m_sbSQL = new StringBuffer();
	try
	{
		m_sbSQL.setLength(0);
		// select
		if (DataFormat.formatDate(Env.getSystemDate(qdlwi.getOfficeID(), qdlwi.getCurrencyID())).equals(DataFormat.formatDate(qdlwi.getDate())))
		{
			log.print("当前余额");
			//m_sbSQL.append(" select sett_subaccounttype_fixed.nfixeddepositmonthid,sum(round(sett_subaccount.mbalance,2)) mBalance \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" select set_fix.nfixeddepositmonthid,sum(round(sett_subaccount.mbalance,2)) mBalance \n");
			//
			//m_sbSQL.append(" from client,sett_account,sett_subaccount,sett_subaccounttype_fixed \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" from client,sett_account,sett_subaccount,(select a.transtime nfixeddepositmonthid, a.officeid nofficeid, a.currencyid ncurrencyid, b.id naccounttypeid from sett_fixeddepositmonth a, sett_accounttype b where 1 = 1 and a.statusid <> 0 and a.transtype = b.naccountgroupid and b.nstatusid <>0) set_fix \n");
			//
			m_sbSQL.append(" where sett_account.id = sett_subaccount.naccountid  \n");
			m_sbSQL.append(" and client.ID = sett_Account.nClientID \n");
			//m_sbSQL.append(" and sett_account.naccounttypeid= sett_subaccounttype_fixed.naccounttypeid(+)  \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" and sett_account.naccounttypeid= set_fix.naccounttypeid(+)  \n");
			//
			if (qdlwi.getEnterpriseTypeID1() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid1 = " + qdlwi.getEnterpriseTypeID1() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID2() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid2 = " + qdlwi.getEnterpriseTypeID2() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID3() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid3 = " + qdlwi.getEnterpriseTypeID3() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID4() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid4 = " + qdlwi.getEnterpriseTypeID4() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID5() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid5 = " + qdlwi.getEnterpriseTypeID5() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID6() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid6 = " + qdlwi.getEnterpriseTypeID6() + " \n");
			}
			if (qdlwi.getClientManager() > 0)
			{
				m_sbSQL.append(" and client.ncustomermanageruserid = " + qdlwi.getClientManager() + " \n");
			}
			if (SETTConstant.AccountType.isFixAccountType(qdlwi.getDepositAccountTypeID()))
			{
				//m_sbSQL.append(" and sett_subaccount.af_ndepositterm(+) = sett_subaccounttype_fixed.nfixeddepositmonthid \n");
				//modify by zcwang 2008-4-18
				m_sbSQL.append(" and sett_subaccount.af_ndepositterm(+) = set_fix.nfixeddepositmonthid \n");
				//
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(qdlwi.getDepositAccountTypeID()))
			{
				//m_sbSQL.append(" and sett_subaccount.af_nnoticeday(+) = sett_subaccounttype_fixed.nfixeddepositmonthid \n");
				//modify by zcwang 2008-4-18
				m_sbSQL.append(" and sett_subaccount.af_nnoticeday(+) = set_fix.nfixeddepositmonthid \n");
				//
			}
			m_sbSQL.append(" and sett_account.naccounttypeid = " + qdlwi.getDepositAccountTypeID() + " \n");
			//m_sbSQL.append(" and  sett_subaccounttype_fixed.nOfficeID = sett_account.nOfficeID \n");
			//m_sbSQL.append(" and  sett_subaccounttype_fixed.nCurrencyID = sett_account.nCurrencyID \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" and  set_fix.nOfficeID = sett_account.nOfficeID \n");
			m_sbSQL.append(" and  set_fix.nCurrencyID = sett_account.nCurrencyID \n");
			//
			m_sbSQL.append(" and sett_account.nOfficeID = " + qdlwi.getOfficeID() + " \n");
			m_sbSQL.append(" and sett_account.nCurrencyID = " + qdlwi.getCurrencyID() + " \n");
//			上海电气新增扩展属性条件
			if (qdlwi.getExtendAttribute1()!=-1 && qdlwi.getExtendAttribute1()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
			}
			if (qdlwi.getExtendAttribute2()!=-1 && qdlwi.getExtendAttribute2()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
			}
			if (qdlwi.getExtendAttribute3()!=-1 && qdlwi.getExtendAttribute3()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
			}
			if (qdlwi.getExtendAttribute4()!=-1 && qdlwi.getExtendAttribute4()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
			}
			
			//m_sbSQL.append(" group by sett_subaccounttype_fixed.nfixeddepositmonthid \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" group by set_fix.nfixeddepositmonthid \n");
			//
		}
		else
		{
			log.print("历史余额");
			//m_sbSQL.append(" select sett_subaccounttype_fixed.nfixeddepositmonthid,sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" select set_fix.nfixeddepositmonthid,sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance \n");
			//
			//m_sbSQL.append(" from client,sett_account,sett_subaccount,sett_subaccounttype_fixed,sett_dailyaccountbalance \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" from client,sett_account,sett_subaccount,(select a.transtime nfixeddepositmonthid, a.officeid nofficeid, a.currencyid ncurrencyid, b.id naccounttypeid from sett_fixeddepositmonth a, sett_accounttype b where 1 = 1 and a.statusid <> 0 and a.transtype = b.naccountgroupid and b.nstatusid <>0) set_fix,sett_dailyaccountbalance \n");
			//
			m_sbSQL.append(" where sett_account.id = sett_subaccount.naccountid and sett_subaccount.id = sett_dailyaccountbalance.nsubaccountid \n");
			m_sbSQL.append(" and client.ID = sett_Account.nClientID \n");
			//m_sbSQL.append(" and sett_account.naccounttypeid= sett_subaccounttype_fixed.naccounttypeid(+)  \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" and sett_account.naccounttypeid= set_fix.naccounttypeid(+)  \n");
			//
			if (qdlwi.getEnterpriseTypeID1() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid1 = " + qdlwi.getEnterpriseTypeID1() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID2() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid2 = " + qdlwi.getEnterpriseTypeID2() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID3() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid3 = " + qdlwi.getEnterpriseTypeID3() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID4() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid4 = " + qdlwi.getEnterpriseTypeID4() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID5() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid5 = " + qdlwi.getEnterpriseTypeID5() + " \n");
			}
			if (qdlwi.getEnterpriseTypeID6() > 0)
			{
				m_sbSQL.append(" and client.nclienttypeid6 = " + qdlwi.getEnterpriseTypeID6() + " \n");
			}
			if (qdlwi.getClientManager() > 0)
			{
				m_sbSQL.append(" and client.ncustomermanageruserid = " + qdlwi.getClientManager() + " \n");
			}
			if (SETTConstant.AccountType.isFixAccountType(qdlwi.getDepositAccountTypeID()))
			{
				//m_sbSQL.append(" and sett_subaccount.af_ndepositterm(+) = sett_subaccounttype_fixed.nfixeddepositmonthid \n");
				//modify by zcwang 2008-4-18
				m_sbSQL.append(" and sett_subaccount.af_ndepositterm(+) = set_fix.nfixeddepositmonthid \n");
				//
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(qdlwi.getDepositAccountTypeID()))
			{
				//m_sbSQL.append(" and sett_subaccount.af_nnoticeday(+) = sett_subaccounttype_fixed.nfixeddepositmonthid \n");
				//modify by zcwang 2008-4-18
				m_sbSQL.append(" and sett_subaccount.af_nnoticeday(+) = set_fix.nfixeddepositmonthid \n");
				//
			}
			m_sbSQL.append(" and sett_account.naccounttypeid = " + qdlwi.getDepositAccountTypeID() + "  \n");
			m_sbSQL.append(" and sett_dailyaccountbalance.dtdate(+) = to_date('" + DataFormat.formatDate(qdlwi.getDate()) + "','yyyy-mm-dd') \n");
			//m_sbSQL.append(" and  sett_subaccounttype_fixed.nOfficeID = sett_account.nOfficeID \n");
			//m_sbSQL.append(" and  sett_subaccounttype_fixed.nCurrencyID = sett_account.nCurrencyID \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" and  set_fix.nOfficeID = sett_account.nOfficeID \n");
			m_sbSQL.append(" and  set_fix.nCurrencyID = sett_account.nCurrencyID \n");
			//
			m_sbSQL.append(" and sett_account.nOfficeID = " + qdlwi.getOfficeID() + " \n");
			m_sbSQL.append(" and sett_account.nCurrencyID = " + qdlwi.getCurrencyID() + " \n");
//			上海电气新增扩展属性条件
			if (qdlwi.getExtendAttribute1()!=-1 && qdlwi.getExtendAttribute1()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
			}
			if (qdlwi.getExtendAttribute2()!=-1 && qdlwi.getExtendAttribute2()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
			}
			if (qdlwi.getExtendAttribute3()!=-1 && qdlwi.getExtendAttribute3()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
			}
			if (qdlwi.getExtendAttribute4()!=-1 && qdlwi.getExtendAttribute4()!=0)
			{
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
			}
			
			//m_sbSQL.append(" group by sett_subaccounttype_fixed.nfixeddepositmonthid \n");
			//modify by zcwang 2008-4-18
			m_sbSQL.append(" group by set_fix.nfixeddepositmonthid \n");
			//
		}

		conn = getConnection();
		log.print("存款贷款汇总-信托存款-定期:通知-查询SQL:" + m_sbSQL.toString());
		ps = conn.prepareStatement(m_sbSQL.toString());
		rs = ps.executeQuery();

		while (rs.next())
		{
			QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
			qdlri.setAccountTypeID(qdlwi.getDepositAccountTypeID());
			qdlri.setFixedTerm(rs.getLong("nfixeddepositmonthid"));
			if (qdlri.getFixedTerm() > 10000)
			{
				qdlri.setFixedTermName(qdlri.getFixedTerm() - 10000 + "天");
			}
			else
			{
				qdlri.setFixedTermName(qdlri.getFixedTerm() + "个月");
			}
			qdlri.setAccountTypeName(SETTConstant.AccountType.getName(qdlwi.getDepositAccountTypeID()) + "-" + qdlri.getFixedTermName());
			qdlri.setSumBalance(rs.getDouble("mBalance"));
			log.print("Balance=====" + qdlri.getSumBalance());
			qdlri.setDepositLoanSearchID(qdlwi.getDepositLoanSearchID());
			v.add(qdlri);
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw e;
	}
	return v.size() > 0 ? v : null;
}

}