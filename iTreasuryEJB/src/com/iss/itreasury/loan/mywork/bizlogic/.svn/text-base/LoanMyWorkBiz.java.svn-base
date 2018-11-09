/*
 * Created on 2007-06-21
 *
 * Title:				iTreasury
 * @author             	付明正 
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在信贷新增审批流,该功能实现查找我的任务
 */
package com.iss.itreasury.loan.mywork.bizlogic;

import com.iss.itreasury.loan.aftercredit.dao.AfterCreditDao;
import com.iss.itreasury.loan.extendapply.dao.ExtendApplyDao;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.mywork.dao.LoanContractRateChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanContractRisklevelChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanContractStatusChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanContractPlanChangeDao;
import com.iss.itreasury.loan.mywork.dao.LoanCreditDao;
import com.iss.itreasury.loan.mywork.dao.LoanTransActionDao;
import com.iss.itreasury.loan.mywork.dao.LoanMyWorkDao;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;

public class LoanMyWorkBiz implements java.io.Serializable {

	public Object queryLoanTransActionWork(LoanMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		LoanMyWorkDao dao = new LoanTransActionDao();
		objReturn = dao.queryMyWork(qInfo);	

		return objReturn;
	}
	public Object queryLoanContractStatusChangeWork(LoanMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		LoanMyWorkDao dao = new LoanContractStatusChangeDao();
		objReturn = dao.queryMyWork(qInfo);
		
		return objReturn;
	}
	
	public Object queryLoanContractRisklevelChangeWork(LoanMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		LoanMyWorkDao dao = new LoanContractRisklevelChangeDao();
		objReturn = dao.queryMyWork(qInfo);
		
		return objReturn;
	}
	
	public Object queryLoanContractPlanChangeWork(LoanMyWorkInfo qInfo) throws Exception {
		 Object objReturn = null;
		LoanMyWorkDao dao = new LoanContractPlanChangeDao();
		objReturn = dao.queryMyWork(qInfo);

		return objReturn;
	}
	
	public Object queryLoanContractRateChangeWork(LoanMyWorkInfo qInfo) throws Exception {
		 Object objReturn = null;
		LoanMyWorkDao dao = new LoanContractRateChangeDao();
		objReturn = dao.queryMyWork(qInfo);

		return objReturn;
	}
		
	public long findYTDrawNoticeIdByPayNoticeId(long payNoticeId) throws Exception {
		 long lReturn = -1;

		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		lReturn = dao.findYTDrawNoticeIdByPayNoticeId(payNoticeId);
			
		return lReturn;
	}
	public long getExtendApplyIdByExtendContractId(long lExtendContractID) throws Exception {
		return new ExtendApplyDao().getExtendApplyIdByExtendContractId(lExtendContractID);
	}

	public Object queryLoanCredit(LoanMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		LoanMyWorkDao dao = new LoanCreditDao();
		objReturn = dao.queryMyWork(qInfo);
		
		return objReturn;
	}
	public Object queryAfterCreditWork(LoanMyWorkInfo qInfo) throws Exception {
		 Object objReturn = null;
		 AfterCreditDao dao = new AfterCreditDao();
		objReturn = dao.queryMyWork(qInfo);

		return objReturn;
	}
}
