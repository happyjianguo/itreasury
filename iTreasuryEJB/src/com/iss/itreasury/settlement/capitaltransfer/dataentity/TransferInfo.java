/*
 * Created on 2007-4-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.capitaltransfer.dataentity;

import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferInfo {
	CapitalTransferInfo CapitalInfo = null;  //资金划拨申请
	TransSpecialOperationInfo TransInfo = null;  //资金划拨业务处理
	
	/**
	 * @return Returns the capitalInfo.
	 */
	public CapitalTransferInfo getCapitalInfo() {
		return CapitalInfo;
	}
	/**
	 * @param capitalInfo The capitalInfo to set.
	 */
	public void setCapitalInfo(CapitalTransferInfo capitalInfo) {
		CapitalInfo = capitalInfo;
	}
	/**
	 * @return Returns the transInfo.
	 */
	public TransSpecialOperationInfo getTransInfo() {
		return TransInfo;
	}
	/**
	 * @param transInfo The transInfo to set.
	 */
	public void setTransInfo(TransSpecialOperationInfo transInfo) {
		TransInfo = transInfo;
	}
}
