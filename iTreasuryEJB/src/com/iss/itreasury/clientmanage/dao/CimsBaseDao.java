/*
 * Created on 2005-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.clientmanage.dao;

import java.sql.Connection;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class CimsBaseDao extends ITreasuryDAO{
    protected Log4j log = new Log4j(Constant.ModuleType.BUDGET, this);
    
    public CimsBaseDao(){
		super();
	}
    
	public CimsBaseDao(String tableName){
		super(tableName);
	}
	
	public CimsBaseDao(Connection conn){
		super(conn);
	}
	
	public CimsBaseDao(String tableName,Connection conn){
		super(tableName,conn);
	}	
	/**
	 * 数据库逻辑删除操作
	 * @param id　　　
	 * @param 
	 * @return
	 * @throws ITreasuryDAOException
	 */	
	public void delete(long id)  throws ITreasuryDAOException{
		//To be modify the delete status defined in Constant
		updateStatus(id, 0);
		
	}

	/**
	 * 数据库更新操作操作
	 * @param id　　　
	 * @param statusID 需要更新到的状态
	 * @return
	 * @throws ITreasuryDAOException
	 */		
	public void updateStatus(long id, long statusID) throws ITreasuryDAOException{
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE \n");
			buffer.append(strTableName);
			buffer.append(" SET STATUSID = " + statusID);
			//TBD: maybe need add update execute date
			buffer.append("\n  WHERE ID = " + id);
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("状态更新异常",e);
		}
		finally
		{
		    finalizeDAO();
		}
		
	}
}
