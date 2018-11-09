/*
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.dao;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.SqlUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report1Dao extends BaseQueryObject {

    private Log4j logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

 
    private String createSqlToCreateVirtualDateColumn() {
        StringBuffer result = new StringBuffer();
        result.append("(");
        result.append("select '01' as day from dual \n");
        for (int i = 2; i <= 9; i++) {
            result.append("union select '" + "0" + i + "' from dual \n");
        }
        for (int i = 10; i <= 31; i++) {
            result.append("union select '" + i + "' from dual \n");
        }
        result.append(") \n");
        return result.toString();
    }

    private String createSqlToVerifyDate(String dayColumnName,
            Timestamp firstDay, Timestamp lastDay) {
        StringBuffer result = new StringBuffer();
        result.append("and to_char(" + dayColumnName + ",'yyyy-mm-dd')>=\n");
        result.append("'"+DataFormat.getDateString(firstDay) + "' \n");
        result.append("and to_char(" + dayColumnName + ",'yyyy-mm-dd')<=\n");
        result.append("'"+DataFormat.getDateString(lastDay) + "' \n");
        return result.toString();
    }

    private String createSqlToVerifyBranchStatus(QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and sett_Branch.nCurrencyId=" + qInfo.getCurrencyID()
                + " \n");
        result.append("and sett_Branch.nOfficeId=" + qInfo.getOfficeID()
                + " \n");
        result.append("and sett_Branch.nStatusId=1 \n");
        return result.toString();
    }

    private String createSqlForBranchDateColumn(Timestamp date,
            QueryFixedDepositInfo qInfo, String bankAccountCodes) {
        StringBuffer result = new StringBuffer();
        result
                .append("(select substr(to_char(sett_BalanceOfBankAccount.dtCurrent,'yyyy-mm-dd'),9,2) as day,\n");
        result
                .append("sum(nvl(sett_BalanceOfBankAccount.mBalance,0)) as balance \n");
        result.append("from sett_BalanceOfBankAccount,sett_Branch \n");
        result
                .append("where sett_Branch.sBankAccountCode=sett_BalanceOfBankAccount.sBankAccountNo \n");
        result.append("and sett_Branch.sBankAccountCode in ("
                + bankAccountCodes + ") \n");
        result.append(createSqlToVerifyBranchStatus(qInfo));
        result.append(createSqlToVerifyDate(
                "sett_BalanceOfBankAccount.dtCurrent", DataFormat
                        .getFirstDateOfMonth(date), DataFormat
                        .getLastDateOfMonth(date)));
        result
                .append("group by substr(to_char(sett_BalanceOfBankAccount.dtCurrent,'yyyy-mm-dd'),9,2)) \n");
        return result.toString();
    }

    public ReportResultInfo[] queryBankDepositBalanceDetail(Timestamp date, QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer strSQL = new StringBuffer();
        strSQL.append("select aa.day as StringColumn1 \n"); //StringColumn1--日期
        for (int i = 1; i <= 9; i++) {
            strSQL.append(",bb" + i + ".balance as DoubleColumn" + i + " \n");
        }//DoubleColumni--中国人民银行，中国工商银行等8种银行对应的每日余额
        strSQL.append("from \n");
        strSQL.append(createSqlToCreateVirtualDateColumn()); //建立日期虚拟表day 1--31
        strSQL.append("aa, \n");
        StringBuffer bankAccountCodes=new StringBuffer();
        bankAccountCodes.append("'-1' \n");
        strSQL.append(createSqlForBranchDateColumn(date,qInfo,bankAccountCodes.toString())); //中国人民银行
        strSQL.append("bb1, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'0200001919223001243', \n");
        bankAccountCodes.append("'0200004919027310217', \n");
        bankAccountCodes.append("'2403020619999999811' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //中国工商银行
        strSQL.append("bb2, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'65100031250730003' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //中国建设银行
        strSQL.append("bb3, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'11001085400059730001' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //基建北京
        strSQL.append("bb4, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'52001624236052500303' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //基建贵州
        strSQL.append("bb5, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'083504120100408000249' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //中国光大银行
        strSQL.append("bb6, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'0103014040000250' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //中国民生银行
        strSQL.append("bb7, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'-1' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //-1--其它一般
        strSQL.append("bb8, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'-1' \n");
        strSQL.append(createSqlForBranchDateColumn(date, qInfo, bankAccountCodes.toString())); //-1--其它基建
        strSQL.append("bb9 \n");
        strSQL.append("where 1=1 \n");
        for (int i = 1; i <= 9; i++) {
            strSQL.append("and aa.day=bb" + i + ".day(+) \n");
        }
        strSQL.append("\n order by to_number(aa.day) \n");
        System.out.println(strSQL.toString());
        ReportResultInfo[] results = null;
        try {
            initDAO();
            prepareStatement(strSQL.toString());
            executeQuery();
            results = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(transRS, "",
                            "com.iss.itreasury.settlement.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            throw e;
        } finally {
            finalizeDAO();
        }
        return results;

    }

    public static void main(String[] args) {
        Report1Dao dao = new Report1Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp date=DataFormat.getDateTime("2005-09-10");
        ReportResultInfo[] infos = null;
        try {
            infos = dao.queryBankDepositBalanceDetail(date, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        for (int i = 0; i < infos.length; i++) {
            ReportResultInfo info1 = infos[i];
            System.out.println(info1.getDoubleColumn6());
        }

    }
}