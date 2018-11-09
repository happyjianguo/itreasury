/* Generated by Together */

package com.iss.itreasury.clientmanage.invest.dao;

import com.iss.itreasury.clientmanage.dao.CimsBaseDao;
import com.iss.itreasury.clientmanage.invest.dataentity.InvestInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;



import java.sql.Connection;
import java.util.Collection;
import com.iss.itreasury.util.ITreasuryException;
/**
*企业对外投资信息DAO ，对应于表
**/
public class InvestDAO extends CimsBaseDao {
	
    public InvestDAO() {
        super("client_InvestOfSubsidiary");
    }
    
    public InvestDAO(Connection con) {
    	super("client_InvestOfSubsidiary",con);
    }

     /**
     *获得属于某一客户的所有企业投资信息
     * */
     public Collection findByClientID(long clientID) throws ITreasuryException{
     	
     	Collection data = null;
     	
     	try
		{
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select *");
			sb.append(" from client_InvestOfSubsidiary ");
			sb.append("  where ClientID = ?  and  (statusid is null or statusid <> 0)");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, clientID);
			
			transRS = transPS.executeQuery();
			
			data=getDataEntitiesFromResultSet(new InvestInfo().getClass());
		}
		catch (Exception e)
		{
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();
		return data;
     }

     /**
      * 根据Code查询 经营管理信息
      * @author chuanliu
      *
      * TODO To change the template for this generated type comment go to
      * Window - Preferences - Java - Code Style - Code Templates
      */
     public InvestInfo findInvest(long ClientID) throws Exception
 	{
     	StringBuffer strSQL = new StringBuffer();
     	InvestInfo Info = new InvestInfo();
     	
     	strSQL.append("select COUNT(ClientID) count,SUM(ContributiveAmount) sum \n");
     	strSQL.append(" from client_InvestOfSubsidiary \n");
 		strSQL.append("WHERE PartnerID = " + ClientID + "  \n"); 	
 		strSQL.append("AND ContributiveCurrency = '人民币' \n");
 		
 		try {			
 			transPS = transConn.prepareStatement(strSQL.toString());
 			transRS = transPS.executeQuery();
 			while(transRS.next())
 			{
 				Info.setCount(transRS.getLong("count"));
 				Info.setSum(transRS.getLong("sum"));		
 			}
 			finalizeDAO();
 		} catch (ITreasuryDAOException e) {
 			e.printStackTrace();
 			throw e;
 		} finally {
 			finalizeDAO();
 		}
     	return (Info);
 	} 	
 }

