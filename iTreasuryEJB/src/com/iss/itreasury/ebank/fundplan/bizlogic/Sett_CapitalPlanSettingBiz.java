package com.iss.itreasury.ebank.fundplan.bizlogic;

import com.iss.itreasury.ebank.fundplan.dao.Sett_CapitalPlanSettingDAO;
import com.iss.itreasury.ebank.fundplan.dataentity.Sett_CapitalPlanSettingInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class Sett_CapitalPlanSettingBiz {

	private Sett_CapitalPlanSettingDAO sett_CapitalPlanSettingDAO = null;
	
	public Sett_CapitalPlanSettingBiz()
	{
		sett_CapitalPlanSettingDAO = new Sett_CapitalPlanSettingDAO();
	}
	
	public PageLoader getCapitalPlanByCondition(Sett_CapitalPlanSettingInfo sett_CapitalPlanSettingInfo)throws Exception
	{
		return sett_CapitalPlanSettingDAO.getCapitalPlanByCondition(sett_CapitalPlanSettingInfo);
	}
	
	public String getCapitalPlanByConditionOrderParam(long lDesc,long lOrderParam)throws Exception
	{
		return sett_CapitalPlanSettingDAO.getCapitalPlanByConditionOrderParam(lDesc,lOrderParam);
	}
	
	public long add(Sett_CapitalPlanSettingInfo sett_CapitalPlanSettingInfo)throws Exception
	{
		long returnID = -1;
		
		if(sett_CapitalPlanSettingInfo.getID() > 0)
		{
			returnID = sett_CapitalPlanSettingDAO.doUpdate(sett_CapitalPlanSettingInfo.getID(),sett_CapitalPlanSettingInfo.getIsCheckPlan());
		}
		else
		{
			if(sett_CapitalPlanSettingDAO.isClientExist(sett_CapitalPlanSettingInfo.getClientID()))
			{
			   throw new IException("客户已存在不能进行保存！");
			}
			returnID = sett_CapitalPlanSettingDAO.doInsert(sett_CapitalPlanSettingInfo);
		}
		
		return returnID;
	}
	public Sett_CapitalPlanSettingInfo findByID(long ID)throws Exception
	{
		return sett_CapitalPlanSettingDAO.findByID(ID);
	}
	
	public boolean isAutoCheck(long nClientID)throws Exception
	{
		return sett_CapitalPlanSettingDAO.isAutoCheck(nClientID);
	}
}
