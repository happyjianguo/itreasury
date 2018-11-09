package com.iss.itreasury.securities.notice.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * Title:        		iTreasury
 * Description:         通过继承的方法保留IException的所有功能并且增加在JDK1.4中出现的
 * 	嵌				套异常的处理(for project that deployed under version or JDK1.4) 此异
 * 常为应用级异常，将不会产生事务的回滚
 *  Copyright (c) 2003 Company: iSoftStone
 * @author             	fanyang 
 * @version
 *  Date of Creation    2004-3-4
 */
public class NoticeException extends IException{
	/** A wrapped Throwable */
	 protected Throwable cause;
	 
		private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	 
	 
	 /**本类不能直接使用，请定义有明确意义的异常子类*/
	 public NoticeException() {
		 super("证券系统发生应用级异常");
	 }
	 public NoticeException(String errorCode)  {
		 super(errorCode);
	 }

	 public NoticeException(String errorCode, Throwable cause)  {
		super(errorCode);
		this.cause = cause;
	}	 
	 	 
	 public NoticeException(String errorCode,String str1, Throwable cause)  {
		 super(errorCode,str1);
		 this.cause = cause;
	 }

	 public NoticeException(String errorCode, String astr1, String astr2,Throwable cause)  {
		super(errorCode, astr1,astr2);
		this.cause = cause;
	}	 
	 
	 public NoticeException(String errorCode, Object aobj[],Throwable cause)  {
		super(errorCode, aobj);
		this.cause = cause;
	}
	
	 public NoticeException(String errorCode, String astr[],Throwable cause)  {
		super(errorCode, astr);
		this.cause = cause;
	}
	

	 
	 
	 // Created to match the JDK 1.4 Throwable method.
	 public Throwable initCause(Throwable cause)  {
		 this.cause = cause;
		 return cause;
	 }
	 public String getMessage() {
		 // Get this exception's message.
		 String msg = super.getMessage();
		 Throwable parent = this;
		 Throwable child;
		 // Look for nested exceptions.
		 while((child = getNestedException(parent)) != null) {
			 // Get the child's message.
			 String msg2 = child.getMessage();
			 // If we found a message for the child exception, 
			 // we append it.
			 if (msg2 != null) {
				 if (msg != null) {
					 msg += ": " + msg2;
				 } else {
					 msg = msg2;
				 }
			 }
			 // Any nested ApplicationException will append its own
			 // children, so we need to break out of here.
			 if (child instanceof NoticeException) {
				 break;
			 }
			 parent = child;
		 }
		 // Return the completed message.
		 return msg;
	 }
	 public void printStackTrace() {
		 // Print the stack trace for this exception.
		 super.printStackTrace();
		 Throwable parent = this;
		 Throwable child;
		 // Print the stack trace for each nested exception.
		 while((child = getNestedException(parent)) != null) {
			 if (child instanceof NoticeException)
				 break;		 	
		 
			 if (child != null) {
			 	log.error("Caused by: ");	
				 child.printStackTrace();
				 }
				 parent = child;
			 
		 }
	 }
	 public void printStackTrace(PrintStream s) {
		 // Print the stack trace for this exception.
		 super.printStackTrace(s);
		 Throwable parent = this;
		 Throwable child;
		 // Print the stack trace for each nested exception.
		 while((child = getNestedException(parent)) != null) {
			 if (child instanceof NoticeException) {
				 break;
			 }
		 
			 if (child != null) {
				 log.error("Caused by: ");
				 child.printStackTrace(s);

				 parent = child;
			 }
		 }
	 }
	 public void printStackTrace(PrintWriter w) {
		 // Print the stack trace for this exception.
		 super.printStackTrace(w);
		 Throwable parent = this;
		 Throwable child;
		 // Print the stack trace for each nested exception.
		 while((child = getNestedException(parent)) != null) {
		 	
			 if (child instanceof NoticeException) {
				 break;
			 }		 	
		 
			 if (child != null) {
				 w.print("Caused by: ");
				 child.printStackTrace(w);

				 parent = child;
			 }
		 }
	 }
	/**
	 * Method getNestedException.
	 * @param parent
	 * @return Throwable
	 */
	private Throwable getNestedException(Throwable parent) {
		if(parent instanceof NoticeException)
			return ((NoticeException)parent).getCause();
		else
			return null;
	}
	 public Throwable getCause()  {
		 return cause;
	 }
	 


}
