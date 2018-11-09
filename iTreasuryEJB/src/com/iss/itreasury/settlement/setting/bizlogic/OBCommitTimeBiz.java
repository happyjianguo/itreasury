package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Timestamp;
import java.util.Date;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_OBCommitTimeDAO;
import com.iss.itreasury.settlement.setting.dataentity.OBCommitTime;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;

public class OBCommitTimeBiz {
	
	public static void save(OBCommitTime cTime) throws SettlementException, ITreasuryDAOException{
		Sett_OBCommitTimeDAO dao = new Sett_OBCommitTimeDAO();
		dao.save(cTime);
	}
	
	public static void update(OBCommitTime cTime) throws SettlementException, ITreasuryDAOException{
		Sett_OBCommitTimeDAO dao = new Sett_OBCommitTimeDAO();
		dao.update(cTime);
	}
	
	public static OBCommitTime findOBCommitTime(OBCommitTime cTime) throws SettlementException, ITreasuryDAOException{
		Sett_OBCommitTimeDAO dao = new Sett_OBCommitTimeDAO();
		return dao.findOBCommitTime(cTime);
	}
	
	public boolean validateOBCommitTime(Timestamp tsExecute, long lOffice, long lCurrencyID) throws IException{
		boolean isValidate = false;
		try {
			Timestamp systemDateTime = Env.getSystemDateTime(lOffice, lCurrencyID);
			String strSD = DataFormat.getDateString(systemDateTime);

			if(tsExecute.compareTo(DataFormat.getDateTime(strSD)) == 0){
				OBCommitTime cTime = new OBCommitTime();
				cTime.setOfficeId(lOffice);
				cTime.setCurrencyId(lCurrencyID);
				OBCommitTime result = OBCommitTimeBiz.findOBCommitTime(cTime);
		
				if (result != null) {
					Date commitDateTime = DataFormat.parseDate(strSD + " " + result.getCommitTime() + ":00", DataFormat.FMT_DATE_YYYYMMDD_HHMMSS);
					
					if(systemDateTime.getTime() >= commitDateTime.getTime()){
						if (result.getIsControl() == SETTConstant.OBCommitTimeControlType.RIGID) {
							throw new IException("提交时间已超过结算最迟接收时间");
						}
						isValidate = false;
					}
					else {
						isValidate = true;
					}
				}
				else{
					isValidate = true;
				}
			}
			else {
				isValidate = true;
			}
		}
		catch(Exception e){
			throw new IException(e.getMessage());
		}
		return isValidate;
	}
}
