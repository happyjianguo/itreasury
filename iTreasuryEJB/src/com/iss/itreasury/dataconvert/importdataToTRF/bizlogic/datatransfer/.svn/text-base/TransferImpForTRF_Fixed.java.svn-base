/*
 * Created on 2006-4-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_FixedInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_Fixed implements IExcelBeanDataTranser{
    public Object transExcelRowToBean(HSSFRow row){
        TRF_FixedInfo info=new TRF_FixedInfo();
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
        info.setAf_SDepositNo(DataFormat.getCellValue(row.getCell((short)9)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)10));
        if(!"".equals(strTemp)){
          info.setMOpenAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)11));
        if(!"".equals(strTemp)){
            info.setMBalance(Double.parseDouble(strTemp));
          }
        strTemp=DataFormat.getCellValue(row.getCell((short)12));
        if(!"".equals(strTemp)){
            info.setAf_MRate(100*Double.parseDouble(strTemp));
          }
        info.setAf_DtStart(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)13))));
        info.setAf_DtEnd(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)14))));
        info.setAf_DtCome(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)15))));
        strTemp=DataFormat.getCellValue(row.getCell((short)16));
        if(!"".equals(strTemp)){
            info.setAf_MPredrawInterest(Double.parseDouble(strTemp));
          }
        info.setDtClearInterest(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)17))));
        strTemp=DataFormat.getCellValue(row.getCell((short)18));
        if(!"".equals(strTemp)){
            info.setAf_NDepositTerm((long)Double.parseDouble(strTemp));
          }
        info.setInputUser(DataFormat.getCellValue(row.getCell((short)19)));
        info.setDtInput(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)20))));
        info.setCheckUser(DataFormat.getCellValue(row.getCell((short)21)));
        info.setDtCheck(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)22))));
        info.setCheckStatus(DataFormat.getCellValue(row.getCell((short)23)));
        info.setSAbstract(DataFormat.getCellValue(row.getCell((short)24)));
        info.setSSubject(DataFormat.getCellValue(row.getCell((short)25)));
        return info;
    }
}
