package com.iss.itreasury.itreasuryinfo.branch.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.branch.dao.BranchRateDao;
import com.iss.itreasury.itreasuryinfo.branch.dataentity.BranchRateInfo;
import com.iss.itreasury.itreasuryinfo.branch.dataentity.BranchRateQueryInfo;
import com.iss.itreasury.itreasuryinfo.branch.dataentity.BranchRateQueryResultInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.system.dao.PageLoader;

/**
 * 银行利率计划业务
 * 
 * @author gmqiu
 * 
 */
public class BranchRateBiz {
	/**
	 * 银行利率Dao
	 */
	private BranchRateDao dao = new BranchRateDao();

	/**
	 * 分页查询利率信息
	 * 
	 * @param qInfo
	 * @return PageLoader
	 */
	public PageLoader queryBranchRatePage(BranchRateQueryInfo qInfo) {
		PageLoader pageLoader = null;
		pageLoader = dao.queryBranchRatePage(qInfo);
		return pageLoader;
	}

	/**
	 * 根据名称模糊查询开户行
	 * 
	 * @param sName
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryBranchByName(BranchRateQueryInfo qInfo, String sName)
			throws ITreasuryException {
		Collection collection = dao.findBranchByName(qInfo, sName);
		return collection;
	}

	/**
	 * 查询其他开户行
	 * 
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryOtherBranchByName(BranchRateQueryInfo qInfo)
			throws ITreasuryException {
		Collection collection = dao.findOtherBranchByName(qInfo);
		return collection;
	}

	/**
	 * 查询其他开户行名称
	 * 
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryOtherByCondition(BranchRateQueryInfo qInfo)
			throws ITreasuryException {
		Collection collection = dao.findOtherByCondition(qInfo);
		return collection;
	}

	/**
	 * 查询开户行名称(用于循环开户行名称)
	 * 
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryBranchRate(BranchRateQueryInfo qInfo, String sName)
			throws ITreasuryException {
		// Collection collection = dao.findByCondition(qInfo, sName);
		Collection collection = dao.findBranchNameByCondition(qInfo, sName);
		return collection;
	}

	public Collection findFirstColumn(BranchRateQueryInfo qInfo)
			throws ITreasuryException {
		Collection collection = dao.findFirstColumn(qInfo);
		return collection;
	}

	public Collection findSecondColumn(BranchRateQueryInfo qInfo)
			throws ITreasuryException {
		Collection collection = dao.findSecondColumn(qInfo, -1);
		return collection;
	}

	/**
	 * add by fxzhang 查询所有开户行在某一段时间内的利率
	 * 
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryBranchRate(BranchRateQueryInfo qInfo)
			throws ITreasuryException {
		// //将collection转换为returnV，
		// returnV<BranchRateResult1(bankTypeID,branchList<BranchRateResult2(branchID,rateList<BranchRateResultInfo>)>)>
		Collection collection_date = dao.findBranchDate(qInfo);
		Collection collection_id = dao.findSecondColumn(qInfo, -1);
		Collection returnV = new ArrayList();
		Collection collection = dao.findDateSlot(qInfo.getValidateStart(),
				qInfo.getValidateEnd());
		// returnV.addAll(collection);
		// BranchRateResult1 result1 = new BranchRateResult1();
		// BranchRateResult2 result2 = new BranchRateResult2();
		if (collection != null && collection.size() > 0
				&& collection_date.size() > 0 && collection_id.size() > 0) {
			Object[] results_date = collection_date.toArray();
			Object[] results_id = collection_id.toArray();
			// Object[] results = collection.toArray();
			for (int i = 0; i < results_date.length; i++) {
				// 循环日期
				BranchRateQueryResultInfo bri = (BranchRateQueryResultInfo) results_date[i];
				BranchRateQueryResultInfo brqri = new BranchRateQueryResultInfo();
				for (int k = 0; k < results_id.length; k++) {
					BranchRateQueryResultInfo bqInfo_id = (BranchRateQueryResultInfo) results_id[k];
					brqri.setBranchID(bqInfo_id.getBranchID());
					brqri.setRate(dao.findBefforValidateRate(qInfo, DataFormat
							.formatDate(bri.getValidate()), bqInfo_id
							.getBranchID()));
				}
				brqri.setValidate(bri.getValidate());
				returnV.add(brqri);
				// for (int j = 0; j < results.length; j++) {
				// BranchRateQueryResultInfo bqInfo =
				// (BranchRateQueryResultInfo)results[j];
				// }
			}
		}
		return returnV;
	}

	/**
	 * 获取开户行生效日期(用于循环生效日期)
	 * 
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryBranchDate(BranchRateQueryInfo qInfo)
			throws ITreasuryException {
		Collection collection = dao.findBranchDate(qInfo);
		return collection;
	}

	/**
	 * 循环时间段
	 * 
	 * @param validateStart
	 * @param validateEnd
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection findDateSlot(String validateStart, String validateEnd)
			throws ITreasuryException {
		Collection collection = dao.findDateSlot(validateStart, validateEnd);
		return collection;
	}

	/**
	 * 循环利率
	 * 
	 * @param qInfo
	 * @param branchId
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryBranchRateByCondition(BranchRateQueryInfo qInfo,
			long branchId) throws ITreasuryException {
		Collection collection = dao.findBranchRateByCondition(qInfo, branchId);
		return collection;
	}

	/**
	 * 获取生效日期利率(用于获取利率)
	 * 
	 * @param qInfo
	 * @param sName
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public double queryRateByCondition(BranchRateQueryInfo qInfo,
			String validate, String sName) throws ITreasuryException {
		double rate = dao.findRateByCondition(qInfo, validate, sName);
		return rate;
	}

	/**
	 * 获取生效日期其他利率(用于循环其他利率)
	 * 
	 * @param qInfo
	 * @param sName
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection findOtherRateByCondition(BranchRateQueryInfo qInfo,
			String validate) throws ITreasuryException {
		Collection collection = dao.findOtherRateByCondition(qInfo, validate);
		return collection;
	}

	/**
	 * 新增利率
	 * 
	 * @param br
	 * @throws SQLException
	 * @throws ITreasuryException
	 */
	public void save(BranchRateInfo br) throws SQLException, ITreasuryException {
		long v_branchId = 0;
		double v_mRate = 0.0;
		String v_dtValidate = "";
		boolean validation = false;
		if (br != null) {
			v_branchId = br.getBranchId();
			v_mRate = br.getmRate();
			v_dtValidate = DataFormat.formatDate(br.getDtValidate());
			validation = dao.isCountmRate(v_branchId, v_mRate, v_dtValidate);
			// 验证该利率值在当前生效时间，当前开户行是否存在，若存在则提示，不存在方可新增。
			if (validation == false) {
				dao.save(br);
			} else {
				throw new ITreasuryException("Sett_E402", null);
			}
		} else {
			throw new ITreasuryException("SETT_403", null);
		}
	}

