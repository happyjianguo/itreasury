/**
 * <p>
 * Title:结算关机处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: isoftstone
 * </p>
 * <p>
 * created by ：yychen
 * </p>
 * <p>
 * date ：2003-09-29
 * </p>
 * <p>
 * modified by :
 * </p>
 * <p>
 * version 1.0
 * </p>
 * <p>
 * Description: 1：按办事处、币种进行处理开/关机 2：线程，使用同一个事务处理，一旦失败全部回滚
 * </p>
 */
package com.iss.itreasury.closesystem;

import java.sql.Connection;
import java.sql.Timestamp;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.loan.closesystem.CloseSystem;
import com.iss.itreasury.loan.closesystem.CloseSystemHome;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogBiz;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogFactory;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

public class DealThread extends Thread
{

	private long		m_lOfficeID		= -1;

	private long		m_lCurrencyID	= -1;

	private long		m_lAction		= -1;

	private long		m_lModelID		= -1;

	private Timestamp	m_tsDate		= null;

	private boolean		m_bNeedCheck	= true;

	public DealThread()
	{

	}

	public DealThread(long lOfficeID, long lCurrencyID, long lAction, long lModelID, Timestamp tsDate,
			boolean bNeedCheck)
	{

		m_lOfficeID = lOfficeID;
		m_lCurrencyID = lCurrencyID;
		m_lAction = lAction;
		m_lModelID = lModelID;
		m_tsDate = tsDate;
		m_bNeedCheck = bNeedCheck;
	}

	public void run()
	{

		boolean bIsSuccess = true; // //是否执行成功
		try
		{
			Log.print("================开始结算开、关机线程================");
			if (m_lAction == Constant.ShutDownAction.OPEN)
			{
				bIsSuccess = openSystem(m_lOfficeID, m_lCurrencyID, m_lModelID, m_tsDate);
			}
			else if (m_lAction == Constant.ShutDownAction.CLOSE)
			{
				bIsSuccess = closeSystem(m_lOfficeID, m_lCurrencyID, m_lModelID, m_tsDate, m_bNeedCheck);
			}
			if (bIsSuccess)
			{
				BeanFactory.getFunctionBean(m_lModelID).setDealStatusID(m_lOfficeID, m_lCurrencyID,
						Constant.ShutDownStatus.SUCCESS);
			}
			else
			{
				BeanFactory.getFunctionBean(m_lModelID).setDealStatusID(m_lOfficeID, m_lCurrencyID,
						Constant.ShutDownStatus.FAIL);
			}
			Log.print("================退出结算开、关机线程================");
		}
		catch (Exception e)
		{
			try
			{
				BeanFactory.getFunctionBean(m_lModelID).setDealStatusID(m_lOfficeID, m_lCurrencyID,
						Constant.ShutDownStatus.FAIL);
				e.printStackTrace();
			}
			catch (Exception ss)
			{
				ss.printStackTrace();
			}
		}
	}

