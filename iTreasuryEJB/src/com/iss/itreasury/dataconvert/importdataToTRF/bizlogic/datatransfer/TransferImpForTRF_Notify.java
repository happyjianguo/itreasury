/*
 * Created on 2006-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_NotifyInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_Notify implements IExcelBeanDataTranser{
    public Object transExcelRowToBean(HSSFRow row){
        TRF_NotifyInfo info=new TRF_NotifyInfo();
        info.setSAccountNo(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getSAccountNo()==null||"".equals(info.getSAccountNo())){
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
        info.setAf_SDepositNo(DataFormat.getCellValue(row.getCell((short)9)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)10));
        if(!"".equals(strTemp)){
          info.setMOpenAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)11));
        if(!"".equals(strTemp)){
            info.setMBalance(Double.parseDouble(strTemp));
          }
        info.setAf_DtStart(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)12))));
        info.setAf_DtEnd(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)13))));
        strTemp=DataFormat.getCellValue(row.getCell((short)14));
        if(!"".equals(strTemp)){
            info.setAf_NNoticeDay(Long.parseLong(strTemp));
          }
        info.setInputUser(DataFormat.getCellValue(row.getCell((short)15)));
        info.setDtInput(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)16))));
        info.setCheckUser(DataFormat.getCellValue(row.getCell((short)17)));
        info.setDtCheck(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)18))));
        info.setCheckStatus(DataFormat.getCellValue(row.getCell((short)19)));
        info.setSAbstract(DataFormat.getCellValue(row.getCell((short)20)));
        info.setSSubject(DataFormat.getCellValue(row.getCell((short)21)));
        return info;
    }
}
