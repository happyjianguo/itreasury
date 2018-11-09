package com.iss.itreasury.itreasuryinfo.branch.dataentity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BranchRateResult1 {

	private long bankTypeID = -1;	//银行类型
	private List branchList = new ArrayList();  //开户行集合  <BranchRateResult2>
	private Collection branchCollection = new ArrayList();
	public long getBankTypeID() {
		return bankTypeID;
	}
	public void setBankTypeID(long bankTypeID) {
		this.bankTypeID = bankTypeID;
	}
	public List getBranchList() {
		return branchList;
	}
	public void setBranchList(List branchList) {
		this.branchList = branchList;
	}
	public Collection getBranchCollection() {
		return branchCollection;
	}
	public void setBranchCollection(Collection branchCollection) {
		this.branchCollection = branchCollection;
	}
	
	
}
