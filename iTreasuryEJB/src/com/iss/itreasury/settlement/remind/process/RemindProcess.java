package com.iss.itreasury.settlement.remind.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.extendapply.dataentity.ExtendContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.matureremind.bizlogic.MatureRemindBiz;
import com.iss.itreasury.settlement.matureremind.dataentity.MatureRemindInfo;
import com.iss.itreasury.settlement.remind.resultinfo.AheadRepayFormInfo;
import com.iss.itreasury.settlement.remind.resultinfo.AtTermFixedDepositInfo;
import com.iss.itreasury.settlement.remind.resultinfo.AtTermLoanInfo;
import com.iss.itreasury.settlement.remind.resultinfo.BillConsignReceiveInfo;
import com.iss.itreasury.settlement.remind.resultinfo.BillRemindInfo;
import com.iss.itreasury.settlement.remind.resultinfo.EnsureDepositInfo;
import com.iss.itreasury.settlement.remind.resultinfo.FreeFormInfo;
import com.iss.itreasury.settlement.remind.resultinfo.FreezeInfo;
import com.iss.itreasury.settlement.remind.resultinfo.LossInfo;
import com.iss.itreasury.settlement.remind.resultinfo.NegotiationInfo;
import com.iss.itreasury.settlement.remind.resultinfo.OBTransactionInfo;
import com.iss.itreasury.settlement.remind.resultinfo.OverDraftAccountInfo;
import com.iss.itreasury.settlement.remind.resultinfo.PrimnessInfo;
import com.iss.itreasury.settlement.remind.resultinfo.RemindAssemble;
import com.iss.itreasury.settlement.remind.resultinfo.SecTransactionInfo;
import com.iss.itreasury.settlement.remind.resultinfo.SecUncheckTransInfo;
import com.iss.itreasury.settlement.remind.resultinfo.UncheckTransactionInfo;
import com.iss.itreasury.settlement.remind.resultinfo.UngrantPayFormInfo;
import com.iss.itreasury.settlement.transupsave.bizlogic.TransUpSaveBiz;
import com.iss.itreasury.settlement.transupsave.dataentity.transupsaveinfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.loan.query.dataentity.QueryPayformOverdue;
 
/**
 * <p>
 * Title: �ػ��߳�
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: isoftstone
 * </p>
 * 
 * @author ��yychen
 * @version 1.0
 */
public class RemindProcess extends SettlementDAO {
	public RemindProcess(Connection conn) {
		super(conn);
	}

	public RemindProcess() {
	}

