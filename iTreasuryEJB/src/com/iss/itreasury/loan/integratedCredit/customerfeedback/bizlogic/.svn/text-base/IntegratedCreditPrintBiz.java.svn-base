package com.iss.itreasury.loan.integratedCredit.customerfeedback.bizlogic;

import java.util.List;

import com.iss.itreasury.loan.integratedCredit.customerfeedback.dao.IntegratedCreditPrintDao;
import com.iss.itreasury.util.AutoFileBean;
import com.iss.itreasury.util.Constant;

public class IntegratedCreditPrintBiz {

	/**
	 * 组合出客户信用评价报告打印内容 
	 * @Create Date: 2008-12-04
	 * @author YuZhang
	 * @param long lClientID 客户ID
	 * @param long lCreditgradeID 信用评价报告ID
	 * @param long lOperationType 操作类型，1打印2导出
	 * @return long 信用评价报告导出操作标识 >0成功 <0失败
	 * @throws Exception
	 * @exception Exception
	 */
	public long exportReport(long lClientID,long lCreditgradeID,long lOperationType) throws Exception
	{
		long lResult = -1;
		List listContentValues = null;
		IntegratedCreditPrintDao dao = new IntegratedCreditPrintDao();
		listContentValues = dao.makeContent(lClientID, lCreditgradeID, lOperationType);
		String sTemplatePath = AutoFileBean.getDestPath(Constant.DocType.LOANCONTRACTTEMPLATE);
		String sTemplateName = "";
		if(lOperationType==1)
		{
			sTemplateName = "asifcKHXYPJBG.txt";
		}
		else if(lOperationType==2)
		{
			sTemplateName = "asifcKHXYPJBGDOC.txt";
		}
		
		lResult = dao.saveExportedReport(listContentValues,sTemplatePath + sTemplateName, sTemplatePath + "客户信用评价报告.doc");
		
		return lResult;
	}

}
