/*
 * Created on 2007-07-23
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.OBCommitTime;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

public class Sett_OBCommitTimeDAO extends SettlementDAO {
	
	public Sett_OBCommitTimeDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_obcommittime";
	}

	public Sett_OBCommitTimeDAO()
	{
		super();
		this.strTableName = "sett_obcommittime";
	}
	
	/**
	 * 
	 * @param cTime
	 * @throws SettlementException
	 * @throws ITreasuryDAOException
	 */
	public void save(OBCommitTime cTime) throws SettlementException, ITreasuryDAOException{
		try{
			long id = getMaxId(strTableName);
			
			String strSql = "insert into "+ strTableName +" values(?,?,?,?,?,?,?,?)";
			transConn = this.getConnection();
			transPS = transConn.prepareStatement(strSql);
			int index = 1;
			transPS.setLong(index++, id);
			transPS.setLong(index++, cTime.getOfficeId());
			transPS.setLong(index++, cTime.getCurrencyId());
			transPS.setString(index++, cTime.getCommitTime());
			transPS.setLong(index++, cTime.getIsControl());
			transPS.setLong(index++, cTime.getInputUserId());
			transPS.setTimestamp(index++, Env.getSystemDateTime(cTime.getOfficeId(), cTime.getCurrencyId()));
			transPS.setLong(index++, cTime.getStatus());
			
			this.executeUpdate();
			this.finalizeDAO();
		}
        catch(Exception e) {
            e.printStackTrace();
            e.printStackTrace();throw new SettlementException();
        }
        finally {
        	this.finalizeDAO();
        }
	}
	
	/**
	 * 
	 * @param cTime
	 * @throws SettlementException
	 * @throws ITreasuryDAOException
	 */
	public void update(OBCommitTime cTime) throws SettlementException, ITreasuryDAOException{
		try{
			String strSql = "update "+ strTableName +" set commitTime=?,isControl=? where id=?";
			transConn = this.getConnection();
			transPS = transConn.prepareStatement(strSql);
			int index = 1;
			transPS.setString(index++, cTime.getCommitTime());
			transPS.setLong(index++, cTime.getIsControl());
			transPS.setLong(index++, cTime.getId());
			
			this.executeUpdate();
			this.finalizeDAO();
		}
        catch(Exception e) {
            e.printStackTrace();
            e.printStackTrace();throw new SettlementException();
        }
        finally {
        	this.finalizeDAO();
        }
	}
	
	/**
	 * 
	 * @param cTime
	 * @return
	 * @throws SettlementException
	 * @throws ITreasuryDAOException
	 */
	public OBCommitTime findOBCommitTime(OBCommitTime cTime) throws SettlementException, ITreasuryDAOException{
		OBCommitTime result = null;
		try{
			String strSql = "select * from "+ strTableName +" where officeId = ? and currencyId = ?";
			transConn = this.getConnection();
			transPS = transConn.prepareStatement(strSql);
			int index = 1;
			transPS.setLong(index++, cTime.getOfficeId());
			transPS.setLong(index++, cTime.getCurrencyId());
			
			transRS = this.executeQuery();
			while(transRS.next()){
				result = new OBCommitTime();
				result.setId(transRS.getLong("id"));
				result.setOfficeId(transRS.getLong("officeId"));
				result.setCurrencyId(transRS.getLong("currencyId"));
				result.setCommitTime(transRS.getString("commitTime"));
				result.setIsControl(transRS.getLong("isControl"));
				result.setInputUserId(transRS.getLong("inputUserId"));
				result.setInputDate(transRS.getTimestamp("inputDate"));
				result.setStatus(transRS.getLong("status"));
			}
			
			this.finalizeDAO();
		}
        catch(Exception e) {
            e.printStackTrace();
            e.printStackTrace();throw new SettlementException();
        }
        finally {
        	this.finalizeDAO();
        }
        return result;
	}
	
    /**
     * 得到最大ID
     * @param tableName
     * @return
     * @throws SettlementException 
     * @throws ITreasuryDAOException 
     * @throws IException
     * @throws SQLException 
     */
    private long getMaxId(String tableName) throws SettlementException, ITreasuryDAOException {
    	try {
    		//conn = Database.getConnection();
        	long lMaxID = -1;
    		String strSql = "select nvl(max(id),0)+1 maxid from " + tableName;
    		transConn = this.getConnection();
    		transPS = transConn.prepareStatement(strSql);
    		transRS = this.executeQuery();
            if(transRS!=null && transRS.next()) {
            	lMaxID = transRS.getLong("maxid");
            }
            this.finalizeDAO();
            return lMaxID;
        }
        catch(Exception e) {
             e.printStackTrace();
             e.printStackTrace();throw new SettlementException();
        }
        finally {
        	this.finalizeDAO();
        }
    }

}
