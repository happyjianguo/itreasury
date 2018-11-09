package com.iss.itreasury.settlement.setting.action;

/**
 * 账户类型编码设置
 */

import java.util.Map;

import com.iss.itreasury.settlement.setting.bizlogic.Sett_AccoutTypeQueryBiz;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_SubAccountTypeSettingBiz;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_SubAccountTypeSettingZBJBiz;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_SubAccoutTypeDQBiz;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_SubAccoutTypeDQSubBiz;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_SubAccoutTypeLoanSubBiz;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_SubAccoutTypeQueryBiz;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_SubAccoutTypeTXBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sett_SubAccountTypeAction
{
	
	public PagerInfo queryAccountType(Map map)throws Exception {
		
		Sett_SubAccoutTypeQueryBiz biz=new Sett_SubAccoutTypeQueryBiz();
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long OfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
			
			long CurrencyID=Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
			
			long lAccountGroupID=-1;
			
			String strTmp = "";
			
			strTmp=map.get("lAccountGroup".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountGroupID = Long.parseLong(strTmp);
			}
			
			pagerInfo = biz.queryBranchInfo(lAccountGroupID, OfficeID, CurrencyID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	public PagerInfo queryAccountTypeTX(Map map)throws Exception {
		
		Sett_SubAccoutTypeTXBiz biz=new Sett_SubAccoutTypeTXBiz();
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long OfficeID=Long.parseLong(map.get("lOfficeID".toLowerCase()).toString());
			
			long CurrencyID=Long.parseLong(map.get("lCurrencyID".toLowerCase()).toString());
			
			long lAccountGroupID=-1;
			
			String strTmp = "";
			
			strTmp=map.get("lAccountGroup".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountGroupID = Long.parseLong(strTmp);
			}
			
			pagerInfo = biz.queryBranchInfo(lAccountGroupID, OfficeID, CurrencyID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	public PagerInfo queryAccountType1(Map map)throws Exception {
		
		Sett_AccoutTypeQueryBiz biz=new Sett_AccoutTypeQueryBiz();
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long OfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
			
			long CurrencyID=Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
			
			long lAccountGroupID=-1;
			
			String strTmp = "";
			
			strTmp=map.get("lAccountGroup".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountGroupID = Long.parseLong(strTmp);
			}
			
			pagerInfo = biz.queryBranchInfo(lAccountGroupID, OfficeID, CurrencyID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	public PagerInfo queryAccountTypeDQ(Map map)throws Exception {
		
		Sett_SubAccoutTypeDQBiz biz=new Sett_SubAccoutTypeDQBiz();
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long OfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
			
			long CurrencyID=Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
			
			long lAccountGroupID=-1;
			
			String strTmp = "";
			
			strTmp=map.get("lAccountGroup".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountGroupID = Long.parseLong(strTmp);
			}
			
			pagerInfo = biz.queryBranchInfo(lAccountGroupID, OfficeID, CurrencyID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	public PagerInfo querySubAccountType(Map map)throws Exception {
		
		Sett_SubAccountTypeSettingBiz biz=new Sett_SubAccountTypeSettingBiz();
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long lOfficeID=Long.parseLong(map.get("lOfficeID".toLowerCase()).toString());
			
			long lCurrencyID=Long.parseLong(map.get("lCurrencyID".toLowerCase()).toString());
			
			long lAccountGroupID=-1;
			long lAccountTypeID=-1;
			
			String strTmp = "";
			
			strTmp=map.get("lAccountGroup".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountGroupID = Long.parseLong(strTmp);
			}
			
			strTmp=map.get("lAccountTypeID".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountTypeID = Long.parseLong(strTmp);
			}
			
			pagerInfo = biz.querySubAccount(lAccountGroupID, lAccountTypeID, lOfficeID, lCurrencyID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	public PagerInfo querySubAccountType1(Map map)throws Exception {
		
		Sett_SubAccountTypeSettingZBJBiz biz=new Sett_SubAccountTypeSettingZBJBiz();
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long lOfficeID=Long.parseLong(map.get("lOfficeID".toLowerCase()).toString());
			
			long lCurrencyID=Long.parseLong(map.get("lCurrencyID".toLowerCase()).toString());
			
			long lAccountGroupID=-1;
			long lAccountTypeID=-1;
			
			String strTmp = "";
			
			strTmp=map.get("lAccountGroup".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountGroupID = Long.parseLong(strTmp);
			}
			
			strTmp=map.get("lAccountTypeID".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountTypeID = Long.parseLong(strTmp);
			}
			
			pagerInfo = biz.querySubAccount(lAccountGroupID, lAccountTypeID, lOfficeID, lCurrencyID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	
	
	
public PagerInfo querySubAccountTypeDQSub(Map map)throws Exception {
		
	Sett_SubAccoutTypeDQSubBiz biz=new Sett_SubAccoutTypeDQSubBiz();
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long lOfficeID=Long.parseLong(map.get("lOfficeID".toLowerCase()).toString());
			
			long lCurrencyID=Long.parseLong(map.get("lCurrencyID".toLowerCase()).toString());
			
			long lAccountGroupID=-1;
			long lAccountTypeID=-1;
			
			String strTmp = "";
			
			strTmp=map.get("lAccountGroup".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountGroupID = Long.parseLong(strTmp);
			}
			
			strTmp=map.get("lAccountTypeID".toLowerCase()).toString();
			
			if (strTmp != null && strTmp.length() > 0) {
				lAccountTypeID = Long.parseLong(strTmp);
			}
			
			pagerInfo = biz.queryBranchInfo(lAccountTypeID, lOfficeID, lCurrencyID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
public PagerInfo querySubAccountTypeLoanSub(Map map)throws Exception {
	
	Sett_SubAccoutTypeLoanSubBiz biz=new Sett_SubAccoutTypeLoanSubBiz();
	PagerInfo pagerInfo=null;
	try {
		//获得登录officeid和currencyid
		long lOfficeID=Long.parseLong(map.get("lOfficeID".toLowerCase()).toString());
		
		long lCurrencyID=Long.parseLong(map.get("lCurrencyID".toLowerCase()).toString());
		
		long lAccountGroupID=-1;
		long lAccountTypeID=-1;
		
		String strTmp = "";
		
		strTmp=map.get("lAccountGroup".toLowerCase()).toString();
		
		if (strTmp != null && strTmp.length() > 0) {
			lAccountGroupID = Long.parseLong(strTmp);
		}
		
		strTmp=map.get("lAccountTypeID".toLowerCase()).toString();
		
		if (strTmp != null && strTmp.length() > 0) {
			lAccountTypeID = Long.parseLong(strTmp);
		}
		
		pagerInfo = biz.queryBranchInfo(lAccountGroupID, lAccountTypeID, lOfficeID, lCurrencyID);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw new Exception(e.getMessage(), e);
	}
	
	return pagerInfo;
	
}
	

}
