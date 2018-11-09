/*
 * Created on 2004-5-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.dataentity.SecuritiesTypeCreditLineInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Database;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_SecuritiesTypeCreditLineDAO extends SecuritiesDAO {
	public SEC_SecuritiesTypeCreditLineDAO() {
		super("SEC_SecuritiesTypeCreditLine");
	}
	/**
	 * 查询所设置的记录ID
	 * @param lSecuritiesTypeID
	 * @return ID
	 * @throws ITreasuryDAOException
	 */
	public long findIDBySecuritiesTypeID(long lSecuritiesTypeId) throws ITreasuryDAOException {
		long lReturn = -1;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = "select ID from SEC_SecuritiesTypeCreditLine where SecuritiesTypeID = "+lSecuritiesTypeId+" and StatusID > 0 order by ID";
		log.info("SQL======"+strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			if(localRS.next()) {
				lReturn = localRS.getLong("ID");
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
			new ITreasuryDAOException("数据库获取表[SEC_SecuritiesTypeCreditLine]重复设置产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return lReturn;
	}

	public Collection querySecuritiesTypeCreditLine(SecuritiesTypeCreditLineInfo queryInfo) throws ITreasuryDAOException {
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = " select * from SEC_SecuritiesTypeCreditLine \n";
		strSQL+= " where statusId >0 \n";
		if(queryInfo.getQuerySecuritiesTypeId() >0)
		{
			strSQL+= " and securitiesTypeId = "+ queryInfo.getQuerySecuritiesTypeId() +" \n";
		}
		if(queryInfo.getQueryCreditLineFrom() != 0 && queryInfo.getQueryCreditLineTo() != 0)
		{
			strSQL+= " and creditLine between "+ queryInfo.getQueryCreditLineFrom()*10000 +" and "+ queryInfo.getQueryCreditLineTo()*10000 +" \n"; 
		}
		if(queryInfo.getQueryInputUserId() >0)
		{
			strSQL+= " and inputUserId = "+ queryInfo.getQueryInputUserId() +" \n";
		}
		
		strSQL+= " order by securitiesTypeId,Id";
		
		log.info("SQL======"+strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			while(localRS.next()) 
			{
				SecuritiesTypeCreditLineInfo info = new SecuritiesTypeCreditLineInfo();
				info.setId(localRS.getLong("Id"));
				info.setSecuritiesTypeId(localRS.getLong("securitiesTypeId"));
				info.setCreditLine(localRS.getDouble("creditLine")/10000);
				info.setStatusId(localRS.getLong("statusId"));
				info.setInputUserId(localRS.getLong("inputUserId"));
				info.setInputDate(localRS.getTimestamp("inputDate"));
				info.setUpdateUserId(localRS.getLong("updateUserId"));
				info.setUpdateDate(localRS.getTimestamp("updateDate"));
				
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
			new ITreasuryDAOException("数据库获取表[SEC_SecuritiesTypeCreditLine]查询产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return list;
	}
	
	//查询证券类别的历史记录
	public Collection querySecuritiesTypeCreditLineHistory(long securitiesTypeId) throws ITreasuryDAOException {
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = " select * from SEC_SecuritiesTypeCreditLine \n";
		strSQL+= " where securitiesTypeId = "+ securitiesTypeId +" \n";
		strSQL+= " order by securitiesTypeId,Id";
		
		log.info("SQL======"+strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			while(localRS.next()) 
			{
				SecuritiesTypeCreditLineInfo info = new SecuritiesTypeCreditLineInfo();
				info.setId(localRS.getLong("Id"));
				info.setSecuritiesTypeId(localRS.getLong("securitiesTypeId"));
				info.setCreditLine(localRS.getDouble("creditLine")/10000);
				info.setStatusId(localRS.getLong("statusId"));
				info.setInputUserId(localRS.getLong("inputUserId"));
				info.setInputDate(localRS.getTimestamp("inputDate"));
				info.setUpdateUserId(localRS.getLong("updateUserId"));
				info.setUpdateDate(localRS.getTimestamp("updateDate"));
				
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
			new ITreasuryDAOException("数据库获取表[SEC_SecuritiesTypeCreditLine]查询产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return list;
	}
	/**
	 * 得到该证券类别所设置的授信额度
	 * @param lSecuritiesTypeId
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double findCreditLineBySecuritiesTypeID(long lSecuritiesTypeId) throws ITreasuryDAOException {
		double dReturn = 0.0;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = "select CreditLine from SEC_SecuritiesTypeCreditLine where SecuritiesTypeID = "+lSecuritiesTypeId+" and StatusID > 0";
		log.info("SQL======"+strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			if(localRS.next()) {
				dReturn = localRS.getDouble("CreditLine")/10000;
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
			new ITreasuryDAOException("数据库获取表[SEC_SecuritiesTypeCreditLine]重复设置产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return dReturn;
	}
	/**
	 * 得到所有设置的证券种类
	 * @author ruixie
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public Collection querySecuritiesType() throws ITreasuryDAOException {
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = " select distinct securitiesTypeId from SEC_SecuritiesTypeCreditLine where statusId >0 order by securitiesTypeId";
		
		log.info("SQL======"+strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			while(localRS.next()) 
			{
				SecuritiesTypeCreditLineInfo info = new SecuritiesTypeCreditLineInfo();
				info.setSecuritiesTypeId(localRS.getLong("securitiesTypeId"));
				
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
			new ITreasuryDAOException("数据库获取表[SEC_SecuritiesTypeCreditLine]查询产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return list;
	}
	
}
