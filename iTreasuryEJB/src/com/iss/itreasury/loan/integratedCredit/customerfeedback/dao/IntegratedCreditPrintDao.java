package com.iss.itreasury.loan.integratedCredit.customerfeedback.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.loan.integratedCredit.customerfeedback.bizlogic.CustomerfeedbackBean;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.CreditFrameInfo;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanBankliabilitiesdetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanClientInfo;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanCreditgrade;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanCreditgradedetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanExternalliabilities;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceanalyse;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceitemdetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanGroupinsidecontact;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanManageanalyse;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanReceiveaccountage;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanReceivefundsonaccount;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class IntegratedCreditPrintDao {

	public static String TEMPLET_SEPERATOR = " &&& ";
	public static String PAGE_SEPERATOR = " :: ";
	public static String CONTENT_SEPERATOR = " ;; "; 
	public static String SEPERATOR = "  "; 
	public static String NOCHARACTOR = "��������"; 
	public static String SHOW_SELECTED = "��";
	public static String SHOW_DISSELECTED = "��";
	
	
	private static Log4j log4j = null;
 
	public IntegratedCreditPrintDao()
	{ 
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}


	/**
	 * ���ɿͻ��������۱�������д�ֶ����� 
	 * @Create Date: 2008-12-04
	 * @author YuZhang
	 * @param lClientID �ͻ�ID
	 * @param lCreditgradeID �������۱���ID
	 * 
	 * @return String �������۱����ӡ����
	 * @throws IException
	 * @exception Exception
	 */
	public List makeContent(long lClientID, long lCreditgradeID, long lOperationType) 
	{
		return new ArrayList();
//		final int[] itemID = {
//				0, 17,   1,   2,   4,   5,   9,   6,  10,  11,  12,
//			   13,  14,  23,  24,  25,  26,  27,  32,  39,  52,
//			   40,  43,  44,  45,  49, 138,  50,  53,  56,  61,
//			   69,  63,  64,  65,  67,  68,  71,  72,  81,  86,
//			   75,  76,  79,  83,  81,  87,  89, 102, 114, 123,
//			  125, 126, 127, 128, 129, 130, 131, 132, 133, 134,
//			  135, 136, 137};
//		final double[] valuesrate = {
//				0.02,0.02,0.02,0.03,0.03,0.03,0.03,0.03,0.03,0.01,0.02,0.03,0.08,0.08,0.04,0.04,0.06,0.05,0.05,
//				0.03,0.06,0.05,0.05,0.04,0.07,0.03,0.03,0.04
//		};
//		CustomerfeedbackDao customerfeedbackDao = new CustomerfeedbackDao();
//		LoanClientDao clientDao = new LoanClientDao();
//		LoanFinanceitemdetailDao loanFinanceitemdetailDao = new LoanFinanceitemdetailDao();
//		
//		LoanCreditgrade creditgrade=new LoanCreditgrade();
//    	LoanClientInfo clientInfo = new LoanClientInfo();
//		LoanReceiveaccountage rage = null;
//		LoanReceivefundsonaccount sonaccount=null;
//		LoanFinanceitemdetail loanFinanceitemdetail = null;
//		CreditFrameInfo einfo=null;
//		LoanExternalliabilities inforInfo = null;
//		LoanGroupinsidecontact groupinsidecontact=null;
//		LoanManageanalyse Manageanalyse=new LoanManageanalyse();
//		LoanCreditGradedetailDao loanCreditGradedetailDao = new LoanCreditGradedetailDao();
//		
//		StringBuffer sbContent = new StringBuffer();
//		int thisYear = Integer.parseInt(DataFormat.formatDate(Env.getSystemDate()).split("-")[0]);
//		int month = Integer.parseInt(DataFormat.formatDate(Env.getSystemDate()).split("-")[1]);
//		String strTemp = "";
//		int i=0;
//		double dtemp1 = 0;
//		double dtemp2 = 0;
//		double dtemp3 = 0;
//		long zzhang = 0;
//		double zamount = 0;
//		double zscale = 0;
//		double  rAmount = 0;
//		double rScale = 0;
//		Collection coll=null;
//		Iterator iter = null;
//		ArrayList contentList = new ArrayList();
//
//		/*
//		 * ��Ƥ
//		 */
//		//��ѯ
//		creditgrade=customerfeedbackDao.findByID(lCreditgradeID);
//		clientInfo = clientDao.findClientByID(lClientID);
//		//��ѯEND
//		//���鱨����
//		contentList.add(creditgrade.getCreditcode());
//		//�������ڣ�  ��  ��  ��
//		if(creditgrade.getGradedate()!=null&&!"".equals(creditgrade.getGradedate()))
//		{
//			strTemp = DataFormat.formatDate(creditgrade.getGradedate());
//			contentList.add(strTemp.substring(0,4));
//			contentList.add(strTemp.substring(5,7));
//			contentList.add(strTemp.substring(8,10));
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//			contentList.add(NOCHARACTOR);
//			contentList.add(NOCHARACTOR);
//		}
//		//�ͻ�����
//		contentList.add(clientInfo.getName()==null&&"".equals(clientInfo.getName())?NOCHARACTOR:clientInfo.getName());
//		//ֱ��������
//		contentList.add(NameRef.getUserNameByID(creditgrade.getInputuserid()));
//		//��������� �����϶���
//		contentList.add(NOCHARACTOR);
//		contentList.add(NOCHARACTOR);
//		/*
//		 * һ��	�������
//		 */
//		//�ͻ���ϸ��ַ
//		contentList.add(clientInfo.getAddress());
//		//��������
//		contentList.add(clientInfo.getZipCode());
//		//����Ӫҵִ�պ���
//		contentList.add(clientInfo.getLicenceCode());
//		//���˴���֤��
//		contentList.add(clientInfo.getLegalPersonCode());
//		//�����֤������
//		contentList.add(clientInfo.getLoanCardNo());
//		//�������
//		contentList.add(clientInfo.getLoanCardPwd());
//		//��Ӫҵ��
//		contentList.add(clientInfo.getSmainbusiness());
//		//��ҵ����Ӫҵ���Ƿ��Ǽ���֧�ַ�չ����Ŀ
//		if(clientInfo.getNupportthedevelopment()==1)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		if(clientInfo.getNupportthedevelopment()==2)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//��Ӫҵ��
//		contentList.add(clientInfo.getSotherbusiness());
//		
//		double financialItem[] = new CustomerfeedbackBean().findLatestFinancialItem(clientInfo.getClientID());
//		//�ʲ���ģ
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[0]) + "Ԫ\t");
//		//���ʲ�
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[1]) + "Ԫ");
//		//��������
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[2]) + "Ԫ\t");
//		//������
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[3]) + "Ԫ");
//		//��������������
//		contentList.add(clientInfo.getStatutoryname());
//		//��������������ְ��
//		contentList.add(clientInfo.getStatutoryotherduties());
//		//���������˵绰
//		contentList.add(clientInfo.getStatutorysphone());
//		//��Ȩ����������
//		contentList.add(clientInfo.getSauthorizedagentname());
//		//��Ȩ����������ְ��
//		contentList.add(clientInfo.getSauotherduties());
//		//��Ȩ�����˵绰
//		contentList.add(clientInfo.getNauphone());
//		//������������
//		contentList.add(clientInfo.getStreasurername());
//		//������������ְ��
//		contentList.add(clientInfo.getStreasurerduty());
//		//�������ܵ绰
//		contentList.add(clientInfo.getNtreasurerphone());
//		//���˴������
//		//����
//		contentList.add(clientInfo.getLegalPerson());
//		//����
//		strTemp = String.valueOf(clientInfo.getNcorporateage());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//�Ա�
//		if (clientInfo.getScorporategender()==LOANConstant.LoanSexb.NAN)
//		{
//			contentList.add("��");
//		}
//		else if (clientInfo.getScorporategender()==LOANConstant.LoanSexb.NV)
//		{
//			contentList.add("Ů");
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//		}
//		//ѧ��
//		contentList.add(clientInfo.getScorporatequalifications());
//		//����
//		contentList.add(clientInfo.getScorporateorigin());
//		//��Ҫ��������
//		contentList.add(clientInfo.getScorworkexperience());
//		//�ͻ����
//		//��ҵ����
//		if(clientInfo.getNcompanynature()==LOANConstant.Corporation.SHIYE)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//��ҵ����
//		if(clientInfo.getNcompanynature()==LOANConstant.Corporation.QIYE)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//��Ʒ
//		if(clientInfo.getMilitaryandciviliangoods()==LOANConstant.Armycorps.JUN)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//��Ʒ
//		if(clientInfo.getMilitaryandciviliangoods()==LOANConstant.Armycorps.MIN)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//���ж�����ҵ
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.GUOYOU)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//�������ι�˾
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.YOUXIAN)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//�ɷ����ޣ����У� 
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.GUFS)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//�ɷ����ޣ�δ���У�
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.GUFW)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//��е��ҵ
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.JXHY)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//���ز�����
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.FDCKF)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//����
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.QCH)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//�Ṥ
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.QG)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//����
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.DZ)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//��ҵ
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.SY)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//����
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.JZ)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//��ó
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.WM)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//ע���ʱ�
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getCaptial()));
//		//ʵ���ʱ�
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDpaidupcapital()));
//		//ʵ���ʱ�/ע���ʱ�
//		if(clientInfo.getCaptial()>0)
//		{
//			contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDpaidupcapital()/ clientInfo.getCaptial() * 100));
//		}
//		else
//		{
//			contentList.add("0.00");
//		}
//		//�ͻ���ҪͶ����1
//		contentList.add(clientInfo.getScustomerinvestors1());
//		//ʵ��Ͷ�ʶ�1
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDinvestmentamount1()));
//		//ռʵ���ʱ�1
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getOfthepaidupcapital1()));
//		//�ͻ���ҪͶ����2
//		contentList.add(clientInfo.getScustomerinvestors2());
//		//ʵ��Ͷ�ʶ�2
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDinvestmentamount2()));
//		//ռʵ���ʱ�2
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getOfthepaidupcapital2()));
//		//�ͻ���ҪͶ����3
//		contentList.add(clientInfo.getScustomerinvestors3());
//		//ʵ��Ͷ�ʶ�3
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDinvestmentamount3()));
//		//ռʵ���ʱ�3
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getOfthepaidupcapital3()));
//		//����Ͷ����?��
//		strTemp = String.valueOf(clientInfo.getTheremaininginvestors());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//��ɽ��
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDsharesamount()));
//		//��ҵ�ϼ����ܲ��Ż�������
//		contentList.add(LOANNameRef.getClientNameById(clientInfo.getParentCorpID()));
//		//ȫ���ӹ�˾?��
//		strTemp = String.valueOf(clientInfo.getNsubsidiary());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//�عɹ�˾?��
//		strTemp = String.valueOf(clientInfo.getNholdingcompany());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//�ιɹ�˾?��
//		strTemp = String.valueOf(clientInfo.getEquitycompany());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//������?��
//		strTemp = String.valueOf(clientInfo.getSubsidiaryplant());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//������֯�ṹ������Ҫ˵��������
//		contentList.add(clientInfo.getOrganizationalstructure());
//		//��ҵ��ʷ�ظ�
//		contentList.add(clientInfo.getQenterprisehistory());
//		//��Ҫ��������
//		contentList.add(clientInfo.getMaindepositarybank());
//		//��ҵ�Ļ��ص�
//		contentList.add(clientInfo.getCorporateculture());
//
//		log4j.info("----�ͻ����������ѯ���----");
//		/*
//		 * �����������
//		 *    ���񱨱����
//		 */
//		//��ѯ
//		String[] cycle = new CustomerfeedbackBean().queryCreditCycle(lCreditgradeID);
//		if (cycle!=null && cycle[0]!=null && cycle[1]!=null)
//		{
//			thisYear = Integer.parseInt(cycle[0]);
//			month = Integer.parseInt(cycle[1]);
//		}
//		int lastYear = thisYear - 1;
//		LoanFinanceanalyse info1 = loanFinanceitemdetailDao.findFinanceAnalyse(lClientID, String.valueOf(lastYear-1), "12");
//		LoanFinanceanalyse info2 = loanFinanceitemdetailDao.findFinanceAnalyse(lClientID, String.valueOf(lastYear), "12");
//		LoanFinanceanalyse info3 = loanFinanceitemdetailDao.findFinanceAnalyse(lClientID, String.valueOf(thisYear), String.valueOf(month));
//		//LoanFinanceanalyse info4 = loanFinanceitemdetailDao.findFinanceAnalyse(lClientID, String.valueOf(thisYear-2), "12");
//		Map map1 = loanFinanceitemdetailDao.findFinanceDetailList(info1.getId());
//		Map map2 = loanFinanceitemdetailDao.findFinanceDetailList(info2.getId());
//		Map map3 = loanFinanceitemdetailDao.findFinanceDetailList(info3.getId());
//		//Map map4 = loanFinanceitemdetailDao.findFinanceDetailList(info4.getId());
//		//��ѯEND
//		//?��?�£������
//		contentList.add(String.valueOf(lastYear-1));
//		contentList.add("12");
//		//?��?�£�ȥ��ͬ�ڣ�
//		contentList.add(String.valueOf(lastYear));
//		contentList.add("12");
//		//?��?�£����£�
//		contentList.add(String.valueOf(thisYear));
//		contentList.add(String.valueOf(month));
//		//�Ƿ���ƣ������
//		if(info1.getIsaudit()==1)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//�Ƿ���ƣ�ȥ��ͬ�ڣ�
//		if(info2.getIsaudit()==1)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//�Ƿ���ƣ����£�
//		if(info3.getIsaudit()==1)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//���񱨱����
//		for (i=1; i<itemID.length; i++)
//		{
//			//������
//			loanFinanceitemdetail = (LoanFinanceitemdetail)map1.get(itemID[i]+"");
//			dtemp1 = loanFinanceitemdetail!=null ? loanFinanceitemdetail.getAmount(): 0.00; 
//			contentList.add(DataFormat.formatDisabledAmount(dtemp1));
//			//����ͬ��
//			loanFinanceitemdetail = (LoanFinanceitemdetail)map2.get(itemID[i]+"");
//			dtemp2 = loanFinanceitemdetail!=null ? loanFinanceitemdetail.getAmount(): 0.00; 
//			contentList.add(DataFormat.formatDisabledAmount(dtemp2));
//			//����
//			loanFinanceitemdetail = (LoanFinanceitemdetail)map3.get(itemID[i]+"");
//			dtemp3 = (loanFinanceitemdetail==null ? 0.00 : loanFinanceitemdetail.getAmount()); 
//			contentList.add(DataFormat.formatDisabledAmount(dtemp3));
//			//�����������
//			contentList.add(DataFormat.formatDisabledAmount(dtemp3-dtemp2));
//			//˵��
//			contentList.add(loanFinanceitemdetail!=null ? loanFinanceitemdetail.getExplain():"");
//		}
//		//���񱨱�˵����
//		contentList.add(info3==null || info3.getExplain()==null ? "" : info3.getExplain());
//
//		/*
//		 * �����������
//		 *    Ӧ���˿��������
//		 */
//		//��ѯ
//		//��ѯEND
//		for (i=1; i<=3; i++)
//		{
//			rage = customerfeedbackDao.findReceiveaccountage(lCreditgradeID,i);
//			if(rage!=null)
//			{
//				//����
//				contentList.add(String.valueOf(rage.getAccountcount()>0?rage.getAccountcount():0));
//				zzhang += rage.getAccountcount();
//				//����Ԫ��
//				contentList.add(DataFormat.formatDisabledAmount(rage.getAmount()));
//				zamount += rage.getAmount();
//				//������%��
//				contentList.add(String.valueOf(rage.getScale()));
//				zscale += rage.getScale();
//				//Ƿ�λ˵��
//				contentList.add(rage.getExplain());
//			}
//			else
//			{
//				//����
//				contentList.add(NOCHARACTOR);
//				//����Ԫ��
//				contentList.add(NOCHARACTOR);
//				//������%��
//				contentList.add(NOCHARACTOR);
//				//Ƿ�λ˵��
//				contentList.add(NOCHARACTOR);
//			}
//		}
//		//�ܼƻ���
//		contentList.add(String.valueOf(zzhang>0?zzhang:0));
//		//�ܼƽ���Ԫ��
//		contentList.add(DataFormat.formatDisabledAmount(zamount));
//		//�ܼƱ�����%��
//		contentList.add(String.valueOf(zscale));
//		//Ӧ�տ�ǰ������λ��Ϣ
//		for (i=1; i<=5; i++)
//		{
//			sonaccount = customerfeedbackDao.findReceivefundsonaccount(lCreditgradeID,i);
//			if(sonaccount!=null)
//			{
//				//��λ����
//				contentList.add(sonaccount.getClientname());
//				//Ƿ�����Ԫ��
//				contentList.add(DataFormat.formatDisabledAmount(sonaccount.getAmount()));
//				rAmount += sonaccount.getAmount();
//				//������%��
//				contentList.add(String.valueOf(sonaccount.getScale()));
//				rScale += sonaccount.getScale();
//				//˵��
//				contentList.add(sonaccount.getExplain());
//			}
//			else
//			{
//				//��λ����
//				contentList.add(NOCHARACTOR);
//				//Ƿ�����Ԫ��
//				contentList.add(NOCHARACTOR);
//				//������%��
//				contentList.add(NOCHARACTOR);
//				//˵��
//				contentList.add(NOCHARACTOR);
//			}
//		}
//		//�ܼ�Ƿ�����Ԫ��
//		contentList.add(DataFormat.formatDisabledAmount(rAmount));
//		//�ܼƱ�����%��
//		contentList.add(String.valueOf(rScale));
//
//		log4j.info("----���������ѯ���----");
//		/*
//		 *  ���и�ծ���
//		 */
//		LoanBankliabilitiesdetail Bankli=null;
//		for (i=1; i<=29; i++)
//		{
//			Bankli = customerfeedbackDao.findBankliabilitiesdetail(lCreditgradeID, i);
//			if(Bankli!=null)
//			{
//				//����������
//				strTemp = Bankli.getLoanbankname();
//				if (strTemp!=null&&!"null".equals(strTemp))
//				{
//					contentList.add(strTemp);
//				}
//				else
//				{
//					contentList.add(NOCHARACTOR);
//				}
//				//ҵ�����
//				contentList.add(DataFormat.formatDisabledAmount(Bankli.getTransamount()));
//				//��ע
//				strTemp = Bankli.getComments();
//				if (strTemp!=null&&!"null".equals(strTemp))
//				{
//					contentList.add(strTemp);
//				}
//				else
//				{
//					contentList.add(NOCHARACTOR);
//				}
//			}
//			else
//			{
//				//����������
//				contentList.add(NOCHARACTOR);
//				//ҵ�����
//				contentList.add(NOCHARACTOR);
//				//��ע
//				contentList.add(NOCHARACTOR);
//			}
//		}
//		//�����Ŵ��Ǽ�ϵͳ��¼
//		einfo = customerfeedbackDao.query(lCreditgradeID);
//		if(einfo!=null&&einfo.getRecord()!=null&&!"null".equals(einfo.getRecord()))
//		{
//			contentList.add(einfo.getRecord());
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//		}
//		//������и�ծ����
//		coll=customerfeedbackDao.quetyExternalliabilities(lCreditgradeID);
//		einfo=customerfeedbackDao.queryLoan_assureoracceptsynopsis(lCreditgradeID);
//		if(lOperationType == 2)
//		{
//			if( coll != null && coll.size() > 0 )
//			{
//				iter = coll.iterator();
//				sbContent.setLength(0);
//				sbContent.append("<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=\"100%\"\r\n");
//				sbContent.append("style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:.75pt solid windowtext;\r\n");
//				sbContent.append("mso-border-insidev:.75pt solid windowtext'>\r\n");
//				sbContent.append("<tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>\r\n");
//				sbContent.append(" <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//				sbContent.append(" padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal style='mso-list:skip;text-autospace:none;vertical-align:\r\n");
//				sbContent.append(" bottom'><span lang=EN-US style='font-family:����_GB2312'><o:p>&nbsp;</o:p></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"8%\" valign=top style='width:8.3%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>����<span\r\n");
//				sbContent.append(" lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"29%\" valign=top style='width:29.7%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>��\r\n");
//				sbContent.append(" ��<span lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"16%\" valign=top style='width:16.74%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>���<span\r\n");
//				sbContent.append(" lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"12%\" valign=top style='width:12.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>������<span\r\n");
//				sbContent.append(" lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"20%\" valign=top style='width:20.58%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>���Ŀ�����<span\r\n");
//				sbContent.append(" lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append("</tr>");
//				i = 0;
//				while ( iter.hasNext() )
//				{
//					inforInfo = ( LoanExternalliabilities ) iter.next();
//					strTemp = "";
//					if(inforInfo.getTranstype() == 1)
//					{
//						strTemp = "����";
//					}
//					else if(inforInfo.getTranstype() == 2)
//					{
//						strTemp = "�ж�";
//					}
//					else
//					{
//						strTemp = "&nbsp;";
//					}
//					sbContent.append("<tr style='mso-yfti-irow:1'>\r\n");
//					sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//					sbContent.append("  solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//					sbContent.append("  none;vertical-align:bottom'><span style='font-family:����_GB2312'>");
//					sbContent.append(strTemp);
//					sbContent.append("<span\r\n");
//					sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//					sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//					sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//					sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  ����_GB2312'> ");
//					sbContent.append(String.valueOf(inforInfo.getStockcount()));
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  ����_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  ����_GB2312'> ");
//					sbContent.append(inforInfo.getPobject());
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  ����_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  ����_GB2312'> ");
//					sbContent.append(DataFormat.formatDisabledAmount(inforInfo.getBalance()));
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  ����_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  ����_GB2312'> ");
//					sbContent.append(DataFormat.formatDate(inforInfo.getEnddate()));
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  ����_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  ����_GB2312'> ");
//					sbContent.append(inforInfo.getPossibillity());
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  ����_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append(" </tr>\r\n" );
//					i++;
//				}
//				sbContent.append("</table>\r\n");
//				contentList.add(sbContent.toString());
//				sbContent.setLength(0);
//			}
//			else
//			{
//				sbContent.setLength(0);
//				sbContent.append("<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=\"100%\"\r\n");
//				sbContent.append(" style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid windowtext .75pt;\r\n");
//				sbContent.append(" mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:.75pt solid windowtext;\r\n");
//				sbContent.append(" mso-border-insidev:.75pt solid windowtext'>\r\n");
//				sbContent.append(" <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='mso-list:skip;text-autospace:none;vertical-align:\r\n");
//				sbContent.append("  bottom'><span lang=EN-US style='font-family:����_GB2312'><o:p>&nbsp;</o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" valign=top style='width:8.3%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>����<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" valign=top style='width:29.7%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>��\r\n");
//				sbContent.append("  ��<span lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" valign=top style='width:16.74%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>���<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" valign=top style='width:12.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>������<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" valign=top style='width:20.58%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>���Ŀ�����<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>");
//				sbContent.append("<tr style='mso-yfti-irow:1'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:����_GB2312'>����<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append(" <tr style='mso-yfti-irow:2'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;mso-border-bottom-alt:solid windowtext .5pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:����_GB2312'>�ж�<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append("</table>\r\n");
//				contentList.add(sbContent.toString());
//				sbContent.setLength(0);
//			}
//		}
//		//		��ӡ�����
//		else if(lOperationType == 1)
//		{
//			if( coll != null && coll.size() > 0 )
//			{
//				
//			}
//			else
//			{
//				sbContent.setLength(0);
//				sbContent.append("<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=\"100%\"\r\n");
//				sbContent.append(" style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid windowtext .75pt;\r\n");
//				sbContent.append(" mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:.75pt solid windowtext;\r\n");
//				sbContent.append(" mso-border-insidev:.75pt solid windowtext'>\r\n");
//				sbContent.append(" <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='mso-list:skip;text-autospace:none;vertical-align:\r\n");
//				sbContent.append("  bottom'><span lang=EN-US style='font-family:����_GB2312'><o:p>&nbsp;</o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" valign=top style='width:8.3%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>����<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" valign=top style='width:29.7%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>��\r\n");
//				sbContent.append("  ��<span lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" valign=top style='width:16.74%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>���<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" valign=top style='width:12.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>������<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" valign=top style='width:20.58%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:����_GB2312'>���Ŀ�����<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>");
//				sbContent.append("<tr style='mso-yfti-irow:1'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:����_GB2312'>����<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append(" <tr style='mso-yfti-irow:2'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;mso-border-bottom-alt:solid windowtext .5pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:����_GB2312'>�ж�<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  ����_GB2312'>��������</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  ����_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append("</table>\r\n");
//				contentList.add(sbContent.toString());
//				sbContent.setLength(0);
//			}
//		}
//		//������/�ж���ҵ���(�г���λ����Ӫ״�����ʽ�ʵ�������������ǰ����)
//		if(einfo!=null&&einfo.getSynopsis()!=null&&!"null".equals(einfo.getSynopsis()))
//		{
//			contentList.add(einfo.getSynopsis());
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//		}
//		groupinsidecontact=customerfeedbackDao.findGroupinsidecontact(lCreditgradeID,lClientID,Env.getSystemDate().toString().substring(0,4));
//		//��ҵ�ڼ��������ü�¼
//		contentList.add(groupinsidecontact.getGrouprecord()==null? "" : groupinsidecontact.getGrouprecord());
//		//�ڲ���˾�վ������
//		contentList.add(DataFormat.formatDisabledAmount(groupinsidecontact.getSettmeasure()));
//		//�վ������
//		contentList.add(DataFormat.formatDisabledAmount(groupinsidecontact.getBalance()));
//		//�����˾�����ü�ҵ��������¼��
//		contentList.add(groupinsidecontact.getOrgrecord()==null ? "" : groupinsidecontact.getOrgrecord());
//
//		log4j.info("----���и�ծ�����ѯ���----");
//		/*
//		 * ����	��Ӫ����
//		 */
//		Manageanalyse=customerfeedbackDao.findManageanalyse(lCreditgradeID);
//		if(Manageanalyse!=null){
//			//�ͻ���Ҫ��Ʒ�ͷ��񣬼���Ӫҵ�����빹��
//			contentList.add(Manageanalyse.getIncomeform()==null? "" : Manageanalyse.getIncomeform());
//			//�ͻ���Ҫ��Ʒ�ͷ������ҵ�ص�;������
//			contentList.add(Manageanalyse.getCompetecondition()==null ?"" :Manageanalyse.getCompetecondition());
//			//�ͻ���Ҫ��Ʒ�ͷ�����г���λ����Ʒ��������ͬҵ��ˮƽ�ȣ�
//			contentList.add(Manageanalyse.getMarketlocation()==null ?"" : Manageanalyse.getMarketlocation());
//			//��ҵ����Ҫ�������ּ���������
//			contentList.add(Manageanalyse.getElementarycondition()==null ? "" :Manageanalyse.getElementarycondition());
//			//�ͻ���Ӫ�����������������ߡ���ҵ���ߡ��ط����ߡ����ɡ�����֧�ֵȣ�
//			contentList.add(Manageanalyse.getAmbienceanalyse()==null ? "" :Manageanalyse.getAmbienceanalyse());
//			//�ͻ���Ӫ��ʩ���������豸ˮƽ���Ƚ���
//			contentList.add(Manageanalyse.getAdvanced()==null ? "" :Manageanalyse.getAdvanced());
//			//�ͻ��г���չ���������������������豸�����ʵ�
//			contentList.add(Manageanalyse.getStartrate()==null ? "" :Manageanalyse.getStartrate());
//			//��ҵ�ڲ�����ˮƽ��������������ṹ����Ӫ��ҵ�����ڲ����Ƶȣ�
//			contentList.add(Manageanalyse.getManagestandard()==null ? "" :Manageanalyse.getManagestandard());
//			//��ҵ��Ա���ɣ���ְ��Ա��������Ա���������䡢ѧ�����ɣ�
//			contentList.add(Manageanalyse.getConsist()==null ? "" :Manageanalyse.getConsist());
//			//��ҵ������Ԥ��������
//			contentList.add(Manageanalyse.getCompletecondition()==null ? "" :Manageanalyse.getCompletecondition());
//			//��ҵ��Ӫ���ٵ���Ҫ���⼰��Ӧ�Բ�
//			contentList.add(Manageanalyse.getSituation()==null ? "" :Manageanalyse.getSituation());
//			//��ҵ����Ⱦ�Ӫ�ƻ�������������ֹ��������ʱ��
//			contentList.add(Manageanalyse.getDesigncondition()==null ? "" :Manageanalyse.getDesigncondition());
//			//��ҵ�����귢չ�滮
//			contentList.add(Manageanalyse.getPlanning()==null ? "" :Manageanalyse.getPlanning());
//		}
//		else
//		{
//			//�ͻ���Ҫ��Ʒ�ͷ��񣬼���Ӫҵ�����빹��
//			contentList.add(NOCHARACTOR);
//			//�ͻ���Ҫ��Ʒ�ͷ������ҵ�ص�;������
//			contentList.add(NOCHARACTOR);
//			//�ͻ���Ҫ��Ʒ�ͷ�����г���λ����Ʒ��������ͬҵ��ˮƽ�ȣ�
//			contentList.add(NOCHARACTOR);
//			//��ҵ����Ҫ�������ּ���������
//			contentList.add(NOCHARACTOR);
//			//�ͻ���Ӫ�����������������ߡ���ҵ���ߡ��ط����ߡ����ɡ�����֧�ֵȣ�
//			contentList.add(NOCHARACTOR);
//			//�ͻ���Ӫ��ʩ���������豸ˮƽ���Ƚ���
//			contentList.add(NOCHARACTOR);
//			//�ͻ��г���չ���������������������豸�����ʵ�
//			contentList.add(NOCHARACTOR);
//			//��ҵ�ڲ�����ˮƽ��������������ṹ����Ӫ��ҵ�����ڲ����Ƶȣ�
//			contentList.add(NOCHARACTOR);
//			//��ҵ��Ա���ɣ���ְ��Ա��������Ա���������䡢ѧ�����ɣ�
//			contentList.add(NOCHARACTOR);
//			//��ҵ������Ԥ��������
//			contentList.add(NOCHARACTOR);
//			//��ҵ��Ӫ���ٵ���Ҫ���⼰��Ӧ�Բ�
//			contentList.add(NOCHARACTOR);
//			//��ҵ����Ⱦ�Ӫ�ƻ�������������ֹ��������ʱ��
//			contentList.add(NOCHARACTOR);
//			//��ҵ�����귢չ�滮
//			contentList.add(NOCHARACTOR);
//		}
//		log4j.info("----��Ӫ������ѯ���----");
//		/*
//		 * �ġ�	���õȼ�
//		 */
//		log4j.info("----��Ӫ������ѯ���----");
//		log4j.info("----���Ʋ�ѯ " + contentList.size() + " ��ֵ----");
//		log4j.info("-----׼��д���ļ�-----");
//		double subcount = 0.00;
//		double count = 0.00;
//		LoanCreditgradedetail loanCreditgradedetail1 = null;
//		LoanCreditgradedetail loanCreditgradedetail2 = null;
//		List list1 = loanCreditGradedetailDao.LoanCreditGradedetail(lClientID,lCreditgradeID);
//		List list2 = loanCreditGradedetailDao.queryLoanCreditGradedetail(lCreditgradeID);
//		for (i = 0; i<28; i++ )
//		{
//			if(list2!=null&&list2.size()>i)
//			{
//				loanCreditgradedetail2 = (LoanCreditgradedetail) list2.get(i);
//				contentList.add(DataFormat.formatDisabledAmount(loanCreditgradedetail2.getScore()));
//				if(list1!=null&&list1.size()>i)
//				{
//					loanCreditgradedetail1 = (LoanCreditgradedetail) list1.get(i);
//					contentList.add(DataFormat.formatDisabledAmount(
//							loanCreditgradedetail2.getScore()-loanCreditgradedetail1.getScore()));
//				}
//				else
//				{
//					contentList.add(DataFormat.formatDisabledAmount(loanCreditgradedetail2.getScore()-0));
//				}
//				contentList.add(loanCreditgradedetail2.getDiversityexplain());
//				contentList.add(loanCreditgradedetail2.getThatadjustment());
//				dtemp1 = loanCreditgradedetail2.getScore()*valuesrate[i];
//				subcount = subcount + dtemp1;
//				count = count + dtemp1;
//			}
//			else
//			{
//				contentList.add("0.00");
//				contentList.add("0.00");
//				contentList.add(NOCHARACTOR);
//				contentList.add(NOCHARACTOR);
//			}
//			if(i==5 || i==11 || i==14 || i==24 || i==27)
//			{
//
//				contentList.add(DataFormat.formatDisabledAmount(subcount));
//				subcount = 0;
//			}
//			if(i==24)
//			{
//				contentList.add(DataFormat.formatDisabledAmount(count));
//			}
//		}
//		if (count>0)
//		{
//			if (count>100)
//			{
//				count = 100;
//			}
//		}
//		else 
//		{
//			count = 0;
//		}
//		contentList.add(DataFormat.formatDisabledAmount(count));
//		count = count * 10;
//		contentList.add(DataFormat.formatDisabledAmount(count));
//		if (count>0)
//		{
//			switch(((int)count)/5)
//			{
//			case 0 :
//			case 1 :
//			case 2 :
//				strTemp = "C";
//				break;
//			case 3 :
//			case 4 :
//			case 5 :
//				strTemp = "B";
//				break;
//			case 6 :
//			case 7 :
//				strTemp = "BB";
//				break;
//			case 8 :
//			case 9 :
//				strTemp = "A-";
//				break;
//			case 10 :
//			case 11 :
//				strTemp = "A";
//				break;
//			case 12 :
//			case 13 :
//				strTemp = "A+";
//				break;
//			case 14 :
//			case 15 :
//			case 16 :
//				strTemp = "AA";
//				break;
//			default:
//				strTemp = count==85.00?"AA":"AAA";
//			}
//			contentList.add(strTemp);
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//		}
//		return contentList;
	}

	/**
	 * ���ɿͻ��������۱���
	 * @Create Date: 2008-12-08
	 * @author YuZhang
	 * @param contantList ����д����List
	 * @param strTempletName ģ������·��
	 * @param strExport �����ļ�����·��
	 * @return long �����ɹ���ʶ�� >0�ɹ� <0ʧ��
	 * @throws IException
	 * @exception Exception
	 */
	public long saveExportedReport(List contantList, String strTempletName, String strExport) throws Exception
	{
		long lResult = 1;
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		PrintWriter pw = null;
		String strContent = "";
		Iterator it = null;
		System.out.println("����");
		try
		{
			java.io.File f = new java.io.File(strTempletName);
			// get the old content
			if (strTempletName != null && strTempletName.length() > 0
					&& contantList!=null && contantList.size()>0 && f.exists())
			{
				f = new java.io.File(strExport);
				if (!f.exists())
				{
					f.createNewFile();
				}
				it = contantList.iterator();
				fr = new FileReader(strTempletName);
				br = new BufferedReader(fr);
				fw = new FileWriter(strExport);
				pw = new PrintWriter(fw);
				String record = new String();
				while ((record = br.readLine()) != null)
				{
					while (record.indexOf(TEMPLET_SEPERATOR)>0&&it.hasNext())
					{
						strContent = (String ) it.next();
						if (strContent==null||"".equals(strContent))
						{
							strContent = NOCHARACTOR;
						}
						record= record.substring(0,record.indexOf(TEMPLET_SEPERATOR)) + 
							strContent + record.substring(record.indexOf(TEMPLET_SEPERATOR) + 5);
					}
					while (record.indexOf(TEMPLET_SEPERATOR)>0)
					{
						record= record.substring(0,record.indexOf(TEMPLET_SEPERATOR)) + 
						NOCHARACTOR + record.substring(record.indexOf(TEMPLET_SEPERATOR) + 5);
					}
					pw.print(record + "\r\n");
				}
				br.close();
				fr.close();
				pw.close();
				fw.close();
			}
			else
			{
				lResult = -1;
			}
		}
		catch (Exception e)
		{
			lResult = -1;
			System.out.println(e.toString());
			throw e;
		}
		return lResult;
	}
}
