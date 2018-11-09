/*
 * Created on 2006-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_TrustLoanInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_TrustLoan implements IExcelBeanDataTranser{
    
    public Object transExcelRowToBean(HSSFRow row){
        TRF_TrustLoanInfo info=new TRF_TrustLoanInfo();
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
        info.setContractCode(DataFormat.getCellValue(row.getCell((short)9)));
        info.setPayFormCode(DataFormat.getCellValue(row.getCell((short)10)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)11));
        if(!"".equals(strTemp)){
          info.setMBalance(Double.parseDouble(strTemp));
        }
        info.setPayFormStatus(DataFormat.getCellValue(row.getCell((short)12)));
        info.setIsCircleLoan(DataFormat.getCellValue(row.getCell((short)13)));
        info.setDtClearInterest(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)14))));
        info.setPayInterestAccountCode(DataFormat.getCellValue(row.getCell((short)15)));
        strTemp=DataFormat.getCellValue(row.getCell((short)16));
        if(!"".equals(strTemp)){
            info.setMInterest(Double.parseDouble(strTemp));
          }
        info.setPaySuretyAccountCode(DataFormat.getCellValue(row.getCell((short)17)));
        info.setReceiveSuretyAccountCode(DataFormat.getCellValue(row.getCell((short)18)));
        strTemp=DataFormat.getCellValue(row.getCell((short)19));
        if(!"".equals(strTemp)){
            info.setAl_MSuretyFee(Double.parseDouble(strTemp));
          }
        strTemp=DataFormat.getCellValue(row.getCell((short)20));
        if(!"".equals(strTemp)){
            info.setAl_MOverDueInterest(Double.parseDouble(strTemp));
          }
        strTemp=DataFormat.getCellValue(row.getCell((short)21));
        if(!"".equals(strTemp)){
            info.setAl_MCompoundInterest(Double.parseDouble(strTemp));
          }
        info.setInputUser(DataFormat.getCellValue(row.getCell((short)22)));
        info.setDtInput(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)23))));
        info.setCheckUser(DataFormat.getCellValue(row.getCell((short)24)));
        info.setDtCheck(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)25))));
        info.setCheckStatus(DataFormat.getCellValue(row.getCell((short)26)));
        info.setSAbstract(DataFormat.getCellValue(row.getCell((short)27)));
        info.setSSubject(DataFormat.getCellValue(row.getCell((short)28)));
        strTemp=DataFormat.getCellValue(row.getCell((short)29));
        if(!"".equals(strTemp)){
            info.setStandBalance1(Double.parseDouble(strTemp));
          }
        return info;
    }

}
