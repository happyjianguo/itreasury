package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANBillInfo;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANCarqualiFiedInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOANCarqualiFiedDAO{

    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	public long doInsert(LOANCarqualiFiedInfo loanCarqualifiedInfo)throws Exception
	{
		int result = 0;
		long maxID = -1;
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		try{
				
		       transConn = Database.getConnection();

		}catch(Exception eConn){
				
				eConn.printStackTrace();
				throw new IException("LOANCarqualiFiedDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			
			maxID = this.getMaxID();
			sbSQL.append(" INSERT INTO LOAN_CARQUALIFIED(ID,GAGEID,QUALIFIEDNUMBER,VINCODE,ENGINENUMBER,CARMODEL,COLOR,SALEPRICE,");
			sbSQL.append(" DATEOFISSUE,STOCKPILESTATUS,CKDATE,COMMENTS,OFFICEID,CURRENCYID,INPUTUSERID,INPUTDATE,STATUS,WARRANTCODE,");
			sbSQL.append(" WARRANTNAME,WARRANTTYPE)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,maxID);
			transPS.setLong(2,loanCarqualifiedInfo.getGageid());
			transPS.setString(3,loanCarqualifiedInfo.getQualifiednumber());
			transPS.setString(4,loanCarqualifiedInfo.getVincode());
			transPS.setString(5,loanCarqualifiedInfo.getEnginenumber());
			transPS.setString(6,loanCarqualifiedInfo.getCarmodel());
			transPS.setString(7,loanCarqualifiedInfo.getColor());
			transPS.setDouble(8,loanCarqualifiedInfo.getSaleprice());
			transPS.setTimestamp(9,loanCarqualifiedInfo.getDateofissue());
			transPS.setLong(10,loanCarqualifiedInfo.getStockpilestatus());
			transPS.setTimestamp(11,loanCarqualifiedInfo.getCkdate());
			transPS.setString(12,loanCarqualifiedInfo.getComments());
			transPS.setLong(13,loanCarqualifiedInfo.getOfficeid());
			transPS.setLong(14,loanCarqualifiedInfo.getCurrencyid());
			transPS.setLong(15,loanCarqualifiedInfo.getInputuserid());
			transPS.setTimestamp(16,loanCarqualifiedInfo.getInputdate());
			transPS.setLong(17,loanCarqualifiedInfo.getStatus());
			transPS.setString(18,builCode(LOANConstant.GageType.QCHGZ));
			transPS.setString(19,LOANConstant.GageType.getName(LOANConstant.GageType.QCHGZ));
			transPS.setLong(20,LOANConstant.GageType.QCHGZ);
			result = transPS.executeUpdate();
			
		}catch(Exception e){
			
			log.error("保存汽车合格证时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		
		return (result>0?maxID:-1);
	}
	
	public long doUpdate(LOANCarqualiFiedInfo loanCarqualifiedInfo)throws Exception
	{
		int result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		try{
				
		       transConn = Database.getConnection();

		}catch(Exception eConn){
				
				eConn.printStackTrace();
				throw new IException("LOANCarqualiFiedDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			sbSQL.append(" UPDATE LOAN_CARQUALIFIED SET QUALIFIEDNUMBER = ?,VINCODE = ?,ENGINENUMBER = ?,CARMODEL = ?,");
			sbSQL.append(" DATEOFISSUE = ?,CKDATE = ?,COMMENTS = ?,COLOR = ?,SALEPRICE = ?");
			sbSQL.append(" WHERE ID = ?");
			log.info("更新汽车合格证sql :"+sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setString(1,loanCarqualifiedInfo.getQualifiednumber());
			transPS.setString(2,loanCarqualifiedInfo.getVincode());
			transPS.setString(3,loanCarqualifiedInfo.getEnginenumber());
			transPS.setString(4,loanCarqualifiedInfo.getCarmodel());
			transPS.setTimestamp(5,loanCarqualifiedInfo.getDateofissue());
			transPS.setTimestamp(6,loanCarqualifiedInfo.getCkdate());
			transPS.setString(7,loanCarqualifiedInfo.getComments());
			transPS.setString(8,loanCarqualifiedInfo.getColor());
			transPS.setDouble(9,loanCarqualifiedInfo.getSaleprice());
			transPS.setLong(10,loanCarqualifiedInfo.getId());
			result = transPS.executeUpdate();
			
			log.info("更新汽车合格证完成.....返回状态是："+(result>0?"成功":"失败"));
		}catch(Exception e){
			
			log.error("更新汽车合格证时出现错误："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		
		return result;
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
				throw new IException("LOANCarqualiFiedDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			 sbSQL.append(" UPDATE  LOAN_CARQUALIFIED SET LENDPERSON = ?,LENDDATE = ?,ANTICIPATEDDATE = ?,");
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
	
	
	public LOANCarqualiFiedInfo findByID(long lID)throws Exception
	{
		Connection transConn = null;
		PreparedStatement transPS = null;
		ResultSet transRS = null;
	    StringBuffer sbSQL = new StringBuffer();
	    LOANCarqualiFiedInfo loanCarqualifiedInfo = null;
	    try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("LOANBillDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			sbSQL.append(" SELECT ID,GAGEID,QUALIFIEDNUMBER,VINCODE,ENGINENUMBER,CARMODEL,COLOR,SALEPRICE,DATEOFISSUE,");
			sbSQL.append(" STOCKPILESTATUS,CKDATE,COMMENTS,INPUTUSERID,INPUTDATE,STATUS,WARRANTCODE,WARRANTNAME,");
			sbSQL.append(" LENDPERSON,LENDDATE,ANTICIPATEDDATE,LENDCAUSE,REALITYDATE FROM LOAN_CARQUALIFIED WHERE ID = ?");
			transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setLong(1,lID);
            transRS = transPS.executeQuery();
            while(transRS != null && transRS.next())
            {
            	loanCarqualifiedInfo = new LOANCarqualiFiedInfo();
            	loanCarqualifiedInfo.setId(transRS.getLong("ID"));
				loanCarqualifiedInfo.setGageid(transRS.getLong("GAGEID"));
				loanCarqualifiedInfo.setQualifiednumber(transRS.getString("QUALIFIEDNUMBER"));
				loanCarqualifiedInfo.setVincode(transRS.getString("VINCODE"));
				loanCarqualifiedInfo.setEnginenumber(transRS.getString("ENGINENUMBER"));
				loanCarqualifiedInfo.setCarmodel(transRS.getString("CARMODEL"));
				loanCarqualifiedInfo.setColor(transRS.getString("COLOR"));
				loanCarqualifiedInfo.setSaleprice(transRS.getDouble("SALEPRICE"));
				loanCarqualifiedInfo.setDateofissue(transRS.getTimestamp("DATEOFISSUE"));
				loanCarqualifiedInfo.setStockpilestatus(transRS.getLong("STOCKPILESTATUS"));
				loanCarqualifiedInfo.setCkdate(transRS.getTimestamp("CKDATE"));
				loanCarqualifiedInfo.setComments(transRS.getString("COMMENTS"));
				loanCarqualifiedInfo.setInputuserid(transRS.getLong("INPUTUSERID"));
				loanCarqualifiedInfo.setInputdate(transRS.getTimestamp("INPUTDATE"));
				loanCarqualifiedInfo.setStatus(transRS.getLong("STATUS"));
				loanCarqualifiedInfo.setWarrantCode(transRS.getString("WARRANTCODE"));
				loanCarqualifiedInfo.setWarrantName(transRS.getString("WARRANTNAME"));
				loanCarqualifiedInfo.setLendPerson(transRS.getString("LENDPERSON"));
				loanCarqualifiedInfo.setLendDate(transRS.getTimestamp("LENDDATE"));
				loanCarqualifiedInfo.setAnticipateDate(transRS.getTimestamp("ANTICIPATEDDATE"));
				loanCarqualifiedInfo.setLendCause(transRS.getString("LENDCAUSE"));
				loanCarqualifiedInfo.setRealityDate(transRS.getTimestamp("REALITYDATE"));
            }
			
		}catch(Exception e){
			log.error("根据ID查找汽车合格证时出现错误："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			this.cleanup(transRS);
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		return loanCarqualifiedInfo;
	}
	
	
	public List getCarWarrantList(long gageID)throws Exception
	{
		List carList = new ArrayList();
		Connection transConn = null;
		PreparedStatement transPS = null;
		ResultSet transRS = null;
	    StringBuffer sbSQL = new StringBuffer();
	    LOANCarqualiFiedInfo loanCarqualifiedInfo = null;
	    try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			sbSQL.append("SELECT ID,GAGEID,QUALIFIEDNUMBER,VINCODE,ENGINENUMBER,CARMODEL,COLOR,SALEPRICE,DATEOFISSUE,STOCKPILESTATUS,");
			sbSQL.append("CKDATE,COMMENTS,OFFICEID,CURRENCYID,INPUTUSERID,INPUTDATE,STATUS,WARRANTCODE,WARRANTNAME ");
			sbSQL.append("FROM LOAN_CARQUALIFIED WHERE GAGEID = ? AND STATUS != "+LOANConstant.GageStatus.INVALID +" AND STATUS!="+LOANConstant.WarrantStatus.BECOME_INVALID );
			log.info("sqlwxsususussus :"+sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,gageID);
			transRS = transPS.executeQuery();
			while(transRS != null && transRS.next())
			{
				loanCarqualifiedInfo = new LOANCarqualiFiedInfo();
				loanCarqualifiedInfo.setId(transRS.getLong("ID"));
				loanCarqualifiedInfo.setGageid(transRS.getLong("GAGEID"));
				loanCarqualifiedInfo.setQualifiednumber(transRS.getString("QUALIFIEDNUMBER"));
				loanCarqualifiedInfo.setVincode(transRS.getString("VINCODE"));
				loanCarqualifiedInfo.setEnginenumber(transRS.getString("ENGINENUMBER"));
				loanCarqualifiedInfo.setCarmodel(transRS.getString("CARMODEL"));
				loanCarqualifiedInfo.setColor(transRS.getString("COLOR"));
				loanCarqualifiedInfo.setSaleprice(transRS.getDouble("SALEPRICE"));
				loanCarqualifiedInfo.setDateofissue(transRS.getTimestamp("DATEOFISSUE"));
				loanCarqualifiedInfo.setStockpilestatus(transRS.getLong("STOCKPILESTATUS"));
				loanCarqualifiedInfo.setCkdate(transRS.getTimestamp("CKDATE"));
				loanCarqualifiedInfo.setComments(transRS.getString("COMMENTS"));
				loanCarqualifiedInfo.setOfficeid(transRS.getLong("OFFICEID"));
				loanCarqualifiedInfo.setCurrencyid(transRS.getLong("CURRENCYID"));
				loanCarqualifiedInfo.setInputuserid(transRS.getLong("INPUTUSERID"));
				loanCarqualifiedInfo.setInputdate(transRS.getTimestamp("INPUTDATE"));
				loanCarqualifiedInfo.setStatus(transRS.getLong("STATUS"));
				loanCarqualifiedInfo.setWarrantCode(transRS.getString("WARRANTCODE"));
				loanCarqualifiedInfo.setWarrantName(transRS.getString("WARRANTNAME"));
				carList.add(loanCarqualifiedInfo);
			}
			
		}catch(Exception e){
			log.error("通过ID汽车合格证时出现错误："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.cleanup(transRS);
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		
		return carList;
	}
	
	public List getQueryCarWarrantList(long gageID)throws Exception
	{
		List carList = new ArrayList();
		Connection transConn = null;
		PreparedStatement transPS = null;
		ResultSet transRS = null;
	    StringBuffer sbSQL = new StringBuffer();
	    LOANCarqualiFiedInfo loanCarqualifiedInfo = null;
	    try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			sbSQL.append("SELECT ID,GAGEID,QUALIFIEDNUMBER,VINCODE,ENGINENUMBER,CARMODEL,COLOR,SALEPRICE,DATEOFISSUE,STOCKPILESTATUS,");
			sbSQL.append("CKDATE,COMMENTS,OFFICEID,CURRENCYID,INPUTUSERID,INPUTDATE,STATUS,WARRANTCODE,WARRANTNAME ");
			sbSQL.append("FROM LOAN_CARQUALIFIED WHERE GAGEID = ? AND STATUS != "+LOANConstant.GageStatus.INVALID);
			log.info("sqlwxsususussus :"+sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,gageID);
			transRS = transPS.executeQuery();
			while(transRS != null && transRS.next())
			{
				loanCarqualifiedInfo = new LOANCarqualiFiedInfo();
				loanCarqualifiedInfo.setId(transRS.getLong("ID"));
				loanCarqualifiedInfo.setGageid(transRS.getLong("GAGEID"));
				loanCarqualifiedInfo.setQualifiednumber(transRS.getString("QUALIFIEDNUMBER"));
				loanCarqualifiedInfo.setVincode(transRS.getString("VINCODE"));
				loanCarqualifiedInfo.setEnginenumber(transRS.getString("ENGINENUMBER"));
				loanCarqualifiedInfo.setCarmodel(transRS.getString("CARMODEL"));
				loanCarqualifiedInfo.setColor(transRS.getString("COLOR"));
				loanCarqualifiedInfo.setSaleprice(transRS.getDouble("SALEPRICE"));
				loanCarqualifiedInfo.setDateofissue(transRS.getTimestamp("DATEOFISSUE"));
				loanCarqualifiedInfo.setStockpilestatus(transRS.getLong("STOCKPILESTATUS"));
				loanCarqualifiedInfo.setCkdate(transRS.getTimestamp("CKDATE"));
				loanCarqualifiedInfo.setComments(transRS.getString("COMMENTS"));
				loanCarqualifiedInfo.setOfficeid(transRS.getLong("OFFICEID"));
				loanCarqualifiedInfo.setCurrencyid(transRS.getLong("CURRENCYID"));
				loanCarqualifiedInfo.setInputuserid(transRS.getLong("INPUTUSERID"));
				loanCarqualifiedInfo.setInputdate(transRS.getTimestamp("INPUTDATE"));
				loanCarqualifiedInfo.setStatus(transRS.getLong("STATUS"));
				loanCarqualifiedInfo.setWarrantCode(transRS.getString("WARRANTCODE"));
				loanCarqualifiedInfo.setWarrantName(transRS.getString("WARRANTNAME"));
				carList.add(loanCarqualifiedInfo);
			}
			
		}catch(Exception e){
			log.error("Query通过ID汽车合格证时出现错误："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.cleanup(transRS);
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		
		return carList;
	}
	
	public long updateStatus(long CID,long lStatus,Connection transConn)throws Exception
	{
		int result = 0;
		StringBuffer sbSQL = new StringBuffer();
		PreparedStatement transPS = null;
		try{
			
			sbSQL.append(" UPDATE LOAN_CARQUALIFIED SET STATUS = ? WHERE ID = ?");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			transPS.setLong(2,CID);
			result = transPS.executeUpdate();

		}catch(Exception e){
			log.error("更新汽车合格证状态时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			this.cleanup(transPS);
		}
		return (result>0?1:-1);
	}
	
	
	public long updateStatus(String strCarID,long lStatus,Connection transConn)throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		int result = 0;
		PreparedStatement transPS = null;
		try{
			sbSQL.append(" UPDATE LOAN_CARQUALIFIED SET STATUS = ? WHERE ID IN("+strCarID+")");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			result = transPS.executeUpdate();
		}catch(Exception e){
			log.error("更新汽车合格证状态时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			 this.cleanup(transPS);
		 }
		return (result>0?1:-1);
	}
	public long updateStatus(String strCarID,long lStatus)throws Exception
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
			sbSQL.append(" UPDATE LOAN_CARQUALIFIED SET STATUS = ? WHERE ID IN("+strCarID+")");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			result = transPS.executeUpdate();
		}catch(Exception e){
			log.error("更新汽车合格证状态时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			 this.cleanup(transPS);
			 this.cleanup(transConn);
		 }
		return (result>0?1:-1);
	}
	public long updateStatus(long carID,long lStatus)throws Exception
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
			sbSQL.append(" UPDATE LOAN_CARQUALIFIED SET STATUS = ? WHERE ID = ?");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			transPS.setLong(2,carID);
			result = transPS.executeUpdate();
		}catch(Exception e){
			log.error("更新汽车合格证状态时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			 this.cleanup(transPS);
			 this.cleanup(transConn);
		 }
		return (result>0?1:-1);
	}
	
	private String builCode(long type)throws Exception
	{
		String strCode = "";
		String strYear = "";
		String strSQL = "";
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
	    	transPS = transConn.prepareStatement("select nvl(max(substr(warrantcode," + (nLen+1) + ",3)),0)+1 as code from LOAN_CARQUALIFIED where warrantcode like 'S" + strYear + type + "%'");
	    	transRS = transPS.executeQuery();
			if(transRS.next())
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
	
	
	private long getMaxID() throws Exception 
	{
		long id = -1;
		Connection localConn = null;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_CARQUALIFIED");
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
