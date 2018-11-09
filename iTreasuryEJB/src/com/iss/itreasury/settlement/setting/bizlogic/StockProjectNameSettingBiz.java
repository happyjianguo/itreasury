/*
 * Created on 2005-1-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dao.Sett_ReleaseAmountLimitSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_StockProjectNameSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_StockProjectSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.StockProjectNameSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StockProjectNameSettingBiz
{
	/**
	 * 
	 */
	Sett_StockProjectNameSettingDAO dao = new Sett_StockProjectNameSettingDAO();
	public StockProjectNameSettingBiz()
	{
		super();
	}
	/**
	 * ������Ŀ����(��Ŀ���Ʋ����ظ�)
	 * @param info
	 * @return
	 * @throws IException
	 */
	public void save(StockProjectNameSettingInfo info) throws IException
	{
		if (info.getId() > 0)
		{
			//�����жϴ˼�¼�Ƿ��޸Ĺ�
			boolean isTouch = false;
			try
			{
				isTouch = dao.isTouch(info);
			}catch(Exception e)
			{
				e.printStackTrace();
				e.printStackTrace();throw new IException("Gen_E001");
			}
			if (isTouch)
			{
				throw new IException("����Ŀ�����Ѿ��������޸Ĺ�!");
			}
		}
		
		info.setModifyDate(com.iss.itreasury.util.Env.getSystemDateTime());//д���޸�ʱ��
		//�ȼ����Ŀ�����Ƿ����
		StockProjectNameSettingInfo tmpInfo = new StockProjectNameSettingInfo();
		tmpInfo.setProjectName(info.getProjectName());
		tmpInfo.setProjectType(info.getProjectType());
		tmpInfo.setStatusID(Constant.RecordStatus.VALID);
		tmpInfo.setCurrencyID(info.getCurrencyID());
		tmpInfo.setOfficeID(info.getOfficeID());
		try
		{
			Collection c = dao.findByCondition(tmpInfo);
			if (c!=null && c.size() >= 1)
			{
				if (c.size() > 1)
				{
					throw new IException("����Ŀ�����Ѿ�����,������¼��!");
				}
				else
				{
					if (((StockProjectNameSettingInfo)c.iterator().next()).getId() != info.getId())
						throw new IException("����Ŀ�����Ѿ�����,������¼��!");
				}
			}
			if (info.getId() > 0)//�޸�
			{
				dao.update(info);
			}
			else//����
			{
				dao.add(info);
			}
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
	}
	
	/**
	 * ɾ����Ŀ����(ͬʱҲɾ������Ŀ�����µ���Ŀ����)
	 * @param id
	 * @throws IException
	 */
	public void delete(StockProjectNameSettingInfo info) throws IException
	{
		//�����жϴ˼�¼�Ƿ��޸Ĺ�
		boolean isTouch = false;
		try
		{
			isTouch = dao.isTouch(info);
		}catch(Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
		if (isTouch)
		{
			throw new IException("����Ŀ�����Ѿ��������޸Ĺ�!");
		}
		
		Sett_StockProjectSettingDAO proDao = new Sett_StockProjectSettingDAO();
		Sett_ReleaseAmountLimitSettingDAO limitDao = new Sett_ReleaseAmountLimitSettingDAO();
		
		//������Ŀ�����Ƿ��Ѿ����ù��ʲ���ծ���������Ŀ
		Collection c = null;
		try
		{
			c = proDao.findByProjectID(info.getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
		
		if (c != null && c.size() > 0)
		{
			throw new IException("����Ŀ���ʲ���ծ���������Ŀ�����ù�,����ɾ����Ŀ����!");
		}
		//������Ŀ�Ƿ��Ѿ����ù�������
		c = limitDao.findByProjectID(info.getId());
		if (c != null && c.size() > 0)
		{
			throw new IException("�Ѿ�Ϊ����Ŀ�����˻�����,����ɾ��������!");
		}
		
		try
		{
			dao.update(info);	//ɾ������Ŀ����
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
	}
	
	/**
	 * ����ID��ѯ��Ŀ������Ϣ
	 * @param id
	 * @return
	 * @throws IException
	 */
	public ITreasuryBaseDataEntity findByID(long id) throws IException
	{
		try
		{
			return dao.findByID(id,(new StockProjectNameSettingInfo()).getClass());
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
	}
	
	/**
	 * ��ѯ����״̬��������Ŀ����
	 * @return
	 * @throws IException
	 */
	public Collection findAllList() throws IException
	{
		StockProjectNameSettingInfo info = new StockProjectNameSettingInfo();
		info.setStatusID(com.iss.itreasury.util.Constant.RecordStatus.VALID);
		try
		{
			return dao.findByCondition(info);
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
	}
	
	public static void main(String[] args)
	{
		StockProjectNameSettingBiz biz = new StockProjectNameSettingBiz();
		StockProjectNameSettingInfo info = new StockProjectNameSettingInfo();
		info.setId(2);
		info.setCurrencyID(1);
		info.setOfficeID(1);
		info.setProjectName("test");
		info.setModifyDate(Timestamp.valueOf("2005-01-18 10:58:01"));
		info.setProjectType(1);
		info.setModifyUserID(28);
		try
		{
			biz.save(info);
		} catch (IException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
