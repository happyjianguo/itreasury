/*
 * Created on 2006-3-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_ClientInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_Client implements IExcelBeanDataTranser{
    public Object transExcelRowToBean(HSSFRow row){
        TRF_ClientInfo info=new TRF_ClientInfo();
        info.setOfficeCode(DataFormat.getCellValue(row.getCell((short)0)));
        info.setCode(DataFormat.getCellValue(row.getCell((short)1)));
        if(info.getCode()==null||"".equalsIgnoreCase(info.getCode())){
            return null;
        }
        info.setName(DataFormat.getCellValue(row.getCell((short)2)));
        info.setSimpleName(DataFormat.getCellValue(row.getCell((short)3)));
        info.setName2(DataFormat.getCellValue(row.getCell((short)4)));
        info.setEngName(DataFormat.getCellValue(row.getCell((short)5)));
        info.setSimpleEngName(DataFormat.getCellValue(row.getCell((short)6)));
        info.setEngName2(DataFormat.getCellValue(row.getCell((short)7)));
        info.setClientBaseType(DataFormat.getCellValue(row.getCell((short)8)));
        info.setSystemIdentify(DataFormat.getCellValue(row.getCell((short)9)));
        info.setQueryPassword(DataFormat.getCellValue(row.getCell((short)10)));
        info.setCustomerManagerUser(DataFormat.getCellValue(row.getCell((short)11)));
        info.setCountry(DataFormat.getCellValue(row.getCell((short)12)));
        info.setInputDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)13))));
        info.setServiceLevel(DataFormat.getCellValue(row.getCell((short)14))); 
        info.setInputUser(DataFormat.getCellValue(row.getCell((short)15)));
        info.setInputTime(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)16))));
        info.setModifyUser(DataFormat.getCellValue(row.getCell((short)17)));
        info.setModifyTime(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)18))));
        info.setStatus(DataFormat.getCellValue(row.getCell((short)19)));
        info.setLegalPerson(DataFormat.getCellValue(row.getCell((short)20)));
        info.setLegalPersonCodeCert(DataFormat.getCellValue(row.getCell((short)21)));
        info.setLicenceCode(DataFormat.getCellValue(row.getCell((short)22)));
        info.setFinancePermissionNo(DataFormat.getCellValue(row.getCell((short)23)));
        info.setCountryTaxRegisterNo(DataFormat.getCellValue(row.getCell((short)24)));
        info.setLocalTaxRegisterNo(DataFormat.getCellValue(row.getCell((short)25)));
        info.setRegisterPlace(DataFormat.getCellValue(row.getCell((short)26)));
        info.setRegisterCurrencyType(DataFormat.getCellValue(row.getCell((short)27)));
        info.setRegisterCapital1(DataFormat.getCellValue(row.getCell((short)28)));
        info.setRegisterCurrencyType2(DataFormat.getCellValue(row.getCell((short)29)));
        info.setRegisterCapital2(DataFormat.getCellValue(row.getCell((short)30)));
        info.setRegisterCurrencyType3(DataFormat.getCellValue(row.getCell((short)31)));
        info.setRegisterCapital3(DataFormat.getCellValue(row.getCell((short)32)));
        info.setRegisterCurrencyType4(DataFormat.getCellValue(row.getCell((short)33)));
        info.setRegisterCapital4(DataFormat.getCellValue(row.getCell((short)34)));
        info.setRegisterCurrencyType5(DataFormat.getCellValue(row.getCell((short)35)));
        info.setRegisterCapital5(DataFormat.getCellValue(row.getCell((short)36)));
        info.setValidateOrganization(DataFormat.getCellValue(row.getCell((short)37)));
        info.setDealScope(DataFormat.getCellValue(row.getCell((short)38)));
        info.setProducts(DataFormat.getCellValue(row.getCell((short)39)));
        info.setOperations(DataFormat.getCellValue(row.getCell((short)40)));
        info.setCapitalScope(DataFormat.getCellValue(row.getCell((short)41)));
        info.setNetCapital(DataFormat.getCellValue(row.getCell((short)42)));
        info.setProductScope(DataFormat.getCellValue(row.getCell((short)43)));
        info.setZipCode(DataFormat.getCellValue(row.getCell((short)44)));
        info.setProvince(DataFormat.getCellValue(row.getCell((short)45))); 
        info.setCity(DataFormat.getCellValue(row.getCell((short)46)));
        info.setAddress(DataFormat.getCellValue(row.getCell((short)47)));
        info.setIsPartner(DataFormat.getCellValue(row.getCell((short)48)));
        info.setIsMarkCompany(DataFormat.getCellValue(row.getCell((short)49)));
        info.setMarkPlace1(DataFormat.getCellValue(row.getCell((short)50)));
        info.setStockNo1(DataFormat.getCellValue(row.getCell((short)51)));
        info.setMarkPlace2(DataFormat.getCellValue(row.getCell((short)52)));
        info.setStockNo2(DataFormat.getCellValue(row.getCell((short)53)));
        info.setMarkPlace3(DataFormat.getCellValue(row.getCell((short)54)));
        info.setStockNo3(DataFormat.getCellValue(row.getCell((short)55)));
        info.setMarkPlace4(DataFormat.getCellValue(row.getCell((short)56)));
        info.setStockNo4(DataFormat.getCellValue(row.getCell((short)57)));
        info.setMarkPlace5(DataFormat.getCellValue(row.getCell((short)58)));
        info.setStockNo5(DataFormat.getCellValue(row.getCell((short)59)));
        info.setLoanCardNo(DataFormat.getCellValue(row.getCell((short)60)));
        info.setLoanCardPwd(DataFormat.getCellValue(row.getCell((short)61)));
        info.setBuildDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)62))));
        if(!"".equals(DataFormat.getCellValue(row.getCell((short)63)))){
          info.setEmployeeNumber(Long.parseLong(DataFormat.getCellValue(row.getCell((short)63))));
        }
        info.setSettlementContacter(DataFormat.getCellValue(row.getCell((short)64)));
        info.setLoanContacter(DataFormat.getCellValue(row.getCell((short)65)));
        info.setPhone(DataFormat.getCellValue(row.getCell((short)66)));
        info.setFax(DataFormat.getCellValue(row.getCell((short)67)));
        info.setWebSite(DataFormat.getCellValue(row.getCell((short)68)));
        info.setEMail(DataFormat.getCellValue(row.getCell((short)69)));
        info.setClientType1(DataFormat.getCellValue(row.getCell((short)70)));
        info.setClientType2(DataFormat.getCellValue(row.getCell((short)71)));
        info.setClientType3(DataFormat.getCellValue(row.getCell((short)72)));
        info.setClientType4(DataFormat.getCellValue(row.getCell((short)73)));
        info.setClientType5(DataFormat.getCellValue(row.getCell((short)74)));
        info.setClientType6(DataFormat.getCellValue(row.getCell((short)75)));
        info.setParentCorp1(DataFormat.getCellValue(row.getCell((short)76)));
        info.setParentCorp2(DataFormat.getCellValue(row.getCell((short)77)));
        info.setBudgetParentCorp(DataFormat.getCellValue(row.getCell((short)78)));
        info.setAccount(DataFormat.getCellValue(row.getCell((short)79)));
        info.setBank1(DataFormat.getCellValue(row.getCell((short)80)));
        info.setBank2(DataFormat.getCellValue(row.getCell((short)81)));
        info.setBank3(DataFormat.getCellValue(row.getCell((short)82)));
        info.setExtendAccount1(DataFormat.getCellValue(row.getCell((short)83)));
        info.setExtendAccount2(DataFormat.getCellValue(row.getCell((short)84)));
        info.setExtendAccount3(DataFormat.getCellValue(row.getCell((short)85)));
        info.setCreditLevelId(DataFormat.getCellValue(row.getCell((short)86)));
        info.setJudgeLevelClient(DataFormat.getCellValue(row.getCell((short)87)));
        info.setCreditLevelDate(DataFormat.getDateTime(DataFormat.getCellValue(row.getCell((short)88))));
        info.setInsideCreditLevel(DataFormat.getCellValue(row.getCell((short)89)));
        info.setRiskLevel(DataFormat.getCellValue(row.getCell((short)90)));
        info.setTalentLevel(DataFormat.getCellValue(row.getCell((short)91)));
        info.setExtendAttribute1(DataFormat.getCellValue(row.getCell((short)92)));
        info.setExtendAttribute2(DataFormat.getCellValue(row.getCell((short)93)));
        info.setExtendAttribute3(DataFormat.getCellValue(row.getCell((short)94)));
        info.setExtendAttribute4(DataFormat.getCellValue(row.getCell((short)95)));
        info.setExtendAttribute5(DataFormat.getCellValue(row.getCell((short)96)));
        info.setExtendAttribute6(DataFormat.getCellValue(row.getCell((short)97)));
        info.setExtendAttribute7(DataFormat.getCellValue(row.getCell((short)98)));
        info.setExtendAttribute8(DataFormat.getCellValue(row.getCell((short)99)));
        info.setExtendInfo1(DataFormat.getCellValue(row.getCell((short)100)));
        info.setExtendInfo2(DataFormat.getCellValue(row.getCell((short)101)));
        info.setExtendInfo3(DataFormat.getCellValue(row.getCell((short)102)));
        info.setExtendInfo4(DataFormat.getCellValue(row.getCell((short)103)));
        info.setExtendInfo5(DataFormat.getCellValue(row.getCell((short)104)));
        info.setExtendInfo6(DataFormat.getCellValue(row.getCell((short)105)));
        info.setExtendInfo7(DataFormat.getCellValue(row.getCell((short)106)));
        info.setExtendInfo8(DataFormat.getCellValue(row.getCell((short)107)));
        return info;
    }
}
