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
	 * ���ݿ��߼�ɾ������
	 * @param id������
	 * @param 
	 * @return
	 * @throws ITreasuryDAOException
	 */	
	public void delete(long id)  throws ITreasuryDAOException{
		//To be modify the delete status defined in Constant
		updateStatus(id, 0);
		
	}

	/**
	 * ���ݿ���²�������
	 * @param id������
	 * @param statusID ��Ҫ���µ���״̬
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
			throw new ITreasuryDAOException("״̬�����쳣",e);
		}
		finally
		{
		    finalizeDAO();
		}
		
	}
}
