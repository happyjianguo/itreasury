/*
 * Created on 2004-5-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.craftbrother.mywork.bizlogic;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.craftbrother.mywork.dataentity.MyWorkColumn;

/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MyWorkDelegation {
	MyWork mywork = null;
	
	public MyWorkDelegation(){
		mywork = new MyWork();
	}
	
	public MyWorkColumn[] getMyWork(long userId,long currencyId,long officeId)throws SecuritiesException{
		return mywork.getMyWork(userId,currencyId,officeId);
	}
}
