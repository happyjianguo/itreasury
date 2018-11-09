package com.iss.itreasury.itreasuryinfo.branch.dataentity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BranchRateResult2 {
	private long branchID = -1;		//开户行类型
	private List rateList = new ArrayList();   //利率集合  <BranchRateResultInfo>
	private Collection rateCollection = new ArrayList();
	public long getBranchID() {
		return branchID;
	}
	public void setBranchID(long branchID) {
		this.branchID = branchID;
	}
	public List getRateList() {
		return rateList;
	}
	public void setRateList(List rateList) {
		this.rateList = rateList;
	}
	public Collection getRateCollection() {
		return rateCollection;
	}
	public void setRateCollection(Collection rateCollection) {
		this.rateCollection = rateCollection;
	}
	
	
}