	/**
	 * 开机操作
	 * 
	 * @param lOfficeID 办事处标识
	 * @param lCurrencyID 币种标识
	 * @param tsDate 开/关机时间 Description:
	 */
	private boolean openSystem(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsDate)
	{

		boolean bIsPassed = true;
		long lSuccessOrFailed = -1;
		Connection conn = null;
		OpenCloseLogBiz ocLog =  null;
		try
		{
			ocLog =  OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,false);
			
			Log.print("================开始进行开机操作================");
			CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, "开始"
					+ DataFormat.getDateString(tsDate) + "开机操作！");
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			if (bIsPassed)
			{
				
				//Modify by leiyang date 2007/07/27
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).openOtherProcess(conn, lOfficeID, lCurrencyID, tsDate);
				}
				catch(Exception e){
					ocLog.addOpenLog(SETTConstant.OpenSystemLogType.READSUBJECT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导入科目余额操作失败");
					e.printStackTrace();
					throw e;
				}
			}

			// 开机导入科目余额(For oracleCPF)
			Log.print("===================开始导入科目余额操作(外围操作)===============" + bIsPassed);
			if (bIsPassed)
			{
				try
				{
					if (Config.getBoolean(Config.SETT_AUTO_IMPORT_GLSUBJECT_BALANCE, false))
					{

						Log.print("===================开始导入科目余额操作===============");
						GlSettingInfo glSettingInfo = new GlSettingInfo();
						glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID);
						
						if(glSettingInfo.getGlName().equals("U850")){
							if(glSettingInfo.getGlOperationType() == Constant.GLOperationType.NoOperator || glSettingInfo.getGlOperationType() == Constant.GLOperationType.ExportOnly){
								throw new IException("总账接口设置错误");
							}
						}
						

						Log.print("===================开始导入科目余额操作=====为ORACLE_CPF==========");
						//开机时取开机日期前一天的的科目余额 modify 2008-01-03 for hualian
						Timestamp previousOpenData= new Timestamp(DataFormat.getPreviousOrNextDate(tsDate, -1).getTime());
						System.out.println("取开机日期前一天为"+previousOpenData);
						GLDealBean.addSubjectBalance(lOfficeID, lCurrencyID, lModelID, previousOpenData, previousOpenData);
						Log.print("===================结束导入科目余额操作===============");
					}

					if (bIsPassed) {
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.READSUBJECT,SETTConstant.OpenCloseStatus.SUCCEED,"导入科目余额操作成功");
					}
					else {
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.READSUBJECT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导入科目余额操作失败");
					}
				}
				catch (IException ie)
				{
					ocLog.addOpenLog(SETTConstant.OpenSystemLogType.READSUBJECT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导入科目余额操作失败：" + ie.getMessage());
					ie.printStackTrace();
					bIsPassed = false;
				}
				catch (Exception e)
				{
					ocLog.addOpenLog(SETTConstant.OpenSystemLogType.READSUBJECT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导入科目余额操作失败");
					e.printStackTrace();
					bIsPassed = false;
				}
			}
			// //修改系统状态
			if (bIsPassed)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, "开始"
						+ DataFormat.getDateString(tsDate) + "修改系统状态，请等待......");
				
				//Modify by leiyang date 2007/07/27
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).setSystemStatusAndDate(conn, lOfficeID, lCurrencyID, Constant.SystemStatus.OPEN, tsDate);
				}
				catch(Exception e){
					ocLog.addOpenLog(SETTConstant.OpenSystemLogType.MODIFYSYSTEMSTATE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"记录开机日志失败");
					e.printStackTrace();
					throw e;
				}
				
				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, DataFormat
								.getDateString(tsDate)
								+ "修改系统状态成功！");
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.MODIFYSYSTEMSTATE,SETTConstant.OpenCloseStatus.SUCCEED,"记录开机日志成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, DataFormat
								.getDateString(tsDate)
								+ "修改系统状态失败！");
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.MODIFYSYSTEMSTATE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"记录开机日志失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			// ///结束处理提交
			// ////如果成功，则提交操作，否则全部回滚
			if (bIsPassed)
			{
				conn.commit();
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, DataFormat
						.getDateString(tsDate)
						+ "开机成功！");
			}
			else
			{
				conn.rollback();
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, DataFormat
						.getDateString(tsDate)
						+ "开机失败！");
				//Modify by leiyang date 2007/06/29
				ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"开机失败");
			}
			conn.close();
			conn = null;
			Log.print("================结束进行开机操作================");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
				BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID,
						Constant.ShutDownStatus.FAIL);
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, DataFormat
						.getDateString(tsDate)
						+ "开机失败！");
				//Modify by leiyang date 2007/06/29
				ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"开机失败");
			}
			catch (Exception sql)
			{
			}
			bIsPassed = false;
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				Log.print("*************finally********");
				e.printStackTrace();
			}
		};
		return bIsPassed;
	}

	/**
	 * 关机操作
	 * 
	 * @param lOfficeID 办事处标识
	 * @param lCurrencyID 币种标识
	 * @param tsDate 开/关机时间 Description:
	 * 
	 */
	private boolean closeSystem(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsDate, boolean bNeedCheck)
	{

		boolean bIsPassed = true;
		Connection conn = null;
		OpenCloseLogBiz ocLog =  null;
		try
		{
			ocLog =  OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID, false);
			
			Log.print("================开始进行关机操作================");
			CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, "开始"
					+ DataFormat.getDateString(tsDate) + "关机操作！");
			conn = Database.getConnection();
			conn.setAutoCommit(false);

			// //// 协定存款到期校验
			// ////执行条件：前几个操作成功，并且需要进行该操作
			if (bIsPassed && bNeedCheck)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "协定存款校验，请等待......");
				
				//Modify by leiyang date 2007/07/27
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).checkNegotiate(conn, lOfficeID, lCurrencyID, tsDate);
				}
				catch(Exception e){
					ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ACCORDDEPOSIT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"协定存款校验失败");
					e.printStackTrace();
					throw e;
				}

				// bIsPassed=false;
				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "协定存款校验成功！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ACCORDDEPOSIT,SETTConstant.OpenCloseStatus.SUCCEED,"协定存款校验成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "协定存款校验失败！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ACCORDDEPOSIT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"协定存款校验失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}			
             //	校验活期账户是否已经复核 modify by xwhe 2008-03-05
			//  执行条件：前几个操作成功，并且需要进行该操作
			if (bIsPassed && bNeedCheck)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "活期账户校验，请等待......");			
				
				//Modify by xwhe date 2008/03/05
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).checkCurrentAcount(conn, lOfficeID, lCurrencyID, tsDate);
				}
				catch(Exception e){
					ocLog.addCloseLog(SETTConstant.CloseSystemLogType.CHECKCURRENTACCOUNT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"活期账户校验失败");
					e.printStackTrace();
					throw e;
				}
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "活期账户校验成功！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.CHECKCURRENTACCOUNT,SETTConstant.OpenCloseStatus.SUCCEED,"活期账户校验成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "活期账户校验失败！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.CHECKCURRENTACCOUNT,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,CloseSystemMain.getM_message());
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			// ////业务校验
			// ////执行条件：前几个操作成功，并且需要进行该操作
			if (bIsPassed && bNeedCheck)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "业务校验，请等待......");
				bIsPassed = BeanFactory.getFunctionBean(lModelID).checkTransaction(conn, lOfficeID, lCurrencyID, tsDate);
				
				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "业务校验成功！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.BUSINESS,SETTConstant.OpenCloseStatus.SUCCEED,"业务校验成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "业务校验失败！");
						//ocLog.addCloseLog(SETTConstant.CloseSystemLogType.BUSINESS,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"业务校验失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			//定期自动续存业务处理
			if (bIsPassed)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "定期自动续存业务，请等待......");
				bIsPassed = BeanFactory.getFunctionBean(lModelID).fixedDepositAutoContinue(null, lOfficeID, lCurrencyID, tsDate);
				
				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "定期自动续存业务处理成功！");
						//ocLog.addCloseLog(SETTConstant.CloseSystemLogType.FIXEDDEPOSITAUTOCONTINUE,SETTConstant.OpenCloseStatus.SUCCEED,"定期自动续存业务处理成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "定期自动续存业务处理失败！");
						//ocLog.addCloseLog(SETTConstant.CloseSystemLogType.FIXEDDEPOSITAUTOCONTINUE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"定期自动续存业务处理失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			// ////日终处理
			// ////执行条件：前几个操作成功
			if (bIsPassed)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "日终处理，请等待......");

				//Modify by leiyang date 2007/07/27
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).endEveryDay(conn, lOfficeID, lCurrencyID, tsDate);
				}
				catch(Exception e){
					ocLog.addCloseLog(SETTConstant.CloseSystemLogType.DAYBOOKDISPOSE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"日终处理失败");
					e.printStackTrace();
					throw e;
				}

				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "日终处理成功！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.DAYBOOKDISPOSE,SETTConstant.OpenCloseStatus.SUCCEED,"日终处理成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "日终处理失败！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.DAYBOOKDISPOSE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"日终处理失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}

			// //////计算利息
			// ////执行条件：前几个操作成功
			Log.print(DataFormat.getDateString(tsDate) + "*********************计算利息外面开始********************");
			Log.print(DataFormat.getDateString(tsDate) + "*********************计算利息外面开始********************");
			if (bIsPassed)
			{
				Log.print(DataFormat.getDateString(tsDate) + "*******计算利息*******************开始********");
				Log.print(DataFormat.getDateString(tsDate) + "*******计算利息*******************开始********");
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "计算利息，请等待......");
				
				//Modify by leiyang date 2007/07/27
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).calculateInterest(conn, lOfficeID, lCurrencyID,tsDate);
				}
				catch(Exception e){
					ocLog.addCloseLog(SETTConstant.CloseSystemLogType.COUNTINTEREST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,e.getMessage());
					e.printStackTrace();
					throw e;
				}
				
				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "计算利息成功！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.COUNTINTEREST,SETTConstant.OpenCloseStatus.SUCCEED,"计算利息成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "计算利息失败！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.COUNTINTEREST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"计算利息失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
				
				Log.print(DataFormat.getDateString(tsDate) + "*******计算利息*******************结束********");
				Log.print(DataFormat.getDateString(tsDate) + "*******计算利息*******************结束********");
			}
			Log.print(DataFormat.getDateString(tsDate) + "*********************计算利息外面结束********************");
			Log.print(DataFormat.getDateString(tsDate) + "*********************计算利息外面结束********************");
			// //////导出会计分录数据
			// ////执行条件：前几个操作成功，并且需要进行该操作
			Log.print(DataFormat.getDateString(tsDate) + "*********************关机是否导出会计分录：" + Config.getBoolean(ConfigConstant.SETT_CLOSESYSTEM_AUTOPOSTGLVOUCHER, false) + "********************");
			
			if(Config.getBoolean(ConfigConstant.SETT_CLOSESYSTEM_AUTOPOSTGLVOUCHER, false)){//added by qhzhou 2011-08-13 是否关机导会计分录
				Log.print(DataFormat.getDateString(tsDate) + "*********************导出会计分录开始********************");
				Log.print(DataFormat.getDateString(tsDate) + "*********************导出会计分录开始********************");
				GlSettingInfo glSettingInfo = new GlSettingInfo();
				// 查找导出的接口配置
				glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID);
	
				if (glSettingInfo.getGlName().equalsIgnoreCase("GENERSOFT")) // 浪潮接口
				{
					//if (bIsPassed && bNeedCheck)
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
								+ DataFormat.getDateString(tsDate) + "校验导出会计分录数据，请等待......");
	
						//Modify by leiyang date 2007/07/27
						try {
							bIsPassed = BeanFactory.getGLWithinBean(lModelID).checkPostVoucher(lOfficeID, lCurrencyID,lModelID, tsDate, tsDate);
						}
						catch(Exception e){
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
							e.printStackTrace();
							throw e;
						}
						
						//Modify by leiyang date 2007/07/05
						try {
							if (bIsPassed)
							{
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
										.getDateString(tsDate)
										+ "导出会计分录数据成功！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.SUCCEED,"导出会计分录数据成功");
							}
							else
							{
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
										.getDateString(tsDate)
										+ "导出会计分录数据失败！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
							}
						}
						catch(Exception e){
							e.printStackTrace();
							throw e;
						}
					}
					else
					{
						Log.print("==========前在处理没有成功，不能导出会计分录(浪潮接口)");
					}
				}
				else if(glSettingInfo.getGlName().equalsIgnoreCase("GASOFT")) //金算盘接口
				{
					//if (bIsPassed && bNeedCheck)
					if (bIsPassed)			  
					{
				  		CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始" + DataFormat.getDateString(tsDate) + "校验导出会计分录数据，请等待......");
						
						//Modify by leiyang date 2007/07/27
						try {
							bIsPassed = BeanFactory.getGLWithinBean(lModelID).checkPostVoucher(lOfficeID, lCurrencyID, lModelID, tsDate, tsDate);
						}
						catch(Exception e){
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
							e.printStackTrace();
							throw e;
						}
	
						//Modify by leiyang date 2007/07/05
						try {
							if (bIsPassed)
							{
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate) + "导出会计分录数据成功！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.SUCCEED,"导出会计分录数据成功");
							}
							else
							{
								///如果没有点击确认，则自动确定操作
								
								//Modify by leiyang date 2007/07/27
								try {
									bIsPassed  = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).updatePostedVoucherStatus(lOfficeID,lCurrencyID,lModelID,tsDate,tsDate);
									bIsPassed = BeanFactory.getGLWithinBean(lModelID).checkPostVoucher(lOfficeID, lCurrencyID, lModelID, tsDate, tsDate);
								}
								catch(Exception e){
									ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
									e.printStackTrace();
									throw e;
								}
								
								//Modify by leiyang date 2007/07/05
								try {
									if (bIsPassed)
									{
										CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate) + "导出会计分录数据成功！");
										ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.SUCCEED,"导出会计分录数据成功");
									}
									else
									{
										CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate) + "导出会计分录数据失败！");
										ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
									}
								}
								catch(Exception e){
									e.printStackTrace();
									throw e;
								}
						    }
						}
						catch(Exception e){
							e.printStackTrace();
							throw e;
						}
				  	}
				  	else{
				  		Log.print("==========前在处理没有成功，不能导出会计分录(金算盘接口)");
				  	}
				 }
				else if (glSettingInfo.getGlName().equalsIgnoreCase("KINGDEE")) // 金蝶接口
				{
					//if (bIsPassed && bNeedCheck)
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
								+ DataFormat.getDateString(tsDate) + "导出会计分录数据，请等待......");
						
						//Modify by leiyang date 2007/07/27
						try {
							bIsPassed = GLDealBean.postGLVoucher(lOfficeID, lCurrencyID, lModelID, tsDate, tsDate);
						}
						catch(Exception e){
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
							e.printStackTrace();
							throw e;
						}
						
						//Modify by leiyang date 2007/07/05
						try {
							if (bIsPassed)
							{
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
										.getDateString(tsDate)
										+ "导出会计分录数据成功！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.SUCCEED,"导出会计分录数据成功");
							}
							else
							{
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
										.getDateString(tsDate)
										+ "导出会计分录数据失败！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
							}
						}
						catch(Exception e){
							e.printStackTrace();
							throw e;
						}
					}
					else
					{
						Log.print("==========前在处理没有成功，不能导出会计分录(金蝶接口)");
					}
				}
				else
				{
					//if (bIsPassed && bNeedCheck)
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
								+ DataFormat.getDateString(tsDate) + "导出会计分录数据，请等待......");
						
						//Modify by leiyang date 2007/07/27
						try {
							/**add annotation by kevin(刘连凯) 2011-06-14
							 * 注意：“导出会计分录数据”这一模块不能使用关机的connection，
							 * 不然的话，当第一次关机失败，导出的凭证不能被自动撤销，而connection的回滚会使传输状态会由成功回滚成失败状态，再次关机就会出现重复导凭证的情况。
							 */
							bIsPassed = GLDealBean.postGLVoucher(lOfficeID, lCurrencyID, lModelID, tsDate, tsDate);
						}
				        catch (IException ie){
				        	CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
									.getDateString(tsDate)
									+ "导出会计分录数据失败！原因："+ie.getMessage());
				        	ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败："+ie.getMessage());
				            ie.printStackTrace();
				            throw ie;
				        }
						catch(Exception e){
							CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
									.getDateString(tsDate)
									+ "导出会计分录数据失败！原因："+e.getMessage());
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
							e.printStackTrace();
							throw e;
						}
	
						//Modify by leiyang date 2007/07/05
						try {
							if (bIsPassed)
							{
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
										.getDateString(tsDate)
										+ "导出会计分录数据成功！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.SUCCEED,"导出会计分录数据成功");
							}
							else
							{
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat
										.getDateString(tsDate)
										+ "导出会计分录数据失败！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXPORTTREASURERLIST,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"导出会计分录数据失败");
							}
						}
						catch(Exception e){
							e.printStackTrace();
							throw e;
						}
					}
					else
					{
						Log.print("==========前在处理没有成功，不能导出会计分录(其它接口)");
					}
				}
			
				Log.print(DataFormat.getDateString(tsDate) + "*********************导出会计分录结束********************");
				Log.print(DataFormat.getDateString(tsDate) + "*********************导出会计分录结束********************");
			}	

			// ////抽取资金计划数据
			// ////执行条件：前几个操作成功
			if (bIsPassed)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "抽取资金计划数据，请等待......");
				
				//Modify by leiyang date 2007/07/27
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).extractPlanData(conn, lOfficeID, lCurrencyID, tsDate);
				}
				catch(Exception e){
					ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXTRACTIVEBANKROLLPLAN,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"抽取资金计划数据失败");
					e.printStackTrace();
					throw e;
				}
				
				
				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "抽取资金计划数据成功！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXTRACTIVEBANKROLLPLAN,SETTConstant.OpenCloseStatus.SUCCEED,"抽取资金计划数据成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "抽取资金计划数据失败！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.EXTRACTIVEBANKROLLPLAN,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"抽取资金计划数据失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false);  //是否为航天科工项目
			if(!isHTKG)
			{
				// //修改合同状态
				// ////执行条件：前几个操作成功
				if (bIsPassed)
				{
					//Modify by leiyang date 2007/07/27
					try {
						bIsPassed = BeanFactory.getFunctionBean(lModelID).closeOtherProcess(conn, lOfficeID, lCurrencyID, tsDate);
					}
					catch(Exception e){
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.MODIFYCONTRACTSTATE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"修改合同状态失败");
						e.printStackTrace();
						throw e;
					}
					
					//Modify by leiyang date 2007/07/27
					try {
						if (bIsPassed) {
							CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
									+ "修改合同状态成功！");
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.MODIFYCONTRACTSTATE,SETTConstant.OpenCloseStatus.SUCCEED,"修改合同状态成功");
						}
						else {
							CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
									+ "修改合同状态失败！");
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.MODIFYCONTRACTSTATE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"修改合同状态失败");
						}
					}
					catch(Exception e){
						e.printStackTrace();
						throw e;
					}
				}
			}
			else
			{
				
				// 增加关机对贷款模块的处理 add by zhouxiang 2011
				if (bIsPassed){
					boolean isLoan = false;
					try{
						String contextURL = Config.getProperty(ConfigConstant.LOAN_REMOTECALL_IP);
						String contextPort = Config.getProperty(ConfigConstant.LOAN_REMOTECALL_PORT);
						if(contextURL == null || contextPort == null){
							Log.print("===================贷款模块处理接口未设置===============");
							throw new IException("===================贷款模块处理接口设置错误==================="); //接口设置错误必须关机失败，否则可能导致贷款合同状态信息不正确
						}else{
							isLoan = true;
							Log.print("===================开始对贷款模块的处理===============");
							CloseSystemHome closeSystemHome = null;
							CloseSystem closeSystem = null;
						    closeSystemHome = (CloseSystemHome)EJBObject.getRemoteEjbHome("HTCloseSystemHome",CloseSystemHome.class,contextURL,contextPort);
						    closeSystem = closeSystemHome.create();
						    bIsPassed = closeSystem.dealContractForShutDown(null,lOfficeID,lCurrencyID,tsDate); 
						}
					}catch (Exception e)
					{
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"贷款模块处理失败");
						e.printStackTrace();
						throw e;
					}
					
					if(isLoan){
						try {
							if (bIsPassed) {
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
										+ "贷款模块处理成功！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.SUCCEED,"贷款模块处理成功");
							}
							else {
								CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
										+ "贷款模块处理失败！");
								ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"贷款模块处理失败");
							}
						}
						catch(Exception e){
							e.printStackTrace();
							throw e;
						}
					}
				}
			}
 
			// //修改系统状态
			// ////执行条件：前几个操作成功
			if (bIsPassed)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, "开始"
						+ DataFormat.getDateString(tsDate) + "修改系统状态，请等待......");
				
				//Modify by leiyang date 2007/07/27
				try {
					bIsPassed = BeanFactory.getFunctionBean(lModelID).setSystemStatusAndDate(conn, lOfficeID, lCurrencyID, Constant.SystemStatus.CLOSE, tsDate);
				}
				catch(Exception e){
					ocLog.addCloseLog(SETTConstant.CloseSystemLogType.MODIFYSYSTEMSTATE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"记录关机日志失败");
					e.printStackTrace();
					throw e;
				}
				
				//Modify by leiyang date 2007/07/05
				try {
					if (bIsPassed)
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "修改系统状态成功！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.MODIFYSYSTEMSTATE,SETTConstant.OpenCloseStatus.SUCCEED,"记录关机日志成功");
					}
					else
					{
						CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
								+ "修改系统状态失败！");
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.MODIFYSYSTEMSTATE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"记录关机日志失败");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}		
			// ///结束处理提交
			// ////如果成功，则提交操作，否则全部回滚
			if (bIsPassed)
			{
				conn.commit();
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
						+ "关机成功！");
			}
			else
			{
				conn.rollback();
				//Modify by leiyang date 2007/06/28
				ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"关机失败");
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
						+ "关机失败！");
			}
			conn.close();
			conn = null;
			Log.print("================结束进行关机操作================");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
				BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID,
						Constant.ShutDownStatus.FAIL);
				//Modify by leiyang date 2007/06/28
				ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"关机失败");
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, DataFormat.getDateString(tsDate)
						+ "关机失败！");
			}
			catch (Exception sql)
			{
			}
			bIsPassed = false;
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				Log.print("*************finally********");
				e.printStackTrace();
			}
		};
		return bIsPassed;
	}

	public boolean closeSystemPublic(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsDate,
			boolean bNeedCheck)
	{

		return closeSystem(lOfficeID, lCurrencyID, lModelID, tsDate, bNeedCheck);
	}

	public boolean openSystemPublic(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsDate)
	{

		return openSystem(lOfficeID, lCurrencyID, lModelID, tsDate);
	}
}
