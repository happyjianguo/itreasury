package com.iss.itreasury.fcinterface.bankportal.bankcode.dao;
/**
 * fszhu
 * 2008-11-27
 */
import java.util.Collection;

import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.AreaCodeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.util.IException;

public interface AreaCodeDAO extends BaseDAO
{
	public static final String tableName = "bankareacode";
	
	public static boolean isNeedPrefix = true;
	
	public AreaCodeInfo[] findByCondition (AreaCodeInfo parmaInfo) throws SystemException;
	
	public Collection findProvince() throws Exception ;
	
	public Collection findCityByProvinceName(String provinceName) throws Exception;
	
	/**
	 * 查找地区编码，通过地区码信息
	 * @param paramInfo
	 * @return String[]
	 * @throws SystemException
	 */
	public String[] findAreaCodeByCondition (AreaCodeInfo paramInfo)throws SystemException,IException ;
	
	public Collection  findEquelAreaCodeByCondition(AreaCodeInfo paramInfo)throws SystemException;

}
 