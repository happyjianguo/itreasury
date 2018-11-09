package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANBillInfo;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANNegotiableseCuritiesInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOANBillDAO{

    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	
	public long doInsert(LOANBillInfo loanBillInfo)throws Exception
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
				throw new IException("LOANBillDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			 maxID = this.getMaxID();
			 sbSQL.append(" INSERT INTO LOAN_BILL(ID,GAGEID,BILLTYPE,BILLSUBTYPE,BILLCODE,PAYNAME,PAYBANK,REMITTERNAME,");
			 sbSQL.append(" PAYEENAME,AMOUNT,PAYDATE,ENDDATE,BILLQS,ACCEPTORNAME,INPUTUSERID,INPUTDATE,OFFICEID,CURRENCYID,STATUS,OUTBILLDATE,");
			 sbSQL.append(" WARRANTCODE,WARRANTNAME)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			 transPS = transConn.prepareStatement(sbSQL.toString());
			 transPS.setLong(1,maxID);
			 transPS.setLong(2,loanBillInfo.getGageid());
			 transPS.setLong(3,loanBillInfo.getBilltype());
			 transPS.setLong(4,loanBillInfo.getBillsubtype());
			 transPS.setString(5,loanBillInfo.getBillcode());
			 transPS.setString(6,loanBillInfo.getPayname());
			 transPS.setString(7,loanBillInfo.getPaybank());
			 transPS.setString(8,loanBillInfo.getRemittername());
			 transPS.setString(9,loanBillInfo.getPayeename());
			 transPS.setDouble(10,loanBillInfo.getAmount());
			 transPS.setLong(11,loanBillInfo.getPaydate());
			 transPS.setTimestamp(12,loanBillInfo.getEnddate());
			 transPS.setString(13,loanBillInfo.getBillqs());
			 transPS.setString(14,loanBillInfo.getAcceptorname());
			 transPS.setLong(15,loanBillInfo.getInputuserid());
			 transPS.setTimestamp(16,loanBillInfo.getInputdate());
			 transPS.setLong(17,loanBillInfo.getOfficeid());
			 transPS.setLong(18,loanBillInfo.getCurrencyid());
			 transPS.setLong(19,LOANConstant.GageStatus.SAVE);
			 transPS.setTimestamp(20,loanBillInfo.getOutbilldate());
			 transPS.setString(21,builCode(loanBillInfo.getBilltype()));
			 transPS.setString(22,LOANConstant.GageType.getName(loanBillInfo.getBilltype()));
			 result = transPS.executeUpdate();
			 
		}catch(Exception e){
			
			log.error("保存票据信息时出错："+e.getMessage());
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.cleanup(transPS);
			this.cleanup(transConn);
		}
		
		return result;
	}
	
	
	public long doUpdate(LOANBillInfo loanBillInfo)throws Exception
	{
		long result = 0;
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		try{
				
		       transConn = Database.getConnection();

		}catch(Exception eConn){
				
				eConn.printStackTrace();
				throw new IException("LOANBillDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			 sbSQL.append(" UPDATE LOAN_BILL SET BILLSUBTYPE = ?,BILLCODE = ?,PAYNAME = ?,PAYBANK = ?,REMITTERNAME = ?,");
			 sbSQL.append(" PAYEENAME = ?,AMOUNT = ?,PAYDATE = ?,ENDDATE = ?,BILLQS = ?,ACCEPTORNAME = ?,OUTBILLDATE = ? ");
			 sbSQL.append(" WHERE ID = ? ");
			 transPS = transConn.prepareStatement(sbSQL.toString());
			 transPS.setLong(1,loanBillInfo.getBillsubtype());
			 transPS.setString(2,loanBillInfo.getBillcode());
			 transPS.setString(3,loanBillInfo.getPayname());
			 transPS.setString(4,loanBillInfo.getPaybank());
			 transPS.setString(5,loanBillInfo.getRemittername());
			 transPS.setString(6,loanBillInfo.getPayeename());
			 transPS.setDouble(7,loanBillInfo.getAmount());
			 transPS.setLong(8,loanBillInfo.getPaydate());
			 transPS.setTimestamp(9,loanBillInfo.getEnddate());
			 transPS.setString(10,loanBillInfo.getBillqs());
			 transPS.setString(11,loanBillInfo.getAcceptorname());
			 transPS.setTimestamp(12,loanBillInfo.getOutbilldate());
			 transPS.setLong(13,loanBillInfo.getId());
			 result = transPS.executeUpdate();
             
		}catch(Exception e){
			
			log.error("更新票据信息时出错："+e.getMessage());
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
				throw new IException("LOANBillDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		try{
			 sbSQL.append(" UPDATE  LOAN_BILL SET LENDPERSON = ?,LENDDATE = ?,ANTICIPATEDDATE = ?,");
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
	
	
	
	public LOANBillInfo findByID(long lID)throws Exception
	{	
		Connection transConn = null;
		PreparedStatement transPS = null;
		ResultSet transRS = null;
	    StringBuffer sbSQL = new StringBuffer();
	    LOANBillInfo loanBillInfo = null;
	    try{
			
			 transConn = Database.getConnection();
			 
		}catch(Exception eConn){
			
			eConn.printStackTrace();
			throw new IException("LOANBillDAO---创建数据库链接时出错："+eConn.getMessage());
		}
		   try{
			     sbSQL.append(" SELECT ID,GAGEID,BILLTYPE,BILLSUBTYPE,BILLCODE,PAYNAME,PAYBANK,REMITTERNAME,PAYEENAME,AMOUNT,");
				 sbSQL.append(" PAYDATE,ENDDATE,BILLQS,ACCEPTORNAME,INPUTUSERID,INPUTDATE,OFFICEID,CURRENCYID,STATUS,OUTBILLDATE,");
				 sbSQL.append(" WARRANTCODE,WARRANTNAME,LENDPERSON,LENDDATE,ANTICIPATEDDATE,LENDCAUSE,REALITYDATE FROM LOAN_BILL WHERE ID = ?");
				 transPS = transConn.prepareStatement(sbSQL.toString());
				 transPS.setLong(1,lID);
				 transRS = transPS.executeQuery();
				 if(transRS != null && transRS.next())
				 {
					 loanBillInfo = new LOANBillInfo();
					 loanBillInfo.setId(transRS.getLong("ID"));
					 loanBillInfo.setGageid(transRS.getLong("GAGEID"));
					 loanBillInfo.setBilltype(transRS.getLong("BILLTYPE"));
					 loanBillInfo.setBillsubtype(transRS.getLong("BILLSUBTYPE"));
					 loanBillInfo.setBillcode(transRS.getString("BILLCODE"));
					 loanBillInfo.setPayname(transRS.getString("PAYNAME"));
					 loanBillInfo.setPaybank(transRS.getString("PAYBANK"));
					 loanBillInfo.setRemittername(transRS.getString("REMITTERNAME"));
					 loanBillInfo.setPayeename(transRS.getString("PAYEENAME"));
					 loanBillInfo.setAmount(transRS.getDouble("AMOUNT"));
					 loanBillInfo.setPaydate(transRS.getLong("PAYDATE"));
					 loanBillInfo.setEnddate(transRS.getTimestamp("ENDDATE"));
					 loanBillInfo.setBillqs(transRS.getString("BILLQS"));
					 loanBillInfo.setAcceptorname(transRS.getString("ACCEPTORNAME"));
					 loanBillInfo.setInputuserid(transRS.getLong("INPUTUSERID"));
					 loanBillInfo.setInputdate(transRS.getTimestamp("INPUTDATE"));
					 loanBillInfo.setOfficeid(transRS.getLong("OFFICEID"));
					 loanBillInfo.setCurrencyid(transRS.getLong("CURRENCYID"));
					 loanBillInfo.setStatus(transRS.getLong("STATUS"));
					 loanBillInfo.setOutbilldate(transRS.getTimestamp("OUTBILLDATE"));
					 loanBillInfo.setWarrantcode(transRS.getString("WARRANTCODE"));
					 loanBillInfo.setWarrantname(transRS.getString("WARRANTNAME"));
					 loanBillInfo.setLendPerson(transRS.getString("LENDPERSON"));
					 loanBillInfo.setLendDate(transRS.getTimestamp("LENDDATE"));
					 loanBillInfo.setAnticipateDate(transRS.getTimestamp("ANTICIPATEDDATE"));
					 loanBillInfo.setLendCause(transRS.getString("LENDCAUSE"));
					 loanBillInfo.setRealityDate(transRS.getTimestamp("REALITYDATE"));
				 }
			   
		   }catch(Exception e){
			   
			   log.error("根据ID查找票据权证时出错："+e.getMessage());
			   e.printStackTrace();
			   throw new IException("Gen_E001",e);
			   
		   }finally{
			   this.cleanup(transRS);
			   this.cleanup(transPS);
			   this.cleanup(transConn);
		   }
		   
		   return loanBillInfo;	
	}
	
	
	
	public long updateStatus(long ID,long lStatus,Connection transConn)throws Exception
	{
		    int result = 0;
		    PreparedStatement transPS = null;
			try{
				transPS = transConn.prepareStatement(" UPDATE LOAN_BILL SET STATUS = ? WHERE ID = ? ");
				transPS.setLong(1,lStatus);
				transPS.setLong(2,ID);
				result = transPS.executeUpdate();
			}catch(Exception e){
				log.error("更新票据状态时出错："+e.getMessage());
				e.printStackTrace();
				throw new IException("Gen_E001",e);
			}finally{
                this.cleanup(transPS);
			}
			return (result>0?1:-1);
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
				transPS = transConn.prepareStatement(" UPDATE LOAN_BILL SET STATUS = ? WHERE ID = ? ");
				transPS.setLong(1,lStatus);
				transPS.setLong(2,ID);
				result = transPS.executeUpdate();
			}catch(Exception e){
				log.error("更新票据状态时出错："+e.getMessage());
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
	    	transPS = transConn.prepareStatement("select nvl(max(substr(warrantcode," + (nLen+1) + ",3)),0)+1 as code from LOAN_BILL where warrantcode like 'S" + strYear + type + "%'");
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
	
	private long getMaxID() throws Exception 
	{
		long id = -1;
		Connection localConn = null;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from LOAN_BILL");
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
