/*
 * Created on 2004-2-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem.basebean;
import java.sql.Connection;
import java.sql.Timestamp;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class FunctionBaseBean
{ 
	/**
				* 计算利息  
				* @param lOfficeID 办事处标识
				* @param lCurrencyID 币种标识  
				* @param tsDate 开/关机时间 
				* Description: 
				* ----待确定
				*/
	public boolean calculateInterest(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
				* 业务校验  
				* @param lOfficeID 办事处标识
				* @param lCurrencyID 币种标识  
				* @param tsDate 开/关机时间 
				*/
	public boolean checkTransaction(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
				* 日终处理 
				* @param lOfficeID 办事处标识
				* @param lCurrencyID 币种标识  
				* @param tsDate 开/关机时间 
				* Description: 
				* ------待确定
				*/
	public boolean endEveryDay(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}	
	/**
				* 抽取数据 
				* @param lOfficeID 办事处标识
				* @param lCurrencyID 币种标识  
				* @param tsDate 开/关机时间 
				* Description: 
				* ------待确定
				*/
	public boolean extractPlanData(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
					* 清洗与整理数据 
					* @param lOfficeID 办事处标识
					* @param lCurrencyID 币种标识  
					* @param tsDate 开/关机时间 
					* Description: 
					* ------待确定
					*/
	public boolean launderPlanData(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 设置开/关机处理状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lStatusID 状态标识
	*/
	public  boolean setDealStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		return true;
	}
	/**
	* 取得开/关机处理状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public  long getDealStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		return -1;
	}
	/**
	* 设置系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识
	* @param strMessage 关机信息 
	*/
	public  boolean setSystemStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		return true;
	
	}
	/**
	* 取得系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public  long getSystemStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		return -1;
	}
	/**
			* 设置系统时间
			* @param lOfficeID 办事处标识
			* @param lCurrencyID 币种标识
			* @param lModelID 模块标识
			* @param strMessage 关机信息 
			*/
	public  boolean setSystemDate(long lOfficeID, long lCurrencyID, Timestamp tsSystemDate) throws Exception
	{
		return true;
	}
	/**
	* 取得系统时间
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public  Timestamp getSystemDate(long lOfficeID, long lCurrencyID) throws Exception
	{
		return null;
	}
	/**
			* 结算修改系统时间、状态
			* @param lOfficeID 办事处标识
			* @param lCurrencyID 币种标识  
			* @param lSystemStatusID 开/关机时间 
			* @param tsDate 开/关机时间 
			* Description:  
			*/
	public boolean setSystemStatusAndDate(Connection conn, long lOfficeID, long lCurrencyID, long lSystemStatusID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
				* 开机其它操作
				* @param lOfficeID 办事处标识
				* @param lCurrencyID 币种标识
				* @param lModelID 模块标识
				* @param strMessage 关机信息 
				*/
	public boolean openOtherProcess(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
					* 关机其它操作
					* @param lOfficeID 办事处标识
					* @param lCurrencyID 币种标识
					* @param lModelID 模块标识
					* @param strMessage 关机信息 
					*/
	public boolean closeOtherProcess(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	
	/**
	* 协定存款校验  
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	*/
	public boolean checkNegotiate(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 活期账户校验  
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	*/
	public boolean checkCurrentAcount(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 合同起始日期  
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	*/
	public boolean setSystemContractDate(Connection conn, long lOfficeID, long lCurrencyID,long lSystemStatusID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 定期自动续存业务处理
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	*/
	public boolean fixedDepositAutoContinue(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
}
