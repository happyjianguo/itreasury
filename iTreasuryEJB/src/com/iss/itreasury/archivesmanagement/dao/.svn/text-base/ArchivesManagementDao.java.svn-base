package com.iss.itreasury.archivesmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.archivesmanagement.dataentity.ArchivesManagementInfo;
import com.iss.itreasury.util.Database;

public class ArchivesManagementDao {
	public ArchivesManagementDao(){
		
	}
	/**
	 * @param args 
	 */
//取所有分类
	 public Collection getAll() throws Exception{
		 Collection rc = new ArrayList();
	        
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select * from archivestypemanagement where statusid !=0");
			 
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				while(rs.next()){
					ArchivesManagementInfo archivesManagementInfo=new ArchivesManagementInfo();
					archivesManagementInfo.setId(rs.getLong("id"));
					archivesManagementInfo.setName(rs.getString("name"));
					archivesManagementInfo.setOfficeID(rs.getLong("officeid"));
					archivesManagementInfo.setUserID(rs.getLong("userid"));
					archivesManagementInfo.setRemark(rs.getString("remark"));
					archivesManagementInfo.setStatusID(rs.getLong("statusid"));
					rc.add(archivesManagementInfo);
				}
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

        return rc;
			
	 }
	 public Collection getAllByOfficeID(long officeID) throws Exception{
		 Collection rc = new ArrayList();
	        
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ; 
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select * from archivestypemanagement where (statusid !=0 and officeid="+officeID+") or remark='系统默认'");
			 
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				while(rs.next()){
					ArchivesManagementInfo archivesManagementInfo=new ArchivesManagementInfo();
					archivesManagementInfo.setId(rs.getLong("id"));
					archivesManagementInfo.setName(rs.getString("name"));
					archivesManagementInfo.setOfficeID(rs.getLong("officeid"));
					archivesManagementInfo.setUserID(rs.getLong("userid"));
					archivesManagementInfo.setRemark(rs.getString("remark"));
					archivesManagementInfo.setStatusID(rs.getLong("statusid"));
					rc.add(archivesManagementInfo);
				}
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

        return rc;
			
	 }
	 //取默认分类
	 public Collection getAllByDefault() throws Exception{
		 Collection rc = new ArrayList();
	        
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select * from archivestypemanagement where statusid=2");
			
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				while(rs.next()){
					ArchivesManagementInfo archivesManagementInfo=new ArchivesManagementInfo();
					archivesManagementInfo.setId(rs.getLong("id"));
					archivesManagementInfo.setName(rs.getString("name"));
					archivesManagementInfo.setOfficeID(rs.getLong("officeid"));
					archivesManagementInfo.setUserID(rs.getLong("userid"));
					archivesManagementInfo.setRemark(rs.getString("remark"));
					archivesManagementInfo.setStatusID(rs.getLong("statusid"));
					rc.add(archivesManagementInfo);
				}
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

        return rc;
			
	 }
	 
	 public long getIdByName(long officeID,String name)throws Exception{
	        long retrunValue=-1;
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select * from archivestypemanagement where statusid in(1,2) and officeid="+officeID);
			sqlBuffer.append("and name='"+name+"'");
			
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				while(rs.next()){
					retrunValue=1;
				}
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

     return retrunValue;
	 }
	 //取自定义分类
	 public Collection getAllBySelfManage(long officeID) throws Exception{
		 Collection rc = new ArrayList();
	        
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select * from archivestypemanagement where statusid =1 and officeid="+officeID);
			
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				while(rs.next()){
					ArchivesManagementInfo archivesManagementInfo=new ArchivesManagementInfo();
					archivesManagementInfo.setId(rs.getLong("id"));
					archivesManagementInfo.setName(rs.getString("name"));
					archivesManagementInfo.setOfficeID(rs.getLong("officeid"));
					archivesManagementInfo.setUserID(rs.getLong("userid"));
					archivesManagementInfo.setRemark(rs.getString("remark"));
					archivesManagementInfo.setStatusID(rs.getLong("statusid"));
					rc.add(archivesManagementInfo);
				}
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

        return rc;
			
	 }
	 public String  getNameByID(long ID) throws Exception{
	        String returnName="";
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ; 
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select * from archivestypemanagement where id="+ID );
			
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				while(rs.next()){
					returnName=rs.getString("name");
				}
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

        return returnName;
			
	 }
	 public void saveTypeSet(String name,String remark,long officeID,long userID)throws Exception{

	        
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("insert into archivestypemanagement(id,name,remark,officeid,userid,statusid) values("); 
			sqlBuffer.append("(select nvl(max(ID)+1,1) ID from archivestypemanagement),"); 
			sqlBuffer.append("'"+name+"','"+remark+"',"+officeID+","+userID+","+1+")");
			 
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}
 
			
	 
	 }
	 public void updateTypeSet(long ID ,String name,String remark,long officeID,long userID)throws Exception{

	        
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
		
			sqlBuffer.append("update archivestypemanagement set name ='"+name+"',");
			sqlBuffer.append("remark ='"+remark+"',");
			sqlBuffer.append("officeID ="+officeID+",");
			sqlBuffer.append("userID ="+userID+" where id="+ID);
			
			 
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

			
	 
	 }
	 public void deleteByID(long ID)throws Exception{

	        
			Connection conn = Database.getConnection();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("update  archivestypemanagement set statusid=0 where id="+ID); 
			
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
			} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

			
	 
	 }
}
