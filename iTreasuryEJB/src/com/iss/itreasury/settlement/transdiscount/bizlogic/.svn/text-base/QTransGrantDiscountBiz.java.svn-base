package com.iss.itreasury.settlement.transdiscount.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.bizlogic.QQueryAccountBiz;
import com.iss.itreasury.settlement.transdiscount.dao.QTransGrantDiscountDao;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * 账户查询业务层
 * @author xiang
 *
 */
public class QTransGrantDiscountBiz {
	
	QTransGrantDiscountDao dao = new QTransGrantDiscountDao();

	public PagerInfo queryTransGrantDiscount(QueryConditionInfo qInfo) throws Exception{
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.queryLoanSQL(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
		
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("STRANSNO");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"STRANSNO","ID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG,PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE+","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NTRANSACTIONTYPEID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDISCOUNTACCOUNTID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDISCOUNTCONTRACTID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getContractNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDISCOUNTNOTEID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			if(qInfo.isPayForm()){
				baseInfo.setExtensionMothod(NameRef.class, "getDiscountPayFormNoByID", new Class[]{long.class});
			}else
			baseInfo.setExtensionMothod(NameRef.class, "getDiscountCredenceNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDEPOSITACCOUNTID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("MDISCOUNTBILLAMOUNT");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("MINTEREST");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("MDISCOUNTAMOUNT");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SABSTRACT");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NSTATUSID");
			baseInfo.setDisplayType(PagerTypeConstant.LONG);
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
}
	
	public PagerInfo queryDiscountBill(Map<String,String> map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			pagerInfo = new PagerInfo();
			String sql = dao.getSQLByDiscountBillByContractIdAndCredenceId(map);
			pagerInfo.setSqlString(sql);
			System.out.println(map);
			pagerInfo.setExtensionMothod(QTransGrantDiscountBiz.class, "queryDiscountBillResult" , map);	
			pagerInfo.setTotalExtensionMothod(QTransGrantDiscountBiz.class, "queryDiscountBillSumAmount" , map);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList<ResultPagerRowInfo> queryDiscountBillResult(ResultSet rs , Map<String,String> map) throws Exception
	{
		System.out.println(map);
		ArrayList<ResultPagerRowInfo> resultList = new ArrayList<ResultPagerRowInfo>();
		ResultPagerRowInfo rowInfo = null;
		try
		{
			if(rs!=null)
			{
				while(rs.next())
				{
					ArrayList<String> list = new ArrayList<String>();
					PagerTools.returnCellList(list,DataFormat.formatInt(rs.getLong("nSerialNo"), 3, true));
					PagerTools.returnCellList(list,rs.getString("sCode"));
					PagerTools.returnCellList(list,DataFormat.formatDisabledAmount(rs.getDouble("mAmount")));
					PagerTools.returnCellList(list,DataFormat.getDateString(rs.getTimestamp("DTDISCOUNTDATE")));
					PagerTools.returnCellList(list,DataFormat.getDateString(rs.getTimestamp("dtEnd")));
					PagerTools.returnCellList(list,rs.getLong("nAddDays"));
					
					Timestamp tsEnd = rs.getTimestamp("dtEnd");
					Timestamp tsDiscountDate=rs.getTimestamp("DTDISCOUNTDATE");
					int nDays = 0;
					String strEnd = "";
					if (tsEnd != null && tsDiscountDate != null)
					{
						strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
								new Integer(strEnd.substring(0, 4)).intValue() - 1900,
								new Integer(strEnd.substring(5, 7)).intValue() - 1,
								new Integer(strEnd.substring(8, 10)).intValue(),
								0,
								0,
								0,
								0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
						{
							nDays = nDays + 3;
						}
					
					}	
					PagerTools.returnCellList(list,nDays);
					PagerTools.returnCellList(list,rs.getDouble("MRATE")+"%");
					PagerTools.returnCellList(list,DataFormat.formatDisabledAmount(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount")));
					PagerTools.returnCellList(list,DataFormat.formatDisabledAmount(rs.getDouble("mCheckAmount")));
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(list);
					//返回数据
					resultList.add(rowInfo);					
				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultList;
	}
	
	public ArrayList<String> queryDiscountBillSumAmount(ResultSet rs , Map<String,String> map) throws Exception
	{
		ArrayList<String> list = new ArrayList<String>();
		DiscountBillInfo info =dao.queryDiscountBillSumAmount(map);
        list.add("汇总贴现笔数{"+info.getCount()+"}");
        list.add("汇总贴现利息{"+DataFormat.formatDisabledAmount(info.getTotalAccrual()) +"}");
        list.add("汇总实付贴现金额{"+DataFormat.formatDisabledAmount(info.getTotalRealAmount())+"}");		
		return list;
	}

}
