/**
 * 
 */
package com.iss.itreasury.craftbrother.apply.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.Log4j;

/**
 * @author sunjing
 * 2011-04-15
 *
 */
public class CraLoanContentDao extends SecuritiesDAO{
	
	Log4j log = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	
	public CraLoanContentDao(){
		super("CRA_LOANCONTENT");
	}
	public CraLoanContentDao(Connection conn){
		super("CRA_LOANCONTENT");
	}
	
	/**
	 * 数据库查找操作 修改记录
	 * 
	 * @param id
	 * @param className
	 *            需要查找的数据库表对应的Data Entity的类名
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByApplyID(long id, Class className)
			throws ITreasuryDAOException {
		Collection coll = null;

		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append("CRA_LOANCONTENT ");
			buffer.append("\n WHERE applyid = " + id);
			buffer.append("\n and statusid = " + 1);
			String strSQL = buffer.toString();
			log.debug(strSQL);
			// prepareStatement(strSQL);
			// transRS = executeQuery();

			prepareStatement(strSQL);
			executeQuery();

			coll = getDataEntitiesFromResultSet(className);

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			finalizeDAO();
		}
		return coll;
	}	
	
}