	/**
	 * �������δ�����֤ȯ����ҵ��ļ�¼��������¼������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllSecTransaction(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		String strDate = "";
		try {
			Log.print("***���뷽�� -- getAllSecTransaction ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			strDate = Env.getSystemDateString(); // ������ʱ��
			sbSQL
					.append(" select n.id NoticeID,n.Code NoticeNo,busitype.Name SecTransTypeDesc,transtype.CapitalDirection CapitalType, \n");
			sbSQL
					.append("   b.sName CompanyBankName,cb.BankName OBankName,cb.BankAccountName OBankAccountName, \n");
			sbSQL.append("   round(d.NetIncome,2) PlanAmount,n.executedate PayOrReceiveDate \n");
			sbSQL
					.append(" from sec_NoticeForm n,sec_DeliverYorder d,sett_Branch b,SEC_TransactionType transtype,SEC_BusinessType busitype,SEC_CounterpartBankAccount cb \n");
			sbSQL
					.append(" where n.DELIVERYORDERID=d.id and n.CompanyBankID=b.ID(+) and n.TransactionTypeid=transtype.ID(+) \n");
			sbSQL
					.append(" and transtype.BusinessTypeID=busitype.ID(+)  and n.CounterpartBankID=cb.ID(+) and n.StatusID>0 \n");
			sbSQL.append(" and d.OfficeID = " + assemble.getOfficeID() + " and d.CurrencyID="
					+ assemble.getCurrencyID() + " and n.StatusID="
					+ SECConstant.NoticeFormStatus.CHECKED + " \n");
			// sbSQL.append(" and transtype.CapitalDirection in ("+
			// SECConstant.Direction.FINANCE_PAY+ ","+
			// SECConstant.Direction.FINANCE_RECEIVE+ ") \n");
			sbSQL.append(" and transtype.CapitalDirection in (" + SECConstant.Direction.FINANCE_PAY
					+ "," + SECConstant.Direction.FINANCE_RECEIVE + ","
					+ SECConstant.Direction.PAY_AND_FINANCE_RECEIVE + ","
					+ SECConstant.Direction.RECEIVE_AND_FINANCE_PAY + ") \n");
			// sbSQL.append(" and n.executedate=to_date('" + strDate+
			// "','yyyy-mm-dd') \n");
			sbSQL.append(" order by n.Code \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";

			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setSecTransactionCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getSecTransactionCount() > 0 && bIsNeedDetail == true) {
				SecTransactionInfo[] infos = new SecTransactionInfo[(int) assemble
						.getSecTransactionCount()];
				log.debug("getAllSecTransaction():" + sbSQL);
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					SecTransactionInfo info = new SecTransactionInfo();
					info.setSecNoticeID(rs.getLong("NoticeID"));
					info.setSecNoticeNo(rs.getString("NoticeNo"));
					info.setTransTypeDesc(rs.getString("SecTransTypeDesc"));
					info.setCapitalType(rs.getLong("CapitalType"));
					info.setCompanyBankName(rs.getString("CompanyBankName"));
					info.setCounterBankName(rs.getString("OBankName"));
					info.setCounterBankAccountName(rs.getString("OBankAccountName"));
					info.setAmount(rs.getDouble("PlanAmount"));
					info.setExecuteDate(rs.getTimestamp("PayOrReceiveDate"));
					infos[i] = info;
					i++;
				}
				assemble.setSecTransactions(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * �������δ�������������ҵ��ļ�¼��������¼������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllOBTransaction(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
	
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		String strDate = "";
		try {
			//System.out.println("***���뷽�� -- getAllOBTransaction ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			strDate = Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID());
			//System.out.println("strDate======="+strDate);
			//sbSQL.append(" SELECT  fin.id as ID, fin.nTransType as TransType, \n");
			sbSQL.append(" SELECT  fin.id as ID, fin.nTransType as TransType, \n");
			//sbSQL.append(" c1.sName as PayerClientName,fin.nPayerAcctID as PayerAcctID,acc1.sAccountNo PayerAcctNo, \n");
			sbSQL.append(" c1.sName as PayerClientName,fin.nPayerAcctID as PayerAcctID,acc1.sAccountNo PayerAcctNo, \n");
			//sbSQL.append(" acc2.sPayeeName as PayeeClientName,fin.nPayeeAcctID as PayeeAcctID,acc2.sPayeeAcctNo PayeeAcctNo, \n");
			sbSQL.append(" c2.sName as PayeeClientName,fin.nPayeeAcctID as PayeeAcctID,acc2.sAccountNo PayeeAcctNo, \n");
			//sbSQL.append(" fin.nRemitType as RemitType,fin.mAmount as Amount,fin.mRealInterest as Interest, \n");
			sbSQL.append(" fin.nRemitType as RemitType,fin.mAmount as Amount,fin.mRealInterest as Interest, \n");
			//sbSQL.append(" fin.dtExecute as ExecuteDate,fin.sNote as Note,fin.nStatus as Status, \n");
			sbSQL.append(" fin.dtExecute as ExecuteDate,fin.sNote as Note,fin.nStatus as Status, \n");
			//sbSQL.append(" fin.cpf_nDefaultTransType as DefaultTransType \n");
			sbSQL.append(" fin.cpf_nDefaultTransType as DefaultTransType \n");
			//sbSQL.append(" FROM  OB_FinanceInstr fin,sett_account acc1,client c1,ob_payeeinfo acc2 \n");
			sbSQL.append(" FROM  OB_FinanceInstr fin,sett_account acc1,client c1,sett_account acc2,client c2  \n");
			//sbSQL.append(" where fin.NPAYERACCTID=acc1.id(+) and acc1.nclientid=c1.id(+) \n");
			sbSQL.append(" where fin.NPAYERACCTID=acc1.id(+) and acc1.nclientid=c1.id(+) \n");
			//sbSQL.append(" 	and fin.nPayeeAcctID=acc2.ID(+) \n");
			sbSQL.append(" and	fin.npayeeacctid=acc2.id(+) and acc2.nclientid=c2.id(+) \n");
			
			sbSQL.append("  and fin.cpf_nofficeid=" + assemble.getOfficeID()
					+ " and fin.nCurrencyID=" + assemble.getCurrencyID() + " \n");
			sbSQL.append("  and (fin.nStatus=" + OBConstant.SettInstrStatus.CHECK
					+ " or fin.nStatus =" + OBConstant.SettInstrStatus.SIGN
					+ ") and fin.nIsCanAccept = 1 \n");
			//sbSQL.append(" and fin.dtExecute<= to_date('" + strDate + "','yyyy-mm-dd') \n");
			//modify by zwxiao 2008-3-31
			//�޸�ԭ�������ڻ����ͻ�ֻ��Ҫ��ʾ����δ���������ָ��
			//sbSQL.append(" and fin.dtExecute = to_date('" + strDate + "','yyyy-mm-dd') \n");	
			//modify by xwhe 2008-04-16
			//����ʾ�޸�Ϊ�������ļ�
			boolean bIsValid = Config.getBoolean(Config.SETTLEMENT_REMIND_INSTRUCTION, false);
			if(bIsValid){
				sbSQL.append(" and fin.dtExecute = to_date('" + strDate + "','yyyy-mm-dd') \n");
				//sbSQL.append(" and 1=1 \n");
			}
			else
			{
				sbSQL.append(" and fin.dtExecute<= to_date('" + strDate + "','yyyy-mm-dd') \n");
			}
			sbSQL.append(" order by ID desc \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			log.debug("getAllOBTransaction():" + strCountSQL);
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setOBTransactionCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getOBTransactionCount() > 0 && bIsNeedDetail == true) {
				OBTransactionInfo[] infos = new OBTransactionInfo[(int) assemble
						.getOBTransactionCount()];
				log.debug("getAllOBTransaction():" + sbSQL);
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					OBTransactionInfo info = new OBTransactionInfo();
					info.setInstructionID(rs.getLong("ID"));
					info.setInstructionNo(rs.getString("ID"));
					info.setInstructionTypeID(rs.getLong("TransType"));
					info.setInstructionStatusID(rs.getLong("Status"));
					info.setAbstract(rs.getString("Note"));
					info.setAmount(rs.getDouble("Amount"));
					info.setExecute(rs.getTimestamp("ExecuteDate"));
					info.setPayAccountID(rs.getLong("PayerAcctID"));
					info.setPayAccountNo(rs.getString("PayerAcctNo"));
					info.setPayClientName(rs.getString("PayerClientName"));
					info.setReceiveAccountID(rs.getLong("PayeeAcctID"));
					info.setReceiveAccountNo(rs.getString("PayeeAcctNo"));
					info.setReceiveClientName(rs.getString("PayeeClientName"));
					info.setRemitTypeID(rs.getLong("RemitType"));
					info.setTransactionTypeID(rs.getLong("DefaultTransType"));
					infos[i] = info;
					i++;
				}
				assemble.setOBTransactions(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * �������δ���ˡ�δ���˵ļ�¼��������¼������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllUncheckRecord(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ( \n");
			sbSQL.append("   select * from SETT_VTRANSACTION \n");
			sbSQL.append("   where OfficeID=" + assemble.getOfficeID() + " and CurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append("         and StatusID=" + SETTConstant.TransactionStatus.SAVE + " \n");
			// sbSQL.append(" and TransactionTypeID not in (" +
			// SETTConstant.TransactionType.ONETOMULTI + "," +
			// SETTConstant.TransactionType.MULTILOANRECEIVE + ") \n");
			sbSQL.append("   union \n");
			sbSQL.append("   select * from SETT_VTRANSACTION \n");
			sbSQL.append("   where OfficeID=" + assemble.getOfficeID() + " and CurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append("         and StatusID=" + SETTConstant.TransactionStatus.CHECK + " \n");
			sbSQL.append("         and TransactionTypeID in ("
					+ SETTConstant.TransactionType.ONETOMULTI + ","
					+ SETTConstant.TransactionType.MULTILOANRECEIVE + ") \n");
			sbSQL.append(" )\n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";

			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setUncheckRecordCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getUncheckRecordCount() > 0 && bIsNeedDetail == true) {
				UncheckTransactionInfo[] infos = new UncheckTransactionInfo[(int) assemble
						.getUncheckRecordCount()];

				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					UncheckTransactionInfo info = new UncheckTransactionInfo();
					info.setID(rs.getLong("ID"));
					info.setTransNo(rs.getString("TRANSNO"));
					info.setTransactionTypeID(rs.getLong("TRANSACTIONTYPEID"));
					info.setPayAccountNo(rs.getString("PAYACCOUNTNO"));
					info.setReceiveAccountNo(rs.getString("RECEIVEACCOUNTNO"));
					info.setAmount(rs.getDouble("PAYAMOUNT"));
					info.setStartInterestDate(rs.getTimestamp("INTERESTSTART"));
					info.setExecuteDate(rs.getTimestamp("EXECUTE"));
					info.setAbstract(rs.getString("Abstract"));
					info.setStatusID(rs.getLong("StatusID"));
					infos[i] = info;
					i++;
				}
				assemble.setUncheckTransInfoS(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * �������֤ȯҵ��δ���ˡ��ݴ�ļ�¼��������¼������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllSecUncheckRecord(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			Log.print("***���뷽�� -- getAllSecUncheckRecord ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select s.*,b.sName BankName,n.Code NoticeNo from sett_TransSecurities s,sett_Branch b,sec_NoticeForm n \n");
			sbSQL
					.append(" where s.nSecuritiesNoticeID=n.ID(+) and s.nbankid=b.ID(+) and s.nOfficeID="
							+ assemble.getOfficeID()
							+ " and s.nCurrencyID="
							+ assemble.getCurrencyID() + " \n");
			sbSQL.append("   and s.nStatusID in (" + SETTConstant.TransactionStatus.TEMPSAVE + ","
					+ SETTConstant.TransactionStatus.SAVE + ") \n");
			sbSQL.append(" order by s.sTransNo \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			log.debug("getAllSecUncheckRecord():" + strCountSQL);
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setSecUncheckRecordCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getSecUncheckRecordCount() > 0 && bIsNeedDetail == true) {
				SecUncheckTransInfo[] infos = new SecUncheckTransInfo[(int) assemble
						.getSecUncheckRecordCount()];
				log.debug("getAllSecUncheckRecord():" + sbSQL);
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					SecUncheckTransInfo info = new SecUncheckTransInfo();
					info.setID(rs.getLong("id"));
					info.setExecuteDate(rs.getTimestamp("dtExecute"));
					info.setTransNo(rs.getString("sTransNo"));
					info.setSecNoticeNo(rs.getString("NoticeNo"));
					info.setTransTypeID(rs.getLong("nTransactionTypeID"));
					info.setCompanyBankName(rs.getString("BankName"));
					info.setAmount(rs.getDouble("mAmount"));
					info.setStatusID(rs.getLong("nStatusID"));
					info.setAbstract(rs.getString("sAbstract"));
					info.setInputUserID(rs.getLong("nInputUserID"));
					infos[i] = info;
					i++;
				}
				assemble.setSecUncheckTransInfos(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * �������͸֧�˻�������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllOverDraftAccount(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			Log.print("***���뷽�� -- getAllOverDraftAccount ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("select a.NCURRENCYID,a.SACCOUNTNO,a.SNAME,\n");
			sbSQL.append("a.NACCOUNTTYPEID,a.NSTATUSID,\n");
			sbSQL.append("sa.MBALANCE,c.SCODE AS ClientCode,c.SNAME AS ClientName \n");
			sbSQL.append("from sett_Account a,sett_subaccount sa,client c \n");
			sbSQL.append(" where sa.nAccountID=a.ID and a.nOfficeID=" + assemble.getOfficeID()
					+ " and a.nCurrencyID=" + assemble.getCurrencyID() + " \n");
			sbSQL.append("   and sa.mBalance < 0 and a.nStatusID="
					+ SETTConstant.AccountStatus.NORMAL + " \n");
			sbSQL.append(" and c.id=a.nclientid \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			log.debug("getAllOverDraftAccount():\n" + strCountSQL);
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setOverDraftAccountCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getOverDraftAccountCount() > 0 && bIsNeedDetail == true) {
				OverDraftAccountInfo[] infos = new OverDraftAccountInfo[(int) assemble
						.getOverDraftAccountCount()];
				log.debug("getAllOverDraftAccount():\n" + sbSQL);
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					OverDraftAccountInfo info = new OverDraftAccountInfo();
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
					info.setAccountNo(rs.getString("SACCOUNTNO"));
					info.setAccountName(rs.getString("SNAME"));
					info.setAccountTypeID(rs.getLong("NACCOUNTTYPEID"));
					info.setStatusID(rs.getLong("NSTATUSID"));
					info.setBalance(rs.getDouble("MBALANCE"));
					info.setClientCode(rs.getString("ClientCode"));
					info.setClientName(rs.getString("ClientName"));
					// TODO:��������
					infos[i] = info;
					i++;
				}
				assemble.setOverDraftAccounts(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * �������δ����ķſ�֪ͨ��������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllUngrantPayForm(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			Log.print("***���뷽�� -- getAllUngrantPayForm ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select client.ID ClientID,client.sCode ClientNo,c.ID ContractID,c.sContractCode ContractNo,");
			sbSQL.append(" c.nTypeID ContractTypeID,p.ID,p.scode,p.dtoutDate,");
			sbSQL.append(" p.MAMOUNT,p.MINTERESTRATE MRATE,p.DTSTART,p.DTEND " );
			sbSQL.append(" from loan_PayForm p,loan_ContractForm c,Client client \n");
			sbSQL.append(" where p.nContractID=c.ID and c.nBorrowClientID=client.ID and c.nOfficeID="
							+ assemble.getOfficeID()
							+ " and c.nCurrencyID="
							+ assemble.getCurrencyID() + " \n");
			sbSQL.append("   and p.nStatusID=" + LOANConstant.LoanPayNoticeStatus.CHECK + " \n");
			sbSQL.append(" union");
			sbSQL.append(" select client.ID ClientID,client.sCode ClientNo,c.ID ContractID,c.sContractCode ContractNo,");
			sbSQL.append(" c.nTypeID ContractTypeID,d.ID,d.scode,c.dtStartDate dtoutDate,");
			sbSQL.append(" d.MAMOUNT,d.MRATE,d.DTPUBLICDATE DTSTART,d.DTATTERM DTEND " );
			sbSQL.append(" from loan_discountcredence d,loan_ContractForm c,Client client \n");
			sbSQL.append(" where d.nContractID=c.ID and c.nBorrowClientID=client.ID and c.nOfficeID="
							+ assemble.getOfficeID()
							+ " and c.nCurrencyID="
							+ assemble.getCurrencyID() + " \n");
			sbSQL.append("   and d.nStatusID=" + LOANConstant.DiscountCredenceStatus.CHECK + " \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setUngrantPayFormCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getUngrantPayFormCount() > 0 && bIsNeedDetail == true) {
				UngrantPayFormInfo[] infos = new UngrantPayFormInfo[(int) assemble
						.getUngrantPayFormCount()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					UngrantPayFormInfo info = new UngrantPayFormInfo();
					//
					info.setLClientID(rs.getLong("ClientID"));
					info.setStrClientNo(rs.getString("ClientNo"));
					info.setLContractID(rs.getLong("ContractID"));
					info.setStrContractNo(rs.getString("ContractNo"));
					info.setLPayFormID(rs.getLong("ID"));
					info.setStrPayFormNo(rs.getString("sCode"));
					info.setTsOutDate(rs.getTimestamp("dtOutDate"));
					info.setLContractTypeID(rs.getLong("ContractTypeID"));
					
					info.setAmount(rs.getDouble("MAMOUNT"));		//������
					info.setRate(rs.getDouble("MRATE"));		//��������
					info.setDtStart(rs.getTimestamp("DTSTART"));		//���ʼ��
					info.setDtEnd(rs.getTimestamp("DTEND"));		//�������
					infos[i] = info;
					i++;
				}
				assemble.setUngrantPayForms(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * �������δ�������ǰ����֪ͨ��������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllAheadRepayForm(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllAheadRepayForm ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select c.ID ContractID,c.sContractCode ContractNo,c.nTypeID ContractTypeID,");
			sbSQL.append(" p.ID PayFormID,p.sCode PayFormNo,a.ID AheadID,a.sCode AheadNo,a.mamount,");
			sbSQL.append(" client.ID ClientID,client.sCode ClientNo,client.sName ClientName \n");
			sbSQL.append(" from loan_AheadRepayForm a,loan_ContractForm c, loan_PayForm p,Client client \n");
			sbSQL.append(" where a.nContractID=c.ID and a.nLoanPayNoticeID=p.ID and c.nBorrowClientID=client.ID \n");
			sbSQL.append("   and a.nOfficeID=" + assemble.getOfficeID() + " and a.nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append("   and a.nStatusID=" + LOANConstant.AheadRepayStatus.CHECK + " \n");
			sbSQL.append("and a.id not in( select v.npreformid from sett_transrepaymentloan v  where v.nstatusid <>0  )");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setAheadPayFormCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getAheadPayFormCount() > 0 && bIsNeedDetail == true) {
				AheadRepayFormInfo[] infos = new AheadRepayFormInfo[(int) assemble
						.getAheadPayFormCount()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					AheadRepayFormInfo info = new AheadRepayFormInfo();
					info.setAheadRepayFormID(rs.getLong("AheadID"));
					info.setAheadRepayFormNo(rs.getString("AheadNo"));
					info.setContractID(rs.getLong("ContractID"));
					info.setContractNo(rs.getString("ContractNo"));
					info.setPayFormID(rs.getLong("PayFormID"));
					info.setPayFormNo(rs.getString("PayFormNo"));
					info.setContractTypeID(rs.getLong("ContractTypeID"));
					info.setClientID(rs.getLong("ClientID"));
					info.setClientNo(rs.getString("ClientNo"));
					info.setClientName(rs.getString("ClientName"));
					info.setMamount(rs.getDouble("mamount"));
					infos[i] = info;
					i++;
				}
				assemble.setAheadPayForms(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * �������δ������⻻֪ͨ��������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllFreeForm(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllFreeForm ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select c.ID ContractID,c.sContractCode ContractNo,c.nTypeID ContractTypeID,p.ID PayFormID,p.sCode PayFormNo,a.ID FreeFormID,a.sCode FreeFormNo,client.ID ClientID,client.sCode ClientNo,client.sName ClientName \n");
			sbSQL
					.append(" from loan_FreeForm a,loan_ContractForm c, loan_PayForm p,Client client \n");
			sbSQL
					.append(" where a.nContractID=c.ID and a.nPayFormID=p.ID and c.nBorrowClientID=client.ID \n");
			sbSQL.append("   and c.nOfficeID=" + assemble.getOfficeID() + " and c.nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append("   and a.nStatusID=" + LOANConstant.FreeApplyStatus.CHECK + " \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setFreeFormCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getFreeFormCount() > 0 && bIsNeedDetail == true) {
				FreeFormInfo[] infos = new FreeFormInfo[(int) assemble.getFreeFormCount()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					FreeFormInfo info = new FreeFormInfo();
					info.setFreeFormID(rs.getLong("FreeFormID"));
					info.setFreeFormNo(rs.getString("FreeFormNo"));
					info.setContractID(rs.getLong("ContractID"));
					info.setContractNo(rs.getString("ContractNo"));
					info.setPayFormID(rs.getLong("PayFormID"));
					info.setPayFormNo(rs.getString("PayFormNo"));
					info.setContractTypeID(rs.getLong("ContractTypeID"));
					info.setClientID(rs.getLong("ClientID"));
					info.setClientNo(rs.getString("ClientNo"));
					info.setClientName(rs.getString("ClientName"));
					infos[i] = info;
					i++;
				}
				assemble.setFreeForms(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ������й涨ʱ���ڵ�չ�ں�ͬ������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllExhibitionForm(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		assemble = getRemindSettingInfo(assemble);
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		String strDate = Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID());
		try {
			// Log.print("***���뷽�� -- getAllExhibitionForm ***^^^^^^^^^^^^^^^^^^^^^"+assemble.getExhibitionDays());
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select a.minterestrate  ExtendInterestRate,c.scode strExtendCode,d.SCONTRACTCODE ContractCode,");
			sbSQL.append(" d.dtenddate ExtendEnd, b.mextendamount ExtendAmount,client.sName strClientName, ");
			sbSQL.append(" b.DTEXTENDBEGINDATE,b.DTEXTENDENDDATE \n");
			sbSQL.append(" from loan_extendform a,loan_extenddetail b, loan_extendcontract c,Client client, loan_ContractForm d \n");
			sbSQL.append(" where a.ID=b.nextendformid and  d.nBorrowClientID=client.ID and a.id = c.nExtendid and a.ncontractid = d.id \n");

			sbSQL.append("   and a.nOfficeID=" + assemble.getOfficeID() + " and a.nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append("   and  (d.dtenddate - to_date('" + strDate + "','yyyy-mm-dd')) <="
					+ assemble.getExhibitionDays() + " \n");
			sbSQL.append("   and a.nStatusID = " + LOANConstant.ExtendStatus.CHECK + " \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			//System.out.println("==========strDate=====" + strDate);
			//System.out.println("==========LOANConstant.ExtendStatus.CHECK====="
			//		+ LOANConstant.ExtendStatus.CHECK);
			ps = conn.prepareStatement(strCountSQL);
			//System.out.println("sql===" + strCountSQL);
			rs = ps.executeQuery();
			assemble.setSecExhibitionCount(rs.getRow());
			if (rs != null && rs.next()) {
				//System.out.println("==========rs.getLong(1)=====" + rs.getLong(1));
				assemble.setSecExhibitionCount(rs.getLong(1));// ȡ������
			}
			//System.out.println("==========assemble.getSecExhibitionCount()====="
			//		+ assemble.getSecExhibitionCount());
			cleanup(rs);
			cleanup(ps);
			if (assemble.getSecExhibitionCount() > 0 && bIsNeedDetail == true) {
				ExtendContractInfo[] infos = new ExtendContractInfo[(int) assemble
						.getSecExhibitionCount()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					ExtendContractInfo info = new ExtendContractInfo();

					info.m_strExtendCode = rs.getString("strExtendCode");// չ�ں�ͬ���,
					info.m_strClientName = rs.getString("strClientName");// ��λ����
					info.m_strContractCode = rs.getString("ContractCode");// ��ͬ���,
					info.m_dExtendAmount = rs.getDouble("ExtendAmount"); // չ�ڽ��,
					info.m_dExtendInterestRate = rs.getDouble("ExtendInterestRate");// չ������,
					info.m_tsExtendEnd = rs.getTimestamp("ExtendEnd");// ��ͬ�������ڣ�
					
					info.dtExtendBeginDate = rs.getTimestamp("DTEXTENDBEGINDATE");		//չ����ʼ����
					info.dtExtendEndDate = rs.getTimestamp("DTEXTENDENDDATE");		//չ�ڽ�������
					
					infos[i] = info;
					i++;
				}
				assemble.setSecExhibitions(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}
	
	 
	/**
	 * δ����ҵ�����Ѵ���
	 * @throws Exception 
	 */
	public long getTranUpProcess(long lOfficeID, long lCurrencyID) throws Exception{
		long count=0;
		Collection con=null;
		TransUpSaveBiz biz = new TransUpSaveBiz();
		transupsaveinfo info=new transupsaveinfo();
		info.setOfficeID(lOfficeID);
		info.setCurrencyid(lCurrencyID);
		info.setStartDt("2000-01-01");
		info.setEndDt(Env.getSystemDateString(lOfficeID,lCurrencyID));
		con=biz.getBankTransFromBS(info);
		if(con!=null){
		Iterator it=con.iterator();
		
			while(it.hasNext()){
				transupsaveinfo mInfo=(transupsaveinfo)it.next();
				if(mInfo.getStatusID()==3){
					count++;
				}
			}
		
		}
		return count;
	}
	/**
	 * ���˳ɹ�ҵ�����Ѵ���
	 * @throws Exception 
	 */
	public long getTranUpProcessSuccess(long lOfficeID, long lCurrencyID) throws Exception{
		long count=0;
		Collection con=null;
		TransUpSaveBiz biz = new TransUpSaveBiz();
		transupsaveinfo info=new transupsaveinfo();
		info.setOfficeID(lOfficeID);
		info.setCurrencyid(lCurrencyID);
		info.setStartDt("2000-01-01");
		info.setEndDt(Env.getSystemDateString(lOfficeID,lCurrencyID));
		con=biz.getBankTransSuccessFromBS(info);
		if(con!=null){
		Iterator it=con.iterator();
		
			while(it.hasNext()){
				transupsaveinfo mInfo=(transupsaveinfo)it.next();
				if(mInfo.getStatusID()==1){
					count++;
				}
			}
		
		}
		return count;
	}
	
