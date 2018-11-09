package com.iss.itreasury.ebank.obquery.action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.ebank.obquery.bizlogic.OBQueryTransAccountBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class OBQueryTransAccountAction {
	
	OBQueryTransAccountBiz oBQueryTransAccountBiz = new OBQueryTransAccountBiz();
	
	public PagerInfo queryTransAccountDetail(Map map) throws Exception{
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
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
					||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
					||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
					||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) 
					&& map.get("strcontractcodectrl").toString()!=null 
					&& map.get("strcontractcodectrl").toString().trim().length()>0)
			{
				strContractCode = map.get("strcontractcodectrl").toString().trim(); //合同号
			}
			
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
				&& map.get("strcontractcode").toString()!=null 
				&& map.get("strcontractcode").toString().trim().length()>0)
			{
				lContractID = Long.parseLong(map.get("strcontractcode").toString().trim()); //合同ID
			}
			
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
				&& map.get("strloannotenoctrl").toString()!=null 
				&& map.get("strloannotenoctrl").toString().trim().length()>0)
			{
				strLoanNoteNo = map.get("strloannotenoctrl").toString().trim(); //放款通知单号
			}
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
				&& map.get("strloannoteno").toString()!=null 
				&& map.get("strloannoteno").toString().trim().length()>0 )
			{
				lLoanNoteID = Long.parseLong(map.get("strloannoteno").toString().trim());  //放款通知单ID
			}
			
			if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED 
					|| lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) 
					&& map.get("strfixeddepositnoctrl")!=null 
					&& map.get("strfixeddepositnoctrl").toString().trim().length()>0)
			{
				if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
				{
					strFixedDepositNo = map.get("strfixeddepositnoctrl").toString().trim(); //定期存款单据号
				}
				else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
				{
					strFixedDepositNo = map.get("strnotifydepositnoctrl").toString().trim(); //通知存款单据号
				}
			}

			if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED || 
				lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) 
				&& map.get("strfixeddepositnoctrl")!=null 
				&& map.get("strfixeddepositnoctrl").toString().trim().length()>0)
			{
				if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
				{		
					lSubAccountID = Long.parseLong(map.get("strfixeddepositno").toString().trim());//子账户ID
				}
				else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
				{
					lSubAccountID = Long.parseLong(map.get("strnotifydepositno").toString().trim());  //子账户ID
				}
			}
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
			qInfo.setContractCode(strContractCode);//合同号
			qInfo.setContractID(lContractID);//合同ID
			qInfo.setLoanNoteNo(strLoanNoteNo);//放款单号
			qInfo.setLoanNoteID(lLoanNoteID);//放款单ID
			qInfo.setFixedDepositNo(strFixedDepositNo);//定期存款单据号
			qInfo.setSubAccountID(lSubAccountID);//定期存款号对应的子账户ID
			qInfo.setStartDate(tsStartDate);
			qInfo.setEndDate(tsEndDate);
			//qInfo.setDesc(1);
			qInfo.setOrderField(1);
			
			pagerInfo = oBQueryTransAccountBiz.queryTransAccountDetail(qInfo,lAccountGroupID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
