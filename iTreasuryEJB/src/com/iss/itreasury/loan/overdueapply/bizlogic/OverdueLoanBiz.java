package com.iss.itreasury.loan.overdueapply.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.overdueapply.dao.OverdueLoanDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class OverdueLoanBiz {
	
	OverdueLoanDao overdueLoanDao = new OverdueLoanDao();

	public PagerInfo queryContractByMultiOption(long lLoanType,long lCurrencyID,long lOfficeID,long lUserID,long lContractIDFrom,
			long lContractIDTo,long lClientID,String tsLoanStart,String tsLoanEnd,long lStatusID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = overdueLoanDao.queryContractByMultiOption(lLoanType,lCurrencyID,lOfficeID,lUserID,lContractIDFrom,
					lContractIDTo,lClientID,tsLoanStart,tsLoanEnd,lStatusID);
			
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(OverdueLoanBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList resultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		Map<String, Long> params = new HashMap<String, Long>();
		
		long contractID = -1;
		long planVersionID = -1;
		long isPlanModifying = -1;
		long isOverDueModifying = -1;
		long isUsed = -1;
		long userType = -1;
		String contractCode = null;
		String borrowClientName = null;
		double loanAmount = 0.00;
		Timestamp loanStart = null;
		Timestamp loanEnd = null;
		long loanTime = -1;
		long statusID = -1;
		
		try {
			while(rs.next()){
				contractID = rs.getLong("ContractID");
				contractCode = rs.getString("ContractCode");
				borrowClientName = rs.getString("BorrowClientName");
				loanAmount = rs.getDouble("MEXAMINEAMOUNT");
				loanStart = rs.getTimestamp("DTSTARTDATE");
				loanEnd = rs.getTimestamp("DTENDDATE");
				loanTime = rs.getLong("LoanTime");
				statusID = rs.getLong("NSTATUSID");
				
				params = this.getPlanVersionID(contractID);
				
				planVersionID = params.get("PlanVersionID");
				isPlanModifying = params.get("IsPlanModifying");
				isOverDueModifying = params.get("IsOverDueModifying");

				if ((isPlanModifying == Constant.YesOrNo.NO)
						&& (isUsed == Constant.YesOrNo.YES)
						&& (userType != Constant.RecordStatus.INVALID)
						&& (userType != LOANConstant.PlanModifyType.OVERDUE)) {
					
					isPlanModifying = Constant.YesOrNo.YES;
				}
				if ((isPlanModifying == Constant.YesOrNo.NO)
						&& (isUsed == Constant.YesOrNo.YES)
						&& (userType == LOANConstant.PlanModifyType.OVERDUE)) {
					
					isOverDueModifying = Constant.YesOrNo.YES;
				}
				//存储列数据
				cellList = new ArrayList();
				if(isPlanModifying == Constant.YesOrNo.NO){
					PagerTools.returnCellList(cellList,DataFormat.formatString(contractCode)+","+contractID+","+planVersionID+","+isOverDueModifying);
				}else{
					PagerTools.returnCellList(cellList,DataFormat.formatString(contractCode)+",-1,-1,0");
				}
				PagerTools.returnCellList(cellList,DataFormat.formatString(borrowClientName));
				PagerTools.returnCellList(cellList,"￥"+DataFormat.formatDisabledAmount(loanAmount,0));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(loanStart)+" 到 "+DataFormat.getDateString(loanEnd));
				PagerTools.returnCellList(cellList,loanTime);
				PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(statusID));
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	//获得表格其他字段的信息
	public Map<String, Long> getPlanVersionID(long contractID){
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Long> map = new HashMap<String, Long>();
		long temp_ID = -1;
		long temp_isUsed = -1;
		long temp_userType = -1;
		long isPlanModifying = Constant.YesOrNo.NO;
		long isOverDueModifying = -1;
		String sql = "";
		try {
			con = Database.getConnection();
			sql = "select id from loan_loancontractplan "
				+ " where nstatusid = "+Constant.RecordStatus.INVALID+" and nplanversion in ( "
				+ " 	select max(nplanversion) from loan_loancontractplan "
				+ " 		where ncontractid = "+contractID+" ) and ncontractid = "+contractID;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				isPlanModifying = Constant.YesOrNo.YES;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sql = "";
			
			sql = "select id,nisused,nusertype from loan_loancontractplan "
				+ " where nstatusid = "+Constant.RecordStatus.VALID+" and nplanversion in ( "
				+ " 	select max(nplanversion) from loan_loancontractplan "
				+ " 		where ncontractid = "+contractID+" ) and ncontractid = "+contractID;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				temp_ID = rs.getLong("id");
				temp_isUsed = rs.getLong("nisused");
				temp_userType = rs.getLong("nusertype");
				isOverDueModifying = Constant.YesOrNo.NO;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			
			
			map.put("isUsed", temp_isUsed);
			map.put("PlanVersionID", temp_ID);
			map.put("userType", temp_userType);
			map.put("IsPlanModifying", isPlanModifying);
			map.put("IsOverDueModifying", isOverDueModifying);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public PagerInfo queryOverDuePlanByContractPlanID(long lContractPlanID,long lCurrencyID,long lOfficeID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = overdueLoanDao.queryOverDuePlanByContractPlanID(lContractPlanID);
			
			pagerInfo.setSqlString(sql);
			
			Map<String, Long> params = new HashMap<String, Long>();
			params.put("lCurrencyID", lCurrencyID);
			params.put("lOfficeID", lOfficeID);
			
			pagerInfo.setExtensionMothod(OverdueLoanBiz.class, "resultHandleForOverDuePlan",params);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList resultHandleForOverDuePlan(ResultSet rs,Map<String, Long> params) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		int iSerialNo = 1;
		long ID = -1;
		long contractID = -1;
		long isUsed = -1;
		long userType = -1;
		long isOverDueModifying = -1;
		long overDueStatusID = -1;
		long overDueInfoNewID = -1;
		long checkUserID = -1;
		long lOfficeID = params.get("lOfficeID");
		long lCurrencyID = params.get("lCurrencyID");
		double dAmount = 0.00;
		double dBalanceRePayTmp = 0.00;
		double dPlanPayAmount = 0.00;
		double dPlanBalance = 0.00;
		double dFineAmount = 0.00;
		double dFineInterestRate = 0.00;
		Timestamp tsPlanDate = null;
		Timestamp tsFineDate = null;
		
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				contractID = rs.getLong("NCONTRACTID");
				isUsed = rs.getLong("NISUSED");
				userType = rs.getLong("NUSERTYPE");
				dPlanPayAmount = rs.getDouble("MAMOUNT");
				overDueStatusID = rs.getLong("NSTATUSID");
				overDueInfoNewID = rs.getLong("OverDueID");
				tsPlanDate = rs.getTimestamp("DTPLANDATE");
				
				isOverDueModifying = Constant.YesOrNo.NO;
				if(isUsed == Constant.YesOrNo.YES && userType == LOANConstant.PlanModifyType.OVERDUE){
					// 表明有执行在修改提交之中，
					// 这时链接的下一个页面最多只能往下下个页面出一个超级链接，
					// 即逾期申请修改
					isOverDueModifying = Constant.YesOrNo.YES;
				}
				if(contractID > 0){
					ContractDao cdao = new ContractDao();
					ContractInfo cInfo = new ContractInfo();
					ContractAmountInfo AInfo = new ContractAmountInfo();
					cInfo = cdao.findByID(contractID);
					dAmount = cInfo.getExamineAmount();
					AInfo = cInfo.getAInfo();
					dBalanceRePayTmp = AInfo.getRepayAmount();
				}
				/**
				 * 如下是处理计划贷款余额用的 采用的原则是“先借先还”的概念自动计算
				 */
				if(dBalanceRePayTmp > 0){
					if(dBalanceRePayTmp >= dPlanPayAmount){
						dPlanBalance = 0;
						dBalanceRePayTmp = dBalanceRePayTmp - dPlanPayAmount;
					}else{
						dPlanBalance = dPlanPayAmount - dBalanceRePayTmp;
						dBalanceRePayTmp = 0;
					}
				}else{
					dPlanBalance = dPlanPayAmount;
				}
				if(overDueInfoNewID > 0){
					dFineAmount = rs.getDouble("MFINEAMOUNT");				//罚息金额
					dFineInterestRate = rs.getDouble("MFINEINTERESTRATE");	//罚息利率
					tsFineDate = rs.getTimestamp("DTFINEDATE");				//罚息日期
					checkUserID = rs.getLong("NNEXTCHECKUSERID");			//审核人ID
				}else{
					dFineAmount = -1; 		// 罚息金额
					dFineInterestRate = -1;	// 罚息利率
					tsFineDate = null; 		// 罚息日期
				}
				if(overDueInfoNewID <= 0){
					Timestamp tsTodayTmp = null;
					Timestamp tsPlanDayTmp = null;
					tsTodayTmp = Env.getSystemDate(Database.getConnection(), lOfficeID, lCurrencyID);
					tsPlanDayTmp = DataFormat.getDateTime(DataFormat.getDateString(tsPlanDate));

					if(DataFormat.getDateString(tsPlanDayTmp).compareTo(DataFormat.getDateString(tsTodayTmp)) <= 0){
						// 表明是已逾期，但是还没有提交逾期申请！
						overDueStatusID = LOANConstant.OverDueStatus.YES;
					}else{
						overDueStatusID = LOANConstant.OverDueStatus.NOTYET;
					}
				}
				
				//存储列数据
				cellList = new ArrayList();
				if(dPlanBalance > 0){
					if((overDueStatusID == LOANConstant.OverDueStatus.YES && isOverDueModifying == Constant.YesOrNo.NO)
							|| overDueStatusID == LOANConstant.OverDueStatus.SUBMIT){
						
						PagerTools.returnCellList(cellList,(iSerialNo++)+","+ID+","+overDueInfoNewID+","+dPlanBalance);
					}else if(overDueStatusID == LOANConstant.OverDueStatus.CHECK 
							|| overDueStatusID==LOANConstant.OverDueStatus.APPROVALING){//表明是已复核  已复核的查看页面
						
						PagerTools.returnCellList(cellList,(iSerialNo++)+","+" ");
					}else{
						PagerTools.returnCellList(cellList,(iSerialNo++)+","+" ");
					}
				}else{
					PagerTools.returnCellList(cellList,(iSerialNo++)+","+" ");
				}
				PagerTools.returnCellList(cellList,"￥"+DataFormat.formatDisabledAmount(dPlanPayAmount,0));
				PagerTools.returnCellList(cellList,"￥"+DataFormat.formatDisabledAmount(dPlanBalance,0));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(tsPlanDate));
				PagerTools.returnCellList(cellList,dFineAmount > 0?"￥"+DataFormat.formatListAmount(dFineAmount):"");
				PagerTools.returnCellList(cellList,DataFormat.getDateString(tsFineDate));
				PagerTools.returnCellList(cellList,dFineInterestRate > 0?DataFormat.formatRate(dFineInterestRate)+"%":"");
				if(dPlanBalance > 0){
					PagerTools.returnCellList(cellList,LOANConstant.OverDueStatus.getName(overDueStatusID));
				}else{
					PagerTools.returnCellList(cellList,"没有逾期");
				}
				
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

}
