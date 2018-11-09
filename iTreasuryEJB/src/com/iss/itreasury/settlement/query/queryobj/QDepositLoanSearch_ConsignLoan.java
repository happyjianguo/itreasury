/*
 * Created on 2003-12-25
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;

import com.iss.itreasury.settlement.query.paraminfo.QueryDepositLoanWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryDepositLoanResultInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QDepositLoanSearch_ConsignLoan extends BaseQueryObject
{

	/**
	 * 
	 */
	public QDepositLoanSearch_ConsignLoan()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 按照委托贷款类型汇总查询
	 * 委托贷款类型 ： 一般委托贷款（3），统借统还（4）
	 * @return
	 * @throws Exception
	 */
	public Collection queryCosignLoanSumByLoanTypeID(QueryDepositLoanWhereInfo qdlwi) throws Exception
	{
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer();
			if (isToday(qdlwi.getOfficeID(), qdlwi.getCurrencyID(), qdlwi.getDate()))
			{
				sb.append(" select vc.loantypeid loantypeid, sum(round(sa.mbalance,2)) Balance \n");
				sb.append(" from Client c,sett_account a,sett_subaccount sa,sett_vcontractinfo vc,sett_accountType sat \n");
				sb.append(" where c.id = a.nclientid and a.id = sa.naccountid \n");
				sb.append(" and a.nofficeid = " + qdlwi.getOfficeID() + " and a.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
				sb.append(" and a.naccounttypeid=sat.id and sat.nAccountGroupID = " + SETTConstant.AccountGroupType.CONSIGN + " and sa.al_nLoanNoteID = vc.loanPayID \n");
				sb.append(" and vc.loantypeid in(" + LOANConstant.LoanType.WT + "," + LOANConstant.LoanType.WT + ") \n");
				if (qdlwi.getEnterpriseTypeID1() > 0)
					sb.append(" and c.nClienttypeID1=" + qdlwi.getEnterpriseTypeID1());
				if (qdlwi.getEnterpriseTypeID2() > 0)
					sb.append(" and c.nClienttypeID2=" + qdlwi.getEnterpriseTypeID2());
				if (qdlwi.getEnterpriseTypeID3() > 0)
					sb.append(" and c.nClienttypeID3=" + qdlwi.getEnterpriseTypeID3());
				if (qdlwi.getEnterpriseTypeID4() > 0)
					sb.append(" and c.nClienttypeID4=" + qdlwi.getEnterpriseTypeID4());
				if (qdlwi.getEnterpriseTypeID5() > 0)
					sb.append(" and c.nClienttypeID5=" + qdlwi.getEnterpriseTypeID5());
				if (qdlwi.getEnterpriseTypeID6() > 0)
					sb.append(" and c.nClienttypeID6=" + qdlwi.getEnterpriseTypeID6());
				if (qdlwi.getClientManager() > 0)
					sb.append(" and c.ncustomermanageruserid=" + qdlwi.getClientManager());
				//上海电气新增扩展属性条件
				if (qdlwi.getExtendAttribute1()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
				}
				if (qdlwi.getExtendAttribute2()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
				}
				if (qdlwi.getExtendAttribute3()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
				}
				if (qdlwi.getExtendAttribute4()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
				}
				sb.append(" group by vc.loantypeid \n");
			}
			else
			{
				sb.append(" select vc.loantypeid loantypeid, sum(round(da.mbalance,2)) Balance \n");
				sb.append(" from Client c,sett_account a,sett_subaccount sa,sett_dailyaccountbalance da,sett_vcontractinfo vc,sett_accountType sat \n");
				sb.append(" where c.id = a.nclientid and a.id = sa.naccountid \n");
				sb.append(" and a.nofficeid = " + qdlwi.getOfficeID() + " and a.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
				sb.append(" and a.naccounttypeid=sat.id and sat.nAccountGroupID = " + SETTConstant.AccountGroupType.CONSIGN + " and sa.al_nLoanNoteID = vc.loanPayID \n");
				sb.append(" and vc.loantypeid in(" + LOANConstant.LoanType.WT + "," + LOANConstant.LoanType.WT + ") \n");
				sb.append(" and sa.id=da.nsubaccountid and da.dtdate=to_date('" + DataFormat.getDateString(qdlwi.getDate()) + "','yyyy-mm-dd') \n");
				if (qdlwi.getEnterpriseTypeID1() > 0)
					sb.append(" and c.nClienttypeID1=" + qdlwi.getEnterpriseTypeID1());
				if (qdlwi.getEnterpriseTypeID2() > 0)
					sb.append(" and c.nClienttypeID2=" + qdlwi.getEnterpriseTypeID2());
				if (qdlwi.getEnterpriseTypeID3() > 0)
					sb.append(" and c.nClienttypeID3=" + qdlwi.getEnterpriseTypeID3());
				if (qdlwi.getEnterpriseTypeID4() > 0)
					sb.append(" and c.nClienttypeID4=" + qdlwi.getEnterpriseTypeID4());
				if (qdlwi.getEnterpriseTypeID5() > 0)
					sb.append(" and c.nClienttypeID5=" + qdlwi.getEnterpriseTypeID5());
				if (qdlwi.getEnterpriseTypeID6() > 0)
					sb.append(" and c.nClienttypeID6=" + qdlwi.getEnterpriseTypeID6());
				if (qdlwi.getClientManager() > 0)
					sb.append(" and c.ncustomermanageruserid=" + qdlwi.getClientManager());
				//上海电气新增扩展属性条件
				if (qdlwi.getExtendAttribute1()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
				}
				if (qdlwi.getExtendAttribute2()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
				}
				if (qdlwi.getExtendAttribute3()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
				}
				if (qdlwi.getExtendAttribute4()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
				}
				sb.append(" group by vc.loantypeid \n");
			}

			ps = con.prepareCall(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
                QueryDepositLoanResultInfo o = new QueryDepositLoanResultInfo();
                o.setLoanTypeID(rs.getLong("loantypeid"));
                o.setSumBalance(rs.getDouble("Balance"));
                v.add(o);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * 按照委托方汇总查询
	 * @return
	 * @throws Exception
	 */
	public Collection queryCosignLoanSumByCosignClientID(QueryDepositLoanWhereInfo qdlwi) throws Exception
	{
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer();
			if (isToday(qdlwi.getOfficeID(), qdlwi.getCurrencyID(), qdlwi.getDate()))
			{
				sb.append(" select vc.consignclientid consignclientid, sum(round(sa.mbalance,2)) Balance \n");
				sb.append(" from Client c,sett_account a,sett_subaccount sa,sett_vcontractinfo vc ,sett_accountType sat \n");
				sb.append(" where c.id = a.nclientid and a.id = sa.naccountid \n");
				sb.append(" and a.nofficeid = " + qdlwi.getOfficeID() + " and a.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
				sb.append(" and a.naccounttypeid=sat.id and sat.nAccountGroupID = " + SETTConstant.AccountGroupType.CONSIGN + " and sa.al_nLoanNoteID = vc.loanPayID \n");
				sb.append(" and vc.loantypeid in(" + qdlwi.getLoanTypeID() + ") \n");
				if (qdlwi.getEnterpriseTypeID1() > 0)
					sb.append(" and c.nClienttypeID1=" + qdlwi.getEnterpriseTypeID1());
				if (qdlwi.getEnterpriseTypeID2() > 0)
					sb.append(" and c.nClienttypeID2=" + qdlwi.getEnterpriseTypeID2());
				if (qdlwi.getEnterpriseTypeID3() > 0)
					sb.append(" and c.nClienttypeID3=" + qdlwi.getEnterpriseTypeID3());
				if (qdlwi.getEnterpriseTypeID4() > 0)
					sb.append(" and c.nClienttypeID4=" + qdlwi.getEnterpriseTypeID4());
				if (qdlwi.getEnterpriseTypeID5() > 0)
					sb.append(" and c.nClienttypeID5=" + qdlwi.getEnterpriseTypeID5());
				if (qdlwi.getEnterpriseTypeID6() > 0)
					sb.append(" and c.nClienttypeID6=" + qdlwi.getEnterpriseTypeID6());
				if (qdlwi.getClientManager() > 0)
					sb.append(" and c.ncustomermanageruserid=" + qdlwi.getClientManager());
				//上海电气新增扩展属性条件
				if (qdlwi.getExtendAttribute1()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
				}
				if (qdlwi.getExtendAttribute2()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
				}
				if (qdlwi.getExtendAttribute3()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
				}
				if (qdlwi.getExtendAttribute4()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
				}
				sb.append(" group by vc.consignclientid \n");

			}
			else
			{
				sb.append(" select vc.consignclientid consignclientid, sum(round(da.mbalance,2)) Balance \n");
				sb.append(" from Client c,sett_account a,sett_subaccount sa,sett_dailyaccountbalance da,sett_vcontractinfo vc,sett_accountType sat \n");
				sb.append(" where c.id = a.nclientid and a.id = sa.naccountid \n");
				sb.append(" and a.nofficeid = " + qdlwi.getOfficeID() + " and a.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
				sb.append(" and a.naccounttypeid=sat.id and sat.nAccountGroupID =" + SETTConstant.AccountGroupType.CONSIGN + " and sa.al_nLoanNoteID = vc.loanPayID \n");
				sb.append(" and vc.loantypeid in(" + qdlwi.getLoanTypeID() + ") \n");
				sb.append(" and sa.id=da.nsubaccountid and da.dtdate=to_date('" + DataFormat.getDateString(qdlwi.getDate()) + "','yyyy-mm-dd') \n");
				if (qdlwi.getEnterpriseTypeID1() > 0)
					sb.append(" and c.nClienttypeID1=" + qdlwi.getEnterpriseTypeID1());
				if (qdlwi.getEnterpriseTypeID2() > 0)
					sb.append(" and c.nClienttypeID2=" + qdlwi.getEnterpriseTypeID2());
				if (qdlwi.getEnterpriseTypeID3() > 0)
					sb.append(" and c.nClienttypeID3=" + qdlwi.getEnterpriseTypeID3());
				if (qdlwi.getEnterpriseTypeID4() > 0)
					sb.append(" and c.nClienttypeID4=" + qdlwi.getEnterpriseTypeID4());
				if (qdlwi.getEnterpriseTypeID5() > 0)
					sb.append(" and c.nClienttypeID5=" + qdlwi.getEnterpriseTypeID5());
				if (qdlwi.getEnterpriseTypeID6() > 0)
					sb.append(" and c.nClienttypeID6=" + qdlwi.getEnterpriseTypeID6());
				if (qdlwi.getClientManager() > 0)
					sb.append(" and c.ncustomermanageruserid=" + qdlwi.getClientManager());
				//上海电气新增扩展属性条件
				if (qdlwi.getExtendAttribute1()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE1 = " + qdlwi.getExtendAttribute1() + " \n");
				}
				if (qdlwi.getExtendAttribute2()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE2 = " + qdlwi.getExtendAttribute2() + " \n");
				}
				if (qdlwi.getExtendAttribute3()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE3 = " + qdlwi.getExtendAttribute3() + " \n");
				}
				if (qdlwi.getExtendAttribute4()>0)
				{
					sb.append(" and c.NEXTENDATTRIBUTE4 = " + qdlwi.getExtendAttribute4() + " \n");
				}
				sb.append(" group by vc.consignclientid \n");
			}
			
            ps = con.prepareCall(sb.toString());
            rs = ps.executeQuery();
            while (rs.next())
            {
                QueryDepositLoanResultInfo o = new QueryDepositLoanResultInfo();
                o.setConsignClientID(rs.getLong("consignclientid"));
                o.setSumBalance(rs.getDouble("Balance"));
                v.add(o);
            }

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return v.size() > 0 ? v : null;
	}
}
