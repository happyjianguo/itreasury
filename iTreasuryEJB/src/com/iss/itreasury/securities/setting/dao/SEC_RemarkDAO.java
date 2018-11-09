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
	 * 从表中取最大的备注编号
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
		try {//内部维护RS、PS、CONN，否则将会产生冲突
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
			new ITreasuryDAOException("数据库获取最大的备注编号产生异常",e);
		} finally {
		}
		this.finalizeDAO();
		return sReturn==null || sReturn==""?"":sReturn;
	}

	/**
	 * 从表中取最大的备注编号
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
		try {//内部维护RS、PS、CONN，否则将会产生冲突
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
	 * 从表中取备注表[sec_Remark]数据
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
				info.setId(localRS.getLong("ID"));						//自增,唯一不空
				//CODE		VARCHAR2(5)
				info.setCode(localRS.getString("CODE"));				//五位数字, 每次新增时自动提示最大的五位数，用户可以修改。
				//DESCRIPTION		VARCHAR2(250)
				info.setDescription(localRS.getString("DESCRIPTION"));	//备注描述
				//BUSINESSTYPE		NUMBER
				info.setBusinessType(localRS.getLong("BUSINESSTYPE"));	//业务类型
				//STATUSID		NUMBER
				info.setStatusID(localRS.getLong("STATUSID"));			//状态：1，有效；-1，已删除
				//INPUTUSERID		NUMBER
				info.setInputUserID(localRS.getLong("INPUTUSERID"));	//录入人
				//INPUTDATE		DATE
				info.setInputDate(localRS.getTimestamp("INPUTDATE"));	//录入时间
				//UPDATEUSERID		NUMBER
				info.setUpdateUserID(localRS.getLong("UPDATEUSERID"));	//修改人
				//UPDATEDATE		DATE
				info.setUpdateDate(localRS.getTimestamp("UPDATEDATE"));	//修改时间

				list.add(info);
			}
		} catch (Exception e) {
			new ITreasuryDAOException("数据库获取备注表[sec_Remark]数据产生异常",e);
		}
		this.finalizeDAO();

		return list;
	}
}