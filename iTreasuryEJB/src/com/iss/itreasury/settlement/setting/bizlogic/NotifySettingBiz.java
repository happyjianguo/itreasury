package com.iss.itreasury.settlement.setting.bizlogic;

import com.iss.itreasury.settlement.setting.dao.Sett_NotifySettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.NotifySettingInfo;

public class NotifySettingBiz {
	private Sett_NotifySettingDAO dao = null;
	public NotifySettingBiz() {
		this.dao = new Sett_NotifySettingDAO();
	}
	/**
	 * 保存info
	 * @param info
	 * @throws Exception
	 */
	public void saveInfo(NotifySettingInfo info) throws Exception{
		long id=dao.isExist(info.getOfficeId(), info.getCurrencyId());
		if(id!=-1){ 
		//if(info.getId()!=-1){
			info.setId(id);
			dao.updateModifyUser(info);
		}else{
			info.setId(dao.createNewID());
			dao.addInfo(info);
		}
	}
	/**
	 * 撤销操作
	 * @param info
	 * @throws Exception
	 */
	public void cancel(NotifySettingInfo info) throws Exception{
		if(info.getId()!=-1){
			dao.updateStatus(info.getId(), info.getStatusId());
		}
	}
	/**
	 * 得到记录结果
	 * @param officeId 办事处id
	 * @param currencyId 币种id
	 * @return 若找不到会返回null
	 * @throws Exception
	 */
	public NotifySettingInfo getInfo(long officeId, long currencyId) throws Exception{
		return dao.findRecord(officeId, currencyId);
	}
}
