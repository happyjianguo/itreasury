package com.iss.itreasury.util;

import javax.ejb.SessionContext;
public class IRollbackException extends IException
{
/*	protected String errorCode;
	protected Object args[];
	protected String strMsgs[];
	protected Exception org;*/
	
	//private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public IRollbackException(SessionContext ctx, String s, String astr)
	{
//		super(s);
		super(s,astr);
		if(ctx != null)
			ctx.setRollbackOnly();
		
/*		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		strMsgs = new String[1];
		strMsgs[0] = astr;*/
	}
	
	//Modified by zwsun, 2007/8/8, 处理多参数的情况
	public IRollbackException(SessionContext ctx, String s, String[] astr)
	{
		super(s,astr);
		if(ctx != null)
			ctx.setRollbackOnly();		
	}	
	
	
	public IRollbackException(SessionContext ctx, String s, Exception exception)
	{
//		super(s);
		super(s,exception);
		if(ctx != null)		
			ctx.setRollbackOnly();
/*		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		org = exception;
		System.err.println(exception.toString());
		exception.printStackTrace();*/
	}
	
	public IRollbackException(SessionContext ctx, String s)
	{
		super(s);
		if(ctx != null)		
			ctx.setRollbackOnly();
/*		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		System.err.println(s);*/
	}
	
	public IRollbackException(SessionContext ctx, IException e)
	{
		super(e.getMessage());
		if(ctx != null)		
			ctx.setRollbackOnly();
		errorCode = e.getErrorCode();
		strMsgs = e.getMessageArgs();
		org = e.getOriginalException();
//		System.err.println(e.getMessage());
	}	
	
	
		
/*	public String getMessage()
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
*/
}