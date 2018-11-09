package com.iss.itreasury.itreasuryinfo.subject.bizlogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.subject.dao.SubjectBalanceDao;
import com.iss.itreasury.itreasuryinfo.subject.dataentity.SubjectBalanceInfo;
import com.iss.itreasury.itreasuryinfo.subject.dataentity.SubjectBalanceQueryInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.system.dao.PageLoader;

/**
 * 用于存放资金项目余额信息业务逻辑方法
 * 
 * @author gmqiu
 * 
 */
public class SubjectBalanceBiz {

	/**
	 * 资金项目余额Dao
	 */
	private SubjectBalanceDao dao = new SubjectBalanceDao();

	/**
	 * 资金项目余额分页查询
	 * 
	 * @param qInfo
	 * @return
	 * @throws IException
	 */
	public PageLoader queryPageList(SubjectBalanceQueryInfo qInfo) {
		PageLoader pageLoader = null;
		pageLoader = dao.queryPageList(qInfo);
		return pageLoader;
	}

	public SubjectBalanceInfo findById(long id) throws ITreasuryException {
		SubjectBalanceInfo subjectBalanceInfo = null;
		subjectBalanceInfo = dao.findById(id);
		return subjectBalanceInfo;
	}

	public boolean findByIdAndDate(long id, String dtBalanceDate)
			throws ITreasuryException {
		boolean b = false;
		b = dao.findByIdAndDate(id, dtBalanceDate);
		return b;
	}

	/**
	 * 新增
	 * 
	 * @param subjectBalanceInfo
	 * @throws ITreasuryException
	 */
	public void save(SubjectBalanceInfo subjectBalanceInfo)
			throws ITreasuryException {
		dao.insert(subjectBalanceInfo);
	}

	public void update(long id, double mBalance) throws ITreasuryException {
		SubjectBalanceInfo subjectBalanceInfo = null;
		subjectBalanceInfo = dao.findById(id);
		if (subjectBalanceInfo.getnStatusId() == IPLANConstant.RateStatus.SAVED) {
			dao.update(id, mBalance);
		}
	}

	/**
	 * 删除操作
	 * 
	 * @param id
	 * @param statusId
	 *            当前状态
	 */
	public void update_delete(long id, long nStatusId)
			throws ITreasuryException {
		if (nStatusId == IPLANConstant.RateStatus.SAVED) {
			this.dao.updateStatusId(id, IPLANConstant.RateStatus.DELETEED, -1);
		} else {
			throw new ITreasuryException("SETT_E406", null);
		}
	}

	/**
	 * 复核操作
	 * 
	 * @param id
	 * @param nStatusId
	 *            当前状态
	 * @param nCheckUserId
	 * @throws ITreasuryException
	 * @throws ClassNotFoundException
	 */
	public void update_check(long id, long nStatusId, long nCheckUserId)
			throws ITreasuryException {
		if (nStatusId == IPLANConstant.RateStatus.SAVED) {
			this.dao.updateStatusId(id, IPLANConstant.RateStatus.CHECKED,
					nCheckUserId);
		} else {
			throw new ITreasuryException("SETT_E406", null);
		}
	}

	/**
	 * 取消复核操作
	 * 
	 * @param id
	 * @param nStatusId
	 *            当前状态
	 * @param nCheckUserId
	 * @throws ITreasuryException
	 */
	public void update_uncheck(long id, long nStatusId, long nCheckUserId)
			throws ITreasuryException {
		if (nStatusId == IPLANConstant.RateStatus.CHECKED) {
			this.dao.updateStatusId(id, IPLANConstant.RateStatus.SAVED, -1);
		} else {
			throw new ITreasuryException("SETT_E406", null);
		}
	}

	/**
	 * 查询当日有效的资金项目余额信息
	 * 
	 * @param nInputUserId
	 * @param dtBalanceDate
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findSubjectBalancesByDayForModify(String dtBalanceDate,
			long nKind) throws ITreasuryException {
		Collection collection = new ArrayList();
		collection = dao.findSubjectBalancesByDayForModify(dtBalanceDate, nKind);
		return collection;
	}

	public Collection findSubject(long nKind) throws ITreasuryException {
		Collection collection = new ArrayList();
		collection = dao.findSubject(nKind);
		return collection;
	}

}
