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
	 * �õ���ͬ�ı����� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT���е�ID
	 * @param lPageNo
	 *            ҳ��
	 * @return String ��ͬ�ı�����
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
	 * ���Ҹ��������ĺ�ͬ�ı���¼
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
	 * ���Ҹ��������ĺ�ͬ�ı���¼id
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
	 * �����ͬ�ı����� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            ContractContentInfo��ͬ�ı�����(�������õ�������:ParentID,ContractID,ContractType,DocName)
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	 * @exception Exception
	 */
	public long saveContractContent(ContractContentInfo info) throws Exception {
		long lResult = -1;
		try {
			ContractContentDao dao = new ContractContentDao();
			lResult = dao.saveContractContent(info); // �ڱ���ʱ�����ر����ID ninghao
														// 03-11-10
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	 * ���Ҵ���ĵ���� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            loanID
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
	 * ���Ҵ���ĵ���� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            clientID
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
	 * �����ͬĳһҳ�����ݵ���Ӧ�ĺ�ͬ�����ļ��� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT���е�ID
	 * @param lPageNo
	 *            ҳ��
	 * @param strContent
	 *            ��ͬ����
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
	 * ��ϳ���ȷ�ĵ�ַ��ʽ Create Date: 2003-10-15
	 * 
	 * @param strContent
	 *            ��ͬ����
	 * @param strTemplate
	 *            ģ������
	 * @return String ���������ĺ�ͬ����
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
			// ��ΪNULL������ modify by wjliu --2007/3/16
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
	 * ɾ����ͬ�ı����� Create Date: 2003-10-15
	 * 
	 * @param long
	 *            lContractID��ͬID
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
	 * ���� �������ͳ�Ʊ� �����ļ� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            CLIENT���е�ID
	 * @return �����ı��ļ�����
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

		/* TOCONFIG��TODELETE */
		/*
		 * ��Ʒ������������Ŀ ninh 2005-03-24
		 */

		// haier ��Ŀ�ж���������//
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

		/* TOCONFIG��END */

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
	 * ���ݺ�ͬ�ı� id �����ı���Ϣ
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
