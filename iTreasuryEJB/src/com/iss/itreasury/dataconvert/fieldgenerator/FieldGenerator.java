/*
 * Created on 2006-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.fieldgenerator;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FieldGenerator {
    /**
     * ����һ���µ����ݿ��ֶ�ֵ 
     * ֻ�����String���ͻ���long����
     * @return �п��ܷ��ؿ�ֵ
     * @throws TRF_Exception
     */
    public Object generateValue(String tableName,String fieldName);
}
