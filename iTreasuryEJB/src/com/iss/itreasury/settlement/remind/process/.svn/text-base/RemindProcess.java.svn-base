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
 * Title: 关机线程
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
 * @author ：yychen
 * @version 1.0
 */
public class RemindProcess extends SettlementDAO {
	public RemindProcess(Connection conn) {
		super(conn);
	}

	public RemindProcess() {
	}

	/**
	 * 获得所有未处理的证券银行业务的记录（包括记录总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
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
			Log.print("***进入方法 -- getAllSecTransaction ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			strDate = Env.getSystemDateString(); // 服务器时间
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
	 * 获得所有未处理的网上银行业务的记录（包括记录总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
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
			//System.out.println("***进入方法 -- getAllOBTransaction ***");
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
			//修改原因是由于华联客户只需要提示当天未处理的网银指令
			//sbSQL.append(" and fin.dtExecute = to_date('" + strDate + "','yyyy-mm-dd') \n");	
			//modify by xwhe 2008-04-16
			//将提示修改为读配置文件
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
	 * 获得所有未复核、未勾账的记录（包括记录总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
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
	 * 获得所有证券业务未复核、暂存的记录（包括记录总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getAllSecUncheckRecord(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			Log.print("***进入方法 -- getAllSecUncheckRecord ***");
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
	 * 获得所有透支账户（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getAllOverDraftAccount(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			Log.print("***进入方法 -- getAllOverDraftAccount ***");
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
					// TODO:增加属性
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
	 * 获得所有未处理的放款通知单（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getAllUngrantPayForm(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			Log.print("***进入方法 -- getAllUngrantPayForm ***");
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
					
					info.setAmount(rs.getDouble("MAMOUNT"));		//还款金额
					info.setRate(rs.getDouble("MRATE"));		//还款利率
					info.setDtStart(rs.getTimestamp("DTSTART"));		//还款开始日
					info.setDtEnd(rs.getTimestamp("DTEND"));		//还款到期日
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
	 * 获得所有未处理的提前还款通知单（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getAllAheadRepayForm(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllAheadRepayForm ***");
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
	 * 获得所有未处理的免换通知单（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getAllFreeForm(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllFreeForm ***");
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
	 * 获得所有规定时间内的展期合同（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
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
			// Log.print("***进入方法 -- getAllExhibitionForm ***^^^^^^^^^^^^^^^^^^^^^"+assemble.getExhibitionDays());
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
				assemble.setSecExhibitionCount(rs.getLong(1));// 取得总数
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

					info.m_strExtendCode = rs.getString("strExtendCode");// 展期合同编号,
					info.m_strClientName = rs.getString("strClientName");// 借款单位名称
					info.m_strContractCode = rs.getString("ContractCode");// 合同编号,
					info.m_dExtendAmount = rs.getDouble("ExtendAmount"); // 展期金额,
					info.m_dExtendInterestRate = rs.getDouble("ExtendInterestRate");// 展期利率,
					info.m_tsExtendEnd = rs.getTimestamp("ExtendEnd");// 合同结束日期，
					
					info.dtExtendBeginDate = rs.getTimestamp("DTEXTENDBEGINDATE");		//展期起始日期
					info.dtExtendEndDate = rs.getTimestamp("DTEXTENDENDDATE");		//展期结束日期
					
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
	 * 未入账业务提醒处理
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
	 * 入账成功业务提醒处理
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
	 * 入账失败业务提醒处理
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
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 开始业务提醒后台处理
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public void StartRemindProcess() throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		try {
			// Log.print("***进入方法 -- StartRemindProcess ***");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select distinct nOfficeID,nCurrencyID from SETT_OFFICETIME c where nstatusid = "
							+ Constant.RecordStatus.VALID + " order by nOfficeID,nCurrencyID desc\n");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			Log.print("不同的办事处,不同的提醒信息,办事处提醒信息获取计算开始......");
			while (rs.next()) {
				RemindAssemble assemble = new RemindAssemble();
				assemble.setOfficeID(rs.getLong("nOfficeID"));
				assemble.setCurrencyID(rs.getLong("nCurrencyID"));
				// 获得业务提醒设置信息
				assemble = this.getRemindSettingInfo(assemble);
				// 获得业务提醒的结果数据
				if (assemble.getIsNeedUncheckRemind() == 1) {
					assemble = this.getAllUncheckRecord(assemble, false); //jzw 1和3走这里
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
				//Modify By Wangzhen 放款单逾期转表外提醒 2011-01-13
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
				// add by yanliu 航天科工
				/*
				 * if (assemble.getIsNeedBankBalance() > 0) { assemble = this.getAllBankBalance(assemble, false); } if
				 * (Config.getBoolean(ConfigConstant.SETTLEMENT_INFORM_BANKCASH, false)) { assemble =
				 * this.getAllRateLow(assemble, false); }
				 */

