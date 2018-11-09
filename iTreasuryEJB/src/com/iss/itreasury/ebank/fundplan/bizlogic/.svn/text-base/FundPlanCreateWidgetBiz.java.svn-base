/**
 * jlzhang
 * Oct 14, 2008
 */
package com.iss.itreasury.ebank.fundplan.bizlogic;

import java.util.List;

import com.iss.itreasury.ebank.fundplan.dao.FundPlanSubmitWidgetDao;
import com.iss.itreasury.ebank.fundplan.dao.SubCapitalPlanDao;
import com.iss.itreasury.ebank.fundplan.model.FundPlanParamInfo;

/**
 * @author xintan
 *
 */
public class FundPlanCreateWidgetBiz {
	
	private FundPlanParamInfo paramInfo = null;
	
	public FundPlanCreateWidgetBiz(FundPlanParamInfo paramInfo){
		this.paramInfo = paramInfo;
	}
	
	/*
	 * desc:在标签中构造表格时使用，根据级别码获取项目列表个数
	 * @param levelcode 一级项目的级别码
	 * @return 数量
	 * */
	public int getRowSpan(String levelcode) throws Exception
	{
		FundPlanSubmitWidgetDao dao = new FundPlanSubmitWidgetDao();
		return dao.getRowSpan(levelcode, paramInfo.getModelId(), paramInfo.getOffice(), paramInfo.getCurrency());
	}
	

	/**
	 * 获取模板显示的项和内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getCapitalPlanContentList() throws Exception
	{
		if("submit".equalsIgnoreCase((paramInfo.getType())))
		{
			//申报和审批
			FundPlanSubmitWidgetDao dao = new FundPlanSubmitWidgetDao();
			return dao.getCapitalPlanContentList(paramInfo);	
			
		}else if("total".equalsIgnoreCase(paramInfo.getType())) {
			//汇总
			SubCapitalPlanDao dao = new SubCapitalPlanDao();
			return dao.findByStartdate(paramInfo.getDateFrom());
		}
		
		return null;
	}
}