/*
 * Created on 2006-10-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obnotifydepositinform.bizlogic;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_NotifyDepositInformDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

/**
 * @author caryzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBNotifyDepositInformBiz {
	
//	新增通知信息，将info里的信息insert到表sett_notifyDepositInform里
	public long add(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	//修改通知信息，将info里的信息update到表sett_notifyDepositInform里
	public long modify(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			lret = dao.modify(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//查询通知信息，按info里的信息查询表
	//sett_notifyDepositInform,sett_account,sett_transopenfixeddeposit,client
	//info里只需传入模块标识和办事处标识
	//返回由NotifyDepositInformInfo组成的集合
	public Collection findByCondition(NotifyDepositInformInfo info) throws Exception
	{
		Vector vret = null;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			vret = dao.findByCondition(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
//	查询通知信息，按id查询表sett_notifyDepositInform
	//返回NotifyDepositInformInfo
	public NotifyDepositInformInfo findByID(long id) throws Exception
	{
		NotifyDepositInformInfo info = null;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			info = dao.findByID(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	//	取消通知，循环调用dao的modify方法
	public long cancel(long[] id) throws Exception
	{
		long lret = -1;
		if (id==null)
		{
			return lret;
		}
		
		if (id.length<=0)
		{
			return lret;
		}
		
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			NotifyDepositInformInfo info = new NotifyDepositInformInfo();
			
			for(int i=0;i<id.length;i++)
			{
				info.setID(id[i]);
				info.setStatusID(SETTConstant.NotifyInformStatus.CANCEL);//这里将状态置为 取消
				lret = dao.modify(info);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	//分页链接查找
	public PageLoader findWithPage (NotifyDepositInformInfo info) throws Exception
	{
		PageLoader loader = null;
		
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}

}