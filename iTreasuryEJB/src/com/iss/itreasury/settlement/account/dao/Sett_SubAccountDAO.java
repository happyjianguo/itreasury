/*
 * Created on 2003-9-4
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dao;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_SubAccountInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.interest.dataentity.BankInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.LiborInterestAdjustInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.util.UtilOperation;
/**
 * @author ruixie
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_SubAccountDAO extends SettlementDAO
{
	Log4j log4j = null;
	public Sett_SubAccountDAO()
	{
		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	public Sett_SubAccountDAO(Connection conn)
	{
		super(conn);
		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	public static void main(java.lang.String[] args) throws Exception
	{
		//�ڴ˴�������������Ӧ�ó���Ĵ��롣
		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			/*
			 * SubAccountCurrentInfo saci = new SubAccountCurrentInfo();
			 * saci.setInterestRatePlanID(123456);
			 * saci.setInterestRatePlanDate(Env.getSystemDate(1, 1));
			 * saci.setAccountID(4); saci.setID(2);
			 * System.out.println("scuuess" +
			 * dao.updateSubAccountCurrent(saci));
			 */
			SubAccountLoanInfo info = new SubAccountLoanInfo();
			info.setLoanNoteID(384);
			info = dao.querySubInfo(info);
			if (info != null)
			{
				System.out.println("not null :" + info.getOpenAmount());
			}
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * ����˵���������������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public long addSubAccountCurrent(SubAccountCurrentInfo saci) throws SQLException
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" insert into sett_SubAccount(ID,NACCOUNTID,MINTEREST,mOpenAmount, \n");
			sbSQL.append(" dtOpen,dtFinish, \n");
			sbSQL.append(" nIsInterest, \n");
			sbSQL.append(" dtClearInterest, \n");
			sbSQL.append(" NSTATUSID,AC_NINTERESTACCOUNTID,AC_NISOVERDRAFT,AC_NFIRSTLIMITTYPEID, \n");
			sbSQL.append(" AC_MFIRSTLIMITAMOUNT,AC_NSECONDLIMITTYPEID, \n");
			sbSQL.append(" AC_MSECONDLIMITAMOUNT,AC_NTHIRDLIMITTYPEID,AC_MTHIRDLIMITAMOUNT, \n");
			// begin 18
			sbSQL.append(" AC_NINTERESTRATEPLANID, \n");
			sbSQL.append(" AC_DTINTERESTRATEPLAN, \n");
			sbSQL.append(" AC_MCAPITALLIMITAMOUNT,AC_NISNEGOTIATE,AC_MNEGOTIATEAMOUNT, \n");
			sbSQL.append(" AC_MNEGOTIATEUNIT,	AC_MNEGOTIATERATE, \n");
			sbSQL.append(" AC_DTNEGOTIATERATE, \n");
			sbSQL.append(" AC_MMONTHLIMITAMOUNT,AC_MDAYLIMITAMOUNT,AC_MNEGOTIATEINTEREST, \n");
			sbSQL.append(" AC_nIsAllBranch , \n"); //add by rxie
			sbSQL.append(" AC_dtNegotiationStartDate , \n"); //add by mingfang
			sbSQL.append(" AC_dtNegotiationEndDate ) \n"); //add by mingfang
			sbSQL.append(" values(?,?,?,?, \n");
			sbSQL.append(" to_date('" + DataFormat.getDateString(saci.getOpenDate()) + "','yyyy-mm-dd')  \n");
			sbSQL.append(" ,to_date('" + DataFormat.getDateString(saci.getFinishDate()) + "','yyyy-mm-dd')  \n");
			sbSQL.append(" ,?, \n");
			sbSQL.append(" to_date('" + DataFormat.getDateString(saci.getClearInterestDate()) + "','yyyy-mm-dd')  \n");
			sbSQL.append(" ,?,?,?,?,?,?,?,?,?,?, \n");
			sbSQL.append(" to_date('" + DataFormat.getDateString(saci.getInterestRatePlanDate()) + "','yyyy-mm-dd')  \n");
			sbSQL.append(" ,?,?,?,?,?, \n");
			sbSQL.append(" to_date('" + DataFormat.getDateString(saci.getNegotiateRateDate()) + "','yyyy-mm-dd')  \n");
			sbSQL.append(" ,?,?,?,?,  \n");
			sbSQL.append(" to_date('" + DataFormat.getDateString(saci.getNegotiationStartDate()) + "','yyyy-mm-dd')  \n"); //add by mingfang
			sbSQL.append(" ,to_date('" + DataFormat.getDateString(saci.getNegotiationEndDate()) + "','yyyy-mm-dd') ) \n");    //add by mingfang
			ps = conn.prepareStatement(sbSQL.toString());
			//get the maximum id
			lReturn = getNextID();
			saci.setID(lReturn);
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(saci, ps, 1);
			ps.executeUpdate();
			//log4j.info("add sett_SubAccount successfully. SubAccountID is "
			// + lReturn);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * ����˵���������������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public long addSubAccountFix(SubAccountFixedInfo safi) throws SQLException
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" insert into sett_SubAccount( ID, NACCOUNTID,MINTEREST,MBALANCE,mOpenAmount,dtOpen,dtFinish,nIsInterest,dtClearInterest,NSTATUSID,  \n");
			sbSQL.append("  AF_SDEPOSITNO, AF_MRATE, AF_DTSTART, AF_DTEND, \n");
			sbSQL.append("  AF_MPREDRAWINTEREST, AF_DTPREDRAW, AF_NDEPOSITTERM, \n");
			sbSQL.append("  AF_NINTERESTPLANID,AF_NNOTICEDAY,AF_NINTERESTACCOUNTID,AF_SSEALNO,AF_NSEALBANKID,AF_ISAUTOCONTINUE,AF_AUTOCONTINUETYPE) \n");
			sbSQL.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			//get the maximum id
			lReturn = getNextID();
			safi.setID(lReturn);
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(safi, ps, 1);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * ����˵���������������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public long addSubAccountLoan(SubAccountLoanInfo sali) throws SQLException
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" insert into sett_SubAccount( \n");
			sbSQL.append(
				" ID,NACCOUNTID,MINTEREST,MBALANCE,mOpenAmount,dtOpen,dtFinish,nIsInterest,NSTATUSID,AL_NLOANNOTEID,AL_NISCYCLOAN,DTCLEARINTEREST,AL_DTCALCULATEINTEREST,  \n");
			sbSQL.append(" AL_NPAYINTERESTACCOUNTID,AL_NRECEIVEINTERESTACCOUNTID,AL_MPREDRAWINTEREST,AL_DTPREDRAW,AL_NPAYSURETYACCOUNTID,AL_NRECEIVESURETYACCOUNTID,  \n");
			sbSQL.append(" AL_NCOMMISSIONACCOUNTID,AL_MSURETYFEE,AL_DTCLEARSUREFEE,AL_MCOMMISSION,AL_DTCLEARCOMMISSION,AL_NINTERESTTAXACCOUNTID,  \n");
			sbSQL.append(" AL_MINTERESTTAX,AL_MINTERESTTAXRATE,AL_DTCLEARINTERESTTAX,AL_DTEFFECTIVETAX,AL_NOVERDUEACCOUNTID,AL_MOVERDUEINTEREST, \n");
			sbSQL.append(" AL_DTCLEAROVERDUE,AL_NCOMPOUNDACCOUNTID,AL_MCOMPOUNDINTEREST,AL_DTCLEARCOMPOUND,AL_mArrearageInterest,AL_nConsignAccountID, \n");
			sbSQL.append(" AL_mOverDueArrearageInterest ,AL_nInterestTaxRatePlanId ) \n");
			sbSQL.append("  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			//get the maximum id
			lReturn = getNextID();
			sali.setID(lReturn);
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(sali, ps, 1);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	////////////////////////////////////////
	/**
	 * ����˵�����������˻�ID���õ����˻���Ϣ
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	//	public SubAccountAssemblerInfo findByID(Connection conn, long
	// lSubAccountID) throws SQLException
	//	{
	//		SubAccountAssemblerInfo saai = null;
	//		PreparedStatement ps = null;
	//		ResultSet rs = null;
	//		StringBuffer sbSQL = null;
	//		try
	//		{
	//			//establish the query sql string
	//			sbSQL = new StringBuffer();
	//			sbSQL.append(" select * from sett_SubAccount ");
	//			sbSQL.append(" where ID = ? ");
	//			ps = conn.prepareStatement(sbSQL.toString());
	//			ps.setLong(1, lSubAccountID);
	//			rs = ps.executeQuery();
	//			if (rs.next())
	//			{
	//				//get the BankBillInfo from current ResultSet object
	//				saai = new SubAccountAssemblerInfo();
	//				getInfoFromResultSet(saai, rs);
	//			}
	//			cleanup(rs);
	//			cleanup(ps);
	//		}
	//		finally
	//		{
	//			cleanup(rs);
	//			cleanup(ps);
	//		}
	//		return saai;
	//	}
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ���������������˻���Ϣ
	 * 
	 * @param qaci
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByConditions(QueryAccountConditionInfo qaci) throws SQLException
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		SubAccountAssemblerInfo saai = null;
		int iTag = 1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			if (qaci.getStartClientCode() != null && qaci.getEndClientCode() != null && qaci.getStartClientCode().length() > 0 && qaci.getEndClientCode().length() > 0)
			{
				sbSQL.append(" select sa.* from sett_Account a, Client c,sett_SubAccount sa ");
				sbSQL.append(" where a.nClientID = c.id ");
				sbSQL.append(" and a.id = sa.nAccountID ");
			}
			else
			{
				sbSQL.append(" select sa.* from sett_Account a, sett_SubAccount sa ");
				sbSQL.append(" where a.id = sa.nAccountID ");
			}
			//appends TypeID to the query where condition
			if (qaci.getOfficeID() > 0)
			{
				sbSQL.append(" and a.nOfficeID = ? ");
			}
			if (qaci.getCurrencyID() > 0)
			{
				sbSQL.append(" and a.nCurrencyID = ? ");
			}
			if (qaci.getStartClientCode() != null && qaci.getEndClientCode() != null && qaci.getStartClientCode().length() > 0 && qaci.getEndClientCode().length() > 0)
			{
				sbSQL.append(" and ( c.SCODE between ? and ? )");
			}
			if (qaci.getStartAccountCode() != null && qaci.getEndAccountCode() != null && qaci.getStartAccountCode().length() > 0 && qaci.getEndAccountCode().length() > 0)
			{
				sbSQL.append(" and ( a.sAccountNo between ? and ? )");
			}
			if (qaci.getAccountTypeID() > 0)
			{
				sbSQL.append(" and a.nAccountTypeID = ? ");
			}
			if (qaci.getCheckStatusID() > 0)
			{
				sbSQL.append(" and a.nCheckStatusID = ? ");
			}
			if (qaci.getStatusID() > 0)
			{
				sbSQL.append(" and a.nStatusID = ? ");
			}
			ps = conn.prepareStatement(sbSQL.toString());
			if (qaci.getOfficeID() > 0)
			{
				ps.setLong(iTag++, qaci.getOfficeID());
			}
			if (qaci.getCurrencyID() > 0)
			{
				ps.setLong(iTag++, qaci.getCurrencyID());
			}
			if (qaci.getStartClientCode() != null && qaci.getEndClientCode() != null && qaci.getStartClientCode().length() > 0 && qaci.getEndClientCode().length() > 0)
			{
				ps.setString(iTag++, qaci.getStartClientCode());
				ps.setString(iTag++, qaci.getEndClientCode());
			}
			if (qaci.getStartAccountCode() != null && qaci.getEndAccountCode() != null && qaci.getStartAccountCode().length() > 0 && qaci.getEndAccountCode().length() > 0)
			{
				ps.setString(iTag++, qaci.getStartAccountCode());
				ps.setString(iTag++, qaci.getEndAccountCode());
			}
			if (qaci.getAccountTypeID() > 0)
			{
				ps.setLong(iTag++, qaci.getAccountTypeID());
			}
			if (qaci.getCheckStatusID() > 0)
			{
				ps.setLong(iTag++, qaci.getCheckStatusID());
			}
			if (qaci.getStatusID() > 0)
			{
				ps.setLong(iTag++, qaci.getStatusID());
			}
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				saai = new SubAccountAssemblerInfo();
				//append one BankBillInfo to the LinkedList object
				getInfoFromResultSet(saai, rs);
				v.add(saai);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * ����˵�������Ļ������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 * @throws Exception 
	 */
	public long updateSubAccountCurrent(SubAccountCurrentInfo saci) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set nAccountID=?, mInterest=?, mOpenAmount=?, \n");
			sbSQL.append(" dtOpen = to_date('" + DataFormat.getDateString(saci.getOpenDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,dtFinish = to_date('" + DataFormat.getDateString(saci.getFinishDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,nIsInterest=?, \n");
			sbSQL.append(" dtClearInterest=to_date('" + DataFormat.getDateString(saci.getClearInterestDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,nStatusID=?, \n");
			sbSQL.append(" AC_nInterestAccountID=?, AC_nIsOverDraft=?, AC_nFirstLimitTypeID=?, AC_mFirstLimitAmount=?, AC_nSecondLimitTypeID=?, \n");
			sbSQL.append(" AC_mSecondLimitAmount=?, AC_nThirdLimitTypeID=?, AC_mThirdLimitAmount=?, AC_nInterestRatePlanID=?, \n");
			sbSQL.append(" AC_dtInterestRatePlan = to_date('" + DataFormat.getDateString(saci.getInterestRatePlanDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,AC_mCapitalLimitAmount=?,\n");
			sbSQL.append(" AC_nIsNegotiate=?, AC_mNegotiateAmount=?, AC_mNegotiateUnit=?, AC_mNegotiateRate=?, \n");
			sbSQL.append(" AC_dtNegotiateRate = to_date('" + DataFormat.getDateString(saci.getNegotiateRateDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,AC_mMonthLimitAmount=?,AC_mDayLimitAmount=?,AC_MNEGOTIATEINTEREST=?,AC_nIsAllBranch=?  \n");
			sbSQL.append(",AC_dtNegotiationStartDate = to_date('" + DataFormat.getDateString(saci.getNegotiationStartDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(",AC_dtNegotiationEndDate = to_date('" + DataFormat.getDateString(saci.getNegotiationEndDate()) + "','yyyy-mm-dd') \n");
			
			//��ǰ����û�м������,���������˻��ڵļ������,���Խ�Ϣ����ռ�����Ϣ�ͼ�������
			sbSQL.append(" , AL_MPREDRAWINTEREST = "+ DataFormat.formatDouble(saci.getDrawInterest()) +" ");
			sbSQL.append(" , AL_DTPREDRAW = to_date('" + DataFormat.getDateString(saci.getPreDrawDate()) + "','yyyy-mm-dd') ");
			
			sbSQL.append(" where ID = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(saci, ps, -1);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return saci.getID();
	}
	/**
	 * ����˵�������Ķ������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public long updateSubAccountFix(SubAccountFixedInfo safi) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set nAccountID=?, mInterest=?, mBalance=?, mOpenAmount=?, \n");
			sbSQL.append(" dtOpen=?, dtFinish=?, nIsInterest=?, dtClearInterest=?, nStatusID=?, \n");
			sbSQL.append(" AF_sDepositNo=?, AF_mRate=?, AF_dtStart=?, AF_dtEnd=?, \n");
			sbSQL.append(" AF_mPreDrawInterest=?, AF_dtPreDraw=?, AF_nDepositTerm=?, AF_nInterestPlanID=?, \n");
			sbSQL.append(" AF_nNoticeDay=?, AF_nInterestAccountID=?, AF_sSealNo=?, AF_nSealBankID=?, af_isautocontinue=?,af_autocontinuetype=?  \n");
			sbSQL.append(" where ID = ? \n");
			log.debug("�޸Ķ������˻�sqlString  =  \n " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(safi, ps, -1);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return safi.getID();
	}
	/**
	 * ����˵�������Ĵ������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public long updateSubAccountLoan(SubAccountLoanInfo sali) throws SQLException
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set nAccountID=?, mInterest=?, mBalance=?, mOpenAmount=?, dtOpen=?, dtFinish=?, nIsInterest=?, nStatusID=?, \n");
			sbSQL.append(" AL_nLoanNoteID=?, AL_nIsCycLoan=?, dtClearInterest=?, al_dtCalculateInterest=?, AL_nPayInterestAccountID=?, \n");
			sbSQL.append(" AL_nReceiveInterestAccountID=?, AL_mPreDrawInterest=?, AL_dtPreDraw=?, AL_nPaySuretyAccountID=?, AL_nReceiveSuretyAccountID=?,\n");
			sbSQL.append(" AL_nCommissionAccountID=?, AL_mSuretyFee=?, AL_dtClearSureFee=?, AL_mCommission=?,AL_dtClearCommission=?, \n");
			sbSQL.append(" AL_nInterestTaxAccountID=?, AL_mInterestTax=?, AL_mInterestTaxRate=?, AL_dtClearInterestTax=? ,AL_dtEffectiveTax=?, \n");
			sbSQL.append(" AL_nOverDueAccountID=?, AL_mOverDueInterest=?, AL_dtClearOverDue=?, AL_nCompoundAccountID=?, AL_mCompoundInterest=?, AL_dtClearCompound=?, AL_mArrearageInterest=?, AL_nConsignAccountID=?, \n");
			sbSQL.append(" AL_mOverDueArrearageInterest=? , AL_nInterestTaxRatePLanID = ? \n");
			sbSQL.append(" where ID = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(sali, ps, -1);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return sali.getID();
	}
	/**
	 * get the current maximum id of table sett_BankBill
	 * 
	 * @return the current maximum id of table sett_BankBill
	 * @exception
	 */
	private long getNextID() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl( max( id ) , 0 ) + 1 as maxno ");
			sbSQL.append(" from sett_SubAccount ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNextID = rs.getLong("maxno");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lNextID;
	}
	private void getInfoFromResultSet(SubAccountCurrentInfo saci, ResultSet rs) throws SQLException
	{
		saci.setID(rs.getLong("ID")); //���˻�ID
		saci.setAccountID(rs.getLong("nAccountID")); //���˻�ID
		saci.setInterest(rs.getDouble("mInterest")); //��ǰ��Ϣ
		saci.setBalance(rs.getDouble("mBalance")); //�˻���ǰ���
		saci.setOpenAmount(rs.getDouble("mOpenAmount")); //�������
		saci.setOpenDate(rs.getTimestamp("dtOpen")); //��������
		saci.setFinishDate(rs.getTimestamp("dtFinish")); //�廧����
		saci.setIsInterest(rs.getLong("nIsInterest")); //�Ƿ��Ϣ
		saci.setClearInterestDate(rs.getTimestamp("dtClearInterest")); //��Ϣ����
		saci.setStatusID(rs.getLong("nStatusID")); //״̬
		saci.setInterestAccountID(rs.getLong("AC_nInterestAccountID"));
		//����Ϣ�˻�ID
		saci.setIsOverDraft(rs.getLong("AC_nIsOverDraft")); //�Ƿ�����͸֧
		saci.setFirstLimitTypeID(rs.getLong("AC_nFirstLimitTypeID")); //��һ��͸֧����
		saci.setFirstLimitAmount(rs.getDouble("AC_mFirstLimitAmount")); //͸֧���
		saci.setSecondLimitTypeID(rs.getLong("AC_nSecondLimitTypeID"));
		saci.setSecondLimitAmount(rs.getDouble("AC_mSecondLimitAmount"));
		saci.setThirdLimitTypeID(rs.getLong("AC_nThirdLimitTypeID"));
		saci.setThirdLimitAmount(rs.getDouble("AC_mThirdLimitAmount"));
		saci.setInterestRatePlanID(rs.getLong("AC_nInterestRatePlanID"));
		//���ʼƻ�ID
		saci.setCapitalLimitAmount(rs.getDouble("AC_mCapitalLimitAmount"));
		//�ʽ����ƽ��
		saci.setIsNegotiate(rs.getLong("AC_nIsNegotiate")); //�Ƿ�Э�����
		saci.setNegotiateAmount(rs.getDouble("AC_mNegotiateAmount")); //Э�����
		saci.setNegotiateUnit(rs.getDouble("AC_mNegotiateUnit")); //Э����λ��Ԫ��
		saci.setNegotiateRate(rs.getDouble("AC_mNegotiateRate")); //Э���������
		saci.setNegotiateRateDate(rs.getTimestamp("AC_dtNegotiateRate"));
		//Э�����������Ч����
		saci.setNegotiateInterest(rs.getDouble("AC_MNEGOTIATEINTEREST"));
		// Э�������Ϣ
		saci.setInterestRatePlanDate(rs.getTimestamp("AC_dtInterestRatePlan"));
		saci.setNegotiationStartDate(rs.getTimestamp("AC_dtNegotiationStartDate")); //Э�������ʼ����
		saci.setNegotiationEndDate(rs.getTimestamp("AC_dtNegotiationEndDate"));//Э�������ֹ����
		//��Ϣ��Ч��
		saci.setDailyUncheckAmount(rs.getDouble("MUNCHECKPAYMENTAMOUNT"));
		// �ۼ�δ���˸�����
		saci.setMonthLimitAmount(rs.getDouble("AC_MMONTHLIMITAMOUNT"));
		// ���ۼ�δ���˸�����
		saci.setDayLimitAmount(rs.getDouble("AC_MDAYLIMITAMOUNT"));
		// �¶��ۼ�֧�����ƽ��
		saci.setIsAllBranch(rs.getLong("AC_nIsAllBranch"));
		//�Ƿ��������п����� add by rxie
		//��������
		saci.setPreDrawDate(rs.getTimestamp("AL_dtPreDraw"));
		//������Ϣ
		saci.setDrawInterest(rs.getDouble("AL_mPreDrawInterest"));
	}
	private void getInfoFromResultSet(SubAccountFixedInfo safi, ResultSet rs) throws SQLException
	{
		safi.setID(rs.getLong("ID")); //���˻�ID
		safi.setAccountID(rs.getLong("nAccountID")); //���˻�ID
		safi.setInterest(rs.getDouble("mInterest")); //��ǰ��Ϣ
		safi.setBalance(rs.getDouble("mBalance")); //�˻���ǰ���
		safi.setOpenAmount(rs.getDouble("mOpenAmount")); //�������
		safi.setOpenDate(rs.getTimestamp("dtOpen")); //��������
		safi.setFinishDate(rs.getTimestamp("dtFinish")); //�廧����
		safi.setIsInterest(rs.getLong("nIsInterest")); //�Ƿ��Ϣ
		safi.setClearInterestDate(rs.getTimestamp("dtClearInterest")); //��Ϣ����
		safi.setStatusID(rs.getLong("nStatusID")); //״̬
		safi.setDailyUncheckAmount(rs.getDouble("MUNCHECKPAYMENTAMOUNT"));
		// �ۼ�δ���˸�����
		safi.setDepositNo(rs.getString("AF_sDepositNo")); //�浥��
		safi.setRate(rs.getDouble("AF_mRate")); //����
		safi.setStartDate(rs.getTimestamp("AF_dtStart")); //��ʼ����
		safi.setEndDate(rs.getTimestamp("AF_dtEnd")); //��������
		safi.setPreDrawInterest(rs.getDouble("AF_mPreDrawInterest")); //������Ϣ
		safi.setPreDrawDate(rs.getTimestamp("AF_dtPreDraw")); //��������
		safi.setDepositTerm(rs.getLong("AF_nDepositTerm")); //���ڴ�����ޣ��£�
		safi.setInterestPlanID(rs.getLong("AF_nInterestPlanID")); //���ʼƻ�
		safi.setNoticeDay(rs.getLong("AF_nNoticeDay")); //֪ͨ���֧ȡ���ڣ��죩
		safi.setInterestAccountID(rs.getLong("AF_nInterestAccountID"));
		//����Ϣ�˻�ID
		safi.setSealNo(rs.getString("AF_sSealNo")); //ӡ������
		safi.setSealBankID(rs.getLong("AF_nSealBankID")); //ӡ������������ID
		//�Զ�������Ϣ
		safi.setIsAutoContinue(rs.getLong("AF_IsAutoContinue"));
		safi.setAutoContinueType(rs.getLong("AF_AutoContinueType"));
	}
	private void getInfoFromResultSet(SubAccountLoanInfo sali, ResultSet rs) throws SQLException
	{
		int i = 0;
		sali.setID(rs.getLong("ID")); //���˻�ID
		sali.setAccountID(rs.getLong("nAccountID")); //���˻�ID
		sali.setInterest(rs.getDouble("mInterest")); //��ǰ��Ϣ
		sali.setBalance(rs.getDouble("mBalance")); //�˻���ǰ���
		sali.setOpenAmount(rs.getDouble("mOpenAmount")); //�������
		sali.setOpenDate(rs.getTimestamp("dtOpen")); //��������
		sali.setFinishDate(rs.getTimestamp("dtFinish")); //�廧����
		sali.setIsInterest(rs.getLong("nIsInterest")); //�Ƿ��Ϣ
		//
		sali.setStatusID(rs.getLong("nStatusID")); //״̬
		sali.setLoanNoteID(rs.getLong("AL_nLoanNoteID")); //�ſ��
		sali.setIsCycLoan(rs.getLong("AL_nIsCycLoan")); //�Ƿ�ѭ������
		sali.setClearInterestDate(rs.getTimestamp("dtClearInterest")); //��Ϣ����
		sali.setCalculateInterestDate(rs.getTimestamp("AL_dtCalculateInterest"));
		//��Ϣ����
		sali.setPayInterestAccountID(rs.getLong("AL_nPayInterestAccountID"));
		//�����Ϣ�˻�ID
		sali.setReceiveInterestAccountID(rs.getLong("AL_nReceiveInterestAccountID"));
		//ί�з���Ϣ�˻�ID
		sali.setPreDrawInterest(rs.getDouble("AL_mPreDrawInterest")); //������Ϣ
		sali.setPreDrawDate(rs.getTimestamp("AL_dtPreDraw")); //��������
		sali.setPaySuretyAccountID(rs.getLong("AL_nPaySuretyAccountID"));
		//�������˻�ID
		sali.setReceiveSuretyAccountID(rs.getLong("AL_nReceiveSuretyAccountID"));
		//�������˻�ID
		sali.setCommissionAccountID(rs.getLong("AL_nCommissionAccountID"));
		//�������˻�ID
		sali.setSuretyFee(rs.getDouble("AL_mSuretyFee")); //������
		sali.setClearSureFeeDate(rs.getTimestamp("AL_dtClearSureFee"));
		//�����ѽ�Ϣ����
		sali.setCommission(rs.getDouble("AL_mCommission")); //������
		sali.setClearCommissionDate(rs.getTimestamp("AL_DTCLEARCOMMISSION"));
		//�����ѽ�Ϣ����
		sali.setInterestTaxAccountID(rs.getLong("AL_nInterestTaxAccountID"));
		//��Ϣ˰���˻�ID
		sali.setInterestTax(rs.getDouble("AL_mInterestTax"));
		sali.setInterestTaxRate(rs.getDouble("AL_mInterestTaxRate")); //��Ϣ˰����
		sali.setInterestTaxRatePlanID(rs.getLong("AL_nInterestTaxRatePlanID"));//��Ϣ˰���ʼƻ�
		sali.setClearInterestTaxDate(rs.getTimestamp("AL_dtClearInterestTax"));
		sali.setEffectiveTaxDate(rs.getTimestamp("AL_dtEffectiveTax")); //��Ч����
		sali.setOverDueAccountID(rs.getLong("AL_nOverDueAccountID")); //��Ϣ�˻�ID
		sali.setOverDueInterest(rs.getDouble("AL_mOverDueInterest"));
		sali.setClearOverDueDate(rs.getTimestamp("AL_dtClearOverDue"));
		sali.setCompoundAccountID(rs.getLong("AL_nCompoundAccountID")); //�����˻���
		sali.setCompoundInterest(rs.getDouble("AL_mCompoundInterest"));
		sali.setClearCompoundDate(rs.getTimestamp("AL_dtClearCompound"));
		sali.setDailyUncheckAmount(rs.getDouble("MUNCHECKPAYMENTAMOUNT"));
		// �ۼ�δ���˸�����
		sali.setArrearageInterest(rs.getDouble("AL_mArrearageInterest"));
		//ǷϢ���
		sali.setConsignAccountID(rs.getLong("AL_nConsignAccountID")); //ί�д���˻���
		sali.setOverDueArrearageInterest(rs.getDouble("AL_mOverDueArrearageInterest"));
		//����ǷϢ
	}
	/**
	 * ��ѯ�����˻�״̬ AccountStatus <>SETTConstant.AccountStatus.CLOSE �廧�ļ�¼
	 * 
	 * @author hjliu 2003-11-11
	 * 
	 * @param conn
	 *            ���ݿ����ӣ��ɷ����ĵ������ṩ��never null.
	 * @param officeID
	 *            ���˻��а��´�ID
	 * @param currencyID
	 *            ���˻�����ID
	 * @return Vector SubAccountInfo�ļ���
	 */
	public Collection findByStatus(long officeID, long currencyID) throws Exception
	{
		Vector subAccountVector = new Vector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			conn = getConnection();
			bufferSQL = new StringBuffer();
			bufferSQL.append(" select sub.* from SETT_SUBACCOUNT sub,SETT_ACCOUNT account \n");
			bufferSQL.append(" where account.ID = sub.nACCOUNTID \n");
			bufferSQL.append(" and \n");
			bufferSQL.append(" account.nOFFICEID = \n");
			bufferSQL.append(officeID + " \n");
			bufferSQL.append(" and  \n");
			bufferSQL.append(" account.nCURRENCYID = \n");
			bufferSQL.append(currencyID + " \n");
			bufferSQL.append(" and \n");
			bufferSQL.append(" sub.nSTATUSID <> \n");
			bufferSQL.append(SETTConstant.SubAccountStatus.FINISH); //�˻��������廧����
			//log4j.info(bufferSQL.toString());
			String sqlString = bufferSQL.toString();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlString);
			while (rset.next())
			{
				subAccountAssemblerInfo = new SubAccountAssemblerInfo();
				//append one BankBillInfo to the LinkedList object
				getInfoFromResultSet(subAccountAssemblerInfo, rset);
				subAccountVector.add(subAccountAssemblerInfo);
			}
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		return subAccountVector;
	}
	private void getInfoFromResultSet(SubAccountAssemblerInfo saai, ResultSet rs) throws SQLException
	{
		saai.setSubAccountCurrentInfo(new SubAccountCurrentInfo());
		saai.setSubAccountFixedInfo(new SubAccountFixedInfo());
		saai.setSubAccountLoanInfo(new SubAccountLoanInfo());
		//log.debug("getInfoFromResultSet--current");
		getInfoFromResultSet(saai.getSubAccountCurrenctInfo(), rs);
		//log.debug("getInfoFromResultSet--fix");
		getInfoFromResultSet(saai.getSubAccountFixedInfo(), rs);
		//log.debug("getInfoFromResultSet--loan");
		getInfoFromResultSet(saai.getSubAccountLoanInfo(), rs);
	}
	/**
	 * Ϊ�ػ�ʹ��
	 * 
	 * @param saci
	 * @param ps
	 * @param lTag
	 * @throws SQLException
	 */
	private void setPrepareStatementByInfo_2(SubAccountCurrentInfo saci, PreparedStatement ps, long lTag) throws SQLException
	{
		int i = 1;
		if (lTag > 0)
		{
			ps.setLong(i++, saci.getID());
		}
		ps.setLong(i++, saci.getAccountID()); // ���˻�ID
		ps.setDouble(i++, saci.getInterest()); // ��ǰ��Ϣ
		ps.setDouble(i++, saci.getOpenAmount()); //�������
		ps.setTimestamp(i++, saci.getOpenDate()); //��������
		ps.setTimestamp(i++, saci.getFinishDate()); //�廧����
		ps.setLong(i++, saci.getIsInterest()); //�Ƿ��Ϣ
		ps.setTimestamp(i++, saci.getClearInterestDate()); //��Ϣ����
		ps.setLong(i++, saci.getStatusID()); // ״̬
		ps.setLong(i++, saci.getInterestAccountID()); // ����Ϣ�˻�ID
		ps.setLong(i++, saci.getIsOverDraft()); // �Ƿ�����͸֧
		ps.setLong(i++, saci.getFirstLimitTypeID()); // ��һ��͸֧����
		ps.setDouble(i++, saci.getFirstLimitAmount()); // ͸֧���
		ps.setLong(i++, saci.getSecondLimitTypeID());
		ps.setDouble(i++, saci.getSecondLimitAmount());
		ps.setLong(i++, saci.getThirdLimitTypeID());
		ps.setDouble(i++, saci.getThirdLimitAmount());
		ps.setLong(i++, saci.getInterestRatePlanID()); // ���ʼƻ�ID
		ps.setTimestamp(i++, saci.getInterestRatePlanDate()); // ���ʼƻ���Ч����
		ps.setDouble(i++, saci.getCapitalLimitAmount()); //�ʽ����ƽ��
		ps.setLong(i++, saci.getIsNegotiate()); //�Ƿ�Э�����
		ps.setDouble(i++, saci.getNegotiateAmount()); //Э�����
		ps.setDouble(i++, saci.getNegotiateUnit()); //Э����λ��Ԫ��
		ps.setDouble(i++, saci.getNegotiateRate()); //Э���������
		ps.setTimestamp(i++, saci.getNegotiateRateDate()); // Э�����������Ч����
		ps.setDouble(i++, saci.getMonthLimitAmount());
		ps.setDouble(i++, saci.getNegotiateInterest()); // Э�������Ϣ
		if (lTag < 0)
		{
			ps.setLong(i++, saci.getID());
		}
	}
	private void setPrepareStatementByInfo(SubAccountCurrentInfo saci, PreparedStatement ps, long lTag) throws SQLException
	{
		int i = 1;
		if (lTag > 0)
		{
			ps.setLong(i++, saci.getID());
		}
		ps.setLong(i++, saci.getAccountID()); // ���˻�ID
		ps.setDouble(i++, saci.getInterest()); // ��ǰ��Ϣ
		ps.setDouble(i++, saci.getOpenAmount()); //�������
		//ps.setTimestamp(i++, saci.getOpenDate()); //��������
		//ps.setTimestamp(i++, saci.getFinishDate()); //�廧����
		ps.setLong(i++, saci.getIsInterest()); //�Ƿ��Ϣ
		//ps.setTimestamp(i++, saci.getClearInterestDate()); //��Ϣ����
		if (lTag > 0)
		{
			ps.setLong(i++, SETTConstant.SubAccountStatus.NORMAL); //״̬
		}
		else
		{
			ps.setLong(i++, saci.getStatusID()); //״̬
		}
		ps.setLong(i++, saci.getInterestAccountID()); // ����Ϣ�˻�ID
		ps.setLong(i++, saci.getIsOverDraft()); // �Ƿ�����͸֧
		ps.setLong(i++, saci.getFirstLimitTypeID()); // ��һ��͸֧����
		ps.setDouble(i++, saci.getFirstLimitAmount()); // ͸֧���
		ps.setLong(i++, saci.getSecondLimitTypeID());
		ps.setDouble(i++, saci.getSecondLimitAmount());
		ps.setLong(i++, saci.getThirdLimitTypeID());
		ps.setDouble(i++, saci.getThirdLimitAmount());
		ps.setLong(i++, saci.getInterestRatePlanID()); // ���ʼƻ�ID
		//ps.setTimestamp(i++, saci.getInterestRatePlanDate()); // ���ʼƻ���Ч����
		ps.setDouble(i++, saci.getCapitalLimitAmount()); //�ʽ����ƽ��
		ps.setLong(i++, saci.getIsNegotiate()); //�Ƿ�Э�����
		ps.setDouble(i++, saci.getNegotiateAmount()); //Э�����
		log.debug("�������˻��޸ģ� ��Ϣ��Ϣ�� Э�����:NegotiateAmount<-->����ǷϢ : " + saci.getNegotiateAmount());
		ps.setDouble(i++, saci.getNegotiateUnit()); //Э����λ��Ԫ��
		log.debug("�������˻��޸ģ�������Ϣ��Э����λ:NegotiateUnit<-->����ǷϢ : " + saci.getNegotiateUnit());
		ps.setDouble(i++, saci.getNegotiateRate()); //Э���������
		//ps.setTimestamp(i++, saci.getNegotiateRateDate()); // Э�����������Ч����
		ps.setDouble(i++, saci.getMonthLimitAmount());
		ps.setDouble(i++, saci.getDayLimitAmount());
		ps.setDouble(i++, saci.getNegotiateInterest()); // Э�������Ϣ
		ps.setLong(i++, saci.getIsAllBranch()); //�Ƿ��������п����� add by rxie
		if (lTag < 0)
		{
			ps.setLong(i++, saci.getID());
		}
	}
	private void setPrepareStatementByInfo(SubAccountFixedInfo safi, PreparedStatement ps, long lTag) throws SQLException
	{
		int i = 1;
		//			'ID', 'NACCOUNTID','MINTEREST','MBALANCE','NSTATUSID',
		//			'AF_SDEPOSITNO', 'AF_MDEPOSITAMOUNT', 'AF_MRATE', 'AF_DTSTART',
		// 'AF_DTEND',
		//			'AF_NISINTEREST','AF_MPREDRAWINTEREST', 'AF_DTPREDRAW',
		// 'AF_NDEPOSITTERM',
		//			'AF_NINTERESTPLANID','AF_NNOTICEDAY','AF_NINTERESTACCOUNTID','AF_SSEALNO','AF_NSEALBANKID'
		//						
		if (lTag > 0)
		{
			ps.setLong(i++, safi.getID());
		}
		ps.setLong(i++, safi.getAccountID()); //���˻�ID
		ps.setDouble(i++, safi.getInterest()); //��ǰ��Ϣ
		ps.setDouble(i++, safi.getBalance()); //�˻���ǰ���
		ps.setDouble(i++, safi.getOpenAmount()); //�������
		ps.setTimestamp(i++, safi.getOpenDate()); //��������
		ps.setTimestamp(i++, safi.getFinishDate()); //�廧����
		ps.setLong(i++, safi.getIsInterest()); //�Ƿ��Ϣ
		ps.setTimestamp(i++, safi.getClearInterestDate()); //��Ϣ����
		if (lTag > 0)
		{
			ps.setLong(i++, SETTConstant.SubAccountStatus.NORMAL); //״̬
		}
		else
		{
			ps.setLong(i++, safi.getStatusID()); //״̬
		}
		ps.setString(i++, safi.getDepositNo()); //�浥��
		ps.setDouble(i++, safi.getRate()); //����
		ps.setTimestamp(i++, safi.getStartDate()); //��ʼ����
		ps.setTimestamp(i++, safi.getEndDate()); //��������
		ps.setDouble(i++, safi.getPreDrawInterest()); //������Ϣ
		log.debug("�������˻��޸ģ���Ϣ��Ϣ��Э����λ:������Ϣ<-->����ǷϢ : " + safi.getPreDrawInterest());
		ps.setTimestamp(i++, safi.getPreDrawDate()); //��������
		ps.setLong(i++, safi.getDepositTerm()); //���ڴ�����ޣ��£�
		ps.setLong(i++, safi.getInterestPlanID()); //���ʼƻ�
		ps.setLong(i++, safi.getNoticeDay()); //֪ͨ���֧ȡ���ڣ��죩
		ps.setLong(i++, safi.getInterestAccountID()); //����Ϣ�˻�ID
		ps.setString(i++, safi.getSealNo()); //ӡ������
		ps.setLong(i++, safi.getSealBankID()); //ӡ������������ID
		ps.setLong(i++, safi.getIsAutoContinue());//�Զ������־
		ps.setLong(i++, safi.getAutoContinueType());//�Զ������־
		if (lTag < 0)
		{
			ps.setLong(i++, safi.getID());
		}
	}
	private void setPrepareStatementByInfo(SubAccountLoanInfo sali, PreparedStatement ps, long lTag) throws SQLException
	{
		int i = 1;
		if (lTag > 0)
		{
			ps.setLong(i++, sali.getID());
		}
		ps.setLong(i++, sali.getAccountID()); //���˻�ID
		ps.setDouble(i++, sali.getInterest()); //��ǰ��Ϣ
		ps.setDouble(i++, sali.getBalance()); //�˻���ǰ���
		ps.setDouble(i++, sali.getOpenAmount()); //�������
		ps.setTimestamp(i++, sali.getOpenDate()); //��������
		ps.setTimestamp(i++, sali.getFinishDate()); //�廧����
		ps.setLong(i++, sali.getIsInterest()); //�Ƿ��Ϣ
		//ps.setTimestamp(i++, sali.getClearInterestDate()); //��Ϣ����
		if (lTag > 0)
		{
			ps.setLong(i++, SETTConstant.SubAccountStatus.NORMAL); //״̬
		}
		else
		{
			ps.setLong(i++, sali.getStatusID()); //״̬
		}
		ps.setLong(i++, sali.getLoanNoteID()); //�ſ��
		ps.setLong(i++, sali.getIsCycLoan()); //�Ƿ�ѭ������
		ps.setTimestamp(i++, sali.getClearInterestDate()); //��Ϣ����
		ps.setTimestamp(i++, sali.getCalculateInterestDate()); //��Ϣ����
		ps.setLong(i++, sali.getPayInterestAccountID()); //�����Ϣ�˻�ID
		ps.setLong(i++, sali.getReceiveInterestAccountID()); //ί�з���Ϣ�˻�ID
		ps.setDouble(i++, sali.getPreDrawInterest()); //������Ϣ
		ps.setTimestamp(i++, sali.getPreDrawDate()); //��������
		ps.setLong(i++, sali.getPaySuretyAccountID()); //�������˻�ID
		ps.setLong(i++, sali.getReceiveSuretyAccountID()); //�������˻�ID
		ps.setLong(i++, sali.getCommissionAccountID()); //�������˻�ID
		ps.setDouble(i++, sali.getSuretyFee()); //������
		ps.setTimestamp(i++, sali.getClearSureFeeDate()); //�����ѽ�Ϣ����
		ps.setDouble(i++, sali.getCommission()); //������
		ps.setTimestamp(i++, sali.getClearCommissionDate()); //�����ѽ�Ϣ����
		ps.setLong(i++, sali.getInterestTaxAccountID()); //��Ϣ˰���˻�ID
		ps.setDouble(i++, sali.getInterestTax());
		ps.setDouble(i++, sali.getInterestTaxRate()); //��Ϣ˰����
		ps.setTimestamp(i++, sali.getClearInterestTaxDate());
		ps.setTimestamp(i++, sali.getEffectiveTaxDate()); //��Ч����
		ps.setLong(i++, sali.getOverDueAccountID()); //��Ϣ�˻�ID
		ps.setDouble(i++, sali.getOverDueInterest());
		ps.setTimestamp(i++, sali.getClearOverDueDate());
		ps.setLong(i++, sali.getCompoundAccountID()); //�����˻���
		ps.setDouble(i++, sali.getCompoundInterest());
		ps.setTimestamp(i++, sali.getClearCompoundDate());
		ps.setDouble(i++, sali.getArrearageInterest()); //ǷϢ���
		ps.setLong(i++, sali.getConsignAccountID()); //ί�д���˻���
		ps.setDouble(i++, sali.getOverDueArrearageInterest()); //����ǷϢ
		ps.setLong(i++,sali.getInterestTaxRatePlanID());//��Ϣ˰���ʼƻ�ID
		if (lTag < 0)
		{
			ps.setLong(i++, sali.getID());
		}
	}
	/**
	 * Ϊ�ػ�ʹ��
	 * 
	 * @param sali
	 * @param ps
	 * @param lTag
	 * @throws SQLException
	 */
	private void setPrepareStatementByInfo_2(SubAccountLoanInfo sali, PreparedStatement ps, long lTag) throws SQLException
	{
		int i = 1;
		if (lTag > 0)
		{
			ps.setLong(i++, sali.getID());
		}
		ps.setLong(i++, sali.getAccountID()); //���˻�ID
		ps.setDouble(i++, sali.getInterest()); //��ǰ��Ϣ
		ps.setDouble(i++, sali.getBalance()); //�˻���ǰ���
		ps.setDouble(i++, sali.getOpenAmount()); //�������
		ps.setTimestamp(i++, sali.getOpenDate()); //��������
		ps.setTimestamp(i++, sali.getFinishDate()); //�廧����
		ps.setLong(i++, sali.getIsInterest()); //�Ƿ��Ϣ
		//ps.setTimestamp(i++, sali.getClearInterestDate()); //��Ϣ����
		ps.setLong(i++, sali.getStatusID()); //״̬
		ps.setLong(i++, sali.getLoanNoteID()); //�ſ��
		ps.setLong(i++, sali.getIsCycLoan()); //�Ƿ�ѭ������
		ps.setTimestamp(i++, sali.getClearInterestDate()); //��Ϣ����
		ps.setTimestamp(i++, sali.getCalculateInterestDate()); //��Ϣ����
		ps.setLong(i++, sali.getPayInterestAccountID()); //�����Ϣ�˻�ID
		ps.setLong(i++, sali.getReceiveInterestAccountID()); //ί�з���Ϣ�˻�ID
		ps.setDouble(i++, sali.getPreDrawInterest()); //������Ϣ
		ps.setTimestamp(i++, sali.getPreDrawDate()); //��������
		ps.setLong(i++, sali.getPaySuretyAccountID()); //�������˻�ID
		ps.setLong(i++, sali.getReceiveSuretyAccountID()); //�������˻�ID
		ps.setLong(i++, sali.getCommissionAccountID()); //�������˻�ID
		ps.setDouble(i++, sali.getSuretyFee()); //������
		ps.setTimestamp(i++, sali.getClearSureFeeDate()); //�����ѽ�Ϣ����
		ps.setDouble(i++, sali.getCommission()); //������
		ps.setTimestamp(i++, sali.getClearCommissionDate()); //�����ѽ�Ϣ����
		ps.setLong(i++, sali.getInterestTaxAccountID()); //��Ϣ˰���˻�ID
		ps.setDouble(i++, sali.getInterestTax());
		ps.setDouble(i++, sali.getInterestTaxRate()); //��Ϣ˰����
		ps.setTimestamp(i++, sali.getClearInterestTaxDate());
		ps.setTimestamp(i++, sali.getEffectiveTaxDate()); //��Ч����
		ps.setLong(i++, sali.getOverDueAccountID()); //��Ϣ�˻�ID
		ps.setDouble(i++, sali.getOverDueInterest());
		ps.setTimestamp(i++, sali.getClearOverDueDate());
		ps.setLong(i++, sali.getCompoundAccountID()); //�����˻���
		ps.setDouble(i++, sali.getCompoundInterest());
		ps.setTimestamp(i++, sali.getClearCompoundDate());
		ps.setDouble(i++, sali.getArrearageInterest()); //ǷϢ���
		ps.setLong(i++, sali.getConsignAccountID()); //ί�д���˻���
		ps.setDouble(i++, sali.getOverDueArrearageInterest()); //����ǷϢ
		if (lTag < 0)
		{
			ps.setLong(i++, sali.getID());
		}
	}
	////////////////////////////////////////
	/**
	 * ����˵���������˻�ID���õ����˻���Ϣ
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	public SubAccountAssemblerInfo findByAccountID(long lAccountID) throws SQLException
	{
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			sbSQL.append(" where nAccountID = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountAssemblerInfo();
				getInfoFromResultSet(saai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}
	/**
	 * �����˻�ID�õ����˻���Ϣ
	 * @param lAccountID
	 * @return
	 * @throws SQLException
	 */
	public SubAccountCurrentInfo findByAccountID1(long lAccountID) throws SQLException
	{
		SubAccountCurrentInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			sbSQL.append(" where nAccountID = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountCurrentInfo();
				getInfoFromResultSet(saai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}
	/**
	 * ����˵�������ݱ�֤���˻�ID���õ���֤��浥����Ϣ�˻�
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	public long getReceiveInterestAccountByMarginAccountID(long lAccountID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long receiverInterestAccountID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct nvl(AC_nInterestAccountID,-1)  InterestAccountID from sett_SubAccount ");
			sbSQL.append(" where nAccountID = ? and AC_nInterestAccountID > 0  and nStatusID <> 0 ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				receiverInterestAccountID = rs.getLong("InterestAccountID");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return receiverInterestAccountID;
	}
	
	/**
	 * ����ָ�������˻�id��ѯ���е����˻�id
	 * 
	 * @param lAccountID
	 * @return long[]
	 * @throws SQLException
	 */
	public long[] findSubAccountIDByAccountID(long lAccountID) throws SQLException
	{
		long[] result = null;
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select id from sett_SubAccount ");
			sbSQL.append(" where nAccountID = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			ArrayList alTemp = new ArrayList(8);
			while (rs.next())
			{
				alTemp.add(new Long(rs.getLong("id")));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			result = new long[alTemp.size()];
			for (int i = 0; i < alTemp.size(); i++)
			{
				result[i] = ((Long) alTemp.get(i)).longValue();
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return result;
	}
	////////////////////////////////////////
	/**
	 * ����˵���������˻�ID�Ͷ��ڴ浥�ţ��õ����˻���Ϣ
	 * 
	 * @param lAccountID
	 * @param strDepositNo
	 * @return AccountInfo
	 * @throws Exception
	 */
	public SubAccountAssemblerInfo findByFixedDepositNo(long lAccountID, String strDepositNo) throws SQLException
	{
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			sbSQL.append(" where AF_sDepositNo = " + "'" + strDepositNo + "' ");
			sbSQL.append(" AND nAccountID = " + lAccountID);
			
			//sbSQL.append(" AND nstatusid = " + SETTConstant.AccountStatus.NORMAL);
			sbSQL.append(" AND nstatusid in (" + SETTConstant.SubAccountStatus.NORMAL+","+SETTConstant.SubAccountStatus.ALLFREEZE+","+SETTConstant.SubAccountStatus.REPORTLOSS+") "); 
			//2004-11-27 ����
			//sbSQL.append(" AND nstatusid > 0 " );
			//sbSQL.append(" where AF_sDepositNo=? and nAccountID = ? and
			// nstatusid = ?");
			//log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			//ps.setString(1, strDepositNo);
			//ps.setLong(2, lAccountID);
			//ps.setLong(3, );
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountAssemblerInfo();
				getInfoFromResultSet(saai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}
	////////////////////////////////////////
	/**
	 * ����˵���������˻�ID�ʹ���֪ͨ���ţ��õ����˻���Ϣ
	 * 
	 * @param lAccountID
	 * @param strDepositNo
	 * @return AccountInfo
	 * @throws Exception
	 */
	public SubAccountAssemblerInfo findByLoanNoteID(long lAccountID, long loanNoteID) throws SQLException
	{
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			if (lAccountID > 0)
				sbSQL.append(" where AL_NLOANNOTEID=? and nAccountID = ? and nstatusid = 1");
			else
				sbSQL.append(" where AL_NLOANNOTEID=? and nstatusid = 1");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, loanNoteID);
			if (lAccountID > 0)
				ps.setLong(2, lAccountID);
			//ps.setLong(3, SETTConstant.AccountStatus.NORMAL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountAssemblerInfo();
				getInfoFromResultSet(saai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}
	
	/**
	 * ����˵���������˻�ID�ʹ���֪ͨ���ţ��õ����˻���Ϣ(ת����������Ϸ���ר�ã��������������ɻ�Ʒ�¼֮ǰ���˻��ѽ���)
	 * 
	 * @param lAccountID
	 * @param strDepositNo
	 * @return AccountInfo
	 * @throws Exception
	 */
	public SubAccountAssemblerInfo findByLoanNoteID1(long lAccountID, long loanNoteID) throws SQLException
	{
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			if (lAccountID > 0)
				sbSQL.append(" where AL_NLOANNOTEID=? and nAccountID = ? ");//and nstatusid = 2"); ת���ַ����������ϣ�����ʱ�����޷���ú��ʵĻ�ƿ�Ŀ,����ʧ��
			else
				sbSQL.append(" where AL_NLOANNOTEID=? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, loanNoteID);
			if (lAccountID > 0)
				ps.setLong(2, lAccountID);
			//ps.setLong(3, SETTConstant.AccountStatus.NORMAL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountAssemblerInfo();
				getInfoFromResultSet(saai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}	
	
/**
 * added by mzh_fu 2007/08/08
 * @param lAccountID
 * @param loanNoteID
 * @param lStatus
 * @return
 * @throws SQLException
 */
	public SubAccountAssemblerInfo findByLoanNoteIDAndStatus(long lAccountID, long loanNoteID,long lStatus) throws SQLException
	{
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			if (lAccountID > 0)
				sbSQL.append(" where AL_NLOANNOTEID=? and nstatusid >0 and nAccountID = ? ");
			else
				sbSQL.append(" where AL_NLOANNOTEID=? and nstatusid >0 ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, loanNoteID);
//			ps.setLong(2, lStatus);
			
			if (lAccountID > 0)
				ps.setLong(2, lAccountID);
			
			//ps.setLong(3, SETTConstant.AccountStatus.NORMAL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountAssemblerInfo();
				getInfoFromResultSet(saai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}
	
	public long updateUncheckPaymentAmount(long lSubAccountID, double dPaymentAmount) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set MUNCHECKPAYMENTAMOUNT = MUNCHECKPAYMENTAMOUNT+? where id=? \n");
			//log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, dPaymentAmount);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}

	/**
	 * ͨ�����˻�ID �� ��ͬID ��ѯ����Ӧ�� ���˻�ID�����˻����
	 * @param lAccountID
	 * @return
	 * @throws Exception
	 */
	public Collection findByConditions4Recog(long lAccountID,long contractid) throws Exception {
		Vector v = new Vector();
		
		Connection        conn = null;
		ResultSet         rs   = null;
		PreparedStatement ps   = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.id as ID ,a.mbalance as mBalance from Sett_Subaccount a ,loan_assurechargeform c where a.al_nloannoteid = c.id ");
			sql.append(" and  c.contractid = "+ contractid);
			sql.append(" AND a.nstatusid in (" + SETTConstant.SubAccountStatus.NORMAL+","+SETTConstant.SubAccountStatus.ALLFREEZE+","+SETTConstant.SubAccountStatus.REPORTLOSS+") "); 
            System.out.println("***************SQL:"+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				SubAccountCurrentInfo ai = new SubAccountCurrentInfo();
				ai.setID(rs.getLong("ID"));
				ai.setBalance(rs.getDouble("mBalance"));
				v.add(ai);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}
	
	/**
	 * ͨ�����˻�ID �� ��ͬID ��ѯ����Ӧ�� ���˻�ID�����˻���� ȡ��������
	 * @param lAccountID
	 * @return
	 * @throws Exception
	 */
	public Collection findByConditions4RecogCancelCheck(long lAccountID,long contractid) throws Exception {
		Vector v = new Vector();
		
		Connection        conn = null;
		ResultSet         rs   = null;
		PreparedStatement ps   = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.id as ID ,a.mbalance as mBalance from Sett_Subaccount a ,loan_assurechargeform c where a.al_nloannoteid = c.id ");
			sql.append(" and  c.contractid = "+ contractid);
            System.out.println("***************SQL:"+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				SubAccountCurrentInfo ai = new SubAccountCurrentInfo();
				ai.setID(rs.getLong("ID"));
				ai.setBalance(rs.getDouble("mBalance"));
				v.add(ai);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}
	
	
	
	public long saveNegotiateCurrentAccount(QueryAccountWhereInfo qaci) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set AC_MNEGOTIATERATE = ? where NACCOUNTID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, qaci.getNegotiateRate());
			ps.setLong(2, qaci.getAccountID());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	public long updateAccountBalance(long lSubAccountID, double dAmount) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set mBalance = mBalance+? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, dAmount);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	public long updateStatus(long lSubAccountID, long lStatusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set NSTATUSID = ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, lStatusID);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	///����֤��
	public long updateFixedForm(long lSubAccountID, String strFixedForm) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set af_sDepositNo = ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, strFixedForm);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	//�����տ�֪ͨ��ID����֤�����˻�
	public long updateLoanNoteIdToSubMargin(long lSubAccountID, long lLoanNoteID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set AL_nLoanNoteID = ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lLoanNoteID);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	
	//������Ϣ�����˻�ID����֤�����˻�
	public long updateInterestAccountIDoSubMargin(long lSubAccountID, long lInterestAccountID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set AC_nInterestAccountID = ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lInterestAccountID);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	///��������޶�
	public long addCapitalLimitAmount(long lSubAccountID, double dCapitalLimitAmount) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set Ac_mCapitalLimitAmount = nvl(Ac_mCapitalLimitAmount,0) + ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, dCapitalLimitAmount);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	public long reduceCapitalLimitAmount(long lSubAccountID, double dCapitalLimitAmount) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set Ac_mCapitalLimitAmount = nvl(Ac_mCapitalLimitAmount,0) - ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, dCapitalLimitAmount);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 *���ļ�����Ϣ���������(������ͨ��Ŀ) 
	 */
	public long updatePreDrawInterestAndDateForICBC(long lSubAccountID, double dPreDrawInterest, Timestamp PreDrawDate, long lAccountTypeID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
			        ||SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID))
			{
				sbSQL.append(" update sett_SubAccount set Al_mPreDrawInterest = nvl(Al_mPreDrawInterest,0) + " + dPreDrawInterest + " \n");
				sbSQL.append(" ,Al_dtPreDraw=to_Date('"+ DataFormat.getDateString(PreDrawDate)+ "','yyyy-mm-dd')");
				sbSQL.append(" ,mInterest = 0,ac_mNegotiateInterest = 0 \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	/**
	 *��Ϣ��������˻���Ϣ(������ͨ��Ŀ) 
	 */
	public long updateDepositInterestForICBC(long lSubAccountID,Timestamp dtSettlementDate, long lAccountTypeID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
			        ||SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID))
			{
				sbSQL.append(" update sett_SubAccount set  \n");
				sbSQL.append(" dtClearInterest=to_Date('"+ DataFormat.getDateString(dtSettlementDate)+ "','yyyy-mm-dd')");
				sbSQL.append(" ,mInterest = 0,ac_mNegotiateInterest = 0 \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	//���ļ�����Ϣ���������
	public long updatePreDrawInterestAndDate(long lSubAccountID, double dPreDrawInterest, Timestamp PreDrawDate, long lAccountTypeID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			
			//�Ƿ����˻�������
			if ( SETTConstant.AccountType.isFixAccountType(lAccountTypeID) )
			{
				//AF_mPreDrawInterest  ������Ϣ
				//AF_dtPreDraw  ��������
				sbSQL.append(" update sett_SubAccount set AF_mPreDrawInterest = " + dPreDrawInterest + " \n" );
				sbSQL.append(" ,AF_dtPreDraw =  \n" );
				sbSQL.append(" decode(sign(to_Date('"+ DataFormat.getDateString(PreDrawDate)+ "','yyyy-mm-dd')-AF_DTEND),1,AF_DTEND,-1,to_Date(' "
													 + DataFormat.getDateString(PreDrawDate)+ "','yyyy-mm-dd'),0,AF_DTEND) ");
				//decode(sign(to_Date('"+DataFomat.getDateString(ts)+'','yyyy-mm-dd')-AF_DTEND),1,AF_DTEND,-1,to_Date('2003-10-13','yyyy-mm-dd'),0,AF_DTEND)
				sbSQL.append(" where ID = " + lSubAccountID );
			}
			
			//֪ͨ���û��AF_DTEND�������� 2007��12��5�� Modify Boxu
			else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
			{
				//AF_mPreDrawInterest  ������Ϣ
				//AF_dtPreDraw  ��������
				sbSQL.append(" update sett_SubAccount set AF_mPreDrawInterest = " + dPreDrawInterest + " \n" );
				sbSQL.append(" ,AF_dtPreDraw =  \n" );
				sbSQL.append(" decode(sign(to_Date('"+ DataFormat.getDateString(PreDrawDate)+ "','yyyy-mm-dd')-dtfinish)" 
													 + " , 1, to_Date('"+DataFormat.getDateString(PreDrawDate)+"','yyyy-mm-dd') "
													 + " , -1, dtfinish "
													 + " , 0, dtfinish " 
													 + " , to_Date('"+DataFormat.getDateString(PreDrawDate)+"','yyyy-mm-dd') ) ");
				sbSQL.append(" where ID = " + lSubAccountID );
			}
			
			//�Ƿ�����˻�������
			//else if (SETTConstant.AccountType.isLoanAccountType(lAccountTypeID))
			//{
			//	sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n" );
			//	sbSQL.append(" , AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n" );
			//	sbSQL.append(" where ID = " + lSubAccountID );
			//}
			
			
			/**
			 * �޸ĵĵط�
			 */
			
			//�Ƿ�����˻�������
			//�Ƿ�֤���˻�������
			else if(  SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
					||SETTConstant.AccountType.isBakAccountType(lAccountTypeID)
					||SETTConstant.AccountType.isReserveAccountType(lAccountTypeID)
					||SETTConstant.AccountType.isLendingAccountType(lAccountTypeID)
					||SETTConstant.AccountType.isMarginAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('"+DataFormat.getDateString(PreDrawDate)+"','yyyy-mm-dd') \n");

				//sbSQL.append(
				//"decode(sign(to_Date('"
				//+ DataFormat.getDateString(PreDrawDate)
				//+ "','yyyy-mm-dd')-AF_DTEND),1,AF_DTEND,-1,to_Date('"
				//+ DataFormat.getDateString(PreDrawDate)
				//+ "','yyyy-mm-dd'),0,AF_DTEND)");
				//decode(sign(to_Date('"+DataFomat.getDateString(ts)+'','yyyy-mm-dd')-AF_DTEND),1,AF_DTEND,-1,to_Date('2003-10-13','yyyy-mm-dd'),0,AF_DTEND)
				//sbSQL.append(" ,dtClearInterest = to_date('" + DataFormat.getDateString(ClearInterestDate) + "','yyyy-mm-dd') \n");
				
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			
			//���ֺ�ί������
			else if (SETTConstant.AccountType.isLoanAccountType(lAccountTypeID) 
						&& !SETTConstant.AccountType.isDiscountAccountType(lAccountTypeID)
						&& !SETTConstant.AccountType.isConsignAccountType(lAccountTypeID))
			{
				
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
				
			}
			
			//���ֺ�ί����
			else if(SETTConstant.AccountType.isDiscountAccountType(lAccountTypeID)
					|| SETTConstant.AccountType.isConsignAccountType(lAccountTypeID))
			{
				
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				//sbSQL.append(" ,dtClearInterest = to_date('" + DataFormat.getDateString(ClearInterestDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			
			}
			
			log.info(sbSQL.toString());
			
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			
			cleanup(ps);
			cleanup(conn);
			
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	//������
	public long updatePreDrawInterestAndDateReverse(long lSubAccountID, double dPreDrawInterest, Timestamp PreDrawDate, long lAccountTypeID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			
			//�Ƿ����˻�������
			//�Ƿ�֪ͨ�˻�������
			if ( SETTConstant.AccountType.isFixAccountType(lAccountTypeID) 
				|| SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AF_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AF_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			//�Ƿ�֤���˻�������
			else if( SETTConstant.AccountType.isMarginAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			//�Ƿ�����˻�������
			else if ( SETTConstant.AccountType.isLoanAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			//�Ƿ�����˻�������
			else if ( SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			//�Ƿ����ִ����˻�������
			else if ( SETTConstant.AccountType.isDiscountAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			//�Ƿ񱸸����˻�������
			else if ( SETTConstant.AccountType.isBakAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			//�Ƿ�׼�����˻�������
			else if ( SETTConstant.AccountType.isReserveAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			//�Ƿ����˻�������
			else if ( SETTConstant.AccountType.isLendingAccountType(lAccountTypeID) )
			{
				sbSQL.append(" update sett_SubAccount set AL_mPreDrawInterest = " + dPreDrawInterest + " \n");
				sbSQL.append(" ,AL_dtPreDraw = to_date('" + DataFormat.getDateString(PreDrawDate) + "','yyyy-mm-dd') \n");
				sbSQL.append("  where ID = " + lSubAccountID);
			}
			
			log.info(sbSQL.toString());
			
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lReturn;
	}
	
	public void updateFinishDateAndStatus(long lSubAccountID, long lStatusID, Timestamp dtFinish) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set NSTATUSID = ?, DTFINISH = to_date('" + DataFormat.getDateString(dtFinish) + "','yyyy-mm-dd') where ID=? \n");
			//log.debug("lSubAccountID: " + lSubAccountID);
			//log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			//ps.setTimestamp(2, dtFinish);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
	}
	
	//add by zwxiao 2010-08-08 ����޸����˻����ѽ����޸�Ϊδ�����״̬
	public void updateNoFinishDateAndStatus(long lSubAccountID, long lStatusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set NSTATUSID = ?, DTFINISH = '' where ID=? \n");
			//log.debug("lSubAccountID: " + lSubAccountID);
			//log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			//ps.setTimestamp(2, dtFinish);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
	}
	
	/**
	 * ����˵�������ݲ�ѯ���������޸ĸ����������˻������ʼƻ�
	 * 
	 * @param qaci,ai
	 * @return long - �����޸ĳɹ��ı�ʶ
	 * @throws Exception
	 */
	public long batchUpdate(QueryAccountConditionInfo qaci, SubAccountCurrentInfo saci) throws SQLException
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psBatch = null;
		ResultSet rs = null;
		ResultSet rsBatch = null;
		StringBuffer sbSQL = null;
		StringBuffer sbSQLBatch = null;
		try
		{
			log.print("sett_SubAccountBatchUpdate Start");
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			String strSQL = "";
			sbSQL = new StringBuffer();
			sbSQLBatch = new StringBuffer();
			//-------------------------
			long lAccountID = -1;
			sbSQL.append(" UPDATE sett_SubAccount SET AC_nInterestRatePlanID=?,AC_dtInterestRatePlan=? ");
			sbSQL.append(" WHERE nAccountID=? ");
			//�ӵ�һ���˻���ʼѭ����ȡ��ÿ���˻�ID
			sbSQLBatch.append(" select a.ID ID from sett_Account a,Client c where a.nClientID = c.ID ");
			sbSQLBatch.append(" and a.nCheckStatusID=? ");
			if (qaci.getStartClientCode() != null && qaci.getEndClientCode() != null && qaci.getStartClientCode().length() > 0 && qaci.getEndClientCode().length() > 0)
			{
				sbSQLBatch.append(" and c.sCode between ? and ? ");
			}
			if (qaci.getStartAccountCode() != null && qaci.getEndAccountCode() != null && qaci.getStartAccountCode().length() > 0 && qaci.getEndAccountCode().length() > 0)
			{
				sbSQLBatch.append(" and a.sAccountNo between ? and ? ");
			}
			if (qaci.getAccountTypeID() > 0)
			{
				sbSQLBatch.append(" and a.nAccountTypeID=? ");
			}
			if (qaci.getBatchUpdateID() > 0)
			{
				sbSQLBatch.append(" and a.nBatchUpdateID=? ");
			}
			//log.print("sbSQLBatch:" + sbSQLBatch.toString());
			//log.print("checkstatusid:" + qaci.getCheckStatusID() +
			// ":accounttype:" + qaci.getAccountTypeID() + ":batchupdateid:" +
			// qaci.getBatchUpdateID());
			psBatch = conn.prepareStatement(sbSQLBatch.toString());
			int lIndex = 1;
			psBatch.setLong(lIndex++, qaci.getCheckStatusID());
			if (qaci.getStartClientCode() != null && qaci.getEndClientCode() != null && qaci.getStartClientCode().length() > 0 && qaci.getEndClientCode().length() > 0)
			{
				psBatch.setString(lIndex++, qaci.getStartClientCode());
				psBatch.setString(lIndex++, qaci.getEndClientCode());
				//log.print("getStartClientCode");
			}
			if (qaci.getStartAccountCode() != null && qaci.getEndAccountCode() != null && qaci.getStartAccountCode().length() > 0 && qaci.getEndAccountCode().length() > 0)
			{
				psBatch.setString(lIndex++, qaci.getStartAccountCode());
				psBatch.setString(lIndex++, qaci.getEndAccountCode());
				//log.print("getStartAccountCode");
			}
			if (qaci.getAccountTypeID() > 0)
			{
				psBatch.setLong(lIndex++, qaci.getAccountTypeID());
				//log.print("getAccountTypeID");
			}
			if (qaci.getBatchUpdateID() > 0)
			{
				psBatch.setLong(lIndex++, qaci.getBatchUpdateID());
				//log.print("getBatchUpdateID");
			}
			rsBatch = psBatch.executeQuery();
			while (rsBatch.next())
			{
				//����sett_SubAccount
				lAccountID = rsBatch.getLong("ID");
				ps = conn.prepareStatement(sbSQL.toString());
				int npos = 1;
				ps.setLong(npos++, saci.getInterestRatePlanID());
				ps.setTimestamp(npos++, saci.getInterestRatePlanDate());
				if (saci.getInterestRatePlanID() < 0 || saci.getInterestRatePlanDate() == null)					//���û��ѡ�����ʼƻ�����Ч���� ����Ҫupdate�˻��ӱ�
				{
					lAccountID = -1;
				}
				ps.setLong(npos++, lAccountID);
				//rs = ps.executeUpdate();
				//����sett_SubAccount����
				lReturn = ps.executeUpdate();
				//����ʧ��
				if (lReturn < 1)
					break;
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(rsBatch);
			cleanup(psBatch);
			//�ӵ�һ���˻���ʼѭ����ȡ��ÿ���˻�ID End
			//�ر���Դ
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			log.print("sett_SubAccountBatchUpdate End");
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(rsBatch);
			cleanup(psBatch);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * �������˻�IDȡ��ͬ�˻�����(���ڼ�����)�ļ�����Ϣ
	 */
	public double getPredrawInterestBySubAccountIDAndAccountType(long subAccountID, long accountType) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		double predrawInterest = 0.0;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			if (SETTConstant.AccountType.isFixAccountType(accountType)
			        ||SETTConstant.AccountType.isNotifyAccountType(accountType)
			        ||SETTConstant.AccountType.isMarginAccountType(accountType))
			{
				sbSQL.append(" select AF_MPREDRAWINTEREST from sett_SubAccount ");
			}
			else
				if (SETTConstant.AccountType.isLoanAccountType(accountType))
				{
					sbSQL.append(" select AL_MPREDRAWINTEREST from sett_SubAccount ");
				}
				else
					return 0.0;
			sbSQL.append(" where ID = ? ");
			log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, subAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				predrawInterest = rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return predrawInterest;
	}
	/**
	 * �������˻�ID����˻�����ID
	 */
	public long getAccountTypeBySubAccountID(long subAccountID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long accountTypeID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select ac.naccounttypeid from sett_account ac, sett_subaccount sac");
			sbSQL.append(" where sac.id = " + subAccountID + " and ac.id = sac.naccountid");
			log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				accountTypeID = rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return accountTypeID;
	}
	/**
	 * �������˻�ID���¶������˻�������Ϣ�ͼ�����Ϣʱ��
	 */
	public void updateFixedPredrawInterestAndPredrawDateByID(long subAccountID, double predrawInterest, Timestamp predrawDate) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set  AF_MPREDRAWINTEREST = ?, AF_DTPREDRAW = ? \n");
			sbSQL.append(" where ID = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, predrawInterest);
			ps.setTimestamp(2, predrawDate);
			ps.setLong(3, subAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
	}
	/**
	 * �������˻�ID���¶������˻�������Ϣ�ͼ�����Ϣʱ��
	 */
	public void updateLoanPredrawInterestAndPredrawDateByID(long subAccountID, double predrawInterest, Timestamp predrawDate) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set  AL_MPREDRAWINTEREST = ?, AL_DTPREDRAW = ?\n");
			sbSQL.append(" where ID = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, predrawInterest);
			ps.setTimestamp(2, predrawDate);
			ps.setLong(3, subAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
	}
	/**
	 * added by qhzhou 2007-07-25
	 * �������˻�ID���¶������˻�������Ϣ
	 */
	public void updateLoanPredrawInterestByID(long subAccountID, double predrawInterest) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set  AF_MPREDRAWINTEREST = ?\n");
			sbSQL.append(" where ID = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, predrawInterest);
			ps.setLong(2, subAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
	}
	/**
	 * ����˵�������Ļ������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	//	public long updateSubAccountCurrent(Connection conn,
	// SubAccountCurrentInfo saci) throws SQLException
	//	{
	//		PreparedStatement ps = null;
	//		StringBuffer sbSQL = null;
	//		try
	//		{
	//			//get the connection from Database
	//			//establish the update sql string
	//			sbSQL = new StringBuffer();
	//
	//			sbSQL.append(" update sett_SubAccount set nAccountID=?, mInterest=?,
	// mOpenAmount=?, dtOpen=?, dtFinish=?, nIsInterest=?, dtClearInterest=?,
	// nStatusID=?, \n");
	//			sbSQL.append(" AC_nInterestAccountID=?, AC_nIsOverDraft=?,
	// AC_nFirstLimitTypeID=?, AC_mFirstLimitAmount=?, AC_nSecondLimitTypeID=?,
	// \n");
	//			sbSQL.append(" AC_mSecondLimitAmount=?, AC_nThirdLimitTypeID=?,
	// AC_mThirdLimitAmount=?, AC_nInterestRatePlanID=?, AC_dtInterestRatePlan
	// = ?, AC_mCapitalLimitAmount=?,\n");
	//			sbSQL.append(" AC_nIsNegotiate=?, AC_mNegotiateAmount=?,
	// AC_mNegotiateUnit=?, AC_mNegotiateRate=?, AC_dtNegotiateRate = ?,
	// AC_mMonthLimitAmount=?,AC_MNEGOTIATEINTEREST=? \n");
	//			sbSQL.append(" where ID = ? \n");
	//			ps = conn.prepareStatement(sbSQL.toString());
	//			//set the PreparedStatement arguments by the BankBillInfo object
	//			setPrepareStatementByInfo_2(saci, ps, -1);
	//			ps.executeUpdate();
	//			cleanup(ps);
	//		}
	//		catch (Exception exp)
	//		{
	//			exp.printStackTrace();
	//		}
	//		finally
	//		{
	//			cleanup(ps);
	//		}
	//		return saci.getID();
	//	}
	/**
	 * ����˵�������Ĵ������˻�
	 * 
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	//	public long updateSubAccountLoan(Connection conn, SubAccountLoanInfo
	// sali) throws SQLException
	//	{
	//		long lReturn = -1;
	//		PreparedStatement ps = null;
	//		StringBuffer sbSQL = null;
	//		try
	//		{
	//			//get the connection from Database
	//			//establish the update sql string
	//			sbSQL = new StringBuffer();
	//			sbSQL.append(" update sett_SubAccount set nAccountID=?, mInterest=?,
	// mBalance=?, mOpenAmount=?, dtOpen=?, dtFinish=?, nIsInterest=?,
	// nStatusID=?, \n");
	//			sbSQL.append(" AL_nLoanNoteID=?, AL_nIsCycLoan=?, dtClearInterest=?,
	// al_dtCalculateInterest=?, AL_nPayInterestAccountID=?, \n");
	//			sbSQL.append(" AL_nReceiveInterestAccountID=?, AL_mPreDrawInterest=?,
	// AL_dtPreDraw=?, AL_nPaySuretyAccountID=?,
	// AL_nReceiveSuretyAccountID=?,\n");
	//			sbSQL.append(" AL_nCommissionAccountID=?, AL_mSuretyFee=?,
	// AL_dtClearSureFee=?, AL_mCommission=?,AL_dtClearCommission=?, \n");
	//			sbSQL.append(" AL_nInterestTaxAccountID=?, AL_mInterestTax=?,
	// AL_mInterestTaxRate=?, AL_dtClearInterestTax=? ,AL_dtEffectiveTax=?,
	// \n");
	//			sbSQL.append(
	//				" AL_nOverDueAccountID=?, AL_mOverDueInterest=?, AL_dtClearOverDue=?,
	// AL_nCompoundAccountID=?, AL_mCompoundInterest=?, AL_dtClearCompound=?,
	// AL_mArrearageInterest=?, Al_nConsignAccountID = ? \n");
	//			sbSQL.append(" where ID = ? \n");
	//			ps = conn.prepareStatement(sbSQL.toString());
	//			//set the PreparedStatement arguments by the BankBillInfo object
	//			setPrepareStatementByInfo_2(sali, ps, -1);
	//			ps.executeUpdate();
	//			cleanup(ps);
	//		}
	//		finally
	//		{
	//			cleanup(ps);
	//		}
	//		return sali.getID();
	//	}
	////////////////////////////////////////
	/**
	 * ����˵�����������˻�ID���õ����˻���Ϣ
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	public SubAccountAssemblerInfo findByID(long lSubAccountID) throws SQLException
	{
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			sbSQL.append(" where ID = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lSubAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountAssemblerInfo();
				getInfoFromResultSet(saai, rs);
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}
	/**
	 * ����˵�����������˻�ID���õ����˻���Ϣ
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	public long findStatusByID(long lSubAccountID) throws Exception
	{
		long lStatus = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			sbSQL.append(" where ID = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lSubAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
			    lStatus = rs.getLong("NSTATUSID");
				
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			try
            {
                cleanup(rs);
                cleanup(ps);
    			cleanup(conn);
            } catch (SQLException e1)
            {
                // TODO �Զ����� catch ��
                e1.printStackTrace();
            }
			
		}
		return lStatus;
	}
	/**
	 * @author hjliu 2003-12-29 ���ܶԸ������ո��������Խ���ǰ�ۼ�ǷϢ����Ϊ����ǰ��Ϣ�ʹ�������ڼ��㵱��ĸ�����ȷֵ
	 * @return 1:�ɹ� ��1:���ɹ�
	 * @throws SQLException
	 */
	public long updateArrearageInterest(long SubAccountID, double compoundInterest, long typeValue) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount  \n");
			if (typeValue == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
			{
				sbSQL.append(" set  AL_MARREARAGEINTEREST = \n");
				sbSQL.append(" Round(MINTEREST,2) +  Round(" + compoundInterest + ",2) \n");
			}
			else
				if (typeValue == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
					sbSQL.append(" set  AL_MOVERDUEARREARAGEINTEREST = \n");
					sbSQL.append(" Round(" + compoundInterest + ",2) \n");
				}
			sbSQL.append(" where ID =  " + SubAccountID);
			//log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			lReturn = 1;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * ��ѯ���еĴ����˻������ڴ��������
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 */
	public Collection findAllLoanAccount(long lOfficeID, long lCurrencyID) throws Exception
	{
		Vector subAccountVector = new Vector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		try
		{
			conn = getConnection();
			bufferSQL = new StringBuffer();
			bufferSQL.append(" select * from  sett_SubAccount  \n");
			bufferSQL.append(" where nAccountID in (  \n");
			bufferSQL.append(" select a.id from sett_account a,sett_accounttype b where a.naccounttypeid=b.id and ");
			bufferSQL.append(" a.nofficeID = " + lOfficeID + " and \n");
			bufferSQL.append(" a.ncurrencyID = " + lCurrencyID + " and \n");
			bufferSQL.append(" b.nAccountGroupID in ( "+SETTConstant.AccountGroupType.TRUST+","+SETTConstant.AccountGroupType.CONSIGN+","+SETTConstant.AccountGroupType.OTHERLOAN+") \n");
			bufferSQL.append(" and b.nstatusId=1 and b.officeId="+lOfficeID+" and b.currencyId="+lCurrencyID+") \n" );
			//log4j.info(bufferSQL.toString());
			String sqlString = bufferSQL.toString();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlString);
			while (rset.next())
			{
				subAccountAssemblerInfo = new SubAccountAssemblerInfo();
				//append one BankBillInfo to the LinkedList object
				getInfoFromResultSet(subAccountAssemblerInfo, rset);
				subAccountVector.add(subAccountAssemblerInfo);
			}
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		return subAccountVector;
	}
	/**
	 * ���ܣ�����������ʵ���� ˵����ȡ����ǰ�ػ����ڷ����ĵ��������������漰���˻�����Ϊ�����
	 * 
	 * @param lOfficeID
	 *            ���´�ID
	 * @param lCurrencyID
	 *            ����ID
	 * @param closeDate
	 *            �ػ�����
	 * @return @throws
	 *         Exception
	 */
	public Collection findAllDepositBankInterestAdjust(long lOfficeID, long lCurrencyID, Timestamp closeDate) throws Exception
	{
		Vector subAccountVector = new Vector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		BankInterestAdjustInfo depositBankInterestAdjustInfo = null;
		try
		{
			conn = getConnection();
			bufferSQL = new StringBuffer();
			bufferSQL.append(" select a. nAccountID AccountID, a.ID SubAccountID ,  \n");
			bufferSQL.append(" b.dtEffective backdate from sett_SubAccount a ,  \n");
			bufferSQL.append(" sett_Account account, sett_InterestRatePlanItem p , \n");
			bufferSQL.append(" sett_InterestRate b where a. nAccountID = account.id  \n");
			bufferSQL.append(" and p.nInterestRatePlanID=a.ac_nInterestRatePlanID  \n");
			bufferSQL.append(" and p.nInterestRateID=b.id and \n");
			bufferSQL.append(" account.nofficeID = " + lOfficeID + " and \n");
			bufferSQL.append(" account.ncurrencyID = " + lCurrencyID + " and \n");
			bufferSQL.append(" b.dtInput=  to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd')  \n");
			bufferSQL.append(" and dtEffective< to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd')  ");
			//log4j.info(bufferSQL.toString());
			String sqlString = bufferSQL.toString();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlString);
			while (rset.next())
			{
				depositBankInterestAdjustInfo = new BankInterestAdjustInfo();
				depositBankInterestAdjustInfo.setAccountID(rset.getLong("AccountID"));
				depositBankInterestAdjustInfo.setSubAccountID(rset.getLong("SubAccountID"));
				depositBankInterestAdjustInfo.setBackDate(rset.getTimestamp("backdate"));
				subAccountVector.add(depositBankInterestAdjustInfo);
			}
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		return subAccountVector;
	}
	/**
	 * ���ܣ������������ʵ���� ˵����ȡ����ǰ�ػ����ڷ����ĵ�����������ָ�������˻�����Ϊ�����
	 * 
	 * @param lOfficeID
	 *            ���´�ID
	 * @param lCurrencyID
	 *            ����ID
	 * @param interestDate
	 *            ��Ϣ����
	 * @param lSubAccountID
	 *            ���˻�ID     
	 * @return @throws
	 *         Exception
	 */
	public Collection findAccountLoanBankInterestAdjust(long lOfficeID, long lCurrencyID, Timestamp interestDate,long lSubAccountID) throws Exception
	{
		Vector subAccountVector = new Vector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		BankInterestAdjustInfo loanBankInterestAdjustInfo = null;
		try
		{
			conn = getConnection();
			bufferSQL = new StringBuffer();
			bufferSQL.append(" SELECT   \n");
			bufferSQL.append("      s.nAccountID as AccountID, \n");
			bufferSQL.append("      s.ID as SubAccountID ,\n");
			bufferSQL.append("      l.dtstartdate as backdate \n");
			bufferSQL.append("      ,l.ID as loanRateAdjustID \n");
			bufferSQL.append(" FROM  \n");
			bufferSQL.append("     sett_account a , \n");
			bufferSQL.append("     sett_subaccount s, \n");
			bufferSQL.append("     LOAN_RATEADJUSTPAYDETAIL l \n");
			bufferSQL.append(" WHERE  \n");
			bufferSQL.append("     l.NISCOUNTINTEREST=1 \n");
			bufferSQL.append("     and s.al_nloannoteid=l.nloanpaynoticeid \n");
			bufferSQL.append("     and a.ID = s.naccountID \n");
			bufferSQL.append("     and a.ncurrencyID =  " + lCurrencyID + " \n");
			bufferSQL.append("     and a.nofficeID   =  " + lOfficeID + " \n");
			bufferSQL.append("     and l.dtstartdate<to_date('" + DataFormat.getDateString(interestDate) + "','yyyy-mm-dd')  \n");
			
			//modified by mzh_fu 2008/06/13 �������� status != Constant.RecordStatus.INVALID
			bufferSQL.append("     and l.status != " + Constant.RecordStatus.INVALID);
			
			//modified by bingliu 2012/03/07 �������� ���˻�ID����
			bufferSQL.append("     and s.id = " + lSubAccountID);
			
			bufferSQL.append(" ORDER BY backDate ");
			
			log.print("��ѯ�����������ʵ�����sql��: ");
			log.print(bufferSQL.toString());
			
			String sqlString = bufferSQL.toString();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlString);
			while (rset.next())
			{
				loanBankInterestAdjustInfo = new BankInterestAdjustInfo();
				loanBankInterestAdjustInfo.setAccountID(rset.getLong("AccountID"));
				loanBankInterestAdjustInfo.setSubAccountID(rset.getLong("SubAccountID"));
				loanBankInterestAdjustInfo.setBackDate(rset.getTimestamp("backdate"));
				loanBankInterestAdjustInfo.setLoanRateAdjustPayDetailID(rset.getLong("loanRateAdjustID"));
				subAccountVector.add(loanBankInterestAdjustInfo);
			}
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		return subAccountVector;
	}
	
	
	/**
	 * ���ܣ������������ʵ���� ˵����ȡ����ǰ�ػ����ڷ����ĵ��������������漰���˻�����Ϊ�����
	 * 
	 * @param lOfficeID
	 *            ���´�ID
	 * @param lCurrencyID
	 *            ����ID
	 * @param closeDate
	 *            �ػ�����
	 * @return @throws
	 *         Exception
	 */
	public Collection findAllLoanBankInterestAdjust(long lOfficeID, long lCurrencyID, Timestamp closeDate) throws Exception
	{
		Vector subAccountVector = new Vector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		BankInterestAdjustInfo loanBankInterestAdjustInfo = null;
		try
		{
			conn = getConnection();
			bufferSQL = new StringBuffer();
			bufferSQL.append(" SELECT   \n");
			bufferSQL.append("      s.nAccountID as AccountID, \n");
			bufferSQL.append("      s.ID as SubAccountID ,\n");
			bufferSQL.append("      l.dtstartdate as backdate \n");
			bufferSQL.append("      ,l.ID as loanRateAdjustID \n");
			bufferSQL.append(" FROM  \n");
			bufferSQL.append("     sett_account a , \n");
			bufferSQL.append("     sett_subaccount s, \n");
			bufferSQL.append("     LOAN_RATEADJUSTPAYDETAIL l \n");
			bufferSQL.append(" WHERE  \n");
			bufferSQL.append("     l.NISCOUNTINTEREST=1 \n");
			bufferSQL.append("     and s.al_nloannoteid=l.nloanpaynoticeid \n");
			bufferSQL.append("     and a.ID = s.naccountID \n");
			bufferSQL.append("     and a.ncurrencyID =  " + lCurrencyID + " \n");
			bufferSQL.append("     and a.nofficeID   =  " + lOfficeID + " \n");
			bufferSQL.append("     and l.dtstartdate<to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd')  \n");
			
			//modified by mzh_fu 2008/06/13 �������� status != Constant.RecordStatus.INVALID
			bufferSQL.append("     and l.status != " + Constant.RecordStatus.INVALID);
			
			bufferSQL.append(" ORDER BY backDate ");
			
			log4j.debug("2004-08-17 ��ѯ�������ʵ�����sql�ǣ�" + bufferSQL.toString());
			
			String sqlString = bufferSQL.toString();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlString);
			while (rset.next())
			{
				loanBankInterestAdjustInfo = new BankInterestAdjustInfo();
				loanBankInterestAdjustInfo.setAccountID(rset.getLong("AccountID"));
				loanBankInterestAdjustInfo.setSubAccountID(rset.getLong("SubAccountID"));
				loanBankInterestAdjustInfo.setBackDate(rset.getTimestamp("backdate"));
				loanBankInterestAdjustInfo.setLoanRateAdjustPayDetailID(rset.getLong("loanRateAdjustID"));
				subAccountVector.add(loanBankInterestAdjustInfo);
			}
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		return subAccountVector;
	}
	
	/**
	 * ���ܣ�����libor���ʵ���� ˵����ȡ����ǰ�ػ����ڷ����ĵ���libor�������漰���˻�����Ϊ�����
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param closeDate
	 * @return
	 * @throws Exception
	 */
	public Collection findAllLoanLiborInterestAdjust(long lOfficeID, long lCurrencyID, Timestamp closeDate) throws Exception
	{
		Vector subAccountVector = new Vector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		LiborInterestAdjustInfo liborInterestAdjustInfo = null;
		try
		{
			conn = getConnection();
			bufferSQL = new StringBuffer();
			bufferSQL.append(" SELECT   \n");
			bufferSQL.append("      s.nAccountID as AccountID, \n");
			bufferSQL.append("      s.ID as SubAccountID ,\n");
			bufferSQL.append("      l.INTERESTSTART as backdate \n");
			bufferSQL.append("      ,l.ID as loanRateAdjustID \n");
			bufferSQL.append(" FROM  \n");
			bufferSQL.append("     sett_account a , \n");
			bufferSQL.append("     sett_subaccount s, \n");
			bufferSQL.append("     LOAN_LIBORINFORM l \n");
			bufferSQL.append(" WHERE  \n");
			bufferSQL.append("     l.ISCOUNTINTEREST=1 \n");
			bufferSQL.append("     and s.al_nloannoteid=l.paynoticeid \n");
			bufferSQL.append("     and a.ID = s.naccountID \n");
			bufferSQL.append("     and a.ncurrencyID =  " + lCurrencyID + " \n");
			bufferSQL.append("     and a.nofficeID   =  " + lOfficeID + " \n");
			bufferSQL.append("     and l.INTERESTSTART < to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd')  \n");
			bufferSQL.append(" ORDER BY backDate ");
			log4j.debug("��ѯLIBOR���ʵ�����sql�ǣ�" + bufferSQL.toString());
			String sqlString = bufferSQL.toString();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlString);
			while (rset.next())
			{
				liborInterestAdjustInfo = new LiborInterestAdjustInfo();
				liborInterestAdjustInfo.setAccountID(rset.getLong("AccountID"));
				liborInterestAdjustInfo.setSubAccountID(rset.getLong("SubAccountID"));
				liborInterestAdjustInfo.setBackDate(rset.getTimestamp("backdate"));
				liborInterestAdjustInfo.setLoanLiborInformID(rset.getLong("loanRateAdjustID"));
				subAccountVector.add(liborInterestAdjustInfo);
			}
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		return subAccountVector;
	}
	/**
	 * Method querySubInfo. ���ݷſ�֪ͨ��id�������˻���Ϣ Ŀǰֻ���ؿ������Ѿ��˻���� �ɽ�һ������
	 * 
	 * @param info
	 * @return SubAccountLoanInfo
	 * @throws Exception
	 */
	public SubAccountLoanInfo querySubInfo(SubAccountLoanInfo info) throws Exception
	{
		SubAccountLoanInfo resultInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select * from SETT_SUBACCOUNT where AL_NLOANNOTEID=? ");
			if(info.getStatusID()==1){
				sbSQL.append(" AND NSTATUSID=1 ");
			}
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getLoanNoteID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				resultInfo = new SubAccountLoanInfo();
				resultInfo.setID(rs.getLong("ID"));
				resultInfo.setLoanNoteID(rs.getLong("AL_NLOANNOTEID"));
				resultInfo.setOpenAmount(rs.getDouble("MOPENAMOUNT"));
				resultInfo.setBalance(rs.getDouble("MBALANCE"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resultInfo;
	}
	public long updateLoanRateAdjustPayDetail(long loanRateAdjustID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("update LOAN_RATEADJUSTPAYDETAIL set NISCOUNTINTEREST = 0 where ID = " + loanRateAdjustID);
			log.debug("�޸�LOAN_RATEADJUSTPAYDETAIL \n " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * ������LOAN_LIBORINFORM���Ƿ���Ϣ�ֶ�ISCOUNTINTEREST�޸�Ϊ0��δ��Ϣ��
	 * @param loanRateAdjustID
	 * @return
	 * @throws SQLException
	 */
	public long updateLoanLiborInform(long loanRateAdjustID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("update LOAN_LIBORINFORM set ISCOUNTINTEREST = 0 where ID = " + loanRateAdjustID);
			log.debug("�޸�LOAN_LIBORINFORM \n " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * Method UpdateOpenAmount.
	 * ���ӿ������
	 * @param lSubAccountID
	 * @param dAmount
	 * @return long
	 * @throws SQLException
	 */
	public long UpdateOpenAmount(long lSubAccountID, double dAmount) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("update sett_subaccount set mopenamount=nvl(mopenamount,0)+" + dAmount);
			sbSQL.append(" where ID = " + lSubAccountID);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	/**
	 * Boxu Add 2008��1��29��
	 * Method UpdateDrawInterest
	 * �޸�֪ͨ������Ϣ
	 * @param lSubAccountID
	 * @param predrawinterest
	 * @return long
	 * @throws SQLException
	 */
	public long UpdateDrawInterest(long lSubAccountID, double predrawinterest, long isPreDraw, String strAction) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		SubAccountAssemblerInfo saai = null;
		try
		{
			saai = this.findByID(lSubAccountID);
			
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_subaccount set ");
			
			if(strAction.equals("check"))
			{
				//������Ϣ=ԭ������Ϣ-֧ȡ����Ӧ������Ϣ,al_mpredrawinterest��ʱ�����һ�μ�����Ϣ
				sbSQL.append(" af_mpredrawinterest = "+UtilOperation.Arith.sub(saai.getSubAccountFixedInfo().getPreDrawInterest(), predrawinterest)+" ");
				sbSQL.append(" , al_mpredrawinterest = "+saai.getSubAccountFixedInfo().getPreDrawInterest()+" ");
				
				//�������ǰ֧ȡ,����������޸�Ϊ��Ϣ��,al_dtpredraw��ʱ�����һ�μ�����
				if(isPreDraw == 1)
				{
					sbSQL.append(" , af_dtpredraw = to_date('"+DataFormat.formatDate( saai.getSubAccountFixedInfo().getClearInterestDate() )+"','yyyy-mm-dd')");
					sbSQL.append(" , al_dtpredraw = to_date('"+DataFormat.formatDate( saai.getSubAccountFixedInfo().getPreDrawDate() )+"','yyyy-mm-dd')");
				}
			}
			else  //ȡ������
			{
				//�������ǰ֧ȡ,���������ȡ��al_dtpredraw��ʱ�����һ�μ�����
				sbSQL.append(" af_dtpredraw = to_date('"+DataFormat.formatDate( saai.getSubAccountCurrenctInfo().getPreDrawDate() )+"','yyyy-mm-dd')");
				sbSQL.append(" , al_dtpredraw = null ");
			}
			
			sbSQL.append(" where ID = " + lSubAccountID );
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lReturn;
	}
	
	/**
	 * Boxu Add 2008��3��18��
	 * Method UpdateDrawInterest
	 * �޸Ķ��ڼ�����Ϣ
	 * @param lSubAccountID
	 * @param predrawinterest
	 * @return long
	 * @throws SQLException
	 */
	public long UpdateFixedDrawInterest(long lSubAccountID, double predrawinterest, String strAction) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		SubAccountAssemblerInfo saai = null;
		try
		{
			saai = this.findByID(lSubAccountID);
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_subaccount set ");
			//�������´浥ʱ��Ҫ��γ弯����Ϣ
			if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
			{
				if(strAction.equals("check"))
				{
					sbSQL.append(" af_mpredrawinterest = 0 ");
					sbSQL.append(" , al_mpredrawinterest = "+predrawinterest+" ");
				}
				else  //ȡ������
				{
					sbSQL.append(" af_mpredrawinterest = "+saai.getSubAccountCurrenctInfo().getDrawInterest()+" ");
					sbSQL.append(" , al_mpredrawinterest = null ");
				}
			}
			else
			{
				if(strAction.equals("check"))
				{
					sbSQL.append(" af_mpredrawinterest = "+(saai.getSubAccountFixedInfo().getPreDrawInterest()-predrawinterest));
					sbSQL.append(" , al_mpredrawinterest = "+(saai.getSubAccountCurrenctInfo().getDrawInterest()+predrawinterest)+" ");
				}
				else  //ȡ������
				{
					sbSQL.append(" af_mpredrawinterest = "+(saai.getSubAccountFixedInfo().getPreDrawInterest()+predrawinterest)+" ");
					sbSQL.append(" , al_mpredrawinterest = "+(saai.getSubAccountCurrenctInfo().getDrawInterest()-predrawinterest));
				}
			}
			
			sbSQL.append(" where ID = " + lSubAccountID );
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lReturn;
	}
	
	/**
	 * Boxu Add 2008��1��29��
	 * Method RollbackDrawInterest
	 * ��ԭ֪ͨ������Ϣ
	 * @param lSubAccountID
	 * @param predrawinterest
	 * @return long
	 * @throws SQLException
	 */
	public long RollbackDrawInterest(long lSubAccountID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		SubAccountAssemblerInfo saai = null;
		try
		{
			saai = this.findByID(lSubAccountID);
			
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_subaccount set af_mpredrawinterest = "+saai.getSubAccountCurrenctInfo().getDrawInterest()+" ");
			sbSQL.append(" , al_mpredrawinterest = null ");
			sbSQL.append(" where ID = " + lSubAccountID );
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lReturn;
	}
	
	/**
	 * ����˵�����������˻�ID���õ����˻���Ϣ
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception 
	 * @throws Exception
	 */
	public SubAccountFixedInfo findFixedSubAccountInfoByID(long lSubAccountID) throws Exception 
	{
	    SubAccountFixedInfo info = new SubAccountFixedInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			sbSQL.append(" where ID = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lSubAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
			    info.setID(rs.getLong("id"));
			    info.setDepositNo(rs.getString("AF_SDEPOSITNO"));
			    info.setAccountID(rs.getLong("NACCOUNTID"));
				
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (SQLException e)
        {
            // TODO �Զ����� catch ��
            e.printStackTrace();
            throw e;
        }
		finally
		{
			try
            {
                cleanup(rs);
                cleanup(ps);
    			cleanup(conn);
            } catch (SQLException e1)
            {
                // TODO �Զ����� catch ��
                e1.printStackTrace();
            }
			
		}
		return info;
	}
	
	/**
	 * ��ѯ���еĴ����˻������ڴ��������
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 */
	public Collection findAllInterestForICBC(long lOfficeID, long lCurrencyID) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		InterestQueryResultInfo interestInfo = null;
		try
		{
			conn = getConnection();
			bufferSQL = new StringBuffer();
			bufferSQL.append(" select sa.nAccountID AccountID,a.nAccountTypeID AccountTypeID,sa.ID SubAccountID,round(nvl(sa.mInterest,0)+nvl(sa.ac_mNegotiateInterest,0),2) Interest from sett_SubAccount sa,sett_Account a \n");
			bufferSQL.append(" where sa.nAccountID=a.ID and a.nStatusID>0 and sa.nStatusID>0 \n");
			bufferSQL.append(" and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID );
			bufferSQL.append(" and a.nAccountTypeID=1");
			bufferSQL.append(" order by sa.nAccountID ");
			//log4j.info(bufferSQL.toString());
			String sqlString = bufferSQL.toString();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlString);
			while (rset.next())
			{
				interestInfo = new InterestQueryResultInfo();
				interestInfo.setAccountID(rset.getLong("AccountID"));
				interestInfo.setSubAccountID(rset.getLong("SubAccountID"));
				interestInfo.setInterest(rset.getDouble("Interest"));
				//interestInfo.setCreateDate(dtCreateDate);
				interestInfo.setAccountTypeID(rset.getLong("AccountTypeID"));
				v.add(interestInfo);
			}
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(stmt);
			cleanup(conn);
		}
		return v;
	}
		//	���������������ʵ����˻���
	public long updateRate(long lSubAccountID, double rate) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set af_mrate = ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setDouble(1, rate);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}

	/**
	 * �������洦�������Ϣ�ͼ�������
	 * @param subAccountID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long ContinueFixedPreDrawInterest(long subAccountID, String strCheckType) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		SubAccountAssemblerInfo SubAccountInfo = null;
		try
		{
			SubAccountInfo = this.findByID(subAccountID);
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_subaccount set ");
			
			if(strCheckType.equals("check"))
			{
				sbSQL.append(" af_mpredrawinterest = 0 ");
				sbSQL.append(" , al_mpredrawinterest = "+SubAccountInfo.getSubAccountFixedInfo().getPreDrawInterest()+" ");
			
				sbSQL.append(" , af_dtpredraw = to_date('"+DataFormat.formatDate( SubAccountInfo.getSubAccountFixedInfo().getClearInterestDate() )+"','yyyy-mm-dd')");
				sbSQL.append(" , al_dtpredraw = to_date('"+DataFormat.formatDate( SubAccountInfo.getSubAccountFixedInfo().getPreDrawDate() )+"','yyyy-mm-dd')");
			}
			else  //ȡ������
			{
				sbSQL.append(" af_mpredrawinterest = "+SubAccountInfo.getSubAccountCurrenctInfo().getDrawInterest()+" ");
				sbSQL.append(" , al_mpredrawinterest = null ");
				
				sbSQL.append(" , af_dtpredraw = to_date('"+DataFormat.formatDate( SubAccountInfo.getSubAccountCurrenctInfo().getPreDrawDate() )+"','yyyy-mm-dd')");
				sbSQL.append(" , al_dtpredraw = null ");
			}
			
			sbSQL.append(" where ID = " + subAccountID );
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			lReturn = 1;
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lReturn;
	}
	
	/**
	 * ����˵�����������˻�ID���õ��ͻ����
	 * Boxu Add 2008��4��30��
	 * @param lAccountID
	 * @return String
	 * @throws Exception
	 */
	public String findClientCodeBySubAccountID(long lSubAccountID) throws SQLException
	{
		String clientCode = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select cc.scode clientCode ");
			sbSQL.append(" from sett_SubAccount ss, sett_account sa, client cc ");
			sbSQL.append(" where ss.naccountid = sa.id and sa.nclientid = cc.id ");
			sbSQL.append(" and ss.id = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lSubAccountID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				clientCode = rs.getString("clientCode");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return clientCode;
	}
	
	/**
	 * ����˵�����������˻�ID���õ��ͻ�ID���ͻ���š��˻���ID��SAP�ͻ����롢SAP��Ӧ�̱���
	 * Add 2012��12��04��
	 * @param lSubAccountID
	 * @return ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findClientInfoBySubAccountID(long lSubAccountID) throws SQLException
	{
		ClientInfo clientInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select cc.id clientID, cc.code clientCode, sat.naccountgroupid accountGroupID, cc.sapClientCode, cc.sapSupplierCode ");
			sbSQL.append(" from sett_SubAccount ss, sett_account sa, client_clientinfo cc, sett_accounttype sat ");
			sbSQL.append(" where ss.naccountid = sa.id and sa.nclientid = cc.id and sa.naccounttypeid = sat.id ");
			sbSQL.append(" and ss.id = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lSubAccountID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				clientInfo = new ClientInfo();
				clientInfo.setClientID(rs.getLong("clientID"));
				clientInfo.setClientCode(rs.getString("clientCode"));
				clientInfo.setAccountGroupID(rs.getLong("accountGroupID"));
				clientInfo.setSAPClientCode(rs.getString("sapClientCode"));
				clientInfo.setSAPSupplierCode(rs.getString("sapSupplierCode"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return clientInfo;
	}
	
	/**
	 * ����˵�����������˻�ID���õ��˻�����
	 * Boxu Add 2008��4��30��
	 * @param lAccountID
	 * @return long
	 * @throws Exception
	 */
	public long findAccountTypeBySubAccountID(long lSubAccountID) throws SQLException
	{
		long accountType = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select sat.id accounttype ");
			sbSQL.append(" from sett_account sa, sett_subaccount ss, sett_accounttype sat ");
			sbSQL.append(" where sa.id = ss.naccountid and sa.naccounttypeid = sat.id ");
			sbSQL.append(" and ss.id = ? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lSubAccountID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountType = rs.getLong("accounttype");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return accountType;
	}
	/**
	 * Method querySubInfo. ���ݷſ�֪ͨ��id�������˻���Ϣ 
	 * add by xwhe 2008-06-26
	 * @param info
	 * @return SubAccountLoanInfo
	 * @throws Exception
	 */
	public SubAccountLoanInfo querySubAccountInfo(SubAccountLoanInfo info) throws Exception
	{
		SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select * from SETT_SUBACCOUNT where id=? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				resultInfo = new SubAccountLoanInfo();
				resultInfo.setID(rs.getLong("ID"));
				resultInfo.setLoanNoteID(rs.getLong("AL_NLOANNOTEID"));
				resultInfo.setClearInterestDate(rs.getTimestamp("dtclearinterest"));
				resultInfo.setBalance(rs.getDouble("mbalance"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resultInfo;
	}
	/**
	 * ����˵�������Ļ������˻�
	 * ���·��� ���޸ļ�����Ϣ�ͼ�������
	 * modify by xwhe 
	 * 2008-10-24
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public long updateSubAccountCurrentInfo(SubAccountCurrentInfo saci) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_SubAccount set nAccountID=?, mInterest=?, mOpenAmount=?, \n");
			sbSQL.append(" dtOpen = to_date('" + DataFormat.getDateString(saci.getOpenDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,dtFinish = to_date('" + DataFormat.getDateString(saci.getFinishDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,nIsInterest=?, \n");
			sbSQL.append(" dtClearInterest=to_date('" + DataFormat.getDateString(saci.getClearInterestDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,nStatusID=?, \n");
			sbSQL.append(" AC_nInterestAccountID=?, AC_nIsOverDraft=?, AC_nFirstLimitTypeID=?, AC_mFirstLimitAmount=?, AC_nSecondLimitTypeID=?, \n");
			sbSQL.append(" AC_mSecondLimitAmount=?, AC_nThirdLimitTypeID=?, AC_mThirdLimitAmount=?, AC_nInterestRatePlanID=?, \n");
			sbSQL.append(" AC_dtInterestRatePlan = to_date('" + DataFormat.getDateString(saci.getInterestRatePlanDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,AC_mCapitalLimitAmount=?,\n");
			sbSQL.append(" AC_nIsNegotiate=?, AC_mNegotiateAmount=?, AC_mNegotiateUnit=?, AC_mNegotiateRate=?, \n");
			sbSQL.append(" AC_dtNegotiateRate = to_date('" + DataFormat.getDateString(saci.getNegotiateRateDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(" ,AC_mMonthLimitAmount=?,AC_mDayLimitAmount=?,AC_MNEGOTIATEINTEREST=?,AC_nIsAllBranch=?  \n");
			sbSQL.append(",AC_dtNegotiationStartDate = to_date('" + DataFormat.getDateString(saci.getNegotiationStartDate()) + "','yyyy-mm-dd') \n");
			sbSQL.append(",AC_dtNegotiationEndDate = to_date('" + DataFormat.getDateString(saci.getNegotiationEndDate()) + "','yyyy-mm-dd') \n");
			
			//��ǰ����û�м������,���������˻��ڵļ������,���Խ�Ϣ����ռ�����Ϣ�ͼ�������
		//	sbSQL.append(" , AL_MPREDRAWINTEREST = "+ DataFormat.formatDouble(saci.getDrawInterest()) +" ");
		//	sbSQL.append(" , AL_DTPREDRAW = to_date('" + DataFormat.getDateString(saci.getPreDrawDate()) + "','yyyy-mm-dd') ");
			
			sbSQL.append(" where ID = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(saci, ps, -1);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return saci.getID();
	}
	
	/**
	 * ����˵����ȡ������ʱ���´������˻������ѽ���ǰû�������������
	 * modify by xwhe 
	 * 2008-10-24
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public long updateCommission(TransAccountDetailInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			if(info.getAmountType()==SETTConstant.AmountType.AmountType_9){
				sbSQL.append(" update sett_SubAccount set AL_MCOMMISSION=AL_MCOMMISSION+"+info.getAmount()+"\n");			
				sbSQL.append(" where ID = "+info.getTransSubAccountID()+" \n");
			}
			System.out.println("����������SQL ================="+sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return info.getTransSubAccountID();
	}
	
	/**
	 * ����˵����ͨ����ͬ�Լ���֤���˻��ŵõ����е����˻����������㣩
	 * modify by xwhe 
	 * 2008-10-24
	 * @param saci
	 * @return @throws
	 *         Exception
	 */
	public List getSubAccountByCOntractIDAndMargin(long lContractID,long marginAccountID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		List returnList = new ArrayList();
		try
		{
			conn = getConnection();
			sql = new StringBuffer();
			
			sql.append("select d.id as subaccountid, d.al_nloannoteid, d.nstatusid, d.mbalance as mbalance, e.naccountgroupid ");	
			 sql.append("from loan_contractform a, loan_assurechargeform b,sett_account c, sett_subaccount d, Sett_AccountType e ");	
			 sql.append("where 1=1  ");	
			 sql.append("and b.id = d.al_nloannoteid and d.nstatusid>0 ");	
			 sql.append("and a.id = b.contractid ");	
			 sql.append("and c.id = d.naccountid ");	
			 sql.append("and c.naccounttypeid = e.id ");	
			 sql.append("and a.id = "+lContractID);	
			 sql.append("and c.id = "+marginAccountID);
			 sql.append("and mbalance > 0 ");
			 sql.append("order by subaccountid ");
			
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				Sett_SubAccountInfo accountInfo = new Sett_SubAccountInfo();
				accountInfo.setId(rs.getLong("subaccountid"));
				accountInfo.setAl_NLoanNoteId(rs.getLong("al_nloannoteid"));
				accountInfo.setNStatusId(rs.getLong("nstatusid"));
				accountInfo.setMBalance(rs.getDouble("mbalance"));
				returnList.add(accountInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return returnList;
	}
	
	/**
	 * ����˵���������˻�ID�ʹ���֪ͨ���ţ��õ����˻���Ϣ
	 * add by zwxiao 2010-08-08 ȥ����״̬�Ĺ��ˣ���Ϊ��ȡ�����˵�ʱ��������ѯ������Ӧ�����˻�
	 * 
	 * @param lAccountID
	 * @param strDepositNo
	 * @return AccountInfo
	 * @throws Exception
	 */
	public SubAccountAssemblerInfo findByLoanNoteIDForCNMEF(long lAccountID, long loanNoteID) throws SQLException
	{
		SubAccountAssemblerInfo saai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_SubAccount ");
			if (lAccountID > 0)
				sbSQL.append(" where AL_NLOANNOTEID=? and nAccountID = ?");
			else
				sbSQL.append(" where AL_NLOANNOTEID=? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, loanNoteID);
			if (lAccountID > 0)
				ps.setLong(2, lAccountID);
			//ps.setLong(3, SETTConstant.AccountStatus.NORMAL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				saai = new SubAccountAssemblerInfo();
				getInfoFromResultSet(saai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return saai;
	}
	public Collection findAutoContinueData(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList arrResult = new ArrayList();	
		SubAccountFixedInfo info = null;
		StringBuffer sql = new StringBuffer();
		try
		{
			initDAO();
		sql.append(" select a.*\n");
		sql.append("   from sett_SubAccount a, sett_Account ma, sett_accountType sat\n");
		sql.append("  where a.nAccountID = ma.ID\n");
		sql.append("    and a.nStatusID = 1\n");
		sql.append("    and ma.nAccountTypeID = sat.id\n");
		sql.append("    and sat.nStatusID = 1\n");
		sql.append("    and sat.nAccountGroupID = "+SETTConstant.AccountGroupType.FIXED+"\n");
		sql.append("    and ma.nofficeid = "+lOfficeID+"\n");
		sql.append("    and ma.nCurrencyID = "+lCurrencyID+"\n");
		sql.append("    and a.af_dtEnd = to_date('"+DataFormat.formatDate(tsDate)+"', 'yyyy-mm-dd')\n");
		sql.append("    and a.af_isautocontinue = 1\n");
		sql.append("    and a.mbalance > 0\n");
		
		ps=con.prepareStatement(sql.toString());
		rs=ps.executeQuery();
		while(rs.next())
		{
			info = new SubAccountFixedInfo();
			this.getInfoFromResultSet(info,rs);
			arrResult.add(info);
		}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;
	}
	public double findAvailableBalance(long lAccountID,long lOfficeID,long lCurrencyID) throws Exception
    {
		Connection conn =  getConnection();;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		double dFirstLimitAmount =0;
		double dSecondLimitAmount =0;
		double dThirdLimitAmount = 0;
		double dCapitalLimitAmount = 0;
		double AvailableBalance=0;
		try
		{
	    	sql.append("SELECT distinct acct.nofficeID            as OfficeID,                           \n");
	    	sql.append("                acct.nCurrencyID          CurrencyID,                            \n");
	    	sql.append("                acct.ID                   AccountID,                             \n");
	    	sql.append("                acct.sAccountNo           as AccountNo,                          \n");
	    	sql.append("                acct.sName                as AccountName,                        \n");
	    	sql.append("                acct.nClientID            as ClientID,                           \n");
	    	sql.append("                acct.nAccountTypeID       as AccountTypeID,                      \n");
	    	sql.append("                acct.dtOpen               as OpenDate,                           \n");
	    	sql.append("                acct.dtFinish             as ClearDate,                          \n");
	    	sql.append("                acct.nstatusid            as MainAccountStatusID,                \n");
	    	sql.append("                acct.sabstract,                                                  \n");
	    	sql.append("                b.balance,                                                       \n");
	    	sql.append("                b.interest,                                                      \n");
	    	sql.append("                b.availableBalance,                                              \n");
	    	sql.append("                b.IsNegotiate,                                                   \n");
	    	sql.append("                b.limitamount,                                                   \n");
	    	sql.append("                aa.ac_nInterestRatePlanID interestPlanID,                        \n");
	    	sql.append("                1                         isToday,                               \n");
	    	sql.append("                ss.AC_NFIRSTLIMITTYPEID   firstLimitTypeId,                      \n");
	    	sql.append("                ss.AC_MFIRSTLIMITAMOUNT   firstLimitAmount,                      \n");
	    	sql.append("                ss.AC_NSECONDLIMITTYPEID  secondLimitTypeId,                     \n");
	    	sql.append("                ss.AC_MSECONDLIMITAMOUNT  secondLimitAmount,                     \n");
	    	sql.append("                ss.AC_NTHIRDLIMITTYPEID   thirdLimitTypeId,                      \n");
	    	sql.append("                ss.AC_MTHIRDLIMITAMOUNT   thirdLimitAmount,                      \n");
	    	sql.append("                ss.AC_MCAPITALLIMITAMOUNT capitalLimitAmount                     \n");
	    	sql.append("  FROM sett_account acct,                                                        \n");
	    	sql.append("       sett_subaccount ss,                                                       \n");
	    	sql.append("       (select DISTINCT a.id, b.ac_nInterestRatePlanID                           \n");
	    	sql.append("          from sett_account a, sett_subaccount b                                 \n");
	    	sql.append("         where a.ID = b.naccountid(+)) aa,                                       \n");
	    	sql.append("       (select distinct a.naccountid,                                            \n");
	    	sql.append("                        a.balance,                                               \n");
	    	sql.append("                        a.interest,                                              \n");
	    	sql.append("                        a.availableBalance,                                      \n");
	    	sql.append("                        a.limitamount,                                           \n");
	    	sql.append("                        aa.IsNegotiate                                           \n");
	    	sql.append("          from (select sum(nvl(ac_nIsNegotiate, 0)) IsNegotiate,                 \n");
	    	sql.append("                       naccountid                                                \n");
	    	sql.append("                  from sett_subaccount                                           \n");
	    	sql.append("                 group by naccountid) aa,                                        \n");
	    	sql.append("               (select acc.id��naccountid,                                       \n");
	    	sql.append("                       sum(round(nvl(subAcct.mbalance, 0), 2)) balance,          \n");
	    	sql.append("                       sum(round(nvl(subAcct.ac_mcapitallimitamount,             \n");
	    	sql.append("                                     0),                                         \n");
	    	sql.append("                                 2)) limitamount,                                \n");
	    	sql.append("                       sum(round(nvl(decode(subAcct.minterest,                   \n");
	    	sql.append("                                            0,                                   \n");
	    	sql.append("                                            subAcct.af_mpredrawinterest,         \n");
	    	sql.append("                                            subAcct.minterest),                  \n");
	    	sql.append("                                     0),                                         \n");
	    	sql.append("                                 2) + round(nvl(subAcct.ac_mNegotiateInterest,   \n");
	    	sql.append("                                                0),                              \n");
	    	sql.append("                                            2)) Interest,                        \n");
	    	sql.append("                       sum(round(nvl(decode(subAcct.nstatusid,                   \n");
	    	sql.append("                                            1,                                   \n");
	    	sql.append("                                            subAcct.mbalance -                   \n");
	    	sql.append("                                            subAcct.MUNCHECKPAYMENTAMOUNT,       \n");
	    	sql.append("                                            5,                                   \n");
	    	sql.append("                                            subAcct.mbalance -                   \n");
	    	sql.append("                                            subAcct.MUNCHECKPAYMENTAMOUNT,       \n");
	    	sql.append("                                            4,                                   \n");
	    	sql.append("                                            0,                                   \n");
	    	sql.append("                                            2,                                   \n");
	    	sql.append("                                            0,                                   \n");
	    	sql.append("                                            7,                                   \n");
	    	sql.append("                                            0,                                   \n");
	    	sql.append("                                            8,                                   \n");
	    	sql.append("                                            subAcct.mbalance -                   \n");
	    	sql.append("                                            subAcct.MUNCHECKPAYMENTAMOUNT,       \n");
	    	sql.append("                                            0),                                  \n");
	    	sql.append("                                     0),                                         \n");
	    	sql.append("                                 2)) availableBalance                            \n");
	    	sql.append("                  from sett_account    acc,                                      \n");
	    	sql.append("                       sett_subaccount subAcct                                   \n");
	    	sql.append("                 where acc.nofficeid = "+lOfficeID+"                                         \n");
	    	sql.append("                   and acc.ncurrencyid = "+lCurrencyID+"                                      \n");
	    	sql.append("                   and acc.id = subAcct.naccountid(+)                            \n");
	    	sql.append("                 group by acc.id) a                                              \n");
	    	sql.append("         where a.naccountid = aa.naccountid(+)) b                                \n");
	    	sql.append(" WHERE b.nAccountId = aa.id                                                      \n");
	    	sql.append("   and ss.naccountid(+) = acct.id                                                \n");
	    	sql.append("   and acct.nofficeid = "+lOfficeID+"                                                       \n");
	    	sql.append("   and acct.nCurrencyID ="+lCurrencyID+"                                                     \n");
	    	sql.append("   and acct.id = b.naccountid(+)                                                 \n");
	    	sql.append("   and acct.nCheckStatusID = 4                                                   \n");
	    	sql.append("   and acct.id="+lAccountID+"                                                    \n");
			ps=conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				AvailableBalance = rs.getDouble("AvailableBalance");
				dFirstLimitAmount =rs.getDouble("FirstLimitAmount");
				dSecondLimitAmount =rs.getDouble("SecondLimitAmount");
				dThirdLimitAmount = rs.getDouble("ThirdLimitAmount");
				dCapitalLimitAmount = rs.getDouble("CapitalLimitAmount");
			}
			
			if(dFirstLimitAmount>0)
			{
				AvailableBalance = AvailableBalance + dFirstLimitAmount - dCapitalLimitAmount;
			}
			else if(dSecondLimitAmount>0)
			{
				AvailableBalance = AvailableBalance + dSecondLimitAmount - dCapitalLimitAmount;
			}
			else if(dThirdLimitAmount>0)
			{
				AvailableBalance = AvailableBalance + dThirdLimitAmount - dCapitalLimitAmount;
			}
			else
			{
				AvailableBalance = AvailableBalance - dCapitalLimitAmount;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}
		return AvailableBalance;
    }
	
	/**
	 * ����˵�����ʲ�ת��������ϣ�ȫ����������˻���Ϣ
	 * @param lsubAccID
	 * @return @throws
	 *         Exception
	 */
	public long clearSubAccountInterestBreakNotify(String sTransNo,long lsubAccID,double dInterest,double dPredrawInterest) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" insert into BREAKNOTIFYINTEREST (TRANSNO,SUBACCOUNTID,INTEREST,PREDRAWINTEREST) values ('"+sTransNo+"',"+lsubAccID+","+dInterest+","+dPredrawInterest+")");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_subaccount a set a.mInterest=0.00,a.AL_MPREDRAWINTEREST =0.00 ");
			sbSQL.append(" where a.ID = "+lsubAccID);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lsubAccID;
	}
	/**
	 * ����˵�����ʲ�ת��������ϣ�ȫ����������˻���Ϣ
	 * @param lsubAccID
	 * @return @throws
	 *         Exception
	 */
	public long comebackSubAccountInterestBreakNotify(String sTransNo,long lsubAccID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_subaccount  set (mInterest,AL_MPREDRAWINTEREST) = (select INTEREST,PREDRAWINTEREST from BREAKNOTIFYINTEREST where TRANSNO = '"+sTransNo+"' and SUBACCOUNTID = " + lsubAccID+")");
			sbSQL.append(" where ID = "+lsubAccID);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			
			sbSQL = new StringBuffer();
			sbSQL.append(" delete BREAKNOTIFYINTEREST where TRANSNO = '"+sTransNo+"' and SUBACCOUNTID = " + lsubAccID);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lsubAccID;
	}
}