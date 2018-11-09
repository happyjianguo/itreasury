/*
 * Created on 2005-4-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.StandardAbstractInfo;
import com.iss.itreasury.util.Constant;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_StandardAbstractDAO extends SettlementDAO
{
	/**
	 * 
	 */
	public Sett_StandardAbstractDAO()
	{
		super();
		this.strTableName = "Sett_StandardAbstract";
		setUseMaxID();
	}
	public Sett_StandardAbstractDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "Sett_StandardAbstract";
		setUseMaxID();
	}
	public long saveStandardAbstract(StandardAbstractInfo info) throws SettlementException
	{
		String sttransRSQL = null;
		long lResult = -1;
		try {
			this.initDAO();
			// if Code duplicate
			sttransRSQL = "select id from Sett_StandardAbstract where nStatusID > 0 and nofficeid=? and (sCode = ? or SDESC=?)";
			transPS = transConn.prepareStatement(sttransRSQL);
			transPS.setLong(1, info.getNOfficeID());
			transPS.setString(2, info.getSCode());
			transPS.setString(3, info.getSDesc());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				long lVar = transRS.getLong("id");
				if (lVar != info.getId()) {
					return -1;
				}

			}
			if (info.getId() < 0) {
				lResult = this.add(info);
			} else {
				this.update(info);
				lResult = info.getId();
			}

		} catch (Exception e) {
			throw new SettlementException();
		} finally {
			try {
				this.finalizeDAO();
			} catch (Exception ex) {
				
			}
		}

		return lResult;

	
	}
	public long getMaxAbstractCode(long lOfficeID) throws SettlementException {

		long lResult = -1;

		StringBuffer sb = new StringBuffer();

		try {
			this.initDAO();
			sb.append(" select nvl(min(id),0) maxcode \n");
			sb.append(" from (select id from Sett_STANDARDABSTRACT  where nofficeid=? \n");
			sb.append("       minus \n");
			sb.append("       select to_number(scode) scode from Sett_STANDARDABSTRACT  where nofficeid=? \n");
			sb.append("       ) \n");
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lOfficeID);
			transPS.setLong(2, lOfficeID);
			transRS = transPS.executeQuery();

			if (transRS.next()) {
				lResult = transRS.getLong(1);
			}
			if (lResult == 0) {
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				sb.setLength(0);

				sb.append(" select max(to_number(scode)) as nMaxAccountType from Sett_STANDARDABSTRACT where nofficeid=? ");
				transPS = transConn.prepareStatement(sb.toString());
				transPS.setLong(1, lOfficeID);
				transRS = transPS.executeQuery();

				if (transRS.next()) {
					lResult = transRS.getLong(1) + 1;
				}
			}

		} catch (Exception e) {
			throw new SettlementException();
		} finally {
			try {
				this.finalizeDAO();
			} catch (Exception ex) {
				
			}
		}

		return lResult;
	}
	
	public long updateStatus(long id, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			//strSQLBuffer.append("dtModify = sysdate, \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);				
		}
		return id;
	}
	
	public long getMaxCode(long lOfficeID) throws SettlementException {

		long lResult = -1;

		StringBuffer sb = new StringBuffer();

		try {
			this.initDAO();
			sb.append(" select nvl(to_number(max(scode))+1,1) from Sett_STANDARDABSTRACT where nofficeid=?");
			sb.append(" and nstatusid = ");
			sb.append(String.valueOf(Constant.RecordStatus.VALID));
			transPS = transConn.prepareStatement(sb.toString());
			transPS.setLong(1, lOfficeID);
			transRS = transPS.executeQuery();

			if (transRS.next()) {
				lResult = transRS.getLong(1);
			}

		} catch (Exception e) {
			throw new SettlementException();
		} finally {
			try {
				this.finalizeDAO();
			} catch (Exception ex) {
				
			}
		}

		return lResult;
	}
	
	//查询所有有效记录 2010-8-13 add by xiangzhou
    public Collection findAllStandardAbstract(StandardAbstractInfo info) throws SettlementException, ITreasuryDAOException, SQLException
    {
    	Collection coll = new ArrayList();
        try
        {
        	this.initDAO();
            StringBuffer buffer = new StringBuffer("");
            
            buffer.append(" select * ");
            buffer.append(" from " + this.strTableName + " \n");
            buffer.append(" where nofficeid = ? ");
            buffer.append(" and nstatusid = ? ");
            buffer.append(" order by scode asc");

            System.out.println(buffer.toString());

            transPS = transConn.prepareStatement(buffer.toString());
            transPS.setLong(1, info.getNOfficeID());
            transPS.setLong(2, info.getNStatusID());
            transRS = transPS.executeQuery();
            while(transRS.next()){
            	StandardAbstractInfo sInfo = new StandardAbstractInfo();
            	sInfo.setId(transRS.getLong(1));
            	sInfo.setNOfficeID(transRS.getLong(2));
            	sInfo.setSCode(transRS.getString(3));
            	sInfo.setSDesc(transRS.getString(4));
            	sInfo.setNAbstractTypeID(transRS.getLong(5));
            	sInfo.setNStatusID(transRS.getLong(6));
            	sInfo.setNDetailID(transRS.getLong(7));
            	coll.add(sInfo);
            }
        } finally {
			try {
				this.finalizeDAO();
			} catch (Exception ex) {
				
			}
		}

        return coll;
    }
    
    public String queryStandardAbstractSql(StandardAbstractInfo info) {
		
    	 StringBuffer buffer = new StringBuffer("");
         
         buffer.append(" select * ");
         buffer.append(" from " + this.strTableName + " \n");
         buffer.append(" where nofficeid = "+info.getNOfficeID());
         buffer.append(" and nstatusid = "+ info.getNStatusID());
         buffer.append(" order by scode asc");
         System.out.println(buffer.toString());
         
        String sql= buffer.toString();
        
		return sql;
		
	}
    
    
    
    
}
