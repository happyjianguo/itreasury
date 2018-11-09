/*
 * Created on 2006-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanPayFormInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_LoanPayForm implements IExcelBeanDataTranser{
    
    public Object transExcelRowToBean(HSSFRow row){
        TRF_LoanPayFormInfo info=new TRF_LoanPayFormInfo();
        info.setSContractCode(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getSContractCode() ==null||"".equals(info.getSContractCode())){
            return null;
        }
        info.setSCode(DataFormat.getCellValue(row.getCell((short)1)));
        info.setDtOutDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)2))));
        String strTemp=DataFormat.getCellValue(row.getCell((short)3));
        if(!"".equals(strTemp)){
          info.setMAmount(Double.parseDouble(strTemp));
        }
        info.setSConsignAccount(DataFormat.getCellValue(row.getCell((short)4)));
        strTemp=DataFormat.getCellValue(row.getCell((short)5));
        if(!"".equals(strTemp)){
          info.setPayFormRate(100*Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)6));
        if(!"".equals(strTemp)){
          info.setMCommissionRate(100*Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)7));
        if(!"".equals(strTemp)){
          info.setMSuretyfeeRate(100*Double.parseDouble(strTemp));
        }
        info.setDtStart(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)8))));        
        info.setDtEnd(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)9))));   
        info.setSReceiveClientName(DataFormat.getCellValue(row.getCell((short)10)));
        info.setSReceiveAccount(DataFormat.getCellValue(row.getCell((short)11)));
        info.setSRemitBank(DataFormat.getCellValue(row.getCell((short)12)));
        info.setStatus(DataFormat.getCellValue(row.getCell((short)13)));
        info.setGrantCurrentAccount(DataFormat.getCellValue(row.getCell((short)14)));
        info.setGrantType(DataFormat.getCellValue(row.getCell((short)15)));
        info.setRemitOutBank(DataFormat.getCellValue(row.getCell((short)16)));
        info.setSRemitInProvince(DataFormat.getCellValue(row.getCell((short)17)));
        info.setSRemitInCity(DataFormat.getCellValue(row.getCell((short)18)));
        return info;
    }
}
