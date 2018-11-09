/*
 * Created on 2004-2-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem.settlement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.closesystem.BeanFactory;
import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.closesystem.basebean.FunctionBaseBean;
import com.iss.itreasury.glinterface.isoftstone.GLISoftStoneBean;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.loan.util.DailyOperation;
import com.iss.itreasury.settlement.bankbill.dao.Sett_BankBillDailyReportDAO;
import com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestSettlement;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryCapitalSuperviseWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QCapitalSupervise;
import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDeposit;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class settFunctionBean extends FunctionBaseBean
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
		 * 
		 */
	public settFunctionBean()
	{
		super();
		// TODO Auto-generated constructor stub
	}
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
		boolean bIsPassed = true;
		try
		{
			/*
			 * 2003-12-29 华能需求变更，对复利征收复利。关机处理改变顺序
			 * 在日终处理中的 账户余额日结完成 后
			 * 1:起息日计息倒填处理
			 * 2:存款银行利率倒填处理
			 * 3:贷款银行利率倒填处理
			 * 4:贷款复利计算日期处理
			 * 5:每日算息
			 * */
			InterestBatch interestBatch = new InterestBatch(conn);
			//加上每日关机倒算利息2003-11-21---------------------------------
			if (bIsPassed)
			{
				//调用批处理接口中的起息日倒填处理接口
				log.print("关机线程开始调用【起息日倒填处理接口】......");
				if (interestBatch.interestStartDateBackward(conn, lOfficeID, lCurrencyID, tsDate) == 1)
				{
					log.print("关机线程调用起息日倒填处理接口功能完成...");
				}
			}
			//增加存款银行利率倒填处理接口 2003-12-30
			if (bIsPassed)
			{
				//调用批处理接口中的存款银行利率倒填处理接口
				log.print("关机线程开始调用【存款银行利率倒填处理接口】......");
				if (interestBatch.depositBankInterestBackward(conn, lOfficeID, lCurrencyID, tsDate) == 1)
				{
					log.print("关机线程调用存款银行利率倒填处理接口功能完成...");
				}
			}
			
			/*if (bIsPassed)
			{
				//调用中国国电贷款逾期处理接口 Boxu 2008-10-06
				System.out.println("关机线程开始调用【中国国电贷款逾期罚息和复利倒算接口】......");
				if (interestBatch.interestGuoDianStartDateBackward(conn, lOfficeID, lCurrencyID, tsDate) == 1)
				{
					System.out.println("关机线程调用中国国电贷款逾期罚息和复利倒算接口功能完成...");
				}
			}*/
			
		   //增加贷款银行利率倒填处理接口 2004-08-17
		   if (bIsPassed)
		   {
			  //调用批处理接口中的存款银行利率倒填处理接口
			  log.print("关机线程开始调用【贷款－银行利率倒填处理接口】......");
			  if (interestBatch.loanBankInterestBackward(conn, lOfficeID, lCurrencyID, tsDate) == 1)
			  {
				  log.print("关机线程调用贷款银行利率倒填处理接口功能完成...");
			  }
		   }
		   //增加贷款Libor利率倒填处理接口 2004-08-17
		   if (bIsPassed)
		   {
			  //调用批处理接口中的Libor利率倒填处理接口
			  log.print("关机线程开始调用【贷款－Libor利率倒填处理接口】......");
			  if (interestBatch.loanLiborInterestBackward(conn, lOfficeID, lCurrencyID, tsDate) == 1)
			  {
				  log.print("关机线程调用贷款Libor利率倒填处理接口功能完成...");
			  }
		   }
			
			if (bIsPassed)
			{
				//调用批处理接口中的每日算息接口
				log.print("关机线程开始调用【每日算息接口】......");
				//InterestBatch interestBatch = new InterestBatch();
				if (interestBatch.dailyCalculateInterest(conn, lOfficeID, lCurrencyID, tsDate) == 1)
				{
					log.print("关机线程调用每日算息接口功能完成...");
				}
			}
		}
		catch (IException e)
		{
			e.printStackTrace();
			bIsPassed = false;
			throw new Exception(e.getMessage());
		}
		return bIsPassed;
	}
	/**
				* 业务校验  
				* @param lOfficeID 办事处标识
				* @param lCurrencyID 币种标识  
				* @param tsDate 开/关机时间 
				*/
	public boolean checkTransaction(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		boolean bIsPassed = true;
		try
		{
			EndDayProcess process = new EndDayProcess();
			if (bIsPassed)
			{
				bIsPassed = process.checkUnCompleteTransaction(conn, lOfficeID, lCurrencyID, tsDate);
				log.print("***********"+bIsPassed);
			}
			if (bIsPassed)
			{
				bIsPassed = process.checkOtherOfficeUnCompleteTransaction(conn, lOfficeID, lCurrencyID, tsDate);
				log.print("***********"+bIsPassed);
			}
			if (bIsPassed)
			{
				bIsPassed = process.checkUnAvailableGL(conn, lOfficeID, lCurrencyID, tsDate);
				log.print("***********"+bIsPassed);
			}
			if (bIsPassed)
			{
				bIsPassed = process.checkGLBalance(conn, lOfficeID, lCurrencyID, tsDate);
				log.print("***********"+bIsPassed);
			}
			if (bIsPassed)
			{
				bIsPassed = process.checkAccountBalance(conn, lOfficeID, lCurrencyID, tsDate);
				log.print("***********"+bIsPassed);
			}
			if (bIsPassed)
			{
				bIsPassed = process.checkFailedGrantInterest(conn, lOfficeID, lCurrencyID, tsDate);
				log.print("***********"+bIsPassed);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
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
		boolean bIsPassed = true;
		try
		{
			EndDayProcess process = new EndDayProcess();
			if (bIsPassed)
			{
				//调用InterestBatch中的账户余额日结接口：dailyAccountBalanceSettlement
				//调用interestBatch的构造方法二!
				InterestBatch interestBatch = new InterestBatch(conn);
				long ltmp = interestBatch.dailyAccountBalanceSettlement(conn, lOfficeID, lCurrencyID, tsDate);
				if (ltmp == 1)
					bIsPassed = true;
				//bIsPassed = process.settEndAccount(conn, lOfficeID, lCurrencyID, tsDate);
			}
			if (bIsPassed)
			{
				bIsPassed = process.settEndGL(conn, lOfficeID, lCurrencyID, tsDate);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
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
	public boolean setDealStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		boolean bIsPassed = true;
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" update sett_OfficeTime set nCloseSystemStatusID = ? where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lOfficeID);
			ps.setLong(3, lCurrencyID);
			ps.executeUpdate();
			//Log.print(sbRecord.toString());
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		};
		return bIsPassed;
	}
	/**
	* 取得开/关机处理状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public long getDealStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		long lStatusID = -1;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select nCloseSystemStatusID from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				lStatusID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		};
		return lStatusID;
	}
	/**
	* 设置系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识
	* @param strMessage 关机信息 
	*/
	public boolean setSystemStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		boolean bIsPassed = true;
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" update sett_OfficeTime set sSystemStatusDesc = ? where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setString(1, Constant.SystemStatus.getName(lStatusID));
			ps.setLong(2, lOfficeID);
			ps.setLong(3, lCurrencyID);
			ps.executeUpdate();
			//Log.print(sbRecord.toString());
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			bIsPassed = false;
			e.printStackTrace();
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		};
		return bIsPassed;
	}
	/**
	* 取得系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public long getSystemStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		long lStatusID = -1;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select sSystemStatusDesc from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				lStatusID = Constant.SystemStatus.getID(rs.getString(1));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		};
		return lStatusID;
	}
	/**
			* 设置系统时间
			* @param lOfficeID 办事处标识
			* @param lCurrencyID 币种标识
			* @param lModelID 模块标识
			* @param strMessage 关机信息 
			*/
	public boolean setSystemDate(long lOfficeID, long lCurrencyID, Timestamp tsSystemDate) throws Exception
	{
		boolean bIsPassed = true;
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" update sett_OfficeTime set dtOpenDate = ? where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setTimestamp(1, tsSystemDate);
			ps.setLong(2, lOfficeID);
			ps.setLong(3, lCurrencyID);
			ps.executeUpdate();
			//Log.print(sbRecord.toString());
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			bIsPassed = false;
			e.printStackTrace();
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		};
		return bIsPassed;
	}
	/**
	* 取得系统时间
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public Timestamp getSystemDate(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		Timestamp tsSystemDate = null;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select dtOpenDate from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				tsSystemDate = rs.getTimestamp(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		};
		return tsSystemDate;
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
		PreparedStatement ps = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsPassed = true;
		try
		{
			String SequenceName = "seq_Trans" + DataFormat.formatInt(lOfficeID, 2) + DataFormat.formatInt(lCurrencyID, 2);
			try
			{
				if (lSystemStatusID == Constant.ShutDownAction.OPEN && bIsPassed)
				{
					sbSQL.setLength(0);
					sbSQL.append(" drop sequence " + SequenceName);
					ps = conn.prepareStatement(sbSQL.toString());
					Log.print(sbSQL.toString());
					long lResult = ps.executeUpdate();
					ps.close();
					ps = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if (lSystemStatusID == Constant.ShutDownAction.OPEN && bIsPassed)
				{
					sbSQL.setLength(0);
					sbSQL.append(" create sequence " + SequenceName);
					sbSQL.append(" increment by 1 ");
					sbSQL.append(" nomaxvalue ");
					sbSQL.append(" nominvalue ");
					sbSQL.append(" nocycle ");
					sbSQL.append(" noorder  ");
					ps = conn.prepareStatement(sbSQL.toString());
					Log.print(sbSQL.toString());
					long lResult = ps.executeUpdate();
					ps.close();
					ps = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (bIsPassed)
			{
				sbSQL.setLength(0);
				sbSQL.append(" update sett_OfficeTime set sSystemStatusDesc='" + Constant.SystemStatus.getName(lSystemStatusID) + "', dtOpenDate =?  ");
				sbSQL.append(" where nOfficeID =  " + lOfficeID + " and nCurrencyID = " + lCurrencyID);
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setTimestamp(1, tsDate);
				Log.print(sbSQL.toString());
				long lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				if (lResult > 0)
				{
					bIsPassed = true;
				}
				else
				{
					bIsPassed = false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		};
		return bIsPassed;
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
		boolean bIsPassed = true;
		try
		{
			if (bIsPassed)
			{
				DailyOperation operation = new DailyOperation();
				bIsPassed = operation.dealContractForOpen(conn, lOfficeID, lCurrencyID, tsDate);
			}			
			/*if (Env.RUN_ENVIRONMENT.equals("isoftstone")) //说明是内部)
			{
				GeneralLedgerOperation operation = new GeneralLedgerOperation(conn);
				operation.createSODGLBalance(lOfficeID, lCurrencyID, tsDate, conn);
			}*/
			GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
			
			if(glSettingInfo.getGlName().equals("ISOFTSTONE"))
			//if(Constant.GLType.getID()==Constant.GLType.ISOFTSTONE)
			{
				GeneralLedgerOperation operation = new GeneralLedgerOperation(conn);
				operation.createSODGLBalance(lOfficeID, lCurrencyID, tsDate, conn);
			}
	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
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
		boolean bIsPassed = true;
		try
		{
			EndDayProcess process = new EndDayProcess();
			bIsPassed = process.otherProcess(conn, lOfficeID, lCurrencyID, 1, tsDate);
			if (bIsPassed)
			{
				DailyOperation operation = new DailyOperation();
				bIsPassed = operation.dealContractForShutDown(conn, lOfficeID, lCurrencyID, tsDate);
			}
	
			////如果总账借口类型不是isoftstone，则需要折转余额，否则如果是isoftstone,则在导出会计分录时折转余额
			GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);

			//修改 by kenny(胡志强) 2007-03-19(解决科目余额双倍的问题)
			/**
			if(glSettingInfo.getGlName().equals("ISOFTSTONE"))
			//if(bIsPassed && Constant.GLType.getID()!= Constant.GLType.ISOFTSTONE)
			{
			    SettGLWithinDao settbean = new SettGLWithinDao();
			    Collection collVoucher = settbean.findGLVoucherByConditionForISS(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, tsDate, tsDate);
                System.out.println("*********bIsPassed******=" + bIsPassed);
                System.out.println("*********collVoucher******=" + collVoucher);
                if (bIsPassed && collVoucher != null)
                {
                    System.out.println("*********postGLVoucher start******=");
                    GLISoftStoneBean bean = new GLISoftStoneBean();
                    collVoucher = bean.postGLVoucher(collVoucher,lOfficeID,lCurrencyID,tsDate);
                    System.out.println("*********postGLVoucher end******=");
                }
			}
			*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
	}
	
	/**
	 * 协定存款校验
	 * 
	 * @param lOfficeID
	 *            办事处标识
	 * @param lCurrencyID
	 *            币种标识
	 * @param tsDate
	 *            开/关机时间
	 */
	public boolean checkNegotiate(Connection conn, long lOfficeID,long lCurrencyID, Timestamp tsDate) throws Exception {
		boolean bIsPassed = true;
		try {
			EndDayProcess process = new EndDayProcess();
			if (bIsPassed) {
				bIsPassed = process.checkNegotiate(conn, lOfficeID,lCurrencyID, tsDate);
				log.print("***********" + bIsPassed);
			}

		} catch (Exception e) {
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
	}
	/**
	 * 活期账户校验
	 * 
	 * @param lOfficeID
	 *            办事处标识
	 * @param lCurrencyID
	 *            币种标识
	 * @param tsDate
	 *            开/关机时间
	 */
	public boolean checkCurrentAcount(Connection conn, long lOfficeID,long lCurrencyID, Timestamp tsDate) throws Exception {
		boolean bIsPassed = true;
		try {
			EndDayProcess process = new EndDayProcess();
			if (bIsPassed) {
				bIsPassed = process.checkCurrentAcount(conn, lOfficeID,lCurrencyID, tsDate);
				log.print("***********" + bIsPassed);
			}

		} catch (Exception e) {
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
	}
	/**
	 * 合同起始日期修改
	 * 
	 * @param lOfficeID
	 *            办事处标识
	 * @param lCurrencyID
	 *            币种标识
	 * @param tsDate
	 *            开/关机时间
	 */
	public boolean setSystemContractDate(Connection conn, long lOfficeID,long lCurrencyID,long lSystemStatusID, Timestamp tsDate) throws Exception {
		boolean bIsPassed = true;
		try {
			EndDayProcess process = new EndDayProcess();
			if (bIsPassed) {
				bIsPassed = process.setSystemContractDate(conn, lOfficeID,lCurrencyID,lSystemStatusID, tsDate);
				log.print("***********" + bIsPassed);
			}

		} catch (Exception e) {
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
	}
	/**
	* 定期自动续存业务处理
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	*/
	public boolean fixedDepositAutoContinue(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		boolean bIsPassed = true;
		try {
			EndDayProcess process = new EndDayProcess();
			if (bIsPassed) {
				bIsPassed = process.fixedDepositAutoContinue(conn, lOfficeID,lCurrencyID, tsDate);
				log.print("***********" + bIsPassed);
			}

		} catch (Exception e) {
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
	}
	
}
