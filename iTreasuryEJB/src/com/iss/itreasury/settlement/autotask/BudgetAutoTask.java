package com.iss.itreasury.settlement.autotask;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Task;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;

public class BudgetAutoTask extends Task{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private String everydayTime = Config.getProperty(ConfigConstant.SETT_TIME_BUDGETAUTOTASK, "09:00:00");
	
	public BudgetAutoTask(){
		super();
        super.m_strName = "自动执行用款计划拨付";
        try {
        	super.setEveryDayRunTime(DataFormat.parseDate(everydayTime, DataFormat.FMT_DATE_HHMMSS));
        } catch(Exception e) {
        	e.printStackTrace();
        	log.info("用款计划自动拨付任务--转换时间出现异常!");
        }
	}
	
	public void run(){
		Log.print("--自动任务后台处理---");
		BudgetProcess process = new BudgetProcess();
		try
		{
			process.StartProcess();	
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Log.print(ex.toString());
		}
	}
}
