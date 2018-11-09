/*
 * Created on 2005-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.sysframe.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BaseDAO_mysql extends AbstractBaseDAO implements BaseDAO
{
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 * @throws SystemException
	 */
	protected BaseDAO_mysql(String tableName, boolean isNeedPrefix, Connection conn)
			throws SystemException
	{
		super(tableName, isNeedPrefix, conn);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.coreobject.dao.BaseDAOImplInterface#geSequenceID()
	 */
	public long geSequenceID() throws SystemException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.coreobject.dao.BaseDAOImplInterface#getMaxID()
	 */
	public long getMaxID() throws SystemException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.coreobject.dao.BaseDAOImplInterface#add(com.iss.itreasury.fcinterface.bankportal.coreobject.dataentity.BaseDataEntity)
	 */
	public long add(BaseDataEntity dataEntity) throws SystemException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.coreobject.dao.BaseDAOImplInterface#update(com.iss.itreasury.fcinterface.bankportal.coreobject.dataentity.BaseDataEntity)
	 */
	public void update(BaseDataEntity dataEntity) throws SystemException
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.coreobject.dao.BaseDAOImplInterface#findByID(long, java.lang.Class)
	 */
	public BaseDataEntity findByID(long id, Class className) throws SystemException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.coreobject.dao.BaseDAOImplInterface#findByCondition(com.iss.itreasury.fcinterface.bankportal.coreobject.dataentity.BaseDataEntity, java.lang.String)
	 */
	public Collection findByCondition(BaseDataEntity conditionInfo, String orderByString) throws SystemException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.coreobject.dao.BaseDAOImplInterface#deletePhysically(long)
	 */
	public void deletePhysically(long id) throws SystemException
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO#deleteAll()
	 */
	public void deleteAll() throws SystemException
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO#findAll(java.lang.Class, java.lang.String)
	 */
	public Collection findAll(Class baseDataEntityClass, String orderByString) throws SystemException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getFieldOpSymbol(int)
	 */
	protected String getFieldOpSymbol(int fieldOp)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getConditionOpSymbol(int)
	 */
	protected String getConditionOpSymbol(int conditionOp)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getFieldExp(java.lang.String, int, java.lang.Object, int)
	 */
	protected String getFieldExp(String fieldName, int fieldOp, Object[] fieldTypeAndValue, int operationType) throws SystemException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getValueExp(java.lang.Object, int)
	 */
	protected String getValueExp(Object[] fieldTypeAndValue, int operationType) throws SystemException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
