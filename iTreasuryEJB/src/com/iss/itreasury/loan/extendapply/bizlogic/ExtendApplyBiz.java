package com.iss.itreasury.loan.extendapply.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.extendapply.dao.ExtendApplyDao_new;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class ExtendApplyBiz {
	
	ExtendApplyDao_new extendApplyDao = new ExtendApplyDao_new();
	
	/**
	 * 合同查找，根据条件查询loan_ContractForm和loan_LoanForm表。
	 * lContractIDFrom和lContractIDTo，同一类型的合同的流水号的部分作为查询范围
	 * @param lInputUserID 登录人标识
	 * @param lCurrencyID 币种标识
	 * @param lOfficeID 办事处标识
	 * @param lTypeID 贷款申请类型标识
	 * @param lContractIDFrom 合同编号起始
	 * @param lContractIDTo 合同编号结束
	 * @param lClientID 借款单位标识
	 * @return PagerInfo
	 * @exception Exception
	 * @author zk 2012-12-31
	 * 
	 */
	public PagerInfo queryContractByMultiOption(long lInputUserID,long lCurrencyID,long lOfficeID,long lTypeID,
			long lContractIDFrom,long lContractIDTo,long lClientID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = extendApplyDao.queryContractByMultiOption(lInputUserID,lCurrencyID,lOfficeID,lTypeID,lContractIDFrom,lContractIDTo,lClientID);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(ExtendApplyBiz.class, "extendApplyResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList extendApplyResultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long ID = -1;
		String sContractCode = null;
		String clientName = null;
		long nTypeID = -1;
		double mexamineAmount = 0.00;
		Timestamp dtStartDate = null;
		long nIntervalNum = -1;
		long isExtend = -1;
		String strIsExtend = "";
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				sContractCode = rs.getString("SCONTRACTCODE") == null ? "" : rs.getString("SCONTRACTCODE");
				clientName = rs.getString("SNAME") == null ? "" : rs.getString("SNAME");
				nTypeID = rs.getLong("NTYPEID");
				mexamineAmount = rs.getDouble("MEXAMINEAMOUNT");
				dtStartDate = rs.getTimestamp("DTSTARTDATE");
				nIntervalNum = rs.getLong("NINTERVALNUM");
				isExtend = rs.getLong("ISEXTEND");
				
				if(isExtend == 1){
					strIsExtend = "是";
				}else{
					strIsExtend = "否";
				}
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,sContractCode+","+ID+","+getExtendStatus(ID));
				PagerTools.returnCellList(cellList,clientName);
				PagerTools.returnCellList(cellList,LOANConstant.LoanType.getName(nTypeID));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mexamineAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatDate(dtStartDate));
				PagerTools.returnCellList(cellList,nIntervalNum);
				PagerTools.returnCellList(cellList,strIsExtend);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	//如果合同有展期并状态为提交则值为2
	private long getExtendStatus(long lContractID) throws RemoteException{
		
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try{
			conn = Database.getConnection();
			sb.append("select ID from loan_ExtendForm where ncontractid = " + lContractID + " and nstatusid = 2");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getLong(1) > 0){
					lResult = 2;
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
		} catch(Exception ex){
			ex.printStackTrace();
			throw new RemoteException(ex.getMessage());
		} finally{
			try{
				if (rs != null){
					rs.close();
					rs = null;
				}
				if (ps != null){
					ps.close();
					ps = null;
				}
				if (conn != null){
					conn.close();
					conn = null;
				}
			} catch (Exception ex){
				throw new RemoteException(ex.getMessage());
			}
		}
		return lResult;
	}
	/**
	 * 查找合同的最新版本还款计划
	 * @param lContractID 合同标识
	 * @return PagerInfo
	 * @exception Exception
	 * @author zk 2012-12-31
	 * 
	 */
	public PagerInfo queryPlanByContract(long lContractID, String lateRateString) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = extendApplyDao.queryPlanByContract(lContractID);
			pagerInfo.setSqlString(sql);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("lateRateString", lateRateString);
			
			pagerInfo.setExtensionMothod(ExtendApplyBiz.class, "getPlanResultByContract", map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList getPlanResultByContract(ResultSet rs, Map map) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long checkboxID = -1;
		long ID = -1;
		long nContractID = -1;
		long nContractPlanID = -1;
		long nLastOverDueID = -1;
		long lCount = 0;
		double mAmount = 0.00;
		double dPlanBalance = 0.00;
		Timestamp dtPlanDate = null;
		String lateRateString = (String) map.get("lateRateString");

		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				nContractID = rs.getLong("NCONTRACTID");
				nContractPlanID = rs.getLong("NCONTRACTPLANID");
				nLastOverDueID = rs.getLong("NLASTOVERDUEID");
				mAmount = rs.getDouble("MAMOUNT");
				dtPlanDate = rs.getTimestamp("DTPLANDATE");
				
				dPlanBalance = getPlanBalance(nContractID, nContractPlanID, ID);
				if(dPlanBalance == 0 || nLastOverDueID > 0){
					checkboxID = -1;
				}else{
					checkboxID = ID;
				}
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,checkboxID);
				PagerTools.returnCellList(cellList,++lCount);
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(dPlanBalance));
				PagerTools.returnCellList(cellList,DataFormat.formatDate(dtPlanDate));
				PagerTools.returnCellList(cellList,lateRateString);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	// 计算计划余额：lID：合同标识  lVersionID: 版本标识  lPlanListID  计划明细标示
	private static double getPlanBalance(long lContractID, long lVersionID, long lPlanListID) throws RemoteException{
		
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
