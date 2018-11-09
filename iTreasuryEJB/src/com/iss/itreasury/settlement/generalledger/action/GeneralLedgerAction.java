package com.iss.itreasury.settlement.generalledger.action;

import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerBiz;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerCheckBiz;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerUnCheckBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import java.util.Map;

public class GeneralLedgerAction
{
  GeneralLedgerBiz biz = new GeneralLedgerBiz();

  public PagerInfo queryGenerlLegerInfo(Map map) throws Exception
  {
    PagerInfo pagerInfo = null;
    try
    {
      long OfficeID = Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
      long CurrencyID = Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
      
      //20121112 add BUG #15660 结算—设置—基础设置—会计分录设置，添加“业务类型”查询条件
      long transactionType = Long.parseLong(map.get("TransactionType".toLowerCase()).toString());

      pagerInfo = this.biz.queryGeneralLedger(OfficeID, CurrencyID ,transactionType);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage(), e);
    }
    return pagerInfo;
  }

  
  public PagerInfo queryAllUnUseAndUsedGenerlLegerInfo(Map map) throws Exception
  {
    PagerInfo pagerInfo = null;
    try
    {
      long OfficeID = Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
      long CurrencyID = Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
      
      //20121112 add BUG #15660 结算—设置—基础设置—会计分录设置，添加“业务类型”查询条件
      long transactionType = Long.parseLong(map.get("TransactionType".toLowerCase()).toString());

      pagerInfo = this.biz.queryAllUnUseAndUsedGenerlLegerInfo(OfficeID, CurrencyID ,transactionType);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage(), e);
    }
    return pagerInfo;
  }
  
  public PagerInfo queryGenerlLegerUnCheckInfo(Map map) throws Exception
  {
	 GeneralLedgerUnCheckBiz biz1=new GeneralLedgerUnCheckBiz();
    PagerInfo pagerInfo = null;
    try
    {
      long OfficeID = Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
      long CurrencyID = Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
      String nStatusID1 = map.get("nStatusID".toLowerCase()).toString();
      long nStatusID = Long.parseLong(nStatusID1);

      pagerInfo = biz1.queryGeneralLedger(nStatusID1, OfficeID, CurrencyID);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage(), e);
    }
    return pagerInfo;
  }

  public PagerInfo queryGenerlLegerCheckInfo(Map map)
    throws Exception
  {
	 GeneralLedgerCheckBiz biz2=new GeneralLedgerCheckBiz();
    PagerInfo pagerInfo = null;
    try
    {
      long OfficeID = Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
      long CurrencyID = Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
      String nStatusID1 = map.get("nStatusID".toLowerCase()).toString();
      long nStatusID = Long.parseLong(nStatusID1);

      pagerInfo = biz2.queryGeneralLedger(nStatusID, OfficeID, CurrencyID);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage(), e);
    }
    return pagerInfo;
  }
}