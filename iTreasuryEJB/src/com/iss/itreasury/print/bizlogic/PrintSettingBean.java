/*
 * Created on 2004-12-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.print.bizlogic;

import com.iss.itreasury.print.dao.sett_PrintSettingDAO;
import com.iss.itreasury.print.dataentity.PrintSettingInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.SessionMng;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSettingBean
{
	
	sett_PrintSettingDAO settingDAO = new sett_PrintSettingDAO();
	
	/**
	 * 查询打印设置信息
	 * 如果没有该办事处、币种、模块的信息则新增一条记录
	 * @param sessionMng
	 * @return PrintSettingInfo
	 * @throws Exception
	 */
	public PrintSettingInfo queryPrintSettingInfo(SessionMng sessionMng) throws Exception
	{
		PrintSettingInfo printSettingInfo = new PrintSettingInfo();
		try
		{
			long lID = -1;
			lID = settingDAO.findSettingID(sessionMng);
			
			if(lID > 0)
			{
				printSettingInfo = (PrintSettingInfo)settingDAO.findByID(lID,printSettingInfo.getClass());
			}
			else
			{
				//如果没有设置记录，则插入一条新记录
				printSettingInfo.setOffice(sessionMng.m_lOfficeID);
				printSettingInfo.setCurrency(sessionMng.m_lCurrencyID);
				printSettingInfo.setModuleType(sessionMng.m_lModuleID);
				settingDAO.setUseMaxID();
				lID = settingDAO.add(printSettingInfo);
				//并且查出这条记录的设置信息
				printSettingInfo = (PrintSettingInfo)settingDAO.findByID(lID,printSettingInfo.getClass());
			}
			return printSettingInfo;
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 更新设置
	 * @param printSettingInfo
	 * @throws Exception
	 */
	public void updatePageSettingInfo (PrintSettingInfo printSettingInfo) throws Exception
	{
		try
		{
			settingDAO.update(printSettingInfo);
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
	}
}
