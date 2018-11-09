/*
 * Created on 2003-9-27
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;
import java.util.List;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.query.dataentity.ContractInfoAdjustQuery;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.AccountRecordConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.AccountRecordInfo;
import com.iss.itreasury.settlement.query.resultinfo.AccountTransactionInfo;
import com.iss.itreasury.settlement.query.resultinfo.AccountTransactionTypeInfo;
import com.iss.itreasury.settlement.query.resultinfo.BankAccountRecordInfo;
import com.iss.itreasury.settlement.query.resultinfo.PrintGLInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryGL extends ITreasuryDAO
{
	private static Log4j log4j = null;
	
	public QueryGL()
	{
		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	public static void main(String[] args)
	{
		Collection cc=null;
		QueryGL g = new QueryGL();
		try
		{
			cc=g.findDailyAccountRecord(1,
			        1,
			        DataFormat.getDateTime("2006-9-27"),
			        DataFormat.getDateTime("2006-9-27"),
			        10,
			        1,
			        1,
			        1);
		}
		catch (RemoteException e)
		{
			// 
			e.printStackTrace();
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(cc);
		
	}
	
	/**
		 * 查询科目号下交易类型汇总
		 * @param strAccount 会计账号
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
	public Collection findGLDetails(String strRootAccount, String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Log.print("**********************in findGLDetails*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			conn = Database.getConnection();
			/*TOCONFIG-TODELETE*/
			/*
			//得到账户类型，账户编号
			
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append(" select nOfficeID,nCurrencyID,sSubjectCode,sTransNo,nTransactionTypeID,nTransDirection, \n");
				sbSQL.append("        mAmount,dtInterestStart,dtExecute,sAbstract,nInputUserID,nCheckUserID,sInputUserName,sCheckUserName \n");
				sbSQL.append(" from \n");
				sbSQL.append(" ( \n");				
			}
			if(!Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append("     select a.nOfficeID,a.nCurrencyID,a.sSubjectCode,a.sTransNo,a.nTransactionTypeID,a.nTransDirection, \n");
				sbSQL.append("            a.mAmount,a.dtInterestStart,a.dtExecute,a.sAbstract,a.nInputUserID,a.nStatusID,a.nCheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
				sbSQL.append("     from  Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+")  b,UserInfo c,UserInfo d \n");
				sbSQL.append("     where a.sSubjectCode=b.sSubjectCode(+) and a.nInputUserID=c.ID(+) and a.nCheckUserID=d.ID(+) and a.nOfficeID= " + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 ");
			}
			else
			{*/
				sbSQL.append("     select a.nOfficeID,a.nCurrencyID,a.sSubjectCode,a.sTransNo,a.nTransactionTypeID,a.nTransDirection, \n");
				sbSQL.append("            a.mAmount,a.dtInterestStart,a.dtExecute,a.nStatusID,a.sAbstract,a.nInputUserID,a.nCheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
				sbSQL.append("     from  Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+")  b,UserInfo c,UserInfo d \n");
				sbSQL.append("     where a.sSubjectCode=b.sSubjectCode(+) and a.nInputUserID=c.ID(+) and a.nCheckUserID=d.ID(+) and a.nOfficeID= " + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 ");
				
			/*}*/
			if (strRootAccount != null && strRootAccount.length() > 0)
			{
				sbSQL.append("       and b.sRootSubject = '" + strRootAccount + "'");
			}
			if (strAccount != null && strAccount.length() > 0)
			{
				sbSQL.append("       and b.sSubjectCode = '" + strAccount + "'");
			}
			if (strTransNo != null && strTransNo.length() > 0)
			{
				sbSQL.append("       and a.sTransNo = '" + strTransNo + "'");
			}
			if (lTypeID > 0)
			{
				sbSQL.append("       and a.nTransDirection = " + lTypeID);
			}
			if (lTransTypeID > 0)
			{
				sbSQL.append("       and a.nTransactionTypeID = " + lTransTypeID);
			}
			if (tsDateStart != null && tsDateEnd != null)
			{
				sbSQL.append("       and   a.dtExecute between ? and ? \n");
			}
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append("   union all \n");
				sbSQL.append("     select a.nOfficeID,a.nCurrencyID,a.sSubjectCode,a.sTransNo,a.nTransactionTypeID,a.nTransDirection, \n");
				sbSQL.append("            a.mAmount,a.dtInterestStart,a.dtExecute,a.sAbstract,a.nInputUserID,a.nCheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
				sbSQL.append("     from  Sett_VSECGlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b,UserInfo c,UserInfo d \n");
				sbSQL.append("     where a.sSubjectCode=b.sSubjectCode(+) and a.nInputUserID=c.ID(+) and a.nCheckUserID=d.ID(+) and a.nOfficeID= " + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 ");
				sbSQL.append("           and a.sTransNo is not null \n");
				if (strRootAccount != null && strRootAccount.length() > 0)
				{
					sbSQL.append("       and b.sRootSubject = '" + strRootAccount + "'");
				}
				if (strAccount != null && strAccount.length() > 0)
				{
					sbSQL.append("       and b.sSubjectCode = '" + strAccount + "'");
				}
				if (strTransNo != null && strTransNo.length() > 0)
				{
					sbSQL.append("       and a.sTransNo = '" + strTransNo + "'");
				}
				if (lTypeID > 0)
				{
					sbSQL.append("       and a.nTransDirection = " + lTypeID);
				}
				if (lTransTypeID > 0)
				{
					sbSQL.append("       and a.nTransactionTypeID = " + lTransTypeID);
				}
				if (tsDateStart != null && tsDateEnd != null)
				{
					//Modify by Forest,20040526,
					//原因是从开户行余额查询进入时，要求查到系统开机日之前的证券相关会计分录
					//临时处理：strAccount不为空，strRootAccount为空认为是从开户行余额查询进入
					if ((strAccount != null && strAccount.length() > 0) && !(strRootAccount != null && strRootAccount.length() > 0))
					{
						sbSQL.append("       and   a.dtExecute <=? and  a.dtExecute <=? \n");
					}
					else
					{
						sbSQL.append("       and   a.dtExecute between ? and ? \n");
					}
				}
				sbSQL.append(" ) \n");
			}
			*/
			String strSQLRecordCount = "";
			strSQLRecordCount = "select count(*) nCount,sum(mAmount) mAmount from (  " + sbSQL.toString() + ") aa";
			ps = conn.prepareStatement(strSQLRecordCount);
			Log.print(strSQLRecordCount);
			if (tsDateStart != null && tsDateEnd != null)
			{
				ps.setTimestamp(1, tsDateStart);
				ps.setTimestamp(2, tsDateEnd);
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN))
				{
					ps.setTimestamp(3, tsDateStart);
					ps.setTimestamp(4, tsDateEnd);
				}
				*/
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
					strSQLRecord = strSQLRecord + " order by dtInterestStart ";
					break;
				case 2 :
					strSQLRecord = strSQLRecord + " order by sTransNo ";
					break;
				case 3 :
					strSQLRecord = strSQLRecord + " order by nTransactionTypeID ";
					break;
				case 4 :
					strSQLRecord = strSQLRecord + " order by sSubjectCode ";
					break;
				case 5 :
					strSQLRecord = strSQLRecord + " order by mAmount ";
					break;
				case 6 :
					strSQLRecord = strSQLRecord + " order by nTransDirection ";
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
			if (tsDateStart != null && tsDateEnd != null)
			{
				ps.setTimestamp(1, tsDateStart);
				ps.setTimestamp(2, tsDateEnd);
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN))
				{
					ps.setTimestamp(3, tsDateStart);
					ps.setTimestamp(4, tsDateEnd);
				}
				*/
			}
			Log.print("before query");
			rs = ps.executeQuery();
			Log.print("after query");
			while (rs.next())
			{
				AccountTransactionInfo accountTransactionInfo = new AccountTransactionInfo();
				
				//added by mzh_fu 2007-3-12 解决把起息日错误显示成了执行日期的问题
				accountTransactionInfo.m_dtInterestStart = rs.getTimestamp("dtInterestStart"); //起息日
				
				accountTransactionInfo.m_tsExecute = rs.getTimestamp("dtExecute"); //执行日期
				//accountTransactionInfo.m_lTransactionNoID = rs.getLong("nTransactionNoID"); //交易标识
				accountTransactionInfo.m_lTransactionTypeID = rs.getLong("nTransactionTypeID");
				accountTransactionInfo.m_strTransactionType = SETTConstant.TransactionType.getName(accountTransactionInfo.m_lTransactionTypeID);
				accountTransactionInfo.m_strTransNo = rs.getString("sTransNo"); //交易号
				accountTransactionInfo.m_strAccountRecord = rs.getString("sSubjectCode"); //科目号
				accountTransactionInfo.m_dAmount = rs.getDouble("mAmount"); //金额
				accountTransactionInfo.m_lDirectionID = rs.getLong("nTransDirection"); //借贷标识
				accountTransactionInfo.m_strDirection = SETTConstant.DebitOrCredit.getName(accountTransactionInfo.m_lDirectionID);
				//借贷名称
				//accountTransactionInfo.m_lStatusID = rs.getLong("nStatusID"); //状态
				//accountTransactionInfo.m_strStatus = SETTConstant.TransactionStatus.getName(accountTransactionInfo.m_lStatusID); //状态名称
				accountTransactionInfo.m_lPageCount = lPageCount;
				accountTransactionInfo.m_lTotalRecordes = lRecordCount;
				accountTransactionInfo.m_dTotalAmount = dSumAmount;
				//				得到对应科目号
				long lOtherDirection = accountTransactionInfo.m_lDirectionID == SETTConstant.DebitOrCredit.DEBIT ? SETTConstant.DebitOrCredit.CREDIT : SETTConstant.DebitOrCredit.DEBIT;
				String strOtherSQL = "select distinct sSubjectCode from Sett_GlEntry where sTransNo='" + accountTransactionInfo.m_strTransNo + "' and nTransDirection=" + lOtherDirection + " and nvl(nStatusID,0)>0 ";
				PreparedStatement psOther = conn.prepareStatement(strOtherSQL);
				ResultSet rsOther = psOther.executeQuery();
				Vector vectorOtherAccount = new Vector(); //对方科目号
				while (rsOther.next())
				{
					accountTransactionInfo.m_strOtherAccountRecord = accountTransactionInfo.m_strOtherAccountRecord + rsOther.getString("sSubjectCode");
				}
				rsOther.close();
				rsOther = null;
				psOther.close();
				psOther = null;
				/*
				if(Env.getProjectName().equals(Constant.ProjectName.HN))
				{
					accountTransactionInfo = findTransactionDetail(accountTransactionInfo);
				}
				else
				{*/
					accountTransactionInfo.m_lStatusID = rs.getLong("nStatusID"); //状态
					accountTransactionInfo.m_strStatus = SETTConstant.TransactionStatus.getName(accountTransactionInfo.m_lStatusID); //状态名称
					
				/*}*/
				accountTransactionInfo.m_strAbstract = rs.getString("sAbstract");
				accountTransactionInfo.m_strInputUser = rs.getString("sInputUserName");
				accountTransactionInfo.m_strCheckUser = rs.getString("sCheckUserName");
				if (accountTransactionInfo.m_strInputUser == null || accountTransactionInfo.m_strInputUser.length() <= 0)
				{
					accountTransactionInfo.m_strInputUser = "机制";
				}
				if (accountTransactionInfo.m_strCheckUser == null || accountTransactionInfo.m_strCheckUser.length() <= 0)
				{
					accountTransactionInfo.m_strCheckUser = "机核";
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
			/*TOCONFIG-END*/
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
		Log.print("********************** finish findGLDetails*********************");
		return (v.size() > 0 ? v : null);
	}
	
	
//	Added by leiyang 2008/01/22 在用子父科目查询时也可用，查询字段使用科目ID
	/**
	 * 查询科目号下交易类型汇总
	 * @param strAccount 会计账号
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
	public Collection findGLDetails(long lAccount, String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Log.print("**********************in findGLDetails*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			conn = Database.getConnection();

			sbSQL.append("     select a.nOfficeID,a.nCurrencyID,a.sSubjectCode,a.sTransNo,a.nTransactionTypeID,a.nTransDirection, \n");
			sbSQL.append("            a.mAmount,a.dtInterestStart,a.dtExecute,a.nStatusID,a.sAbstract,a.nInputUserID,a.nCheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
			sbSQL.append("     from  Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID+" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid)  b,UserInfo c,UserInfo d \n");
			sbSQL.append("     where a.sSubjectCode(+)=b.sSubjectCode and a.nInputUserID=c.ID(+) and a.nCheckUserID=d.ID(+) and a.nOfficeID= " + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 ");
				
			if (strAccount != null && strAccount.length() > 0)
			{
				sbSQL.append("       and b.sSubjectCode = '" + strAccount + "'");
			}
			if (strTransNo != null && strTransNo.length() > 0)
			{
				sbSQL.append("       and a.sTransNo = '" + strTransNo + "'");
			}
			if (lTypeID > 0)
			{
				sbSQL.append("       and a.nTransDirection = " + lTypeID);
			}
			if (lTransTypeID > 0)
			{
				sbSQL.append("       and a.nTransactionTypeID = " + lTransTypeID);
			}
			if (tsDateStart != null && tsDateEnd != null)
			{
				sbSQL.append("       and   a.dtExecute between ? and ? \n");
			}

			String strSQLRecordCount = "";
			strSQLRecordCount = "select count(*) nCount,sum(mAmount) mAmount from (  " + sbSQL.toString() + ") aa";
			ps = conn.prepareStatement(strSQLRecordCount);
			Log.print(strSQLRecordCount);
			if (tsDateStart != null && tsDateEnd != null)
			{
				ps.setTimestamp(1, tsDateStart);
				ps.setTimestamp(2, tsDateEnd);
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
					strSQLRecord = strSQLRecord + " order by dtInterestStart ";
					break;
				case 2 :
					strSQLRecord = strSQLRecord + " order by sTransNo ";
					break;
				case 3 :
					strSQLRecord = strSQLRecord + " order by nTransactionTypeID ";
					break;
				case 4 :
					strSQLRecord = strSQLRecord + " order by sSubjectCode ";
					break;
				case 5 :
					strSQLRecord = strSQLRecord + " order by mAmount ";
					break;
				case 6 :
					strSQLRecord = strSQLRecord + " order by nTransDirection ";
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
			if (tsDateStart != null && tsDateEnd != null)
			{
				ps.setTimestamp(1, tsDateStart);
				ps.setTimestamp(2, tsDateEnd);
			}
			Log.print("before query");
			rs = ps.executeQuery();
			Log.print("after query");
			while (rs.next())
			{
				AccountTransactionInfo accountTransactionInfo = new AccountTransactionInfo();
				
				//added by mzh_fu 2007-3-12 解决把起息日错误显示成了执行日期的问题
				accountTransactionInfo.m_dtInterestStart = rs.getTimestamp("dtInterestStart"); //起息日
				
				accountTransactionInfo.m_tsExecute = rs.getTimestamp("dtExecute"); //执行日期
				//accountTransactionInfo.m_lTransactionNoID = rs.getLong("nTransactionNoID"); //交易标识
				accountTransactionInfo.m_lTransactionTypeID = rs.getLong("nTransactionTypeID");
				accountTransactionInfo.m_strTransactionType = SETTConstant.TransactionType.getName(accountTransactionInfo.m_lTransactionTypeID);
				accountTransactionInfo.m_strTransNo = rs.getString("sTransNo"); //交易号
				accountTransactionInfo.m_strAccountRecord = rs.getString("sSubjectCode"); //科目号
				accountTransactionInfo.m_dAmount = rs.getDouble("mAmount"); //金额
				accountTransactionInfo.m_lDirectionID = rs.getLong("nTransDirection"); //借贷标识
				accountTransactionInfo.m_strDirection = SETTConstant.DebitOrCredit.getName(accountTransactionInfo.m_lDirectionID);
				//借贷名称
				accountTransactionInfo.m_lPageCount = lPageCount;
				accountTransactionInfo.m_lTotalRecordes = lRecordCount;
				accountTransactionInfo.m_dTotalAmount = dSumAmount;
				//				得到对应科目号
				long lOtherDirection = accountTransactionInfo.m_lDirectionID == SETTConstant.DebitOrCredit.DEBIT ? SETTConstant.DebitOrCredit.CREDIT : SETTConstant.DebitOrCredit.DEBIT;
				String strOtherSQL = "select distinct sSubjectCode from Sett_GlEntry where sTransNo='" + accountTransactionInfo.m_strTransNo + "' and nTransDirection=" + lOtherDirection + " and nvl(nStatusID,0)>0 ";
				PreparedStatement psOther = conn.prepareStatement(strOtherSQL);
				ResultSet rsOther = psOther.executeQuery();
				Vector vectorOtherAccount = new Vector(); //对方科目号
				while (rsOther.next())
				{
					accountTransactionInfo.m_strOtherAccountRecord = accountTransactionInfo.m_strOtherAccountRecord + rsOther.getString("sSubjectCode");
				}
				rsOther.close();
				rsOther = null;
				psOther.close();
				psOther = null;
				
				accountTransactionInfo.m_lStatusID = rs.getLong("nStatusID"); //状态
				accountTransactionInfo.m_strStatus = SETTConstant.TransactionStatus.getName(accountTransactionInfo.m_lStatusID); //状态名称
				accountTransactionInfo.m_strAbstract = rs.getString("sAbstract");
				accountTransactionInfo.m_strInputUser = rs.getString("sInputUserName");
				accountTransactionInfo.m_strCheckUser = rs.getString("sCheckUserName");
				if (accountTransactionInfo.m_strInputUser == null || accountTransactionInfo.m_strInputUser.length() <= 0)
				{
					accountTransactionInfo.m_strInputUser = "机制";
				}
				if (accountTransactionInfo.m_strCheckUser == null || accountTransactionInfo.m_strCheckUser.length() <= 0)
				{
					accountTransactionInfo.m_strCheckUser = "机核";
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
			/*TOCONFIG-END*/
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
		Log.print("********************** finish findGLDetails*********************");
		return (v.size() > 0 ? v : null);
	}
	
	/**
	 * 日结会计账汇总(查询表内表外汇总)
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 * @param tsDateStart 起始日期
	 * @param tsDateEnd 结束日期
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 */
	public AccountRecordInfo findDailyAccountRecordhz(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Log.print("**********************in findDailyAccountRecordhz*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountRecordInfo accountRecordInfo =null;
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			double dDebitsSumAmount = 0.00;
			double dCreditSumAmount = 0.00;
			double dOffDebitSumAmount = 0.00;
			double dOffCreditSumAmount = 0.00;
			long lDirection = 0;
			 
			//因为华能要求包含证券部分会计账（其它不需要,如果没有证券模块，加入会报错），所以动态生成SQL
			//modify by Forest, 20040525
			//modify by Forest, 20040525, 需求变更：结算查到的证券的分录有两个条件：
			//							1、必须是在结算处理的。
			//							2、只有102（开户行）科目的才能查到。
				sbSQL.append("  select sum(decode(sett_glsubjectdefinition.nSubjectType,"+SETTConstant.SubjectAttribute.TABLEOUT+",0.00,MAMOUNT)) sumamount, \n");
				sbSQL.append("  sum(DECODE(sett_glsubjectdefinition.nSubjectType,"+SETTConstant.SubjectAttribute.TABLEOUT+",MAMOUNT)) sumOffbalanceAmount,NTransDIRECTION  \n");
				sbSQL.append("  from sett_GLENTRY,sett_glsubjectdefinition  \n");
				sbSQL.append("  where sett_GLENTRY.nOfficeID=" + lOfficeID + " and sett_GLENTRY.nCurrencyID=" + lCurrencyID + " \n");
				sbSQL.append("       and sett_GLENTRY.sSubjectCode = sett_glsubjectdefinition.sSegmentCode2 \n");
				sbSQL.append("       and sett_GLENTRY.nOfficeID = sett_glsubjectdefinition.nOfficeID \n");
				sbSQL.append("       and sett_GLENTRY.nCurrencyID = sett_glsubjectdefinition.nCurrencyID \n");
				sbSQL.append("       and dtExecute between ? and ? and nvl(sett_GLENTRY.nStatusID,0)>0 \n");
				sbSQL.append("  group by NTransDIRECTION \n");
				/*
				sbSQL.append("  select sum(MAMOUNT) sumamount,0.0 sumOffbalanceAmount,NTransDIRECTION  \n");
				sbSQL.append("  from sett_GLENTRY  \n");
				sbSQL.append("  where nOfficeID=" + lOfficeID + " and nCurrencyID=" + lCurrencyID + " \n");
				sbSQL.append("       and dtExecute between ? and ? and nvl(sett_GLENTRY.nStatusID,0)>0 \n");
				sbSQL.append("  group by NTransDIRECTION \n");
				*/
			sbSQL.append("order by  NTransDIRECTION \n");
			
			Log.print("查询会总金额：" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setTimestamp(1, tsDateStart);
			ps.setTimestamp(2, tsDateEnd);
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				ps.setTimestamp(3, tsDateStart);
				ps.setTimestamp(4, tsDateEnd);
			}
			*/
			rs = ps.executeQuery();
			while (rs.next())
			{
				lDirection = rs.getLong("NTransDIRECTION");
				if (lDirection == SETTConstant.DebitOrCredit.DEBIT)
				{
					dDebitsSumAmount = rs.getDouble("sumamount");
					dOffDebitSumAmount = rs.getDouble("sumOffbalanceAmount");
				}					
				else if (lDirection == SETTConstant.DebitOrCredit.CREDIT)
				{
					dCreditSumAmount = rs.getDouble("sumamount");
					dOffCreditSumAmount = rs.getDouble("sumOffbalanceAmount");
				}
			}
			accountRecordInfo = new AccountRecordInfo();
			accountRecordInfo.m_dDebitsSumAmount = dDebitsSumAmount; //满足条件全部借方合计,（上海电气只取表内的借方合计）
			accountRecordInfo.m_dCreditSumAmount = dCreditSumAmount; //满足条件全部贷方合计，（上海电气只取表内的贷方合计）
			accountRecordInfo.m_dOffDebitSumAmount = dOffDebitSumAmount;//表外业务借方合计
			accountRecordInfo.m_dOffCreditSumAmount = dOffCreditSumAmount;//表外业务借方合计
			rs.close();
			rs = null;
			ps.close();
			ps = null;
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
		return accountRecordInfo;
	}
	
	/**
	 * 日结会计账汇总(查询日结 分页)
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 * @param tsDateStart 起始日期
	 * @param tsDateEnd 结束日期
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 */
	//Modifyed by leiyang 2008/01/22 修改sql增加显示字段 id, nParentSubjectId， 修改了科目类型不同而产生的计算方工不同
	public PageLoader findDailyAccountRecordrj(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		PageLoader pageLoader =null;
		 StringBuffer sbSelect = new StringBuffer();
		 StringBuffer sbFrom = new StringBuffer();
		 StringBuffer sbWhere = new StringBuffer();
		 try{
			 sbSelect.append("  id, m_strAccount, m_strName, m_lSubjectType, \n");
			 sbSelect.append("        m_dDebitAmount, m_lDebitNumber, \n");	 
			 sbSelect.append("         m_dLoanAmount, m_lCreditNumber,m_dStartBalance, nParentSubjectId, \n");	 
			 //sbSelect.append(" decode(m_lSubjectType,'"+SETTConstant.DebitOrCredit.DEBIT+"' , m_dStartBalance+m_dDebitAmount-m_dLoanAmount,'"+SETTConstant.DebitOrCredit.CREDIT+"',m_dStartBalance+m_dLoanAmount-m_dDebitAmount) m_dEndBalance \n");
			 sbSelect.append(" decode(m_lSubjectType, '"+SETTConstant.SubjectAttribute.ASSET+"', m_dStartBalance+m_dDebitAmount-m_dLoanAmount, '"+SETTConstant.SubjectAttribute.PAYOUT+"', m_dStartBalance+m_dDebitAmount-m_dLoanAmount, '"+SETTConstant.SubjectAttribute.DEBT+"',m_dStartBalance+m_dLoanAmount-m_dDebitAmount, '"+SETTConstant.SubjectAttribute.RIGHT+"',m_dStartBalance+m_dLoanAmount-m_dDebitAmount, '"+SETTConstant.SubjectAttribute.INCOME+"',m_dStartBalance+m_dLoanAmount-m_dDebitAmount) m_dEndBalance  \n");
 
			 
			 sbFrom.append("  ( select      Sett_GlSubjectDefinition.sSubjectCode m_strAccount,Sett_GlSubjectDefinition.sSubjectName m_strName,Sett_GlSubjectDefinition.nSubjectType m_lSubjectType, Sett_GlSubjectDefinition.nParentSubjectId, Sett_GlSubjectDefinition.id, \n");
			 sbFrom.append("        nvl(DebitSett_GlEntry.mDebit,0.0) m_dDebitAmount,DebitSett_GlEntry.nDebitCount m_lDebitNumber, \n");
			 sbFrom.append("        nvl(LoanSett_GlEntry.mLoan,0.0) m_dLoanAmount,LoanSett_GlEntry.nLoanCount m_lCreditNumber, \n");
//			是否当天
			boolean bIsToday = false;
			if(DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)) && CloseSystemMain.getSystemStatusID(lOfficeID, lCurrencyID,Constant.ModuleType.SETTLEMENT)!=Constant.SystemStatus.CLOSE)
			{
				bIsToday = true;
				//判断是接还是贷
				sbFrom.append(" nvl(decode(Sett_GlSubjectDefinition.nSubjectType,'"+SETTConstant.DebitOrCredit.DEBIT+"' ,SubjectCurrentBalance.mBalance,'"+SETTConstant.DebitOrCredit.CREDIT+"',-SubjectCurrentBalance.mBalance),0.0) m_dStartBalance \n");
			}
			else
			{
				bIsToday = false;
				sbFrom.append(" nvl(decode(Sett_GlSubjectDefinition.nSubjectType,'"+SETTConstant.DebitOrCredit.DEBIT+"' ,SubjectPreBalance.mBalance,'"+SETTConstant.DebitOrCredit.CREDIT+"',-SubjectPreBalance.mBalance),0.0) m_dStartBalance \n");
			}
			sbFrom.append(" from ( \n");
			sbFrom.append("   select distinct id,sRootSubject sSubjectCode,sRootSubjectName sSubjectName,nSubjectType,nParentSubjectId \n");
//			修改 by zyyao(2007-06-08)(去掉多余的子查询)
			sbFrom.append("   from  Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+" \n");
			//修改 by kenny(2007-05-02)(放开对表外科目的限制)
			//sbFrom.append("   and nsubjecttype <> 6 ) \n");
			//sbFrom.append("   ) \n");
			//sbFrom.append("   where nOfficeID=" + lOfficeID + " and nCurrencyID=" + lCurrencyID + " \n");
			sbFrom.append(" )  Sett_GlSubjectDefinition, \n");
			sbFrom.append(" ( \n");
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbFrom.append("   select sSubjectCode,sum(mDebit) mDebit,sum(nDebitCount) nDebitCount \n");
				sbFrom.append("   from \n");
				sbFrom.append("   ( \n");
			}
			*/
//			修改 by zyyao(2007-06-08)(去掉多余的左连接)
			sbFrom.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount \n");
			sbFrom.append("       from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbFrom.append("       where a.sSubjectCode=b.sSubjectCode and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " \n");
			sbFrom.append("           and a.dtExecute between  to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbFrom.append("       group by b.sRootSubject \n");
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbFrom.append("     union \n");
				sbFrom.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount \n");
				sbFrom.append("       from Sett_VSECGlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
				sbFrom.append("       where a.sSubjectCode=b.sSubjectCode(+) and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " \n");
				sbFrom.append("           and a.dtExecute between  to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
				sbFrom.append("       	 and a.sTransNo is not null and a.sSubjectCode like '%.102%' \n");
				sbFrom.append("       group by b.sRootSubject \n");
				sbFrom.append("   ) group by sSubjectCode \n");
			}
			*/
			sbFrom.append(" ) DebitSett_GlEntry, \n");
			
			sbFrom.append(" ( \n");
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{			
				sbFrom.append("   select sSubjectCode,sum(mLoan) mLoan,sum(nLoanCount) nLoanCount \n");
				sbFrom.append("   from \n");
				sbFrom.append("   ( \n");
			}*/
			sbFrom.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount \n");
			sbFrom.append("       from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbFrom.append("       where a.sSubjectCode=b.sSubjectCode and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " \n");
			sbFrom.append("             and a.dtExecute between to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbFrom.append("       group by b.sRootSubject \n");
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbFrom.append("     union \n");
				sbFrom.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount \n");
				sbFrom.append("       from Sett_VSECGlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
				sbFrom.append("       where a.sSubjectCode=b.sSubjectCode(+) and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " \n");
				sbFrom.append("             and a.dtExecute between to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
				sbFrom.append("             and a.sTransNo is not null and a.sSubjectCode like '%.102%' \n");
				sbFrom.append("       group by b.sRootSubject \n");
				sbFrom.append("   ) group by sSubjectCode \n");
			}*/
			sbFrom.append(" ) LoanSett_GlEntry, \n");
			sbFrom.append(" ( \n");
			sbFrom.append("   select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject sSubjectCode \n");
			sbFrom.append("   from Sett_GlBalance a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbFrom.append("   where  a.sGLSubjectCode=b.sSubjectCode and  a.dtGlDate   = to_date('" + DataFormat.getDateString(DataFormat.getPreviousDate(tsDateStart)) + "','yyyy-mm-dd')  and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " group by b.sRootSubject \n");
			sbFrom.append(" ) SubjectPreBalance, \n");
			sbFrom.append(" ( \n");
			sbFrom.append("   select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject sSubjectCode \n");
			sbFrom.append("   from Sett_GlBalance a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbFrom.append("   where  a.sGLSubjectCode=b.sSubjectCode and  a.dtGlDate   = to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd')  and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " group by b.sRootSubject \n");
			sbFrom.append(" ) SubjectCurrentBalance \n");
			sbFrom.append(" where Sett_GlSubjectDefinition.sSubjectCode=DebitSett_GlEntry.sSubjectCode(+)  \n");
			sbFrom.append("       and Sett_GlSubjectDefinition.sSubjectCode=LoanSett_GlEntry.sSubjectCode(+)  and Sett_GlSubjectDefinition.sSubjectCode=SubjectPreBalance.sSubjectCode(+)  and Sett_GlSubjectDefinition.sSubjectCode=SubjectCurrentBalance.sSubjectCode(+)  \n");

			if(Config.getBoolean(ConfigConstant.SETTMENT_DAILYGLQUERY_ISLEACHING,true))
			{
				sbFrom.append(" and (DebitSett_GlEntry.mDebit <>0 or LoanSett_GlEntry.mLoan <>0 )  \n");
			}
			
			sbFrom.append(" order by Sett_GlSubjectDefinition.sSubjectCode \n");
			sbFrom.append(" ) \n");
			
			/*
			if (!Env.getProjectName().equals(Constant.ProjectName.SEFC))
			{
				sbSQL.append("       and (DebitSett_GlEntry.mDebit <> 0 or LoanSett_GlEntry.mLoan <> 0 or DebitSett_GlEntry.nDebitCount <> 0 or LoanSett_GlEntry.nLoanCount <> 0) ");
			}*/
			
			sbWhere.append(" 1=1");
		pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.query.resultinfo.AccountRecordInfo",
				null);
		String strSQLRecord="";
		switch ((int) lOrderParam)
		{
			case 1 :
			default :
				strSQLRecord =  " order by m_strAccount ";
				break;
				//                case 2:
				//                    break;
			case 3 :
				strSQLRecord =  " order by m_dDebitAmount ";
				break;
			case 4 :
				strSQLRecord =  " order by m_dLoanAmount ";
				break;
			case 5 :
				strSQLRecord =  " order by m_lDebitNumber";
				break;
			case 6 :
				strSQLRecord =  " order by m_lCreditNumber ";
				break;
			case 7 :
			    if (DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)))
				{
					strSQLRecord =  " order by m_dStartBalance ";
				}
				else
				{
				    strSQLRecord =  " order by m_dStartBalance ";
				}					
				break;
			case 8 :
			    if (DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)))
				{
					strSQLRecord =  " order by m_dStartBalance ";
				}
				else
				{
				    strSQLRecord =  " order by m_dStartBalance ";
				}
				break;
			case 10:
				strSQLRecord =  " order by m_lSubjectType ";
				break;
		}
		if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			strSQLRecord += " desc";
		}
		pageLoader.setOrderBy(strSQLRecord);
		 }
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException(e.getMessage());
			}
		return pageLoader;
	}
	
	//Added by leiyang 2008/01/22 在用子父科目查询科目金额详细信息时也可用，查询字段使用科目ID
	public AccountRecordInfo getDailyAccountRecord(long lAccount, long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd) throws RemoteException
	{
		Log.print("**********************in getDailyAccountRecord*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountRecordInfo accountRecordInfo = null;
		
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			 
			sbSQL.append("  select \n");
			sbSQL.append("  sum(m_dDebitAmount) m_dDebitAmount, sum(m_lDebitNumber) m_lDebitNumber, \n");
			sbSQL.append("  sum(m_dLoanAmount) m_dLoanAmount, sum(m_lCreditNumber) m_lCreditNumber,sum(m_dStartBalance) m_dStartBalance, \n");	 
			sbSQL.append("  sum(decode(m_lSubjectType, '"+SETTConstant.SubjectAttribute.ASSET+"', m_dStartBalance+m_dDebitAmount-m_dLoanAmount, '"+SETTConstant.SubjectAttribute.PAYOUT+"', m_dStartBalance+m_dDebitAmount-m_dLoanAmount, '"+SETTConstant.SubjectAttribute.DEBT+"',m_dStartBalance+m_dLoanAmount-m_dDebitAmount, '"+SETTConstant.SubjectAttribute.RIGHT+"',m_dStartBalance+m_dLoanAmount-m_dDebitAmount, '"+SETTConstant.SubjectAttribute.INCOME+"',m_dStartBalance+m_dLoanAmount-m_dDebitAmount)) m_dEndBalance  \n");
			sbSQL.append("  from \n");
			sbSQL.append("  ( select Sett_GlSubjectDefinition.sSubjectCode m_strAccount,Sett_GlSubjectDefinition.sSubjectName m_strName,Sett_GlSubjectDefinition.nSubjectType m_lSubjectType, Sett_GlSubjectDefinition.nParentSubjectId, Sett_GlSubjectDefinition.id, \n");
			sbSQL.append("    nvl(DebitSett_GlEntry.mDebit,0.0) m_dDebitAmount,DebitSett_GlEntry.nDebitCount m_lDebitNumber, \n");
			sbSQL.append("    nvl(LoanSett_GlEntry.mLoan,0.0) m_dLoanAmount,LoanSett_GlEntry.nLoanCount m_lCreditNumber, \n");
			//是否当天
			if(DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)) && CloseSystemMain.getSystemStatusID(lOfficeID, lCurrencyID,Constant.ModuleType.SETTLEMENT)!=Constant.SystemStatus.CLOSE)
			{
				sbSQL.append(" nvl(decode(Sett_GlSubjectDefinition.nSubjectType,'"+SETTConstant.DebitOrCredit.DEBIT+"' ,SubjectCurrentBalance.mBalance,'"+SETTConstant.DebitOrCredit.CREDIT+"',-SubjectCurrentBalance.mBalance),0.0) m_dStartBalance \n");
			}
			else
			{
				sbSQL.append(" nvl(decode(Sett_GlSubjectDefinition.nSubjectType,'"+SETTConstant.DebitOrCredit.DEBIT+"' ,SubjectPreBalance.mBalance,'"+SETTConstant.DebitOrCredit.CREDIT+"',-SubjectPreBalance.mBalance),0.0) m_dStartBalance \n");
			}
			sbSQL.append(" from ( \n");
			sbSQL.append(" select distinct id,sRootSubject sSubjectCode,sRootSubjectName sSubjectName,nSubjectType,nParentSubjectId \n");
			sbSQL.append(" from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+" \n");
			sbSQL.append(" )  Sett_GlSubjectDefinition, \n");
			sbSQL.append(" ( \n");
			sbSQL.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount \n");
			sbSQL.append("       from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("       where a.sSubjectCode=b.sSubjectCode and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " \n");
			sbSQL.append("       and a.dtExecute between  to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append("       group by b.sRootSubject \n");
			sbSQL.append(" ) DebitSett_GlEntry, \n");
			sbSQL.append(" ( \n");
			sbSQL.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount \n");
			sbSQL.append("       from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("       where a.sSubjectCode=b.sSubjectCode and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " \n");
			sbSQL.append("             and a.dtExecute between to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append("       group by b.sRootSubject \n");
			sbSQL.append(" ) LoanSett_GlEntry, \n");
			sbSQL.append(" ( \n");
			sbSQL.append("   select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject sSubjectCode \n");
			sbSQL.append("   from Sett_GlBalance a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("   where  a.sGLSubjectCode=b.sSubjectCode and  a.dtGlDate   = to_date('" + DataFormat.getDateString(DataFormat.getPreviousDate(tsDateStart)) + "','yyyy-mm-dd')  and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " group by b.sRootSubject \n");
			sbSQL.append(" ) SubjectPreBalance, \n");
			sbSQL.append(" ( \n");
			sbSQL.append("   select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject sSubjectCode \n");
			sbSQL.append("   from Sett_GlBalance a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("   where  a.sGLSubjectCode=b.sSubjectCode and  a.dtGlDate   = to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd')  and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " group by b.sRootSubject \n");
			sbSQL.append(" ) SubjectCurrentBalance \n");
			sbSQL.append(" where Sett_GlSubjectDefinition.sSubjectCode=DebitSett_GlEntry.sSubjectCode(+)  \n");
			sbSQL.append("   and Sett_GlSubjectDefinition.sSubjectCode=LoanSett_GlEntry.sSubjectCode(+)  and Sett_GlSubjectDefinition.sSubjectCode=SubjectPreBalance.sSubjectCode(+)  and Sett_GlSubjectDefinition.sSubjectCode=SubjectCurrentBalance.sSubjectCode(+)  \n");
			if(Config.getBoolean(ConfigConstant.SETTMENT_DAILYGLQUERY_ISLEACHING,true))
			{
				sbSQL.append(" and (DebitSett_GlEntry.mDebit <>0 or LoanSett_GlEntry.mLoan <>0 )  \n");
			}
			sbSQL.append("   start with Sett_GlSubjectDefinition.Id = "+ lAccount +"  \n");
			sbSQL.append("   connect by prior Sett_GlSubjectDefinition.Id = Sett_GlSubjectDefinition.Nparentsubjectid  \n");
			sbSQL.append(" order by Sett_GlSubjectDefinition.sSubjectCode \n");
			sbSQL.append(" ) \n");

			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				accountRecordInfo = new AccountRecordInfo();
				accountRecordInfo.setM_dDebitAmount(rs.getDouble("m_dDebitAmount")); //借方金额
				accountRecordInfo.setM_lDebitNumber(rs.getLong("m_lDebitNumber")); //借方凭证
				accountRecordInfo.setM_dLoanAmount(rs.getDouble("m_dLoanAmount")); //贷方金额
				accountRecordInfo.setM_lCreditNumber(rs.getLong("m_lCreditNumber")); //贷方凭证
				accountRecordInfo.setM_dStartBalance(rs.getDouble("m_dStartBalance")); //期初余额
				accountRecordInfo.setM_dEndBalance(rs.getDouble("m_dEndBalance")); //期末余额
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
		return accountRecordInfo;
	}
	
	
	/**
		 * 日结会计账汇总
		 * @param lOfficeID  办事处标识
		 * @param lCurrencyID 币种
		 * @param tsDateStart 起始日期
		 * @param tsDateEnd 结束日期
		 * @param lPageLineCount
		 * @param lPageNo
		 * @param lOrderParam
		 * @param lDesc
		 */
	public Collection findDailyAccountRecord(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Log.print("**********************in findDailyAccountRecord*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			double dDebitsSumAmount = 0.00;
			double dCreditSumAmount = 0.00;
			double dOffDebitSumAmount = 0.00;
			double dOffCreditSumAmount = 0.00;
			long lDirection = 0;
			 
			//因为华能要求包含证券部分会计账（其它不需要,如果没有证券模块，加入会报错），所以动态生成SQL
			//modify by Forest, 20040525
			//modify by Forest, 20040525, 需求变更：结算查到的证券的分录有两个条件：
			//							1、必须是在结算处理的。
			//							2、只有102（开户行）科目的才能查到。
				sbSQL.append("  select sum(decode(sett_glsubjectdefinition.nSubjectType,"+SETTConstant.SubjectAttribute.TABLEOUT+",0.00,MAMOUNT)) sumamount, \n");
				sbSQL.append("  sum(DECODE(sett_glsubjectdefinition.nSubjectType,"+SETTConstant.SubjectAttribute.TABLEOUT+",MAMOUNT)) sumOffbalanceAmount,NTransDIRECTION  \n");
				sbSQL.append("  from sett_GLENTRY,sett_glsubjectdefinition  \n");
				sbSQL.append("  where sett_GLENTRY.nOfficeID=" + lOfficeID + " and sett_GLENTRY.nCurrencyID=" + lCurrencyID + " \n");
				sbSQL.append("       and sett_GLENTRY.sSubjectCode = sett_glsubjectdefinition.sSegmentCode2 \n");
				sbSQL.append("       and sett_GLENTRY.nOfficeID = sett_glsubjectdefinition.nOfficeID \n");
				sbSQL.append("       and sett_GLENTRY.nCurrencyID = sett_glsubjectdefinition.nCurrencyID \n");
				sbSQL.append("       and dtExecute between ? and ? and nvl(sett_GLENTRY.nStatusID,0)>0 \n");
				sbSQL.append("  group by NTransDIRECTION \n");
				/*
				sbSQL.append("  select sum(MAMOUNT) sumamount,0.0 sumOffbalanceAmount,NTransDIRECTION  \n");
				sbSQL.append("  from sett_GLENTRY  \n");
				sbSQL.append("  where nOfficeID=" + lOfficeID + " and nCurrencyID=" + lCurrencyID + " \n");
				sbSQL.append("       and dtExecute between ? and ? and nvl(sett_GLENTRY.nStatusID,0)>0 \n");
				sbSQL.append("  group by NTransDIRECTION \n");
				*/
			sbSQL.append("order by  NTransDIRECTION \n");
			
			Log.print("查询会总金额：" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setTimestamp(1, tsDateStart);
			ps.setTimestamp(2, tsDateEnd);
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				ps.setTimestamp(3, tsDateStart);
				ps.setTimestamp(4, tsDateEnd);
			}
			*/
			rs = ps.executeQuery();
			while (rs.next())
			{
				lDirection = rs.getLong("NTransDIRECTION");
				if (lDirection == SETTConstant.DebitOrCredit.DEBIT)
				{
					dDebitsSumAmount = rs.getDouble("sumamount");
					dOffDebitSumAmount = rs.getDouble("sumOffbalanceAmount");
				}					
				else if (lDirection == SETTConstant.DebitOrCredit.CREDIT)
				{
					dCreditSumAmount = rs.getDouble("sumamount");
					dOffCreditSumAmount = rs.getDouble("sumOffbalanceAmount");
				}
			}
			
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			/*sbSQL.append(" select Sett_GlSubjectDefinition.sSubjectCode,Sett_GlSubjectDefinition.sSubjectName,Sett_GlSubjectDefinition.nSubjectType, \n");
			sbSQL.append("        DebitSett_GlEntry.mDebit,DebitSett_GlEntry.nDebitCount, \n");
			sbSQL.append("        LoanSett_GlEntry.mLoan,LoanSett_GlEntry.nLoanCount, \n");
			sbSQL.append("        SubjectPreBalance.mBalance mPreBalance,SubjectCurrentBalance.mBalance mCurrentBalance \n");
			sbSQL.append(" from \n");
			sbSQL.append(" ( \n");
			sbSQL.append("   select distinct sRootSubject sSubjectCode,sRootSubjectName sSubjectName,nSubjectType \n");
			sbSQL.append("   from  Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+" \n");
			sbSQL.append(" )  Sett_GlSubjectDefinition, \n");
			sbSQL.append(" ( \n");*/
			
			sbSQL = new StringBuffer();
			//Modify by leiyang 2008/01/24 把期初余额和期末余额放到SQL语句中计算
			sbSQL.append(" select id,sSubjectCode,sSubjectName,nSubjectType,mDebit,nDebitCount,mLoan,nLoanCount,m_dStartBalance, \n");
			sbSQL.append(" decode(nSubjectType, '"+SETTConstant.SubjectAttribute.ASSET+"', m_dStartBalance+mDebit-mLoan, '"+SETTConstant.SubjectAttribute.PAYOUT+"', m_dStartBalance+mDebit-mLoan, '"+SETTConstant.SubjectAttribute.DEBT+"',m_dStartBalance+mLoan-mDebit, '"+SETTConstant.SubjectAttribute.RIGHT+"',m_dStartBalance+mLoan-mDebit, '"+SETTConstant.SubjectAttribute.INCOME+"',m_dStartBalance+mLoan-mDebit) m_dEndBalance  \n");
			sbSQL.append(" from   ( \n");
			sbSQL.append(" select Sett_GlSubjectDefinition.id,Sett_GlSubjectDefinition.sSubjectCode,Sett_GlSubjectDefinition.sSubjectName,Sett_GlSubjectDefinition.nSubjectType, \n");
			sbSQL.append("        nvl(DebitSett_GlEntry.mDebit,0.0) mDebit,nvl(DebitSett_GlEntry.nDebitCount,0) nDebitCount,  \n");
			sbSQL.append("        nvl(LoanSett_GlEntry.mLoan,0.0) mLoan,nvl(LoanSett_GlEntry.nLoanCount,0) nLoanCount,  \n");
			boolean bIsToday = false;
			if(DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)) && CloseSystemMain.getSystemStatusID(lOfficeID, lCurrencyID,Constant.ModuleType.SETTLEMENT)!=Constant.SystemStatus.CLOSE){
				bIsToday = true;
				//判断是接还是贷
				sbSQL.append(" nvl(decode(Sett_GlSubjectDefinition.nSubjectType,'"+SETTConstant.DebitOrCredit.DEBIT+"' ,SubjectCurrentBalance.mBalance,'"+SETTConstant.DebitOrCredit.CREDIT+"',-SubjectCurrentBalance.mBalance),0.0) m_dStartBalance \n");
			}
			else{
				bIsToday = false;
				sbSQL.append(" nvl(decode(Sett_GlSubjectDefinition.nSubjectType,'"+SETTConstant.DebitOrCredit.DEBIT+"' ,SubjectPreBalance.mBalance,'"+SETTConstant.DebitOrCredit.CREDIT+"',-SubjectPreBalance.mBalance),0.0) m_dStartBalance \n");
			}
			
			//sbSQL.append("        SubjectPreBalance.mBalance mPreBalance,SubjectCurrentBalance.mBalance mCurrentBalance \n");
			sbSQL.append(" from \n");
			sbSQL.append(" ( \n");
			sbSQL.append("   select distinct id, sRootSubject sSubjectCode,sRootSubjectName sSubjectName,nSubjectType \n");
			sbSQL.append("   from  Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+" \n");
			sbSQL.append(" )  Sett_GlSubjectDefinition, \n");
			sbSQL.append(" ( \n");

//			修改 by zyyao(2007-06-08)(去掉多余的左连接)
			sbSQL.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount \n");
			sbSQL.append("       from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("       where a.sSubjectCode=b.sSubjectCode and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " \n");
			sbSQL.append("           and a.dtExecute between  to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append("       group by b.sRootSubject \n");
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append("     union \n");
				sbSQL.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount \n");
				sbSQL.append("       from Sett_VSECGlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
				sbSQL.append("       where a.sSubjectCode=b.sSubjectCode(+) and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " \n");
				sbSQL.append("           and a.dtExecute between  to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
				sbSQL.append("       	 and a.sTransNo is not null and a.sSubjectCode like '%.102%' \n");
				sbSQL.append("       group by b.sRootSubject \n");
				sbSQL.append("   ) group by sSubjectCode \n");
			}
			*/
			sbSQL.append(" ) DebitSett_GlEntry, \n");
			
			sbSQL.append(" ( \n");
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{			
				sbSQL.append("   select sSubjectCode,sum(mLoan) mLoan,sum(nLoanCount) nLoanCount \n");
				sbSQL.append("   from \n");
				sbSQL.append("   ( \n");
			}*/
			sbSQL.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount \n");
			sbSQL.append("       from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("       where a.sSubjectCode=b.sSubjectCode and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " \n");
			sbSQL.append("             and a.dtExecute between to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
			sbSQL.append("       group by b.sRootSubject \n");
			/*
			if (Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append("     union \n");
				sbSQL.append("       select b.sRootSubject sSubjectCode,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount \n");
				sbSQL.append("       from Sett_VSECGlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
				sbSQL.append("       where a.sSubjectCode=b.sSubjectCode(+) and  a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " \n");
				sbSQL.append("             and a.dtExecute between to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd') and  to_date('" + DataFormat.getDateString(tsDateEnd) + "','yyyy-mm-dd') \n");
				sbSQL.append("             and a.sTransNo is not null and a.sSubjectCode like '%.102%' \n");
				sbSQL.append("       group by b.sRootSubject \n");
				sbSQL.append("   ) group by sSubjectCode \n");
			}*/
			sbSQL.append(" ) LoanSett_GlEntry, \n");
			sbSQL.append(" ( \n");
			sbSQL.append("   select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject sSubjectCode \n");
			sbSQL.append("   from Sett_GlBalance a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("   where  a.sGLSubjectCode=b.sSubjectCode and  a.dtGlDate   = to_date('" + DataFormat.getDateString(DataFormat.getPreviousDate(tsDateStart)) + "','yyyy-mm-dd')  and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " group by b.sRootSubject \n");
			sbSQL.append(" ) SubjectPreBalance, \n");
			sbSQL.append(" ( \n");
			sbSQL.append("   select sum(nvl(a.mDebitBalance,0)+nvl(a.mCreditBalance,0)) mBalance,b.sRootSubject sSubjectCode \n");
			sbSQL.append("   from Sett_GlBalance a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("   where  a.sGLSubjectCode=b.sSubjectCode and  a.dtGlDate   = to_date('" + DataFormat.getDateString(tsDateStart) + "','yyyy-mm-dd')  and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " group by b.sRootSubject \n");
			sbSQL.append(" ) SubjectCurrentBalance \n");
			sbSQL.append(" where Sett_GlSubjectDefinition.sSubjectCode=DebitSett_GlEntry.sSubjectCode(+)  \n");
			sbSQL.append("       and Sett_GlSubjectDefinition.sSubjectCode=LoanSett_GlEntry.sSubjectCode(+)  and Sett_GlSubjectDefinition.sSubjectCode=SubjectPreBalance.sSubjectCode(+)  and Sett_GlSubjectDefinition.sSubjectCode=SubjectCurrentBalance.sSubjectCode(+) \n");
			sbSQL.append("  )");
			/*
			if (!Env.getProjectName().equals(Constant.ProjectName.SEFC))
			{
				sbSQL.append("       and (DebitSett_GlEntry.mDebit <> 0 or LoanSett_GlEntry.mLoan <> 0 or DebitSett_GlEntry.nDebitCount <> 0 or LoanSett_GlEntry.nLoanCount <> 0) ");
			}*/
			//查询记录
			String strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " )  ";
			switch ((int) lOrderParam)
			{
				case 1 :
				default :
					strSQLRecord = strSQLRecord + " order by sSubjectCode ";
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
				    if (DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)))
					{
						strSQLRecord = strSQLRecord + " order by mCurrentBalance ";
					}
					else
					{
					    strSQLRecord = strSQLRecord + " order by mPreBalance ";
					}					
					break;
				case 8 :
				    if (DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)))
					{
						strSQLRecord = strSQLRecord + " order by mCurrentBalance ";
					}
					else
					{
					    strSQLRecord = strSQLRecord + " order by mPreBalance ";
					}
					break;
				case 10:
					strSQLRecord = strSQLRecord + " order by nSubjectType ";
					break;
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQLRecord += " desc";
			}
			Log.print(strSQLRecord);
			ps = conn.prepareStatement(strSQLRecord);
			rs = ps.executeQuery();
			
			//解决效率问题，将是否当天的判断提到循环外面 add by rxie
			/*boolean bIsToday = false;
			if(DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)) && CloseSystemMain.getSystemStatusID(lOfficeID, lCurrencyID,Constant.ModuleType.SETTLEMENT)!=Constant.SystemStatus.CLOSE)
			{
				bIsToday = true;
			}
			else
			{
				bIsToday = false;
			}*/
			
			while (rs.next())
			{
				AccountRecordInfo accountRecordInfo = new AccountRecordInfo();
				accountRecordInfo.setId(rs.getLong("id"));  //科目ID
				accountRecordInfo.m_strAccount = rs.getString("sSubjectCode"); //科目号
				accountRecordInfo.m_strName = rs.getString("sSubjectName"); //科目名称
				accountRecordInfo.m_dDebitAmount = rs.getDouble("mDebit"); //借方合计
				accountRecordInfo.m_dLoanAmount = rs.getDouble("mLoan"); //贷方合计
				accountRecordInfo.m_dDebitsSumAmount = dDebitsSumAmount; //满足条件全部借方合计,（上海电气只取表内的借方合计）
				accountRecordInfo.m_dCreditSumAmount = dCreditSumAmount; //满足条件全部贷方合计，（上海电气只取表内的贷方合计）
				accountRecordInfo.m_dOffDebitSumAmount = dOffDebitSumAmount;//表外业务借方合计
				accountRecordInfo.m_dOffCreditSumAmount = dOffCreditSumAmount;//表外业务借方合计
				accountRecordInfo.m_lSubjectType = rs.getLong("nSubjectType");
				accountRecordInfo.m_lDebitNumber = rs.getLong("nDebitCount");
				accountRecordInfo.m_lCreditNumber = rs.getLong("nLoanCount");
				accountRecordInfo.m_lNumber = accountRecordInfo.m_lDebitNumber + accountRecordInfo.m_lCreditNumber; //凭证数量
				
				QueryGL glquery = new QueryGL();
				AccountRecordInfo tempInfo = glquery.getDailyAccountRecord(accountRecordInfo.getId(), lOfficeID, lCurrencyID, tsDateStart, tsDateEnd);
				accountRecordInfo.setM_dStartBalance(tempInfo.getM_dStartBalance());
				accountRecordInfo.setM_dEndBalance(tempInfo.getM_dEndBalance());
				accountRecordInfo.setM_dDebitAmount(tempInfo.getM_dDebitAmount());
				accountRecordInfo.setM_dLoanAmount(tempInfo.getM_dLoanAmount());
				accountRecordInfo.setM_lDebitNumber(tempInfo.getM_lDebitNumber());
				accountRecordInfo.setM_lCreditNumber(tempInfo.getM_lCreditNumber());
				
				//accountRecordInfo.m_dStartBalance = rs.getDouble("");
				//accountRecordInfo.m_d
				/*if (bIsToday)//modify by rxie
				{
					accountRecordInfo.m_dStartBalance = rs.getDouble("mCurrentBalance");
				}
				else
				{
					accountRecordInfo.m_dStartBalance = rs.getDouble("mPreBalance");
				}
				long lSubjectTypeID = rs.getLong("nSubjectType");
				if (SETTConstant.SubjectAttribute.getDirection(lSubjectTypeID) == SETTConstant.DebitOrCredit.DEBIT)
				{
					accountRecordInfo.m_dEndBalance = accountRecordInfo.m_dStartBalance + accountRecordInfo.m_dDebitAmount - accountRecordInfo.m_dLoanAmount;
				}
				else
				{
					accountRecordInfo.m_dStartBalance = -accountRecordInfo.m_dStartBalance;
					accountRecordInfo.m_dEndBalance = accountRecordInfo.m_dStartBalance + accountRecordInfo.m_dLoanAmount - accountRecordInfo.m_dDebitAmount;
				}*/
				//Log.print("******accountRecordInfo.m_strAccount="+accountRecordInfo.m_strAccount);
				//Log.print("******accountRecordInfo.m_strName="+accountRecordInfo.m_strName);
				accountRecordInfo.m_lPageCount = 1;
				v.add(accountRecordInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			/*TOCONFIG-END*/
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
		 * Added by leiyang 2008/01/28
		 * 日结会计账汇总(New)
		 * @param lOfficeID  办事处标识
		 * @param lCurrencyID 币种
		 * @param tsDateStart 起始日期
		 * @param tsDateEnd 结束日期
		 * @param lPageLineCount
		 * @param lPageNo
		 * @param lOrderParam
		 * @param lDesc
		 */
	public Collection findDailyAccountRecordNew(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc,String strSubjectStart,
			String strSubjectEnd,
	        long iflk) throws RemoteException
	{
		Log.print("**********************in findDailyAccountRecordNew*********************");
		StringBuffer sbSQL = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			double dDebitsSumAmount = 0.00;
			double dCreditSumAmount = 0.00;
			double dOffDebitSumAmount = 0.00;
			double dOffCreditSumAmount = 0.00;
			 
			sbSQL = new StringBuffer();
			sbSQL.append("  select sum(decode(sett_glsubjectdefinition.nSubjectType,"+SETTConstant.SubjectAttribute.TABLEOUT+",0.00,MAMOUNT)) sumamount, \n");
			sbSQL.append("  sum(DECODE(sett_glsubjectdefinition.nSubjectType,"+SETTConstant.SubjectAttribute.TABLEOUT+",MAMOUNT)) sumOffbalanceAmount,NTransDIRECTION  \n");
			sbSQL.append("  from sett_GLENTRY,sett_glsubjectdefinition  \n");
			sbSQL.append("  where sett_GLENTRY.nOfficeID=" + lOfficeID + " and sett_GLENTRY.nCurrencyID=" + lCurrencyID + " \n");
			sbSQL.append("       and sett_GLENTRY.sSubjectCode = sett_glsubjectdefinition.sSegmentCode2 \n");
			if (!strSubjectStart.equals(""))
			{
				sbSQL.append( " and sett_GLENTRY.sSubjectCode>='" + strSubjectStart + "' \n");
			} 
			if (!strSubjectEnd.equals(""))
			{
				sbSQL.append( " and sett_GLENTRY.sSubjectCode<='" + strSubjectEnd+ "' \n");
			} 
			sbSQL.append("       and sett_GLENTRY.nOfficeID = sett_glsubjectdefinition.nOfficeID \n");
			sbSQL.append("       and sett_GLENTRY.nCurrencyID = sett_glsubjectdefinition.nCurrencyID \n");
			sbSQL.append("       and dtExecute between ? and ? and nvl(sett_GLENTRY.nStatusID,0)>0 \n");
			sbSQL.append("  group by NTransDIRECTION \n");
			sbSQL.append("order by  NTransDIRECTION \n");
			
			Log.print("SQL Info：" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setTimestamp(1, tsDateStart);
			ps.setTimestamp(2, tsDateEnd);
			rs = ps.executeQuery();
			while (rs.next())
			{
				long lDirection = rs.getLong("NTransDIRECTION");
				if (lDirection == SETTConstant.DebitOrCredit.DEBIT)
				{
					dDebitsSumAmount = rs.getDouble("sumamount");
					dOffDebitSumAmount = rs.getDouble("sumOffbalanceAmount");
				}					
				else if (lDirection == SETTConstant.DebitOrCredit.CREDIT)
				{
					dCreditSumAmount = rs.getDouble("sumamount");
					dOffCreditSumAmount = rs.getDouble("sumOffbalanceAmount");
				}
			}
			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
	
			
			sbSQL = new StringBuffer();
			sbSQL.append("  select Sett_GLSD.id,Sett_GLSD.m_strAccount,Sett_GLSD.m_strName,Sett_GLSD.m_lSubjectType,Sett_GLSD.nbalancedirection, \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1)) m_dDebitAmount,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,1)+1, INSTR(Sett_GLSD.All_Amount,'|',1,2)-INSTR(Sett_GLSD.All_Amount,'|',1,1)-1)) m_lDebitNumber,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1)) m_dLoanAmount,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,3)+1, INSTR(Sett_GLSD.All_Amount,'|',1,4)-INSTR(Sett_GLSD.All_Amount,'|',1,3)-1)) m_lCreditNumber,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount))) m_dStartBalance,  \n");
			sbSQL.append("         decode(Sett_GLSD.nbalancedirection,"+SETTConstant.DebitOrCredit.DEBIT+",TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount)))+TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1))-TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1)),"+SETTConstant.DebitOrCredit.CREDIT+",TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount)))+TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1))-TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1))) m_dEndBalance  \n");
			sbSQL.append("  from (  \n");
			sbSQL.append("  select a.id id ,a.ssegmentcode2 m_strAccount, a.ssegmentname2 m_strName, a.nsubjecttype m_lSubjectType, a.nbalancedirection nbalancedirection, (  \n");
			sbSQL.append("  select sum(DebitSett_GlEntry.m_dDebitAmount) || '|' ||   \n");
			sbSQL.append("         sum(DebitSett_GlEntry.m_lDebitNumber) || '|' ||   \n");
			sbSQL.append("         sum(LoanSett_GlEntry.m_dLoanAmount) || '|' ||   \n");
			sbSQL.append("         sum(LoanSett_GlEntry.m_lCreditNumber) || '|' ||   \n");
			sbSQL.append("         sum(SubjectBalance.m_dStartBalance) All_Amount  \n");
			sbSQL.append("  from  \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         b.nparentsubjectid nparentsubjectid,  \n");
			sbSQL.append("         b.nbalancedirection nbalancedirection,   \n");
			sbSQL.append("         nvl(sum(a.mAmount),0.0) m_dDebitAmount,  \n");
			sbSQL.append("         nvl(count(a.sTransNO),0) m_lDebitNumber  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlEntry t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+"  \n");
			if (!strSubjectStart.equals(""))
			{
				sbSQL.append( " and t.sSubjectCode>='" + strSubjectStart + "' \n");
			} 
			if (!strSubjectEnd.equals(""))
			{
				sbSQL.append( " and t.sSubjectCode<='" + strSubjectEnd+ "' \n");
			}			
			sbSQL.append("         and t.nStatusID != 0  \n");
			sbSQL.append("         and t.nTransDirection = 1  \n");
			sbSQL.append("         and t.dtExecute between to_date('"+DataFormat.getDateString(tsDateStart)+"', 'yyyy-mm-dd') and to_date('"+DataFormat.getDateString(tsDateEnd)+"', 'yyyy-mm-dd')) a,  \n");
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append("  where a.sSubjectCode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id, b.nparentsubjectid, b.nbalancedirection  \n");
			sbSQL.append("  ) DebitSett_GlEntry,   \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         nvl(sum(a.mAmount),0.0) m_dLoanAmount,  \n");
			sbSQL.append("         nvl(count(a.sTransNO),0) m_lCreditNumber  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlEntry t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+"  \n");
			if (!strSubjectStart.equals(""))
			{
				sbSQL.append( " and t.sSubjectCode>='" + strSubjectStart + "' \n");
			} 
			if (!strSubjectEnd.equals(""))
			{
				sbSQL.append( " and t.sSubjectCode<='" + strSubjectEnd+ "' \n");
			}
			sbSQL.append("         and t.nStatusID != 0  \n");
			sbSQL.append("         and t.nTransDirection = 2  \n");
			sbSQL.append("         and t.dtExecute between to_date('"+DataFormat.getDateString(tsDateStart)+"', 'yyyy-mm-dd') and to_date('"+DataFormat.getDateString(tsDateEnd)+"', 'yyyy-mm-dd')) a,  \n");
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("  where a.sSubjectCode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id  \n");
			sbSQL.append("  ) LoanSett_GlEntry,  \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         decode(b.nbalancedirection,"+SETTConstant.DebitOrCredit.DEBIT+",sum(nvl(a.mDebitBalance,0) + nvl(a.mCreditBalance,0)),"+SETTConstant.DebitOrCredit.CREDIT+",-sum(nvl(a.mDebitBalance,0) + nvl(a.mCreditBalance,0))) m_dStartBalance  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlBalance t  \n");
			sbSQL.append("         where t.nofficeid = "+lOfficeID+"  \n");
			sbSQL.append("         and t.ncurrencyid = "+lCurrencyID+"  \n");
			
			//判断是否是今天查询
			if(DataFormat.getDateString(tsDateStart).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)) && CloseSystemMain.getSystemStatusID(lOfficeID, lCurrencyID,Constant.ModuleType.SETTLEMENT)!=Constant.SystemStatus.CLOSE){
				sbSQL.append("  and t.dtgldate = to_date('"+DataFormat.getDateString(tsDateStart)+"','yyyy-mm-dd'))a,  \n");
			}
			else {
				sbSQL.append("  and t.dtgldate = to_date('"+DataFormat.getDateString(DataFormat.getPreviousDate(tsDateStart))+"','yyyy-mm-dd'))a,  \n");
			}
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append("  where a.sglsubjectcode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id, b.nbalancedirection  \n");
			sbSQL.append("  ) SubjectBalance  \n");
			sbSQL.append("  where DebitSett_GlEntry.id = LoanSett_GlEntry.id  \n");
			sbSQL.append("  and DebitSett_GlEntry.id = SubjectBalance.id  \n");
			if(iflk>0){
				sbSQL.append("  and (DebitSett_GlEntry.m_lDebitNumber >0 or  LoanSett_GlEntry.m_lCreditNumber>0) \n");
			}
			sbSQL.append("  start with DebitSett_GlEntry.id = a.id  \n");
			sbSQL.append("  connect by prior DebitSett_GlEntry.id = DebitSett_GlEntry.nparentsubjectid  \n");
			sbSQL.append("  ) All_Amount from sett_glsubjectdefinition a  \n");
			sbSQL.append("  where a.nOfficeID = "+lOfficeID+" and a.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("  ) Sett_GLSD  \n"); 
			
			StringBuffer strSQLRecord = new StringBuffer();
			strSQLRecord.append("select * from ( " + sbSQL.toString() + " ) \n");
			switch ((int) lOrderParam)
			{
				case 1 :
					strSQLRecord = strSQLRecord.append(" order by m_strAccount");
					break;
				case 3 :
					strSQLRecord = strSQLRecord.append(" order by m_dDebitAmount");
					break;
				case 4 :
					strSQLRecord = strSQLRecord.append(" order by m_dLoanAmount");
					break;
				case 5 :
					strSQLRecord = strSQLRecord.append(" order by m_lDebitNumber");
					break;
				case 6 :
					strSQLRecord = strSQLRecord.append(" order by m_lCreditNumber");
					break;
				case 7 :
					strSQLRecord = strSQLRecord.append(" order by m_dStartBalance");
					break;
				case 8 :
					strSQLRecord = strSQLRecord.append(" order by m_dEndBalance");
					break;
				case 10:
					strSQLRecord = strSQLRecord.append(" order by m_lSubjectType");
					break;
				default :
					strSQLRecord = strSQLRecord.append(" order by id");
					break;
			}
			
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQLRecord.append(" desc");
			}
			Log.print(strSQLRecord.toString());
			ps = conn.prepareStatement(strSQLRecord.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				AccountRecordInfo accountRecordInfo = new AccountRecordInfo();
				accountRecordInfo.setId(rs.getLong("id"));  //科目ID
				accountRecordInfo.setM_strAccount(rs.getString("m_strAccount")); //科目号
				accountRecordInfo.setM_strName(rs.getString("m_strName")); //科目名称
				accountRecordInfo.setM_lSubjectType(rs.getDouble("m_lSubjectType")); //科目类型
				accountRecordInfo.setM_dDebitAmount(rs.getDouble("m_dDebitAmount")); //借方合计金额
				accountRecordInfo.setM_dLoanAmount(rs.getDouble("m_dLoanAmount")); //贷方合计金额
				accountRecordInfo.setM_lDebitNumber(rs.getLong("m_lDebitNumber")); //借方合计数量
				accountRecordInfo.setM_lCreditNumber(rs.getLong("m_lCreditNumber")); //贷方合计数量
				accountRecordInfo.setM_dStartBalance(rs.getDouble("m_dStartBalance")); //期初金额
				accountRecordInfo.setM_dEndBalance(rs.getDouble("m_dEndBalance")); //期末金额
				
				accountRecordInfo.m_dDebitsSumAmount = dDebitsSumAmount; //满足条件全部借方合计,（上海电气只取表内的借方合计）
				accountRecordInfo.m_dCreditSumAmount = dCreditSumAmount; //满足条件全部贷方合计，（上海电气只取表内的贷方合计）
				accountRecordInfo.m_dOffDebitSumAmount = dOffDebitSumAmount;//表外业务借方合计
				accountRecordInfo.m_dOffCreditSumAmount = dOffCreditSumAmount;//表外业务借方合计
				accountRecordInfo.m_lNumber = accountRecordInfo.m_lDebitNumber + accountRecordInfo.m_lCreditNumber; //凭证数量
				
				accountRecordInfo.m_lPageCount = 1;
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
	 * @author 马现福
	 * 2008/01/28
	 * 放款通知单导出excel
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 * @param StrContractCode 合同编号
	 * @param strDateFrom 起始日期
	 * @param strDateTo 结束日期
	 * @param lOrderParam 所要排序的字段
	 * @param lDesc 排序方式
	 * @return Collection
	 * @throws RemoteException
	 */
	public Collection findPayFormInfo(long lOfficeID, long lCurrencyID,String StrContractCode, String strDateFrom, String strDateTo, long lOrderParam, long lDesc) throws RemoteException
	{
		StringBuffer sbSQL = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		
		try
		{
			conn = Database.getConnection();//获得连接
			 
			sbSQL = new StringBuffer();
			sbSQL.append("  select tmp.DtoutDate,tmp.Code ,tmp.Name,tmp.SContractCode,tmp.SCode, \n");
			sbSQL.append("  tmp.NIsCircle,tmp.MAmount,tmp.MInterestRate,tmp.NIntervalNoticeNum \n");
			sbSQL.append("  from ((select lp.DtoutDate,cc.Code ,cc.Name,lc.SContractCode,lp.SCode,\n ");
			sbSQL.append(" lc.NIsCircle,lp.MAmount,lp.MInterestRate,lp.NIntervalNoticeNum \n");
			sbSQL.append(" from (select min(id) id ,nloancontractid \n");
			sbSQL.append("      from (select * \n");
			sbSQL.append("           from SETT_TRANSGRANTLOAN aa \n");
			sbSQL.append("           where aa.nofficeid = "+lOfficeID+" \n");
			sbSQL.append("           and aa.ncurrencyid = "+lCurrencyID+" \n");
			sbSQL.append("           and aa.nstatusid in ("+SETTConstant.TransactionStatus.CHECK+") \n");
			sbSQL.append("           and aa.nloancontractid not in \n");
			sbSQL.append("            (select nloancontractid \n");
			sbSQL.append("            from SETT_TRANSGRANTLOAN \n");
			sbSQL.append("            where dtexecute > to_date('"+DataFormat.getDateString(Env.getSystemDate(lOfficeID,lCurrencyID))+"', 'yyyy-mm-dd') \n");
			sbSQL.append("            and nofficeid = "+lOfficeID+" \n");
			sbSQL.append("            and ncurrencyid = "+lCurrencyID+" \n");
			sbSQL.append("            and nstatusid in ("+SETTConstant.TransactionStatus.CHECK+")) \n");
			sbSQL.append("            order by aa.nloancontractid, aa.dtmodify) d \n");
			sbSQL.append("            group by nloancontractid) dd, \n");
			sbSQL.append(" SETT_TRANSGRANTLOAN e,loan_payform lp,loan_contractform lc,client_clientinfo cc \n");
			sbSQL.append(" where e.id = dd.id \n");
			sbSQL.append(" and lp.id = e.nloannoteid \n");
			sbSQL.append(" and lp.ncontractid = lc.id \n");
			sbSQL.append(" and lc.nborrowclientid = cc.id \n");
			sbSQL.append(" and lp.nOfficeID = lc.nOfficeID \n");
			sbSQL.append(" and lc.nOfficeID = cc.OfficeID \n");
			sbSQL.append(" and lp.nCurrencyID = lc.nCurrencyID \n");
			sbSQL.append(" and lp.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED+" \n");
			sbSQL.append(" and lc.niscircle = 2 )\n");
			sbSQL.append(" union \n");
			sbSQL.append(" (select lp.DtoutDate,cc.Code ,cc.Name,lc.SContractCode,lp.SCode,\n ");
			sbSQL.append(" lc.NIsCircle,lp.MAmount,lp.MInterestRate,lp.NIntervalNoticeNum \n");
			sbSQL.append(" from (select id ,nloancontractid \n");
			sbSQL.append("      from (select * \n");
			sbSQL.append("           from SETT_TRANSGRANTLOAN aa \n");
			sbSQL.append("           where aa.nofficeid = "+lOfficeID+" \n");
			sbSQL.append("           and aa.ncurrencyid = "+lCurrencyID+" \n");
			sbSQL.append("           and aa.nstatusid in ("+SETTConstant.TransactionStatus.CHECK+") \n");
			sbSQL.append("           and aa.nloancontractid not in \n");
			sbSQL.append("            (select nloancontractid \n");
			sbSQL.append("            from SETT_TRANSGRANTLOAN \n");
			sbSQL.append("            where dtexecute > to_date('"+DataFormat.getDateString(Env.getSystemDate(lOfficeID,lCurrencyID))+"', 'yyyy-mm-dd') \n");
			sbSQL.append("            and nofficeid = "+lOfficeID+" \n");
			sbSQL.append("            and ncurrencyid = "+lCurrencyID+" \n");
			sbSQL.append("            and nstatusid in ("+SETTConstant.TransactionStatus.CHECK+")) \n");
			sbSQL.append("            order by aa.nloancontractid, aa.dtmodify) d \n");
			sbSQL.append("            group by id,nloancontractid) dd, \n");
			sbSQL.append(" SETT_TRANSGRANTLOAN e,loan_payform lp,loan_contractform lc,client_clientinfo cc \n");
			sbSQL.append(" where e.id = dd.id \n");
			sbSQL.append(" and lp.id = e.nloannoteid \n");
			sbSQL.append(" and lp.ncontractid = lc.id \n");
			sbSQL.append(" and lc.nborrowclientid = cc.id \n");
			sbSQL.append(" and lp.nOfficeID = lc.nOfficeID \n");
			sbSQL.append(" and lc.nOfficeID = cc.OfficeID \n");
			sbSQL.append(" and lp.nCurrencyID = lc.nCurrencyID \n");
			sbSQL.append(" and lp.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED+" \n");
			sbSQL.append(" and lc.niscircle = 1 )) tmp \n");
			sbSQL.append("  where 1=1 \n");
			if(null != StrContractCode && !"".equals(StrContractCode)){
				sbSQL.append(" and tmp.SContractCode = '"+StrContractCode+"' \n");
			}
			if(null != strDateFrom && !"".equals(strDateFrom)){
				sbSQL.append(" and to_char(tmp.DtoutDate,'mm-dd') >= to_char(to_date('" + strDateFrom+"','mm-dd'),'mm-dd') \n");
			}
			if(null != strDateTo && !"".equals(strDateTo)){
				sbSQL.append(" and to_char(tmp.DtoutDate,'mm-dd') <= to_char(to_date('" + strDateTo+"','mm-dd'),'mm-dd') \n");
			}
			sbSQL.append(" group by tmp.DtoutDate,tmp.Code ,tmp.Name,tmp.SContractCode,tmp.SCode,\n");
			sbSQL.append(" tmp.NIsCircle,tmp.MAmount,tmp.MInterestRate,tmp.NIntervalNoticeNum \n");
	
			switch ((int) lOrderParam)
			{
				case 1 :
					sbSQL.append(" order by tmp.DtoutDate");
					break;
				case 2 :
					sbSQL.append(" order by tmp.Code");
					break;
				case 3 :
					sbSQL.append(" order by tmp.Name");
					break;
				case 4 :
					sbSQL.append(" order by tmp.SContractCode");
					break;
				case 5 :
					sbSQL.append(" order by tmp.SCode");
					break;
				case 6 :
					sbSQL.append(" order by tmp.NIsCircle");
					break;
				case 7 :
					sbSQL.append(" order by tmp.MAmount");
					break;
				case 8:
					sbSQL.append(" order by tmp.MInterestRate");
					break;
				case 9:
					sbSQL.append(" order by tmp.NIntervalNoticeNum");
					break;
				default :
					sbSQL.append(" order by tmp.DtoutDate");
					break;
			}
				
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				sbSQL.append(" desc");
			}
			else{
				sbSQL.append(" asc");
			}
			System.out.println("SQL Info：" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();		
			while (rs.next())
			{
				ContractInfoAdjustQuery recordInfo = new ContractInfoAdjustQuery();
				recordInfo.setDtoutDate(rs.getTimestamp(1));  //科目ID
				recordInfo.setCode(rs.getString(2)); //科目号
				recordInfo.setName(rs.getString(3)); //科目名称
				recordInfo.setSContractCode(rs.getString(4)); //科目类型
				recordInfo.setSCode(rs.getString(5)); //借方合计金额
				recordInfo.setNIsCircle(rs.getLong(6)); //贷方合计金额
				recordInfo.setMAmount(rs.getDouble(7)); //借方合计数量
				recordInfo.setMInterestRate(rs.getDouble(8)); //贷方合计数量
				recordInfo.setNIntervalNoticeNum(rs.getDouble(9)); //期初金额
				list.add(recordInfo);
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
		return (list.size() > 0 ? list : null);
	}
	
	/**
	 * Added by leiyang 2008/03/11
	 * 开户行余额汇总查询
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 * @param tsDateStart 起始日期
	 * @param tsDateEnd 结束日期
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 */
	public Collection findBankAccountRecord(long lOfficeID, long lCurrencyID, Timestamp tsDate, String strBankCodeStart, String strBankCodeEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Log.print("**********************in findDailyAccountRecordNew*********************");
		StringBuffer sbSQL = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
	
			sbSQL = new StringBuffer();
			sbSQL.append("  select Sett_GLSD.id, Sett_GLSD.sbankCode, Sett_GLSD.sbankName, Sett_GLSD.nSubjectId, Sett_GLSD.ssegmentcode, Sett_GLSD.nSubjectType, Sett_GLSD.nbalancedirection, \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1)) m_dDebitAmount,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,1)+1, INSTR(Sett_GLSD.All_Amount,'|',1,2)-INSTR(Sett_GLSD.All_Amount,'|',1,1)-1)) m_lDebitNumber,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1)) m_dLoanAmount,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,3)+1, INSTR(Sett_GLSD.All_Amount,'|',1,4)-INSTR(Sett_GLSD.All_Amount,'|',1,3)-1)) m_lCreditNumber,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount))) m_dStartBalance,  \n");
			sbSQL.append("         decode(Sett_GLSD.nbalancedirection,"+SETTConstant.DebitOrCredit.DEBIT+",TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount)))+TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1))-TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1)),"+SETTConstant.DebitOrCredit.CREDIT+",TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount)))+TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1))-TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1))) m_dEndBalance  \n");
			sbSQL.append("  from (  \n");
			sbSQL.append("  select b.id id, b.scode sbankCode, b.sname sbankName, a.id nSubjectId, a.ssegmentcode2 ssegmentcode, a.nsubjecttype nSubjectType, a.nbalancedirection nbalancedirection, (  \n");
			sbSQL.append("  select sum(DebitSett_GlEntry.m_dDebitAmount) || '|' ||   \n");
			sbSQL.append("         sum(DebitSett_GlEntry.m_lDebitNumber) || '|' ||   \n");
			sbSQL.append("         sum(LoanSett_GlEntry.m_dLoanAmount) || '|' ||   \n");
			sbSQL.append("         sum(LoanSett_GlEntry.m_lCreditNumber) || '|' ||   \n");
			sbSQL.append("         sum(SubjectBalance.m_dStartBalance) All_Amount  \n");
			sbSQL.append("  from  \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         b.nparentsubjectid nparentsubjectid,  \n");
			sbSQL.append("         b.nbalancedirection nbalancedirection,   \n");
			sbSQL.append("         nvl(sum(a.mAmount),0.0) m_dDebitAmount,  \n");
			sbSQL.append("         nvl(count(a.sTransNO),0) m_lDebitNumber  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlEntry t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("         and t.nStatusID != 0  \n");
			sbSQL.append("         and t.nTransDirection = 1  \n");
			sbSQL.append("         and t.dtExecute = to_date('"+DataFormat.getDateString(tsDate)+"', 'yyyy-mm-dd')) a,  \n");
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append("  where a.sSubjectCode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id, b.nparentsubjectid, b.nbalancedirection  \n");
			sbSQL.append("  ) DebitSett_GlEntry,   \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         nvl(sum(a.mAmount),0.0) m_dLoanAmount,  \n");
			sbSQL.append("         nvl(count(a.sTransNO),0) m_lCreditNumber  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlEntry t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("         and t.nStatusID != 0  \n");
			sbSQL.append("         and t.nTransDirection = 2  \n");
			sbSQL.append("         and t.dtExecute = to_date('"+DataFormat.getDateString(tsDate)+"', 'yyyy-mm-dd')) a,  \n");
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("  where a.sSubjectCode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id  \n");
			sbSQL.append("  ) LoanSett_GlEntry,  \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         decode(b.nbalancedirection,"+SETTConstant.DebitOrCredit.DEBIT+",sum(nvl(a.mDebitBalance,0) + nvl(a.mCreditBalance,0)),"+SETTConstant.DebitOrCredit.CREDIT+",-sum(nvl(a.mDebitBalance,0) + nvl(a.mCreditBalance,0))) m_dStartBalance  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlBalance t  \n");
			sbSQL.append("         where t.nofficeid = "+lOfficeID+"  \n");
			sbSQL.append("         and t.ncurrencyid = "+lCurrencyID+"  \n");
			
			//判断是否是今天查询
			if(DataFormat.getDateString(tsDate).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)) && CloseSystemMain.getSystemStatusID(lOfficeID, lCurrencyID,Constant.ModuleType.SETTLEMENT)!=Constant.SystemStatus.CLOSE){
				sbSQL.append("  and t.dtgldate = to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'))a,  \n");
			}
			else {
				sbSQL.append("  and t.dtgldate = to_date('"+DataFormat.getDateString(DataFormat.getPreviousDate(tsDate))+"','yyyy-mm-dd'))a,  \n");
			}
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append("  where a.sglsubjectcode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id, b.nbalancedirection  \n");
			sbSQL.append("  ) SubjectBalance  \n");
			sbSQL.append("  where DebitSett_GlEntry.id = LoanSett_GlEntry.id  \n");
			sbSQL.append("  and DebitSett_GlEntry.id = SubjectBalance.id  \n");
			sbSQL.append("  start with DebitSett_GlEntry.id = a.id  \n");
			sbSQL.append("  connect by prior DebitSett_GlEntry.id = DebitSett_GlEntry.nparentsubjectid  \n");
			sbSQL.append("  ) All_Amount from sett_glsubjectdefinition a, sett_Branch b  \n");
			sbSQL.append("  where a.ssegmentcode2 = b.ssubjectcode  \n");
			sbSQL.append("  and a.nOfficeID = "+lOfficeID+" and a.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("  and b.nOfficeID = "+lOfficeID+" and b.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("  and b.nstatusid = 1 and b.banksubjecttype != 2  \n");
			if(!strBankCodeStart.equals("") && !strBankCodeEnd.equals("")){
				sbSQL.append("  and b.scode >= '"+ strBankCodeStart +"' and b.scode <= '"+ strBankCodeEnd +"'  \n");
			}
			sbSQL.append("  ) Sett_GLSD  \n");
			
			StringBuffer strSQLRecord = new StringBuffer();
			strSQLRecord.append("select * from ( " + sbSQL.toString() + " ) \n");
			switch ((int) lOrderParam)
			{
				case 1 :
					strSQLRecord = strSQLRecord.append(" order by sbankCode");
					break;
				case 2 :
					strSQLRecord = strSQLRecord.append(" order by sbankName");
					break;
				case 3 :
					strSQLRecord = strSQLRecord.append(" order by ssegmentcode");
					break;
				case 4 :
					strSQLRecord = strSQLRecord.append(" order by m_dStartBalance");
					break;
				case 5 :
					strSQLRecord = strSQLRecord.append(" order by m_dDebitAmount");
					break;
				case 6 :
					strSQLRecord = strSQLRecord.append(" order by m_dLoanAmount");
					break;
				case 7 :
					strSQLRecord = strSQLRecord.append(" order by m_dEndBalance");
					break;
				default :
					strSQLRecord = strSQLRecord.append(" order by sbankCode");
					break;
			}
			
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQLRecord.append(" desc");
			}
			Log.print(strSQLRecord.toString());
			ps = conn.prepareStatement(strSQLRecord.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				BankAccountRecordInfo accountRecordInfo = new BankAccountRecordInfo();
				accountRecordInfo.setId(rs.getLong("id")); //开户行ID
				accountRecordInfo.setM_strBankCode(rs.getString("sbankCode")); //开户行Cdoe
				accountRecordInfo.setM_strBankName(rs.getString("sbankName")); //开户行名称
				accountRecordInfo.setSubjectId(rs.getLong("nSubjectId")); //科目ID
				accountRecordInfo.setM_strRootSubject(rs.getString("ssegmentcode")); //科目号
				accountRecordInfo.setM_lsubjectType(rs.getLong("nSubjectType")); //科目类型
				accountRecordInfo.setM_dDebitAmount(rs.getDouble("m_dDebitAmount")); //借方合计金额
				accountRecordInfo.setM_dLoanAmount(rs.getDouble("m_dLoanAmount")); //贷方合计金额
				accountRecordInfo.setM_lDebitNumber(rs.getLong("m_lDebitNumber")); //借方合计数量
				accountRecordInfo.setM_lCreditNumber(rs.getLong("m_lCreditNumber")); //贷方合计数量
				accountRecordInfo.setM_dStartBalance(rs.getDouble("m_dStartBalance")); //期初金额
				accountRecordInfo.setM_dEndBalance(rs.getDouble("m_dEndBalance")); //期末金额
				accountRecordInfo.m_lPageCount = 1;

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
	 * 通过sSubjectCode（科目号）查询sSubjectName（科目名称）.
	 * @param accountTransactionInfo
	 * @return
	 * @throws RemoteException
	 */
	private String getsSubjectName(String m_strAccount){
		Log.print("**********************in getsSubjectName*********************");
		String sSubjectName="";
		String sql="";
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = Database.getConnection();
			sql="select SSUBJECTNAME from Sett_VGlSubjectDefinition where SSUBJECTCODE=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,m_strAccount);
		    rs=ps.executeQuery();
		    rs.next();
		    sSubjectName=rs.getString("SSUBJECTNAME");
		    rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch(Exception ex){
			ex.printStackTrace();
			//System.out.println("222222222");
			
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
	
			}
		}
		return sSubjectName;
	}
	/*
	 * 根据交易号得到交易的详细情况	 
	 */
	public AccountTransactionInfo findTransactionDetail(AccountTransactionInfo accountTransactionInfo) throws RemoteException
	{
		Log.print("**********************in findTransactionDetail*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.OPENFIXEDDEPOSIT || accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransOpenFixedDeposit  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransFixedContinue  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransFixedWithDraw  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.DISCOUNTGRANT)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransGrantDiscount  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.DISCOUNTRECEIVE)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransRepaymentDiscount  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.TRUSTLOANGRANT || accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANGRANT)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransGrantLoan  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.TRUSTLOANRECEIVE || accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransRepaymentLoan  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.GENERALLEDGER)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransGeneralLedger  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.INTERESTGRANT)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransCurrentClearInterest  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.SPECIALOPERATION)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransSpecialOperation  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.TRANSFEE)
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from Sett_TransFee  ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
            else if (accountTransactionInfo.m_lTransactionTypeID == SETTConstant.TransactionType.COMPATIBILITY)
            {//兼容业务 ninh 2004-09-15
                sbSQL.append("  select ID,OfficeID nOfficeID,CurrencyID nCurrencyID,TransNo sTransNo,TransactionTypeID nTransactionTypeID");
                sbSQL.append("  ,StatusID nStatusID,InputUserID nInputUserID,CheckUserID nCheckUserID,Abstract sAbstract ");
                sbSQL.append("  from sett_transcompatibility  ");
                sbSQL.append("  where TransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
            }
			else
			{
				sbSQL.append("  select ID,nOfficeID,nCurrencyID,sTransNo,nTransactionTypeID,nStatusID,nInputUserID,nCheckUserID,sAbstract ");
				sbSQL.append("  from  Sett_TransCurrentDeposit ");
				sbSQL.append("  where sTransNo= '" + accountTransactionInfo.m_strTransNo + "' ");
			}
			ps = conn.prepareStatement(sbSQL.toString());
			Log.print("before query");
			Log.print(sbSQL.toString());
			rs = ps.executeQuery();
			Log.print("after query");
			if (rs.next())
			{
				accountTransactionInfo.m_strAbstract = rs.getString("sAbstract"); //摘要
				accountTransactionInfo.m_strInputUser = NameRef.getUserNameByID(rs.getLong("nInputUserID")); //录入人
				accountTransactionInfo.m_strCheckUser = NameRef.getUserNameByID(rs.getLong("nCheckUserID")); //复核人
				accountTransactionInfo.m_lStatusID = rs.getLong("nStatusID"); //状态
				accountTransactionInfo.m_strStatus = SETTConstant.TransactionStatus.getName(accountTransactionInfo.m_lStatusID); //状态名称
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
		 * 日结会计账汇总
		 * @param lOfficeID  办事处标识
		 * @param lCurrencyID 币种
		 * @param tsDateStart 起始日期
		 * @param tsDateEnd 结束日期
		 * @param lPageLineCount
		 * @param lPageNo
		 * @param lOrderParam
		 * @param lDesc
		 */
	public Collection findGLTransType(String strAccount, long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Log.print("**********************in findGLTransType*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			//added by jiangwei
			sbSQL.setLength(0);
			sbSQL.append(" select Sett_TransTypeDefinition.nTransactionTypeID, \n");
			sbSQL.append(" DebitSett_GlEntry.mDebit,DebitSett_GlEntry.nDebitCount, \n");
			sbSQL.append(" LoanSett_GlEntry.mLoan,LoanSett_GlEntry.nLoanCount  \n");
			sbSQL.append(" from \n");
			sbSQL.append(" ( \n");
			sbSQL.append(" select distinct a.nTransactionTypeID \n");
			sbSQL.append(" from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append(" where a.sSubjectCode=b.sSubjectCode(+) and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.dtExecute between ? and ? \n");
			//sbSQL.append(" where a.sSubjectCode(+)=b.sSubjectCode and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " \n");
			//sbSQL.append(" and a.dtExecute between ? and ? \n");
			sbSQL.append(" ) Sett_TransTypeDefinition, ");
			sbSQL.append(" ( \n");
			sbSQL.append(" select a.nTransactionTypeID,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount \n");
			sbSQL.append(" from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append(" where a.sSubjectCode=b.sSubjectCode(+) and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.dtExecute between ? and ? \n");
			sbSQL.append(" group by a.nTransactionTypeID \n");
			sbSQL.append(" ) DebitSett_GlEntry, \n");
			sbSQL.append(" ( \n");
			sbSQL.append(" select a.nTransactionTypeID,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount \n");
			sbSQL.append(" from Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append(" where a.sSubjectCode=b.sSubjectCode(+) and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " \n");
			sbSQL.append(" and b.sRootSubject = '" + strAccount + "' and a.dtExecute between ? and ? \n");
			sbSQL.append(" group by a.nTransactionTypeID \n");
			sbSQL.append(" ) LoanSett_GlEntry \n");
			sbSQL.append(" where Sett_TransTypeDefinition.nTransactionTypeID=DebitSett_GlEntry.nTransactionTypeID(+) \n");
			sbSQL.append(" and Sett_TransTypeDefinition.nTransactionTypeID=LoanSett_GlEntry.nTransactionTypeID(+)  \n");
			sbSQL.append(" and (DebitSett_GlEntry.mDebit <> 0 or LoanSett_GlEntry.mLoan <> 0 or DebitSett_GlEntry.nDebitCount<>0 or LoanSett_GlEntry.nLoanCount<>0) ");
			//查询记录
			String strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " ) ";
			switch ((int) lOrderParam)
			{
				case 1 :
				default :
					strSQLRecord = strSQLRecord + " order by nTransactionTypeID ";
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
			log4j.print(sbSQL.toString());
			ps = conn.prepareStatement(strSQLRecord);
			int nPos = 1;
			ps.setTimestamp(nPos++, tsDateStart);
			ps.setTimestamp(nPos++, tsDateEnd);
			ps.setTimestamp(nPos++, tsDateStart);
			ps.setTimestamp(nPos++, tsDateEnd);
			ps.setTimestamp(nPos++, tsDateStart);
			ps.setTimestamp(nPos++, tsDateEnd);
			rs = ps.executeQuery();
			
			System.out.println("现在要查询的科目号为："+strAccount);
			
			while (rs.next())
			{
				AccountTransactionTypeInfo accountTransactionTypeInfo = new AccountTransactionTypeInfo();
				accountTransactionTypeInfo.m_dDebit = rs.getDouble("mDebit"); //借方合计
				accountTransactionTypeInfo.m_dLoan = rs.getDouble("mLoan"); //贷方合计
				accountTransactionTypeInfo.m_lDebitNumber = rs.getLong("nDebitCount");
				accountTransactionTypeInfo.m_lCreditNumber = rs.getLong("nLoanCount");
				accountTransactionTypeInfo.m_lNumber = accountTransactionTypeInfo.m_lDebitNumber + accountTransactionTypeInfo.m_lCreditNumber; //凭证数量
				accountTransactionTypeInfo.m_lTransactionTypeID = rs.getLong("nTransactionTypeID");
				accountTransactionTypeInfo.m_strTransactionType = SETTConstant.TransactionType.getName(accountTransactionTypeInfo.m_lTransactionTypeID);
				accountTransactionTypeInfo.m_lPageCount = 1;
				
				accountTransactionTypeInfo.m_strAccount=strAccount;	//得到科目号
				System.out.println("加入类accountTransactionTypeInfo的科目号为:"+accountTransactionTypeInfo.m_strAccount);
				v.add(accountTransactionTypeInfo);
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
	
	
	//Added by leiyang 2008/01/21 在用子父科目查询时也可用，查询字段使用科目ID
		/**
		 * 日结会计账汇总
		 * @param lOfficeID  办事处标识
		 * @param lCurrencyID 币种
		 * @param tsDateStart 起始日期
		 * @param tsDateEnd 结束日期
		 * @param lPageLineCount
		 * @param lPageNo
		 * @param lOrderParam
		 * @param lDesc
		 */
	public Collection findGLTransType(long lAccount, long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Log.print("**********************in findGLTransType*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			sbSQL.append(" select distinct Sett_TransTypeDefinition.nTransactionTypeID,  \n");
			sbSQL.append(" DebitSett_GlEntry.mDebit,DebitSett_GlEntry.nDebitCount, \n");
			sbSQL.append(" LoanSett_GlEntry.mLoan,LoanSett_GlEntry.nLoanCount  \n");
			sbSQL.append(" from \n");
			sbSQL.append(" ( \n");
			sbSQL.append(" select distinct a.nTransactionTypeID,a.sSubjectCode \n");
			sbSQL.append(" from Sett_GlEntry a, (select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID);
			sbSQL.append(" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid) b  \n");
			sbSQL.append(" where a.sSubjectCode(+)=b.sSubjectCode and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " \n");
			sbSQL.append(" and a.dtExecute between ? and ? \n");
			sbSQL.append(" ) Sett_TransTypeDefinition, ");
			sbSQL.append(" ( \n");
			sbSQL.append(" select a.nTransactionTypeID,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount \n");
			sbSQL.append(" from Sett_GlEntry a, (select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID);
			sbSQL.append(" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid) b  \n");
			sbSQL.append(" where a.sSubjectCode(+)=b.sSubjectCode and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " \n");
			sbSQL.append(" and a.dtExecute between ? and ? \n");
			sbSQL.append(" group by a.nTransactionTypeID \n");
			sbSQL.append(" ) DebitSett_GlEntry, \n");
			sbSQL.append(" ( \n");
			sbSQL.append(" select a.nTransactionTypeID,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount \n");
			sbSQL.append(" from Sett_GlEntry a, (select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID);
			sbSQL.append(" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid) b  \n");
			sbSQL.append(" where a.sSubjectCode(+)=b.sSubjectCode and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " \n");
			sbSQL.append(" and a.dtExecute between ? and ? \n");
			sbSQL.append(" group by a.nTransactionTypeID \n");
			sbSQL.append(" ) LoanSett_GlEntry \n");
			sbSQL.append(" where Sett_TransTypeDefinition.nTransactionTypeID=DebitSett_GlEntry.nTransactionTypeID(+) \n");
			sbSQL.append(" and Sett_TransTypeDefinition.nTransactionTypeID=LoanSett_GlEntry.nTransactionTypeID(+)  \n");
			sbSQL.append(" and (DebitSett_GlEntry.mDebit <> 0 or LoanSett_GlEntry.mLoan <> 0 or DebitSett_GlEntry.nDebitCount<>0 or LoanSett_GlEntry.nLoanCount<>0) ");
			//查询记录
			String strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " ) ";
			switch ((int) lOrderParam)
			{
				case 1 :
				default :
					strSQLRecord = strSQLRecord + " order by nTransactionTypeID ";
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
			
			System.out.println("现在要查询的科目ID为：" + lAccount);
			
			while (rs.next())
			{
				AccountTransactionTypeInfo accountTransactionTypeInfo = new AccountTransactionTypeInfo();
				accountTransactionTypeInfo.m_dDebit = rs.getDouble("mDebit"); //借方合计
				accountTransactionTypeInfo.m_dLoan = rs.getDouble("mLoan"); //贷方合计
				accountTransactionTypeInfo.m_lDebitNumber = rs.getLong("nDebitCount");
				accountTransactionTypeInfo.m_lCreditNumber = rs.getLong("nLoanCount");
				accountTransactionTypeInfo.m_lNumber = accountTransactionTypeInfo.m_lDebitNumber + accountTransactionTypeInfo.m_lCreditNumber; //凭证数量
				accountTransactionTypeInfo.m_lTransactionTypeID = rs.getLong("nTransactionTypeID");
				accountTransactionTypeInfo.m_strTransactionType = SETTConstant.TransactionType.getName(accountTransactionTypeInfo.m_lTransactionTypeID);
				accountTransactionTypeInfo.m_lPageCount = 1;
				
				//accountTransactionTypeInfo.m_strAccount = rs.getString("sSubjectCode");	//得到科目号
				//System.out.println("加入类accountTransactionTypeInfo的科目号为:"+accountTransactionTypeInfo.m_strAccount);
				v.add(accountTransactionTypeInfo);
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
	 * 日结会计账汇总
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 * @param tsDateStart 起始日期
	 * @param tsDateEnd 结束日期
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 */
public Collection findGLTransTypeForAll(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
{
	Log.print("**********************in findGLTransType ForAll*********************");
	StringBuffer sbSQL = new StringBuffer();
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Vector vAll = new Vector();	//得到所有的科目集合的信息
	Vector vOne = new Vector();	//得到一个的科目集合的信息

	Vector vAccount=new Vector();
	String strAccount="";
	try
	{
		//得到账户类型，账户编号
		conn = Database.getConnection();

		//select sSegmentCode1||'.'||sSegmentCode2||'.'||sSegmentCode3||'.'||sSegmentCode4 subjectNO from sett_VGlSubjectDefinition where nStatus=1 and nofficeid=1 and ncurrencyid=1;
		//第一步得到所有的科目号关于此办事处和币种的
		sbSQL.setLength(0);
		//sbSQL.append(" select sSegmentCode1||'.'||sSegmentCode2||'.'||sSegmentCode3||'.'||sSegmentCode4 subjectNO \n");
		
		//modify by liuchuan
		sbSQL.append(" select sSegmentCode2 subjectNO \n");	
		
		sbSQL.append(" from sett_VGlSubjectDefinition a \n");
		sbSQL.append(" where a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatus,0) >0 " + " \n");
		log4j.print(sbSQL.toString());
		ps = conn.prepareStatement(sbSQL.toString());
		rs = ps.executeQuery();
		while (rs.next())
		{
			strAccount=rs.getString("subjectNO");  //得到科目号
			if(strAccount!=null && !strAccount.equals("")){
				vAccount.add(strAccount);
			}
		}
		
		//遍历得到的科目号进行逐个科目的查找相关的科目信息
		if(vAccount!=null && vAccount.size()>0){
			System.out.println("    kkf:得到的科目数为:"+vAccount.size());
			
			for(int i=0;i<vAccount.size();i++){
				strAccount=(String)vAccount.get(i);
				System.out.println("    开始第"+i+"个科目的查询,/n");
				
				//查找出每个的集合文件
				vOne = (Vector)findGLTransType(strAccount,lOfficeID,lCurrencyID,tsDateStart,tsDateEnd,10000,lPageNo,lOrderParam,lDesc);
				
				if(vOne!=null && vOne.size()>0){
					//每个里面没内容就不添加到汇总科目里面
					vAll.add(vOne);	//把每个科目的信息添加到汇总的科目信息里
					System.out.println("   目前汇总科目里面的科目个数为:"+vAll.size());
				}
			}
		}
		
		//关闭资源
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
	return (vAll.size() > 0 ? vAll : null);
}

/**
 * 查询科目号下交易类型汇总
 * @param strAccount 会计账号
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
public Collection findGLDetailsForPrint(String strRootAccount, String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd, long lOrderParam, long lDesc) throws RemoteException
{
Log.print("**********************in findGLDetails*********************");
StringBuffer sbSQL = new StringBuffer();
Connection conn = null;
PreparedStatement ps = null;
ResultSet rs = null;
Vector v = new Vector();
try
{
	conn = Database.getConnection();

		sbSQL.append("     select a.nOfficeID,a.nCurrencyID,a.sSubjectCode,a.sTransNo,a.nTransactionTypeID,a.nTransDirection, \n");
		sbSQL.append("            a.mAmount,a.dtInterestStart,a.dtExecute,a.nStatusID,a.sAbstract,a.nInputUserID,a.nCheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
		sbSQL.append("     from  Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+")  b,UserInfo c,UserInfo d \n");
		sbSQL.append("     where a.sSubjectCode=b.sSubjectCode(+) and a.nInputUserID=c.ID(+) and a.nCheckUserID=d.ID(+) and a.nOfficeID= " + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 ");
		
	if (strRootAccount != null && strRootAccount.length() > 0)
	{
		sbSQL.append("       and b.sRootSubject = '" + strRootAccount + "'");
	}
	if (strAccount != null && strAccount.length() > 0)
	{
		sbSQL.append("       and b.sSubjectCode = '" + strAccount + "'");
	}
	if (strTransNo != null && strTransNo.length() > 0)
	{
		sbSQL.append("       and a.sTransNo = '" + strTransNo + "'");
	}
	if (lTypeID > 0)
	{
		sbSQL.append("       and a.nTransDirection = " + lTypeID);
	}
	if (lTransTypeID > 0)
	{
		sbSQL.append("       and a.nTransactionTypeID = " + lTransTypeID);
	}
	if (tsDateStart != null && tsDateEnd != null)
	{
		sbSQL.append("       and   a.dtExecute between ? and ? \n");
	}
	
	String strSQLRecordCount = "";
	strSQLRecordCount = "select count(*) nCount,sum(mAmount) mAmount from (  " + sbSQL.toString() + ") aa";
	ps = conn.prepareStatement(strSQLRecordCount);
	Log.print(strSQLRecordCount);
	if (tsDateStart != null && tsDateEnd != null)
	{
		ps.setTimestamp(1, tsDateStart);
		ps.setTimestamp(2, tsDateEnd);
		
	}
	Log.print("********************** before count*********************");
	rs = ps.executeQuery();
	Log.print("********************** after count*********************");
	
	double dSumAmount = 0.0;
	if (rs.next())
	{
		dSumAmount = rs.getDouble(2);
	}
	rs.close();
	rs = null;
	ps.close();
	ps = null;

	//查询记录
	String strSQLRecord = "select * from  ( select aa.* from ( " + sbSQL.toString();
	switch ((int) lOrderParam)
	{
		case 1 :
		default :
			strSQLRecord = strSQLRecord + " order by dtInterestStart ";
			break;
		case 2 :
			strSQLRecord = strSQLRecord + " order by sTransNo ";
			break;
		case 3 :
			strSQLRecord = strSQLRecord + " order by nTransactionTypeID ";
			break;
		case 4 :
			strSQLRecord = strSQLRecord + " order by sSubjectCode ";
			break;
		case 5 :
			strSQLRecord = strSQLRecord + " order by mAmount ";
			break;
		case 6 :
			strSQLRecord = strSQLRecord + " order by nTransDirection ";
			break;
	}
	if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
	{
		strSQLRecord += " desc";
	}
	strSQLRecord = strSQLRecord + " ) aa ) ";
	Log.print(strSQLRecord);
	ps = conn.prepareStatement(strSQLRecord);
	if (tsDateStart != null && tsDateEnd != null)
	{
		ps.setTimestamp(1, tsDateStart);
		ps.setTimestamp(2, tsDateEnd);
		
	}
	Log.print("before query");
	rs = ps.executeQuery();
	Log.print("after query");
	while (rs.next())
	{
		AccountTransactionInfo accountTransactionInfo = new AccountTransactionInfo();
		accountTransactionInfo.m_tsExecute = rs.getTimestamp("dtExecute"); //执行日期
		//accountTransactionInfo.m_lTransactionNoID = rs.getLong("nTransactionNoID"); //交易标识
		accountTransactionInfo.m_lTransactionTypeID = rs.getLong("nTransactionTypeID");
		accountTransactionInfo.m_strTransactionType = SETTConstant.TransactionType.getName(accountTransactionInfo.m_lTransactionTypeID);
		accountTransactionInfo.m_strTransNo = rs.getString("sTransNo"); //交易号
		accountTransactionInfo.m_strAccountRecord = rs.getString("sSubjectCode"); //科目号
		accountTransactionInfo.m_dAmount = rs.getDouble("mAmount"); //金额
		accountTransactionInfo.m_lDirectionID = rs.getLong("nTransDirection"); //借贷标识
		accountTransactionInfo.m_strDirection = SETTConstant.DebitOrCredit.getName(accountTransactionInfo.m_lDirectionID);
		//借贷名称
		//accountTransactionInfo.m_lStatusID = rs.getLong("nStatusID"); //状态
		//accountTransactionInfo.m_strStatus = SETTConstant.TransactionStatus.getName(accountTransactionInfo.m_lStatusID); //状态名称

		accountTransactionInfo.m_dTotalAmount = dSumAmount;
		//				得到对应科目号
		long lOtherDirection = accountTransactionInfo.m_lDirectionID == SETTConstant.DebitOrCredit.DEBIT ? SETTConstant.DebitOrCredit.CREDIT : SETTConstant.DebitOrCredit.DEBIT;
		String strOtherSQL = "select distinct sSubjectCode from Sett_GlEntry where sTransNo='" + accountTransactionInfo.m_strTransNo + "' and nTransDirection=" + lOtherDirection + " and nvl(nStatusID,0)>0 ";
		PreparedStatement psOther = conn.prepareStatement(strOtherSQL);
		ResultSet rsOther = psOther.executeQuery();
		Vector vectorOtherAccount = new Vector(); //对方科目号
		while (rsOther.next())
		{
			accountTransactionInfo.m_strOtherAccountRecord = accountTransactionInfo.m_strOtherAccountRecord + rsOther.getString("sSubjectCode");
		}
		rsOther.close();
		rsOther = null;
		psOther.close();
		psOther = null;
		
			accountTransactionInfo.m_lStatusID = rs.getLong("nStatusID"); //状态
			accountTransactionInfo.m_strStatus = SETTConstant.TransactionStatus.getName(accountTransactionInfo.m_lStatusID); //状态名称
		
		accountTransactionInfo.m_strAbstract = rs.getString("sAbstract");
		accountTransactionInfo.m_strInputUser = rs.getString("sInputUserName");
		accountTransactionInfo.m_strCheckUser = rs.getString("sCheckUserName");
		if (accountTransactionInfo.m_strInputUser == null || accountTransactionInfo.m_strInputUser.length() <= 0)
		{
			accountTransactionInfo.m_strInputUser = "机制";
		}
		if (accountTransactionInfo.m_strCheckUser == null || accountTransactionInfo.m_strCheckUser.length() <= 0)
		{
			accountTransactionInfo.m_strCheckUser = "机核";
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
	/*TOCONFIG-END*/
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
Log.print("********************** finish findGLDetails*********************");
return (v.size() > 0 ? v : null);
}

//Modify by leiyang 2008/01/22 支持子科目
public Collection PrintGL(long lOfficeID,long lCurrencyID,PrintGLInfo printGLInfo) throws Exception
{
	Vector v = new Vector();
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuffer m_sbSQL = null;
	m_sbSQL = new StringBuffer();
	try
	{
		conn = Database.getConnection();
		m_sbSQL.append("select distinct t.stransno,t.SBILLNO,t.SBANKCHEQUENO, t1.dtexecute dtexecute1,to_char(t1.dtExecute,'dd') ExecuteDay,to_char(t1.dtExecute,'mm') ExecuteMonth,to_char(t1.dtExecute,'yyyy') ExecuteYear, t2.nsubjecttype, t1.mamount mamount1 ,t1.nTransactionTypeID TransTypeID,t1.SABSTRACT,t1.NTRANSDIRECTION  NTRANSDIRECTION1  ");
		m_sbSQL.append("from sett_transaccountdetail  t,sett_glentry t1,sett_glsubjectdefinition t2 ");
		m_sbSQL.append(" where t.stransno (+)= t1.stransno ");
		m_sbSQL.append("and t1.ssubjectcode (+)= t2.ssegmentcode2");
		m_sbSQL.append(" and t1.nstatusid > 0");
		m_sbSQL.append(" and t1.nofficeid ="+lOfficeID);
		m_sbSQL.append(" and t1.ncurrencyid = "+lCurrencyID);
		m_sbSQL.append(" and t2.nofficeid ="+lOfficeID);
		m_sbSQL.append(" and t2.ncurrencyid = "+lCurrencyID);
		if(printGLInfo.getStartDate()!=null && printGLInfo.getEndDate()!=null){
			m_sbSQL.append(" and t1.dtexecute between to_date('"+DataFormat.formatDate(printGLInfo.getStartDate())+"','yyyy-mm-dd') and to_date('"+DataFormat.formatDate(printGLInfo.getEndDate())+"','yyyy-mm-dd') ");
		}
		m_sbSQL.append(" start with t2.Id = "+ printGLInfo.getId() +"  connect by prior t2.Id = t2.Nparentsubjectid");
		m_sbSQL.append(" order by t1.dtexecute");
		Log.print(m_sbSQL.toString());
		ps = conn.prepareStatement(m_sbSQL.toString());

		rs = ps.executeQuery();
//		取得期初余额
		double dBalance = 0.0;
		dBalance=getGLBalance(lOfficeID,lCurrencyID,printGLInfo);
		Log.print("科目的期初金额："+dBalance);
//		取得期初余额结束
		Timestamp tsLastDate = null; //每日合计 用
		double dDayPayBalance = 0.0; //每日付款合计
		double dDayReceiveBalance = 0.0; //每日收款合计
		int derection=-1;
		boolean mark=true;
		while(rs.next())
		{
			if (tsLastDate != null && rs.getTimestamp("dtexecute1") != null && !DataFormat.formatDate(tsLastDate).equals(DataFormat.formatDate(rs.getTimestamp("dtexecute1"))))
			{
				PrintGLInfo resultInfo2 = new PrintGLInfo();
				resultInfo2.setTransNo("&nbsp");
				resultInfo2.setAbstract("<b>本日合计</b>");
				resultInfo2.setExecuteMonth(Long.valueOf(DataFormat.getMonthString(tsLastDate)).longValue() + 1);
				resultInfo2.setExecuteYear(Long.valueOf(DataFormat.getYearString(tsLastDate)).longValue());
				resultInfo2.setExecuteDate(tsLastDate);
				resultInfo2.setTransTypeID(-1000); //表示为日合计
				resultInfo2.setPayAmount(dDayPayBalance);
				resultInfo2.setReceiveAmount(dDayReceiveBalance);
				resultInfo2.setBalance(dBalance>0?dBalance:-dBalance);
				v.add(resultInfo2);
				
				dDayPayBalance = 0.0;
				dDayReceiveBalance = 0.0;
			}
			tsLastDate = rs.getTimestamp("dtexecute1");

			PrintGLInfo resultInfo = new PrintGLInfo();
			resultInfo.setTransNo(rs.getString("stransno"));
			resultInfo.setExecuteDate(rs.getTimestamp("dtexecute1"));
			resultInfo.setExecuteDay(rs.getLong("ExecuteDay"));
			resultInfo.setExecuteMonth(rs.getLong("ExecuteMonth"));
			resultInfo.setExecuteYear(rs.getLong("ExecuteYear"));
			resultInfo.setTransTypeID(rs.getLong("TransTypeID"));
			resultInfo.setAbstract(rs.getString("SABSTRACT"));
			resultInfo.setBillNo(rs.getString("SBILLNO"));
			resultInfo.setBankChequeNo(rs.getString("SBANKCHEQUENO"));
			derection=rs.getInt("NTRANSDIRECTION1");
			if(derection==SETTConstant.ControlDirection.DEBIT)
				resultInfo.setPayAmount(rs.getDouble("mamount1"));
			if(derection==SETTConstant.ControlDirection.CREDIT)
			resultInfo.setReceiveAmount(rs.getDouble("mamount1"));
			resultInfo.setNsubjecttype(rs.getLong("nsubjecttype"));
			
			if(resultInfo.getNsubjecttype()!=SETTConstant.SubjectAttribute.ASSET )
			{
				dBalance = -dBalance ;
				mark=false;
			}
			//资产类
			if(resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.ASSET  || resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.PAYOUT)
			{
				dBalance = dBalance + DataFormat.formatDouble(resultInfo.getPayAmount(), 2) - DataFormat.formatDouble(resultInfo.getReceiveAmount(), 2);
			}
			//负债类
			if(resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.RIGHT ||resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.INCOME ||resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.DEBT)
			{
				dBalance = dBalance - DataFormat.formatDouble(resultInfo.getPayAmount(), 2) + DataFormat.formatDouble(resultInfo.getReceiveAmount(), 2);
				dBalance = -dBalance ;
			}
			dDayPayBalance = dDayPayBalance + DataFormat.formatDouble(resultInfo.getPayAmount(), 2);
			dDayReceiveBalance = dDayReceiveBalance + DataFormat.formatDouble(resultInfo.getReceiveAmount(), 2);
			resultInfo.setBalance(dBalance>0?dBalance:-dBalance);
			v.add(resultInfo);
		}
		PrintGLInfo resultInfo3 = new PrintGLInfo();
		resultInfo3.setTransNo("&nbsp");
		resultInfo3.setAbstract("<b>本日合计</b>");
		resultInfo3.setExecuteDate(tsLastDate);
		if (tsLastDate != null)
		{
			long lExecuteMonth = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(5, 7)).longValue();
			long lExecuteYear = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(0, 4)).longValue();
			resultInfo3.setExecuteMonth(lExecuteMonth);
			resultInfo3.setExecuteYear(lExecuteYear);
			Log.print("lExecuteMonth" + lExecuteMonth);
			Log.print("setExecuteYear" + lExecuteYear);
			Log.print("tsLastDate" + tsLastDate);
		}
		resultInfo3.setTransTypeID(-1000); //表示为日合计
		resultInfo3.setPayAmount(dDayPayBalance);
		resultInfo3.setReceiveAmount(dDayReceiveBalance);
		resultInfo3.setBalance(dBalance>0?dBalance:-dBalance);
		Log.print("本日合计");
		v.add(resultInfo3);
		
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
	finally
	{
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}
	return v;
}

//上面方法的支持分页方法
public Collection PrintGLfy(long lOfficeID,long lCurrencyID,PrintGLInfo printGLInfo, long lPageLineCount, long lPageNo) throws Exception
{
	Vector v = new Vector();
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuffer m_sbSQL = null;
	m_sbSQL = new StringBuffer();
	try
	{
		conn = Database.getConnection();
		m_sbSQL.append("select distinct t.stransno,t.SBILLNO,t.SBANKCHEQUENO, t1.dtexecute dtexecute1,to_char(t1.dtExecute,'dd') ExecuteDay,to_char(t1.dtExecute,'mm') ExecuteMonth,to_char(t1.dtExecute,'yyyy') ExecuteYear, t2.nsubjecttype, t1.mamount mamount1 ,t1.nTransactionTypeID TransTypeID,t1.SABSTRACT,t1.NTRANSDIRECTION  NTRANSDIRECTION1  ");
		m_sbSQL.append("from sett_transaccountdetail  t,sett_glentry t1,sett_glsubjectdefinition t2 ");
		m_sbSQL.append(" where t.stransno (+)= t1.stransno ");
		m_sbSQL.append("and t1.ssubjectcode (+)= t2.ssegmentcode2");
		m_sbSQL.append(" and t1.nstatusid > 0");
		m_sbSQL.append(" and t1.nofficeid ="+lOfficeID);
		m_sbSQL.append(" and t1.ncurrencyid = "+lCurrencyID);
		m_sbSQL.append(" and t2.nofficeid ="+lOfficeID);
		m_sbSQL.append(" and t2.ncurrencyid = "+lCurrencyID);
		if(printGLInfo.getStartDate()!=null && printGLInfo.getEndDate()!=null){
			m_sbSQL.append(" and t1.dtexecute between to_date('"+DataFormat.formatDate(printGLInfo.getStartDate())+"','yyyy-mm-dd') and to_date('"+DataFormat.formatDate(printGLInfo.getEndDate())+"','yyyy-mm-dd') ");
		}
		m_sbSQL.append(" start with t2.Id = "+ printGLInfo.getId() +"  connect by prior t2.Id = t2.Nparentsubjectid");
		m_sbSQL.append(" order by t1.dtexecute");
		Log.print(m_sbSQL.toString());
		
		String strSQLRecordCount = "select count(*) nCount from (  " + m_sbSQL.toString() + ") aa";
		ps = conn.prepareStatement(strSQLRecordCount);
		rs = ps.executeQuery();
		
		long lRecordCount = 0; //记录数目
		//double dSumAmount = 0.0;
		if (rs.next())
		{
			lRecordCount = rs.getLong(1);
			//dSumAmount = rs.getDouble(2);
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
		
//		查询记录
		String strSQLRecord = "select * from  ( select aa.*,rownum r from ( " + m_sbSQL.toString();
		long lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
		long lRowNumEnd = lRowNumStart + lPageLineCount - 1;
		strSQLRecord = strSQLRecord + " ) aa ) where r between " + lRowNumStart + " and  " + lRowNumEnd;
		ps = conn.prepareStatement(strSQLRecord);

		rs = ps.executeQuery();
//		取得期初余额
		double dBalance = 0.0;
		dBalance=getGLBalance(lOfficeID,lCurrencyID,printGLInfo);
		Log.print("科目的期初金额："+dBalance);
//		取得期初余额结束
		Timestamp tsLastDate = null; //每日合计 用
		double dDayPayBalance = 0.0; //每日付款合计
		double dDayReceiveBalance = 0.0; //每日收款合计
		int derection=-1;
		boolean mark=true;
		while(rs.next())
		{
			if (tsLastDate != null && rs.getTimestamp("dtexecute1") != null && !DataFormat.formatDate(tsLastDate).equals(DataFormat.formatDate(rs.getTimestamp("dtexecute1"))))
			{
				PrintGLInfo resultInfo2 = new PrintGLInfo();
				resultInfo2.setTransNo("&nbsp");
				resultInfo2.setAbstract("<b>本日合计</b>");
				resultInfo2.setExecuteMonth(Long.valueOf(DataFormat.getMonthString(tsLastDate)).longValue() + 1);
				resultInfo2.setExecuteYear(Long.valueOf(DataFormat.getYearString(tsLastDate)).longValue());
				resultInfo2.setExecuteDate(tsLastDate);
				resultInfo2.setTransTypeID(-1000); //表示为日合计
				resultInfo2.setPayAmount(dDayPayBalance);
				resultInfo2.setReceiveAmount(dDayReceiveBalance);
				resultInfo2.setBalance(dBalance>0?dBalance:-dBalance);
				v.add(resultInfo2);
				
				dDayPayBalance = 0.0;
				dDayReceiveBalance = 0.0;
			}
			tsLastDate = rs.getTimestamp("dtexecute1");

			PrintGLInfo resultInfo = new PrintGLInfo();
			resultInfo.setTransNo(rs.getString("stransno"));
			resultInfo.setLPageCount(lPageCount) ;
			resultInfo.setExecuteDate(rs.getTimestamp("dtexecute1"));
			resultInfo.setExecuteDay(rs.getLong("ExecuteDay"));
			resultInfo.setExecuteMonth(rs.getLong("ExecuteMonth"));
			resultInfo.setExecuteYear(rs.getLong("ExecuteYear"));
			resultInfo.setTransTypeID(rs.getLong("TransTypeID"));
			resultInfo.setAbstract(rs.getString("SABSTRACT"));
			resultInfo.setBillNo(rs.getString("SBILLNO"));
			resultInfo.setBankChequeNo(rs.getString("SBANKCHEQUENO"));
			derection=rs.getInt("NTRANSDIRECTION1");
			if(derection==SETTConstant.ControlDirection.DEBIT)
				resultInfo.setPayAmount(rs.getDouble("mamount1"));
			if(derection==SETTConstant.ControlDirection.CREDIT)
			resultInfo.setReceiveAmount(rs.getDouble("mamount1"));
			resultInfo.setNsubjecttype(rs.getLong("nsubjecttype"));
			
			if(resultInfo.getNsubjecttype()!=SETTConstant.SubjectAttribute.ASSET )
			{
				dBalance = -dBalance ;
				mark=false;
			}
			//资产类
			if(resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.ASSET  || resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.PAYOUT)
			{
				dBalance = dBalance + DataFormat.formatDouble(resultInfo.getPayAmount(), 2) - DataFormat.formatDouble(resultInfo.getReceiveAmount(), 2);
			}
			//负债类
			if(resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.RIGHT ||resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.INCOME ||resultInfo.getNsubjecttype()==SETTConstant.SubjectAttribute.DEBT)
			{
				dBalance = dBalance - DataFormat.formatDouble(resultInfo.getPayAmount(), 2) + DataFormat.formatDouble(resultInfo.getReceiveAmount(), 2);
				dBalance = -dBalance ;
			}
			dDayPayBalance = dDayPayBalance + DataFormat.formatDouble(resultInfo.getPayAmount(), 2);
			dDayReceiveBalance = dDayReceiveBalance + DataFormat.formatDouble(resultInfo.getReceiveAmount(), 2);
			resultInfo.setBalance(dBalance>0?dBalance:-dBalance);
			v.add(resultInfo);
		}
		PrintGLInfo resultInfo3 = new PrintGLInfo();
		resultInfo3.setTransNo("&nbsp");
		resultInfo3.setAbstract("<b>本日合计</b>");
		resultInfo3.setExecuteDate(tsLastDate);
		if (tsLastDate != null)
		{
			long lExecuteMonth = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(5, 7)).longValue();
			long lExecuteYear = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(0, 4)).longValue();
			resultInfo3.setExecuteMonth(lExecuteMonth);
			resultInfo3.setExecuteYear(lExecuteYear);
			Log.print("lExecuteMonth" + lExecuteMonth);
			Log.print("setExecuteYear" + lExecuteYear);
			Log.print("tsLastDate" + tsLastDate);
		}
		resultInfo3.setTransTypeID(-1000); //表示为日合计
		resultInfo3.setPayAmount(dDayPayBalance);
		resultInfo3.setReceiveAmount(dDayReceiveBalance);
		resultInfo3.setBalance(dBalance>0?dBalance:-dBalance);
		Log.print("本日合计");
		v.add(resultInfo3);
		
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
	finally
	{
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}
	return v;
}


	//modify by leiyang 修改得到期初余额，可被父科目使用
	public double getGLBalance(long lOfficeID,long lCurrencyID,PrintGLInfo printGLInfo) throws Exception
	{
		double dReturn = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			/*m_sbSQL.append("select t.MDEBITBALANCE Balance,t.MCREDITBALANCE Balance1,t.NBALANCEDIRECTION from sett_glbalance t where t.nofficeid="+lOfficeID+" and t.ncurrencyid="+lCurrencyID);
			m_sbSQL.append(" and t.sglsubjectcode='"+printGLInfo.getSsubjectcode()+"'");
			m_sbSQL.append(" and t.DTGLDATE=to_date('"+ DataFormat.formatDate(DataFormat.getPreviousDate(printGLInfo.getStartDate())) + "','yyyy-mm-dd') \n");*/
			m_sbSQL.append("select sum(t1.MDEBITBALANCE) Balance,sum(t1.MCREDITBALANCE) Balance1,avg(t1.NBALANCEDIRECTION) NBALANCEDIRECTION  ");
			m_sbSQL.append(" from sett_glbalance t1,(select t.* from sett_glsubjectdefinition t");
			m_sbSQL.append(" where t.nofficeid=" + lOfficeID);
			m_sbSQL.append(" and t.ncurrencyid=" + lCurrencyID);
			m_sbSQL.append(" start with t.Id ="+ printGLInfo.getId() +"  connect by prior t.Id = t.Nparentsubjectid) t2");
			m_sbSQL.append(" where t1.sglsubjectcode(+) = t2.ssegmentcode2");
			m_sbSQL.append(" and t1.DTGLDATE=to_date('"+ DataFormat.formatDate(DataFormat.getPreviousDate(printGLInfo.getStartDate())) + "','yyyy-mm-dd') \n");
			m_sbSQL.append(" and t1.nofficeid = " + lOfficeID + "  and t1.ncurrencyid = " + lCurrencyID ) ;
			
			Log.print("取得科目期初余额SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				if(rs.getInt("NBALANCEDIRECTION")==SETTConstant.ControlDirection.DEBIT) dReturn = rs.getDouble("Balance");
			    if(rs.getInt("NBALANCEDIRECTION")==SETTConstant.ControlDirection.CREDIT)  dReturn = rs.getDouble("Balance1");
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return dReturn;
	}
	
//	/* Create 2008-12-25
//	 * Modify 2010-11-05 Boxu 增加多选查询条件
//	 * 日结科目汇总查询
//	 * 查询条件使用AccountRecordConditionInfo Bean
//	 * 日结会计账汇总(查询日结 分页)
//	 * @param conditionInfo
//	 */
//	public PageLoader findDailyAccountRecord(AccountRecordConditionInfo conditionInfo)
//		throws ITreasuryDAOException
//	{
//		PageLoader pageLoader = null;
//		StringBuffer sbSelect = null;
//		StringBuffer sbFrom = null;
//		StringBuffer sbWhere = null;
//		String strSQLRecord = "";
//		String allSub = conditionInfo.getAllSub();
//        try {
//        	int _compareDate = conditionInfo.getTsDateStart().compareTo(Env.getSystemDate(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId()));
//
//	        sbSelect = new StringBuffer();
//	        sbFrom = new StringBuffer();
//	        sbWhere = new StringBuffer();
//			sbSelect.append(" * \n");
//			
//			sbFrom.append(" ( \n");
//			sbFrom.append("  select a.id, \n");
//			sbFrom.append("         a.ssegmentcode2 m_strAccount, \n");
//			sbFrom.append("         a.ssegmentname2 m_strName, \n");
//			sbFrom.append("         a.nSubjectType m_lSubjectType, \n");
//			sbFrom.append("         a.nparentsubjectid nParentSubjectId, \n");
//			sbFrom.append("         a.nBalanceDirection, \n");
//			sbFrom.append("         decode(a.nBalanceDirection, "+SETTConstant.DebitOrCredit.DEBIT+", sum(nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0)), "+SETTConstant.DebitOrCredit.CREDIT+", -sum(nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0))) m_dStartBalance \n");
//			sbFrom.append("  from \n");
//			
//			//Modify 2010-11-05 Boxu 增加多选查询条件
//			sbFrom.append(" ( select * from sett_glsubjectdefinition aa where 1=1 \n");
//			if(!allSub.equals("")){
//				if(allSub.indexOf(",")==-1){
//					sbFrom.append( " and aa.ssegmentcode2 like '%" + allSub + "%' \n");
//				}else{
//					String []sub = allSub.split(",");
//					sbFrom.append( " and ( ");
//					for(int i  = 0 ; i < sub.length;i++){
//						if(i!=sub.length-1){
//							sbFrom.append( "  aa.ssegmentcode2 like '%" + sub[i] + "%' or ");
//			             }else{
//			            	 sbFrom.append( "  aa.ssegmentcode2 like '%" + sub[i] + "%' ) ");
//			             }    
//					}
//				}
//			}
//			sbFrom.append(" ) a, \n");
//			
//			sbFrom.append("       (select * from Sett_GlBalance \n");
//			//判断是否是今天查询
//			if(_compareDate == 0 && CloseSystemMain.getSystemStatusID(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId(), Constant.ModuleType.SETTLEMENT) != Constant.SystemStatus.CLOSE){
//				sbFrom.append("     where dtgldate = to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
//			}
//			else {
//				sbFrom.append("     where dtgldate = to_date('"+DataFormat.getDateString(DataFormat.getPreviousDate(conditionInfo.getTsDateStart()))+"','yyyy-mm-dd') \n");
//			}
//			
//			//Modify 2010-11-05 Boxu 增加多选查询条件
//			if(!allSub.equals("")){
//				if(allSub.indexOf(",")==-1){
//					sbFrom.append( " and sGlSubjectCode like '%" + allSub + "%' \n");
//				}else{
//					String []sub = allSub.split(",");
//					sbFrom.append( " and ( ");
//					for(int i  = 0 ; i < sub.length;i++){
//						if(i!=sub.length-1){
//							sbFrom.append( "  sGlSubjectCode like '%" + sub[i] + "%' or ");
//			             }else{
//			            	 sbFrom.append( "  sGlSubjectCode like '%" + sub[i] + "%' ) ");
//			             }
//					}
//				}
//			}
//			
//			sbFrom.append("       ) b \n");
//			//增加是否虑空条件 虑空 必须是借方和贷方都为0
//			if(conditionInfo.getIflv()>0){
//				sbFrom.append("       , \n");
//				
//				sbFrom.append("  (select sSubjectCode,count(sTransNO) mNumber from Sett_GlEntry  \n");
//				sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
//				sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
//				sbFrom.append("  and nStatusID>0 \n");
//				if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
//					sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
//				}
//				if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
//					sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
//				}
//				
//				//Modify 2010-11-05 Boxu 增加多选查询条件
//				if(!allSub.equals("")){
//					if(allSub.indexOf(",")==-1){
//						sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
//					}else{
//						String []sub = allSub.split(",");
//						sbFrom.append( " and ( ");
//						for(int i  = 0 ; i < sub.length;i++){
//							if(i!=sub.length-1){
//								sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
//				             }else{
//				            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
//				             }
//						}
//					}
//				}
//				
//				sbFrom.append("  group by sSubjectCode ) c \n");
//			}
//			
//			sbFrom.append(" where a.ssegmentcode2 = b.sglsubjectcode(+) \n");
//			
//			if(conditionInfo.getIflv()>0){
//				sbFrom.append("  and a.ssegmentcode2 = c.sSubjectCode(+) \n");
//				sbFrom.append("  and c.mNumber>0  \n");
//			}
//			sbFrom.append("  and a.nOfficeID = "+conditionInfo.getOfficeId()+" \n");
//			sbFrom.append("  and a.nCurrencyID = "+conditionInfo.getCurrencyId()+" \n");
//			sbFrom.append("  group by a.id, a.ssegmentcode2, a.ssegmentname2, a.nSubjectType, a.nparentsubjectid, a.nBalanceDirection \n");
//			sbFrom.append(" ) \n");
//			
//			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
//			pageLoader.initPageLoader(
//				new AppContext(),
//				sbFrom.toString(),
//				sbSelect.toString(),
//				sbWhere.toString(),
//				(int) conditionInfo.getLPageCount(),
//				"com.iss.itreasury.settlement.query.resultinfo.AccountRecordInfo",
//				null);
//			
//			switch((int)conditionInfo.getLOrderParam()){
//				case 1 :
//					strSQLRecord =  " order by m_strAccount ";
//					break;
//				case 2 :
//					strSQLRecord =  " order by m_strName ";
//					break;
//				case 3 :
//					strSQLRecord =  " order by m_dStartBalance ";
//					break;
//				default :
//					strSQLRecord =  " order by m_strAccount ";
//					break;
//			}
//			if(conditionInfo.getLDesc() == Constant.PageControl.CODE_ASCORDESC_DESC){
//				strSQLRecord += " desc";
//			}
//			pageLoader.setOrderBy(strSQLRecord);
//        }
//        catch (Exception e) {
//        	e.printStackTrace();
//	        throw new ITreasuryDAOException("查询异常", e);
//	    }
//		return pageLoader;
//	}
	
	/* Create 2008-12-25
	 * Modify 2010-11-05 Boxu 增加多选查询条件
	 * 目前发现滤空会出现一个问题，只能显示发生交易的科目，而不能显示其上级科目的信息，应武钢客户要求修改，目前只想到
	 * 下面的实现方式，实现比较杂乱，如有更好的方法请修改
	 * 日结科目汇总查询
	 * 查询条件使用AccountRecordConditionInfo Bean
	 * 日结会计账汇总(查询日结 分页)
	 * @param conditionInfo
	 */
	public PageLoader findDailyAccountRecord(AccountRecordConditionInfo conditionInfo)
		throws ITreasuryDAOException
	{
		PageLoader pageLoader = null;
		StringBuffer sbSelect = null;
		StringBuffer sbFrom = null;
		StringBuffer sbWhere = null;
		String strSQLRecord = "";
		String allSub = conditionInfo.getAllSub();
        try {
        	int _compareDate = conditionInfo.getTsDateStart().compareTo(Env.getSystemDate(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId()));

	        sbSelect = new StringBuffer();
	        sbFrom = new StringBuffer();
	        sbWhere = new StringBuffer();
			sbSelect.append(" * \n");
			
			sbFrom.append(" ( \n");
			sbFrom.append("  select a.id, \n");
			sbFrom.append("         a.sSubjectCode m_strAccount, \n");
			sbFrom.append("         a.ssegmentname2 m_strName, \n");
			sbFrom.append("         a.nSubjectType m_lSubjectType, \n");
			sbFrom.append("         a.nparentsubjectid nParentSubjectId, \n");
			sbFrom.append("         a.nBalanceDirection, \n");
			sbFrom.append("         decode(a.nBalanceDirection, "+SETTConstant.DebitOrCredit.DEBIT+", sum(nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0)), "+SETTConstant.DebitOrCredit.CREDIT+", -sum(nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0))) m_dStartBalance \n");
			sbFrom.append("  from \n");
			
			//Modify 2010-11-05 Boxu 增加多选查询条件
			sbFrom.append(" ( select * from sett_vglsubjectdefinition aa where 1=1 \n");//Maxd_2012-06-05 14:08:01 sett_glsubjectdefinition替换为sett_vglsubjectdefinition 
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and aa.sSubjectCode like '%" + allSub + "%' \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  aa.sSubjectCode like '%" + sub[i] + "%' or ");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
			             }else{
			            	 sbFrom.append( "  aa.sSubjectCode like '%" + sub[i] + "%' ) ");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
			             }    
					}
				}
			}
			sbFrom.append(" ) a, \n");
			
			sbFrom.append("       (select * from Sett_GlBalance \n");
			//判断是否是今天查询
			if(_compareDate == 0 && CloseSystemMain.getSystemStatusID(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId(), Constant.ModuleType.SETTLEMENT) != Constant.SystemStatus.CLOSE){
				sbFrom.append("     where dtgldate = to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			}
			else {
				sbFrom.append("     where dtgldate = to_date('"+DataFormat.getDateString(DataFormat.getPreviousDate(conditionInfo.getTsDateStart()))+"','yyyy-mm-dd') \n");
			}
			
			//Modify 2010-11-05 Boxu 增加多选查询条件
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and sGlSubjectCode like '%" + allSub + "%' \n");
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  sGlSubjectCode like '%" + sub[i] + "%' or ");
			             }else{
			            	 sbFrom.append( "  sGlSubjectCode like '%" + sub[i] + "%' ) ");
			             }
					}
				}
			}
			
			sbFrom.append("       ) b \n");
			
			//增加是否虑空条件
			if(conditionInfo.getIflv() > 0)
			{
				sbFrom.append("       , \n");
				sbFrom.append(" ( select distinct sSubjectCode from ( ");
				
				sbFrom.append(" SELECT sSubjectCode1 sSubjectCode from ( ");
				sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
				sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
				sbFrom.append("  from Sett_GlEntry \n");
				sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
				sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
				sbFrom.append("  and nStatusID>0 \n");
				if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
					sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
				}
				if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
					sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
				}
				
				if(!allSub.equals("")){
					if(allSub.indexOf(",")==-1){
						sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
					}else{
						String []sub = allSub.split(",");
						sbFrom.append( " and ( ");
						for(int i  = 0 ; i < sub.length;i++){
							if(i!=sub.length-1){
								sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
				             }else{
				            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
				             }
						}
					}
				}
				sbFrom.append("  group by sSubjectCode ) \n");
				
				sbFrom.append("  union all \n");
				
				sbFrom.append(" SELECT sSubjectCode2 sSubjectCode from ( ");
				sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
				sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
				sbFrom.append("  from Sett_GlEntry \n");
				sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
				sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
				sbFrom.append("  and nStatusID>0 \n");
				if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
					sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
				}
				if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
					sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
				}
				
				if(!allSub.equals("")){
					if(allSub.indexOf(",")==-1){
						sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
					}else{
						String []sub = allSub.split(",");
						sbFrom.append( " and ( ");
						for(int i  = 0 ; i < sub.length;i++){
							if(i!=sub.length-1){
								sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
				             }else{
				            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
				             }
						}
					}
				}
				sbFrom.append("  group by sSubjectCode ) \n");
				
				sbFrom.append("  union all \n");
				
				sbFrom.append(" SELECT sSubjectCode3 sSubjectCode from ( ");
				sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
				sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
				sbFrom.append("  from Sett_GlEntry \n");
				sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
				sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
				sbFrom.append("  and nStatusID>0 \n");
				if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
					sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
				}
				if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
					sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
				}
				
				if(!allSub.equals("")){
					if(allSub.indexOf(",")==-1){
						sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
					}else{
						String []sub = allSub.split(",");
						sbFrom.append( " and ( ");
						for(int i  = 0 ; i < sub.length;i++){
							if(i!=sub.length-1){
								sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
				             }else{
				            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
				             }
						}
					}
				}
				sbFrom.append("  group by sSubjectCode ) \n");
				
				sbFrom.append("  union all \n");
				
				sbFrom.append(" SELECT sSubjectCode4 sSubjectCode from ( ");
				sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
				sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
				sbFrom.append("  from Sett_GlEntry \n");
				sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
				sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
				sbFrom.append("  and nStatusID>0 \n");
				if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
					sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
				}
				if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
					sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
				}
				
				if(!allSub.equals("")){
					if(allSub.indexOf(",")==-1){
						sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
					}else{
						String []sub = allSub.split(",");
						sbFrom.append( " and ( ");
						for(int i  = 0 ; i < sub.length;i++){
							if(i!=sub.length-1){
								sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
				             }else{
				            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
				             }
						}
					}
				}
				sbFrom.append("  group by sSubjectCode ) \n");
				
				sbFrom.append("  union all \n");
				
				sbFrom.append(" SELECT sSubjectCode5 sSubjectCode from ( ");
				sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
				sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
				sbFrom.append("  from Sett_GlEntry \n");
				sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
				sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
				sbFrom.append("  and nStatusID>0 \n");
				if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
					sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
				}
				if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
					sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
				}
				
				if(!allSub.equals("")){
					if(allSub.indexOf(",")==-1){
						sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
					}else{
						String []sub = allSub.split(",");
						sbFrom.append( " and ( ");
						for(int i  = 0 ; i < sub.length;i++){
							if(i!=sub.length-1){
								sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
				             }else{
				            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
				             }
						}
					}
				}
				sbFrom.append("  group by sSubjectCode ) \n");
				
				sbFrom.append("  union all \n");
				
				sbFrom.append(" SELECT sSubjectCode6 sSubjectCode from ( ");
				sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
				sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
				sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
				sbFrom.append("  from Sett_GlEntry \n");
				sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
				sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
				sbFrom.append("  and nStatusID>0 \n");
				if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
					sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
				}
				if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
					sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
				}
				
				if(!allSub.equals("")){
					if(allSub.indexOf(",")==-1){
						sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
					}else{
						String []sub = allSub.split(",");
						sbFrom.append( " and ( ");
						for(int i  = 0 ; i < sub.length;i++){
							if(i!=sub.length-1){
								sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
				             }else{
				            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
				             }
						}
					}
				}
				sbFrom.append("  group by sSubjectCode ) \n");
				
				sbFrom.append("  ) ) c \n");
			}
			
			sbFrom.append(" where a.sSubjectCode = b.sglsubjectcode(+) \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
			
			if(conditionInfo.getIflv()>0){
				sbFrom.append("  and a.sSubjectCode = c.sSubjectCode \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
			}
			
			sbFrom.append("  and a.nOfficeID = "+conditionInfo.getOfficeId()+" \n");
			sbFrom.append("  and a.nCurrencyID = "+conditionInfo.getCurrencyId()+" \n");
			sbFrom.append("  group by a.id, a.sSubjectCode, a.ssegmentname2, a.nSubjectType, a.nparentsubjectid, a.nBalanceDirection \n");
			sbFrom.append(" ) \n");
			
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) conditionInfo.getLPageCount(),
				"com.iss.itreasury.settlement.query.resultinfo.AccountRecordInfo",
				null);
			
			switch((int)conditionInfo.getLOrderParam()){
				case 1 :
					strSQLRecord =  " order by m_strAccount ";
					break;
				case 2 :
					strSQLRecord =  " order by m_strName ";
					break;
				case 3 :
					strSQLRecord =  " order by m_dStartBalance ";
					break;
				default :
					strSQLRecord =  " order by m_strAccount ";
					break;
			}
			if(conditionInfo.getLDesc() == Constant.PageControl.CODE_ASCORDESC_DESC){
				strSQLRecord += " desc";
			}
			pageLoader.setOrderBy(strSQLRecord);
        }
        catch (Exception e) {
        	e.printStackTrace();
	        throw new ITreasuryDAOException("查询异常", e);
	    }
		return pageLoader;
	}
	
	/**
	 * Create 2008-12-25
	 * 日结科目汇总查询
	 * 查询条件使用AccountRecordConditionInfo Bean
	 * 日结科目汇总查询，不用进行再次科目明细查询
	 * @param conditionInfo
	 */
	public Collection findDailyAccountRecordCollection(AccountRecordConditionInfo conditionInfo)
		throws ITreasuryDAOException
	{
		StringBuffer sbSQL = null;
		StringBuffer sbGlEntrySQL = null; 
		Vector v = null;
		
		try
		{
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/
			
	        try {
	        	int _compareDate = conditionInfo.getTsDateStart().compareTo(Env.getSystemDate(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId()));
	        	sbGlEntrySQL = new StringBuffer();
	        	sbGlEntrySQL.append(" select glsubject.id,  \n");
	        	sbGlEntrySQL.append("        glsubject.ssegmentcode2, \n");
	        	sbGlEntrySQL.append("        sum(glentry.mAmount) mAmount, \n");
	        	sbGlEntrySQL.append("        sum(glentry.mNumber) mNumber \n");
	        	sbGlEntrySQL.append(" from \n");
	        	sbGlEntrySQL.append("   ( \n");
	        	sbGlEntrySQL.append("    select a.id, a.ssegmentcode2, MAX(a.ssegmentcode2) OVER(order by rownum) AS startCode \n");
	        	sbGlEntrySQL.append("    from sett_glsubjectdefinition a \n");
	        	sbGlEntrySQL.append("    where a.nOfficeID = "+conditionInfo.getOfficeId()+" \n");
	        	sbGlEntrySQL.append("    and a.nCurrencyID = "+conditionInfo.getCurrencyId()+" \n");	        	
	        	sbGlEntrySQL.append("    start with a.ssegmentcode2 in \n");
	        	sbGlEntrySQL.append("     ( \n");
	        	sbGlEntrySQL.append("       select t.sSubjectCode from Sett_GlEntry t \n");
	        	sbGlEntrySQL.append("       where t.nOfficeID = "+conditionInfo.getOfficeId()+" \n");
	        	sbGlEntrySQL.append("       and t.nCurrencyID = "+conditionInfo.getCurrencyId()+" \n");
	        	if (!conditionInfo.getSubjectCodeStart().equals(""))
	        	{
	        		sbGlEntrySQL.append( " and t.sSubjectCode>='" + conditionInfo.getSubjectCodeStart() + "' \n");
	        	} 
	        	if (!conditionInfo.getSubjectCodeEnd().equals(""))
	        	{
	        		sbGlEntrySQL.append( " and t.sSubjectCode<='" + conditionInfo.getSubjectCodeEnd() + "' \n");
	        	}
	        	sbGlEntrySQL.append("       and t.nStatusID > 0 \n");
	        	sbGlEntrySQL.append("       and t.nTransDirection = ? \n");
	        	sbGlEntrySQL.append("       and t.dtExecute between to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"', 'yyyy-mm-dd') and to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"', 'yyyy-mm-dd') \n");
	        	sbGlEntrySQL.append("       group by t.sSubjectCode \n");
	        	sbGlEntrySQL.append("     ) \n");
	        	sbGlEntrySQL.append("    connect by prior a.nparentsubjectid = a.id \n");
	        	sbGlEntrySQL.append("   ) glsubject,");
	        	sbGlEntrySQL.append("   ( \n");
	        	sbGlEntrySQL.append("    select t.sSubjectCode, sum(t.mamount) mAmount, count(t.sTransNO) mNumber from Sett_GlEntry t \n");
	        	sbGlEntrySQL.append("    where t.nOfficeID = "+conditionInfo.getOfficeId()+" \n");
	        	sbGlEntrySQL.append("    and t.nCurrencyID = "+conditionInfo.getCurrencyId()+" \n");
	        	sbGlEntrySQL.append("    and t.nStatusID > 0 \n");
	        	sbGlEntrySQL.append("    and t.nTransDirection = ? \n");
	        	sbGlEntrySQL.append("    and t.dtExecute between to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"', 'yyyy-mm-dd') and to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"', 'yyyy-mm-dd') \n");
	        	sbGlEntrySQL.append("    group by t.sSubjectCode \n");
	        	sbGlEntrySQL.append("   ) glentry \n");
	        	sbGlEntrySQL.append(" where glsubject.startCode = glentry.ssubjectcode(+) \n");
	        	sbGlEntrySQL.append(" group by glsubject.id, glsubject.ssegmentcode2 \n");

				sbSQL = new StringBuffer();
				sbSQL.append("select SubjectBalance.id, \n");
				sbSQL.append("       SubjectBalance.ssegmentcode2 subjectCode, \n");
				sbSQL.append("       SubjectBalance.ssegmentname2 subjectName, \n");
				sbSQL.append("       SubjectBalance.nSubjectType subjectType, \n");
				sbSQL.append("       SubjectBalance.nBalanceDirection subjectBalanceDirection, \n");
				sbSQL.append("       SubjectBalance.mStartAmount, \n");
				sbSQL.append("       nvl(DebitSett_GlEntry.mAmount, 0) mDebitAmount, \n");
				sbSQL.append("       nvl(DebitSett_GlEntry.mNumber, 0) mDebitNumber, \n");
				sbSQL.append("       nvl(LoanSett_GlEntry.mAmount, 0) mLoanAmount,  \n");
				sbSQL.append("       nvl(LoanSett_GlEntry.mNumber, 0) mLoanNumber, \n");
				//sbSQL.append("       decode(SubjectBalance.nSubjectType, 1, SubjectBalance.mStartAmount+nvl(DebitSett_GlEntry.mAmount, 0)-nvl(LoanSett_GlEntry.mAmount, 0), 5, SubjectBalance.mStartAmount+nvl(DebitSett_GlEntry.mAmount, 0)-nvl(LoanSett_GlEntry.mAmount, 0), 2, SubjectBalance.mStartAmount+nvl(LoanSett_GlEntry.mAmount, 0)-nvl(DebitSett_GlEntry.mAmount, 0), 3, SubjectBalance.mStartAmount+nvl(LoanSett_GlEntry.mAmount, 0)-nvl(DebitSett_GlEntry.mAmount, 0), 4, SubjectBalance.mStartAmount+nvl(LoanSett_GlEntry.mAmount, 0)-nvl(DebitSett_GlEntry.mAmount, 0)) mEndAmount \n");
				sbSQL.append("       decode(SubjectBalance.nSubjectType, "+SETTConstant.SubjectAttribute.ASSET+", SubjectBalance.mStartAmount+nvl(DebitSett_GlEntry.mAmount, 0)-nvl(LoanSett_GlEntry.mAmount, 0), "+SETTConstant.SubjectAttribute.PAYOUT+", SubjectBalance.mStartAmount+nvl(DebitSett_GlEntry.mAmount, 0)-nvl(LoanSett_GlEntry.mAmount, 0), "+SETTConstant.SubjectAttribute.DEBT+", SubjectBalance.mStartAmount+nvl(LoanSett_GlEntry.mAmount, 0)-nvl(DebitSett_GlEntry.mAmount, 0), "+SETTConstant.SubjectAttribute.RIGHT+", SubjectBalance.mStartAmount+nvl(LoanSett_GlEntry.mAmount, 0)-nvl(DebitSett_GlEntry.mAmount, 0), "+SETTConstant.SubjectAttribute.INCOME+", SubjectBalance.mStartAmount+nvl(LoanSett_GlEntry.mAmount, 0)-nvl(DebitSett_GlEntry.mAmount, 0)) mEndAmount \n");
				sbSQL.append("from \n");
				//SubjectBalance
				sbSQL.append("( \n");
				sbSQL.append("  select a.id, a.ssegmentcode2, a.ssegmentname2, a.nSubjectType, a.nparentsubjectid, a.nBalanceDirection, decode(a.nBalanceDirection, "+SETTConstant.DebitOrCredit.DEBIT+", nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0), "+SETTConstant.DebitOrCredit.CREDIT+", -nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0)) mStartAmount \n");
				sbSQL.append("  from sett_glsubjectdefinition a, \n");
				sbSQL.append("       (select * from Sett_GlBalance \n");
				//判断是否是今天查询
				if(_compareDate == 0 && CloseSystemMain.getSystemStatusID(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId(), Constant.ModuleType.SETTLEMENT) != Constant.SystemStatus.CLOSE){
					sbSQL.append("     where dtgldate = to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
				}
				else {
					sbSQL.append("     where dtgldate = to_date('"+DataFormat.getDateString(DataFormat.getPreviousDate(conditionInfo.getTsDateStart()))+"','yyyy-mm-dd') \n");
				}
				sbSQL.append("       ) b \n");
				sbSQL.append("  where a.ssegmentcode2 = b.sglsubjectcode(+) \n");
				sbSQL.append("  and a.nOfficeID = "+ conditionInfo.getOfficeId() +" \n");
				sbSQL.append("  and a.nCurrencyID = "+ conditionInfo.getCurrencyId() +" \n");
				//sbSQL.append("  and b.dtgldate = to_date('2008-12-02','yyyy-mm-dd') \n");
				sbSQL.append(") SubjectBalance, \n");
				//DebitSett_GlEntry
				sbSQL.append("( \n");
				sbSQL.append(sbGlEntrySQL.toString());
				sbSQL.append(") DebitSett_GlEntry, \n");
				//LoanSett_GlEntry
				sbSQL.append("( \n");
				sbSQL.append(sbGlEntrySQL.toString());
				sbSQL.append(") LoanSett_GlEntry \n");
				sbSQL.append("where SubjectBalance.id = DebitSett_GlEntry.id(+) \n");
				sbSQL.append("and SubjectBalance.id = LoanSett_GlEntry.id(+) \n");
				if(Config.getBoolean(ConfigConstant.SETTMENT_DAILYGLQUERY_ISLEACHING,true)||conditionInfo.getIflv()>0)
				{
					sbSQL.append(" and (DebitSett_GlEntry.mNumber <>0 or LoanSett_GlEntry.mNumber <>0) \n");
				}
				
				//Order by 
				sbSQL.append("order by SubjectBalance.ssegmentcode2 \n");
				
				prepareStatement(sbSQL.toString());
				int index = 1;
				transPS.setLong(index++, SETTConstant.DebitOrCredit.DEBIT);
				transPS.setLong(index++, SETTConstant.DebitOrCredit.DEBIT);
				transPS.setLong(index++, SETTConstant.DebitOrCredit.CREDIT);
				transPS.setLong(index++, SETTConstant.DebitOrCredit.CREDIT);
				
				log4j.info("OBFinanceInstrDAO.findDailyAccountRecordCollection()\n");
				log4j.info("sbSQL=\n" + sbSQL.toString());
				
				executeQuery();
				
				v = new Vector();
				while(transRS.next())
				{
					AccountRecordInfo accountRecordInfo = new AccountRecordInfo();
					accountRecordInfo.setId(transRS.getLong("id"));  //科目ID
					accountRecordInfo.setM_strAccount(transRS.getString("subjectCode")); //科目号
					accountRecordInfo.setM_strName(transRS.getString("subjectName")); //科目名称
					accountRecordInfo.setM_lSubjectType(transRS.getDouble("subjectType")); //科目类型
					accountRecordInfo.setM_dStartBalance(transRS.getDouble("mStartAmount")); //期初金额
					accountRecordInfo.setM_dDebitAmount(transRS.getDouble("mDebitAmount")); //借方合计金额
					accountRecordInfo.setM_lDebitNumber(transRS.getLong("mDebitNumber")); //借方合计数量
					accountRecordInfo.setM_dLoanAmount(transRS.getDouble("mLoanAmount")); //贷方合计金额
					accountRecordInfo.setM_lCreditNumber(transRS.getLong("mLoanNumber")); //贷方合计数量
					accountRecordInfo.setM_dEndBalance(transRS.getDouble("mEndAmount")); //期末金额
					
					//accountRecordInfo.m_dDebitsSumAmount = dDebitsSumAmount; //满足条件全部借方合计,（上海电气只取表内的借方合计）
					//accountRecordInfo.m_dCreditSumAmount = dCreditSumAmount; //满足条件全部贷方合计，（上海电气只取表内的贷方合计）
					//accountRecordInfo.m_dOffDebitSumAmount = dOffDebitSumAmount;//表外业务借方合计
					//accountRecordInfo.m_dOffCreditSumAmount = dOffCreditSumAmount;//表外业务借方合计
					//accountRecordInfo.m_lNumber = accountRecordInfo.m_lDebitNumber + accountRecordInfo.m_lCreditNumber; //凭证数量
					
					//accountRecordInfo.m_lPageCount = 1;
					v.add(accountRecordInfo);
				}
	        }
	        catch (Exception e) {
		        throw new ITreasuryDAOException("查询异常", e);
		    }
			
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ITreasuryDAOException("查询异常",e);
        }
        finally {
        	finalizeDAO();
        }
		return (v.size() > 0 ? v : null);
	}
	
	/**
	 * 日结科目汇总查询(查询表内表外汇总)
	 * 查询条件使用AccountRecordConditionInfo Bean
	 * @param conditionInfo
	 */
	public AccountRecordInfo findDailyAccountRecordCount(AccountRecordConditionInfo conditionInfo)
		throws ITreasuryDAOException
	{
		StringBuffer sbSQL = null;
		AccountRecordInfo accountRecordInfo = null;
		try
		{
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/
	        
	        try {
	        	sbSQL = new StringBuffer();
				sbSQL.append(" select nvl(sum(decode(b.nSubjectType, " + SETTConstant.SubjectAttribute.TABLEOUT + ", 0.00, a.MAMOUNT)),0.0) SumAmount,");
				sbSQL.append("        nvl(sum(decode(b.nSubjectType, " + SETTConstant.SubjectAttribute.TABLEOUT + ", a.MAMOUNT)),0.0) OffSumAmount,");
				sbSQL.append("        a.NTransDirection \n");
				sbSQL.append(" from sett_GLENTRY a, sett_vglsubjectdefinition b \n"); //凡是查询用到Sett_GlSubjectDefinition的，都改成视图sett_vglsubjectdefinition update by hitler 2012-06-12
				sbSQL.append(" where a.nOfficeID = ?");
				sbSQL.append(" and a.nCurrencyID = ?");
				sbSQL.append(" and b.nofficeid = "+conditionInfo.getOfficeId()+" ");
				sbSQL.append(" and b.ncurrencyid = "+conditionInfo.getCurrencyId()+" ");
				sbSQL.append(" and a.sSubjectCode = b.sSubjectCode");
				sbSQL.append(" and a.dtExecute between ? and ?");
				sbSQL.append(" and a.nStatusID > 0");
				sbSQL.append(" group by a.NTransDirection");
				
				prepareStatement(sbSQL.toString());
				int nIndex = 1;

				transPS.setLong(nIndex++, conditionInfo.getOfficeId());
				transPS.setLong(nIndex++, conditionInfo.getCurrencyId());
				transPS.setTimestamp(nIndex++, conditionInfo.getTsDateStart());
				transPS.setTimestamp(nIndex++, conditionInfo.getTsDateEnd());
				
				log4j.info("OBFinanceInstrDAO.findDailyAccountRecordCount()\n");
				log4j.info("sbSQL=\n" + sbSQL.toString());

				executeQuery();
				
				long lTransDirection = 0;
				accountRecordInfo = new AccountRecordInfo();
				while(transRS.next())
				{
					lTransDirection = transRS.getLong("NTransDirection");
					if(lTransDirection == SETTConstant.DebitOrCredit.DEBIT) {
						accountRecordInfo.setM_dDebitsSumAmount(transRS.getDouble("SumAmount")); //表内业务借方合计
						accountRecordInfo.setM_dOffDebitSumAmount(transRS.getDouble("OffSumAmount")); //表外业务借方合计
					}			
					if(lTransDirection == SETTConstant.DebitOrCredit.CREDIT) {
						accountRecordInfo.setM_dCreditSumAmount(transRS.getDouble("SumAmount")); //表内业务贷方合计
						accountRecordInfo.setM_dOffCreditSumAmount(transRS.getDouble("OffSumAmount")); //表外业务贷方合计
					}
				}
	        }
	        catch (Exception e) {
		        throw new ITreasuryDAOException("查询异常", e);
		    }
			
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ITreasuryDAOException("查询异常",e);
        }
        finally {
        	finalizeDAO();
        }
		return accountRecordInfo;
	}
	
	/**
	 * Create 2008-12-25
	 * 日结科目汇总查询(查询单个科目汇总)
	 * 查询条件使用AccountRecordConditionInfo Bean
	 */
	public AccountRecordInfo getDailyAccountRecord(AccountRecordConditionInfo conditionInfo)
		throws ITreasuryDAOException
	{
		StringBuffer sbSQL = null;
		StringBuffer sbGlEntrySQL = null; 
		AccountRecordInfo accountRecordInfo = null;
		
		try
		{
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/
			
	        try {
	        	int _compareDate = conditionInfo.getTsDateStart().compareTo(Env.getSystemDate(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId()));
	        	
	        	sbGlEntrySQL = new StringBuffer();
	        	sbGlEntrySQL.append(" select a.sSubjectCode, \n");
	        	sbGlEntrySQL.append("        sum(a.mAmount) mAmount, \n");
	        	sbGlEntrySQL.append("        count(a.sTransNO) mNumber \n");
	        	sbGlEntrySQL.append(" from Sett_GlEntry a \n");
	        	sbGlEntrySQL.append(" where a.nOfficeID = ? \n");
	        	sbGlEntrySQL.append(" and a.nCurrencyID = ? \n");
	        	sbGlEntrySQL.append(" and a.nStatusID > 0 \n");
	        	sbGlEntrySQL.append(" and a.nTransDirection = ? \n");
	        	sbGlEntrySQL.append(" and a.dtExecute between ? and ? \n");
	        	sbGlEntrySQL.append(" group by a.sSubjectCode \n");
	        	
	        	sbSQL = new StringBuffer();
				sbSQL.append("select SubjectBalance.id, \n");
				sbSQL.append("       SubjectBalance.sSubjectCode subjectCode, \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode 
				sbSQL.append("       SubjectBalance.ssegmentname2 subjectName, \n");
				sbSQL.append("       SubjectBalance.nSubjectType subjectType, \n");
				sbSQL.append("       SubjectBalance.nBalanceDirection subjectBalanceDirection, \n");
				//sbSQL.append("       SubjectBalance.mStartAmount, \n");
				sbSQL.append("       SubjectBalance.mStartDebitAmount, \n");
				sbSQL.append("       SubjectBalance.mStartCreditAmount, \n");
				sbSQL.append("       Sett_GlEntry.mDebitAmount, \n");
				sbSQL.append("       Sett_GlEntry.mDebitNumber, \n");
				sbSQL.append("       Sett_GlEntry.mLoanAmount,  \n");
				sbSQL.append("       Sett_GlEntry.mLoanNumber, \n");
				//sbSQL.append("       decode(SubjectBalance.nSubjectType, "+SETTConstant.SubjectAttribute.ASSET+", SubjectBalance.mStartAmount+Sett_GlEntry.mDebitAmount-Sett_GlEntry.mLoanAmount, "+SETTConstant.SubjectAttribute.PAYOUT+", SubjectBalance.mStartAmount+Sett_GlEntry.mDebitAmount-Sett_GlEntry.mLoanAmount, "+SETTConstant.SubjectAttribute.DEBT+", SubjectBalance.mStartAmount+Sett_GlEntry.mLoanAmount-Sett_GlEntry.mDebitAmount, "+SETTConstant.SubjectAttribute.RIGHT+", SubjectBalance.mStartAmount+Sett_GlEntry.mLoanAmount-Sett_GlEntry.mDebitAmount, "+SETTConstant.SubjectAttribute.INCOME+", SubjectBalance.mStartAmount+Sett_GlEntry.mLoanAmount-Sett_GlEntry.mDebitAmount) mEndAmount \n");
				sbSQL.append("       decode(SubjectBalance.nSubjectType, "+SETTConstant.SubjectAttribute.ASSET+", SubjectBalance.mStartDebitAmount+Sett_GlEntry.mDebitAmount, "+SETTConstant.SubjectAttribute.PAYOUT+", SubjectBalance.mStartDebitAmount+Sett_GlEntry.mDebitAmount, "+SETTConstant.SubjectAttribute.DEBT+", SubjectBalance.mStartDebitAmount-Sett_GlEntry.mDebitAmount, "+SETTConstant.SubjectAttribute.RIGHT+", SubjectBalance.mStartDebitAmount-Sett_GlEntry.mDebitAmount, "+SETTConstant.SubjectAttribute.INCOME+", SubjectBalance.mStartDebitAmount-Sett_GlEntry.mDebitAmount) mEndDebitAmount, \n");
				sbSQL.append("       decode(SubjectBalance.nSubjectType, "+SETTConstant.SubjectAttribute.ASSET+", SubjectBalance.mStartDebitAmount-Sett_GlEntry.mLoanAmount, "+SETTConstant.SubjectAttribute.PAYOUT+", SubjectBalance.mStartDebitAmount-Sett_GlEntry.mLoanAmount, "+SETTConstant.SubjectAttribute.DEBT+", SubjectBalance.mStartDebitAmount+Sett_GlEntry.mLoanAmount, "+SETTConstant.SubjectAttribute.RIGHT+", SubjectBalance.mStartDebitAmount+Sett_GlEntry.mLoanAmount, "+SETTConstant.SubjectAttribute.INCOME+", SubjectBalance.mStartDebitAmount+Sett_GlEntry.mLoanAmount) mEndCreditAmount \n");
				sbSQL.append("from \n");
				//SubjectBalance
				sbSQL.append(" ( \n");
				//sbSQL.append(" select a.*, decode(a.nBalanceDirection, "+SETTConstant.DebitOrCredit.DEBIT+", nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0), "+SETTConstant.DebitOrCredit.CREDIT+", -nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0)) mStartAmount \n");
				sbSQL.append(" select a.*, decode(a.nBalanceDirection, "+SETTConstant.DebitOrCredit.DEBIT+", nvl(b.mDebitBalance,0), "+SETTConstant.DebitOrCredit.CREDIT+", -nvl(b.mDebitBalance,0)) mStartDebitAmount, nvl(b.mCreditBalance, 0) mStartCreditAmount  \n");
				sbSQL.append(" from sett_vglsubjectdefinition a, \n");//Maxd_2012-06-05 14:08:01 sett_glsubjectdefinition替换为sett_vglsubjectdefinition 
				sbSQL.append("      (select * from Sett_GlBalance \n");
				//判断是否是今天查询
				sbSQL.append("         where dtgldate = ? \n");
				sbSQL.append("      ) b \n");
				
				sbSQL.append(" where a.sSubjectCode = ? \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode 
				sbSQL.append(" and a.sSubjectCode = b.sglsubjectcode(+) \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode 
				sbSQL.append(" and a.nOfficeID = ? \n");
				sbSQL.append(" and a.nCurrencyID = ? \n");
				sbSQL.append(" ) SubjectBalance, \n");
				//DebitSett_GlEntry & LoanSett_GlEntry
				sbSQL.append(" ( \n");
	        	sbSQL.append("select min(a.ssegmentcode2) sSubjectCode, \n");
	        	sbSQL.append("       sum(nvl(DebitSett_GlEntry.mAmount, 0)) mDebitAmount, \n");
	        	sbSQL.append("       sum(nvl(DebitSett_GlEntry.mNumber, 0)) mDebitNumber, \n");
	        	sbSQL.append("       sum(nvl(LoanSett_GlEntry.mAmount, 0)) mLoanAmount, \n");
	        	sbSQL.append("       sum(nvl(LoanSett_GlEntry.mNumber, 0)) mLoanNumber \n");
	        	sbSQL.append("from  \n");
	        	sbSQL.append("sett_vglsubjectdefinition a, \n");//Maxd_2012-06-05 14:08:01 sett_glsubjectdefinition替换为sett_vglsubjectdefinition 
	        	//DebitSett_GlEntry
	        	sbSQL.append(" ( \n");
	        	sbSQL.append(sbGlEntrySQL.toString());
	        	sbSQL.append("  ) DebitSett_GlEntry, \n");
	        	//LoanSett_GlEntry
	        	sbSQL.append(" ( \n");
	        	sbSQL.append(sbGlEntrySQL.toString());
	        	sbSQL.append("  ) LoanSett_GlEntry \n");
	        	sbSQL.append("where a.nOfficeID = ? \n");
	        	sbSQL.append("and a.nCurrencyID = ? \n");
	        	sbSQL.append("and a.sSubjectCode = DebitSett_GlEntry.sSubjectCode(+) \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode 
	        	sbSQL.append("and a.sSubjectCode = LoanSett_GlEntry.sSubjectCode(+) \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode 
	        	sbSQL.append("and a.sSubjectCode like ? || '%' \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode 
	        	sbSQL.append(" ) Sett_GlEntry \n");
	        	sbSQL.append(" where SubjectBalance.ssegmentcode2 = Sett_GlEntry.sSubjectCode \n");

				prepareStatement(sbSQL.toString());
				int nIndex = 1;

				//判断是否是今天查询
//				if(_compareDate == 0 && CloseSystemMain.getSystemStatusID(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId(), Constant.ModuleType.SETTLEMENT) != Constant.SystemStatus.CLOSE){
//					transPS.setTimestamp(nIndex++, conditionInfo.getTsDateStart());
//				}
//				else {
				//无论是否查询当天，都要查前一天的期末余额
					transPS.setTimestamp(nIndex++, DataFormat.getDateTime(DataFormat.getDateString(DataFormat.getPreviousDate(conditionInfo.getTsDateStart()))));
//				}
				transPS.setString(nIndex++,  conditionInfo.getSubjectCode());
				transPS.setLong(nIndex++, conditionInfo.getOfficeId());
				transPS.setLong(nIndex++, conditionInfo.getCurrencyId());
				transPS.setLong(nIndex++, conditionInfo.getOfficeId());
				transPS.setLong(nIndex++, conditionInfo.getCurrencyId());
				transPS.setLong(nIndex++, SETTConstant.DebitOrCredit.DEBIT);
				transPS.setTimestamp(nIndex++, conditionInfo.getTsDateStart());
				transPS.setTimestamp(nIndex++, conditionInfo.getTsDateEnd());
				transPS.setLong(nIndex++, conditionInfo.getOfficeId());
				transPS.setLong(nIndex++, conditionInfo.getCurrencyId());
				transPS.setLong(nIndex++, SETTConstant.DebitOrCredit.CREDIT);
				transPS.setTimestamp(nIndex++, conditionInfo.getTsDateStart());
				transPS.setTimestamp(nIndex++, conditionInfo.getTsDateEnd());
				transPS.setLong(nIndex++, conditionInfo.getOfficeId());
				transPS.setLong(nIndex++, conditionInfo.getCurrencyId());
				transPS.setString(nIndex++,  conditionInfo.getSubjectCode());
				
				log4j.info("OBFinanceInstrDAO.getDailyAccountRecord()\n");
				log4j.info("subjectCode = " + conditionInfo.getSubjectCode());
				log4j.info("sbSQL=\n" + sbSQL.toString());

				executeQuery();
				
				while (transRS.next())
				{
					accountRecordInfo = new AccountRecordInfo();
					accountRecordInfo.setId(transRS.getLong("id"));  //科目ID
					accountRecordInfo.setM_strAccount(transRS.getString("subjectCode")); //科目号
					accountRecordInfo.setM_strName(transRS.getString("subjectName")); //科目名称
					accountRecordInfo.setM_lSubjectType(transRS.getDouble("subjectType")); //科目类型
					//accountRecordInfo.setM_dStartBalance(transRS.getDouble("mStartAmount")); //期初余额
					accountRecordInfo.setM_dStartDebitBalance(transRS.getDouble("mStartDebitAmount"));//期初借方余额
					accountRecordInfo.setM_dStartCreditBalance(transRS.getDouble("mStartCreditAmount"));//期初贷方余额
					accountRecordInfo.setM_dDebitAmount(transRS.getDouble("mDebitAmount")); //借方金额
					accountRecordInfo.setM_lDebitNumber(transRS.getLong("mDebitNumber")); //借方张数合计
					accountRecordInfo.setM_dLoanAmount(transRS.getDouble("mLoanAmount")); //贷方金额
					accountRecordInfo.setM_lCreditNumber(transRS.getLong("mLoanNumber")); //贷方张数合计
					//accountRecordInfo.setM_dEndBalance(transRS.getDouble("mEndAmount")); //期末余额
					accountRecordInfo.setM_dEndDebitBalance(transRS.getDouble("mEndDebitAmount"));//期末借方余额
					accountRecordInfo.setM_dEndCreditBalance(transRS.getDouble("mEndCreditAmount"));//期末贷方余额
				}
				
				if(accountRecordInfo != null) {
					log4j.info("Query Info:  accountRecordInfo  =  " + accountRecordInfo.toString());
				}
	        }
	        catch (Exception e) {
		        throw new ITreasuryDAOException("查询异常", e);
		    }
			
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ITreasuryDAOException("查询异常",e);
        }
        finally {
        	finalizeDAO();
        }
		return accountRecordInfo;
	}
}