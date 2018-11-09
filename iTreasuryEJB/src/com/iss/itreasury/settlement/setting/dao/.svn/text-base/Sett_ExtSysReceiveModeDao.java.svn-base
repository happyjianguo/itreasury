package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.setting.dataentity.ExtSysReceiveModeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * 外部系统指令接收方式
 * @author xiangzhou
 *	2011-03-23
 */
public class Sett_ExtSysReceiveModeDao extends ITreasuryDAO{
	
	public Sett_ExtSysReceiveModeDao()
	{
		super("EP_EXTSYSRECEIVEMODE");
	}
	
	public Collection getExtSysReceiveMode() throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v =  new Vector();
		StringBuffer strSQL = new StringBuffer();
		ExtSysReceiveModeInfo info = null;
		
		try 
		{
			conn = Database.getConnection();
			
			strSQL.append(" select ");
			strSQL.append(" e.id ExtSystemID, e.scode extSystemCode, e.sname extSystemName, nvl(m.receivemode,"+SETTConstant.ExtSysReceiveMode.UNAUTO+") receivemode, ReceiveUser, u.sname username ");
			strSQL.append(" from ep_ExtSystem e, (select * from EP_EXTSYSRECEIVEMODE where status = 1) m, userinfo u ");
			strSQL.append(" where m.extsystemid(+) = e.id and u.id(+) = m.receiveuser and e.lstatus = "+Constant.RecordStatus.VALID);
			strSQL.append(" order by e.scode");
			
			System.out.println("strSQL-->"+strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while(rs!=null&&rs.next()){
				info = new ExtSysReceiveModeInfo();
				info.setExtSystemID(rs.getLong("ExtSystemID"));
				info.setExtSystemCode(rs.getString("extSystemCode"));
				info.setExtSystemName(rs.getString("extSystemName"));
				info.setReceiveMode(rs.getLong("ReceiveMode"));
				info.setReceiveUser(rs.getLong("ReceiveUser"));
				info.setReceiveUserName(rs.getString("username"));
				v.add(info);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return v.size()>0?v:null;
	}
	
	public void setExtSysReceiveMode(ExtSysReceiveModeInfo info) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = new StringBuffer();
		
		try 
		{
			conn = Database.getConnection();
			strSQL.append(" update ");
			strSQL.append(" EP_EXTSYSRECEIVEMODE t ");
			strSQL.append(" set t.RECEIVEMODE = "+info.getReceiveMode());
			strSQL.append(" ,t.RECEIVEUSER = "+info.getReceiveUser());
			strSQL.append(" where t.extsystemid ="+info.getExtSystemID());
			System.out.println("strSQL-->"+strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			int i = ps.executeUpdate();
			if(i==0) addExtSysReceiveMode(info);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
	}
	
	public void addExtSysReceiveMode(ExtSysReceiveModeInfo info) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = new StringBuffer();
		
		try 
		{
			conn = Database.getConnection();
			strSQL.append(" INSERT INTO");
			strSQL.append(" EP_EXTSYSRECEIVEMODE");
			strSQL.append(" (ID, EXTSYSTEMID, RECEIVEMODE, ReceiveUser, STATUS)");
			strSQL.append(" values(");
			strSQL.append(" (select nvl(max(id)+1,1) from EP_EXTSYSRECEIVEMODE),"+info.getExtSystemID()+","+info.getReceiveMode());
			strSQL.append(" ,"+info.getReceiveUser());
			strSQL.append(" ,"+Constant.RecordStatus.VALID+")");
			System.out.println("strSQL-->"+strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
	}
}
