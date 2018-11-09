package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANLandHousingWarrantInfo;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANNegotiableseCuritiesInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOANNegotiableseCuritiesDAO{

    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
    
    public long doInsert(LOANNegotiableseCuritiesInfo loanNegotiablesecuritiesInfo)throws Exception
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
			throw new IException("LOANNegotiableseCuritiesDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			maxID = getMaxID();
			sbSQL.append(" INSERT INTO LOAN_NEGOTIABLESECURITIES(ID,GAGEID,CERTIFICATENUMBER,ACCOUNTNUMBER,EMISSIONORGCODE,EMISSIONORGNAME,STOCKPROPERTY,");
			sbSQL.append(" DATEOFISSUE,NENUMBEER,FREEZEDATEFROM,FREEZEDATETO,CHECKINORGAN,CHECKINDATE,STATUS,INPUTUSERID,INPUTDATE,OFFICEID,CURRENCYID,");
			sbSQL.append(" WARRANTCODE,WARRANTNAME,WARRANTTYPE,CHINESENUMERAL)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,maxID);
			transPS.setLong(2,loanNegotiablesecuritiesInfo.getGageid());
			transPS.setString(3,loanNegotiablesecuritiesInfo.getCertificatenumber());
			transPS.setString(4,loanNegotiablesecuritiesInfo.getAccountnumber());
			transPS.setString(5,loanNegotiablesecuritiesInfo.getEmissionorgcode());
			transPS.setString(6,loanNegotiablesecuritiesInfo.getEmissionorgname());
			transPS.setString(7,loanNegotiablesecuritiesInfo.getStockproperty());
			transPS.setTimestamp(8,loanNegotiablesecuritiesInfo.getDateofissue());
			transPS.setLong(9,loanNegotiablesecuritiesInfo.getNenumbeer());
			transPS.setTimestamp(10,loanNegotiablesecuritiesInfo.getFreezedatefrom());
			transPS.setTimestamp(11,loanNegotiablesecuritiesInfo.getFreezedateto());
			transPS.setString(12,loanNegotiablesecuritiesInfo.getCheckinorgan());
			transPS.setTimestamp(13,loanNegotiablesecuritiesInfo.getCheckindate());
			transPS.setLong(14,loanNegotiablesecuritiesInfo.getStatus());
			transPS.setLong(15,loanNegotiablesecuritiesInfo.getInputuserid());
			transPS.setTimestamp(16,loanNegotiablesecuritiesInfo.getInputdate());
			transPS.setLong(17,loanNegotiablesecuritiesInfo.getOfficeid());
			transPS.setLong(18,loanNegotiablesecuritiesInfo.getCurrencyid());
			transPS.setString(19,builCode(LOANConstant.GageType.GPGQ));
			transPS.setString(20,LOANConstant.GageType.getName(LOANConstant.GageType.GPGQ));
			transPS.setLong(21,LOANConstant.GageType.GPGQ);
			transPS.setString(22,loanNegotiablesecuritiesInfo.getChinesenumeral());
			result = transPS.executeUpdate();
			
		}catch(Exception e){
			
			log.error("插入股票股权信息时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_001",e);
			
		}finally{
            this.cleanup(transPS);
            this.cleanup(transConn);
		}
		log.info("插入股票股权信息"+(result>0?"成功":"失败"));
		
		return (result>0?maxID:-1);
	}
	
	public long doUpdate(LOANNegotiableseCuritiesInfo loanNegotiablesecuritiesInfo)throws Exception
	{
        int result = 0;
        Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		
		try{
			
			 transConn = Database.getConnection();

		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("LOANNegotiableseCuritiesDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			sbSQL.append(" UPDATE LOAN_NEGOTIABLESECURITIES SET CERTIFICATENUMBER = ?,ACCOUNTNUMBER = ?,EMISSIONORGCODE = ?,EMISSIONORGNAME = ?,");
			sbSQL.append(" STOCKPROPERTY = ?,DATEOFISSUE = ?,NENUMBEER = ?,FREEZEDATEFROM = ?,FREEZEDATETO = ?,CHECKINORGAN = ?,CHECKINDATE = ?,");
			sbSQL.append(" CHINESENUMERAL = ? WHERE ID = ?");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setString(1,loanNegotiablesecuritiesInfo.getCertificatenumber());
			transPS.setString(2,loanNegotiablesecuritiesInfo.getAccountnumber());
			transPS.setString(3,loanNegotiablesecuritiesInfo.getEmissionorgcode());
			transPS.setString(4,loanNegotiablesecuritiesInfo.getEmissionorgname());
			transPS.setString(5,loanNegotiablesecuritiesInfo.getStockproperty());
			transPS.setTimestamp(6,loanNegotiablesecuritiesInfo.getDateofissue());
			transPS.setLong(7,loanNegotiablesecuritiesInfo.getNenumbeer());
			transPS.setTimestamp(8,loanNegotiablesecuritiesInfo.getFreezedatefrom());
			transPS.setTimestamp(9,loanNegotiablesecuritiesInfo.getFreezedateto());
			transPS.setString(10,loanNegotiablesecuritiesInfo.getCheckinorgan());
			transPS.setTimestamp(11,loanNegotiablesecuritiesInfo.getCheckindate());
			transPS.setString(12,loanNegotiablesecuritiesInfo.getChinesenumeral());
			transPS.setLong(13,loanNegotiablesecuritiesInfo.getId());
			result = transPS.executeUpdate();
			
		}catch(Exception e){
			
			log.error("更新股票股权信息时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_001",e);
			
		}finally{
          this.cleanup(transPS);
          this.cleanup(transConn);
		}
		log.info("更新股票股权信息"+(result>0?"成功":"失败"));
		return result;
	}
	
	public long updateLendInfo(long lID,String lendPerson,Timestamp lendDate,Timestamp anticiPateDate,String lendCause,Timestamp realiTyDate)throws Exception
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
			throw new IException("LOANNegotiableseCuritiesDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			 sbSQL.append(" UPDATE  LOAN_NEGOTIABLESECURITIES SET LENDPERSON = ?,LENDDATE = ?,ANTICIPATEDDATE = ?,");
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
	
	public LOANNegotiableseCuritiesInfo findByID(long ID)throws Exception
	{
			Connection transConn = null;
			PreparedStatement transPS = null;
			ResultSet transRS = null;
		    StringBuffer sbSQL = new StringBuffer();
		    LOANNegotiableseCuritiesInfo loanNegotiablesecuritiesInfo = null;
		    try{
				
				 transConn = Database.getConnection();
				 
			}catch(Exception eConn){
				
				eConn.printStackTrace();
				throw new IException("LOANNegotiableseCuritiesDAO---创建数据库链接时出错："+eConn.getMessage());
			}
		   try{
			   sbSQL.append("SELECT ID,GAGEID,CERTIFICATENUMBER,ACCOUNTNUMBER,EMISSIONORGCODE,EMISSIONORGNAME,STOCKPROPERTY,DATEOFISSUE,NENUMBEER,");
			   sbSQL.append("FREEZEDATEFROM,FREEZEDATETO,CHECKINORGAN,CHECKINDATE,STATUS,INPUTUSERID,INPUTDATE,OFFICEID,CURRENCYID,WARRANTCODE,WARRANTNAME,");
			   sbSQL.append("CHINESENUMERAL,LENDPERSON,LENDDATE,ANTICIPATEDDATE,LENDCAUSE,REALITYDATE FROM LOAN_NEGOTIABLESECURITIES WHERE ID = ?");
			   transPS = transConn.prepareStatement(sbSQL.toString());
			   transPS.setLong(1,ID);
			   transRS = transPS.executeQuery();
			   if(transRS != null && transRS.next())
			   {
				   loanNegotiablesecuritiesInfo = new LOANNegotiableseCuritiesInfo();
				   loanNegotiablesecuritiesInfo.setId(transRS.getLong("ID"));
				   loanNegotiablesecuritiesInfo.setGageid(transRS.getLong("GAGEID"));
				   loanNegotiablesecuritiesInfo.setCertificatenumber(transRS.getString("CERTIFICATENUMBER"));
				   loanNegotiablesecuritiesInfo.setAccountnumber(transRS.getString("ACCOUNTNUMBER"));
				   loanNegotiablesecuritiesInfo.setEmissionorgname(transRS.getString("EMISSIONORGCODE"));
				   loanNegotiablesecuritiesInfo.setEmissionorgcode(transRS.getString("EMISSIONORGNAME"));
				   loanNegotiablesecuritiesInfo.setStockproperty(transRS.getString("STOCKPROPERTY"));
				   loanNegotiablesecuritiesInfo.setDateofissue(transRS.getTimestamp("DATEOFISSUE"));
				   loanNegotiablesecuritiesInfo.setNenumbeer(transRS.getLong("NENUMBEER"));
				   loanNegotiablesecuritiesInfo.setFreezedatefrom(transRS.getTimestamp("FREEZEDATEFROM"));
				   loanNegotiablesecuritiesInfo.setFreezedateto(transRS.getTimestamp("FREEZEDATETO"));
				   loanNegotiablesecuritiesInfo.setCheckinorgan(transRS.getString("CHECKINORGAN"));
				   loanNegotiablesecuritiesInfo.setCheckindate(transRS.getTimestamp("CHECKINDATE"));
				   loanNegotiablesecuritiesInfo.setStatus(transRS.getLong("STATUS"));
				   loanNegotiablesecuritiesInfo.setInputuserid(transRS.getLong("INPUTUSERID"));
				   loanNegotiablesecuritiesInfo.setInputdate(transRS.getTimestamp("INPUTDATE"));
				   loanNegotiablesecuritiesInfo.setOfficeid(transRS.getLong("OFFICEID"));
				   loanNegotiablesecuritiesInfo.setCurrencyid(transRS.getLong("CURRENCYID"));
				   loanNegotiablesecuritiesInfo.setWarrantcode(transRS.getString("WARRANTCODE"));
				   loanNegotiablesecuritiesInfo.setWarrantname(transRS.getString("WARRANTNAME"));
				   loanNegotiablesecuritiesInfo.setChinesenumeral(transRS.getString("CHINESENUMERAL"));
				   loanNegotiablesecuritiesInfo.setLendPerson(transRS.getString("LENDPERSON"));
				   loanNegotiablesecuritiesInfo.setLendDate(transRS.getTimestamp("LENDDATE"));
				   loanNegotiablesecuritiesInfo.setAnticipateDate(transRS.getTimestamp("ANTICIPATEDDATE"));
				   loanNegotiablesecuritiesInfo.setLendCause(transRS.getString("LENDCAUSE"));
				   loanNegotiablesecuritiesInfo.setRealityDate(transRS.getTimestamp("REALITYDATE"));
			   }
			   
		   }catch(Exception e){
			   log.error("通过ID查找股票股权权证时出错："+e.getMessage());
			   e.printStackTrace();
			   throw new IException("Gen_E001",e);
		   }finally{
			   this.cleanup(transRS);
			   this.cleanup(transPS);
			   this.cleanup(transConn);
		   }
		   return loanNegotiablesecuritiesInfo;
	}
	
	public long updateStatus(long ID,long lStatus,Connection transConn)throws Exception
    {
    	StringBuffer sbSQL = new StringBuffer();
    	int result = 0;
    	PreparedStatement transPS = null;
    	try{
			if(transConn == null)
				transConn = Database.getConnection();
			
		}catch(Exception eConn){
			throw new IException(eConn.getMessage());
		}
		try{
			sbSQL.append(" UPDATE LOAN_NEGOTIABLESECURITIES SET STATUS = ? WHERE ID = ? ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			transPS.setLong(2,ID);
			result = transPS.executeUpdate();

		}catch(Exception e){
			log.error("更新股票股权权证状态时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}finally{
			 this.cleanup(transPS);
		 }
		return (result>0?1:-1);
    }
	
	public long updateStatus(long ID,long lStatus)throws Exception
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
			sbSQL.append(" UPDATE LOAN_NEGOTIABLESECURITIES SET STATUS = ? WHERE ID = ? ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,lStatus);
			transPS.setLong(2,ID);
			result = transPS.executeUpdate();

		}catch(Exception e){
			log.error("更新股票股权权证状态时出错："+e.getMessage());
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
	    	transPS = transConn.prepareStatement("select nvl(max(substr(warrantcode," + (nLen+1) + ",3)),0)+1 as code from LOAN_NEGOTIABLESECURITIES where warrantcode like 'S" + strYear + type + "%'");
	    	transRS = transPS.executeQuery();
			if(transRS.next() && transRS != null)
			{
				long number = transRS.getLong("code");
				if (number < 10)
				{
					strCode = "S" + strYear +type + "00" + number;
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
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_NEGOTIABLESECURITIES");
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
