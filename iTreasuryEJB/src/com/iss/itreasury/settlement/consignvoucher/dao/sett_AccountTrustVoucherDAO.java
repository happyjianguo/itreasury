/*
 * Created on 2004-3-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.consignvoucher.dao;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.consignvoucher.bizlogic.ConsignVoucher;
import com.iss.itreasury.settlement.consignvoucher.bizlogic.ConsignVoucherHome;
import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherConditionInfo;
import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
/**
 * @author ruixie
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class sett_AccountTrustVoucherDAO extends ITreasuryDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public sett_AccountTrustVoucherDAO()
	{
		super("sett_AccountTrustVoucher");
	}
	//debug
	public static void main(String[] args)
	{
		sett_AccountTrustVoucherDAO dao = new sett_AccountTrustVoucherDAO();
		try
		{
			AccountTrustVoucherConditionInfo conditionInfo = new AccountTrustVoucherConditionInfo();
			conditionInfo.setAccountID(1);
			conditionInfo.setVoucherStart("000001");
			conditionInfo.setVoucherEnd("00010");
			long lReturn = dao.findExistVoucher(conditionInfo);
			System.out.println("lReturn=" + lReturn);
			/*
			 Iterator it = null;
			 if(c != null)
			 {
			 it = c.iterator();
			 }
			 if(it != null && it.hasNext())
			 {
			 while(it.hasNext())
			 {
			 AccountTrustVoucherInfo atvi = (AccountTrustVoucherInfo)it.next();
			 System.out.println("accountid====="+atvi.getAccountID());
			 }
			 }
			 */
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * ͨ��ID��ѯ ί�и���ƾ֤��Ϣ
	 * 
	 * @param ID
	 * @return c
	 * @throws Exception
	 */
	public Collection findByID(long ID) throws Exception
	{
		try
		{
			initDAO();
			String strSQL = "select * from sett_AccountTrustVoucher where ID = " + ID;
			AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
			log4j.print("ί�и���ƾ֤find by ID��" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(accountTrustVoucherInfo.getClass());
			finalizeDAO();
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return null;
	}
	public Collection findByConditions(AccountTrustVoucherConditionInfo conditionInfo) throws Exception
	{
		try
		{
			initDAO();
			String strSQL = "select * from sett_AccountTrustVoucher where 1=1";
			if (conditionInfo.getAccountID() > 0)
			{
				strSQL += " and AccountID =" + conditionInfo.getAccountID();
			}
			if (conditionInfo.getVoucherStart() != null && conditionInfo.getVoucherStart().length() > 0 && conditionInfo.getVoucherEnd() != null && conditionInfo.getVoucherEnd().length() > 0)
			{
				strSQL += " and VoucherNo between '" + conditionInfo.getVoucherStart() + "' and '" + conditionInfo.getVoucherEnd() + "'";
			}
			if (conditionInfo.getInputDate() != null)
			{
				strSQL += " and InputDate =to_date('" + DataFormat.formatDate(conditionInfo.getInputDate()) + "','yyyy-mm-dd')";
			}
			AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
			log4j.print("ί�и���ƾ֤find by condition ��" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(accountTrustVoucherInfo.getClass());
			finalizeDAO();
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return null;
	}
	/**
	 * ��ѯ�����Ƿ��Ѿ�����ͬ��ƾ֤����
	 * @param strVoucherNo
	 * @param strPassword
	 * @param tsInputDate
	 * @return �� true û�� false
	 * @throws Exception
	 */
	public long findExistVoucher(AccountTrustVoucherConditionInfo accountConditionInfo) throws Exception
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " select * from sett_AccountTrustVoucher \n" + " where AccountID = " + accountConditionInfo.getAccountID() + " and VoucherNo between '"
					+ accountConditionInfo.getVoucherStart() + "' and '" + accountConditionInfo.getVoucherEnd() + "' \n" + " and InputDate = to_date('"
					+ DataFormat.formatDate(Env.getSystemDate(accountConditionInfo.getOfficeID(), accountConditionInfo.getCurrencyID())) + "','yyyy-mm-dd') \n" + " and StatusID >0 ";
			AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
			log4j.print("�Ƿ���ƾ֤���ڣ�" + strSQL);
			System.out.println("this is my first test =========..,the Sql is:");
			System.out.println(strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			log.print("rs==========" + rs);
			if (rs.next())
			{//�м�¼ ˵�����Ѿ�ʹ�õ�ƾ֤
				lReturn = 1;
			}
			rs.close();
			rs = null;
			finalizeDAO();
			return lReturn;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return lReturn;
	}
	/*
	 * �ж��Ƿ������������˻�
	 */
	public long findIsCanReset(long lAccountID) throws Exception
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " select * from sett_AccountTrustVoucher \n" + " where AccountID = " + lAccountID + " and StatusID > " + SETTConstant.ConsignVoucherStatus.NOTUSE;
			AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
			log4j.print("�Ƿ������������˻� ��" + strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			log.print("rs==========" + rs);
			if (rs.next())
			{//�м�¼ ˵����������
				lReturn = 1;
			}
			rs.close();
			rs = null;
			finalizeDAO();
			return lReturn;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return lReturn;
	}
	/**
	 * �����˻�
	 * @param lAccountID
	 * @return >0 ���óɹ�
	 * @throws Exception
	 */
	public long resetAccount(long lAccountID) throws Exception
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " update sett_AccountTrustVoucher set StatusID = 0 \n" + " where AccountID = " + lAccountID;
			log4j.print("�����˻� ��" + strSQL);
			prepareStatement(strSQL);
			lReturn = executeUpdate();
			finalizeDAO();
			return lReturn;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return lReturn;
	}
	/**
	 * ����ID �޸�״̬ �����޸Ľ���
	 * @param lID
	 * @param lStatusID
	 * @return >0 seccess <0 false
	 * @throws IException
	 */
	public long updateStatusByID(long lID, long lStatusID) throws IException
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " update sett_AccountTrustVoucher set StatusID=" + lStatusID + " where ID = " + lID + " and StatusID <> " + SETTConstant.ConsignVoucherStatus.ISUSE;
			log4j.print("ί�и���ƾ֤update by ID ��" + strSQL);
			prepareStatement(strSQL);
			lReturn = executeUpdate();
			finalizeDAO();
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return lReturn;
	}
	/**
	 * ���ݽ��׺� �޸� ƾ֤״̬ ����ɾ������
	 * @param strTransNo
	 * @param lStatusID
	 * @throws IException
	 */
	public long updateStatusByTransNo(String strTransNo) throws IException
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " update sett_AccountTrustVoucher set StatusID=" + SETTConstant.ConsignVoucherStatus.NOTUSE + " ,TransactionNo = null \n" + " where TransactionNo='" + strTransNo
					+ "' and StatusID = " + SETTConstant.ConsignVoucherStatus.ISUSE;
			log4j.print("ί�и���ƾ֤update ��" + strSQL);
			prepareStatement(strSQL);
			lReturn = executeUpdate();
			finalizeDAO();
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return lReturn;
	}
	/**
	 * ƾ֤ʹ��ʱupdate״̬
	 * @param strVoucherNo
	 * @param strPassword
	 * @throws IException
	 */
	public long updateStatusByUse(String strVoucherNo, String strPassword, long lAccountID, String strTransNo) throws IException
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " update sett_AccountTrustVoucher \n" + " set StatusID = " + SETTConstant.ConsignVoucherStatus.ISUSE + " ,TransactionNo = '" + strTransNo + "' \n" + " where VoucherNo = '"
					+ strVoucherNo + "' and Password = '" + strPassword + "' and StatusID=" + SETTConstant.ConsignVoucherStatus.NOTUSE + " and AccountID = " + lAccountID;
			log4j.print("ί�и���ƾ֤ ʹ�� update ��" + strSQL);
			prepareStatement(strSQL);
			lReturn = executeUpdate();
			finalizeDAO();
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return lReturn;
	}
}
