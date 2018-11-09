/**
 * create on 2010-11-4
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.ebank.obsystem.dataentity.AcctPrvgByClientInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BankUserDistributeDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_InnerUserDistributDAO;
import com.iss.itreasury.settlement.setting.dataentity.UserDistributeQueryInfo;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

/** 
 * @author zyzhu
 * 
 */
public class SettUserDistributeBiz {

	// 成员变量
	private Connection conn = null; // 数据库连接

	// 构造函数
	public SettUserDistributeBiz() {
		try {
			conn = Database.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PageLoader queryUserDistribute(UserDistributeQueryInfo queryInfo) throws Exception
	{
		PageLoader pageLoader = null;
		Sett_InnerUserDistributDAO dao = new Sett_InnerUserDistributDAO();
		pageLoader = dao.queryUserDistribute(queryInfo);
		return pageLoader;
	
	}
	
	public String queryClientCodeByID(long id)throws Exception
	{
		Sett_InnerUserDistributDAO dao = new Sett_InnerUserDistributDAO();
		String clientCode = "";
		clientCode = dao.queryClientCodeByID(id);
		return clientCode;
	}
	public void deleteAuthority(Collection c)throws Exception
	{
		try{
			Sett_InnerUserDistributDAO dao = new Sett_InnerUserDistributDAO();
			Iterator it=c.iterator();
			while(it.hasNext())
			{
				AcctPrvgByClientInfo acctPrvgByClientInfo=new AcctPrvgByClientInfo();
				acctPrvgByClientInfo=(AcctPrvgByClientInfo)it.next();
				dao.deleteAuthority(acctPrvgByClientInfo);
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public PageLoader queryBankUserDistribute(UserDistributeQueryInfo queryInfo) throws Exception
	{
		PageLoader pageLoader = null;
		Sett_BankUserDistributeDAO dao = new Sett_BankUserDistributeDAO();
		pageLoader = dao.queryBankUserDistribute(queryInfo);
		return pageLoader;
	
	}
	
	public void deleteBankAuthority(Collection c)throws Exception
	{
		try{
			Sett_BankUserDistributeDAO dao = new Sett_BankUserDistributeDAO();
			Iterator it=c.iterator();
			while(it.hasNext())
			{
				AcctPrvgByClientInfo acctPrvgByClientInfo=new AcctPrvgByClientInfo();
				acctPrvgByClientInfo=(AcctPrvgByClientInfo)it.next();
				dao.deleteAuthority(acctPrvgByClientInfo);
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public String queryBankClientCodeByID(long id)throws Exception
	{
		Sett_BankUserDistributeDAO dao = new Sett_BankUserDistributeDAO();
		String clientCode = "";
		clientCode = dao.queryClientCodeByID(id);
		return clientCode;
	}
	
	
	
	
}
