/*
 * Created on 2006-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.verifyrule;

import org.apache.poi.hssf.usermodel.HSSFCell;
import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.dataconvert.util.TRF_Constant;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class StringRule extends AbstractRule {

    public boolean verify() {
        if (cell == null) {
            return true;
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING
                || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
            return true;
        } else {
            getErrorInfo().setErrorTypeId(
                    TRF_Constant.DataConvertErrorType.STRINGTRANSERR);
            getErrorInfo().setExeclValue(DataFormat.getCellValue(cell));
            getErrorInfo().setExcelColumn(cell.getCellNum());
            return false;
        }
    }

}