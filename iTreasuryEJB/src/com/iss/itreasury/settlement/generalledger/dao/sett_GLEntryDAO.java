package com.iss.itreasury.settlement.generalledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.assistant.dataentity.AssistantInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.QueryGLEntryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;

/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author yehuang
 * @version Date of Creation 2003-9-18
 */
public class sett_GLEntryDAO extends SettlementDAO {

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public sett_GLEntryDAO() {
		super.strTableName = "SETT_GLENTRY";
	}

	public sett_GLEntryDAO(Connection conn) {
		super(conn);
		super.strTableName = "SETT_GLENTRY";
	}

	/**
	 * 新增分录设置定义记录
	 * 
	 * @param info
	 *            GLEntryDefinitionInfo
	 * @return分录设置定义记录id
	 * @throws Exception
	 */

	public long add(GLEntryInfo info) throws Exception {
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into \n");
		buffer.append(strTableName);
		buffer.append("\n (ID, \n");
		buffer.append("nOfficeID,\n");
		buffer.append("nCurrencyID,\n");
		buffer.append("sSubjectCode,\n");
		buffer.append("sTransNo,\n");
		buffer.append("nTransactionTypeID,\n");
		buffer.append("nTransDirection,\n ");
		buffer.append("mAmount,\n");
		buffer.append("dtExecute,\n ");
		buffer.append("dtInterestStart,\n");
		buffer.append("sAbstract,\n");
		buffer.append("sMultiCode,\n");
		buffer.append("nInputUserID,\n");
		buffer.append("nCheckUserID,\n ");
		buffer.append("nStatusID,\n");
		buffer.append("nGroup,\n ");
		buffer.append("nType)\n");
		buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			long id = this.getNextID();
			info.setID(id);
			log.info(buffer.toString());
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			pstmt.setLong(nIndex++, info.getID());
			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setString(nIndex++, info.getSubjectCode());
			pstmt.setString(nIndex++, info.getTransNo());
			pstmt.setLong(nIndex++, info.getTransactionTypeID());
			pstmt.setLong(nIndex++, info.getTransDirection());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setTimestamp(nIndex++, info.getExecute());
			pstmt.setTimestamp(nIndex++, info.getInterestStart());
			pstmt.setString(nIndex++, info.getAbstract());
			pstmt.setString(nIndex++, info.getMultiCode());
			pstmt.setLong(nIndex++, info.getInputUserID());
			pstmt.setLong(nIndex++, info.getCheckUserID());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setLong(nIndex++, info.getGroup());
			pstmt.setLong(nIndex++, info.getType());

			pstmt.execute();

			lReturn = id;
		} finally {
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return lReturn;
	}

	private long getNextID() throws Exception {
		return getSett_GLEntryID();
	}

	private long getAssitantNextID() throws Exception {
		return getAssitantID();
	}

	public Collection findByTransNoAndStatusID(String stranNo, long statusID)
			throws Exception {

		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer
				.append("SELECT ID, nOfficeID,nCurrencyID,sSubjectCode,sTransNo,nTransactionTypeID,nTransDirection,mAmount,dtExecute, dtInterestStart,sAbstract,sMultiCode, nInputUserID,nCheckUserID,nStatusID,nGroup,nType FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE sTransNo = '" + stranNo + "' \n");
		buffer.append("AND nStatusID = " + statusID + "\n");
		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();
			Map idcache = new HashMap();
			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();
				tmp.setID(rset.getLong("ID"));
				
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setMultiCode(rset.getString("sMultiCode"));
				tmp.setInputUserID(rset.getLong("nInputUserID"));
				tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setGroup(rset.getLong("nGroup"));
				tmp.setType(rset.getLong("nType"));
				list.add(tmp);
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		if (list.size() == 0)
			return null;

		return list;
	}

	public Collection findByExecuteDate(Timestamp execDate, long officeID,
			long currencyID) throws Exception {

		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer
				.append("SELECT ID, nOfficeID,nCurrencyID,sSubjectCode,sTransNo,nTransactionTypeID,nTransDirection,mAmount,dtExecute, dtInterestStart,sAbstract,sMultiCode, nInputUserID,nCheckUserID,nStatusID,nGroup,nType,nPostStatusID FROM \n");
		buffer.append(" sett_vglentry ");
		buffer.append("\n WHERE dtExecute = ? \n");
		buffer.append("AND nOfficeID = " + officeID);
		buffer.append("AND nCurrencyID = " + currencyID);
		buffer
				.append("AND nStatusID = "
						+ SETTConstant.TransactionStatus.CHECK);
		buffer.append(" order by STRANSNO,ntransactiontypeid,NTRANSDIRECTION");

		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, execDate);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();

