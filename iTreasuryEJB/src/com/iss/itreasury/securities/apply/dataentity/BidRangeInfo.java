package com.iss.itreasury.securities.apply.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class BidRangeInfo extends SECBaseDataEntity {

	private long id;
	private long applyFormId;
	private double bidStartAmount;
	private double bidEndAmount;
	private double bidStartRate;
	private double bidEndRate;
	private double applyQuantity;
	private long statusId;


	/**
	 * @return Returns the applyFormId.
	 */
	public long getApplyFormId() {
		return applyFormId;
	}
	/**
	 * @param applyFormId The applyFormId to set.
	 */
	public void setApplyFormId(long applyFormId) {
		this.applyFormId = applyFormId;
		putUsedField("applyFormId", applyFormId);
	}
	/**
	 * @return Returns the applyQuantity.
	 */
	public double getApplyQuantity() {
		return applyQuantity;
	}
	/**
	 * @param applyQuantity The applyQuantity to set.
	 */
	public void setApplyQuantity(double applyQuantity) {
		this.applyQuantity = applyQuantity;
		putUsedField("applyQuantity", applyQuantity);
	}
	/**
	 * @return Returns the bidEndAmount.
	 */
	public double getBidEndAmount() {
		return bidEndAmount;
	}
	/**
	 * @param bidEndAmount The bidEndAmount to set.
	 */
	public void setBidEndAmount(double bidEndAmount) {
		this.bidEndAmount = bidEndAmount;
		putUsedField("bidEndAmount", bidEndAmount);
	}
	/**
	 * @return Returns the bidEndRate.
	 */
	public double getBidEndRate() {
		return bidEndRate;
	}
	/**
	 * @param bidEndRate The bidEndRate to set.
	 */
	public void setBidEndRate(double bidEndRate) {
		this.bidEndRate = bidEndRate;
		putUsedField("bidEndRate", bidEndRate);
	}
	/**
	 * @return Returns the bidStartAmount.
	 */
	public double getBidStartAmount() {
		return bidStartAmount;
	}
	/**
	 * @param bidStartAmount The bidStartAmount to set.
	 */
	public void setBidStartAmount(double bidStartAmount) {
		this.bidStartAmount = bidStartAmount;
		putUsedField("bidStartAmount", bidStartAmount);
	}
	/**
	 * @return Returns the bidStartRate.
	 */
	public double getBidStartRate() {
		return bidStartRate;
	}
	/**
	 * @param bidStartRate The bidStartRate to set.
	 */
	public void setBidStartRate(double bidStartRate) {
		this.bidStartRate = bidStartRate;
		putUsedField("bidStartRate", bidStartRate);
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}
	
}
