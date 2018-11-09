package com.iss.itreasury.settlement.transloan.dao;

import java.sql.Timestamp;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
public class TransloanDao {
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
	 * 付息活期账户
	 */
	public final static int ORDER_PAYINTEREST_ACCOUNT = 13;
	private final static String ORDER_PAYINTEREST_ACCOUNT_NAME = "nPayInterestAccountID";

	/**
	 * 交易号
	 */
	public final static int ORDER_TEMPTRANS_NO = 14;
	private final static String ORDER_TEMPTRANS_NO_NAME = "sTempTransNo";
	
	private final static StringBuffer sbRepaymentSQL = new StringBuffer(128);
	
	static {
		sbRepaymentSQL.append("ID, NOFFICEID, NCURRENCYID, STRANSNO, NTRANSACTIONTYPEID, \n");
		sbRepaymentSQL.append("NFREEFORMID, NISCANCLELOAN, NCLIENTID, NDEPOSITACCOUNTID, SCONSIGNVOUCHERNO, \n");
		sbRepaymentSQL.append("SCONSIGNPASSWORD, SBILLNO, NBILLTYPEID, NBILLBANKID, NBANKID, \n");
		sbRepaymentSQL.append("SEXTBANKNO, NCASHFLOWID, NLOANACCOUNTID, NLOANCONTRACTID, NLOANNOTEID, \n");
		sbRepaymentSQL.append("NPREFORMID, MAMOUNT, DTINTERESTSTART, NABSTRACTID, SABSTRACT, \n");
		sbRepaymentSQL.append("NCONSIGNACCOUNTID, NCONSIGNDEPOSITACCOUNTID, NPAYINTERESTACCOUNTID, NINTERESTBANKID, NINTERESTCASHFLOWID, \n");
		sbRepaymentSQL.append("NRECEIVEINTERESTACCOUNTID, NPAYSUERTYFEEACCOUNTID, NSUERTYFEEBANKID, NSUERTYFEECASHFLOWID, NRECEIVESUERTYFEEACCOUNTID, \n");
		sbRepaymentSQL.append("NCOMMISSIONACCOUNTID, NCOMMISSIONBANKID, NCOMMISSIONCASHFLOWID, MINTEREST, MINTERESTRECEIVABLE, \n");
		sbRepaymentSQL.append("MINTERESTINCOME, MINTERESTTAX, MCOMPOUNDINTEREST, MOVERDUEINTEREST, MSURETYFEE, \n");
		sbRepaymentSQL.append("MCOMMISSION, SADJUSTINTERESTREASON, MADJUSTINTEREST, MAHEADRECEIVEINTEREST, NISREMITINTEREST, \n");
		sbRepaymentSQL.append("NISREMITCOMPOUNDINTEREST, NISREMITOVERDUEINTEREST, NISREMITSURETYFEE, NISREMITCOMMISSION, NCAPITALANDINTERESTDEALWAY, \n");
		sbRepaymentSQL.append("MREALINTEREST, MREALINTERESTRECEIVABLE, MREALINTERESTINCOME, MREALINTERESTTAX, MREALCOMPOUNDINTEREST, \n");
		sbRepaymentSQL.append("MREALOVERDUEINTEREST, MREALSURETYFEE, MREALCOMMISSION, DTINPUT, NINPUTUSERID, \n");
		sbRepaymentSQL.append("NCHECKUSERID, SCHECKABSTRACT, NSTATUSID, DTMODIFY,DTEXECUTE,DTINTERESTCLEAR,NSUBACCOUNTID,mCurrentBalance, \n");
		sbRepaymentSQL.append("dtLatestInterestClear,nTransDirectionID,sDeclarationNo,sTempTransNO, \n");
		sbRepaymentSQL.append("dtCompoundInterestStart,mCompoundAmount,mCompoundRate, \n");	//复利信息
		sbRepaymentSQL.append("dtOverDueStart,mOverDueAmount,mOverDueRate, \n");			//逾期罚息信息
		sbRepaymentSQL.append("mLoanRepaymentRate,dtSuretyFeeStart,mSuretyFeeRate, \n");	//为打印添加第二组信息
		sbRepaymentSQL.append("dtCommissionStart,mCommissionRate,nSerialNo,SINSTRUCTIONNO "); //为网银添加
	}
	public String queryTransloanSQL(TransRepaymentLoanInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		long[] statusids = null;
		statusids = qInfo.getStatusIDs();
		
		sbSQL.append("select ");
		sbSQL.append(sbRepaymentSQL.toString());
		sbSQL.append(" FROM SETT_TRANSREPAYMENTLOAN ");
		sbSQL.append(" where nOfficeID = " + qInfo.getOfficeID());
		sbSQL.append(" and nCurrencyID = " + qInfo.getCurrencyID());
		sbSQL.append(" and nTransactionTypeID = " + qInfo.getTransactionTypeID() + " \n");
		
		if (qInfo.getInputUserID() > 0) //录入人
		{
			sbSQL.append(" and nInputUserID =" + qInfo.getInputUserID() + " \n");
		}
		if (qInfo.getCheckUserID() > -1) //复合人
		{
			sbSQL.append(" and nCheckUserID =" + qInfo.getCheckUserID() + " \n");
		}
		
		if (qInfo.getTransDirectionID() > 0){ //交易方向
			sbSQL.append(" and nTransDirectionID =" + qInfo.getTransDirectionID() + " \n");
		}
		
		long[] statusIDs = qInfo.getStatusIDs();

		Log.print("状态数组:" + statusIDs);
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
				{ //暂存没有时间限制
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
					{ //保存要查当天的
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
						sbSQL.append(" and dtExecute= TO_DATE('"+DataFormat.getDateString(qInfo.getExecute())+"','yyyy-mm-dd') )");
					}
					else
						if (statusIDs[i] == SETTConstant.TransactionStatus.CHECK)
						{ //已经复核的要查当天
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
							sbSQL.append(" and dtExecute= TO_DATE('"+DataFormat.getDateString(qInfo.getExecute())+"','yyyy-mm-dd') )");
						}
						else
						{
							sbSQL.append("and ");
							sbSQL.append("nStatusID = " + statusIDs[i]); //空白的时候
						}
			}
			if (!isStart)
				sbSQL.append(") ");
		}
		else
		{ //如果没有statusID，默认查全部有效记录
			sbSQL.append(" and (nStatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
			sbSQL.append(" or (nStatusID = " + SETTConstant.TransactionStatus.SAVE);
			sbSQL.append(" and dtExecute= TO_DATE('"+DataFormat.getDateString(qInfo.getExecute())+"','yyyy-mm-dd') )");
		}
		if (qInfo.getOrderByType() > 0)
		{
			sbSQL.append(" order by " + getOrderColumnName((int) qInfo.getOrderByType()));
			if (qInfo.getAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_ASC)
			{
				sbSQL.append(" asc ");
			}
			else
			{
				sbSQL.append(" desc ");
			}
		}

		
		return sbSQL.toString();
	}

