package com.iss.itreasury.evoucher.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dataentity.ClientRightEntity;
import com.iss.itreasury.evoucher.util.VOUConstant.EleDocsSet;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

public class ClientRightSetDao extends ITreasuryDAO {

	public ClientRightSetDao(String tableName) {
		super(tableName);
	}

	public ClientRightSetDao(Connection conn) {
		super("sett_signature", conn);
	}

	public void batchRemove(String[] ids) throws VoucherException  {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from sett_signature where nclientid in (");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i].split("__")[0]);
			sb.append(id);
			if(i!=ids.length-1)
			sb.append(",");
		}
		sb.append(")");
		try {
			this.prepareStatement(sb.toString());
			this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			throw new VoucherException();
		}
	}
}
