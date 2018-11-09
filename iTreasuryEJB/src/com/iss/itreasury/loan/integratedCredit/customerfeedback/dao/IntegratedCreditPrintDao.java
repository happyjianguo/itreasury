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
	public static String NOCHARACTOR = "　　　　"; 
	public static String SHOW_SELECTED = "■";
	public static String SHOW_DISSELECTED = "□";
	
	
	private static Log4j log4j = null;
 
	public IntegratedCreditPrintDao()
	{ 
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}


	/**
	 * 生成客户信用评价报告需填写字段内容 
	 * @Create Date: 2008-12-04
	 * @author YuZhang
	 * @param lClientID 客户ID
	 * @param lCreditgradeID 信用评价报告ID
	 * 
	 * @return String 信用评价报告打印内容
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
//		 * 封皮
//		 */
//		//查询
//		creditgrade=customerfeedbackDao.findByID(lCreditgradeID);
//		clientInfo = clientDao.findClientByID(lClientID);
//		//查询END
//		//调查报告编号
//		contentList.add(creditgrade.getCreditcode());
//		//评定日期：  年  月  日
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
//		//客户名称
//		contentList.add(clientInfo.getName()==null&&"".equals(clientInfo.getName())?NOCHARACTOR:clientInfo.getName());
//		//直接评价人
//		contentList.add(NameRef.getUserNameByID(creditgrade.getInputuserid()));
//		//评价审查人 评价认定人
//		contentList.add(NOCHARACTOR);
//		contentList.add(NOCHARACTOR);
//		/*
//		 * 一、	基本情况
//		 */
//		//客户详细地址
//		contentList.add(clientInfo.getAddress());
//		//邮政编码
//		contentList.add(clientInfo.getZipCode());
//		//法人营业执照号码
//		contentList.add(clientInfo.getLicenceCode());
//		//法人代码证号
//		contentList.add(clientInfo.getLegalPersonCode());
//		//贷款卡（证）号码
//		contentList.add(clientInfo.getLoanCardNo());
//		//贷款卡密码
//		contentList.add(clientInfo.getLoanCardPwd());
//		//主营业务
//		contentList.add(clientInfo.getSmainbusiness());
//		//企业的主营业务是否是集团支持发展的项目
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
//		//兼营业务
//		contentList.add(clientInfo.getSotherbusiness());
//		
//		double financialItem[] = new CustomerfeedbackBean().findLatestFinancialItem(clientInfo.getClientID());
//		//资产规模
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[0]) + "元\t");
//		//净资产
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[1]) + "元");
//		//销售收入
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[2]) + "元\t");
//		//净利润
//		contentList.add(DataFormat.formatDisabledAmount(financialItem[3]) + "元");
//		//法定代表人姓名
//		contentList.add(clientInfo.getStatutoryname());
//		//法定代表人其他职务
//		contentList.add(clientInfo.getStatutoryotherduties());
//		//法定代表人电话
//		contentList.add(clientInfo.getStatutorysphone());
//		//授权代理人姓名
//		contentList.add(clientInfo.getSauthorizedagentname());
//		//授权代理人其他职务
//		contentList.add(clientInfo.getSauotherduties());
//		//授权代理人电话
//		contentList.add(clientInfo.getNauphone());
//		//财务主管姓名
//		contentList.add(clientInfo.getStreasurername());
//		//财务主管其他职务
//		contentList.add(clientInfo.getStreasurerduty());
//		//财务主管电话
//		contentList.add(clientInfo.getNtreasurerphone());
//		//法人代表简历
//		//姓名
//		contentList.add(clientInfo.getLegalPerson());
//		//年龄
//		strTemp = String.valueOf(clientInfo.getNcorporateage());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//性别
//		if (clientInfo.getScorporategender()==LOANConstant.LoanSexb.NAN)
//		{
//			contentList.add("男");
//		}
//		else if (clientInfo.getScorporategender()==LOANConstant.LoanSexb.NV)
//		{
//			contentList.add("女");
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//		}
//		//学历
//		contentList.add(clientInfo.getScorporatequalifications());
//		//籍贯
//		contentList.add(clientInfo.getScorporateorigin());
//		//主要工作经历
//		contentList.add(clientInfo.getScorworkexperience());
//		//客户类别
//		//事业法人
//		if(clientInfo.getNcompanynature()==LOANConstant.Corporation.SHIYE)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//企业法人
//		if(clientInfo.getNcompanynature()==LOANConstant.Corporation.QIYE)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//军品
//		if(clientInfo.getMilitaryandciviliangoods()==LOANConstant.Armycorps.JUN)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//民品
//		if(clientInfo.getMilitaryandciviliangoods()==LOANConstant.Armycorps.MIN)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//国有独资企业
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.GUOYOU)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//有限责任公司
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.YOUXIAN)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//股份有限（上市） 
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.GUFS)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//股份有限（未上市）
//		if(clientInfo.getNcompanynature()==LOANConstant.Compang.GUFW)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//机械行业
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.JXHY)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//房地产开发
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.FDCKF)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//汽车
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.QCH)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//轻工
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.QG)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//电子
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.DZ)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//商业
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.SY)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//建筑
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.JZ)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//外贸
//		if(clientInfo.getNindustrytypeidx() ==LOANConstant.Calling.WM)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//注册资本
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getCaptial()));
//		//实收资本
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDpaidupcapital()));
//		//实收资本/注册资本
//		if(clientInfo.getCaptial()>0)
//		{
//			contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDpaidupcapital()/ clientInfo.getCaptial() * 100));
//		}
//		else
//		{
//			contentList.add("0.00");
//		}
//		//客户主要投资人1
//		contentList.add(clientInfo.getScustomerinvestors1());
//		//实际投资额1
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDinvestmentamount1()));
//		//占实收资本1
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getOfthepaidupcapital1()));
//		//客户主要投资人2
//		contentList.add(clientInfo.getScustomerinvestors2());
//		//实际投资额2
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDinvestmentamount2()));
//		//占实收资本2
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getOfthepaidupcapital2()));
//		//客户主要投资人3
//		contentList.add(clientInfo.getScustomerinvestors3());
//		//实际投资额3
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDinvestmentamount3()));
//		//占实收资本3
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getOfthepaidupcapital3()));
//		//其余投资人?个
//		strTemp = String.valueOf(clientInfo.getTheremaininginvestors());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//入股金额
//		contentList.add(DataFormat.formatDisabledAmount(clientInfo.getDsharesamount()));
//		//企业上级主管部门或隶属于
//		contentList.add(LOANNameRef.getClientNameById(clientInfo.getParentCorpID()));
//		//全资子公司?个
//		strTemp = String.valueOf(clientInfo.getNsubsidiary());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//控股公司?个
//		strTemp = String.valueOf(clientInfo.getNholdingcompany());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//参股公司?个
//		strTemp = String.valueOf(clientInfo.getEquitycompany());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//附属厂?个
//		strTemp = String.valueOf(clientInfo.getSubsidiaryplant());
//		contentList.add(strTemp.equals("-1") ? NOCHARACTOR : strTemp);
//		strTemp = "";
//		//关于组织结构其他需要说明的事项
//		contentList.add(clientInfo.getOrganizationalstructure());
//		//企业历史沿革
//		contentList.add(clientInfo.getQenterprisehistory());
//		//主要开户银行
//		contentList.add(clientInfo.getMaindepositarybank());
//		//企业文化特点
//		contentList.add(clientInfo.getCorporateculture());
//
//		log4j.info("----客户基本情况查询完毕----");
//		/*
//		 * 二、财务分析
//		 *    财务报表分析
//		 */
//		//查询
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
//		//查询END
//		//?年?月（年初余额）
//		contentList.add(String.valueOf(lastYear-1));
//		contentList.add("12");
//		//?年?月（去年同期）
//		contentList.add(String.valueOf(lastYear));
//		contentList.add("12");
//		//?年?月（本月）
//		contentList.add(String.valueOf(thisYear));
//		contentList.add(String.valueOf(month));
//		//是否审计（年初余额）
//		if(info1.getIsaudit()==1)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//是否审计（去年同期）
//		if(info2.getIsaudit()==1)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//是否审计（本月）
//		if(info3.getIsaudit()==1)
//		{
//			contentList.add(SHOW_SELECTED);
//		}
//		else
//		{
//			contentList.add(SHOW_DISSELECTED);
//		}
//		//财务报表分析
//		for (i=1; i<itemID.length; i++)
//		{
//			//年初余额
//			loanFinanceitemdetail = (LoanFinanceitemdetail)map1.get(itemID[i]+"");
//			dtemp1 = loanFinanceitemdetail!=null ? loanFinanceitemdetail.getAmount(): 0.00; 
//			contentList.add(DataFormat.formatDisabledAmount(dtemp1));
//			//上年同期
//			loanFinanceitemdetail = (LoanFinanceitemdetail)map2.get(itemID[i]+"");
//			dtemp2 = loanFinanceitemdetail!=null ? loanFinanceitemdetail.getAmount(): 0.00; 
//			contentList.add(DataFormat.formatDisabledAmount(dtemp2));
//			//当月
//			loanFinanceitemdetail = (LoanFinanceitemdetail)map3.get(itemID[i]+"");
//			dtemp3 = (loanFinanceitemdetail==null ? 0.00 : loanFinanceitemdetail.getAmount()); 
//			contentList.add(DataFormat.formatDisabledAmount(dtemp3));
//			//比年初余额差异
//			contentList.add(DataFormat.formatDisabledAmount(dtemp3-dtemp2));
//			//说明
//			contentList.add(loanFinanceitemdetail!=null ? loanFinanceitemdetail.getExplain():"");
//		}
//		//财务报表说明：
//		contentList.add(info3==null || info3.getExplain()==null ? "" : info3.getExplain());
//
//		/*
//		 * 二、财务分析
//		 *    应收账款帐龄分析
//		 */
//		//查询
//		//查询END
//		for (i=1; i<=3; i++)
//		{
//			rage = customerfeedbackDao.findReceiveaccountage(lCreditgradeID,i);
//			if(rage!=null)
//			{
//				//户数
//				contentList.add(String.valueOf(rage.getAccountcount()>0?rage.getAccountcount():0));
//				zzhang += rage.getAccountcount();
//				//金额（万元）
//				contentList.add(DataFormat.formatDisabledAmount(rage.getAmount()));
//				zamount += rage.getAmount();
//				//比例（%）
//				contentList.add(String.valueOf(rage.getScale()));
//				zscale += rage.getScale();
//				//欠款单位说明
//				contentList.add(rage.getExplain());
//			}
//			else
//			{
//				//户数
//				contentList.add(NOCHARACTOR);
//				//金额（万元）
//				contentList.add(NOCHARACTOR);
//				//比例（%）
//				contentList.add(NOCHARACTOR);
//				//欠款单位说明
//				contentList.add(NOCHARACTOR);
//			}
//		}
//		//总计户数
//		contentList.add(String.valueOf(zzhang>0?zzhang:0));
//		//总计金额（万元）
//		contentList.add(DataFormat.formatDisabledAmount(zamount));
//		//总计比例（%）
//		contentList.add(String.valueOf(zscale));
//		//应收款前五名单位信息
//		for (i=1; i<=5; i++)
//		{
//			sonaccount = customerfeedbackDao.findReceivefundsonaccount(lCreditgradeID,i);
//			if(sonaccount!=null)
//			{
//				//单位名称
//				contentList.add(sonaccount.getClientname());
//				//欠款金额（万元）
//				contentList.add(DataFormat.formatDisabledAmount(sonaccount.getAmount()));
//				rAmount += sonaccount.getAmount();
//				//比例（%）
//				contentList.add(String.valueOf(sonaccount.getScale()));
//				rScale += sonaccount.getScale();
//				//说明
//				contentList.add(sonaccount.getExplain());
//			}
//			else
//			{
//				//单位名称
//				contentList.add(NOCHARACTOR);
//				//欠款金额（万元）
//				contentList.add(NOCHARACTOR);
//				//比例（%）
//				contentList.add(NOCHARACTOR);
//				//说明
//				contentList.add(NOCHARACTOR);
//			}
//		}
//		//总计欠款金额（万元）
//		contentList.add(DataFormat.formatDisabledAmount(rAmount));
//		//总计比例（%）
//		contentList.add(String.valueOf(rScale));
//
//		log4j.info("----财务分析查询完毕----");
//		/*
//		 *  银行负债情况
//		 */
//		LoanBankliabilitiesdetail Bankli=null;
//		for (i=1; i<=29; i++)
//		{
//			Bankli = customerfeedbackDao.findBankliabilitiesdetail(lCreditgradeID, i);
//			if(Bankli!=null)
//			{
//				//贷款行名称
//				strTemp = Bankli.getLoanbankname();
//				if (strTemp!=null&&!"null".equals(strTemp))
//				{
//					contentList.add(strTemp);
//				}
//				else
//				{
//					contentList.add(NOCHARACTOR);
//				}
//				//业务余额
//				contentList.add(DataFormat.formatDisabledAmount(Bankli.getTransamount()));
//				//备注
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
//				//贷款行名称
//				contentList.add(NOCHARACTOR);
//				//业务余额
//				contentList.add(NOCHARACTOR);
//				//备注
//				contentList.add(NOCHARACTOR);
//			}
//		}
//		//银行信贷登记系统记录
//		einfo = customerfeedbackDao.query(lCreditgradeID);
//		if(einfo!=null&&einfo.getRecord()!=null&&!"null".equals(einfo.getRecord()))
//		{
//			contentList.add(einfo.getRecord());
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//		}
//		//对外或有负债分析
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
//				sbContent.append(" bottom'><span lang=EN-US style='font-family:楷体_GB2312'><o:p>&nbsp;</o:p></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"8%\" valign=top style='width:8.3%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>笔数<span\r\n");
//				sbContent.append(" lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"29%\" valign=top style='width:29.7%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>对\r\n");
//				sbContent.append(" 象<span lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"16%\" valign=top style='width:16.74%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>余额<span\r\n");
//				sbContent.append(" lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"12%\" valign=top style='width:12.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>到期日<span\r\n");
//				sbContent.append(" lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append(" </td>\r\n");
//				sbContent.append(" <td width=\"20%\" valign=top style='width:20.58%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append(" border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append(" solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append(" <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append(" text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>垫款的可能性<span\r\n");
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
//						strTemp = "担保";
//					}
//					else if(inforInfo.getTranstype() == 2)
//					{
//						strTemp = "承兑";
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
//					sbContent.append("  none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>");
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
//					sbContent.append("  楷体_GB2312'> ");
//					sbContent.append(String.valueOf(inforInfo.getStockcount()));
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  楷体_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  楷体_GB2312'> ");
//					sbContent.append(inforInfo.getPobject());
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  楷体_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  楷体_GB2312'> ");
//					sbContent.append(DataFormat.formatDisabledAmount(inforInfo.getBalance()));
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  楷体_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  楷体_GB2312'> ");
//					sbContent.append(DataFormat.formatDate(inforInfo.getEnddate()));
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  楷体_GB2312'><o:p></o:p></span></u></p>\r\n");
//					sbContent.append("  </td>\r\n");
//					sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//					sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//					sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//					sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//					sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//					sbContent.append("  vertical-align:bottom'><u><span lang=EN-US style='mso-fareast-font-family:\r\n");
//					sbContent.append("  楷体_GB2312'> ");
//					sbContent.append(inforInfo.getPossibillity());
//					sbContent.append(" </span></u><u><span lang=EN-US style='font-family:\r\n");
//					sbContent.append("  楷体_GB2312'><o:p></o:p></span></u></p>\r\n");
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
//				sbContent.append("  bottom'><span lang=EN-US style='font-family:楷体_GB2312'><o:p>&nbsp;</o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" valign=top style='width:8.3%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>笔数<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" valign=top style='width:29.7%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>对\r\n");
//				sbContent.append("  象<span lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" valign=top style='width:16.74%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>余额<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" valign=top style='width:12.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>到期日<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" valign=top style='width:20.58%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>垫款的可能性<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>");
//				sbContent.append("<tr style='mso-yfti-irow:1'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>担保<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append(" <tr style='mso-yfti-irow:2'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;mso-border-bottom-alt:solid windowtext .5pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>承兑<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append("</table>\r\n");
//				contentList.add(sbContent.toString());
//				sbContent.setLength(0);
//			}
//		}
//		//		打印待解决
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
//				sbContent.append("  bottom'><span lang=EN-US style='font-family:楷体_GB2312'><o:p>&nbsp;</o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" valign=top style='width:8.3%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>笔数<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" valign=top style='width:29.7%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>对\r\n");
//				sbContent.append("  象<span lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" valign=top style='width:16.74%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>余额<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" valign=top style='width:12.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>到期日<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" valign=top style='width:20.58%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-left:none;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;mso-list:skip;\r\n");
//				sbContent.append("  text-autospace:none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>垫款的可能性<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>");
//				sbContent.append("<tr style='mso-yfti-irow:1'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  border-bottom:none;mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-right-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>担保<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append(" <tr style='mso-yfti-irow:2'>\r\n");
//				sbContent.append("  <td width=\"11%\" valign=top style='width:11.84%;border:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;mso-border-bottom-alt:solid windowtext .5pt;\r\n");
//				sbContent.append("  padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal style='margin-right:-22.4pt;mso-list:skip;text-autospace:\r\n");
//				sbContent.append("  none;vertical-align:bottom'><span style='font-family:楷体_GB2312'>承兑<span\r\n");
//				sbContent.append("  lang=EN-US><o:p></o:p></span></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"8%\" style='width:8.3%;border-top:none;border-left:none;border-bottom:\r\n");
//				sbContent.append("  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;mso-border-alt:\r\n");
//				sbContent.append("  solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"29%\" style='width:29.7%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"16%\" style='width:16.74%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=right style='text-align:right;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"12%\" style='width:12.84%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append("  <td width=\"20%\" style='width:20.58%;border-top:none;border-left:none;\r\n");
//				sbContent.append("  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n");
//				sbContent.append("  mso-border-top-alt:solid windowtext .75pt;mso-border-left-alt:solid windowtext .75pt;\r\n");
//				sbContent.append("  mso-border-alt:solid windowtext .75pt;padding:0cm 5.4pt 0cm 5.4pt'>\r\n");
//				sbContent.append("  <p class=MsoNormal align=center style='text-align:center;text-autospace:none;\r\n");
//				sbContent.append("  vertical-align:bottom'><span lang=EN-US style='mso-fareast-font-family:\r\n");
//				sbContent.append("  楷体_GB2312'>　　　　</span><span lang=EN-US style='font-family:\r\n");
//				sbContent.append("  楷体_GB2312'><o:p></o:p></span></p>\r\n");
//				sbContent.append("  </td>\r\n");
//				sbContent.append(" </tr>\r\n");
//				sbContent.append("</table>\r\n");
//				contentList.add(sbContent.toString());
//				sbContent.setLength(0);
//			}
//		}
//		//被担保/承兑企业简况(市场地位，经营状况，资金实力，资信情况，前景等)
//		if(einfo!=null&&einfo.getSynopsis()!=null&&!"null".equals(einfo.getSynopsis()))
//		{
//			contentList.add(einfo.getSynopsis());
//		}
//		else
//		{
//			contentList.add(NOCHARACTOR);
//		}
//		groupinsidecontact=customerfeedbackDao.findGroupinsidecontact(lCreditgradeID,lClientID,Env.getSystemDate().toString().substring(0,4));
//		//企业在集团内信用记录
//		contentList.add(groupinsidecontact.getGrouprecord()==null? "" : groupinsidecontact.getGrouprecord());
//		//在财务公司日均结算存款：
//		contentList.add(DataFormat.formatDisabledAmount(groupinsidecontact.getSettmeasure()));
//		//日均存款余额：
//		contentList.add(DataFormat.formatDisabledAmount(groupinsidecontact.getBalance()));
//		//与财务公司的信用及业务往来记录：
//		contentList.add(groupinsidecontact.getOrgrecord()==null ? "" : groupinsidecontact.getOrgrecord());
//
//		log4j.info("----银行负债情况查询完毕----");
//		/*
//		 * 三、	经营分析
//		 */
//		Manageanalyse=customerfeedbackDao.findManageanalyse(lCreditgradeID);
//		if(Manageanalyse!=null){
//			//客户主要产品和服务，及主营业务收入构成
//			contentList.add(Manageanalyse.getIncomeform()==null? "" : Manageanalyse.getIncomeform());
//			//客户主要产品和服务的行业特点和竞争情况
//			contentList.add(Manageanalyse.getCompetecondition()==null ?"" :Manageanalyse.getCompetecondition());
//			//客户主要产品和服务的市场定位（产品质量、在同业中水平等）
//			contentList.add(Manageanalyse.getMarketlocation()==null ?"" : Manageanalyse.getMarketlocation());
//			//行业内主要竞争对手及其基本情况
//			contentList.add(Manageanalyse.getElementarycondition()==null ? "" :Manageanalyse.getElementarycondition());
//			//客户经营环境分析（国家政策、产业政策、地方政策、法律、集团支持等）
//			contentList.add(Manageanalyse.getAmbienceanalyse()==null ? "" :Manageanalyse.getAmbienceanalyse());
//			//客户经营设施、技术、设备水平的先进性
//			contentList.add(Manageanalyse.getAdvanced()==null ? "" :Manageanalyse.getAdvanced());
//			//客户市场拓展能力，销售渠道、现有设备开工率等
//			contentList.add(Manageanalyse.getStartrate()==null ? "" :Manageanalyse.getStartrate());
//			//企业内部管理水平（包括法人治理结构、经营层业绩、内部机制等）
//			contentList.add(Manageanalyse.getManagestandard()==null ? "" :Manageanalyse.getManagestandard());
//			//企业人员构成（在职人员、离退人员人数，年龄、学历构成）
//			contentList.add(Manageanalyse.getConsist()==null ? "" :Manageanalyse.getConsist());
//			//企业近三年预算完成情况
//			contentList.add(Manageanalyse.getCompletecondition()==null ? "" :Manageanalyse.getCompletecondition());
//			//企业经营面临的主要问题及相应对策
//			contentList.add(Manageanalyse.getSituation()==null ? "" :Manageanalyse.getSituation());
//			//企业本年度经营计划及完成情况（截止申请授信时）
//			contentList.add(Manageanalyse.getDesigncondition()==null ? "" :Manageanalyse.getDesigncondition());
//			//企业近三年发展规划
//			contentList.add(Manageanalyse.getPlanning()==null ? "" :Manageanalyse.getPlanning());
//		}
//		else
//		{
//			//客户主要产品和服务，及主营业务收入构成
//			contentList.add(NOCHARACTOR);
//			//客户主要产品和服务的行业特点和竞争情况
//			contentList.add(NOCHARACTOR);
//			//客户主要产品和服务的市场定位（产品质量、在同业中水平等）
//			contentList.add(NOCHARACTOR);
//			//行业内主要竞争对手及其基本情况
//			contentList.add(NOCHARACTOR);
//			//客户经营环境分析（国家政策、产业政策、地方政策、法律、集团支持等）
//			contentList.add(NOCHARACTOR);
//			//客户经营设施、技术、设备水平的先进性
//			contentList.add(NOCHARACTOR);
//			//客户市场拓展能力，销售渠道、现有设备开工率等
//			contentList.add(NOCHARACTOR);
//			//企业内部管理水平（包括法人治理结构、经营层业绩、内部机制等）
//			contentList.add(NOCHARACTOR);
//			//企业人员构成（在职人员、离退人员人数，年龄、学历构成）
//			contentList.add(NOCHARACTOR);
//			//企业近三年预算完成情况
//			contentList.add(NOCHARACTOR);
//			//企业经营面临的主要问题及相应对策
//			contentList.add(NOCHARACTOR);
//			//企业本年度经营计划及完成情况（截止申请授信时）
//			contentList.add(NOCHARACTOR);
//			//企业近三年发展规划
//			contentList.add(NOCHARACTOR);
//		}
//		log4j.info("----经营分析查询完毕----");
//		/*
//		 * 四、	信用等级
//		 */
//		log4j.info("----经营分析查询完毕----");
//		log4j.info("----共计查询 " + contentList.size() + " 个值----");
//		log4j.info("-----准备写入文件-----");
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
	 * 生成客户信用评价报告
	 * @Create Date: 2008-12-08
	 * @author YuZhang
	 * @param contantList 需填写内容List
	 * @param strTempletName 模板完整路径
	 * @param strExport 生成文件完整路径
	 * @return long 操作成功标识符 >0成功 <0失败
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
		System.out.println("进入");
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
