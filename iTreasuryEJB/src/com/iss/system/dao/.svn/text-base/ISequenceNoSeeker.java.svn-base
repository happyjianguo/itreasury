//Source file: D:\\iAMPro-Product\\DevelopCode\\javasource\\com\\iss\\iam\\common\\ISequenceNoSeeker.java
package com.iss.system.dao;
import java.sql.Connection;

import com.iss.system.action.ActionException;
/**
 * 该接口用地查找数据库表中的下一个SequenceNo.
 */
public interface ISequenceNoSeeker
{
	/**
	 * 返回数据库表中下一个可以使用的SequenceNo.
	 * 
	 * @param strTable 用于查找SequencoNo的数据库表。
	 * @param objCurrentSequenceNo 
	 * 当前（最后一个）的SequenceNo,如果为null,则是第一次调用.
	 * @param conn
	 * @return Object
	 * @retrurn 返回下一个可能使用的SequenceNo.
	 */
	public Object nextSequenceNo(Connection conn, String strTable, Object objCurrentSequenceNo) throws ActionException;
}
