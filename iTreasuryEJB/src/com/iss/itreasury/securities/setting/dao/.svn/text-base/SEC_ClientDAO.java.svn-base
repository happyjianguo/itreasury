package com.iss.itreasury.securities.setting.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dataentity.ClientInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SEC_ClientDAO  extends SecuritiesDAO {

	public SEC_ClientDAO(){
		super("SEC_Client");
	}
	/**
	 * 查找所有的业务单位
	 * @return Collection
	 */
	public Collection findByAll() throws SecuritiesException{
		try {
			initDAO();
			
			String strSQL = "select * from SEC_Client";
			ClientInfo clientInfo = new ClientInfo();
			 
			prepareStatement(strSQL);
			executeQuery();
	
			Collection c = getDataEntitiesFromResultSet(clientInfo.getClass());
	
			finalizeDAO();
			return c;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 * 条件查找
	 * @return Collection
	 */
	public Collection findByLinkSearch(String strLinkSearch) throws SecuritiesException{
		try {
			initDAO();
			
			String strSQL = "select * from SEC_Client where 1=1 "+strLinkSearch;
			ClientInfo info = new ClientInfo();
			
			 
			prepareStatement(strSQL);
			executeQuery();
	
			Collection c = getDataEntitiesFromResultSet(info.getClass());
	
			finalizeDAO();
			return c;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 * 从表中取最大的备注编号
	 * @param  nothing
	 * @return String
	 * @exception throws ITreasuryDAOException
	 */
	public String getMaxCode() throws SecuritiesException	{
		String sReturn = "";
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();
			bufSQL.append("SELECT LPAD((NVL(MAX(id),0)+1),5,'0') maxCode FROM  sec_Client \n");
			log.info("SQL="+bufSQL.toString());

			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				sReturn = localRS.getString("maxCode");
			}
			if(localRS != null) {
				localRS.close();
				localRS = null;
			}
			if(localPS != null) {
				localPS.close();
				localPS = null;
			}
			this.finalizeDAO();
		} catch (SQLException e) {

		} catch (ITreasuryDAOException e) {
			new SecuritiesDAOException(e);
		}

		return sReturn==null || sReturn==""?"":sReturn;
	}
	public static void main(String[] args) {
		ClientInfo info = new ClientInfo();
		//info.setId(3);
		System.out.println("getid()********************"+info.getId());
		info.setCode("aaa");
		info.setName("ffff");
		     
		SEC_ClientDAO dao = new SEC_ClientDAO();
		
//		try {
//			//System.out.println(dao.getMaxCode());
//		} catch (ITreasuryDAOException e) {
//		}
	}
}
