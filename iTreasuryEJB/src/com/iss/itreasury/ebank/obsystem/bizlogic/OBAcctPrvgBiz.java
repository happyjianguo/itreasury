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
import com.iss.itreasury.ebank.obsystem.dao.OB_AcctPrvgByClientDao;
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
public class OBAcctPrvgBiz {
	
	//�õ��˻�Ȩ����Ϣ
	//�����¼�����Ŀͻ�id
	public Collection getAcctPrvgs(long clientid,long currencyId ) throws Exception
	{
		Vector vret = null;
		try{
			OB_AcctPrvgByClientDao dao = new OB_AcctPrvgByClientDao();
			
			//�õ����е��ϼ���λ��Ϣ
			Vector v = (Vector)dao.getParentClientIDs(clientid);
			//�õ����е��˻���Ϣ
			Vector v1 = (Vector)dao.getAcctIDs(clientid,currencyId);
			//�������
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
							
							//�ж��Ƿ���Ȩ��
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
	
	//	�����˻�Ȩ����Ϣ
	//������AcctPrvgByClientInfo��ɵļ���
	public void saveAcctPrvgs(Collection c) throws Exception
	{
		try{
			OB_AcctPrvgByClientDao dao = new OB_AcctPrvgByClientDao();
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
	/*public void saveAcctPrvgs(AcctPrvgByClientInfo info) throws Exception
	{
		try{
			OB_AcctPrvgByClientDao dao = new OB_AcctPrvgByClientDao();
			
			dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}*/
	//����ǰɾ���˻�Ȩ����Ϣ��
	public void delAcctPrvgs(Collection c) throws Exception
	{
		int i=0;
		try{
			OB_AcctPrvgByClientDao dao = new OB_AcctPrvgByClientDao();
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
	/*public void delAcctPrvgs(long accountId) throws Exception
	{
		try{
			OB_AcctPrvgByClientDao dao = new OB_AcctPrvgByClientDao();
			
			dao.del(accountId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}*/
	
	public static void main (String [] args){
		Collection coll=new Vector();
		
		OBAcctPrvgBiz b=new OBAcctPrvgBiz();
		try{
			AccountInfo info=new AccountInfo();
			coll.add(info);
			b.delAcctPrvgs(coll);
			
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		
	}

}