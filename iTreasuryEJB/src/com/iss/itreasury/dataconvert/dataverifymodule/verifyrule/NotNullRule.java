/*
 * Created on 2006-5-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.verifyrule;

import org.apache.poi.hssf.usermodel.HSSFCell;
import com.iss.itreasury.dataconvert.util.TRF_Constant;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NotNullRule extends AbstractRule{
    
    public boolean verify() {
        if(cell==null){
            getErrorInfo().setErrorTypeId(TRF_Constant.DataConvertErrorType.NULLERR);
            return false;
        }
        if(cell.getCellType()!=HSSFCell.CELL_TYPE_BLANK){
            return true;
        }else{
            getErrorInfo().setErrorTypeId(TRF_Constant.DataConvertErrorType.NULLERR);
            getErrorInfo().setExcelColumn(cell.getCellNum());
            return false;
        }
    }

    

}
