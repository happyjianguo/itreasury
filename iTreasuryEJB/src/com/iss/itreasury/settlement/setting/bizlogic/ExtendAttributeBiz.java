/*
 * Created on 2004-11-16
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_ExtendAttributeDAO;
import com.iss.itreasury.settlement.setting.dataentity.ExtendAttributeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 * 扩展属性操作(操作表sett_IndustryType)
 */
public class ExtendAttributeBiz
{
	Sett_ExtendAttributeDAO dao = null;
	public ExtendAttributeBiz()
	{
		dao = new Sett_ExtendAttributeDAO();
	}
	 /**
     * 新增或修改扩展属性:根据info里的ID,如果大于-1则是修改,否则新增
     * @param info
     * @return 新增或修改的扩展属性ID,0表示属性名已存在
     * @throws SettlementException
     */
	public long save(ExtendAttributeInfo info) throws SettlementException
	{
		long returnLong = -1;
		try
		{
			//先根据扩展属性名称判断此属性名称是否已经存在
			ExtendAttributeInfo tmpInfo = new ExtendAttributeInfo();
			tmpInfo.setAttributeID(info.getAttributeID());
			tmpInfo.setName(info.getName());
			tmpInfo.setOfficeID(info.getOfficeID());
			tmpInfo.setStatus(Constant.RecordStatus.VALID);
			Collection c = dao.findByCondition(tmpInfo);
			if (c!=null && c.size()>0)
			{
				//如果是新增操作,直接抛出异常
				if (info.getId()==-1)
				{
					return 0;
				}
				else
				{
					//如果查出的信息的ID不是要修改的此条信息
					if (((ExtendAttributeInfo)c.iterator().next()).getId()!=info.getId())
					{
						Log.print("已经存在此扩展属性名称====ID:"+info.getId());
						Log.print("需要修改的====ID:"+((ExtendAttributeInfo)c.iterator().next()).getId());
						return 0;
					}
				}
			}
			//判断结束
				
			if (info.getId()!=-1)//更新操作
			{
				dao.update(info);
				returnLong = info.getId();
			}
			else//新增
			{
				returnLong = dao.add(info);
			}
		}catch(ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		finally
		{
			try
			{
				dao.clearDAO();
			} catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
				throw new SettlementException();
			}
		}
		return returnLong;
	}
	 /**
     * 删除扩展属性(更新其状态)
     * @param id
     * @throws SettlementException
     */
	public void delete(long id) throws SettlementException
	{
		ExtendAttributeInfo info = new ExtendAttributeInfo();
		info.setId(id);
		info.setStatus(Constant.RecordStatus.INVALID);
		try
		{
			dao.update(info);
		} catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		finally
		{
			try
			{
				dao.clearDAO();
			} catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
				throw new SettlementException();
			}
		}
	}
	/**
     * 根据扩展属性类型查询扩展属性
     * @param lAttributeTypeID
     * @return
     * @throws SettlementException
     */
	public Collection findByAttrID(long lAttributeID,long lOfficeID) throws SettlementException
	{
		ExtendAttributeInfo info = new ExtendAttributeInfo();
		info.setAttributeID(lAttributeID);
		info.setOfficeID(lOfficeID);
		info.setStatus(Constant.RecordStatus.VALID);
		try
		{
			return dao.findByCondition(info,"order by dtInput desc");
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		finally
		{
			try
			{
				dao.clearDAO();
			} catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
				throw new SettlementException();
			}
		}
	}
	/**
     * 根据扩展属性ID得到属性信息
     * @param id
     * @return
     * @throws SettlementException
     */
	public ExtendAttributeInfo findByID(long id) throws SettlementException
	{
		try
		{
			return (ExtendAttributeInfo)dao.findByID(id,(new ExtendAttributeInfo()).getClass());
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		finally
		{
			try
			{
				dao.clearDAO();
			} catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
				throw new SettlementException();
			}
		}
	}
}
