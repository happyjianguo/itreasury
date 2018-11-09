/*
 * Created on 2004-11-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.system.dataentity.ChildClientPrivilegeInfo;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.IException;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChildClientPrivilegeDao {

	public Vector getChildClientPrivilege(long lClientID) throws Exception {
		Connection m_Conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vResult = new Vector();
		String strSQL = "";

		if (lClientID <= 0) {
			return null;
		}

		try {
			m_Conn = Database.getConnection();

			strSQL = " SELECT a.*,b.sName,b.sCode ";
			strSQL += " FROM childClientPrivilege a,client b ";
			strSQL += " WHERE a.ChildClientID = b.id AND a.clientID = ? ";
			strSQL += " ORDER BY clientID,ChildClientID";

			Log.print("strSQL=\n" + strSQL);
			ps = m_Conn.prepareStatement(strSQL); 
			ps.setLong(1, lClientID);

			rs = ps.executeQuery();

			long tempType = -1;
			long tempChildClientID = -1;
			long i = 0;
			ChildClientPrivilegeInfo tempInfo = null;

			while (rs.next()) {
				i++;
				if (tempChildClientID != rs.getLong("ChildClientID")) {
					if (i > 1 && tempInfo != null) {
						vResult.addElement(tempInfo);
					}

					tempInfo = new ChildClientPrivilegeInfo();

					tempChildClientID = rs.getLong("ChildClientID");
					tempInfo.setId(rs.getLong("id"));
					tempInfo.setClientID(rs.getLong("clientID"));
					tempInfo.setChildClientID(rs.getLong("ChildClientID"));
					tempInfo.setChildClientName(rs.getString("sName"));
					tempInfo.setChildClientCode(rs.getString("sCode"));
				}

				tempType = rs.getLong("privilegeType");
				if (tempInfo != null && tempType == SYSConstant.privilegeType.privilegeType_1) {
					tempInfo.setPrivilege1Value(rs.getLong("privilegeValue"));
				}
				if (tempInfo != null && tempType == SYSConstant.privilegeType.privilegeType_2) {
					tempInfo.setPrivilege2Value(rs.getLong("privilegeValue"));
				}
			}

			if (tempInfo != null) {
				vResult.addElement(tempInfo);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			m_Conn.close();
			m_Conn = null;
		} catch (Exception e) {
			Log.print("getChildClientPrivilege failed.  Exception is " + e.toString());
			if (m_Conn != null) {
				m_Conn.close();
				m_Conn = null;
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (m_Conn != null) {
					m_Conn.close();
					m_Conn = null;
				}
			} catch (Exception ex) {
				Log.print("getChildClientPrivilege failed.  Exception is " + ex.toString());
			}
		}
		return vResult;
	}

	public void saveChildClientPrivilege(ChildClientPrivilegeInfo[] infos) throws Exception {
		Connection m_Conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";

		try {
			m_Conn = Database.getConnection();
			m_Conn.setAutoCommit(false);

			if (infos == null) {
				return;
			}

			long tempID = 0;
			long tempClientID = -1;

			for (int i = 0; i < infos.length; i++) {
				if (infos[i] != null) {
					if (tempClientID != infos[i].getClientID()) {
						strSQL = " DELETE  childClientPrivilege ";
						strSQL += " WHERE ClientID = ?";

						Log.print("strSQL=\n" + strSQL);
						ps = m_Conn.prepareStatement(strSQL);
						ps.setLong(1, infos[i].getClientID());
						ps.executeUpdate();

						tempClientID = infos[i].getClientID();
					}

					ChildClientPrivilegeInfo[] tempInfos = new ChildClientPrivilegeInfo[2];

					ChildClientPrivilegeInfo tempInfo = new ChildClientPrivilegeInfo();
					tempInfo.setId(infos[i].getId());
					tempInfo.setClientID(infos[i].getClientID());
					tempInfo.setChildClientID(infos[i].getChildClientID());
					tempInfo.setPrivilegeType(1);
					tempInfo.setPrivilegeValue(infos[i].getPrivilege1Value());
					tempInfos[0] = tempInfo;

					tempInfo = new ChildClientPrivilegeInfo();
					tempInfo.setId(infos[i].getId());
					tempInfo.setClientID(infos[i].getClientID());
					tempInfo.setChildClientID(infos[i].getChildClientID());
					tempInfo.setPrivilegeType(2);
					tempInfo.setPrivilegeValue(infos[i].getPrivilege2Value());
					tempInfos[1] = tempInfo;

					for (int j = 0; j < tempInfos.length; j++) {
						strSQL = " SELECT MAX(id) ";
						strSQL += " FROM childClientPrivilege";
						Log.print("strSQL=\n" + strSQL);
						ps = m_Conn.prepareStatement(strSQL);
						rs = ps.executeQuery();
						if (rs.next()) {
							tempID = rs.getLong(1) + 1;
						}

						strSQL = " INSERT INTO  childClientPrivilege ";
						strSQL += " (id,ClientID,ChildClientID,privilegeType,privilegeValue)";
						strSQL += " VALUES(?,?,?,?,?)";
						Log.print("strSQL=\n" + strSQL);
						ps = m_Conn.prepareStatement(strSQL);
						ps.setLong(1, tempID);
						ps.setLong(2, tempInfos[j].getClientID());
						ps.setLong(3, tempInfos[j].getChildClientID());
						ps.setLong(4, tempInfos[j].getPrivilegeType());
						ps.setLong(5, tempInfos[j].getPrivilegeValue());

						ps.executeUpdate();
					}
				}
			}

			m_Conn.commit();
			m_Conn.setAutoCommit(true);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (m_Conn != null) {
				m_Conn.close();
				m_Conn = null;
			}

		} catch (Exception e) {
			m_Conn.rollback();
			Log.print("saveChildClientPrivilege failed.  Exception is " + e.toString());
			if (m_Conn != null) {
				m_Conn.close();
				m_Conn = null;
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (m_Conn != null) {
					m_Conn.close();
					m_Conn = null;
				}
			} catch (Exception ex) {
				Log.print("saveChildClientPrivilege failed.  Exception is " + ex.toString());
			}
		}
	}

	public void saveChildClient(ChildClientPrivilegeInfo[] infos) throws Exception, IException {
		Connection m_Conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";

		try {
			m_Conn = Database.getConnection();
			m_Conn.setAutoCommit(false);

			if (infos == null) {
				return;
			}

			long tempID = 0;
			long tempClientID = -1;

			for (int i = 0; i < infos.length; i++) {

				if (infos[i] != null) {

					strSQL = " SELECT DISTINCT a.ChildClientID, b.sName";
					strSQL += " FROM childClientPrivilege a,client b";
					strSQL += " WHERE a.ChildClientID = b.id AND a.ClientID = ? AND a.ChildClientID = ?";
					Log.print("strSQL=\n" + strSQL);
					ps = m_Conn.prepareStatement(strSQL);
					ps.setLong(1, infos[i].getClientID());
					ps.setLong(2, infos[i].getChildClientID());
					rs = ps.executeQuery();

					if (rs != null) {
						if (rs.next()) {
							if (rs.getLong(1) > 0) {
								throw new IException("Sys_E003", rs.getString("sName"));
							}
						}
					}

					ChildClientPrivilegeInfo[] tempInfos = new ChildClientPrivilegeInfo[2];
					ChildClientPrivilegeInfo tempInfo = new ChildClientPrivilegeInfo();
					tempInfo.setId(infos[i].getId());
					tempInfo.setClientID(infos[i].getClientID());
					tempInfo.setChildClientID(infos[i].getChildClientID());
					tempInfo.setPrivilegeType(1);
					tempInfo.setPrivilegeValue(infos[i].getPrivilege1Value());
					tempInfos[0] = tempInfo;

					tempInfo = new ChildClientPrivilegeInfo();
					tempInfo.setId(infos[i].getId());
					tempInfo.setClientID(infos[i].getClientID());
					tempInfo.setChildClientID(infos[i].getChildClientID());
					tempInfo.setPrivilegeType(2);
					tempInfo.setPrivilegeValue(infos[i].getPrivilege2Value());
					tempInfos[1] = tempInfo;

					for (int j = 0; j < tempInfos.length; j++) {
						strSQL = " SELECT MAX(id) ";
						strSQL += " FROM childClientPrivilege";
						Log.print("strSQL=\n" + strSQL);
						ps = m_Conn.prepareStatement(strSQL);
						rs = ps.executeQuery();

						if (rs.next()) {
							tempID = rs.getLong(1) + 1;
						}

						strSQL = " INSERT INTO  childClientPrivilege ";
						strSQL += " (id,ClientID,ChildClientID,privilegeType,privilegeValue)";
						strSQL += " VALUES(?,?,?,?,?)";
						Log.print("strSQL=\n" + strSQL);
						ps = m_Conn.prepareStatement(strSQL);
						ps.setLong(1, tempID);
						ps.setLong(2, tempInfos[j].getClientID());
						ps.setLong(3, tempInfos[j].getChildClientID());
						ps.setLong(4, tempInfos[j].getPrivilegeType());
						ps.setLong(5, tempInfos[j].getPrivilegeValue());

						ps.executeUpdate();
					}
				}
			}

			m_Conn.commit();
			m_Conn.setAutoCommit(true);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (m_Conn != null) {
				m_Conn.close();
				m_Conn = null;
			}

		} 
		catch (IException ie) {
			m_Conn.rollback();
			if (m_Conn != null) {
				m_Conn.close();
				m_Conn = null;
			}
			throw ie;
		}
		catch (Exception e) {
			m_Conn.rollback();
			Log.print("saveChildClient failed.  Exception is " + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (m_Conn != null) {
					m_Conn.close();
					m_Conn = null;
				}
			} catch (Exception ex) {
				Log.print("saveChildClient failed.  Exception is " + ex.toString());
			}
		}
	}

	public static void main(String[] args) {
		try {
			ChildClientPrivilegeDao dao = new ChildClientPrivilegeDao();

			ChildClientPrivilegeInfo[] infos = new ChildClientPrivilegeInfo[2];

			ChildClientPrivilegeInfo info = new ChildClientPrivilegeInfo();
			info.setId(1);
			info.setClientID(1);
			info.setChildClientID(2);
			info.setPrivilege1Value(1);
			info.setPrivilege2Value(2);
			infos[0] = info;

			info = new ChildClientPrivilegeInfo();
			info.setId(2);
			info.setClientID(2);
			info.setChildClientID(3);
			info.setPrivilege1Value(2);
			info.setPrivilege2Value(1);
			infos[1] = info;

			dao.saveChildClientPrivilege(infos);

			//dao.getChildClientPrivilege();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void deleteChildClient(long clientId, long childClientId) throws Exception {
		Connection m_Conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";

		try {
			m_Conn = Database.getConnection();

			if (clientId <= 0 || childClientId <= 0) {
				return;
			}

			strSQL = " DELETE  childClientPrivilege ";
			strSQL += " WHERE ClientID = ? AND ChildClientID = ?";

			Log.print("strSQL=\n" + strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, clientId);
			ps.setLong(2, childClientId);
			ps.executeUpdate();

			ps.close();
			ps = null;
			if (m_Conn != null) {
				m_Conn.close();
				m_Conn = null;
			}

		} catch (Exception e) {
			Log.print("deleteChildClient failed.  Exception is " + e.toString());
			if (m_Conn != null) {
				m_Conn.close();
				m_Conn = null;
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (m_Conn != null) {
					m_Conn.close();
					m_Conn = null;
				}
			} catch (Exception ex) {
				Log.print("deleteChildClient failed.  Exception is " + ex.toString());
			}
		}
	}
}

