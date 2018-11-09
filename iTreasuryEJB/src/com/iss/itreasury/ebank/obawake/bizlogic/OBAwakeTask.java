/*
 * Created on 2003-12-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obawake.bizlogic;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Task;
import com.iss.itreasury.ebank.obawake.dao.*;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBAwakeTask extends Task
{

	//private long lOfficeID = -1;
	//private long lCurrencyID = -1;
	//public String strMSG = "";
	/**
	 * Constructor for AutoInterestSettlementTask.
	 */
	public OBAwakeTask()
	{
		super();
		super.m_strName = "业务提醒后台处理";
		super.setRunIntervalTime(1);
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		Log.print("--业务提醒后台处理---");
		OBAwake a = new OBAwake();
		try
		{
			//Modify by leiyang date 2007/06/23
			System.out.println("run***********");
			a.getAllAwakeContract();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Log.print(ex.toString());
		}
	}
	/*
	 * @author haoning
	 * @time 2003-12-11
	 * @param args
	 * function
	 */
	/*public String getAwakeMSG(long lCurrencyID, long lOfficeID)
	{
		String sss = "";
		try
		{
			OBAwakeTask aaa;
			aaa = new OBAwakeTask();
			//aaa.setCurrencyID(lCurrencyID);
			//aaa.setOfficeID(lOfficeID);
			aaa.run();
			//sss = aaa.strMSG;
			Log.print("业务提醒 : " + sss);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}

		return sss;
	}*/

	/*
	 * @author haoning
	 * @time 2003-12-11
	 * @param args
	 * function
	 */
	public static void main(String[] args)
	{
		String sss = "";
		OBAwakeTask aaa = new OBAwakeTask();
		aaa.run();
		//sss = aaa.strMSG;
		Log.print("业务提醒 : " + sss);

	}
	/**
	 * @param 
	 * function 得到/设置
	 * return long
	 */
	/*public long getOfficeID()
	{
		return lOfficeID;
	}

	/**
	 * @param 
	 * function 得到/设置
	 * return void
	 */
	/*public void setOfficeID(long l)
	{
		this.lOfficeID = l;
	}

	/**
	 * @param 
	 * function 得到/设置
	 * return long
	 */
	/*public long getCurrencyID()
	{
		return lCurrencyID;
	}

	/**
	 * @param 
	 * function 得到/设置
	 * return void
	 */
	/*public void setCurrencyID(long l)
	{
		this.lCurrencyID = l;
	}*/

}