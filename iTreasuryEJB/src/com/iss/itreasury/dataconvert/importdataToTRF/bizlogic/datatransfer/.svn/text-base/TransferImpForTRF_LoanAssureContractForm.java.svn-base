/*
 * Created on 2006-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanAssureContractFormInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_LoanAssureContractForm implements IExcelBeanDataTranser{
    public Object transExcelRowToBean(HSSFRow row){
        TRF_LoanAssureContractFormInfo info=new TRF_LoanAssureContractFormInfo();
        info.setSContractCode(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getSContractCode() ==null||"".equals(info.getSContractCode())){
            return null;
        }
        info.setCurrency(DataFormat.getCellValue(row.getCell((short)1)));
        info.setOffice(DataFormat.getCellValue(row.getCell((short)2)));
        info.setSApplyCode(DataFormat.getCellValue(row.getCell((short)3)));
        info.setAssuredClientName(DataFormat.getCellValue(row.getCell((short)4)));
        info.setAssuredClientCode(DataFormat.getCellValue(row.getCell((short)5)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)6));
        if(!"".equals(strTemp)){
          info.setMLoanAmount(Double.parseDouble(strTemp));
        }
        info.setDtStartDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)7))));
        info.setDtEndDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)8))));
        info.setSLoanReason(DataFormat.getCellValue(row.getCell((short)9)));
        info.setSLoanPurpose(DataFormat.getCellValue(row.getCell((short)10)));
        info.setContractManagerPerson(DataFormat.getCellValue(row.getCell((short)11)));
        strTemp=DataFormat.getCellValue(row.getCell((short)12));
        if(!"".equals(strTemp)){
          info.setCreditContraryAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)13));
        if(!"".equals(strTemp)){
          info.setAssureAmount(Double.parseDouble(strTemp));
        }
        info.setAssureClientName1(DataFormat.getCellValue(row.getCell((short)14)));
        info.setAssureClientCode1(DataFormat.getCellValue(row.getCell((short)15)));
        info.setAssureClientName2(DataFormat.getCellValue(row.getCell((short)16)));
        info.setAssureClientCode2(DataFormat.getCellValue(row.getCell((short)17)));
        info.setAssureClientName3(DataFormat.getCellValue(row.getCell((short)18)));
        info.setAssureClientCode3(DataFormat.getCellValue(row.getCell((short)19)));
        strTemp=DataFormat.getCellValue(row.getCell((short)20));
        if(!"".equals(strTemp)){
          info.setImpawAmount(Double.parseDouble(strTemp));
        }
        info.setImpawClientName(DataFormat.getCellValue(row.getCell((short)21)));
        info.setImpawClientCode(DataFormat.getCellValue(row.getCell((short)22)));
        strTemp=DataFormat.getCellValue(row.getCell((short)23));
        if(!"".equals(strTemp)){
          info.setPledgeAmount(Double.parseDouble(strTemp));
        }
        info.setPledgeClientName(DataFormat.getCellValue(row.getCell((short)24)));
        info.setPledgeClientCode(DataFormat.getCellValue(row.getCell((short)25)));
        strTemp=DataFormat.getCellValue(row.getCell((short)26));
        if(!"".equals(strTemp)){
          info.setMChargeRate(100*Double.parseDouble(strTemp));
        } 
        info.setStatus(DataFormat.getCellValue(row.getCell((short)27)));
        info.setRemark(DataFormat.getCellValue(row.getCell((short)28)));
        info.setRiskLevel(DataFormat.getCellValue(row.getCell((short)29)));
        info.setType1(DataFormat.getCellValue(row.getCell((short)30)));
        info.setType2(DataFormat.getCellValue(row.getCell((short)31)));
        info.setType3(DataFormat.getCellValue(row.getCell((short)32)));
        info.setChargeAssureType(DataFormat.getCellValue(row.getCell((short)33)));
        info.setBeneficiary(DataFormat.getCellValue(row.getCell((short)34)));
        info.setAssureType1(DataFormat.getCellValue(row.getCell((short)35)));
        info.setAssureType2(DataFormat.getCellValue(row.getCell((short)36)));
        strTemp=DataFormat.getCellValue(row.getCell((short)37));
        if(!"".equals(strTemp)){
          info.setMSelfAmount(Double.parseDouble(strTemp));
        } 
        return info;
    }

}
