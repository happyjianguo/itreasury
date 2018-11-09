package com.iss.itreasury.settlement.logger.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.logger.dao.OpenCloseLogDao;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * ���ػ���־��ѯ
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
			//�õ���ѯSQL
			sql = dao.findOpenCloseLogInfoNew(searchInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//��־���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("code");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"code", "code"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//��־����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ocdate");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//��־����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("openclosetype");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.OpenCloseType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("batchno");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sname");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	/**
	 * ���ػ���־��ѯ-��ϸ��Ϣ
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
			//�õ���ѯSQL
			sql = dao.findOpenCloseLogDetailNewUI(code);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//��־����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("openclosedate");
			baseInfo.setDisplayType(PagerTypeConstant.DATETIME);
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("content");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

}
