/*
 * Created on 2004-08-20
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesListAccountParam;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-08-20
 *  Date of Modify      2004-12-30
 *  Modifier            Jason Fang
 */
public class PrintSecuritiesListAccountBean 
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	private StringBuffer sbSelect	       = null;
	private StringBuffer sbFrom		       = null;
	private StringBuffer sbWhere	       = null;
	private StringBuffer sbSecSubject	   = null;
	private StringBuffer sbSecuritiesMenu  = null;

	/**
	 * 返回查询明细账表的SQL语句
	 * @param  parameter
	 * @return void
	 * @exception nothing
	 */
	private void initialSql(PrintSecuritiesListAccountParam parameter) 
	{
		//查询日期开始日的前一天
		
		String preQueryDateStart = DataFormat.formatDate( IDate.before(parameter.getNoticeInputDateStart(),1),1 );
		
		
		//查询条件
        //条件1.证券菜单
		/**
		sbSecuritiesMenu = new StringBuffer();
		
		sbSecuritiesMenu.append(" and substr(t.ID,0,2) in ( ");
		switch( (int)parameter.getSecuritiesMenuId() )
		{
			case 1://股票
				sbSecuritiesMenu.append("16,17,18");
				break;
			case 5://封闭式基金
				sbSecuritiesMenu.append("56,57,58");
				break;
			case 3://企业债
				sbSecuritiesMenu.append("46,47");
				break;
			case 4://可转债
				sbSecuritiesMenu.append("51,52,53");
				break;
			case 2://债转股
				sbSecuritiesMenu.append("54");
				break;
			case 7://交易所国债
				sbSecuritiesMenu.append("33,34");
				break;
			case 8://交易所债券回购
				sbSecuritiesMenu.append("26");
				break;
			case 10://银行间国债
				sbSecuritiesMenu.append("31,32");
				break;
			case 12://央行票据
				sbSecuritiesMenu.append("21,22");
				break;
			case 14://金融债
				sbSecuritiesMenu.append("36,37");
				break;
			case 13://政策性金融债
				sbSecuritiesMenu.append("41,42");
				break;
			case 11://银行间债券回购
				sbSecuritiesMenu.append("26");
				break;
			case 6://开放式基金
				sbSecuritiesMenu.append("61,62,63,64");
				break;
			case 9://资产回购
				sbSecuritiesMenu.append("71");
				break;
			case 15://委托理财
				sbSecuritiesMenu.append("73");
				break;
			case 16://结构性投资
				sbSecuritiesMenu.append("77");
				break;
			case 17://债券承销
				sbSecuritiesMenu.append("81");
				break;
		}
		sbSecuritiesMenu.append(" ) ");
		**/
		
		//条件2.二级科目（交易类型）
		String[] transactionTypeIDs = parameter.getTransactionTypeIDs();
		
		sbSecSubject	            = new StringBuffer();
		
		if (transactionTypeIDs != null && transactionTypeIDs.length > 0)
		{
			sbSecSubject.append(" and b.TRANSACTIONTYPEID in ( ");
            for (int i = 0; i <= transactionTypeIDs.length - 1; i++)
            {
            	switch((int) Long.parseLong(transactionTypeIDs[i]))
				{
            		case 1:
            			sbSecSubject.append("1601,1602,1603,1604,1605,1606," +
            					"1701,1702,1703,1704,1705,1706,1707,1708," +
            					"1801,1802,1803,1804,1805,1806");//股票
            			break;
            		case 2:
            			sbSecSubject.append("5601,5602,5603," +
            					"5701,5702,5703,5704," +
            					"5801,5802,5803,5804," +
            					"6101,6102," +
            					"6201,6202," +
            					"6301,6401,6402");//基金
            			break;
            		//case 3:
            		//	sbSecSubject.append("61,62,63,64");//开放式基金
            		//	break;
            		case 4:
            			sbSecSubject.append("4601,4602,4603,4604," +
            					"4701,4702,4703,4704," +
            					"5101,5102,5103," +
            					"5201,5202,5203,5204," +
            					"5301,5302,5303,5304," +
            					"5401");//债券
            			break;
            		//case 5:
            		//	sbSecSubject.append("51,52,53,54");//可转债(包括债转股)
            		//	break;
            		case 6:
            			sbSecSubject.append("2101," +
            					"2201,2202,2203,2204");//央行票据
            			break;
            		case 7:
            			sbSecSubject.append("3601," +
            					"3701,3702,3703,3704");//金融债
            			break;
            		case 8:
            			sbSecSubject.append("4101," +
            					"4201,4201,4201,4204");//政策性金融债
            			break;
            		case 9:
            			sbSecSubject.append("3101," +
            					"3201,3202,3203,3204," +
            					"3301," +
            					"3401,3402,3403,3404");//国债
            			break;
            		//case 10:
            		//	sbSecSubject.append("33,34");//交易所国债
            		//	break;
            		//case 11:
            		//	sbSecSubject.append("26");//银行间债券回购
            		//	break;
            		//case 12:
            		//	sbSecSubject.append("27");//交易所债券回购
            		//	break;
            		case 13:
            			sbSecSubject.append("7301,7302,7303,7304,7305,7306");//委托理财
            			break;
            		case 14:
            			sbSecSubject.append("2603,2604," +
            					"2703,2704");//债券回购
            			break;
            		case 15:
            			sbSecSubject.append("71");//信贷资产回购???
            			break;
            		case 16:
            			sbSecSubject.append("2601,2602" +
            					",2701,2702");//卖出回购证券款
            			break;
            		case 17:
            			sbSecSubject.append("7101,7102,7103,7104,7105");//卖出回购信贷资产款
            			break;
            		case 18:
            			sbSecSubject.append("7701,7702,7703,7704");//结构性投资
            			break;
            			
            	}
            	
            	 sbSecSubject.append(" , ");
            	
            }
            sbSecSubject.append(" 0000) \n");
            
          
        }
		
		//SELECT
		
		sbSelect	= new StringBuffer();
		
		sbSelect.append("	subjectTypeId, \n");
		sbSelect.append("	subjectName, \n");
		sbSelect.append("	nvl(capitaldirection,-1)  capitaldirection, \n");
		sbSelect.append("	securitiesId, \n");
		sbSelect.append("	businessType, \n");
		sbSelect.append("	securitiesTypeId, \n");
		sbSelect.append("	deliverycode, \n");
		sbSelect.append("	deliverydate, \n");
		sbSelect.append("	securitiesName, \n");
		sbSelect.append("	counterPartId, \n");
		sbSelect.append("	startDebitBalance, \n");
		sbSelect.append("	startCreditBalance, \n");
		sbSelect.append("	debitAmount, \n");
		sbSelect.append("	creditAmount, \n");
		sbSelect.append("	endDebitBalance, \n");
		sbSelect.append("	endCreditBalance, \n");
		sbSelect.append("	remark, \n");
		sbSelect.append("	sitetype, \n");
		sbSelect.append("	svoucherno \n"); 
		
		
		//FROM
		
		sbFrom		= new StringBuffer();
		
		
		sbFrom.append("(   \n");
		
		sbFrom.append("-------**拼写第一个表：自营证券和短期投资,主表是cc**-  \n");
		
		sbFrom.append(" (  \n");
		sbFrom.append("   SELECT  \n");
		sbFrom.append("	aa.subjectTypeId  subjectTypeId, \n");
		sbFrom.append("	aa.subjectName  subjectName, \n");
		sbFrom.append("	cc.capitaldirection  capitaldirection, \n");
		sbFrom.append("	nvl(aa.securitiesId,-1) securitiesId, \n");
		sbFrom.append("	nvl(aa.businessType,-1) businessType, \n");
		sbFrom.append("	nvl(aa.securitiesTypeId,-1) securitiesTypeId, \n");
		sbFrom.append("	cc.code deliverycode, \n");
		sbFrom.append("	cc.deliverydate deliverydate, \n");
		sbFrom.append("	nvl(aa.shortName,'') securitiesName, \n");
		sbFrom.append("	nvl(aa.counterpartId,-1) counterPartId, \n");
		sbFrom.append("	nvl(bb.debitAmount,0.00) startDebitBalance, \n");
		sbFrom.append("	nvl(bb.creditAmount,0.00) startCreditBalance, \n");
		sbFrom.append("	nvl(cc.debitAmount,0.00) debitAmount, \n");
		sbFrom.append("	nvl(cc.creditAmount,0.00) creditAmount, \n");
		sbFrom.append("	nvl(dd.debitAmount,0.00) endDebitBalance, \n");
		sbFrom.append("	nvl(dd.creditAmount,0.00) endCreditBalance, \n");
		sbFrom.append("	cc.remark remark, \n");
		sbFrom.append("	-1 sitetype, \n");
		sbFrom.append("	cc.svoucherno svoucherno \n");
		
		sbFrom.append("	FROM \n");
		sbFrom.append("\n	( SELECT DISTINCT a.id securitiesId, substr(min(b.transactiontypeid),0,2)  businessType,a.typeid securitiesTypeId, a.shortName ,  \n");
		sbFrom.append("           c.counterpartID,min(t.capitaldirection) capitaldirection ,st.id subjectTypeId,st.name subjectName ,c.id accountid,c.clientid clientid  \n");
		
		sbFrom.append("	  FROM sec_Securities a, sec_DeliveryOrder b, sec_Account c ,sec_transactiontype t ,sec_subjecttype st \n");
		
		sbFrom.append("	  WHERE a.ID = b.securitiesID \n");
		sbFrom.append("		AND b.accountID = c.ID \n");
		sbFrom.append("		AND st.id in(1,2) \n");
		sbFrom.append("		AND b.transactiontypeid = t.id \n");
		sbFrom.append("		AND t.subjecttype = st.id \n");
		sbFrom.append("		AND t.capitaldirection <> 0\n");
		sbFrom.append("		AND a.statusID = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND a.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND b.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND b.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND b.statusID >= "+SECConstant.DeliveryOrderStatus.CHECKED+" \n");
		sbFrom.append("		AND c.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND c.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND c.statusID = "+SECConstant.AccountStatus.CHECKED+" \n");
		
		
		/**
		if (parameter.getSecuritiesMenuId() > -1) {
			sbFrom.append(sbSecuritiesMenu.toString());
		}
		if (parameter.getSecuritiesId() != null && !"".equals(parameter.getSecuritiesId())) {
			sbFrom.append("		AND a.ID IN ("+parameter.getSecuritiesId()+") \n");
		}
		**/
		if (parameter.getCounterPartId() != null && !"".equals(parameter.getCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getCounterPartId()+") \n");
		}
		if (parameter.getBourseCounterPartId() != null && !"".equals(parameter.getBourseCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getBourseCounterPartId()+") \n");
		}
		//二级科目
		
		sbFrom.append(sbSecSubject.toString());
		
		sbFrom.append(" group by   st.id,st.name,a.id,a.shortName,c.counterpartID ,a.typeid,c.id ,c.clientid \n");
		
		sbFrom.append("	) aa, \n");
		
		
		sbFrom.append("	(  select s.id securitiesId,s.shortname securitiesName, \n");
		sbFrom.append("		ds.cost debitAmount,0  creditAmount,ds.clientid clientid,ds.accountid accountid  \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		 sec_dailystock ds ,sec_securities s \n");
		sbFrom.append("		WHERE   ds.securitiesid = s.id \n");
		sbFrom.append("			AND s.statusid   = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND s.currencyid = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			AND ds.stockdate = TO_DATE('"+preQueryDateStart+"', 'YYYY-MM-DD') \n"); 
		
		sbFrom.append("	) bb, \n");
		
		sbFrom.append("	(SELECT  distinct  d.code code ,d.remark remark, \n");
		sbFrom.append("	        d.counterpartid,s.typeid,s.id securitiesId,s.shortName, \n");
		sbFrom.append("		    DECODE(t.capitaldirection, "+SECConstant.Direction.RECEIVE+", nvl(d.amount,0.0) - nvl(pl.profitLoss,0.0), "+
			                           SECConstant.Direction.FINANCE_RECEIVE+", nvl(d.amount,0.0) - nvl(pl.profitLoss,0.0), \n");
		sbFrom.append(	               SECConstant.Direction.PAY_AND_RECEIVE+", nvl(d.amount,0.0) - nvl(pl.profitLoss,0.0) )  creditAmount, \n");
		sbFrom.append("		    DECODE(t.capitaldirection, "+SECConstant.Direction.PAY+", d.amount, "+
			                           SECConstant.Direction.FINANCE_PAY+", d.amount, \n");
		sbFrom.append(                 SECConstant.Direction.PAY_AND_RECEIVE+", d.amount) debitAmount\n");
		sbFrom.append("	          ,t.capitaldirection capitaldirection,d.deliveryDate deliverydate  \n");
		sbFrom.append("	          ,d.clientid,d.accountid,v.SVOUCHERNO SVOUCHERNO ,pl.profitLoss  \n");
		
		sbFrom.append("	FROM \n");
		sbFrom.append("	  sec_deliveryorder d,sec_transactiontype t,sec_securities s,sec_subjecttype st  \n");
		//拼写凭证号子表
		sbFrom.append("	  ,( \n");
		sbFrom.append("	   		SELECT distinct a.id,c.svoucherno  \n");
		sbFrom.append("	   		FROM sec_deliveryorder a,sec_glentry b ,SETT_GLORACLEVOUCHER c  \n");
		sbFrom.append("	    	WHERE   \n");
		sbFrom.append("	  			a.code = c.stransactionno  \n");
		sbFrom.append("	  			AND a.transactiontypeid = b.transactiontypeid  \n");
		sbFrom.append("	  			AND b.executedate = c.dtgldate  \n");
		sbFrom.append("	  			AND b.statusid > 0  \n");
		sbFrom.append("	   ) v \n");
		//计算损益，从而得出成本
		sbFrom.append("	  ,( \n");
		sbFrom.append("	   		SELECT d.id,d.code,d.amount,ds.profitloss  \n");
		sbFrom.append("	   		FROM sec_deliveryorder d,sec_transactiontype t,sec_dailystock ds  \n");
		sbFrom.append("	    	WHERE d.transactiontypeid =t.id  \n");
		sbFrom.append("	  			AND t.iscarrycost = 1  \n");
		sbFrom.append("	  			AND d.clientid = ds.clientid(+)  \n");
		sbFrom.append("	  			AND d.accountid = ds.accountid(+)  \n");
		sbFrom.append("	  			AND d.securitiesid = ds.securitiesid(+)  \n");
		sbFrom.append("	  			AND d.deliverydate = ds.stockdate(+)  \n");
		sbFrom.append("	   ) pl \n");
		sbFrom.append("	  WHERE   d.transactiontypeid = t.id \n");
		sbFrom.append("	    AND d.securitiesid = s.id \n");
		sbFrom.append("	    AND t.capitaldirection <> 0 \n");
		sbFrom.append("	    AND t.subjecttype = st.id \n");
		
        //国债付息为收益，不算作余额表内
		sbFrom.append("		AND t.id not in(3403)  \n");
		
		sbFrom.append("	    AND st.id in(1,2) \n");
		sbFrom.append("		AND s.statusid  = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND d.statusID >= "+SECConstant.DeliveryOrderStatus.CHECKED+" \n");
		sbFrom.append("		AND d.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND d.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND d.id= v.id(+) \n ");
		sbFrom.append("		AND d.id= pl.id(+)  \n ");
		sbFrom.append("		AND d.deliverydate >= TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		AND d.deliverydate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		//sbFrom.append("	  order by d.securitiesid ,d.deliverydate,d.clientid,d.accountid  \n");
		sbFrom.append("	) cc, \n");
		
		sbFrom.append("	(  select ds.securitiesid, ds.cost debitAmount,0  creditAmount \n");
		sbFrom.append("		,ds.clientid clientid,ds.accountid accountid,ds.stockdate deliverydate  \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		sec_dailystock ds  \n");
		sbFrom.append("			WHERE  ds.stockdate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		
		sbFrom.append("	) dd \n");
		
		sbFrom.append("	WHERE \n");
		sbFrom.append("     cc.securitiesid = dd.securitiesid(+) \n");
		sbFrom.append("	AND dd.securitiesid = aa.securitiesid(+) \n");
		sbFrom.append("	AND aa.securitiesid = bb.securitiesid(+) \n");
		
		sbFrom.append(" AND cc.clientid = dd.clientid(+) \n");
		sbFrom.append("	AND dd.clientid = aa.clientid(+) \n");
		sbFrom.append("	AND aa.clientid = bb.clientid(+) \n");
		
		sbFrom.append(" AND cc.accountid = dd.accountid(+) \n");
		sbFrom.append("	AND dd.accountid = aa.accountid(+) \n");
		sbFrom.append("	AND aa.accountid = bb.accountid(+) \n");
		
		sbFrom.append("	AND cc.deliverydate = dd.deliverydate(+) \n");
		
		sbFrom.append("	AND aa.subjectTypeId > 0 \n");
		sbFrom.append("	 \n");
		
		
		sbFrom.append("	 union \n");
		
		sbFrom.append("-------**拼写第一个表：自营证券和短期投资,主表是bb**-  \n");
		
		sbFrom.append("   SELECT  \n");
		sbFrom.append("	aa.subjectTypeId  subjectTypeId, \n");
		sbFrom.append("	aa.subjectName  subjectName, \n");
		sbFrom.append("	-1  capitaldirection, \n");
		sbFrom.append("	nvl(aa.securitiesId,-1) securitiesId, \n");
		sbFrom.append("	nvl(aa.businessType,-1) businessType, \n");
		sbFrom.append("	nvl(aa.securitiesTypeId,-1) securitiesTypeId, \n");
		sbFrom.append("	'' deliverycode, \n");
		sbFrom.append("	null deliverydate, \n");
		sbFrom.append("	nvl(aa.shortName,'') securitiesName, \n");
		sbFrom.append("	nvl(aa.counterpartId,-1) counterPartId, \n");
		sbFrom.append("	nvl(bb.debitAmount,0.00) startDebitBalance, \n");
		sbFrom.append("	nvl(bb.creditAmount,0.00) startCreditBalance, \n");
		sbFrom.append("	0.00 debitAmount, \n");
		sbFrom.append("	0.00 creditAmount, \n");
		sbFrom.append("	nvl(dd.debitAmount,0.00) endDebitBalance, \n");
		sbFrom.append("	nvl(dd.creditAmount,0.00) endCreditBalance, \n");
		sbFrom.append("	'' remark, \n");
		sbFrom.append("	-1 sitetype, \n");
		sbFrom.append("	'' svoucherno \n");
		
		sbFrom.append("	FROM \n");
		sbFrom.append("\n	( SELECT DISTINCT a.id securitiesId, substr(min(b.transactiontypeid),0,2)  businessType,a.typeid securitiesTypeId, a.shortName ,  \n");
		sbFrom.append("           c.counterpartID,min(t.capitaldirection) capitaldirection ,st.id subjectTypeId,st.name subjectName ,c.id accountid,c.clientid clientid  \n");
		
		sbFrom.append("	  FROM sec_Securities a, sec_DeliveryOrder b, sec_Account c ,sec_transactiontype t ,sec_subjecttype st \n");
		
		sbFrom.append("	  WHERE a.ID = b.securitiesID \n");
		sbFrom.append("		AND b.accountID = c.ID \n");
		sbFrom.append("		AND st.id in(1,2) \n");
		sbFrom.append("		AND b.transactiontypeid = t.id \n");
		sbFrom.append("		AND t.subjecttype = st.id \n");
		sbFrom.append("		AND t.capitaldirection <> 0\n");
		sbFrom.append("		AND a.statusID = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND a.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND b.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND b.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND b.statusID >= "+SECConstant.DeliveryOrderStatus.CHECKED+" \n");
		sbFrom.append("		AND c.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND c.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND c.statusID = "+SECConstant.AccountStatus.CHECKED+" \n");
		
		
		
		/**
		if (parameter.getSecuritiesMenuId() > -1) {
			sbFrom.append(sbSecuritiesMenu.toString());
		}
		if (parameter.getSecuritiesId() != null && !"".equals(parameter.getSecuritiesId())) {
			sbFrom.append("		AND a.ID IN ("+parameter.getSecuritiesId()+") \n");
		}
		**/
		if (parameter.getCounterPartId() != null && !"".equals(parameter.getCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getCounterPartId()+") \n");
		}
		if (parameter.getBourseCounterPartId() != null && !"".equals(parameter.getBourseCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getBourseCounterPartId()+") \n");
		}
		//二级科目
		
		sbFrom.append(sbSecSubject.toString());
		
		sbFrom.append(" group by   st.id,st.name,a.id,a.shortName,c.counterpartID ,a.typeid,c.id ,c.clientid \n");
		
		sbFrom.append("	) aa, \n");
		
		
		sbFrom.append("	(  select s.id securitiesId,s.shortname securitiesName, \n");
		sbFrom.append("		ds.cost debitAmount,0  creditAmount,ds.clientid clientid,ds.accountid accountid  \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		 sec_dailystock ds ,sec_securities s \n");
		sbFrom.append("		WHERE   ds.securitiesid = s.id \n");
		sbFrom.append("			AND s.statusid   = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND s.currencyid = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			AND ds.stockdate = TO_DATE('"+preQueryDateStart+"', 'YYYY-MM-DD') \n"); 
		
		sbFrom.append("	) bb, \n");
		
		sbFrom.append("	(  select ds.securitiesid, ds.cost debitAmount,0  creditAmount \n");
		sbFrom.append("		,ds.clientid clientid,ds.accountid accountid,ds.stockdate deliverydate  \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		sec_dailystock ds  \n");
		sbFrom.append("			WHERE  ds.stockdate <= TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		
		sbFrom.append("	) dd \n");
		
		
		
		
		sbFrom.append("	WHERE \n");
		sbFrom.append("     bb.securitiesid = aa.securitiesid(+) \n");
		sbFrom.append(" AND aa.securitiesid = dd.securitiesid(+) \n");
		
		sbFrom.append(" AND bb.clientid = aa.clientid(+) \n");
		sbFrom.append(" AND aa.clientid = dd.clientid(+) \n");
		
		sbFrom.append(" AND bb.debitAmount> 0.0 \n");
		
		sbFrom.append(" AND bb.accountid = aa.accountid(+) \n");
		sbFrom.append(" AND aa.accountid = dd.accountid(+) \n");
		
		sbFrom.append(" AND dd.deliverydate(+)= TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		
		sbFrom.append("	AND aa.subjectTypeId > 0 \n");
		sbFrom.append("	 \n");
		
		sbFrom.append("	 ) \n");
		
		
		sbFrom.append(" union \n");
		
		
		sbFrom.append("-----------拼写第二个表：债券回购（businessTypeId is 26 & 27）,主表是cc**  \n");
		
		sbFrom.append(" (  \n");
		sbFrom.append(" SELECT  \n");
		sbFrom.append("  aa.subjectTypeId  subjectTypeId, \n");
		sbFrom.append("	 aa.subjectName  subjectName, \n");
		sbFrom.append("	 cc.capitaldirection  capitaldirection, \n");
		sbFrom.append("	 nvl(aa.securitiesId,-1) securitiesId, \n");
		sbFrom.append("	 nvl(aa.businessType,-1) businessType, \n");
		sbFrom.append("	 nvl(aa.securitiesTypeId,-1) securitiesTypeId, \n");
		sbFrom.append("	 cc.code deliverycode, \n");
		sbFrom.append("	 cc.deliverydate deliverydate, \n");
		sbFrom.append("	 nvl(aa.shortName,'') securitiesName, \n");
		sbFrom.append("	 nvl(aa.counterpartId,-1) counterPartId,\n");
		sbFrom.append("  nvl(bb.debitAmount,0.00) startDebitBalance, \n");
		sbFrom.append("  nvl(bb.creditAmount,0.00) startCreditBalance, \n");
		sbFrom.append("	 nvl(cc.debitAmount,0.00) debitAmount, \n");
		sbFrom.append("  nvl(cc.creditAmount,0.00) creditAmount, \n");
		sbFrom.append("	 0.00 endDebitBalance, \n");
		sbFrom.append("	 0.00 endCreditBalance, \n");
		sbFrom.append("  cc.remark remark, \n");
		sbFrom.append("	 aa.sitetype, \n");
		sbFrom.append("	 cc.svoucherno svoucherno \n");
		
		sbFrom.append("	 FROM    \n");
		
		sbFrom.append("	(  SELECT DISTINCT a.id securitiesId, substr(min(b.transactiontypeid),0,2)  businessType, \n");
		sbFrom.append("	   	a.typeid securitiesTypeId, a.shortName ,c.counterpartID,min(t.capitaldirection) capitaldirection , \n");
		sbFrom.append("		st.id subjectTypeId,st.name subjectName,c.id accountid,c.clientid clientid,sitetype \n");
		
		sbFrom.append("	   FROM sec_Securities a, sec_DeliveryOrder b, sec_Account c ,sec_counterpart ct, sec_transactiontype t ,sec_subjecttype st \n");
		
		sbFrom.append("	   WHERE b.securitiesID = a.id(+) \n");
		sbFrom.append("		AND b.accountID = c.ID(+) \n");
		sbFrom.append("		AND c.counterpartid = ct.id(+) \n");
		sbFrom.append("		AND st.id in(3,4) \n");
		sbFrom.append("		AND b.transactiontypeid = t.id \n");
		sbFrom.append("		AND t.subjecttype = st.id \n");
		sbFrom.append("		AND t.capitaldirection <> 0 \n");
		
		sbFrom.append("		AND b.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND a.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND t.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND c.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND b.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND b.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND c.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND c.currencyID = "+parameter.getCurrencyId()+" \n");
		
		/**
		if (parameter.getSecuritiesMenuId() > -1) {
			sbFrom.append(sbSecuritiesMenu.toString());
		}
		if (parameter.getSecuritiesId() != null && !"".equals(parameter.getSecuritiesId())) {
			sbFrom.append("		AND a.ID IN ("+parameter.getSecuritiesId()+") \n");
		}
		**/
		if (parameter.getCounterPartId() != null && !"".equals(parameter.getCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getCounterPartId()+") \n");
		}
		
		if (parameter.getBourseCounterPartId() != null && !"".equals(parameter.getBourseCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getBourseCounterPartId()+") \n");
		}
		//二级科目
		
		sbFrom.append(sbSecSubject.toString());
		
		sbFrom.append(" group by   st.id,st.name,a.id,a.shortName,c.counterpartID ,a.typeid,c.id ,c.clientid,sitetype \n");
		
		sbFrom.append("	) aa, \n");
		
		sbFrom.append("	(  SELECT s.id securitiesId,s.shortname securitiesName,-1 counterpartid,min(t.capitaldirection) capitaldirection ,d.clientid ,d.accountid, \n");
		sbFrom.append("		  sum( DECODE(t.capitaldirection, 1, amount, 4, amount, 3, amount)) creditAmount,  \n");
		sbFrom.append("		  sum( DECODE(t.capitaldirection, 2, amount, 5, amount, 3, amount)) debitAmount \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		sec_securities s,sec_deliveryorder d,sec_transactiontype t \n");
		sbFrom.append("		WHERE   d.securitiesid = s.id \n");
		sbFrom.append("		    AND substr(d.transactiontypeid,0,2) in(26,27) \n");
		sbFrom.append("		    AND d.transactiontypeid = t.id \n");
		sbFrom.append("			AND s.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		    AND s.currencyid = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			AND d.deliverydate	< TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		group by  s.id,s.shortname   ,d.clientid ,d.accountid   \n");
		sbFrom.append("	) bb, \n");
		
		sbFrom.append("	(  SELECT distinct d.code code ,d.remark remark,d.counterpartid,s.typeid,s.id securitiesId,s.shortName, \n");
		sbFrom.append("	     t.capitaldirection capitaldirection,d.deliveryDate deliverydate ,d.clientid,d.accountid, \n");
		sbFrom.append("		 DECODE(t.capitaldirection, 1, d.amount, 4, d.amount,3, d.amount) creditAmount ,  \n");
		sbFrom.append("		 DECODE(t.capitaldirection, 2, d.amount, 5, d.amount,3, d.amount) debitAmount ,st.id subjecttypeid ,v.svoucherno svoucherno \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		 sec_deliveryorder d,sec_transactiontype t,sec_securities s,sec_subjecttype st  \n");
        //拼写凭证号子表
		sbFrom.append("	  ,( \n");
		sbFrom.append("	   		SELECT distinct a.id,c.svoucherno  \n");
		sbFrom.append("	   		FROM sec_deliveryorder a,sec_glentry b ,SETT_GLORACLEVOUCHER c  \n");
		sbFrom.append("	    	WHERE   \n");
		sbFrom.append("	  			a.code = c.stransactionno  \n");
		sbFrom.append("	  			AND a.transactiontypeid = b.transactiontypeid  \n");
		sbFrom.append("	  			AND b.executedate = c.dtgldate  \n");
		sbFrom.append("	  			AND b.statusid > 0  \n");
		sbFrom.append("	   ) v \n");
		sbFrom.append("		WHERE  d.transactiontypeid = t.id \n");
		sbFrom.append("		     AND d.securitiesid = s.id \n");
		sbFrom.append("		     AND t.capitaldirection <>0 \n");
		sbFrom.append("	         AND t.subjecttype = st.id \n");
		sbFrom.append("	         AND st.id in(3,4) \n");
		sbFrom.append("	         AND d.id = v.id(+) \n");
		sbFrom.append("			 AND s.statusid   = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		     AND d.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		     AND d.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			 AND d.deliverydate >=  TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("			 AND d.deliverydate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		//sbFrom.append("		 order by d.securitiesid ,d.deliverydate,d.clientid,d.accountid   \n");
		sbFrom.append("	) cc \n");
		
		
		sbFrom.append("	WHERE \n");
		sbFrom.append("	   cc.securitiesid = aa.securitiesid(+) \n");
		sbFrom.append("	  AND aa.securitiesid = bb.securitiesid(+) \n");
		
		sbFrom.append("	  AND cc.clientid = aa.clientid(+) \n");
		sbFrom.append("	  AND aa.clientid = bb.clientid(+) \n");
		
		sbFrom.append("	  AND cc.accountid = aa.accountid(+) \n");
		sbFrom.append("	  AND aa.accountid = bb.accountid(+) \n");
		
		sbFrom.append("	  AND cc.subjecttypeid = aa.subjectTypeId(+) \n");
		sbFrom.append("	  AND aa.subjectTypeId > 0 \n");
		sbFrom.append("	 \n");
		
		sbFrom.append("  union \n ");
		
		sbFrom.append("-----------拼写第二个表：债券回购（businessTypeId is 26 & 27）,主表是bb**  \n");
		
		sbFrom.append(" SELECT  \n");
		sbFrom.append("  aa.subjectTypeId  subjectTypeId, \n");
		sbFrom.append("	 aa.subjectName  subjectName, \n");
		sbFrom.append("	 cc.capitaldirection  capitaldirection, \n");
		sbFrom.append("	 nvl(aa.securitiesId,-1) securitiesId, \n");
		sbFrom.append("	 nvl(aa.businessType,-1) businessType, \n");
		sbFrom.append("	 nvl(aa.securitiesTypeId,-1) securitiesTypeId, \n");
		sbFrom.append("	 cc.code deliverycode, \n");
		sbFrom.append("	 cc.deliverydate deliverydate, \n");
		sbFrom.append("	 nvl(aa.shortName,'') securitiesName, \n");
		sbFrom.append("	 nvl(aa.counterpartId,-1) counterPartId,\n");
		sbFrom.append("  nvl(bb.debitAmount,0.00) startDebitBalance, \n");
		sbFrom.append("  nvl(bb.creditAmount,0.00) startCreditBalance, \n");
		sbFrom.append("	 nvl(cc.debitAmount,0.00) debitAmount, \n");
		sbFrom.append("  nvl(cc.creditAmount,0.00) creditAmount, \n");
		sbFrom.append("	 0.00 endDebitBalance, \n");
		sbFrom.append("	 0.00 endCreditBalance, \n");
		sbFrom.append("  cc.remark remark, \n");
		sbFrom.append("	 aa.sitetype, \n");
		sbFrom.append("	 cc.svoucherno svoucherno \n");
		
		sbFrom.append("	 FROM    \n");
		
		sbFrom.append("	(  SELECT DISTINCT a.id securitiesId, substr(min(b.transactiontypeid),0,2)  businessType, \n");
		sbFrom.append("	   	a.typeid securitiesTypeId, a.shortName ,c.counterpartID,min(t.capitaldirection) capitaldirection , \n");
		sbFrom.append("		st.id subjectTypeId,st.name subjectName,c.id accountid,c.clientid clientid,sitetype \n");
		
		sbFrom.append("	   FROM sec_Securities a, sec_DeliveryOrder b, sec_Account c ,sec_counterpart ct, sec_transactiontype t ,sec_subjecttype st \n");
		
		sbFrom.append("	   WHERE b.securitiesID = a.id(+) \n");
		sbFrom.append("		AND b.accountID = c.ID(+) \n");
		sbFrom.append("		AND c.counterpartid = ct.id(+) \n");
		sbFrom.append("		AND st.id in(3,4) \n");
		sbFrom.append("		AND b.transactiontypeid = t.id \n");
		sbFrom.append("		AND t.subjecttype = st.id \n");
		sbFrom.append("		AND t.capitaldirection <> 0 \n");
		
		sbFrom.append("		AND b.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND a.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND t.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND c.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND b.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND b.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND c.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND c.currencyID = "+parameter.getCurrencyId()+" \n");
		
		/**
		if (parameter.getSecuritiesMenuId() > -1) {
			sbFrom.append(sbSecuritiesMenu.toString());
		}
		if (parameter.getSecuritiesId() != null && !"".equals(parameter.getSecuritiesId())) {
			sbFrom.append("		AND a.ID IN ("+parameter.getSecuritiesId()+") \n");
		}
		**/
		if (parameter.getCounterPartId() != null && !"".equals(parameter.getCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getCounterPartId()+") \n");
		}
		
		if (parameter.getBourseCounterPartId() != null && !"".equals(parameter.getBourseCounterPartId())) {
			sbFrom.append("		AND c.counterPartID IN ("+parameter.getBourseCounterPartId()+") \n");
		}
		//二级科目
		
		sbFrom.append(sbSecSubject.toString());
		
		sbFrom.append(" group by   st.id,st.name,a.id,a.shortName,c.counterpartID ,a.typeid,c.id ,c.clientid,sitetype \n");
		
		sbFrom.append("	) aa, \n");
		
		sbFrom.append("	(  SELECT s.id securitiesId,s.shortname securitiesName,-1 counterpartid,min(t.capitaldirection) capitaldirection ,d.clientid ,d.accountid  ,st.id subjecttypeid , \n");
		sbFrom.append("		  sum( DECODE(t.capitaldirection, 1, amount, 4, amount, 3, amount)) creditAmount,  \n");
		sbFrom.append("		  sum( DECODE(t.capitaldirection, 2, amount, 5, amount, 3, amount)) debitAmount \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		sec_securities s,sec_deliveryorder d,sec_transactiontype t ,sec_subjecttype st \n");
		sbFrom.append("		WHERE   d.securitiesid = s.id \n");
		sbFrom.append("		    AND substr(d.transactiontypeid,0,2) in(26,27) \n");
		sbFrom.append("		    AND t.subjecttype = st.id  \n");
		sbFrom.append("		    AND d.transactiontypeid = t.id \n");
		sbFrom.append("			AND s.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		    AND s.currencyid = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			AND d.deliverydate	< TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		group by  s.id,s.shortname   ,d.clientid ,d.accountid  , st.id \n");
		sbFrom.append("	) bb, \n");
		
		sbFrom.append("	(  SELECT distinct d.code code ,d.remark remark,d.counterpartid,s.typeid,s.id securitiesId,s.shortName, \n");
		sbFrom.append("	     t.capitaldirection capitaldirection,d.deliveryDate deliverydate ,d.clientid,d.accountid, \n");
		sbFrom.append("		 DECODE(t.capitaldirection, 1, d.amount, 4, d.amount,3, d.amount) creditAmount ,  \n");
		sbFrom.append("		 DECODE(t.capitaldirection, 2, d.amount, 5, d.amount,3, d.amount) debitAmount ,st.id subjecttypeid ,v.svoucherno svoucherno \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		 sec_deliveryorder d,sec_transactiontype t,sec_securities s,sec_subjecttype st  \n");
        //拼写凭证号子表
		sbFrom.append("	  ,( \n");
		sbFrom.append("	   		SELECT distinct a.id,c.svoucherno  \n");
		sbFrom.append("	   		FROM sec_deliveryorder a,sec_glentry b ,SETT_GLORACLEVOUCHER c  \n");
		sbFrom.append("	    	WHERE   \n");
		sbFrom.append("	  			a.code = c.stransactionno  \n");
		sbFrom.append("	  			AND a.transactiontypeid = b.transactiontypeid  \n");
		sbFrom.append("	  			AND b.executedate = c.dtgldate  \n");
		sbFrom.append("	  			AND b.statusid > 0  \n");
		sbFrom.append("	   ) v \n");
		sbFrom.append("		WHERE  d.transactiontypeid = t.id \n");
		sbFrom.append("		     AND d.securitiesid = s.id \n");
		sbFrom.append("		     AND t.capitaldirection <>0 \n");
		sbFrom.append("	         AND t.subjecttype = st.id \n");
		sbFrom.append("	         AND st.id in(3,4) \n");
		sbFrom.append("	         AND d.id = v.id(+) \n");
		sbFrom.append("			 AND s.statusid   = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		     AND d.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		     AND d.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			 AND d.deliverydate >=  TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("			 AND d.deliverydate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		//sbFrom.append("		 order by d.securitiesid ,d.deliverydate,d.clientid,d.accountid   \n");
		sbFrom.append("	) cc \n");
		
		
		sbFrom.append("	WHERE \n");
		sbFrom.append("	   bb.securitiesid = aa.securitiesid(+) \n");
		sbFrom.append("	  AND aa.securitiesid = cc.securitiesid(+) \n");
		
		sbFrom.append("	  AND bb.clientid = aa.clientid(+) \n");
		sbFrom.append("	  AND aa.clientid = cc.clientid(+) \n");
		
		sbFrom.append("	  AND bb.accountid = aa.accountid(+) \n");
		sbFrom.append("	  AND aa.accountid = cc.accountid(+) \n");
		
		sbFrom.append("	  AND bb.subjecttypeid = aa.subjectTypeId(+) \n");
		sbFrom.append("	  AND aa.subjectTypeId > 0 \n");
		sbFrom.append("	 \n");
		
		
		sbFrom.append(" ) \n");
		
		sbFrom.append(" union \n");
		
		
		sbFrom.append("-----------拼写第三个表：买入回购资产，卖出回购资产款，结构性投资,主表是cc**  \n");
		
		sbFrom.append(" (  \n");
		sbFrom.append(" SELECT  \n");
		sbFrom.append("  aa.subjectTypeId  subjectTypeId, \n");
		sbFrom.append("	 aa.subjectName  subjectName, \n");
		sbFrom.append("	 cc.capitaldirection  capitaldirection, \n");
		sbFrom.append("	 -1 securitiesId, \n");
		sbFrom.append("	 nvl(aa.businessType,-1) businessType, \n");
		sbFrom.append("	 -1 securitiesTypeId, \n");
		sbFrom.append("	 cc.code deliverycode, \n");
		sbFrom.append("	 cc.executedate deliverydate, \n");
		sbFrom.append("	 '' securitiesName, \n");
		sbFrom.append("	 nvl(aa.counterpartId,-1) counterPartId,\n");
		sbFrom.append("  nvl(bb.debitAmount,0.00) startDebitBalance, \n");
		sbFrom.append("  nvl(bb.creditAmount,0.00) startCreditBalance, \n");
		sbFrom.append("	 nvl(cc.debitAmount,0.00) debitAmount, \n");
		sbFrom.append("  nvl(cc.creditAmount,0.00) creditAmount, \n");
		sbFrom.append("	 0.00 endDebitBalance, \n");
		sbFrom.append("	 0.00 endCreditBalance, \n");
		sbFrom.append("  cc.remark remark, \n");
		sbFrom.append("	 aa.sitetype, \n");
		sbFrom.append("	 cc.svoucherno svoucherno \n");
		
		sbFrom.append("	 FROM    \n");
		
		sbFrom.append("	(  SELECT DISTINCT d.counterpartid counterpartid  ,substr(min(b.transactiontypeid),0,2)  businessType, \n");
		sbFrom.append("	   	min(t.capitaldirection) capitaldirection , \n");
		sbFrom.append("		st.id subjectTypeId,st.name subjectName,sitetype  \n");
		
		sbFrom.append("	   FROM sec_noticeform b, sec_deliveryorder d,sec_counterpart c,sec_transactiontype t ,sec_subjecttype st   \n");
		
		sbFrom.append("	   WHERE b.deliveryorderid = d.id \n");
		sbFrom.append("		AND d.counterpartid = c.ID \n");
		sbFrom.append("		AND b.transactionTypeId = t.id \n");
		sbFrom.append("		AND t.subjecttype = st.id \n");	
		sbFrom.append("		AND st.id in(2,3,4,5) \n"); //2:委托理财归到短期投资，但取值逻辑与合同业务相同
		sbFrom.append("		AND t.capitaldirection <> 0 \n");
		
		sbFrom.append("		AND b.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND d.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND t.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND c.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		
		/**
		if (parameter.getSecuritiesMenuId() > -1) {
			sbFrom.append(sbSecuritiesMenu.toString());
		}
		if (parameter.getSecuritiesId() != null && !"".equals(parameter.getSecuritiesId())) {
			sbFrom.append("		AND  1<>1 \n");
		}
		**/
		if (parameter.getCounterPartId() != null && !"".equals(parameter.getCounterPartId())) {
			sbFrom.append("		AND c.ID IN ("+parameter.getCounterPartId()+") \n");
		}
		
		if (parameter.getBourseCounterPartId() != null && !"".equals(parameter.getBourseCounterPartId())) {
			sbFrom.append("		AND c.ID IN ("+parameter.getBourseCounterPartId()+") \n");
		}
		//二级科目
		
		sbFrom.append(sbSecSubject.toString());
		
		sbFrom.append(" group by   st.id,st.name,d.counterpartid,t.subjecttype ,sitetype \n");
		
		sbFrom.append("	) aa, \n");
		
		sbFrom.append("	(  SELECT c.id counterpartid,min(t.capitaldirection) capitaldirection, substr(t.id,0,2) businessTypeId, \n");
		sbFrom.append("		  SUM( DECODE(t.capitaldirection, 1, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 4, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) ) creditAmount,  \n");
		sbFrom.append("		  SUM( DECODE(t.capitaldirection, 2, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 5, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) ) debitAmount \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		sec_noticeform n,sec_deliveryorder d ,sec_transactiontype t,sec_counterpart  c \n");
		sbFrom.append("		WHERE    n.deliveryorderid = d.id \n");
		sbFrom.append("		    AND d.counterpartid = c.id \n");
		sbFrom.append("		    AND n.transactiontypeid = t.id \n");
		sbFrom.append("		    AND t.id in(7103,7102,7302,7303,7702,7703) \n");//涉及到利息的交易不在报表的统计范围之内,故过滤掉交易类型7304,7704
		sbFrom.append("			AND n.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND t.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND c.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		    AND d.officeID = "+parameter.getOfficeId()+" \n");
		/**结构性投资的币种限制条件**/
		sbFrom.append("		    AND d.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			AND n.executedate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		group by  c.id  ,substr(t.id,0,2)  \n");
		sbFrom.append("	) bb, \n");
		
		sbFrom.append("	(  SELECT distinct c.id counterpartid,n.code,n.remark,t.capitaldirection capitaldirection,n.executedate , \n");
		sbFrom.append("	     DECODE(t.capitaldirection, 1, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4	,n.noticeInterest,n.noticeAmount), 4, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) creditAmount, \n");
		sbFrom.append("		 DECODE(t.capitaldirection, 2, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 5, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) debitAmount  \n");
		sbFrom.append("		 ,st.id subjectTypeid  ,v.svoucherno svoucherno  ,substr(n.transactiontypeid,0,2) businessTypeId \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		 sec_noticeform n,sec_deliveryorder d ,sec_transactiontype t,sec_counterpart  c,sec_subjecttype st    \n");
        //拼写凭证号子表
		sbFrom.append("	  ,( \n");
		sbFrom.append("	   		SELECT distinct a.id,c.svoucherno  \n");
		sbFrom.append("	   		FROM sec_deliveryorder a,sec_glentry b ,SETT_GLORACLEVOUCHER c  \n");
		sbFrom.append("	    	WHERE   \n");
		sbFrom.append("	  			a.code = c.stransactionno  \n");
		sbFrom.append("	  			AND a.transactiontypeid = b.transactiontypeid  \n");
		sbFrom.append("	  			AND b.executedate = c.dtgldate  \n");
		sbFrom.append("	  			AND b.statusid > 0  \n");
		sbFrom.append("	   ) v \n");
		sbFrom.append("		WHERE  n.deliveryorderid = d.id \n");
		sbFrom.append("		     AND d.counterpartid = c.id \n");
		sbFrom.append("		     AND n.transactiontypeid = t.id \n");
		sbFrom.append("	         AND t.subjecttype = st.id  \n");
		sbFrom.append("	         AND t.capitaldirection <>0 \n");
		sbFrom.append("	         AND d.id=v.id(+) \n");
		sbFrom.append("		     AND t.id in(7103,7102,7302,7303,7702,7703) \n");
		sbFrom.append("	         AND (n.noticeAmount > 0 or n.noticeInterest > 0) \n");
		sbFrom.append("			 AND n.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND t.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND c.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		     AND d.officeID = "+parameter.getOfficeId()+" \n");
		/**结构性投资的币种限制条件**/
		sbFrom.append("		     AND d.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			 AND n.executedate >=  TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("			 AND n.executedate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		//sbFrom.append("		  order by d.securitiesid ,d.deliverydate,d.clientid,d.accountid   \n");
		sbFrom.append("	) cc \n");
		
		
		sbFrom.append("	WHERE \n");
		sbFrom.append("	    cc.subjectTypeid = aa.subjectTypeId \n");
		sbFrom.append("	  AND cc.counterpartid = aa.counterpartid(+) \n");
		sbFrom.append("	  AND aa.counterpartid  = bb.counterpartid(+) \n");
		sbFrom.append("	  AND cc.businessTypeId = aa.businessType(+) \n");
		sbFrom.append("	  AND aa.businessType   = bb.businessTypeId(+) \n");
		sbFrom.append("	  AND aa.subjectTypeId > 0 \n");
		sbFrom.append("	 \n");
		
		sbFrom.append(" union \n");
		
		sbFrom.append("-----------拼写第三个表：买入回购资产，卖出回购资产款，结构性投资,主表是bb**  \n");
		
		sbFrom.append(" SELECT  \n");
		sbFrom.append("  aa.subjectTypeId  subjectTypeId, \n");
		sbFrom.append("	 aa.subjectName  subjectName, \n");
		sbFrom.append("	 cc.capitaldirection  capitaldirection, \n");
		sbFrom.append("	 -1 securitiesId, \n");
		sbFrom.append("	 nvl(aa.businessType,-1) businessType, \n");
		sbFrom.append("	 -1 securitiesTypeId, \n");
		sbFrom.append("	 cc.code deliverycode, \n");
		sbFrom.append("	 cc.executedate deliverydate, \n");
		sbFrom.append("	 '' securitiesName, \n");
		sbFrom.append("	 nvl(aa.counterpartId,-1) counterPartId,\n");
		sbFrom.append("  nvl(bb.debitAmount,0.00) startDebitBalance, \n");
		sbFrom.append("  nvl(bb.creditAmount,0.00) startCreditBalance, \n");
		sbFrom.append("	 nvl(cc.debitAmount,0.00) debitAmount, \n");
		sbFrom.append("  nvl(cc.creditAmount,0.00) creditAmount, \n");
		sbFrom.append("	 0.00 endDebitBalance, \n");
		sbFrom.append("	 0.00 endCreditBalance, \n");
		sbFrom.append("  cc.remark remark, \n");
		sbFrom.append("	 aa.sitetype, \n");
		sbFrom.append("	 cc.svoucherno svoucherno \n");
		
		sbFrom.append("	 FROM    \n");
		
		sbFrom.append("	(  SELECT DISTINCT d.counterpartid counterpartid  ,substr(min(b.transactiontypeid),0,2)  businessType, \n");
		sbFrom.append("	   	min(t.capitaldirection) capitaldirection , \n");
		sbFrom.append("		st.id subjectTypeId,st.name subjectName,sitetype  \n");
		
		sbFrom.append("	   FROM sec_noticeform b, sec_deliveryorder d,sec_counterpart c,sec_transactiontype t ,sec_subjecttype st   \n");
		
		sbFrom.append("	   WHERE b.deliveryorderid = d.id \n");
		sbFrom.append("		AND d.counterpartid = c.ID \n");
		sbFrom.append("		AND b.transactionTypeId = t.id \n");
		sbFrom.append("		AND t.subjecttype = st.id \n");	
		sbFrom.append("		AND st.id in(2,3,4,5) \n"); //2:委托理财归到短期投资，但取值逻辑与合同业务相同
		sbFrom.append("		AND t.capitaldirection <> 0 \n");
		
		sbFrom.append("		AND b.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND d.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND t.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND c.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		
		/**
		if (parameter.getSecuritiesMenuId() > -1) {
			sbFrom.append(sbSecuritiesMenu.toString());
		}
		if (parameter.getSecuritiesId() != null && !"".equals(parameter.getSecuritiesId())) {
			sbFrom.append("		AND  1<>1 \n");
		}
		**/
		if (parameter.getCounterPartId() != null && !"".equals(parameter.getCounterPartId())) {
			sbFrom.append("		AND c.ID IN ("+parameter.getCounterPartId()+") \n");
		}
		
		if (parameter.getBourseCounterPartId() != null && !"".equals(parameter.getBourseCounterPartId())) {
			sbFrom.append("		AND c.ID IN ("+parameter.getBourseCounterPartId()+") \n");
		}
		//二级科目
		
		sbFrom.append(sbSecSubject.toString());
		
		sbFrom.append(" group by   st.id,st.name,d.counterpartid,t.subjecttype ,sitetype \n");
		
		sbFrom.append("	) aa, \n");
		
		sbFrom.append("	(  SELECT c.id counterpartid,min(t.capitaldirection) capitaldirection, substr(t.id,0,2) businessTypeId ,st.id subjectTypeid ,\n");
		sbFrom.append("		  SUM( DECODE(t.capitaldirection, 1, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 4, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) ) creditAmount,  \n");
		sbFrom.append("		  SUM( DECODE(t.capitaldirection, 2, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 5, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) ) debitAmount \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		sec_noticeform n,sec_deliveryorder d ,sec_transactiontype t,sec_counterpart  c ,sec_subjecttype st \n");
		sbFrom.append("		WHERE    n.deliveryorderid = d.id \n");
		sbFrom.append("		    AND d.counterpartid = c.id \n");
		sbFrom.append("		    AND t.subjecttype = st.id  \n");
		sbFrom.append("		    AND n.transactiontypeid = t.id \n");
		sbFrom.append("		    AND d.officeID = "+parameter.getOfficeId()+" \n");
		/**结构性投资的币种限制条件**/
		sbFrom.append("		    AND d.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		    AND t.id in(7103,7102,7302,7303,7702,7703) \n");//涉及到利息的交易不在报表的统计范围之内,故过滤掉交易类型7304,7704
		sbFrom.append("			AND n.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND t.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND c.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			AND n.executedate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		group by  c.id  ,substr(t.id,0,2) ,st.id  \n");
		sbFrom.append("	) bb, \n");
		
		sbFrom.append("	(  SELECT distinct c.id counterpartid,n.code,n.remark,t.capitaldirection capitaldirection,n.executedate , \n");
		sbFrom.append("	     DECODE(t.capitaldirection, 1, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4	,n.noticeInterest,n.noticeAmount), 4, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) creditAmount, \n");
		sbFrom.append("		 DECODE(t.capitaldirection, 2, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 5, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount), 3, decode(TO_NUMBER(substr(n.transactiontypeid,3,4)),4,n.noticeInterest,n.noticeAmount)) debitAmount  \n");
		sbFrom.append("		 ,st.id subjectTypeid  ,v.svoucherno svoucherno  ,substr(n.transactiontypeid,0,2) businessTypeId \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		 sec_noticeform n,sec_deliveryorder d ,sec_transactiontype t,sec_counterpart  c,sec_subjecttype st    \n");
        //拼写凭证号子表
		sbFrom.append("	  ,( \n");
		sbFrom.append("	   		SELECT distinct a.id,c.svoucherno  \n");
		sbFrom.append("	   		FROM sec_deliveryorder a,sec_glentry b ,SETT_GLORACLEVOUCHER c  \n");
		sbFrom.append("	    	WHERE   \n");
		sbFrom.append("	  			a.code = c.stransactionno  \n");
		sbFrom.append("	  			AND a.transactiontypeid = b.transactiontypeid  \n");
		sbFrom.append("	  			AND b.executedate = c.dtgldate  \n");
		sbFrom.append("	  			AND b.statusid > 0  \n");
		sbFrom.append("	   ) v \n");
		sbFrom.append("		WHERE  n.deliveryorderid = d.id \n");
		sbFrom.append("		     AND d.counterpartid = c.id \n");
		sbFrom.append("		     AND n.transactiontypeid = t.id \n");
		sbFrom.append("	         AND t.subjecttype = st.id  \n");
		sbFrom.append("	         AND t.capitaldirection <>0 \n");
		sbFrom.append("	         AND d.id=v.id(+) \n");
		sbFrom.append("		     AND t.id in(7103,7102,7302,7303,7702,7703) \n");
		sbFrom.append("	         AND (n.noticeAmount > 0 or n.noticeInterest > 0) \n");
		sbFrom.append("			 AND n.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND d.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND t.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("			 AND c.statusid   >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		     AND d.officeID = "+parameter.getOfficeId()+" \n");
		/**结构性投资的币种限制条件**/
		sbFrom.append("		     AND d.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("			 AND n.executedate >=  TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateStart())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("			 AND n.executedate < TO_DATE('"+DataFormat.getDateString(parameter.getNoticeInputDateEnd())+"', 'YYYY-MM-DD') \n");
		//sbFrom.append("		  order by d.securitiesid ,d.deliverydate,d.clientid,d.accountid   \n");
		sbFrom.append("	) cc \n");
		
		
		sbFrom.append("	WHERE \n");
		sbFrom.append("	    bb.subjectTypeid = aa.subjectTypeId \n");
		sbFrom.append("	  AND bb.counterpartid = aa.counterpartid(+) \n");
		sbFrom.append("	  AND aa.counterpartid  = cc.counterpartid(+) \n");
		sbFrom.append("	  AND bb.businessTypeId = aa.businessType(+) \n");
		sbFrom.append("	  AND aa.businessType   = cc.businessTypeId(+) \n");
		sbFrom.append("	  AND aa.subjectTypeId > 0 \n");
		sbFrom.append("	 \n");
		
		
		sbFrom.append( " ) \n");
		
		sbFrom.append("	) \n");
		
        //WHERE
		
		sbWhere		= new StringBuffer();
		
		sbWhere.append("	capitaldirection is not null or ( capitaldirection is null  and startdebitbalance <> startcreditbalance ) \n");
		
		
			
	}

	/**
	 * 明细账表数据
	 * @param  PrintSecuritiesListAccountParam
	 * @return PageLoader
	 * @exception SecuritiesException
	 */
	public PageLoader printSecuritiesListAccount(PrintSecuritiesListAccountParam parameter) throws SecuritiesException {
		this.initialSql(parameter);
		
		//获取PageLoader对象
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
			"com.iss.system.dao.PageLoader");

		//初始化PageLoader对象、实现查询和分页
		
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.print.dataentity.PrintSecuritiesListAccountInfo",
			null);
		pageLoader.setOrderBy(" ORDER BY subjectTypeId,securitiesTypeId, securitiesId  ,counterpartid,deliverydate ,deliverycode \n");

		return pageLoader;
	}
}