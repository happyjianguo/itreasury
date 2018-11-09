package com.iss.itreasury.settlement.Liquidation.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;

import com.iss.itreasury.settlement.Liquidation.dao.LiquidationDao;
import com.iss.itreasury.settlement.Liquidation.dao.LiquidationDetailDao;
import com.iss.itreasury.settlement.Liquidation.dataentity.LiquidationInfo;
import com.iss.itreasury.settlement.Liquidation.dataentity.LiquidationdetailInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class LiquitationBiz {
	
	/**保存清算交易
	 * 执行此方法将生成一条清算主交易和多比明细交易，明细交易的笔数则
	 * 要看参与交易或协调交易的办事处个数，每个办事处会生成一条明细交易
	 * @param LiquidationInfo info
	 * @return
	 */
	public long Save(LiquidationInfo info,Connection conn)
	{
		long lret = -1;
		Connection conInternal = null;
		try
		{
			// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
			if (conn == null)
			{
				try
				{
					conInternal = Database.getConnection();
					conInternal.setAutoCommit(false);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new IException("无法取得数据库连接");
				}
			}
			else
			{
				conInternal = conn;
			}
			
			//开始保存主表
			LiquidationDao dao = new LiquidationDao(conInternal);
			info.setStatusid(SETTConstant.TransactionStatus.SAVE);
			lret = dao.add(info);
			
			//开始保存明细
			if(lret>0)
			{
				LiquidationDetailDao detaildao = new LiquidationDetailDao(conInternal);
				//开始付款方办事处明细
				if(info.getNPayofficeid()>0)
				{
					LiquidationdetailInfo payinfo = new LiquidationdetailInfo();
					payinfo.setLiquidationid(lret);
					payinfo.setNOfficeid(info.getNPayofficeid());
					payinfo.setNCurrencyid(info.getNCurrencyid());
					String sdebit = "";//借方
					String scredit = "";//贷方
					if(info.getNPayofficeid()==info.getNParentOfficeID())
					{//如果付款方是总部
						//借方为付款方账户
						sdebit = NameRef.getAccountNoByID(info.getNPayaccountid());
						//贷方为收款方备付金账户
						scredit = NameRef.getAccountNoByID(NameRef.getBakAccountIDByOfficeID(info.getNReceiveofficeid(), info.getNCurrencyid()));
					}
					else
					{
						//借方为付款方账户
						sdebit = NameRef.getAccountNoByID(info.getNPayaccountid());
						//贷方为付款方备付金账户
						scredit = NameRef.getAccountNoByID(NameRef.getBakAccountIDByOfficeID(info.getNPayofficeid(), info.getNCurrencyid()));
					}
					payinfo.setSDebit(sdebit);
					payinfo.setSCredit(scredit);
					payinfo.setNStatusid(Constant.RecordStatus.VALID);
					detaildao.add(payinfo);
				}
				//开始收款方办事处明细
				if(info.getNReceiveofficeid()>0)
				{
					LiquidationdetailInfo receiveinfo = new LiquidationdetailInfo();
					receiveinfo.setLiquidationid(lret);
					receiveinfo.setNOfficeid(info.getNReceiveofficeid());
					receiveinfo.setNCurrencyid(info.getNCurrencyid());
					String sdebit = "";//借方
					String scredit = "";//贷方
					if(info.getNReceiveofficeid()==info.getNParentOfficeID())
					{//如果收款方是总部
						//贷方为收款方账户
						scredit = NameRef.getAccountNoByID(info.getNReceiveaccountid());
						//借方为付款方备付金账户
						sdebit = NameRef.getAccountNoByID(NameRef.getBakAccountIDByOfficeID(info.getNPayofficeid(), info.getNCurrencyid()));
					}
					else
					{
						//贷方为收款方账户
						scredit = NameRef.getAccountNoByID(info.getNReceiveaccountid());
						//借方为收款方备付金账户
						sdebit = NameRef.getAccountNoByID(NameRef.getBakAccountIDByOfficeID(info.getNReceiveofficeid(), info.getNCurrencyid()));
					}
					receiveinfo.setSDebit(sdebit);
					receiveinfo.setSCredit(scredit);
					receiveinfo.setNStatusid(Constant.RecordStatus.VALID);
					detaildao.add(receiveinfo);
				}
				//开始总部明细，如果交易双方都不是总部，那么总部作为协调方也要保存一条明细
				if(info.getNPayofficeid()>0&&info.getNReceiveofficeid()>0
						&& info.getNPayofficeid()!=info.getNParentOfficeID()
						&& info.getNReceiveofficeid()!=info.getNParentOfficeID())
				{
					LiquidationdetailInfo zbinfo = new LiquidationdetailInfo();
					zbinfo.setLiquidationid(lret);
					zbinfo.setNOfficeid(info.getNParentOfficeID());
					zbinfo.setNCurrencyid(info.getNCurrencyid());
					String sdebit = "";//借方
					String scredit = "";//贷方
					//借方为付款方备付金账户
					sdebit = NameRef.getAccountNoByID(NameRef.getBakAccountIDByOfficeID(info.getNPayofficeid(), info.getNCurrencyid()));
					//贷方为收款方备付金账户
					scredit = NameRef.getAccountNoByID(NameRef.getBakAccountIDByOfficeID(info.getNReceiveofficeid(), info.getNCurrencyid()));
					zbinfo.setSDebit(sdebit);
					zbinfo.setSCredit(scredit);
					zbinfo.setNStatusid(Constant.RecordStatus.VALID);
					detaildao.add(zbinfo);
				}
			}
			
			if (conn == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}
		}
		catch(Exception e)
		{
			if (conn == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					eRollback.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		finally
		{
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (conn == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					eClose.printStackTrace();
				}
			}
		}
		return lret;
	}
	
	/**复核清算主表数据
	 * @param LiquidationInfo info
	 * @return
	 */
	public void Check(LiquidationInfo info,Connection conn)
	{
		Connection conInternal = null;
		try
		{
			// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
			if (conn == null)
			{
				try
				{
					conInternal = Database.getConnection();
					conInternal.setAutoCommit(false);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new IException("无法取得数据库连接");
				}
			}
			else
			{
				conInternal = conn;
			}
			
			LiquidationDao dao = new LiquidationDao(conInternal);

			//根据交易号查询清算交易ID
			long id = dao.getIDbyTransno(info.getSTransno());
			
			//开始更新主表状态
			dao.updateStatus(id, SETTConstant.TransactionStatus.CHECK);
			
			if (conn == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}
		}
		catch(Exception e)
		{
			if (conn == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					eRollback.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		finally
		{
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (conn == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					eClose.printStackTrace();
				}
			}
		}
	}
	
	/**取消复核清算主表数据
	 * @param LiquidationInfo info
	 * @return
	 */
	public void CancelCheck(LiquidationInfo info,Connection conn)
	{
		Connection conInternal = null;
		try
		{
			// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
			if (conn == null)
			{
				try
				{
					conInternal = Database.getConnection();
					conInternal.setAutoCommit(false);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new IException("无法取得数据库连接");
				}
			}
			else
			{
				conInternal = conn;
			}
			
			LiquidationDao dao = new LiquidationDao(conInternal);

			//根据交易号查询清算交易ID
			long id = dao.getIDbyTransno(info.getSTransno());
			
			//开始更新主表状态
			dao.updateStatus(id, SETTConstant.TransactionStatus.SAVE);
			
			if (conn == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}
		}
		catch(Exception e)
		{
			if (conn == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					eRollback.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		finally
		{
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (conn == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					eClose.printStackTrace();
				}
			}
		}
	}
	
	/**删除清算主表数据及明细表数据
	 * @param LiquidationInfo info
	 * @return
	 */
	public void Delete(LiquidationInfo info,Connection conn)
	{
		Connection conInternal = null;
		try
		{
			// 判断是否应该在该方法内部启动事务，如果连接参数为 NULL ，就表示该方法负责维护整个事务。
			if (conn == null)
			{
				try
				{
					conInternal = Database.getConnection();
					conInternal.setAutoCommit(false);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new IException("无法取得数据库连接");
				}
			}
			else
			{
				conInternal = conn;
			}
			
			LiquidationDao dao = new LiquidationDao(conInternal);
			LiquidationDetailDao detaildao = new LiquidationDetailDao(conInternal);

			//根据交易号查询清算交易ID
			long id = dao.getIDbyTransno(info.getSTransno());
			
			//开始删除主表
			dao.delete(id);
			
			//开始删除明细
			detaildao.deleteByliquidationid(id);
			
			if (conn == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("事务提交异常");
				}
			}
		}
		catch(Exception e)
		{
			if (conn == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					eRollback.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		finally
		{
			// 不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放，不必再次抛出关闭连接的异常。
			if (conn == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					eClose.printStackTrace();
				}
			}
		}
	}
}
