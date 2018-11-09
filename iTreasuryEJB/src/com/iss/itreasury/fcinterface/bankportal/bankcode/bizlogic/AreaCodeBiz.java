package com.iss.itreasury.fcinterface.bankportal.bankcode.bizlogic;
/**
 * fszhu
 * 2008-11-27
 * 
 */
import java.util.Collection;

import com.iss.itreasury.fcinterface.bankportal.bankcode.dao.AreaCodeDAO;
import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.AreaCodeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business.BusinessException;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOFactory;
import com.iss.itreasury.fcinterface.bankportal.util.Logger;

public class AreaCodeBiz 
{
	private static Logger log = new Logger(AreaCodeBiz.class);

	/**
	 * 通过AreaCodeInfo 查询符合条件的地区码信息
	 * @param condition
	 * @return AreaCodeInfo[]
	 * @throws BusinessException
	 */
	public AreaCodeInfo[] findByCondition(AreaCodeInfo condition) throws BusinessException
	{
		log.info("Enter AreaCodeBiz.findByCondition(AreaCodeInfo condition)");
		AreaCodeInfo[] result = null;
		AreaCodeDAO areaCodeDAO = null;
		if(condition==null)
		{
			throw new BusinessException("查询条件为空，请重新查询！");
		}
		log.info("查询地区信息条件："+condition);
		try 
		{
			areaCodeDAO = (AreaCodeDAO) DAOFactory.getDAOImpl(AreaCodeDAO.class, null);
			result = areaCodeDAO.findByCondition(condition);
		} catch (SystemException e) 
		{	
			e.printStackTrace();
			throw new BusinessException("查询地区信息出错："+e);
		}
		return result;
	}


	/**
	 * 查询地区编码 通过id
	 * @param id 
	 * @return AreaCodeInfo
	 * @throws BusinessException
	 */
	public AreaCodeInfo findById(long id)throws BusinessException
	{
		log.info("Enter AreaCodeBiz.findById()...");
		log.info("待查询地区信息的id值为："+id);
		AreaCodeInfo result = null;;
		AreaCodeDAO areaCodeDAO = null;;
		try 
		{
			areaCodeDAO = (AreaCodeDAO) DAOFactory.getDAOImpl(AreaCodeDAO.class, null);
			result=(AreaCodeInfo) areaCodeDAO.findByID(id, AreaCodeInfo.class);
		} catch (SystemException e) 
		{
			e.printStackTrace();
			throw new BusinessException("查询地区码信息时出错：" + e);
		}
		return result;
	}


	/**
	 * 查询地区码中的省名称
	 * @return Collection
	 * @throws BusinessException
	 */
	public String[] findAreaCodeProvince() throws Exception
	{
		log.info("Enter AreaCodeBiz.findAreaCodeProvince()...");
		String[] province = null;
		try 
		{
			AreaCodeDAO areaCodeDao = (AreaCodeDAO) DAOFactory.getDAOImpl(AreaCodeDAO.class, null);
			province = (String[]) areaCodeDao.findProvince().toArray(new String[0]);
		} catch (SystemException e) 
		{
			e.printStackTrace();
			throw new BusinessException("查询省名称时出错："+e);
		}
		return  province;
	}



	/**
	 * 通过省名称查询市名称
	 * @param provinceName
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findAreaCodeCityByProvince(String provinceName) throws Exception
	{
		log.info("Enter AreaCodeBiz.findAreaCodeCityByProvince(String provinceName)....");
		log.info("查询条件省名："+provinceName);
		AreaCodeDAO areaCodeDao = null;
		Collection city = null;
		try 
		{
			areaCodeDao = (AreaCodeDAO) DAOFactory.getDAOImpl(AreaCodeDAO.class, null);
			city = areaCodeDao.findCityByProvinceName(provinceName);
		} catch (SystemException e) 
		{
			e.printStackTrace();
			throw new BusinessException("查询市名称时出错："+e);
		}
		return  city;
	}
	/**
	 * 
	 * 通过地区码信息查询地区编码
	 * @param condition
	 * @return
	 * @throws BusinessException
	 */
	public String[] findAreaCodeByCondition(AreaCodeInfo condition) throws Exception
	{
		log.info("Enter AreaCodeBiz.findAreaCodeByCondition(AreaCodeInfo condition)....");
		if(condition==null)
		{
			throw new BusinessException("查询条件为空，请重新查询！");
		}
		String[] result = null;
		AreaCodeDAO areaCodeDao = null;
		try 
		{
			areaCodeDao = (AreaCodeDAO) DAOFactory.getDAOImpl(AreaCodeDAO.class, null);
			result=areaCodeDao.findAreaCodeByCondition(condition);
		} catch (SystemException e) 
		{
			e.printStackTrace();
			throw new BusinessException("查询地区编码时出错："+e.getMessage());
		}
		return result;
	}

}
