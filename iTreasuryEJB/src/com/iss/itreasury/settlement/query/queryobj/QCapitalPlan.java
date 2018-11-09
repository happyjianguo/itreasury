package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.settlement.query.paraminfo.QCapitalPlanParamInfo;
import com.iss.itreasury.settlement.query.resultinfo.QCapitalPlanResulInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class QCapitalPlan
{

	/**
	 * @param args
	 * @throws IException 
	 */
	public static void main(String[] args) throws IException
	{
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		String str = DateFormat.getDateInstance().format(calendar.getTime());
		QCapitalPlanParamInfo info = new QCapitalPlanParamInfo();
		info.setStrClientID("01-0018");
		info.setStartDate(new Timestamp(calendar.getTime().getTime() - 60 * 60
				* 60 * 12 * 365));
		info.setEndDate(new Timestamp(calendar.getTime().getTime()+60 * 60
				* 60 * 12 * 365));
		QCapitalPlan biz = new QCapitalPlan();
		System.out.println(biz.query(info));
	}

	/**
	 * 返回周期计划信息
	 * 
	 * @return
	 * @throws IException 
	 */
	public HashMap getPeriod() throws IException
	{
		HashMap hm = new HashMap();
		long period = -1;
		Timestamp time = null;
		StringBuffer strSQL = new StringBuffer();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			strSQL.append("select period,startdate from SETT_PERIODSETTING");
			System.out.println(" strSQL==： \n" + strSQL.toString());
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				period = rs.getLong("period");
				time = rs.getTimestamp("startdate");
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		hm.put("period", new Long(period));
		hm.put("startdate", time);
		return hm;
	}

	/**
	 * 返回客户的收付金额
	 * 
	 * @param info
	 * @return
	 * @throws IException 
	 */
	private Collection getResultInfo(QCapitalPlanParamInfo info) throws IException
	{
		double Amount = 0.0;
		long AccountGroupID = 0;
		long TransDirection = 0;

		HashMap hm = null;
		ArrayList al = new ArrayList();
		StringBuffer strSQL = new StringBuffer();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		strSQL
				.append("select c.mamount,c.ntransdirection,b.naccountgroupid \n");
		strSQL
				.append("from sett_account a,sett_accounttype b,sett_transaccountdetail c,OB_PERIODPLAN d \n");
		strSQL.append("where c.ntransaccountid=a.id \n");
		strSQL.append("and a.naccounttypeid=b.id \n");
		strSQL.append("and b.nstatusId=1 \n");
		// strSQL.append("and d.clientid=a.nclientid \n");
		strSQL.append("and d.statusid<>0 \n");
		if (!info.getStrClientID().equals(""))
			strSQL.append("and d.clientid=(select id from client where scode='"
					+ info.getStrClientID() + "') \n");
		if (info.getStartDate() != null)
			strSQL.append("and c.dtexecute>=to_date('"
					+ info.getStartDate().toString().substring(0, 10)
					+ "','yyyy-mm-dd') \n");
		if (info.getEndDate() != null)
			strSQL.append("and c.dtexecute<=to_date('"
					+ info.getEndDate().toString().substring(0, 10)
					+ "','yyyy-mm-dd')");

		System.out.println(" strSQL==：\n" + strSQL.toString());

		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				hm = new HashMap();
				Amount = rs.getDouble("mamount");
				AccountGroupID = rs.getLong("naccountgroupid");
				TransDirection = rs.getLong("ntransdirection");
				hm.put("Amount", new Double(Amount));
				hm.put("AccountGroupID", new Long(AccountGroupID));
				hm.put("TransDirection", new Long(TransDirection));
				al.add(hm);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return al;
	}

	/**
	 * 返回收付款结果集的方法
	 * 
	 * @param QCapitalPlanParamInfo
	 * @throws IException 
	 */
	private HashMap getInfo(QCapitalPlanParamInfo info) throws IException
	{
		double Amount = 0.0;
		long AccountGroupID = 0;
		long TransDirection = 0;

		double gathering = 0.0;// 收款
		double payment = 0.0;// 付款

		HashMap hm = new HashMap();
		ArrayList coll = (ArrayList) this.getResultInfo(info);
		for (int i = 0; i < coll.size(); i++)
		{
			hm = (HashMap) coll.get(i);
			Amount = new Double(hm.get("Amount").toString()).doubleValue();
			AccountGroupID = new Long(hm.get("AccountGroupID").toString())
					.longValue();
			TransDirection = new Long(hm.get("TransDirection").toString())
					.longValue();
			if (AccountGroupID == SETTConstant.AccountGroupType.CURRENT
					|| AccountGroupID == SETTConstant.AccountGroupType.FIXED
					|| AccountGroupID == SETTConstant.AccountGroupType.NOTIFY
					|| AccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT
					|| AccountGroupID == SETTConstant.AccountGroupType.MARGIN)
			{
				if (TransDirection == 1)
					gathering = gathering + Amount;
				else if (TransDirection == 2)
					payment = payment + Amount;
			} else if (AccountGroupID == SETTConstant.AccountGroupType.TRUST
					|| AccountGroupID == SETTConstant.AccountGroupType.CONSIGN
					|| AccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
					|| AccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
			{
				if (TransDirection == 1)
					payment = payment + Amount;
				else if (TransDirection == 2)
					gathering = gathering + Amount;
			}
		}
		hm.put("payment", new Double(payment));
		hm.put("gathering", new Double(gathering));
		return hm;
	}

	/**
	 * 返回计划周期表信息
	 * 
	 * @param info
	 * @return
	 * @throws IException 
	 */
	private Collection getPeriodInfo(QCapitalPlanParamInfo info) throws IException
	{
		QCapitalPlanResulInfo rInfo;
		ArrayList al = new ArrayList();
		StringBuffer strSQL = new StringBuffer();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			strSQL.append("select * from OB_PERIODPLAN \n");
			strSQL.append("where 1=1 \n");
			if (!info.getStrClientID().equals(""))
				strSQL.append("and clientid=(select id from client where scode='"
						+ info.getStrClientID() + "') \n");
			if (info.getStartDate() != null)
				strSQL.append("and startdate>=to_date('" + info.getStartDate().toString().substring(0,10)
						+ "','yyyy-mm-dd') \n");
			if (info.getEndDate() != null)
				strSQL.append("and enddate<to_date('" + info.getEndDate().toString().substring(0,10)
						+ "','yyyy-mm-dd') \n");
			strSQL.append(" and statusid = 2");
			System.out.println(" strSQL==： \n" + strSQL.toString());
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				rInfo = new QCapitalPlanResulInfo();
				rInfo.setClientID(rs.getLong("clientid"));
				rInfo.setStartDate(rs.getTimestamp("startdate"));
				rInfo.setEndDate(rs.getTimestamp("enddate"));
				rInfo.setPlanPayout(rs.getDouble("payamount"));
				rInfo.setPlanEarning(rs.getDouble("receivamount"));
				al.add(rInfo);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return al;
	}
	
	public Collection query(QCapitalPlanParamInfo info) throws IException
	{
		HashMap hm = new HashMap();
		QCapitalPlanParamInfo pinfo;
		QCapitalPlanResulInfo rInfo = new QCapitalPlanResulInfo();
		ArrayList al = (ArrayList) this.getPeriodInfo(info);
		ArrayList al1 = new ArrayList();
		for(int i=0;i<al.size();i++)
		{
			rInfo = (QCapitalPlanResulInfo) al.get(i);
			pinfo = new QCapitalPlanParamInfo();
			pinfo.setStrClientID(info.getStrClientID());
			pinfo.setStartDate(rInfo.getStartDate());
			pinfo.setEndDate(rInfo.getEndDate());
			hm = this.getInfo(pinfo);
			rInfo.setFactPayout(new Double(hm.get("payment").toString()).doubleValue());
			rInfo.setFactEarning(new Double(hm.get("gathering").toString()).doubleValue());
			rInfo.setPayoutBalance(rInfo.getPlanPayout() - (new Double(hm.get("payment").toString()).doubleValue()));
			rInfo.setEarningBalance(rInfo.getPlanEarning() - (new Double(hm.get("gathering").toString()).doubleValue()));
			al1.add(rInfo);
		}
		return al1;		
	}
}
