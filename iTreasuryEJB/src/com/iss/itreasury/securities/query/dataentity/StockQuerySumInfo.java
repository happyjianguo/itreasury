/*
 * Created on 2004-4-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author hjliu
 * 证券证券查询合计信息
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StockQuerySumInfo extends SECBaseDataEntity implements Serializable {
	
	
	private double stockQuantitySum = 0.0;  //库存合计    
	private double stockCostSum = 0.0;     //实际成本合计
	private double stockNetCostSum = 0.0;  //实际成本（净价）合计
	
	

	/**
	 * @return Returns the stockCostSum.
	 */
	public double getStockCostSum() {
		return stockCostSum;
	}

	/**
	 * @param stockCostSum The stockCostSum to set.
	 */
	public void setStockCostSum(double stockCostSum) {
		this.stockCostSum = stockCostSum;
	}

	/**
	 * @return Returns the stockNetCostSum.
	 */
	public double getStockNetCostSum() {
		return stockNetCostSum;
	}

	/**
	 * @param stockNetCostSum The stockNetCostSum to set.
	 */
	public void setStockNetCostSum(double stockNetCostSum) {
		this.stockNetCostSum = stockNetCostSum;
	}


	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return
	 */
	public double getStockQuantitySum() {
		return stockQuantitySum;
	}

	/**
	 * @param d
	 */
	public void setStockQuantitySum(double d) {
		stockQuantitySum = d;
	}

}
