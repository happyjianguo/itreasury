/*
 * Created on 2006-9-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.bulletin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataconvert.util.Log;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BulletinDao extends ITreasuryDAO {
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this);
	  
	public BulletinDao() {
		super("bulletin");
	}
	  
	public BulletinDao(Connection conn) {
		super("bulletin", conn);
	}
	
	//新增公告信息，将info里的信息insert到表bulletin里
	/**
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long add(BulletinInfo info) throws Exception
	{
		long lret = -1;
		
		Connection con = null;
		PreparedStatement ps = null;
		java.sql.Statement st=null;
		//String sql="insert into Bulletin values ((SELECT nvl(MAX(id)+1,1) id from Bulletin),?,?,?,?,?,?,?,?)";
		String sql="insert into Bulletin values ((SELECT nvl(MAX(id)+1,1) id from Bulletin),"+
		info.getModuleID()+",'"+info.getClients()+"','"+info.getHeader()+"','"+info.getContent()+"',"+info.getStatusID()+",to_date('"+DataFormat.getDateString(info.getReleaseDate())+"','yyyy-mm-dd'),"+info.getUserID()+","+info.getOfficeId()+")";
		//String sql="insert into Bulletin values ((SELECT nvl(MAX(id)+1,1) id from Bulletin),2,3,'4','5',2,to_date('2006-9-12','yyyy-mm-dd'),2,2)";
		Log.print(sql);
		try
		{
			Log.print("111111111111111111111111111111111");
			con = Database.getConnection();
			Log.print("22222222222222222222222222222222222");
			st=con.createStatement();
			Log.print("3333333333333333333333333333333333");
			st.execute(sql);
			Log.print("44444444444444444444444444444444444");
			/*ps = con.prepareStatement(sql);
			Log.print("33333333333333333333333333333333333");
			ps.setLong(1, info.getModuleID());
			ps.setString(2, info.getClients());
			ps.setString(3, info.getHeader());
			ps.setString(4, info.getContent());
			ps.setLong(5, info.getStatusID());
			ps.setDate(6,DataFormat.getDate(info.getReleaseDate()));
			ps.setLong(7, info.getUserID());
			ps.setLong(8, info.getOfficeId());
			ps.execute(sql);
		    Log.print("44444444444444444444444444444444444444");*/
			lret=1;
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(ps);
			cleanup(con);
			throw e;

		}
		finally
		{
			cleanup(st);
			cleanup(ps);
			cleanup(con);
		}

		
		return lret;
	}
	
	//	修改公告信息，将info里的信息update到表bulletin里
	public long modify(BulletinInfo info) throws Exception
	{
		long lret = -1;

		Connection con = null;
		PreparedStatement ps = null;
		java.sql.Statement st=null;
		//String sql="insert into Bulletin values ((SELECT nvl(MAX(id)+1,1) id from Bulletin),?,?,?,?,?,?,?,?)";
	    String sql="update bulletin set clientIds='"+info.getClients()+"',header='"+info.getHeader()+"',content='"+info.getContent()+"',statusID="+info.getStatusID()+" where id="+info.getId();
		//String sql="insert into Bulletin values ((SELECT nvl(MAX(id)+1,1) id from Bulletin),2,3,'4','5',2,to_date('2006-9-12','yyyy-mm-dd'),2,2)";
		Log.print(sql);
		try
		{
			Log.print("111111111111111111111111111111111");
			con = Database.getConnection();
			Log.print("22222222222222222222222222222222222");
			st=con.createStatement();
			Log.print("3333333333333333333333333333333333");
			st.execute(sql);
			Log.print("44444444444444444444444444444444444");
			/*ps = con.prepareStatement(sql);
			Log.print("33333333333333333333333333333333333");
			ps.setLong(1, info.getModuleID());
			ps.setString(2, info.getClients());
			ps.setString(3, info.getHeader());
			ps.setString(4, info.getContent());
			ps.setLong(5, info.getStatusID());
			ps.setDate(6,DataFormat.getDate(info.getReleaseDate()));
			ps.setLong(7, info.getUserID());
			ps.setLong(8, info.getOfficeId());
			ps.execute(sql);
		    Log.print("44444444444444444444444444444444444444");*/
			lret=1;
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(ps);
			cleanup(con);
			throw e;

		}
		finally
		{
			cleanup(st);
			cleanup(ps);
			cleanup(con);
		}
		
		return lret;
	}
	
	//	查询公告信息，按info里的信息查询表bulletin
	//返回由BulletinInfo组成的集合
	public Vector findByCondition(BulletinInfo info) throws Exception
	{
		Vector vret=null;
		if(info.getModifyId()==1){
			vret=findByCondition1(info);
			
		}
		else if(info.getModifyId()==2)
		{
			vret=findByCondition2(info);
		}
		else if(info.getModifyId()==3){
			vret=findByCondition3(info);
		}
    //拼sql
		return vret;
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
	/**
	 * 此方法用于实现查询sql串的组装
	 * @param info
	 * @return
	 */
	private Vector findByCondition1(BulletinInfo info) throws Exception{
		Vector vret = new Vector();
	    //拼sql串
			//StringBuffer sql=new StringBuffer("");
			//String sql ="select b.id Id, b.releaseDate releaseDate,b.header header,u.sName name,b.STATUSID STATUSID from bulletin b ,UserInfo u where b.Userid=u.id";
			String sql=this.assembleSQL(info);
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;

			try
			{
				con = Database.getConnection();
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				/*rs.next();
				System.out.println(rs.getLong(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getLong(5));*/
				while(rs.next()){
					//System.out.println("进入获取rs");
					BulletinInfo bulletinInfo=new BulletinInfo();
					bulletinInfo.setId(rs.getLong(1));
					//bulletinInfo.setReleaseDate(rs.getString(2));
					bulletinInfo.setReleaseDate(rs.getTimestamp(2));
					bulletinInfo.setHeader(rs.getString(3));
					bulletinInfo.setUserName(rs.getString(4));
					bulletinInfo.setStatusID(rs.getLong(5));
					System.out.println(rs.getLong(1));
					System.out.println(rs.getString(2));
					System.out.println(rs.getString(3));
					System.out.println(rs.getString(4));
					System.out.println(rs.getLong(5));
					vret.add(bulletinInfo);
				
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
			return vret.size()>0?vret:null;
	}
	private Vector findByCondition2(BulletinInfo info) throws Exception{
		Vector vret = new Vector();
	    //拼sql串
			//StringBuffer sql=new StringBuffer("");
			//String sql ="select b.id Id, b.releaseDate releaseDate,b.header header,u.sName name,b.STATUSID STATUSID from bulletin b ,UserInfo u where b.Userid=u.id";
			String sql="select b.id Id, b.releaseDate releaseDate,b.header header,u.sName name,b.STATUSID STATUSID, b.content content ,b.clientids clients from bulletin b ,UserInfo u where b.Userid=u.id and b.id="+info.getId();
			Log.print(sql);
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;

			try
			{
				con = Database.getConnection();
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				/*rs.next();
				System.out.println(rs.getLong(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getLong(5));*/
				while(rs.next()){
					//System.out.println("进入获取rs");
					BulletinInfo bulletinInfo=new BulletinInfo();
					bulletinInfo.setId(rs.getLong(1));
					//bulletinInfo.setReleaseDate(rs.getString(2));
					bulletinInfo.setReleaseDate(rs.getTimestamp(2));
					bulletinInfo.setHeader(rs.getString(3));
					bulletinInfo.setUserName(rs.getString(4));
					bulletinInfo.setStatusID(rs.getLong(5));
					bulletinInfo.setContent(rs.getString(6));
					bulletinInfo.setClients(rs.getString(7));
					System.out.println(rs.getLong(1));
					//System.out.println(rs.getString(2));
					System.out.println(rs.getString(3));
					System.out.println(rs.getString(4));
					System.out.println(rs.getLong(5));
					vret.add(bulletinInfo);
				
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
			return vret.size()>0?vret:null;
	}
	
	private Vector findByCondition3(BulletinInfo info) throws Exception{
		Vector vret = new Vector();
	    //拼sql串
			//StringBuffer sql=new StringBuffer("");
			//String sql ="select b.id Id, b.releaseDate releaseDate,b.header header,u.sName name,b.STATUSID STATUSID from bulletin b ,UserInfo u where b.Userid=u.id";
			String sql="select header ,content,CLIENTIDS,OFFICEID from bulletin where (clientIDs like '"+info.getClients()+",%' or clientIDs like '%,"+info.getClients()+",%' or clientIDs like '%,"+info.getClients()+"' or clientIDs='"+info.getClients()+"' or clientIDs='all' or clientIDs='part') and statusid=1";
		    //String sql="select header ,content from bulletin";
			Log.print(sql);
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;

			try
			{
				con = Database.getConnection();
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				/*rs.next();
				System.out.println(rs.getLong(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getLong(5));*/
				while(rs.next()){
					//System.out.println("进入获取rs");
					BulletinInfo bulletinInfo=new BulletinInfo();
					bulletinInfo.setHeader(rs.getString(1));
					bulletinInfo.setContent(rs.getString(2));
					bulletinInfo.setClients(rs.getString("CLIENTIDS"));
					bulletinInfo.setOfficeId(rs.getLong("OFFICEID"));
					//System.out.println(rs.getLong(1));
					//System.out.println(rs.getString(2));
					vret.add(bulletinInfo);
				
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
			return vret.size()>0?vret:null;
	}
	
	/**
	 * modify by leiyang 20081120
	 * 网银查找公告信息新方法
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public String findByBulletinString(BulletinInfo info) throws ITreasuryDAOException{
		
		StringBuffer strBulletin = new StringBuffer();
		
	    try {
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/
		
	        try{
	        	StringBuffer strSQL = new StringBuffer();
	        	strSQL.append("select header || ';;' || content hc  from bulletin");
	        	strSQL.append(" where (clientIDs like ? || ',%'");
	        	strSQL.append(" or clientIDs like '%,' || ? || ',%'");
	        	strSQL.append(" or clientIDs like '%,' || ?");
	        	strSQL.append(" or clientIDs = ?");
	        	strSQL.append(" or clientIDs = 'all'");
	        	strSQL.append(" or clientIDs = 'part')");
	        	strSQL.append(" and OFFICEID = ?");
	        	strSQL.append(" and statusid = ?");
	        	strSQL.append(" order by releasedate desc , id desc");
	        	
			    prepareStatement(strSQL.toString());
			    transPS.setString(1, info.getClients());
			    transPS.setString(2, info.getClients());
			    transPS.setString(3, info.getClients());
			    transPS.setString(4, info.getClients());
			    transPS.setLong(5, info.getOfficeId());
			    transPS.setLong(6, Constant.RecordStatus.VALID);
			    
			    log4j.print("strSQL = \n" + strSQL.toString());
			    
			    executeQuery();
	
			    while(transRS.next()){
			    	strBulletin.append(transRS.getString("hc") + "::");
			    }
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("查询网银公告栏出错", e);
		    }

		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
	    }
	    catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	      throw new ITreasuryDAOException("查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    if(strBulletin.toString().equals("") == false){
	    	return strBulletin.substring(0,strBulletin.length()-2);
	    }
	    else {
	    	return strBulletin.toString();
	    }

	}
	
	private String assembleSQL(BulletinInfo info){
		long id = -1; 
		long moduleID = -1;//模块标识
		String clients = "";//公告客户
		String header = "";//公告标题
		String content = "";//公告内容
		long statusID = -1;//公告状态
		String releaseDate ="";//发布日期
		long userID = -1;//发布人
		String userName="";//发布人姓名
		//为了查询而设的属性
		String fromReleaseDate ="";//发布日期由
		String endReleaseDate ="";//发布日期到
		StringBuffer strSQL=new StringBuffer();
		String sql="";
		
		strSQL.append("select b.id Id, b.releaseDate releaseDate,b.header header,u.sName name,b.STATUSID STATUSID \n");
		strSQL.append("from bulletin b ,UserInfo u \n");
		strSQL.append("where b.Userid=u.id \n");
		if(info.getHeader()!=null&&info.getHeader().length()>0){
			header=info.getHeader();
			strSQL.append("and b.header='"+header+"'\n");
		}
		if(!info.getClients().equals("-1")){
			clients=info.getClients();
			strSQL.append("and ( b.clientids like'%,"+clients+",%' or b.clientids like'"+clients+",%' or b.clientids like'%,"+clients+"' or clientIDs like '"+clients+"')\n" );
		}
		if(info.getUserID()!=-1){
			userID=info.getUserID();
			strSQL.append("and u.id="+userID+"\n");
		}
		if(info.getStatusID()!=-1){
			statusID=info.getStatusID();
			strSQL.append("and b.STATUSID="+statusID+"\n");
		}
		if(info.getFromReleaseDate()!=null&&info.getFromReleaseDate().length()>0
				&&info.getEndReleaseDate()!=null&&info.getEndReleaseDate().length()>0)
		{
			fromReleaseDate=info.getFromReleaseDate();
			endReleaseDate=info.getEndReleaseDate();
			strSQL.append("and b.releaseDate between to_date('"+fromReleaseDate+"','yyyy-mm-dd') and to_date('"+endReleaseDate+"','yyyy-mm-dd') \n");
		}
		if(info.getFromReleaseDate()!=null&&info.getFromReleaseDate().length()>0
				&&(info.getEndReleaseDate()==null||info.getEndReleaseDate().trim().length()<=0))
		{
			fromReleaseDate=info.getFromReleaseDate();
			strSQL.append("and releaseDate>to_date('"+fromReleaseDate+"','yyyy-mm-dd') \n");
		}
		if((info.getFromReleaseDate()==null||info.getFromReleaseDate().trim().length()<=0)
				&&info.getEndReleaseDate()!=null&&info.getEndReleaseDate().length()>0)
		{
			endReleaseDate=info.getEndReleaseDate();
			strSQL.append("and releaseDate<to_date('"+endReleaseDate+"','yyyy-mm-dd') \n");
		}
		strSQL.append("order by b.id");
		
		sql=strSQL.toString();
		Log.print("SQL:"+sql);
		return sql;
	}
	public PageLoader findWithPage(BulletinInfo info) throws Exception
	{
		String[] sql = getSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.system.bulletin.dataentity.BulletinInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		return pageLoader;
		
	}
	//分页当中引用的得到sql语句的函数
	private String[] getSQL (BulletinInfo info)
	{
		long id = -1; 
		long moduleID = -1;//模块标识
		String clients = "";//公告客户
		String header = "";//公告标题
		String content = "";//公告内容
		long statusID = -1;//公告状态
		String releaseDate ="";//发布日期
		long userID = -1;//发布人
		String userName="";//发布人姓名
		//为了查询而设的属性
		String fromReleaseDate ="";//发布日期由
		String endReleaseDate ="";//发布日期到
		
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		
		//select
		sql[0] =" b.id Id, b.releaseDate releaseDate,b.header header,b.content,u.sName userName,b.STATUSID STATUSID, b.CLIENTIDS CLIENTIDS";
		//from
		sql[1] =" bulletin b ,UserInfo u ";
		//where
		sql[2] ="b.Userid=u.id ";
		
		strSQL.append("and b.OFFICEID='"+info.getOfficeId()+"'\n");
		strSQL.append("and b.MODULEID='"+info.getModuleID()+"'\n");
		/**********处理查询条件*************/
		if(info.getHeader()!=null&&info.getHeader().length()>0){
			header=info.getHeader();
			strSQL.append("and b.header='"+header+"'\n");
		}
		if(!info.getClients().equals("-1")){
			clients=info.getClients();
			strSQL.append("and ( b.clientids like'%,"+clients+",%' or b.clientids like'"+clients+",%' or b.clientids like'%,"+clients+"' or clientIDs like '"+clients+"' or b.clientids='all' or b.clientids='part')\n" );
		}
		if(info.getUserID()!=-1){
			userID=info.getUserID();
			strSQL.append("and u.id="+userID+"\n");
		}
		if(info.getStatusID()!=-1){
			statusID=info.getStatusID();
			strSQL.append("and b.STATUSID="+statusID+"\n");
		}
		if(info.getFromReleaseDate()!=null&&info.getFromReleaseDate().length()>0
				&&info.getEndReleaseDate()!=null&&info.getEndReleaseDate().length()>0)
		{
			fromReleaseDate=info.getFromReleaseDate();
			endReleaseDate=info.getEndReleaseDate();
			strSQL.append("and b.releaseDate between to_date('"+fromReleaseDate+"','yyyy-mm-dd') and to_date('"+endReleaseDate+"','yyyy-mm-dd') \n");
		}
		if(info.getFromReleaseDate()!=null&&info.getFromReleaseDate().length()>0
				&&(info.getEndReleaseDate()==null||info.getEndReleaseDate().trim().length()<=0))
		{
			fromReleaseDate=info.getFromReleaseDate();
			strSQL.append("and releaseDate>to_date('"+fromReleaseDate+"','yyyy-mm-dd') \n");
		}
		if((info.getFromReleaseDate()==null||info.getFromReleaseDate().trim().length()<=0)
				&&info.getEndReleaseDate()!=null&&info.getEndReleaseDate().length()>0)
		{
			endReleaseDate=info.getEndReleaseDate();
			strSQL.append("and releaseDate<to_date('"+endReleaseDate+"','yyyy-mm-dd') \n");
		}
	
		
		sql[2] = sql[2] + strSQL.toString();
		//	order
		String strDesc = "";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" \n order by b.id" + strDesc);
		sql[3] = oBuf.toString();
		
		return sql;
			
	}
	/**
	 * 
	 */
	/*public void ceshi(){
		String sql="select * from loan_creditprove";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		
			try {
				con = Database.getConnection();
				ps = con.prepareStatement(sql);
			    ps.executeQuery();
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO 自动生成 catch 块
					e1.printStackTrace();
				}
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO 自动生成 catch 块
					e1.printStackTrace();
				}
			}
		
	}*/
	public static void main(String[] args){
		//Calendar rightNow = Calendar.getInstance();
	

		// System.out.println(rightNow.get(1));
		// System.out.println(rightNow.get(2));
		// System.out.println(rightNow.get(5));
		/*Vector v=new Vector();
		BulletinInfo info=new BulletinInfo();
		BulletinDao bd=new BulletinDao();
		try {
			v=bd.findByCondition(info);
			System.out.println("查询成功");
			
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}*/
		Calendar rightNow = Calendar.getInstance();
		System.out.print(rightNow.get(1)+"-"+(rightNow.get(2)+1)+"-"+rightNow.get(5));
	}

	public String query(BulletinInfo info) {
		// TODO Auto-generated method stub
		
		String clients = "";//公告客户
		String header = "";//公告标题
		long statusID = -1;//公告状态
		long userID = -1;//发布人
		//为了查询而设的属性
		String fromReleaseDate ="";//发布日期由
		String endReleaseDate ="";//发布日期到
		
		StringBuffer strSQL = new StringBuffer();
		//select
		strSQL.append(" select b.id Id, b.releaseDate releaseDate,b.header header,b.content,u.sName userName,b.STATUSID STATUSID, b.CLIENTIDS CLIENTIDS");
		//from
		strSQL.append(" from bulletin b ,UserInfo u ");
		//where
		strSQL.append(" where b.Userid=u.id ");
		strSQL.append(" and b.OFFICEID='"+info.getOfficeId()+"'\n");
		strSQL.append(" and b.MODULEID='"+info.getModuleID()+"'\n");
		/**********处理查询条件*************/
		if(info.getHeader()!=null&&info.getHeader().length()>0){
			header=info.getHeader();
			strSQL.append(" and b.header='"+header+"'\n");
		}
		if(!info.getClients().equals("")){
			clients=info.getClients();
			strSQL.append(" and ( b.clientids like'%,"+clients+",%' or b.clientids like'"+clients+",%' or b.clientids like'%,"+clients+"' or clientIDs like '"+clients+"' or b.clientids='all' or b.clientids='part')\n" );
		}
		if(info.getUserID()!=-1){
			userID=info.getUserID();
			strSQL.append(" and u.id="+userID+"\n");
		}
		if(info.getStatusID()!=-1){
			statusID=info.getStatusID();
			strSQL.append(" and b.STATUSID="+statusID+"\n");
		}
		if(info.getFromReleaseDate()!=null&&info.getFromReleaseDate().length()>0
				&&info.getEndReleaseDate()!=null&&info.getEndReleaseDate().length()>0)
		{
			fromReleaseDate=info.getFromReleaseDate();
			endReleaseDate=info.getEndReleaseDate();
			strSQL.append(" and b.releaseDate between to_date('"+fromReleaseDate+"','yyyy-mm-dd') and to_date('"+endReleaseDate+"','yyyy-mm-dd') \n");
		}
		if(info.getFromReleaseDate()!=null&&info.getFromReleaseDate().length()>0
				&&(info.getEndReleaseDate()==null||info.getEndReleaseDate().trim().length()<=0))
		{
			fromReleaseDate=info.getFromReleaseDate();
			strSQL.append(" and releaseDate>to_date('"+fromReleaseDate+"','yyyy-mm-dd') \n");
		}
		if((info.getFromReleaseDate()==null||info.getFromReleaseDate().trim().length()<=0)
				&&info.getEndReleaseDate()!=null&&info.getEndReleaseDate().length()>0)
		{
			endReleaseDate=info.getEndReleaseDate();
			strSQL.append(" and releaseDate<to_date('"+endReleaseDate+"','yyyy-mm-dd') \n");
		}
		
		return strSQL.toString();
	}
    
}
