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
 * Title:数据操作类
 * </p>
 * <p>
 * Description:用于将数据插入数据表中
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
		 * 根据兼容业务的记录id查找兼容业务的信息包括明细
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
			log.print("======Sett_TransCompatibilityDetailDAO：进入查找兼容业务明细信息======");
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
			log.print("======Sett_TransCompatibilityDetailDAO：结束查找兼容业务明细信息======");
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("查找兼容业务明细信息产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("查找兼容业务明细信息产生错误", e);
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
		log.debug("=============进入明细匹配====");
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
			//log.debug("===明细匹配SQL:" + strSQLBuffer.toString());
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
			e.printStackTrace();throw new SettlementDAOException("根据匹配兼容明细记录产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("根据匹配兼容明细记录产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
		log.debug("=============结束明细匹配====");
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
		//Number	记录ID	外键：关联Sett_ TransCompatibility
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
		//Number	序列号
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
		//Number	客户号	
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
		//Number	账户号	
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
		//Number	合同号	
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
		//	Number	借据号	
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
		//	Code	存单号	
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
		//Number	票据号ID	
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
		//	Number	总账ID	
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
		//	Number	开户行ID	
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
		//	Number	单边账类型ID	取值于常量定义
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
		//	Number	现金流向ID	
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
		//	Number	交易方向	
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
		//	Money	金额
		double dAmount = info.getAmount();
		if (isAndNeed)
		{
			strSQLBuffer.append(" AND ");
		}
		strSQLBuffer.append(" Amount = " + dAmount + " \n");
		isAndNeed = true;
		//	Code	凭证号	
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
		//	Code	密码	
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
		//	Abstract	银行账号
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
		//	Abstract	银行客户
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
		//	Abstract	银行支票	
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
		//	Abstract	银行保单号	
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
		//	Abstract	省	
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
		//	Abstract	市	
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
		//	Abstract	银行	
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
		 * @param  strTableName1,数据库表别名如"a"
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
			info.setCompatibilityID(rs.getLong("CompatibilityID")); //	Number	记录ID	外键：关联Sett_ TransCompatibility
			info.setSerialNo(rs.getLong("SerialNo")); //	Number	序列号	
			info.setClientID(rs.getLong("ClientID")); //	Number	客户号	
			info.setAccountID(rs.getLong("AccountID")); //	Number	账户号	
			info.setContractID(rs.getLong("ContractID")); //	Number	合同号	
			info.setDueBillID(rs.getLong("DueBillID")); //	Number	借据号	
			info.setDepositForm(rs.getString("DepositForm")); //	Code	存单号	
			info.setBillID(rs.getLong("BillID")); //	Number	票据号ID	
			info.setGLID(rs.getLong("GLID")); //	Number	总账ID	
			info.setBankID(rs.getLong("BankID")); //	Number	开户行ID	
			info.setSingleTypeID(rs.getLong("SingleTypeID")); //	Number	单边账类型ID	取值于常量定义
			info.setCashFlowID(rs.getLong("CashFlowID")); //	Number	现金流向ID	
			info.setTransDirectionID(rs.getLong("TransDirectionID")); //	Number	交易方向	
			info.setAmount(rs.getDouble("Amount")); //	Money	金额	
			info.setVoucher(rs.getString("Voucher")); //	Code	凭证号	
			info.setPassword(rs.getString("Password")); //	Code	密码	
			info.setBankAccount(rs.getString("BankAccount")); //	Abstract	银行账号	
			info.setBankClient(rs.getString("BankClient")); //	Abstract	银行客户	
			info.setBankCheckNo(rs.getString("BankCheckNo")); //	Abstract	银行支票	
			info.setDeclarationNo(rs.getString("DeclarationNo")); //	Abstract	银行保单号	
			info.setRemitProvince(rs.getString("RemitProvince")); //	Abstract	省	
			info.setRemitCity(rs.getString("RemitCity")); //	Abstract	市	
			info.setRemitBank(rs.getString("RemitBank")); //	Abstract	银行	
			info.setSubAccountID(rs.getLong("SubAccountID"));//子账户id
			vctReturn.add(info);
		}
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
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