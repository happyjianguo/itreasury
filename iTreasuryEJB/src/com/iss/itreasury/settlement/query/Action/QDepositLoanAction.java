package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QDepositLoanBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryDepositLoanWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryOtherDepositInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.SessionMng;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 账户查询操作类
 * 
 * @author xiang
 * 
 */
public class QDepositLoanAction {

	QDepositLoanBiz biz = new QDepositLoanBiz();

	public PagerInfo queryDepositLoan(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		
		  //定义变量
		long lOfficeID = Long.valueOf((String)map.get("lofficeid"));
		long lCurrencyID =Long.valueOf((String)map.get("lcurrencyid"));
		long lClientManager = -1;
		long lEnterpriseTypeID1 = -1;//属性1
		long lEnterpriseTypeID2 = -1;//属性2
		long lEnterpriseTypeID3 = -1;//属性3
		long lEnterpriseTypeID4 = -1;//属性4
		long lEnterpriseTypeID5 = -1;//属性5
		long lEnterpriseTypeID6 = -1;//属性6
		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4
		Timestamp tsDate = null;//日期
		long unit = 1;
		//读取数据
		String strTemp = null;
		
		strTemp = (String)map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			unit = Long.valueOf(unit).longValue();
		}
		strTemp = (String)map.get("strdate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDate = DataFormat.getDateTime(strTemp);
		}
		//客户经理
		strTemp = (String)map.get("lclientmanager");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientManager = Long.valueOf(strTemp).longValue();
		}
		//属性1
		strTemp = (String)map.get("enterprisetypeid1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID1 = Long.valueOf(strTemp).longValue();
		}
		//属性2
		strTemp = (String)map.get("enterprisetypeid2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID2 = Long.valueOf(strTemp).longValue();
		}
		//属性3
		strTemp = (String)map.get("enterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID3 = Long.valueOf(strTemp).longValue();
		}
		//属性4
		strTemp = (String)map.get("enterprisetypeid4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID4 = Long.valueOf(strTemp).longValue();
		}
		//属性5
		strTemp = (String)map.get("enterprisetypeid5");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID5 = Long.valueOf(strTemp).longValue();
		}
		//属性6
		strTemp = (String)map.get("enterprisetypeid6");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID6 = Long.valueOf(strTemp).longValue();
		}
		//扩展属性1
		strTemp = (String)map.get("selextendattribute1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute1 = Long.parseLong(strTemp);
		}
		//扩展属性2
		strTemp = (String)map.get("selextendattribute2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute2 = Long.parseLong(strTemp);
		}
		//扩展属性3
		strTemp = (String)map.get("selextendattribute3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute3 = Long.parseLong(strTemp);
		}
		//扩展属性4
		strTemp = (String)map.get("selextendattribute4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute4 = Long.parseLong(strTemp);
		}
		
		QueryDepositLoanWhereInfo info = new QueryDepositLoanWhereInfo();
		
		info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setClientManager(lClientManager);
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
		info.setDate(tsDate);
		
		pagerInfo = biz.queryDepositLoan(info,unit);
		return pagerInfo;
	}
	
	public PagerInfo queryDepositLoan_Deposit(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		
		  //定义变量
		long lOfficeID = Long.valueOf((String)map.get("lofficeid"));
		long lCurrencyID =Long.valueOf((String)map.get("lcurrencyid"));
		long lClientManager = -1;
		long lEnterpriseTypeID1 = -1;//属性1
		long lEnterpriseTypeID2 = -1;//属性2
		long lEnterpriseTypeID3 = -1;//属性3
		long lEnterpriseTypeID4 = -1;//属性4
		long lEnterpriseTypeID5 = -1;//属性5
		long lEnterpriseTypeID6 = -1;//属性6
		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4
		Timestamp tsDate = null;//日期
	    long lDepositLoanSearchID=-1;
		long unit = 1;
		long type=-1;
		//读取数据
		String strTemp = null;
		
		strTemp = (String)map.get("type");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			type = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			unit = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("strdate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDate = DataFormat.getDateTime(strTemp);
		}
		//客户经理
		strTemp = (String)map.get("lclientmanager");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientManager = Long.valueOf(strTemp).longValue();
		}
		//属性1
		strTemp = (String)map.get("enterprisetypeid1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID1 = Long.valueOf(strTemp).longValue();
		}
		//属性2
		strTemp = (String)map.get("enterprisetypeid2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID2 = Long.valueOf(strTemp).longValue();
		}
		//属性3
		strTemp = (String)map.get("enterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID3 = Long.valueOf(strTemp).longValue();
		}
		//属性4
		strTemp = (String)map.get("enterprisetypeid4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID4 = Long.valueOf(strTemp).longValue();
		}
		//属性5
		strTemp = (String)map.get("enterprisetypeid5");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID5 = Long.valueOf(strTemp).longValue();
		}
		//属性6
		strTemp = (String)map.get("enterprisetypeid6");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID6 = Long.valueOf(strTemp).longValue();
		}
		//扩展属性1
		strTemp = (String)map.get("selextendattribute1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute1 = Long.parseLong(strTemp);
		}
		//扩展属性2
		strTemp = (String)map.get("selextendattribute2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute2 = Long.parseLong(strTemp);
		}
		//扩展属性3
		strTemp = (String)map.get("selextendattribute3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute3 = Long.parseLong(strTemp);
		}
		//扩展属性4
		strTemp = (String)map.get("selextendattribute4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute4 = Long.parseLong(strTemp);
		}
		
		strTemp = (String)map.get("ldepositloansearchid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lDepositLoanSearchID = Long.parseLong(strTemp);
		}
		
		QueryDepositLoanWhereInfo info = new QueryDepositLoanWhereInfo();
		//
        info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setClientManager(lClientManager);
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
		info.setDate(tsDate);
		info.setDepositLoanSearchID(lDepositLoanSearchID);
		
		pagerInfo = biz.queryDepositLoan_Deposit(info,unit);
		return pagerInfo;
	}

	public PagerInfo queryTransAccountDetail(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		
		  //定义变量
		long lOfficeID = Long.valueOf((String)map.get("lofficeid"));
		long lCurrencyID =Long.valueOf((String)map.get("lcurrencyid"));
		long lClientManager = -1;
		long lEnterpriseTypeID1 = -1;//属性1
		long lEnterpriseTypeID2 = -1;//属性2
		long lEnterpriseTypeID3 = -1;//属性3
		long lEnterpriseTypeID4 = -1;//属性4
		long lEnterpriseTypeID5 = -1;//属性5
		long lEnterpriseTypeID6 = -1;//属性6
		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4
		Timestamp tsDate = null;//日期
	    long lDepositLoanSearchID=-1;
		long unit = 1;
		long type=-1;
		//读取数据
		String strTemp = null;
		
		strTemp = (String)map.get("type");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			type = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			unit = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("strdate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDate = DataFormat.getDateTime(strTemp);
		}
		//客户经理
		strTemp = (String)map.get("lclientmanager");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientManager = Long.valueOf(strTemp).longValue();
		}
		//属性1
		strTemp = (String)map.get("enterprisetypeid1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID1 = Long.valueOf(strTemp).longValue();
		}
		//属性2
		strTemp = (String)map.get("enterprisetypeid2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID2 = Long.valueOf(strTemp).longValue();
		}
		//属性3
		strTemp = (String)map.get("enterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID3 = Long.valueOf(strTemp).longValue();
		}
		//属性4
		strTemp = (String)map.get("enterprisetypeid4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID4 = Long.valueOf(strTemp).longValue();
		}
		//属性5
		strTemp = (String)map.get("enterprisetypeid5");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID5 = Long.valueOf(strTemp).longValue();
		}
		//属性6
		strTemp = (String)map.get("enterprisetypeid6");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID6 = Long.valueOf(strTemp).longValue();
		}
		//扩展属性1
		strTemp = (String)map.get("selextendattribute1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute1 = Long.parseLong(strTemp);
		}
		//扩展属性2
		strTemp = (String)map.get("selextendattribute2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute2 = Long.parseLong(strTemp);
		}
		//扩展属性3
		strTemp = (String)map.get("selextendattribute3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute3 = Long.parseLong(strTemp);
		}
		//扩展属性4
		strTemp = (String)map.get("selextendattribute4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute4 = Long.parseLong(strTemp);
		}
		
		strTemp = (String)map.get("ldepositloansearchid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lDepositLoanSearchID = Long.parseLong(strTemp);
		}
		
		QueryDepositLoanWhereInfo info = new QueryDepositLoanWhereInfo();
		//
        info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setClientManager(lClientManager);
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
		info.setDate(tsDate);
		info.setDepositLoanSearchID(lDepositLoanSearchID);
		
		pagerInfo = biz.queryTransAccountDetail(info,unit);
		return pagerInfo;
	}

	public PagerInfo queryCosignLoanSumByLoanTypeID(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		
		  //定义变量
		long lOfficeID = Long.valueOf((String)map.get("lofficeid"));
		long lCurrencyID =Long.valueOf((String)map.get("lcurrencyid"));
		long lClientManager = -1;
		long lEnterpriseTypeID1 = -1;//属性1
		long lEnterpriseTypeID2 = -1;//属性2
		long lEnterpriseTypeID3 = -1;//属性3
		long lEnterpriseTypeID4 = -1;//属性4
		long lEnterpriseTypeID5 = -1;//属性5
		long lEnterpriseTypeID6 = -1;//属性6
		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4
		Timestamp tsDate = null;//日期
	    long lDepositLoanSearchID=-1;
		long unit = 1;
		long type=-1;
		//读取数据
		String strTemp = null;
		
		strTemp = (String)map.get("type");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			type = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			unit = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("strdate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDate = DataFormat.getDateTime(strTemp);
		}
		//客户经理
		strTemp = (String)map.get("lclientmanager");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientManager = Long.valueOf(strTemp).longValue();
		}
		//属性1
		strTemp = (String)map.get("enterprisetypeid1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID1 = Long.valueOf(strTemp).longValue();
		}
		//属性2
		strTemp = (String)map.get("enterprisetypeid2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID2 = Long.valueOf(strTemp).longValue();
		}
		//属性3
		strTemp = (String)map.get("enterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID3 = Long.valueOf(strTemp).longValue();
		}
		//属性4
		strTemp = (String)map.get("enterprisetypeid4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID4 = Long.valueOf(strTemp).longValue();
		}
		//属性5
		strTemp = (String)map.get("enterprisetypeid5");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID5 = Long.valueOf(strTemp).longValue();
		}
		//属性6
		strTemp = (String)map.get("enterprisetypeid6");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID6 = Long.valueOf(strTemp).longValue();
		}
		//扩展属性1
		strTemp = (String)map.get("selextendattribute1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute1 = Long.parseLong(strTemp);
		}
		//扩展属性2
		strTemp = (String)map.get("selextendattribute2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute2 = Long.parseLong(strTemp);
		}
		//扩展属性3
		strTemp = (String)map.get("selextendattribute3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute3 = Long.parseLong(strTemp);
		}
		//扩展属性4
		strTemp = (String)map.get("selextendattribute4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute4 = Long.parseLong(strTemp);
		}
		
		strTemp = (String)map.get("ldepositloansearchid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lDepositLoanSearchID = Long.parseLong(strTemp);
		}
		
		QueryDepositLoanWhereInfo info = new QueryDepositLoanWhereInfo();
		//
        info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setClientManager(lClientManager);
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
		info.setDate(tsDate);
		info.setDepositLoanSearchID(lDepositLoanSearchID);
		
		pagerInfo = biz.queryCosignLoanSumByLoanTypeID(info,unit);
		return pagerInfo;
	}
	
	public PagerInfo queryOtherDepositInfo(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		
		  //定义变量
		long lOfficeID = Long.valueOf((String)map.get("lofficeid"));
		long lCurrencyID =Long.valueOf((String)map.get("lcurrencyid"));
		long lClientManager = -1;
		long lEnterpriseTypeID1 = -1;//属性1
		long lEnterpriseTypeID2 = -1;//属性2
		long lEnterpriseTypeID3 = -1;//属性3
		long lEnterpriseTypeID4 = -1;//属性4
		long lEnterpriseTypeID5 = -1;//属性5
		long lEnterpriseTypeID6 = -1;//属性6
		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4
		Timestamp tsDate = null;//日期
		long unit = 1;
		long AccountTypeID = -1;
		//读取数据
		String strTemp = null;
		
		AccountTypeID = map.get("accounttypeid") ==null?AccountTypeID:Long.valueOf(map.get("accounttypeid")+"").longValue();
		
		strTemp = (String)map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			unit = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)map.get("strdate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDate = DataFormat.getDateTime(strTemp);
		}
		//客户经理
		strTemp = (String)map.get("lclientmanager");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientManager = Long.valueOf(strTemp).longValue();
		}
		//属性1
		strTemp = (String)map.get("enterprisetypeid1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID1 = Long.valueOf(strTemp).longValue();
		}
		//属性2
		strTemp = (String)map.get("enterprisetypeid2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID2 = Long.valueOf(strTemp).longValue();
		}
		//属性3
		strTemp = (String)map.get("enterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID3 = Long.valueOf(strTemp).longValue();
		}
		//属性4
		strTemp = (String)map.get("enterprisetypeid4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID4 = Long.valueOf(strTemp).longValue();
		}
		//属性5
		strTemp = (String)map.get("enterprisetypeid5");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID5 = Long.valueOf(strTemp).longValue();
		}
		//属性6
		strTemp = (String)map.get("enterprisetypeid6");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID6 = Long.valueOf(strTemp).longValue();
		}
		//扩展属性1
		strTemp = (String)map.get("selextendattribute1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute1 = Long.parseLong(strTemp);
		}
		//扩展属性2
		strTemp = (String)map.get("selextendattribute2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute2 = Long.parseLong(strTemp);
		}
		//扩展属性3
		strTemp = (String)map.get("selextendattribute3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute3 = Long.parseLong(strTemp);
		}
		//扩展属性4
		strTemp = (String)map.get("selextendattribute4");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lExtendAttribute4 = Long.parseLong(strTemp);
		}
		
		QueryOtherDepositInfo info = new QueryOtherDepositInfo();
		//
		//
        info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setExtendAttribute1(lExtendAttribute1);
		info.setExtendAttribute2(lExtendAttribute2);
		info.setExtendAttribute3(lExtendAttribute3);
		info.setExtendAttribute4(lExtendAttribute4);
		info.setAccountTypeID(AccountTypeID);
		info.setDate(tsDate);
		info.setLClientManager(lClientManager);
		info.setEnterpriseTypeID1(lEnterpriseTypeID1);
		info.setEnterpriseTypeID2(lEnterpriseTypeID2);
		info.setEnterpriseTypeID3(lEnterpriseTypeID3);
		info.setEnterpriseTypeID4(lEnterpriseTypeID4);
		info.setEnterpriseTypeID5(lEnterpriseTypeID5);
		info.setEnterpriseTypeID6(lEnterpriseTypeID6);
		
		pagerInfo = biz.queryOtherDepositInfo(info,unit);
		return pagerInfo;
		
	}

	public PagerInfo queryDepositLoan_FixedNotice(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		
		  //定义变量
		long lOfficeID = Long.valueOf((String)map.get("lofficeid"));
		long lCurrencyID =Long.valueOf((String)map.get("lcurrencyid"));
		Timestamp tsDate = null;//日期
		String strDate = "";
		long unit=1;
		long lClientManager = -1;
		long lEnterpriseTypeID1 = -1;//属性1
		long lEnterpriseTypeID2 = -1;//属性2
		long lEnterpriseTypeID3 = -1;//属性3
		long lEnterpriseTypeID4 = -1;//属性4
		long lEnterpriseTypeID5 = -1;//属性5
		long lEnterpriseTypeID6 = -1;//属性6

		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4
		long lDepositAccountTypeID = -1;//区分定期通知的标识
		long lDepositLoanSearchID = -1;//设置表中的ID
		
		String CurrencySymbol="";
		
		//读取数据
		String strTemp = null;
		
		strTemp = (String)map.get("strdate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDate = DataFormat.getDateTime(strTemp);
			strDate = DataFormat.getDateString(tsDate);
		}
		
		strTemp = (String)map.get("currencysymbol");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			CurrencySymbol = (String)map.get("currencysymbol");
		}
		
		strTemp = (String)map.get("ldepositaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lDepositAccountTypeID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			unit = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)map.get("ldepositloansearchid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lDepositLoanSearchID = Long.valueOf(strTemp).longValue();
		}
		//客户经理
		strTemp = (String)map.get("lclientmanager");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientManager = Long.valueOf(strTemp).longValue();
		}
		//属性1
		strTemp = (String)map.get("lenterprisetypeid1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID1 = Long.valueOf(strTemp).longValue();
		}
		//属性2
		strTemp = (String)map.get("lenterprisetypeid2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID2 = Long.valueOf(strTemp).longValue();
		}
		//属性3
		strTemp = (String)map.get("lenterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID3 = Long.valueOf(strTemp).longValue();
		}
		//属性4
		strTemp = (String)map.get("lenterprisetypeid3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID4 = Long.valueOf(strTemp).longValue();
		}
		//属性5
		strTemp = (String)map.get("lenterprisetypeid5");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID5 = Long.valueOf(strTemp).longValue();
		}
		//属性6
		strTemp = (String)map.get("lenterprisetypeid6");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lEnterpriseTypeID6 = Long.valueOf(strTemp).longValue();
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

        QueryDepositLoanWhereInfo qdlwi = null;
		
		qdlwi = new QueryDepositLoanWhereInfo();
		//
        qdlwi.setOfficeID(lOfficeID);
		qdlwi.setCurrencyID(lCurrencyID);
		qdlwi.setClientManager(lClientManager);
		qdlwi.setEnterpriseTypeID1(lEnterpriseTypeID1);
		qdlwi.setEnterpriseTypeID2(lEnterpriseTypeID2);
		qdlwi.setEnterpriseTypeID3(lEnterpriseTypeID3);
		qdlwi.setEnterpriseTypeID4(lEnterpriseTypeID4);
		qdlwi.setEnterpriseTypeID5(lEnterpriseTypeID5);
		qdlwi.setEnterpriseTypeID6(lEnterpriseTypeID6);
		qdlwi.setExtendAttribute1(lExtendAttribute1);
		qdlwi.setExtendAttribute2(lExtendAttribute2);
		qdlwi.setExtendAttribute3(lExtendAttribute3);
		qdlwi.setExtendAttribute4(lExtendAttribute4);
		qdlwi.setDepositAccountTypeID(lDepositAccountTypeID);
		qdlwi.setDate(tsDate);
		qdlwi.setDepositLoanSearchID(lDepositLoanSearchID);
		
		pagerInfo = biz.queryDepositLoan_FixedNotice(qdlwi,unit,CurrencySymbol);
		return pagerInfo;
		
	}

	public PagerInfo queryFixedDepositInfo(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		
		String strAccountNoFrom = "";
		String strAccountNoTo = "";
		//add by 2012-05-18 添加指定账户编号
		String strAccountCodes = "";
		String strClientNameFrom = "";
		String strClientNameTo = "";
		String strClientNoFrom = "";
		String strClientNoTo = "";
		long lEnterpriseTypeID1 = -1;
		long lEnterpriseTypeID2 = -1;
		long lEnterpriseTypeID3 = -1;
		long lEnterpriseTypeID4 = -1;
		long lEnterpriseTypeID5 = -1;
		long lEnterpriseTypeID6 = -1;
		long lClientManager = -1;
		long lClientSort = -1;
		long lClientType = -1;
		long lAccountType = -1;
		long lDepositOrgin = -1;
		double dFixedAmountFrom = 0.0;
		double dFixedAmountTo = 0.0;
		String strFixedDepositNoFrom = "";
		String strFixedDepositNoTo = "";
		long lFixedDepositStatus = -1;
		java.sql.Timestamp tsFixedEndDate = null;
		java.sql.Timestamp tsFixedEndDateFrom = null;
		java.sql.Timestamp tsFixedEndDateTo = null;
		double dFixedRate = 0.0;
		java.sql.Timestamp tsFixedStartDateFrom = null;
		java.sql.Timestamp tsFixedStartDateTo = null;
		long lIsFixedDeposit = -1;
		long lIsLeaching = -1;
		long lIsNoticeDeposit = -1;
		double dNoticeBalanceFrom = 0.0;
		double dNoticeBalanceTo = 0.0;
		String strNoticeDepositNoFrom = "";
		String strNoticeDepositNoTo = "";
		long lNoticeDepositStatus = -1;
		double dNoticeDrawAmountFrom = 0.0;
		double dNoticeDrawAmountTo = 0.0;
		java.sql.Timestamp tsNoticeEndDate = null;
		java.sql.Timestamp tsNoticeStartDateFrom = null;
		java.sql.Timestamp tsNoticeStartDateTo = null;
		long lOrderField = -1;
		long lParentCompany1 = -1;
		long lParentCompany2 = -1;
		long lCurrencyID = -1;
		long lOfficeID = -1;
		long lFixedDepositTermFrom = -1;
		long lFixedDepositTermTo = -1;
		long lNiticeDays = -1;
		long lExtendAttribute1 = -1;//扩展属性1
		long lExtendAttribute2 = -1;//扩展属性2
		long lExtendAttribute3 = -1;//扩展属性3
		long lExtendAttribute4 = -1;//扩展属性4

		long lDepositNoChoose = -1; // 定期存单查询方式选择

		//保证金存款查询
		long lIsMarginDeposit = -1; // 是否查询保证金存款
		String strMarginDepositNoFrom = ""; // 保证金存款单据号
		String strMarginDepositNoTo = "";
		Timestamp tsMarginStartDateFrom = null; // 起始日期
		Timestamp tsMarginStartDateTo = null;
		Timestamp tsMarginEndDateFrom = null; // 到期日期
		Timestamp tsMarginEndDateTo = null;
		long lMarginDepositStatus = -1; // 保证金存款状态
		double dMarginAmountFrom = 0.0; // 保证金存款金额
		double dMarginAmountTo = 0.0;
		double dMarginRate = 0.0; // 利率
		Timestamp tsMarginEndDate = null; // 定期截止日期
		
		long unit = 1;
		String ControlSource = "";
		
		strAccountNoFrom = (String)map.get("strAccountNoFrom".toLowerCase());
		strAccountNoFrom = (String)map.get("strAccountNoFrom".toLowerCase());
		strAccountNoTo = (String)map.get("strAccountNoTo".toLowerCase());
		strAccountCodes = (String)map.get("strAccountCodes".toLowerCase());
		strClientNameFrom = (String)map.get("strClientNameFrom".toLowerCase());
		strClientNameTo = (String)map.get("strClientNameTo".toLowerCase());
		strClientNoFrom = (String)map.get("strClientNoFrom".toLowerCase());
		strClientNoTo = (String)map.get("strClientNoTo".toLowerCase());
		lClientSort = Long.valueOf((String)map.get("lClientSort".toLowerCase()));
		lClientType = Long.valueOf((String)map.get("lClientType".toLowerCase()));
		lAccountType = Long.valueOf((String)map.get("lAccountType".toLowerCase()));
		lDepositOrgin = Long.valueOf((String)map.get("lDepositOrgin".toLowerCase()));
		dFixedAmountFrom = Double.valueOf((String)map.get("dFixedAmountFrom".toLowerCase()));
		dFixedAmountTo = Double.valueOf((String)map.get("dFixedAmountTo".toLowerCase()));
		strFixedDepositNoFrom = (String)map.get("strFixedDepositNoFrom".toLowerCase());
		strFixedDepositNoTo = (String)map.get("strFixedDepositNoTo".toLowerCase());
		lFixedDepositStatus = Long.valueOf((String)map.get("lFixedDepositStatus".toLowerCase()));
		tsFixedEndDate = toTimestamp(map.get("tsFixedEndDate".toLowerCase()));
		tsFixedEndDateFrom = toTimestamp(map.get("tsFixedEndDateFrom".toLowerCase()));
		tsFixedEndDateTo = toTimestamp(map.get("tsFixedEndDateTo".toLowerCase()));
		dFixedRate = Double.valueOf((String)map.get("dFixedRate".toLowerCase()));
		tsFixedStartDateFrom = toTimestamp(map.get("tsFixedStartDateFrom".toLowerCase()));
		tsFixedStartDateTo = toTimestamp(map.get("tsFixedStartDateTo".toLowerCase()));
		lIsFixedDeposit = Long.valueOf((String)map.get("lIsFixedDeposit".toLowerCase()));
		lIsLeaching = Long.valueOf((String)map.get("lIsLeaching".toLowerCase()));
		lIsNoticeDeposit = Long.valueOf((String)map.get("lIsNoticeDeposit".toLowerCase()));
		dNoticeBalanceFrom = Double.valueOf((String)map.get("dNoticeBalanceFrom".toLowerCase()));
		dNoticeBalanceTo = Double.valueOf((String)map.get("dNoticeBalanceTo".toLowerCase()));
		strNoticeDepositNoFrom = (String)map.get("strNoticeDepositNoFrom".toLowerCase());
		strNoticeDepositNoTo = (String)map.get("strNoticeDepositNoTo".toLowerCase());
		lNoticeDepositStatus = Long.valueOf((String)map.get("lNoticeDepositStatus".toLowerCase()));
		dNoticeDrawAmountFrom = Double.valueOf((String)map.get("dNoticeDrawAmountFrom".toLowerCase()));
		dNoticeDrawAmountTo = Double.valueOf((String)map.get("dNoticeDrawAmountTo".toLowerCase()));
		tsNoticeEndDate = toTimestamp(map.get("tsNoticeEndDate".toLowerCase()));
		tsNoticeStartDateFrom = toTimestamp(map.get("tsNoticeStartDateFrom".toLowerCase()));
		tsNoticeStartDateTo = toTimestamp(map.get("tsNoticeStartDateTo".toLowerCase()));
		lParentCompany1 = Long.valueOf((String)map.get("lParentCompany1".toLowerCase()));
		lParentCompany2 = Long.valueOf((String)map.get("lParentCompany2".toLowerCase()));
		lCurrencyID = Long.valueOf((String)map.get("lCurrencyID".toLowerCase()));
		lOfficeID = Long.valueOf((String)map.get("lOfficeID".toLowerCase()));
		lFixedDepositTermFrom = Long.valueOf((String)map.get("lFixedDepositTermFrom".toLowerCase()));
		lFixedDepositTermTo = Long.valueOf((String)map.get("lFixedDepositTermTo".toLowerCase()));
		lExtendAttribute1 = Long.valueOf((String)map.get("lExtendAttribute1".toLowerCase()));
		lExtendAttribute2 = Long.valueOf((String)map.get("lExtendAttribute2".toLowerCase()));
		lExtendAttribute3 = Long.valueOf((String)map.get("lExtendAttribute3".toLowerCase()));
		lExtendAttribute4 = Long.valueOf((String)map.get("lExtendAttribute4".toLowerCase()));
		lClientManager = Long.valueOf((String)map.get("lClientManager".toLowerCase()));
		lNiticeDays = Long.valueOf((String)map.get("lNiticeDays".toLowerCase()));
		lEnterpriseTypeID1 = Long.valueOf((String)map.get("lEnterpriseTypeID1".toLowerCase()));
		lEnterpriseTypeID2 = Long.valueOf((String)map.get("lEnterpriseTypeID2".toLowerCase()));
		lEnterpriseTypeID3 = Long.valueOf((String)map.get("lEnterpriseTypeID3".toLowerCase()));
		lEnterpriseTypeID4 = Long.valueOf((String)map.get("lEnterpriseTypeID4".toLowerCase()));
		lEnterpriseTypeID5 = Long.valueOf((String)map.get("lEnterpriseTypeID5".toLowerCase()));
		lEnterpriseTypeID6 = Long.valueOf((String)map.get("lEnterpriseTypeID6".toLowerCase()));
		lDepositNoChoose = Long.valueOf((String)map.get("lDepositNoChoose".toLowerCase()));
		lIsMarginDeposit = Long.valueOf((String)map.get("lIsMarginDeposit".toLowerCase()));
		strMarginDepositNoFrom = (String)map.get("strMarginDepositNoFrom".toLowerCase());
		strMarginDepositNoTo = (String)map.get("strMarginDepositNoTo".toLowerCase());
		tsMarginStartDateFrom = toTimestamp(map.get("tsMarginStartDateFrom".toLowerCase()));
		tsMarginStartDateTo = toTimestamp(map.get("tsMarginStartDateTo".toLowerCase()));
		tsMarginEndDateFrom = toTimestamp(map.get("tsMarginEndDateFrom".toLowerCase()));
		tsMarginEndDateTo = toTimestamp(map.get("tsMarginEndDateTo".toLowerCase()));
		lMarginDepositStatus = Long.valueOf((String)map.get("lMarginDepositStatus".toLowerCase()));
		dMarginAmountFrom = Double.valueOf((String)map.get("dMarginAmountFrom".toLowerCase()));
		dMarginAmountTo = Double.valueOf((String)map.get("dMarginAmountTo".toLowerCase()));
		dMarginRate = Double.valueOf((String)map.get("dMarginRate".toLowerCase()));
		tsMarginEndDate = toTimestamp(map.get("tsMarginEndDate".toLowerCase()));
		
		if(map.get("unit".toLowerCase())!=null){
			unit = Long.valueOf((String)map.get("unit".toLowerCase()));
		}
		ControlSource = (String)map.get("ControlSource".toLowerCase());
		
		QueryFixedDepositInfo info = new QueryFixedDepositInfo();
		//
		info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setAccountNoFrom(strAccountNoFrom);
		info.setAccountNoTo(strAccountNoTo);
		//add by 2012-05-18 添加指定账户编号
		info.setAppointAccountNo(strAccountCodes);
		info.setClientNameFrom(strClientNameFrom);
		info.setClientNameTo(strClientNameTo);
		info.setClientNoFrom(strClientNoFrom);
		info.setClientNoTo(strClientNoTo);
		info.setClientSort(lClientSort);
		info.setClientType(lClientType);
		info.setClientManager(lClientManager);
		info.setAccountType(lAccountType);
		info.setDepositOrgin(lDepositOrgin);
		info.setFixedAmountFrom(dFixedAmountFrom);
		info.setFixedAmountTo(dFixedAmountTo);
		info.setFixedDepositNoFrom(strFixedDepositNoFrom);
		info.setFixedDepositNoTo(strFixedDepositNoTo);
		info.setFixedDepositStatus(lFixedDepositStatus);
		info.setFixedEndDate(tsFixedEndDate);
		info.setFixedEndDateFrom(tsFixedEndDateFrom);
		info.setFixedEndDateTo(tsFixedEndDateTo);
		info.setFixedRate(dFixedRate);
		info.setFixedStartDateFrom(tsFixedStartDateFrom);
		info.setFixedStartDateTo(tsFixedStartDateTo);
		info.setIsFixedDeposit(lIsFixedDeposit);
		info.setIsLeaching(lIsLeaching);
		info.setIsNoticeDeposit(lIsNoticeDeposit);
		info.setNoticeBalanceFrom(dNoticeBalanceFrom);
		info.setNoticeBalanceTo(dNoticeBalanceTo);
		info.setNoticeDepositNoFrom(strNoticeDepositNoFrom);
		info.setNoticeDepositNoTo(strNoticeDepositNoTo);
		info.setNoticeDepositStatus(lNoticeDepositStatus);
		info.setNoticeDrawAmountFrom(dNoticeDrawAmountFrom);
		info.setNoticeDrawAmountTo(dNoticeDrawAmountTo);
		info.setNoticeEndDate(tsNoticeEndDate);
		info.setNoticeStartDateFrom(tsNoticeStartDateFrom);
		info.setNoticeStartDateTo(tsNoticeStartDateTo);
		info.setOrderField(lOrderField);
		info.setParentCompany1(lParentCompany1);
		info.setParentCompany2(lParentCompany2);
		info.setFixedDepositTermFrom(lFixedDepositTermFrom);
		info.setFixedDepositTermTo(lFixedDepositTermTo);
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
		info.setNoticeDays(lNiticeDays);

		//2006.3.27 feiye
		info.setDepositNoChoose(lDepositNoChoose);

		// 保证金存款查询
		info.setIsMarginDeposit(lIsMarginDeposit);	// 是否查询保证金存款
		info.setMarginDepositNoFrom(strMarginDepositNoFrom);	// 保证金存款单据号
		info.setMarginDepositNoTo(strMarginDepositNoTo);
		info.setMarginStartDateFrom(tsMarginStartDateFrom);	// 起始日期
		info.setMarginStartDateTo(tsMarginStartDateTo);		
		info.setMarginEndDateFrom(tsMarginEndDateFrom);	//到期日期
		info.setMarginEndDateTo(tsMarginEndDateTo);
		info.setMarginDepositStatus(lMarginDepositStatus);	// 保证金存款状态
		info.setMarginAmountFrom(dMarginAmountFrom);	// 保证金存款金额
		info.setMarginAmountTo(dMarginAmountTo);
		info.setMarginRate(dMarginRate);	// 利率
		info.setMarginEndDate(tsMarginEndDate);	// 定期截止日期

		//add by mzh 2007-3-1
		info.setNoticeBalanceFrom(dNoticeBalanceFrom);
		info.setNoticeBalanceTo(dNoticeBalanceTo);
		
		info.setUnit(unit);
		info.setControlSource(ControlSource);
		
		pagerInfo = biz.queryFixedDepositInfo(info);
		return pagerInfo;
		
	}
	
	public static Timestamp toTimestamp(Object object){
		Timestamp timestamp = null;
		if(!object.equals("null")){
			timestamp = Timestamp.valueOf((String)object);
		}
		return timestamp;
	}
}
