package com.iss.itreasury.settlement.integration.serviceinterface;

import com.iss.itreasury.settlement.integration.service.ISETTService;
import com.iss.itreasury.settlement.integration.client.info.ImpAccountResult;
import com.iss.itreasury.settlement.integration.client.info.BSAccountInfo;
import com.iss.itreasury.settlement.integration.client.info.SettResultInfo;
import com.iss.itreasury.settlement.integration.client.info.SettTransInfo;
import com.iss.itreasury.settlement.integration.client.info.SystemStatusInfo;
import java.rmi.Remote;

public interface ISETTBankService extends ISETTService ,Remote{	
	public ImpAccountResult impAccount(BSAccountInfo accountInfo) throws Exception;
	public SystemStatusInfo querySystemStatus(String refferenceCode)throws Exception;
	public SettResultInfo checkSettTransStatus(SettTransInfo info) throws Exception;
}
