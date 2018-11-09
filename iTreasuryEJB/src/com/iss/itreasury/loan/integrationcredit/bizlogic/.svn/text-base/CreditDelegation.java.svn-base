package com.iss.itreasury.loan.integrationcredit.bizlogic;

import com.iss.itreasury.loan.util.LOANConstant;

public class CreditDelegation {
	
	public static AbstractCreditBiz getCreditInstance(){
		AbstractCreditBiz biz = null;
		long relationId = LOANConstant.CreditRelationType.SIMPLE;
		
		if(relationId == LOANConstant.CreditRelationType.SIMPLE){
			biz = new CreditSimpleBiz();
		}
		else if(relationId == LOANConstant.CreditRelationType.MULTILEVEL){
			biz = new CreditBiz();
		}
		return biz;
	}

}
