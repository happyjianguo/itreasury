package com.iss.itreasury.craftbrother.craAwake.bizlogic;
import com.iss.itreasury.craftbrother.craAwake.dao.CraAwakeDAO;
import com.iss.itreasury.craftbrother.craAwake.dataentity.CraAwakeCondition;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Task;

public class CraAwakeTask  extends Task
{

	/**
	 * Constructor for AutoInterestSettlementTask.
	 */
	public CraAwakeTask()
	{
		super();
		super.m_strName = "业务提醒后台处理";
		super.setRunIntervalTime(5);
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		Log.print("--业务提醒后台处理---");
		CraAwakeDAO a = new CraAwakeDAO();
		try
		{
			System.out.println("run***********");
			a.getAllAwakeContract();
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
		CraAwakeTask aaa = new CraAwakeTask();
		CraAwakeCondition.AwakeMSG.put("lCurrencyID","1");
		CraAwakeCondition.AwakeMSG.put("lOfficeID","1");
		aaa.run();
		System.out.println(CraAwakeCondition.AwakeMSG.get("11").toString());
		//sss = aaa.strMSG;
		Log.print("业务提醒 : " + sss);

	}  

}
