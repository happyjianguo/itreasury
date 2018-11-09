package com.iss.itreasury.settlement.transdiscount.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.transdiscount.dao.QTransGrantDiscountDao;
import com.iss.itreasury.settlement.transdiscount.dao.QTransRepaymentDiscountDao;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * 账户查询业务层
 * @author xiang
 *
 */
public class QTransRepaymentDiscountBiz {
	
	QTransRepaymentDiscountDao dao = new QTransRepaymentDiscountDao();

	public PagerInfo queryTransRepaymentDiscount(QueryConditionInfo qInfo) throws Exception{
	{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.queryLoanSQL(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
		
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("STRANSNO");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"STRANSNO","ID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG,PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE+","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NTRANSACTIONTYPEID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDISCOUNTACCOUNTID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDISCOUNTCONTRACTID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getContractNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDISCOUNTNOTEID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getDiscountCredenceNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NDISCOUNTBILLID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getDiscountBillNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NBANKID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getBankNameByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("MAMOUNT");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SABSTRACT");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NSTATUSID");
			baseInfo.setDisplayType(PagerTypeConstant.LONG);
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
