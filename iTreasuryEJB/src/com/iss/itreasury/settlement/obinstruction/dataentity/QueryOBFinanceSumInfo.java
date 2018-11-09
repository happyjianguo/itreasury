package com.iss.itreasury.settlement.obinstruction.dataentity;

import java.io.Serializable;

public class QueryOBFinanceSumInfo implements Serializable{

	public QueryOBFinanceSumInfo()
	{
		super();
		
	}
	 private double AllFinanceSumInfo = 0.0;
	 private double PageFinanceSumInfo = 0.0;
	public double getAllFinanceSumInfo() {
		return AllFinanceSumInfo;
	}
	public void setAllFinanceSumInfo(double allFinanceSumInfo) {
		AllFinanceSumInfo = allFinanceSumInfo;
	}
	public double getPageFinanceSumInfo() {
		return PageFinanceSumInfo;
	}
	public void setPageFinanceSumInfo(double pageFinanceSumInfo) {
		PageFinanceSumInfo = pageFinanceSumInfo;
	}
}
