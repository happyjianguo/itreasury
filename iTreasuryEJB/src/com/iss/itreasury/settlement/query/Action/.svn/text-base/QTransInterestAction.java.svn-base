package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QTransInterestBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransInterestConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 账户查询操作类
 * 
 * @author xiang
 * 
 */
public class QTransInterestAction {

	
	QTransInterestBiz biz = new QTransInterestBiz();

	public PagerInfo queryTransInterest(Map map) throws Exception {

		long lOfficeID = Long.valueOf((String)map.get("lofficeid")).longValue();//办事处
		long lCurrencyID = Long.valueOf((String)map.get("lcurrencyid")).longValue();//币种
		
		long lInterestSettlementType = -1;//结息类型(1,结算交易；2，结息记录)
		long[] lInterestType = new long[]{SETTConstant.InterestFeeType.INTEREST ,SETTConstant.InterestFeeType.COMMISION,SETTConstant.InterestFeeType.ASSURE,SETTConstant.InterestFeeType.COMPOUNDINTEREST ,SETTConstant.InterestFeeType.FORFEITINTEREST };;//利息类型
		
		long lClientIDStart = -1;//客户ID（由）
		String sClientNoStart = "";//客户编号（由）
		long lClientIDEnd = -1;//客户ID（至）
		String sClientNoEnd = "";//客户编号（至）
		long lAccountIDStart = -1;//账户（由）
		String sAccountNoStart = "";//账户号（由）
		long lAccountIDEnd = -1;//账户（至）
		String sAccountNoEnd = "";//账户号（至）
		//add by 2012-05-18 添加指定账户编号
		String strAccountCodes = "";
		long lAccountTypeID = -1;//账户类型ID
				
		String sFixedDepositNoStart = "";//定期存单号（由）
		String sFixedDepositNoEnd = "";//定期存单号（至）	
		String sNotifyDepositNoStart = "";//通知存单号（由）
		String sNotifyDepositNoEnd = "";//通知存单号（至）	
		long lDepositTerm = -1;// 存款期限
		
		String sContractNoStart = "";//合同号（由）
		String sContractNoEnd = "";//合同号（至）	
		String sPayFormNoStart = "";//放款通知单(贴现凭证)ID（由）
		String sPayFormNoEnd = "";//放款通知单(贴现凭证)ID（至）	
		long lLoanTypeID = -1;//贷款种类
		long lLoanTerm = -1;//贷款期限
		long lLoanYear = -1;//贷款年期
		
		long lConsignClientID = -1;//委托方客户ID
		long lClientNature = -1;//客户性质
		
		Timestamp tsAutoExecuteDateStart = null;//结息自动执行时间（由）
		Timestamp tsAutoExecuteDateEnd = null;//结息自动执行时间（至）
		Timestamp tsExecuteDateStart = null;//结算执行日（由）
		Timestamp tsExecuteDateEnd = null;//结算执行日（至）		
		Timestamp tsCalInterestDateStart = null;//结息日期（由）
		Timestamp tsCalInterestDateEnd = null;//结息日期（至）	
		
		long lEnterpriseTypeID1 = -1;//客户属性1
		long lEnterpriseTypeID2 = -1;//客户属性2
		long lEnterpriseTypeID3 = -1;//客户属性3
		long lEnterpriseTypeID4 = -1;//客户属性4
		long lEnterpriseTypeID5 = -1;//客户属性5
		long lEnterpriseTypeID6 = -1;//客户属性6
		
		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4
		
		long lOrderID = -1;//排序类型ID
		long lDESC = -1;//升降序ID
		long lPageCount = -1;//每页纪录条数

		long unit = 1;
		
		String strTemp = null;
		strTemp = (String)map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			unit = Long.valueOf(strTemp).longValue();
		}
		//读取数据
		//结息类型(1,结算交易；2，结息记录)
		strTemp = (String)map.get("linterestsettlementtype");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lInterestSettlementType = Long.valueOf(strTemp).longValue();
			if (lInterestSettlementType == 3 || lInterestSettlementType == 4)
			{
				lInterestType = null;
			}
		}
		
		if (map.get("checkbox28") != null && map.get("checkbox28").equals("1"))
		{
			//利息类型
			strTemp = (String)map.get("linteresttype");
			if (strTemp != null && strTemp.trim().length() > 0 && Long.valueOf(strTemp).longValue() > 0)
			{
			    lInterestType = new long[]{Long.valueOf(strTemp).longValue()};
			}
			else 
			{
				//lInterestType = new long[]{SETTConstant.InterestFeeType.INTEREST ,SETTConstant.InterestFeeType.COMPOUNDINTEREST ,SETTConstant.InterestFeeType.FORFEITINTEREST ,SETTConstant.InterestFeeType.INTERESTTAX };
			}
		}
		
		//客户ID（由）
		strTemp = (String)map.get("lclientidstart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lClientIDStart = Long.valueOf(strTemp).longValue();
		}
		//客户编号（由）
		strTemp = (String)map.get("sclientnostart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sClientNoStart = strTemp;
		}
		//客户ID（至）
		strTemp = (String)map.get("lclientidend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lClientIDEnd = Long.valueOf(strTemp).longValue();
		}
		//客户编号（至）
		strTemp = (String)map.get("sclientnoend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sClientNoEnd = strTemp;
		}

		//账户（由）
		strTemp = (String)map.get("laccountidstart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountIDStart = Long.valueOf(strTemp).longValue();
		}
		//账户号（由）
		strTemp = (String)map.get("saccountnostart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sAccountNoStart = strTemp;
		}

		//账户（至）
		strTemp = (String)map.get("laccountidend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountIDEnd = Long.valueOf(strTemp).longValue();
		}
		//账户号（至）
		strTemp = (String)map.get("saccountnoend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sAccountNoEnd = strTemp;
		}
		
		//add by 2012-05-18 添加指定账户编号
		strTemp = (String)map.get("straccountcodes");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strAccountCodes = strTemp;
		}

		//账户类型ID
		strTemp = (String)map.get("laccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountTypeID = Long.valueOf(strTemp).longValue();
		}
		
		//定期存单号（由）
		strTemp = (String)map.get("sfixeddepositnostart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sFixedDepositNoStart = strTemp;
		}
		//定期存单号（至）	
		strTemp = (String)map.get("sfixeddepositnoend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sFixedDepositNoEnd = strTemp;
		}
		
		//通知存单号（由）
		strTemp = (String)map.get("snotifydepositnostart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sNotifyDepositNoStart = strTemp;
		}
		//通知存单号（至）	
		strTemp = (String)map.get("snotifydepositnoend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sNotifyDepositNoEnd = strTemp;
		}

		// 存款期限
		strTemp = (String)map.get("ldepositterm");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lDepositTerm = Long.valueOf(strTemp).longValue();
		}

		//合同号（由）
		strTemp = (String)map.get("scontractnostart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sContractNoStart = strTemp;
		}
		//合同号（至）
		strTemp = (String)map.get("scontractnostart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sContractNoEnd = strTemp;
		}

		//放款通知单(贴现凭证)ID（由）
		strTemp = (String)map.get("spayformnostart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sPayFormNoStart = strTemp;
		}
		//放款通知单(贴现凭证)ID（至）	
		strTemp = (String)map.get("spayformnoend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    sPayFormNoEnd = strTemp;
		}

		// 贷款种类
		strTemp = (String)map.get("lloantypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lLoanTypeID = Long.valueOf(strTemp).longValue();
		}

		// 贷款期限
		strTemp = (String)map.get("lLoanTerm");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lLoanTerm = Long.valueOf(strTemp).longValue();
		}

		// 贷款年期
		strTemp = (String)map.get("lloanyear");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lLoanYear = Long.valueOf(strTemp).longValue();
		}

		//委托方客户ID
		strTemp = (String)map.get("lconsignclientid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lConsignClientID = Long.valueOf(strTemp).longValue();
		}

		//客户性质
		strTemp = (String)map.get("lenterprisetypeid1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lEnterpriseTypeID1 = Long.valueOf(strTemp).longValue();
		}
		
		//客户性质
		strTemp = (String)map.get("lenterprisetypeid2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lEnterpriseTypeID2 = Long.valueOf(strTemp).longValue();
		}
		
		//客户性质
		strTemp = (String)map.get("lenterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lEnterpriseTypeID3 = Long.valueOf(strTemp).longValue();
		}
		
		//客户性质
		strTemp = (String)map.get("lenterprisetypeid4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lEnterpriseTypeID4 = Long.valueOf(strTemp).longValue();
		}
		
		//客户性质
		strTemp = (String)map.get("lenterprisetypeid5");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lEnterpriseTypeID5 = Long.valueOf(strTemp).longValue();
		}
		
		//客户性质
		strTemp = (String)map.get("lenterprisetypeid6");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lEnterpriseTypeID6 = Long.valueOf(strTemp).longValue();
		}
		
		//结息自动执行时间（由）
		strTemp = (String)map.get("tsautoexecutedatestart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsAutoExecuteDateStart = DataFormat.getDateTime(strTemp);
		}
		//结息自动执行时间（至）
		strTemp = (String)map.get("tsautoexecutedateend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsAutoExecuteDateEnd = DataFormat.getDateTime(strTemp);
		}
		
		//结算执行日（由）
		strTemp = (String)map.get("tsexecutedatestart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecuteDateStart = DataFormat.getDateTime(strTemp);
		}
		//结算执行日（至）
		strTemp = (String)map.get("tsexecutedateend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecuteDateEnd = DataFormat.getDateTime(strTemp);
		}
		//结息日期（由）
		strTemp = (String)map.get("tscalinterestdatestart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsCalInterestDateStart = DataFormat.getDateTime(strTemp);
		}
		//结息日期（至）
		strTemp = (String)map.get("tscalinterestdateend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsCalInterestDateEnd = DataFormat.getDateTime(strTemp);
		}
		//lOrderID
		strTemp = (String)map.get("lorderid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lOrderID = Long.valueOf(strTemp).longValue();
		}
		//扩展属性1
		strTemp = (String)map.get("lextendattribute1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute1 = Long.parseLong(strTemp);
		}
		//扩展属性2
		strTemp = (String)map.get("lextendattribute2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute2 = Long.parseLong(strTemp);
		}
		//扩展属性3
		strTemp = (String)map.get("lextendattribute3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute3 = Long.parseLong(strTemp);
		}
		//扩展属性4
		strTemp = (String)map.get("lextendattribute4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute4 = Long.parseLong(strTemp);
		}
		
		PagerInfo pagerInfo = null;

		QueryTransInterestConditionInfo info = new QueryTransInterestConditionInfo();
		
		info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setInterestSettlementType(lInterestSettlementType);
		info.setInterestType(lInterestType);
		info.setClientIDStart(lClientIDStart);

		info.setClientNoStart(sClientNoStart);
		info.setClientIDEnd(lClientIDEnd);
		info.setClientNoEnd(sClientNoEnd);
		info.setAccountIDStart(lAccountIDStart);
		if (lAccountIDStart > 0)
			info.setAccountNoStart(sAccountNoStart);
		
		info.setAccountIDEnd(lAccountIDEnd);
		if (lAccountIDEnd > 0)
			info.setAccountNoEnd(sAccountNoEnd);
		//add by 2012-05-18 添加指定账户编号
		info.setAppointAccountNo(strAccountCodes);
		info.setAccountTypeID(lAccountTypeID);
		info.setFixedDepositNoStart(sFixedDepositNoStart);
		info.setFixedDepositNoEnd(sFixedDepositNoEnd);

		info.setNotifyDepositNoStart(sNotifyDepositNoStart);
		info.setNotifyDepositNoEnd(sNotifyDepositNoEnd);
		info.setDepositTerm(lDepositTerm);
		info.setContractNoStart(sContractNoStart);
		info.setContractNoEnd(sContractNoEnd);

		info.setPayFormNoStart(sPayFormNoStart);
		info.setPayFormNoEnd(sPayFormNoEnd);
		info.setLoanTypeID(lLoanTypeID);
		info.setLoanTerm(lLoanTerm);
		info.setLoanYear(lLoanYear);

		info.setConsignClientID(lConsignClientID);
		info.setClientNature(lClientNature);
		info.setAutoExecuteDateStart(tsAutoExecuteDateStart);
		info.setAutoExecuteDateEnd(tsAutoExecuteDateEnd);
		info.setExecuteDateStart(tsExecuteDateStart);

		info.setExecuteDateEnd(tsExecuteDateEnd);
		info.setCalInterestDateStart(tsCalInterestDateStart);
		info.setCalInterestDateEnd(tsCalInterestDateEnd);
		info.setOrderID(lOrderID);
		info.setDESC(lDESC);
		info.setPageCount(lPageCount);
		
		info.setEnterpriseTypeID1(lEnterpriseTypeID1);
		info.setEnterpriseTypeID2(lEnterpriseTypeID2);
		info.setEnterpriseTypeID3(lEnterpriseTypeID3);
		info.setEnterpriseTypeID4(lEnterpriseTypeID4);
		info.setEnterpriseTypeID5(lEnterpriseTypeID5);
		info.setEnterpriseTypeID6(lEnterpriseTypeID6);
		
		info.setExtendAttribute1(lExtendAttribute1);
		info.setExtendAttribute2(lExtendAttribute2);
		info.setExtendAttribute3(lExtendAttribute3);
		info.setExtendAttribute4(lExtendAttribute4);
		pagerInfo = biz.queryTransInterest(info,unit);
		return pagerInfo;
	}

}
