/*
 * Created on 2004-06-23
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.market.exception;

/**
 * @author kewen hu 2004-06-23 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MarketException extends Exception {
    protected String errorCode;
    protected Object args[];
    protected String strMsgs[];
    protected Exception org;

    public MarketException() {
        errorCode = null;
        args = null;
        org = null;
    }

    public MarketException(String s) {
        super(s);
        errorCode = null;
        args = null;
        org = null;
        errorCode = s;
    }

    public MarketException(String s, Exception exception) {
        super(s);
        errorCode = null;
        args = null;
        org = null;
        errorCode = s;
        org = exception;
    }

    public MarketException(String s, Object aobj[]) {
        super(s);
        errorCode = null;
        args = null;
        org = null;
        errorCode = s;
        args = aobj;
    }

    public MarketException(String s, String astr[]) {
        super(s);
        errorCode = null;
        args = null;
        org = null;
        errorCode = s;
        strMsgs = astr;
    }

    public String getMessage() {
        return super.getMessage();
    }

    public void setOriginalException(Exception exception) {
        org = exception;
    }

    public Exception getOriginalException() {
        return org;
    }

    public void setArgument(Object aobj[]) {
        args = aobj;
    }

    public Object[] getArgument() {
        return args;
    }

    public void setMessageArgs(String astr[]) {
        strMsgs = astr;
    }

    public String[] getMessageArgs() {
        return strMsgs;
    }

    public void setErrorCode(String s) {
        errorCode = s;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static void main(String args1[]) {
        try {
            throw new MarketException("ECM0001");
        } catch (MarketException secException) {
        }
    }
}