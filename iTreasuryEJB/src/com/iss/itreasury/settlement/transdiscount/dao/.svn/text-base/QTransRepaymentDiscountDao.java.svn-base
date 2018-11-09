package com.iss.itreasury.settlement.transdiscount.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;

/**
 * 账户查询数据层
 * 
 * @author xiang
 * 
 */
public class QTransRepaymentDiscountDao {

	public String queryLoanSQL(QueryConditionInfo info) {
		long nOfficeID = info.getOfficeID();
		long nCurrencyID = info.getCurrencyID();
		long nUserID = info.getUserID();
		long nTypeID = info.getTypeID();
		long nStatusID = info.getStatusID();
		Timestamp date = info.getDate();

		StringBuffer buffer = new StringBuffer("");
		buffer.append(" select * ");
		buffer.append(" from Sett_TransRepaymentDiscount \n");
		buffer.append(" where ID is not null ");

		// 日期特别处理
		if (nStatusID != SETTConstant.TransactionStatus.TEMPSAVE) {
			buffer.append(" and dtExecute=to_date('"
					+ DataFormat.getDateString(date) + "','yyyy-mm-dd')");
		}
		buffer.append(" and nOfficeID=" + nOfficeID);
		buffer.append(" and nCurrencyID=" + nCurrencyID);

		// 根据查询类型处理用户ID
		if (nTypeID == 0) {
			buffer.append(" and nInputUserID=" + nUserID);
			buffer.append(" and nCheckUserID!=" + nUserID);
		} else if (nTypeID == 1) {
			buffer.append(" and nCheckUserID=" + nUserID);
			buffer.append(" and nInputUserID!=" + nUserID);
		}

		buffer.append(" and nTransactionTypeID="
				+ SETTConstant.TransactionType.DISCOUNTRECEIVE + " ");

		if (nTypeID == 0) {
			if (nStatusID > 0)
				buffer.append(" and nStatusID=" + nStatusID);
			else
				buffer.append(" and nStatusID in("
						+ SETTConstant.TransactionStatus.TEMPSAVE + ","
						+ SETTConstant.TransactionStatus.SAVE + ") ");
		} else {
			buffer.append(" and nStatusID="
					+ SETTConstant.TransactionStatus.CHECK);
		}
		Log.print(buffer.toString());
		return buffer.toString();
	}

}
