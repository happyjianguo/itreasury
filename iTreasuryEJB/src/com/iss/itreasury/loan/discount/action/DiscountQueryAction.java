package com.iss.itreasury.loan.discount.action;

import java.util.Map;

import com.iss.itreasury.loan.discount.biz.DiscountQueryBiz;
import com.iss.itreasury.loan.query.bizlogic.QueryNoticeBiz;
import com.iss.itreasury.loan.query.dataentity.QueryNoticeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class DiscountQueryAction {
	DiscountQueryBiz biz = new DiscountQueryBiz();
	public PagerInfo findDiscountBillByDiscountID(Map map) throws Exception {
		long lLoanID = Long.valueOf(map.get("lloanid")+"").longValue();
		long level = Long.valueOf(map.get("level")+"").longValue();
		PagerInfo pagerInfo = null;
		try {

			pagerInfo = biz.findDiscountBillByDiscountID(lLoanID,level);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	public PagerInfo findDiscountBillAmount(Map map) throws Exception {
		long lLoanID = Long.valueOf(map.get("lloanid")+"").longValue();
		double purchaserInterestRate = Double.valueOf(map.get("purchaserinterestrate")+"").doubleValue();
		double fDiscountRate = Double.valueOf(map.get("fdiscountrate")+"").doubleValue();
		String rq = map.get("rq")+"";
		
		String symbol = map.get("symbol")+"";
		PagerInfo pagerInfo = null;
		try {
			pagerInfo = biz.findDiscountBillAmount(lLoanID,rq,fDiscountRate,symbol,purchaserInterestRate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	public PagerInfo findBillInterestByID(Map map) throws Exception {
		long lcontractid = Long.valueOf(map.get("lcontractid")+"").longValue();
		String symbol = map.get("symbol")+"";
		if(lcontractid<0)return null;
		PagerInfo pagerInfo;
		try {
			pagerInfo = biz.findBillInterestByID(lcontractid,-1,1000,1,1, Constant.PageControl.CODE_ASCORDESC_ASC,symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo findBillInterest(Map map) throws Exception {
		long lcredenceid = Long.valueOf(map.get("lcredenceid")+"").longValue();
		String symbol = map.get("symbol")+"";
		if(lcredenceid<0)return null;
		PagerInfo pagerInfo;
		try {
			pagerInfo = biz.findBillInterest(-1,lcredenceid,1000,1,1, Constant.PageControl.CODE_ASCORDESC_ASC,symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
}
