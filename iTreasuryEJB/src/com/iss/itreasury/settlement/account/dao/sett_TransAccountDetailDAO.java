package com.iss.itreasury.settlement.account.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-22
 */
public class sett_TransAccountDetailDAO extends SettlementDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public sett_TransAccountDetailDAO()
	{
		super.strTableName = "sett_TransAccountDetail";
	}
	public sett_TransAccountDetailDAO(Connection conn)
	{
		super(conn);
		super.strTableName = "sett_TransAccountDetail";
	}
	public long add(TransAccountDetailInfo info) throws SQLException
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (ID, \n");
		buffer.append(" nOfficeID,\n");
		buffer.append(" nCurrencyID,\n");
		buffer.append(" nTransactionTypeID,\n");

		buffer.append(" dtExecute,\n ");

		buffer.append(" sTransNo,\n");
		buffer.append(" nTransAccountID,\n ");
		buffer.append(" nSubAccountID,\n ");
		buffer.append(" mAmount,\n");
		buffer.append(" nTransDirection,\n ");
		buffer.append(" nOppAccountID,\n ");
		buffer.append(" nOppSubAccountID,\n ");

		buffer.append(" dtInterestStart,\n");

		buffer.append(" sAbstract,\n");
		buffer.append(" nStatusID,\n");
		buffer.append(" nBillTypeID,\n");
		buffer.append(" sBillNo,\n");
		buffer.append(" sBankChequeNo,\n");
		buffer.append(" nGroup,\n");
		buffer.append(" nInterestBackFlag,\n");
		buffer.append(" NABSTRACTID,\n");
		buffer.append(" nSerialNo,  \n");
		buffer.append(" NDISCOUNTBILLID ,  \n");
		buffer.append(" budgetItemID,  \n");
		buffer.append(" budgetStatusID,   \n");
		//		为账户对账单信息查询 所加
		buffer.append(" OPPACCOUNTNO,  \n");
		buffer.append(" OPPACCOUNTNAME,   \n");
		//增加金额类型
		buffer.append(" AMOUNTTYPE )  \n");
		buffer.append(" values(?,?,?,?, \n");
		buffer.append(" to_date('" + DataFormat.getDateString(info.getDtExecute()) + "','yyyy-mm-dd')  \n");
		buffer.append(" ,?,?,?,?,?,?,?, \n");
		buffer.append(" to_date('" + DataFormat.getDateString(info.getDtInterestStart()) + "','yyyy-mm-dd')  \n");
		buffer.append(" ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		log.info(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			long id = this.getNextID();
			info.setId(id);
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getId());
			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setLong(nIndex++, info.getTransactionTypeID());

			//			pstmt.setTimestamp(nIndex++, info.getDtExecute());

			pstmt.setString(nIndex++, info.getTransNo());
			pstmt.setLong(nIndex++, info.getTransAccountID());
			pstmt.setLong(nIndex++, info.getTransSubAccountID());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setLong(nIndex++, info.getTransDirection());
			pstmt.setLong(nIndex++, info.getOppAccountID());
			pstmt.setLong(nIndex++, info.getOppSubAccountID());

			//			pstmt.setTimestamp(nIndex++, info.getDtInterestStart());

			pstmt.setString(nIndex++, info.getAbstractStr());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setLong(nIndex++, info.getBillTypeID());
			pstmt.setString(nIndex++, info.getBillNo());
			pstmt.setString(nIndex++, info.getBankChequeNo());
			pstmt.setLong(nIndex++, info.getGroup());
			pstmt.setLong(nIndex++, info.getInterestBackFlag());
			pstmt.setLong(nIndex++, info.getAbstractID());
			pstmt.setLong(nIndex++, info.getSerialNo());
			pstmt.setLong(nIndex++, info.getDiscountBillID());
			pstmt.setLong(nIndex++, info.getBudgetItemID());
			pstmt.setLong(nIndex++, info.getBudgetStatusID());
			//			为账户对账单信息查询 所加
			pstmt.setString(nIndex++, info.getOppAccountNo());
			pstmt.setString(nIndex++, info.getOppAccountName());
			//增加金额类型
			pstmt.setLong(nIndex++, info.getAmountType());
			//
			pstmt.execute();
			lReturn = id;
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return lReturn;
	}
	private long getNextID() throws SQLException
	{
		try
		{
			return getSett_TransAccountDetailID();
		}
		catch (Exception e)
		{
			throw (SQLException) e;
		}
	}
	public long deleteByTransNo(String stransNo) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" WHERE stransNo = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, SETTConstant.TransactionStatus.DELETED);
			ps.setString(2, stransNo);
			lReturn = ps.executeUpdate();
		}
		finally
		{
			try
			{
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 方法说明：修改状态
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return long  
	 * @throws IException
	 */
	public void updateTransNo(String oldStransNo, String newStransNo, long serialNo) throws SQLException
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("sTransNo = ?,\n");
			strSQLBuffer.append("NGROUP = ?,\n");
			strSQLBuffer.append("nSerialNo = ?\n");
			strSQLBuffer.append(" WHERE sTransNo = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, newStransNo);
			ps.setLong(2, serialNo);
			ps.setLong(3, serialNo);
			ps.setString(4, oldStransNo);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
	}
	/**
	 * 方法说明：根据正式交易号和序列号修改为临时交易号
	 * @param 
	 * @return long  
	 * @throws IException
	 */
	public void updateTempTransNoByTransNoAndSerialNo(String strTransNo,long strSerialNo,String strTempTransNo) throws SQLException
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();

			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("sTransNo = ?,\n");
			strSQLBuffer.append("NGROUP = ?,\n");
			strSQLBuffer.append("nSerialNo = ?\n");
			strSQLBuffer.append(" WHERE sTransNo = ? \n");
			strSQLBuffer.append(" and NGROUP = ?\n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, strTempTransNo);
			ps.setLong(2, -1);
			ps.setLong(3, -1);
			ps.setString(4, strTransNo);
			ps.setLong(5, strSerialNo);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
	}
	
	/**
	 * 功能说明:取出需要倒填的交易账户以及其借贷差额汇总,作为结果集.
	 * 刘惠军 2003-11-20
	 * @param conn  外部调用者提供的连接,不可为空!
	 * @param lOfficeID 办事处
	 * @param lCurrencyID  币种
	 * @param closeDate 关机日期
	 * @return 结果集
	 * @throws Exception
	 */
	public Collection findByCloseDate(long lOfficeID, long lCurrencyID, Timestamp closeDate) throws Exception
	{
		Vector transAccountDetailVector = new Vector();
		TransAccountDetailInfo transAccountDetailInfo = null;
		PreparedStatement ps = null;
		Connection conn = null;

		ResultSet rset = null;
		System.out.println("lhj debug info =======关机日期closeDate是：" + closeDate);
		StringBuffer SQLBuffer = new StringBuffer();
		//select ntransAccountID,nsubAccountID,dtInterestStart,sum(decode(nTransDirection,1,-mAmount,2,mamount)) BackAmount from sett_transAccountDetail where nstatusId=3 and dtExecute ='11/16/2003 00:00:00' and dtInterestStart < dtExecute  and ninterestBackflag<>1 group by nTransAccountID,nSubAccountID,dtInterestStart;
		SQLBuffer.append(" select nTransAccountID,nSubAccountID,dtInterestStart, \n");
		SQLBuffer.append(" sum(decode(nTransDirection,1,-mAmount,2,mAmount)) BackAmount \n");
		SQLBuffer.append(" from sett_TransAccountDetail where nstatusID = 3 and dtExecute =to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd') \n");
		SQLBuffer.append(" and dtInterestStart < dtExecute  and ninterestBackflag <> 1  \n");
		//SQLBuffer.append(" and nOfficeID = ? and nCurrencyID = ?  \n");
		//modify by bingliu 20110813 加入通存通兑（跨机构转账）之后，账户在每个机构发生的账务明细都应该算入
		SQLBuffer.append(" and nCurrencyID = ?  \n");
		SQLBuffer.append(" group by nTransAccountID,nSubAccountID,dtInterestStart \n");
		String sqlString = SQLBuffer.toString();
		log.print("起息日倒填处理在账户交易明细表中查询的SQL语句是:" + sqlString);
		try
		{
			conn = getConnection();
			ps = conn.prepareStatement(sqlString);
			int nIndex = 1;
			//ps.setTimestamp(nIndex++, closeDate);
			//ps.setLong(nIndex++, lOfficeID);
			ps.setLong(nIndex++, lCurrencyID);
			rset = ps.executeQuery();
			while (rset.next())
			{
				transAccountDetailInfo = new TransAccountDetailInfo();
				transAccountDetailInfo.setOfficeID(lOfficeID); //办事处
				transAccountDetailInfo.setCurrencyID(lCurrencyID); //币种
				transAccountDetailInfo.setDtExecute(closeDate); //关机日期
				//交易账户ID
				long transAccountID = rset.getLong("nTransAccountID");
				transAccountDetailInfo.setTransAccountID(transAccountID);
				//交易子账户ID
				long subAccountID = rset.getLong("nSubAccountID");
				transAccountDetailInfo.setTransSubAccountID(subAccountID);
				//起息日
				Timestamp interestStartDate = rset.getTimestamp("dtInterestStart");
				transAccountDetailInfo.setDtInterestStart(interestStartDate);
				//倒填金额
				double backAmount = 0.0;
				backAmount = rset.getDouble("BackAmount");
				transAccountDetailInfo.setAmount(backAmount);
				//放入vector
				transAccountDetailVector.add(transAccountDetailInfo);
			}
			//release the resultSet and  preparedStatement
			cleanup(rset);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new Exception();
		}
		finally
		{
			try
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);

			}
			catch (SQLException e)
			{
				e.printStackTrace();
				e.printStackTrace();throw new Exception();
			}

		}
		return transAccountDetailVector;
	}
	//select dtInterestStart, sum(decode(nTransDirection ,1,-mAmount,2, mAmount)) BackAmount from sett_TransAccountDetail where nStatusID =3 and dtExecute=执行日期 and nTransAccountID =主账户ID and nSubAccountID =子账户ID and dtInterestStart< dtExecute  and nInterestBackflag <>1 group by dtInterestStart
	/**
		 * 功能说明:检查是否需要倒填的交易账户以及其借贷差额汇总,作为结果集.
		 * 刘惠军 2003-11-20
		 * @param conn  外部调用者提供的连接,不可为空!
		 * @param lOfficeID 办事处
		 * @param lCurrencyID  币种
		 * @param closeDate 关机日期
		 * @return 结果集
		 * @throws Exception
		 */
	public Collection findByIsBackward(long lAccountID, long lSubAccountID, long lOfficeID, long lCurrencyID, Timestamp closeDate) throws Exception
	{
		Vector transAccountDetailVector = new Vector();
		TransAccountDetailInfo transAccountDetailInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rset = null;
		StringBuffer SQLBuffer = new StringBuffer();
		//select ntransAccountID,nsubAccountID,dtInterestStart,sum(decode(nTransDirection,1,-mAmount,2,mamount)) BackAmount from sett_transAccountDetail where nstatusId=3 and dtExecute ='11/16/2003 00:00:00' and dtInterestStart < dtExecute  and ninterestBackflag<>1 group by nTransAccountID,nSubAccountID,dtInterestStart;
		//		      
		SQLBuffer.append(" select dtInterestStart, \n");
		SQLBuffer.append(" sum(decode(nTransDirection ,1,-mAmount,2, mAmount)) BackAmount  \n");
		SQLBuffer.append(" from sett_TransAccountDetail where nStatusID =3  \n");
		SQLBuffer.append(" and dtExecute=to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd') \n");
		SQLBuffer.append(" and nTransAccountID =" + lAccountID + " and nSubAccountID =" + lSubAccountID + "\n");
		//SQLBuffer.append(" and nOfficeID = " + lOfficeID + " and nCurrencyID = " + lCurrencyID + "  \n");
		//modify by bingliu 20110813 加入通存通兑（跨机构转账）之后，账户在每个机构发生的账务明细都应该算入
		SQLBuffer.append(" and nCurrencyID = " + lCurrencyID + "  \n");
		SQLBuffer.append(" and dtInterestStart< dtExecute  and nInterestBackflag <>1  \n");
		SQLBuffer.append(" group by dtInterestStart \n");
		String sqlString = SQLBuffer.toString();
		log.print("检查是否倒填处理在账户交易明细表中查询的SQL语句是: ");
		log.print(sqlString);
		try
		{
			conn = getConnection();
			ps = conn.prepareStatement(sqlString);
			int nIndex = 1;
			///ps.setTimestamp(nIndex++, closeDate);
			//			   ps.setLong(nIndex++,lOfficeID);
			//			   ps.setLong(nIndex++,lCurrencyID);
			rset = ps.executeQuery();
			while (rset.next())
			{
				transAccountDetailInfo = new TransAccountDetailInfo();
				transAccountDetailInfo.setOfficeID(lOfficeID); //办事处
				transAccountDetailInfo.setCurrencyID(lCurrencyID); //币种
				transAccountDetailInfo.setDtExecute(closeDate); //关机日期
				//交易账户ID
				//				   long transAccountID = rset.getLong("nTransAccountID");
				transAccountDetailInfo.setTransAccountID(lAccountID);
				//				   //交易子账户ID
				//				   long subAccountID = rset.getLong("nSubAccountID");
				transAccountDetailInfo.setTransSubAccountID(lSubAccountID);
				//起息日
				Timestamp interestStartDate = rset.getTimestamp("dtInterestStart");
				transAccountDetailInfo.setDtInterestStart(interestStartDate);
				//倒填金额
				double backAmount = 0.0;
				backAmount = rset.getDouble("BackAmount");
				transAccountDetailInfo.setAmount(backAmount);
				//放入vector
				transAccountDetailVector.add(transAccountDetailInfo);
			}
			//release the resultSet and  preparedStatement
			cleanup(rset);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			try
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);

			}
			catch (SQLException e)
			{
				throw e;
			}

		}
		return transAccountDetailVector;
	}
	/**
	 * *修改倒填交易的状态为已处理
	 * @author hjliu
	 * 2003-11-25
	 * @param conn 数据库连接，由外部调用者提供，不可为空！
	 * @param nAccountID   主账户
	 * @param nSubAccountID 子账户 
	 * @param nCurrencyID  币种
	 * @param dtInterestStart 起息日（倒填日期）
	 * @param dtExecute 执行日（关机日期）
	 * @return lReturn -1 不成功，1：成功
	 * @throws Exception
	 */
	public long updateInterestBackFlag(long nAccountID, long nSubAccountID, long nCurrencyID, Timestamp dtInterestStart, Timestamp dtExecute) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{

			/*修改倒填交易的状态为已处理*/
			/*update sett_transAccountDetail set nInterestBackFlag = 1 where nStatusID=3 and  dtExecute = to_date('2003-01-21','yyyy-mm-dd')
			 and   dtInterestStart = to_Date('2003-01-12','yyyy-mm-dd') and  nInterestBackFlag <> 1 and nTransAccountID = 105 and nSubAccountID = 197
			 and nCurrencyID= 2
			 */
			conn = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("  nInterestBackFlag = 1 \n");
			strSQLBuffer.append("  WHERE  nStatusID = 3  \n");
			strSQLBuffer.append("  and dtExecute = to_date('" + DataFormat.getDateString(dtExecute) + "','yyyy-mm-dd') \n");
			strSQLBuffer.append("  and dtInterestStart = to_date('" + DataFormat.getDateString(dtInterestStart) + "','yyyy-mm-dd') \n");
			strSQLBuffer.append("  and nInterestBackFlag <> 1  \n");
			strSQLBuffer.append("  and nTransAccountID = " + nAccountID + " \n");
			strSQLBuffer.append("  and nSubAccountID = " + nSubAccountID + " \n");
			strSQLBuffer.append("  and nCurrencyID= " + nCurrencyID);
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = conn.prepareStatement(strSQL);
			//ps.setTimestamp(1, dtExecute);
			//ps.setTimestamp(2, dtInterestStart);
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new Exception();
		}
		finally
		{
			try
			{

				cleanup(ps);
				cleanup(conn);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				e.printStackTrace();throw new Exception();
			}
		}
		return lReturn;
	}

	/**
			 * 功能说明:容错处理，根据账户交易表中的余额对账户余额表进行修补.
			 * 刘惠军 2003-12-14
			 * @param lAccountID 主账户ID
			 * @param lSubAccountID 子账户ID
			 * @param lOfficeID 办事处
			 * @param lCurrencyID  币种
			 * @param closeDate 关机日期
			 * @param Flag 是1：查找余额。否-1：查找计息余额
			 * @return TransAccountDetailInfo
			 * @throws Exception
			 */
	public TransAccountDetailInfo findByFaultTolerance(long lAccountID, long lSubAccountID, long lOfficeID, long lCurrencyID, Timestamp closeDate, long Flag) throws Exception
	{

		TransAccountDetailInfo transAccountDetailInfo = new TransAccountDetailInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rset = null;
		StringBuffer SQLBuffer = new StringBuffer();
		//select ntransAccountID,nsubAccountID,dtInterestStart,sum(decode(nTransDirection,1,-mAmount,2,mamount)) BackAmount from sett_transAccountDetail where nstatusId=3 and dtExecute ='11/16/2003 00:00:00' and dtInterestStart < dtExecute  and ninterestBackflag<>1 group by nTransAccountID,nSubAccountID,dtInterestStart;
		//		      
		SQLBuffer.append(" select dtInterestStart, \n");
		SQLBuffer.append(" sum(decode(nTransDirection ,1,-mAmount,2, mAmount)) BackAmount  \n");
		SQLBuffer.append(" from sett_TransAccountDetail where nStatusID =3  \n");
		if (Flag == SETTConstant.BooleanValue.ISTRUE)
			SQLBuffer.append(" and dtExecute=to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd') \n");
		else
			SQLBuffer.append(" and dtInterestStart=to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd') \n");
		SQLBuffer.append(" and nTransAccountID =" + lAccountID + " and nSubAccountID =" + lSubAccountID + "\n");
		SQLBuffer.append(" and nOfficeID = " + lOfficeID + " and nCurrencyID = " + lCurrencyID + "  \n");
		//modify by bingliu 20110813 加入通存通兑（跨机构转账）之后，账户在每个机构发生的账务明细都应该算入
		SQLBuffer.append(" and nCurrencyID = " + lCurrencyID + "  \n");
		SQLBuffer.append(" group by dtInterestStart \n");
		String sqlString = SQLBuffer.toString();
		log.print("容错处理在账户交易明细表中查询的SQL语句是: ");
		log.print(sqlString);
		try
		{
			conn = getConnection();
			ps = conn.prepareStatement(sqlString);
			int nIndex = 1;
			rset = ps.executeQuery();
			while (rset.next())
			{
				
				transAccountDetailInfo.setOfficeID(lOfficeID); //办事处
				transAccountDetailInfo.setCurrencyID(lCurrencyID); //币种
				transAccountDetailInfo.setDtExecute(closeDate); //关机日期
				transAccountDetailInfo.setTransAccountID(lAccountID);
				transAccountDetailInfo.setTransSubAccountID(lSubAccountID);
				//起息日
				Timestamp interestStartDate = rset.getTimestamp("dtInterestStart");
				transAccountDetailInfo.setDtInterestStart(interestStartDate);
				//倒填金额
				double backAmount = 0.0;
				backAmount = rset.getDouble("BackAmount");
				transAccountDetailInfo.setAmount(backAmount);
			}
			//release the resultSet and  preparedStatement
			cleanup(rset);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new Exception();
		}
		finally
		{
			try
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);

			}
			catch (SQLException e)
			{
				e.printStackTrace();
				e.printStackTrace();throw new Exception();
			}

		}
		return transAccountDetailInfo;
	}
	
	public Vector findByTransNo(String strTransNo,long lOfficeID, long lCurrencyID)throws SettlementDAOException{
		boolean isSelfManagedCon = false;
		ResultSet rs = null;
		Vector rtVector = new Vector();
		if (transConn != null)
		{
			isSelfManagedCon = true;
		}
		TransAccountDetailInfo resultInfo = new TransAccountDetailInfo();
		try
		{
			log.print("======Sett_TransaAccountDetailDAO：findByTransNo() begin======");
			log.print("======ltransID======:" + strTransNo);
			log.print("======lOfficeID======:" + lOfficeID);
			log.print("======lCurrencyID======:" + lCurrencyID);
			
			initDAO();
			
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE STRANSNO = '" + strTransNo +"' \n");
			strSQLBuffer.append(" and NOFFICEID = "+ lOfficeID +"\n");
			strSQLBuffer.append(" and NCURRENCYID = "+lCurrencyID );
			strSQLBuffer.append(" and nstatusID != 0");
			strSQLBuffer.append(" order by NTRANSDIRECTION \n");
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			if(rs==null ){
				
			}else{
				while(rs.next()){
					resultInfo = new TransAccountDetailInfo();
					
					resultInfo.setId(rs.getLong("ID"));
					resultInfo.setOfficeID(rs.getLong("NOFFICEID")); //办事处编号ID
					resultInfo.setCurrencyID(rs.getLong("NCURRENCYID")); //币种
					resultInfo.setTransactionTypeID(rs.getLong("NTRANSACTIONTYPEID")); //交易类型
					
					resultInfo.setDtExecute(rs.getTimestamp("DTEXECUTE"));  //执行日
					resultInfo.setTransNo(rs.getString("STRANSNO"));  //交易编号
					resultInfo.setTransAccountID(rs.getLong("NTRANSACCOUNTID")); //交易账户ID
					resultInfo.setTransSubAccountID(rs.getLong("NSUBACCOUNTID")); //交易子账户ID
					resultInfo.setTransDirection(rs.getLong("NTRANSDIRECTION"));  //交易方向
					resultInfo.setAmount(rs.getDouble("MAMOUNT"));  //交易发生额
					resultInfo.setOppAccountID(rs.getLong("NOPPACCOUNTID"));  //对方账户ID
					resultInfo.setOppSubAccountID(rs.getLong("NOPPSUBACCOUNTID")); //对方子账户ID
					resultInfo.setDtInterestStart(rs.getTimestamp("DTINTERESTSTART"));   //起息日
					
					
					resultInfo.setAbstractID(rs.getLong("NABSTRACTID"));   //摘要id
					resultInfo.setAbstractStr(rs.getString("SABSTRACT"));  //摘要
					resultInfo.setBankChequeNo(rs.getString("SBANKCHEQUENO"));  //银行支票号
					resultInfo.setBillNo(rs.getString("SBILLNO"));          //票据号
					resultInfo.setBillTypeID(rs.getLong("NBILLTYPEID"));    //票据类型
					resultInfo.setDiscountBillID(rs.getLong("NDISCOUNTBILLID"));     
					resultInfo.setGroup(rs.getLong("NGROUP"));              //批组号
					resultInfo.setInterestBackFlag(rs.getLong("NINTERESTBACKFLAG"));   //利息倒填标志
	
					resultInfo.setSerialNo(rs.getLong("NSERIALNO"));   //一付多收序列号
					resultInfo.setStatusID(rs.getLong("NSTATUSID"));
					resultInfo.setDiscountBillID(rs.getLong("NDISCOUNTBILLID"));//
	
					//resultInfo.setCommonOperation(rs.getBoolean(""));
	//				定期存单号(只作为账簿与账户参数传递使用，在数据库中没有对应的字段)
	//				resultInfo.setFixedDepositNo(rs.getString(""));  
	//				放款通知单ID(只作为账簿与账户参数传递使用，在数据库中没有对应的字段)
	//				resultInfo.setLoanNoteID(rs.getLong(""));					
					
					rtVector.add(resultInfo);
				}
			}
			
			rs.close();

			log.print("======Sett_TransaAccountDetailDAO：findByTransNo() begin======");
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("findByTransNo产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("findByTransNo息产生错误", e);
		} 
		finally 
		{
			if (!isSelfManagedCon)
			{
				try
				{
					finalizeDAO();
				}
				catch (ITreasuryDAOException e)
				{
					e.printStackTrace();throw new SettlementDAOException(e);
				}
			}
		}
		return rtVector;
	}
	
	/**
	 * 根据ID修改预算状态
	 * @param id
	 * @param budgetStatusID
	 * @throws SQLException
	 */
	public long updateBudgetStatusID(long id,long budgetStatusID) throws SQLException
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" set budgetStatusID = "+budgetStatusID+" where id="+id+"");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);			
			ps.executeUpdate();
			lReturn = 1;
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return lReturn;
	}
	
	public Collection findByAccountID(long transAccountID, Timestamp startDate, Timestamp endDate) throws SettlementDAOException{
		
		boolean isSelfManagedCon = false;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList(4);

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1 = 1 \n");
		if (transConn != null)
		{
			isSelfManagedCon = true;
		}
		
		try
		{
			log.print("======Sett_TransaAccountDetailDAO：findByAccountID() begin======");			
			
			initDAO();
			
			if(transAccountID > 0) {
				buffer.append(" and nTransAccountID=" + transAccountID + " \n");
			}
			if (startDate != null)
			{
				buffer.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')>= to_date('"
								+ DataFormat.formatDate(startDate) + "','yyyy-mm-dd')");
			}
			if (startDate != null)
			{
				buffer.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')<= to_date('"
								+ DataFormat.formatDate(endDate) + "','yyyy-mm-dd')");
			}
			buffer.append(" order by sTransNo");
			
			log.info("sett_TransAccountDetailDAO[findByAccountID]_SQL:"+buffer.toString());
			
			prepareStatement(buffer.toString());
			rset = executeQuery();
			TransAccountDetailInfo resultInfo = null;

			while(rset.next()){
				resultInfo = new TransAccountDetailInfo();
				
				resultInfo.setId(rset.getLong("ID"));
				resultInfo.setOfficeID(rset.getLong("NOFFICEID")); //办事处编号ID
				resultInfo.setCurrencyID(rset.getLong("NCURRENCYID")); //币种
				resultInfo.setTransactionTypeID(rset.getLong("NTRANSACTIONTYPEID")); //交易类型
				
				resultInfo.setDtExecute(rset.getTimestamp("DTEXECUTE"));  //执行日
				resultInfo.setTransNo(rset.getString("STRANSNO"));  //交易编号
				resultInfo.setTransAccountID(rset.getLong("NTRANSACCOUNTID")); //交易账户ID
				resultInfo.setTransSubAccountID(rset.getLong("NSUBACCOUNTID")); //交易子账户ID
				resultInfo.setTransDirection(rset.getLong("NTRANSDIRECTION"));  //交易方向
				resultInfo.setAmount(rset.getDouble("MAMOUNT"));  //交易发生额
				resultInfo.setOppAccountID(rset.getLong("NOPPACCOUNTID"));  //对方账户ID
				resultInfo.setOppSubAccountID(rset.getLong("NOPPSUBACCOUNTID")); //对方子账户ID
				resultInfo.setDtInterestStart(rset.getTimestamp("DTINTERESTSTART"));   //起息日
				
				
				resultInfo.setAbstractID(rset.getLong("NABSTRACTID"));   //摘要id
				resultInfo.setAbstractStr(rset.getString("SABSTRACT"));  //摘要
				resultInfo.setBankChequeNo(rset.getString("SBANKCHEQUENO"));  //银行支票号
				resultInfo.setBillNo(rset.getString("SBILLNO"));          //票据号
				resultInfo.setBillTypeID(rset.getLong("NBILLTYPEID"));    //票据类型
				resultInfo.setDiscountBillID(rset.getLong("NDISCOUNTBILLID"));     
				resultInfo.setGroup(rset.getLong("NGROUP"));              //批组号
				resultInfo.setInterestBackFlag(rset.getLong("NINTERESTBACKFLAG"));   //利息倒填标志

				resultInfo.setSerialNo(rset.getLong("NSERIALNO"));   //一付多收序列号
				resultInfo.setStatusID(rset.getLong("NSTATUSID"));
				resultInfo.setDiscountBillID(rset.getLong("NDISCOUNTBILLID"));//

				//resultInfo.setCommonOperation(rs.getBoolean(""));
//				定期存单号(只作为账簿与账户参数传递使用，在数据库中没有对应的字段)
//				resultInfo.setFixedDepositNo(rs.getString(""));  
//				放款通知单ID(只作为账簿与账户参数传递使用，在数据库中没有对应的字段)
//				resultInfo.setLoanNoteID(rs.getLong(""));					
				
				list.add(resultInfo);
			}
			
			rset.close();

			log.print("======Sett_TransaAccountDetailDAO：findByAccountID() end======");
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("findByTransNo产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("findByTransNo息产生错误", e);
		} 
		finally 
		{
			if (!isSelfManagedCon)
			{
				try
				{
					finalizeDAO();
				}
				catch (ITreasuryDAOException e)
				{
					e.printStackTrace();throw new SettlementDAOException(e);
				}
			}
		}
		
		if (list.size() == 0) {
			return null;
		}
		
		return list;
	}
	
	/**
	 * 功能说明:取出需要倒填罚息和复利的自营和委贷的账户记录集
	 * @param conn  外部调用者提供的连接,不可为空
	 * @param lOfficeID 办事处
	 * @param lCurrencyID  币种
	 * @param closeDate 关机日期
	 * @return 
	 * @throws Exception
	 */
	public Collection findGuoDianByCloseDate(long lOfficeID, long lCurrencyID, Timestamp closeDate) throws Exception
	{
		Vector transAccountDetailVector = new Vector();
		TransAccountDetailInfo transAccountDetailInfo = null;
		PreparedStatement ps = null;
		Connection conn = null;

		ResultSet rset = null;
		System.out.println("lhj debug info =======关机日期closeDate是：" + closeDate);
		
		StringBuffer SQLBuffer = new StringBuffer();
		SQLBuffer.append(" select sa.id accid, ss.id subaccid, overdue.dtfinedate, overdue.niscompoundinterest, overdue.mfineinterestrate \n");
		SQLBuffer.append(" from sett_account sa, sett_subaccount ss, loan_overdueform overdue \n");
		SQLBuffer.append(" where sa.id = ss.naccountid and ss.al_nloannoteid = overdue.npayformid \n");
		SQLBuffer.append(" and sa.nofficeid = ? and sa.ncurrencyid = ? \n");
		SQLBuffer.append(" and overdue.nofficeid = ? and overdue.ncurrencyid = ? \n");
		SQLBuffer.append(" and ss.al_nloannoteid in (select distinct npayformid from loan_overdueform o where o.nstatusid = ?) \n");
		SQLBuffer.append(" and sa.nstatusid = ? and ss.nstatusid = ? \n");
		SQLBuffer.append(" and ss.al_dtclearoverdue < to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd') \n");
		SQLBuffer.append(" and ( ss.ac_nisnegotiate <> 9 or ss.ac_nisnegotiate is null ) \n");
		SQLBuffer.append(" group by sa.id, ss.id, overdue.dtfinedate, overdue.niscompoundinterest, overdue.mfineinterestrate \n");
		String sqlString = SQLBuffer.toString();
		
		System.out.println("起息日倒填处理在账户交易明细表中查询的SQL语句是:" + sqlString);
		try
		{
			conn = getConnection();
			ps = conn.prepareStatement(sqlString);
			int nIndex = 1;
			
			ps.setLong(nIndex++, lOfficeID);
			ps.setLong(nIndex++, lCurrencyID);
			ps.setLong(nIndex++, lOfficeID);
			ps.setLong(nIndex++, lCurrencyID);
			ps.setLong(nIndex++, LOANConstant.LoanStatus.CHECK);
			ps.setLong(nIndex++, SETTConstant.AccountStatus.NORMAL);
			ps.setLong(nIndex++, SETTConstant.SubAccountStatus.NORMAL);
			
			rset = ps.executeQuery();
			
			while (rset.next())
			{
				transAccountDetailInfo = new TransAccountDetailInfo();
				
				transAccountDetailInfo.setOfficeID(lOfficeID);  //办事处
				transAccountDetailInfo.setCurrencyID(lCurrencyID);  //币种
				transAccountDetailInfo.setDtExecute(closeDate);  //关机日期
				transAccountDetailInfo.setTransAccountID(rset.getLong("accid"));  //交易账户ID
				transAccountDetailInfo.setTransSubAccountID(rset.getLong("subaccid"));  //交易子账户ID
				transAccountDetailInfo.setDtInterestStart(rset.getTimestamp("dtfinedate"));  //罚息开始日期
				transAccountDetailInfo.setTransDirection(rset.getLong("niscompoundinterest"));  //用这个保存是否计算复利
				transAccountDetailInfo.setRate(rset.getDouble("mfineinterestrate"));  //罚息利率
				
				transAccountDetailVector.add(transAccountDetailInfo);
			}
			
			cleanup(rset);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new Exception();
		}
		finally
		{
			try
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				e.printStackTrace();throw new Exception();
			}
		}
		
		return transAccountDetailVector;
	}
}