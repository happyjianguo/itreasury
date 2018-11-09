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
import com.iss.itreasury.settlement.setting.dao.Sett_StockProjectSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.StockProjectSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StockProjectSettingBiz
{
	
	Sett_StockProjectSettingDAO dao = new Sett_StockProjectSettingDAO();
	public StockProjectSettingBiz()
	{
		super();
	}
	
	/**
	 * 保存项目设置
	 * @param info
	 * @throws IException:如果存在此项目组合抛出异常提示
	 */
	public void save(StockProjectSettingInfo info) throws IException
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
				throw new IException("Gen_E001");
			}
			if (isTouch)
			{
				throw new IException("此项目设置已经被他人修改过!");
			}
		}
		
		info.setModifyDate(com.iss.itreasury.util.Env.getSystemDateTime());//写入修改时间
		//先检查此项目组合是否已经设置过
		StockProjectSettingInfo tmpInfo = new StockProjectSettingInfo();
		tmpInfo.setProjectID(info.getProjectID());
		tmpInfo.setAccountType(info.getAccountType());
		tmpInfo.setContractType(info.getContractType());
		tmpInfo.setRelateClientType(info.getRelateClientType());
		if (info.getProjectType() == SETTConstant.StockProjectType.PROFITANDLOSS)//是损益表项目
			tmpInfo.setProfitAndLossType(info.getProfitAndLossType());
		tmpInfo.setCurrencyID(info.getCurrencyID());
		tmpInfo.setOfficeID(info.getOfficeID());
		tmpInfo.setStatusID(Constant.RecordStatus.VALID);
		try
		{
			Collection c = dao.findByCondition(tmpInfo);
			System.out.println("c.size="+c.size());
			if (c!=null && c.size() >= 1)
			{
				if (c.size() > 1)
				{
					throw new IException("此项目组合已经存在,请重新录入","");
				}
				else
				{
					if (((StockProjectSettingInfo)c.iterator().next()).getId() != info.getId())
						throw new IException("此项目组合已经存在,请重新录入","");
				}
			}
			if (info.getId() > 0)
			{
				dao.update(info);
			}
			else
			{
				dao.add(info);
			}
		}
		catch(ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 逻辑删除项目设置
	 * @param id
	 * @throws IException
	 */
	public void delete(StockProjectSettingInfo info) throws IException
	{
		//首先判断此记录是否被修改过
		boolean isTouch = false;
		try
		{
			isTouch = dao.isTouch(info);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		if (isTouch)
		{
			throw new IException("此项目设置已经被他人修改过!");
		}
		
		try
		{
			dao.update(info);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 根据项目组合ID查询具体设置信息
	 * @param id
	 * @return
	 * @throws IException
	 */
	public ITreasuryBaseDataEntity findByID(long id) throws IException
	{
		try
		{
			return dao.findByID(id,(new StockProjectSettingInfo()).getClass());
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 根据项目名称ID查询此项目的项目设置
	 * @param projectID
	 * @return
	 * @throws IException
	 */
	public Collection findByProjectID(long projectID) throws IException
	{
		StockProjectSettingInfo info = new StockProjectSettingInfo();
		info.setProjectID(projectID);
		info.setStatusID(com.iss.itreasury.util.Constant.RecordStatus.VALID);
		Collection c = null;
		try
		{
			c = dao.findByCondition(info,"order by modifyDate desc");
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return c;
	}
	
	/**
	 * 查询已经设置的项目
	 * @return
	 * @throws IException
	 */
	public Collection findByCondition(StockProjectSettingInfo info) throws IException
	{
		Collection c = null;
		try
		{
			 c = dao.findByProjectCondition(info);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return c;
	}
	public static void main(String[] args) throws IException
	{
		StockProjectSettingInfo info = new StockProjectSettingInfo();
		info.setOfficeID(1);
		info.setCurrencyID(1);
		info.setModifyDate(Timestamp.valueOf("2005-01-19 09:42:36"));
		info.setId(4);
		info.setModifyUserID(28);
		info.setProjectType(1);
		info.setProjectID(1);
		info.setContractType(12);
		info.setAccountType(8);
		info.setRelateClientType(3);
		StockProjectSettingBiz biz = new StockProjectSettingBiz();
		biz.findByCondition(info);
	}
}
