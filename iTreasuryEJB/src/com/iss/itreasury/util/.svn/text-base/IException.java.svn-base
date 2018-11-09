package com.iss.itreasury.util;
//import java.io.PrintStream;
import javax.ejb.SessionContext;
public class IException extends Exception
{
	protected String errorCode;
	protected Object args[];
	protected String strMsgs[];
	protected Exception org;
	protected boolean isNeedRollBack = false;
	//private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public IException()
	{
		errorCode = null;
		args = null;
		org = null;
	}

	public IException(String s)
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
	}
	public IException(String s, Exception exception)
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		org = exception;
		//add by wjliu -2007-5-31 增加错误提示信息
		if (exception instanceof IException)
		{
			errorCode = ((IException) exception).getErrorCode();
			
			strMsgs = ((IException) exception).getMessageArgs();
		}
		else if (exception instanceof IRollbackException)
		{
			errorCode = ((IRollbackException) exception).getErrorCode();
			
			strMsgs = ((IRollbackException) exception).getMessageArgs();
		}
		else if (exception instanceof IRuntimeException)
		{
			errorCode = ((IRuntimeException) exception).getErrorCode();
			
			strMsgs = ((IRuntimeException) exception).getMessageArgs();
		}
		
		System.out.println(exception.toString());
		exception.printStackTrace();
	}
	public IException(String s, Object aobj[])
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		args = aobj;
	}
	public IException(String s, String astr)
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		strMsgs = new String[1];
		strMsgs[0] = astr;
	}
	public IException(String s, String astr1, String astr2)
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
	public IException(boolean isNeedRollBack, String s,  Exception exception)
	{
		super(s);
		args = null;
		org = exception;
		errorCode = s;
		this.isNeedRollBack = isNeedRollBack;
		if(exception != null){
			System.out.println(exception.toString());
			exception.printStackTrace();
		}		
		
	}
	
	public IException(String s, String astr[])
	{
		super(s);
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		strMsgs = astr;
	}	

	public String translateExceptionMessage() throws Exception
	{
		String errMessage = Env.getEnvItem(errorCode,"IExceptionMessage.properties");
		return errMessage;
	}
	/*
	public IException(SessionContext ctx, String s, String astr)
	{
		super(s);
		ctx.setRollbackOnly();
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		strMsgs = new String[1];
		strMsgs[0] = astr;
	}
	public IException(SessionContext ctx, String s, Exception exception)
	{
		super(s);
		ctx.setRollbackOnly();
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		org = exception;
		log.error(exception.toString());
		exception.printStackTrace();
	}
	
	public IException(SessionContext ctx, String s)
	{
		super(s);
		ctx.setRollbackOnly();
		errorCode = null;
		args = null;
		org = null;
		errorCode = s;
		log.error(s);
	}
	*/
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
			/*try
			{
				throw new IException("ECM0001");
			}
			catch (IException iException)
			{
				System.out.println(iException.getMessage());
				throw new IException(iException.getMessage());
			}*/
			throw new IException("ECM0001");
		}
		catch (Exception iss)
		{
			System.out.println(iss.getMessage());
		}
	}
}