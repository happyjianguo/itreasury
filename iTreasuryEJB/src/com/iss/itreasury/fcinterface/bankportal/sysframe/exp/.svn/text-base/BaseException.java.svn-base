/*
 * Created on 2005-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.sysframe.exp;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 当前接口包的异常基类，为适应jdk1.3的运行环境，实现了异常跟踪的特性，
 * 当前报中的自定义异常都应继承此对象。
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BaseException extends Exception
{
	/** A wrapped Throwable */
	protected Throwable cause = null;
	protected String errorCode = null;
	/**
	 * @return Returns the errorCode.
	 */
	public String getErrorCode()
	{
		return errorCode;
	}
	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * Constructor for BankServiceException.
	 */
	public BaseException()
	{
		super("Error occurred.");
	}

	/**
	 * Constructor for BaseException.
	 * @param message
	 */
	public BaseException(String message)
	{
		super(message);
	}

	/**
	 * Constructor for BaseException.
	 * @param message
	 * @param cause
	 */
	public BaseException(String message, Throwable cause)
	{
		super(message);
		this.cause = cause;
	}

	/**
	 * Constructor for BaseException.
	 * @param cause
	 */
	public BaseException(Throwable cause)
	{
		this.cause = cause;
	}

	//Created to match the JDK 1.4 Throwable method.
	public Throwable initCause(Throwable cause)
	{
		this.cause = cause;
		return cause;
	}
	
	public void printStackTrace()
	{
		// Print the stack trace for this exception.
		super.printStackTrace();
		Throwable parent = this;
		Throwable child;
		// Print the stack trace for each nested exception.
		while (true)
		{
			if (parent instanceof BaseException)
			{
				BaseException expTemp = (BaseException) parent;
				if ((child = expTemp.getCause()) == null)
					break;
			}
			else
			{
				break;
			}

			if (child != null)
			{
				System.err.print("Caused by: ");
				child.printStackTrace();

				parent = child;
			}
		}
	}
	public void printStackTrace(PrintStream s)
	{
		// Print the stack trace for this exception.
		super.printStackTrace(s);
		Throwable parent = this;
		Throwable child;
		// Print the stack trace for each nested exception.
		while (true)
		{
			if (parent instanceof BaseException)
			{
				BaseException expTemp = (BaseException) parent;
				if ((child = expTemp.getCause()) == null)
					break;
			}
			else
			{
				break;
			}

			if (child != null)
			{
				s.print("Caused by: ");
				child.printStackTrace(s);
				
				parent = child;
			}
		}
	}
	public void printStackTrace(PrintWriter w)
	{
		// Print the stack trace for this exception.
		super.printStackTrace(w);
		Throwable parent = this;
		Throwable child;
		// Print the stack trace for each nested exception.
		while (true)
		{
			if (parent instanceof BaseException)
			{
				BaseException expTemp = (BaseException) parent;
				if ((child = expTemp.getCause()) == null)
					break;
			}
			else
			{
				break;
			}
			
			if (child != null)
			{
				w.print("Caused by: ");
				child.printStackTrace(w);
				
				parent = child;
			}
		}
	}
	
	public Throwable getCause()
	{
		return cause;
	}
}
