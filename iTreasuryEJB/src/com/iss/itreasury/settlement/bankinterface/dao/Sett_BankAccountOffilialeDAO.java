package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountOffiliale;
import com.iss.itreasury.settlement.bankinterface.dataentity.BranchInfo;

/**
 * @author kenny
 *
 */
public class Sett_BankAccountOffilialeDAO extends SettlementDAO {
	/**
	 * Constructor for Sett_BankAccountOffiliale.
	 * @param conn
	 */
	public Sett_BankAccountOffilialeDAO(Connection conn) {
		super(conn);
		this.strTableName = "Sett_BankAccountOffiliale";
	}

	/**
	 * Constructor for Sett_BankAccountOffiliale.
	 */
	public Sett_BankAccountOffilialeDAO() {
		super();
		this.strTableName = "Sett_BankAccountOffiliale";
	}

	/**
	 * 
	 * @param BankAccountOffiliale bankAccountOffiliale
	 * @throws SQLException 
	 */
	public Collection findBankAccountNoByCondition(BankAccountOffiliale bankAccountOffiliale) throws SQLException {
		ArrayList list = new ArrayList();
		try {
			this.initDAO();

			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT sBankAccountNo, nBnkType FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE ");
			String strs[] = this.getAllFieldNameBuffer(bankAccountOffiliale, DAO_OPERATION_FIND);
			buffer.append(strs[0]);
			String strSQL = buffer.toString();

			this.prepareStatement(strSQL);
			this.setPrepareStatementByDataEntity(bankAccountOffiliale, DAO_OPERATION_FIND, strs[0]);
			this.executeQuery();

			while (this.transRS != null && this.transRS.next()) {
				list.add(this.transRS.getString("sBankAccountNo")+":"+this.transRS.getString("nBnkType"));
			}

			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return list;
	}
}