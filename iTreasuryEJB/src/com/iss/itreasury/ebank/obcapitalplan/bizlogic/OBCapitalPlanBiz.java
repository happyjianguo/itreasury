/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obcapitalplan.bizlogic;

import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obcapitalplan.dao.OBCapitalPlanDao;
import com.iss.itreasury.ebank.obcapitalplan.dataentity.OBCapitalPlanInfo;
import com.iss.itreasury.ebank.util.OBConstant;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author yangliu
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBCapitalPlanBiz
{
	public OBCapitalPlanBiz()
	{
	}

	/**
	 * �½����޸ļ�¼
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long save(OBCapitalPlanInfo info) throws IException
	{
		long retlong = -1;
		try
		{
			// ��ע����ʼ���ڵ������ڵĿ�ʼ����+���ڵı���
			// ��������=��ʼ����+����
			// ���ж�ʱ�������Ƿ��е��ӣ�����������½����޸�
			OBCapitalPlanDao dao = new OBCapitalPlanDao();
			//OBCapitalPlanInfo info1 = new OBCapitalPlanInfo();

//			if (info.getId() != -1)
//			{
//				info1 = dao.findByID(info.getId(), info.getClientID());
//			}
			if (info.getId() == -1)
			{
				info.setStatusID(1);
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				String ls = df.format(Env.getSystemDateTime());
				info.setInputDate(DataFormat.getDateTime(ls));
				// info.setEndDate(nextDate);
				info.setPeriodID(dao.getId());
				retlong = dao.add(info);
			} else
			{
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				String ls = df.format(Env.getSystemDateTime());
				info.setModifyDate(DataFormat.getDateTime(ls));
				dao.update(info);
				retlong = info.getId();
			}

		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new IException("Gen_E001");
		}
		return retlong;
	}

	/**
	 * ����ID��ѯ��¼��ϸ��Ϣ
	 * 
	 * @param id
	 * @return
	 * @throws IException
	 */
	public OBCapitalPlanInfo findById(long id, long ClientID) throws IException
	{
		OBCapitalPlanInfo info = null;
		try
		{
			OBCapitalPlanDao dao = new OBCapitalPlanDao();
			info = (OBCapitalPlanInfo) dao.findByID(id, ClientID);
		} catch (IException ie)
		{
			throw new IException("Gen_E001");
		}
		return info;
	}

	/**
	 * ���ݲ�ѯ������ѯ��¼
	 * 
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection queryByCondition(OBCapitalPlanInfo info)
			throws IException
	{
		Collection coll = null;
		try
		{
			// info.setStatusID(1);//д��ʽ�����ʱ��Ҫ�ñ�����ʾ��Ч
			OBCapitalPlanDao dao = new OBCapitalPlanDao();
			coll = dao.queryByCondition(info);
		} catch (IException ie)
		{
			throw new IException("Gen_E001");
		}
		return coll;
	}

	/**
	 * ���ݼ�¼IDɾ����¼
	 * 
	 * @param id
	 * @return
	 * @throws IException
	 */
	public long deleteById(long id) throws IException
	{
		long retlong = -1;
		OBCapitalPlanInfo info = new OBCapitalPlanInfo();
		OBCapitalPlanDao dao = new OBCapitalPlanDao();
		info.setId(id);
		info.setStatusID(OBConstant.OBCapitalPlan.DEL);
		retlong = dao.deleteById(info);
		return retlong;
	}

	/**
	 * ���ݼ�¼IDɾ����¼
	 * 
	 * @param id
	 * @throws IException
	 */
	public void cancelCheck(OBCapitalPlanInfo info) throws IException
	{
		try
		{
			OBCapitalPlanDao dao = new OBCapitalPlanDao();
			dao.update(info);
		} catch (IException ie)
		{
			throw new IException("Gen_E001");
		}
	}

	/**
	 * ����
	 * 
	 * @param OBCapitalPlanInfo
	 * @return
	 * @throws IException
	 */
	public void check(OBCapitalPlanInfo info) throws IException
	{
		try
		{
			OBCapitalPlanDao dao = new OBCapitalPlanDao();
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			String ls = df.format(Env.getSystemDateTime());
			info.setCheckUserDate(DataFormat.getDateTime(ls));
			System.out.println(info);
			dao.update(info);
		} catch (IException ie)
		{
			ie.printStackTrace();
			throw new IException("Gen_E001");
		}
	}

	public Collection querySettByCondition(OBCapitalPlanInfo info)
			throws IException
	{
		Collection coll = null;
		try
		{
			// info.setStatusID(1);//д��ʽ�����ʱ��Ҫ�ñ�����ʾ��Ч
			OBCapitalPlanDao dao = new OBCapitalPlanDao();
			coll = dao.querySettByCondition(info);
		} catch (IException ie)
		{
			throw new IException("Gen_E001");
		}
		return coll;
	}

	public boolean isDate(long lID,Timestamp thisDate, Timestamp nextDate, long ClientID)
			throws IException
	{
		OBCapitalPlanDao dao = new OBCapitalPlanDao();
		return dao.isDate(lID, thisDate, nextDate, ClientID);
	}


	public static void main(String[] args) throws IException
	{
		OBCapitalPlanBiz biz = new OBCapitalPlanBiz();
		OBCapitalPlanInfo info = new OBCapitalPlanInfo();
		info.setId(1);
		info.setCheckUserID(2);
		info.setStatusID(OBConstant.OBCapitalPlan.CHECK);
		biz.check(info);
	}

}
