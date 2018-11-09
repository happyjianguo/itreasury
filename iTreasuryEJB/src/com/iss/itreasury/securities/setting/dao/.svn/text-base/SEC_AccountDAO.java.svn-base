package com.iss.itreasury.securities.setting.dao;


import java.util.*;
import java.util.Collection;   
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dataentity.AccountInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.util.SECConstant;
 
/**
 * @author yuxu 
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SEC_AccountDAO extends SecuritiesDAO {

	public SEC_AccountDAO(){
		super("SEC_Account");
	}
	/**
	 * 条件查找
	 * @return Collection
	 */
	public Collection findByLinkSearch(String strLinkSearch) throws SecuritiesException{
		try {
			initDAO();
			
			String strSQL = "select * from SEC_Account where 1=1 "+strLinkSearch;
		
			AccountInfo info = new AccountInfo();
			 
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
	 * 根据帐户日期、业务单位、开户营业部、资金帐号查找余额
	 * */
	public Collection findByAccDateCltCtpAccCode(
			long accountId,
			long counterpartId,
			long clientId,
			String accountDate,
			long inputUserID,
			long officeId,
			long currencyId
			
		)throws SecuritiesException
	   {
		
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;
        
		
		StringBuffer bufSQL = new StringBuffer();
		
		bufSQL.append("SELECT \n");
		bufSQL.append("	NVL(A.ACCOUNTDATE,'') ACCOUNTDATE, \n");
		bufSQL.append("	NVL(B.CLIENTID,-1) CLIENTID, \n");
		bufSQL.append("	NVL(B.COUNTERPARTID,-1) COUNTERPARTID, \n");
		bufSQL.append("	NVL(B.ACCOUNTCODE,'') ACCOUNTCODE, \n");
		bufSQL.append("	NVL(B.OPENDATE,'') OPENDATE, \n");			
		bufSQL.append("	NVL(B.STOCKHOLDERACCOUNTID1,-1) STOCKHOLDERACCOUNTID1, \n");  /////////////
		bufSQL.append("	NVL(A.BALANCE,0.0) BALANCE \n");
		bufSQL.append(" FROM SEC_DAILYACCOUNT A, SEC_ACCOUNT B  \n ");
		bufSQL.append(" WHERE A.ACCOUNTID = B.ID \n");
		bufSQL.append(" and counterpartid in (select sec_counterpart.id from sec_counterpart  where isbankofdeposit = 1) \n");
//		bufSQL.append(" WHERE A.ACCOUNTID = B.ID AND B.INPUTUSERID = "+inputUserID+" \n");
		bufSQL.append("	AND B.STATUSID = "+SECConstant.SecuritiesStatus.CHECKED+" \n"); 
		bufSQL.append(" AND B.OFFICEID ="+officeId+" \n");
		bufSQL.append(" AND B.CURRENCYID="+currencyId+"\n ");
		if(accountId == -1){}
		else
		{
		  bufSQL.append(" AND A.ACCOUNTID = "+accountId+" \n");	
		}
		if(counterpartId==-1){}
		else
		{
		  bufSQL.append(" AND B.COUNTERPARTID = "+counterpartId+" \n");	
		}
		if(clientId==-1){}
		else
		{
		  bufSQL.append(" AND B.CLIENTID = "+clientId+" \n");		
		}
		if(accountDate==null||accountDate==""||accountDate.length()==0 || accountDate.equals("null")){}
		else
		{
		  bufSQL.append(" AND TO_CHAR(A.ACCOUNTDATE,'yyyy-mm-dd') ='"+accountDate+"' \n");
		}
		bufSQL.append("ORDER BY ACCOUNTDATE DESC \n");
		
		
		log.info("SQL="+bufSQL.toString()); 
		try {
			this.initDAO();
			log.info("数据库初始化成功！");
			localPS = transConn.prepareStatement(bufSQL.toString());
			log.info("数据库连接成功！");
			localRS = localPS.executeQuery();
			log.info("数据库执行成功！");
			Vector v = new Vector();
			while (localRS.next()) {
				
				v.add(localRS.getTimestamp("ACCOUNTDATE"));
				v.add(String.valueOf(localRS.getLong("CLIENTID")));
				v.add(String.valueOf(localRS.getLong("COUNTERPARTID")));
				v.add(localRS.getString("ACCOUNTCODE"));
				v.add(localRS.getTimestamp("OPENDATE"));
				v.add(localRS.getString("STOCKHOLDERACCOUNTID1"));  
				v.add(localRS.getString("BALANCE"));
				
			}
			  
			list.add(v);
			this.finalizeDAO();
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取证券资料数据产生异常",e);
		}
		
		

		return list;
	 	
	}
	
	public static void main(String[] args) {
	}
}
