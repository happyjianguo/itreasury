/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              yehuang 
 * @version
 *  Date of Creation    2004-4-9
 * 
 * ***特殊说明：部分方直接写到了本delegation里面，没有涉及DAO ***
 */
package com.iss.itreasury.securities.bizdelegation;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.closesystem.GLDealBean;
import com.iss.itreasury.dao.ITreasuryDAOException;
//import com.iss.itreasury.glinterface.oracle.OracleFinInterface; //zpli 2005-09-14
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.AccountRecordInfo;
import com.iss.itreasury.securities.query.dataentity.AccountTransactionInfo;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_AccountTypeDAO;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountTypeInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.dao.SEC_GLEntryDefinitionDAO;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GLDelegation
{
	private SEC_GLEntryDefinitionDAO GLEntryFacade = null;
	private SEC_AccountTypeDAO AccountTypeFacade = null;
	public GLDelegation()
	{
		GLEntryFacade = new SEC_GLEntryDefinitionDAO();
		AccountTypeFacade = new SEC_AccountTypeDAO();
	}
	/**
	 * 日结会计帐汇总
	 * 
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 * @param tsDateStart 起始日期
	 * @param tsDateEnd 结束日期
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 */
	public Collection findDailyAccountRecord(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
		throws RemoteException
	{
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到帐户类型，账户编号
			conn = Database.getConnection();
			double dDebitsSumAmount = 0.00;
			double dCreditSumAmount = 0.00;
			long lDirection = 0;
			sbSQL.append(
				"select sum(AMOUNT) sumamount,TransDIRECTION  from sec_GLENTRY  where OfficeID="
					+ lOfficeID
					+ " and CurrencyID="
					+ lCurrencyID
					+ " and Executedate between ? and ? and nvl(sec_GLENTRY.StatusID,0)>0  group by TransDIRECTION order by  TransDIRECTION ");
			Log.print("查询会总金额：" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setTimestamp(1, tsDateStart);
			ps.setTimestamp(2, tsDateEnd);
			rs = ps.executeQuery();
			while (rs.next())
			{
				lDirection = rs.getLong("TransDIRECTION");
				if (lDirection == SECConstant.DebitOrCredit.DEBIT)
					dDebitsSumAmount = rs.getDouble("sumamount");
				else if (lDirection == SECConstant.DebitOrCredit.CREDIT)
					dCreditSumAmount = rs.getDouble("sumamount");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sbSQL.setLength(0);
			sbSQL.append(" select Sec_GlSubjectDefinition.SubjectCode,Sec_GlSubjectDefinition.nSubjectType, \n");
			sbSQL.append(" DebitSec_GlEntry.mDebit,DebitSec_GlEntry.nDebitCount, \n");
			sbSQL.append(" LoanSec_GlEntry.mLoan,LoanSec_GlEntry.nLoanCount, \n");
			sbSQL.append(" SubjectPreBalance.mBalance mPreBalance,SubjectCurrentBalance.mBalance mCurrentBalance \n");
			sbSQL.append(" from \n");
			sbSQL.append(" ( \n");
			sbSQL.append(" select distinct sRootSubject SubjectCode,nSubjectType \n");
			sbSQL.append(" from Sett_VGlSubjectDefinition \n");
			sbSQL.append(" where nOfficeID=" + lOfficeID + " and nCurrencyID=" + lCurrencyID + " \n");
			sbSQL.append(" )  Sec_GlSubjectDefinition, \n");
			sbSQL.append(" ( \n");
			
			//Modify by leiyang 2007-11-13
			
			/*sbSQL.append(" select b.sRootSubject SubjectCode,sum(a.Amount) mDebit,count(a.deliveryorderCode) nDebitCount \n");
			sbSQL.append(" from Sec_GlEntry a,Sett_VGlSubjectDefinition b \n");
			sbSQL.append(
				" where a.SubjectCode=b.sSubjectCode(+) and  a.OfficeID="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection =  "
					+ SECConstant.DebitOrCredit.DEBIT
					+ " \n");
			sbSQL.append(" and a.Executedate between  to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append(" group by b.sRootSubject \n");
			sbSQL.append(" ) DebitSec_GlEntry, \n");*/
			
			sbSQL.append(" select a.SubjectCode, sum(a.mDebit) mDebit, count(a.nDebitCount) nDebitCount \n");
			sbSQL.append(" from (select distinct a.subjectcode SubjectCode, a.Amount mDebit, a.deliveryorderCode nDebitCount \n");
			sbSQL.append("   from Sec_GlEntry a, Sett_VGlSubjectDefinition b");
			sbSQL.append(" where a.SubjectCode=b.sSubjectCode(+) and  a.OfficeID="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection =  "
					+ SECConstant.DebitOrCredit.DEBIT
					+ " \n");
			sbSQL.append(" and a.Executedate between  to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ) a group by a.SubjectCode \n");
			sbSQL.append(" ) DebitSec_GlEntry, \n");
			sbSQL.append(" ( \n");
			
			//Modify by leiyang 2007-11-13
			
			/*sbSQL.append(" select b.sRootSubject SubjectCode,sum(a.Amount) mLoan,count(a.deliveryorderCode) nLoanCount \n");
			sbSQL.append(" from Sec_GlEntry a,Sett_VGlSubjectDefinition b \n");
			sbSQL.append(
				" where a.SubjectCode=b.sSubjectCode(+) and  a.OfficeID="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection = "
					+ SECConstant.DebitOrCredit.CREDIT
					+ " \n");
			sbSQL.append(
				" and a.Executedate between to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append(" group by b.sRootSubject \n");
			sbSQL.append(" ) LoanSec_GlEntry, \n");*/

			sbSQL.append(" select a.SubjectCode, sum(a.mLoan) mLoan, count(a.nLoanCount) nLoanCount \n");
			sbSQL.append(" from (select distinct a.subjectcode SubjectCode, a.Amount mLoan, a.deliveryorderCode nLoanCount from Sec_GlEntry a, Sett_VGlSubjectDefinition b \n");
			sbSQL.append(" where a.SubjectCode = b.sSubjectCode(+)and a.OfficeID ="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection = "
					+ SECConstant.DebitOrCredit.CREDIT
					+ " \n");
			sbSQL.append(" and a.Executedate between to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ) a group by a.SubjectCode \n");
			sbSQL.append(" ) LoanSec_GlEntry, \n");
			
			sbSQL.append(" ( \n");
			sbSQL.append(" select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject SubjectCode \n");
			sbSQL.append(" from Sett_GlBalance a,Sett_VGlSubjectDefinition b \n");
			sbSQL.append(
				" where  a.sGLSubjectCode=b.sSubjectCode(+) and  a.dtGlDate   = to_date('"
					+ DataFormat.getDateString(DataFormat.getPreviousDate(tsDateStart))
					+ "','yyyy-mm-dd')  and a.nOfficeID="
					+ lOfficeID
					+ " and a.nCurrencyID="
					+ lCurrencyID
					+ " group by b.sRootSubject \n");
			sbSQL.append(" ) SubjectPreBalance, \n");
			sbSQL.append(" ( \n");
			sbSQL.append(" select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject SubjectCode \n");
			sbSQL.append(" from Sett_GlBalance a,Sett_VGlSubjectDefinition b \n");
			sbSQL.append(
				" where  a.sGLSubjectCode=b.sSubjectCode(+) and  a.dtGlDate   = to_date('"
					+ DataFormat.getDateString(tsDateStart)
					+ "','yyyy-mm-dd')  and a.nOfficeID="
					+ lOfficeID
					+ " and a.nCurrencyID="
					+ lCurrencyID
					+ " group by b.sRootSubject \n");
			sbSQL.append(" ) SubjectCurrentBalance \n");
			sbSQL.append(" where Sec_GlSubjectDefinition.SubjectCode=DebitSec_GlEntry.SubjectCode(+) and Sec_GlSubjectDefinition.SubjectCode=DebitSec_GlEntry.SubjectCode(+) \n");
			sbSQL.append(
				" and Sec_GlSubjectDefinition.SubjectCode=LoanSec_GlEntry.SubjectCode(+)  and Sec_GlSubjectDefinition.SubjectCode=SubjectPreBalance.SubjectCode(+)  and Sec_GlSubjectDefinition.SubjectCode=SubjectCurrentBalance.SubjectCode(+) \n");
			sbSQL.append(" and (DebitSec_GlEntry.mDebit <> 0 or LoanSec_GlEntry.mLoan <> 0 or DebitSec_GlEntry.nDebitCount <> 0 or LoanSec_GlEntry.nLoanCount <> 0) ");
			//查询记录
			String strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " )  ";
			switch ((int) lOrderParam)
			{
				case 1 :
				default :
					strSQLRecord = strSQLRecord + " order by SubjectCode ";
					break;
					//                case 2:
					//                    break;
				case 3 :
					strSQLRecord = strSQLRecord + " order by mDebit ";
					break;
				case 4 :
					strSQLRecord = strSQLRecord + " order by mLoan ";
					break;
				case 5 :
					strSQLRecord = strSQLRecord + " order by nDebitCount";
					break;
				case 6 :
					strSQLRecord = strSQLRecord + " order by nLoanCount ";
					break;
				case 7 :
					strSQLRecord = strSQLRecord + " order by mBalance ";
					break;
				case 8 :
					strSQLRecord = strSQLRecord + " order by mBalance ";
					break;
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQLRecord += " desc";
			}
			Log.print(strSQLRecord);
			ps = conn.prepareStatement(strSQLRecord);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AccountRecordInfo accountRecordInfo = new AccountRecordInfo();
				accountRecordInfo.account = rs.getString("SubjectCode"); //科目号
				accountRecordInfo.debitAmount = rs.getDouble("mDebit"); //借方合计
				accountRecordInfo.loanAmount = rs.getDouble("mLoan"); //贷方合计
				accountRecordInfo.debitsSumAmount = dDebitsSumAmount; //满足条件全部借方合计,
				accountRecordInfo.creditSumAmount = dCreditSumAmount; //满足条件全部贷方合计，
				accountRecordInfo.debitNumber = rs.getLong("nDebitCount");
				accountRecordInfo.creditNumber = rs.getLong("nLoanCount");
				accountRecordInfo.number = accountRecordInfo.debitNumber + accountRecordInfo.creditNumber; //凭证数量
				if (DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)))
				{
					accountRecordInfo.startBalance = rs.getDouble("mCurrentBalance");
				}
				else
				{
					accountRecordInfo.startBalance = rs.getDouble("mPreBalance");
				}
				long lSubjectTypeID = rs.getLong("nSubjectType");
				if (SECConstant.SubjectAttribute.getDirection(lSubjectTypeID) == SECConstant.DebitOrCredit.DEBIT)
				{
					accountRecordInfo.endBalance = accountRecordInfo.startBalance + accountRecordInfo.debitAmount - accountRecordInfo.loanAmount;
				}
				else
				{
					accountRecordInfo.startBalance = -accountRecordInfo.startBalance;
					accountRecordInfo.endBalance = accountRecordInfo.startBalance + accountRecordInfo.loanAmount - accountRecordInfo.debitAmount;
				}
				Log.print("******accountRecordInfo.m_strAccount=" + accountRecordInfo.account);
				accountRecordInfo.pageCount = 1;
				v.add(accountRecordInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return (v.size() > 0 ? v : null);
	}
	/**
	 * 查询科目号下交易类型汇总
	 * 
	 * @param strAccount 会计帐号
	 * @param strTransNo 交易号
	 * @param lTypeID 借或者贷
	 * @param lTransTypeID 交易类型 
	 * @param tsDateStart 起始日期
	 * @param tsDateEnd 结束日期
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 */
	public Collection findGLDetails(
		String strRootAccount,
		String strAccount,
		String strTransNo,
		long lOfficeID,
		long lCurrencyID,
		long lTypeID,
		long lTransTypeID,
		Timestamp tsDateStart,
		Timestamp tsDateEnd,
		long lPageLineCount,
		long lPageNo,
		long lOrderParam,
		long lDesc)
		throws RemoteException
	{
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到帐户类型，账户编号
			conn = Database.getConnection();
			sbSQL.append(" select distinct a.OfficeID,a.CurrencyID,a.SubjectCode,a.TransactionTypeID,a.TransDirection, \n");
			sbSQL.append(" a.deliveryordercode, a.Amount,a.InterestStartDate,a.ExecuteDate,a.remark,a.InputUserID,a.CheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
			sbSQL.append(" from  Sec_GlEntry a,Sett_VGlSubjectDefinition b,UserInfo c,UserInfo d ");
			sbSQL.append(
				" where a.SubjectCode=b.sSubjectCode(+) and a.InputUserID=c.ID(+) and a.CheckUserID=d.ID(+) and a.OfficeID= "
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 ");
			if (strRootAccount != null && strRootAccount.length() > 0)
			{
				sbSQL.append(" and b.sRootSubject = '" + strRootAccount + "'");
			}
			if (strAccount != null && strAccount.length() > 0)
			{
				sbSQL.append(" and b.sSubjectCode = '" + strAccount + "'");
			}
			if (strTransNo != null && strTransNo.length() > 0)
			{
				sbSQL.append(" and a.deliveryorderCode = '" + strTransNo + "'");
			}
			if (lTypeID > 0)
			{
				sbSQL.append(" and a.TransDirection = " + lTypeID);
			}
			if (lTransTypeID > 0)
			{
				sbSQL.append(" and a.TransactionTypeID = " + lTransTypeID);
			}
			if (tsDateStart != null && tsDateEnd != null)
			{
				sbSQL.append(" and   a.Executedate between ? and ? ");
			}
			String strSQLRecordCount = "";
			strSQLRecordCount = "select count(*) nCount,sum(Amount) Amount from (  " + sbSQL.toString() + ") aa";
			ps = conn.prepareStatement(strSQLRecordCount);
			Log.print(strSQLRecordCount);
			int nPos = 1;
			if (tsDateStart != null && tsDateEnd != null)
			{
				ps.setTimestamp(nPos++, tsDateStart);
				ps.setTimestamp(nPos++, tsDateEnd);
			}
			Log.print("********************** before count*********************");
			rs = ps.executeQuery();
			Log.print("********************** after count*********************");
			long lRecordCount = 0; //记录数目
			double dSumAmount = 0.0;
			if (rs.next())
			{
				lRecordCount = rs.getLong(1);
				dSumAmount = rs.getDouble(2);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//计算总页数
			long lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			//查询记录
			String strSQLRecord = "select * from  ( select aa.*,rownum r from ( " + sbSQL.toString();
			switch ((int) lOrderParam)
			{
				case 1 :
				default :
					strSQLRecord = strSQLRecord + " order by InterestStartDate ";
					break;
				case 2 :
					strSQLRecord = strSQLRecord + " order by deliveryordercode ";
					break;
				case 3 :
					strSQLRecord = strSQLRecord + " order by TransactionTypeID ";
					break;
				case 4 :
					strSQLRecord = strSQLRecord + " order by SubjectCode ";
					break;
				case 5 :
					strSQLRecord = strSQLRecord + " order by Amount ";
					break;
				case 6 :
					strSQLRecord = strSQLRecord + " order by TransDirection ";
					break;
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQLRecord += " desc";
			}
			long lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			long lRowNumEnd = lRowNumStart + lPageLineCount - 1;
			strSQLRecord = strSQLRecord + " ) aa ) where r between " + lRowNumStart + " and  " + lRowNumEnd;
			Log.print(strSQLRecord);
			ps = conn.prepareStatement(strSQLRecord);
			nPos = 1;
			if (tsDateStart != null && tsDateEnd != null)
			{
				ps.setTimestamp(nPos++, tsDateStart);
				ps.setTimestamp(nPos++, tsDateEnd);
			}
			Log.print("before query");
			rs = ps.executeQuery();
			Log.print("after query");
			while (rs.next())
			{
				AccountTransactionInfo accountTransactionInfo = new AccountTransactionInfo();
				accountTransactionInfo.execute = rs.getTimestamp("ExecuteDate"); //执行日期
				//accountTransactionInfo.m_lTransactionNoID = rs.getLong("nTransactionNoID"); //交易标识
				accountTransactionInfo.transactionTypeID = rs.getLong("TransactionTypeID");
				accountTransactionInfo.transactionType = SECConstant.BusinessType.getName(accountTransactionInfo.transactionTypeID);
				accountTransactionInfo.transNo = rs.getString("deliveryordercode"); //交易号
				accountTransactionInfo.account = rs.getString("SubjectCode"); //科目号
				accountTransactionInfo.amount = rs.getDouble("Amount"); //金额
				accountTransactionInfo.directionID = rs.getLong("TransDirection"); //借贷标识
				accountTransactionInfo.direction = SECConstant.DebitOrCredit.getName(accountTransactionInfo.directionID);
				//借贷名称
				//accountTransactionInfo.m_lStatusID = rs.getLong("nStatusID"); //状态
				//accountTransactionInfo.m_strStatus = SETTConstant.TransactionStatus.getName(accountTransactionInfo.m_lStatusID); //状态名称
				accountTransactionInfo.pageCount = lPageCount;
				accountTransactionInfo.totalRecordes = lRecordCount;
				accountTransactionInfo.totalAmount = dSumAmount;
				//得到对应科目号
				long lOtherDirection = accountTransactionInfo.directionID == SECConstant.DebitOrCredit.DEBIT ? SECConstant.DebitOrCredit.CREDIT : SECConstant.DebitOrCredit.DEBIT;
				String strOtherSQL =
					"select distinct SubjectCode from Sec_GlEntry where deliveryordercode='" + accountTransactionInfo.transNo + "' and TransDirection=" + lOtherDirection + " and nvl(StatusID,0)>0 ";
				PreparedStatement psOther = conn.prepareStatement(strOtherSQL);
				ResultSet rsOther = psOther.executeQuery();
				Vector vectorOtherAccount = new Vector(); //对方科目号
				while (rsOther.next())
				{
					accountTransactionInfo.otherAccountRecord = accountTransactionInfo.otherAccountRecord + rsOther.getString("SubjectCode");
				}
				rsOther.close();
				rsOther = null;
				psOther.close();
				psOther = null;
				accountTransactionInfo = findTransactionDetail(accountTransactionInfo);
				accountTransactionInfo.strAbstract = rs.getString("remark");
				accountTransactionInfo.inputUser = rs.getString("sInputUserName");
				accountTransactionInfo.checkUser = rs.getString("sCheckUserName");
				if (accountTransactionInfo.inputUser == null || accountTransactionInfo.inputUser.length() <= 0)
				{
					accountTransactionInfo.inputUser = "机制";
				}
				if (accountTransactionInfo.checkUser == null || accountTransactionInfo.checkUser.length() <= 0)
				{
					accountTransactionInfo.checkUser = "机核";
				}
				v.add(accountTransactionInfo);
			}
			Log.print("after circle");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		Log.print("********************** finish *********************");
		return (v.size() > 0 ? v : null);
	}
	/**
	 * 根据交易号得到交易的详细情况	 
	 * 
	 * @param accountTransactionInfo
	 * @return
	 * @throws RemoteException
	 */
	public AccountTransactionInfo findTransactionDetail(AccountTransactionInfo accountTransactionInfo) throws RemoteException
	{
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到帐户类型，账户编号
			conn = Database.getConnection();
			sbSQL.append("  select ID,OfficeID,CurrencyID,TransactionTypeID,StatusID,InputUserID,CheckUserID,remark ");
			sbSQL.append("  from Sec_deliveryorder  ");
			sbSQL.append("  where code = '" + accountTransactionInfo.transNo + "' ");
			ps = conn.prepareStatement(sbSQL.toString());
			Log.print("before query");
			Log.print(sbSQL.toString());
			rs = ps.executeQuery();
			Log.print("after query");
			if (rs.next())
			{
				accountTransactionInfo.strAbstract = rs.getString("remark"); //摘要
				accountTransactionInfo.inputUser = NameRef.getUserNameCodeByID(rs.getLong("InputUserID")); //录入人
				accountTransactionInfo.checkUser = NameRef.getUserNameCodeByID(rs.getLong("CheckUserID")); //复核人
				accountTransactionInfo.statusID = rs.getLong("StatusID"); //状态
				accountTransactionInfo.status = SECConstant.TransactionStatus.getName(accountTransactionInfo.statusID); //状态名称
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		Log.print("********************** finish *********************");
		return accountTransactionInfo;
	}
	/**
	 * 导入科目余额
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param tsStartDate
	 * @param tsEndDate
	 * @return
	 * @throws RemoteException
	 */
	public boolean importSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsStartDate, Timestamp tsEndDate) throws RemoteException
	{ 
		boolean bIsSuccess = true;
		try
		{ 	
			bIsSuccess = GLDealBean.addSubjectBalance(lOfficeID, lCurrencyID,Constant.ModuleType.SECURITIES, tsStartDate,tsEndDate);
			 
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			bIsSuccess = false;
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			 
		}
		return bIsSuccess;
	}
	/**
	 * 日结会计帐汇总
	 * 
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 * @param tsDateStart 起始日期
	 * @param tsDateEnd 结束日期
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 */
	public Collection findGLTransType(String strAccount, long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
		throws RemoteException
	{
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到帐户类型，账户编号
			conn = Database.getConnection();
			//added by jiangwei
			sbSQL.setLength(0);
			sbSQL.append(" select Sett_TransTypeDefinition.TransactionTypeID, \n");
			sbSQL.append(" DebitSett_GlEntry.mDebit,DebitSett_GlEntry.nDebitCount, \n");
			sbSQL.append(" LoanSett_GlEntry.mLoan,LoanSett_GlEntry.nLoanCount  \n");
			sbSQL.append(" from \n");
			sbSQL.append(" ( \n");
			sbSQL.append(" select distinct a.TransactionTypeID \n");
			sbSQL.append(" from Sec_GlEntry a,Sett_VGlSubjectDefinition b  \n");
			sbSQL.append(" where a.SubjectCode=b.sSubjectCode(+) and a.OfficeID=" + lOfficeID + " and a.CurrencyID=" + lCurrencyID + " and nvl(a.StatusID,0) >0 " + " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.ExecuteDate between ? and ? \n");
			sbSQL.append(" ) Sett_TransTypeDefinition, ");
			sbSQL.append(" ( \n");
			
			//Modify by leiyang 2007-11-14
			/*sbSQL.append(" select a.TransactionTypeID,sum(a.Amount) mDebit,count(a.deliveryorderCode) nDebitCount \n");
			sbSQL.append(" from Sec_GlEntry a,Sett_VGlSubjectDefinition b  \n");
			sbSQL.append(
				" where a.SubjectCode=b.sSubjectCode(+) and a.OfficeID="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection =  "
					+ SECConstant.DebitOrCredit.DEBIT
					+ " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.ExecuteDate between ? and ? \n");
			sbSQL.append(" group by a.TransactionTypeID \n");
			sbSQL.append(" ) DebitSett_GlEntry, \n");*/
			sbSQL.append(" select a.TransactionTypeID, sum(a.mDebit) mDebit, count(a.nDebitCount) nDebitCount \n");
			sbSQL.append(" from (select distinct a.TransactionTypeID TransactionTypeID, a.Amount mDebit, a.deliveryorderCode nDebitCount from Sec_GlEntry a, Sett_VGlSubjectDefinition b  \n");
			sbSQL.append(" where a.SubjectCode=b.sSubjectCode(+) and a.OfficeID="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection =  "
					+ SECConstant.DebitOrCredit.DEBIT
					+ " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.ExecuteDate between ? and ? \n");
			sbSQL.append(" ) a group by a.TransactionTypeID \n");
			sbSQL.append(" ) DebitSett_GlEntry, \n");
			sbSQL.append(" ( \n");
			
			//Modify by leiyang 2007-11-14
			/*sbSQL.append(" select a.TransactionTypeID,sum(a.Amount) mLoan,count(a.deliveryorderCode) nLoanCount \n");
			sbSQL.append(" from Sec_GlEntry a,Sett_VGlSubjectDefinition b  \n");
			sbSQL.append(
				" where a.SubjectCode=b.sSubjectCode(+) and a.OfficeID="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection = "
					+ SECConstant.DebitOrCredit.CREDIT
					+ " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.ExecuteDate between ? and ? \n");
			sbSQL.append(" group by a.TransactionTypeID \n");
			sbSQL.append(" ) LoanSett_GlEntry \n");*/
			sbSQL.append(" select a.TransactionTypeID, sum(a.mLoan) mLoan, count(a.nLoanCount) nLoanCount \n");
			sbSQL.append(" from (select distinct a.TransactionTypeID TransactionTypeID, a.Amount mLoan, a.deliveryorderCode nLoanCount from Sec_GlEntry a, Sett_VGlSubjectDefinition b  \n");
			sbSQL.append(" where a.SubjectCode=b.sSubjectCode(+) and a.OfficeID="
					+ lOfficeID
					+ " and a.CurrencyID="
					+ lCurrencyID
					+ " and nvl(a.StatusID,0) >0 "
					+ " and a.TransDirection = "
					+ SECConstant.DebitOrCredit.CREDIT
					+ " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.ExecuteDate between ? and ? \n");
			sbSQL.append(" ) a group by a.TransactionTypeID  \n");
			sbSQL.append(" ) LoanSett_GlEntry \n");
			
			
			sbSQL.append(" where Sett_TransTypeDefinition.TransactionTypeID=DebitSett_GlEntry.TransactionTypeID(+) \n");
			sbSQL.append(" and Sett_TransTypeDefinition.TransactionTypeID=LoanSett_GlEntry.TransactionTypeID(+)  \n");
			sbSQL.append(" and (DebitSett_GlEntry.mDebit <> 0 or LoanSett_GlEntry.mLoan <> 0 or DebitSett_GlEntry.nDebitCount<>0 or LoanSett_GlEntry.nLoanCount<>0) ");
			//查询记录
			String strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " ) ";
			switch ((int) lOrderParam)
			{
				case 1 :
				default :
					strSQLRecord = strSQLRecord + " order by TransactionTypeID ";
					break;
				case 2 :
					strSQLRecord = strSQLRecord + " order by mDebit ";
					break;
				case 3 :
					strSQLRecord = strSQLRecord + " order by mLoan ";
					break;
				case 4 :
					strSQLRecord = strSQLRecord + " order by nDebitCount ";
					break;
				case 5 :
					strSQLRecord = strSQLRecord + " order by nLoanCount ";
					break;
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQLRecord += " desc";
			}
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(strSQLRecord);
			int nPos = 1;
			ps.setTimestamp(nPos++, tsDateStart);
			ps.setTimestamp(nPos++, tsDateEnd);
			ps.setTimestamp(nPos++, tsDateStart);
			ps.setTimestamp(nPos++, tsDateEnd);
			ps.setTimestamp(nPos++, tsDateStart);
			ps.setTimestamp(nPos++, tsDateEnd);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AccountRecordInfo accountRecordInfo = new AccountRecordInfo();
				accountRecordInfo.debit = rs.getDouble("mDebit"); //借方合计
				accountRecordInfo.loan = rs.getDouble("mLoan"); //贷方合计
				accountRecordInfo.debitNumber = rs.getLong("nDebitCount");
				accountRecordInfo.creditNumber = rs.getLong("nLoanCount");
				accountRecordInfo.number = accountRecordInfo.debitNumber + accountRecordInfo.creditNumber; //凭证数量
				accountRecordInfo.transactionTypeID = rs.getLong("TransactionTypeID");
				accountRecordInfo.transactionType = SECConstant.BusinessType.getName(accountRecordInfo.transactionTypeID);
				accountRecordInfo.pageCount = 1;
				v.add(accountRecordInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return (v.size() > 0 ? v : null);
	}
	/**
	 * 科目设置新增操作
	 */
	public long addGLEntryDefinition(GLEntryDefinitionInfo info) throws SecuritiesException
	{
		SEC_GLEntryDefinitionDAO dao = new SEC_GLEntryDefinitionDAO();
		try
		{
			dao.setUseMaxID();
			return dao.add(info);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("", e);
		}
	}
	/**
	 * 科目设置查找（排序）操作
	 * @param officeID
	 * @param currencyID
	 * @param orderType
	 * @return
	 * @throws SecuritiesException
	 */
	public Collection findAllAndOrderBy(GLEntryDefinitionInfo info) throws SecuritiesException
	{
		SEC_GLEntryDefinitionDAO dao = new SEC_GLEntryDefinitionDAO();
		return dao.findAllAndOrderBy(info);
	}
	
	/**
	 * 科目设置查找（排序）操作
	 * @param officeID
	 * @param currencyID
	 * @param orderType
	 * @return
	 * @throws SecuritiesException
	 */
	public Collection findByConditions(GLEntryDefinitionInfo info) throws SecuritiesException
	{
		SEC_GLEntryDefinitionDAO dao = new SEC_GLEntryDefinitionDAO();
		return dao.findByConditions(info);
	}
	/**
	 *科目设置的单笔查询操作
	 */
	public GLEntryDefinitionInfo findGLEntryByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		GLEntryDefinitionInfo info = new GLEntryDefinitionInfo();
		try
		{
			SEC_GLEntryDefinitionDAO dao = new SEC_GLEntryDefinitionDAO();
			info = (GLEntryDefinitionInfo) dao.findByID(lID, info.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	/**
	 *科目设置删除操作
	*/
	public void deleteGLEntry(long id) throws SecuritiesException
	{
		try
		{
			GLEntryFacade.delete(id);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 *科目设置更新操作
	 */
	public void updateGLEntry(GLEntryDefinitionInfo info) throws SecuritiesException
	{
		try
		{
			GLEntryFacade.update(info);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 * 帐务类型设置新增操作
	 */
	public long addAccountType(AccountTypeInfo info) throws SecuritiesException
	{
		SEC_AccountTypeDAO dao = new SEC_AccountTypeDAO();
		try
		{
			dao.setUseMaxID();
			return dao.add(info);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("", e);
		}
	}
	/**
	 * 帐务类型设置查找（排序）操作
	 * @param officeID
	 * @param currencyID
	 * @param orderType
	 * @return
	 * @throws SecuritiesException
	 */
	public Collection findAllAccountType(AccountTypeInfo info) throws SecuritiesException
	{
		SEC_AccountTypeDAO dao = new SEC_AccountTypeDAO();
		return dao.findAllAccountType(info);
	}
	/**
	 *帐务类型设置的单笔查询操作
	 */
	public AccountTypeInfo findAccountTypeByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		AccountTypeInfo info = new AccountTypeInfo();
		try
		{
			SEC_AccountTypeDAO dao = new SEC_AccountTypeDAO();
			info = (AccountTypeInfo) dao.findByID(lID, info.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	/**
	 *帐务类型设置删除操作
	 */
	public void deleteAccountType(long id) throws SecuritiesException
	{
		try
		{
			AccountTypeFacade.delete(id);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 *帐务类型设置更新操作
	 */
	public void updateAccountType(AccountTypeInfo info) throws SecuritiesException
	{
		try
		{
			AccountTypeFacade.update(info);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
}
