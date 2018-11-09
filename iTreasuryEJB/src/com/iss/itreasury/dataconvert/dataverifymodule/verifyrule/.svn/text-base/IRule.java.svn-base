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
public interface IRule {
    /**
     * 验证excel单元格的合法性
     * @param cell
     * @return
     */
    public boolean verify();
    
    /**
     * 当校验错误的时候可以通过这个方法取得错误信息
     * @return
     */
    public ErrorInfo getErrorInfo();
    
    /**
     * 设置需要校验的单元格
     * @param cell
     */
    public void setHSSFCell(HSSFCell cell);
    
}
