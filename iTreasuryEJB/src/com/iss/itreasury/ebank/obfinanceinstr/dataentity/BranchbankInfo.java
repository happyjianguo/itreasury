
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.util.*;

public class BranchbankInfo implements java.io.Serializable
{
	private long branchID = -1;
	private String branchCode = "";
	private String branchName = "";
	private long officeID = -1;
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getBranchID() {
		return branchID;
	}
	public void setBranchID(long branchID) {
		this.branchID = branchID;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}
	