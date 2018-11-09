package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANExterioRunitsInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOANExterioRunitsDAO{

private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);

	
	public long insert(LOANExterioRunitsInfo loanExteriorunitsInfo)throws Exception
	{
		long maxID = -1;
		int result = -1;
		String sCode = "";
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		try{
				
		       transConn = Database.getConnection();

		}catch(Exception eConn){
				
				eConn.printStackTrace();
				throw new IException("LOANExterioRunitsDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			maxID = this.getMaxID();
			sbSQL.append(" INSERT INTO LOAN_EXTERIORUNITS(ID,SCODE,SNAME,SLEGALPERSONCODECERT,OFFICEID,CURRENCYID,NSTATUSID,");
			sbSQL.append(" INPUTUSERID,INPUTDATE)VALUES(?,?,?,?,?,?,?,?,?)");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,maxID);
			transPS.setString(2,getsCode(maxID));
			transPS.setString(3,loanExteriorunitsInfo.getSName());
			transPS.setString(4,loanExteriorunitsInfo.getSlegalpersonCodecret());
			transPS.setLong(5,loanExteriorunitsInfo.getOfficeID());
			transPS.setLong(6,loanExteriorunitsInfo.getCurrencyID());
			transPS.setLong(7,loanExteriorunitsInfo.getNStatusID());
			transPS.setLong(8,loanExteriorunitsInfo.getInputuserID());
			transPS.setTimestamp(9,loanExteriorunitsInfo.getInputDate());
			result = transPS.executeUpdate();
			
		}catch(Exception e){
			
			log.error("保存外部单位信息时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
            this.cleanup(transPS);
            this.cleanup(transConn);
		}
		return (result>0?maxID:-1);
	}
	private String getsCode(long unitsID)throws Exception
	{
		String sCode = "";
		if(unitsID < 10)
		{
			sCode = "00"+unitsID;
		}
		else if(unitsID >= 10 && unitsID < 100)
		{
			sCode = "0"+unitsID;
		}
		else
		{
			sCode = ""+unitsID;
		}
		return sCode;
	}
	private long getMaxID() throws Exception 
	{
		long id = -1;
		Connection localConn = null;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_EXTERIORUNITS");
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