				// 获取限额底线提醒信息
				if (assemble.getIsNeedAccountDeadLine() == 1) {
					assemble = this.getAccountDeadLineRemind(assemble, false);
				}
				// 协定存款提醒
				if (assemble.getLNegotiation() >= 0) {
					assemble = this.getNegotiation(assemble, false);
				}
				// 保证金存款提醒
				if (assemble.getLEnsureDeposit() >= 0) {
					assemble = this.getEnsureDepositInfo(assemble, false);
				}
				// 向数据库中插入数据
				this.saveRemindResultInfo(assemble);
				// 获得业务提醒信息
				String strMsg = this.getRemindMSG(assemble.getOfficeID(), assemble.getCurrencyID());
				if (assemble.getOfficeID() == 1 && assemble.getCurrencyID() == 1) {
					Log.print("办事处:" + assemble.getOfficeID() + "\n币种:" + assemble.getCurrencyID()
							+ "\n");
					Log.print("\n业务提醒打印在后台的信息:\n" + strMsg + "\n");
				}
				
				// 保存业务提醒信息
				String strKey = String.valueOf(assemble.getOfficeID())
						+ String.valueOf(assemble.getCurrencyID());
				RemindAssemble.RemintMSG.put(strKey, strMsg);
				// 保存网上业务总数（为声音提醒加入的）
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

