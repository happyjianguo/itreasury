/*
 * Created on 2005-6-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.awake.bizlogic;

import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Task;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AwakeTask extends Task{

    /**
     * 
     */
    public AwakeTask()
    {
        super();
        super.m_strName = "资金预算业务提醒后台处理";
        super.setRunIntervalTime(5);
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        Log.print("--资金预算业务提醒后台处理---");
        Awake a = new Awake();
        try
        {
            a.getAllAwake();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.print(ex.toString());
        }
    }
    
    public static void main(String[] args) {
    }
}
