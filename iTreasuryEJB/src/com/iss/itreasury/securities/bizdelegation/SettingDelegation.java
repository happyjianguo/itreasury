/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-15
 */
package com.iss.itreasury.securities.bizdelegation;
import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.iss.itreasury.securities.setting.bizlogic.Setting;
import com.iss.itreasury.securities.setting.bizlogic.SettingHome;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SettingDelegation {
	private Setting settingFacade = null;
	public SettingDelegation(boolean isNeedInitEJB) throws RemoteException {
		if (isNeedInitEJB) {
			SettingHome home;
			try {
				try {
					home = (SettingHome) EJBHomeFactory.getFactory()
							.lookUpHome(SettingHome.class);
				} catch (IException e) {
					throw new RemoteException("EJBHomeFactory连接错误", e);
				}
				settingFacade = (Setting) home.create();
			} catch (CreateException ce) {
				throw new RemoteException("发生CreateException", ce);
			}
		}
	}
	//add your methods that need be delegated at here.....
	
}
