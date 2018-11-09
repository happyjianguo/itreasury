package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOAN_DepositReceiptInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LOAN_DepositReceiptDAO extends ITreasuryDAO {

	 private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	 
	 public LOAN_DepositReceiptDAO()
	 {
		 super("LOAN_DEPOSITRECEIPT");
	 }
	 
	 public LOAN_DepositReceiptDAO(Connection conn)
	 {
		 super("LOAN_DEPOSITRECEIPT",conn);
	 }
	 
	 public long insert(LOAN_DepositReceiptInfo loan_DepositReceiptInfo)throws ITreasuryDAOException
	 {
		 int result = 0;
		 StringBuffer stringBufferSQL = new StringBuffer();
		 long maxID = getMaxID();
		 try{
			 
			  initDAO();
			 
		 }catch(ITreasuryDAOException e){
			 
			 e.printStackTrace();
			 throw new ITreasuryDAOException("创建数据库连接时出错：",e);
		 }
		 try{
			 
			 stringBufferSQL.append("INSERT INTO LOAN_DEPOSITRECEIPT(ID,WARRANTCODE,WARRANTNAME,WARRANTTYPE,GAGEID,DEPOITRECEIPTNUMBER,BANKNAME,REGULARACCOUNTNAME,");
			 stringBufferSQL.append("REGULARACCOUNTNUMBER,TIMELIMIT,RATE,DEPOSITRECEIPTAMOUNT,STARTDATE,ENDDATE,STATUS,INPUTUSERID,INPUTDATE,OFFICEID,CURRENCYID)");
			 stringBufferSQL.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?)");
             this.prepareStatement(stringBufferSQL.toString());
             this.transPS.setLong(1,maxID);
             this.transPS.setString(2,builCode(LOANConstant.GageType.CD));
             this.transPS.setString(3,LOANConstant.GageType.getName(LOANConstant.GageType.CD));
             this.transPS.setLong(4,LOANConstant.GageType.CD);
             this.transPS.setLong(5,loan_DepositReceiptInfo.getGageID());
             this.transPS.setString(6,loan_DepositReceiptInfo.getDepositReceiptNumber());
             this.transPS.setString(7,loan_DepositReceiptInfo.getBankName());
             this.transPS.setString(8,loan_DepositReceiptInfo.getRegularAccountName());
             this.transPS.setString(9,loan_DepositReceiptInfo.getRegularAccountNumber());
             this.transPS.setString(10,loan_DepositReceiptInfo.getTimeLimit());
             this.transPS.setDouble(11,loan_DepositReceiptInfo.getRate());
             this.transPS.setDouble(12,loan_DepositReceiptInfo.getDepositReceiptAmount());
             this.transPS.setTimestamp(13,loan_DepositReceiptInfo.getStartDate());
             this.transPS.setTimestamp(14,loan_DepositReceiptInfo.getEndDate());
             this.transPS.setLong(15,loan_DepositReceiptInfo.getStatus());
             this.transPS.setLong(16,loan_DepositReceiptInfo.getInputUserID());
             this.transPS.setLong(17,loan_DepositReceiptInfo.getOfficeID());
             this.transPS.setLong(18,loan_DepositReceiptInfo.getCurrencyID());
             result = this.executeUpdate();
             
		 }catch(Exception e2){
			 
			 e2.printStackTrace();
			 throw new ITreasuryDAOException("Gen_E001",e2);
			 
		 }finally{
			 
			 this.finalizeDAO();
		 }
		 return (result>0?maxID:-1);
	 }
	 
	 public long update(LOAN_DepositReceiptInfo loan_DepositReceiptInfo)throws ITreasuryDAOException
	 {
		 int result = 0;
		 StringBuffer stringBufferSQL = new StringBuffer();
		 try{
			 
			  initDAO();
			 
		 }catch(ITreasuryDAOException e){
			 
			 e.printStackTrace();
			 throw new ITreasuryDAOException("创建数据库连接时出错：",e);
		 }
		 try{
			 
			 stringBufferSQL.append("UPDATE LOAN_DEPOSITRECEIPT SET DEPOITRECEIPTNUMBER = ?,BANKNAME = ?,REGULARACCOUNTNAME = ?,REGULARACCOUNTNUMBER = ?,");
			 stringBufferSQL.append("TIMELIMIT = ?,RATE = ?,DEPOSITRECEIPTAMOUNT = ?,STARTDATE = ?,ENDDATE = ? WHERE ID = ?");
             this.prepareStatement(stringBufferSQL.toString());
             this.transPS.setString(1,loan_DepositReceiptInfo.getDepositReceiptNumber());
             this.transPS.setString(2,loan_DepositReceiptInfo.getBankName());
             this.transPS.setString(3,loan_DepositReceiptInfo.getRegularAccountName());
             this.transPS.setString(4,loan_DepositReceiptInfo.getRegularAccountNumber());
             this.transPS.setString(5,loan_DepositReceiptInfo.getTimeLimit());
             this.transPS.setDouble(6,loan_DepositReceiptInfo.getRate());
             this.transPS.setDouble(7,loan_DepositReceiptInfo.getDepositReceiptAmount());
             this.transPS.setTimestamp(8,loan_DepositReceiptInfo.getStartDate());
             this.transPS.setTimestamp(9,loan_DepositReceiptInfo.getEndDate());
             this.transPS.setLong(10,loan_DepositReceiptInfo.getID());

             result = this.executeUpdate();
             
		 }catch(Exception e2){
			 
			 e2.printStackTrace();
			 throw new ITreasuryDAOException("Gen_E001",e2);
			 
		 }finally{
			 
			 this.finalizeDAO();
		 }
		 return (result>0?loan_DepositReceiptInfo.getID():-1);
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
				sbSQL.append(" UPDATE LOAN_DEPOSITRECEIPT SET STATUS = ? WHERE ID = ? ");
				transPS = transConn.prepareStatement(sbSQL.toString());
				transPS.setLong(1,lStatus);
				transPS.setLong(2,ID);
				result = transPS.executeUpdate();

			}catch(Exception e){
				log.error("更新存单权证状态时出错："+e.getMessage());
				e.printStackTrace();
				throw new IException("Gen_E001",e);
			}finally{
				 this.cleanup(transPS);
			 }
			return (result>0?1:-1);
	    }
	 public LOAN_DepositReceiptInfo findByID(long receiptID)throws ITreasuryDAOException
	 {
		 
		 StringBuffer stringBufferSQL = new StringBuffer();
		 LOAN_DepositReceiptInfo loan_DepositReceiptInfo = null;
		 
		 try{
			 
			  initDAO();
			 
		 }catch(ITreasuryDAOException e){
			 
			 e.printStackTrace();
			 throw new ITreasuryDAOException("创建数据库连接时出错：",e);
		 }
		 try{
			 
			 stringBufferSQL.append("SELECT ID,WARRANTCODE,WARRANTNAME,WARRANTTYPE,GAGEID,DEPOITRECEIPTNUMBER,BANKNAME,REGULARACCOUNTNAME,");
			 stringBufferSQL.append("REGULARACCOUNTNUMBER,TIMELIMIT,RATE,DEPOSITRECEIPTAMOUNT,STARTDATE,ENDDATE,STATUS,INPUTUSERID,INPUTDATE,");
			 stringBufferSQL.append("OFFICEID,CURRENCYID,LENDPERSON,LENDDATE,ANTICIPATEDDATE,LENDCAUSE,REALITYDATE FROM LOAN_DEPOSITRECEIPT WHERE ID = ?");
             this.prepareStatement(stringBufferSQL.toString());
             this.transPS.setLong(1,receiptID);
             this.executeQuery();
             if(this.transRS.next() && this.transRS != null)
             {
            	 loan_DepositReceiptInfo = new LOAN_DepositReceiptInfo();
            	 loan_DepositReceiptInfo.setID(transRS.getLong("ID"));
            	 loan_DepositReceiptInfo.setWarrantCode(transRS.getString("WARRANTCODE"));
            	 loan_DepositReceiptInfo.setWarrantName(transRS.getString("WARRANTNAME"));
            	 loan_DepositReceiptInfo.setWarrantType(transRS.getLong("WARRANTTYPE"));
            	 loan_DepositReceiptInfo.setGageID(transRS.getLong("GAGEID"));
            	 loan_DepositReceiptInfo.setDepositReceiptNumber(transRS.getString("DEPOITRECEIPTNUMBER"));
            	 loan_DepositReceiptInfo.setBankName(transRS.getString("BANKNAME"));
            	 loan_DepositReceiptInfo.setRegularAccountName(transRS.getString("REGULARACCOUNTNAME"));
            	 loan_DepositReceiptInfo.setRegularAccountNumber(transRS.getString("REGULARACCOUNTNUMBER"));
            	 loan_DepositReceiptInfo.setTimeLimit(transRS.getString("TIMELIMIT"));
            	 loan_DepositReceiptInfo.setRate(transRS.getDouble("RATE"));
            	 loan_DepositReceiptInfo.setDepositReceiptAmount(transRS.getDouble("DEPOSITRECEIPTAMOUNT"));
            	 loan_DepositReceiptInfo.setStartDate(transRS.getTimestamp("STARTDATE"));
            	 loan_DepositReceiptInfo.setEndDate(transRS.getTimestamp("ENDDATE"));
            	 loan_DepositReceiptInfo.setStatus(transRS.getLong("STATUS"));
            	 loan_DepositReceiptInfo.setInputUserID(transRS.getLong("INPUTUSERID"));
            	 loan_DepositReceiptInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
            	 loan_DepositReceiptInfo.setOfficeID(transRS.getLong("OFFICEID"));
            	 loan_DepositReceiptInfo.setCurrencyID(transRS.getLong("CURRENCYID"));
            	 loan_DepositReceiptInfo.setLendPerson(transRS.getString("LENDPERSON"));
            	 loan_DepositReceiptInfo.setLendDate(transRS.getTimestamp("LENDDATE"));
            	 loan_DepositReceiptInfo.setAnticipatedDate(transRS.getTimestamp("ANTICIPATEDDATE"));
            	 loan_DepositReceiptInfo.setLendCause(transRS.getString("LENDCAUSE"));
            	 loan_DepositReceiptInfo.setRealityDate(transRS.getTimestamp("REALITYDATE"));
            }
             
		 }catch(Exception e2){
			 
			 e2.printStackTrace();
			 throw new ITreasuryDAOException("Gen_E001",e2);
			 
		 }finally{
			 
			 this.finalizeDAO();
		 }
		 return loan_DepositReceiptInfo;
	 }
	 public long updateLendInfo(long lID,String lendPerson,Timestamp lendDate,Timestamp anticiPateDate,String lendCause,Timestamp realiTyDate)throws Exception
	 {
			int result = 0;
			long maxID = -1;
			StringBuffer sbSQL = new StringBuffer();
			try{
				 initDAO();
				 sbSQL.append(" UPDATE  LOAN_DEPOSITRECEIPT SET LENDPERSON = ?,LENDDATE = ?,ANTICIPATEDDATE = ?,");
				 sbSQL.append(" LENDCAUSE = ?,REALITYDATE = ? WHERE ID = ?");
				 prepareStatement(sbSQL.toString());
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
                this.finalizeDAO();
			}
			return (result>0?lID:-1);
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
		    	transPS = transConn.prepareStatement("select nvl(max(substr(warrantcode," + (nLen+1) + ",3)),0)+1 as code from LOAN_DEPOSITRECEIPT where warrantcode like 'S" + strYear + type + "%'");
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
	 
	 private long getMaxID()throws ITreasuryDAOException
	 {
			long id = -1;
			Connection localConn = null;
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			StringBuffer sb = new StringBuffer();
			try{ 
				  // 内部维护RS和PS，否则将会产生冲突,但Connection使用同一个
					localConn = Database.getConnection();
					localPS = localConn.prepareStatement("select nvl(max(ID)+1,1) ID from LOAN_DEPOSITRECEIPT");
					localRS = localPS.executeQuery();
					if (localRS.next()) {
						id = localRS.getLong("ID");
					}
			}catch(Exception e){
				
				e.printStackTrace();
				throw new ITreasuryDAOException("数据库获取担保品ID产生异常",e);
				
			}finally{
				try{
					if(localRS != null)
					{
						localRS.close();
					}
					if(localPS != null)
					{
						localPS.close();
					}
					if(localConn != null)
					{
						localConn.close();
					}
				}catch(Exception e){
						e.printStackTrace();
				}
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
