package com.iss.itreasury.util;

import com.iss.itreasury.loan.contract.bizlogic.Contract;
import com.iss.itreasury.loan.contract.bizlogic.ContractHome;

public class MagnifierTool {
	

	public String getUpPayAmount(long contractid) throws Exception{
		ContractHome contractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
		Contract c_ejb = contractHome.create();
        return DataFormat.formatDisabledAmount(c_ejb.findByID(contractid).getAInfo().getUnPayAmount(),2);
	}
}
