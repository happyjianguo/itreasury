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
 * ITreasuryException��ʹ�ã�
 * 1.ÿ���쳣�Ĺ��캯���ɶԳ��֣�����Ҫ�����ع����쳣����Ҫ�����ع����쳣<P>
 * ���������Ƿ��빹�캯������:SessionContext ,�������ǵ�ʹ���������Ƶ��쳣������ˣ�<P>
 * �Ƿ��������ع�����ҵ���߼���EJB���׳���<P>
 * 2.һ��ĺ�̨ҵ���߼���ʹ��֤ȯģ��ͨ�õ��쳣ITreasuryException�����쳣��iTreasury���쳣����  <P>
 * IException����������Ѿ�ʵ������IException�Ĺ��ܣ�ͬʱ�ṩ����JDK1.4���ṩ���쳣��ջ����Ļ���<P>
 * ����������Ŀǰ��Ŀ��Ȼʹ��JDK1.3�����Ƿ�����ع���ҵ���߼�EJB�������׳���ʹ�÷����μ� 2       <P>
 * 3.����ҵ���߼����쳣(��AccountStatusException)��������ҵ���߼����ж��壬���������ع����ɲ�׽��<P>
 * �쳣���ϲ�ҵ���߼�������Ƿ�ع���SecuritiesDAOException��װ������DAO����쳣�Ҳ���������ع� <P>
 * 4.�쳣��ǰ̨����:����ҵ�����ȫ���ڿ���ҳ����ɣ��ڿ���ҳ����ʹ��try��catch Exception��������JSP<P>��
 * ��Java������쳣��ͨ������:<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.util.SessionMng"></jsp:useBean><P>
 * �������쳣�����쳣��catch���У��������´��룺<P>
 * 		exp.printStackTrace();��������<P>����������������������
 *		sessionMng.getActionMessages().addMessage(exp);<P>
 *		strNextPageURL = strFailPageURL;<P>
 *��ActionMessages������ʾ���û����쳣��Ϣͳһ����
 *4.������붨���ʹ�÷�ʽ���iTreasuryĿǰ�ķ�ʽ����ͨ����IMessageException.properties����������<P>
 *�ʹ�����ʾ��Ϣ��ӳ���ϵ����ITreasuryException���캯�������������뼰������ҳ�沶���쳣��<P>
 *ͨ��ActionMessages���쳣����ʾ����ͳһ����	<P>
 * */


public class ITreasuryException extends IException {
	/** A wrapped Throwable */
	 protected Throwable cause;
	 
		//private Log4j log = new Log4j(Constant.ModuleType.SECURITIES);	 
	 
	/***
	 *ͨ�õ��쳣����û�б�Ҫ���û���ʾ��Ϣ���쳣��
	 *��ʾ:ϵͳæ�����Ժ����ԡ������Ȼ�����⣬��֪ͨϵͳ����Ա! 	
	 * */

	 public ITreasuryException() {
		 super("Gen_E001");
	 }
		/***
		 *ͨ�õ��쳣����û�б�Ҫ���û���ʾ��Ϣ���쳣��ϣ����������ع�
		 *��ʾ:ϵͳæ�����Ժ����ԡ������Ȼ�����⣬��֪ͨϵͳ����Ա! 	
		 * */	 
	 public ITreasuryException(SessionContext sc) {
		 super("Gen_E001");
		 sc.setRollbackOnly();
	 }	 

	 /**
	  * ֻ�д������û���쳣�����Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */
	 public ITreasuryException(String errorCode, Throwable cause)  {
		 super(errorCode);
		 this.cause = cause;
	 }
	 /**
	  * ֻ�д������û���쳣��������������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param cause       �������쳣���쳣��������û�д���null
	  * @param sc          ������������
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
	  * �쳣����Ϊһ���Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣���������˻���
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */
	 public ITreasuryException(String errorCode,String str1, Throwable cause)  {
		 super(errorCode,str1);
		 this.cause = cause;
	 }
	 /**
	  * �쳣����Ϊһ���Ҳ�������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣���������˻���
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */
	 public ITreasuryException(String errorCode,String str1, Throwable cause,SessionContext sc)  {
		 super(errorCode,str1);
		 this.cause = cause;
		 sc.setRollbackOnly();
	 }	 
//////////////////////////////////////////////////////	 
	 
	 /**
	  * �쳣����Ϊ�����Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */ 
	 public ITreasuryException(String errorCode, String astr1, String astr2,Throwable cause)  {
		super(errorCode, astr1,astr2);
		this.cause = cause;
	}
	 
	 /**
	  * �쳣����Ϊ�����Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */ 
	 public ITreasuryException(String errorCode, String astr1, String astr2,Throwable cause,SessionContext sc)  {
		super(errorCode, astr1,astr2);
		this.cause = cause;
	}	 	 
//////////////////////////////////////////////////////			 
	 /**
	  * �쳣����Ϊ������������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */ 	
	 public ITreasuryException(String errorCode, String astr[],Throwable cause)  {
		super(errorCode, astr);
		this.cause = cause;
	}
	 
	 /**
	  * �쳣����Ϊ������������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
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
