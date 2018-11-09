/*
 * Created on 2006-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.debug;

import com.iss.itreasury.dataconvert.dataverifymodule.bizlogic.VerifyExcelDataBiz;
import com.iss.itreasury.dataconvert.importdataToDB.ImportBizInvoker;
import com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.ImportDataToTRFBiz;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Debuger {
    
    private String client = "001结算和信贷-基础数据-客户";
    
    public void debugUploadToTempTable(){
        ImportDataToTRFBiz biz=new ImportDataToTRFBiz();
        biz.importDataToTRF("d:/upload/dataconvert/user_data/5-1数据.xls");
    }
    
    public void debugVerify(){
        VerifyExcelDataBiz biz=new VerifyExcelDataBiz();
        biz.verifyExcel("d:/upload/dataconvert/user_data/5-1数据.xls");
    }
    
    public void debugImportData(){
        //"client","currency","fixed","notify","contract","assurecontract"
        //"loanpayform","contractplan","trustloan","consignloan","assureaccount","discountaccount"
        String[] tableNames=new String[]{client,"currency","fixed","notify","contract","assurecontract","loanpayform","contractplan","trustloan","consignloan","assureaccount","discountaccount"};
        ImportBizInvoker invoker=new ImportBizInvoker();
        invoker.invoke(tableNames);
    }
    
    public static void main(String[] args) {
        //new Debuger().debugUploadToTempTable();
        //new Debuger().debugImportData();
        new Debuger().debugVerify();
    }
}
