/**
 * <p>Title:关机监控</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone </p>
 * <p>created by ：yychen</p>
 * <p>modified by :      </p>
 * <p>version 1.0</p>
 * <p>Description: </p>
 */
package com.iss.itreasury.closesystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import java.util.ArrayList;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogBiz;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogFactory;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;

public class CloseSystemMain
{
	private static long[] m_lOfficeID = null;
	private static String[][][] m_strDealMessage = null; ////开/关机处理信息[办事处][币种][模块]
	private static long m_lExportDataStatusID = -1; ////导出会计分录状态  
	private static String  m_message = ""; ////导出会计分录状态  
	public static int getOfficeIndex(long lOfficeID)
	{
		int i = 0;
		for (i = 0; i < m_lOfficeID.length; i++)
		{
			if (m_lOfficeID[i] == lOfficeID) //找到位置
			{
				break;
			}
		}
		return i;
	}
	/**
			* 取得开/关机处理允许最大时间 
			* return 最大时间(毫秒)
			*/
	public static long getDealMaxSecond()
	{
		return 10000;
	}
	/**
				* 取得开/关机处理中导出会计分路 
				* return 最大时间(毫秒)
				*/
	public static long getExportMaxSecond()
	{
		return 6000;
	}
	/**
		* 取得本系统的币种最大值（个数） 
		* return 币种最大值（个数） 
		*/
	public static long getCurrentCount()
	{
		return Constant.CurrencyType.getLength();
	}
	/**
			* 取得本系统的模块最大值（个数） 
			* return 模块最大值（个数） 
			*/
	public static long getModelCount()
	{
		//return Constant.ModuleType.getLength();
		//TODO should be checked by yychen
		return 7;
	}
	/**
	 * 增加开/关机处理信息
	 * @param lOfficeID 办事处标识
	 * @param lCurrencyID 币种标识
	 * @param lModelID 模块标识
	 * @param strMessage 关机信息 
	 */
	public static void addDealMessage(long lOfficeID, long lCurrencyID, long lModelID, String strMessage)
	{
		m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1] = DataFormat.formatString(m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1]) + " \n " + DataFormat.formatString(strMessage);
	}
	/**
	* 重置开/关机处理信息
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	*/
	public static void resetDealMessage(long lOfficeID, long lCurrencyID, long lModelID)
	{
		m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1] = "";
	}
	/**
	* 取得开/关机处理信息
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理信息
	*/
	public static String getDealMessage(long lOfficeID, long lCurrencyID, long lModelID)
	{
		return DataFormat.formatString(m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1]);
	}
	/**
		* 设置开/关机处理状态
		* @param lOfficeID 办事处标识
		* @param lCurrencyID 币种标识
		* @param lModelID 模块标识
		* @param strMessage 关机信息 
		*/
	public static void setDealStatusID(long lOfficeID, long lCurrencyID, long lModelID, long lStatusID) throws Exception
	{
		try
		{
			BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, lStatusID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	* 取得开/关机处理状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static long getDealStatusID(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
		return BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID);
	}
	/**
		* 设置系统状态
		* @param lOfficeID 办事处标识
		* @param lCurrencyID 币种标识
		* @param lModelID 模块标识
		* @param strMessage 关机信息 
		*/
	public static void setSystemStatusID(long lOfficeID, long lCurrencyID, long lModelID, long lStatusID) throws Exception
	{
		try
		{
			BeanFactory.getFunctionBean(lModelID).setSystemStatusID(lOfficeID, lCurrencyID, lStatusID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	* 取得系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static long getSystemStatusID(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
		return BeanFactory.getFunctionBean(lModelID).getSystemStatusID(lOfficeID, lCurrencyID);
	}
	/**
			* 设置系统时间
			* @param lOfficeID 办事处标识
			* @param lCurrencyID 币种标识
			* @param lModelID 模块标识
			* @param strMessage 关机信息 
			*/
	public static void setSystemDate(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsSystemDate) throws Exception
	{
		try
		{
			BeanFactory.getFunctionBean(lModelID).setSystemDate(lOfficeID, lCurrencyID, tsSystemDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	* 取得系统时间
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static Timestamp getSystemDate(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
		return BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID);
	}
	/**
	* 请求开关机处理  
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* @param lActionID 开/关机
	* @param tsDate 开/关机时间 
	* Description: 页面调用接口，方法中调用ShutDownDeal.deal();
	*/
	public static synchronized void requireDeal(long lOfficeID, long lCurrencyID, long lModelID, long lActionID, Timestamp tsDate)
	{
		boolean bIsPassed = true; ////是否通过校验
		boolean bIsSuccess = true; ////是否执行成功
		boolean bBeContinue = true; ///是否需要继续执行
		boolean bIsExecuted = false; ///是否执行过开关机操作
		boolean bNeedCheck = true; ////关机是否需要进行业务校验以及导出会计账]
		OpenCloseLogBiz ocLog =  null;
		try {
			ocLog =  OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,true);
			
			/////判断本办事处、本币种是否正在开、关机
			if (BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID) == Constant.ShutDownStatus.DOING)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"正在进行开关机操作,请等待！");
				bIsPassed = false;
				
				//Modify by leiyang date 2007/07/05
				try {
					if(lActionID == Constant.ShutDownAction.CLOSE) {
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"正在进行关机操作");
					}
					else {
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL ,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"正在进行开机操作");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			///判断本办事处、本币种是否可以开、关机
			if (BeanFactory.getFunctionBean(lModelID).getSystemStatusID(lOfficeID, lCurrencyID) == lActionID)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"正处于" + Constant.ShutDownAction.getName(lActionID) + "状态，请检查！");
				bIsPassed = false;
				
				//Modify by leiyang date 2007/07/05
				try {
					if(lActionID == Constant.ShutDownAction.CLOSE) {
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"正处于关机状态");
					}
					else {
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL ,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"正处于开机状态");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			if (bIsPassed)
			{
				long lDealingAction = lActionID; //////正在进行的操作----开、关机
				Timestamp tsDealingDate = null; //////正在进行的操作日期
				Timestamp tsSystem = BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID);
				//// 如果进行的是关机操作，则操作日期为系统时间；
				//// 如果进行的是开机，则操作时间为系统的下一天；
				if (lDealingAction == Constant.ShutDownAction.CLOSE)
				{
					tsDealingDate = tsSystem;
				}
				else
				{
					tsDealingDate = DataFormat.getNextDate(tsSystem);
				}
				///选择的操作日期是否有效
				if (DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) > 0)
				{
					CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"请选择正确的" + Constant.ShutDownAction.getName(lActionID) + "日期！");
					bIsPassed = false;
					
					//Modify by leiyang date 2007/07/05
					try {
						if(lActionID == Constant.ShutDownAction.CLOSE) {
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"没有选择正确的关机日期");
						}
						else {
							ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL ,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"没有选择正确的开机日期");
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				//////循环控制：开机后是否需要立即进行关机操作以及关机后是否需要立即进行开机操作；
				//////原因：为了解决跨天进行开机的问题；
				while (bIsPassed && bIsSuccess && bBeContinue && DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) <= 0)
				{
					Log.print("======================" + BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID) + "=====" + tsDealingDate + "====" + lDealingAction);
					CloseSystemMain.resetDealMessage(lOfficeID, lCurrencyID, lModelID);
					BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.DOING);
					bIsExecuted = true;
					DealThread deal = new DealThread(lOfficeID, lCurrencyID, lDealingAction, lModelID, tsDealingDate, bNeedCheck);
					Thread dealthread = new Thread(deal);
					dealthread.start();
					long lCountTime = 0;
					boolean bIsEnd = true;
					/////监视开关机操作是否完成
					/////如果操作超时，则强制退出，关机失败；
					/////如果关机成功，则退出监视循环；
					/////如果关机失败，则退出监视循环；
					/////否则，等待并循环监视。					
					while (bIsEnd)
					{
						if (lCountTime > CloseSystemMain.getDealMaxSecond())
						{
							dealthread.stop();
							dealthread = null;
							BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.OVERTIME);
							CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"开机超时，请重新开机！");
							bIsEnd = false;
							bIsSuccess = false;
						}
						else if (BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID) == Constant.ShutDownStatus.SUCCESS)
						{
							BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.SUCCESS);
							bIsEnd = false;
						}
						else if (BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID) == Constant.ShutDownStatus.FAIL)
						{
							BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
							bIsEnd = false;
							bIsSuccess = false;
						}
						else
						{
							lCountTime++;
						}
						Thread.currentThread().sleep(1000);
					}
					/////根据操作的成败，修改内存中的开关机操作状态
					if (bIsSuccess)
					{
						BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.SUCCESS);
					}
					else
					{
						BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
					}
					/////判断是否需要继续进行下一步操作
					/////1：刚才进行的是开机操作
					/////如果刚才进行的操作时间小于需要进行开关机操作的时间，则继续进行当天的关机操作，否则退出操作
					/////2：刚才进行的是关机操作
					/////如果刚才进行的操作时间小于需要进行开关机操作的时间，则继续进行下一天的开机操作，否则退出操作
					if (bIsSuccess)
					{
						if (lDealingAction == Constant.ShutDownAction.OPEN)
						{
							if (DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) < 0)
							{
								lDealingAction = Constant.ShutDownAction.CLOSE;
								tsDealingDate = tsDealingDate;
								bNeedCheck = false;
								
								//modify by leiyang3 解決開關機問題
								ocLog.colseConn();
								OpenCloseLogFactory.bulidInstance(lOfficeID,lCurrencyID,ocLog.lUserId,SETTConstant.OpenCloseType.CLOSE);
								ocLog = OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,true);
							}
							else
							{
								bBeContinue = false;
							}
						}
						else if (lDealingAction == Constant.ShutDownAction.CLOSE)
						{
							bIsExecuted = true;
							if (DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) < 0)
							{
								lDealingAction = Constant.ShutDownAction.OPEN;
								tsDealingDate = DataFormat.getNextDate(BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID));
								
								//modify by leiyang3 解決開關機問題
								ocLog.colseConn();
								OpenCloseLogFactory.bulidInstance(lOfficeID,lCurrencyID,ocLog.lUserId,SETTConstant.OpenCloseType.OPEN);
								ocLog = OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,true);
							}
							else
							{
								bBeContinue = false;
							}
						}
						else
						{
							bBeContinue = false;
						}
					}
				}
				//////如果执行过并且成功的执行，则修改操作状态为成功，否则修改为失败！
				if (bIsPassed && bIsSuccess && bIsExecuted)
				{
					BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.SUCCESS);
				}
				else
				{
					BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
				}
			}
			Log.print("结算End： " + CloseSystemMain.getDealMessage(lOfficeID, lCurrencyID, lModelID));
		
		}catch (Exception e){
			try
			{
				BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
				e.printStackTrace();
			}
			catch (Exception ss)
			{
				ss.printStackTrace();
			}
		} finally {
			try {
				ocLog.colseConn();
			} catch(IException ie) {
				ie.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 提交关机申请
	 * @param officeID
	 * @param currencyID
	 * @throws Exception
	 */
	public static void CloseApply(long officeID,long currencyID) throws Exception
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "update sett_officetime set nCloseSystemStatusID="+Constant.ShutDownStatus.REQUEST);
			strSQL.append( " ,dtApplyTime=sysdate ");
			strSQL.append( " where nOfficeID=? ");
			strSQL.append( " and nCurrencyID=? ");
			
			System.out.println("申请关机："+strSQL.toString());

			ps = conn.prepareStatement( strSQL.toString() );
			ps.setLong(1,officeID);
			ps.setLong(2,currencyID);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
	}

	
	/**
	 * 取消关机申请
	 * @param officeID
	 * @param currencyID
	 * @throws Exception
	 */
	public static void CancelCloseApply(long officeID,long currencyID) throws Exception
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "update sett_officetime set nCloseSystemStatusID="+Constant.ShutDownStatus.FAIL);
			strSQL.append( " ,dtApplyTime=null ");
			strSQL.append( " where nOfficeID=? ");
			strSQL.append( " and nCurrencyID=? ");
			
			System.out.println("取消申请关机："+strSQL.toString());

			ps = conn.prepareStatement( strSQL.toString() );
			ps.setLong(1,officeID);
			ps.setLong(2,currencyID);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
	}
	
	/**
	 * 获得关机队列
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public static CloseQueueItemInfo getCloseApplyInfo(long officeID,long currencyID) throws Exception
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		CloseQueueItemInfo item=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "select *  from sett_officetime where nStatusID=" + Constant.RecordStatus.VALID);
			strSQL.append( " and nOfficeID=? ");
			strSQL.append( " and nCurrencyID=? ");
			
			System.out.println("获取办事处关机状态："+strSQL.toString());

			ps = conn.prepareStatement( strSQL.toString() );
			ps.setLong(1,officeID);
			ps.setLong(2,currencyID);

			rs = ps.executeQuery();
			long id=1;
			while (rs.next())
			{
				item = new CloseQueueItemInfo();
				item.setID( id++ );
				item.setOfficeID( rs.getLong("nOfficeID"));
				item.setCurrencyID( rs.getLong("nCurrencyID"));
				item.setCloseSystemStatusID( rs.getLong("nCloseSystemStatusID"));
				item.setApplyTime(rs.getTimestamp("dtApplyTime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
		return item;
		
	}

	/**
	 * 获得关机队列
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public static ArrayList getCloseApplyQueue() throws Exception
	{
		ArrayList a = new ArrayList();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "select *  from sett_officetime where nStatusID=" + Constant.RecordStatus.VALID);
			strSQL.append( " and nCloseSystemStatusID in ("+Constant.ShutDownStatus.DOING +"," +Constant.ShutDownStatus.REQUEST+")");
			strSQL.append( " order by dtApplyTime");

			System.out.println("取关机队列："+strSQL.toString());
			ps = conn.prepareStatement( strSQL.toString() );
			rs = ps.executeQuery();
			long id=1;
			while (rs.next())
			{
				CloseQueueItemInfo item = new CloseQueueItemInfo();
				item.setID( id++ );
				item.setOfficeID( rs.getLong("nOfficeID"));
				item.setOfficeName(NameRef.getOfficeNameByID( item.getOfficeID() ));
				item.setCurrencyID( rs.getLong("nCurrencyID"));
				item.setCurrencyName(Constant.CurrencyType.getName(item.getCurrencyID()));
				item.setCloseSystemStatusID( rs.getLong("nCloseSystemStatusID"));
				item.setCloseSystemStatusName(Constant.ShutDownStatus.getName( item.getCloseSystemStatusID()));
				item.setApplyTime(rs.getTimestamp("dtApplyTime"));
				a.add(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
		
		return a;
		
	}
	
	//	初始化关机信息
	private static boolean m_bIsInit = false;
	static {
		if (!m_bIsInit)
		{
			PreparedStatement ps=null;
			ResultSet rs=null;
			Connection conn=null;			
			try
			{
				conn = Database.getConnection();
				//查找办事处的数量
				ps = conn.prepareStatement("select count(*) nCount from office where nStatusID=" + Constant.RecordStatus.VALID);
				rs = ps.executeQuery();
				int iOfficeCount = 0; //办事处的数量
				int iCurrentCount = (int) getCurrentCount();
				int iModelCount = (int) getModelCount();
				
				if (rs.next())
				{
					iOfficeCount = rs.getInt("nCount");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				m_lOfficeID = new long[iOfficeCount];
				m_strDealMessage = new String[iOfficeCount][iCurrentCount][iModelCount];
				ps = conn.prepareStatement("select * from Office where nStatusID=" + Constant.RecordStatus.VALID);
				rs = ps.executeQuery();
				int nSize = 0;
				while (rs.next())
				{
					m_lOfficeID[nSize] = rs.getLong("id");
					nSize++;
				}
			}
			catch (Exception exp)
			{
				System.out.println(exp);
			}
			finally{
				try {
					if (rs!=null)
					{
						rs.close();
						rs=null;
					}
					if (ps!=null)
					{
						ps.close();
						ps=null;
					}
					if (conn!=null)
					{
						conn.close();
						conn=null;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			m_bIsInit = true;
		}
	}
	
	public static void main(String args[])
	{
		try {
			//CloseSystemMain.CloseApply(1,1);
			//CloseQueueItemInfo info=CloseSystemMain.getCloseApplyInfo(1,1);
			//ArrayList a = CloseSystemMain.getCloseApplyQueue();
			CloseSystemMain.CancelCloseApply(1,1);
			//System.out.println("----"+a.size() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getM_message() {
		return m_message;
	}
	public static void setM_message(String mMessage) {
		m_message = mMessage;
	}
	
}
