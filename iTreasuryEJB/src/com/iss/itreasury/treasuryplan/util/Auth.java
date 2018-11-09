/*
 * Created on 2005-1-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import com.iss.itreasury.util.Database;

/**
 * @author ycliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Auth {

	private Hashtable userInfo = new Hashtable();
	
	public void init(){
		userInfo.put("","");
		userInfo.put("","");
		userInfo.put("","");
		userInfo.put("","");
		userInfo.put("","");
		userInfo.put("","");
	}
	public void startUser(){
		Connection conn = null;
		Vector vUser = new Vector();
		String userColumn="AUTHORIZEDUSER";
		String departmentColumn = "AUTHORIZEDDEPARTMENT";
		try {
			conn= Database.getConnection();
			conn.setAutoCommit(false);
			int lineLevel = this.getMaxLevel(conn);
			for(int i = lineLevel-1 ;i>0;i--){
				vUser = this.getUserAuth(i,conn,userColumn);
				for(int j = 0 ;j<vUser.size();j++){
					this.update(conn,(AuthInfo)vUser.get(j),userColumn);
				}
				conn.commit();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
				  conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	}
	
	public void startDepartment(){
		Connection conn = null;
		Vector vUser = new Vector();
		String userColumn="AUTHORIZEDUSER";
		String departmentColumn = "AUTHORIZEDDEPARTMENT";
		try {
			conn= Database.getConnection();
			conn.setAutoCommit(false);
			int lineLevel = this.getMaxLevel(conn);
			for(int i = lineLevel-1 ;i>0;i--){
				vUser = this.getUserAuth(i,conn,departmentColumn);
				for(int j = 0 ;j<vUser.size();j++){
					this.update(conn,(AuthInfo)vUser.get(j),departmentColumn);
				}
				conn.commit();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
				  conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	}
	
	public Vector getUserAuth(long lineLevel,Connection conn,String authName) throws SQLException{
		Vector rnt  = new Vector();
		String sql ="";
		AuthInfo authInfo = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			sql = "select a.id ,b."+authName+" from TREA_TPTEMPLATE a, TREA_TPTEMPLATE b "
                  +" where a.id = b.parentlineid"
                  +" and a.linelevel= "+lineLevel +
                  " and a.statusid = 1 and b.statusid = 1"
                  +" group by a.id,b."+authName+" order by a.id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				long id = rs.getLong("id");
				String auth = rs.getString(authName);
				if(id>0 && auth!=null && !auth.equals("")){
					authInfo = new AuthInfo();
					authInfo.setId(id);
					authInfo.setAuth(auth);
					rnt.add(authInfo);
				}
			}
		}finally{
			if(rs!=null)
				rs.close();
			if(pstmt!=null)
				pstmt.close();
				
		}
		return authConnect(rnt);
	}
	
	public Vector authConnect(Vector v){
		Vector vRtn = new Vector();
		AuthInfo a1 = null;
		AuthInfo a2 = null;
		boolean isRepeat = false;
		if(v!=null && v.size()>0){
			vRtn.add(v.get(0));
			for(int i =1 ;i<v.size();i++){
				a1 = (AuthInfo)v.get(i);
				for(int j =0 ;j<vRtn.size();j++){
					a2 = (AuthInfo)vRtn.get(j);
					if(a1.getId()==a2.getId()){
						a2.setAuth(getAuth(a2.getAuth(),a1.getAuth()));
						vRtn.set(j,a2);
						isRepeat = true;
					}
				}
				if(!isRepeat){
					vRtn.add(a1);
				}else{
					isRepeat = false;
				}
			}
		}
		return vRtn;
	}
	
	public String getAuth(String oneStr,String twoStr){
		String rnt = "";
		boolean isRepeat = false;
		if(oneStr==null || oneStr.equals("")){
			if(twoStr!=null && !twoStr.equals("")){
				rnt = twoStr;
			}
		}else if(twoStr ==null || twoStr.equals("")){
			if(oneStr!=null && !oneStr.equals("")){
				rnt = oneStr;
			}
		}else{
			rnt = twoStr;
			String ones [] = oneStr.split(",");
		    String twos [] = twoStr.split(",");
		    for(int i = 0 ;i<ones.length;i++ ){
		    	for(int j = 0 ;j<twos.length;j++){
		    		if(ones[i].trim().equals(twos[j].trim())){
		    			isRepeat = true;
		    			break;
		    		}
		    	}
	    		if(!isRepeat){
	    			if(!ones[i].equals("")){
	    					rnt = rnt +","+ones[i];
	    			}
	    		}else{
	    			isRepeat = false;
	    		}
		    }
		}
		return rnt;
	}
	
	public String getAllAuth(Vector vAuth){
		String rnt = "";
		Vector vAll = new Vector();
		String tmpStr ="";
		for(int i = 0;i<vAuth.size();i++){
			tmpStr = (String)vAuth.get(i);
			if(tmpStr==null || tmpStr.length()<2){
				
			}else if(tmpStr.indexOf(",")<0){
				vAll.add(tmpStr);
			}else {
				String str [] = tmpStr.split(",");
				for(int j = 0;j<str.length;j++)
				{
					if(str!=null&&!str.equals("")&&!str.equals("<>"))
					        vAll.add(str[j]);
				}
			}
		}
		Vector lastV = this.delrepeat(vAll);
		for(int i =0;i<lastV.size();i++){
			if(i==lastV.size())
			   rnt =rnt+(String)lastV.get(i);
			else
				rnt =rnt+(String)lastV.get(i)+",";
		}
		return rnt;
	}
	
	public Vector delrepeat(Vector v){
		Vector d = new Vector();
		
		String vStr = "";
		String dStr = "";
		boolean isRepeat = false;
		if(v!=null && v.size()>0){
			d.add(v.get(0));
			for(int i = 1;i<v.size();i++){
				vStr = (String)v.get(i);
				for(int j=0 ;j<d.size();j++){
					dStr = (String)d.get(j);
					if(vStr!=null && dStr!=null &&vStr.equals(dStr))
					   {
						  isRepeat = true;
						  break;
					   }
				}
				if(!isRepeat){
					d.add(dStr);
				}else{
					isRepeat = false;
				}
					
			}
		}
		return d;
	}
	
	public int getMaxLevel(Connection conn) throws SQLException{
		int rnt = 0;
		String sql ="";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sql = "select max(LINELEVEL) maxlevel from TREA_TPTEMPLATE ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
			  rnt = rs.getInt("maxlevel");
		}finally{
			if(rs!=null)
				rs.close();
			if(pstmt!=null)
				pstmt.close();
				
		}
		return rnt;
	}
	
	public void startUserNameToID(){
		Connection conn = null;
		Vector vUser = new Vector();
		String userColumn="AUTHORIZEDUSER";
		String departmentColumn = "AUTHORIZEDDEPARTMENT";
		PreparedStatement pstmt = null;
		PreparedStatement updatestmt = null;
		ResultSet rs = null;
		try {
			conn= Database.getConnection();
			conn.setAutoCommit(false);
			String usersql = "select id ,sname from userinfo where nstatusid =1 and NOFFICEID = 1";
			String updateSql ="";
			pstmt = conn.prepareStatement(usersql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				updateSql= "update trea_tptemplate set " +
						"authorizeduser  = " +
						"REPLACE(authorizeduser," +
						"'<"+rs.getString("sname")+">'," +
								"'<"+rs.getString("id")+">')";
				System.out.println(updateSql);
				updatestmt = conn.prepareStatement(updateSql);
				//updatestmt.setString(1,rs.getString("sname"));
				//updatestmt.setString(2,rs.getString("id"));
				updatestmt.execute();
				updateSql = "";
			}
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
				  conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	}
	
	public void startDepartmentNameToID(){
		Connection conn = null;
		Vector vUser = new Vector();
		String userColumn="AUTHORIZEDUSER";
		String departmentColumn = "AUTHORIZEDDEPARTMENT";
		PreparedStatement pstmt = null;
		PreparedStatement updatestmt = null;
		ResultSet rs = null;
		try {
			conn= Database.getConnection();
			conn.setAutoCommit(false);
			String usersql = "select id,departmentname from department where statusid = 1";
			String updateSql = "";
			pstmt = conn.prepareStatement(usersql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				updateSql = "update trea_tptemplate set AUTHORIZEDDEPARTMENT = " +
				"REPLACE(AUTHORIZEDDEPARTMENT," +
				"'<"+rs.getString("departmentname")+">'," +
						"'<"+rs.getString("id")+">')";
				System.out.println(updateSql);
				updatestmt = conn.prepareStatement(updateSql);
				//updatestmt.setString(1,rs.getString("departmentname"));
				//updatestmt.setString(2,rs.getString("id"));
				updatestmt.execute();
				updateSql = "";
			}
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
				  conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	}
	
	public void last(){
		Connection conn = null;
		PreparedStatement updatestmt = null;
		
		try {
			conn= Database.getConnection();
			conn.setAutoCommit(false);
			
				String updateSql = "update trea_tptemplate set AUTHORIZEDDEPARTMENT = " +
				"REPLACE(AUTHORIZEDDEPARTMENT,',','')";
				System.out.println(updateSql);
				updatestmt = conn.prepareStatement(updateSql);
				updatestmt.execute();
				
				updateSql = "update trea_tptemplate set AUTHORIZEDUSER = " +
				"REPLACE(AUTHORIZEDUSER,',','')";
				System.out.println(updateSql);
				updatestmt = conn.prepareStatement(updateSql);
				updatestmt.execute();
			
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
				  conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	}
	
	public void update(Connection conn,AuthInfo authInfo,String authName) throws SQLException{
		//int rnt = 0;
		String sql ="";
		PreparedStatement pstmt = null;
			sql = "update TREA_TPTEMPLATE set  "+authName+"=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,authInfo.getAuth());
			pstmt.setLong(2,authInfo.getId());
			pstmt.execute();
	}
	public class AuthInfo{
		private long id;
		private String auth;
		
		
		/**
		 * @return Returns the auth.
		 */
		public String getAuth() {
			return auth;
		}
		/**
		 * @param auth The auth to set.
		 */
		public void setAuth(String auth) {
			this.auth = auth;
		}
		/**
		 * @return Returns the id.
		 */
		public long getId() {
			return id;
		}
		/**
		 * @param id The id to set.
		 */
		public void setId(long id) {
			this.id = id;
		}
	}
	public static void main(String[] args) {
		Auth auth = new Auth();
		auth.startUser();
		auth.startDepartment();
		auth.startUserNameToID();
		auth.startDepartmentNameToID();
		auth.last();
		
	}
}
