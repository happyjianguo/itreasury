package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BranchInfo;

/**
 * @author kenny
 *
 */
public class Sett_BranchDAO extends SettlementDAO {
	/**
	 * Constructor for Sett_Branch.
	 * @param conn
	 */
	public Sett_BranchDAO(Connection conn) {
		super(conn);
		this.strTableName = "Sett_Branch";
	}

	/**
	 * Constructor for Sett_Branch.
	 */
	public Sett_BranchDAO() {
		super();
		this.strTableName = "Sett_Branch";
	}

	/**
	 * 
	 * @param BranchInfo branchInfo
	 * @throws SQLException 
	 */
	public Collection findBankAccountNoByCondition(BranchInfo branchInfo) throws SQLException {
		ArrayList list = new ArrayList();
		try {
			this.initDAO();

			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT sBankAccountCode, nBankType FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE ");
			String strs[] = this.getAllFieldNameBuffer(branchInfo, DAO_OPERATION_FIND);
			buffer.append(strs[0]);
			String strSQL = buffer.toString();

			this.prepareStatement(strSQL);
			this.setPrepareStatementByDataEntity(branchInfo, DAO_OPERATION_FIND, strs[0]);
			this.executeQuery();

			while (this.transRS != null && this.transRS.next()) {
				list.add(this.transRS.getString("sBankAccountCode")+":"+this.transRS.getString("nBankType"));
			}

			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return list;
	}
}