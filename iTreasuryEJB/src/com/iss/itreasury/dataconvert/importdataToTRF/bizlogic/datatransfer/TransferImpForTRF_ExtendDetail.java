/*
 * Created on 2006-4-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_ExtendDetailInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_ExtendDetail implements IExcelBeanDataTranser{
    
    public Object transExcelRowToBean(HSSFRow row){
        TRF_ExtendDetailInfo info=new TRF_ExtendDetailInfo();
        info.setLoanContractCode(DataFormat.getCellValue(row.getCell((short)0)));
        info.setExtendContractCode(DataFormat.getCellValue(row.getCell((short)1)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)2));
        if(!"".equals(strTemp)){
          info.setAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)3));
        if(!"".equals(strTemp)){
            info.setExecuteInterestRate(100*Double.parseDouble(strTemp));
          }
        info.setStartDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)4))));
        info.setEndDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)5))));
        strTemp=DataFormat.getCellValue(row.getCell((short)6));
        if(!"".equals(strTemp)){
            info.setExtendTerm((long)Double.parseDouble(strTemp));
          }
        return info;
    }
}