	/**
	 * 根据id修改利率
	 * 
	 * @param id
	 * @param branchId
	 * @param mRate
	 * @param dtValidate
	 * @throws ITreasuryException
	 */
	public void update(long id, long branchId, double mRate,
			Timestamp dtValidate) throws SQLException, ITreasuryException {
		boolean validation = false;
		if (id != -1) {
			BranchRateInfo u_bri = dao.findById(id);
			if (u_bri.getnStatusId() == IPLANConstant.RateStatus.SAVED) {
				if (branchId == u_bri.getBranchId()
						&& mRate == u_bri.getmRate()
						&& dtValidate == u_bri.getDtValidate()) {
					throw new ITreasuryException("Sett_E404", null);
				} else {
					validation = dao.isCountmRate(branchId, mRate, DataFormat
							.formatDate(dtValidate));
					if (validation == false) {
						dao.update(id, branchId, mRate, dtValidate);
					} else {
						throw new ITreasuryException("Sett_E402", null);
					}
				}
			} else {
				throw new ITreasuryException("Sett_E406", null);
			}
		} else {
			throw new ITreasuryException("Sett_E407", null);
		}
	}

	/**
	 * 根据id查询某条利率信息
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws ITreasuryException
	 */
	public BranchRateInfo findById(long id) throws SQLException,
			ITreasuryException {
		BranchRateInfo info = null;
		if (id != -1) {
			info = dao.findById(id);
		} else {
			throw new ITreasuryException("Sett_E407", null);
		}
		return info;
	}

