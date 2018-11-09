/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obcontent.dao;


import java.sql.*;
import java.util.*;
import java.io.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.extendapply.dataentity.ExtendApplyInfo;
import com.iss.itreasury.ebank.obcontent.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.util.LOANEnv;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.dataentity.AutoFileInfo;
import com.iss.itreasury.loan.repayplan.dataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBContentDao
{

	public static String strSeparator = java.io.File.separator;
	public static String TEMPLET_SEPERATOR = " &&& ";
	public static String PAGE_SEPERATOR = " :: ";
	public static String CONTENT_SEPERATOR = " ;; ";
	public static String SEPERATOR = "  ";

	private static Log4j log4j = null;

	private int arrLen = 5010;

	public OBContentDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	//	///////////////////////////////////////////////////////////////////////
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);

	private String formatString(String str) throws SQLException
	{
		return (str == null ? "" : str);
	}

	private void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(CallableStatement cs) throws SQLException
	{
		try
		{
			if (cs != null)
			{
				cs.close();
				cs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	//	///////////////////////////////////////////////////////////////////////////

	/**
	* �����ļ�
	* Create Date: 2003-10-15
	* @param strName �ļ���������·����������ʱ�ļ���Ϊ��
	* @param lStepNo ҳ��
	* @param strContent �ļ�����
	* @return String �������޸��ļ������֣�����Ϊ0�����ɹ�
	* @exception Exception
	*/
	public String saveContent(String strName, long lStepNo, String strContent) throws Exception
	{
		int i = 0;
		long lResult = 0;
		String sTmp = new String(strContent);
		String sOldContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try
		{

			// get the old content
			if (strName != null && strName.length() > 0)
			{
				fr = new FileReader(strName);
				br = new BufferedReader(fr);
				String record = new String();
				while ((record = br.readLine()) != null)
				{
					sbTmp = sbTmp.append(record);
				}
				sOldContent = sbTmp.toString();
				sbTmp.setLength(0);
				br.close();
				fr.close();
			}
			else // get the file name if create a new file
				{
				AutoFileInfo fileInfo = new AutoFileInfo();
				fileInfo.setFileType("txt");
				strName = AutoFileBean.getDestPath(Constant.DocType.LOANCONTRACTCONTENT);
				strName += AutoFileBean.getFileName(fileInfo);
			}

			//get the new content
			int nSIndex = 0;
			int nEIndex = 0;
			int nIndex = 0;
			String strData = "";
			for (i = 0; i < lStepNo; i++)
			{

				nSIndex = nEIndex;
				if (i > 0)
				{
					nIndex = nSIndex + 4;
				}

				if (sOldContent != null && sOldContent.length() > 0)
				{
					nEIndex = sOldContent.indexOf(PAGE_SEPERATOR, nIndex);
				}
				else
				{
					nEIndex = 0;
				}

				if (nEIndex <= nSIndex)
				{
					i++;
					break;
				}
			}

			if (i < lStepNo)
			{
				sOldContent = sOldContent + PAGE_SEPERATOR + strContent;
			}
			else
			{
				if (nSIndex > 0)
				{
					nSIndex = nSIndex + 4;
				}

				if (nEIndex > 0)
				{
					sOldContent = sOldContent.substring(0, nSIndex) + strContent + sOldContent.substring(nEIndex);
				}
				else if (sOldContent == null || sOldContent.length() <= 0)
				{
					sOldContent = strContent;
				}
				else
				{
					sOldContent = sOldContent.substring(0, nSIndex) + strContent;
				}
			}

			//update content
			java.io.File f = new java.io.File(strName);
			if (f.exists() && lStepNo ==1)
			{
				//f.delete();
			}
			FileWriter fw = new FileWriter(strName);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(sOldContent);
			pw.close();
			fw.close();

		}
		catch (Exception e)
		{
			strName = "";
			System.out.println(e.toString());
			throw e;
		}
		finally
		{

		}
		return strName;
	}

	/**
	* �õ��ļ�����
	* Create Date: 2003-10-15
	* @param strName �ļ���������·����
	* @param lStepNo ҳ��
	* @return String �ļ�����
	* @exception Exception
	*/
	public String getFileContent(String strName, long lStepNo) throws Exception
	{
		int i = 0;
		String sWholeContent = "";
		String sContractContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try
		{
			// get the whole content
			sWholeContent = getFileContent(strName);

			//get the content
			if (sWholeContent.length() > 0)
			{
				int nSIndex = 0;
				int nEIndex = 0;
				String strData = "";
				for (i = 0; i < lStepNo; i++)
				{
					nSIndex = nEIndex;
					nEIndex = sWholeContent.indexOf(PAGE_SEPERATOR, nSIndex + 4);
					if (nEIndex <= nSIndex)
					{
						i++;
						break;
					}
				}
				if (i < lStepNo)
				{
					sContractContent = "";
				}
				else
				{
					if (nSIndex > 0)
					{
						nSIndex = nSIndex + 4;
					}

					if (nEIndex > 0)
					{
						sContractContent = sWholeContent.substring(nSIndex, nEIndex);
					}
					else
					{
						sContractContent = sWholeContent.substring(nSIndex);
					}
				}
			}
			else
			{
				sContractContent = "";
			}
		}
		catch (Exception e)
		{
			sContractContent = "";
			System.out.println(e.toString());
			throw e;
		}
		finally
		{

		}
		return sContractContent;
	}

	/**
	* �õ��ļ�ȫ������
	* Create Date: 2003-10-15
	* @param strName �ļ���������·����
	* @return String �ļ�����
	* @exception Exception
	*/
	public String getFileContent(String strName) throws IException
	{
		int i = 0;
		String sContractContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try
		{
			// get the whole content
			if (strName != null && strName.length() > 0)
			{
				fr = new FileReader(strName);
				br = new BufferedReader(fr);
				String record = new String();

				while ((record = br.readLine()) != null)
				{
					sbTmp = sbTmp.append(record);
				}

				sContractContent = sbTmp.toString();
				sbTmp.setLength(0);
				br.close();
				fr.close();
			}
			else
			{
				sContractContent = "";
			}
		}
		catch (Exception e)
		{
			sContractContent = "";
			log4j.error(e.getMessage());
			throw new IException("Loan_E106");
		}
		return sContractContent;
	}

	/**
	* �ϲ��ļ�ģ����ļ�����
	* Create Date: 2003-10-15
	* @param strTempletName    �ļ�ģ����
	* @param strContentName    �ļ�������
	* @return String   �ϲ����ַ���
	* @exception Exception
	*/
	public String mergeContent(String strContentName, String strTempletName) throws IException, Exception
	{
		StringBuffer sbTmp = new StringBuffer();
		String strTemplet = "";
		String strContent = "";
		String strUnite = "";
		String record = new String();
		String arrContent[] = new String[arrLen];
		FileReader fr = null;
		BufferedReader br = null;
		int nArrayLength = 0;
		int nIndex1; //PAGE_SEPERATOR������λ��
		int nIndex2; //CONTENT_SEPERATOR������λ��
		int nTmp = 0;

		try
		{
			// get strContent
			if (strContentName == null || strContentName.length() == 0)
			{
				strContent = "";
			}
			else
			{
				try
				{
					fr = new FileReader(strContentName);
					br = new BufferedReader(fr);
					while ((record = br.readLine()) != null)
					{
						sbTmp = sbTmp.append(record);
					}
					strContent = sbTmp.toString();
					sbTmp.setLength(0);
					br.close();
					fr.close();
				}
				catch (Exception e)
				{
					log4j.error("1:" + e.getMessage());
					throw new IException("Loan_E106");
				}
			}

			// get the templet content:strTempletContent

			try
			{
				fr = new FileReader(strTempletName);
				br = new BufferedReader(fr);
				while ((record = br.readLine()) != null)
				{
					sbTmp = sbTmp.append(record);
				}
				strTemplet = sbTmp.toString();
				sbTmp.setLength(0);
				br.close();
				fr.close();
			}
			catch (Exception e)
			{
				log4j.error("2:" + e.getMessage());
				throw new IException("Loan_E106");
			}

			// String to Array
			String strPage = "";
			//ȡ��ĳһҳ������
			nIndex1 = strContent.indexOf(PAGE_SEPERATOR);
			while (nIndex1 >= 0)
			{
				strPage = strContent.substring(0, nIndex1);
				//ȡ��ÿһ���������
				nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
				while (nIndex2 >= 0)
				{
					if (nTmp < arrLen)
					{
						if (nIndex2 == 0)
						{
							arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else
						{
							arrContent[nTmp] = strPage.substring(0, nIndex2);
						}
					}
					strPage = strPage.substring(nIndex2 + 4);
					nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
					nTmp++;

				}

				if (nTmp < arrLen)
				{
					if (!strPage.equals(""))
					{
						arrContent[nTmp] = strPage;
					}
					else
					{
						arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}
				strContent = strContent.substring(nIndex1 + 4);
				nIndex1 = strContent.indexOf(PAGE_SEPERATOR);
				nTmp++;

			}
			//���һҳ
			strPage = strContent.substring(0);
			//ȡ��ÿһ���������
			nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
			while (nIndex2 >= 0)
			{
				if (nTmp < arrLen)
				{
					if (nIndex2 == 0)
					{
						arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					else
					{
						arrContent[nTmp] = strPage.substring(0, nIndex2);
					}
				}
				strPage = strPage.substring(nIndex2 + 4);
				nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
				nTmp++;

			}
			if (nTmp < arrLen)
			{
				if (!strPage.equals(""))
				{
					arrContent[nTmp] = strPage;
				}
				else
				{
					arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			nArrayLength = nTmp + 1;

			nTmp = 0;
			nIndex1 = strTemplet.indexOf(TEMPLET_SEPERATOR);

			while (nIndex1 >= 0)
			{

				if (nTmp < nArrayLength)
				{
					strUnite = strUnite + strTemplet.substring(0, nIndex1) + SEPERATOR + arrContent[nTmp] + SEPERATOR;
					nTmp++;
				}
				else
				{
					strUnite = strUnite + strTemplet.substring(0, nIndex1) + SEPERATOR + SEPERATOR;
				}
				strTemplet = strTemplet.substring(nIndex1 + 4);
				nIndex1 = strTemplet.indexOf(TEMPLET_SEPERATOR);

			}
			strUnite = strUnite + strTemplet;

			if (strTempletName.equals(Env.UPLOAD_PATH+"loan/template/yhchdhptxxy.txt"))
			{
				strContent = getFileContent(strContentName, 5);

				int nIndex = 0; //","������λ��
				String sTemp = strContent;
				nTmp = 0;
				if (sTemp.length() > 0)
				{
					nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0)
					{
						sTemp = sTemp.substring(nIndex + 4);
						nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					nTmp++;
				}
				String arrCont[] = new String[nTmp];

				if (strContent.length() > 0)
				{
					nIndex = 0; //","������λ��
					nTmp = 0;
					nIndex = strContent.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0)
					{
						arrCont[nTmp] = strContent.substring(0, nIndex);
						strContent = strContent.substring(nIndex + 4);
						nIndex = strContent.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					if (nTmp < arrLen)
					{
						arrCont[nTmp] = strContent;
					}
					nTmp++;
				}

				sTemp = "";
				nIndex = 0;
				for (int x = 0; x < nTmp / 10; x++)
				{
					sTemp += " <tr bgcolor=#FFFFFF > ";
					sTemp += "<td class=td-topright align=center >" + (x + 1) + "</td>";
					for (int i = 0; i < 10; i++)
					{
						if (nIndex < arrLen)
						{
							if (i == 9)
							{
								sTemp += "<td class=td-top align=center ><font face=����>" + arrCont[nIndex++] + "</font></td>";
							}
							else
							{
								sTemp += "<td class=td-topright align=center ><font face=����>" + arrCont[nIndex++] + "</font></td>";
							}
						}
					}
					sTemp += " </tr> ";
				}

				sTemp += "</table></body></html>";
				strUnite += sTemp;
			}
		}
		catch (IException ie)
		{
			log4j.error("3:" + ie.getMessage());
			throw new IException(ie.getMessage());
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strUnite;
	}

	/**
	* ��ϳ���ȷ�ĵ�ַ��ʽ
	* Create Date: 2003-10-15
	* @param  strProvince ʡ
	* @param  strCity ��
	* @param  strAddress ��ַ
	* @return String   ������ȷ�ĵ�ַ��ʽ
	* @exception Exception
	*/
	private String getAddress(String strProvince, String strCity, String sAddress) throws Exception
	{
		String strAddress = "";
		try
		{
			if (strProvince != null && strProvince.trim().length() > 0)
			{
				strAddress = strProvince + "ʡ";
			}
			if (strCity != null && strCity.trim().length() > 0)
			{
				strAddress = strAddress + strCity + "��";
			}
			if (sAddress == null)
			{
				sAddress = "";
			}
			strAddress = strAddress + sAddress;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strAddress;
	}

	/**
	* �õ���ͬ�ı�����
	* Create Date: 2003-10-15
	* @param lID LOAN_CONTRACTCONTENT���е�ID
	* @param lPageNo ҳ��
	* @return String ��ͬ�ı�����
	* @exception Exception
	*/
	public String getContent(long lID, long lPageNo) throws IException, Exception
	{
		OBContentInfo info = new OBContentInfo();
		String sContent = "";

		try
		{
			info = findByID(lID);
			String sFileName = info.getDocName();
			if (!sFileName.equals(""))
			{

				sContent = getFileContent(sFileName, lPageNo);
			}
			else
			{
				sContent = "";
			}
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
		return sContent;
	}


	/**
	* ��д�ı�ȱʡֵ  �������ͳ�Ʊ�
	* Create Date: 2003-10-15
	* @param lID    �ͻ���ʾ��Client���еı�ʾ��
	* @return String   ���غ�ͬ�ļ�����·��
	* @exception Exception
	*/
	public String addFinanceTJB(long lID) throws Exception
	{

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[50];

		int PAGECOUNT = 35;

		try
		{
			//*  �������ͳ�Ʊ�
			//saPageContent[0] = ""+lID;
			//saPageContent[PAGECOUNT-1] = ""+lID;
			for (int i = 0; i < PAGECOUNT; i++)
			{
				if (i == 0)
				{
					saPageContent[i] = "" + lID;
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
				if (i == PAGECOUNT - 1)
				{
					saPageContent[i] = "" + lID;
					sContent += formatString(saPageContent[i]);
				}
				else
				{
					saPageContent[i] = "";
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);
			//*/

		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return sFileName;
	}
	/**
	* ��д�ı�ȱʡֵ  ���չ��Э��
	* Create Date: 2003-11-12
	* @param lID    LOAN_EXTENDFORM���е�ID
	* @return String   ���غ�ͬ�ļ�����·��
	* @exception Exception
	*/
	public String addJKZQXY(long lID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();
		ExtendApplyInfo e_info = new ExtendApplyInfo();

		int FIRSTPAGECOUNT = 22;
		int SECONDPAGECOUNT = 33;
		int THIRDPAGECOUNT = 29;
		int i;

		try
		{
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb.append(" ass.NASSURETYPEID,ass.SASSURECODE,");
			sb.append(" extail.MEXTENDAMOUNT as ExtAmount,");
			sb.append(" extail.DTEXTENDENDDATE as ExtEndDate,");
			sb.append(" ext.MINTERESTADJUST as ExtInterestRate,");
			sb.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount");
			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb.append(" loan_ExtendForm ext,loan_ExtendDetail extail,loan_loanContractAssure ass");
			sb.append(" WHERE con.nBorrowClientID = cc.ID");
			sb.append(" AND ext.NCONTRACTID = con.id");
			sb.append(" AND ext.id = extail.NEXTENDFORMID");
			sb.append(" AND ass.NCONTRACTID(+) = con.id");
			sb.append(" AND ass.nClientID = c.id(+)");
			sb.append(" AND ass.nAssureTypeID(+) != " + LOANConstant.AssureType.CREDIT);
			sb.append(" AND ext.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			int iFlag = 0;
			while (rs.next())
			{
				if (iFlag > 0)
				{
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); //չ�ڽ��
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate").before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate : rs.getTimestamp("ExtEndDate"); //չ������
				}
				else
				{
					cInfo.setName(rs.getString("sName")); //  �ͻ�����
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs.getString("sCity"), rs.getString("sAddress"))); //  ��ַ
					cInfo.setPhone(rs.getString("sPhone")); // ���
					cInfo.setFax(rs.getString("sFax")); // ����
					cInfo.setZipCode(rs.getString("sZipCode")); // ��������
					cInfo.setBank1(rs.getString("sBank1")); //��������
					cInfo.setAccount(rs.getString("sAccount")); // �˺�

					consignInfo.setName(rs.getString("ConsignName")); //  �ͻ�����
					consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
					consignInfo.setAddress(getAddress(rs.getString("ConsignProvince"), rs.getString("ConsignCity"), rs.getString("ConsignAddress"))); //  ��ַ
					consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
					consignInfo.setFax(rs.getString("ConsignFax")); // ����
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
					consignInfo.setBank1(rs.getString("ConsignBank1")); //��������
					consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�

					info.setLoanReason(rs.getString("sLoanReason")); //���ԭ��
					info.setLoanPurpose(rs.getString("sLoanPurpose")); //�����;
					info.setExamineAmount(rs.getDouble("mExamineAmount")); //�����׼���
					info.setIntervalNum(rs.getLong("nIntervalNum")); //�������
					info.setLoanStart(rs.getTimestamp("dtStartDate")); //���ʼʱ��
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); //�������ʱ��
					info.setContractID(rs.getLong("contractID")); //��ͬID
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, info.getContractID(), null);
					info.setInterestRate(rInfo.getLateRate()); //ִ������
					info.setOther(rs.getString("sOther")); //������Դ
					info.setContractCode(rs.getString("SCONTRACTCODE")); //��ͬ���
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); //¼��ʱ��

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); //չ�ڽ��
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); //չ������
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); //չ������

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); //��֤����
					e_info.m_sExCode = rs.getString("SASSURECODE"); //������ͬ���*/
				}
				iFlag++;
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// ��ͬ��1ҳ
			//������
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			//�����
			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? "" : consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? "" : consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? "" : consignInfo.getFax();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? "" : consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? "" : consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? "" : consignInfo.getAccount();

			//������
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo.getAccount();
			//saPageContent[index++] = (info.getLoanReason() == null) ? "" : info.getLoanReason();

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				if (i == FIRSTPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "�����";

			sTemp = "";
			if (info.getExamineAmount() > 0)
			{
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			//saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat.formatRate(info.getInterestRate()); //��Ϣ
			saPageContent[index++] = "�����"; //չ�ڱ���
			sTemp = "";
			if (e_info.m_dExtendAmount > 0)
			{
				sTemp = DataFormat.formatAmount(e_info.m_dExtendAmount);
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			sTemp = "";
			sTemp = DataFormat.getDateString(e_info.m_tsExtendEndDate);
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			//saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat.formatRate(e_info.m_dInterestRate); //��Ϣ

			long[] lAssureType = { LOANConstant.AssureType.ASSURE, LOANConstant.AssureType.PLEDGE, LOANConstant.AssureType.IMPAWN };
			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					sTemp = "";
					sTemp = DataFormat.getDateString(info.getInputDate());
					if (sTemp.length() > 9)
					{
						saPageContent[index++] = sTemp.substring(0, 4);
						saPageContent[index++] = sTemp.substring(5, 7);
						saPageContent[index++] = sTemp.substring(8, 10);
					}
					else
					{
						saPageContent[index++] = "";
						saPageContent[index++] = "";
						saPageContent[index++] = "";
					}

					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "����ҽ���ͬ";

			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				if (i == SECONDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			//��ͬ��3ҳ
			sContent = "";
			index = 0;
			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
					saPageContent[index++] = "";
				}
			}

			sTemp = DataFormat.getDateString();
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = "";

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				if (i == THIRDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent); //*/
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}
	/**
	* ��д�ı�ȱʡֵ  ί�д���չ��Э��
	* Create Date: 2003-11-12
	* @param lID    LOAN_EXTENDFORM���е�ID
	* @return String   ���غ�ͬ�ļ�����·��
	* @exception Exception
	*/
	public String addWTDKZQXY(long lID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ClientInfo WTconsignInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();
		ExtendApplyInfo e_info = new ExtendApplyInfo();

		int FIRSTPAGECOUNT = 22;
		int SECONDPAGECOUNT = 31; //33;
		int THIRDPAGECOUNT = 28; //25;
		int FOURTHPAGECOUNT = 24;
		int i;

		try
		{
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb.append(" ass.NASSURETYPEID,ass.SASSURECODE,");
			sb.append(" extail.MEXTENDAMOUNT as ExtAmount,");
			sb.append(" extail.DTEXTENDENDDATE as ExtEndDate,");
			sb.append(" ext.MINTERESTADJUST as ExtInterestRate,");
			sb.append(" ccc.sName as WTConsignName,ccc.sLegalPerson as WTConsignLegalPerson,");
			sb.append(" ccc.sProvince as WTConsignProvince,ccc.sCity as WTConsignCity,");
			sb.append(" ccc.sAddress as WTConsignAddress,ccc.sPhone as WTConsignPhone,");
			sb.append(" ccc.sFax as WTConsignFax,ccc.sZipCode as WTConsignZipCode,");
			sb.append(" ccc.sBank1 as WTConsignBank1,ccc.sAccount as WTConsignAccount,");
			sb.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount");
			sb.append(" FROM loan_contractForm con,Client c,Client cc,Client ccc,");
			sb.append(" loan_ExtendForm ext,loan_ExtendDetail extail,loan_loanContractAssure ass");
			sb.append(" WHERE con.nBorrowClientID = cc.ID");
			sb.append(" AND con.NCONSIGNCLIENTID = ccc.ID");
			sb.append(" AND ext.NCONTRACTID = con.id");
			sb.append(" AND ext.id = extail.NEXTENDFORMID");
			sb.append(" AND ass.NCONTRACTID(+) = con.id");
			sb.append(" AND ass.nClientID = c.id(+)");
			sb.append(" AND ass.nAssureTypeID(+) != " + LOANConstant.AssureType.CREDIT);
			sb.append(" AND ext.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			int iFlag = 0;
			while (rs.next())
			{
				if (iFlag > 0)
				{
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); //չ�ڽ��
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate").before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate : rs.getTimestamp("ExtEndDate"); //չ������
				}
				else
				{
					cInfo.setName(rs.getString("sName")); //  �ͻ�����
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs.getString("sCity"), rs.getString("sAddress"))); //  ��ַ
					cInfo.setPhone(rs.getString("sPhone")); // ���
					cInfo.setFax(rs.getString("sFax")); // ����
					cInfo.setZipCode(rs.getString("sZipCode")); // ��������
					cInfo.setBank1(rs.getString("sBank1")); //��������
					cInfo.setAccount(rs.getString("sAccount")); // �˺�

					WTconsignInfo.setName(rs.getString("WTConsignName")); //  �ͻ�����
					WTconsignInfo.setLegalPerson(rs.getString("WTConsignLegalPerson")); // ���˴���
					WTconsignInfo.setAddress(getAddress(rs.getString("WTConsignProvince"), rs.getString("WTConsignCity"), rs.getString("WTConsignAddress"))); //  ��ַ
					WTconsignInfo.setPhone(rs.getString("WTConsignPhone")); // ���
					WTconsignInfo.setFax(rs.getString("WTConsignFax")); // ����
					WTconsignInfo.setZipCode(rs.getString("WTConsignZipCode")); // ��������
					WTconsignInfo.setBank1(rs.getString("WTConsignBank1")); //��������
					WTconsignInfo.setAccount(rs.getString("WTConsignAccount")); // �˺�

					consignInfo.setName(rs.getString("ConsignName")); //  �ͻ�����
					consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
					consignInfo.setAddress(getAddress(rs.getString("ConsignProvince"), rs.getString("ConsignCity"), rs.getString("ConsignAddress"))); //  ��ַ
					consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
					consignInfo.setFax(rs.getString("ConsignFax")); // ����
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
					consignInfo.setBank1(rs.getString("ConsignBank1")); //��������
					consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�

					info.setLoanReason(rs.getString("sLoanReason")); //���ԭ��
					info.setLoanPurpose(rs.getString("sLoanPurpose")); //�����;
					info.setExamineAmount(rs.getDouble("mExamineAmount")); //�����׼���
					info.setIntervalNum(rs.getLong("nIntervalNum")); //�������
					info.setLoanStart(rs.getTimestamp("dtStartDate")); //���ʼʱ��
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); //�������ʱ��
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, rs.getLong("contractID"), null);
					info.setInterestRate(rInfo.getLateRate()); //ִ������
					info.setOther(rs.getString("sOther")); //������Դ
					info.setContractID(rs.getLong("contractID")); //��ͬID
					info.setContractCode(rs.getString("SCONTRACTCODE")); //��ͬ���
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); //¼��ʱ��

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); //չ�ڽ��
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); //չ������
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); //չ������

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); //��֤����
					e_info.m_sExCode = rs.getString("SASSURECODE"); //������ͬ���*/
				}
				iFlag++;
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// ��ͬ��1ҳ
			//ί����
			saPageContent[index++] = (WTconsignInfo.getName() == null) ? "" : WTconsignInfo.getName();
			saPageContent[index++] = (WTconsignInfo.getLegalPerson() == null) ? "" : WTconsignInfo.getLegalPerson();
			saPageContent[index++] = (WTconsignInfo.getAddress() == null) ? "" : WTconsignInfo.getAddress();
			saPageContent[index++] = (WTconsignInfo.getPhone() == null) ? "" : WTconsignInfo.getPhone();
			saPageContent[index++] = (WTconsignInfo.getFax() == null) ? "" : WTconsignInfo.getFax();
			saPageContent[index++] = (WTconsignInfo.getZipCode() == null) ? "" : WTconsignInfo.getZipCode();
			saPageContent[index++] = (WTconsignInfo.getBank1() == null) ? "" : WTconsignInfo.getBank1();
			saPageContent[index++] = (WTconsignInfo.getAccount() == null) ? "" : WTconsignInfo.getAccount();

			//������
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			//�����
			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? "" : consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? "" : consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? "" : consignInfo.getFax();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? "" : consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? "" : consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? "" : consignInfo.getAccount();

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				if (i == FIRSTPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;

			//������
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo.getAccount();
			//saPageContent[index++] = (info.getLoanReason() == null) ? "" : info.getLoanReason();

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "����ҽ���ͬ";
			saPageContent[index++] = "�����";

			sTemp = "";
			if (info.getExamineAmount() > 0)
			{
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			//saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat.formatRate(info.getInterestRate()); //��Ϣ
			saPageContent[index++] = "�����"; //չ�ڱ���
			sTemp = "";
			if (e_info.m_dExtendAmount > 0)
			{
				sTemp = DataFormat.formatAmount(e_info.m_dExtendAmount);
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(e_info.m_tsExtendEndDate);
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			//saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat.formatRate(e_info.m_dInterestRate); //��Ϣ
			saPageContent[index++] = ""; //��������Ϣ//��Ϊ  ��������Ϣ
			saPageContent[index++] = ""; //������
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				if (i == SECONDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			//��ͬ��3ҳ
			sContent = "";
			index = 0;

			long[] lAssureType = { LOANConstant.AssureType.ASSURE, LOANConstant.AssureType.PLEDGE, LOANConstant.AssureType.IMPAWN };
			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					sTemp = "";
					sTemp = DataFormat.getDateString(info.getInputDate());
					if (sTemp.length() > 9)
					{
						saPageContent[index++] = sTemp.substring(0, 4);
						saPageContent[index++] = sTemp.substring(5, 7);
						saPageContent[index++] = sTemp.substring(8, 10);
					}
					else
					{
						saPageContent[index++] = "";
						saPageContent[index++] = "";
						saPageContent[index++] = "";
					}

					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "����ҽ���ͬ";

			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				if (i == THIRDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 3, sContent);

			//��ͬ��4ҳ
			sContent = "";
			index = 0;

			sTemp = DataFormat.getDateString();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = "";

			saPageContent[index++] = (WTconsignInfo.getName() == null) ? "" : WTconsignInfo.getName();
			saPageContent[index++] = (WTconsignInfo.getLegalPerson() == null) ? "" : WTconsignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FOURTHPAGECOUNT; i++)
			{
				if (i == FOURTHPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent); //*/
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	* ���ݺ�ͬ���ݱ�ʾ����ͬҳ�룬�޸�ָ��ҳ�ĺ�ͬ����
	* Create Date: 2003-10-15
	* @param  lID ��ͬ���ݱ�ʾ(LOAN_CONTRACTCONTENT���е�ID)
	* @param  lPageNo �޸ĺ�ͬ�ڼ�ҳ������
	* @param  strContent ��ͬ���޸�ҳ������
	* @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	* @exception Exception
	*/
	public long update(long lID, long lPageNo, String strContent) throws Exception
	{
		long lResult = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String sDocName = "";

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.sDocName");
			sbSQL.append(" FROM OB_ContractContent a");
			sbSQL.append(" WHERE a.ID = ?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				sDocName = rs.getString("sDocName"); //��ͬ�ı��ļ���
			}

			String sTemp = saveContent(sDocName, lPageNo, strContent);
			if (sTemp != null && !sTemp.equals(""))
			{
				lResult = 1;
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	* �õ���ͬ�ı�����
	* Create Date: 2003-10-15
	* @param lID LOAN_CONTRACTCONTENT���е�ID
	* @return OBContentInfo ��ͬ�ı�����
	* @exception Exception
	*/
	public OBContentInfo findByID(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		OBContentInfo info = new OBContentInfo();
		String sContent = "";

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*");
			sbSQL.append(" FROM OB_ContractContent a");
			sbSQL.append(" WHERE a.ID = ?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				info.setParentID(rs.getLong("nParentID")); //��ͬ�����ID
				info.setContractTypeID(rs.getLong("nContractTypeID")); //��ͬ����ID
				info.setContractID(rs.getLong("nContractID")); //��ͬID
				info.setDocName(rs.getString("sDocName")); //��ͬ�ı��ļ���		]

			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;

	}
	/**
	* �õ���������ID
	* Create Date: 2003-10-15
	* @param lID loanID
	* @return ��������ID
	* @exception Exception
	*/
	public long findLoanQuestinoaryByLoanID(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long ret = -1;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT ID");
			sbSQL.append(" FROM OB_ContractContent");
			sbSQL.append(" WHERE nParentID = ? and nContractTypeID=?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			ps.setLong(2, LOANConstant.ContractType.DKDCB);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ret = rs.getLong("ID");
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return ret;

	}

	/**
	* �õ���������ID
	* Create Date: 2003-10-15
	* @param lID loanID
	* @return ��������ID
	* @exception Exception
	*/
	public long findLoanQuestinoaryByClient(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long ret = -1;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" SELECT ID");
			sbSQL.append(" FROM OB_ContractContent");
			sbSQL.append(" WHERE nContractTypeID=? ");
			sbSQL.append(" and nParentID in (select ID from Loan_Loanform where loan_loanForm.Nborrowclientid=?) ");
			sbSQL.append(" order by ID  desc ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(2, lID);
			ps.setLong(1, LOANConstant.ContractType.DKDCB);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ret = rs.getLong("ID");
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return ret;

	}

	/**
	* ��ȡָ����ͬ�ĺ�ͬģ��
	* Create Date: 2003-10-15
	* @param  lContentID �ı�ID
	* @param  lContractTypeID �ı�����ID
	* @return String   ���غ�ͬ�ĺ�ͬģ��
	* @exception Exception
	*/
	public String getTemplate(long lContentID, long lContractTypeID) throws Exception
	{
		String strTemplate = "";
		try
		{
			strTemplate = getDocumentName(lContentID, lContractTypeID);
			int len = 0;
			if (strTemplate.length() > 0)
			{
				len = strTemplate.indexOf(";");
				strTemplate = strTemplate.substring(0, len);
			}
			else
			{
				strTemplate = "";
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strTemplate;
	}

	/**
	* ��ȡָ����ͬ���ı���JSP�ļ���
	* Create Date: 2003-10-15
	* @param  lContentID �ı�ID
	* @param  lContractTypeID �ı�����ID
	* @return String   �����ı���JSP�ļ���
	* @exception Exception
	*/
	public String getJspName(long lContentID, long lContractTypeID) throws Exception
	{
		String strTemplate = "";
		try
		{
			strTemplate = getDocumentName(lContentID, lContractTypeID);
			int len = 0;
			if (strTemplate.length() > 0)
			{
				len = strTemplate.indexOf(";");
				strTemplate = strTemplate.substring(len + 1, strTemplate.length());
			}
			else
			{
				strTemplate = "";
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strTemplate;
	}

	/**
	* ��ȡָ����ͬ�ĺ�ͬģ����ı���JSP�ļ���
	* Create Date: 2003-10-15
	* @param  lContentID �ı�ID
	* @param  lContractTypeID �ı�����ID
	* @return String   ���غ�ͬģ����ı���JSP�ļ���
	* @exception Exception
	*/
	public String getDocumentName(long lContentID, long lContractTypeID) throws Exception
	{
		String strTemplate = "";
		PreparedStatement ps = null;
		ResultSet rs1 = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();

		try
		{
			long lContractType = -1;
			if (lContractTypeID == LOANConstant.ContractType.ASSURE)
			{
				lContractType = LOANConstant.Template.HN_ASSURE;
			}
			else if (lContractTypeID == LOANConstant.ContractType.PLEDGE)
			{
				lContractType = LOANConstant.Template.HN_PLEDGE;
			}
			else if (lContractTypeID == LOANConstant.ContractType.IMPAWN)
			{
				lContractType = LOANConstant.Template.HN_IMPAWN;
			}
			else if (lContractTypeID == LOANConstant.ContractType.EXTEND)
			{
				OBContentInfo info = new OBContentInfo();
				info = findByID(lContentID);
				long lLoanType = -1;

				con = Database.getConnection();
				sbSQL.append(" SELECT a.nTypeID");
				sbSQL.append(" FROM loan_contractform a");
				sbSQL.append(" WHERE a.ID = ?");
				log4j.info(sbSQL.toString());

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, info.getContractID());
				rs1 = ps.executeQuery();

				while (rs1.next())
				{
					lLoanType = rs1.getLong("nTypeID"); //��������ID
				}
				rs1.close();
				rs1 = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);

				if (lLoanType == LOANConstant.LoanType.WT)
				{
					lContractType = LOANConstant.Template.HN_EXTENDWT;
				}
				else
				{
					lContractType = LOANConstant.Template.HN_EXTENDZY;
				}

			}
			else if (lContractTypeID == LOANConstant.ContractType.TX)
			{
				lContractType = LOANConstant.Template.HN_TX;
			}
			else if (lContractTypeID == LOANConstant.ContractType.DKDCB)
			{
				lContractType = LOANConstant.Template.HN_DKDCB;
			}

			else if (lContractTypeID == LOANConstant.ContractType.LOAN) //����ͬ
			{
				OBContentInfo info = new OBContentInfo();
				info = findByID(lContentID);
				long lLoanType = -1;

				con = Database.getConnection();
				sbSQL.append(" SELECT a.nTypeID");
				sbSQL.append(" FROM loan_contractform a");
				sbSQL.append(" WHERE a.ID = ?");
				log4j.info(sbSQL.toString());

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, info.getParentID());
				rs1 = ps.executeQuery();

				while (rs1.next())
				{
					lLoanType = rs1.getLong("nTypeID"); //��������ID
				}

				if (lLoanType == LOANConstant.LoanType.ZY)
				{
					lContractType = LOANConstant.Template.HN_ZYDK;
				}
				if (lLoanType == LOANConstant.LoanType.WT)
				{
					lContractType = LOANConstant.Template.HN_WTDK;
				}
				if (lLoanType == LOANConstant.LoanType.TX)
				{
					lContractType = LOANConstant.Template.HN_TX;
				}
				if (lLoanType == LOANConstant.LoanType.ZGXE)
				{
					lContractType = LOANConstant.Template.HN_ZGXE;
				}
				if (lLoanType == LOANConstant.LoanType.YT)
				{
					lContractType = LOANConstant.Template.HN_YTDK;
				}
			}
			strTemplate = LOANConstant.Template.getName(lContractType);

			if (rs1 != null)
			{
				rs1.close();
				rs1 = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs1 != null)
				{
					rs1.close();
					rs1 = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return strTemplate;
	}

	/**
	* �����ͬ�ı�����
	* Create Date: 2003-10-15
	* @param lID OBContentInfo��ͬ�ı�����(�������õ�������:ParentID,ContractID,ContractType,DocName)
	* @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	* @exception Exception
	*/
	public long saveContent(OBContentInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		long lResult = -1;
		long lContentID = 1;

		try
		{
			conn = Database.getConnection();
			sbSQL.append(" SELECT MAX(NVL(con.id,0)) as contentID");
			sbSQL.append(" FROM OB_ContractContent con");
			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				lContentID = rs.getLong("contentID");
			}

			lContentID = lContentID + 1;

			sbSQL.setLength(0);
			sbSQL.append(" INSERT INTO OB_ContractContent");
			sbSQL.append(" (ID,nParentID,nContractID,nContractTypeID,sDocName,sCode)");
			sbSQL.append(" VALUES (?,?,?,?,?,?)");
			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContentID);
			ps.setLong(2, info.getParentID());
			ps.setLong(3, info.getContractID());
			ps.setLong(4, info.getContractTypeID());
			ps.setString(5, info.getDocName());
			ps.setString(6, info.getCode());

			lResult = ps.executeUpdate();
			if (lResult > 0)
			{
				lResult = lContentID;
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	* ɾ����ͬ�ı�����
	* Create Date: 2003-10-15
	* @param long lContractID��ͬID
	* @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	* @exception Exception
	*/
	public long deleteContent(long lContractID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = new StringBuffer();
		long lResult = -1;

		try
		{
			conn = Database.getConnection();

			sbSQL.append(" DELETE OB_ContractContent");
			sbSQL.append(" WHERE nContractID = ? ");

			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			lResult = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	* ��д�ı�ȱʡֵ  ��������
	* Create Date: 2003-10-15
	* @param loanID
	* @return String   ���ص�����ļ�����·��
	* @exception Exception
	*/
	public String addLoanQuestionary(long loanID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long clientID = -1;

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		String[] sConsignInfo = new String[10];
		ClientInfo cInfo = new ClientInfo();

		int FIRSTPAGECOUNT = 64;
		int SECONDPAGECOUNT = 48;
		int THIRDPAGECOUNT = 26;
		int FORTHPAGECOUNT = 16;

		int i;

		try
		{
			conn = Database.getConnection();

			sb.append("select c.* from loan_loanForm l,client c");
			sb.append(" where c.id=l.nBorrowClientID and l.id=?");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, loanID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				cInfo.setName(rs.getString("sName")); //  �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs.getString("sCity"), rs.getString("sAddress"))); //  ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setParentCorpID(rs.getLong("NPARENTCORPID1")); //ĸ��˾���
				//cInfo.setCorpNatureID(rs.getLong("NCORPNATUREID")); //��ҵ����
				cInfo.setBank1(rs.getString("sBank1")); //��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
			}

			cleanup(rs);
			cleanup(ps);
			sb.setLength(0);

			if (cInfo.getParentCorpID() > 0)
			{
				sb.append("select * from client where id=?");
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, cInfo.getParentCorpID());
				rs = ps.executeQuery();
				if (rs.next());
				cInfo.setParentCorpName(rs.getString("sName"));
				cleanup(rs);
				cleanup(ps);
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			int index = 0;

			// ��1ҳ
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo.getPhone();
			saPageContent[index++] = (cInfo.getParentCorpName() == null) ? "" : cInfo.getParentCorpName();
			saPageContent[index++] = (LOANConstant.ClientCorpIndustry.getName(cInfo.getCorpNatureID()));
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo.getAccount();
			while (index < FIRSTPAGECOUNT)
				saPageContent[index++] = "";

			System.out.println("**************��һҳȡֵ���*************");

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				if (i == FIRSTPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// ��2ҳ,
			sContent = "";
			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				if (i == SECONDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			//��3ҳ
			sContent = "";
			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				if (i == THIRDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			//����ҳ
			sContent = "";
			for (i = 0; i < FORTHPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < FORTHPAGECOUNT; i++)
			{
				if (i == FORTHPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	* ��ͬ�ķſ�ͻ���ƻ�
	* Create Date: 2003-12-20
	* @param Collection cPlan ��ͬ
	* @return String   ���ص�����ļ�����·��
	* @exception Exception
	*/
	public String getPlanTab(Collection cPlan) throws Exception
	{
		Iterator iter = cPlan.iterator();
		int nPlan = cPlan.size();
		String sTemp = "";
		String[] strPayTime = new String[nPlan];
		String[] strRepayTime = new String[nPlan];
		String[] strPayAmount = new String[nPlan];
		String[] strRepayAmount = new String[nPlan];
		double dTotalPay = 0;
		double dTotalRepay = 0;
		int iPay = 0;
		int iRepay = 0;
		try
		{

			iter = cPlan.iterator();
			for (int i = 0; iter.hasNext(); i++)
			{
				RepayPlanInfo rp_info = (RepayPlanInfo) iter.next();
				if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) //
				{
					if (rp_info.tsPlanDate != null)
					{ 
						strPayTime[iPay] = DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strPayTime[iPay] = "&nbsp;";
					}
					if (rp_info.dAmount >= 0)
					{
						strPayAmount[iPay] = DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strPayAmount[iPay] = "&nbsp;";
					}
					dTotalPay = dTotalPay + rp_info.dAmount;
					iPay++;
				}
				else
				{
					if (rp_info.tsPlanDate != null)
					{
						strRepayTime[iRepay] = DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strRepayTime[iRepay] = "&nbsp;";
					}
					if (rp_info.dAmount >= 0)
					{
						strRepayAmount[iRepay] = DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strRepayAmount[iRepay] = "&nbsp;";
					}
					dTotalRepay = dTotalRepay + rp_info.dAmount;
					iRepay++;
				}
			}

			sTemp += " <b>������</b> <p>&nbsp;</p> \n";
			sTemp += " <p><h4 ALIGN=center><strong>�����ʽ</strong></h4><br></p> \n";
			sTemp += " <table width=100% border=0 cellspacing=0 class=table1 > \n";
			sTemp += " <tr> \n";
			sTemp += " <td colspan=2 ALIGN=center width=50%  class=td-rightbottom> ��ʽ </td> \n";
			sTemp += " <td colspan=2 ALIGN=center width=50% class=td-rightbottom> ���ʽ </td> \n";
			sTemp += " </tr> \n";
			sTemp += " <tr align=center> \n";
			sTemp += " <td width=25%  class=td-rightbottom> ������� </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> ����� </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> �������� </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> ������ </td> \n";
			sTemp += " </tr> \n";

			int iCount = iRepay > iPay ? iRepay : iPay;

			for (int j = 0; j < iCount; j++)
			{
				sTemp += " <TR> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strPayTime[j]) + " </TD> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strPayAmount[j]) + " </TD> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strRepayTime[j]) + " </TD> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strRepayAmount[j]) + " </TD> \n";
				sTemp += " </TR> \n";
			}
			sTemp += " <tr align=center> \n";
			sTemp += " <td width=25%  class=td-rightbottom> �ϼ� </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom>&nbsp;" + DataFormat.formatListAmount(dTotalPay) + " </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> �ϼ� </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom>&nbsp;" + DataFormat.formatListAmount(dTotalRepay) + " </td> \n";
			sTemp += " </tr> \n";
			sTemp += " </table> \n";

		}
		catch (RuntimeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sTemp;
	}

	public static void main(String[] args)
	{
		try
		{
			OBContentDao dao = new OBContentDao();

			String ss = dao.addJKZQXY(8);
			System.out.println("*************ss=" + ss);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
