/*
 * Created on 2003-12-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.report.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.util.Collection;

public class ReportContractInfo
{
	private ReportContractDetailInfo RCDInfo = null; //合同信息
	private ReportInterestInfo RIInfo = null;//利息情况
	private Collection CorpusList = null; //贷款本金发放，收回情况
	private Collection RepayList = null; //贷款利息，费用收回情况
	private Collection RateList = null; //利率调整情况
	private Collection ExtendList = null; //展期情况
	private Collection FreeList = null; //免还情况
	/**
	 * @return
	 */
	public Collection getExtendList()
	{
		return ExtendList;
	}

	/**
	 * @return
	 */
	public Collection getFreeList()
	{
		return FreeList;
	}

	/**
	 * @return
	 */
	public Collection getRateList()
	{
		return RateList;
	}

	/**
	 * @return
	 */
	public ReportContractDetailInfo getRCDInfo()
	{
		return RCDInfo;
	}

	/**
	 * @return
	 */
	public Collection getRepayList()
	{
		return RepayList;
	}

	/**
	 * @param collection
	 */
	public void setExtendList(Collection collection)
	{
		ExtendList = collection;
	}

	/**
	 * @param collection
	 */
	public void setFreeList(Collection collection)
	{
		FreeList = collection;
	}

	/**
	 * @param collection
	 */
	public void setRateList(Collection collection)
	{
		RateList = collection;
	}

	/**
	 * @param info
	 */
	public void setRCDInfo(ReportContractDetailInfo info)
	{
		RCDInfo = info;
	}

	/**
	 * @param collection
	 */
	public void setRepayList(Collection collection)
	{
		RepayList = collection;
	}

	/**
	 * @return
	 */
	public Collection getCorpusList()
	{
		return CorpusList;
	}

	/**
	 * @param collection
	 */
	public void setCorpusList(Collection collection)
	{
		CorpusList = collection;
	}

	/**
	 * @return
	 */
	public ReportInterestInfo getRIInfo()
	{
		return RIInfo;
	}

	/**
	 * @param info
	 */
	public void setRIInfo(ReportInterestInfo info)
	{
		RIInfo = info;
	}

}
