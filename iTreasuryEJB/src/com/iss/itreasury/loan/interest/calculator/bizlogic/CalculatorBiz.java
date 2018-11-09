package com.iss.itreasury.loan.interest.calculator.bizlogic;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import com.iss.itreasury.loan.interest.calculator.dao.*;
import com.iss.itreasury.loan.interest.calculator.dataentity.*;
import com.iss.itreasury.util.DataFormat;

public class CalculatorBiz {
	//有效
	private static final String  VALID = "yes";
	//无效
	private static final String  INVALID = "no";
	/**
	 * 计算合同contractNo1到contractNo2，从startDate到endDate这段时间的利息
	 * @param contractNo1
	 * @param contractNo2
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public HashMap calculate(String contractNo1, String contractNo2,Date startDate,Date endDate) {
		CalcutatorDAO dao = new CalcutatorDAO();
		HashMap ReturnMap = new HashMap();
		List result = new ArrayList();
		List messages = new ArrayList();		
		try {
			//得到放还款记录
			HashMap payment = dao.getPayment(contractNo1, contractNo2);
			//得到利率
			HashMap interest = dao.getInterest(contractNo1, contractNo2);
			HashMap cycReturn = null;	
			Iterator iter = payment.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
//				System.out.println("key=============================" + key + "\n");
				List lstPayment = (List)payment.get(key);
				List lstInterest = (List)interest.get(key);				
				cycReturn = calculateInterest(key.toString(),lstPayment, lstInterest, startDate, endDate);
				LoanInterestResultInfo info  = (LoanInterestResultInfo)cycReturn.get("info");
				String message = (String)cycReturn.get("message");
				if(info != null)
				{
					result.add(info);
//					System.out.print("info: \t key = " + info.getContractNo() + "\t startDate = " + info.getStartDate() + "\t endDate = " + info.getEndDate() + "\n");
				}
				if(!message.equals(""))
				{					
					messages.add(message);
//					System.out.println("\n massage  ========= "+ message );
				}
			}
			ReturnMap.put("list",result);
			ReturnMap.put("messages",messages);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ReturnMap;
	}
	
	/**
	 * 按放还款时间划分时间段
	 * @param contractNo
	 * @param lstPayment
	 * @param lstInterest
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	private HashMap calculateInterest(String contractNo, List lstPayment, List lstInterest,Date startDate, Date endDate) throws Exception {	
		HashMap cycReturn1 = new HashMap();		
		LoanInterestResultInfo info = null;
		HashMap cycReturn2 = null;
		double interest = 0;
		int interestFrom = 0;
		double totalPayment = 0;
		Date firstDate = null;
		Date lastDate = null;
		String message = "";
		String valid = VALID;
		LoanPaymentInfo info1 = null;
		LoanPaymentInfo info2 = null;
		if (lstPayment != null) {
			int i = 0;
			//得到计算利息开始时间之前的交易总额。
			for (; i < lstPayment.size(); i++)
			{
				info1 = (LoanPaymentInfo)lstPayment.get(i);	
				if(info1.getExecuteDate().after(startDate) )
				{
					break;
				}
				totalPayment += info1.getAmount();
			}
			//如果在计算利息开始时间之前，已有一笔或多笔交易
			if(i > 0)
			{
				//如果最后一笔放还款的总额为零且在计算利息开始时间之前，则不计算此合同利息				
				if((i == lstPayment.size()) && (totalPayment == 0 ))
				{																
					valid = INVALID;
				}
				//否则，返回到开始时间之前的最近一次交易。
				else
				{
					i--;
					info1 = (LoanPaymentInfo)lstPayment.get(i);	
					totalPayment -= info1.getAmount();
				}
				
			}	
			//如果在计算利息结束时间之前没有任何交易，则此合同不计算在内。
			else if(info1.getExecuteDate().after(endDate) )
			{
				valid = INVALID;
			}
			if(valid.equals(VALID))
			{
				for ( ; i < lstPayment.size(); i++) 
				{
					info1 = (LoanPaymentInfo)lstPayment.get(i);				
					Date dTemp1 = info1.getExecuteDate().before(startDate) ? startDate : info1.getExecuteDate();				
					Date dTemp2 = endDate;
					if(i<lstPayment.size()-1)
					{
						info2 = (LoanPaymentInfo)lstPayment.get(i+1);
						dTemp2 = info1.getExecuteDate().before(endDate) ? info2.getExecuteDate() : endDate;
					}					
					totalPayment += info1.getAmount();
					//如果最后一笔放还款的总额为零，则计算利息的结束时间为最后一笔放还款的执行时间。
					if((i == lstPayment.size()-1) && (totalPayment == 0 ))
					{						
						lastDate = info1.getExecuteDate();
						break;
					}
					cycReturn2 = calculateInterest(totalPayment, dTemp1, dTemp2, lstInterest, interestFrom);
					valid = (String)cycReturn2.get("valid");
					if(valid.equals(VALID))
					{
						interest += ((Double)cycReturn2.get("interest")).doubleValue();
						interestFrom = ((Integer)cycReturn2.get("interestFrom")).intValue();
						if(firstDate == null )
						{
							firstDate = (Date) cycReturn2.get("firstDate");
						}			
						lastDate = (Date) cycReturn2.get("lastDate");				
						message = message + (String)cycReturn2.get("message");
//						System.out.println("payment interest: "+ totalPayment +"\t"+dTemp1+"\t"+dTemp2+"\t"+interest+"\n");	
//						System.out.println("massage  ========= "+ message );
					}								
				}
				if(valid.equals(VALID))
				{
					if(!message.equals(""))
					{
						message = "合同 " + contractNo + " 在 " + message;
					}
					info = new LoanInterestResultInfo();
					info.setContractNo(contractNo);
					info.setStartDate(firstDate);
					info.setEndDate(lastDate);
					info.setInterest(interest);	
				}
				else
				{
					message = "合同 " +  contractNo + " 从 " + startDate.toString() + " 到 " + endDate.toString() + " 这段时间之内没有定义利率，无法计算。";
				}
			}
		}
		
		cycReturn1.put("info", info);	
		cycReturn1.put("message", message);
		return cycReturn1;
	}
	
	/**
	 * 按利率变化划分时间段
	 * @param amount
	 * @param startDate
	 * @param endDate
	 * @param lstInterest
	 * @param interestFrom
	 * @return
	 * @throws Exception
	 */
	private HashMap calculateInterest(double amount, Date startDate, Date endDate, List lstInterest, int interestFrom) throws Exception {
		HashMap cycReturn = new HashMap();
		double interest = 0;	
		String message = "";
		Date firstDate = null;
		Date lastDate = null;
		String valid = VALID;
		Date dTemp1 = startDate;
		Date dTemp2 = endDate;	
		LoanInterestInfo tempInfo1 = null;
		LoanInterestInfo tempInfo2 = null;
		if(lstInterest != null && lstInterest.size() > 0) 
		{
			int i = interestFrom;
			//得到第一个计算的利率，即startDate时间之前的第一个利率。
			if(interestFrom == 0)
			{				
				for( ; i < lstInterest.size(); i++)
				{
					tempInfo1 = (LoanInterestInfo)lstInterest.get(i);	
					if ( tempInfo1.getStartDate().after(startDate)) 
					{					
						break;
					}
				}
				interestFrom = i-1;
				//如果startDate之前没有定义任何利率，则从最开始定义的利率时间算起。并保存信息以便提示客户。
				if(interestFrom < 0)
				{
					if(tempInfo1.getStartDate().after(endDate))
					{
						//message = "在" + startDate.toString() + "到" + endDate.toString() + "之间没有定义利率，无法计算。";
						interestFrom = 0;
						valid = INVALID;
					}
					else
					{
						message = tempInfo1.getStartDate().toString()+" 之前没有定义利率，故利息从 " + tempInfo1.getStartDate().toString() + " 开始计算。";
//						System.out.print(message);
						interestFrom = 0;
						dTemp1 = tempInfo1.getStartDate();
					}
				}
			}
			//如果在startDate和endDate之间有利率变动，分时间段累计相加。
			if(valid.equals(VALID))
			{
				for(i = interestFrom; i < lstInterest.size(); i++) 
				{				
					tempInfo1 = (LoanInterestInfo)lstInterest.get(i);	
					if(i == interestFrom)
					{
						firstDate = dTemp1;
					}
					if(i < lstInterest.size() -1) 
					{
						tempInfo2 = (LoanInterestInfo)lstInterest.get(i+1);
						dTemp2 = tempInfo2.getStartDate().before(endDate) ? tempInfo2.getStartDate() : endDate;
					}
					else
					{
						dTemp2 = endDate;
					}
//					System.out.print("计算从 "+ dTemp1 + " 到 " + dTemp2);
					interest += calculateInterest(amount, tempInfo1.getRate(), getDays(dTemp1, dTemp2));
//					System.out.println("rate interest: "+amount+"\t"+dTemp1+"\t"+dTemp2+"\t"+interest);
					
					if(dTemp2.before(endDate))
					{
						dTemp1 = dTemp2;
					}
					else
					{											
						lastDate = dTemp2;
						interestFrom = i;						
						break;
					}
				}				
			}	
		}
		else
		{
			valid = INVALID;
		}
//		System.out.print("firstDate = " + firstDate + "\t lastDate = " + lastDate + "\n");
		cycReturn.put("valid",valid);		
		cycReturn.put("interest",new Double(interest));
		cycReturn.put("interestFrom",new Integer(interestFrom));
		cycReturn.put("firstDate",firstDate);
		cycReturn.put("lastDate",lastDate);
		cycReturn.put("message",message);
		return cycReturn;
	}
	
