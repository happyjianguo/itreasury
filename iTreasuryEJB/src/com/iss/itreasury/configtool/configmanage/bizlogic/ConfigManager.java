/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.configmanage.bizlogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.iss.itreasury.configtool.configmanage.dataentity.*;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.IException;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConfigManager {
	private static ConfigManager m_instance = null;
	private String[] fileNames = null;
	private static HashMap result = new HashMap();

	private ConfigManager() {}

	public static ConfigManager getInstance() {
		if (m_instance == null) {
			m_instance = new ConfigManager();
		}

		return m_instance;
	}

	/**
	 * 得到itreasury系统的模块列表
	 * 
	 * @param
	 * @throws Exception
	 * @return Collection
	 */
	public HashMap getAllItem() throws Exception {
		if(result.size()==0)
		{
			ConfigManageBiz biz = new ConfigManageBiz();
			Collection c = null;
			
			c = biz.findModuleList();
			
			if (c != null && c.size() > 0) {
				Iterator it = c.iterator();
				
				fileNames = new String[c.size()];
	
				for (int n=0;it.hasNext();n++) {
					ConfigItemInfo cInfo = (ConfigItemInfo) it.next();
	
					fileNames[n] = cInfo.getDesc();
				}
			}
	
			try {
				for (int i = 0; i < fileNames.length; i++) {
					if (fileNames[i] != null && fileNames[i].length() > 0) {
	
						c = biz.findConfigListByFileName(fileNames[i]);
	
						if (c != null && c.size() > 0) {
							Iterator it = c.iterator();
	
							while (it.hasNext()) {
								ConfigItemInfo cInfo = (ConfigItemInfo) it.next();
	
								result.put(cInfo.getNamemap(), cInfo.getVale());
							}
						}
					}
				}
				
			} catch (IException ie) {
				throw ie;
			} catch (Exception e) {
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return result;
	}
}