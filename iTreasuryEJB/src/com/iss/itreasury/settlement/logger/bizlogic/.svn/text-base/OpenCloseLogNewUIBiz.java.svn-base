package com.iss.itreasury.settlement.logger.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.logger.dao.OpenCloseLogDao;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * 开关机日志查询
 * @author songwenxiao
 *
 */
public class OpenCloseLogNewUIBiz {
	
	OpenCloseLogDao dao = new OpenCloseLogDao();

	public PagerInfo getOpenCloseLogInfo(QueryOpenCloseLog searchInfo) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.findOpenCloseLogInfoNew(searchInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//日志编号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("code");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"code", "code"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//日志日期
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ocdate");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//日志类型
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("openclosetype");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.OpenCloseType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//操作次数
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("batchno");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//操作人
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sname");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
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
	 * 开关机日志查询-详细信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public PagerInfo getOpenCloseLogDetail(String code) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.findOpenCloseLogDetailNewUI(code);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//日志日期
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("openclosedate");
			baseInfo.setDisplayType(PagerTypeConstant.DATETIME);
			depictList.add(baseInfo);
			
			//操作详情
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("content");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
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

}
