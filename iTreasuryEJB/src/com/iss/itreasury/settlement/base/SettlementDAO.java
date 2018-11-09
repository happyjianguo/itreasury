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
 * ����DAO������ͨ�ò��������¼��裺 <p>
 * 1.���ݿ��ֶ�����DataEntity��������һһ��Ӧ����ͬ <p>
 * 2.���б�������DataEntity�̳г�����SECBaseDataEntity<p>
 * 3.���в���������������ݿ���������ΪID��������long<p>
 * 
 * ������������ϼ����������������Զ�����ֲ�����������Ҫ�̳�SecuritiesDAO��<p>
 * 1.���ڲ�����ʼǰ����initDAO<p>
 * 2.�ڽ���ǰʹ��finalizeDAO<p>
 * 3.�����в���PrepareStatement��ResultSetֱ�ӽ��в����������ǵ����������ɸ���ά��ʹ��SecuritiesDAO����ķ�����<p> 
 * PreparedStatement prepareStatement(String sql) 		<p>
 * ResultSet��executeQuery()                                    <p>
 * void	 	 executeUpdate()                                <p>
 * �����ǽ��в���<p>
 * 
 * ���ֻ���setPrepareStatementByDataEntity��<p>
 *getDataEntityFromResultSet��������������������������ز���ʵ�ָ÷���������ʹ��ͨ�õ����ݿ��������<p>
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
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("״̬�����쳣",e);
		}
		
	}	

}
