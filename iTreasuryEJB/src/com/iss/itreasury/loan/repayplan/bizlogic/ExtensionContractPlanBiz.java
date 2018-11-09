package com.iss.itreasury.loan.repayplan.bizlogic;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.repayplan.dao.ExtensionContractPlanDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class ExtensionContractPlanBiz {

	/**
	 * 展期合同执行计划查看
	 * add by liaiyi 2013-02-25
	 */
  
	ExtensionContractPlanDao extensionContractPlanDao = new ExtensionContractPlanDao();
	public PagerInfo queryExtensionContractPlanInfo(long contractpayplanversionid) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = extensionContractPlanDao.queryExtensionContractPlanSQL(contractpayplanversionid);
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(ExtensionContractPlanBiz.class, "resultSetHandle");
			
			
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
		ContractOperation operation = new ContractOperation();
		ContractDao contractdao = new ContractDao();
		
		long ID = -1;
		long nContractID = -1;
		Timestamp dtPlanDate = null;
		Timestamp dtModifyDate = null;
		int nPayTypeID = -1;
		double amount = 0.00;
		double fExecuteInterestRate = 0.00;
		String sType = "";
		String lateRateString = "";
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				nContractID = rs.getLong("NCONTRACTID");
				dtPlanDate = rs.getTimestamp("DTPLANDATE");
				nPayTypeID = rs.getInt("NPAYTYPEID");
				amount = rs.getDouble("MAMOUNT");
				sType = rs.getString("STYPE");
				dtModifyDate = rs.getTimestamp("DTMODIFYDATE");
				if(contractdao.findByID(nContractID).getLoanTypeID() == LOANConstant.LoanType.RZZL){
					fExecuteInterestRate = contractdao.getLatelyRateForRZZLPlan(nContractID,dtPlanDate).getRate();
					lateRateString = DataFormat.formatRate(fExecuteInterestRate);
				}else{
					fExecuteInterestRate = contractdao.getLatelyRate(0,nContractID,dtPlanDate).getLateRate();
					lateRateString = contractdao.getLatelyRate(0,nContractID,dtPlanDate).getLateRateString();
				}
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatDate(dtPlanDate));
				PagerTools.returnCellList(cellList,LOANConstant.PlanType.getName(nPayTypeID));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(amount));
				PagerTools.returnCellList(cellList,sType);
				PagerTools.returnCellList(cellList,lateRateString+"%");
				PagerTools.returnCellList(cellList,DataFormat.getDateString(dtModifyDate));
				
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
}}

