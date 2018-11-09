package com.iss.itreasury.integratedCredit.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.integratedCredit.dataentity.loanConClusion;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class LoanConClusionDao extends SettlementDAO{

	private static Log4j log4j = null;

	public LoanConClusionDao() {
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertCreditgrade(loanConClusion info)
	throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		long ID=-1;

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();
			System.out.println("ttttttttttttttttttttttt========="+info.getId());
			if(info.getId()<0)
			{
			ID = this.getMaxID(11);
               System.out.println("444444444444444444444444===="+ID);
			strSQL = "insert into loan_conclusion ("
					+ "id,CREDITGRADEID,clientid,OPINION,SORTSINKIN,LONGSINKIN, "
					+ "PAYOFF,SHAPE,WORKING,SUMUP,REMARK"
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getCreditgradeid());
			ps.setLong(n++, info.getClientid());
			ps.setString(n++, info.getOpinion());
			ps.setString(n++, info.getSortsinkin());
			ps.setString(n++, info.getLongsinkin());
			ps.setString(n++, info.getPayoff());
			ps.setString(n++, info.getShape());
			ps.setString(n++, info.getWorking());
			ps.setString(n++, info.getSumup());
			ps.setString(n++, info.getRemark());
			
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			
			

				if (lResult < 0) {
					log.info("insert a loan plan detail fail");
				
				} 
			}else
			{
				// 更新记录
				strSQL = "update loan_conclusion set ";
				if (info.getClientid() > 0)
					strSQL = strSQL + "  clientid=?";
				if (info.getCreditgradeid()> 0)
					strSQL = strSQL + " ,Creditgradeid=?";
				if (info.getOpinion() !=null)
					strSQL = strSQL + " ,Opinion=?";
				if (info.getSortsinkin() !=null)
					strSQL = strSQL + " ,Sortsinkin=?";
				if (info.getLongsinkin()!=null)
					strSQL = strSQL + " ,Longsinkin=?";
				if (info.getShape() !=null)
					strSQL = strSQL + " ,Shape=?";
				if (info.getWorking() !=null)
					strSQL = strSQL + " ,Working=?";
				if (info.getSumup() !=null)
					strSQL = strSQL + " ,Sumup=?";
				
				if (info.getRemark() !=null)
					strSQL = strSQL + " ,Remark=?";
			

				strSQL = strSQL + " where ID=?";
				int nindex = 1;
				ps = conn.prepareStatement(strSQL);

				if (info.getClientid() > 0)
					ps.setLong(nindex++, info.getClientid());
				if (info.getCreditgradeid() > 0)
					ps.setLong(nindex++, info.getCreditgradeid());
				if (info.getOpinion()!=null)
					ps.setString(nindex++, info.getOpinion());
				if (info.getSortsinkin()!=null)
					ps.setString(nindex++, info.getSortsinkin());
				if (info.getLongsinkin() !=null)
					ps.setString(nindex++, info.getLongsinkin());
				if (info.getShape() !=null)
					ps.setString(nindex++, info.getShape());
				if (info.getWorking() !=null)
					ps.setString(nindex++, info.getWorking());
				if (info.getSumup() !=null)
					ps.setString(nindex++, info.getSumup());
				
				if (info.getRemark() !=null)
					ps.setString(nindex++, info.getRemark());
				
				ps.setLong(nindex++, info.getId());

				
				lResult = ps.executeUpdate();
				if (lResult < 0) {
					log.info("update loan create info error:" + lResult);
				}else
					ID =info.getId();
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (ID);
	}
	/**
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	 public loanConClusion findByID(long creditgradeid)throws Exception
	 {
		 loanConClusion info;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection con = null;
	    info = new loanConClusion();
	    try
	    {
	        con = Database.getConnection();
	        StringBuffer sbSQL = new StringBuffer();
	        sbSQL.append(" select * from loan_conclusion");
	        sbSQL.append(" WHERE  Creditgradeid= " + creditgradeid );
	        log.info(sbSQL.toString());
	        ps = con.prepareStatement(sbSQL.toString());
	        rs = ps.executeQuery();
	        if(rs.next())
	        {
	            info.setId(rs.getLong("id"));//ID
	            info.setCreditgradeid(rs.getLong("Creditgradeid"));
	            info.setClientid(rs.getLong("Clientid"));//客户ID
	            info.setOpinion(rs.getString("opinion"));//
	            info.setSortsinkin(rs.getString("Sortsinkin"));
	            info.setLongsinkin(rs.getString("Longsinkin"));
	            info.setPayoff(rs.getString("Payoff"));
	            info.setShape(rs.getString("Shape"));
	            info.setSumup(rs.getString("Sumup"));
	            info.setWorking(rs.getString("Working"));
	            info.setRemark(rs.getString("Remark"));
	            
	        }
	        if(rs != null)
	        {
	            rs.close();
	            rs = null;
	        }
	        if(ps != null)
	        {
	            ps.close();
	            ps = null;
	        }
	        if(con != null)
	        {
	            con.close();
	            con = null;
	        }
	    }
	    catch(Exception e)
	    {
	        log.error(e.toString());
	        throw new IException("Gen_E001");
	    }
	    finally
	    {
	        try
	        {
	            if(rs != null)
	            {
	                rs.close();
	                rs = null;
	            }
	            if(ps != null)
	            {
	                ps.close();
	                ps = null;
	            }
	            if(con != null)
	            {
	                con.close();
	                con = null;
	            }
	        }
	        catch(Exception e)
	        {
	            log.error(e.toString());
	            throw new IException("Gen_E001");
	        }
	    }
	    return info;
	}
	 
	 /**
		 * 
		 * @param lID
		 * @return
		 * @throws Exception
		 */
		 public loanConClusion findByID2(long id)throws Exception 
		 {
			 loanConClusion info;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    Connection con = null;
		    info = new loanConClusion();
		    try
		    {
		        con = Database.getConnection();
		        StringBuffer sbSQL = new StringBuffer();
		        sbSQL.append(" select * from loan_conclusion");
		        sbSQL.append(" WHERE  id= " + id );
		        log.info(sbSQL.toString());
		        ps = con.prepareStatement(sbSQL.toString());
		        rs = ps.executeQuery();
		        if(rs.next())
		        {
		            info.setId(rs.getLong("id"));//ID
		            info.setCreditgradeid(rs.getLong("Creditgradeid"));
		            info.setClientid(rs.getLong("Clientid"));//客户ID
		            info.setOpinion(rs.getString("opinion"));//
		            info.setSortsinkin(rs.getString("Sortsinkin"));
		            info.setLongsinkin(rs.getString("Longsinkin"));
		            info.setPayoff(rs.getString("Payoff"));
		            info.setShape(rs.getString("Shape"));
		            info.setSumup(rs.getString("Sumup"));
		            info.setWorking(rs.getString("Working"));
		            info.setRemark(rs.getString("Remark"));
		            
		        }
		        if(rs != null)
		        {
		            rs.close();
		            rs = null;
		        }
		        if(ps != null)
		        {
		            ps.close();
		            ps = null;
		        }
		        if(con != null)
		        {
		            con.close();
		            con = null;
		        }
		    }
		    catch(Exception e)
		    {
		        log.error(e.toString());
		        throw new IException("Gen_E001");
		    }
		    finally
		    {
		        try
		        {
		            if(rs != null)
		            {
		                rs.close();
		                rs = null;
		            }
		            if(ps != null)
		            {
		                ps.close();
		                ps = null;
		            }
		            if(con != null)
		            {
		                con.close();
		                con = null;
		            }
		        }
		        catch(Exception e)
		        {
		            log.error(e.toString());
		            throw new IException("Gen_E001");
		        }
		    }
		    return info;
		}
	/**
	 * 得到ＩＤ最大值
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getMaxID(long type) throws Exception {
		long lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			
		   strSQL = "select nvl(max(id)+1,1) from  loan_conclusion ";
			
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}
}
