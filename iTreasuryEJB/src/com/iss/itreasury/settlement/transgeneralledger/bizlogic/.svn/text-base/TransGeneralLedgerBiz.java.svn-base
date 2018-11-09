package com.iss.itreasury.settlement.transgeneralledger.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.transgeneralledger.dao.TransGeneralLedgerDao;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class TransGeneralLedgerBiz {
	TransGeneralLedgerDao transGeneralLedgerDao = new TransGeneralLedgerDao();
	
	public PagerInfo queryTransGeneralLedger(TransGeneralLedgerInfo qInfo) throws Exception{
		// TODO Auto-generated method stub
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try
			{
				pagerInfo = new PagerInfo();
				//得到查询SQL
				sql = transGeneralLedgerDao.queryTransGeneralLedger(qInfo);
				pagerInfo.setSqlString(sql);
				
				ArrayList depictList = new ArrayList();
				PagerDepictBaseInfo baseInfo = null;
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("stransno");
				baseInfo.setExtension(true);
				baseInfo.setExtensionName(new String[]{"STRANSNO", "ID" });
				baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
				baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("businessTypeName");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NACCOUNTID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NCLIENTID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getClientNameByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NDIRECTION");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.DebitOrCredit.class, "getName", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("MAMOUNT");
				baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NGENERALLEDGERONE");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getGLTypeDescByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NDIRONE");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.DebitOrCredit.class, "getName", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("MONE");
				baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NGENERALLEDGERTWO");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getGLTypeDescByID", new Class[]{long.class});
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NDIRTWO");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.DebitOrCredit.class, "getName", new Class[]{long.class});
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("MTWO");
				baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NGENERALLEDGERTHREE");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getGLTypeDescByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NDIRTHREE");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.DebitOrCredit.class, "getName", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("MTHREE");
				baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NGENERALLEDGERFOUR");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getGLTypeDescByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NDIRFOUR");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.DebitOrCredit.class, "getName", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("MFOUR");
				baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("DTINTERESTSTART");
				baseInfo.setDisplayType(PagerTypeConstant.DATE);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("DTEXECUTE");
				baseInfo.setDisplayType(PagerTypeConstant.DATE);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("SABSTRACT");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NSTATUSID");
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
