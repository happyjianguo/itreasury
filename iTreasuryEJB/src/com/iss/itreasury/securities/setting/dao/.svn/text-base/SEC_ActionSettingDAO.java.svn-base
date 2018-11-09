package com.iss.itreasury.securities.setting.dao;

import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.setting.dataentity.ActionSettingInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
/**
 * 
 * @author zpli
 *
 */
public class SEC_ActionSettingDAO extends SecuritiesDAO {

	public SEC_ActionSettingDAO() {
		super("sec_sectypactionesetting");
		
	}
	/**
	 * 
	 * @param lLoanTypeId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection findActionByLoanType(long lLoanTypeId) throws SecuritiesDAOException{
		
		
		try {
			initDAO();
			
			String strSQL = "select * from sec_sectypactionesetting where LOANTYPEID="+lLoanTypeId;
		
			ActionSettingInfo info = new ActionSettingInfo();
			 
			prepareStatement(strSQL);
			executeQuery();
	
			Collection c = getDataEntitiesFromResultSet(info.getClass());
	
			finalizeDAO();
			
			return c;
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
	}
}
