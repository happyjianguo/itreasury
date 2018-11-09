package com.iss.itreasury.itreasuryinfo.lending.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.lending.dao.LendingFormDao;
import com.iss.itreasury.itreasuryinfo.lending.dataentity.LendingFormInfo;
import com.iss.itreasury.itreasuryinfo.lending.dataentity.LendingFormQueryInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.system.dao.PageLoader;

/**
 * ���ڴ�Ų��뵥��Ϣҵ���߼�����
 * 
 * @author gmqiu
 * 
 */
public class LendingFormBiz {

	/**
	 * ���뵥��ϢDao
	 */
	LendingFormDao dao = new LendingFormDao();

	/**
	 * ��ҳ��ѯ���뵥��Ϣ
	 * 
	 * @param qInfo
	 * @return
	 * @throws RemoteException
	 * @throws IException
	 */
	public PageLoader queryLendingFormPage(LendingFormQueryInfo qInfo) {
		PageLoader pageLoader = null;
		pageLoader = dao.queryLendingFormPage(qInfo);
		return pageLoader;
	}
	
	/**
	 * �������뵥��Ϣ
	 * 
	 * @param lendingFormInfo
	 * @throws ITreasuryDAOException
	 */
	public void save(LendingFormInfo lendingFormInfo) throws ITreasuryException {
		if (lendingFormInfo != null) {
			this.dao.insertLendingForm(lendingFormInfo);
		} else {
			throw new ITreasuryException("SETT_E403", null);
		}
	}

	/**
	 * ���ݱ�Ų�ѯ������Ϣ
	 * 
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws ITreasuryException
	 */
	public LendingFormInfo findById(long id) throws ITreasuryException {
		LendingFormInfo lf = null;
		if (id != -1) {
			lf = this.dao.findById(id);
		} else {
			throw new ITreasuryException("SETT_E407", null);
		}
		return lf;
	}

	/**
	 * �޸Ĳ��뵥��Ϣ
	 * 
	 * @param lendingFormInfo
	 * @throws ITreasuryException
	 */
	public void update(LendingFormInfo lendingFormInfo)
			throws ITreasuryException {
		if (lendingFormInfo.getnStatusId() == IPLANConstant.LendStatus.LEND_SAVE) {
			this.dao.updateLendingForm(lendingFormInfo);
		} else {
			throw new ITreasuryException("SETT_E406", null);
		}
	}

	/**
	 * ȡ������,״̬���Ѳ��롢�ѻ����Ϊ���뱣��
	 * 
	 * @param id
	 * @param nStatusId
	 * @param nCheckUserId
	 * @param nPayInputUserId
	 * @param nPayCheckUserId
	 * @throws ClassNotFoundException
	 * @throws ITreasuryException
	 */
	public void update_uncheck(long id, long nStatusId, long nCheckUserId,
			long nPayInputUserId, long nPayCheckUserId)
			throws ITreasuryException {
		switch ((int) nStatusId) {
		// ȡ�����븴�ˣ�ֻ��״̬���Ѳ���ʱ����ȡ�����븴�ˣ�
		case (int) IPLANConstant.LendStatus.LENDED:
			this.dao.update_uncheck(id, IPLANConstant.LendStatus.LEND_SAVE,
					nCheckUserId, nPayInputUserId, nPayCheckUserId);
			break;
		// ȡ������ˣ�ֻ��״̬���ѻ���ʱ����ȡ������ˣ�
		case (int) IPLANConstant.LendStatus.REPAYED:
			this.dao.update_uncheck(id, IPLANConstant.LendStatus.LEND_SAVE,
					nCheckUserId, nPayInputUserId, nPayCheckUserId);
			break;
		}
	}

	/**
	 * �޸�״̬_���븴��
	 * 
	 * @param id
	 * @param nStatusId
	 * @param nCheckUserId
	 * @param nPayInputUserId
	 * @param nPayCheckUserId
	 * @throws ClassNotFoundException
	 * @throws ITreasuryException
	 */
	public void update_lend_check(long id, long nStatusId, long nCheckUserId,
			long nPayInputUserId, long nPayCheckUserId)
			throws ITreasuryException {
		if (id != -1) {
			LendingFormInfo lendingFormInfo = this.dao.findById(id);
			// ���븴�ˣ�ֻ��״̬�ǲ��뱣��ʱ���ܲ��븴�ˣ�
			if (nCheckUserId != lendingFormInfo.getnInputUserId()) {
				if (nStatusId == IPLANConstant.LendStatus.LEND_SAVE) {
					this.dao.updateStatusId(id,
							IPLANConstant.LendStatus.LENDED, nCheckUserId, -1, -1);
				} else {
					throw new ITreasuryException("SETT_E406", null);
				}
			} else {
				throw new ITreasuryException("SETT_E410", null);
			}
		} else {
			throw new ITreasuryException("SETT_E407", null);
		}
	}

