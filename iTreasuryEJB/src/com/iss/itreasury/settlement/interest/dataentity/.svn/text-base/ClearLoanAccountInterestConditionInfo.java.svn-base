package com.iss.itreasury.settlement.interest.dataentity;

import java.sql.Timestamp;

/**
 * 
 * @author hjliu
 *  date of creation 2003-11-10
 *  com.iss.itreasury.settlement.interest.bizlogic包中的结息接口interestBatch中方法clearLoanAccountInterest
 *  以及clearLoanAccountInterestReverse的查询条件类，含20个属性
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

public class ClearLoanAccountInterestConditionInfo {
	
	 long AccountID   = -1;           //主账户ID
	 long SubAccountID= -1;           //子账户ID
	 Timestamp InterestDate = null;   //在贷款账户结息时该属性是：结息日
	                                  //在贷款账户结息反交易时该属性是指：上一结息日
	 double Interest = 0.0;           // 应付利息
	 double InterestReceivable = 0.0; //应付计提利息
	 double SuretyFee = 0.0;          //应付担保费
	 double Commision  = 0.0;        //应付手续费
	 double CompoundInterest  = 0.0;  // 应付复利
	 double OverDueInterest  = 0.0;   //应付逾期罚息
	 double RealInterest  = 0.0;      //实付利息 
	 double RealInterestReceivable = 0.0; // 实付计提利息	
	 double RealSuretyFee = 0.0;      //实付担保费
	 double RealCommission = 0.0;     //实付手续费
	 double RealCompoundInterest = 0.0;//实付复利
	 double RealOverDueInterest = 0.0; //实付逾期罚息
	 long   isRemitInterest   = -1;    //是否免还剩余利息
	 long   isRemitSuretyFee   = -1;   // 是否免还剩余担保费
	 long   isRemitCommision   = -1;  //是否免还剩余手续费
	 long   isRemitCompoundInterest = -1;//是否免还剩余复利
	 long   isRemitOverDueInterest  = -1;//是否免还剩余逾期罚息
	 boolean isClearInterest = false;
	 Timestamp ClearInterestDate = null;   //本次结息日，反结息是保存本次结息日

	/**
	 * @return  主账户
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @return 应付手续费（精确值）
	 */
	public double getCommision()
	{
		return Commision;
	}

	/**
	 * @return 应付复利（精确值）
	 */
	public double getCompoundInterest()
	{
		return CompoundInterest;
	}

	/**
	 * @return 应付利息（精确值）
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * @return 结息日
	 */
	public Timestamp getInterestDate()
	{
		return InterestDate;
	}

	/**
	 * @return 应付计提利息（精确值）
	 */
	public double getInterestReceivable()
	{
		return InterestReceivable;
	}

	/**
	 * @return  是否免还剩余手续费
	 */
	public long getIsRemitCommision()
	{
		return isRemitCommision;
	}

	/**
	 * @return 是否免还剩余复利
	 */
	public long getIsRemitCompoundInterest()
	{
		return isRemitCompoundInterest;
	}

	/**
	 * @return 是否免还剩余利息
	 */
	public long getIsRemitInterest()
	{
		return isRemitInterest;
	}

	/**
	 * @return 是否免还剩余逾期罚息
	 */ 
	public long getIsRemitOverDueInterest()
	{
		return isRemitOverDueInterest;
	}

	/**
	 * @return 是否免还剩余担保费
	 */
	public long getIsRemitSuretyFee()
	{
		return isRemitSuretyFee;
	}

	/**
	 * @return 应付逾期罚息（精确值）
	 */
	public double getOverDueInterest()
	{
		return OverDueInterest;
	}

	/**
	 * @return 实付手续费（四舍五入后的值）
	 */
	public double getRealCommision()
	{
		return RealCommission;
	}

	/**
	 * @return 实付复利（四舍五入后的值）
	 */
	public double getRealCompoundInterest()
	{
		return RealCompoundInterest;
	}

	/**
	 * @return 实付利息（四舍五入后的值）
	 */
	public double getRealInterest()
	{
		return RealInterest;
	}

	/**
	 * @return 实付计提利息（四舍五入后的值）
	 */
	public double getRealInterestReceivable()
	{
		return RealInterestReceivable;
	}

	/**
	 * @return 实付逾期罚息（四舍五入后的值）
	 */
	public double getRealOverDueInterest()
	{
		return RealOverDueInterest;
	}

	/**
	 * @return 实付担保费（四舍五入后的值）
	 */
	public double getRealSuretyFee()
	{
		return RealSuretyFee;
	}

	/**
	 * @return 子账户
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @return 应付担保费（精确值）
	 */ 
	public double getSuretyFee()
	{
		return SuretyFee;
	}

	/**
	 * @param mAccountID
	 */
	public void setAccountID(long lAccountID)
	{
		AccountID = lAccountID;
	}

	/**
	 * @param dCommision
	 */
	public void setCommision(double dCommision)
	{
		Commision = dCommision;
	}

	/**
	 * @param dCompoundInterest
	 */
	public void setCompoundInterest(double dCompoundInterest)
	{
		CompoundInterest = dCompoundInterest;
	}

	/**
	 * @param dInterest
	 */
	public void setInterest(double dInterest)
	{
		Interest = dInterest;
	}

	/**
	 * @param dtInterestDate
	 */
	public void setInterestDate(Timestamp dtInterestDate)
	{
		InterestDate = dtInterestDate;
	}

	/**
	 * @param dInterestReceivable
	 */
	public void setInterestReceivable(double dInterestReceivable)
	{
		InterestReceivable = dInterestReceivable;
	}

	/**
	 * @param lIsRemitCommission
	 */
	public void setIsRemitCommision(long lIsRemitCommission)
	{
		isRemitCommision = lIsRemitCommission;
	}

	/**
	 * @param lIsRemitCompoundInterest
	 */
	public void setIsRemitCompoundInterest(long lIsRemitCompoundInterest)
	{
		isRemitCompoundInterest = lIsRemitCompoundInterest;
	}

	/**
	 * @param lIsRemitInterest
	 */
	public void setIsRemitInterest(long lIsRemitInterest)
	{
		isRemitInterest = lIsRemitInterest;
	}

	/**
	 * @param lIsRemitOverDueInterest
	 */
	public void setIsRemitOverDueInterest(long lIsRemitOverDueInterest)
	{
		isRemitOverDueInterest = lIsRemitOverDueInterest;
	}

	/**
	 * @param lIsRemitSuretyFee
	 */
	public void setIsRemitSuretyFee(long lIsRemitSuretyFee)
	{
		isRemitSuretyFee = lIsRemitSuretyFee;
	}

	/**
	 * @param dOverDueInterest
	 */
	public void setOverDueInterest(double dOverDueInterest)
	{
		OverDueInterest = dOverDueInterest;
	}

	/**
	 * @param dRealCommission
	 */
	public void setRealCommission(double dRealCommission)
	{
		RealCommission = dRealCommission;
	}

	/**
	 * @param dRealCompoundInterest
	 */
	public void setRealCompoundInterest(double dRealCompoundInterest)
	{
		RealCompoundInterest = dRealCompoundInterest;
	}

	/**
	 * @param dRealInterest
	 */
	public void setRealInterest(double dRealInterest)
	{
		RealInterest = dRealInterest;
	}

	/**
	 * @param dRealInterestReceivable
	 */
	public void setRealInterestReceivable(double dRealInterestReceivable)
	{
		RealInterestReceivable = dRealInterestReceivable;
	}

	/**
	 * @param dRealOverDueInterest
	 */
	public void setRealOverDueInterest(double dRealOverDueInterest)
	{
		RealOverDueInterest = dRealOverDueInterest;
	}

	/**
	 * @param dRealSuretyFee
	 */
	public void setRealSuretyFee(double dRealSuretyFee)
	{
		RealSuretyFee = dRealSuretyFee;
	}

	/**
	 * @param lSubAccountID
	 */
	public void setSubAccountID(long lSubAccountID)
	{
		SubAccountID = lSubAccountID;
	}

	/**
	 * @param dSuretyFee
	 */
	public void setSuretyFee(double dSuretyFee)
	{
		SuretyFee = dSuretyFee;
	}

	public boolean isClearInterest() {
		return isClearInterest;
	}

	public void setClearInterest(boolean isClearInterest) {
		this.isClearInterest = isClearInterest;
	}

	public Timestamp getClearInterestDate() {
		return ClearInterestDate;
	}

	public void setClearInterestDate(Timestamp clearInterestDate) {
		ClearInterestDate = clearInterestDate;
	}

	public double getRealCommission() {
		return RealCommission;
	}

}