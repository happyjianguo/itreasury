package com.iss.itreasury.settlement.transloan.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.transloan.dao.QTransRepaymentLoanDao;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class QTransRepaymentLoanBiz {

	QTransRepaymentLoanDao dao = new QTransRepaymentLoanDao();

	public PagerInfo queryAccount(TransRepaymentLoanInfo qInfo) throws Exception {
		// TODO Auto-generated method stub
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try {
				
				
				pagerInfo = new PagerInfo();
				// 得到查询SQL
				sql = dao.queryRepaymentLoanSQL(qInfo);
				pagerInfo.setSqlString(sql);

				ArrayList depictList = new ArrayList();
				PagerDepictBaseInfo baseInfo = null;
				//交易号
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("STRANSNO");
				baseInfo.setExtension(true);
				baseInfo.setExtensionName(new String[] { "STRANSNO", "ID" });
				baseInfo.setExtensionType(new long[] { PagerTypeConstant.LONG,
						PagerTypeConstant.LONG });
				baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + ","
						+ PagerTypeConstant.LOGOTYPE);
				depictList.add(baseInfo);
				//业务类型
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NTRANSACTIONTYPEID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.TransactionType.class,
						"getName", new Class[] { long.class });
				depictList.add(baseInfo);
				//活期账户号 
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NDEPOSITACCOUNTID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class,
						"getAccountNoByID", new Class[] { long.class });
				depictList.add(baseInfo);
				
				//自营贷款账户号(委托)
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NLOANACCOUNTID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class,
						"getAccountNoByID", new Class[] { long.class });
				depictList.add(baseInfo);
				//合同号
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NLOANCONTRACTID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class,
						"getContractNoByID", new Class[] { long.class });
				depictList.add(baseInfo);
				//放款通知单据号
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NLOANNOTEID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(NameRef.class,
						"getPayFormNoByID", new Class[] { long.class });
				depictList.add(baseInfo);
				//金额
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("MAMOUNT");
				baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
				depictList.add(baseInfo);
				//贷款起息日
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("DTINTERESTSTART");
				baseInfo.setDisplayType(PagerTypeConstant.DATE);
				depictList.add(baseInfo);
				//摘要
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("SABSTRACT");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				//状态
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NSTATUSID");
				baseInfo.setDisplayType(PagerTypeConstant.LONG);
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(
						SETTConstant.TransactionStatus.class, "getName",
						new Class[] { long.class });
				depictList.add(baseInfo);

				pagerInfo.setDepictList(depictList);

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}

	}
}
