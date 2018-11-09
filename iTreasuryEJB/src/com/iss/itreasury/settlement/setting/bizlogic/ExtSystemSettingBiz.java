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
					throw new Exception("�ⲿϵͳ����Ѵ��ڣ����������룡");
				}
				if(info.getSName().equals(infoExist.getSName()))
				{
					throw new Exception("�ⲿϵͳ�����Ѵ��ڣ����������룡");
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
					throw new Exception("�ⲿϵͳ���Ϊϵͳ�ڲ���ţ����������룡");
				}
				if(info.getSName().equals(infoOrginal.getSName()))
				{
					throw new Exception("�ⲿϵͳ����Ϊϵͳ�ڲ����ƣ����������룡");
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
					throw new Exception("�ⲿϵͳ�����Ѵ��ڣ����������룡");
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
					throw new Exception("�ⲿϵͳ����Ϊϵͳ�ڲ����ƣ����������룡");
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
		boolean isAllowDelete = true;  //�Ƿ�����ɾ��
		isAllowDelete = dao.isAllowDelete(info);
		if(isAllowDelete==false)
		{
			throw new Exception("�ⲿϵͳ�����ѱ�ʹ�ã�ɾ��ʧ�ܣ�");
			
		}
		dao.update(info);
		flag = 1;
		return flag;
	}
	

	


}
