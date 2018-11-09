/**
 * @author XiaoQiao
 * @Date   2012-6-14
 */
package com.iss.itreasury.loan.reportelements.bizlogic;


import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.reportelements.dao.ContractExtDao;
import com.iss.itreasury.loan.reportelements.dataentity.ContractExtQueryInfo;
import com.iss.system.dao.PageLoader;

public class ContractExtBiz {
	
	ContractExtDao cDao = new ContractExtDao();
	boolean flag = false;

	public boolean update(ContractExtQueryInfo cExtInfo){
		try {
			flag = cDao.update(cExtInfo);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public PageLoader getContractExtInfo(long status,String startContract,String endContract,String orderType,String orderField){
		return cDao.getContractExtInfo(status, startContract, endContract,orderType,orderField);
	}
	
	public PageLoader getContractExtInfoById(long id){
		return cDao.getContractExtInfoById(id);
	}
}
