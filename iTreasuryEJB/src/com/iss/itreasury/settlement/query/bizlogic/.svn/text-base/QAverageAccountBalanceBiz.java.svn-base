package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.settlement.query.Dao.QAverageAccountBalanceDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.QFixedDeposit;
import com.iss.itreasury.settlement.query.resultinfo.QueryFixedDepositSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QAverageAccountBalanceBiz {
	
	QAverageAccountBalanceDao qAverageAccountBalanceDao = new QAverageAccountBalanceDao();

	public PagerInfo queryAccount(QueryAccountWhereInfo qawi) throws Exception {
		// TODO Auto-generated method stub
		PagerInfo pagerInfo = null;
		String sql = null;
		Map<String,Long> map = new HashMap<String,Long>();
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = qAverageAccountBalanceDao.queryAccountSQL(qawi);
			pagerInfo.setSqlString(sql);
			
			map.put("lUnit", qawi.getLUnit());
			
			pagerInfo.setExtensionMothod(QAverageAccountBalanceBiz.class, "resultSetHandle",map);
			
			pagerInfo.setTotalExtensionMothod(QAverageAccountBalanceBiz.class, "resultSetHandle_total",map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;

	}
	
	public ArrayList resultSetHandle(ResultSet rs,Map map) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		String AccountNo = "";
		String AccountName = "";
		double _Balance = 0.00;
		double NegotiateBalance = 0.00;
		double Balance = 0.00;
		long lIntervalDays = 1;
		long nAccountTypeid = -1;
		
		long lUnit = (Long)map.get("lUnit");
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					AccountNo = rs.getString("AccountNo");
					AccountName = rs.getString("AccountName");
					Balance = rs.getDouble("Balance");
					NegotiateBalance = rs.getDouble("NegotiateBalance");
					lIntervalDays = rs.getLong("IntervalDays");
					nAccountTypeid = rs.getLong("nAccountTypeid");
					
					//处理数据
					_Balance = 0.00;
					if((Balance - NegotiateBalance)!=0){
						_Balance = (Balance - NegotiateBalance)/lIntervalDays/lUnit;
					}
					if(NegotiateBalance > 0){
						NegotiateBalance = NegotiateBalance/lIntervalDays/lUnit;
					}
					if(Balance > 0){
						Balance = Balance/lIntervalDays/lUnit;
					}
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,AccountNo); 
					PagerTools.returnCellList(cellList,AccountName); 
					PagerTools.returnCellList(cellList,SETTConstant.AccountType.getName(nAccountTypeid));
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(_Balance)); 
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(NegotiateBalance)); 
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(Balance)); 
					PagerTools.returnCellList(cellList,lIntervalDays);
					
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
	
	public ArrayList resultSetHandle_total(ResultSet rs,Map param) throws Exception {
		
		long lUnit = (Long)param.get("lUnit");
		
		double _BalanceSum = 0.0;
		double NegotiateBalanceSum = 0.0;
		double BalanceSum = 0.0;
		
		ArrayList<String> list = null;
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					BalanceSum = BalanceSum + rs.getDouble("Balance");
					NegotiateBalanceSum = NegotiateBalanceSum + rs.getDouble("NegotiateBalance");
				}
			}
			
			if(BalanceSum - NegotiateBalanceSum >0){
				_BalanceSum = (BalanceSum - NegotiateBalanceSum)/lUnit;
			}
			if(NegotiateBalanceSum >0){
				NegotiateBalanceSum = NegotiateBalanceSum/lUnit;
			}
			if(BalanceSum >0){
				BalanceSum = BalanceSum/lUnit;
			}
			
			list = new ArrayList<String>();
			list.add("平均余额合计{" + DataFormat.formatListAmount(_BalanceSum) + "}");
			list.add("平均协定余额合计{" + DataFormat.formatListAmount(NegotiateBalanceSum) + "}");
			list.add("合计{" + DataFormat.formatListAmount(BalanceSum) + "}");
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
}