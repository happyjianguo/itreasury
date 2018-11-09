/*
 * Created on 2004-3-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.repayplan.dao;

import java.util.*;
import java.sql.*;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.repayplan.dataentity.*;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PlanModifyDao
{

	public PlanModifyDao()
	{
	}
    public static void main(String[] args)
    {
        try
        {
            RepayPlanDao dao = new RepayPlanDao();

            Collection c = dao.findPlanByContract(507,1000,1,1,1);
            System.out.println("*************=" + c.size());
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
	/**
	 * 根据计划标示查找还款计划
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>查找还款计划</b>
	 * <ul>
	 * <li>操作数据库表ContractPayPlan
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long       lID                还款计划标示       
	 *
	 * @param     long       lUserID            用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long       lOfficeID          办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    PayPlanInfo     
	 *
	 * @exception Exception
	**/
	public PlanModifyInfo findPlanModifyByID(long lID)
	{
		PlanModifyInfo info = new PlanModifyInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try
		{
			ContractDao contractDao = new ContractDao();
			conn = Database.getConnection();
			sb.append("select * from loan_PlanModifyForm where 1=1 and ID = ?");
			Log.print(" ======= PlanModifyDao.findPlanModifyByID() Start ... ");
			Log.print(sb.toString());
			Log.print(" ======= PlanModifyDao.findPlanModifyByID() End ... ");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				info.setID(rs.getLong("ID"));
				info.setNextCheckUserID(rs.getLong("nnextcheckuserid"));
				info.setNextCheckUserLevel(rs.getLong("nNextCheckLevel"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setPlanID(rs.getLong("nPlanID"));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setContractID(rs.getLong("nContractID"));
				info.setInputDate(rs.getTimestamp("dtInput"));
				info.setOfficeID(rs.getLong("nOfficeID"));
				
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
			}
		}
		return info;
	}
	/**
	 * 通过合同ID查找计划
	 * @param lID 合同ID
	 * @return
	 */
	
	
	public PlanModifyInfo findPlanModifyByContractID(long lID)
	{
		PlanModifyInfo info = new PlanModifyInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try
		{
			ContractDao contractDao = new ContractDao();
			conn = Database.getConnection();
			sb.append("select * from loan_PlanModifyForm where 1=1 and nstatusid != 0 and ncontractid = ?");
			Log.print(" ======= PlanModifyDao.findPlanModifyByID() Start ... ");
			Log.print(sb.toString());
			Log.print(" ======= PlanModifyDao.findPlanModifyByID() End ... ");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				info.setID(rs.getLong("ID"));
				info.setNextCheckUserID(rs.getLong("nnextcheckuserid"));
				info.setNextCheckUserLevel(rs.getLong("nNextCheckLevel"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setPlanID(rs.getLong("nPlanID"));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setContractID(rs.getLong("nContractID"));
				info.setInputDate(rs.getTimestamp("dtInput"));
				info.setOfficeID(rs.getLong("nOfficeID"));
				
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
			}
		}
		return info;
	}



    
}
