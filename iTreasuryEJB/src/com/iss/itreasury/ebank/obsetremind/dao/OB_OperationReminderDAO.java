/*
 * Credate date 2007/06/21
 */
package com.iss.itreasury.ebank.obsetremind.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.ebank.obawake.dataentity.OBAwakeCondition;
import com.iss.itreasury.ebank.obsetremind.dataentity.OB_OperationReminderInfo;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;

/**
 * author leiyang
 * Modify by leiyang date 2007/06/21
 */
public class OB_OperationReminderDAO extends ITreasuryDAO {

    public OB_OperationReminderDAO() {
        super("ob_operationreminder");
    }

    public OB_OperationReminderDAO(String tableName) {
        super(tableName);
    }

    public OB_OperationReminderDAO(String tableName, Connection conn) {
        super(tableName, conn);
    }

    public OB_OperationReminderDAO(boolean isNeedPrefix) {
        super(isNeedPrefix);
    }

    public OB_OperationReminderDAO(String tableName, boolean isNeedPrefix) {
        super(tableName, isNeedPrefix);
    }

    public OB_OperationReminderDAO(String tableName, boolean isNeedPrefix, Connection conn) {
        super(tableName, isNeedPrefix, conn);
    }
    
    /**
     * 添加一条 OperationReminderInfo 
     * @param info
     * @throws IException
     */
    public void addOperationReminder(OB_OperationReminderInfo info) throws IException {
    	try {
    		long id = getMaxId("ob_operationreminder");
    		
    		initDAO();
    		String strSql = "insert into ob_operationreminder(ID,OfficeID,CurrencyID,ClientID,OperationId,OperationFate,InputUserId,InputDate,Status) values(?,?,?,?,?,?,?,?,?)";
    		PreparedStatement ps = transConn.prepareStatement(strSql);
    		int index = 1;
    		ps.setLong(index++, id);
    		ps.setLong(index++, info.getOfficeId());
    		ps.setLong(index++, info.getCurrencyId());
    		ps.setLong(index++, info.getClientId());
    		ps.setLong(index++, info.getOperationId());
    		ps.setLong(index++, info.getOperationFate());
    		ps.setLong(index++, info.getInputUserId());
    		ps.setTimestamp(index++, Env.getSystemDateTime());
    		ps.setLong(index++, info.getStatusId());
    		ps.executeUpdate();
    		ps.close();
            finalizeDAO();
    		System.out.println(strSql);
    	}
        catch(Exception e) {
            e.printStackTrace();
            throw new IException("程序异常",e);
        }
        finally {
        	finalizeDAO();
        }
    }
    
    /**
     * 得到最大ID
     * @param tableName
     * @return
     * @throws IException
     */
    public long getMaxId(String tableName) throws IException {

    	try {
    		initDAO();
        	long lMaxID = -1;
    		String strSql = "select nvl(max(id),0)+1 maxid from " + tableName;
    		Statement state = transConn.createStatement();
    		ResultSet rs = state.executeQuery(strSql);
            if(rs!=null && rs.next()) {
            	lMaxID = rs.getLong("maxid");
            }
            rs.close();
            state.close();
            finalizeDAO();
    		System.out.println(strSql);
            return lMaxID;
        }
        catch(Exception e)
        {
             e.printStackTrace();
             throw new IException("程序异常",e);
        }
        finally {
        	finalizeDAO();
        }
    }
    
    /**
     * 修改 OperationReminderInfo
     * @param info
     * @throws IException
     */
    public void update(OB_OperationReminderInfo info) throws IException {
    	StringBuffer strSql = new StringBuffer();
    	PreparedStatement ps = null;
    	try {
    		initDAO();
    		strSql.append("update ob_operationreminder ");
    		strSql.append("set OperationFate=?, ModifyDate=? where Id =?");
    		ps = transConn.prepareStatement(strSql.toString());
    		int index = 1;
    		ps.setLong(index++, info.getOperationFate());
    		ps.setTimestamp(index++, Env.getSystemDateTime());
    		ps.setLong(index++, info.getId());
    		ps.executeUpdate();
    		
      		ps.close();
            finalizeDAO();
    		System.out.println(strSql.toString());
    	}
        catch(Exception e) {
            e.printStackTrace();
            throw new IException("程序异常",e);
        }
        finally {
        	finalizeDAO();
        }
    }
    
