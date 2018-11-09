package com.iss.itreasury.settlement.transfixeddeposit.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class TransFixedContinueBiz {
	Sett_TransFixedContinueDAO fixedContinuedao = new Sett_TransFixedContinueDAO();
	
	public PagerInfo findByConditions(QueryByStatusConditionInfo info) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{		
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = fixedContinuedao.queryTransFixedContinueSQL(info);
			System.out.print("888888888:"+sql);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			//���׺�
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sTransNo");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"sTransNo", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nTransactionTypeID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			//�����˻���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("naccountid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getAccountNoByID", new Class[]{long.class});
			depictList.add(baseInfo);
			//���ڴ��ݺ�
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sdepositno");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			//�´��ݺ�
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("snewdepositno");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			//���ױ�����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("mamount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			//������Ϣ���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("mwithdrawinterest");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			//��ʼ��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtintereststart");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			//������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtexecute");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			//ժҪ
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sabstract");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			//״̬
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nstatusid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣:"+e.getMessage());
		}
		return pagerInfo;
	}
	
}
