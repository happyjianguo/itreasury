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
	 * ��ѯ��ӡ������Ϣ
	 * ���û�иð��´������֡�ģ�����Ϣ������һ����¼
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
				//���û�����ü�¼�������һ���¼�¼
				printSettingInfo.setOffice(sessionMng.m_lOfficeID);
				printSettingInfo.setCurrency(sessionMng.m_lCurrencyID);
				printSettingInfo.setModuleType(sessionMng.m_lModuleID);
				settingDAO.setUseMaxID();
				lID = settingDAO.add(printSettingInfo);
				//���Ҳ��������¼��������Ϣ
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
	 * ��������
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
