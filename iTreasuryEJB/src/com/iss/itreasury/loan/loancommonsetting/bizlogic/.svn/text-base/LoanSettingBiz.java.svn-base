package com.iss.itreasury.loan.loancommonsetting.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.loancommonsetting.dao.LoanSettingDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.bizdelegation.AccountDailyTransGatherDelegation;
import com.iss.itreasury.settlement.query.bizlogic.QTransAccountBiz;
import com.iss.itreasury.settlement.query.bizlogic.QTransactionBiz;
import com.iss.itreasury.settlement.query.paraminfo.AccountDailyTransGatherConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class LoanSettingBiz {

	LoanSettingDao loanSettingDao = new LoanSettingDao();

	/**
	 * 申请书管理人权限转移biz层
	 * add by liaiyi 2012-12-17
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryAccount(ContractInfo ci) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		double mLoanAmount = 0.00;//金额
		
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = loanSettingDao.queryLoanDao(ci);
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(LoanSettingBiz.class, "resultSetHandle");
			
			pagerInfo.setUsertext("金额合计{LoanAmount}");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList resultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		PagerInfo pagerInfo = null;
		RateInfo ri = new RateInfo();
        ContractDao dao = new ContractDao();
        ContractInfo ci = null;
		
		long LoanID = -1;
		long LoanTypeID = -1;
		long StatusID = -1;//申请书状态
		long IntervalNum = -1;//期限
		String ApplyCode = "";//申请书编号
		String BorrowClientName = "";//借款单位
		String ClientName = "";//委托单位
		String InputUserName = "";//申请书管理人
		double mLoanAmount = 0.00;//金额
		double InterestRate = 0.00;//执行利率
		double mdiscountrate  = 0.00;
		
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					
					//获取数据
					LoanID = rs.getLong("LoanID");
					ApplyCode = rs.getString("ApplyCode");//申请书编号
					LoanTypeID = rs.getLong("LoanTypeID");//贷款类型
					BorrowClientName = rs.getString("BorrowClientName");//借款单位
					ClientName = rs.getString("ConsignClientName");//委托单位
					mLoanAmount = rs.getDouble("LoanAmount");//金额
					InterestRate =rs.getDouble("mdiscountrate");//执行利率
					IntervalNum = rs.getLong("RateName");//利率期限
					StatusID = rs.getLong("NSTATUSID");//申请书状态
					InputUserName = rs.getString("InputUserName");//申请书管理人
					
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,LoanID); 
					PagerTools.returnCellList(cellList,ApplyCode); //申请书编号 
					PagerTools.returnCellList(cellList,LOANConstant.LoanType.getName(LoanTypeID)); //贷款类型
					PagerTools.returnCellList(cellList,BorrowClientName); //借款单位
					PagerTools.returnCellList(cellList,ClientName); //委托单位
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mLoanAmount)); //金额
					PagerTools.returnCellList(cellList,InterestRate+"%"); //执行利率
					PagerTools.returnCellList(cellList,IntervalNum); //利率期限
					PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(StatusID)); //申请书状态
					PagerTools.returnCellList(cellList,InputUserName); //申请书管理人
					
					
					mdiscountrate  = 0.00;
					if(LoanTypeID==LOANConstant.LoanType.TX)
                    {
						mdiscountrate = rs.getDouble("mdiscountrate");
                    }
                    else
                    {
                        ri = new RateInfo();
                        ri=dao.getLatelyRate(LoanID,-1,null);
                        mdiscountrate = ri.getLateRate();
                    }
					PagerTools.returnCellList(cellList,mdiscountrate);
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}

}

