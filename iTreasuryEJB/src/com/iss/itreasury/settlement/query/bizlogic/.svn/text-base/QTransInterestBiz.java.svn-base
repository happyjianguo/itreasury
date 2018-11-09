package com.iss.itreasury.settlement.query.bizlogic;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.query.Dao.QTransInterestDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransInterestConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransInterest;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransInterestSumInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * 账户查询业务层
 * 
 * @author xiang
 * 
 */
public class QTransInterestBiz {

	QTransInterestDao dao = new QTransInterestDao();

	public PagerInfo queryTransInterest(QueryTransInterestConditionInfo info,long unit)
			throws Exception {
		// TODO Auto-generated method stub
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try {
				pagerInfo = new PagerInfo();
				// 得到查询SQL
				sql = dao.getTransInterestSQL(info);
				pagerInfo.setSqlString(sql);

				Map params = new HashMap();
				params.put("param", info);
				params.put("unit", unit);
				pagerInfo.setExtensionMothod(QTransInterestBiz.class,
						"resultSetHandle", params);
				pagerInfo.setTotalExtensionMothod(QTransInterestBiz.class,
						"totalResultSetHandle", params);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}

	}
	public ArrayList totalResultSetHandle(ResultSet rs, Map params) throws Exception{
		long unit = Long.valueOf(params.get("unit")+"").longValue();
		QueryTransInterestConditionInfo info = (QueryTransInterestConditionInfo)params.get("param");
		QTransInterest qobj = new QTransInterest();
		double dAmountSum = qobj.getAmountSum(info);//结息金额合计
		double dNegotiateInterestSum1 = qobj.getNegotiateInterestSum(info,"1");
		double dNegotiateInterestSum = qobj.getNegotiateInterestSum(info);
		long lCount = qobj.getCount(info);
		QueryTransInterestSumInfo sumInfo = new QueryTransInterestSumInfo();
		//umInfo.setCreditSum(dCreditSum);
		//sumInfo.setDebitSum(dDebitSum);
		sumInfo.setAmountSum(dAmountSum-dNegotiateInterestSum1);
		sumInfo.setCount(lCount);
		sumInfo.setNegotiateInterestSum(dNegotiateInterestSum);
		ArrayList list = new ArrayList();
		list.add("利息{"+DataFormat.formatListAmount(sumInfo.getAmountSum()/unit)+"}");
		list.add("协定利息{"+DataFormat.formatListAmount(sumInfo.getNegotiateInterestSum()/unit)+"}");
		list.add("利息合计{"+DataFormat.formatListAmount(sumInfo.getAmountSum()/unit+sumInfo.getNegotiateInterestSum()/unit)+"}");
		list.add("记录合计{"+sumInfo.getCount()+"}");
		return list;
	}
	public ArrayList resultSetHandle(ResultSet rs, Map params)
			throws IException, RemoteException, SQLException {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		long unit = Long.valueOf(params.get("unit")+"").longValue();
		ResultPagerRowInfo rowInfo = null;// 行
		InterestOperation io = new InterestOperation();
			if (rs != null) {
				while (rs.next()) {

					double interestTax=0.0;
					interestTax = io.getInterestTaxByPlan(rs.getLong("AccountID"), rs.getLong("SubAccountID"),rs.getDouble("Interest")).getInterestTax();

					// 存储列数据
					
					long InterestTypeID = rs.getLong("InterestTypeID");
					double negotiateinterest = rs.getDouble("mnegotiateinterest");
					long negotiaterate = rs.getLong("mnegotiaterate");

					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("InterestSettlementDate")));
					PagerTools.returnCellList(cellList,rs.getString("AccountNo"));
					PagerTools.returnCellList(cellList,rs.getString("PayInterestAccountNo"));
					PagerTools.returnCellList(cellList,rs.getString("ReceiveInterestAccountNo"));
					PagerTools.returnCellList(cellList,rs.getString("ContractNo")==null?"":rs.getString("ContractNo"));
					PagerTools.returnCellList(cellList,rs.getString("PayFormNo")==null?"":rs.getString("PayFormNo"));
					PagerTools.returnCellList(cellList,rs.getString("DepositNo")==null?"":rs.getString("DepositNo"));
					PagerTools.returnCellList(cellList,rs.getString("ClientName")==null?"":rs.getString("ClientName"));
					PagerTools.returnCellList(cellList,DataFormat.formatAmountUseZero(rs.getDouble("InterestRate"), 6));
					PagerTools.returnCellList(cellList,InterestTypeID==8?"冲销计提":SETTConstant.InterestFeeType.getName(InterestTypeID));
				
					if(InterestTypeID==1){
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(rs.getDouble("Interest")/unit-negotiateinterest/unit));
					}else if(InterestTypeID==6){
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(rs.getDouble("Interest")/unit));
					}else{
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(rs.getDouble("Interest")/unit));
					}	
					PagerTools.returnCellList(cellList,negotiaterate<0?"":DataFormat.formatAmountUseZero(negotiaterate, 6)+ "%");
					PagerTools.returnCellList(cellList,negotiateinterest<0?"":DataFormat.formatListAmount(negotiateinterest/unit));

					if(InterestTypeID==1){
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(rs.getDouble("Interest")/unit));
					}else if(InterestTypeID==6){
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(rs.getDouble("Interest")/unit+negotiateinterest/unit));
					}else{
						PagerTools.returnCellList(cellList,DataFormat.formatListAmount(rs.getDouble("Interest")/unit+negotiateinterest/unit));
					}
					PagerTools.returnCellList(cellList,InterestTypeID!=SETTConstant.InterestFeeType.COMMISION?DataFormat.formatListAmount(interestTax/unit) : "");
					PagerTools.returnCellList(cellList,rs.getString("TransNo") == null ? "" : rs.getString("TransNo"));
					PagerTools.returnCellList(cellList,rs.getLong("CheckUserID") > 0 ? NameRef.getUserNameByID(rs.getLong("CheckUserID")) : "机制");

					// 存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

					// 返回数据
					resultList.add(rowInfo);

				}
			}
		return resultList;
	}
			

}
