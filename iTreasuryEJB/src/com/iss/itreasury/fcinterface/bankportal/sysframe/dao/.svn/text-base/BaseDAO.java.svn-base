/*
 * Created on 2005-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.sysframe.dao;

import java.util.Collection;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface BaseDAO
{
	public long geSequenceID() throws SystemException;
	public long getMaxID() throws SystemException;

	/**
	 * 数据库新增操作，新增的ID必须在子类set入正确的数值
	 * 
	 * @param dataEntity
	 *            需要被插入数据库表对应的Data Entity的实例
	 * @param
	 * @return 新产生的ID
	 * @throws ITreasuryDAOException
	 */
	public long add(BaseDataEntity dataEntity) throws SystemException;

	/**
	 * 数据库更新操作
	 * 
	 * @param dataEntity 需要被更新的数据库表对应的Data Entity的实例
	 * @param
	 * @return @throws ITreasuryDAOException
	 */
	public void update(BaseDataEntity dataEntity) throws SystemException;
	
	/**
	 * 数据库查找操作
	 * @param id　　　
	 * @param className 需要查找的数据库表对应的Data Entity的类名
	 * @return
	 * @throws ITreasuryDAOException
	 */		
	public BaseDataEntity findByID(long id, Class className) throws SystemException;

	/**
	 * 数据库查找操作
	 * @param conditionInfo 查询条件，如果被set过值，则认为该字段为查询条件进行匹配　　　
	 * @param className 需要查找的数据库表对应的Data Entity的类名
	 * @param orderByString order by SQL语句，手工添加
	 * @return
	 * @throws ITreasuryDAOException
	 */			
	public Collection findByCondition(BaseDataEntity conditionInfo, String orderByString) throws SystemException;
	
	/**
	 * 查找数据库中的所有记录
	 * @param orderByString
	 * @return
	 * @throws SystemException
	 */
	public Collection findAll(Class baseDataEntityClass, String orderByString) throws SystemException;
	
	/**
	 * 从数据库中根据id物理删除一条记录
	 * 使用时请确定是逻辑删除还是物理删除！！
	 * */
	public void deletePhysically(long id) throws SystemException;
	
	/**
	 * 删除数据库中所有的记录
	 * @throws SystemException
	 */
	public void deleteAll() throws SystemException;
}
