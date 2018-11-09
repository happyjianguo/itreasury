package com.iss.itreasury.evoucher.setting.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherException;

public class DocTypeRightSetDao extends ITreasuryDAO {

	public DocTypeRightSetDao() {
		super("sett_billtype_signature");
	}

	public DocTypeRightSetDao(Connection con) {
		super("sett_billtype_signature", con);
	}

	public void setRight(long ncurrencyid, long nofficeid) throws VoucherException {
		try {
			String sql = "delete from sett_billtype_signature where NOFFICEID="
					+ nofficeid + " and NCURRENCYID=" + ncurrencyid;
			prepareStatement(sql);
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			throw new VoucherException();
		}
	}
}
