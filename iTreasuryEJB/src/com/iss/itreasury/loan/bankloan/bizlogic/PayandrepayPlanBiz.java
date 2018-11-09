package com.iss.itreasury.loan.bankloan.bizlogic;
/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.loan.bankloan.dao.PayandrepayPlanDao;
import com.iss.itreasury.loan.bankloan.dataentity.PayandrepayPlanInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class PayandrepayPlanBiz{
	
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	//��������info�����Ϣinsert����loan_PayandrepayPlan��
	public long add(PayandrepayPlanInfo info) throws IException
	{
		long lret = -1;
		try
		{
			PayandrepayPlanDao dao = new PayandrepayPlanDao();
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
	
	//	�޸ģ���info�����Ϣupdate����loan_PayandrepayPlan��
	public long modify(PayandrepayPlanInfo info) throws IException
	{
		long lret = -1;
		try
		{
			PayandrepayPlanDao dao = new PayandrepayPlanDao();
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
	
	//	ɾ������info�����Ϣupdate����loan_PayandrepayPlan��
	public long delete(String[] id) throws IException
	{
		long lret = -1;
		try
		{
			PayandrepayPlanDao dao = new PayandrepayPlanDao();
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
	
	//	��ѯ����info�����Ϣ��ѯ��loan_PayandrepayPlan
	//	������PayandrepayPlanInfo��ɵļ���
	public PayandrepayPlanInfo findById(long id) throws IException
	{
		PayandrepayPlanInfo vret = null;
		try
		{
			PayandrepayPlanDao dao = new PayandrepayPlanDao();
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
			PayandrepayPlanDao dao = new PayandrepayPlanDao();
			cret = dao.findAll(id);
		}catch(Exception e){
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return cret;
	}
	
}
