//Source file: D:\\iAMPro-Product\\DevelopCode\\javasource\\com\\iss\\iam\\common\\ISequenceNoSeeker.java
package com.iss.system.dao;
import java.sql.Connection;

import com.iss.system.action.ActionException;
/**
 * �ýӿ��õز������ݿ���е���һ��SequenceNo.
 */
public interface ISequenceNoSeeker
{
	/**
	 * �������ݿ������һ������ʹ�õ�SequenceNo.
	 * 
	 * @param strTable ���ڲ���SequencoNo�����ݿ��
	 * @param objCurrentSequenceNo 
	 * ��ǰ�����һ������SequenceNo,���Ϊnull,���ǵ�һ�ε���.
	 * @param conn
	 * @return Object
	 * @retrurn ������һ������ʹ�õ�SequenceNo.
	 */
	public Object nextSequenceNo(Connection conn, String strTable, Object objCurrentSequenceNo) throws ActionException;
}
