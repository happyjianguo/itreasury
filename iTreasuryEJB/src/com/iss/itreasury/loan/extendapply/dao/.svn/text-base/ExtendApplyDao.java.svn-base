package com.iss.itreasury.loan.extendapply.dao;

/**
 * <p>Title: iTreasury</p>
 * <p>Description: 财务</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iss</p>
 * @author 方远明
 * @version 1.0
 */
import java.util.*;
import java.rmi.RemoteException;
import java.sql.*;

import com.iss.itreasury.loan.extendapply.dataentity.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.*;

public class ExtendApplyDao {
	private static Log4j log4j = null;

	public ExtendApplyDao() {
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	 * Create Date: 2006-3-16
	 * 
	 * 根据合同号lContractID
	 *    币种 lCurrencyID
	 *    办公室 lOfficeID
	 *    看该合同是否有过展期 
	 * 
	 * @param lContractID
	 *            合同ID
	 * @param lCurrencyID 
	 * @param lOfficeID           
	 * @return boolean 存在展期
	 * @exception Exception
	 */	
	public boolean ishaveContract(long lContractID, long lCurrencyID,
			long lOfficeID) throws Exception {
		Connection cnn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try {
			cnn = Database.getConnection();
			if (lContractID > 0 && lCurrencyID > 0 && lOfficeID > 0) {
				sb = new StringBuffer();
				sb.append(" select ID from loan_ExtendForm where 1=1 ");
				sb.append(" and ncontractid = " + lContractID);
				sb.append(" and nstatusid in( 2,3,5)");
				sb.append(" and NCURRENCYID=" + lCurrencyID);
				sb.append(" and NOFFICEID=" + lOfficeID);
				ps = cnn.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					return true;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RemoteException(ex.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (cnn != null) {
					cnn.close();
					cnn = null;
				}
			} catch (Exception ex) {
				throw new RemoteException(ex.getMessage());
			}
		}
		return false;
	}

	/**
	 * 根据合同号得到展期信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 展期信息
	 * @exception Exception
	 */
	public Collection findByContractID(long lContractID) throws Exception {
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select c.nserialno ,a.scode ,");
			sbSQL.append(" min(b.dtextendbegindate) as dtBegin,");
			sbSQL.append(" max(b.dtextendenddate) as dtEnd,");
			sbSQL.append(" sum(b.mextendamount) as extendamount");
			sbSQL.append(" from loan_extendcontract a,");
			sbSQL.append(" loan_extenddetail b,loan_extendform c");
			sbSQL.append(" where a.nextendid = c.id ");
			sbSQL.append(" and a.nstatusid = 3   ");
			sbSQL.append(" and b.nextendformid = c.id  ");
			sbSQL.append(" and c.ncontractid = ?  ");
			sbSQL.append(" group by c.nserialno ,a.scode  ");
			sbSQL.append(" order by c.nserialno ");
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExtendApplyInfo info = new ExtendApplyInfo();
				info.m_dExtendAmount = rs.getDouble("extendamount"); // 展期金额
				info.m_tsExtendBeginDate = rs.getTimestamp("dtBegin"); // 展期起始日期
				info.m_tsExtendEndDate = rs.getTimestamp("dtEnd"); // 展期结束日期
				info.m_lSerialNo = rs.getLong("nserialno"); // 展期申请序列号
				info.m_sExCode = rs.getString("scode"); // 展期合同编号
				vResult.add(info);
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}

	public long getExtendApplyIdByExtendContractId(long lExtendContractID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lReturn = -1;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select nextendid ");
			sbSQL.append(" from loan_extendcontract ");
			sbSQL.append(" where id = ?  ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lExtendContractID);
			rs = ps.executeQuery();
			while (rs.next()) {
				lReturn = rs.getLong("nextendid");

			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}

	/**
	 * 根据合同号得到展期信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 展期信息
	 * @exception Exception
	 */
	public boolean findIfExist(String strSQL) throws Exception {
		boolean bIsExist = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				bIsExist = true;
			} else {
				bIsExist = false;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return bIsExist;
	}

	/**
	 * Method isExistContractPlan.
	 * 
	 * @param lContractID
	 * @param lPlanVersionID
	 * @return long
	 * @throws Exception
	 */
	public long isExistContractPlan(long lContractID, long lPlanVersionID)
			throws Exception {
		long lResult = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		try {
			con = Database.getConnection();
			sbSQL
					.append("select count(*) num from loan_loancontractplan where NCONTRACTID=? and NPLANVERSION>=? ");
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lPlanVersionID);
			rs = ps.executeQuery();
			if (rs.next()) {
				lResult = rs.getLong("num");
				Log.print("==========存在合同计划数目：" + lResult);
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	//更新展期申请状态
	public long updateStatus(long lID, long lStatusID)throws Exception
	{
		long lReturn = -1;	
		Connection con = null;
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{			
			con = Database.getConnection();
			StringBuffer buffer = new 	StringBuffer();
			buffer.append("update loan_extendform set nStatusID=? where ID=?");																		
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++,lStatusID);
			ps.setLong(index++,lID);
			int i=ps.executeUpdate();
			if(i>0)
			{
				lReturn=lID;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{	
			try
			{	
			//	rs.close();				
				ps.close();				
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return lReturn;
	}
	
	//Modify by leiyang date 2007/07/10
	/**
	 * 检查合同状态
	 */
	public long checkContractState(long lExtendApplyID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = "select * from LOAN_EXTENDCONTRACT t where t.nextendid = ? and t.nStatusId not in(?,?,?)";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lExtendApplyID);
			ps.setLong(2, Constant.RecordStatus.INVALID);
			ps.setLong(3, LOANConstant.LoanStatus.CANCEL);
			ps.setLong(4, LOANConstant.ContractStatus.REFUSE);
			rs = ps.executeQuery();
			System.out.println(strSQL);
			
			while(rs.next()) {
				lResult = lResult + 1;
			}
			
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}	
		}
		return lResult;
	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lID
	 * @param lStatusID
	 * @return
	 * @throws Exception
	 */
	public long updateStatusAndCheckStatus(long lID, long lStatusID)throws Exception
	{
		long lReturn = -1;	
		Connection con = null;
		PreparedStatement ps = null;			
		try 
		{			
			con = Database.getConnection();
			String strSql =  "update loan_extendform set nStatusID=? where ID=? and nStatusID=?";																		
			ps = con.prepareStatement(strSql);

			ps.setLong(1,lStatusID);
			ps.setLong(2,lID);
			ps.setLong(3,LOANConstant.ExtendStatus.CHECK);
			
			lReturn = ps.executeUpdate();
		} 
		catch (Exception e) 
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{	
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}				
		}
		return lReturn;
	}
	
	//更新展期合同状态
	public long updateContractStatus(long lID, long lStatusID)throws Exception
	{
		long lReturn = -1;	
		Connection con = null;
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{			
			con = Database.getConnection();
			StringBuffer buffer = new 	StringBuffer();
			buffer.append("update LOAN_EXTENDCONTRACT set nStatusID=? where ID=?");																		
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++,lStatusID);
			ps.setLong(index++,lID);
			int i=ps.executeUpdate();
			if(i>0)
			{
				lReturn=lID;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{	
			try
			{	
			//	rs.close();				
				ps.close();				
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return lReturn;
	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lID
	 * @param lStatusID
	 * @return
	 * @throws Exception
	 */
	public long updateContractStatusAndCheckStatus(long lID, long lStatusID)throws Exception
	{
		long lReturn = -1;	
		Connection con = null;
		PreparedStatement ps = null;			
		try 
		{			
			con = Database.getConnection();
			String strSql =  "update LOAN_EXTENDCONTRACT set nStatusID=? where ID=? and nStatusID=?";																		
			ps = con.prepareStatement(strSql);

			ps.setLong(1,lStatusID);
			ps.setLong(2,lID);
			ps.setLong(3,LOANConstant.LoanStatus.CHECK);
			
			lReturn = ps.executeUpdate();
		} 
		catch (Exception e) 
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{	
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}				
		}
		return lReturn;
	}

	/**
	 * 根据传入的sql语句中包含的合同号找到本合同号的贷款最近一次风险变更生效日期 
	 * 
	 * @param strSQL 
	 * @return String 
	 * @exception Exception
	 */
	public String getLastEffectDate(String strSQL) throws Exception {
		String lastEffectDate = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lastEffectDate = DataFormat.formatDate(rs.getTimestamp("EFFECTDATE"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lastEffectDate;
	}

	
	//
	public static void main(String args[]) {
		try {
			ExtendApplyDao cdao = new ExtendApplyDao();
			cdao.isExistContractPlan(391, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
