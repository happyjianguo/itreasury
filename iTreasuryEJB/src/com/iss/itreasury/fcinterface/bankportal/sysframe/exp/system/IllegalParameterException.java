package com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system;

/**
 * 无效参数异常
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class IllegalParameterException extends SystemException
{

	/**
	 * Constructor for InvaliadParameterException.
	 */
	public IllegalParameterException()
	{
		super();
	}

	/**
	 * Constructor for InvaliadParameterException.
	 * @param message
	 */
	public IllegalParameterException(String message)
	{
		super(message);
	}

	/**
	 * Constructor for InvaliadParameterException.
	 * @param message
	 * @param cause
	 */
	public IllegalParameterException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor for InvaliadParameterException.
	 * @param cause
	 */
	public IllegalParameterException(Throwable cause)
	{
		super(cause);
	}

}
