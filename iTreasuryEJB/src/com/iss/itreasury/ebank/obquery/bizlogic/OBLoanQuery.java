/*
 * Created on 2004-2-16
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obquery.bizlogic;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obquery.dataentity.*;
import com.iss.itreasury.ebank.obquery.dao.*;
import com.iss.itreasury.loan.loanapply.dataentity.*;
import com.iss.itreasury.loan.loanapply.dao.*;
import com.iss.itreasury.loan.query.dataentity.*;
import com.iss.itreasury.loan.discount.dataentity.*;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.*;

public class OBLoanQuery {

	public Collection queryLoan(OBQueryTermInfo termInfo) throws Exception
	{
		Collection c=null;
		OBQueryDao dao=new OBQueryDao();
		try {
			c=dao.queryLoan( termInfo );
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	public QuerySumInfo queryLoanApplySum(OBQueryTermInfo termInfo) throws Exception
	{
		QuerySumInfo sumInfo=null;
		OBQueryDao dao=new OBQueryDao();
		try {
			sumInfo=dao.queryLoanApplySum( termInfo );
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	}
	public LoanApplyInfo findByID(long lLoanID) throws Exception
	{
		LoanApplyDao dao=new LoanApplyDao();
		LoanApplyInfo applyInfo=null;
		try{
			applyInfo=dao.findByID(lLoanID);
		}catch(Exception e){
			throw e;
		}
		return applyInfo;
	}	
	public AssureInfo findAssureByID(long aID) throws Exception
	{
		FormAssureDao fDao=new FormAssureDao();
		AssureInfo aInfo=null;
        
		try
		{
			aInfo=fDao.findByID(aID);
		}
		catch (Exception e)
		{
		   throw e;
		}
		return aInfo;
	}
	public LoanPlanDetailInfo findPlanByID(long aPlanID) throws Exception
		{
			LoanRepayPlanDetailDao pdDao=new LoanRepayPlanDetailDao();
			LoanPlanDetailInfo pdInfo=null;
        
			try
			{
				pdInfo=pdDao.findByID(aPlanID);
			}
			catch (Exception e)
			{
				throw e;
			}
			return pdInfo;
		}
		
	public Collection findPlanByLoanID(long lLoanID,long lPageNo,long lPageLine,long lOrderParam,long lDesc) throws Exception
	{
		LoanRepayPlanDao pDao=new LoanRepayPlanDao();
		Collection c=null;
        
		try
		{
			c=pDao.findByLoanID(lLoanID,lPageNo,lPageLine,lOrderParam,lDesc);
		}
		catch (Exception e)
		{
			throw e;
		}
                
		return c;
	}
	public InterestRateInfo findInterestRateByID (long lID) throws Exception
	   {
		   //可能还会需要别的信息
		   long lOfficeID=0;
		   long lInputUserID=0;
		   long lUpdateUserID=0;

		   InterestRateInfo ii = new InterestRateInfo();
		   Connection con = null;
		   PreparedStatement ps = null;
		   ResultSet rs = null;

		   try
		   {
			   con = Database.getConnection();
			   StringBuffer sb = new StringBuffer();
			   sb.append(" SELECT a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,b.id as parentid ");
			   sb.append(" FROM loan_InterestRate a,loan_INTERESTRATETYPEINFO b ");
			   sb.append(" WHERE a.id=? and b.ID=a.NBANKINTERESTTYPEID ");

			   ps = con.prepareStatement(sb.toString());
			   ps.setLong(1, lID);
			   rs = ps.executeQuery();
			   if( rs != null && rs.next() )
			   {
				   ii.setID(rs.getLong("ID"));
				   ii.setBankInterestTypeID(rs.getLong("nBankInterestTypeID"));
				   ii.setInterestRate(rs.getDouble("mRate"));
				   ii.setValiDate(rs.getTimestamp("dtValidate"));
				   ii.setInputUserID(rs.getLong("nInputUserID"));
				   ii.setInputDate(rs.getTimestamp("dtInput"));
				   ii.setInterestRateCode(rs.getString("SINTERESTRATENO"));
				   ii.setInterestRateName(rs.getString("SINTERESTRATENAME"));

				   ii.setCheckUserID(rs.getLong("nModifyUserID"));

				   ii.setCheckDate(rs.getTimestamp("dtModify"));
				   //ii.m_lLoanType = rs.getLong("nCurrencyID");
				   //ii.m_lBankInterestID = rs.getLong("ParentID");        //这个实际就是上边的BankInterestTypeID

			   }

			   rs.close();rs = null;
			   ps.close();ps = null;


			   sb.setLength(0);
			   sb.append(" SELECT *  ");
			   sb.append(" FROM userinfo ");
			   sb.append(" WHERE id=? ");

			   ps = con.prepareStatement(sb.toString());
			   ps.setLong(1, ii.getCheckUserID());
			   rs = ps.executeQuery();
			   if( rs != null && rs.next() )
			   {
				   ii.setCheckUserName(rs.getString("sName"));
			   }

			   rs.close();rs = null;
			   ps.close();ps = null;

			   sb.setLength(0);
			   sb.append(" SELECT sName ");
			   sb.append(" FROM userinfo ");
			   sb.append(" WHERE id=? ");

			   ps = con.prepareStatement(sb.toString());
			   ps.setLong(1, ii.getInputUserID());
			   rs = ps.executeQuery();
			   if( rs != null && rs.next() )
			   {
				   ii.setInputUserName(rs.getString("sName"));
			   }


			   rs.close();rs = null;
			   ps.close();ps = null;
			   sb.setLength(0);

			   con.close();con = null;
		   }
		   catch(Exception e)
		   {
				throw e;
		   }
		   finally
		   {
			   try
			   {
				   if( rs != null )
				   {
					   rs.close();
					   rs = null;
				   }
				   if( ps != null )
				   {
					   ps.close();
					   ps = null;
				   }
				   if( con != null )
				   {
					   con.close();
					   con = null;
				   }
			   }
			   catch(Exception e)
			   {
				   throw e;
			   }
		   }
		   return ii;
	   }	
	public DiscountLoanInfo findDiscountByID(long lDiscountID) throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		DiscountLoanInfo lai = new DiscountLoanInfo();

		try
		{
			con = Database.getConnection();

			strSQL  = " select a.*, ";
			strSQL += " c.sName sClientName, c.sAccount, d.sName sInputUserName, nvl(e.sContractCode,'') sContractCode ";
			strSQL += " from Loan_LoanForm a,Client c,UserInfo d,Loan_ContractForm e ";
			strSQL += " where a.nBorrowClientID=c.ID(+) and a.nInputUserID=d.ID and a.ID=e.nLoanID(+) and a.ID=? ";

			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lDiscountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lai.setID(lDiscountID); //贴现ID标识
				lai.setDiscountCode(rs.getString("sApplyCode")); //贴现编号
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
				lai.setDiscountStartDate(rs.getTimestamp("dtStartDate")); //贴现开始日
				lai.setDiscountEndDate(rs.getTimestamp("dtEndDate")); //贴现到期日
				lai.setInputUserID(rs.getLong("nInputUserID"));
				lai.setInputUserName(rs.getString("sInputUserName"));
				lai.setInputDate(rs.getTimestamp("dtInputDate"));
				lai.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //下一个审核人标示
				if (lai.getStatusID() == LOANConstant.LoanStatus.SUBMIT && lai.getNextCheckUserID() != lai.getInputUserID())
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

			//strSQL = " select count(*),sum(nvl(mAmount,0)),count() from Loan_DiscountFormBill where nLoanID=? and nStatusID=? ";
			strSQL = " select sum(nvl(BankAmount,0))+sum(nvl(BizAmount,0)) BillAmount,sum(BankCount) BankCount,sum(BizCount) BizCount from ( "+
					 " select count(*) BankCount,sum(nvl(mAmount,0)) BankAmount,0 BizCount,0 BizAmount from Loan_DiscountFormBill where nLoanID=? and nStatusID=? and nAcceptPoTypeID=? "+
					 " union all "+
					 " select 0 BankCount,0 BankAmount,count(*) BizCount,sum(nvl(mAmount,0)) BizAmount from Loan_DiscountFormBill where nLoanID=? and nStatusID=? and nAcceptPoTypeID=? "+
					 " ) ";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lDiscountID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			ps.setLong(3, LOANConstant.DraftType.BANK);
			ps.setLong(4, lDiscountID);
			ps.setLong(5, Constant.RecordStatus.VALID);
			ps.setLong(6, LOANConstant.DraftType.BIZ);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lai.setBillAmount(rs.getDouble("BillAmount")); 	//申请贴现汇票总金额
				lai.setBankCount(rs.getLong("BankCount")); 		//银行承兑汇票（张数）
				lai.setBizCount(rs.getLong("BizCount")); 		//商业承兑汇票（张数）
				lai.setBillCount(rs.getLong("BankCount")+rs.getLong("BizCount")); //申请贴现汇票（张数）
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
			catch (Exception ex)
			{
				Log.print(ex.toString());
				throw new IException("Gen_E001");
			}
		}

		return lai;
	}
	/**
	 * 查询一个贴现申请下的贴现票据，操作DiscountBill表
	 * @param lDiscountApplyID 贴现标识
	 * @return 返回贴现票据的列表
	 */
	public Collection findDiscountBillByDiscountID(long lDicountApplyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
		throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;

		Vector v = new Vector();
		long lRecordCount = 0;
		long lPageCount = 0;
		long lRowNumStart = 0;
		long lRowNumEnd = 0;
		double dTotalAmount = 0; //总票据金额
		long lBankCount = 0;
		long lBizCount = 0;

		try
		{
			con = Database.getConnection();
			
			strSQL = " select sum(BankCount) BankCount,sum(BizCount) BizCount from ( "+
					 " select count(*) BankCount,0 BizCount from Loan_DiscountFormBill where nLoanID=? and nStatusID=? and nAcceptPoTypeID=? "+
					 " union all "+
					 " select 0 BankCount,count(*) BizCount from Loan_DiscountFormBill where nLoanID=? and nStatusID=? and nAcceptPoTypeID=? "+
					 " ) ";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lDicountApplyID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			ps.setLong(3, LOANConstant.DraftType.BANK);
			ps.setLong(4, lDicountApplyID);
			ps.setLong(5, Constant.RecordStatus.VALID);
			ps.setLong(6, LOANConstant.DraftType.BIZ);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lBankCount = rs.getLong("BankCount"); 	//银行承兑汇票（张数）
				lBizCount = rs.getLong("BizCount"); 	//商业承兑汇票（张数）
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			//计算记录总数
			strSelect = " select count(*),sum(mAmount) ";
			strSQL = " from Loan_DiscountFormBill where nStatusID=" + Constant.RecordStatus.VALID + " and nLoanID=" + lDicountApplyID;

			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
			}
			Log.print("==============");
			Log.print(lRecordCount);
			Log.print(dTotalAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			lPageCount = lRecordCount / lPageLineCount;

			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) lOrderParam)
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
					strSQL += " order by nAcceptPOTypeID";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();

				dbill.setDiscountApplyID(lDicountApplyID); //贴现标示
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手

				dbill.setCount(lRecordCount);
				dbill.setTotalAmount(dTotalAmount);
				dbill.setBankCount(lBankCount);
				dbill.setBizCount(lBizCount);
				v.add(dbill);
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
				throw new IException("Gen_E001");
			}
		}

		return (v.size() > 0 ? v : null);
	}
	   	
	/**
	 * 查询一个贴现申请下的贴现票据并计息，操作DiscountBill表
	 * @param lContractID 贴现合同标识
	 * @param lDiscountCredenceID 贴现凭证标识
	 * @return 返回贴现票据的列表
	 */
	public Collection findBillInterestByID(long lDiscountID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
		throws IException
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
		double dExamineAmount = 0; //票面金额
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

			Log.print("申请标示：" + lDiscountID);

			if (lDiscountID > 0)
			{
				strSQL = " select a.* from Loan_LoanForm a where a.ID=? ";

				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lDiscountID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dExamineAmount = rs.getDouble("mExamineAmount"); 	//票面金额
					dRealAmount = rs.getDouble("mCheckAmount"); 		//实付贴现额
					dAccrual = dExamineAmount - dRealAmount; 			//贴现利息
					dDiscountRate = rs.getDouble("mDiscountRate"); 		//贴现利率
					tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				
				strSQL = " from Loan_DiscountFormBill where nStatusID=" + Constant.RecordStatus.VALID + " and nLoanID=" + lDiscountID;

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

			lPageCount = lRecordCount / lPageLineCount;

			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) lOrderParam)
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

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

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

				dbill.setDiscountApplyID(lDiscountID); //贴现合同标示
				dbill.setDiscountDate(tsDiscountDate); //计息日
				dbill.setDiscountRate(dDiscountRate); //计息利率

				//dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
				//dbill.OB_lDiscountCredenceID = rs.getLong("ob_nDiscountCredenceID");
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手				
				//////////////////////////////////////////////////////////

				//dAmount = rs.getDouble("mAmount"); //汇票金额

				tsEnd = rs.getTimestamp("dtEnd");
				nDays = 0;
				if (tsEnd != null && tsDiscountDate != null)
				{
					strEnd = tsEnd.toString();
					tsEnd =	new java.sql.Timestamp(	new Integer(strEnd.substring(0, 4)).intValue() - 1900,new Integer(strEnd.substring(5, 7)).intValue() - 1,new Integer(strEnd.substring(8, 10)).intValue(),0,0,0,0);
					nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
				}
				if (nDays >= 0)
				{
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
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
				dbill.setRealAmount(rs.getDouble("mCheckAmount"));//实付贴现金额
				dbill.setAccrual(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount"));//贴现利息
				
				//////////////////////////////////////////////////////////

				dbill.setCount(lRecordCount);
				//dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
				//dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));
				dbill.setTotalAmount(dTotalAmount);
				dbill.setTotalAccrual(dTotalAccrual);
				dbill.setTotalRealAmount(dTotalRealAmount);
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalAmount, 2) - DataFormat.formatDouble(dTotalAccrual, 2));
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
			catch (Exception ex)
			{
				Log.print(ex.toString());
				throw new IException("Gen_E001");
			}
		}

		Log.print("======退出贴现计息(findBillInterestByID)方法======");

		return (v.size() > 0 ? v : null);

	}	 
	
	/**
	 * 查询一个贴现申请下的贴现票据并计息，操作DiscountBill表
	 * @param lContractID 贴现合同标识
	 * @param lDiscountCredenceID 贴现凭证标识
	 * @return 返回贴现票据的列表
	 */
	public Collection findBillInterestByID(long lContractID, long lDiscountCredenceID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
		throws IException
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

			Log.print("合同标示：" + lContractID);
			Log.print("凭证标示：" + lDiscountCredenceID);

			if (lContractID > 0)
			{
				strSQL = " select a.* from Loan_ContractForm a where a.ID=? ";

				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dExamineAmount = rs.getDouble("mExamineAmount"); 	//批准金额
					dRealAmount = rs.getDouble("mCheckAmount"); 		//核定金额
					dAccrual = dExamineAmount - dRealAmount; 			//贴现利息
					dDiscountRate = rs.getDouble("mDiscountRate"); 		//贴现利率
					tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + lContractID;

			}
			else if (lDiscountCredenceID > 0)
			{
				strSQL = " select a.* from Loan_ContractForm a, Loan_DiscountCredence b where a.ID=b.nContractID and b.ID=? ";

				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lDiscountCredenceID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dExamineAmount = rs.getDouble("mExamineAmount"); 	//批准金额
					dRealAmount = rs.getDouble("mCheckAmount");			//核定金额
					dAccrual = dExamineAmount - dRealAmount; 			//贴现利息
					dDiscountRate = rs.getDouble("mDiscountRate"); 		//贴现利率
					tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountCredenceID=" + lDiscountCredenceID;

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

			lPageCount = lRecordCount / lPageLineCount;

			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) lOrderParam)
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

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

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

				dbill.setDiscountContractID(lContractID); //贴现合同标示
				dbill.setDiscountDate(tsDiscountDate); //计息日
				dbill.setDiscountRate(dDiscountRate); //计息利率

				dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
				//dbill.OB_lDiscountCredenceID = rs.getLong("ob_nDiscountCredenceID");
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
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
				dbill.setRealAmount(rs.getDouble("mCheckAmount"));//实付贴现金额
				dbill.setAccrual(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount"));//贴现利息

				//////////////////////////////////////////////////////////

				dbill.setCount(lRecordCount);
				//dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
				//dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalAmount, 2) - DataFormat.formatDouble(dTotalAccrual, 2));
				dbill.setTotalAmount(dTotalAmount);
				dbill.setTotalAccrual(dTotalAccrual);
				dbill.setTotalRealAmount(dTotalRealAmount);
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
				throw new IException("Gen_E001");
			}
		}

		Log.print("======退出贴现计息(findBillInterestByID)方法======");

		return (v.size() > 0 ? v : null);

	}
	public Collection findDiscountBillByContractID(long lDiscountContractID,
												  long lPageLineCount,
												  long lPageNo,
												  long lOrderParam,
												  long lDesc) throws IException
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
		double dTotalAmount = 0; //总票据金额

		try
		{
			con = Database.getConnection();

			//计算记录总数
			strSelect = " select count(*),sum(mAmount) ";
			strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + lDiscountContractID;

			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
			}
			Log.print("==============");
			Log.print(lRecordCount);
			Log.print(dTotalAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			lPageCount = lRecordCount / lPageLineCount;

			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) lOrderParam)
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
					strSQL += " order by nAcceptPOTypeID";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();

				dbill.setDiscountContractID(lDiscountContractID); //贴现标示
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手

				dbill.setCount(lRecordCount);
				dbill.setTotalAmount(dTotalAmount);
				v.add(dbill);
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
				throw new IException("Gen_E001");
			}
		}

		return (v.size() > 0 ? v : null);
	}
	
	public static void main(String[] args) {
	}
}
