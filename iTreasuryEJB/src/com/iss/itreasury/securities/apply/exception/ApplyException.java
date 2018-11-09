package com.iss.itreasury.securities.apply.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;


public class ApplyException extends SecuritiesException {
	public ApplyException(String message)  {
		super();
	}
	public ApplyException(String message, Throwable cause)  {
		super(message,cause);
	}
	 
	public ApplyException(String message, Object aobj[],Throwable cause)  {
	   super(message,cause);

   }
   //@TBD: �����µ���������鴦����쳣
}
