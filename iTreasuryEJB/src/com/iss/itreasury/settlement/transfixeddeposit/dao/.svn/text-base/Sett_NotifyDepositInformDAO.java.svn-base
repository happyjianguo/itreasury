/*
 * Created on 2006-10-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.settlement.util.*;

/**
 * @author caryzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sett_NotifyDepositInformDAO extends SettlementDAO
{
	public Sett_NotifyDepositInformDAO()
	{
		super();
	}
	
	/**
	 * Constructor for Sett_AccountBankDAO.
	 * @param conn
	 */
	public Sett_NotifyDepositInformDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_notifyDepositInform";
	}
	//得到通知指令的状态
	public long getStatus (long id) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			conn = Database.getConnection();
			strSQL = new StringBuffer();
		    strSQL.append(" select t.statusid status from sett_notifyDepositInform t where t.id = ?");
		    ps = conn.prepareStatement(strSQL.toString());	
			int nIndex = 1;
			ps.setLong( nIndex++ ,id);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				lret = rs.getLong("status");
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				throw exp;
			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);		
			}			
			//System.out.println("**********hydao****************************id="+id);
			//System.out.println("**********hydao****************************lret="+lret);
		return lret;
	}

	//	新增通知信息，将info里的信息insert到表sett_notifyDepositInform里
	public long add(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if(info!=null)
			{
				conn = getConnection();
				strSQL = new StringBuffer();
				strSQL.append("INSERT INTO sett_notifyDepositInform(ID,MODULEID,OFFICEID,DEPOSITNO,NOTIFYDATE,AMOUNT,STATUSID,USERID,STRANSNO) VALUES((SELECT nvl(MAX(id)+1,1) ID FROM sett_notifyDepositInform ),?,?,?,?,?,?,?,?) ");
				ps = conn.prepareStatement(strSQL.toString());
				int nIndex = 1;
				ps.setLong( nIndex++ ,info.getModuleID());
				ps.setLong(nIndex++ , info.getOfficeID());
				ps.setString(nIndex++ , info.getDepositNo());
				ps.setDate(nIndex++ , DataFormat.getDate(info.getNotifyDate()));
				ps.setDouble(nIndex++ , info.getAmount());
				ps.setLong(nIndex++ , info.getStatusID());
				ps.setLong(nIndex++ , info.getUserID());
				ps.setString(nIndex++,info.getStransno());
				
				lret = ps.executeUpdate();
	
				cleanup(ps);
				cleanup(conn);
			}
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}
	/**
	 * 插入交易id
	 */
	public long insertTransID (long transId ,long id) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		try
		{
			conn = Database.getConnection();
			
			strSQL = new StringBuffer();
			strSQL.append(" UPDATE sett_notifyDepositInform ");
			strSQL.append(" set transId = ? ");
			strSQL.append(" where id = ? ");
			
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			
			ps.setLong(nIndex++ , transId);
			ps.setLong(nIndex++ , id);
			
			lret = ps.executeUpdate();				
			cleanup(ps);
			cleanup(conn);

		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}	
	
	//修改通知指令 交易号，
	public long insertTransNo (String stransno,long id) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		try
		{
			conn = Database.getConnection();
			
			strSQL = new StringBuffer();
			strSQL.append(" UPDATE sett_notifyDepositInform ");
			strSQL.append(" set stransno = ? ");
			strSQL.append(" where id = ? ");
			
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			
			ps.setString(nIndex++ , stransno);
			ps.setLong(nIndex++ , id);
			
			lret = ps.executeUpdate();				
			cleanup(ps);
			cleanup(conn);

		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}
	/**
	 * 通过交易ｉｄ查找记录，Added by zwsun, 2007/7/17
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public NotifyDepositInformInfo getInfoByTransId(long transId) throws Exception
	{
		NotifyDepositInformInfo info = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT ");
			strSQL.append(" t.id,");//id
			strSQL.append(" to_char(t.notifydate , 'yyyy-mm-dd' ) notifyDate , ");//通知日期
			strSQL.append(" to_char(t.notifyDate+(u.nnoticeday-10000),'yyyy-mm-dd') strTakeDate "); //取款日期
			
			strSQL.append(" FROM sett_notifyDepositInform t ,");
			strSQL.append(" sett_transopenfixeddeposit u  ");
			
			strSQL.append(" WHERE u.sdepositno = t.depositno ");
			strSQL.append(" AND t.transId = ? ");						
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,transId );
			System.out.print("sql="+strSQL);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				info = new NotifyDepositInformInfo();
				info.setID(rs.getLong("id"));
				info.setNotifyDate(rs.getString("notifyDate"));
				info.setStrTakeDate(rs.getString("strTakeDate"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return info == null ? new NotifyDepositInformInfo(): info;
	}	
	
	/**
	 * 通过交易号查找记录，Added by zwsun, 2007/7/17
	 * @param stransno
	 * @return
	 * @throws Exception
	 */
	public NotifyDepositInformInfo getInfoByTransNo (String stransno) throws Exception
	{
		NotifyDepositInformInfo info = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT ");
			strSQL.append(" to_char(t.notifydate , 'yyyy-mm-dd' ) notifyDate , ");//通知日期
			strSQL.append(" to_char(t.notifyDate+(u.nnoticeday-10000),'yyyy-mm-dd') strTakeDate "); //取款日期
			
			strSQL.append(" FROM sett_notifyDepositInform t ,");
			strSQL.append(" sett_transopenfixeddeposit u  ");
			
			strSQL.append(" WHERE u.sdepositno = t.depositno ");
			strSQL.append(" AND t.stransno = ? ");						
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setString( nIndex++ ,stransno );
			System.out.print("sql="+strSQL);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				info = new NotifyDepositInformInfo();
				info.setNotifyDate(rs.getString("notifyDate"));
				info.setStrTakeDate(rs.getString("strTakeDate"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return info == null ? new NotifyDepositInformInfo(): info;
	}
	
	//修改通知信息，将info里的信息update到表sett_notifyDepositInform里
	public long modify(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if(info!=null)
			{
				
				conn = Database.getConnection();
			
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE sett_notifyDepositInform ");
				
				if (info.getStatusID() == 0)//撤销的状态
				{
					strSQL.append(" SET statusid = ? ");
					strSQL.append(" WHERE id = "+info.getID());
					
					ps = conn.prepareStatement(strSQL.toString());
					int nIndex = 1;
					
					ps.setLong(nIndex++ , info.getStatusID());
				}
				if (info.getStatusID() == 1)//修改通知日期和取款金额
				{
					if(info.getIsDele() ==1)//如果是通知支取时候删除,此时将通知指令的已使用改为保存
					{
						strSQL.append(" SET statusid = ? ");
						strSQL.append(" WHERE depositno = ?");
						strSQL.append(" AND stransno = ?");
						
						ps = conn.prepareStatement(strSQL.toString());
						int nIndex = 1;
						
						ps.setLong(nIndex++ , SETTConstant.NotifyInformStatus.CANCEL);
						ps.setString(nIndex++ , info.getDepositNo());
						ps.setString(nIndex++ , info.getStransno());
					}
					else
					{
						strSQL.append(" SET notifydate = to_date( ? ,'yyyy-mm-dd') ,");
						strSQL.append(" amount = ? ");
						strSQL.append(" WHERE id = ?");
						strSQL.append(" AND depositno = ?");
						
						ps = conn.prepareStatement(strSQL.toString());
						int nIndex = 1;
						
						ps.setString( nIndex++ , info.getNotifyDate());
						ps.setDouble( nIndex++ , info.getAmount());
						ps.setLong( nIndex++ , info.getID());
						ps.setString( nIndex++ , info.getDepositNo());	
					}
 				}	
				if (info.getStatusID() == 2 )//通知支取，将状态写为已使用
				{
					strSQL.append(" SET statusid = ? ");
					strSQL.append(" WHERE depositno = ?");
					strSQL.append(" AND id = ?");
					
					ps = conn.prepareStatement(strSQL.toString());
					int nIndex = 1;
					
					ps.setLong(nIndex++ , SETTConstant.NotifyInformStatus.USED);
					ps.setString(nIndex++ , info.getDepositNo());
					ps.setLong(nIndex++ , info.getID());
				}
					
				
				lret = ps.executeUpdate();				
				cleanup(ps);
				cleanup(conn);
				
			}
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}
	
	//查询通知信息，按info里的信息查询表sett_notifyDepositInform
	//info里只需传入模块标识和办事处标识
	//返回由NotifyDepositInformInfo组成的集合
	public Vector findByCondition(NotifyDepositInformInfo info) throws Exception
	{
		Vector vret = null;
		
		return vret;
	}
	
	//	查询通知信息，按id查询表sett_notifyDepositInform
	//返回NotifyDepositInformInfo
	public NotifyDepositInformInfo findByID(long id) throws Exception
	{
		NotifyDepositInformInfo info = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT  c.scode clientCode ,");	//客户编号		
			strSQL.append(" c.sname clientName, " );//客户名称	
			strSQL.append(" a.saccountno  saccountno,");//账户编号
			strSQL.append(" u.mamount mAmount ,");//本金
			strSQL.append(" (b.mBalance-b.mUncheckPaymentAmount)  balance,");//余额
			strSQL.append(" to_char(b.AF_dtStart,'yyyy-mm-dd') startdate,");//起始日
			strSQL.append(" t.depositno DepositNo,");//单据号
			strSQL.append(" t.amount amount, ");//支取金额
			strSQL.append(" to_char(t.notifydate , 'yyyy-mm-dd' ) notifyDate , ");//通知日期
			strSQL.append(" t.id id,"); //id值
			//Added by zwsun, 2007/7/4
			strSQL.append(" t.moduleid moduleId");//模块id
			
			strSQL.append(" FROM sett_notifyDepositInform t ,");
			strSQL.append(" sett_transopenfixeddeposit u , ");
			strSQL.append(" client c , ");
			strSQL.append(" sett_account a ,");
			strSQL.append(" sett_SubAccount b ");
			
			strSQL.append(" WHERE u.sdepositno = t.depositno ");
			strSQL.append(" AND c.id = u.nclientid ");
			strSQL.append(" AND a.id = u.naccountid ");
			strSQL.append(" AND b.nAccountID = a.ID ");
			strSQL.append(" AND t.depositno = b.af_sdepositno ");
			strSQL.append(" AND b.nstatusid>0 ");
			strSQL.append(" AND t.id = ? ");						
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,id );
			
			System.out.print("id="+id);
			System.out.print("sql="+strSQL);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				info = new NotifyDepositInformInfo();
				
				info.setClientCode(rs.getString("clientCode"));
				info.setClientName(rs.getString("clientName"));
				info.setSaccountno(rs.getString("saccountno"));
				info.setmAmount(rs.getDouble("mAmount"));
				info.setBalance(rs.getDouble("balance"));
				info.setStartdate(rs.getString("startdate"));
				info.setDepositNo(rs.getString("DepositNo"));
				info.setAmount(rs.getDouble("amount"));
				info.setNotifyDate(rs.getString("notifyDate"));
				info.setID(rs.getLong("id"));
				//Added by zwsun, 2007/7/4
				info.setModuleID(rs.getLong("moduleId"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return info;
	}
	
	//  查询，用分页的方式
	public PageLoader findWithPage(NotifyDepositInformInfo info) throws Exception
	{
		String[] sql = getSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		return pageLoader;
		
	}
	//分页当中引用的得到sql语句的函数
	private String[] getSQL (NotifyDepositInformInfo info)
	{
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		long  moduleID = info.getModuleID();
		
		//select
		sql[0] =" a.saccountno  saccountno,  c.sname clientName ,  t.depositno DepositNo ,u.mamount mAmount ,t.amount amount ,t.moduleid moduleID, t.notifydate notifyDate1 , t.id ID , t.statusid statusID , t.userid userID";
		//from
		sql[1] =" sett_notifyDepositInform t ,sett_transopenfixeddeposit u, sett_account a , client c ";
	   if (moduleID== Constant.ModuleType.EBANK)
        {
        	sql[1] = sql[1]+ " , ob_user us" ;
        }
		
		//where
		sql[2] =" t.statusid >0 ";
		strSQL.append(" and t.depositno  = u.sdepositno ");
        strSQL.append(" and a.id = u.naccountid ");
        strSQL.append(" and  c.id = u.nclientid");
    
        if (moduleID== Constant.ModuleType.EBANK)
        {
        	strSQL.append(" and t.moduleid = " + Constant.ModuleType.EBANK);
        	strSQL.append(" and us.id = t.userid");
        	strSQL.append(" and t.userid = "+info.getUserID());
        }
		if (moduleID == Constant.ModuleType.SETTLEMENT )
		{
//modified by zwsun, 2007/6/27, 			
//			strSQL.append(" and t.moduleid = " + Constant.ModuleType.SETTLEMENT);
			strSQL.append(" and u.nofficeid = " + info.getOfficeID());//区分办事处
			strSQL.append(" and u.ncurrencyid = " + info.getCurrencyID());//区分币种
				
		}
		sql[2] = sql[2] + strSQL.toString();
		//	order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" \n order by t.notifydate" + strDesc);
		sql[3] = oBuf.toString();
		
		return sql;
			
	}
}
