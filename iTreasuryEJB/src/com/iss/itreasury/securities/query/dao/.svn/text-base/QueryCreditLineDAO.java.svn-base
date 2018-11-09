package com.iss.itreasury.securities.query.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryCreditLineInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * @author gqfang
 * 
 * 综合授信情况查询
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryCreditLineDAO extends SecuritiesDAO
{
	public QueryCreditLineDAO() 
	{
		super("SEC_SecuritiesCreditLine");
	}
	public Collection QueryAllSecuritiesIdAndType(QueryCreditLineInfo paraInfo) throws ITreasuryDAOException
	{
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = " select * from SEC_SecuritiesCreditLine \n";
		strSQL+= " where statusId <> 0  \n";
		
		if( paraInfo.getSecuritiesId() > 0 )
		{
			strSQL += " And  securitiesId = "+paraInfo.getSecuritiesId() +" \n";
		}
		if( paraInfo.getSecuritiesTypeId() > 0 )
		{
			strSQL += " And securitiesTypeId = "+paraInfo.getSecuritiesTypeId()+" \n";
		}
		strSQL+= " order by securitiesId,securitiesTypeId";

		log.info("SQL======"+strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			while(localRS.next()) 
			{
				QueryCreditLineInfo info = new QueryCreditLineInfo();
				info.setId(localRS.getLong("Id"));
				info.setSecuritiesTypeId(localRS.getLong("securitiesTypeId"));
				info.setSecuritiesId(localRS.getLong("securitiesId"));
				info.setCreditLine(localRS.getDouble("creditLine")/10000);
				
				list.add(info);
			}
			if(localRS != null) {
				localRS.close();
				localRS = null;
			}
			if(localPS != null) {
				localPS.close();
				localPS = null;
			}
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取表[SEC_SecuritiesCreditLine]查询产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return list;
	}
}