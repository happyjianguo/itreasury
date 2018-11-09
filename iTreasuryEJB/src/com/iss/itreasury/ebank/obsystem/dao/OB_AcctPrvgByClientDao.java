/*
 * Created on 2006-11-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obsystem.dao;

import com.iss.itreasury.ebank.obsystem.dataentity.AcctPrvgByClientInfo;
import com.iss.itreasury.ebank.obsystem.dataentity.ParentClientInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.ebank.obsystem.dataentity.AccountInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.util.Log;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OB_AcctPrvgByClientDao {
	Vector vp =new Vector();
	int myTmp=0;
	
	//	新增账户权限信息，Collection里是由对象AcctPrvgByClientInfo组成的集合，
	//将Collection里的信息insert到表Ob_acctprvgbyclient里
	public long add(AcctPrvgByClientInfo info) throws Exception
	{
		long lret = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		sql="insert into Ob_acctprvgbyclient values (?,?)";
		try{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			ps.setLong(1, info.getClientID());
			ps.setLong(2,info.getAccountID());
			lret=ps.executeUpdate();
			System.out.print(lret);
			if(lret>0){
				Log.print("增加成功");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lret;
		
	}
	
	//	删除账户权限信息，Collection里是由对象AcctPrvgByClientInfo组成的集合，
	//根据对象AcctPrvgByClientInfo里记录的accountID删除表Ob_acctprvgbyclient里的相应记录
	//此处为了避免重复的操作需要先将不同的accountID从Collection里提取出来。
	public long del(long accountId) throws Exception
	{
		long lret = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		sql="delete from Ob_acctprvgbyclient where accountid=?";
		try{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			ps.setLong(1, accountId);
			lret=ps.executeUpdate();
			System.out.print(lret);
			if(lret>0){
				Log.print("删除成功");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lret;
	}
	
	//	查询上级单位的信息，Collection里是由对象ParentClientInfo组成的集合，
	//根据传入的clientid查询视图client里的相应上级单位
	//根据表client的字段NLEVELCODE可得出上下级单位的关系	
	public Collection getParentClientIDs(long clientid) throws Exception
	{
		getgetParentClientID(clientid);	
		return vp.size()>0?vp:null;
		}
	//递归调用，实现由下级账户查找上级账户！
	private void getgetParentClientID(long clientid) throws Exception{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		long myClientid=-1;
		long parentcorpId1=-1;
		long parentcorpId2=-1;
		String myClientName="";
		sql="SELECT id,sname ,nparentcorpid1,nparentcorpid2 FROM client  WHERE id="+clientid+"";
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				myClientid=rs.getLong("id");
				myClientName=rs.getString("sname");
				parentcorpId1=rs.getLong("nparentcorpid1");
				//parentcorpId2=rs.getLong("nparentcorpid2");
				Log.print("myClientid:"+myClientid);
				Log.print(":"+myClientName);
				if(myTmp!=0){
					ParentClientInfo p1=new ParentClientInfo();
				    p1.setClientid(clientid);
				    p1.setClientName(myClientName);
				    vp.add(p1);
				}
				 myTmp++;
			    
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			if(parentcorpId1!=0&&parentcorpId1!=-1){
				myClientid=parentcorpId1;
				getgetParentClientID(myClientid);
			}
			//if(parentcorpId2!=0&&parentcorpId2!=-1){
				//myClientid=parentcorpId2;
				//getgetParentClientID(myClientid);
			//}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
	}

	
	//	得出本单位的账户信息，Collection里是由对象AccountInfo组成的集合，
	//根据传入的clientid查询表sett_account里的相应账户信息
	public Collection getAcctIDs(long clientid,long currencyId) throws Exception
	{
		Vector v =new Vector();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		long myClientid=-1;
		long parentcorpId1=-1;
		long parentcorpId2=-1;
		String myClientName="";
		sql="SELECT id ,saccountno FROM sett_account  WHERE nclientid="+clientid+" and NSTATUSID!=0 and NCURRENCYID="+currencyId+" order by saccountno";
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				AccountInfo info=new AccountInfo();
				info.setAcctid(rs.getLong("id"));
				info.setAcctNo(rs.getString("saccountno"));
				Log.print(rs.getLong("id"));
				Log.print(rs.getString("saccountno"));
				v.add(info);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return v.size()>0?v:null;
	}
	
	//	得出账户权限信息，根据AcctPrvgByClientInfo里的clientID和accountID查询表AcctPrvgByClientInfo，
	//如果有记录存在则返回1表示有权限
	//如果没有记录存在则返回2表示没有权限
	public long getAcctPrvgs(AcctPrvgByClientInfo info) throws Exception
	{
		long l = 2;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		long myClientid=-1;
		long parentcorpId1=-1;
		long parentcorpId2=-1;
		String myClientName="";
		sql="SELECT * FROM Ob_acctprvgbyclient   WHERE clientid="+info.getClientID()+" and accountid="+info.getAccountID()+"";
		System.out.println(sql);
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				l=1;
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		System.out.print(l);
		return l;
	}
	private void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Statement st) throws SQLException
	{
		try
		{
			if (st != null)
			{
				st.close();
				st = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	public static void main(String [] args){
		OB_AcctPrvgByClientDao odao=new OB_AcctPrvgByClientDao();
		AcctPrvgByClientInfo info=new AcctPrvgByClientInfo();
		info.setClientID(3);
		info.setAccountID(3);
		try {
			odao.add(info);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
