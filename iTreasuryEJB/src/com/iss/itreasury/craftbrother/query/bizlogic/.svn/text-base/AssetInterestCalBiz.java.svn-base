package com.iss.itreasury.craftbrother.query.bizlogic;

import com.iss.itreasury.craftbrother.query.dao.QueryAssetInterestCalDAO;
import com.iss.itreasury.craftbrother.query.dataentity.AssetInterestCalInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;


public class AssetInterestCalBiz {
//	public AssetInterestCalBiz(){
//		
//	}
	QueryAssetInterestCalDAO dao = new QueryAssetInterestCalDAO();
	public PageLoader QueryAssetInterestCal(AssetInterestCalInfo info)throws Exception{
		PageLoader pageLoader = null;
		try{
			pageLoader= dao.QueryAssetInterestCal(info);
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new IException("Gen_E001") ;
		}
		return pageLoader;
	}
}
