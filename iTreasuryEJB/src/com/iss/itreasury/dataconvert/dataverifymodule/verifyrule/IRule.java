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
     * ��֤excel��Ԫ��ĺϷ���
     * @param cell
     * @return
     */
    public boolean verify();
    
    /**
     * ��У������ʱ�����ͨ���������ȡ�ô�����Ϣ
     * @return
     */
    public ErrorInfo getErrorInfo();
    
    /**
     * ������ҪУ��ĵ�Ԫ��
     * @param cell
     */
    public void setHSSFCell(HSSFCell cell);
    
}
