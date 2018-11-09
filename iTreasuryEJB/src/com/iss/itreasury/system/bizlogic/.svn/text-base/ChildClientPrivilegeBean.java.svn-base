/*
 * Created on 2004-11-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.bizlogic;

import java.util.Vector;
import java.util.Iterator;

import com.iss.itreasury.system.dao.ChildClientPrivilegeDao;
import com.iss.itreasury.system.dataentity.ChildClientPrivilegeInfo;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Constant;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChildClientPrivilegeBean {
	public Vector getChildClientPrivilege(long lClientID) throws Exception {
		Vector vResult = null;
		try {
			ChildClientPrivilegeDao dao = new ChildClientPrivilegeDao();
			vResult = dao.getChildClientPrivilege(lClientID);
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		return vResult;
	}

	public Vector getChildClientPrivilege1(long lClientID) throws Exception {
		Vector vResult = null;
		try {
			ChildClientPrivilegeDao dao = new ChildClientPrivilegeDao();
			Vector vTemp = dao.getChildClientPrivilege(lClientID);
			
			if (vTemp!=null && vTemp.size()>0)
			{
				vResult = new Vector();
				Iterator it = vTemp.iterator();
				while (it.hasNext())
				{
					ChildClientPrivilegeInfo tempInfo = new ChildClientPrivilegeInfo();
					tempInfo = (ChildClientPrivilegeInfo)it.next();
					if (tempInfo.getPrivilege1Value()==Constant.YesOrNo.YES)
					{
						vResult.addElement(tempInfo);
					}
				}	
			}
		
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		return vResult;
	}

	public void saveChildClientPrivilege(ChildClientPrivilegeInfo[] infos) throws Exception {
		try {
			ChildClientPrivilegeDao dao = new ChildClientPrivilegeDao();
			dao.saveChildClientPrivilege(infos);
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}

	public void saveChildClient(ChildClientPrivilegeInfo[] infos) throws Exception, IException {
		try {
			ChildClientPrivilegeDao dao = new ChildClientPrivilegeDao();
			dao.saveChildClient(infos);
		} catch (IException ie) {
			Log.print(ie.toString());
			throw ie;
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}

	public void deleteChildClient(ChildClientPrivilegeInfo[] infos) throws Exception {
		try {
			if (infos == null) {
				return;
			}

			ChildClientPrivilegeDao dao = new ChildClientPrivilegeDao();

			for (int i = 0; i < infos.length; i++) {
				dao.deleteChildClient(infos[i].getClientID(), infos[i].getChildClientID());
			}

		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}
}