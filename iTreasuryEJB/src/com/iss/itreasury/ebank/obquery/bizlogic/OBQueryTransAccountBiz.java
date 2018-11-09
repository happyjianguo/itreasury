package com.iss.itreasury.ebank.obquery.bizlogic;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao;
import com.iss.itreasury.ebank.obquery.dao.OBQueryTransAccountDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class OBQueryTransAccountBiz {
	
	OBQueryTransAccountDao oBQueryTransAccountDao = new OBQueryTransAccountDao();
	
	public PagerInfo queryTransAccountDetail(QueryTransAccountDetailWhereInfo qInfo,long lAccountGroupID) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			//sql = "select 1 from sys_user where 1=2";
			sql = oBQueryTransAccountDao.queryTransAccountDetailSQL(qInfo);
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			double dBalance = 0.0;
			QTransAccountDetail qobj = new QTransAccountDetail();
			OBAccountBalanceQueryDao bqDao = new OBAccountBalanceQueryDao();
			
			dBalance = qobj.queryTransAccountBalance(qInfo);//账户的期初余额
			if(qInfo.getContractID() > 0){
				dBalance = bqDao.getLoanHisBalanceByContractID(qInfo);//合同的期初余额
			}
			if(qInfo.getLoanNoteID() > 0)
			{
				dBalance = bqDao.getLoanHisBalanceByLoanNoteID(qInfo);//放款单的期初余额
			}
			paramMap.put("qInfo", qInfo);
			paramMap.put("dBalance",dBalance+"");
			paramMap.put("lAccountGroupID",lAccountGroupID+"");
			pagerInfo.setExtensionMothod(OBQueryTransAccountBiz.class, "transDetailResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList transDetailResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long isIntrDate = -1;
		Timestamp interestStartDate = null;
		Timestamp executeDate = null;
		String sAbstract = "";
		long transTypeId = -1;
		String transNo = "";
		long groupId = -1;
		String billNo = "";
		String oppAccountNo = "";
		String oppAccountName = "";
		double payAmount = 0.0;
		double receiveAmount = 0.0;
		double balance = 0.0;
		Timestamp tsStartDate = null;
		Timestamp tsEndDate = null;
		String executeMonth = "";
		String executeYear = "";
		double dDayPayBalance = 0.0;
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dDayReceiveBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		long lAccountGroupID = Long.valueOf(paramMap.get("lAccountGroupID").toString());
		
		QueryTransAccountDetailWhereInfo qInfo = (QueryTransAccountDetailWhereInfo)paramMap.get("qInfo");
		isIntrDate = qInfo.getIsIntrDate();
		QTransAccountDetail qobj = new QTransAccountDetail();
		Collection coll = qobj.queryTransAccountDetail(qInfo);
		Iterator it = null;
		QueryTransAccountDetailResultInfo qtri = new QueryTransAccountDetailResultInfo();
		
		try{
			
			tsStartDate = qInfo.getStartDate();
			tsEndDate = qInfo.getEndDate();
			
			double dBalance = Double.valueOf(paramMap.get("dBalance").toString()).doubleValue();
			//double dCanUseBalance = Double.valueOf(paramMap.get("dCanUseBalance").toString()).doubleValue();
			
			if (coll!=null && coll.size()<2)
			{
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatDate(tsStartDate));
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>期初余额</B>");
				if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
				{
					PagerTools.returnCellList(cellList,"&nbsp;");
				}
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>"+(dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance))+"</B>");
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//返回数据
				resultList.add(rowInfo);
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>本月合计</B>");
				PagerTools.returnCellList(cellList,"&nbsp;");
				if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
				{
					PagerTools.returnCellList(cellList,"&nbsp;");
				}
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"0.00");
				PagerTools.returnCellList(cellList,"0.00");
				PagerTools.returnCellList(cellList,"<B>"+(dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)) + "</B>");
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//返回数据
				resultList.add(rowInfo);
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>本年合计</B>");
				PagerTools.returnCellList(cellList,"&nbsp;");
				if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
				{
					PagerTools.returnCellList(cellList,"&nbsp;");
				}
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"0.00");
				PagerTools.returnCellList(cellList,"0.00");
				PagerTools.returnCellList(cellList,"<B>"+(dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)) + "</B>");
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//返回数据
				resultList.add(rowInfo);
				
			}else if(coll!=null && coll.size()>1)
			{
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatDate(tsStartDate));
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>期初余额</B>");
				if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
				{
					PagerTools.returnCellList(cellList,"&nbsp;");
				}
				if(SETTConstant.AccountGroupType.YT!=lAccountGroupID){
					PagerTools.returnCellList(cellList,"&nbsp;");
				}
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>"+(dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance))+"</B>");
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//返回数据
				resultList.add(rowInfo);
				
			
				Timestamp tsTempDate = null;
				Timestamp tsYearLoopDate = tsStartDate;
			
				for(tsYearLoopDate = tsStartDate ; (tsYearLoopDate.before(tsEndDate)||tsYearLoopDate.equals(tsEndDate)) ;  )
				{
				
					dYearPayBalance = 0.0;
					dYearReceiveBalance = 0.0;
					int nYears = Integer.valueOf(DataFormat.getYearString(tsYearLoopDate)).intValue();
					for(tsTempDate = tsYearLoopDate ; 
						Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() == nYears 
								&& (DataFormat.getMonthString(tsTempDate).equals(DataFormat.getMonthString(tsEndDate))
								||tsTempDate.before(tsEndDate)
								||DataFormat.formatDate(tsTempDate).equals(DataFormat.formatDate(tsEndDate))) ; 
						tsTempDate = DataFormat.getNextDate(DataFormat.getLastDateOfMonth(tsTempDate)) )
					{//将月份从开始日期开始循环 以取得没有交易的月的合计
						dMonthPayBalance = 0.0;
						dMonthReceiveBalance = 0.0;
						
						if(coll != null)
						{
							it = coll.iterator();
						}
						if(it != null && it.hasNext())
						{
							int i=0;
							while(it.hasNext())
							{
								i++;
								qtri = (QueryTransAccountDetailResultInfo)it.next();
								if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue() != Long.valueOf(DataFormat.getMonthString(qtri.getExecuteDate())).longValue())
								{//如果不是本月的则不显示在本月范围之内
									continue;
								}
								if(Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() != Long.valueOf(DataFormat.getYearString(qtri.getExecuteDate())).longValue())
								{//如果不是本年的则不显示在本年范围之内
									continue;
								}
								if(qtri.getTransTypeID() > -1000)//如果不是日合计的 金额
								{
									dMonthPayBalance = dMonthPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
									dMonthReceiveBalance = dMonthReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
									dYearPayBalance = dYearPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
									dYearReceiveBalance = dYearReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
								}
								
								//存储列数据
								cellList = new ArrayList();
								
								PagerTools.returnCellList(cellList,qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;");
								
								if(SETTConstant.TransactionType.isInterestTransaction(qtri.getTransTypeID()))
								{
									PagerTools.returnCellList(cellList,qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;");
								}else{
									PagerTools.returnCellList(cellList,qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;");
								}
									
								PagerTools.returnCellList(cellList,qtri.getAbstract() != null? (qtri.getAbstract().equals("<b>本日合计</b>")?"<b>本日合计</b>":qtri.getAbstract()): "&nbsp;");
								
								if( SETTConstant.AccountGroupType.CURRENT != lAccountGroupID && SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
								{
									PagerTools.returnCellList(cellList,SETTConstant.TransactionType.getName(qtri.getTransTypeID()));
									if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID
										||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID
										||SETTConstant.AccountGroupType.MARGIN == lAccountGroupID)
									{
										PagerTools.returnCellList(cellList,qtri.getDepositNo() != null ?qtri.getDepositNo() : "&nbsp;");
									}
									if(SETTConstant.AccountGroupType.TRUST == lAccountGroupID
										||SETTConstant.AccountGroupType.CONSIGN == lAccountGroupID
										||SETTConstant.AccountGroupType.DISCOUNT == lAccountGroupID
										||SETTConstant.AccountGroupType.OTHERLOAN == lAccountGroupID)
									{
										PagerTools.returnCellList(cellList,qtri.getContractCode() != null ? qtri.getContractCode() : "&nbsp;");
									}
								}
								
								PagerTools.returnCellList(cellList,qtri.getOppAccountID() > 0 ?  NameRef.getAccountNoByID(qtri.getOppAccountID()): (qtri.getOppAccountNo()!=null?qtri.getOppAccountNo():""));
								
								if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
								{
									PagerTools.returnCellList(cellList,qtri.getOppAccountID() > 0 ?  NameRef.getAccountNameByID(qtri.getOppAccountID()) : (qtri.getOppAccountName()!=null?qtri.getOppAccountName():""));
								}			
								if(qtri.getTransTypeID() == -1000)//日合计需要加粗显示
								{
									PagerTools.returnCellList(cellList,"<B>"+(qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00")+"</B>");
									PagerTools.returnCellList(cellList,"<B>"+(qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00")+"</B>");
									PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatDisabledAmount(qtri.getBalance()))+"</B>");
								}else{
									PagerTools.returnCellList(cellList,qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "&nbsp;");
									PagerTools.returnCellList(cellList,qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "&nbsp;");
									PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(qtri.getBalance()));
								}
								
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//返回数据
								resultList.add(rowInfo);
								
								dBalance = qtri.getBalance();
							}
						}
					
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,DataFormat.getLastDateString(tsTempDate));
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"<B>本月合计</B>");
						
						if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
						{
							PagerTools.returnCellList(cellList,"&nbsp;");
						}
						if(SETTConstant.AccountGroupType.YT!=lAccountGroupID){
							PagerTools.returnCellList(cellList,"&nbsp;");
						}
				
				
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"<B>"+(dMonthPayBalance != 0 ? DataFormat.formatDisabledAmount(dMonthPayBalance) : "0.00") + "</B>");
						PagerTools.returnCellList(cellList,"<B>"+(dMonthReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dMonthReceiveBalance) : "0.00") + "</B>");
						PagerTools.returnCellList(cellList,"<B>"+(dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00") + "</B>");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//返回数据
						resultList.add(rowInfo);
						
					}
					
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,DataFormat.getYearString(DataFormat.getPreviousMonth(tsTempDate,1)));
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"<B>本年合计</B>");
					PagerTools.returnCellList(cellList,"&nbsp;");
					if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
					{
						PagerTools.returnCellList(cellList,"&nbsp;");
					}
					if(SETTConstant.AccountGroupType.YT!=lAccountGroupID){
						PagerTools.returnCellList(cellList,"&nbsp;");
					}
					PagerTools.returnCellList(cellList,"<B>"+(dYearPayBalance != 0 ? DataFormat.formatDisabledAmount(dYearPayBalance) : "0.00") + "</B>");
					PagerTools.returnCellList(cellList,"<B>"+(dYearReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dYearReceiveBalance) : "0.00") + "</B>");
					PagerTools.returnCellList(cellList,"<B>"+(dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00") + "</B>");
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//返回数据
					resultList.add(rowInfo);
					
					tsYearLoopDate = DataFormat.getDateTime(nYears+1,1,1,0,0,0);
				}
			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public java.sql.Timestamp getNextMonth1(java.sql.Timestamp tsDate, int nMonth)
	{
		if (null == tsDate)
			return null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		java.sql.Timestamp returnTsDate = DataFormat.getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1 + nMonth, calendar.get(Calendar.DATE), 0, 0, 0);
		if( returnTsDate.getMonth()-tsDate.getMonth()>nMonth)
		{	while(returnTsDate.getMonth()-tsDate.getMonth()>nMonth)
				returnTsDate=DataFormat.getPreviousDate(returnTsDate);
		}	
		return returnTsDate;
	}
}
