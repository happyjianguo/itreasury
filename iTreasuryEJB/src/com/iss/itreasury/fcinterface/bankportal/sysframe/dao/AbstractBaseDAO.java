/**
 * Title:        		BankPortal
 * Description:         
 * Copyright:           Copyright (c) 2005
 * Company:             iSoftStone
 * @author              mxzhou 
 * @version
 * Date of Creation     2005-5-10
 */

package com.iss.itreasury.fcinterface.bankportal.sysframe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.Database;
import com.iss.itreasury.fcinterface.bankportal.util.Env;
import com.iss.itreasury.fcinterface.bankportal.util.Logger;

/**
 * @author mxzhou 
 * TODO To change the template for this generated type comment go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
abstract class AbstractBaseDAO
{
	/**DAO操作类型常量定义*/
	protected static final int DAO_OPERATION_ADD = 1; //新增操作
	protected static final int DAO_OPERATION_DEL = 2; //删除操作
	protected static final int DAO_OPERATION_UPDATE = 3; //更新操作
	protected static final int DAO_OPERATION_FIND = 4; //查询操作
	/**记录ID取值方式常量定义*/
	protected static final int ID_TYPE_MAXID = 101; //ID取max id
	protected static final int ID_TYPE_SQUENCE = 102; //ID取sequence
	protected static final int ID_TYPE_APPOINT = 103; //ID为外部指定
	
	/** 日志对象 */
	private Logger log = new Logger(AbstractBaseDAO.class);
	/** DAO操作的数据库表名 */
	protected String strTableName = null;
	/** DAO使用的数据库连接 */
	protected Connection transConn = null;
	/** 数据库连接是否是自维护的 */
	private boolean isSelfManagedConn = false;
	/** DAO使用的结果集 */
	protected ResultSet transRS = null;
	/** DAO使用的PreparedStatement */
	protected PreparedStatement transPS = null;

	/** ID类型，默认取max id */
	private int idType = ID_TYPE_MAXID;

	/** 数据库字段是否有类型前缀 */
	private boolean isNeedPrefix = true;

	
	/**数据库字段的比较方式*/
	protected static final int FIELD_EQU = 201; 				//等于（默认）
	protected static final int FIELD_ABOVE = 202; 				//大于
	protected static final int FIELD_BELOW = 203; 				//小于
	protected static final int FIELD_ABOVE_EQU = 204; 			//大于等于
	protected static final int FIELD_BELOW_EQU = 205; 			//小于等于
	protected static final int FIELD_LIKE = 206; 				//like
	protected static final String SUFFIX_EQU    		= "_e";	//等于（默认）
	protected static final String SUFFIX_ABOVE      	= "_a"; //大于
	protected static final String SUFFIX_BELOW      	= "_b"; //小于
	protected static final String SUFFIX_ABOVE_EQU      = "_ae";//大于等于
	protected static final String SUFFIX_BELOW_EQU      = "_be";//小于等于
	protected static final String SUFFIX_LIKE      		= "_l"; //like
    protected static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) FIELD_EQU:
                strReturn = SUFFIX_EQU;
                break;
            case (int) FIELD_ABOVE:
                strReturn = SUFFIX_ABOVE;
                break;
            case (int) FIELD_BELOW:
                strReturn = SUFFIX_BELOW;
                break;
            case (int) FIELD_ABOVE_EQU:
                strReturn = SUFFIX_ABOVE_EQU;
                break;
            case (int) FIELD_BELOW_EQU:
                strReturn = SUFFIX_BELOW_EQU;
                break;
            case (int) FIELD_LIKE:
                strReturn = SUFFIX_LIKE;
                break;
        }
        return strReturn;
    }
	/**数据库条件的连接方式*/
	protected static final int CONDITION_COMMA = 251; //逗号
	protected static final int CONDITION_AND = 252; //条件与
	protected static final int CONDITION_OR = 253; //条件或
	
	/**
	 * 构造方法
	 */
	protected AbstractBaseDAO(String tableName, boolean isNeedPrefix, Connection conn)
	 	throws SystemException
	{
		this.strTableName = tableName;
		this.isNeedPrefix = isNeedPrefix;
		this.transConn = conn;		
		if(conn != null)
		{
			this.isSelfManagedConn = true;
		}
	}

	/**
	 * 设置ID类型(默认是通过max id获取)
	 */
	public void setIDType(int idType)
	{
		this.idType = idType;
	}

	/**
	 * @return Returns the isSelfManagedConn.
	 */
	public boolean isSelfManagedConn()
	{
		return isSelfManagedConn;
	}

	/**
	 * @param isSelfManagedConn
	 *            The isSelfManagedConn to set.
	 */
	public void setSelfManagedConn(boolean isSelfManagedConn)
	{
		this.isSelfManagedConn = isSelfManagedConn;
	}
	/**
	 * @return Returns the isNeedPrefix.
	 */
	public boolean isNeedPrefix()
	{
		return isNeedPrefix;
	}
	
	/**
	 * DAO初使化类，在使用具体的DAO操作前，必须先调用此方法!!!!
	 * 
	 * @param
	 * @param
	 * @return @throws
	 *         BaseDAOException
	 */
	protected void initDAO() throws SystemException
	{
		try
		{
			if (transConn == null)
			{
				transConn = Database.getConnection();
			}
		}
		catch (Exception e)
		{
			throw new SystemException("数据库初使化异常发生", e);
		}
	}

	/**
	 * PreparedStatement准备工作
	 * 
	 * @param
	 * @param
	 * @return @throws
	 *         ITreasuryDAOException
	 */
	protected PreparedStatement prepareStatement(String sql)
			throws SystemException
	{
		try
		{
			log.debug("SQL语句:" + sql);
			transPS = transConn.prepareStatement(sql);
		}
		catch (SQLException e)
		{
			throw new SystemException("PrepareStatement初使化异常发生", e);
		}
		return transPS;
	}

	/**
	 * 数据库执行操作
	 * 
	 * @param
	 * @param
	 * @return @throws
	 *         ITreasuryDAOException
	 */
	protected ResultSet executeQuery() throws SystemException
	{
		try
		{
			transRS = transPS.executeQuery();
		}
		catch (SQLException e)
		{
			throw new SystemException("数据库执行操作异常发生", e);
		}
		return transRS;
	}

	/**
	 * 数据库更新操作
	 * 
	 * @param
	 * @param
	 * @return @throws
	 *         ITreasuryDAOException
	 */
	protected int executeUpdate() throws SystemException
	{
		try
		{
			return transPS.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new SystemException("数据库更新操作异常发生", e);
		}
	}

	/**
	 * DAO结束操作，在DAO操作的结尾必须调用此方法!!!!!!!!
	 * 
	 * @param
	 * @param
	 * @return @throws
	 *         ITreasuryDAOException
	 */
	protected void finalizeDAO() throws SystemException
	{
		try
		{
			if (transRS != null)
			{
				transRS.close();
				transRS = null;
			}

			if (transPS != null)
			{
				transPS.close();
				transPS = null;
			}

			if (transConn != null && !isSelfManagedConn)
			{
				transConn.close();
				transConn = null;
			}
		}
		catch (SQLException e)
		{
			throw new SystemException("数据库关闭异常发生", e);
		}
	}

	/**
	 * @param usedFields
	 * @param usedFieldMap
	 * @param dao_operation_update
	 * @return
	 */
	protected String generateFieldExp(String[] usedFields, HashMap usedFieldMap, int operationType)
		throws SystemException
	{
		if(usedFields == null || usedFields.length <= 0)
		{
			return "";
		}
		
		StringBuffer fieldExp = new StringBuffer();
		for(int i = 0; i < usedFields.length; i++)
		{
			String fieldName = null;//字段名称
			String suffix = null;//字段后缀
			int fieldOp = -1;//字段的操作类型
			int conditionOp = -1;//条件操作类型
			Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(usedFields[i]);//字段的类型和值
			String curFieldExp = null;//当前字段表达式
			
			//获取字段名称和操作后缀
			int pos = usedFields[i].indexOf("_");
			if(pos != -1)
			{
				if(this.isNeedPrefix)
				{
					pos = usedFields[i].indexOf("_", pos+1);
					if(pos != -1)
					{
						suffix = usedFields[i].substring(pos);
						//后缀处理限制在几种已定义的操作符，
						//操作符不会再增加，此项功能不允许再扩展！
						if(suffix.equals(SUFFIX_EQU) ||  
						   suffix.equals(SUFFIX_ABOVE) || 
				           suffix.equals(SUFFIX_BELOW) || 
						   suffix.equals(SUFFIX_ABOVE_EQU) || 
						   suffix.equals(SUFFIX_BELOW_EQU) || 
						   suffix.equals(SUFFIX_LIKE))
						{
							fieldName = usedFields[i].substring(0, pos);
						}
						else
						{
							fieldName = usedFields[i];
							suffix = SUFFIX_EQU;
						}						
					}
					else
					{
						fieldName = usedFields[i];
						suffix = SUFFIX_EQU;
					}
				}
				else
				{
					suffix = usedFields[i].substring(pos);
					//后缀处理限制在几种已定义的操作符，
					//操作符不会再增加，此项功能不允许再扩展！
					if(suffix.equals(SUFFIX_EQU) ||  
					   suffix.equals(SUFFIX_ABOVE) || 
			           suffix.equals(SUFFIX_BELOW) || 
					   suffix.equals(SUFFIX_ABOVE_EQU) || 
					   suffix.equals(SUFFIX_BELOW_EQU) || 
					   suffix.equals(SUFFIX_LIKE))
					{
						fieldName = usedFields[i].substring(0, pos);
					}
					else
					{
						fieldName = usedFields[i];
						suffix = SUFFIX_EQU;
					}		
				}
			}
			else
			{
				fieldName = usedFields[i];
				suffix = SUFFIX_EQU;
			}
			
			//更新操作不对ID进行处理
			if (operationType == DAO_OPERATION_UPDATE && fieldName.compareToIgnoreCase("n_id") == 0)
			{
				continue;
			}
			
			//根据字段操作后缀获得字段操作类型（默认为“等于”操作）
			fieldOp = getFieldOp(suffix);
			//根据数据库操作类型获取条件操作符（默认为“逗号”）
			conditionOp = getConditionOp(operationType);
			
			//根据字段名称，字段操作类型，以及数据库操作类型，构造当前字段表达式
			curFieldExp = getFieldExp(fieldName, fieldOp, fieldTypeAndValue, operationType);
			
			//添加当前字段表达式
			if(curFieldExp != null && curFieldExp.length() > 0)
			{
				if(fieldExp.length() > 0)
				{
					fieldExp.append(" " + getConditionOpSymbol(conditionOp) + " ");
				}
				fieldExp.append(curFieldExp);
			}
		}
		return fieldExp.toString();
	}
	
	protected String generateValueExp(String[] usedFields, HashMap usedFieldMap, int operationType)
		throws SystemException
	{
		if(usedFields == null || usedFields.length <= 0)
		{
			return "";
		}

		StringBuffer valueExp = new StringBuffer();
		for (int i = 0; i < usedFields.length; i++)
		{
			String fieldName = usedFields[i];
			
			//更新操作不对ID进行处理
			if (operationType == DAO_OPERATION_UPDATE && fieldName.compareToIgnoreCase("n_id") == 0)
			{
				continue;
			}
			
			Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(fieldName);//字段的类型和值
			String curValueExp = null;
			//根据数据库操作类型获取条件操作符（默认为“逗号”）
			int conditionOp = getConditionOp(operationType);
			//根据字段值，以及数据库操作类型，构造当前字段值表达式
			curValueExp = getValueExp(fieldTypeAndValue, operationType);

			//添加当前字段表达式
			if(curValueExp != null && curValueExp.length() > 0)
			{
				if(valueExp.length() > 0)
				{
					valueExp.append(" " + getConditionOpSymbol(conditionOp) + " ");
				}
				valueExp.append(curValueExp);
			}
		}
		return valueExp.toString();
	}
	
	private int getFieldOp(String suffix)
	{
		if(getName(FIELD_ABOVE).equals(suffix))
		{
			return FIELD_ABOVE;
		}
		else if(getName(FIELD_BELOW).equals(suffix))
		{
			return FIELD_BELOW;
		}
		else if(getName(FIELD_ABOVE_EQU).equals(suffix))
		{
			return FIELD_ABOVE_EQU;
		}
		else if(getName(FIELD_BELOW_EQU).equals(suffix))
		{
			return FIELD_BELOW_EQU;
		}
		else if(getName(FIELD_LIKE).equals(suffix))
		{
			return FIELD_LIKE;
		}
		//默认为“等于”操作
		else
		{
			return FIELD_EQU;
		}
	}
	
	private int getConditionOp(int operationType)
	{
		int result = -1;
		switch(operationType)
		{
			case (DAO_OPERATION_FIND):
				result = CONDITION_AND;
				break;
			default:
				result = CONDITION_COMMA;
				break;
		}
		return result;
	}

	abstract protected String getFieldOpSymbol(int fieldOp);
	abstract protected String getConditionOpSymbol(int conditionOp);
	
	abstract protected String getFieldExp(String fieldName, int fieldOp, Object[] fieldTypeAndValue, int operationType) throws SystemException;
	abstract protected String getValueExp(Object[] fieldTypeAndValue, int operationType) throws SystemException;;

	/**
	 * @param usedFields
	 * @param usedFieldMap
	 */
	protected long setPrepareStatementByFieldValue(String[] usedFields, HashMap usedFieldMap, int operationType)
		throws SystemException
	{
		long returnId = -1;
		if(usedFields == null || usedFields.length <= 0)
		{
			return returnId;
		}
		
		int psPos = 1;//PrepareStatement中“？”的位置
		for (int i = 0; i < usedFields.length; i++)
		{
			String fieldName = usedFields[i];
			Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(fieldName);//字段的类型和值
			String fieldType = (String)fieldTypeAndValue[0];
			Object fieldValue = fieldTypeAndValue[1];
			//log.debug("Field Name: "+ fieldName + " Value: " + fieldValue);

			try
			{
				switch(operationType)
				{
					case DAO_OPERATION_ADD:
						{
							if (Long.class.getName().equals(fieldType))
							{
								long value = ((Long)(fieldValue)).longValue();
								//添加操作对ID进行特殊处理
								if (fieldName.compareToIgnoreCase("n_id") == 0)
								{
									if (idType == ID_TYPE_MAXID)
									{
										value = getMaxID();
									}
									else if (idType == ID_TYPE_SQUENCE)
									{
										value = geSequenceID();
									}
									else//idType == ID_TYPE_APPOINT
									{
										//value保持不变
									}
									returnId = value;
								}
								transPS.setLong(psPos, value);
							}
							else if (Double.class.getName().equals(fieldType))
							{
								if(((Double)fieldTypeAndValue[1]) == null
										|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
								{
									continue;//滤过初试值为空的double型
								}
								else
								{
									double value = ((Double) (fieldValue)).doubleValue();
									transPS.setDouble(psPos, value);
								}
							}
							else if (String.class.getName().equals(fieldType))
							{
								transPS.setString(psPos, (String) fieldValue);
							}
							else if (Date.class.getName().equals(fieldType))
							{
								Date time = (Date) fieldValue;
								if (time == null || time.equals(Env.getNullTimeStamp()))
								{
									time = null;
									transPS.setTimestamp(psPos, null);
								}
								else
								{
									transPS.setTimestamp(psPos, new Timestamp(time.getTime()));
								}
							}
							else
							{
								throw new SystemException("DataEntity中出现未知的数据类型：type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
							}
							break;
						}
					case DAO_OPERATION_UPDATE:
						{
							if (Long.class.getName().equals(fieldType))
							{
								//更新操作不对ID进行处理
								if (fieldName.compareToIgnoreCase("n_id") == 0)
								{
									continue;
								}
								
								long value = ((Long)(fieldValue)).longValue();
								transPS.setLong(psPos, value);
							}
							else if (Double.class.getName().equals(fieldType))
							{
								if(((Double)fieldTypeAndValue[1]) == null
										|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
								{
									continue;//滤过初试值为空的double型
								}
								else
								{
									double value = ((Double) (fieldValue)).doubleValue();
									transPS.setDouble(psPos, value);
								}
							}
							else if (String.class.getName().equals(fieldType))
							{
								transPS.setString(psPos, (String) fieldValue);
							}
							else if (Date.class.getName().equals(fieldType))
							{
								Date time = (Date) fieldValue;
								if (time == null || time.equals(Env.getNullTimeStamp()))
								{
									time = null;
									transPS.setTimestamp(psPos, null);
								}
								else
								{
									transPS.setTimestamp(psPos, new Timestamp(time.getTime()));
								}
							}
							else
							{
								throw new SystemException("DataEntity中出现未知的数据类型：type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
							}
							break;
						}
					case DAO_OPERATION_DEL:
						{
							break;
						}
					case DAO_OPERATION_FIND:
						{
							if (Long.class.getName().equals(fieldType))
							{
								if(((Long)fieldTypeAndValue[1]) != null
										&& ((Long)fieldTypeAndValue[1]).longValue() != -1)
								{
									long value = ((Long)(fieldValue)).longValue();
									transPS.setLong(psPos, value);
								}
								else
								{
									//对查找方法滤过初始值
									continue;
								}
							}
							else if (Double.class.getName().equals(fieldType))
							{
								if(((Double)fieldTypeAndValue[1]) == null
										|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
								{
									//对查找方法滤过初始值
									continue;
								}
								else
								{
									double value = ((Double) (fieldValue)).doubleValue();
									transPS.setDouble(psPos, value);
								}
							}
							else if (String.class.getName().equals(fieldType))
							{
								if(((String)fieldTypeAndValue[1]) != null
										&& ((String)fieldTypeAndValue[1]).trim().length() > 0)
								{
									transPS.setString(psPos, (String) fieldValue);
								}
								else
								{
									continue;
								}
							}
							else if (Date.class.getName().equals(fieldType))
							{

								if(((Date)fieldTypeAndValue[1]) != null
										&& ((Date)fieldTypeAndValue[1]).getTime() != Env.getNullTimeStamp().getTime())
								{
									Date time = (Date) fieldValue;
									transPS.setTimestamp(psPos, new Timestamp(time.getTime()));
								}
								else
								{
									//对查找方法滤过初始值
									continue;
								}
							}
							else
							{
								throw new SystemException("DataEntity中出现未知的数据类型：type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
							}
							break;
						}
				}
				psPos++;
			}
			catch (Exception e)
			{
				throw new SystemException("设置PrepareStatement时出现异常，出错原因："+e.getMessage(), e);
			}
		}
		return returnId;
	}
    
    /**
     * @param usedFields
     * @param usedFieldMap
     */
    protected long setPrepareStatementByFieldValue(String[] usedFields, HashMap usedFieldMap, int operationType, int beginPos)
        throws SystemException
    {
        if(usedFields == null || usedFields.length <= 0)
        {
            return beginPos;
        }
        
        int psPos = beginPos;//PrepareStatement中“？”的位置
        for (int i = 0; i < usedFields.length; i++)
        {
            String fieldName = usedFields[i];
            Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(fieldName);//字段的类型和值
            String fieldType = (String)fieldTypeAndValue[0];
            Object fieldValue = fieldTypeAndValue[1];
            //log.debug("Field Name: "+ fieldName + " Value: " + fieldValue);

            try
            {
                switch(operationType)
                {
                    case DAO_OPERATION_ADD:
                        {
                            if (Long.class.getName().equals(fieldType))
                            {
                                long value = ((Long)(fieldValue)).longValue();
                                //添加操作对ID进行特殊处理
                                if (fieldName.compareToIgnoreCase("n_id") == 0)
                                {
                                    if (idType == ID_TYPE_MAXID)
                                    {
                                        value = getMaxID();
                                    }
                                    else if (idType == ID_TYPE_SQUENCE)
                                    {
                                        value = geSequenceID();
                                    }
                                    else//idType == ID_TYPE_APPOINT
                                    {
                                        //value保持不变
                                    }
//                                    psPos = value;
                                }
                                transPS.setLong(psPos, value);
                            }
                            else if (Double.class.getName().equals(fieldType))
                            {
                                if(((Double)fieldTypeAndValue[1]) == null
                                        || Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
                                {
                                    continue;//滤过初试值为空的double型
                                }
                                else
                                {
                                    double value = ((Double) (fieldValue)).doubleValue();
                                    transPS.setDouble(psPos, value);
                                }
                            }
                            else if (String.class.getName().equals(fieldType))
                            {
                                transPS.setString(psPos, (String) fieldValue);
                            }
                            else if (Date.class.getName().equals(fieldType))
                            {
                                Date time = (Date) fieldValue;
                                if (time == null || time.equals(Env.getNullTimeStamp()))
                                {
                                    time = null;
                                    transPS.setTimestamp(psPos, null);
                                }
                                else
                                {
                                    transPS.setTimestamp(psPos, new Timestamp(time.getTime()));
                                }
                            }
                            else
                            {
                                throw new SystemException("DataEntity中出现未知的数据类型：type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
                            }
                            break;
                        }
                    case DAO_OPERATION_UPDATE:
                        {
                            if (Long.class.getName().equals(fieldType))
                            {
                                //更新操作不对ID进行处理
                                if (fieldName.compareToIgnoreCase("n_id") == 0)
                                {
                                    continue;
                                }
                                
                                long value = ((Long)(fieldValue)).longValue();
                                transPS.setLong(psPos, value);
                            }
                            else if (Double.class.getName().equals(fieldType))
                            {
                                if(((Double)fieldTypeAndValue[1]) == null
                                        || Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
                                {
                                    continue;//滤过初试值为空的double型
                                }
                                else
                                {
                                    double value = ((Double) (fieldValue)).doubleValue();
                                    transPS.setDouble(psPos, value);
                                }
                            }
                            else if (String.class.getName().equals(fieldType))
                            {
                                transPS.setString(psPos, (String) fieldValue);
                            }
                            else if (Date.class.getName().equals(fieldType))
                            {
                                Date time = (Date) fieldValue;
                                if (time == null || time.equals(Env.getNullTimeStamp()))
                                {
                                    time = null;
                                    transPS.setTimestamp(psPos, null);
                                }
                                else
                                {
                                    transPS.setTimestamp(psPos, new Timestamp(time.getTime()));
                                }
                            }
                            else
                            {
                                throw new SystemException("DataEntity中出现未知的数据类型：type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
                            }
                            break;
                        }
                    case DAO_OPERATION_DEL:
                        {
                            break;
                        }
                    case DAO_OPERATION_FIND:
                        {
                            if (Long.class.getName().equals(fieldType))
                            {
                                if(((Long)fieldTypeAndValue[1]) != null
                                        && ((Long)fieldTypeAndValue[1]).longValue() != -1)
                                {
                                    long value = ((Long)(fieldValue)).longValue();
                                    transPS.setLong(psPos, value);
                                }
                                else
                                {
                                    //对查找方法滤过初始值
                                    continue;
                                }
                            }
                            else if (Double.class.getName().equals(fieldType))
                            {
                                if(((Double)fieldTypeAndValue[1]) == null
                                        || Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
                                {
                                    //对查找方法滤过初始值
                                    continue;
                                }
                                else
                                {
                                    double value = ((Double) (fieldValue)).doubleValue();
                                    transPS.setDouble(psPos, value);
                                }
                            }
                            else if (String.class.getName().equals(fieldType))
                            {
                                if(((String)fieldTypeAndValue[1]) != null
                                        && ((String)fieldTypeAndValue[1]).trim().length() > 0)
                                {
                                    transPS.setString(psPos, (String) fieldValue);
                                }
                                else
                                {
                                    continue;
                                }
                            }
                            else if (Date.class.getName().equals(fieldType))
                            {

                                if(((Date)fieldTypeAndValue[1]) != null
                                        && ((Date)fieldTypeAndValue[1]).getTime() != Env.getNullTimeStamp().getTime())
                                {
                                    Date time = (Date) fieldValue;
                                    transPS.setTimestamp(psPos, new Timestamp(time.getTime()));
                                }
                                else
                                {
                                    //对查找方法滤过初始值
                                    continue;
                                }
                            }
                            else
                            {
                                throw new SystemException("DataEntity中出现未知的数据类型：type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
                            }
                            break;
                        }
                }
                psPos++;
            }
            catch (Exception e)
            {
                throw new SystemException("设置PrepareStatement时出现异常，出错原因："+e.getMessage(), e);
            }
        }
        return psPos;
    }    
	
	protected abstract long geSequenceID() throws SystemException;
	protected abstract long getMaxID() throws SystemException;

}