package com.iss.itreasury.itreasuryinfo.fixed.bizlogic;

import com.iss.itreasury.itreasuryinfo.fixed.dao.FixedDipositDao;
import com.iss.itreasury.itreasuryinfo.fixed.dataentity.FixedDipositInfo;
import com.iss.itreasury.itreasuryinfo.fixed.dataentity.FixedDipositQueryInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.system.dao.PageLoader;
/**
 * 操作公司定期存款信息业务
 * @author gmqiu
 *
 */
public class FixedDipositBiz {

	private FixedDipositDao dao = new FixedDipositDao();
	
	/**
	 * 分页查询定期存款信息
	 * 
	 * @param qInfo
	 * @return
	 */
	public PageLoader queryFixedDipositPage(FixedDipositQueryInfo qInfo) {
		PageLoader pageLoader = null;
		pageLoader = dao.queryPage(qInfo);
		return pageLoader;
	}
	
	/**
	 * 根据ID查询定期存款信息
	 * @param id
	 * @return
	 * @throws ITreasuryException
	 */
	public FixedDipositInfo findById(long id) throws ITreasuryException {
		FixedDipositInfo fixedDipositInfo = null;
		fixedDipositInfo = dao.findById(id);
		return fixedDipositInfo;
	}
	
	/**
	 * 新增定期存款信息
	 * @param fixedDipositInfo
	 * @throws ITreasuryException
	 */
	public void insert(FixedDipositInfo fixedDipositInfo) throws ITreasuryException {
		dao.add(fixedDipositInfo);
	}
	
	/**
	 * 修改定期存款信息
	 * @param fixedDipositInfo
	 * @throws ITreasuryException
	 */
	public void update(FixedDipositInfo fixedDipositInfo) throws ITreasuryException {
		if(fixedDipositInfo.getnStatusId() == IPLANConstant.RateStatus.SAVED){
			dao.update(fixedDipositInfo);
		}else{
			throw new ITreasuryException("SETT_E406", null);
		}
	}
	
	/**
	 * 修改状态
	 * @param id
	 * @param nStatusId
	 * @param nCheckUserId
	 * @throws ITreasuryException
	 */
	public void updateStatus(long id, long nStatusId, long nCheckUserId) throws ITreasuryException {
		//只有当状态为"已保存"并且复核人ID不为空时才能复核
		if(nStatusId == IPLANConstant.RateStatus.SAVED && nCheckUserId != -1){
			dao.updateStatus(id, IPLANConstant.RateStatus.CHECKED, nCheckUserId);
		}
		//只有当状态为"已复核"并且复核人ID不为空时才能取消复核
		else if(nStatusId == IPLANConstant.RateStatus.CHECKED){
			dao.updateStatus(id, IPLANConstant.RateStatus.SAVED, nCheckUserId);
		}
		//只有当状态为"已保存"并且复核人ID为空时才能删除
		else if(nStatusId == IPLANConstant.RateStatus.SAVED && nCheckUserId == -1){
			dao.updateStatus(id, IPLANConstant.RateStatus.DELETEED, nCheckUserId);
		}
		//否则提示状态已更新，请检查后重新操作
		else{
			throw new ITreasuryException("SETT_E406", null);
		}
	}
	
}
