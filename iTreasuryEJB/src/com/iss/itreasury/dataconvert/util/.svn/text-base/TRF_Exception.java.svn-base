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
    //为用户定义的errcode,可通过某种方式解析成对用户友好的信息
    private String errCode = "";

    /**
     * 默认的无参数构造方法
     */
    public TRF_Exception() {
    }

    /**
     * 实现普通exception的错误信息记录功能 如果信息属于自定义errcode范围 则可以通过某种方式解析,提供友好的信息给客户
     * 
     * @param message
     */
    public TRF_Exception(String message) {
        super(message);
        errCode = message;
    }

    /**
     * 实现普通exception的嵌套功能 如果信息属于自定义errcode范围 则可以通过某种方式解析,提供友好的信息给客户
     * 
     * @param message
     * @param cause
     */
    public TRF_Exception(String message, Throwable cause) {
        super(message, cause);
        errCode = message;
    }

    /**
     * 取得嵌套的exception信息
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