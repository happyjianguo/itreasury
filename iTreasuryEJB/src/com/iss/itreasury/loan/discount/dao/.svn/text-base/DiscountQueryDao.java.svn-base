package com.iss.itreasury.loan.discount.dao;

import com.iss.itreasury.util.Constant;

public class DiscountQueryDao {

	public String findDiscountBillByDiscountID(long loanID) {
		String strSQL = "select * from Loan_DiscountFormBill where nStatusID="
			+ Constant.RecordStatus.VALID + " and nLoanID="
			+ loanID;
		return strSQL;
	}

}
