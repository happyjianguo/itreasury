/*
 * Created on 2006-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ErrorInfo {
    //id
    private long id=-1;
    //��������id
    private long errorTypeId=-1;
    //�������ڱ�
    private String excelName="";
    //����������
    private long excelRow=-1;
    //����������
    private long excelColumn=-1;
    //����Ԫ���ֵ
    private String execlValue="";
    
    public long getErrorTypeId() {
        return errorTypeId;
    }
    public void setErrorTypeId(long errorTypeId) {
        this.errorTypeId = errorTypeId;
    }
    public long getExcelColumn() {
        return excelColumn;
    }
    public void setExcelColumn(long excelColumn) {
        this.excelColumn = excelColumn;
    }
    public long getExcelRow() {
        return excelRow;
    }
    public void setExcelRow(long excelRow) {
        this.excelRow = excelRow;
    }
    public String getExeclValue() {
        return execlValue;
    }
    public void setExeclValue(String execlValue) {
        this.execlValue = execlValue;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getExcelName() {
        return excelName;
    }
    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }
}
