package com.iss.itreasury.settlement.transcurrentdeposit.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class TransCurrentDepositBiz {
	
	Sett_TransCurrentDepositDAO dao = new Sett_TransCurrentDepositDAO();
	
	public PagerInfo findByConditions(TransCurrentDepositInfo info) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.queryTransCurrentDeposit(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sTransNo");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"sTransNo", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nTransactionTypeID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//银行收款
			if(info.getTransactionTypeID() == SETTConstant.TransactionType.BANKRECEIVE){
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nReceiveAccountID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nReceiveClientID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getClientNameByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
			}else{
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nPayAccountID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nPayClientID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class, "getClientNameByID", new Class[]{long.class});
				depictList.add(baseInfo);
				
			}
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("mAmount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtInterestStart");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtExecute");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sAbstract");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nStatusID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
		}
		return pagerInfo;
	}
	
}
