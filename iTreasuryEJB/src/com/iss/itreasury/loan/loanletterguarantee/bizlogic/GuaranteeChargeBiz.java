package com.iss.itreasury.loan.loanletterguarantee.bizlogic;

import java.util.Collection;

import com.iss.itreasury.loan.loanletterguarantee.dao.GuaranteeChargeDao;
import com.iss.itreasury.loan.loanletterguarantee.dataentity.GuaranteeChargeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class GuaranteeChargeBiz {
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	//新增
	public long add(GuaranteeChargeInfo info) throws IException
	{
		long lret = -1;
		try
		{
			GuaranteeChargeDao dao = new GuaranteeChargeDao();
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	删除
	public long delete(String[] id) throws IException
	{
		long lret = -1;
		try
		{
			GuaranteeChargeDao dao = new GuaranteeChargeDao();
			lret = dao.delete(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	查询
	//	返回由GuaranteeChargeInfo组成的集合
	public Collection findAll(long id) throws IException
	{
		Collection vret = null;
		try
		{
			GuaranteeChargeDao dao = new GuaranteeChargeDao();
			vret = dao.findAll(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return vret;
	}

}
