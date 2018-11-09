package com.iss.itreasury.settlement.transgeneralledger.dao;

import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.util.DataFormat;

public class TransGeneralLedgerDao {
	
	public String queryTransGeneralLedger(TransGeneralLedgerInfo qInfo){
		
		long[] statusids = null;
		statusids = qInfo.getStatusIDs();
		
		StringBuffer strSQLBuffer = new StringBuffer(256);

		strSQLBuffer.append("SELECT tt.*,'总账业务' as businessTypeName FROM \n");
		strSQLBuffer.append("SETT_TRANSGENERALLEDGER tt \n");

		//flag for deciding whether there is WHERE in query string
		boolean isNeedWhere = false;

		StringBuffer strWhereSQLBuffer = new StringBuffer(128);

		if (qInfo.getID() != -1)
		{
			strWhereSQLBuffer.append(" AND ID = " + qInfo.getID() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getOfficeID() != -1)
		{
			strWhereSQLBuffer.append(" AND NOFFICEID = " + qInfo.getOfficeID() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getCurrencyID() != -1)
		{
			strWhereSQLBuffer.append(" AND NCURRENCYID = " + qInfo.getCurrencyID() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
		{
			strWhereSQLBuffer.append(" AND STRANSNO = '" + qInfo.getTransNo() + "' \n");
			isNeedWhere = true;
		}

		if (qInfo.getTransActionTypeID() != -1)
		{
			strWhereSQLBuffer.append(" AND NTRANSACTIONTYPEID = " + qInfo.getTransActionTypeID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getClientID() != -1)
		{
			strWhereSQLBuffer.append(" AND NCLIENTID = " + qInfo.getClientID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getAccountID() != -1)
		{
			strWhereSQLBuffer.append(" AND NACCOUNTID = " + qInfo.getAccountID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getDirection() != -1)
		{
			strWhereSQLBuffer.append(" AND NDIRECTION = " + qInfo.getDirection() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getAmount() != 0.0)
		{
			strWhereSQLBuffer.append(" AND MAMOUNT = " + qInfo.getAmount() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getGeneralLedgerOne() != -1)
		{
			strWhereSQLBuffer.append(" AND NGENERALLEDGERONE = " + qInfo.getGeneralLedgerOne() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getDirOne() != -1)
		{
			strWhereSQLBuffer.append(" AND NDIRONE = " + qInfo.getDirOne() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getAmountOne() != 0.0)
		{
			strWhereSQLBuffer.append(" AND MONE = " + qInfo.getAmountOne() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getGeneralLedgerTwo() != -1)
		{
			strWhereSQLBuffer.append(" AND NGENERALLEDGERTWO = " + qInfo.getGeneralLedgerTwo() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getDirTwo() != -1)
		{
			strWhereSQLBuffer.append(" AND NDIRTWO = " + qInfo.getDirTwo() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getAmountTwo() != 0.0)
		{
			strWhereSQLBuffer.append(" AND MTWO = " + qInfo.getAmountTwo() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getGeneralLedgerThree() != -1)
		{
			strWhereSQLBuffer.append(" AND NGENERALLEDGERTHREE = " + qInfo.getGeneralLedgerThree() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getDirThree() != -1)
		{
			strWhereSQLBuffer.append(" AND NDIRTHREE = " + qInfo.getDirThree() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getAmountThree() != 0.0)
		{
			strWhereSQLBuffer.append(" AND MTHREE = " + qInfo.getAmountThree() + " \n");
			isNeedWhere = true;
		}
		//---------------------------中  交  加  入-------------------------------
		if (qInfo.getGeneralLedgerFour()!= -1)
		{
			strWhereSQLBuffer.append(" AND NGENERALLEDGERFOUR = " + qInfo.getGeneralLedgerFour() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getDirFour()!= -1)
		{
			strWhereSQLBuffer.append(" AND NDIRFOUR = " + qInfo.getDirFour() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getAmountFour() != 0.0)
		{
			strWhereSQLBuffer.append(" AND MFOUR = " + qInfo.getAmountFour() + " \n");
			isNeedWhere = true;
		}
		//----------------------------------------------------------------------------

		if (qInfo.getVoucherNo() != null && qInfo.getVoucherNo().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SVOUCHERNO = " + qInfo.getVoucherNo() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getVoucherPWD() != null && qInfo.getVoucherPWD().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SVOUCHERPWD = " + qInfo.getVoucherPWD() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getBillNo() != null && qInfo.getBillNo().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SBILLNO = " + qInfo.getBillNo() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getBillTypeID() != -1)
		{
			strWhereSQLBuffer.append(" AND NBILLTYPEID = " + qInfo.getBillTypeID() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getBillBankID() != -1)
		{
			strWhereSQLBuffer.append(" AND NBILLBANKID = " + qInfo.getBillBankID() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getPayExtBankNo() != null && qInfo.getPayExtBankNo().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SPAYEXTBANKNO = " + qInfo.getPayExtBankNo() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getReceiveExtBankNo() != null && qInfo.getReceiveExtBankNo().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SRECEIVEEXTBANKNO = " + qInfo.getReceiveExtBankNo() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getInterestStartDate() != null)
		{
			strWhereSQLBuffer.append(
				" AND DTINTERESTSTART = to_date('"
					+ DataFormat.getDateString(qInfo.getInterestStartDate())
					+ "', 'yyyy-mm-dd') \n");
			isNeedWhere = true;
		}

		if (qInfo.getExecuteDate() != null)
		{
			strWhereSQLBuffer.append(
				" AND DTEXECUTE = to_date('"
					+ DataFormat.getDateString(qInfo.getExecuteDate())
					+ "', 'yyyy-mm-dd') \n");
			isNeedWhere = true;
		}

		if (qInfo.getModifyDate() != null)
		{
			strWhereSQLBuffer.append(
				" AND DTMODIFY = to_date('"
					+ DataFormat.getDateString(qInfo.getModifyDate())
					+ "', 'yyyy-mm-dd') \n");
			isNeedWhere = true;
		}

		if (qInfo.getInputUserID() != -1)
		{
			strWhereSQLBuffer.append(" AND NINPUTUSERID = " + qInfo.getInputUserID() + " \n");
			isNeedWhere = true;
		}

		if (qInfo.getInputDate() != null)
		{
			strWhereSQLBuffer.append(
				" AND DTINPUT = to_date('"
					+ DataFormat.getDateString(qInfo.getInputDate())
					+ "', 'yyyy-mm-dd') \n");
			isNeedWhere = true;
		}

		if (qInfo.getCheckUserID() != -1)
		{
			strWhereSQLBuffer.append(" AND NCHECKUSERID = " + qInfo.getCheckUserID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getSignUserID() != -1)
		{
			strWhereSQLBuffer.append(" AND NSIGNUSERID = " + qInfo.getSignUserID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getConfirmUserID() != -1)
		{
			strWhereSQLBuffer.append(" AND NCONFIRMUSERID = " + qInfo.getConfirmUserID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getConfirmOfficeID() != -1)
		{
			strWhereSQLBuffer.append(" AND NCONFIRMOFFICEID = " + qInfo.getConfirmOfficeID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getAbstractID() != -1)
		{
			strWhereSQLBuffer.append(" AND NABSTRACTID = " + qInfo.getAbstractID() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getAbstract() != null && qInfo.getAbstract().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SABSTRACT = " + qInfo.getAbstract() + " \n");
			isNeedWhere = true;
		}
		if (qInfo.getCheckAbstract() != null && qInfo.getCheckAbstract().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SCHECKABSTRACT = '" + qInfo.getCheckAbstract() + "' \n");
			isNeedWhere = true;
		}

		if (qInfo.getCancelCheckAbstract() != null && qInfo.getCancelCheckAbstract().length() > 0)
		{
			strWhereSQLBuffer.append(
				" AND SCANCLECHECKABSTRACT = '" + qInfo.getCancelCheckAbstract() + "' \n");
			isNeedWhere = true;
		}
		if (qInfo.getConfirmAbstract() != null && qInfo.getConfirmAbstract().length() > 0)
		{
			strWhereSQLBuffer.append(" AND SCONFIRMABSTRACT = '" + qInfo.getConfirmAbstract() + "' \n");
			isNeedWhere = true;
		}

		if (qInfo.getStatusIDs() != null)
		{
			strWhereSQLBuffer.append(" AND NSTATUSID IN(" + qInfo.getStatusIDs()[0] + " \n");

			for (int i = 1; i < qInfo.getStatusIDs().length; i++)
			{
				strWhereSQLBuffer.append(" ," + qInfo.getStatusIDs()[i] + " \n");
			}

			strWhereSQLBuffer.append(")");

			isNeedWhere = true;
		}

		if (qInfo.getSumForSearch() != 0.0)
		{
			strWhereSQLBuffer.append(" AND MSUMFORSEARCH = " + qInfo.getSumForSearch() + " \n");
			isNeedWhere = true;
		}

		if (isNeedWhere)
		{
			strSQLBuffer.append(" WHERE");
			//Cut first "AND"
			strSQLBuffer.append(strWhereSQLBuffer.substring(4));
		}

		
		
		return strSQLBuffer.toString();
	}


}
