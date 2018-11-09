/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.configmanage.bizlogic;

import java.io.FileInputStream;
import java.util.Vector;
import java.util.Collection;
import java.util.HashMap;

import org.w3c.dom.Document;

import com.iss.itreasury.configtool.configmanage.dataentity.*;
import com.iss.itreasury.configtool.util.Helper;
import com.iss.itreasury.configtool.configmanage.xmlmsg.*;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Env;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConfigManageBiz {
	private String fileseparator = "/";
	private String configFilePath = ".config";
		
	public ConfigManageBiz()
	{
		if (Env.getCPF_OS().equalsIgnoreCase("windows"))
		{
			fileseparator = "\\";
		}
		else 
		{
			fileseparator = "/";
		}
		configFilePath = ".config"+fileseparator;	
	}
	/**
	 * 得到itreasury系统的模块列表
	 * @param 
	 * @throws Exception
	 * @return Collection
	 */
	public Collection findModuleList() throws Exception
	{
		Collection result = null;
		try
		{
			result = findConfigListByFileName("itreasury.xml");
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		
		return result.size()>0?result:null;
	}
	
	/**
	 * 根据模块查询配置项列表
	 * @param configFileName
	 * @throws Exception
	 * @return Collection 
	 */
	public Collection findConfigListByFileName(String configFileName) throws Exception
	{
		Vector result = new Vector();
		try
		{
			FileInputStream input = Helper.loadConfig(configFilePath+configFileName);
			
			Document node = Helper.parse(input);
			
			ConfigItemDetail info = new ConfigItemDetail();
			
			info.marshal(node);
			
			if (info != null)
			{
				int count = info.getResultSetSize(); //得到正常余额信息的数量
				
				if (count > 0) //如果实际接受到的结果总数大于0
				{
					for (int i = 0; i < count; i++)
					{
						if (info.getResult(i) != null)
						{							
							ConfigItemInfo cInfo = (ConfigItemInfo) info.getResult(i);	
							result.addElement(cInfo);
						}
					}
				}
			}		
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		
		return result.size()>0?result:null;
	}
	
	/**
	 * 修改配置项
	 * @param configFileName
	 * @throws Exception
	 * @return Collection
	 */
	public void updateConfigItem(String configFileName,ConfigItemInfo cInfo) throws Exception
	{		
		Vector result = new Vector();
		try
		{
			FileInputStream input = Helper.loadConfig(configFilePath+configFileName);
			
			Document node = Helper.parse(input);
			
			ConfigItemDetail info = new ConfigItemDetail();
			
			info.modify(node,cInfo);
			
			Helper.outputToFile((configFilePath+configFileName),node);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 新增配置项
	 * @param configFileName
	 * @throws Exception
	 * @return Collection
	 */
	public void addConfigItem(String configFileName,ConfigItemInfo cInfo) throws Exception
	{
		Vector result = new Vector();
		try
		{
			ConfigManager cong = ConfigManager.getInstance();
			HashMap  allItems = cong.getAllItem();
			
			Object o = allItems.get(cInfo.getNamemap());
		
			if (o != null)  
			{
				throw new IException("Config_E001");
			}
			
			FileInputStream input = Helper.loadConfig(configFilePath+configFileName);
			
			Document node = Helper.parse(input);
			
			ConfigItemDetail info = new ConfigItemDetail();
			
			info.append(node,cInfo);	
			
			Helper.outputToFile((configFilePath+configFileName),node);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 删除配置项
	 * @param configFileName
	 * @throws Exception
	 * @return Collection
	 */
	public void delConfigItem(String configFileName,ConfigItemInfo cInfo) throws Exception
	{
		Vector result = new Vector();
		try
		{
			FileInputStream input = Helper.loadConfig(configFilePath+configFileName);
			
			Document node = Helper.parse(input);
			
			ConfigItemDetail info = new ConfigItemDetail();
			
			info.delete(node,cInfo);	

			Helper.outputToFile((configFilePath+configFileName),node);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	public static void main(String args[])
	{
		try
		{	Log.print("***************begin***************");
			int i = 0;
			ConfigManageBiz biz = new ConfigManageBiz();  
			/*Collection c =biz.findModuleList();
			
			if (c != null && c.size()>0)
			{
				Iterator it = c.iterator();
				
				while (it.hasNext())
				{
					ConfigItemInfo info = (ConfigItemInfo)it.next();
					System.out.println("*******************"+i+++"******************");
					System.out.println("getName="+info.getName());
					System.out.println("getNamemap="+info.getNamemap());
					System.out.println("getType="+info.getType());
					System.out.println("getDesc="+info.getDesc());
					System.out.println("getVale="+info.getVale());
					
				}
			}*/ 

			ConfigItemInfo info = new ConfigItemInfo();
			info.setName("1");
			info.setType("integer");
			info.setNamemap("1");
			info.setDesc("1");
			info.setVale("1");
			biz.addConfigItem("global.xml",info);
			Log.print("\n***************end***************"+biz.configFilePath);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
