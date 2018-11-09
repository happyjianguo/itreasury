package com.iss.itreasury.itreasuryinfo.lending.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.lending.dataentity.BankAccountInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class BankAccountDao extends ITreasuryDAO {
	
	protected Log4j log = new Log4j(Constant.ModuleType.FUNDPLAN, this);

	private StringBuffer strSqlBuffer = null;

	public BankAccountDao() {
		super("iplan_BankAccountInfo");
	}

	public BankAccountDao(Connection conn) {
		super("iplan_BankAccountInfo", conn);
	}

	public Collection findBankAccount() throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select ba.sAccountNo,ba.sAccounName from iplan_BankAccountInfo ba where ba.nStatusId = "
						+ IPLANConstant.VALID + " ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();
			while (transRS.next()) {
				BankAccountInfo ba = new BankAccountInfo();
				ba.setsAccountNo(transRS.getString("sAccountNo"));
				ba.setsAccounName(transRS.getString("sAccounName"));
				collection.add(ba);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally {
			finalizeDAO();
		}
		return collection;
	}

	/**
	 * 新增
	 * 
	 * @param bankAccountInfo
	 * @throws ITreasuryDAOException
	 */
	public void insert(BankAccountInfo bankAccountInfo)
			throws ITreasuryDAOException {
		add(bankAccountInfo);
	}

	/**
	 * 判断该账户号是否存在
	 * 
	 * @param sAccountNo
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean isCountAccountNo(String sAccountNo)
			throws ITreasuryDAOException {
		boolean b = false;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select count(1) from iplan_BankAccountInfo ba where ba.nStatusId = "
						+ IPLANConstant.VALID
						+ " and ba.sAccountNo = '"
						+ sAccountNo + "' ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();
			while (transRS.next()) {
				int i = transRS.getInt(1);
				if (i > 0) {
					b = true;
				} else {
					b = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally {
			finalizeDAO();
		}
		return b;
	}

}
