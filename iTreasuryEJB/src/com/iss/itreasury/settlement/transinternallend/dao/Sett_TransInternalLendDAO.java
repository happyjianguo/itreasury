/**
 * Creat by kevin(刘连凯)
 * 2011-07-13
 * 内部拆借业务
 */
package com.iss.itreasury.settlement.transinternallend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transinternallend.dataentity.QueryInternalLendConditionInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.settlement.util.SETTConstant;



public class Sett_TransInternalLendDAO extends SettlementDAO{
	public Sett_TransInternalLendDAO(){	    
	     super ("SETT_TRANSINTERNALLENDING");
	}
	public Sett_TransInternalLendDAO(Connection conn){	    
	     super ("SETT_TRANSINTERNALLENDING",conn);
	}
	
	private final static StringBuffer sbInternalLendSQL = new StringBuffer(128);
	static {
		sbInternalLendSQL.append("ID,NOFFICEID,NCURRENCYID, STRANSNO, NTRANSACTIONTYPEID,\n");
		sbInternalLendSQL.append("MAMOUNT,NLENDINGACCOUNTID,MLENDINGACCOUNTBALANCE, NBRANCHOFFICEID, NRESERVEACCOUNTID, \n");
		sbInternalLendSQL.append("MRESERVEACCOUNTBALANCE,DTINTERESTSTART,DTEXECUTE,DTMODIFY, DTINPUT,\n");
		sbInternalLendSQL.append("NINPUTUSERID,NCHECKUSERID,NSTATUSID, SABSTRACT, SCHECKABSTRACT,NOFFICECLIENTID \n");	
		
	}	
	private static TransInternalLendInfo resultToInfo(ResultSet rset) throws SQLException
	{
		TransInternalLendInfo info = new TransInternalLendInfo();
		info.setId(rset.getLong(1));
		info.setOfficeID(rset.getLong(2));
		info.setCurrencyID(rset.getLong(3));
		info.setTransNO(rset.getString(4) == null ? "" : rset.getString(4));
		info.setTransActionTypeID(rset.getLong(5));
		info.setAmount(rset.getDouble(6));
		info.setLendingAccountID(rset.getLong(7));
		info.setLendingAccountBalance(rset.getDouble(8));
		info.setBranchOfficeID(rset.getLong(9));
		info.setReserveAccountID(rset.getLong(10));
		info.setReserveAccountBalance(rset.getDouble(11));		
		info.setInterestStart(rset.getTimestamp(12));
		info.setExecute(rset.getTimestamp(13));
		info.setModify(rset.getTimestamp(14));
		info.setInputDate(rset.getTimestamp(15));
		info.setInputUserID(rset.getLong(16));
		info.setCheckUserID(rset.getLong(17));
		info.setStatusID(rset.getLong(18));
		info.setAbstract(rset.getString(19));
		info.setCheckAbstract(rset.getString(20));
		info.setOfficeClientID(rset.getLong(21));		
		return info;
		
	}
	public long add(TransInternalLendInfo info) throws SQLException
	{
		long id = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer(128);
		buffer.append("insert into SETT_TRANSINTERNALLENDING \n");
		buffer.append("(  \n");
		buffer.append(sbInternalLendSQL.toString());
		buffer.append(" ) \n");
		buffer.append("\n");
		buffer.append("values \n");
		buffer.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?)");
		try
		{
			conn = this.getConnection();
			id = this.getSett_TransInternalLendingId();
			info.setId(id);
			int index = 1;
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setLong(index++, info.getId());
			pstmt.setLong(index++, info.getOfficeID());
			pstmt.setLong(index++, info.getCurrencyID());
			pstmt.setString(index++, info.getTransNO());
			pstmt.setLong(index++, info.getTransActionTypeID());		
			pstmt.setDouble(index++, info.getAmount());
			pstmt.setLong(index++, info.getLendingAccountID());
			pstmt.setDouble(index++, info.getLendingAccountBalance());
			pstmt.setLong(index++, info.getBranchOfficeID());
			pstmt.setLong(index++, info.getReserveAccountID());
			pstmt.setDouble(index++, info.getReserveAccountBalance());
			pstmt.setTimestamp(index++, info.getInterestStart());
			pstmt.setTimestamp(index++, info.getExecute());			
			pstmt.setTimestamp(index++, info.getInputDate());
			pstmt.setLong(index++, info.getInputUserID());
			pstmt.setLong(index++, info.getCheckUserID());	
			pstmt.setLong(index++, info.getStatusID());		
			pstmt.setString(index++, info.getAbstract());
			pstmt.setString(index++, info.getCheckAbstract());
			pstmt.setLong(index++, info.getOfficeClientID());		
			log.print(buffer.toString());
			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return id;
	}
	public long update(TransInternalLendInfo info) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		int index = 1;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("update SETT_TRANSINTERNALLENDING set \n");
			sbSQL.append("dtModify=sysdate, \n");
			sbSQL.append("NOFFICEID=?,NCURRENCYID=?, STRANSNO=?, NTRANSACTIONTYPEID=?,\n");
			sbSQL.append("MAMOUNT=?,NLENDINGACCOUNTID=?,MLENDINGACCOUNTBALANCE=?, NBRANCHOFFICEID=?, NRESERVEACCOUNTID=?, \n");
			sbSQL.append("MRESERVEACCOUNTBALANCE=?,DTINTERESTSTART=?,DTEXECUTE=?, DTINPUT=?,\n");
			sbSQL.append("NINPUTUSERID=?,NCHECKUSERID=?,NSTATUSID=?, SABSTRACT=?, SCHECKABSTRACT =?,NOFFICECLIENTID=? \n");	
			sbSQL.append("where ID=? \n");
			pstmt = conn.prepareStatement(sbSQL.toString());			
			pstmt.setLong(index++, info.getOfficeID());
			pstmt.setLong(index++, info.getCurrencyID());
			pstmt.setString(index++, info.getTransNO());
			pstmt.setLong(index++, info.getTransActionTypeID());		
			pstmt.setDouble(index++, info.getAmount());
			pstmt.setLong(index++, info.getLendingAccountID());
			pstmt.setDouble(index++, info.getLendingAccountBalance());
			pstmt.setLong(index++, info.getBranchOfficeID());
			pstmt.setLong(index++, info.getReserveAccountID());
			pstmt.setDouble(index++, info.getReserveAccountBalance());
			pstmt.setTimestamp(index++, info.getInterestStart());
			pstmt.setTimestamp(index++, info.getExecute());			
			pstmt.setTimestamp(index++, info.getInputDate());
			pstmt.setLong(index++, info.getInputUserID());
			pstmt.setLong(index++, info.getCheckUserID());	
			pstmt.setLong(index++, info.getStatusID());		
			pstmt.setString(index++, info.getAbstract());
			pstmt.setString(index++, info.getCheckAbstract());
			pstmt.setLong(index++, info.getOfficeClientID());
			pstmt.setLong(index++, info.getId());
			log.debug(sbSQL.toString());
			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}
	/**
	 * 根据id查询全部信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	
	public TransInternalLendInfo findByID(long id) throws SQLException
	{
		TransInternalLendInfo info = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT \n");
			sbSQL.append(sbInternalLendSQL.toString());
			sbSQL.append("FROM SETT_TRANSINTERNALLENDING");
			sbSQL.append(" where id=?");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setLong(1, id);
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				info = resultToInfo(rset);
			}
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return info;
	}	
	/**
	 * 查询满足一定状态集合的数据-链接查找
	 * @param info
	 * @return
	 * @throws Exception
	 */
	
	public Collection findByStatus(QueryInternalLendConditionInfo info)	throws Exception 
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";
			if(info.getStatus()!=null)
			{
				query = getQueryString(info);
			}
			else
			{
				return arrResult;
			}
			
			//排序条件
			String order = getOrderString(info);			
			//业务处理
			if (info.getTypeID() == 0) 
			{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）
								
				buffer.append("select * \n");
				buffer.append("from SETT_TRANSINTERNALLENDING \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");			
				buffer.append(""+ order + "");
				ps = con.prepareStatement(buffer.toString());				
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				
				
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransInternalLendInfo resultInfo = new TransInternalLendInfo();					
					resultInfo = resultToInfo( rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //业务复核
				{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
				
				buffer.append("select * \n");
				buffer.append("from SETT_TRANSINTERNALLENDING \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and nCheckUserID=? and dtExecute=? \n");				
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								

				rs = ps.executeQuery();
				while (rs.next())
				{
					TransInternalLendInfo resultInfo = new TransInternalLendInfo();		
					resultInfo = resultToInfo(rs);
					arrResult.add(resultInfo);

				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;

	}
	/**
	 * 把状态数组转换为sql语句
	 * @param info
	 * @return
	 */
	private String getQueryString(QueryInternalLendConditionInfo info) 
	{
		String query;
		query ="nStatusID=";
		for(int i=0;i<info.getStatus().length;i++)
		{									
			if(i<info.getStatus().length -1)
			{	
								
				query= query+ info.getStatus()[i] + " or nStatusID=";
			}
			else
			{
				query= query+ info.getStatus()[i];
			}
		}	
		return query;
	}
	/**
	 * 根据不同的值选择不同的排序方式
	 * @param info
	 * @return
	 */
	private String getOrderString(QueryInternalLendConditionInfo info) 
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case 1 :			
				order=" ORDER BY STRANSNO ";				
				break;
			case 2 :		
				order=" ORDER BY NLENDINGACCOUNTID ";			
				break;
			case 3 :
				order=" ORDER BY NRESERVEACCOUNTID ";					
				break;
			case 4 :			
				order=" ORDER BY MAMOUNT  ";					
				break;	
			case 5 :
				order=" ORDER BY DTINTERESTSTART ";					
				break;						
			case 6 :
				order=" ORDER BY NSTATUSID ";					
				break;			
			default :
				isNeedOrderBy = false;
				break;
		}
		if(isNeedOrderBy)
		{
			if (info.isDesc())
				order= order +" DESC \n";
			else
				order= order +" ASC \n";		
		}
		else
		{
			order = "ORDER BY ID DESC \n";
		}
		return order;
	}
	/**
	 * 查询机构id为info.getBranchOfficeid,并且ID不为info.getId()的数据
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Collection findOtherByinfo(TransInternalLendInfo info)	throws Exception 
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer buffer = new StringBuffer();								
			buffer.append("select * \n");
			buffer.append("from SETT_TRANSINTERNALLENDING \n");
			buffer.append("where \n");
			buffer.append(" ID!=? \n");
			buffer.append(" and NBRANCHOFFICEID=? \n");
			buffer.append(" and NSTATUSID!=?  \n");	
			buffer.append(" and NTRANSACTIONTYPEID=?  \n");		
			ps = con.prepareStatement(buffer.toString());				
			log.info(buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getId());
			ps.setLong(index++, info.getBranchOfficeID());	
			ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);
			ps.setLong(index++, info.getTransActionTypeID());	
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				TransInternalLendInfo resultInfo = new TransInternalLendInfo();					
				resultInfo = resultToInfo( rs);
				arrResult.add(resultInfo);
			}			 			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;

	}
	/**
	 * 通过账户id查询账户的当前余额或可用余额，当isAvailable为true时查询的是可用余额
	 * @param lAccountID
	 * @param isAvailable
	 * @return
	 * @throws Exception
	 */
	public double getBalanceByAccountID(long lAccountID,boolean isAvailable)throws Exception 
	{
		double dBalance=0.0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer buffer = new StringBuffer();			
			if(isAvailable){
				buffer.append(" select a.MBALANCE-a.MUNCHECKPAYMENTAMOUNT  MBALANCE \n");				
			}else{
				buffer.append(" select a.MBALANCE  MBALANCE \n");
			}
			buffer.append("from sett_SubAccount a, sett_account b \n");
			buffer.append(" where a.NACCOUNTID=b.id \n");
			buffer.append(" and b.id=? \n");
			buffer.append(" and a.NSTATUSID>0 and a.NSTATUSID!=?  \n");			
			ps = con.prepareStatement(buffer.toString());				
			log.print(buffer.toString());
			int index = 1;
			ps.setLong(index++, lAccountID );
			ps.setLong(index++, SETTConstant.SubAccountStatus.FINISH );			
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				dBalance=rs.getDouble(1);
			}			 			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return dBalance;

	}
	public TransInternalLendInfo match(TransInternalLendInfo conditionInfo) throws SQLException
	{
		TransInternalLendInfo info = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append(" SELECT \n");
			sbSQL.append(sbInternalLendSQL.toString());
			sbSQL.append(" FROM SETT_TRANSINTERNALLENDING ");
			sbSQL.append(" where NOFFICEID=? and NCURRENCYID=? AND NTRANSACTIONTYPEID=? AND MAMOUNT=? AND NLENDINGACCOUNTID=?  ");
			sbSQL.append(" AND NRESERVEACCOUNTID=? AND DTINTERESTSTART=? AND NSTATUSID=? AND NINPUTUSERID!=? ");			
			pstmt = conn.prepareStatement(sbSQL.toString());
			log.print(sbSQL.toString());
			int index=1;
			pstmt.setLong(index++, conditionInfo.getOfficeID());
			pstmt.setLong(index++, conditionInfo.getCurrencyID());
			pstmt.setLong(index++, conditionInfo.getTransActionTypeID());
			pstmt.setDouble(index++, conditionInfo.getAmount());
			pstmt.setLong(index++, conditionInfo.getLendingAccountID());
			pstmt.setLong(index++, conditionInfo.getReserveAccountID());
			pstmt.setTimestamp(index++, conditionInfo.getInterestStart());
			pstmt.setLong(index++, conditionInfo.getStatusID());
			pstmt.setLong(index++, conditionInfo.getInputUserID());			
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				info = resultToInfo(rset);
			}
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return info;
	}
	public long updateStatus(long id, long statusId, String strAbstract, long lUserID) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update SETT_TRANSINTERNALLENDING set NSTATUSID=?,SCHECKABSTRACT=?,NCHECKUSERID=?,dtModify=sysdate where ID=? ");
			pstmt.setLong(1, statusId);
			pstmt.setString(2, strAbstract);
			pstmt.setLong(3, lUserID);
			pstmt.setLong(4, id);
			pstmt.executeUpdate();
			this.cleanup(pstmt);
			this.cleanup(conn);
			returnValue = id;
		}
		finally
		{
		    this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}
	public TransInternalLendInfo findByMultipleCondition (TransInternalLendInfo conditionInfo) throws SQLException
	{
		TransInternalLendInfo info = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append(" SELECT \n");
			sbSQL.append(sbInternalLendSQL.toString());
			sbSQL.append(" FROM SETT_TRANSINTERNALLENDING ");
			sbSQL.append(" where 1=1 "); 
			if(conditionInfo.getId()>0)
				sbSQL.append(" and id=? "); 
			if(conditionInfo.getTransActionTypeID()>0)
				sbSQL.append(" and NTRANSACTIONTYPEID=? "); 
			if(conditionInfo.getTransNO()!=null&&conditionInfo.getTransNO().trim().length()>0)
				sbSQL.append(" and STRANSNO=? "); 
			pstmt = conn.prepareStatement(sbSQL.toString());
			log.print(sbSQL.toString());
			int index=1;
			if(conditionInfo.getId()>0)
				pstmt.setLong(index++, conditionInfo.getId());
			if(conditionInfo.getTransActionTypeID()>0)
				pstmt.setLong(index++, conditionInfo.getTransActionTypeID());
			if(conditionInfo.getTransNO()!=null&&conditionInfo.getTransNO().trim().length()>0)
				pstmt.setString(index++, conditionInfo.getTransNO());			
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				info = resultToInfo(rset);
			}
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return info;
	}

}
