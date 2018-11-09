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
	 * 从表[SEC_ExchangeCenter]中取最大的证交所代码
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
			new ITreasuryDAOException("数据库获取表[SEC_ExchangeCenter]最大的证交所代码产生异常",e);
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

		this.initDAO();
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT COUNT(*) count FROM SEC_ExchangeCenter WHERE Code = '"+sCode.trim()+"' \n");
		bufSQL.append("	AND StatusID = "+SECConstant.SettingStatus.CHECKED+" \n");
		log.info("SQL="+bufSQL.toString());
		try {//内部维护RS、PS、CONN，否则将会产生冲突
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
	 * 判断交易市场名称是否已经存在
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
		try {//内部维护RS、PS、CONN，否则将会产生冲突
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
			throw new IException("证券交易市场名称“" + sName + "”已经存在！",e);
		} finally {
		}
		this.finalizeDAO();
		return;
	}

	/**
	 * 从表中取表[SEC_ExchangeCenter]数据
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
				info.setId(localRS.getLong("ID"));						//自增,唯一不空
				//CODE		VARCHAR2(5)
				info.setCode(localRS.getString("CODE"));				//五位数字, 每次新增时自动提示最大的五位数，用户可以修改。
				//DESCRIPTION		VARCHAR2(250)
				info.setName(localRS.getString("NAME"));				//业务类型
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
			new ITreasuryDAOException("数据库获取备注表[SEC_ExchangeCenter]数据产生异常",e);
		}
		this.finalizeDAO();

		return list;
	}
}