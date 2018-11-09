/*
 * Created on 2004-4-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author hjliu
 * 证券库存查询的页面显示类
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QuerySecuritiesStockInfo extends SECBaseDataEntity implements Serializable {
	
	private Timestamp stockDate      = null;    //库存日期
	private String clientName        = "";      //业务单位名称	
	
	private String stockHolderAccountName = ""; //股东帐户名称
	
	//private long  accountType        = -1;      //资金账户类型 1:证券账户2:开发式基金账户3:银行账户	
	private String bankOfDepositName = "";      //在资金账户类型是证券账户的前提下，显示开户营业部名称
	private String accountCode       = "";      //资金账号	
	private String accountName       = "";      //资金账户名称
	private String fundManagerCoName = "";      //在资金账户类型是开放式基金账户的前提下，显示基金管理公司名称--给国机的结果
	//交易对手－－给华能的结果
	private long counterPartId = -1;
	private String securitiesType    = "";      //证券类别	
	private String securitiesName    = "";      //证券名称	
	private double quantity          = 0.0;      //库存
	//private double securitiesRate    = 0.0;     //回购抵押率，折成标准券的比例	
	private double standardQuantity  = 0.0;      //折成标准券 
	private double frozenQuantity    = 0.0;      //已冻结库存	
	private double unitCost          = 0.0;     //单位成本
	private double unitNetCost       = 0.0;     //单位成本（净价）
	private double cost              = 0.0;     //实际成本
	private double netCost           = 0.0;     //实际成本（净价）
	
    //	业务性质ID 
	//           businessAttributeId＝1时候交易对手是交易对手
    //           businessAttributeId＝2时候交易对手是开户营业部
	//           businessAttributeId＝3时候交易对手是基金管理公司
	private long  businessAttributeId  = -1;

	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	/**
	 * @return Returns the bankOfDepositName.
	 */
	public String getBankOfDepositName() {
		return bankOfDepositName;
	}

	/**
	 * @param bankOfDepositName The bankOfDepositName to set.
	 */
	public void setBankOfDepositName(String bankOfDepositName) {
		this.bankOfDepositName = bankOfDepositName;
	}

	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return Returns the cost.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost The cost to set.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	

	/**
	 * @return Returns the netCost.
	 */
	public double getNetCost() {
		return netCost;
	}

	/**
	 * @param netCost The netCost to set.
	 */
	public void setNetCost(double netCost) {
		this.netCost = netCost;
	}

	

	
	/**
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName() {
		return securitiesName;
	}

	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName) {
		this.securitiesName = securitiesName;
	}

	/**
	 * @return Returns the securitiesType.
	 */
	public String getSecuritiesType() {
		return securitiesType;
	}

	/**
	 * @param securitiesType The securitiesType to set.
	 */
	public void setSecuritiesType(String securitiesType) {
		this.securitiesType = securitiesType;
	}


	/**
	 * @return Returns the unitCost.
	 */
	public double getUnitCost() {
		return unitCost;
	}

	/**
	 * @param unitCost The unitCost to set.
	 */
	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	/**
	 * @return Returns the unitNetCost.
	 */
	public double getUnitNetCost() {
		return unitNetCost;
	}

	/**
	 * @param unitNetCost The unitNetCost to set.
	 */
	public void setUnitNetCost(double unitNetCost) {
		this.unitNetCost = unitNetCost;
	}

	/**
	 * @return Returns the fundManagerCoName.
	 */
	public String getFundManagerCoName() {
		return fundManagerCoName;
	}

	/**
	 * @param fundManagerCoName The fundManagerCoName to set.
	 */
	public void setFundManagerCoName(String fundManagerCoName) {
		this.fundManagerCoName = fundManagerCoName;
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
	public String getStockHolderAccountName() {
		return stockHolderAccountName;
	}

	/**
	 * @param string
	 */
	public void setStockHolderAccountName(String string) {
		stockHolderAccountName = string;
	}

	/**
	 * @return
	 */
	public double getFrozenQuantity() {
		return frozenQuantity;
	}

	/**
	 * @return
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @return
	 */
	public double getStandardQuantity() {
		return standardQuantity;
	}

	/**
	 * @param d
	 */
	public void setFrozenQuantity(double d) {
		frozenQuantity = d;
	}

	/**
	 * @param d
	 */
	public void setQuantity(double d) {
		quantity = d;
	}

	/**
	 * @param d
	 */
	public void setStandardQuantity(double d) {
		standardQuantity = d;
	}

    /**
     * @return Returns the businessAttributeId.
     */
    public long getBusinessAttributeId() {
        return businessAttributeId;
    }
    /**
     * @param businessAttributeId The businessAttributeId to set.
     */
    public void setBusinessAttributeId(long businessAttributeId) {
        this.businessAttributeId = businessAttributeId;
    }
    /**
     * @return Returns the accountCode.
     */
    public String getAccountCode() {
        return accountCode;
    }
    /**
     * @param accountCode The accountCode to set.
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
	/**
	 * @return
	 */
	public long getCounterPartId()
	{
		return counterPartId;
	}

	/**
	 * @param l
	 */
	public void setCounterPartId(long l)
	{
		counterPartId = l;
	}

	/**
	 * @return
	 */
	public Timestamp getStockDate()
	{
		return stockDate;
	}

	/**
	 * @param timestamp
	 */
	public void setStockDate(Timestamp timestamp)
	{
		stockDate = timestamp;
	}

}
