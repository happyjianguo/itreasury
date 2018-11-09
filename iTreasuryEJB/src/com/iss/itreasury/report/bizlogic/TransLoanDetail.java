package com.iss.itreasury.report.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.report.dao.TransLoanDetailDAO;
import com.iss.itreasury.report.dateentity.TransLoanGatherInfo;

public class TransLoanDetail {

	public Collection getTransLoanList(TransLoanGatherInfo info)
	{
		TransLoanDetailDAO dao = new TransLoanDetailDAO();
		Collection collection = null;
		List list = null;
		
		try
		{
			collection = dao.getTransLoanList(info);
//			list = getResult(collection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return collection;
	}
	
	/**
	 * 期初余额
	 * @param info
	 * @return
	 */
	public Double getBegainMoney(TransLoanGatherInfo info)
	{
		TransLoanDetailDAO dao = new TransLoanDetailDAO();
		Double money = null;
		try
		{
			money = dao.getBegainMoney(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return money;
	}
	
	//废弃暂时不用
	private List getResult(Collection collection) throws Exception
	{
		Iterator it = null;
		List list = new ArrayList();
		List transList = new ArrayList();
		List sizeList = new ArrayList();
		TransLoanGatherInfo gather = null;
		//每月条数
		int size1 = 0;
		int size2 = 0;
		int size3 = 0;
		int size4 = 0;
		int size5 = 0;
		int size6 = 0;
		int size7 = 0;
		int size8 = 0;
		int size9 = 0;
		int size10 = 0;
		int size11 = 0;
		int size12 = 0;
		//一月份
		double grant_month1 = 0.00;
		double repay_month1 = 0.00;
		double amount_month1 = 0.00;
		double grant_year1 = 0.00;
		double repay_year1 = 0.00;
		double amount_year1 = 0.00;
		//二月份
		double grant_month2 = 0.00;
		double repay_month2 = 0.00;
		double amount_month2 = 0.00;
		double grant_year2 = 0.00;
		double repay_year2 = 0.00;
		double amount_year2 = 0.00;
		//三月份
		double grant_month3 = 0.00;
		double repay_month3 = 0.00;
		double amount_month3 = 0.00;
		double grant_year3 = 0.00;
		double repay_year3 = 0.00;
		double amount_year3 = 0.00;
		//四月份
		double grant_month4 = 0.00;
		double repay_month4 = 0.00;
		double amount_month4 = 0.00;
		double grant_year4 = 0.00;
		double repay_year4 = 0.00;
		double amount_year4 = 0.00;
		//五月份
		double grant_month5 = 0.00;
		double repay_month5 = 0.00;
		double amount_month5 = 0.00;
		double grant_year5 = 0.00;
		double repay_year5 = 0.00;
		double amount_year5 = 0.00;
		//六月份
		double grant_month6 = 0.00;
		double repay_month6 = 0.00;
		double amount_month6 = 0.00;
		double grant_year6 = 0.00;
		double repay_year6 = 0.00;
		double amount_year6 = 0.00;
		//七月份
		double grant_month7 = 0.00;
		double repay_month7 = 0.00;
		double amount_month7 = 0.00;
		double grant_year7 = 0.00;
		double repay_year7 = 0.00;
		double amount_year7 = 0.00;
		//八月份
		double grant_month8 = 0.00;
		double repay_month8 = 0.00;
		double amount_month8 = 0.00;
		double grant_year8 = 0.00;
		double repay_year8 = 0.00;
		double amount_year8 = 0.00;
		//九月份
		double grant_month9 = 0.00;
		double repay_month9 = 0.00;
		double amount_month9 = 0.00;
		double grant_year9 = 0.00;
		double repay_year9 = 0.00;
		double amount_year9 = 0.00;
		//十月份
		double grant_month10 = 0.00;
		double repay_month10 = 0.00;
		double amount_month10 = 0.00;
		double grant_year10 = 0.00;
		double repay_year10 = 0.00;
		double amount_year10 = 0.00;
		//十一月份
		double grant_month11 = 0.00;
		double repay_month11 = 0.00;
		double amount_month11 = 0.00;
		double grant_year11 = 0.00;
		double repay_year11 = 0.00;
		double amount_year11 = 0.00;
		//十二月份
		double grant_month12 = 0.00;
		double repay_month12 = 0.00;
		double amount_month12 = 0.00;
		double grant_year12 = 0.00;
		double repay_year12 = 0.00;
		double amount_year12 = 0.00;
		list.add(collection);//把查询结果放入list
		it = collection.iterator();
		while(it.hasNext())
		{
			gather = (TransLoanGatherInfo)it.next();
			int month = gather.getTsTransDate().getMonth();
			//一月份的月统计和年统计
			if(month == 0)
			{
				grant_month1 += gather.getDbGrantLoanAmount();
				repay_month1 += gather.getDbRepaymentAmount();
				amount_month1 += gather.getDbLoanAmount();
				size1++;
			}
			//二月份的月统计和年统计
			else if(month == 1)
			{
				grant_month2 += gather.getDbGrantLoanAmount();
				repay_month2 += gather.getDbRepaymentAmount();
				amount_month2 += gather.getDbLoanAmount();
				size2++;
			}
			//三月份的月统计和年统计
			else if(month == 2)
			{
				grant_month3 += gather.getDbGrantLoanAmount();
				repay_month3 += gather.getDbRepaymentAmount();
				amount_month3 += gather.getDbLoanAmount();
				size3++;
			}
			//四月份
			else if(month == 3)
			{
				grant_month4 += gather.getDbGrantLoanAmount();
				repay_month4 += gather.getDbRepaymentAmount();
				amount_month4 += gather.getDbLoanAmount();
				size4++;
			}
			//五月份
			else if(month == 4)
			{
				grant_month5 += gather.getDbGrantLoanAmount();
				repay_month5 += gather.getDbRepaymentAmount();
				amount_month5 += gather.getDbLoanAmount();
				size5++;
			}
			//六月份
			else if(month == 5)
			{
				grant_month6 += gather.getDbGrantLoanAmount();
				repay_month6 += gather.getDbRepaymentAmount();
				amount_month6 += gather.getDbLoanAmount();
				size6++;
			}
			//七月份
			else if(month == 6)
			{
				grant_month7 += gather.getDbGrantLoanAmount();
				repay_month7 += gather.getDbRepaymentAmount();
				amount_month7 += gather.getDbLoanAmount();
				size7++;
			}
			//八月份
			else if(month == 7)
			{
				grant_month8 += gather.getDbGrantLoanAmount();
				repay_month8 += gather.getDbRepaymentAmount();
				amount_month8 += gather.getDbLoanAmount();
				size8++;
			}
			//九月份
			else if(month == 8)
			{
				grant_month9 += gather.getDbGrantLoanAmount();
				repay_month9 += gather.getDbRepaymentAmount();
				amount_month9 += gather.getDbLoanAmount();
				size9++;
			}
			//十月份
			else if(month == 9)
			{
				grant_month10 += gather.getDbGrantLoanAmount();
				repay_month10 += gather.getDbRepaymentAmount();
				amount_month10 += gather.getDbLoanAmount();
				size10++;
			}
			//十一月份
			else if(month == 10)
			{
				grant_month11 += gather.getDbGrantLoanAmount();
				repay_month11 += gather.getDbRepaymentAmount();
				amount_month11 += gather.getDbLoanAmount();
				size11++;
			}
			//十二月份
			else if(month == 11)
			{
				grant_month12 += gather.getDbGrantLoanAmount();
				repay_month12 += gather.getDbRepaymentAmount();
				amount_month12 += gather.getDbLoanAmount();
				size12++;
			}
		}
		sizeList.add(new Integer(size1));
		if(size2 != 0)
		{
			sizeList.add(new Integer(size1 + size2));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size3 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size4 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size5 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size6 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5 + size6));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size7 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5 + size6 + size7));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size8 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5 + size6 + size7 + size8));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size9 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5 + size6 + size7 + size8 + size9));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size10 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5 + size6 + size7 + size8 + size9 + size10));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size11 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5 + size6 + size7 + size8 + size9 + size10 + size11));
		}else{
			sizeList.add(new Integer(0));
		}
		if(size12 != 0)
		{
			sizeList.add(new Integer(size1 + size2 + size3 + size4 + size5 + size6 + size7 + size8 + size9 + size10 + size11 + size12));
		}else{
			sizeList.add(new Integer(0));
		}
		
		//年合计
		//一月份
		grant_year1 = grant_month1;
		repay_year1 = repay_month1;
		amount_year1 = amount_month1;
		//二月份
		grant_year2 = grant_year1 + grant_month2;
		repay_year2 = repay_year1 + repay_month2;
		amount_year2 = amount_year1 + amount_month2;
		
		grant_year3 = grant_year2 + grant_month3;
		repay_year3 = repay_year2 + repay_month3;
		amount_year3 = amount_year2 + amount_month3;
		
		grant_year4 = grant_year3 + grant_month4;
		repay_year4 = repay_year3 + repay_month4;
		amount_year4 = amount_year3 + amount_month4;
		
		grant_year5 = grant_year4 + grant_month5;
		repay_year5 = repay_year4 + repay_month5;
		amount_year5 = amount_year4 + amount_month5;
		
		grant_year6 = grant_year5 + grant_month6;
		repay_year6 = repay_year5 + repay_month6;
		amount_year6 = amount_year5 + amount_month6;
		
		grant_year7 = grant_year6 + grant_month7;
		repay_year7 = repay_year6 + repay_month7;
		amount_year7 = amount_year6 + amount_month7;
		
		grant_year8 = grant_year7 + grant_month8;
		repay_year8 = repay_year7 + repay_month8;
		amount_year8 = amount_year7 + amount_month8;
		
		grant_year9 = grant_year8 + grant_month9;
		repay_year9 = repay_year8 + repay_month9;
		amount_year9 = amount_year8 + amount_month9;
		
		grant_year10 = grant_year9 + grant_month10;
		repay_year10 = repay_year9 + repay_month10;
		amount_year10 = amount_year9 + amount_month10;
		
		grant_year11 = grant_year10 + grant_month11;
		repay_year11 = repay_year10 + repay_month11;
		amount_year11 = amount_year10 + amount_month11;
		
		grant_year12 = grant_year11 + grant_month12;
		repay_year12 = repay_year11 + repay_month12;
		amount_year12 = amount_year11 + amount_month12;
		TransLoanGatherInfo trans_month1 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year1 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month2 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year2 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month3 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year3 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month4 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year4 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month5 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year5 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month6 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year6 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month7 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year7 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month8 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year8 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month9 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year9 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month10 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year10 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month11 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year11 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_month12 = new TransLoanGatherInfo();
		TransLoanGatherInfo trans_year12 = new TransLoanGatherInfo();
		//一月份
		trans_month1.setDbGrantLoanAmount(grant_month1);
		trans_month1.setDbRepaymentAmount(repay_month1);
		trans_month1.setDbLoanAmount(amount_month1);
		trans_year1.setDbGrantLoanAmount(grant_year1);
		trans_year1.setDbRepaymentAmount(repay_year1);
		trans_year1.setDbLoanAmount(amount_year1);
		//二月份
		trans_month2.setDbGrantLoanAmount(grant_month2);
		trans_month2.setDbRepaymentAmount(repay_month2);
		trans_month2.setDbLoanAmount(amount_month2);
		trans_year2.setDbGrantLoanAmount(grant_year2);
		trans_year2.setDbRepaymentAmount(repay_year2);
		trans_year2.setDbLoanAmount(amount_year2);
		//三月份
		trans_month3.setDbGrantLoanAmount(grant_month3);
		trans_month3.setDbRepaymentAmount(repay_month3);
		trans_month3.setDbLoanAmount(amount_month3);
		trans_year3.setDbGrantLoanAmount(grant_year3);
		trans_year3.setDbRepaymentAmount(repay_year3);
		trans_year3.setDbLoanAmount(amount_year3);
		//四月份
		trans_month4.setDbGrantLoanAmount(grant_month4);
		trans_month4.setDbRepaymentAmount(repay_month4);
		trans_month4.setDbLoanAmount(amount_month4);
		trans_year4.setDbGrantLoanAmount(grant_year4);
		trans_year4.setDbRepaymentAmount(repay_year4);
		trans_year4.setDbLoanAmount(amount_year4);
		//五月份
		trans_month5.setDbGrantLoanAmount(grant_month5);
		trans_month5.setDbRepaymentAmount(repay_month5);
		trans_month5.setDbLoanAmount(amount_month5);
		trans_year5.setDbGrantLoanAmount(grant_year5);
		trans_year5.setDbRepaymentAmount(repay_year5);
		trans_year5.setDbLoanAmount(amount_year5);
		//六月份
		trans_month6.setDbGrantLoanAmount(grant_month6);
		trans_month6.setDbRepaymentAmount(repay_month6);
		trans_month6.setDbLoanAmount(amount_month6);
		trans_year6.setDbGrantLoanAmount(grant_year6);
		trans_year6.setDbRepaymentAmount(repay_year6);
		trans_year6.setDbLoanAmount(amount_year6);
		//七月份
		trans_month7.setDbGrantLoanAmount(grant_month7);
		trans_month7.setDbRepaymentAmount(repay_month7);
		trans_month7.setDbLoanAmount(amount_month7);
		trans_year7.setDbGrantLoanAmount(grant_year7);
		trans_year7.setDbRepaymentAmount(repay_year7);
		trans_year7.setDbLoanAmount(amount_year7);
		//八月份
		trans_month8.setDbGrantLoanAmount(grant_month8);
		trans_month8.setDbRepaymentAmount(repay_month8);
		trans_month8.setDbLoanAmount(amount_month8);
		trans_year8.setDbGrantLoanAmount(grant_year8);
		trans_year8.setDbRepaymentAmount(repay_year8);
		trans_year8.setDbLoanAmount(amount_year8);
		//九月份
		trans_month9.setDbGrantLoanAmount(grant_month9);
		trans_month9.setDbRepaymentAmount(repay_month9);
		trans_month9.setDbLoanAmount(amount_month9);
		trans_year9.setDbGrantLoanAmount(grant_year9);
		trans_year9.setDbRepaymentAmount(repay_year9);
		trans_year9.setDbLoanAmount(amount_year9);
		//十月份
		trans_month10.setDbGrantLoanAmount(grant_month10);
		trans_month10.setDbRepaymentAmount(repay_month10);
		trans_month10.setDbLoanAmount(amount_month10);
		trans_year10.setDbGrantLoanAmount(grant_year10);
		trans_year10.setDbRepaymentAmount(repay_year10);
		trans_year10.setDbLoanAmount(amount_year10);
		//十一月份
		trans_month11.setDbGrantLoanAmount(grant_month11);
		trans_month11.setDbRepaymentAmount(repay_month11);
		trans_month11.setDbLoanAmount(amount_month11);
		trans_year11.setDbGrantLoanAmount(grant_year11);
		trans_year11.setDbRepaymentAmount(repay_year11);
		trans_year11.setDbLoanAmount(amount_year11);
		//十二月份
		trans_month12.setDbGrantLoanAmount(grant_month12);
		trans_month12.setDbRepaymentAmount(repay_month12);
		trans_month12.setDbLoanAmount(amount_month12);
		trans_year12.setDbGrantLoanAmount(grant_year12);
		trans_year12.setDbRepaymentAmount(repay_year12);
		trans_year12.setDbLoanAmount(amount_year12);
		
		//存入translist
		transList.add(trans_month1);
		transList.add(trans_year1);
		
		transList.add(trans_month2);
		transList.add(trans_year2);
		
		transList.add(trans_month3);
		transList.add(trans_year3);
		
		transList.add(trans_month4);
		transList.add(trans_year4);
		
		transList.add(trans_month5);
		transList.add(trans_year5);
		
		transList.add(trans_month6);
		transList.add(trans_year6);
		
		transList.add(trans_month7);
		transList.add(trans_year7);
		
		transList.add(trans_month8);
		transList.add(trans_year8);
		
		transList.add(trans_month9);
		transList.add(trans_year9);
		
		transList.add(trans_month10);
		transList.add(trans_year10);
		
		transList.add(trans_month11);
		transList.add(trans_year11);
		
		transList.add(trans_month12);
		transList.add(trans_year12);
		
		list.add(transList);
		
		list.add(sizeList);
		return list;
	}
}
