/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer;

import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IExcelBeanDataTranser {
    /**
     * ����null��ζ����һ����ЧӦ�ñ�����
     * @param row
     * @return
     */
    public Object transExcelRowToBean(HSSFRow row);
}
