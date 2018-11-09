/*
 * 创建日期 2004-11-24
 * 
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.iss.itreasury.settlement.base;

import java.sql.Connection;
import java.sql.SQLException;
import com.iss.itreasury.util.Database;

/**
 * @author jinchen
 * 维护数据库连接其他普通javabean的一般方法
 * 
 */
public class SettlementBaseBean
{

    /**
     * 
     */
    public SettlementBaseBean()
    {
        super();
        // TODO 自动生成构造函数存根
    }

    protected Connection initConnection() throws SettlementException
    {
        Connection transConn = null;
        try
        {
            transConn = Database.getConnection();
            transConn.setAutoCommit(false);
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new SettlementException("数据库初使化异常发生", e);
        }
        return transConn;

    }

    protected void finalizeConnection(Connection transConn)
            throws SettlementException
    {
        try
        {
            if (transConn != null)
            {
                transConn.close();
                transConn = null;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new SettlementException("数据库关闭异常发生", e);
        }
    }

    public static void main(String[] args)
    {
    }
}