/*
 * Created on 2005-12-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.query.dao;

import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.loan.query.dataentity.RepurchaseFormDetailInfo;
import com.iss.system.dao.SqlUtil;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryRepurchaseFormDetailDao extends LoanDAO{
    public QueryRepurchaseFormDetailDao() {
        super("");
        // TODO Auto-generated constructor stub
    }
    
    public RepurchaseFormDetailInfo[] queryRepurchaseFormDetail(long repurchaseFormId) throws Exception{
        StringBuffer strSql=new StringBuffer();
        strSql.append("select loan_RepurchaseForm.code as repurchaseCode,\n");
        strSql.append("loan_RepurchaseForm.bankId as repurchaseBankId,\n");
        strSql.append("loan_RepurchaseForm.amount as repurchaseAmount,\n");
        strSql.append("loan_RepurchaseForm.rate as repurchaseRate,\n");
        strSql.append("loan_RepurchaseForm.startDate as repurchaseStartDate,\n");
        strSql.append("loan_RepurchaseForm.endDate as repurchaseEndDate,\n");
        strSql.append("userInfo.sName as userName,\n");
        strSql.append("loan_RepurchaseForm.inputDate as repurchaseInputDate,\n");
        strSql.append("loan_RepurchaseForm.statusId as repurchaseStatusId,\n");
        strSql.append("loan_RepurchaseForm.remark as repurchaseRemark,\n");
        strSql.append("loan_RepurchaseForm.repurchaseDate as repurchaseDate,\n");
        strSql.append("loan_ContractForm.id as contractId,\n");
        strSql.append("loan_ContractForm.sContractCode as contractCode,\n");
        strSql.append("client.sName as clientName,\n");
        strSql.append("loan_ContractForm.nTypeId as contractTypeId,\n");
        strSql.append("loan_ContractForm.dtStartDate as contractStartDate,\n");
        strSql.append("loan_ContractForm.dtEndDate as contractEndDate,\n");
        strSql.append("loan_ContractForm.mExamineAmount as contractExamineAmount \n");
        strSql.append("from loan_RepurchaseForm,loan_ContractForm,client, \n");
        strSql.append("loan_RepurchaseItem,userInfo \n");
        strSql.append("where loan_RepurchaseForm.id="+repurchaseFormId+" \n");
        strSql.append("and loan_RepurchaseForm.id=loan_RepurchaseItem.repurchaseId \n");
        strSql.append("and loan_RepurchaseItem.contractId=loan_ContractForm.id \n");
        strSql.append("and loan_ContractForm.nBorrowClientId=client.id \n");
        strSql.append("and loan_RepurchaseForm.inputUserId=userInfo.id \n");
        RepurchaseFormDetailInfo[] result=null;
        try {
            this.initDAO();
            this.prepareStatement(strSql.toString());
            this.executeQuery();
            result=(RepurchaseFormDetailInfo[])SqlUtil.parseDataEntityBeans(this.transRS,"","com.iss.itreasury.loan.query.dataentity.RepurchaseFormDetailInfo");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        finally{
            this.finalizeDAO();
        }
        return result;
    }
}
