package com.iss.itreasury.ebank.obquery.bizlogic;


import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.ebank.obquery.dao.OBHisBalanceDAO;
import com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class  OBHisBalanceBiz{
	OBHisBalanceDAO dao=new OBHisBalanceDAO();
	/**
	 *汇总账户余额查询-历史余额查询 
	 * @param info
	 * @return
	 */
	public PageLoader QueryHistoryBalance(OBQueryAccInfo info)throws Exception
	{
	    PageLoader pageLoader = null;
		try{
		    info.setQuerytype(OBConstant.QueryByUnderClient.NO);//账户交易
			pageLoader= dao.QueryHistoryBalance(info);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IException("Gen_E001") ;
		}
		return pageLoader;			
	}
	/**
	 *下属单位账户信息查询-汇总账户历史余额查询
	 * @param info
	 * @return
	 */
	public PageLoader QueryClientHistoryBal(OBQueryAccInfo info)throws Exception
	{    
        PageLoader pageLoader = null;
    
		try{
		    info.setQuerytype(OBConstant.QueryByUnderClient.YES);//下属单位账户交易
			pageLoader= dao.QueryClientHistoryBal(info);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IException("Gen_E001") ;
		}
		return pageLoader;			
	}
	/**
	 *透支查询
	 * @param info
	 * @return
	 */
	public Collection QueryOverdraft(OBQueryAccInfo info)throws Exception{
		Collection coll=new ArrayList();
		try{
			coll=dao.QueryOverdraft(info);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex ;
		}
		return coll;				
	}

}