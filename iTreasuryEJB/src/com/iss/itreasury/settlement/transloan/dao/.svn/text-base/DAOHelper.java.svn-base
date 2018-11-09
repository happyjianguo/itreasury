/*
 * 创建日期 2003-10-10
 */
package com.iss.itreasury.settlement.transloan.dao;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
/**
 * DAO帮助类
 * @author yqwu
 */
public class DAOHelper
{
	/**
	 * 条件类型：=
	 */
	public static final int CONDITION_TYPE_EQUAL = 1;
	/**
	 * 条件类型：<>
	 */
	public static final int CONDITION_TYPE_NOT_EQUAL = 2;
	/**
	 * 条件类型：LIKE
	 */
	public static final int CONDITION_TYPE_LIKE = 3;
	/**
	 * 条件类型：BETWEEN
	 */
	public static final int CONDITION_TYPE_BETWEEN = 4;
	/**
	 * 条件类型LIKE的情况'%VALUE%'
	 */
	public static final int LIKE_TYPE_ALL = 1;
	/**
	 * 条件类型LIKE的情况'VALUE%'
	 */
	public static final int LIKE_TYPE_RIGHT = 2;
	/**
	 * 条件类型LIKE的情况'%VALUE'
	 */
	public static final int LIKE_TYPE_LEFT = 3;
	/**
	 * 查询帮助对象类型
	 */
	public static final int HELPER_SELECT = 1;
	/**
	 * 更新帮助对象类型
	 */
	public static final int HELPER_UPDATE = 2;
	/**
	 * 插入帮助对象类型
	 */
	public static final int HELPER_INSERT = 3;
	/**
	 * 条件对象默认命名
	 */
	private static final String DEFAULT_NAME = "[未命名]";
	/**
	 * LOG日志对象
	 */
	private static Logger logger = Logger.getLogger(DAOHelper.class.getName());
	/**
	 * 整数类型
	 */
	private static final int TYPE_LONG = 1;
	/**
	 * 浮点类型
	 */
	private static final int TYPE_DOUBLE = 2;
	/**
	 * 字符串类型
	 */
	private static final int TYPE_STRING = 3;
	/**
	 * 时间戳类型
	 */
	private static final int TYPE_TIMESTAMP = 4;
	/**
	 * 创建PreparedStatement
	 * @param conn Connection
	 * @param condition 条件对象
	 * @return PreparedStatement
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	public static PreparedStatement buildPreparedStatemnet(Connection conn, DAOHelper condition) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException
	{
		PreparedStatement pstmt = null;
		long time = Env.getSystemDateTime().getTime();
		switch (condition.getHelpType())
		{
			case HELPER_SELECT :
				pstmt = buildSELECTPreparedStatement(conn, condition);
				break;
			case HELPER_UPDATE :
				pstmt = buildUPDATEPreparedStatement(conn, condition);
				break;
			case HELPER_INSERT :
				pstmt = buildINSERTPreparedStatement(conn, condition);
				break;
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("\n生成查询条件耗时(ms):" + (Env.getSystemDateTime().getTime() - time));
		}
		return pstmt;
	}
	/**
	 * 得到UPATE类型的PreparedStatement
	 * @param conn
	 * @param condition
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	private static PreparedStatement buildUPDATEPreparedStatement(Connection conn, DAOHelper condition) throws IllegalAccessException, InvocationTargetException, SQLException
	{
		return null;
	}
	/**
	 * 得到INSERT类型的PreparedStatement
	 * @param conn
	 * @param condition
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	private static PreparedStatement buildINSERTPreparedStatement(Connection conn, DAOHelper condition) throws IllegalAccessException, InvocationTargetException, SQLException
	{
		return null;
	}
	/**
	 * 得到select类型的PreparedStatement
	 * @param conn
	 * @param condition
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	private static PreparedStatement buildSELECTPreparedStatement(Connection conn, DAOHelper condition) throws IllegalAccessException, InvocationTargetException, SQLException
	{
		PreparedStatement pstmt;
		buildSELECTSqlString(condition);
		pstmt = setSELECTPreparedStatement(conn, condition);
		return pstmt;
	}
	/**
	 * 创建完整的sql语句
	 * @param condition 条件对象
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void buildSELECTSqlString(DAOHelper condition) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		//生成sql语句的where子句条件
		buildSELECTSqlStringConditionValues(condition);
		//生成排序语句
		if (condition.orderColumnName != null && condition.orderColumnName.trim().length() > 0)
		{
			condition.sqlBuffer.append("\n order by ").append(condition.orderColumnName);
			if (condition.desc)
			{
				condition.sqlBuffer.append(" desc");
			}
		}
		//记录日志
		/*if (logger.isDebugEnabled())
		{
			StringBuffer buffer = new StringBuffer(128);
			buffer.append("\n").append(condition.getName()).append("-查询时创建的SQL语句：\n");
			buffer.append(condition.sqlBuffer);
			logger.debug(buffer.toString());
		}*/
	}
	/**
	 * 创建sql语句的条件值
	 * @param condition 条件对象
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void buildSELECTSqlStringConditionValues(DAOHelper condition) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Method[] methodArray = condition.info.getClass().getDeclaredMethods();
		Method method;
		String methodName, returntype;
		for (int i = 0; i < methodArray.length; i++)
		{
			method = methodArray[i];
			methodName = method.getName();
			returntype = method.getReturnType().getName();
			if (methodName.startsWith("get") && getClassType(returntype) > 0)
			{
				buildSELECTSqlStringConditionValuesCore(condition, method);
			}
		}
		Log.print(condition.sqlBuffer.toString());
	}
	/**
	 * 创建sql语句的条件值的核心方法
	 * @param condition 条件对象
	 * @param method 方法对象
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void buildSELECTSqlStringConditionValuesCore(DAOHelper condition, Method method) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String classType, fieldName, methodName, columnPrefix;
		Object value, moreValue, lessValue;
		boolean valueIsNull;
		int notEqualIndex, likeIndex, betweenIndex;
		value = method.invoke(condition.info, new Object[]{});
		valueIsNull = value == null;
		classType = method.getReturnType().getName();
		methodName = method.getName();
		fieldName = methodName.substring(3);
		columnPrefix = getColumnPrefix(classType, fieldName);
		if ((!valueIsNull || condition.strict) &&  condition.notMatch.indexOf(methodName)==-1)
		{
			if (valueIsNull)
			{
				setWhereOrAnd(condition);
				condition.sqlBuffer.append(columnPrefix).append(fieldName);
				condition.sqlBuffer.append(" is NULL ");
			}
			else
			{
				notEqualIndex = condition.notEqual.indexOf(methodName);
				likeIndex = condition.likes.indexOf(methodName);
				betweenIndex = condition.between.indexOf(methodName);
				int conditionType = CONDITION_TYPE_EQUAL;
				//if (!condition.strict)
				{
					if (notEqualIndex != -1 || likeIndex != -1 || betweenIndex != -1)
					{
						if (notEqualIndex != -1)
						{
							conditionType = CONDITION_TYPE_NOT_EQUAL;
						}
						else if (likeIndex != -1)
						{
							conditionType = CONDITION_TYPE_LIKE;
						}
						else if (betweenIndex != -1)
						{
							conditionType = CONDITION_TYPE_BETWEEN;
						}
					}
				}
				switch (conditionType)
				{
					case (CONDITION_TYPE_NOT_EQUAL) :
						setWhereOrAnd(condition);
						condition.sqlBuffer.append(columnPrefix).append(fieldName);
						condition.sqlBuffer.append("<>? ");
						condition.classTypes.add(classType);
						condition.values.add(value);
						break;
					case (CONDITION_TYPE_LIKE) :
						setWhereOrAnd(condition);
						condition.sqlBuffer.append(columnPrefix).append(fieldName);
						condition.sqlBuffer.append(" like ?");
						condition.classTypes.add(classType);
						condition.values.add(condition.likesKeywords.get(likeIndex));
						break;
					case (CONDITION_TYPE_BETWEEN) :
						moreValue = condition.betweenMoreValue.get(betweenIndex);
						lessValue = condition.betweenLessValue.get(betweenIndex);
						if (moreValue != null)
						{
							setWhereOrAnd(condition);
							condition.sqlBuffer.append(columnPrefix).append(fieldName).append(">");
							if (((Boolean) condition.betweenMoreValueInclude.get(betweenIndex)).booleanValue())
							{
								condition.sqlBuffer.append("=");
							}
							condition.sqlBuffer.append("? ");
							condition.classTypes.add(classType);
							condition.values.add(condition.betweenMoreValue.get(betweenIndex));
						}
						if (lessValue != null)
						{
							setWhereOrAnd(condition);
							condition.sqlBuffer.append(columnPrefix).append(fieldName).append("<");
							if (((Boolean) condition.betweenLessValueInclude.get(betweenIndex)).booleanValue())
							{
								condition.sqlBuffer.append("=");
							}
							condition.sqlBuffer.append("? ");
							condition.classTypes.add(classType);
							condition.values.add(condition.betweenLessValue.get(betweenIndex));
						}
						break;
					default :
						boolean addNotNull = condition.notNull.contains(methodName);
						if (condition.strict || !isNull(classType, value) || addNotNull)
						{
							setWhereOrAnd(condition);
							condition.sqlBuffer.append(columnPrefix).append(fieldName);
							if (getClassType(classType) == TYPE_STRING && ((String) value).length() == 0 && !addNotNull)
							{
								condition.sqlBuffer.append(" is NULL ");
							}
							else
							{
								condition.sqlBuffer.append("=? ");
								condition.classTypes.add(classType);
								condition.values.add(value);
							}
						}
						break;
				}
			}
		}
	}
	/**
	 * 判断条件值是否为空
	 * @param classType
	 * @param value
	 * @return
	 */
	private static boolean isNull(String classType, Object value)
	{
		int type = getClassType(classType);
		boolean isNull = true;
		switch (type)
		{
			case (TYPE_LONG) :
				long valueLong = ((Long) value).longValue();
				if (valueLong != -1)
				{
					isNull = false;
				}
				break;
			case (TYPE_DOUBLE) :
				double valueDouble = ((Double) value).doubleValue();
				if (valueDouble == 0)
				{
					isNull = true;
				}
				break;
			case (TYPE_STRING) :
				String valueString = (String) value;
				if (valueString != null && valueString.trim().length() > 0)
				{
					isNull = false;
				}
				break;
			case (TYPE_TIMESTAMP) :
				if (value != null)
				{
					isNull = false;
				}
				break;
		}
		return isNull;
	}
	/**
	 * 得到列的前缀
	 * @param classType String
	 * @param fieldName String
	 * @return 列的前缀
	 */
	private static String getColumnPrefix(String classType, String fieldName)
	{
		int type = getClassType(classType);
		String returnValue = "";
		switch (type)
		{
			case (TYPE_LONG) :
				if (!fieldName.equals("ID"))
				{
					returnValue = "N";
				}
				break;
			case (TYPE_DOUBLE) :
				returnValue = "M";
				break;
			case (TYPE_STRING) :
				returnValue = "S";
				break;
			case (TYPE_TIMESTAMP) :
				returnValue = "DT";
				break;
		}
		return returnValue;
	}
	/**
	 * 得到值类型
	 * @param classTypeName String
	 * @return 值类型
	 */
	private static int getClassType(String classTypeName)
	{
		if (classTypeName.equals("long"))
		{
			return TYPE_LONG;
		}
		if (classTypeName.equals("double"))
		{
			return TYPE_DOUBLE;
		}
		if (classTypeName.equals("java.lang.String"))
		{
			return TYPE_STRING;
		}
		if (classTypeName.equals("java.sql.Timestamp"))
		{
			return TYPE_TIMESTAMP;
		}
		return -1;
	}
	/**
	 * 拼写sql语句的where||and关键字
	 * @param buffer
	 */
	private static void setWhereOrAnd(DAOHelper condition)
	{
		if (condition.sqlBuffer.substring(condition.sqlBuffer.length() - condition.tableName.length()).endsWith(condition.tableName))
		{
			condition.sqlBuffer.append("\n where ");
		}
		else
		{
			condition.sqlBuffer.append("\n and ");
		}
	}
	/**
	 * 得到sql table名称
	 * @param sqlString
	 * @return
	 */
	private static String getTableName(String sqlString)
	{
		int lastBlankIndex = sqlString.lastIndexOf(" ");
		String tableName = sqlString.substring(lastBlankIndex).trim();
		return tableName;
	}
	/**
	 * 设置PreparedStatement
	 * @param conn Connection
	 * @param condition 条件对象
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	private static PreparedStatement setSELECTPreparedStatement(Connection conn, DAOHelper condition) throws SQLException
	{
		String classTypeName;
		Object value;
		int classType;
		PreparedStatement pstmt;
		long longValue;
		double doubleValue;
		String stringValue;
		Timestamp timestampValue;
		pstmt = conn.prepareStatement(condition.sqlBuffer.toString());
		for (int i = 0; i < condition.classTypes.size(); i++)
		{
			classTypeName = (String) condition.classTypes.get(i);
			classType = getClassType(classTypeName);
			switch (classType)
			{
				case (TYPE_LONG) :
					pstmt.setLong(i + 1, ((Long) condition.values.get(i)).longValue());
					break;
				case (TYPE_DOUBLE) :
					pstmt.setDouble(i + 1, ((Double) condition.values.get(i)).doubleValue());
					break;
				case (TYPE_STRING) :
					pstmt.setString(i + 1, (String) condition.values.get(i));
					break;
				case (TYPE_TIMESTAMP) :
					pstmt.setTimestamp(i + 1, (Timestamp) condition.values.get(i));
					break;
			}
		}
		return pstmt;
	}
	private Object info;
	private ArrayList notMatch;
	private ArrayList notEqual;
	private ArrayList likes;
	private ArrayList likesKeywords;
	private ArrayList between;
	private ArrayList betweenMoreValue;
	private ArrayList betweenLessValue;
	private ArrayList betweenMoreValueInclude;
	private ArrayList betweenLessValueInclude;
	private ArrayList notNull;
	private ArrayList classTypes;
	private ArrayList values;
	private int orderType;
	private boolean desc;
	private boolean strict;
	private String orderColumnName;
	private StringBuffer sqlBuffer;
	private String sqlString;
	private String tableName;
	private String name;
	private int helpType = 1;
	public DAOHelper()
	{
		notMatch = new ArrayList();
		notEqual = new ArrayList();
		likes = new ArrayList();
		between = new ArrayList();
		likesKeywords = new ArrayList();
		betweenMoreValue = new ArrayList();
		betweenLessValue = new ArrayList();
		classTypes = new ArrayList();
		values = new ArrayList();
		betweenMoreValueInclude = new ArrayList();
		betweenLessValueInclude = new ArrayList();
		notNull = new ArrayList();
	}
	/**
	 * 设置对象名称，用于LOG日志 
	 * @param name String
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * 
	 * @param helpType
	 */
	public void setHelpType(int helpType)
	{
		this.helpType = helpType;
	}
	/**
	 * 
	 * @return
	 */
	public int getHelpType()
	{
		return this.helpType;
	}
	/**
	 * 取对象名称
	 * @return name
	 */
	public String getName()
	{
		if (this.name != null)
		{
			return this.name;
		}
		return DEFAULT_NAME;
	}
	/**
	 * 设置排序列名
	 * @param orderColumnName String
	 */
	public void setOrderColumnName(String orderColumnName)
	{
		this.orderColumnName = orderColumnName;
	}
	/**
	 * 设置值对象
	 * @param info Object
	 */
	public void setInfo(Object info)
	{
		this.info = info;
	}
	/**
	 * 设置条件属性：是否严格匹配
	 * @param strict --true,如果严格匹配，则对值对象各个属性严格匹配<br>
	 *                --false,不严格匹配，忽略空的属性条件
	 */
	public void setStrict(boolean strict)
	{
		this.strict = strict;
	}
	/**
	 * 设置忽略空的条件
	 * @param methodName
	 */
	public void setNotNull(String methodName)
	{
		this.notNull.add(methodName);
	}
	/**
	 * 得到值对象
	 * @return 值对象
	 */
	public Object getInfo()
	{
		return this.info;
	}
	/**
		 * 设置不严格的属性条件
		 * @param methodName 值对象的方法名
		 */
	public void setNotMatch(String methodName)
	{
		this.notMatch.add(methodName);
	}
	/**
	 * 设置不等于的属性条件
	 * @param methodName 值对象的方法名
	 */
	public void setNotEquals(String methodName)
	{
		this.notEqual.add(methodName);
	}
	/**
	 * 设置排序类型
	 * @param orderType int
	 */
	public void setOrderType(int orderType)
	{
		this.orderType = orderType;
	}
	/**
	 * 设置是否降序，默认升序
	 * @param desc boolean
	 */
	public void setDesc(boolean desc)
	{
		this.desc = desc;
	}
	/**
	 * 得到排序类型
	 * @return 排序类型
	 */
	public int getOrderType()
	{
		return this.orderType;
	}
	/**
	 * 是否降序
	 * @return 是否降序
	 */
	public boolean isDesc()
	{
		return this.desc;
	}
	/**
	 * 设置BETWEEN条件
	 * @param methodName 方法名
	 * @param lessValue 最小值
	 * @param includeLess 是否包含(>=)最小值，true-包含,false-不包含
	 * @param moreValue 最大值
	 * @param includeMore 是否包含(>=)最大值，true-包含,false-不包含
	 */
	public void setBetweenItem(String methodName, Object lessValue, boolean includeLess, Object moreValue, boolean includeMore)
	{
		if (lessValue != null || moreValue != null)
		{
			this.between.add(methodName);
			this.betweenLessValue.add(lessValue);
			this.betweenMoreValue.add(moreValue);
			this.betweenLessValueInclude.add(this.betweenLessValueInclude.size(), new Boolean(includeLess));
			this.betweenMoreValueInclude.add(this.betweenMoreValueInclude.size(), new Boolean(includeMore));
		}
	}
	/**
	 * 设置LIKE条件
	 * @param methodName 方法名
	 * @param likeType LIKE 类型
	 */
	public void setLikeItem(String methodName, int likeType) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Method method = this.info.getClass().getMethod(methodName, new Class[]{});
		String keyword = (String) method.invoke(this.info, new Object[]{});
		if (keyword != null)
		{
			this.likes.add(methodName);
			StringBuffer buffer = new StringBuffer(32);
			if (likeType == LIKE_TYPE_ALL || likeType == LIKE_TYPE_RIGHT)
			{
				buffer.append("%");
			}
			buffer.append(keyword);
			if (likeType == LIKE_TYPE_ALL || likeType == LIKE_TYPE_LEFT)
			{
				buffer.append("%");
			}
			this.likesKeywords.add(buffer.toString());
		}
	}
	/**
	 * 设置SQL语句
	 * @param sqlString
	 */
	public void setSqlString(String sqlString)
	{
		this.sqlString = sqlString.trim();
		this.tableName = getTableName(this.sqlString);
		this.sqlBuffer = new StringBuffer(128);
		this.sqlBuffer.append(sqlString);
	}
}
