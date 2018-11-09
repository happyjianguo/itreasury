/*
 * Created on 2004-5-8
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.util;
import java.sql.Timestamp;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Task;
import com.iss.itreasury.loan.credit.bizlogic.*;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DailyTask extends Task
{
    /**
     * Constructor for DailyTask.
     */
    public DailyTask()
    {
        super();
        super.m_strName = "授信状态后台处理";
        Timestamp tsRunTime = new Timestamp(104, 6, 8, 0, 5, 0, 0);
       System.out.println(tsRunTime);
        super.setEveryDayRunTime(tsRunTime);
      // super.setRunIntervalTime(5);
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        Log.print("当前时间：" + Env.getSystemDateTime());
        Log.print("--申请书状态更新后台处理开始---");        
        DailyOperation a = new DailyOperation();        
        try
        {
            a.dealAll();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.print(ex.toString());
        }
    }

    public static void main(String[] args)
    {
        String sss = "";
        DailyTask aaa = new DailyTask();
       // aaa.run();
    }

}
