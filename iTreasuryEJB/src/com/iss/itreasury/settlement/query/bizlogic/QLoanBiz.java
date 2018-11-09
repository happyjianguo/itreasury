package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.loan.loanpaynotice.bizlogic.LoanPayNotice;
import com.iss.itreasury.loan.loanpaynotice.bizlogic.LoanPayNoticeHome;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.Dao.QLoanDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QAccountBalance;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;


/**
 * 贷款通知书查询数据层
 * @author songwenxiao
 *
 */
public class QLoanBiz {
	
	QLoanDao dao = new QLoanDao();

	public PagerInfo queryCounterTrans( QueryAccountWhereInfo qawi,long lUnit,String CurrencySymbol) throws Exception{
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.getLoanNoticeBookSQL(qawi,lUnit);
			pagerInfo.setSqlString(sql);
			
			
			Map map =new HashedMap();
			map.put("currencysymbol", CurrencySymbol);
			
			pagerInfo.setUsertext("余额合计{Balance};利息合计{interest}");
			pagerInfo.setExtensionMothod(QLoanBiz.class, "resultSetHandle",map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
}
	
	public ArrayList resultSetHandle(ResultSet rs,Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		QueryAccountBalanceResultInfo obj = null;
		LoanPayNoticeInfo l_info=null;
		LoanPayNotice loanPayNotice=null;
		
		String currencysymbol=(String) map.get("currencysymbol");
		
		LoanPayNoticeHome loanPayNoticeHome;
		loanPayNoticeHome = (LoanPayNoticeHome)EJBObject.getEJBHome("LoanPayNoticeHome");
		loanPayNotice = loanPayNoticeHome.create();
		l_info = new LoanPayNoticeInfo();
		
		
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					obj=new QueryAccountBalanceResultInfo();
					obj.setClientName(rs.getString("ClientName"));
					obj.setIsToday(rs.getLong("IsToday"));
					obj.setAccountNo(rs.getString("AccountNo"));
					obj.setSubAccountID(rs.getLong("SubAccountID"));
					obj.setLoanTypeID(rs.getLong("LoanTypeID"));
					obj.setBalanceDate(rs.getTimestamp("BalanceDate"));
					obj.setContractCode(rs.getString("ContractCode"));
					obj.setContractStatusID(rs.getLong("ContractStatusID"));
					obj.setLoanPayCode(rs.getString("LoanPayCode"));
					obj.setLoanPayStartDate(rs.getTimestamp("LoanPayStartDate"));
					obj.setLoanpayEndDate(rs.getString("LoanpayEndDate"));
					obj.setSubAccountStatusID(rs.getLong("SubAccountStatusID"));
					obj.setLoanPayAmount(rs.getDouble("LoanPayAmount"));
					obj.setLoanPayID(rs.getLong("LoanPayID"));
					obj.setOpenAmount(rs.getDouble("OpenAmount"));
					obj.setLoanPreDrawInterest(rs.getDouble("LoanPreDrawInterest"));
					obj.setLoanPayAmount(rs.getDouble("LoanPayAmount"));
					obj.setInterest(rs.getDouble("Interest"));
					obj.setBalance(rs.getDouble("Balance"));
					
					
					double dAmount =obj.getLoanPayAmount();
					double dInterest = obj.getInterest();
					double dBanlance = obj.getBalance();
					if(obj.getLoanTypeID()==LOANConstant.LoanType.YT)
					{
						BankLoanQuery bankLoanQuery =new BankLoanQuery();
						//承贷比例
						double dRate = 0.0;
						dRate=bankLoanQuery.findRateByFormID(obj.getLoanPayID());					
						
						dBanlance = bankLoanQuery.findBalanceByFormID(obj.getLoanPayID());
								
					}
					if(obj.getLoanTypeID()==LOANConstant.LoanType.ZTX)
					{
						dAmount =obj.getOpenAmount();
					}
					
					String dAmountV =(dAmount>0.0?DataFormat.formatDisabledAmount(dAmount):"0.00");
					
					String dBanlanceV= dBanlance>0.0?DataFormat.formatDisabledAmount(dBanlance):"0.00";
					
					
					if(obj. getLoanTypeID()!=-1 && obj. getLoanTypeID()==LOANConstant.LoanType.TX)  
		             {
						String ContractInterestRate= (obj. getContractInterestRate()>0.0?DataFormat.formatRate(obj. getContractInterestRate()):"0.00") ;
		              }else{
		            	  
		            	String InterestRate=  (l_info.getInterestRate()>0.0?DataFormat.formatRate(l_info.getInterestRate()):"0.00");
		             
		              }    
					
					 l_info = loanPayNotice.findLoanPayNoticeByID(obj.getLoanPayID());
					  String strContractStatus=LOANConstant.ContractStatus.getName(obj.getContractStatusID());
				      if(strContractStatus == null )
				      {
				    	  strContractStatus="";	
				    	  
				      }
				      
				      
				      String strType = SETTConstant.SubAccountStatus.getName( obj.getSubAccountStatusID() );
					
					
					
					
					String strQueryDate = DataFormat.getDateString(obj.getBalanceDate());
					
					String dInterestV= (dInterest - obj.getLoanPreDrawInterest())>0.0?DataFormat.formatDisabledAmount((dInterest - obj.getLoanPreDrawInterest())):"0.00";
					
					String LoanPreDrawInterestV= obj.getLoanPreDrawInterest()>0.0?DataFormat.formatDisabledAmount(obj.getLoanPreDrawInterest()):"0.00" ;
					//处理数据
					
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,obj.getClientName()); 
					PagerTools.returnCellList(cellList,obj.getAccountNo()+","+obj.getSubAccountID()+","+obj.getLoanTypeID()+","+strQueryDate); 
					PagerTools.returnCellList(cellList,obj.getContractCode()); 
					PagerTools.returnCellList(cellList,strContractStatus);
					PagerTools.returnCellList(cellList,obj.getLoanPayCode());
					PagerTools.returnCellList(cellList,DataFormat.getDateString(obj.getLoanPayStartDate()) );
					PagerTools.returnCellList(cellList,obj.getLoanpayEndDate());
					PagerTools.returnCellList(cellList,strType);
					PagerTools.returnCellList(cellList,currencysymbol+(dAmount>0.0?DataFormat.formatDisabledAmount(dAmount):"0.00") );
					PagerTools.returnCellList(cellList,currencysymbol+(dBanlance>0.0?DataFormat.formatDisabledAmount(dBanlance):"0.00") );
					
					if(obj. getLoanTypeID()!=-1 && obj. getLoanTypeID()==LOANConstant.LoanType.TX)  
		             {
						PagerTools.returnCellList(cellList,(obj. getContractInterestRate()>0.0?DataFormat.formatRate(obj. getContractInterestRate()):"0.00")  );
		             }
					else
					{
		            	 PagerTools.returnCellList(cellList,(l_info.getInterestRate()>0.0?DataFormat.formatRate(l_info.getInterestRate()):"0.00"));
		           
		            }   
					PagerTools.returnCellList(cellList,currencysymbol+((dInterest - obj.getLoanPreDrawInterest())>0.0?DataFormat.formatDisabledAmount((dInterest - obj.getLoanPreDrawInterest())):"0.00" ));
					PagerTools.returnCellList(cellList,currencysymbol+(obj.getLoanPreDrawInterest()>0.0?DataFormat.formatDisabledAmount(obj.getLoanPreDrawInterest()):"0.00" ));
					
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
