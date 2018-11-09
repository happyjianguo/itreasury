package com.iss.itreasury.loan.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractQueryInfo;
import com.iss.itreasury.loan.loanapply.dao.LoanApplyDao;
import com.iss.itreasury.loan.loanapply.dataentity.LoanQueryInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.util.IException;

public class LoanWaitOperationDelegation {

	public Collection queryLoanApply(LoanQueryInfo loanQueryInfo)
			throws IException {
		try {
			return new LoanApplyDao().queryWaitOperation(loanQueryInfo);
		} catch (Exception e) {
			throw new IException("查询待办任务(申请)，出现异常", e);
		}
	}

	public Collection queryLoanContract(ContractQueryInfo contractQueryInfo) throws IException {
		try {
			return new ContractDao().queryWaitOperation(contractQueryInfo);
		} catch (Exception e) {
			throw new IException("查询待办任务(合同)，出现异常", e);
		}
	}
	
	public Collection queryLoanPayNotice(LoanPayNoticeInfo loanPayNoticeInfo) throws IException {
		try {
			return new LoanPayNoticeDao().queryWaitOperation(loanPayNoticeInfo);
		} catch (Exception e) {
			throw new IException("查询待办任务(放款通知单)，出现异常", e);
		}
	}
}
