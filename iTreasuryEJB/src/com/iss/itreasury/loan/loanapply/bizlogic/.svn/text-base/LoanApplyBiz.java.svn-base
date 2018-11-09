package com.iss.itreasury.loan.loanapply.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.loanapply.dao.FormAssureDao;
import com.iss.itreasury.loan.loanapply.dao.LoanApplyDao_new;
import com.iss.itreasury.loan.loanapply.dataentity.AssureInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class LoanApplyBiz {
	
	LoanApplyDao_new loanApplyDao = new LoanApplyDao_new();
	
	/**
	 * 查询贷款申请的所有计划明细biz
	 * @author zk 2012-12-18
	 *
	 */
	public PagerInfo queryPlanByLoanID(long lLoanID, String status, String lCurrencyID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = loanApplyDao.queryPlanByLoanID(lLoanID);
			pagerInfo.setSqlString(sql);
			
			Map<String,String> params = new HashMap<String,String>();
			params.put("status", status);
			params.put("lCurrencyID", lCurrencyID);
			
			pagerInfo.setUsertext("放款合计{mamount}[where npaytypeid = "+LOANConstant.PlanType.PAY+"];还款合计{mamount}[where npaytypeid = "+LOANConstant.PlanType.REPAY+"]");
			pagerInfo.setExtensionMothod(LoanApplyBiz.class, "resultSetHandle",params);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList resultSetHandle(ResultSet rs, Map params) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		ContractOperation operation = new ContractOperation();
		
		long ID = -1;
		long loanID = -1;
		long payTypeID = -1;
		long loanType = -1;
		long lCurrencyID = -1;
		Timestamp planDate = null;
		Timestamp modifyDate = null;
		double amount = 0.00;
		double interestRate = 0.00;
		double adjustRate = 0.00;
		double staidAdjustRate = 0.00;
		String planType = "";
		String strRate = "";
		String status = (String) params.get("status");
		String strCurrencyID = (String) params.get("lCurrencyID");
		if(strCurrencyID != null && strCurrencyID.length() > 0){
			lCurrencyID = Long.parseLong((String) params.get("lCurrencyID"));
		}
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				payTypeID = rs.getLong("NPAYTYPEID");
				planDate = rs.getTimestamp("DTPLANDATE");
				modifyDate = rs.getTimestamp("DTMODIFYDATE");
				amount = rs.getDouble("MAMOUNT");
				loanType = rs.getLong("NTYPEID");
				planType = rs.getString("STYPE");
				interestRate = rs.getDouble("MINTERESTRATE");
				adjustRate = rs.getDouble("MADJUSTRATE");
				staidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
				loanID = rs.getLong("loanID");
				
				interestRate = interestRate*(1+adjustRate/100)+staidAdjustRate;
				strRate = DataFormat.formatRate(interestRate,6);
				if(loanType != 3 && loanType != 4){
					strRate = operation.getLoanLatelyRate(loanID).getLateRateString();
				}
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,ID);
				if("check".equals(status)){
					PagerTools.returnCellList(cellList,DataFormat.formatDate(planDate));
				}else{
					PagerTools.returnCellList(cellList,DataFormat.formatDate(planDate)+","+ID);
				}
				PagerTools.returnCellList(cellList,LOANConstant.PlanType.getName(payTypeID));
				PagerTools.returnCellList(cellList,Constant.CurrencyType.getSymbol(lCurrencyID)+DataFormat.formatNumber(amount,2));
				PagerTools.returnCellList(cellList,planType);
				if(interestRate == 0){
					PagerTools.returnCellList(cellList,"0.000000 %");
				}else{
					PagerTools.returnCellList(cellList,DataFormat.formatString(strRate)+" %");
				}
				PagerTools.returnCellList(cellList,DataFormat.formatDate(modifyDate));
				
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
	/**
	 * 申请贷款担保方式biz
	 * @author zk 2012-12-19
	 *
	 */
	public PagerInfo queryLoanApplyGuaranteeByLoanID(long lLoanID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = loanApplyDao.queryLoanApplyGuaranteeByLoanID(lLoanID);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(LoanApplyBiz.class, "guaranteeResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList guaranteeResultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		ContractOperation operation = new ContractOperation();
		
		long ID = -1;
		long assureTypeID = -1;
		double amount = 0.00;
		double loanAmount = 0.00;
		double assRate = 0.00;
		String clientCode = "";
		String clientName = "";
		String clientType = "";
		String clientContacter = "";
		String clientPhone = "";
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				assureTypeID = rs.getLong("NASSURETYPEID");
				amount = rs.getDouble("MAMOUNT");
				loanAmount = rs.getDouble("MLOANAMOUNT");
				clientCode = rs.getString("SCODE");
				clientName = rs.getString("SNAME");
				clientType = rs.getString("CLIENTTYPE");
				clientContacter = rs.getString("SCONTACTER") == null ? "":rs.getString("SCONTACTER");
				clientPhone = rs.getString("SPHONE") == null ? "":rs.getString("SPHONE");
				
				assRate=(amount/loanAmount)*100;

				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,ID);
				PagerTools.returnCellList(cellList,clientCode+","+ID+","+clientType+","+assureTypeID);
				PagerTools.returnCellList(cellList,clientName);
				PagerTools.returnCellList(cellList,LOANConstant.AssureType.getName(assureTypeID));
				PagerTools.returnCellList(cellList,clientContacter);
				PagerTools.returnCellList(cellList,clientPhone);
				PagerTools.returnCellList(cellList,DataFormat.formatNumber(amount,2));
				PagerTools.returnCellList(cellList,DataFormat.formatNumber(assRate,6));
				
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
	private void colseRs(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
	}

	public PagerInfo queryLoanApply(long loanID) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = "select 1 from dual";
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("loanID", loanID);

			pagerInfo.setExtensionMothod(LoanApplyBiz.class,
					"queryLoanApplyResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryLoanApplyResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		long loanID = Long.parseLong(map.get("loanID") + "");
		LoanApplyHome home = (LoanApplyHome) EJBObject
				.getEJBHome("LoanApplyHome");
		LoanApply lla = home.create();
		LoanApplyInfo appInfo = lla.findByID(loanID);

		FormAssureDao fAssure = new FormAssureDao();
		double loanAmount = appInfo.getLoanAmount();
		Vector assVector = (Vector) fAssure.findByLoanID(loanID, 1,
				Constant.PageControl.CODE_ASCORDESC_DESC);

		// added by xiong fei 2010/05/18

		int iCount = assVector.size();
		AssureInfo assInfo = null;
		String asssCode = "";
		String asssName = "";
		long assTypeID = -1;
		String asssContacter = "";
		String asssPhone = "";
		double assAmount = 0;
		double assRate = 0;
		for (int i = 0; i < iCount; i++) {
			assInfo = (AssureInfo) assVector.get(i);
			asssCode = assInfo.getClientCode();
			asssName = assInfo.getClientName();
			assTypeID = assInfo.getAssureTypeID();
			asssContacter = assInfo.getClientContacter();
			asssPhone = assInfo.getClientPhone();
			assAmount = assInfo.getAmount();
			if ( loanAmount==0 )
  			{
  				assRate=0;
  			}
  			else
  			{
  				assRate=assAmount/loanAmount*100;
  			}	
			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, asssCode);
			PagerTools.returnCellList(cellList, asssName);
			PagerTools.returnCellList(cellList, LOANConstant.AssureType
					.getName(assTypeID));
			PagerTools.returnCellList(cellList, asssContacter);
			PagerTools.returnCellList(cellList, asssPhone);
			PagerTools.returnCellList(cellList, DataFormat.formatNumber(
					assAmount, 2));
			PagerTools.returnCellList(cellList, DataFormat.formatNumber(
					assRate, 6));
			// 存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// 返回数据
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

}
