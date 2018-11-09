package com.iss.itreasury.loan.repayplan.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.clientmanage.client.bizlogic.ClientmanageBiz;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.repayplan.dao.ContractPlanDao;
import com.iss.itreasury.loan.repayplan.dataentity.PlanVersionInfo;
import com.iss.itreasury.loan.repayplan.dataentity.QueryContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dao.FlexiGridDao;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class ContractPlanBiz {

	ContractPlanDao contractPlanDao = new ContractPlanDao();
	

	public PagerInfo queryContractInfo(QueryContractInfo contractInfo) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = contractPlanDao.queryContractSQL(contractInfo);
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(ContractPlanBiz.class, "resultSetHandle");
			
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
		
		RateInfo ri = new RateInfo();
        ContractDao dao = new ContractDao();
        ContractInfo info = new ContractInfo();
       // QueryContractInfo queryContractInfo = new QueryContractInfo();
		
		long ID = -1;
		long StatusID = -1;//合同状态
		long IntervalNum = -1;//期限
		long LoanTypeID = -1;//贷款种类
		String ContractCode = "";//申请书编号
		String Name = "";//借款单位
		String lateRateString = "";
		double mLoanAmount = 0.00;//金额
		double InterestRate = 0.00;//执行利率
		double mdiscountrate  = 0.00;
		
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					ID = rs.getLong("ID");
					ContractCode = rs.getString("ContractCode");//合同编号
					LoanTypeID = rs.getLong("LoanTypeID");//贷款类型
					Name = rs.getString("Name");//贷款单位
					mLoanAmount = rs.getDouble("MLOANAMOUNT");//金额
					IntervalNum = rs.getLong("IntervalNum");//利率期限
					StatusID = rs.getLong("NSTATUSID");//合同状态
					
					lateRateString = dao.getLatelyRate(0, ID, null).getLateRateString();
					
					//存储列数据
					cellList = new ArrayList(); 
					
					PagerTools.returnCellList(cellList,ContractCode+","+ID+","+ContractCode); //合同编号 

					PagerTools.returnCellList(cellList,LOANConstant.LoanType.getName(LoanTypeID)); //贷款类型
					PagerTools.returnCellList(cellList,Name); //借款单位
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mLoanAmount)); //金额
					PagerTools.returnCellList(cellList,lateRateString+"%"); //执行利率
					PagerTools.returnCellList(cellList,IntervalNum); //利率期限
					PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(StatusID)); //合同状态
					
					
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

	
  public PagerInfo queryContractModifyInfo(long lContractID) throws Exception {
	 
			PagerInfo pagerInfo = null;
			String sql = null;
			Map<String, String> map = new HashMap<String, String>();
			try
			{
				pagerInfo = new PagerInfo();
				//得到查询SQL
				sql = contractPlanDao.queryContractModifySQL(lContractID);
				pagerInfo.setSqlString(sql);

				map.put("sql", sql);
				
				pagerInfo.setExtensionMothod(ContractPlanBiz.class, "resultSetHandle1",map);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}
		
		public ArrayList resultSetHandle1(ResultSet rs,Map<String, String> map) throws Exception{
			
			ArrayList resultList = new ArrayList(); //最终返回结果
			ArrayList cellList = null;//列
			ResultPagerRowInfo rowInfo = null;//行
			
			RateInfo ri = new RateInfo();
	        ContractDao dao = new ContractDao();
	        ContractInfo info = new ContractInfo();
	        PlanVersionInfo planVersionInfo = new PlanVersionInfo();
	        FlexiGridDao flexiGridDao = new FlexiGridDao();
			
	        String sStatusApply = "";
	        
			long ID = -1;
			long nContractID = -1;
			long nStatusID = -1;
			String nPlanVersion = "";
			Date dtInput = null;
			long sumCount = 0;
			long lCount = 0;
			boolean max = false;//最大的条数
			
			try {
				if(rs!=null)
				{   
					sumCount = flexiGridDao.getRowNum(map.get("sql").toString());
					
					while(rs.next())
					{
						lCount++;
			
						//获取数据
						ID = rs.getLong("ID");
						nContractID = rs.getLong("nContractID");
						nPlanVersion = rs.getString("nPlanVersion");
						dtInput = rs.getDate("dtInput");
						nStatusID = rs.getLong("nStatusID");
						
						 if (nStatusID == 1) 
						 {
							 sStatusApply = "已审批";
						  }
						 else if(nStatusID == 2)
						 {
						 	 sStatusApply = "审批中";
						 } 
						 else { 
							 sStatusApply = "已保存"; 
							 }
						//存储列数据
						cellList = new ArrayList(); 
						
						if(lCount == sumCount){
							max = true;
						}
						PagerTools.returnCellList(cellList,nPlanVersion+","+nContractID+","+nContractID+","+ID+","+nPlanVersion+","+max);
						PagerTools.returnCellList(cellList,dtInput);
						PagerTools.returnCellList(cellList,sStatusApply); 
						
						
						
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

  
  public PagerInfo queryContractModify(long contractPayPlanVersionID ,long lVersionID ,long lVersionID_r ) throws Exception
	// TODO Auto-generated method stub
	{
	  
		PagerInfo pagerInfo = null;
		String sql = null;
		
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			if(lVersionID >1){
				sql = contractPlanDao.queryContractModifySQL1(lVersionID);
			}
			else{
				if(contractPayPlanVersionID == Constant.YesOrNo.YES){
					sql = contractPlanDao.queryContractModifySQL1(lVersionID_r);
				}
			}
		
			pagerInfo.setSqlString(sql);

			pagerInfo.setUsertext("放款合计{MAMOUNT}[where npaytypeid = "+LOANConstant.PlanType.PAY+"];还款合计{MAMOUNT}[where npaytypeid = "+LOANConstant.PlanType.REPAY+"]");
			pagerInfo.setExtensionMothod(ContractPlanBiz.class, "handleMethod");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList handleMethod(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
        ContractDao contractdao = new ContractDao();
        
		long ID = -1;
		long lContractID = -1;
		long nPayTypeID = -1;//放款/还款
		String sType = null;//类型
		double fExecuteInterestRate = 0.00;
		String lateRateString = "";//执行利率
		double dAmount = 0.00;//金额
		Timestamp dtPlanDate = null;//原始计划日期
		Timestamp dtModifyDate = null;//修改日期
		
		try {
			if(rs!=null)
			{   
				while(rs.next())
				{
					//获取数据
					ID = rs.getLong("ID");
					lContractID = rs.getLong("ncontractid");
					dtPlanDate = rs.getTimestamp("dtPlanDate");
					nPayTypeID = rs.getLong("nPayTypeID");
					dAmount = rs.getDouble("MAMOUNT");
					//System.out.println(DataFormat.formatListAmount(dAmount));
					sType = rs.getString("STYPE");
					dtModifyDate = rs.getTimestamp("dtModifyDate");
					
					if(contractdao.findByID(lContractID).getLoanTypeID() == LOANConstant.LoanType.RZZL){
						fExecuteInterestRate = contractdao.getLatelyRateForRZZLPlan(lContractID, dtPlanDate).getRate();
						lateRateString = DataFormat.formatRate(fExecuteInterestRate);
					}else{
						fExecuteInterestRate = contractdao.getLatelyRate(0, lContractID,dtPlanDate).getLateRate();
						lateRateString = contractdao.getLatelyRate(0, lContractID, dtPlanDate).getLateRateString();
					}
					
					//存储列数据
					cellList = new ArrayList(); 
					
					
					PagerTools.returnCellList(cellList,ID);
					PagerTools.returnCellList(cellList,DataFormat.formatDate(dtPlanDate)+","+ID+","+DataFormat.formatDate(dtPlanDate)+","+dAmount); 
					PagerTools.returnCellList(cellList,LOANConstant.PlanType.getName(nPayTypeID)); 
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(dAmount)); 
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
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public PagerInfo queryContractPlan(long contractPayPlanVersionID ) throws Exception
	// TODO Auto-generated method stub
	{
	  
		PagerInfo pagerInfo = null;
		String sql = null;
		
		try
		{
			pagerInfo = new PagerInfo();
			sql = contractPlanDao.queryContractModifySQL1(contractPayPlanVersionID);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(ContractPlanBiz.class, "resultSetHandle2");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList resultSetHandle2(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
        ContractDao contractdao = new ContractDao();
        
		long ID = -1;
		long lContractID = -1;
		long nPayTypeID = -1;//放款/还款
		String sType = null;//类型
		double fExecuteInterestRate = 0.00;
		String lateRateString = "";//执行利率
		double dAmount = 0.00;//金额
		Timestamp dtPlanDate = null;//原始计划日期
		Timestamp dtModifyDate = null;//修改日期
		
		try {
			if(rs!=null)
			{   
				while(rs.next())
				{
					//获取数据
					ID = rs.getLong("ID");
					lContractID = rs.getLong("ncontractid");
					dtPlanDate = rs.getTimestamp("dtPlanDate");
					nPayTypeID = rs.getLong("nPayTypeID");
					dAmount = rs.getDouble("MAMOUNT");
					//System.out.println(DataFormat.formatListAmount(dAmount));
					sType = rs.getString("STYPE");
					dtModifyDate = rs.getTimestamp("dtModifyDate");
					
					if(contractdao.findByID(lContractID).getLoanTypeID() == LOANConstant.LoanType.RZZL){
						fExecuteInterestRate = contractdao.getLatelyRateForRZZLPlan(lContractID, dtPlanDate).getRate();
						lateRateString = DataFormat.formatRate(fExecuteInterestRate);
					}else{
						fExecuteInterestRate = contractdao.getLatelyRate(0, lContractID,dtPlanDate).getLateRate();
						lateRateString = contractdao.getLatelyRate(0, lContractID, dtPlanDate).getLateRateString();
					}
					
					//存储列数据
					cellList = new ArrayList(); 
					
					
					PagerTools.returnCellList(cellList,DataFormat.formatDate(dtPlanDate)); 
					PagerTools.returnCellList(cellList,LOANConstant.PlanType.getName(nPayTypeID)); 
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(dAmount)); 
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
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}

}
