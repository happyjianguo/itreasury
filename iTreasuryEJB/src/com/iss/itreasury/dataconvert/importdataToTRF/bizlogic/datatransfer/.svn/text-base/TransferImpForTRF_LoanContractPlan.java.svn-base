/*
 * Created on 2006-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanContractPlanInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_LoanContractPlan implements IExcelBeanDataTranser{
    
    public Object transExcelRowToBean(HSSFRow row){
        TRF_LoanContractPlanInfo info=new TRF_LoanContractPlanInfo();
        info.setSContractCode(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getSContractCode() ==null||"".equals(info.getSContractCode())){
            return null;
        }
        info.setPayType(DataFormat.getCellValue(row.getCell((short)1)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)2));
        if(!"".equals(strTemp)){
          info.setMAmount(Double.parseDouble(strTemp));
        }
        info.setDtPlanDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)3))));   
        return info;
    }
}
