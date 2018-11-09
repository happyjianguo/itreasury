package com.iss.itreasury.settlement.base;



import java.sql.Connection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;




/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-26
 */
/**
 * 以下DAO的所有通用操作有以下假设： <p>
 * 1.数据库字段名与DataEntity中属性名一一对应且相同 <p>
 * 2.所有被操作的DataEntity继承抽象类SECBaseDataEntity<p>
 * 3.所有操作假设操作的数据库表的主键名为ID且类型是long<p>
 * 
 * 如果不符合以上假设的特殊操作，请自定义各种操作，但仍需要继承SecuritiesDAO，<p>
 * 1.并在操作开始前调用initDAO<p>
 * 2.在结束前使用finalizeDAO<p>
 * 3.子类中不对PrepareStatement和ResultSet直接进行操作，即它们的生命周期由父类维护使用SecuritiesDAO定义的方法：<p> 
 * PreparedStatement prepareStatement(String sql) 		<p>
 * ResultSet　executeQuery()                                    <p>
 * void	 	 executeUpdate()                                <p>
 * 对它们进行操作<p>
 * 
 * 如果只针对setPrepareStatementByDataEntity或<p>
 *getDataEntityFromResultSet有特殊操作，可以在子类中重载并且实现该方法而继续使用通用的数据库操作方法<p>
 * 				
 * 
 * 		
 */
public abstract class SettlementDAO extends ITreasuryDAO{
	
	public SettlementDAO(String tableName,boolean isNeedPrefix){
		super(tableName,true);
	}
	public SettlementDAO(String tableName){
		super(tableName);
	}
	
	public SettlementDAO(String tableName,Connection conn){
		super(tableName,conn);
	}	

	public SettlementDAO(String tableName,boolean isNeedPrefix,Connection conn){
			super(tableName,isNeedPrefix,conn);
	}	
	
	public SettlementDAO(String tableName,String sequence,boolean isNeedPrefix){
		super(tableName,sequence,isNeedPrefix);
	}
	public SettlementDAO(String tableName,String sequence,boolean isNeedPrefix,Connection conn){
		super(tableName,sequence,isNeedPrefix,conn);
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
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("状态更新异常",e);
		}
		
	}	

}
