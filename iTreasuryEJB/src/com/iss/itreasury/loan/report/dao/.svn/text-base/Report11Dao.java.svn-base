/*
 * Created on 2005-12-08
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.dao;

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
public class Report11Dao extends LoanDAO {

    public Report11Dao() {
        super("");
        // TODO Auto-generated constructor stub
    }

    private String CreateSqlToVerifyContractformStatus(ReportWhereInfo wInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and loan_ContractForm.nCurrencyId="
                + wInfo.getCurrencyId() + " \n");
        result.append("and loan_ContractForm.nOfficeId=" + wInfo.getOfficeId()
                + " \n");
        result.append("and (loan_ContractForm.nStatusId in (6,7,8,9) \n");
        result.append("or (loan_ContractForm.nStatusId=5 \n");
        result.append("and loan_RiskLevel.nStatusId=2 \n");
        result.append("and loan_RiskLevel.nChangeLevel<>"+LOANConstant.VentureLevel.A+")) \n");
        return result.toString();
    }
     
    private String CreateSqlForExtendColumn(boolean isAtTermDayColumn){
        StringBuffer result=new StringBuffer();
        result.append("(select loan_ContractForm.id as id, \n");
        if(isAtTermDayColumn){
            result.append("max(loan_ExtendDetail.dtExtendEndDate) as myDate \n");
        }
        else{
            result.append("count(loan_ExtendDetail.id) as myCount \n");
        }
        result.append("from loan_ContractForm, \n");
        result.append("loan_ExtendForm,loan_ExtendDetail \n");
        result.append("where loan_ExtendForm.nStatusId in (3,5) \n");
        result.append("and loan_ExtendDetail.nExtendFormId=loan_ExtendForm.id \n");
        result.append("and loan_ExtendForm.nContractId=loan_ContractForm.id \n");
        result.append("group by loan_ContractForm.id) \n");
        return result.toString();
    }

    /**
     * 查询财务公司高风险贷款明细
     * 
     * @param wInfo
     * @return
     * @throws Exception
     */
    public ReportResultInfo[] queryTreasuryCompanyHignRiskLoanDetailOfAssignedTypeId3(
            ReportWhereInfo wInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        //合同编号
        strSql
                .append("select loan_ContractForm.sContractCode as StringColumn1, \n");
        //单位名称
        strSql.append("client.sName as StringColumn2, \n");
        //合同id
        strSql.append("loan_ContractForm.id as LongColumn1, \n");
        //期限
        strSql.append("loan_ContractForm.nInterValnum as LongColumn2, \n");
        //贷款起始日
        strSql.append("loan_ContractForm.dtStartDate as TsColumn1, \n");
        //贷款到期日
        strSql.append("loan_ContractForm.dtEndDate as TsColumn2, \n");
        //展期到期日
        strSql.append("aa.myDate as TsColumn3, \n");
        //展期次数
        strSql.append("bb.myCount as LongColumn3, \n");
        //行业分类
        strSql.append("loan_ContractForm.nTypeId2 as LongColumn4, \n");
        //贷款分类
        strSql.append("loan_ContractForm.nSubTypeId as LongColumn5, \n");
        //担保方式
        strSql.append("loan_ContractForm.nIsCredit as LongColumn6, \n");
        strSql.append("loan_ContractForm.nIsAssure as LongColumn7, \n");
        strSql.append("loan_ContractForm.nIsImpawn as LongColumn8, \n");
        strSql.append("loan_ContractForm.nIsPledge as LongColumn9, \n");
        strSql.append("loan_ContractForm.isRecognizance as LongColumn10 \n");
        strSql.append("from loan_ContractForm,client,loan_RiskLevel, \n");
        strSql.append(CreateSqlForExtendColumn(true));
        strSql.append("aa, \n");
        strSql.append(CreateSqlForExtendColumn(false));
        strSql.append("bb \n");
        strSql.append("where 1=1 \n");
        strSql.append("and loan_ContractForm.nTypeId3=" + wInfo.getTypeId3()
                + " \n");
        strSql.append(CreateSqlToVerifyContractformStatus(wInfo));
        strSql.append("and loan_ContractForm.nBorrowClientId=client.id \n");
        strSql.append("and loan_ContractForm.id=loan_RiskLevel.nContractId(+) \n");
        strSql.append("and loan_ContractForm.id=aa.id(+) \n");
        strSql.append("and loan_ContractForm.id=bb.id(+) \n");
        strSql.append("order by loan_ContractForm.id \n");
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
}