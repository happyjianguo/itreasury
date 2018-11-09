/*
 * Created on 2004-9-10
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;

import com.iss.itreasury.settlement.settadjustinterestrate.bizlogic.SettAdjustInterestRate;
import com.iss.itreasury.settlement.settadjustinterestrate.bizlogic.SettAdjustInterestRateHome;
import com.iss.itreasury.util.EJBHomeFactory;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettAdjustInterestRateDelegation
{
    private SettAdjustInterestRate settAdjustInterestRateFacade = null;
	public SettAdjustInterestRateDelegation() throws RemoteException
	{
		try
		{
		    SettAdjustInterestRateHome home = (SettAdjustInterestRateHome) EJBHomeFactory.getFactory().lookUpHome(SettAdjustInterestRateHome.class);
		    settAdjustInterestRateFacade = home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}
}
