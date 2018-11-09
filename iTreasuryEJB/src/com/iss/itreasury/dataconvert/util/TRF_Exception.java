/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TRF_Exception extends RuntimeException {
    //Ϊ�û������errcode,��ͨ��ĳ�ַ�ʽ�����ɶ��û��Ѻõ���Ϣ
    private String errCode = "";

    /**
     * Ĭ�ϵ��޲������췽��
     */
    public TRF_Exception() {
    }

    /**
     * ʵ����ͨexception�Ĵ�����Ϣ��¼���� �����Ϣ�����Զ���errcode��Χ �����ͨ��ĳ�ַ�ʽ����,�ṩ�Ѻõ���Ϣ���ͻ�
     * 
     * @param message
     */
    public TRF_Exception(String message) {
        super(message);
        errCode = message;
    }

    /**
     * ʵ����ͨexception��Ƕ�׹��� �����Ϣ�����Զ���errcode��Χ �����ͨ��ĳ�ַ�ʽ����,�ṩ�Ѻõ���Ϣ���ͻ�
     * 
     * @param message
     * @param cause
     */
    public TRF_Exception(String message, Throwable cause) {
        super(message, cause);
        errCode = message;
    }

    /**
     * ȡ��Ƕ�׵�exception��Ϣ
     */
    public String getMessage() {
        if (getCause() == null) {
            return super.getMessage();
        } else {
            return super.getMessage() + "; nested exception is "
                    + getCause().getClass().getName() + ": "
                    + getCause().getMessage();
        }
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}