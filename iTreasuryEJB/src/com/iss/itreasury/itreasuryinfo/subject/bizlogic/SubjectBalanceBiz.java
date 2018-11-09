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
 * ���ڴ���ʽ���Ŀ�����Ϣҵ���߼�����
 * 
 * @author gmqiu
 * 
 */
public class SubjectBalanceBiz {

	/**
	 * �ʽ���Ŀ���Dao
	 */
	private SubjectBalanceDao dao = new SubjectBalanceDao();

	/**
	 * �ʽ���Ŀ����ҳ��ѯ
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
	 * ����
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
	 * ɾ������
	 * 
	 * @param id
	 * @param statusId
	 *            ��ǰ״̬
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
	 * ���˲���
	 * 
	 * @param id
	 * @param nStatusId
	 *            ��ǰ״̬
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
	 * ȡ�����˲���
	 * 
	 * @param id
	 * @param nStatusId
	 *            ��ǰ״̬
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
	 * ��ѯ������Ч���ʽ���Ŀ�����Ϣ
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
