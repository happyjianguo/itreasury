package com.iss.itreasury.ebank.obaheadrepaynotice.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
/**
 * @author gqzhang
 *贴现申请DAO To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class OBAheadRepayNoticeDao extends SettlementDAO
{
	private Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this); //
	public OBAheadRepayNoticeDao()
	{
		super();
	}

	public AheadRepayNoticeInfo findAheadRepayNoticeByID(long lID) throws Exception
	{
		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,");
			sbSQL.append(" b.id as ContractID,b.sContractCode,");
			sbSQL.append(" b.nIntervalNum,b.mExamineAmount,b.mAdjustRate,");
			sbSQL.append(" c.sCode as PayCode,c.mInterestRate,");
			sbSQL.append(" c.mAmount as PayAmount,c.id as PayID,");
			sbSQL.append(" d.sName as clientName,e.sName as userName");

			sbSQL.append(" ,pp.id as PlanID ");//2004-01-15 ninh

			sbSQL.append(" FROM OB_AheadRepayForm a,loan_ContractForm b,");
			sbSQL.append(" loan_PayForm c,Client d,ob_user e");

			sbSQL.append(" ,loan_loancontractplan pp ");//2004-01-15 ninh

			sbSQL.append(" WHERE b.id = a.nContractID");
			sbSQL.append(" AND c.ID=a.nLoanPayNoticeID");
			sbSQL.append(" AND b.nBorrowClientID=d.ID(+)");
			sbSQL.append(" AND a.nInputUserID = e.id(+)");

			sbSQL.append(" and b.id=pp.nContractID(+) ");//2004-01-15 ninh
			sbSQL.append(" and pp.nStatusID(+) = "+Constant.RecordStatus.VALID);//2004-01-15 ninh

			sbSQL.append(" AND a.id = ?");

			sbSQL.append(" order by pp.id desc ");//2004-01-15 ninh

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				ContractDao dao = new ContractDao();
				info.setID(rs.getLong("id"));
				info.setCode(rs.getString("sInstructionNo")); //通知单编号
				info.setClientName(rs.getString("clientName")); //借款单位
				info.setContractCode(rs.getString("sContractCode")); //合同编号
				info.setContractID(rs.getLong("ContractID")); //合同ID
				info.setIntervalNum(rs.getLong("nIntervalNum")); //贷款期限
				info.setContractAmount(rs.getDouble("mExamineAmount")); //合同金额

				info.setPlanID(rs.getLong("PlanID"));//计划标识
				Log.print("PlanID:"+rs.getLong("PlanID"));

				ContractAmountInfo cInfo = dao.getLateAmount(info.getContractID());
				info.setContractBalance(cInfo.getBalanceAmount()); //合同余额
				info.setLetoutNoticeCode(rs.getString("PayCode")); //放款通知单编号
				info.setLetoutNoticeRate(rs.getDouble("mInterestRate") * (1 + rs.getDouble("mAdjustRate") / 100));
				//放款通知单利率,提前还款利率
				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //放款通知单金额
				info.setLetoutNoticeBalance(getLateAmount(rs.getLong("PayID"))); //放款通知单余额
				info.setAmount(rs.getDouble("mAmount")); //提前还款金额
				//info.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //审核人
				info.setInputUserID(rs.getLong("nInputUserID")); //录入人ID
				info.setInputUserName(rs.getString("userName")); //录入人
				info.setInputDate(rs.getTimestamp("dtInputDate")); //录入时间
				info.setStatusID(rs.getLong("nStatusID")); //状态
				info.setIsAhead(rs.getLong("nisahead")); //是否提前还款
				info.setStatus(OBConstant.LoanInstrStatus.getName(info.getStatusID())); //状态

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
	* 得到放款通知单当前金额
	* Create Date: 2003-10-15
	* @param lID 放款通知单ID
	* @return double
	* @exception Exception
	*/
	public double getLateAmount(long lID) throws Exception
	{
		double dBalance = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lID > 0)
			{
				sbSQL.append(" SELECT SUM(a.mBalance) Balance");
				sbSQL.append(" FROM sett_subAccount a,loan_payform b");
				sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				sbSQL.append(" AND b.id = ? ");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					dBalance = rs.getDouble("Balance");
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
		return dBalance;
	}

	/**
	 * Method saveDiscountCredence.
	 * 保存提前还款票据信息
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long saveAheadRepayNotice(AheadRepayNoticeInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		long lID = -1;
		String sCode = "";

		try
		{
			//operation Adding
			if (info != null&& info.getID()<0)
			{
				conn = Database.getConnection();
				/*首先获得ob_AheadRepayForm 的 MAXID */
				strSQL = " SELECT NVL(MAX(ID)+1,1) ID";
				strSQL += " FROM OB_AheadRepayForm ";

				ps = conn.prepareStatement(strSQL);
				log4j.info(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lID = rs.getLong("ID");
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

				sCode = OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN);

				log4j.info("生成提前还款通知单：" + sCode);

				/* 复制信息从loan_loanForm 到loan_contractForm */
				strSQL = "INSERT INTO OB_AheadRepayForm ";
				strSQL += " (ID,nContractID,";
				strSQL += " nLoanPayNoticeID,mAmount,sInstructionNo,nInputUserID,";
				strSQL += " dtInputDate,nStatusID,nisAhead)";
				strSQL += " values (?,?,?,?,?,?,sysdate,?,?)";

				log4j.info(strSQL);
				ps = conn.prepareStatement(strSQL);
				int n = 1;
				ps.setLong(n++, lID);
				//ps.setLong(n++, info.getOfficeID());
				//ps.setLong(n++, info.getCurrencyID());
				ps.setLong(n++, info.getContractID());
				ps.setLong(n++, info.getLetoutNoticeID());
				ps.setDouble(n++, info.getAmount());
				ps.setString(n++, sCode);
				ps.setLong(n++, info.getInputUserID());
				//ps.setLong(n++, info.getInputUserID());
				//ebank operation save first change status to "SAVE" then submit to submit
				ps.setLong(n++, OBConstant.LoanInstrStatus.SAVE);				
				ps.setLong(n++, info.getIsAhead());
				ps.executeUpdate();
				ps.close();
				ps = null;

			}
			//operation update
			else
			{
				conn = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(" UPDATE ob_AheadRepayForm");
				sbSQL.append(" SET mAmount = ? "); //状态

				//if (info.getAmount() > 0)
				//{
				//	sbSQL.append(" ,mAmount = ?");
				//}

				sbSQL.append(" WHERE id = ? "); //提前还款通知单ID

				log4j.info(sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				int index = 1;
				//ps.setLong(index++, info.getStatusID());

				//if (info.getAmount() > 0)
				//{
					ps.setDouble(index++, info.getAmount());
				//}

				ps.setLong(index++, info.getID());

				lID = ps.executeUpdate();
				//如果更新成功返回ID
				if (lID >0)
				{
				    lID = info.getID();
				}

				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
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
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
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
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			e.printStackTrace();
			throw e;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lID;
	}

	public long updateStatus(long lAheadPayID,long lStatusID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		long lID = -1;
		String sCode = "";

		try
		{
			conn = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE ob_AheadRepayForm");
			sbSQL.append(" SET nStatusID = ? "); //状态

			sbSQL.append(" WHERE id = ? "); //提前还款通知单ID

			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);

			ps.setLong(index++, lAheadPayID);

			lID = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
			return lID;
	}

}
