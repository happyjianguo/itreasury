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
	 * ������޸Ĺ���
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
     		throw new IException("�ƻ�ҵ����ϵ������ʧ��");
		}
		return lreturn;
	}
	
	/**
	 * @author Boxu
	 * ��ѯ����
	 */
	public PlanOperationInfo findPlanOperation(PlanOperationInfo planInfo) throws Exception {
		PlanOperationInfo info = new PlanOperationInfo();;
		try{
			plandao = new PlanOperationDao();
			info = plandao.findPlanOperation(planInfo);
		}catch (Exception e) {
     		throw new IException("�ƻ�ҵ����ϵ������ʧ��");
		}
		return info;
	}
	
	/**
	 * @author Boxu
	 * ��ϵ����ӡ����
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
     		throw new IException("��ӡ�ƻ�ҵ����ϵ��ʧ��");
		}
		return planInfo;
	}
	
	/**
	 * @author Boxu
	 * ���ܴ�ӡ����
	 */
	public Collection GatherPrintPlanOperation(PlanOperationInfo planInfo) throws Exception {
		Collection coll = null;
		try{
			plandao = new PlanOperationDao();
			coll = plandao.GatherPrintPlanOperation(planInfo);
		}catch (Exception e) {
     		throw new IException("��ӡ���ܵ�ʧ��");
		}
		return coll;
	}
}