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
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.QueryDepositLoanWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryDepositLoanResultInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_DepositLoanSearchSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.DepositLoanSearchSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QDepositLoanSearch_IntrustLoan extends BaseQueryObject
{
	//public static String AccountTypeGroupName[] = {"自营贷款","离岸信托贷款"};
	//public static String AccountTypeGroupID[] = {"8", "18" };

	//
	Log4j logger = null;
	//查询信托贷款和离岸信托贷款下级分类
	public Vector queryLoanDetail(long lAccountTypeID,QueryDepositLoanWhereInfo qdlwi) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();		
		try
		{			
			m_sbSQL = new StringBuffer();		
			 
			m_sbSQL.append(" SELECT  nLoanTypeID \n");
			m_sbSQL.append(" FROM Sett_SubAccountType_Loan \n");
			m_sbSQL.append(" WHERE  nAccountTypeID=" + lAccountTypeID + " \n");
			m_sbSQL.append(" and nofficeid = " + qdlwi.getOfficeID() + " and ncurrencyid = " + qdlwi.getCurrencyID() + "\n");
			m_sbSQL.append(" and nStatusID<>0 ");				
			m_sbSQL.append(" order by nLoanTypeID \n");	
				
			conn = getConnection();
			
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				long nLoantypeID = -1;
				nLoantypeID = rs.getLong("nLoanTypeID");
				String str = String.valueOf(nLoantypeID);
				v.addElement(str);
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
	
	//查询信托贷款和离岸信托贷款下级分类汇总值
	public Collection queryIntrustLoanDetail(QueryDepositLoanWhereInfo qdlwi,Vector loanTypeVec,long lAccountTypeID) throws Exception
	{

		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();		
		try
		{
			
			if(loanTypeVec!=null && loanTypeVec.size()!=0)
			{
				for(int i=0;i<loanTypeVec.size();i++)
				{
					m_sbSQL = new StringBuffer();	
					// select			
					if(DataFormat.formatDate(Env.getSystemDate(qdlwi.getOfficeID(),qdlwi.getCurrencyID())).equals(DataFormat.formatDate(qdlwi.getDate())))
					{
						log.print("当前余额"); 
						m_sbSQL.append(" select sum(round(sett_subaccount.mbalance,2)) mBalance from Client,sett_account,sett_subaccount,loan_PayForm payform, loan_ContractForm contractform \n");
						m_sbSQL.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_subaccount.naccountid \n");
						m_sbSQL.append(" AND ");
						m_sbSQL.append(" sett_subaccount.AL_nLoanNoteID = payform.ID \n");
						m_sbSQL.append(" AND ");
						m_sbSQL.append(" payform.nContractID = contractform.ID \n");
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
						m_sbSQL.append(" and contractform.ntypeid = " + Long.valueOf((String)loanTypeVec.elementAt(i)).longValue() + " \n");
					}
					else
					{
						log.print("历史余额");
						m_sbSQL.append(" select sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance from Client,sett_account,sett_dailyaccountbalance,sett_subaccount,loan_PayForm payform, loan_ContractForm contractform \n");
						m_sbSQL.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_dailyaccountbalance.naccountid \n");
						m_sbSQL.append(" and sett_subaccount.id = sett_dailyaccountbalance.nSubAccountID \n");
						m_sbSQL.append(" AND ");
						m_sbSQL.append(" sett_subaccount.AL_nLoanNoteID = payform.ID \n");
						m_sbSQL.append(" AND ");
						m_sbSQL.append(" payform.nContractID = contractform.ID \n");
						m_sbSQL.append(" and sett_account.nofficeid = "+qdlwi.getOfficeID()+" and sett_account.ncurrencyid = "+qdlwi.getCurrencyID()+" \n");						
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
						m_sbSQL.append(" and contractform.ntypeid = " + Long.valueOf((String)loanTypeVec.elementAt(i)).longValue() + " \n");
						m_sbSQL.append(" and sett_dailyaccountbalance.dtdate = to_date('"+DataFormat.formatDate(qdlwi.getDate())+"','yyyy-mm-dd') \n");
					}
//					上海电气新增扩展属性条件
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
					ps = conn.prepareStatement(m_sbSQL.toString());
					rs = ps.executeQuery();
					while (rs.next())
					{
						QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
						qdlri.setAccountTypeName(SETTConstant.SettLoanType.getName(Long.valueOf((String)loanTypeVec.elementAt(i)).longValue()));
						qdlri.setAccountTypeID(Long.valueOf((String)loanTypeVec.elementAt(i)).longValue());
						qdlri.setSumBalance(rs.getDouble("mBalance"));
						log.print("Balance====="+qdlri.getSumBalance());
						v.add(qdlri);
					}
					cleanup(rs);
					cleanup(ps);
					cleanup(conn);					
				}				
			}
					
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return v.size() > 0 ? v : null;
	}
	//查询信托贷款和离岸信托贷款汇总值
	public Collection queryTransAccountDetail(QueryDepositLoanWhereInfo qdlwi) throws Exception
	{

		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement AccountGroupPs = null;
		ResultSet AccountGroupRs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
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
			conn = getConnection();
			AccountGroupPs = conn.prepareStatement(strSQL);
			AccountGroupRs = AccountGroupPs.executeQuery();
						
			while (AccountGroupRs.next())
			{
				m_sbSQL.setLength(0);
				// select
				if(DataFormat.formatDate(Env.getSystemDate(qdlwi.getOfficeID(),qdlwi.getCurrencyID())).equals(DataFormat.formatDate(qdlwi.getDate())))
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
					m_sbSQL.append(" and sett_account.naccounttypeid in (" + AccountGroupRs.getLong("ID") + ")  \n");
				}
				else
				{
					log.print("历史余额");
					m_sbSQL.append(" select sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance from Client,sett_account,sett_dailyaccountbalance \n");
					m_sbSQL.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_dailyaccountbalance.naccountid \n");
					m_sbSQL.append(" and sett_account.nofficeid = "+qdlwi.getOfficeID()+" and sett_account.ncurrencyid = "+qdlwi.getCurrencyID()+" \n");
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
					m_sbSQL.append(" and sett_account.naccounttypeid in (" + AccountGroupRs.getLong("ID") + ")  \n");
					m_sbSQL.append(" and sett_dailyaccountbalance.dtdate = to_date('"+DataFormat.formatDate(qdlwi.getDate())+"','yyyy-mm-dd') \n");
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
				log.print("存款贷款汇总查询SQL:" + m_sbSQL.toString());
				ps = conn.prepareStatement(m_sbSQL.toString());
				rs = ps.executeQuery();

				while (rs.next())
				{
					QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
					qdlri.setAccountTypeName(SETTConstant.AccountType.getName(AccountGroupRs.getLong("ID")));
					qdlri.setAccountTypeID(AccountGroupRs.getLong("ID"));
					qdlri.setSumBalance(rs.getDouble("mBalance"));
					log.print("Balance====="+qdlri.getSumBalance());
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

}