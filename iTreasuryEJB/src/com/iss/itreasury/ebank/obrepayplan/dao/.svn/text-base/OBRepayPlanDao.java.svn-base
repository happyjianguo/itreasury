/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obrepayplan.dao;

import com.iss.itreasury.ebank.obrepayplan.dataentity.*;
import com.iss.itreasury.ebank.util.*;
import java.util.*;
import com.iss.itreasury.ebank.obdataentity.OBSecurityInfo;
import com.iss.itreasury.ebank.obdataentity.OBPageInfo;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.util.*;
import java.rmi.RemoteException;
import com.iss.itreasury.settlement.util.SETTConstant;
import java.sql.*;


/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBRepayPlanDao extends OBBaseDao
{
	/**
	 * 执行计划更改合同查找（合同的状态为“执行中”）
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param OBQueryContractInfo  查询条件
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findContractByMultiOption(OBQueryContractInfo o) throws Exception
	{
		/*
		strSQL_pre =
							"SELECT COUNT(*) FROM loan_contractform bb, "
								+ " (select nContractID,nNextCheckUserID,nInputUserID,nStatusID  from loan_PlanModifyForm where nStatusID = "
								+ LOANConstant.PlanModifyStatus.SUBMIT
								+ " )  cc ";
						strSQL_con =
							" WHERE bb.nTypeID != "
								+ LOANConstant.LoanType.TX
								+ "and  cc.nContractID(+) = bb.ID and (cc.nNextCheckUserID is null or cc.nNextCheckUserID = cc.nInputUserID) and bb.NCURRENCYID="
								+ o.getCurrencyID()
								+ " AND bb.NOFFICEID="
								+ o.getOfficeID();
		*/
		return null;
	}

	/**
	 * 判断合同执行计划是否能够被指定的来源修改
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lID                   合同标示
	 * @param     long     lSourceID             来源标示
	 * @param     long     lUserID               用户标示
	 * @return    long
	 **/
	public long findCanBeModify(long lContractID,OBSecurityInfo sInfo) throws Exception
	{
		return 1;
/*
		long lResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lStatusID = -1;
		long lisUsed = -1;
		long lUserType = -1;

		try
		{
			conn = Database.getConnection();

			sb.append(
				"select NSTATUSID,NISUSED,NUSERTYPE from loan_loanContractPlan where NCONTRACTID = ? and NPLANVERSION = (select max(NPLANVERSION) from loan_loanContractPlan where NCONTRACTID = ?)");

			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lStatusID = rs.getLong(1);
				lisUsed = rs.getLong(2);
				lUserType = rs.getLong(3);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			if (lStatusID == 0 && lisUsed == 1 && lUserType != lSourceID)
			{ // 说明正在修改中而且不是菜单中的修改
				lResult = 1;
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new RemoteException(ex.getMessage());
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
				throw new RemoteException(ex.getMessage());
			}
		}
		return lResult;
*/
	}

	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     分页和排序的信息
	 * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findPlanVerByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws Exception
	{
		return null;
	}

	/**
	 * 生成一个版本号为空的新版本，并复制版本明细
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBPlanDetailInfo     dInfo
	 * @param     OBSecurityInfo     sInfo
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection addTempVersion(OBPlanDetailInfo dInfo) throws Exception
	{
		return null;
	}

	/**
	 * 根据执行计划版本的ID查找计划明细信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     versionID
	 * @param     OBPageInfo     pInfo 翻页信息
	 * @param     OBSecurityInfo sInfo 安全相关信息
	 * @return    Collection
	 * @exception Exception
	**/
	public Collection findPlanByVer(long versionID,OBPageInfo pInfo,OBSecurityInfo sInfo) throws Exception
	{
		return  null;
	}

	/**
	 * 自动新增还款计划
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBPlanAssignInfo     o
	 * @return    long     新增或修改成功，
	 * @exception Exception
	**/
	public long autoSavePlan(OBPlanAssignInfo o) throws Exception
	{
		return 1;
	}

	/**
	 * 根据计划标示查找还款计划
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param     long       lID                还款计划标示
	 * @return    PayPlanInfo
	 * @exception Exception
	**/
	public OBRepayPlanInfo findPlanDetailByID(long lID,OBSecurityInfo sInfo) throws Exception
	{
		OBRepayPlanInfo rp_info = new OBRepayPlanInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try
		{
			ContractDao contractDao = new ContractDao();
			conn = Database.getConnection();
			sb.append("select a.*,b.nContractID from loan_loanContractPlanDetail a,loan_loanContractPlan b where a.nContractPlanID = b.ID and a.ID = ?");
			Log.print(" ======= RepayPlanNewEJB.findPlanByID() Start ... ");
			Log.print(sb.toString());
			Log.print(" ======= RepayPlanNewEJB.findPlanByID() End ... ");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				rp_info.setID(rs.getLong("ID"));
				rp_info.setVersionNo(rs.getLong("nContractPlanID"));
				rp_info.setPlanDate(rs.getTimestamp("DTPLANDATE"));
				rp_info.setExecuteInterestRate(contractDao.getLatelyRate(0, rs.getLong("nContractID"), rp_info.getPlanDate()).getLateRate());
				rp_info.setLoanOrRepay(rs.getInt("NPAYTYPEID"));
				rp_info.setAmount(rs.getDouble("MAMOUNT"));
				rp_info.setType(rs.getString("STYPE"));
				rp_info.setInputDate(rs.getTimestamp("DTMODIFYDATE"));
				rp_info.setLastExtendID(rs.getLong("NLASTEXTENDID"));
				rp_info.setLastOverDueID(rs.getLong("NLASTOVERDUEID"));
				//rp_info.lisOverDue = rs.getLong("NISOVERDUE");
				rp_info.setPlanBalance(getPlanBalance(rs.getLong("nContractID"), rp_info.getVersionNo(), rp_info.getID()));
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
			throw new RemoteException(ex.getMessage());
		}
		finally
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return rp_info;

	}

	/**
	* 新增还款计划或者修改
	* @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	* @param     OBPlanDetailInfo       detail
	* @param     OBSecurityInfo         sInfo
	* @return    long     新增或修改成功
	* @exception Exception
	**/
	public long addPlan(OBPlanDetailInfo detail) throws Exception
	{
		return 1;
	}

	/**
	* 修改原始计划
	* @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	* @param     OBPlanDetailInfo       detail
	* @param     OBSecurityInfo         sInfo
	* @return    long     新增或修改成功
	* @exception Exception
	**/
	public long updatePlan(OBPlanDetailInfo detail) throws Exception
	{
		return 1;
	}

	/**
	* 删除原始计划明细
	* @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	* @param     long       lID
	* @param     OBSecurityInfo         sInfo
	* @return    long     新增或修改成功
	* @exception Exception
	**/
	public long deletePlan(long lID,OBSecurityInfo sInfo) throws Exception
	{
		return 1;
	}

	/**
	 * 修改新版计划状态
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long        lID                     版本纪录标示
	 * @param     long        newStatus               新状态
	 * @param     OBSecurityInfo sInfo
	 * @return    long        成功，返回值 == 1，失败，返回值 == 0。
	 * @exception Exception
	**/
	public long updateStatus(long lID, long newStatus,OBSecurityInfo sInfo) throws Exception
	{
		return 1;
	}

	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     分页和排序的信息
	 * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findPlanByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws Exception
	{
		return null;
	}

	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     分页和排序的信息
	 * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findTempPlanVerByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws Exception
	{
		return null;
	}

	/**
	 * 根据执行计划版本的ID查找计划明细信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     versionID
	 * @param     OBPageInfo     pInfo 翻页信息
	 * @param     OBSecurityInfo sInfo 安全相关信息
	 * @return    Collection
	 * @exception Exception
	**/
	public Collection findTempPlanByVer(long versionID,OBPageInfo pInfo,OBSecurityInfo sInfo) throws Exception
	{
		return  null;
	}

	/**
	 * 根据ContractPayPlanVersion中的nContractID查找计划版本信息
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     long     lContractID
	 * @param     OBPageInfo     分页和排序的信息
	 * @param     OBSecurityInfo 有关安全的信息
	 * @return    Collection
	 * @exception Exception
	 **/
	public Collection findTempPlanByContract(long lContractID, OBPageInfo pageInfo,OBSecurityInfo securityInfo) throws Exception
	{
		return null;
	}

	// 计算计划余额：lID：合同标识  lVersionID: 版本标识  lPlanListID  计划明细标示
	private static double getPlanBalance(long lContractID, long lVersionID, long lPlanListID) throws RemoteException
	{
		double dResult = 0;
		double dRepayTotal = -1;
		double dPlanTotal = -1;
		double dTmp = 0;
		double dListAmount = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try
		{
			conn = Database.getConnection();
			sb.append(
				" select sum(mOpenAmount-mBalance) MREPAYTOTAL from sett_subAccount where AL_nLoanNoteID in (select id from loan_payform where nContractID = "
					+ lContractID
					+ " ) and nStatusID = "
					+ SETTConstant.SubAccountStatus.NORMAL);
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dRepayTotal = rs.getDouble("MREPAYTOTAL");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			sb.append(
				"select nContractPlanID,sum(MAMOUNT) MPLANTOTAL from loan_loanContractPlanDetail where NPAYTYPEID = "
					+ LOANConstant.PlanType.REPAY
					+ " and nContractPlanID = "
					+ lVersionID
					+ " group by nContractPlanID");
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dPlanTotal = rs.getDouble("MPLANTOTAL");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			sb.append(
				"select sum(mAmount) from loan_loanContractPlanDetail where nPayTypeID = "
					+ LOANConstant.PlanType.REPAY
					+ " and nContractPlanID = "
					+ lVersionID
					+ " and dtPlanDate < (select dtplandate from loan_loanContractPlanDetail where id ="
					+ lPlanListID
					+ " )");
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dTmp = rs.getDouble(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			sb.append("select mAmount from loan_loanContractPlanDetail where id =" + lPlanListID);
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dListAmount = rs.getDouble(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			System.out.println("dResult is :" + dResult);
			System.out.println("dRepayTotal is :" + dRepayTotal);
			System.out.println("dPlanTotal is :" + dPlanTotal);
			System.out.println("dTmp is :" + dTmp);
			System.out.println("dListAmount is :" + dListAmount);

			if ((dRepayTotal - dTmp) > 0)
			{
				if (dListAmount > (dRepayTotal - dTmp))
				{
					dResult = dListAmount - (dRepayTotal - dTmp);
				}
				else
				{
					dResult = 0;
				}

			}
			else
			{
				dResult = dListAmount;
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new RemoteException(ex.getMessage());
		}
		finally
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return dResult;
	}

}
