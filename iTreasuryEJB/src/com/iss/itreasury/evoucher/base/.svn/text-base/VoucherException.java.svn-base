/*
 * Title:        		iTreasury
 * Description:         通过继承的方法保留IException的所有功能并且增加在JDK1.4中出现的<P>
 * 						嵌套异常的处理(for project that deployed under version or JDK1.4)<P> 
 * Copyright (c) 2006 	Company: iSoftStone
 * @author             	yanliu 
 * @version
 * Date of Creation    2006-9-18
 */

package com.iss.itreasury.evoucher.base;

import javax.ejb.SessionContext;
import com.iss.itreasury.util.ITreasuryException;

public class VoucherException extends ITreasuryException{
		 
	 
	/***
	 *通用的异常或者没有必要给用户提示信息的异常，
	 *提示:系统忙，请稍后再试。如果仍然有问题，请通知系统管理员! 	
	 * */

	 public VoucherException() {
		 super("Gen_E001",null);
	 }
		/***
		 *通用的异常或者没有必要给用户提示信息的异常，希望产生事务回滚
		 *提示:系统忙，请稍后再试。如果仍然有问题，请通知系统管理员! 	
		 * */	 
	 public VoucherException(SessionContext sc) {
		 super("Gen_E001",null);
	 }	 

	 /**
	  * 只有错误代码没有异常参数且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */
	 public VoucherException(String errorCode, Throwable cause)  {
		 super(errorCode,cause);
	 }
	 /**
	  * 只有错误代码没有异常参数并产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * @param sc          容器的上下文
	  * */	 	 

	 public VoucherException(String errorCode, Throwable cause,SessionContext sc)  {
		super(errorCode,cause,sc);
	}	 
//////////////////////////////////////////////////////	 
	 /**
	  * 异常参数为一个且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数，如账户号
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */
	 public VoucherException(String errorCode,String str1, Throwable cause)  {
		 super(errorCode,str1,cause);
	 }
	 /**
	  * 异常参数为一个且产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数，如账户号
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */
	 public VoucherException(String errorCode,String str1, Throwable cause,SessionContext sc)  {
		 super(errorCode,str1,cause);
	 }	 
//////////////////////////////////////////////////////	 
	 
	 /**
	  * 异常参数为两个且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数1，如账户号
	  * @param str2       显示的异常参数2  如金额
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */ 
	 public VoucherException(String errorCode, String astr1, String astr2,Throwable cause)  {
		super(errorCode, astr1,astr2,cause);
		this.cause = cause;
	}
	 
	 /**
	  * 异常参数为两个且不产生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数1，如账户号
	  * @param str2       显示的异常参数2  如金额
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */ 
	 public VoucherException(String errorCode, String astr1, String astr2,Throwable cause,SessionContext sc)  {
		super(errorCode, astr1,astr2,cause);
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
	 public VoucherException(String errorCode, String astr[],Throwable cause)  {
		super(errorCode, astr,cause);
		this.cause = cause;
	}
	 
	 /**
	  * 异常参数为两个且生事务回滚的异常
	  * @param errorCode  IMessageException.properties
	  * @param str1       显示的异常参数1，如账户号
	  * @param str2       显示的异常参数2  如金额
	  * @param cause       产生本异常的异常或错误，如果没有传入null
	  * */ 	
	 public VoucherException(String errorCode, String astr[],Throwable cause,SessionContext sc)  {
		super(errorCode, astr,cause);
		this.cause = cause;
		sc.setRollbackOnly();
	}	 

}
