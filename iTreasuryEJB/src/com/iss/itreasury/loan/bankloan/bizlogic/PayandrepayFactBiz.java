package com.iss.itreasury.loan.bankloan.bizlogic;
/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.loan.bankloan.dao.PayandrepayFactDao;
import com.iss.itreasury.loan.bankloan.dataentity.PayandrepayFactInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class PayandrepayFactBiz {
	
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	//新增，将info里的信息insert到表loan_BankLoan里
	public long add(PayandrepayFactInfo info) throws IException
	{
		long lret = -1;
		try
		{
			PayandrepayFactDao dao = new PayandrepayFactDao();
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
	
	//	修改，将info里的信息update到表loan_BankLoan里
	public long modify(PayandrepayFactInfo info) throws IException
	{
		long lret = -1;
		try
		{
			PayandrepayFactDao dao = new PayandrepayFactDao();
			lret = dao.modify(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	删除，将info里的信息update到表loan_BankLoan里
	public long delete(String[] id) throws IException
	{
		long lret = -1;
		try
		{
			PayandrepayFactDao dao = new PayandrepayFactDao();
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
	
	//	查询，按info里的信息查询表loan_BankLoan
	//	返回由PayandrepayFactInfo组成的集合
	public PayandrepayFactInfo findById(long id) throws IException
	{
		PayandrepayFactInfo vret = null;
		try
		{
			PayandrepayFactDao dao = new PayandrepayFactDao();
			vret = dao.findById(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	public List findAll(long id) throws IException{
		List cret = null;
		try{
			PayandrepayFactDao dao = new PayandrepayFactDao();
			cret = dao.findAll(id);
		}catch(Exception e){
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return cret;
	}
	
}
