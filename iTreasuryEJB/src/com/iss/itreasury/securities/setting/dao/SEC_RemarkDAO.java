/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu 
 * @version
 * Date of Creation     2004-03-15
 */
package com.iss.itreasury.securities.setting.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.util.Database;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.exception.SettingException;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.setting.dataentity.RemarkInfo;

public class SEC_RemarkDAO extends SecuritiesDAO {
	public SEC_RemarkDAO() {
		super("SEC_Remark");
	}

	/**
	 * �ӱ���ȡ���ı�ע���
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
		bufSQL.append("SELECT LPAD((NVL(MAX(code),0)+1),5,'0') maxCode FROM  sec_Remark \n");
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
			new ITreasuryDAOException("���ݿ��ȡ���ı�ע��Ų����쳣",e);
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

		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT COUNT(*) count FROM sec_Remark WHERE Code = '"+sCode.trim()+"' \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SettingStatus.CHECKED+" \n");
		log.info("SQL="+bufSQL.toString());
		try {//�ڲ�ά��RS��PS��CONN�����򽫻������ͻ
			this.initDAO();
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
			this.finalizeDAO();
		}

		return;
	}

	/**
	 * �ӱ���ȡ��ע��[sec_Remark]����
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
		bufSQL.append("	NVL(DESCRIPTION,'') DESCRIPTION, \n");
		bufSQL.append("	NVL(BUSINESSTYPE,-1) BUSINESSTYPE, \n");
		bufSQL.append("	NVL(STATUSID,-1) STATUSID, \n");
		bufSQL.append("	NVL(INPUTUSERID,-1) INPUTUSERID, \n");
		bufSQL.append("	NVL(INPUTDATE,NULL) INPUTDATE, \n");
		bufSQL.append("	NVL(UPDATEUSERID,-1) UPDATEUSERID, \n");
		bufSQL.append("	NVL(UPDATEDATE,NULL) UPDATEDATE \n");
		bufSQL.append("FROM SEC_Remark \n");
		bufSQL.append("WHERE ID >= 0 \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SettingStatus.CHECKED+" \n");
		bufSQL.append("ORDER BY ID \n");
		log.info("SQL="+bufSQL.toString());
		try {
			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				RemarkInfo info = new RemarkInfo();
				//ID	NOT NULL	NUMBER
				info.setId(localRS.getLong("ID"));						//����,Ψһ����
				//CODE		VARCHAR2(5)
				info.setCode(localRS.getString("CODE"));				//��λ����, ÿ������ʱ�Զ���ʾ������λ�����û������޸ġ�
				//DESCRIPTION		VARCHAR2(250)
				info.setDescription(localRS.getString("DESCRIPTION"));	//��ע����
				//BUSINESSTYPE		NUMBER
				info.setBusinessType(localRS.getLong("BUSINESSTYPE"));	//ҵ������
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
			new ITreasuryDAOException("���ݿ��ȡ��ע��[sec_Remark]���ݲ����쳣",e);
		}
		this.finalizeDAO();

		return list;
	}
}