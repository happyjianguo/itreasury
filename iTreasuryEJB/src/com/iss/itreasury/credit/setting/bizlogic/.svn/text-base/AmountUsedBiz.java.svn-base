package com.iss.itreasury.credit.setting.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.credit.setting.dao.AmountUsedDao;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.ApplyUsedAmountInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class AmountUsedBiz {

	AmountUsedDao amountUsedDao= new AmountUsedDao();
	
	/**
	 * 贷款申请占用的额度biz层
	 * add by liaiyi 2013-01-09
	 */
	
	 public PagerInfo queryApplyUsedAmount(long ID) throws Exception{
		   
			PagerInfo pagerInfo = null;
			String sql = null;
			try
			{
				pagerInfo = new PagerInfo();
				sql = amountUsedDao.queryApplyUsedAmountSQL(ID);
				pagerInfo.setSqlString(sql);
				
				pagerInfo.setExtensionMothod(AmountUsedBiz.class, "resultSetHandle");
				
				pagerInfo.setUsertext("贷款申请占用的额度（除开自营贷款）总计{mApplyAmount}");
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
			AmountFormViewInfo formViewInfo = new AmountFormViewInfo();
			ApplyUsedAmountInfo applyUsedAmountInfo = new ApplyUsedAmountInfo();
			
			long nloantype = -1;//贷款类型
			long nintervalnum = -1;//期限
			long nstatusid = -1;//申请单状态
			String sName = null;//客户名称
			String sapplycode = null;//申请单编号
			double mApplyAmount = 0.00;//借款额度
			Timestamp dtstartdate = null;//开始日期
			Timestamp dtenddate = null;// 结束日期
			  
			
			try {
				if(rs!=null)
				{
				while(rs.next()){
					
					sName = rs.getString("sName");
					sapplycode = rs.getString("sapplycode");
					nloantype = rs.getLong("nloantype");
					mApplyAmount = rs.getDouble("mApplyAmount");
					dtstartdate = rs.getTimestamp("dtstartdate");
					dtenddate = rs.getTimestamp("dtenddate");
					nintervalnum = rs.getLong("nintervalnum");
					nstatusid = rs.getLong("nstatusid");
					
					//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,sName);
						PagerTools.returnCellList(cellList,sapplycode);
						PagerTools.returnCellList(cellList,LOANConstant.LoanType.getName(nloantype));
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mApplyAmount));
						PagerTools.returnCellList(cellList,DataFormat.formatDate(dtstartdate));
						PagerTools.returnCellList(cellList,DataFormat.formatDate(dtenddate));
						PagerTools.returnCellList(cellList,nintervalnum+"个月");
						PagerTools.returnCellList(cellList,LOANConstant.LoanStatus.getName(nstatusid));
						
						
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
						
						//返回数据
						resultList.add(rowInfo);
					}	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			
			return resultList;
		}
			
	
			/**
			 * 贷款合同占用的额度biz层
			 * add by liaiyi 2013-01-09
			 */
			
			 public PagerInfo queryContractUsedAmount(long ID) throws Exception{
				   
					PagerInfo pagerInfo = null;
					String sql = null;
					try
					{
						pagerInfo = new PagerInfo();
						sql = amountUsedDao.queryContractUsedAmount(ID);
						pagerInfo.setSqlString(sql);
						
						pagerInfo.setExtensionMothod(AmountUsedBiz.class, "resultSetHandle2");
						
						pagerInfo.setUsertext("贷款合同占用的额度（除开自营贷款）总计{mContractAmount}");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						throw new Exception("====>查询异常", e);
					}
					return pagerInfo;
				}
					public ArrayList resultSetHandle2(ResultSet rs) throws IException {
					
					ArrayList resultList = new ArrayList(); //最终返回结果
					ArrayList cellList = null;//列
					ResultPagerRowInfo rowInfo = null;//行
					AmountFormViewInfo formViewInfo = new AmountFormViewInfo();
					ApplyUsedAmountInfo applyUsedAmountInfo = new ApplyUsedAmountInfo();
					
					long nloantype = -1;//贷款类型
					long nintervalnum = -1;//期限
					long nstatusid = -1;//合同状态
					String sName = null;//客户名称
					String scontractcode = null;//合同编号
					double mContractAmount = 0.00;//贷款金额
					Timestamp dtstartdate = null;//合同开始日期
					Timestamp dtenddate = null;// 合同结束日期
					
					try {
						if(rs!=null)
						{
						while(rs.next()){
							
							sName = rs.getString("sName");
							scontractcode = rs.getString("scontractcode");
							nloantype = rs.getLong("loantype");
							mContractAmount = rs.getDouble("mContractAmount");
							dtstartdate = rs.getTimestamp("dtstartdate");
							dtenddate = rs.getTimestamp("dtenddate");
							nintervalnum = rs.getLong("nintervalnum");
							nstatusid = rs.getLong("nstatusid");
							
							//存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,sName);
								PagerTools.returnCellList(cellList,scontractcode);
								PagerTools.returnCellList(cellList,LOANConstant.LoanType.getName(nloantype));
								PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mContractAmount));
								PagerTools.returnCellList(cellList,DataFormat.formatDate(dtstartdate));
								PagerTools.returnCellList(cellList,DataFormat.formatDate(dtenddate));
								PagerTools.returnCellList(cellList,nintervalnum+"个月");
								PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(nstatusid));
								
								
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
								
								//返回数据
								resultList.add(rowInfo);
							}	
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
						throw new IException(e.getMessage());
					}
					
					return resultList;
				}
	/**
	 * 自营贷款放款单占用的额度 biz层
	 * add by liaiyi 2013-01-09
	 */
	
	 public PagerInfo queryLoanUsedAmount(long ID) throws Exception{
		   
			PagerInfo pagerInfo = null;
			String sql = null;
			try
			{
				pagerInfo = new PagerInfo();
				sql = amountUsedDao.queryLoanUsedAmountSQL(ID);
				pagerInfo.setSqlString(sql);
				
				pagerInfo.setExtensionMothod(AmountUsedBiz.class, "resultSetHandle1");
				
				pagerInfo.setUsertext("自营贷款放款单占用的额度总计{mamount}");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}
			public ArrayList resultSetHandle1(ResultSet rs) throws IException {
			
			ArrayList resultList = new ArrayList(); //最终返回结果
			ArrayList cellList = null;//列
			ResultPagerRowInfo rowInfo = null;//行
			AmountFormViewInfo formViewInfo = new AmountFormViewInfo();
			ApplyUsedAmountInfo applyUsedAmountInfo = new ApplyUsedAmountInfo();
			
			long nstatusid = -1;//放款单状态 	  
			String sName = null;//客户名称
			String sCode = null;//客户编号
			String scontractcode = null;//合同编号
			String loanpfcode = null;//放款单编号
			double mamount = 0.00;//放款单金额（）
			Timestamp dtstart = null;//放款单开始日期
			Timestamp dtend = null;//放款单结束日期
			      
			
			
			try {
				if(rs!=null)
				{
				while(rs.next()){
					
					sCode = rs.getString("sCode");
					sName = rs.getString("sName");
					scontractcode = rs.getString("scontractcode");
					loanpfcode = rs.getString("loanpfcode");
					mamount = rs.getDouble("mamount");
					dtstart = rs.getTimestamp("dtstart");
					dtend = rs.getTimestamp("dtend");
					nstatusid = rs.getLong("nstatusid");
					
					//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,sCode);
						PagerTools.returnCellList(cellList,sName);
						PagerTools.returnCellList(cellList,scontractcode);
						PagerTools.returnCellList(cellList,loanpfcode);
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mamount));
						PagerTools.returnCellList(cellList,DataFormat.formatDate(dtstart));
						PagerTools.returnCellList(cellList,DataFormat.formatDate(dtend));
						PagerTools.returnCellList(cellList,LOANConstant.LoanPayNoticeStatus.getName(nstatusid));
						
						
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
						
						//返回数据
						resultList.add(rowInfo);
					}	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			
			return resultList;
		}
		
			/**
			 * 还款单释放的额度  biz层
			 * add by liaiyi 2013-01-09
			 */
			
			 public PagerInfo queryRepayUsedAmount(long ID) throws Exception{
				   
					PagerInfo pagerInfo = null;
					String sql = null;
					try
					{
						pagerInfo = new PagerInfo();
						sql = amountUsedDao.queryRepayUsedAmountSQL(ID);
						pagerInfo.setSqlString(sql);
						
						pagerInfo.setExtensionMothod(AmountUsedBiz.class, "resultSetHandle3");
						
						pagerInfo.setUsertext("还款单释放的额度总计{mCircleAmount}");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						throw new Exception("====>查询异常", e);
					}
					return pagerInfo;
				}
					public ArrayList resultSetHandle3(ResultSet rs) throws IException {
					
					ArrayList resultList = new ArrayList(); //最终返回结果
					ArrayList cellList = null;//列
					ResultPagerRowInfo rowInfo = null;//行
					AmountFormViewInfo formViewInfo = new AmountFormViewInfo();
					ApplyUsedAmountInfo applyUsedAmountInfo = new ApplyUsedAmountInfo();
					
					long nstatusid = -1;//合同状态 	  
					long nintervalnum = -1;//期限
					String sName = null;//客户名称
					String scontractcode = null;//合同编号
					double mCircleAmount = 0.00;//还款金额（）
					Timestamp dtstartdate = null;//合同开始日期
					Timestamp dtenddate = null;//合同结束日期
					      
					
					try {
						if(rs!=null)
						{
						while(rs.next()){
							
							sName = rs.getString("sName");
							scontractcode = rs.getString("scontractcode");
							mCircleAmount = rs.getDouble("mCircleAmount");
							dtstartdate = rs.getTimestamp("dtstartdate");
							dtenddate = rs.getTimestamp("dtenddate");
							nintervalnum = rs.getLong("nintervalnum");
							nstatusid = rs.getLong("nstatusid");
							
							//存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,sName);
								PagerTools.returnCellList(cellList,scontractcode);
								PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mCircleAmount));
								PagerTools.returnCellList(cellList,DataFormat.formatDate(dtstartdate));
								PagerTools.returnCellList(cellList,DataFormat.formatDate(dtenddate));
								PagerTools.returnCellList(cellList,nintervalnum+"个月");
								PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(nstatusid));
								
								
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
								
								//返回数据
								resultList.add(rowInfo);
							}	
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
						throw new IException(e.getMessage());
					}
					
					return resultList;
				}
}
