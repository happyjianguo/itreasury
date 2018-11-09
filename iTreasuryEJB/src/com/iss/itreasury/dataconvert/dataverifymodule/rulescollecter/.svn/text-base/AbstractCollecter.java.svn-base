/*
 * Created on 2006-5-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.rulescollecter;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.iss.itreasury.dataconvert.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractCollecter implements ICollecter {

    protected boolean isNullRow(HSSFRow row, int checkPos) {
        if (DataFormat.getCellValue(row.getCell((short) checkPos)) == null
                || "".equals(DataFormat.getCellValue(
                        row.getCell((short) checkPos)).trim())) {
            return true;
        }
        return false;
    }

}