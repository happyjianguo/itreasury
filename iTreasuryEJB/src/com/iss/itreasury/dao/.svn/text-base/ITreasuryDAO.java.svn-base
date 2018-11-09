/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.dao;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;

import com.iss.itreasury.glinterface.kingdee.GLKingDeeBean;
import com.iss.itreasury.glinterface.u850.ConnectionSQLServer;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public abstract class ITreasuryDAO {

	/** 新增操作静态类型 */
	final static protected int DAO_OPERATION_ADD = 0;

	/** 删除操作静态类型 */
	final static protected int DAO_OPERATION_DEL = 1;

	/** 更新操作静态类型 */
	final static protected int DAO_OPERATION_UPDATE = 2;

	/** 查询操作静态类型 */
	final static protected int DAO_OPERATION_FIND = 3;

	/** 匹配操作静态类型 */
	final static protected int DAO_OPERATION_MATCH = 4;

	/** 批量复核静态类型 */
	final static protected int DAO_OPERATION_BATCHCHECK = 5;

	/** 当前DAO操作的数据库表名 */
	protected String strTableName = null;

	/** 当前DAO操作的Sequence名 */
	protected String strSequence = null;

	/** 当前DAO的使用的数据库连接 */
	protected Connection transConn = null;

	/** 是否数据库连接是自维护的（即不是容器维护的） */
	private boolean isSelfManagedConn = false;

	/** 当前DAO的使用的结果集 */
	protected ResultSet transRS = null;

	/** 当前DAO的使用的PreparedStatement */
	protected PreparedStatement transPS = null;

	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	protected ITreasuryDAO() {
	}

	protected ITreasuryDAO(String tableName) {
		strTableName = tableName;
	}

	protected ITreasuryDAO(String tableName, String sequence) {
		strTableName = tableName;
		strSequence = sequence;
	}

	protected ITreasuryDAO(Connection conn) {
		transConn = conn;
		isSelfManagedConn = true;
	}
	
	protected ITreasuryDAO(Connection conn,boolean isSelfManagedConn) {
		transConn = conn;
		this.isSelfManagedConn = isSelfManagedConn;
	}

	protected ITreasuryDAO(String tableName, Connection conn) {
		strTableName = tableName;
		transConn = conn;
		isSelfManagedConn = true;
	}

	protected ITreasuryDAO(String tableName, String sequence, Connection conn) {
		strTableName = tableName;
		strSequence = sequence;
		transConn = conn;
		isSelfManagedConn = true;
	}

	protected ITreasuryDAO(boolean isNeedPrefix) {
		this.isNeedPrefix = isNeedPrefix;
	}

	protected ITreasuryDAO(String tableName, boolean isNeedPrefix) {
		strTableName = tableName;
		this.isNeedPrefix = isNeedPrefix;
	}

	protected ITreasuryDAO(String tableName, String sequence,
			boolean isNeedPrefix) {
		strTableName = tableName;
		strSequence = sequence;
		this.isNeedPrefix = isNeedPrefix;
	}

	protected ITreasuryDAO(String tableName, boolean isNeedPrefix,
			Connection conn) {
		strTableName = tableName;
		transConn = conn;
		isSelfManagedConn = true;
		this.isNeedPrefix = isNeedPrefix;
	}

	protected ITreasuryDAO(String tableName, String sequence,
			boolean isNeedPrefix, Connection conn) {
		strTableName = tableName;
		strSequence = sequence;
		transConn = conn;
		isSelfManagedConn = true;
		this.isNeedPrefix = isNeedPrefix;
	}

	/** 使用squence还是max id获取ID的标志 */
	private boolean isUseMaxID = false;

	/**
	 * 标志位,用于在数据库字段有类型前缀的表所对应的DAO
	 */
	private boolean isNeedPrefix = false;

	/**
	 * 默认是通过sequence获取ID，如果需要获取Max ID，请使用此方法
	 */
	public void setUseMaxID() {
		this.isUseMaxID = true;
	}

	/**
	 * DAO初使化类，在使用具体的DAO操作前，必须先调用此方法!!!!
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws SecuritiesDAOException
	 */
	protected void initDAO() throws ITreasuryDAOException {
		try {
			if (transConn == null)
				transConn = Database.getConnection();
		} catch (Exception e) {
			throw new ITreasuryDAOException("数据库初使化异常发生", e);
		}
	}

	/**
	 * PreparedStatement准备工作
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ITreasuryDAOException
	 */
	protected PreparedStatement prepareStatement(String sql)
			throws ITreasuryDAOException {
		try {
			log.debug("ITreasuryDAO::SQL语句:" + sql);
			if (transConn != null && transConn.isClosed()) {
				try {
					transConn = Database.getConnection();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			transPS = transConn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new ITreasuryDAOException("PrepareStatement初使化异常发生", e);
		}
		return transPS;
	}

	/**
	 * PreparedStatement准备工作 --
	 * 
	 * @author wjliu 2007/2/2
	 * @param sql
	 *            SQL语句
	 * @param showSQL
	 *            是否打出SQL语句
	 * @return
	 * @throws ITreasuryDAOException
	 */
	protected PreparedStatement prepareStatement(String sql, boolean showSQL)
			throws ITreasuryDAOException {
		try {

			// log.debug("ITreasuryDAO::SQL语句:"+sql);
			if (showSQL == true) {
				prepareStatement(sql);
			} else {
				if (transConn != null && transConn.isClosed()) {
					try {
						transConn = Database.getConnection();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				transPS = transConn.prepareStatement(sql);
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("PrepareStatement初使化异常发生", e);
		}
		return transPS;
	}

	/**
	 * 数据库执行操作
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ITreasuryDAOException
	 */
	protected ResultSet executeQuery() throws ITreasuryDAOException {
		try {
			transRS = transPS.executeQuery();
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行操作异常发生", e);
		}
		return transRS;
	}

	/**
	 * 数据库更新操作
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ITreasuryDAOException
	 */
	protected int executeUpdate() throws ITreasuryDAOException {
		try {
			return transPS.executeUpdate();
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库更新操作异常发生", e);
		}
	}

	/**
	 * DAO结束操作，在DAO操作的结尾必须调用此方法!!!!!!!!
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ITreasuryDAOException
	 */
	protected void finalizeDAO() throws ITreasuryDAOException {
		try {
			if (transRS != null) {
				transRS.close();
				transRS = null;
			}

			if (transPS != null) {
				transPS.close();
				transPS = null;
			}

			if (transConn != null && !isSelfManagedConn) {
				transConn.close();
				transConn = null;
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库关闭异常发生", e);
		}
	}

	/**
	 * 数据库新增操作，新增的ID必须在子类set入正确的数值 修改记录: 2008/03/26 mzh_fu 将关闭连接放到 finally 中
	 * 
	 * @param dataEntity
	 *            需要被插入数据库表对应的Data Entity的实例
	 * @param
	 * @return 新产生的ID
	 * @throws ITreasuryDAOException
	 */
	public long add(ITreasuryBaseDataEntity dataEntity)
			throws ITreasuryDAOException {

		long id = -1;

		try {

			initDAO();

			// 设置空值到DataEntity的已使用表,使得setPrepareStatementByDataEntity会ID进行付值
			dataEntity.setId(-1);

			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO " + strTableName + " (\n");
			String[] buffers = getAllFieldNameBuffer(dataEntity,
					DAO_OPERATION_ADD);
			buffer.append(buffers[0]);
			buffer.append("\n) " + "VALUES (\n");
			buffer.append(buffers[1] + ") \n");

			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);

			id = setPrepareStatementByDataEntity(dataEntity, DAO_OPERATION_ADD,
					buffers[0].toString());

			executeUpdate();

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}

		return id;
	}

	/**
	 * 数据库更新操作 修改记录: 2008/03/26 mzh_fu 将关闭连接放到 finally 中
	 * 
	 * @param dataEntity
	 *            需要被更新的数据库表对应的Data Entity的实例
	 * @param
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public void update(ITreasuryBaseDataEntity dataEntity)
			throws ITreasuryDAOException {
		try {

			initDAO();

			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE " + strTableName + " SET \n");

			String[] buffers = getAllFieldNameBuffer(dataEntity,
					DAO_OPERATION_UPDATE);
			buffer.append(buffers[0]);
			buffer.append(" WHERE ID = " + dataEntity.getId());

			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			setPrepareStatementByDataEntity(dataEntity, DAO_OPERATION_UPDATE,
					buffers[0].toString());

			executeUpdate();
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
	}
	/**
	 * 数据库更新操作 修改记录: 2008/10/14 qianggao 将关闭连接放到 finally 中
	 * 将修改记录插入log表。
	 * @param dataEntity
	 *            需要被更新的数据库表对应的Data Entity的实例
	 * @param clintID
	 *            修改人
	 * @param officeID
	 * 
	 * @param insertLogTypeName 
	 *             插入日志的业务类型名称              
	 * @return
	 * @throws ITreasuryDAOException
	 */
	
	/**
	 * 根据操作类型(add 还是 update)获取所有的字段名称
	 * 
	 * @return String[]
	 * @param String1:
	 * @param String2
	 *            update操作不使用该返回值
	 * @throws ITreasuryDAOException
	 * @deprecated
	 */

	public String[] getAllFieldNameBuffer(Class className, int operationType)
			throws ITreasuryDAOException {
		StringBuffer buffer1 = new StringBuffer();// 名称
		StringBuffer buffer2 = new StringBuffer();// 问号
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(className);
		} catch (IntrospectionException e) {
			throw new ITreasuryDAOException("Java Bean.内省异常发生", e);
		}
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		int j = 1;
		for (int i = 0; i < properties.length; i++) {
			Class p = properties[i].getPropertyType();
			// log.debug(
			// "Property type:\n "
			// + p.getName()
			// + "\n Property name:\n "
			// + properties[i].getName());
			String fieldName = properties[i].getName();
			if (fieldName.compareToIgnoreCase("class") == 0)// ignore field
															// "class" of super
															// class
				continue;

			if (operationType == DAO_OPERATION_ADD) {
				buffer1.append(fieldName + ",");
			} else if (operationType == DAO_OPERATION_UPDATE) {
				if (fieldName.compareToIgnoreCase("id") == 0)// 更新操作不对id进行处理
					continue;
				buffer1.append(fieldName + " = ?,");
			} else {
				throw new ITreasuryDAOException(
						"DAO getAllFieldNameBuffer,错误的操作类型", null);
			}

			buffer2.append("?,");
		}
		String strBuffer1 = buffer1.toString();
		String strBuffer2 = buffer2.toString();

		// log.debug("----before cut-------");
		// log.debug(strBuffer1+strBuffer2);
		// cut last ","
		strBuffer1 = strBuffer1.substring(0, (strBuffer1.length() - 1));
		strBuffer2 = strBuffer2.substring(0, (strBuffer2.length() - 1));
		// log.debug("----afer cut-------");
		// log.debug(strBuffer1+strBuffer2);

		String[] strs = { strBuffer1, strBuffer2 };
		return strs;

	}

	/**
	 * 根据操作类型(add 还是 update)从Data Entity中的Hashtable中获取所有被使用过的字段名称
	 * 
	 * @return String[]
	 * @param String1:
	 * @param String2
	 *            update操作不使用该返回值
	 * @throws ITreasuryDAOException
	 */

	public String[] getAllFieldNameBuffer(ITreasuryBaseDataEntity dataEntity,
			int operationType) throws ITreasuryDAOException {
		StringBuffer buffer1 = new StringBuffer();// 名称
		StringBuffer buffer2 = new StringBuffer();// 问号

		HashMap allFields = dataEntity.gainAllUsedFieldsAndValue();
		Set allFieldNames = allFields.keySet();

		int j = 1;
		Iterator it = allFieldNames.iterator();
		while (it.hasNext()) {
			String fieldName = (String) it.next();
			log.debug("Used Field Name: " + fieldName);

			if (operationType == DAO_OPERATION_ADD) {
				buffer1.append(fieldName + ",");
			} else if (operationType == DAO_OPERATION_UPDATE
					|| operationType == DAO_OPERATION_FIND) {
				if (operationType == DAO_OPERATION_UPDATE
						&& fieldName.compareToIgnoreCase("id") == 0)// 更新操作不对id进行处理
					continue;
				if(operationType == DAO_OPERATION_UPDATE
						&& fieldName.compareToIgnoreCase("inputUserID") == 0)//更新操作不对录入人进行处理
				{
					continue;
				}
				// do not modify any blank at below codes because "= ?," is
				// match condition for split field name
				if (operationType == DAO_OPERATION_FIND)
					buffer1.append(fieldName + " = ? AND ");
				else
					buffer1.append(fieldName + " = ?,");
			} else {
				throw new ITreasuryDAOException(
						"DAO getAllFieldNameBuffer,错误的操作类型", null);
			}

			buffer2.append("?,");
		}
		String strBuffer1 = buffer1.toString();
		String strBuffer2 = buffer2.toString();

		// log.debug("----before cut-------");
		// log.debug(strBuffer1+strBuffer2);

		if (operationType == DAO_OPERATION_FIND)// cut last "and"
			strBuffer1 = strBuffer1.substring(0, (strBuffer1.length() - 4));
		else
			// cut last ","
			strBuffer1 = strBuffer1.substring(0, (strBuffer1.length() - 1));
		strBuffer2 = strBuffer2.substring(0, (strBuffer2.length() - 1));
		// log.debug("----afer cut-------");
		// log.debug(strBuffer1+strBuffer2);

		String[] strs = { strBuffer1, strBuffer2 };
		return strs;

	}

	/**
	 * 根据操作类型(add 还是 update)从Data Entity中的Hashtable中获取所有被使用过的字段名称
	 * 
	 * @return String[]
	 * @param String1:
	 * @param String2
	 *            update操作不使用该返回值
	 * @throws ITreasuryDAOException
	 */
	public String[] getAllFieldNameAndValueBuffer(
			ITreasuryBaseDataEntity dataEntity, int operationType)
			throws ITreasuryDAOException {
		StringBuffer bufferName = new StringBuffer(); // 名称
		StringBuffer bufferValue = new StringBuffer(); // 值

		HashMap allFields = dataEntity.gainAllUsedFieldsAndValue();
		Set allFieldNames = allFields.keySet();
		Set allFieldValues = allFields.entrySet();

		int j = 1;
		Iterator itName = allFieldNames.iterator();
		Iterator itValue = allFieldValues.iterator();
		while (itName.hasNext() && itValue.hasNext()) {
			String fieldName = (String) itName.next();
			String fieldValue = (String) itValue.next();
			log.debug("Used Field Name: " + fieldName);
			bufferName.append(fieldName + " = ? AND ");
		}
		String strBufferName = bufferName.toString();
		String strBufferValue = bufferValue.toString();

		if (operationType == DAO_OPERATION_FIND)// cut last "and"
			strBufferName = strBufferName.substring(0,
					(strBufferName.length() - 4));
		else
			// cut last ","
			strBufferName = strBufferName.substring(0,
					(strBufferName.length() - 1));
		strBufferValue = strBufferValue.substring(0,
				(strBufferValue.length() - 1));
		// log.debug("----afer cut-------");

		String[] strs = { strBufferName, strBufferValue };
		return strs;
	}

	/**
	 * 根据DataEntity中的信息向PrepareStatement中设置数据
	 * 
	 * @param ITreasuryBaseDataEntity
	 *            需要传递给PrepareStatement数据的Data Entity
	 * @param operationType
	 *            操作类型
	 * @return 如果是针对add操作，返回ID值，否则返回-1
	 * @throws ITreasuryDAOException
	 */
	public long setPrepareStatementByDataEntity(
			ITreasuryBaseDataEntity dataEntity, int operationType)
			throws ITreasuryDAOException {
		return setPrepareStatementByDataEntity(dataEntity, operationType,
				getAllFieldNameBuffer(dataEntity, DAO_OPERATION_FIND)[0]);
	}

	/**
	 * 根据DataEntity中的信息向PrepareStatement中设置数据
	 * 
	 * @param ITreasuryBaseDataEntity
	 *            需要传递给PrepareStatement数据的Data Entity
	 * @param operationType
	 *            操作类型
	 * @param fieldNames
	 *            用逗号隔开的所有字段名称
	 * @return 如果是针对add操作，返回ID值，否则返回-1
	 * @throws ITreasuryDAOException
	 */
	public long setPrepareStatementByDataEntity(
			ITreasuryBaseDataEntity dataEntity, int operationType,
			String fieldNames) throws ITreasuryDAOException {

		String[] fieldNameArray = null;
		long id = -1;
		// maybe has blank in every string, you must trim it before using it

		// fieldNameArray = fieldNames.split(",");
		if (operationType == DAO_OPERATION_FIND) {
			fieldNameArray = DataFormat.splitString(fieldNames, "AND");
			// String lastOne = fieldNameArray[fieldNameArray.length-1];
			// cut last one " = ?"
			// lastOne = lastOne.substring(0,lastOne.length()-4);
			// fieldNameArray[fieldNameArray.length-1] = lastOne;
		} else
			fieldNameArray = DataFormat.splitString(fieldNames, ",");
		int j = 1;
		for (int i = 0; i < fieldNameArray.length; i++) {
			String fieldName = null;

			fieldName = (fieldNameArray[i]).trim();

			if (operationType == DAO_OPERATION_UPDATE
					|| operationType == DAO_OPERATION_FIND) {// cut " = ?"
				fieldName = fieldName.substring(0, fieldName.length() - 4);
			}

			// log.debug(
			// "Field Name: "+ fieldName);

			// 更新操作不对ID进行处理
			if (operationType == DAO_OPERATION_UPDATE
					&& fieldName.compareToIgnoreCase("id") == 0)// 更新操作不对id进行处理
				continue;
			try {
				HashMap allFieldsAndValues = dataEntity
						.gainAllUsedFieldsAndValue();
				Object resValue = allFieldsAndValues.get(fieldName);

				if (resValue instanceof Long) {
					long value = ((Long) (resValue)).longValue();
					if (operationType == DAO_OPERATION_ADD
							&& fieldName.compareToIgnoreCase("id") == 0) {
						if (!isUseMaxID)
							value = geSequenceID();
						else
							value = getMaxID();
						transPS.setLong(j, value);
						id = value;
					} else
						transPS.setLong(j, value);
				} else if (resValue instanceof Double) {
					double value = ((Double) (resValue)).doubleValue();
					transPS.setDouble(j, value);
				} else if (resValue instanceof String) {
					transPS.setString(j, (String) resValue);
				} else if (resValue instanceof Timestamp) {
					Timestamp time = (Timestamp) resValue;
					if (time.equals(ITreasuryBaseDataEntity.getNullTimeStamp()))
						time = null;
					transPS.setTimestamp(j, time);
				} else if (resValue instanceof java.util.Date) {
					java.util.Date date = (java.util.Date) resValue;
					Date time = new Date(date.getTime());
					if (time.equals(ITreasuryBaseDataEntity.getNullTimeStamp()))
						time = null;
					transPS.setDate(j, time);
				} else {
					throw new ITreasuryDAOException(
							"Debug:setPrepareStatementByDataEntity类型不匹配", null);
				}
				// log.debug("value is: "+resValue);

			} catch (SQLException e) {
				throw new ITreasuryDAOException("数据库异常发生", e);
			}
			j++;

		}
		// log.debug("-----end of setPrepareStatementByDataEntity--------");
		return id;

	}

	/**
	 * 数据库查找操作 修改记录: 2008/03/26 mzh_fu 将关闭连接放到 finally 中
	 * 
	 * @param id
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public ITreasuryBaseDataEntity findByID(long id, Class className)
			throws ITreasuryDAOException {
		ITreasuryBaseDataEntity res = null;

		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE id = " + id);
			String strSQL = buffer.toString();
			log.debug(strSQL);
			// prepareStatement(strSQL);
			// transRS = executeQuery();

			prepareStatement(strSQL);
			executeQuery();

			res = getDataEntityFromResultSet(className);

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
		return res;
	}

	/**
	 * 数据库查找操作 修改记录: 2008/08/27 xfma 将关闭连接放到 finally 中
	 * 
	 * @param id
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public ITreasuryBaseDataEntity findByID(long id, Class className,
			String strTableName) throws ITreasuryDAOException {
		ITreasuryBaseDataEntity res = null;

		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE n_accountid = " + id);
			String strSQL = buffer.toString();
			log.debug(strSQL);
			// prepareStatement(strSQL);
			// transRS = executeQuery();

			prepareStatement(strSQL);
			executeQuery();

			res = getDataEntityFromResultSet(className);

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
		return res;
	}

	/**
	 * 数据库查找操作
	 * 
	 * @param conditionInfo
	 *            查询条件，如果被set过值，则认为该字段为查询条件进行匹配
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByCondition(ITreasuryBaseDataEntity conditionInfo)
			throws ITreasuryDAOException {
		return findByCondition(conditionInfo, "order by ID ");
	}

	/**
	 * 标准摘要查找并排序
	 * 2010-4-16 tanxin
	 * @param conditionInfo
	 * 查询条件，如果被set过值，则认为该字段为查询条件进行匹配
	 * @return StandardAbstractInfo的结果集
	 * @throws ITreasuryDAOException
	 */
	public Collection findStandardByCondition(ITreasuryBaseDataEntity conditionInfo) throws ITreasuryDAOException {
		String orderBy = " order by id ";
		return findByCondition(conditionInfo, orderBy);
	}

	
	
	/**
	 * 数据库查找操作
	 * 
	 * @param conditionInfo
	 *            查询条件，如果被set过值，则认为该字段为查询条件进行匹配
	 * @param classPath
	 *            需要查找的数据库表对应的Data Entity的类路经和名称
	 * @param orderByString
	 *            order by SQL语句，手工添加
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public PageLoader findByCondition(ITreasuryBaseDataEntity conditionInfo,
			String orderByString, String classPath)
			throws ITreasuryDAOException {
		StringBuffer m_sbSelect = new StringBuffer();
		StringBuffer m_sbFrom = new StringBuffer();
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbSelect.append(" * \n");
		m_sbFrom.append(" " + strTableName + " \n");
		String strs[] = this.getAllFieldNameBuffer(conditionInfo,
				DAO_OPERATION_FIND);
		m_sbWhere.append(" 1 = 1 \n");
		m_sbWhere.append(strs[0]);

		// 获取PageLoader对象
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(),
				m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT, classPath, null);

		pageLoader.setOrderBy(" " + orderByString);
		return pageLoader;
	}

	/**
	 * 数据库查找操作 修改记录: 2008/03/26 mzh_fu 将关闭连接放到 finally 中
	 * 
	 * @param conditionInfo
	 *            查询条件，如果被set过值，则认为该字段为查询条件进行匹配
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @param orderByString
	 *            order by SQL语句，手工添加
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByCondition(ITreasuryBaseDataEntity conditionInfo,
			String orderByString) throws ITreasuryDAOException {
		Collection res = null;
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE ");
			String strs[] = this.getAllFieldNameBuffer(conditionInfo,
					DAO_OPERATION_FIND);
			buffer.append(strs[0]);

			String strSQL = buffer.toString();
			if (orderByString != null) {
				strSQL += " ";
				strSQL += orderByString;
			}
			log.debug(strSQL);
			// prepareStatement(strSQL);
			// transRS = executeQuery();

			prepareStatement(strSQL);
			setPrepareStatementByDataEntity(conditionInfo, DAO_OPERATION_FIND,
					strs[0]);

			executeQuery();

			res = this.getDataEntitiesFromResultSet(conditionInfo.getClass());

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}

		return res;
	}

	/**
	 * @author 马现福 数据库查找操作 修改记录: 2008/03/26 mzh_fu 将关闭连接放到 finally 中
	 * @param conditionInfo
	 *            查询条件，如果被set过值，则认为该字段为查询条件进行匹配
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByConditionAndTable(
			ITreasuryBaseDataEntity conditionInfo, String strTableName)
			throws ITreasuryDAOException {
		Collection res = null;
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE ");
			String strs[] = this.getAllFieldNameBuffer(conditionInfo,
					DAO_OPERATION_FIND);
			buffer.append(strs[0]);

			String strSQL = buffer.toString();
			log.debug(strSQL);

			prepareStatement(strSQL);
			setPrepareStatementByDataEntity(conditionInfo, DAO_OPERATION_FIND,
					strs[0]);

			executeQuery();

			res = this.getDataEntitiesFromResultSet(conditionInfo.getClass());

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}

		return res;
	}

	/**
	 * 从ResultSet中获取查询结果
	 * 
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @param
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public ITreasuryBaseDataEntity getDataEntityFromResultSet(
			Class dataEntityClass) throws ITreasuryDAOException {
		ITreasuryBaseDataEntity res = null;
		try {
			try {
				// log.debug("--------getDataEntityFromResultSet----------");

				res = (ITreasuryBaseDataEntity) dataEntityClass.newInstance();
			} catch (InstantiationException e) {
				throw new ITreasuryDAOException("Data Entity实例化异常发生", e);
			} catch (IllegalAccessException e) {
				throw new ITreasuryDAOException("Data Entity实例化非法访问异常发生", e);
			}

			ResultSetDynaClass rsdc = new ResultSetDynaClass(transRS);
			// log.debug("--------ResultSetDynaClass" + rsdc);
			Iterator rows = rsdc.iterator();

			while (rows.hasNext()) {
				DynaBean row = (DynaBean) rows.next();
				// ... process this row ...
				// log.debug("DynaBean" + row);
				BeanInfo beanInfo = Introspector.getBeanInfo(dataEntityClass);
				PropertyDescriptor[] properties = beanInfo
						.getPropertyDescriptors();
				for (int i = 0; i < properties.length; i++) {
					Class p = properties[i].getPropertyType();
					// log.debug(
					// "Property type:\n "
					// + p.getName()
					// + "\n Property name:\n "
					// + properties[i].getName());
					String fieldName = properties[i].getName();
					fieldName = getFieldName(p, fieldName);
					Method writeMethod = properties[i].getWriteMethod();
					if (writeMethod == null)
						continue;

					if (row == null)
						continue;
					Object valueObj = null;
					try {
						// log.debug("method name:" + writeMethod.getName());
						// log.debug("value is:" +
						// row.get(fieldName.toLowerCase()));

						// modified by mzh_fu 2007/06/20 解决前缀问题
						boolean blnOldIsNeedPrefix = isNeedPrefix;
						try {
							valueObj = row.get(fieldName.toLowerCase());
						} catch (Exception e) {
							// e.printStackTrace();
							try {
								isNeedPrefix = !isNeedPrefix;
								fieldName = getFieldName(p,properties[i].getName()).toLowerCase();
								valueObj = row.get(fieldName);

							} catch (Exception ex) {
								// ex.printStackTrace();
							} finally {
								isNeedPrefix = blnOldIsNeedPrefix;
							}
						}
					} catch (IllegalArgumentException e) {
						// //可能在DataEntity中定义有对应的数据库表中没有的变量
						continue;
					}

					// log.debug("value class-------"+valueObj.getClass());

					if (valueObj instanceof BigDecimal) {
						if (p.getName().compareToIgnoreCase("long") == 0) {
							valueObj = new Long(valueObj.toString());
						} else if (p.getName().compareToIgnoreCase("double") == 0) {
							valueObj = new Double(valueObj.toString());
						}
					} else if (valueObj instanceof Timestamp) {
					} else if (valueObj instanceof String) {
					} else if (valueObj instanceof Date) {
						//valueObj = new Timestamp(DataFormat.getDate(valueObj.toString()).getTime());
						//modify by leiyang 2008-11-06 关于修改时间的时分秒问题
						valueObj = transRS.getTimestamp(fieldName);
					} else {
						continue;
					}

					Object[] args = { valueObj };
					try {
						writeMethod.invoke(res, args);
					} catch (IllegalAccessException e) {
						throw new ITreasuryDAOException("Data Entity实例化非法异常发生",
								e);
					} catch (InvocationTargetException e) {
						throw new ITreasuryDAOException("Data Entity调用目标异常发生",
								e);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库异常发生", e);
		} catch (IntrospectionException e) {
			throw new ITreasuryDAOException("Java Bean.内省异常发生", e);
		}

		return res;

	}

	/**
	 * 从ResultSet中获取查询结果
	 * 
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @param
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection getDataEntitiesFromResultSet(Class dataEntityClass)
			throws ITreasuryDAOException {
		ITreasuryBaseDataEntity res = null;
		ArrayList resList = new ArrayList();
		try {
			ResultSetDynaClass rsdc = new ResultSetDynaClass(transRS);
			log.debug("--------ResultSetDynaClass" + rsdc);
			Iterator rows = rsdc.iterator();

			while (rows.hasNext()) {
				try {
					// log.debug("--------getDataEntityFromResultSet----------");

					res = (ITreasuryBaseDataEntity) dataEntityClass
							.newInstance();
				} catch (InstantiationException e) {
					throw new ITreasuryDAOException("Data Entity实例化异常发生", e);
				} catch (IllegalAccessException e) {
					throw new ITreasuryDAOException("Data Entity实例化非法访问异常发生", e);
				}

				DynaBean row = (DynaBean) rows.next();
				// ... process this row ...
				// log.debug("DynaBean" + row);
				BeanInfo beanInfo = Introspector.getBeanInfo(dataEntityClass);
				PropertyDescriptor[] properties = beanInfo
						.getPropertyDescriptors();
				for (int i = 0; i < properties.length; i++) {
					Class p = properties[i].getPropertyType();
					// log.debug(
					// "Property type:\n "
					// + p.getName()
					// + "\n Property name:\n "
					// + properties[i].getName());
					String fieldName = properties[i].getName();
					fieldName = getFieldName(p, fieldName);
					Method writeMethod = properties[i].getWriteMethod();
					if (writeMethod == null)
						continue;

					if (row == null)
						continue;
					Object valueObj = null;
					try {
						// log.debug("method name:" + writeMethod.getName());
						// log.debug("value is:" +
						// row.get(fieldName.toLowerCase()));

						// modified by mzh_fu 2007/06/20 解决前缀问题
						try {
							valueObj = row.get(fieldName.toLowerCase());
						} catch (Exception e) {
						}

						if (valueObj == null) {// 可能在DataEntity中定义有对应的数据库表中没有的变量
							// continue;
							// modified by mzh_fu 2007/06/20 解决前缀问题
							if (isNeedPrefix) {
								valueObj = row.get(properties[i].getName()
										.toLowerCase());
							} else {
								valueObj = row.get(getFieldName(p,
										properties[i].getName()).toLowerCase());
							}
						}
					} catch (IllegalArgumentException e) {
						// //可能在DataEntity中定义有对应的数据库表中没有的变量
						// e.printStackTrace();
						continue;
					}

					// log.debug("value class-------"+valueObj.getClass());

					if (valueObj instanceof BigDecimal) {
						if (p.getName().compareToIgnoreCase("long") == 0) {
							valueObj = new Long(valueObj.toString());
						} else if (p.getName().compareToIgnoreCase("double") == 0) {
							valueObj = new Double(valueObj.toString());
						}
					} else if (valueObj instanceof Timestamp
							|| valueObj instanceof String) {
					} else if (valueObj instanceof Date) {
						valueObj = new Timestamp(DataFormat.getDate(
								valueObj.toString()).getTime());
					} else {
						continue;
					}

					Object[] args = { valueObj };
					try {
						writeMethod.invoke(res, args);
					} catch (IllegalAccessException e) {
						throw new ITreasuryDAOException("Data Entity实例化非法异常发生",
								e);
					} catch (InvocationTargetException e) {
						throw new ITreasuryDAOException("Data Entity调用目标异常发生",
								e);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				resList.add(res);
			}

		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库异常发生", e);
		} catch (IntrospectionException e) {
			throw new ITreasuryDAOException("Java Bean.内省异常发生", e);
		}

		return resList;
	}

	/**
	 * 根据sequence的命名规则获取下一个可用的ID值 sequence命名规则:
	 * seq_+tablename，例如,SEC_Remark表的sequence为SEQ_SEC_Remark
	 * 
	 * @param
	 * @param
	 * @return 下一个ID
	 * @throws ITreasuryDAOException
	 */
	protected long geSequenceID() throws ITreasuryDAOException {

		/**
		 * 此方法只能在DAO中被调用，即不重新创建数据库资源，因此也不需要 关闭数据库资源
		 */
		long id = -1;
		String strSeqName = "";
		if (strSequence == null || "".equalsIgnoreCase(strSequence)) {
			strSeqName = "SEQ_" + strTableName;
		} else {
			strSeqName = strSequence;
		}
		String sql = "SELECT " + strSeqName + ".nextval nextid from dual";
		// prepareStatement(sql);
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		try { // 内部维护RS和PS，否则将会产生冲突,但Connection使用同一个
			if (transConn != null && transConn.isClosed()) {
				try {
					transConn = Database.getConnection();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			localPS = transConn.prepareStatement(sql);
			localRS = localPS.executeQuery();

			if (localRS.next()) {
				id = localRS.getLong("nextid");
			}
			if (localRS != null)
				localRS.close();
			if (localPS != null)
				localPS.close();
		} catch (SQLException e) {
			new ITreasuryDAOException("数据库获取ID产异常", e);
		}

		return id;
	}

	private long getMaxID() throws ITreasuryDAOException {
		long id = -1;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from " + strTableName);
		try { // 内部维护RS和PS，否则将会产生冲突,但Connection使用同一个
			localPS = transConn.prepareStatement(sb.toString());
			ResultSet rs = localPS.executeQuery();
			if (rs.next()) {
				id = rs.getLong("ID");
			}
			if (localRS != null)
				localRS.close();
			if (localPS != null)
				localPS.close();
		} catch (SQLException e) {
			new ITreasuryDAOException("数据库获取ID产异常", e);
		}
		return id;
	}

	/**
	 * 根据数据类型及是否有前缀,确定改字段在DataEntity中的名称 注:字段ID没有前缀
	 */
	private String getFieldName(Class propertyType, String fieldName) {
		if (isNeedPrefix && !fieldName.equalsIgnoreCase("id")) {
			String prefixName = ITreasuryBaseDataEntity.DataTypeName
					.getPrefixByDataType(propertyType.getName());
			// log.debug("-------prefixName: "+prefixName);
			// 此判断条件为了对表SETT_SubAccount的字段进行处理，该表中以"AC_"，"AF_"，"AL_"开头的字段没有前缀而其他字段有前缀
			// Huang Ye 2004/08/16
			if (!fieldName.startsWith("AC_") && !fieldName.startsWith("AF_")
					&& !fieldName.startsWith("AL_")) {
				fieldName = prefixName + fieldName;
			}
			// log.debug("-------fieldName: "+fieldName);
		}
		return fieldName;
	}

	/**
	 * @return Returns the isSelfManagedConn.
	 */
	public boolean isSelfManagedConn() {
		return isSelfManagedConn;
	}

	/**
	 * @param isSelfManagedConn
	 *            The isSelfManagedConn to set.
	 */
	public void setSelfManagedConn(boolean isSelfManagedConn) {
		this.isSelfManagedConn = isSelfManagedConn;
	}

	/**
	 * 从数据库中根据id物理删除一条记录 使用时请确定是逻辑删除还是物理删除！！ 修改记录: 2008/03/26 mzh_fu 将关闭连接放到
	 * finally 中
	 */
	public void deletePhysically(long id) throws ITreasuryDAOException {
		try {
			String strSQL = "delete from " + strTableName + " where id = " + id;
			initDAO();
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeUpdate();

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
	}
	
	/**
	 * 金蝶DAO初使化类，初始化金蝶连接
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws SecuritiesDAOException
	 */
	protected void initKingDeeDAO(long lOfficeID,long lCurrencyID) throws ITreasuryDAOException {
		try {
			if (transConn == null)
//				transConn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
				transConn = GLKingDeeBean.get_jdbc_connection(lOfficeID,lCurrencyID,0);
		} catch (Exception e) {
			throw new ITreasuryDAOException("金蝶数据库链接异常", e);
		}
	}
}
