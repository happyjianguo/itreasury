/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.util;

/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.PrintStream;

public class IRuntimeException extends RuntimeException
{

	protected String errorCode;
	protected Object args[];
	protected String strMsgs[];
	protected Exception org;

	public IRuntimeException()
	{
		errorCode = null;
		args = null;
		org = null;
	}

	public IRuntimeException(String s)
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
	}

	public IRuntimeException(String s, Exception exception)
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		org = exception;
	}

	public IRuntimeException(String s, Object aobj[])
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		args = aobj;
	}

	public IRuntimeException(String s,String astr)
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;

		strMsgs = new String[1];
		strMsgs[0] = astr;
	}

	public IRuntimeException(String s,String astr1,String astr2)
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;

		strMsgs = new String[2];
		strMsgs[0] = astr1;
		strMsgs[1] = astr2;
	}

	public IRuntimeException(String s, String astr[])
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		strMsgs = astr;
	}

	public String getMessage()
	{
		return super.getMessage();
	}

	public void setOriginalException(Exception exception)
	{
		org = exception;
	}

	public Exception getOriginalException()
	{
		return org;
	}

	public void setArgument(Object aobj[])
	{
		args = aobj;
	}

	public Object[] getArgument()
	{
		return args;
	}

	public void setMessageArgs(String astr[])
	{
		strMsgs = astr;
	}

	public String[] getMessageArgs()
	{
		return strMsgs;
	}

	public void setErrorCode(String s)
	{
		errorCode = s;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public static void main(String args1[])
	{
		try
		{
			throw new IRuntimeException("ECM5555");
		}
		catch(IRuntimeException secRuntimeException)
		{
			System.out.println(secRuntimeException.getMessage());
		}
	}
}

