package com.iss.itreasury.system.history.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iss.inut.workflow.ws.WorkflowManager;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.system.history.dao.ApproveHistoryDao;
import com.iss.itreasury.system.history.dataentity.HistoryAdviseInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.SortUtil;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class ApproveHistoryBiz {
	
	ApproveHistoryDao approveHistoryDao = new ApproveHistoryDao();
	
	/**
	 * 审批意见
	 * @param HistoryAdviseInfo hInfo
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-18
	 */
	public PagerInfo queryApproveHistoryInfo(HistoryAdviseInfo hInfo,InutApprovalRecordInfo aInfo,long userID) throws Exception{
			
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			
			Map<String, Long> param = new HashMap<String, Long>();
			param.put("userID", userID);
			param.put("ID", hInfo.getEntryID());
			//得到查询SQL
			if(hInfo.getEntryID() > 0){
				sql = approveHistoryDao.queryApproveHistoryInfoByID(hInfo);
				pagerInfo.setSqlString(sql);
				pagerInfo.setExtensionMothod(ApproveHistoryBiz.class,"getApproveResultSetHandle1",param);
			}else{
				sql = approveHistoryDao.queryApproveHistoryInfoWithoutID(aInfo);
				pagerInfo.setSqlString(sql);
				pagerInfo.setExtensionMothod(ApproveHistoryBiz.class,"getApproveResultSetHandle2",param);
			}
			
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
		
	}
	public ArrayList getApproveResultSetHandle1(ResultSet rs,Map<String, Long> param) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		
		List list = new ArrayList();
		List approvalVector = new ArrayList();
		HistoryAdviseInfo tempInfo = null;
		WorkflowManager workflowManager = null;
		long userID = param.get("userID");
		long ID = param.get("ID");
		
		try {
			while(rs.next()){
				tempInfo = new HistoryAdviseInfo();
				tempInfo.setId(rs.getLong("id"));
				tempInfo.setOperator(NameRef.getUserNameByID(Long.parseLong((String)rs.getString("CALLER"))));
				tempInfo.setOpTime(rs.getTimestamp("EXECUTE_DATE"));
				tempInfo.setAdvise(rs.getString("ADVISE_VALUE"));
				tempInfo.setAction(rs.getString("ACTION_NAME"));				
				tempInfo.setStatusID(rs.getLong("STATUSID"));
				list.add(tempInfo);	
				
			}
			workflowManager = WorkflowManager.instance(String.valueOf(userID));
			approvalVector = workflowManager.getHistoryAdvise(ID);
			
			resultList = this.addFormData(approvalVector,list);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	public ArrayList getApproveResultSetHandle2(ResultSet rs,Map<String, Long> param) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		
		List list = new ArrayList();
		List approvalVector = new ArrayList();
		List sysApprovalVector = new ArrayList();
		InutApprovalRecordInfo tempInfo = null;
		WorkflowManager workflowManager = null;
		long userID = param.get("userID");
		
		try {
			while(rs.next()){
				tempInfo = new InutApprovalRecordInfo();
				tempInfo.setId(rs.getLong("id"));
				tempInfo.setOfficeID(rs.getLong("officeid"));
				tempInfo.setCurrencyID(rs.getLong("currencyid"));
				tempInfo.setModuleID(rs.getLong("moduleid"));
				tempInfo.setTransTypeID(rs.getLong("transTypeID"));
				tempInfo.setActionID(rs.getLong("actionID"));
				tempInfo.setTransID(rs.getString("transid"));
				tempInfo.setApprovalEntryID(rs.getLong("approvalentryid"));
				tempInfo.setLastAppUserID(rs.getLong("lastappuserid"));
				tempInfo.setStatusID(rs.getLong("statusid"));
				list.add(tempInfo);
				
			}
			
			workflowManager = WorkflowManager.instance(String.valueOf(userID));
			if(list != null && list.size()>0){
				Iterator it = list.iterator();
	   			while(it.hasNext()){
	   				tempInfo = (InutApprovalRecordInfo)it.next();
	   				List tempList = workflowManager.getHistoryAdvise(tempInfo.getApprovalEntryID());
	   				approvalVector.addAll(tempList);
	   				HistoryAdviseInfo qhaInfo=new HistoryAdviseInfo();
			   		qhaInfo.setEntryID(tempInfo.getApprovalEntryID());
			   		List sysTempList = approveHistoryDao.queryApproveHistoryInfoByCondition(qhaInfo);
			   		sysApprovalVector.add(sysTempList);
	   			}
	   		}
			
			resultList = this.addFormData(approvalVector,sysApprovalVector);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	public ArrayList addFormData(List approvalVector,List sysApprovalVector) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		List sortlist = new ArrayList();
		
		try
		{
			sortlist = SortUtil.AdviseRecordSort(approvalVector,sysApprovalVector);		
		   	if(sortlist!=null && sortlist.size()>0){
		   		int num = 0;
		   		Iterator it = sortlist.iterator();
				while(it.hasNext()){					
					HistoryAdviseInfo haInfo = (HistoryAdviseInfo)it.next();
					
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,++num);
					PagerTools.returnCellList(cellList,haInfo.getOperator());
					PagerTools.returnCellList(cellList,DataFormat.formatDate((java.util.Date)haInfo.getOpTime(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS));
					PagerTools.returnCellList(cellList,DataFormat.formatNullString((String)haInfo.getAction()));
					PagerTools.returnCellList(cellList,DataFormat.formatNullString(haInfo.getAdvise()));
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					
					//返回数据
					resultList.add(rowInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}

}
