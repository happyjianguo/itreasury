package com.iss.itreasury.settlement.reportlossorfreeze.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.reportlossorfreeze.dao.Sett_ReportLossOrFreezeQueryDAO;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class ReportLossOrFreezeBiz {
	
	Sett_ReportLossOrFreezeQueryDAO dao=new Sett_ReportLossOrFreezeQueryDAO();
	
	/*public PagerInfo findByConditions( ReportLossOrFreezeQueryInfo info) throws Exception
	{
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try
		{
			pagerInfo=new PagerInfo();
			//�õ���ѯ��SQL
			sql=dao.getSQL(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//���ױ��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSNO");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"TRANSNO", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSACTIONTYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
						
			//�ͻ����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientcode");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�˻���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("accountNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�ɴ浥��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("OLDDEPOSITNO");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�´浥��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NEWDEPOSITNO");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//��Ч����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EFFECTIVEDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//ִ������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EXECUTEDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//����Ա
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("checkUserName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//ִ�л���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EXECUTEGOVERNMENT");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FREEZETYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.SubAccountStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FREEZEAMOUNT");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣:"+e.getMessage());
		}
		return pagerInfo;
}*/
	/**
	 * ��ʧ�ͽ��
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public PagerInfo findByConditionsSuspendAndHanding( ReportLossOrFreezeQueryInfo info) throws Exception
	{
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try
		{
			pagerInfo=new PagerInfo();
			//�õ���ѯ��SQL
			sql=dao.getSQL(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//���ױ��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSNO");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"TRANSNO", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSACTIONTYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
						
			//�ͻ����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientcode");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�˻���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("accountNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�ɴ浥��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("OLDDEPOSITNO");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//��Ч����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EFFECTIVEDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//ִ������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EXECUTEDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//����Ա
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("checkUserName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
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
	/**
	 * ����֤�顪�����Ӳ���
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public PagerInfo findByConditionsIssCertificates( ReportLossOrFreezeQueryInfo info) throws Exception
	{
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try
		{
			pagerInfo=new PagerInfo();
			//�õ���ѯ��SQL
			sql=dao.getSQL(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//���ױ��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSNO");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"TRANSNO", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSACTIONTYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
						
			//�ͻ����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientcode");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�˻���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("accountNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�ɴ浥��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("OLDDEPOSITNO");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�´浥��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NEWDEPOSITNO");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//��Ч����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EFFECTIVEDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//ִ������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EXECUTEDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//����Ա
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("checkUserName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
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
	/**
	 * ����ͽⶳ
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public PagerInfo findReportFreezeByConditions( ReportLossOrFreezeQueryInfo info) throws Exception
	{
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try
		{
			pagerInfo=new PagerInfo();
			//�õ���ѯ��SQL
			sql=dao.getSQL(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//���ױ��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSNO");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"TRANSNO", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("TRANSACTIONTYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.TransactionType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
						
			//�ͻ����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("clientcode");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�˻���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("accountNo");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//�ɴ浥��
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("OLDDEPOSITNO");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//��Ч����
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EFFECTIVEDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//��������
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("FREEZETYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.SubAccountStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//ִ�л���
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EXECUTEGOVERNMENT");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
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