	/**
	 * 复核，修改利率状态为3
	 * 
	 * @param id
	 * @param nCheckUserId
	 * @param dtCheckDate
	 * @throws SQLException
	 * @throws ITreasuryException
	 */
	public void update_check(long id, long nCheckUserId) throws SQLException,
			ITreasuryException {
		if (id != -1 && nCheckUserId != -1) {
			BranchRateInfo u_bri = dao.findById(id);
			if (u_bri.getnStatusId() != IPLANConstant.RateStatus.CHECKED) {
				if (u_bri.getnInputUserId() != nCheckUserId) {
					dao.update_check(id, nCheckUserId);
				} else {
					throw new ITreasuryException("Sett_E410", null);
				}
			} else {
				throw new ITreasuryException("Sett_E406", null);
			}
		} else {
			throw new ITreasuryException("Sett_E408", null);
		}
	}

	/**
	 * 取消复核
	 * 
	 * @param id
	 * @throws SQLException
	 * @throws ITreasuryException
	 */
	public void update_uncheck(long id) throws SQLException, ITreasuryException {
		if (id != -1) {
			BranchRateInfo u_bri = dao.findById(id);
			if (u_bri.getnStatusId() == IPLANConstant.RateStatus.CHECKED) {
				dao.update_uncheck(id);
			} else {
				throw new ITreasuryException("Sett_E406", null);
			}
		} else {
			throw new ITreasuryException("Sett_E407", null);
		}
	}

	/**
	 * 删除某条利率，实际是把状态改为0，数据库中任存在此条记录
	 * 
	 * @param id
	 * @throws SQLException
	 * @throws ITreasuryException
	 */
	public void delete_upadte(long id) throws SQLException, ITreasuryException {
		if (id != -1) {
			BranchRateInfo u_bri = dao.findById(id);
			if (u_bri.getnStatusId() == IPLANConstant.RateStatus.SAVED) {
				dao.delete_upadte(id);
			} else {
				throw new ITreasuryException("Sett_E406", null);
			}
		} else {
			throw new ITreasuryException("Sett_E407", null);
		}
	}

