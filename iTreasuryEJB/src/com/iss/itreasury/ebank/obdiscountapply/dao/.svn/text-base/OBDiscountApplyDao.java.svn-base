package com.iss.itreasury.ebank.obdiscountapply.dao;
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
import com.iss.itreasury.ebank.obdiscountapply.dataentity.*;
/**
 * @author gqzhang
 *贴现申请DAO To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class OBDiscountApplyDao extends SettlementDAO
{
	private Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this); //
	public OBDiscountApplyDao()
	{
		super();
	}
	/**
	 * Method saveDiscount1.
	 * 保存贴现申请信息（第一步，申请单位的信息）
	 *申请的状态为：撰写
	 *操作表：OB_Loan
	 *返回：贴现申请ID
	 * @param info
	 * @return long
	 */
	public long saveDiscount1(DiscountMainInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lMaxID = -1;
		long lTypeID = OBConstant.LoanInstrType.DISCOUNT;
		long lModuleID = OBConstant.SubModuleType.LOAN;
		try
		{
			Log.print("~~~~~~~~~~~进入方法saveDiscount1~~~~~~~~~~");
			conn = getConnection();
			//第一次暂存
			if (info.getID() <= 0)
			{
				//获取最大的ID
				sbSQL = new StringBuffer();
				sbSQL = sbSQL.append("select nvl(max(ID)+1,1) from OB_LOAN");
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs.next())
				{
					lMaxID = rs.getLong(1);
					info.setID(lMaxID);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				Log.print("lMaxID:" + lMaxID);
				//指令编号
				String strInstructionNo = OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN);
				//String strInstructionNo = "1111";
				sbSQL = new StringBuffer();
				sbSQL.append(" insert into OB_Loan \n");
				sbSQL.append(" ( \n");
				sbSQL.append(" ID,NTYPEID,NCURRENCYID,NOFFICEID,SINSTRUCTIONNO,NBORROWCLIENTID,NINPUTUSERID,DTINPUTDATE,NSTATUSID");
				sbSQL.append(" ) \n");
				sbSQL.append(" values(?,?,?,?,?,?,?,?,?) \n");
				Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
				Log.print(sbSQL.toString());
				Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
				ps = conn.prepareStatement(sbSQL.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, info.getID());
				ps.setLong(nIndex++, lTypeID);
				ps.setLong(nIndex++, info.getCurrencyID());
				ps.setLong(nIndex++, info.getOfficeID());
				ps.setString(nIndex++, strInstructionNo);
				ps.setLong(nIndex++, info.getClientID());
				ps.setLong(nIndex++, info.getUserID());
				ps.setTimestamp(nIndex++, info.getDate());
				ps.setLong(nIndex++, info.getStatusID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
			{
				sbSQL = new StringBuffer();
				sbSQL.append(" update OB_Loan set NTYPEID=?,NCURRENCYID=?,NOFFICEID=?,NBORROWCLIENTID=?,NINPUTUSERID=?,DTINPUTDATE=?,NSTATUSID=? where id=?");
				Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
				Log.print(sbSQL.toString());
				Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
				ps = conn.prepareStatement(sbSQL.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, lTypeID);
				ps.setLong(nIndex++, info.getCurrencyID());
				ps.setLong(nIndex++, info.getOfficeID());
				ps.setLong(nIndex++, info.getClientID());
				ps.setLong(nIndex++, info.getUserID());
				ps.setTimestamp(nIndex++, info.getDate());
				ps.setLong(nIndex++, info.getStatusID());
				ps.setLong(nIndex++, info.getID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lMaxID);
	}
	/**
	 * Method findDiscountByID.
	 * 根据贴现申请的ID查询贴现申请的信息及贴现票据的信息
	 * @param lDiscountID
	 * @return DiscountInfo
	 * @throws Exception
	 */
	public DiscountInfo findDiscountByID(long lDiscountID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		DiscountInfo lai = new DiscountInfo();
		try
		{
			Log.print("~~~~~~~~~~~进入方法findDiscountByID~~~~~~~~~~");
			con = Database.getConnection();
			strSQL = " select a.*, ";
			strSQL += " c.sName sClientName, c.sAccount, d.sName sInputUserName, nvl(e.sContractCode,'') sContractCode ";
			strSQL += " from OB_Loan a,Client c,OB_User d,Loan_ContractForm e ";
			strSQL += " where a.nBorrowClientID=c.ID(+) and a.nInputUserID=d.ID and a.ID=e.nLoanID(+) and a.ID=? ";
			Log.print("======================");
			Log.print(strSQL);
			Log.print("======================");
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lDiscountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lai.setID(lDiscountID); //贴现ID标识
				lai.setDiscountCode(rs.getString("SINSTRUCTIONNO")); //贴现编号
				lai.setContractCode(rs.getString("sContractCode")); //贴现合同编号
				lai.setStatusID(rs.getLong("nStatusID")); //状态
				//申请单位
				lai.setApplyClientID(rs.getLong("nBorrowClientID")); //申请单位编号
				lai.setApplyClientName(rs.getString("sClientName")); //申请单位名称
				lai.setApplyLOfficeAccount(rs.getString("sAccount")); //申请单位开户办事处账号
				lai.setApplyDiscountAmount(rs.getDouble("mLoanAmount")); //申请贴现金额
				lai.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
				lai.setCheckAmount(rs.getDouble("mCheckAmount")); //核定金额
				lai.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
				lai.setInterest(rs.getDouble("mExamineAmount") - rs.getDouble("mCheckAmount")); //贴现利息
				lai.setDiscountPurpose(rs.getString("sLoanPurpose")); //贴现用途
				lai.setDiscountReason(rs.getString("sLoanReason")); //贴现原因
				lai.setDiscountDate(rs.getTimestamp("dtDiscountDate")); //贴现计息日
				lai.setDiscountStartDate(rs.getTimestamp("dtDiscountDate")); //贴现日
				lai.setDiscountEndDate(rs.getTimestamp("dtEndDate")); //贴现到期日
				lai.setInputUserID(rs.getLong("nInputUserID"));
				lai.setInputUserName(rs.getString("sInputUserName"));
				lai.setInputDate(rs.getTimestamp("dtInputDate"));
				lai.setDocumentType(rs.getString("SDOCUMENTTYPE"));
				lai.setIsPurchaserInterest(rs.getLong("ISPURCHASERINTEREST"));//是否买方付息
				lai.setDiscountClientID(rs.getLong("DISCOUNTCLIENTID"));//出票人ID
				lai.setDiscountClientName(rs.getString("DISCOUNTCLIENTNAME"));//出票人名称 
				lai.setSubTypeId(rs.getLong("nsubtypeid"));//子类型ID
				lai.setTypeID(rs.getLong("ntypeid"));//类型ID
				if (lai.getStatusID() == OBConstant.LoanInstrStatus.SUBMIT && lai.getNextCheckUserID() != lai.getInputUserID())
				{
					lai.setIsCheck(Constant.YesOrNo.YES); //已审核过
				}
				else
				{
					lai.setIsCheck(Constant.YesOrNo.NO); //未审核过
				}
				//贴现票据
				lai.setBankAccepPO(rs.getLong("nBankAcceptPO")); //银行承兑汇票（张数）
				lai.setBizAcceptPO(rs.getLong("nBizAcceptPO")); //商业承兑汇票（张数）
				lai.setApplyDiscountPO(lai.getBankAccepPO() + lai.getBizAcceptPO()); //申请贴现汇票（张数）
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			strSQL =
				" select sum(nvl(BankAmount,0))+sum(nvl(BizAmount,0)) BillAmount,sum(BankCount) BankCount,sum(BizCount) BizCount from ( "
					+ " select count(*) BankCount,sum(nvl(mAmount,0)) BankAmount,0 BizCount,0 BizAmount from OB_DiscountBill where nLoanID=? and nStatusID=? " 
                    + " and nAcceptPoType=? " 
                    //+ " and to_char(dtEnd,'yyyy-mm-dd') >=? and  to_char(DTCREATE,'yyyy-mm-dd') <=? "
					+ " union all "
					+ " select 0 BankCount,0 BankAmount,count(*) BizCount,sum(nvl(mAmount,0)) BizAmount from OB_DiscountBill where nLoanID=? and nStatusID=? and nAcceptPoType=? " 
                    //+ " and to_char(dtEnd,'yyyy-mm-dd') >=? and  to_char(DTCREATE,'yyyy-mm-dd') <=? "
					+ " ) ";
			Log.print(strSQL);
			Log.print("贴现日期:" + lai.getDiscountDate());
			ps = con.prepareStatement(strSQL);
			int nIndex = 1;
			ps.setLong(nIndex++, lDiscountID);
			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
			ps.setLong(nIndex++, OBConstant.DraftType.BANK);
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			ps.setLong(nIndex++, lDiscountID);
			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
			ps.setLong(nIndex++, OBConstant.DraftType.BIZ);
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			rs = ps.executeQuery();
			if (rs.next())
			{
				lai.setBillAmount(rs.getDouble("BillAmount")); //申请贴现汇票总金额
				lai.setBankCount(rs.getLong("BankCount")); //银行承兑汇票（张数）
				lai.setBizCount(rs.getLong("BizCount")); //商业承兑汇票（张数）
				lai.setBillCount(rs.getLong("BankCount") + rs.getLong("BizCount")); //申请贴现汇票（张数）
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lai;
	}
	/**
	 * Method saveDiscount2.
	 * 保存贴现申请信息（第二步，贴现票据的信息）
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long saveDiscount2(DiscountBillStatInfo info) throws Exception
	{
		long lID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~进入方法saveDiscount2~~~~~~~~~~");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update OB_LOAN set NINPUTUSERID=?,DTINPUTDATE=?,nBankAcceptPO=?,nBizAcceptPO=?,mLoanAmount=?,sLoanReason=?,sLoanPurpose=?,DTDISCOUNTDATE=?,DTSTARTDATE=?,NSUBTYPEID=?,ISPURCHASERINTEREST=?,DISCOUNTCLIENTID=?,DISCOUNTCLIENTNAME=? where ID=? ");
			Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
			Log.print(sbSQL.toString());
			Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
			ps = conn.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, info.getInputUserID());
			ps.setTimestamp(nIndex++, info.getDate());
			ps.setLong(nIndex++, info.getBankAcceptPO());
			ps.setLong(nIndex++, info.getBizAcceptPO());
			ps.setDouble(nIndex++, info.getAmount());
			ps.setString(nIndex++, info.getReason());
			ps.setString(nIndex++, info.getPurpose());
			ps.setTimestamp(nIndex++, info.getDiscountStartDate());
            ps.setTimestamp(nIndex++, info.getDiscountStartDate());
            ps.setLong(nIndex++, info.getSubTypeId());//子类型
            ps.setLong(nIndex++, info.getIsPurchaserInterest());//是否买方付息
            ps.setLong(nIndex++, info.getDiscountClientID());//出票人ID
            ps.setString(nIndex++, info.getDiscountClientName());//出票人名称
			ps.setLong(nIndex++, info.getID());
			ps.executeUpdate();
			ps.close();
			ps = null;
			lID = info.getID();
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	/**
	 * 更新贴现申请记录状态
	 * @see com.iss.itreasury.dao.SettlementDAO#updateStatus(long, long)
	 */
	public long updateStatus(long lDiscountID, long lStatusID) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~进入方法updateStatus~~~~~~~~~~");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update OB_LOAN set nStatusID=? where ID=? ");
			Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
			Log.print(sbSQL.toString());
			Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lDiscountID);
			if (ps.executeUpdate() > 0)
			{
				lResult = 1;
			}
			else
			{
				lResult = 0;
			}
			ps.close();
			ps = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lResult;
	}
	/**
	 * Method deleteDiscountBill
	 * 批量删除贴现票据信息（逻辑删除）.
	 * @param lDiscountBillID
	 * @return long 1-删除成功 0删除失败
	 * @throws Exception
	 */
	public long deleteDiscountBill(long lDiscountBillID[]) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		int i = 0;
		long lBillID = -1;
		long lDiscountApplyID = -1;
		long nStatusID = -1;
		long nSerialNo = -1;
		try
		{
			Log.print("~~~~~~~~~~~进入方法deleteDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			for (i = 0; i < lDiscountBillID.length; i++)
			{
				if (lDiscountBillID[i] > 0)
				{
					lBillID = lDiscountBillID[i];
				}
			}
			//贴现申请标示
			strSQL = " select nLoanID from OB_DiscountBill where ID=? ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lBillID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lDiscountApplyID = rs.getLong("nLoanID");
			}
			Log.print(lDiscountApplyID);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			for (i = 0; i < lDiscountBillID.length; i++)
			{
				if (lDiscountBillID[i] > 0)
				{
					//更新记录
					strSQL = " update OB_DiscountBill set nStatusID=? where ID=? ";
					Log.print(strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1, Constant.RecordStatus.INVALID);
					ps.setLong(2, lDiscountBillID[i]);
					ps.executeUpdate();
					ps.close();
					ps = null;
					//更新序列号
					strSQL = " select nSerialNo from OB_DiscountBill where ID=? ";
					Log.print(strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1, lDiscountBillID[i]);
					rs = ps.executeQuery();
					if (rs.next())
					{
						nSerialNo = rs.getLong("nSerialNo");
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					if (lDiscountApplyID > 0 && nSerialNo > 0)
					{
						strSQL = " update OB_DiscountBill set nSerialNo=nSerialNo-1 where nSerialNo>? and nLoanID=? ";
						Log.print("更新序列号");
						Log.print(strSQL);
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1, nSerialNo);
						ps.setLong(2, lDiscountApplyID);
						ps.executeUpdate();
						ps.close();
						ps = null;
					}
				}
			}
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lDiscountBillID.length);
	}
	/**
	 * Method findDiscountBillByDiscountID.
	 * 根据贴现申请标识查询贴现票据
	 * 查询表：OB_DiscountBill 返回：由DiscountBillInfo组成的 Collection
	 * @param discountBillQueryInfo
	 * @return Collection
	 */
	public Vector findDiscountBillByDiscountID(DiscountBillQueryInfo discountBillQueryInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		String strUpdate = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		Timestamp tsEnd = null; //贴现日期
		String strEnd = ""; //贴现日期
		int nDays = 0; //实际贴现天数
		double dAmount = 0; //票据金额
		double dAccrual = 0; //贴现利息
		double dRealAmount = 0; //实付贴现金额
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额
		try
		{
			Log.print("~~~~~~~~~~~进入方法findDiscountBillByDiscountID~~~~~~~~~~");
			con = Database.getConnection();
			Log.print("贴现标识：" + discountBillQueryInfo.getDiscountID());
			Log.print("贴现日  ：" + discountBillQueryInfo.getDate());
			Log.print("======开始校验贴现日，贴现日应该小于所有票据到期日======");
			//贴现日输入校验，应该大于所有票据到期日
			strSQL = " select count(*) from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nLoanID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				Log.print("==============票据总张数==============" + lRecordCount);
				//Log.print("==============票据总金额==============" + dTotalAmount);
			}
			//如果记录数小于等于0
			if (lRecordCount <= 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
				return null;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("==============贴现日校验结束==============");
			Log.print("==============开始查询票据总数和总金额=============");
			//计算记录总数
			strSelect = " select count(*),sum(nvl(mAmount,0)) ";
			strSQL = " from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nLoanID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
			}
			Log.print("==============票据总张数==============" + lRecordCount);
			Log.print("==============票据总金额==============" + dTotalAmount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("=============结束查询票据总数和总金额=========");
			lPageCount = lRecordCount / discountBillQueryInfo.getPageLineCount();
			if ((lRecordCount % discountBillQueryInfo.getPageLineCount()) != 0)
			{
				lPageCount++;
			}
			//排序处理
			switch ((int) discountBillQueryInfo.getOrderParam())
			{
				case 1 :
					strSQL += " order by nSerialNo";
					break;
				case 2 :
					strSQL += " order by sUserName";
					break;
				case 3 :
					strSQL += " order by sBank";
					break;
				case 4 :
					strSQL += " order by nIsLocal";
					break;
				case 5 :
					strSQL += " order by dtCreate";
					break;
				case 6 :
					strSQL += " order by dtEnd";
					break;
				case 7 :
					strSQL += " order by sCode";
					break;
				case 8 :
					strSQL += " order by mAmount";
					break;
				case 9 :
					strSQL += " order by nAddDays";
					break;
				case 10 :
					strSQL += " order by nAcceptPOType";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}
			if (discountBillQueryInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			//返回需求的结果集
			lRowNumStart = (discountBillQueryInfo.getPageNo() - 1) * discountBillQueryInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + discountBillQueryInfo.getPageLineCount() - 1;
			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			Log.print("翻页查询开始");
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();
				dbill.setDiscountApplyID(discountBillQueryInfo.getDiscountID()); //贴现标示
				dbill.setDiscountBillID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setDiscountCredenceID(rs.getLong("NDISCOUNTCREDENCEID")); //贴现凭证标识
				dbill.setUser(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDay(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOType")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
				dbill.setNLoanID(rs.getLong("nLoanID"));//贴现申请指令ID
				//票汇实付金额需要处理？？
				v.add(dbill);
			}
			Log.print("翻页查询结束");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		Log.print("======退出贴现计息(findDiscountBillByDiscountID)方法======");
		return (v.size() > 0 ? v : null);
	}
	
	
	/**
	 * Method findDiscountBillByDiscountID.
	 * 根据贴现申请标识查询贴现票据
	 * 查询表：OB_DiscountBill 返回：由DiscountBillInfo组成的 Collection
	 * @param discountBillQueryInfo
	 * @return Collection
	 */
	public Vector findByID(DiscountBillQueryInfo discountBillQueryInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		String strUpdate = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		Timestamp tsEnd = null; //贴现日期
		String strEnd = ""; //贴现日期
		int nDays = 0; //实际贴现天数
		double dAmount = 0; //票据金额
		double dAccrual = 0; //贴现利息
		double dRealAmount = 0; //实付贴现金额
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额
		try
		{
			Log.print("~~~~~~~~~~~进入方法findDiscountBillByDiscountID~~~~~~~~~~");
			con = Database.getConnection();
			Log.print("贴现标识：" + discountBillQueryInfo.getDiscountID());
			Log.print("贴现日  ：" + discountBillQueryInfo.getDate());
			Log.print("======开始校验贴现日，贴现日应该小于所有票据到期日======");
			//贴现日输入校验，应该大于所有票据到期日
			strSQL = " select count(*) from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and ID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				Log.print("==============票据总张数==============" + lRecordCount);
				//Log.print("==============票据总金额==============" + dTotalAmount);
			}
			//如果记录数小于等于0
			if (lRecordCount <= 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
				return null;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("==============贴现日校验结束==============");
			Log.print("==============开始查询票据总数和总金额=============");
			//计算记录总数
			strSelect = " select count(*),sum(nvl(mAmount,0)) ";
			strSQL = " from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and ID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
			}
			Log.print("==============票据总张数==============" + lRecordCount);
			Log.print("==============票据总金额==============" + dTotalAmount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("=============结束查询票据总数和总金额=========");
			lPageCount = lRecordCount / discountBillQueryInfo.getPageLineCount();
			if ((lRecordCount % discountBillQueryInfo.getPageLineCount()) != 0)
			{
				lPageCount++;
			}
			//排序处理
			switch ((int) discountBillQueryInfo.getOrderParam())
			{
				case 1 :
					strSQL += " order by nSerialNo";
					break;
				case 2 :
					strSQL += " order by sUserName";
					break;
				case 3 :
					strSQL += " order by sBank";
					break;
				case 4 :
					strSQL += " order by nIsLocal";
					break;
				case 5 :
					strSQL += " order by dtCreate";
					break;
				case 6 :
					strSQL += " order by dtEnd";
					break;
				case 7 :
					strSQL += " order by sCode";
					break;
				case 8 :
					strSQL += " order by mAmount";
					break;
				case 9 :
					strSQL += " order by nAddDays";
					break;
				case 10 :
					strSQL += " order by nAcceptPOType";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}
			if (discountBillQueryInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			//返回需求的结果集
			lRowNumStart = (discountBillQueryInfo.getPageNo() - 1) * discountBillQueryInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + discountBillQueryInfo.getPageLineCount() - 1;
//			strSQL = "select * " + strSQL;
//			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
//			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			strSQL = " select * " + strSQL ;
			Log.print("翻页查询开始");
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();
				dbill.setDiscountApplyID(discountBillQueryInfo.getDiscountID()); //贴现标示
				dbill.setDiscountBillID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setDiscountCredenceID(rs.getLong("NDISCOUNTCREDENCEID")); //贴现凭证标识
				dbill.setUser(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDay(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOType")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
				dbill.setNLoanID(rs.getLong("nLoanID"));//贴现申请指令ID
				//票汇实付金额需要处理？？
				v.add(dbill);
			}
			Log.print("翻页查询结束");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		Log.print("======退出贴现计息(findByID)方法======");
		return (v.size() > 0 ? v : null);
	}
	/**
	 * Method updateDiscount.
	 * 修改贴现信息
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long updateDiscount(DiscountInfo info) throws Exception
	{
		long lID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		int nIndex = 1;
		try
		{
			Log.print("==========updateDiscount start=============");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("UPDATE OB_Loan SET  ");
			if (info.getDocumentType() != null && info.getDocumentType().length() > 0)
			{
				//随表报送书面材料
				sbSQL.append("SDOCUMENTTYPE=?, ");
			}
			sbSQL.append("ninputuserid=ninputuserid ");
			sbSQL.append("where id=? AND  nCurrencyID = ? AND ninputuserid = ? ");
			Log.print("\n==========SQL==========");
			Log.print(sbSQL.toString());
			Log.print("==========SQL==========\n");
			ps = conn.prepareStatement(sbSQL.toString());
			if (info.getDocumentType() != null && info.getDocumentType().length() > 0)
			{
				ps.setString(nIndex++, info.getDocumentType());
			}
			ps.setLong(nIndex++, info.getID());
			ps.setLong(nIndex++, info.getCurrencyID());
			ps.setLong(nIndex++, info.getInputUserID());
			if (ps.executeUpdate() > 0)
			{
				Log.print("updateDiscount success！");
			}
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			lID = info.getID();
			Log.print("==========updateDiscount end=============");
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	/**
	 * Method saveDiscountBill.
	 * 保存贴现票据信息
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long saveDiscountBill(DiscountBillInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		int nMaxSerialNo = -1;
		try
		{
			Log.print("~~~~~~~~~~~进入方法saveDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//得到最大的ID
			strSQL = " select nvl(max(ID)+1,1) from OB_DiscountBill ";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//获取序列号
			strSQL = " select nvl(max(nSerialNo)+1,1) from OB_DiscountBill " + " where nLoanID=" + info.getDiscountApplyID() + " and nStatusID = " + Constant.RecordStatus.VALID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				nMaxSerialNo = rs.getInt(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//存储记录
			strSQL =
				" Insert into OB_DiscountBill(ID,nLoanID,nSerialNo,sUserName,sBank,nIsLocal,dtCreate,dtEnd,sCode,mAmount,nStatusID,nAddDays,nAcceptPOType,sFormerOwner)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lMaxID);
			ps.setLong(2, info.getDiscountApplyID());
			ps.setLong(3, nMaxSerialNo);
			ps.setString(4, info.getUser());
			ps.setString(5, info.getBank());
			ps.setLong(6, info.getIsLocal());
			ps.setTimestamp(7, info.getCreate());
			ps.setTimestamp(8, info.getEnd());
			ps.setString(9, info.getCode());
			ps.setDouble(10, info.getAmount());
			ps.setLong(11, Constant.RecordStatus.VALID);
			ps.setLong(12, info.getAddDay());
			ps.setLong(13, info.getAcceptPOTypeID());
			ps.setString(14, info.getFormerOwner());
			ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			//+++++++++++++++++++++++++++++
			Timestamp tsDiscountEndDate = null;
			strSQL = " select dtEndDate from OB_LOAN where ID=? ";
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, info.getDiscountApplyID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				tsDiscountEndDate = rs.getTimestamp("dtEndDate");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (tsDiscountEndDate == null && info.getEnd() != null)
			{
				//更新记录
				strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setTimestamp(1, info.getEnd());
				ps.setLong(2, info.getDiscountApplyID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
				if (tsDiscountEndDate != null && info.getEnd() != null && tsDiscountEndDate.before(info.getEnd()))
				{
					//更新记录
					strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
					//Log.print (strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setTimestamp(1, info.getEnd());
					ps.setLong(2, info.getDiscountApplyID());
					ps.executeUpdate();
					ps.close();
					ps = null;
				}
			conn.close();
			conn = null;
			//+++++++++++++++++++++++++++++
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			//throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lMaxID);
	}
	/**
	 * Method findDiscountBillByID.
	 * 根据标识查询一条贴现票据的信息
	 * @param lDiscountBillID
	 * @return DiscountBillInfo
	 * @throws Exception
	 */
	public DiscountBillInfo findDiscountBillByID(long lDiscountBillID) throws Exception
	{
		DiscountBillInfo dbi = new DiscountBillInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~进入方法findDiscountBillByID~~~~~~~~~~");
			con = Database.getConnection();
			strSQL = " select * from Loan_DiscountContractBill where ID=? and nStatusID=?";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lDiscountBillID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				
				dbi.setDiscountBillID(lDiscountBillID); //票据标示
				//dbi.setDiscountApplyID(rs.getLong("nLoanID")); //贴现标识
				dbi.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbi.setUser(rs.getString("sUserName")); //原始出票人
				dbi.setBank(rs.getString("sBank")); //承兑银行
				dbi.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地
				dbi.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbi.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbi.setCode(rs.getString("sCode")); //汇票号码
				dbi.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbi.setAddDay(rs.getLong("nAddDays")); //节假日增加计息天数
				dbi.setAcceptPOTypeID(rs.getLong("NACCEPTPOTYPEID")); //汇票类型
				dbi.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return dbi;
	}
	/**
	 * Method updateDiscountBill.
	 * 更新贴现票据明细信息
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long updateDiscountBill(DiscountBillInfo info) throws Exception
	{
		long lID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~进入方法updateDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//更新记录
			strSQL = " update OB_DiscountBill set sUserName=?,sBank=?,nIsLocal=?,dtCreate=?,dtEnd=?,sCode=?,mAmount=?,nAddDays=?,nAcceptPOType=?,sFormerOwner=? where ID=? ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			int nIndex = 1;
			ps.setString(nIndex++, info.getUser());
			ps.setString(nIndex++, info.getBank());
			ps.setLong(nIndex++, info.getIsLocal());
			ps.setTimestamp(nIndex++, info.getCreate());
			ps.setTimestamp(nIndex++, info.getEnd());
			ps.setString(nIndex++, info.getCode());
			ps.setDouble(nIndex++, info.getAmount());
			ps.setLong(nIndex++, info.getAddDay());
			ps.setLong(nIndex++, info.getAcceptPOTypeID());
			ps.setString(nIndex++, info.getFormerOwner());
			ps.setLong(nIndex++, info.getDiscountBillID());
			ps.executeUpdate();
			ps.close();
			ps = null;
			lID = info.getDiscountBillID();
			conn.close();
			conn = null;
			//			+++++++++++++++++++++++++++++
			Timestamp tsDiscountEndDate = null;
			strSQL = " select dtEndDate from OB_LOAN where ID=? ";
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, info.getDiscountApplyID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				tsDiscountEndDate = rs.getTimestamp("dtEndDate");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (tsDiscountEndDate == null && info.getEnd() != null)
			{
				//更新记录
				strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setTimestamp(1, info.getEnd());
				ps.setLong(2, info.getDiscountApplyID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
				if (tsDiscountEndDate != null && info.getEnd() != null && tsDiscountEndDate.before(info.getEnd()))
				{
					//更新记录
					strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
					//Log.print (strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setTimestamp(1, info.getEnd());
					ps.setLong(2, info.getDiscountApplyID());
					ps.executeUpdate();
					ps.close();
					ps = null;
				}
			conn.close();
			conn = null;
			//+++++++++++++++++++++++++++++
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
		try
		{
			OBDiscountApplyDao dao = new OBDiscountApplyDao();
			/*DiscountMainInfo info = new DiscountMainInfo();
			info.setID(1);
			info.setCurrencyID(1);
			info.setOfficeID(1);
			dao.saveDiscount1(info);
			*/
			/*
				DiscountBillStatInfo info = new DiscountBillStatInfo();
				info.setAmount(100);
				info.setBankAcceptPO(2);
				info.setBizAcceptPO(4);
				info.setInputUserID(3);
				info.setDiscountStartDate(DataFormat.getDateTime("2005-01-06"));
				info.setReason("reson");
				info.setPurpose("purpose1");
				info.setDate(DataFormat.getDateTime("2003-01-05"));
				info.setID(1);
				dao.saveDiscount2(info);
			*/
			/*dao.updateStatus(1,3);
			 */
			/*DiscountBillInfo info = new DiscountBillInfo();
			info.setDiscountBillID(3);
			info.setDiscountApplyID(1);
			info.setDiscountCredenceID(2);
			info.setUser("user3");
			info.setBank("bank3");
			info.setIsLocal(1);
			info.setCreate(DataFormat.getDateTime("2006-4-1"));
			info.setEnd(DataFormat.getDateTime("2008-8-8"));
			info.setCode("code3");
			info.setAmount(200);
			info.setAddDay(3);
			info.setAcceptPOTypeID(2);
			info.setFormerOwner("FormerOwner3");
			// dao.saveDiscountBill(info);
			dao.updateDiscountBill(info);
			*/
			/*long[] lID = {0,1};
			dao.deleteDiscountBill(lID);
			*/
			DiscountInfo info = new DiscountInfo();
			info = dao.findDiscountByID(112);
			Log.print("bank:" + info.getApplyBank());
			Log.print("amount:" + info.getApplyDiscountAmount());
			//Log.print("documenttype:" + info.getDocumentType());
			/*DiscountBillInfo info = null;
			info = dao.findDiscountBillByID(2);
			Log.print("user："+info.getUser());
			Log.print("lSerialNo："+info.getSerialNo());
			Log.print("addday："+info.getAddDay());
			Log.print("strBank："+info.getBank());
			*/
			/*	Vector vctInfo = null;
				DiscountBillQueryInfo info = new DiscountBillQueryInfo();
				info.setDiscountID(1);
				info.setPageLineCount(3);
				info.setPageNo(2);
				info.setDate(DataFormat.getDateTime("2006-01-05"));
				vctInfo = dao.findDiscountBillByDiscountID(info);
				if (vctInfo != null)
				{
					Log.print("not null");
				}
				else
				{
					Log.print("null");
				}
				*/
			/*private long lDiscountID = -1;
			private long lContractID = -1;
			private long lPageLineCount= -1;
			private long lPageNo = -1;
			private long lOrderParam = -1;
			private long lDesc = -1;
			private double dRate = 0.0;
			private Timestamp tsDate = null;
			*/
			/*	DiscountInfo info = new DiscountInfo();
				info.setID(1);
				info.setInputUserID(1);
				info.setCurrencyID(1);
				info.setDocumentType("1111111111");
				dao.updateDiscount(info);
				*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public Collection findDiscountBillByContractID(DiscountBillQueryInfo info) throws Exception, IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		double dDiscountRate = 0; //利率
		Timestamp tsDiscountDate = null; //计息日
		double dExamineAmount = 0; //批准金额
		double dDiscountAccrual = 0; //利息
		double dCheckAmount = 0; //实付金额
		Timestamp tsEnd = null; //贴现日期
		String strEnd = ""; //贴现日期
		int nDays = 0; //实际贴现天数
		double dAmount = 0; //票据金额
		double dAccrual = 0; //贴现利息
		double dRealAmount = 0; //实付贴现金额
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额
		try
		{
			con = Database.getConnection();
			Log.print("======进入贴现计息(findBillInterestByID)方法======");
			Log.print("合同标示：" + info.getContractID());
			Log.print("凭证标示：" + info.getDiscountCredenceID());
			if (info.getContractID() > 0)
			{
				strSQL = " select a.* from Loan_ContractForm a where a.ID=? ";
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, info.getContractID());
				rs = ps.executeQuery();
				if (rs.next())
				{
					dExamineAmount = rs.getDouble("mExamineAmount"); //批准金额
					dRealAmount = rs.getDouble("mCheckAmount"); //核定金额
					dAccrual = dExamineAmount - dRealAmount; //贴现利息
					dDiscountRate = rs.getDouble("mDiscountRate"); //贴现利率
					tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + info.getContractID();
			}
			else
				if (info.getDiscountCredenceID() > 0)
				{
					//strSQL = " select a.* from Loan_ContractForm a, Loan_DiscountCredence b where a.ID=b.nContractID and b.ID=? ";
					strSQL = " select a.* from Loan_ContractForm a, OB_DiscountCredence b where a.ID=b.nContractID and b.ID=? ";
					ps = con.prepareStatement(strSQL);
					ps.setLong(1, info.getDiscountCredenceID());
					rs = ps.executeQuery();
					if (rs.next())
					{
						dExamineAmount = rs.getDouble("mExamineAmount"); //批准金额
						dRealAmount = rs.getDouble("mCheckAmount"); //核定金额
						dAccrual = dExamineAmount - dRealAmount; //贴现利息
						dDiscountRate = rs.getDouble("mDiscountRate"); //贴现利率
						tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountCredenceID=" + info.getDiscountCredenceID();
				}
			Log.print("======开始查询票据总数和总金额======");
			//计算记录总数
			strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) ";
			//strSQL = " from DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountApplyID=" + lDiscountApplyID;
			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
				dTotalRealAmount = rs.getDouble(3);
				dTotalAccrual = dTotalAmount - dTotalRealAmount;
			}
			Log.print("==============");
			Log.print("票据总张数=" + lRecordCount);
			Log.print("票据总金额=" + dTotalAmount);
			Log.print("票据总利息=" + dTotalAccrual);
			Log.print("总实付金额=" + dTotalRealAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("======结束查询票据总数和总金额======");
			lPageCount = lRecordCount / info.getPageLineCount();
			if ((lRecordCount % info.getPageLineCount()) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) info.getOrderParam())
			{
				case 1 :
					strSQL += " order by nSerialNo ";
					break;
				case 2 :
					strSQL += " order by sUserName ";
					break;
				case 3 :
					strSQL += " order by sBank ";
					break;
				case 4 :
					strSQL += " order by nIsLocal ";
					break;
				case 5 :
					strSQL += " order by dtCreate ";
					break;
				case 6 :
					strSQL += " order by dtEnd ";
					break;
				case 7 :
					strSQL += " order by nAddDays ";
					break;
				case 8 :
					strSQL += " order by sCode ";
					break;
				case 9 :
					strSQL += " order by mAmount ";
					break;
				case 10 :
					strSQL += " order by nAcceptPOTypeID";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}
			if (info.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (info.getPageNo() - 1) * info.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + info.getPageLineCount() - 1;
			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			Log.print("翻页查询开始");
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();
				dbill.setDiscountContractID(info.getContractID()); //贴现合同标示
				dbill.setDiscountDate(tsDiscountDate); //计息日
				dbill.setDiscountRate(dDiscountRate); //计息利率
				dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
				//ob使用标识
				dbill.setOBDiscountCredenceID(rs.getLong("ob_nDiscountCredenceID")); //
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setUser(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDay(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手
				//////////////////////////////////////////////////////////
				//dAmount = rs.getDouble("mAmount"); //汇票金额
				tsEnd = rs.getTimestamp("dtEnd");
				nDays = 0;
				if (tsEnd != null && tsDiscountDate != null)
				{
					strEnd = tsEnd.toString();
					tsEnd =
						new java.sql.Timestamp(
							new Integer(strEnd.substring(0, 4)).intValue() - 1900,
							new Integer(strEnd.substring(5, 7)).intValue() - 1,
							new Integer(strEnd.substring(8, 10)).intValue(),
							0,
							0,
							0,
							0);
					nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
				}
				if (nDays >= 0)
				{
					if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
					{
						nDays = nDays + 3;
					}
					//dAccrual = dAmount * (dDiscountRate / 360) * 0.01 * nDays;
					//dAccrual = DataFormat.formatDouble(dAccrual, 2);
					//dRealAmount = dAmount - dAccrual;
				}
				/*
				Log.print("========================");
				Log.print("贴现天数=" + nDays);
				Log.print("汇票金额=" + dAmount);
				Log.print("汇票利息=" + dAccrual);
				Log.print("实付金额=" + dRealAmount);
				Log.print("========================");
				dTotalAccrual = DataFormat.formatDouble(dTotalAccrual, 2) + DataFormat.formatDouble(dAccrual, 2);
				dTotalRealAmount = DataFormat.formatDouble(dTotalRealAmount, 2) + DataFormat.formatDouble(dRealAmount, 2);
				*/
				dbill.setDays(nDays); //实际贴现天数
				dbill.setCheckAmount(rs.getDouble("mCheckAmount")); //实付贴现金额
				dbill.setInterest(rs.getDouble("mAmount") - rs.getDouble("mCheckAmount")); //贴现利息
				//////////////////////////////////////////////////////////
				dbill.setCount(lRecordCount);
				//dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
				//dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalAmount, 2) - DataFormat.formatDouble(dTotalAccrual, 2));
				dbill.setTotalAmount(dTotalAmount);
				dbill.setTotalInterest(dTotalAccrual);
				dbill.setTotalAmount(dTotalRealAmount);
				v.add(dbill);
			}
			Log.print("翻页查询结束");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
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
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		Log.print("======退出贴现计息(findBillInterestByID)方法======");
		return (v.size() > 0 ? v : null);
	}
	/**
	* 查找一条贴现信息，操作数据库表DiscountApply，
	* @param lDiscountID 贴现标识
	*/
	public DiscountCredenceInfo findDiscountCredenceByID(long lDiscountCredenceID) throws Exception, IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		DiscountCredenceInfo lai = new DiscountCredenceInfo();
		try
		{
			con = Database.getConnection();
			strSQL = " select a.*, ";
			strSQL += " b.ID nContractID,b.sContractCode,b.nBorrowClientID,b.mExamineAmount,b.mCheckAmount,b.mDiscountRate,c.sName sClientName, ";
			strSQL += " d.sName sInputUserName,e.sAccountno sGrantCurrentAccount,e.sName sGrantName,f.sName AccName,f.sCode AccCode";
			strSQL += " from OB_DiscountCredence a, Loan_ContractForm b, Client c, ob_User d, Sett_Account e, Sett_Branch f ";
			strSQL += " where a.nContractID=b.ID and b.nBorrowClientID=c.ID(+) and a.nInputUserID=d.ID and b.nTypeID=? and a.ID=? ";
			strSQL += " and a.nGrantCurrentAccountID = e.ID(+) and a.nAccountBankID = f.ID(+)";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.LoanType.TX);
			ps.setLong(2, lDiscountCredenceID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//贴现申请
				lai.setDiscountContractID(rs.getLong("nContractID")); //贴现ID标识
				lai.setDiscountContractCode(rs.getString("sContractCode")); //贴现编号
				//申请单位
				lai.setApplyClientID(rs.getLong("nBorrowClientID")); //申请单位编号
				lai.setApplyClientName(rs.getString("sClientName")); //申请单位名称
				lai.setApplyAccount(rs.getString("sApplyAccount")); //申请单位账号
				lai.setApplyBank(rs.getString("sApplyBank")); //申请单位开户银行
				lai.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额
				lai.setCheckAmount(rs.getDouble("mCheckAmount")); //核定金额
				//lai.setInterest(rs.getDouble("mExamineAmount") - rs.getDouble("mCheckAmount")); //贴现利息
				lai.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
				//贴现凭证
				lai.setID(lDiscountCredenceID); //贴现凭证标识
				//lai.setCode(rs.getString("sCode")); //贴现凭证编号
				lai.setCode(rs.getString("sInstructionNo")); //贴现凭证编号
				lai.setFillDate(rs.getTimestamp("dtFillDate"));
				lai.setDraftTypeID(rs.getLong("nDraftTypeID")); //贴现汇票种类标示
				lai.setDraftCode(rs.getString("sDraftCode")); //贴现汇票号码
				lai.setPublicDate(rs.getTimestamp("dtPublicDate")); //发票日
				lai.setAtTerm(rs.getTimestamp("dtAtTerm")); //到期日
				lai.setAcceptClientName(rs.getString("sAcceptClient")); //承兑单位名称
				lai.setAcceptAccount(rs.getString("sAcceptAccount")); //承兑单位账号
				lai.setAcceptBank(rs.getString("sAcceptBank")); //承兑单位银行
				lai.setStatusID(rs.getLong("nStatusID")); //贴现凭证是否删除
				lai.setBillAmount(rs.getDouble("mAmount")); //贴现凭证金额
				lai.setBillInterest(rs.getDouble("mInterest")); //贴现凭证利息
				lai.setBillCheckAmount(rs.getDouble("mAmount") - rs.getDouble("mInterest")); //贴现凭证核定金额
				lai.setInputUserID(rs.getLong("nInputUserID"));
				lai.setInputUserName(rs.getString("sInputUserName"));
				lai.setInputDate(rs.getTimestamp("dtInputDate"));
				//lai.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //下一个审核人标示
				lai.setGrantTypeID(rs.getLong("nGrantTypeID"));
				lai.setAccountBankID(rs.getLong("nAccountBankID"));
				lai.setAccountBankCode(rs.getString("AccCode"));
				lai.setAccountBankName(rs.getString("AccName"));
				lai.setReceiveClientCode(rs.getString("sReceiveAccount"));
				lai.setReceiveClientName(rs.getString("sReceiveClientName"));
				lai.setRemitBank(rs.getString("sRemitBank"));
				lai.setRemitInProvince(rs.getString("sRemitInProvince"));
				lai.setRemitInCity(rs.getString("sRemitInCity"));
				lai.setGrantCurrentAccountID(rs.getLong("nGrantCurrentAccountID"));
				lai.setGrantCurrentAccountCode(rs.getString("sGrantCurrentAccount"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
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
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lai;
	}
	/**
	 * Method saveDiscountCredence.
	 * 保存贴现票据信息
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long saveDiscountCredence(DiscountCredenceInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		String strCredenceCode = null;
		long lMaxID = -1;
		int nMaxSerialNo = -1;
		try
		{
			Log.print("~~~~~~~~~~~进入方法saveDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//第一次暂存
			//得到最大的ID
			strSQL = "select nvl(max(ID)+1,1) from OB_discountcredence";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//strCredenceCode = createCredenceCode(info.getDiscountContractID());
			strCredenceCode = OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN);
			//存储记录
			strSQL =
				" Insert into Ob_DiscountCredence(ID,nContractID,dtFillDate,nDraftTypeID,sDraftCode, "
					+ " dtPublicDate,dtAtTerm,sApplyClient,sApplyAccount,sApplyBank,sAcceptClient,sAcceptAccount,sAcceptBank, "
					+ " mAmount,mRate,mInterest,nStatusID,nInputUserID,dtInputDate,sCode, "
					+ " nGrantTypeID,nAccountBankID,sReceiveAccount,sReceiveClientName,sRemitBank,sRemitInProvince,sRemitInCity,nGrantCurrentAccountID,SINSTRUCTIONNO) "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?) ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lMaxID);
			ps.setLong(2, info.getDiscountContractID());
			ps.setTimestamp(3, info.getFillDate());
			ps.setLong(4, info.getDraftTypeID());
			ps.setString(5, info.getDraftCode());
			ps.setTimestamp(6, info.getPublicDate());
			ps.setTimestamp(7, info.getAtTerm());
			ps.setString(8, info.getApplyClientName());
			ps.setString(9, info.getApplyAccount());
			ps.setString(10, info.getApplyBank());
			ps.setString(11, info.getAcceptClientName());
			ps.setString(12, info.getAcceptAccount());
			ps.setString(13, info.getAcceptBank());
			ps.setDouble(14, info.getBillAmount());
			ps.setDouble(15, info.getDiscountRate());
			ps.setDouble(16, info.getBillInterest());
			//ps.setLong(17, LOANConstant.DiscountCredenceStatus.SUBMIT);
			//初始为撰写状态
			ps.setLong(17, OBConstant.LoanInstrStatus.SAVE);
			ps.setLong(18, info.getInputUserID());
			ps.setString(19, strCredenceCode);
			ps.setLong(20, info.getGrantTypeID());
			ps.setLong(21, info.getAccountBankID());
			ps.setString(22, info.getReceiveClientCode());
			ps.setString(23, info.getReceiveClientName());
			ps.setString(24, info.getRemitBank());
			ps.setString(25, info.getRemitInProvince());
			ps.setString(26, info.getRemitInCity());
			ps.setLong(27, info.getGrantCurrentAccountID());
			ps.setString(28, strCredenceCode);
			ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lMaxID);
	}

    /**
     * ninh 2004-3-10
     * Method cancelDiscountCredenceByID.
     * 取消贴现票据明细信息
     * @param lDiscountCredenceID
     * @return long
     * @throws Exception
     */
    public long cancelDiscountCredenceByID(long lDiscountCredenceID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        String strSQL = null;
        long lResult = 0;

        try
        {
            con = Database.getConnection();

            strSQL = " update ob_DiscountCredence set nStatusID = ? where ID = ? ";
            ps = con.prepareStatement(strSQL);

            ps.setLong(1, Constant.RecordStatus.INVALID);
            ps.setLong(2, lDiscountCredenceID);

            lResult = ps.executeUpdate();
            

            ps.close();
            ps = null;
            
            strSQL = " update Loan_DiscountContractBill set ob_nDiscountCredenceID = null where ob_nDiscountCredenceID = ? ";
            ps = con.prepareStatement(strSQL);

            ps.setLong(1, lDiscountCredenceID);

            lResult = ps.executeUpdate();

            ps.close();
            ps = null;
            con.close();
            con = null;
        }
        catch (Exception ex)
        {
            log4j.error(ex.toString());
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
            catch (Exception ex)
            {
                log4j.error(ex.toString());
                throw new IException("Gen_E001");
            }
        }
        return lResult;
    }



	/**
	 * Method updateDiscountCredence.
	 * 更新贴现票据明细信息
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long updateDiscountCredence(DiscountCredenceInfo info) throws Exception
	{
		long lID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~进入方法updateDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//更新记录
			//更新记录
			strSQL =
				" update OB_DiscountCredence set sApplyAccount=?,sApplyBank=?,sAcceptClient=?,sAcceptAccount=?,sAcceptBank=?, "
					+ " nGrantTypeID=?,nAccountBankID=?,sReceiveAccount=?,sReceiveClientName=?,sRemitBank=?,sRemitInProvince=?,sRemitInCity=?,nGrantCurrentAccountID=? "
					+ " where ID=? ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			//ps.setLong(1, lDraftTypeID);
			ps.setString(1, info.getApplyAccount());
			ps.setString(2, info.getApplyBank());
			ps.setString(3, info.getAcceptClientName());
			ps.setString(4, info.getAcceptAccount());
			ps.setString(5, info.getAcceptBank());
			ps.setLong(6, info.getGrantTypeID());
			ps.setLong(7, info.getAccountBankID());
			ps.setString(8, info.getReceiveClientCode());
			ps.setString(9, info.getReceiveClientName());
			ps.setString(10, info.getRemitBank());
			ps.setString(11, info.getRemitInProvince());
			ps.setString(12, info.getRemitInCity());
			ps.setLong(13, info.getGrantCurrentAccountID());
			ps.setLong(14, info.getID());
			Log.print("lGrantTypeID=" + info.getGrantTypeID());
			Log.print("lAccountBankID=" + info.getAccountBankID());
			Log.print("strReceiveClientCode=" + info.getReceiveClientCode());
			Log.print("strReceiveClientName=" + info.getReceiveClientName());
			Log.print("strRemitBank=" + info.getRemitBank());
			Log.print("strRemitInProvince=" + info.getRemitInProvince());
			Log.print("strRemitInCity=" + info.getRemitInCity());
			Log.print("lGrantCurrentAccountID=" + info.getGrantCurrentAccountID());
			ps.executeUpdate();
			ps.close();
			ps = null;
			lID = info.getID();
			//lID = info.getDiscountBillID();
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	/**
	 * 更新贴现凭证记录状态
	 * @
	 */
	public long updateCredenceStatus(long lCredenceID, long lStatusID) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~进入方法updateCredenceStatus~~~~~~~~~~");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update ob_discountcredence set nStatusID=? where ID=? ");
			Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
			Log.print(sbSQL.toString());
			Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lCredenceID);
			if (ps.executeUpdate() > 0)
			{
				lResult = 1;
			}
			else
			{
				lResult = 0;
			}
			ps.close();
			ps = null;
			String strSQL = "";
			System.out.println("lID!!!!!!!!!!!!!===(*******)" + lResult + "pppppp" + lCredenceID);
			//如果是取消，着要吧相应的票据滞空
			System.out.println("lStattuuuuuuuuuuuuuuuuuuuuuuuuusID====" + lStatusID);
			if (lStatusID == OBConstant.LoanInstrStatus.CANCEL)
			{
				System.out.println("inininininininininininininininini!@");
				strSQL = " update Loan_DiscountContractBill set nDiscountCredenceID = null,ob_nDiscountCredenceID = null where ob_nDiscountCredenceID = ? ";
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, lCredenceID);
				lResult = ps.executeUpdate();
				System.out.println("lID!!!!!!!!!!!!!===(*******)" + lResult);
				ps.close();
				ps = null;
			}
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lResult;
	}
	/**
	 * 计算给定的贴现票据利息，操作DiscountBill表
	 * @param lContractID 贴现合同标识
	 * @param strBillID 贴现票据标识字符串，逗号分割
	 * @return 返回贴现票据的列表
	 */
	public DiscountBillInfo findDiscountBillByBillsID(long lContractID, String strBillID) throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		double dDiscountRate = 0; //利率
		Timestamp tsDiscountDate = null; //计息日
		double dExamineAmount = 0; //批准金额
		double dDiscountAccrual = 0; //利息
		double dCheckAmount = 0; //实付金额
		long lDiscountBillID = -1;
		long lBankCount = 0;
		Timestamp tsEnd = null; //贴现日期
		String strEnd = ""; //贴现日期
		int nDays = 0; //实际贴现天数
		double dAmount = 0; //票据金额
		double dAccrual = 0; //贴现利息
		double dRealAmount = 0; //实付贴现金额
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额
		DiscountBillInfo dbi = new DiscountBillInfo();
		try
		{
			conn = Database.getConnection();
			Log.print("======开始查询票据总数和总金额======");
			//计算记录总数
			strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) ";
			strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and ID in ( " + strBillID + " ) ";
			Log.print(strSelect + strSQL);
			ps = conn.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
				dTotalRealAmount = rs.getDouble(3);
			}
			Log.print("==============");
			Log.print("票据总张数=" + lRecordCount);
			Log.print("票据总金额=" + dTotalAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("======结束查询票据总数和总金额======");
			if (strBillID.indexOf(",") == -1)
			{
				if (Long.parseLong(strBillID.trim()) > 0)
				{
					dbi = findDiscountBillByID(Long.parseLong(strBillID.trim()));
				}
			}
			else
			{
				strSelect = " select count(*) ";
				strSQL =
					" from Loan_DiscountContractBill where nStatusID="
						+ Constant.RecordStatus.VALID
						+ " and ID in ( "
						+ strBillID
						+ " ) and nAcceptPOTypeID = "
						+ LOANConstant.DraftType.BANK;
				Log.print(strSelect + strSQL);
				ps = conn.prepareStatement(strSelect + strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lBankCount = rs.getLong(1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				if (lBankCount == lRecordCount)
				{
					dbi.setAcceptPOTypeID(LOANConstant.DraftType.BANK);
				}
				else
					if (lBankCount == 0)
					{
						dbi.setAcceptPOTypeID(LOANConstant.DraftType.BIZ);
					}
					else
					{
						dbi.setAcceptPOTypeID(-1);
					}
				dbi.setCode("详见明细表");
				dbi.setCreate(null);
				dbi.setEnd(null);
				dbi.setUser("详见明细表");
				dbi.setBank("详见明细表");
			}
			dbi.setTotalAmount(dTotalAmount);
			dbi.setTotalInterest(dTotalAmount - dTotalRealAmount);
			dbi.setTotalAmount(dTotalRealAmount);
			conn.close();
			conn = null;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return (dbi);
	}
	/**
	 *
	 * 生成贴现凭证编号
	 *
	 */
	private String createCredenceCode(long lContractID) throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		try
		{
			con = Database.getConnection();
			strSQL = " select nvl(max(sCode),0) sCode from Ob_DiscountCredence where nContractID = ? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				lCode = Long.parseLong(strCode) + 1;
				strCode = DataFormat.formatInt(lCode, 3, true);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		Log.print(":::::::::: ::::strcode::::::" + strCode);
		return strCode;
	}
	/**
	 * 将贴现凭证对应的贴现票据存盘，操作DiscountCredence表
	 * @param lDiscountCredenceID 贴现凭证标识，如果<=0计算，否则计算并修改
	 * @param lDiscountContractID 贴现合同标识
	 * @return 返回贴现凭证标识
	 */
	public long saveDiscountCredenceBill(long lDiscountCredenceID, long lDiscountContractID, long lDiscountBillID[]) throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSelect = null;
		String strSQL = null;
		long nStatusID = -1;
		int i = 0;
		long lRecordCount = -1;
		double dDiscountRate = 0; //利率
		Timestamp tsDiscountDate = null; //计息日
		double dExamineAmount = 0; //批准金额
		double dDiscountAccrual = 0; //利息
		double dCheckAmount = 0; //实付金额
		Timestamp tsEnd = null; //贴现日期
		String strEnd = ""; //贴现日期
		long nDays = 0; //实际贴现天数
		double dAmount = 0; //票据金额
		double dAccrual = 0; //贴现利息
		double dRealAmount = 0; //实付贴现金额
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额
		long lCredenceStatusID = -1; //贴现凭证状态
		try
		{
			conn = Database.getConnection();
			if (lDiscountCredenceID > 0)
			{
				//更新记录
				strSQL = " update Loan_DiscountContractBill set OB_nDiscountCredenceID=? where nDiscountCredenceID=? ";
				Log.print(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, -1);
				ps.setLong(2, lDiscountCredenceID);
				ps.executeUpdate();
				ps.close();
				ps = null;
				//更新记录
				for (i = 0; i < lDiscountBillID.length; i++)
				{
					if (lDiscountBillID[i] > 0)
					{
						//更新记录
						strSQL = " update Loan_DiscountContractBill set OB_nDiscountCredenceID=? where ID=? ";
						Log.print(strSQL);
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1, lDiscountCredenceID);
						ps.setLong(2, lDiscountBillID[i]);
						ps.executeUpdate();
						ps.close();
						ps = null;
					}
				}
				//strSQL = " select sum(nvl(mAmount,0)) mTotalAmount,sum(nvl(mCheckAmount,0)) mTotalCheckAmount from Loan_DiscountContractBill a where a.nDiscountCredenceID=? ";
				strSQL = " select sum(nvl(mAmount,0)) mTotalAmount,sum(nvl(mCheckAmount,0)) mTotalCheckAmount from Loan_DiscountContractBill a where a.ob_nDiscountCredenceID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, lDiscountCredenceID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					//dTotalAmount = Data.formatDouble(rs.getDouble(1),2);
					//dTotalRealAmount = DataFormat.formatDouble(rs.getDouble(2),2);
					//dTotalAccrual = DataFormat.formatDouble(dTotalAmount,2) - DataFormat.formatDouble(dTotalRealAmount,2);
					dTotalAmount = rs.getDouble(1);
					dTotalRealAmount = rs.getDouble(2);
					dTotalAccrual = dTotalAmount - dTotalRealAmount;
				}
				Log.print("==============");
				Log.print("汇票金额=" + dTotalAmount);
				Log.print("汇票利息=" + dTotalAccrual);
				Log.print("实付金额=" + dTotalRealAmount);
				Log.print("==============");
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = " update ob_DiscountCredence set mAmount=?, mInterest=? where ID=? ";
				Log.print(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setDouble(1, dTotalAmount);
				ps.setDouble(2, dTotalAccrual);
				ps.setLong(3, lDiscountCredenceID);
				ps.executeUpdate();
				ps.close();
				ps = null;
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
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lDiscountCredenceID;
	}
}