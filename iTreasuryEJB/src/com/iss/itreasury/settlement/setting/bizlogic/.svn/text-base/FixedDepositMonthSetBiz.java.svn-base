package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.settlement.setting.dao.FixedDepositMonthSetDao;
import com.iss.itreasury.settlement.setting.dataentity.FixedDepositMonthInfo;
import com.iss.itreasury.settlement.transloan.dao.QTransGrantLoanDao;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class FixedDepositMonthSetBiz {
	FixedDepositMonthSetDao dao = new FixedDepositMonthSetDao();

	public PagerInfo queryFixedDepositMonthSet(FixedDepositMonthInfo qInfo)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = dao.queryFixedDepositMonthSetSQL(qInfo);
			pagerInfo.setSqlString(sql);

			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSTIME");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[] { "TRANSTIME", "ID" });
			baseInfo.setExtensionType(new long[] { PagerTypeConstant.STRING,
					PagerTypeConstant.STRING });
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + ","
					+ PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SNAME");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("MODIFYDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);

			pagerInfo.setDepictList(depictList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public Collection queryFixedDepositMonth(FixedDepositMonthInfo info) {
		Collection coll = null;
		FixedDepositMonthSetDao dao = new FixedDepositMonthSetDao();
		try {
			coll = dao.queryFixedDepositMonth(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coll;
	}

	public void saveFixedDepositMonth(FixedDepositMonthInfo info)
			throws Exception {
		FixedDepositMonthSetDao dao = new FixedDepositMonthSetDao();
		Collection coll = null;
		try {
			coll = dao.checkFixedDepositMonth(info);
			if (coll.size() > 0) {
				throw new IException("存款期限设置重复！");
			}
			if (info.getId() > 0) {
				dao.update(info);
			} else {
				dao.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	}

	public boolean checkInUsed(long depositterm) throws Exception {
		boolean used = false;
		// Collection coll = null;
		long flag;
		FixedDepositMonthSetDao dao = new FixedDepositMonthSetDao();
		try {
			flag = dao.checkInUsed(depositterm);
			if (flag > 0) {
				used = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return used;
	}
}
