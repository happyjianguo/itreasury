package com.iss.itreasury.loan.integratedCredit.customerfeedback.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.loan.integratedCredit.customerfeedback.dao.CustomerfeedbackDao;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dao.LoanFinanceitemdetailDao;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.FinancialTargetInfo;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceanalyse;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceitemdetail;
import com.iss.itreasury.util.Env;

/**
 * 从数据库中读取原始数据，计算指定行业的各项指标
 * @author lcliu
 */
public class FinancialTarget
{
	/*
	 * 财务指标分析参数表第二维（指标编号）与loan_financeitem表itemID对照表
	 * -1 代表未存储财务指标
	 */
	private int[] itemID = {126, 127, 128, 129, 136, 139, 135, 140};
	
	/*
	 * 财务指标分析参数表
	 * 第一维：行业类型，按LOANConstant.Calling类的常量排序
	 * 第二维：财务指标，依次为：
	 * 		1--资产负债率
	 * 		2--流动比率
	 * 		3--速动比率
	 * 		4--应收账款周转率
	 * 		5--利息保障倍数
	 * 		6--资产报酬率
	 * 		7--销售收入增长率
	 * 		8--经营现金流动负债比率
	 * 第三维：参数值，依次为：
	 * 		0--满意值
	 * 		1--不允许值
	 */
	private double[][][] formula =
		{
			{{1.00, 0.00}, {1.00, 0.00}, {1.00, 0.00}, {1.00, 0.00}, {1.00, 0.00}, {1.00, 0.00}, {1.00, 0.00}, {1.00, 0.00}},
			{{0.65, 1.00}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {8.00, 1.00}, {0.07, 0.02}, {0.10, 0.00}, {1.50, 0.00}},
			{{0.70, 0.90}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {0.80, 0.10}, {0.08, 0.02}, {0.10, 0.00}, {1.50, 0.00}},
			{{0.65, 0.85}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {3.00, 1.20}, {0.07, 0.02}, {0.10, 0.00}, {1.50, 0.00}},
			{{0.65, 0,85}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {2.00, 0.30}, {0.12, 0.04}, {0.10, 0.00}, {1.50, 0.00}},
			{{0.65, 0.85}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {2.40, 0.80}, {0.12, 0.04}, {0.10, 0.00}, {1.50, 0.00}},
			{{0.65, 0.85}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {4.00, 1.00}, {0.08, 0.02}, {0.10, 0.00}, {1.50, 0.00}},
			{{0.65, 0.90}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {0.80, 0.10}, {0.08, 0.02}, {0.10, 0.00}, {1.50, 0.00}},
			{{0.65, 0.85}, {1.50, 1.00}, {1.00, 0.50}, {1.50, 1.00}, {4.00, 1.00}, {0.10, 0.03}, {0.10, 0.00}, {1.50, 0.00}}
		};
	
	/**
	 * 从数据库中读取原始数据，计算指定行业的各项指标，返回指标的List。
	 * 指标的计算公式为 10*（比率-不允许值）/（满意值-不允许值）；得分为负值时，以0分计算；超过10分以10分计算。
	 * 其中比率为从数据空中读取的原始数据。
	 * 不同行业、不同指标的参数列表参照double[][][] formula。
	 * 
	 * @param callingType
	 * @param ClientID
	 * @return
	 */
	public List findCallingInfo(long callingType, long clientID, long creditGradeID)
	{
		List lst = new ArrayList();
		double[] originalData = new double[8];		// 原始数据数组
		
		
		// 从数据库中读取财务周期
		String[] cycleDate = new CustomerfeedbackDao().queryCreditCycle(creditGradeID);
		
		// 若从数据库中读取的财务周期无效，则取系统时间
		if (cycleDate == null)
		{
			cycleDate = new String[2];
		}
		if (cycleDate[0] == null || cycleDate[1] == null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(Env.getCurrentSystemDate());
			cycleDate[0] = String.valueOf(calendar.get(Calendar.YEAR));
			cycleDate[1] = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		
		LoanFinanceitemdetailDao dao = new LoanFinanceitemdetailDao();
		LoanFinanceanalyse financeInfo = dao.findFinanceAnalyse(clientID, cycleDate[0], cycleDate[1]);		// 查找财务分析主表信息
		Map map = dao.findFinanceDetailList(financeInfo.getId());		// 取财务分析详表map
		
		for (int i=0; i<itemID.length; i++)	// 获得财务指标原始值
		{
			LoanFinanceitemdetail info = (LoanFinanceitemdetail)map.get(itemID[i]+"");
			originalData[i] = info==null ? 0 : info.getAmount();
		}
		
		for (int i=0; i<8; i++)		// 计算财务指标并封装到Info中
		{
			FinancialTargetInfo targetInfo = new FinancialTargetInfo();
			targetInfo.setCallingType(callingType);
			targetInfo.setClientID(clientID);
			targetInfo.setOriginalData(originalData[i]);
			
			// 计算财务指标
			double newData = 10 * (originalData[i] - formula[(int)callingType][i][1]) / (formula[(int)callingType][i][0] - formula[(int)callingType][i][1]);
			newData = newData<0 ? 0 : newData;
			newData = newData>10 ? 10 : newData;
			targetInfo.setNewData(newData);
			lst.add(targetInfo);
		}
		
		return lst;
	}
}
