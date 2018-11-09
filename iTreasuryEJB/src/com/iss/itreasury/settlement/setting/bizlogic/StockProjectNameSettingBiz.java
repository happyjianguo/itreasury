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
	 * 保存项目名称(项目名称不能重复)
	 * @param info
	 * @return
	 * @throws IException
	 */
	public void save(StockProjectNameSettingInfo info) throws IException
	{
		if (info.getId() > 0)
		{
			//首先判断此记录是否被修改过
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
				throw new IException("此项目名称已经被他人修改过!");
			}
		}
		
		info.setModifyDate(com.iss.itreasury.util.Env.getSystemDateTime());//写入修改时间
		//先检查项目名称是否可用
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
					throw new IException("此项目名称已经存在,请重新录入!");
				}
				else
				{
					if (((StockProjectNameSettingInfo)c.iterator().next()).getId() != info.getId())
						throw new IException("此项目名称已经存在,请重新录入!");
				}
			}
			if (info.getId() > 0)//修改
			{
				dao.update(info);
			}
			else//新增
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
	 * 删除项目名称(同时也删除此项目名称下的项目设置)
	 * @param id
	 * @throws IException
	 */
	public void delete(StockProjectNameSettingInfo info) throws IException
	{
		//首先判断此记录是否被修改过
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
			throw new IException("此项目名称已经被他人修改过!");
		}
		
		Sett_StockProjectSettingDAO proDao = new Sett_StockProjectSettingDAO();
		Sett_ReleaseAmountLimitSettingDAO limitDao = new Sett_ReleaseAmountLimitSettingDAO();
		
		//检查此项目名称是否已经设置过资产负债或损益表项目
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
			throw new IException("此项目在资产负债或损益表项目中设置过,请先删除项目设置!");
		}
		//检查此项目是否已经设置过豁免额度
		c = limitDao.findByProjectID(info.getId());
		if (c != null && c.size() > 0)
		{
			throw new IException("已经为此项目设置了豁免额度,请先删除豁免额度!");
		}
		
		try
		{
			dao.update(info);	//删除此项目名称
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 根据ID查询项目名称信息
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
	 * 查询正常状态的所有项目名称
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
