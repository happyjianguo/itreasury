package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import com.iss.itreasury.settlement.query.Dao.QCounterTransDao;
import com.iss.itreasury.settlement.query.paraminfo.QCounterTransInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * ҵ����ͳ��
 * 
 * @author songwenxiao
 * 
 */
public class QCounterTransBiz {

	QCounterTransDao dao = new QCounterTransDao();

	public PagerInfo queryCounterTrans(QCounterTransInfo paramInfo)
			throws Exception {
		// TODO Auto-generated method stub
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try {
				pagerInfo = new PagerInfo();
				// �õ���ѯSQL
				sql = dao.queryCounterTransSQL(paramInfo);
				pagerInfo.setSqlString(sql);

				// pagerInfo.setUsertext("���׽��ϼ�{SumPay};���׼�¼�����ϼ�{nCount}");
				pagerInfo.setTotalExtensionMothod(QCounterTransBiz.class,
						"totalResultSetHandle");

				ArrayList depictList = new ArrayList();
				PagerDepictBaseInfo baseInfo = null;

				// ��������
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("TransactionTypeID");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.TransactionType.class,
						"getName", new Class[] { long.class });
				depictList.add(baseInfo);

				// ���׽��ϼ�
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("SumReceive");
				baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
				depictList.add(baseInfo);

				// ���׼�¼����
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("nCount");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);

				pagerInfo.setDepictList(depictList);

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>��ѯ�쳣", e);
			}
			return pagerInfo;
		}

	}

	public ArrayList totalResultSetHandle(ResultSet rs) throws Exception {
		double sum =0;
		long count = 0;
		while(rs.next()){
		    sum += rs.getDouble("SumPay");
			count += rs.getLong("nCount");
		}
		ArrayList list = new ArrayList();
		list.add("���׽��ϼ�{" + DataFormat.formatDisabledAmount(sum) + "}");
		list.add("���׼�¼�����ϼ�{" + count + "}");
		return list;
	}

}
