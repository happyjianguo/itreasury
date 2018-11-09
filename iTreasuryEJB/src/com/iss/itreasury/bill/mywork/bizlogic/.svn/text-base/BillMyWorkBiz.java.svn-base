package com.iss.itreasury.bill.mywork.bizlogic;

import com.iss.itreasury.bill.mywork.dao.BillDraftOutActionDao;
import com.iss.itreasury.bill.mywork.dao.BillMyWorkDao;
import com.iss.itreasury.bill.mywork.dao.BillTransDiscountActonDao;
import com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo;

public class BillMyWorkBiz implements java.io.Serializable {

	public Object queryBillDraftOutActionWork(BillMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		BillMyWorkDao dao = new BillDraftOutActionDao();
		objReturn = dao.queryMyWork(qInfo);
		
		return objReturn;
	}
	public Object queryBillTransDiscountActionWork(BillMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		BillMyWorkDao dao = new BillTransDiscountActonDao();
		objReturn = dao.queryMyWork(qInfo);
		
		return objReturn;
	}
}
