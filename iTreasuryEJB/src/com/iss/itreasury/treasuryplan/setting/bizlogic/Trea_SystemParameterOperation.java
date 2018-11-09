package com.iss.itreasury.treasuryplan.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_SystemParameterDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.SystemParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class Trea_SystemParameterOperation
{
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	private Trea_SystemParameterDAO dao = new Trea_SystemParameterDAO();
	
	/**
	 * ����ϵͳ����
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public long add(SystemParameterInfo info) throws Exception
	{
		long lReturn = -1;
		lReturn = dao.add(info);
		return lReturn;
	}
	
	/**
	 * �޸�ϵͳ����
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public void update(SystemParameterInfo info) throws Exception
	{
		dao.update(info);
		
	}
	/**
	 * ����ϵͳ����
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public long save(SystemParameterInfo info) throws Exception
	{
		long lReturn = -1;
		/*if(info.getId()>0)
		{
			if(info.getParameterValue())
			dao.update(info);			
		}
		else
		{*/			
			log.debug(UtilOperation.dataentityToString(info));
			dao.setUseMaxID();
			lReturn=dao.add(info);
		//}
		return lReturn;
	}
	
	/**
	 * ��ѯϵͳ����������׼����
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Collection search(SystemParameterInfo info) throws Exception
	{
		
		return dao.findByCondition(info,"order by EFFECTIVEDATE desc ");
	}
	
	/**
	 * delete ɾ������ϵͳ����
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long delete(SystemParameterInfo info) throws Exception
	{
		long lReturn = -1;
		info.setStatusID(0);
		log.debug(UtilOperation.dataentityToString(info));		
		dao.update(info);
		return lReturn;
	}
	/**
	 * find ����ϵͳ����
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public SystemParameterInfo find(SystemParameterInfo info) throws Exception
	{
		SystemParameterInfo rtnInfo = null;		
		rtnInfo = dao.find(info);		
		return rtnInfo;
	}
	/**
	 * find ����ϵͳ����(��Ч���������)
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public SystemParameterInfo findLastEffectiveDate(SystemParameterInfo info) throws Exception
	{
		SystemParameterInfo rtnInfo = null;		
		rtnInfo = dao.findLastEffectiveDate(info);		
		return rtnInfo;
	}
	
	
}