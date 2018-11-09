/**
 * <p>Title:�ػ�ʱ�Ժ�ͬ���еĲ��� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone </p>
 * <p>created by ��fanyang</p>
 * <p>date ��2004-07-14</p>
 * <p>modified by :      </p>
 * <p>version 1.0</p>
 * <p>Description: </p>
 */
package com.iss.itreasury.closesystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.securities.util.*;

import com.iss.itreasury.util.Log4j;

public class SecContractOperation
{
	//	��־
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SecContractOperation()
	{
	}
	/**
	* ɨ�����еĺ�ͬ,������״̬��δִ�е�״̬��Ϊ��ִ��,������δִ��״̬�ĺ�ͬ
	* ��������һ��֪ͨ����״̬�������
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ  
	* @param tsDate ��/�ػ�ʱ�� 
	* Description: 
	* ----��ȷ��
	*/
	public boolean updateContractStatus(Connection conn, long lOfficeID, long lCurrencyID) throws Exception
	{
		boolean bIsPassed = true;
		PreparedStatement ps= null;
		try
		{
			String sql = "";
			sql = " update sec_applycontract set statusid = " + SECConstant.ContractStatus.ACTIVE
				+ " where id in ( "
				+ " select distinct a.id from sec_applycontract a,sec_noticeform b "
				+ " where a.id =  b.contractid "
				+ " and a.statusid = " + SECConstant.ContractStatus.NOTACTIVE
				+ " and a.currencyid = " + lCurrencyID
				+ " and a.officeid = " + lOfficeID
				+ " and b.statusid in (" + SECConstant.NoticeFormStatus.CHECKED + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + "))";
				
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
//			if (bIsPassed)
//			{
//				
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
			// �ͷ����ݿ����ӵ�
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		finally
		{
			// �ͷ����ݿ����ӵ�
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		return bIsPassed;
	}
	public static void main(String args[]) throws Exception
	{
		SecContractOperation test = new SecContractOperation();
		Connection conn = Database.getConnection();
		test.updateContractStatus(conn,1,1);
		System.out.println("yeah");
	}
}
