/*
 * Created on 2006-11-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obsystem.bizlogic;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import com.iss.itreasury.ebank.obsystem.dao.OB_AcctPrvgByClientDaoBankAcount;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.ebank.obsystem.dataentity.AcctPrvgByClientInfo;
import com.iss.itreasury.ebank.obsystem.dataentity.ParentClientInfo;
import com.iss.itreasury.ebank.obsystem.dataentity.AccountInfo;
/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBAcctPrvgBizBankAcount {
	
	//得到账户权限信息
	//传入登录网银的客户id
	public Collection getAcctPrvgs(long clientid,long currencyid) throws Exception
	{
		Vector vret = null;
		try{
			OB_AcctPrvgByClientDaoBankAcount dao = new OB_AcctPrvgByClientDaoBankAcount();
			
			//得到所有的上级单位信息
			Vector v = (Vector)dao.getParentClientIDs(clientid);
			//得到所有的账户信息
			Vector v1 = (Vector)dao.getAcctIDs(clientid,currencyid);
			//定义变量
			long havePrvg = 2;
			
			if (v != null)
			{
				Iterator it = v.iterator();
				vret = new Vector();
				
				while (it.hasNext())
				{
					ParentClientInfo cInfo = (ParentClientInfo)it.next();
					
					if (v1!= null)
					{
						Iterator it1 = v1.iterator();
						while (it1.hasNext())
						{
							AccountInfo aInfo = (AccountInfo)it1.next();
							
							AcctPrvgByClientInfo info = new AcctPrvgByClientInfo();
							
							info.setAccountID(aInfo.getAcctid());
							info.setAccountNo(aInfo.getAcctNo());
							info.setClientID(cInfo.getClientid());
							info.setClientName(cInfo.getClientName());
							
							//判断是否有权限
							havePrvg = dao.getAcctPrvgs(info);
							info.setHavePrvg(havePrvg);
							
							vret.add(info);
						}
					}					
				}
			}			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	//	保存账户权限信息
	//传入由AcctPrvgByClientInfo组成的集合
	public void saveAcctPrvgs(Collection c) throws Exception
	{
		try{
			OB_AcctPrvgByClientDaoBankAcount dao = new OB_AcctPrvgByClientDaoBankAcount();
			Iterator it=c.iterator();
			while(it.hasNext()){
				dao.add((AcctPrvgByClientInfo)it.next());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}

	//保存前删除账户权限信息！
	public void delAcctPrvgs(Collection c) throws Exception
	{
		int i=0;
		try{
			OB_AcctPrvgByClientDaoBankAcount dao = new OB_AcctPrvgByClientDaoBankAcount();
			Iterator it=c.iterator();
			while(it.hasNext()){
				AccountInfo info=new AccountInfo();
				info=(AccountInfo)it.next();
				dao.del(info.getAcctid());
				System.out.print(i++);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}



}
