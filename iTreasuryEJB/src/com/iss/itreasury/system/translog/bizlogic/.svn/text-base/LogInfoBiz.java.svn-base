package com.iss.itreasury.system.translog.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.system.logger.dataentity.QueryLoggerInfo;
import com.iss.itreasury.system.translog.dao.LogInfoDao;
import com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class LogInfoBiz {
	
	LogInfoDao logInfoDao = new LogInfoDao();
	
   /**
	 * 日志查询biz
	 * @author zk 2012-12-13
	 *
	 */
   public PagerInfo queryLogInfo(QueryTransLogInfo transLogInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = logInfoDao.queryLogInfo(transLogInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("username");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("departmentname");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("functionpointdescription");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("maininfo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("status");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(Constant.LogActionResult.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("accesstime");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("time");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientip");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("currencyid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(Constant.CurrencyType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		
		return pagerInfo;
	}
   /**
	 * 操作日志查询biz
	 * @author zk 2012-12-14
	 *
	 */
   	public PagerInfo queryOperationLogInfo(QueryLoggerInfo loggerInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = logInfoDao.queryOperationLogInfo(loggerInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("username");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("departmentname");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("businesstype");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("transcode");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("actiontype");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("result");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("accesstime");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("time");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("remoteip");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("currencyid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(Constant.CurrencyType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("failreason");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(LogInfoBiz.class, "getFailReason", new Class[]{String.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		
		return pagerInfo;
	}
   	/**
     * 获得操作日志失败原因
     * @param failreason
     * @return String
     */
    public static final String getFailReason(String failreason) {
    	String reason = "";
        try
        {
        	if(failreason != null && !"".equals(failreason)){
        		
        		reason = IExceptionMessage.getExceptionMessage(failreason.trim()) != null ? IExceptionMessage.getExceptionMessage(failreason.trim()):failreason.trim();
				if(reason==null||"".equals(failreason))
					reason = failreason;
			}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return reason;
    }

}
