/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.bizdelegation;

import com.iss.itreasury.loan.credit.bizlogic.*;
import com.iss.itreasury.loan.actiontrace.bizlogic.*;
import com.iss.itreasury.loan.credit.dataentity.*;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.IException;
import java.util.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditDelegation
{
	private CreditBean bizBean = new CreditBean();
	public CreditProductInfo findProductInfoByLoanType(long loanTypeID) throws Exception
	{
		return bizBean.findProductInfoByLoanType(loanTypeID);
	}

	public CreditProductInfo findProductInfoByCreditType(long CreditTypeID) throws Exception
	{
		return bizBean.findProductInfoByCreditTypeID(CreditTypeID);
	}

	public long saveProductInfo(CreditProductInfo info) throws Exception
	{
		return bizBean.saveProductInfo(info);
	}

	public void deleteProductInfo(long id) throws Exception,IException
	{
		bizBean.deleteProductInfo(id);
	}

	public void deleteProductInfoByCreditTypeID(long CreditTypeID) throws Exception
	{
		bizBean.deleteProductInfoByCreditTypeID(CreditTypeID);
	}

	public Collection findLimitInfoByCondition(CreditLimitQueryInfo qInfo) throws Exception
	{
		return bizBean.findLimitInfoByCondition(qInfo);
	}

	public long saveLimitInfo(CreditLimitInfo info) throws Exception,IException
	{
		return bizBean.saveLimitInfo(info);
	}

	public void deleteLimitInfo(long id, long lInputUserID) throws Exception
	{
		bizBean.deleteLimitInfo(id,lInputUserID);
	}

	public CreditLimitInfo findLimitInfoByID(long id) throws Exception
	{
		return bizBean.findLimitInfoByID(id);
	}

	public void cancelLimitInfoByID(long id) throws Exception
	{
		bizBean.cancelLimitInfoByID(id);
	}

	public void active(long[] id,long lInputUserID) throws Exception
	{
		bizBean.active(id,lInputUserID);
	}

	public void cancelActive(long[] id, long lInputUserID) throws Exception
	{
		bizBean.cancelActive(id,lInputUserID);
	}

	public void freeze(long[] id, long lInputUserID) throws Exception
	{
		bizBean.freeze(id,lInputUserID);
	}

	public void cancelFreeze(long[] id, long lInputUserID) throws Exception
	{
		bizBean.cancelFreeze(id,lInputUserID);
	}

	public CreditInfo findCreditInfo(long clientID, long loanTypeID) throws Exception
	{
		return bizBean.findCreditInfo(clientID, loanTypeID);
	}
	
	public Collection findApplyDetail(long clientID) throws Exception
	{
		return bizBean.findApplyDetail( clientID );
	}

	public Collection findUseDetail(long clientID) throws Exception
	{
		return bizBean.findUseDetail( clientID );
	}	
	
	public Collection findTraceByLimitID(long lID) throws Exception
	{
		ActionTraceBean traceBean = new ActionTraceBean();
		return traceBean.findTraceHistory(Constant.ModuleType.LOAN,LOANConstant.TraceType.CREDIT,lID);
	
	}
}
