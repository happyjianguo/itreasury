/*
 * Created on 2006-5-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yinghu 这是一个模拟spring的JdbcTemplate的提供数据库操作包装的类 TODO To change the
 *         template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class TinyJdbcTemplate {
    private Connection connection = null;

    private boolean isSupportTrasaction = false;

    /**
     * 转换到事务模式，一旦到事务模式就不能转回了。 当一个事务提交以后必须调用此方法进行下一个事务的初始化
     * 
     * @throws TRF_Exception
     *             初始化出错时
     */
    public void newTransaction() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DataBaseUtil.getConnection();
            }
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new TRF_Exception("初始化数据连接出错", e);
        }
        isSupportTrasaction = true;
    }

    /**
     * 提交事务
     * 
     * @throws TRF_Exception
     *             提交出错时
     */
    public void commitTransaction() {
        if (isSupportTrasaction == false) {
            return;
        }
        try {
            if (connection == null || connection.isClosed()) {
                return;
            }
            connection.commit();
        } catch (Exception e) {
            throw new TRF_Exception("提交事务出错", e);
        }
    }
    
    /**
     * 回滚事务
     * @throws TRF_Exception
     *         回滚出错时 
     */
    public void rollBackTransaction(){
        if (isSupportTrasaction == false) {
            return;
        }
        try {
            if (connection == null || connection.isClosed()) {
                return;
            }
            connection.rollback();
        } catch (Exception e) {
            throw new TRF_Exception("回滚事务出错", e);
        }
    }

    /**
     * 关闭事务
     * @throws TRF_Exception
     *            关闭出错时
     */
    public void closeTransaction() {
        if (isSupportTrasaction == false) {
            return;
        }
        try {
            if (connection == null || connection.isClosed()) {
                return;
            }
            connection.close();
        } catch (Exception e) {
            throw new TRF_Exception("关闭事务出错", e);
        }
    }

    /**
     * 查询的模板方法，需要一个处理单行记录的callback 此方法适应于简单数据或者没有和数据库表进行同名匹配的对象
     * 
     * @param sql
     * @param rch
     * @throws TRF_Exception任何数据库错误
     * @return 查不到时返回0长度List不会返回null
     */
    public List query(String sql, RowCallbackHandler rch) {
        if (isSupportTrasaction && connection == null) {
            throw new IllegalStateException("事务尚未初始化");
        }
        if (!isSupportTrasaction) {
            connection = DataBaseUtil.getConnection();
        }
        List result = new LinkedList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rch.processRow(rs));
            }
        } catch (Exception e) {
            throw new TRF_Exception("查询过程中出错", e);
        } finally {
            if (isSupportTrasaction) {
                DataBaseUtil.closeDataBaseResource(null, null, ps, rs);
            } else {
                DataBaseUtil.closeDataBaseResource(connection, null, ps, rs);
            }
        }
        return result;
    }

    /**
     * 查询的模板方法 此方法适应于和数据库表同名匹配的对象
     * 
     * @param sql
     * @param dataEntityClass
     * @throws TRF_Exception任何数据库错误
     * @return 查不到时返回0长度collecion不会返回null
     */
    public Collection queryAndGetResultByDataTranserUtil(String sql,
            Class dataEntityClass) {
        if (isSupportTrasaction && connection == null) {
            throw new IllegalStateException("事务尚未初始化");
        }
        if (!isSupportTrasaction) {
            connection = DataBaseUtil.getConnection();
        }
        //保证不会返回null
        Collection result = new LinkedList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new DataTranserUtil().getDataEntitiesFromResultSet(
                    dataEntityClass, rs);
        } catch (Exception e) {
            throw new TRF_Exception("查询过程中出错", e);
        } finally {
            if (isSupportTrasaction) {
                DataBaseUtil.closeDataBaseResource(null, null, ps, rs);
            } else {
                DataBaseUtil.closeDataBaseResource(connection, null, ps, rs);
            }
        }
        return result;
    }
    
    /**
     * 修改的模板方法（包括增，删，查）
     * @param sql
     * @throws TRF_Exception任何数据库错误
     * @return
     */
    public int update(String sql){
        if (isSupportTrasaction && connection == null) {
            throw new IllegalStateException("事务尚未初始化");
        }
        if (!isSupportTrasaction) {
            connection = DataBaseUtil.getConnection();
        }
        PreparedStatement ps = null;
        int result=-1;
        try {
            ps = connection.prepareStatement(sql);
            result = ps.executeUpdate();
        } catch (Exception e) {
            throw new TRF_Exception("修改过程中出错", e);
        } finally {
            if (isSupportTrasaction) {
                DataBaseUtil.closeDataBaseResource(null, null, ps, null);
            } else {
                DataBaseUtil.closeDataBaseResource(connection, null, ps, null);
            }
        }
        return result;
    }
}