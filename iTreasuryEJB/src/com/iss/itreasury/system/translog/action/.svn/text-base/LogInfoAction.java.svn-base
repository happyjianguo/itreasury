package com.iss.itreasury.system.translog.action;

import java.util.Map;

import com.iss.itreasury.system.logger.dataentity.QueryLoggerInfo;
import com.iss.itreasury.system.translog.bizlogic.LogInfoBiz;
import com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class LogInfoAction {
	
	LogInfoBiz logInfoBiz = new LogInfoBiz();
	
	/**
	 * 日志查询action
	 * @author zk 2012-12-13
	 *
	 */
	public PagerInfo queryLogInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long hidUserIdJS = -1;
		long selDepartment = -1;
		long hidUserIdOB = -1;
		long clientid = -1;
		String usernameJS = null;
		String usernameOB = null;
		String clientname = null;
		String startTime =	null;
		String endTime = null;
		try
		{
			QueryTransLogInfo transLogInfo = new QueryTransLogInfo();
			transLogInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			if(map != null && map.get("hiduseridjs") != null && !"".equals(map.get("hiduseridjs"))){//结算用户ID
				
				hidUserIdJS = Long.parseLong((String)map.get("hiduseridjs"));
			}
			if(map != null && map.get("usernamejs") != null && !"".equals(map.get("usernamejs"))){//结算用户名
				
				usernameJS = (String)map.get("usernamejs");
			}
			if(map != null && map.get("seldepartment") != null && !"".equals(map.get("seldepartment"))){//部门编号
				
				selDepartment = Long.parseLong((String)map.get("seldepartment"));
			}
			if(map != null && map.get("hiduseridob") != null && !"".equals(map.get("hiduseridob"))){//网银用户ID
				
				hidUserIdOB = Long.parseLong((String)map.get("hiduseridob"));
			}
			if(map != null && map.get("usernameob") != null && !"".equals(map.get("usernameob"))){//网银用户名
				
				usernameOB = (String)map.get("usernameob");
			}
			if(map != null && map.get("hidclientid") != null && !"".equals(map.get("hidclientid"))){//客户编号
				
				clientid = Long.parseLong((String)map.get("hidclientid"));
			}
			if(map != null && map.get("clientname") != null && !"".equals(map.get("clientname"))){//客户名称
				
				clientname = (String)map.get("clientname");
			}
			if(map != null && map.get("starttime") != null && !"".equals(map.get("starttime")) && !"::".equals(map.get("starttime"))){//开始时间
				
				String[] time = ((String)map.get("starttime")).split(":");
				for(int i=0;i<time.length;i++){
					if(time[i].length()<2){
						time[i]="0"+time[i];
					}
				}
				startTime = time[0]+":"+time[1]+":"+time[2];
			}
			if(map != null && map.get("endtime") != null && !"".equals(map.get("endtime")) && !"::".equals(map.get("endtime"))){//结束时间
				
				String[] time = ((String)map.get("endtime")).split(":");
				for(int i=0;i<time.length;i++){
					if(time[i].length()<2){
						time[i]="0"+time[i];
					}
				}
				endTime = time[0]+":"+time[1]+":"+time[2];
			}
			transLogInfo.setQuerylogtype(String.valueOf(transLogInfo.getLogtype()));
			transLogInfo.setQuerystatus(String.valueOf(transLogInfo.getStatus()));
			transLogInfo.setStarttime(startTime);
			transLogInfo.setEndtime(endTime);
			if(transLogInfo.getUsertype() == 0){
				transLogInfo.setUserid(hidUserIdJS);
				transLogInfo.setQueryuserid(String.valueOf(hidUserIdJS));
				transLogInfo.setUsername(usernameJS);
				transLogInfo.setDepartmentid(selDepartment);
			}else if(transLogInfo.getUsertype() == 1){
				transLogInfo.setUserid(hidUserIdOB);
				transLogInfo.setQueryuserid(String.valueOf(hidUserIdOB));
				transLogInfo.setUsername(usernameOB);
				transLogInfo.setClientid(clientid);
				transLogInfo.setDepartmentname(clientname);
			}
			
			pagerInfo = logInfoBiz.queryLogInfo(transLogInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * 操作日志查询action
	 * @author zk 2012-12-14
	 *
	 */
	public PagerInfo queryOperationLogInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long hidUserIdJS = -1;
		long selDepartment = -1;
		String usernameJS = null;
		String startTime =	null;
		String endTime = null;
		String startDate = null;
		String endDate = null;
		String status = null;
		try
		{
			QueryLoggerInfo loggerInfo = new QueryLoggerInfo();
			loggerInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			
			if(map != null && map.get("seldepartment") != null && !"".equals(map.get("seldepartment"))){//部门编号
				
				selDepartment = Long.parseLong((String)map.get("seldepartment"));
			}
			if(map != null && map.get("hiduseridjs") != null && !"".equals(map.get("hiduseridjs"))){//结算用户ID
				
				hidUserIdJS = Long.parseLong((String)map.get("hiduseridjs"));
			}
			if(map != null && map.get("usernamejs") != null && !"".equals(map.get("usernamejs"))){//结算用户名
				
				usernameJS = (String)map.get("usernamejs");
			}
			if(map != null && map.get("status") != null && !"".equals(map.get("status"))){//日志状态
				
				status = (String)map.get("status");
			}
			if(map != null && map.get("starttime") != null && !"".equals(map.get("starttime")) && !((String)map.get("starttime")).endsWith("::")){//开始时间
				
				String[] time = ((String)map.get("starttime")).split(":");
				for(int i=0;i<time.length;i++){
					
					if(time[i].length()<2){
						time[i]="0"+time[i];
					}
				}
				startTime = time[0]+":"+time[1]+":"+time[2];
			}
			if(map != null && map.get("endtime") != null && !"".equals(map.get("endtime")) && !((String)map.get("endtime")).endsWith("::")){//结束时间
				
				String[] time = ((String)map.get("endtime")).split(":");
				for(int i=0;i<time.length;i++){
					
					if(time[i].length()<2){
						time[i]="0"+time[i];
					}
				}
				endTime = time[0]+":"+time[1]+":"+time[2];
			}
			String startTime1 = startTime;
			if(startTime1 == null || "".equals(startTime1)){
				startTime1 = "00:00:00";
			}
			if(loggerInfo.getStartDate() != null && !"".equals(loggerInfo.getStartDate())){
				startDate = loggerInfo.getStartDate() + " " + startTime1;
				loggerInfo.setStartDate(startDate);
			}
			String endTime2 = endTime;
			if(endTime2 == null || "".equals(endTime2)){
				endTime2 = "23:59:59";
			}
			if(loggerInfo.getEndDate() != null && !"".equals(loggerInfo.getEndDate())){
				endDate = loggerInfo.getEndDate() + " " + endTime2;
				loggerInfo.setEndDate(endDate);
			}
			loggerInfo.setResult(status);
			
			if(loggerInfo.getUserType() == 0){
				loggerInfo.setDepartmentid(selDepartment);
				loggerInfo.setUserID(hidUserIdJS);
				loggerInfo.setUserName(usernameJS);
			}
			
			pagerInfo = logInfoBiz.queryOperationLogInfo(loggerInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}

}