    /**
     * 通过部分OBAwakeCondition信息进行查询
     * @param awake
     * @return
     * @throws IException
     */
    public Collection findOperationReminder(OBAwakeCondition awake) throws IException {
		StringBuffer strSql = new StringBuffer();
		Vector vector = new Vector();
		OB_OperationReminderInfo resultInfo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			initDAO();
			strSql.append("select * from ob_operationreminder ");
			strSql.append("where OfficeID=? and CurrencyID=? and ClientID=?");
			ps = transConn.prepareStatement(strSql.toString());
    		
    		int index = 1;
    		ps.setLong(index++, awake.getOfficeID());
    		ps.setLong(index++, awake.getCurrencyID());
    		ps.setLong(index++, awake.getClientID());
    		rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			resultInfo = new OB_OperationReminderInfo();
    			resultInfo.setId(rs.getLong("Id"));
    			resultInfo.setOfficeId(rs.getLong("OfficeId"));
    			resultInfo.setCurrencyId(rs.getLong("CurrencyId"));
    			resultInfo.setClientId(rs.getLong("ClientId"));
    			resultInfo.setOperationId(rs.getLong("OperationId"));
    			resultInfo.setOperationFate(rs.getLong("OperationFate"));
    			resultInfo.setStatusId(rs.getLong("Status"));
    			vector.add(resultInfo);
    		}
    		rs.close();
    		ps.close();
            finalizeDAO();
    		System.out.println(strSql);
		}
        catch(Exception e) {
            e.printStackTrace();
            throw new IException("程序异常",e);
        }
        finally {
        	finalizeDAO();
        }
        
        if(vector.size() == 0) {
        	return null;
        }
        else {
        	return vector;
        }
    }
    
    /**
     * 通过部分OperationReminderInfo信息进行查询
     * @param info
     * @return
     * @throws IException
     */
    public OB_OperationReminderInfo findOperationReminder(OB_OperationReminderInfo info) throws IException {
		StringBuffer strSql = new StringBuffer();
		OB_OperationReminderInfo resultInfo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
    	try {
    		initDAO();
    		strSql.append("select * from ob_operationreminder ");
    		strSql.append("where OfficeID=? and CurrencyID=? and ClientID=? and OperationId=?");
    		ps = transConn.prepareStatement(strSql.toString());
    		
    		int index = 1;
    		ps.setLong(index++, info.getOfficeId());
    		ps.setLong(index++, info.getCurrencyId());
    		ps.setLong(index++, info.getClientId());
    		ps.setLong(index++, info.getOperationId());
    		rs = ps.executeQuery();
    		while(rs.next()) {
    			resultInfo = new OB_OperationReminderInfo();
    			resultInfo.setId(rs.getLong("Id"));
    			resultInfo.setOfficeId(rs.getLong("OfficeId"));
    			resultInfo.setCurrencyId(rs.getLong("CurrencyId"));
    			resultInfo.setClientId(rs.getLong("ClientId"));
    			resultInfo.setOperationId(rs.getLong("OperationId"));
    			resultInfo.setOperationFate(rs.getLong("OperationFate"));
    			resultInfo.setStatusId(rs.getLong("Status"));
    		}
    		rs.close();
    		ps.close();
            finalizeDAO();
    		System.out.println(strSql);
    	}
        catch(Exception e) {
            e.printStackTrace();
            throw new IException("程序异常",e);
        }
        finally {
        	finalizeDAO();
        }
        return resultInfo;
    }
}
