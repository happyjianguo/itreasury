package com.iss.itreasury.loan.reportelements.bizlogic;

import com.iss.itreasury.loan.reportelements.dao.RepayExtDao;
import com.iss.itreasury.loan.reportelements.dataentity.RepayExtInfo;
import com.iss.itreasury.loan.reportelements.dataentity.RepayExtQueryInfo;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;

/**
 * ������ϸ��¼
 * 
 * 
 * @date 2012��6��19��
 * @author Eric
 *
 */
public class RepayExtBiz {
//	��ҳ��ѯ
	public PageLoader queryConditionRepayExtQueryInfo(RepayExtQueryInfo queryConditionRepayExtQueryInfo) throws Exception
	{
		PageLoader pageLoader = null;
		RepayExtDao repayExtDao = new RepayExtDao();
		pageLoader = repayExtDao.queryRepayExtQueryInfo(queryConditionRepayExtQueryInfo);
		return pageLoader;
	}
	
	/**
	 * �������ݿ�
	 * 
	 * @see com.iss.itreasury.loan.reportelements.dao.RepayExtDao.save(RepayExtInfo repayExtInfo)
	 * 
	 * @param repayExtInfo
	 * @throws Exception
	 */
	public Boolean save(RepayExtInfo repayExtInfo) throws Exception
	{
		Boolean flag = new Boolean(true);
		RepayExtDao repayExtDao = new RepayExtDao();
		long lResult = repayExtDao.save(repayExtInfo);
		System.out.println(lResult);
		if (lResult != 1) {
			Log.print("�������");
			flag = Boolean.FALSE;
		}
		
		return flag;
	}
}
