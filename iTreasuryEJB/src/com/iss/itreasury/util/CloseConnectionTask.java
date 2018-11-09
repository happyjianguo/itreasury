/*
 * Created on 2005-3-11
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author yychen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CloseConnectionTask extends Task
{
    private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    /**
     * Constructor for RemindBankAccountTransInfoTask.
     */
    public CloseConnectionTask()
    {
        super.m_strName = "自动关闭数据库连接";
        super.setRunIntervalTime(60);///设置自动运行时间，每隔60分钟执行---需要配置
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        try
        {
            log.debug("自动关闭数据库连接开始");
            if (Env.connectionList != null && Env.connectionList.size() > 0)
            {
                ArrayList list = Env.connectionList;
                for (int i=0,n=list.size();i<n;i++)
                {
                    ConnectionInfo info = (ConnectionInfo) list.get(0);
                    log.debug(Env.connectionList.size() + "有连接需要释放" + info.getIntervalMinute());
                    if (info.getConn() == null || info.getConn().isClosed())
                    {
                        list.remove(0);
                        log.debug("连接已释放");
                     }
                    else if (info.getIntervalMinute() > 180)///如果180分钟后未关闭，则自动关闭---需要配置
                    {
                        log.debug("*****************连接释放*****************");
                        info.CloseConn();
                        Env.connectionList.remove(0);
                    }
                    else
                    {   
                        list.remove(0);
                        list.add(info);
                        log.debug("*****************连接可疑*****************");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}