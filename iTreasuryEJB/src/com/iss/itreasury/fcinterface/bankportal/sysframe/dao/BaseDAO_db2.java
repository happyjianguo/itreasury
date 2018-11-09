package com.iss.itreasury.fcinterface.bankportal.sysframe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import com.iss.itreasury.fcinterface.bankportal.privilegemgt.DataPrivilegeUtil;
import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOUtil;
import com.iss.itreasury.fcinterface.bankportal.util.Env;

public class BaseDAO_db2 extends AbstractBaseDAO implements BaseDAO
{
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 * @throws SystemException
	 */
	protected BaseDAO_db2(String tableName, boolean isNeedPrefix, Connection conn)
		throws SystemException
	{
		super(tableName, isNeedPrefix, conn);
	}

	/**
	 * ����sequence�����������ȡ��һ�����õ�IDֵ sequence��������:
	 * seq_+tablename������,SEC_Remark���sequenceΪSEQ_SEC_Remark
	 * 
	 * @param
	 * @param
	 * @return ��һ��ID
	 * @throws ITreasuryDAOException
	 */
	public long geSequenceID() throws SystemException
	{
		/**
		 * �˷���ֻ����DAO�б����ã��������´������ݿ���Դ�����Ҳ����Ҫ �ر����ݿ���Դ
		 */
		long id = -1;
		String strSeqName = "SEQ_" + strTableName;
		//String sql = "SELECT " + strSeqName + ".nextval nextid from dual";
		String sql ="select nextval for"+ strSeqName+" as nextid  from sysibm.sysdummy1";
		//prepareStatement(sql);
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		try
		{
			//�ڲ�ά��RS��PS�����򽫻������ͻ,��Connectionʹ��ͬһ��
			if (transConn == null)
			{
				throw new SystemException("the connection is null");
			}

			localPS = transConn.prepareStatement(sql);
			localRS = localPS.executeQuery();

			if (localRS.next())
			{
				id = localRS.getLong("nextid");
			}
			return id;
		}
		catch (SQLException e)
		{
			throw new SystemException("���ݿ��ȡID���쳣������ԭ��"+e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (localRS != null)
				{
					localRS.close();
				}
				if (localPS != null)
				{
					localPS.close();
				}
			}
			catch(Exception e)
			{
			}
		}
	}

	public long getMaxID() throws SystemException
	{
		long id = -1;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select coalesce(max(n_id)+1,1) n_id from " + strTableName);
		try
		{
			//�ڲ�ά��RS��PS�����򽫻������ͻ,��Connectionʹ��ͬһ��
			if (transConn == null)
			{
				throw new SystemException("the connection is null");
			}

			localPS = transConn.prepareStatement(sb.toString());
			ResultSet rs = localPS.executeQuery();
			if (rs.next())
			{
				id = rs.getLong("n_id");
			}
			return id;
		}
		catch (SQLException e)
		{
			throw new SystemException("���ݿ��ȡID�����쳣������ԭ��"+e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (localRS != null)
				{
					localRS.close();
				}
				if (localPS != null)
				{
					localPS.close();
				}
			}
			catch(Exception e)
			{
			}
		}
	}