	// 获取保证金存款提醒信息
	public RemindAssemble getEnsureDepositInfo(RemindAssemble assemble, boolean bDetail)
			throws SQLException {

		// 定义数据库查询变量
		long lOfficeId = assemble.getOfficeID();
		long lCurrencyId = assemble.getCurrencyID();

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 计算需要提醒的日期
		int iSumDay = (int) assemble.getLNegotiation();
		if(iSumDay < 0){
			return assemble;
		}
		Timestamp dtToDay = Env.getSystemDate(lOfficeId, lCurrencyId); // 业务当前日期
		Date dtTmp = new Date(dtToDay.getTime());
		dtTmp = DataFormat.getPreviousOrNextDate(dtTmp, iSumDay * (-1));
		dtToDay = new Timestamp(dtTmp.getTime());

		ArrayList arrl_EnsureDeposit = new ArrayList(); // 需要提醒的保证金存款详细信息

		// 开始获取
		try {

			// 获取必要的提醒信息
			assemble = getRemindSettingInfo(assemble);

			// 获取数据库连接
			conn = getConnection();

			// 创建查询的SQL语句
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

			// 查询数据表
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 获取需要提醒的保证金存款账户ID
			StringBuffer strbTmp = new StringBuffer("");
			long lSum = 0;
			while (rs.next()) {
				strbTmp.append(rs.getString("ACCOUNT_ID"));
				strbTmp.append(",");
				lSum++;

				// 获取详细信息
				if (bDetail) {
					EnsureDepositInfo ensureDepositInfo = new EnsureDepositInfo();
					ensureDepositInfo.setSDepositNo(rs.getString("SDEPOSITNO"));
					ensureDepositInfo.setNAccountId(rs.getLong("ACCOUNT_ID"));
					ensureDepositInfo.setNClientId(rs.getLong("NCLIENTID"));
					ensureDepositInfo.setDtEnd(rs.getTimestamp("DTEND"));
					arrl_EnsureDeposit.add(ensureDepositInfo);
				}
			}

			// 存贮需要提醒的协定存款的账户ID的字符串
			assemble.setStr_EnsureDeposit(strbTmp.toString());
			assemble.setLSumEnsureDeposit(lSum);
			assemble.setArrl_EnsureDeposit(arrl_EnsureDeposit);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// 返回函数值
		return assemble;

	}

	// 获取协定存款的提醒信息
	public RemindAssemble getNegotiation(RemindAssemble assemble, boolean bDetail)
			throws SQLException {

		// 定义数据库查询变量
		long lOfficeId = assemble.getOfficeID();
		long lCurrencyId = assemble.getCurrencyID();

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		
		ArrayList arrl_Negotiation = new ArrayList(); // 需要提醒的协定存款详细信息
		
		// 开始获取
		try {
            // 获得业务提醒设置信息
			assemble = getRemindSettingInfo(assemble);
            //计算需要提醒的日期
			int iSumDay = (int) assemble.getLNegotiation();
			if(iSumDay < 0){
				return assemble;
			}
            // 获取必要的提醒信息
			Timestamp dtToDay = Env.getSystemDate(lOfficeId, lCurrencyId); // 业务当前日期
			Date dtTmp = new Date(dtToDay.getTime());
			dtTmp = DataFormat.getPreviousOrNextDate(dtTmp, iSumDay);// jzw 2010-04-20 修改协定存款提醒天数的错误
			dtToDay = new Timestamp(dtTmp.getTime()); 
			
			// 获取数据库连接
			conn = getConnection();
            //查询协定存款的提醒天数
			// 创建查询的SQL语句
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
			// 查询数据表
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();
			
			// 获取需要提醒的协定存款账户ID
			StringBuffer strbTmp = new StringBuffer("");
			long lSum = 0;
			while (rs.next()) {
				strbTmp.append(rs.getString("ACCOUNT_ID"));
				strbTmp.append(",");
				lSum++;

				// 获取详细信息
				if (bDetail) {
					NegotiationInfo negotiationInfo = new NegotiationInfo();
					negotiationInfo.setAccountId(rs.getLong("ACCOUNT_ID"));
					negotiationInfo.setClientId(rs.getLong("NCLIENTID"));
					negotiationInfo.setEndDate(rs.getTimestamp("AC_DTNEGOTIATIONENDDATE"));
					arrl_Negotiation.add(negotiationInfo);
					assemble.setArrl_Negotiation(arrl_Negotiation);
					}
			}
			
			// 存贮需要提醒的协定存款的账户ID的字符串
			assemble.setStr_NegotiationAccountId(strbTmp.toString());
			assemble.setLSumNegotiation(lSum);
			assemble.setArrl_Negotiation(arrl_Negotiation);
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// 返回函数值
		return assemble;

	}

	// 账号余额底线校验
	private boolean chkBalanceLimited(long accountID, double balanceLimited) throws SQLException {

		// 定义变量
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 返回值，默认需要提醒
		boolean bResult = true;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// 账户余额底线
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	SUM(MBALANCE) SUMMBALANCE ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_SUBACCOUNT ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	NACCOUNTID = " + accountID + " AND ");
			strSQLQuery.append("	NSTATUSID = " + Constant.RecordStatus.VALID);

			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 将查询所得值存储在零时提醒信息对象
			while (rs.next()) {
				if (rs.getString("SUMMBALANCE") != null
						&& balanceLimited < rs.getDouble("SUMMBALANCE")) {
					bResult = false;
				}
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// 返回函数值
		return bResult;
	}

	// 账号周期内累计借款贷款发生额上限校验
	private boolean chkDebitCreditLimited(long accountID, double Limited, String strType,
			Timestamp dtStart, Timestamp dtEnd) throws SQLException {

		// 定义变量
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 返回值，默认需要提醒
		boolean bResult = true;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// 账户余额底线
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

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 将查询所得值存储在零时提醒信息对象
			while (rs.next()) {
				if (Limited < rs.getDouble("SUMMAMOUNT"))
					bResult = false;
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// 返回函数值
		return bResult;
	}

	// 账号周期内单笔交易额上限校验
	private boolean chkTranspayLimited(long accountID, double dTranspayLimited, Timestamp dtStart,
			Timestamp dtEnd) throws SQLException {

		// 定义变量
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 返回值，默认需要提醒
		boolean bResult = false;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// 账户余额底线
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

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 如果查询结果不是空则需要提醒
			while (rs.next()) {
				bResult = true;
				break;
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// 返回函数值
		return bResult;

	}

	// 获取限额底线提醒信息
	private RemindAssemble getAccountDeadLineRemind(RemindAssemble assemble,
			boolean bIsNeedInfoDetail) throws SQLException, Exception {

		// 定义变量
		long lOfficeId = assemble.getOfficeID();
		long lCurrencyId = assemble.getCurrencyID();

		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		Timestamp dtToDay = Env.getSystemDate(lOfficeId, lCurrencyId); // 业务当前日期

		Timestamp dtStart = null; // 起始日期
		Timestamp dtEnd = null; // 结束日期
		int nStatdays = 0; // 周期

		ArrayList arrl_BalanceLimited = new ArrayList(); // 需要提醒的逾越余额底线的账号ID
		ArrayList arrl_DebitLimited = new ArrayList(); // 需要提醒的周期内逾越借方累计发生额的账户ID
		ArrayList arrl_CreditLimited = new ArrayList(); // 需要提醒的周期内逾越贷方累计发生额的账户ID
		ArrayList arrl_TranspayLimited = new ArrayList(); // 需要提醒的逾过单笔支付发生额上限的账户ID

		// 获取必要的提醒信息
		assemble = getRemindSettingInfo(assemble);
		try {
			// 获取数据库连接
			conn = getConnection();

			// 创建查询的SQL语句
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

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 将查询所得值存储在零时提醒信息对象
			while (rs.next()) {

				// 计算起始日期，结束日期
				dtStart = rs.getTimestamp("VALIDDATE");
				// 如果起始日期大于业务日期，则不需要考虑这条记录
				if (dtStart.after(dtToDay)) {
					continue;
				}
				// 获取周期
				nStatdays = rs.getInt("STATDAYS") - 1;
				// 计算结束日期
				dtEnd = DataFormat.getNextDate(dtStart, nStatdays);
				// 循环计算当前业务是日属于那个周期间
				while (dtEnd.before(dtToDay)) {
					// 获取下一个起始日期
					dtStart = DataFormat.getNextDate(dtEnd, 1);
					dtEnd = DataFormat.getNextDate(dtStart, nStatdays);
				}

				// 获取需要校验的账号ID
				long lAccountID = rs.getLong("ACCOUNTID");
				// 获取限制金额
				double dBalanceLimited = rs.getDouble("BALANCELIMITED"); // 余额底线
				double dDebitLimited = rs.getDouble("DEBITLIMITED"); // 借款金额
				double dCreditLimited = rs.getDouble("CREDITLIMITED"); // 贷款金额
				double dTranspayLimited = rs.getDouble("TRANSPAYLIMITED"); // 单笔交易额

				// 查询统计判断是否该账号需要提醒
				// 余额底线
				if (dBalanceLimited > 0 && chkBalanceLimited(lAccountID, dBalanceLimited)) {
					arrl_BalanceLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("账号：" + lAccountID + "需要提醒余额底线。");
				}
				// 借款金额
				if (dDebitLimited > 0 && chkDebitCreditLimited(lAccountID, dDebitLimited, "DebitLimited", dtStart, dtEnd)) {
					arrl_DebitLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("账号：" + lAccountID + "需要提醒，借款金额超过周期内上限额。");
				}

				// 贷款金额
				if (dCreditLimited > 0 && chkDebitCreditLimited(lAccountID, dCreditLimited, "CreditLimited", dtStart,
						dtEnd)) {
					arrl_CreditLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("账号：" + lAccountID + "需要提醒，贷款金额超过周期内上限额。");
				}

				// 单笔交易额
				if (dTranspayLimited > 0 && chkTranspayLimited(lAccountID, dTranspayLimited, dtStart, dtEnd)) {
					arrl_TranspayLimited.add(new String(rs.getString("ACCOUNTID")));
					Log.print("账号：" + lAccountID + "需要提醒，单笔交易额超过周期内上限额。");
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

		// 将查询所得值存储在提醒信息对象
		if (arrl_BalanceLimited.size() > 0)
			assemble.setArrl_BalanceLimited(arrl_BalanceLimited);
		if (arrl_DebitLimited.size() > 0)
			assemble.setArrl_DebitLimited(arrl_DebitLimited);
		if (arrl_CreditLimited.size() > 0)
			assemble.setArrl_CreditLimited(arrl_CreditLimited);
		if (arrl_TranspayLimited.size() > 0)
			assemble.setArrl_TranspayLimited(arrl_TranspayLimited);

		// 如果需要获取详细信息，则查询提醒的详细信息
		if (assemble.getFixedDepositCount() > 0 && bIsNeedInfoDetail == true) {
			// 查询获取提醒的详细信息
		}

		// 返回函数值
		return assemble;

	}

	/**
	 * 获得所有即将到期的定期存单（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getAllAtTermFixedDeposit(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllAtTermFixedDeposit ***");
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
	 * 获得所有即将到期的贷款存单（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getAllAtTermLoan(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllAtTermLoan ***");
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
	 * 获得所有需要转表外的放款通知单信息（包括总数）
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getOffbalanceinfo(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getOffbalanceinfo ***");
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

	
	
	// 将ArrayList类型转换为字符串,字符串格式是："1,2,3"
	private String ArrayListToString(ArrayList arrTmp) {

		// 定义变量
		StringBuffer strbTmp = new StringBuffer("");
		int i = 0;

		if (arrTmp != null) {
			for (i = 0; i < arrTmp.size() - 1; i++) {
				strbTmp.append(arrTmp.get(i));
				strbTmp.append(",");
			}
			strbTmp.append(arrTmp.get(i));
		}

		// 返回函数值
		return strbTmp.toString();

	}

	// 将字符串转换为ArrayList类型，字符串格式是："1,2,3"
	private ArrayList StringToArrayList(String strTmp) {

		// 定义变量
		ArrayList arrTmp = null;

		if (strTmp != "" && strTmp != null) {
			arrTmp = new ArrayList();
			String[] straTmp = DataFormat.splitString(strTmp, ",");
			for (int i = 0; i < straTmp.length; i++)
				if (straTmp[i] != "" && straTmp[i] != null)
					arrTmp.add(straTmp[i]);
		}

		// 返回函数值
		return arrTmp;
	}

	/**
	 * 保存业务提醒信息
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	private RemindAssemble saveRemindResultInfo(RemindAssemble assemble) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		long lRecordCount = 0;
		String strSQL = "";

		// 限额提醒信息数组转换为字符串
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
			// Log.print("***进入方法 -- saveReminResultInfo ***");
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
				// 修改
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
				// 新增
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
	 * 获得业务提醒设置信息
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	private RemindAssemble getRemindSettingInfo(RemindAssemble assemble) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		try {
			// Log.print("***进入方法 -- getRemindSettingInfo ***");
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

				assemble.setIsNeedBankBalance(rs.getDouble("MBANKLOWBALANCE"));// 银行余额提醒金额
				assemble.setIsNeedRateOfBankAndSett(rs.getDouble("MBANKLOWRATE"));// 银行头寸与活期存款提醒比例
				assemble.setIsNeedAccountDeadLine(rs.getLong("NACCOUNTDEADLINE"));// 是否需要限额底线提醒
				assemble.setLNegotiation(rs.getLong("NNEGOTIATION")); // 是否需要提醒协定存款
				assemble.setLEnsureDeposit(rs.getLong("NENSUREDEPOSIT")); // 是否需要提醒保证金存款
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
	 * 获得业务提醒信息
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public RemindAssemble getRemindInfo(long lOfficeID, long lCurrencyID) throws Exception {
		RemindAssemble assemble = null;
		try {
			// Log.print("***进入方法 -- getRemindInfo ***");
			assemble = new RemindAssemble();
			assemble.setOfficeID(lOfficeID);
			assemble.setCurrencyID(lCurrencyID);
			// 获得业务提醒设置信息
			assemble = this.getRemindSettingInfo(assemble);
			// 获得业务提醒结果信息
			assemble = this.getRemindResultInfo(assemble);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return assemble;
	}
	

	/**
	 * 获得业务提醒信息
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
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
			// Log.print("***进入方法 -- getRemindMSG ***");
			
			
			
			
			assemble = new RemindAssemble();
			sbMsg = new StringBuffer();
			assemble = this.getRemindInfo(lOfficeID, lCurrencyID);
			/****************结息提醒设置*********************/
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
			/**************************手工收款设置****************************************/
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
				//结息提醒

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
					//add by zcwang 2007-10-15 是否显示结算0笔业务提醒的业务信息
					
						if(settlement_remind)
						{
							sbMsg.append("共有<A href='remindOBTransaction.jsp' target=_blank>&nbsp;"
								+ assemble.getOBTransactionCount() + "&nbsp;</A>笔网上业务需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getOBTransactionCount()>0)
							{
							sbMsg.append("共有<A href='remindOBTransaction.jsp' target=_blank>&nbsp;"
									+ assemble.getOBTransactionCount() + "&nbsp;</A>笔网上业务需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
						}
					//
					}
			/*
				//中交加入  手工收款提醒！
				if (assemble.getIsNeedShouRemind() == 1) {
				sbMsg.append("共有<A href='shouGong.jsp' target=_blank>&nbsp;"
						+ myCount + "&nbsp;</A>笔手工收款需要处理！  ");
				}
			*/	
				if (assemble.getIsNeedShouRemind() == 1) {
						if(settlement_remind)
						{
							sbMsg.append("共有<A href='shouGong.jsp' target=_blank>&nbsp;"
								+myCount1+"&nbsp;</A>笔未入账需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(myCount1>0)
							{
							sbMsg.append("共有<A href='shouGong.jsp' target=_blank>&nbsp;"
										+myCount1+"&nbsp;</A>笔未入账需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
								    
				if (assemble.getIsNeedShouRemind() == 1) {
						if(settlement_remind)
						{
							sbMsg.append("共有<A href='shouGongSuccess.jsp' target=_blank>&nbsp;"
								+ myCount2 + "&nbsp;</A>笔入账成功！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(myCount2>0)
							{
								sbMsg.append("共有<A href='shouGongSuccess.jsp' target=_blank>&nbsp;"
										+ myCount2 + "&nbsp;</A>笔入账成功！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedShouRemind() == 1) {
						if(settlement_remind)
						{
							sbMsg.append("共有<A href='shouGongFail.jsp' target=_blank>&nbsp;"
									+ myCount3 + "&nbsp;</A>笔入账失败！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(myCount3>0)
							{
								sbMsg.append("共有<A href='shouGongFail.jsp' target=_blank>&nbsp;"
										+ myCount3 + "&nbsp;</A>笔入账失败！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
							}
						}
					}
				if (assemble.getIsNeedUncheckRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindUncheckTrans.jsp' target=_blank>&nbsp;"
								+ assemble.getUncheckRecordCount() + "&nbsp;</A>笔存贷款交易需要复核/勾账！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getUncheckRecordCount()>0)
							{
								sbMsg.append("共有<A href='remindUncheckTrans.jsp' target=_blank>&nbsp;"
										+ assemble.getUncheckRecordCount() + "&nbsp;</A>笔存贷款交易需要复核/勾账！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
						}
					}
				if (assemble.getIsNeedSecTransactionRemind() == 1) {
					// sbMsg.append("共有<A href='remindSecTransaction.jsp'
					// target=_blank>&nbsp;"+assemble.getSecTransactionCount()+"&nbsp;</A>笔收/付款日期为今日的证券业务通知单尚未处理！
					// ");
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindSecTransaction.jsp' target=_blank>&nbsp;"
								+ assemble.getSecTransactionCount() + "&nbsp;</A>笔证券业务通知单尚未处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getSecTransactionCount()>0)
							{
								sbMsg.append("共有<A href='remindSecTransaction.jsp' target=_blank>&nbsp;"
										+ assemble.getSecTransactionCount() + "&nbsp;</A>笔证券业务通知单尚未处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedSecUncheckRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindSecUncheckTrans.jsp' target=_blank>&nbsp;"
								+ assemble.getSecUncheckRecordCount()
								+ "&nbsp;</A>笔状态为未复核/暂存的证券交易未处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getSecUncheckRecordCount()>0)
							{
								sbMsg.append("共有<A href='remindSecUncheckTrans.jsp' target=_blank>&nbsp;"
										+ assemble.getSecUncheckRecordCount()
										+ "&nbsp;</A>笔状态为未复核/暂存的证券交易未处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedOverDraftRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindOverDraftAccount.jsp' target=_blank>&nbsp;"
								+ assemble.getOverDraftAccountCount() + "&nbsp;</A>笔账户透支！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getOverDraftAccountCount()>0)
							{
								sbMsg.append("共有<A href='remindOverDraftAccount.jsp' target=_blank>&nbsp;"
										+ assemble.getOverDraftAccountCount() + "&nbsp;</A>笔账户透支！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
						}
					}
				if (assemble.getIsNeedUngrantPayFormRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindUngrantPayForm.jsp' target=_blank>&nbsp;"
								+ assemble.getUngrantPayFormCount() + "&nbsp;</A>笔放款通知单需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getUngrantPayFormCount()>0)
							{
								sbMsg.append("共有<A href='remindUngrantPayForm.jsp' target=_blank>&nbsp;"
										+ assemble.getUngrantPayFormCount() + "&nbsp;</A>笔放款通知单需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedAheadPayFormRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindAheadRepayForm.jsp' target=_blank>&nbsp;"
								+ assemble.getAheadPayFormCount() + "&nbsp;</A>笔还款通知单需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getAheadPayFormCount()>0)
							{
								sbMsg.append("共有<A href='remindAheadRepayForm.jsp' target=_blank>&nbsp;"
										+ assemble.getAheadPayFormCount() + "&nbsp;</A>笔还款通知单需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					
					}
				if (assemble.getIsNeedFreeFormRemind() == 1) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindFreeForm.jsp' target=_blank>&nbsp;"
								+ assemble.getFreeFormCount() + "&nbsp;</A>笔免还通知单需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getFreeFormCount()>0)
							{
								sbMsg.append("共有<A href='remindFreeForm.jsp' target=_blank>&nbsp;"
										+ assemble.getFreeFormCount() + "&nbsp;</A>笔免还通知单需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
						
					}
				if (assemble.getFixedDepositAheadDays() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindAtTermFixedDeposit.jsp' target=_blank>&nbsp;"
								+ assemble.getFixedDepositCount() + "&nbsp;</A>笔定期存款将到期/已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getFixedDepositCount()>0)
							{
								sbMsg.append("共有<A href='remindAtTermFixedDeposit.jsp' target=_blank>&nbsp;"
										+ assemble.getFixedDepositCount() + "&nbsp;</A>笔定期存款将到期/已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getExhibitionDays() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindExhibition.jsp' target=_blank>&nbsp;"
								+ assemble.getSecExhibitionCount() + "&nbsp;</A>笔展期合同！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getSecExhibitionCount()>0)
							{
								sbMsg.append("共有<A href='remindExhibition.jsp' target=_blank>&nbsp;"
										+ assemble.getSecExhibitionCount() + "&nbsp;</A>笔展期合同！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getLoanAheadDays() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindAtTermLoan.jsp' target=_blank>&nbsp;"
								+ assemble.getLoanCount() + "&nbsp;</A>笔贷款将到期/已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getLoanCount()>0)
							{
								sbMsg.append("共有<A href='remindAtTermLoan.jsp' target=_blank>&nbsp;"
										+ assemble.getLoanCount() + "&nbsp;</A>笔贷款将到期/已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				// add by rxie for SEFC
				/*（中交没有汇票和汇票托收的提醒）
				if (assemble.getIsNeedBillRemindDay() > 0) {
					sbMsg.append("共有<A href='remindBill.jsp' target=_blank>&nbsp;"
							+ assemble.getBillRemindDay() + "&nbsp;</A>张汇票已到期！  ");
				}
				
				if (assemble.getIsNeedBillConsignReceiveDay() > 0) {
					sbMsg.append("共有<A href='remindBillConsignReceive.jsp' target=_blank>&nbsp;"
							+ assemble.getBillConsignReceiveDay() + "&nbsp;</A>张汇票托收已到期！  ");
				}
				*/
				if (assemble.getIsNeedFreezeDay() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindFreeze.jsp' target=_blank>&nbsp;"
								+ assemble.getFreezeDay() + "&nbsp;</A>笔冻结已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getFreezeDay()>0)
							{
								sbMsg.append("共有<A href='remindFreeze.jsp' target=_blank>&nbsp;"
										+ assemble.getFreezeDay() + "&nbsp;</A>笔冻结已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedLossDay() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindLoss.jsp' target=_blank>&nbsp;"
								+ assemble.getLossDay() + "&nbsp;</A>笔存单挂失！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getLossDay()>0)
							{
								sbMsg.append("共有<A href='remindLoss.jsp' target=_blank>&nbsp;"
										+ assemble.getLossDay() + "&nbsp;</A>笔存单挂失！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				if (assemble.getIsNeedPrimnessDay() > 0) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindPrimness.jsp' target=_blank>&nbsp;"
								+ assemble.getPrimnessDay() + "&nbsp;</A>笔呆滞已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{	
							if(assemble.getPrimnessDay()>0)
							{
								sbMsg.append("共有<A href='remindPrimness.jsp' target=_blank>&nbsp;"
										+ assemble.getPrimnessDay() + "&nbsp;</A>笔呆滞已到期！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}

				// 活期账号限额提醒
				if (assemble.getIsNeedAccountDeadLine() > 0) {
					ArrayList arr_Tmp = null;
					String strPathHead = "/NASApp/iTreasury-settlement/settlement/set/";
					String strTmp = null;

					// 余额底线
					arr_Tmp = assemble.getArrl_BalanceLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>个活期账户低于余额底线！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>个活期账户低于余额底线！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}

					// 借款发生额
					arr_Tmp = assemble.getArrl_DebitLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>个活期账户借方发生额超过限额！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>个活期账户借方发生额超过限额！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}

					// 贷款发生额
					arr_Tmp = assemble.getArrl_CreditLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>个活期账户贷方发生额超过限额！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>个活期账户贷方发生额超过限额！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}

					// 单笔交易发生额
					arr_Tmp = assemble.getArrl_TranspayLimited();
					if (arr_Tmp != null) {
						strTmp = ArrayListToString(arr_Tmp);
							if(settlement_remind)
							{
							sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
									+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
									+ "&nbsp;</A>个活期账户单笔发生额超过限额！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else
							{
								if(arr_Tmp.size()>0)
								{
									sbMsg.append("共有<A href='" + strPathHead + "control/c811.jsp?idStr="
											+ strTmp + "' target=_blank>&nbsp;" + arr_Tmp.size()
											+ "&nbsp;</A>个活期账户单笔发生额超过限额！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									
								}
							}
						}
				}
				// 获取协定存款提醒信息
				
				if (assemble.getLNegotiation() >= 0) {
						if(settlement_remind)
						{
						sbMsg.append("共有<A href='remindNegotiateDeposit.jsp' target=_'blank'>&nbsp;"
								+ assemble.getLSumNegotiation() + "&nbsp;</A>笔协定存款将要到期！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						else
						{
							if(assemble.getLSumNegotiation()>0)
							{
								sbMsg.append("共有<A href='remindNegotiateDeposit.jsp' target=_'blank'>&nbsp;"
										+ assemble.getLSumNegotiation() + "&nbsp;</A>笔协定存款将要到期！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								
							}
						}
					}
				//Add By Wangzhen 放款单逾期转表外提醒 2011-01-13
				if (assemble.getIsNeedOffBalanceFormRemind() == 1) {
					if(settlement_remind)
					{
					sbMsg.append("共有<A href='remindOffbalanceInfo.jsp' target=_blank>&nbsp;"
							+ assemble.getOFFBALANCEACCOUNT() + "&nbsp;</A>笔放款通知单已坏帐需要处理！ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}
					else
					{
						if(assemble.getOFFBALANCEACCOUNT()>0)
						{
							sbMsg.append("共有<A href='remindOffbalanceInfo.jsp' target=_blank>&nbsp;"
									+ assemble.getOFFBALANCEACCOUNT() + "&nbsp;</A>笔放款通知单已坏帐需要处理！  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
						}
					}
				}
//				modify by wjliu --2007-3-21 改为不输出该信息
				//Log.print("获取协定存款提醒信息：" + sbMsg.toString());
				
				// 获取保证金存款提醒信息
				/*(中交没有保证金滚动条提醒)
				if (assemble.getLEnsureDeposit() >= 0) {
					sbMsg.append("共有<A href='remindEnsureDeposit.jsp' target=_'blank'>&nbsp;"
							+ assemble.getLSumEnsureDeposit() + "&nbsp;</A>笔保证金存款将要到期！");
				}
				*/

				// add by yanliu
				/*
				if (assemble.getIsNeedBankBalance() > 0) {
					sbMsg.append("共有<A href='remindBankBalance.jsp?dLowBalance="
							+ assemble.getIsNeedBankBalance() + "' target=_blank>&nbsp;"
							+ assemble.getBankBalanceCount() + "&nbsp;</A>个开户行余额低于"
							+ assemble.getIsNeedBankBalance() + "元！  ");
				}
				*/

				if (Config.getBoolean(ConfigConstant.SETTLEMENT_INFORM_BANKCASH, false)) {
					DecimalFormat df = new DecimalFormat("#.00");
					String tmp = "0.00";
					if (assemble.getRateOfBankAndSett() > 0)
						tmp = df.format(assemble.getRateOfBankAndSett());
					sbMsg.append("银行头寸与活期存款比例为&nbsp;" + tmp + "%&nbsp;！  ");
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
						sbMsg.append("今天是" + lMonth + "月" + lDay + "日，请注意计算人行准备金！  ");
					}
					if ((lMonth == 3 || lMonth == 6 || lMonth == 9 || lMonth == 12) && lDay == 20) {
						sbMsg.append("今天是" + lMonth + "月" + lDay + "日，请注意季度结息！  ");
					}
					if (strLastday.equals(strToday)) {
						sbMsg.append("今天是" + lMonth + "月" + lDay + "日，请注意提供内部对账单！  ");
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
	 * 获得业务提醒结果信息
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	private RemindAssemble getRemindResultInfo(RemindAssemble assemble) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;

		// 限额提醒信息数组
		ArrayList arr_BalanceLimited = null;
		ArrayList arr_DebitLimited = null;
		ArrayList arr_CreditLimited = null;
		ArrayList arr_TranspayLimited = null;

		try {
			// Log.print("***进入方法 -- getRemindResultInfo ***");
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
				assemble.setSecExhibitionCount(rs.getLong("nExtendnotice"));// 展期合同提醒数量
				assemble.setBillRemindDay(rs.getLong("nBillRemindCount"));
				assemble.setBillConsignReceiveDay(rs.getLong("nBillConsignReceiveCount"));
				assemble.setFreezeDay(rs.getLong("nFreezeCount"));
				assemble.setLossDay(rs.getLong("nLossCount"));
				assemble.setPrimnessDay(rs.getLong("nPrimnessCount"));

				assemble.setRateOfBankAndSett(rs.getDouble("MRATEOFBANKANDSETT"));
				assemble.setBankBalanceCount(rs.getLong("NBANKLOWBALANCECOUNT"));

				// 获取限额提醒信息
				arr_BalanceLimited = StringToArrayList(rs.getString("BALANCELIMITED"));
				arr_DebitLimited = StringToArrayList(rs.getString("DEBITLIMITED"));
				arr_CreditLimited = StringToArrayList(rs.getString("CREDITLIMITED"));
				arr_TranspayLimited = StringToArrayList(rs.getString("TRANSPAYLIMITED"));
				assemble.setArrl_BalanceLimited(arr_BalanceLimited);
				assemble.setArrl_DebitLimited(arr_DebitLimited);
				assemble.setArrl_CreditLimited(arr_CreditLimited);
				assemble.setArrl_TranspayLimited(arr_TranspayLimited);

				// 获取协定存款提醒信息
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
	 * 获得 汇票到期提醒及提前天数 合同号、贴现凭证号、汇票号码、汇票种类、到期日、托收日、金额
	 */
	public RemindAssemble getAllBillRemind(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllBillRemind ***");
			// 获得业务提醒设置信息
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("获得业务提醒设置信息结束");
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
	 * 获得 汇票托收提醒及提前天数 合同号、贴现凭证号、汇票号码、汇票种类、到期日、托收日、金额
	 */
	public RemindAssemble getAllBillConsignReceive(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllBillConsignReceive ***");
			// 获得业务提醒设置信息
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("获得业务提醒设置信息结束");
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
	 * 获得 冻结到期提醒及提前天数 客户号、名称、账户号、存单号、冻结方式、冻结金额、解冻金额、冻结执行单位、申请执行单位、日期;
	 */
	public RemindAssemble getAllFreeze(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllFreeze ***");
			// 获得业务提醒设置信息
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("获得业务提醒设置信息结束");
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
	//中交加入
	}

	/**
	 * 获得 挂失到期提醒及挂失天数 交易类型、客户号、名称、账户号、存单号、新存单、日期
	 */
	public RemindAssemble getAllLoss(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllLoss ***");
			// 获得业务提醒设置信息
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("获得业务提醒设置信息结束");
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
	 * 获得 贷款呆滞提醒及到期后N天提醒
	 */
	public RemindAssemble getAllPrimness(RemindAssemble assemble, boolean bIsNeedDetail)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		String strCountSQL = "";
		try {
			// Log.print("***进入方法 -- getAllPrimness ***");
			// 获得业务提醒设置信息
			assemble = this.getRemindSettingInfo(assemble);
			// Log.print("获得业务提醒设置信息结束");
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

		Timestamp dtToDay = Env.getSystemDateTime(); // 业务系统日期
		//dtToDay.setDate(1);
		
		System.out.println("系统当前系统日期是："+DataFormat.getDateString(dtToDay));
		
		Date dtTmp = new Date(dtToDay.getTime());
		int i= 0 ;
		dtTmp = DataFormat.getPreviousOrNextDate(dtTmp, i);
		dtToDay = new Timestamp(dtTmp.getTime());
		
		System.out.println("提前"+i+"天后的日期是："+ DataFormat.getDateString(dtToDay));

	}

	private static void test_2() {

		Log.print("--业务提醒后台处理---");
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
