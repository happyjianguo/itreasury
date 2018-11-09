/*
 * Created on 2006-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.matureremind.dao;

import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.settlement.matureremind.dataentity.MatureRemindInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.util.Log;




/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MatureRemindDao extends SettlementDAO
{
	public MatureRemindDao()
	{
		super();
	}
	
	/**
	 * Constructor for Sett_AccountBankDAO.
	 * @param conn
	 */
	public MatureRemindDao(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_matureRemind";
	}
	
	//查询结息提醒设置信息，查询表sett_matureRemind
	//返回由BulletinInfo组成的集合
	public Vector findAll(MatureRemindInfo info) throws Exception
	{
		Vector vret = new Vector();
		//hy
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;		
		
		try
		{
			//链接数据库
			conn = getConnection();						
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append("SELECT * FROM sett_matureRemind WHERE officeid = ? and currencyid = ? ");
			ps = conn.prepareStatement(sbSQL.toString());		
			int nIndex = 1;
			ps.setLong ( nIndex++ ,info.getOfficeId());
			ps.setLong ( nIndex++ ,info.getCurrencyid());
			rs = ps.executeQuery();	
			
			while (rs.next())
			{	
				
				MatureRemindInfo mrInfo = new MatureRemindInfo();
				mrInfo.setContent( rs.getString("content"));//得到结息内容
				mrInfo.setMaturity( rs.getDate("remindDate").toString());//得到提醒日期				
				vret.add ( mrInfo );
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
		
		return vret.size() > 0 ? vret : null ;
	}
	
	//	新增结息提醒设置信息，将info里的信息insert到表sett_matureRemind里
	public long add(MatureRemindInfo info) throws Exception
	{
		long lret = -1;
		//hy
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			conn = getConnection();
			strSQL = new StringBuffer();
			strSQL.append("INSERT INTO sett_matureRemind VALUES(?,?,?,?) ");
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			ps.setDate ( nIndex++ ,DataFormat.getDate(info.getMaturity()));
			ps.setString( nIndex++ , info.getContent());
			ps.setLong( nIndex++ ,info.getOfficeId());
			ps.setLong( nIndex++ ,info.getCurrencyid());
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
	
	//修改结息提醒设置信息，将info里的信息update到表sett_matureRemind里
	public long update(MatureRemindInfo info) throws Exception
	{
		long lret = -1;
		//hy
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			conn = getConnection();
			strSQL = new StringBuffer();
			strSQL.append(" update sett_matureRemind set content = ? where reminddate = ? and officeid = ? and currencyid = ?");
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			ps.setString( nIndex++ , info.getContent());
			ps.setDate ( nIndex++ ,DataFormat.getDate(info.getMaturity()));
			ps.setLong( nIndex++ ,info.getOfficeId());
			ps.setLong( nIndex++ ,info.getCurrencyid());
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
	
	//	删除结息提醒设置信息，根据info里的信息删除表sett_matureRemind的数据
	public long del(MatureRemindInfo info) throws Exception
	{
		long lret = -1;
		
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			conn = getConnection();
		
			strSQL = new StringBuffer();
			strSQL.append("DELETE FROM sett_matureRemind ");
			strSQL.append(" WHERE remindDate = to_date( '" + info.getMaturity() + "', 'yyyy-mm-dd')");			
			strSQL.append(" AND content = '" + info.getContent() + "'");			
			strSQL.append(" AND officeId = " + info.getOfficeId());	
			strSQL.append(" AND currencyid = " + info.getCurrencyid());
//			Log.print(strSQL.toString());  //jzw 2010-04-20 调试删除结息提醒的时候哪里报错
			System.out.println("jzw----测试结息提醒删除语句"+strSQL.toString()); //jzw 2010-04-20 调试删除结息提醒的时候哪里报错
			ps = conn.prepareStatement(strSQL.toString());
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

	//查找设置日期当中是否有传入的日期
	public Vector findDate (MatureRemindInfo info) throws Exception
	{
		Vector vret = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;		
		
		try
		{
			//链接数据库
			conn = getConnection();						
			//establish the insert sql string
			sbSQL = new StringBuffer();			
			sbSQL.append("SELECT * FROM sett_matureRemind WHERE remindDate = to_date('"+info.getMaturity()+"','yyyy-mm-dd')");
			sbSQL.append(" AND officeId = " + info.getOfficeId() );
			sbSQL.append(" AND currencyid = " + info.getCurrencyid() );
			ps = conn.prepareStatement(sbSQL.toString());				
			rs = ps.executeQuery();							
			while (rs.next())
			{	
				MatureRemindInfo mrInfo = new MatureRemindInfo();
				mrInfo.setContent( rs.getString("content"));//得到结息内容
				mrInfo.setMaturity( rs.getDate("remindDate").toString());//得到提醒日期				
				vret.add ( mrInfo );													
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
		return vret.size() > 0 ? vret : null ;
	}
}
