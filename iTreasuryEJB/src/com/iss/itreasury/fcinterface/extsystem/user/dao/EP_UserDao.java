package com.iss.itreasury.fcinterface.extsystem.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.fcinterface.extsystem.user.dataentity.EP_UserInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Constant;

public class EP_UserDao {	
	protected String tableName = null;	
	protected Connection conn = null;	
	protected ResultSet rs = null;	
	protected PreparedStatement ps = null;
	/** 是否数据库连接是自维护的（即不是容器维护的） */
	private boolean isSelfManagedConn = false;
	public EP_UserDao(){}
	public EP_UserDao(Connection con)
	{		
		conn=con;
		isSelfManagedConn = true;
	}	
	protected void initDAO() throws Exception {
		try {
			if (conn == null)
				conn = Database.getConnection();
		} catch (Exception e) {
			throw new Exception("数据库初使化异常发生");
		}
	}
	protected void finalizeDAO() throws Exception {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (ps != null) {
				ps.close();
				ps = null;
			}

			if (conn != null && !isSelfManagedConn) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			throw new Exception("数据库关闭异常发生", e);
		}
	}
	/**
	 * 多条件查询
	 * @param infoCondition
	 * @return
	 * @throws Exception
	 */
	public Collection findByCondition(EP_UserInfo infoCondition) throws Exception
	{
		Vector vectTemp = new Vector();
		EP_UserInfo userinfo=null;
		try {			
			initDAO();			
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select * from ep_user  where 1=1 ");
			if(infoCondition.getID()>0){
				buffer.append(" and id= "+infoCondition.getID());
			}
			if(infoCondition.getSName()!=null){
				buffer.append(" and sname= '"+infoCondition.getSName()+"'");
			}
			if(infoCondition.getNExtSystemID()>0){
				buffer.append(" and nextsystemid= "+infoCondition.getNExtSystemID());
			}
			if(infoCondition.getNStatus()>0){
				buffer.append(" and nstatus= "+infoCondition.getNStatus());
			}
			ps=conn.prepareStatement(buffer.toString());
			rs=ps.executeQuery();
			while(rs.next()){
				userinfo=new EP_UserInfo();
				userinfo.setID(rs.getLong("id"));
				userinfo.setSName(rs.getString("sname"));
				userinfo.setNExtSystemID(rs.getLong("nextsystemid"));
				userinfo.setNInputUserID(rs.getLong("ninputuserid"));
				userinfo.setNStatus(rs.getLong("nstatus"));	
				vectTemp.add(userinfo);
			}			
			this.finalizeDAO();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询异常");
		} finally {
			finalizeDAO();
		}
		return vectTemp.size() > 0 ? vectTemp : null;
	}
	/**
	 * 新增方法
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long add(EP_UserInfo info)throws Exception
	{
		long lReturnID=-1;	
		long maxid=-1;
		StringBuffer buffer =null;
		try {			
			initDAO();
			buffer = new StringBuffer();
			buffer.append(" select nvl(max(id),0)+1 maxid from ep_user  ");
			ps=conn.prepareStatement(buffer.toString());
			rs=ps.executeQuery();
			while(rs.next()){
				maxid=rs.getLong("maxid");
			}
			if(maxid<0){
				throw new Exception("添加用户异常");
			}
			rs.close();
			rs=null;
			ps.close();
			ps=null;			
			buffer = new StringBuffer();
			buffer.append(" insert into ep_user(id,sname,nextsystemid,ninputuserid,dtinput,nstatus) ");
			buffer.append(" values(?,?,?,?,sysdate,?)");			
			ps=conn.prepareStatement(buffer.toString());
			int index=1;
			ps.setLong(index++, maxid);
			ps.setString(index++, info.getSName());
			ps.setLong(index++, info.getNExtSystemID());
			ps.setLong(index++, info.getNInputUserID());
			ps.setLong(index++, Constant.RecordStatus.VALID);
			int num=ps.executeUpdate();
			if(num>0){
				lReturnID=maxid;
			}					
			this.finalizeDAO();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("添加用户异常");
		} finally {
			finalizeDAO();
		}		
		return lReturnID;
	}
	


}
