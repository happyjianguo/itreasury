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
 * 用于存放拆入单信息业务逻辑方法
 * 
 * @author gmqiu
 * 
 */
public class LendingFormBiz {

	/**
	 * 拆入单信息Dao
	 */
	LendingFormDao dao = new LendingFormDao();

	/**
	 * 分页查询拆入单信息
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
	 * 新增拆入单信息
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
	 * 根据编号查询拆入信息
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
	 * 修改拆入单信息
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
	 * 取消复核,状态由已拆入、已还款改为拆入保存
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
		// 取消拆入复核（只有状态是已拆入时才能取消拆入复核）
		case (int) IPLANConstant.LendStatus.LENDED:
			this.dao.update_uncheck(id, IPLANConstant.LendStatus.LEND_SAVE,
					nCheckUserId, nPayInputUserId, nPayCheckUserId);
			break;
		// 取消还款复核（只有状态是已还款时才能取消还款复核）
		case (int) IPLANConstant.LendStatus.REPAYED:
			this.dao.update_uncheck(id, IPLANConstant.LendStatus.LEND_SAVE,
					nCheckUserId, nPayInputUserId, nPayCheckUserId);
			break;
		}
	}

	/**
	 * 修改状态_拆入复核
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
			// 拆入复核（只有状态是拆入保存时才能拆入复核）
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
	 * 修改状态_还款录入
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
			// 还款录入（只有状态是已拆入时才能还款录入）
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
	 * 修改状态_还款复核
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
			// 还款复核（只有状态是还款保存时才能还款复核）
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
	 * 修改状态_删除
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
			// 删除（只有状态是拆入保存时才能删除）
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
	 * 资金拆入统计查询
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
	 * 获得所有对手方信息
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
//	 * 获取时间段内最大拆入利率
//	 * @param qInfo
//	 * @return
//	 * @throws ITreasuryException
//	 */
//	public double getMaxPayRate(LendingFormQueryInfo qInfo) throws ITreasuryException {
//		double d = dao.findMaxPayRate(qInfo);
//		return d;
//	}

}
