package com.iss.itreasury.settlement.transfixeddeposit.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class TransFixedDepositBiz {
	Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
	
	public PagerInfo findByConditions(QueryByStatusConditionInfo info) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{		
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.queryTransFixedOpenDeposit(info);
			//System.out.print("sql789789789:"+sql);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//交易号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sTransNo");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"sTransNo", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			//交易类型
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nTransactionTypeID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			//定期账户号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("naccountid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			//定期客户名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nclientid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getClientNameByID", new Class[]{long.class});
			depictList.add(baseInfo);
			//存款单据号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sdepositno");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			//活期账户号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ncurrentaccountid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			//开户行名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nbankid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getBankNameByID", new Class[]{long.class});
			depictList.add(baseInfo);
			//交易金额
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("mamount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			//起始日
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtstart");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			//到期日
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtend");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			//状态
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
			throw new Exception("====>查询异常:"+e.getMessage());
		}
		return pagerInfo;
	}
	
}
