package com.iss.itreasury.loan.contract.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.iss.itreasury.loan.contract.dao.ContractActivationDao;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class ContractActivationBiz {
	
	ContractActivationDao contractDao = new ContractActivationDao();
	
	public PagerInfo queryContractActivationInfo(long lType,long lCurrencyID,long lOfficeID,long lUserID,
			long lContractIDFrom,long lContractIDTo,long lClientID,double dAmountFrom,double dAmountTo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = contractDao.queryContractActivationInfo(lType,lCurrencyID,lOfficeID,lUserID,
					lContractIDFrom,lContractIDTo,lClientID,dAmountFrom,dAmountTo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setUsertext("总金额{MEXAMINEAMOUNT}");
			
			pagerInfo.setExtensionMothod(ContractActivationBiz.class, "getContractInfoResult");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList getContractInfoResult(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long nContractID = -1;
		String sContractCode = "";
		long loanTypeID = -1;
		String borrowClientName = "";
		String clientName = "";
		double loanAmount = 0.00;
		double interestRate = 0.00;
		String lateRateString = "";
		String rate = "";
		long intervalNum = -1;
		long statusID = -1;

		try {
			while(rs.next()){
				nContractID = rs.getLong("ContractID");
				sContractCode = rs.getString("ContractCode");
				loanTypeID = rs.getLong("nTypeID");
				borrowClientName = rs.getString("BorrowClientName");
				clientName = rs.getString("ConsignClientName");
				loanAmount = rs.getDouble("MEXAMINEAMOUNT");
				intervalNum = rs.getLong("RateName");
				statusID = rs.getLong("NSTATUSID");
				
				if(loanTypeID == LOANConstant.LoanType.TX || loanTypeID == LOANConstant.LoanType.ZTX){
					interestRate = rs.getDouble("mdiscountrate");
				}else if(loanTypeID == LOANConstant.LoanType.DB){
					interestRate = rs.getDouble("assurechargerate");
				}else{
					RateInfo rateInfo = new RateInfo();
					ContractDao dao = new ContractDao();
					rateInfo = dao.getLatelyRate(-1, nContractID, null);
					interestRate = rateInfo.getLateRate();
					lateRateString = rateInfo.getLateRateString();
				}
				
				if(loanTypeID == LOANConstant.LoanType.TX || loanTypeID == LOANConstant.LoanType.ZTX || loanTypeID == LOANConstant.LoanType.DB ){
					rate = DataFormat.formatRate(interestRate,6);
				}else{
					rate = lateRateString;
				}
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,nContractID);
				PagerTools.returnCellList(cellList,sContractCode);
				PagerTools.returnCellList(cellList,LOANConstant.LoanType.getName(loanTypeID));
				PagerTools.returnCellList(cellList,borrowClientName);
				PagerTools.returnCellList(cellList,clientName);
				PagerTools.returnCellList(cellList,"￥"+DataFormat.formatListAmount(loanAmount));
				PagerTools.returnCellList(cellList,rate+"%");
				PagerTools.returnCellList(cellList,intervalNum);
				PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(statusID));
				
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
