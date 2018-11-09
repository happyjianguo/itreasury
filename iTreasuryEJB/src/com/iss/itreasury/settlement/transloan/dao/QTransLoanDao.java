package com.iss.itreasury.settlement.transloan.dao;

import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * 账户查询数据层
 * 
 * @author xiang
 * 
 */
public class QTransLoanDao {
	/**
	 * 交易号
	 */
	public final static int ORDER_TRANS_NO = 1;
	private final static String ORDER_TRANS_NO_NAME = "sTransNo";
	/**
	 * 交易类型
	 */
	public final static int ORDER_TRANSACTION_TYPE_ID = 2;
	private final static String ORDER_TRANSACTION_TYPE_ID_NAME = "nTransactionTypeID";
	/**
	 * 贷款账户
	 */
	public final static int ORDER_LOAN_ACCOUNT_ID = 3;
	private final static String ORDER_LOAN_ACCOUNT_ID_NAME = "nLoanAccountID";
	/**
	 * 贷款合同号
	 */
	public final static int ORDER_LOAN_CONTRACT_ID = 4;
	private final static String ORDER_LOAN_CONTRACT_ID_NAME = "nLoanContractID";
	/**
	 * 放款通知单
	 */
	public final static int ORDER_LOAN_NOTE_ID = 5;
	private final static String ORDER_LOAN_NOTE_ID_NAME = "nLoanNoteID";
	/**
	 * 活期存款账户ID
	 */
	public final static int ORDER_DEPOSIT_ACCOUNT_ID = 6;
	private final static String ORDER_DEPOSIT_ACCOUNT_ID_NAME = "nDepositAccountID";
	/**
	 * 金额
	 */
	public final static int ORDER_AMOUNT = 7;
	private final static String ORDER_AMOUNT_NAME = "mAmount";
	/**
	 * 起息日
	 */
	public final static int ORDER_INTEREST_START = 8;
	private final static String ORDER_INTEREST_START_NAME = "dtInterestStart";
	/**
	 * 摘要
	 */
	public final static int ORDER_ABSTRACT = 9;
	private final static String ORDER_ABSTRACT_NAME = "sAbstract";
	/**
	 * 交易状态
	 */
	public final static int ORDER_STATUS = 10;
	private final static String ORDER_STATUS_NAME = "nStatusID";
	/**
	 * 修改时间
	 */
	public final static int ORDER_MODIFY = 11;
	/**
	 * 录入日期
	 */
	public final static int ORDER_INPUT_DATE = 12;

	public String queryLoanSQL(TransGrantLoanInfo info) {
		StringBuffer sbGrantSQL = new StringBuffer();
		sbGrantSQL
				.append("ID,NOFFICEID,NCURRENCYID, STRANSNO, NTRANSACTIONTYPEID, \n");
		sbGrantSQL
				.append("NLOANACCOUNTID,NLOANCONTRACTID, NLOANNOTEID, NEXTENDFORMID, NCONSIGNDEPOSITACCOUNTID,\n");
		sbGrantSQL
				.append("NISKEEPACCOUNT, NPAYINTERESTACCOUNTID, NRECEIVEINTERESTACCOUNTID,NPAYSURETYFEEACCOUNTID, NRECEIVESURETYFEEACCOUNTID,\n");
		sbGrantSQL
				.append("NPAYCOMMISIONACCOUNTID,MINTERESTTAXRATE, DTINTERESTTAXRATEVAULEDATE, NDEPOSITACCOUNTID, NPAYTYPEID,\n");
		sbGrantSQL
				.append("NBANKID, SEXTACCTNO, SEXTACCTNAME, SBANKNAME, SPROVINCE, \n");
		sbGrantSQL
				.append("SCITY, MAMOUNT,NCASHFLOWID, DTINTERESTSTART, DTEXECUTE,\n");
		sbGrantSQL
				.append("DTMODIFY, DTINPUT, NINPUTUSERID,NCHECKUSERID, NABSTRACTID,  \n");
		sbGrantSQL
				.append("SABSTRACT, SCHECKABSTRACT, NSTATUSID, SEXTBANKNO ,nInterestTaxPlanId \n");

		StringBuffer sbSQL = new StringBuffer(128);
		sbSQL.append("SELECT ");
		sbSQL.append(sbGrantSQL.toString());
		sbSQL.append("FROM SETT_TRANSGRANTLOAN");
		sbSQL.append(" where nOfficeID = " + info.getOfficeID());
		sbSQL.append("  and nCurrencyID = " + info.getCurrencyID());
		sbSQL
				.append(" and nTransactionTypeID = "
						+ info.getTransactionTypeID());
		if (info.getInputUserID() > 0) {
			sbSQL.append(" and nInputUserID =" + info.getInputUserID() + " \n");
		}
		if (info.getCheckUserID() != -1) {
			sbSQL.append(" and nCheckUserID =" + info.getCheckUserID() + " \n");
		}
		long[] statusIDs = info.getStatusIDs();

		for (int i = 0; i < statusIDs.length; i++) {
			Log.print("statusID" + i + ":" + statusIDs[i]);
		}

		if (statusIDs != null) {
			boolean isStart = true;
			for (int i = 0; i < statusIDs.length; i++) {
				Log.print("status:" + statusIDs[i]);
				if (statusIDs[i] == SETTConstant.TransactionStatus.TEMPSAVE) { // 暂存没有时间限制
					if (isStart) {
						sbSQL.append("and (");
						isStart = !isStart;
					} else {
						sbSQL.append("or ");
					}
					sbSQL.append("(nStatusID = "
							+ SETTConstant.TransactionStatus.TEMPSAVE + ") ");
				} else if (statusIDs[i] == SETTConstant.TransactionStatus.SAVE) { // 保存要查当天的
					if (isStart) {
						sbSQL.append("and (");
						isStart = !isStart;
					} else {
						sbSQL.append("or ");
					}
					sbSQL.append("(nStatusID = "
							+ SETTConstant.TransactionStatus.SAVE);
					sbSQL.append(" and dtExecute=to_date('"+ (info.getExecute()+"").split(" ")[0]+"','yyyy-mm-dd')) ");
				} else if (statusIDs[i] == SETTConstant.TransactionStatus.CHECK) { // 已经复核的要查当天
					if (isStart) {
						sbSQL.append("and (");
						isStart = !isStart;
					} else {
						sbSQL.append("or ");
					}
					sbSQL.append("(nStatusID = "
							+ SETTConstant.TransactionStatus.CHECK);
					sbSQL.append(" and dtExecute=to_date('"+ (info.getExecute()+"").split(" ")[0]+"','yyyy-mm-dd')) ");
				} else {
					sbSQL.append("and ");
					sbSQL.append("nStatusID = " + statusIDs[i]); // 空白的时候
				}
			}
			if (!isStart)
				sbSQL.append(") ");
		} else { // 如果没有statusID，默认查全部有效记录
			sbSQL.append(" and (nStatusID = "
					+ SETTConstant.TransactionStatus.TEMPSAVE + ") ");
			sbSQL.append(" or (nStatusID = "
					+ SETTConstant.TransactionStatus.SAVE);
			sbSQL.append(" and dtExecute=to_date('"+ (info.getExecute()+"").split(" ")[0]+"','yyyy-mm-dd 24hh')) ");
		}
		Log.print(sbSQL.toString());
		return sbSQL.toString();
	}

}
