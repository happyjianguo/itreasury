package com.iss.itreasury.glinterface.kingdee.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.glinterface.dataentity.GLKingDeePzflInfo;
import com.iss.itreasury.glinterface.kingdee.dao.GLVoucherDao;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * 金蝶操作类
 * @author xiangzhou 2012-11-27
 *
 */
public class GLVoucherBiz {
	
	GLVoucherDao dao = new GLVoucherDao();

	public PagerInfo queryGLVoucher(GLKingDeePzflInfo pzflinfo) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.queryGLVoucherSQL(pzflinfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FNumber");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"FNumber", "FNumber", "FAccountCode"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.STRING, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FEntryDC");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.DebitOrCredit.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FAccountCode");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FAmount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FCustomerCode");
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
	
	public GLKingDeePzflInfo queryGLEntry(GLKingDeePzflInfo info) throws Exception{
		info = dao.queryGLEntry(info);
		return info;
	}
	
	public long modifyGLEntry(GLKingDeePzflInfo info) throws Exception{
		long sign = -1;
		sign = dao.modifyGLEntry(info);
		return sign;
	}
	
	public PagerInfo queryGLKingdeeclient(GLKingDeePzflInfo pzflinfo) throws Exception
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.queryGLKingdeeclientSQL(pzflinfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"clientCode","clientID","clientCode","clientName","kingDeeID","kingDeeClientName"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG, PagerTypeConstant.STRING, PagerTypeConstant.STRING, PagerTypeConstant.LONG, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("assitantValue");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("kingDeeClientName");
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
	
	public long insertGLKingdeeclient(GLKingDeePzflInfo info) throws Exception{
		long sign = -1;
		sign = dao.insertGLKingdeeclient(info);
		return sign;
	}
	public long modifyGLKingdeeclient(GLKingDeePzflInfo info) throws Exception{
		long sign = -1;
		sign = dao.modifyGLKingdeeclient(info);
		return sign;
	}
	
}
