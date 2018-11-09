/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_DiscountAccountInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_DiscountAccount implements IExcelBeanDataTranser{
    
    public Object transExcelRowToBean(HSSFRow row){
        TRF_DiscountAccountInfo info=new TRF_DiscountAccountInfo();
        info.setSAccountNo(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getSAccountNo() ==null||"".equals(info.getSAccountNo())){
            return null;
        }
        info.setOfficeCode(DataFormat.getCellValue(row.getCell((short)1)));
        info.setCurrency(DataFormat.getCellValue(row.getCell((short)2)));
        info.setAccountType(DataFormat.getCellValue(row.getCell((short)3)));
        info.setClientCode(DataFormat.getCellValue(row.getCell((short)4)));
        info.setSName(DataFormat.getCellValue(row.getCell((short)5)));
        info.setDtOpen(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)6))));
        info.setDtFinish(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)7))));
        info.setStatus(DataFormat.getCellValue(row.getCell((short)8)));
        info.setLoanContractCode(DataFormat.getCellValue(row.getCell((short)9)));
        info.setDiscountCredenceCode(DataFormat.getCellValue(row.getCell((short)10)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)11));
        if(!"".equals(strTemp)){
          info.setMBalance(Double.parseDouble(strTemp));
        }
        info.setDiscountCredenceStatus(DataFormat.getCellValue(row.getCell((short)12)));
        info.setInputUser(DataFormat.getCellValue(row.getCell((short)13)));
        info.setDtInput(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)14))));
        info.setCheckUser(DataFormat.getCellValue(row.getCell((short)15)));
        info.setDtCheck(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)16))));
        info.setCheckStatus(DataFormat.getCellValue(row.getCell((short)17)));
        info.setSAbstract(DataFormat.getCellValue(row.getCell((short)18)));
        info.setSSubject(DataFormat.getCellValue(row.getCell((short)19)));
        return info;
    }

}