	/**
	 * 计算利息
	 * @param amount     贷款本金     
	 * @param rate       年利率
	 * @param days       贷款天数
	 * @return
	 */
	private double calculateInterest(double amount, double rate, int days) {
//		System.out.println(" :\t" + amount + "*" + rate + "/36000*" + days + " = " + amount*rate/36000*days);
		return amount*rate/36000*days;
	}
	
	/**
	 * 取得两个时间之间的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private int getDays(Date startDate, Date endDate) {
		Timestamp tsStart = Timestamp.valueOf(String.valueOf(startDate)+" 00:00:00");
		Timestamp tsEnd = Timestamp.valueOf(String.valueOf(endDate)+" 00:00:00");		
		return DataFormat.getIntervalDays(tsStart,tsEnd)-1;
	}
	
	/**
	 * 计算利息
	 * @param principalAmount    贷款本金
	 * @param rate               年利率
	 * @param term				 贷款期限（月）
	 * @return
	 */
	public double calculate(double principalAmount,double rate,int term) {
		return principalAmount*(rate*term/1200);
	}
	
//	public static void main(String[] args) {
//		CalculatorBiz biz = new CalculatorBiz();
//		biz.calculate("contractno1-1","contractno3-1",Date.valueOf("2006-11-21"),Date.valueOf("2006-11-23"));
//	}
}
