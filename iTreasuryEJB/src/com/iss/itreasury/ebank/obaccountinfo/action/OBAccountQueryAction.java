package com.iss.itreasury.ebank.obaccountinfo.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryBiz;
import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere;
import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.dataentity.QueryClientSAInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class OBAccountQueryAction {
	
	OBAccountQueryBiz oBAccountQueryBiz = new OBAccountQueryBiz();
	
	/**
	 * 查询活期账户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryCurrentBalace(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lClientID = -1;
			long lCurrencyID = -1; 
			long lOfficeID = -1;
			long lUserID = -1;
			
			QueryClientSAInfo qInfo = new QueryClientSAInfo();

			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.parseLong(map.get("lclientid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				lUserID = Long.parseLong(map.get("luserid").toString().trim());
			}
			
			pagerInfo = oBAccountQueryBiz.queryCurrentBalace(lClientID, lCurrencyID, lOfficeID, lUserID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 查询活期账户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryCurrentBalaceDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lAccountID =-1;
			long lCurrencyID =-1;
			long lOfficeID = -1;
			OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
			OBAccountQueryInfo info = new OBAccountQueryInfo();
			OBAccountQueryInfo tempinfo = new OBAccountQueryInfo();
			if(map.get("laccountid")!=null && map.get("laccountid").toString().trim().length()>0){
				lAccountID = Long.parseLong(map.get("laccountid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			info.setAccountID(lAccountID);
			info.setCurrencyID(lCurrencyID);
			info.setOfficeID(lOfficeID);
			//获取账户对应的开户机构
			tempinfo = dao.getOfficeAndCurrencyByAccountID(lAccountID);
			info.setExecuteDate(Env.getSystemDate(tempinfo.getOfficeID(), tempinfo.getCurrencyID()));
			pagerInfo = oBAccountQueryBiz.queryCurrentBalaceDetail(info,tempinfo);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}

	/**
	 * 查询定期账户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryFixedBalace(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lClientID = -1;
			long lCurrencyID = -1; 
			long lOfficeID = -1;
			long lUserID = -1;
			long lStatusID = -1;
			QueryClientSAInfo qInfo = new QueryClientSAInfo();

			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.parseLong(map.get("lclientid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				lUserID = Long.parseLong(map.get("luserid").toString().trim());
			}
			if(map.get("lstatusid")!=null && map.get("lstatusid").toString().trim().length()>0){
				lStatusID = Long.parseLong(map.get("lstatusid").toString().trim());
			}
			
			pagerInfo = oBAccountQueryBiz.queryFixedBalace(lClientID, lCurrencyID, lOfficeID, lStatusID, lUserID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 查询定期账户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryFixedBalaceDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lAccountID =-1;
			long lCurrencyID =-1;
			long lOfficeID = -1;
			String strDepositNo = "";
			long lSubAccountID = -1;
			
			OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
			OBAccountQueryInfo info = new OBAccountQueryInfo();
			OBAccountQueryInfo tempinfo = new OBAccountQueryInfo();
			if(map.get("laccountid")!=null && map.get("laccountid").toString().trim().length()>0){
				lAccountID = Long.parseLong(map.get("laccountid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			if(map.get("strdepositno")!=null && map.get("strdepositno").toString().trim().length()>0){
				strDepositNo = map.get("strdepositno").toString().trim();
			}
			if(map.get("lsubaccountid")!=null && map.get("lsubaccountid").toString().trim().length()>0){
				lSubAccountID = Long.parseLong(map.get("lsubaccountid").toString().trim());
			}
			info.setAccountID(lAccountID);
			info.setSubAccountID(lSubAccountID);
			info.setCurrencyID(lCurrencyID);
			info.setOfficeID(lOfficeID);
			//获取账户对应的开户机构
			tempinfo = dao.getOfficeAndCurrencyByAccountID(lAccountID);
			info.setExecuteDate(Env.getSystemDate(tempinfo.getOfficeID(), tempinfo.getCurrencyID()));
			pagerInfo = oBAccountQueryBiz.queryFixedBalaceDetail(info);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 查询通知账户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryNoticeBalaceDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lAccountID =-1;
			long lCurrencyID =-1;
			long lOfficeID = -1;
			String strDepositNo = "";
			long lSubAccountID = -1;
			
			OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
			OBAccountQueryInfo info = new OBAccountQueryInfo();
			OBAccountQueryInfo tempinfo = new OBAccountQueryInfo();
			if(map.get("laccountid")!=null && map.get("laccountid").toString().trim().length()>0){
				lAccountID = Long.parseLong(map.get("laccountid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			if(map.get("strdepositno")!=null && map.get("strdepositno").toString().trim().length()>0){
				strDepositNo = map.get("strdepositno").toString().trim();
			}
			if(map.get("lsubaccountid")!=null && map.get("lsubaccountid").toString().trim().length()>0){
				lSubAccountID = Long.parseLong(map.get("lsubaccountid").toString().trim());
			}
			info.setAccountID(lAccountID);
			info.setSubAccountID(lSubAccountID);
			info.setCurrencyID(lCurrencyID);
			info.setOfficeID(lOfficeID);
			//获取账户对应的开户机构
			tempinfo = dao.getOfficeAndCurrencyByAccountID(lAccountID);
			info.setExecuteDate(Env.getSystemDate(tempinfo.getOfficeID(), tempinfo.getCurrencyID()));
			pagerInfo = oBAccountQueryBiz.queryNoticeBalaceDetail(info);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 查询贷款账户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryLoanBalace(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lClientID = -1;
			long lCurrencyID = -1; 
			long lOfficeID = -1;
			long lUserID = -1;
			
			QueryClientSAInfo qInfo = new QueryClientSAInfo();

			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.parseLong(map.get("lclientid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				lUserID = Long.parseLong(map.get("luserid").toString().trim());
			}
			
			pagerInfo = oBAccountQueryBiz.queryLoanBalace(lClientID, lCurrencyID, lOfficeID, lUserID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 查询定期账户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryLoanBalaceDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lAccountID =-1;
			long lCurrencyID =-1;
			long lOfficeID = -1;
			String strDepositNo = "";
			long lSubAccountID = -1;
			long lContractID = -1;
			
			OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
			OBAccountQueryInfo info = new OBAccountQueryInfo();
			OBAccountQueryInfo tempinfo = new OBAccountQueryInfo();
			if(map.get("laccountid")!=null && map.get("laccountid").toString().trim().length()>0){
				lAccountID = Long.parseLong(map.get("laccountid").toString().trim());
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			if(map.get("lcontractid")!=null && map.get("lcontractid").toString().trim().length()>0){
				lContractID = Long.parseLong(map.get("lcontractid").toString().trim());
			}
			if(map.get("strdepositno")!=null && map.get("strdepositno").toString().trim().length()>0){
				strDepositNo = map.get("strdepositno").toString().trim();
			}
			if(map.get("lsubaccountid")!=null && map.get("lsubaccountid").toString().trim().length()>0){
				lSubAccountID = Long.parseLong(map.get("lsubaccountid").toString().trim());
			}
			info.setAccountID(lAccountID);
			info.setSubAccountID(lSubAccountID);
			info.setCurrencyID(lCurrencyID);
			info.setOfficeID(lOfficeID);
			info.setContractID(lContractID);
			//获取账户对应的开户机构
			tempinfo = dao.getOfficeAndCurrencyByAccountID(lAccountID);
			info.setExecuteDate(Env.getSystemDate(tempinfo.getOfficeID(), tempinfo.getCurrencyID()));
			pagerInfo = oBAccountQueryBiz.queryLoanBalaceDetail(info);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 下属单位账户余额查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryOBAccountQueryAmountInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			long lAccountID =-1;
			long lAccountGroupID = -1;
			long lClientID = -1;
			long lCurrencyID = -1;
			long lOfficeID = -1;
			long lParentCorpID = -1;
			String strEndDate = "";
			Timestamp tsEndDate = null;
			String fromStr = "";
			
			if(map.get("laccountgroupid")!=null && map.get("laccountgroupid").toString().trim().length()>0){
				lAccountGroupID = Long.parseLong(map.get("laccountgroupid").toString().trim());
			}
			if(map.get("lparentcorpid")!=null && map.get("lparentcorpid").toString().trim().length()>0){
				lParentCorpID = Long.parseLong(map.get("lparentcorpid").toString().trim());
			}
			if(map.get("laccountid")!=null && map.get("laccountid").toString().trim().length()>0){
				lAccountID = Long.parseLong(map.get("laccountid").toString().trim());
			}
			if(map.get("tsend").toString()!=null && map.get("tsend").toString().trim().length()>0)
			{
				strEndDate = map.get("tsend").toString().trim();
				tsEndDate = DataFormat.getDateTime(strEndDate);  //起始日期
			}
			if(map.get("fromaccounttype")!=null && map.get("fromaccounttype").toString().trim().length()>0){
				fromStr = map.get("fromaccounttype").toString().trim();
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
			}
			OBAccountQueryWhere info = new OBAccountQueryWhere();
			info.setCurrencyID(lCurrencyID);
			info.setOfficeID(lOfficeID);
			info.setDateTo(tsEndDate);
		    Map paramMap = new HashMap();
		    paramMap.put("info", info);
		    paramMap.put("lAccountGroupID", lAccountGroupID);
		    paramMap.put("lOfficeID", lOfficeID);
		    paramMap.put("lCurrencyID", lCurrencyID);
		    paramMap.put("lClientID", lClientID);
		    paramMap.put("lAccountID", lAccountID);
		    paramMap.put("lParentCorpID", lParentCorpID);
		    paramMap.put("fromAccountType", fromStr);
		    paramMap.put("info", info);
		    
			pagerInfo = oBAccountQueryBiz.queryOBAccountQueryAmountInfo(paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 下属单位账户余额明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryOBAccountQueryAmountDetailInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryTransAccountDetailWhereInfo qInfo = new QueryTransAccountDetailWhereInfo();
			//定义变量
			String strClientCode = "";//客户号
			String strAccountNo = "";//账号
			long lAccountID = -1;//账户ID
			long lAccountTypeID = -1;//账户类型ID
			long lAccountGroupID = -1;//账户组ID
			String strContractCode = "";//合同号
			long lContractID = -1;//合同ID
			String strLoanNoteNo = "";//放款单号
			long lLoanNoteID = -1;//放款单ID
			String strFixedDepositNo = "";//定期存款单据号
			long lSubAccountID = -1;//定期存款单据对应的子账户ID
			Timestamp tsStartDate = null;//开始日期
			String strStartDate = "";
			String strEndDate = "";
			Timestamp tsEndDate = null;//结束日期
			double dBalance = 0.0;//期初余额
			long lOfficeID = -1;
			long lCurrencyID = -1;
			String sClientCode = ""; 
			
			//读取数据
			if(map.get("laccountid").toString()!=null && map.get("laccountid").toString().trim().length()>0)
			{
				lAccountID = Long.parseLong(map.get("laccountid").toString().trim()); //账户ID
			}
			if(map.get("laccounttypeid").toString()!=null && map.get("laccounttypeid").toString().trim().length()>0)
			{
				lAccountTypeID = Long.parseLong(map.get("laccounttypeid").toString().trim()); //账户ID
			}
			if(map.get("laccountgroupid").toString()!=null && map.get("laccountgroupid").toString().trim().length()>0)
			{
				lAccountGroupID = Long.parseLong(map.get("laccountgroupid").toString().trim()); //账户ID
			}
			if(map.get("tsstart").toString()!=null && map.get("tsstart").toString().trim().length()>0)
			{
				strStartDate = map.get("tsstart").toString().trim();
				tsStartDate = DataFormat.getDateTime(strStartDate);  //起始日期
			}
			if(map.get("tsend").toString()!=null && map.get("tsend").toString().trim().length()>0)
			{
				strEndDate = map.get("tsend").toString().trim();
				tsEndDate = DataFormat.getDateTime(strEndDate);  //起始日期
			}
//			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
//					||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
//					||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
//					||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) 
//					&& map.get("strcontractcodectrl").toString()!=null 
//					&& map.get("strcontractcodectrl").toString().trim().length()>0)
//			{
//				strContractCode = map.get("strcontractcodectrl").toString().trim(); //合同号
//			}
			
//			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
//				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
//				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
//				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
//				&& map.get("strcontractcode").toString()!=null 
//				&& map.get("strcontractcode").toString().trim().length()>0)
//			{
//				lContractID = Long.parseLong(map.get("strcontractcode").toString().trim()); //合同ID
//			}
			
//			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
//				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
//				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
//				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
//				&& map.get("strloannotenoctrl").toString()!=null 
//				&& map.get("strloannotenoctrl").toString().trim().length()>0)
//			{
//				strLoanNoteNo = map.get("strloannotenoctrl").toString().trim(); //放款通知单号
//			}
//			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
//				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
//				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
//				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
//				&& map.get("strloannoteno").toString()!=null 
//				&& map.get("strloannoteno").toString().trim().length()>0 )
//			{
//				lLoanNoteID = Long.parseLong(map.get("strloannoteno").toString().trim());  //放款通知单ID
//			}
			
//			if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED 
//					|| lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) 
//					&& map.get("strfixeddepositnoctrl")!=null 
//					&& map.get("strfixeddepositnoctrl").toString().trim().length()>0)
//			{
//				if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
//				{
//					strFixedDepositNo = map.get("strfixeddepositnoctrl").toString().trim(); //定期存款单据号
//				}
//				else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
//				{
//					strFixedDepositNo = map.get("strnotifydepositnoctrl").toString().trim(); //通知存款单据号
//				}
//			}

//			if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED || 
//				lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) 
//				&& map.get("strfixeddepositnoctrl")!=null 
//				&& map.get("strfixeddepositnoctrl").toString().trim().length()>0)
//			{
//				if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
//				{		
//					lSubAccountID = Long.parseLong(map.get("strfixeddepositno").toString().trim());//子账户ID
//				}
//				else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
//				{
//					lSubAccountID = Long.parseLong(map.get("strnotifydepositno").toString().trim());  //子账户ID
//				}
//			}
			if(map.get("lofficeid").toString()!=null && map.get("lofficeid").toString().trim().length()>0)
			{
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim()); //账户ID
			}
			if(map.get("lcurrencyid").toString()!=null && map.get("lcurrencyid").toString().trim().length()>0)
			{
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim()); //账户ID
			}
			if(map.get("sclientcode").toString()!=null && map.get("sclientcode").toString().trim().length()>0)
			{
				sClientCode = map.get("sclientcode").toString().trim(); //账户ID
			}
			//qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			
			//初始化并设置查询条件类
	        QueryTransAccountDetailWhereInfo qtai = new QueryTransAccountDetailWhereInfo();
			QTransAccountDetail qobj = new QTransAccountDetail();
			
		    qInfo.setOfficeID(lOfficeID);
			qInfo.setCurrencyID(lCurrencyID);
			qInfo.setClientCode(sClientCode);//客户号
			qInfo.setAccountNo(strAccountNo);//账号
			qInfo.setAccountID(lAccountID);//账户ID
			qInfo.setAccountTypeID(lAccountTypeID);//账户类型ID
			//qInfo.setContractCode(strContractCode);//合同号
			//qInfo.setContractID(lContractID);//合同ID
			//qInfo.setLoanNoteNo(strLoanNoteNo);//放款单号
			//qInfo.setLoanNoteID(lLoanNoteID);//放款单ID
			//qInfo.setFixedDepositNo(strFixedDepositNo);//定期存款单据号
			//qInfo.setSubAccountID(lSubAccountID);//定期存款号对应的子账户ID
			qInfo.setStartDate(tsStartDate);
			qInfo.setEndDate(tsEndDate);
			//qInfo.setDesc(1);
			qInfo.setOrderField(1);
			
			pagerInfo = oBAccountQueryBiz.queryOBAccountQueryAmountDetailInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
}
