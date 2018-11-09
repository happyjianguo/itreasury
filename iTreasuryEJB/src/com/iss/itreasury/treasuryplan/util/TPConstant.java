/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-6
 */
package com.iss.itreasury.treasuryplan.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Properties;

import com.iss.itreasury.treasuryplan.util.dataentity.*;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;

import javax.servlet.jsp.JspWriter;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * @author yehuang
 *
 * 定义资金计划的常量
 * 
 */
public class TPConstant extends Constant
{
	/**
	 * 资金计划编制汇总方式
	 * @author yuanxue
	 *
	 */
	public static class GatherType
	{
		public static final int DAYSUM = 1; //按日 一般适合周计划
		public static final int WEEKSUM = 2; //按周 一般适合月计划
		public static final int MONTHSUM = 3; //按月 一般适合年计划
	
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DAYSUM :
					strReturn = "按日汇总";
					break;
				case (int) WEEKSUM :
					strReturn = "按周汇总";
					break;
				case (int) MONTHSUM :
					strReturn = "按月汇总";
					break;
	
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[3];
			lTemp[0] = DAYSUM;
			lTemp[1] = WEEKSUM;
			lTemp[2] = MONTHSUM;

			return lTemp;
		}
		public static final void showGatherTypeList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				ex.printStackTrace() ;
			}
		}
	}
	/**
	 * 资金计划状态
	 * @author gdzhao
	 *
	 */
	public static class PlanVersionStatus
	{
		public static final long SAVE = 1; //撰写
		public static final long SUBMIT = 2; //已提交
		public static final long CHECK = 3; //已审核
		public static final long NOTACTIVE = 4; //未执行
		public static final long ACTIVE = 5; //执行中
		public static final long FINISH = 10; //已结束
		public static final long CANCEL = 11; //已取消
		public static final long REFUSE = 12; //已拒绝
		public static final long DELETE = 0; //删除

		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "撰写";
					break;
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) CHECK :
					strReturn = "已审核";
					break;
				case (int) NOTACTIVE :
					strReturn = "未执行";
					break;
				case (int) ACTIVE :
					strReturn = "执行中";
					break;
				case (int) FINISH :
					strReturn = "已结束";
					break;
				case (int) CANCEL :
					strReturn = "已取消";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) DELETE :
					strReturn = "已删除";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[9];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = NOTACTIVE;
			lTemp[4] = ACTIVE;
			lTemp[5] = FINISH;
			lTemp[6] = CANCEL;
			lTemp[7] = REFUSE;
			lTemp[8] = DELETE;

			return lTemp;
		}
		public static final long[] getQueryCode()
		{
			long[] lTemp = null;
			lTemp = new long[5];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = CANCEL;
			lTemp[4] = REFUSE;

			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = getQueryCode();
						break;	
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				ex.printStackTrace() ;
			}
		}
	}	
	/**
	 * 资金计划查询统计方式
	 * @author xrli
	 *
	 */
	public static class QueryStatType
	{
		public static final int DAY = 1; //按日
		public static final int WEEK = 2; //按周
		public static final int MONTH = 3; //按月
		public static final int YEAR = 4; //按年
		public static final int MONTHSUM = 5; //按月汇总
		public static final int YEARSUM = 6; //按年汇总
		

		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DAY :
					strReturn = "按日";
					break;
				case (int) WEEK :
					strReturn = "按周";
					break;
				case (int) MONTH :
					strReturn = "按月";
					break;
				/*case (int) YEAR :
					strReturn = "按年";
					break;*/
				case (int) MONTHSUM :
					strReturn = "按月汇总";
					break;
				case (int) YEARSUM :
					strReturn = "按年汇总";
					break;				
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[5];
			lTemp[0] = DAY;
			lTemp[1] = WEEK;
			lTemp[2] = MONTH;
			//lTemp[3] = YEAR;
			lTemp[3] = MONTHSUM;
			lTemp[4] = YEARSUM;			

			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				ex.printStackTrace() ;
			}
		}
	}
	/**
	 * 资金计划交易名称
	 * */
	public static final class TPTransaction
    {

		
		public static final class TransactionName{
			public static final long FixedMaturity = 1;		   //定期到期支取
			public static final long  FixedMaturityToCurrent = 2;//定期到期本金转活期
			public static final long  FixedMaturityInterest = 3;//定期到期利息
			
			public static final long CurrentInterestSettlement = 4; //活期结息
			public static String getNameByID(long id){
				String name = "";
				switch((int)id){
					case(int)FixedMaturity:{
						name = "定期到期支取";
					}
					break;
					case (int)FixedMaturityToCurrent:{
						name = "定期到期本金转活期";
					}
					break;
					case (int)FixedMaturityInterest:{
						name = "定期到期利息";
					} 		
					break;

					case (int)CurrentInterestSettlement:{
						name = "活期结息";
					} 		
					break;					
				}
				return name;
			}
		}
		
		
		public static final class Interest_Transaction
        {
			public static final long  TrustLoanSettlement = 1;	 //自营贷款结息
			public static final long  ConsignLoanSettlement = 2;	 //委托贷款结息
			public static final long  ConsignLoanSettlementCommission = 3;	 //委托贷款结手续费
			
			public static String getNameByID(long id)
            {
				String name = "";
				switch((int)id){
					case(int)TrustLoanSettlement:
                    {
						name = "自营贷款结息";
					}
					break;
					case(int)ConsignLoanSettlement:
                    {
						name = "委托贷款结息";
					}
					break;
					case(int)ConsignLoanSettlementCommission:
                    {
						name = "委托贷款结手续费";
					}
					break;					
				}
				return name;
			}			
		}
		
		public static final class Loan_Transaction
        {
			public static final long  ZYGrant = 1;	 //自营贷款发放
			public static final long  ZYRepayment = 2;//自营贷款收回
			public static final long  WTGrant = 3; //委托贷款发放
			public static final long  WTRepayment = 4; //委托贷款收回			
			public static final long  TXGrant = 5; //贴现贷款发放
			public static final long  TXRepayment = 6; //贴现贷款收回
			public static final long  YTGrant = 7; //银团贷款发放
			public static final long  YTRepayment = 8; //银团贷款收回						
			public static final long  ZYSettlementInterest = 9; //自营贷款结息
			public static final long  WTSettlementInterest = 10; //委托贷款结息			
			public static final long  WTSettlementInterestTax = 11; //委托贷款结息利息税费
			public static final long  WTSettlementInterestComm = 12; //委托贷款结手续费			
			public static String getNameByID(long id)
            {
				String name = "";
				switch((int)id)
                {
					case(int)ZYGrant:
                    {
						name = "自营贷款发放";
					}
					break;
					case (int)ZYRepayment:
                    {
						name = "自营贷款收回";
					}
					break;
					case (int)WTGrant:
                    {
						name = "委托贷款发放";
					}
					break;
					case (int)WTRepayment:
                    {
						name = "委托贷款收回";
					}
					break;
					case (int)TXGrant:
                    {
						name = "贴现贷款发放";
					}
					break;
					case (int)TXRepayment:
                    {
						name = "贴现收回";
					} 	
					break;
					case (int)YTGrant:
                    {
						name = "银团贷款发放";
					}
					break;
					case (int)YTRepayment:
                    {
						name = "银团贷款收回";
					} 	
					break;
					case (int)ZYSettlementInterest:
                    {
						name = "自营贷款结息";
					} 	
					break;										
					case (int) WTSettlementInterest:
                    {
						name = "委托贷款结息";
					} 	
					break;
					case (int) WTSettlementInterestTax:
                    {
						name = "委托贷款结息利息税费";
					} 	
					break;										
				}
				return name;
			}
		}		
	}
	
	public static final class AmountFlag
    {
		public static final long Amount  = 1; //发生额
		public static final long Balance = 2; //余额
	}
	
	/**
	 * 
	 * 页面操作定义
	 * 
	 */
	public static class Actions
	{
		public static final int CREATETEMPSAVE = 1; //新增临时保存
		public static final int MODIFYTEMPSAVE = 2; //修改临时保存
		public static final int CREATESAVE = 3; //创建保存
		public static final int MODIFYSAVE = 4; //修改保存
		public static final int DELETE = 5; //删除
		public static final int LINKSEARCH = 6; //链接查找
		public static final int MATCHSEARCH = 7; //匹配查找
		public static final int CHECK = 8; //复核/审核
		public static final int CANCELCHECK = 9; //取消复核
		public static final int NEXTSTEP = 10; //下一步
		public static final int TODETAIL = 11; //点交易号到详细页面
		public static final int MODIFYNEXTSTEP = 12; //更改下一步
		public static final int REJECT = 13; //拒绝
		public static final int RETURN = 14; //返回修改
		public static final int CHECKOVER = 15; //审核完成
		public static final int MASSCHECK = 16; //批量复核
		public static final int MASSCANCELCHECK = 17; //批量取消复核
		public static final int UPDATESEARCH = 18; //修改查找
		public static final int CHECKSEARCH = 19; //审核查找
		public static final int COMMIT = 20; //提交

		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case CREATETEMPSAVE :
					strReturn = "临时保存";
					break;
				case MODIFYTEMPSAVE :
					strReturn = "临时保存";
					break;
				case CREATESAVE :
					strReturn = "创建保存";
					break;
				case MODIFYSAVE :
					strReturn = "修改保存";
					break;
				case DELETE :
					strReturn = "删除";
					break;
				case LINKSEARCH :
					strReturn = "链接查找";
					break;
				case MATCHSEARCH :
					strReturn = "匹配查找";
					break;
				case CHECK :
					strReturn = "复核";
					break;
				case CANCELCHECK :
					strReturn = "取消复核";
					break;
				case NEXTSTEP :
					strReturn = "下一步";
					break;
				case TODETAIL :
					strReturn = "察看明细";
					break;
				case REJECT :
					strReturn = "拒绝";
					break;
				case RETURN :
					strReturn = "返回修改";
					break;
				case CHECKOVER :
					strReturn = "审核完成";
					break;
				case MASSCHECK :
					strReturn = "批量复核";
					break;
				case MASSCANCELCHECK :
					strReturn = "批量取消复核";
					break;
				case UPDATESEARCH :
					strReturn = "修改查找";
					break;
				case CHECKSEARCH :
					strReturn = "审核查找";
					break;
				case COMMIT :
					strReturn = "提交";

			}
			return strReturn;
		}
	}
	

	public static final class InterestSettlementDate
    {
		public final static long Mar_21 = 1;
		public final static long Jun_21 = 2;
		public final static long Sep_21 = 3;
		public final static long Dec_21 = 4;
		
		static public boolean isSettlementDate(Timestamp date)
        {
			boolean res = false;
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int month = calendar.get(GregorianCalendar.MONTH);
			int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
			if(month == GregorianCalendar.MARCH
					|| month == GregorianCalendar.JUNE
					|| month == GregorianCalendar.SEPTEMBER
					|| month == GregorianCalendar.DECEMBER){
				if(day == 21)
					res = true;
			}
			return res;
		}
		
		/**
		 * 是否是当前季度的结息日
		 * */
		static public boolean isCurrentQuarterSettlementInterestDate(Timestamp date)
        {
			System.out.println("---------start isCurrentQuarterSettlementInterestDate:");
			if(!InterestSettlementDate.isSettlementDate(date))
				return false;
			
			Timestamp today = Env.getSystemDateTime();
			GregorianCalendar currCalendar = new GregorianCalendar();
			currCalendar.setTime(today);
			int currentMonth = currCalendar.get(GregorianCalendar.MONTH);
			
			
			GregorianCalendar forecastCalendar = new GregorianCalendar();
			forecastCalendar.setTime(date);
			int forecastMonth = forecastCalendar.get(GregorianCalendar.MONTH);
			System.out.println("---------当前月份是:"+currentMonth);
			if((forecastMonth == GregorianCalendar.MARCH
					|| forecastMonth == GregorianCalendar.JUNE
					|| forecastMonth == GregorianCalendar.SEPTEMBER
					|| forecastMonth == GregorianCalendar.DECEMBER) && currentMonth == forecastMonth){
				return true;
			}
            else
				return false;
		}
		
	/*	*//**
		 * 获取上一个结息日
		 * *//*
		
		static public Timestamp getLastInterestSettlementDay(Timestamp date){
			GregorianCalendar currCalendar = new GregorianCalendar();
			currCalendar.setTime(date);
			int currentMonth = currCalendar.get(GregorianCalendar.MONTH);
			int currentYear = currCalendar.get(GregorianCalendar.YEAR);
			int currentDate = currCalendar.get(GregorianCalendar.DATE);
			GregorianCalendar resCal = new GregorianCalendar();
			int resYear = 0;
			int resMonth = 0;
			
			if(currentMonth == GregorianCalendar.JANUARY
					|| currentMonth == GregorianCalendar.JANUARY
					){
				resYear = currentYear-1;
				resMonth = GregorianCalendar.DECEMBER;
			}else if(currentMonth == GregorianCalendar.APRIL
					|| currentMonth == GregorianCalendar.MAY
					){
				resYear = currentYear;
				resMonth = GregorianCalendar.MAY;
			}else if(currentMonth == GregorianCalendar.JULY
					|| currentMonth == GregorianCalendar.AUGUST
					){
				resYear = currentYear;
				resMonth = GregorianCalendar.MAY;
			}else if(currentMonth == GregorianCalendar.OCTOBER
					|| currentMonth == GregorianCalendar.NOVEMBER
			){
				resYear = currentYear;
				resMonth = GregorianCalendar.MAY;
			}else if(currentMonth == GregorianCalendar.MARCH
					|| currentMonth == GregorianCalendar.JUNE
					|| currentMonth == GregorianCalendar.SEPTEMBER
					|| currentMonth == GregorianCalendar.DECEMBER){
				//if()
			}
			
			
		}
	*/	
	}

	public static final class DayCount
    {
		private static long maxDayCount = 0;	
		private static long runDayCount = 0;	
		
		public static DayCount getInstance()
        {
			String path = TPConstant.class.getResource("").getPath()+"/";
			String configFileName = "plan_daycount.properties";
			InputStream input = null;
			try 
            {
				input = new FileInputStream(configFileName);
				
				Properties p = new Properties();
				p.load(input);	
				maxDayCount = Long.parseLong(((String)p.get("maxDayCount")).trim());
				runDayCount = Long.parseLong(((String)p.get("runDayCount")).trim());
				
			} 
            catch (FileNotFoundException e) 
            {
				e.printStackTrace();
			} 
            catch (IOException e) 
            {
				e.printStackTrace();
			} 
            finally
            {
				
				try 
                {
					if(input!=null)
					   input.close();
					
				} 
                catch (IOException e1) 
                {
					e1.printStackTrace();
				}
			}
			DayCount dayCount = null;
			try 
            {
				dayCount = (DayCount)DayCount.class.newInstance();
			} 
            catch (Exception e) 
            {
				dayCount = new DayCount();
			}
			return dayCount;
		}
		
		public long getRunDayCount()
        {
			return runDayCount;
		}
		public long getMaxDayCount()
        {
			return maxDayCount;
		}
		
		public static void setRunDayCount(String val)
        {
			String path = TPConstant.class.getResource("").getPath()+"/";
			String configFileName = "plan_daycount.properties";
			OutputStream output = null;
			InputStream input = null;
			
			try 
            {
				input = new FileInputStream(configFileName);
	
				Properties p = new Properties();
				p.load(input);
				Object maxDayCount = p.get("maxDayCount");
				p.setProperty("maxDayCount",(String)maxDayCount);
				p.setProperty("runDayCount",val);
				
				output = new FileOutputStream(configFileName);
				p.store(output,"");
				
			} 
            catch (FileNotFoundException e) 
            {
				e.printStackTrace();
			} 
            catch (IOException e) 
            {
				e.printStackTrace();
			}
             finally
             {
				
				try 
                {
					if(output!=null)
						output.close();
					if(input!=null)
						input.close();
					
				} 
                catch (IOException e1) 
                {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	public static final class RowID
    {
		/*
	    3662	 	期末可用余额
	    3662	 	期末余额
		3661	     差额调节
		3660	 	资金头寸
		3659	 	必要备付金
		1830	 	资金运用
		2	 	    资金来源
		1	 	    期初可用余额
		1	 	    期初余额
		
	 */
		private static long beginBalanceRowID = 1;
		private static long beginBalanceRowID1 = 1;		
		private static long formRowID = 2;
		private static long toRowID = 1830;
		private static long needMoneyRowID = 3659;
		private static long cashRowID = 3660;
		private static long adjustCashRowID = 3660;
		private static long balanceRegulateRowID = 3661;
		private static long endBalanceRowID = 3662;
		private static long endBalanceRowID1 = 3662;
		private static long enterpriseDeposit = 0;
		private static long enterpriseWithdraw = 0;			
				
		public static RowID getInstance()
        {			
			String configFileName = "plan_aftertransconfig.properties";
			InputStream input = null;
			try
            {
				input = new FileInputStream(configFileName);
				Properties p = new Properties();
				p.load(input);
				beginBalanceRowID = Long.parseLong(((String)p.get("beginBalanceRowID")).trim());
				beginBalanceRowID1 = Long.parseLong(((String)p.get("beginBalanceRowID1")).trim());
				formRowID = Long.parseLong(((String)p.get("formRowID")).trim());
				toRowID = Long.parseLong(((String)p.get("toRowID")).trim());
				cashRowID = Long.parseLong(((String)p.get("cashRowID")).trim());
				adjustCashRowID = Long.parseLong(((String)p.get("adjustCashRowID")).trim());
				balanceRegulateRowID = Long.parseLong(((String)p.get("balanceRegulateRowID")).trim());
				endBalanceRowID = Long.parseLong(((String)p.get("endBalanceRowID")).trim());
				endBalanceRowID1 = Long.parseLong(((String)p.get("endBalanceRowID1")).trim());
				needMoneyRowID = Long.parseLong(((String)p.get("needMoneyRowID")).trim());
				enterpriseDeposit = Long.parseLong(((String)p.get("enterpriseDeposit")).trim());
				enterpriseWithdraw = Long.parseLong(((String)p.get("enterpriseWithdraw")).trim());	
				
			} 
            catch (FileNotFoundException e) 
            {				
				e.printStackTrace();
			} 
            catch (IOException e) 
            {				
				e.printStackTrace();
			}
			catch(Exception e)
            {
				System.out.println("not found plan_aftertransconfig.properties");
				//e.printStackTrace();
			} 
            finally
            {
				
				try 
                {
					if(input!=null)
					   input.close();
					
				} 
                catch (Exception e1) 
                {
					e1.printStackTrace();
				}
			}
			RowID rowId = null;
			try 
            {
				rowId = (RowID)RowID.class.newInstance();
			} 
            catch (Exception e) 
            {
				rowId = new RowID();
			}			
			return rowId;
		}
		
		public long getBeginBalanceRowID()
        {
			return beginBalanceRowID;
		}
		public long getFormRowID()
        {
			return formRowID;
		}
		public long getToRowID()
        {
			return toRowID;
		}
		public long getNeedMoneyRowID()
        {
			return needMoneyRowID;
		}
		public long getCashRowID()
        {
			return cashRowID;
		}
		public long getAdjustCashRowID()
        {
			return adjustCashRowID;
		}
		public long getBalanceRegulateRowID()
        {
			return balanceRegulateRowID;
		}
		public long getEndBalanceRowID()
        {
			return endBalanceRowID;
		}
		/**
		 * @return Returns the beginBalanceRowID1.
		 */
		public long getBeginBalanceRowID1() 
        {
			return beginBalanceRowID1;
		}

		/**
		 * @return Returns the endBalanceRowID1.
		 */
		public long getEndBalanceRowID1() 
        {
			return endBalanceRowID1;
		}
		/**
		 * @return Returns the enterpriseDeposit.
		 */
		public long getEnterpriseDeposit() 
        {
			return enterpriseDeposit;
		}
		/**
		 * @return Returns the enterpriseWithdraw.
		 */
		public long getEnterpriseWithdraw()
         {
			return enterpriseWithdraw;
		}		
	}
	
	/**
	 * 在添加客户的时候需要自动加入模板的指定行项目下
	 * lineNoPrefix1 //来源　－活期定期通知
	 * lineNoPrefix2 //来源　－自营，委托，贴现
	 * lineNoPrefix3 //运用　－活期定期通知
	 * lineNoPrefix4 //运用　－自营，委托，贴现
	 * @author ycliu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static final class LineNoPrefix
    {
		
		private static Prefixs p ;
		
		public static LineNoPrefix getInstance() throws IOException, SAXException
        {
			LineNoPrefix lineNo = new LineNoPrefix();
			String xmlFileName="plan_lineNoPrefix.xml";
			String userDir = System.getProperties().getProperty("user.dir");
			//System.out.println("----------userDir:"+userDir);
			java.io.File srcfile = new java.io.File(xmlFileName);
			Digester digester = new Digester();
			digester.addObjectCreate("prefixs",Prefixs.class);
			digester.addSetProperties("prefixs");
			digester.addObjectCreate("prefixs/clientType",ClientType.class);
			digester.addSetProperties("prefixs/clientType");
			digester.addSetNext("prefixs/clientType","add");
			
			digester.addObjectCreate("prefixs/clientType/prefix",Prefix.class);
			digester.addSetProperties("prefixs/clientType/prefix");
			digester.addCallMethod("prefixs/clientType/prefix/accountType","addAccountType",0);
			//digester.addCallMethod("prefixs/clientType/prefix/fromOrTo","setFromOrTo",0,new java.lang.Class[]{long.class});
			digester.addSetNext("prefixs/clientType/prefix","addPrefix");

			p = (Prefixs)digester.parse(srcfile); 
			
			return lineNo;
		}
		
		public Prefixs getAll()
        {
			return p;
		}
		public ClientType getClientType(long clientTypeId){
			return p.getClientTypePrefix(clientTypeId);
		}
	}
    
    /**
     * @name: YesOrNo
     * @description:是否
     * @author: jason
     * @create: 2005-11-16
     */
    public static class YesOrNo
    {
        public static final long YES = 1; //是

        public static final long NO  = 0; //否

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) YES:
                    strReturn = "是";
                    break;
                case (int) NO:
                    strReturn = "否";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { YES, NO };
            return lTemp;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
            return Constant.getAllCode("com.iss.itreasury.util.Constant$YesOrNo",officeID,currencyID);
        }                
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,String strProperty) throws Exception
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
    
    
    
    /**
     * @name: AmountDirection
     * @description:金额增减方向
     * @author: jason
     * @create: 2005-11-19
     */
    public static class AmountDirection
    {
        public static final long PLUS      = 1; //增加

        public static final long SUBTRACT  = 2; //减少

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) PLUS:
                    strReturn = "增加";
                    break;
                case (int) SUBTRACT:
                    strReturn = "减少";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { PLUS, SUBTRACT };
            return lTemp;
        }
                  
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,String strProperty) throws Exception
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
    
    
    /**
     * @name: AmountFlag
     * @description:金额标志
     * @author: jason
     * @create: 2005-11-19
     */
    public static class AmountFlagForSetting
    {
        public static final long Today_Amount       = 1; //本日发生额

        public static final long Today_Balance      = 2; //本日余额
        
        public static final long Yesterday_Balance  = 3; //上日余额

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) Today_Amount:
                    strReturn = "本日发生额";
                    break;
                case (int) Today_Balance:
                    strReturn = "本日余额";
                    break;
                case (int) Yesterday_Balance:
                    strReturn = "上日余额";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { Today_Amount, Today_Balance,Yesterday_Balance };
            return lTemp;
        }
                  
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,String strProperty) throws Exception
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
    
    
    
    
    /**
     * @name: CaculateFlag
     * @description:计算标志
     * @author: jason
     * @create: 2005-11-19
     */
    public static class CaculateFlag
    {
        public static final long Plus            = 1; //加法

        public static final long Subtract        = 2; //减法
        
        public static final long Multiplication  = 3; //乘法
        
        public static final long Division        = 4; //除法

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) Plus:
                    strReturn = "+";
                    break;
                case (int) Subtract:
                    strReturn = "-";
                    break;
                case (int) Multiplication:
                    strReturn = "*";
                    break;
                case (int) Division:
                    strReturn = "/";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { Plus, Subtract,Multiplication,Division };
            return lTemp;
        }
                  
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,String strProperty) throws Exception
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
    
    
   
    /**
     * @name: LineType
     * @description: 行项目的类型
     * @author: jason
     * @create: 2005-11-16
     */
    public static class LineType
    {
        public static final long Others         = 1; //其他行项目，可以在"其它行项目设置"中维护

        public static final long Balance_Begin  = 2; //期初可用余额
        
        public static final long Balance_End    = 3; //期末可用余额
        
        public static final long CommonItem     = 0; //一般项目(无特殊处理)
        
        
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) Others:
                    strReturn = "其他行项目";
                    break;
                case (int) Balance_Begin:
                    strReturn = "期初可用余额";
                    break;
                case (int) Balance_End:
                    strReturn = "期末可用余额";
                    break;
                case (int) CommonItem:
                    strReturn = "一般项目";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            {  CommonItem , Balance_Begin , Balance_End , Others };
            return lTemp;
        }
                        
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,String strProperty) throws Exception
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
    
    
  
    /**
     *
     * @name: DataSource
     * @description: 行项目的数据来自于哪个模块
     * @author: jason
     * @create: 2005-11-18
     *
     */
    public static class DataSource
    {
        //模块类型
        public static final long SETTLEMENT = 1; //结算

        public static final long LOAN = 2; //贷款

        public static final long SECURITIES = 5; //证券

        public static final long GENERALLEDGER = 9; // 总账

        //
        /**
         * 得到所有的模块
         * 
         * @return long[]
         */
        public static final long[] getAllCode()
        {
            long[] allCode = null;
            
            long[] lTemp =  { SETTLEMENT, LOAN,SECURITIES, GENERALLEDGER };
            
            allCode = lTemp;
            
            return allCode;
        }
        
        /**
         * 
         * @param lCode
         * @return @throws
         *         Exception
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) SETTLEMENT:
                    strReturn = "结算";
                    break;
                case (int) LOAN:
                    strReturn = "信贷";
                    break;
                case (int) SECURITIES:
                    strReturn = "证券";
                    break;
                case (int) GENERALLEDGER:
                    strReturn = "总账";
                    break;
            }
            return strReturn;
        }
        
        
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,String strProperty) throws Exception
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
}
