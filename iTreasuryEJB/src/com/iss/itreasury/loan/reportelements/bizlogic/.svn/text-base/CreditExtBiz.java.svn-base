/**
 * @author XiaoQiao
 * @Date   2012-6-19
 */
package com.iss.itreasury.loan.reportelements.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.reportelements.dao.CreditExtDao;
import com.iss.itreasury.loan.reportelements.dataentity.CreditExtQueryInfo;
import com.iss.system.dao.PageLoader;

public class CreditExtBiz {
	
	CreditExtDao cDao = new CreditExtDao();
	boolean flag = false;
	
	public boolean update(CreditExtQueryInfo cInfo){
		try {
			flag = cDao.update(cInfo);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public PageLoader getCreditExtInfo(long status,String startId,String endId,String orderType,String orderField){
		return cDao.getCreditExtInfo(status, startId, endId,orderType,orderField);
	}
	
	public PageLoader getCreditExtInfoById(long id){
		return cDao.getCreditExtInfoById(id);
	}
}
