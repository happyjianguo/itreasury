package com.iss.itreasury.securities.setting.dao;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dataentity.StockHolderAccountInfo;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SEC_StockHolderAccountDAO extends SecuritiesDAO {

	public SEC_StockHolderAccountDAO(){
		super("SEC_StockHolderAccount");
	}
	
	/**
	 * Ìõ¼þ²éÕÒ
	 * @return Collection
	 */
	public Collection findByLinkSearch(String strLinkSearch) throws SecuritiesException{
		try {
			initDAO();
			
			String strSQL = "select * from SEC_StockHolderAccount where 1=1 "+strLinkSearch;
			StockHolderAccountInfo info = new StockHolderAccountInfo();
			 
			prepareStatement(strSQL);
			executeQuery();
	
			Collection c = getDataEntitiesFromResultSet(info.getClass());
			
			finalizeDAO();
			return c;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	public static void main(String[] args) {
		SEC_StockHolderAccountDAO dao = new SEC_StockHolderAccountDAO();
		
		
		try {
			Collection col = dao.findByLinkSearch("");
			System.out.println("+++++++++++++++++"+col.size());
			Iterator itResult = col.iterator();
			
			while (itResult.hasNext()){
				StockHolderAccountInfo tmpInfo = (StockHolderAccountInfo)itResult.next();
				
				System.out.println("id***********"+tmpInfo.getId());
			}
		} catch (SecuritiesException e) {
		
		}
		
	}
}