	/**
	 * ���ݿ�����������������ID����������set����ȷ����ֵ
	 * 
	 * @param dataEntity
	 *            ��Ҫ���������ݿ���Ӧ��Data Entity��ʵ��
	 * @param
	 * @return �²�����ID
	 * @throws ITreasuryDAOException
	 */
	public long add(BaseDataEntity dataEntity) throws SystemException
	{
		try
		{
			String[] usedFields = dataEntity.getUsedField();
			HashMap usedFieldMap = dataEntity.getUsedFieldMap();
			
			initDAO();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO "+ strTableName+" (\n");
			buffer.append(generateFieldExp(usedFields, usedFieldMap, DAO_OPERATION_ADD));
			buffer.append("\n) "+ "VALUES (\n");				
			buffer.append(generateValueExp(usedFields, usedFieldMap, DAO_OPERATION_ADD));
			buffer.append("\n) \n");
			
			String strSQL = buffer.toString();
			prepareStatement(strSQL);
	
			long id = setPrepareStatementByFieldValue(usedFields, usedFieldMap, DAO_OPERATION_ADD);
			executeUpdate();
			return id;
		}
		catch(SystemException e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}

	/**
	 * ���ݿ���²���
	 * 
	 * @param dataEntity ��Ҫ�����µ����ݿ���Ӧ��Data Entity��ʵ��
	 * @param
	 * @return @throws ITreasuryDAOException
	 */
	public void update(BaseDataEntity dataEntity) throws SystemException
	{
		try
		{
			String[] usedFields = dataEntity.getUsedField();
			HashMap usedFieldMap = dataEntity.getUsedFieldMap();
	
			initDAO();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE "+ strTableName+" SET \n");
			String updateFieldExp = generateFieldExp(usedFields, usedFieldMap, DAO_OPERATION_UPDATE);
			
			//���û���κ��ֶ���Ҫ���£������κβ���
			if(updateFieldExp == null || updateFieldExp.trim().length() <= 0)
			{
				return;
			}
			
			//���򣬽��и���
			buffer.append(updateFieldExp);
			buffer.append(" WHERE n_id = "+ dataEntity.getId());
			
			String strSQL = buffer.toString();
			prepareStatement(strSQL);
			
			setPrepareStatementByFieldValue(usedFields, usedFieldMap, DAO_OPERATION_UPDATE);
			executeUpdate();	
		}
		catch(SystemException e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}

	/**
	 * ���ݿ���Ҳ���
	 * @param id������
	 * @param className ��Ҫ���ҵ����ݿ���Ӧ��Data Entity������
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public BaseDataEntity findByID(long id, Class className)
			throws SystemException
	{
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE n_id = " + id);
			String strSQL = buffer.toString();
	
			prepareStatement(strSQL);
			
			//System.out.println("++++++++++++"+strSQL);
			executeQuery();
	
			BaseDataEntity res = DAOUtil.getDataEntityFromResultSet(transRS, className, isNeedPrefix());
			return res;
		}
		catch(SystemException e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}

	/**
	 * ���ݿ���Ҳ���
	 * @param conditionInfo ��ѯ�����������set��ֵ������Ϊ���ֶ�Ϊ��ѯ��������ƥ�䡡����
	 * @param className ��Ҫ���ҵ����ݿ���Ӧ��Data Entity������
	 * @param orderByString order by SQL��䣬�ֹ����
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByCondition(BaseDataEntity conditionInfo,
			String orderByString) throws SystemException
	{
		try
		{
			String[] usedFields = conditionInfo.getUsedField();
			HashMap usedFieldMap = conditionInfo.getUsedFieldMap();
			initDAO();			
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE 1=1 ");
			String fieldExp = generateFieldExp(usedFields, usedFieldMap, DAO_OPERATION_FIND);
			if(fieldExp != null && fieldExp.trim().length() > 0)
			{
				buffer.append("and \n" + fieldExp.trim());
			}
			if(orderByString != null)
			{
				buffer.append(" \n" + orderByString);
			}
			
			String strSQL = buffer.toString();
			prepareStatement(DataPrivilegeUtil.processSQL(strSQL,conditionInfo.getSessionMng()));
			
			setPrepareStatementByFieldValue(usedFields, usedFieldMap, DAO_OPERATION_FIND);
			
			executeQuery();
	
			Collection res = DAOUtil.getDataEntitiesFromResultSet(transRS, conditionInfo.getClass(), isNeedPrefix());
			return res;
		}
		catch(SystemException e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}
	
	public Collection findAll(Class baseDataEntityClass, String orderByString) throws SystemException
	{
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			if(orderByString != null)
			{
				buffer.append(" "+orderByString);
			}
			String strSQL = buffer.toString();
			prepareStatement(strSQL);
	
			executeQuery();
	
			Collection res = DAOUtil.getDataEntitiesFromResultSet(transRS, baseDataEntityClass, isNeedPrefix());
			return res;
		}
		catch(SystemException e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}

	/**
	 * �����ݿ��и���id����ɾ��һ����¼
	 * ʹ��ʱ��ȷ�����߼�ɾ����������ɾ������
	 * */
	public void deletePhysically(long id) throws SystemException
	{
		try
		{
			initDAO();
			String strSQL = "delete from "+strTableName +" where n_id = "+ id;
			prepareStatement(strSQL);
			executeUpdate();
			finalizeDAO();
		}
		catch(SystemException e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}

	/**
	 * ɾ�����ݿ������еļ�¼
	 * @throws SystemException
	 */
	public void deleteAll() throws SystemException
	{
		try
		{
			initDAO();
			String strSQL = "delete from "+strTableName;
			prepareStatement(strSQL);
			executeUpdate();
			finalizeDAO();
		}
		catch(SystemException e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getFieldOpSymbol(int)
	 */
	protected String getFieldOpSymbol(int fieldOp)
	{
        String strReturn = ""; //��ʼ������ֵ
        switch ((int) fieldOp)
        {
            case (int) FIELD_EQU:
                strReturn = "=";
                break;
            case (int) FIELD_ABOVE:
                strReturn = ">";
                break;
            case (int) FIELD_BELOW:
                strReturn = "<";
                break;
            case (int) FIELD_ABOVE_EQU:
                strReturn = ">=";
                break;
            case (int) FIELD_BELOW_EQU:
                strReturn = "<=";
                break;
            case (int) FIELD_LIKE:
                strReturn = "like";
                break;
        }
        return strReturn;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getConditionOpSymbol(int)
	 */
	protected String getConditionOpSymbol(int conditionOp)
	{
        String strReturn = ""; //��ʼ������ֵ
        switch ((int) conditionOp)
        {
            case (int) CONDITION_COMMA:
                strReturn = ",";
                break;
            case (int) CONDITION_AND:
                strReturn = "and";
                break;
            case (int) CONDITION_OR:
                strReturn = "or";
                break;
        }
        return strReturn;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getFieldExp(java.lang.String, int, java.lang.Object, int)
	 */
	protected String getFieldExp(String fieldName, int fieldOp, Object[] fieldTypeAndValue, int operationType)
		throws SystemException
	{
		String fieldExp = "";
		String fieldType = (String)fieldTypeAndValue[0];
		
		switch(operationType)
		{
			case DAO_OPERATION_ADD:
				{
					if (Long.class.getName().equals(fieldType))
					{
						fieldExp = fieldName;
					}
					else if (Double.class.getName().equals(fieldType))
					{
						if(((Double)fieldTypeAndValue[1]) == null
								|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
						{
							fieldExp = "";
						}
						else
						{
							fieldExp = fieldName;
						}
					}
					else if (String.class.getName().equals(fieldType))
					{
						fieldExp = fieldName;
					}
					else if (Date.class.getName().equals(fieldType))
					{
						fieldExp = fieldName;
					}
					else
					{
						throw new SystemException("�����ֶα��ʽʱ����δ֪���������ͣ�type["+fieldType+"] value["+fieldTypeAndValue[1]+"]");
					}
					break;
				}
			case DAO_OPERATION_UPDATE:
				{
					if (Long.class.getName().equals(fieldType))
					{
						fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " ?";
					}
					else if (Double.class.getName().equals(fieldType))
					{
						if(((Double)fieldTypeAndValue[1]) == null
								|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
						{
							fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " null";
						}
						else
						{
							fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " ?";
						}
					}
					else if (String.class.getName().equals(fieldType))
					{
						fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " ?";
					}
					else if (Date.class.getName().equals(fieldType))
					{
						fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " ?";
					}
					else
					{
						throw new SystemException("�����ֶα��ʽʱ����δ֪���������ͣ�type["+fieldType+"] value["+fieldTypeAndValue[1]+"]");
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
							
							if("n_officeid".equals(fieldName))
							{
								//���ӶԶ���´��Ĺ�������
								if(!Env.isHQ(((Long)fieldTypeAndValue[1]).longValue()))
								{
									fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + "CAST(? AS DECIMAL)";
								}					
								else
								{
									fieldTypeAndValue[1]=new Long(-1);
									fieldExp = "";
								}
							}
							else
							{
									fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " CAST(? AS DECIMAL)";
							}
						}
						else
						{
							fieldExp = "";
						}
					}
					else if (Double.class.getName().equals(fieldType))
					{
						if(((Double)fieldTypeAndValue[1]) == null
								|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
						{
							fieldExp = "";
						}
						else
						{
							fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " ?";
						}
					}
					else if (String.class.getName().equals(fieldType))
					{
						if(((String)fieldTypeAndValue[1]) != null
								&& ((String)fieldTypeAndValue[1]).trim().length() > 0)
						{
							fieldExp = fieldName + " " + getFieldOpSymbol(fieldOp) + " ?";
						}
						else
						{
							fieldExp = "";
						}
					}
					else if (Date.class.getName().equals(fieldType))
					{
						if(((Date)fieldTypeAndValue[1]) != null
								&& ((Date)fieldTypeAndValue[1]).getTime() != Env.getNullTimeStamp().getTime())
						{
							fieldExp = "db2_to_char(cast("+fieldName+" as timestamp),'yyyyMMdd') " + getFieldOpSymbol(fieldOp) + "db2_to_char(cast(? as timestamp),'yyyyMMdd')";
						}
						else
						{
							fieldExp = "";
						}
					}
					else
					{
						throw new SystemException("�����ֶα��ʽʱ����δ֪���������ͣ�type["+fieldType+"] value["+fieldTypeAndValue[1]+"]");
					}
					break;
				}
		}

		return fieldExp;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dao.AbstractBaseDAO#getValueExp(java.lang.Object, int)
	 */
	protected String getValueExp(Object[] fieldTypeAndValue, int operationType)
		throws SystemException	
	{
		String valueExp = "";
		String fieldType = (String)fieldTypeAndValue[0];
		
		switch(operationType)
		{
			case DAO_OPERATION_ADD:
				{
					if (Long.class.getName().equals(fieldType))
					{
						valueExp = "?";// + ((Long)(fieldTypeAndValue[1])).longValue();
					}
					else if (Double.class.getName().equals(fieldType))
					{
						if(((Double)fieldTypeAndValue[1]) == null
								|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
						{
							valueExp = "";
						}
						else
						{
							valueExp = "?";// + ((Double)(fieldTypeAndValue[1])).doubleValue();
						}
					}
					else if (String.class.getName().equals(fieldType))
					{
						valueExp = "?";// + (String)fieldTypeAndValue[1];
					}
					else if (Date.class.getName().equals(fieldType))
					{
						valueExp = "?";//to_date("+(Date)fieldTypeAndValue[1]+",'yyyy-MM-dd')";
					}
					else
					{
						throw new SystemException("�����ֶ�ֵ���ʽʱ����δ֪���������ͣ�type["+fieldType+"] value["+fieldTypeAndValue[1]+"]");
					}
					break;
				}
			case DAO_OPERATION_UPDATE:
				{
					if (Long.class.getName().equals(fieldType))
					{
						valueExp = "?";// + ((Long)(fieldTypeAndValue[1])).longValue();
					}
					else if (Double.class.getName().equals(fieldType))
					{
						if(((Double)fieldTypeAndValue[1]) == null
								|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
						{
							valueExp = "";
						}
						else
						{
							valueExp = "?";// + ((Double)(fieldTypeAndValue[1])).doubleValue(); 
						}
					}
					else if (String.class.getName().equals(fieldType))
					{
						valueExp = "?";// + (String)fieldTypeAndValue[1];
					}
					else if (Date.class.getName().equals(fieldType))
					{
						valueExp = "?";//to_date("+(Date)fieldTypeAndValue[1]+",'yyyy-MM-dd')";
					}
					else
					{
						throw new SystemException("�����ֶ�ֵ���ʽʱ����δ֪���������ͣ�type["+fieldType+"] value["+fieldTypeAndValue[1]+"]");
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
							valueExp = "?";// + ((Long)(fieldTypeAndValue[1])).longValue();
						}
						else
						{
							valueExp = "";
						}
					}
					else if (Double.class.getName().equals(fieldType))
					{
						if(((Double)fieldTypeAndValue[1]) == null
								|| Double.isNaN(((Double)fieldTypeAndValue[1]).doubleValue()))
						{
							valueExp = "";
						}
						else
						{
							valueExp = "?";// + ((Double)(fieldTypeAndValue[1])).doubleValue();
						}
					}
					else if (String.class.getName().equals(fieldType))
					{
						if(((String)fieldTypeAndValue[1]) != null
							&& ((String)fieldTypeAndValue[1]).trim().length() > 0)
						{
							valueExp = "?";// + (String)fieldTypeAndValue[1];
						}
						else
						{
							valueExp = "";
						}
					}
					else if (Date.class.getName().equals(fieldType))
					{
						if(((Date)fieldTypeAndValue[1]) != null
								&& ((Date)fieldTypeAndValue[1]).getTime() != Env.getNullTimeStamp().getTime())
						{
							valueExp = "?";//to_date("+(Date)fieldTypeAndValue[1]+",'yyyy-MM-dd')";
						}
						else
						{
							valueExp = "";
						}
					}
					else
					{
						throw new SystemException("�����ֶ�ֵ���ʽʱ����δ֪���������ͣ�type["+fieldType+"] value["+fieldTypeAndValue[1]+"]");
					}
					break;
				}
		}

		return valueExp;
	}
}
