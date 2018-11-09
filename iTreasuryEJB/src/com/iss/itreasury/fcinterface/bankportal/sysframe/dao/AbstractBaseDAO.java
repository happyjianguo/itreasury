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
	/**DAO�������ͳ�������*/
	protected static final int DAO_OPERATION_ADD = 1; //��������
	protected static final int DAO_OPERATION_DEL = 2; //ɾ������
	protected static final int DAO_OPERATION_UPDATE = 3; //���²���
	protected static final int DAO_OPERATION_FIND = 4; //��ѯ����
	/**��¼IDȡֵ��ʽ��������*/
	protected static final int ID_TYPE_MAXID = 101; //IDȡmax id
	protected static final int ID_TYPE_SQUENCE = 102; //IDȡsequence
	protected static final int ID_TYPE_APPOINT = 103; //IDΪ�ⲿָ��
	
	/** ��־���� */
	private Logger log = new Logger(AbstractBaseDAO.class);
	/** DAO���������ݿ���� */
	protected String strTableName = null;
	/** DAOʹ�õ����ݿ����� */
	protected Connection transConn = null;
	/** ���ݿ������Ƿ�����ά���� */
	private boolean isSelfManagedConn = false;
	/** DAOʹ�õĽ���� */
	protected ResultSet transRS = null;
	/** DAOʹ�õ�PreparedStatement */
	protected PreparedStatement transPS = null;

	/** ID���ͣ�Ĭ��ȡmax id */
	private int idType = ID_TYPE_MAXID;

	/** ���ݿ��ֶ��Ƿ�������ǰ׺ */
	private boolean isNeedPrefix = true;

	
	/**���ݿ��ֶεıȽϷ�ʽ*/
	protected static final int FIELD_EQU = 201; 				//���ڣ�Ĭ�ϣ�
	protected static final int FIELD_ABOVE = 202; 				//����
	protected static final int FIELD_BELOW = 203; 				//С��
	protected static final int FIELD_ABOVE_EQU = 204; 			//���ڵ���
	protected static final int FIELD_BELOW_EQU = 205; 			//С�ڵ���
	protected static final int FIELD_LIKE = 206; 				//like
	protected static final String SUFFIX_EQU    		= "_e";	//���ڣ�Ĭ�ϣ�
	protected static final String SUFFIX_ABOVE      	= "_a"; //����
	protected static final String SUFFIX_BELOW      	= "_b"; //С��
	protected static final String SUFFIX_ABOVE_EQU      = "_ae";//���ڵ���
	protected static final String SUFFIX_BELOW_EQU      = "_be";//С�ڵ���
	protected static final String SUFFIX_LIKE      		= "_l"; //like
    protected static final String getName(long lCode)
    {
        String strReturn = ""; //��ʼ������ֵ
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
	/**���ݿ����������ӷ�ʽ*/
	protected static final int CONDITION_COMMA = 251; //����
	protected static final int CONDITION_AND = 252; //������
	protected static final int CONDITION_OR = 253; //������
	
	/**
	 * ���췽��
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
	 * ����ID����(Ĭ����ͨ��max id��ȡ)
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
	 * DAO��ʹ���࣬��ʹ�þ����DAO����ǰ�������ȵ��ô˷���!!!!
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
			throw new SystemException("���ݿ��ʹ���쳣����", e);
		}
	}

	/**
	 * PreparedStatement׼������
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
			log.debug("SQL���:" + sql);
			transPS = transConn.prepareStatement(sql);
		}
		catch (SQLException e)
		{
			throw new SystemException("PrepareStatement��ʹ���쳣����", e);
		}
		return transPS;
	}

	/**
	 * ���ݿ�ִ�в���
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
			throw new SystemException("���ݿ�ִ�в����쳣����", e);
		}
		return transRS;
	}

	/**
	 * ���ݿ���²���
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
			throw new SystemException("���ݿ���²����쳣����", e);
		}
	}

	/**
	 * DAO������������DAO�����Ľ�β������ô˷���!!!!!!!!
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
			throw new SystemException("���ݿ�ر��쳣����", e);
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
			String fieldName = null;//�ֶ�����
			String suffix = null;//�ֶκ�׺
			int fieldOp = -1;//�ֶεĲ�������
			int conditionOp = -1;//������������
			Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(usedFields[i]);//�ֶε����ͺ�ֵ
			String curFieldExp = null;//��ǰ�ֶα��ʽ
			
			//��ȡ�ֶ����ƺͲ�����׺
			int pos = usedFields[i].indexOf("_");
			if(pos != -1)
			{
				if(this.isNeedPrefix)
				{
					pos = usedFields[i].indexOf("_", pos+1);
					if(pos != -1)
					{
						suffix = usedFields[i].substring(pos);
						//��׺���������ڼ����Ѷ���Ĳ�������
						//���������������ӣ�����ܲ���������չ��
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
					//��׺���������ڼ����Ѷ���Ĳ�������
					//���������������ӣ�����ܲ���������չ��
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
			
			//���²�������ID���д���
			if (operationType == DAO_OPERATION_UPDATE && fieldName.compareToIgnoreCase("n_id") == 0)
			{
				continue;
			}
			
			//�����ֶβ�����׺����ֶβ������ͣ�Ĭ��Ϊ�����ڡ�������
			fieldOp = getFieldOp(suffix);
			//�������ݿ�������ͻ�ȡ������������Ĭ��Ϊ�����š���
			conditionOp = getConditionOp(operationType);
			
			//�����ֶ����ƣ��ֶβ������ͣ��Լ����ݿ�������ͣ����쵱ǰ�ֶα��ʽ
			curFieldExp = getFieldExp(fieldName, fieldOp, fieldTypeAndValue, operationType);
			
			//��ӵ�ǰ�ֶα��ʽ
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
			
			//���²�������ID���д���
			if (operationType == DAO_OPERATION_UPDATE && fieldName.compareToIgnoreCase("n_id") == 0)
			{
				continue;
			}
			
			Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(fieldName);//�ֶε����ͺ�ֵ
			String curValueExp = null;
			//�������ݿ�������ͻ�ȡ������������Ĭ��Ϊ�����š���
			int conditionOp = getConditionOp(operationType);
			//�����ֶ�ֵ���Լ����ݿ�������ͣ����쵱ǰ�ֶ�ֵ���ʽ
			curValueExp = getValueExp(fieldTypeAndValue, operationType);

			//��ӵ�ǰ�ֶα��ʽ
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
		//Ĭ��Ϊ�����ڡ�����
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
		
		int psPos = 1;//PrepareStatement�С�������λ��
		for (int i = 0; i < usedFields.length; i++)
		{
			String fieldName = usedFields[i];
			Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(fieldName);//�ֶε����ͺ�ֵ
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
								//��Ӳ�����ID�������⴦��
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
										//value���ֲ���
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
									continue;//�˹�����ֵΪ�յ�double��
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
								throw new SystemException("DataEntity�г���δ֪���������ͣ�type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
							}
							break;
						}
					case DAO_OPERATION_UPDATE:
						{
							if (Long.class.getName().equals(fieldType))
							{
								//���²�������ID���д���
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
									continue;//�˹�����ֵΪ�յ�double��
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
								throw new SystemException("DataEntity�г���δ֪���������ͣ�type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
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
									//�Բ��ҷ����˹���ʼֵ
									continue;
								}
							}
							else if (Double.class.getName().equals(fieldType))
							{
								if(((Double)fieldTypeAndValue[1]) == null
										|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
								{
									//�Բ��ҷ����˹���ʼֵ
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
									//�Բ��ҷ����˹���ʼֵ
									continue;
								}
							}
							else
							{
								throw new SystemException("DataEntity�г���δ֪���������ͣ�type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
							}
							break;
						}
				}
				psPos++;
			}
			catch (Exception e)
			{
				throw new SystemException("����PrepareStatementʱ�����쳣������ԭ��"+e.getMessage(), e);
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
        
        int psPos = beginPos;//PrepareStatement�С�������λ��
        for (int i = 0; i < usedFields.length; i++)
        {
            String fieldName = usedFields[i];
            Object[] fieldTypeAndValue = (Object[])usedFieldMap.get(fieldName);//�ֶε����ͺ�ֵ
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
                                //��Ӳ�����ID�������⴦��
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
                                        //value���ֲ���
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
                                    continue;//�˹�����ֵΪ�յ�double��
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
                                throw new SystemException("DataEntity�г���δ֪���������ͣ�type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
                            }
                            break;
                        }
                    case DAO_OPERATION_UPDATE:
                        {
                            if (Long.class.getName().equals(fieldType))
                            {
                                //���²�������ID���д���
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
                                    continue;//�˹�����ֵΪ�յ�double��
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
                                throw new SystemException("DataEntity�г���δ֪���������ͣ�type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
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
                                    //�Բ��ҷ����˹���ʼֵ
                                    continue;
                                }
                            }
                            else if (Double.class.getName().equals(fieldType))
                            {
                                if(((Double)fieldTypeAndValue[1]) == null
                                        || Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
                                {
                                    //�Բ��ҷ����˹���ʼֵ
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
                                    //�Բ��ҷ����˹���ʼֵ
                                    continue;
                                }
                            }
                            else
                            {
                                throw new SystemException("DataEntity�г���δ֪���������ͣ�type["+fieldValue.getClass().getName()+"] value["+fieldValue+"]");
                            }
                            break;
                        }
                }
                psPos++;
            }
            catch (Exception e)
            {
                throw new SystemException("����PrepareStatementʱ�����쳣������ԭ��"+e.getMessage(), e);
            }
        }
        return psPos;
    }    
	
	protected abstract long geSequenceID() throws SystemException;
	protected abstract long getMaxID() throws SystemException;

}