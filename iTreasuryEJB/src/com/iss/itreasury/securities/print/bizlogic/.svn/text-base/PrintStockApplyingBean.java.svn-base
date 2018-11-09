/*
 * Created on 2004-5-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintStockApplyingParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Env;

/**
 * @author huanwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class PrintStockApplyingBean {

    protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

    private StringBuffer sbSelect = null;

    private StringBuffer sbFrom = null;

    private StringBuffer sbWhere = null;

    private void getSQL(PrintStockApplyingParam printParam) {
        sbSelect = new StringBuffer();
        sbFrom = new StringBuffer();
        sbWhere = new StringBuffer();
        sbWhere.append(" ");
        sbSelect.append("   *  \n");
        //开始的左括号
        
        
        sbFrom.append(" ( \n");
        
        sbFrom.append(" select g.SECURITIESCODE1 SECURITIESCODE, \n");//3
        
        sbFrom.append(" g.SHORTNAME SECURITIESNAME, \n");//4
        
        sbFrom
                .append(" f.ApplyID,f.CLIENTNAME,f.HOLDERACCOUNTCODE,f.HOLDERACCOUNTNAME,f.TRANSACTIONTYPENAME, \n");//5
        sbFrom
                .append(" f.COUNTERPARTNAME,f.AccountID,f.ApplyCode,f.ApplyDate,f.QuantityForApply,f.PriceForApply, \n");//6
        sbFrom
                .append(" f.ConfirmDate,f.QuantityForConfirm,f.PriceForConfirm  \n");//7
        
        sbFrom.append(" from (select \n");//8
        
        sbFrom
                .append(" d.TRANSACTIONTYPENAME,d.ApplyID,d.ApplyCode,d.AccountID,d.SECURITIESID,d.ApplyDate,d.QuantityForApply, \n");//9
        sbFrom
                .append(" d.PriceForApply,d.ConfirmDate,d.QuantityForConfirm,d.PriceForConfirm,e.CLIENTNAME, \n");//10
        sbFrom
                .append(" e.HOLDERACCOUNTCODE,e.HOLDERACCOUNTNAME,e.COUNTERPARTNAME \n");//11
        
        sbFrom.append(" from(select c.Name TRANSACTIONTYPENAME,a.ID  \n");//12
        
        sbFrom
                .append(" ApplyID,a.code ApplyCode,a.AccountID,a.SECURITIESID,a.TRANSACTIONDATE  ApplyDate, \n");//13
        sbFrom
                .append(" a.Quantity QuantityForApply,a.Price PriceForApply,b.TransactionDate ConfirmDate,b.Quantity \n");//14
        
        sbFrom.append(" QuantityForConfirm, \n");//15
        
        sbFrom.append(" b.Price PriceForConfirm \n");//16

        sbFrom
                .append(" from SEC_DELIVERYORDER a,sec_noticeform,(select TransactionDate,Quantity,Price,RELATEDDELIVERYORDERID \n");//16

        sbFrom.append(" from SEC_DELIVERYORDER \n");//16

        sbFrom
                .append(" where TRANSACTIONTYPEID in(1602,1605,1704,1708) and statusID>=3)b,SEC_TransactionType c \n");//16

        sbFrom.append(" where b.RELATEDDELIVERYORDERID(+)=a.ID  \n");//16
		     
		sbFrom.append(" and a.id = sec_noticeform.deliveryorderid(+) ");
		sbFrom.append(" and ( (decode(a.TRANSACTIONTYPEID,1601,1,1604,1,0)=1 and a.statusID>=3)  \n");
		sbFrom.append(" or (decode(a.TRANSACTIONTYPEID,1701,1,1705,1,0)=1 \n");
		sbFrom.append(" and sec_noticeform.statusid="+SECConstant.NoticeFormStatus.COMPLETED + ") )\n");
		
        //		条件一：申购录入日期开始日

        Timestamp applyInputDateStart = printParam.getApplyInputDateStart();
        if (applyInputDateStart != null) {
            String strApplyInputDateStart = DataFormat
                    .getDateString(applyInputDateStart);
            sbFrom.append(" and \n");
            sbFrom.append(" a.TRANSACTIONDATE >= to_Date('"
                    + strApplyInputDateStart + "','yyyy-mm-dd') \n");
        }
        //条件二：申购录入日期开始日
        Timestamp applyInputDateEnd = printParam.getApplyInputDateEnd();
        if (applyInputDateEnd != null) {
            String strApplyInputDateEnd = DataFormat
                    .getDateString(applyInputDateEnd);
            sbFrom.append(" and \n");
            sbFrom.append(" a.TRANSACTIONDATE <= to_Date('"
                    + strApplyInputDateEnd + "','yyyy-mm-dd') \n");
        }
        
    	//条件：申购交割单录入日期
		Timestamp deliveryOrderInputDateStart = printParam.getDeliveryOrderInputDateStart();
		String strDeliveryOrderInputDateStart = DataFormat.getDateString(deliveryOrderInputDateStart);
		
		Timestamp deliveryOrderInputDateEnd = printParam.getDeliveryOrderInputDateEnd();
		String strDeliveryOrderInputDateEnd = DataFormat.getDateString(deliveryOrderInputDateEnd);
        //申购交割单录入日期校验
		if (!"".equals(strDeliveryOrderInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(a.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strDeliveryOrderInputDateStart + "'");
		}
		 
		if (!"".equals(strDeliveryOrderInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(a.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strDeliveryOrderInputDateEnd + "'");
		}
		

        //		条件三：交易类型
        String[] transactionTypeIDs = printParam.getTransactionTypeIDs();
        if (transactionTypeIDs != null && transactionTypeIDs.length > 0) {
            sbFrom.append(" and a.TRANSACTIONTYPEID in ( ");
            for (int i = 0; i < transactionTypeIDs.length - 1; i++) {
                sbFrom.append(Long.parseLong(transactionTypeIDs[i]) + ",");
            }
            sbFrom.append(Long.parseLong(transactionTypeIDs[transactionTypeIDs.length - 1])
                    + ") \n");
        }
        else
        {
            sbFrom.append(" and a.TRANSACTIONTYPEID in (1601,1604,1701,1705) \n ");
        }
        //		条件四：资金账号
        String[] accountIDs = printParam.getAccountIDs();
        if (accountIDs != null && accountIDs.length > 0) {
            sbFrom.append(" and a.AccountID in ( ");
            for (int i = 0; i < accountIDs.length - 1; i++) {
                sbFrom.append(Long.parseLong(accountIDs[i]) + ",");
            }
            sbFrom.append(Long.parseLong(accountIDs[accountIDs.length - 1])
                    + ") \n");
        }
        
        sbFrom.append(" and a.TRANSACTIONTYPEID=c.ID \n");//16
        
        sbFrom.append(" )d,SEC_VACCOUNTMAGNIFIER  e \n");//16
        
        sbFrom.append(" where d.AccountID=e.ID \n");//16
	//      条件五：业务单位名称
        if(printParam.getClientName()!=null)
        sbFrom.append(" and e.CLIENTNAME = '"+printParam.getClientName()+"'"+" \n");//16
        
        //条件六：股东账户名称
        String[] holderAccountNames = printParam.getHolderAccountNames();
        if (holderAccountNames != null && holderAccountNames.length > 0) {
            sbFrom.append(" and e.HOLDERACCOUNTNAME in ('");
            for (int i = 0; i < holderAccountNames.length - 1; i++) {
                sbFrom.append(holderAccountNames[i] + "','");
            }
            sbFrom.append(holderAccountNames[holderAccountNames.length - 1] + "') \n");
        }
	//      条件七：开户营业部名称
        if(printParam.getCounterpartName()!=null)
        sbFrom.append(" and e.COUNTERPARTNAME = '"+printParam.getCounterpartName()+"'"+" \n");//16
        //结束的右括号
        sbFrom.append(" )f,SEC_SECURITIES g \n");
        
        sbFrom.append(" where f.SECURITIESID=g.ID \n");
        
	//      条件八：证券名称
        String[] securitiesNames = printParam.getSecuritiesNames();
        if (securitiesNames != null && securitiesNames.length > 0) {
            sbFrom.append(" and  g.SHORTNAME in ('");
            for (int i = 0; i < securitiesNames.length - 1; i++) {
                sbFrom.append(securitiesNames[i] + "','");
            }
            sbFrom.append(securitiesNames[securitiesNames.length - 1] + "') \n");
        }

        
        
	//      结束的右括号
        sbFrom.append(" ) \n");
    }
	private void getCNMEFSQL(PrintStockApplyingParam printParam) {
		  sbSelect = new StringBuffer();
		  sbFrom = new StringBuffer();
		  sbWhere = new StringBuffer();
		  sbWhere.append(" ");
		  sbSelect.append("   *  \n");
		  //开始的左括号
        
        
		  sbFrom.append(" ( \n");
        
		  sbFrom.append(" select g.SECURITIESCODE1 SECURITIESCODE, \n");//3
        
		  sbFrom.append(" g.SHORTNAME SECURITIESNAME, \n");//4
        
		  sbFrom
				  .append(" f.ApplyID,f.CLIENTNAME,f.HOLDERACCOUNTCODE,f.HOLDERACCOUNTNAME,f.TRANSACTIONTYPENAME, \n");//5
		  sbFrom
				  .append(" f.COUNTERPARTNAME,f.AccountID,f.ApplyCode,f.ApplyDate,f.QuantityForApply,f.PriceForApply,f.amountForApply, \n");//6
		  sbFrom
				  .append(" f.ConfirmDate,f.QuantityForConfirm,f.PriceForConfirm  \n");//7
				  
		  sbFrom.append(" ,f.NetIncomeForApply,f.RateForApply, \n");
		  sbFrom.append(" f.sellQuantity quantityForSell,f.sellPrice  priceForSell,f.sellAmount amountForSell, f.closePrice  closePrice,f.cost cost \n");        		  
		  
		  sbFrom.append(" from (select \n");//8
        
		  sbFrom
				  .append(" d.TRANSACTIONTYPENAME,d.ApplyID,d.ApplyCode,d.AccountID,d.SECURITIESID,d.ApplyDate,d.QuantityForApply,d.amountForApply, \n");//9
		  sbFrom
				  .append(" d.PriceForApply,d.ConfirmDate,d.QuantityForConfirm,d.PriceForConfirm,e.CLIENTNAME, \n");//10
		  sbFrom
				  .append(" e.HOLDERACCOUNTCODE,e.HOLDERACCOUNTNAME,e.COUNTERPARTNAME \n");//11
				  
		  sbFrom.append(",d.NetIncomeForApply,d.RateForApply, \n");
		  sbFrom.append(" d.sellQuantity,d.sellPrice,d.sellAmount, d.closePrice closePrice,d.cost \n");		  
        
		  sbFrom.append(" from(select c.Name TRANSACTIONTYPENAME,a.ID  \n");//12
        
		  sbFrom
				  .append(" ApplyID,a.code ApplyCode,a.AccountID,a.SECURITIESID,a.TRANSACTIONDATE  ApplyDate, \n");//13
		  sbFrom
				  .append(" a.Quantity QuantityForApply,a.Price PriceForApply,a.amount amountForApply,b.TransactionDate ConfirmDate,b.Quantity \n");//14
        
		  sbFrom.append(" QuantityForConfirm, \n");//15
        
		  sbFrom.append(" b.Price PriceForConfirm \n");//16
		  
		  sbFrom.append(",a.netincome NetIncomeForApply,a.rate RateForApply, \n");
		  sbFrom.append(" sell.sellQuantity sellQuantity,sell.sellPrice sellPrice,sell.sellAmount sellAmount  ,m.closePrice closePrice ,sec_dailystock.cost \n");

		  sbFrom
				  .append(" from SEC_DELIVERYORDER a ,(select TransactionDate,Quantity,Price,accountid,securitiesid \n");//16

		  sbFrom.append(" from SEC_DELIVERYORDER \n");//16

		  sbFrom
				  .append(" where TRANSACTIONTYPEID in(1602,1605,1608,1610,1704,1708) and statusID>=3)b,SEC_TransactionType c , \n");//16
		  
		  sbFrom.append(" ( select sum(quantity) sellQuantity,sum(amount)/sum(quantity) sellPrice,sum(amount) sellAmount,accountid,securitiesid \n");
		  sbFrom.append(" from sec_deliveryorder");
		  sbFrom.append("  where transactiontypeid in(1607,1609,1709,1710) and statusid >=3 \n");
		  sbFrom.append("   group by accountid,securitiesid  ) sell \n");
		  sbFrom.append("   ,sec_securitiesmarket m,sec_securities n ,sec_dailystock \n");		  

		  sbFrom.append(" where a.accountid = b.accountid(+) and a.securitiesid = b.securitiesid(+) \n");//16
		  sbFrom.append("   and a.accountid = sell.accountid(+) and a.securitiesid = sell.securitiesid(+)	 \n");
		     
		  //sbFrom.append(" and a.id = sec_noticeform.deliveryorderid(+) ");
		  sbFrom.append(" and ( (decode(a.TRANSACTIONTYPEID,1601,1,1604,1,0)=1 and a.statusID>=3)  \n");
		  sbFrom.append(" or (decode(a.TRANSACTIONTYPEID,1701,1,1705,1,0)=1 \n");
		  sbFrom.append(" ) ) \n");
		  sbFrom.append(" and a.securitiesid = n.id(+) and n.securitiescode1=m.securitiescode(+) \n");
		  
		  sbFrom.append(" and a.clientid = sec_dailystock.clientid(+) \n");
		  sbFrom.append(" and a.securitiesid =sec_dailystock.securitiesid(+) \n");
		  sbFrom.append(" and a.accountid = sec_dailystock.accountid(+) \n");
		  sbFrom.append(" and sec_dailystock.stockdate(+) = to_Date('"+DataFormat.getDateString(printParam.getApplyInputDateEnd())+ "','yyyy-mm-dd') \n");
		  
		  sbFrom.append(" and m.closedate(+) = to_Date('"+DataFormat.getDateString(printParam.getApplyInputDateEnd())+ "','yyyy-mm-dd') \n");
		
		  // 条件一：申购录入日期开始日

		  Timestamp applyInputDateStart = printParam.getApplyInputDateStart();
		  if (applyInputDateStart != null) {
			  String strApplyInputDateStart = DataFormat
					  .getDateString(applyInputDateStart);
			  sbFrom.append(" and \n");
			  sbFrom.append(" a.TRANSACTIONDATE >= to_Date('"
					  + strApplyInputDateStart + "','yyyy-mm-dd') \n");
		  }
		  //条件二：申购录入日期开始日
		  Timestamp applyInputDateEnd = printParam.getApplyInputDateEnd();
		  if (applyInputDateEnd != null) {
			  String strApplyInputDateEnd = DataFormat
					  .getDateString(applyInputDateEnd);
			  sbFrom.append(" and \n");
			  sbFrom.append(" a.TRANSACTIONDATE <= to_Date('"
					  + strApplyInputDateEnd + "','yyyy-mm-dd') \n");
		  }

		  // 条件三：交易类型
		  String[] transactionTypeIDs = printParam.getTransactionTypeIDs();
		  if (transactionTypeIDs != null && transactionTypeIDs.length > 0) {
			  sbFrom.append(" and a.TRANSACTIONTYPEID in ( ");
			  for (int i = 0; i < transactionTypeIDs.length - 1; i++) {
				  sbFrom.append(Long.parseLong(transactionTypeIDs[i]) + ",");
			  }
			  sbFrom.append(Long.parseLong(transactionTypeIDs[transactionTypeIDs.length - 1])
					  + ") \n");
		  }
		  else
		  {
			  sbFrom.append(" and a.TRANSACTIONTYPEID in (1601,1604,1701,1705) \n ");
		  }
		  // 条件四：资金账号
		  String[] accountIDs = printParam.getAccountIDs();
		  if (accountIDs != null && accountIDs.length > 0) {
			  sbFrom.append(" and a.AccountID in ( ");
			  for (int i = 0; i < accountIDs.length - 1; i++) {
				  sbFrom.append(Long.parseLong(accountIDs[i]) + ",");
			  }
			  sbFrom.append(Long.parseLong(accountIDs[accountIDs.length - 1])
					  + ") \n");
		  }
        
		  sbFrom.append(" and a.TRANSACTIONTYPEID=c.ID  order by a.transactiondate  \n");//16
        
		  sbFrom.append(" )d,SEC_VACCOUNTMAGNIFIER  e \n");//16
        
		  sbFrom.append(" where d.AccountID=e.ID \n");//16
		// 条件五：业务单位名称
		  if(printParam.getClientName()!=null)
		  sbFrom.append(" and e.CLIENTNAME = '"+printParam.getClientName()+"'"+" \n");//16
        
		//条件六：股东账户名称
		  String[] holderAccountNames = printParam.getHolderAccountNames();
		  if (holderAccountNames != null && holderAccountNames.length > 0) {
			  sbFrom.append(" and e.HOLDERACCOUNTNAME in ('");
			  for (int i = 0; i < holderAccountNames.length - 1; i++) {
				  sbFrom.append(holderAccountNames[i] + "','");
			  }
			  sbFrom.append(holderAccountNames[holderAccountNames.length - 1] + "') \n");
		  }
		//条件七：开户营业部名称
		  if(printParam.getCounterpartName()!=null)
		  sbFrom.append(" and e.COUNTERPARTNAME = '"+printParam.getCounterpartName()+"'"+" \n");//16
		  //结束的右括号
		  sbFrom.append(" )f,SEC_SECURITIES g \n");
        
		  sbFrom.append(" where f.SECURITIESID=g.ID \n");
        
		//条件八：证券名称
		  String[] securitiesNames = printParam.getSecuritiesNames();
		  if (securitiesNames != null && securitiesNames.length > 0) {
			  sbFrom.append(" and  g.SHORTNAME in ('");
			  for (int i = 0; i < securitiesNames.length - 1; i++) {
				  sbFrom.append(securitiesNames[i] + "','");
			  }
			  sbFrom.append(securitiesNames[securitiesNames.length - 1] + "') \n");
		  }

        
        
		//结束的右括号
		  sbFrom.append(" ) \n");
	  }
    public PageLoader PrintStockApplying(PrintStockApplyingParam printParam)
            throws SecuritiesException {

        //if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
        //{
		//	System.out.println("项目名称---国机");
		//	getCNMEFSQL(printParam);
        //}
        //else
        //{
			System.out.println("项目名称---");
			getSQL(printParam);
        //}
        //
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
                .getBaseObject("com.iss.system.dao.PageLoader");

        //log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
        pageLoader
                .initPageLoader(
                        new AppContext(),
                        sbFrom.toString(),
                        sbSelect.toString(),
                        sbWhere.toString(),
                        (int) Constant.PageControl.CODE_PAGELINECOUNT,
                        "com.iss.itreasury.securities.print.dataentity.PrintStockApplyingInfo",
                        null);
		pageLoader.setOrderBy("  ");
        return pageLoader;
    }
}
