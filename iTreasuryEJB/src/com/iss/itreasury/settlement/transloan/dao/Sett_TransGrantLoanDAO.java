/*
 * �������� 2003-10-8
 */
package com.iss.itreasury.settlement.transloan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.loanpaynotice.dataentity.PayNoticeRateInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
/**
 * ����Ž��ױ�DAO
 * @author yqwu
 */
public class Sett_TransGrantLoanDAO extends SettlementDAO
{
	private final static StringBuffer sbGrantSQL = new StringBuffer(128);
	/**
		 * ���׺�
		 */
	public final static int ORDER_TRANS_NO = 1;
	private final static String ORDER_TRANS_NO_NAME = "sTransNo";
	/**
	 * ��������
	 */
	public final static int ORDER_TRANSACTION_TYPE_ID = 2;
	private final static String ORDER_TRANSACTION_TYPE_ID_NAME = "nTransactionTypeID";
	/**
	 * �����˻�
	 */
	public final static int ORDER_LOAN_ACCOUNT_ID = 3;
	private final static String ORDER_LOAN_ACCOUNT_ID_NAME = "nLoanAccountID";
	/**
	 * �����ͬ��
	 */
	public final static int ORDER_LOAN_CONTRACT_ID = 4;
	private final static String ORDER_LOAN_CONTRACT_ID_NAME = "nLoanContractID";
	/**
	 * �ſ�֪ͨ��
	 */
	public final static int ORDER_LOAN_NOTE_ID = 5;
	private final static String ORDER_LOAN_NOTE_ID_NAME = "nLoanNoteID";
	/**
	 * ���ڴ���˻�ID
	 */
	public final static int ORDER_DEPOSIT_ACCOUNT_ID = 6;
	private final static String ORDER_DEPOSIT_ACCOUNT_ID_NAME = "nDepositAccountID";
	/**
	 * ���
	 */
	public final static int ORDER_AMOUNT = 7;
	private final static String ORDER_AMOUNT_NAME = "mAmount";
	/**
	 * ��Ϣ��
	 */
	public final static int ORDER_INTEREST_START = 8;
	private final static String ORDER_INTEREST_START_NAME = "dtInterestStart";
	/**
	 * ժҪ
	 */
	public final static int ORDER_ABSTRACT = 9;
	private final static String ORDER_ABSTRACT_NAME = "sAbstract";
	/**
	 * ����״̬
	 */
	public final static int ORDER_STATUS = 10;
	private final static String ORDER_STATUS_NAME = "nStatusID";
	/**
	 * �޸�ʱ��
	 */
	public final static int ORDER_MODIFY = 11;
	/**
	 * ¼������
	 */
	public final static int ORDER_INPUT_DATE = 12;
	private static Logger logger = Logger.getLogger(Sett_TransGrantLoanDAO.class);
	static {
		sbGrantSQL.append("ID,NOFFICEID,NCURRENCYID, STRANSNO, NTRANSACTIONTYPEID, \n");
		sbGrantSQL.append("NLOANACCOUNTID,NLOANCONTRACTID, NLOANNOTEID, NEXTENDFORMID, NCONSIGNDEPOSITACCOUNTID,\n");
		sbGrantSQL.append("NISKEEPACCOUNT, NPAYINTERESTACCOUNTID, NRECEIVEINTERESTACCOUNTID,NPAYSURETYFEEACCOUNTID, NRECEIVESURETYFEEACCOUNTID,\n");
		sbGrantSQL.append("NPAYCOMMISIONACCOUNTID,MINTERESTTAXRATE, DTINTERESTTAXRATEVAULEDATE, NDEPOSITACCOUNTID, NPAYTYPEID,\n");
		sbGrantSQL.append("NBANKID, SEXTACCTNO, SEXTACCTNAME, SBANKNAME, SPROVINCE, \n");
		sbGrantSQL.append("SCITY, MAMOUNT,NCASHFLOWID, DTINTERESTSTART, DTEXECUTE,\n");
		sbGrantSQL.append("DTMODIFY, DTINPUT, NINPUTUSERID,NCHECKUSERID, NABSTRACTID,  \n");
		sbGrantSQL.append("SABSTRACT, SCHECKABSTRACT, NSTATUSID, SEXTBANKNO ,nInterestTaxPlanId \n");
	}
	/**
	 * ���Ӵ���ż�¼
	 * @param info TransGrantLoanInfo
	 * @return TransGrantLoanInfo ID
	 * @throws SQLException
	 */
	public long add(TransGrantLoanInfo info) throws SQLException
	{
		long id = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer(128);
		buffer.append("insert into SETT_TRANSGRANTLOAN \n");
		buffer.append("(  \n");
		buffer.append(sbGrantSQL.toString());
		buffer.append(" ) \n");
		buffer.append("\n");
		buffer.append("values \n");
		buffer.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?)");
		try
		{
			conn = this.getConnection();
			id = getNextID(conn);
			info.setID(id);
			int index = 1;
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setLong(index++, info.getID());
			pstmt.setLong(index++, info.getOfficeID());
			pstmt.setLong(index++, info.getCurrencyID());
			pstmt.setString(index++, info.getTransNo());
			pstmt.setLong(index++, info.getTransactionTypeID());
			pstmt.setLong(index++, info.getLoanAccountID());
			pstmt.setLong(index++, info.getLoanContractID());
			pstmt.setLong(index++, info.getLoanNoteID());
			pstmt.setLong(index++, info.getExtendFormID());
			pstmt.setLong(index++, info.getConsignDepositAccountID());
			pstmt.setLong(index++, info.isKeepAccount() ? 1 : 0);
			pstmt.setLong(index++, info.getPayInterestAccountID());
			pstmt.setLong(index++, info.getReceiveInterestAccountID());
			pstmt.setLong(index++, info.getPaySuretyFeeAccountID());
			pstmt.setLong(index++, info.getReceiveSuretyFeeAccountID());
			pstmt.setLong(index++, info.getPayCommisionAccountID());
			pstmt.setDouble(index++, info.getInterestTaxRate());
			pstmt.setTimestamp(index++, info.getInterestTaxRateVauleDate());
			pstmt.setLong(index++, info.getDepositAccountID());
			pstmt.setLong(index++, info.getPayTypeID());
			pstmt.setLong(index++, info.getBankID());
			pstmt.setString(index++, info.getExtAcctNo());
			pstmt.setString(index++, info.getExtAcctName());
			pstmt.setString(index++, info.getBankName());
			pstmt.setString(index++, info.getProvince());
			pstmt.setString(index++, info.getCity());
			pstmt.setDouble(index++, info.getAmount());
			pstmt.setLong(index++, info.getCashFlowID());
			pstmt.setTimestamp(index++, info.getInterestStart());
			pstmt.setTimestamp(index++, info.getExecute());
			//pstmt.setTimestamp(index++, info.getModify());
			pstmt.setTimestamp(index++, info.getInputDate());
			pstmt.setLong(index++, info.getInputUserID());
			pstmt.setLong(index++, info.getCheckUserID());
			pstmt.setLong(index++, info.getAbstractID());
			pstmt.setString(index++, info.getAbstract());
			pstmt.setString(index++, info.getCheckAbstract());
			pstmt.setLong(index++, info.getStatusID());
			pstmt.setString(index++, info.getExtBankNo());
			pstmt.setLong(index++, info.getInterestTaxPlanId());
			log.debug(buffer.toString());
			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return id;
	}
	/**
	 * �޸Ĵ���ż�¼��״̬
	 * @param id long
	 * @param statusId long
	 * @return id
	 * @throws SQLException
	 */
	public long updateStatus(long id, long statusId) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update SETT_TRANSGRANTLOAN set NSTATUSID=?,dtModify=sysdate where ID=?");
			pstmt.setLong(1, statusId);
			pstmt.setLong(2, id);
			pstmt.execute();

