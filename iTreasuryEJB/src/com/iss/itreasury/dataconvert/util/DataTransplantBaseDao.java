/*
 * Created on 2006-3-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.iss.itreasury.dataconvert.fieldgenerator.FieldGenerator;
import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.util.Log4j;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataTransplantBaseDao {
    /** 当前DAO操作的数据库表名 */
    protected String strTableName = "";

    /** 当前DAO的使用的数据库连接 */
    protected Connection connection = null;

    /** connection是否是dao管理 */
    private boolean isDaoManagedCon = true;

    /** 当前DAO的使用的结果集 */
    protected ResultSet resultSet = null;

    /** 当前DAO的使用的PreparedStatement */
    protected PreparedStatement preparedStatement = null;

    protected FieldGenerator fieldGenerator = new MaxFieldGenerator();

    protected Log4j log = new Log4j(1, this);

    /**
     * 从外部注入connection后,就可以由外部管理事务
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
        isDaoManagedCon = false;
    }

    public void setStrTableName(String strTableName) {
        this.strTableName = strTableName;
    }

    public void setFieldGenerator(FieldGenerator fieldGenerator) {
        this.fieldGenerator = fieldGenerator;
    }
    
    /**
     * DAO初使化方法，在使用具体的DAO操作前，必须先调用此方法!!!!
     * 
     * @return
     * @throws RuntimeException
     */
    protected void initDAO() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DataBaseUtil.getConnection();
                isDaoManagedCon = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("数据库初始化异常", e);
        }
    }

    /**
     * PreparedStatement准备工作
     * 
     * @return
     * @throws IllegalStateException
     *             没初始化连接
     * @throws RuntimeException
     *             发生数据库错误
     */
    protected void prepareStatement(String sql) {
        //log.debug("SQL语句:" + sql);
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("连接为空或者已经关闭");
            }
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException("发生数据库错误", e);
        }
    }

    /**
     * 数据库执行操作
     * 
     * @return
     * @throws RuntimeException
     *             发生数据库异常
     */
    protected void executeQuery() {
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("数据库执行操作异常发生", e);
        }
    }

    /**
     * DAO结束操作，在DAO操作的结尾必须调用此方法!!!!!!!!
     * 
     * @return
     * @throws RuntimeException
     */
    protected void finalizeDAO() {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                preparedStatement = null;
            }
            if (connection != null && isDaoManagedCon) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库关闭异常发生", e);
        }
    }

    /**
     * @param dataEntityWithId
     *            必须有一个long型的id属性才能使用这个方法，注意id必须是小写
     * @param baseLineEntity
     *            用dataEntityWithId和baseLineEntity进行比较不同的字段进行添加
     *            如果baseLineEntity为null，则和类型的默认值进行比较
     * @param isOnlyAddValuesDifferentFromDefault
     *            这个值为false时，忽略前面的条件，插入所有字段 
     * @return 新产生的ID
     * @throws RuntimeException
     *             发生数据库异常
     */
    public final long add(Object dataEntityWithId,Object baseLineEntity,boolean isOnlyAddValuesDifferentFromDefault) {
        if (dataEntityWithId == null) {
            throw new NullPointerException("传入了空参数");
        }
        StringBuffer strSql = new StringBuffer();
        strSql.append("INSERT INTO " + strTableName + " (id \n");
        Map keyAndValue = null;
        if(isOnlyAddValuesDifferentFromDefault){
            keyAndValue=TinyBeanUtil.findChangedFieldsOfDataEntity(dataEntityWithId,baseLineEntity); 
        }
        else{
            keyAndValue=TinyBeanUtil.getAllFields(dataEntityWithId);
        }
        //保证加入的字段不含id,id字段已经手工加入
        if(keyAndValue.containsKey("id")){
            keyAndValue.remove("id");
        }
        Set key = keyAndValue.keySet();
        StringBuffer strFields = new StringBuffer();
        for (Iterator i = key.iterator(); i.hasNext();) {
            strFields.append("," + (String) i.next());
        }
        strSql.append(strFields);
        strSql.append(" \n)" + "values (?\n");
        for (int i = 0, n = key.size(); i < n; i++) {
            strSql.append(",?");
        }
        strSql.append(") \n");
        initDAO();
        prepareStatement(strSql.toString());
        long id = ((BigDecimal)fieldGenerator.generateValue(this.strTableName,"id")).longValue();
        try {
            preparedStatement.setLong(1,id);
            //ps此时开始位置已经是2
            setPreparedStatement(this.preparedStatement,keyAndValue,2);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("数据库操作发生异常", e);
        } finally {
            finalizeDAO();
        }
        return id;
    }

    /**
     * 数据库更新操作
     * @param dataEntity
     *            必须有一个long型的id属性才能使用这个方法，注意id必须是小写
     * @param baseLineEntity
     *            用dataEntityWithId和baseLineEntity进行比较不同的字段进行修改
     *            如果baseLineEntity为null，则和类型的默认值进行比较      
     * @throws RuntimeException
     *             发生数据库异常
     */
    public final void update(Object dataEntityWithId,Object baseLineEntity) {
        if (dataEntityWithId == null) {
            throw new NullPointerException("传入了空参数");
        }
        StringBuffer strSql = new StringBuffer();
        strSql.append("update " + strTableName + " set \n");
        Map keyAndValue =TinyBeanUtil.findChangedFieldsOfDataEntity(dataEntityWithId,baseLineEntity);
        Set key = keyAndValue.keySet();
        //保证加入的字段不含id,id字段已经手工加入
        if (key.contains("id")) {
            key.remove("id");
        }
        StringBuffer strFields = new StringBuffer();
        for (Iterator i = key.iterator(); i.hasNext();) {
            strFields.append((String) i.next() + "=?,");
        }
        //去掉最后一个逗号
        strFields.setLength(strFields.length() - 1);
        strSql.append(strFields + " \n");
        try {
            strSql.append("where id="
                    + BeanUtils.getProperty(dataEntityWithId, "id") + " \n");
            initDAO();
            prepareStatement(strSql.toString());
            setPreparedStatement(this.preparedStatement,keyAndValue,1);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("数据库操作发生异常", e);
        } finally {
            finalizeDAO();
        }
    }

    /**
     * @param dataEntity
     * @param baseLineEntity
     *            用dataEntityWithId和baseLineEntity进行比较不同的字段作为条件
     *            如果baseLineEntity为null，则和类型的默认值进行比较     
     * @param orderByStr
     *         参数不能为空
     * @return  不会返回null，查补到返回0长度集合 
     * @throws RuntimeException
     *             发生数据库异常的时候
     */
    public final Collection findByCondition(Object dataEntity,Object baseLineEntity, String orderByStr) {
        if (dataEntity == null || orderByStr == null) {
            throw new NullPointerException("传入了空参数");
        }
        StringBuffer strSql = new StringBuffer();
        strSql.append("select * from " + strTableName + " \n");
        strSql.append("where 1=1 \n");
        Map keyAndValue = TinyBeanUtil.findChangedFieldsOfDataEntity(dataEntity,baseLineEntity);
        Set key = keyAndValue.keySet();
        StringBuffer strFields = new StringBuffer();
        for (Iterator i = key.iterator(); i.hasNext();) {
            strFields.append("and " + (String) i.next() + "=? \n");
        }
        strSql.append(strFields);
        strSql.append(orderByStr + " \n");
        Collection result = null;
        try {
            initDAO();
            prepareStatement(strSql.toString());
            setPreparedStatement(this.preparedStatement,keyAndValue,1);
            executeQuery();
            result = new DataTranserUtil().getDataEntitiesFromResultSet(
                    dataEntity.getClass(), resultSet);
        } catch (Exception e) {
            throw new RuntimeException("数据库操作发生异常", e);
        } finally {
            finalizeDAO();
        }
        return result;
    }
    
    private void setPreparedStatement(PreparedStatement ps,Map keyAndValue,int startPos){
        Set key = keyAndValue.keySet();
        int counter = startPos;
        try{
        for (Iterator i = key.iterator(); i.hasNext();) {
            Object value=keyAndValue.get((String) i.next());
            if(value instanceof Long){
                ps.setLong(counter,Long.parseLong(value.toString()));
            }
            else if(value instanceof Double){
                ps.setDouble(counter,Double.parseDouble(value.toString()));
            }
            else if(value instanceof String){
                ps.setString(counter,value.toString());
            }
            else if(value instanceof Timestamp){
                ps.setTimestamp(counter,(Timestamp)value);
            }
            else if(value==null){
                ps.setNull(counter,Types.VARCHAR);
            }
            else{
                throw new TRF_Exception("you are trying to put an unsupported datatype into ourdatabase");
            }
            counter++;
          }
        }
        catch(Exception e){
          throw new TRF_Exception("error occurs when setting preparedstatement",e);  
        }
    }

    /**
     * 通过id查找,查不到时返回null
     * 
     * @param id
     * @param entityClass
     * @return
     * @throws RuntimeException
     */
    public final Object findById(long id, Class entityClass) {
        String strSql = "select * from " + strTableName + " where id=" + id
                + " \n";
        initDAO();
        prepareStatement(strSql);
        executeQuery();
        Collection c = new DataTranserUtil().getDataEntitiesFromResultSet(
                entityClass, resultSet);
        if (c.size() == 0) {
            return null;
        } else {
            return c.iterator().next();
        }
    }

    /**
     * 通过id物理删除
     * 
     * @param id
     * @throws RuntimeException
     *             发生数据库错误时
     */
    public final void deletePhysically(long id) {
        String strSQL = "delete from " + strTableName + " where id = " + id;
        initDAO();
        prepareStatement(strSQL);
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("发生数据库错误", e);
        }
        finalizeDAO();
    }

    /**
     * 用id排序的条件查询方法
     * @param dataEntity
     * @param baseLineEntity
     *            用dataEntityWithId和baseLineEntity进行比较不同的字段作为条件
     *            如果baseLineEntity为null，则和类型的默认值进行比较
     * @return
     */
    public final Collection findByConditionOrderById(Object dataEntity,Object baseLineEntity) {
        return findByCondition(dataEntity,baseLineEntity,"order by id \n");
    }
}