	/**
	 * �޸�״̬_����¼��
	 * 
	 * @param id
	 * @param nStatusId
	 * @param nCheckUserId
	 * @param nPayInputUserId
	 * @param nPayCheckUserId
	 * @throws ClassNotFoundException
	 * @throws ITreasuryException
	 */
	public void update_reply_input(long id, long nStatusId,
			long nPayInputUserId, String dtPayDate) throws ITreasuryException {
		if (id != -1) {
			// ����¼�루ֻ��״̬���Ѳ���ʱ���ܻ���¼�룩
			if (nStatusId == IPLANConstant.LendStatus.LENDED) {
				this.dao.update_repay_save(id,
						IPLANConstant.LendStatus.REPAY_SAVE, nPayInputUserId,
						dtPayDate);
			} else {
				throw new ITreasuryException("SETT_E406", null);
			}
		} else {
			throw new ITreasuryException("SETT_E407", null);
		}
	}

	/**
	 * �޸�״̬_�����
	 * 
	 * @param id
	 * @param nStatusId
	 * @param nCheckUserId
	 * @param nPayInputUserId
	 * @param nPayCheckUserId
	 * @throws ClassNotFoundException
	 * @throws ITreasuryException
	 */
	public void update_reply_check(long id, long nStatusId, long nCheckUserId,
			long nPayInputUserId, long nPayCheckUserId)
			throws ITreasuryException {
		if (id != -1) {
			LendingFormInfo lendingFormInfo = this.dao.findById(id);
			// ����ˣ�ֻ��״̬�ǻ����ʱ���ܻ���ˣ�
			if (lendingFormInfo.getnPayInputUserId() != nPayCheckUserId) {
				if (nStatusId == IPLANConstant.LendStatus.REPAY_SAVE) {
					nCheckUserId = lendingFormInfo.getnCheckUserId();
					nPayInputUserId = lendingFormInfo.getnPayInputUserId();
					this.dao.updateStatusId(id,
							IPLANConstant.LendStatus.REPAYED, nCheckUserId,
							nPayInputUserId, nPayCheckUserId);
				} else {
					throw new ITreasuryException("SETT_E406", null);
				}
			} else {
				throw new ITreasuryException("SETT_E410", null);
			}
		} else {
			throw new ITreasuryException("SETT_E407", null);
		}
	}

	/**
	 * �޸�״̬_ɾ��
	 * 
	 * @param id
	 * @param nStatusId
	 * @param nCheckUserId
	 * @param nPayInputUserId
	 * @param nPayCheckUserId
	 * @throws ITreasuryException
	 * @throws ClassNotFoundException
	 */
	public void deleteStatusId(long id, long nStatusId, long nCheckUserId,
			long nPayInputUserId, long nPayCheckUserId)
			throws ITreasuryException {
		if (id != -1) {
			// ɾ����ֻ��״̬�ǲ��뱣��ʱ����ɾ����
			if (nStatusId == IPLANConstant.LendStatus.LEND_SAVE) {
				this.dao.updateStatusId(id, IPLANConstant.LendStatus.DELETEED,
						-1, -1, -1);
			} else {
				throw new ITreasuryException("SETT_E406", null);
			}
		} else {
			throw new ITreasuryException("SETT_E407", null);
		}
	}
	
	/**
	 * �ʽ����ͳ�Ʋ�ѯ
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 */
	public Collection queryLendingFormForReport(LendingFormQueryInfo qInfo) throws ITreasuryException {
		Collection collection = null;
		collection = dao.findLendingFormForReport(qInfo);
		return collection;
	}
	
	/**
	 * ������ж��ַ���Ϣ
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 */
	public Collection getAllCounterParty(LendingFormQueryInfo qInfo) throws ITreasuryException {
		Collection collection = null;
		collection = dao.findCounterParty(qInfo);
		return collection;
	}
	
//	/**
//	 * ��ȡʱ���������������
//	 * @param qInfo
//	 * @return
//	 * @throws ITreasuryException
//	 */
//	public double getMaxPayRate(LendingFormQueryInfo qInfo) throws ITreasuryException {
//		double d = dao.findMaxPayRate(qInfo);
//		return d;
//	}

}
