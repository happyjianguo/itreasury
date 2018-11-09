/*
 * Created on 2004-11-24
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalance.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.ejb.SessionContext;

import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;

/**
 * Title:        		iTreasury
 * Description:         ͨ���̳еķ�������IException�����й��ܲ���������JDK1.4�г��ֵ�<P>
 * 						Ƕ���쳣�Ĵ���(for project that deployed under version or JDK1.4)<P> 
 *  Copyright           (c) 2004 Company: iSoftStone
 * @author              kewen hu 
 * @version
 *  Date of Creation    2004-11-23
 */
public class OffBalanceException extends ITreasuryException {
	/***
	 *ͨ�õ��쳣����û�б�Ҫ���û���ʾ��Ϣ���쳣��
	 *��ʾ:ϵͳæ�����Ժ����ԡ������Ȼ�����⣬��֪ͨϵͳ����Ա! 	
	 * */
	 public OffBalanceException() {
		 super("Gen_E001",null);
	 }
	/***
	 *ͨ�õ��쳣����û�б�Ҫ���û���ʾ��Ϣ���쳣��ϣ����������ع�
	 *��ʾ:ϵͳæ�����Ժ����ԡ������Ȼ�����⣬��֪ͨϵͳ����Ա! 	
	 * */	 
	 public OffBalanceException(SessionContext sc) {
		 super("Gen_E001",null);
	 }	 
	 /**
	  * ֻ�д������û���쳣�����Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */
	 public OffBalanceException(String errorCode, Throwable cause)  {
		 super(errorCode,cause);
	 }
	 /**
	  * ֻ�д������û���쳣��������������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param cause       �������쳣���쳣��������û�д���null
	  * @param sc          ������������
	  * */	 	 
	 public OffBalanceException(String errorCode, Throwable cause,SessionContext sc)  {
		super(errorCode,cause,sc);
	}	 
	 /**
	  * �쳣����Ϊһ���Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣���������˻���
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */
	 public OffBalanceException(String errorCode,String str1, Throwable cause)  {
		 super(errorCode,str1,cause);
	 }
	 /**
	  * �쳣����Ϊһ���Ҳ�������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣���������˻���
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */
	 public OffBalanceException(String errorCode,String str1, Throwable cause,SessionContext sc)  {
		 super(errorCode,str1,cause);
	 }	 
	 /**
	  * �쳣����Ϊ�����Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */ 
	 public OffBalanceException(String errorCode, String astr1, String astr2,Throwable cause)  {
		super(errorCode, astr1,astr2,cause);
		this.cause = cause;
	}
	 /**
	  * �쳣����Ϊ�����Ҳ���������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */ 
	 public OffBalanceException(String errorCode, String astr1, String astr2,Throwable cause,SessionContext sc)  {
		super(errorCode, astr1,astr2,cause);
		this.cause = cause;
	}
	 /**
	  * �쳣����Ϊ������������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */ 	
	 public OffBalanceException(String errorCode, String astr[],Throwable cause)  {
		super(errorCode, astr,cause);
		this.cause = cause;
	}
	 /**
	  * �쳣����Ϊ������������ع����쳣
	  * @param errorCode  IMessageException.properties
	  * @param str1       ��ʾ���쳣����1�����˻���
	  * @param str2       ��ʾ���쳣����2  ����
	  * @param cause       �������쳣���쳣��������û�д���null
	  * */ 	
	 public OffBalanceException(String errorCode, String astr[],Throwable cause,SessionContext sc)  {
		super(errorCode, astr,cause);
		this.cause = cause;
		sc.setRollbackOnly();
	}
}