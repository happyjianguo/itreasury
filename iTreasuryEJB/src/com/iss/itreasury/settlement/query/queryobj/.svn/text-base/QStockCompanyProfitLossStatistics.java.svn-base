/*
 * Created on 2005-1-24
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.clientcenter.query.queryobj.BaseQueryObject;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.query.paraminfo.QueryStockCompanyStatisticsInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryStockCompanyStatisticsResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryStockCompanyStatisticsSumInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;

/**
 * @author ygzhao
 * 股份公司关联客户损益表项目分析 
 */
public class QStockCompanyProfitLossStatistics extends BaseQueryObject
{
    private Collection c = new ArrayList();
    private QueryStockCompanyStatisticsSumInfo qscsSumInfo = new QueryStockCompanyStatisticsSumInfo();

    /**
     *  
     */
    public QStockCompanyProfitLossStatistics()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public Collection findByCondition(QueryStockCompanyStatisticsInfo info)
            throws Exception
    {        
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append(" select projectID,clientTypeID,sum(amount) amount,releaseAmountLimit,awokeRate \n");
            buffer.append(" from \n");
            buffer.append(" ( \n");
            //
            buffer.append(" SELECT aa.projectID, aa.clientTypeID, bb.amount, r.releaseAmountLimit, r.awokeRate \n");
            buffer.append(" FROM \n");
            buffer.append(" (select a.projectID,b.ID as clientTypeID from SETT_STOCKPROJECTSETTING a, SETT_ENTERPRICETYPE b \n");
            buffer.append("  where a.statusID = 1 and b.nStatusID = 1 and projectType = 2 \n");
            buffer.append("   and accountType != -1 and contractType != 13 \n"); ////非担保类型
            buffer.append("  and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n");
            if(info.getProjectID() > 0)
                buffer.append("  and a.projectID = " + info.getProjectID());
            if(info.getClientType() > 0)
                buffer.append("  and b.ID = " + info.getClientType() + " \n");
            buffer.append("  group by a.projectID, b.ID ) aa,\n");
            buffer.append(" (select sum(v.Interest) as amount, v.CLIENTTYPEID1 as clientTypeID1, v.CLIENTTYPEID2 as clientTypeID2, \n");
            buffer.append("   v.CLIENTTYPEID3 as clientTypeID3, v.CLIENTTYPEID4 as clientTypeID4, v.CLIENTTYPEID5 as clientTypeID5, v.CLIENTTYPEID6 as clientTypeID6, p.projectID \n");
            buffer.append("  from sett_vTransInterestDetail v, \n");            
            buffer.append(" (select projectID,contractType,accountType,relateClientType,profitAndLossType from SETT_STOCKPROJECTSETTING where ");
            if (info.getProjectID() > 0)
                buffer.append(" projectID = " + info.getProjectID());
            else
                buffer.append(" projectType = 2 ");
            buffer.append(" and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n");
            buffer.append(" and accountType != -1 and contractType != 13 "); ////非担保类型
            buffer.append(" and statusID = 1 )");
            buffer.append("	p \n");            
            buffer.append(" where v.CurrencyID = " + info.getCurrencyID());
            buffer.append(" and v.OfficeID = " + info.getOfficeID());
            buffer.append(" and v.IsRecord = 1 \n");//是否结息记录
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");//执行日
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");       
            buffer.append(" and '<' || replace(p.profitAndLossType,',','><') || '>'like '%<' || v.InterestTypeID || '>%' \n");//损益类型
            buffer.append(" and v.AccountTypeID = p.accountType \n");//账户类型
            buffer.append(" and (v.LoanTypeID = p.contractType or p.contractType=-1)\n");//合同类型
            if(info.getEnterpriseTypeID1() > 0)
            	buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1());//客户属性1
            if(info.getEnterpriseTypeID2() > 0)
            	buffer.append(" and v.clientTypeID2 = " + info.getEnterpriseTypeID2());//客户属性2
            if(info.getEnterpriseTypeID3() > 0)
            	buffer.append(" and v.clientTypeID3 = " + info.getEnterpriseTypeID3());//客户属性3
            if(info.getEnterpriseTypeID4() > 0)
            	buffer.append(" and v.clientTypeID4 = " + info.getEnterpriseTypeID4());//客户属性4
            if(info.getEnterpriseTypeID5() > 0)
            	buffer.append(" and v.clientTypeID5 = " + info.getEnterpriseTypeID5());//客户属性5
            if(info.getEnterpriseTypeID6() > 0)
            	buffer.append(" and v.clientTypeID6 = " + info.getEnterpriseTypeID6());//客户属性6
            buffer.append("\n group by v.clientTypeID1, v.clientTypeID2, v.clientTypeID3, v.clientTypeID4, v.clientTypeID5, v.clientTypeID6, projectID \n");//将按照不同的查询进行不同的分组
            buffer.append(" )bb, \n");
            buffer.append("	(select * from SETT_RELEASEAMOUNTLIMITSETTING where statusid = 1 \n");
            buffer.append(" and CurrencyID = " + info.getCurrencyID() + " and OfficeID = " + info.getOfficeID() + " \n");            
            buffer.append(" and clientSettingFlag = 2) r \n");
            buffer.append(" WHERE aa.projectID = bb.projectID(+) \n");
            buffer.append(" AND aa.clientTypeID = bb.clientTypeID(+) \n");            
            buffer.append(" AND aa.projectID = r.projectID(+) \n");
            buffer.append(" AND aa.clientTypeID = r.clientType(+) \n"); 
            if(info.getClientType() > -1)
                buffer.append(" AND aa.clientTypeID = " + info.getClientType() + "\n");
            if(info.getProjectID() > -1)
                buffer.append(" AND aa.projectID = " + info.getProjectID() + "\n");   
            //担保手续费
            //某客户某时间范围内的担保手续费=该客户该时间范围（指收付款日期）内、状态是已审核的，收款通知单中的所有手续费之和—保后处理通知单中的所有手续费之和
            buffer.append(" UNION ALL \n");
            buffer.append(" SELECT aa.projectID, aa.clientTypeID, bb.amount, r.releaseAmountLimit, r.awokeRate \n");
            buffer.append(" FROM \n");
            buffer.append(" (select a.projectID,b.ID as clientTypeID from SETT_STOCKPROJECTSETTING a, SETT_ENTERPRICETYPE b \n");
            buffer.append("  where a.statusID = 1 and b.nStatusID = 1 and projectType = 2 \n");
            buffer.append("  and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n");
            buffer.append("  and a.accountType = -1 and a.contractType = 13 \n");//担保
            buffer.append("  and b.ID = 5 \n");
            if(info.getProjectID() > 0)
                buffer.append("  and a.projectID = " + info.getProjectID());
            buffer.append("  group by a.projectID, b.ID ) aa,\n");
            buffer.append(" (select sum(amount) amount, nCorpNatureID from \n");
            buffer.append("  (select ASSURECHARGEACCOUNTID as accountID, sum(ASSURECHARGEAMOUNT) as amount\n");
            buffer.append("   from (\n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID,ASSURECHARGEAMOUNT\n");
            buffer.append("   from Loan_AssureChargeForm where STATUSID = 3 \n");
            buffer.append("   union all\n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID, - ASSURECHARGEAMOUNT as ASSURECHARGEAMOUNT \n");//!
            buffer.append("   from Loan_AssureManagementForm where STATUSID = 3 \n");
            buffer.append("  ) \n");
            buffer.append("  where ASSURECHARGEACCOUNTID != -1 \n");

