/*
 * Created on 2005-10-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.dao;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

import java.sql.Timestamp;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.system.dao.*;

public class Report10Dao extends BaseQueryObject {
    public static final int SEGMENT_DEPOSITSTRUCTURE = 1;//存款结构节点

    public static final int SEGMENT_ATTERMMONTH = 2;//到期月节点

    private String createSqlForClientTableSegment() {
        StringBuffer result = new StringBuffer();
        result.append("(select client.sName as clientName, \n");
        result.append("client.id as clientId \n");
        result.append("from client) \n");
        return result.toString();
    }
    
    private String createSqlForDepositStructureSegment(Timestamp date, int conditionOfMonth,
            int segmentType, QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");//单位名称
        result
                .append("sum(nvl(dailyAcct.mBalance/10000,0)) as balance \n");//本金余额
        result
                .append("from sett_account acct, sett_subAccount subAcct, client, \n");
        result.append("SETT_DAILYACCOUNTBALANCE dailyAcct \n");
        result
                .append("where subAcct.nAccountID=acct.id and acct.nclientid=client.id  \n");
        result.append("and dailyAcct.NSUBACCOUNTID=subAcct.Id \n");
        if (segmentType == SEGMENT_DEPOSITSTRUCTURE) {
        result.append("and subAcct.af_ndepositterm=" + conditionOfMonth + " \n");
        }else{
            Timestamp firstDayOfNextNMonth=DataFormat.getFirstDateOfMonth(DataFormat.getNextMonth(date,conditionOfMonth));
            result.append("and subAcct.AF_dtEnd>=to_date('"+DataFormat.getDateString(firstDayOfNextNMonth)+"','yyyy-mm-dd') \n"); 
            result.append("and subAcct.AF_dtEnd<to_date('"+DataFormat.getDateString(DataFormat.getNextMonth(firstDayOfNextNMonth,1))+"','yyyy-mm-dd') \n");
        }
        result.append("and to_char(dailyAcct.dtDate,'yyyy-mm-dd')='"
                + DataFormat.formatDate(date) + "' \n");
        result.append("and acct.nAccountTypeID in ("
                + getFixAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
        result.append("and acct.nofficeid=" + qInfo.getOfficeID() + " \n");
        result.append("and acct.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
        result.append("group by client.id) \n");
        return result.toString();
    }


    public ReportResultInfo[] queryFixedDepositStructure(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select aa.clientName as StringColumn1 \n");
        for(int i=1;i<=3;i++){
            strSql.append(",bb"+i+".balance as DoubleColumn"+i+" \n");
        }
        for(int i=1;i<=12;i++){
            strSql.append(",cc"+i+".balance as DoubleColumn"+(i+3)+" \n");    
        }
        strSql.append("from \n");
        strSql.append(createSqlForClientTableSegment());
        strSql.append("aa, \n");
        strSql.append(createSqlForDepositStructureSegment(date,3,SEGMENT_DEPOSITSTRUCTURE,qInfo));//3个月的结构
        strSql.append("bb1, \n");
        strSql.append(createSqlForDepositStructureSegment(date,6,SEGMENT_DEPOSITSTRUCTURE,qInfo));//6个月的结构
        strSql.append("bb2, \n");
        strSql.append(createSqlForDepositStructureSegment(date,12,SEGMENT_DEPOSITSTRUCTURE,qInfo));//12个月的结构
        strSql.append("bb3 \n");
        for(int i=1;i<=12;i++){
            strSql.append(", \n");
            strSql.append(createSqlForDepositStructureSegment(date,i,SEGMENT_ATTERMMONTH,qInfo));//第i个月到期
            strSql.append("cc"+i+" \n");
        }
        strSql.append("where 1=1 \n");
        for(int i=1;i<=3;i++){
            strSql.append("and aa.clientId=bb"+i+".clientId(+) \n");
        }
        for(int i=1;i<=12;i++){
            strSql.append("and aa.clientId=cc"+i+".clientId(+) \n");
        }
        strSql.append("order by aa.clientId \n");
        System.out.println(strSql.toString());
        ReportResultInfo[] result = null;
        try {
            this.initDAO();
            this.prepareStatement(strSql.toString());
            this.executeQuery();
            result = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(transRS, "",
                            "com.iss.itreasury.settlement.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            throw e;
        } finally {
            this.finalizeDAO();
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Report10Dao dao = new Report10Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts = DataFormat.getDateTime("2005-10-11");
        try {
            dao.queryFixedDepositStructure(ts, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}