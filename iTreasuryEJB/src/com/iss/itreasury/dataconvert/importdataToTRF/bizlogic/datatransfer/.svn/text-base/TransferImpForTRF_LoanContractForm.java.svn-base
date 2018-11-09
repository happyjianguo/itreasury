/*
 * Created on 2006-4-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanContractFormInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_LoanContractForm implements IExcelBeanDataTranser{
    public Object transExcelRowToBean(HSSFRow row){
        TRF_LoanContractFormInfo info=new TRF_LoanContractFormInfo();
        info.setSContractCode(DataFormat.getCellValue(row.getCell((short)0)));
        if(info.getSContractCode() ==null||"".equals(info.getSContractCode())){
            return null;
        }
        info.setLoanType(DataFormat.getCellValue(row.getCell((short)1)));
        info.setSubLoanType(DataFormat.getCellValue(row.getCell((short)2)));
        info.setCurrency(DataFormat.getCellValue(row.getCell((short)3)));
        info.setOffice(DataFormat.getCellValue(row.getCell((short)4)));
        info.setSApplyCode(DataFormat.getCellValue(row.getCell((short)5)));
        info.setConsignClientName(DataFormat.getCellValue(row.getCell((short)6)));
        info.setConsignClientCode(DataFormat.getCellValue(row.getCell((short)7)));
        info.setBorrowClientName(DataFormat.getCellValue(row.getCell((short)8)));
        info.setBorrowClientCode(DataFormat.getCellValue(row.getCell((short)9)));
        String strTemp=DataFormat.getCellValue(row.getCell((short)10));
        if(!"".equals(strTemp)){
          info.setMLoanAmount(Double.parseDouble(strTemp));
        }
         strTemp=DataFormat.getCellValue(row.getCell((short)11));
        if(!"".equals(strTemp)){
          info.setNIntervalNum((long)Double.parseDouble(strTemp));
        }
        info.setDtStartDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)12))));
        info.setDtEndDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)13))));
        info.setSLoanReason(DataFormat.getCellValue(row.getCell((short)14)));
        info.setSLoanPurpose(DataFormat.getCellValue(row.getCell((short)15)));
        info.setContractManagerPerson(DataFormat.getCellValue(row.getCell((short)16)));
        strTemp=DataFormat.getCellValue(row.getCell((short)17));
        if(!"".equals(strTemp)){
          info.setCreditLoanAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)18));
        if(!"".equals(strTemp)){
          info.setAssureAmount(Double.parseDouble(strTemp));
        }
        info.setAssureClientName1(DataFormat.getCellValue(row.getCell((short)19)));
        info.setAssureClientCode1(DataFormat.getCellValue(row.getCell((short)20)));
        info.setAssureClientName2(DataFormat.getCellValue(row.getCell((short)21)));
        info.setAssureClientCode2(DataFormat.getCellValue(row.getCell((short)22)));
        info.setAssureClientName3(DataFormat.getCellValue(row.getCell((short)23)));
        info.setAssureClientCode3(DataFormat.getCellValue(row.getCell((short)24)));
        strTemp=DataFormat.getCellValue(row.getCell((short)25));
        if(!"".equals(strTemp)){
          info.setImpawAmount(Double.parseDouble(strTemp));
        }
        info.setImpawClientName(DataFormat.getCellValue(row.getCell((short)26)));
        info.setImpawClientCode(DataFormat.getCellValue(row.getCell((short)27)));
        info.setSImpawName(DataFormat.getCellValue(row.getCell((short)28)));
        info.setSImpawQuality(DataFormat.getCellValue(row.getCell((short)29)));
        info.setSImpawStatus(DataFormat.getCellValue(row.getCell((short)30)));
        strTemp=DataFormat.getCellValue(row.getCell((short)31));
        if(!"".equals(strTemp)){
          info.setPledgeAmount(Double.parseDouble(strTemp));
        }
        info.setPledgeClientName(DataFormat.getCellValue(row.getCell((short)32)));
        info.setPledgeClientCode(DataFormat.getCellValue(row.getCell((short)33)));
        strTemp=DataFormat.getCellValue(row.getCell((short)34));
        if(!"".equals(strTemp)){
          info.setMPledgeAmount(Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)35));
        if(!"".equals(strTemp)){
          info.setMPledgeRate(100*Double.parseDouble(strTemp));
        }
        info.setPledgeGoodsCondition(DataFormat.getCellValue(row.getCell((short)36)));
        strTemp=DataFormat.getCellValue(row.getCell((short)37));
        if(!"".equals(strTemp)){
          info.setLoanContractInerestRate(100*Double.parseDouble(strTemp));
        }
        strTemp=DataFormat.getCellValue(row.getCell((short)38));
        if(!"".equals(strTemp)){
          info.setLoanExecuteInterestRate(100*Double.parseDouble(strTemp));
        }
        info.setStatus(DataFormat.getCellValue(row.getCell((short)39)));
        strTemp=DataFormat.getCellValue(row.getCell((short)40));
        if(!"".equals(strTemp)){
          info.setMChargeRate(100*Double.parseDouble(strTemp));
        }
        info.setChargeAssureType(DataFormat.getCellValue(row.getCell((short)41)));
        info.setSClientInfo(DataFormat.getCellValue(row.getCell((short)42)));
        info.setRiskLevel(DataFormat.getCellValue(row.getCell((short)43)));
        info.setType1(DataFormat.getCellValue(row.getCell((short)44)));
        info.setType2(DataFormat.getCellValue(row.getCell((short)45)));
        info.setType3(DataFormat.getCellValue(row.getCell((short)46)));
        info.setReturnDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)47))));
        strTemp=DataFormat.getCellValue(row.getCell((short)48));
        if(!"".equals(strTemp)){
          info.setReturnAmount(Double.parseDouble(strTemp));
        }
        info.setRemark(DataFormat.getCellValue(row.getCell((short)49)));
        return info;
    }
}
