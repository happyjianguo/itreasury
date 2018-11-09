/*
 * Created on 2004-5-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.craftbrother.mywork.bizlogic;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.craftbrother.mywork.dao.SEC_MyWorkDAO;
import com.iss.itreasury.craftbrother.mywork.dataentity.MyWorkColumn;
import com.iss.itreasury.securities.util.SECConstant;

/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MyWork {
	/**
	 * 获得当前用户的"我的工作"信息,***********************主要方法
	 * @param userId
	 * @return
	 */
	public MyWorkColumn[] getMyWork(long userId,long currencyId,long officeId)throws SecuritiesException{
		
		SEC_MyWorkDAO dao = new SEC_MyWorkDAO();
		
		MyWorkColumn[] columns = new MyWorkColumn[4];				//五个栏位,申请书,交割单,合同书,计划,通知单
		
		MyWorkColumn colTmp = null;
		
		/**
		 * 	public final static int SEC_APPLYFORM 		= 1;				//申请书
			public final static int SEC_DELIVERORDER 	= 2;				//交割单
			public final static int SEC_CONTRACT 		= 3;				//合同
			public final static int SEC_NOTICE			= 4;				//通知书
		 */
		try{
			/**
			 * 申请书
			 */
			columns[0] = new MyWorkColumn(0,0,0);
			
			columns[0].getRootNode().setNodeName("申请书待审批处理");
			//查找已经保存的记录
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_APPLYFORM,SECConstant.ApplyFormStatus.SUBMITED,SECConstant.Actions.CHECKSEARCH);
			
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("转贴现");
			
			//添加到申请书栏位上
			columns[0].append(colTmp);
			
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_APPLYFORM,SECConstant.ApplyFormStatus.SUBMITED,SECConstant.Actions.NEXTSTEP);
			
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("资金拆借");
			
			//添加到申请书栏位上
			columns[0].append(colTmp);
			
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_APPLYFORM,SECConstant.ApplyFormStatus.SUBMITED,SECConstant.Actions.LINKSEARCH);
			
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("资产转让");
			
			//添加到申请书栏位上
			columns[0].append(colTmp);
			
			//查找待复核的记录
			//colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_APPLYFORM,SECConstant.ApplyFormStatus.SUBMITED,SECConstant.Actions.LINKSEARCH);
			
			//colTmp.getCurrentNode().setNodeId(0);
			//colTmp.getCurrentNode().setNodeName("已提交");
			
			//添加到申请书栏位上
			//columns[0].append(colTmp);
			
			/**
			 * 交割单
			 */
			columns[1] = new MyWorkColumn(0,0,0);
			
			columns[1].getRootNode().setNodeName("交割单待审批处理");
			
			//查找已保存的记录
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_DELIVERORDER,SECConstant.DeliveryOrderStatus.SAVED,SECConstant.Actions.CHECKSEARCH);
			
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("资金拆借");
			
			//添加到交割单栏位上
			columns[1].append(colTmp);
			
			//查找已保存的记录
			//colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_DELIVERORDER,SECConstant.DeliveryOrderStatus.TEMPSAVED,SECConstant.Actions.LINKSEARCH);
			
			//colTmp.getCurrentNode().setNodeId(0);
			//colTmp.getCurrentNode().setNodeName("已暂存");
			
			//添加到交割单栏位上
			//columns[1].append(colTmp);
			
			
			/**
			 *  合同
			 */
			columns[2] = new MyWorkColumn(0,0,0);
			
			columns[2].getRootNode().setNodeName("合同待审批处理");
			//查找已经保存的记录
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_CONTRACT,SECConstant.ContractStatus.SUBMIT,SECConstant.Actions.CHECKSEARCH);
			
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("转贴现");
			
			//添加到合同栏位上
			columns[2].append(colTmp);
			
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_CONTRACT,SECConstant.ContractStatus.SUBMIT,SECConstant.Actions.LINKSEARCH);
			
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("资产转让");
			
			//添加到合同栏位上
			columns[2].append(colTmp);
			
			//查找待复核的记录
			//colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_CONTRACT,SECConstant.ContractStatus.SAVE,SECConstant.Actions.LINKSEARCH);
			
			//colTmp.getCurrentNode().setNodeId(0);
			//colTmp.getCurrentNode().setNodeName("撰写");
			
			//添加到合同栏位上
			//columns[2].append(colTmp);
			
			//查找待激活的记录
			//colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_CONTRACT,SECConstant.ContractStatus.CHECK,SECConstant.Actions.COMMIT);
			
			//colTmp.getCurrentNode().setNodeId(0);
			//colTmp.getCurrentNode().setNodeName("待激活");
			
			//添加到合同栏位上
			//columns[2].append(colTmp);
			
			//查找待激活的记录
			//colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_CONTRACT,SECConstant.ContractStatus.SUBMIT,SECConstant.Actions.LINKSEARCH);
			
			//colTmp.getCurrentNode().setNodeId(0);
			//colTmp.getCurrentNode().setNodeName("已提交");
			
			//添加到合同栏位上
			//columns[2].append(colTmp);
					  
			/**
			 * 通知书
			 */
			columns[3] = new MyWorkColumn(0,0,0);
			columns[3].getRootNode().setNodeName("业务通知单待审批处理");
			
			//查找已经保存的记录
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_NOTICE,SECConstant.NoticeFormStatus.SUBMITED,SECConstant.Actions.CHECKSEARCH);
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("资金拆借");
			
			columns[3].append(colTmp);
			
			colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_NOTICE,SECConstant.NoticeFormStatus.SUBMITED,SECConstant.Actions.NEXTSTEP);
			colTmp.getCurrentNode().setNodeId(0);
			colTmp.getCurrentNode().setNodeName("资产转让");
			
			columns[3].append(colTmp);
			
			//查找已经保存的记录
			//colTmp = dao.getSingleWork(userId,currencyId,officeId,SEC_MyWorkDAO.SEC_NOTICE,SECConstant.NoticeFormStatus.SUBMITED,SECConstant.Actions.LINKSEARCH);
			//colTmp.getCurrentNode().setNodeId(0);
			//colTmp.getCurrentNode().setNodeName("已提交");

			//columns[3].append(colTmp);
			
		}catch(SecuritiesDAOException e){
			e.printStackTrace();
			throw new SecuritiesException("获得我的工作信息出错",e);
		}
		return columns;
	}
}
