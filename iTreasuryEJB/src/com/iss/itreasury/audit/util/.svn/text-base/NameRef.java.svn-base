
package com.iss.itreasury.audit.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.iss.itreasury.util.Database;

public class NameRef {
	private static HashMap hmSubjectName = new HashMap();
	
	private static void findSubject() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// hmClientName = new HashMap();
			// hmClientCode = new HashMap();
			//
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id,SSEGMENTCODE2,SSEGMENTNAME2 from sett_glsubjectdefinition ");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				hmSubjectName.put(String.valueOf(rs.getLong("SSEGMENTCODE2")), rs
						.getString("SSEGMENTNAME2"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception sqle) {

			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				;
			}

		}
	}
	public static String getSubjectNameBySubjectCode(String subjectCode) {
		String strReturn = "";
		try {
			Object obj = hmSubjectName.get(subjectCode);
			if (obj != null) {
				strReturn = obj.toString();
			} else {
				findSubject();
				obj = hmSubjectName.get(subjectCode);
				strReturn = (obj != null ? obj.toString() : "");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
}
