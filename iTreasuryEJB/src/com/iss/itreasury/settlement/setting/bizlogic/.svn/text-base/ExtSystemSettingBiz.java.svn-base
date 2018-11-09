package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.ArrayList;
import java.util.Iterator;

import com.iss.itreasury.settlement.setting.dao.ExtSystemSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.ExtSystemSettingInfo;




public class ExtSystemSettingBiz {
	
	public long addExtSystemSetting(ExtSystemSettingInfo info)throws Exception
	{
		long flag = -1;
		Iterator it = null;
		ExtSystemSettingDAO dao = new ExtSystemSettingDAO();
		ArrayList list = new ArrayList();
		ExtSystemSettingInfo infoExist = new ExtSystemSettingInfo();
		ExtSystemSettingInfo infoOrginal = new ExtSystemSettingInfo();
		list = dao.findExtSystemInformation();
		if(list!=null)
		{
			it = list.iterator();
			while(it.hasNext())
			{
				infoExist =(ExtSystemSettingInfo) it.next();
				if(info.getSCode().equals(infoExist.getSCode()))
				{
					throw new Exception("外部系统编号已存在，请重新输入！");
				}
				if(info.getSName().equals(infoExist.getSName()))
				{
					throw new Exception("外部系统名称已存在，请重新输入！");
				}
			}
		}
		
		list = dao.findExtSystemOrginalInformation();
		if(list!=null)
		{
			it = list.iterator();
			while(it.hasNext())
			{
				infoOrginal = (ExtSystemSettingInfo)it.next();
				if(info.getSCode().equals(infoOrginal.getSCode()))
				{
					throw new Exception("外部系统编号为系统内部编号，请重新输入！");
				}
				if(info.getSName().equals(infoOrginal.getSName()))
				{
					throw new Exception("外部系统名称为系统内部名称，请重新输入！");
				}
			}
		}

		dao.add(info);
		flag = 1;
		
		
		return flag;
			
	}

	
	public ExtSystemSettingInfo findExtSystemSettingByID(long id)throws Exception
	{
		ExtSystemSettingInfo info = null;
		ExtSystemSettingDAO dao = null;
		try
		{
			info = new ExtSystemSettingInfo();
			dao = new ExtSystemSettingDAO();
			info=(ExtSystemSettingInfo)dao.findByID(id, ExtSystemSettingInfo.class);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return info;
		
	}
	
	public long updateExtSystemSetting(ExtSystemSettingInfo info)throws Exception
	{
		long flag = -1;
		Iterator it =null;
		ExtSystemSettingDAO dao = new ExtSystemSettingDAO();
		ExtSystemSettingInfo infoExist = new ExtSystemSettingInfo();
		ExtSystemSettingInfo infoOrginal = new ExtSystemSettingInfo();
		ArrayList list = new ArrayList();
		list = dao.findExtSystemInformationExcept(info.getId());
		if(list!=null)
		{
			it = list.iterator();
			while(it.hasNext())
			{
				infoExist = (ExtSystemSettingInfo)it.next();
				if(info.getSName().equals(infoExist.getSName()))
				{
					throw new Exception("外部系统名称已存在，请重新输入！");
				}

			}
		}
		
		list = dao.findExtSystemOrginalInformation();
		if(list!=null)
		{
			it = list.iterator();
			while(it.hasNext())
			{
				infoOrginal = (ExtSystemSettingInfo)it.next();

				if(info.getSName().equals(infoOrginal.getSName()))
				{
					throw new Exception("外部系统名称为系统内部名称，请重新输入！");
				}
			}
		}



		dao.update(info);
		flag = 1;
		return flag;
		
	
	}
	
	public long deleteExtSystemSetting(ExtSystemSettingInfo info)throws Exception
	{
		long flag = -1;
		ExtSystemSettingDAO dao = new ExtSystemSettingDAO();
		boolean isAllowDelete = true;  //是否允许删除
		isAllowDelete = dao.isAllowDelete(info);
		if(isAllowDelete==false)
		{
			throw new Exception("外部系统数据已被使用！删除失败！");
			
		}
		dao.update(info);
		flag = 1;
		return flag;
	}
	

	


}
