package com.iss.itreasury.settlement.transloan.dao;

import org.apache.log4j.Logger;

import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

public class QTransRepaymentLoanDao {
	private static Logger logger = Logger
			.getLogger(Sett_TransGrantLoanDAO.class);
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

	/**
	 * 付息活期账户
	 */
	public final static int ORDER_PAYINTEREST_ACCOUNT = 13;
	private final static String ORDER_PAYINTEREST_ACCOUNT_NAME = "nPayInterestAccountID";

	/**
	 * 交易号
	 */
	public final static int ORDER_TEMPTRANS_NO = 14;
	private final static String ORDER_TEMPTRANS_NO_NAME = "sTempTransNo";

	public String queryRepaymentLoanSQL(TransRepaymentLoanInfo info) {
		Log
				.print("step into QTransRepaymentLoanDao.queryRepaymentLoanSQL............");
		StringBuffer sbSQL = new StringBuffer(128); // 基本查询语句

		sbSQL.append("SELECT ");
		sbSQL.append("ID, NOFFICEID, NCURRENCYID, STRANSNO, NTRANSACTIONTYPEID, \n");
		sbSQL.append("NFREEFORMID, NISCANCLELOAN, NCLIENTID, NDEPOSITACCOUNTID, SCONSIGNVOUCHERNO, \n");
		sbSQL.append("SCONSIGNPASSWORD, SBILLNO, NBILLTYPEID, NBILLBANKID, NBANKID, \n");
		sbSQL.append("SEXTBANKNO, NCASHFLOWID, NLOANACCOUNTID, NLOANCONTRACTID, NLOANNOTEID, \n");
		sbSQL.append("NPREFORMID, MAMOUNT, DTINTERESTSTART, NABSTRACTID, SABSTRACT, \n");
		sbSQL.append("NCONSIGNACCOUNTID, NCONSIGNDEPOSITACCOUNTID, NPAYINTERESTACCOUNTID, NINTERESTBANKID, NINTERESTCASHFLOWID, \n");
		sbSQL.append("NRECEIVEINTERESTACCOUNTID, NPAYSUERTYFEEACCOUNTID, NSUERTYFEEBANKID, NSUERTYFEECASHFLOWID, NRECEIVESUERTYFEEACCOUNTID, \n");
		sbSQL.append("NCOMMISSIONACCOUNTID, NCOMMISSIONBANKID, NCOMMISSIONCASHFLOWID, MINTEREST, MINTERESTRECEIVABLE, \n");
		sbSQL.append("MINTERESTINCOME, MINTERESTTAX, MCOMPOUNDINTEREST, MOVERDUEINTEREST, MSURETYFEE, \n");
		sbSQL.append("MCOMMISSION, SADJUSTINTERESTREASON, MADJUSTINTEREST, MAHEADRECEIVEINTEREST, NISREMITINTEREST, \n");
		sbSQL.append("NISREMITCOMPOUNDINTEREST, NISREMITOVERDUEINTEREST, NISREMITSURETYFEE, NISREMITCOMMISSION, NCAPITALANDINTERESTDEALWAY, \n");
		sbSQL.append("MREALINTEREST, MREALINTERESTRECEIVABLE, MREALINTERESTINCOME, MREALINTERESTTAX, MREALCOMPOUNDINTEREST, \n");
		sbSQL.append("MREALOVERDUEINTEREST, MREALSURETYFEE, MREALCOMMISSION, DTINPUT, NINPUTUSERID, \n");
		sbSQL.append("NCHECKUSERID, SCHECKABSTRACT, NSTATUSID, DTMODIFY,DTEXECUTE,DTINTERESTCLEAR,NSUBACCOUNTID,mCurrentBalance, \n");
		sbSQL.append("dtLatestInterestClear,nTransDirectionID,sDeclarationNo,sTempTransNO, \n");
		sbSQL.append("dtCompoundInterestStart,mCompoundAmount,mCompoundRate, \n");	//复利信息
		sbSQL.append("dtOverDueStart,mOverDueAmount,mOverDueRate, \n");			//逾期罚息信息
		sbSQL.append("mLoanRepaymentRate,dtSuretyFeeStart,mSuretyFeeRate, \n");	//为打印添加第二组信息
		sbSQL.append("dtCommissionStart,mCommissionRate,nSerialNo,SINSTRUCTIONNO ");
		sbSQL.append("FROM SETT_TRANSREPAYMENTLOAN ");
		sbSQL.append(" where nOfficeID = " + info.getOfficeID());
		sbSQL.append(" and nCurrencyID = " + info.getCurrencyID());
		sbSQL.append(" and nTransactionTypeID = " + info.getTransactionTypeID()
				+ " \n");
		if (info.getInputUserID() > 0) // 录入人
		{
			sbSQL.append(" and nInputUserID =" + info.getInputUserID() + " \n");
		}
		if (info.getCheckUserID() > -1) // 复合人
		{
			sbSQL.append(" and nCheckUserID =" + info.getCheckUserID() + " \n");
		}

		if (info.getTransDirectionID() > 0) { // 交易方向
			sbSQL.append(" and nTransDirectionID ="
					+ info.getTransDirectionID() + " \n");
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
			sbSQL.append(" and dtExecute=to_date('"+ (info.getExecute()+"").split(" ")[0]+"','yyyy-mm-dd')) ");
		}
		if (info.getOrderByType() > 0) {
			sbSQL.append(" order by "
					+ getOrderColumnName((int) info.getOrderByType()));
			if (info.getAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_ASC) {
				sbSQL.append(" asc ");
			} else {
				sbSQL.append(" desc ");
			}
		}
		Log.print(sbSQL.toString());
		return sbSQL.toString();

	}

	private static String getOrderColumnName(int orderType) {
		String returnValue = "";
		switch (orderType) {
		case ORDER_TRANS_NO:
			returnValue = ORDER_TRANS_NO_NAME;
			break;
		case ORDER_TRANSACTION_TYPE_ID:
			returnValue = ORDER_TRANSACTION_TYPE_ID_NAME;
			break;
		case ORDER_LOAN_ACCOUNT_ID:
			returnValue = ORDER_LOAN_ACCOUNT_ID_NAME;
			break;
		case ORDER_LOAN_CONTRACT_ID:
			returnValue = ORDER_LOAN_CONTRACT_ID_NAME;
			break;
		case ORDER_LOAN_NOTE_ID:
			returnValue = ORDER_LOAN_NOTE_ID_NAME;
			break;
		case ORDER_DEPOSIT_ACCOUNT_ID:
			returnValue = ORDER_DEPOSIT_ACCOUNT_ID_NAME;
			break;
		case ORDER_AMOUNT:
			returnValue = ORDER_AMOUNT_NAME;
			break;
		case ORDER_INTEREST_START:
			returnValue = ORDER_INTEREST_START_NAME;
			break;
		case ORDER_ABSTRACT:
			returnValue = ORDER_ABSTRACT_NAME;
			break;
		case ORDER_STATUS:
			returnValue = ORDER_STATUS_NAME;
			break;
		case ORDER_PAYINTEREST_ACCOUNT:
			returnValue = ORDER_PAYINTEREST_ACCOUNT_NAME;
			break;
		case ORDER_TEMPTRANS_NO:
			returnValue = ORDER_TEMPTRANS_NO_NAME;
			break;
		}
		return returnValue;
	}
}