	/**
	 * 将次序类型转换为列名
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
			case ORDER_PAYINTEREST_ACCOUNT :
				returnValue = ORDER_PAYINTEREST_ACCOUNT_NAME;
				break;
			case ORDER_TEMPTRANS_NO :
				returnValue = ORDER_TEMPTRANS_NO_NAME;
				break;
		}
		return returnValue;
	}

	public String findInterestByNoteID(long lLoanNoteID, long officeID,
			long currencyID) {
		String tsDate = Env.getSystemDateString(officeID, currencyID);
		StringBuffer sbSQL = new StringBuffer(128);
		sbSQL.append(" SELECT a.nloanaccountid,a.nsubaccountid,a.nclientid, a.nloancontractid,a.npreformid,a.mamount, b.dtoutdate,\n");
		sbSQL.append(" nvl(b.minterestrate,0) interestrate,nvl(b.mAdjustRate,0) mAdjustRate,nvl(b.mStaidAdjustRate,0) mStaidAdjustRate,a.dtintereststart \n");
		sbSQL.append("FROM SETT_TRANSREPAYMENTLOAN a,loan_payform b \n");
		sbSQL.append(" where a.nloannoteid = b.id ");
		sbSQL.append(" and a.nofficeid = '");
		sbSQL.append(officeID);
		sbSQL.append("'");
		sbSQL.append(" and a.ncurrencyid =  '");
		sbSQL.append(currencyID).append("'");
		sbSQL.append(" and a.nstatusid = "+SETTConstant.TransactionStatus.CHECK);
		sbSQL.append(" and b.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
		sbSQL.append(" and a.nloannoteid = '").append(lLoanNoteID).append("'");
		sbSQL.append(" and a.dtexecute =to_date('" + tsDate +"','yyyy-mm-dd')");	//有干扰找XWHE,此条件加入原因：当一笔放款单，有多次还款时，系统无法区分在上次结息日以前的还款也会查出来，这是错误的，现在业务流程是，利随本清
		sbSQL.append(" and a.ntransactiontypeid in("+SETTConstant.TransactionType.TRUSTLOANRECEIVE+","+SETTConstant.TransactionType.CONSIGNLOANRECEIVE+")");
		return sbSQL.toString();
	}
}
