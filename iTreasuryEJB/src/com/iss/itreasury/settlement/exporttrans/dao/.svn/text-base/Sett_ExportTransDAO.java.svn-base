/*
 * Created on 2008-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.exporttrans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.settlement.exporttrans.dataentity.ExportTransInfo;
import com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo;
import com.iss.itreasury.util.Database;

/**
 * @author lenovo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Sett_ExportTransDAO {
    //将信息插入表sett_exportTrans
    public long add(ExportTransInfo info) throws Exception {
        long lret = -1;
        int index = 0;
        
        Connection conn = Database.getConnection();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("insert into sett_exporttrans(MODULEID,STRANSNO,EXPORTTIMES) ");
		sqlBuffer.append(" values(?,?,?) ");
		System.out.println(sqlBuffer.toString());
		
		try {	
			pstat = conn.prepareStatement(sqlBuffer.toString());
			pstat.setLong(++index, info.getModuleID());
			pstat.setString(++index, info.getTransno());
			pstat.setLong(++index, info.getExporttimes());
			pstat.executeUpdate();
		
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
           
        return lret;
    }
    //更新信息sett_exportTrans
    public long update(ExportTransInfo info) throws Exception {
        long lret = -1;
        int index = 0;
        
        Connection conn = Database.getConnection();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("update sett_exporttrans t ");
		sqlBuffer.append(" set t.exporttimes = ? ");
		sqlBuffer.append(" where t.stransno = ? ");
		sqlBuffer.append(" and t.MODULEID = ? ");
		System.out.println(sqlBuffer.toString());
		
		try {
			
			pstat = conn.prepareStatement(sqlBuffer.toString());
			pstat.setLong(++index, info.getExporttimes());
			pstat.setString(++index, info.getTransno());
			pstat.setLong(++index, info.getModuleID());
			lret = pstat.executeUpdate();
		
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
           
        
        return lret;
    }
    
    //根据查询条件查询表sett_exportTrans
    public ExportTransInfo findByCondition(ExportTransInfo info) throws Exception {
        ExportTransInfo rinfo = null;
        
        Connection conn = Database.getConnection();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from sett_exporttrans t ");
		sqlBuffer.append(" where t.stransno = ? ");
		sqlBuffer.append(" and t.MODULEID = ? ");
		System.out.println(sqlBuffer.toString());
		
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			pstat.setString(1, info.getTransno());
			pstat.setLong(2, info.getModuleID());
			rs = pstat.executeQuery();
			while(rs.next()){
				rinfo = new ExportTransInfo();
				rinfo.setModuleID(rs.getLong("MODULEID"));
				rinfo.setTransno(rs.getString("STRANSNO"));
				rinfo.setExporttimes(rs.getLong("EXPORTTIMES"));
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
        
        return rinfo;
    }
}