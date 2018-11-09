/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu 
 * @version
 * Date of Creation     2004-03-17
 */
package com.iss.itreasury.securities.setting.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.exception.SettingException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.setting.dataentity.ExchangeCenterInfo;

public class SEC_ExchangeCenterDAO extends SecuritiesDAO {
	public SEC_ExchangeCenterDAO() {
		super("SEC_ExchangeCenter");
	}

	/**
	 * �ӱ�[SEC_ExchangeCenter]��ȡ����֤��������
	 * @param  nothing
	 * @return String
	 * @exception throws ITreasuryDAOException
	 */
	public String getMaxCode() throws ITreasuryDAOException {
		String sReturn = "";
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT LPAD((NVL(MAX(code),0)+1),5,'0') maxCode FROM  SEC_ExchangeCenter \n");
		log.info("SQL="+bufSQL.toString());
		try {//�ڲ�ά��RS��PS��CONN�����򽫻������ͻ
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				sReturn = localRS.getString("maxCode");
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
			new ITreasuryDAOException("���ݿ��ȡ��[SEC_ExchangeCenter]����֤������������쳣",e);
		} finally {
		}
		this.finalizeDAO();
		return sReturn==null || sReturn==""?"":sReturn;
	}

	/**
	 * �ӱ���ȡ���ı�ע���
	 * @param  nothing
	 * @return String
	 * @exception throws ITreasuryDAOException
	 */
	public void checkRemarkCode(String sCode) throws ITreasuryDAOException, SettingException {
		long lReturn = 0;
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT COUNT(*) count FROM SEC_ExchangeCenter WHERE Code = '"+sCode.trim()+"' \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SettingStatus.CHECKED+" \n");
		log.info("SQL="+bufSQL.toString());
		try {//�ڲ�ά��RS��PS��CONN�����򽫻������ͻ
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				lReturn = localRS.getLong("count");
			}
			if(localRS != null) {
				localRS.close();
				localRS = null;
			}
			if(localPS != null) {
				localPS.close();
				localPS = null;
			}
			if (lReturn != 0) {
				throw new Exception("");
			}
		} catch (Exception e) {
			throw new SettingException(sCode);
		} finally {
		}
		this.finalizeDAO();
		return;
	}
	
	/**
	 * �жϽ����г������Ƿ��Ѿ�����
	 * @param  nothing
	 * @return String
	 * @throws IException 
	 * @exception throws ITreasuryDAOException
	 */
	public void checkRemarkName(String sName , long lID) throws IException {
		long lReturn = 0;
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT COUNT(*) count FROM SEC_ExchangeCenter WHERE name = '"+sName.trim()+"' \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SettingStatus.CHECKED+" \n");
		if(lID > 0)
		{
			bufSQL.append(" and id <> " + lID);
		}
		log.info("SQL="+bufSQL.toString());
		try {//�ڲ�ά��RS��PS��CONN�����򽫻������ͻ
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				lReturn = localRS.getLong("count");
			}
			if(localRS != null) {
				localRS.close();
				localRS = null;
			}
			if(localPS != null) {
				localPS.close();
				localPS = null;
			}
			if (lReturn != 0) {
				throw new Exception("");
			}
		} catch (Exception e) {
			throw new IException("֤ȯ�����г����ơ�" + sName + "���Ѿ����ڣ�",e);
		} finally {
		}
		this.finalizeDAO();
		return;
	}

	/**
	 * �ӱ���ȡ��[SEC_ExchangeCenter]����
	 * @param  nothing
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Collection find() throws ITreasuryDAOException {
		ArrayList list = new ArrayList();
		PreparedStatement localPS = null;
		ResultSet localRS = null;

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT \n");
		bufSQL.append("	NVL(ID,-1) ID, \n");
		bufSQL.append("	NVL(CODE,'') CODE, \n");
		bufSQL.append("	NVL(NAME,'') NAME, \n");
		bufSQL.append("	NVL(STATUSID,-1) STATUSID, \n");
		bufSQL.append("	NVL(INPUTUSERID,-1) INPUTUSERID, \n");
		bufSQL.append("	NVL(INPUTDATE,NULL) INPUTDATE, \n");
		bufSQL.append("	NVL(UPDATEUSERID,-1) UPDATEUSERID, \n");
		bufSQL.append("	NVL(UPDATEDATE,NULL) UPDATEDATE \n");
		bufSQL.append("FROM SEC_ExchangeCenter \n");
		bufSQL.append("WHERE ID >= 0 \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SettingStatus.CHECKED+" \n");
		bufSQL.append("ORDER BY ID \n");
		log.info("SQL="+bufSQL.toString());
		try {
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				ExchangeCenterInfo info = new ExchangeCenterInfo();
				//ID	NOT NULL	NUMBER
				info.setId(localRS.getLong("ID"));						//����,Ψһ����
				//CODE		VARCHAR2(5)
				info.setCode(localRS.getString("CODE"));				//��λ����, ÿ������ʱ�Զ���ʾ������λ�����û������޸ġ�
				//DESCRIPTION		VARCHAR2(250)
				info.setName(localRS.getString("NAME"));				//ҵ������
				//STATUSID		NUMBER
				info.setStatusID(localRS.getLong("STATUSID"));			//״̬��1����Ч��-1����ɾ��
				//INPUTUSERID		NUMBER
				info.setInputUserID(localRS.getLong("INPUTUSERID"));	//¼����
				//INPUTDATE		DATE
				info.setInputDate(localRS.getTimestamp("INPUTDATE"));	//¼��ʱ��
				//UPDATEUSERID		NUMBER
				info.setUpdateUserID(localRS.getLong("UPDATEUSERID"));	//�޸���
				//UPDATEDATE		DATE
				info.setUpdateDate(localRS.getTimestamp("UPDATEDATE"));	//�޸�ʱ��

				list.add(info);
			}
		} catch (Exception e) {
			new ITreasuryDAOException("���ݿ��ȡ��ע��[SEC_ExchangeCenter]���ݲ����쳣",e);
		}
		this.finalizeDAO();

		return list;
	}
}