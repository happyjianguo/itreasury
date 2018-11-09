package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANAccountReceivableInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOANAccountReceivableDAO{

    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	
	public long insert(LOANAccountReceivableInfo loanAccountReceivableInfo,Connection transConn)throws Exception
	{
		 long maxID = -1;
		 int result = 0;
		 StringBuffer sbSQL = new StringBuffer();
		 PreparedStatement transPS = null;
		 try{
			 
			 maxID = this.getMaxID();
			 sbSQL.append(" INSERT INTO LOAN_ACCOUNTRECEIVABLEINFO(ID,GAGEID,DEBTOR,CONTRACTORINVOICENUMBER,ACCOUNTRECEIVABLEAMOUNT,ACCOUNTRECEIVABLEBALANCE,");
			 sbSQL.append(" RETURNTIME,ACCOUNTNAME,ACCOUNTNUMBER,OPERBANK,TRANSFERNOTICE,ACCOUNTRECEIVABLEPROMISES,OFFICEID,CURRENCY,");
			 sbSQL.append(" INPUTUSERID,INPUTDATE,STATUS)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			 transPS = transConn.prepareStatement(sbSQL.toString());
			 transPS.setLong(1,maxID);
			 transPS.setLong(2,loanAccountReceivableInfo.getGageid());
			 transPS.setLong(3,loanAccountReceivableInfo.getDebtor());
			 transPS.setString(4,loanAccountReceivableInfo.getContractorinvoicenumber());
			 transPS.setDouble(5,loanAccountReceivableInfo.getAccountreceivableamount());
			 transPS.setDouble(6,loanAccountReceivableInfo.getAccountreceivablebalance());
			 transPS.setTimestamp(7,loanAccountReceivableInfo.getReturntime());
			 transPS.setString(8,loanAccountReceivableInfo.getAccountname());
			 transPS.setString(9,loanAccountReceivableInfo.getAccountnumber());
			 transPS.setString(10,loanAccountReceivableInfo.getOperbank());
			 transPS.setLong(11,loanAccountReceivableInfo.getTransfernotice());
			 transPS.setLong(12,loanAccountReceivableInfo.getAccountreceivablepromises());
			 transPS.setLong(13,loanAccountReceivableInfo.getOfficeid());
			 transPS.setLong(14,loanAccountReceivableInfo.getCurrency());
			 transPS.setLong(15,loanAccountReceivableInfo.getInputuserid());
			 transPS.setTimestamp(16,loanAccountReceivableInfo.getInputdate());
			 transPS.setLong(17,loanAccountReceivableInfo.getStatus());
			 result = transPS.executeUpdate();
			 
		 }catch(Exception e){
			 
			 log.error("登记应收款信息时出错："+e.getMessage());
			 e.printStackTrace();
			 throw new IException("Gen_E001",e);
			 
		 }finally{
			 cleanup(transPS);
		 }
		 
		return (result>0?maxID:-1);
	}
	
	
	public long doUpdate(LOANAccountReceivableInfo loanAccountreceivableInfo,Connection transConn)throws Exception
	{
		 StringBuffer sbSQL = new StringBuffer();
		 int result = 0;
		 PreparedStatement transPS = null;
		 try{
			 sbSQL.append(" UPDATE LOAN_ACCOUNTRECEIVABLEINFO SET DEBTOR = ?,CONTRACTORINVOICENUMBER = ?,ACCOUNTRECEIVABLEAMOUNT = ?,ACCOUNTRECEIVABLEBALANCE = ?,");
			 sbSQL.append(" RETURNTIME = ?,ACCOUNTNAME = ?,ACCOUNTNUMBER = ?,OPERBANK = ?,TRANSFERNOTICE = ?,ACCOUNTRECEIVABLEPROMISES = ?");
			 sbSQL.append(" WHERE ID = ?");
			 transPS = transConn.prepareStatement(sbSQL.toString());
			 transPS.setLong(1,loanAccountreceivableInfo.getDebtor());
			 transPS.setString(2,loanAccountreceivableInfo.getContractorinvoicenumber());
			 transPS.setDouble(3,loanAccountreceivableInfo.getAccountreceivableamount());
			 transPS.setDouble(4,loanAccountreceivableInfo.getAccountreceivablebalance());
			 transPS.setTimestamp(5,loanAccountreceivableInfo.getReturntime());
			 transPS.setString(6,loanAccountreceivableInfo.getAccountname());
			 transPS.setString(7,loanAccountreceivableInfo.getAccountnumber());
			 transPS.setString(8,loanAccountreceivableInfo.getOperbank());
			 transPS.setLong(9,loanAccountreceivableInfo.getTransfernotice());
			 transPS.setLong(10,loanAccountreceivableInfo.getAccountreceivablepromises());
			 transPS.setLong(11,loanAccountreceivableInfo.getId());
			 result = transPS.executeUpdate();
			 
		 }catch(Exception e){
			 
			 log.error("更新应收款信息时出错："+e.getMessage());
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
			transPS = transConn.prepareStatement(" UPDATE LOAN_ACCOUNTRECEIVABLEINFO SET STATUS = ? WHERE ID = ? ");
			transPS.setLong(1,lStatus);
			transPS.setLong(2,lID);
			result = transPS.executeUpdate();
		}catch(Exception e){
			log.error("更新应收款状态时出错："+e.getMessage());
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
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_ACCOUNTRECEIVABLEINFO");
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


















