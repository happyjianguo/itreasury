/*
 * Created on 2004-10-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.settadjustinterestrate.dao;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestConditionInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestQueryInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettRateAdjustPayConditionInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettInterestRateSettingInfo;
import com.iss.itreasury.settlement.settpaynotice.dao.SettPayNoticeDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author jinchen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Sett_RateAdjustPayConditionDAO extends SettlementDAO
{
	/**
	 *  
	 */
	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public Sett_RateAdjustPayConditionDAO()
	{
		super("LOAN_RATEADJUSTPAYCONDITION");
		// TODO Auto-generated constructor stub
	}
	public SettAdjustInterestConditionInfo findAdjustInterestRateByID(long lID) throws SettlementDAOException
	{
		SettAdjustInterestConditionInfo info = new SettAdjustInterestConditionInfo();
		try
		{
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			/*
			 * sbSQL.append(" select a.*, "); sbSQL.append(" b.ID as
			 * nContractID, b.sContractCode, c.mExamineAmount, f.mrate as
			 * mRateNow,g.mrate as mRateBefore, c.dtStartDate, c.dtEndDate,
			 * d.sName, e.sInterestRateNo ,g.dtValidate as dtRateValidate ");
			 * sbSQL.append(" from loan_rateadjustpaycondition a,
			 * loan_ContractForm b, loan_loanForm c, Client d,
			 * loan_InterestRateTypeInfo e, loan_InterestRate f
			 * ,loan_InterestRate g "); sbSQL.append(" where
			 * a.nContractID=b.ID(+) and b.nLoanID=c.ID(+) and
			 * c.nBorrowClientID=d.ID(+) and a.nBankInterestID=f.ID(+) ");
			 * sbSQL.append(" and f.nBankInterestTypeID=e.ID and a.ID=? and
			 * e.nCurrencyID=? "); sbSQL.append(" and b.nBankInterestID=g.ID(+)
			 * ");
			 */
			//添加 查询用户 2004,10,13
			sbSQL.append(" select a.*, ");
			sbSQL
					.append(" b.ID as nContractID, b.sContractCode, c.mExamineAmount, f.mrate as mRateNow,g.mrate as mRateBefore, c.dtStartDate, c.dtEndDate, d.sName, h.sName as sInputUserName, e.sInterestRateNo ,g.dtValidate as dtRateValidate ");
			sbSQL.append(" from loan_rateadjustpaycondition a, loan_ContractForm b, loan_loanForm c, Client d, loan_InterestRateTypeInfo e, loan_InterestRate f ,loan_InterestRate g , userinfo h");
			sbSQL.append(" where a.nContractID=b.ID(+) and b.nLoanID=c.ID(+) and c.nBorrowClientID=d.ID(+) and a.nBankInterestID=f.ID(+) ");
			sbSQL.append(" and f.nBankInterestTypeID=e.ID and a.ID=? and e.nCurrencyID=? ");
			sbSQL.append(" and b.nBankInterestID=g.ID(+) and a.InputUserId = h.id");
			log4j.info(" 查询申请利率修改的方法为: findAdjustInterestRateByID SQL =  " + sbSQL.toString());
			Log.print(" 查询申请利率调整的方法为: findAdjustInterestRateByID(long lID)");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1, lID);
			transPS.setLong(2, Constant.CurrencyType.RMB);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				info.setID(lID);
				info.setLoanPayNoticeID(transRS.getLong("nLoanPayNoticeID"));
				info.setContractID(transRS.getLong("nContractID"));
				info.setContractNo(transRS.getString("sContractCode"));
				info.setClientName(transRS.getString("sName"));
				info.setExamineAmount(transRS.getDouble("mExamineAmount"));
				//info.fInterestRate = transRS.getFloat ("mRateBefore");
				info.setStartDate(transRS.getTimestamp("dtStartDate"));
				info.setEndDate(transRS.getTimestamp("dtEndDate"));
				info.setBankLoanInterestRateID(transRS.getLong("nBankInterestID"));
				info.setBankLoanInterestRateNo(transRS.getString("sInterestRateNo"));
				info.setRate(transRS.getFloat("mRateNow"));
				info.setValidate(transRS.getTimestamp("dtValiDate"));
				info.setReason(transRS.getString("sReason"));
				info.setStatusID(transRS.getLong("nStatusID"));
				info.setInputUserID(transRS.getLong("nInputUserID"));
				//info.strInputUserName = findUserNameByID ((int)
				// info.lInputUserID);
				info.setInputUserName(transRS.getString("sInputUserName"));
				info.setInputDate(transRS.getTimestamp("dtInputDate"));
				//
				info.setRateValidate(transRS.getTimestamp("dtRateValidate"));
				//
				//Timestamp tsTemp =
				// DataFormat.getPreviousDate(info.tsValidate);
				info.setNextCheckLevel(transRS.getLong("nNextCheckLevel"));
				info.setAdjustRate(transRS.getDouble("MADJUSTRATE"));
				info.setStaidAdjustRate(transRS.getDouble("MSTAIDADJUSTRATE"));
			}
			this.finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log4j.error(e.getMessage());
			throw new SettlementDAOException(e);
		}
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Log.print(e.toString());
			}
		}
		return info;
	}
	/**
	 * 检查是否有重复记录
	 * 
	 * @param info
	 * @return 存在重复记录返回真
	 * @throws SettlementDAOException
	 */
	public boolean findRateAdjustPayConditionByCondition(SettRateAdjustPayConditionInfo info) throws SettlementDAOException
	{
		boolean boolTemp = false;
		Vector vectTemp = new Vector();
		long lID = info.getId();
		//LoanInterestRateID;
		long lLoanInterestRateID = info.getBankInterestId();
		long lContractID = info.getContractId();
		long lLoanPayNoticeID = info.getLoanPayNoticeId();
		double dRate = info.getRate();
		double dAdjustRate = info.getAdjustRate();
		double dStaidAdjustRate = info.getStaidAdjustRate();
		Timestamp tsValidate = info.getValidate();
		String strReason = info.getReason();
		long lCurrencyID = info.getCurrencyId();
		long lOfficeID = info.getOfficeId();
		long lInputUserID = info.getInputUserId();
		Timestamp tsInputDate = info.getInputDate();
		/*
		 * ps.setLong(1, lMaxID); ps.setLong(2, lLoanInterestRateID);
		 * ps.setLong(3, lContractID); ps.setLong(4, lLoanPayNoticeID);
		 * ps.setDouble(5, dRate); ps.setTimestamp(6, tsValidate);
		 * ps.setString(7, strReason); ps.setLong(8, lInputUserID);
		 * ps.setLong(9, LOANConstant.InterestRateSettingStatus.SUBMIT);
		 * ps.setLong(10, lOfficeID); ps.setLong(11, lInputUserID);
		 * ps.setDouble(12, dAdjustRate); ps.setDouble(13, dStaidAdjustRate);
		 */
		try
		{
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select count(*) from loan_rateadjustpaycondition where 1=1 ");
			if (lLoanInterestRateID > 0)
			{
				sbSQL.append(" and nBankInterestID = " + lLoanInterestRateID);
			}
			if (dAdjustRate != -100)
			{
				sbSQL.append(" and MADJUSTRATE = " + dAdjustRate);
			}
			if (dStaidAdjustRate != -100)
			{
				sbSQL.append(" and MSTAIDADJUSTRATE = " + dStaidAdjustRate);
			}
			if (lContractID > 0)
			{
				//sbSQL += " and nContractID = " + lContractID;
				sbSQL.append(" and nContractID = " + lContractID);
			}
			if (lLoanPayNoticeID > 0)
			{
				//sbSQL += " and nLoanPayNoticeID = " + lLoanPayNoticeID;
				sbSQL.append(" and nLoanPayNoticeID = " + lLoanPayNoticeID);
			}
			if (tsValidate != null)
			{
				//    			sbSQL += " and to_char(dtValidate,'yyyy-mm-dd') = '" +
				//    				DataFormat.getDateString(tsValidate) + "' ";
				sbSQL.append(" and to_char(dtValidate,'yyyy-mm-dd') = '" + DataFormat.getDateString(tsValidate) + "' ");
			}
			log4j.info("检查是否有重复记录:findRateAdjustPayConditionByCondition SQL =  " + sbSQL.toString());
			log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			String strTemp = null;
			long lTemp;
			while (transRS.next())
			{
				lTemp = transRS.getLong(1);
				vectTemp.add(String.valueOf(lTemp));
			}
			this.finalizeDAO();
			if (null != vectTemp)
			{
				if (vectTemp.size() > 0)
					boolTemp = true;
			}
		}
		catch (ITreasuryDAOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log4j.error(e1.getMessage());
			throw new SettlementDAOException(e1);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Log.print(e.toString());
			}
		}
		return boolTemp;
	}
	public Collection findAdjustInterestByMultiOption(SettAdjustInterestQueryInfo qInfo) throws SettlementDAOException
	{
		long lActionID = qInfo.getActionID();
		Timestamp tsStartDate = qInfo.getStartDate();
		Timestamp tsEndDate = qInfo.getEndDate();
		long lStatusID = qInfo.getStatusID();
		long lCurrencyID = qInfo.getCurrencyID();
		long lOfficeID = qInfo.getOfficeID();
		long lUserID = qInfo.getUserID();
		long lPageLineCount = qInfo.getPageLineCount();
		long lPageNo = qInfo.getPageNo();
		String strOrderParam = qInfo.getOrderParam();
		long lDesc = qInfo.getDesc();
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		String strSelect = null;
		try
		{
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			/*
			 * 计算记录总数
			 */
			strSelect = " select count(*) ";
			//sbSQL.append(" select count(*) ");
			sbSQL.append(" from loan_rateadjustpaycondition aa, loan_ContractForm bb, loan_LoanForm cc, ");
			sbSQL.append(" Client dd, loan_InterestRateTypeInfo ee, loan_InterestRate ff , loan_InterestRate gg ");
			sbSQL
					.append(" where aa.nContractID=bb.ID and bb.nLoanID=cc.ID(+) and cc.nBorrowClientID=dd.ID(+) and aa.nBankInterestID=ff.ID(+) and ff.nBankInterestTypeID=ee.ID and bb.nBankInterestID=gg.ID(+) ");
			//////////////////////查询条件////////////////////////////////////////////////////
			if (lOfficeID != -1)
			{
				//strSQL = strSQL + " and aa.nOfficeID = " + lOfficeID;
				sbSQL.append(" and aa.nOfficeID = " + lOfficeID);
			}
			if (lCurrencyID != -1)
			{
				//strSQL = strSQL + " and ee.nCurrencyID = " + lCurrencyID;
				sbSQL.append(" and ee.nCurrencyID = " + lCurrencyID);
				//strSQL = strSQL + " and ff.nCurrencyID = " + lCurrencyID;
			}
			if (tsStartDate != null)
			{
				//strSQL = strSQL + " and to_char(aa.dtInputDate,'yyyy-mm-dd')
				// >= '" + DataFormat.getDateString (tsStartDate) + "'";
				sbSQL.append(" and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(tsStartDate) + "'");
			}
			if (tsEndDate != null)
			{
				//strSQL = strSQL + " and to_char(aa.dtInputDate,'yyyy-mm-dd')
				// <= '" + DataFormat.getDateString (tsEndDate) + "'";
				sbSQL.append(" and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(tsEndDate) + "'");
			}
			//申请修改查询
			if (lActionID == 1)
			{
				if (lUserID != -1 && lStatusID == SETTConstant.SettAdjustInterestStatus.SUBMIT)
				{
					//strSQL = strSQL + " and aa.nInputUserID = " + lUserID + "
					// and aa.NNEXTCHECKUSERID = " + lUserID + " and
					// aa.nStatusID = " +
					// LOANConstant.InterestRateSettingStatus.SUBMIT;
					//strSQL = strSQL + " and aa.nInputUserID = " + lUserID + "
					// and aa.nNextCheckLevel = 1 and aa.nStatusID = " +
					// SETTConstant.SettAdjustInterestStatus.SUBMIT;
					sbSQL.append(" and aa.nInputUserID = " + lUserID + " and aa.nStatusID = " + SETTConstant.SettAdjustInterestStatus.SUBMIT);
				}
				/*
				 * if (lUserID != -1 && lStatusID ==
				 * SETTConstant.SettAdjustInterestStatus.CHECK) { strSQL =
				 * strSQL + " and aa.nInputUserID = " + lUserID + " and
				 * aa.nStatusID = " +
				 * SETTConstant.SettAdjustInterestStatus.CHECK; sbSQL.append() }
				 * if (lUserID != -1 && lStatusID == -1) { //strSQL = strSQL + "
				 * and (( aa.nInputUserID = " + lUserID // + " and
				 * aa.NNEXTCHECKUSERID = " + lUserID + " and // aa.nStatusID = " + //
				 * LOANConstant.InterestRateSettingStatus.SUBMIT + " ) or ( //
				 * aa.nInputUserID = " + lUserID + " and aa.nStatusID = " + //
				 * LOANConstant.InterestRateSettingStatus.CHECK + " )) "; strSQL =
				 * strSQL + " and (( aa.nInputUserID = " + lUserID + " and
				 * aa.nNextCheckLevel = 1 and aa.nStatusID = " +
				 * LOANConstant.InterestRateSettingStatus.SUBMIT + " ) or (
				 * aa.nInputUserID = " + lUserID + " and aa.nStatusID = " +
				 * LOANConstant.InterestRateSettingStatus.CHECK + " )) "; }
				 */
			}
			//复核查询
			else if (lActionID == 2)
			{
				/*
				 * ApprovalBiz appBiz = new ApprovalBiz();
				 * //获得真正来审批这个记录的人（真实或传给的虚拟的人！） String strUser =
				 * appBiz.findTheVeryUser(lModule, lLoanType, lAction, lUserID);
				 * //lUserID =
				 * appBiz.findTheVeryUser(lModule,lLoanType,lAction,lUserID); if
				 * (lUserID >= 0 && lStatusID ==
				 * LOANConstant.InterestRateSettingStatus.SUBMIT) strSQL += "
				 * and aa.NSTATUSID = " +
				 * LOANConstant.InterestRateSettingStatus.SUBMIT + " and
				 * aa.NNEXTCHECKUSERID in " + strUser; if (lUserID >= 0 &&
				 * lStatusID == LOANConstant.InterestRateSettingStatus.CHECK)
				 * strSQL += " and aa.NSTATUSID = " +
				 * LOANConstant.InterestRateSettingStatus.CHECK; if (lUserID >=
				 * 0 && (lStatusID <= 0 || lStatusID > 3)) strSQL += " and
				 * ((aa.NSTATUSID = " +
				 * LOANConstant.InterestRateSettingStatus.SUBMIT + " and
				 * aa.NNEXTCHECKUSERID in " + strUser + ") or aa.NSTATUSID = " +
				 * LOANConstant.InterestRateSettingStatus.CHECK + ")";
				 * 
				 * if (lStatusID > 0) { strSQL += " and aa.nStatusID = " +
				 * lStatusID; } else { strSQL += " and aa.nStatusID in ( " +
				 * LOANConstant.InterestRateSettingStatus.CHECK + "," +
				 * LOANConstant.InterestRateSettingStatus.SUBMIT + ")"; }
				 */
				if (lUserID != -1)
				{
					sbSQL.append(" and ( (aa.nStatusID = " + SETTConstant.SettAdjustInterestStatus.SUBMIT + "aa.nInputUserID <> " + lUserID + " ) or (aa.nStatusID="
							+ SETTConstant.SettAdjustInterestStatus.CHECK + " and aa.NNEXTCHECKUSERID = " + lUserID + " ) )");
				}
				/*
				 * strSQL+=" and (
				 * (a.nStatusID="+SETTConstant.SettPayNoticeStatus.SUBMIT +" and
				 * a.nInputUserID <>"+qInfo.getInputUserID() +") or
				 * (a.nStatusID="+SETTConstant.SettPayNoticeStatus.CHECK +" and
				 * a.NNEXTCHECKUSERID="+qInfo.getInputUserID() +" ) )";
				 */
			}
			log4j.info(" 查询利率调整的 计算记录数 SQL =  " + strSelect + sbSQL.toString());
			log.print(strSelect + sbSQL.toString());
			transPS = transConn.prepareStatement(strSelect + sbSQL.toString());
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				lRecordCount = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			// 排序处理
			int nIndex = 0;
			//String orderParamString = qInfo.getOrderParam();
			nIndex = strOrderParam.indexOf(".");
			if (nIndex > 0)
			{
				if (strOrderParam.substring(0, nIndex).toLowerCase().equals("LOAN_RATEADJUSTPAYCONDITION"))
				{
					sbSQL.append(" order by " + strOrderParam.substring(nIndex + 1));
				}
			}
			else
			{
				sbSQL.append(" order by aa.ID");
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				//strSQL += " desc";
				sbSQL.append(" desc ");
			}
			//          返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;
			String strSQL = sbSQL.toString();
			strSQL = " select aa.*,bb.sContractCode,dd.sName,ee.sInterestRateNo,ee.sInterestRateName,gg.mRate as mRateBefore ,ff.mRate*(1+nvl(aa.MADJUSTRATE,0)/100)+nvl(aa.MSTAIDADJUSTRATE,0) as mRateNow,bb.NINTERVALNUM "
					+ strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			log4j.info(" 查询利率调整的  SQL =  " + strSQL);
			log.print(" 查询利率调整的  SQL =  " + strSQL);
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			while (transRS != null && transRS.next())
			{ //返回SettInterestRateSettingInfo
				SettAdjustInterestConditionInfo info = new SettAdjustInterestConditionInfo();
				info.setId(transRS.getLong("ID"));
				info.setContractID(transRS.getLong("nContractID"));
				info.setContractNo(transRS.getString("sContractCode"));
				info.setLoanPayNoticeID(transRS.getLong("nLoanPayNoticeID"));
				info.setClientName(transRS.getString("sName"));
				//info.mExamineAmount = transRS.getDouble ("mExamineAmount");
				//info.fRate = transRS.getFloat ("mRate");
				info.setRate(transRS.getDouble("mRateNow"));
				//info.fInterestRate = transRS.getFloat ("mRateBefore");
				//info.tsStartDate = transRS.getTimestamp ("dtStartDate");
				//info.tsEndDate = transRS.getTimestamp ("dtEndDate");
				info.setBankLoanInterestRateNo(transRS.getString("sInterestRateNo"));
				info.setBankLoanInterestRateName(transRS.getString("sInterestRateName"));
				//info.fRate = transRS.getFloat ("dRate");
				info.setValidate(transRS.getTimestamp("dtValiDate"));
				Timestamp tsTemp = DataFormat.getPreviousDate(info.getValidate());
				//RateInfo rinfo = new RateInfo();
				//rinfo = dao.getLatelyRate((long) -1, info.lContractID, tsTemp);
				try
				{
					if (info.getLoanPayNoticeID() > 0)
					{
						SettPayNoticeDAO settPayNoticeDao = new SettPayNoticeDAO();
						info.setInterestRate(settPayNoticeDao.getLatelyRate(info.getLoanPayNoticeID(), tsTemp));
					}
					else
					{
						info.setFInterestRate(UtilOperation.getLoanInterestRate(null, (long) -1, info.getContractID(), tsTemp));
						//info.fInterestRate = rinfo.getLateRate(); //执行利率
					}
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.print("herecontratID=" + info.getContractID());
				log.print("tstemp=" + tsTemp);
				//info.strReason = transtransRS.getString ("sReason");
				info.setStatusID(transRS.getLong("nStatusID"));
				info.setInputDate(transRS.getTimestamp("dtInputDate"));
				info.setIntervalNum(transRS.getLong("NINTERVALNUM"));
				info.setNextCheckLevel(transRS.getLong("nNextCheckLevel"));
				info.setCount(lRecordCount);
				v.add(info);
			}
			this.finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			log4j.error(e.getMessage());
			throw new SettlementDAOException(e);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Log.print(e.toString());
			}
		}
		return (v.size() > 0 ? v : null);
	}
	public long cancelAdjustInterestRate(long lAdjustConditionID) throws SettlementDAOException
	{
		String strSQL = null;
		long lContractID = -1;
		long lLoanPayNoticeID = -1;
		try
		{
			this.initDAO();
			//          查出该loan_rateadjustpaycondition下的合同id和放款通知单id
			strSQL = " select ncontractid,nloanpaynoticeid from loan_rateadjustpaycondition " + " where id = ?";
			transPS = transConn.prepareStatement(strSQL);
			transPS.setLong(1, lAdjustConditionID);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				lContractID = transRS.getLong("nContractID");
				lLoanPayNoticeID = transRS.getLong("nLoanPayNoticeID");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			if (lContractID > 0)//删除
			{
				strSQL = " delete from loan_rateadjustcontractdetail where ncontractid = ? and nadjustconditionid = ? ";
				//System.out.println("sql="+strSQL);
				transPS = transConn.prepareStatement(strSQL);
				transPS.setLong(1, lContractID);
				transPS.setLong(2, lAdjustConditionID);
				transPS.execute();
				transPS.close();
				transPS = null;
			}
			if (lLoanPayNoticeID > 0)
			{
				strSQL = " delete from loan_rateadjustpaydetail where nLoanpayNoticeID = ? and nadjustconditionid = ? ";
				//System.out.println("sql="+strSQL);
				transPS = transConn.prepareStatement(strSQL);
				transPS.setLong(1, lLoanPayNoticeID);
				transPS.setLong(2, lAdjustConditionID);
				transPS.execute();
				transPS.close();
				transPS = null;
			}
			else
			//删除该合同下所有放款通知单的相关数据
			{
				strSQL = " delete from loan_rateadjustpaydetail where nLoanpayNoticeID in ( " + " select id from loan_payform where ncontractid = ? ) " + " and nadjustconditionid = ? ";
				//System.out.println("sql="+strSQL);
				transPS = transConn.prepareStatement(strSQL);
				transPS.setLong(1, lContractID);
				transPS.setLong(2, lAdjustConditionID);
				transPS.execute();
				transPS.close();
				transPS = null;
			}
		}
		catch (ITreasuryDAOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log4j.error(e1.getMessage());
			throw new SettlementDAOException(e1);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Log.print(e.toString());
			}
		}
		return lAdjustConditionID;
	}
	public static void main(String[] args)
	{
	}
}