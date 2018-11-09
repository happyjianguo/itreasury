package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.util.Database;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANCapitalRatingGeneralInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOANCapitalRatingGeneralDAO{

	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	public long insert(LOANCapitalRatingGeneralInfo loanCapitalRatingGeneralInfo,Connection transConn)throws Exception
	{
		 long maxID = -1;
		 StringBuffer sbSQL = new StringBuffer();
		 PreparedStatement transPS = null;
		 int result = 0;
		 try{
			 
			 if(transConn == null){
				transConn = Database.getConnection();
			 }
			 
		 }catch(Exception eConn){
			 throw new IException("保存担保品(应收账款)信息,创建数据库链接时出错："+eConn.getMessage());
		 }
		 try{
				maxID = this.getMaxID();
				sbSQL.append(" INSERT INTO LOAN_CAPITALRATINGGENERAL(ID,GAGEID,EQUIPMENTTYPE,PROCESVERBALCODE,PERIODOFVALIDITY,BENCHMARKDAY,MATURITY,CHECKINMACHINE,");
				sbSQL.append(" CHECKINDATE,OFFICEID,CURRENCYID,INPUTUSERID,INPUTDATE,STATUS)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				transPS = transConn.prepareStatement(sbSQL.toString());
	            transPS.setLong(1,maxID);
	            transPS.setLong(2,loanCapitalRatingGeneralInfo.getGageid());
	            transPS.setLong(3,loanCapitalRatingGeneralInfo.getEquipmenttype());
	            transPS.setString(4,loanCapitalRatingGeneralInfo.getProcesverbalcode());
	            transPS.setLong(5,loanCapitalRatingGeneralInfo.getPeriodofvalidity());
	            transPS.setTimestamp(6,loanCapitalRatingGeneralInfo.getBenchmarkday());
	            transPS.setTimestamp(7,loanCapitalRatingGeneralInfo.getMaturity());
	            transPS.setString(8,loanCapitalRatingGeneralInfo.getCheckinmachine());
	            transPS.setTimestamp(9,loanCapitalRatingGeneralInfo.getCheckindate());
	            transPS.setLong(10,loanCapitalRatingGeneralInfo.getOfficeid());
	            transPS.setLong(11,loanCapitalRatingGeneralInfo.getCurrencyid());
	            transPS.setLong(12,loanCapitalRatingGeneralInfo.getInputuserid());
	            transPS.setTimestamp(13,loanCapitalRatingGeneralInfo.getInputdate());
	            transPS.setLong(14,loanCapitalRatingGeneralInfo.getStatus());
                result = transPS.executeUpdate();
                log.info("资产评估登记信息"+(result>0?"成功":"失败"));
           
		 }catch(Exception e){
			 
			 log.error("资产评估登记信息时出错："+e.getMessage());
			 e.printStackTrace();
			 throw new IException("Gen_E001",e);
			 
		 }finally{
			 
			 cleanup(transPS);
		 }
		return (result>0?maxID:-1);
	}
	
	
	public long doUpdate(LOANCapitalRatingGeneralInfo loanCapitalratinggeneralInfo,Connection transConn)throws Exception
	{
		 int result = 0;
		 StringBuffer sbSQL = new StringBuffer();
		 PreparedStatement transPS = null;
		 try{
			sbSQL.append(" UPDATE LOAN_CAPITALRATINGGENERAL SET EQUIPMENTTYPE = ?,PROCESVERBALCODE = ?,PERIODOFVALIDITY = ?,BENCHMARKDAY = ?,MATURITY = ?,CHECKINMACHINE = ?,");
			sbSQL.append(" CHECKINDATE = ? WHERE ID = ?");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,loanCapitalratinggeneralInfo.getEquipmenttype());
			transPS.setString(2,loanCapitalratinggeneralInfo.getProcesverbalcode());
			transPS.setLong(3,loanCapitalratinggeneralInfo.getPeriodofvalidity());
			transPS.setTimestamp(4,loanCapitalratinggeneralInfo.getBenchmarkday());
			transPS.setTimestamp(5,loanCapitalratinggeneralInfo.getMaturity());
			transPS.setString(6,loanCapitalratinggeneralInfo.getCheckinmachine());
			transPS.setTimestamp(7,loanCapitalratinggeneralInfo.getCheckindate());
			transPS.setLong(8,loanCapitalratinggeneralInfo.getId());
            result = transPS.executeUpdate();
            log.info("更新资产评估登记信息"+(result>0?"成功":"失败"));
            
		 }catch(Exception e){
			 
			 log.error("更新资产评估登记信息时出错："+e.getMessage());
			 e.printStackTrace();
			 throw new IException("Gen_E001",e);
			 
		 }finally{
			 this.cleanup(transPS);
		 }
		 
		return (result>0?1:-1);
	}
	
	
	public long updateStatus(long lID,long lStatus,Connection transConn)throws Exception
	{
		int result = 0;
		PreparedStatement transPS = null;
		try{
			transPS = transConn.prepareStatement(" UPDATE LOAN_CAPITALRATINGGENERAL SET STATUS = ? WHERE ID = ? ");
			transPS.setLong(1,lStatus);
			transPS.setLong(2,lID);
			result = transPS.executeUpdate();
			
		}catch(Exception e){
			log.error("更新资产评估信息时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			this.cleanup(transPS);
		}
		return (result>0?1:-1);
	}
	
	private long getMaxID() throws Exception 
	{
		long id = -1;
		Connection localConn = null;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_CAPITALRATINGGENERAL");
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




















