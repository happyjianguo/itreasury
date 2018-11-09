/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.lang.Integer;
import com.iss.itreasury.clientmanage.client.dao.ClientmanageDAO;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.configtool.constantmanage.dao.ConstantManageDao;
import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantManageInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.encrypt.EncryptManager;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLEntryDefinitionDAO;
import com.iss.itreasury.settlement.setting.dataentity.OfficeInfo;
import com.iss.itreasury.settlement.setting.dataentity.validateInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.bizdelegation.PrivilegeDelegation;
import com.iss.itreasury.system.privilege.dao.Sys_GroupDAO;
import com.iss.itreasury.system.privilege.dao.Sys_Group_Of_UserDAO;
import com.iss.itreasury.system.privilege.dao.Sys_PrivilegeDAO;
import com.iss.itreasury.system.privilege.dao.Sys_Privilege_Of_GroupDAO;
import com.iss.itreasury.system.privilege.dao.Sys_UserDAO;
import com.iss.itreasury.system.privilege.dataentity.QueryOfficeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_Group_Of_UserInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_PrivilegeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_Privilege_Of_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserAuthorityInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;


/**
 * @author weilu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_OfficeDAO extends SettlementDAO {
	/**
	 * 
	 */
	public Sett_OfficeDAO() {
		super("Office", "SEQ_sett_office", true);
		// TODO Auto-generated constructor stub
	}
	public Sett_OfficeDAO(Connection conn) {
		super("Office", "SEQ_sett_office", true,conn);
		// TODO Auto-generated constructor stub
	}
	/**
	 * ��ѯ���а��´����
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>��ѯ���а��´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>����Collection��������OfficeInfo
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lPageLineCount
	 *            ÿҳ��������
	 * @param lPageNo
	 *            �ڼ�ҳ����
	 * @param lOrderParam
	 *            �������������ݴ˲��������������������
	 * @param isDesc
	 *            �������
	 * @return Collection
	 * @exception SettlementException
	 */
	public Collection findAllOffice(long lCurrencyID, long lPageLineCount,
			long lPageNo, long lOrderParam, boolean isDesc)
			throws SettlementException {
		Vector v = new Vector();
		String strSQL = null;
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try {
			initDAO();
			// ��������Ľ����
			StringBuffer sb = new StringBuffer();
			sb
					.append(" select Office.*,sett_CurrencySubject.sSubject,sett_account.SACCOUNTNO SACCOUNTNO,sett_OFFICERESERVEDACCOUNT.NRESERVEDACCOUNTID RESERVEDACCOUNT \n");
			sb
					.append(" from sett_account, Office,sett_OFFICERESERVEDACCOUNT,sett_CurrencySubject \n");
			sb
					.append(" where sett_account.id(+)=sett_OFFICERESERVEDACCOUNT.NRESERVEDACCOUNTID \n");
			sb.append(" and Office.ID=sett_CurrencySubject.nRecordID(+) ");
			sb.append(" and sett_CurrencySubject.sTableName(+)='office'");
			sb
					.append(" and office.id=sett_OFFICERESERVEDACCOUNT.nofficeid(+) and sett_OFFICERESERVEDACCOUNT.ncurrencyid(+)=? and  office.nStatusID=? and sett_CurrencySubject.nCurrencyId(+)=?\n");
			strSQL = sb.toString();
			switch ((int) lOrderParam) {
			case 1:
				strSQL += " order by office.sCode";
				break;
			case 2:
				strSQL += " order by office.sName";
				break;
			case 3:
				strSQL += " order by SACCOUNTNO";
				break;
			case 4:
				strSQL += " order by office.SSUBJECTCODE";
				break;
			}
			if (isDesc)
				strSQL += " asc";
			else
				strSQL += " desc";
			transPS = prepareStatement(strSQL);
			System.out.println(strSQL);
			transPS.setLong(1, lCurrencyID);
			transPS.setLong(2, Constant.RecordStatus.VALID);
			transPS.setLong(3, lCurrencyID);
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OfficeInfo officeInfo = new OfficeInfo();
				officeInfo.m_lID = transRS.getLong("ID");
				officeInfo.m_strCode = transRS.getString("sCode");
				officeInfo.m_strName = transRS.getString("sName");
				officeInfo.m_lReservedAccountID = transRS
						.getLong("RESERVEDACCOUNT");
				officeInfo.m_strSubjectCode = transRS.getString("sSubjectCode");
				officeInfo.m_strReservedAccountNo = transRS
						.getString("SACCOUNTNO");
				officeInfo.m_lPageCount = lPageCount;
				v.add(officeInfo);
			}
			// �ر���Դ
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return (v.size() > 0 ? v : null);
	}
	/**
	 * ��ѯ���а��´����
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>��ѯ���а��´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>����Collection��������OfficeInfo
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lPageLineCount
	 *            ÿҳ��������
	 * @param lPageNo
	 *            �ڼ�ҳ����
	 * @param lOrderParam
	 *            �������������ݴ˲��������������������
	 * @param isDesc
	 *            �������
	 * @return Collection
	 * @exception SettlementException
	 */
	public Collection findAllOffice(long lPageLineCount,
			long lPageNo, long lOrderParam, boolean isDesc)
			throws SettlementException {
		Vector v = new Vector();
		String strSQL = null;
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try {
			initDAO();
			// ��������Ľ����
			StringBuffer sb = new StringBuffer();
			sb.append(" select Office.*  from Office where  nStatusID>0 \n");
			strSQL = sb.toString();
			switch ((int) lOrderParam) {
			case 1:
				strSQL += " order by office.sCode";
				break;
			case 2:
				strSQL += " order by office.sName";
				break;
			}
			if (isDesc)
				strSQL += " asc";
			else
				strSQL += " desc";
			transPS = prepareStatement(strSQL);
			System.out.println(strSQL);
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				OfficeInfo officeInfo = new OfficeInfo();
				officeInfo.m_lID = transRS.getLong("ID");
				officeInfo.m_strCode = transRS.getString("sCode");
				officeInfo.m_strName = transRS.getString("sName");
				officeInfo.m_ORGLEVEL= transRS.getLong("ORGLEVEL");
				officeInfo.m_lPageCount = lPageCount;
				v.add(officeInfo);
			}
			// �ر���Դ
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return (v.size() > 0 ? v : null);
	}
	/**
	 * ���ݱ�ʶ��ѯ���´����
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>���ݱ�ʶ��ѯ���´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>������OfficeInfo
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return OfficeInfo
	 * @exception Exception
	 */
	public OfficeInfo findOfficeByID(long lID) throws SettlementException {
		String FindString = null;
		OfficeInfo OfficeInfo = null;
		try {
			initDAO();
			// Find in the Branch by lID
			FindString = " select Office.ID, Office.sCode,Office.sName, sett_OFFICERESERVEDACCOUNT.NRESERVEDACCOUNTID, Office.sSubjectCode,sett_account.SACCOUNTNO  SACCOUNTNO"
					+ " from sett_account, Office,sett_OFFICERESERVEDACCOUNT      "
					+ " where sett_account.id(+)=sett_OFFICERESERVEDACCOUNT.nReservedAccountID and Office.ID =?       ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				OfficeInfo = new OfficeInfo();
				OfficeInfo.m_lID = transRS.getLong("ID");
				OfficeInfo.m_strCode = transRS.getString("sCode");
				OfficeInfo.m_strName = transRS.getString("sName");
				OfficeInfo.m_lReservedAccountID = transRS
						.getLong("NRESERVEDACCOUNTID");
				OfficeInfo.m_strSubjectCode = transRS.getString("sSubjectCode");
				OfficeInfo.m_strReservedAccountNo = transRS
						.getString("SACCOUNTNO");
			}
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return (OfficeInfo);
	}
	/**
	 * ���ݱ�ʶ��ѯ���´����
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>���ݱ�ʶ��ѯ���´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>������OfficeInfo
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return OfficeInfo
	 * @exception Exception
	 */
	public OfficeInfo findOfficeByIDOnly(long lID) throws SettlementException {
		String FindString = null;
		OfficeInfo OfficeInfo = null;
		try {
			initDAO();
			// Find in the Branch by lID
			FindString = " select * from  Office  where id=? ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				OfficeInfo = new OfficeInfo();
				OfficeInfo.m_lID = transRS.getLong("ID");
				OfficeInfo.m_strCode = transRS.getString("sCode");
				OfficeInfo.m_strName = transRS.getString("sName");
				OfficeInfo.m_ORGLEVEL=transRS.getLong("ORGLEVEL");
			}
			transPS.close();
			transPS=null;
			transRS.close();
			transRS=null;
			
			FindString = " select * from userinfo where nofficeid = ? and nisadminuser = ? order by id ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transPS.setLong(2, 1);
			transRS = executeQuery();
			int i = 1;
			while (transRS.next()) {
				if (i>0) {
					OfficeInfo.setSID1(transRS.getLong("id"));
					OfficeInfo.setStrUserName1(transRS.getString("sname"));
					OfficeInfo.setStrLoginName1(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword1(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn1(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo1(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode1(transRS.getString("scode"));
				}  
				else {
					OfficeInfo.setSID2(transRS.getLong("id"));
					OfficeInfo.setStrUserName2(transRS.getString("sname"));
					OfficeInfo.setStrLoginName2(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword2(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn2(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo2(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode2(transRS.getString("scode"));
				}

				i--;
				continue;
			}
			transPS.close();
			transPS=null;
			transRS.close();
			transRS=null;
			
			FindString = " select * from userinfo where nofficeid = ? and sname ='����' order by id ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = executeQuery();
			i = 1;
			while (transRS.next()) {
				if (i>0) {
					OfficeInfo.setJzID(transRS.getLong("id"));
					OfficeInfo.setStrUserName3(transRS.getString("sname"));
					OfficeInfo.setStrLoginName3(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword3(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn3(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo3(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode3(transRS.getString("scode"));
				}
				i--;
				continue;
			}
			transPS.close();
			transPS=null;
			transRS.close();
			transRS=null;
			
			FindString = " select * from userinfo where nofficeid = ? and sname ='����' order by id ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = executeQuery();
			i = 1;
			while (transRS.next()) {
				if (i>0) {
					OfficeInfo.setJhID(transRS.getLong("id"));
					OfficeInfo.setStrUserName4(transRS.getString("sname"));
					OfficeInfo.setStrLoginName4(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword4(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn4(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo4(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode4(transRS.getString("scode"));
				}
				i--;
				continue;
			}
			transPS.close();
			transPS=null;
			transRS.close();
			transRS=null;
			
			FindString = " select dtopendate from sett_OfficeTime where nofficeid = ? ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = executeQuery();
			if (transRS.next()) {
				OfficeInfo.setDtOpenDate(transRS.getTimestamp("dtopendate"));
			}
			transPS.close();
			transPS=null;
			transRS.close();
			transRS=null;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return (OfficeInfo);
	}
	/**
	 * ���ݱ�ʶ��ѯ���´����
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>���ݱ�ʶ��ѯ���´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>������OfficeInfo
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return OfficeInfo
	 * @exception Exception
	 */
	public OfficeInfo findOfficeByID(long lID, long lCurrencyID)
			throws SettlementException {
		String FindString = null;
		OfficeInfo OfficeInfo = new OfficeInfo();
		try {
			initDAO();
			// Find in the Branch by lID
			FindString = " select Office.*, sett_OFFICERESERVEDACCOUNT.NRESERVEDACCOUNTID,sett_account.SACCOUNTNO  SACCOUNTNO"
					+ " from sett_account, Office,sett_OFFICERESERVEDACCOUNT      "
					+ " where sett_account.id(+)=sett_OFFICERESERVEDACCOUNT.NRESERVEDACCOUNTID and office.id=sett_OFFICERESERVEDACCOUNT.NOFFICEID(+) and sett_OFFICERESERVEDACCOUNT.NCURRENCYID(+)=?  and Office.ID =?     ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lCurrencyID);
			transPS.setLong(2, lID);
			transRS = executeQuery();
			if (transRS.next()) {
				OfficeInfo.m_lID = transRS.getLong("ID");
				OfficeInfo.m_strCode = transRS.getString("sCode");
				OfficeInfo.m_strName = transRS.getString("sName");
				OfficeInfo.m_lReservedAccountID = transRS
						.getLong("NRESERVEDACCOUNTID");
				OfficeInfo.m_strSubjectCode = transRS.getString("sSubjectCode");
				OfficeInfo.m_strReservedAccountNo = transRS
						.getString("SACCOUNTNO");
			}

			FindString = " select * from userinfo where nofficeid = ? and nisadminuser = ? order by id ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transPS.setLong(2, 1);
			transRS = executeQuery();
			int i = 1;
			while (transRS.next()) {
				if (i>0) {
					OfficeInfo.setSID1(transRS.getLong("id"));
					OfficeInfo.setStrUserName1(transRS.getString("sname"));
					OfficeInfo.setStrLoginName1(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword1(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn1(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo1(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode1(transRS.getString("scode"));
				}  
				else {
					OfficeInfo.setSID2(transRS.getLong("id"));
					OfficeInfo.setStrUserName2(transRS.getString("sname"));
					OfficeInfo.setStrLoginName2(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword2(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn2(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo2(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode2(transRS.getString("scode"));
				}

				i--;
				continue;
			}
			FindString = " select * from userinfo where nofficeid = ? and sname ='����' order by id ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = executeQuery();
			i = 1;
			while (transRS.next()) {
				if (i>0) {
					OfficeInfo.setJzID(transRS.getLong("id"));
					OfficeInfo.setStrUserName3(transRS.getString("sname"));
					OfficeInfo.setStrLoginName3(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword3(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn3(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo3(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode3(transRS.getString("scode"));
				}
				i--;
				continue;
			}

			FindString = " select * from userinfo where nofficeid = ? and sname ='����' order by id ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = executeQuery();
			i = 1;
			while (transRS.next()) {
				if (i>0) {
					OfficeInfo.setJhID(transRS.getLong("id"));
					OfficeInfo.setStrUserName4(transRS.getString("sname"));
					OfficeInfo.setStrLoginName4(transRS.getString("sloginno"));
					OfficeInfo.setStrPassword4(transRS.getString("spassword"));
					OfficeInfo.setStrCertCn4(transRS.getString("scertcn"));
					OfficeInfo.setStrCertNo4(transRS.getString("scertno"));
					OfficeInfo.setStrUserCode4(transRS.getString("scode"));
				}
				i--;
				continue;
			}
			
			FindString = " select dtopendate from sett_OfficeTime where nofficeid = ? ";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lID);
			transRS = executeQuery();
			if (transRS.next()) {
				OfficeInfo.setDtOpenDate(transRS.getTimestamp("dtopendate"));
			}

			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return (OfficeInfo);
	}

	/**
	 * ������´����
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>������´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>���lID<0������Office��������һ����¼
	 * <li>������±�ʶ��lID�ļ�¼��Ϣ
	 * <li>��״̬��Ϊ����
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param strCode
	 * @param strName
	 * @param lReservedAccountID
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveOffice(long lID, String strCode, String strName,
			long lReservedAccountID, String strSubjectCode, long lCurrencyID)
			throws SettlementException {
		String strSQL = null;
		long lResult = -1;
		long lMaxID = -1;
		long lRecordID = lID;
		try {
			initDAO();
			// if Code duplicate
			strSQL = "select id from office where nStatusID>0 and (sCode = ? or sName=?)";
			transPS = prepareStatement(strSQL);
			transPS.setString(1, strCode.trim());
			transPS.setString(2, strName.trim());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				long lVar = transRS.getLong("id");
				if (lVar != lID) {
					finalizeDAO();
					return -1;
				}
			}
			if (lID < 0) {
				// �õ����ID
				strSQL = "select nvl(max(ID)+1,1) ID from Office";
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				transRS.next();
				lMaxID = transRS.getLong(1);
				// �ر���Դ
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				// insert�µļ�¼
				strSQL = "insert into Office ( ID, sCode, sName, sSubjectCode ,nStatusID,dtOpenDate,dtCalInterest,nSystemStatusID ) values (?,?,?,?,?,?,?,?) ";
				transPS = prepareStatement(strSQL);
				transPS.setLong(1, lMaxID);
				transPS.setString(2, strCode.trim());
				transPS.setString(3, strName.trim());
				transPS.setString(4, strSubjectCode);
				transPS.setLong(5, Constant.RecordStatus.VALID);
				transPS.setTimestamp(6, Env.getSystemDate());
				transPS.setTimestamp(7, DataFormat.getPreviousDate(Env
						.getSystemDate()));
				transPS.setLong(8, Constant.SystemStatus.OPEN);
				lResult = executeUpdate();
				// �ر���Դ
				transPS.close();
				transPS = null;
				// OfficTime����insert�µļ�¼
				for (int i = 1; i <= Constant.CurrencyType.getLength(); i++) {
					// �õ����ID
					strSQL = "select nvl(max(ID)+1,1) ID from sett_OfficeTime ";
					transPS = prepareStatement(strSQL);
					transRS = executeQuery();
					transRS.next();
					long lTempMaxID = transRS.getLong(1);
					// �ر���Դ
					transRS.close();
					transRS = null;
					transPS.close();
					transPS = null;
					strSQL = "insert into sett_OfficeTime ( ID, nOfficeID,nCurrencyID ,nSystemStatusID,dtOpenDate,dtCalInterest,nStatusID ) values (?,?,?,?,?,?,?) ";
					Log.print(strSQL);
					transPS = prepareStatement(strSQL);
					transPS.setLong(1, lTempMaxID);
					transPS.setLong(2, lMaxID);
					transPS.setLong(3, (long) i);
					transPS.setLong(4, Constant.SystemStatus.OPEN);
					transPS.setTimestamp(5, Env.getSystemDate());
					transPS.setTimestamp(6, DataFormat.getPreviousDate(Env
							.getSystemDate()));
					transPS.setLong(7, Constant.RecordStatus.VALID);
					lResult = executeUpdate();
					// �ر���Դ
					transPS.close();
					transPS = null;
				}
				//
				strSQL = " insert into sett_OFFICERESERVEDACCOUNT(NOFFICEID, NRESERVEDACCOUNTID, NCURRENCYID) values(?,?,?) ";
				transPS = prepareStatement(strSQL);
				transPS.setLong(1, lMaxID);
				transPS.setLong(2, lReservedAccountID);
				transPS.setLong(3, lCurrencyID);
				transPS.executeUpdate();
				lRecordID = lMaxID;
			} else {
				strSQL = " update Office set sCode=?, sName=?, sSubjectCode=? where ID=?";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, strCode.trim());
				transPS.setString(2, strName.trim());
				transPS.setString(3, strSubjectCode);
				transPS.setLong(4, lID);
				lResult = executeUpdate();
				// �ر���Դ
				transPS.close();
				transPS = null;
				/*
				 * // // �������˻������ strSQL = " select * from
				 * sett_OFFICERESERVEDACCOUNT where NOFFICEID=? and
				 * NCURRENCYID=? "; transPS = prepareStatement(strSQL);
				 * transPS.setLong(1, lID); transPS.setLong(2, lCurrencyID);
				 * transRS = executeQuery(); if (transRS.next()) {
				 * transRS.close(); transRS = null; transPS.close(); transPS =
				 * null; strSQL = " update sett_OFFICERESERVEDACCOUNT set
				 * NRESERVEDACCOUNTID=? where NOFFICEID=? and NCURRENCYID=?";
				 * transPS = prepareStatement(strSQL); transPS.setLong(1,
				 * lReservedAccountID); transPS.setLong(2, lID);
				 * transPS.setLong(3, lCurrencyID); transPS.executeUpdate(); }
				 * else { transRS.close(); transRS = null; transPS.close();
				 * transPS = null; strSQL = " insert into
				 * sett_OFFICERESERVEDACCOUNT(NOFFICEID, NRESERVEDACCOUNTID,
				 * NCURRENCYID) values(?,?,?) "; transPS =
				 * prepareStatement(strSQL); transPS.setLong(1, lID);
				 * transPS.setLong(2, lReservedAccountID); transPS.setLong(3,
				 * lCurrencyID); transPS.executeUpdate(); }
				 */
			}
			// �ر���Դ
			this.finalizeDAO();
			// savesett_CurrencySubject("office", lRecordID, lCurrencyID,
			// strSubjectCode, lRecordID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return lResult;
	}

	public boolean validateOfficeNameAndCode(OfficeInfo officeInfo)
			throws SettlementException {
		String strSQL = "";
		boolean bl = false;
		try {
			initDAO();

			strSQL = " select id from office where ( (nStatusID>0 and sCode = ?) or (nStatusID>0 and sName=?) ) ";

			if (officeInfo.getM_lID() > 0) {
				strSQL += " and id != ? ";
			}

			transPS = prepareStatement(strSQL);
			transPS.setString(1, officeInfo.getM_strCode().trim());
			//transPS.setString(2, officeInfo.getM_strCode().trim());
			transPS.setString(2, officeInfo.getM_strName().trim());

			if (officeInfo.getM_lID() > 0) {
				transPS.setLong(3, officeInfo.getM_lID());
			}

			transRS = transPS.executeQuery();
			while (transRS.next()) {
				bl = true;
			}

			// �ر���Դ
			this.finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}

		return bl;
	}

	/**
	 * ������´����
	 * <p>
	 * <b>Add Boxu 2008��1��7��</b>
	 * <ol>
	 * <b>������´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>���lID<0������Office��������һ����¼
	 * <li>������±�ʶ��lID�ļ�¼��Ϣ
	 * <li>��״̬��Ϊ����
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param strCode
	 * @param strName
	 * @param lReservedAccountID
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveNewOffice(OfficeInfo officeInfo) throws SettlementException ,IException{
		String strSQL = null;
		long lResult = -1;
		long lMaxID = -1;
		long groupId = -1;
		long sysManagerDutyId = -1;// ϵͳ���� ְ��id add by zhanglei
		long lRecordID = officeInfo.getM_lID();
		long userid = -1;
		try {			
			initDAO();
			// if Code duplicate
			strSQL = "select id from office where (nStatusID>0 and sCode = ?)  or (nStatusID>0 and sName=?) ";
			transPS = prepareStatement(strSQL);
			transPS.setString(1, officeInfo.getM_strCode().trim());
			//transPS.setString(2, officeInfo.getM_strCode().trim());
			transPS.setString(2, officeInfo.getM_strName().trim());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				long lVar = transRS.getLong("id");
				if (lVar != officeInfo.getM_lID()) {
					finalizeDAO();
					return -1;
				}
			}
			if (officeInfo.getM_lID() < 0) {

				// ���´���ű����ID����һ��
				//lMaxID = Long.parseLong(officeInfo.getM_strCode());
				transPS.close();
				transPS = null;
				strSQL = " select nvl(max(id)+1,1) ID from office ";
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				transRS.next();
				lMaxID = transRS.getLong(1);

				// �ر���Դ
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				// insert�µļ�¼
				strSQL = "insert into Office ( ID, sCode, sName, sSubjectCode ,nStatusID,dtOpenDate,dtCalInterest,nSystemStatusID ) values (?,?,?,?,?,?,?,?) ";
				transPS = prepareStatement(strSQL);
				transPS.setLong(1, lMaxID);
				transPS.setString(2, officeInfo.getM_strCode().trim());
				transPS.setString(3, officeInfo.getM_strName().trim());
				transPS.setString(4, officeInfo.getM_strSubjectCode());
				transPS.setLong(5, Constant.RecordStatus.VALID);
				transPS.setTimestamp(6, Env.getSystemDate());
				transPS.setTimestamp(7, DataFormat.getPreviousDate(Env
						.getSystemDate()));

				transPS.setLong(8, Constant.SystemStatus.OPEN);
				lResult = executeUpdate();
				// �ر���Դ
				transPS.close();
				transPS = null;
				// OfficTime����insert�µļ�¼
				for (int i = 1; i <= Constant.CurrencyType.getLength(); i++) {
					// �õ����ID
					strSQL = "select nvl(max(ID)+1,1) ID from sett_OfficeTime ";
					transPS = prepareStatement(strSQL);
					transRS = executeQuery();
					transRS.next();
					long lTempMaxID = transRS.getLong(1);
					// �ر���Դ
					transRS.close();
					transRS = null;
					transPS.close();
					transPS = null;
					strSQL = "insert into sett_OfficeTime ( ID, nOfficeID,nCurrencyID ,nSystemStatusID,dtOpenDate,dtCalInterest,nStatusID ) values (?,?,?,?,?,?,?) ";
					Log.print(strSQL);
					transPS = prepareStatement(strSQL);
					transPS.setLong(1, lTempMaxID);
					transPS.setLong(2, lMaxID);
					transPS.setLong(3, (long) i);
					transPS.setLong(4, Constant.SystemStatus.OPEN);
					transPS.setTimestamp(5, officeInfo.getDtOpenDate());
					transPS.setTimestamp(6, DataFormat
							.getPreviousDate(officeInfo.getDtOpenDate()));

					transPS.setLong(7, Constant.RecordStatus.VALID);
					lResult = executeUpdate();
					// �ر���Դ
					transPS.close();
					transPS = null;
				}

				strSQL = " insert into sett_OFFICERESERVEDACCOUNT(NOFFICEID, NRESERVEDACCOUNTID, NCURRENCYID) values(?,?,?) ";
				transPS = prepareStatement(strSQL);
				transPS.setLong(1, lMaxID);
				transPS.setLong(2, officeInfo.getM_lReservedAccountID());
				transPS.setLong(3, officeInfo.getCurrencyId());
				transPS.executeUpdate();
				long agency_id = -1;
				String qSql = "select (max(id)+1) id from mg_r_duty_agency";
				transPS=prepareStatement(qSql);
				ResultSet rsm = transPS.executeQuery(qSql);
				while(rsm.next()){
					agency_id=rsm.getLong("id");
				}
				
				/*****************************����˾Ĭ��ְ��*********************************/
				String sSql = "select d.id from mg_duty d where d.agency_type = '1001' and d.is_default_duty = 1";
				transPS=prepareStatement(sSql);
				ResultSet sRS = transPS.executeQuery(sSql);
				if(sRS.next()){
					sysManagerDutyId=sRS.getLong("id");
				}
				/*****************************����˾Ĭ��ְ��*********************************/
				
				String sql = "insert into  mg_r_duty_agency (id, dutyid, agencyid, name) values("+agency_id+","+sysManagerDutyId+","+lMaxID+",'"+officeInfo.getM_strName().trim()+"--ϵͳ����--ְ��')";
				transPS.execute(sql);
				lRecordID = lMaxID;


				// �����Ӱ��´��û�
				PrivilegeDelegation privilege = new PrivilegeDelegation();
				Sys_UserInfo userCondition = null;
				Sys_Group_Of_UserInfo group_userCondition = new Sys_Group_Of_UserInfo();
				Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true,
						transConn);
				if (officeInfo.getStrUserName1() != null
						&& !officeInfo.getStrUserName1().equals("")) {
					userCondition = new Sys_UserInfo();
					userCondition.setName(officeInfo.getStrUserName1());
					userCondition.setLoginNo(officeInfo.getStrLoginName1());
					userCondition.setPassword(officeInfo.getStrPassword1());
					userCondition.setOfficeID(lMaxID);
					userCondition.setNusergroupid(groupId); // �û����ʶ
															// �Զ�����2���û�����2���û��������û��鲻��д����ȫ���޸�
															// 2010-5-12
					userCondition.setCurrencyID(String.valueOf(officeInfo
							.getCurrencyId()));
					userCondition.setIsAdminUser(1);
					userCondition.setIsVirtualUser(4);
					userCondition.setCertCn(officeInfo.getStrCertCn1());
					userCondition.setCertNo(officeInfo.getStrCertNo1());
					userCondition.setCode(officeInfo.getStrUserCode1());
					userCondition.setPurviewType(1);//�û�������Ϊϵͳ����Ա
					userCondition.setInputUserID(-1);
					/**
					 * SDCϵͳ���ɡ�ʹ���µ� ��ʼ������Ա
					 */
					userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
					userCondition.setInput(Env.getSystemDateTime());
					userCondition.setIsAdminUser(1);
					userCondition.setCheck(Env.getSystemDateTime());
					userCondition.setChangePassword(Env.getSystemDate());
					userCondition.setFlag(1);
					userCondition.setCurrencyID(officeInfo.getCurrencyId()+"");
					userCondition.setCheckUserID(2);
					userDao.setUseMaxID();
					
					userid = userDao.add(userCondition);
					userCondition.setId(userid);
	                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
	                {
	                	EncryptManager.getBeanFactory().changeUserPassword(userid,userCondition.getPassword(),transConn);
	                }
					
					sql="insert into mg_r_user_duty_agency(id,userid,AGENCY_DUTY_ID) values((select max(id)+1 from mg_r_user_duty_agency),"+userid+","+agency_id+")";
					transPS=prepareStatement(sql);
					try{
						transPS.execute(sql);
					}catch(Exception e){
						throw new Exception("�����û�"+userCondition.getName()+"ʧ��"+e.getMessage());
					}
					//�½�����ԱȨ�޼�¼
					this.addUserAuthority(userCondition);
				}
				if (officeInfo.getStrUserName2() != null
						&& !officeInfo.getStrUserName2().equals("")) {
					userCondition = new Sys_UserInfo();
					userCondition.setName(officeInfo.getStrUserName2());
					userCondition.setLoginNo(officeInfo.getStrLoginName2());
					userCondition.setPassword(officeInfo.getStrPassword2());
					userCondition.setOfficeID(lMaxID);
					userCondition.setNusergroupid(groupId); // �û����ʶ
															// �Զ�����2���û�����2���û��������û��鲻��д����ȫ���޸�
															// 2010-5-12
					userCondition.setCurrencyID(String.valueOf(officeInfo
							.getCurrencyId()));
					userCondition.setIsAdminUser(1);
					
					userCondition.setCheckUserID(2);
					userCondition.setIsVirtualUser(4);
					userCondition.setCertCn(officeInfo.getStrCertCn2());
					userCondition.setCertNo(officeInfo.getStrCertNo2());
					userCondition.setCode(officeInfo.getStrUserCode2());
					userCondition.setPurviewType(1);

					/**
					 * SDCϵͳ���ɡ�ʹ���µ� ��ʼ������Ա
					 */
					userCondition.setFlag(1);
					userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
					userCondition.setInput(Env.getSystemDateTime());
					userCondition.setCheck(Env.getSystemDateTime());
					userCondition.setChangePassword(Env.getSystemDate());
					userCondition.setCurrencyID(officeInfo.getCurrencyId()+"");
					userDao.setUseMaxID();
					
					userid = userDao.add(userCondition);
					userCondition.setId(userid);
	                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
	                {
	                	EncryptManager.getBeanFactory().changeUserPassword(userid,userCondition.getPassword(),transConn);
	                }
	                
					sql="insert into mg_r_user_duty_agency(id,userid,AGENCY_DUTY_ID) values((select max(id)+1 from mg_r_user_duty_agency),"+userid+","+agency_id+")";
					transPS=prepareStatement(sql);
					try{
						transPS.execute(sql);
					}catch(Exception e){
						throw new Exception("�����û�"+userCondition.getName()+"ʧ��"+e.getMessage());
					}
					//�½�����ԱȨ�޼�¼
					this.addUserAuthority(userCondition);
				}
				
				//�����ܲ��ʻ�����Ϣ
				this.copyAccountGroup(lMaxID);
				//�����ܲ��Ļ�Ʒ�¼����
				Sett_GLEntryDefinitionDAO gleDifineDao=new Sett_GLEntryDefinitionDAO(transConn);
				long lOfficeIDSource=this.getHeadOfficeCode();
				if(lOfficeIDSource>0&&lMaxID>0){
					log.info("�����ֲ��Ļ�Ʒ�¼����");
					if(!gleDifineDao.batchCopy(lOfficeIDSource, lMaxID)){
						throw new Exception("�����ֲ��Ļ�Ʒ�¼����ʧ�ܡ�");	
					}
				}				
				//��������Ļ����ͻ���
				long clientID = -1;
				ClientInfo cInfo=new ClientInfo();	
				String M_sOfficeNo=NameRef.getOfficeNoByID(officeInfo.getM_lOfficeID());
				ClientmanageDAO clientDao = new ClientmanageDAO("client_clientinfo", transConn);
				clientDao.setUseMaxID();
				
				int clientNameLength= Integer.valueOf(Config.getConfigItem(ConfigConstant.GLOBAL_MAXACCOUNTNO_LENGTH,"6")).intValue()-2;
				cInfo.setCode(""+M_sOfficeNo+"-JG"+ DataFormat.transCode(Long.valueOf(officeInfo.getM_strCode()).longValue(), clientNameLength));
				cInfo.setName(officeInfo.getM_strName());
				cInfo.setOfficeID(officeInfo.getM_lOfficeID());// �������´�ID
				cInfo.setIsInstitutionalclient(lMaxID);//�Ƿ��ǻ����ͻ����ǣ���������ID�����ǣ����NULL/-1
				cInfo.setInputDate(Env.getSystemDateTime());
				cInfo.setInputTime(Env.getSystemDateTime());
				cInfo.setStatusID(1);
				cInfo.setInputUserID(userid);
				clientID=clientDao.add(cInfo);
				if(clientID<0)	throw new Exception("�����ͻ�����ʧ�ܡ�");		
				
			} else {
				strSQL = " update Office set sCode=?, sName=?, sSubjectCode=? where ID=? ";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, officeInfo.getM_strCode().trim());
				transPS.setString(2, officeInfo.getM_strName().trim());
				transPS.setString(3, officeInfo.getM_strSubjectCode());
				transPS.setLong(4, officeInfo.getM_lID());
				lResult = executeUpdate();

				transPS.close();
				transPS = null;

				// ϵͳ�û� 1
				strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=?,scode=? where ID=? ";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, officeInfo.getStrUserName1().trim());
				transPS.setString(2, officeInfo.getStrLoginName1().trim());
				transPS.setString(3, officeInfo.getStrPassword1().trim());
				transPS.setString(4, officeInfo.getStrCertNo1().trim());
				transPS.setString(5, officeInfo.getStrCertCn1().trim());
				transPS.setString(6, officeInfo.getStrUserCode1().trim());
				transPS.setLong(7, officeInfo.getSID1());
				lResult = executeUpdate();

				transPS.close();
				transPS = null;

                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
                {
                	EncryptManager.getBeanFactory().changeUserPassword(officeInfo.getSID1(),officeInfo.getStrPassword1(),transConn);
                }
                
				// ϵͳ�û� 2
				strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=?,scode=? where ID=? ";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, officeInfo.getStrUserName2().trim());
				transPS.setString(2, officeInfo.getStrLoginName2().trim());
				transPS.setString(3, officeInfo.getStrPassword2().trim());
				transPS.setString(4, officeInfo.getStrCertNo2().trim());
				transPS.setString(5, officeInfo.getStrCertCn2().trim());
				transPS.setString(6, officeInfo.getStrUserCode2().trim());
				transPS.setLong(7, officeInfo.getSID2());
				lResult = executeUpdate();
				// �ر���Դ
				transPS.close();
				transPS = null;
				
                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
                {
                	EncryptManager.getBeanFactory().changeUserPassword(officeInfo.getSID2(),officeInfo.getStrPassword2(),transConn);
                }
                /*
				// �����û�
				strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=?,scode=? where ID=? ";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, officeInfo.getStrUserName3().trim());
				transPS.setString(2, officeInfo.getStrLoginName3().trim());
				transPS.setString(3, officeInfo.getStrPassword3().trim());
				transPS.setString(4, officeInfo.getStrCertNo3().trim());
				transPS.setString(5, officeInfo.getStrCertCn3().trim());
				transPS.setString(6, officeInfo.getStrUserCode3().trim());
				transPS.setLong(7, officeInfo.getJzID());
				lResult = executeUpdate();
				// �ر���Դ
				transPS.close();
				transPS = null;
				
                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
                {
                	EncryptManager.getBeanFactory().changeUserPassword(officeInfo.getJzID(),officeInfo.getStrPassword3());
                }
                
				// �����û� 
				strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=?,scode=? where ID=? ";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, officeInfo.getStrUserName4().trim());
				transPS.setString(2, officeInfo.getStrLoginName4().trim());
				transPS.setString(3, officeInfo.getStrPassword4().trim());
				transPS.setString(4, officeInfo.getStrCertNo4().trim());
				transPS.setString(5, officeInfo.getStrCertCn4().trim());
				transPS.setString(6, officeInfo.getStrUserCode4().trim());
				transPS.setLong(7, officeInfo.getJhID());
				lResult = executeUpdate();
				// �ر���Դ
				transPS.close();
				transPS = null;
				*/
                /*
                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
                {
                	EncryptManager.getBeanFactory().changeUserPassword(officeInfo.getJhID(),officeInfo.getStrPassword4());
                }
                */
//				strSQL = " update mg_r_duty_agency t set t.name = " +
//				 " (select t1.name || '---' || t2.name from mg_agency t1, mg_duty t2 where t1.id = t.agencyid and t2.id = t.dutyid) "+
//		         "  where t.agencyid =  "+officeInfo.getM_lID();
//				transPS = prepareStatement(strSQL);
//				executeUpdate();
//				// �ر���Դ
//				transPS.close();
//				transPS = null;
					
				
				/*
				 * // // �������˻������ strSQL = " select * from
				 * sett_OFFICERESERVEDACCOUNT where NOFFICEID=? and
				 * NCURRENCYID=? "; transPS = prepareStatement(strSQL);
				 * transPS.setLong(1, lID); transPS.setLong(2, lCurrencyID);
				 * transRS = executeQuery(); if (transRS.next()) {
				 * transRS.close(); transRS = null; transPS.close(); transPS =
				 * null; strSQL = " update sett_OFFICERESERVEDACCOUNT set
				 * NRESERVEDACCOUNTID=? where NOFFICEID=? and NCURRENCYID=?";
				 * transPS = prepareStatement(strSQL); transPS.setLong(1,
				 * lReservedAccountID); transPS.setLong(2, lID);
				 * transPS.setLong(3, lCurrencyID); transPS.executeUpdate(); }
				 * else { transRS.close(); transRS = null; transPS.close();
				 * transPS = null; strSQL = " insert into
				 * sett_OFFICERESERVEDACCOUNT(NOFFICEID, NRESERVEDACCOUNTID,
				 * NCURRENCYID) values(?,?,?) "; transPS =
				 * prepareStatement(strSQL); transPS.setLong(1, lID);
				 * transPS.setLong(2, lReservedAccountID); transPS.setLong(3,
				 * lCurrencyID); transPS.executeUpdate(); }
				 */
			}

			// �ر���Դ
			
			//this.finalizeDAO();
			// savesett_CurrencySubject("office", lRecordID, lCurrencyID,
			// strSubjectCode, lRecordID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new IException(ex.getMessage());
			}
		}

		return lResult;
	}
	/**
	 * ������´����
	 * <p>
	 * <b>Add Boxu 2008��1��7��</b>
	 * <ol><b>������´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>���lID<0������Office��������һ����¼
	 * <li>������±�ʶ��lID�ļ�¼��Ϣ
	 * <li>��״̬��Ϊ����
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param strCode
	 * @param strName
	 * @param lReservedAccountID
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public OfficeInfo saveNewOfficeWithoutSDC(OfficeInfo officeInfo) throws SettlementException
	{
		String strSQL = "";
		long lResult = -1;
		long lMaxID = -1;
		long groupId = -1;
		boolean IsHeadquarter=false;
		boolean isFilterStatus=false;
		long lRecordID = officeInfo.getM_lID();
		long authID1=-1;
		long authID2=-1;
		long userId1=-1;
		long userId2=-1;
		long userId3=-1;
		long userId4=-1;
		long clientID=-1;
		long privilegeID=-1;
		String FindString = null;
		Sys_PrivilegeInfo privateInfo = null;
		OfficeInfo oInfo=new OfficeInfo();
		try
		{
			initDAO();
			
			//if Code duplicate
			//�ܲ�ֻ����һ����У��
			//IsHeadquarter=validateHeadquarterNums(officeInfo);	
			
			if(officeInfo.getM_ORGLEVEL()==SETTConstant.OrganizationLevel.Headquarters){
				
				strSQL = " select * from office where  nStatusID >0 and ORGLEVEL = "+ SETTConstant.OrganizationLevel.Headquarters;
				Log.print(strSQL);
				if(officeInfo.getM_lID()>0){
					
					strSQL +=" and id <> " + officeInfo.getM_lID();
				}
				
				transPS = prepareStatement(strSQL);
				
				transRS = transPS.executeQuery();
				
				if (transRS.next()) {
					IsHeadquarter=true;
					throw new Exception("�ܲ�ֻ����һ����");
				}
				else{
					IsHeadquarter=false;
				}
				transPS.close();
				transPS = null;
				transRS.close();
				transRS = null;
			}
			
			if(IsHeadquarter==false){
				//��֧������ź� �������Ƶ��ظ�У��
				strSQL = "select id from office where (nStatusID>0 and sCode = ?)  or (nStatusID>0 and sName=?) ";
				Log.print(strSQL);
				transPS = prepareStatement(strSQL);
				transPS.setString(1, officeInfo.getM_strCode().trim());
				transPS.setString(2, officeInfo.getM_strName().trim());
				transRS = transPS.executeQuery();
					while (transRS.next())
					{
						long lVar = transRS.getLong("id");
						if (lVar != officeInfo.getM_lID())
						{
							finalizeDAO();
							oInfo.setM_lID(-1);
							return oInfo;
						}
					}
					transPS.close();
					transPS = null;
					transRS.close();
					transRS = null;
					//�����ķ�֧��������
					if (officeInfo.getM_lID() < 0)
					{
						//�õ����ID
						strSQL = " select nvl(max(id)+1,1) ID from office ";
						transPS = prepareStatement(strSQL);
						transRS = executeQuery();
						transRS.next();
						lMaxID = transRS.getLong(1);
						
						//���´���ű����ID����һ��
						//lMaxID = Long.parseLong(officeInfo.getM_strCode());
						
						//�ر���Դ
						transRS.close();
						transRS = null;
						transPS.close();
						transPS = null;
						//insert�µļ�¼ �����֧������Ϣ
						strSQL = "insert into Office ( ID, sCode, sName, sSubjectCode ,nStatusID,dtOpenDate,dtCalInterest,nSystemStatusID,ORGLEVEL) values (?,?,?,?,?,?,?,?,?) ";
						Log.print(strSQL);
						transPS = prepareStatement(strSQL);
						transPS.setLong(1, lMaxID);
						transPS.setString(2, officeInfo.getM_strCode().trim());
						transPS.setString(3, officeInfo.getM_strName().trim());
						transPS.setString(4, officeInfo.getM_strSubjectCode());
						transPS.setLong(5, Constant.RecordStatus.VALID);
						transPS.setTimestamp(6, Env.getSystemDate());
						transPS.setTimestamp(7, DataFormat.getPreviousDate(Env.getSystemDate()));
		
						transPS.setLong(8, Constant.SystemStatus.OPEN);
						transPS.setLong(9, officeInfo.getM_ORGLEVEL());
						lResult = executeUpdate();
						oInfo.setM_lID(lResult);
						//�ر���Դ
						transPS.close();
						transPS = null;
						//OfficTime����insert�µļ�¼ ����ϵͳʱ����Ϣ
						for (int i = 1; i <= Constant.CurrencyType.getLength(); i++)
						{
							//�õ����ID
							strSQL = "select nvl(max(ID)+1,1) ID from sett_OfficeTime ";
							transPS = prepareStatement(strSQL);
							transRS = executeQuery();
							transRS.next();
							long lTempMaxID = transRS.getLong(1);
							//�ر���Դ
							transRS.close();
							transRS = null;
							transPS.close();
							transPS = null;
							strSQL = "insert into sett_OfficeTime ( ID, nOfficeID,nCurrencyID ,nSystemStatusID,dtOpenDate,dtCalInterest,nStatusID ) values (?,?,?,?,?,?,?) ";
							Log.print(strSQL);
							transPS = prepareStatement(strSQL);
							transPS.setLong(1, lTempMaxID);
							transPS.setLong(2, lMaxID);
							transPS.setLong(3, (long) i);
							transPS.setLong(4, Constant.SystemStatus.OPEN);
							
							//transPS.setTimestamp(5, Env.getSystemDate());
							//transPS.setTimestamp(6, DataFormat.getPreviousDate(Env.getSystemDate()));
							transPS.setTimestamp(5, officeInfo.getDtOpenDate());
							transPS.setTimestamp(6, DataFormat.getPreviousDate(officeInfo.getDtOpenDate()));
							
							transPS.setLong(7, Constant.RecordStatus.VALID);
							lResult = executeUpdate();
							//�ر���Դ
							transPS.close();
							transPS = null;
						}
						// ���� ��֧����׼�����˻���Ϣ��û�ã�
						 strSQL = " insert into sett_OFFICERESERVEDACCOUNT(NOFFICEID, NRESERVEDACCOUNTID, NCURRENCYID) values(?,?,?) ";
						 Log.print(strSQL);
						 transPS = prepareStatement(strSQL);
						 transPS.setLong(1, lMaxID);
						 transPS.setLong(2, officeInfo.getM_lReservedAccountID());
						 transPS.setLong(3, officeInfo.getCurrencyId());
						 transPS.executeUpdate();
						 lRecordID = lMaxID;
						 
						 transPS.close();
						 transPS = null;
						 
						 //�����Ӱ��´�ϵͳ�û���
						 Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group",transConn);
						 Sys_GroupInfo groupCondition = new Sys_GroupInfo();
		                 groupDao.setUseMaxID();
		                 groupCondition.setInputDate(Env.getSystemDateTime());
		                 groupCondition.setDtCheck(Env.getSystemDateTime());
		                 groupCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
		                 groupCondition.setOfficeID(lMaxID);
		                 groupCondition.setName("ϵͳ������");
		                 groupCondition.setModuleID(Constant.ModuleType.SYSTEM);
		                 groupId = groupDao.add(groupCondition);
		                 
		                 Sys_Privilege_Of_GroupDAO privilege_groupDao = new Sys_Privilege_Of_GroupDAO("Sys_Privilege_Of_Group",transConn);
		                 Sys_Privilege_Of_GroupInfo groupInfo = null;
		                 Sys_PrivilegeDAO pDao=new Sys_PrivilegeDAO("sys_privilege",transConn);
						 
						 //ֱ��copy һ���ܲ������в˵����������´���������״̬��������ϵͳģ�����Ч�˵� 
						 //coll=CopyMenusFromOneBranchToAnother(1, lMaxID, false);
						FindString = " select * from sys_privilege where OFFICEID=" +officeInfo.getM_lOfficeID();
						
						if(isFilterStatus){
							
							FindString+=" and STATUSID > 0 ";
							
						}

						transPS = prepareStatement(FindString);

						transRS = transPS.executeQuery();
						
						while (transRS.next()) {
							privateInfo = new Sys_PrivilegeInfo();
							privilegeID=-1;
							privateInfo.setModuleID(transRS.getLong("MODULEID"));
							privateInfo.setName(transRS.getString("name"));
							privateInfo.setPlevel(transRS.getLong("PLEVEL")) ;
							privateInfo.setPrivilegeNo(transRS.getString("PRIVILEGENO"));
							privateInfo.setMenuUrl(transRS.getString("MENUURL"));
							privateInfo.setStatusID(transRS.getLong("STATUSID"));
							privateInfo.setOfficeID(lMaxID);
							pDao.setUseMaxID();
							privilegeID=pDao.add(privateInfo);
							
							 //��ϵͳ�˵��ҵ�ϵͳ�û�����
							if(privateInfo.getModuleID()==Constant.ModuleType.SYSTEM && privateInfo.getStatusID()>0 && privilegeID>0)
							{
								groupInfo = new Sys_Privilege_Of_GroupInfo();
								
								privilege_groupDao.setUseMaxID();
			                	groupInfo.setGroupID(groupId);
			                	groupInfo.setPrivilegeID(privilegeID);
			                    privilege_groupDao.add(groupInfo);

							}
						}
						transPS.close();
						transPS = null;
						transRS.close();
						transRS = null;
						
						 //�����ӻ����û���2��ϵͳ����Ա �� ���ƻ����û� ������ �� ��Ȩ
						Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
			            Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);
			            Sys_UserDAO authDao = new Sys_UserDAO("sys_userauthority", true, transConn);
						 
						//PrivilegeDelegation privilege = new PrivilegeDelegation();
						 Sys_UserInfo userCondition = new Sys_UserInfo();
						 Sys_Group_Of_UserInfo group_userCondition = new Sys_Group_Of_UserInfo();
						 Sys_UserAuthorityInfo authInfo=new Sys_UserAuthorityInfo();
						 
						 if(officeInfo.getStrUserName1() != null && !officeInfo.getStrUserName1().equals(""))
						 {	
							 userCondition = new Sys_UserInfo();
							 userCondition.setName(officeInfo.getStrUserName1());
							 userCondition.setLoginNo(officeInfo.getStrLoginName1());
							 userCondition.setPassword(officeInfo.getStrPassword1());
							 userCondition.setNusergroupid(groupId);  //�û����ʶ �Զ�����2���û�����2���û��������û��鲻��д����ȫ���޸� 2010-5-12
							 userCondition.setIsAdminUser(1);
							 userCondition.setIsVirtualUser(4);
							 userCondition.setCertCn(officeInfo.getStrCertCn1());
							 userCondition.setCertNo(officeInfo.getStrCertNo1());
							 userCondition.setPurviewType(1);
			                 userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
			                 userCondition.setInput(Env.getSystemDateTime());
			                 userCondition.setCheck(Env.getSystemDateTime());
			                 userCondition.setChangePassword(Env.getSystemDate());
			                 userCondition.setOfficeID(lMaxID);
			                 userDao.setUseMaxID();

				             userId1 = userDao.add(userCondition);
				            
				             if(userId1>0){   
				            	 oInfo.setSID1(userId1);
				            	 
					             userCondition.setId(userId1);
					             userCondition.setInputUserID(userId1);
					             userCondition.setCheckUserID(userId1);
					             userCondition.setCode("00"+userId1);
					             userDao.update(userCondition);
					             
					             //������Ȩ��Ϣ
					             authInfo=new Sys_UserAuthorityInfo();
					             
					             authInfo.setAuthorizedOfficeId(lMaxID);
					             authInfo.setNStatusId(SYSConstant.SysCheckStatus.CHECK);
					             authInfo.setUserId(userId1);
					             authInfo.setnInputUserId(userId1);
					             authInfo.setDtInput(Env.getSystemDateTime());
					             authInfo.setDtCheck(Env.getSystemDateTime());
					             authInfo.setCheckUserId(userId1);
					             authInfo.setSCurrencyId("1,2,3");//����ң���Ԫ��ŷԪ
					             authInfo.setUserResponsibility(1);//�û�ְ��(1 ϵͳ����Ա��-1 ҵ��Ա)
	
				             	 authDao.setUseMaxID();
				             	 authID1=authDao.add(authInfo);     		
				            		
				             	 if(authID1>0){
					            		//�����û��������û�����Ϣ
				             		group_userCondition = new Sys_Group_Of_UserInfo();                    
					            			group_userCondition.setGroupID(groupId);
					            			group_userCondition.setUserID(userId1);
					            			group_userCondition.setOfficeID(lMaxID);
					            			group_userDao.setUseMaxID();
						            		group_userDao.add(group_userCondition);

				             	 }
				             	 else{
				             		 
				             		throw new Exception("ϵͳ����Ա1��Ȩʧ�ܡ�");
				             		 
				             	 }
				             }
				             else{
				            	 
				            	 throw new Exception("ϵͳ����Ա1����ʧ�ܡ�");
				             }
							 							 
						 }
						 if(officeInfo.getStrUserName2() != null && !officeInfo.getStrUserName2().equals(""))
						 {
							 userCondition = new Sys_UserInfo();
							 
							 userCondition.setName(officeInfo.getStrUserName2());
							 userCondition.setLoginNo(officeInfo.getStrLoginName2());
							 userCondition.setPassword(officeInfo.getStrPassword2());
							 //userCondition.setOfficeID(lMaxID);
							 userCondition.setNusergroupid(groupId);  //�û����ʶ  �Զ�����2���û�����2���û��������û��鲻��д����ȫ���޸� 2010-5-12
							 //userCondition.setCurrencyID(String.valueOf(officeInfo.getCurrencyId()));
							 userCondition.setIsAdminUser(1);
							 userCondition.setIsVirtualUser(4);
							 userCondition.setCertCn(officeInfo.getStrCertCn2());
							 userCondition.setCertNo(officeInfo.getStrCertNo2());
							 userCondition.setPurviewType(1);
			                 userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
			                 userCondition.setInput(Env.getSystemDateTime());
			                 userCondition.setCheck(Env.getSystemDateTime());
			                 userCondition.setChangePassword(Env.getSystemDate());
			                 userCondition.setOfficeID(lMaxID);
			                 userDao.setUseMaxID();

				             userId2 = userDao.add(userCondition);
				            
				             if(userId2>0){   
				            	 oInfo.setSID2(userId2);
					             userCondition.setId(userId2);
					             userCondition.setInputUserID(userId2);
					             userCondition.setCheckUserID(userId2);
					             userCondition.setCode("00"+userId2);
					             userDao.update(userCondition);
					                
					             //������Ȩ��Ϣ
					             authInfo=new Sys_UserAuthorityInfo();
					             
					             authInfo.setAuthorizedOfficeId(lMaxID);
					             authInfo.setNStatusId(SYSConstant.SysCheckStatus.CHECK);
					             authInfo.setUserId(userId2);
					             authInfo.setnInputUserId(userId2);
					             authInfo.setDtInput(Env.getSystemDateTime());
					             authInfo.setDtCheck(Env.getSystemDateTime());
					             authInfo.setCheckUserId(userId2);
					             authInfo.setSCurrencyId("1,2,3");//����ң���Ԫ��ŷԪ
					             authInfo.setUserResponsibility(1);//�û�ְ��(1 ϵͳ����Ա��-1 ҵ��Ա)
					             
				             	 authDao.setUseMaxID();
				             	 authID2=authDao.add(authInfo);     		
				            		
				             	 if(authID2>0){
					            		//�����û��������û�����Ϣ
				             		group_userCondition = new Sys_Group_Of_UserInfo();
					            			group_userCondition.setGroupID(groupId);
					            			group_userCondition.setUserID(userId2);
					            			group_userCondition.setOfficeID(lMaxID);
					            			group_userDao.setUseMaxID();
						            		group_userDao.add(group_userCondition);

				             	 }
				             	 else{
				             		 
				             		throw new Exception("ϵͳ����Ա2��Ȩʧ�ܡ�");
				             		 
				             	 }
				             }
				             else{
				            	 
				            	 throw new Exception("ϵͳ����Ա2����ʧ�ܡ�");
				             }
						 } 
//						 if (officeInfo.getStrUserName3() != null && !officeInfo.getStrUserName3().equals("")) {
//							 	
//							 	userCondition = new Sys_UserInfo();
//							 
//								userCondition.setName(officeInfo.getStrUserName3());
//								userCondition.setLoginNo(officeInfo.getStrLoginName3());
//								userCondition.setPassword(officeInfo.getStrPassword3());
//								//userCondition.setOfficeID(lMaxID);
//								userCondition.setNusergroupid(groupId); // �û����ʶ
//																		// �Զ�����2���û�����2���û��������û��鲻��д����ȫ���޸�
//																		// 2010-5-12
//								userCondition.setCurrencyID(String.valueOf(officeInfo.getCurrencyId()));
//								userCondition.setIsAdminUser(0);
//								userCondition.setCheckUserID(2);
//								userCondition.setIsVirtualUser(4);
//								userCondition.setCertCn(officeInfo.getStrCertCn3());
//								userCondition.setCertNo(officeInfo.getStrCertNo3());
//								userCondition.setCode(officeInfo.getStrUserCode3());
//								userCondition.setPurviewType(-1);
//			
//				                 userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
//				                 userCondition.setInput(Env.getSystemDateTime());
//				                 userCondition.setCheck(Env.getSystemDateTime());
//				                 userCondition.setChangePassword(Env.getSystemDate());
//				                 userCondition.setOfficeID(lMaxID);
//				                 userDao.setUseMaxID();
//
//					             userId3 = userDao.add(userCondition);
//					            
//					             if(userId3>0){  
//					            	 oInfo.setJzID(userId3);
//						             userCondition.setId(userId3);
//						             userCondition.setInputUserID(userId3);
//						             userCondition.setCheckUserID(userId3);
//						             userCondition.setCode("00"+userId3);
//						             userDao.update(userCondition);
//						             
//
//					             }
//					             else{
//					            	 
//					            	 throw new Exception("�����û�����ʧ�ܡ�");
//					             }
//							}
//							
//							//�������´������û�
//							if (officeInfo.getStrUserName4() != null && !officeInfo.getStrUserName4().equals("")) {
//								
//								userCondition = new Sys_UserInfo();
//								
//								userCondition.setName(officeInfo.getStrUserName4());
//								userCondition.setLoginNo(officeInfo.getStrLoginName4());
//								userCondition.setPassword(officeInfo.getStrPassword4());
//								//userCondition.setOfficeID(lMaxID);
//								userCondition.setNusergroupid(groupId); // �û����ʶ
//																		// �Զ�����2���û�����2���û��������û��鲻��д����ȫ���޸�
//																		// 2010-5-12
//								//userCondition.setCurrencyID(String.valueOf(officeInfo.getCurrencyId()));
//								userCondition.setIsAdminUser(0);
//								userCondition.setCheckUserID(2);
//								userCondition.setIsVirtualUser(4);
//								userCondition.setCertCn(officeInfo.getStrCertCn4());
//								userCondition.setCertNo(officeInfo.getStrCertNo4());
//								userCondition.setCode(officeInfo.getStrUserCode4());
//								userCondition.setPurviewType(-1);
//				                 userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
//				                 userCondition.setInput(Env.getSystemDateTime());
//				                 userCondition.setCheck(Env.getSystemDateTime());
//				                 userCondition.setChangePassword(Env.getSystemDate());
//				                 userCondition.setOfficeID(lMaxID);
//				                 userDao.setUseMaxID();
//
//					             userId4 = userDao.add(userCondition);
//					            
//					             if(userId4>0){   
//					            	 oInfo.setJhID(userId4);
//					            	 
//						             userCondition.setId(userId4);
//						             userCondition.setInputUserID(userId4);
//						             userCondition.setCheckUserID(userId4);
//						             userCondition.setCode("00"+userId4);
//						             userDao.update(userCondition);
//						             
//					            	 
//					             }
//					             else{
//					            	 
//					            	 throw new Exception("�����û�����ʧ�ܡ�");
//					             }
//							}
							
							//��������Ļ����ͻ���
							ClientInfo cInfo=new ClientInfo();	
							String M_sOfficeNo=NameRef.getOfficeNoByID(officeInfo.getM_lOfficeID());
							String clientTableName=Config.getConfigItem(ConfigConstant.SETTLEMENT_CLIENT_TABLENAME,"client_clientinfo");
							Log.print("��������ȡ���Ŀͻ���Ϣ��Ϊ��"+clientTableName);
							ClientmanageDAO clientDao = new ClientmanageDAO(clientTableName, transConn);
							
							int clientNameLength= Integer.valueOf(Config.getConfigItem(ConfigConstant.GLOBAL_MAXACCOUNTNO_LENGTH,"6")).intValue()-2;
							
							clientDao.setUseMaxID();
							if(clientTableName.equals("client_clientinfo")){
								
								cInfo.setCode(""+M_sOfficeNo+"-JG"+ DataFormat.transCode(Long.valueOf(officeInfo.getM_strCode()).longValue(), clientNameLength));
								cInfo.setName(officeInfo.getM_strName());
								cInfo.setOfficeID(officeInfo.getM_lOfficeID());// �������´�ID
								cInfo.setIsInstitutionalclient(lMaxID);//�Ƿ��ǻ����ͻ����ǣ���������ID�����ǣ����NULL/-1
								cInfo.setInputDate(Env.getSystemDateTime());
								cInfo.setInputTime(Env.getSystemDateTime());
								cInfo.setStatusID(1);
								cInfo.setInputUserID(userId1);
							}
							else  {
								
								cInfo.setCode(""+M_sOfficeNo+"-JG"+ DataFormat.transCode(Long.valueOf(officeInfo.getM_strCode()).longValue(), clientNameLength),"s");
								cInfo.setName(officeInfo.getM_strName(),"s");
								cInfo.setOfficeID(officeInfo.getM_lOfficeID(),"n");// �������´�ID
								cInfo.setIsInstitutionalclient(lMaxID);//�Ƿ��ǻ����ͻ����ǣ���������ID�����ǣ����NULL/-1
								cInfo.setInputDate(Env.getSystemDateTime(),"DTINPUT");
								cInfo.setStatusID(1,"n");
								cInfo.setInputUserID(userId1,"n");
							}
							clientID=clientDao.add(cInfo);
							
							if(clientID>0)
							{
	
							}
							else{								
								throw new Exception("�����ͻ�����ʧ�ܡ�");	
							}
							
							
					}
					else
					{
						oInfo.setM_lID(officeInfo.getM_lID());
						oInfo.setSID1(officeInfo.getSID1());
						oInfo.setSID2(officeInfo.getSID2());
						oInfo.setJzID(officeInfo.getJzID());
						oInfo.setJhID(officeInfo.getJhID());
						
						strSQL = " update Office set sCode=?, sName=?, sSubjectCode=? where ID=? ";
						Log.print(strSQL);
						transPS = prepareStatement(strSQL);
						transPS.setString(1, officeInfo.getM_strCode().trim());
						transPS.setString(2, officeInfo.getM_strName().trim());
						transPS.setString(3, officeInfo.getM_strSubjectCode());
						transPS.setLong(4, officeInfo.getM_lID());
						lResult = executeUpdate();
						
						transPS.close();
						transPS = null;
						
						//ϵͳ�û� 1
						strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=? where ID=? ";
						Log.print(strSQL);
						transPS = prepareStatement(strSQL);
						transPS.setString(1, officeInfo.getStrUserName1().trim());
						transPS.setString(2, officeInfo.getStrLoginName1().trim());
						transPS.setString(3, officeInfo.getStrPassword1().trim());
						transPS.setString(4, officeInfo.getStrCertNo1().trim());
						transPS.setString(5, officeInfo.getStrCertCn1().trim());
						transPS.setLong(6, officeInfo.getSID1());
						lResult = executeUpdate();
						
						transPS.close();
						transPS = null;
                        
						//ϵͳ�û� 2
						strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=? where ID=? ";
						Log.print(strSQL);
						transPS = prepareStatement(strSQL);
						transPS.setString(1, officeInfo.getStrUserName2().trim());
						transPS.setString(2, officeInfo.getStrLoginName2().trim());
						transPS.setString(3, officeInfo.getStrPassword2().trim());
						transPS.setString(4, officeInfo.getStrCertNo2().trim());
						transPS.setString(5, officeInfo.getStrCertCn2().trim());
						transPS.setLong(6, officeInfo.getSID2());
						lResult = executeUpdate();
						
						//�ر���Դ
						transPS.close();
						transPS = null;
						
						// �����û�
						strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=?,scode=? where ID=? ";
						Log.print(strSQL);
						transPS = prepareStatement(strSQL);
						transPS.setString(1, officeInfo.getStrUserName3().trim());
						transPS.setString(2, officeInfo.getStrLoginName3().trim());
						transPS.setString(3, officeInfo.getStrPassword3().trim());
						transPS.setString(4, officeInfo.getStrCertNo3().trim());
						transPS.setString(5, officeInfo.getStrCertCn3().trim());
						transPS.setString(6, officeInfo.getStrUserCode3().trim());
						transPS.setLong(7, officeInfo.getJzID());
						lResult = executeUpdate();
						// �ر���Դ
						transPS.close();
						transPS = null;
						
		                
						// �����û� 
						strSQL = " update userinfo set sname=?, sloginno=?, spassword=?, scertno=?, scertcn=?,scode=? where ID=? ";
						Log.print(strSQL);
						transPS = prepareStatement(strSQL);
						transPS.setString(1, officeInfo.getStrUserName4().trim());
						transPS.setString(2, officeInfo.getStrLoginName4().trim());
						transPS.setString(3, officeInfo.getStrPassword4().trim());
						transPS.setString(4, officeInfo.getStrCertNo4().trim());
						transPS.setString(5, officeInfo.getStrCertCn4().trim());
						transPS.setString(6, officeInfo.getStrUserCode4().trim());
						transPS.setLong(7, officeInfo.getJhID());
						lResult = executeUpdate();
						// �ر���Դ
						transPS.close();
						transPS = null;
						
						
						
						//ͬ������ ����Ļ����ͻ������ƣ�
						String clientTableName=Config.getConfigItem(ConfigConstant.SETTLEMENT_CLIENT_TABLENAME,"client_clientinfo");
						Log.print("��������ȡ���Ŀͻ���Ϣ��Ϊ��"+clientTableName);

							
						// ��������Ļ����ͻ�����
						if(clientTableName.toLowerCase().equals("client"))
						{
							strSQL = " update "+clientTableName+" cf set cf.sname=? where cf.isinstitutionalclient=?  ";
						}
						else if(clientTableName.toLowerCase().equals("client_clientinfo")){
							
							strSQL = " update "+clientTableName+" cf set cf.name=? where cf.isinstitutionalclient=?  ";
						}

						Log.print(strSQL);
						transPS = prepareStatement(strSQL);
						transPS.setString(1, officeInfo.getM_strName());
						transPS.setLong(2, officeInfo.getM_lID());
						
						lResult = executeUpdate();
						// �ر���Դ
						transPS.close();
						transPS = null;


						

					}
			}

		}
		catch (Exception e)
		{
			
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		
		return oInfo;
	}
	
	/**
	 * ɾ�����´����
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>ɾ�����´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>��״̬��Ϊɾ��
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void
	 * @exception Exception
	 */
	public long deleteOffice(long lID) throws SettlementException {
		long lResult = -1;
		String removeString = null;
		try {
			initDAO();
			removeString = " update Office set nStatusID = ? WHERE ID =? ";
			transPS = prepareStatement(removeString);
			transPS.setLong(1, Constant.RecordStatus.INVALID);
			transPS.setLong(2, lID);
			lResult = executeUpdate();

			// �ر���Դ
			transPS.close();
			transPS = null;

			removeString = " update sett_OfficeTime set nStatusID = ? WHERE nOfficeID =? ";
			transPS = prepareStatement(removeString);
			transPS.setLong(1, Constant.RecordStatus.INVALID);
			transPS.setLong(2, lID);
			executeUpdate();

			// �ر���Դ
			transPS.close();
			transPS = null;

			// ɾ��SETT_OFFICERESERVEDACCOUNT��ؼ�¼
			removeString = " delete from SETT_OFFICERESERVEDACCOUNT WHERE NOFFICEID =? ";
			transPS = prepareStatement(removeString);
			transPS.setLong(1, lID);
			executeUpdate();

			// �ر���Դ
			transPS.close();
			transPS = null;

			// ������������û�
			removeString = " update userinfo set nstatusid = 0 where nofficeid ="
					+ lID + " ";
			transPS = prepareStatement(removeString);
			executeUpdate();

			// �ر���Դ
			transPS.close();
			transPS = null;

			// ������������û�
			removeString = " update ob_user set nstatus = 0 where nofficeid ="
					+ lID + " ";
			transPS = prepareStatement(removeString);
			executeUpdate();
			
			// �ر���Դ
			transPS.close();
			transPS = null;
			
			//add by kevin(������) 2011-11-22
			// ���������Ϣ
			removeString = " delete constantManageInfo  where OFFICEID ="
					+ lID + " ";
			transPS = prepareStatement(removeString);
			executeUpdate();			
			// �ر���Դ
			transPS.close();
			transPS = null;
			
			// �����Ʒ�¼����
			removeString = " delete sett_glentrydefinition  where NOFFICEID ="
					+ lID + " ";
			transPS = prepareStatement(removeString);
			executeUpdate();			
			// �ر���Դ
			transPS.close();
			transPS = null;
			
			// ����û�����λ��Ӧ��
			removeString = " delete mg_r_user_duty_agency  where AGENCY_DUTY_ID in (select id from mg_r_duty_agency where agencyid="+lID +")";					
			transPS = prepareStatement(removeString);
			executeUpdate();			
			// �ر���Դ
			transPS.close();
			transPS = null;
			// �����λ��ְ��͵�λ�Ĺҽӣ�
			removeString = " delete mg_r_duty_agency  where agencyid ="+lID +"";					
			transPS = prepareStatement(removeString);
			executeUpdate();			
			// �ر���Դ
			transPS.close();
			transPS = null;
			
			//add by kevin(������) 2011-07-25
			// ��������ͻ�			
			String clientTableName=Config.getConfigItem(ConfigConstant.SETTLEMENT_CLIENT_TABLENAME,"client_clientinfo");
			Log.print("��������ȡ���Ŀͻ���Ϣ��Ϊ��"+clientTableName);			
			if(clientTableName.toLowerCase().equals("client"))
			{
				removeString = " update "+clientTableName+" cf set cf.nstatusid=0 where cf.isinstitutionalclient="+lID + "";;
			}
			else if(clientTableName.toLowerCase().equals("client_clientinfo")){
				
				removeString = " update "+clientTableName+" cf set cf.statusid=0 where cf.isinstitutionalclient="+ lID + "";;
			}			
			transPS = prepareStatement(removeString);
			executeUpdate();

			// �ر���Դ
			finalizeDAO();
			// deletesett_CurrencySubject("office", lID);
			// deleteSubject(lID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new SettlementException();
			}
		}

		return lResult;
	}

	/**
	 * ɾ�����´�Ч�� 2007��1��8�� Add Boxu
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>ɾ�����´����</b>
	 * <ul>
	 * <li>�������ݿ��Office
	 * <li>��״̬��Ϊɾ��
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void
	 * @exception Exception
	 */
	public Collection validateOffice(long lID) throws SettlementException {
		Collection coll = new ArrayList();
		String removeString = null;
		validateInfo validateinfo = null;
		try {
			initDAO();

			// �˻�Ч��
			removeString = " select sa.naccounttypeid, count(sa.id) accountnum from sett_account sa where sa.nstatusid != ? and sa.nofficeid = ? group by sa.naccounttypeid ";
			transPS = prepareStatement(removeString);
			transPS.setLong(1, Constant.RecordStatus.INVALID);
			transPS.setLong(2, lID);
			transRS = transPS.executeQuery();

			while (transRS.next()) {
				validateinfo = new validateInfo();

				long naccounttypeid = transRS.getLong("naccounttypeid");
				validateinfo.setOperationName(NameRef
						.getAccountTypeNameByAccountTypeID(naccounttypeid));
				validateinfo.setOperationNum(transRS.getLong("accountnum"));
				validateinfo.setOperationType(1);

				coll.add(validateinfo);
			}

			// Ч�����ҵ��
			removeString = " select sv.transactiontypeid, count(sv.id) transid from sett_vtransaction sv where sv.statusid != ? and sv.officeid = ? group by sv.transactiontypeid ";
			transPS = prepareStatement(removeString);
			transPS.setLong(1, Constant.RecordStatus.INVALID);
			transPS.setLong(2, lID);
			transRS = transPS.executeQuery();

			while (transRS.next()) {
				validateinfo = new validateInfo();

				long naccounttypeid = transRS.getLong("transactiontypeid");
				validateinfo.setOperationName(SETTConstant.TransactionType
						.getName(naccounttypeid));
				validateinfo.setOperationNum(transRS.getLong("transid"));
				validateinfo.setOperationType(2);

				coll.add(validateinfo);
			}

			// Ч�����ҵ��
			removeString = " select vt.Actionname, count(vt.ID) vtid from v_loan_transaction vt where vt.Statusid != ? and vt.Officeid = ? group by vt.Actionid, vt.Actionname ";
			transPS = prepareStatement(removeString);
			transPS.setLong(1, Constant.RecordStatus.INVALID);
			transPS.setLong(2, lID);
			transRS = transPS.executeQuery();

			while (transRS.next()) {
				validateinfo = new validateInfo();

				validateinfo.setOperationName(transRS.getString("Actionname"));
				validateinfo.setOperationNum(transRS.getLong("vtid"));
				validateinfo.setOperationType(2);

				coll.add(validateinfo);
			}

			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new SettlementException();
			}
		}

		return coll;
	}

	/**
	 * @param lID
	 *            //���´�
	 * @param lCurrencyID
	 *            //����
	 * @return //���ظ��¼�¼��
	 * @throws SettlementException
	 */
	public long updateOffiecCurrency(long lID, String lCurrencyID)
			throws SettlementException {
		long lResult = -1;
		String updateString = null;
		try {
			initDAO();
			updateString = " update  Office  set CURRENCYID = ?  WHERE ID =?       ";
			transPS = prepareStatement(updateString);
			transPS.setString(1, lCurrencyID);
			transPS.setLong(2, lID);
			lResult = executeUpdate();
			// �ر���Դ
			transPS.close();
			transPS = null;
			// �ر���Դ
			finalizeDAO();
			// deletesett_CurrencySubject("office", lID);
			// deleteSubject(lID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new SettlementException();
			}
		}
		return lResult;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2008-01-16)
	 * 
	 * @return long
	 */
	public long getMaxOfficeID() throws SettlementException {
		long lResult = -1;
		StringBuffer sb = new StringBuffer();
		try {
			initDAO();

			sb.setLength(0);

			sb.append(" select max(id) maxId from office ");
			transPS = prepareStatement(sb.toString());
			transRS = executeQuery();
			if (transRS.next()) {
				lResult = transRS.getLong(1) + 1;
			}

			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return lResult;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-2-5 18:30:17)
	 * 
	 * @return long
	 */
	public long getMaxOfficeCode() throws SettlementException {
		long lResult = -1;
		StringBuffer sb = new StringBuffer();
		try {
			initDAO();
			// sb.append(" select nvl(min(to_number(scode),0) maxcode \n");
			// sb.append(" from (select to_number(scode) scode from office where
			// nStatusID>0 \n");
			// sb.append(" minus \n");
			// sb.append(" select to_number(scode) scode from office where
			// nStatusID>0 \n");
			// sb.append(" ) \n");
			// transPS = prepareStatement(sb.toString());
			// transRS = executeQuery();
			// if (transRS.next())
			// {
			// lResult = transRS.getLong(1);
			// }
			// if (lResult == 0)
			// {
			sb.setLength(0);
			// transRS.close();
			// transRS = null;
			// transPS.close();
			// transPS = null;
			sb
					.append(" select max(to_number(scode)) as nMaxAccountType from office where nStatusID>0 ");
			transPS = prepareStatement(sb.toString());
			transRS = executeQuery();
			if (transRS.next()) {
				lResult = transRS.getLong(1) + 1;
			}
			// }
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return lResult;
	}
	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-2-5 18:30:17)
	 * 
	 * @return long
	 */
	public long getHeadOfficeCode() throws SettlementException {
		long lResult = -1;
		StringBuffer sb = new StringBuffer();
		try {
			initDAO();
			sb.append(" select id as HeadID from office where nStatusID>0  and ORGLEVEL="+SETTConstant.OrganizationLevel.Headquarters);
			transPS = prepareStatement(sb.toString());
			transRS = executeQuery();
			if (transRS.next()) {
				lResult = transRS.getLong(1);
			}
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	/**
	 * �˴����뷽��˵��,ͨ�����´�ID��ѯ���´������жϰ��´��Ƿ����ܲ����� �������ڣ�(2011-07-22 18:30:17)
	 * 
	 * @return long
	 */
	public long getOfficeLevelByID(long officeID) throws SettlementException {
		long lResult = -1;
		StringBuffer sb = new StringBuffer();
		try {
			initDAO();
			sb.append(" select ORGLEVEL from office where nStatusID>0  and id = "+ officeID );
			transPS = prepareStatement(sb.toString());
			transRS = executeQuery();
			if (transRS.next()) {
				lResult = transRS.getLong(1);
			}
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return lResult;
	}
	/**
	 * 2007.6.13 qhzhou
	 * 
	 * @param lOfficeID
	 * @return long[] //����IDΪlOfficeID�İ��´����б��������ʾ
	 * @throws SettlementException
	 */
	public long[] findCurrencyIDsByOfficeID(long lOfficeID)
			throws SettlementException {
		long[] lResult = null;
		String[] sCurrencyID = null;
		String FindString = "";
		try {
			initDAO();
			FindString = "select o.currencyid from office o where o.id = ?		";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lOfficeID);
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				try {
					sCurrencyID = DataFormat.splitString(transRS.getString(1),
							",");
				} catch (Exception e) {
					sCurrencyID = new String[0];
				}
			}
			lResult = new long[sCurrencyID.length];
			for (int i = 0; i < sCurrencyID.length; i++) {
				lResult[i] = Long.parseLong(sCurrencyID[i]);
			}
			java.util.Arrays.sort(lResult);
			finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return lResult;
	}

	/**
	 * 2007.8.13 peter
	 * 
	 * @param lOfficeID
	 * @return String //����IDΪlOfficeID�İ��´����б����ַ�����ʾ
	 * @throws SettlementException
	 */
	public String findCurrencyIDByOfficeID(long lOfficeID)
			throws SettlementException {
		String lResult = null;
		String FindString = "";
		try {
			initDAO();
			FindString = "select o.currencyid from office o where o.id = ?		";
			transPS = prepareStatement(FindString);
			transPS.setLong(1, lOfficeID);
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				lResult = transRS.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			try {
				finalizeDAO();
			} catch (Exception ex) {
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	public ArrayList findOfficeInformation() throws Exception
	{
		ArrayList list = new ArrayList();
		com.iss.itreasury.dataentity.OfficeInfo info = null;new com.iss.itreasury.dataentity.OfficeInfo();
		StringBuffer sql = new StringBuffer();
		try
		{
			initDAO();
			sql.append(" select id,sname from office o");
			sql.append(" where o.nstatusid !="+SYSConstant.SysCheckStatus.DELETED);
			sql.append(" order by id ");
			transPS = prepareStatement(sql.toString());
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				info = new com.iss.itreasury.dataentity.OfficeInfo();
				info.setM_lID(transRS.getLong("id"));
				info.setM_strName(transRS.getString("sname"));
				list.add(info);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
			
		}
		return list.size()>0?list:null;
	}
	
	public ArrayList findOfficeByAuthority(Sys_UserAuthorityInfo authorityInfo) throws Exception
	{
		ArrayList list = new ArrayList();
		com.iss.itreasury.dataentity.OfficeInfo info = null;
		StringBuffer sql = new StringBuffer();
		try
		{
			initDAO();
			sql.append(" select distinct o.id,o.sname ");
			sql.append(" from office o,sys_userauthority s ");
			sql.append(" where s.authorizedofficeid = o.id ");
			sql.append(" and s.nstatusid ="+SYSConstant.SysAuthority.ALREADYAUTHORITY);  //״̬Ϊ����Ȩ
			if(authorityInfo.getUserId()>-1)
			{
				sql.append(" and s.userid ="+authorityInfo.getUserId());
			}
			sql.append(" order by o.id ");
			transPS = prepareStatement(sql.toString());
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				info = new com.iss.itreasury.dataentity.OfficeInfo();
				info.setM_lID(transRS.getLong("id"));
				info.setM_strName(transRS.getString("sname"));
				list.add(info);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
			
		}
		return list.size()>0?list:null;
	}
	
	public ArrayList findOfficeByModule (QueryOfficeInfo queryOfficeInfo)throws Exception
	{
		ArrayList list = new ArrayList();
		com.iss.itreasury.dataentity.OfficeInfo info = null;
		StringBuffer sql = new StringBuffer();
		try
		{
			initDAO();
			sql.append(" select distinct o.id, o.sname ");
			sql.append(" from office o, ");
			sql.append(" sys_userauthority s, ");
			sql.append(" sys_group_of_user g, ");
			sql.append(" sys_privilege_of_group spou, ");
			sql.append(" sys_privilege sp ");
			sql.append(" where s.authorizedofficeid = o.id ");
			sql.append(" and s.userid = g.userid ");
			sql.append(" and s.authorizedofficeid = g.officeid ");
			sql.append(" and sp.officeid = g.officeid ");
			sql.append(" and g.groupid = spou.groupid ");
			sql.append(" and spou.privilegeid = sp.id ");
			sql.append(" and s.nstatusid ="+SYSConstant.SysAuthority.ALREADYAUTHORITY);
			if(queryOfficeInfo.getLUserID()>0)
			{
				sql.append(" and s.userid ="+queryOfficeInfo.getLUserID());
			}
			if(queryOfficeInfo.getLModelID()>0)
			{
				sql.append(" and sp.moduleid ="+queryOfficeInfo.getLModelID());
			}
			sql.append(" order by o.id ");
			log.info("sql="+sql.toString());
			transPS = prepareStatement(sql.toString());
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				info = new com.iss.itreasury.dataentity.OfficeInfo();
				info.setM_lID(transRS.getLong("id"));
				info.setM_strName(transRS.getString("sname"));	
				list.add(info);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
			
		}
		return list.size()>0?list:null;
	}
	
	public void addUserAuthority(Sys_UserInfo userCondition) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		try
		{
			//this.setSelfManagedConn(false);
			sql.append(" insert into mg_r_user_authorty(id,userid,orgid,inputuserid,dtinput,flag) ");
			sql.append(" values(seq_mg_r_user_authorty.nextval,?,?,-1,?,1)");
			log.info("sql="+sql.toString());
			transPS = prepareStatement(sql.toString());
			transPS.setString(1, String.valueOf(userCondition.getId()));
			transPS.setString(2, String.valueOf(userCondition.getOfficeID()));
			transPS.setDate(3, new Date(userCondition.getInput().getTime()));
			transPS.execute();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("��������Ա�û�ʧ��!",e);
		}
		finally
		{
			if(transPS!=null)
			{
				transPS.close();
				transPS = null;
			}
		}

	}
	/**
	 * ���´�����
	 * @param officeInfo
	 * @return
	 * @throws Exception 
	 */
	public long updateOffice(OfficeInfo officeInfo) throws Exception {
		
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		String strSQL  = "";
		try {
			conn = Database.getConnection();
			strSQL = " update Office set sName=? where ID=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1, officeInfo.getM_strName().trim());
			ps.setLong(2, officeInfo.getM_lID());
			lResult = ps.executeUpdate();
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new IException("���°��´�ʧ��");
		}finally
		{
			if(ps != null)
			{
				ps.close();
				ps = null;
			}
			if(conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		return lResult;
		
	}
	/**
	 * ��ʼ���½����´��û���
	 * @throws Exception
	 */
	public void copyAccountGroup(long lOfficeId)throws Exception
	{
		ConstantManageInfo info = null;
		ArrayList list = new ArrayList();
		try
		{
			ConstantManageDao dao = new ConstantManageDao("constantManageInfo",transConn);
			list = this.getAccountGroupByHeadquarters();
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				info = (ConstantManageInfo)it.next();
				info.setOfficeID(lOfficeId);
				dao.add(info);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("�����ܲ��ʻ������",e);
		}
	}
	
	public ArrayList getAccountGroupByHeadquarters() throws Exception
	{
		StringBuffer sql = new StringBuffer();
		ArrayList list = new ArrayList();
		ConstantManageInfo condition = null;
		try
		{
			sql.append(" select m.constantid, m.officeid, m.currencyid, m.value ");
			sql.append(" from constantManageInfo m, office o, constantinfo c,CURRENCYINFO b ");
			sql.append(" where c.id = m.constantid ");
			sql.append(" and m.officeid = o.id ");
			sql.append(" and b.id = m.currencyid ");
			sql.append(" and o.orglevel = "+SETTConstant.OrganizationLevel.Headquarters);
			sql.append(" and c.name like '%com.iss.itreasury.settlement.util.SETTConstant$AccountGroupType%' ");
			sql.append(" and b.name like '%�����Ԫ%' ");
			//add by kevin(������)2011-11-22�ų�	������׼���𡢲�� �������˻���
			sql.append(" and m.value not in (");
			sql.append(" "+SETTConstant.AccountGroupType.BAK+","+SETTConstant.AccountGroupType.RESERVE+","+SETTConstant.AccountGroupType.LENDING+"");
			sql.append(" )");
			log.info("��ȡ�ܲ��ʻ�����Ϣ   sql="+sql);
			transPS = prepareStatement(sql.toString());
			transRS = executeQuery();
			while(transRS.next())
			{
				condition = new ConstantManageInfo();
				condition.setConstantID(transRS.getLong("constantid"));
				condition.setOfficeID(transRS.getLong("officeid"));
				condition.setCurrencyID(transRS.getLong("currencyid"));
				condition.setValue(transRS.getLong("value"));
				list.add(condition);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("��ȡ�ܲ��ʻ������!",e);
		}
		finally
		{
			if(transRS!=null)
			{
				transRS.close();
				transRS = null;
			}
			if(transPS!=null)
			{
				transPS.close();
				transPS = null;
			}
		}
		return list;
	}
}