	/**
	 * 验证利率值是否重复
	 * 
	 * @param branchId
	 * @param mRate
	 * @param dtValidate
	 * @throws SQLException
	 * @throws ITreasuryDAOException
	 * @return
	 */
	public boolean validation(long branchId, double mRate, String dtValidate)
			throws ITreasuryDAOException {
		boolean b = false;
		b = dao.isCountmRate(branchId, mRate, dtValidate);
		return b;
	}

	
	
	
	/**
	 * 全部利率台账查询,拼页面元素
	 * 
	 * @param qInfo
	 * @return String
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public String getBranchRate(BranchRateQueryInfo qInfo) throws ITreasuryException {
		Connection conn = null;
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryException("Gen_E001", e);
		}
		BranchRateDao rateDao = new BranchRateDao(conn);

		StringBuffer sb = new StringBuffer();
		Collection collection_date = null;
		Collection collection_id = null;
		Collection collection_first = null;
		Object[] results_date = null;
		Object[] results_id = null;
		Object[] results_first = null;
		try {
			// 日期集合
			collection_date = rateDao.findDateSlot(qInfo.getValidateStart(), qInfo.getValidateEnd());
			// 表头第一行集合
			collection_first = rateDao.findFirstColumn(qInfo);
			// 表头第二行集合，其中包含branchId
			collection_id = rateDao.findSecondColumn(qInfo, -1);
			if (collection_date.size() > 0 && collection_id.size() > 0 && collection_first.size() > 0) {
				results_date = collection_date.toArray();
				results_id = collection_id.toArray();
				results_first = collection_first.toArray();
			}
			// 创建表头第一行
			sb.append("<tr valign='middle'>");
			sb.append("<td height='25' width='100%' align='center' colspan='"+(collection_id.size()+1)+"' class=ItemTitle>");
			sb.append("<h5>银行利率台账</h5> </td>");
			sb.append("</tr>");
			sb.append("<tr valign='middle'>");
			sb.append("<td align='center' rowspan='2' class=ItemTitle>日期</td>");
			for (int i = 0; i < results_first.length; i++) {
				BranchRateQueryResultInfo obj_oneTitle = (BranchRateQueryResultInfo) results_first[i];
				sb.append("<td align='center' class=ItemTitle>");
				sb.append(IPLANConstant.Bank.getBankName(obj_oneTitle.getBankTypeID()));
				sb.append("</td>");
			}
			sb.append("</tr>");
			// 创建表头第二行
			sb.append("<tr valign='middle'>");
			for (int i = 0; i < results_id.length; i++) {
				BranchRateQueryResultInfo obj_twoTitle = (BranchRateQueryResultInfo) results_id[i];
				sb.append("<td align='center' class=ItemTitle>");
				sb.append(obj_twoTitle.getBankAccountCode());
				sb.append("</td>");
			}
			sb.append("</tr>");
			// 第三行开始创建数据
			// 按日期循环,循环一次创建一个<tr>
			for (int i = 0; i < results_date.length; i++) {
				BranchRateQueryResultInfo obj_date = (BranchRateQueryResultInfo) results_date[i];
				sb.append("<tr valign='middle'>");
				sb.append("<td width='7%' align='center' class=ItemBody>");
				sb.append(DataFormat.formatDate(obj_date.getValidate()));
				sb.append("</td>");
				// 按branchId循环，循环一次创建一个<td>
				for (int j = 0; j < results_id.length; j++) {
					BranchRateQueryResultInfo obj_id = (BranchRateQueryResultInfo) results_id[j];
					sb.append("<td align='center' class=ItemBody>");
					// 查询当前日期之前（包含当前日期）最大日期的利率值
					// 传入一个日期,一个branchId得到一个利率
					sb.append(rateDao.findBefforValidateRate(qInfo, DataFormat.formatDate(obj_date.getValidate()), obj_id.getBranchID()));
					sb.append("</td>");
				}
				sb.append("</tr>");
			}
			try {
				conn.commit();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ITreasuryException("Gen_E001", e1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 如果出现异常，则回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
			throw new ITreasuryException("Gen_E001", e);
		} finally {
			// 最后关闭数据库连接
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
		}
		return sb.toString();
	}
	
	public String getBranchRatePrint(BranchRateQueryInfo qInfo) throws ITreasuryException {
		Connection conn = null;
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryException("Gen_E001", e);
		}
		BranchRateDao rateDao = new BranchRateDao(conn);

		StringBuffer sb = new StringBuffer();
		Collection collection_date = null;
		Collection collection_id = null;
		Collection collection_first = null;
		Object[] results_date = null;
		Object[] results_id = null;
		Object[] results_first = null;
		try {
			// 日期集合
			collection_date = rateDao.findDateSlot(qInfo.getValidateStart(), qInfo.getValidateEnd());
			// 表头第一行集合
			collection_first = rateDao.findFirstColumn(qInfo);
			// 表头第二行集合，其中包含branchId
			collection_id = rateDao.findSecondColumn(qInfo, -1);
			if (collection_date.size() > 0 && collection_id.size() > 0 && collection_first.size() > 0) {
				results_date = collection_date.toArray();
				results_id = collection_id.toArray();
				results_first = collection_first.toArray();
			}
			// 创建表头第一行
			sb.append("<tr valign='middle'>");
			sb.append("<td height='25' width='100%' align='center' colspan='"+(collection_id.size()+1)+"' class=ItemTitle>");
			sb.append("<h5>银行利率台账</h5> </td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td align='center' rowspan='2' class='td-topright' height='20'>日期</td>");
			for (int i = 0; i < results_first.length; i++) {
				BranchRateQueryResultInfo obj_oneTitle = (BranchRateQueryResultInfo) results_first[i];
				sb.append("<td align='center' class='td-topright' height='20'>");
				sb.append(IPLANConstant.Bank.getBankName(obj_oneTitle.getBankTypeID()));
				sb.append("</td>");
			}
			sb.append("</tr>");
			// 创建表头第二行
			sb.append("<tr>");
			for (int i = 0; i < results_id.length; i++) {
				BranchRateQueryResultInfo obj_twoTitle = (BranchRateQueryResultInfo) results_id[i];
				sb.append("<td align='center' class='td-topright' height='20'>");
				sb.append(obj_twoTitle.getBankAccountCode());
				sb.append("</td>");
			}
			sb.append("</tr>");
			// 第三行开始创建数据
			// 按日期循环,循环一次创建一个<tr>
			for (int i = 0; i < results_date.length; i++) {
				BranchRateQueryResultInfo obj_date = (BranchRateQueryResultInfo) results_date[i];
				sb.append("<tr>");
				sb.append("<td width='7%' align='center' class='td-topright' height='20'>");
				sb.append(DataFormat.formatDate(obj_date.getValidate()));
				sb.append("</td>");
				// 按branchId循环，循环一次创建一个<td>
				for (int j = 0; j < results_id.length; j++) {
					BranchRateQueryResultInfo obj_id = (BranchRateQueryResultInfo) results_id[j];
					sb.append("<td align='center' class='td-topright' height='20'>");
					// 查询当前日期之前（包含当前日期）最大日期的利率值
					// 传入一个日期,一个branchId得到一个利率
					sb.append(rateDao.findBefforValidateRate(qInfo, DataFormat.formatDate(obj_date.getValidate()), obj_id.getBranchID()));
					sb.append("</td>");
				}
				sb.append("</tr>");
			}
			try {
				conn.commit();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ITreasuryException("Gen_E001", e1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 如果出现异常，则回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
			throw new ITreasuryException("Gen_E001", e);
		} finally {
			// 最后关闭数据库连接
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
		}
		return sb.toString();
	}

	/**
	 * 查询单个利率台账
	 * 
	 * @param qInfo
	 * @param firstTitle
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public String getSingleBranchRate(BranchRateQueryInfo qInfo, long firstTitle) throws ITreasuryException {
		Connection conn = null;
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryException("Gen_E001", e);
		}
		BranchRateDao rateDao = new BranchRateDao(conn);

		StringBuffer sb = new StringBuffer();
		Collection collection_date = null;
		Collection collection_id = null;
		Object[] results_date = null;
		Object[] results_id = null;
		try {
			// 日期集合
			collection_date = rateDao.findDateSlot(qInfo.getValidateStart(), qInfo.getValidateEnd());
			// 表头第二行集合，其中包含branchId
			collection_id = rateDao.findSecondColumn(qInfo, firstTitle);
			if (collection_date.size() > 0 && collection_id.size() > 0) {
				results_date = collection_date.toArray();
				results_id = collection_id.toArray();
				// 创建表头第一行
				sb.append("<tr valign='middle'>");
				sb.append("<td height='25' width='100%' align='center' colspan='"+(collection_id.size()+1)+"' class=ItemTitle>");
				sb.append("<h5>银行利率台账</h5> </td>");
				sb.append("</tr>");
				sb.append("<tr valign='middle'>");
				sb.append("<td align='center' rowspan='2' class=ItemTitle>日期</td>");
				sb.append("<td align='center' colspan='" + results_id.length + "' class=ItemTitle>");
				sb.append(IPLANConstant.Bank.getBankName(firstTitle));
				sb.append("</td>");
				// 创建表头第二行
				sb.append("<tr valign='middle'>");
				for (int i = 0; i < results_id.length; i++) {
					BranchRateQueryResultInfo obj_twoTitle = (BranchRateQueryResultInfo) results_id[i];
					sb.append("<td align='center' class=ItemTitle>");
					sb.append(obj_twoTitle.getBankAccountCode());
					sb.append("</td>");
				}
				sb.append("</tr>");
				// 第三行开始创建数据
				// 按日期循环,循环一次创建一个<tr>
				for (int i = 0; i < results_date.length; i++) {
					BranchRateQueryResultInfo obj_date = (BranchRateQueryResultInfo) results_date[i];
					sb.append("<tr valign='middle'>");
					sb.append("<td width='7%' align='center' class=ItemBody>");
					sb.append(DataFormat.formatDate(obj_date.getValidate()));
					sb.append("</td>");
					// 按branchId循环，循环一次创建一个<td>
					for (int j = 0; j < results_id.length; j++) {
						BranchRateQueryResultInfo obj_id = (BranchRateQueryResultInfo) results_id[j];
						sb.append("<td align='center' class=ItemBody>");
						// 查询当前日期之前（包含当前日期）最大日期的利率值
						// 传入一个日期,一个branchId得到一个利率
						sb.append(rateDao.findBefforValidateRate(qInfo, DataFormat.formatDate(obj_date.getValidate()),obj_id.getBranchID()));
						sb.append("</td>");
					}
					sb.append("</tr>");
				}
				try {
					conn.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new ITreasuryException("Gen_E001", e1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 如果出现异常，则回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
			throw new ITreasuryException("Gen_E001", e);
		} finally {
			// 最后关闭数据库连接
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
		}
		return sb.toString();
	}
	
	public String getSingleBranchRatePrint(BranchRateQueryInfo qInfo, long firstTitle) throws ITreasuryException {
		Connection conn = null;
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryException("Gen_E001", e);
		}
		BranchRateDao rateDao = new BranchRateDao(conn);

		StringBuffer sb = new StringBuffer();
		Collection collection_date = null;
		Collection collection_id = null;
		Object[] results_date = null;
		Object[] results_id = null;
		try {
			// 日期集合
			collection_date = rateDao.findDateSlot(qInfo.getValidateStart(), qInfo.getValidateEnd());
			// 表头第二行集合，其中包含branchId
			collection_id = rateDao.findSecondColumn(qInfo, firstTitle);
			if (collection_date.size() > 0 && collection_id.size() > 0) {
				results_date = collection_date.toArray();
				results_id = collection_id.toArray();
				// 创建表头第一行
				sb.append("<tr valign='middle'>");
				sb.append("<td height='25' width='100%' align='center' colspan='"+(collection_id.size()+1)+"' class=ItemTitle>");
				sb.append("<h5>银行利率台账</h5> </td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td align='center' rowspan='2' class='td-topright' height='20'>日期</td>");
				sb.append("<td align='center' colspan='" + results_id.length + "' class='td-topright' height='20'>");
				sb.append(IPLANConstant.Bank.getBankName(firstTitle));
				sb.append("</td>");
				// 创建表头第二行
				sb.append("<tr>");
				for (int i = 0; i < results_id.length; i++) {
					BranchRateQueryResultInfo obj_twoTitle = (BranchRateQueryResultInfo) results_id[i];
					sb.append("<td align='center' class='td-topright' height='20'>");
					sb.append(obj_twoTitle.getBankAccountCode());
					sb.append("</td>");
				}
				sb.append("</tr>");
				// 第三行开始创建数据
				// 按日期循环,循环一次创建一个<tr>
				for (int i = 0; i < results_date.length; i++) {
					BranchRateQueryResultInfo obj_date = (BranchRateQueryResultInfo) results_date[i];
					sb.append("<tr>");
					sb.append("<td width='7%' align='center' class='td-topright' height='20'>");
					sb.append(DataFormat.formatDate(obj_date.getValidate()));
					sb.append("</td>");
					// 按branchId循环，循环一次创建一个<td>
					for (int j = 0; j < results_id.length; j++) {
						BranchRateQueryResultInfo obj_id = (BranchRateQueryResultInfo) results_id[j];
						sb.append("<td align='center' class='td-topright' height='20'>");
						// 查询当前日期之前（包含当前日期）最大日期的利率值
						// 传入一个日期,一个branchId得到一个利率
						sb.append(rateDao.findBefforValidateRate(qInfo, DataFormat.formatDate(obj_date.getValidate()),obj_id.getBranchID()));
						sb.append("</td>");
					}
					sb.append("</tr>");
				}
				try {
					conn.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new ITreasuryException("Gen_E001", e1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 如果出现异常，则回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
			throw new ITreasuryException("Gen_E001", e);
		} finally {
			// 最后关闭数据库连接
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ITreasuryException("Gen_E001", e);
			}
		}
		return sb.toString();
	}


}
