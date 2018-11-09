package com.iss.itreasury.settlement.transfixeddeposit.dao;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

public class NotifyDepositDao {
	
	public String queryNotifyDepositSQL(NotifyDepositInformInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();

		long  moduleID = qInfo.getModuleID();
		
		//select
		sbSQL.append(" select a.saccountno  saccountno,  c.sname clientName ,  t.depositno DepositNo ,u.mamount mAmount ,t.amount amount ,t.moduleid moduleID, t.notifydate notifyDate1 , t.id ID , t.statusid statusID , t.userid userID \n");
		//from
		sbSQL.append(" from sett_notifyDepositInform t ,sett_transopenfixeddeposit u, sett_account a , client c \n");
		if (moduleID== Constant.ModuleType.EBANK)
        {
        	sbSQL.append(" , ob_user us \n");
        }
		sbSQL.append(" where \n");
		//where
		sbSQL.append(" t.statusid >0 \n");
		sbSQL.append(" and t.depositno  = u.sdepositno \n");
		sbSQL.append(" and a.id = u.naccountid \n");
		sbSQL.append(" and c.id = u.nclientid \n");
    
        if (moduleID== Constant.ModuleType.EBANK)
        {
        	sbSQL.append(" and t.moduleid = " + Constant.ModuleType.EBANK+"\n");
        	sbSQL.append(" and us.id = t.userid \n");
        	sbSQL.append(" and t.userid = "+qInfo.getUserID()+"\n");
        }
		if (moduleID == Constant.ModuleType.SETTLEMENT )
		{
			sbSQL.append(" and u.nofficeid = " + qInfo.getOfficeID());//区分办事处
			sbSQL.append(" and u.ncurrencyid = " + qInfo.getCurrencyID());//区分币种
				
		}
		
		return sbSQL.toString();
	}
	
	



}
