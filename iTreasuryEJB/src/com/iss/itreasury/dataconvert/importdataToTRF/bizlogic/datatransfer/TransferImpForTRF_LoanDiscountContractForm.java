/*
 * Created on 2006-4-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanDiscountContractFormInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_LoanDiscountContractForm implements IExcelBeanDataTranser{
    
    public Object transExcelRowToBean(HSSFRow row){
        TRF_LoanDiscountContractFormInfo info=new TRF_LoanDiscountContractFormInfo();
        info.setSContractCode(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getSContractCode() ==null||"".equals(info.getSContractCode())){
            return null;
        }
        String strTemp=DataFormat.getCellValue(row.getCell((short)1));
        if(!"".equals(strTemp)){
          info.setMInterestRate(Double.parseDouble(strTemp));
        }
        info.setDtDiscountDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)2))));
        info.setStatus(DataFormat.getCellValue(row.getCell((short)3)));
        info.setContractManagerPerson(DataFormat.getCellValue(row.getCell((short)4)));
        info.setBorrowClient(DataFormat.getCellValue(row.getCell((short)5)));
        info.setBorrowClientCode(DataFormat.getCellValue(row.getCell((short)6)));
        strTemp=DataFormat.getCellValue(row.getCell((short)7));
        if(!"".equals(strTemp)){
          info.setMLoanAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)8));
        if(!"".equals(strTemp)){
          info.setMInterest(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)9));
        if(!"".equals(strTemp)){
          info.setMCheckAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)10));
        if(!"".equals(strTemp)){
          info.setNBankAcceptPo((long)Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)11));
        if(!"".equals(strTemp)){
          info.setNBizAcceptPo((long)Double.parseDouble(strTemp));
        }
        info.setBill_Serialno(DataFormat.getCellValue(row.getCell((short)12)));
        info.setBill_AcceptPoType(DataFormat.getCellValue(row.getCell((short)13)));
        info.setBill_SUserName(DataFormat.getCellValue(row.getCell((short)14)));
        info.setBill_DtCreate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)15))));
        info.setBill_SBank(DataFormat.getCellValue(row.getCell((short)16)));
        info.setBill_DtEnd(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)17))));
        info.setBill_SCode(DataFormat.getCellValue(row.getCell((short)18)));
        strTemp=DataFormat.getCellValue(row.getCell((short)19));
        if(!"".equals(strTemp)){
          info.setBill_MAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)20));
        if(!"".equals(strTemp)){
          info.setBill_MInterest(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)21));
        if(!"".equals(strTemp)){
          info.setBill_MRealAmount(Double.parseDouble(strTemp));
        }
        return info;
    }

}
