/*
 * Created on 2004-7-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.compatibilitysetting.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilitySubAccountSettingInfo;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilitySubAccountSettingWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
/**
 * @author rxie
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Sett_CompatibilitySubAccountSettingDao extends SettlementDAO
{
	public Sett_CompatibilitySubAccountSettingDao()
	{
		super("Sett_SubAccount", true);
	}
	public static void main(String[] args)
	{
		Sett_CompatibilitySubAccountSettingDao dao = new Sett_CompatibilitySubAccountSettingDao();
		try
		{
			/*			CompatibilitySubAccountSettingInfo info = (CompatibilitySubAccountSettingInfo) dao
			 .findByID(1, CompatibilitySubAccountSettingInfo.class);
			 System.out.println("zzzzzzzzz" + info);
			 */
			CompatibilitySubAccountSettingWhereInfo conditionInfo = new CompatibilitySubAccountSettingWhereInfo();
			conditionInfo.setOfficeID(1);
			conditionInfo.setCurrencyID(1);
			conditionInfo.setInputUserID(1);
			conditionInfo.setSubAccountStatusID(3);
			Collection coll = dao.findByConditions(conditionInfo);
			System.out.println("lReturn=" + coll);
			Iterator it = null;
			if (coll != null)
			{
				it = coll.iterator();
			}
			if (it != null && it.hasNext())
			{
				while (it.hasNext())
				{
					CompatibilitySubAccountSettingInfo info = (CompatibilitySubAccountSettingInfo) it.next();
					System.out.println("accountid==========================================================" + info.getAccountId());
				}
			}
		}
		catch (Exception e)
		{
		}
	}
	public Collection findByConditions(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		try
		{
			initDAO();
			String strSQL = "";
			strSQL = " select sett_SubAccount.* from sett_Account,sett_SubAccount,(select ID,nContractID,sCode from LOAN_DISCOUNTCREDENCE union select ID,nContractID,sCode from loan_PayForm) loan_PayForm,loan_ContractForm,Client \n"
					+ " where sett_Account.ID = sett_SubAccount.nAccountID and sett_SubAccount.AL_nLoanNoteID = loan_PayForm.ID and sett_Account.nClientID = Client.ID and loan_PayForm.nContractID = loan_ContractForm.ID  \n"
					+ " and sett_Account.nOfficeID = "
					+ conditionInfo.getOfficeID()
					+ " and sett_Account.nCurrencyID = "
					+ conditionInfo.getCurrencyID()
					+ " \n"
					+ " and sett_Account.nCheckStatusID = " + SETTConstant.AccountCheckStatus.CHECK + " \n";
			strSQL += " and sett_SubAccount.nInputUserID is not null \n";
			strSQL += " and sett_SubAccount.nCheckUserID is not null \n";
			if (conditionInfo.getClientCode() != null && conditionInfo.getClientCode().length() > 0)
			{
				strSQL += " and Client.sCode = '" + conditionInfo.getClientCode() + "' \n";
			}
			if (conditionInfo.getAccountNo() != null && conditionInfo.getAccountNo().length() > 0)
			{
				strSQL += " and sett_Account.sAccountNo = '" + conditionInfo.getAccountNo() + "' \n";
			}
			if (conditionInfo.getContractCode() != null && conditionInfo.getContractCode().length() > 0)
			{
				strSQL += " and loan_ContractForm.sContractCode = '" + conditionInfo.getContractCode() + "' \n";
			}
			if (conditionInfo.getLoanNoteNo() != null && conditionInfo.getLoanNoteNo().length() > 0)
			{
				strSQL += " and loan_PayForm.sCode = '" + conditionInfo.getLoanNoteNo() + "' \n";
			}
			if (conditionInfo.getPayInterestAccountID() > 0)
			{
				strSQL += " and sett_SubAccount.AL_nPayInterestAccountID = " + conditionInfo.getPayInterestAccountID();
			}
			if (conditionInfo.getReceiveInterestAccountID() > 0)
			{
				strSQL += " and sett_SubAccount.AL_nReceiveInterestAccountID = " + conditionInfo.getReceiveInterestAccountID();
			}
			if (conditionInfo.getSubject() != null && conditionInfo.getSubject().length() > 0)
			{
				strSQL += " and sett_SubAccount.sSubject = '" + conditionInfo.getSubject() + "' ";
			}
			strSQL += " order by sett_Account.sAccountNo,loan_ContractForm.sContractCode,loan_PayForm.sCode ";
			System.out.println("find by condition ��" + strSQL);
			CompatibilitySubAccountSettingInfo subAccountInfo = new CompatibilitySubAccountSettingInfo();
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(subAccountInfo.getClass());
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
	/*
	 * ���Ӳ���
	 */
	public Collection findBySaveLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		try
		{
			initDAO();
			String strSQL = "";
			strSQL = " select sett_SubAccount.* from sett_Account,sett_SubAccount \n" + " where sett_Account.ID = sett_SubAccount.nAccountID \n" + " and sett_Account.nOfficeID = "
					+ conditionInfo.getOfficeID() + " and sett_Account.nCurrencyID = " + conditionInfo.getCurrencyID() + " \n" + " and sett_Account.nCheckStatusID = "
					+ SETTConstant.AccountCheckStatus.CHECK + " \n";
			strSQL += " and sett_SubAccount.nInputUserID = " + conditionInfo.getInputUserID();
			strSQL += " and sett_SubAccount.nCheckUserID is null \n";
			strSQL += " and sett_SubAccount.nStatusID = " + conditionInfo.getSubAccountStatusID() + " \n";
			System.out.println("find by condition ��" + strSQL);
			CompatibilitySubAccountSettingInfo subAccountInfo = new CompatibilitySubAccountSettingInfo();
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(subAccountInfo.getClass());
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
	public Collection findByCheckLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		try
		{
			initDAO();
			String strSQL = "";
			strSQL = " select sett_SubAccount.* from sett_Account,sett_SubAccount \n" + " where sett_Account.ID = sett_SubAccount.nAccountID \n" + " and sett_Account.nOfficeID = "
					+ conditionInfo.getOfficeID() + " and sett_Account.nCurrencyID = " + conditionInfo.getCurrencyID() + " \n" + " and sett_Account.nCheckStatusID = "
					+ SETTConstant.AccountCheckStatus.CHECK + " \n";
			if (conditionInfo.getSubAccountStatusID() == SETTConstant.SubAccountStatus.NEWSAVE)//�����Ҫ���˵��˻�
			{
				strSQL += " and sett_SubAccount.nCheckUserID is null ";
				strSQL += " and sett_SubAccount.nInputUserID <>" + conditionInfo.getCheckUserID() + " \n";
				strSQL += " and sett_SubAccount.nStatusID = " + conditionInfo.getSubAccountStatusID() + " \n";
			}
			else if (conditionInfo.getSubAccountStatusID() == SETTConstant.SubAccountStatus.NORMAL)//����Լ����˵��˻�
			{
				strSQL += " and sett_SubAccount.nCheckUserID = " + conditionInfo.getCheckUserID();
				strSQL += " and sett_SubAccount.nInputUserID <>" + conditionInfo.getCheckUserID() + " \n";
				strSQL += " and sett_SubAccount.nStatusID = " + conditionInfo.getSubAccountStatusID() + " \n";
			}
			System.out.println("find by condition ��" + strSQL);
			CompatibilitySubAccountSettingInfo subAccountInfo = new CompatibilitySubAccountSettingInfo();
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(subAccountInfo.getClass());
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
	/*
	 * ���������˻��Ѿ��޸ģ�����Ҫ���˵��˻�
	 */
	public Collection findByOldCheckQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		try
		{
			initDAO();
			String strSQL = "";
			strSQL = " select sett_SubAccount.* from sett_Account,sett_SubAccount \n" + " where sett_Account.ID = sett_SubAccount.nAccountID \n" + " and sett_Account.nOfficeID = "
					+ conditionInfo.getOfficeID() + " and sett_Account.nCurrencyID = " + conditionInfo.getCurrencyID() + " \n" + " and sett_Account.nCheckStatusID = "
					+ SETTConstant.AccountCheckStatus.CHECK + " \n";
			strSQL += " and sett_SubAccount.nInputUserID <> " + conditionInfo.getInputUserID();
			strSQL += " and sett_SubAccount.nCheckUserID is not null \n";
			strSQL += " and sett_SubAccount.nStatusID = " + conditionInfo.getSubAccountStatusID() + " \n";
			System.out.println("find by condition ��" + strSQL);
			CompatibilitySubAccountSettingInfo subAccountInfo = new CompatibilitySubAccountSettingInfo();
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(subAccountInfo.getClass());
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
	 * �����˻��Ƿ���Ա�ɾ��
	 * 
	 * @param lSubAccountID
	 * @return @throws
	 *         Exception
	 */
	public long findIsCanDelete(long lSubAccountID) throws Exception
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " select * from sett_TransAccountDetail where nSubAccountID = " + lSubAccountID + " and nStatusID > 0 ";
			System.out.println("�Ƿ����ɾ������˻� ��" + strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs.next())
			{//�м�¼ ˵������
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
	 * ����LoanNoteIDȡ�ú�ͬID
	 */
	public long findContractIDByLoanNoteID(long lLoanNoteID) throws Exception
	{
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " select ID,nContractID from LOAN_DISCOUNTCREDENCE \n" + " where ID = " + lLoanNoteID + " \n" + " union \n" + " select ID,nContractID from loan_PayForm \n"
					+ " where ID = " + lLoanNoteID;
			System.out.println("����LoanNoteIDȡ�ú�ͬID" + strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs.next())
			{//�м�¼ ˵������
				lReturn = rs.getLong("nContractID");
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
}
