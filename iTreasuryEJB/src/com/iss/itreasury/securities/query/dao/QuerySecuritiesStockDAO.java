/*
 * Created on 2004-4-7
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dao;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesStockInfo;
import com.iss.itreasury.securities.query.dataentity.StockQuerySumInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * @author hjliu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QuerySecuritiesStockDAO extends SecuritiesDAO {
	public QuerySecuritiesStockDAO() {
		super("SEC_SecuritiesStock");
	}

	/**
	 * Ìõ¼þ²éÕÒ
	 * 
	 * @return Collection
	 */
	public Collection findByCondiction(String SQLString)
		throws SecuritiesDAOException {
		try {
			initDAO();
			prepareStatement(SQLString);
			StockQuerySumInfo sumInfo = new StockQuerySumInfo();
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(sumInfo.getClass());
			finalizeDAO();
			return c;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
}