            if (info.getDateFrom() != null)
                buffer.append(" and EXECUTEDATE>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");
            if (info.getDateTo() != null)
                buffer.append(" and EXECUTEDATE<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");
            
            buffer.append("  group by ASSURECHARGEACCOUNTID)a, \n");
            buffer.append("  (select sett_account.ID as accountID, client.nCorpNatureID \n");
            buffer.append("   from sett_account,client \n");
            buffer.append("   where sett_account.nClientID = client.ID )b \n");
            buffer.append(" where a.accountID = b.accountID \n");
            buffer.append(" group by nCorpNatureID \n");
            buffer.append(" ) bb, \n");
            buffer.append(" (select * from SETT_RELEASEAMOUNTLIMITSETTING where statusid = 1 \n");
            buffer.append(" and CurrencyID = " + info.getCurrencyID() + " and OfficeID = " + info.getOfficeID() + "\n");
            buffer.append(" and clientSettingFlag = 2) r \n");
            buffer.append(" WHERE \n");
            buffer.append(" aa.clientTypeID = bb.nCorpNatureID(+) \n");
            buffer.append(" AND aa.projectID = r.projectID(+) \n");
            buffer.append(" AND aa.clientTypeID = r.clientType(+) \n");
            buffer.append(" ) \n");
            buffer.append(" group by projectID,clientTypeID,releaseAmountLimit,awokeRate \n");           
            //
            String strSQL = buffer.toString();
            log.debug(" 损益表项目分析 findByCondition sql = \n" + strSQL);
            prepareStatement(strSQL);
            executeQuery();
            Log.print("after executeQuery();");
            double amountSumTemp = 0.0;
            double raLimitSumTemp = 0.0;
            while (transRS.next())
            {            
                QueryStockCompanyStatisticsResultInfo tempInfo = new QueryStockCompanyStatisticsResultInfo();
                tempInfo.setAmount(transRS.getDouble("amount"));         
                tempInfo.setProjectID(transRS.getLong("projectID"));           
                tempInfo.setClientType(transRS.getLong("clientTypeID"));            
                tempInfo.setReleaseAmountLimit(transRS
                        .getDouble("releaseAmountLimit"));
                tempInfo.setAwokeRate(transRS.getDouble("awokeRate"));
                if (transRS.getDouble("releaseAmountLimit") != 0.0)//使用百分比
                    tempInfo.setUsedPercent(transRS.getDouble("amount")
                            / transRS.getDouble("releaseAmountLimit"));
                amountSumTemp = amountSumTemp + transRS.getDouble("amount");//累加金额
                raLimitSumTemp = raLimitSumTemp
                        + transRS.getDouble("releaseAmountLimit");//累加豁免额度                         
                c.add(tempInfo);
            }
            qscsSumInfo.setAmountSum(amountSumTemp);
            qscsSumInfo.setReleaseAmountLimitSum(raLimitSumTemp);       

            finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        return c;
    }  
    
    public Collection findByClientType(QueryStockCompanyStatisticsInfo info)
    throws Exception
    {    
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer(); 
            buffer.append(" select clientID, sum(amount) amount, projectID, releaseAmountLimit, awokeRate \n");
            buffer.append(" from \n");
            buffer.append(" ( \n");            
            buffer.append(" SELECT a.clientID, b.amount, " + info.getProjectID() + " AS projectID, r.releaseAmountLimit, r.awokeRate \n");
            buffer.append(" FROM \n");
            buffer.append(" (select distinct clientID from sett_vTransInterestDetail where corpNatureID = "+ info.getClientType() + " \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");
            buffer.append(" ) a, \n");
            buffer.append(" ( \n");
            buffer.append(" select sum(v.Interest) as amount, v.clientID, p.projectID \n");
            buffer.append(" from sett_vTransInterestDetail v, \n");
            buffer.append(" (select projectid,contractType,accountType,relateClientType,profitAndLossType from SETT_STOCKPROJECTSETTING \n");
            buffer.append(" where officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n");
            buffer.append(" and accountType != -1 and contractType != 13 \n"); ////非担保类型
            buffer.append(" and statusID = 1 ");
            buffer.append(" and projectID = "+ info.getProjectID() + ") p \n");
            
            buffer.append(" where v.CurrencyID = " + info.getCurrencyID() + " and v.OfficeID = " + info.getOfficeID() + " \n");
            buffer.append(" and v.IsRecord = 1 \n");//是否结息记录
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");//执行日
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");        
            buffer.append(" and '<' || replace(p.profitAndLossType,',','><') || '>'like '%<' || v.InterestTypeID || '>%' \n");
            buffer.append(" and v.AccountTypeID = p.accountType \n");
            buffer.append(" and (v.LoanTypeID = p.contractType or p.contractType=-1) \n");
            buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1() + " \n");//客户属性1
            buffer.append(" and v.clientTypeID2 = " + info.getEnterpriseTypeID2() + " \n");//客户属性2
            buffer.append(" and v.clientTypeID3 = " + info.getEnterpriseTypeID3() + " \n");//客户属性3
            buffer.append(" and v.clientTypeID4 = " + info.getEnterpriseTypeID4() + " \n");//客户属性4
            buffer.append(" and v.clientTypeID5 = " + info.getEnterpriseTypeID5() + " \n");//客户属性5
            buffer.append(" and v.clientTypeID6 = " + info.getEnterpriseTypeID6() + " \n");//客户属性6
            buffer.append(" group by v.ClientID, p.projectID) b, \n");
            buffer.append(" (select projectID,clientID,releaseAmountLimit,awokeRate \n");
            buffer.append(" from SETT_RELEASEAMOUNTLIMITSETTING \n");
            buffer.append(" where CurrencyID = " + info.getCurrencyID() + " and OfficeID = " + info.getOfficeID() + " \n");
            buffer.append(" and statusID = " + Constant.RecordStatus.VALID + " \n");
            buffer.append(" and clientSettingFlag = 1 \n");
            if(info.getProjectID() > -1)
                buffer.append(" and projectID = " + info.getProjectID() + " \n");
            buffer.append(" and clientType = " + info.getClientType() + ") r \n");
            buffer.append(" WHERE \n");
            buffer.append(" a.clientID = b.clientID(+) \n");
            buffer.append(" AND a.clientID = r.clientID(+)\n");
            //
            buffer.append(" UNION ALL \n");
            buffer.append(" SELECT a.clientID, b.amount, " + info.getProjectID() + " AS projectID, r.releaseAmountLimit, r.awokeRate \n");
            buffer.append(" FROM \n");
            buffer.append(" ( \n");
            buffer.append("  select distinct client.ID as clientID \n");
            buffer.append("  from \n");
            buffer.append("  ( \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID \n");
            buffer.append("   from Loan_AssureChargeForm where STATUSID = 3 \n");
            buffer.append("   union all \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID \n");
            buffer.append("   from Loan_AssureManagementForm where STATUSID = 3 \n");
            buffer.append("  ), sett_account,client \n");
            buffer.append("  where ASSURECHARGEACCOUNTID != -1 \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");        
            buffer.append("  and ASSURECHARGEACCOUNTID = sett_Account.ID \n");
            buffer.append("  and sett_account.nClientID = client.ID \n");
            buffer.append("  and client.nCorpNatureID = " + info.getClientType() + " \n");
            buffer.append("  ) a, \n");
            buffer.append("  ( \n");
            buffer.append("  select sum(v.amount) as amount, v.clientID, p.projectID \n");
            buffer.append("  from \n");
            buffer.append("  ( \n");
            buffer.append("   select sum(amount) amount, client.ID as clientID, client.nCorpNatureID from \n");
            buffer.append("   (select ASSURECHARGEACCOUNTID as accountID, sum(ASSURECHARGEAMOUNT) as amount \n");
            buffer.append("   from ( \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID,ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureChargeForm where STATUSID = 3 \n");
            buffer.append("   union all \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID, - ASSURECHARGEAMOUNT as ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureManagementForm where STATUSID = 3 \n");
            buffer.append("   	   )\n");
            buffer.append("   where ASSURECHARGEACCOUNTID != -1 \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");      
            buffer.append("   group by ASSURECHARGEACCOUNTID \n");
            buffer.append("   )c, sett_account, client \n");
            buffer.append("   where sett_account.nClientID = client.ID \n");
            
            buffer.append("   and c.accountID = sett_account.ID \n");
            buffer.append("   group by client.ID, client.nCorpNatureID \n");
            buffer.append("  ) v, \n");
            buffer.append("  (select projectid,contractType,accountType,relateClientType,profitAndLossType \n");
            buffer.append("   from SETT_STOCKPROJECTSETTING where \n");
            buffer.append("   officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n");
            buffer.append("   and statusID = 1  and projectID = " + info.getProjectID() + " \n");
            buffer.append("   and accountType = -1 and contractType = 13 ) p \n");//担保
            buffer.append("   where 1=1 \n");
        //  buffer.append("   and v.nCorpNatureID = " + info.getClientType() + " \n");
            buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1() + " \n");//客户属性1
            buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1() + " \n");//客户属性1
            buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1() + " \n");//客户属性1
            buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1() + " \n");//客户属性1
            buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1() + " \n");//客户属性1
            buffer.append(" and v.clientTypeID1 = " + info.getEnterpriseTypeID1() + " \n");//客户属性1
            buffer.append("   group by v.ClientID, p.projectID \n");
            buffer.append("   ) b, \n");
            buffer.append("   (select projectID,clientID,releaseAmountLimit,awokeRate \n");
            buffer.append("   from SETT_RELEASEAMOUNTLIMITSETTING \n");
            buffer.append("   where CurrencyID = " + info.getCurrencyID() + " and OfficeID = " + info.getOfficeID() + " \n");
            buffer.append("   and statusID = 1 and clientSettingFlag = 1 \n");
            if(info.getProjectID() > -1)
                buffer.append(" and projectID = " + info.getProjectID() + " \n");
            buffer.append(" and clientType = " + info.getClientType() + ") r \n");
            buffer.append("   WHERE \n");
            buffer.append("   a.clientID = b.clientID(+) \n");            
            buffer.append("   AND a.clientID = r.clientID(+) \n");
            buffer.append("   ) \n");
            buffer.append("   group by clientID,projectID,releaseAmountLimit,awokeRate \n");
                       
            String strSQL = buffer.toString();
            log.debug(" 损益表项目分析 findByClientType sql = \n" + strSQL);
            prepareStatement(strSQL);
            executeQuery();
            Log.print("after executeQuery();");
            double amountSumTemp = 0.0;
            double raLimitSumTemp = 0.0;
            while (transRS.next())
            {   
                QueryStockCompanyStatisticsResultInfo tempInfo = new QueryStockCompanyStatisticsResultInfo();
                tempInfo.setClientID(transRS.getLong("clientID")); 
                tempInfo.setProjectID(transRS.getLong("projectID"));
                tempInfo.setAmount(transRS.getDouble("amount"));
                tempInfo.setReleaseAmountLimit(transRS.getDouble("releaseAmountLimit")); 
                tempInfo.setAwokeRate(transRS.getDouble("awokeRate"));
                amountSumTemp = amountSumTemp + transRS.getDouble("amount");//累加金额
                raLimitSumTemp = raLimitSumTemp
                        + transRS.getDouble("releaseAmountLimit");//累加豁免额度
                if (transRS.getDouble("releaseAmountLimit") != 0.0)//使用百分比
                    tempInfo.setUsedPercent(transRS.getDouble("amount")
                            / transRS.getDouble("releaseAmountLimit"));
                c.add(tempInfo);
            }
            qscsSumInfo.setAmountSum(amountSumTemp);
            qscsSumInfo.setReleaseAmountLimitSum(raLimitSumTemp);

            finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    public Collection findByProject(QueryStockCompanyStatisticsInfo info)
    throws Exception
    {    
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append(" SELECT SUM(v.Interest) AS amount, " + info.getClientType() + " AS corpNatureID, p.* \n");//
            buffer.append(" FROM \n");
            buffer.append(" (select projectid,contractType,accountType,relateClientType,profitAndLossType from SETT_STOCKPROJECTSETTING  \n");
            buffer.append(" where projectID = " + info.getProjectID() + " and statusID=1 \n"); 
            buffer.append(" and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID());            
            buffer.append(" and contractType != 13 and accountType != -1 ) \n");            
            buffer.append("	p, \n");
            
            buffer.append("	(select * from sett_vTransInterestDetail WHERE \n");
            buffer.append(" CurrencyID = " + info.getCurrencyID());
            buffer.append(" and OfficeID = " + info.getOfficeID());
            buffer.append(" and IsRecord = 1 \n");//是否结息记录
            buffer.append(" and corpNatureID = " + info.getClientType() + " \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");//执行日
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");  
            buffer.append(" ) v \n");
            buffer.append(" WHERE 1=1 \n");                      
            //buffer.append(" AND '<' || replace(p.profitAndLossType,',','><') || '>'like '%<' || v.InterestTypeID || '>%' \n");//损益类型
            buffer.append(" AND p.accountType = v.AccountTypeID(+) \n");//账户类型
            buffer.append(" AND p.contractType = v.LoanTypeID(+) \n");//合同类型            
            buffer.append("\n GROUP BY v.clientTypeID1, v.clientTypeID2, v.clientTypeID3, v.clientTypeID4, v.clientTypeID5, v.clientTypeID6, p.projectid, p.contractType, p.accountType, p.relateClientType, p.profitAndLossType \n");//将按照不同的查询进行不同的分组
            buffer.append(" UNION ALL \n");
            buffer.append(" SELECT SUM(v.amount) AS amount, 3 AS corpNatureID, p.* \n");
            buffer.append(" FROM \n");
            buffer.append(" (select projectid,contractType,accountType,relateClientType,profitAndLossType from SETT_STOCKPROJECTSETTING  \n");
            buffer.append(" where projectID = " + info.getProjectID() + " and statusID=1 \n"); 
            buffer.append(" and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID());            
            buffer.append(" and contractType = 13 and accountType = -1 ) \n");            
            buffer.append("	p, \n");
            buffer.append(" ( \n");
            buffer.append(" select sum(amount) amount, client.nCorpNatureID from \n");
            buffer.append("  (select ASSURECHARGEACCOUNTID as accountID, sum(ASSURECHARGEAMOUNT) as amount \n");
            buffer.append("   from ( \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID,ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureChargeForm where STATUSID = 3 \n");
            buffer.append("   union all \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID, - ASSURECHARGEAMOUNT as ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureManagementForm where STATUSID = 3 \n");
            buffer.append("  ) \n");
            buffer.append("  where ASSURECHARGEACCOUNTID != -1 \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");  
            buffer.append("  group by ASSURECHARGEACCOUNTID \n");
            buffer.append("  )a, sett_account, client \n");
            buffer.append("  where sett_account.nClientID = client.ID \n");
            buffer.append("  and a.accountID = sett_account.ID \n");
            buffer.append("  group by client.nCorpNatureID \n");
            buffer.append(" ) v \n");
        //  buffer.append(" GROUP BY v.nCorpNatureID, p.projectid, p.contractType, p.accountType, p.relateClientType, p.profitAndLossType \n");
            buffer.append(" GROUP BY v.clientTypeID1, v.clientTypeID2, v.clientTypeID3, v.clientTypeID4, v.clientTypeID5, v.clientTypeID6, p.projectid, p.contractType, p.accountType, p.relateClientType, p.profitAndLossType \n");
            String strSQL = buffer.toString();
            log.debug(" 损益表项目分析 findByProject sql = \n" + strSQL);
            prepareStatement(strSQL);
            executeQuery();
            Log.print("after executeQuery();");
            double amountSumTemp = 0.0;
            double raLimitSumTemp = 0.0;
            while (transRS.next())
            {            
                QueryStockCompanyStatisticsResultInfo tempInfo = new QueryStockCompanyStatisticsResultInfo();
                tempInfo.setAmount(transRS.getDouble("amount"));         
                tempInfo.setProfitAndLossType(transRS.getString("profitAndLossType"));           
                tempInfo.setClientType(transRS.getLong("corpNatureID"));
                tempInfo.setAccountType(transRS.getLong("accountType"));
                tempInfo.setContractType(transRS.getLong("contractType"));
                tempInfo.setRelateClientType(transRS.getLong("relateClientType"));
                amountSumTemp = amountSumTemp + transRS.getDouble("amount");//累加金额    
                       
                c.add(tempInfo);
            }
            qscsSumInfo.setAmountSum(amountSumTemp);
            qscsSumInfo.setReleaseAmountLimitSum(raLimitSumTemp);        
            finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    //输出从起始日期到终止日期，该客户类型下每一个客户，该账户类型/合同类型/损益类型/有关客户的汇总结息金额
    public Collection findByProjectAndClientType(QueryStockCompanyStatisticsInfo info)
    throws Exception
    {    
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();            
            buffer.append(" SELECT a.clientID,sum(v.Interest) AS amount,projectid,contractType,accountType,relateClientType,profitAndLossType \n");//
            buffer.append(" FROM \n");
            buffer.append(" (select distinct clientID from sett_vTransInterestDetail  \n");
            buffer.append("  where corpNatureID = " + info.getClientType() + " and IsRecord = 1 \n");
            buffer.append("  and CurrencyID = " + info.getCurrencyID() + " and OfficeID = " + info.getOfficeID() + " \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");//执行日
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n"); 
            buffer.append(" ) a, \n");
            buffer.append(" (select * from sett_vTransInterestDetail \n");
            buffer.append(" where CurrencyID = " + info.getCurrencyID() + " and OfficeID = " + info.getOfficeID() + "\n");
            buffer.append(" and IsRecord = 1 \n");//是否结息记录
            if(info.getClientID() > -1)
                buffer.append(" and ClientID = " + info.getClientID() + " \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");//执行日
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n"); 
            if(info.getClientType() > -1)
                buffer.append(" and corpNatureID = " + info.getClientType());//客户类型
            buffer.append(" )v, \n");
            buffer.append(" (select projectid,contractType,accountType,relateClientType,profitAndLossType from SETT_STOCKPROJECTSETTING where  ");
            buffer.append(" officeID = " + info.getOfficeID());
            buffer.append(" and currencyID = " + info.getCurrencyID());
            buffer.append(" and projectID = " + info.getProjectID() + " \n"); 
            if(info.getAccountType() > -1)
                buffer.append(" and accountType = " + info.getAccountType() + " \n");
            if(info.getContractType() > -1)
                buffer.append(" and contractType = " + info.getContractType() + " \n");
            if(info.getRelateClientType() > -1)
                buffer.append(" and relateClientType = " + info.getRelateClientType() + " \n");                
            buffer.append(" and statusID=1 ) ");
            buffer.append("	p  \n");            
            buffer.append(" WHERE \n");
            buffer.append(" a.clientID = v.clientID(+) \n");
            buffer.append(" AND '<' || replace(p.profitAndLossType,',','><') || '>'like '%<' || v.InterestTypeID || '>%' \n");//损益类型
            buffer.append(" AND v.AccountTypeID = p.accountType \n");//账户类型
            buffer.append(" AND (v.LoanTypeID = p.contractType or p.contractType=-1) \n");//合同类型
            buffer.append(" GROUP BY a.ClientID, p.projectid, p.contractType, p.accountType, p.relateClientType, p.profitAndLossType \n");//将按照不同的查询进行不同的分组
            buffer.append(" UNION ALL \n");
            buffer.append(" select client.ID clientID, sum(ASSURECHARGEAMOUNT) as amount, \n");            
            buffer.append(" " + info.getProjectID() + " as projectID, 13 as contractType, \n");
            buffer.append("  -1 as accountType,1 as relateClientType,'4' as profitAndLossType \n");
            buffer.append("   from ( \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID,ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureChargeForm where STATUSID = 3 \n");
            buffer.append("   union all \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID, - ASSURECHARGEAMOUNT as ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureManagementForm where STATUSID = 3 \n");
            buffer.append("   ) a,sett_account,client \n");
            buffer.append("  where ASSURECHARGEACCOUNTID != -1\n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n"); 
            buffer.append("  and client.nCorpNatureID = " + info.getClientType() + " \n");
            buffer.append("  and  a.ASSURECHARGEACCOUNTID = sett_account.ID \n");
            buffer.append("  and sett_account.nClientID = client.ID \n");
            buffer.append("  group by client.ID \n");
            
            String strSQL = buffer.toString();
            log.debug(" 损益表项目分析 findByProjectAndClientType sql = \n" + strSQL);
            prepareStatement(strSQL);
            executeQuery();
            Log.print("after executeQuery();");
            double amountSumTemp = 0.0;
            
            while (transRS.next())
            {            
                QueryStockCompanyStatisticsResultInfo tempInfo = new QueryStockCompanyStatisticsResultInfo();
                tempInfo.setClientID(transRS.getLong("clientID")); 
                tempInfo.setProjectID(transRS.getLong("projectID"));
                tempInfo.setAmount(transRS.getDouble("amount"));                
                tempInfo.setAccountType(transRS.getLong("accountType"));
                tempInfo.setContractType(transRS.getLong("contractType"));
                tempInfo.setProfitAndLossType(transRS.getString("profitAndLossType"));
                tempInfo.setRelateClientType(transRS.getLong("relateClientType"));
                amountSumTemp = amountSumTemp + transRS.getDouble("amount");//累加金额
                
                c.add(tempInfo);
            }
            qscsSumInfo.setAmountSum(amountSumTemp);            

            finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    //输出从起始日期到终止日期，该客户下每一个账户类型/合同类型/损益类型/有关客户的汇总结息金额
    public Collection findByClientTypeAndProject(QueryStockCompanyStatisticsInfo info)
    throws Exception
    {    
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();            
            
            buffer.append(" SELECT "  + info.getClientID() + " AS ClientID, sum(v.Interest) AS amount, \n");
            buffer.append(" projectID,contractType,accountType,relateClientType,profitAndLossType \n");
            buffer.append(" FROM \n");
            buffer.append(" (select projectID,contractType,accountType,relateClientType,profitAndLossType \n");
            buffer.append("  from SETT_STOCKPROJECTSETTING where \n");            
            buffer.append(" projectID = " + info.getProjectID() + " and accountType != -1 and contractType != 13 \n"); 
            buffer.append(" and officeID = " + info.getOfficeID());
            buffer.append(" and currencyID = " + info.getCurrencyID());
            buffer.append(" and statusID=1 ) ");
            buffer.append("	p, \n");
            buffer.append(" (select * from sett_vTransInterestDetail \n");
            buffer.append(" where CurrencyID = " + info.getCurrencyID() + " and OfficeID = " + info.getOfficeID() + "\n");
            buffer.append(" and IsRecord = 1 \n");//是否结息记录
            if(info.getClientID() > -1)
                buffer.append(" and ClientID = " + info.getClientID() + " \n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");//执行日
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n"); 
            if(info.getClientType() > -1)
                buffer.append(" and corpNatureID = " + info.getClientType());//客户类型
            buffer.append(" )v \n");            
            buffer.append(" WHERE \n");
            buffer.append(" p.contractType = v.LoanTypeID(+) \n");
            buffer.append(" AND p.accountType = v.AccountTypeID(+) \n");
            //buffer.append(" AND '<' || replace(p.profitAndLossType,',','><') || '>'like '%<' || v.InterestTypeID || '>%' \n");//损益类型
                      
            buffer.append("\n GROUP BY p.projectid, p.contractType, p.accountType, p.relateClientType, p.profitAndLossType \n");//将按照不同的查询进行不同的分组
            buffer.append(" UNION ALL \n");
            buffer.append(" select " + info.getClientID() + " as clientID, sum(ASSURECHARGEAMOUNT) as amount, \n");            
            buffer.append(" " + info.getProjectID() + " as projectID, 13 as contractType, \n");
            buffer.append("  -1 as accountType,1 as relateClientType,'4' as profitAndLossType \n");
            buffer.append("   from ( \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID,ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureChargeForm where STATUSID = 3 \n");
            buffer.append("   union all \n");
            buffer.append("   select EXECUTEDATE,ASSURECHARGEACCOUNTID, - ASSURECHARGEAMOUNT as ASSURECHARGEAMOUNT \n");
            buffer.append("   from Loan_AssureManagementForm where STATUSID = 3 \n");
            buffer.append("   ) a,sett_account \n");
            buffer.append("  where ASSURECHARGEACCOUNTID != -1\n");
            if (info.getDateFrom() != null)
                buffer.append(" and ExecuteDate>=to_date('"
                        + DataFormat.getDateString(info.getDateFrom())
                        + "','yyyy-mm-dd') \n");
            if (info.getDateTo() != null)
                buffer.append(" and ExecuteDate<=to_date('"
                        + DataFormat.getDateString(info.getDateTo())
                        + "','yyyy-mm-dd') \n");
            buffer.append("  and  a.ASSURECHARGEACCOUNTID = sett_account.ID \n");
            buffer.append("  and sett_account.nClientID = " + info.getClientID() + " \n");            
            String strSQL = buffer.toString();
            log.debug(" 损益表项目分析 findByClientTypeAndProject sql = \n" + strSQL);
            prepareStatement(strSQL);
            executeQuery();
            Log.print("after executeQuery();");
            double amountSumTemp = 0.0;
            
            while (transRS.next())
            {            
                QueryStockCompanyStatisticsResultInfo tempInfo = new QueryStockCompanyStatisticsResultInfo();
                tempInfo.setClientID(transRS.getLong("clientID")); 
                tempInfo.setProjectID(transRS.getLong("projectID"));
                tempInfo.setAmount(transRS.getDouble("amount"));                
                tempInfo.setAccountType(transRS.getLong("accountType"));
                tempInfo.setContractType(transRS.getLong("contractType"));
                tempInfo.setProfitAndLossType(transRS.getString("profitAndLossType"));
                tempInfo.setRelateClientType(transRS.getLong("relateClientType"));
                amountSumTemp = amountSumTemp + transRS.getDouble("amount");//累加金额
                
                c.add(tempInfo);
            }
            qscsSumInfo.setAmountSum(amountSumTemp);            

            finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        return c;
    }

    public QueryStockCompanyStatisticsSumInfo getSumInfo()
    {
        return this.qscsSumInfo;
    }   
}