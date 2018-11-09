// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

package com.iss.itreasury.loan.integratedCredit.customerfeedback.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanCreditgradeInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;

public class SelectDao extends SettlementDAO
{

    private Log4j log4j;

    public SelectDao()
    {
        log4j = new Log4j(1L, this);
    }

    public LoanCreditgradeInfo findId(LoanCreditgradeInfo loancreditgradeinfo)
    {
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        Connection connection = null;
        String s = "";
        if(loancreditgradeinfo != null)
        {
            System.out.println("***dwj***进入Dao层******");
            try
            {
                connection = Database.getConnection();
                StringBuffer stringbuffer = new StringBuffer();
                stringbuffer.append(" SELECT * ");
                stringbuffer.append(" FROM sett_candebusinessapplications ");
                stringbuffer.append(" WHERE sapplicationno=?");
                log4j.info(stringbuffer.toString());
                preparedstatement = connection.prepareStatement(stringbuffer.toString());
                preparedstatement.setString(1, s);
                for(resultset = preparedstatement.executeQuery(); resultset.next(); System.out.println("*****dwj******服值*"))
                    System.out.println("***dwj****进入Dao层*****");

                if(resultset != null)
                {
                    resultset.close();
                    resultset = null;
                }
                if(preparedstatement != null)
                {
                    preparedstatement.close();
                    preparedstatement = null;
                }
                if(connection != null)
                {
                    connection.close();
                    connection = null;
                }
            }
            catch(Exception exception)
            {
                log4j.error(exception.toString());
            }
            finally
            {
                try
                {
                    if(resultset != null)
                    {
                        resultset.close();
                        resultset = null;
                    }
                    if(preparedstatement != null)
                    {
                        preparedstatement.close();
                        preparedstatement = null;
                    }
                    if(connection != null)
                    {
                        connection.close();
                        connection = null;
                    }
                }
                catch(Exception exception2)
                {
                    log4j.error(exception2.toString());
                }
            }
        }
        return loancreditgradeinfo;
    }

    public ClientInfo quetyclient(long l)
        throws Exception
    {
        ArrayList arraylist = null;
        ClientInfo clientinfo = null;
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        Object obj = null;
        arraylist = new ArrayList();
        try
        {
            log4j.info("\n=======findByID start====");
            connection = Database.getConnection();
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append(" select * from client ");
            stringbuffer.append(" where id=" + l + " ");
            log4j.info("\n=======findByID start====" + stringbuffer.toString());
            preparedstatement = connection.prepareStatement(stringbuffer.toString());
            for(resultset = preparedstatement.executeQuery(); resultset.next(); System.out.println(" test  resutlInfo  whether getName---------" + clientinfo.getName()))
            {
                clientinfo = new ClientInfo();
                clientinfo.setClientID(resultset.getLong("ID"));
                clientinfo.setCode(resultset.getString("scode"));
                clientinfo.setName(resultset.getString("sname"));
            }

            if(resultset != null)
            {
                resultset.close();
                resultset = null;
            }
            if(preparedstatement != null)
            {
                preparedstatement.close();
                preparedstatement = null;
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            throw new Exception(exception.getMessage());
        }
        finally
        {
            try
            {
                if(resultset != null)
                {
                    resultset.close();
                    resultset = null;
                }
                if(preparedstatement != null)
                {
                    preparedstatement.close();
                    preparedstatement = null;
                }
                if(connection != null)
                {
                    connection.close();
                    connection = null;
                }
            }
            catch(Exception exception2)
            {
                throw new Exception(exception2.getMessage());
            }
        }
        return clientinfo;
    }
}
