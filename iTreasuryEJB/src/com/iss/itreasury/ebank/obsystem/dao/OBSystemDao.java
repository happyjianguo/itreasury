package com.iss.itreasury.ebank.obsystem.dao;

/**
 * <p>Title: iTreasury </p>
 * <p>Description: 系统管理部分</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: 曾海燕
 * @version 1.0
 * @Date: 2003-08-13
 */

import java.sql.*;
import java.util.*;
import com.iss.itreasury.ebank.obsystem.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.util.*;

public class OBSystemDao
{
	private static Log4j log4j = null;

	public OBSystemDao()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}

	/**
	 * 查找签认金额设置
	 * Create Date: 2003-8-13
	 * @param lClientID 客户ID
	 * @param lCurrencyID 币种ID
	 * @return SignAmountInfo
	 * @exception Exception
	 */
	public SignAmountInfo querySignAmount(long lClientID, long lCurrencyID) throws Exception
	{
		//定义变量
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lIndex = 0;
		SignAmountInfo info = new SignAmountInfo();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			info.setClientID(lClientID);
			info.setCurrencyID(lCurrencyID);

			sbSQL.append(" SELECT * FROM OB_SignAmount ");
			sbSQL.append(" WHERE  nClientid = " + info.getClientID());
			sbSQL.append(" AND  ncurrencyid = " + info.getCurrencyID());
			sbSQL.append(" ORDER BY mAmount");

			log4j.info(sbSQL.toString());

			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			//取得结果集
			while (rs.next())
			{
				info.setInputUserID(rs.getLong("nsetuserid"));

				if (lIndex == 0)
				{
					info.setAmount1(rs.getDouble("mAmount"));
					info.setSignUserID1(rs.getLong("nSignUserID"));
				}

				if (lIndex == 1)
				{
					info.setAmount2(rs.getDouble("mAmount"));
					info.setSignUserID2(rs.getLong("nSignUserID"));
				}

				if (lIndex == 2)
				{
					info.setAmount3(rs.getDouble("mAmount"));
					info.setSignUserID3(rs.getLong("nSignUserID"));
				}

				lIndex++;
			}

			//释放资源
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
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}

	/**
	 * 保存签认金额设置
	 * Create Date: 2003-8-13
	 * @param SignAmountInfo
	 * @return long
	 * @exception Exception
	 */

	public long addSignAmount(SignAmountInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		int iIndex = 0;
		double[] dAmount = new double[3];
		long[] lSignUserID = new long[3];
		dAmount[0] = 0.00;
		dAmount[1] = 0.00;
		dAmount[2] = 0.00;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" DELETE FROM OB_SignAmount");
			sbSQL.append(" WHERE  nclientid = " + info.getClientID());
			sbSQL.append(" AND  ncurrencyid = " + info.getCurrencyID());

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			lReturn = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}

			if (info.getAmount1() > 0 && info.getSignUserID1() > 0)
			{
				dAmount[0] = info.getAmount1();
				lSignUserID[0] = info.getSignUserID1();
			}

			/* end add */
			if (info.getAmount2() > 0 && info.getSignUserID2() > 0 && info.getAmount2() > info.getAmount1())
			{
				dAmount[1] = info.getAmount2();
				lSignUserID[1] = info.getSignUserID2();
			}

			if (info.getAmount2() > 0 && info.getSignUserID2() > 0 && info.getAmount2() < info.getAmount1())
			{
				throw new IException("OB_EC09");
			}

			if (info.getAmount3() > 0 && info.getSignUserID3() > 0 && info.getAmount3() > info.getAmount2())
			{
				dAmount[2] = info.getAmount3();
				lSignUserID[2] = info.getSignUserID3();
			}

			if (info.getAmount3() > 0 && info.getSignUserID3() > 0 && info.getAmount3() < info.getAmount2())
			{
				throw new IException("OB_EC10");
			}

			for (iIndex = 0; iIndex < 3; iIndex++)
			{
				if (dAmount[iIndex] > 0 && lSignUserID[iIndex] > 0)
				{
					sbSQL.setLength(0);
					sbSQL.append(" INSERT INTO  OB_SignAmount ");
					sbSQL.append(" (nClientID,nCurrencyID,mAmount,nSignUserID,nSetUserID,dtSet)");
					sbSQL.append("  VALUES (?,?,?,?,?,sysdate )");
					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, info.getClientID());
					ps.setLong(2, info.getCurrencyID());
					ps.setDouble(3, dAmount[iIndex]);
					ps.setLong(4, lSignUserID[iIndex]);
					ps.setLong(5, info.getInputUserID());
					lReturn = ps.executeUpdate();
					log4j.info("保存成功！");
				}

				if (ps != null)
				{
					ps.close();
					ps = null;

				}
			}

			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (IException e)
		{
			Log.print(e.toString());
			throw e;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	/**
	 * 查找签认金额设置(新奥--活期)
	 * Create Date: 2011-4-15
	 * @param lClientID 客户ID
	 * @param lCurrencyID 币种ID
	 * @return SignAmountInfo
	 * @exception Exception
	 */
	public SignAmountInfo querySignAmountForCurr(long lClientID, long lCurrencyID) throws Exception
	{
		//定义变量
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lIndex = 0;
		SignAmountInfo info = new SignAmountInfo();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			info.setClientID(lClientID);
			info.setCurrencyID(lCurrencyID);

			sbSQL.append(" SELECT * FROM OB_SignAmount_Curr ");
			sbSQL.append(" WHERE  nClientid = " + info.getClientID());
			sbSQL.append(" AND  ncurrencyid = " + info.getCurrencyID());
			sbSQL.append(" ORDER BY mAmount");

			log4j.info(sbSQL.toString());

			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			//取得结果集
			while (rs.next())
			{
				info.setInputUserID(rs.getLong("nsetuserid"));

				if (lIndex == 0)
				{
					info.setAmount1(rs.getDouble("mAmount"));
					info.setSignUserID1(rs.getLong("nSignUserID"));
				}

				if (lIndex == 1)
				{
					info.setAmount2(rs.getDouble("mAmount"));
					info.setSignUserID2(rs.getLong("nSignUserID"));
				}

				if (lIndex == 2)
				{
					info.setAmount3(rs.getDouble("mAmount"));
					info.setSignUserID3(rs.getLong("nSignUserID"));
				}

				lIndex++;
			}

			//释放资源
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
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}

	/**
	 * 保存签认金额设置（新奥--活期）
	 * Create Date: 2011-4-15
	 * @param SignAmountInfo
	 * @return long
	 * @exception Exception
	 */

	public long addSignAmountForCurr(SignAmountInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		int iIndex = 0;
		double[] dAmount = new double[3];
		long[] lSignUserID = new long[3];
		dAmount[0] = 0.00;
		dAmount[1] = 0.00;
		dAmount[2] = 0.00;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" DELETE FROM OB_SignAmount_Curr");
			sbSQL.append(" WHERE  nclientid = " + info.getClientID());
			sbSQL.append(" AND  ncurrencyid = " + info.getCurrencyID());

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			lReturn = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}

			if (info.getAmount1() > 0 && info.getSignUserID1() > 0)
			{
				dAmount[0] = info.getAmount1();
				lSignUserID[0] = info.getSignUserID1();
			}

			/* end add */
			if (info.getAmount2() > 0 && info.getSignUserID2() > 0 && info.getAmount2() > info.getAmount1())
			{
				dAmount[1] = info.getAmount2();
				lSignUserID[1] = info.getSignUserID2();
			}

			if (info.getAmount2() > 0 && info.getSignUserID2() > 0 && info.getAmount2() < info.getAmount1())
			{
				throw new IException("OB_EC09");
			}

			if (info.getAmount3() > 0 && info.getSignUserID3() > 0 && info.getAmount3() > info.getAmount2())
			{
				dAmount[2] = info.getAmount3();
				lSignUserID[2] = info.getSignUserID3();
			}

			if (info.getAmount3() > 0 && info.getSignUserID3() > 0 && info.getAmount3() < info.getAmount2())
			{
				throw new IException("OB_EC10");
			}

			for (iIndex = 0; iIndex < 3; iIndex++)
			{
				if (dAmount[iIndex] > 0 && lSignUserID[iIndex] > 0)
				{
					sbSQL.setLength(0);
					sbSQL.append(" INSERT INTO OB_SignAmount_Curr ");
					sbSQL.append(" (nClientID,nCurrencyID,mAmount,nSignUserID,nSetUserID,dtSet)");
					sbSQL.append("  VALUES (?,?,?,?,?,sysdate )");
					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, info.getClientID());
					ps.setLong(2, info.getCurrencyID());
					ps.setDouble(3, dAmount[iIndex]);
					ps.setLong(4, lSignUserID[iIndex]);
					ps.setLong(5, info.getInputUserID());
					lReturn = ps.executeUpdate();
					log4j.info("保存成功！");
				}

				if (ps != null)
				{
					ps.close();
					ps = null;

				}
			}

			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (IException e)
		{
			Log.print(e.toString());
			throw e;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	/**
	 * 查找签认金额设置(新奥--定期)
	 * Create Date: 2011-4-15
	 * @param lClientID 客户ID
	 * @param lCurrencyID 币种ID
	 * @return SignAmountInfo
	 * @exception Exception
	 */
	public SignAmountInfo querySignAmountForFix(long lClientID, long lCurrencyID) throws Exception
	{
		//定义变量
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lIndex = 0;
		SignAmountInfo info = new SignAmountInfo();
		
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			info.setClientID(lClientID);
			info.setCurrencyID(lCurrencyID);
			
			sbSQL.append(" SELECT * FROM OB_SignAmount_Fix ");
			sbSQL.append(" WHERE  nClientid = " + info.getClientID());
			sbSQL.append(" AND  ncurrencyid = " + info.getCurrencyID());
			sbSQL.append(" ORDER BY mAmount");
			
			log4j.info(sbSQL.toString());
			
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			//取得结果集
			while (rs.next())
			{
				info.setInputUserID(rs.getLong("nsetuserid"));
				
				if (lIndex == 0)
				{
					info.setAmount1(rs.getDouble("mAmount"));
					info.setSignUserID1(rs.getLong("nSignUserID"));
				}
				
				if (lIndex == 1)
				{
					info.setAmount2(rs.getDouble("mAmount"));
					info.setSignUserID2(rs.getLong("nSignUserID"));
				}
				
				if (lIndex == 2)
				{
					info.setAmount3(rs.getDouble("mAmount"));
					info.setSignUserID3(rs.getLong("nSignUserID"));
				}
				
				lIndex++;
			}
			
			//释放资源
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
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	
	/**
	 * 保存签认金额设置（新奥--定期）
	 * Create Date: 2011-4-15
	 * @param SignAmountInfo
	 * @return long
	 * @exception Exception
	 */
	
	public long addSignAmountForFix(SignAmountInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		int iIndex = 0;
		double[] dAmount = new double[3];
		long[] lSignUserID = new long[3];
		dAmount[0] = 0.00;
		dAmount[1] = 0.00;
		dAmount[2] = 0.00;
		
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append(" DELETE FROM OB_SignAmount_Fix");
			sbSQL.append(" WHERE  nclientid = " + info.getClientID());
			sbSQL.append(" AND  ncurrencyid = " + info.getCurrencyID());
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			lReturn = ps.executeUpdate();
			
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			
			if (info.getAmount1() > 0 && info.getSignUserID1() > 0)
			{
				dAmount[0] = info.getAmount1();
				lSignUserID[0] = info.getSignUserID1();
			}
			
			/* end add */
			if (info.getAmount2() > 0 && info.getSignUserID2() > 0 && info.getAmount2() > info.getAmount1())
			{
				dAmount[1] = info.getAmount2();
				lSignUserID[1] = info.getSignUserID2();
			}
			
			if (info.getAmount2() > 0 && info.getSignUserID2() > 0 && info.getAmount2() < info.getAmount1())
			{
				throw new IException("OB_EC09");
			}
			
			if (info.getAmount3() > 0 && info.getSignUserID3() > 0 && info.getAmount3() > info.getAmount2())
			{
				dAmount[2] = info.getAmount3();
				lSignUserID[2] = info.getSignUserID3();
			}
			
			if (info.getAmount3() > 0 && info.getSignUserID3() > 0 && info.getAmount3() < info.getAmount2())
			{
				throw new IException("OB_EC10");
			}
			
			for (iIndex = 0; iIndex < 3; iIndex++)
			{
				if (dAmount[iIndex] > 0 && lSignUserID[iIndex] > 0)
				{
					sbSQL.setLength(0);
					sbSQL.append(" INSERT INTO OB_SignAmount_Fix ");
					sbSQL.append(" (nClientID,nCurrencyID,mAmount,nSignUserID,nSetUserID,dtSet)");
					sbSQL.append("  VALUES (?,?,?,?,?,sysdate )");
					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, info.getClientID());
					ps.setLong(2, info.getCurrencyID());
					ps.setDouble(3, dAmount[iIndex]);
					ps.setLong(4, lSignUserID[iIndex]);
					ps.setLong(5, info.getInputUserID());
					lReturn = ps.executeUpdate();
					log4j.info("保存成功！");
				}
				
				if (ps != null)
				{
					ps.close();
					ps = null;
					
				}
			}
			
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (IException e)
		{
			Log.print(e.toString());
			throw e;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	
	////////////////

	/**
	 *  票据查询
	 * Create Date: 2003-8-13
	 * @param queryVoucher 查询信息
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryVoucher(QueryVoucherInfo qrInfo) throws Exception
	{
		//定义变量
		VoucherInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" SELECT a.sBillNo,a.nStatusID,a.dtRegister ");
			sbSQL.append(" FROM sett_BankBill a ");
			sbSQL.append(" WHERE a.nRequireClientID = " + qrInfo.getClientID());
			//			sbSQL.append(" AND  a.nCurrencyID = " + qrInfo.getCurrencyID());

			if (qrInfo.getTypeID() > 0)
			{
				sbSQL.append(" AND a.nTypeID = " + qrInfo.getTypeID());
			}

			if (qrInfo.getStartVoucherNo() != null && !qrInfo.getStartVoucherNo().equals(""))
			{
				sbSQL.append(" AND a.sBillNo  >= '" + qrInfo.getStartVoucherNo() + "'");
			}

			if (qrInfo.getEndVoucherNo() != null && !qrInfo.getEndVoucherNo().equals(""))
			{
				sbSQL.append(" AND a.sBillNo  <= '" + qrInfo.getEndVoucherNo() + "'");
			}

			//申请开始日期
			if (qrInfo.getStartDate() != null && qrInfo.getStartDate().length() > 9)
			{
				sbSQL.append(" AND a.dtRequire >= ? ");
			}
			//申请结束日期
			if (qrInfo.getEndDate() != null && qrInfo.getEndDate().length() > 9)
			{
				sbSQL.append(" AND a.dtRequire <= ? ");
			}

			sbSQL.append("\n ORDER BY a.sBillNo ");

			ps = con.prepareStatement(sbSQL.toString());

			int nIndex = 1;

			if (qrInfo.getStartDate() != null && qrInfo.getStartDate().length() > 9)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qrInfo.getStartDate().substring(0, 10) + " 00:00:00"));
			}

			if (qrInfo.getEndDate() != null && qrInfo.getEndDate().length() > 9)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qrInfo.getEndDate().substring(0, 10) + " 23:59:59"));
			}

			log4j.info(sbSQL.toString());
			rs = ps.executeQuery();

			//取得结果集
			while (rs.next())
			{
				info = new VoucherInfo();
				info.setStatus(rs.getLong("nStatusID"));
				info.setVoucherNo(rs.getString("sBillNo"));
				info.setDate(rs.getTimestamp("dtRegister"));
				vReturn.add(info);
				info = null;
			}

			//释放资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;

		}
		catch (Exception e)
		{
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	/**
	 *  获取所有交易类型
	 * Create Date: 2003-8-13
	 * @param lAccountID  账户ID
	 * @return Collection
	 * @exception Exception
	 */
	public Collection getInitAccountPrvgByAccountNo(String strAccountNo) throws Exception
	{
		//定义变量
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountPrvgInfo info = null;
		Vector vResult = new Vector();
		long lAccountID = -1;
		long lAccountTypeID = -1;

		boolean CAPTRANSFER_SELF = false; //资金划拨-本转
		boolean CAPTRANSFER_BANKPAY = false; //资金划拨-银行付款
		boolean CAPTRANSFER_INTERNALVIREMENT = false; //资金划拨-内部转账
		boolean OPENFIXDEPOSIT = false; //定期开立
		boolean FIXEDTOCURRENTTRANSFER = false; //定期支取
		boolean OPENNOTIFYACCOUNT = false; //通知开立
		boolean NOTIFYDEPOSITDRAW = false; //通知支取
		boolean TRUSTLOANRECEIVE = false; //自营贷款清还
		boolean CONSIGNLOANRECEIVE = false; //委托贷款清还	
		boolean INTERESTFEEPAYMENT = false; //利息费用清还	
		
		//中交，下划
		boolean BANKPAY_DOWNTRANSFER = false;//资金划拨－下划

		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement("select id,NACCOUNTTYPEID from sett_account where sAccountNo = '" + strAccountNo + "'");
			rs = ps.executeQuery();
			if (rs.next())
			{
				lAccountID = rs.getLong("id");
				lAccountTypeID = rs.getLong("NACCOUNTTYPEID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
			        ||SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID))
			{
				CAPTRANSFER_SELF = true; //资金划拨-本转
				CAPTRANSFER_BANKPAY = true; //资金划拨-银行付款
				CAPTRANSFER_INTERNALVIREMENT = true; //资金划拨-内部转账
				OPENFIXDEPOSIT = true; //定期开立
				FIXEDTOCURRENTTRANSFER = true; //定期支取
				OPENNOTIFYACCOUNT = true; //通知开立
				NOTIFYDEPOSITDRAW = true; //通知支取
				TRUSTLOANRECEIVE = true; //自营贷款清还
				CONSIGNLOANRECEIVE = true; //委托贷款清还	
				INTERESTFEEPAYMENT = true; //利息费用清还		
				
				BANKPAY_DOWNTRANSFER =true;//资金划拨－下划
			}
			else if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID ))
			{
				OPENFIXDEPOSIT = true; //定期开立
				FIXEDTOCURRENTTRANSFER = true; //定期支取
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID ))
			{
				OPENNOTIFYACCOUNT = true; //通知开立
				NOTIFYDEPOSITDRAW = true; //通知支取				
			}
			else if (SETTConstant.AccountType.isTrustAccountType(lAccountTypeID ))
			{
				//TRUSTLOANRECEIVE = true; //自营贷款清还
				//INTERESTFEEPAYMENT = true; //利息费用清还					
			}
			else if (SETTConstant.AccountType.isConsignAccountType(lAccountTypeID ))
			{
				//CONSIGNLOANRECEIVE = true; //委托贷款清还
				//INTERESTFEEPAYMENT = true; //利息费用清还					
			}

			if (CAPTRANSFER_SELF == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_SELF);
				info.setValue(CAPTRANSFER_SELF);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			对外付款
			if (CAPTRANSFER_BANKPAY == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
				info.setValue(CAPTRANSFER_BANKPAY);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}
			//中交，资金划拨－下划
			if (BANKPAY_DOWNTRANSFER == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
				info.setTypeID(OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER);
				info.setValue(BANKPAY_DOWNTRANSFER);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			内部转账
			if (CAPTRANSFER_INTERNALVIREMENT == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
				info.setValue(CAPTRANSFER_INTERNALVIREMENT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			定期开立
			if (OPENFIXDEPOSIT == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); //定期存款
				info.setTypeID(OBConstant.SettInstrType.OPENFIXDEPOSIT);
				info.setValue(OPENFIXDEPOSIT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			定期支取
			if (FIXEDTOCURRENTTRANSFER == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); //定期存款
				info.setTypeID(OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER);
				info.setValue(FIXEDTOCURRENTTRANSFER);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			通知开立
			if (OPENNOTIFYACCOUNT == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.NOTISFY); //通知存款
				info.setTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);
				info.setValue(OPENNOTIFYACCOUNT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			通知支取
			if (NOTIFYDEPOSITDRAW == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.NOTISFY); //通知存款
				info.setTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
				info.setValue(NOTIFYDEPOSITDRAW);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			自营贷款清还
			if (TRUSTLOANRECEIVE == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); //贷款清还
				info.setTypeID(OBConstant.SettInstrType.TRUSTLOANRECEIVE);
				info.setValue(TRUSTLOANRECEIVE);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			委托贷款清还
			if (CONSIGNLOANRECEIVE == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); //贷款清还
				info.setTypeID(OBConstant.SettInstrType.CONSIGNLOANRECEIVE);
				info.setValue(CONSIGNLOANRECEIVE);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

			//			利息费用清还
			if (INTERESTFEEPAYMENT == true)
			{
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); //贷款清还
				info.setTypeID(OBConstant.SettInstrType.INTERESTFEEPAYMENT);
				info.setValue(INTERESTFEEPAYMENT);
				info.setAccountID(lAccountID);
				vResult.add(info);
			}

		}
		catch (Exception e)
		{
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		Log.print("++++++++++++++++++++++++++++++++++v.size=" + vResult.size());
		return vResult.size() > 0 ? vResult : null;
	}

	/**
	 *  账户交易类型查询
	 * Create Date: 2003-8-13
	 * @param lAccountID  账户ID
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryAccountPrvg(long lAccountID) throws Exception
	{
		//定义变量
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountPrvgInfo info = null;
		Vector vResult = new Vector();
		long lTempType = -1;
	
		boolean CAPTRANSFER_SELF = false; //资金划拨-本转
		boolean CAPTRANSFER_BANKPAY = false; //资金划拨-银行付款
		boolean CAPTRANSFER_INTERNALVIREMENT = false; //资金划拨-内部转账
		boolean CAPTRANSFER_OTHER = false; //资金划拨-其他
		boolean CAPTRANSFER_FINCOMPANYPAY = false;//资金划拨-银行付款---财司代付
		boolean CAPTRANSFER_PAYSUBACCOUNT = false;//资金划拨-银行付款---拨子账户
		boolean CHILDCAPTRANSFER = false; //下属单位资金划拨
		boolean OPENFIXDEPOSIT = false; //定期开立
		boolean FIXEDTOCURRENTTRANSFER = false; //定期支取
		boolean OPENNOTIFYACCOUNT = false; //通知开立
		boolean NOTIFYDEPOSITDRAW = false; //通知支取
		boolean TRUSTLOANRECEIVE = false; //自营贷款清还
		boolean CONSIGNLOANRECEIVE = false; //委托贷款清还	
		boolean INTERESTFEEPAYMENT = false; //利息费用清还	
		boolean YTLOANRECEIVE = false; //银团贷款清还
		boolean CHANGEFIXDEPOSIT = false; //定期转存
		boolean DRIVEFIXDEPOSIT = false; //定期续存
		boolean APPLYCAPITAL = false; //资金申领
		//中交 资金划拨－下划
		boolean CAPTRANSFER_BANKPAY_DOWNTRANSFER = false; //资金划拨—下划


		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" SELECT nTransType FROM OB_AccountPrvg");
			sbSQL.append(" WHERE  nAccountID = " + lAccountID);

			log4j.info(sbSQL.toString());


			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{

				long lTransType = rs.getLong("nTransType");
				
				if (lTransType == OBConstant.SettInstrType.APPLYCAPITAL)
				{
					APPLYCAPITAL = true;
				}
				
				if (lTransType == OBConstant.SettInstrType.CAPTRANSFER_SELF)
				{
					CAPTRANSFER_SELF = true;
				}

				if (lTransType == OBConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					CAPTRANSFER_BANKPAY = true;
				}
				if (lTransType == OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER )
				{
					CAPTRANSFER_BANKPAY_DOWNTRANSFER = true;
				}
				if (lTransType == OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY)
				{
					CAPTRANSFER_FINCOMPANYPAY = true;
				}
				if (lTransType == OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT)
				{
					CAPTRANSFER_PAYSUBACCOUNT = true;
				}
				if (lTransType == OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
				{
					CAPTRANSFER_INTERNALVIREMENT = true;
				}
				if (lTransType == OBConstant.SettInstrType.CAPTRANSFER_OTHER)
				{
					CAPTRANSFER_OTHER = true;
				}
				if (lTransType == OBConstant.SettInstrType.CHILDCAPTRANSFER)
				{
					CHILDCAPTRANSFER = true;
				}
				if (lTransType == OBConstant.SettInstrType.OPENFIXDEPOSIT)
				{
					OPENFIXDEPOSIT = true;
				}
				if (lTransType == OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER)
				{
					FIXEDTOCURRENTTRANSFER = true;
				}
				if (lTransType == OBConstant.SettInstrType.OPENNOTIFYACCOUNT)
				{
					OPENNOTIFYACCOUNT = true;
				}
				if (lTransType == OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
				{
					NOTIFYDEPOSITDRAW = true;
				}
				if (lTransType == OBConstant.SettInstrType.TRUSTLOANRECEIVE)
				{
					TRUSTLOANRECEIVE = true;
				}
				if (lTransType == OBConstant.SettInstrType.CONSIGNLOANRECEIVE)
				{
					CONSIGNLOANRECEIVE = true;
				}
				if (lTransType == OBConstant.SettInstrType.INTERESTFEEPAYMENT)
				{
					INTERESTFEEPAYMENT = true;
				}
				if (lTransType == OBConstant.SettInstrType.YTLOANRECEIVE)
				{
					YTLOANRECEIVE = true;
				}
				if (lTransType == OBConstant.SettInstrType.CHANGEFIXDEPOSIT)
				{
					CHANGEFIXDEPOSIT = true;
				}
				if (lTransType == OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
				{
					DRIVEFIXDEPOSIT = true;
				}
				
			}
			
				//			本转
//				info = new AccountPrvgInfo();
//				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
//				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_SELF);
//				info.setValue(CAPTRANSFER_SELF);
//				vResult.add(info);
	
				//			对外付款(银行付款)
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
				info.setValue(CAPTRANSFER_BANKPAY);
				vResult.add(info);
				

				//       下划
//				info = new AccountPrvgInfo();
//				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
//				info.setTypeID(OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER);
//				info.setValue(CAPTRANSFER_BANKPAY_DOWNTRANSFER);
//				vResult.add(info);
				//				财司代付
//				info = new AccountPrvgInfo();
//				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
//				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY);
//				info.setValue(CAPTRANSFER_FINCOMPANYPAY);
//				vResult.add(info);
				
				//				拨子账户
//				info = new AccountPrvgInfo();
//				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
//				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT);
//				info.setValue(CAPTRANSFER_PAYSUBACCOUNT);
//				vResult.add(info);
				
				//			内部转账
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
				info.setValue(CAPTRANSFER_INTERNALVIREMENT);
				vResult.add(info);
	
				//			其他
//				info = new AccountPrvgInfo();
//				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
//				info.setTypeID(OBConstant.SettInstrType.CAPTRANSFER_OTHER);
//				info.setValue(CAPTRANSFER_OTHER);
//				vResult.add(info);
				
				//			下属单位资金划拨
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.CAPTRANSFER); //资金划拨
				info.setTypeID(OBConstant.SettInstrType.CHILDCAPTRANSFER);
				info.setValue(CHILDCAPTRANSFER);
				vResult.add(info);
	
				//			定期开立
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); //定期存款
				info.setTypeID(OBConstant.SettInstrType.OPENFIXDEPOSIT);
				info.setValue(OPENFIXDEPOSIT);
				vResult.add(info);
	
				//			定期支取
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); //定期存款
				info.setTypeID(OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER);
				info.setValue(FIXEDTOCURRENTTRANSFER);
				vResult.add(info);
				
				//定期转存
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); //定期存款
				info.setTypeID(OBConstant.SettInstrType.CHANGEFIXDEPOSIT);
				info.setValue(CHANGEFIXDEPOSIT);
				vResult.add(info);
				
				//定期续存
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.FIXED); //定期存款
				info.setTypeID(OBConstant.SettInstrType.DRIVEFIXDEPOSIT);
				info.setValue(DRIVEFIXDEPOSIT);
				vResult.add(info);
				
	
				//			通知开立
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.NOTISFY); //通知存款
				info.setTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);
				info.setValue(OPENNOTIFYACCOUNT);
				vResult.add(info);
	
				//			通知支取
				info = new AccountPrvgInfo();
				info.setGroupID(OBConstant.TransTypeGroupSet.NOTISFY); //通知存款
				info.setTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
				info.setValue(NOTIFYDEPOSITDRAW);
				vResult.add(info);
	
				//			自营贷款清还
				//info = new AccountPrvgInfo();
				//info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); //贷款清还
				//info.setTypeID(OBConstant.SettInstrType.TRUSTLOANRECEIVE);
				//info.setValue(TRUSTLOANRECEIVE);
				//vResult.add(info);
	
				//			委托贷款清还
				//info = new AccountPrvgInfo();
				//info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); //贷款清还
				//info.setTypeID(OBConstant.SettInstrType.CONSIGNLOANRECEIVE);
				//info.setValue(CONSIGNLOANRECEIVE);
				//vResult.add(info);
	
				//			利息费用清还
				//info = new AccountPrvgInfo();
				//info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); //贷款清还
				//info.setTypeID(OBConstant.SettInstrType.INTERESTFEEPAYMENT);
				//info.setValue(INTERESTFEEPAYMENT);
				//vResult.add(info);
				
				//			银团贷款清还
				//info = new AccountPrvgInfo();
				//info.setGroupID(OBConstant.TransTypeGroupSet.LOANREPAYMENT); //贷款清还
				//info.setTypeID(OBConstant.SettInstrType.YTLOANRECEIVE);
				//info.setValue(YTLOANRECEIVE);
				//vResult.add(info);		

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
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}

	/**
	 *  保存账户交易类型设置
	 * Create Date: 2003-8-13
	 * @param Collection  
	 * @return long 大于0表示成功，小于0表示保存失败
	 * @exception Exception
	 */
	public long addAccountPrvg(Collection c) throws Exception
	{
		//定义变量
		Connection con = null;
		PreparedStatement ps = null;
		Iterator it = null;
		AccountPrvgInfo info = null;
		long lReturn = -1;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSql = new StringBuffer();

			if (c != null)
			{
				it = c.iterator();
				it.hasNext();
				info = (AccountPrvgInfo) it.next();

				if (info != null)
				{
					sbSql.append(" DELETE FROM OB_AccountPrvg  WHERE  nAccountID = " + info.getAccountID());
					log4j.info(sbSql.toString());
					ps = con.prepareStatement(sbSql.toString());
					lReturn = ps.executeUpdate();

					if (ps != null)
					{
						ps.close();
						ps = null;
					}
				}

				it = c.iterator();
				for (int i = 0; it.hasNext(); i++)
				{
					info = (AccountPrvgInfo) it.next();

					if (info.getTypeID() != -1)
					{

						sbSql.setLength(0);

						sbSql.append(" INSERT INTO  OB_AccountPrvg ");
						sbSql.append(" (nAccountID,nTransType,nSetUserID,dtSet) ");
						sbSql.append(" values (?,?,?,sysdate )");
						log4j.info(sbSql.toString());

						ps = con.prepareStatement(sbSql.toString());

						ps.setLong(1, info.getAccountID());
						ps.setLong(2, info.getTypeID());
						ps.setLong(3, info.getInputUserID());
						lReturn = ps.executeUpdate();

						if (ps != null)
						{
							ps.close();
							ps = null;
						}
					}
				}
			}

			if (con != null)
			{
				con.close();
				con = null;
			}

		}
		catch (Exception e)
		{
			Log.print(e.toString());
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
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	/**
	 *  查询收款人资料信息（包括财务公司客户以及非财务公司客户） 
	 * Create Date: 2003-8-13
	 * 该方法只匹配收款方名称和账号，对于其他栏位不做限制
	 * @param ClientCapInfo  
	 * @return long 返回收款人资料信息的id
	 * @exception Exception
	 */
	public long findPayee(ClientCapInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = 0;
	
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (info.getCity() == null)
			{
				info.setCity("");
			}
			if (info.getPayeeAccoutNO() == null)
			{
				info.setPayeeAccoutNO("");
			}
			if (info.getPayeeBankNO() == null)
			{
				info.setPayeeBankNO("");
			}
			if (info.getPayeeBankName() == null)
			{
				info.setPayeeBankName("");
			}
			if (info.getPayeeProv() == null)
			{
				info.setPayeeProv("");
			}
			if (info.getPayeeName() == null)
			{
				info.setPayeeName("");
			}

			//只要收款方名称，账号和数据库记录有重复的话，则修改记录
			sbSQL.append(" SELECT id FROM OB_PayeeInfo");
			sbSQL.append(" WHERE  nclientid = " + info.getClientID());
			sbSQL.append(" AND ncurrencyid = " + info.getCurrencyID());
			sbSQL.append(" AND niscpfacct = " + info.getIsCPFAcct());
			sbSQL.append(" AND spayeename = '" + info.getPayeeName() + "' ");
			sbSQL.append(" AND spayeeacctno = '" + info.getPayeeAccoutNO() + "' ");
			//modify by xwhe 2009-04-01
//			sbSQL.append(" AND spayeebankname = '"+ info.getPayeeBankName() +"'");
//			sbSQL.append(" AND spayeeprov='"+info.getPayeeProv()+"'");
//			sbSQL.append(" AND spayeecity='"+info.getCity()+"'");
			sbSQL.append(" AND NSTATUSID = " + OBConstant.RecordStatus.VALID);
			
			if (info.getPayeeBankName()!=null && !info.getPayeeBankName().trim().equals(""))
			{
				sbSQL.append(" and spayeebankname = '"+info.getPayeeBankName()+"'");
			}else{
				sbSQL.append(" and spayeebankname is null");
			}
			
			if (info.getPayeeProv()!=null && !info.getPayeeProv().trim().equals(""))
			{
				sbSQL.append(" and spayeeprov = '"+info.getPayeeProv()+"'");
			}else{
				sbSQL.append(" and spayeeprov is null");
			}
			
			if (info.getCity()!=null && !info.getCity().trim().equals(""))
			{
				sbSQL.append(" and spayeecity = '"+info.getCity()+"'");
			}else{
				sbSQL.append(" and spayeecity is null");
			}
			
			if (info.getSPayeeBankExchangeNO()!=null && !info.getSPayeeBankExchangeNO().trim().equals(""))
			{
				sbSQL.append(" and SPAYEEBANKEXCHANGENO = '"+info.getSPayeeBankExchangeNO()+"'");
			}else{
				sbSQL.append(" and SPAYEEBANKEXCHANGENO is null");
			}
			
			if (info.getSPayeeBankCNAPSNO()!=null && !info.getSPayeeBankCNAPSNO().trim().equals(""))
			{
				sbSQL.append(" and SPAYEEBANKCNAPSNO = '"+info.getSPayeeBankCNAPSNO()+"'");
			}else{
				sbSQL.append(" and SPAYEEBANKCNAPSNO is null");
			}
			
			if (info.getSPayeeBankOrgNO()!=null && !info.getSPayeeBankOrgNO().trim().equals(""))
			{
				sbSQL.append(" and SPAYEEBANKORGNO = '"+info.getSPayeeBankOrgNO()+"'");
			}else{
				sbSQL.append(" and SPAYEEBANKORGNO is null");
			}

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				//已经存在
				lResult = rs.getLong(1);
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
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
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
		return lResult;
	}

	/**
	 *  增 加收款人资料信息（包括财务公司客户以及非财务公司客户） 
	 * Create Date: 2003-8-13
	 * 该方法只匹配收款方名称和账号，对于其他栏位不做限制
	 * @param ClientCapInfo  
	 * @return long 大于0表示成功，小于,等于0表示保存失败
	 * @exception Exception
	 */
	public long addPayee(ClientCapInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lMaxID = -1;
		boolean bExist = false; //是否重复录入
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			lMaxID = findPayee(info);

			if (lMaxID >0)
			{
				//已经存在
				bExist = true;
				
				//修改此记录
				info.setID(lMaxID);
				updatePayee(info);
			}
			else
			{
				bExist = false; //不存在
			}

			if (!bExist)
			{
				sbSQL.append("SELECT NVL(MAX(id),1)+1 FROM OB_PayeeInfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs.next())
				{
					lMaxID = rs.getLong(1);
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

				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO  OB_PayeeInfo ");
				sbSQL.append(" (id,nclientid,ncurrencyid,");
				sbSQL.append(" niscpfacct,spayeename,");
				sbSQL.append(" spayeeacctno,sPayeeBankName,");
				sbSQL.append(" spayeeprov,");
				sbSQL.append(" spayeecity,ninputuserid,dtinput,NSTATUSID,");
				sbSQL.append(" SPAYEEBANKEXCHANGENO,SPAYEEBANKCNAPSNO,SPAYEEBANKORGNO,BANKNAME) ");
				sbSQL.append(" VALUES(");
				sbSQL.append(lMaxID + "," + info.getClientID() + "," + info.getCurrencyID() + ",");
				sbSQL.append(info.getIsCPFAcct() + ",'" + info.getPayeeName() + "','");
				sbSQL.append(info.getPayeeAccoutNO() + "','" + info.getPayeeBankName() + "','");
				sbSQL.append(info.getPayeeProv() + "','");
				sbSQL.append(info.getCity() + "'," + info.getInputUserID() + ",sysdate," + OBConstant.RecordStatus.VALID + ", ");
				sbSQL.append("'"+info.getSPayeeBankExchangeNO()+"',");
				sbSQL.append("'"+info.getSPayeeBankCNAPSNO()+"',");
				sbSQL.append("'"+info.getSPayeeBankOrgNO()+"',");
				sbSQL.append("'"+info.getBankName()+"')");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.executeUpdate();
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
			}

			if (con != null)
			{
				con.close();
				con = null;
			}

		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
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
		return lMaxID;
	}


	/**
	 *  修改收款人资料信息（包括中油客户以及非中油客户）
	 * Create Date: 2003-8-13
	 * @param ClientCapInfo  
	 * @return long 大于0表示成功，小于,等于0表示保存失败
	 * @exception Exception
	 */
	public long updatePayee(ClientCapInfo info) throws Exception
	{
		//定义变量
		String strSQL = null;
		long lResult = -1;
		Connection con = null;
		PreparedStatement ps = null;

		try
		{
			if (info.getID() > 0)
			{
				con = Database.getConnection();

				StringBuffer sbCondition = new StringBuffer();
				sbCondition.append(" DTINPUT = sysdate ");

				if (info.getIsCPFAcct() > 0)
				{
					sbCondition.append(",niscpfacct = ? ");
				}

				if (info.getCity() != null)
				{
					sbCondition.append(",SPAYEECITY = ? ");
				}

				if (info.getPayeeAccoutNO() != null)
				{
					sbCondition.append(",SPAYEEACCTNO = ? ");
				}

				if (info.getPayeeBankName() != null)
				{
					sbCondition.append(",SPAYEEBANKNAME = ? ");
				}

				if (info.getPayeeName() != null)
				{
					sbCondition.append(",SPAYEENAME = ? ");
				}

				if (info.getPayeeProv() != null)
				{
					sbCondition.append(",SPAYEEPROV = ? ");
				}
				
				if(info.getSPayeeBankCNAPSNO()!=null)
				{
					sbCondition.append(",SPAYEEBANKCNAPSNO = ? ");
				}
				
				if(info.getSPayeeBankExchangeNO()!=null)
				{
					sbCondition.append(",SPAYEEBANKEXCHANGENO = ? ");
				}
				
				if(info.getSPayeeBankOrgNO()!=null)
				{
					sbCondition.append(",SPAYEEBANKORGNO = ? ");
				}
				

				strSQL = " UPDATE OB_PayeeInfo SET " + sbCondition.toString() + "WHERE ID=? ";

				log4j.info("strSQL = " + strSQL);
				ps = con.prepareStatement(strSQL);
				int lIndex = 1;

				if (info.getIsCPFAcct() > 0)
				{
					ps.setLong(lIndex++, info.getIsCPFAcct());
				}

				if (info.getCity() != null)
				{
					ps.setString(lIndex++, info.getCity());
				}

				if (info.getPayeeAccoutNO() != null)
				{
					ps.setString(lIndex++, info.getPayeeAccoutNO());
				}

				if (info.getPayeeBankName() != null)
				{
					ps.setString(lIndex++, info.getPayeeBankName());
				}

				if (info.getPayeeName() != null)
				{
					ps.setString(lIndex++, info.getPayeeName());
				}

				if (info.getPayeeProv() != null)
				{
					ps.setString(lIndex++, info.getPayeeProv());
				}
				
				if(info.getSPayeeBankCNAPSNO()!=null)
				{
					ps.setString(lIndex++, info.getSPayeeBankCNAPSNO());
				}
				
				if(info.getSPayeeBankExchangeNO()!=null)
				{
					ps.setString(lIndex++, info.getSPayeeBankExchangeNO());
				}
				
				if(info.getSPayeeBankOrgNO()!=null)
				{
					ps.setString(lIndex++, info.getSPayeeBankOrgNO());
				}
				
				
				

				ps.setLong(lIndex++, info.getID());
				lResult = ps.executeUpdate();

				//关闭资源
				ps.close();
				ps = null;
				con.close();
				con = null;

			}
			else
			{
				lResult = 0;
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
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	 *  增 加收款人资料信息（包括财务公司客户以及非财务公司客户） 
	 * Create Date: 2003-8-13
	 * 该方法只匹配收款方名称、账号、开户省、开户市、开户银行，任何栏位不同，即新增记录
	 * @param ClientCapInfo  
	 * @return long 大于0表示成功，小于,等于0表示保存失败
	 * @exception Exception
	 */
	public long addPayee1(ClientCapInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lMaxID = -1;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (info.getCity() == null)
			{
				info.setCity("");
			}
			if (info.getPayeeAccoutNO() == null)
			{
				info.setPayeeAccoutNO("");
			}
			if (info.getPayeeBankNO() == null)
			{
				info.setPayeeBankNO("");
			}
			if (info.getPayeeBankName() == null)
			{
				info.setPayeeBankName("");
			}
			if (info.getPayeeProv() == null)
			{
				info.setPayeeProv("");
			}
			if (info.getPayeeName() == null)
			{
				info.setPayeeName("");
			}

			//只要收款方名称，账号和数据库记录有重复的话，则修改记录
			sbSQL.append(" SELECT * FROM OB_PayeeInfo");
			sbSQL.append(" WHERE  nclientid = " + info.getClientID());
			sbSQL.append(" AND ncurrencyid = " + info.getCurrencyID());
			sbSQL.append(" AND niscpfacct = " + info.getIsCPFAcct());
			sbSQL.append(" AND spayeename = '" + info.getPayeeName() + "' ");
			sbSQL.append(" AND spayeeacctno = '" + info.getPayeeAccoutNO() + "' ");
			sbSQL.append(" AND NSTATUSID = " + OBConstant.RecordStatus.VALID);
			if (info.getIsCPFAcct() == 2)
			{
				if (info.getPayeeBankName().length() == 0)
				{
					sbSQL.append(" AND (SPAYEEBANKNAME is null or SPAYEEBANKNAME = '') ");
				}
				else
				{
					sbSQL.append(" AND SPAYEEBANKNAME = '" + info.getPayeeBankName() + "' ");
				}
				System.out.println("prov's length is : " + info.getPayeeProv().length());
				if (info.getPayeeProv().length() == 0)
				{
					sbSQL.append(" AND (SPAYEEPROV is null or SPAYEEPROV = '') ");
				}
				else
				{
					sbSQL.append(" AND SPAYEEPROV = '" + info.getPayeeProv() + "' ");
				}
				if (info.getCity().length() == 0)
				{
					sbSQL.append(" AND (SPAYEECITY is null or SPAYEECITY = '') ");
				}
				else
				{
					sbSQL.append(" AND SPAYEECITY = '" + info.getCity() + "' ");
				}
			}

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
				throw new IException("OB_EC25");
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			sbSQL.setLength(0);

			sbSQL.append("SELECT NVL(MAX(id),0)+1 FROM OB_PayeeInfo");
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
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

			sbSQL.setLength(0);
			sbSQL.append(" INSERT INTO  OB_PayeeInfo ");
			sbSQL.append(" (id,nclientid,ncurrencyid,");
			sbSQL.append(" niscpfacct,spayeename,");
			sbSQL.append(" spayeeacctno,sPayeeBankName,");
			sbSQL.append(" spayeeprov,spayeecity,");
			sbSQL.append(" ninputuserid,dtinput,NSTATUSID)");
			sbSQL.append(" VALUES(");
			sbSQL.append(lMaxID + "," + info.getClientID() + "," + info.getCurrencyID() + ",");
			sbSQL.append(info.getIsCPFAcct() + ",'" + info.getPayeeName() + "','");
			sbSQL.append(info.getPayeeAccoutNO() + "','" + info.getPayeeBankName() + "','");
			sbSQL.append(info.getPayeeProv() + "','");
			sbSQL.append(info.getCity() + "'," + info.getInputUserID() + ",sysdate," + OBConstant.RecordStatus.VALID + ") ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.executeUpdate();

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
		catch (IException e)
		{
			log4j.error(e.toString());
			throw e;
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
		return lMaxID;
	}

	/**
	 *  查找所有收款方资料（区分中油内部账户和非内部账户）
	 * Create Date: 2003-8-13
	 * @param lUserID 用户ID
	 * @param lClientID 登录用户所属客户ID
	 * @param lCurrencyID  登录币种
	 * @param lISCPFAccount 是否是中油内部账户，参见常量
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryPayee(long lClientID, long lCurrencyID, long lISCPFAccount) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		ClientCapInfo info = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT * FROM OB_PayeeInfo");
			sbSQL.append(" WHERE nclientid =" + lClientID);
			sbSQL.append(" AND ncurrencyid =" + lCurrencyID);
			sbSQL.append(" AND niscpfacct =" + lISCPFAccount);
			sbSQL.append(" AND NSTATUSID =" + OBConstant.RecordStatus.VALID);
			sbSQL.append(" ORDER BY spayeeacctno asc " );
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				info = new ClientCapInfo();
				info.setClientID(rs.getLong("nclientid"));
				info.setCurrencyID(rs.getLong("ncurrencyid"));
				info.setID(rs.getLong("id"));
				info.setIsCPFAcct(rs.getLong("niscpfacct"));
				info.setCity(rs.getString("spayeecity"));
				info.setPayeeAccoutNO(rs.getString("spayeeacctno"));
				//info.setPayeeBankNO(rs.getString("spayeebankno"));
				info.setPayeeBankName(rs.getString("spayeebankname"));
				info.setPayeeName(rs.getString("spayeename"));
				info.setPayeeProv(rs.getString("spayeeprov"));
				info.setSPayeeBankCNAPSNO(rs.getString("SPAYEEBANKCNAPSNO"));
				info.setSPayeeBankExchangeNO(rs.getString("SPAYEEBANKEXCHANGENO"));
				info.setSPayeeBankOrgNO(rs.getString("SPAYEEBANKORGNO"));

				if (info.getIsCPFAcct() == OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES)
				{
					info.setPayeeProv("--");
					info.setCity("--");
				}
				else
				{
					info.setPayeeBankNO("--");
				}
				vReturn.add(info);
				info = null;
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
		return vReturn.size() > 0 ? vReturn : null;
	}

	/**
	 * 删除一条收款方资料
	 * Create Date: 2003-8-13
	 * @param lID 收款方ID
	 * @return long 大于0表示成功，小于,等于0表示保存失败
	 * @exception Exception
	 */
	public long deletePayee(long lID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		int nIndex = 1;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("UPDATE OB_PAYEEINFO SET NSTATUSID = ? where ID = ?");
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			nIndex = 1;
			ps.setLong(nIndex++, OBConstant.RecordStatus.INVALID);
			ps.setLong(nIndex++, lID);
			lReturn = ps.executeUpdate();

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
		return lReturn;
	}

	/**
	 * 查找一条收款方资料
	 * Create Date: 2003-8-13
	 * @param lID 收款方ID
	 * @return ClientCapInfo
	 * @exception Exception
	 */
	public ClientCapInfo findPayeeInfoByID(long lID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ClientCapInfo info = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" SELECT * FROM OB_PayeeInfo WHERE id =" + lID);

			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				info = new ClientCapInfo();
				info.setClientID(rs.getLong("nclientid"));
				info.setCurrencyID(rs.getLong("ncurrencyid"));
				info.setID(rs.getLong("id"));
				info.setIsCPFAcct(rs.getLong("niscpfacct"));
				info.setCity(rs.getString("spayeecity"));
				info.setPayeeAccoutNO(rs.getString("spayeeacctno"));
				//info.setPayeeBankNO(rs.getString("spayeebankno"));
				info.setPayeeBankName(rs.getString("spayeebankname"));
				info.setPayeeName(rs.getString("spayeename"));
				info.setPayeeProv(rs.getString("spayeeprov"));
				info.setSPayeeBankCNAPSNO(rs.getString("SPAYEEBANKEXCHANGENO"));
				info.setSPayeeBankExchangeNO(rs.getString("SPAYEEBANKCNAPSNO"));
				info.setSPayeeBankOrgNO(rs.getString("SPAYEEBANKORGNO"));

				if (info.getIsCPFAcct() == OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES)
				{
					info.setPayeeProv("--");
					info.setCity("--");
				}
				else
				{
					info.setPayeeBankNO("--");
				}

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
		return info;
	}

	/**
	 * findClient 查找现有客户
	 * 根据客户编号查找现有客户，返回客户详细资料
	 * 操作Client数据表
	 * 查询记录
	 * haoning
	 * @param lClientID String  客户编号的ID
	 * @return ClientInfo  详细的客户信息
	 * @throws RemoteException`
	 */
	public OBClientInfo findClientByID(long lClientID) throws Exception
	{
		String strSQL = null;
		OBClientInfo ci = new OBClientInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lParentCorpID = -1;
		long lOfficeID = -1;
		long lMaxLoanID = -1; //客户贷款调查表的标示
		long lMaxAssureID = -1; //客户担保调查表的标示
		String[] strQTClientName = new String[3];
		float[] fQTScale = new float[3];
		String[] strQTCardNo = new String[3];
		String[] strQTPwd = new String[3];
		try
		{
			//查找客户信息(客户资料
			con = Database.getConnection();
			//查找客户信息
			strSQL = " select a.*  " + " ,b.sName as ParentCorpName " + " ,b2.sName as ParentCorpName2 " + " ,c.sName as OfficeName " + " ,d.sName as LoanClientType "
				//+ " ,e.sName as SettClientType "
	+" from Client a ,Client b,Client b2,Office c  " + " ,LOAN_ClientType d "
				//+ " ,SETT_ClientType e "
	+" where a.NPARENTCORPID1 = b.ID(+) " + " and a.NPARENTCORPID2 = b2.ID(+) " + " and a.NOFFICEID = c.ID(+) " + " and a.NLOANCLIENTTYPEID=d.ID(+) "
				//+ " and a.NSETTCLIENTTYPEID=e.ID(+) "
	+" and a.id=? and a.nStatusID =? ";
			log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lClientID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				log4j.info("取客户资料信息");
				ci.setClientID(rs.getLong("ID"));
				lOfficeID = rs.getLong("NOFFICEID");
				ci.setOfficeName(rs.getString("OfficeName")); //财务公司名称
				ci.setOfficeID(lOfficeID); //客户编号
				ci.setCode(rs.getString("SCODE")); //客户名称
				ci.setName(rs.getString("SNAME"));
				ci.setLicenceCode(rs.getString("SLICENCECODE")); //营业执照
				ci.setCorpNatureID(rs.getLong("NCORPNATUREID")); //企业性质
				//上级主管部门（母公司）
				ci.setParentCorpID(rs.getLong("NPARENTCORPID1")); //ID
				ci.setParentCorpName(rs.getString("ParentCorpName")); //上级单位名称				
				//上级主管部门2（母公司）
				ci.setParentCorpID2(rs.getLong("NPARENTCORPID2"));
				ci.setParentCorpName2(rs.getString("ParentCorpName2")); //上级单位2名称

				ci.setEmail(rs.getString("SEMAIL")); //电子邮件
				if (rs.getString("SPROVINCE") == null)
				{
					log4j.info("省份 is null");
				}
				else
				{
					ci.setProvince(rs.getString("SPROVINCE")); //省份
					//ci.m_strAddress = ci.m_strProvince;
				}
				if (rs.getString("SCITY") != null)
				{
					ci.setCity(rs.getString("SCITY")); //城市SCITY
					//ci.m_strAddress = ci.m_strAddress + ci.m_strCity;
				}
				if (rs.getString("SADDRESS") != null)
				{
					ci.setAddress(rs.getString("SADDRESS"));
				}
				log4j.info("----------测试运行点--1----------");
				ci.setZipCode(rs.getString("SZIPCODE")); //邮编
				ci.setPhone(rs.getString("SPHONE")); //电话
				ci.setFax(rs.getString("SFAX")); //传真
				ci.setIsPartner(rs.getLong("NISPARTNER")); //是否是股东
				ci.setAccount(rs.getString("SACCOUNT")); //财务公司账号
				ci.setBank1(rs.getString("SBANK1")); //开户银行1
				ci.setBank2(rs.getString("SBANK2")); //开户银行2
				ci.setBank3(rs.getString("SBANK3")); //开户银行3
				log4j.info("----------测试运行点----2--------");
				ci.setBankAccount1(rs.getString("SEXTENDACCOUNT1")); //银行账户1
				ci.setBankAccount2(rs.getString("SEXTENDACCOUNT2")); //银行账户2
				ci.setBankAccount3(rs.getString("SEXTENDACCOUNT3")); //银行账户3
				ci.setLoanCardNo(rs.getString("SLOANCARDNO")); //贷款卡号
				ci.setLoanCardPwd(rs.getString("SLOANCARDPWD")); //贷款卡密码
				ci.setCreditLevelID(rs.getLong("NCREDITLEVELID")); //信用等级
				ci.setContacter(rs.getString("SCONTACTER")); //联系人
				log4j.info("----------测试运行点---3---------");
				//客户分类
				ci.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID")); //自营贷款
				ci.setLoanClientType(rs.getString("LoanClientType")); //中文描述
				log4j.info("自营贷款客户分类:" + rs.getString("LoanClientType"));
				ci.setSettClientTypeID(rs.getLong("NSETTCLIENTTYPEID")); //结算
				//ci.setSettClientType(rs.getString("SettClientType"));//中文描述

				ci.setRiskLevelID(rs.getLong("NRISKLEVELID")); //风险级别
				ci.setLegalPerson(rs.getString("SLEGALPERSON")); //法人代表
				ci.setCaptial(rs.getString("SCAPITAL")); //注册资本
				log4j.info("----------测试运行点---4---------");
				//评级单位
				ci.setJudGelevelClient(rs.getString("SJUDGELEVELCLIENT"));
				//经营范围
				ci.setDealScope(rs.getString("SDEALSCOPE"));
				//-------------------------------华能-----------
				//手工录入风险评级(华能专用)
				ci.setRiskLevel(rs.getString("SRISKLEVEL"));
				//机组容量
				ci.setGeneratorCapacity(rs.getString("SGENERATORCAPACITY"));
				ci.setIntelligenceLevel(rs.getString("STALENTLEVEL"));
				ci.setLegalPersonCode(rs.getString("SLEGALPERSONCODECERT"));
				log4j.info("----------测试运行点---5---------");
			}
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
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			//查找控股单位信息
			strSQL = " select * from PARTNEROFCLIENT " + " where nClientID = ? " + "       and nPartnerID > 0 ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				ci.setKGClientID(rs.getLong("nPartnerID")); //控股单位ID
				ci.setKGClientName(rs.getString("SPARTNERNAME"));
				ci.setFKGScale(rs.getFloat("MSTOCKRATE"));
				ci.setKGCardNo(rs.getString("SLOANCARDNO"));
				ci.setKGPwd(rs.getString("SLOANCARDPWD"));
				//log4j.info("得到控股单位信息");
			}
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
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			//查找其他股东单位
			strSQL = " select * from PARTNEROFCLIENT " + " where nClientID = ? " + "       and  nPartnerID = -1 ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			int i = 0;
			while (rs != null && rs.next() && i < ci.getQTClientName().length)
			{
				//ci.m_lQTClientID[i] = rs.getLong("nPartnerID"); //其他股东单位1ID
				strQTClientName[i] = rs.getString("SPARTNERNAME");
				fQTScale[i] = rs.getFloat("MSTOCKRATE");
				strQTCardNo[i] = rs.getString("SLOANCARDNO");
				strQTPwd[i] = rs.getString("SLOANCARDPWD");
				//log4j.info("得到其他股东单位信息" + (i + 1));
				i++;
			}
			ci.setQTClientName(strQTClientName);
			ci.setFQTScale(fQTScale);
			ci.setQTCardNo(strQTCardNo);
			ci.setQTPwd(strQTPwd);
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
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
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
		return ci;
	}

	/**
		 * 查找一条收款方资料
		 * Create Date: 2003-8-13
		 * @param lID 收款方ID
		 * @return ClientCapInfo
		 * @exception Exception
		 */
	public ClientCapInfo findAccount(long lClientID, long lCurrencyID, String strAccountno,long officeid) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strReturn = "";
		ClientCapInfo info = new ClientCapInfo();

		try
		{
			con = Database.getConnection();
			String strSQL = "";

			strSQL = " select a.saccountno,a.sname from sett_account a,sett_accounttype b" + " where a.saccountno = '" + strAccountno + "'" +
					" and a.NOFFICEID="+officeid+" and a.NCURRENCYID="+lCurrencyID+" and a.NACCOUNTTYPEID=b.id and b.NACCOUNTGROUPID in ("+SETTConstant.AccountGroupType.CURRENT+","+SETTConstant.AccountGroupType.OTHERDEPOSIT+")";
			if (strAccountno != null && strAccountno.length() > 0)
			{
				log4j.info(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info.setPayeeAccoutNO(rs.getString("saccountno"));
					info.setPayeeName(rs.getString("sname"));
				}
				else
				{
					info = null;
				}
			}
			else
			{
				info = null;
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
		return info;
	}

	/**
		 * 查找一条收款方资料(通过财务公司内部账户或该账户的银行账户均能查找--上气用)
		 * Create Date: 2003-8-13
		 * @param lID 收款方ID
		 * @return ClientCapInfo
		 * @exception Exception
		 */
	public ClientCapInfo findAccountForSEFC(long lClientID, long lCurrencyID, String strAccountno) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strReturn = "";
		ClientCapInfo info = new ClientCapInfo();

		try
		{
			con = Database.getConnection();
			String strSQL = "";

			strSQL = " select a.saccountno,a.sname,b.sbankaccountno,b.sbranchname from sett_account a,SETT_BANKACCOUNTOFFILIALE b " 
					+ "where a.id=b.nwithinaccountid(+) and (a.saccountno = '" + strAccountno + "' or b.sbankaccountno = '"+ strAccountno +"')";
			if (strAccountno != null && strAccountno.length() > 0)
			{
				log4j.info(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info.setPayeeAccoutNO(rs.getString("saccountno"));
					info.setPayeeName(rs.getString("sname"));
					info.setPayeeBankNO(rs.getString("sbankaccountno"));
					info.setPayeeBankName(rs.getString("sbranchname"));
				}
				else
				{
					info = null;
				}
			}
			else
			{
				info = null;
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
		return info;
	}

}
