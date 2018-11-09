package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANLandHousingWarrantInfo;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANWarrantApplyInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.loan.util.LOANConstant;

public class LOANLandHousingWarrantDAO{

    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	
	public long doInsert(LOANLandHousingWarrantInfo loanLandHousingWarrantInfo)throws Exception
	{
		long maxID = -1;
		int result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		
		try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("保存国有、房屋所有权权证,创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			
			sbSQL.append("INSERT INTO LOAN_LANDHOUSINGWARRANT(ID,GAGEID,WARRANTCODE,WARRANTNAME,WARRANTTYPE,LANDDIRTID,LANDLOCATION,");
			sbSQL.append("LANDFIELDSNUMBER,LANDCHARTNUMBER,LANDPROPERTIES,LANDUSEAREA,LANDPURPOSE,LANDUSETYPE,LANDCATEGORYSCOPE,");
			sbSQL.append("LANDCHECKINORGAN,LANDCHECKINDATE,LANDSETDATE,LANDTITLEORDER,LANDTIMELIMIT,LANDADVERSARIA,HOUSEDROITNUMBER,");
			sbSQL.append("HOUSELOCATION,HOUSEDROITTYPE,HOUSESETDATE,HOUSEFAITHDATE,HOUSELOGOUTDATE,HOUSECHECKINORGAN,HOUSECHECKINDATE,");
			sbSQL.append("HDROITVALUE,STATUS,INPUTUSERID,INPUTDATE,OFFICEID,CURRENCYID)");
			sbSQL.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?)");
		    maxID = this.getMaxID();
		    transPS = transConn.prepareStatement(sbSQL.toString());
		    transPS.setLong(1,maxID);
		    transPS.setLong(2,loanLandHousingWarrantInfo.getGageid());
		    transPS.setString(3,this.builCode(loanLandHousingWarrantInfo.getWarrantType()));
		    transPS.setString(4,LOANConstant.GageType.getName(loanLandHousingWarrantInfo.getWarrantType()));
		    transPS.setLong(5,loanLandHousingWarrantInfo.getWarrantType());
		    transPS.setString(6,loanLandHousingWarrantInfo.getLandDirtid());
		    transPS.setString(7,loanLandHousingWarrantInfo.getLandLocation());
		    transPS.setString(8,loanLandHousingWarrantInfo.getLandFieldsnumber());
		    transPS.setString(9,loanLandHousingWarrantInfo.getLandChartnumber());
		    transPS.setString(10,loanLandHousingWarrantInfo.getLandProperties());
		    transPS.setString(11,loanLandHousingWarrantInfo.getLandUsearea());
		    transPS.setString(12,loanLandHousingWarrantInfo.getLandPurpose());
		    transPS.setString(13,loanLandHousingWarrantInfo.getLandUsetype());
		    transPS.setString(14,loanLandHousingWarrantInfo.getLandCategoryscope());
		    transPS.setString(15,loanLandHousingWarrantInfo.getLandCheckinorgan());
		    transPS.setTimestamp(16,loanLandHousingWarrantInfo.getLandCheckindate());
		    transPS.setTimestamp(17,loanLandHousingWarrantInfo.getLandSetdate());
		    transPS.setString(18,loanLandHousingWarrantInfo.getLandTitleorder());
		    transPS.setString(19,loanLandHousingWarrantInfo.getLandTimelimit());
		    transPS.setString(20,loanLandHousingWarrantInfo.getLandAdversaria());
		    transPS.setString(21,loanLandHousingWarrantInfo.getHouseDroitnumber());
		    transPS.setString(22,loanLandHousingWarrantInfo.getHouseLocation());
		    transPS.setString(23,loanLandHousingWarrantInfo.getHouseDroittype());
		    transPS.setTimestamp(24,loanLandHousingWarrantInfo.getHouseSetdate());
		    transPS.setTimestamp(25,loanLandHousingWarrantInfo.getHouseFaithdate());
		    transPS.setTimestamp(26,loanLandHousingWarrantInfo.getHouseLogoutdate());
		    transPS.setString(27,loanLandHousingWarrantInfo.getHouseCheckinorgan());
		    transPS.setTimestamp(28,loanLandHousingWarrantInfo.getHouseCheckindate());
		    transPS.setDouble(29,loanLandHousingWarrantInfo.getHdroitValue());
		    transPS.setLong(30,loanLandHousingWarrantInfo.getStatus());
		    transPS.setLong(31,loanLandHousingWarrantInfo.getInputuserid());
		    transPS.setLong(32,loanLandHousingWarrantInfo.getOfficeid());
		    transPS.setLong(33,loanLandHousingWarrantInfo.getCurrencyid());
		    result = transPS.executeUpdate();
		    
		
		}catch(Exception e){
			
			log.error("保存国有土地、房屋所有权权证时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		return (result>0?maxID:-1);
	}
	
	
	public long doUpdate(LOANLandHousingWarrantInfo loanLandHousingWarrantInfo)throws Exception
	{
		
		int result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		
		try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("更新国有、房屋所有权权证,创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			sbSQL.append("UPDATE LOAN_LANDHOUSINGWARRANT SET LANDDIRTID = ?,LANDLOCATION = ?,LANDFIELDSNUMBER = ?,LANDCHARTNUMBER = ?,");
			sbSQL.append("LANDPROPERTIES = ?,LANDUSEAREA = ?,LANDPURPOSE = ?,LANDUSETYPE = ?,LANDCATEGORYSCOPE = ?,LANDCHECKINORGAN = ?,");
			sbSQL.append("LANDCHECKINDATE = ?,LANDSETDATE = ?,LANDTITLEORDER = ?,LANDTIMELIMIT = ?,LANDADVERSARIA = ?,HOUSEDROITNUMBER = ?,");
			sbSQL.append("HOUSELOCATION = ?,HOUSEDROITTYPE = ?,HOUSESETDATE = ?,HOUSEFAITHDATE = ?,HOUSELOGOUTDATE = ?,HOUSECHECKINORGAN = ?,");
			sbSQL.append("HOUSECHECKINDATE = ?,HDROITVALUE = ? WHERE ID = ?");
			transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setString(1,loanLandHousingWarrantInfo.getLandDirtid());
		    transPS.setString(2,loanLandHousingWarrantInfo.getLandLocation());
		    transPS.setString(3,loanLandHousingWarrantInfo.getLandFieldsnumber());
		    transPS.setString(4,loanLandHousingWarrantInfo.getLandChartnumber());
		    transPS.setString(5,loanLandHousingWarrantInfo.getLandProperties());
		    transPS.setString(6,loanLandHousingWarrantInfo.getLandUsearea());
		    transPS.setString(7,loanLandHousingWarrantInfo.getLandPurpose());
		    transPS.setString(8,loanLandHousingWarrantInfo.getLandUsetype());
		    transPS.setString(9,loanLandHousingWarrantInfo.getLandCategoryscope());
		    transPS.setString(10,loanLandHousingWarrantInfo.getLandCheckinorgan());
		    transPS.setTimestamp(11,loanLandHousingWarrantInfo.getLandCheckindate());
		    transPS.setTimestamp(12,loanLandHousingWarrantInfo.getLandSetdate());
		    transPS.setString(13,loanLandHousingWarrantInfo.getLandTitleorder());
		    transPS.setString(14,loanLandHousingWarrantInfo.getLandTimelimit());
		    transPS.setString(15,loanLandHousingWarrantInfo.getLandAdversaria());
		    transPS.setString(16,loanLandHousingWarrantInfo.getHouseDroitnumber());
		    transPS.setString(17,loanLandHousingWarrantInfo.getHouseLocation());
		    transPS.setString(18,loanLandHousingWarrantInfo.getHouseDroittype());
		    transPS.setTimestamp(19,loanLandHousingWarrantInfo.getHouseSetdate());
		    transPS.setTimestamp(20,loanLandHousingWarrantInfo.getHouseFaithdate());
		    transPS.setTimestamp(21,loanLandHousingWarrantInfo.getHouseLogoutdate());
		    transPS.setString(22,loanLandHousingWarrantInfo.getHouseCheckinorgan());
		    transPS.setTimestamp(23,loanLandHousingWarrantInfo.getHouseCheckindate());
		    transPS.setDouble(24,loanLandHousingWarrantInfo.getHdroitValue());
		    transPS.setLong(25,loanLandHousingWarrantInfo.getId());
		    result = transPS.executeUpdate();
			
		}catch(Exception e){
			
			log.error("更新国有土地所有权、房屋他项权益权证时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			
			this.cleanup(transPS);
			this.cleanup(transConn);
			
		}
		
		return (result>0?loanLandHousingWarrantInfo.getId():-1);
	}
	
	
	public long updateLendInfo(long lID,String lendPerson,Timestamp lendDate,Timestamp anticiPateDate,String lendCause,Timestamp realiTyDate)throws Exception
	{
		int result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		
		try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("保存申请出库、出借、归还,创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			 sbSQL.append(" UPDATE  LOAN_LANDHOUSINGWARRANT SET LENDPERSON = ?,LENDDATE = ?,ANTICIPATEDDATE = ?,");
			 sbSQL.append(" LENDCAUSE = ?,REALITYDATE = ? WHERE ID = ?");
			 transPS = transConn.prepareStatement(sbSQL.toString());
			 transPS.setString(1,lendPerson);
			 transPS.setTimestamp(2,lendDate);
			 transPS.setTimestamp(3,anticiPateDate);
			 transPS.setString(4,lendCause);
			 transPS.setTimestamp(5,realiTyDate);
			 transPS.setLong(6,lID);
			 result = transPS.executeUpdate();
			 
		}catch(Exception e){
			
			log.info("保存申请时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
           this.cleanup(transPS);
           this.cleanup(transConn);
		}
		return (result>0?lID:-1);
	}
	
	public LOANLandHousingWarrantInfo findByID(long lID)throws Exception
	{
		
		Connection transConn = null;
		PreparedStatement transPS = null;
		ResultSet transRS = null;
		StringBuffer sbSQL = new StringBuffer();
		LOANLandHousingWarrantInfo loanLandHousingWarrantInfo = null;
		try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("findByID().....创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			 sbSQL.append("SELECT ID,GAGEID,WARRANTCODE,WARRANTNAME,WARRANTTYPE,LANDDIRTID,LANDLOCATION,");
			 sbSQL.append("LANDFIELDSNUMBER,LANDCHARTNUMBER,LANDPROPERTIES,LANDUSEAREA,LANDPURPOSE,LANDUSETYPE,LANDCATEGORYSCOPE,");
			 sbSQL.append("LANDCHECKINORGAN,LANDCHECKINDATE,LANDSETDATE,LANDTITLEORDER,LANDTIMELIMIT,LANDADVERSARIA,HOUSEDROITNUMBER,");
			 sbSQL.append("HOUSELOCATION,HOUSEDROITTYPE,HOUSESETDATE,HOUSEFAITHDATE,HOUSELOGOUTDATE,HOUSECHECKINORGAN,HOUSECHECKINDATE,");
			 sbSQL.append("HDROITVALUE,STATUS,INPUTUSERID,INPUTDATE,OFFICEID,CURRENCYID,LENDPERSON,LENDDATE,ANTICIPATEDDATE,LENDCAUSE,");
			 sbSQL.append("REALITYDATE  FROM LOAN_LANDHOUSINGWARRANT WHERE ID = ?");
			 transPS = transConn.prepareStatement(sbSQL.toString());
             transPS.setLong(1,lID);
             transRS = transPS.executeQuery();
             if(transRS != null && transRS.next())
             {
            	 loanLandHousingWarrantInfo = new LOANLandHousingWarrantInfo();
            	 loanLandHousingWarrantInfo.setId(transRS.getLong("ID"));
            	 loanLandHousingWarrantInfo.setGageid(transRS.getLong("GAGEID"));
            	 loanLandHousingWarrantInfo.setWarrantcode(transRS.getString("WARRANTCODE"));
            	 loanLandHousingWarrantInfo.setWarrantname(transRS.getString("WARRANTNAME"));
            	 loanLandHousingWarrantInfo.setWarrantType(transRS.getLong("WARRANTTYPE"));
            	 loanLandHousingWarrantInfo.setLandDirtid(transRS.getString("LANDDIRTID"));
            	 loanLandHousingWarrantInfo.setLandLocation(transRS.getString("LANDLOCATION"));
            	 loanLandHousingWarrantInfo.setLandFieldsnumber(transRS.getString("LANDFIELDSNUMBER"));
            	 loanLandHousingWarrantInfo.setLandChartnumber(transRS.getString("LANDCHARTNUMBER"));
            	 loanLandHousingWarrantInfo.setLandProperties(transRS.getString("LANDPROPERTIES"));
            	 loanLandHousingWarrantInfo.setLandUsearea(transRS.getString("LANDUSEAREA"));
            	 loanLandHousingWarrantInfo.setLandPurpose(transRS.getString("LANDPURPOSE"));
            	 loanLandHousingWarrantInfo.setLandUsetype(transRS.getString("LANDUSETYPE"));
            	 loanLandHousingWarrantInfo.setLandCategoryscope(transRS.getString("LANDCATEGORYSCOPE"));
            	 loanLandHousingWarrantInfo.setLandCheckinorgan(transRS.getString("LANDCHECKINORGAN"));
            	 loanLandHousingWarrantInfo.setLandCheckindate(transRS.getTimestamp("LANDCHECKINDATE"));
            	 loanLandHousingWarrantInfo.setLandSetdate(transRS.getTimestamp("LANDSETDATE"));
            	 loanLandHousingWarrantInfo.setLandTitleorder(transRS.getString("LANDTITLEORDER"));
            	 loanLandHousingWarrantInfo.setLandTimelimit(transRS.getString("LANDTIMELIMIT"));
            	 loanLandHousingWarrantInfo.setLandAdversaria(transRS.getString("LANDADVERSARIA"));
            	 loanLandHousingWarrantInfo.setHouseDroitnumber(transRS.getString("HOUSEDROITNUMBER"));
            	 loanLandHousingWarrantInfo.setHouseLocation(transRS.getString("HOUSELOCATION"));
            	 loanLandHousingWarrantInfo.setHouseDroittype(transRS.getString("HOUSEDROITTYPE"));
            	 loanLandHousingWarrantInfo.setHouseSetdate(transRS.getTimestamp("HOUSESETDATE"));
            	 loanLandHousingWarrantInfo.setHouseFaithdate(transRS.getTimestamp("HOUSEFAITHDATE"));
            	 loanLandHousingWarrantInfo.setHouseLogoutdate(transRS.getTimestamp("HOUSELOGOUTDATE"));
            	 loanLandHousingWarrantInfo.setHouseCheckinorgan(transRS.getString("HOUSECHECKINORGAN"));
            	 loanLandHousingWarrantInfo.setHouseCheckindate(transRS.getTimestamp("HOUSECHECKINDATE"));
            	 loanLandHousingWarrantInfo.setHdroitValue(transRS.getDouble("HDROITVALUE"));
            	 loanLandHousingWarrantInfo.setStatus(transRS.getLong("STATUS"));
            	 loanLandHousingWarrantInfo.setInputuserid(transRS.getLong("INPUTUSERID"));
            	 loanLandHousingWarrantInfo.setInputdate(transRS.getTimestamp("INPUTDATE"));
            	 loanLandHousingWarrantInfo.setOfficeid(transRS.getLong("OFFICEID"));
            	 loanLandHousingWarrantInfo.setCurrencyid(transRS.getLong("CURRENCYID"));
            	 loanLandHousingWarrantInfo.setLendPerson(transRS.getString("LENDPERSON"));
            	 loanLandHousingWarrantInfo.setLendDate(transRS.getTimestamp("LENDDATE"));
            	 loanLandHousingWarrantInfo.setAnticipateDate(transRS.getTimestamp("ANTICIPATEDDATE"));
            	 loanLandHousingWarrantInfo.setLendCause(transRS.getString("LENDCAUSE"));
            	 loanLandHousingWarrantInfo.setRealityDate(transRS.getTimestamp("REALITYDATE"));
            	 
             }
			
			
			
		}catch(Exception e){
			
			log.error("查找国有土地使用权、房屋他项所有权益权证时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			
			this.cleanup(transRS);
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		
		return loanLandHousingWarrantInfo;
	}
	
	public long updateStatus(long lID,long lStatus,Connection transConn)throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		int result = 0;
		PreparedStatement transPS = null;
		try{
			sbSQL.append(" UPDATE LOAN_LANDHOUSINGWARRANT SET STATUS = ? WHERE ID = ? ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			transPS.setLong(2,lID);
			result = transPS.executeUpdate();
			
		}catch(Exception e){
			log.error("更新国有土地、房屋他项权益状态时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			 this.cleanup(transPS);
		}
		
		return (result>0?lID:-1);
	}

	
	public long updateStatus(long lID,long lStatus)throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		int result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		try{
				transConn = Database.getConnection();
			
		}catch(Exception eConn){
			throw new IException(eConn.getMessage());
		}
		try{
			sbSQL.append(" UPDATE LOAN_LANDHOUSINGWARRANT SET STATUS = ? WHERE ID = ? ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			transPS.setLong(2,lID);
			result = transPS.executeUpdate();
			
		}catch(Exception e){
			log.error("更新国有土地、房屋他项权益状态时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			 this.cleanup(transPS);
			 this.cleanup(transConn);
		}
		return (result>0?lID:-1);
	}

	
	
	
	
	
	
	
	
	private long getMaxID() throws Exception 
	{
		long id = -1;
		Connection localConn = null;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_LANDHOUSINGWARRANT");
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
	private String builCode(long type)throws Exception
	{
		String strCode = "";
		String strYear = "";
		int nLen = 0;
		Connection transConn = null;
		ResultSet transRS = null;	
		PreparedStatement transPS = null;
		try{
			transConn = Database.getConnection();
			transPS = transConn.prepareStatement(" select to_char(sysdate,'yy') as year from dual ");
			transRS = transPS.executeQuery();
			if (transRS.next() && transRS != null){
				strYear = transRS.getString("year");
			}
			nLen = String.valueOf(type).length() + strYear.length() + 1;
			log.info("strYear:" + strYear + " applyType:" + type+"nLen:"+nLen);
			this.cleanup(transRS);
			this.cleanup(transPS);
		}catch(Exception e){
			this.cleanup(transRS);
			this.cleanup(transPS);
			this.cleanup(transConn);
			log.error("生成编号第一步时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
	    try{
	    	transPS = transConn.prepareStatement("select nvl(max(substr(warrantcode," + (nLen+1) + ",3)),0)+1 as code from LOAN_LANDHOUSINGWARRANT where warrantcode like 'S" + strYear + type + "%'");
	    	transRS = transPS.executeQuery();
			if(transRS.next() && transRS != null)
			{
				long number = transRS.getLong("code");
				if (number < 10)
				{
					strCode = "S" + strYear + type + "00" + number;
				}
				else if (number < 100)
				{
					strCode = "S" + strYear + type + "0" + number;
				}
				else
				{
					strCode = "S" + strYear + type + number;
				}
			}
            log.info("编号生成成功："+strCode);
		}catch(Exception e){
			
			log.error("生成编号第二步时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			
			this.cleanup(transRS);
			this.cleanup(transPS);
			this.cleanup(transConn);
			
		}
		return strCode;
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























