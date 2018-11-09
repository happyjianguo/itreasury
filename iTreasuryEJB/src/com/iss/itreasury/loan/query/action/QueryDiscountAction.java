package com.iss.itreasury.loan.query.action;

import java.util.Map;

import com.iss.itreasury.loan.query.bizlogic.QueryDiscountBiz;
import com.iss.itreasury.loan.query.dataentity.QueryDiscountInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QueryDiscountAction {
	
	QueryDiscountBiz discountBiz = new QueryDiscountBiz();
	
	/**
	 * 贴现合同查询
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-15
	 */
	public PagerInfo queryDiscountContractInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try {
			QueryDiscountInfo discountInfo = new QueryDiscountInfo();
			discountInfo.convertMapToDataEntity(map);
			
			pagerInfo = discountBiz.queryDiscountContractInfo(discountInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * 贴现票据明细表查询
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-15
	 */
	public PagerInfo queryDiscountBillDetailInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lContractID = -1;
		long lDiscountID = -1;
		try {
			if(map != null && map.get("lcontractid") != null){
				lContractID = Long.parseLong((String) map.get("lcontractid"));
			}
			if(map != null && map.get("ldiscountid") != null){
				lDiscountID = Long.parseLong((String) map.get("ldiscountid"));
			}
			
			pagerInfo = discountBiz.queryDiscountBillDetailInfo(lContractID,lDiscountID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * 贴现票据计息明细表查询
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-16
	 */
	public PagerInfo queryDiscountBillInterestInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lContractID = -1;
		long lDiscountID = -1;
		long lCredenceID = -1;
		try {
			if(map != null && map.get("lcontractid") != null){
				lContractID = Long.parseLong((String) map.get("lcontractid"));
			}
			if(map != null && map.get("ldiscountid") != null){
				lDiscountID = Long.parseLong((String) map.get("ldiscountid"));
			}
			if(map != null && map.get("lcredenceid") != null){
				lCredenceID = Long.parseLong((String) map.get("lcredenceid"));
			}
			
			pagerInfo = discountBiz.queryDiscountBillInterestInfo(lContractID,lDiscountID,lCredenceID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * 贴现凭证信息查询
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-16
	 */
	public PagerInfo queryDiscountVoucherInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try {
			QueryDiscountInfo discountVoucherInfo = new QueryDiscountInfo();
			discountVoucherInfo.convertMapToDataEntity(map);
			
			pagerInfo = discountBiz.queryDiscountVoucherInfo(discountVoucherInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
