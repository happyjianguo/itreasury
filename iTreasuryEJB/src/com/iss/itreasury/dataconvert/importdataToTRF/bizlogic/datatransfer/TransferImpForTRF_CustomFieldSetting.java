/*
 * Created on 2006-4-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_CustomFieldSetting;
import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferImpForTRF_CustomFieldSetting implements IExcelBeanDataTranser{
    public Object transExcelRowToBean(HSSFRow row){
        TRF_CustomFieldSetting info=new TRF_CustomFieldSetting();
        String strTemp=DataFormat.getCellValue(row.getCell((short)0));
        
        return info;
    }
}
