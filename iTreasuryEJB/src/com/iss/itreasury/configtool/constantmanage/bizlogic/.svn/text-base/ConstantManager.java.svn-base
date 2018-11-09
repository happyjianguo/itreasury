/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.constantmanage.bizlogic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;

import com.iss.itreasury.configtool.constantmanage.dao.ConstantDao;
import com.iss.itreasury.configtool.constantmanage.dao.ConstantManageDao;
import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantInfo;
import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantManageInfo;
import com.iss.itreasury.configtool.util.CONFIGConstant;
import com.iss.itreasury.util.AbstractConstantManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConstantManager extends AbstractConstantManager{

	private static ConstantManager m_instance = new ConstantManager();
	  
	public static ConstantManager getInstance()
	{
		return m_instance;
	}
	private static String[] constants = {"com.iss.itreasury.util.Constant", "com.iss.itreasury.settlement.util.SETTConstant",
			"com.iss.itreasury.loan.util.LOANConstant", "com.iss.itreasury.ebank.util.OBConstant",
			"com.iss.itreasury.bill.util.BILLConstant", "com.iss.itreasury.securities.util.SECConstant",
			"com.iss.itreasury.system.util.SYSConstant", "com.iss.itreasury.treasuryplan.util.TPConstant",
			"com.iss.itreasury.craftbrother.util.CRAconstant","com.iss.itreasury.creditrating.util.CreRtConstant",
			"com.iss.itreasury.evoucher.util.VOUConstant"};

	public static Collection getAllConstant() throws Exception {
		Vector vResult = new Vector();
		boolean bIsGet = false;
		int m = 0;

		try {

			for (int i = 0; i < constants.length; i++) {
	
				//得到一个常量类
				Class constantClass = Class.forName(constants[i]);

				//取得此常量类的所有的内部类
				Class innerClasses[] = constantClass.getDeclaredClasses();

				for (int n = 0; n < innerClasses.length; n++) {

					//取得一个内部类
					Class innerClass = Class.forName(innerClasses[n].getName());

					bIsGet = false;
					//取得此内部类的所有方法
					Method methods[] = innerClass.getDeclaredMethods();

					for (m = 0; m < methods.length; m++) {
						//如果此常量类有getAllCode方法则加入管理
						if (methods[m].getName().equalsIgnoreCase("getAllCode")) {
							if (methods[m].getParameterTypes().length >0) bIsGet = true;
						}
					}

					if (bIsGet) {
						ConstantInfo info = new ConstantInfo();
						info.setName(innerClass.getName());
						vResult.addElement(info);
					}
				}
			}

		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return vResult.size() > 0 ? vResult : null;
	}
	
	public long[] getFieldsByClassName(String name,long officeID,long currencyID) {
		
		long[] ll = null;
		int i = 0;
		
		try
		{
			ConstantDao dao = new ConstantDao();
			ConstantInfo info = (ConstantInfo)dao.findByName(name);
			
			if (info!=null)
			{
				if (info.getStatusID()==CONFIGConstant.ManageStatus.MANAGE)
				{
					ConstantManageDao cmDao = new ConstantManageDao();
					Collection c = cmDao.findByConstantID(info.getId(),officeID,currencyID);
					
					if (c!=null && c.size()>0)
					{
						ll = new long[c.size()];
						
						Iterator it = c.iterator();
						
						while(it.hasNext())
						{
							ConstantManageInfo cInfo = (ConstantManageInfo)it.next();
							ll[i] = cInfo.getValue();
							i++;
						}
					}
				}
				else
				{
					ll = getAllFields(name);
				}
			}
			else
			{
				ll = getAllFields(name);
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
		}
		
		return ll;
	}
	
	//Boxu update 2007-11-26 查询返回集合
	public long[] getFieldsByClassNames(String name, long officeID, long currencyID) 
	{
		long[] ll = null;
		int i = 0;
		Collection coll = null;
		
		try
		{
			ConstantDao dao = new ConstantDao();
			coll = (Collection)dao.findByConstantNameID(name, officeID, currencyID);
			
			if (coll!=null && coll.size()>0)
			{
				ll = new long[coll.size()];
				
				Iterator it = coll.iterator();
				
				while(it.hasNext())
				{
					ConstantManageInfo info = (ConstantManageInfo)it.next();
					
					ll[i] = info.getValue();
					i++;
				}
			}
			else
			{
				ll = getAllFields(name);
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
		}
		
		return ll;
	}
	
	public static String getNameByFields(String obj,long field) {
		String name = null;
		long tmp = -1;
		try {
			Class cc = Class.forName(obj);
			//取得此类的所有方法
			Method methods[] = cc.getDeclaredMethods();
			
			Object param[]=new Object[1];
			param[0]=new Long(field);
			
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().equalsIgnoreCase("getName")) {
					
					name =(String)methods[i].invoke(cc,param);
				}
			}

		} catch (Exception e) {
			Log.print(e.toString());
		}

		return name;
	}
	
	public static String getModuleNameByName(String constantName) throws Exception {
		String tmp = "";
		String moduleName = "";

		try {
			int index = constantName.indexOf("itreasury.");
			tmp = constantName.substring(index + 10, constantName.length());
			index = tmp.indexOf(".");
			moduleName = tmp.substring(0, index);
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return moduleName;
	}

	public static Collection getConstantByCondition(ConstantInfo condition) throws Exception {
		Vector vResult = new Vector();
		String tmp = "";
		String constantName = "";
		String moduleName = "";

		if (condition.getStatusID() > CONFIGConstant.ManageStatus.NOTSET
				|| (condition.getConstantDesc() != null && condition.getConstantDesc().length() > 0)) {
			return null;
		}

		try {
			Vector v = (Vector) getAllConstant();

			if (v != null && v.size() > 0) {
				Iterator it = v.iterator();

				while (it.hasNext()) {
					ConstantInfo info = (ConstantInfo) it.next();

					moduleName = getModuleNameByName(info.getName());
					info.setModuleName(CONFIGConstant.ModuleType.getNameByModuleName(moduleName));
					info.setModuleID(CONFIGConstant.ModuleType.getModuleID(moduleName));
					info.setStatusID(CONFIGConstant.ManageStatus.NOTSET);
					info.setStatus(CONFIGConstant.ManageStatus.getName(info.getStatusID()));

					if (condition.getModuleID() >= 0) {

						if (moduleName.equalsIgnoreCase(CONFIGConstant.ModuleType.getModuleName(condition.getModuleID()))) 
						{
							vResult.addElement(info);
						}
					} else {
						vResult.addElement(info);
					}
				}
			}

		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return vResult.size() > 0 ? vResult : null;
	}

	public static void main(String[] args) {
		try {
			ConstantInfo info = new ConstantInfo();
			info.setModuleID(1);
			ConstantManager.getConstantByCondition(info);
		} catch (Exception e) {
			Log.print(e.toString());
		}
	}

}