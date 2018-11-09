package com.iss.itreasury.util;

import java.util.Date;

import com.iss.itreasury.bankportal.integration.client.BPClientAgent;
import com.iss.itreasury.bankportal.integration.info.ReqInstructionInfo;
import com.iss.itreasury.settlement.bankinstruction.instructionbean.InstructionBean_Product;

public class BudgetAutoTask extends Task{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	public BudgetAutoTask(){
		Date date = new Date();
		date.setHours(9);
		date.setMinutes(0);
		date.setSeconds(0);
        super.m_strName = "自动执行用款计划拨付";
        super.setEveryDayRunTime(date);		//设置每天自动执行的时间
	}
	
	public void run(){

	}
}
