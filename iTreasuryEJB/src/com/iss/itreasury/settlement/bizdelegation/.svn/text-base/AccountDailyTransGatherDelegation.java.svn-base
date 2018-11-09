/*
 * Created on 2005-8-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.AccountDailyTransGatherConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.AccountDailyTransGather;

/**
 * @author hkzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountDailyTransGatherDelegation {
	public Vector findAccountDailyTransGatherInfos(AccountDailyTransGatherConditionInfo aInfo){
		AccountDailyTransGather accga = new AccountDailyTransGather();
		Vector v = new Vector();
		try{
		v = accga.findAccountDailyTransGatherInfos(aInfo);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return v;
	}
}
