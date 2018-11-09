package com.iss.itreasury.settlement.capitaltransfer.bizlogic;

import java.rmi.RemoteException;

import com.iss.itreasury.settlement.capitaltransfer.dao.CapitalTransferApplyDao;
import com.iss.itreasury.settlement.capitaltransfer.dataentity.CapitalTransferInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class CapitalTransferApplyBiz {

	public long add(CapitalTransferInfo info) throws RemoteException, IException
	{
		long result = -1;
		
		CapitalTransferApplyDao dao = new CapitalTransferApplyDao();
		CapitalTransferInfo ctinfo = new CapitalTransferInfo();
		try 
		{
			ctinfo = dao.findByID(info.getID());
			if( ctinfo.getID() != -1 ) {
				dao.update(info);
				result = info.getID();
			} else {
				result = dao.add(info);
				
				if(result < 0) {
					throw new RemoteException("save capitaltransfer create error");
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public CapitalTransferInfo findByID ( long ApplyID )  throws RemoteException, IException
	{
		CapitalTransferInfo info = new CapitalTransferInfo();
		CapitalTransferApplyDao dao = new CapitalTransferApplyDao();
		
		try {
			info = dao.findByID(ApplyID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	public PageLoader getApprovalTrans ( CapitalTransferInfo conditionInfo ,long userID) {
		PageLoader pageLoader = (PageLoader)
        com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		CapitalTransferApplyDao dao = new CapitalTransferApplyDao();
		
		try {
			pageLoader = dao.getApprovalTrans(conditionInfo,userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageLoader;
	}
}
