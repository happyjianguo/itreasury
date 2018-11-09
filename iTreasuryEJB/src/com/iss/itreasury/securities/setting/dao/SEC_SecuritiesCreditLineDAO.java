/*
 * Created on 2004-5-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.setting.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.dataentity.SecuritiesCreditLineInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_SecuritiesCreditLineDAO extends SecuritiesDAO {
	public SEC_SecuritiesCreditLineDAO() {
		super("SEC_SecuritiesCreditLine");
	}
	
	/**
	 * ��ѯ�����õļ�¼ID
	 * @param lSecuritiesTypeID
	 * @return ID
	 * @throws ITreasuryDAOException
	 */
	public long findIDBySecuritiesID(long lSecuritiesId) throws ITreasuryDAOException {
		long lReturn = -1;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = "select ID from SEC_SecuritiesCreditLine where SecuritiesID = "+lSecuritiesId+" and StatusID > 0 order by ID";
		log.info("SQL======"+strSQL);
		try {//�ڲ�ά��RS��PS��CONN�����򽫻������ͻ
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
			new ITreasuryDAOException("���ݿ��ȡ��[SEC_SecuritiesCreditLine]�ظ����ò����쳣",e);
		} finally {
		}
		this.finalizeDAO();
		return lReturn;
	}

	public Collection querySecuritiesTypeCreditLine(SecuritiesCreditLineInfo queryInfo) throws ITreasuryDAOException {
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = " select * from SEC_SecuritiesCreditLine \n";
		strSQL+= " where statusId >0 \n";
		if(queryInfo.getQuerySecuritiesTypeId() >0)
		{
			strSQL+= " and securitiesTypeId = "+ queryInfo.getQuerySecuritiesTypeId() +" \n";
		}
		if(queryInfo.getQuerySecuritiesId() >0)
		{
			strSQL+= " and securitiesId = "+ queryInfo.getQuerySecuritiesId() +" \n";
		}
		//if(queryInfo.getQueryCreditLineFrom() != 0 && queryInfo.getQueryCreditLineTo() != 0)
		//{
			//strSQL+= " and creditLine between "+ queryInfo.getQueryCreditLineFrom()*10000 +" and "+ queryInfo.getQueryCreditLineTo()*10000 +" \n"; 
		//}
		if(queryInfo.getQueryCreditLineFrom()>0 && queryInfo.getQueryCreditLineTo()>0){
			strSQL += " and creditLine >= " + queryInfo.getQueryCreditLineFrom()*10000;
			strSQL += " and creditLine <= " + queryInfo.getQueryCreditLineTo()*10000;
		}
		if(queryInfo.getQueryInputUserId() >0)
		{
			strSQL+= " and inputUserId = "+ queryInfo.getQueryInputUserId() +" \n";
		}
		
		strSQL+= " order by securitiesId,Id";
		System.out.println("SQL======"+strSQL);
		log.info("SQL======"+strSQL);
		try {//�ڲ�ά��RS��PS��CONN�����򽫻������ͻ
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			while(localRS.next()) 
			{
				SecuritiesCreditLineInfo info = new SecuritiesCreditLineInfo();
				info.setId(localRS.getLong("Id"));
				info.setSecuritiesTypeId(localRS.getLong("securitiesTypeId"));
				info.setSecuritiesId(localRS.getLong("securitiesId"));
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
			new ITreasuryDAOException("���ݿ��ȡ��[SEC_SecuritiesCreditLine]��ѯ�����쳣",e);
		} finally {
		}
		this.finalizeDAO();
		return list;
	}
	//	��ѯ֤ȯ�������ʷ��¼
	public Collection querySecuritiesCreditLineHistory(long securitiesId) throws ITreasuryDAOException {
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		this.initDAO();
		String strSQL = "";
		strSQL = " select * from SEC_SecuritiesCreditLine \n";
		strSQL+= " where securitiesId = "+ securitiesId +" \n";
		strSQL+= " order by securitiesId,Id";
		
		log.info("SQL======"+strSQL);
		try {//�ڲ�ά��RS��PS��CONN�����򽫻������ͻ
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			while(localRS.next()) 
			{
				SecuritiesCreditLineInfo info = new SecuritiesCreditLineInfo();
				info.setId(localRS.getLong("Id"));
				info.setSecuritiesTypeId(localRS.getLong("securitiesTypeId"));
				info.setSecuritiesId(localRS.getLong("securitiesId"));
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
			new ITreasuryDAOException("���ݿ��ȡ��[SEC_SecuritiesCreditLine]��ѯ�����쳣",e);
		} finally {
		}
		this.finalizeDAO();
		return list;
	}

	
}