	/**
	 * ����ʧ��ҵ�����Ѵ���
	 * @throws Exception 
	 */
	public long getTranUpProcessFail(long lOfficeID, long lCurrencyID) throws Exception{
		long count=0;
		Collection con=null;
		TransUpSaveBiz biz = new TransUpSaveBiz();
		transupsaveinfo info=new transupsaveinfo();
		info.setOfficeID(lOfficeID);
		info.setCurrencyid(lCurrencyID);
		info.setStartDt("2000-01-01");
		info.setEndDt(Env.getSystemDateString(lOfficeID,lCurrencyID));
		con=biz.getBankFailTransFromBS(info);
		if(con!=null){
		Iterator it=con.iterator();
		
			while(it.hasNext()){
				transupsaveinfo mInfo=(transupsaveinfo)it.next();
				if(mInfo.getStatusID()==3){
					count++;
				}
			}
		
		}
		return count;
	}
	
	public static void main(String [] args){
		RemindProcess rp=new RemindProcess();
		try {
			System.out.print(rp.getTranUpProcess(1, 1));
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʼҵ�����Ѻ�̨����
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public void StartRemindProcess() throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		try {
			// Log.print("***���뷽�� -- StartRemindProcess ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select distinct nOfficeID,nCurrencyID from SETT_OFFICETIME c where nstatusid = "
							+ Constant.RecordStatus.VALID + " order by nOfficeID,nCurrencyID desc\n");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			Log.print("��ͬ�İ��´�,��ͬ��������Ϣ,���´�������Ϣ��ȡ���㿪ʼ......");
			while (rs.next()) {
				RemindAssemble assemble = new RemindAssemble();
				assemble.setOfficeID(rs.getLong("nOfficeID"));
				assemble.setCurrencyID(rs.getLong("nCurrencyID"));
				// ���ҵ������������Ϣ
				assemble = this.getRemindSettingInfo(assemble);
				// ���ҵ�����ѵĽ������
				if (assemble.getIsNeedUncheckRemind() == 1) {
					assemble = this.getAllUncheckRecord(assemble, false); //jzw 1��3������
				}
				if (assemble.getIsNeedSecUncheckRemind() == 1) {
					assemble = this.getAllSecUncheckRecord(assemble, false);
				}
				if (assemble.getIsNeedOverDraftRemind() == 1) {
					assemble = this.getAllOverDraftAccount(assemble, false);
				}
				if (assemble.getIsNeedUngrantPayFormRemind() == 1) {
					assemble = this.getAllUngrantPayForm(assemble, false);  
				}
				if (assemble.getIsNeedAheadPayFormRemind() == 1) {
					assemble = this.getAllAheadRepayForm(assemble, false); 
				}
				if (assemble.getIsNeedFreeFormRemind() == 1) {
					assemble = this.getAllFreeForm(assemble, false);
				}
				if (assemble.getFixedDepositAheadDays() > 0) {
					assemble = this.getAllAtTermFixedDeposit(assemble, false);
				}
				if (assemble.getLoanAheadDays() > 0) {
					assemble = this.getAllAtTermLoan(assemble, false);
				}
				//Modify By Wangzhen �ſ����ת�������� 2011-01-13
				if (assemble.getIsNeedOffBalanceFormRemind() == 1) {
				    assemble = this.getOffbalanceinfo(assemble, false);
				}		
				if (assemble.getIsNeedOBTransactionRemind() == 1) {
					assemble = this.getAllOBTransaction(assemble, false);
				}
				if (assemble.getIsNeedSecTransactionRemind() == 1) {
					assemble = this.getAllSecTransaction(assemble, false);
				}
				// add by rxie for SEFC
				if (assemble.getIsNeedBillRemindDay() > 0) {
					assemble = this.getAllBillRemind(assemble, false);
				}
				if (assemble.getIsNeedBillConsignReceiveDay() > 0) {
					assemble = this.getAllBillConsignReceive(assemble, false);
				}
				if (assemble.getIsNeedFreezeDay() > 0) {
					assemble = this.getAllFreeze(assemble, false);
				}
				if (assemble.getIsNeedLossDay() > 0) {
					assemble = this.getAllLoss(assemble, false);
				}
				if (assemble.getIsNeedPrimnessDay() > 0) {
					assemble = this.getAllPrimness(assemble, false);
				}
				if (assemble.getExhibitionDays() > 0) {
					assemble = this.getAllExhibitionForm(assemble, false);
				}
				// add by yanliu ����ƹ�
				/*
				 * if (assemble.getIsNeedBankBalance() > 0) { assemble = this.getAllBankBalance(assemble, false); } if
				 * (Config.getBoolean(ConfigConstant.SETTLEMENT_INFORM_BANKCASH, false)) { assemble =
				 * this.getAllRateLow(assemble, false); }
				 */

				// ��ȡ�޶����������Ϣ
				if (assemble.getIsNeedAccountDeadLine() == 1) {
					assemble = this.getAccountDeadLineRemind(assemble, false);
				}
				// Э���������
				if (assemble.getLNegotiation() >= 0) {
					assemble = this.getNegotiation(assemble, false);
				}
				// ��֤��������
				if (assemble.getLEnsureDeposit() >= 0) {
					assemble = this.getEnsureDepositInfo(assemble, false);
				}
				// �����ݿ��в�������
				this.saveRemindResultInfo(assemble);
				// ���ҵ��������Ϣ
				String strMsg = this.getRemindMSG(assemble.getOfficeID(), assemble.getCurrencyID());
				if (assemble.getOfficeID() == 1 && assemble.getCurrencyID() == 1) {
					Log.print("���´�:" + assemble.getOfficeID() + "\n����:" + assemble.getCurrencyID()
							+ "\n");
					Log.print("\nҵ�����Ѵ�ӡ�ں�̨����Ϣ:\n" + strMsg + "\n");
				}
				
				// ����ҵ��������Ϣ
				String strKey = String.valueOf(assemble.getOfficeID())
						+ String.valueOf(assemble.getCurrencyID());
				RemindAssemble.RemintMSG.put(strKey, strMsg);
				// ��������ҵ��������Ϊ�������Ѽ���ģ�
				RemindAssemble.OBCountForSoundRemind.put(strKey, String.valueOf(assemble
						.getOBTransactionCount()));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
	}

	// ��ȡ��֤����������Ϣ
	public RemindAssemble getEnsureDepositInfo(RemindAssemble assemble, boolean bDetail)
			throws SQLException {

		// �������ݿ��ѯ����
		long lOfficeId = assemble.getOfficeID();
		long lCurrencyId = assemble.getCurrencyID();

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// ������Ҫ���ѵ�����
		int iSumDay = (int) assemble.getLNegotiation();
		if(iSumDay < 0){
			return assemble;
		}
		Timestamp dtToDay = Env.getSystemDate(lOfficeId, lCurrencyId); // ҵ��ǰ����
		Date dtTmp = new Date(dtToDay.getTime());
		dtTmp = DataFormat.getPreviousOrNextDate(dtTmp, iSumDay * (-1));
		dtToDay = new Timestamp(dtTmp.getTime());

		ArrayList arrl_EnsureDeposit = new ArrayList(); // ��Ҫ���ѵı�֤������ϸ��Ϣ

		// ��ʼ��ȡ
		try {

			// ��ȡ��Ҫ��������Ϣ
			assemble = getRemindSettingInfo(assemble);

			// ��ȡ���ݿ�����
			conn = getConnection();

			// ������ѯ��SQL���
			StringBuffer strSQLQuery = new StringBuffer();
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	SDEPOSITNO,NACCOUNTID ACCOUNT_ID, ");
			strSQLQuery.append("	NCLIENTID,DTEND ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_TRANSOPENMARGINDEPOSIT ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	NOFFICEID = " + lOfficeId + " AND ");
			strSQLQuery.append("	NCURRENCYID = " + lCurrencyId + "	AND ");
			strSQLQuery.append("	DTEND <= TO_DATE('" + DataFormat.getDateString(dtToDay)
					+ "','YYYY-MM-DD')	AND  ");
			strSQLQuery.append("	NSTATUSID >= 3 ");

			// ��ѯ���ݱ�
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// ��ȡ��Ҫ���ѵı�֤�����˻�ID
			StringBuffer strbTmp = new StringBuffer("");
			long lSum = 0;
			while (rs.next()) {
				strbTmp.append(rs.getString("ACCOUNT_ID"));
				strbTmp.append(",");
				lSum++;

				// ��ȡ��ϸ��Ϣ
				if (bDetail) {
					EnsureDepositInfo ensureDepositInfo = new EnsureDepositInfo();
					ensureDepositInfo.setSDepositNo(rs.getString("SDEPOSITNO"));
					ensureDepositInfo.setNAccountId(rs.getLong("ACCOUNT_ID"));
					ensureDepositInfo.setNClientId(rs.getLong("NCLIENTID"));
					ensureDepositInfo.setDtEnd(rs.getTimestamp("DTEND"));
					arrl_EnsureDeposit.add(ensureDepositInfo);
				}
			}

			// ������Ҫ���ѵ�Э�������˻�ID���ַ���
			assemble.setStr_EnsureDeposit(strbTmp.toString());
			assemble.setLSumEnsureDeposit(lSum);
			assemble.setArrl_EnsureDeposit(arrl_EnsureDeposit);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ������ݿ�����
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// ���غ���ֵ
		return assemble;

	}

	// ��ȡЭ������������Ϣ
	public RemindAssemble getNegotiation(RemindAssemble assemble, boolean bDetail)
			throws SQLException {

		// �������ݿ��ѯ����
		long lOfficeId = assemble.getOfficeID();
		long lCurrencyId = assemble.getCurrencyID();

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		
		ArrayList arrl_Negotiation = new ArrayList(); // ��Ҫ���ѵ�Э�������ϸ��Ϣ
		
		// ��ʼ��ȡ
		try {
            // ���ҵ������������Ϣ
			assemble = getRemindSettingInfo(assemble);
            //������Ҫ���ѵ�����
			int iSumDay = (int) assemble.getLNegotiation();
			if(iSumDay < 0){
				return assemble;
			}
            // ��ȡ��Ҫ��������Ϣ
			Timestamp dtToDay = Env.getSystemDate(lOfficeId, lCurrencyId); // ҵ��ǰ����
			Date dtTmp = new Date(dtToDay.getTime());
			dtTmp = DataFormat.getPreviousOrNextDate(dtTmp, iSumDay);// jzw 2010-04-20 �޸�Э��������������Ĵ���
			dtToDay = new Timestamp(dtTmp.getTime()); 
			
			// ��ȡ���ݿ�����
			conn = getConnection();
            //��ѯЭ��������������
			// ������ѯ��SQL���
			StringBuffer strSQLQuery = new StringBuffer();
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	DISTINCT SETT_ACCOUNT.ID ACCOUNT_ID, ");
			strSQLQuery.append("	SETT_ACCOUNT.NCLIENTID,SETT_SUBACCOUNT.AC_DTNEGOTIATIONENDDATE ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_ACCOUNT,SETT_SUBACCOUNT,SETT_ACCOUNTTYPE ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	SETT_SUBACCOUNT.NACCOUNTID = SETT_ACCOUNT.ID AND ");
			strSQLQuery.append("	SETT_ACCOUNTTYPE.ID = SETT_ACCOUNT.NACCOUNTTYPEID AND ");
			strSQLQuery.append("	SETT_ACCOUNTTYPE.NACCOUNTGROUPID = 1 AND ");
			strSQLQuery.append("	SETT_ACCOUNT.NOFFICEID = " + lOfficeId + " AND ");
			strSQLQuery.append("	SETT_ACCOUNT.NCURRENCYID = " + lCurrencyId + "	AND ");
			strSQLQuery.append("	SETT_SUBACCOUNT.AC_NISNEGOTIATE =1 AND ");
			strSQLQuery.append("	SETT_SUBACCOUNT.AC_DTNEGOTIATIONENDDATE <= TO_DATE('"
					+ DataFormat.getDateString(dtToDay) + "','YYYY-MM-DD')	AND  ");
			strSQLQuery.append("	SETT_ACCOUNT.NSTATUSID = 1 AND ");
			strSQLQuery.append("	SETT_SUBACCOUNT.NSTATUSID =1 AND ");
			strSQLQuery.append("	SETT_ACCOUNTTYPE.NSTATUSID =1 ");
			// ��ѯ���ݱ�
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();
			
			// ��ȡ��Ҫ���ѵ�Э������˻�ID
			StringBuffer strbTmp = new StringBuffer("");
			long lSum = 0;
			while (rs.next()) {
				strbTmp.append(rs.getString("ACCOUNT_ID"));
				strbTmp.append(",");
				lSum++;

				// ��ȡ��ϸ��Ϣ
				if (bDetail) {
					NegotiationInfo negotiationInfo = new NegotiationInfo();
					negotiationInfo.setAccountId(rs.getLong("ACCOUNT_ID"));
					negotiationInfo.setClientId(rs.getLong("NCLIENTID"));
					negotiationInfo.setEndDate(rs.getTimestamp("AC_DTNEGOTIATIONENDDATE"));
					arrl_Negotiation.add(negotiationInfo);
					assemble.setArrl_Negotiation(arrl_Negotiation);
					}
			}
			
			// ������Ҫ���ѵ�Э�������˻�ID���ַ���
			assemble.setStr_NegotiationAccountId(strbTmp.toString());
			assemble.setLSumNegotiation(lSum);
			assemble.setArrl_Negotiation(arrl_Negotiation);
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ������ݿ�����
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// ���غ���ֵ
		return assemble;

	}

	// �˺�������У��
	private boolean chkBalanceLimited(long accountID, double balanceLimited) throws SQLException {

		// �������
		// �������ݿ��ѯ����
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// ����ֵ��Ĭ����Ҫ����
		boolean bResult = true;

		// ��ʼ��ѯͳ��
		try {

			// ��ȡ���ݿ�����
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// �˻�������
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	SUM(MBALANCE) SUMMBALANCE ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_SUBACCOUNT ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	NACCOUNTID = " + accountID + " AND ");
			strSQLQuery.append("	NSTATUSID = " + Constant.RecordStatus.VALID);

			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// ����ѯ����ֵ�洢����ʱ������Ϣ����
			while (rs.next()) {
				if (rs.getString("SUMMBALANCE") != null
						&& balanceLimited < rs.getDouble("SUMMBALANCE")) {
					bResult = false;
				}
			}

		} finally {
			// ������ݿ�����
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// ���غ���ֵ
		return bResult;
	}

	// �˺��������ۼƽ������������У��
	private boolean chkDebitCreditLimited(long accountID, double Limited, String strType,
			Timestamp dtStart, Timestamp dtEnd) throws SQLException {

		// �������
		// �������ݿ��ѯ����
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// ����ֵ��Ĭ����Ҫ����
		boolean bResult = true;

		// ��ʼ��ѯͳ��
		try {

			// ��ȡ���ݿ�����
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// �˻�������
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	SUM(MAMOUNT) SUMMAMOUNT ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_TRANSACCOUNTDETAIL ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	NTRANSACCOUNTID = " + accountID + " AND ");
			if ("DebitLimited".equals(strType)) {
				strSQLQuery.append("	NTRANSDIRECTION = 1 AND ");
			} else if ("CreditLimited".equals(strType)) {
				strSQLQuery.append("	NTRANSDIRECTION = 2 AND ");
			}

			strSQLQuery.append("	DTEXECUTE >= TO_DATE('" + dtStart.toString().substring(0, 10)
					+ "','YYYY-MM-DD') AND ");
			strSQLQuery.append("	DTEXECUTE <= TO_DATE('" + dtEnd.toString().substring(0, 10)
					+ "','YYYY-MM-DD') AND ");
			strSQLQuery.append("	NSTATUSID = " + Constant.RecordStatus.VALID);

			// ��ѯ���ݿ�
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// ����ѯ����ֵ�洢����ʱ������Ϣ����
			while (rs.next()) {
				if (Limited < rs.getDouble("SUMMAMOUNT"))
					bResult = false;
			}

		} finally {
			// ������ݿ�����
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// ���غ���ֵ
		return bResult;
	}

	// �˺������ڵ��ʽ��׶�����У��
	private boolean chkTranspayLimited(long accountID, double dTranspayLimited, Timestamp dtStart,
			Timestamp dtEnd) throws SQLException {

		// �������
		// �������ݿ��ѯ����
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// ����ֵ��Ĭ����Ҫ����
		boolean bResult = false;

		// ��ʼ��ѯͳ��
		try {

			// ��ȡ���ݿ�����
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// �˻�������
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	MAMOUNT ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_TRANSACCOUNTDETAIL ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	MAMOUNT >= " + dTranspayLimited + " AND ");
			strSQLQuery.append("	NTRANSACCOUNTID = " + accountID + " AND ");

			strSQLQuery.append("	DTEXECUTE >= TO_DATE('" + dtStart.toString().substring(0, 10)
					+ "','YYYY-MM-DD') AND ");
			strSQLQuery.append("	DTEXECUTE <= TO_DATE('" + dtEnd.toString().substring(0, 10)
					+ "','YYYY-MM-DD') AND ");
			strSQLQuery.append("	NSTATUSID = " + Constant.RecordStatus.VALID);

			// ��ѯ���ݿ�
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// �����ѯ������ǿ�����Ҫ����
			while (rs.next()) {
				bResult = true;
				break;
			}

		} finally {
			// ������ݿ�����
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// ���غ���ֵ
		return bResult;

	}

	// ��ȡ�޶����������Ϣ
	private RemindAssemble getAccountDeadLineRemind(RemindAssemble assemble,
			boolean bIsNeedInfoDetail) throws SQLException, Exception {

		// �������
		long lOfficeId = assemble.getOfficeID();
		long lCurrencyId = assemble.getCurrencyID();

		// �������ݿ��ѯ����
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		Timestamp dtToDay = Env.getSystemDate(lOfficeId, lCurrencyId); // ҵ��ǰ����

		Timestamp dtStart = null; // ��ʼ����
		Timestamp dtEnd = null; // ��������
		int nStatdays = 0; // ����

		ArrayList arrl_BalanceLimited = new ArrayList(); // ��Ҫ���ѵ���Խ�����ߵ��˺�ID
		ArrayList arrl_DebitLimited = new ArrayList(); // ��Ҫ���ѵ���������Խ�跽�ۼƷ�������˻�ID
		ArrayList arrl_CreditLimited = new ArrayList(); // ��Ҫ���ѵ���������Խ�����ۼƷ�������˻�ID
		ArrayList arrl_TranspayLimited = new ArrayList(); // ��Ҫ���ѵ��������֧�����������޵��˻�ID

		// ��ȡ��Ҫ��������Ϣ
		assemble = getRemindSettingInfo(assemble);
		try {
			// ��ȡ���ݿ�����
			conn = getConnection();

			// ������ѯ��SQL���
			StringBuffer strSQLQuery = new StringBuffer();
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	ACCOUNTID, ");
			strSQLQuery.append("	BALANCELIMITED,DEBITLIMITED, ");
			strSQLQuery.append("	CREDITLIMITED,TRANSPAYLIMITED,");
			strSQLQuery.append("	VALIDDATE,STATDAYS ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_ACCOUNTDEADLINE ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	STATUSID = " + Constant.RecordStatus.VALID);

			// ��ѯ���ݿ�
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// ����ѯ����ֵ�洢����ʱ������Ϣ����
			while (rs.next()) {

				// ������ʼ���ڣ���������
				dtStart = rs.getTimestamp("VALIDDATE");
				// �����ʼ���ڴ���ҵ�����ڣ�����Ҫ����������¼
				if (dtStart.after(dtToDay)) {
					continue;
				}
				// ��ȡ����
				nStatdays = rs.getInt("STATDAYS") - 1;
				// �����������
				dtEnd = DataFormat.getNextDate(dtStart, nStatdays);
				// ѭ�����㵱ǰҵ�����������Ǹ����ڼ�
				while (dtEnd.before(dtToDay)) {
					// ��ȡ��һ����ʼ����
					dtStart = DataFormat.getNextDate(dtEnd, 1);
					dtEnd = DataFormat.getNextDate(dtStart, nStatdays);
				}

				// ��ȡ��ҪУ����˺�ID
				long lAccountID = rs.getLong("ACCOUNTID");
				// ��ȡ���ƽ��
				double dBalanceLimited = rs.getDouble("BALANCELIMITED"); // ������
				double dDebitLimited = rs.getDouble("DEBITLIMITED"); // �����
				double dCreditLimited = rs.getDouble("CREDITLIMITED"); // ������
				double dTranspayLimited = rs.getDouble("TRANSPAYLIMITED"); // ���ʽ��׶�

				// ��ѯͳ���ж��Ƿ���˺���Ҫ����
				// ������
				if (dBalanceLimited > 0 && chkBalanceLimited(lAccountID, dBalanceLimited)) {
					arrl_BalanceLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("�˺ţ�" + lAccountID + "��Ҫ���������ߡ�");
				}
				// �����
				if (dDebitLimited > 0 && chkDebitCreditLimited(lAccountID, dDebitLimited, "DebitLimited", dtStart, dtEnd)) {
					arrl_DebitLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("�˺ţ�" + lAccountID + "��Ҫ���ѣ����������������޶");
				}

				// ������
				if (dCreditLimited > 0 && chkDebitCreditLimited(lAccountID, dCreditLimited, "CreditLimited", dtStart,
						dtEnd)) {
					arrl_CreditLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("�˺ţ�" + lAccountID + "��Ҫ���ѣ�����������������޶");
				}

				// ���ʽ��׶�
				if (dTranspayLimited > 0 && chkTranspayLimited(lAccountID, dTranspayLimited, dtStart, dtEnd)) {
					arrl_TranspayLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("�˺ţ�" + lAccountID + "��Ҫ���ѣ����ʽ��׶�����������޶");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// ����ѯ����ֵ�洢��������Ϣ����
		if (arrl_BalanceLimited.size() > 0)
			assemble.setArrl_BalanceLimited(arrl_BalanceLimited);
		if (arrl_DebitLimited.size() > 0)
			assemble.setArrl_DebitLimited(arrl_DebitLimited);
		if (arrl_CreditLimited.size() > 0)
			assemble.setArrl_CreditLimited(arrl_CreditLimited);
		if (arrl_TranspayLimited.size() > 0)
			assemble.setArrl_TranspayLimited(arrl_TranspayLimited);

		// �����Ҫ��ȡ��ϸ��Ϣ�����ѯ���ѵ���ϸ��Ϣ
		if (assemble.getFixedDepositCount() > 0 && bIsNeedInfoDetail == true) {
			// ��ѯ��ȡ���ѵ���ϸ��Ϣ
		}

		// ���غ���ֵ
		return assemble;

	}

	/**
	 * ������м������ڵĶ��ڴ浥������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllAtTermFixedDeposit(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllAtTermFixedDeposit ***");
			assemble = getRemindSettingInfo(assemble);
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("select * from ( select decode(AF_ISAUTOCONTINUE,1,AF_ISAUTOCONTINUE,-1) ISAUTOCONTINUE,c.ID ClientID,c.sCode ClientNo,c.sName ClientName,a.ID AccountID,a.sAccountNo AccountNo,sa.*");
			sbSQL.append(" from sett_subAccount sa,sett_Account a,Client c ,sett_accountType d\n");
			sbSQL.append(" where a.ID=sa.nAccountID and a.nClientID=c.ID and a.nAccountTypeID=d.id and d.nAccountGroupID="
							+ SETTConstant.AccountGroupType.FIXED + " \n");
			sbSQL.append(" and a.nOfficeID=" + assemble.getOfficeID() + " and a.nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append(" and (sa.af_dtEnd-to_date('"
					+ Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID())
					+ "','yyyy-mm-dd'))<=" + assemble.getFixedDepositAheadDays() + " \n");
			sbSQL.append(" and sa.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL + " \n");
			sbSQL.append(")");
			sbSQL.append(" order by ISAUTOCONTINUE ,af_dtEnd ,ClientNo,AccountNo  \n");
			
			
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setFixedDepositCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getFixedDepositCount() > 0 && bIsNeedDetail == true) {
				AtTermFixedDepositInfo[] infos = new AtTermFixedDepositInfo[(int) assemble
						.getFixedDepositCount()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					AtTermFixedDepositInfo info = new AtTermFixedDepositInfo();
					//
					info.setSubAccountID(rs.getLong("ID"));
					info.setFixedDepositNo(rs.getString("AF_sDepositNo"));
					info.setAccountID(rs.getLong("AccountID"));
					info.setAccountNo(rs.getString("AccountNo"));
					info.setClientID(rs.getLong("ClientID"));
					info.setClientNo(rs.getString("ClientNo"));
					info.setClientName(rs.getString("ClientName"));
					info.setMbalance(rs.getDouble("MBALANCE"));
					info.setAf_mrate(rs.getDouble("AF_MRATE"));
					info.setEndDate(rs.getTimestamp("AF_dtEnd"));
					info.setDepsitterm(rs.getLong("AF_NDEPOSITTERM"));
					info.setIsautocontinue(rs.getLong("AF_ISAUTOCONTINUE"));
					info.setAutocontinuetype(rs.getLong("AF_AUTOCONTINUETYPE"));
					infos[i] = info;
					i++;
				}
				assemble.setFixedDeposits(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ������м������ڵĴ���浥������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getAllAtTermLoan(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllAtTermLoan ***");
			assemble = getRemindSettingInfo(assemble);
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select p.ID PayFormID,p.sCode PayFormNo,c.ID ContractID,c.sContractCode ContractNo,");
			sbSQL.append(" c.nTypeID ContractTypeID,a.ID AccountID,a.sAccountNo AccountNo,client.ID ClientID,");
			sbSQL.append(" client.sCode ClientNo,client.sName ClientName,p.dtEnd EndDate,");
			sbSQL.append(" p.MAMOUNT,p.MINTERESTRATE RATE,p.DTSTART STARTDATE ");
			sbSQL.append(" from sett_subAccount sa,sett_Account a,loan_payform p,loan_ContractForm c,");
			sbSQL.append(" Client client,sett_accountType sat \n");
			sbSQL.append(" where a.ID=sa.nAccountID and sa.al_nLoanNoteID=p.ID ");
			sbSQL.append(" and p.nContractID=c.ID and a.nClientID=client.ID \n");
			sbSQL.append(" and a.nAccountTypeID = sat.id and sat.nAccountGroupID in ("
					+ SETTConstant.AccountGroupType.TRUST + ","
					+ SETTConstant.AccountGroupType.CONSIGN + ") \n");
			sbSQL.append(" and a.nOfficeID=" + assemble.getOfficeID() + " and a.nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append(" and (p.dtEnd-to_date('"
					+ Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID())
					+ "','yyyy-mm-dd'))<=" + assemble.getLoanAheadDays() + " \n");
			sbSQL.append(" and sa.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL + " \n");
			sbSQL.append(" and sa.mBalance > 0 \n");
			sbSQL.append(" and c.nStatusID != " + LOANConstant.ContractStatus.DELAYDEBT + " \n");
			sbSQL.append(" and c.ISEXTEND < 0 ");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setLoanCount(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getLoanCount() > 0 && bIsNeedDetail == true) {
				AtTermLoanInfo[] infos = new AtTermLoanInfo[(int) assemble.getLoanCount()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					AtTermLoanInfo info = new AtTermLoanInfo();
					//
					info.setContractID(rs.getLong("ContractID"));
					info.setContractNo(rs.getString("ContractNo"));
					info.setPayFormID(rs.getLong("PayFormID"));
					info.setPayFormNo(rs.getString("PayFormNo"));
					info.setAccountID(rs.getLong("AccountID"));
					info.setAccountNo(rs.getString("AccountNo"));
					info.setClientID(rs.getLong("ClientID"));
					info.setClientNo(rs.getString("ClientNo"));
					info.setClientName(rs.getString("ClientName"));
					info.setMamount(rs.getDouble("MAMOUNT"));
					info.setRate(rs.getDouble("RATE"));
					info.setStartDate(rs.getTimestamp("STARTDATE"));
					info.setEndDate(rs.getTimestamp("EndDate"));
					info.setContractTypeID(rs.getLong("ContractTypeID"));
					infos[i] = info;
					i++;
				}
				assemble.setLoans(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	
	
	/**
	 * ���������Ҫת����ķſ�֪ͨ����Ϣ������������
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getOffbalanceinfo(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getOffbalanceinfo ***");
			assemble = getRemindSettingInfo(assemble);
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select * ");
			sbSQL.append(" from SETT_PAYFORM_OVERDUE s ");
			sbSQL.append(" where s.ncurrencyid="+assemble.getCurrencyID()+"");
			sbSQL.append(" and s.nofficeid="+assemble.getOfficeID()+"");
			sbSQL.append(" and s.nstatusid=2 ");
			sbSQL.append(" and s.statusid!=2 ");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			//System.out.print(strCountSQL);
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setOFFBALANCEACCOUNT(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getOFFBALANCEACCOUNT() > 0 && bIsNeedDetail == true) {
				QueryPayformOverdue[] infos = new QueryPayformOverdue[(int) assemble.getOFFBALANCEACCOUNT()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					QueryPayformOverdue info = new QueryPayformOverdue();
					info.setId(rs.getLong("id"));
					info.setNborrowclientname(rs.getString("nborrowclientname"));
					info.setNtypename(rs.getString("ntypename"));
					info.setContractCode(rs.getString("contractcode"));
					info.setScode(rs.getString("scode"));
					info.setMamount(rs.getDouble("mamount"));
					info.setMbalance(rs.getLong("mbalance"));
					info.setMinterestrate(rs.getDouble("minterestrate"));
					info.setMcompoundinterest(rs.getDouble("mcompoundinterest"));
					info.setDtoutdate(rs.getTimestamp("dtoutdate"));
					info.setDtend(rs.getTimestamp("dtend"));
					info.setNdrawnoticeid(rs.getLong("ndrawnoticeid"));
					info.setMOVERDUEINTEREST(rs.getDouble("MOVERDUEINTEREST"));
					infos[i] = info;
					i++;
				}
				assemble.setOverdue(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	
	
	// ��ArrayList����ת��Ϊ�ַ���,�ַ�����ʽ�ǣ�"1,2,3"
	private String ArrayListToString(ArrayList arrTmp) {

		// �������
		StringBuffer strbTmp = new StringBuffer("");
		int i = 0;

		if (arrTmp != null) {
			for (i = 0; i < arrTmp.size() - 1; i++) {
				strbTmp.append(arrTmp.get(i));
				strbTmp.append(",");
			}
			strbTmp.append(arrTmp.get(i));
		}

		// ���غ���ֵ
		return strbTmp.toString();

	}

	// ���ַ���ת��ΪArrayList���ͣ��ַ�����ʽ�ǣ�"1,2,3"
	private ArrayList StringToArrayList(String strTmp) {

		// �������
		ArrayList arrTmp = null;

		if (strTmp != "" && strTmp != null) {
			arrTmp = new ArrayList();
			String[] straTmp = DataFormat.splitString(strTmp, ",");
			for (int i = 0; i < straTmp.length; i++)
				if (straTmp[i] != "" && straTmp[i] != null)
					arrTmp.add(straTmp[i]);
		}

		// ���غ���ֵ
		return arrTmp;
	}

	/**
	 * ����ҵ��������Ϣ
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	private RemindAssemble saveRemindResultInfo(RemindAssemble assemble) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		long lRecordCount = 0;
		String strSQL = "";

		// �޶�������Ϣ����ת��Ϊ�ַ���
		ArrayList arrTmp = null;
		String strBalanceLimited = null;
		String strDebitLimited = null;
		String strCreditLimited = null;
		String strTranspayLimited = null;
		if (assemble.getIsNeedAccountDeadLine() == 1) {
			arrTmp = assemble.getArrl_BalanceLimited();
			if (arrTmp != null) {
				strBalanceLimited = ArrayListToString(arrTmp);
			}
			arrTmp = assemble.getArrl_DebitLimited();
			if (arrTmp != null) {
				strDebitLimited = ArrayListToString(arrTmp);
			}
			arrTmp = assemble.getArrl_CreditLimited();
			if (arrTmp != null) {
				strCreditLimited = ArrayListToString(arrTmp);
			}
			arrTmp = assemble.getArrl_TranspayLimited();
			if (arrTmp != null) {
				strTranspayLimited = ArrayListToString(arrTmp);
			}
		}

		try {
			// Log.print("***���뷽�� -- saveReminResultInfo ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select count(*) from sett_reminderresult \n");
			sbSQL.append(" where nOfficeID=" + assemble.getOfficeID() + " and nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				lRecordCount = rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
			if (lRecordCount > 0) {
				// �޸�
				sbSQL = null;
				sbSQL = new StringBuffer();
				sbSQL.append(" update sett_ReminderResult set nfixeddepositcount=?,nloancount=?,");
				sbSQL.append("npreinputcount=?,ninterestcomputecount=?,nnetoperationcount=?,");
				sbSQL.append("noverdraftcount=?,nloannotecount=?,npreformcount=?,");
				sbSQL.append("nUnchekRecordCount=?,nfreeformcount=?,nSecOperationCount=?,");
				sbSQL.append("nSecUnchekRecordCount=?,nBillRemindCount=?,");
				sbSQL.append("nBillConsignReceiveCount=?,nFreezeCount=?,nLossCount=?,");
				sbSQL.append("nPrimnessCount=?,MRATEOFBANKANDSETT=?,NBANKLOWBALANCECOUNT=?,");
				sbSQL.append("BALANCELIMITED=?,DEBITLIMITED=?,CREDITLIMITED=?,");
				sbSQL.append("TRANSPAYLIMITED=?,nextendnotice = ?,");
				sbSQL.append("NNEGOTIATIONCOUNT = ?,NEGOTIATEACCOUNT = ?,");
				sbSQL.append("NENSUREDEPOSITCOUNT = ?,ENSUREDEPOSITACCOUNT = ?,offbalanceaccount=? \n");
				sbSQL.append(" where nofficeid=? and ncurrencyid=? \n");
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, assemble.getFixedDepositCount());
				ps.setLong(2, assemble.getLoanCount());
				ps.setLong(3, 0); // TODO
				ps.setLong(4, 0); // TODO
				ps.setLong(5, assemble.getOBTransactionCount());
				ps.setLong(6, assemble.getOverDraftAccountCount());
				ps.setLong(7, assemble.getUngrantPayFormCount());
				ps.setLong(8, assemble.getAheadPayFormCount());
				ps.setLong(9, assemble.getUncheckRecordCount());
				ps.setLong(10, assemble.getFreeFormCount());
				ps.setLong(11, assemble.getSecTransactionCount());
				ps.setLong(12, assemble.getSecUncheckRecordCount());

				ps.setLong(13, assemble.getBillRemindDay());
				ps.setLong(14, assemble.getBillConsignReceiveDay());
				ps.setLong(15, assemble.getFreezeDay());
				ps.setLong(16, assemble.getLossDay());
				ps.setLong(17, assemble.getPrimnessDay());

				ps.setDouble(18, assemble.getRateOfBankAndSett());
				ps.setLong(19, assemble.getBankBalanceCount());

				ps.setString(20, strBalanceLimited);
				ps.setString(21, strDebitLimited);
				ps.setString(22, strCreditLimited);
				ps.setString(23, strTranspayLimited);
				ps.setLong(24, assemble.getSecExhibitionCount());

				ps.setLong(25, assemble.getLSumNegotiation());
				ps.setString(26, assemble.getStr_NegotiationAccountId());

				ps.setLong(27, assemble.getLSumEnsureDeposit());
				ps.setString(28, assemble.getStr_EnsureDeposit());
				ps.setLong(29, assemble.getOFFBALANCEACCOUNT());
				ps.setLong(30, assemble.getOfficeID());
				ps.setLong(31, assemble.getCurrencyID());

				ps.executeUpdate();
				cleanup(ps);
			} else {
				// ����
				sbSQL = null;
				sbSQL = new StringBuffer();
				sbSQL.append(" insert into sett_ReminderResult(nofficeid,");
				sbSQL.append("ncurrencyid,nfixeddepositcount,nloancount,");
				sbSQL.append("npreinputcount,ninterestcomputecount,nnetoperationcount,");
				sbSQL.append("noverdraftcount,nloannotecount,npreformcount,");
				sbSQL.append("nUnchekRecordCount,nfreeformcount,nSecOperationCount,");
				sbSQL.append("nSecUnchekRecordCount,nBillRemindCount,nBillConsignReceiveCount,");
				sbSQL.append("nFreezeCount,nLossCount,nPrimnessCount,MRATEOFBANKANDSETT,");
				sbSQL.append("NBANKLOWBALANCECOUNT,BALANCELIMITED,DEBITLIMITED,CREDITLIMITED,");
				sbSQL.append("TRANSPAYLIMITED,nextendnotice,");
				sbSQL.append("NNEGOTIATIONCOUNT,NEGOTIATEACCOUNT,");
				sbSQL.append("NENSUREDEPOSITCOUNT,ENSUREDEPOSITACCOUNT,offbalanceaccount) \n");
				sbSQL
						.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, assemble.getOfficeID());
				ps.setLong(2, assemble.getCurrencyID());
				ps.setLong(3, assemble.getFixedDepositCount());
				ps.setLong(4, assemble.getLoanCount());
				ps.setLong(5, 0); // TODO
				ps.setLong(6, 0); // TODO
				ps.setLong(7, assemble.getOBTransactionCount());
				ps.setLong(8, assemble.getOverDraftAccountCount());
				ps.setLong(9, assemble.getUngrantPayFormCount());
				ps.setLong(10, assemble.getAheadPayFormCount());
				ps.setLong(11, assemble.getUncheckRecordCount());
				ps.setLong(12, assemble.getFreeFormCount());
				ps.setLong(13, assemble.getSecTransactionCount());
				ps.setLong(14, assemble.getSecUncheckRecordCount());
				ps.setLong(15, assemble.getBillRemindDay());
				ps.setLong(16, assemble.getBillConsignReceiveDay());
				ps.setLong(17, assemble.getFreezeDay());
				ps.setLong(18, assemble.getLossDay());
				ps.setLong(19, assemble.getPrimnessDay());
				ps.setDouble(20, assemble.getRateOfBankAndSett());
				ps.setLong(21, assemble.getBankBalanceCount());
				ps.setString(22, strBalanceLimited);
				ps.setString(23, strDebitLimited);
				ps.setString(24, strCreditLimited);
				ps.setString(25, strTranspayLimited);
				ps.setLong(26, assemble.getSecExhibitionCount());

				ps.setLong(27, assemble.getLSumNegotiation());
				ps.setString(28, assemble.getStr_NegotiationAccountId());

				ps.setLong(29, assemble.getLSumEnsureDeposit());
				ps.setString(30, assemble.getStr_EnsureDeposit());
				ps.setLong(31, assemble.getOFFBALANCEACCOUNT());
				ps.executeUpdate();
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ���ҵ������������Ϣ
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	private RemindAssemble getRemindSettingInfo(RemindAssemble assemble) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		try {
			// Log.print("***���뷽�� -- getRemindSettingInfo ***");
			conn = getConnection();
			sbSQL = new StringBuffer();

			sbSQL.append(" select * from SETT_OPERATIONREMINDER \n");
			sbSQL.append(" where nOfficeID=" + assemble.getOfficeID() + " and nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setIsNeedUncheckRemind(1);
				assemble.setIsNeedAheadPayFormRemind(rs.getLong("nPreform"));
				assemble.setIsNeedUngrantPayFormRemind(rs.getLong("nLoanNote"));
				assemble.setIsNeedOverDraftRemind(rs.getLong("nOverDraft"));
				assemble.setFixedDepositAheadDays(rs.getLong("nFixedDeposit"));
				assemble.setLoanAheadDays(rs.getLong("nLoan"));
				assemble.setIsNeedFreeFormRemind(rs.getLong("nFreeform"));
				assemble.setIsNeedOBTransactionRemind(rs.getLong("nNetOperation"));
				assemble.setIsNeedSecTransactionRemind(rs.getLong("nSecOperation"));
				RemindAssemble.setIsOBSoundRemind(rs.getLong("nIsOBSoundRemind"));
				assemble.setExhibitionDays(rs.getLong("nExtendnotice"));
				//System.out.println("========assemble.setExhibitionDays================="
				//		+ assemble.getExhibitionDays());
				assemble.setIsNeedBillRemindDay(rs.getLong("nBillRemindDay"));
				assemble.setIsNeedBillConsignReceiveDay(rs.getLong("nBillConsignReceiveDay"));
				assemble.setIsNeedFreezeDay(rs.getLong("nFreezeDay"));
				assemble.setIsNeedLossDay(rs.getLong("nLossDay"));
				assemble.setIsNeedPrimnessDay(rs.getLong("nPrimnessDay"));

				assemble.setIsNeedBankBalance(rs.getDouble("MBANKLOWBALANCE"));// ����������ѽ��
				assemble.setIsNeedRateOfBankAndSett(rs.getDouble("MBANKLOWRATE"));// ����ͷ������ڴ�����ѱ���
				assemble.setIsNeedAccountDeadLine(rs.getLong("NACCOUNTDEADLINE"));// �Ƿ���Ҫ�޶��������
				assemble.setLNegotiation(rs.getLong("NNEGOTIATION")); // �Ƿ���Ҫ����Э�����
				assemble.setLEnsureDeposit(rs.getLong("NENSUREDEPOSIT")); // �Ƿ���Ҫ���ѱ�֤����
				assemble.setIsNeedOffBalanceFormRemind(rs.getLong("OFFBALANCENOTICE"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ���ҵ��������Ϣ
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	public RemindAssemble getRemindInfo(long lOfficeID, long lCurrencyID) throws Exception {
		RemindAssemble assemble = null;
		try {
			// Log.print("***���뷽�� -- getRemindInfo ***");
			assemble = new RemindAssemble();
			assemble.setOfficeID(lOfficeID);
			assemble.setCurrencyID(lCurrencyID);
			// ���ҵ������������Ϣ
			assemble = this.getRemindSettingInfo(assemble);
			// ���ҵ�����ѽ����Ϣ
			assemble = this.getRemindResultInfo(assemble);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return assemble;
	}
	

	/**
	 * ���ҵ��������Ϣ
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	/**
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 * @throws Exception
	 */
	private String getRemindMSG(long lOfficeID, long lCurrencyID) throws Exception {
		RemindAssemble assemble = null;
		StringBuffer sbMsg = null;
		try {
			// Log.print("***���뷽�� -- getRemindMSG ***");
			
			
			
			
			assemble = new RemindAssemble();
			sbMsg = new StringBuffer();
			assemble = this.getRemindInfo(lOfficeID, lCurrencyID);
			/****************��Ϣ��������*********************/
			MatureRemindBiz mrRemindBiz = new MatureRemindBiz();
			MatureRemindInfo mrInfo = new MatureRemindInfo() ;	
			mrInfo.setCurrencyid(lCurrencyID);
			mrInfo.setOfficeId(lOfficeID);
			mrInfo.setMaturity(Env.getSystemDateString(lOfficeID,lCurrencyID));
			Collection coll = mrRemindBiz.findDate(mrInfo);
			Iterator iter = null;
			if ( coll != null)
			{
		  		assemble.setIsNeedMatureRemind(1);
		  		
			}
			/**************************�ֹ��տ�����****************************************/
			TransUpSaveBiz biz = new TransUpSaveBiz();
			transupsaveinfo info=new transupsaveinfo();
			long myCount1=this.getTranUpProcess(lOfficeID, lCurrencyID);
			long myCount2=this.getTranUpProcessSuccess(lOfficeID, lCurrencyID);
			long myCount3=this.getTranUpProcessFail(lOfficeID, lCurrencyID);
			if(myCount1>0){
				assemble.setIsNeedShouRemind(1);
			}
			if(myCount2>0){
				assemble.setIsNeedShouRemind(1);
			}
			if(myCount3>0){
				assemble.setIsNeedShouRemind(1);
			}
			if (assemble != null) {
				boolean settlement_remind = Config.getBoolean("settlement_remind", true);
				//��Ϣ����

				if (assemble.getIsNeedMatureRemind() > 0)
				{
					iter = coll.iterator();
					while (iter.hasNext()) 
					{						
						mrInfo = (MatureRemindInfo) iter.next();
						sbMsg.append(mrInfo.getContent()+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");						
					}
				}
				
				if (assemble.getIsNeedOBTransactionRemind() == 1) {
					//add by zcwang 2007-10-15 �Ƿ���ʾ����0��ҵ�����ѵ�ҵ����Ϣ
					
						if(settlement_remind)
						{
							sbMsg.append("����<A href='remindOBTransaction.jsp' target=_blank>&nbsp;"
								+ assemble.getOBTransactionCount() + "&nbsp;</A>������ҵ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getOBTransactionCount()>0)
							{
							sbMsg.append("����<A href='remindOBTransaction.jsp' target=_blank>&nbsp;"
									+ assemble.getOBTransactionCount() + "&nbsp;</A>������ҵ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
						}
					//
					}
			/*
				//�н�����  �ֹ��տ����ѣ�
				if (assemble.getIsNeedShouRemind() == 1) {
				sbMsg.append("����<A href='shouGong.jsp' target=_blank>&nbsp;"
						+ myCount + "&nbsp;</A>���ֹ��տ���Ҫ����  ");
				}
			*/	
				if (assemble.getIsNeedShouRemind() == 1) {
						if(settlement_remind)
						{
							sbMsg.append("����<A href='shouGong.jsp' target=_blank>&nbsp;"
								+myCount1+"&nbsp;</A>��δ������Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(myCount1>0)
							{
							sbMsg.append("����<A href='shouGong.jsp' target=_blank>&nbsp;"
										+myCount1+"&nbsp;</A>��δ������Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
								    
				if (assemble.getIsNeedShouRemind() == 1) {
						if(settlement_remind)
						{
							sbMsg.append("����<A href='shouGongSuccess.jsp' target=_blank>&nbsp;"
								+ myCount2 + "&nbsp;</A>�����˳ɹ���  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(myCount2>0)
							{
								sbMsg.append("����<A href='shouGongSuccess.jsp' target=_blank>&nbsp;"
										+ myCount2 + "&nbsp;</A>�����˳ɹ���  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedShouRemind() == 1) {
						if(settlement_remind)
						{
							sbMsg.append("����<A href='shouGongFail.jsp' target=_blank>&nbsp;"
									+ myCount3 + "&nbsp;</A>������ʧ�ܣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(myCount3>0)
							{
								sbMsg.append("����<A href='shouGongFail.jsp' target=_blank>&nbsp;"
										+ myCount3 + "&nbsp;</A>������ʧ�ܣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
							}
						}
					}
				if (assemble.getIsNeedUncheckRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindUncheckTrans.jsp' target=_blank>&nbsp;"
								+ assemble.getUncheckRecordCount() + "&nbsp;</A>�ʴ�������Ҫ����/���ˣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getUncheckRecordCount()>0)
							{
								sbMsg.append("����<A href='remindUncheckTrans.jsp' target=_blank>&nbsp;"
										+ assemble.getUncheckRecordCount() + "&nbsp;</A>�ʴ�������Ҫ����/���ˣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
						}
					}
				if (assemble.getIsNeedSecTransactionRemind() == 1) {
					// sbMsg.append("����<A href='remindSecTransaction.jsp'
					// target=_blank>&nbsp;"+assemble.getSecTransactionCount()+"&nbsp;</A>����/��������Ϊ���յ�֤ȯҵ��֪ͨ����δ����
					// ");
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindSecTransaction.jsp' target=_blank>&nbsp;"
								+ assemble.getSecTransactionCount() + "&nbsp;</A>��֤ȯҵ��֪ͨ����δ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getSecTransactionCount()>0)
							{
								sbMsg.append("����<A href='remindSecTransaction.jsp' target=_blank>&nbsp;"
										+ assemble.getSecTransactionCount() + "&nbsp;</A>��֤ȯҵ��֪ͨ����δ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedSecUncheckRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindSecUncheckTrans.jsp' target=_blank>&nbsp;"
								+ assemble.getSecUncheckRecordCount()
								+ "&nbsp;</A>��״̬Ϊδ����/�ݴ��֤ȯ����δ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getSecUncheckRecordCount()>0)
							{
								sbMsg.append("����<A href='remindSecUncheckTrans.jsp' target=_blank>&nbsp;"
										+ assemble.getSecUncheckRecordCount()
										+ "&nbsp;</A>��״̬Ϊδ����/�ݴ��֤ȯ����δ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedOverDraftRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindOverDraftAccount.jsp' target=_blank>&nbsp;"
								+ assemble.getOverDraftAccountCount() + "&nbsp;</A>���˻�͸֧��  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getOverDraftAccountCount()>0)
							{
								sbMsg.append("����<A href='remindOverDraftAccount.jsp' target=_blank>&nbsp;"
										+ assemble.getOverDraftAccountCount() + "&nbsp;</A>���˻�͸֧��  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
						}
					}
				if (assemble.getIsNeedUngrantPayFormRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindUngrantPayForm.jsp' target=_blank>&nbsp;"
								+ assemble.getUngrantPayFormCount() + "&nbsp;</A>�ʷſ�֪ͨ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getUngrantPayFormCount()>0)
							{
								sbMsg.append("����<A href='remindUngrantPayForm.jsp' target=_blank>&nbsp;"
										+ assemble.getUngrantPayFormCount() + "&nbsp;</A>�ʷſ�֪ͨ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedAheadPayFormRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindAheadRepayForm.jsp' target=_blank>&nbsp;"
								+ assemble.getAheadPayFormCount() + "&nbsp;</A>�ʻ���֪ͨ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getAheadPayFormCount()>0)
							{
								sbMsg.append("����<A href='remindAheadRepayForm.jsp' target=_blank>&nbsp;"
										+ assemble.getAheadPayFormCount() + "&nbsp;</A>�ʻ���֪ͨ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					
					}
				if (assemble.getIsNeedFreeFormRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindFreeForm.jsp' target=_blank>&nbsp;"
								+ assemble.getFreeFormCount() + "&nbsp;</A>���⻹֪ͨ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getFreeFormCount()>0)
							{
								sbMsg.append("����<A href='remindFreeForm.jsp' target=_blank>&nbsp;"
										+ assemble.getFreeFormCount() + "&nbsp;</A>���⻹֪ͨ����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
						
					}
				if (assemble.getFixedDepositAheadDays() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindAtTermFixedDeposit.jsp' target=_blank>&nbsp;"
								+ assemble.getFixedDepositCount() + "&nbsp;</A>�ʶ��ڴ�����/�ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getFixedDepositCount()>0)
							{
								sbMsg.append("����<A href='remindAtTermFixedDeposit.jsp' target=_blank>&nbsp;"
										+ assemble.getFixedDepositCount() + "&nbsp;</A>�ʶ��ڴ�����/�ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getExhibitionDays() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindExhibition.jsp' target=_blank>&nbsp;"
								+ assemble.getSecExhibitionCount() + "&nbsp;</A>��չ�ں�ͬ��  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getSecExhibitionCount()>0)
							{
								sbMsg.append("����<A href='remindExhibition.jsp' target=_blank>&nbsp;"
										+ assemble.getSecExhibitionCount() + "&nbsp;</A>��չ�ں�ͬ��  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getLoanAheadDays() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindAtTermLoan.jsp' target=_blank>&nbsp;"
								+ assemble.getLoanCount() + "&nbsp;</A>�ʴ������/�ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getLoanCount()>0)
							{
								sbMsg.append("����<A href='remindAtTermLoan.jsp' target=_blank>&nbsp;"
										+ assemble.getLoanCount() + "&nbsp;</A>�ʴ������/�ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				// add by rxie for SEFC
				/*���н�û�л�Ʊ�ͻ�Ʊ���յ����ѣ�
				if (assemble.getIsNeedBillRemindDay() > 0) {
					sbMsg.append("����<A href='remindBill.jsp' target=_blank>&nbsp;"
							+ assemble.getBillRemindDay() + "&nbsp;</A>�Ż�Ʊ�ѵ��ڣ�  ");
				}
				
				if (assemble.getIsNeedBillConsignReceiveDay() > 0) {
					sbMsg.append("����<A href='remindBillConsignReceive.jsp' target=_blank>&nbsp;"
							+ assemble.getBillConsignReceiveDay() + "&nbsp;</A>�Ż�Ʊ�����ѵ��ڣ�  ");
				}
				*/
				if (assemble.getIsNeedFreezeDay() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindFreeze.jsp' target=_blank>&nbsp;"
								+ assemble.getFreezeDay() + "&nbsp;</A>�ʶ����ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getFreezeDay()>0)
							{
								sbMsg.append("����<A href='remindFreeze.jsp' target=_blank>&nbsp;"
										+ assemble.getFreezeDay() + "&nbsp;</A>�ʶ����ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedLossDay() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindLoss.jsp' target=_blank>&nbsp;"
								+ assemble.getLossDay() + "&nbsp;</A>�ʴ浥��ʧ��  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getLossDay()>0)
							{
								sbMsg.append("����<A href='remindLoss.jsp' target=_blank>&nbsp;"
										+ assemble.getLossDay() + "&nbsp;</A>�ʴ浥��ʧ��  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedPrimnessDay() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindPrimness.jsp' target=_blank>&nbsp;"
								+ assemble.getPrimnessDay() + "&nbsp;</A>�ʴ����ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{	
							if(assemble.getPrimnessDay()>0)
							{
								sbMsg.append("����<A href='remindPrimness.jsp' target=_blank>&nbsp;"
										+ assemble.getPrimnessDay() + "&nbsp;</A>�ʴ����ѵ��ڣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}

				// �����˺��޶�����
				if (assemble.getIsNeedAccountDeadLine() > 0) {
					ArrayList arr_Tmp = null;
					String strPathHead = "/NASApp/iTreasury-settlement/settlement/set/";
					String strTmp = null;

					// ������
					arr_Tmp = assemble.getArrl_BalanceLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>�������˻����������ߣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>�������˻����������ߣ�  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}

					// ������
					arr_Tmp = assemble.getArrl_DebitLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>�������˻��跽��������޶  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>�������˻��跽��������޶  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}

					// �������
					arr_Tmp = assemble.getArrl_CreditLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>�������˻�������������޶  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>�������˻�������������޶  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}

					// ���ʽ��׷�����
					arr_Tmp = assemble.getArrl_TranspayLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>�������˻����ʷ�������޶  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("����<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>�������˻����ʷ�������޶  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}
				}
				// ��ȡЭ�����������Ϣ
				
				if (assemble.getLNegotiation() >= 0) {
						if(settlement_remind)
						{
						sbMsg.append("����<A href='remindNegotiateDeposit.jsp' target=_'blank'>&nbsp;"
								+ assemble.getLSumNegotiation() + "&nbsp;</A>��Э����Ҫ���ڣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getLSumNegotiation()>0)
							{
								sbMsg.append("����<A href='remindNegotiateDeposit.jsp' target=_'blank'>&nbsp;"
										+ assemble.getLSumNegotiation() + "&nbsp;</A>��Э����Ҫ���ڣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				//Add By Wangzhen �ſ����ת�������� 2011-01-13
				if (assemble.getIsNeedOffBalanceFormRemind() == 1) {
					if(settlement_remind)
					{
					sbMsg.append("����<A href='remindOffbalanceInfo.jsp' target=_blank>&nbsp;"
							+ assemble.getOFFBALANCEACCOUNT() + "&nbsp;</A>�ʷſ�֪ͨ���ѻ�����Ҫ���� &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}
					else
					{
						if(assemble.getOFFBALANCEACCOUNT()>0)
						{
							sbMsg.append("����<A href='remindOffbalanceInfo.jsp' target=_blank>&nbsp;"
									+ assemble.getOFFBALANCEACCOUNT() + "&nbsp;</A>�ʷſ�֪ͨ���ѻ�����Ҫ����  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
						}
					}
				}
//				modify by wjliu --2007-3-21 ��Ϊ���������Ϣ
				//Log.print("��ȡЭ�����������Ϣ��" + sbMsg.toString());
				
				// ��ȡ��֤����������Ϣ
				/*(�н�û�б�֤�����������)
				if (assemble.getLEnsureDeposit() >= 0) {
					sbMsg.append("����<A href='remindEnsureDeposit.jsp' target=_'blank'>&nbsp;"
							+ assemble.getLSumEnsureDeposit() + "&nbsp;</A>�ʱ�֤���Ҫ���ڣ�");
				}
				*/

				// add by yanliu
				/*
				if (assemble.getIsNeedBankBalance() > 0) {
					sbMsg.append("����<A href='remindBankBalance.jsp?dLowBalance="
							+ assemble.getIsNeedBankBalance() + "' target=_blank>&nbsp;"
							+ assemble.getBankBalanceCount() + "&nbsp;</A>��������������"
							+ assemble.getIsNeedBankBalance() + "Ԫ��  ");
				}
				*/

				if (Config.getBoolean(ConfigConstant.SETTLEMENT_INFORM_BANKCASH, false)) {
					DecimalFormat df = new DecimalFormat("#.00");
					String tmp = "0.00";
					if (assemble.getRateOfBankAndSett() > 0)
						tmp = df.format(assemble.getRateOfBankAndSett());
					sbMsg.append("����ͷ������ڴ�����Ϊ&nbsp;" + tmp + "%&nbsp;��  ");
				}
				if (Config.getBoolean(ConfigConstant.SETTLEMENT_INFORM_AUTOTIME, false)) {
					long lYear = Long.parseLong(DataFormat.getYearString(Env.getSystemDate(assemble
							.getOfficeID(), assemble.getCurrencyID())));
					long lMonth = Long.parseLong(DataFormat.getRealMonthString(Env.getSystemDate(
							assemble.getOfficeID(), assemble.getCurrencyID())));
					long lDay = Long.parseLong(DataFormat.getDayString(Env.getSystemDate(assemble
							.getOfficeID(), assemble.getCurrencyID())));
					String strLastday = DataFormat.getDateString(DataFormat.getLastDateOfMonth(Env
							.getSystemDate(assemble.getOfficeID(), assemble.getCurrencyID())));
					String strToday = DataFormat.getDateString(Env.getSystemDate(assemble
							.getOfficeID(), assemble.getCurrencyID()));
					if (lDay == 1 || lDay == 11 || lDay == 21) {
						sbMsg.append("������" + lMonth + "��" + lDay + "�գ���ע���������׼����  ");
					}
					if ((lMonth == 3 || lMonth == 6 || lMonth == 9 || lMonth == 12) && lDay == 20) {
						sbMsg.append("������" + lMonth + "��" + lDay + "�գ���ע�⼾�Ƚ�Ϣ��  ");
					}
					if (strLastday.equals(strToday)) {
						sbMsg.append("������" + lMonth + "��" + lDay + "�գ���ע���ṩ�ڲ����˵���  ");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return (sbMsg == null ? "" : sbMsg.toString());
	}

	/**
	 * ���ҵ�����ѽ����Ϣ
	 * 
	 * @param lOfficeID
	 *            ���´�
	 * @param lCurrencyID
	 *            ����
	 */
	private RemindAssemble getRemindResultInfo(RemindAssemble assemble) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;

		// �޶�������Ϣ����
		ArrayList arr_BalanceLimited = null;
		ArrayList arr_DebitLimited = null;
		ArrayList arr_CreditLimited = null;
		ArrayList arr_TranspayLimited = null;

		try {
			// Log.print("***���뷽�� -- getRemindResultInfo ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_ReminderResult \n");
			sbSQL.append(" where nOfficeID =" + assemble.getOfficeID() + " and nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setAheadPayFormCount(rs.getLong("npreformcount"));
				assemble.setFixedDepositCount(rs.getLong("nfixeddepositcount"));
				assemble.setLoanCount(rs.getLong("nloancount"));
				assemble.setOverDraftAccountCount(rs.getLong("noverdraftcount"));
				assemble.setUncheckRecordCount(rs.getLong("nUnchekRecordCount"));
				assemble.setSecUncheckRecordCount(rs.getLong("nSecUnchekRecordCount"));
				assemble.setSecUncheckRecordCount(rs.getLong("nSecUnchekRecordCount"));
				assemble.setOFFBALANCEACCOUNT(rs.getLong("offbalanceaccount"));
				assemble.setUngrantPayFormCount(rs.getLong("nloannotecount"));
				assemble.setFreeFormCount(rs.getLong("nfreeformcount"));
				assemble.setOBTransactionCount(rs.getLong("nNetOperationCount"));
				assemble.setSecTransactionCount(rs.getLong("nSecOperationCount"));
				assemble.setSecExhibitionCount(rs.getLong("nExtendnotice"));// չ�ں�ͬ��������
				assemble.setBillRemindDay(rs.getLong("nBillRemindCount"));
				assemble.setBillConsignReceiveDay(rs.getLong("nBillConsignReceiveCount"));
				assemble.setFreezeDay(rs.getLong("nFreezeCount"));
				assemble.setLossDay(rs.getLong("nLossCount"));
				assemble.setPrimnessDay(rs.getLong("nPrimnessCount"));

				assemble.setRateOfBankAndSett(rs.getDouble("MRATEOFBANKANDSETT"));
				assemble.setBankBalanceCount(rs.getLong("NBANKLOWBALANCECOUNT"));

				// ��ȡ�޶�������Ϣ
				arr_BalanceLimited = StringToArrayList(rs.getString("BALANCELIMITED"));
				arr_DebitLimited = StringToArrayList(rs.getString("DEBITLIMITED"));
				arr_CreditLimited = StringToArrayList(rs.getString("CREDITLIMITED"));
				arr_TranspayLimited = StringToArrayList(rs.getString("TRANSPAYLIMITED"));
				assemble.setArrl_BalanceLimited(arr_BalanceLimited);
				assemble.setArrl_DebitLimited(arr_DebitLimited);
				assemble.setArrl_CreditLimited(arr_CreditLimited);
				assemble.setArrl_TranspayLimited(arr_TranspayLimited);

				// ��ȡЭ�����������Ϣ
				assemble.setLSumNegotiation(rs.getLong("NNEGOTIATIONCOUNT"));
				assemble.setStr_NegotiationAccountId(rs.getString("NEGOTIATEACCOUNT"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ��� ��Ʊ�������Ѽ���ǰ���� ��ͬ�š�����ƾ֤�š���Ʊ���롢��Ʊ���ࡢ�����ա������ա����
	 */
	public RemindAssemble getAllBillRemind(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllBillRemind ***");
			// ���ҵ������������Ϣ
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("���ҵ������������Ϣ����");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select distinct contract.sContractCode ContractCode,credence.sCode DiscountCredenceNo,bill.sCode DiscountBillNo,bill.nAcceptpoTypeID DiscountBillTypeID,bill.dtEnd DiscountBillEndDate,null DiscountBillConsignReceiveDate,bill.mAmount Amount \n");
			sbSQL
					.append(" from loan_ContractForm contract,loan_DiscountCredence credence,loan_DiscountContractBill bill \n");
			sbSQL
					.append(" where contract.ID = bill.nContractID and credence.ID = bill.nDiscountCredenceID \n");
			sbSQL.append(" and contract.nOfficeID = " + assemble.getOfficeID()
					+ " and contract.nCurrencyID = " + assemble.getCurrencyID()
					+ " and bill.nStatusID > 0 \n");
			sbSQL
					.append(" and bill.id not in (select NDISCOUNTBILLID from sett_transrepaymentdiscount where NTRANSACTIONTYPEID = "
							+ SETTConstant.TransactionType.DISCOUNTRECEIVE
							+ " and NSTATUSID = "
							+ SETTConstant.TransactionStatus.CHECK + ")\n");
			sbSQL
					.append(" and contract.ID in (select NDISCOUNTCONTRACTID from Sett_TransGrantDiscount where NSTATUSID = "
							+ SETTConstant.TransactionStatus.CHECK + ")\n");
			sbSQL
					.append(" and credence.ID in (select NDISCOUNTNOTEID from Sett_TransGrantDiscount where NSTATUSID = "
							+ SETTConstant.TransactionStatus.CHECK + ")\n");
			sbSQL.append(" and (bill.dtEnd-to_date('"
					+ Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID())
					+ "','yyyy-mm-dd'))<=" + assemble.getIsNeedBillRemindDay() + " \n");

			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setBillRemindDay(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getIsNeedBillRemindDay() > 0 && bIsNeedDetail == true) {
				BillRemindInfo[] infos = new BillRemindInfo[(int) assemble.getBillRemindDay()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					BillRemindInfo info = new BillRemindInfo();
					//
					info.setContractCode(rs.getString("ContractCode"));
					info.setDiscountCredenceNo(rs.getString("DiscountCredenceNo"));
					info.setDiscountBillNo(rs.getString("DiscountBillNo"));
					info.setDiscountBillTypeID(rs.getLong("DiscountBillTypeID"));
					info.setDiscountBillEndDate(rs.getTimestamp("DiscountBillEndDate"));
					info.setDiscountBillConsignReceiveDate(rs
							.getTimestamp("DiscountBillConsignReceiveDate"));
					info.setAmount(rs.getDouble("Amount"));

					infos[i] = info;
					i++;
				}
				assemble.setBillRemindInfos(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ��� ��Ʊ�������Ѽ���ǰ���� ��ͬ�š�����ƾ֤�š���Ʊ���롢��Ʊ���ࡢ�����ա������ա����
	 */
	public RemindAssemble getAllBillConsignReceive(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllBillConsignReceive ***");
			// ���ҵ������������Ϣ
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("���ҵ������������Ϣ����");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select distinct contract.sContractCode ContractCode,credence.sCode DiscountCredenceNo,bill.sCode DiscountBillNo,bill.nAcceptpoTypeID DiscountBillTypeID,bill.dtEnd DiscountBillEndDate,offBalance.consignDate DiscountBillConsignReceiveDate,bill.mAmount Amount \n");
			sbSQL
					.append(" from loan_ContractForm contract,loan_DiscountCredence credence,loan_DiscountContractBill bill,sett_OffBalanceRegister offBalance \n");
			sbSQL
					.append(" where contract.ID = bill.nContractID and credence.ID = bill.nDiscountCredenceID and offBalance.loanNoteID = credence.ID and offBalance.Billid = bill.id \n");
			sbSQL.append(" and contract.nOfficeID = " + assemble.getOfficeID()
					+ " and contract.nCurrencyID = " + assemble.getCurrencyID()
					+ " and bill.nStatusID > 0 \n");
			sbSQL.append(" and offBalance.STATUSID > 0 \n");
			sbSQL.append(" and (offBalance.consignDate-to_date('"
					+ Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID())
					+ "','yyyy-mm-dd'))<=" + assemble.getIsNeedBillConsignReceiveDay() + " \n");

			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			Log.print("strCountSQL = " + strCountSQL);
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setBillConsignReceiveDay(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getIsNeedBillConsignReceiveDay() > 0 && bIsNeedDetail == true) {
				BillConsignReceiveInfo[] infos = new BillConsignReceiveInfo[(int) assemble
						.getBillConsignReceiveDay()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					BillConsignReceiveInfo info = new BillConsignReceiveInfo();
					//
					info.setContractCode(rs.getString("ContractCode"));
					info.setDiscountCredenceNo(rs.getString("DiscountCredenceNo"));
					info.setDiscountBillNo(rs.getString("DiscountBillNo"));
					info.setDiscountBillTypeID(rs.getLong("DiscountBillTypeID"));
					info.setDiscountBillEndDate(rs.getTimestamp("DiscountBillEndDate"));
					info.setDiscountBillConsignReceiveDate(rs
							.getTimestamp("DiscountBillConsignReceiveDate"));
					info.setAmount(rs.getDouble("Amount"));

					infos[i] = info;
					i++;
				}
				assemble.setBillConsignReceiveInfos(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ��� ���ᵽ�����Ѽ���ǰ���� �ͻ��š����ơ��˻��š��浥�š����᷽ʽ��������ⶳ������ִ�е�λ������ִ�е�λ������;
	 */
	public RemindAssemble getAllFreeze(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllFreeze ***");
			// ���ҵ������������Ϣ
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("���ҵ������������Ϣ����");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select client.sCode ClientCode,client.sName ClientName,account.sAccountNo AccountNo,a.oldDepositNo DepositNo \n");
			sbSQL
					.append(" ,sAccount.nstatusid freezeType,a.FreezeAmount freezeAmount,0 freeAmount,a.executeGovernment,a.applyCompany,a.effectiveDate \n");
			sbSQL
					.append(" from sett_ReportlossOrFreeze a,Client,sett_Account account,sett_subaccount sAccount \n");
			sbSQL
					.append(" where a.ClientID = Client.ID and a.AccountID = account.ID and account.id = sAccount.nAccountid and a.Status = 3 \n");
			sbSQL.append(" and a.TransactionType = " + SETTConstant.TransactionType.FREEZE + " \n");
			sbSQL.append(" and sAccount.nstatusid in("
					+ SETTConstant.SubAccountStatus.ONLYPAYFREEZE + ","
					+ SETTConstant.SubAccountStatus.ALLFREEZE + ","
					+ SETTConstant.SubAccountStatus.PARTFREEZE + ") \n");
			sbSQL.append(" and a.OfficeID = " + assemble.getOfficeID() + " and a.CurrencyID = "
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append(" and (a.FREEZEENDDATE-to_date('"
					+ Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID())
					+ "','yyyy-mm-dd'))<=" + assemble.getIsNeedFreezeDay() + " \n");

			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			Log.print("strCountSQL = " + strCountSQL);
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setFreezeDay(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getIsNeedFreezeDay() > 0 && bIsNeedDetail == true) {
				FreezeInfo[] infos = new FreezeInfo[(int) assemble.getFreezeDay()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					FreezeInfo info = new FreezeInfo();
					//
					info.setClientCode(rs.getString("ClientCode"));
					info.setClientName(rs.getString("ClientName"));
					info.setAccountNo(rs.getString("AccountNo"));
					info.setDepositNo(rs.getString("DepositNo"));
					info.setFreezeType(rs.getLong("FreezeType"));
					info.setFreezeAmount(rs.getDouble("FreezeAmount"));
					info.setExecuteGovernment(rs.getString("ExecuteGovernment"));
					info.setApplyCompany(rs.getString("ApplyCompany"));
					info.setEffectiveDate(rs.getTimestamp("EffectiveDate"));

					infos[i] = info;
					i++;
				}
				assemble.setFreezeInfos(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	//�н�����
	}

	/**
	 * ��� ��ʧ�������Ѽ���ʧ���� �������͡��ͻ��š����ơ��˻��š��浥�š��´浥������
	 */
	public RemindAssemble getAllLoss(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllLoss ***");
			// ���ҵ������������Ϣ
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("���ҵ������������Ϣ����");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select client.sCode ClientCode,client.sName ClientName,account.sAccountNo AccountNo,a.oldDepositNo DepositNo,a.newDepositNo newDepositNo \n");
			sbSQL
					.append(" ,a.subAccountNewStatus freezeType,a.FreezeAmount freezeAmount,0 freeAmount,a.executeGovernment,a.applyCompany,a.effectiveDate \n");
			sbSQL
					.append(" from sett_ReportlossOrFreeze a,Client,sett_Account account,sett_subaccount saccount \n");
			sbSQL
					.append(" where a.ClientID = Client.ID and a.AccountID = account.ID and a.Status = 3 \n");
			sbSQL.append(" and saccount.NACCOUNTID=a.AccountID \n");
			sbSQL
					.append(" and (saccount.AF_SDEPOSITNO = a.oldDepositNo or saccount.AF_SDEPOSITNO = a.newDepositNo) \n");
			sbSQL.append(" and saccount.nstatusid <> " + SETTConstant.AccountStatus.NORMAL + " \n");
			sbSQL.append(" and a.TransactionType = " + SETTConstant.TransactionType.REPORTLOSS
					+ " \n");
			// sbSQL.append(" and a.AccountID not in (select aa.NACCOUNTID from
			// sett_subaccount aa where
			// aa.nstatusid="+SETTConstant.AccountStatus.NORMAL+" and
			// ((aa.AF_SDEPOSITNO=DepositNo or aa.AF_SDEPOSITNO=newDepositNo)
			// and aa.NACCOUNTID=a.AccountID))\n");
			sbSQL.append(" and a.OfficeID = " + assemble.getOfficeID() + " and a.CurrencyID = "
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append(" and (to_date('"
					+ Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID())
					+ "','yyyy-mm-dd')-a.effectiveDate)>=" + assemble.getIsNeedLossDay() + " \n");

			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";
			// Log.print("strCountSQL = "+strCountSQL);
			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setLossDay(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getIsNeedLossDay() > 0 && bIsNeedDetail == true) {
				LossInfo[] infos = new LossInfo[(int) assemble.getLossDay()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					LossInfo info = new LossInfo();
					//
					info.setClientCode(rs.getString("ClientCode"));
					info.setClientName(rs.getString("ClientName"));
					info.setAccountNo(rs.getString("AccountNo"));
					info.setDepositNo(rs.getString("DepositNo"));
					info.setNewDepositNo(rs.getString("NewDepositNo"));
					info.setEffectiveDate(rs.getTimestamp("EffectiveDate"));

					infos[i] = info;
					i++;
				}
				assemble.setLossInfos(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	/**
	 * ��� ����������Ѽ����ں�N������
	 */
	public RemindAssemble getAllPrimness(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***���뷽�� -- getAllPrimness ***");
			// ���ҵ������������Ϣ
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("���ҵ������������Ϣ����");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select p.ID PayFormID,p.sCode PayFormNo,c.ID ContractID,c.sContractCode ContractNo,");
			sbSQL.append(" c.nTypeID ContractTypeID,a.ID AccountID,a.sAccountNo AccountNo,client.ID ClientID,");
			sbSQL.append(" client.sCode ClientNo,client.sName ClientName,p.dtEnd EndDate,");
			sbSQL.append(" c.mexamineamount amount,c.minterestrate rate,c.dtactive dtActive \n");
			sbSQL.append(" from sett_subAccount sa,sett_Account a,loan_payform p,loan_ContractForm c,");
			sbSQL.append(" Client client,sett_accountType sat \n");
			sbSQL.append(" where a.ID=sa.nAccountID and sa.al_nLoanNoteID=p.ID and p.nContractID=c.ID and a.nClientID=client.ID \n");
			sbSQL.append(" and a.nAccountTypeID=sat.id and sat.nAccountGroupID in ("
					+ SETTConstant.AccountGroupType.TRUST + ","
					+ SETTConstant.AccountGroupType.CONSIGN + ") \n");
			sbSQL.append(" and a.nOfficeID=" + assemble.getOfficeID() + " and a.nCurrencyID="
					+ assemble.getCurrencyID() + " \n");
			sbSQL.append(" and (to_date('"
					+ Env.getSystemDateString(assemble.getOfficeID(), assemble.getCurrencyID())
					+ "','yyyy-mm-dd')-p.dtEnd) >=" + assemble.getIsNeedPrimnessDay() + " \n");
			sbSQL.append(" and sa.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL + " \n");
			sbSQL.append(" and sa.mBalance > 0 \n");
			sbSQL.append(" and c.nStatusID = " + LOANConstant.ContractStatus.DELAYDEBT + " \n");
			strCountSQL = "select count(*) from (" + sbSQL.toString() + ")";

			ps = conn.prepareStatement(strCountSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				assemble.setPrimnessDay(rs.getLong(1));
			}
			cleanup(rs);
			cleanup(ps);
			if (assemble.getIsNeedPrimnessDay() > 0 && bIsNeedDetail == true) {
				PrimnessInfo[] infos = new PrimnessInfo[(int) assemble.getPrimnessDay()];
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next()) {
					PrimnessInfo info = new PrimnessInfo();
					//
					info.setContractID(rs.getLong("ContractID"));
					info.setContractNo(rs.getString("ContractNo"));
					info.setPayFormID(rs.getLong("PayFormID"));
					info.setPayFormNo(rs.getString("PayFormNo"));
					info.setAccountID(rs.getLong("AccountID"));
					info.setAccountNo(rs.getString("AccountNo"));
					info.setClientID(rs.getLong("ClientID"));
					info.setClientNo(rs.getString("ClientNo"));
					info.setClientName(rs.getString("ClientName"));
					info.setEndDate(rs.getTimestamp("EndDate"));
					info.setContractTypeID(rs.getLong("ContractTypeID"));
					info.setAmount(rs.getDouble("amount"));
					info.setRate(rs.getDouble("rate"));
					info.setDtActive(rs.getTimestamp("dtActive"));
					infos[i] = info;
					i++;
				}
				assemble.setPrimnessInfos(infos);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(conn);
			throw new Exception(e.getMessage());
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return assemble;
	}

	private static void test_1() {

		Timestamp dtToDay = Env.getSystemDateTime(); // ҵ��ϵͳ����
		//dtToDay.setDate(1);
		
		System.out.println("ϵͳ��ǰϵͳ�����ǣ�"+DataFormat.getDateString(dtToDay));
		
		Date dtTmp = new Date(dtToDay.getTime());
		int i= 0 ;
		dtTmp = DataFormat.getPreviousOrNextDate(dtTmp, i);
		dtToDay = new Timestamp(dtTmp.getTime());
		
		System.out.println("��ǰ"+i+"���������ǣ�"+ DataFormat.getDateString(dtToDay));

	}

	private static void test_2() {

		Log.print("--ҵ�����Ѻ�̨����---");
		RemindProcess process = new RemindProcess();
		try {
			RemindAssemble assemble = new RemindAssemble();
			assemble.setOfficeID(1);
			assemble.setCurrencyID(1);
			// Log.print("1111111111111111111111111111111111111");

			process.StartRemindProcess();
			// Log.print("2222222222222222222222222222222222222");
		} catch (Exception ex) {
			ex.printStackTrace();
			// Log.print(ex.toString());
		}

	}
	
}
