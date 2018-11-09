/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	���� 
 * Company:             iSoftStone
 * @version				iTreasury3.2����
 * Description:         ��Ʒ��3.2�ڽ���,��������������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						��Ϊ�ù�����ӵ�ͳһ�ı�׼����������ù���         
 */
package com.iss.itreasury.system.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.approval.dataentity.ApprovalOpinionInfo;
import com.iss.itreasury.util.IException;

public class ApprovalOpinionDao extends ITreasuryDAO implements
		java.io.Serializable {

	public ApprovalOpinionDao() {
		super("SYS_APPROVALOPINION");
	}

	public ApprovalOpinionDao(Connection con) {
		super("SYS_APPROVALOPINION", con);
	}

	public long findApprovalID(ApprovalOpinionInfo info) {
		long lReturn = -1;

		return lReturn;
	}

	public Collection queryByCondition(ApprovalOpinionInfo info)
			throws IException {
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try {
			initDAO();
			strSQL = "select * from SYS_APPROVALOPINION where 1=1";
			if (info.getId() > 0) {
				strSQL = strSQL + " and id = " + info.getId();
			}
			if (info.getOfficeID() > 0) {
				strSQL = strSQL + " and officeid = " + info.getOfficeID();
			}
//			jzw 2010-06-29 ��ѯ��ʱ�������Ƕ�����֣�����ȥ������������ѯ
//			if (info.getCurrencyID() > 0) {
//				strSQL = strSQL + " and currencyid = " + info.getCurrencyID();
//			}
			if (info.getModuleID() > 0) {
				strSQL = strSQL + " and moduleid = " + info.getModuleID();
			}
			if (info.getCode() != null && info.getCode().length() > 0) {
				strSQL = strSQL + " and code like '" + info.getCode() + "'";
			}
			if (info.getDescription() != null
					&& info.getDescription().length() > 0) {
				strSQL = strSQL + " and description like '"
						+ info.getDescription() + "'";
				;
			}
			if (info.getStatusID() > 0) {
				strSQL = strSQL + " and statusid = " + info.getStatusID();
			}
			if (info.getInputUserID() > 0) {
				strSQL = strSQL + " and inputuserid = " + info.getInputUserID();
			}
			if (info.getInputDate() != null) {
				strSQL = strSQL + " and inputdate = " + info.getStatusID();
			}
			strSQL = strSQL + " order by id,inputdate ";

			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next()) {
				ApprovalOpinionInfo tempInfo = new ApprovalOpinionInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOfficeID(localRS.getLong("officeid"));
				tempInfo.setCurrencyID(localRS.getLong("currencyid"));
				tempInfo.setModuleID(localRS.getLong("moduleid"));
				tempInfo.setCode(localRS.getString("code"));
				tempInfo.setDescription(localRS.getString("description"));
				tempInfo.setStatusID(localRS.getLong("statusID"));
				tempInfo.setInputUserID(localRS.getLong("inputUserID"));
				tempInfo.setInputDate(localRS.getTimestamp("inputDate"));
				v_Return.add(tempInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("Gen_E001", e);
		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				e1.printStackTrace();
			}
		}

		return v_Return;
	}

	/**
	 * �˷�������ģ���ID��������������� ���ʱ��:2007-04-24 �����:����
	 * 
	 * @param moudleID
	 * @return int
	 * @throws IException
	 */
	public String getStringMaxcode(long moduleID) throws IException {

		
		String strSql = null;//sql���
		PreparedStatement prstmt = null;
		ResultSet rs = null;
		String maxCode = null;//����moduleID�õ�����codeֵ
		String newmaxCode = null;//���һ���µ�code
		String Codeq = null;//���moduleID���moduleID�ĳ���С��2��ת����0+moduleID����ʽ�����򲻱�
		String Codeh = null;//code�ĵ���ֵ
		String strnum = null;
		String zero = "0";
		String zeroy = "01";
		try {
			initDAO();
			strSql = "select max(to_number(t.code)) from sys_approvalopinion t where 1=1 and moduleid=?";
			prstmt = super.prepareStatement(strSql.toString());
			prstmt.setLong(1, moduleID);
			rs = prstmt.executeQuery();
			while (rs.next()) {
				maxCode = rs.getString(1);
			}
			if (maxCode == null || maxCode == "" || maxCode.length() < 3) {
				if (moduleID < 10) {
					newmaxCode = DataFormat.formatInt(moduleID, 2) + zeroy;
				} else {
					newmaxCode = moduleID + zeroy;
				}
			} else {

				if (moduleID < 10) {
					newmaxCode = zero + maxCode;
					Codeq = DataFormat.formatInt(moduleID, 2);
				} else {
					Codeq = String.valueOf(moduleID);
					newmaxCode = maxCode;
				}
				Codeh = newmaxCode.substring(Codeq.length(), newmaxCode
						.length());

				int c = Integer.parseInt(Codeh);
				int num = ++c;

				if (num < 10) {
					strnum = DataFormat.formatInt(num, 2);

				} else {
					strnum = String.valueOf(num);

				}
				newmaxCode = Codeq + strnum;

			}

		} catch (Exception ex) {

			ex.printStackTrace();
			throw new IException("��ѯ����������ʧ�ܣ�����", ex);

		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				e1.printStackTrace();
			}
		}
		return newmaxCode;
	}

}
