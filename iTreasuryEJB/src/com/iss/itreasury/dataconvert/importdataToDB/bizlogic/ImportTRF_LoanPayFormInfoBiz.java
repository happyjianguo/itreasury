/*
 * Created on 2006-4-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_PayFormInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanPayFormInfo;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;
import com.iss.itreasury.dataconvert.util.TRF_Constant;
import com.iss.itreasury.dataconvert.util.TinyBeanUtil;


/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_LoanPayFormInfoBiz extends AbstractImportBiz{
    
    private Loan_PayFormInfo parseLoan_PayForm(TRF_LoanPayFormInfo info){
        Loan_PayFormInfo result=new Loan_PayFormInfo();
        TinyBeanUtil.copyFieldsValues(info,result);
        result.setNContractId(NameRef.getLoanContractIdByCode(info.getSContractCode()));
        result.setNBankInterestId(NameRef.getInterestRateIdByRate(info.getPayFormRate()));
        result.setMInterestRate(info.getPayFormRate());
        //默认为已使用
        result.setNStatusId(1);
        result.setNGrantCurrentAccountId(NameRef.getAccountIdByAccountCode(info.getGrantCurrentAccount()));
        result.setNGrantTypeId(TRF_Constant.CapitalType.getValue(info.getGrantType()));
        result.setNAccountBankId(NameRef.getBranchIdByBranchCode(info.getRemitOutBank()));
        result.setNOfficeId(1);
        result.setNCurrencyId(1);
        return result;
    }
    
    public void importData(){
        Collection c=this.readDataFromTRF("TRF_LoanPayForm",TRF_LoanPayFormInfo.class);
        DataTransplantBaseDao dao=new DataTransplantBaseDao();
        dao.setStrTableName("loan_PayForm");
        for(Iterator i=c.iterator();i.hasNext();){
            TRF_LoanPayFormInfo info=(TRF_LoanPayFormInfo)i.next();
            dao.add(parseLoan_PayForm(info),null,false);
        }
    }
}
