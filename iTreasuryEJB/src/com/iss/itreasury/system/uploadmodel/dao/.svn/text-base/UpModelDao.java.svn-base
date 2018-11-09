package com.iss.itreasury.system.uploadmodel.dao;

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
import com.iss.itreasury.system.uploadmodel.dataentity.UpModelInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class UpModelDao extends ITreasuryDAO {
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this);
	  
	public UpModelDao() {
		super("UpModelDao");
	}
	  
	public UpModelDao(Connection conn) {
		super("UpModelDao", conn);
	}
	
	/**
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long add(UpModelInfo info) throws Exception
	{
		long lret = -1;
		
		Connection con = null;
		PreparedStatement ps = null;
		java.sql.Statement st=null;
		String sql="insert into upModel_docinfo values ((SELECT nvl(MAX(id)+1,1) id from upModel_docinfo),"+
		info.getModuleID()+",'"+info.getClients()+"','"+info.getHeader()+"','"+info.getContent()+"',"+info.getStatusID()+",to_date('"+DataFormat.getDateString(info.getReleaseDate())+"','yyyy-mm-dd'),"+info.getUserID()+","+info.getOfficeId()+","+info.getFileId()+","+
		info.getType()+","+info.getArchivesTyepID()+","+info.getParentID()+","+info.getParentType()+")";
		Log.print(sql);
		try
		{
			con = Database.getConnection();
			st=con.createStatement();
			System.out.println("sql=====222==="+sql);
			st.execute(sql);
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
	
	public long modify(UpModelInfo info) throws Exception
	{
		long lret = -1;

		Connection con = null;
		PreparedStatement ps = null;
		java.sql.Statement st=null;
		System.out.println("info.getStatusID()====="+info.getStatusID());
	    String sql="update upmodel_docinfo set clientIds='"+info.getClients()+"',header='"+info.getHeader()+"',content='"+info.getContent()+"',statusID="+info.getStatusID()+" where id="+info.getId();
	
		Log.print(sql);
		try
		{
			con = Database.getConnection();
			st=con.createStatement();
			st.execute(sql);
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
	
	public Vector findByCondition(UpModelInfo info) throws Exception
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
	
	private Vector findByCondition1(UpModelInfo info) throws Exception{
		Vector vret = new Vector();
		String sql=this.assembleSQL(info);
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				UpModelInfo upModelInfo=new UpModelInfo();
				upModelInfo.setId(rs.getLong(1));
				upModelInfo.setReleaseDate(rs.getTimestamp(2));
				upModelInfo.setHeader(rs.getString(3));
				upModelInfo.setUserName(rs.getString(4));
				upModelInfo.setStatusID(rs.getLong(5));
				upModelInfo.setFileId(rs.getLong(6));
				vret.add(upModelInfo);
			
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
	private Vector findByCondition2(UpModelInfo info) throws Exception{
		Vector vret = new Vector();
		String sql="select b.id Id, b.releaseDate releaseDate,b.header header,u.sName name,b.STATUSID STATUSID, b.content content ,b.clientids clients,b.nfileid fileId from upmodel_docinfo b ,UserInfo u where b.Userid=u.id and b.id="+info.getId();
		Log.print(sql);
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				UpModelInfo upModelInfo=new UpModelInfo();
				upModelInfo.setId(rs.getLong(1));
				upModelInfo.setReleaseDate(rs.getTimestamp(2));
				upModelInfo.setHeader(rs.getString(3));
				upModelInfo.setUserName(rs.getString(4));
				upModelInfo.setStatusID(rs.getLong(5));
				upModelInfo.setContent(rs.getString(6));
				upModelInfo.setClients(rs.getString(7));
				upModelInfo.setFileId(rs.getLong(8));
				System.out.println(rs.getLong(1));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getLong(5));
				vret.add(upModelInfo);
			
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
	
	private Vector findByCondition3(UpModelInfo info) throws Exception{
		Vector vret = new Vector();
		String sql="select header ,content,CLIENTIDS,OFFICEID,nfileid from bulletin where (clientIDs like '"+info.getClients()+",%' or clientIDs like '%,"+info.getClients()+",%' or clientIDs like '%,"+info.getClients()+"' or clientIDs='"+info.getClients()+"' or clientIDs='all' or clientIDs='part') and statusid=1";
	   
		Log.print(sql);
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				UpModelInfo upModelInfo=new UpModelInfo();
				upModelInfo.setHeader(rs.getString(1));
				upModelInfo.setContent(rs.getString(2));
				upModelInfo.setClients(rs.getString("CLIENTIDS"));
				upModelInfo.setOfficeId(rs.getLong("OFFICEID"));
				upModelInfo.setFileId(rs.getLong("nfileid"));
				vret.add(upModelInfo);
			
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
	
	public Vector findByUpModel(UpModelInfo info) throws Exception{
		Vector vret = new Vector();
		String sql="select distinct header ,content,CLIENTIDS,OFFICEID,releasedate,nfileid,ntypeid,id from upmodel_docinfo where " +
			           "statusid="+info.getStatusID()+" and ((officeid="+info.getOfficeId()+" and (clientids='"+info.getClients()+"' or clientids='part')) or clientids='all') "+
					   " order by id";
		Log.print(sql);
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		System.out.print(sql);
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				UpModelInfo upModelInfo=new UpModelInfo();
				upModelInfo.setHeader(rs.getString(1));
				upModelInfo.setContent(rs.getString(2));
				upModelInfo.setClients(rs.getString("CLIENTIDS"));
				upModelInfo.setOfficeId(rs.getLong("OFFICEID"));
				upModelInfo.setReleaseDate(rs.getTimestamp("releasedate"));
				upModelInfo.setFileId(rs.getLong("nfileid"));
				upModelInfo.setType(rs.getLong("ntypeid"));
				upModelInfo.setId(rs.getLong("id"));
				vret.add(upModelInfo);
			
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
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
	
	
	public String findByBulletinString(UpModelInfo info) throws ITreasuryDAOException{
		
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
	        	strSQL.append("select header || ';;' || content hc  from upmodel_docinfo");
	        	strSQL.append(" where (clientIDs like ? || ',%'");
	        	strSQL.append(" or clientIDs like '%,' || ? || ',%'");
	        	strSQL.append(" or clientIDs like '%,' || ?");
	        	strSQL.append(" or clientIDs = ?");
	        	strSQL.append(" or clientIDs = 'all'");
	        	strSQL.append(" or clientIDs = 'part')");
	        	strSQL.append(" and OFFICEID = ?");
	        	strSQL.append(" and statusid = ?");
	        	strSQL.append(" order by releasedate desc");
	        	
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
	
	private String assembleSQL(UpModelInfo info){
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
		
		strSQL.append("select  b.id Id, b.releaseDate releaseDate,b.header header,u.sName name,b.STATUSID STATUSID,b.nfileid fileId \n");
		strSQL.append("from upmodel_docinfo b ,UserInfo u \n");
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
	public PageLoader findWithPage(UpModelInfo info) throws Exception
	{
		String[] sql = getSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.system.uploadmodel.dataentity.UpModelInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		return pageLoader;
		
	}
	//分页当中引用的得到sql语句的函数
	private String[] getSQL (UpModelInfo info)
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
		sql[1] =" upmodel_docinfo b ,UserInfo u ";
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
		}else{
			strSQL.append("and b.STATUSID<>-1 \n");			
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
	
	public boolean isNameRepeat(UpModelInfo info) throws Exception
	{
		boolean isRepeat = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer(); 
		sql.append("select header from upModel_docinfo where header='"+info.getHeader()+"' and statusid<>'-1' and id<>"+info.getId()+" ");
		try {
			con = Database.getConnection();
			pst=con.prepareStatement(sql.toString());
			rs=pst.executeQuery();
			if(rs.next()){
				isRepeat=true;
			}
			cleanup(rs);
			cleanup(pst);
			cleanup(con);
		} 	catch (SQLException e)
		{
			e.printStackTrace();
			cleanup(rs);
			cleanup(pst);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			cleanup(rs);
			cleanup(pst);
			cleanup(con);
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(pst);
			cleanup(con);
		}
		return isRepeat;
	}
	
	public static void main(String[] args){
	
		Calendar rightNow = Calendar.getInstance();
		System.out.print(rightNow.get(1)+"-"+(rightNow.get(2)+1)+"-"+rightNow.get(5));
	}
    
}
