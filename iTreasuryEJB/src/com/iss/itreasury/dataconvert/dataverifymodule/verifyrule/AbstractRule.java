/*
 * Created on 2006-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.verifyrule;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.iss.itreasury.dataconvert.dataverifymodule.dataentity.ErrorInfo;


/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractRule implements IRule{
    private ErrorInfo errorInfo=new ErrorInfo();
    
    protected HSSFCell cell=null;
    
    public void setHSSFCell(HSSFCell cell){
        this.cell=cell;
    }
    
    public ErrorInfo getErrorInfo(){
        return errorInfo;
    }

}
