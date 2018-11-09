/*
 * Created on 2004-10-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_interestrateplanDAO;
import com.iss.itreasury.settlement.setting.dataentity.SettinterestrateplanInfo;
/**
 * @author weilu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SettinterestrateplanBiz
{
	/**
	 *  
	 */
	Sett_interestrateplanDAO dao = null;
	public SettinterestrateplanBiz()
	{
		dao = new Sett_interestrateplanDAO();
	}
	public long saveInterestPlanMain(long lID, long lOfficeID,
			long lInterestPlanTypeID, String strName, String strCode,
			long lInputUserID, Timestamp tsInput,long nCurrencyID) throws SettlementException
	{
		return dao.saveInterestPlanMain(lID, lOfficeID, lInterestPlanTypeID,
				strName, strCode, lInputUserID, tsInput,nCurrencyID);
	}
	public long saveInterestPlanItem(long lInterestRatePlanID,
			long lInterestPlanTypeID, long[] lAryDayType, long[] lAryDayCount,
			long[] lAryBalanceType, double[] dAryBalance,
			Timestamp[] tsAryDateStart, Timestamp[] tsAryDateEnd,
			long[] lAryInterestRateID, double[] dAryInterestRate)
			throws SettlementException
	{
		return dao.saveInterestPlanItem(lInterestRatePlanID,
				lInterestPlanTypeID, lAryDayType, lAryDayCount,
				lAryBalanceType, dAryBalance, tsAryDateStart, tsAryDateEnd,
				lAryInterestRateID, dAryInterestRate);
	}
	public long deleteInterestRatePlan(long lID) throws SettlementException
	{
		return dao.deleteInterestRatePlan(lID);
	}
	public String getNewInterestPlanCode(long lOfficeID) throws SettlementException
	{
		return dao.getNewInterestPlanCode(lOfficeID);
	}
	public SettinterestrateplanInfo findInterestRatePlanByID(long lID) throws SettlementException 
	{
		return dao.findInterestRatePlanByID(lID);
	}
	public Collection findInterestRatePlanItemByInterestRatePlanID(long lInterestRatePlanID) throws SettlementException
	{
		return dao.findInterestRatePlanItemByInterestRatePlanID(lInterestRatePlanID);
	}
	public Collection findAllInterestPlan(long lOfficeID,long nCurrencyID, long lIDStart, long lIDEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws SettlementException
	{
		return dao.findAllInterestPlan( lOfficeID,nCurrencyID,  lIDStart,  lIDEnd,  lPageLineCount,  lPageNo,  lOrderParam,  lDesc);
	}
	public String getNewInterestPlanCode(long lOfficeID,long lCurrencyID) throws SettlementException
	{
		return dao.getNewInterestPlanCode(lOfficeID,lCurrencyID); 
	}
}
