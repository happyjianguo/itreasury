package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANBillInfo;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANDroitScopeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOANDroitScopeDAO{

    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	
	public long doInsert(LOANDroitScopeInfo loanDroitscopeInfo)throws Exception
	{
		int result = 0;
		long maxID = -1;
		Connection transConn = null;
		PreparedStatement transPS = null;
		try{
				
		       transConn = Database.getConnection();

		}catch(Exception eConn){
				
				eConn.printStackTrace();
				throw new IException("LOANDroitScopeDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			maxID = this.getMaxID();
			transPS = transConn.prepareStatement("INSERT INTO LOAN_DROITSCOPE(ID,GAGEID,ZNUMBER,HNUMBER,AREA,STATUS)VALUES(?,?,?,?,?,?)");
			transPS.setLong(1,maxID);
			transPS.setLong(2,loanDroitscopeInfo.getGageID());
			transPS.setString(3,loanDroitscopeInfo.getZnumber());
			transPS.setString(4,loanDroitscopeInfo.getHnumber());
			transPS.setString(5,loanDroitscopeInfo.getArea());
			transPS.setLong(6,loanDroitscopeInfo.getStatus());
			result = transPS.executeUpdate();
		}catch(Exception e){
			log.error("增加权利范围时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		return (result>0?maxID:-1);
	}
	
	public long doUpdate(LOANDroitScopeInfo loanDroitscopeInfo)throws Exception
	{
		int result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		try{
				
		       transConn = Database.getConnection();

		}catch(Exception eConn){
				
				eConn.printStackTrace();
				throw new IException("LOANDroitScopeDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			transPS = transConn.prepareStatement("UPDATE LOAN_DROITSCOPE SET ZNUMBER = ?,HNUMBER = ?,AREA = ? WHERE ID = ?");
			transPS.setString(1,loanDroitscopeInfo.getZnumber());
			transPS.setString(2,loanDroitscopeInfo.getHnumber());
			transPS.setString(3,loanDroitscopeInfo.getArea());
			transPS.setLong(4,loanDroitscopeInfo.getID());
			result = transPS.executeUpdate();
		}catch(Exception e){
			log.error("更新权利范围时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		return (result>0?loanDroitscopeInfo.getID():-1);
	}
	public long updateStatus(long ID,long lStatus)throws Exception
	{
		int result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		try{
			transConn = Database.getConnection();
		}catch(Exception eConn){
			throw new IException(eConn.getMessage());
		}
		try{
			
			transPS = transConn.prepareStatement("UPDATE LOAN_DROITSCOPE SET STATUS = ? WHERE ID = ?");
			transPS.setLong(1,lStatus);
			transPS.setLong(2,ID);
			result = transPS.executeUpdate();
			
		}catch(Exception e1){
			
			log.error("更新房屋他项权益-权利范围状态时出错："+e1.getMessage());
			e1.printStackTrace();
			throw new IException("Gen_E001",e1);
			
		}finally{
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		return (result>0?ID:-1);
	}
	
	public List getDroitscopeByList(long gageID)throws Exception
	{
		List droitScopeList = new ArrayList();
		Connection transConn = null;
		PreparedStatement transPS = null;
		ResultSet transRS = null;
	    StringBuffer sbSQL = new StringBuffer();
	    try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("LOANDroitScopeDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			sbSQL.append("SELECT ID,GAGEID,ZNUMBER,HNUMBER,AREA,STATUS FROM LOAN_DROITSCOPE WHERE GAGEID = ?");
			sbSQL.append(" AND STATUS != 0" );
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,gageID);
			transRS = transPS.executeQuery();
			while(transRS != null && transRS.next())
			{
				LOANDroitScopeInfo loanDroitscopeInfo = new LOANDroitScopeInfo();
				loanDroitscopeInfo.setID(transRS.getLong("ID"));
				loanDroitscopeInfo.setGageID(transRS.getLong("GAGEID"));
				loanDroitscopeInfo.setZnumber(transRS.getString("ZNUMBER"));
				loanDroitscopeInfo.setHnumber(transRS.getString("HNUMBER"));
				loanDroitscopeInfo.setArea(transRS.getString("AREA"));
				loanDroitscopeInfo.setStatus(transRS.getLong("STATUS"));
				droitScopeList.add(loanDroitscopeInfo);
			}
			log.info("根据房屋他项权益ID查找到"+droitScopeList.size()+"条权利范围信息："+sbSQL.toString());
		}catch(Exception e){
			log.error("根据房屋他项权益ID查找权利范围时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			this.cleanup(transRS);
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		return droitScopeList;
	}
	private long getMaxID() throws Exception 
	{
		long id = -1;
		Connection localConn = null;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_DROITSCOPE");
		try { // 内部维护RS和PS，否则将会产生冲突,但Connection使用同一个
			localConn = Database.getConnection();
			localPS = localConn.prepareStatement(sb.toString());
			localRS = localPS.executeQuery();
			if (localRS.next()) {
				id = localRS.getLong("ID");
			}
		} catch (Exception e) {
			throw new IException("数据库获取担保品ID产生异常",e);
		}finally{
			this.cleanup(localRS);
			this.cleanup(localPS);
			this.cleanup(localConn);
		}
		return id;
	}
	private  void cleanup(ResultSet rs) throws SQLException
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
}
