/*
 * Created on 2004-8-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.loandrawnotice.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.loandrawnotice.dataentity.LoanDrawNoticeInfo;
import com.iss.itreasury.loan.loandrawnotice.dataentity.YTDrawAllAmountInfo;
import com.iss.itreasury.loan.loandrawnotice.dataentity.YTDrawInfo;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanDrawNoticeDao
{
	private static Log4j log4j = null;

	public LoanDrawNoticeDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
		* 修改银团提款单明细信息
		* Create Date: 2003-10-15
		* @param Collection 银团提款单明细信息
		* @return long 如大于0表示成功，小于等于0表示失败
		* @exception Exception
		*/
	public long updateYTDraw(Collection c) throws Exception
	{
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;
		Iterator it = null;
		YTDrawInfo info = new YTDrawInfo();
		StringBuffer sbSQL = new StringBuffer();

		try
		{
			if (c != null)
			{
				con = Database.getConnection();

				it = c.iterator();
				if (it.hasNext())
				{
					info = (YTDrawInfo) it.next();
					sbSQL.append(" DELETE loan_yt_drawFormBankAssign");
					sbSQL.append(" WHERE nDrawId = ? ");

					log4j.info("del sql = \n" + sbSQL.toString() + "\n DrawId=" + info.getDrawID());

					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, info.getDrawID());
					lResult = ps.executeUpdate();
				}

				Iterator it1 = c.iterator();
				while (it1.hasNext())
				{
					info = (YTDrawInfo) it1.next();
					sbSQL.setLength(0);
					sbSQL.append(" INSERT INTO loan_yt_drawFormBankAssign");
					sbSQL.append(" (nDrawId,"); //提款单id
					sbSQL.append(" nContractID,"); //合同ID
					sbSQL.append(" nIsHead,"); //
					sbSQL.append(" mDrawAmount,"); //提款金额
					sbSQL.append(" mChargeAmount)"); //应付代理费
					sbSQL.append(" VALUES ");
					sbSQL.append("(?,?,?,?,?)");

					log4j.info("add sql = \n" + sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					int nIndex = 1;
					ps.setLong(nIndex++, info.getDrawID());
					ps.setLong(nIndex++, info.getContractID());
					ps.setLong(nIndex++, info.getIsHead());
					ps.setDouble(nIndex++, info.getDrawAmount());
					ps.setDouble(nIndex++, info.getChargeAmount());
					lResult = ps.executeUpdate();
				}
			}

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
		* 得到银团提款单明细信息
		* Create Date: 2003-10-15
		* @param lContractID 提款单id
		* @return Collection
		* @exception Exception
		*/
	public Collection getYTDraw(long lDrawId) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		if (lDrawId <= 0)
		{
			return null;
		}
		
		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,b.sbankname,");
			sbSQL.append(" (b.mcreditamount + b.massureamount) loanAmount,b.mrate ");
			sbSQL.append(" FROM loan_yt_drawFormBankAssign a,");
			sbSQL.append(" loan_YT_LoanContractBankAssign b ");
			sbSQL.append(" WHERE a.nContractID = b.nContractID ");
			sbSQL.append(" AND a.nIsHead = b.nIsHead ");
			sbSQL.append(" AND a.nDrawId = ? ");
			sbSQL.append(" ORDER BY b.nIsHead ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lDrawId);
			rs = ps.executeQuery();

			while (rs.next())
			{
				YTDrawInfo info = new YTDrawInfo();
				info.setIsHead(rs.getLong("nIsHead")); //ID
				info.setBankName(rs.getString("sBankName")); //银行名称
				info.setLoanAmount(rs.getDouble("loanAmount")); //承贷金额
				info.setLoanRate(DataFormat.BigToDouble(rs.getBigDecimal("mRate"),6)); //承贷比例
				info.setDrawAmount(rs.getDouble("mDrawAmount"));//提款金额
				info.setChargeAmount(rs.getDouble("mChargeAmount"));//应付代理费
				vResult.add(info);
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;

	}

	/**
	 * added by mzh_fu 2007/08/18
	 * @param lContractId
	 * @return
	 * @throws Exception
	 */
		
	public double getYTUnDrawAmountByContractId(long lContractId) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		double dReturn = 0.00d;
		
		try
		{
			con = Database.getConnection();
	
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select (lcf.mexamineamount - ");
			sbSQL.append("	  (select nvl(sum(nvl(mAmount,0)),0) sumDrawAmount ");
			sbSQL.append("	   from loan_yt_drawform lyd ");
			sbSQL.append("     where lyd.ncontractid = ?");
			sbSQL.append("           and lyd.nstatusid > 0)) unDrawAmount ");
			sbSQL.append(" from loan_contractform lcf ");
			sbSQL.append(" where lcf.id = ? ");
			sbSQL.append("       and lcf.nstatusid > 0 ");

	
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractId);
			ps.setLong(2, lContractId);
			rs = ps.executeQuery();
	
			if(rs.next()){
				dReturn = rs.getDouble("unDrawAmount");
			}
		}
		catch (Exception e)
		{
			throw new IException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException(e.getMessage());
			}
		}
		return dReturn;
	
	}
	

	/**
	 * added by mzh_fu 2007/08/18
	 * @param lContractId
	 * @return
	 * @throws Exception
	 */
		
	public double getYTDrawAmountByDrawNoticeId(long lDrawNoticeId) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		double dReturn = 0.00d;
		
		try
		{
			con = Database.getConnection();
	
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select mAmount ");
			sbSQL.append("	   from loan_yt_drawform ");
			sbSQL.append("     where id = ?");
	
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lDrawNoticeId);
			rs = ps.executeQuery();
	
			if(rs.next()){
				dReturn = rs.getDouble("mAmount");
			}
		}
		catch (Exception e)
		{
			throw new IException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException(e.getMessage());
			}
		}
		return dReturn;
	
	}
	
	//	added by qhzhou 2007.6.27
	public long updateLoanDrawNoticeStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_yt_drawform  set NSTATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateLoanDrawNoticeStatus error : "
						+ lResult);
				return -1;
			} else {
				return lId;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * @param lId
	 * @param statusId
	 * @return
	 * @throws IException
	 */
	public long updateStatusAndCheckStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			String strSQL = " update loan_yt_drawform  set NSTATUSID = ? where ID = ? and NSTATUSID = ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, LOANConstant.LoanDrawNoticeStatus.CHECK);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	//Modify by leiyang date 2007/07/11
	/**
	 * 校验放款通知单状态
	 */
	public long checkPayNoticeState(long id) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL.append("select * from loan_payform t where t.ndrawnoticeid = ? and t.nStatusId not in(?,?)");
				
			ps = conn.prepareStatement(strSQL.toString());
			ps.setLong(1, id);
			ps.setLong(2, Constant.RecordStatus.INVALID);
			ps.setLong(3, LOANConstant.LoanPayNoticeStatus.REFUSE);


			rs = ps.executeQuery();
			System.out.println(strSQL);
			
			while(rs.next()) {
				lResult = lResult + 1;
			}
			
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
		}
		catch (Exception e) {
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
			e.printStackTrace();
			throw e;
		}
		finally {
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
		}
		return lResult;
	}
	
	/**
	 * 校验银团贷款，每个参与行累计发放金额，不包括本提款单
	 * @param contractID
	 * @param drawNoticeID
	 * @return
	 * @throws Exception
	 */
	public Collection validateYTDrawAllAmountInfo(long contractID,long drawNoticeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Collection coll = new ArrayList();
		try
		{
			con = Database.getConnection();
	
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select b.nishead,b.sbankname,b.nattendbankid,sum(nvl(b.mdrawamount, 0)) mdrawamount, sum(nvl(b.mchargeamount, 0)) mchargeamount \n");
			sbSQL.append("	   from loan_yt_drawform a,(select aa.ndrawid,aa.mdrawamount,aa.mchargeamount,bb.sbankname, bb.nishead,bb.nattendbankid from loan_yt_drawformbankassign     aa,loan_yt_loancontractbankassign bb where bb.nishead = aa.nishead(+) and aa.ncontractid = "+contractID+" and bb.ncontractid = "+contractID+" ) b ");
			if(drawNoticeID<0)
			{
				sbSQL.append("     where a.id = b.ndrawid and a.nstatusid != "+LOANConstant.LoanDrawNoticeStatus.CANCEL+" and a.ncontractid = "+contractID+" group by b.nishead, b.sbankname, b.nattendbankid order by b.nishead");
			}
			else
			{
				sbSQL.append("     where a.id = b.ndrawid and a.nstatusid != "+LOANConstant.LoanDrawNoticeStatus.CANCEL+" and a.ncontractid = "+contractID+" and a.id!="+drawNoticeID+" group by b.nishead, b.sbankname, b.nattendbankid order by b.nishead");
			}
	
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
	
			while(rs.next()){
				YTDrawAllAmountInfo info = new YTDrawAllAmountInfo();
				info.setIsHead(rs.getLong("nishead"));
				info.setBankName(rs.getString("sbankname"));
				info.setAttendBankID(rs.getLong("nattendbankid"));
				info.setDrawAllAmount(rs.getDouble("mdrawamount"));
				info.setChargeAllAmount(rs.getDouble("mchargeamount"));
				coll.add(info);
			}
		}
		catch (Exception e)
		{
			throw new IException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException(e.getMessage());
			}
		}
		return coll;
	
	}
}
