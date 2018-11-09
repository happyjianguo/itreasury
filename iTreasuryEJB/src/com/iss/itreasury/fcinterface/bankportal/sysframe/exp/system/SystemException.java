/*
 * Created on 2005-5-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.BaseException;

/**
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SystemException extends BaseException
{
	/**
	 * 
	 */
	public SystemException()
	{
		super();
	}
	
	/**
	 * @param string
	 * @param e
	 */
	public SystemException(String string, Throwable e)
	{
		super(string, e);
	}

	/**
	 * @param message
	 */
	public SystemException(String string)
	{
		super(string);
	}

	/**
	 * @param cause
	 */
	public SystemException(Throwable cause)
	{
		super(cause);
	}

}
