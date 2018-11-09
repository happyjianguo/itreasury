/*
 * Created on 2005-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.dao;

import java.sql.Timestamp;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.SqlUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report3Dao extends LoanDAO {

    public Report3Dao() {
        super("");
        // TODO Auto-generated constructor stub
    }

    private String CreateSqlToVerifyContractformStatus(ReportWhereInfo wInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and loan_ContractForm.nCurrencyId="
                + wInfo.getCurrencyId() + " \n");
        result.append("and loan_ContractForm.nOfficeId=" + wInfo.getOfficeId()
                + " \n");
        result.append("and loan_ContractForm.nStatusId in (5,6,7,8,9) \n");
        return result.toString();
    }

    private String createSqlToVerifyDate(String dayColumnName,
            Timestamp firstDay, Timestamp lastDay) {
        StringBuffer result = new StringBuffer();
        result.append("and to_char(" + dayColumnName + ",'yyyy-mm-dd')>=\n");
        result.append("'" + DataFormat.getDateString(firstDay) + "' \n");
        result.append("and to_char(" + dayColumnName + ",'yyyy-mm-dd')<=\n");
        result.append("'" + DataFormat.getDateString(lastDay) + "' \n");
        return result.toString();
    }

    //取不包括担保的贷款类型id集合生成字串中间用","分隔
    private String getTypeIdsWithoutDB() {
        long[] typeIds = LOANConstant.LoanType.getAllCode();
        long[] typeIdsWithOutDB = new long[typeIds.length - 1];
        //筛选出不是担保的id集合
        for (int i = 0, j = 0; i < typeIds.length; i++) {
            if (typeIds[i] != LOANConstant.LoanType.DB) {
                typeIdsWithOutDB[j] = typeIds[i];
                j++;
            }
        }
        String result = "";
        if (typeIdsWithOutDB.length == 0) {
            return result;
        }
        result = Long.toString(typeIds[0]);
        if (typeIdsWithOutDB.length == 1) {
            return result;
        }
        for (int i = 1; i < typeIdsWithOutDB.length; i++) {
            result = result + "," + Long.toString(typeIdsWithOutDB[i]);
        }
        return result;
    }

    /**
     * 查询贷款每月到期明细
     * 
     * @param wInfo
     * @return ReportResultInfo[]
     * @throws Exception
     */
    public ReportResultInfo[] queryLoanAtTermDetailInOneMonth(
            ReportWhereInfo wInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select * from (");
        strSql.append("select sContractCode as StringColumn1, \n");
        strSql.append("client.sName as StringColumn2, \n");
        strSql.append("loan_ContractForm.nTypeId as LongColumn1, \n");
        strSql.append("mExamineAmount/10000 as DoubleColumn1, \n");
        strSql.append("dtEndDate as TsColumn1, \n");
        strSql.append("nTypeId3 as LongColumn2, \n");
        strSql.append("beneficiary as StringColumn3 \n");
        strSql.append("from loan_ContractForm,client \n");
        strSql.append("where nTypeId in ("+getTypeIdsWithoutDB()+") \n");
        strSql.append(createSqlToVerifyDate("dtEndDate",DataFormat.getFirstDateOfMonth(wInfo.getDate()),DataFormat.getLastDateOfMonth(wInfo.getDate())));
        strSql.append(CreateSqlToVerifyContractformStatus(wInfo));
        strSql.append("and loan_ContractForm.nBorrowClientId=client.id \n");
        strSql.append("order by dtEndDate) \n");
        strSql.append("union all \n");
        //上面是贷款类型，下面是担保类型
        strSql.append("select * from (");
        strSql.append("select sContractCode as StringColumn1, \n");
        strSql.append("client.sName as StringColumn2, \n");
        strSql.append("loan_ContractForm.nTypeId as LongColumn1, \n");
        strSql.append("mExamineAmount/10000 as DoubleColumn1, \n");
        strSql.append("dtEndDate as TsColumn1, \n");
        strSql.append("nTypeId3 as LongColumn2, \n");
        strSql.append("beneficiary as StringColumn3 \n");
        strSql.append("from loan_ContractForm,client \n");
        strSql.append("where nTypeId="+LOANConstant.LoanType.DB+"  \n");
        strSql.append(createSqlToVerifyDate("dtEndDate",DataFormat.getFirstDateOfMonth(wInfo.getDate()),DataFormat.getLastDateOfMonth(wInfo.getDate())));
        strSql.append(CreateSqlToVerifyContractformStatus(wInfo));
        strSql.append("and loan_ContractForm.nBorrowClientId=client.id \n");
        strSql.append("order by dtEndDate) \n"); 
        System.out.println(strSql.toString());
        ReportResultInfo[] result = null;
        try {
            this.initDAO();
            this.prepareStatement(strSql.toString());
            this.executeQuery();
            result = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(transRS, "",
                            "com.iss.itreasury.loan.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.finalizeDAO();
        }
        return result;
    }

    public static void main(String[] args) {

    }
}