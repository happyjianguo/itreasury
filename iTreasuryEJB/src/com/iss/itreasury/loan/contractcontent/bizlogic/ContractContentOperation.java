/*
 * Created on 2003-10-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contractcontent.bizlogic;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.contractcontent.dao.*;
import com.iss.itreasury.loan.contractcontent.dataentity.*;

import java.util.*;

/**
 * @author hyzeng
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractContentOperation {
	private static Log4j log4j = null;

	public ContractContentOperation() {
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	 * 得到合同文本内容 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT表中的ID
	 * @param lPageNo
	 *            页号
	 * @return String 合同文本内容
	 * @exception Exception
	 */
	public String getContractContent(long lID, long lPageNo) throws IException,
			Exception {
		String sContent = "";
		try {
			ContractContentDao dao = new ContractContentDao();
			sContent = dao.getContractContent(lID, lPageNo);
		} catch (IException ie) {
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		}
		return sContent;
	}

	/**
	 * 查找复核条件的合同文本记录
	 * 
	 * @param queryInfo
	 * @return
	 * @throws IException
	 * @throws Exception
	 */
	public long getContractContentID(ContractContentQueryInfo queryInfo)
			throws IException, Exception {
		long lReturn = -1;
		try {
			ContractContentDao dao = new ContractContentDao();
			lReturn = dao.getContractContentID(queryInfo);
		} catch (IException ie) {
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		}
		return lReturn;
	}

	/**
	 * 查找复核条件的合同文本记录id
	 * 
	 * @param queryInfo
	 * @return
	 * @throws IException
	 * @throws Exception
	 */
	public Collection findContractContentInfos(
			ContractContentQueryInfo queryInfo) throws IException, Exception {
		Collection c = null;
		try {
			ContractContentDao dao = new ContractContentDao();
			c = dao.findContractContentInfos(queryInfo);
		} catch (IException ie) {
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		}
		return c;
	}

	/**
	 * 保存合同文本内容 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            ContractContentInfo合同文本内容(必须设置的内容有:ParentID,ContractID,ContractType,DocName)
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long saveContractContent(ContractContentInfo info) throws Exception {
		long lResult = -1;
		try {
			ContractContentDao dao = new ContractContentDao();
			lResult = dao.saveContractContent(info); // 在保存时，返回保存的ID ninghao
														// 03-11-10
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	 * 查找贷款的调查表 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            loanID
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long findLoanQuestionaryID(long loanID) throws Exception {

		long lResult = -1;
		try {
			ContractContentDao dao = new ContractContentDao();
			lResult = dao.findLoanQuestinoaryByLoanID(loanID);
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	 * 查找贷款的调查表 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            clientID
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long findLoanQuestionaryByClient(long lID) throws Exception {

		long lResult = -1;
		try {
			ContractContentDao dao = new ContractContentDao();
			lResult = dao.findLoanQuestinoaryByClient(lID);
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	 * 保存合同某一页的内容到相应的合同内容文件中 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT表中的ID
	 * @param lPageNo
	 *            页号
	 * @param strContent
	 *            合同内容
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long putContractContent(long lID, long lPageNo, String strContent)
			throws Exception {
		long lResult = -1;
		try {
			ContractContentDao dao = new ContractContentDao();
			lResult = dao.update(lID, lPageNo, strContent);
		} catch (IException ie) {
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	 * 组合出正确的地址格式 Create Date: 2003-10-15
	 * 
	 * @param strContent
	 *            合同内容
	 * @param strTemplate
	 *            模板内容
	 * @return String 返回完整的合同内容
	 * @exception Exception
	 */
	public String exportContract(long lContentID, long lContractTypeID)
			throws IException, Exception {
		String sContent = "";
		String sContentPath = "";
		String sTemplatePath = "";
		ContractContentInfo info = new ContractContentInfo();
		try {

			ContractContentDao dao = new ContractContentDao();
			info = dao.findByID(lContentID);

			sTemplatePath = AutoFileBean
					.getDestPath(Constant.DocType.LOANCONTRACTTEMPLATE);
			String sTemplateName = dao.getContractTemplate(lContentID, info
					.getContractTypeID());
			// 加为NULL的条件 modify by wjliu --2007/3/16
			// if (!sTemplateName.equals("") && !info.getDocName().equals(""))
			if (null != sTemplateName && !sTemplateName.equals("")
					&& null != info.getDocName() && !info.getDocName().equals("")) {
				sContent = dao.mergeContractContent(info.getDocName(),
						sTemplatePath + sTemplateName);
			} else {
				sContent = "";
			}

		} catch (IException ie) {
			log4j.error("3:" + ie.getMessage());
			throw new IException(ie.getMessage());
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return sContent;
	}

	/**
	 * 删除合同文本内容 Create Date: 2003-10-15
	 * 
	 * @param long
	 *            lContractID合同ID
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	/*
	 * public long deleteContractContent(long lContractID) throws Exception {
	 * long lResult = -1; try { ContractContentDao dao = new
	 * ContractContentDao(); lResult = dao.deleteContractContent(lContractID); }
	 * catch (Exception e) { log4j.error(e.toString()); throw new
	 * IException("Gen_E001"); } return lResult; }
	 */

	/**
	 * 新增 财务情况统计表 内容文件 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            CLIENT表中的ID
	 * @return 返回文本文件名称
	 * @exception Exception
	 */
	public String addClientContent(long lID) throws Exception {
		String strDocName = "";
		try {
			ContractContentDao dao = new ContractContentDao();
			strDocName = dao.addFinanceTJB(lID);
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strDocName;
	}

	public long addLoanQuestionary(long loanID) throws Exception {
		String strReturn = "";
		long ret = -1;
		ContractContentDao dao = new ContractContentDao();

		/* TOCONFIG—TODELETE */
		/*
		 * 产品化不再区分项目 ninh 2005-03-24
		 */

		// haier 项目判断用配置项//
		// if ( Config.GLOBAL.getProjectType() == Constant.ProjectType.HAIER )
		// {
		// strReturn=dao.addHaierLoanQuestionary(loanID);
		// }
		// else
		// {
		// strReturn=dao.addLoanQuestionary(loanID);
		// }
		/*
		 * if (Env.getProjectName().equals(Constant.ProjectName.HAIER)) {
		 * strReturn=dao.addHaierLoanQuestionary(loanID); } else { strReturn =
		 * dao.addLoanQuestionary(loanID); } //
		 */

		strReturn = dao.addLoanQuestionary(loanID);

		/* TOCONFIG—END */

		System.out.println("+++++++++" + strReturn);

		try {
			if (loanID == -1)
				return -1;
			ContractContentInfo info = new ContractContentInfo();
			info.setParentID(loanID);
			info.setContractTypeID(LOANConstant.ContractType.DKDCB);
			info.setDocName(strReturn);
			ret = dao.saveContractContent(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 根据合同文本 id 查找文本信息
	 * 
	 * @param contractContentID
	 * @return
	 * @throws Exception
	 */
	public ContractContentInfo findContractContentByID(long contractContentID)
			throws Exception {
		ContractContentInfo ccInfo = new ContractContentInfo();
		try {
			ContractContentDao dao = new ContractContentDao();
			ccInfo = dao.findByID(contractContentID);
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return ccInfo;
	}
}
