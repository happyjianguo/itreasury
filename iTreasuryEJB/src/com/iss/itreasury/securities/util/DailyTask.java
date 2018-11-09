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
package com.iss.itreasury.securities.util;
import java.sql.Timestamp;

import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Task;

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
        super.m_strName = "申请书状态更新后台处理";
        Timestamp tsRunTime = new Timestamp(104, 6, 8, 0, 0, 0, 0);
        super.setEveryDayRunTime(tsRunTime);
        //super.setRunIntervalTime(5);
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
        Log.print("--申请书状态更新后台处理结束---");
        
        Log.print("--开始违规交割单检查---");
		DeliveryOrderServiceOperation op = new DeliveryOrderServiceOperation(null);
		Timestamp date = Env.getSecuritiesSystemDate(1, 1);
		date = UtilOperation.getNextNDay(date, -1);
		System.out.println("检查日:"+date);
		try {
			op.checkViolativeDeliveryOrder(date);
		} catch (SecuritiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.print("--结束违规交割单检查---");
        
//        try
//        {
//            DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation();
//            deliveryOrderServiceOperation.deliverAutomatically(1,1);
//        } catch (RemoteException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SecuritiesException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        /*
        Log.print("--日结后台处理开始---");
        try
        {
            EndProcessDelegation endProcessDelegation = new EndProcessDelegation();            
            endProcessDelegation.endProcess(1,1);
        } catch (SecuritiesException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (RemoteException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Log.print("--日结后台处理结束---");
        */
    }

    public static void main(String[] args)
    {
        String sss = "";
        DailyTask aaa = new DailyTask();
        aaa.run();
    }

}
