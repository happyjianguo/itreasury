/*
 * Created on 2006-3-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_CurrencyInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_Currency implements IExcelBeanDataTranser{
    public Object transExcelRowToBean(HSSFRow row){
        TRF_CurrencyInfo info=new TRF_CurrencyInfo();
        info.setAccountNo(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getAccountNo()==null||"".equals(info.getAccountNo())){
            return null;
        }
        info.setOfficeCode(DataFormat.getCellValue(row.getCell((short)1)));
        info.setCurrency(DataFormat.getCellValue(row.getCell((short)2)));
        info.setAccountType(DataFormat.getCellValue(row.getCell((short)3)));
        info.setClientCode(DataFormat.getCellValue(row.getCell((short)4)));
        info.setName(DataFormat.getCellValue(row.getCell((short)5)));
        info.setOpenDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)6))));
        info.setFinishDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)7))));
        info.setStatus(DataFormat.getCellValue(row.getCell((short)8)));
        //本方法体使用的唯一的暂存变量
        String strTemp=DataFormat.getCellValue(row.getCell((short)9));
        if(!"".equals(strTemp)){
            info.setAccountBalance(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)10));
        if(!"".equals(strTemp)){
            info.setAccountInterest(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)11));
        if(!"".equals(strTemp)){
            info.setSumPayInterest(Double.parseDouble(strTemp));
        }
        info.setIsOverDraft(DataFormat.getCellValue(row.getCell((short)12)));
        info.setFirstLimitType(DataFormat.getCellValue(row.getCell((short)13)));
        strTemp=DataFormat.getCellValue(row.getCell((short)14));
        if(!"".equals(strTemp)){
            info.setFirstLimitAmount(Double.parseDouble(strTemp));
        }
        info.setSecondLimitType(DataFormat.getCellValue(row.getCell((short)15)));
        strTemp=DataFormat.getCellValue(row.getCell((short)16));
        if(!"".equals(strTemp)){
            info.setSecondLimitAmount(Double.parseDouble(strTemp));
        }
        info.setThirdLimitType(DataFormat.getCellValue(row.getCell((short)17)));
        strTemp=DataFormat.getCellValue(row.getCell((short)18));
        if(!"".equals(strTemp)){
            info.setThirdLimitAmount(Double.parseDouble(strTemp));
        }
        info.setRatePlan(DataFormat.getCellValue(row.getCell((short)19)));
        info.setIsNegotiate(DataFormat.getCellValue(row.getCell((short)20)));
        strTemp=DataFormat.getCellValue(row.getCell((short)21));
        if(!"".equals(strTemp)){
            info.setCapitalLimitAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)22));
        if(!"".equals(strTemp)){
            info.setNegotiateUnit(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)23));
        if(!"".equals(strTemp)){
            info.setNegotiateRate(100*Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)24));
        if(!"".equals(strTemp)){
            info.setCapitalLimitBalance(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)25));
        if(!"".equals(strTemp)){
            info.setOneBusinessLimitAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)26));
        if(!"".equals(strTemp)){
            info.setAnyBusinessLimitAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)27));
        if(!"".equals(strTemp)){
            info.setAnyBusinessAmount(Double.parseDouble(strTemp));
        }
        info.setInputUser(DataFormat.getCellValue(row.getCell((short)28)));
        info.setInputDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)29))));
        info.setCheckUser(DataFormat.getCellValue(row.getCell((short)30)));
        info.setCheckDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)31))));
        info.setCheckStatus(DataFormat.getCellValue(row.getCell((short)32))); 
        info.setAbstract(DataFormat.getCellValue(row.getCell((short)33)));
        info.setSubjectCode(DataFormat.getCellValue(row.getCell((short)34)));
        strTemp=DataFormat.getCellValue(row.getCell((short)35));
        if(!"".equals(strTemp)){
            info.setStandBalance1(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)36));
        if(!"".equals(strTemp)){
            info.setStandBalance2(Double.parseDouble(strTemp));
        }
        return info;
    }
}
