package com.iss.itreasury.settlement.transspecial.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.query.Dao.QTransAccountDao;
import com.iss.itreasury.settlement.transspecial.dao.TransspecialDao;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * 特殊业务处理 链接查找方法
 * @author liangli5
 *
 */
public class TransspecialBiz {
	
	TransspecialDao transspecialDao = new TransspecialDao();


	public PagerInfo queryTransspecial(QueryBySubSpecialTypeAndStatusInfo qInfo) throws Exception{
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = transspecialDao.queryTransspecialSQL(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("stransno");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"stransno", "id" , "noperationtypeid"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.STRING, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("noperationtypeid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("npayaccountid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("npaybankid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getBankNameByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("npaygeneralledgertypeid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getGLTypeDescByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nreceiveaccountid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nreceivebankid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getBankNameByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nreceivegeneralledgertypeid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getGLTypeDescByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("mpayamount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtintereststart");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtexecute");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sabstract");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nstatusid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionStatus.class, "getName", new Class[]{long.class});
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


	
	
	
}
