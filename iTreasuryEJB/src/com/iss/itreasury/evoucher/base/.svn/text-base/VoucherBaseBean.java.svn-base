/*
 * Created on 2006-9-18
 *
 * Title:				iTreasury
 * @author             	yanliu 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2006
 * @version
 * Description:         
 */
package com.iss.itreasury.evoucher.base;

import java.sql.Connection;
import java.sql.SQLException;
import com.iss.itreasury.util.Database;

/**
 * @author yanliu
 * 维护数据库连接其他普通javabean的一般方法
 * 
 */
public class VoucherBaseBean
{

    /**
     * 
     */
    public VoucherBaseBean()
    {
        super();
        // TODO 自动生成构造函数存根
    }

    protected Connection initConnection() throws VoucherException
    {
        Connection transConn = null;
        try
        {
            transConn = Database.getConnection();
            transConn.setAutoCommit(false);
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherException("数据库初使化异常发生", e);
        }
        return transConn;

    }

    protected void finalizeConnection(Connection transConn)
            //throws VoucherException
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
            //throw new VoucherException("数据库关闭异常发生", e);
        }
    }

    public static void main(String[] args)
    {
    }
}