			returnValue = id;
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}

	public long updateContractStatus(long id, long statusId) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "loan_contractForm set nStatusId=? where id=?");
			pstmt.setLong(1, statusId);
			pstmt.setLong(2, id);
			pstmt.execute();

			returnValue = id;
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}
	/**
		 * �޸Ĵ���ż�¼��״̬
		 * @param id long
		 * @param statusId long
		 * @param strAbstract String
		 * @param lUserID long
		 * @return id
		 * @throws SQLException
		 */
	public long updateStatus(long id, long statusId, String strAbstract, long lUserID) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update SETT_TRANSGRANTLOAN set NSTATUSID=?,sCheckAbstract=?,nCheckUserID=?,dtModify=sysdate where ID=? ");
			pstmt.setLong(1, statusId);
			pstmt.setString(2, strAbstract);
			pstmt.setLong(3, lUserID);
			pstmt.setLong(4, id);
			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}
	/**
	 * �޸Ĵ���ż�¼
	 * @param info TransGrantLoanInfo
	 * @return id
	 * @throws SQLException
	 */
	public long update(TransGrantLoanInfo info) throws SQLException
	{
		long returnValue = -1;
		Connection conn = null;
		int index = 1;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("update SETT_TRANSGRANTLOAN set \n");
			sbSQL.append("dtModify=sysdate, \n");
			sbSQL.append("NOFFICEID=?, NCURRENCYID=?, STRANSNO=?, NTRANSACTIONTYPEID=?, \n");
			sbSQL.append("NLOANACCOUNTID=?, NLOANCONTRACTID=?, NLOANNOTEID=?, \n");
			sbSQL.append("NEXTENDFORMID=?, NCONSIGNDEPOSITACCOUNTID=?, NISKEEPACCOUNT=?, \n");
			sbSQL.append("NPAYINTERESTACCOUNTID=?, NRECEIVEINTERESTACCOUNTID=?, \n");
			sbSQL.append("NPAYSURETYFEEACCOUNTID=?, NRECEIVESURETYFEEACCOUNTID=?, \n");
			sbSQL.append("NPAYCOMMISIONACCOUNTID=?, MINTERESTTAXRATE=?, \n");
			sbSQL.append("DTINTERESTTAXRATEVAULEDATE=?, NDEPOSITACCOUNTID=?, \n");
			sbSQL.append("NPAYTYPEID=?, NBANKID=?, SEXTACCTNO=?, SEXTACCTNAME=?, \n");
			sbSQL.append("SBANKNAME=?, SPROVINCE=?, SCITY=?, MAMOUNT=?, \n");
			sbSQL.append("NCASHFLOWID=?, DTINTERESTSTART=?, DTEXECUTE=?,\n");
			sbSQL.append("DTINPUT=?, NINPUTUSERID=?, \n");
			sbSQL.append("NCHECKUSERID=?, NABSTRACTID=?, SABSTRACT=?,\n");
			sbSQL.append("SCHECKABSTRACT=?, SEXTBANKNO=?  ,nInterestTaxPlanId = ? \n");
			sbSQL.append("where ID=?\n");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setLong(index++, info.getOfficeID());
			pstmt.setLong(index++, info.getCurrencyID());
			pstmt.setString(index++, info.getTransNo());
			pstmt.setLong(index++, info.getTransactionTypeID());
			pstmt.setLong(index++, info.getLoanAccountID());
			pstmt.setLong(index++, info.getLoanContractID());
			pstmt.setLong(index++, info.getLoanNoteID());
			pstmt.setLong(index++, info.getExtendFormID());
			pstmt.setLong(index++, info.getConsignDepositAccountID());
			pstmt.setLong(index++, info.isKeepAccount() ? 1 : 0);
			pstmt.setLong(index++, info.getPayInterestAccountID());
			pstmt.setLong(index++, info.getReceiveInterestAccountID());
			pstmt.setLong(index++, info.getPaySuretyFeeAccountID());
			pstmt.setLong(index++, info.getReceiveSuretyFeeAccountID());
			pstmt.setLong(index++, info.getPayCommisionAccountID());
			pstmt.setDouble(index++, info.getInterestTaxRate());
			pstmt.setTimestamp(index++, info.getInterestTaxRateVauleDate());
			pstmt.setLong(index++, info.getDepositAccountID());
			pstmt.setLong(index++, info.getPayTypeID());
			pstmt.setLong(index++, info.getBankID());
			pstmt.setString(index++, info.getExtAcctNo());
			pstmt.setString(index++, info.getExtAcctName());
			pstmt.setString(index++, info.getBankName());
			pstmt.setString(index++, info.getProvince());
			pstmt.setString(index++, info.getCity());
			pstmt.setDouble(index++, info.getAmount());
			pstmt.setLong(index++, info.getCashFlowID());
			pstmt.setTimestamp(index++, info.getInterestStart());
			pstmt.setTimestamp(index++, info.getExecute());
			pstmt.setTimestamp(index++, info.getInputDate());
			pstmt.setLong(index++, info.getInputUserID());
			pstmt.setLong(index++, info.getCheckUserID());
			pstmt.setLong(index++, info.getAbstractID());
			pstmt.setString(index++, info.getAbstract());
			pstmt.setString(index++, info.getCheckAbstract());
			pstmt.setString(index++,info.getExtBankNo());
			pstmt.setLong(index++, info.getInterestTaxPlanId());
			pstmt.setLong(index++, info.getID());
			log.debug(sbSQL.toString());
			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return returnValue;
	}

	/**
	 * ���ſ�֪ͨ����״̬�Ƿ�����
	 * @param lPayFormID
	 * @return
	 * @throws SQLException
	 */
	public boolean checkPayForm(long lPayFormID,long lStatusToCheck)throws SQLException{
		boolean blnIsOK=false;
		Connection conn=null;
		try{
			Log.print("У��ſ�֪ͨ��״̬......");
			Log.print("�ſ�֪ͨ��ID:"+lPayFormID);
			conn = this.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSql = "select nstatusid from loan_payform where id="+lPayFormID
				+" and nstatusid="+lStatusToCheck;
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			if (rs.next()){
				blnIsOK = true;
			}
			this.cleanup(rs);
			this.cleanup(ps);
		}catch(SQLException sqlExp){throw sqlExp;}
		finally{
			this.cleanup(conn);
		}
		return blnIsOK;
	}
	/**
	 * ͨ�����׺Ų��ҽ���ID 
	 * @param id ID
	 * @return ����ż�¼
	 * @throws SQLException
	 */
	public long getIDByTransNo(String strTransNo) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		long lID = -1;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT ID from SETT_TRANSGRANTLOAN where sTransNo=? ");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setString(1, strTransNo);
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				lID = rset.getLong("ID");
			}
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return lID;
	}	
	
	/**
		 * ͨ��id���Ҵ���ż�¼ 
		 * @param id ID
		 * @return ����ż�¼
		 * @throws SQLException
		 */
	public TransGrantLoanInfo findByID(long id) throws SQLException
	{
		TransGrantLoanInfo info = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT \n");
			sbSQL.append(sbGrantSQL.toString());
			sbSQL.append("FROM SETT_TRANSGRANTLOAN");
			sbSQL.append(" where id=?");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setLong(1, id);
			rset = pstmt.executeQuery();
			if (rset != null && rset.next())
			{
				info = resultToInfo(rset);
			}
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return info;
	}
	
	/*
	 * ͨ���ſ�֪ͨ�����Ҵ���ż�¼
	 * @param lLoanNoteID �ſ�֪ͨ��ID
	 * @return ����ż�¼
	 * @throws SQLException
	 */
	public TransGrantLoanInfo findByLoanNoteID(long lLoanNoteID) throws SQLException
	{
		TransGrantLoanInfo info = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT \n");
			sbSQL.append(sbGrantSQL.toString());
			sbSQL.append("FROM SETT_TRANSGRANTLOAN ");
			sbSQL.append("WHERE NLOANNOTEID = ? ");
			pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setLong(1, lLoanNoteID);
			rset = pstmt.executeQuery();
			Log.print("���ݷſ�֪ͨ��ID�������д������Ϣ2:" + lLoanNoteID);
			if (rset != null && rset.next())
			{
				info = resultToInfo(rset);
			}
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return info;
	}
	
	/**
	 * �����������Ҵ���ż�¼
	 * @param info TransGrantLoanInfo
	 * @param orderType long
	 * @param isDesc boolean
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByCondition(TransGrantLoanInfo info) throws Exception
	{
		List result = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			Log.print("**���뷽����Sett_TransGrantLoanDAO.findByCondition()***");

			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT \n");
			sbSQL.append(sbGrantSQL.toString());
			sbSQL.append("FROM SETT_TRANSGRANTLOAN");
			sbSQL.append(" where nOfficeID = " + info.getOfficeID());
			sbSQL.append(" and nCurrencyID = " + info.getCurrencyID());
			sbSQL.append(" and nTransactionTypeID = " + info.getTransactionTypeID());
			if (info.getInputUserID() > 0)
			{
				sbSQL.append(" and nInputUserID =" + info.getInputUserID() + " \n");
			}
			if (info.getCheckUserID() != -1)
			{
				sbSQL.append(" and nCheckUserID =" + info.getCheckUserID() + " \n");
			}
			long[] statusIDs = info.getStatusIDs();
			
			Log.print("״̬����:" + statusIDs);
						for (int i = 0; i < statusIDs.length; i++)
						{
							Log.print("statusID" + i + ":" + statusIDs[i]);
						}

						if (statusIDs != null)
						{
							boolean isStart = true;
							for (int i = 0; i < statusIDs.length; i++)
							{
								Log.print("status:" + statusIDs[i]);
								if (statusIDs[i] == SETTConstant.TransactionStatus.TEMPSAVE)
								{ //�ݴ�û��ʱ������
									if (isStart)
									{
										sbSQL.append("and (");
										isStart = !isStart;
									}
									else
									{
										sbSQL.append("or ");
									}
									sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
								}
								else
									if (statusIDs[i] == SETTConstant.TransactionStatus.SAVE)
									{ //����Ҫ�鵱���
										if (isStart)
										{
											sbSQL.append("and (");
											isStart = !isStart;
										}
										else
										{
											sbSQL.append("or ");
										}
										sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.SAVE);
										sbSQL.append(" and dtExecute=?) ");
									}
									else
										if (statusIDs[i] == SETTConstant.TransactionStatus.CHECK)
										{ //�Ѿ����˵�Ҫ�鵱��
											if (isStart)
											{
												sbSQL.append("and (");
												isStart = !isStart;
											}
											else
											{
												sbSQL.append("or ");
											}
											sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.CHECK);
											sbSQL.append(" and dtExecute=?) ");
										}
										else
										{
											sbSQL.append("and ");
											sbSQL.append("nStatusID = " + statusIDs[i]); //�հ׵�ʱ��
										}
							}
							if (!isStart)
								sbSQL.append(") ");
						}
						else
						{ //���û��statusID��Ĭ�ϲ�ȫ����Ч��¼
							sbSQL.append(" and (nStatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
							sbSQL.append(" or (nStatusID = " + SETTConstant.TransactionStatus.SAVE);
							sbSQL.append(" and dtExecute=?) ");
						}
			
			/*if (statusIDs != null)
			{
				sbSQL.append(" and (nStatusID =" + statusIDs[0] + " \n");
				for (int i = 1; i < statusIDs.length; i++)
				{
					sbSQL.append(" or nStatusID =" + statusIDs[i] + " \n");
				}
				sbSQL.append(")");
			}*/
			if (info.getOrderByType() > 0)
			{
				sbSQL.append(" order by " + getOrderColumnName((int) info.getOrderByType()));
				if (info.getAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_ASC)
				{
					sbSQL.append(" asc ");
				}
				else
				{
					sbSQL.append(" desc ");
				}
			}
			Log.print("SQL = " + sbSQL.toString());
			int intIndex=0;
			pstmt = conn.prepareStatement(sbSQL.toString());
			if (statusIDs != null)
			{ //���ִ��ʱ��
				for (int i = 0; i < statusIDs.length; i++)
				{ //����д洢��
					if (statusIDs[i] == SETTConstant.TransactionStatus.SAVE)
					{
						pstmt.setTimestamp(++intIndex, info.getExecute());
					}
					else if (statusIDs[i] == SETTConstant.TransactionStatus.CHECK)
					{
						pstmt.setTimestamp(++intIndex, info.getExecute());
					}
				}
			}
			rset = pstmt.executeQuery();
			while (rset.next())
			{
				result.add(resultToInfo(rset));
			}
		}
		finally
		{
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return result;
	}
	/**
	 * �õ�����������Ϣ
	 * @param info
	 * @return LoanConditionInfo
	 * @throws Exception
	 */
	public LoanPayFormDetailInfo findPayFormDetailByCondition(LoanPayFormDetailInfo info) throws Exception
	{
		LoanPayFormDetailInfo returnInfo = null;
		Connection conn = null;
		try
		{
			//modify by leiyang 2008/06/12
			Log.print("****���뷽����Sett_TransGrantLoanDAO.findPayFormDetailByCondition()*****");
			
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(64);
			sbSQL.append("select a.nTypeID1,a.nTypeID2,a.nTypeID3,a.dtStartDate,a.dtEndDate,a.nTypeID,b.DTSTART,b.DTEND,a.nIntervalNum,b.dtInputDate,d.sName,c.mRate,b.mInterestRate,\n");
			sbSQL.append("b.MCOMMISSIONRATE,b.MSURETYFEERATE,b.mAmount,b.dtOutDate,d.sCode dsCode,a.SCONTRACTCODE,b.SCODE bSCODE,\n");
			sbSQL.append("a.nChargeRateTypeID,a.nTypeID lnTypeID,b.nGrantTypeID,b.sReceiveAccount,b.sReceiveClientName, ");
			sbSQL.append("b.sRemitInProvince,b.sRemitInCity,b.sRemitBank,b.nGrantCurrentAccountID,b.nAccountBankID, ");
			sbSQL.append("a.nconsignclientid ,a.nsubtypeid subtypeid , b.isRemitOverDueInterest isRemitOverDueInterest ");
			sbSQL.append("from loan_contractform a,loan_payform b,loan_interestRate c,client d\n");
			sbSQL.append("where a.id = b.nContractID\n");
			sbSQL.append("and b.NBANKINTERESTID = c.id(+)\n");
			sbSQL.append("and a.nConsignClientID = d.id(+) and b.ID=?\n");

			Log.print("SQL  = " + sbSQL.toString());
			PreparedStatement pstmt = conn.prepareStatement(sbSQL.toString());
			pstmt.setLong(1, info.getLoadNoteID());
			Log.print("�ſ�֪ͨ��:" + info.getLoadNoteID());
			ResultSet rset = pstmt.executeQuery();
			int index = 1;
			if (rset.next())
			{
				returnInfo = info;
				returnInfo.setStart(rset.getTimestamp("dtStartDate"));
				returnInfo.setEnd(rset.getTimestamp("dtEndDate"));
				returnInfo.setDTSTART(rset.getTimestamp("DTSTART"));
				returnInfo.setDTEND(rset.getTimestamp("DTEND"));
				returnInfo.setLoanType(rset.getLong("nTypeID"));
				returnInfo.setLoanTerm(rset.getLong("nIntervalNum"));
				returnInfo.setClientName(rset.getString("sName"));
				
				returnInfo.setSubTypeID(rset.getLong("subtypeid"));
				
				UtilOperation uo = new UtilOperation();
				PayNoticeRateInfo rateInfo = uo.getRateValue(Constant.RateType.INTEREST,info.getLoadNoteID(),null);
				returnInfo.setInterestRate(rateInfo.getInterestRate());
				returnInfo.setOverDueInterestRate(rateInfo.getOverDueInterestRate());
				returnInfo.setIsRemitOverDueInterest(rset.getLong("isRemitOverDueInterest"));
				
				/*if (returnInfo.getLoanType()==LOANConstant.LoanType.YT
				  ||returnInfo.getLoanType()==LOANConstant.LoanType.ZGXEDQ
				  ||returnInfo.getLoanType()==LOANConstant.LoanType.ZGXEZCQ
				  ||returnInfo.getLoanType()==LOANConstant.LoanType.ZYDQ
				  ||returnInfo.getLoanType()==LOANConstant.LoanType.ZYZCQ){//��������зſ�
					returnInfo.setInterestRate(rset.getDouble("mRate"));					
				}
				else if(returnInfo.getLoanType()==LOANConstant.LoanType.WT 
					  ||returnInfo.getLoanType()==LOANConstant.LoanType.WTTJTH){//�����ί��
					returnInfo.setInterestRate(rset.getDouble("mInterestRate"));
				}*/
				
				
				returnInfo.setPoundage(rset.getDouble("MCOMMISSIONRATE"));
				returnInfo.setCommissionRate(rset.getDouble("MCOMMISSIONRATE"));
				returnInfo.setAssureRate(rset.getDouble("MSURETYFEERATE"));
				returnInfo.setAmount(rset.getDouble("mAmount"));
				returnInfo.setInterestStart(rset.getTimestamp("dtOutDate"));
				returnInfo.setClientCode(rset.getString("dsCode"));
				returnInfo.setContractCode(rset.getString("SCONTRACTCODE"));
				returnInfo.setLoanNoteCode(rset.getString("bSCODE"));
				returnInfo.setChargeRateTypeID(rset.getLong("nChargeRateTypeID"));
				returnInfo.setTypeID(rset.getLong("lnTypeID"));
				
				returnInfo.setInputDate(rset.getTimestamp("dtInputDate"));
				returnInfo.setGrantTypeID(rset.getLong("nGrantTypeID"));
				returnInfo.setReceiveAccountNo(rset.getString("sReceiveAccount"));
				returnInfo.setReceiveAccountName(rset.getString("sReceiveClientName"));
				returnInfo.setRemitInProvince(rset.getString("sRemitInProvince"));
				returnInfo.setRemitInCity(rset.getString("sRemitInCity"));
				returnInfo.setRemitBank(rset.getString("sRemitBank"));
				returnInfo.setAccountBankID(rset.getLong("nAccountBankID"));
				returnInfo.setGrantCurrentAccountID(rset.getLong("nGrantCurrentAccountID"));
				
				//������ҵ����͵������ࣨadd by Forest��
				returnInfo.setTypeID1(rset.getLong("nTypeID1"));//��������
				returnInfo.setTypeID2(rset.getLong("nTypeID2"));//��ҵ����1
				returnInfo.setTypeID3(rset.getLong("nTypeID3"));//��ҵ����2
				
				//�ͻ�ID
				returnInfo.setClientId(rset.getLong("nconsignclientid"));
			}
			this.cleanup(rset);
			this.cleanup(pstmt);

			
			/*//ȡ��������
			sbSQL = null;
			sbSQL = new StringBuffer(64);
			sbSQL.append("SELECT b.mAdjustRate \n");
			sbSQL.append("FROM loan_payform a,loan_contractform b \n");
			sbSQL.append("WHERE a.nContractID = b.ID AND a.id=? \n");

			Log.print("SQL  = " + sbSQL.toString());
			PreparedStatement pstmt2 = conn.prepareStatement(sbSQL.toString());
			pstmt2.setLong(1, info.getLoadNoteID());
			Log.print("�ſ�֪ͨ��:" + info.getLoadNoteID());
			ResultSet rset2 = pstmt2.executeQuery();
			if (rset2.next())
			{
				if (returnInfo != null)
				{
					*//**Ϊ�˵õ���ȷ������ֵ,Ҫ����ת������
					 *�������ʼ��㹫ʽ:��������*(1+��������/100)
					 *//*
					Log.print("----��ʼ��������----");
					Log.print("��������:"+returnInfo.getInterestRate());
					double dBase=returnInfo.getInterestRate();	//��������
					double dAjust=rset2.getDouble("mAdjustRate");
					double dAjusted=0.0;
					dAjusted=Arith.round(Arith.mul(dBase,Arith.add(1.0,Arith.div(dAjust,100))),6);
					//double adjustedRate = (returnInfo.getInterestRate()*(100000000+rset2.getDouble("mAdjustRate")*1000000))/100000000;
					returnInfo.setInterestRate(dAjusted);
					Log.print("���ݿ��е�������:"+rset2.getDouble("mAdjustRate"));
					Log.print("�����������:"+returnInfo.getInterestRate());
				}
			}

			this.cleanup(rset2);
			this.cleanup(pstmt2);*/
		}
		finally
		{
			this.cleanup(conn);
		}
		return returnInfo;
	}

	public void updateLoanPayFormStatus(long id, long statusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "loan_PayForm set nStatusId=? where id=?");
			pstmt.setLong(1, statusID);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}

	/**
		 * �����������Ҵ���ż�¼
		 * @param info TransGrantLoanInfo
		 * @param orderType long
		 * @param isDesc boolean
		 * @return Collection
		 * @throws Exception
		 */
	public Collection match(DAOHelper condition) throws Exception
	{
		List result = new ArrayList();
		Connection conn = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try
		{
			Log.print("����DAO��match......");
			conn = this.getConnection();
			StringBuffer sbSQL = new StringBuffer(128);
			sbSQL.append("SELECT \n");
			sbSQL.append(sbGrantSQL.toString());
			sbSQL.append("FROM SETT_TRANSGRANTLOAN");
			condition.setSqlString(sbSQL.toString());
			condition.setOrderColumnName(getOrderColumnName(condition.getOrderType()));
			pstmt = DAOHelper.buildPreparedStatemnet(conn, condition);
			rset = pstmt.executeQuery();
			while (rset.next())
			{
				result.add(resultToInfo(rset));
			}
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		finally
		{
		    this.cleanup(rset);		
		    this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return result;
	}

	/**
		 * ��ResultSetת��ΪTransGrantLoanInfo 
		 * @param rset ResultSet
		 * @return TransGrantLoanInfo
		 * @throws SQLException
		 */
	private static TransGrantLoanInfo resultToInfo(ResultSet rset) throws SQLException
	{
		TransGrantLoanInfo info = new TransGrantLoanInfo();
		info.setID(rset.getLong(1));
		info.setOfficeID(rset.getLong(2));
		info.setCurrencyID(rset.getLong(3));
		info.setTransNo(rset.getString(4) == null ? "" : rset.getString(4));
		info.setTransactionTypeID(rset.getLong(5));
		info.setLoanAccountID(rset.getLong(6));
		info.setLoanContractID(rset.getLong(7));
		info.setLoanNoteID(rset.getLong(8));
		info.setExtendFormID(rset.getLong(9));
		info.setConsignDepositAccountID(rset.getLong(10));
		info.setKeepAccount(rset.getLong(11) == 1 ? true : false);
		info.setPayInterestAccountID(rset.getLong(12));
		info.setReceiveInterestAccountID(rset.getLong(13));
		info.setPaySuretyFeeAccountID(rset.getLong(14));
		info.setReceiveSuretyFeeAccountID(rset.getLong(15));
		info.setPayCommisionAccountID(rset.getLong(16));
		info.setInterestTaxRate(rset.getDouble(17));
		info.setInterestTaxRateVauleDate(rset.getTimestamp(18));
		info.setDepositAccountID(rset.getLong(19));
		info.setPayTypeID(rset.getLong(20));
		info.setBankID(rset.getLong(21));
		info.setExtAcctNo(rset.getString(22));
		info.setExtAcctName(rset.getString(23));
		info.setBankName(rset.getString(24) == null ? "" : rset.getString(24));
		info.setProvince(rset.getString(25));
		info.setCity(rset.getString(26) == null ? "" : rset.getString(26));
		info.setAmount(rset.getDouble(27));
		info.setCashFlowID(rset.getLong(28));
		info.setInterestStart(rset.getTimestamp(29));
		info.setExecute(rset.getTimestamp(30));
		info.setModify(rset.getTimestamp(31));
		info.setInputDate(rset.getTimestamp(32));
		info.setInputUserID(rset.getLong(33));
		info.setCheckUserID(rset.getLong(34));
		info.setAbstractID(rset.getLong(35));
		info.setAbstract(rset.getString(36));
		info.setCheckAbstract(rset.getString(37));
		info.setStatusID(rset.getLong(38));
		info.setExtBankNo(rset.getString(39));
		info.setInterestTaxPlanId(rset.getLong("nInterestTaxPlanId"));
		return info;
	}
	/**
	 * �õ���һ��ID
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private static long getNextID(Connection conn) throws SQLException
	{
		long id = -1;
		String sqlString = "select SEQ_TRANSGRANTLOANID.nextval from dual";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			pstmt = conn.prepareStatement(sqlString);
			rset = pstmt.executeQuery();
			if (rset.next())
			{
				id = rset.getLong(1);
			}
			rset.close();
            rset = null;
            pstmt.close();
		    pstmt = null; //��̬�������޷�ֱ������cleanup()����
		}
			catch(Exception e)
		{
		}
		finally
		{
		    try
			{
		        if (rset != null)
				{
		            rset.close();
		            rset = null;
				}
				if (pstmt != null)
				{
				    pstmt.close();
				    pstmt = null;
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return id;
	}
	/**
			 * ����������ת��Ϊ����
			 * @param orderType
			 * @return
			 */
	private static String getOrderColumnName(int orderType)
	{
		String returnValue = "";
		switch (orderType)
		{
			case ORDER_TRANS_NO :
				returnValue = ORDER_TRANS_NO_NAME;
				break;
			case ORDER_TRANSACTION_TYPE_ID :
				returnValue = ORDER_TRANSACTION_TYPE_ID_NAME;
				break;
			case ORDER_LOAN_ACCOUNT_ID :
				returnValue = ORDER_LOAN_ACCOUNT_ID_NAME;
				break;
			case ORDER_LOAN_CONTRACT_ID :
				returnValue = ORDER_LOAN_CONTRACT_ID_NAME;
				break;
			case ORDER_LOAN_NOTE_ID :
				returnValue = ORDER_LOAN_NOTE_ID_NAME;
				break;
			case ORDER_DEPOSIT_ACCOUNT_ID :
				returnValue = ORDER_DEPOSIT_ACCOUNT_ID_NAME;
				break;
			case ORDER_AMOUNT :
				returnValue = ORDER_AMOUNT_NAME;
				break;
			case ORDER_INTEREST_START :
				returnValue = ORDER_INTEREST_START_NAME;
				break;
			case ORDER_ABSTRACT :
				returnValue = ORDER_ABSTRACT_NAME;
				break;
			case ORDER_STATUS :
				returnValue = ORDER_STATUS_NAME;
				break;
		}
		return returnValue;
	}

	public static void main(String[] args) throws Exception
	{
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		LoanPayFormDetailInfo info = new LoanPayFormDetailInfo();
		info.setLoadNoteID(108);
		info = dao.findPayFormDetailByCondition(info);
	}
}