				tmp.setID(rset.getLong("ID"));
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setMultiCode(rset.getString("sMultiCode"));
				tmp.setInputUserID(rset.getLong("nInputUserID"));
				tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setGroup(rset.getLong("nGroup"));
				tmp.setType(rset.getLong("nType"));
				tmp.setPostStatusID(rset.getLong("nPostStatusID"));
				list.add(tmp);
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return list;
	}

	/**
	 * @author liuqing 说明：按日期取未传输的分录信息
	 * @param execDate
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public Collection findByExecuteDateAndPostStatusID(Timestamp execDate,
			long officeID, long currencyID) throws Exception {

		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		// buffer.append("SELECT ID,
		// nOfficeID,nCurrencyID,sSubjectCode,sTransNo,nTransactionTypeID,nTransDirection,mAmount,dtExecute,
		// dtInterestStart,sAbstract,sMultiCode,
		// nInputUserID,nCheckUserID,nStatusID,nGroup,nType,nPostStatusID FROM
		// \n");
		// buffer.append(" sett_vglentry ");
		// buffer.append("\n WHERE dtExecute = ? \n");
		// buffer.append(" AND nOfficeID = " + officeID);
		// buffer.append(" AND nCurrencyID = " + currencyID);
		// buffer.append(" AND nStatusID = " +
		// SETTConstant.TransactionStatus.CHECK);
		// //liuq 增量导凭证
		// buffer.append(" AND (nPostStatusID in (2) or nPostStatusID is null)
		// ");
		// buffer.append(" order by STRANSNO,NTRANSDIRECTION ");

		/**
		 * --------------start----------------- add by shuangniu 2011-01-18
		 * 新奥增加辅助核算信息
		 */
		buffer.append(" select gl.id," + " \n ");
		buffer.append("        gl.nofficeid," + " \n ");
		buffer.append(" 		  gl.ncurrencyid," + " \n ");
		buffer.append(" 		  gl.ssubjectcode," + " \n ");
		buffer.append(" 		  gl.stransno," + " \n ");
		buffer.append(" 		  gl.dtexecute," + " \n ");
		buffer.append(" 		  gl.dtintereststart," + " \n ");
		buffer.append(" 		  gl.mamount," + " \n ");
		buffer.append(" 		  gl.ninputuserid," + " \n ");
		buffer.append(" 		  gl.sInputUserName," + " \n ");
		buffer.append(" 		  gl.ncheckuserid," + " \n ");
		buffer.append(" 		  gl.sCheckUserName," + " \n ");
		buffer.append(" 		  gl.sabstract," + " \n ");
		buffer.append(" 		  gl.sMultiCode," + " \n ");
		buffer.append(" 		  gl.ntransdirection," + " \n ");
		buffer.append(" 		  gl.ntransactiontypeid," + " \n ");
		buffer.append(" 		  gl.nStatusID," + " \n ");
		buffer.append(" 		  gl.nGroup," + " \n ");
		buffer.append(" 		  gl.nType," + " \n ");
		buffer.append(" 		  gl.nPostStatusID," + " \n ");
		buffer.append(" 		  assist.assitantName," + " \n ");
		buffer.append(" 		  assist.assitantvalue," + " \n ");
		buffer.append("        assist.clientCode," + " \n ");
		buffer.append("        assist.clientName," + " \n ");
		buffer.append("        assist.clientShortName" + " \n ");
		buffer
				.append("   from (select g.*, ui.sname sInputUserName, uc.sname sCheckUserName"
						+ " \n ");
		buffer
				.append("           from sett_vglentry g, userinfo ui, userinfo uc"
						+ " \n ");
		buffer.append("          where g.ninputuserid = ui.id(+)" + " \n ");
		buffer
				.append("            and g.ncheckuserid = uc.id(+)) gl,"
						+ " \n ");
		buffer
				.append("        (select gs.*, ci.code clientCode, ci.name clientName, ci.name2 clientShortName"
						+ " \n ");
		buffer.append("           from gl_assistant gs, client_clientinfo ci"
				+ " \n ");
		buffer
				.append("          where gs.assitantvalue = substr(ci.code, (instr(ci.code, '-') + 1))"
						+ " \n ");
		// buffer.append(" where gs.assitantvalue = 'XA' || substr(ci.code,
		// (instr(ci.code, '-') + 2))" + " \n ");
		buffer.append("            and ci.statusid = "
				+ Constant.RecordStatus.VALID + ") assist" + " \n ");
		buffer.append("  where gl.id = assist.glentryid(+)" + " \n ");
		buffer.append("    and gl.nofficeid = 1" + " \n ");
		buffer.append("    and gl.ncurrencyid = 1" + " \n ");
		buffer.append("    and gl.dtexecute = ?" + " \n ");

		/**
		 * --------------end----------------- add by shuangniu 2011-01-18
		 * 新奥增加辅助核算信息
		 */

		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, execDate);
			// System.out.println("SQL："+buffer.toString() + " \n 日期：" +
			// DataFormat.getDateString(execDate));
			rset = pstmt.executeQuery();

			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();

				tmp.setID(rset.getLong("ID"));
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setMultiCode(rset.getString("sMultiCode"));
				tmp.setInputUserID(rset.getLong("nInputUserID"));
				tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setInputUserName(rset.getString("sInputUserName"));
				tmp.setCheckUserName(rset.getString("sCheckUserName"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setGroup(rset.getLong("nGroup"));
				tmp.setType(rset.getLong("nType"));
				tmp.setPostStatusID(rset.getLong("nPostStatusID"));

				/**
				 * --------------start----------------- modify by shangniu
				 * 为辅助核素信息赋值
				 */
				// 辅助核算信息
				tmp.setAssitantName(rset.getString("assitantName") == null ? ""
						: rset.getString("assitantName"));
				tmp
						.setAssitantValue(rset.getString("assitantvalue") == null ? ""
								: rset.getString("assitantvalue"));
				// 客户信息
				tmp.setClientCode(rset.getString("clientCode") == null ? ""
						: rset.getString("clientCode"));
				tmp.setClientName(rset.getString("clientName") == null ? ""
						: rset.getString("clientName"));
				tmp
						.setClientShortName(rset.getString("clientShortName") == null ? ""
								: rset.getString("clientShortName"));
				/**
				 * --------------end----------------- modify by shangniu
				 * 为辅助核素信息赋值
				 */

				list.add(tmp);
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return list;
	}

	/**
	 * 上海电气专用明细科目汇总
	 * 
	 * @param execDate
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public Collection findByExecuteDateHz(Timestamp execDate, long officeID,
			long currencyID) throws Exception {

		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer
				.append("SELECT nOfficeID,nCurrencyID,sSubjectCode,nTransDirection,sum(mAmount) mAmount,dtExecute,nStatusID, nPostStatusID FROM \n");
		buffer.append(" sett_vglentry ");
		buffer.append("\n WHERE dtExecute = ? \n");
		buffer.append("AND nOfficeID = " + officeID);
		buffer.append("AND nCurrencyID = " + currencyID);
		buffer
				.append("AND nStatusID = "
						+ SETTConstant.TransactionStatus.CHECK);
		buffer
				.append(" group by nOfficeID,nCurrencyID,sSubjectCode,nTransDirection,dtExecute,nStatusID, nPostStatusID");

		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, execDate);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();

				// tmp.setID(rset.getLong("ID"));
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				// tmp.setTransNo(rset.getString("sTransNo"));
				// tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				// tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				// tmp.setAbstract(rset.getString("sAbstract"));
				// tmp.setMultiCode(rset.getString("sMultiCode"));
				// tmp.setInputUserID(rset.getLong("nInputUserID"));
				// tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				// tmp.setGroup(rset.getLong("nGroup"));
				// tmp.setType(rset.getLong("nType"));
				tmp.setPostStatusID(rset.getLong("nPostStatusID"));
				// modify by bingliu 2012-03-27 过滤掉金额为0的凭证，否则会导致押账出错
				if (tmp.getAmount() != 0) {
					list.add(tmp);
				}
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return list;
	}

	public Collection findByCondition(QueryGLEntryInfo condition)
			throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList(4);

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE 1 = 1 \n");
		if (condition.getSubjectCode() != null
				&& condition.getSubjectCode().length() > 0) {
			buffer.append(" and sSubjectCode='"
					+ condition.getSubjectCode().trim() + "' \n");
		}
		if (condition.getStartDate() != null) {
			buffer
					.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')>= to_date('"
							+ DataFormat.formatDate(condition.getStartDate())
							+ "','yyyy-mm-dd')");
		}
		if (condition.getEndDate() != null) {
			buffer
					.append(" and to_date(to_char(dtInterestStart,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(condition.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		if (condition.getStatusID() > 0) {
			buffer.append(" and nStatusID=" + condition.getStatusID() + " \n");
		}
		buffer.append(" order by sTransNo");
		try {
			conn = this.getConnection();
			log.info("sett_GLEntryDAO[findByCondition]_SQL:"
					+ buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();

				tmp.setID(rset.getLong("ID"));
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setMultiCode(rset.getString("sMultiCode"));
				tmp.setInputUserID(rset.getLong("nInputUserID"));
				tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setGroup(rset.getLong("nGroup"));
				tmp.setType(rset.getLong("nType"));
				tmp.setPostStatusID(rset.getLong("nPostStatusID"));

				list.add(tmp);
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		if (list.size() == 0) {
			return null;
		}

		return list;
	}

	public boolean checkPostVoucher(Timestamp execDate, long officeID,
			long currencyID) throws Exception {

		boolean bReturn = true;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT a.ID FROM \n");
		buffer.append("SETT_GLENTRY a,SETT_VGLSUBJECTDEFINITION b \n");
		buffer
				.append("\n WHERE a.ssubjectcode=b.ssubjectcode  and  a.dtExecute = ? \n");
		buffer.append(" AND a.nOfficeID = " + officeID);
		buffer.append(" AND a.nCurrencyID = " + currencyID);
		buffer.append(" AND a.nStatusID = "
				+ SETTConstant.TransactionStatus.CHECK);
		buffer.append(" AND a.nPostStatusID != "
				+ Constant.GLPostStatus.SUCCESS);
		buffer.append(" AND b.nsubjecttype != "
				+ SETTConstant.SubjectAttribute.TABLEOUT);

		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, execDate);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				bReturn = false;
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return bReturn;
	}

	public void deleteByTransNo(String stransNo) throws Exception {
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			/**
			 * Important: If any field in database changed, please correct them
			 * at here and fucntion:addDatatoPrepareStatement
			 */
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" WHERE stransNo = ? \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, SETTConstant.TransactionStatus.DELETED);
			ps.setString(2, stransNo);
			ps.executeUpdate();

		}

		finally {
			cleanup(ps);
			cleanup(con);
		}

	}

	// 根据已合并的交易编号来改变状态
	// 参数交易号，需要修改成的状态和新生成的批次号
	public void updateByTransNo(String stransNo, long lStatus, String sbatchno)
			throws Exception {
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			/**
			 * Important: If any field in database changed, please correct them
			 * at here and fucntion:addDatatoPrepareStatement
			 */
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" ,sbatchno = ? \n");
			strSQLBuffer.append(" WHERE stransNo = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lStatus);
			ps.setString(2, sbatchno);
			ps.setString(3, stransNo);
			ps.executeUpdate();
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(con);
		}

	}

	// 根据批次号来改变状态,批次号也置为空
	// 参数需要修改成的状态和批次号
	public void updateBySbatchNo(String sbatchno, long lStatus)
			throws Exception {
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			/**
			 * Important: If any field in database changed, please correct them
			 * at here and fucntion:addDatatoPrepareStatement
			 */
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" ,sbatchno = '' \n");
			strSQLBuffer.append(" WHERE sbatchno = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lStatus);
			ps.setString(2, sbatchno);
			ps.executeUpdate();
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(con);
		}

	}

	// 根据批次号来得到交易号
	// 参数为批次号
	public Collection findTransNoBySbatchNo(String sbatchno) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		String stransNo = "";
		try {
			con = getConnection();
			/**
			 * Important: If any field in database changed, please correct them
			 * at here and fucntion:addDatatoPrepareStatement
			 */
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select distinct STRANSNO from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE sbatchno = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, sbatchno);
			rs = ps.executeQuery();
			while (rs.next()) {
				stransNo = rs.getString("STRANSNO");
				list.add(stransNo);
			}
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(con);
		}
		return list;
	}

	// 根据交易号来得到批次号
	// 参数为交易号
	public String findSbatchNoByTransNo(String stransNo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sbatchno = "";
		try {
			con = getConnection();
			/**
			 * Important: If any field in database changed, please correct them
			 * at here and fucntion:addDatatoPrepareStatement
			 */
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select sbatchno from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE STRANSNO = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, stransNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				sbatchno = rs.getString("sbatchno");
			}
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(con);
		}
		return sbatchno;
	}

	// 根据批次号来得到整个信息
	// 参数为批次号
	public Collection findAllBySbatchNo(String sbatchno) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rset = null;
		List list = new ArrayList();
		String stransNo = "";
		try {
			con = getConnection();
			/**
			 * Important: If any field in database changed, please correct them
			 * at here and fucntion:addDatatoPrepareStatement
			 */
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE sbatchno = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, sbatchno);
			rset = ps.executeQuery();
			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();
				tmp.setID(rset.getLong("ID"));
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setMultiCode(rset.getString("sMultiCode"));
				tmp.setInputUserID(rset.getLong("nInputUserID"));
				tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setGroup(rset.getLong("nGroup"));
				tmp.setType(rset.getLong("nType"));
				tmp.setPostStatusID(rset.getLong("nPostStatusID"));
				list.add(tmp);
			}
		} finally {
			this.cleanup(rset);
			this.cleanup(ps);
			this.cleanup(con);
		}
		return list;
	}

	/**
	 * 资金计划需求: 到分录表sett_glentry中查找相同交易号、 相同金额、借贷方向相反、科目号like '102%'
	 * 的分录是否存在。如果存在则说明交易对方是外部银行，数据应该采集
	 */
	public boolean isInvolvedWithBankForTP(long officeID, long currencyID,
			String transNo, double amount, long debitOrCredit,
			long accountTypeID) throws Exception {
		boolean bReturn = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT a.ID FROM \n");
		buffer.append("SETT_GLENTRY a where ");
		buffer.append(" a.nOfficeID = " + officeID);
		buffer.append(" AND a.nCurrencyID = " + currencyID);
		buffer.append(" AND a.nStatusID > 0 ");
		buffer.append(" AND a.stransNo = '" + transNo + "'");
		buffer.append(" AND a.SSUBJECTCODE like '102%'");
		if (SETTConstant.AccountType.isCurrentAccountType(accountTypeID)
				|| SETTConstant.AccountType
						.isOtherDepositAccountType(accountTypeID))
			buffer.append(" AND a.mAmount = " + amount);
		else
			buffer.append(" AND a.mAmount >= " + amount);
		buffer.append(" AND a.NTRANSDIRECTION != " + debitOrCredit);

		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			if (rset.next()) {
				bReturn = true;
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return bReturn;
	}

	/**
	 * 此方法只针对一付多收活期数据采集 对于一付多收统计的金额需特殊处理，因一付多收的交易双方并不是一一对应的关系 资金计划需求:
	 * 到分录表sett_glentry中查找相同交易号、 相同金额、借贷方向相反、科目号like '102%'
	 * 的分录是否存在。如果存在则说明交易对方是外部银行， 数据采集此科目对应的金额
	 */
	public double getAmontOfOppositeForTP(long officeID, long currencyID,
			String transNo, long debitOrCredit) throws Exception {
		double bReturn = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT a.mamount FROM \n");
		buffer.append("SETT_GLENTRY a WHERE ");
		buffer.append(" a.nOfficeID = " + officeID);
		buffer.append(" AND a.nCurrencyID = " + currencyID);
		buffer.append(" AND a.nStatusID > 0 ");
		buffer.append(" AND a.stransNo = '" + transNo + "'");
		buffer.append(" AND a.SSUBJECTCODE like '102%'");
		buffer.append(" AND a.NTRANSDIRECTION != " + debitOrCredit);

		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				bReturn += rset.getDouble("mamount");
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return bReturn;
	}

	public Collection findByExecuteDateForNC(Timestamp execDate, long officeID,
			long currencyID) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer
				.append("SELECT ID, nOfficeID,nCurrencyID,sSubjectCode,sTransNo,nTransactionTypeID,nTransDirection,mAmount,dtExecute, dtInterestStart,sAbstract,sMultiCode, nInputUserID,nCheckUserID,nStatusID,nGroup,nType,nPostStatusID FROM \n");
		buffer.append(strTableName);
		buffer.append("\n WHERE dtExecute = ? \n");
		buffer.append("AND nOfficeID = " + officeID);
		buffer.append("AND nCurrencyID = " + currencyID);
		buffer
				.append("AND nStatusID = "
						+ SETTConstant.TransactionStatus.CHECK);
		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			System.out
					.println("findByExecuteDateForNC===============" + buffer);
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, execDate);
			rset = pstmt.executeQuery();
			Map idcache = new HashMap();
			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();
				String assInfo = "";
				tmp.setID(rset.getLong("ID"));
				if (Config.getProperty(ConfigConstant.GLOBAL_BTNLEVERLOG_ISVALID).trim().equals("true")) {
					if (idcache.get(rset.getLong("ID")) == null) {
						assInfo = findAssitant(rset.getLong("ID"));
						idcache.put(rset.getLong("ID"), assInfo);
					} else{
						assInfo = idcache.get(rset.getLong("ID")) + "";
					}
					String []assInfos = assInfo.split(",");	
					if(assInfos.length==2){
						tmp.setAssitantName(assInfos[0]);
						tmp.setAssitantValue(assInfos[1]);
					}else if(assInfos.length==1){
						tmp.setAssitantName(assInfos[0]);
					}
				}
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setMultiCode(rset.getString("sMultiCode"));
				tmp.setInputUserID(rset.getLong("nInputUserID"));
				tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setGroup(rset.getLong("nGroup"));
				tmp.setType(rset.getLong("nType"));
				tmp.setPostStatusID(rset.getLong("nPostStatusID"));
				list.add(tmp);
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return list;
	}

	// Boxu Add 2008年5月6
	public long checkSubject(String subject, long accountType) throws Exception {
		// sett_accounttype ，sett_subaccounttype_current ,
		// sett_subaccounttype_fixed , sett_subaccounttype_loan
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// StringBuffer buffer = new StringBuffer();

		String sqlStr1 = "select * from sett_accounttype where id="
				+ accountType + " and SSUBJECTCODE = '" + subject
				+ "' and NSTATUSID>0";
		String sqlStr2 = "select * from sett_subaccounttype_current where NACCOUNTTYPEID="
				+ accountType
				+ " and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT= '"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT= '"
				+ subject
				+ "' or SNEGOTIATEINTERESTSUBJECT= '"
				+ subject
				+ "' or SCOMMISSIONSUBJECT= '" + subject + "') and NSTATUSID>0";
		String sqlStr3 = "select * from sett_subaccounttype_fixed where NACCOUNTTYPEID="
				+ accountType
				+ " and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT= '"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT= '"
				+ subject
				+ "') and NSTATUSID>0";
		String sqlStr4 = "select * from sett_subaccounttype_loan where NACCOUNTTYPEID="
				+ accountType
				+ " and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT='"
				+ subject
				+ "' or SINTERESTSUBJECT='"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT='"
				+ subject
				+ "' or SCOMMISSIONSUBJECT='" + subject + "') and NSTATUSID>0";

		System.out
				.println("------------------------sqlStr1-------------------------------="
						+ sqlStr1);
		System.out
				.println("------------------------sqlStr2-------------------------------="
						+ sqlStr2);
		System.out
				.println("------------------------sqlStr3-------------------------------="
						+ sqlStr3);
		System.out
				.println("------------------------sqlStr4-------------------------------="
						+ sqlStr4);

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sqlStr1);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				return 1;
			}

			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr2);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				return 1;
			}

			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr3);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				return 1;
			}

			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr4);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				return 1;
			}

			pstmt.clearParameters();
			pstmt.close();
			rset.close();
		} finally {
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return lReturn;
	}

	// yyhe 于 2006-12-27 增加
	public long checkSubject1(String subject, long accountType)
			throws Exception {
		// sett_accounttype ，sett_subaccounttype_current ,
		// sett_subaccounttype_fixed , sett_subaccounttype_loan
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// StringBuffer buffer = new StringBuffer();

		String sqlStr1 = "select * from sett_accounttype where SACCOUNTTYPECODE='"
				+ accountType
				+ "' and SSUBJECTCODE = '"
				+ subject
				+ "' and NSTATUSID>0";
		String sqlStr2 = "select * from sett_subaccounttype_current where NACCOUNTTYPEID='"
				+ accountType
				+ "' and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT= '"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT= '"
				+ subject
				+ "' or SNEGOTIATEINTERESTSUBJECT= '"
				+ subject
				+ "' or SCOMMISSIONSUBJECT= '" + subject + "') and NSTATUSID>0";
		String sqlStr3 = "select * from sett_subaccounttype_fixed where NACCOUNTTYPEID='"
				+ accountType
				+ "' and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT= '"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT= '"
				+ subject
				+ "') and NSTATUSID>0";
		String sqlStr4 = "select * from sett_subaccounttype_loan where NACCOUNTTYPEID='"
				+ accountType
				+ "' and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT='"
				+ subject
				+ "' or SINTERESTSUBJECT='"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT='"
				+ subject
				+ "' or SCOMMISSIONSUBJECT='" + subject + "') and NSTATUSID>0";

		System.out
				.println("------------------------sqlStr1-------------------------------="
						+ sqlStr1);
		System.out
				.println("------------------------sqlStr2-------------------------------="
						+ sqlStr2);
		System.out
				.println("------------------------sqlStr3-------------------------------="
						+ sqlStr3);
		System.out
				.println("------------------------sqlStr4-------------------------------="
						+ sqlStr4);
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sqlStr1);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				// lReturn = 1;
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr2);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr3);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr4);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

		} finally {
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return lReturn;
	}

	// yyhe 于 2006-12-28 增加
	public long checkSubject2(String subject, long accountType)
			throws Exception {
		// sett_accounttype ，sett_subaccounttype_current ,
		// sett_subaccounttype_fixed , sett_subaccounttype_loan
		long lReturn = -1;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// StringBuffer buffer = new StringBuffer();

		String sqlStr1 = "select * from sett_accounttype where SACCOUNTTYPECODE='"
				+ accountType
				+ "' and SSUBJECTCODE = '"
				+ subject
				+ "' and NSTATUSID>0";
		String sqlStr2 = "select * from sett_subaccounttype_current where NACCOUNTTYPEID='"
				+ accountType
				+ "' and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT= '"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT= '"
				+ subject
				+ "' or SNEGOTIATEINTERESTSUBJECT= '"
				+ subject
				+ "' or SCOMMISSIONSUBJECT= '" + subject + "') and NSTATUSID>0";
		String sqlStr3 = "select * from sett_subaccounttype_fixed where NACCOUNTTYPEID='"
				+ accountType
				+ "' and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT= '"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT= '"
				+ subject
				+ "') and NSTATUSID>0";
		String sqlStr4 = "select * from sett_subaccounttype_loan where NACCOUNTTYPEID='"
				+ accountType
				+ "' and (SSUBJECTCODE = '"
				+ subject
				+ "' or SPAYINTERESTSUBJECT='"
				+ subject
				+ "' or SINTERESTSUBJECT='"
				+ subject
				+ "' or SBOOKEDINTERESTSUBJECT='"
				+ subject
				+ "' or SINTERESTTAXSUBJECT='"
				+ subject
				+ "' or SCOMMISSIONSUBJECT='" + subject + "') and NSTATUSID>0";

		System.out
				.println("------------------------sqlStr1-------------------------------="
						+ sqlStr1);
		System.out
				.println("------------------------sqlStr2-------------------------------="
						+ sqlStr2);
		System.out
				.println("------------------------sqlStr3-------------------------------="
						+ sqlStr3);
		System.out
				.println("------------------------sqlStr4-------------------------------="
						+ sqlStr4);
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sqlStr1);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				// lReturn = 1;
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr2);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr3);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

			pstmt = conn.prepareStatement(sqlStr4);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return 1;
			}
			pstmt.clearParameters();
			pstmt.close();
			rset.close();

		} finally {
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return lReturn;
	}

	// 查找辅助核算信息 yyhe 于 2006-12-26 增加
	public String findAssitant(long glentryid) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String svalue = null;
		String assitantname = "";
		String assitantvalue = "";
		StringBuffer buffer = new StringBuffer();
		buffer.append("select assitantname,assitantvalue \n");
		buffer.append(" from gl_assistant ");
		buffer.append(" where glentryid = " + glentryid + "");

		System.out.println("查找辅助核算信息SQL = " + buffer.toString());
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();

			if (rset.next()) {

				assitantname = rset.getString("assitantname");
				assitantvalue = rset.getString("assitantvalue");
			}
			svalue = assitantname + "," + assitantvalue;
		} finally {
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return svalue;
	}

	// 查找辅助核算信息 yyhe 于 2006-12-31 增加
	public String findAssitantInfo(String stransNo, Timestamp execDate,
			long nOfficeID, long nCurrencyID) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String svalue = null;
		String assitantname = "";
		String assitantvalue = "";
		StringBuffer buffer = new StringBuffer();

		buffer
				.append(" select * from sett_glentry g , gl_assistant t where \n");
		buffer.append(" g.id = t.glentryid and \n");
		buffer.append(" stransno in \n");
		buffer.append(" ( \n");
		buffer.append(" select sTransNo \n");
		buffer
				.append(" from sett_transaccountdetail trans,sett_account acc \n");
		buffer.append(" where trans.ntransaccountid = acc.id \n");
		buffer.append(" and acc.naccounttypeid = 11 \n");
		buffer.append(" and trans.dtExecute = ? \n");
		buffer.append(" and trans.nOfficeID = ? \n");
		buffer.append(" and trans.nCurrencyID = ?	\n");
		buffer.append(" and trans.nStatusID >0 \n");
		buffer.append(" and stransno=? \n");
		buffer.append(" ) \n");
		System.out.println("查找辅助核算信息SQL--二级户 = " + buffer.toString());
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, execDate);
			pstmt.setLong(2, nOfficeID);
			pstmt.setLong(3, nCurrencyID);
			pstmt.setString(4, stransNo + "1");
			rset = pstmt.executeQuery();

			if (rset.next()) {

				assitantname = rset.getString("assitantname");
				assitantvalue = rset.getString("assitantvalue");
			}
			svalue = assitantname + "," + assitantvalue;
		} finally {
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return svalue;
	}

	public static void main(String[] args) {

		/*
		 * sett_GLEntryDAO dao = new sett_GLEntryDAO(); long ll = -1; try { ll =
		 * dao.checkSubject2("530103",20); System.out.println(ll); if(ll>0) {
		 * 
		 * System.out.println("ok1");
		 * 
		 * }else{ System.out.println("ok2"); } } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */
	}

	// 增加辅助核算信息
	public long addAssitant(AssistantInfo assistantInfo) throws Exception {
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String assitantvalue = "";
		assitantvalue = assistantInfo.getAssitantValue();

		if (assitantvalue != null && assitantvalue.length() > 6
				&& assistantInfo.getGlentryID() > 0) {
			// assitantvalue = assitantvalue.substring(assitantvalue.length() -
			// 6);
			assitantvalue = assitantvalue.substring(3);

			StringBuffer buffer = new StringBuffer();
			buffer.append(" insert into gl_assistant ");
			buffer
					.append(" (id, glentryid, assitantname, assitantvalue, moditydate, modifyuserid, statusid) ");
			buffer.append(" values ");
			buffer.append(" ( ?, ?, ?, ?, sysdate, ?, ?) ");

			System.out.println("------------------------------------="
					+ buffer.toString());
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(buffer.toString());

				long id = this.getAssitantNextID();

				int nIndex = 1;

				pstmt.setLong(nIndex++, id);
				pstmt.setLong(nIndex++, assistantInfo.getGlentryID());
				pstmt.setString(nIndex++, assistantInfo.getAssitantName());
				pstmt.setString(nIndex++, assitantvalue);
				pstmt.setLong(nIndex++, assistantInfo.getModifyUserID());
				pstmt.setLong(nIndex++, assistantInfo.getStatusId());

				lReturn = pstmt.executeUpdate();
			} finally {
				this.cleanup(pstmt);
				this.cleanup(conn);
			}
		}

		return lReturn;
	}

	// 专门针对于国电开发
	public Collection findByExecuteDateForU850(Timestamp execDate,
			long officeID, long currencyID) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList list = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer
				.append("SELECT g.ID, g.nOfficeID, g.nCurrencyID, g.sSubjectCode, g.sTransNo, g.nTransactionTypeID, g.nTransDirection, g.mAmount, g.dtExecute, g.dtInterestStart, g.sAbstract, g.sMultiCode, g.nInputUserID, g.nCheckUserID, g.nStatusID, g.nGroup, g.nType, g.nPostStatusID \n");
		buffer.append(" , a.assitantname, a.assitantvalue ");
		buffer
				.append(" from sett_vglentry g, (select * from gl_assistant ga where ga.statusid > 0) a ");
		buffer.append(" where 1 = 1 ");

		buffer.append(" and g.id = a.glentryid(+) ");
		buffer.append(" and g.dtExecute = ? ");
		buffer.append(" and g.nOfficeID = ? ");
		buffer.append(" and g.nCurrencyID = ? ");
		buffer.append(" and g.nStatusID = ? ");
		// 成功导出的不再导出
		// buffer.append(" and nvl(nPostStatusID, -1) != ? ");

		buffer
				.append(" order by g.STRANSNO, g.ntransactiontypeid, g.NTRANSDIRECTION ");

		try {
			conn = this.getConnection();
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());

			int index = 1;
			pstmt.setTimestamp(index++, execDate);
			pstmt.setLong(index++, officeID);
			pstmt.setLong(index++, currencyID);
			pstmt.setLong(index++, SETTConstant.TransactionStatus.CHECK);
			// pstmt.setLong ( index++, Constant.GLPostStatus.SUCCESS );

			rset = pstmt.executeQuery();

			while (rset.next()) {
				GLEntryInfo tmp = new GLEntryInfo();

				tmp.setID(rset.getLong("ID"));
				tmp.setOfficeID(rset.getLong("nOfficeID"));
				tmp.setCurrencyID(rset.getLong("nCurrencyID"));
				tmp.setSubjectCode(rset.getString("sSubjectCode"));
				tmp.setTransNo(rset.getString("sTransNo"));
				tmp.setTransactionTypeID(rset.getLong("nTransactionTypeID"));
				tmp.setTransDirection(rset.getLong("nTransDirection"));
				tmp.setAmount(rset.getDouble("mAmount"));
				tmp.setExecute(rset.getTimestamp("dtExecute"));
				tmp.setInterestStart(rset.getTimestamp("dtInterestStart"));
				tmp.setAbstract(rset.getString("sAbstract"));
				tmp.setMultiCode(rset.getString("sMultiCode"));
				tmp.setInputUserID(rset.getLong("nInputUserID"));
				tmp.setCheckUserID(rset.getLong("nCheckUserID"));
				tmp.setStatusID(rset.getLong("nStatusID"));
				tmp.setGroup(rset.getLong("nGroup"));
				tmp.setType(rset.getLong("nType"));
				tmp.setPostStatusID(rset.getLong("nPostStatusID"));

				// 辅助核算名称
				if (rset.getString("assitantname") != null
						&& rset.getString("assitantname").length() > 0) {
					tmp.setAssitantName(rset.getString("assitantname"));
				}
				// 辅助核算值
				if (rset.getString("assitantvalue") != null
						&& rset.getString("assitantvalue").length() > 0) {
					tmp.setAssitantValue(rset.getString("assitantvalue"));
				}

				list.add(tmp);
			}
		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);

		}

		return list;
	}
}