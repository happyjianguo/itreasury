package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.setting.dao.SettOfficeTimeDAO;
import com.iss.itreasury.settlement.setting.dataentity.SettOfficeTimeInfo;
import com.iss.itreasury.util.IException;

public class SettOfficeTimeBiz {
	public long addSettOfficeTime(SettOfficeTimeInfo info)throws IException{
		long id = -1;
		try{
			SettOfficeTimeDAO dao = new SettOfficeTimeDAO();
			id = dao.add(info);
		}catch(Exception ex){
			throw new IException("Gen_E001");
		}
		return id;
	}
	
	public Collection getExistCurrency(long lOfficeId) throws IException{
		Collection c = null;
		try{
			SettOfficeTimeDAO dao = new SettOfficeTimeDAO();
			c = dao.getExistCurrency(lOfficeId);
		}catch(Exception ex){
			throw new IException("Gen_E001");
		}
		return c;
	}
}
