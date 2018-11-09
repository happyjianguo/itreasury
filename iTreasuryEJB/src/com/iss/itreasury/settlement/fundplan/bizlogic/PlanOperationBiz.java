package com.iss.itreasury.settlement.fundplan.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.fundplan.dao.PlanOperationDao;
import com.iss.itreasury.settlement.fundplan.dataentity.PlanOperationInfo;
import com.iss.itreasury.util.IException;

/**
 * @author Boxu
 *
 */
public class PlanOperationBiz {
	PlanOperationDao plandao = null;
	/**
	 * @author Boxu
	 * 保存或修改功能
	 */
	public long savePlanOperation(PlanOperationInfo planInfo) throws Exception {
		long lreturn = -1;
		try{
			plandao = new PlanOperationDao();
			if(planInfo.getId() > 0) {
				 plandao.update(planInfo);
				 lreturn = planInfo.getId();
			} else {
				plandao.setUseMaxID();
				lreturn = plandao.add(planInfo);
			}
		}catch (Exception e) {
     		throw new IException("计划业务联系单保存失败");
		}
		return lreturn;
	}
	
	/**
	 * @author Boxu
	 * 查询功能
	 */
	public PlanOperationInfo findPlanOperation(PlanOperationInfo planInfo) throws Exception {
		PlanOperationInfo info = new PlanOperationInfo();;
		try{
			plandao = new PlanOperationDao();
			info = plandao.findPlanOperation(planInfo);
		}catch (Exception e) {
     		throw new IException("计划业务联系单查找失败");
		}
		return info;
	}
	
	/**
	 * @author Boxu
	 * 联系单打印功能
	 */
	public PlanOperationInfo PrintPlanOperation(PlanOperationInfo planInfo) throws Exception {
		String strTemp = "";
		try{
			if(planInfo.getOpinion() != null && planInfo.getOpinion().length() > 0)
			{
				plandao = new PlanOperationDao();
				strTemp = plandao.PrintPlanOperation(planInfo);
				planInfo.setOpinion(strTemp);
			}
		}catch (Exception e) {
     		throw new IException("打印计划业务联系单失败");
		}
		return planInfo;
	}
	
	/**
	 * @author Boxu
	 * 汇总打印功能
	 */
	public Collection GatherPrintPlanOperation(PlanOperationInfo planInfo) throws Exception {
		Collection coll = null;
		try{
			plandao = new PlanOperationDao();
			coll = plandao.GatherPrintPlanOperation(planInfo);
		}catch (Exception e) {
     		throw new IException("打印汇总单失败");
		}
		return coll;
	}
}