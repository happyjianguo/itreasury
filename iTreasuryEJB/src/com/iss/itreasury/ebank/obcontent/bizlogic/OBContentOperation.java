/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obcontent.bizlogic;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.obcontent.dao.*;
import com.iss.itreasury.ebank.obcontent.dataentity.*;
import java.util.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBContentOperation
{

	private static Log4j log4j = null;

	public OBContentOperation()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	* 得到合同文本内容
	* Create Date: 2003-10-15
	* @param lID LOAN_CONTRACTCONTENT表中的ID
	* @param lPageNo 页号
	* @return String 合同文本内容
	* @exception Exception
	*/
	public String getContractContent(long lID, long lPageNo) throws IException, Exception
	{
		String sContent = "";
		try
		{
			OBContentDao dao = new OBContentDao();
			sContent = dao.getContent(lID, lPageNo);
		}
		catch (IException ie)
		{
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		}
		return sContent;
	}

	/**
	* 保存合同文本内容
	* Create Date: 2003-10-15
	* @param lID ContractContentInfo合同文本内容(必须设置的内容有:ParentID,ContractID,ContractType,DocName)
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long saveContractContent(OBContentInfo info) throws Exception
	{
		long lResult = -1;
		try
		{
			OBContentDao dao = new OBContentDao();
			lResult = dao.saveContent(info); //在保存时，返回保存的ID   ninghao  03-11-10
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	* 查找贷款的调查表
	* Create Date: 2003-10-15
	* @param lID loanID 
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long findLoanQuestionaryID(long loanID) throws Exception
	{

		long lResult = -1;
		try
		{
			OBContentDao dao = new OBContentDao();
			lResult = dao.findLoanQuestinoaryByLoanID(loanID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}
	/**
	* 查找贷款的调查表
	* Create Date: 2003-10-15
	* @param lID clientID 
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long findLoanQuestionaryByClient(long lID) throws Exception
	{

		long lResult = -1;
		try
		{
			OBContentDao dao = new OBContentDao();
			lResult = dao.findLoanQuestinoaryByClient(lID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}
	/**
	* 保存合同某一页的内容到相应的合同内容文件中
	* Create Date: 2003-10-15
	* @param lID LOAN_OBContent表中的ID
	* @param lPageNo 页号
	* @param strContent 合同内容
	* @return long 大于0表示成功，小于等于0表示失败 
	* @exception Exception
	*/
	public long putOBContent(long lID, long lPageNo, String strContent) throws Exception
	{
		long lResult = -1;
		try
		{
			OBContentDao dao = new OBContentDao();
			lResult = dao.update(lID, lPageNo, strContent);
		}
		catch (IException ie)
		{
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	* 组合出正确的地址格式
	* Create Date: 2003-10-15
	* @param  strContent 合同内容
	* @param  strTemplate 模板内容
	* @return String   返回完整的合同内容
	* @exception Exception
	*/
	public String exportContract(long lContentID, long lContractTypeID) throws IException,Exception
	{
		String sContent = "";
		String sContentPath = "";
		String sTemplatePath = "";
		OBContentInfo info = new OBContentInfo();
		try
		{
			OBContentDao dao = new OBContentDao();
			info = dao.findByID(lContentID);

			sTemplatePath = AutoFileBean.getDestPath(Constant.DocType.LOANCONTRACTTEMPLATE);
			String sTemplateName = dao.getTemplate(lContentID, info.getContractTypeID());

			if (!sTemplateName.equals("") && !info.getDocName().equals(""))
			{
				sContent = dao.mergeContent(info.getDocName(), sTemplatePath + sTemplateName);
			}
			else
			{
				sContent = "";
			}
		}
		catch (IException ie)
		{
			log4j.error("3:"+ie.getMessage());
			throw new IException(ie.getMessage());
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return sContent;
	}

	/**
	* 删除合同文本内容
	* Create Date: 2003-10-15
	* @param long lContractID合同ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	/*
	public long deleteOBContent(long lContractID) throws Exception
	{
		long lResult = -1;
		try
		{
			OBContentDao dao = new OBContentDao();
			lResult = dao.deleteOBContent(lContractID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}*/

	/**
	* 新增 财务情况统计表 内容文件
	* Create Date: 2003-10-15
	* @param lID    CLIENT表中的ID
	* @return 返回文本文件名称  
	* @exception Exception
	*/
	public String addClientContent(long lID) throws Exception
	{
		String strDocName = "";
		try
		{
			OBContentDao dao = new OBContentDao();
			strDocName = dao.addFinanceTJB(lID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strDocName;
	}

	public long addLoanQuestionary(long loanID) throws Exception
	{
		String strReturn = "";
		long ret = -1;
		OBContentDao dao = new OBContentDao();

		strReturn = dao.addLoanQuestionary(loanID);
		System.out.println("+++++++++" + strReturn);

		try
		{
			if (loanID == -1)
				return -1;
			OBContentInfo info = new OBContentInfo();
			info.setParentID(loanID);
			info.setContractTypeID(LOANConstant.ContractType.DKDCB);
			info.setDocName(strReturn);
			ret = dao.saveContent(info);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

}
