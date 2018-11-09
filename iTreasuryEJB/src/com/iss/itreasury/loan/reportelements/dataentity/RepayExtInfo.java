package com.iss.itreasury.loan.reportelements.dataentity;

/**
 * 单笔还款通知单需要补录的信息有：还款明细备注
 * 	数据库映射
 * @date 2012年6月14日 
 * @author ylwue
 *
 */
public class RepayExtInfo {
	/* 放款通知单号 */
	private String payCode = "";

	/* 还款通知单号 */
	private String repayCode = "";

	/* 还款明细备注 */
	private String sdetailremarks = "";
	/* 合同编号 */
	private String scontractcodeID = "";
	/**
	 * @return the payCode
	 */
	public String getPayCode() {
		return payCode;
	}
	/**
	 * @param payCode the payCode to set
	 */
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	/**
	 * @return the repayCode
	 */
	public String getRepayCode() {
		return repayCode;
	}
	/**
	 * @param repayCode the repayCode to set
	 */
	public void setRepayCode(String repayCode) {
		this.repayCode = repayCode;
	}
	/**
	 * @return the sdetailremarks
	 */
	public String getSdetailremarks() {
		return sdetailremarks;
	}
	/**
	 * @param sdetailremarks the sdetailremarks to set
	 */
	public void setSdetailremarks(String sdetailremarks) {
		this.sdetailremarks = sdetailremarks;
	}
	/**
	 * @return the scontractcodeID
	 */
	public String getScontractcodeID() {
		return scontractcodeID;
	}
	/**
	 * @param scontractcodeID the scontractcodeID to set
	 */
	public void setScontractcodeID(String scontractcodeID) {
		this.scontractcodeID = scontractcodeID;
	}

	
}
