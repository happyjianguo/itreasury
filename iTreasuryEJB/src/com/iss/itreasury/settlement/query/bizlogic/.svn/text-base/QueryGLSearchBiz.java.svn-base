package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.bizdelegation.GLQueryDelegation;
import com.iss.itreasury.settlement.query.Dao.QueryGLDao;
import com.iss.itreasury.settlement.query.paraminfo.AccountRecordConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.settlement.query.resultinfo.AccountRecordInfo;
import com.iss.itreasury.settlement.query.resultinfo.PrintGLInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QueryGLSearchBiz {
	QueryGLDao dao = new QueryGLDao();

	  public PagerInfo getDailyGL(AccountRecordConditionInfo conditionInfo) throws Exception
	  {
	    PagerInfo pagerInfo = null;
	    String sql = null;
	    try
	    {
	      pagerInfo = new PagerInfo();

	      sql = this.dao.findQueryGLSearchDailyRecord(conditionInfo);
	      pagerInfo.setSqlString(sql);
	      Map map = new HashedMap();
	      map.put("condition", conditionInfo);
	      
	      pagerInfo.setExtensionMothod(QueryGLSearchBiz.class, "resultSetHandle", map);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw new Exception("====>查询异常", e);
	    }
	    return pagerInfo;
	  }

		
	  public ArrayList resultSetHandle(ResultSet rs, Map map) throws Exception
	  {
		  	ArrayList resultList = new ArrayList(); //最终返回结果
			ArrayList cellList = null;//列
			ResultPagerRowInfo rowInfo = null;//行
			
			
			String stransno = "";
			String sbillno = "";
			String sbankchequeno ="";
			Timestamp dtexecute = null;
			String executeDay = "";
			String executeMonth = "";
			String executeYear = "";
			long nsubjecttype = -1;
			double mamount = 0.0;
			long transtypeID = -1;
			String sabstract = "";
			long ntransdirection = -1;
			AccountRecordConditionInfo conditionInfo = (AccountRecordConditionInfo)map.get("condition");
			QueryGLDao dao2 = new QueryGLDao();
			Timestamp tsTempDate=null;
//			取得期初余额
			double dBalance = 0.0;
			dBalance=dao2.getGLBalance(conditionInfo);
			tsTempDate = conditionInfo.getTsDateStart();
			
			double dDayPayBalance = 0.0; //每日付款合计
			double dDayReceiveBalance = 0.0; //每日收款合计
			double dDayPayBalanceMonthTotal = 0.0;
			double dDayReceiveBalanceMonthTotal =0.0;
			double dDayPayBalanceDayTotal = 0.0;
			double dDayReceiveBalanceDayTotal =0.0;
			double dDayPayBalanceYearTotal = 0.0;
			double dDayReceiveBalanceYearTotal =0.0;
			double dDayPayBalanceT = 0.0;
			double dDayReceiveBalanceT =0.0;
			int num = 0;
			int derection=-1;
			boolean mark=true;
			boolean flag=true;
			try{
				
				if(rs!=null)
				{
					while(rs.next())
					{
						num++;
						stransno = rs.getString("stransno");
						sbillno = rs.getString("SBILLNO");
						sbankchequeno = rs.getString("SBANKCHEQUENO");
						dtexecute = rs.getTimestamp("dtexecute1");
						executeDay = rs.getString("ExecuteDay");
						executeMonth = rs.getString("ExecuteMonth");
						executeYear = rs.getString("ExecuteYear");
						nsubjecttype = rs.getLong("nsubjecttype");
						mamount = rs.getDouble("mamount1");
						transtypeID = rs.getLong("TransTypeID");
						sabstract = rs.getString("SABSTRACT");
						ntransdirection = rs.getLong("NTRANSDIRECTION1");
						if(flag==true)
						{
							cellList = new ArrayList();
							
							PagerTools.returnCellList(cellList,DataFormat.formatDate(conditionInfo.getTsDateStart()));
							PagerTools.returnCellList(cellList,"");
							PagerTools.returnCellList(cellList,"<b>期初余额</b>");
							PagerTools.returnCellList(cellList,"&nbsp");
							PagerTools.returnCellList(cellList,"&nbsp");
							PagerTools.returnCellList(cellList,"&nbsp");
							PagerTools.returnCellList(cellList,"&nbsp");
							PagerTools.returnCellList(cellList,"<b>"+(dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance))+"</b>");
							flag = false;
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
							//返回数据
							resultList.add(rowInfo);
						}
						if(ntransdirection==SETTConstant.ControlDirection.DEBIT)
						{
							dDayPayBalanceT = mamount;
							dDayReceiveBalanceT=0.0;
						}
						if(ntransdirection==SETTConstant.ControlDirection.CREDIT)
						{
							dDayReceiveBalanceT = mamount;
							dDayPayBalanceT =0.0;
						}
						if(nsubjecttype!=SETTConstant.SubjectAttribute.ASSET )
						{
							dBalance = -dBalance ;
							mark=false;
						}
						//资产类
						if(nsubjecttype ==SETTConstant.SubjectAttribute.ASSET  || nsubjecttype ==SETTConstant.SubjectAttribute.PAYOUT)
						{
							dBalance = dBalance + dDayPayBalanceT - dDayReceiveBalanceT ;
						}
						//负债类
						if(nsubjecttype ==SETTConstant.SubjectAttribute.RIGHT ||nsubjecttype ==SETTConstant.SubjectAttribute.INCOME ||nsubjecttype ==SETTConstant.SubjectAttribute.DEBT)
						{
							dBalance = dBalance - dDayPayBalanceT + dDayReceiveBalanceT ;
							dBalance = -dBalance ;
						}
						dDayPayBalance = dDayPayBalance + dDayPayBalanceT;
						dDayReceiveBalance = dDayReceiveBalance + dDayReceiveBalanceT;
						
						
						if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue()+1 == Long.valueOf(executeMonth)&&
								Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() == Long.valueOf(executeYear)&&
								Long.valueOf(DataFormat.getDayString(tsTempDate)).longValue() == Long.valueOf(executeDay))
						{
							dDayPayBalanceDayTotal=dDayPayBalanceDayTotal+dDayPayBalanceT;
							dDayReceiveBalanceDayTotal = dDayReceiveBalanceDayTotal + dDayReceiveBalanceT;
						}
						if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue()+1 == Long.valueOf(executeMonth)&&
								Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() == Long.valueOf(executeYear))
						{//如果是本月的则显示在本月范围之内
							dDayPayBalanceMonthTotal=dDayPayBalanceMonthTotal+dDayPayBalanceT;
							dDayReceiveBalanceMonthTotal = dDayReceiveBalanceMonthTotal + dDayReceiveBalanceT;
						}
						if(Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() == Long.valueOf(executeYear))
						{//如果不是本年的则不显示在本年范围之内
							dDayPayBalanceYearTotal=dDayPayBalanceYearTotal+dDayPayBalanceT;
							dDayReceiveBalanceYearTotal = dDayReceiveBalanceYearTotal + dDayReceiveBalanceT;
						}
						
						
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,DataFormat.formatDate(dtexecute));
						PagerTools.returnCellList(cellList,stransno+","+stransno);
						PagerTools.returnCellList(cellList,sabstract);
						PagerTools.returnCellList(cellList,sbillno);
						PagerTools.returnCellList(cellList,sbankchequeno);
						PagerTools.returnCellList(cellList, dDayPayBalanceT != 0.0 ? DataFormat.formatDisabledAmount(dDayPayBalanceT) : "&nbsp;");
						PagerTools.returnCellList(cellList, dDayReceiveBalanceT != 0.0 ? DataFormat.formatDisabledAmount(dDayReceiveBalanceT) : "&nbsp;");
						PagerTools.returnCellList(cellList,dBalance>0?dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00":-dBalance != 0 ? DataFormat.formatDisabledAmount(-dBalance) : "0.00");
						
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
						//返回数据
						resultList.add(rowInfo);

						}
					
						//本日合计
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"<b>"+DataFormat.formatDate(tsTempDate)+"<b>");
						PagerTools.returnCellList(cellList,"");
						PagerTools.returnCellList(cellList,"<b>本日合计</b>");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList, "<b>"+(dDayPayBalanceDayTotal != 0.0 ? DataFormat.formatDisabledAmount(dDayPayBalanceDayTotal) : "&nbsp")+"</b>");
						PagerTools.returnCellList(cellList, "<b>"+(dDayReceiveBalanceDayTotal != 0.0 ? DataFormat.formatDisabledAmount(dDayReceiveBalanceDayTotal) : "&nbsp")+"</b>");
						PagerTools.returnCellList(cellList, "<b>"+(dBalance>0?dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00":-dBalance != 0 ? DataFormat.formatDisabledAmount(-dBalance) : "0.00")+"</b>");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(resultList.lastIndexOf(num++)));
						//返回数据
						resultList.add(rowInfo);
						
						//本月合计
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"<b>"+(DataFormat.getLastDateString(tsTempDate))+"<b>");
						PagerTools.returnCellList(cellList,"");
						PagerTools.returnCellList(cellList,"<b>本月合计</b>");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList, "<b>"+(dDayPayBalanceMonthTotal != 0.0 ? DataFormat.formatDisabledAmount(dDayPayBalanceMonthTotal) : "&nbsp")+"</b>");
						PagerTools.returnCellList(cellList, "<b>"+(dDayReceiveBalanceYearTotal != 0.0 ? DataFormat.formatDisabledAmount(dDayReceiveBalanceYearTotal) : "&nbsp")+"</b>");
						PagerTools.returnCellList(cellList, "<b>"+(dBalance>0?dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00":-dBalance != 0 ? DataFormat.formatDisabledAmount(-dBalance) : "0.00")+"</b>");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(resultList.lastIndexOf(num++)));
						//返回数据
						resultList.add(rowInfo);
						
						//本年合计
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"<b>"+Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue()+"<b>");
						PagerTools.returnCellList(cellList,"");
						PagerTools.returnCellList(cellList,"<b>本年合计</b>");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList, "<b>"+(dDayPayBalanceYearTotal != 0.0 ? DataFormat.formatDisabledAmount(dDayPayBalanceYearTotal) : "&nbsp")+"</b>");
						PagerTools.returnCellList(cellList, "<b>"+(dDayReceiveBalanceYearTotal != 0.0 ? DataFormat.formatDisabledAmount(dDayReceiveBalanceYearTotal) : "&nbsp")+"</b>");
						PagerTools.returnCellList(cellList, "<b>"+(dBalance>0?dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00":-dBalance != 0 ? DataFormat.formatDisabledAmount(-dBalance) : "0.00")+"</b>");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(resultList.lastIndexOf(num++)));
						//返回数据
						resultList.add(rowInfo);
						
						//余额
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList,"");
						PagerTools.returnCellList(cellList,"<b>余额</b>");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList,"&nbsp");
						PagerTools.returnCellList(cellList, "&nbsp");
						PagerTools.returnCellList(cellList, "&nbsp");
						PagerTools.returnCellList(cellList,"<b>"+(dBalance>0?dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00":-dBalance != 0 ? DataFormat.formatDisabledAmount(-dBalance) : "0.00")+"</b>");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(resultList.lastIndexOf(num++)));
						//返回数据
						resultList.add(rowInfo);
					}
					
					
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			
			return resultList;
	  }
}
