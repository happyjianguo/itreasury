/*
 * Created on 2005-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.constantmanage.bizlogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.configtool.util.CONFIGConstant;
import com.iss.itreasury.configtool.constantmanage.dao.*;
import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantInfo;
import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantManageInfo;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConstantManageBiz {



	public static void  main(String[] args)
	{
		ConstantManageBiz biz = new ConstantManageBiz();
		Collection c = null;

		System.out.println("***********begin*************");
		try
		{
			/*Collection c = biz.getAllConstant();

			if (c!=null&&c.size()>0)
			{System.out.println("\n size="+c.size());
				Iterator it = c.iterator();

				while (it.hasNext())
				{
					ConstantInfo info = (ConstantInfo)it.next();
					System.out.println("\n name="+info.getName());
				}

			}*/
			String name = Constant.UserGruopLever.class.getName();
			System.out.println("name="+name);

			long[] ll = ConstantManager.getInstance().getAllFields(name);

			for (int i=0;i<ll.length;i++)
			{
				System.out.println("ll="+ll[i]);
				System.out.println("name="+ConstantManager.getNameByFields(name,ll[i]));
			}

			/*ConstantInfo condition = new ConstantInfo();
			condition.setModuleID(0);
			condition.setStatusID(1);

			Collection c = biz.findByCondition(condition);

			if (c!=null&&c.size()>0)
			{System.out.println("\n size="+c.size());
				Iterator it = c.iterator();

				while (it.hasNext())
				{
					ConstantInfo info = (ConstantInfo)it.next();
					System.out.println("\n name="+info.getName());
					System.out.println("\n ModuleName="+info.getModuleName());
					System.out.println("\n StatusID="+info.getStatusID());
					System.out.println("\n Desc="+info.getConstantDesc());
				}

			}*/
			/*
			ConstantManageInfo condition = new ConstantManageInfo();
			condition.setConstantID(1);
			condition.setCurrencyID(1);
			condition.setOfficeID(1);

			c = biz.getConstantManageInfoByCondition(condition);

			System.out.println("************************c="+c);
			if (c != null)
			{
				System.out.println("************************size="+c.size());
				Iterator it = c.iterator();

				while (it.hasNext())
				{
					ConstantManageInfo info = (ConstantManageInfo)it.next();
					System.out.println("\n name="+info.getName());
					System.out.println("\n Check="+info.getCheck());
					System.out.println("\n Value="+info.getValue());
				}
			}*/

		}
		catch(Exception e)
		{
			Log.print(e.toString());
		}

		System.out.println("***********end*************");
	}

	public Collection findByCondition(ConstantInfo condition) throws Exception
	{
		Vector vResult = new Vector();
		Iterator it = null;
		HashMap hm = new HashMap();
		Collection c = null;

		try{

			ConstantDao dao = new ConstantDao();
			vResult = (Vector)dao.getConstantInfoByCondition(condition);

			if (vResult!=null && vResult.size()>0)
			{
				it = vResult.iterator();

				while (it.hasNext())
				{
					ConstantInfo cInfo = (ConstantInfo)it.next();
					hm.put(cInfo.getName(),cInfo);
				}
			}

			if (condition.getStatusID() > CONFIGConstant.ManageStatus.NOTSET)
			{
				;
			}
			else if (condition.getStatusID()==CONFIGConstant.ManageStatus.NOTSET)
			{
				vResult = new Vector();

				c = ConstantManager.getConstantByCondition(condition);

				if (c!=null && c.size()>0)
				{
					it = c.iterator();

					while (it.hasNext())
					{
						ConstantInfo cInfo = (ConstantInfo)it.next();

						if (hm.get(cInfo.getName())==null)
						{
							vResult.addElement(cInfo);
						}
					}
				}
			}
			else if (condition.getConstantDesc()!=null&&condition.getConstantDesc().length()>0)
			{
				;
			}
			else
			{
				vResult = new Vector();

				c = ConstantManager.getConstantByCondition(condition);

				if (c!=null && c.size()>0)
				{
					it = c.iterator();

					while (it.hasNext())
					{
						ConstantInfo cInfo = (ConstantInfo)it.next();

						if (hm.get(cInfo.getName())!=null)
						{
							vResult.addElement(hm.get(cInfo.getName()));
						}
						else
						{
							vResult.addElement(cInfo);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return vResult;
	}

	public long addToManage(ConstantInfo info) throws Exception
	{
		long id = -1;

		try
		{
			if (info==null)
			{
				return id;
			}

			ConstantDao dao = new ConstantDao();

			if (info.getStatusID()==CONFIGConstant.ManageStatus.NOTSET)
			{
				info.setStatusID(CONFIGConstant.ManageStatus.MANAGE);
			}
			else if (info.getStatusID()==CONFIGConstant.ManageStatus.MANAGE)
			{
				info.setStatusID(CONFIGConstant.ManageStatus.NOTMANAGE);
			}
			else if (info.getStatusID()==CONFIGConstant.ManageStatus.NOTMANAGE)
			{
				info.setStatusID(CONFIGConstant.ManageStatus.MANAGE);
			}

			if (info.getId()>0)
			{
				id = info.getId();
				dao.update(info);
			}
			else
			{
				dao.setUseMaxID();
				id = dao.add(info);
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return id;
	}

	public ConstantInfo findConstantByID(long id) throws Exception
	{
		ConstantInfo info = null;
		try
		{
			ConstantDao dao = new ConstantDao();
			info = (ConstantInfo)dao.findByID(id,ConstantInfo.class);
			info.setModuleName(CONFIGConstant.ModuleType.getName(info.getModuleID()));
			info.setStatus(CONFIGConstant.ManageStatus.getName(info.getStatusID()));
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return info;
	}

	public Collection getConstantManageInfoByCondition(ConstantManageInfo condition) throws Exception {

		 Vector v = new  Vector();

		Iterator it = null;
		HashMap hm = new HashMap();

		if (condition.getConstantID()<=0||condition.getOfficeID()<=0||condition.getCurrencyID()<=0)
		{
			return null;
		}

		try
		{
			ConstantManageDao dao = new ConstantManageDao();
			Collection c = dao.getConstantManageInfoByCondition(condition);

			if (c!=null && c.size()>0)
			{
				it = c.iterator();

				while (it.hasNext())
				{
					ConstantManageInfo cInfo = (ConstantManageInfo)it.next();
					hm.put(String.valueOf(cInfo.getValue()),cInfo);
				}
			}

			ConstantInfo info = findConstantByID(condition.getConstantID());
			String name = info.getName();
			long[] fields = ConstantManager.getInstance().getAllFields(name);

			for (int i=0;i<fields.length;i++)
			{
				ConstantManageInfo cmInfo = new ConstantManageInfo();

				cmInfo.setOfficeID(condition.getOfficeID());
				cmInfo.setCurrencyID(condition.getCurrencyID());
				cmInfo.setConstantID(condition.getConstantID());
				cmInfo.setValue(fields[i]);
				cmInfo.setName(ConstantManager.getNameByFields(name,cmInfo.getValue()));

				if (hm.get(String.valueOf(cmInfo.getValue()))!=null)
				{
					cmInfo.setCheck(true);
				}
				else
				{
					cmInfo.setCheck(false);
				}

				if (cmInfo.getName()!=null && cmInfo.getName().length()>0)
				{
					v.addElement(cmInfo);
				}

			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return v.size()>0?v:null;
	}

	public void saveConstantManageInfo(ConstantManageInfo[] condition) throws Exception {

		if (condition == null || condition.length<=0)
		{
			return;
		}

		try
		{
			ConstantManageDao dao = new ConstantManageDao();

			if ( condition[0].getAllOffice() > 0 || condition[0].getAllCurrency() >0 || condition[0].getAllForeignCurrency() > 0)
			{
				dao.saveAllConstantManageInfo(condition);
			}
			else
			{
				if (condition[0]!=null)
				{
					dao.delByCondition(condition[0]);
				}

				for (int i=0;i<condition.length;i++)
				{
					if (condition[i]!=null && condition[i].getValue()!=-9999999)
					{
						dao.add(condition[i]);
					}
				}
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}

}
