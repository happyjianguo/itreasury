package com.iss.itreasury.report.delegation;

import com.iss.itreasury.report.bizlogic.LoanInterestReportBiz;

public class LoanInterestReportDelegetion {
	/**
	 * ���ɱ�����Ϣ��Ϣ����������
	 * @author yunzhou
	 * @date 2011-01-17
	 * @return
	 * @throws Exception
	 */
	public boolean CreateLoanInterestReportData() throws Exception{
		LoanInterestReportBiz biz = new LoanInterestReportBiz();
		return biz.CreateLoanInterestReportData();
	}
}
