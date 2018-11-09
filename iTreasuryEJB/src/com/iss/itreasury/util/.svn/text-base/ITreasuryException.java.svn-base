/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.util;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.ejb.SessionContext;

/**
 * ITreasuryException的使用：
 * 1.每种异常的构造函数成对出现，即需要产生回滚的异常或不需要产生回滚的异常<P>
 * 区别在于是否传入构造函数参数:SessionContext ,由于我们的使用容器控制的异常管理，因此，<P>
 * 是否产生事务回滚由有业务逻辑的EJB中抛出．<P>
 * 2.一般的后台业务逻辑都使用证券模块通用的异常ITreasuryException，该异常从iTreasury的异常基类  <P>
 * IException派生，因此已经实现所有IException的功能，同时提供了在JDK1.4中提供的异常堆栈处理的机制<P>
 * （由于我们目前项目仍然使用JDK1.3），是否产生回滚由业务逻辑EJB决定并抛出，使用方法参见 2       <P>
 * 3.具有业务逻辑的异常(如AccountStatusException)在所属的业务逻辑包中定义，并不产生回滚，由捕捉该<P>
 * 异常的上层业务逻辑类决定是否回滚．SecuritiesDAOException封装了所有DAO层的异常且不产生事务回滚 <P>
 * 4.异常的前台处理:所有业务调用全部在控制页面完成，在控制页面中使用try－catch Exception捕获所有JSP<P>　
 * 的Java代码的异常，通过导入:<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.util.SessionMng"></jsp:useBean><P>
 * 来处理异常，在异常的catch块中，输入以下代码：<P>
 * 		exp.printStackTrace();　　　　<P>　　　　　　　　　　　
 *		sessionMng.getActionMessages().addMessage(exp);<P>
 *		strNextPageURL = strFailPageURL;<P>
 *在ActionMessages对于提示给用户的异常信息统一处理
 *4.错误代码定义和使用方式遵从iTreasury目前的方式，即通过在IMessageException.properties定义错误代码<P>
 *和错误提示信息的映射关系，在ITreasuryException构造函数出传入错误代码及参数，页面捕获到异常后<P>
 *通过ActionMessages对异常的提示进行统一处理	<P>
 * */


public class ITreasuryException extends IException {
	/** A wrapped Throwable */
	 protected Throwable cause;
	 
		//private Log4j log = new Log4j(Constant.ModuleType.SECURITIES);	 
	 
	/***
	 *通用的异常或者没有必要给用户提示信息的异常，
	 *提示:系统忙，请稍后再试。如果仍然有问题，请通知系统管理员! 	
	 * */

	 public ITreasuryException() {
		 super("Gen_E001");
	 }
		/***
		 *通用的异常或者没有必要给用户提示信息的异常，希望产生事务回滚
		 *提示:系统忙，请稍后再试。如果仍然有问题，请通知系统管理员! 	
		 * */	 
	 public ITreasuryException(SessionContext sc) {
		 super("Gen_E001");
		 sc.setRollbackOnly();
	 }	 

	 /**
	  * 只有错误代码没有异常参数且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */
	 public ITreasuryException(String errorCode, Throwable cause)  {
		 super(errorCode);
		 this.cause = cause;
	 }
	 /**
	  * 只有错误代码没有异常参数并产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * @param sc          容器的上下文
	  * */	 	 

	 public ITreasuryException(String errorCode, Throwable cause,SessionContext sc)  {
		super(errorCode);
		this.cause = cause;
		
		//added by mzh_fu 2007/08/06
		if(sc != null)	
		 sc.setRollbackOnly();
	}	 
//////////////////////////////////////////////////////	 
	 /**
	  * 异常参数为一个且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数，如账户号
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */
	 public ITreasuryException(String errorCode,String str1, Throwable cause)  {
		 super(errorCode,str1);
		 this.cause = cause;
	 }
	 /**
	  * 异常参数为一个且产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数，如账户号
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */
	 public ITreasuryException(String errorCode,String str1, Throwable cause,SessionContext sc)  {
		 super(errorCode,str1);
		 this.cause = cause;
		 sc.setRollbackOnly();
	 }	 
//////////////////////////////////////////////////////	 
	 
	 /**
	  * 异常参数为两个且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数1，如账户号
	  * @param str2       显示的异常参数2  如金额
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */ 
	 public ITreasuryException(String errorCode, String astr1, String astr2,Throwable cause)  {
		super(errorCode, astr1,astr2);
		this.cause = cause;
	}
	 
	 /**
	  * 异常参数为两个且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数1，如账户号
	  * @param str2       显示的异常参数2  如金额
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */ 
	 public ITreasuryException(String errorCode, String astr1, String astr2,Throwable cause,SessionContext sc)  {
		super(errorCode, astr1,astr2);
		this.cause = cause;
	}	 	 
//////////////////////////////////////////////////////			 
	 /**
	  * 异常参数为两个且生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数1，如账户号
	  * @param str2       显示的异常参数2  如金额
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */ 	
	 public ITreasuryException(String errorCode, String astr[],Throwable cause)  {
		super(errorCode, astr);
		this.cause = cause;
	}
	 
	 /**
	  * 异常参数为两个且生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数1，如账户号
	  * @param str2       显示的异常参数2  如金额
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */ 	
	 public ITreasuryException(String errorCode, String astr[],Throwable cause,SessionContext sc)  {
		super(errorCode, astr);
		this.cause = cause;
		sc.setRollbackOnly();
	}	 
//////////////////////////////////////////////////////	

	 
	 
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
			 if (child instanceof ITreasuryException) {
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
			 if (child instanceof ITreasuryException)
				 break;		 	
		 
			 if (child != null) {
			     
			 	System.out.println("Caused by: ");	
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
			 if (child instanceof ITreasuryException) {
				 break;
			 }
		 
			 if (child != null) {
			     System.out.println("Caused by: ");
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
		 	
			 if (child instanceof ITreasuryException) {
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
		if(parent instanceof ITreasuryException)
			return ((ITreasuryException)parent).getCause();
		else
			return null;
	}
	 public Throwable getCause()  {
		 return cause;
	 }
	 

	
}
