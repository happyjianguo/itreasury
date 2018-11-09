package com.iss.itreasury.craftbrother.query.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.craftbrother.query.dao.QueryAssetInterestCalDAO;
import com.iss.itreasury.craftbrother.query.dao.QueryAttornContractDAO;
import com.iss.itreasury.craftbrother.query.dataentity.AssetInterestCalInfo;
import com.iss.itreasury.craftbrother.query.dataentity.QueryAttornContractInfo;
import com.iss.itreasury.craftbrother.query.dataentity.QueryAttornContractResultInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;


public class QueryAttornContractBiz {

	QueryAttornContractDAO dao = new QueryAttornContractDAO();

	public Collection QueryAttornContractInfo(QueryAttornContractInfo info)throws Exception{
		List QueryAttornContractList = new ArrayList();
		QueryAttornContractList = dao.QueryAttornContractTotalInfo(info);
		return QueryAttornContractList;
	}
	
	public Collection QueryAttornContractParticularInfo(QueryAttornContractInfo info)throws Exception{
		List QueryAttornContractList = new ArrayList();
		QueryAttornContractList = dao.QueryAttornContractParticularInfo(info);
		return QueryAttornContractList;
	}
	
}
