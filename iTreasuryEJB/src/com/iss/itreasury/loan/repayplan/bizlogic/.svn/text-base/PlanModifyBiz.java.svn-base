/*
 * Created on 2004-3-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.repayplan.bizlogic;

import com.iss.itreasury.loan.repayplan.dao.PlanModifyDao;
import com.iss.itreasury.loan.repayplan.dataentity.PlanModifyInfo;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PlanModifyBiz
{
	public PlanModifyDao dao = null;
	
	public PlanModifyBiz()
	{
		dao = new PlanModifyDao();
	}

    public static void main(String[] args)
    {
		PlanModifyInfo info = new PlanModifyInfo();
		PlanModifyBiz biz = new PlanModifyBiz();
		
		info= biz.findPlanModifyByID(1);
		
		System.out.println("n======userlevel="+info.getNextCheckUserLevel());
		
		
    }
    
	/**
	 * 根据计划标示查找还款计划
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>查找还款计划</b>
	 * <ul>
	 * <li>操作数据库表ContractPayPlan
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long       lID                还款计划标示       
	 *
	 * @param     long       lUserID            用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long       lOfficeID          办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    PayPlanInfo     
	 *
	 * @exception Exception
	**/
	public PlanModifyInfo findPlanModifyByID(long lID)
	{
		return dao.findPlanModifyByID(lID);
	}



    
}
