package com.iss.itreasury.settlement.reportlossorfreeze.biz;

import java.util.ArrayList;

import com.iss.itreasury.settlement.query.paraminfo.QueryClientGatherWhereInfo;
import com.iss.itreasury.settlement.reportlossorfreeze.dao.QReportLossOrFreezeDao;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class QReportLossOrFreezeBiz {

	QReportLossOrFreezeDao dao = new QReportLossOrFreezeDao();
	
	public PagerInfo getReportLoss(ReportLossOrFreezeQueryInfo info) throws Exception{
	{
			PagerInfo pagerInfo = null;
			String sql = null;
			try
			{
				pagerInfo = new PagerInfo();
				//得到查询SQL
				sql = dao.getReportLossOrFreezeSQL(info);
				pagerInfo.setSqlString(sql);
				
				ArrayList depictList = new ArrayList();
				PagerDepictBaseInfo baseInfo = null;
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("TRANSNO");
				baseInfo.setExtension(true);
				baseInfo.setExtensionName(new String[]{"TRANSNO","ID"});
				baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.LONG});
				baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE+","+PagerTypeConstant.LOGOTYPE);
				depictList.add(baseInfo);
				
				
				//交易类型
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("TRANSACTIONTYPE");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
				depictList.add(baseInfo);
				
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("EFFECTIVEDATE");
				baseInfo.setDisplayType(PagerTypeConstant.DATE);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("CLIENTCODE");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("CLIENTNAME");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("ACCOUNTNO");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);

				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("OLDDEPOSITNO");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NEWDEPOSITNO");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("EXECUTEDATE");
				baseInfo.setDisplayType(PagerTypeConstant.DATE);
				depictList.add(baseInfo);
				
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("CHECKUSERNAME");
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
	
	public PagerInfo queryFreezeInfo(ReportLossOrFreezeQueryInfo info) throws Exception{
		{
				PagerInfo pagerInfo = null;
				String sql = null;
				try
				{
					pagerInfo = new PagerInfo();
					//得到查询SQL
					sql = dao.getReportLossOrFreezeSQL(info);
					pagerInfo.setSqlString(sql);
					
					ArrayList depictList = new ArrayList();
					PagerDepictBaseInfo baseInfo = null;
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("TRANSNO");
					baseInfo.setExtension(true);
					baseInfo.setExtensionName(new String[]{"TRANSNO","ID"});
					baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.LONG});
					baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE+","+PagerTypeConstant.LOGOTYPE);
					depictList.add(baseInfo);
					
					
					//交易类型
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("TRANSACTIONTYPE");
					baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
					baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
					depictList.add(baseInfo);
					
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("EFFECTIVEDATE");
					baseInfo.setDisplayType(PagerTypeConstant.DATE);
					depictList.add(baseInfo);
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("CLIENTCODE");
					baseInfo.setDisplayType(PagerTypeConstant.STRING);
					depictList.add(baseInfo);
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("CLIENTNAME");
					baseInfo.setDisplayType(PagerTypeConstant.STRING);
					depictList.add(baseInfo);
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("ACCOUNTNO");
					baseInfo.setDisplayType(PagerTypeConstant.STRING);
					depictList.add(baseInfo);

					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("OLDDEPOSITNO");
					baseInfo.setDisplayType(PagerTypeConstant.STRING);
					depictList.add(baseInfo);

					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("FREEZETYPE");
					baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
					baseInfo.setExtensionMothod(SETTConstant.SubAccountStatus.class, "getName", new Class[]{long.class});
					depictList.add(baseInfo);
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("FREEZEAMOUNT");
					baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
					depictList.add(baseInfo);
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("EXECUTEDATE");
					baseInfo.setDisplayType(PagerTypeConstant.DATE);
					depictList.add(baseInfo);
					
					baseInfo = new PagerDepictBaseInfo();
					baseInfo.setDisplayName("CHECKUSERNAME");
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

}
