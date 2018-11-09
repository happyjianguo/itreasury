package com.iss.itreasury.settlement.transferloancontract.dao;


import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.IException;

public class Sett_TransferLoanContractDetailDepositDAO extends SettlementDAO{
	public Sett_TransferLoanContractDetailDepositDAO()
	{
		super("SETT_TRANSFERAGENTAMOUNT");
	}

	public void  deleteTransferDetailByID(long transferLoanAmountID) throws IException
	{
		StringBuffer sql = new StringBuffer(); 
		sql.append("delete from SETT_TRANSFERAGENTAMOUNT t where t.TRANSFERLOANAMOUNTID=?");
		try {
			initDAO();
			prepareStatement(sql.toString());
			transPS.setLong(1, transferLoanAmountID);
			executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("信贷资产转让删除收款明细出错");
		}
		finally
		{
			finalizeDAO();
		}
		
	}
}
