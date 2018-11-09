package com.iss.itreasury.loan.bankloan.bizlogic;
/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.loan.bankloan.dao.InterestDetailDao;
import com.iss.itreasury.loan.bankloan.dataentity.InterestDetailInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class InterestDetailBiz {
	
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	//新增，将info里的信息insert到表loan_InterestDetail里
	public long add(InterestDetailInfo info) throws IException
	{
		long lret = -1;
		try
		{
			InterestDetailDao dao = new InterestDetailDao();
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
	
	//	删除，将info里的信息update到表loan_InterestDetail里
	public long delete(String[] id) throws IException
	{
		long lret = -1;
		try
		{
			InterestDetailDao dao = new InterestDetailDao();
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
	
	//	查询，按info里的信息查询表loan_InterestDetail
	//	返回由InterestDetailInfo组成的集合
	public List findAll(long id) throws IException
	{
		List vret = null;
		try
		{
			InterestDetailDao dao = new InterestDetailDao();
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
