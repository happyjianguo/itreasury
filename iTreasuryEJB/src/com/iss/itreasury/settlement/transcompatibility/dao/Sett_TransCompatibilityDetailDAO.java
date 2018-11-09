/*
 * Created on 2004-8-02
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityDetailInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
/**
 * 
 * <p>
 * Title:���ݲ�����
 * </p>
 * <p>
 * Description:���ڽ����ݲ������ݱ���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:isoftstone
 * </p>
 * 
 * @author gqzhang
 * @version 1.0
 */
public class Sett_TransCompatibilityDetailDAO extends SettlementDAO
{
	public Sett_TransCompatibilityDetailDAO()
	{
		super("Sett_TransCompatibilityDetail");
		this.setUseMaxID();
	}
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
		 * Method findByMainID.
		 * ���ݼ���ҵ��ļ�¼id���Ҽ���ҵ�����Ϣ������ϸ
		 * @param ltransID
		 * @return TransCompatibilityInfo
		 * @throws Exception
		 */
	public Vector findByMainID(long ltransID) throws SettlementDAOException
	{
		ResultSet rs = null;
		Vector vctResult = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
		try
		{
			log.print("======Sett_TransCompatibilityDetailDAO��������Ҽ���ҵ����ϸ��Ϣ======");
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(" select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where CompatibilityID=" + ltransID);
			strSQLBuffer.append(" order by SerialNo asc ");
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vctResult = getCompatibilityDetailInfoFromResultSet(rs);
			rs.close();
			log.print("======Sett_TransCompatibilityDetailDAO���������Ҽ���ҵ����ϸ��Ϣ======");
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("���Ҽ���ҵ����ϸ��Ϣ��������", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("���Ҽ���ҵ����ϸ��Ϣ��������", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
		return vctResult;
	}
	/**
	 * Method match.
	 * @param info
	 * @return TransCompatibilityDetailInfo
	 * @throws SettlementDAOException
	 */
	public TransCompatibilityDetailInfo match(TransCompatibilityDetailInfo info) throws SettlementDAOException
	{
		log.debug("=============������ϸƥ��====");
		TransCompatibilityDetailInfo resultInfo = null;
		ResultSet rs = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
		try
		{
			log.debug("=============1====");
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where \n");
			strSQLBuffer.append(getMatchWhereSQL(info));
			//log.debug("===��ϸƥ��SQL:" + strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			log.debug("=============2====");
			rs = executeQuery();
			Vector vctTemp = getCompatibilityDetailInfoFromResultSet(rs);
			if (vctTemp != null && vctTemp.size() > 0)
			{
				log.debug("=============3====");
				resultInfo = (TransCompatibilityDetailInfo) vctTemp.elementAt(0);
				if (resultInfo != null)
				{
					log.print("===resultInfo:" + resultInfo);
				}
			}
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("����ƥ�������ϸ��¼��������", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("����ƥ�������ϸ��¼��������", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
		log.debug("=============������ϸƥ��====");
		return resultInfo;
	}
	/**
	 * Method getMatchWhereSQL.
	 * @param info
	 * @return String
	 * @throws SettlementDAOException
	 */
	private String getMatchWhereSQL(TransCompatibilityDetailInfo info) throws SettlementDAOException
	{
		StringBuffer strSQLBuffer = new StringBuffer();
		boolean isAndNeed = false;
		//id
		long lId = info.getId();
		if (lId > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" ID = " + lId + " \n");
			isAndNeed = true;
		}
		//Number	��¼ID	���������Sett_ TransCompatibility
		long lCompatibilityID = info.getCompatibilityID();
		if (lCompatibilityID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" CompatibilityID = " + lCompatibilityID + " \n");
			isAndNeed = true;
		}
		//Number	���к�
		long lSerialNo = info.getSerialNo();
		if (lSerialNo > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" SerialNo = " + lSerialNo + " \n");
			isAndNeed = true;
		}
		//Number	�ͻ���	
		long lClientID = info.getClientID();
		if (lClientID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" ClientID = " + lClientID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" ClientID <= 0 \n");
			isAndNeed = true;
		}
		//Number	�˻���	
		long lAccountID = info.getAccountID();
		if (lAccountID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" AccountID = " + lAccountID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" AccountID <= 0 \n");
			isAndNeed = true;
		}
		//Number	��ͬ��	
		long lContractID = info.getContractID();
		if (lContractID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" ContractID = " + lContractID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" ContractID <= 0 \n");
			isAndNeed = true;
		}
		//	Number	��ݺ�	
		long lDueBillID = info.getDueBillID();
		if (lDueBillID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" DueBillID = " + lDueBillID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" DueBillID <= 0 \n");
			isAndNeed = true;
		}
		//	Code	�浥��	
		String strDepositForm = info.getDepositForm();
		if (strDepositForm == null || "".equals(strDepositForm))
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" DepositForm IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" DepositForm = '" + strDepositForm + "' \n");
			isAndNeed = true;
		}
		//Number	Ʊ�ݺ�ID	
		long lBillID = info.getBillID();
		if (lBillID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" BillID = " + lBillID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" BillID <= 0 \n");
			isAndNeed = true;
		}
		//	Number	����ID	
		long lGLID = info.getGLID();
		if (lGLID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" GLID = " + lGLID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" GLID <= 0 \n");
			isAndNeed = true;
		}
		//	Number	������ID	
		long lBankID = info.getBankID();
		if (lBankID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" BankID = " + lBankID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" BankID <= 0 \n");
			isAndNeed = true;
		}
		//	Number	����������ID	ȡֵ�ڳ�������
		long lSingleTypeID = info.getSingleTypeID();
		if (lSingleTypeID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" SingleTypeID = " + lSingleTypeID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" (SingleTypeID <= 0 or  SingleTypeID is null ) \n");
			isAndNeed = true;
		}
		//	Number	�ֽ�����ID	
		long lCashFlowID = info.getCashFlowID();
		if (lCashFlowID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" CashFlowID = " + lCashFlowID + " \n");
			isAndNeed = true;
		}
		else
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" (CashFlowID <= 0 or CashFlowID is null ) \n");
			isAndNeed = true;
		}
		//	Number	���׷���	
		long lTransDirectionID = info.getTransDirectionID();
		if (lTransDirectionID > 0)
		{
			if (isAndNeed)
			{
				strSQLBuffer.append(" AND ");
			}
			strSQLBuffer.append(" TransDirectionID = " + lTransDirectionID + " \n");
			isAndNeed = true;
		}
		//	Money	���
		double dAmount = info.getAmount();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		strSQLBuffer.append(" Amount = " + dAmount + " \n");
		isAndNeed = true;
		//	Code	ƾ֤��	
		String strVoucher = info.getVoucher();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strVoucher == null ||  strVoucher.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  Voucher IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  Voucher = '" + strVoucher + "' \n");
			isAndNeed = true;
		}
		//	Code	����	
		String strPassword = info.getPassword();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strPassword == null || strPassword.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  Password IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  Password = '" + strPassword + "' \n");
			isAndNeed = true;
		}
		//	Abstract	�����˺�
		String strBankAccount = info.getBankAccount();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strBankAccount == null || strBankAccount.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  BankAccount IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  BankAccount = '" + strBankAccount + "' \n");
			isAndNeed = true;
		}
		//	Abstract	���пͻ�
		String strBankClient = info.getBankClient();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strBankClient == null || strBankClient.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  BankClient IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  BankClient = '" + strBankClient + "' \n");
			isAndNeed = true;
		}
		//	Abstract	����֧Ʊ	
		String strBankCheckNo = info.getBankCheckNo();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strBankCheckNo == null || strBankCheckNo.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  BankCheckNo IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  BankCheckNo = '" + strBankCheckNo + "' \n");
			isAndNeed = true;
		}
		//	Abstract	���б�����	
		String strDeclarationNo = info.getDeclarationNo();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strDeclarationNo == null || strDeclarationNo.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  DeclarationNo IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  DeclarationNo = '" + strDeclarationNo + "' \n");
			isAndNeed = true;
		}
		//	Abstract	ʡ	
		String strRemitProvince = info.getRemitProvince();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strRemitProvince == null || strRemitProvince.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  RemitProvince IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  RemitProvince = '" + strRemitProvince + "' \n");
			isAndNeed = true;
		}
		//	Abstract	��	
		String strRemitCity = info.getRemitCity();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strRemitCity == null || strRemitCity.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  RemitCity IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  RemitCity = '" + strRemitCity + "' \n");
			isAndNeed = true;
		}
		//	Abstract	����	
		String strRemitBank = info.getRemitBank();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		if (strRemitBank == null ||  strRemitBank.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append("  RemitBank IS NULL \n");
			isAndNeed = true;
		}
		else
		{
			strSQLBuffer.append("  RemitBank = '" + strRemitBank + "' \n");
			isAndNeed = true;
		}
		return strSQLBuffer.toString();
	}
	/**
		 * Method getCompatibilityDetailInfoFromResultSet.
		 * @param rs
		 * @param  strTableName1,���ݿ�������"a"
		 * @return Vector
		 * @throws Exception
		 */
	public Vector getCompatibilityDetailInfoFromResultSet(ResultSet rs) throws SQLException
	{
		Vector vctReturn = new Vector();
		TransCompatibilityDetailInfo info = null;
		while (rs.next())
		{
			info = new TransCompatibilityDetailInfo();
			info.setId(rs.getLong("ID")); //Number	ID	
			info.setCompatibilityID(rs.getLong("CompatibilityID")); //	Number	��¼ID	���������Sett_ TransCompatibility
			info.setSerialNo(rs.getLong("SerialNo")); //	Number	���к�	
			info.setClientID(rs.getLong("ClientID")); //	Number	�ͻ���	
			info.setAccountID(rs.getLong("AccountID")); //	Number	�˻���	
			info.setContractID(rs.getLong("ContractID")); //	Number	��ͬ��	
			info.setDueBillID(rs.getLong("DueBillID")); //	Number	��ݺ�	
			info.setDepositForm(rs.getString("DepositForm")); //	Code	�浥��	
			info.setBillID(rs.getLong("BillID")); //	Number	Ʊ�ݺ�ID	
			info.setGLID(rs.getLong("GLID")); //	Number	����ID	
			info.setBankID(rs.getLong("BankID")); //	Number	������ID	
			info.setSingleTypeID(rs.getLong("SingleTypeID")); //	Number	����������ID	ȡֵ�ڳ�������
			info.setCashFlowID(rs.getLong("CashFlowID")); //	Number	�ֽ�����ID	
			info.setTransDirectionID(rs.getLong("TransDirectionID")); //	Number	���׷���	
			info.setAmount(rs.getDouble("Amount")); //	Money	���	
			info.setVoucher(rs.getString("Voucher")); //	Code	ƾ֤��	
			info.setPassword(rs.getString("Password")); //	Code	����	
			info.setBankAccount(rs.getString("BankAccount")); //	Abstract	�����˺�	
			info.setBankClient(rs.getString("BankClient")); //	Abstract	���пͻ�	
			info.setBankCheckNo(rs.getString("BankCheckNo")); //	Abstract	����֧Ʊ	
			info.setDeclarationNo(rs.getString("DeclarationNo")); //	Abstract	���б�����	
			info.setRemitProvince(rs.getString("RemitProvince")); //	Abstract	ʡ	
			info.setRemitCity(rs.getString("RemitCity")); //	Abstract	��	
			info.setRemitBank(rs.getString("RemitBank")); //	Abstract	����	
			info.setSubAccountID(rs.getLong("SubAccountID"));//���˻�id
			vctReturn.add(info);
		}
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	public static void main(java.lang.String[] args) throws Exception
	{
		//�ڴ˴�������������Ӧ�ó���Ĵ��롣
		try
		{
			Sett_TransCompatibilityDetailDAO dao = new Sett_TransCompatibilityDetailDAO();
			TransCompatibilityDetailInfo info = null;
			Vector vctResult = dao.findByMainID(1);
			if (vctResult != null)
			{
				for (int i = 0; i < vctResult.size(); i++)
				{
					info = (TransCompatibilityDetailInfo) vctResult.elementAt(i);
					if (info != null)
					{
						System.out.println("==========" + info.getId());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}