package com.iss.itreasury.glinterface.kingdee.Action;

import java.util.Map;

import com.iss.itreasury.glinterface.dataentity.GLKingDeePzflInfo;
import com.iss.itreasury.glinterface.kingdee.bizlogic.GLVoucherBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class GLVoucherAction {
	
	GLVoucherBiz GLVoucherBiz = new GLVoucherBiz();
	
	public PagerInfo queryGLVoucher(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			GLKingDeePzflInfo pzflinfo = new GLKingDeePzflInfo();
			pzflinfo.convertMapToDataEntity(map);//将Map转化为INFO
			pagerInfo = GLVoucherBiz.queryGLVoucher(pzflinfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryGLKingdeeclient(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			GLKingDeePzflInfo pzflinfo = new GLKingDeePzflInfo();
			pzflinfo.convertMapToDataEntity(map);//将Map转化为INFO
			pagerInfo = GLVoucherBiz.queryGLKingdeeclient(pzflinfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	

}
