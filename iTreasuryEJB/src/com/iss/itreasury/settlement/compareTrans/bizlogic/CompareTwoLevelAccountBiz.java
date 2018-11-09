package com.iss.itreasury.settlement.compareTrans.bizlogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Stack;

import com.iss.itreasury.settlement.compareTrans.dao.CompareTwoLevelAccountDao;
import com.iss.itreasury.settlement.compareTrans.dataentity.CompareTwoLevelAccountCondtion;
import com.iss.itreasury.settlement.compareTrans.dataentity.CompareTwoLevelAccountDetailCondtion;
import com.iss.itreasury.settlement.compareTrans.dataentity.CompareTwoLevelAccountDetailResultInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class CompareTwoLevelAccountBiz {
	private HashMap settMap = null;
	private HashMap bankMap = null;
	
	public PageLoader compareTwoLevelAccountTrans(CompareTwoLevelAccountCondtion queryInfo)throws IException
	{
		try
		{
			CompareTwoLevelAccountDao dao = new CompareTwoLevelAccountDao();
		    PageLoader  pageloader = dao.compareTwoLevelAccountTrans(queryInfo);
		    return pageloader;
		}
		catch (Exception exp)
		{
			throw new IException("查询二级户对账列表错误",exp);
		}
	}
	public Collection compareTwoLevelAccountDetailTrans(CompareTwoLevelAccountDetailCondtion queryInfo)throws IException
	{
		Collection coll = null;
		try
		{
			settMap = findAllSettTwoLevelAccountTrans(queryInfo);
			bankMap = findAllBankTwoLevelAccountTrans(queryInfo);
			coll = new ArrayList();
			long intervalDays=intervalDays(DataFormat.getDate(queryInfo.getStartDate()),DataFormat.getDate(queryInfo.getEndDate()));
			Date tempDate=null;
			System.out.println("intervalDays="+intervalDays);
			for(int i=0;i<=intervalDays;i++)
			{
				//首先显示日期行,从最近一天开始往前推
				if(tempDate==null)
				{
					tempDate=DataFormat.getDate(queryInfo.getEndDate());
				}
				else
				{
				 	tempDate=DataFormat.getDate(DataFormat.formatDate(before(tempDate,1), DataFormat.FMT_DATE_YYYYMMDD));
				}
				CompareTwoLevelAccountDetailResultInfo dtDateInfo = new CompareTwoLevelAccountDetailResultInfo();
				dtDateInfo.setDtDate(tempDate);
				coll.add(dtDateInfo);
			Stack settStack=null;
			if(settMap!=null && settMap.get(DataFormat.formatDate(tempDate,DataFormat.FMT_DATE_SPECIAL))!=null)
			{
				settStack=(Stack)settMap.get(DataFormat.formatDate(tempDate,DataFormat.FMT_DATE_SPECIAL));
				System.out.println("settStack="+settStack.size());
			}
			//取出银行交易记录
			Stack bankStack=null;
			if(bankMap!=null && bankMap.get(DataFormat.formatDate(tempDate,DataFormat.FMT_DATE_SPECIAL))!=null)
			{
				bankStack=(Stack)bankMap.get(DataFormat.formatDate(tempDate,DataFormat.FMT_DATE_SPECIAL));
				System.out.println("bankStack="+bankStack.size());
			}
			
			if(settStack==null || settStack.empty())
			{
				if(bankStack!=null && !bankStack.empty())
				{
					CompareTwoLevelAccountDetailResultInfo info = null;
					while(!bankStack.empty())
					{
						info=(CompareTwoLevelAccountDetailResultInfo)bankStack.pop();
						coll.add(info);
					}
				}
			}
			else if(bankStack==null || bankStack.empty())
			{
				if(settStack!=null && !settStack.empty())
				{
					CompareTwoLevelAccountDetailResultInfo info = null;
					while(!settStack.empty())
					{
						info=(CompareTwoLevelAccountDetailResultInfo)settStack.pop();
						coll.add(info);
					}
				}
			}	
			else
			{
				int popFlag=-1;
				CompareTwoLevelAccountDetailResultInfo bankInfo=null;
				CompareTwoLevelAccountDetailResultInfo settInfo=null;
				CompareTwoLevelAccountDetailResultInfo bankInfo1 = null;
				CompareTwoLevelAccountDetailResultInfo settInfo1 = null;
				while(!settStack.empty() || !bankStack.empty())
				{
					System.out.println("settStack.size="+settStack.size()+",bankStack.size="+bankStack.size());
					if(settInfo==null)
					{
						if(!settStack.empty())
						{
							settInfo=(CompareTwoLevelAccountDetailResultInfo)settStack.pop();
							System.out.println(settInfo.getSettAmount());
						}
					}
					if(bankInfo==null)
					{
						if(!bankStack.empty())
						{
							bankInfo=(CompareTwoLevelAccountDetailResultInfo)bankStack.pop();
							System.out.println(bankInfo.getBankAmount());
						}
					}
					if(popFlag==1)
					{
						if(!settStack.empty())
						{
							settInfo=(CompareTwoLevelAccountDetailResultInfo)settStack.pop();
							System.out.println("1:"+settInfo.getSettAmount());
						}
						else if(settStack.empty())
						{						
							System.out.println("settStack.empty()");
							settInfo=new CompareTwoLevelAccountDetailResultInfo();
							settInfo.setSettAmount(Double.NaN);
						}
						if(!bankStack.empty())
						{
							bankInfo=(CompareTwoLevelAccountDetailResultInfo)bankStack.pop();
							System.out.println("1:"+bankInfo.getBankAmount());
						}
						else if(bankStack.empty())
						{						
							System.out.println("bankStack.empty()");
							bankInfo=new CompareTwoLevelAccountDetailResultInfo();
							bankInfo.setBankAmount(Double.NaN);
						}
					}
					else if(popFlag==2)
					{
						if(!settStack.empty())
						{
							settInfo=(CompareTwoLevelAccountDetailResultInfo)settStack.pop();
							System.out.println("2:"+settInfo.getSettAmount());
						}
						else if(settStack.empty())
						{						
							System.out.println("settStack.empty()");
							settInfo=new CompareTwoLevelAccountDetailResultInfo();
							settInfo.setSettAmount(Double.NaN);
						}
					}
					else if(popFlag==3)
					{						
						
						if(!bankStack.empty())
						{
							bankInfo=(CompareTwoLevelAccountDetailResultInfo)bankStack.pop();
							System.out.println("3:"+bankInfo.getBankAmount());
						}
						else if(bankStack.empty())
						{						
							System.out.println("bankStack.empty()");
							bankInfo=new CompareTwoLevelAccountDetailResultInfo();
							bankInfo.setBankAmount(Double.NaN);
						}
					}
					System.out.println("bankInfo amount is :  " + bankInfo.getBankAmount());
					System.out.println("settInfo amount is :  " + settInfo.getSettAmount());
					//比较金额的大小
					if(equal(settInfo.getSettAmount(),bankInfo.getBankAmount(),2))
					{
						//如果借贷相同,则去找下一条,如果下一条金额相同,则取下一条,将此记录再压入堆栈. 
						//  如果下一条金额不同,则将此记录再压入堆栈.
						if(settInfo.getSettDirectType() != bankInfo.getBankDirectType() || (bankInfo.getBankSabstract()!=null && settInfo.getSettSabstract()!=null && settInfo.getSettSabstract().indexOf(bankInfo.getBankSabstract())<0) || (bankInfo.getBankSabstract()!=null && settInfo.getSettSabstract()==null) || (settInfo.getSettOppAccountNO()!=null && !settInfo.getSettOppAccountNO().equals(bankInfo.getBankOppAccountNO())) || (settInfo.getSettOppAccountNO()==null && bankInfo.getBankOppAccountNO()!=null))
						{
							if(bankStack.empty())    //bankInfo 为最后一个值
							  {
								bankStack.push(bankInfo);    //由于借贷同,则将其重新放入栈内,只显示一方
							    System.out.println("sett == bank :  sett < bank");
							    coll.add(settInfo);
							    popFlag=1; 
							  }
							else
							 {
							       ArrayList list = new ArrayList();
	                               list.add(bankInfo);
	                               while(!bankStack.empty())
							       {
							           bankInfo1 = (CompareTwoLevelAccountDetailResultInfo)bankStack.pop();	 //取下一个,来判断是否符合
							           if(equal(settInfo.getSettAmount(),bankInfo1.getBankAmount(),2))   //判断金额相等
								         {
							        	   if(settInfo.getSettDirectType() == bankInfo1.getBankDirectType() && ((bankInfo1.getBankSabstract()!=null && settInfo.getSettSabstract()!=null && settInfo.getSettSabstract().indexOf(bankInfo1.getBankSabstract())>=0) || bankInfo1.getBankSabstract()==null) && (settInfo.getSettOppAccountNO()!=null &&settInfo.getSettOppAccountNO().equals(bankInfo1.getBankOppAccountNO())))
									          {
							        		   CompareTwoLevelAccountDetailResultInfo eqInfo = new CompareTwoLevelAccountDetailResultInfo();
							        		   eqInfo.setSettTransNo(settInfo.getSettTransNo());
							        		   eqInfo.setSettDirectType(settInfo.getSettDirectType());
							        		   eqInfo.setSettAmount(settInfo.getSettAmount());
							        		   eqInfo.setSettSabstract(settInfo.getSettSabstract());
							        		   eqInfo.setSettOppAccountNO(settInfo.getSettOppAccountNO());
							        		   eqInfo.setSettOppAccountNAME(settInfo.getSettOppAccountNAME());
							        		   eqInfo.setBankTransNo(bankInfo1.getBankTransNo());
							        		   eqInfo.setBankDirectType(bankInfo1.getBankDirectType());
							        		   eqInfo.setBankAmount(bankInfo1.getBankAmount());
							        		   eqInfo.setBankSabstract(bankInfo1.getBankSabstract());
							        		   eqInfo.setBankOppAccountNO(bankInfo1.getBankOppAccountNO());
							        		   eqInfo.setBankOppAccountNAME(bankInfo1.getBankOppAccountNAME());
							        		   coll.add(eqInfo);
							        		   popFlag=1; 
									            break;
									          }
							        	   else if(bankStack.isEmpty())
									       {
//							        		 金额不同
										        list.add(bankInfo1); 
										        coll.add(settInfo);
										        popFlag=1; 
										         break;
									       }
							        	   else
									         {
									           list.add(bankInfo1); 
									         }
							        	   
								         }
							           else
								       {
								        //金额不同
								        list.add(bankInfo1); 
								        coll.add(settInfo);
								        popFlag=1; 
								         break;
								       }
							       } 
	                               //将取出来的值再压回堆栈
	      						 if(list != null && list.size() > 0 )
	      						 {
	      						   for(int m =list.size()-1 ; m >=0; m--)
	      						   {
	      						     bankStack.push(list.get(m));
	      						   }
	      						 }
							 }
							
						}
						else
						 {
							//相等
							System.out.println("sett == bank");
							 CompareTwoLevelAccountDetailResultInfo eqInfo = new CompareTwoLevelAccountDetailResultInfo();
			        		   eqInfo.setSettTransNo(settInfo.getSettTransNo());
			        		   eqInfo.setSettDirectType(settInfo.getSettDirectType());
			        		   eqInfo.setSettAmount(settInfo.getSettAmount());
			        		   eqInfo.setSettSabstract(settInfo.getSettSabstract());
			        		   eqInfo.setSettOppAccountNO(settInfo.getSettOppAccountNO());
			        		   eqInfo.setSettOppAccountNAME(settInfo.getSettOppAccountNAME());
			        		   eqInfo.setBankTransNo(bankInfo.getBankTransNo());
			        		   eqInfo.setBankDirectType(bankInfo.getBankDirectType());
			        		   eqInfo.setBankAmount(bankInfo.getBankAmount());
			        		   eqInfo.setBankSabstract(bankInfo.getBankSabstract());
			        		   eqInfo.setBankOppAccountNO(bankInfo.getBankOppAccountNO());
			        		   eqInfo.setBankOppAccountNAME(bankInfo.getBankOppAccountNAME());
			        		   coll.add(eqInfo);
			        		   popFlag=1;
						 }
						
						
					}
					else if(getLessNum(settInfo.getSettAmount(),bankInfo.getBankAmount()))
					{
						System.out.println("sett<bank");
						coll.add(settInfo);
				        if(bankStack.empty())
				        {
				           bankStack.push(bankInfo);
				           popFlag=1;
				        }
				        else
				        {		
						 popFlag=2;
						}	
					}
					else if(getLessNum(bankInfo.getBankAmount(),settInfo.getSettAmount()))
					{						
						System.out.println("sett>bank");
						coll.add(bankInfo);
						 if(settStack.empty())
				        {
				           settStack.push(settInfo);
				           popFlag=1;
				        }
				        else
				        {		
						 popFlag=3;
						}
					}
				}
				
			}
		
		 } 
		}
		catch (Exception exp)
		{
			throw new IException("查询二级户对账列表错误",exp);
		}
		 return coll;
	}
	
	/**
	 * @param info
	 * @return
	 * @throws IException
	 */
	private HashMap findAllSettTwoLevelAccountTrans(CompareTwoLevelAccountDetailCondtion info) throws IException
	{

		HashMap transMap = null;
		try
		{
			ArrayList aList = null;
			CompareTwoLevelAccountDao dao = new CompareTwoLevelAccountDao();
			aList = dao.queryAllSettTwoLevelAccountTrans(info);
			if (aList != null && aList.size() > 0)
			{
				CompareTwoLevelAccountDetailResultInfo[] trans = null;
				trans = (CompareTwoLevelAccountDetailResultInfo[]) aList.toArray(new CompareTwoLevelAccountDetailResultInfo[0]);
				transMap = compareConvertInfo(trans);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询交易信息表出错.");
		}
		return transMap;
	}
	private HashMap findAllBankTwoLevelAccountTrans(CompareTwoLevelAccountDetailCondtion info) throws IException
	{

		HashMap transMap = null;
		try
		{
			ArrayList aList = null;
			CompareTwoLevelAccountDao dao = new CompareTwoLevelAccountDao();
			aList = dao.queryAllBankTwoLevelAccountTrans(info);
			if (aList != null && aList.size() > 0)
			{
				CompareTwoLevelAccountDetailResultInfo[] trans = null;
				trans = (CompareTwoLevelAccountDetailResultInfo[]) aList.toArray(new CompareTwoLevelAccountDetailResultInfo[0]);
				transMap = compareConvertInfo(trans);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询银行交易信息表出错.");
		}
		return transMap;
	}
	private HashMap compareConvertInfo(CompareTwoLevelAccountDetailResultInfo[] trans) throws Exception
	{

		HashMap transMap = null;
		if (trans != null && trans.length > 0)
		{
			transMap = new HashMap();
			Date tempDate = null;
			ArrayList transList = null;
			for (int i = 0; i < trans.length; i++)
			{
				CompareTwoLevelAccountDetailResultInfo info = trans[i];
				if (tempDate == null)
				{
					tempDate = info.getDtDate();
					transList = new ArrayList();
				}
				if (IDate.compareDate(tempDate, info.getDtDate()) == 0)
				{
					transList.add(info);
				}
				else
				{
					transMap.put(DataFormat.formatDate(tempDate, DataFormat.FMT_DATE_SPECIAL), convertStack(transList));
					transList = new ArrayList();
					tempDate = info.getDtDate();
					transList.add(info);
				}
			}
			if (trans != null && trans.length > 0)
			{
				transMap.put(DataFormat.formatDate(tempDate, DataFormat.FMT_DATE_SPECIAL), convertStack(transList));
			}
		}
		return transMap;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private Stack convertStack(ArrayList list) throws Exception
	{

		Stack tempStack = null;
		if (list != null && list.size() > 0)
		{
			tempStack = new Stack();
			CompareTwoLevelAccountDetailResultInfo[] tempTrans = (CompareTwoLevelAccountDetailResultInfo[]) list.toArray(new CompareTwoLevelAccountDetailResultInfo[0]);
			for (int i = tempTrans.length-1;i>=0; i--)
			{
				tempStack.push(tempTrans[i]);
			}
		}
		return tempStack;
	}
	
	public static boolean equal(double d1, double d2, int scale)
	{

		if (Double.isNaN(d1) || Double.isNaN(d2))
		{
			return false;
		}

		double difference = Math.abs(d1 - d2);
		double minNum = Math.pow(10, (-1) * scale);
		if (difference < minNum)
			return true;
		else
			return false;
	}

	// d1>d2 return true
	public static boolean getLessNum(double d1, double d2)
	{

		if (Double.isNaN(d1) && Double.isNaN(d2))
		{
			return false;
		}
		if (Double.isNaN(d1) && !Double.isNaN(d2))
		{
			return false;
		}
		if (!Double.isNaN(d1) && Double.isNaN(d2))
		{
			return true;
		}
		if (!Double.isNaN(d1) && !Double.isNaN(d2))
		{
			if (d1 < d2)
			{
				return true;
			}
		}
		return false;
	}

	public static long intervalDays(Date dtBeginDate, Date dtEndDate)
	{

		GregorianCalendar gc1, gc2;

		gc1 = new GregorianCalendar();
		gc1.setTime(dtBeginDate);
		gc2 = new GregorianCalendar();
		gc2.setTime(dtEndDate);

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc1.clear(Calendar.HOUR);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.HOUR);

		long lInterval = 0;
		lInterval = gc2.getTime().getTime() - gc1.getTime().getTime();
		lInterval = lInterval / (24 * 60 * 60 * 1000);
		return lInterval;
	}
	/**
	 * 获得该日期指定天数之前的日期
	 * 
	 * @param dtDate
	 * @param lDays
	 * @return 返回日期
	 */
	public static Date before(Date dtDate, long lDays)
	{

		long lCurrentDate = 0;
		lCurrentDate = dtDate.getTime() - lDays * 24 * 60 * 60 * 1000;
		Date dtBefor = new Date(lCurrentDate);
		return dtBefor;
	}
}
