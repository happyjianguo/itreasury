/* Generated by Together */

package com.iss.itreasury.clientmanage.enterpriselaw.dao;


import com.iss.itreasury.util.ITreasuryException;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.clientmanage.dao.CimsBaseDao;
import com.iss.itreasury.clientmanage.enterpriselaw.dataentity.EnterpriseLawInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;

/**
*企业诉讼信息，对应于表Client_EnterpriseLaw
*
* */
public class EnterpriseLawDAO extends CimsBaseDao {
    public EnterpriseLawDAO() {
        super("Client_EnterpriseLaw");
    }
    
    public EnterpriseLawDAO(Connection con) {
    	super("Client_EnterpriseLaw",con);
    }

    /**
     *获得属于某一客户的所有企业诉讼信息
     * */
    public Collection findByClientID(long clientId)throws ITreasuryException{
        
        Collection data = null;
       
     	try
		{
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from");
			sb.append(" Client_EnterpriseLaw ");
			sb.append("  where ClientID = ?  and  (statusid is null or statusid <> 0)");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, clientId);
			
			transRS = transPS.executeQuery();
			
			data=getDataEntitiesFromResultSet(new EnterpriseLawInfo().getClass());
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
     *判断一个客户的编号是否重复
     * */
    public String isRename(long clientId,String newcode)throws ITreasuryException{
        
        String s = "";
       
     	try
		{
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select lawrecordno from");
			sb.append(" Client_EnterpriseLaw ");
			sb.append(" where ClientID = "+clientId+"  and  (statusid is null or statusid <> 0) and lawrecordno like '"+newcode+"'");
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			
			while(transRS.next())
 			{	
 				s = transRS.getString("lawrecordno");
 			}
 			if (s!="")
 			{	
	 			if(s.indexOf("-")>0)
	 			{	
	 				long index = Long.parseLong(s.substring(s.indexOf("-")+1))+1;
	 				s = s.substring(0,s.indexOf("-")+1);
	 				s = s+index;
	 				}
	 			else
	 			{
	 				s=s+"-1";	
	 				}
 			}
		}
		catch (Exception e)
		{
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();
		return s;
     }
    
    /**
     *产生一个客户的编号
     * */
    public String creatCode(long clientId)throws ITreasuryException{
        
        String s = "";
       
     	try
		{
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select lawrecordno from");
			sb.append(" Client_EnterpriseLaw ");                                               
			sb.append(" where ClientID = "+clientId+"  and  (statusid is null or statusid <> 0) and lawrecordno like 'LawRecord%'");
			System.out.println(sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			
			
			while(transRS.next())
			{	
				s = transRS.getString("lawrecordno");
				
			}
			System.out.println("============s==="+s);
			if (s!="")
			{	
	 			if(s.indexOf("-")>0)
	 			{	
	 				long index = Long.parseLong(s.substring(s.indexOf("-")+1))+1;
	 				s = s.substring(0,s.indexOf("-")+1);
	 				s = s+index;
	 				}
	 			else
	 			{
	 				s=s+"-1";	
	 				}
			}
			else
			{
				s = "LawRecord-0";
				}
		}
		catch (Exception e)
		{
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finalizeDAO();
		System.out.println("============s1==="+s);
		return s;
     }
    
    /**
     * 根据Code查询 经营管理信息
     * @author chuanliu
     *
     * TODO To change the template for this generated type comment go to
     * Window - Preferences - Java - Code Style - Code Templates
     */
    public EnterpriseLawInfo findEnterpriseLaw(long ClientID) throws Exception
	{
    	StringBuffer strSQL = new StringBuffer();
    	EnterpriseLawInfo Info = new EnterpriseLawInfo();
    	
    	strSQL.append("select COUNT(LawRecordNo) count,SUM(ExecuteAmount) sum\n");
    	strSQL.append("from Client_EnterpriseLaw \n");
		strSQL.append("WHERE ClientID = " + ClientID + "  \n");
 		strSQL.append("AND Currency = '人民币' \n");
		
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
    

