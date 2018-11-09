package com.iss.itreasury.ebank.obquery.bizlogic;


import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.ebank.obquery.dao.OBTodayBalanceDAO;
import com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo;
import com.iss.itreasury.ebank.obquery.dataentity.OBTodayBalanceResultInfo;
import com.iss.itreasury.util.IException;

public class  OBTodayBalanceBiz{
	OBTodayBalanceDAO dao=new OBTodayBalanceDAO();
	/**
	 *汇总账户余额查询-当日余额查询
	 * @param info
	 * @return
	 */
	public OBTodayBalanceResultInfo[] QueryTodayBalance(OBQueryAccInfo info) throws Exception{

	    OBTodayBalanceResultInfo[] results = null;
	    
		try{
		    results=dao.QueryTodayBalance(info);
		    		    
		}catch(Exception ex){
			
		    ex.printStackTrace();
			
			throw new IException("Gen_E001") ;
		}
		
		return results;				
	}
	/**
	 *下属单位账户信息查询-汇总账户当日余额查询
	 * @param info
	 * @return
	 */
	/*public OBTodayBalanceResultInfo[] QueryClientTodayBal(OBQueryAccInfo info)throws Exception{
	    
	    OBTodayBalanceResultInfo[] results = null;
	    
		try{
		    results=dao.QueryClientTodayBal(info);
		    
		}catch(Exception ex){
			
		    ex.printStackTrace();
			
			throw new IException("Gen_E001") ;
		}
		return results;				
	}*/
	
}